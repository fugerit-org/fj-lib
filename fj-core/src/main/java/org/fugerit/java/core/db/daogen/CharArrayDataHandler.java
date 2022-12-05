package org.fugerit.java.core.db.daogen;

import java.io.CharArrayReader;
import java.io.CharArrayWriter;
import java.io.Reader;
import java.io.Serializable;
import java.sql.Clob;

import org.fugerit.java.core.io.StreamIO;

public abstract class CharArrayDataHandler {

	public abstract Reader toReader();
	
	public abstract char[] getData();

	public static CharArrayDataHandler newHandlerByte( char[] data ) throws Exception {
		CharArrayDataHandler r = null;
		if ( data != null ) {
			r = new PreloadCharArrayDataHandler( data );
		}
		return r;
	}
	
	public static CharArrayDataHandler newHandlerDefault( Clob c ) throws Exception {
		return newHandlerPreload( c );
	}
	
	@Override
	public String toString() {
		return new String( this.getData() );
	}
	
	public static CharArrayDataHandler newHandlerPreload( Clob c ) throws Exception {
		CharArrayDataHandler handler = null;
		if ( c != null && c.length() > 0 ) {
			CharArrayWriter writer = new CharArrayWriter();
			StreamIO.pipeChar( c.getCharacterStream(), writer, StreamIO.MODE_CLOSE_BOTH );
			char[] data = writer.toCharArray();
			if ( data != null && data.length > 0 ) {
				handler = new PreloadCharArrayDataHandler( data );
			}
		}
		return handler;
	}
	
}

class PreloadCharArrayDataHandler extends CharArrayDataHandler implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4655970671421459368L;
	
	private char[] data;
	
	public PreloadCharArrayDataHandler(char[] data) {
		super();
		this.data = data;
	}

	@Override
	public Reader toReader() {
		return new CharArrayReader( this.data );
	}

	@Override
	public char[] getData() {
		return this.data;
	}
	
}
