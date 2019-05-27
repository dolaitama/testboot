package com.sugon.core.model;

import com.sugon.domain.DyView;


public class ListParams {
	
	private String param = "";// 传递过来的参数
	private String dyViewId;
	private DyView dyView;
	private boolean winIsClose = false;

	public String getParam() {
		return param;
	}

	public void setParam(String param) {
		this.param = param;
	}

	public String getDyViewId() {
		return dyViewId;
	}

	public void setDyViewId(String dyViewId) {
		this.dyViewId = dyViewId;
	}

	public DyView getDyView() {
		return dyView;
	}

	public void setDyView(DyView dyView) {
		this.dyView = dyView;
	}

	public boolean isWinIsClose() {
		return winIsClose;
	}

	public void setWinIsClose(boolean winIsClose) {
		this.winIsClose = winIsClose;
	}

}
