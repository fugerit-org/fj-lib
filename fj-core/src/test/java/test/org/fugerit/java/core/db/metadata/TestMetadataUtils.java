package test.org.fugerit.java.core.db.metadata;

import static org.junit.Assert.fail;

import org.fugerit.java.core.db.connect.ConnectionFactory;
import org.fugerit.java.core.db.connect.SingleConnectionFactory;
import org.fugerit.java.core.db.metadata.ColumnModel;
import org.fugerit.java.core.db.metadata.DataBaseModel;
import org.fugerit.java.core.db.metadata.ForeignKeyModel;
import org.fugerit.java.core.db.metadata.IndexModel;
import org.fugerit.java.core.db.metadata.MetaDataUtils;
import org.fugerit.java.core.db.metadata.TableModel;
import org.junit.Test;

import test.org.fugerit.java.MemDBTestBase;

public class TestMetadataUtils extends MemDBTestBase {

	public TestMetadataUtils() throws Exception {
		super();
	}
	
	@Test
	public void testDatabaseMetadata() {
		try {
			ConnectionFactory cf = new SingleConnectionFactory( this.getConnection() );
			DataBaseModel dbModel = MetaDataUtils.createModel( cf );
			for ( TableModel tableModel : dbModel.getTableList() ) {
				logger.info( "current table : "+tableModel+" ("+tableModel.getComment()+")" );
				logger.info( "primary key : "+tableModel.getPrimaryKey() );
				for ( ColumnModel columnModel : tableModel.getColumnList() ) {
					logger.info( "column : "+columnModel+" ("+columnModel.getComment()+")" );
				}
				for ( IndexModel indexModel : tableModel.getIndexList() ) {
					logger.info( "index : "+indexModel );
				}
				for ( ForeignKeyModel foreignKeyModel : tableModel.getForeignKeyList() ) {
					logger.info( "foreign key : "+foreignKeyModel );
				}
			}
		} catch (Exception e) {
			String message = "Error : "+e;
			logger.error( message, e );
			fail( message );
		}
	}
	
}
