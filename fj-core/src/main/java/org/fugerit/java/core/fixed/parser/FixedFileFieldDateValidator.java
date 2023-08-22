package org.fugerit.java.core.fixed.parser;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.fugerit.java.core.cfg.ConfigException;
import org.fugerit.java.core.cfg.ConfigRuntimeException;
import org.fugerit.java.core.lang.helpers.BooleanUtils;
import org.fugerit.java.core.lang.helpers.StringUtils;
import org.w3c.dom.Element;

public class FixedFileFieldDateValidator extends FixedFileFieldBasicValidator implements FixedFileFieldValidator {

	public static final String ATT_NAME_FORMAT = "format";
	
	public static final String ATT_NAME_MIN_MAX_SYSDATE = "sysdate";
	
	public static final String ATT_NAME_MAX = "max";
	public static final String ATT_NAME_MIN = "min";
	
	public static final String ATT_NAME_STRICT = "strict";
	public static final String ATT_NAME_STRICT_DEFAULT = "true";

	/**
	 * 
	 */
	private static final long serialVersionUID = -5571414972052579008L;

	private String format;
	
	private Date max;
	
	private Date min;
	
	private boolean strict;

	protected String getFormat() {
		return format;
	}

	protected boolean isStrict() {
		return strict;
	}

	private SimpleDateFormat newDateFormat() {
		SimpleDateFormat sdf = new SimpleDateFormat( this.getFormat() );
		Calendar cal = Calendar.getInstance();
		if ( this.strict ) {
			cal.setLenient(false);
			sdf.setCalendar( cal );	
		}
		return sdf;
	}
	
	private String handleDateValidation( String fieldLabel, String fieldValue, int rowNumber, int colNumber ) {
		SimpleDateFormat sdf = newDateFormat();
		String message = null;
		try {
			Date d = sdf.parse( fieldValue );
			if ( this.min != null ) {
				if ( d.before( this.min ) ) {
					message = super.defaultFormatMessage( "error.date.min", fieldLabel, fieldValue, rowNumber, colNumber, sdf.format( this.min ) );
				}
			}
			if ( this.max != null ) {
				if ( d.after( this.max ) ) {
					message = super.defaultFormatMessage( "error.date.max", fieldLabel, fieldValue, rowNumber, colNumber, sdf.format( this.max ) );
				}
			}
		} catch (ParseException e) {
			message = super.defaultFormatMessage( "error.date", fieldLabel, fieldValue, rowNumber, colNumber, this.getFormat() );
		}
		return message;
	}
	
	@Override
	public FixedFileFieldValidationResult checkField(String fieldLabel, String fieldValue, int rowNumber, int colNumber ) {
		FixedFileFieldValidationResult result = null;
		boolean valid = true;
		String message = null;
		Exception exception = null;
		if ( fieldValue == null ) {
			fieldValue = "";
		}
		try {
			FixedFileFieldValidationResult checkRequired = super.checkRequired(fieldLabel, fieldValue, rowNumber, colNumber);
			if ( checkRequired.isValid() && StringUtils.isNotEmpty( fieldValue ) ) {
				String dateValidationMessage = this.handleDateValidation(fieldLabel, fieldValue, rowNumber, colNumber);
				if ( dateValidationMessage != null ) {
					message = dateValidationMessage;
					valid = false;
				}
			} else {
				result = checkRequired;
			}
		} catch (Exception e) {
			valid = false;
			message = "Validation exception "+e.getMessage();
			exception = e;
		}
		if ( result == null ) {
			result = new FixedFileFieldValidationResult( valid, fieldLabel, message, exception, rowNumber, colNumber );
		}
		return result;
	}

	private Date getDate( String label, String value, String format ) {
		SimpleDateFormat sdf = newDateFormat();
		Date d = null;
		if ( StringUtils.isNotEmpty( value ) ) {
			if ( ATT_NAME_MIN_MAX_SYSDATE.equalsIgnoreCase( value ) ) {
				d = new Date();
			} else {
				try {
					d = sdf.parse( value );
				} catch (ParseException e) {
					throw new ConfigRuntimeException( label+" should be valid for format date or 'sysdate' : "+value+" , "+format, e );
				}
			}
		}
		return d;
	}
	
	@Override
	public void configure( Element tag ) throws ConfigException {
		String config = tag.getAttribute( ATT_NAME_FORMAT );
		String strict = tag.getAttribute( ATT_NAME_STRICT );
		String max = tag.getAttribute( ATT_NAME_MAX );
		String min = tag.getAttribute( ATT_NAME_MIN );
		logger.info( "config "+ATT_NAME_FORMAT+" -> '"+config+"' "+ATT_NAME_STRICT+" -> "+strict );
		this.strict = BooleanUtils.isTrue( StringUtils.valueWithDefault( strict , ATT_NAME_STRICT_DEFAULT ) );
		if ( StringUtils.isEmpty( config ) ) {
			throw new ConfigException( ATT_NAME_FORMAT+" is mandatory attribute" );
		} else {
			this.format = config;
		}
		this.min = getDate( ATT_NAME_MIN , min , this.getFormat() );
		this.max = getDate( ATT_NAME_MAX ,max , this.getFormat() );
		super.configure( tag, "core.fixed.parser.validator" );
	}
	
}
