package org.fugerit.java.ext.doc;

/*
 * 
 *
 * @author Morozko
 *
 */
public class DocImage extends DocElement {

	private Integer scaling;
	
	private String url;

	/*
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/*
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/*
	 * @return the scaling
	 */
	public Integer getScaling() {
		return scaling;
	}

	/*
	 * @param scaling the scaling to set
	 */
	public void setScaling(Integer scaling) {
		this.scaling = scaling;
	}
	
}
