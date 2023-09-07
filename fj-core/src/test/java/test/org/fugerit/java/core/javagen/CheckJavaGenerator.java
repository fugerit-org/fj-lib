package test.org.fugerit.java.core.javagen;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.AbstractList;
import java.util.Properties;

import org.fugerit.java.core.cfg.ConfigException;
import org.fugerit.java.core.javagen.GeneratorNameHelper;
import org.fugerit.java.core.javagen.SimpleJavaGenerator;

public class CheckJavaGenerator extends SimpleJavaGenerator {

	public void init() throws ConfigException {
		this.setExtendsClass( AbstractList.class.getName() );
		this.setImplementsInterface( Serializable.class.getName() );
		super.init( new File( "target/generator_test" ), "test.TestClass", STYLE_CLASS, new Properties() );
	}

	@Override
	public void generateBody() throws IOException {
		this.addSerialVerUID();
		this.printlnWithTabs( 2, "//"+GeneratorNameHelper.toClassName( this.getPackageName() ) );
		this.printlnWithTabs( 2, "private String "+GeneratorNameHelper.toPropertyName( "test" ) );
	}

}
