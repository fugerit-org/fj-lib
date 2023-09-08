package test.org.fugerit.java.core.db.connect;

import java.io.InputStream;
import java.sql.Connection;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.fugerit.java.core.db.connect.CfConfig;
import org.fugerit.java.core.db.connect.ConnectionFacadeWrapper;
import org.fugerit.java.core.db.connect.ConnectionFactory;
import org.fugerit.java.core.db.connect.ConnectionFactoryCloseable;
import org.fugerit.java.core.db.connect.ConnectionFactoryImpl;
import org.fugerit.java.core.db.connect.SingleConnectionFactory;
import org.fugerit.java.core.lang.helpers.ClassHelper;
import org.fugerit.java.core.util.PropsIO;
import org.fugerit.java.core.xml.dom.DOMIO;
import org.junit.Assert;
import org.junit.Test;
import org.w3c.dom.Document;

import lombok.extern.slf4j.Slf4j;
import test.org.fugerit.java.BasicTest;
import test.org.fugerit.java.core.db.BasicDBHelper;

@Slf4j
public class TestConnectionFactory extends BasicTest {

	private static final String JNDI_DS_NAME = "java:/comp/env/datasource/ds";
	
	private boolean worker( ConnectionFactory wrapped ) throws Exception {
		boolean ok = false;
		try ( ConnectionFactoryCloseable cf = ConnectionFactoryImpl.wrap( wrapped );
				Connection conn = cf.getConnection() ) {
			log.info( "db info : {}", cf.getDataBaseInfo() );
			log.info( "driver info : {}", ConnectionFactoryImpl.getDriverInfo( cf ) );
		}
		ok = (wrapped != null);
		return ok;
	}
	
	@Test
	public void testCFProps() throws Exception {
		ConnectionFactory wrapped = ConnectionFactoryImpl.newInstance( PropsIO.loadFromClassLoaderSafe( BasicDBHelper.DEFAULT_DB_CONN_PATH ) );
		boolean ok = this.worker(wrapped);
		Assert.assertTrue(ok);
	}
		
	
	@Test
	public void testCFXml() throws Exception {
		try ( InputStream is = ClassHelper.loadFromDefaultClassLoader( "core/db/connct-properties-mem.xml" ) ) {
			Document doc = DOMIO.loadDOMDoc( is );
			CfConfig cfConfig = ConnectionFactoryImpl.parseCfConfig( doc.getDocumentElement() );
			boolean ok = this.worker( cfConfig.getCfMap().get( "mem-db" ) );
			Assert.assertTrue(ok);
			ok = this.worker( cfConfig.getCfMap().get( "mem-db-pooled" ) );
			Assert.assertTrue(ok);
		}
	}
	
	private DataSource createDS() throws NamingException {
		InitialContext initialContext = new InitialContext();
		return (DataSource)initialContext.lookup( JNDI_DS_NAME );
	}
	
	@Test
	public void testCFDS() throws Exception {
		boolean ok = this.worker( new ConnectionFacadeWrapper( ConnectionFactoryImpl.newInstance( this.createDS() ) ) );
		Assert.assertTrue(ok);
	}
	
	@Test
	public void testCFSingle() throws Exception {
		DataSource ds = this.createDS();
		try ( Connection conn = ds.getConnection() ) {
			boolean ok = this.worker( new SingleConnectionFactory(conn) );
			Assert.assertTrue(ok);
		}
	}
	
	@Test
	public void testCFJndi() throws Exception {
		boolean ok = this.worker( ConnectionFactoryImpl.newInstance( JNDI_DS_NAME ) );
		Assert.assertTrue(ok);
	}
	
}
