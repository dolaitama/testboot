package com.sugon.service;

import java.util.List;
import java.util.Map;

import org.jsoup.nodes.Element;

import com.sugon.core.model.DataGrid;
import com.sugon.core.model.GridPageInfo;

/**
 * 
 * <P>公司名称: 云潮网络</P>
 * <P>项目名称： 哪有车爬虫</P>
 * <P>模块名称： 分页信息类</P>
 * @Title:	PageResultSet.java
 * @Description: TODO
 * @author:    jieketao
 * @version:   1.0
 * Create at:  2012-1-18 下午02:26:45
 */
public interface PageResultSet {

	/*private static final Logger logger = LoggerFactory.getLogger(PageResultSet.class);

	private GridPageInfo pageInfo;
	
	private int curPage = 1; // 当前页

	private int pageSize = 20; // 每页显示的行数

	private long rowsCount = 0; // 总行数

	private int pageCount = 0; // 总页数

	private String sql = null;
	
	@Resource
	private CommonDao commonMapper;
	
	public PageResultSet(){
		
	}
	
	public PageResultSet(DataGrid dataGrid) {

		this.pageSize = dataGrid.getRows();
		this.curPage = dataGrid.getPage();
		this.rowsCount=dataGrid.getTotal();
	}

	public PageResultSet(GridPageInfo pageInfo) {

		this.pageInfo = pageInfo;
		this.pageSize = pageInfo.getRp();
		this.curPage = pageInfo.getPage();
		this.rowsCount=pageInfo.getTotal();

	}*/
	
	public void init(DataGrid dataGrid);
	
	public void init(GridPageInfo pageInfo);
	
	/**
	 * 获得进行记录总数的查询
	 * 
	 * @param param
	 * @param rowMapper
	 * @return
	 * @throws Exception
	 */
	public long queryRowsCount(Object[] param) throws Exception;

	/**
	 * 查询列表20160320lsx
	 * @param param
	 * @param sql
	 * @param con
	 * @param colFields
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> getData(String sql, Map<String, Element> colFields) throws Exception;
}