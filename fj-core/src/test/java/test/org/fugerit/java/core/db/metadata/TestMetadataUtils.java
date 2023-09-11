package test.org.fugerit.java.core.db.metadata;

import org.fugerit.java.core.db.connect.ConnectionFactory;
import org.fugerit.java.core.db.connect.SingleConnectionFactory;
import org.fugerit.java.core.db.metadata.ColumnModel;
import org.fugerit.java.core.db.metadata.DataBaseModel;
import org.fugerit.java.core.db.metadata.ForeignKeyModel;
import org.fugerit.java.core.db.metadata.IndexModel;
import org.fugerit.java.core.db.metadata.MetaDataUtils;
import org.fugerit.java.core.db.metadata.TableModel;
import org.fugerit.java.core.function.SafeFunction;
import org.junit.Assert;
import org.junit.Test;

import test.org.fugerit.java.core.db.helpers.MemDBTestBase;

public class TestMetadataUtils extends MemDBTestBase {
	
	@Test
	public void testDatabaseMetadata() {
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
			Assert.assertTrue( ok );
		} );
	}
}
