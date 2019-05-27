package com.sugon.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sugon.core.model.GridPageInfo;
import com.sugon.core.model.Option;
import com.sugon.core.model.QueryBean;
import com.sugon.core.util.GridPageInfoUtil;
import com.sugon.domain.DyView;
import com.sugon.domain.RightParam;
import com.sugon.service.DyViewService;
import com.sugon.service.ISqlCombin;
import com.sugon.service.PageResultSet;
import com.sugon.service.impl.base.BaseServiceImpl;
import com.sugon.util.DateUtil;
import com.sugon.util.HtmlUtil;

@Service
public class DyViewServiceImpl extends BaseServiceImpl implements DyViewService{
	
	private static Logger log = LoggerFactory.getLogger(DyViewServiceImpl.class);
	
	public DyViewServiceImpl() {
		super();
		this.mapper="DyViewMapper";
	}
	
	@Autowired
	private PageResultSet pageResultSet;
	
	@Autowired
	private ISqlCombin iSqlCombin;

	//查询列表业务逻辑
	@Override
	public GridPageInfo businessDataProcess(String dyViewId, HttpServletRequest req, String sort, String order, List<RightParam> rps)
			throws Exception {
		GridPageInfo gInfo = new GridPageInfo();
		if (StringUtils.isNotEmpty(dyViewId)) {
			QueryBean queryBean = getComposeQueryConBean(dyViewId,
					req, sort, order, rps);
			//查询列表数据
			findDyDataList(queryBean);
			gInfo = queryBean.getPageInfo();
		}
		return gInfo;
		
	}
	
	@Override
	public QueryBean getComposeQueryConBean(String viewId,
			HttpServletRequest req, String sort, String order, List<RightParam> rps)
			throws Exception {
		QueryBean result=new QueryBean();
		if (StringUtils.isNotEmpty(viewId)) {
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("id", viewId);
			DyView view = (DyView) this.baseDao.getByParam(this.mapper+".getByParam", param);
			if (view != null) {
				GridPageInfo pageInfo = GridPageInfoUtil
						.getGridPageInfo(req);
				Map<String, Element> colFieldMap = new LinkedHashMap<String, Element>();
				Document doc = HtmlUtil.parse(view.getDefcol());
				Elements els = doc.select("th");
				for (Element el : els) {
					colFieldMap.put(el.attr("field"), el);
				}
				//获取完整查询语句
				String sql = this.iSqlCombin.getQuerySql(view,req,order,sort, rps);
				result.setQueryDef(view.getQuerydef());
				result.setColPageTableColInfoMap(colFieldMap);
				result.setSort(sort);
				result.setOrder(order);
				result.setSql(sql);
				result.setPageInfo(pageInfo);
				result.setExpInfo(view.getExpinfo());
			}
		}
		return result;
	}
	
	/**
	 * 获得分页数据对象
	 */
	public void findDyDataList(QueryBean bean)
			throws Exception {
		this.pageResultSet.init(bean.getPageInfo());
		bean.getPageInfo().setRows(pageResultSet.getData(bean.getSql(),
				bean.getColPageTableColInfoMap()));
	}
	
	/**
	 * 动态报表业务处理，首先从查询已经配置数据 然后通过gson将json转化为java对象 然后通过操作java对象的方式动态拼html以及sql
	 */
	@Override
	public DyView businessHeadProcess(String viewId, HttpServletRequest req,
			String pageSize, String pageIndex, String sort, String order)
			throws Exception {
		DyView dataBean = null;
		if (StringUtils.isNotEmpty(viewId)) {
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("id", viewId);
			dataBean = (DyView) this.baseDao.getByParam(this.mapper+".getByParam", param);
			if(dataBean != null){
				String queryDef = "";
				try {
					if(dataBean.getQuerydef()!=null){//初始化下拉列表数据
						queryDef = initSelectOptions(dataBean.getQuerydef());
						req.setAttribute("formHtml", queryDef);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				if(dataBean.getType()==0){
					log.debug("****************formHtml**************");
					log.debug(queryDef);
					req.setAttribute("headHtml", dataBean.getDefcol());
					log.debug("****************headHtml**************");
					log.debug(dataBean.getDefcol());
				}else{
					req.setAttribute("option", dataBean.getEoption());
					req.setAttribute("optionsetter", dataBean.getOptionsetter());
				}
				req.setAttribute("js", dataBean.getDefjs());
				
			}
		}
		return dataBean;
	}
	
	/**
	 * 初始化下拉框数据
	 */
	public String initSelectOptions(String queryDef)throws Exception{
		Document doc = HtmlUtil.parse(queryDef);
		Elements selects = doc.select("select");
		Elements inputs = doc.select("input");
		for(Element select : selects){
			String selectSql = select.attr("optionSql");
			if(StringUtils.isNotEmpty(selectSql)){
				List<Option> optionList=iSqlCombin.findOptionsBySql(selectSql,
						select.attr("headName"),
						select.attr("headValue"),
						select.attr("defaultValue")
					);
				String options = "";
				for(Option opt : optionList){
					String selected="";
					if(opt.isDefaultSelect()){
						selected = " selected='selected'";
					}
					options += "<option value='"+opt.getValue()+"'"+selected+">"+opt.getText()+"</option>";
				}
				select.html(options);
			}
		}
		
		for(Element input : inputs){
			String dfd = input.attr("defaultDate");
			input.removeAttr("defaultDate");
			if(StringUtils.isNotBlank(dfd)){
				int day = Integer.parseInt(dfd);
				Calendar cal = Calendar.getInstance();
				cal.add(Calendar.DATE, day);
				Date date = cal.getTime();
				String time = DateUtil.date2Str(date, DateUtil.FORMAT_DATE);
				input.val(time);
			}
		}
		
		queryDef = doc.select("body").html();
		queryDef = queryDef.replaceAll(" sql=\"(.*?)\"", " ")
				.replaceAll("sql='(.*?)'", " ")
				.replaceAll("optionSql=\"(.*?)\"", " ")
				.replaceAll("optionSql='(.*?)'", " ")
				.replaceAll("headName=\"(.*?)\"", " ")
				.replaceAll("headName='(.*?)'", " ")
				.replaceAll("defaultValue=\"(.*?)\"", " ")
				.replaceAll("defaultValue='(.*?)'", " ")
				.replaceAll("headValue=\"(.*?)\"", " ")
				.replaceAll("headValue='(.*?)'", " ")
				.replaceAll("optionValue=\"(.*?)\"", " ")
				.replaceAll("optionValue='(.*?)'", " ")
				.replaceAll("optionName=\"(.*?)\"", " ")
				.replaceAll("optionName='(.*?)'", " ")
				.replaceAll("#contener#", "<span style=\"display:-moz-inline-box;display:inline-block;padding-left:5px;\">")
				.replaceAll("#/contener#", "</span>");
		return queryDef;
	}

}
