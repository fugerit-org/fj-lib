package org.fugerit.java.core.util.tree;

import java.util.Properties;

import org.fugerit.java.core.cfg.ConfigException;
import org.w3c.dom.Element;

public interface TreeDecorator<T> {

	void init( Properties generalProps, Element root ) throws ConfigException;
	
	void setupData( T current, T parent, Element tag ) throws Exception;

}
