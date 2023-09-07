package test.org.fugerit.java.core.javagen;

import org.junit.Assert;
import org.junit.Test;

import lombok.extern.slf4j.Slf4j;
import test.org.fugerit.java.BasicTest;

@Slf4j
public class TestJavaGenerator extends BasicTest {
	
	@Test
	public void generate() {
		try {
			CheckJavaGenerator generator = new CheckJavaGenerator();	
			generator.init();
			generator.generate();
			log.info( "ok : {}", generator.getContent() );
			generator.write();
			generator.generate();
			Assert.assertNotNull(generator);
		} catch (Exception e) {
			this.failEx(e);
		}
	}
	
}
