package org.fugerit.java.core.web.servlet.config;

import java.util.Properties;


import org.fugerit.java.core.cfg.ConfigException;
import org.fugerit.java.core.db.connect.ConnectionFacade;
import org.fugerit.java.core.db.connect.ConnectionFactory;
import org.fugerit.java.core.db.connect.ConnectionFactoryImpl;

public class ConnectionFactoryConfig extends BasicConfig {

	/**
	 * 
	 */
	private static final long serialVersionUID = 808870888421246321L;

	@Override
	public void configure(Properties props) throws ConfigException {
		this.getLogger().info( "configure start" );
		String cfName = props.getProperty( "cf-name" );
		try {
			this.getLogger().info( "configure cf-name : "+cfName );
			ConnectionFactory cf = ConnectionFactoryImpl.newInstance( props );
			if ( "true".equalsIgnoreCase( props.getProperty( "cf-test" ) ) ) {
				this.getLogger().info( "configure testing connection..." );
				cf.getConnection().close();
				this.getLogger().info( "configure connection test [OK]" );
			} else {
				this.getLogger().info( "configure connection skipped" );
			}
			ConnectionFacade.registerFactory( cfName , cf );
		} catch (Exception e) {
			throw ( new ConfigException( e ) );
		}
		this.getLogger().info( "configure [OK]" );
	}

}
