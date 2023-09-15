package test.org.fugerit.java.core.cfg;

import java.io.Closeable;

import org.fugerit.java.core.cfg.CloseHelper;
import org.fugerit.java.core.cfg.ConfigException;
import org.fugerit.java.core.cfg.ConfigRuntimeException;
import org.junit.Assert;
import org.junit.Test;

public class TestConfig {

	
	@Test
	public void testClose() throws ConfigException {
		// close config exception
		CloseHelper.close( (Closeable)null );
		CloseHelper.close( (AutoCloseable)null );
		CloseHelper.close( CloseableFactory.DO_NOTHING_CLOSEABLE );
		Assert.assertThrows( ConfigException.class, () -> CloseHelper.close( CloseableFactory.ERROR_CLOSEABLE ) );
		CloseHelper.close( CloseableFactory.wrap( CloseableFactory.DO_NOTHING_CLOSEABLE ) );
		Assert.assertThrows( ConfigException.class, () -> CloseHelper.close( CloseableFactory.wrap(  CloseableFactory.ERROR_CLOSEABLE ) ) );
		// close runtime exception
		CloseHelper.closeRuntimeEx( (Closeable)null );
		CloseHelper.closeRuntimeEx( (AutoCloseable)null );
		CloseHelper.closeRuntimeEx( CloseableFactory.DO_NOTHING_CLOSEABLE );
		Assert.assertThrows( ConfigRuntimeException.class, () -> CloseHelper.closeRuntimeEx( CloseableFactory.ERROR_CLOSEABLE ) );
		CloseHelper.closeRuntimeEx( CloseableFactory.wrap( CloseableFactory.DO_NOTHING_CLOSEABLE ) );
		Assert.assertThrows( ConfigRuntimeException.class, () -> CloseHelper.closeRuntimeEx( CloseableFactory.ERROR_AUTO_CLOSEABLE ) );
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
		Assert.assertNull( e1 );
		Exception e2 = CloseHelper.closeAll( CloseableFactory.wrap( CloseableFactory.DO_NOTHING_CLOSEABLE ), CloseableFactory.wrap( CloseableFactory.DO_NOTHING_CLOSEABLE ) );
		Assert.assertNull( e2 );
		Exception e3 = CloseHelper.closeAll( CloseableFactory.DO_NOTHING_CLOSEABLE, CloseableFactory.ERROR_CLOSEABLE );
		Assert.assertNotNull( e3 );
		Exception e4 = CloseHelper.closeAll( CloseableFactory.wrap( CloseableFactory.DO_NOTHING_CLOSEABLE ), CloseableFactory.wrap( CloseableFactory.ERROR_CLOSEABLE ) );
		Assert.assertNotNull( e4);
		Assert.assertThrows( ConfigRuntimeException.class, () -> CloseHelper.closeAllAndThrowConfigRuntime( 
				 CloseableFactory.DO_NOTHING_CLOSEABLE, 
				 CloseableFactory.ERROR_CLOSEABLE ) );
	}
	
}
