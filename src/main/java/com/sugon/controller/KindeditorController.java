/**
 * 
 */
package com.sugon.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.sugon.constants.Const;
import com.sugon.constants.ConstKindeditor;
import com.sugon.util.MD5Util;

/**
 * @author pc
 * @date 2017年11月23日 上午10:04:10
 */
@Controller
@RequestMapping("/kindeditor")
public class KindeditorController {
	
	@Autowired
	private Const con;
	
	@RequestMapping("upload")
	@ResponseBody
	public Map<String, Object> upload(HttpServletRequest req, HttpServletResponse res) {
		// 文件保存目录路径
		String savePath = con.IMAGE_SAVEPATH;
		// 允许上传的文件扩展名
		HashMap<String, String> extMap = ConstKindeditor.extMap;

		if (!ServletFileUpload.isMultipartContent(req)) {
			return getError("请选择文件。");
		}
		// 检查目录
		File uploadDir = new File(savePath);
		if (!uploadDir.isDirectory()) {
			return getError("上传目录不存在。");
		}
		// 检查目录写权限
		if (!uploadDir.canWrite()) {
			return getError("上传目录没有写权限。");
		}

		String dirName = req.getParameter("dir");
		if (dirName == null) {
			dirName = "image";
		}
		if (!extMap.containsKey(dirName)) {
			return getError("目录名不正确。");
		}

		// 创建文件夹
		savePath += dirName + "/";
		File saveDirFile = new File(savePath);
		if (!saveDirFile.exists()) {
			saveDirFile.mkdirs();
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String ymd = sdf.format(new Date());
		savePath += ymd + "/";
		File dirFile = new File(savePath);
		if (!dirFile.exists()) {
			dirFile.mkdirs();
		}

		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) req;
		List<MultipartFile> list = multipartRequest.getMultiFileMap().get(
				"imgFile");
		for (MultipartFile file : list) {

			String fileName = file.getOriginalFilename();
			// 检查扩展名
			String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1)
					.toLowerCase();
			if (!Arrays.<String> asList(extMap.get(dirName).split(","))
					.contains(fileExt)) {
				return getError("上传文件扩展名是不允许的扩展名。\n只允许" + extMap.get(dirName)
						+ "格式。");
			}

			SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
			String newFileName = df.format(new Date()) + "_"
					+ new Random().nextInt(1000) + "." + fileExt;

			try {
				File uploadedFile = new File(savePath, newFileName);
				FileCopyUtils.copy(file.getInputStream(), new FileOutputStream(
						uploadedFile));

				String localFileName = uploadedFile.getName();
				String fileNamePrefix = new SimpleDateFormat("yyyyMMddHHmmss")
						.format(new Date());
				String folderName = new SimpleDateFormat("yyyyMMdd")
						.format(new Date());
				System.out.println(folderName);
				newFileName = MD5Util.md5(new StringBuilder()
						.append(localFileName).append(fileNamePrefix)
						.toString())
						+ localFileName.substring(localFileName
								.lastIndexOf("."));

				Map<String, Object> obj = new HashMap<String, Object>();
				String url = "http://localhost:8080/images_cus/image/20171122/20171122173105_695.jpg";
				obj.put("error", 0);
				obj.put("url", url);
				return obj;
			} catch (Exception e) {
				return getError(e.getMessage());
			}
		}
		return getError("请选择文件。");
	}

	public Map<String, Object> getError(String message) {
		Map<String, Object> obj = new HashMap<String, Object>();
		obj.put("error", 1);
		obj.put("message", message);
		return obj;
	}

}
