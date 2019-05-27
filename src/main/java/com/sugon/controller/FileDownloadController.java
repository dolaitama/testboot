package com.sugon.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sugon.util.file.FileOperator;
import com.sugon.util.file.FileOperatorImpl;

@Controller
@RequestMapping("fileDownload")
public class FileDownloadController {

	public static final String FILE_NAME = "fileName";

	public static final String FILE_PATH = "filePath";
	
	private FileOperator fileOperator = new FileOperatorImpl();

	@RequestMapping("download")
	@ResponseBody
	private void fileDownLoad(HttpServletRequest req,
			HttpServletResponse response) throws ServletException, IOException {
		String fileName = req.getParameter(FILE_NAME);
		String filePath = req.getParameter(FILE_PATH);
		File loadFile = new File(filePath);

		response.setHeader("Content-disposition", "attachment; filename="
				+ fileName + ".xls");// filename是下载的xls的名，建议最好用英文
		response.setContentType("application/msexcel;charset=UTF-8");// 设置类型
		response.setHeader("Pragma", "No-cache");// 设置头
		response.setHeader("Cache-Control", "no-cache");// 设置头
		response.setDateHeader("Expires", 0);// 设置日期头

		ServletOutputStream out = response.getOutputStream();
		InputStream in = new FileInputStream(loadFile);

		int byteRead = 0;
		byte[] buffer = new byte[8192];
		while ((byteRead = in.read(buffer, 0, 8192)) != -1) {
			out.write(buffer, 0, byteRead);
		}
		in.close();
		out.flush();
		out.close();
		File parent = loadFile.getParentFile();
		fileOperator.deleteFile(parent);
	}

}
