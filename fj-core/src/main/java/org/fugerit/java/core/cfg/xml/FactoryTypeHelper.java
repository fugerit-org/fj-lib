package org.fugerit.java.core.cfg.xml;

import java.io.Serializable;

import org.fugerit.java.core.cfg.ConfigException;
import org.fugerit.java.core.cfg.ConfigurableObject;
import org.fugerit.java.core.cfg.helpers.UnsafeHelper;
import org.fugerit.java.core.lang.helpers.ClassHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FactoryTypeHelper<T> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6578485338271471032L;
	
	private static Logger logger = LoggerFactory.getLogger( FactoryTypeHelper.class );
	
	private Class<?> returnType;
	
	private FactoryTypeHelper( Class<?> returnType ) {
		this.returnType = returnType;
	}
	
	@SuppressWarnings("unchecked")
	public T createHelper( FactoryType factoryType ) throws ConfigException {
		logger.info( "factoryType : {} , resultType : {}", factoryType, this.returnType );
		T res = null;
		try {
			res = (T)ClassHelper.newInstance( factoryType.getType() );
			if ( res instanceof ConfigurableObject && factoryType.getElement() != null ) {
				logger.info( "ConfigurableObject -> try configure()" );
				((ConfigurableObject)res).configure( factoryType.getElement());
			}
		} catch (Exception | NoClassDefFoundError e) {
			UnsafeHelper.handleUnsafe( new ConfigException( "Type cannot be loaded : "+e, e ), factoryType.getUnsafe(), factoryType.getUnsafeMode() );
		}
		return res;
	}
	
	public static <T> FactoryTypeHelper<T> newInstance( Class<? extends T> ct ) {
		return new FactoryTypeHelper<T>( ct );
	}
	
}
