package org.fugerit.java.ext.doc;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.Reader;
import java.util.Iterator;
import java.util.Properties;

import javax.xml.parsers.SAXParser;

import org.fugerit.java.core.io.StreamIO;
import org.fugerit.java.core.lang.helpers.Result;
import org.fugerit.java.core.xml.XMLValidator;
import org.fugerit.java.core.xml.sax.XMLFactorySAX;
import org.fugerit.java.core.xml.sax.XMLValidatorSAX;
import org.fugerit.java.core.xml.sax.dh.DefaultHandlerComp;
import org.fugerit.java.core.xml.sax.er.ByteArrayEntityResolver;
import org.fugerit.java.ext.doc.itext.ITextDocHandler;
import org.fugerit.java.ext.doc.type.XlsTypeHandler;
import org.fugerit.java.ext.doc.xml.DocContentHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.InputSource;

import com.lowagie.text.Document;
import com.lowagie.text.PageSize;
import com.lowagie.text.html.HtmlWriter;
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.rtf.RtfWriter2;

import jxl.Workbook;

/*
 * 
 * 
 * @author Matteo a.k.a. Fugerit
 *
 */
public class DocFacade {

	private static Logger logger = LoggerFactory.getLogger( DocFacade.class );
	
	private static final Properties DEFAULT_PARAMS = new Properties();
	
	private static void print( PrintStream s, DocContainer docContainer, String indent ) {
		Iterator<DocElement> it = docContainer.docElements();
		while ( it.hasNext() ) {
			DocElement docElement = (DocElement) it.next();
			s.println( indent+docElement );
			if ( docElement instanceof DocContainer ) {
				print( s, (DocContainer)docElement, indent+"  " );
			}
		}
	}
	
	private static void print( PrintStream s, DocContainer docContainer ) {
		print( s, docContainer, "" );
	}
	
	public static void print( PrintStream s, DocBase doc ) {
		print( s, doc.getDocBody() );
	}
	
	private static final String DTD = "/org/fugerit/java/ext/doc/res/doc-1-0.dtd";
	private static final String XSD = "/org/fugerit/java/ext/doc/res/doc-1-0.xsd";
	
	private static byte[] readData( String res, byte[] alreadRead ) {
		byte[] data = alreadRead;
		if ( data != null ) {
			try {
				data = StreamIO.readBytes( DocFacade.class.getResourceAsStream( res ) );	
			} catch (Exception e) {
				logger.warn( "Read error", e );
			}	
		}
		return data;
	}
	
	private static final byte[] XSD_DATA = readData( XSD, null );
	private static final byte[] DTD_DATA = readData( DTD, null );
	
	public static final String PARAM_DEFINITION_MODE = "definition-mode";
	public static final String PARAM_DEFINITION_MODE_XSD = "xsd";
	public static final String PARAM_DEFINITION_MODE_DTD = "dtd";
	public static final String PARAM_DEFINITION_MODE_DEFAULT = PARAM_DEFINITION_MODE_XSD;
	
	public final static String SYSTEM_ID = "http://javacoredoc.fugerit.org";
	
	private static ByteArrayEntityResolver newEntityResolver( Properties params ) {
		byte[] data = null;
		String validatationMode = params.getProperty( PARAM_DEFINITION_MODE, PARAM_DEFINITION_MODE_DEFAULT ) ;
		if ( PARAM_DEFINITION_MODE_DTD.equalsIgnoreCase( validatationMode ) ) {
			data = DTD_DATA;
		} else {
			data = XSD_DATA;
		}
		logger.debug( PARAM_DEFINITION_MODE+" -> "+validatationMode );
		ByteArrayEntityResolver er = new ByteArrayEntityResolver( data, null, SYSTEM_ID );
		return er;
	}
	
	public static boolean validate( Reader is, Properties params ) throws Exception {
		ByteArrayEntityResolver er = newEntityResolver( params );
		XMLValidator validator = XMLValidatorSAX.newInstance( er );
		Result result = validator.validateXML( is );
		return result.isTotalSuccess();
	}
	
	public static DocBase parse( Reader is, DocHelper docHelper ) throws Exception {
		return parse( is, docHelper, DEFAULT_PARAMS );
	}
	
	public static DocBase parse( Reader is, DocHelper docHelper, Properties params ) throws Exception {
		SAXParser parser = XMLFactorySAX.makeSAXParser( false ,  false );
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		StreamIO.pipeStream( DocFacade.class.getResourceAsStream( DTD  ), baos, StreamIO.MODE_CLOSE_BOTH );
		ByteArrayEntityResolver er = newEntityResolver( params );
		DocContentHandler dch =  new DocContentHandler( docHelper );
		DefaultHandlerComp dh = new DefaultHandlerComp( dch );
		dh.setWrappedEntityResolver( er );
		parser.parse( new InputSource(is), dh);
		DocBase docBase = dch.getDocBase();
		is.close();
		return docBase;
	}	
	
	public static DocBase parse( InputStream is, DocHelper docHelper, Properties params ) throws Exception {
		SAXParser parser = XMLFactorySAX.makeSAXParser( true, true );
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		StreamIO.pipeStream( DocFacade.class.getResourceAsStream( DTD  ), baos, StreamIO.MODE_CLOSE_BOTH );
		ByteArrayEntityResolver er = new ByteArrayEntityResolver( baos.toByteArray(), null, SYSTEM_ID );
		DocContentHandler dch = new DocContentHandler( docHelper );
		DefaultHandlerComp dh = new DefaultHandlerComp( dch );
		dh.setWrappedEntityResolver( er );
		parser.parse( is, dh);
		DocBase docBase = dch.getDocBase();
		is.close();
		return docBase;
	}	
	
	public static DocBase parse( Reader is ) throws Exception {
		return parse( is, DocHelper.DEFAULT, DEFAULT_PARAMS );
	}
	
	public static DocBase parse( InputStream is ) throws Exception {
		return parse( is, DocHelper.DEFAULT, DEFAULT_PARAMS );
	}

	
	public static void createXLS( DocBase docBase, OutputStream outputStream ) throws Exception {
		String excelTemplate = docBase.getInfo().getProperty( XlsTypeHandler.PROP_XLS_TEMPLATE );
		Workbook templateXls = null;
		if ( excelTemplate != null ) {
			templateXls = Workbook.getWorkbook( new File( excelTemplate ) );
		}			
		XlsTypeHandler.handleDoc( docBase , outputStream, templateXls );
	}	
	

	
	
	public static void createPDF( DocBase docBase, OutputStream outputStream ) throws Exception {
		
		
		
		String[] margins = docBase.getInfo().getProperty( "margins", "20;20;20;20" ).split( ";" );
		Document document = new Document( PageSize.A4, Integer.parseInt( margins[0] ),
				Integer.parseInt( margins[1] ),
				Integer.parseInt( margins[2] ), 
				Integer.parseInt( margins[3] ) );
		
		// allocate buffer
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		// create pdf writer
		PdfWriter pdfWriter = PdfWriter.getInstance( document, baos );
		// create doc handler
		ITextDocHandler handler = new ITextDocHandler( document, pdfWriter );
		
		if ( "true".equalsIgnoreCase( docBase.getInfo().getProperty( "set-total-page" ) ) ) {
			handler.handleDoc( docBase );
			int totalPageCount = pdfWriter.getCurrentPageNumber()-1;
			document = new Document( PageSize.A4, Integer.parseInt( margins[0] ),
					Integer.parseInt( margins[1] ),
					Integer.parseInt( margins[2] ), 
					Integer.parseInt( margins[3] ) );
			baos = new ByteArrayOutputStream();
			pdfWriter = PdfWriter.getInstance( document, baos );
			handler = new ITextDocHandler(document, pdfWriter, totalPageCount );
		}
		
		handler.handleDoc( docBase );
		
		baos.writeTo( outputStream );
		baos.close();
		outputStream.close();		
	}		
	
	public static void createRTF( DocBase docBase, OutputStream outputStream ) throws Exception {
		String[] margins = docBase.getInfo().getProperty( "margins", "20;20;20;20" ).split( ";" );
		Document document = new Document( PageSize.A4, Integer.parseInt( margins[0] ),
				Integer.parseInt( margins[1] ),
				Integer.parseInt( margins[2] ), 
				Integer.parseInt( margins[3] ) );
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		RtfWriter2 rtfWriter2 = RtfWriter2.getInstance( document, baos );
		ITextDocHandler handler = new ITextDocHandler( document, rtfWriter2 );
		handler.handleDoc( docBase );
		baos.writeTo( outputStream );
		baos.close();
		outputStream.close();		
	}	
	
	public static void createHTML( DocBase docBase, OutputStream outputStream ) throws Exception {
		Document document = new Document( );
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		HtmlWriter.getInstance( document, baos );
		ITextDocHandler handler = new ITextDocHandler( document, ITextDocHandler.DOC_OUTPUT_HTML );
		handler.handleDoc( docBase );
		baos.writeTo( outputStream );
		baos.close();
		outputStream.close();		
	}		
	
}