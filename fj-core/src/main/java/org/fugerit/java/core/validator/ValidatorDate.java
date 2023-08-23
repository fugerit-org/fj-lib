package org.fugerit.java.core.validator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import org.fugerit.java.core.cfg.ConfigException;
import org.fugerit.java.core.lang.helpers.StringUtils;

public class ValidatorDate extends BasicValidator {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5690219274192449044L;

	public static final String KEY_DATEFORMAT = "dateFormat";
	
	public static final String KEY_MINDATE = "minDate";
	
	public static final String KEY_MAXDATE = "maxDate";
	
	public static final String ERROR_KEY_DATE = "error.date";
	
	public static final String ERROR_KEY_DATE_MIN = "error.date.min";
	
	public static final String ERROR_KEY_DATE_MAX = "error.date.max";

	private String dateFormat;

	private String minDate;
	
	private String maxDate;
	
	public String getDateFormat() {
		return dateFormat;
	}

	public String getMinDate() {
		return minDate;
	}

	public String getMaxDate() {
		return maxDate;
	}

	protected Date setDate( SimpleDateFormat sdf, String d ) throws ParseException {
		Date res = null;
		if ( StringUtils.isNotEmpty( d ) ) {
			res = sdf.parse( d );
		}
		return res;
	}
	
	@Override
	public void checkConfig() throws ConfigException {
		super.checkConfig();
		if ( StringUtils.isEmpty( this.getDateFormat() ) ) {
			throw new ConfigException( "You must configure a 'dateFormat' attribute for this validator "+this.getId() );
		}
	}

	@Override
	public void configure( Properties atts ) throws ConfigException {
		super.configure(atts);
		try {
			String dateFormatLocal = atts.getProperty( KEY_DATEFORMAT ); 
			if ( StringUtils.isNotEmpty( dateFormatLocal ) ) {
				this.dateFormat = dateFormatLocal;
			}
			String minDateLocal = atts.getProperty( KEY_MINDATE );
			if ( StringUtils.isNotEmpty( minDateLocal ) ) {
				this.minDate = minDateLocal;
			}
			String maxDateLocal = atts.getProperty( KEY_MAXDATE );
			if ( StringUtils.isNotEmpty( maxDateLocal ) ) {
				this.maxDate = maxDateLocal;
			}
		} catch (Exception e) {
			throw new ConfigException( e );
		}
	}

	@Override
	public boolean validate(ValidatorContext context) throws Exception {
		String minDateLocal = this.checkOverride( context, this.getMinDate(), KEY_MINDATE );
		String maxDateLocal = this.checkOverride( context, this.getMaxDate(), KEY_MAXDATE );
		return super.validate( context ) && this.validate( context, minDateLocal, maxDateLocal );
	}
	
	protected boolean validate( ValidatorContext context, String minDate, String maxDate ) throws ConfigException  {
		boolean valid = true;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat( this.getDateFormat() );
			Date d = sdf.parse( context.getValue() );
			if ( StringUtils.isNotEmpty( minDate ) && d.before( sdf.parse( minDate ) ) ) {
				valid = false;
				String message = this.formatMessage( context.getBundle() , ERROR_KEY_DATE_MIN, context.getLabel(), context.getValue(), minDate );
				context.getResult().addError( context.getFieldId(), message );
			}
			if ( StringUtils.isNotEmpty( maxDate ) && d.after( sdf.parse( maxDate ) ) ) {
				valid = false;
				String message = this.formatMessage( context.getBundle() , ERROR_KEY_DATE_MAX, context.getLabel(), context.getValue(), maxDate );
				context.getResult().addError( context.getFieldId(), message );
			}
		} catch (Exception e) {
			valid = false;
			String message = this.formatMessage( context.getBundle() , ERROR_KEY_DATE, context.getLabel(), context.getValue(), StringUtils.valueWithDefault( this.getInfo(), this.getDateFormat() ) );
			context.getResult().addError( context.getFieldId(), message );
		}
		return valid;
	}

}
