package org.fugerit.java.core.validator;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.fugerit.java.core.cfg.ConfigException;
import org.fugerit.java.core.util.PropertyEntry;

public class ValidatorResult implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5473162078733130133L;

	public static final String GENERIC_ID = ValidatorResult.class.getName()+"_GENERIC_ID";
	
	private transient List<PropertyEntry> errors;
	
	private transient List<PropertyEntry> warnings;

	public ValidatorResult() {
		this.errors = new ArrayList<PropertyEntry>();
		this.warnings = new ArrayList<PropertyEntry>();
	}

	public void addGenericError( String message ) throws ConfigException {
		this.addError( GENERIC_ID, message );
	}
	
	public void addGenericWarning( String fieldId, String message ) throws ConfigException {
		this.addWarning( GENERIC_ID , message );
	}
	
	public void addError( String fieldId, String message ) throws ConfigException {
		this.errors.add( PropertyEntry.newEntry( fieldId , message ) );
	}
	
	public void addWarning( String fieldId, String message ) throws ConfigException {
		this.warnings.add( PropertyEntry.newEntry( fieldId , message ) );
	}

	public List<PropertyEntry> getErrors() {
		return errors;
	}

	public List<PropertyEntry> getWarnings() {
		return warnings;
	}
	
	private List<String> filter( List<PropertyEntry> list, String fieldId ) {
		return list.stream().filter( 
				entry -> fieldId.equals( entry.getKey() ) 
				).map( 
				entry -> entry.getValue() 
				).collect( Collectors.toList() );
	}
	
	public List<String> getFieldErrors(String fieldId ) {
		return filter( this.getErrors() , fieldId );
	}
	
	public List<String> getFieldWarning(String fieldId ) {
		return filter( this.getWarnings() , fieldId );
	}
	
	public List<String> getGenericErrors() {
		return this.getFieldErrors( GENERIC_ID );
	}
	
	public List<String> getGenericWarning() {
		return this.getFieldWarning( GENERIC_ID );
	}
	
}
