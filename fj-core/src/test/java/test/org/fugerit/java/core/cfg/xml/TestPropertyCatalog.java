package test.org.fugerit.java.core.cfg.xml;

import static org.junit.jupiter.api.Assertions.fail;

import org.fugerit.java.core.cfg.xml.PropertyCatalog;
import org.fugerit.java.core.cfg.xml.PropertyHolder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import test.org.fugerit.java.BasicTest;

class TestPropertyCatalog extends BasicTest {

	private static final PropertyCatalog CATALOG = PropertyCatalog.loadConfigSafe( "cl://core/cfg/xml/property-catalog-test.xml" );
	
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
	
	private boolean testWorker( PropertyCatalog catalog ) {
		boolean ok = false;
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
		logger.info( "property catalog test OK!" );
		ok = true;
		return ok;
	}
	
	@Test
	void testinit() throws Exception {
		boolean ok = this.testWorker(CATALOG);
		Assertions.assertTrue( ok );
	}
	
	@Test
	void testSerialization() {
		try {
			PropertyCatalog deserializedValue = (PropertyCatalog) this.fullSerializationTest(CATALOG);
			boolean ok = this.testWorker(deserializedValue);
			Assertions.assertTrue( ok );
		} catch (Exception e) {
			this.failEx(e);
		}
		
	}
	
}
