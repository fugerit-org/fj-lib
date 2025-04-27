package test.org.fugerit.java.core.util.checkpoint;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Duration;

import org.awaitility.Awaitility;
import org.fugerit.java.core.util.checkpoint.Checkpoints;
import org.junit.jupiter.api.Test;

import test.org.fugerit.java.BasicTest;

class TestCheckpoints extends BasicTest {

	private Checkpoints generate( int size ) {
		Checkpoints cp = Checkpoints.newInstance();
		for ( int k=0; k<size; k++ ) {
			Awaitility.await().atMost( Duration.ofMillis( 500 ) );
			cp.addCheckpoint( "step_"+(k+1) );
		}
		return cp;
	}
	
	@Test
	void testPrintInfo() {
		Checkpoints cp = this.generate( 4 );
		assertTrue( ( cp != null ) );
		cp.printInfo();
	}
	
	@Test
	void testPrettyPrintInfo() {
		Checkpoints cp = this.generate( 4 );
		assertTrue( ( cp != null ) );
		cp.prettyPrintInfo();
	}
	
}
