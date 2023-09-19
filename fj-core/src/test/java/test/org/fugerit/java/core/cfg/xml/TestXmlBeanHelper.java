package test.org.fugerit.java.core.cfg.xml;

import java.io.Reader;
import java.io.StringReader;

import org.fugerit.java.core.cfg.xml.TextValueType;
import org.fugerit.java.core.cfg.xml.XmlBeanHelper;
import org.fugerit.java.core.function.SafeFunction;
import org.fugerit.java.core.xml.dom.DOMIO;
import org.junit.Assert;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import lombok.Getter;
import lombok.Setter;
import test.org.fugerit.java.BasicTest;

public class TestXmlBeanHelper extends BasicTest {

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
				Assert.assertNull( check.getField1() );
				boolean set = XmlBeanHelper.setFromElementSafe( check, root, mode );
				if ( expectedSet ) {
					Assert.assertEquals( TEST_VALUE , check.getField1() );	
				} else {
					Assert.assertNull( check.getField1() );	
				}
				return set;
			}	
		} );
	}
	
	@Test
	public void testTextValyeType() {
		SafeFunction.apply( () -> {
			try ( Reader reader = new StringReader( "<config>test1</config>" ) ) {
				Document doc = DOMIO.loadDOMDoc( reader );
				Element root = doc.getDocumentElement();
				XmlBeanText check = new XmlBeanText();
				Assert.assertNull( check.getTextValue() );
				boolean set = XmlBeanHelper.setFromElementSafe( check, root, XmlBeanHelper.MODE_XML_FULL );
				Assert.assertEquals( TEST_VALUE , check.getTextValue() );	
				Assert.assertTrue( set );
			}	
		} );
	}
	
	@Test
	public void testSetFromElement() {
		SafeFunction.apply( () -> {
			try ( Reader reader = new StringReader( "<config>test1</config>" ) ) {
				Document doc = DOMIO.loadDOMDoc( reader );
				Element root = doc.getDocumentElement();
				XmlBeanText check = new XmlBeanText();
				Assert.assertNull( check.getTextValue() );
				XmlBeanHelper.setFromElement( check, root );
				Assert.assertEquals( TEST_VALUE , check.getTextValue() );	
			}	
		} );
	}
	
	@Test
	public void testSetFromAttributes() {
		boolean set = this.setWorker( "<config field1='test1'/>" , XmlBeanHelper.MODE_XML_ATTRIBUTES, true );
		Assert.assertTrue( set );
	}
	
	@Test
	public void testSetFromAttributesFull() {
		boolean set = this.setWorker( "<config field1='test1'/>" , XmlBeanHelper.MODE_XML_FULL, true );
		Assert.assertTrue( set );
	}
	
	@Test
	public void testSetFromElements() {
		boolean set = this.setWorker( "<config><field1>test1</field1></config>" , XmlBeanHelper.MODE_XML_ELEMENTS, true );
		Assert.assertTrue( set );
	}
	
	@Test
	public void testSetFromElementsFull() {
		boolean set = this.setWorker( "<config><field1>test1</field1></config>" , XmlBeanHelper.MODE_XML_FULL, true );
		Assert.assertTrue( set );
	}
	
	@Test
	public void testSetFromAttributesFail() {
		boolean set = this.setWorker( "<config fieldDoesNotExist='test1'/>" , XmlBeanHelper.MODE_XML_ATTRIBUTES, false  );
		Assert.assertFalse( set );
	}
	
	@Test
	public void testSetFromElementsFAil() {
		boolean set = this.setWorker( "<config><fieldDoesNotExist>test1</fieldDoesNotExist></config>" , XmlBeanHelper.MODE_XML_ELEMENTS, false  );
		Assert.assertFalse( set );
	}
	
}

