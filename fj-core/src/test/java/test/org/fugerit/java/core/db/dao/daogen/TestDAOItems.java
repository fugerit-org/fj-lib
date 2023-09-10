package test.org.fugerit.java.core.db.dao.daogen;

import java.io.IOException;
import java.math.BigDecimal;

import org.fugerit.java.core.db.dao.DAORuntimeException;
import org.fugerit.java.core.db.daogen.BaseIdFinder;
import org.fugerit.java.core.db.daogen.BasicDaoResult;
import org.fugerit.java.core.db.daogen.ResultUtils;
import org.fugerit.java.core.util.result.Result;
import org.junit.Assert;
import org.junit.Test;

import lombok.extern.slf4j.Slf4j;
import test.org.fugerit.java.BasicTest;

@Slf4j
public class TestDAOItems extends BasicTest {

	@Test
	public void testBasicIdFinder() throws IOException {
		BigDecimal value = BigDecimal.valueOf( 10L );
		BaseIdFinder finder = new BaseIdFinder();
		finder.setId( value );
		finder.setId( value.longValue() );
		log.info( "item : {}", finder );
		Assert.assertEquals( value , finder.getId() );
		Assert.assertNotNull( this.fullSerializationTest( finder ) );
	}
	
	@Test
	public void testBasicDaoResult() throws IOException {
		BigDecimal value = BigDecimal.valueOf( 10L );
		BasicDaoResult<BigDecimal> item = new BasicDaoResult<>();
		item.setSingleResult( value );
		item.evaluateResultFromList();
		log.info( "item : {}", item );
		Assert.assertEquals( value , ResultUtils.oneOut( item ) );
		item.setResult( item );
		item.setResult( Result.RESULT_CODE_OK , "desc ok" );
		item.getList().clear();
		item.evaluateResultFromList();
		Assert.assertNull(ResultUtils.oneOut( new BasicDaoResult<>( BasicDaoResult.RESULT_NODATAFOUND ) ) );
		Assert.assertNotNull( new BasicDaoResult<>( Result.RESULT_CODE_OK, "test desc" ) );
		item.getList().add( value );
		item.getList().add( value );
		Assert.assertThrows( DAORuntimeException.class , () -> ResultUtils.oneOut( item ) );
	}
	
}
