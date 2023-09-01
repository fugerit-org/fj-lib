package test.org.fugerit.java.core.xml.config;

import java.util.ArrayList;

import org.fugerit.java.core.cfg.ConfigRuntimeException;

public class MyCollectionFail extends ArrayList<String> {

	private static final long serialVersionUID = 528424023650086821L;

	private static final boolean FAIL = true;
	
	public MyCollectionFail() {
		if ( FAIL ) {
			throw new ConfigRuntimeException( "Fail on constructor" );	
		}
	}
	
}
