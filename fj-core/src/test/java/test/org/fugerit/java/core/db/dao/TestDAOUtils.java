package test.org.fugerit.java.core.db.dao;

import java.sql.Connection;
import java.util.List;

import org.fugerit.java.core.db.connect.ConnectionFactoryCloseable;
import org.fugerit.java.core.db.connect.ConnectionFactoryImpl;
import org.fugerit.java.core.db.dao.BasicDAOFactory;
import org.fugerit.java.core.db.dao.DAOException;
import org.fugerit.java.core.db.dao.DAORuntimeException;
import org.fugerit.java.core.db.dao.DAOUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import test.org.fugerit.java.core.db.TestBasicDBHelper;

public class TestDAOUtils extends TestBasicDBHelper {

	private ConnectionFactoryCloseable connFactory() {
		return ConnectionFactoryImpl.wrap( new ConnectionFactoryImpl() {	
			@Override
			public Connection getConnection() throws DAOException {
				return newConnection();
			}
		});
	}
	

	private static final String DEF_USER = "user1";
	
	@Test
	public void testDAOUtilsNG() {
		DAORuntimeException.apply( () -> {
			try ( Connection conn = newConnection() ) {
				DAOUtils utils = new DAOUtils( new BasicDAOFactory( connFactory() ) );
				String user = utils.extractString( "SELECT username FROM fugerit.user WHERE username = ''user1''" );
				Assertions.assertEquals( DEF_USER, user );
				List<String> list = utils.extractStringList( "SELECT username FROM fugerit.user" );
				Assertions.assertFalse( list.isEmpty() );
				long idL = utils.extractLong( "SELECT id FROM fugerit.user WHERE username = ''user1''" );
				Assertions.assertNotEquals( 0L , idL );
				double idD = utils.extractDouble( "SELECT id FROM fugerit.user WHERE username = ''user1''" );
				Assertions.assertNotEquals( 0D , idD );
			}
		} );
	}
	
}
