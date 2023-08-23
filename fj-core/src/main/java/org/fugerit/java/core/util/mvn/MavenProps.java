package org.fugerit.java.core.util.mvn;

import java.util.Properties;

import org.fugerit.java.core.util.PropsIO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MavenProps {

	private MavenProps() {}
	
	private static final Logger logger= LoggerFactory.getLogger(MavenProps.class);
	
	public static final String VERSION = "version";
	
	public static final String GROUP_ID = "gropupId";
	
	public static final String ARTIFACT_ID = "artifactId";
	
	public static String getPropery( String groupId, String artifactId, String proertyName ) {
		return loadMavenProps(groupId, artifactId).getProperty( proertyName );
	}
	
	public static Properties loadMavenProps( String groupId, String artifactId ) {
		Properties props = null;
		try {
			String path = "META-INF/maven/"+groupId+"/"+artifactId+"/pom.properties";
			props = PropsIO.loadFromClassLoader( path );
			logger.debug( "Maven Properties : {}", props );
		} catch (Throwable e) {
			logger.warn( "Failed to load props : "+e, e );
			props = new Properties();
		}
		return props;
	}
	
}
