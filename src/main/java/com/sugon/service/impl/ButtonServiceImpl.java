package com.sugon.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.sugon.domain.Button;
import com.sugon.domain.Sysmenu;
import com.sugon.service.ButtonService;
import com.sugon.service.impl.base.BaseServiceImpl;

/**
* Button业务操作接口实现
*
* @author  administrator
* @version  [版本号, ${date}]
* @see  [相关类/方法]
* @since  [产品/模块版本]
*/
@Service
public class ButtonServiceImpl extends BaseServiceImpl implements ButtonService {

	public ButtonServiceImpl() {
		super();
		this.mapper="ButtonMapper";
	}

	@Override
	public Integer getMax() {
		return (Integer) this.baseDao.getByParam(this.mapper+".getMax", null);
	}

	@Override
	public List<Button> listByParam(Sysmenu menu) {
		return (List<Button>) this.baseDao.queryList(this.mapper+".listByParam", menu);
	}
}
