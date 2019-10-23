package org.fugerit.java.core.util.tree;

import java.io.Serializable;
import java.util.Properties;

import org.fugerit.java.core.cfg.ConfigException;
import org.w3c.dom.Element;

public class DefaultTreeDecorator<T> implements Serializable, TreeDecorator<T> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 145354442344L;


	@Override
	public void init(Properties generalProps, Element root) throws ConfigException {
	}
	
	@Override
	public void setupData(T current, T parent, Element tag) throws Exception {
	}
	
}
