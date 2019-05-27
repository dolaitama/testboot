package com.sugon.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sugon.core.exception.TiancheException;
import com.sugon.core.model.SessionInfo;
import com.sugon.core.security.SecurityConstants;
import com.sugon.core.util.SecurityUtil;
import com.sugon.domain.Role;
import com.sugon.domain.Staff;
import com.sugon.domain.Sysright;
import com.sugon.response.Result;
import com.sugon.service.RoleService;
import com.sugon.service.StaffService;
import com.sugon.service.SysrightService;

@Controller
@RequestMapping("staff")
public class StaffController {
	
	@Autowired
	private SysrightService sysrightService;
	
	@Autowired
	private StaffService staffService;
	
	@Autowired
	private RoleService roleService;
	
	@RequestMapping("toLogin")
	public String toLogin(){
		return "login";
	}
	
	@RequestMapping("doLogin")
	@ResponseBody
	public Result doLogin(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="username", required=false) String username,
			@RequestParam(value="password", required=false) String password){
		Subject subject = org.apache.shiro.SecurityUtils.getSubject();
		UsernamePasswordToken token = new UsernamePasswordToken(username, password);
		subject.login(token);
		/*Map<String, Object> param = new HashMap<String, Object>();
		param.put("loginname", username);
		param.put("loginpwd", password);
		Staff staff = (Staff) this.staffService.getByParam(param);
		if(staff != null){
			req.getSession().setAttribute("currentStaff", staff);
			return Result.create();
		}else{
			throw new TiancheException("用户名或密码不正确");
		}*/
		return Result.create();
	}
	
	@RequestMapping("doLoginout")
	public String doLoginout(HttpServletRequest req, HttpServletResponse res){
		HttpSession session = req.getSession(false);
		if(session != null){
			session.removeAttribute("currentStaff");
			session.removeAttribute(SecurityConstants.SESSION_SESSIONINFO);
		}
		return "login";
	}
	
	@RequestMapping("view")
	public String view(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="id", required=false) Long id){
		try{
			SessionInfo sessionInfo = SecurityUtil.getCurrentSessionInfo(req);
			List<Role> roleList = sessionInfo.getRoleList();
			List<Sysright> rights = sessionInfo.getSysrightList();
			boolean admin = false;
			for(Sysright right : rights){
				if("staff".equals(right.getRightcode())){
					admin = true;
					break;
				}
			}
			if(admin){
				roleList = (List<Role>) this.roleService.queryList(null);
			}
			Staff staff = null;
			if(id != null){
				Map<String, Object> param = new HashMap<String, Object>();
				param.put("id", id);
				staff = (Staff) this.staffService.getByParam(param);
			}
			String roleTreeStr = "";
			String editType = "edit";
			if(staff == null){
				staff = new Staff();
				staff.setStatus("1");
				roleTreeStr = this.roleService.getRoleTreeStr(roleList, null);
				editType = "new";
			}else{
				List<Role> assginRoleList = this.roleService.getRoleTreeByUid(id);
				roleTreeStr = this.roleService.getRoleTreeStr(roleList, assginRoleList);
			}
			StringBuilder sb = new StringBuilder();
			for (Role node : roleList) {
				sb.append(node.getId()).append("','");
			}
			if (sb.length() > 0) {
				sb.delete(sb.length() - 2, sb.length());
			}
			String firstLevelRole = "'" + sb.toString();
			req.setAttribute("roleTreeStr", roleTreeStr);
			req.setAttribute("editType", editType);
			req.setAttribute("firstLevelRole", firstLevelRole);
			req.setAttribute("staff", staff);
			return "sys/staff-view";
		}catch(Exception e){
			e.printStackTrace();
			throw new TiancheException();
		}
	}
	
	@RequestMapping("save")
	@ResponseBody
	public Result save(HttpServletRequest req, HttpServletResponse res,
			Staff staff, @RequestParam(value="functionid", required= false) String functionid){
		try{
			SessionInfo sessionInfo = SecurityUtil.getCurrentSessionInfo(req);
			this.staffService.saveStaff(staff, functionid, sessionInfo);
		}catch(Exception e){
			e.printStackTrace();
			if(e instanceof TiancheException){
				throw new TiancheException(e);
			}
			throw new TiancheException("操作失败");
		}
		return Result.create();
	}
	
}
