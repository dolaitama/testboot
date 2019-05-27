package com.sugon.service.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sugon.core.model.TreeNode;
import com.sugon.core.util.SecurityUtil;
import com.sugon.domain.Role;
import com.sugon.domain.Staff;
import com.sugon.domain.Sysright;
import com.sugon.service.RoleService;
import com.sugon.service.SessionService;
import com.sugon.service.StaffService;
import com.sugon.service.SysrightService;
@Service
public class SessionServiceImpl implements SessionService {
	
	@Autowired
	private StaffService staffService;
	
	@Autowired
	private SysrightService sysrightService;
	
	@Autowired
	private RoleService roleService;

	@Override
	public void refreshSession(Staff staff) throws Exception{
    	List<TreeNode> systreeNodeList = sysrightService
				.getSysmenuTreeByUserId(staff.getId());
		List<Role> roleList = roleService.getRoleTreeByUid(staff.getId());
		List<Sysright> sysrightList = staffService
				.findRoleRightByStaffId(staff.getId());
		SecurityUtil.putUserToSession(SecurityUtils.getSubject().getSession(), staff, sysrightList,
				systreeNodeList, roleList, "", "");
    }

}
