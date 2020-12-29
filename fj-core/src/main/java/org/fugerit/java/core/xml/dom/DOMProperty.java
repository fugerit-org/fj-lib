package org.fugerit.java.core.xml.dom;

import java.util.Properties;

import org.fugerit.java.core.cfg.ConfigException;
import org.fugerit.java.core.util.PropertyEntry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.DOMException;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class DOMProperty {

	private static Logger logger = LoggerFactory.getLogger( DOMProperty.class );
	
	public static final String TAG_ENTRY = "entry";
	
	public static final String ATT_KEY = "key";
	
	public static PropertyEntry createNtry( Element propertyTag ) throws DOMException, ConfigException {
		return PropertyEntry.newEntry( propertyTag.getAttribute( ATT_KEY ) , propertyTag.getTextContent() );
	}
	
	public static void fill( Properties props, NodeList propertyTagList ) throws DOMException, ConfigException {
		for ( int k=0; k<propertyTagList.getLength(); k++ ) {
			Element propertyTag = (Element) propertyTagList.item( k );
			PropertyEntry entry = createNtry( propertyTag );
			String oldValue = entry.setProperty( props );
			if ( oldValue != null ) {
				logger.debug( "override value '{}' for entry {}", oldValue, entry.toSimpleString() );
			}
		}
	}
	
	public static void fill( Properties props, Element tag, String entryTag ) throws DOMException, ConfigException {
		NodeList propertyTagList = tag.getElementsByTagName( entryTag );
		fill(props, propertyTagList);
	}
	
	public static void fill( Properties props, Element tag ) throws DOMException, ConfigException {
		fill(props, tag, TAG_ENTRY);
	}
	
}
