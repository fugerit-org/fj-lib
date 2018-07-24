package org.fugerit.java.core.fixed.parser.helper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;

import org.fugerit.java.core.fixed.parser.FixedFieldFileDescriptor;

public class ReaderFixedFieldFileReader extends FixedFieldFileReaderAbstract {
	
	private BufferedReader br;
	
	private String currentLine;
	
	private String currentEndline;
	
	public ReaderFixedFieldFileReader( FixedFieldFileDescriptor descriptor, Reader reader ) throws IOException {
		super( descriptor );
		if ( reader instanceof BufferedReader ) {
			this.br = (BufferedReader) reader;	
		} else {
			this.br = new BufferedReader( reader );
		}
	}
 	
	public boolean hasNext() throws IOException {
		if ( this.getDescriptor().isCustomEndlineActive() ) {
			char[] buffer = new char[ this.getDescriptor().getCheckLengh() + this.getEndline().length() ];
			int read = this.br.read( buffer );
			if ( read >= 0 ) {
				this.currentLine = new String( buffer, 0, buffer.length-this.getEndline().length() );
				this.currentEndline = new String( buffer, buffer.length-this.getEndline().length(), this.getEndline().length() );
				if ( this.getDescriptor().isCustomEndlineActive() ) {
					if ( this.currentLine.length() != this.getDescriptor().getCheckLengh() ) {
						this.addRecordLenthError( this.currentLine.length() );
					}
					if ( !this.getEndline().equals( this.currentEndline ) ) {
						this.addEndlineError( this.currentEndline );
					} 
					if ( this.getGenericValidationErrors().size() > 0 ) {
						this.currentLine = null;
					}
				}
				
			} else {
				this.currentLine = null;
				this.currentEndline = null;
			}
		} else {
			this.currentLine = this.br.readLine();
		}
		if ( this.currentLine != null ) {
			this.rowNumber++;
		}
		return this.currentLine != null;
	}

	@Override
	public void close() throws IOException {
		this.br.close();
	}

	@Override
	public String nextLine() {
		return this.currentLine;
	}
	
}
