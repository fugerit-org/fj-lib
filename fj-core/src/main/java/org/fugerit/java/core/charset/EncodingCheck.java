/*
 *
		Fugerit Java Library is distributed under the terms of :

                                 Apache License
                           Version 2.0, January 2004
                        http://www.apache.org/licenses/


	Full license :
		http://www.apache.org/licenses/LICENSE-2.0
		
	Project site: 
		https://www.fugerit.org/
	
	SCM site :
		https://github.com/fugerit79/fj-lib
	
 *
 */
package org.fugerit.java.core.charset;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CodingErrorAction;
import java.util.Arrays;

import lombok.extern.slf4j.Slf4j;

/**
 * <p>Library to check if and encoding is respected.</p>
 * 
 * @author Fugerit
 *
 */
@Slf4j
public class EncodingCheck {

	/**
	 * Buffer size for reading the input
	 */
	public static final int READ_BUFFER_SIZE = 1024 * 1024;
	
	private EncodingCheck() {}
	
	/**
	 * <p>Check encoding from an input stream.
	 * Reading is buffered.
	 * </p>
	 * 
	 * @param is			the source stream
	 * @param encoding		encoding to check
	 * @return				<code>true</code> if all bytes belong to give encoding, <code>false</code> otherwise.
	 * @throws IOException	in case issues arise
	 */
	public static boolean checkEncoding( final InputStream is, final String encoding ) throws IOException {
		boolean result = true;
		byte[] buffer = new byte[ READ_BUFFER_SIZE ];
		int read = is.read( buffer );
		int totalSize = 0;
		while ( read > 0 && result ) {
			totalSize+= read;
			byte[] checkData = buffer;
			if ( read != buffer.length ) {
				checkData = Arrays.copyOf( buffer , read );
			}
			result = checkEncoding( checkData , encoding );
			read = is.read( buffer );
		}
		log.debug( "totalSize : {}", totalSize );
        return result;
	}
	
	/**
	 * <p>Check if every byte in an array belong to a given encoding.</p>
	 * 
	 * @param bytes			the source byte array
	 * @param encoding		encoding to check
	 * @return				<code>true</code> if all bytes belong to give encoding, <code>false</code> otherwise.
	 */
	public static boolean checkEncoding( final byte[] bytes, final String encoding ) {
		boolean result = false;
        final CharsetDecoder decoder = Charset.forName(encoding).newDecoder();
        decoder.onMalformedInput(CodingErrorAction.REPORT);
        decoder.onUnmappableCharacter(CodingErrorAction.REPORT);
        final ByteBuffer buffer = ByteBuffer.wrap(bytes);
        try {
            decoder.decode(buffer);
            result = true;
        } catch (final CharacterCodingException e) {
        	log.debug( "Wrong encoding : {}", e.toString() );
        	result = false;
        }
        return result;
    }
	
}
