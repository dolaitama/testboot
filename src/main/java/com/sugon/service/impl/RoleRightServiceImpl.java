package com.sugon.service.impl;

import org.springframework.stereotype.Service;
import com.sugon.service.impl.base.BaseServiceImpl; 
import com.sugon.service.RoleRightService; 

/**
* RoleRight业务操作接口实现
*
* @author  administrator
* @version  [版本号, ${date}]
* @see  [相关类/方法]
* @since  [产品/模块版本]
*/
@Service
public class RoleRightServiceImpl extends BaseServiceImpl implements RoleRightService {

	public RoleRightServiceImpl() {
		super();
		this.mapper="RoleRightMapper";
	}
}
