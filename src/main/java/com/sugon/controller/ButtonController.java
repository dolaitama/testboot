package com.sugon.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.beans.factory.annotation.Autowired;

import com.sugon.controller.base.BaseController;
import com.sugon.core.exception.TiancheException;
import com.sugon.domain.Button;
import com.sugon.response.Result;
import com.sugon.service.ButtonService;
/**
* Button 控制器
*
* @author  administrator
* @version  [版本号, ${date}]
* @see  [相关类/方法]
* @since  []
*/
@Controller
@RequestMapping("/button")
public class ButtonController extends BaseController<Button> {

	private final Logger logger = LoggerFactory.getLogger(ButtonController.class);
	private ButtonService buttonService;

	@Autowired
	public ButtonController(ButtonService buttonService){
		this.buttonService=buttonService;
		this.baseService=buttonService;
		this.model="button";
	}
	
	@Override
	public ModelAndView view(HttpServletRequest req, HttpServletRequest res, 
			Button item){
		ModelAndView mv = new ModelAndView();
		if(item.getId() != null){
			item = (Button) this.buttonService.getByParam(item);
		}
		mv.addObject("item", item);
		return mv;
	}
	
	/* 
	 * 新增、编辑
	 * 
	 */
	@Override
	public Result save(HttpServletRequest req, Button item){
		if(item.getId() == null){
			Integer max = this.buttonService.getMax();
			int code = 1;
			if(max != null){
				code = max*2;
			}
			item.setCode(code);
			return super.save(req, item);
		}else{
			return super.update(req, item);
		}
	}
	
}
