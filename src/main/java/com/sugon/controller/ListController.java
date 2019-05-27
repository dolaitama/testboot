package com.sugon.controller;

import java.io.File;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Lists;
import com.sugon.constants.Const;
import com.sugon.core.model.GridPageInfo;
import com.sugon.core.model.ListParams;
import com.sugon.core.model.QueryBean;
import com.sugon.core.model.SessionInfo;
import com.sugon.core.model.TreeNode;
import com.sugon.core.util.SecurityUtil;
import com.sugon.domain.DyView;
import com.sugon.domain.RightParam;
import com.sugon.domain.Sysmenu;
import com.sugon.domain.Sysright;
import com.sugon.service.BusDataExportExcel;
import com.sugon.service.DyViewService;
import com.sugon.service.RightParamService;
import com.sugon.service.SysmenuService;
import com.sugon.util.HtmlUtil;

/**
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/list")
public class ListController {
	
	@Autowired
	private Const con;
	
	@Autowired
	private DyViewService dyViewService;
	
	@Autowired
	private BusDataExportExcel busDataExportExcel;
	
	@Autowired
	private RightParamService rightParamService;
	
	@Autowired
	private SysmenuService sysmenuService;
	
	private static Logger log = LoggerFactory.getLogger(ListController.class);
	
	
	/**
	 * 主面板
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping("mainPage")
	public String mainPage(HttpServletRequest req, HttpServletResponse res){
		return "login-main";
	}
	
	/**
	 * 跳转到指定页面
	 * @param req
	 * @param res
	 * @param toPage
	 * @return
	 */
	@RequestMapping("redirect")
	public String redirect(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="toPage", required=false) String toPage){
		return toPage;
	}
	
	@RequestMapping("navTreeListByMenu")
	@ResponseBody
	public List<TreeNode> navTreeListByMenu(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="menuId", required=false) String menuId) throws Exception {
		List<TreeNode> treeNodes = Lists.newArrayList();
		try {
			SessionInfo sessionInfo = SecurityUtil.getCurrentSessionInfo(req);
			if (sessionInfo != null) {
				for (TreeNode node : sessionInfo.getSystreeNodeList()) {
					if(StringUtils.isNotBlank(menuId)){
						if (node.getId().equals(menuId)) {
							treeNodes.add(node);
							break;
						}
					}else{
						if(node.getParentid()==null){
							treeNodes.add(node);
						}
					}
				}
			}
			return treeNodes;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	/**
	 * 初始化报表界面
	 * 
	 * @return
	 */
	@RequestMapping("toRptIndex")
	public String toRptIndex(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="rows", required=false) String rows,
			@RequestParam(value="page", required=false) String page,
			@RequestParam(value="sort", required=false) String sort,
			@RequestParam(value="order", required=false) String order,
			@RequestParam(value="id", required=false) String id,ListParams listParams) {
		try {
			if (StringUtils.isNotEmpty(id)) {
				listParams.setDyViewId(id);
				DyView dv = this.dyViewService.businessHeadProcess(id,
						req, rows, page, sort, order);
				req.setAttribute("listParams", listParams);
				if(dv != null && dv.getType()==1){
					return "list/figure";
				}
			}
		} catch (Exception e) {
			log.error("toRptIndex>>", e);
		}
		return "list/center";
	}
	
	/**
	 * 加载报表界面数据
	 * @param req
	 * @param res
	 * @param listParams
	 * @param sort
	 * @param order
	 * @param dyViewId
	 */
	@RequestMapping("queryPageData")
	@ResponseBody
	public GridPageInfo queryPageData(HttpServletRequest req, HttpServletResponse res,
			ListParams listParams,@RequestParam(value="sort", required=false) String sort,
			@RequestParam(value="order", required=false) String order,
			@RequestParam(value="dyViewId", required=false) String dyViewId){
		SessionInfo sessionInfo = SecurityUtil.getCurrentSessionInfo(req);
		List<RightParam> rps = new ArrayList<RightParam>();
		if(!SecurityUtil.checkAdmin(sessionInfo)){
			List<Sysright> rights = sessionInfo.getSysrightList();
			for(Sysright right : rights){
				RightParam param = new RightParam();
				if(right.getMenuid() != null){
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("id", right.getMenuid());
					Sysmenu menu = (Sysmenu) this.sysmenuService.getByParam(map);
					if(menu.getDyviewid() != null && StringUtils.isNotBlank(dyViewId) 
							&& dyViewId.equals(menu.getDyviewid().toString())){
						param.setRightcode(right.getRightcode());
					}
				}else{
					param.setRightcode(right.getRightcode());
				}
				if(StringUtils.isNotBlank(param.getRightcode())){
					List<RightParam> rp = (List<RightParam>) this.rightParamService.queryList(param);
					rps.addAll(rp);
				}
			}
		}
		try {
			if (StringUtils.isNotEmpty(dyViewId)) {
				GridPageInfo gInfo = this.dyViewService.businessDataProcess(dyViewId, req, sort,
					order, rps);
				gInfo.setQuerySql("");
				gInfo.setColumns("");
				return gInfo;
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("报表Id["+listParams.getDyViewId()+"]查询数据出错了！", e);
		}
		return null;
	}
	
	/**
	 * 导出报表
	 * 
	 * @return
	 */
	@RequestMapping("doHandleDataExport")
	@ResponseBody
	public String doHandleDataExport(HttpServletRequest req,
			ListParams listParams,@RequestParam(value="sort", required=false) String sort,
			@RequestParam(value="order", required=false) String order) {
		SessionInfo sessionInfo = SecurityUtil.getCurrentSessionInfo(req);
		List<RightParam> rps = new ArrayList<RightParam>();
		if(!SecurityUtil.checkAdmin(sessionInfo)){
			List<Sysright> rights = sessionInfo.getSysrightList();
			for(Sysright right : rights){
				RightParam param = new RightParam();
				if(right.getMenuid() != null){
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("id", right.getMenuid());
					Sysmenu menu = (Sysmenu) this.sysmenuService.getByParam(map);
					if(menu.getDyviewid() != null && StringUtils.isNotBlank(listParams.getDyViewId()) 
							&& listParams.getDyViewId().equals(menu.getDyviewid().toString())){
						param.setRightcode(right.getRightcode());
					}
				}else{
					param.setRightcode(right.getRightcode());
				}
				List<RightParam> rp = (List<RightParam>) this.rightParamService.queryList(param);
				rps.addAll(rp);
			}
		}
		String returnJson="";
		try {
			QueryBean queryBean = this.dyViewService.getComposeQueryConBean(listParams.getDyViewId(),
							req, sort, order, rps);
			String titleText = queryBean.getExpInfo();
			log.debug("the expInfo is :"+titleText);
			String fileName=String.valueOf(Math.abs(UUID.randomUUID()
					.getLeastSignificantBits()));
			String uuidFile = "";
			String dir = req.getSession().getServletContext().getRealPath("")+ File.separator +"export"
					+ File.separator + fileName;
			File file = new File(dir);
			if (!file.exists()) {
				file.mkdirs();
			}
			uuidFile = dir + File.separator + fileName + ".xls";
			file = new File(uuidFile);
			if (!file.exists()) {
				file.createNewFile();
			}
			String pageTitle = "清单表";
			this.busDataExportExcel.exportFile(uuidFile, pageTitle,
					titleText, queryBean, null);
			pageTitle = URLEncoder.encode(pageTitle, "UTF-8");
			returnJson = "filePath=".concat(uuidFile).concat(
					"&fileName=").concat(fileName);
		} catch (Exception e) {
			log.error("Error in doHandleDataExport", e);
		}
		return returnJson.replaceAll("\\\\", "/");
	}
	
	/**
	 * to配置列表页
	 * @return
	 */
	@RequestMapping("toViewList")
	public String toViewList() {
		return "list/view_list";
	}
	
	/**
	 * 编辑配置报表
	 * 
	 */
	@RequestMapping("viewEdit")
	public String viewEdit(HttpServletRequest req, HttpServletResponse res,
			DyView dyView) {
		try {
			DyView entity = (DyView) this.dyViewService.getByParam(dyView);
			if(entity != null){
				if(StringUtils.isNotBlank(dyView.getDefsql())){
					entity.setDefsql(dyView.getDefsql().replaceAll("&quot;", "\""));
				}
				if(StringUtils.isNotBlank(dyView.getDefjs())){
					entity.setDefjs(dyView.getDefjs().replaceAll("&quot;", "\""));
				}
				if(StringUtils.isNotBlank(dyView.getQuerydef())){
					entity.setQuerydef(dyView.getQuerydef().replaceAll("&quot;","\""));
				}
				if(StringUtils.isNotBlank(dyView.getEoption())){
					entity.setEoption(dyView.getEoption().replaceAll("&quot;", "\""));
				}
				if(StringUtils.isNotBlank(dyView.getOptionsetter())){
					entity.setOptionsetter(dyView.getOptionsetter().replaceAll("&quot;", "\""));
				}
				if(StringUtils.isNotBlank(dyView.getDefcol())){
					entity.setDefcol(dyView.getDefcol().replaceAll("&quot;", "\""));
					Document doc = HtmlUtil.parse(entity.getDefcol());
					/*Elements els = doc.select("th[exp]");*/
					Elements els = doc.select("th");
					StringBuilder colName = new StringBuilder();
					StringBuilder colWidth = new StringBuilder();
					StringBuilder colField = new StringBuilder();
					StringBuilder colMeta = new StringBuilder();
					for(Element el : els){
						if(!StringUtils.isNotBlank(el.attr("exp"))){
							el.attr("exp",con.EXP_DEFAULT);
						}
						if(el.attr("exp").equals("true") && !("操作").equals(el.html())){
							colName.append(el.html());
							colName.append(",");
							colWidth.append(el.attr("width").replaceAll("px", ""));
							colWidth.append(",");
							colField.append(el.attr("field"));
							colField.append(",");
							colMeta.append(el.attr("fieldType"));
							colMeta.append(",");
						}
					}
					if (colName.length() > 0)
						colName = colName.deleteCharAt(colName.length() - 1);
					if (colWidth.length() > 0)
						colWidth = colWidth.deleteCharAt(colWidth.length() - 1);
					if (colField.length() > 0)
						colField = colField.deleteCharAt(colField.length() - 1);
					if (colMeta.length() > 0)
						colMeta = colMeta.deleteCharAt(colMeta.length() - 1);
					String titleText = colName.toString().concat(";")
							.concat(colWidth.toString()).concat(";")
							.concat(colField.toString()).concat(";")
							.concat(colMeta.toString());
					entity.setExpinfo(titleText);
				}
				if(entity.getId() == null){
					this.dyViewService.insert(entity);
				}else{
					this.dyViewService.update(entity);
				}
				ListParams listParams = new ListParams();
				listParams.setWinIsClose(true);
				req.setAttribute("listParams", listParams);
			}
		} catch (Exception e) {
			log.error("Error in DynamicTableAction.viewEdit method!Args-->id is :",e);
			return "error";
		}
		return "list/view_edit";
	}
	
	/**
	 * 到编辑view页面
	 * @return
	 */
	@RequestMapping("toViewEdit")
	public String toViewEdit(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="id", required=false) Long id) {
		ListParams listParams = new ListParams();
		DyView dv = new DyView();
		dv.setId(id);
		listParams.setDyView((DyView) this.dyViewService.getByParam(dv));
		req.setAttribute("listParams", listParams);
		return "list/view_edit";
	}
	
}
