package com.sugon.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import com.sugon.core.exception.AppRunTimeException;
import com.sugon.core.model.Option;
import com.sugon.dao.base.BaseDao;
import com.sugon.domain.DyView;
import com.sugon.domain.RightParam;
import com.sugon.service.ISqlCombin;
import com.sugon.util.HtmlUtil;

@Service
public class ISqlCombinImpl implements ISqlCombin {

	@Resource
	private BaseDao baseDao;
	
	/**
	 * 拼接查询条件20160320lsx
	 */
	public String getQuerySql(DyView view, HttpServletRequest req, String order, String sort, List<RightParam> rps) throws AppRunTimeException {
		try {
			String sql = "";
			String querySql = "";
			if (view.getDefsql() != null) {
				if (StringUtils.isNotBlank(view.getDefsql())) {
					sql += view.getDefsql();
				}
				
				String queryDef = view.getQuerydef();
				Document doc = HtmlUtil.parse(queryDef);
				Elements inputs = doc.select("input[name]");
				Elements selects = doc.select("select[name]");
				for(Element input : inputs){
					String name = input.attr("name");
					if("page".equals(name) || "rows".equals(name)){
						continue;
					}
					String sel_sql = input.attr("sql");
					String param = req.getParameter(name);
					if(StringUtils.isNotBlank(param)){
						if(StringUtils.isNotBlank(querySql)){
							querySql += " and ";
						}
						if(StringUtils.isNotBlank(sel_sql)){
							querySql += " "+sel_sql+" ";
							querySql = querySql.replaceAll("#"+name+"#", param);
						}else{
							querySql += " "+name+"='"+param+"' ";
						}
					}
				}
				for(Element select : selects){
					String name = select.attr("name");
					String sel_sql = select.attr("sql");
					String param = req.getParameter(name);
					if(StringUtils.isNotBlank(param)){
						if(StringUtils.isNotBlank(querySql)){
							querySql += " and ";
						}
						if(StringUtils.isNotBlank(sel_sql)){
							querySql += " "+sel_sql+" ";
							querySql = querySql.replaceAll("#"+name+"#", param);
						}else{
							querySql += " "+name+"='"+param+"' ";
						}
					}
				}
			}
			//数据控制权限
			for(RightParam rp : rps){
				if("page".equals(rp.getParamkey()) || "rows".equals(rp.getParamkey())){
					continue;
				}
				if(StringUtils.isNotBlank(querySql)){
					querySql += " and ";
				}
				String val = "";
				if("like".equals(rp.getOperate())){
					val += " like '%"+rp.getParamvalue()+"%'";
				}else if("in".equals(rp.getOperate()) || "not in".equals(rp.getOperate())){
					val += " "+rp.getOperate()+" ("+rp.getParamvalue()+")";
				}else{
					val += rp.getOperate()+"'"+rp.getParamvalue()+"'";
				}
				querySql +=" "+rp.getParamkey()+val;
			}
			if (StringUtils.isNotBlank(order) && StringUtils.isNotBlank(sort)) {
				sql = sql.replaceAll("# order(.*?)#", " order by " +  sort+ " " + order)
						.replaceAll("#order(.*?)#", " order by " +  sort+ " " + order);
			}else{
				sql = sql.replaceAll("#(.*?)order by #", " ").replaceAll("#(.*?)order by#", " ").replaceAll("#", " ");
			}
			if(StringUtils.isNotBlank(querySql)){
				sql = sql.replaceAll("1=1 and", " "+querySql+" and ")
							.replaceAll("1 =1 and", " "+querySql+" and ")
							.replaceAll("1 = 1 and", " "+querySql+" and ")
							.replaceAll("1= 1 and", " "+querySql+" and ")
							.replaceAll("1=1", " "+querySql+" ")
							.replaceAll("1 = 1", " "+querySql+" ")
							.replaceAll("1 =1", " "+querySql+" ")
							.replaceAll("1= 1", " "+querySql+" ");
			}else{
				sql = sql.replaceAll("where 1=1 and", " where ")
							.replaceAll("where 1 = 1 and", " where ")
							.replaceAll("where 1 =1 and", " where ")
							.replaceAll("where 1= 1 and", " where ")
							.replaceAll("where 1=1", " ")
							.replaceAll("where 1 = 1", " ")
							.replaceAll("where 1 =1", " ")
							.replaceAll("where 1= 1", " ");
			}
			/*sql = sql.replaceAll("& and(.*?)&", " "+querySql+" ").replaceAll("&and(.*?)&", " "+querySql+" ");*/
			return sql;
		} catch (Exception e) {
			throw new AppRunTimeException(
					"Error in ISqlCombinImpl getQuerySql :", e);
		}
	}

	@Override
	public List<Option> findOptionsBySql(String sql, String headKey,
			String keyValue, String defaultValule) throws AppRunTimeException {
		List<Option> options=new ArrayList<Option>();
		if(!("").equals(sql)){
			Map<String, Object> sqlmap = new HashMap<String, Object>();
			sqlmap.put("sql", sql);
			List<Map<String,Object>> mapList=(List<Map<String, Object>>) this.baseDao.queryList("DyViewMapper.excuteQuery", sqlmap);
			if(StringUtils.isNotBlank(headKey)){
				Option option=new Option();
				option.setText(headKey);
				option.setValue(keyValue);
				options.add(option);
			}
			for(Map<String,Object> map:mapList){
				Option option=new Option();
				option.setText(map.get("name").toString());
				option.setValue(map.get("id").toString());
				if(map.get("id").toString().equals(defaultValule)){
					option.setDefaultSelect(true);
				}
				options.add(option);
			}
		}
		return options;
	}
}
