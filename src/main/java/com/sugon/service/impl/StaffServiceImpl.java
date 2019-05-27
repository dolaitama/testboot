package com.sugon.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.sugon.core.model.SessionInfo;
import com.sugon.domain.Role;
import com.sugon.domain.RoleRight;
import com.sugon.domain.Staff;
import com.sugon.domain.StaffRole;
import com.sugon.domain.Sysright;
import com.sugon.service.StaffService;
import com.sugon.service.impl.base.BaseServiceImpl;
import com.sugon.util.DateUtil;

@Service
public class StaffServiceImpl extends BaseServiceImpl implements StaffService {
	
	public StaffServiceImpl() {
		super();
		this.mapper="StaffMapper";
	}
	
	@Override
	public List<Sysright> findRoleRightByStaffId(Long id) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("id", id);
		List<RoleRight> roleRightList = (List<RoleRight>) this.baseDao.queryList("RoleRightMapper.findRoleRightListByStaffId", param);
		if (roleRightList == null) {
			return null;
		}
		List<Sysright> sysrightList = new ArrayList<Sysright>();
		for (RoleRight right : roleRightList) {
			sysrightList.add(right.getSysright());
		}
		return sysrightList;
	}

	@Override
	public void saveStaff(Staff entity, String functionid, SessionInfo sessionInfo) throws Exception {
		if(entity.getId() != null){
			this.baseDao.update(this.mapper+".update", entity);
			List<Role> roleList = (List<Role>) this.baseDao.queryList("RoleMapper.findByUid", entity);
			
			String funcs[] = functionid.split(",");
			List<String> addFuns = new ArrayList<String>();
			List<String> delFuns = new ArrayList<String>();
			if (StringUtils.isNotBlank(functionid)) {
				addFuns = getRoleAddList(funcs, roleList);
				delFuns = getRoleDelList(funcs, roleList);
			} else {
				for (Role node : roleList) {
					delFuns.add(node.getId().toString());
				}
			}
			for (String fun : addFuns) {
				StaffRole sr = new StaffRole();
				sr.setStaffid(entity.getId());
				sr.setRoleid(Long.parseLong(fun));
				this.baseDao.insert("StaffRoleMapper.insert", sr);
			}
			for (String fun : delFuns) {
				Map<String, Object> param = new HashMap<>();
				param.put("staffid", entity.getId());
				param.put("roleid", fun);
				List<StaffRole> srList = (List<StaffRole>) this.baseDao.queryList("StaffRoleMapper.queryList", param);
				for(StaffRole srole : srList){
					this.baseDao.delete("StaffRoleMapper.delete", srole);
				}
			}
		}else{
			entity.setCreatetime(DateUtil.nowStr());
			entity.setUpdatetime(DateUtil.nowStr());
			entity.setLoginpwd("123456");
			this.baseDao.insert(this.mapper+".insert", entity);
			if ((entity.getId() != null)
					&& StringUtils.isNotBlank(functionid)) {
				String funcs[] = functionid.split(",");
				for (String fun : funcs) {
					StaffRole sr = new StaffRole();
					sr.setStaffid(entity.getId());
					sr.setRoleid(Long.parseLong(fun));
					this.baseDao.insert("StaffRoleMapper.insert", sr);
				}
			}
		}
	}
	
	private List<String> getRoleDelList(String[] funcs, List<Role> roleList) {
		List<String> result = new ArrayList<String>();
		for (Role node : roleList) {
			boolean existFlag = false;
			for (String fun : funcs) {
				if (fun.equals(node.getId().toString())) {
					existFlag = true;
					break;
				}
			}
			if (!existFlag) {
				result.add(node.getId().toString());
			}
		}
		return result;
	}

	private List<String> getRoleAddList(String funcs[], List<Role> roleList){
		List<String> result = new ArrayList<String>();
		for (String fun : funcs) {
			boolean existFlag = false;
			for (Role node : roleList) {
				if (node.getId().toString().equals(fun)) {
					existFlag = true;
					break;
				}
			}
			if (!existFlag) {
				result.add(fun);
			}
		}
		return result;
	}

}
