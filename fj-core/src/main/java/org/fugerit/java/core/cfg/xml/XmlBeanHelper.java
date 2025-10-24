package org.fugerit.java.core.cfg.xml;

import org.fugerit.java.core.function.SafeFunction;
import org.fugerit.java.core.lang.helpers.ClassHelper;
import org.fugerit.java.core.lang.helpers.reflect.MethodHelper;
import org.w3c.dom.Attr;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import lombok.extern.slf4j.Slf4j;

/**
 * <p>Set fields in a bean from an xml.</p>
 * 
 * <p>NOTE: starting from version 8.4.X java.lang.Exception removed from all methods in favor of org.fugerit.java.core.cfg.ConfigRuntimeException.</p>
 *  
 * @see <a href="https://fuzzymemory.fugerit.org/src/docs/sonar_cloud/java-S112.html">Define and throw a dedicated exception instead of using a generic one.</a>
 * 
 */
@Slf4j
public class XmlBeanHelper {

	private XmlBeanHelper() {}
	
	/**
	 * Try to set all fields of the bean from the attributes of an xml element
	 */
	public static final String MODE_XML_ATTRIBUTES = "bean-xml-attributes";

	/**
	 * Try to set all fields of the bean from the children of an xml element
	 */
	public static final String MODE_XML_ELEMENTS = "bean-xml-elements";

	/**
	 * Try to set all fields of the bean from both the attributes and children of an xml element (in this order).
	 */
	public static final String MODE_XML_FULL = "bean-xml-full";
	
	/**
	 * Default mode, as MODE_XML_ATTRIBUTES.
	 */
	public static final String MODE_XML_DEFAULT = MODE_XML_ATTRIBUTES;
	
	public static <T> T setFromElement( String type, Element config ) {
		return setFromElement( type ,  config, MODE_XML_DEFAULT );
	}
	
	public static <T> T setFromElement( String type, Element config, String mode ) {
		return SafeFunction.get( () -> {
			@SuppressWarnings("unchecked")
			T bean = (T) ClassHelper.newInstance( type );
			setFromElement(bean, config, mode);
			return bean;	
		} );
	}
	
	public static <T> void setFromElement( T bean, Element config ) {
		setFromElement( bean, config, MODE_XML_DEFAULT );
	}
	
	public static <T> void setFromElement( T bean, Element config, String mode ) {
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
	
	public static <T> boolean setFromElementSafe( T bean, Element config, String mode ) {
		boolean set = false;
		try {
			setFromElement( bean , config, mode );
			set = true;
		} catch (Exception e) {
			log.debug( "Cannot set all parameters from bean, usually safe to ignore : {}", e.toString() );
		}
		return set;
	}
	
	public static <T> void setFromElementSafe( T bean, Element config ) {
		setFromElementSafe(bean, config, MODE_XML_DEFAULT );
	}

	public static <T> void setFromElementAtts( T bean, Element config ) {
		NamedNodeMap atts = config.getAttributes();
		for ( int ak=0; ak<atts.getLength(); ak++ ) {
			Attr att = (Attr)atts.item( ak );
			String key = att.getName();
			String value = att.getValue();
			MethodHelper.invokeSetter( bean , key, String.class, value );
		}
	}
	
	public static <T> void setFromElementKids( T bean, Element config ) {
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
