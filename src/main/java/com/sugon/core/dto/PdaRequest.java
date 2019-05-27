package com.sugon.core.dto;

import java.io.Serializable;

/**
 * PDA请求对象
 * 
 * @类名: PdaRequest.java
 */
public class PdaRequest<T> implements Serializable {

	private static final long serialVersionUID = 932755814693221291L;

	/**
	 * 请求的其他信息
	 */
	private T data;

	/**
	 * 分页对象。如果返回对象PdaResponse的data为list，则此参数有效。
	 */
	private Pagination pagination;

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public Pagination getPagination() {
		return pagination;
	}

	public void setPagination(Pagination pagination) {
		this.pagination = pagination;
	}

}
