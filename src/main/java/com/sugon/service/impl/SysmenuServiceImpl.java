package com.sugon.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sugon.constants.Const;
import com.sugon.core.exception.TiancheException;
import com.sugon.domain.DyView;
import com.sugon.domain.Sysmenu;
import com.sugon.service.SysmenuService;
import com.sugon.service.impl.base.BaseServiceImpl;

@Service
public class SysmenuServiceImpl extends BaseServiceImpl implements SysmenuService {
	
	@Autowired
	private Const con;
	
	public SysmenuServiceImpl() {
		super();
		this.mapper="SysmenuMapper";
	}
	
	@Autowired
	private RoleServiceImpl roleService;

	@Override
	public List<Sysmenu> listParentMenu() {
		return (List<Sysmenu>) this.baseDao.queryList(this.mapper+".listParnetMenu", null);
	}

	@Override
	public void saveMenu(Sysmenu entity, Integer type, String functionid) throws Exception {
		Long code = entity.getDyviewid();
		if(code != null && StringUtils.isBlank(entity.getUrl())){
			entity.setUrl(con.MENU_PREFIX+code);
		}
		if(entity.getId() != null){
			this.baseDao.update(this.mapper+".update",entity);
		}else{
			DyView dv = null;
			if(code != null){
				dv = new DyView();
				dv.setId(code);
				dv = (DyView) this.baseDao.getByParam("DyViewMapper.getByParam", dv);
			}
			if(dv != null){
				throw new TiancheException("动态配置编号已存在，");
			}
			dv = new DyView();
			dv.setId(code);
			dv.setType(type);
			this.baseDao.insert("DyViewMapper.insert",dv);
			entity.setStatus("1");
			this.baseDao.insert(this.mapper+".insert",entity);
		}
	}

	@Override
	public void deleteById(Long id) throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("id", id);
		this.baseDao.delete("DyViewMapper.deleteByMenuId", param);
		this.baseDao.delete(this.mapper+".delete", param);
	}

}
