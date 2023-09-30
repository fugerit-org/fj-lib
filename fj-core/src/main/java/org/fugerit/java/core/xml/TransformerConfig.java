package org.fugerit.java.core.xml;

import java.util.Map;
import java.util.Properties;
import java.util.stream.Stream;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;

public final class TransformerConfig {

	public static final String YES = "yes";
	public static final String NO = "no";
	
	private Properties outputProperties;
	
	private TransformerConfig() {
		this.outputProperties = new Properties();
	}
	
	public static TransformerConfig newConfig() {
		return new TransformerConfig();
	}
	
	public void setAll( Transformer t ) {
		t.setOutputProperties( this.outputProperties );
	}
	
	public Stream<Map.Entry<Object, Object>> stream() {
		return this.outputProperties.entrySet().stream();
	}
	
	public TransformerConfig add( String key, String value ) {
		this.outputProperties.setProperty(key, value);
		return this;
	}
	
	public TransformerConfig omitXmlDeclarationYes() {
		return this.add( OutputKeys.OMIT_XML_DECLARATION, YES );
	}
	
	public TransformerConfig omitXmlDeclarationNo() {
		return this.add( OutputKeys.OMIT_XML_DECLARATION, NO );
	}
	
	public TransformerConfig indentYes() {
		return this.add( OutputKeys.INDENT, YES );
	}
	
	public TransformerConfig indentNo() {
		return this.add( OutputKeys.INDENT, NO );
	}
	
	public TransformerConfig methodXml() {
		return this.method( "xml" );
	}
	
	public TransformerConfig method( String value ) {
		return this.add( OutputKeys.METHOD, value );
	}
	
	public TransformerConfig indentAmount( int indentAmount ) {
		return this.add( "{http://xml.apache.org/xslt}indent-amount", String.valueOf( indentAmount ) );
	}
	
	public static TransformerConfig newIndentConfig( Integer indentAmount ) {
		TransformerConfig config = newConfig().indentYes().methodXml().omitXmlDeclarationYes();
		if ( indentAmount != null ) {
			config = config.indentAmount( indentAmount.intValue() );
		}
		return config;
	}
	
}
