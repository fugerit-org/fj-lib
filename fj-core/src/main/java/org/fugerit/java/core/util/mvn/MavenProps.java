package org.fugerit.java.core.util.mvn;

import java.util.Optional;
import java.util.Properties;

import org.fugerit.java.core.util.PropsIO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Utility class for loading Maven artifact metadata from the classpath.
 * <p>
 * This class provides access to standard Maven properties such as {@code groupId}, {@code artifactId},
 * and {@code version}, as defined in the {@code pom.properties} file bundled with each artifact.
 * </p>
 *
 * @since 8.7.0
 */
public class MavenProps {

	private MavenProps() {}
	
	private static final Logger logger= LoggerFactory.getLogger(MavenProps.class);
	
	public static final String VERSION = "version";
	
	public static final String GROUP_ID = "groupId";
	
	public static final String ARTIFACT_ID = "artifactId";

	/**
	 * @deprecated It is substituted by {@link #getProperty(String, String, String)} method.
	 * @since 8.7.0
	 */
	@Deprecated
	public static String getPropery( String groupId, String artifactId, String propertyName ) {
		return getProperty( groupId, artifactId, propertyName );
	}

	/**
	 * Loads the specified Maven property from the classpath.
	 *
	 * @param groupId the Maven groupId of the dependency
	 * @param artifactId the Maven artifactId of the dependency
	 * @param propertyName the name of the property to retrieve (e.g. {@code version})
	 * @return the property value, or {@code null} if not found
	 * @see #getPropertyOptional(String, String, String)
	 */
	public static String getProperty( String groupId, String artifactId, String propertyName ) {
		return loadMavenProps(groupId, artifactId).getProperty( propertyName );
	}

	/**
	 * Loads the specified Maven property from the classpath and returns it as an {@link Optional}.
	 *
	 * @param groupId the Maven groupId of the dependency
	 * @param artifactId the Maven artifactId of the dependency
	 * @param propertyName the name of the property to retrieve (e.g. {@code version})
	 * @return an {@code Optional} containing the property value if found, or {@code Optional.empty()} otherwise
	 * @see #getProperty(String, String, String)
	 */
	public static Optional<String> getPropertyOptional(String groupId, String artifactId, String propertyName ) {
		return Optional.ofNullable( loadMavenProps(groupId, artifactId).getProperty( propertyName ) );
	}
	
	private static final String SEP = "/";

	/**
	 * Loads the {@code pom.properties} file for the specified Maven dependency from the classpath.
	 *
	 * @param groupId the Maven groupId of the dependency
	 * @param artifactId the Maven artifactId of the dependency
	 * @return a {@link Properties} object containing the loaded properties,
	 *         or an empty {@code Properties} object if the file could not be found or loaded
	 */
	public static Properties loadMavenProps( String groupId, String artifactId ) {
		try {
			String path = String.join(SEP, "META-INF", "maven", groupId, artifactId, "pom.properties");
			Properties props = PropsIO.loadFromClassLoader( path );
			logger.debug( "Maven Properties : {}", props );
			return props;
		} catch (Exception e) {
			logger.warn( "Failed to load props : "+e, e );
			return new Properties();
		}
	}
	
}
