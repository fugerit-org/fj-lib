package org.fugerit.java.core.util.result;

import java.io.Serializable;
import java.util.List;

public class DefaultPagedResult<T> extends AbstractPagedResult<T>  implements Serializable {

	/*
	 * 
	 */
	private static final long serialVersionUID = -4104228790597768353L;

	private int realPerPage;
	
	private int realCurrentPage;
	
	private String virtualKey;
	
	/**
	 * Creates a new PagedResult
	 * 
	 * @param <T>			the parameter type
	 * @param perPage		the number of element per page
	 * @param elementCount	the total element count
	 * @param currentPage	the current element
	 * @param pageElements	the content of the current page
	 * @return				the paged result
	 */
	public static <T> PagedResult<T>  newPagedResult( int perPage, long elementCount, int currentPage, List<T> pageElements ) {
		return new DefaultPagedResult<>( perPage, elementCount, currentPage, pageElements, perPage, currentPage, null );
	}
	
	public static <T> PagedResult<T>  newPagedResult( int perPage, long elementCount, int currentPage, List<T> pageElements, int realPerPage, int realCurrentPage, String virtualKey ) {
		return new DefaultPagedResult<>( perPage, elementCount, currentPage, pageElements, realPerPage, realCurrentPage, virtualKey );
	}
	
	public static <T>  PagedResult<T>  newPagedResult( int resultCode ) {
		DefaultPagedResult<T> result = new DefaultPagedResult<>( -1, -1, -1, null, -1, -1, null );
		result.setResultCode( resultCode );
		return result;
	}
	
	protected DefaultPagedResult(int perPage, long elementCount, int currentPage, List<T> pageElements, int realPerPage, int realCurrentPage, String virtualKey) {
		super(perPage, elementCount, currentPage, calcPageCount( elementCount, perPage ), pageElements);
		this.realCurrentPage = realCurrentPage;
		this.realPerPage = realPerPage;
		this.virtualKey = virtualKey;
	}

	private static int calcPageCount( long elementCount, int perPage ) {
		int pageCount = 0;
		if(perPage>0)
		{
			pageCount = (int) ( ( elementCount )/( perPage ) );
			if ( ( elementCount )%( perPage ) != 0 ) {
				pageCount++;
			}	
		}
		return pageCount;
	}


	@Override
	public String getVirtualSearchKey() {
		return this.virtualKey;
	}

	@Override
	public Integer getRealPerPage() {
		return this.realPerPage;
	}
	
	@Override
	public Integer getRealCurrentPage() {
		return this.realCurrentPage;
	}

	@Override
	public PagedResult<T> getVirtualPage( int currentPage ) {
		int offset =  ((this.getRealCurrentPage()-1)*this.getRealPerPage());
		int virtualStart = (currentPage-1)*this.getPerPage()-offset;
		int virtualEnd = virtualStart+this.getPerPage();
		if ( this.getLogger().isDebugEnabled() ) {
			String message = "current page : "+currentPage+" size : "+this.getCurrentPageSize()+" vs : "+virtualStart+" ve : "+virtualEnd+" rps:"+this.getRealPerPage()+" , rp:"+this.getRealCurrentPage();
			this.getLogger().debug( message );
		}
		List<T> elements = this.getPageElementsList().subList( virtualStart , virtualEnd );
		return newPagedResult( this.getPerPage(), this.getElementCount(), currentPage, elements );
	}

	@Override
	public boolean isFullResult() {
		return false;
	}
	
	
}
