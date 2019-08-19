package org.fugerit.java.core.db.daogen;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.sql.Blob;

import org.fugerit.java.core.io.StreamIO;

public abstract class ByteArrayDataHandler {

	public abstract InputStream getInputStream();
	
	public abstract byte[] getData();

	public static ByteArrayDataHandler newHandlerByte( byte[] data ) throws Exception {
		return new PreloadByteArrayDataHandler( data );
	}
	
	public static ByteArrayDataHandler newHandlerDefault( Blob b ) throws Exception {
		return newHandlerPreload(b);
	}
	
	public String toString() {
		return new String( this.getData() );
	}
	
	public static ByteArrayDataHandler newHandlerPreload( final Blob b ) throws Exception {
		ByteArrayDataHandler handler = null;
		if ( b.length() > 0 ) {
			byte[] data = StreamIO.readBytes( b.getBinaryStream() );
			if ( data != null && !( data.length == 1 && data[0] == 0) ) {
				handler = new PreloadByteArrayDataHandler( data );
			}
		}
		return handler;
	}
	
}

class PreloadByteArrayDataHandler extends ByteArrayDataHandler {

	private byte[] data;
	
	public PreloadByteArrayDataHandler(byte[] data) {
		super();
		this.data = data;
	}

	@Override
	public InputStream getInputStream() {
		return new ByteArrayInputStream( this.data );
	}

	@Override
	public byte[] getData() {
		return this.data;
	}
	
}