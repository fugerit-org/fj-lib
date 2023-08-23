package org.fugerit.java.core.javagen;

import java.io.IOException;

public interface JavaGenerator {

	default void generate() throws Exception {
		throw new UnsupportedOperationException( "This method should be implemented" );
	}
	
	public String getContent();
	
	public void write() throws IOException;
	
}
