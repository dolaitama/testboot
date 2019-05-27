package com.sugon.core.exception;

public class TiancheException extends RuntimeException {

	/**  
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)  
	 */ 
	private static final long serialVersionUID = -1628753112229676056L;
	private String localMessage;
	private ErrorStatus errorStatus;

	public TiancheException() {
		super();
	}
	
	public TiancheException(Exception exception){
		TiancheException e = (TiancheException) exception;
		this.localMessage = e.getLocalMessage();
		this.errorStatus = e.getErrorStatus();
	}
	
	public TiancheException(ErrorStatus errorStatus){
		this(errorStatus,errorStatus.getMessage(),null);
	}

	public TiancheException(String message) {
		this(message, null);
	}

	public TiancheException(ErrorStatus errorStatus, String message) {
		this(errorStatus, message, null);
	}

	public TiancheException(String message, Throwable cause) {
		this(ErrorStatus.UNKOWN, message, cause);
	}

	public TiancheException(ErrorStatus errorStatus, String message,
			Throwable cause) {
		super(message, cause);
		this.errorStatus = errorStatus;
		this.localMessage = message;
	}

	public String getLocalMessage() {
		return localMessage;
	}

	public void setLocalMessage(String localMessage) {
		this.localMessage = localMessage;
	}

	public ErrorStatus getErrorStatus() {
		return errorStatus;
	}

	public void setErrorStatus(ErrorStatus errorStatus) {
		this.errorStatus = errorStatus;
	}

}
