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
		String prefix = "";
		for (int k=0; k<this.depth; k++) {
			prefix+= "  ";
		}
		this.writer.println( prefix+line );
	}
	
	/* (non-Javadoc)
	 * @see org.xml.sax.ContentHandler#characters(char[], int, int)
	 */
	public void characters(char[] arg0, int arg1, int arg2) throws SAXException {
		this.ignorableWhitespace( arg0, arg1, arg2 );
	}

	/* (non-Javadoc)
	 * @see org.xml.sax.ContentHandler#endDocument()
	 */
	public void endDocument() throws SAXException {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see org.xml.sax.ContentHandler#endElement(java.lang.String, java.lang.String, java.lang.String)
	 */
	public void endElement(String arg0, String arg1, String arg2) throws SAXException {
		depth--;
		this.println( "</"+arg2+">" );
	}

	/* (non-Javadoc)
	 * @see org.xml.sax.ContentHandler#endPrefixMapping(java.lang.String)
	 */
	public void endPrefixMapping(String arg0) throws SAXException {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see org.xml.sax.ContentHandler#ignorableWhitespace(char[], int, int)
	 */
	public void ignorableWhitespace(char[] arg0, int arg1, int arg2) throws SAXException {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see org.xml.sax.ContentHandler#processingInstruction(java.lang.String, java.lang.String)
	 */
	public void processingInstruction(String arg0, String arg1) throws SAXException {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see org.xml.sax.ContentHandler#setDocumentLocator(org.xml.sax.Locator)
	 */
	public void setDocumentLocator(Locator arg0) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see org.xml.sax.ContentHandler#skippedEntity(java.lang.String)
	 */
	public void skippedEntity(String arg0) throws SAXException {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see org.xml.sax.ContentHandler#startDocument()
	 */
	public void startDocument() throws SAXException {
		
	}

	/* (non-Javadoc)
	 * @see org.xml.sax.ContentHandler#startElement(java.lang.String, java.lang.String, java.lang.String, org.xml.sax.Attributes)
	 */
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
	public void startPrefixMapping(String arg0, String arg1) throws SAXException {
		// TODO Auto-generated method stub
		
	}

}
