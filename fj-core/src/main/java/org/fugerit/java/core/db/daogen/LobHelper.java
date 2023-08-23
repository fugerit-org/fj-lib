package org.fugerit.java.core.db.daogen;

import java.sql.Blob;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.SQLException;

public class LobHelper {

	private LobHelper() {}
	
	public static Clob createClob( Connection conn, CharArrayDataHandler dh ) throws SQLException {
		Clob c = null;
		if ( dh != null ) {
			c = conn.createClob();
			c.setString(1, new String( dh.getData() ) );
		}
		return c;
	}
	
	public static Blob createBlob( Connection conn, ByteArrayDataHandler dh ) throws SQLException {
		Blob b = null;
		if ( dh != null ) {
			b = conn.createBlob();
			b.setBytes(1, dh.getData());
		}
		return b;
	}
	
}
