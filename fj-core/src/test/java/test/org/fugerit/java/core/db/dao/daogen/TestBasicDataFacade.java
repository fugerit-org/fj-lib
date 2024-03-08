package test.org.fugerit.java.core.db.dao.daogen;

import lombok.extern.slf4j.Slf4j;
import org.fugerit.java.core.db.dao.DAOException;
import org.fugerit.java.core.db.dao.DAORuntimeException;
import org.fugerit.java.core.db.dao.FieldList;
import org.fugerit.java.core.db.daogen.*;
import org.fugerit.java.core.function.SafeFunction;
import org.junit.Assert;
import org.junit.Test;
import test.org.fugerit.java.BasicTest;
import test.org.fugerit.java.core.db.TestBasicDBHelper;
import test.org.fugerit.java.core.db.dao.ModelUser;

import java.io.Serializable;
import java.math.BigDecimal;

@Slf4j
public class TestBasicDataFacade extends TestBasicDBHelper implements Serializable {

	private static final long serialVersionUID = 2428066303144473612L;

	private static final BasicTest HELPER = new BasicTest();
	
	private static final String TABLE_NAME = "fugerit.user";
	
	@Test
	public void testBasicDataFacade1() {
		
		SafeFunction.apply( () -> {
			try ( CloseableDAOContextSC context =  new CloseableDAOContextSC( newConnection() ) ) {
				// basic features
				BasicDataFacade<ModelUser> facade = new BasicDataFacade<>( TABLE_NAME, ModelUser.RSE );
				Assert.assertNotNull(  HELPER.fullSerializationTest(facade) );
				Assert.assertNull( facade.getQueryView() );
				Assert.assertNotNull( facade.getRse() );
				Assert.assertNull( facade.getSequenceName() );
				Assert.assertNull( facade.generateId( context ) );
				Assert.assertEquals( TABLE_NAME , facade.getTableName() );	
				BasicDaoResult<ModelUser> resultAll = facade.loadAll(context);
				Assert.assertFalse( resultAll.getList().isEmpty() );
				facade.loadAllStream( context ).forEach( m -> log.info( "test basic dao result stream {}", m) );
				log.info( "test basic dao result first() {}", resultAll.getFirst() );
				Assert.assertThrows( DAORuntimeException.class , resultAll::getOne );
				BasicDaoResult<ModelUser> resultOne = new BasicDaoResult<>();
				Assert.assertFalse( resultOne.getFirst().isPresent() );
				Assert.assertFalse( resultOne.getOne().isPresent() );
				resultOne.getList().add( resultAll.getFirst().get() );
				Assert.assertTrue( resultOne.getFirst().isPresent() );
				Assert.assertTrue( resultOne.getOne().isPresent() );
			}
		} );
	}

	@Test
	public void testBasicDataFacade2() {
		SafeFunction.apply( () -> {
			try ( CloseableDAOContextSC context =  new CloseableDAOContextSC( newConnection() ) ) {
				// advance feature features
				ModeUserDataFacade facade = new ModeUserDataFacade();
				Assert.assertNotNull(  HELPER.fullSerializationTest(facade) );
				Assert.assertEquals( "SELECT * FROM "+TestBasicDataFacade.TABLE_NAME, facade.getQueryView() );
				Assert.assertNotNull( facade.getRse() );
				Assert.assertEquals( "fugerit.seq_test", facade.getSequenceName() );
				Assert.assertNotEquals( BigDecimal.valueOf( 0 ), facade.generateId( context ) );
				Assert.assertEquals( TABLE_NAME , facade.getTableName() );	
				BasicDaoResult<ModelUser> resultAll = facade.loadAll(context);
				Assert.assertFalse( resultAll.getList().isEmpty() );
				// evaluate
				facade.generateId(context);
				ModelUser userTest = resultAll.getList().get( 0 );
				BasicDaoResult<ModelUser> resultUpdate = new BasicDaoResult<>();
				facade.evaluteSqlUpdateResult( 1 , userTest, resultUpdate );	// typical update ok result
				facade.evaluteSqlUpdateResult( 1 , null, resultUpdate );
				facade.evaluteSqlUpdateResult( 0 , null, resultUpdate );		// typical update ko result
				// wrapper
				BasicWrapper<ModelUser> userWrapper = new BasicWrapper<ModelUser>(userTest);
				log.info( "test 1 : {}", userWrapper.unwrap() );
				log.info( "test 2 : {}", userWrapper.unwrapModel() );
				log.info( "test 3 : {}", HELPER.fullSerializationTest( userWrapper ) );
				log.info( "test 4 : {}", userWrapper );
				userWrapper.wrapModel( userTest );
				Assert.assertThrows( UnsupportedOperationException.class , () -> BasicWrapper.throwUnsupported( "message" ) );
				userWrapper.wrapModel( userTest );
				// test DataEntityUtils
				DataEntityUtils.unwrap( facade );
				Assert.assertThrows( DAOException.class ,  () -> DataEntityUtils.unwrap( userTest ) );
				QueryHelper helper = new QueryHelper( TABLE_NAME , new FieldList() );
				DataEntityUtils.addToQuery( facade , helper );
			}
		} );
	}
	
	class ModeUserDataFacade extends BasicDataFacade<ModelUser> implements DataEntityInfo {

		public ModeUserDataFacade() {
			super(TestBasicDataFacade.TABLE_NAME, ModelUser.RSE, "SELECT * FROM "+TestBasicDataFacade.TABLE_NAME);
		}

		private static final long serialVersionUID = -1838566691428422314L;

		@Override
		public String getSequenceName() {
			return "fugerit.seq_test";
		}
		
	}
	
}

