package com.zrahh.SpiderDemo.utils;

import org.htmlcleaner.TagNode;
import org.htmlcleaner.XPatherException;

public class HtmlUtils {
	
	public static String getText(TagNode node,String xpath ) {
		
		Object[] evaluateXPath;
		String result = null;
		try {
			evaluateXPath = node.evaluateXPath(xpath);
			if (evaluateXPath.length > 0) {
				// 获取指定标签
				TagNode titleNode = (TagNode)evaluateXPath[0];
				// 获取标签内容
				result = titleNode.getText().toString().trim();
			}
		} catch (XPatherException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return result;
			
	}
	
	public static String getAttrByName(TagNode node, String xpath, String attr) {
		
		Object[] evaluateXPath2;
		String result=null;
		try {
			evaluateXPath2 = node.evaluateXPath(xpath);
			// 判断长>0
			if (evaluateXPath2.length>0) {
				TagNode picNode = (TagNode)evaluateXPath2[0];
				// 根据标签属性获标签值
				result = picNode.getAttributeByName(attr);
				
			}
		} catch (XPatherException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;	
		
	}

}
