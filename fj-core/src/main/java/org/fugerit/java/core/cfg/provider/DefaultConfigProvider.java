package org.fugerit.java.core.cfg.provider;

import java.io.InputStream;

import org.fugerit.java.core.cfg.ConfigException;
import org.fugerit.java.core.io.helper.StreamHelper;

public class DefaultConfigProvider implements ConfigProvider {

	@Override
	public InputStream readConfiguration(String mode, String path) throws ConfigException {
		InputStream is = null;
		try {
			is = StreamHelper.resolveStreamByMode( mode , path );
		} catch (Exception e) {
			throw new ConfigException( "Error loading configuration mode:"+mode+", path:"+path, e );
		}
		return is;
	}

}
