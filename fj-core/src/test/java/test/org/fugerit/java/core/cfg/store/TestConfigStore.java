package test.org.fugerit.java.core.cfg.store;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.fugerit.java.core.cfg.ConfigRuntimeException;
import org.fugerit.java.core.cfg.store.ConfigStoreMap;
import org.fugerit.java.core.cfg.store.ConfigStoreUtils;
import org.fugerit.java.core.cfg.store.helper.ConfigStoreDefault;
import org.fugerit.java.core.cfg.store.helper.ConfigStoreMapDefault;
import org.fugerit.java.core.cfg.store.helper.ConfigStoreProps;
import org.fugerit.java.core.lang.helpers.ClassHelper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lombok.extern.slf4j.Slf4j;
import test.org.fugerit.java.BasicTest;
import test.org.fugerit.java.core.testhelpers.FailInputStream;

@Slf4j
public class TestConfigStore extends BasicTest {

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
    	boolean ok = false;
        try (InputStream is = ClassHelper.loadFromDefaultClassLoader( "core/util/collection/test_store_1.properties" );
        		FileOutputStream fos = new FileOutputStream( new File( "target/test_config_store_props.properties" ) )) {
            ConfigStoreDefault configStore = ConfigStoreProps.read( is );
            for ( int k=0; k<CONFIG_STORE_MAP_NAMES.length; k++ ) {
            	 logger.info( "test {} -> {}", k, configStore.getConfigMap( CONFIG_STORE_MAP_NAMES[k] ) );
            }
            configStore.addConfigStoreMap(ADD_NAME, ADD_MAP);
            ConfigStoreProps.write(configStore, fos);
            ok = true;
        } catch (Exception e) {
            throw new ConfigRuntimeException( e );
        }
        Assertions.assertTrue( ok );
    }

	private static final String PATH_PROPS = "src/test/resources/core/util/collection/test_store_1.properties";
	
	@Test
	void testRead() {
		boolean ok = false;
		try ( InputStream is = new FileInputStream( new File( PATH_PROPS ) ) ) {
			ConfigStoreDefault configStore = ConfigStoreProps.read( is );
			log.info( "configStore : {}", configStore );
			ok = configStore != null;
		} catch (IOException e) {
			failEx(e);
		}
		Assertions.assertTrue( ok );
	}
	
	@Test
	void testReadKo() {
		boolean ok = false;
		try ( InputStream is = new FailInputStream() ) {
			ConfigStoreDefault configStore = ConfigStoreProps.read( is );
			log.info( "configStore : {}", configStore );
		} catch (ConfigRuntimeException e) {
			ok = true;
		} catch (IOException e) {
			failEx(e);
		}
		Assertions.assertTrue( ok );
	}
	
	@Test
	void testOperation() {
		boolean ok = false;
		try ( InputStream is = new FileInputStream( new File( PATH_PROPS ) ) ) {
			ConfigStoreDefault configStore = ConfigStoreProps.read( is );
			log.info( "configStore : {}", configStore );
			ok = configStore != null;
			if ( ok ) {
				ConfigStoreMap map = configStore.remove( "config.store.map.entry3" );
				log.info( "map removed : {}", map );
			}
		} catch (IOException e) {
			failEx(e);
		}
		Assertions.assertTrue( ok );
	}

	@Test
	void testGetConfigProps() {
		boolean ok = false;
		try ( InputStream is = new FileInputStream( new File( PATH_PROPS ) ) ) {
			ConfigStoreProps configStore = (ConfigStoreProps)ConfigStoreProps.read( is );
			ok = configStore.getConfigProps() != null;
		} catch (IOException e) {
			failEx(e);
		}
		Assertions.assertTrue( ok );
	}
	
	@Test
	void testToProperties() {
		Properties props = ConfigStoreUtils.toProperties( ADD_MAP );
		log.info( "toProperties : {}", props );
		Assertions.assertEquals( ADD_MAP.getKeySet().size() , props.keySet().size() );
	}
    
}

