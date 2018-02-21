package org.fugerit.java.tool.fixed;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.Reader;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.fugerit.java.core.fixed.parser.FixedFieldDescriptor;
import org.fugerit.java.core.fixed.parser.FixedFieldFileConfig;
import org.fugerit.java.core.fixed.parser.FixedFieldFileDescriptor;
import org.fugerit.java.core.fixed.parser.FixedFieldFileReader;
import org.fugerit.java.tool.ToolHandlerHelper;

public class FixedToExcel extends ToolHandlerHelper {

	/**
	 * Arg for input file
	 */
	public static final String PARAM_INPUT_FILE = "input-file";

	/**
	 * Arg for input config
	 */
	public static final String PARAM_INPUT_CONFIG = "input-config";
	
	/**
	 * Arg for input config
	 */
	public static final String PARAM_CONFIG_ID = "config-id";
	
	/**
	 * Arg output xml
	 */
	public static final String PARAM_OUTPUT_XLS = "output-xls";
	
	@Override
	public int handleWorker(Properties params) throws Exception {
		int exit = EXIT_OK;
		String inputFilePath = params.getProperty( PARAM_INPUT_FILE );
		String inputConfigPath = params.getProperty( PARAM_INPUT_CONFIG );
		String configId = params.getProperty( PARAM_CONFIG_ID );
		String outputXls = params.getProperty( PARAM_OUTPUT_XLS );
		
		FileInputStream fis = new FileInputStream( new File( inputConfigPath ) );
		FixedFieldFileConfig config = FixedFieldFileConfig.parseConfig( fis );
		fis.close();
		FixedFieldFileDescriptor descriptor = config.getFileDescriptor( configId );
		Reader fr = new FileReader( new File( inputFilePath) );
		FixedFieldFileReader reader = new FixedFieldFileReader( descriptor, fr );
		
		Workbook workbook = new HSSFWorkbook();
		Sheet s = workbook.createSheet( "data" );
		
		int currentRow = 0;
		
		Row rowHead = s.createRow( 0 );
		for ( int k=0; k<descriptor.getListFields().size(); k++ ) {
			FixedFieldDescriptor ffd = descriptor.getListFields().get( k );
			Cell cell = rowHead.createCell( k );
			String value = ffd.getName();
			cell.setCellValue( value );
		}
		currentRow++;
		
		while ( reader.hasNext() ) {
			Map<String, String> map = reader.nextRawMap();
			Row row = s.createRow( currentRow );
			int currentCell = 0;
			Iterator<FixedFieldDescriptor> it = descriptor.getListFields().iterator();
			while ( it.hasNext() ) {
				FixedFieldDescriptor ffd = it.next();
				Cell cell = row.createCell( currentCell );
				String value = map.get( ffd.getNormalizedName() );
				cell.setCellValue( value );
				currentCell++;
			}
			currentRow++;
			if ( currentRow%1000 == 0 ) {
				System.out.println( "CURRENT ROW "+currentRow );
			}
		}
		
		reader.close();
		FileOutputStream fos = new FileOutputStream( new File( outputXls ) );
		workbook.close();
		workbook.write( fos );
		fos.flush();
		fos.close();

		return exit;
	}

}
