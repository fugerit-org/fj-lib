package test.org.fugerit.java.core.db.connect;

import java.io.InputStream;
import java.sql.Connection;
import java.util.Properties;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.fugerit.java.core.cfg.ConfigRuntimeException;
import org.fugerit.java.core.db.connect.CfConfig;
import org.fugerit.java.core.db.connect.ConnectionFacade;
import org.fugerit.java.core.db.connect.ConnectionFacadeWrapper;
import org.fugerit.java.core.db.connect.ConnectionFactory;
import org.fugerit.java.core.db.connect.ConnectionFactoryCloseable;
import org.fugerit.java.core.db.connect.ConnectionFactoryImpl;
import org.fugerit.java.core.db.connect.DbcpConnectionFactory;
import org.fugerit.java.core.db.connect.SingleConnectionFactory;
import org.fugerit.java.core.db.dao.DAOException;
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
		log.info( "cf -> {}", wrapped );
		try ( ConnectionFactoryCloseable cf = ConnectionFactoryImpl.wrap( wrapped );
				Connection conn = cf.getConnection() ) {
			log.info( "db info : {}", cf.getDataBaseInfo() );
			log.info( "db info second time : {}", cf.getDataBaseInfo() );
			log.info( "driver info : {}", ConnectionFactoryImpl.getDriverInfo( cf ) );
		}
		ok = (wrapped != null);
		return ok;
	}
	
	@Test
	public void testCFProps() throws Exception {
		String name = "wrapped";
		Properties props= PropsIO.loadFromClassLoaderSafe( BasicDBHelper.DEFAULT_DB_CONN_PATH );
		props.setProperty( ConnectionFactoryImpl.PROP_CF_EXT_POOLED_IC , "1" );
		props.setProperty( ConnectionFactoryImpl.PROP_CF_EXT_POOLED_SC , "1" );
		props.setProperty( ConnectionFactoryImpl.PROP_CF_EXT_POOLED_MC , "3" );
		ConnectionFactory wrapped = ConnectionFactoryImpl.newInstance( props );
		ConnectionFacade.registerFactory( name , wrapped );
		boolean ok = this.worker( ConnectionFacade.getFactory( name ) );
		Assert.assertTrue(ok);
		// pooled
		ok = this.worker( new DbcpConnectionFactory( props ) );
		Assert.assertTrue(ok);
	}
	
	@Test
	public void testCFPropsPrefix() throws Exception {
		boolean ok = this.worker(
				ConnectionFactoryImpl.newInstance( 
						PropsIO.loadFromClassLoaderSafe( "core/db/base-db-conn.properties" ),
						"prefixtest", TestConnectionFactory.class.getClassLoader() ) );
		Assert.assertTrue(ok);
	}
		
	private boolean loadXmlHelper( String path ) throws Exception {
		boolean ok = false;
		try ( InputStream is = ClassHelper.loadFromDefaultClassLoader( path ) ) {
			Document doc = DOMIO.loadDOMDoc( is );
			CfConfig cfConfig = ConnectionFactoryImpl.parseCfConfig( doc.getDocumentElement() );
			ok = this.worker( cfConfig.getCfMap().get( "mem-db" ) );
			Assert.assertTrue(ok);
			ok = this.worker( cfConfig.getCfMap().get( "mem-db-pooled" ) );
			Assert.assertTrue(ok);
		}
		return ok;
	}
	
	@Test
	public void testCFXml() throws Exception {
		boolean ok = this.loadXmlHelper( "core/db/connct-properties-mem.xml" );
		Assert.assertTrue(ok);
	}

	@Test
	public void testCFXmlFail1() throws Exception {
		Assert.assertThrows( ConfigRuntimeException.class , () -> this.loadXmlHelper( "core/db/connct-properties-mem-fail1.xml" ) );
	}

	@Test
	public void testCFXmlFail2() throws Exception {
		Assert.assertThrows( ConfigRuntimeException.class , () -> this.loadXmlHelper( "core/db/connct-properties-mem-fail2.xml" ) );
	}
	

	@Test
	public void testCFUnsupportedMode() throws Exception {
		Properties props = new Properties();
		props.setProperty( ConnectionFactoryImpl.PROP_CF_MODE , "mode-not-exists" );
		Assert.assertThrows( DAOException.class , () ->ConnectionFactoryImpl.newInstance(props) );
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
	public void testCFJndi1() throws Exception {
		Properties props = new Properties();
		props.setProperty( ConnectionFactoryImpl.PROP_CF_MODE , ConnectionFactoryImpl.PROP_CF_MODE_DS );
		props.setProperty( ConnectionFactoryImpl.PROP_CF_MODE_DS_NAME , JNDI_DS_NAME );
		boolean ok = this.worker( ConnectionFactoryImpl.newInstance( props ) );
		Assert.assertTrue(ok);
	}
	
	@Test
	public void testCFJndi2() throws Exception {
		Properties props = new Properties();
		props.setProperty( ConnectionFactoryImpl.PROP_CF_MODE , ConnectionFactoryImpl.PROP_CF_MODE_DS2 );
		props.setProperty( ConnectionFactoryImpl.PROP_CF_MODE_DS_NAME , JNDI_DS_NAME );
		boolean ok = this.worker( ConnectionFactoryImpl.newInstance( props ) );
		Assert.assertTrue(ok);
	}

	@Test
	public void testCFDirect1() throws Exception {
		boolean ok = this.worker( ConnectionFactoryImpl.newInstance( "org.hsqldb.jdbcDriver", "jdbc:hsqldb:mem:base_db_direct1", "testuser", "testp" ) );
		Assert.assertTrue(ok);
	}
	

	@Test
	public void testCFDirect2() throws Exception {
		boolean ok = this.worker( ConnectionFactoryImpl.newInstance( "org.hsqldb.jdbcDriver", "jdbc:hsqldb:mem:base_db_direct2", "testuser", "testp", TestConnectionFactory.class.getClassLoader() ) );
		Assert.assertTrue(ok);
	}
	
	@Test
	public void testCFDirect3() throws Exception {
		boolean ok = this.worker( ConnectionFactoryImpl.newInstance( new org.hsqldb.jdbcDriver(), "jdbc:hsqldb:mem:base_db_direct3", "testuser", "testp" ) );
		Assert.assertTrue(ok);
	}
	
}
