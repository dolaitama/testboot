package com.sugon.core.model;

import java.io.Serializable;
import java.util.Date;

import com.sugon.util.DateUtil;

/**
 * 主键id
 * @author admin
 *
 */
public abstract class IdModel implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public IdModel(){
		this.createTime = DateUtil.now();
		this.updateTime = DateUtil.now();
	}

	/**
	 * 同步表id
	 */
	protected Long id;
	
	/**
	 * 创建时间
	 */
	protected Date createTime;
	
	/**
	 * 修改时间
	 */
	protected Date updateTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
}