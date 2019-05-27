package com.sugon.domain;

import com.sugon.domain.base.BaseEntity;

import java.io.Serializable;
/**
* MenuRight 实体类
*
* @author  administrator
* @version  [版本号, ${date}]
* @see  [相关类/方法]
* @since  []
*/
@SuppressWarnings("serial")
public class MenuRight extends BaseEntity implements Serializable {

	/**
	* 
	*/
	private Long id;

	/**
	* 
	*/
	private Long menuid;

	/**
	* 
	*/
	private String rightcode;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getMenuid() {
		return menuid;
	}

	public void setMenuid(Long menuid) {
		this.menuid = menuid;
	}

	public String getRightcode() {
		return rightcode;
	}

	public void setRightcode(String rightcode) {
		this.rightcode = rightcode;
	}

}
