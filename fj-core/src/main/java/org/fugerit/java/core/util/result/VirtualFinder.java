package org.fugerit.java.core.util.result;

public interface VirtualFinder {

	/*
	 * Virtual "per page" value
	 * 
	 * @return
	 */
	public int getPerPage();
	
	/*
	 * Virtual "current page" value
	 * 
	 * @return
	 */
	public int getCurrentPage();
	
	/*
	 * Real "per page" value
	 * 
	 * @return
	 */
	public int getRealPerPage();
	
	/*
	 * Real "current page" value
	 * 
	 * @return
	 */
	public int getRealCurrentPage();
	
	
	/*
	 * <code>true</code> if real "per page" is less than virtual "per page"
	 * 
	 * @return
	 */
	public boolean isVirtualPagingUsed();
	
	/*
	 * Return the search virtual key or an UnsupportedOperationException
	 * 
	 * @return
	 * @throws UnsupportedOperationException
	 */
	public String getSearchVirtualKey() throws UnsupportedOperationException;
	
}
