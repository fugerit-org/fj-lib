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
import org.junit.Assert;
import org.junit.Test;

import test.org.fugerit.java.core.db.BasicDBHelper;

public class TestMisc extends BasicDBHelper {

	class CustomSimpleServiceProvider extends SimpleServiceProvider<String> {	
		private static final long serialVersionUID = 89245089543485253L;
		@Override
		public CloseableDAOContext newDefaultContext() throws DAOException {
			CloseableDAOContextSC context = new CloseableDAOContextSC( newConnection() );
			context.setAttribute( "a" , "b" );
			return context;
		}
	};
	
	@Test
	public void testSimpleServiceProvider() {
		SafeFunction.apply(() -> {
			CustomSimpleServiceProvider provider = new CustomSimpleServiceProvider();
			try ( CloseableDAOContext context = provider.newDefaultContext() ) {
				Assert.assertEquals( "b" , context.getAttribute( "a" ) );
			}
			SimpleServiceResult<List<String>> resultList = new SimpleServiceResult<>();
			Assert.assertNull( provider.createResponseFromList(resultList) );
			List<String> list = new ArrayList<String>();
			resultList.setContent(list);
			resultList.getContent().add( "a" );
			Assert.assertNotNull( provider.createResponseFromList(resultList) );
			SimpleServiceResult<String> resultObject = new SimpleServiceResult<>();
			Assert.assertNull( provider.createResponseFromObject( resultObject ) );
			resultObject.setContent( "b" );
			Assert.assertNotNull( provider.createResponseFromObject( resultObject ) );
			provider.createResultFromList(resultList);
			provider.createResultFromObject(resultObject);
			provider.defaultConvertToUtilDate( null );
			provider.defaultConvertToUtilDate( "2023-09-11" );
			resultObject.addInfoEsito( "a" , "b" );
			resultObject.addInfoDefaultKO();
			resultObject.addInfoDefaultOK();
			resultList.addInfoMultipleResult();
			resultObject.addInfoNoDataFound();
			Assert.assertThrows( DAOException.class , () -> SimpleServiceProvider.throwDAOException( new IOException( "a" ) ) );
			Assert.assertEquals( SimpleServiceResult.DEFAULT_OK , 	SimpleServiceResult.newDefaultResult( "e" ).getResult() );
			Assert.assertEquals( SimpleServiceResult.DEFAULT_OK , 	SimpleServiceResult.newDefaultResult( list ).getResult() );
			Assert.assertEquals( SimpleServiceResult.DEFAULT_KO , 	SimpleServiceResult.newDefaultResult( null ).getResult() );
			Assert.assertEquals( SimpleServiceResult.DEFAULT_KO , 	SimpleServiceResult.newDefaultResult( new ArrayList<String>() ).getResult() );
			resultObject.setResult( SimpleServiceResult.DEFAULT_OK );
			Assert.assertEquals( SimpleServiceResult.DEFAULT_OK , resultObject.getResult() );
			Assert.assertNotNull( new SimpleServiceResult<String>( SimpleServiceResult.DEFAULT_OK ) );
		});
	}
	
	@Test
	public void testSQLTypeConverter() {
		SafeFunction.apply(() -> {
			try ( Connection conn = newConnection() ) {
				Clob clob = LobHelper.createClob( conn , CharArrayDataHandler.newHandlerByte( "c".toCharArray() ) );
				Blob blob = LobHelper.createBlob( conn , ByteArrayDataHandler.newHandlerByte( "b".getBytes() ) );
				Assert.assertNotNull( clob );
				Assert.assertNotNull( blob );
				Assert.assertNotNull( SQLTypeConverter.utilDateToSqlDate( new Date() ) );
				Assert.assertNotNull( SQLTypeConverter.utilDateToSqlTime( new Date() ) );
				Assert.assertNotNull( SQLTypeConverter.utilDateToSqlTimestamp( new Date() ) );
				Assert.assertNotNull( SQLTypeConverter.clobToCharHandler( clob ) );
				Assert.assertNotNull( SQLTypeConverter.blobToByteHandler( blob ) );
				Assert.assertTrue( SQLTypeConverter.CONVERT_EX.apply( new IOException( "text" ) ) instanceof SQLException );
			}
		});
	}
	
	@Test
	public void testBasicHelper() {
		SafeFunction.apply(() -> {
			Assert.assertThrows( UnsupportedOperationException.class , () -> BasicHelper.throwUnsupported( "text" ) );
		});
	}
	
}
