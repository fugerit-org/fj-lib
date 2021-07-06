package org.fugerit.java.ext.doc.filter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fugerit.java.core.io.StreamIO;
import org.fugerit.java.core.log.BasicLogObject;
import org.fugerit.java.core.log.LogUtils;
import org.fugerit.java.core.web.servlet.config.ConfigContext;
import org.fugerit.java.core.web.servlet.context.RequestContext;
import org.fugerit.java.ext.doc.config.DocConstants;
import org.fugerit.java.ext.doc.config.DocServletConfig;
import org.fugerit.java.ext.doc.filter.facade.DocRequestConfig;
import org.fugerit.java.ext.doc.filter.facade.DocRequestPushbody;
import org.w3c.dom.Element;

public class DocRequestFacade extends BasicLogObject {

	private DocRequestConfig docRequestConfig;
	
	private DocRequestPushbody docRequestPushbody;
	
	private void log( String uri, String render, String truncate, String fileName, String name, String type, String outMode ) {
		StringBuilder buffer = new StringBuilder();
		buffer.append( "start processing " );
		buffer.append( DocServletConfig.VERSION );
		LogUtils.appendPropDefault( buffer , "out-mode", outMode );
		LogUtils.appendPropDefault( buffer , "uri", uri );
		LogUtils.appendPropDefault( buffer , "render-type", render );
		LogUtils.appendPropDefault( buffer , "truncate", truncate );
		LogUtils.appendPropDefault( buffer , "name", name );
		LogUtils.appendPropDefault( buffer , "type", type );
		LogUtils.appendPropDefault( buffer , "filename", fileName );
		this.getLogger().info( buffer.toString() );
	}
	
	public void handleDoc( RequestContext rq ) throws ServletException, IOException {
		
		HttpServletRequest request = rq.getRequest();
		HttpServletResponse response = rq.getResponse();
		
		request.setAttribute( "docConsts" , DocConstants.DEF );
		String uri = request.getRequestURI();
		
		String render = request.getParameter( "render-type" );
		String truncate = request.getParameter( "truncate" );
		String data = uri.substring( uri.lastIndexOf( "/" )+1 );
		String name = data;
		String fileName = data;
		String type = render;
		int index = data.lastIndexOf( "." );
		if ( render == null ) {
			name = data.substring( 0, index );
			type = data.substring( index+1 );
		} else if ( index != -1 ) {
			name = data.substring( 0, index );	
		}
		if ( truncate != null ) {
			name = name.substring( 0, Integer.parseInt( truncate ) );
		}
		
		log(uri, render, truncate, fileName, fileName, type, this.getDocRequestConfig().getOutMode() );
		
		DocHandler docHandler = (DocHandler)this.getDocRequestConfig().getDocHandlerMap().get( name );
		DocContext docContext = new DocContext( this.getDocRequestConfig() );
		docContext.setName(name);
		docContext.setFileName( fileName );
		docContext.setType( type );
	 	
		String contentDisposition = "attachment; filename="+docContext.getFileName();
		this.getLogger().info("contentDisposition  : "+contentDisposition );
		if ( contentDisposition != null ) {
			response.addHeader("Content-Disposition", contentDisposition );
		}			
		
		if ( DocHandler.MODE_DIRECT.equalsIgnoreCase( docHandler.getMode() ) ) {
			/*
			 * Mode direct : the docHandler is responsable for rendering the result like a servlet
			 * 				in this mode, different format should be handled separately
			 */
			try {
				docHandler.handleDoc(request, response, this.getDocRequestConfig().getContext().getContext() );
			} catch (Exception e) {
				throw new ServletException( e );
			}
		} else {
			/*
			 * Standard mode, the request is passed to an xml generator.
			 */
			this.docRequestPushbody.handleDoc( rq , docContext, docHandler );
		}
		
		this.getLogger().info( "end processing" );
	}

	
	public void configure( Element root, ConfigContext context ) {
		this.docRequestConfig = new DocRequestConfig();
		this.docRequestConfig.configure(root, context);
		this.docRequestPushbody = new DocRequestPushbody( this.docRequestConfig );
	}
	
	public DocRequestConfig getDocRequestConfig() {
		return docRequestConfig;
	}

	private static String init() {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			InputStream is = DocRequestFacade.class.getResourceAsStream( "/org/fugerit/java/ext/doc/res/error-doc.xml" );
			StreamIO.pipeStream( is , baos, StreamIO.MODE_CLOSE_BOTH );	
		} catch (Exception e) {
			e.printStackTrace();
		}
		return baos.toString();
	}
	
	public static final String ERROR_XML_DATA = init();
		
}
