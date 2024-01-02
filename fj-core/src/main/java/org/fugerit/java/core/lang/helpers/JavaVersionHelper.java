package org.fugerit.java.core.lang.helpers;

import org.fugerit.java.core.cfg.ConfigRuntimeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JavaVersionHelper {

	private JavaVersionHelper() {}
	
	public static final int MAJOR_VERSION_JAVA_8 = 8;
	
	public static final int MAJOR_VERSION_JAVA_9 = 9;
	
	public static final int MAJOR_VERSION_JAVA_10 = 10;
	
	public static final int MAJOR_VERSION_JAVA_11 = 11;
	
	public static final int MAJOR_VERSION_JAVA_17 = 17;
	
	public static final int MAJOR_VERSION_JAVA_21 = 21;
	
	public static final int MAJOR_VERSION_FJ_CORE_REF = MAJOR_VERSION_JAVA_8;	// current reference version for fj-core library!
	
	private static final Logger logger = LoggerFactory.getLogger( JavaVersionHelper.class );
	
	private static final String SYS_PROP = "java.version";
	
	private static final String REMOVE_1_X = "1.";
	
	public static final int UNDEFINED = -1;
	
	public static int parseUniversalJavaMajorVersion( String javaVersion ) {
		int res = UNDEFINED;
		if ( StringUtils.isEmpty( javaVersion ) ) {
			throw new ConfigRuntimeException( "null property "+SYS_PROP );
		} else {
			logger.info( "{} -> {}", SYS_PROP, javaVersion );
			if ( javaVersion.startsWith( REMOVE_1_X ) ) {
				javaVersion = javaVersion.substring( REMOVE_1_X.length() );
			}
			int index = javaVersion.indexOf( '.' );
			try {
				if ( index != -1 ) {
					res = Integer.parseInt( javaVersion.substring( 0, index ) );
				} else {
					res = Integer.parseInt( javaVersion );
				}
			} catch (Exception e) {
				log.warn( "Error finding java version : "+e, e );
			}
			if ( res < MAJOR_VERSION_FJ_CORE_REF ) {
				logger.info( "major version found : '{}' is lower than minimum required -> '{}'", res, MAJOR_VERSION_FJ_CORE_REF );
			}
			logger.info( "parseUniversalJavaMajorVersion -> '{}'", res );
		}
		return res;
	}
	
	public static int parseUniversalJavaMajorVersion() {
		return parseUniversalJavaMajorVersion( System.getProperty( SYS_PROP ) );
	}
	
}
