package com.sugon.domain;

import com.sugon.domain.base.BaseEntity;

import java.io.Serializable;
/**
* Sysmenu 实体类
*
* @author  administrator
* @version  [版本号, ${date}]
* @see  [相关类/方法]
* @since  []
*/
@SuppressWarnings("serial")
public class Sysmenu extends BaseEntity implements Serializable {

	/**
	* 
	*/
	private Long id;

	/**
	* 权限编码
	*/
	private String rightcode;

	/**
	* 父菜单id
	*/
	private Long parentid;

	/**
	* 动态配置表单id
	*/
	private Long dyviewid;

	/**
	* 菜单显示内容
	*/
	private String menutext;

	/**
	* 菜单链接
	*/
	private String url;

	/**
	* 排序
	*/
	private Integer orderno;

	/**
	* 图标（暂未使用）
	*/
	private String iconcls;

	/**
	* 图标（暂未使用）
	*/
	private String icon;

	/**
	* 暂未使用
	*/
	private String markurl;

	/**
	* 0不可用1可用
	*/
	private String status;

	/**
	* 包含按钮
	*/
	private Integer hascode;

	/**
	* 按钮code
	*/
	private Integer code;
	
	private Integer urltype;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRightcode() {
		return rightcode;
	}

	public void setRightcode(String rightcode) {
		this.rightcode = rightcode;
	}

	public Long getParentid() {
		return parentid;
	}

	public void setParentid(Long parentid) {
		this.parentid = parentid;
	}

	public Long getDyviewid() {
		return dyviewid;
	}

	public void setDyviewid(Long dyviewid) {
		this.dyviewid = dyviewid;
	}

	public String getMenutext() {
		return menutext;
	}

	public void setMenutext(String menutext) {
		this.menutext = menutext;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Integer getOrderno() {
		return orderno;
	}

	public void setOrderno(Integer orderno) {
		this.orderno = orderno;
	}

	public String getIconcls() {
		return iconcls;
	}

	public void setIconcls(String iconcls) {
		this.iconcls = iconcls;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getMarkurl() {
		return markurl;
	}

	public void setMarkurl(String markurl) {
		this.markurl = markurl;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	public Integer getUrltype() {
		return urltype;
	}

	public void setUrltype(Integer urltype) {
		this.urltype = urltype;
	}

}
