package org.fugerit.java.core.lang.helpers.filter;

import java.io.Serializable;

import org.fugerit.java.core.cfg.ConfigException;
import org.fugerit.java.core.lang.compare.CompareHelper;
import org.fugerit.java.core.lang.helpers.StringUtils;
import org.fugerit.java.core.lang.helpers.reflect.PathHelper;

public class FilterApplyDefault implements FilterApply, Serializable {

	public static final String EV_IS_NOT_NULL = "isNotNull";
	public static final String EV_IS_NULL = "isNull";
	
	public static final String EV_IS_NOT_EMPTY = "isNotEmpty";
	public static final String EV_IS_EMPTY = "isEmpty";
	
	public static final String EV_NOT_EQUALS = "notEquals";
	public static final String EV_EQUALS = "equals";
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1397966868435958139L;

	@Override
	public boolean accept(Object target, FilterInfo filter) throws Exception {
		boolean ok = true;
		Object checkValue = target;
		if ( StringUtils.isNotEmpty( filter.getPath() ) ) {
			checkValue = PathHelper.lookup( filter.getPath() , target );
		}
		String evaluate = StringUtils.valueWithDefault( filter.getEvaluate() , EV_EQUALS );
		switch ( evaluate ) {
		case EV_IS_NULL:
			ok = ( checkValue == null );
			break;
		case EV_IS_NOT_NULL:
			ok = ( checkValue != null );
			break;
		case EV_IS_EMPTY:
			ok = ( StringUtils.isEmpty( String.valueOf( checkValue ) ) );
			break;
		case EV_IS_NOT_EMPTY:
			ok = ( StringUtils.isNotEmpty( String.valueOf( checkValue ) ) );
			break;
		case EV_EQUALS:
			ok = CompareHelper.equals( checkValue, filter.getValue() );
			break;
		case EV_NOT_EQUALS:
			ok = CompareHelper.notEquals( checkValue, filter.getValue() );
			break;					
		default:
			if ( filter.getValue() != null ) {
				ok = CompareHelper.equals( checkValue, filter.getValue() );
				break;
			} else {
				throw new ConfigException( "Evaluate not recognized '"+filter.getEvaluate()+"'" );	
			}
		}	
		return ok;
	}

}
