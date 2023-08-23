package org.fugerit.java.core.fixed.parser.helper;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;

import org.fugerit.java.core.fixed.parser.FixedFieldFileDescriptor;

public class StreamFixedFieldFileReader extends FixedFieldFileReaderAbstract {
	
	private InputStream is;
	
	private BufferedReader br;
	
	private byte[] linebuffer;
	
	public StreamFixedFieldFileReader( FixedFieldFileDescriptor descriptor, InputStream is ) throws IOException {
		super( descriptor );
		this.is = new BufferedInputStream( is );
		this.br = new BufferedReader( new InputStreamReader( is ) );
	}

	private void handleRead( byte[] buffer ) {
		byte[] currentendline = Arrays.copyOfRange( buffer , buffer.length-this.getEndline().length(), buffer.length );
		if ( this.getDescriptor().isCustomEndlineActive() ) {
			if ( this.linebuffer.length != this.getDescriptor().getCheckLengh() ) {
				this.addRecordLenthError( this.linebuffer.length );
			}
			boolean found = false;
			for ( int k=0; k<currentendline.length && !found; k++ ) {
				if ( currentendline[k] != this.getEndline().getBytes()[k] ) {
					this.addEndlineError( new String( currentendline ) );
					found = true;
				} 	
			}
			if ( this.getGenericValidationErrors().size() > 0 ) {
				this.linebuffer = null;
			}
		}
	}
	
	@Override
	public boolean hasNext() throws IOException {
		if ( this.getDescriptor().isCustomEndlineActive() ) {
			byte[] buffer = new byte[ this.getDescriptor().getCheckLengh() + this.getEndline().length() ];
			int read = this.is.read( buffer );
			if ( read > 0 ) {
				this.linebuffer = Arrays.copyOfRange( buffer , 0, buffer.length-this.getEndline().length() );
				this.handleRead(buffer);
			} else {
				this.linebuffer = null;
			}
		} else {
			String line = this.br.readLine();
			if (  line != null ) {
				this.linebuffer = line.getBytes();	
			} else {
				this.linebuffer = null;
			}
		}
		if ( this.linebuffer != null ) {
			this.rowNumber++;
		}
		return this.linebuffer != null;
	}

	@Override
	public void close() throws IOException {
		this.is.close();
	}

	@Override
	public String nextLine() {
		return new String( linebuffer );
	}
	
}