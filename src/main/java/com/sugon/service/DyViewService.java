package com.sugon.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.sugon.core.model.GridPageInfo;
import com.sugon.core.model.QueryBean;
import com.sugon.domain.DyView;
import com.sugon.domain.RightParam;
import com.sugon.service.base.BaseService;

public interface DyViewService extends BaseService {

	public GridPageInfo businessDataProcess(String dyViewId, HttpServletRequest req, String sort, String order, List<RightParam> rps) throws Exception;
	
	public DyView businessHeadProcess(String viewId, HttpServletRequest req,
			String pageSize,String pageIndex,String sort,String order) throws Exception;

	public QueryBean getComposeQueryConBean(String dyViewId,
			HttpServletRequest req, String sort, String order, List<RightParam> rps) throws Exception;
	
}
