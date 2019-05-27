package com.sugon.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import com.sugon.controller.base.BaseController;
import com.sugon.domain.RightParam;
import com.sugon.service.RightParamService;
/**
* RightParam 控制器
*
* @author  administrator
* @version  [版本号, ${date}]
* @see  [相关类/方法]
* @since  []
*/
@Controller
@RequestMapping("/rightParam")
public class RightParamController extends BaseController<RightParam> {

	private final Logger logger = LoggerFactory.getLogger(RightParamController.class);
	private RightParamService rightParamService;

	@Autowired
	public RightParamController(RightParamService rightParamService){
		this.rightParamService=rightParamService;
		this.baseService=rightParamService;
		this.model="rightParam";
	}
}
