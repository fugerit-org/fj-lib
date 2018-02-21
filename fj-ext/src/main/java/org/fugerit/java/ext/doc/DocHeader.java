package org.fugerit.java.ext.doc;

/*
 * 
 *
 * @author Morozko
 *
 */
public class DocHeader extends DocHeaderFooter {

	public DocHeader() {
		this.useHeader = false;
	}
	
	private boolean useHeader;

	/*
	 * @return the useHeader
	 */
	public boolean isUseHeader() {
		return useHeader;
	}

	/*
	 * @param useHeader the useHeader to set
	 */
	public void setUseHeader(boolean useHeader) {
		this.useHeader = useHeader;
	}
	
}
