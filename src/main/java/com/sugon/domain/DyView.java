package com.sugon.domain;

import com.sugon.domain.base.BaseEntity;

import java.io.Serializable;
/**
* DyView 实体类
*
* @author  administrator
* @version  [版本号, ${date}]
* @see  [相关类/方法]
* @since  []
*/
@SuppressWarnings("serial")
public class DyView extends BaseEntity implements Serializable {

	/**
	* 主键
	*/
	private Long id;

	/**
	* 列表字段
	*/
	private String defcol;

	/**
	* 列表查询条件
	*/
	private String querydef;

	/**
	* 列表主查询语句
	*/
	private String defsql;

	/**
	* 列表js方法
	*/
	private String defjs;

	/**
	* 导出字段（自动生成无需编辑）
	*/
	private String expinfo;

	/**
	* echarts option编辑器js
	*/
	private String optionsetter;

	/**
	* 报表类型0表格1图表
	*/
	private Integer type;

	/**
	* echarts配置项
	*/
	private String eoption;

	/**
	* 模型
	*/
	private String model;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDefcol() {
		return defcol;
	}

	public void setDefcol(String defcol) {
		this.defcol = defcol;
	}

	public String getQuerydef() {
		return querydef;
	}

	public void setQuerydef(String querydef) {
		this.querydef = querydef;
	}

	public String getDefsql() {
		return defsql;
	}

	public void setDefsql(String defsql) {
		this.defsql = defsql;
	}

	public String getDefjs() {
		return defjs;
	}

	public void setDefjs(String defjs) {
		this.defjs = defjs;
	}

	public String getExpinfo() {
		return expinfo;
	}

	public void setExpinfo(String expinfo) {
		this.expinfo = expinfo;
	}

	public String getOptionsetter() {
		return optionsetter;
	}

	public void setOptionsetter(String optionsetter) {
		this.optionsetter = optionsetter;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getEoption() {
		return eoption;
	}

	public void setEoption(String eoption) {
		this.eoption = eoption;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

}