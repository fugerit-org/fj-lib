package test.org.fugerit.java.core.util.result;

import java.util.Arrays;
import java.util.List;

import org.fugerit.java.core.util.result.AbstractPagedResult;
import org.fugerit.java.core.util.result.BasicResult;
import org.fugerit.java.core.util.result.DefaultPagedResult;
import org.fugerit.java.core.util.result.DefaultVirtualFinder;
import org.fugerit.java.core.util.result.PageInfo;
import org.fugerit.java.core.util.result.PagedResult;
import org.fugerit.java.core.util.result.Result;
import org.fugerit.java.core.util.result.ResultHelper;
import org.fugerit.java.core.util.result.VirtualPageCache;
import org.junit.Assert;
import org.junit.Test;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TestResult {

	private static Integer[] DATA_SET = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };
	
	private static List<Integer> DATA = Arrays.asList( DATA_SET );
	
	private <T> boolean testPagedResultWorker( PagedResult<T> result ) {
		if ( result.isResultOk() ) {
			log.info( "info : {}", result.getInfo() );
			log.info( "isSupportVirtualPaging : {}", result.isSupportVirtualPaging() );
			log.info( "getOffset : {}", result.getOffset() );
			log.info( "getCurrentPage : {}", result.getCurrentPage() );
			log.info( "getCurrentPageSize : {}", result.getCurrentPageSize() );
			log.info( "getElementCount : {}", result.getElementCount() );
			log.info( "getPageCount : {}", result.getPageCount() );
			log.info( "getPerPage : {}", result.getPerPage() );
			log.info( "getRealCurrentPage : {}", result.getRealCurrentPage() );
			log.info( "getRealPerPage : {}", result.getRealPerPage() );
			log.info( "isFirstPage : {}", result.isFirstPage() );
			log.info( "isLastPage : {}", result.isLastPage() );
			log.info( "isFullResult : {}", result.isFullResult() );
			log.info( "getVirtualSearchKey : {}", result.getVirtualSearchKey() );
			result.getPageCountIterator().forEachRemaining( p -> log.info( "page it : {}", p ) );
			result.getPageElementsList().stream().forEach( e -> log.info( "element list : {}", e ) );
			result.getPageElements().forEachRemaining( e -> log.info( "element it : {}", e ) );
		}
 		return this.testResultWorker(result);
	}
	
	private boolean testResultWorker( Result result ) {
		log.info( "result : {}, info : ", result, result.getInfoMap() );
		result.setResultCode( result.getResultCode() );
		return result.isResultOk();
	}
	
	@Test
	public void testBasicResult() {
		BasicResult result = new BasicResult( Result.RESULT_CODE_OK );
		boolean ok = this.testResultWorker(result);
		Assert.assertTrue( ok );
	}
	
	@Test
	public void testPagedResultVirtual() {
		PagedResult<Integer> result = DefaultPagedResult.newPagedResult( 10, DATA.size(), 0, DATA );
		boolean ok = this.testPagedResultWorker(result);
		Assert.assertTrue( ok );
		ok = this.testPagedResultWorker( result.getVirtualPage( 0 ) );
		Assert.assertTrue( ok );
	}
	
	@Test
	public void testPagedResult() {
		PagedResult<Integer> result = DefaultPagedResult.newPagedResult( 3, DATA.size(), 0, DATA, 3, DATA.size(), "test" );
		boolean ok = this.testPagedResultWorker(result);
		Assert.assertTrue( ok );
	}

	private int testPagedResultFullWorker( int perPage, List<Integer> data ) {
		int currentPage = 0;
		log.info( "total size : {}", data.size() );
		while ( currentPage*perPage < data.size() ) {
			int fromIndex = currentPage*perPage;
			int toIndex = (currentPage+1)*perPage;
			List<Integer> createList = data.subList( fromIndex, Math.min( data.size() , toIndex ) );
			PagedResult<Integer> result = DefaultPagedResult.newPagedResult(perPage, createList.size(), currentPage, createList);
			currentPage++;
			log.info( "current page : {}", currentPage );
			result.getPageElementsList().stream().forEach( e -> log.info( "element : {}", e ) );
		}
		return currentPage;	
	}
	
	@Test
	public void testPagedResultFull3() {
		int perPage = 3;
		int currentPage = this.testPagedResultFullWorker(perPage, DATA);
		Assert.assertEquals( 4 , currentPage );	
	}
	
	@Test
	public void testPagedResultFull5() {
		int perPage = 5;
		int currentPage = this.testPagedResultFullWorker(perPage, DATA);
		Assert.assertEquals( 2 , currentPage );	
	}
	
	@Test
	public void testPagedResultAlt() {
		PagedResult<Integer> result = DefaultPagedResult.newPagedResult( 3, DATA.size(), 1, DATA );
		boolean ok = this.testPagedResultWorker(result);
		Assert.assertTrue( ok );
	}
	
	@Test
	public void testPagedResultKo() {
		PagedResult<Integer> result = DefaultPagedResult.newPagedResult( Result.RESULT_CODE_KO );
		boolean ok = this.testPagedResultWorker(result);
		Assert.assertFalse( ok );
	}
	
	@Test
	public void testConstants() {
		Assert.assertEquals( Integer.valueOf( -1 ) , PagedResult.ELEMENT_COUNT_UNAVAILABLE );
		Assert.assertFalse( AbstractPagedResult.DEFAULT_SUPPORT_VIRTUAL_PAGING );
	}
	
	@Test
	public void testVirtualFinder() {
		DefaultVirtualFinder finder = new DefaultVirtualFinder( 5, 0, 10, 0, "TEST" );
		log.info( "finder.getSearchVirtualKey : {}", finder.getSearchVirtualKey() );
		log.info( "finder.getCurrentPage : {}", finder.getCurrentPage() );
		log.info( "finder.getPerPage : {}", finder.getPerPage() );
		log.info( "finder.getRealCurrentPage : {}", finder.getRealCurrentPage() );
		log.info( "finder.getRealPerPage : {}", finder.getRealPerPage() );
		log.info( "finder.isVirtualPagingUsed : {}", finder.isVirtualPagingUsed() );
		log.info( "finder : {}", finder );
		Assert.assertTrue( finder != null );
	}
	
	@Test
	public void testVirtualFinderFail() {
		DefaultVirtualFinder finder = new DefaultVirtualFinder( 5, 0 );
		log.info( "finder.isVirtualPagingUsed : {}", finder.isVirtualPagingUsed() );
		Assert.assertThrows( UnsupportedOperationException.class , () -> finder.getSearchVirtualKey() );
	}
	
	@Test
	public void testVirtualCache() {
		VirtualPageCache<Integer> cache = new VirtualPageCache<>();
		int perPage = 3;
		int elementCount = DATA.size();
		cache.addPageToCache( DefaultPagedResult.newPagedResult( perPage, elementCount, 0, DATA, 3, DATA.size(), "test" ) );
		Assert.assertNull( cache.getCachedPage( new DefaultVirtualFinder(perPage, elementCount, 3, DATA.size(), "test" ) ) );
	}

	@Test
	public void testResultHelper() {
		Assert.assertEquals( 1 , ResultHelper.createList( "a" ).size() );
	}

	@Test
	public void testPageInfo() {
		PageInfo info = new PageInfo( 1 , 10 );
		Assert.assertEquals( 1 , info.getNumber() );
		Assert.assertEquals( 10 , info.getSize() );
	}
	
}
