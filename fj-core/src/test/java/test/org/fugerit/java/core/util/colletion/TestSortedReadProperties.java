package test.org.fugerit.java.core.util.colletion;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Properties;

import org.fugerit.java.core.cfg.ConfigRuntimeException;
import org.fugerit.java.core.lang.helpers.ClassHelper;
import org.fugerit.java.core.util.collection.SortedProperties;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import test.org.fugerit.java.BasicTest;

class TestSortedReadProperties extends BasicTest {

    private static final Logger logger = LoggerFactory.getLogger( TestSortedReadProperties.class );

    @Test
    void test1() {
    	boolean ok = false;
        try ( InputStream is = ClassHelper.loadFromDefaultClassLoader( "core/util/collection/test_store_1.properties" );
        		FileOutputStream fos = new FileOutputStream( new File( "target/test_sorted_properties.properties" ) ) ) {
            Properties testProps = new SortedProperties();
            testProps.load( is );
            logger.info( "test toString() {}", testProps );
            logger.info( "test keySet() :" );
            for ( Object k : testProps.keySet() ) {
            	logger.info( "{} -> {}", k, testProps.get( k ) );
            }
            logger.info( "test keys() :" );
            Enumeration<Object> keys = testProps.keys();
            while ( keys.hasMoreElements() ) {
            	Object k = keys.nextElement();
            	logger.info( "{} -> {}", k, testProps.get( k ) );
            }
            testProps.store( fos , "test sorted properties" );
            logger.info( "hashCode : {}, toString {}", testProps.hashCode(), testProps.toString() );
            ok = testProps.equals( testProps );
        } catch (Exception e) {
            throw new ConfigRuntimeException( e );
        }
        assertTrue( ok );
    }
	
}
