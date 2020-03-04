/*
 *
		Fugerit Java Library is distributed under the terms of :

                                 Apache License
                           Version 2.0, January 2004
                        http://www.apache.org/licenses/


	Full license :
		http://www.apache.org/licenses/LICENSE-2.0
		
	Project site: 
		https://www.fugerit.org/
	
	SCM site :
		https://github.com/fugerit79/fj-lib
	
 *
 */
package org.fugerit.java.core.db.connect;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.Driver;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.fugerit.java.core.db.dao.DAOException;
import org.fugerit.java.core.db.metadata.DataBaseInfo;
import org.fugerit.java.core.log.BasicLogObject;
import org.fugerit.java.core.log.LogFacade;
import org.fugerit.java.core.xml.dom.DOMUtils;
import org.fugerit.java.core.xml.dom.SearchDOM;
import org.w3c.dom.Element;


/**
 * <p>Implementing class for ConnectionFactory.</p>
 * 
 * @author Fugerit
 */
public abstract class ConnectionFactoryImpl extends BasicLogObject implements ConnectionFactory {
	
	protected void init() throws DAOException {
		this.dataBaseInfo = new DataBaseInfo();
		this.dataBaseInfo.init( this );
	}
	
	private DataBaseInfo dataBaseInfo = null;
	
	/* (non-Javadoc)
	 * @see org.fugerit.java.core.db.connect.ConnectionFactory#getDataBaseInfo()
	 */
	public DataBaseInfo getDataBaseInfo() throws DAOException {
		if ( this.dataBaseInfo == null ) {
			this.init();
		}
		return this.dataBaseInfo;
	}

	/**
	 * Paramter to set connection mode
	 * 
	 */
	public static final String PROP_CF_MODE = "db-cf-mode";
	
	/**
	 * Direct connection mode (DriverManager)
	 */
	public static final String PROP_CF_MODE_DC = "DC";
	
	/**
	 * Strict datasource mode
	 */
	public static final String PROP_CF_MODE_DS = "DS";
	
	/**
	 * Loose datasource mode
	 */
	public static final String PROP_CF_MODE_DS2 = "DS2";
	
	
	/**
	 * Name of the data source in connection mode DS and DS2
	 * 
	 */
	public static final String PROP_CF_MODE_DS_NAME = "db-mode-ds-name";
	
	/**
	 * Prefix to use when looking for other DC properties
	 * 
	 */
	public static final String PROP_CF_MODE_DC_PREFIX = "db-mode-dc-prefix";
	
	/**
	 * Property for URL in DC mode
	 * 
	 */
	public static final String PROP_CF_MODE_DC_URL = "db-mode-dc-url";

	/**
	 * Property for Driver in DC mode
	 * 
	 */
	public static final String PROP_CF_MODE_DC_DRV = "db-mode-dc-drv";
	
	/**
	 * Property for User in DC mode
	 * 
	 */
	public static final String PROP_CF_MODE_DC_USR = "db-mode-dc-usr";
	
	/**
	 * Property for Password in DC mode
	 * 
	 */
	public static final String PROP_CF_MODE_DC_PWD = "db-mode-dc-pwd";
	
	/**
	 * Property to create a pooled data source
	 * 
	 */
	public static final String PROP_CF_EXT_POOLED = "db-ext-pooled";

	/**
	 * Property to create a pooled data source (starting connections)
	 * 
	 */
	public static final String PROP_CF_EXT_POOLED_SC = "db-ext-pooled-sc";

	/**
	 * Property to create a pooled data source (idle connections)
	 * 
	 */
	public static final String PROP_CF_EXT_POOLED_IC = "db-ext-pooled-ic";
	
	/**
	 * Property to create a pooled data source (maximum connections)
	 * 
	 */
	public static final String PROP_CF_EXT_POOLED_MC = "db-ext-pooled-mc";

	/**
	 * Parse a configuration Element looking for ConnectionFactory configuration
	 * 
	 * @param cfConfig		the Element
	 * @return			the CfConfig
	 * @throws Exception	in case of issues
	 */
	public static CfConfig parseCfConfig( Element cfConfig ) throws Exception {
		CfConfig config = new CfConfig();
		SearchDOM searchDOM = SearchDOM.newInstance( true , true );
		List<Element> cfConfigEntryList = searchDOM.findAllTags( cfConfig , "cf-config-entry" );
		Iterator<Element> cfConfigEntryIt = cfConfigEntryList.iterator();
		while ( cfConfigEntryIt.hasNext() ) {
			Element currentEntryTag = (Element) cfConfigEntryIt.next();
			Properties props = DOMUtils.attributesToProperties( currentEntryTag );
			String id = props.getProperty( "id" );
			if ( id == null || id.trim().length() == 0 ) {
				throw new Exception( "Connection factory id must be defined." );
			} else if ( config.getCfMap().containsKey( id ) ) {
				throw new Exception( "Connection factory id already used : '"+id+"'" );
			} else {
				config.getCfMap().put( id , newInstance( props ) );
			}
 		}
		return config;
	}
	
	/**
	 * Return basic driver info for a ConnectionFactory
	 * 
	 * @param cf		the ConnectionFactory
	 * @return			a string describing the driver
	 * @throws Exception	in case of issues
	 */
	public static String getDriverInfo( ConnectionFactory cf ) throws Exception {
		String result = "";
		Connection conn = cf.getConnection();
		DatabaseMetaData databaseMetaData = conn.getMetaData();
		result = databaseMetaData.getDriverName()+" "+databaseMetaData.getDriverVersion();
		conn.close();
		return result;
	}
	
	/**
	 * Helper method to create a property with prefix
	 * 
	 * @param prefix	the prefix
	 * @param name		the property base name
	 * @return			the property full name ( prefix-name, or name if prefix == null)
	 */
	private static String getParamName( String prefix, String name ) {
		String res = name;
		if ( prefix != null && !prefix.equals( "" ) ) {
			res = prefix+"-"+name;
		}
		return res;
	}
	
	/**
	 * Creates a ConnectionFactory from a property object
	 * 
	 * @param props		the configuration properties
	 * @return			the ConnectionFactory
	 * @throws DAOException	in case of issues
	 */
	public static ConnectionFactory newInstance( Properties props ) throws DAOException {
		return newInstance( props, null, null );
	}
	
	/**
	 * Creates a ConnectionFactory from a property object
	 * 
	 * @param props		the configuration properties
	 * @param propsPrefix	prefix to use for properties
	 * @param cl		class loader to use
	 * @return			the ConnectionFactory
	 * @throws DAOException	in case of issues
	 */	
	public static ConnectionFactory newInstance( Properties props, String propsPrefix, ClassLoader cl ) throws DAOException {
		if ( cl == null ) {
			cl = Thread.currentThread().getContextClassLoader();
		}
		ConnectionFactory cf = null;
		String prefix = props.getProperty( PROP_CF_MODE_DC_PREFIX, propsPrefix );
		String mode = props.getProperty( getParamName( prefix, PROP_CF_MODE ) );
		LogFacade.getLog().info( "ConnectionFactory.newInstance() mode : "+mode );
		if ( PROP_CF_MODE_DC.equalsIgnoreCase( mode ) ) {
			if ( "true".equalsIgnoreCase( props.getProperty( getParamName( prefix, PROP_CF_EXT_POOLED ) ) ) ) {
				int sc = Integer.parseInt( props.getProperty( getParamName( prefix, PROP_CF_EXT_POOLED_SC ), "3" ) );
				int ic = Integer.parseInt( props.getProperty( getParamName( prefix, PROP_CF_EXT_POOLED_IC ), "10" ) );
				int mc = Integer.parseInt( props.getProperty( getParamName( prefix, PROP_CF_EXT_POOLED_MC ), "30" ) );
				cf = new DbcpConnectionFactory( props.getProperty( getParamName( prefix, PROP_CF_MODE_DC_DRV ) ), 
						props.getProperty( getParamName( prefix, PROP_CF_MODE_DC_URL ) ),
						props.getProperty( getParamName( prefix, PROP_CF_MODE_DC_USR ) ),
						props.getProperty( getParamName( prefix, PROP_CF_MODE_DC_PWD ) ), sc, ic, mc, cl );
			} else {
				cf = newInstance( props.getProperty( getParamName( prefix, PROP_CF_MODE_DC_DRV ) ), 
						props.getProperty( getParamName( prefix, PROP_CF_MODE_DC_URL ) ),
						props.getProperty( getParamName( prefix, PROP_CF_MODE_DC_USR ) ),
						props.getProperty( getParamName( prefix, PROP_CF_MODE_DC_PWD ) ), cl );
			}
		} else if ( PROP_CF_MODE_DS.equalsIgnoreCase( mode ) ) {
			cf = newInstance( props.getProperty( PROP_CF_MODE_DS_NAME ) );	
		} else if ( PROP_CF_MODE_DS2.equalsIgnoreCase( mode ) ) {
			String dsName = props.getProperty( PROP_CF_MODE_DS_NAME );
			try {
				javax.naming.InitialContext ctx = new javax.naming.InitialContext();
	            DataSource dataSource = ( DataSource ) ctx.lookup( dsName );
	            cf = newInstance( dataSource );
			} catch (Exception e) {
				throw ( new DAOException( e ) );
			}
		} else {
			throw ( new DAOException( "Unsupported factory mode ( valid values ar 'dc', 'ds', 'ds2' )" ) );
		}
		return cf;
	}	
	
	/**
	 * Creates a new ConnectionFactory Direct instance
	 * 
	 * @param drv	driver
	 * @param url	jdbc url
	 * @param usr	user
	 * @param pwd	password
	 * @return		the ConnectionFactory
	 * @throws DAOException	in case of issues
	 */
	public static ConnectionFactory newInstance(Driver drv, String url, String usr, String pwd) throws DAOException {
		return new DirectConnectionFactory( drv, url , usr, pwd );
	}	

	/**
	 * Creates a new ConnectionFactory Direct instance
	 * 
	 * @param drv	driver
	 * @param url	jdbc url
	 * @param usr	user
	 * @param pwd	password
	 * @return		the ConnectionFactory
	 * @throws DAOException	in case of issues
	 */
	public static ConnectionFactory newInstance(String drv, String url, String usr, String pwd) throws DAOException {
		return newInstance( drv, url, usr, pwd, null );
	}
	
	/**
	 * Creates a new ConnectionFactory Direct instance
	 * 
	 * @param drv	driver
	 * @param url	jdbc url
	 * @param usr	user
	 * @param pwd	password
	 * @param cl 	class loader to use
	 * @return		the ConnectionFactory
	 * @throws DAOException	in case of issues
	 */
	public static ConnectionFactory newInstance(String drv, String url, String usr, String pwd, ClassLoader cl) throws DAOException {
		ConnectionFactory connectionFactory = null;
		try {
			LogFacade.getLog().info( "ConnectionFactoryImpl.newInstance() direct connection driver   : "+drv );
			LogFacade.getLog().info( "ConnectionFactoryImpl.newInstance() direct connection url      : "+url );
			LogFacade.getLog().info( "ConnectionFactoryImpl.newInstance() direct connection username : "+usr );
			LogFacade.getLog().info( "ConnectionFactoryImpl.newInstance() direct connection password : ******" );
			Driver driver = null;
			if ( cl != null ) {
				driver = (Driver)cl.loadClass( drv ).newInstance();
			} else {
				driver= (Driver)Class.forName( drv ).newInstance();
			}
			connectionFactory = ( new DirectConnectionFactory( driver, url, usr, pwd ) );
		} catch (Exception e) {
			throw ( new DAOException( e ) );
		}
		return connectionFactory;
	}
	
	/**
	 * Creates a new ConnectionFactory Data Source instance
	 * 		
	 * @param dsName	the DataSource name
	 * @return			the ConnectionFactory
	 * @throws DAOException		in case of issues
	 */
	public static ConnectionFactoryImpl newInstance(String dsName) throws DAOException {
		LogFacade.getLog().info( "ConnectionFactoryImpl.newInstance() data source name : "+dsName );
		return (new DSConnectionFactory(dsName));
	}

	/**
	 * Creates a new ConnectionFactory Data Source instance
	 * 		
	 * @param ds		the DataSource
	 * @return			the ConnectionFactory
	 * @throws DAOException		in case of issues
	 */
	public static ConnectionFactoryImpl newInstance(DataSource ds) throws DAOException {
		LogFacade.getLog().info( "ConnectionFactoryImpl.newInstance() data source : "+ds );
		return (new DS2ConnectionFactory(ds));
	}	
	
	/*
	 * (non-Javadoc)
	 * @see org.fugerit.java.core.db.connect.ConnectionFactory#getConnection()
	 */
	public abstract Connection getConnection() throws DAOException;

	/*
	 * (non-Javadoc)
	 * @see org.fugerit.java.core.db.connect.ConnectionFactory#release()
	 */
	public void release() throws DAOException {
		
	}	
	
}

/**
 * ConnectionFactory implementations based on DriverManager
 * 
 * @author Fugerit
 *
 */
class DirectConnectionFactory extends ConnectionFactoryImpl {

	private String url; 
	private Driver driver;
	private Properties info;
	
	public DirectConnectionFactory( Driver drv, String url, String usr, String pwd ) {
		this.driver = drv;
		this.url = url;
		this.info = new Properties();
		this.info.setProperty( "user", usr );
		this.info.setProperty( "password", pwd );
	}
	
	public Connection getConnection() throws DAOException {
		Connection conn = null;
		try {
			conn = this.driver.connect( this.url, this.info );
		} catch (Exception e) {
			e.printStackTrace();
			throw ( new DAOException(e) );
		}

		return conn;
	}
	
}

/**
 * ConnectionFactory implementation based on a Data  Source
 * 
 * @author Fugerit
 *
 */
class DSConnectionFactory extends ConnectionFactoryImpl {
	
	public String toString() {
		return this.getClass().getName()+"[dsName:"+this.dsName+",source:"+this.source+"]";
	}	
	
	public Connection getConnection() throws DAOException {
		Connection conn = null;
		try {
			conn = this.source.getConnection();
		} catch (SQLException se) {
			throw (new DAOException("Cannot create connection", se));
		}
		return conn;
	}

	private String dsName;
	
	private DataSource source;	
	
	public DSConnectionFactory(String dsName) throws DAOException  {
		this.getLogger().info( "INIT START, dsName="+dsName );
		this.dsName = dsName;
		try {
			Context ctx = new InitialContext();
			source = (DataSource) ctx.lookup(dsName);
		} catch (NamingException ne) {
			throw (new DAOException("Cannot create ConnectionFactory", ne));
		} catch (Exception e) {
            throw (new DAOException("Fatal Error", e));
        }
		this.getLogger().info( "INIT END, source="+source );
	}	
	
}

/**
 * ConnectionFactory implementation based on a Data Source (v2)
 * 
 * @author Fugerit
 *
 */
class DS2ConnectionFactory extends ConnectionFactoryImpl {
	
	public String toString() {
		return this.getClass().getName()+"[source:"+this.source+"]";
	}	
	
	public Connection getConnection() throws DAOException {
		Connection conn = null;
		try {
			conn = this.source.getConnection();
		} catch (SQLException se) {
			throw (new DAOException("Cannot create Connection", se));
		}
		return conn;
	}
	
	private DataSource source;	
	
	public DS2ConnectionFactory(DataSource ds) throws DAOException  {
		this.source = ds;
	}	
	
}
