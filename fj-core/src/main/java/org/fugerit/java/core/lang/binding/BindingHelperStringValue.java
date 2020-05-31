package org.fugerit.java.core.lang.binding;


/**
 * Convert any value found from the source binding to a string.
 * 
 * Null value is preserved, otherwise toString() i used.
 * 
 * @author Matteo a.k.a. Fugerit
 *
 */
public class BindingHelperStringValue extends BindingHelperDefault {

	/**
	 * 
	 */
	private static final long serialVersionUID = 229728343462037771L;
	
	public static final BindingHelper DEFAULT = new BindingHelperStringValue();
	
	public BindingHelperStringValue() {
		super( BindingCatalogConfig.ID_STRING_VALUE_HELPER );
	}

	@Override
	public Object convertValue(BindingContext context, BindingConfig binding, BindingFieldConfig field, Object value) throws Exception {
		Object res = super.convertValue(context, binding, field, value);
		if ( res != null ) {
			res = res.toString();
		}
		return res;
	}

}
