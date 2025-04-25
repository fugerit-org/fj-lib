package test.org.fugerit.java.core.db.metadata;

import org.fugerit.java.core.db.connect.ConnectionFactory;
import org.fugerit.java.core.db.connect.ConnectionFactoryCloseable;
import org.fugerit.java.core.db.connect.ConnectionFactoryImpl;
import org.fugerit.java.core.db.connect.SingleConnectionFactory;
import org.fugerit.java.core.db.dao.DAOUtilsNG;
import org.fugerit.java.core.db.dao.OpDAO;
import org.fugerit.java.core.db.metadata.ColumnModel;
import org.fugerit.java.core.db.metadata.DataBaseModel;
import org.fugerit.java.core.db.metadata.ForeignKeyModel;
import org.fugerit.java.core.db.metadata.IndexModel;
import org.fugerit.java.core.db.metadata.JdbcAdaptor;
import org.fugerit.java.core.db.metadata.MetaDataUtils;
import org.fugerit.java.core.db.metadata.TableId;
import org.fugerit.java.core.db.metadata.TableModel;
import org.fugerit.java.core.function.SafeFunction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import test.org.fugerit.java.core.db.helpers.MemDBTestBase;

public class TestMetadataUtils extends MemDBTestBase {
	
	@Test
	void testDatabaseMetadata() {
		SafeFunction.apply( () -> {
			boolean ok = false;
			ConnectionFactory cf = new SingleConnectionFactory( this.getConnection() );
			DataBaseModel dbModel = MetaDataUtils.createModel( cf );
			for ( TableModel tableModel : dbModel.getTableList() ) {
				logger.info( "current table : "+tableModel+" ("+tableModel.getComment()+")" );
				logger.info( "tableId {}", tableModel.getTableId().getKey() );
				logger.info( "tableId eq {} - {}", tableModel.getTableId().hashCode(), tableModel.getTableId().equals( tableModel.getTableId() ) );
				logger.info( "primary key : "+tableModel.getPrimaryKey() );
				logger.info( "insert query : {}", MetaDataUtils.insertQueryBuilder( tableModel ) );
				for ( ColumnModel columnModel : tableModel.getColumnList() ) {
					logger.info( "column : "+columnModel+" ("+columnModel.getComment()+")" );
				}
				for ( IndexModel indexModel : tableModel.getIndexList() ) {
					logger.info( "index : {}", indexModel );
				}
				for ( ForeignKeyModel foreignKeyModel : tableModel.getForeignKeyList() ) {
					logger.info( "foreign key : {}, id : {}", foreignKeyModel , foreignKeyModel.getForeignTableId() );
					logger.info( "foreign column list : {}", foreignKeyModel.foreignColumnList( tableModel ) );
					logger.info( "internal column list : {}", foreignKeyModel.internalColumnList( tableModel ) );
				}
			}
			ok = true;
			Assertions.assertTrue( ok );
		} );
	}
	
	@Test
	void testDatabaseMetadataAlt() {
		SafeFunction.apply( () -> {
			try ( ConnectionFactoryCloseable cf =  ConnectionFactoryImpl.wrap( new SingleConnectionFactory( this.getConnection() ) ) ) {
				Assertions.assertNotNull( MetaDataUtils.createModel( cf, MetaDataUtils.typesAll() ) );
			}
		} );
	}
	
	@Test
	void testDatabaseMetadataConsts() {
		Assertions.assertEquals( MetaDataUtils.TYPE_TABLE , MetaDataUtils.typesAll()[0]);
		Assertions.assertEquals( MetaDataUtils.TYPE_VIEW , MetaDataUtils.typesAll()[1]);
		Assertions.assertEquals( MetaDataUtils.TYPE_TABLE , MetaDataUtils.typesTable()[0]);
		Assertions.assertEquals( MetaDataUtils.TYPE_TABLE , MetaDataUtils.typesDefault()[0]);
		Assertions.assertEquals( MetaDataUtils.TYPE_VIEW , MetaDataUtils.typesView()[0]);
	}
	
	@Test
	void testDatabaseMetadataAdatpor() {
		SafeFunction.apply( () -> {
			try ( ConnectionFactoryCloseable cf =  ConnectionFactoryImpl.wrap( new SingleConnectionFactory( this.getConnection() ) ) ) {
				
				JdbcAdaptor mysqlAdaptor = MetaDataUtils.getDefaultAdaptorFor( "mysql" , cf );
				logger.info( "mysqlAdaptor {}", mysqlAdaptor );
				JdbcAdaptor oracleAdaptor = MetaDataUtils.getDefaultAdaptorFor( "oracle" , cf );
				Assertions.assertNotNull( oracleAdaptor );
				// setup data
				DAOUtilsNG.execute( cf.getConnection() , OpDAO.newExecuteOp( 
						"CREATE TABLE all_col_comments ( owner VARCHAR(32), " +
						"table_name VARCHAR(32), column_name VARCHAR(32) NOT NULL,  comments VARCHAR(32) NOT NULL )") );
				DAOUtilsNG.update( cf.getConnection() , OpDAO.newUpdateOp( "INSERT INTO all_col_comments (owner, table_name, column_name, comments) VALUES ( 'fugerit', 'user', 'id', 'user id' )" ) );
				// test found
				TableId tableId = new TableId( "user", "fugerit", null );
				String comment1 = oracleAdaptor.getColumnComment( tableId, "id" );
				Assertions.assertEquals( "user id" , comment1 );
				String comment2 = oracleAdaptor.getColumnComment( tableId, "not exists" );
				Assertions.assertEquals( "" , comment2 );
				Assertions.assertNotNull( mysqlAdaptor );
				JdbcAdaptor adaptor = MetaDataUtils.getDefaultAdaptorFor( "postgres" , cf );
				logger.info( "jdbcAdatpror default {}", adaptor );
				Assertions.assertNotNull( adaptor );
				Assertions.assertEquals( "", adaptor.getColumnComment( null , null ) );
				Assertions.assertEquals( "", adaptor.getTableComment( null ) );
				
			}
		} );
		
		
	}
}
