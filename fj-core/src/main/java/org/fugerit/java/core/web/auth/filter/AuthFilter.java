package org.fugerit.java.core.web.auth.filter;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fugerit.java.core.lang.helpers.ClassHelper;
import org.fugerit.java.core.web.auth.handler.AuthHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AuthFilter implements Filter {

	private final static Logger logger = LoggerFactory.getLogger( AuthFilter.class );
	
	/**
	 * Relative path to context root of configuration file
	 */
	public static final String INIT_PARAM_CONFIG = "auth-filter-config";
	
	/**
	 * Name of the key in the general properties of auth-filter-config to look fo auth handler type
	 */
	public static final String KEY_AUTH_HANDLER_TYPE = "auth-handler-type";
	
	private AuthResourcesConfig config;
	
	private AuthHandler handler;
	
	private File configFile;
	
	@Override
	public void destroy() {
		this.config = null;
	}

	private void handleNoAccess( String currentUrl, int auth, HttpServletResponse response, int httpCode ) throws IOException {
		logger.info( "Access forbidded to: {}, auth result: {}, http code: {}, check configuration file {} if that's not correct", currentUrl, auth, httpCode, this.configFile.getCanonicalPath() );
		response.sendError( httpCode );
	}
	
	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		int auth = AuthHandler.AUTH_AUTHORIZED;
		if ( req instanceof HttpServletRequest && res instanceof HttpServletResponse ) {
			HttpServletRequest request = (HttpServletRequest) req;
			HttpServletResponse response = (HttpServletResponse) res;
			String currentUrl = request.getRequestURI().substring( request.getContextPath().length() );
			try {
				AuthResourcesEntry entry = this.config.match( currentUrl );
				auth = this.handler.checkAuth( request, entry.getAuth() );
			} catch (Exception e) {
				new ServletException( "Error during auth filter : "+e , e );
			} finally {
				logger.debug( " check result url : {} -> auth : {}", currentUrl, auth );
			}
			if ( auth == AuthHandler.AUTH_AUTHORIZED ) {
				chain.doFilter( req , res );
			} else if ( auth == AuthHandler.AUTH_NOAUTH ) {
				handleNoAccess(currentUrl, auth, response, HttpServletResponse.SC_UNAUTHORIZED);
			} else if ( auth == AuthHandler.AUTH_HIDDEN ) {
				handleNoAccess(currentUrl, auth, response, HttpServletResponse.SC_NOT_FOUND);		
			} else {
				handleNoAccess(currentUrl, auth, response, HttpServletResponse.SC_FORBIDDEN);
			}
		} else {
			chain.doFilter( req , res );
		}
	}

	@Override
	public void init(FilterConfig chain) throws ServletException {
		String configPath = chain.getInitParameter( INIT_PARAM_CONFIG );
		this.configFile = new File( chain.getServletContext().getRealPath( "/" ), configPath );
		logger.info( " INIT AuthFilter {} (file:{} exists? {})", configPath, this.configFile.getAbsoluteFile(), this.configFile.exists() );
		if ( this.configFile.exists() ) {
			try ( FileInputStream fis = new FileInputStream( this.configFile ) ) {
				this.config = AuthResourcesConfig.loadConfig( fis );
				String handlerType = this.config.getGeneralProps().getProperty( KEY_AUTH_HANDLER_TYPE );
				this.handler = (AuthHandler)ClassHelper.newInstance( handlerType );
				logger.info( " INIT AuthFilter OK, auth-handler-type: {}", handlerType );		
			} catch (Exception e) {
				throw new ServletException( "Error loading config : "+e, e );
			}
		} else {
			throw new ServletException( "Cannot configure auth filter, config does not exists: "+this.configFile.getAbsolutePath() );
		} 
	}

	
}
