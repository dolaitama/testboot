package com.sugon.domain;

import com.sugon.domain.base.BaseEntity;

import java.io.Serializable;
/**
* Role 实体类
*
* @author  administrator
* @version  [版本号, ${date}]
* @see  [相关类/方法]
* @since  []
*/
@SuppressWarnings("serial")
public class Role extends BaseEntity implements Serializable {

	/**
	* 角色id
	*/
	private Long id;

	/**
	* 角色名称
	*/
	private String name;

	/**
	* 0不可用1可用
	*/
	private String status;

	/**
	* 创建时间
	*/
	private String createtime;

	/**
	* 更新时间
	*/
	private String updatetime;

	/**
	* 包含按钮
	*/
	private Integer hascode;

	/**
	* 按钮code
	*/
	private Integer code;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCreatetime() {
		return createtime;
	}

	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}

	public String getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(String updatetime) {
		this.updatetime = updatetime;
	}

	public Integer getHascode() {
		return hascode;
	}

	public void setHascode(Integer hascode) {
		this.hascode = hascode;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

}
