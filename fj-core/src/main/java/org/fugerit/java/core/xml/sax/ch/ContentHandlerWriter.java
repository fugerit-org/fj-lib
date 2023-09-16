package org.fugerit.java.core.xml.sax.ch;

import java.io.PrintWriter;

import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;

public class ContentHandlerWriter implements ContentHandler {

	
	public ContentHandlerWriter( PrintWriter writer ) {
		this.writer = writer;
		this.depth = 0;
	}
	
	private int depth;
	
	private PrintWriter writer;
	
	private void println( String line ) {
		StringBuilder builder = new StringBuilder();
		for (int k=0; k<this.depth; k++) {
			builder.append( "  " );
		}
		builder.append( line );
		this.writer.println( builder.toString() );
	}
	
	/* (non-Javadoc)
	 * @see org.xml.sax.ContentHandler#characters(char[], int, int)
	 */
	@Override
	public void characters(char[] arg0, int arg1, int arg2) throws SAXException {
		this.ignorableWhitespace( arg0, arg1, arg2 );
	}

	/* (non-Javadoc)
	 * @see org.xml.sax.ContentHandler#endDocument()
	 */
	@Override
	public void endDocument() throws SAXException {
		// do nothing implementation : subclasses should implement it, if needed
	}

	/* (non-Javadoc)
	 * @see org.xml.sax.ContentHandler#endElement(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public void endElement(String arg0, String arg1, String arg2) throws SAXException {
		depth--;
		this.println( "</"+arg2+">" );
	}

	/* (non-Javadoc)
	 * @see org.xml.sax.ContentHandler#endPrefixMapping(java.lang.String)
	 */
	@Override
	public void endPrefixMapping(String arg0) throws SAXException {
		// do nothing implementation : subclasses should implement it, if needed
	}

	/* (non-Javadoc)
	 * @see org.xml.sax.ContentHandler#ignorableWhitespace(char[], int, int)
	 */
	@Override
	public void ignorableWhitespace(char[] arg0, int arg1, int arg2) throws SAXException {
		// do nothing implementation : subclasses should implement it, if needed
	}

	/* (non-Javadoc)
	 * @see org.xml.sax.ContentHandler#processingInstruction(java.lang.String, java.lang.String)
	 */
	@Override
	public void processingInstruction(String arg0, String arg1) throws SAXException {
		// do nothing implementation : subclasses should implement it, if needed
	}

	/* (non-Javadoc)
	 * @see org.xml.sax.ContentHandler#setDocumentLocator(org.xml.sax.Locator)
	 */
	@Override
	public void setDocumentLocator(Locator arg0) {
		// do nothing implementation : subclasses should implement it, if needed
	}

	/* (non-Javadoc)
	 * @see org.xml.sax.ContentHandler#skippedEntity(java.lang.String)
	 */
	@Override
	public void skippedEntity(String arg0) throws SAXException {
		// do nothing implementation : subclasses should implement it, if needed
	}

	/* (non-Javadoc)
	 * @see org.xml.sax.ContentHandler#startDocument()
	 */
	@Override
	public void startDocument() throws SAXException {
		// do nothing implementation : subclasses should implement it, if needed
	}

	/* (non-Javadoc)
	 * @see org.xml.sax.ContentHandler#startElement(java.lang.String, java.lang.String, java.lang.String, org.xml.sax.Attributes)
	 */
	@Override
	public void startElement(String arg0, String arg1, String arg2, Attributes arg3) throws SAXException {
		if ( arg3.getLength() == 0 ) {
			this.println( "<"+arg2+">" );
		} else {
			for (int k=0; k<arg3.getLength(); k++) {
				String line = arg3.getQName( k )+"='"+arg3.getValue( k )+"'";
				if ( k==0 ) {
					line = "<"+arg2+" "+line;
					if ( arg3.getLength() == 1 ) {
						line+= ">";
					}
				} else if ( arg3.getLength()-1==k ) {
					line = "        "+line+">";
				} else {
					line = "        "+line;
				}
				this.println( line );
			}	
		}
		depth++;
	}

	/* (non-Javadoc)
	 * @see org.xml.sax.ContentHandler#startPrefixMapping(java.lang.String, java.lang.String)
	 */
	@Override
	public void startPrefixMapping(String arg0, String arg1) throws SAXException {
		// do nothing implementation : subclasses should implement it, if needed
	}

}
