package com.sugon.response;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings({"serial","rawtypes"})
public class DataGrid implements Serializable {

	private Long total = 0L;
	
	private List rows = new ArrayList();

	public Long getTotal() {
		return total;
	}

	public void setTotal(Long total) {
		this.total = total;
	}

	public List getRows() {
		return rows;
	}

	public void setRows(List rows) {
		this.rows = rows;
	}

}
