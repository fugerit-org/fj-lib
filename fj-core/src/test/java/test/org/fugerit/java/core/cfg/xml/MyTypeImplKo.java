package test.org.fugerit.java.core.cfg.xml;

import org.fugerit.java.core.cfg.ConfigRuntimeException;

public class MyTypeImplKo implements MyType {

	private static final boolean FAIL = true;
	
	public MyTypeImplKo() {
		if ( FAIL ) {
			throw new ConfigRuntimeException( "Creation fail for test" );
		}
	}
	
}
