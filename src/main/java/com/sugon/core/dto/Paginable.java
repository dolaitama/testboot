package com.sugon.core.dto;


public interface Paginable {

	public int getTotalCount();


	public int getTotalPage();

	public int getPageSize();


	public int getPageNo();


	public boolean isFirstPage();


	public boolean isLastPage();


	public int getNextPage();


	public int getPrePage();
}
