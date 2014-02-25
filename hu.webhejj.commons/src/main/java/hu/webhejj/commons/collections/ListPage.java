/*
 *  Copyright Gergely Nagy <greg@webhejj.hu>
 *
 *  Licensed under the Apache License, Version 2.0; 
 *  you may obtain a copy of the License at:
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 */
package hu.webhejj.commons.collections;

import java.util.List;

public class ListPage<T> {

	private int pageSize;
	private int currentPage;
	private int totalItemCount;
	private List<T> page;

	public ListPage() {
	}
	public ListPage(int pageSize, int currentPage, int totalItemCount, List<T> page) {
		this.pageSize = pageSize;
		this.currentPage = currentPage;
		this.totalItemCount = totalItemCount;
		this.page = page;
	}
	
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	public int getTotalItemCount() {
		return totalItemCount;
	}
	public void setTotalItemCount(int totalPages) {
		this.totalItemCount = totalPages;
	}
	public List<T> getPage() {
		return page;
	}
	public void setPage(List<T> page) {
		this.page = page;
	}
	
	@Override
	public String toString() {
		int fromIndex = (currentPage - 1) * pageSize;
		int toIndex = fromIndex + page.size();
		int totalPages = (totalItemCount + pageSize - 1) / pageSize;
		return String.format("Page [%d...%d) %d/%d %s", fromIndex, toIndex, currentPage, totalPages, page);
	}
}
