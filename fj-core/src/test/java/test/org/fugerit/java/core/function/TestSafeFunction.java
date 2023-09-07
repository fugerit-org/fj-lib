package test.org.fugerit.java.core.function;

import java.io.IOException;

import org.fugerit.java.core.cfg.ConfigRuntimeException;
import org.fugerit.java.core.function.SafeFunction;
import org.fugerit.java.core.xml.XMLException;
import org.junit.Assert;
import org.junit.Test;

import test.org.fugerit.java.helpers.FailHelper;

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
			SafeFunction.get( () -> {
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
	
}
