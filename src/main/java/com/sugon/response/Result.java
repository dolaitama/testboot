package com.sugon.response;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

/**
 * 返回结果类
 * @author lsx
 * @date 2019年5月27日
 */
public class Result {
	private boolean success = true;
	private int code = 1000;
	private String message = "暂无相关信息";
	private String error = "操作异常";
	private Map<String, Object> data = new HashMap<String, Object>();

	public static Result create() {
		return new Result();
	}

	public Result add(Map<String, Object> map) {
		this.data.putAll(map);
		return this;
	}
	
	public Result fail(){
		return this.fail(null, null);
	}
	
	public Result fail(String message){
		return this.fail(null, message);
	}
	
	public Result fail(String error, String message){
		this.success=false;
		this.code=1010;
		if(StringUtils.isNotBlank(error)){
			this.error=error;
		}
		if(StringUtils.isNotBlank(message)){
			this.message=message;
		}
		return this;
	}

	public Result addMapString(Map<String, String> map) {
		this.data.putAll(map);
		return this;
	}

	public Result add(String key, Object value) {
		data.put(key, value);
		return this;
	}

	public boolean isSuccess() {
		return success;
	}

	public Result setSuccess(boolean success) {
		this.success = success;
		return this;
	}

	public Map<String, Object> getData() {
		return data;
	}

	public void setData(Map<String, Object> data) {
		this.data = data;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public Result(){}
	public Result( boolean success ,int code , String message , String error ){
		this.code=code;
		this.success=success;
		this.message=message;
		this.error=error;
	}

	public  static Result SUCCESS(int code,String message){
		return new Result(true,code,message,null);
	}
}
