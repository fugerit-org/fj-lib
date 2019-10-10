package org.fugerit.java.core.db.helpers;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.sql.Blob;
import java.sql.SQLException;

import org.fugerit.java.core.db.daogen.ByteArrayDataHandler;
import org.fugerit.java.core.io.StreamIO;

/**
 * 
 *
 * @author Fugerit
 *
 */
public class BlobData implements Serializable {

	/*
	 * 
	 */
	private static final long serialVersionUID = 5664628013389279648L;

	public static BlobData valueOf( Blob b ) throws SQLException {
		BlobData blobData = new BlobData();
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			InputStream is = b.getBinaryStream();
			StreamIO.pipeStream( is , baos, StreamIO.MODE_CLOSE_BOTH );
			is.close();
			blobData.setData( baos.toByteArray() );
		} catch (Exception e) {
			throw ( new SQLException( e.toString() ) );
		}
		return blobData;
	}
	
	public static BlobData valueOf( ByteArrayDataHandler b ) {
		BlobData blobData = new BlobData();
		blobData.setData( b.getData() );
		return blobData;
	}
	
	private byte[] data;

	/*
	 * @return the data
	 */
	public byte[] getData() {
		return data;
	}

	/*
	 * @param data the data to set
	 */
	public void setData(byte[] data) {
		this.data = data;
	}
	
}
