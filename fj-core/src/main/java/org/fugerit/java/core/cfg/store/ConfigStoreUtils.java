package org.fugerit.java.core.cfg.store;

import java.util.Properties;

import org.fugerit.java.core.cfg.store.helper.ConfigStoreMapDefault;
import org.fugerit.java.core.util.collection.SortedProperties;

public class ConfigStoreUtils {

    public static ConfigStoreMap fromProperties( Properties props ) {
        ConfigStoreMapDefault map = new ConfigStoreMapDefault();
        for ( Object k : props.keySet() ) {
            String key = k.toString();
            map.add( key, props.getProperty( key ) );
        }
        return map;
    }
    public static Properties toProperties( ConfigStoreMap map ) {
        SortedProperties props = new SortedProperties();
        for ( String key : map.getKeySet() ) {
            props.setProperty( key, map.getValue( key ) );
        }
        return props;
    }

}
