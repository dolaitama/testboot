package com.sugon.start.config;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AccountException;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sugon.domain.Staff;
import com.sugon.domain.Sysright;
import com.sugon.service.SessionService;
import com.sugon.service.StaffService;
import com.sugon.service.SysrightService;
@Component
public class CustRealm extends AuthorizingRealm {
	
	@Autowired
	private StaffService staffService;
	
	@Autowired
	private SysrightService sysrightService;
	
	@Autowired
	private SessionService sessionService;

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
		String username = (String) SecurityUtils.getSubject().getPrincipal();
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		List<Sysright> srs = this.sysrightService.listByUserName(username);
		Set<String> set = new HashSet<String>();
		for(Sysright sr : srs){
			set.add(sr.getRightcode());
		}
		info.setRoles(set);
		return info;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken authenticationToken) throws AuthenticationException {
		UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("loginname", token.getUsername());
		Staff staff = (Staff) this.staffService.getByParam(param);
		if(staff==null){
			throw new AccountException("用户名不正确");
		}else if(staff.getLoginpwd() != null && 
				!staff.getLoginpwd().equals(new String((char []) token.getCredentials()))){
			throw new AccountException("密码不正确");
		}
		SecurityUtils.getSubject().getSession().setAttribute("currentStaff", staff);
		try {
			this.sessionService.refreshSession(staff);
		} catch (Exception e) {
			throw new AccountException("用户无权登录");
		}
		return new SimpleAuthenticationInfo(token.getPrincipal(), staff.getLoginpwd(), getName());
	}

}
