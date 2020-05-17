package org.fugerit.java.core.lang.binding;

import java.io.Serializable;

import org.fugerit.java.core.cfg.ConfigException;
import org.fugerit.java.core.cfg.helpers.XMLConfigurableObject;
import org.fugerit.java.core.cfg.xml.IdConfigType;
import org.fugerit.java.core.lang.helpers.BooleanUtils;
import org.fugerit.java.core.lang.helpers.ClassHelper;
import org.fugerit.java.core.lang.helpers.StringUtils;
import org.fugerit.java.core.lang.helpers.reflect.PathHelper;
import org.fugerit.java.core.util.collection.KeyString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Element;

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
public class BindingHelperDefault extends XMLConfigurableObject implements Serializable, BindingHelper, IdConfigType, KeyString {

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
	
	protected Class<?> resolveType( BindingFieldConfig field ) throws Exception {
		Class<?> c = this.getParamType();
		if ( StringUtils.isNotEmpty( field.getTypeTo() ) ) {
			c = Class.forName( field.getTypeTo() );
		}
		return c;
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
			String useBindingId = field.getUseBinding();
			String tryInit = StringUtils.valueWithDefault( binding.getTryInit() , BooleanUtils.BOOLEAN_1 );
			// check if I have to look for another binding to use
			if ( StringUtils.isNotEmpty( useBindingId ) ) {
				Object newFrom = valueFrom;
				Object newTo = PathHelper.bindInit( bindTo , to );
				binding.getCatalog().bind( useBindingId, newFrom, newTo);
				bind = true;
			} else {
				// actual binding
				Class<?> paramType = this.resolveType(field);
				bind = PathHelper.bind( bindTo , to, valueFrom, paramType, BooleanUtils.isTrue( tryInit ) );
			}	
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

	@Override
	public void configure(Element tag) throws ConfigException {
		
	}

	private String id;
	
	private String type;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	@Override
	public String getKey() {
		return this.getId();
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
}
