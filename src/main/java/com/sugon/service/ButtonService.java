package com.sugon.service;

import java.util.List;

import com.sugon.domain.Button;
import com.sugon.domain.Sysmenu;
import com.sugon.service.base.BaseService;
/**
* Button业务操作接口
*
* @author  administrator
* @version  [版本号, ${date}]
* @see  [相关类/方法]
* @since  [产品/模块版本]
*/
public interface ButtonService extends BaseService {

	Integer getMax();

	List<Button> listByParam(Sysmenu menu);

}