package test.org.fugerit.java.core.db.dao.daogen;

import org.fugerit.java.core.db.dao.FieldList;
import org.fugerit.java.core.db.daogen.BasicDAOHelper;
import org.fugerit.java.core.db.daogen.CloseableDAOContextSC;
import org.fugerit.java.core.db.daogen.UpdateHelper;
import org.fugerit.java.core.db.helpers.DAOID;
import org.fugerit.java.core.function.SafeFunction;
import org.junit.Assert;
import org.junit.Test;

import lombok.extern.slf4j.Slf4j;
import test.org.fugerit.java.core.db.TestBasicDBHelper;

@Slf4j
public class TestBasicDAOHelper extends TestBasicDBHelper {

	@Test
	public void testHelpersCreation() {
		SafeFunction.apply( () -> {
			try ( CloseableDAOContextSC context =  new CloseableDAOContextSC( newConnection() ) ) {
				BasicDAOHelper<String> helper = new BasicDAOHelper<String>( context );
				Assert.assertNotNull( helper.newDeleteHelper( "test1" ) );
				Assert.assertNotNull( helper.newInsertHelper( "test2" ) );
				Assert.assertNotNull( helper.newUpdateHelper( "test3" ) );
				Assert.assertNotNull( helper.newSelectHelper( "test4", null ) );
				Assert.assertNotNull( helper.newSelectHelper( "test5", "test6" ) );
				UpdateHelper updater = helper.newUpdateHelper( "fugerit.user" );
				updater.addSetParam( "username" , "test1" );
				updater.andWhereParam( "id" , DAOID.valueOf( -100 ) );
				int updateResult = helper.update( updater );
				Assert.assertEquals( 0 , updateResult );
				FieldList fl = new FieldList();
				log.info( "fieldListToString 0 {}", BasicDAOHelper.fieldListToString( fl ) );
				fl.addField( "1" );
				log.info( "fieldListToString 1 {}", BasicDAOHelper.fieldListToString( fl ) );
				fl.addField( "2" );
				log.info( "fieldListToString 2 {}", BasicDAOHelper.fieldListToString( fl ) );
				Assert.assertNotNull( helper.getLogger() );
			}
		} );
		
	}
	
}
