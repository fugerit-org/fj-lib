package org.fugerit.java.core.cfg.provider;

import java.io.InputStream;
import java.io.Serializable;

import org.fugerit.java.core.cfg.ConfigException;
import org.fugerit.java.core.io.helper.StreamHelper;

public class DefaultConfigProvider implements ConfigProvider, Serializable {

	private static final long serialVersionUID = 1840575515710083273L;

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
