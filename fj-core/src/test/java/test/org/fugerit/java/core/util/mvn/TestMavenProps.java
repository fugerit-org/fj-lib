package test.org.fugerit.java.core.util.mvn;

import org.fugerit.java.core.util.mvn.MavenProps;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TestMavenProps {

	@Test
	public void testMavenOk() {
		String prop = MavenProps.getPropery( "org.fugerit.java", "fj-core", "artifactId" );
		log.info( "version {}", prop );
		Assertions.assertEquals( "fj-core" , prop );
	}
	
	@Test
	public void testMavenKo() {
		String prop = MavenProps.getPropery( "org.fugerit.java.no.exists", "fj-core", "artifactId" );
		log.info( "version {}", prop );
		Assertions.assertNull( prop );
	}
	
}
