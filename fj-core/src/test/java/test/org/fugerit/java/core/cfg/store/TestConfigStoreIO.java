package test.org.fugerit.java.core.cfg.store;

import java.util.Date;

import org.fugerit.java.core.cfg.ConfigRuntimeException;
import org.fugerit.java.core.cfg.store.helper.ConfigStoreDefault;
import org.fugerit.java.core.cfg.store.helper.ConfigStoreIO;
import org.fugerit.java.core.cfg.store.helper.ConfigStoreMapDefault;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class TestConfigStoreIO {

    private static final Logger logger = LoggerFactory.getLogger( TestConfigStoreIO.class );

    @Test
    public void test1() {
    	boolean ok = false;
        try {
            ConfigStoreIO configStoreIO = new ConfigStoreIO( "fj-core_test" );
            ConfigStoreDefault configStore = configStoreIO.loadConfig();
            ConfigStoreMapDefault map = new ConfigStoreMapDefault();
            map.add( "test-entry-01", "value "+new Date());
            configStore.addConfigStoreMap( "test-map", map );
            configStoreIO.saveConfig( configStore );
            logger.info( "teat ok" );
            ok = true;
        } catch (Exception e) {
            throw new ConfigRuntimeException( e );
        }
        Assertions.assertTrue( ok );
    }

}
