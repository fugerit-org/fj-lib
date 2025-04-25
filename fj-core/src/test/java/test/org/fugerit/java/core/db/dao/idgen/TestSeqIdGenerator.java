package test.org.fugerit.java.core.db.dao.idgen;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.Statement;
import java.util.Properties;

import org.fugerit.java.core.cfg.ConfigException;
import org.fugerit.java.core.db.connect.ConnectionFactory;
import org.fugerit.java.core.db.connect.SingleConnectionFactory;
import org.fugerit.java.core.db.dao.DAOException;
import org.fugerit.java.core.db.dao.idgen.BasicIdGenerator;
import org.fugerit.java.core.db.dao.idgen.BasicSeqIdGenerator;
import org.fugerit.java.core.db.dao.idgen.GenericSeqIdGenerator;
import org.fugerit.java.core.db.dao.idgen.IdGeneratorFacade;
import org.fugerit.java.core.db.dao.idgen.MysqlSeqIdGenerator;
import org.fugerit.java.core.db.dao.idgen.OracleSeqIdGenerator;
import org.fugerit.java.core.db.dao.idgen.PostgresqlSeqIdGenerator;
import org.fugerit.java.core.db.dao.idgen.SqlServerSeqIdGenerator;
import org.fugerit.java.core.db.daogen.CloseableDAOContext;
import org.fugerit.java.core.db.daogen.CloseableDAOContextSC;
import org.fugerit.java.core.db.helpers.DAOID;
import org.fugerit.java.core.db.helpers.DbUtils;
import org.fugerit.java.core.function.SafeFunction;
import org.fugerit.java.core.xml.XMLException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import test.org.fugerit.java.core.db.TestBasicDBHelper;
import test.org.fugerit.java.core.db.helpers.MemDBTestBase;
import test.org.fugerit.java.helpers.FailHelper;

public class TestSeqIdGenerator extends MemDBTestBase {

	private static final String SEQ_NAME = "fugerit.seq_test";
	
	private static Properties getConfiguration() {
		Properties props = new Properties();
		props.setProperty( BasicSeqIdGenerator.PROP_SEQ_NAME , SEQ_NAME );
		return props;
	}
	
	protected boolean testSequenceWorker( String syntax, String 
			sequenceName, BasicSeqIdGenerator gen, String prepareSql ) {
		return SafeFunction.get( () -> {
			try (Connection conn = this.getConnection();
					Statement stm = conn.createStatement() ) {
				String sqlSyntax = "SET DATABASE SQL SYNTAX "+syntax+" TRUE";
				stm.execute( sqlSyntax );
				if ( prepareSql != null ) {
					stm.execute( prepareSql );
				}
				ConnectionFactory cf = new SingleConnectionFactory( conn );
				gen.setConnectionFactory( cf );
				gen.setSequenceName( sequenceName );
				DAOID id = gen.generateId();
				logger.info( "new id : {}", id );
				return id != null;
			}
		} );
	}
	
	@Test
	void testIdentify() {
		String[] products = TestBasicDBHelper.PRODUCT_NAME_STRING;
		for ( int k=0; k<products.length; k++ ) {
			int code = DbUtils.indentifyDB( products[k] );
			Assertions.assertNotEquals( -1 , code );
		}
	}
	
	@Test
	void testPopstgresSequence() {
		PostgresqlSeqIdGenerator idGenerator = new PostgresqlSeqIdGenerator();
		idGenerator.setAutoCloseConnection( true );
		boolean ok = testSequenceWorker( "PGS" , SEQ_NAME , idGenerator, null );
		Assertions.assertTrue(ok);
	}
	
	@Test
	void testOracleSequence() {
		boolean ok = testSequenceWorker( "ORA" , SEQ_NAME , new OracleSeqIdGenerator(), null );
		Assertions.assertTrue(ok);
	}
	
	@Test
	void testMySqlSequence() {
		String prepareSql = "CREATE TABLE "+SEQ_NAME+" (id INT NOT NULL AUTO_INCREMENT, PRIMARY KEY (id))";
		boolean ok = testSequenceWorker( "MYS" , "fugerit.seq_test" , new MysqlSeqIdGenerator( "CALL IDENTITY()" ), prepareSql );
		Assertions.assertTrue(ok);
		Assertions.assertNotNull( new MysqlSeqIdGenerator() ); // test default constructor
	}
	
	@Test
	void testSqlServer() {
		SqlServerSeqIdGenerator idGenerator = new SqlServerSeqIdGenerator( s -> "SELECT nextval('"+s+"')" );
		boolean ok = testSequenceWorker( "MSS" , SEQ_NAME , idGenerator, null );
		SqlServerSeqIdGenerator.createSequenceQuery( SEQ_NAME );	// invocation test
		Assertions.assertTrue(ok);
	}
	
	@Test
	void testFacade1() {
		DAOID id = SafeFunction.get( () -> {
			try (Connection conn = this.getConnection();
					Statement stm = conn.createStatement() ) {
				String sqlSyntax = "SET DATABASE SQL SYNTAX PGS TRUE";
				stm.execute( sqlSyntax );
				BasicIdGenerator idGenerator = IdGeneratorFacade.sequenceGenerator(conn, SEQ_NAME);
				return idGenerator.generateId();
			}
		} );
		Assertions.assertNotNull( id );
	}
	
	@Test
	void testFacade2() {
		DAOID id = SafeFunction.get( () -> {
			try (Connection conn = this.getConnection();
					CloseableDAOContext context = new CloseableDAOContextSC( conn );
					Statement stm = conn.createStatement() ) {
				String sqlSyntax = "SET DATABASE SQL SYNTAX PGS TRUE";
				stm.execute( sqlSyntax );
				BasicIdGenerator idGenerator = IdGeneratorFacade.sequenceGenerator(context, SEQ_NAME);
				return idGenerator.generateId();
			}
		} );
		Assertions.assertNotNull( id );
	}
	
	@Test
	void testGeneric() {
		DAOID id = SafeFunction.get( () -> {
			try (Connection conn = this.getConnection();
					Statement stm = conn.createStatement() ) {
				String sqlSyntax = "SET DATABASE SQL SYNTAX PGS TRUE";
				stm.execute( sqlSyntax );
				GenericSeqIdGenerator idGenerator = new GenericSeqIdGenerator();
				Assertions.assertThrows( ConfigException.class , () -> idGenerator.configure( new Properties() ) );
				idGenerator.setConnectionFactory( new SingleConnectionFactory( conn ) );
				Assertions.assertThrows( ConfigException.class , () -> idGenerator.configure( new Properties() ) );
				idGenerator.configure( getConfiguration() );
				return idGenerator.generateId();
			}
		} );
		Assertions.assertNotNull( id );
	}
	
	@Test
	void testSqlServerFail() {
		SqlServerSeqIdGenerator idGenerator = new SqlServerSeqIdGenerator() ;
		idGenerator.setSequenceName(SEQ_NAME);
		Assertions.assertThrows( Exception.class , () -> testSequenceWorker( "MSS" , SEQ_NAME , idGenerator, null ) );
	}
	
	@Test
	void testBasic() throws ConfigException, IOException, XMLException {
		BasicIdGenerator idGenerator = new BasicIdGenerator() {	
			@Override
			public DAOID generateId(Connection conn) throws DAOException {
				if ( FailHelper.DO_FAIL ) {
					throw new UnsupportedOperationException( "for test" );
				}
				return null;
			}
		};
		try ( InputStream source = new ByteArrayInputStream( "".getBytes() )  ) {
			idGenerator.configureProperties(source);
		}
		try ( InputStream source = new ByteArrayInputStream( "<config/>".getBytes() )  ) {
			idGenerator.configureXML(source);
		}
		idGenerator.setAutoCloseConnection( true );
		Assertions.assertTrue(idGenerator.isAutoCloseConnection());
	}
	
	@Test
	void testBasicSeq() throws ConfigException, IOException, XMLException {
		BasicSeqIdGenerator idGenerator = new BasicSeqIdGenerator() {

			@Override
			protected String createSequenceQuery() {
				if ( FailHelper.DO_FAIL ) {
					throw new UnsupportedOperationException( "for test" );
				}
				return null;
			}
		};
		idGenerator.configure( getConfiguration() );
		idGenerator.setAutoCloseConnection( true );
		Assertions.assertTrue(idGenerator.isAutoCloseConnection());
	}
	
}
