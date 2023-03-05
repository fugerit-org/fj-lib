package org.fugerit.java.core.cfg.store;

import java.util.Set;

public interface ConfigStore {

    ConfigStoreMap getDefaultConfigMap();

    boolean containsConfigMap(String name);

    ConfigStoreMap getConfigMap(String name);

    ConfigStoreMap getConfigMap(Class<?> c);

    Set<String> getConfigMapNames();

}
