package test.org.fugerit.java.core.cfg.xml;

import static org.junit.jupiter.api.Assertions.fail;

import java.io.InputStream;

import org.awaitility.core.ThrowingRunnable;
import org.fugerit.java.core.cfg.ConfigException;
import org.fugerit.java.core.cfg.ConfigRuntimeException;
import org.fugerit.java.core.cfg.xml.FactoryCatalog;
import org.fugerit.java.core.cfg.xml.FactoryType;
import org.fugerit.java.core.cfg.xml.FactoryTypeHelper;
import org.fugerit.java.core.cfg.xml.GenericListCatalogConfig;
import org.fugerit.java.core.lang.helpers.ClassHelper;
import org.fugerit.java.core.util.collection.ListMapStringKey;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import lombok.extern.slf4j.Slf4j;
import test.org.fugerit.java.BasicTest;

@Slf4j
public class TestFactoryCatalog extends BasicTest {

	private static FactoryCatalog catalog;
	
	@BeforeAll
	public static void init() {
		try ( InputStream is = ClassHelper.loadFromDefaultClassLoader( "core/cfg/xml/factory-catalog-test.xml" ) ) {
			catalog = new FactoryCatalog();
			GenericListCatalogConfig.load( is , catalog );
		} catch (Exception e) {
			throw ConfigRuntimeException.convertExMethod( "init" , e );
		}
	}
	
	private class ConfigTester implements ThrowingRunnable {
		private String catalogName;
		private FactoryCatalog catalog;
		public ConfigTester(String catalogName, FactoryCatalog catalog) {
			super();
			this.catalogName = catalogName;
			this.catalog = catalog;
		}
		@Override
		public void run() throws Throwable {
			ListMapStringKey<FactoryType> selectedCatalog = catalog.getListMap( this.catalogName );
			log.info( "selectedCatalog -> {}", selectedCatalog );
			Assertions.assertTrue( !selectedCatalog.isEmpty() );
			FactoryTypeHelper<MyType> helper = FactoryTypeHelper.newInstance( MyType.class );
			for ( FactoryType ft : selectedCatalog ) {
				log.info( "ft : {}", ft.getInfo(), ft.getElement() );
				MyType current = (MyType) helper.createHelper( ft );
				log.info( "current -> {}", current );
			}
		}
	}
	
	@Test
	public void testOk() {
		try {
			FactoryCatalog loadedCatalog = (FactoryCatalog)this.fullSerializationTest(catalog);
			ConfigTester tester = new ConfigTester( "default-catalog", loadedCatalog );
			tester.run();
		} catch (Throwable e) {
			fail( "Error : "+e );
		}
	}
		
	@Test
	public void testKo() {
		Assertions.assertNotNull( new ConfigTester( "error-catalog", catalog ) );
	}
	
}


