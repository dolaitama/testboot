package com.sugon.domain;

import com.sugon.domain.base.BaseEntity;

import java.io.Serializable;
/**
* Sysright 实体类
*
* @author  administrator
* @version  [版本号, ${date}]
* @see  [相关类/方法]
* @since  []
*/
@SuppressWarnings("serial")
public class Sysright extends BaseEntity implements Serializable {

	/**
	* 权限编码
	*/
	private String rightcode;

	/**
	* 父权限编码
	*/
	private String parentrightcode;

	/**
	* 权限名称
	*/
	private String rightname;

	/**
	* 权限类型（暂未启用）
	*/
	private String righttype;

	/**
	* 权限描述
	*/
	private String rightdesc;

	/**
	* 排序
	*/
	private Integer showorder;

	/**
	* 所属系统（暂未启用）
	*/
	private String ownersystem;

	/**
	* 菜单id
	*/
	private Long menuid;

	public String getRightcode() {
		return rightcode;
	}

	public void setRightcode(String rightcode) {
		this.rightcode = rightcode;
	}

	public String getParentrightcode() {
		return parentrightcode;
	}

	public void setParentrightcode(String parentrightcode) {
		this.parentrightcode = parentrightcode;
	}

	public String getRightname() {
		return rightname;
	}

	public void setRightname(String rightname) {
		this.rightname = rightname;
	}

	public String getRighttype() {
		return righttype;
	}

	public void setRighttype(String righttype) {
		this.righttype = righttype;
	}

	public String getRightdesc() {
		return rightdesc;
	}

	public void setRightdesc(String rightdesc) {
		this.rightdesc = rightdesc;
	}

	public Integer getShoworder() {
		return showorder;
	}

	public void setShoworder(Integer showorder) {
		this.showorder = showorder;
	}

	public String getOwnersystem() {
		return ownersystem;
	}

	public void setOwnersystem(String ownersystem) {
		this.ownersystem = ownersystem;
	}

	public Long getMenuid() {
		return menuid;
	}

	public void setMenuid(Long menuid) {
		this.menuid = menuid;
	}

}
