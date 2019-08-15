package org.fugerit.java.core.db.helpers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class SQLScriptReader implements AutoCloseable {

	private BufferedReader reader;
	
	private String nextScript;
	
	private boolean skipSemicolon;
	
	public SQLScriptReader( InputStream is ) {
		this.reader = new BufferedReader( new InputStreamReader( is ) );
		this.skipSemicolon = false;
		this.nextScript = null;
	}

	@Override
	public void close() throws Exception {
		this.reader.close();
	}
	
	public boolean hasNext() throws IOException {
		this.nextScript = null;
		StringBuilder currentScript = new StringBuilder();
		char[] c = new char[1];
		int read = this.reader.read( c );
		while ( read > 0 && this.nextScript == null ) {
			char current = c[0];
			if ( current == '\'' ) {
				this.skipSemicolon = !this.skipSemicolon;
			}
			if ( current == ';' && !skipSemicolon ) {
				this.nextScript = currentScript.toString();
			} else {
				currentScript.append(current );
				read = this.reader.read( c );
			}
		}
		return (this.nextScript != null);
	}
	
	public String next() {
		return this.nextScript;
	}
	
}
