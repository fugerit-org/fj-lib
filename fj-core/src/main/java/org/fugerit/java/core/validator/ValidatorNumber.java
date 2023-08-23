package org.fugerit.java.core.validator;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import org.fugerit.java.core.cfg.ConfigException;
import org.fugerit.java.core.lang.helpers.BooleanUtils;
import org.fugerit.java.core.lang.helpers.StringUtils;

/**
 * Basic number validation implementation.
 * 
 * @see <a href="https://jupiterdocs.fugerit.org/docs/validator/validator_number.html">ValidatorNumber Docs @ fugerit.org</a>
 * @see <a href="https://jupiterdocs.fugerit.org/docs/validator/">ValidatorCatalog Docs @ fugerit.org</a>
 * @author Matteo a.k.a. Fugerit
 * @since 0.7.4.7
 *
 */
public class ValidatorNumber extends BasicValidator {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5690219274192449044L;

	public static final String KEY_MAXIMUM_INTEGER_DIGITS = "maximumIntegerDigits";
	
	public static final String KEY_MINIMUM_FRACTION_DIGITS = "maximumFractionDigits";
	
	public static final String KEY_GROUPING_USED = "groupingUsed";
	
	public static final String KEY_CURRENCY = "currency";
	
	public static final String KEY_MINVALUE = "minValue";
	
	public static final String KEY_MAXVALUE = "maxValue";
	
	public static final String ERROR_KEY_NUMBER = "error.number";
	
	public static final String ERROR_KEY_NUMBER_MIN = "error.number.min";
	
	public static final String ERROR_KEY_NUMBER_MAX = "error.number.max";
	
	public static final String ERROR_KEY_NUMBER_MAXINTEGER = "error.number.maxinteger";
	public static final String ERROR_KEY_NUMBER_MAXFRACTION = "error.number.maxfraction";

	private int maximumIntegerDigits;
	
	private int maximumFractionDigits;

	private boolean groupingUsed;
	
	private boolean currency;
	
	private String minValue;
	
	private String maxValue;

	public int getMaximumIntegerDigits() {
		return maximumIntegerDigits;
	}

	public int getMaximumFractionDigits() {
		return maximumFractionDigits;
	}

	public boolean isGroupingUsed() {
		return groupingUsed;
	}

	public boolean isCurrency() {
		return currency;
	}

	public String getMinValue() {
		return minValue;
	}

	public String getMaxValue() {
		return maxValue;
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
	}

	@Override
	public void configure( Properties atts ) throws ConfigException {
		super.configure(atts);
		try {
			String maxIntegerDigitsLocal = atts.getProperty( KEY_MAXIMUM_INTEGER_DIGITS , String.valueOf( NO_LENGTH_CONSTRAINT ) ); 
			if ( StringUtils.isNotEmpty( maxIntegerDigitsLocal ) ) {
				this.maximumIntegerDigits = Integer.parseInt( maxIntegerDigitsLocal );
			}
			String maxFractionDigitsLocal = atts.getProperty( KEY_MINIMUM_FRACTION_DIGITS , String.valueOf( NO_LENGTH_CONSTRAINT ) ); 
			if ( StringUtils.isNotEmpty( maxFractionDigitsLocal ) ) {
				this.maximumFractionDigits = Integer.parseInt( maxFractionDigitsLocal );
			}
			String groupingUsedLocal = atts.getProperty( KEY_GROUPING_USED ); 
			if ( StringUtils.isNotEmpty( groupingUsedLocal ) ) {
				this.groupingUsed = BooleanUtils.isTrue( groupingUsedLocal );
			}
			String currencyLocal = atts.getProperty( KEY_CURRENCY ); 
			if ( StringUtils.isNotEmpty( currencyLocal ) ) {
				this.currency = BooleanUtils.isTrue( currencyLocal );
			}
			String minValueLocal = atts.getProperty( KEY_MINVALUE );
			if ( StringUtils.isNotEmpty( minValueLocal ) ) {
				this.minValue = minValueLocal;
			}
			String maxValueLocal = atts.getProperty( KEY_MAXVALUE );
			if ( StringUtils.isNotEmpty( maxValueLocal ) ) {
				this.maxValue = maxValueLocal;
			}
		} catch (Exception e) {
			throw new ConfigException( e );
		}
	}

	@Override
	public boolean validate(ValidatorContext context) throws Exception {
		String minValueLocal = this.checkOverride( context, this.getMinValue(), KEY_MINVALUE );
		String maxValueLocal = this.checkOverride( context, this.getMaxValue(), KEY_MAXVALUE );
		return super.validate( context ) && this.validate( context, minValueLocal, maxValueLocal );
	}
	
	protected NumberFormat newFormat( ValidatorContext context ) {
		NumberFormat nf = null;
		if ( this.isCurrency() ) {
			nf = NumberFormat.getCurrencyInstance( context.getLocale() );
		} else {
			nf = NumberFormat.getInstance( context.getLocale() );
		}
		if ( this.getMaximumIntegerDigits() != NO_LENGTH_CONSTRAINT ) {
			nf.setMaximumIntegerDigits( this.getMaximumIntegerDigits() );
		}
		if ( this.getMaximumFractionDigits() != NO_LENGTH_CONSTRAINT ) {
			nf.setMaximumFractionDigits( this.getMaximumFractionDigits() );
		}
		return nf;
	}
	
	protected boolean validate( ValidatorContext context, String minValue, String maxValue ) throws ConfigException {
		boolean valid = true;
		NumberFormat nf = this.newFormat(context);
		try {
			Number n = nf.parse( context.getValue() );
			BigDecimal bd = new BigDecimal( String.valueOf( n ) );
			if ( bd.scale() > this.getMaximumFractionDigits() ) {
				valid = false;
				String message = this.formatMessage( context.getBundle() , ERROR_KEY_NUMBER_MAXFRACTION, context.getLabel(), context.getValue(), String.valueOf( this.getMaximumFractionDigits() ) );
				context.getResult().addError( context.getFieldId(), message );
			} else if (  bd.precision()-bd.scale() > this.getMaximumIntegerDigits() ) {
				valid = false;
				String message = this.formatMessage( context.getBundle() , ERROR_KEY_NUMBER_MAXINTEGER, context.getLabel(), context.getValue(), String.valueOf( this.getMaximumIntegerDigits() ) );
				context.getResult().addError( context.getFieldId(), message );

			}
			if ( StringUtils.isNotEmpty( minValue ) && n.doubleValue() < nf.parse( minValue ).doubleValue() ) {
				valid = false;
				String message = this.formatMessage( context.getBundle() , ERROR_KEY_NUMBER_MIN, context.getLabel(), context.getValue(), minValue );
				context.getResult().addError( context.getFieldId(), message );
			}
			if ( StringUtils.isNotEmpty( maxValue ) && n.doubleValue() > nf.parse( maxValue ).doubleValue() ) {
				valid = false;
				String message = this.formatMessage( context.getBundle() , ERROR_KEY_NUMBER_MAX, context.getLabel(), context.getValue(), minValue );
				context.getResult().addError( context.getFieldId(), message );
			}
		} catch (Exception e) {
			valid = false;
			String message = this.formatMessage( context.getBundle() , ERROR_KEY_NUMBER, context.getLabel(), context.getValue(), StringUtils.valueWithDefault( this.getInfo(), nf.toString() ) );
			context.getResult().addError( context.getFieldId(), message );
		}
		return valid;
	}

}
