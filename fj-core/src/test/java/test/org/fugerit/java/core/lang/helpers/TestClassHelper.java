package test.org.fugerit.java.core.lang.helpers;

import java.io.InputStream;

import org.fugerit.java.core.cfg.ConfigException;
import org.fugerit.java.core.io.StreamIO;
import org.fugerit.java.core.lang.helpers.ClassHelper;
import org.fugerit.java.core.lang.helpers.StringUtils;
import org.junit.Assert;
import org.junit.Test;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import test.org.fugerit.java.BasicTest;

@Slf4j
public class TestClassHelper extends BasicTest {

	private static final String PATH_TXT_OK = "core/test_test.txt";
	
	private static final String PATH_TXT_KO = "ko_"+PATH_TXT_OK;
	
	private boolean testNewInstanceWorker( String type, boolean expectedException ) {
		boolean ok = false;
		try {
			log.info( "try to create : {}", type );
			Object res = ClassHelper.newInstance( type );
			log.info( "object created : {}", res );
			ok = !expectedException && (res != null);
		} catch (ConfigException | ClassNotFoundException | NoSuchMethodException e) {
			if ( expectedException ) {
				log.info( "expected exception ok : {}", e.toString() );
				ok = true;
			} else {
				this.failEx(e);
			}
		} catch (Exception e) {
			this.failEx(e);
		}
		return ok;
	}
	
	private boolean testLoadFromDefaultClassLoaderWorker( String path, boolean expectedException ) {
		boolean ok = false;
		log.info( "try to load path : {}", path );
		try ( InputStream is = ClassHelper.loadFromDefaultClassLoader( path ) )  {
			String text = StreamIO.readString( is );
			log.info( "found : {}", text );
			ok = !expectedException && ( StringUtils.isNotEmpty( text ) );
		} catch (Exception e) {
			if ( expectedException ) {
				log.info( "expected exception ok : {}", e.toString() );
				ok = true;
			} else {
				this.failEx(e);
			}
		}
		return ok;
	}
	
	private boolean testLoadFromClassLoaderWorker( String path, boolean expectedException, Class<?> c ) {
		boolean ok = false;
		log.info( "try to load path : {}", path );
		try ( InputStream is = ClassHelper.loadFromClassLoader( c, path ) )  {
			String text = StreamIO.readString( is );
			log.info( "found : {}", text );
			ok = !expectedException && ( StringUtils.isNotEmpty( text ) );
		} catch (Exception e) {
			if ( expectedException ) {
				log.info( "expected exception ok : {}", e.toString() );
				ok = true;
			} else {
				this.failEx(e);
			}
		}
		return ok;
	}	
	
	@Test
	public void testNewInstanceOk() {
		boolean ok = this.testNewInstanceWorker( TestClassHelper.class.getName(), false );
		Assert.assertTrue( ok );
	}
	
	@Test
	public void testNewInstanceClassNotFoundException() {
		boolean ok = this.testNewInstanceWorker( TestClassHelper.class.getName()+"NotExist", true );
		Assert.assertTrue( ok );
	}

	@Test
	public void testNewInstanceConfigException() {
		boolean ok = this.testNewInstanceWorker( TestPrivateConstructor.class.getName(), true );
		Assert.assertTrue( ok );
	}

	@Test
	public void testNewInstanceNoSuchMethodException() {
		boolean ok = this.testNewInstanceWorker( TestNoDefaultConstructor.class.getName(), true );
		Assert.assertTrue( ok );
	}

	
	@Test
	public void testLoadFromDefaultClassLoaderOk() {
		boolean ok = this.testLoadFromDefaultClassLoaderWorker( PATH_TXT_OK, false );
		Assert.assertTrue( ok );
	}
	
	@Test
	public void testLoadFromClassLoaderOk1() {
		boolean ok = this.testLoadFromClassLoaderWorker( PATH_TXT_OK, false, TestClassHelper.class );
		Assert.assertTrue( ok );
	}
	
	@Test
	public void testLoadFromClassLoaderOk2() {
		boolean ok = this.testLoadFromClassLoaderWorker( PATH_TXT_OK, false, ClassHelper.class );
		Assert.assertTrue( ok );
	}
	
	@Test
	public void testLoadFromClassLoaderOk3() {
		boolean ok = this.testLoadFromClassLoaderWorker( PATH_TXT_OK, false, null );
		Assert.assertTrue( ok );
	}
	
	@Test
	public void testLoadFromDefaultClassLoaderko() {
		boolean ok = this.testLoadFromDefaultClassLoaderWorker( PATH_TXT_KO, true );
		Assert.assertTrue( ok );
	}
	
	@Test
	public void testLoadFromClassLoaderko1() {
		boolean ok = this.testLoadFromClassLoaderWorker( PATH_TXT_KO, true, TestClassHelper.class );
		Assert.assertTrue( ok );
	}
	
	@Test
	public void testLoadFromClassLoaderKo2() {
		boolean ok = this.testLoadFromClassLoaderWorker( PATH_TXT_KO, true, ClassHelper.class );
		Assert.assertTrue( ok );
	}
	
	@Test
	public void testToFullClassName() {
		String name = ClassHelper.toFullClassName( new TestClassHelper() );
		log.info( "tesToFullClassName {}", name );
		Assert.assertEquals( TestClassHelper.class.getName(), name );
	}
	
	@Test
	public void testToSimpleClassName() {
		String name = ClassHelper.toSimpleClassName( new TestClassHelper() );
		log.info( "testToSimpleClassName {}", name );
		Assert.assertEquals( TestClassHelper.class.getSimpleName() , name );
	}
	
}

class TestPrivateConstructor {
	
	private TestPrivateConstructor() {}
	
}

class TestNoDefaultConstructor {
	
	@Getter private boolean test;
	
	public TestNoDefaultConstructor( boolean test ) { this.test = test; }
	
}
