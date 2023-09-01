package test.org.fugerit.java.core.cfg.xml;

import static org.junit.Assert.fail;

import java.io.InputStream;

import org.fugerit.java.core.cfg.ConfigException;
import org.fugerit.java.core.cfg.ConfigRuntimeException;
import org.fugerit.java.core.cfg.xml.FactoryCatalog;
import org.fugerit.java.core.cfg.xml.FactoryType;
import org.fugerit.java.core.cfg.xml.FactoryTypeHelper;
import org.fugerit.java.core.cfg.xml.GenericListCatalogConfig;
import org.fugerit.java.core.lang.helpers.ClassHelper;
import org.fugerit.java.core.util.collection.ListMapStringKey;
import org.junit.Assert;
import org.junit.Test;

import lombok.extern.slf4j.Slf4j;
import test.org.fugerit.java.BasicTest;

@Slf4j
public class TestFactoryCatalog extends BasicTest {

	private static final String PATH_TEST_CATALOG = "core/cfg/xml/factory-catalog-test.xml";
	
	@Test
	public void testOk() {
		try ( InputStream is = ClassHelper.loadFromDefaultClassLoader( PATH_TEST_CATALOG ) ) {
			FactoryCatalog catalog = new FactoryCatalog();
			GenericListCatalogConfig.load( is , catalog );
			ListMapStringKey<FactoryType> selectedCatalog = catalog.getListMap( "default-catalog" );
			log.info( "defaultCatalog -> {}", selectedCatalog );
			Assert.assertTrue( !selectedCatalog.isEmpty() );
			FactoryTypeHelper<MyType> helper = FactoryTypeHelper.newInstance( MyType.class );
			for ( FactoryType ft : selectedCatalog ) {
				log.info( "ft : {}", ft.getInfo(), ft.getElement() );
				MyType current = (MyType) helper.createHelper( ft );
				log.info( "current -> {}", current );
			}
		} catch (Exception e) {
			this.failEx(e);
		}
	}
		
	@Test
	public void testKo() {
		try ( InputStream is = ClassHelper.loadFromDefaultClassLoader( PATH_TEST_CATALOG ) ) {
			FactoryCatalog catalog = new FactoryCatalog();
			GenericListCatalogConfig.load( is , catalog );
			ListMapStringKey<FactoryType> selectedCatalog = catalog.getListMap( "error-catalog" );
			log.info( "catalog -> {}", selectedCatalog );
			Assert.assertTrue( !selectedCatalog.isEmpty() );
			FactoryTypeHelper<MyType> helper = FactoryTypeHelper.newInstance( MyType.class );
			for ( FactoryType ft : selectedCatalog ) {
				log.info( "ft : {}", ft.getInfo(), ft.getElement() );
				MyType current = (MyType) helper.createHelper( ft );
				log.info( "current -> {}", current );
			}
			fail( "Should throw exception before this line! "+selectedCatalog );
		} catch (Exception e) {
			Assert.assertTrue( e instanceof ConfigRuntimeException || e instanceof ConfigException );
		}
	}
	
}
