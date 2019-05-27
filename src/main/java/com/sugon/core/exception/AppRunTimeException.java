package com.sugon.core.exception;

public class AppRunTimeException extends Exception
{
  private static final long serialVersionUID = 1L;
  String code = "";
  String type = "";

  public String code() {
    return this.code;
  }

  public String type() {
    return this.type;
  }

  public AppRunTimeException()
  {
  }

  public AppRunTimeException(String msg)
  {
    super(msg);
  }

  public AppRunTimeException(String message, Throwable cause) {
    super(message, cause);
  }

  public AppRunTimeException(Throwable cause) {
    super(cause);
  }

  public AppRunTimeException(ErrorCode code, String msg) {
    super(msg);
    this.code = code.code;
    this.type = code.type;
  }

  public AppRunTimeException(ErrorCode code, String msg, Throwable th) {
    super(msg, th);
    this.code = code.code;
    this.type = code.type;
  }

  public static enum ErrorCode
  {
    IFACE_SUCCESS("0000", "0"), 
    UNAUTHORIZED("0001", "1"), BAD_REQUEST("0002", "1"), 
    PARAM_LEN_ERROR("0003", "1"), NOT_FOUND("0004", "1"), UNKNOWN("9999", "1"), 
    FEE_INSUFFICIENT("0001", "2"), BALLANCE_INSUFF("0002", "2"), BUS_NO_PERMIT("0003", "2");

    public final String code;
    public final String type;

    private ErrorCode(String c, String t) { this.code = c;
      this.type = t; }

    public static ErrorCode getErrorCode(String c, String t)
    {
      for (ErrorCode err : values()) {
        if ((err.code.equals(c)) && (err.type.equals(t)))
          return err;
      }
      return UNKNOWN;
    }
  }
}