package test.org.fugerit.java.core.cfg.helpers;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.Properties;

import org.fugerit.java.core.cfg.ConfigException;
import org.fugerit.java.core.cfg.helpers.DefaultConfigurableObject;
import org.fugerit.java.core.cfg.helpers.PropertiesConfigurableObject;
import org.fugerit.java.core.cfg.helpers.UnsafeHelper;
import org.fugerit.java.core.cfg.helpers.XMLConfigurableObject;
import org.fugerit.java.core.lang.helpers.ClassHelper;
import org.fugerit.java.core.xml.XMLException;
import org.fugerit.java.core.xml.dom.DOMIO;
import org.junit.Assert;
import org.junit.Test;
import org.w3c.dom.Element;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TestConfigHelpers {

	@Test
	public void testUnsafeHelper() throws ConfigException {
		UnsafeHelper.handleUnsafe( new IOException( "a" ) , UnsafeHelper.UNSAFE_TRUE ); 
		Assert.assertThrows( Exception.class, () -> UnsafeHelper.handleUnsafe( new IOException( "a" ) , UnsafeHelper.UNSAFE_FALSE ) );
	}
	
	@Test
	public void testConfigurableObject() throws ConfigException, IOException, XMLException {
		XMLConfigurableObject cfg = XMLConfigurableObject.DO_NOTHING;
		cfg.configure( (Element) null );
		Assert.assertNotNull( cfg );
		Assert.assertThrows( ConfigException.class , () -> cfg.configure( new Properties() ) );
		Assert.assertThrows( ConfigException.class , () -> {
			try ( InputStream is = ClassHelper.loadFromDefaultClassLoader( "core/cfg/xml/props/props-01.properties" ) ) {
				cfg.configureProperties(is);
			}
		});
		cfg.getLogger().info( "test {}", cfg );
		DefaultConfigurableObject dCfg = new DefaultConfigurableObject() {
			private static final long serialVersionUID = -1350720094668591320L;
		};
		try ( InputStream is = ClassHelper.loadFromDefaultClassLoader( "core/cfg/xml/props/props-01.properties" ) ) {
			dCfg.configureProperties(is);
		}
		try ( StringReader reader = new StringReader( "<config test='prop1'/>" ) ) {
			dCfg.configure( DOMIO.loadDOMDoc( reader ).getDocumentElement() );
		}
		dCfg.configure( new Properties() );
		PropertiesConfigurableObject pCfg = new PropertiesConfigurableObject() {
			private static final long serialVersionUID = -1356127113306493537L;
			@Override
			public void configure(Properties props) throws ConfigException {
				log.info( "pring props : {}", props );
			}
		};
		Assert.assertThrows( ConfigException.class , () -> { 
			try ( StringReader reader = new StringReader( "<config test='prop1'/>" ) ) {
				pCfg.configure( DOMIO.loadDOMDoc( reader ).getDocumentElement() );
			}
		});
		try ( InputStream is = ClassHelper.loadFromDefaultClassLoader( "core/cfg/xml/props/props-01.properties" ) ) {
			pCfg.configureProperties(is);
		}
		pCfg.configure( new Properties() );
	}
	
}
