package com.sugon.core.util;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.sugon.core.model.SessionInfo;
import com.sugon.core.model.TreeNode;
import com.sugon.core.security.SecurityConstants;
import com.sugon.domain.Role;
import com.sugon.domain.Staff;
import com.sugon.domain.Sysright;
import com.sugon.service.RoleService;
import com.sugon.service.StaffService;
import com.sugon.service.SysrightService;

/**
 * 系统使用的特殊工具类 简化代码编写.
 */
public class SecurityUtil {
	
	@Autowired
	private StaffService staffService;
	
	@Autowired
	private SysrightService sysrightService;
	
	@Autowired
	private RoleService roleService;
	
	private static final Logger logger = LoggerFactory
			.getLogger(SecurityUtil.class);

	/**
	 * User转SessionInfo.
	 * 
	 * @param user
	 * @return
	 */
	public static SessionInfo userToSessionInfo(Staff user,
			 List<Sysright> sysrightList, List<TreeNode> systreeNodeList,
			 List<Role> roleList, String macAddress,String token) {
		SessionInfo sessionInfo = new SessionInfo();
		sessionInfo.setStaffId(user.getId());
		sessionInfo.setLoginName(user.getLoginname());
		sessionInfo.setIp("ip");
		sessionInfo.setUserName(user.getName());
		if (StringUtils.isNotEmpty(macAddress)) {
			sessionInfo.setMac(macAddress);
		}
		sessionInfo.setSystreeNodeList(systreeNodeList);
		sessionInfo.setSysrightList(sysrightList);
		sessionInfo.setRoleList(roleList);
		sessionInfo.setToken(token);
		return sessionInfo;
	}

	/**
	 * 将用户放入session中.
	 * 
	 * @param user
	 */
	public static void putUserToSession(Session session, Staff user, 
			List<Sysright> sysrightList, List<TreeNode> systreeNodeList,
			List<Role> roleList, String macAddress,String token) {
		Serializable sessionId = session.getId();
		logger.debug("putUserToSession:{}", sessionId);
		SessionInfo sessionInfo = userToSessionInfo(user,
				sysrightList, systreeNodeList, roleList, macAddress,token);
		sessionInfo.setId(sessionId);
		session.setAttribute(
				SecurityConstants.SESSION_SESSIONINFO, sessionInfo);
		SecurityConstants.sessionInfoMap.put(sessionId, sessionInfo);
	}

	/**
	 * 获取当前用户session信息.
	 */
	public static SessionInfo getCurrentSessionInfo(HttpServletRequest req) {
		return (SessionInfo)SecurityUtils.getSubject().getSession().getAttribute(SecurityConstants.SESSION_SESSIONINFO);
	}

	public static void removeUserFromSession(Serializable sessionId) {
		if (sessionId != null) {
			Set<Serializable> keySet = SecurityConstants.sessionInfoMap.keySet();
			for (Serializable key : keySet) {
				if (key.equals(sessionId)) {
					logger.debug("removeUserFromSession:{}", sessionId);
					SecurityConstants.sessionInfoMap.remove(key);
				}
			}
		}
	}
	
	public static boolean checkAdmin(SessionInfo sessionInfo){
		List<Role> roles = sessionInfo.getRoleList();
		for(Role role : roles){
			if(role.getId().intValue()==1){
				return true;
			}
		}
		return false;
	}

}
