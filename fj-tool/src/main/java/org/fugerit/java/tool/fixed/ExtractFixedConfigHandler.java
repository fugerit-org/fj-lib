package org.fugerit.java.tool.fixed;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.fugerit.java.core.fixed.parser.FixedFieldDescriptor;
import org.fugerit.java.tool.ToolHandlerHelper;

public class ExtractFixedConfigHandler extends ToolHandlerHelper {

	/**
	 * Arg for input file
	 */
	public static final String PARAM_INPUT_FILE = "input-file";
	
	/**
	 * Arg for input file
	 */
	public static final String PARAM_NORMALIZED_NAME = "normalize-name";
	
	/**
	 * Arg for input file
	 */
	public static final String PARAM_OUTPUT_FILE = "output-file";
	
	@Override
	public int handleWorker(Properties params) throws Exception {
		int exit = EXIT_OK;
		String inputFilePath = params.getProperty( PARAM_INPUT_FILE );
		boolean normalizeName = "1".equalsIgnoreCase( params.getProperty( PARAM_NORMALIZED_NAME , "0" ) );
		File inputFile = new File( inputFilePath );
		XSSFWorkbook workbook = new XSSFWorkbook( inputFile );
		Sheet sheet = workbook.getSheetAt( 0 );
		Iterator<Row> rows = sheet.rowIterator();
		List<FixedFieldDescriptor> listFields = new ArrayList<FixedFieldDescriptor>();
		int rowCount = 0;
		int start = 1;
		while ( rows.hasNext() ) {
			Row row = rows.next();
			String name = row.getCell( 0 ).getStringCellValue();
			String length = row.getCell( 1 ).getStringCellValue();
			try {
				Integer len = Integer.valueOf( length );
				if ( normalizeName ) {
					
				}
				FixedFieldDescriptor ffd = new FixedFieldDescriptor( name, start, len );
				logger.info( String.valueOf( ffd ) );
				listFields.add( ffd );
				start+= len;
			} catch (Exception e) {
				logger.warn( "Failed parsing of length for row : "+rowCount+" name "+name );
			}
			rowCount++;
		}
		return exit;
	}

}
