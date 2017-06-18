/*
 *
		Fugerit Java Library is distributed under the terms of :

                                 Apache License
                           Version 2.0, January 2004
                        http://www.apache.org/licenses/


	Full license :
		http://www.apache.org/licenses/LICENSE-2.0
		
	Project site: 
		http://www.fugerit.org/java/
	
	SCM site :
		https://github.com/fugerit79/fj-lib
	
 *
 */
package org.fugerit.java.core.db.metadata;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import org.fugerit.java.core.db.connect.ConnectionFactory;
import org.fugerit.java.core.db.connect.ConnectionFactoryImpl;
import org.fugerit.java.core.log.BasicLogObject;
import org.fugerit.java.core.log.LogFacade;

/**
 * 
 *
 * @author Fugerit
 *
 */
public class MetaDataUtils {

	public static void main( String[] args ) {
		try {
			ConnectionFactory cf = ConnectionFactoryImpl.newInstance( "org.postgresql.Driver", "jdbc:postgresql://localhost/jlibweb", "opinf", "opinf" );
			createModel( cf );
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static String insertQueryBuilder( TableModel tableModel ) {
		List<ColumnModel> columnList = tableModel.getColumnList();
		StringBuffer insertSQL = new StringBuffer();
		StringBuffer tmpBuffer = new StringBuffer();
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
	
	public static DataBaseModel createModel( ConnectionFactory cf ) throws Exception {
		return createModel( cf , null, null);
	}
	
	public static DataBaseModel createModel( ConnectionFactory cf, String catalog, String schema ) throws Exception {
		return createModel( cf , catalog, schema, (JdbcAdaptor) new DefaulJdbcdaptor( cf ) );	 
	}
	
	private static final int MODE_LOOSE = 1;
	
	private static final int MODE_STRICT = 2;
	
	private static DataBaseModel createModel( ConnectionFactory cf, String catalog, String schema, JdbcAdaptor jdbcAdaptor ) throws Exception { 
		
		DataBaseModel dataBaseModel = new DataBaseModel();

		LogFacade.getLog().debug( "DataBaseModel.DataBaseModel() catalog : "+catalog ); 
		
		LogFacade.getLog().debug( "DataBaseModel.DataBaseModel() schema  : "+schema );
		
		Connection conn = cf.getConnection();
		DatabaseMetaData dbmd = conn.getMetaData();
		
		dataBaseModel.setDatabaseProductName( dbmd.getDatabaseProductName() );
		dataBaseModel.setDatabaseProductVersion( dbmd.getDatabaseProductVersion() );
		dataBaseModel.setDriverName( dbmd.getDriverName() );
		dataBaseModel.setDriverVersion( dbmd.getDriverVersion() );		
		
		int mode = MODE_STRICT;
		
		String driverInfo = dataBaseModel.getDatabaseProductName().toLowerCase();
		if ( driverInfo.indexOf( "oracle" ) != -1 ) {
			LogFacade.getLog().info( "setting adaptor for oracle" );
			jdbcAdaptor = new OracleJdbcAdaptor( cf );
		} else if ( driverInfo.indexOf( "postgres" ) != -1 ) {
			LogFacade.getLog().info( "setting adaptor for postgres" );
		} else if ( driverInfo.indexOf( "mysql" ) != -1 ) {
			LogFacade.getLog().info( "setting adaptor for mysql" );
			jdbcAdaptor = new MysqlJdbcAdatapor( cf );
		} else if ( driverInfo.indexOf( "access" ) != -1 ) {
			LogFacade.getLog().info( "setting adaptor for access" );
			mode = MODE_LOOSE;
		}
		
		// estrazione tabelle
		String[] types = { "TABLE" };
		ResultSet tableRS = dbmd.getTables( catalog, schema, null, types );
		
		while ( tableRS.next() ) {
			TableModel tableModel = new TableModel();
			TableId tableId = new TableId();
			tableId.setTableCatalog( tableRS.getString( "TABLE_CAT" ) );
			tableId.setTableName( tableRS.getString( "TABLE_NAME" ) );
			tableId.setTableSchema( tableRS.getString( "TABLE_SCHEM" ) );
			LogFacade.getLog().debug( "DataBaseModel.DataBaseModel() tableId  : "+tableId );
			tableModel.setTableId( tableId );
			LogFacade.getLog().debug( "TABLE EX : "+tableId);
			String tableComment = tableRS.getString( "REMARKS" );
			if ( tableComment == null || tableComment.length() == 0 ) {
				tableComment = jdbcAdaptor.getTableComment( tableId );
			}
			tableModel.setComment( tableComment );
			
			ResultSet columnsRS = dbmd.getColumns( tableModel.getCatalog(), tableModel.getSchema(), tableModel.getName(), null );
			while ( columnsRS.next() ) {
				ColumnModel columnModel = new ColumnModel();
				columnModel.setName( columnsRS.getString( "COLUMN_NAME" ) );
				columnModel.setTypeSql( columnsRS.getInt( "DATA_TYPE" ) );
				columnModel.setTypeName( columnsRS.getString( "TYPE_NAME" ) );
				//System.out.println( "columnModel : "+columnModel );
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
			columnsRS.close();
			
			if ( mode == MODE_STRICT ) {
				ResultSet pkRS = dbmd.getPrimaryKeys( tableModel.getCatalog(), tableModel.getSchema(), tableModel.getName() );
				IndexModel primaryKey = new IndexModel();
				while ( pkRS.next() ) {
					primaryKey.setName( pkRS.getString( "PK_NAME" ) );
					primaryKey.addColumn( (ColumnModel)tableModel.getColumnMap().get( pkRS.getString( "COLUMN_NAME" ) ) );
				}
				tableModel.setPrimaryKey( primaryKey );
				pkRS.close();
			} else {
				LogFacade.getLog().debug( "DataBaseModel createModel() : SKIPPING PRIMARY KEY" );
			}

			
			// estrazione indici
			ResultSet indexRS = dbmd.getIndexInfo( tableModel.getCatalog(), tableModel.getSchema(), tableModel.getName(), true, true );
			while ( indexRS.next() ) {
				String indexName = indexRS.getString( "INDEX_NAME" );
				IndexModel indexModel = (IndexModel)tableModel.getIndexMap().get( indexName );
				if ( indexModel==null ) {
					indexModel = new IndexModel();
					indexModel.setName( indexName );
					tableModel.addIndex( indexModel );
				}
				String columnName = indexRS.getString( "COLUMN_NAME" );
				if ( columnName != null ) {
					indexModel.addColumn( (ColumnModel)tableModel.getColumnMap().get( columnName ) );	
				}
			}
			indexRS.close();
			
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

			dataBaseModel.addTable( tableModel );
			
		}
		tableRS.close();
		
		conn.close();
		
		return dataBaseModel;
	}
	
}

interface JdbcAdaptor {
	
	public String getTableComment( TableId tableId ) throws Exception;
	
	public String getColumnComment( TableId tableId, String columnName ) throws Exception;
	
	public String getColumnExtraInfo( TableId tableId, String columnName ) throws Exception;
	
}

class DefaulJdbcdaptor extends BasicLogObject implements JdbcAdaptor {

	private ConnectionFactory connectionFactory;
	
	/*
	 * <p>Creats a new instance of AbstractCommentAdaptor.</p>
	 *
	 * @param connectionFactory
	 */
	public DefaulJdbcdaptor(ConnectionFactory connectionFactory) {
		super();
		this.connectionFactory = connectionFactory;
	}

	/* (non-Javadoc)
	 * @see org.opinf.jlib.mod.tools.db.meta.JdbcCommentAdaptor#getColumnComment(org.fugerit.java.core.db.metadata.TableId, java.lang.String)
	 */
	public String getColumnComment(TableId tableId, String columnName) throws Exception {
		return "";
	}

	/* (non-Javadoc)
	 * @see org.opinf.jlib.mod.tools.db.meta.JdbcCommentAdaptor#getTableComment(org.fugerit.java.core.db.metadata.TableId)
	 */
	public String getTableComment(TableId tableId) throws Exception {
		return "";
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

	public String getColumnExtraInfo(TableId tableId, String columnName) throws Exception {
		this.getLogger().debug( "getColumnExtraInfo tableId    : "+tableId );
		this.getLogger().debug( "getColumnExtraInfo columnName : "+columnName );
		return "";
	}
	
}

class MysqlJdbcAdatapor extends DefaulJdbcdaptor {

	public MysqlJdbcAdatapor(ConnectionFactory connectionFactory) {
		super(connectionFactory);
	}

	public String getColumnExtraInfo(TableId tableId, String columnName) throws Exception {
		String info = "";
		Connection conn = this.getConnectionFactory().getConnection();
		this.getLogger().debug( "getColumnExtraInfo tableId    : "+tableId );
		this.getLogger().debug( "getColumnExtraInfo columnName : "+columnName );
		try {
			String sql = "SELECT extra FROM information_schema.columns WHERE table_catalog IS NULL AND table_schema=? AND table_name=? AND column_name=?";
			PreparedStatement pstm = conn.prepareStatement( sql );
			pstm.setString( 1, tableId.getTableCatalog() );
			pstm.setString( 2, tableId.getTableName() );
			pstm.setString( 3, columnName );
			ResultSet rs = pstm.executeQuery();
			if ( rs.next() ) {
				info = rs.getString( "extra" );
				this.getLogger().debug( "getColumnExtraInfo extra : "+info );
			}
			rs.close();
			pstm.close();
		} catch (Exception e) {
			throw e;
		} finally {
			conn.close();
		}
		return info;
	}
	
}

class OracleJdbcAdaptor extends DefaulJdbcdaptor {

	/*
	 * <p>Creats a new instance of OracleCommentAdaptor.</p>
	 *
	 * @param connectionFactory
	 */
	public OracleJdbcAdaptor(ConnectionFactory connectionFactory) {
		super(connectionFactory);
	}

	/* (non-Javadoc)
	 * @see org.opinf.jlib.mod.tools.db.meta.AbstractCommentAdaptor#getColumnComment(org.fugerit.java.core.db.metadata.TableId, java.lang.String)
	 */
	public String getColumnComment(TableId tableId, String columnName) throws Exception {
		String comment = "";
		Connection conn = this.getConnectionFactory().getConnection();
		try {
			String sql = "SELECT comments FROM all_col_comments WHERE OWNER=? AND table_name=? AND column_name=?";
			PreparedStatement pstm = conn.prepareStatement( sql );
			pstm.setString( 1, tableId.getTableSchema() );
			pstm.setString( 2, tableId.getTableName() );
			pstm.setString( 3, columnName );
			ResultSet rs = pstm.executeQuery();
			if ( rs.next() ) {
				comment = rs.getString( "comments" );
			}
			rs.close();
			pstm.close();
		} catch (Exception e) {
			throw e;
		} finally {
			conn.close();
		}
		return comment;
	}

	/* (non-Javadoc)
	 * @see org.opinf.jlib.mod.tools.db.meta.AbstractCommentAdaptor#getTableComment(org.fugerit.java.core.db.metadata.TableId)
	 */
	public String getTableComment(TableId tableId) throws Exception {
		String comment = "";
		Connection conn = this.getConnectionFactory().getConnection();
		try {
			String sql = "SELECT comments FROM all_tab_comments WHERE OWNER=? AND table_name=?";
			PreparedStatement pstm = conn.prepareStatement( sql );
			pstm.setString( 1, tableId.getTableSchema() );
			pstm.setString( 2, tableId.getTableName() );
			ResultSet rs = pstm.executeQuery();
			if ( rs.next() ) {
				comment = rs.getString( "comments" );
			}
			rs.close();
			pstm.close();
		} catch (Exception e) {
			throw e;
		} finally {
			conn.close();
		} 
		return comment;
	}
	
	
	
}
