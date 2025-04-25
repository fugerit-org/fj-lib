package test.org.fugerit.java.core.db.dao.daogen;

import org.fugerit.java.core.cfg.ConfigRuntimeException;
import org.fugerit.java.core.db.dao.FieldList;
import org.fugerit.java.core.db.daogen.DeleteHelper;
import org.fugerit.java.core.db.daogen.InsertHelper;
import org.fugerit.java.core.db.daogen.QueryHelper;
import org.fugerit.java.core.db.daogen.SelectHelper;
import org.fugerit.java.core.db.daogen.UpdateHelper;
import org.fugerit.java.core.db.helpers.DAOID;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TestQueryHelper {

	private static final String TABLE_NAME = "fugert.user";
	
	@Test
	public void testQueryHelper() {
		QueryHelper helper = new QueryHelper( TABLE_NAME , new FieldList() );
		helper.openPara();
		helper.appendWithSpace( "1=1" );
		helper.closePara();
		log.info( "query : {}", helper.getQueryContent() );
		Assertions.assertNotNull( helper.getQueryContent() );
	}
	
	@Test
	public void testUpdateHelper() {
		UpdateHelper helper = new UpdateHelper( TABLE_NAME , new FieldList() );
		Assertions.assertThrows( ConfigRuntimeException.class , helper::getQueryContent );
		helper.addSetParam( "username" , "user1" );
		helper.addSetParam( "id" , DAOID.valueOf( 1 ) );
		helper.addSetParam( "field" , null );
		helper.andWhereParam( "username" , "user1" );
		helper.andWhereParam( "id" , DAOID.valueOf( 1 ) );
		log.info( "query : {}", helper.getQueryContent() );
		Assertions.assertNotNull( helper.getQueryContent() );
	}
	
	@Test
	public void testDeleteHelper() {
		DeleteHelper helper = new DeleteHelper( TABLE_NAME , new FieldList() );
		Assertions.assertThrows( ConfigRuntimeException.class , helper::getQueryContent );
		helper.andWhereParam( "username" , "user1" );
		helper.andWhereParam( "id" , DAOID.valueOf( 1 ) );
		log.info( "query : {}", helper.getQueryContent() );
		Assertions.assertNotNull( helper.getQueryContent() );
	}
	
	@Test
	public void testInsertHelper() {
		InsertHelper helper = new InsertHelper( TABLE_NAME , new FieldList() );
		helper.addParam( "username" , "user1" );
		helper.addParam( "id" , DAOID.valueOf( 1 ) );
		helper.addParam( "age", null );		// not added as it is null
		log.info( "query : {}", helper.getQueryContent() );
		Assertions.assertNotNull( helper.getQueryContent() );
	}
	
	@Test
	public void testSelectHelper1() {
		SelectHelper helper = new SelectHelper( TABLE_NAME , new FieldList() );
		helper.addNullComparison( "username", SelectHelper.COMPARE_EQUAL, false );
		helper.addNullComparison( "id", SelectHelper.COMPARE_EQUAL, true );
		helper.orEqualParam( "field1" , "1" );
		helper.orLikeParam( "field2" , "1" );
		helper.andLikeParam( "field3" , "1" );
		helper.addOrderBy( "username", SelectHelper.ORDER_ASC );
		Assertions.assertNotNull( helper.getQueryContent() );
	}
	
	@Test
	public void testSelectHelper2() {
		SelectHelper helper = new SelectHelper( TABLE_NAME , new FieldList() );
		helper.addOrderBy( "field4" );
		Assertions.assertNotNull( helper.getQueryContent() );
	}
	
	@Test
	public void testSelectHelper3() {
		SelectHelper helper =  SelectHelper.newCustomSelectHelper(TABLE_NAME, new FieldList(), false, "" );
		helper.addOrderBy( null );
		helper.addOrderBy( "f1", SelectHelper.ORDER_ASC );
		helper.addOrderBy( "f2", SelectHelper.ORDER_DESC );
		Assertions.assertFalse( helper.addParam( "username", null, null, null, null ) );
		Assertions.assertNotNull( helper.getQueryContent() );
	}
	
}
