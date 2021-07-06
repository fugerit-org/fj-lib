package org.fugerit.java.ext.doc;

/*
 * 
 * 
 * @author Matteo a.k.a. Fugerit
 *
 */
public class DocCell extends DocContainer implements DocStyle {
	
	@Override
	public String toString() {
		return super.toString()+"[align:"+this.getAlign()+"]";
	}
	
	public boolean isHeader() {
		return header;
	}

	public void setHeader(boolean header) {
		this.header = header;
	}

	private boolean header;
	
	private int align;
	
	private int valign;

	private String type;
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getValign() {
		return valign;
	}

	public void setValign(int valign) {
		this.valign = valign;
	}

	private int cSpan;
	
	private int rSpan;
	
	private String foreColor;
	
	private String backColor;
	
	private DocBorders docBorders;

	/*
	 * @return the docBorders
	 */
	public DocBorders getDocBorders() {
		return docBorders;
	}

	/*
	 * @param docBorders the docBorders to set
	 */
	public void setDocBorders(DocBorders docBorders) {
		this.docBorders = docBorders;
	}

	/*
	 * @return the cSpan
	 */
	public int getCSpan() {
		return cSpan;
	}

	/*
	 * @param span the cSpan to set
	 */
	public void setCSpan(int span) {
		cSpan = span;
	}

	/*
	 * @return the rSpan
	 */
	public int getRSpan() {
		return rSpan;
	}

	/*
	 * @param span the rSpan to set
	 */
	public void setRSpan(int span) {
		rSpan = span;
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

	/*
	 * @return the backColor
	 */
	@Override
	public String getBackColor() {
		return backColor;
	}

	/*
	 * @param backColor the backColor to set
	 */
	@Override
	public void setBackColor(String backColor) {
		this.backColor = backColor;
	}

	/*
	 * @return the foreColor
	 */
	@Override
	public String getForeColor() {
		return foreColor;
	}

	/*
	 * @param foreColor the foreColor to set
	 */
	@Override
	public void setForeColor(String foreColor) {
		this.foreColor = foreColor;
	}
	
}
