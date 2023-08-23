package org.fugerit.java.core.javagen;

import java.io.IOException;

public interface JavaGenerator {

	void generate() throws IOException;
	
	String getContent();
	
	void write() throws IOException;
	
}
