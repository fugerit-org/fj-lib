package test.org.fugerit.java.core.util.fun.helper;

import org.fugerit.java.core.util.fun.StringFormat;
import org.fugerit.java.core.util.fun.helper.FormatTime;
import org.fugerit.java.core.util.fun.helper.MillisToSecondsFormat;
import org.junit.Assert;
import org.junit.Test;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TestFormatTime {

	private String worker( StringFormat<Number> format ) {
		String res = format.convert( 1000 ); 
		log.info( "format : {}", res );
		return res;
	}
	
	@Test
	public void test1() {
		MillisToSecondsFormat format = new MillisToSecondsFormat();
		log.info( "getAppend() : {}", format.getAppend() );
		Assert.assertEquals( "1.000" , this.worker(format) );
	}
	
	@Test
	public void test2() {
		MillisToSecondsFormat format = new MillisToSecondsFormat( "s" );
		log.info( "getAppend() : {}", format.getAppend() );
		Assert.assertEquals( "1.000s" , this.worker(format) );
	}
	
	@Test
	public void test3() {
		FormatTime format = new FormatTime( "yyyy-MM-dd" );
		Assert.assertEquals( "1970-01-01" , this.worker( format ) );
	}
	
}
