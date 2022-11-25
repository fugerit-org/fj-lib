package test.org.fugerit.java.core.util.i18n;

import org.fugerit.java.core.util.i18n.BundleMapI18N;
import org.fugerit.java.core.util.i18n.HelperI18N;
import org.fugerit.java.core.util.i18n.ParamI18N;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestHelperI18N {

	public final static String LANG_EN = "en";
	
	public final static String LANG_IT = "it";
	
	public final static String LANG_DEF = LANG_EN;
	
	public final static String LANG_ALT[] = { LANG_IT };
	
	private final static Logger logger = LoggerFactory.getLogger( TestHelperI18N.class );
	
	private static final String CONF_PATH = "core.util.i18n.test";
	
	private HelperI18N helper;
	
	private void testHelper( String expectedValue, String lang, String key, Object... params ) {
		String value = this.helper.getString( lang , key, params );
		logger.info( "key:{} , lang:{} -> {}", lang, key, value );
		Assert.assertEquals( "Value different for key : "+key+", lang : "+lang , expectedValue, value );
	}
	
	@Before
	public void init() {
		this.helper = BundleMapI18N.newHelperI18N( CONF_PATH, LANG_DEF, LANG_ALT );
	}
	
	@Test
	public void testSimpleProperty1() {
		String key = "test.prop.1"; // test.prop.1 is available for both languages
		this.testHelper( "Test property 1", LANG_DEF,  key );
		this.testHelper( "Proprietà test 1", LANG_ALT[0],  key );
	}
	
	@Test
	public void testSimpleProperty2() {
		String key = "test.prop.2";	// test.prop.2 is available only for default language
		this.testHelper( "Test property 2", LANG_DEF,  key );
		this.testHelper( "Test property 2", LANG_ALT[0],  key );
	}
	
	@Test
	public void testSimpleProperty3() {
		String key = "test.prop.3";	// test.prop.3 is available only for default language
		this.testHelper( null, LANG_DEF, key  );
		this.testHelper( "Proprietà test 3", LANG_ALT[0], key );
	}
	
	@Test
	public void testComplexProperty1() {
		String key = "test.complex.prop.1";	// contains a integer param and a i18n param
		String keyParam = "test.param.1";	// the key for the i18n param
		this.testHelper( "Complex property 1 -> simple parameter : 3 and i18n parameter : (ParamI18N value)", 
				LANG_DEF,  key, new Integer( 3 ), ParamI18N.newParamI18N( keyParam ) );
		this.testHelper( "Proprietà complessa test 1 -> parametro semplice : 3 e parametro i18n : (valore ParamI18N)", 
				LANG_ALT[0],  key, new Integer( 3 ), ParamI18N.newParamI18N( keyParam ) );
	}
	
	
		
}