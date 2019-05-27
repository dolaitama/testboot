package com.sugon.service;

import java.util.List;

import com.sugon.domain.Sysmenu;
import com.sugon.service.base.BaseService;

public interface SysmenuService extends BaseService {

	List<Sysmenu> listParentMenu();

	void saveMenu(Sysmenu menu, Integer type, String functionid) throws Exception;

	void deleteById(Long id) throws Exception;

}
