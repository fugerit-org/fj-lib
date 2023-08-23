package org.fugerit.java.core.javagen;

import java.io.IOException;

public interface JavaGenerator {

	public void generate() throws Exception;
	
	public String getContent();
	
	public void write() throws IOException;
	
}
