package org.fugerit.java.core.lang.binding;

import javax.xml.datatype.XMLGregorianCalendar;

/**
 * Convert any value found from the source binding to a string.
 * 
 * Null value is preserved, otherwise toString() i used.
 * 
 * @author Matteo a.k.a. Fugerit
 *
 */
public class BindingHelperXMLToDate extends BindingHelperDefault {

	public static final String ID = "xml-to-date";
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 229728343462037771L;
	
	public static final BindingHelper DEFAULT = new BindingHelperXMLToDate();
	
	public BindingHelperXMLToDate() {
		super( ID );
	}

	@Override
	public Object convertValue(BindingContext context, BindingConfig binding, BindingFieldConfig field, Object value) throws Exception {
		Object res = super.convertValue(context, binding, field, value);
		if ( res != null ) {
			XMLGregorianCalendar d = (XMLGregorianCalendar)res;
			res = d.toGregorianCalendar().getTime();
		}
		return res;
	}

	@Override
	public Class<?> getParamType() {
		return null;
	}

}
