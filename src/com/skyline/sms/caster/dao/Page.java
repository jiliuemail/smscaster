package com.skyline.sms.caster.dao;

public class Page {
	
	private int firstResultIndex;
	
	private int pageSize;
	
	private int totalResultCount;
	
	private int currentPageNumber;
	
	private int pageCount;
	
	public Page() {
		this(15);
	}

	public Page(int pageSize) {
		super();
		this.pageSize = pageSize;
	}
	
	

	protected int getTotalResultCount() {
		return totalResultCount;
	}

	public int getFirstResultIndex(){
		return firstResultIndex;
	}

	public int getPageSize(){
		return pageSize;
	}

	public void setTotalResultCount(int totalResultCount) {
		this.totalResultCount = totalResultCount;
		pageCount = totalResultCount / pageSize + totalResultCount % pageSize;
	}
	
	
	public void firstPage(){
		firstResultIndex = 1;
		currentPageNumber = 1;
	}
	
	public void nextPage(){
		if (currentPageNumber >= pageCount) {
			return;
		}
		currentPageNumber ++;
		firstResultIndex = firstResultIndex + pageSize;
	}
	
	public void previousPage(){
		if (currentPageNumber <= 1) {
			return ;
		}
		currentPageNumber --;
		firstResultIndex = firstResultIndex - pageSize;
		firstResultIndex = firstResultIndex < 1 ? 1 :firstResultIndex;
	}
	
	public void goPage(int pageNumber){
		if (pageNumber < 1 || pageNumber > pageCount) {
			return;
		}
		firstResultIndex = (pageNumber - 1) * pageSize + 1;
		currentPageNumber = pageNumber;
	}
}
