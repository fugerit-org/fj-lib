package org.fugerit.java.core.xml;

import java.io.Reader;
import java.io.Writer;

import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.fugerit.java.core.io.helper.StreamHelper;
import org.w3c.dom.Node;

public class XMLWhiteSpaceRemove {

	public static final String PATH_REMOVE_WHITESPACE_NODES_XSLT =  StreamHelper.PATH_CLASSLOADER+"core/xml/remove_whitespace_nodes.xslt";
	
	private XMLWhiteSpaceRemove() {}
	
	public static void cleanBlankNodesIndent2( Node node, Writer writer ) throws XMLException {
		cleanBlankNodes( node, writer, TransformerConfig.newIndentConfig( 2 ));
	}
	
	public static void cleanBlankNodes( Node node, Writer writer, TransformerConfig config ) throws XMLException {
		cleanBlankNodes( node, new StreamResult( writer ), config);
	}
	
	public static void cleanBlankNodes( Node node, Result result, TransformerConfig config ) throws XMLException {
		cleanBlankNodes( new DOMSource( node ), result, config);
	}
	
	public static void cleanBlankNodes( Source source, Result result, TransformerConfig config ) throws XMLException {
		cleanBlankNodes(source, result, config, PATH_REMOVE_WHITESPACE_NODES_XSLT);
	}
	
	public static void cleanBlankNodes( Source source, Result result, TransformerConfig config, String xlstPath ) throws XMLException {
		XMLException.apply( () -> {
			try ( Reader xlstClean = StreamHelper.resolveReader(xlstPath) ) {
				Transformer t = TransformerXML.newTransformer( new StreamSource( xlstClean ), config );
				t.transform( source , result );
			}	
		});
	}
	
}
