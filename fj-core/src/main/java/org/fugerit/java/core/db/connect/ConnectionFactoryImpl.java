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
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import javax.naming.InitialContext;
import javax.sql.DataSource;

import org.fugerit.java.core.cfg.ConfigRuntimeException;
import org.fugerit.java.core.db.dao.DAOException;
import org.fugerit.java.core.db.metadata.DataBaseInfo;
import org.fugerit.java.core.function.UnsafeSupplier;
import org.fugerit.java.core.lang.helpers.BooleanUtils;
import org.fugerit.java.core.lang.helpers.StringUtils;
import org.fugerit.java.core.log.BasicLogObject;
import org.fugerit.java.core.util.PropsIO;
import org.fugerit.java.core.xml.dom.DOMUtils;
import org.fugerit.java.core.xml.dom.SearchDOM;
import org.w3c.dom.Element;

import lombok.extern.slf4j.Slf4j;


/**
 * <p>Implementing class for ConnectionFactory.</p>
 * 
 * @author Fugerit
 */
@Slf4j
public abstract class ConnectionFactoryImpl extends BasicLogObject implements ConnectionFactory {
	
	protected void init() throws DAOException {
		this.dataBaseInfo = new DataBaseInfo();
		this.dataBaseInfo.init( this );
	}
	
	private DataBaseInfo dataBaseInfo = null;
	
	/* (non-Javadoc)
	 * @see org.fugerit.java.core.db.connect.ConnectionFactory#getDataBaseInfo()
	 */
	@Override
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

	public static ConnectionFactoryCloseable wrap( ConnectionFactory cf ) {
		return new ConnectionFactoryCloseable() {
			@Override
			public void close() throws Exception {
				this.release();
			}
			@Override
			public void release() throws DAOException {
				cf.release();
			}
			@Override
			public DataBaseInfo getDataBaseInfo() throws DAOException {
				return cf.getDataBaseInfo();
			}
			@Override
			public Connection getConnection() throws DAOException {
				return cf.getConnection();
			}
		};
	}
	
	/**
	 * Parse a configuration Element looking for ConnectionFactory configuration
	 * 
	 * @param cfConfig			the Element
	 * @return					the CfConfig
	 * @throws DAOException		in case of issues
	 */
	public static CfConfig parseCfConfig( Element cfConfig ) throws DAOException {
		CfConfig config = new CfConfig();
		SearchDOM searchDOM = SearchDOM.newInstance( true , true );
		List<Element> cfConfigEntryList = searchDOM.findAllTags( cfConfig , "cf-config-entry" );
		Iterator<Element> cfConfigEntryIt = cfConfigEntryList.iterator();
		while ( cfConfigEntryIt.hasNext() ) {
			Element currentEntryTag = (Element) cfConfigEntryIt.next();
			Properties props = DOMUtils.attributesToProperties( currentEntryTag );
			String id = props.getProperty( "id" );
			if ( StringUtils.isEmpty( id ) ) {
				throw new ConfigRuntimeException( "Connection factory id must be defined." );
			} else if ( config.getCfMap().containsKey( id ) ) {
				throw new ConfigRuntimeException( "Connection factory id already used : '"+id+"'" );
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
	 * @throws DAOException	in case of issues
	 */
	public static String getDriverInfo( ConnectionFactory cf ) throws DAOException {
		return DAOException.get( () -> {
			try (Connection conn = cf.getConnection()) {
				DatabaseMetaData databaseMetaData = conn.getMetaData();
				return databaseMetaData.getDriverName()+" "+databaseMetaData.getDriverVersion();
			}
		});
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
		Properties prefixProps = props;
		if ( StringUtils.isNotEmpty( prefix ) ) {
			prefixProps = PropsIO.subProps( props , prefix+"-" );
			log.info( "subProps : {} -> {}", prefix, prefixProps );
		}
		String mode = prefixProps.getProperty( PROP_CF_MODE );
		log.info( "ConnectionFactory.newInstance() mode : {}", mode );
		if ( PROP_CF_MODE_DC.equalsIgnoreCase( mode ) ) {
			if ( BooleanUtils.isTrue( prefixProps.getProperty(PROP_CF_EXT_POOLED ) ) ) {
				cf = new DbcpConnectionFactory(prefixProps, cl);
			} else {
				cf = newInstance( prefixProps.getProperty( PROP_CF_MODE_DC_DRV ), 
						prefixProps.getProperty(PROP_CF_MODE_DC_URL ),
						prefixProps.getProperty(PROP_CF_MODE_DC_USR ),
						prefixProps.getProperty(PROP_CF_MODE_DC_PWD ), cl );
			}
		} else if ( PROP_CF_MODE_DS.equalsIgnoreCase( mode ) || PROP_CF_MODE_DS2.equalsIgnoreCase( mode ) ) {
			String dsName = prefixProps.getProperty( PROP_CF_MODE_DS_NAME );
			log.info( "dsName -> {}", dsName );
			cf = newInstance( dsName );
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
		Properties info = new Properties();
		info.setProperty( "user", usr );
		info.setProperty( "password", pwd );
		return new SupplierConnectionFactory( "directConnectionSupplier" , () -> drv.connect( url, info ) );
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
		log.info( "ConnectionFactoryImpl.newInstance() direct connection driver   : {}", drv );
		log.info( "ConnectionFactoryImpl.newInstance() direct connection url      : {}", url );
		log.info( "ConnectionFactoryImpl.newInstance() direct connection username : {}", usr );
		log.info( "ConnectionFactoryImpl.newInstance() direct connection password : ******" );
		return DAOException.get( () -> {
			Driver driver = null;
			if ( cl != null ) {
				driver = (Driver)cl.loadClass( drv ).getDeclaredConstructor().newInstance();
			} else {
				driver= (Driver)Class.forName( drv ).asSubclass( Driver.class ).getDeclaredConstructor().newInstance();
			}
			return newInstance(driver, url, usr, pwd);
		});
	}
	
	/**
	 * Creates a new ConnectionFactory Data Source instance
	 * 		
	 * @param dsName	the DataSource name
	 * @return			the ConnectionFactory
	 * @throws DAOException		in case of issues
	 */
	public static ConnectionFactoryImpl newInstance(String dsName) throws DAOException {
		log.info( "ConnectionFactoryImpl.newInstance() data source name : {}", dsName );
		return newInstance( DAOException.get( () -> (DataSource)new InitialContext().lookup( dsName ) ) );
	}

	/**
	 * Creates a new ConnectionFactory Data Source instance
	 * 		
	 * @param ds		the DataSource
	 * @return			the ConnectionFactory
	 * @throws DAOException		in case of issues
	 */
	public static ConnectionFactoryImpl newInstance(DataSource ds) throws DAOException {
		log.info( "ConnectionFactoryImpl.newInstance() data source : {}", ds );
		return new SupplierConnectionFactory( "dataSourceSupplier" , ds::getConnection );
	}	
	
	/*
	 * (non-Javadoc)
	 * @see org.fugerit.java.core.db.connect.ConnectionFactory#getConnection()
	 */
	@Override
	public abstract Connection getConnection() throws DAOException;

	/*
	 * (non-Javadoc)
	 * @see org.fugerit.java.core.db.connect.ConnectionFactory#release()
	 */
	@Override
	public void release() throws DAOException {
		
	}	
	
}

class SupplierConnectionFactory extends ConnectionFactoryImpl {
	
	private String description;
	
	private UnsafeSupplier<Connection, Exception> supplier;

	public SupplierConnectionFactory(String description, UnsafeSupplier<Connection, Exception> supplier) {
		super();
		this.description = description;
		this.supplier = supplier;
	}

	@Override
	public Connection getConnection() throws DAOException {
		return DAOException.get( supplier );
	}
	
	@Override
	public String toString() {
		return "SupplierConnectionFactory["+this.description+"]";
	}
	
}
