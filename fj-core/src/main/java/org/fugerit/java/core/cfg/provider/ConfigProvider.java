package org.fugerit.java.core.cfg.provider;

import java.io.InputStream;

import org.fugerit.java.core.cfg.ConfigException;

public interface ConfigProvider {

	InputStream readConfiguration( String mode, String path ) throws ConfigException;
	
}
