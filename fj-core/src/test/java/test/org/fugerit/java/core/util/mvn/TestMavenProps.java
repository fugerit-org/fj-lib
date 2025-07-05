package test.org.fugerit.java.core.util.mvn;

import org.fugerit.java.core.util.mvn.MavenProps;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

@Slf4j
class TestMavenProps {

	@Test
	void testMavenOk() {
		Optional prop = MavenProps.getPropertyOptional( "org.fugerit.java", "fj-core", "artifactId" );
		log.info( "version ok {}", prop.get() );
		Assertions.assertEquals( "fj-core" , prop.get() );
	}
	
	@Test
	void testMavenKo() {
		// use deprecated method for coverage
		String prop = MavenProps.getPropery( "org.fugerit.java.no.exists", "fj-core", "artifactId" );
		log.info( "version ko {}", prop );
		Assertions.assertNull( prop );
	}
	
}
