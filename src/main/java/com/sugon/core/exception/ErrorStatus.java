package com.sugon.core.exception;

public enum ErrorStatus {

	BAD_PARAM (1001 , "不合法的参数"),
	TIME_ERROR (1002 , "超出时间范围"),
	UNKOWN (1010, "未知错误"),
	NEED_LOGIN (1009, "需要登录"),
	NOT_TRADEPWD (1020, "还未设置交易密码"),
	NOT_TRADEBANK (1030, "还未绑定银行卡");
	
	private final int code;
	private final String message;
	private ErrorStatus(int code, String message) {
		this.code = code;
		this.message = message;
	}
	public int getCode() {
		return code;
	}
	public String getMessage() {
		return message;
	}
	
}
