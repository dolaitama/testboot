package com.sugon.service;

import java.util.List;

import com.sugon.core.model.SessionInfo;
import com.sugon.domain.Staff;
import com.sugon.domain.Sysright;
import com.sugon.service.base.BaseService;

public interface StaffService extends BaseService{

	void saveStaff(Staff staff, String functionid, SessionInfo sessionInfo) throws Exception;

	List<Sysright> findRoleRightByStaffId(Long id);

}
