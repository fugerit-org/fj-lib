package org.fugerit.java.core.lang.binding;

import java.util.Collection;

import org.fugerit.java.core.lang.helpers.StringUtils;

/**
 * Convert any value found from the source binding to a string.
 * 
 * Null value is preserved, otherwise toString() i used.
 * 
 * @author Matteo a.k.a. Fugerit
 *
 */
public class BindingHelperCollectionToObject extends BindingHelperDefault {

	public static final String ID = "collection-to-object";
	
	public static final String MULTI_VALUE_MODE = "multiValueMode";
	
	public static final String MULTI_VALUE_MODE_FAIL = "fail";
	
	public static final String MULTI_VALUE_MODE_FIRST = "first";
	
	public static final String MULTI_VALUE_MODE_DEFAULT = MULTI_VALUE_MODE_FAIL;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 229728343462037771L;
	
	public static final BindingHelper DEFAULT = new BindingHelperCollectionToObject();
	
	public BindingHelperCollectionToObject(String id) {
		super(id);
	}

	public BindingHelperCollectionToObject() {
		this( ID );
	}

	private Object convertWorker( Collection<?> c, Object res, String multiValueMode ) throws BindingException {
		if ( c.size() > 1 && multiValueMode.equalsIgnoreCase( MULTI_VALUE_MODE_FAIL ) ) {
			throw new BindingException( "Binding error : expected maximum one value : "+c.size() );
		} else if ( c.size() > 0 ) {
			// take first element
			int count = 0;
			for ( Object obj : c ) {
				res = obj;
				count++;
				if ( count > 1 ) {
					break;
				}
			}
		}
		return res;
	}
	
	@Override
	public Object convertValue(BindingContext context, BindingConfig binding, BindingFieldConfig field, Object value) throws Exception {
		Object res = super.convertValue(context, binding, field, value);
		String multiValueMode = StringUtils.valueWithDefault( this.getParam01() , MULTI_VALUE_MODE_DEFAULT );
		if ( res != null && ( value instanceof Collection<?>  ) ) {
			Collection<?> c = (Collection<?>)value;
			res = this.convertWorker(c, res, multiValueMode);
		}
		return res;
	}
	
}
