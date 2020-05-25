package org.fugerit.java.core.lang.binding;

import org.fugerit.java.core.lang.helpers.ClassHelper;
import org.fugerit.java.core.lang.helpers.reflect.PathHelper;

/**
 * Convert any value found from the source binding to a string.
 * 
 * Null value is preserved, otherwise toString() i used.
 * 
 * @author Matteo a.k.a. Fugerit
 *
 */
public class BindingHelperInitTo extends BindingHelperDefault {

	public static final String ID = "init-to";
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 229728343462037771L;
	
	public static final BindingHelper DEFAULT = new BindingHelperInitTo();
	
	public BindingHelperInitTo() {
		super( ID );
	}

	@Override
	public void bindingWorker(BindingConfig binding, BindingFieldConfig field, Object from, Object to) throws Exception {
		String actualBindingTo = field.getActualBindTo();
		Object value = PathHelper.bindInit( actualBindingTo, to );
		if ( value == null ) {
			String type = field.getTypeTo();
			value = ClassHelper.newInstance( type );
			PathHelper.bind( actualBindingTo , to, value );
		}
	}

}
