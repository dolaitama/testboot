package com.sugon.core.model;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.jsoup.nodes.Element;

public class QueryBean {

	private Map<String, Element> colPageTableColInfoMap = new HashMap<String, Element>();

	private String sql;
	private GridPageInfo pageInfo;
	private String sort;
	private String order;
	private String queryDef;
	private String expInfo;

	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

	public GridPageInfo getPageInfo() {
		return pageInfo;
	}

	public void setPageInfo(GridPageInfo pageInfo) {
		this.pageInfo = pageInfo;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public Map<String, Element> getColPageTableColInfoMap() {
		return colPageTableColInfoMap;
	}

	public void setColPageTableColInfoMap(
			Map<String, Element> colPageTableColInfoMap) {
		this.colPageTableColInfoMap = colPageTableColInfoMap;
	}

	public String getQueryDef() {
		return queryDef;
	}

	public void setQueryDef(String queryDef) {
		this.queryDef = queryDef;
	}

	public String getExpInfo() {
		return expInfo;
	}

	public void setExpInfo(String expInfo) {
		this.expInfo = expInfo;
	}

	public String getColumnName(boolean b) {
		Iterator<Map.Entry<String, Element>> it = colPageTableColInfoMap.entrySet().iterator();
		StringBuilder str = new StringBuilder();
		while (it.hasNext()){
			Map.Entry<String, Element> entity = it.next();
			if(!String.valueOf(b).equals(entity.getValue().attr("hidden").equals("")) && !("操作").equals(entity.getValue().html())){
				str.append(entity.getValue().html());
				str.append(",");
			}
		}
		if (str.length() > 0)
			str = str.deleteCharAt(str.length() - 1);
		return str.toString();
	}
	
	public String getColumnName() {
		Iterator<Map.Entry<String, Element>> it = colPageTableColInfoMap.entrySet().iterator();
		StringBuilder str = new StringBuilder();
		while (it.hasNext()){
			Map.Entry<String, Element> entity = it.next();
			if(entity.getValue().attr("exp").equals("true") && !("操作").equals(entity.getValue().html())){
				str.append(entity.getValue().html());
				str.append(",");
			}
		}
		if (str.length() > 0)
			str = str.deleteCharAt(str.length() - 1);
		return str.toString();
	}

	public String getColumnWidth(boolean b) {
		Iterator<Map.Entry<String, Element>> it = colPageTableColInfoMap.entrySet().iterator();
		StringBuilder str = new StringBuilder();
		while (it.hasNext()){
			Map.Entry<String, Element> entity = it.next();
			if(!String.valueOf(b).equals(entity.getValue().attr("hidden").equals("")) && !("操作").equals(entity.getValue().html())){
				str.append(entity.getValue().attr("width").replaceAll("px", ""));
				str.append(",");
			}
		}
		if (str.length() > 0)
			str = str.deleteCharAt(str.length() - 1);
		return str.toString();
	}
	
	public String getColumnWidth() {
		Iterator<Map.Entry<String, Element>> it = colPageTableColInfoMap.entrySet().iterator();
		StringBuilder str = new StringBuilder();
		while (it.hasNext()){
			Map.Entry<String, Element> entity = it.next();
			if(entity.getValue().attr("exp").equals("true") && !("操作").equals(entity.getValue().html())){
				str.append(entity.getValue().attr("width").replaceAll("px", ""));
				str.append(",");
			}
		}
		if (str.length() > 0)
			str = str.deleteCharAt(str.length() - 1);
		return str.toString();
	}

	public String getColumnMetaType(boolean b) {
		Iterator<Map.Entry<String, Element>> it = colPageTableColInfoMap.entrySet().iterator();
		StringBuilder str = new StringBuilder();
		while (it.hasNext()){
			Map.Entry<String, Element> entity = it.next();
			if(!String.valueOf(b).equals(entity.getValue().attr("hidden").equals("")) && !("操作").equals(entity.getValue().html())){
				str.append(entity.getValue().attr("fieldType"));
				str.append(",");
			}
		}
		if (str.length() > 0)
			str = str.deleteCharAt(str.length() - 1);
		return str.toString();
	}
	
	public String getColumnMetaType() {
		Iterator<Map.Entry<String, Element>> it = colPageTableColInfoMap.entrySet().iterator();
		StringBuilder str = new StringBuilder();
		while (it.hasNext()){
			Map.Entry<String, Element> entity = it.next();
			if(entity.getValue().attr("exp").equals("true") && !("操作").equals(entity.getValue().html())){
				str.append(entity.getValue().attr("fieldType"));
				str.append(",");
			}
		}
		if (str.length() > 0)
			str = str.deleteCharAt(str.length() - 1);
		return str.toString();
	}

	public String getColumnField(boolean b) {
		Iterator<Map.Entry<String, Element>> it = colPageTableColInfoMap.entrySet().iterator();
		StringBuilder str = new StringBuilder();
		while (it.hasNext()){
			Map.Entry<String, Element> entity = it.next();
			if(!String.valueOf(b).equals(entity.getValue().attr("hidden").equals("")) && !("操作").equals(entity.getValue().html())){
				str.append(entity.getValue().attr("field"));
				str.append(",");
			}
		}
		if (str.length() > 0)
			str = str.deleteCharAt(str.length() - 1);
		return str.toString();
	}
	
	public String getColumnField() {
		Iterator<Map.Entry<String, Element>> it = colPageTableColInfoMap.entrySet().iterator();
		StringBuilder str = new StringBuilder();
		while (it.hasNext()){
			Map.Entry<String, Element> entity = it.next();
			if(entity.getValue().attr("exp").equals("true") && !("操作").equals(entity.getValue().html())){
				str.append(entity.getValue().attr("field"));
				str.append(",");
			}
		}
		if (str.length() > 0)
			str = str.deleteCharAt(str.length() - 1);
		return str.toString();
	}

}
