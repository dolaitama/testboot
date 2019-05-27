package com.sugon.domain;

import com.sugon.domain.base.BaseEntity;

import java.io.Serializable;
/**
* Button 实体类
*
* @author  administrator
* @version  [版本号, ${date}]
* @see  [相关类/方法]
* @since  []
*/
@SuppressWarnings("serial")
public class Button extends BaseEntity implements Serializable {

	/**
	* 
	*/
	private Integer id;

	/**
	* 按钮名称
	*/
	private String name;

	/**
	* 按钮编码
	*/
	private Integer code;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

}
