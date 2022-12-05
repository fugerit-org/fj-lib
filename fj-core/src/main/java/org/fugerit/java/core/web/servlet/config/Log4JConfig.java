package org.fugerit.java.core.web.servlet.config;

import java.lang.reflect.Method;
import java.util.Properties;

import org.fugerit.java.core.cfg.ConfigException;
import org.fugerit.java.core.util.PropsIO;

public class Log4JConfig extends BasicConfig {

	/*
	 * 
	 */
	private static final long serialVersionUID = 8238463428937400722L;

	@Override
	public void configure(Properties props) throws ConfigException {
		this.getLogger().info( "configure start" );
		String configPath = props.getProperty( "log4j-config" );
		try {
			this.getLogger().info( "configure path : "+configPath );
			Class<?> c = Class.forName( "org.apache.log4j.PropertyConfigurator" );
			Class<?> params[] = { Properties.class };
			Method m = c.getDeclaredMethod( "configure" , params );
			Object[] values = { PropsIO.loadFromStream( this.getConfigContext().resolveStream( configPath ) ) };
			m.invoke( null , values );
		} catch (Exception e) {
			throw ( new ConfigException( e ) );
		}
		this.getLogger().info( "configure [OK]" );
	}

}
