package test.org.fugerit.java.core.db.dao;

import java.io.Closeable;
import java.io.IOException;
import java.sql.Connection;

import org.fugerit.java.core.db.dao.CloseDAOHelper;
import org.fugerit.java.core.db.dao.DAORuntimeException;
import org.junit.Assert;
import org.junit.Test;

import test.org.fugerit.java.core.db.TestBasicDBHelper;

public class TestCloseDAOHelper extends TestBasicDBHelper {

	@Test
	public void testClose1() {
		DAORuntimeException.apply( () -> {
			boolean ok = false;
			try ( Connection conn = newConnection() ) {
				CloseDAOHelper.close( conn );
				ok = true;
			}	
			Assert.assertTrue(ok);
		} );
	}
	
	@Test
	public void testClose2() {
		DAORuntimeException.apply( () -> {
			boolean ok = false;
			try ( Connection conn = newConnection() ) {
				CloseDAOHelper.close( new Closeable() {	
					@Override
					public void close() throws IOException {
						// do nothing
					}
				} );
				ok = true;
				Assert.assertTrue(ok);
			}	
		} );
	}
	
	@Test
	public void testClose3() {
		DAORuntimeException.apply( () -> {
			boolean ok = false;
			CloseDAOHelper.close( (AutoCloseable)null );
			ok = true;
			Assert.assertTrue(ok);
		} );
	}
	
	@Test
	public void testClose4() {
		DAORuntimeException.apply( () -> {
			boolean ok = false;
			CloseDAOHelper.close( (Closeable)null );
			ok = true;
			Assert.assertTrue(ok);
		} );
	}
	
}
