package org.fugerit.java.tool.fixed;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.fugerit.java.core.fixed.parser.FixedFieldDescriptor;
import org.fugerit.java.tool.ToolHandlerHelper;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class ExtractFixedConfigHandler extends ToolHandlerHelper {

	/**
	 * Arg for input file
	 */
	public static final String PARAM_INPUT_FILE = "input-file";
	
	/**
	 * Arg output xml
	 */
	public static final String PARAM_OUTPUT_XML = "output-xml";
	
	/**
	 * Arg output bean
	 */
	public static final String PARAM_OUTPUT_BEAN = "output-bean";
	
	@Override
	public int handleWorker(Properties params) throws Exception {
		int exit = EXIT_OK;
		String inputFilePath = params.getProperty( PARAM_INPUT_FILE );
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
				FixedFieldDescriptor ffd = new FixedFieldDescriptor( name, start, len );
				logger.info( String.valueOf( ffd ) );
				listFields.add( ffd );
				start+= len;
			} catch (Exception e) {
				logger.warn( "Failed parsing of length for row : "+rowCount+" name "+name );
			}
			rowCount++;
		}
		workbook.close();
		// generating xml output
		String outputXmlPath = params.getProperty( PARAM_OUTPUT_XML );
		if ( outputXmlPath != null ) {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder parser = dbf.newDocumentBuilder();
			Document doc = parser.newDocument();
			Element root = doc.createElement( "field-list" );
			Iterator<FixedFieldDescriptor> itFields = listFields.iterator();
			while ( itFields.hasNext() ) {
				FixedFieldDescriptor ffd = itFields.next();
				Element fieldTag = doc.createElement( "field" );
				fieldTag.setAttribute( "id" , ffd.getNormalizedName() );
				fieldTag.setAttribute( "start" , String.valueOf( ffd.getStart() ) );
				fieldTag.setAttribute( "length" , String.valueOf( ffd.getLength() ) );
				fieldTag.setAttribute( "end" , String.valueOf( ffd.getEnd() ) );
				fieldTag.setAttribute( "description" , ffd.getName() );
				root.appendChild( fieldTag );
			}
			TransformerFactory tFactory = TransformerFactory.newInstance();
			Transformer transformer = tFactory.newTransformer();
			transformer.setOutputProperty( OutputKeys.INDENT, "yes" );
			transformer.setOutputProperty( OutputKeys.OMIT_XML_DECLARATION, "yes" );
			DOMSource source = new DOMSource( root );
			FileOutputStream fos = new FileOutputStream( new File( outputXmlPath ) );
			StreamResult result = new StreamResult( fos );
			transformer.transform(source, result);
			fos.close();
		}
		return exit;
	}

}
