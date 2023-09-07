package org.fugerit.java.core.io;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.function.Supplier;

import org.fugerit.java.core.cfg.ConfigRuntimeException;

public class SafeIO {

	private SafeIO() {}
	
	public static String readStringStream( Supplier<InputStream> isp ) {
		return readString(() -> new InputStreamReader( isp.get() ) );
	}
	
	public static String readString( Supplier<Reader> isp ) {
		String data = null;
		try ( Reader reader = isp.get() ) {
			data = StreamIO.readString(reader);
		} catch (IOException | ConfigRuntimeException e) {
			throw ConfigRuntimeException.convertExMethod( "readString" , e );
		}
		return data;
	}
	
	public static byte[] readBytes( Supplier<InputStream> isp ) {
		byte[] data = null;
		try ( InputStream is = isp.get() ) {
			data = StreamIO.readBytes(is);
		} catch (IOException | ConfigRuntimeException e) {
			throw ConfigRuntimeException.convertExMethod( "readBytes" , e );
		}
		return data;
	}
	
}
