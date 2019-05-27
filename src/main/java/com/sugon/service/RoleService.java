package com.sugon.service;

import java.util.List;

import com.sugon.core.model.SessionInfo;
import com.sugon.domain.Role;
import com.sugon.service.base.BaseService;

public interface RoleService extends BaseService {

	List<Role> getRoleTreeByUid(Long id);

	String getRoleTreeStr(List<Role> roleList, List<Role> assginRoleList);

	void saveRole(Role role, String functionid, SessionInfo sessionInfo);

}
