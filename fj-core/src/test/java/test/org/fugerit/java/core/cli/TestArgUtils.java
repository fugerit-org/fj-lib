package test.org.fugerit.java.core.cli;

import java.util.Properties;

import org.fugerit.java.core.cli.ArgUtils;
import org.junit.Assert;
import org.junit.Test;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TestArgUtils {

	private static String TEST_PARAM_NAME = "param1";
	
	private static String TEST_PARAM_VALUE = "test1";
	
	private static final String[] ARGS = {
			ArgUtils.getArgString( ArgUtils.ARG_PARAM_FILE ), "src/test/resources/core/cli/param1.properties",
			ArgUtils.getArgString( "input" ), "test-input",
	};
	
	private static final String[] ARGS_ALT = {
			ArgUtils.getArgString( ArgUtils.ARG_PARAM_FILE ), "src/test/resources/core/cli/param1.properties",
			ArgUtils.getArgString( "force" ),
	};
	
	@Test
	public void test1() {
		Properties params = ArgUtils.getArgs( ARGS, true, false );
		log.info( "params : {}", params );
		Assert.assertEquals( TEST_PARAM_VALUE , params.getProperty( TEST_PARAM_NAME ) );
	}
	
	@Test
	public void test2() {
		Properties params = ArgUtils.getArgs( ARGS, true, true );
		log.info( "params : {}", params );
		Assert.assertEquals( TEST_PARAM_VALUE , params.getProperty( TEST_PARAM_NAME ) );
	}
	
	@Test
	public void test3() {
		Properties params = ArgUtils.getArgs( ARGS );
		log.info( "params : {}", params );
		Assert.assertEquals( TEST_PARAM_VALUE , params.getProperty( TEST_PARAM_NAME ) );
	}
	
	@Test
	public void test4() {
		Properties params = ArgUtils.getArgs( ARGS, false, false );
		log.info( "params : {}", params );
		Assert.assertNull( params.getProperty( TEST_PARAM_NAME ) );
	}
	
	@Test
	public void testAlt() {
		Properties params = ArgUtils.getArgs( ARGS_ALT, false, false );
		log.info( "params : {}", params );
		Assert.assertNull( params.getProperty( TEST_PARAM_NAME ) );
	}

	@Test
	public void testNoParam() {
		String[] args = { "test" };
		Properties params = ArgUtils.getArgs( args, false, false );
		log.info( "params : {}", params );
		Assert.assertNull( params.getProperty( TEST_PARAM_NAME ) );
	}
	
}
