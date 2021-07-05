package org.fugerit.java.core.web.servlet.config;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fugerit.java.core.cfg.ConfigException;
import org.fugerit.java.core.cfg.VersionUtils;

public class VersionConfig extends BasicConfig {


	/*
	 * 
	 */
	private static final long serialVersionUID = 8415080002663193371L;

	private static List<VersionBean> appList = Collections.synchronizedList( new ArrayList<VersionBean>() );
	
	public static final String ATT_NAME = "VERSION_BEAN";
	
	private VersionBean versionBean;
	
	public static final String OPERATION_VERSION = "version";
	
	@Override
	public void configure(Properties props) throws ConfigException {
		this.getLogger().info( "start" );
		this.versionBean = new VersionBean( props.getProperty( "app-name" ), 
									props.getProperty( "app-version" ), 
									props.getProperty( "app-date" ), 
									( new Timestamp( System.currentTimeMillis() ) ).toString() );
		this.versionBean.setReleaseName( props.getProperty( "release-name" ) );
		appList.remove( this.versionBean );
		appList.add( this.versionBean );
		this.getConfigContext().getContext().setAttribute( ATT_NAME , this.versionBean );
		this.getLogger().info( "end" );
	}	
	
	public void renderVersion( HttpServletRequest request, HttpServletResponse response ) throws IOException {
		response.setContentType( "text/html" );
		PrintWriter pw = response.getWriter();
		pw.println( "<html>" );
		pw.println( "<head>" );
		pw.println( "<title>Version page: "+this.versionBean.getAppName()+"</title>" );
		pw.println( "</head>" );
		pw.println( "<body>" );
		pw.println( "<h3>Version page (ConfigServlet load time : "+ConfigServlet.LOAD_TIME+") "+InetAddress.getLocalHost()+"</h3>" );
		pw.println( "<p>Application : "+this.versionBean.getAppName()+"</p>" );
		pw.println( "<p>Version : "+this.versionBean.getAppVersion()+"</p>" );
		if ( this.versionBean.getReleaseName() != null ) {
			pw.println( "<p>Release : "+this.versionBean.getReleaseName()+"</p>" );	
		}
		pw.println( "<p>Date : "+this.versionBean.getAppDate()+"</p>" );
		pw.println( "<p>Last startup : "+this.versionBean.getLastStartup()+"</p>" );
		Properties moduleList = VersionUtils.getModuleList();
		Iterator<Object> moduleIt = moduleList.keySet().iterator();
		pw.println( "<ul>module list:" );
		while ( moduleIt.hasNext() ) {
			String key = (String) moduleIt.next();
			String value = VersionUtils.getVersionString( key );
			pw.println( "<li>"+value+"</li>" );
		}
		pw.println( "</ul>" );
		if ( this.getInitLog() != null ) {
			pw.println( "<ul>init log:" );
			Iterator<String> it = this.getInitLog().iterator();
			while ( it.hasNext() ) {
				pw.println( "<li>"+it.next()+"</li>" );
			}	
		}
		pw.println( "</ul>" );
		if ( "1".equalsIgnoreCase( request.getParameter( "applist" ) ) ) {
			pw.println( "<ul>application list:" );
			Iterator<VersionBean> itApp = appList.iterator();
			while ( itApp.hasNext() ) {
				VersionBean currentApp = (VersionBean)itApp.next();
				pw.println( "<li>"+currentApp.getAppName()+" - "+currentApp.getAppDate()+" - "+currentApp.getAppVersion()+"</li>" );
			}
			pw.println( "</ul>" );
		}
		if ( "1".equalsIgnoreCase( request.getParameter( "status" ) ) ) {
			pw.println( "<ul>server status:" );
			Runtime rt = Runtime.getRuntime();
			pw.println( "<li>os           : "+System.getProperty( "os.name" )+" ("+System.getProperty( "os.version" )+" "+System.getProperty( "os.arch" )+")</li>" );
			pw.println( "<li>java         : Java "+System.getProperty( "java.version" )+" ("+System.getProperty( "java.vendor" )+", url:"+System.getProperty( "java.vendor.url" )+")</li>" );
			pw.println( "<li>java specs   : "+System.getProperty( "java.specification.name" )+" "+System.getProperty( "java.specification.version" )+" ("+System.getProperty( "java.specification.vendor" )+")</li>" );
			pw.println( "<li>java runtime : "+System.getProperty( "java.runtime.name" )+" "+System.getProperty( "java.runtime.version" )+"</li>" );
			pw.println( "<li>java vm      : "+System.getProperty( "java.vm.name" )+" "+System.getProperty( "java.vm.version" )+" ("+System.getProperty( "java.vm.vendor" )+"), info:"+System.getProperty( "java.vm.info" )+"</li>" );
			pw.println( "<li>server       : "+this.getConfigContext().getContext().getServerInfo()+"</li>" );
			pw.println( "<li>processors   : "+rt.availableProcessors()+"</li>" );
			pw.println( "<li>free  memory : "+rt.freeMemory()+"</li>" );
			pw.println( "<li>total memory : "+rt.totalMemory()+"</li>" );
			pw.println( "<li>max   memory : "+rt.maxMemory()+"</li>" );
			pw.println( "</ul>" );
		}
		if ( "1".equalsIgnoreCase( request.getParameter( "sysprops" ) ) ) {
			pw.println( "<ul>system properties:" );
			Iterator<Object> itKeys = System.getProperties().keySet().iterator();
			while ( itKeys.hasNext() ) {
				String key = (String)itKeys.next();
				String value = System.getProperty( key );
				pw.println( "<li>"+key+"="+value+"</li>" );
			}
			pw.println( "</ul>" );
		}
		pw.println( "</body>" );
		pw.println( "</html>" );
	}
	
	private List<String> initLog;

	public List<String> getInitLog() {
		return initLog;
	}

	public void setInitLog(List<String> initLog) {
		this.initLog = initLog;
	}
	
}
