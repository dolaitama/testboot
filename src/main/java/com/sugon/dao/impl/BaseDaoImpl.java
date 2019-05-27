package com.sugon.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Service;

import com.sugon.dao.base.BaseDao;
import com.sugon.domain.base.BaseEntity;
import com.sugon.response.DataGrid;

@Service("baseDaoImpl")
public class BaseDaoImpl extends SqlSessionDaoSupport implements BaseDao {
	
	@Override
	@Resource
	public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
		super.setSqlSessionFactory(sqlSessionFactory);
	}

	@Override
	public void insert(String statement, Object entity) {
		this.getSqlSession().insert(statement, entity);
	}

	@Override
	public void update(String statement, Object entity) {
		this.getSqlSession().update(statement, entity);
	}
	
	@Override
	public void delete(String statement, Object entity) {
		this.getSqlSession().delete(statement, entity);
	}

	@Override
	public Object getByParam(String statement, Object entity) {
		return this.getSqlSession().selectOne(statement, entity);
	}

	@Override
	public List<?> queryList(String statement, Object entity) {
		return this.getSqlSession().selectList(statement, entity);
	}

	@Override
	public DataGrid queryDataGrid(String statement, Object entity) {
		BaseEntity baseEntity = (BaseEntity) entity;
		baseEntity.initPage();
		List<?> list = this.getSqlSession().selectList(statement, baseEntity);
		int rowSise = this.getSqlSession().selectOne(statement + "Count", baseEntity);
		DataGrid dg = new DataGrid();
		dg.setRows(list);
		dg.setTotal((long) rowSise);
		return dg;
	}
	
}
