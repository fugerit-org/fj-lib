package test.org.fugerit.java.core.function;

import org.fugerit.java.core.cfg.ConfigRuntimeException;
import org.junit.Assert;
import org.junit.Test;

public class TestSafeFunctionCoverageExample {

	private static final SafeFunctionCoverageExampleInput INPUT = SafeFunctionCoverageExampleInput.newInput( "test" );
	
	private static final SafeFunctionCoverageExampleInput INPUT_ERROR = SafeFunctionCoverageExampleInput.newInput( null );
	
	private boolean testWorker( SafeFunctionCoverageExample exampleImpl ) {
		boolean ok = false;
		exampleImpl.error(INPUT);
		exampleImpl.warn(INPUT);
		exampleImpl.info(INPUT);
		ok = true;
		return ok;
	}
	
	@Test
	public void testClassic() {
		SafeFunctionCoverageExample exampleImpl = new SafeFunctionCoverageExampleClassicImpl();
		Assert.assertTrue( this.testWorker(exampleImpl) );
		Assert.assertThrows( ConfigRuntimeException.class , () -> exampleImpl.error(INPUT_ERROR) );
	}
	
	@Test
	public void testSafe() {
		SafeFunctionCoverageExample exampleImpl = new SafeFunctionCoverageExampleSafeImpl();
		Assert.assertTrue( this.testWorker(exampleImpl) );
		Assert.assertThrows( ConfigRuntimeException.class , () -> exampleImpl.error(INPUT_ERROR) );
	}
	
}
