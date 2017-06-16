package org.fugerit.java.core.util.result;

public abstract class AbstractVirtualFinder implements VirtualFinder {

	public AbstractVirtualFinder(int perPage, int currentPage) {
		super();
		this.perPage = perPage;
		this.currentPage = currentPage;
	}

	private int perPage;
	
	private int currentPage;
	
	@Override
	public int getPerPage() {
		return this.perPage;
	}

	@Override
	public int getCurrentPage() {
		return this.currentPage;
	}

	@Override
	public boolean isVirtualPagingUsed() {
		return this.getPerPage() < this.getRealPerPage();
	}

	public String toString() {
		return "VirtualFinder[perPage:"+this.getPerPage()+",currentPage:"+this.getCurrentPage()+
				",realPerPage:"+this.getRealPerPage()+",realCurrentPage:"+this.getRealCurrentPage()+"]";
	}
	
}
