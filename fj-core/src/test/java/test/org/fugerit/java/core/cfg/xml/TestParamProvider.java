package test.org.fugerit.java.core.cfg.xml;

import org.fugerit.java.core.util.regex.SimpleParamProvider;

public class TestParamProvider extends SimpleParamProvider {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1549784901025171679L;

	public TestParamProvider() {
		super();
		this.getProperties().setProperty( "base-conf-path" , "core/cfg/xml/props" );
	}
	
}
