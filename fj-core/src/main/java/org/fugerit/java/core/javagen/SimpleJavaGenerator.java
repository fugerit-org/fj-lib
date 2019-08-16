package org.fugerit.java.core.javagen;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Iterator;
import java.util.Properties;

import org.fugerit.java.core.lang.helpers.StringUtils;

public abstract class SimpleJavaGenerator extends BasicJavaGenerator {

	public static final String PROP_VERSION = "gen-version";
	public static final String PROP_AUTHOR = "gen-author";
	public static final String PROP_CLASS_COMMENT = "gen-class-comment";
	
	public static final String STYLE_CLASS = "class";
	public static final String STYLE_INTERFACE = "interface";
	
	public SimpleJavaGenerator(File sourceFolder, String fullObjectBName, String javaStyle, Properties config) {
		super(sourceFolder, fullObjectBName);
		this.setJavaStyle( javaStyle );
		this.config = config;
	}

	public static final String CUSTOM_CODE_START = "// custom code start ( code above here will be overwritten )";
	public static final String CUSTOM_CODE_END = "// custom code end ( code below here will be overwritten )";

	public static final String CUSTOM_IMPORT_START = "// custom import start ( code above here will be overwritten )";
	public static final String CUSTOM_IMPORT_END = "// custom import end ( code below here will be overwritten )";
	
	private String javaStyle;
	
	private String extendsClass;
	
	private String implementsInterface;
	
	private Properties config;

	protected Properties getConfig() {
		return config;
	}

	protected void setConfig(Properties config) {
		this.config = config;
	}

	protected String getJavaStyle() {
		return javaStyle;
	}

	protected void setJavaStyle(String javaStyle) {
		this.javaStyle = javaStyle;
	}

	protected String getExtendsClass() {
		return extendsClass;
	}

	protected void setExtendsClass(String extendsClass) {
		this.extendsClass = extendsClass;
	}

	protected String getImplementsInterface() {
		return implementsInterface;
	}

	protected void setImplementsInterface(String implementsInterface) {
		this.implementsInterface = implementsInterface;
	}

	@Override
	public void generate() throws Exception {
		this.getWriter().println( "package "+this.getPackageName()+";" );
		this.getWriter().println();
		if ( !this.getImportList().isEmpty() ) {
			Iterator<String> itImport = this.getImportList().iterator();
			while ( itImport.hasNext() ) {
				this.getWriter().println( "import "+itImport.next()+";" );
			}
			this.getWriter().println();
		}
		this.customPartWorker( CUSTOM_IMPORT_START , CUSTOM_IMPORT_END, "" );
		String version = this.getConfig().getProperty( PROP_VERSION );
		String author = this.getConfig().getProperty( PROP_AUTHOR );
		String comments = this.getConfig().getProperty( PROP_CLASS_COMMENT );
		this.getWriter().println( "/**" );
		if ( version != null ) {
			this.getWriter().println( " * "+this.getJavaName()+", version : "+version );
		} else {
			this.getWriter().println( " * "+this.getJavaName()+", version : auto generated on "+new Timestamp( System.currentTimeMillis() ) );
		}
		if ( StringUtils.isNotEmpty( comments ) ) {
			this.getWriter().println( " *" );
			this.getWriter().println( " * note: "+comments );
		}
		if ( author != null ) {
			this.getWriter().println( " *" );
			this.getWriter().println( " * author: "+author );
		}
		this.getWriter().println( " *" );
		this.getWriter().println( " * warning!: auto generated object, insert custom code only between comments :" );
		this.getWriter().println( " * "+CUSTOM_CODE_START );
		this.getWriter().println( " * "+CUSTOM_CODE_END );
		this.getWriter().println( " */" );
		String impl = "";
		if ( StringUtils.isNotEmpty( this.extendsClass ) ) {
			impl+= " extends "+this.extendsClass;
		}
		// interfaces
		if ( StringUtils.isNotEmpty( this.implementsInterface ) ) {
			impl+= " implements "+this.implementsInterface;
		}
		this.getWriter().println( "public "+this.getJavaStyle()+" "+this.getJavaName()+impl+" {" );
		this.getWriter().println();
		this.customPartWorker( CUSTOM_CODE_START , CUSTOM_CODE_END, "	" );
		this.generateBody();
		this.getWriter().println( "}" );
	}
	
	public abstract void generateBody() throws Exception;
	
	protected void customPartWorker( String startTag, String endTag, String indent ) throws FileNotFoundException, IOException {
		customPartWorker( this.getJavaFile(), this.getWriter(), startTag, endTag, indent );
	}
	
	
}
