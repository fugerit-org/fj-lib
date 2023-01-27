package org.fugerit.java.core.cfg.provider;

import java.util.HashMap;
import java.util.Map;

import org.fugerit.java.core.lang.helpers.StringUtils;
import org.fugerit.java.core.util.ObjectUtils;

public class ConfigProviderFacade {

	private static ConfigProvider defaultProvider = new DefaultConfigProvider();
	
	private static final ConfigProviderFacade INSTANCE = new ConfigProviderFacade();
	
	public static ConfigProviderFacade getInstance() {
		return INSTANCE;
	}
	
	private Map<String, ConfigProvider> map;
	
	private ConfigProviderFacade() {
		this.map = new HashMap<>();
	}
	
	public ConfigProvider getDefaultProvider() {
		return defaultProvider;
	}
	
	public ConfigProvider getProviderByName( String name ) {
		return this.resolveProvider(name);
	}
	
	public ConfigProvider getProviderByCaller( Object caller ) {
		return this.resolveProvider( caller.getClass().getName() );
	}
	
	public ConfigProvider getProviderWithDefault( String name, Object caller ) {
		ConfigProvider configProvider = null;
		if ( StringUtils.isNotEmpty( name ) ) {
			configProvider = this.map.get( name );
		}
		if ( configProvider == null && caller != null ) {
			configProvider = this.map.get( caller.getClass().getName() );
		}
		return ObjectUtils.objectWithDefault( configProvider , this.getDefaultProvider() );
	}
	
	public void setDefaultProvider( ConfigProvider configProvider ) {
		defaultProvider = configProvider;
	}
	
	private ConfigProvider resolveProvider( String name ) {
		return ObjectUtils.objectWithDefault( map.get( name ) , defaultProvider );
	}
	
	public ConfigProvider registerByCaller( Object caller, ConfigProvider configProvider ) {
		String name = caller.getClass().getName();
		return this.registerByName(name, configProvider);
	}
	
	public ConfigProvider registerByName( String name, ConfigProvider configProvider ) {
		ConfigProvider previousProvider = map.get( name );
		this.map.put( name , configProvider );
		return previousProvider;
	}
	
}
