package com.sugon.controller.base;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.sugon.response.Result;
import com.sugon.service.base.BaseService;

@Controller
public class BaseController<T> {
	private Logger log = LoggerFactory.getLogger(BaseController.class);
	
	public String model = "";
	public BaseService baseService;
	
	@RequestMapping("view")
	public ModelAndView view(HttpServletRequest req, HttpServletRequest res, 
			T item){
		ModelAndView mv = new ModelAndView();
		mv.setViewName(this.model+"/view");
		return mv;
	}
	
	/**
	 * @description 获取单个数据
	 * @param req
	 * @param res
	 * @param id
	 * @return
	 */
	@RequestMapping("getByParam")
	@ResponseBody
	public Result getByParam(HttpServletRequest req, HttpServletResponse res, 
			T item){
		Object obj = this.baseService.getByParam(item);
		return Result.create().add("item", obj);
	}
	
	/**
	 * @description 新增
	 * @param req
	 * @param item
	 * @return
	 */
	@RequestMapping("save")
	@ResponseBody
	public Result save(HttpServletRequest req, T item){
		try {
			this.baseService.insert(item);
		} catch (Exception e) {
			log.error("新增失败", e);
			return Result.create().fail("新增失败");
		}
		return Result.create();
	}
	
	/**
	 * @description 更新
	 * @param req
	 * @param item
	 * @return
	 */
	@RequestMapping("update")
	@ResponseBody
	public Result update(HttpServletRequest req, T item){
		try{
			this.baseService.update(item);
		}catch (Exception e){
			log.error("修改失败", e);
			return Result.create().fail("修改失败");
		}
		return Result.create();
	}
	
	/**
	 * @description 删除
	 * @param req
	 * @param item
	 * @return
	 */
	@RequestMapping("delete")
	@ResponseBody
	public Result delete(HttpServletRequest req, T item){
		try{
			this.baseService.delete(item);
		}catch (Exception e){
			log.error("删除失败", e);
			return Result.create().fail("删除失败");
		}
		return Result.create();
	}
	
	/**
	 * @description 获取多个数据
	 * @param req
	 * @param res
	 * @param id
	 * @return
	 */
	@RequestMapping("queryList")
	@ResponseBody
	public Result queryList(HttpServletRequest req, T item){
		return Result.create().add("list", this.baseService.queryList(item));
	}
	
	/**
	 * @description 分页查询
	 * @param req
	 * @param res
	 * @param id
	 * @return
	 */
	@RequestMapping("queryDataGrid")
	@ResponseBody
	public Result queryDataGrid(HttpServletRequest req, T item){
		return Result.create().add("dataGrid", this.baseService.queryDataGrid(item));
	}

}
