package org.fugerit.java.core.fixed.parser;

import java.io.InputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.fugerit.java.core.lang.helpers.ClassHelper;
import org.fugerit.java.core.lang.helpers.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * 
 * @author fugerit
 *
 */
public class FixedFieldFileConfig {

	private static void handleValidatorList( FixedFieldFileDescriptor fileDescriptor, Element currentFileTag ) throws Exception {
		NodeList validatorListTagList = currentFileTag.getElementsByTagName( "validator-list" );
		for ( int k=0; k<validatorListTagList.getLength(); k++ ) {
			Element currentValidatorListTag = (Element) validatorListTagList.item( k );
			NodeList validatorTagList = currentValidatorListTag.getElementsByTagName( "validator" );
			for ( int j=0; j<validatorTagList.getLength(); j++ ) {
				Element currentValidatorTag = (Element) validatorTagList.item( j );
				String id = currentValidatorTag.getAttribute( "id" );
				String type = currentValidatorTag.getAttribute( "type" );
				FixedFileFieldValidator validator = (FixedFileFieldValidator)ClassHelper.newInstance( type );
				validator.configure( currentValidatorTag );
				fileDescriptor.getValidators().put( id , validator );
			}
		}
	}
	
	private static void handleFiledList( FixedFieldFileDescriptor fileDescriptor, Element currentFileTag ) {
		NodeList fieldListTagList = currentFileTag.getElementsByTagName( "field-list" );
		for ( int k=0; k<fieldListTagList.getLength(); k++ ) {
			Element currentFieldListTag = (Element) fieldListTagList.item( k );
			NodeList fieldTagList = currentFieldListTag.getElementsByTagName( "field" );
			for ( int j=0; j<fieldTagList.getLength(); j++ ) {
				Element currentFieldTag = (Element) fieldTagList.item( j );
				String id = currentFieldTag.getAttribute( "id" );
				String description = currentFieldTag.getAttribute( "description" );
				String start = currentFieldTag.getAttribute( "start" );
				String length = currentFieldTag.getAttribute( "length" );
				String validator = currentFieldTag.getAttribute( "validator" );
				FixedFieldDescriptor currentField = new FixedFieldDescriptor( id, description, Integer.parseInt( start ), Integer.parseInt( length ) );
				//totalLength+= currentField.getLength();
				if ( StringUtils.isNotEmpty( validator ) ) {
					FixedFileFieldValidator fieldValidator = fileDescriptor.getValidators().get( validator );
					currentField.setValidator( fieldValidator );
				}
				fileDescriptor.getListFields().add( currentField );
			}
		}
	}
	
	public static FixedFieldFileConfig parseConfig( InputStream is ) throws Exception {
		FixedFieldFileConfig config = new FixedFieldFileConfig();
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder parser = dbf.newDocumentBuilder();
		Document doc = parser.parse( is );
		Element root = doc.getDocumentElement();
		NodeList fileTagList = root.getElementsByTagName( "fixed-field-file" );
		for ( int i=0; i<fileTagList.getLength(); i++ ) {
			Element currentFileTag = (Element) fileTagList.item( i );
			String idFile = currentFileTag.getAttribute( "id" );
			String checkLength = currentFileTag.getAttribute( "check-length" );
			String endline = currentFileTag.getAttribute( "endline" );
			String encoding = currentFileTag.getAttribute( "encoding" );
			String baseLocale = currentFileTag.getAttribute( "base-locale" );
			FixedFieldFileDescriptor fileDescriptor = new FixedFieldFileDescriptor( idFile, "map", encoding );
			if ( StringUtils.isNotEmpty( checkLength ) ) {
				fileDescriptor.setCheckLengh( Integer.parseInt( checkLength.trim() ) );
			}
			if ( StringUtils.isNotEmpty( endline ) ) {
				fileDescriptor.setEndline( endline );
			}
			if ( StringUtils.isNotEmpty( baseLocale ) ) {
				fileDescriptor.setBaseLocale( baseLocale );
			}
			
			// validator list
			handleValidatorList(fileDescriptor, currentFileTag);
			
			//int totalLength = 0;
			
			// field list
			handleFiledList(fileDescriptor, currentFileTag);
			
			// TODO: review
			//fileDescriptor.setCheckLengh( totalLength );
			config.addFileDescriptor( idFile , fileDescriptor );
		}
		is.close();
		return config;
	}
	
	private FixedFieldFileConfig() {
		this.mapFileDescriptor = new HashMap<String, FixedFieldFileDescriptor>();
	}
	
	private Map<String, FixedFieldFileDescriptor> mapFileDescriptor;
	
	public void addFileDescriptor( String id, FixedFieldFileDescriptor descriptor ) {
		this.mapFileDescriptor.put( id , descriptor );
	}
	
	public FixedFieldFileDescriptor getFileDescriptor( String id ) {
		return this.mapFileDescriptor.get( id );
	}

	public Collection<String> getIds() {
		return this.mapFileDescriptor.keySet();
	}
	
}

