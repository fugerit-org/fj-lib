package test.org.fugerit.java.core.util.i18n;

import org.fugerit.java.core.util.i18n.BundleMapI18N;
import org.fugerit.java.core.util.i18n.HelperI18N;
import org.fugerit.java.core.util.i18n.ParamI18N;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class TestHelperI18N {

	public static final String LANG_EN = "en";
	
	public static final String LANG_IT = "it";
	
	public static final String LANG_DEF = LANG_EN;
	
	public static final String LANG_ALT[] = { LANG_IT };
	
	private static final Logger logger = LoggerFactory.getLogger( TestHelperI18N.class );
	
	private static final String CONF_PATH = "core.util.i18n.test";
	
	private static final String CONF_PATH_PARAM = "core.util.i18n.param";

	private HelperI18N helper;

	private HelperI18N helperParam;

	@BeforeEach
	public void init() {
		this.helper = BundleMapI18N.newHelperI18N( CONF_PATH, LANG_DEF, LANG_ALT );
		this.helperParam = BundleMapI18N.newHelperI18N( CONF_PATH_PARAM, LANG_DEF, LANG_ALT );
	}

	private void testHelper( String expectedValue, String lang, String key, Object... params ) {
		String value = this.helper.getString( lang , key, params );
		logger.info( "key:{} , lang:{} -> {}", lang, key, value );
		Assertions.assertEquals(  expectedValue, value, "Value different for key : "+key+", lang : "+lang );
	}

	@Test
	void testSimpleProperty1() {
		String key = "test.prop.1"; // test.prop.1 is available for both languages
		this.testHelper( "Test property 1", LANG_DEF,  key );
		this.testHelper( "Proprietà test 1", LANG_ALT[0],  key );
	}
	
	@Test
	void testSimpleProperty2() {
		String key = "test.prop.2";	// test.prop.2 is available only for default language
		this.testHelper( "Test property 2", LANG_DEF,  key );
		this.testHelper( "Test property 2", LANG_ALT[0],  key );
	}
	
	@Test
	void testSimpleProperty3() {
		String key = "test.prop.3";	// test.prop.3 is available only for default language
		this.testHelper( null, LANG_DEF, key  );
		this.testHelper( "Proprietà test 3", LANG_ALT[0], key );
	}
	
	@Test
	void testComplexProperty1() {
		String key = "test.complex.prop.1";	// contains a integer param and a i18n param
		String keyParam = "test.param.1";	// the key for the i18n param
		this.testHelper( "Complex property 1 -> simple parameter : 3 and i18n parameter : (ParamI18N value)", 
				LANG_DEF,  key, Integer.valueOf( 3 ), ParamI18N.newParamI18N( keyParam ) );
		this.testHelper( "Proprietà complessa test 1 -> parametro semplice : 3 e parametro i18n : (valore ParamI18N)", 
				LANG_ALT[0],  key, Integer.valueOf( 3 ), ParamI18N.newParamI18N( keyParam ) );
	}
	
	@Test
	void testComplexProperty1Alt() {
		String key = "test.complex.prop.1";	// contains a integer param and a i18n param
		String keyParam = "test.param.1.alt";	// the key for the i18n param
		this.testHelper( "Complex property 1 -> simple parameter : 3 and i18n parameter : (ParamI18N value alternative bundle)", 
				LANG_DEF,  key, Integer.valueOf( 3 ), ParamI18N.newParamI18N( keyParam, this.helperParam ) );
		this.testHelper( "Proprietà complessa test 1 -> parametro semplice : 3 e parametro i18n : (valore ParamI18N bundle alternativo)", 
				LANG_ALT[0],  key, Integer.valueOf( 3 ), ParamI18N.newParamI18N( keyParam, this.helperParam ) );
	}
		
}
