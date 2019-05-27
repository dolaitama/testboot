/**
 * 
 */
package com.sugon.constants;

import java.util.HashMap;

/**
 * @author pc
 * @date 2017年11月24日 上午10:29:13
 */
public class ConstKindeditor {
	
	//后缀名定义
	public static HashMap<String, String> extMap = new HashMap<String, String>(){
		private static final long serialVersionUID = -5670443877678942406L;
		{
			put("image", "gif,jpg,jpeg,png,bmp");
			put("flash", "swf,flv");
			put("media", "swf,flv,mp3,wav,wma,wmv,mid,avi,mpg,asf,rm,rmvb");
			put("file", "doc,docx,xls,xlsx,ppt,htm,html,txt,zip,rar,gz,bz2");
		}
	};

}
