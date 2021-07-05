package org.fugerit.java.ext.doc;

/*
 * 
 * 
 * @author Matteo a.k.a. Fugerit
 *
 */
public class DocBorders {

	@Override
	public Object clone() throws CloneNotSupportedException {
		DocBorders borders = new DocBorders();
		borders.setBorderWidthBottom( this.getBorderWidthBottom() );
		borders.setBorderWidthTop( this.getBorderWidthTop() );
		borders.setBorderWidthRight( this.getBorderWidthRight() );
		borders.setBorderWidthLeft( this.getBorderWidthLeft() );
		return borders;
	}

	private int paddingBottom;
	
	private int paddingTop;
	
	private int paddingRight;
	
	private int paddingLeft;
	
	private String borderColorTop;
	
	private String borderColorBottom;
	
	private String borderColorLeft;
	
	private String borderColorRight;
	
	private int borderWidthTop;

	private int borderWidthBottom;
	
	private int borderWidthLeft;

	private int borderWidthRight;

	/*
	 * @return the borderWidthBottom
	 */
	public int getBorderWidthBottom() {
		return borderWidthBottom;
	}

	/*
	 * @param borderWidthBottom the borderWidthBottom to set
	 */
	public void setBorderWidthBottom(int borderWidthBottom) {
		this.borderWidthBottom = borderWidthBottom;
	}

	/*
	 * @return the borderWidthLeft
	 */
	public int getBorderWidthLeft() {
		return borderWidthLeft;
	}

	/*
	 * @param borderWidthLeft the borderWidthLeft to set
	 */
	public void setBorderWidthLeft(int borderWidthLeft) {
		this.borderWidthLeft = borderWidthLeft;
	}

	/*
	 * @return the borderWidthRight
	 */
	public int getBorderWidthRight() {
		return borderWidthRight;
	}

	/*
	 * @param borderWidthRight the borderWidthRight to set
	 */
	public void setBorderWidthRight(int borderWidthRight) {
		this.borderWidthRight = borderWidthRight;
	}

	/*
	 * @return the borderWidthTop
	 */
	public int getBorderWidthTop() {
		return borderWidthTop;
	}

	/*
	 * @param borderWidthTop the borderWidthTop to set
	 */
	public void setBorderWidthTop(int borderWidthTop) {
		this.borderWidthTop = borderWidthTop;
	}

	/*
	 * @return the borderColorBottom
	 */
	public String getBorderColorBottom() {
		return borderColorBottom;
	}

	/*
	 * @param borderColorBottom the borderColorBottom to set
	 */
	public void setBorderColorBottom(String borderColorBottom) {
		this.borderColorBottom = borderColorBottom;
	}

	/*
	 * @return the borderColorLeft
	 */
	public String getBorderColorLeft() {
		return borderColorLeft;
	}

	/*
	 * @param borderColorLeft the borderColorLeft to set
	 */
	public void setBorderColorLeft(String borderColorLeft) {
		this.borderColorLeft = borderColorLeft;
	}

	/*
	 * @return the borderColorRight
	 */
	public String getBorderColorRight() {
		return borderColorRight;
	}

	/*
	 * @param borderColorRight the borderColorRight to set
	 */
	public void setBorderColorRight(String borderColorRight) {
		this.borderColorRight = borderColorRight;
	}

	/*
	 * @return the borderColorTop
	 */
	public String getBorderColorTop() {
		return borderColorTop;
	}

	/*
	 * @param borderColorTop the borderColorTop to set
	 */
	public void setBorderColorTop(String borderColorTop) {
		this.borderColorTop = borderColorTop;
	}

	/*
	 * @return the paddingBottom
	 */
	public int getPaddingBottom() {
		return paddingBottom;
	}

	/*
	 * @param paddingBottom the paddingBottom to set
	 */
	public void setPaddingBottom(int paddingBottom) {
		this.paddingBottom = paddingBottom;
	}

	/*
	 * @return the paddingLeft
	 */
	public int getPaddingLeft() {
		return paddingLeft;
	}

	/*
	 * @param paddingLeft the paddingLeft to set
	 */
	public void setPaddingLeft(int paddingLeft) {
		this.paddingLeft = paddingLeft;
	}

	/*
	 * @return the paddingRight
	 */
	public int getPaddingRight() {
		return paddingRight;
	}

	/*
	 * @param paddingRight the paddingRight to set
	 */
	public void setPaddingRight(int paddingRight) {
		this.paddingRight = paddingRight;
	}

	/*
	 * @return the paddingTop
	 */
	public int getPaddingTop() {
		return paddingTop;
	}

	/*
	 * @param paddingTop the paddingTop to set
	 */
	public void setPaddingTop(int paddingTop) {
		this.paddingTop = paddingTop;
	}
	
}
