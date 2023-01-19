package org.fugerit.java.core.util;

import java.util.Arrays;
import java.util.List;

/*
 * 
 *
 * @author Morozko
 *
 */
public class BinaryCalc {
	
	public static long hexToLong( String hexString ) {
		long result = 0;
		hexString = hexString.toUpperCase();
		for ( int k=hexString.length()-1, esp=0; k>=0; k--, esp++ ) {
			result+= BinaryNumber.hexToInt( hexString.charAt( k ) )*Math.pow( 16 , esp );
		}
		return result;
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
	
	public static final char[] VALUES_BIN = { '0' , '1' };
	public static final char[] VALUES_OCT = { '0' , '1' , '2' , '3' , '4' , '5' , '6', '7' };
	public static final char[] VALUES_HEX = { '0' , '1' , '2' , '3' , '4' , '5' , '6', '7' , 
												'8' , '9' , 'A', 'B', 'C', 'D' , 'E' , 'F'};
	
	public static final List<Character> LIST_BIN = Arrays.asList( convert( VALUES_BIN ) );
	public static final List<Character> LIST_OCT = Arrays.asList( convert( VALUES_OCT ) );
	public static final List<Character> LIST_HEX = Arrays.asList( convert( VALUES_HEX ) );
	
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
			int current = (int)this.data[ k ] * (int)Math.pow( 2 , esp );
			result+= current;
		}
		return result;
	}
	
	public int binSize() {
		return this.size;
	}
	
	public int octSize() {
		return this.size( OCT_RADIX );
	}
	
	public int hextSize() {
		return this.size( HEX_RADIX );
	}	
	
	public int getBin( int index ) {
		return this.data[index];
	}	
	
	public int getOct( int index ) {
		return this.get( index, OCT_RADIX );
	}
	
	public int getHex( int index ) {
		return this.get( index, HEX_RADIX );
	}	
	
	public char getBinValue( int index ) {
		return VALUES_BIN[ this.getBin( index ) ];
	}
	
	public char getOctValue( int index ) {
		return VALUES_OCT[ this.getOct( index ) ];
	}	
	
	public char getHexValue( int index ) {
		return VALUES_HEX[ this.getHex( index ) ];
	}

	private String getString( char[] values, int radix ) { 
		StringBuilder buffer = new StringBuilder();
		for ( int k=0; k<this.size( radix ); k++ ) {
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
	
	@Override
	public String toString() {
		return this.getBinString();
	}
	
	public static void main( String[] args ) {
		try {
			BinaryNumber n = new BinaryNumber();
			n.setValue( 255 );
			for ( int k=0; k<n.hextSize(); k++ ) {
				System.out.println( "TEST "+n.getHex( k ) );
			}
			System.out.println( "A :"+n.getHexString() );
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
