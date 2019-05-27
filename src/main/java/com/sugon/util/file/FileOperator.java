package com.sugon.util.file;

import java.io.File;
import java.io.InputStream;
import java.util.List;

public abstract interface FileOperator {
	public abstract void writeFile(String paramString1, String paramString2,
			String paramString3);

	public abstract void writeFile(String paramString1, String paramString2,
			String paramString3, String paramString4);

	public abstract String readFile(String paramString);

	public abstract List<String> readFileList(String paramString);

	public abstract List<String> readFileList(String paramString1,
			String paramString2);

	public abstract boolean delete(String paramString);

	public abstract boolean deleteFile(File paramFile);

	public abstract boolean copyFile(File paramFile1, File paramFile2);

	public abstract boolean copy(File paramFile1, File paramFile2);

	public abstract boolean writeFile(String paramString, File paramFile);

	public abstract String fileName(String paramString);

	public abstract void writeFile(String paramString1, String paramString2,
			List<String> paramList);

	public abstract boolean writeFileByPath(InputStream paramInputStream,
			String paramString) throws Exception;
}