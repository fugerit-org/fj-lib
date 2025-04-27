package test.org.fugerit.java.core.function;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.function.Consumer;

import org.fugerit.java.core.cfg.ConfigRuntimeException;
import org.fugerit.java.core.db.dao.DAOException;
import org.fugerit.java.core.db.dao.DAORuntimeException;
import org.fugerit.java.core.function.SafeFunction;
import org.fugerit.java.core.lang.helpers.BooleanUtils;
import org.fugerit.java.core.xml.XMLException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import lombok.extern.slf4j.Slf4j;
import test.org.fugerit.java.helpers.FailHelper;

@Slf4j
class TestSafeFunction {

	private static final DAOException TEST_CHECKED_EX = new DAOException( "test checked" );
	
	private static final DAORuntimeException TEST_RUNTIME_EX = new DAORuntimeException( "test runtime" );

	@Test
	void testGetUsingDefault() {
		Assertions.assertEquals( "2", SafeFunction.getUsingDefault( () -> null, () -> "2" ) );
		Assertions.assertEquals( "0", SafeFunction.getUsingDefault( () -> "0", () -> "1" ) );
	}
	
	@Test
	void testFailGet() {
		Assertions.assertThrows(ConfigRuntimeException.class, () -> {
			SafeFunction.get( () -> {
				if ( FailHelper.DO_FAIL ) {
					throw new IOException( "test" );
				} else {
					return "ok";
				}
			});
		});
	}
	
	@Test
	void testFailGetGen() {
		Assertions.assertThrows(XMLException.class, () -> {
			SafeFunction.getEx( () -> {
				if ( FailHelper.DO_FAIL ) {
					throw new IOException( "test" );
				} else {
					return "ok";
				}
			}, XMLException.CONVERT_FUN );
		});
	}
	
	@Test
	void testFailApplyGen() {
		Assertions.assertThrows(ConfigRuntimeException.class, () -> {
			SafeFunction.apply( () -> { throw new IOException( "ex" ); } );
		});
	}
	
	@Test
	void testApplyLogGen() {
		boolean ok = false;
		SafeFunction.apply( () -> { throw new IOException( "ex1" ); }, e -> log.warn( "Error on exception {}", e, e.getMessage() ) );
		ok = true;
		Assertions.assertTrue(ok);
	}
	
	@Test
	void testGetLogGen() {
		String res = SafeFunction.get( () -> { throw new IOException( "ex2" ); }, e -> log.warn( "Error on exception {}", e, e.getMessage() ) );
		Assertions.assertNull( res );
	}
	
	@Test
	void testApplyWithMessage() {
		Assertions.assertThrows( ConfigRuntimeException.class , () -> SafeFunction.applyWithMessage( () -> { throw new IOException( "exApplyWithMessage" ); }, "error test" ) );
	}
	
	@Test
	void testGetWithMessage() {
		Assertions.assertThrows( ConfigRuntimeException.class , () -> SafeFunction.getWithMessage( () -> { throw new IOException( "exGetWithMessage" ); }, "error test" ) );
	}
	
	@Test
	void testApplyWithMessageOk() {
		boolean ok = true;
		SafeFunction.applyWithMessage( () -> log.info( "ok" ), "no error apply" );
		Assertions.assertTrue( ok );
	}
	
	@Test
	void testGetWithMessageOk() {
		Assertions.assertEquals( BooleanUtils.BOOLEAN_1 , SafeFunction.getWithMessage( () -> BooleanUtils.BOOLEAN_1, "no error get" ) );
	}
	
	@Test
	void testGetSilent() {
		String res = SafeFunction.getSilent( () -> { throw new IOException( "exGetSilent" ); } );
		Assertions.assertNull( res );
	}
	
	@Test
	void testGetWithDefaultLogGen() {
		String res = SafeFunction.getWithDefault( () -> { throw new IOException( "ex3" ); }, e -> { 
			log.warn( "Error on exception {}", e, e.getMessage() );
			return "default-string";
		} );
		Assertions.assertEquals( "default-string", res );
	}
	
	@Test
	void testApplyOnCondition() {
		boolean ok = SafeFunction.applyOnCondition( () -> false , () -> log.info( "do nothing" ) );
		Assertions.assertFalse( ok );
		ok = SafeFunction.applyIfNotNull( null , () -> log.info( "do nothing" ) );
		Assertions.assertFalse( ok );
	}

	@Test
	void testGetOnCondition() {
		String res = SafeFunction.getOnCondition( () -> Boolean.FALSE , () -> "a" );
		Assertions.assertNull( res );
		res = SafeFunction.getIfNotNull( null , () -> "b" );
		Assertions.assertNull( res );
	}

	private boolean testExHandlerWorker( Consumer<Exception> exHandler, boolean ex ) {
		if ( ex ) {
			assertThrows( Exception.class , () -> exHandler.accept( new IOException( "test" ) ) );
		} else {
			exHandler.accept( new IOException( "test" ) );
		}
		return true;
	}
	
	@Test
	void testExHandler() {
		Assertions.assertTrue( this.testExHandlerWorker( SafeFunction.EX_CONSUMER_LOG_WARN, false ) );
		Assertions.assertTrue( this.testExHandlerWorker( SafeFunction.EX_CONSUMER_TRACE_WARN, false ) );
		Assertions.assertTrue( this.testExHandlerWorker( SafeFunction.EX_CONSUMER_THROW_CONFIG_RUNTIME, true ) );
	}
	
	@Test
	void testExHandlerThrowConfigRuntime() {
		Assertions.assertThrows( ConfigRuntimeException.class , 
				() -> SafeFunction.EX_CONSUMER_THROW_CONFIG_RUNTIME.accept( TEST_RUNTIME_EX ) );
		Assertions.assertThrows( ConfigRuntimeException.class , 
				() -> SafeFunction.EX_CONSUMER_THROW_CONFIG_RUNTIME.accept( TEST_CHECKED_EX ) );
	}
	
	@Test
	void testExHandlerThrowConfigRuntimeRethrowRTE() {
		Assertions.assertThrows( TEST_RUNTIME_EX.getClass() , 
				() -> SafeFunction.EX_CONSUMER_RETHROW_RTE_OR_CONVERT_CHECKED_TO_CRE.accept( TEST_RUNTIME_EX ) );
		Assertions.assertThrows( ConfigRuntimeException.class , 
				() -> SafeFunction.EX_CONSUMER_RETHROW_RTE_OR_CONVERT_CHECKED_TO_CRE.accept( TEST_CHECKED_EX ) );
	}
	
	public String testExampleToOneLineClassic() {
		StringBuilder builder = new StringBuilder();
		try ( BufferedReader reader = new BufferedReader( new StringReader( "test" ) ) ) {
			reader.lines().forEach( line -> builder.append( line+" " ) );
		} catch (IOException e) {
			throw new ConfigRuntimeException( e );
		}
		return builder.toString();
	}
	
	public String testExampleToOneLineSafeFunction() {
		return SafeFunction.get(() -> {
			StringBuilder builder = new StringBuilder();
			try ( BufferedReader reader = new BufferedReader( new StringReader( "test" ) ) ) {
				reader.lines().forEach( line -> builder.append( line+" " ) );
			}	
			return builder.toString();
		});
	}
	
	@Test
	void testExample() {
		String resClassic = this.testExampleToOneLineClassic();
		String resSafeFunction = this.testExampleToOneLineSafeFunction();
		Assertions.assertEquals( resClassic, resSafeFunction );
	}
	
	
}
