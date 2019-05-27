package com.sugon.service;

import com.sugon.core.model.QueryBean;

public interface BusDataExportExcel {

	public String exportFile(String fileInfo, String pageTitle,
			String titleText,QueryBean queryBean,String maxExportIndex)throws Exception;
}
