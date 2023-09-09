/**
 * 
 */
package org.fugerit.java.tool.mail;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.fugerit.java.core.function.SafeFunction;
import org.fugerit.java.core.lang.helpers.BooleanUtils;
import org.fugerit.java.tool.RunToolException;
import org.fugerit.java.tool.ToolHandlerHelper;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Daneel
 *
 */
@Slf4j
public class SendMail extends ToolHandlerHelper {

	public static final String ARG_SMTP_HOST = "mail.smpt.host";
	public static final String ARG_MAIL_TO = "to";
	public static final String ARG_MAIL_FROM = "from";
	public static final String ARG_MAIL_SUBJECT = "subject";
	public static final String ARG_MAIL_BODY = "body";
	public static final String ARG_BODY_TYPE = "type";
	
	public static final String ARG_TEST = "test";
	
	@Override
	public int handleWorker(Properties params) throws RunToolException {
		return SafeFunction.get(() -> {
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
			boolean test = BooleanUtils.isTrue( params.getProperty( ARG_TEST ) );
			log.info( "Sendil mail {}", message );
			if ( test ) {
				log.info( "Test mode activated, actual send skipped!" );
			} else {
				Transport.send( message );	
			}
			return EXIT_OK;
		});
	}

	
	
}
