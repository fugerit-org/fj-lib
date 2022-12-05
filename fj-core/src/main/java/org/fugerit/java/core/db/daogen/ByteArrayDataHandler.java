package org.fugerit.java.core.db.daogen;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.sql.Blob;

import org.fugerit.java.core.io.StreamIO;

public abstract class ByteArrayDataHandler {

	public abstract InputStream toInputStream();
	
	public abstract byte[] getData();

	public static ByteArrayDataHandler newHandlerByte( byte[] data ) throws Exception {
		ByteArrayDataHandler r = null;
		if ( data != null ) {
			r = new PreloadByteArrayDataHandler( data );
		}
		return r;
	}
	
	public static ByteArrayDataHandler newHandlerDefault( Blob b ) throws Exception {
		return newHandlerPreload(b);
	}
	
	@Override
	public String toString() {
		return new String( this.getData() );
	}
	
	public static ByteArrayDataHandler newHandlerPreload( final Blob b ) throws Exception {
		ByteArrayDataHandler handler = null;
		if ( b != null && b.length() > 0 ) {
			byte[] data = StreamIO.readBytes( b.getBinaryStream() );
			if ( data != null && !( data.length == 1 && data[0] == 0) ) {
				handler = new PreloadByteArrayDataHandler( data );
			}
		}
		return handler;
	}
	
}

class PreloadByteArrayDataHandler extends ByteArrayDataHandler implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 181786331304919843L;
	
	private byte[] data;
	
	public PreloadByteArrayDataHandler(byte[] data) {
		super();
		this.data = data;
	}

	@Override
	public InputStream toInputStream() {
		return new ByteArrayInputStream( this.data );
	}

	@Override
	public byte[] getData() {
		return this.data;
	}
	
}
