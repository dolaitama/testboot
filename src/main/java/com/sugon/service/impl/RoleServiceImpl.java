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
import com.sugon.domain.Sysright;
import com.sugon.service.RoleService;
import com.sugon.service.impl.base.BaseServiceImpl;
import com.sugon.util.DateUtil;
@Service
public class RoleServiceImpl extends BaseServiceImpl implements RoleService {
	
	public RoleServiceImpl() {
		super();
		this.mapper="RoleMapper";
	}
	
	@Override
	public List<Role> getRoleTreeByUid(Long id) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("id", id);
		List<Role> roleList = (List<Role>) this.baseDao.queryList(this.mapper+".findByUid", param);
		return roleList;
	}

	@Override
	public String getRoleTreeStr(List<Role> roleList, List<Role> assginRoleList) {
		StringBuilder sb=new StringBuilder();
		try {
			for(Role r:roleList){
				sb.append("<li id=\""+r.getId()+"\" ");
				if(isNodeChecked(r.getId().toString(), null, assginRoleList)){
					sb.append("data-jstree='{\"selected\":true}'");
				}
				sb.append(">");				
				sb.append("<a href=\"#\">"+r.getName()+"</a>");
				sb.append("</li>");
			}
			return sb.toString();
		} catch (Exception e) {
			throw e;
		}
	}
	
	private boolean isNodeChecked(String id,String parentId,List<Role> assginRoleList){
		if(assginRoleList==null)return false;
		for(Role role:assginRoleList){
			if(role.getId().toString().equals(id)){
				return true;
			}
		}
		return false;
	}

	@Override
	public void saveRole(Role entity, String functionid, SessionInfo sessionInfo) {
		String now = DateUtil.nowStr();
		if(entity.getId() != null){
			entity.setUpdatetime(now);
			this.baseDao.update(this.mapper+".update", entity);
			
			List<Sysright> sysrightList = (List<Sysright>) this.baseDao.queryList("SysrightMapper.findSysrightListByRoleId", entity);
			String funcs[] = functionid.split(",");
			List<String> addFuns = new ArrayList<String>();
			List<String> delFuns = new ArrayList<String>();
			if (StringUtils.isNotBlank(functionid)) {
				addFuns = getRightAddList(funcs, sysrightList);
				delFuns = getRightDelList(funcs, sysrightList);
			}else{
				for (Sysright node : sysrightList) {
					delFuns.add(node.getRightcode());
				}
			}
			for (String fun : addFuns) {
				RoleRight rr = new RoleRight();
				rr.setRightcode(fun);
				rr.setRoleid(entity.getId());
				this.baseDao.insert("RoleRightMapper.insert", rr);
			}
			for (String fun : delFuns) {
				Map<String, Object> param = new HashMap<String, Object>();
				param.put("roleid", entity.getId());
				param.put("rightcode", fun);
				List<RoleRight> rrList = (List<RoleRight>) this.baseDao.queryList("RoleRightMapper.queryList", param);
				for(RoleRight rright : rrList){
					this.baseDao.delete("RoleRightMapper.delete", rright);
				}
			}
		}else{
			entity.setStatus("1");
			entity.setCreatetime(now);
			entity.setUpdatetime(now);
			this.baseDao.insert(this.mapper+".insert", entity);
			List<String> addFuns = new ArrayList<String>();
			if ((entity.getId() != null)
					&& StringUtils.isNotBlank(functionid)) {
				String funcs[] = functionid.split(",");
				addFuns = getRightAddList(funcs, null);
				for (String fun : addFuns) {
					RoleRight sr = new RoleRight();
					sr.setRoleid(entity.getId());
					sr.setRightcode(fun);
					this.baseDao.insert("RoleRightMapper.insert", sr);
				}
			}
		}
	}
	
	public List<String> getRightAddList(String funcs[],
			List<Sysright> sysrightList) {
		List<String> result = new ArrayList<String>();
		for (String fun : funcs) {
			boolean existFlag = false;
			if(sysrightList != null){
				for (Sysright node : sysrightList) {
					if (node.getRightcode().equals(fun)) {
						existFlag = true;
						break;
					}
				}
			}
			if (!existFlag) {
				result.add(fun);
			}
		}
		return result;
	}
	
	public List<String> getRightDelList(String funcs[],
			List<Sysright> sysrightList) {
		List<String> result = new ArrayList<String>();
		for (Sysright node : sysrightList) {
			boolean existFlag = false;
			for (String fun : funcs) {
				if (fun.equals(node.getRightcode())) {
					existFlag = true;
					break;
				}
			}
			if (!existFlag) {
				result.add(node.getRightcode());
			}
		}
		return result;
	}

}
