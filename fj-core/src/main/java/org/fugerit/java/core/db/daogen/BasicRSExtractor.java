package org.fugerit.java.core.db.daogen;

import java.io.Serializable;

import org.fugerit.java.core.db.dao.RSExtractor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class BasicRSExtractor<T> implements RSExtractor<T>, Serializable {

	protected static Logger logger = LoggerFactory.getLogger( BasicRSExtractor.class );
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6077230325800314699L;

}
