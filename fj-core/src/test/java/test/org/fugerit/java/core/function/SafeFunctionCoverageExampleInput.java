package test.org.fugerit.java.core.function;

import java.io.IOException;

public abstract class SafeFunctionCoverageExampleInput {

	public static SafeFunctionCoverageExampleInput newInput( String value ) {
		return new SafeFunctionCoverageExampleInput() {
			@Override
			public String getValue() throws IOException {
				if ( value == null ) {
					throw new IOException( "Null value!" );
				}
				return value;
			}
		};
	}

	public abstract String getValue() throws IOException;
	
}
