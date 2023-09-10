package org.fugerit.java.core.cfg.store.helper;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import org.fugerit.java.core.cfg.store.ConfigStoreMap;
import org.fugerit.java.core.lang.helpers.StringUtils;

public class ConfigStoreMapDefault implements ConfigStoreMap {

    private Map<String, String> config;

    public ConfigStoreMapDefault() {
        this.config = new LinkedHashMap<>();
    }

    @Override
    public Set<String> getKeySet() {
        return this.config.keySet();
    }

    @Override
    public String getValue(String key) {
        return this.config.get( key );
    }

    @Override
    public String getValue(String key, String def) {
        return StringUtils.valueWithDefault( this.getValue( key ), def );
    }

    public void add( String key, String value ) {
        this.config.put( key, value );
    }

    @Override
    public String toString() {
        return "ConfigStoreMapDefault{" +
                "config=" + config +
                '}';
    }

}
