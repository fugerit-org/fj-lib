package org.fugerit.java.core.util.regex;

import java.io.Serializable;
import java.util.Properties;

public class SimpleParamProvider implements ParamProvider, Serializable {

	private static final long serialVersionUID = -6113410989966425831L;

	private Properties props;
	
	public SimpleParamProvider()  {
		this.props = new Properties();
	}
	
	@Override
	public Properties getProperties() {
		return this.props;
	}

}
