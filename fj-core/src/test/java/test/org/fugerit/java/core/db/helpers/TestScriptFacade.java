package test.org.fugerit.java.core.db.helpers;

import static org.junit.Assert.fail;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import org.fugerit.java.core.db.helpers.SQLScriptReader;
import org.fugerit.java.core.lang.helpers.ClassHelper;
import org.junit.Test;

import test.org.fugerit.java.MemDBTestBase;

public class TestScriptFacade extends MemDBTestBase {

	public TestScriptFacade() throws Exception {
		super();
	}

	@Test
	public void testReadScript() {
		try ( InputStream is = ClassHelper.loadFromDefaultClassLoader( DEFAULT_DB_INIT_PATH );
				SQLScriptReader reader = new SQLScriptReader( is ) ) {
			while ( reader.hasNext() ) {
				String script = reader.next();
				logger.info( "current -> "+script );
			}
		} catch (Exception e) {
			String message = "Error : "+e;
			logger.error( message, e );
			fail( message );
		}
	}
	
	@Test
	public void testSelect() {
		try ( Connection conn = this.getConnection();
				Statement stm = conn.createStatement();
				ResultSet rs = stm.executeQuery( "SELECT * FROM fugerit.users " ) ) {
			int read = 0;
			while ( rs.next() ) {
				read++;
			}
			logger.info( "total record read from table : "+read );
		} catch (Exception e) {
			String message = "Error : "+e;
			logger.error( message, e );
			e.printStackTrace();
			fail( message );
		}
	}
	
}
