package com.sugon.util.file;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class FileOperatorImpl
  implements FileOperator
{
  private static final Log log = LogFactory.getLog(FileOperatorImpl.class);

  public static int BF_SIZE = 1024;

  public String readFile(String path) {
    try {
      BufferedReader reader = new BufferedReader(new FileReader(path));
      try {
        StringBuilder builder = new StringBuilder();
        char[] ch = new char[BF_SIZE];
        for (int i = 0; (i = reader.read(ch)) != -1; ) {
          builder.append(ch, 0, i);
        }
        return builder.toString();
      } finally {
        reader.close();
      }
    } catch (FileNotFoundException e) {
      log.error("file is not found in readFile method, path is:" + path, 
        e);
    } catch (IOException e) {
      log.error("IO Exception in readFile method, path is:" + path, e);
    }
    return "";
  }

  public List<String> readFileList(String path) {
    List result = new ArrayList();
    try
    {
      File file = new File(path);
      if (!file.exists()) {
        file.createNewFile();
      }
      InputStreamReader isr = new InputStreamReader(new FileInputStream(
        path), "gbk");
      BufferedReader reader = new BufferedReader(isr);
      try {
        String str = "";
        do {
          result.add(str);

          if ((str = reader.readLine()) == null) break;  } while (str.length() > 0);
      }
      finally
      {
        reader.close();
      }
    } catch (FileNotFoundException e) {
      log.warn("文件未找到！：" + path, e);
    } catch (IOException e) {
      log.warn("IO异常！：" + path, e);
    }
    return result;
  }

  public List<String> readFileList(String path, String charset) {
    List result = new ArrayList();
    try {
      InputStreamReader isr = new InputStreamReader(new FileInputStream(
        path), charset);
      BufferedReader reader = new BufferedReader(isr);
      try {
        String str = "";
        do {
          result.add(str);

          if ((str = reader.readLine()) == null) break;  } while (str.length() > 0);
      }
      finally
      {
        reader.close();
      }
    } catch (FileNotFoundException e) {
      log.warn("文件未找到！：" + path, e);
    } catch (IOException e) {
      log.warn("IO异常！：" + path, e);
    }
    return result;
  }

  public void writeFile(String path, String name, String content) {
    if (content == null)
      content = "";
    try
    {
      File f = new File(path);
      if (!f.exists()) {
        f.mkdirs();
      }
      BufferedWriter writer = new BufferedWriter(new FileWriter(path + 
        name, true));
      try {
        if (StringUtils.isNotBlank(content)) {
          writer.write(content);
          writer.newLine();
          writer.flush();
        }
      } finally {
        writer.close();
      }
    } catch (IOException e) {
      log.error("IO Exception in writeFile method, path is:" + path + 
        ", name is:" + name + ", content is:" + content, e);
      throw new RuntimeException(e);
    }
  }

  public void writeFile(String path, String name, String content, String charset) {
    if (content == null)
      content = "";
    try
    {
      File f = new File(path);
      if (!f.exists()) {
        f.mkdirs();
      }
      FileOutputStream fos = new FileOutputStream(path + name);
      OutputStreamWriter oswrite = new OutputStreamWriter(fos, charset);
      BufferedWriter writer = new BufferedWriter(oswrite);
      try {
        if (StringUtils.isNotBlank(content)) {
          writer.write(content);
          writer.newLine();
          writer.flush();
        }
      } finally {
        writer.close();
      }
    } catch (IOException e) {
      log.error("IO Exception in writeFile method, path is:" + path + 
        ", name is:" + name + ", content is:" + content, e);
      throw new RuntimeException(e);
    }
  }

  public void writeFile(String path, String name, List<String> list) {
    try {
      File f = new File(path);
      if (!f.exists()) {
        f.mkdirs();
      }
      BufferedWriter writer = new BufferedWriter(new FileWriter(path + 
        name, true));
      try {
        for (String content : list) {
          if ((content != null) && (content.length() > 0)) {
            writer.write(content);
            writer.newLine();
          }
        }
        writer.flush();
      } finally {
        writer.close();
      }
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public boolean writeFile(String url, File dist) {
    try {
      writeFile(new URL(url).openStream(), dist);
      return true;
    } catch (IOException e) {
      log.error("IO Exception in writeFile method, url is:" + url, e);
    }return false;
  }

  public boolean deleteFile(File file)
  {
    if (file.isDirectory()) {
      for (File f : file.listFiles()) {
        deleteFile(f);
      }
      file.delete();
    } else {
      file.delete();
    }
    return file.delete();
  }

  public boolean delete(String fileName)
  {
    File file = new File(fileName);
    if (!file.exists()) {
      log.debug("删除文件失败：" + fileName + "文件不存在");
      return false;
    }
    if (file.isFile())
    {
      return deleteFile(fileName);
    }
    return deleteDirectory(fileName);
  }

  public boolean deleteFile(String fileName)
  {
    File file = new File(fileName);
    if ((file.isFile()) && (file.exists())) {
      file.delete();
      log.debug("删除单个文件" + fileName + "成功！");
      return true;
    }
    log.debug("删除单个文件" + fileName + "失败！");
    return false;
  }

  public boolean deleteDirectory(String dir)
  {
    if (!dir.endsWith(File.separator)) {
      dir = dir + File.separator;
    }
    File dirFile = new File(dir);

    if ((!dirFile.exists()) || (!dirFile.isDirectory())) {
      log.debug("删除目录失败" + dir + "目录不存在！");
      return false;
    }
    boolean flag = true;

    File[] files = dirFile.listFiles();
    for (int i = 0; i < files.length; i++)
    {
      if (files[i].isFile()) {
        flag = deleteFile(files[i].getAbsolutePath());
        if (!flag) {
          break;
        }
      }
      else
      {
        flag = deleteDirectory(files[i].getAbsolutePath());
        if (!flag)
        {
          break;
        }
      }
    }
    if (!flag) {
      log.debug("删除目录失败");
      return false;
    }

    if (dirFile.delete()) {
      log.debug("删除目录" + dir + "成功！");
      return true;
    }
    log.debug("删除目录" + dir + "失败！");
    return false;
  }

  public boolean copy(File src, File dist)
  {
    boolean isSuccess = false;
    if (src.exists()) {
      if (src.isFile()) {
        isSuccess = copyFile(src, dist);
      }
      else {
        if (dist.getPath().indexOf(src.getPath()) != -1) {
          return false;
        }
        dist.mkdirs();
        File[] fs = src.listFiles();
        for (File f : fs) {
          copy(
            f, 
            new File(dist.getPath() + File.separator + 
            f.getName()));
        }
        isSuccess = true;
      }
    }
    return isSuccess;
  }

  public boolean copyFile(File src, File dist) {
    if ((src.exists()) && (src.isFile())) {
      try {
        writeFile(new FileInputStream(src), dist);
        return true;
      } catch (FileNotFoundException e) {
        log.error(
          "File is not found in copyFile method, srcFile is:" + 
          src.getAbsolutePath() + ", distPath is:" + 
          dist.getAbsolutePath(), e);
      }
    }
    return false;
  }

  public boolean writeFile(InputStream sour, File dist) {
    try {
      File p = dist.getParentFile();
      if ((p != null) && (!p.exists())) {
        p.mkdirs();
      }
      BufferedInputStream is = null;
      BufferedOutputStream os = null;
      byte[] bf = new byte[BF_SIZE];
      try {
        is = new BufferedInputStream(sour);
        os = new BufferedOutputStream(new FileOutputStream(dist));
        for (int i = -1; (i = is.read(bf)) != -1; ) {
          os.write(bf, 0, i);
        }
        return true;
      } finally {
        if (is != null) {
          is.close();
        }
        if (os != null)
          os.close();
      }
    }
    catch (FileNotFoundException e) {
      log.warn("复制文件时，找不到文件", e);
    } catch (IOException e) {
      log.warn("复制文件时，io异常", e);
    }
    return false;
  }

  public boolean writeFileByPath(InputStream sour, String path) throws Exception
  {
    try {
      File dist = new File(path);
      BufferedInputStream is = null;
      BufferedOutputStream os = null;
      byte[] bf = new byte[BF_SIZE];
      try {
        is = new BufferedInputStream(sour);
        os = new BufferedOutputStream(new FileOutputStream(dist));
        for (int i = -1; (i = is.read(bf)) != -1; ) {
          os.write(bf, 0, i);
        }
        return true;
      } finally {
        if (is != null) {
          is.close();
        }
        if (os != null)
          os.close();
      }
    }
    catch (FileNotFoundException e) {
      throw e;
    } catch (IOException e) {
      throw e;
    }
  }

  public String fileName(String resUrl) {
    int datepath = Calendar.getInstance().get(1) + 
      Calendar.getInstance().get(2) + 
      Calendar.getInstance().get(5);
    resUrl = datepath + resUrl;
    return resUrl;
  }

  public static void main(String[] args) {
    FileOperatorImpl f = new FileOperatorImpl();
    String path = "/data/app/nyc/website/seed/";
    String name = "22.txt";
    String content = "hello1";
    f.writeFile(path, name, content);
  }
}
