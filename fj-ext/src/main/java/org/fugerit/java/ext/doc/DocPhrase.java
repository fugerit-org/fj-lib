package org.fugerit.java.ext.doc;

public class DocPhrase extends DocElement implements DocStyle {

	public DocPhrase() {
		this.text = "";
	}
	
	private int style;
	
	private int size;
	
	private String text;
	
	private String foreColor;
	
	private String backColor;	
		
	private String fontName;
	
	private Float leading;
	
	public Float getLeading() {
		return leading;
	}

	public void setLeading(Float leading) {
		this.leading = leading;
	}
	
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



	@Override
	public String toString() {
		return super.toString()+"[text:"+this.getText()+"]";
	}
	
}
