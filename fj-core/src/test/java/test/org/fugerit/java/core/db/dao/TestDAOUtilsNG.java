package test.org.fugerit.java.core.db.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import org.fugerit.java.core.db.dao.DAORuntimeException;
import org.fugerit.java.core.db.dao.DAOUtilsNG;
import org.fugerit.java.core.db.dao.DefaultLoadResultNG;
import org.fugerit.java.core.db.dao.FieldList;
import org.fugerit.java.core.db.dao.LoadResultNG;
import org.fugerit.java.core.db.dao.OpDAO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import lombok.extern.slf4j.Slf4j;
import test.org.fugerit.java.core.db.TestBasicDBHelper;

@Slf4j
public class TestDAOUtilsNG extends TestBasicDBHelper {

	private static final String QUERY_DEF = "SELECT * FROM fugerit.user";
	
	private static final String QUERY_DEF_USERNAME = "SELECT * FROM fugerit.user WHERE username = ?";
	
	private static final String DEF_USER = "user1";

	@Test
	void testDefaultLoadResultNG() {
		DAORuntimeException.apply( () -> {
			List<ModelUser> list = new ArrayList<>();
			try ( Connection conn = newConnection();
					Statement stm = conn.createStatement(); 
					ResultSet rs = stm.executeQuery( QUERY_DEF );
					LoadResultNG<ModelUser> lr = DefaultLoadResultNG.newLoadResult(conn, ModelUser.RSE, stm, rs ) ) {
				DAOUtilsNG.fillList( lr, list );
				Assertions.assertFalse( list.isEmpty() );
			}
		} );
		DAORuntimeException.apply( () -> {
			List<ModelUser> list = new ArrayList<>();
			try ( Connection conn = newConnection();
					Statement stm = conn.createStatement(); 
					ResultSet rs = stm.executeQuery( QUERY_DEF );
					LoadResultNG<ModelUser> lr = DefaultLoadResultNG.newLoadResultCloseConnection(conn, OpDAO.newQueryOp( QUERY_DEF , ModelUser.RSE ) ) ) {
				DAOUtilsNG.fillList( lr, list );
				Assertions.assertFalse( list.isEmpty() );
			}
		} );
		DAORuntimeException.apply( () -> {
			List<ModelUser> list = new ArrayList<>();
			try ( Connection conn = newConnection();
					Statement stm = conn.createStatement(); 
					ResultSet rs = stm.executeQuery( QUERY_DEF );
					LoadResultNG<ModelUser> lr = DefaultLoadResultNG.newLoadResult(ModelUser.RSE, stm, rs ) ) {
				DAOUtilsNG.fillList( lr, list );
				Assertions.assertFalse( list.isEmpty() );
			}
		} );
		DAORuntimeException.apply( () -> {
			try ( Connection conn = newConnection();
					Statement stm = conn.createStatement(); 
					ResultSet rs = stm.executeQuery( QUERY_DEF );
					LoadResultNG<ModelUser> lr = DefaultLoadResultNG.newLoadResult(ModelUser.RSE, stm, rs ) ) {
				Iterator<ModelUser> it = DAOUtilsNG.toIterator( lr );
				Assertions.assertThrows( NoSuchElementException.class , it::next );
			}
		} );	
	}

	@Test
	void testDAOUtilsNGLoadMethods() {
		OpDAO<ModelUser> opDaoQueryAll = OpDAO.newQueryOp( QUERY_DEF , ModelUser.RSE );
		DAORuntimeException.apply( () -> {
			try ( Connection conn = newConnection() ) {
				List<ModelUser> list = DAOUtilsNG.loadList(conn, opDaoQueryAll);
				Assertions.assertFalse( list.isEmpty() );
			}
		} );
		DAORuntimeException.apply( () -> {
			try ( Connection conn = newConnection() ) {
				List<ModelUser> list = DAOUtilsNG.loadListFields(conn, opDaoQueryAll.getSql(), opDaoQueryAll.getRsExtractor(), opDaoQueryAll.getFieldList().toArray() );
				Assertions.assertFalse( list.isEmpty() );
			}
		} );
		DAORuntimeException.apply( () -> {
			try ( Connection conn = newConnection() ) {
				List<ModelUser> list = DAOUtilsNG.loadList(conn, opDaoQueryAll.getSql(), opDaoQueryAll.getRsExtractor(), new Object[0] );
				Assertions.assertFalse( list.isEmpty() );
			}
		} );
		DAORuntimeException.apply( () -> {
			try ( Connection conn = newConnection() ) {
				List<ModelUser> test = new ArrayList<>();
				DAOUtilsNG.loadStream(conn, opDaoQueryAll).forEach( test::add );
				Assertions.assertFalse( test.isEmpty() );
			}
		} );
		DAORuntimeException.apply( () -> {
			try ( Connection conn = newConnection() ) {
				List<ModelUser> test = new ArrayList<>();
				DAOUtilsNG.loadStreamFields(conn, opDaoQueryAll.getSql(), opDaoQueryAll.getRsExtractor(), opDaoQueryAll.getFieldList().toArray() ).forEach( test::add );
				Assertions.assertFalse( test.isEmpty() );
			}
		} );
		DAORuntimeException.apply( () -> {
			try ( Connection conn = newConnection() ) {
				List<ModelUser> test = new ArrayList<>();
				DAOUtilsNG.loadStream(conn, opDaoQueryAll.getSql(), opDaoQueryAll.getRsExtractor(), new Object[0] ).forEach( test::add );
				Assertions.assertFalse( test.isEmpty() );
			}
		} );
	}
	
	@Test
	void testDAOUtilsNG() {
		OpDAO<ModelUser> opDaoQueryAll = OpDAO.newQueryOp( QUERY_DEF , ModelUser.RSE );
		FieldList flUsername = new FieldList();
		flUsername.addField( DEF_USER );
		// load all
		DAORuntimeException.apply( () -> {
			try ( Connection conn = newConnection() ) {
				List<ModelUser> list = new ArrayList<>();
				DAOUtilsNG.extractAll( conn, list, opDaoQueryAll );
				Assertions.assertFalse( list.isEmpty() );
			}
		} );
		DAORuntimeException.apply( () -> {
			List<ModelUser> list = new ArrayList<>();
			try ( Connection conn = newConnection();
					LoadResultNG<ModelUser> lr = DAOUtilsNG.extractAll( conn, opDaoQueryAll ) ) {
				DAOUtilsNG.fillList( lr , list );
				log.info( "count : {}", lr.getCount() );
				Assertions.assertFalse( list.isEmpty() );
			}
		} );
		DAORuntimeException.apply( () -> {
			List<ModelUser> list = new ArrayList<>();
			try ( Connection conn = newConnection();
					LoadResultNG<ModelUser> lr = DAOUtilsNG.extraAllFields( conn, QUERY_DEF_USERNAME , ModelUser.RSE, DEF_USER ) ) {
				DAOUtilsNG.fillList( lr , list );
				log.info( "count : {}", lr.getCount() );
				Assertions.assertFalse( list.isEmpty() );
			}
		} );
		DAORuntimeException.apply( () -> {
			List<ModelUser> list = new ArrayList<>();
			try ( Connection conn = newConnection();
					LoadResultNG<ModelUser> lr = DAOUtilsNG.extraAll( conn, QUERY_DEF_USERNAME , ModelUser.RSE, flUsername.getField( 0 ) ) ) {
				DAOUtilsNG.fillList( lr , list );
				log.info( "count : {}", lr.getCount() );
				Assertions.assertFalse( list.isEmpty() );
			}
		} );
		DAORuntimeException.apply( () -> {
			try ( Connection conn = newConnection() ) {
				List<ModelUser> list = new ArrayList<>();
				DAOUtilsNG.extraAllFields( conn, list, QUERY_DEF_USERNAME , ModelUser.RSE, DEF_USER );
				Assertions.assertFalse( list.isEmpty() );
			}
		} );
		DAORuntimeException.apply( () -> {
			try ( Connection conn = newConnection() ) {
				List<ModelUser> list = new ArrayList<>();
				DAOUtilsNG.extraAll( conn, list, QUERY_DEF_USERNAME, ModelUser.RSE, flUsername.getField( 0 ) );
				Assertions.assertFalse( list.isEmpty() );
			}
		} );
		// select one
		DAORuntimeException.apply( () -> {
			try ( Connection conn = newConnection() ) {
				ModelUser model = DAOUtilsNG.extraOneFields( conn, QUERY_DEF_USERNAME, ModelUser.RSE, flUsername.getField( 0 ) );
				Assertions.assertNotNull( model );
			}
		} );
		DAORuntimeException.apply( () -> {
			try ( Connection conn = newConnection() ) {
				ModelUser model = DAOUtilsNG.extraOne( conn, QUERY_DEF_USERNAME, ModelUser.RSE, DEF_USER );
				Assertions.assertNotNull( model );
			}
		} );
		// create table
		DAORuntimeException.apply( () -> {
			OpDAO<ModelUser> op = OpDAO.newExecuteOp( "CREATE TABLE fugerit.test_dao_util_ng ( field_content VARCHAR(32) )" );
			try ( Connection conn = newConnection() ) {
				boolean res = DAOUtilsNG.execute(conn, op);
				Assertions.assertFalse( res );
			}
		} );
		// update
		DAORuntimeException.apply( () -> {
			OpDAO<ModelUser> op = OpDAO.newUpdateOp( "INSERT INTO fugerit.test_dao_util_ng VALUES ( 'test data' )" );
			try ( Connection conn = newConnection() ) {
				int res = DAOUtilsNG.update(conn, op);
				Assertions.assertEquals( 1 , res );
			}
		} );
		DAORuntimeException.apply( () -> {
			try ( Connection conn = newConnection() ) {
				int res = DAOUtilsNG.update(conn, "INSERT INTO fugerit.test_dao_util_ng VALUES ( ? )", DEF_USER);
				Assertions.assertEquals( 1 , res );
			}
		} );
		DAORuntimeException.apply( () -> {
			try ( Connection conn = newConnection() ) {
				int res = DAOUtilsNG.updateFields(conn, "INSERT INTO fugerit.test_dao_util_ng VALUES ( ? )", flUsername.getField( 0 ));
				Assertions.assertEquals( 1 , res );
			}
		} );
	}
	
}
