package test.org.fugerit.java.core.util.mvn;

import org.fugerit.java.core.util.mvn.FJCoreMaven;
import org.fugerit.java.core.util.mvn.MavenProps;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

@Slf4j
class TestMavenProps {

	@Test
	void testMavenOk() {
		Optional prop = MavenProps.getPropertyOptional(FJCoreMaven.FJ_CORE_GROUP_ID, FJCoreMaven.FJ_CORE_ARTIFACT_ID, MavenProps.ARTIFACT_ID );
		log.info( "version ok {}", prop.get() );
		Assertions.assertEquals( "fj-core" , prop.get() );
	}
	
	@Test
	void testMavenKo() {
		// use deprecated method for coverage
		String prop = MavenProps.getPropery( "org.fugerit.java.no.exists", FJCoreMaven.FJ_CORE_ARTIFACT_ID, MavenProps.ARTIFACT_ID );
		log.info( "version ko {}", prop );
		Assertions.assertNull( prop );
	}

	@Test
	void testFjCoreVersion() {
		Optional fjCoreVersion = FJCoreMaven.getFJCoreVersion();
		Assertions.assertTrue( fjCoreVersion.isPresent() );
		log.info( "fjCoreVersion ok {}", fjCoreVersion.get() );
	}
	
}
