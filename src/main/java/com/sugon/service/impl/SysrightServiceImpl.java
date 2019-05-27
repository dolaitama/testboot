package com.sugon.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.sugon.core.model.SysmenuBean;
import com.sugon.core.model.SysrightBean;
import com.sugon.core.model.TreeNode;
import com.sugon.domain.RoleRight;
import com.sugon.domain.Sysmenu;
import com.sugon.domain.Sysright;
import com.sugon.service.SysrightService;
import com.sugon.service.impl.base.BaseServiceImpl;

@Service
public class SysrightServiceImpl extends BaseServiceImpl implements SysrightService {
	
	public SysrightServiceImpl() {
		super();
		this.mapper="SysrightMapper";
	}
	
	@Override
	public List<TreeNode> getSysmenuTreeByUserId(Long staffId) throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("staffid", staffId);
		List<Sysmenu> sysmenuList = (List<Sysmenu>) this.baseDao.queryList("SysmenuMapper.findMenuByStaffId", param);//sysmenuDao.findMenuByStaffId(staffId);
		List<SysmenuBean> firstLevelList = findFirstLevelSysmenu(sysmenuList);
		for (SysmenuBean sysmenu : firstLevelList) {
			sysmenuList.remove(sysmenu.getSysmenu());
		}
		for (Sysmenu sysmenu : sysmenuList) {
			for (SysmenuBean bean : firstLevelList) {
				if (bean.getSysmenu().getId().equals(sysmenu.getParentid())) {
					bean.getSubSysmenuList().add(sysmenu);
				}
			}
		}
		return getNavMenuTreeByList(firstLevelList);
	}

	public List<SysmenuBean> findFirstLevelSysmenu(List<Sysmenu> sysmenuList) {
		List<SysmenuBean> result = Lists.newArrayList();
		for (Sysmenu sysmenu : sysmenuList) {
			if (sysmenu.getParentid() == null) {
				SysmenuBean bean = new SysmenuBean(sysmenu);
				result.add(bean);
			}
		}
		return result;
	}

	public List<TreeNode> getNavMenuTreeByList(List<SysmenuBean> beanList)
			throws Exception {
		List<TreeNode> nodes = Lists.newArrayList();
		for (SysmenuBean bean : beanList) {
			if (bean != null) {
				TreeNode node = this.sysmenuToTreeNode(bean.getSysmenu());
				List<TreeNode> childrenTreeNodes = Lists.newArrayList();
				if (bean.getSubSysmenuList() != null) {
					for (Sysmenu subResource : bean.getSubSysmenuList()) {
						TreeNode cnode = sysmenuToTreeNode(subResource);
						if (cnode != null) {
							childrenTreeNodes.add(cnode);
						}
					}
				}
				node.setChildren(childrenTreeNodes);
				if (node != null) {
					nodes.add(node);
				}

			}
		}
		return nodes;
	}

	private TreeNode sysmenuToTreeNode(Sysmenu bean) throws Exception {
		TreeNode treeNode = new TreeNode(bean.getId().toString(),
				bean.getMenutext(), bean.getIconcls());
		// 自定义属性 url
		Map<String, Object> attributes = Maps.newHashMap();
		attributes.put("url", bean.getUrl());
		attributes.put("markUrl", bean.getMarkurl());
		attributes.put("code", bean.getId());
		treeNode.setAttributes(attributes);
		return treeNode;
	}

	@Override
	public List<TreeNode> getSysrightTreeByUserId(Long staffId) throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("staffid", staffId);
		List<Sysright> sysrightList = (List<Sysright>) this.baseDao.queryList(this.mapper+".findSysrightListByStaffId", param);
		return getSysrightTreeByList(sysrightList);
	}

	public List<TreeNode> getSysrightTreeByList(List<Sysright> sysrightList)
			throws Exception {
		List<TreeNode> result = new ArrayList<TreeNode>();
		List<SysrightBean> firstLevelList = findFirstLevelSysright(sysrightList);
		for (SysrightBean sysmenu : firstLevelList) {
			sysrightList.remove(sysmenu.getSysright());
		}
		for (SysrightBean bean : firstLevelList) {
			TreeNode node = this.sysrightToTreeNode(bean.getSysright());
			List<TreeNode> children = new ArrayList<TreeNode>();
			for (Sysright sysright : sysrightList) {
				if (bean.getSysright().getRightcode()
						.equals(sysright.getParentrightcode())) {
					TreeNode child = sysrightToTreeNode(sysright);
					List<Sysright> childList = getChildSysright(sysright,
							sysrightList);
					if (childList.size() > 0) {
						List<TreeNode> ccList = new ArrayList<TreeNode>();
						for (Sysright cc : childList) {
							ccList.add(this.sysrightToTreeNode(cc));
						}
						child.setChildren(ccList);
					}
					children.add(child);
				}
			}
			node.setChildren(children);
			result.add(node);
		}
		return result;
	}
	
	public List<SysrightBean> findFirstLevelSysright(List<Sysright> sysrightList){
		List<SysrightBean> result=Lists.newArrayList();
		for(Sysright sysright:sysrightList){			
			if(StringUtils.isBlank(sysright.getParentrightcode())){
				SysrightBean bean=new SysrightBean(sysright);	
				result.add(bean);
			}
		}
		return result;
	}
	
	private List<Sysright> getChildSysright(Sysright sysright,List<Sysright> sysrightList){
		List<Sysright> result=new ArrayList<Sysright>();
		for(Sysright right:sysrightList){
			if(StringUtils.isNotBlank(right.getParentrightcode())
					&&right.getParentrightcode().equals(sysright.getRightcode())){
				result.add(right);
			}
		}
		return result;
	}

	private TreeNode sysrightToTreeNode(Sysright bean) throws Exception {
		TreeNode treeNode = new TreeNode(bean.getRightcode(),
				bean.getRightname());
		// 自定义属性 url
		Map<String, Object> attributes = Maps.newHashMap();
		attributes.put("code", bean.getRightcode());
		treeNode.setAttributes(attributes);
		return treeNode;
	}

	/* 
	 * pDisabled父节点disabled
	 */
	@Override
	public String getFunctionTreeStr(List<TreeNode> sessionTreeNode,
			List<TreeNode> assginTreeNode, boolean pDisabled) {
		StringBuilder sb=new StringBuilder();
		try {
			for(TreeNode node:sessionTreeNode){
				sb.append("<li id=\""+node.getId()+"\" ");
				sb.append("data-jstree='{");
				if(pDisabled){
					sb.append("\"disabled\": true");
				}
				if(isNodeChecked(node.getId(), null, assginTreeNode)){
					if(pDisabled){
						sb.append(",");
					}
					sb.append("\"selected\":true");
				}
				sb.append("}'");
				sb.append(">");				
				sb.append("<a href=\"#\">"+node.getText()+"</a>");
				
				if(node.getChildren()!=null){
					sb.append("<ul>");
					for(TreeNode cn:node.getChildren()){
						sb.append("<li id=\""+cn.getId()+"\" ");
						if(isNodeChecked(cn.getId(), node.getId(), assginTreeNode)){
							sb.append("data-jstree='{\"selected\":true}'");
						}
						sb.append(">");				
						sb.append("<a href=\"#\">"+cn.getText()+"</a>");
						//第三级
						if(cn.getChildren()!=null){
							sb.append("<ul>");
							for(TreeNode ccn:cn.getChildren()){
								sb.append("<li id=\""+ccn.getId()+"\" ");
								if(isNodeChecked(ccn.getId(), cn.getId(), assginTreeNode)){
									sb.append("data-jstree='{\"selected\":true}'");
								}
								sb.append(">");				
								sb.append("<a href=\"#\">"+ccn.getText()+"</a>");								
								
								sb.append("</li>");
							}
							sb.append("</ul>");
						}
						
						sb.append("</li>");
					}
					sb.append("</ul>");
				}
				sb.append("</li>");
			}
			return sb.toString();
		} catch (Exception e) {
			throw e;
		}
	}
	
	private boolean isNodeChecked(String id,String parentId,List<TreeNode> assginTreeNode){
		if(assginTreeNode==null)return false;
		for(TreeNode node:assginTreeNode){
			if(node.getId().equals(id)){
				return true;
			}
		}
		return false;
	}

	@Override
	public List<Sysright> findSysrightListByStaffId(Long staffId)
			throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("staffid", staffId);
		return (List<Sysright>) this.baseDao.queryList(this.mapper+".findSysrightListByStaffId", param);
	}

	@Override
	public List<TreeNode> getSysrightTreeNodeList(Long staffId) throws Exception {
		List<TreeNode> result=new ArrayList<TreeNode>();
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("staffid", staffId);
		List<Sysright> sysrightList = (List<Sysright>) this.baseDao.queryList(this.mapper+".findSysrightListByStaffId", param);
		for(Sysright sysright:sysrightList){
			result.add(sysrightToTreeNode(sysright));
		}
		return result;
	}

	@Override
	public List<TreeNode> getSysrightTreeByUserRight(List<Sysright> sysrightList) throws Exception {
		List<Sysright> list = (List<Sysright>) this.baseDao.queryList(this.mapper+".findSysrightListByRights", sysrightList);
		return getSysrightTreeByList(list);
	}

	@Override
	public List<TreeNode> getSysrightTreeNodeListByRoleId(Long roleId) throws Exception {
		List<TreeNode> result=new ArrayList<TreeNode>();
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("id", roleId);
		List<Sysright> sysrightList=(List<Sysright>) this.baseDao.queryList(this.mapper+".findSysrightListByRoleId", param);
		for(Sysright sysright:sysrightList){
			result.add(sysrightToTreeNode(sysright));
		}
		return result;
	}

	@Override
	public void saveRight(Sysright entity) throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("rightcode", entity.getRightcode());
		Sysright right = (Sysright) this.baseDao.getByParam(this.mapper+".getByParam", param);
		if(right != null){
			this.baseDao.update(this.mapper+".update", entity);
		}else{
			this.baseDao.insert(this.mapper+".insert", entity);
			RoleRight rr = new RoleRight();
			rr.setRightcode(entity.getRightcode());
			rr.setRoleid(1L);
			this.baseDao.insert("RoleRightMapper.insert",rr);
		}
	}

	@Override
	public List<Sysright> listParentRight() {
		return (List<Sysright>) this.baseDao.queryList(this.mapper+".listParentRight", null);
	}

	@Override
	public List<Sysright> listByUserName(String username) {
		return (List<Sysright>) this.baseDao.queryList(this.mapper+".listByUserName", username);
	}

}
