package org.fugerit.java.core.cfg.store;

import java.util.Set;

public interface ConfigStoreMap {

    Set<String> getKeySet();

    String getValue( String key );

    String getValue( String key, String def );

}
