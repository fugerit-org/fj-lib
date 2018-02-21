package org.fugerit.java.ext.doc.xsl;

import java.io.File;
import java.io.FileInputStream;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

/*
 * 
 *
 * @author mfranci
 *
 */
public class XsltFacade {

	public static void main( String[] args ) {
		try {
			StreamResult result = new StreamResult( System.out );
			TransformerFactory factory = TransformerFactory.newInstance();
			StreamSource xsl = new StreamSource( new FileInputStream( new File( "test/doc1.xsl" ) ) );
			StreamSource source = new StreamSource( new FileInputStream( new File( "test/doc1.xml" ) ) );
			Transformer transformer = factory.newTransformer( xsl );
			transformer.transform(source, result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
