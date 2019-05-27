package com.sugon.core.model;

import com.sugon.core.exception.UnsupportedValueException;

public enum Status {
	
	/**
	 * 未处理
	 */
	UNDO("0"),
	
	/**
	 * 已处理
	 */
	DONE("1"),
	
	/**
	 * 异常
	 */
	ERROR("9");
	
	private String index;

	private Status(String index) {
		this.index = index;
	}

	public String idx() {
		return index;
	}

	public static Status indexOf(String index) {
		for (Status item : Status.values()) {
			if (item.index == index) {
				return item;
			}
		}
		throw new UnsupportedValueException("枚举类型 Status 不支持整型值 " + index);
	}
	
	public static Status nameOf(String name) {
		for (Status item : Status.values()) {
			if (item.toString().equals(name)) {
				return item;
			}
		}
		throw new UnsupportedValueException("枚举类型 Status 不支持字面值 " + name);
	}
}
