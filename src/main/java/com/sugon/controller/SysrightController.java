package com.sugon.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sugon.core.exception.TiancheException;
import com.sugon.domain.RightParam;
import com.sugon.domain.Staff;
import com.sugon.domain.Sysmenu;
import com.sugon.domain.Sysright;
import com.sugon.response.Result;
import com.sugon.service.RightParamService;
import com.sugon.service.SessionService;
import com.sugon.service.SysmenuService;
import com.sugon.service.SysrightService;

@Controller
@RequestMapping("sysright")
public class SysrightController {
	
	@Autowired
	private SysrightService sysrightService;
	
	@Autowired
	private SessionService sessionService;
	
	@Autowired
	private RightParamService rightParamService;
	
	@Autowired
	private SysmenuService sysmenuService;
	
	@RequestMapping("view")
	public String view(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="code", required=false) String code){
		Sysright right = null;
		if(StringUtils.isNotBlank(code)){
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("rightcode", code);
			right = (Sysright) this.sysrightService.getByParam(map);
		}
		//菜单
		List<Sysmenu> menus = (List<Sysmenu>) this.sysmenuService.queryList(null);
		String editType = "edit";
		if(right == null){
			right = new Sysright();
			editType = "new";
		}else{
			//权限参数
			List<RightParam> rps = new ArrayList<RightParam>();
			RightParam param = new RightParam();
			param.setRightcode(code);
			rps = (List<RightParam>) this.rightParamService.queryList(param);
			req.setAttribute("rps", rps);
		}
		req.setAttribute("menus", menus);
		req.setAttribute("editType", editType);
		req.setAttribute("right", right);
		return "sys/right-view";
	}
	
	@RequestMapping("save")
	@ResponseBody
	public Result save(HttpServletRequest req, HttpServletResponse res,
			Sysright right){
		Staff staff = (Staff) req.getAttribute("currentStaff");
		try{
			this.sysrightService.saveRight(right);
			this.sessionService.refreshSession(staff);
		}catch (Exception e){
			e.printStackTrace();
			if(e instanceof TiancheException){
				throw new TiancheException(e);
			}
			throw new TiancheException("操作失败");
		}
		return Result.create();
	}
	
	@RequestMapping("parentList")
	@ResponseBody
	public List<Sysright> parentList(HttpServletRequest req, HttpServletResponse res){
		return this.sysrightService.listParentRight();
	}
	
	@RequestMapping("rightList")
	@ResponseBody
	public List<Sysright> rightList(HttpServletRequest req, HttpServletResponse res){
		return (List<Sysright>) this.sysrightService.queryList(null);
	}

}
