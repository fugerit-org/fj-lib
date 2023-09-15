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
package org.fugerit.java.core.db.metadata;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.fugerit.java.core.db.connect.ConnectionFactory;
import org.fugerit.java.core.db.dao.DAOException;
import org.fugerit.java.core.log.BasicLogObject;
import org.fugerit.java.core.log.LogFacade;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 *
 * @author Fugerit
 *
 */
@Slf4j
public class MetaDataUtils {

	private MetaDataUtils() {}
	
	public static final String TYPE_TABLE = "TABLE";
	
	public static final String TYPE_VIEW = "VIEW";
	
	public static final String[] typesAll() {
		return Arrays.asList( TYPE_TABLE, TYPE_VIEW ).toArray( new String[0] );
	}
	
	public static final String[] typesDefault() {
		return Arrays.asList( TYPE_TABLE ).toArray( new String[0] );
	}
	
	public static final String[] typesView() {
		return Arrays.asList( TYPE_VIEW ).toArray( new String[0] );
	}
	
	public static final String[] typesTable() {
		return Arrays.asList( TYPE_TABLE ).toArray( new String[0] );
	}
	
	public static final List<String> tableListAll() {
		List<String> tableNameList = new ArrayList<String>();
		tableNameList.add( "*" );
		return tableNameList;
	}
	
	private static final String S_COLUMN_NAME = "COLUMN_NAME";
	
	public static String insertQueryBuilder( TableModel tableModel ) {
		List<ColumnModel> columnList = tableModel.getColumnList();
		StringBuilder insertSQL = new StringBuilder();
		StringBuilder tmpBuffer = new StringBuilder();
		insertSQL.append( "INSERT INTO "+tableModel.getName()+" ( "+((ColumnModel)columnList.get( 0 )).getName() );
		tmpBuffer.append( " VALUES ( ? " );
		for ( int k=1; k<columnList.size(); k++ ) {
			insertSQL.append( ", "+	((ColumnModel)columnList.get( k )).getName() );
			tmpBuffer.append( ", ? " );
		}
		insertSQL.append( ") " );
		tmpBuffer.append( ") " );
		insertSQL.append( tmpBuffer.toString() );		
		return insertSQL.toString();
	}
	
	public static DataBaseModel createModel( ConnectionFactory cf ) throws DAOException {
		return createModel( cf , null, null);
	}
	
	public static DataBaseModel createModel( ConnectionFactory cf, String[] types ) throws DAOException {
		return createModel( cf , null, null, types );
	}
	
	public static DataBaseModel createModel( ConnectionFactory cf, String catalog, String schema ) throws DAOException {
		return createModel(cf, catalog, schema, tableListAll());
	}
	
	public static DataBaseModel createModel( ConnectionFactory cf, String catalog, String schema, String[] types) throws DAOException {
		return createModel(cf, catalog, schema, tableListAll(),  types);
	}
	
	public static DataBaseModel createModel( ConnectionFactory cf, String catalog, String schema, List<String> tableNameList ) throws DAOException {
		return createModel( cf , catalog, schema, new DefaulJdbcdaptor( cf ), tableNameList, typesDefault() );	 
	}
	
	public static DataBaseModel createModel( ConnectionFactory cf, String catalog, String schema, List<String> tableNameList, String[] types ) throws DAOException {
		return createModel( cf , catalog, schema, new DefaulJdbcdaptor( cf ), tableNameList, types );	 
	}
	
	private static final int MODE_LOOSE = 1;
	
	private static final int MODE_STRICT = 2;
	
	private static String createDescribeQuery( TableId tableId ) {
		return "SELECT * FROM "+tableId.toIdString()+" WHERE 1=0";
	}
	
	public static JdbcAdaptor getDefaultAdaptorFor( String databaseProductName, ConnectionFactory cf ) {
		JdbcAdaptor jdbcAdaptor = null;
		String driverInfo = databaseProductName.toLowerCase();
		if ( driverInfo.indexOf( "oracle" ) != -1 ) {
			LogFacade.getLog().info( "setting adaptor for oracle for {}", driverInfo );
			jdbcAdaptor = new OracleJdbcAdaptor( cf );
		} else if ( driverInfo.indexOf( "mysql" ) != -1 ) {
			LogFacade.getLog().info( "setting adaptor for mysql for {}", driverInfo );
			jdbcAdaptor = new MysqlJdbcAdatapor( cf );
		} else {
			LogFacade.getLog().info( "setting adaptor default for {}", driverInfo );
			jdbcAdaptor = new DefaulJdbcdaptor( cf );
		}
		return jdbcAdaptor;
	}
	
	private static int handleDriverInfo( DataBaseModel dataBaseModel, MetaDataUtilsContext context ) {
		int mode = MODE_STRICT;
		String driverInfo = dataBaseModel.getDatabaseProductName().toLowerCase();
		context.setJdbcAdaptor( getDefaultAdaptorFor(driverInfo, context.getCf()) );
		if ( driverInfo.indexOf( "access" ) != -1 ) {
			mode = MODE_LOOSE;
		}
		return mode;
	}
	
	private static DataBaseModel createModel( ConnectionFactory cf, String catalog, String schema, JdbcAdaptor jdbcAdaptor, List<String> tableNameList, String[] types ) throws DAOException { 
		return createModel( new MetaDataUtilsContext(cf, catalog, schema, jdbcAdaptor, tableNameList, types) );
	}	
	
	private static void handleCurrentTableColumns( MetaDataUtilsContext context, TableModel tableModel, DatabaseMetaData dbmd, Connection conn ) throws SQLException, DAOException {
		JdbcAdaptor jdbcAdaptor = context.getJdbcAdaptor();
		TableId tableId = tableModel.getTableId();
		try ( ResultSet columnsRS = dbmd.getColumns( tableModel.getCatalog(), tableModel.getSchema(), tableModel.getName(), null ) ) {
			while ( columnsRS.next() ) {
				ColumnModel columnModel = new ColumnModel();
				columnModel.setName( columnsRS.getString( S_COLUMN_NAME ) );
				columnModel.setTypeSql( columnsRS.getInt( "DATA_TYPE" ) );
				columnModel.setTypeName( columnsRS.getString( "TYPE_NAME" ) );
				String isNullable = columnsRS.getString( "IS_NULLABLE" );
				if ( "NO".equalsIgnoreCase( isNullable ) ) {
					columnModel.setNullable( ColumnModel.NULLABLE_FALSE );	
				} else if ( "YES".equalsIgnoreCase( isNullable ) ) {
					columnModel.setNullable( ColumnModel.NULLABLE_TRUE );
				} else {
					columnModel.setNullable( ColumnModel.NULLABLE_UNKNOWN );
				}
				columnModel.setSize( columnsRS.getInt( "CHAR_OCTET_LENGTH" ) );
				String columnComment = columnsRS.getString( "REMARKS" );
				if ( columnComment == null || columnComment.length() == 0 ) {
					columnComment = jdbcAdaptor.getColumnComment( tableId, columnModel.getName() );
				}
				columnModel.setComment( columnComment );
				columnModel.setExtra( jdbcAdaptor.getColumnExtraInfo(  tableId, columnModel.getName() ) );
				tableModel.addColumn( columnModel );
			}
		}
		String sql = createDescribeQuery( tableId );
		try ( Statement stm = conn.createStatement(); 
				ResultSet rsJavaType = stm.executeQuery( sql )  ) {
			ResultSetMetaData rsmd = rsJavaType.getMetaData();
			for ( int k=0; k<rsmd.getColumnCount(); k++ ) {
				String colName = rsmd.getColumnName( k+1 );
				String javaName = rsmd.getColumnClassName( k+1 );
				ColumnModel columnModel = tableModel.getColumn( colName );
				columnModel.setJavaType( javaName );
			}
		} catch ( Exception e ) {
			LogFacade.getLog().info( "Error getting java type : "+e, e );
		}
	}

	private static void handleCurrentTableIndex( TableModel tableModel, DatabaseMetaData dbmd, int mode ) throws SQLException, DAOException{
		if ( mode == MODE_STRICT ) {
			try ( ResultSet pkRS = dbmd.getPrimaryKeys( tableModel.getCatalog(), tableModel.getSchema(), tableModel.getName() ) ) {
				IndexModel primaryKey = new IndexModel();
				while ( pkRS.next() ) {
					primaryKey.setName( pkRS.getString( "PK_NAME" ) );
					ColumnModel column = tableModel.getColumn( pkRS.getString( S_COLUMN_NAME ) );
					primaryKey.addColumn( column );
				}
				if ( !primaryKey.getColumnList().isEmpty() ) {
					tableModel.setPrimaryKey( primaryKey );		
				}
			}
		} else {
			LogFacade.getLog().debug( "DataBaseModel createModel() : SKIPPING PRIMARY KEY" );
		}

		// index extraction
		try ( ResultSet indexRS = dbmd.getIndexInfo( tableModel.getCatalog(), tableModel.getSchema(), tableModel.getName(), true, true ); ) {
			while ( indexRS.next() ) {
				String indexName = indexRS.getString( "INDEX_NAME" );
				IndexModel indexModel = (IndexModel)tableModel.getIndexMap().get( indexName );
				if ( indexModel==null ) {
					indexModel = new IndexModel();
					indexModel.setName( indexName );
					tableModel.addIndex( indexModel );
				}
				String columnName = indexRS.getString( S_COLUMN_NAME );
				if ( columnName != null ) {
					indexModel.addColumn( (ColumnModel)tableModel.getColumnMap().get( columnName ) );	
				}
			}
		}
	}
	
	private static void handleCurrentTableStrict( TableModel tableModel, DatabaseMetaData dbmd, int mode ) throws SQLException, DAOException {
		if ( mode == MODE_STRICT ) {
			// estrazione chiavi esterne
			ResultSet foreignRS = dbmd.getImportedKeys( tableModel.getCatalog(), tableModel.getSchema(), tableModel.getName() );
			while ( foreignRS.next() ) {
				String foreignName = foreignRS.getString( "FK_NAME" );
				ForeignKeyModel foreignKeyModel = (ForeignKeyModel)tableModel.getForeignKeyMap().get( foreignName );
				if ( foreignKeyModel == null ) {
					foreignKeyModel = new ForeignKeyModel();
					foreignKeyModel.setName( foreignName );
					TableId foreignTableId = new TableId();
					foreignTableId.setTableName( foreignRS.getString( "PKTABLE_NAME" ) );
					foreignTableId.setTableCatalog( foreignRS.getString( "PKTABLE_CAT" ) );
					foreignTableId.setTableSchema( foreignRS.getString( "PKTABLE_SCHEM" ) );
					foreignKeyModel.setForeignTableId( foreignTableId );
					tableModel.addForeignKey( foreignKeyModel );
				}
				String columnNamePk = foreignRS.getString( "PKCOLUMN_NAME" );
				String columnNameFk = foreignRS.getString( "FKCOLUMN_NAME" );
				foreignKeyModel.getColumnMap().setProperty( columnNamePk , columnNameFk );
			}
			foreignRS.close();
		} else {
			LogFacade.getLog().debug( "DataBaseModel createModel() : SKIPPING FOREIGN KEYS" );
		}
	}
		
	private static void handleCurrentTableWorker( MetaDataUtilsContext context, TableModel tableModel, ResultSet tableRS, DatabaseMetaData dbmd, int mode, Connection conn ) throws SQLException, DAOException {
		JdbcAdaptor jdbcAdaptor = context.getJdbcAdaptor();
		TableId tableId = tableModel.getTableId();
		
		log.debug( "TABLE EX : {}", tableId);
		String tableComment = tableRS.getString( "REMARKS" );
		if ( tableComment == null || tableComment.length() == 0 ) {
			tableComment = jdbcAdaptor.getTableComment( tableId );
		}
		tableModel.setComment( tableComment );
		handleCurrentTableColumns(context, tableModel, dbmd, conn);
		log.info( "Current table : {}", tableId );
		
		handleCurrentTableIndex( tableModel, dbmd, mode );
		
		handleCurrentTableStrict( tableModel, dbmd, mode );
	}
	
	private static void handleCurrentTable( MetaDataUtilsContext context, TableModel tableModel, ResultSet tableRS, DatabaseMetaData dbmd, int mode, Connection conn ) throws SQLException, DAOException {
		TableId tableId = new TableId();
		tableId.setTableCatalog( tableRS.getString( "TABLE_CAT" ) );
		tableId.setTableName( tableRS.getString( "TABLE_NAME" ) );
		tableId.setTableSchema( tableRS.getString( "TABLE_SCHEM" ) );
		boolean doTable = false;
		for ( String checkTableName : context.getTableNameList() ) {
			if ( checkTableName.endsWith( "*" ) ) {
				String check1 = checkTableName.substring( 0 , checkTableName.length()-1 ).toLowerCase();
				String check2 = tableId.getTableName().toLowerCase();
				if ( check2.toLowerCase().startsWith( check1 ) ) {
					doTable = true;	
				}
			} else if ( checkTableName.equalsIgnoreCase( tableId.getTableName() ) ) {
				doTable = true;
			}
		}
		LogFacade.getLog().info( "DataBaseModel.DataBaseModel() tableId  : {} doTable : {}", tableId, doTable );
		if ( doTable ) {
			tableModel.setTableId( tableId );
			handleCurrentTableWorker(context, tableModel, tableRS, dbmd, mode, conn);
		}
	}
	
	private static DataBaseModel createModel( MetaDataUtilsContext context ) throws DAOException { 
		return DAOException.get( () -> {
			DataBaseModel dataBaseModel = new DataBaseModel();
			try ( Connection conn = context.getCf().getConnection() ) {
				LogFacade.getLog().debug( "DataBaseModel.DataBaseModel() catalog : {}, schema : {}", context.getCatalog(), context.getSchema() ); 
				// set metadata
				DatabaseMetaData dbmd = conn.getMetaData();
				dataBaseModel.setDatabaseProductName( dbmd.getDatabaseProductName() );
				dataBaseModel.setDatabaseProductVersion( dbmd.getDatabaseProductVersion() );
				dataBaseModel.setDriverName( dbmd.getDriverName() );
				dataBaseModel.setDriverVersion( dbmd.getDriverVersion() );		
				// driver info setup
				int mode = handleDriverInfo(dataBaseModel, context);
				// table metadata
				try ( ResultSet tableRS = dbmd.getTables( context.getCatalog(), context.getSchema(), null, context.getTypes() ) ) {			
					while ( tableRS.next() ) {
						TableModel tableModel = new TableModel();
							handleCurrentTable(context, tableModel, tableRS, dbmd, mode, conn);
							dataBaseModel.addTable( tableModel );
					}
				}
			}
			return dataBaseModel;
		} );
	}
	
}

@ToString
class DefaulJdbcdaptor extends BasicLogObject implements JdbcAdaptor {

	private ConnectionFactory connectionFactory;
	
	private String descriptor;
	
	private String sqlTablesExtraInfo;
	
	private String sqlColumnsExtraInfo;
	
	public DefaulJdbcdaptor(ConnectionFactory connectionFactory, String descriptor, String sqlTablesExtraInfo, String sqlColumnsExtraInfo) {
		super();
		this.connectionFactory = connectionFactory;
		this.descriptor = descriptor;
		this.sqlTablesExtraInfo = sqlTablesExtraInfo;
		this.sqlColumnsExtraInfo = sqlColumnsExtraInfo;
	}

	/*
	 * <p>Creats a new instance of AbstractCommentAdaptor.</p>
	 *
	 * @param connectionFactory
	 */
	public DefaulJdbcdaptor(ConnectionFactory connectionFactory) {
		this( connectionFactory, null, null, null );
	}

	protected String getExtraInfoHelper( String sql, TableId tableId, String columnName) throws DAOException {
		return DAOException.get( () -> {
			String info = "";
			this.getLogger().debug( "getExtraInfoHelper tableId    : {}, columnName : {}", tableId, columnName );
			try ( PreparedStatement pstm = this.connectionFactory.getConnection().prepareStatement( sql ) ) {
				pstm.setString( 1, tableId.getTableSchema() );
				pstm.setString( 2, tableId.getTableName() );
				if ( columnName != null ) {
					pstm.setString( 3, columnName );	
				}
				try ( ResultSet rs = pstm.executeQuery() ) {
					if ( rs.next() ) {
						info = rs.getString( 1 );
						this.getLogger().debug( "getExtraInfoHelper {} extra : {}", this.descriptor, info );
					}	
				}
			}
			return info;
		} );
	}
	
	/* (non-Javadoc)
	 * @see org.opinf.jlib.mod.tools.db.meta.JdbcCommentAdaptor#getColumnComment(org.fugerit.java.core.db.metadata.TableId, java.lang.String)
	 */
	@Override
	public String getColumnComment(TableId tableId, String columnName) throws DAOException {
		return this.sqlColumnsExtraInfo == null ? "" : this.getExtraInfoHelper( this.sqlColumnsExtraInfo, tableId, columnName );
	}

	/* (non-Javadoc)
	 * @see org.opinf.jlib.mod.tools.db.meta.JdbcCommentAdaptor#getTableComment(org.fugerit.java.core.db.metadata.TableId)
	 */
	@Override
	public String getTableComment(TableId tableId) throws DAOException {
		return this.sqlTablesExtraInfo == null ? "" : this.getExtraInfoHelper( this.sqlTablesExtraInfo, tableId, null );
	}

	/*
	 * @return the connectionFactory
	 */
	public ConnectionFactory getConnectionFactory() {
		return connectionFactory;
	}

	/*
	 * @param connectionFactory the connectionFactory to set
	 */
	public void setConnectionFactory(ConnectionFactory connectionFactory) {
		this.connectionFactory = connectionFactory;
	}

	@Override
	public String getColumnExtraInfo(TableId tableId, String columnName) throws DAOException {
		this.getLogger().debug( "getColumnExtraInfo tableId    : {}, columnName : {}", tableId, columnName );
		return "";
	}
	
}

class MysqlJdbcAdatapor extends DefaulJdbcdaptor {

	public MysqlJdbcAdatapor(ConnectionFactory connectionFactory) {
		super(connectionFactory, MysqlJdbcAdatapor.class.getSimpleName(), null, 
				"SELECT extra FROM information_schema.columns WHERE table_catalog IS NULL AND table_schema=? AND table_name=? AND column_name=?" );
	}
	
}

class OracleJdbcAdaptor extends DefaulJdbcdaptor {

	/*
	 * <p>Creats a new instance of OracleCommentAdaptor.</p>
	 *
	 * @param connectionFactory
	 */
	public OracleJdbcAdaptor(ConnectionFactory connectionFactory) {
		super(connectionFactory, OracleJdbcAdaptor.class.getSimpleName(), 
				"SELECT comments FROM all_col_comments WHERE OWNER=? AND table_name=? AND column_name=?", 
				"SELECT comments FROM all_col_comments WHERE OWNER=? AND table_name=? AND column_name=?" );
	}

}

@AllArgsConstructor
class MetaDataUtilsContext {

	@Getter private ConnectionFactory cf;
	@Getter private String catalog;
	@Getter private String schema;
	@Getter @Setter private JdbcAdaptor jdbcAdaptor;
	@Getter private List<String> tableNameList;
	@Getter private String[] types; 
	
}
