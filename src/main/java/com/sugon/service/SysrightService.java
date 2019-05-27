package com.sugon.service;

import java.util.List;

import com.sugon.core.model.TreeNode;
import com.sugon.domain.Sysright;
import com.sugon.service.base.BaseService;

public interface SysrightService extends BaseService{

	List<TreeNode> getSysmenuTreeByUserId(Long id) throws Exception;

	List<TreeNode> getSysrightTreeByUserId(Long staffId) throws Exception;

	String getFunctionTreeStr(List<TreeNode> systreeNodeList, List<TreeNode> assginTreeNode, boolean pDisabled);

	List<Sysright> findSysrightListByStaffId(Long staffId) throws Exception;

	List<TreeNode> getSysrightTreeNodeList(Long id) throws Exception;

	List<TreeNode> getSysrightTreeByUserRight(List<Sysright> sysrightList) throws Exception;

	List<TreeNode> getSysrightTreeNodeListByRoleId(Long id) throws Exception;

	void saveRight(Sysright right) throws Exception;

	List<Sysright> listParentRight();

	List<Sysright> listByUserName(String username);

}
