package test.org.fugerit.java.core.cfg.store;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import org.fugerit.java.core.cfg.ConfigRuntimeException;
import org.fugerit.java.core.cfg.store.helper.ConfigStoreDefault;
import org.fugerit.java.core.cfg.store.helper.ConfigStoreMapDefault;
import org.fugerit.java.core.cfg.store.helper.ConfigStoreProps;
import org.fugerit.java.core.lang.helpers.ClassHelper;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestConfigStore {

    private static final Logger logger = LoggerFactory.getLogger( TestConfigStore.class );

    private static final String [] CONFIG_STORE_MAP_NAMES = {
    		"config.store.map.entry1",
    		"config.store.map.entry2",
    		"config.store.map.entry3"
    };
    
    private static final String ADD_NAME = "config.store.map.entry4";
    
    private static final ConfigStoreMapDefault ADD_MAP = new ConfigStoreMapDefault();
    static {
    	ADD_MAP.add( "prop1" , "Test 04 01" );
    	ADD_MAP.add( "prop2" , "Test 04 02" );
    }
    
    @Test
    public void test1() {
        try (InputStream is = ClassHelper.loadFromDefaultClassLoader( "core/util/collection/test_store_1.properties" );
        		FileOutputStream fos = new FileOutputStream( new File( "target/test_config_store_props.properties" ) )) {
            ConfigStoreDefault configStore = ConfigStoreProps.read( is );
            for ( int k=0; k<CONFIG_STORE_MAP_NAMES.length; k++ ) {
            	 logger.info( "test {} -> {}", k, configStore.getConfigMap( CONFIG_STORE_MAP_NAMES[k] ) );
            }
            configStore.addConfigStoreMap(ADD_NAME, ADD_MAP);
            ConfigStoreProps.write(configStore, fos);
        } catch (Exception e) {
            throw new ConfigRuntimeException( e );
        }
    }

}
