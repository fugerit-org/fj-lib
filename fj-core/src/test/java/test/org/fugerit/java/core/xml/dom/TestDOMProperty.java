package test.org.fugerit.java.core.xml.dom;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.fugerit.java.core.cfg.ConfigException;
import org.fugerit.java.core.lang.helpers.ClassHelper;
import org.fugerit.java.core.xml.XMLException;
import org.fugerit.java.core.xml.dom.DOMIO;
import org.fugerit.java.core.xml.dom.DOMProperty;
import org.junit.Assert;
import org.junit.Test;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class TestDOMProperty {

	@Test
	public void testFill() throws IOException, XMLException, DOMException, ConfigException {
		Properties props = new Properties();
		String key = "key1";
		String oldValue = "oldValue" ;
		props.setProperty( key, oldValue );
		Assert.assertEquals( oldValue , props.getProperty( key ) );
		try ( InputStream is = ClassHelper.loadFromDefaultClassLoader( "core/xml/dom/property.xml" ) ) {
			Document doc = DOMIO.loadDOMDoc( is );
			Element root = doc.getDocumentElement();
			DOMProperty.fill( props , root.getElementsByTagName( DOMProperty.TAG_ENTRY ) );
			Assert.assertNotEquals( oldValue , props.getProperty( key ) );
		}
	}
	
}
