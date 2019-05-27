package com.sugon.core.exception;

/**
 * 调用了尚未提供支持的方法时抛出。
 * @author Fred
 * 2008-9-26 下午01:43:51
 */
public class UnsupportedValueException extends GenericException {

    private static final long serialVersionUID = -8817834237727533811L;

	public UnsupportedValueException() {
        super();
    }

    public UnsupportedValueException(String message) {
        super(message);
    }

    public UnsupportedValueException(Throwable cause) {
        super(cause);
    }

    public UnsupportedValueException(String message, Throwable cause) {
        super(message, cause);
    }

}
