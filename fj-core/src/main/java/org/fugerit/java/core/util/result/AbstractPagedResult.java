package org.fugerit.java.core.util.result;

import java.util.ArrayList;
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

	protected AbstractPagedResult() {
		super( RESULT_CODE_OK );
	}
	
	protected AbstractPagedResult(int perPage, long elementCount, int currentPage, int pageCount, List<T> pageElements) {
		this();
		this.offset = perPage*(currentPage-1);
		this.perPage = perPage;
		this.elementCount = elementCount;
		this.currentPage = currentPage;
		this.pageElements = pageElements;
		this.pageCount = pageCount;
	}

	/*
	 * <p>The position of the first element of the current pages ( (currentPage-1) * perPage )</p> 
	 * 
	 * @return	offset of the first element in this page
	 */
	@Override
	public Integer getOffset() {
		return Integer.valueOf( this.offset );
	}

	/*
	 * <p>Maximum number of elements in a page</p>
	 * 
	 * @return	maximum number of elements in a page
	 */
	@Override
	public Integer getPerPage() {
		return Integer.valueOf( this.perPage );
	}

	/*
	 * <p>Total number of elements in all pages</p>
	 * 
	 * @return	total number of elements in all pages
	 */
	@Override
	public Long getElementCount() {
		return Long.valueOf( this.elementCount );
	}

	/*
	 * <p>Position of current page ( in the range 1 - n )</p>
	 * 
	 * @return	position of current page
	 */
	@Override
	public Integer getCurrentPage() {
		return Integer.valueOf( this.currentPage );
	}
	
	/*
	 * <p>Total number of pages</p>
	 * 
	 * @return	total number of pages
	 */
	@Override
	public Integer getPageCount() {
		return Integer.valueOf( pageCount );
	}		

	/*
	 * <p>Number of elements in current page</p>
	 * 
	 * @return	the size of the current page
	 */
	@Override
	public Integer getCurrentPageSize() {
		return Integer.valueOf( this.pageElements.size() );
	}
	
	/*
	 * <p>Elements in the current page</p>
	 * 
	 * @return	elements in the current page
	 */
	@Override
	public Iterator<T> getPageElements() {
		return this.pageElements.iterator();
	}

	/*
	 * <p>Elements in the current page</p>
	 * 
	 * @return	elements in the current page
	 */
	@Override
	public List<T> getPageElementsList() {
		return this.pageElements;
	}
	
	/*
	 * <p>Iterator over page numbers ( 1 - n )</p>
	 * 
	 * @return	iterator over page numbers ( 1 - n )
	 */
	@Override
	public Iterator<Integer> getPageCountIterator() {
		List<Integer> list = new ArrayList<>();
		for ( int k=1; k<=this.pageCount; k++ ) {
			list.add( Integer.valueOf( k ) );
		}
		return list.iterator();
	}

	@Override
	public Map<String, Object> getInfo() {
		return this.getInfoMap();
	}
	
	@Override
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
