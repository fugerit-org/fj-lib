package test.org.fugerit.java.core.db.helpers;

import static org.junit.jupiter.api.Assertions.fail;

import java.sql.ResultSet;
import java.sql.Statement;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestMemDBTestBase extends MemDBTestBase {

	public TestMemDBTestBase() throws Exception {
		super();
	}

	protected static final Logger logger = LoggerFactory.getLogger(MemDBTestBase.class);

	public void execute( Statement stm, String sql ) throws Exception {
		logger.info( "Execute : "+sql+" START!" );
		boolean res = false;
		String check = sql.trim().toUpperCase();
		if ( check.startsWith( "INSERT" ) || 
				check.startsWith( "DELETE" ) ||
				check.startsWith( "UPDATE" ) ) {
			res = ( stm.executeUpdate(sql) > 0 );
		} else if ( check.startsWith( "SELECT" ) ) {
			try ( ResultSet rs = stm.executeQuery( sql ) ) {
				int recordCount = 0;
				while ( rs.next() ) {
					recordCount++;
				}
				logger.info( "record count : "+recordCount );
			}
		} else {
			res = stm.execute(sql);
		}
		logger.info( "Execute : "+sql+" RES:"+res );
	}
	
	@Test
	public void simpleTest01() {
		try ( Statement stm = this.getConnection().createStatement() ) {
			execute(stm, "CREATE SCHEMA TEST");
			execute(stm, "CREATE TABLE TEST.TEST_01 ( id bigint NOT NULL )");
			execute(stm, "INSERT INTO TEST.TEST_01 (id) VALUES ( 1 )");
			execute(stm, "INSERT INTO TEST.TEST_01 (id) VALUES ( 2 )");
			execute(stm, "SELECT * FROM TEST.TEST_01");
			execute(stm, "SELECT * FROM fugerit.user");
		} catch (Exception e) {
			String message = "Error "+e;
			logger.error( message, e );
			fail( message );
		}
	}

}
