package org.fugerit.java.ext.doc;

/*
 * 
 *
 * @author mfranci
 *
 */
public class DocInfo extends DocElement {

	public static final String INFO_NAME_CSS_LINK = "html-css-link";
	
	public static final String INFO_NAME_CSS_STYLE = "html-css-style";
	
	public static final String INFO_NAME_PAGE_ORIENT = "page-orient";
	
	public static final String INFO_NAME_PDF_FORMAT = "pdf-format";
	
	private String name;
	
	private StringBuffer content = new StringBuffer();

	/*
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/*
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/*
	 * @return the content
	 */
	public StringBuffer getContent() {
		return content;
	}

	/*
	 * @param content the content to set
	 */
	public void setContent(StringBuffer content) {
		this.content = content;
	}
	
}
