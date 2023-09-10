package org.fugerit.java.core.cfg.store.helper;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import org.fugerit.java.core.cfg.ConfigRuntimeException;
import org.fugerit.java.core.cfg.store.ConfigStore;
import org.fugerit.java.core.cfg.store.ConfigStoreMap;
import org.fugerit.java.core.lang.helpers.StringUtils;

public class ConfigStoreDefault implements ConfigStore {

    private Map<String, ConfigStoreMap> configs;

    private String defaultName;

    public ConfigStoreDefault() {
        this.configs = new LinkedHashMap<>();
        this.defaultName = null;
    }

    public String getDefaultName() {
		return defaultName;
	}

	public void setDefaultName(String defaultName) {
		this.defaultName = defaultName;
	}

	public void addConfigStoreMap( String name, ConfigStoreMap map ) {
        this.addConfigStoreMap( name, map, false );
    }

    public ConfigStoreMap addConfigStoreMap( String name, ConfigStoreMap map, boolean def ) {
    	ConfigStoreMap res = this.configs.put( name, map );
        if ( def ) {
            this.defaultName = name;
        }
        return res;
    }

    public ConfigStoreMap remove( String name ) {
        return this.configs.remove( name );
    }

    @Override
    public ConfigStoreMap getDefaultConfigMap() {
        if (StringUtils.isEmpty( this.defaultName ) ) {
            throw new ConfigRuntimeException( "Default config was not set" );
        }
        return this.getConfigMap( this.defaultName );
    }

    @Override
    public boolean containsConfigMap(String name) {
        return this.configs.containsKey( name );
    }

    @Override
    public ConfigStoreMap getConfigMap(String name) {
        return this.configs.get( name );
    }

    @Override
    public ConfigStoreMap getConfigMap(Class<?> c) {
        return this.getConfigMap( c.getClass().getName() );
    }

    @Override
    public Set<String> getConfigMapNames() {
        return this.configs.keySet();
    }

}
