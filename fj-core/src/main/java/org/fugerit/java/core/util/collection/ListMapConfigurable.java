package org.fugerit.java.core.util.collection;

import java.util.Properties;

import org.w3c.dom.Element;

public interface ListMapConfigurable {

	Properties getConfig();
	
	void initFromElementAttributes( Element tag );
	
}
