package com.sugon.core.dto;

import java.io.Serializable;

import com.fasterxml.jackson.databind.JsonSerializer;
import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.GsonBuilder;
import com.sugon.core.dto.gson.GsonUtils;

/**
 * web端向pda端响应时的对象（需要经过gson处理后返回给请求段）
 * 
 * @类名: PdaResponse.java
 */
public class PdaResponse<T> implements Serializable {

	private static final long serialVersionUID = -7816945325851639128L;

	private static GsonBuilder gsonBuilder = GsonUtils.createCommonBuilder();

	/**
	 * 是否成功
	 */
	private boolean success;

	/**
	 * 提示消息
	 */
	private String message;
	
	/**
	 * 单行数据
	 */
	private T data;

	/**
	 * 查询记录数量
	 */
	private long total = 0;

	public PdaResponse() {
		this(true, "", null);
	}

	public PdaResponse(boolean success, String message, T data) {
		this.success = success;
		this.message = message;
		this.data = data;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	/**
	 * 将本对象以JSON的格式打印出来。
	 */
	public String toString() {
		return gsonBuilder.create().toJson(this);
	}

	public PdaResponse<T> appendJsonIncludes(String... propNames) {
		return this;
	}

	public PdaResponse<T> appendJsonExcludes(final String... propNames) {

		gsonBuilder.addSerializationExclusionStrategy(new ExclusionStrategy() {

			@Override
			public boolean shouldSkipField(FieldAttributes attr) {
				for (String propName : propNames) {
					if (propName.equals(attr)) {
						return true;
					}
				}
				return false;
			}

			@Override
			public boolean shouldSkipClass(Class<?> clazz) {
				return false;
			}
		});
		return this;
	}

	public PdaResponse<T> appendJsonTransformers(JsonSerializer<?> transformer, Class<?>... classes) {
		for (Class<?> clazz : classes) {
			gsonBuilder.registerTypeHierarchyAdapter(clazz, transformer);
		}
		return this;
	}

}
