package com.sugon.core.model;

import java.util.List;

import com.google.common.collect.Lists;
import com.sugon.domain.Sysright;

public class SysrightBean {
	
	public SysrightBean(Sysright sysright){
		this.sysright=sysright;
	}

	private Sysright sysright;
	
	private List<Sysright> childList=Lists.newArrayList();
	
	private List<SysrightBean> childBeanList=Lists.newArrayList();

	public Sysright getSysright() {
		return sysright;
	}

	public void setSysright(Sysright sysright) {
		this.sysright = sysright;
	}

	public List<Sysright> getChildList() {
		return childList;
	}

	public void setChildList(List<Sysright> childList) {
		this.childList = childList;
	}

	public List<SysrightBean> getChildBeanList() {
		return childBeanList;
	}

	public void setChildBeanList(List<SysrightBean> childBeanList) {
		this.childBeanList = childBeanList;
	}
	
	
	

}
