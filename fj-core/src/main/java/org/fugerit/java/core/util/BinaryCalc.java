package org.fugerit.java.core.util;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.function.ToIntFunction;

/*
 * 
 *
 * @author Morozko
 *
 */
public class BinaryCalc {
	
	private BinaryCalc() {}
	
	private static final BigDecimal ZERO = new BigDecimal( "0" ).setScale( 0 ); 
	
	private static long convertToLong( String stringVal, ToIntFunction<Character> fun, int pow ) {
		BigDecimal result = ZERO;
		stringVal = stringVal.toUpperCase();
		for ( int k=stringVal.length()-1, esp=0; k>=0; k--, esp++ ) {
			result = result.add( BigDecimal.valueOf( fun.applyAsInt( stringVal.charAt( k ) )*Math.pow( pow , esp ) ) );
		}
		return result.longValue();
	}
	
	public static long hexToLong( String stringVal ) {
		return convertToLong( stringVal , BinaryNumber::hexToInt , 16 );
	}
	
	public static long octToLong( String stringVal ) {
		return convertToLong( stringVal , BinaryNumber::octToInt , 8 );
	}
	
	public static long binToLong( String stringVal ) {
		return convertToLong( stringVal , BinaryNumber::binToInt , 2 );
	}

	public static String longToHex( long value ) {
		return new BinaryNumber( value ).getHexString();
	}
	
	public static String longToOct( long value ) {
		return new BinaryNumber( value ).getOctString();
	}
	
	public static String longToBin( long value ) {
		return new BinaryNumber( value ).getBinString();
	}
	
}

class BinaryNumber {

	private static Character[] convert( char[] c ) {
		Character[] result = new Character[ c.length ];
		for ( int k = 0; k < c.length; k++ ) {
			result[k] = Character.valueOf( c[k] );
		}
		return result;
	}
	
	public static final int BIN_RADIX = 1;
	
	public static final int OCT_RADIX = 3;
	
	public static final int HEX_RADIX = 4;
	
	protected static final char[] VALUES_BIN = { '0' , '1' };
	protected static final char[] VALUES_OCT = { '0' , '1' , '2' , '3' , '4' , '5' , '6', '7' };
	protected static final char[] VALUES_HEX = { '0' , '1' , '2' , '3' , '4' , '5' , '6', '7' , 
												'8' , '9' , 'A', 'B', 'C', 'D' , 'E' , 'F'};
	
	protected static final List<Character> LIST_BIN = Arrays.asList( convert( VALUES_BIN ) );
	protected static final List<Character> LIST_OCT = Arrays.asList( convert( VALUES_OCT ) );
	protected static final List<Character> LIST_HEX = Arrays.asList( convert( VALUES_HEX ) );
	
	public static int hexToInt( char c ) {
		return LIST_HEX.indexOf( Character.valueOf( c ) );
	}
	
	public static int octToInt( char c ) {
		return LIST_OCT.indexOf( Character.valueOf( c ) );
	}
	
	public static int binToInt( char c ) {
		return LIST_BIN.indexOf( Character.valueOf( c ) );
	}	
	
	public BinaryNumber() {
		this.data = new byte[64];
		this.size = 0;
	}
	
	public BinaryNumber( long value ) {
		this();
		this.setValue(value);
	}
	
	private byte[] data;
	
	private int size;
	
	public void setValue( long value ) {
		this.size = 0;
		long rest = 0;
		while ( value != 0 ) {
			rest = value%2;
			value = value/2;
			this.data[this.size] = (byte)rest;
			this.size++;
		}
	}
		
	public int size( int radix ) {
		return (this.size+this.size%radix)/radix; 
	}

	public int get( int index, int radix ) {
		int result = 0;
		for ( int k=index*radix, esp=0; k < Math.min( index*radix+radix , this.size ); k++, esp++ ) {
			int current = this.data[ k ] * (int)Math.pow( 2 , esp );
			result+= current;
		}
		return result;
	}
	
	private String getString( char[] values, int radix ) { 
		StringBuilder buffer = new StringBuilder();
		int currSize = this.size( radix );
		for ( int k=currSize-1; k>=0; k-- ) {
			buffer.append( values[ this.get( k , radix ) ] );
		}
		return buffer.toString();
	}
	
	public String getBinString() { 
		return this.getString( VALUES_BIN, BIN_RADIX );
	}
	
	public String getOctString() { 
		return this.getString( VALUES_OCT, OCT_RADIX );
	}	
	
	public String getHexString() { 
		return this.getString( VALUES_HEX, HEX_RADIX );
	}	
	
}
