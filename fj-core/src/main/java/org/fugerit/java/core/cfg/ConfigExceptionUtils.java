package org.fugerit.java.core.cfg;

public class ConfigExceptionUtils {

	public ConfigException stadardExceptionWrapping( Exception e ) throws ConfigException {
		throw new ConfigException( "Configuration error : "+e, e );
	}
	
	public ConfigRuntimeException stadardRuntimeExceptionWrapping( Exception e ) {
		throw new ConfigRuntimeException( "Configuration error : "+e, e );
	}
	
}
