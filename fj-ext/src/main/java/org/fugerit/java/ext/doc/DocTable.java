package org.fugerit.java.ext.doc;

/*
 * 
 *
 * @author mfranci
 *
 */
public class DocTable extends DocContainer implements DocStyle {

	
	private int padding;
	
	private int spacing;
	
	public int getPadding() {
		return padding;
	}

	public void setPadding(int padding) {
		this.padding = padding;
	}

	public int getSpacing() {
		return spacing;
	}

	public void setSpacing(int spacing) {
		this.spacing = spacing;
	}

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

	private Float spaceBefore;
	
	private Float spaceAfter;
	
	private int[] colWithds;
	
	private int width;
	
	private int columns;
	
	private String foreColor;
	
	private String backColor;	

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

	/*
	 * @return the columns
	 */
	public int getColumns() {
		return columns;
	}

	/*
	 * @param columns the columns to set
	 */
	public void setColumns(int columns) {
		this.columns = columns;
	}

	/*
	 * @return the width
	 */
	public int getWidth() {
		return width;
	}

	/*
	 * @param width the width to set
	 */
	public void setWidth(int width) {
		this.width = width;
	}

	/*
	 * @return the colWithds
	 */
	public int[] getColWithds() {
		return colWithds;
	}

	/*
	 * @param colWithds the colWithds to set
	 */
	public void setColWithds(int[] colWithds) {
		this.colWithds = colWithds;
	}
	
}
