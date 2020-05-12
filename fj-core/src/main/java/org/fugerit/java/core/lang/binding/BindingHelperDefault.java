package org.fugerit.java.core.lang.binding;

import java.io.Serializable;

import org.fugerit.java.core.cfg.xml.BasicIdConfigType;
import org.fugerit.java.core.lang.helpers.ClassHelper;
import org.fugerit.java.core.lang.helpers.StringUtils;
import org.fugerit.java.core.lang.helpers.reflect.PathHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Default binding helper implementation
 * 
 * New helper could just extends this class and override convertValue() or bindingWorker() method
 * 
 * @see BindingHelperStringValue as an example
 * 
 * @author Matteo a.k.a. Fugerit
 *
 */
public class BindingHelperDefault extends BasicIdConfigType implements Serializable, BindingHelper {

	public static final BindingHelper DEFAULT = new BindingHelperDefault( BindingCatalogConfig.ID_DEFAULT_HELPER );
	
	public BindingHelperDefault() {
	}
	
	public BindingHelperDefault( String id ) {
		this.setId( id );
	}
	
	private final static Logger logger = LoggerFactory.getLogger( BindingHelperDefault.class );
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1342342342323L;

	public Object convertValue(BindingConfig binding, BindingFieldConfig field, Object value) throws Exception {
		return value;
	}
	
	public void bindingWorker(BindingConfig binding, BindingFieldConfig field, Object from, Object to) throws Exception {
		String bindFrom = field.getActualBindFrom();
		String bindTo = field.getActualBindTo();
		Object valueFrom = PathHelper.lookup( bindFrom , from );
		valueFrom = this.convertValue(binding, field, valueFrom );
		if ( valueFrom == null && StringUtils.isNotEmpty( field.getInitOnNull() ) ) {
			valueFrom = ClassHelper.newInstance( field.getInitOnNull() );
		}
		boolean bind = false;
		if ( valueFrom != null ) {
			bind = PathHelper.bind( bindTo , to, valueFrom, this.getParamType(), true );
		}
		logger.debug( "bindFrom {} to {} "+bind, bindFrom, bindTo );
	}
	
	@Override
	public void bind(BindingConfig binding, BindingFieldConfig field, Object from, Object to) throws BindingException {
		try {
			this.bindingWorker(binding, field, from, to);
		} catch (Exception e) {
			throw new BindingException( binding, field, from, to, e );
		}
	}

	public Class<?> getParamType() {
		return null;
	}
	
}
