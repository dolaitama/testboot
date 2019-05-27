package com.sugon.dao.base;

import java.util.List;

import com.sugon.response.DataGrid;

public interface BaseDao {
	// 添加一个实体
	public void insert(String sqlId, Object entity);
	
	// 按实体来删除
	public void delete(String sqlId, Object entity);

	// 按实体来更新
	public void update(String sqlId, Object entity);

	// 按实体获取单个实体
	public Object getByParam(String sqlId, Object entity);

	// 按实体来查询多个实体
	public List<?> queryList(String sqlId, Object entity);

	// 分页查询
	public DataGrid queryDataGrid(String sqlId, Object entity);
}
