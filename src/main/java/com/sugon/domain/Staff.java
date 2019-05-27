package com.sugon.domain;

import com.sugon.domain.base.BaseEntity;

import java.io.Serializable;
/**
* Staff 实体类
*
* @author  administrator
* @version  [版本号, ${date}]
* @see  [相关类/方法]
* @since  []
*/
@SuppressWarnings("serial")
public class Staff extends BaseEntity implements Serializable {

	/**
	* 
	*/
	private Long id;

	/**
	* 登录名称
	*/
	private String loginname;

	/**
	* 登录密码
	*/
	private String loginpwd;

	/**
	* 姓名
	*/
	private String name;

	/**
	* 性别0男1女
	*/
	private String sex;

	/**
	* 电话
	*/
	private String tel;

	/**
	* 备注
	*/
	private String remark;

	/**
	* 状态0不可用1可用
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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLoginname() {
		return loginname;
	}

	public void setLoginname(String loginname) {
		this.loginname = loginname;
	}

	public String getLoginpwd() {
		return loginpwd;
	}

	public void setLoginpwd(String loginpwd) {
		this.loginpwd = loginpwd;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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

}
