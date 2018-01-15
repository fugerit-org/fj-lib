package org.fugerit.java.core.fixed.parser;

public class FixedFieldDescriptor {
	
	public FixedFieldDescriptor(String id, String name, int start, int length) {
		super();
		this.normalizedName = id;
		this.name = name;
		this.start = start;
		this.length = length;
	}
	
	public FixedFieldDescriptor(String name, int start, int length) {
		this( normalizeName( name ), name, start, length );
	}
	
	private String name;
	
	private FixedFileFieldValidator validator;
	
	private String normalizedName;
	
	private int start;
	
	private int length;

	public String getName() {
		return name;
	}
	
	public String getNormalizedName() {
		return normalizedName;
	}

	public int getStart() {
		return start;
	}

	public int getLength() {
		return length;
	}

	public int getEnd() {
		return this.getStart()+this.getLength();
	}
	
	public static String normalizeName( String name ) {
		String prenormalize = name.toLowerCase().replaceAll( "[^A-Za-z0-9]", " " ).trim();
		String[] tokens = prenormalize.split( " " );
		StringBuffer buffer = new StringBuffer();
		buffer.append( tokens[0] );
		for ( int k=1; k<tokens.length; k++ ) {
			if ( tokens[k].length() > 0 ) {
				buffer.append( tokens[k].substring( 0 , 1 ).toUpperCase() );
				buffer.append( tokens[k].substring( 1 ) );
			}
		}
		return buffer.toString();
	}
	
	public String toString() {
		return this.getClass().getSimpleName()+"[name:"+this.getName()+",start:"+this.getStart()+",length:"+this.getLength()+",normalized-name:"+this.getNormalizedName()+"]";
	}

	public FixedFileFieldValidator getValidator() {
		return validator;
	}

	public void setValidator(FixedFileFieldValidator validator) {
		this.validator = validator;
	}

}
