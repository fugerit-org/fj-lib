package org.fugerit.java.core.util.result;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public abstract class AbstractPagedResult<T> extends BasicResult implements PagedResult<T> {

	public static final boolean DEFAULT_SUPPORT_VIRTUAL_PAGING = false;
	
	private int pageCount;
	
	private int offset;
	
	private int perPage;
	
	private long elementCount;
	
	private int currentPage;
	
	private List<T> pageElements;
	
	private Map<String, Object> info;

	protected AbstractPagedResult(int perPage, long elementCount, int currentPage, int pageCount, List<T> pageElements) {
		super( RESULT_CODE_OK );
		this.offset = perPage*(currentPage-1);
		this.perPage = perPage;
		this.elementCount = elementCount;
		this.currentPage = currentPage;
		this.pageElements = pageElements;
		this.pageCount = pageCount;
		this.info = new HashMap<String, Object>();
	}
	

	

	/*
	 * <p>The position of the first element of the current pages ( (currentPage-1) * perPage )</p> 
	 * 
	 * @return	offset of the first element in this page
	 */
	@Override
	public Integer getOffset() {
		return new Integer( this.offset );
	}

	/*
	 * <p>Maximum number of elements in a page</p>
	 * 
	 * @return	maximum number of elements in a page
	 */
	public Integer getPerPage() {
		return new Integer( this.perPage );
	}

	/*
	 * <p>Total number of elements in all pages</p>
	 * 
	 * @return	total number of elements in all pages
	 */
	public Long getElementCount() {
		return new Long( this.elementCount );
	}

	/*
	 * <p>Position of current page ( in the range 1 - n )</p>
	 * 
	 * @return	position of current page
	 */
	public Integer getCurrentPage() {
		return new Integer( this.currentPage );
	}
	
	/*
	 * <p>Total number of pages</p>
	 * 
	 * @return	total number of pages
	 */
	public Integer getPageCount() {
		return new Integer( pageCount );
	}		

	/*
	 * <p>Number of elements in current page</p>
	 * 
	 * @return	the size of the current page
	 */
	public Integer getCurrentPageSize() {
		return new Integer( this.pageElements.size() );
	}
	
	/*
	 * <p>Elements in the current page</p>
	 * 
	 * @return	elements in the current page
	 */
	public Iterator<T> getPageElements() {
		return this.pageElements.iterator();
	}

	/*
	 * <p>Elements in the current page</p>
	 * 
	 * @return	elements in the current page
	 */
	public List<T> getPageElementsList() {
		return this.pageElements;
	}
	
	/*
	 * <p>Iterator over page numbers ( 1 - n )</p>
	 * 
	 * @return	iterator over page numbers ( 1 - n )
	 */
	public Iterator<Integer> getPageCountIterator() {
		List<Integer> list = new ArrayList<Integer>();
		for ( int k=1; k<=this.pageCount; k++ ) {
			list.add( new Integer( k ) );
		}
		return list.iterator();
	}

	public Map<String, Object> getInfo() {
		return info;
	}
	
	public boolean isLastPage() {
		boolean lastPage = false;
		long lp = (this.getElementCount()/this.getPerPage())+1;
		if ( this.getElementCount()%this.getPerPage() == 0 ) {
			lp--;
		}
		lastPage = this.getCurrentPage() == lp;
		return lastPage;
	}
	
	@Override
	public boolean isFirstPage() {
		return this.getCurrentPage() == FIRST_PAGE_INDEX;
	}
	
	@Override
	public boolean isSupportVirtualPaging() {
		return DEFAULT_SUPPORT_VIRTUAL_PAGING;
	}

}
