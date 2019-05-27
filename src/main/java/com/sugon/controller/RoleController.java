package com.sugon.controller;

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
import com.sugon.core.model.SessionInfo;
import com.sugon.core.model.TreeNode;
import com.sugon.core.util.SecurityUtil;
import com.sugon.domain.Role;
import com.sugon.domain.Sysright;
import com.sugon.response.Result;
import com.sugon.service.RoleService;
import com.sugon.service.SysrightService;

@RequestMapping("/role")
@Controller
public class RoleController {
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private SysrightService sysrightService;
	
	@RequestMapping("view")
	public String view(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="id", required=false) Long id){
		try{
			SessionInfo sessionInfo = SecurityUtil.getCurrentSessionInfo(req);
			List<TreeNode> systreeNodeList = this.sysrightService
					.getSysrightTreeByUserRight(sessionInfo.getSysrightList());
			Role role = null;
			if(id != null){
				Map<String, Object> param = new HashMap<String, Object>();
				param.put("id", id);
				role = (Role) this.roleService.getByParam(param);
			}
			String funcTreeStr = "";
			String editType = "edit";
			if(role == null){
				role = new Role();
				funcTreeStr = this.sysrightService.getFunctionTreeStr(
						systreeNodeList, null, false);
				editType = "new";
			}else{
				List<TreeNode> assginTreeNode = this.sysrightService
						.getSysrightTreeNodeListByRoleId(id);
				funcTreeStr = this.sysrightService.getFunctionTreeStr(
						systreeNodeList, assginTreeNode, false);
			}
			StringBuilder sb = new StringBuilder();
			List<Sysright> sysrightList = this.sysrightService
					.findSysrightListByStaffId(sessionInfo.getStaffId());
			for (Sysright node : sysrightList) {
				sb.append(node.getRightcode()).append("','");
			}
			if (sb.length() > 0) {
				sb.delete(sb.length() - 2, sb.length());
			}
			String firstLevelFun = "'" + sb.toString();
			req.setAttribute("funcTreeStr", funcTreeStr);
			req.setAttribute("editType", editType);
			req.setAttribute("firstLevelFun", firstLevelFun);
			req.setAttribute("role", role);
			return "sys/role-view";
		}catch (Exception e){
			e.printStackTrace();
			throw new TiancheException();
		}
	}
	
	/**
	 * 保存角色
	 * @param req
	 * @param res
	 * @param role
	 * @param functionid
	 * @return
	 */
	@RequestMapping("save")
	@ResponseBody
	public Result save(HttpServletRequest req, HttpServletResponse res,
			Role role, @RequestParam(value="functionid", required= false) String functionid){
		try{
			SessionInfo sessionInfo = SecurityUtil.getCurrentSessionInfo(req);
			this.roleService.saveRole(role, functionid, sessionInfo);
		}catch(Exception e){
			if(e instanceof TiancheException){
				throw new TiancheException(e);
			}
			throw new TiancheException("操作失败");
		}
		return Result.create();
	}
	
	/**
	 * 删除角色
	 * @param req
	 * @param res
	 * @param id
	 * @return
	 */
	@RequestMapping("delete")
	@ResponseBody
	public Result delete(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="id", required=false) Long id){
		try{
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("id", id);
			this.roleService.delete(param);
			return Result.create();
		}catch (Exception e){
			if(e instanceof TiancheException){
				throw new TiancheException(e);
			}
			throw new TiancheException("操作失败");
		}
	}

}
