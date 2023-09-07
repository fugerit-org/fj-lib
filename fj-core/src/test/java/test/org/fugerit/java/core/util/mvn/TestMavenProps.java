package test.org.fugerit.java.core.util.mvn;

import org.fugerit.java.core.util.mvn.MavenProps;
import org.junit.Assert;
import org.junit.Test;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TestMavenProps {

	@Test
	public void testMavenOk() {
		String prop = MavenProps.getPropery( "org.fugerit.java", "fj-core", "artifactId" );
		log.info( "version {}", prop );
		Assert.assertEquals( "fj-core" , prop );
	}
	
	@Test
	public void testMavenKo() {
		String prop = MavenProps.getPropery( "org.fugerit.java.no.exists", "fj-core", "artifactId" );
		log.info( "version {}", prop );
		Assert.assertNull( prop );
	}
	
}
