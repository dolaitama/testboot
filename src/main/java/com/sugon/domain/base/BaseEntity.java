package com.sugon.domain.base;

public class BaseEntity {

	private int page;// 当前页码
	private int start = 0; // 开始页码
	private int rows = 20; // 默认每页大小
	private String order;
	
	public String getOrder(){
		return order;
	}
	
	public void setOrder(String order){
		this.order = order;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}
	
	public void initPage(){
		if(this.page <=0){
			this.page=1;
		}
		this.start = (this.page - 1)*this.rows;
	}
}
