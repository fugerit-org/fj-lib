package test.org.fugerit.java.core.cfg.xml;

import static org.junit.Assert.fail;

import java.io.InputStream;

import org.fugerit.java.core.cfg.xml.PropertyCatalog;
import org.fugerit.java.core.cfg.xml.PropertyHolder;
import org.fugerit.java.core.lang.helpers.ClassHelper;
import org.junit.Test;

import test.org.fugerit.java.BasicTest;

public class TestPropertyCatalog extends BasicTest {

	private final static String CONFIG_PATH = "core/cfg/xml/property-catalog-test.xml";
	
	private void checkSize( PropertyHolder holder, int size ) {
		if ( holder.getInnerProps().size() != size ) {
			fail( "Wrong holder size : "+holder.getInnerProps().size()+" expected "+size );
		}
	}
	
	private void checkFail( PropertyHolder holder, String key, String expected ) {
		if ( holder == null ) {
			fail( "Null holder!" );
		} else {
			String value = holder.getInnerProps().getProperty( key );
			if ( ( expected == null && value != null ) 
					|| ( value == null && expected != null )
					|| !value.equals( expected ) ) {
				fail( "Expected "+expected+" value, found : "+value );	
			} else {
				logger.info( "holder {}, key '{}' -> value '{}' ok!", holder.getId(), key, value );
			}
		}
	}
	
	@Test
	public void testinit() throws Exception {
		PropertyCatalog catalog = new PropertyCatalog();
		try ( InputStream is = ClassHelper.loadFromDefaultClassLoader( CONFIG_PATH ) ) {
			PropertyCatalog.load( is , catalog );	
		}
		PropertyHolder props01 = catalog.getDefaultCatalog().get( "props-01"  );
		PropertyHolder props02 = catalog.getDefaultCatalog().get( "props-02" );
		PropertyHolder multi = catalog.getDefaultCatalog().get( "props-multi" );
		checkFail( props01 , "props.01.01", "test 01 01" );
		checkFail( props01 , "props.01.02", "test 01 02" );
		checkFail( props01 , "props.01.03", "test 01 03" );
		checkFail( props02 , "props.02.01", "test 02 01" );
		checkFail( props02 , "props.02.02", "test 02 02" );
		checkFail( props02 , "props.02.03", "test 02 03" );
		checkFail( multi , "props.common.01", "common 02 01" );
		checkFail( multi , "props.common.02", "common 01 02" );
		checkFail( multi , "props.common.03", "common 02 03" );		
		checkSize( props01 , 5 );
		checkSize( props02 , 5 );
		checkSize( multi , 9 );	// 4 unique to props01 + 4 unique to props02 + 1 common prop
		logger.info( "property catalog testo OK!" );
	}
	
}
