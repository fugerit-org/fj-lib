package org.fugerit.java.core.util.i18n;

public class ParamI18N {
	
	private ParamI18N( String key ) {
		this.key = key;
	}
	
	private String key;

	public String getKey() {
		return key;
	}
	
	public static ParamI18N newParamI18N( String key ) {
		return new ParamI18N( key );
	}

	@Override
	public String toString() {
		return this.getClass().getSimpleName()+"[key:"+this.getKey()+"]";
	}
	
}
