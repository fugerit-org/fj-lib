package org.fugerit.java.core.lang.binding;

import org.fugerit.java.core.cfg.xml.BasicIdConfigType;
import org.fugerit.java.core.lang.helpers.StringUtils;

/**
 * Config attributes : 
 * 
 * id (required) - id of the binding
 * bindFrom (optional, default to id) - path to source object property to use for binding
 * bindTo (optional, default to id) - path to destination object property to use for binding
 * initOnNull (optional) - if set, the type to be used to create value if 'bindFrom' path lookup to 'null'
 * helper (optional, default to 'default-helper') helper to be used for binding (for instance if a type mapping is needed)
 * 
 * @author Matteo a.k.a. Fugerit
 *
 */
public class BindingFieldConfig extends BasicIdConfigType {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3443837411485058583L;

	private String bindFrom;
	
	private String bindTo;

	private String initOnNull;

	private String helper;
	
	public String getBindFrom() {
		return bindFrom;
	}

	public void setBindFrom(String bindFrom) {
		this.bindFrom = bindFrom;
	}

	public String getBindTo() {
		return bindTo;
	}

	public void setBindTo(String bindTo) {
		this.bindTo = bindTo;
	}

	public String getInitOnNull() {
		return initOnNull;
	}

	public void setInitOnNull(String initOnNull) {
		this.initOnNull = initOnNull;
	}

	public String getHelper() {
		return helper;
	}

	public void setHelper(String helper) {
		this.helper = helper;
	}

	/**
	 * Returns bindFrom field or, if not found, id field
	 * 
	 * @return the actual binding
	 */
	public String getActualBindFrom() {
		return StringUtils.valueWithDefault( this.getBindFrom() , this.getId() );
	}
	
	/**
	 * Returns findTo field or, if not found, id field
	 * 
	 * @return the actual binding
	 */
	public String getActualBindTo() {
		return StringUtils.valueWithDefault( this.getBindTo() , this.getId() );
	}
	
}
