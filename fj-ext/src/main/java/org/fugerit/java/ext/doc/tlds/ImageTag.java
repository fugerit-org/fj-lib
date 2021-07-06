package org.fugerit.java.ext.doc.tlds;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;

import org.fugerit.java.core.log.LogFacade;
import org.fugerit.java.core.web.tld.helpers.TagSupportHelper;

/*
 * 
 *
 * @author Morozko
 *
 */
public class ImageTag extends TagSupportHelper {

	/*
	 * 
	 */
	private static final long serialVersionUID = -318929122394310665L;

	private String url;
	
	private String file;
	
	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
	}

	private String scaling;

	private String webapp;

	/*
	 * @return the scaling
	 */
	public String getScaling() {
		return scaling;
	}

	/*
	 * @param scaling the scaling to set
	 */
	public void setScaling(String scaling) {
		this.scaling = scaling;
	}

	/*
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/*
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/*
	 * @return the webapp
	 */
	public String getWebapp() {
		return webapp;
	}

	/*
	 * @param webapp the webapp to set
	 */
	public void setWebapp(String webapp) {
		this.webapp = webapp;
	}

	/* (non-Javadoc)
	 * @see javax.servlet.jsp.tagext.TagSupport#doStartTag()
	 */
	@Override
	public int doStartTag() throws JspException {
		StringBuilder render = new StringBuilder();
		render.append( "<image url='" );
		if ( this.getUrl() != null ) {			
			HttpServletRequest request = (HttpServletRequest)this.pageContext.getRequest();
			if ( this.getWebapp()== null || "true".equalsIgnoreCase( this.getWebapp() ) ) {
				render.append( request.getScheme() );
				render.append( "://" );
				render.append( request.getServerName() );
				render.append( ":" );
				render.append( request.getServerPort() );
				render.append( request.getContextPath() );
			}
			render.append( this.getUrl() );
		} else if ( this.getFile() != null ) {
			File base = new File( pageContext.getServletContext().getRealPath( "/" ) );
			LogFacade.getLog().info( "ImageTag > base dir : "+base.getAbsolutePath() );
			File file = new File( base, this.file );
			try {
				URL url = file.toURI().toURL();
				LogFacade.getLog().info( "ImageTag > file url : "+url );
				render.append( url.toString() );
			} catch (MalformedURLException e) {
				throw ( new JspException( e.toString(), e ) );
			}
		} else {
			throw ( new JspException( "Either url or file attribute must be declared!" ) );
		}
		render.append( "'" );
		if ( this.getScaling() != null ) {
			render.append( " scaling='"  );
			render.append( this.getScaling() );
			render.append( "'" );
		}
		render.append( "/>" );
		this.print( render.toString() );
		return EVAL_PAGE;
	}
	
}
