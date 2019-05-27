package com.sugon.service.impl;

import org.springframework.stereotype.Service;

import com.sugon.service.RightParamService;
import com.sugon.service.impl.base.BaseServiceImpl;

/**
* RightParam业务操作接口实现
*
* @author  administrator
* @version  [版本号, ${date}]
* @see  [相关类/方法]
* @since  [产品/模块版本]
*/
@Service
public class RightParamServiceImpl extends BaseServiceImpl implements RightParamService {

	public RightParamServiceImpl() {
		super();
		this.mapper="RightParamMapper";
	}

}
