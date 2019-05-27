package com.sugon.util;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class HtmlUtil {
	/*public static Map<String, ColsInfo> init(String htmlStr){
		Map<String, ColsInfo> colsMap = new HashMap<String, ColsInfo>();
		htmlStr = "<html><body><table>"+htmlStr+"</table></body></html>";
		Document doc = Jsoup.parse(htmlStr); 
		Elements els = doc.select("th");
		for(Element el : els){
			
		}
		return doc;
	}*/

	public static Document parse(String htmlStr) {
		htmlStr = "<html><body>"+htmlStr+"</body></html>";
		Document doc = Jsoup.parse(htmlStr);
		return doc;
	}

}
