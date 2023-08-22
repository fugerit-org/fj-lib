package org.fugerit.java.core.util.i18n;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;

public class XMLResourceBundleControl extends ResourceBundle.Control {
	
    @Override
    public List<String> getFormats(String baseName) {
        if (baseName == null) {
            throw new IllegalArgumentException( "baseName must be provided" );
        }
        return Arrays.asList("xml");
    }

    private ResourceBundle handleFormatXML( String baseName, Locale locale,
            String format,
            ClassLoader loader, boolean reload ) throws IOException {
    	ResourceBundle bundle = null;
    	if (format.equals("xml")) {
            String bundleName = toBundleName(baseName, locale);
            String resourceName = toResourceName(bundleName, format);
            URL url = loader.getResource(resourceName);
            if (url != null) {
                URLConnection connection = url.openConnection();
                if (connection != null) {
                    if (reload) {
                        // disable caches if reloading
                        connection.setUseCaches(false);
                    }
                    try (InputStream stream = connection.getInputStream()) {
                        if (stream != null) {
                            BufferedInputStream bis =
                                new BufferedInputStream(stream);
                            bundle = new XMLResourceBundle(bis);
                        }
                    }
                }
            }
        }
    	return bundle;
    }
    
    @Override
    public ResourceBundle newBundle(String baseName, Locale locale,
                                    String format,
                                    ClassLoader loader,
                                    boolean reload)
        throws IllegalAccessException,
               InstantiationException, IOException {
        if (baseName == null || locale == null
            || format == null || loader == null) {
        	throw new IllegalArgumentException( "incomplete configuration (baseName, locale, format and loader must be provided)" );
        }
        return this.handleFormatXML(baseName, locale, format, loader, reload);
    }

    private static class XMLResourceBundle extends ResourceBundle {
        private Properties props;

        XMLResourceBundle(InputStream stream) throws IOException {
            props = new Properties();
            props.loadFromXML(stream);
        }

        protected Object handleGetObject(String key) {
            if (key == null) {
            	throw new IllegalArgumentException( "key must be provided" );
            }
            return props.get(key);
        }

        @SuppressWarnings("unchecked")
		public Enumeration<String> getKeys() {
        	return (Enumeration<String>) props.propertyNames();
        }
    }

}