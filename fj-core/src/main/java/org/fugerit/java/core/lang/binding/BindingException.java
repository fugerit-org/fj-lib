package org.fugerit.java.core.lang.binding;

import org.fugerit.java.core.lang.helpers.StringUtils;

public class BindingException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9146563860710408319L;

	public BindingException() {
		super();
	}

	public BindingException(String message, Throwable cause) {
		super(message, cause);
	}

	public BindingException(String message) {
		super(message);
	}

	public BindingException(Throwable cause) {
		super(cause);
	}
	
	public BindingException( BindingConfig binding, BindingFieldConfig field, Throwable cause ) {
		super( toMessage(binding, field), cause );
	}

	public BindingException( BindingConfig binding, BindingFieldConfig field, Object from, Object to, Throwable cause ) {
		super( toMessage(binding, field, from, to), cause );
	}
	
	public static String toMessage( BindingConfig binding, BindingFieldConfig field, Object from, Object to ) {
		StringBuilder builder = new StringBuilder();
		builder.append( toMessage(binding, field) );
		builder.append( " from:" );
		builder.append( from );
		builder.append( " to:" );
		builder.append( to );	
		return builder.toString();
	}
	
	public static String toMessage( BindingConfig binding, BindingFieldConfig field ) {
		StringBuilder builder = new StringBuilder();
		builder.append( "Exception on " );
		if ( StringUtils.appendIfNotNull( builder, binding  ) ) {
			builder.append( " -> " );
		}
		StringUtils.appendIfNotNull( builder, field  );
		return builder.toString();
	}
	
}
