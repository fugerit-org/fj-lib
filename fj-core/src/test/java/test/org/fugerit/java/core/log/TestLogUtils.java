package test.org.fugerit.java.core.log;

import java.io.IOException;

import org.fugerit.java.core.cfg.ConfigRuntimeException;
import org.fugerit.java.core.log.BasicLogObject;
import org.fugerit.java.core.log.LogFacade;
import org.fugerit.java.core.log.LogObject;
import org.fugerit.java.core.log.LogUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import lombok.extern.slf4j.Slf4j;
import test.org.fugerit.java.helpers.FailHelper;

@Slf4j
public class TestLogUtils {

	@Test
	public void testLogUtilsBuild() {
		StringBuilder builder = new StringBuilder();
		LogUtils.appendPropDefault(builder, "key", "value" );
		log.info( "result {}", builder );
		Assertions.assertEquals( "key=value" , builder.toString() );
	}
	
	@Test
	public void testLogUtilsAppendable() {
		Appendable builder = new StringBuilder();
		LogUtils.appendPropDefault(builder, "key", "value" );
		log.info( "result {}", builder );
		Assertions.assertEquals( "key=value" , builder.toString() );
	}
	
	@Test
	public void testLogUtilsAppendableFail() {
		Appendable builder = new Appendable() {
			@Override
			public Appendable append(CharSequence csq, int start, int end) throws IOException {
				// do nothing
				return null;
			}
			@Override
			public Appendable append(char c) throws IOException {
				// do nothing
				return null;
			}
			@Override
			public Appendable append(CharSequence csq) throws IOException {
				if ( FailHelper.DO_FAIL ) {
					throw new IOException( "fail by scenario" );
				}
				return null;
			}
		};
		Assertions.assertThrows( ConfigRuntimeException.class , () -> LogUtils.appendProp(builder, "key", "value", ",", "test_") );
	}
	
	@Test
	public void testWrap() {
		LogObject obj = LogUtils.wrap( log );
		obj.getLogger().info( "wrap ok!" );
		Assertions.assertNotNull( obj );
	}
	
	@Test
	public void testBasicLogObject() {
		LogObject obj = new BasicLogObject();
		obj.getLogger().info( "BasicLogObject OK!" );
		Assertions.assertNotNull( obj );
	}
	
	@Test
	public void testLogFacade() {
		LogFacade.getLog().info( "LogFacade OK!" );
		LogFacade.handleWarn( new IOException( "a" ) );
		LogFacade.handleError( new IOException( "b" ) );
		LogFacade.handleWarn( log, new IOException( "c" ) );
		LogFacade.handleError( log, new IOException( "d" ) );
		Assertions.assertNotNull( LogFacade.newLogger( new TestLogUtils() ) );
	}
	
}
