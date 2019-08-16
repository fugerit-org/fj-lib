package org.fugerit.java.core.cfg.xml;

import org.fugerit.java.core.lang.helpers.ClassHelper;
import org.fugerit.java.core.lang.helpers.reflect.MethodHelper;
import org.w3c.dom.Attr;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

public class XmlBeanHelper {

	public static <T> T setFromElement( String type, Element config ) throws Exception {
		@SuppressWarnings("unchecked")
		T bean = (T) ClassHelper.newInstance( type );
		setFromElement(bean, config);
		return bean;
	}
	
	public static <T> void setFromElement( T bean, Element config ) throws Exception {
		NamedNodeMap atts = config.getAttributes();
		for ( int ak=0; ak<atts.getLength(); ak++ ) {
			Attr att = (Attr)atts.item( ak );
			String key = att.getName();
			String value = att.getValue();
			MethodHelper.invokeSetter( bean , key, String.class, value );
		}
		if ( bean instanceof TextValueType ) {
			((TextValueType) bean ).setTextValue( config.getTextContent() );
		}
	}
	
}
