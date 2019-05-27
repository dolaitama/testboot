package com.sugon.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import com.sugon.controller.base.BaseController;
import com.sugon.domain.DyView;
import com.sugon.service.DyViewService;
/**
* DyView 控制器
*
* @author  administrator
* @version  [版本号, ${date}]
* @see  [相关类/方法]
* @since  []
*/
@Controller
@RequestMapping("/dyView")
public class DyViewController extends BaseController<DyView> {

	private final Logger logger = LoggerFactory.getLogger(DyViewController.class);
	private DyViewService dyViewService;

	@Autowired
	public DyViewController(DyViewService dyViewService){
		this.dyViewService=dyViewService;
		this.baseService=dyViewService;
		this.model="dyView";
	}
}
