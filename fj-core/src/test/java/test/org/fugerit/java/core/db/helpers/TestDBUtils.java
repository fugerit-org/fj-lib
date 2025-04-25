package test.org.fugerit.java.core.db.helpers;

import lombok.extern.slf4j.Slf4j;
import org.fugerit.java.core.db.connect.ConnectionFactory;
import org.fugerit.java.core.db.connect.ConnectionFactoryImpl;
import org.fugerit.java.core.db.dao.DAOUtils;
import org.fugerit.java.core.db.dao.DAOUtilsNG;
import org.fugerit.java.core.db.dao.OpDAO;
import org.fugerit.java.core.db.dao.idgen.BasicSeqIdGenerator;
import org.fugerit.java.core.db.dao.idgen.IdGeneratorFacade;
import org.fugerit.java.core.db.helpers.DAOID;
import org.fugerit.java.core.db.helpers.DbUtils;
import org.fugerit.java.core.function.SafeFunction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import test.org.fugerit.java.BasicTest;

import java.sql.Connection;
import java.sql.DriverManager;

@Slf4j
public class TestDBUtils extends BasicTest {

	private int testWorker( ConnectionFactory cf ) {
		return SafeFunction.get( () -> {
			try ( Connection conn = cf.getConnection() ) {
				DAOUtilsNG.execute( conn, OpDAO.newExecuteOp( "CREATE SEQUENCE IF NOT EXISTS test_seq AS INTEGER START WITH 10" ) );
				int res = DbUtils.indentifyDB( conn );
				BasicSeqIdGenerator idGenerator = IdGeneratorFacade.sequenceGenerator( conn, "test_seq" );
				DAOID id = idGenerator.generateId( conn );
				log.info( "generated id : {}", id );
				Assertions.assertNotNull( id );
				return res;
			}
		} );
	}

	@Test
	void testH2() {
		SafeFunction.apply( () -> {
			ConnectionFactory cf = ConnectionFactoryImpl.newInstance( "org.h2.Driver",
					"jdbc:h2:mem:default", "user_h2", "pass_h2" );
			Assertions.assertEquals( DbUtils.DB_H2, this.testWorker( cf ) );
		} );
	}

	@Test
	void testHsqldb() {
		SafeFunction.apply( () -> {
			ConnectionFactory cf = ConnectionFactoryImpl.newInstance( "org.hsqldb.jdbc.JDBCDriver",
					"jdbc:hsqldb:mem:dbutils_test", "user_hsqldb", "pass_hsqldb" );
			Assertions.assertEquals( DbUtils.DB_HSQLDB, this.testWorker( cf ) );
		} );
	}

}
