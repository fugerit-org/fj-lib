package test.org.fugerit.java.core.cfg.store;

import org.fugerit.java.core.cfg.ConfigRuntimeException;
import org.fugerit.java.core.cfg.store.ConfigStore;
import org.fugerit.java.core.cfg.store.ConfigStoreMap;
import org.fugerit.java.core.cfg.store.helper.ConfigStoreDefault;
import org.fugerit.java.core.cfg.store.helper.ConfigStoreIO;
import org.fugerit.java.core.cfg.store.helper.ConfigStoreMapDefault;
import org.fugerit.java.core.cfg.store.helper.ConfigStoreProps;
import org.fugerit.java.core.lang.helpers.ClassHelper;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Date;

public class TestConfigStoreIO {

    private static final Logger logger = LoggerFactory.getLogger( TestConfigStoreIO.class );

    @Test
    public void test1() {
        try {
            ConfigStoreIO configStoreIO = new ConfigStoreIO( "fj-core_test" );
            ConfigStoreDefault configStore = configStoreIO.loadConfig();
            ConfigStoreMapDefault map = new ConfigStoreMapDefault();
            map.add( "test-entry-01", "value "+new Date());
            configStore.addConfigStoreMap( "test-map", map );
            configStoreIO.saveConfig( configStore );
        } catch (Exception e) {
            throw new ConfigRuntimeException( e );
        }
    }

}
