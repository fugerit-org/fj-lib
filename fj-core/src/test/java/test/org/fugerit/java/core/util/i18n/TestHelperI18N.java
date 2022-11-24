package test.org.fugerit.java.core.util.i18n;

import org.fugerit.java.core.util.i18n.HelperI18N;
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
	
	private void testHelper( String lang, String key, Object... params ) {
		String value = this.helper.getString( lang , key, params );
		logger.info( "key:{} , lang:{} -> {}", lang, key, value );
	}
	
	@Before
	public void init() {
		this.helper = HelperI18N.newHelperI18N( CONF_PATH, LANG_DEF, LANG_ALT );
	}
	
	@Test
	public void testSimpleProperty() {
		this.testHelper(  LANG_DEF,  "test.prop.1" );
		this.testHelper(  LANG_ALT[0],  "test.prop.1" );
	}
		
}
