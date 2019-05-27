package com.sugon.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.sugon.core.model.DataGrid;
import com.sugon.core.model.GridPageInfo;
import com.sugon.dao.base.BaseDao;
import com.sugon.service.PageResultSet;

@Service
public class PageResultSetImpl implements PageResultSet{
	
	private static final Logger logger = LoggerFactory.getLogger(PageResultSet.class);

	private GridPageInfo pageInfo;
	
	private int curPage = 1; // 当前页

	private int pageSize = 20; // 每页显示的行数

	private long rowsCount = 0; // 总行数

	private int pageCount = 0; // 总页数

	private String sql = null;
	
	@Resource
	private BaseDao baseDao;
	
	@Override
	public void init(DataGrid dataGrid){
		this.pageSize = dataGrid.getRows();
		this.curPage = dataGrid.getPage();
		this.rowsCount=dataGrid.getTotal();
	}
	
	@Override
	public void init(GridPageInfo pageInfo){
		this.pageInfo = pageInfo;
		this.pageSize = pageInfo.getRp();
		this.curPage = pageInfo.getPage();
		this.rowsCount=pageInfo.getTotal();
	}
	
	/**
	 * 获得进行记录总数的查询
	 * 
	 * @param param
	 * @param rowMapper
	 * @return
	 * @throws Exception
	 */
	@Override
	public long queryRowsCount(Object[] param) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		long count = 0;
		if (StringUtils.isBlank(this.sql)) {
			return 0;
		}
		String tmpsql = "SELECT COUNT(1) FROM (" + sql + ") l";
		map.put("sql", tmpsql);
		try {
			count = (long) this.baseDao.getByParam("DyViewMapper.count", map);
		} catch (Exception e) {
			throw e ;
		}
		return count;
	}

	/**
	 * 查询列表20160320lsx
	 * @param param
	 * @param sql
	 * @param con
	 * @param colFields
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<Map<String, Object>> getData(String sql, Map<String, Element> colFields) throws Exception {
		if(StringUtils.isBlank(sql)){
			return null;
		}
		try {
			this.sql = sql;
			if(rowsCount==0){
				this.rowsCount = queryRowsCount(null);
				this.pageInfo.setTotal(this.rowsCount);
			}
			this.pageCount = (int) Math.ceil((double) rowsCount / pageSize);
			this.pageInfo.setPageCount(pageCount);
			int startNum = 0;
			if (this.pageCount<=1&&this.curPage<=1) {
				this.pageInfo.setPage(1);
				startNum = 0;
			} else {
				startNum = (curPage - 1) * pageSize;
			}
			this.pageInfo.setQuerySql(sql);
			String tmpsql=sql+ " limit " + startNum + " , " + pageSize;
			logger.info(tmpsql);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("sql", tmpsql);
			return (List<Map<String, Object>>) this.baseDao.queryList("DyViewMapper.excuteQuery", map);
		} catch (Exception e) {
			throw e;
		}
	}

}
