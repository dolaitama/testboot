package com.sugon.service.impl.base;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.sugon.dao.base.BaseDao;
import com.sugon.response.DataGrid;
import com.sugon.service.base.BaseService;

@Service
public class BaseServiceImpl implements BaseService {
	
	public String mapper="";
	@Autowired
	@Qualifier("baseDaoImpl")
	public BaseDao baseDao;

	@Override
	public void insert(Object entity) throws Exception {
		this.baseDao.insert(mapper+".insert", entity);
	}
	
	@Override
	public void delete(Object entity) throws Exception {
		this.baseDao.delete(mapper+".delete", entity);
	}
	
	@Override
	public void update(Object entity) throws Exception {
		this.baseDao.update(mapper+".update", entity);
	}

	@Override
	public Object getByParam(Object entity) {
		return this.baseDao.getByParam(mapper+".getByParam", entity);
	}

	@Override
	public List<?> queryList(Object entity) {
		return this.baseDao.queryList(mapper+".queryList", entity);
	}

	@Override
	public DataGrid queryDataGrid(Object entity) {
		return this.baseDao.queryDataGrid(mapper+".queryDataGrid", entity);
	}

}
