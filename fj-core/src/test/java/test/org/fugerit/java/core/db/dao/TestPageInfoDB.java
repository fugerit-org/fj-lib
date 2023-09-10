package test.org.fugerit.java.core.db.dao;

import org.fugerit.java.core.db.dao.PageInfoDB;
import org.junit.Assert;
import org.junit.Test;

import lombok.extern.slf4j.Slf4j;
import test.org.fugerit.java.BasicTest;

@Slf4j
public class TestPageInfoDB extends BasicTest {

	@Test
	public void testPI() {
		PageInfoDB info = new PageInfoDB( 3, 5, "test asc" );
		log.info( "info : {} -> {}", info, info.getOrder() );
		Assert.assertNotNull( info );
	}
	
}
