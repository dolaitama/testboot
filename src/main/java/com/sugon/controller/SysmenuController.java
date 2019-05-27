package com.sugon.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sugon.core.exception.TiancheException;
import com.sugon.domain.Button;
import com.sugon.domain.DyView;
import com.sugon.domain.Staff;
import com.sugon.domain.Sysmenu;
import com.sugon.response.Result;
import com.sugon.service.ButtonService;
import com.sugon.service.DyViewService;
import com.sugon.service.SessionService;
import com.sugon.service.SysmenuService;
import com.sugon.service.SysrightService;

@Controller
@RequestMapping("menu")
public class SysmenuController {
	
	@Autowired
	private SysmenuService sysmenuService;
	
	@Autowired
	private SessionService sessionService;
	
	@Autowired
	private DyViewService dyViewService;
	
	@Autowired
	private ButtonService buttonService;
	
	@Autowired
	private SysrightService sysrightService;
	
	@RequestMapping("view")
	public String view(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="id", required=false) Long id){
		try{
			Sysmenu menu = null;
			List<Button> bts = new ArrayList<Button>();
			DyView dv = new DyView();
			String editType = "edit";
			if(id == null){
				menu = new Sysmenu();
				editType = "new";
			}else{
				Map<String, Object> param = new HashMap<String, Object>();
				param.put("id", id);
				menu = (Sysmenu) this.sysmenuService.getByParam(param);
				if(menu.getDyviewid() != null){
					param.put("id", menu.getDyviewid());
					dv = (DyView) this.dyViewService.getByParam(param);
				}
			}
			req.setAttribute("editType", editType);
			req.setAttribute("menu", menu);
			req.setAttribute("dv", dv);
			req.setAttribute("bts", bts);
			return "sys/menu-view";
		}catch(Exception e){
			e.printStackTrace();
			throw new TiancheException();
		}
	}
	
	@RequestMapping("save")
	@ResponseBody
	public Result save(HttpServletRequest req, HttpServletResponse res,
			Sysmenu menu, Integer type, String functionid){
		Staff staff = (Staff) req.getAttribute("currentStaff");
		try{
			this.sysmenuService.saveMenu(menu, type, functionid);
			this.sessionService.refreshSession(staff);
			return Result.create();
		}catch (Exception e){
			e.printStackTrace();
			if(e instanceof TiancheException){
				throw new TiancheException(e);
			}
			throw new TiancheException("操作失败");
		}
	}
	
	@RequestMapping("delete")
	@ResponseBody
	public Result delete(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="id", required=false) Long id){
		Staff staff = (Staff) req.getAttribute("currentStaff");
		try{
			this.sysmenuService.deleteById(id);
			this.sessionService.refreshSession(staff);
			return Result.create();
		}catch (Exception e){
			e.printStackTrace();
			if(e instanceof TiancheException){
				throw new TiancheException(e);
			}
			throw new TiancheException("操作失败");
		}
	}
	
	@RequestMapping("parentList")
	@ResponseBody
	public List<Sysmenu> parentList(HttpServletRequest req, HttpServletResponse res){
		return this.sysmenuService.listParentMenu();
	}
	
}
