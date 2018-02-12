package org.fugerit.java.ext.doc;

/*
 * 
 *
 * @author Morozko
 *
 */
public class DocHeaderFooter extends DocContainer {

	public DocHeaderFooter() {
		this.basic = true;
	}
	
	private boolean basic;
	
	public boolean isBasic() {
		return basic;
	}

	public void setBasic(boolean basic) {
		this.basic = basic;
	}

	private int borderWidth;
	
	public int getBorderWidth() {
		return borderWidth;
	}

	public void setBorderWidth(int borderWidth) {
		this.borderWidth = borderWidth;
	}

	private int align;

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
	
	private boolean numbered;

	/*
	 * @return the numbered
	 */
	public boolean isNumbered() {
		return numbered;
	}

	/*
	 * @param numbered the numbered to set
	 */
	public void setNumbered(boolean numbered) {
		this.numbered = numbered;
	}

	
}
