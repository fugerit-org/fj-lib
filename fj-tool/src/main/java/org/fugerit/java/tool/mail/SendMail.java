/**
 * 
 */
package org.fugerit.java.tool.mail;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.fugerit.java.tool.ToolHandlerHelper;

/**
 * @author Daneel
 *
 */
public class SendMail extends ToolHandlerHelper {

	public final static String ARG_SMTP_HOST = "mail.smpt.host";
	public final static String ARG_MAIL_TO = "to";
	public final static String ARG_MAIL_FROM = "from";
	public final static String ARG_MAIL_SUBJECT = "subject";
	public final static String ARG_MAIL_BODY = "body";
	public final static String ARG_BODY_TYPE = "type";
	
	@Override
	public int handleWorker(Properties params) throws Exception {
		int exit = EXIT_OK;
		Properties props = new Properties();
		props.setProperty( ARG_SMTP_HOST , params.getProperty( ARG_SMTP_HOST ) );
		
		String from = params.getProperty( ARG_MAIL_FROM );
		String to = params.getProperty( ARG_MAIL_TO );
		String subject = params.getProperty( ARG_MAIL_SUBJECT );
		String body = params.getProperty( ARG_MAIL_BODY );
		String type = params.getProperty( ARG_BODY_TYPE, "text/plain" );
		
		Session session = Session.getDefaultInstance( props );
		MimeMessage message = new MimeMessage( session );
		message.setFrom( new InternetAddress( from ) );
		String[] splitTo = to.split( ";" );
		for ( int k=0; k<splitTo.length; k++ ) {
			message.addRecipient( Message.RecipientType.TO, new InternetAddress( splitTo[k] ) );	
		}
		
		final MimeBodyPart textPart = new MimeBodyPart();
        textPart.setContent( body, type ); 
		
        Multipart multipart = new MimeMultipart( "alternative" );
        multipart.addBodyPart( textPart );
        
		message.setSubject( subject );
		message.setContent( multipart );
		
		return exit;
	}

	
	
}
