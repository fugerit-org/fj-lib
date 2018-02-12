package org.fugerit.java.ext.doc;

/*
 * 
 * 
 * @author Matteo a.k.a. Fugerit
 *
 */
public class DocFooter extends DocHeaderFooter {

	public DocFooter() {
		this.useFooter = false;
	}
	
	private boolean useFooter;

	/*
	 * @return the useFooter
	 */
	public boolean isUseFooter() {
		return useFooter;
	}

	/*
	 * @param useFooter the useFooter to set
	 */
	public void setUseFooter(boolean useFooter) {
		this.useFooter = useFooter;
	}
	
}
