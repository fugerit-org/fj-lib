package test.org.fugerit.java.core.xml.dom;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;

import org.fugerit.java.core.lang.helpers.ClassHelper;
import org.fugerit.java.core.xml.TransformerConfig;
import org.fugerit.java.core.xml.XMLException;
import org.fugerit.java.core.xml.XMLWhiteSpaceRemove;
import org.fugerit.java.core.xml.dom.DOMIO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TestDOMClean {

	private static final String INPUT_FILE_XML = "dom_clean1.xml";
	
	private void testWorker( String testFile, File outFile, TransformerConfig config) throws IOException, XMLException {
		try ( InputStream is = ClassHelper.loadFromDefaultClassLoader( "core/xml/dom/"+testFile );
				Writer writer = new FileWriter( outFile ) ) {
			Document doc = DOMIO.loadDOMDoc( is );
			config.stream().forEach( e -> log.info( "outputProperty {} -> {}", e.getKey(), e.getValue() ) );
			XMLWhiteSpaceRemove.cleanBlankNodes(doc, writer, TransformerConfig.newIndentConfig( 5 ));
		}
	}
	
	@Test
	void testClean1() throws IOException, XMLException {
		String testFile = INPUT_FILE_XML;
		File outFile =  new File( "target", "cleaned_a_"+testFile );
		try ( InputStream is = ClassHelper.loadFromDefaultClassLoader( "core/xml/dom/"+testFile );
				Writer writer = new FileWriter( outFile ) ) {
			Document doc = DOMIO.loadDOMDoc( is );
			XMLWhiteSpaceRemove.cleanBlankNodesIndent2(doc, writer);
		}
		Assertions.assertTrue( outFile.exists() );
	}
	
	@Test
	void testClean2() throws IOException, XMLException {
		String testFile = "dom_clean1.xml";
		File outFile =  new File( "target", "cleaned_b_"+testFile );
		this.testWorker(testFile, outFile, TransformerConfig.newIndentConfig( 5 ));
		Assertions.assertTrue( outFile.exists() );
	}
	
	@Test
	void testClean3() throws IOException, XMLException {
		String testFile = "dom_clean1.xml";
		File outFile =  new File( "target", "cleaned_c_"+testFile );
		this.testWorker(testFile, outFile, TransformerConfig.newConfig().indentNo().omitXmlDeclarationNo());
		Assertions.assertTrue( outFile.exists() );
	}
	
}
