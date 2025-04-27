package test.org.fugerit.java.core.db.dao.daogen;

import java.io.IOException;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.fugerit.java.core.db.dao.DAOException;
import org.fugerit.java.core.db.daogen.BasicHelper;
import org.fugerit.java.core.db.daogen.ByteArrayDataHandler;
import org.fugerit.java.core.db.daogen.CharArrayDataHandler;
import org.fugerit.java.core.db.daogen.CloseableDAOContext;
import org.fugerit.java.core.db.daogen.CloseableDAOContextSC;
import org.fugerit.java.core.db.daogen.LobHelper;
import org.fugerit.java.core.db.daogen.SQLTypeConverter;
import org.fugerit.java.core.db.daogen.SimpleServiceProvider;
import org.fugerit.java.core.db.daogen.SimpleServiceResult;
import org.fugerit.java.core.function.SafeFunction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import test.org.fugerit.java.core.db.TestBasicDBHelper;

class TestMisc extends TestBasicDBHelper {

	class CustomSimpleServiceProvider extends SimpleServiceProvider<String> {	
		private static final long serialVersionUID = 89245089543485253L;
		@Override
		public CloseableDAOContext newDefaultContext() throws DAOException {
			CloseableDAOContextSC context = new CloseableDAOContextSC( newConnection() );
			context.setAttribute( "a" , "b" );
			return context;
		}
	}
	
	@Test
	void testSimpleServiceProvider() {
		SafeFunction.apply(() -> {
			CustomSimpleServiceProvider provider = new CustomSimpleServiceProvider();
			try ( CloseableDAOContext context = provider.newDefaultContext() ) {
				Assertions.assertEquals( "b" , context.getAttribute( "a" ) );
			}
			SimpleServiceResult<List<String>> resultList = new SimpleServiceResult<>();
			Assertions.assertNull( provider.createResponseFromList(resultList) );
			List<String> list = new ArrayList<String>();
			resultList.setContent(list);
			resultList.getContent().add( "a" );
			Assertions.assertNotNull( provider.createResponseFromList(resultList) );
			SimpleServiceResult<String> resultObject = new SimpleServiceResult<>();
			Assertions.assertNull( provider.createResponseFromObject( resultObject ) );
			resultObject.setContent( "b" );
			Assertions.assertNotNull( provider.createResponseFromObject( resultObject ) );
			provider.createResultFromList(resultList);
			provider.createResultFromObject(resultObject);
			provider.defaultConvertToUtilDate( null );
			provider.defaultConvertToUtilDate( "2023-09-11" );
			resultObject.addInfoEsito( "a" , "b" );
			resultObject.addInfoDefaultKO();
			resultObject.addInfoDefaultOK();
			resultList.addInfoMultipleResult();
			resultObject.addInfoNoDataFound();
			Assertions.assertThrows( DAOException.class , () -> SimpleServiceProvider.throwDAOException( new IOException( "a" ) ) );
			Assertions.assertEquals( SimpleServiceResult.DEFAULT_OK , 	SimpleServiceResult.newDefaultResult( "e" ).getResult() );
			Assertions.assertEquals( SimpleServiceResult.DEFAULT_OK , 	SimpleServiceResult.newDefaultResult( list ).getResult() );
			Assertions.assertEquals( SimpleServiceResult.DEFAULT_KO , 	SimpleServiceResult.newDefaultResult( null ).getResult() );
			Assertions.assertEquals( SimpleServiceResult.DEFAULT_KO , 	SimpleServiceResult.newDefaultResult( new ArrayList<String>() ).getResult() );
			resultObject.setResult( SimpleServiceResult.DEFAULT_OK );
			Assertions.assertEquals( SimpleServiceResult.DEFAULT_OK , resultObject.getResult() );
			Assertions.assertNotNull( new SimpleServiceResult<String>( SimpleServiceResult.DEFAULT_OK ) );
		});
	}
	
	@Test
	void testSQLTypeConverter() {
		SafeFunction.apply(() -> {
			try ( Connection conn = newConnection() ) {
				Clob clob = LobHelper.createClob( conn , CharArrayDataHandler.newHandlerByte( "c".toCharArray() ) );
				Blob blob = LobHelper.createBlob( conn , ByteArrayDataHandler.newHandlerByte( "b".getBytes() ) );
				Assertions.assertNotNull( clob );
				Assertions.assertNotNull( blob );
				Assertions.assertNotNull( SQLTypeConverter.utilDateToSqlDate( new Date() ) );
				Assertions.assertNotNull( SQLTypeConverter.utilDateToSqlTime( new Date() ) );
				Assertions.assertNotNull( SQLTypeConverter.utilDateToSqlTimestamp( new Date() ) );
				Assertions.assertNotNull( SQLTypeConverter.clobToCharHandler( clob ) );
				Assertions.assertNotNull( SQLTypeConverter.blobToByteHandler( blob ) );
				Assertions.assertTrue( SQLTypeConverter.CONVERT_EX.apply( new IOException( "text" ) ) instanceof SQLException );
			}
		});
	}
	
	@Test
	void testBasicHelper() {
		SafeFunction.apply(() -> {
			Assertions.assertThrows( UnsupportedOperationException.class , () -> BasicHelper.throwUnsupported( "text" ) );
		});
	}
	
}
