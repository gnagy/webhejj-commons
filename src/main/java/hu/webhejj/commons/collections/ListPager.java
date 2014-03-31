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
import java.util.Collections;
import java.util.List;

public class ListPager {

	private int pageSize;
	private int fromIndex;
	private int toIndex;
	private int pageIndex;
	private int lastPageIndex;
	private int lastResultIndex;
	private boolean hasResults;

	public ListPager(Paging paging, int totalResults) {
		
		if(totalResults > 0) {
			hasResults  = true;
			pageSize = Math.max(0, paging.getPageSize());
			lastResultIndex = totalResults - 1;
			lastPageIndex = lastResultIndex / pageSize;
			
			gotoPage(paging.getPageNumber());
		}
		// else everything stays at == 0, hasResults == false
	}

	public void gotoPage(int pageNumber) {
		pageIndex = Math.min(lastPageIndex, Math.max(0, pageNumber - 1));
		fromIndex = Math.min(lastResultIndex, pageIndex * pageSize);
		toIndex = Math.min(lastResultIndex + 1, fromIndex + pageSize);
	}
	public void skipPages(int numberOfPages) {
		gotoPage(pageIndex + 1 + numberOfPages);
	}
	public void nextPage() {
		gotoPage(pageIndex + 2);
	}
	public void previousPage() {
		gotoPage(pageIndex);
	}
	
	/** @return from index, inclusive */
	public int getFromIndex() {
		return fromIndex;
	}
	/** @return to index, exclusive */
	public int getToIndex() {
		return toIndex;
	}
	/** @return total number of results */
	public int getLastResultIndex() {
		return lastResultIndex;
	}
	public boolean hasResults() {
		return hasResults;
	}
	public int getCurrentPageNumber() {
		return hasResults() ? pageIndex + 1 : 0;
	}
	public int getNumberOfPages() {
		return hasResults() ? lastPageIndex + 1 : 0;
	}
	
	@Override
	public String toString() {
		return String.format("Paged [%d...%d)  %d/%d", fromIndex, toIndex, getCurrentPageNumber(), getNumberOfPages());
	}
	
	public <T> ListPage<T> page(List<T> list) {
		if(hasResults) {
			List<T> subList = new ArrayList<T>(toIndex - fromIndex);
			for(int i = fromIndex; i < toIndex; i++) {
				subList.add(list.get(i));
			}
			return new ListPage<T>(pageSize, getCurrentPageNumber(), getLastResultIndex() + 1, subList);
		
		} else {
			return new ListPage<T>(pageSize, 0, 0, Collections.<T>emptyList());
		}
	}

	public static <T> List<List<T>> pages(List<T> list, int pageSize) {
		if(list == null || list.size() == 0) {
			return Collections.emptyList();
		}
		ListPager listPager = new ListPager(new Paging(0, pageSize), list.size());
		int numberOfPages = listPager.getNumberOfPages();
		if(numberOfPages == 1) {
			return Collections.singletonList(list);
		
		} else {
			List<List<T>> pages = new ArrayList<List<T>>(numberOfPages);
			for(int i = 0; i < numberOfPages; i++) {
				pages.add(listPager.page(list).getPage());
				listPager.nextPage();
			}
			return pages;
		}
	}
}
