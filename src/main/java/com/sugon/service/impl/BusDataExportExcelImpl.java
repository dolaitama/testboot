package com.sugon.service.impl;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor.HSSFColorPredefined;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sugon.core.model.QueryBean;
import com.sugon.service.BusDataExportExcel;
import com.sugon.service.PageResultSet;

@Service
public class BusDataExportExcelImpl implements BusDataExportExcel{
	
	private static final Logger logger = LoggerFactory.getLogger(BusDataExportExcelImpl.class);

	@Autowired
	private PageResultSet pageResultSet;

	@SuppressWarnings("rawtypes")
	public String exportFile(String fileInfo, String pageTitle,
			String titleText,QueryBean queryBean,String maxExportIndex)throws Exception{
		int maxPageSize = 50000;
		if(StringUtils.isNotBlank(maxExportIndex)){
			maxPageSize = Integer.valueOf(maxExportIndex);
		}
		long countIndex = 999999999;
		String sheetName = pageTitle;

		// 取列头
		String[] colNameIds = titleText.split(";");
		if(colNameIds.length>0){
			String[] colNameInfo = colNameIds[0].split("\\,");// 列头中文信息
			String[] colDataInfo =  colNameIds[3].split("\\,");// 列头数据类型信息
			String[] colLengthInfo =  colNameIds[1].split("\\,");// 列头宽度信息
			String[] colIndexInfo =  colNameIds[2].split("\\,");// 列头数据源标记信息
			int indexPage = 1;
	
			// 写XLS文件
			boolean hasMoreHead = false;
			HSSFWorkbook book = new HSSFWorkbook();
			boolean pageQuery = false;
			List dataList = null;
			if (countIndex > maxPageSize) {// 分页查询
				queryBean.getPageInfo().setPage(1);
				queryBean.getPageInfo().setRp(maxPageSize);
				this.pageResultSet.init(queryBean.getPageInfo());
				dataList = pageResultSet.getData(queryBean.getSql(),
						queryBean.getColPageTableColInfoMap());
				pageQuery = true;
			} 
			if (null == dataList) {
				try{
//					book.close();
				}catch(Exception e){
					
				}
				return null;
			}
			int rownum = 1;
			while(dataList.size() > 0) {
				HSSFCellStyle style = book.createCellStyle();
				style.setBorderBottom(BorderStyle.THIN);
				style.setBorderLeft(BorderStyle.THIN);
				style.setBorderRight(BorderStyle.THIN);
				style.setBorderTop(BorderStyle.THIN);
	
				OutputStream output = null;
				try {
					output = new FileOutputStream(fileInfo);
				} catch (FileNotFoundException e) {
					logger.error("目标文件加载失败", e);
				}
				Sheet sheet = null;
				if (indexPage > 1) {
					sheetName = pageTitle + "-" + (indexPage - 1);
					sheet = book.createSheet(sheetName);
					rownum = 0;
				} else {
					// 写表头
					if (hasMoreHead) {
						sheet = book.getSheetAt(0);
					} else {
						sheet = book.createSheet(sheetName);
					}
	
					if (!hasMoreHead) {
						Row row = sheet.createRow(0);
						HSSFCellStyle headStyle = book.createCellStyle();
						headStyle.setBorderBottom(BorderStyle.THIN);
						headStyle.setBorderLeft(BorderStyle.THIN);
						headStyle.setBorderRight(BorderStyle.THIN);
						headStyle.setBorderTop(BorderStyle.THIN);
	
						// 上下左右居中
						headStyle.setAlignment(HorizontalAlignment.CENTER);
	
						// 设置底色
						headStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
						headStyle.setFillForegroundColor(HSSFColorPredefined.LEMON_CHIFFON.getIndex());
						
						for (int j = 0; j < colNameInfo.length; j++) {
							Cell cell = row.createCell(j);
							cell.setCellValue(colNameInfo[j]);
							cell.setCellStyle(headStyle);
						}
					}
				}
	
	
				// 设置列宽
				for (int j = 0; j < colLengthInfo.length; j++) {
					sheet.setColumnWidth(j,
							Integer.valueOf(colLengthInfo[j])*60 );
				}
	
				// 单元格左对齐
				style.setAlignment(HorizontalAlignment.LEFT);
				style.setWrapText(true);
	
				for (int i = 0; i < dataList.size(); i++) {
					Map dataMap = (Map) dataList.get(i);
					Row row = sheet.createRow(rownum);
					for (int j = 0; j < colIndexInfo.length; j++) {
						Object obj = dataMap.get(colIndexInfo[j]);
						Cell cell = row.createCell(j);
						cell.setCellStyle(style);
						if (null == obj) {
							cell.setCellType(CellType.STRING);
							cell.setCellValue("--");
						} else {
							if (colDataInfo[j].equalsIgnoreCase("integer")) {
								cell.setCellType(CellType.NUMERIC);
								cell.setCellValue(Integer.valueOf(String
										.valueOf(obj)));
							} else if (colDataInfo[j]
									.equalsIgnoreCase("string")) {
								cell.setCellType(CellType.STRING);
								cell.setCellValue(String.valueOf(obj));
							} else if (colDataInfo[j].equalsIgnoreCase("date")) {
								cell.setCellType(CellType.STRING);
								cell.setCellValue(String.valueOf(obj));
							} else if (colDataInfo[j].equalsIgnoreCase("time")) {
								cell.setCellType(CellType.STRING);
								cell.setCellValue(String.valueOf(obj));
							} else if (colDataInfo[j]
									.equalsIgnoreCase("timestamp")) {
								cell.setCellType(CellType.STRING);
								cell.setCellValue(String.valueOf(obj));
							} else {
								cell.setCellType(CellType.STRING);
								cell.setCellValue(String.valueOf(obj));
							}
						}
					}
					rownum++;
				}
				// 清除数据集合重新再查询
				if (pageQuery&&dataList.size()==maxPageSize) {// 执行分页查询
					
					indexPage++;
					queryBean.getPageInfo().setPage(indexPage);
					queryBean.getPageInfo().setRp(maxPageSize);
					try {
						this.pageResultSet.init(queryBean.getPageInfo());
						dataList = pageResultSet.getData(queryBean.getSql(),
								queryBean.getColPageTableColInfoMap());
					} catch (Exception e) {
						logger.error("findDynamicDataList失败!", e);
					}
				}else {
					dataList.clear();
				}
				try {
					book.write(output);
					output.flush();
					output.close();
//					book.close();
				} catch (IOException e) {
					logger.error("输出文件失败!", e);
				}
			}
			return fileInfo;
		}
		return null;
	}

}
