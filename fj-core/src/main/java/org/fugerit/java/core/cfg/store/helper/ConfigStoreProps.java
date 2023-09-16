package org.fugerit.java.core.cfg.store.helper;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Properties;
import java.util.Set;
import java.util.stream.Collectors;

import org.fugerit.java.core.cfg.ConfigRuntimeException;
import org.fugerit.java.core.cfg.store.ConfigStore;
import org.fugerit.java.core.cfg.store.ConfigStoreMap;
import org.fugerit.java.core.cfg.store.ConfigStoreUtils;
import org.fugerit.java.core.lang.helpers.StringUtils;
import org.fugerit.java.core.util.collection.SortedProperties;
import org.fugerit.java.core.util.collection.SubPropsUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConfigStoreProps extends ConfigStoreDefault {

	private static final Logger logger = LoggerFactory.getLogger( ConfigStoreProps.class );
	
	private static final String SEP = ".";
	
	private static final String SEP_NAMES = ",";

	private static final String KEY_ENTRY_NAMES = "_"+ConfigStoreProps.class.getSimpleName()+"_ENTRY_NAMES";
	
	private Properties configProps;

	private ConfigStoreProps(Properties configProps) {
		this.configProps = configProps;
		if ( configProps.containsKey( KEY_ENTRY_NAMES ) ) {
			String [] names = configProps.getProperty( KEY_ENTRY_NAMES ).split( SEP_NAMES );
			for ( int k=0; k<names.length; k++ ) {
				if ( StringUtils.isNotEmpty( names[k] ) ) {
					this.getConfigMap(names[k]);
				}
			}
		}
	}

	public Properties getConfigProps() {
		return configProps;
	}

	@Override
	public ConfigStoreMap getConfigMap(String name) {
		ConfigStoreMap map = super.getConfigMap(name);
		if (map == null) {
			Properties subProps = SubPropsUtils.subSortedProps(this.configProps, name + SEP, true);
			if (subProps != null && !subProps.isEmpty()) {
				map = ConfigStoreUtils.fromProperties(subProps);
			}
		}
		return map;
	}


	@Override
	public ConfigStoreMap remove(String name) {
		ConfigStoreMap res = super.remove(name);
		this.configProps.remove(name);
		return res;
	}

	private Set<String> getNameSet() {
		String names = this.configProps.getProperty( KEY_ENTRY_NAMES, "" );
		Set<String> res = Arrays.asList( names.split( SEP_NAMES ) ).stream()
				.filter( StringUtils::isNotEmpty )
				.collect( Collectors.toCollection( LinkedHashSet::new ) );
		logger.debug( "getNameSet() names '{}' -> {}", names, res );
		return res;
	}
	
	private void remove( String name, ConfigStoreMap res ) {
		for ( String key : res.getKeySet() ) {
			this.configProps.remove( name+SEP+key );
		}
	}
	
	private static void add( Properties configProps, String name, ConfigStoreMap res ) {
		for ( String key : res.getKeySet() ) {
			configProps.setProperty(name+SEP+key, res.getValue(key) );
		}
	}
	
	@Override
	public ConfigStoreMap addConfigStoreMap(String name, ConfigStoreMap map, boolean def) {
		ConfigStoreMap res = super.addConfigStoreMap(name, map, def);
		Set<String> nameSet = this.getNameSet();
		if ( res == null ) {
			nameSet.add( name );	
		} else {
			this.remove(name,map);
		}
		logger.debug( "addConfigStoreMap() name set -> {}", nameSet );
		this.configProps.setProperty( KEY_ENTRY_NAMES , StringUtils.concat( SEP_NAMES , nameSet ) );
		add(this.configProps, name, map);
		return res;
	}

	public static ConfigStoreDefault read(InputStream is) {
		SortedProperties props = new SortedProperties();
		try {
			props.load(is);
		} catch (IOException e) {
			throw new ConfigRuntimeException("Error loading properties : " + e, e);
		}
		return new ConfigStoreProps(props);
	}

	
	public static void write(ConfigStore store, OutputStream os) {
		String comment = ConfigStoreProps.class.getSimpleName() + " saved on " + new Timestamp(System.currentTimeMillis());
		try {
			if (store instanceof ConfigStoreProps) {
				// if it is an instance of ConfigStoreProps save the inner properties
				((ConfigStoreProps) store).configProps.store(os, comment);
			} else {
				// otherwise iterate over all the values and save them
				SortedProperties props = new SortedProperties();
				props.setProperty( KEY_ENTRY_NAMES , StringUtils.concat( SEP_NAMES, store.getConfigMapNames() ) );
				for ( String name : store.getConfigMapNames() ) {
					add( props, name, store.getConfigMap( name ) );
				}
			}
		} catch (IOException e) {
			throw new ConfigRuntimeException("Error storing properties : " + e, e);
		}
	}

}
