package org.fugerit.java.core.cfg.provider;

import java.io.InputStream;
import java.io.Serializable;

import org.fugerit.java.core.cfg.ConfigException;
import org.fugerit.java.core.lang.helpers.Wrapper;

public class ConfigProviderWrapper implements Wrapper<ConfigProvider>, ConfigProvider, Serializable {

	private static final long serialVersionUID = 4374243909891989966L;

	@Override
	public InputStream readConfiguration(String mode, String path) throws ConfigException {
		return this.wrapped.readConfiguration(mode, path);
	}

	private ConfigProvider wrapped;
	
	public ConfigProviderWrapper() {
		this( ConfigProviderFacade.getInstance().getDefaultProvider() );
	}
	
	public ConfigProviderWrapper(ConfigProvider wrapped) {
		super();
		this.wrapModel(wrapped);
	}

	@Override
	public ConfigProvider unwrapModel() {
		return this.unwrap();
	}

	@Override
	public void wrapModel(ConfigProvider wrapped) {
		this.wrapped = wrapped;
	}

	@Override
	public ConfigProvider unwrap() {
		return this.wrapped;
	}
	
}
