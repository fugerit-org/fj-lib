package test.org.fugerit.java.core.cfg.xml;

import java.io.Reader;
import java.io.StringReader;

import org.fugerit.java.core.cfg.xml.TextValueType;
import org.fugerit.java.core.cfg.xml.XmlBeanHelper;
import org.fugerit.java.core.function.SafeFunction;
import org.fugerit.java.core.xml.dom.DOMIO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import lombok.Getter;
import lombok.Setter;
import test.org.fugerit.java.BasicTest;

class TestXmlBeanHelper extends BasicTest {

	public static final String TEST_VALUE = "test1";
	
	// test bean
	public class XmlBeanCheck {
		@Getter @Setter private String field1;
	}
	
	// test bean
	public class XmlBeanText implements TextValueType {
		@Getter @Setter private String textValue;
	}
	
	private boolean setWorker( String xml, String mode, boolean expectedSet ) {
		return SafeFunction.get( () -> {
			try ( Reader reader = new StringReader( xml ) ) {
				Document doc = DOMIO.loadDOMDoc( reader );
				Element root = doc.getDocumentElement();
				XmlBeanCheck check = new XmlBeanCheck();
				Assertions.assertNull( check.getField1() );
				boolean set = XmlBeanHelper.setFromElementSafe( check, root, mode );
				if ( expectedSet ) {
					Assertions.assertEquals( TEST_VALUE , check.getField1() );	
				} else {
					Assertions.assertNull( check.getField1() );	
				}
				return set;
			}	
		} );
	}
	
	@Test
	void testTextValyeType() {
		SafeFunction.apply( () -> {
			try ( Reader reader = new StringReader( "<config>test1</config>" ) ) {
				Document doc = DOMIO.loadDOMDoc( reader );
				Element root = doc.getDocumentElement();
				XmlBeanText check = new XmlBeanText();
				Assertions.assertNull( check.getTextValue() );
				boolean set = XmlBeanHelper.setFromElementSafe( check, root, XmlBeanHelper.MODE_XML_FULL );
				Assertions.assertEquals( TEST_VALUE , check.getTextValue() );	
				Assertions.assertTrue( set );
			}	
		} );
	}
	
	@Test
	void testSetFromElement() {
		SafeFunction.apply( () -> {
			try ( Reader reader = new StringReader( "<config>test1</config>" ) ) {
				Document doc = DOMIO.loadDOMDoc( reader );
				Element root = doc.getDocumentElement();
				XmlBeanText check = new XmlBeanText();
				Assertions.assertNull( check.getTextValue() );
				XmlBeanHelper.setFromElement( check, root );
				Assertions.assertEquals( TEST_VALUE , check.getTextValue() );	
			}	
		} );
	}
	
	@Test
	void testSetFromAttributes() {
		boolean set = this.setWorker( "<config field1='test1'/>" , XmlBeanHelper.MODE_XML_ATTRIBUTES, true );
		Assertions.assertTrue( set );
	}
	
	@Test
	void testSetFromAttributesFull() {
		boolean set = this.setWorker( "<config field1='test1'/>" , XmlBeanHelper.MODE_XML_FULL, true );
		Assertions.assertTrue( set );
	}
	
	@Test
	void testSetFromElements() {
		boolean set = this.setWorker( "<config><field1>test1</field1></config>" , XmlBeanHelper.MODE_XML_ELEMENTS, true );
		Assertions.assertTrue( set );
	}
	
	@Test
	void testSetFromElementsFull() {
		boolean set = this.setWorker( "<config><field1>test1</field1></config>" , XmlBeanHelper.MODE_XML_FULL, true );
		Assertions.assertTrue( set );
	}
	
	@Test
	void testSetFromAttributesFail() {
		boolean set = this.setWorker( "<config fieldDoesNotExist='test1'/>" , XmlBeanHelper.MODE_XML_ATTRIBUTES, false  );
		Assertions.assertFalse( set );
	}
	
	@Test
	void testSetFromElementsFAil() {
		boolean set = this.setWorker( "<config><fieldDoesNotExist>test1</fieldDoesNotExist></config>" , XmlBeanHelper.MODE_XML_ELEMENTS, false  );
		Assertions.assertFalse( set );
	}
	
}

