package org.fugerit.java.core.util.result;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

/*
 * Inteface for handling paged result 
 * 
 * @author fugerit
 *
 * @param &lt;E&gt;
 */
public interface PagedResult<T> extends Result {

	
	public static final int FIRST_PAGE_INDEX = 1;
	
	/**
	 * The method getElementCount() returns this value if the element count is unavalable
	 */
	public final static Integer ELEMENT_COUNT_UNAVAILABLE = -1;
	
	/**
	 * <p>The position of the first element of the current pages ( (currentPage-1) * perPage )</p> 
	 * 
	 * @return	offset of the first element in this page
	 */
	public Integer getOffset();
	
	/**
	 * <p>Maximum number of elements in a page</p>
	 * 
	 * @return	maximum number of elements in a page
	 */
	public Integer getPerPage();

	/**
	 * <p>Total number of elements in all pages</p>
	 * 
	 * @return	total number of elements in all pages
	 */
	public Long getElementCount();
	
	/**
	 * <p>Position of current page ( in the range 1 - n )</p>
	 * 
	 * @return	position of current page
	 */
	public Integer getCurrentPage();	
	
	/**
	 * <p>Total number of pages</p>
	 * 
	 * @return	total number of pages
	 */
	public Integer getPageCount();	
	
	/**
	 * <p>Number of elements in current page</p>
	 * 
	 * @return	the size of the current page
	 */
	public Integer getCurrentPageSize();
	
	/**
	 * <p>Elements in the current page</p>
	 * 
	 * @return	elements in the current page
	 */
	public Iterator<T> getPageElements();

	/**
	 * <p>Elements in the current page</p>
	 * 
	 * @return	elements in the current page
	 */
	public List<T> getPageElementsList();
	
	/**
	 * <p>Iterator over page numbers ( 1 - n )</p>
	 * 
	 * @return	iterator over page numbers ( 1 - n )
	 */
	public Iterator<Integer> getPageCountIterator();
	
	/**
	 * Result code for this page
	 * 
	 * @return result code
	 */
	@Override
	public int getResultCode();
	
	/**
	 * Additional info of this page.
	 * 
	 * @return	info
	 */
	public Map<String, Object> getInfo();
	
	
	/**
	 * <code>true</code> if this is the last page.
	 * 
	 * @return <code>true</code> if it's the last page
	 */
	public boolean isLastPage();
	
	/**
	 * <code>true</code> if this is the last page.
	 * 
	 * @return <code>true</code> if it's the first page
	 */
	public boolean isFirstPage();	
	
	
	// ******* additiona method for virtual paging *******
	
	// *******************************************************************************************
	// *******************************************************************************************
	// * VIRTUAL PAGING IS A METHOD WHERE A VIRTUAL PAGE IS MAPPED INTO A BIGGER PAGE            *
	// *******************************************************************************************
	// *******************************************************************************************
	
	/*
	 * Virtual search key
	 * 
	 * @return	the virtual search key
	 */
	public String getVirtualSearchKey();
	
	public Integer getRealPerPage();
	
	public Integer getRealCurrentPage();
	
	public PagedResult<T> getVirtualPage( int currentPage );
	
	public boolean isSupportVirtualPaging();
	
	
	// ******* additiona method for full result *******
	
	// *******************************************************************************************
	// *******************************************************************************************
	// * FULL RESULT IS A METHOD OF ACCESS PAGE WHERE ALL ELEMENTS ARE ACCESSIBLE ONLY ITERATING *
	// *******************************************************************************************
	// *******************************************************************************************
	
	/**
	 * <code>true</code> if the the page contains the full result
	 * 
	 * @return true if the page contains the full result
	 */
	public boolean isFullResult();

}
