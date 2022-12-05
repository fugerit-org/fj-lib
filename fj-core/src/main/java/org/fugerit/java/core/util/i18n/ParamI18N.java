package org.fugerit.java.core.util.i18n;

public class ParamI18N {
	
	private ParamI18N( String key, HelperI18N helper ) {
		this.key = key;
		this.helper = helper;
	}
	
	private String key;

	private HelperI18N helper;
	
	public String getKey() {
		return key;
	}
	
	public HelperI18N getHelper() {
		return helper;
	}

	public static ParamI18N newParamI18N( String key ) {
		return newParamI18N( key, null );
	}

	public static ParamI18N newParamI18N( String key, HelperI18N helper ) {
		return new ParamI18N( key, helper );
	}
	
	@Override
	public String toString() {
		return this.getClass().getSimpleName()+"[key:"+this.getKey()+"]";
	}
	
}
