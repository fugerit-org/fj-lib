package org.fugerit.java.core.cfg.xml;

import org.fugerit.java.core.lang.helpers.ClassHelper;
import org.fugerit.java.core.lang.helpers.reflect.MethodHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Attr;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XmlBeanHelper {

	private static final Logger logger = LoggerFactory.getLogger( XmlBeanHelper.class );
	
	public static final String MODE_XML_ATTRIBUTES = "bean-xml-attributes";
	
	public static final String MODE_XML_ELEMENTS = "bean-xml-elements";
	
	public static final String MODE_XML_FULL = "bean-xml-full";
	
	public static final String MODE_XML_DEFAULT = MODE_XML_ATTRIBUTES;
	
	public static <T> T setFromElement( String type, Element config ) throws Exception {
		@SuppressWarnings("unchecked")
		T bean = (T) ClassHelper.newInstance( type );
		setFromElement(bean, config);
		return bean;
	}
	
	public static <T> T setFromElement( String type, Element config, String mode ) throws Exception {
		@SuppressWarnings("unchecked")
		T bean = (T) ClassHelper.newInstance( type );
		setFromElement(bean, config, mode);
		return bean;
	}
	
	public static <T> void setFromElement( T bean, Element config ) throws Exception {
		setFromElement( bean, config, MODE_XML_DEFAULT );
	}
	
	public static <T> void setFromElement( T bean, Element config, String mode ) throws Exception {
		if ( MODE_XML_ATTRIBUTES.equalsIgnoreCase( mode ) || MODE_XML_FULL.equalsIgnoreCase( mode ) ) {
			setFromElementAtts(bean, config);
		}
		if ( MODE_XML_ELEMENTS.equalsIgnoreCase( mode ) || MODE_XML_FULL.equalsIgnoreCase( mode ) ) {
			setFromElementKids(bean, config);
		}
		if ( bean instanceof TextValueType ) {
			((TextValueType) bean ).setTextValue( config.getTextContent() );
		}
	}
	
	public static <T> void setFromElementSafe( T bean, Element config, String mode ) {
		try {
			setFromElement( bean , config, mode );
		} catch (Exception e) {
			logger.warn( "Cannot set all parameters from bean, usually safe to ignore : "+e );
		}
	}
	
	public static <T> void setFromElementSafe( T bean, Element config ) {
		setFromElementSafe(bean, config, MODE_XML_DEFAULT );
	}

	public static <T> void setFromElementAtts( T bean, Element config ) throws Exception {
		NamedNodeMap atts = config.getAttributes();
		for ( int ak=0; ak<atts.getLength(); ak++ ) {
			Attr att = (Attr)atts.item( ak );
			String key = att.getName();
			String value = att.getValue();
			MethodHelper.invokeSetter( bean , key, String.class, value );
		}
	}
	
	public static <T> void setFromElementKids( T bean, Element config ) throws Exception {
		NodeList kids = config.getChildNodes();
		for ( int ak=0; ak<kids.getLength(); ak++ ) {
			Node current = kids.item( ak );
			if ( current.getNodeType() == Node.ELEMENT_NODE ) {
				Element tag = (Element)current;
				String key = tag.getTagName();
				MethodHelper.invokeSetter( bean , key, String.class, tag.getTextContent() );
			}			
		}
	}
	
}
