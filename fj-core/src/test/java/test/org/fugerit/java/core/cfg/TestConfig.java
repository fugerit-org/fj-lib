package test.org.fugerit.java.core.cfg;

import java.io.Closeable;

import org.fugerit.java.core.cfg.CloseHelper;
import org.fugerit.java.core.cfg.ConfigException;
import org.fugerit.java.core.cfg.ConfigRuntimeException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class TestConfig {

	
	@Test
	void testClose() throws ConfigException {
		// close config exception
		CloseHelper.close( (Closeable)null );
		CloseHelper.close( (AutoCloseable)null );
		CloseHelper.close( CloseableFactory.DO_NOTHING_CLOSEABLE );
		Assertions.assertThrows( ConfigException.class, () -> CloseHelper.close( CloseableFactory.ERROR_CLOSEABLE ) );
		CloseHelper.close( CloseableFactory.wrap( CloseableFactory.DO_NOTHING_CLOSEABLE ) );
		Assertions.assertThrows( ConfigException.class, () -> CloseHelper.close( CloseableFactory.wrap(  CloseableFactory.ERROR_CLOSEABLE ) ) );
		// close runtime exception
		CloseHelper.closeRuntimeEx( (Closeable)null );
		CloseHelper.closeRuntimeEx( (AutoCloseable)null );
		CloseHelper.closeRuntimeEx( CloseableFactory.DO_NOTHING_CLOSEABLE );
		Assertions.assertThrows( ConfigRuntimeException.class, () -> CloseHelper.closeRuntimeEx( CloseableFactory.ERROR_CLOSEABLE ) );
		CloseHelper.closeRuntimeEx( CloseableFactory.wrap( CloseableFactory.DO_NOTHING_CLOSEABLE ) );
		Assertions.assertThrows( ConfigRuntimeException.class, () -> CloseHelper.closeRuntimeEx( CloseableFactory.ERROR_AUTO_CLOSEABLE ) );
		// close silent
		CloseHelper.closeSilent( (Closeable)null );
		CloseHelper.closeSilent( (AutoCloseable)null );
		CloseHelper.closeSilent( CloseableFactory.DO_NOTHING_CLOSEABLE );
		CloseHelper.closeSilent( CloseableFactory.ERROR_CLOSEABLE );
		CloseHelper.closeSilent( CloseableFactory.wrap( CloseableFactory.DO_NOTHING_CLOSEABLE ) );
		CloseHelper.closeSilent( CloseableFactory.wrap(  CloseableFactory.ERROR_CLOSEABLE ) );
		// close all
		CloseHelper.closeAll( CloseableFactory.DO_NOTHING_CLOSEABLE, null );
		CloseHelper.closeAll( CloseableFactory.wrap( CloseableFactory.DO_NOTHING_CLOSEABLE ), null );
		CloseHelper.closeAll();
		Exception e1 = CloseHelper.closeAll( CloseableFactory.DO_NOTHING_CLOSEABLE, CloseableFactory.DO_NOTHING_CLOSEABLE );
		Assertions.assertNull( e1 );
		Exception e2 = CloseHelper.closeAll( CloseableFactory.wrap( CloseableFactory.DO_NOTHING_CLOSEABLE ), CloseableFactory.wrap( CloseableFactory.DO_NOTHING_CLOSEABLE ) );
		Assertions.assertNull( e2 );
		Exception e3 = CloseHelper.closeAll( CloseableFactory.DO_NOTHING_CLOSEABLE, CloseableFactory.ERROR_CLOSEABLE );
		Assertions.assertNotNull( e3 );
		Exception e4 = CloseHelper.closeAll( CloseableFactory.wrap( CloseableFactory.DO_NOTHING_CLOSEABLE ), CloseableFactory.wrap( CloseableFactory.ERROR_CLOSEABLE ) );
		Assertions.assertNotNull( e4);
		Assertions.assertThrows( ConfigRuntimeException.class, () -> CloseHelper.closeAllAndThrowConfigRuntime( 
				 CloseableFactory.DO_NOTHING_CLOSEABLE, 
				 CloseableFactory.ERROR_CLOSEABLE ) );
	}
	
}
