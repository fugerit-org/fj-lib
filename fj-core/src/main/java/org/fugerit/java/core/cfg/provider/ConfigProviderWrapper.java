package org.fugerit.java.core.cfg.provider;

import java.io.InputStream;

import org.fugerit.java.core.cfg.ConfigException;
import org.fugerit.java.core.lang.helpers.Wrapper;

public class ConfigProviderWrapper implements Wrapper<ConfigProvider>, ConfigProvider {

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
		this.wrapped = wrapped;
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
