package test.org.fugerit.java.core.function;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

import org.fugerit.java.core.cfg.ConfigRuntimeException;
import org.fugerit.java.core.function.SafeFunction;
import org.fugerit.java.core.xml.XMLException;
import org.junit.Assert;
import org.junit.Test;

import lombok.extern.slf4j.Slf4j;
import test.org.fugerit.java.helpers.FailHelper;

@Slf4j
public class TestSafeFunction {

	@Test
	public void testFailGet() {
		Assert.assertThrows(ConfigRuntimeException.class, () -> {
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
	public void testFailGetGen() {
		Assert.assertThrows(XMLException.class, () -> {
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
	public void testFailApplyGen() {
		Assert.assertThrows(ConfigRuntimeException.class, () -> {
			SafeFunction.apply( () -> { throw new IOException( "ex" ); } );
		});
	}
	
	@Test
	public void testApplyLogGen() {
		boolean ok = false;
		SafeFunction.apply( () -> { throw new IOException( "ex1" ); }, e -> log.warn( "Error on exception {}", e, e.getMessage() ) );
		ok = true;
		Assert.assertTrue(ok);
	}
	
	@Test
	public void testGetLogGen() {
		String res = SafeFunction.get( () -> { throw new IOException( "ex2" ); }, e -> log.warn( "Error on exception {}", e, e.getMessage() ) );
		Assert.assertNull( res );
	}
	
	@Test
	public void testGetWithDefaultLogGen() {
		String res = SafeFunction.getWithDefault( () -> { throw new IOException( "ex3" ); }, e -> { 
			log.warn( "Error on exception {}", e, e.getMessage() );
			return "default-string";
		} );
		Assert.assertEquals( "default-string", res );
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
	public void testExample() {
		String resClassic = this.testExampleToOneLineClassic();
		String resSafeFunction = this.testExampleToOneLineSafeFunction();
		Assert.assertEquals( resClassic, resSafeFunction );
	}
	
	
}
