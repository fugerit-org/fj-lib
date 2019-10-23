package org.fugerit.java.core.util.tree;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import org.fugerit.java.core.cfg.ConfigException;
import org.fugerit.java.core.lang.helpers.StringUtils;
import org.fugerit.java.core.lang.helpers.reflect.MethodHelper;
import org.w3c.dom.Element;

public class InheritTreeDecorator<T> implements Serializable, TreeDecorator<T> {

	private Set<String> inheritAtt;

	public static final String ATT_INHERIT_PROPERTY = "inherit-property";

	@Override
	public void init(Properties generalProps, Element root) throws ConfigException {
		this.inheritAtt = new HashSet<>();
		String inheritAtt = generalProps.getProperty( ATT_INHERIT_PROPERTY );
		if ( StringUtils.isNotEmpty( inheritAtt ) ) {
			String[] split = inheritAtt.split( "," );
			for ( String current : split ) {
				if ( StringUtils.isNotEmpty( current ) ) {
					this.inheritAtt.add( current );	
				}
			}
		}
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 145354442344L;

	@Override
	public void setupData(T current, T parent, Element tag) throws Exception {
		if ( parent != null && current != null ) {
			for ( String currentProperty : this.inheritAtt ) {
				String currentValue = (String)MethodHelper.invokeGetter( current , currentProperty );
				if ( StringUtils.isEmpty( currentValue ) ) {
					String parentValue = (String)MethodHelper.invokeGetter( parent , currentProperty );
					if ( StringUtils.isNotEmpty( parentValue ) ) {
						MethodHelper.invokeSetter( current , currentProperty, String.class, parentValue  );
					}
				}
			}
		}
	}
	
}
