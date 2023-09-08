package test.org.fugerit.java.core.lang.helpers;

import java.io.IOException;

import org.fugerit.java.core.lang.helpers.ExHandler;
import org.fugerit.java.core.lang.helpers.Result;
import org.fugerit.java.core.lang.helpers.ResultExHandler;
import org.junit.Assert;
import org.junit.Test;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TestExHandler {

	private boolean worker( ExHandler handler ) {
		handler.fatal( new IOException( "fatal" ) );
		handler.error( new IOException( "error" ) );
		handler.warning( new IOException( "warning" ) );
		return handler != null;
	}
	
	@Test
	public void testResultExHandler() {
		ResultExHandler handler = new ResultExHandler();
		handler.setResult( new Result() );
		log.info( "test : {}", handler.getResult() );
		boolean ok = this.worker(handler);
		Assert.assertTrue( ok );
	}
	
	@Test
	public void testResultExHandlerAlt() {
		org.fugerit.java.core.util.result.ResultExHandler handler = new org.fugerit.java.core.util.result.ResultExHandler();
		handler.setResult( new Result() );
		log.info( "test : {}", handler.getResult() );
		boolean ok = this.worker(handler);
		Assert.assertTrue( ok );
	}
	
}
