/*
 *  Copyright Gergely Nagy <greg@webhejj.hu>
 *
 *  Licensed under the Apache License, Version 2.0;
 *  you may obtain a copy of the License at:
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 */
package hu.webhejj.commons.collections;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;

public class ListPagerTest {

	
	@Test
	public void testGotoPage() {
		ListPager pager = createPager(25,  100);
		Assert.assertTrue(pager.hasResults());
		
		pager.gotoPage(-1);
		assertPagerState(pager, 1, 0, 25);
		pager.gotoPage(0);
		assertPagerState(pager, 1, 0, 25);
		pager.gotoPage(1);
		assertPagerState(pager, 1, 0, 25);
		pager.gotoPage(2);
		assertPagerState(pager, 2, 25, 50);
		pager.gotoPage(3);
		assertPagerState(pager, 3, 50, 75);
		pager.gotoPage(4);
		assertPagerState(pager, 4, 75, 100);
		pager.gotoPage(5);
		assertPagerState(pager, 4, 75, 100);
	}

	@Test
	public void testTotalResults() {
		ListPager pager = createPager(25,  0);
		Assert.assertFalse(pager.hasResults());
		assertPagerState(pager, 0, 0, 0);
		Assert.assertEquals(0, pager.getNumberOfPages());

		pager = createPager(25,  1);
		Assert.assertTrue(pager.hasResults());
		assertPagerState(pager, 1, 0, 1);
		Assert.assertEquals(1, pager.getNumberOfPages());

		pager = createPager(25,  25);
		Assert.assertTrue(pager.hasResults());
		assertPagerState(pager, 1, 0, 25);
		Assert.assertEquals(1, pager.getNumberOfPages());

		pager = createPager(25,  26);
		Assert.assertTrue(pager.hasResults());
		assertPagerState(pager, 1, 0, 25);
		Assert.assertEquals(2, pager.getNumberOfPages());
	}
	
	@Test
	public void testGetPage() {
		List<Integer> list = createList(0, 100);
		ListPager pager = createPager(5,  list.size());
		pager.gotoPage(2);
		
		ListPage<Integer> listPage = pager.page(list);
		Assert.assertEquals(2, listPage.getCurrentPage());
		Assert.assertEquals(100, listPage.getTotalItemCount());
		Assert.assertEquals(5, listPage.getPageSize());
		Assert.assertEquals(createList(5,  10), listPage.getPage());
	}
	
	@Test
	public void testGetPages() {
		List<Integer> list = createList(0, 100);
		List<List<Integer>> pages = ListPager.pages(list, 25);
		Assert.assertEquals(4, pages.size());
		Assert.assertEquals(createList(0, 25), pages.get(0));
		Assert.assertEquals(createList(25, 50), pages.get(1));
		Assert.assertEquals(createList(50, 75), pages.get(2));
		Assert.assertEquals(createList(75, 100), pages.get(3));
	}

	
	protected void assertPagerState(ListPager pager, int currentPageNumber, int fromIndex, int toIndex) {
		Assert.assertEquals(currentPageNumber, pager.getCurrentPageNumber());
		Assert.assertEquals(fromIndex, pager.getFromIndex());
		Assert.assertEquals(toIndex, pager.getToIndex());
	}
	
	protected ListPager createPager(int pageSize, int listSize) {
		Paging paging = new Paging(0, pageSize);
		ListPager pager = new ListPager(paging, listSize);
		return pager;
	}
	
	protected List<Integer> createList(int from, int to) {
		int size = to - from;
		List<Integer> list = new ArrayList<Integer>(size);
		for(int i = from; i < to; i++) {
			list.add(i);
		}
		return list;
	}
}
