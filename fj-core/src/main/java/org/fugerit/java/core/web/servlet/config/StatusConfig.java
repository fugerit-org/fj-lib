package org.fugerit.java.core.web.servlet.config;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fugerit.java.core.cfg.ConfigException;

public class StatusConfig extends BasicConfig {


	/*
	 * 
	 */
	private static final long serialVersionUID = 84150833023293371L;

	@Override
	public void configure(Properties props) throws ConfigException {
	}	
	
	public static final String OPERATION = "status";

    private static void printSession( HttpServletRequest request, PrintWriter pw ) {
    	String style = " style='border: solid black 1px;' ";
    	SessionContext session = SessionContext.getHttpSessionContext( request );
    	pw.println( "<table width='100%'>" );
    	pw.println( "<caption "+style+"><b>session attributes</b></caption>" );
    	pw.println( "<tr>" );
    	pw.println( "<th "+style+">name</th>" );
    	pw.println( "<th "+style+">type</th>" );
    	pw.println( "<th "+style+">size</th>" );
    	pw.println( "</tr>" );
    	Iterator<String> itNames = session.attributeNames();
    	while ( itNames.hasNext() ) {
    		pw.println( "<tr>" );
    		String name = (String)itNames.next();
    		pw.println( "<td "+style+">"+name+"</td>" );
    		Object data = session.getAttribute( name );
    		String size = "";
    		String type = "null";
    		if ( data != null ) {
    			type = data.getClass().getName();
    		}
    		pw.println( "<td "+style+">"+type+"</td>" );
    		try {
    			ByteArrayOutputStream baos = new ByteArrayOutputStream();
    			ObjectOutputStream os = new ObjectOutputStream( baos );
    			os.writeObject( data );
    			os.close();
    			size = ""+baos.toByteArray().length;
    		} catch (Exception e) {
    			size = e.toString();
    		}
    		pw.println( "<td "+style+">"+size+"</td>" );
    		pw.println( "</tr>" );
    	}
    	pw.println( "</table>" );
    }
    
	
	public void render( HttpServletRequest request, HttpServletResponse response ) throws IOException {
		response.setContentType( "text/html" );
		PrintWriter pw = response.getWriter();
		pw.println( "<html>" );
		pw.println( "<head>" );
		VersionBean versionBean = (VersionBean)this.getConfigContext().getContext().getAttribute( VersionConfig.ATT_NAME );
		pw.println( "<title>Status page: "+versionBean.getAppName()+"</title>" );
		pw.println( "</head>" );
		pw.println( "<body>" );
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
		printSession(request, pw);
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
