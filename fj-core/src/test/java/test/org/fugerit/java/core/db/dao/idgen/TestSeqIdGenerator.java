package test.org.fugerit.java.core.db.dao.idgen;

import java.sql.Connection;
import java.sql.Statement;

import org.fugerit.java.core.db.connect.ConnectionFactory;
import org.fugerit.java.core.db.connect.SingleConnectionFactory;
import org.fugerit.java.core.db.dao.idgen.BasicSeqIdGenerator;
import org.fugerit.java.core.db.dao.idgen.OracleSeqIdGenerator;
import org.fugerit.java.core.db.dao.idgen.PostgresqlSeqIdGenerator;
import org.junit.Test;

import test.org.fugerit.java.core.db.helpers.MemDBTestBase;

public class TestSeqIdGenerator extends MemDBTestBase {
	
	protected void testSequenceWorker( String syntax, String sequenceName, BasicSeqIdGenerator gen ) {
		try {
			Connection conn = this.getConnection();
			try ( Statement stm = conn.createStatement() ) {
				stm.execute( "SET DATABASE SQL SYNTAX "+syntax+" TRUE" );
			}
			ConnectionFactory cf = new SingleConnectionFactory( conn );
			gen.setConnectionFactory( cf );
			gen.setSequenceName( sequenceName );
			logger.info( "new id : "+gen.generateId() );	
		} catch (Exception e) {
			this.failEx(e);
		}
	}
	
	@Test
	public void testPopstgresSequence() {
		testSequenceWorker( "PGS" , "fugerit.seq_test" , new PostgresqlSeqIdGenerator() );
	}
	
	@Test
	public void testOracleSequence() {
		testSequenceWorker( "ORA" , "fugerit.seq_test" , new OracleSeqIdGenerator() );
	}
	
//	@Test
//	public void testMySqlSequence() throws Exception {
//		testSequenceWorker( "MYS" , "seq_test" , new MysqlSeqIdGenerator() );
//	}
	
}
