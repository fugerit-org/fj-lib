package test.org.fugerit.java.core.javagen;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import lombok.extern.slf4j.Slf4j;
import test.org.fugerit.java.BasicTest;

@Slf4j
class TestJavaGenerator extends BasicTest {
	
	@Test
	void generate() {
		try {
			CheckJavaGenerator generator = new CheckJavaGenerator();	
			generator.init();
			generator.generate();
			log.info( "ok : {}", generator.getContent() );
			generator.write();
			generator.generate();
			Assertions.assertNotNull(generator);
		} catch (Exception e) {
			this.failEx(e);
		}
	}

	@Test
	void generateNoPublicClass() {
		try {
			NoPublicClassJavaGenerator generator = new NoPublicClassJavaGenerator();
			generator.init();
			generator.generate();
			log.info( "ok : {}", generator.getContent() );
			generator.write();
			generator.generate();
			Assertions.assertNotNull(generator);
		} catch (Exception e) {
			this.failEx(e);
		}
	}
	
}
