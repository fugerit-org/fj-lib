package test.org.fugerit.java.core.util.checkpoint;

import static org.junit.Assert.assertTrue;

import org.fugerit.java.core.cfg.ConfigRuntimeException;
import org.fugerit.java.core.util.checkpoint.Checkpoints;
import org.junit.Test;

import test.org.fugerit.java.BasicTest;

public class TestCheckpoints extends BasicTest {

	private Checkpoints generate( int size ) {
		Checkpoints cp = Checkpoints.newInstance();
		for ( int k=0; k<size; k++ ) {
			try {
				Thread.sleep( 10+(int)(Math.random()*10) );
			} catch (InterruptedException e) {
				throw new ConfigRuntimeException( e );
			}
			cp.addCheckpoint( "step_"+(k+1) );
		}
		return cp;
	}
	
	@Test
	public void testPrintInfo() {
		Checkpoints cp = this.generate( 4 );
		assertTrue( ( cp != null ) );
		cp.printInfo();
	}
	
	@Test
	public void testPrettyPrintInfo() {
		Checkpoints cp = this.generate( 4 );
		assertTrue( ( cp != null ) );
		cp.prettyPrintInfo();
	}
	
}
