package org.fugerit.java.core.util.result;

public class DefaultVirtualFinder extends AbstractVirtualFinder {
	
	public DefaultVirtualFinder(int perPage, int currentPage ) {
		this(perPage, currentPage, perPage, currentPage, null );
	}
	
	public DefaultVirtualFinder(int perPage, int currentPage, int realPerPage, int realCurrentPage, String searchVirtualKey ) {
		super(perPage, currentPage);
		this.realperPage = realPerPage;
		this.realCurrentPage = realCurrentPage;
		this.searchVirtualKey = searchVirtualKey;
	}

	private String searchVirtualKey;
	
	private int realperPage;
	
	private int realCurrentPage;

	@Override
	public int getRealPerPage() {
		return this.realperPage;
	}

	@Override
	public int getRealCurrentPage() {
		return this.realCurrentPage;
	}

	@Override
	public String getSearchVirtualKey() throws UnsupportedOperationException {
		if ( this.searchVirtualKey == null ) {
			throw new UnsupportedOperationException( "No search virtual key defined" );
		}
		return this.searchVirtualKey;
	}
	
	

}
