package org.fugerit.java.ext.doc;

/*
 * 
 *
 * @author mfranci
 *
 */
public class DocPara extends DocElement implements DocStyle {


	public static final int STYLE_NORMAL = 1;
	public static final int STYLE_BOLD = 2;
	public static final int STYLE_UNDERLINE = 3;
	public static final int STYLE_ITALIC = 4;
	public static final int STYLE_BOLDITALIC = 5;
	
	public static final int ALIGN_UNSET = 0;
	// h align
	public static final int ALIGN_LEFT = 1;
	public static final int ALIGN_CENTER = 2;
	public static final int ALIGN_RIGHT = 3;
	public static final int ALIGN_JUSTIFY = 9;
	public static final int ALIGN_JUSTIFY_ALL = 8;
	// v align
	public static final int ALIGN_TOP = 4;
	public static final int ALIGN_MIDDLE = 5;
	public static final int ALIGN_BOTTOM = 6;	
	
	private int style;
	
	private int size;
	
	private String text;

	
	
	private String foreColor;
	
	private String backColor;	
	
	
	private String fontName;
	
	public String getFontName() {
		return fontName;
	}

	public void setFontName(String fontName) {
		this.fontName = fontName;
	}

	
	@Override
	public String getForeColor() {
		return foreColor;
	}



	@Override
	public void setForeColor(String foreColor) {
		this.foreColor = foreColor;
	}



	@Override
	public String getBackColor() {
		return backColor;
	}



	@Override
	public void setBackColor(String backColor) {
		this.backColor = backColor;
	}



	public int getStyle() {
		return style;
	}



	public void setStyle(int style) {
		this.style = style;
	}



	public int getSize() {
		return size;
	}



	public void setSize(int size) {
		this.size = size;
	}



	public String getText() {
		return text;
	}



	public void setText(String text) {
		this.text = text;
	}
	
	private Float spaceBefore;
	
	private Float spaceAfter;	
	
	public Float getSpaceBefore() {
		return spaceBefore;
	}

	public void setSpaceBefore(Float spaceBefore) {
		this.spaceBefore = spaceBefore;
	}

	public Float getSpaceAfter() {
		return spaceAfter;
	}

	public void setSpaceAfter(Float spaceAfter) {
		this.spaceAfter = spaceAfter;
	}

	private Float leading;
	
	public Float getLeading() {
		return leading;
	}

	public void setLeading(Float leading) {
		this.leading = leading;
	}

	public DocPara() {
		this.setText( "" );
	}
	
	public static int parseStyle( String style ) {
		int result = STYLE_NORMAL;
		if ( "bold".equalsIgnoreCase( style ) ) {
			result = STYLE_BOLD;
		} else if ( "underline".equalsIgnoreCase( style ) ) {
			result = STYLE_UNDERLINE;
		} else if ( "italic".equalsIgnoreCase( style ) ) {
			result = STYLE_ITALIC;
		} else if ( "bolditalic".equalsIgnoreCase( style ) ) {
			result = STYLE_BOLDITALIC;
		}
		return result;
	}
	
	private int align;

	private String format;
	
	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	private String type;
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	/*
	 * @return the align
	 */
	public int getAlign() {
		return align;
	}

	/*
	 * @param align the align to set
	 */
	public void setAlign(int align) {
		this.align = align;
	}


	@Override
	public String toString() {
		return super.toString()+"[text:"+this.getText()+"]";
	}
	

	
}
