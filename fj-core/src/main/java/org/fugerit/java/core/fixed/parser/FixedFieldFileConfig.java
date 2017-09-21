package org.fugerit.java.core.fixed.parser;

import java.io.InputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * 
 * @author fugerit
 *
 */
public class FixedFieldFileConfig {

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
			FixedFieldFileDescriptor fileDescriptor = new FixedFieldFileDescriptor( idFile, "map" );
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
					FixedFieldDescriptor currentField = new FixedFieldDescriptor( id, description, Integer.parseInt( start ), Integer.parseInt( length ) );
					fileDescriptor.getListFields().add( currentField );
				}
			}
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
