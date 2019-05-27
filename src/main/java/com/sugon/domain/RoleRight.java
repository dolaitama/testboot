package com.sugon.domain;

import java.io.Serializable;

import com.sugon.domain.base.BaseEntity;
/**
* RoleRight 实体类
*
* @author  administrator
* @version  [版本号, ${date}]
* @see  [相关类/方法]
* @since  []
*/
@SuppressWarnings("serial")
public class RoleRight extends BaseEntity implements Serializable {

	/**
	* 
	*/
	private Long id;

	/**
	* 角色id
	*/
	private Long roleid;

	/**
	* 权限编码
	*/
	private String rightcode;
	
	private Sysright sysright;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getRoleid() {
		return roleid;
	}

	public void setRoleid(Long roleid) {
		this.roleid = roleid;
	}

	public String getRightcode() {
		return rightcode;
	}

	public void setRightcode(String rightcode) {
		this.rightcode = rightcode;
	}

	public Sysright getSysright() {
		return sysright;
	}

	public void setSysright(Sysright sysright) {
		this.sysright = sysright;
	}

}
