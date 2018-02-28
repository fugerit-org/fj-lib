package org.fugerit.java.ext.doc.filter.facade;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fugerit.java.core.log.BasicLogObject;
import org.fugerit.java.core.web.servlet.context.RequestContext;
import org.fugerit.java.core.web.servlet.response.HttpServletResponseByteData;
import org.fugerit.java.ext.doc.DocBase;
import org.fugerit.java.ext.doc.DocException;
import org.fugerit.java.ext.doc.DocFacade;
import org.fugerit.java.ext.doc.filter.DocContext;
import org.fugerit.java.ext.doc.filter.DocHandler;
import org.fugerit.java.ext.doc.filter.DocTypeHandler;
import org.fugerit.java.ext.doc.filter.HttpServletResponsePush;

public class DocRequestPushbody extends BasicLogObject {

	private DocRequestConfig docRequestConfig;
	
	public DocRequestPushbody(DocRequestConfig docRequestConfig) {
		super();
		this.docRequestConfig = docRequestConfig;
	}

	protected DocRequestConfig getDocRequestConfig() {
		return docRequestConfig;
	}

	public void handleDoc( RequestContext rq, DocContext docContext, DocHandler docHandler ) throws IOException {
		
		HttpServletRequest request = rq.getRequest();
		HttpServletResponse response = rq.getResponse();
		
		String type = docContext.getType();
		String name = docContext.getName();
		
		request.setAttribute( "doc.render.type" , type );
		
		String contentType = this.getDocRequestConfig().getMime().getProperty( type, "text/xml" );
		
		if ( !"SERVER".equals( contentType ) ) {
			response.setContentType( contentType );	
		}

		DocTypeHandler docTypeHandler = (DocTypeHandler)this.getDocRequestConfig().getTypeHandlerMap().get( type );

		// fine processamento
		
		try {
			//se il doc handler à null usiamo il default doc handler
			if ( docTypeHandler != null ) {
				docTypeHandler.handleDocTypeInit(request, response, docContext);
			}
			if ( docHandler.getForward() != null ) {
				docHandler.handleDoc( request, response, this.getDocRequestConfig().getContext().getContext() );
				RequestDispatcher rd = request.getRequestDispatcher( docHandler.getForward() );
				this.getLogger().info( "forward : '"+docHandler.getForward()+"'" );
				rd.forward( request , response );	
			} else {
				HttpServletResponse resp = null;
				if ( "pushbody".equalsIgnoreCase( this.getDocRequestConfig().getOutMode() ) ) {
					resp = new HttpServletResponsePush( response );
				} else {
					resp = new HttpServletResponseByteData( response );
				}
				
				docHandler.handleDoc( request, resp, this.getDocRequestConfig().getContext().getContext() );
				String encoding = docHandler.getEncoding();
				try {
					response.setCharacterEncoding( encoding );	
				} catch ( Throwable t  ) {
					this.getLogger().info( "failed setting character encoding : "+t );
				}
				
				String jspRelative = this.getDocRequestConfig().getJspPath()+"/"+name+".jsp";
				String jspHandler = this.getDocRequestConfig().getContext().getContext().getRealPath( jspRelative );
				File jspFile = new File( jspHandler );
				if ( !jspFile.exists() ) {
					throw new DocException( "01", "Jsp File doesn't exists : '"+jspRelative+"'", null );
				}
				
				
				// use jsp?
				if ( docHandler.isUseJsp() ) {
					String jspPath = this.getDocRequestConfig().getJspPath()+"/doc-handler.jsp";
					if (this.getDocRequestConfig().getProcessingPage() != null ) {
						request.setAttribute( "doc-handler-name" , name );
						jspPath = this.getDocRequestConfig().getJspPath()+"/"+this.getDocRequestConfig().getProcessingPage() ;
					}
					RequestDispatcher rd = request.getRequestDispatcher( jspPath );
					this.getLogger().info( "jspPath : '"+jspPath+"'" );
					rd.forward( request , resp );
					String xmlData = null;
					if ( "pushbody".equalsIgnoreCase( this.getDocRequestConfig().getOutMode() ) ) {
						StringWriter sw = (StringWriter)request.getAttribute( "doc.writer" );
						xmlData = sw.toString();
					} else {
						HttpServletResponseByteData re = (HttpServletResponseByteData) resp;
						re.flush();
						xmlData = re.getBaos().toString();	
					}
					if ( this.getDocRequestConfig().isDebug() ) {
						this.getLogger().info( "xmlData 1 : \n"+xmlData );
					}
					if ( !this.getDocRequestConfig().isSkipFilter() ) {
						if ( this.getDocRequestConfig().isDebug() ) {
							this.getLogger().info( "skip filter : true" );
						}
					}	
					docContext.setXmlData( xmlData );
				
				}
				
				docContext.setType( type );
				docContext.setContentType( contentType );
				docContext.setEncoding( encoding );

				this.handleDocWorker(request, response, docTypeHandler, docContext);
				
				docHandler.handleDocPost(request, response, this.getDocRequestConfig().getContext().getContext());
				if ( docTypeHandler != null ) {
					docTypeHandler.handleDocTypePost(request, response, docContext);
				}
			}

		} catch (Exception e) {
			response.sendError( HttpServletResponse.SC_INTERNAL_SERVER_ERROR );
		}
	
	}
	
	private void handleDocWorker( HttpServletRequest request, HttpServletResponse response, DocTypeHandler docTypeHandler, DocContext docContext ) throws Exception {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		docContext.setBufferStream( baos );
		this.getLogger().info( "handleDocWorker - docTypeHandler 1 : "+docTypeHandler );
		if ( docTypeHandler == null ) {
			DocBase docBase = docContext.getDocBase( request );
			if ( docContext.getType().equalsIgnoreCase( "xml" ) ) {
				docContext.getBufferStream().write( docContext.getXmlData().getBytes() );
			} else if ( docContext.getType().equalsIgnoreCase( "pdf" ) ) {
				DocFacade.createPDF( docBase , docContext.getBufferStream() );
			} else if ( docContext.getType().equalsIgnoreCase( "rtf" ) ) {
				DocFacade.createRTF( docBase , docContext.getBufferStream() );
			} else if ( docContext.getType().equalsIgnoreCase( "html" ) ) {
				DocFacade.createHTML( docBase , docContext.getBufferStream() );		
			}
			this.getLogger().info("content type doc  : "+docContext.getContentType()+" : "+docContext.getEncoding() );
		} else {
			docTypeHandler.handleDocType( request, response, docContext );
		}
		OutputStream os = response.getOutputStream();
		baos.writeTo( os );
		baos.flush();
		os.flush();
	}
	
}
