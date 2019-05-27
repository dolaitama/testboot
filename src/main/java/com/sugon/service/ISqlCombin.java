package com.sugon.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.sugon.core.exception.AppRunTimeException;
import com.sugon.core.model.Option;
import com.sugon.domain.DyView;
import com.sugon.domain.RightParam;

public interface ISqlCombin {

	public String getQuerySql(DyView view, HttpServletRequest req, String order, String sort, List<RightParam> rps) throws AppRunTimeException;

	public List<Option> findOptionsBySql(String selectSql, String attr,
			String attr2, String attr3) throws AppRunTimeException;
}