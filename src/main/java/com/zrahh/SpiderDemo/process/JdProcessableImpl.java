package com.zrahh.SpiderDemo.process;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import org.htmlcleaner.XPatherException;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zrahh.SpiderDemo.domain.Page;
import com.zrahh.SpiderDemo.utils.HtmlUtils;
import com.zrahh.SpiderDemo.utils.PageUtils;

public class JdProcessableImpl implements JdProcessable {

	@Override
	public void process(Page page) {
		// 解析页面内容，返回根节点
		HtmlCleaner htmlCleaner = new HtmlCleaner();
		TagNode rootNode = htmlCleaner.clean(page.getContent());
		if (page.getUrl().startsWith("https://list.jd.com/list.html")) {
			Object[] evaluateXPath;
			try {
				evaluateXPath = rootNode.evaluateXPath("//li[@class=\"gl-item\"]/div/div[1]/a");
				String url = null;
				for (Object object : evaluateXPath) {
					TagNode aNode = (TagNode) object;
					url = "https:" + aNode.getAttributeByName("href");
					page.addUrl(url);
					
				}

				String nextUrl = HtmlUtils.getAttrByName(rootNode, "//a[@class=\"pn-next\"]", "href");
				if (nextUrl != null) {					
					page.addUrl("https://list.jd.com" + nextUrl);
				}

			} catch (XPatherException e) {
				e.printStackTrace();
			}

		} else {
			praseProduct(page, rootNode);
		}

	}

	private void praseProduct(Page page, TagNode clean) {
		try {
			// 获取title
			getTitle(page, clean);

			// 获取图片地址
			getPicUrl(page, clean);

			// 获取价格
			JSONArray jsonArray = getPrice(page);

			// 提取标签属性
			getSpec(page, clean, jsonArray);

		} catch (XPatherException e) {
			e.printStackTrace();
		}
	}

	private void getPicUrl(Page page, TagNode clean) {
		String picUrl = "https:" + HtmlUtils.getAttrByName(clean, "//img[@id=\"spec-img\"]", "data-origin");
		page.addField("picUrl", picUrl);
	}

	private void getTitle(Page page, TagNode clean) {
		String title = HtmlUtils.getText(clean, "//div[@class=\"sku-name\"]");
		// 添加数据到page对象中的map
		page.addField("title", title);
	}

	private void getSpec(Page page, TagNode clean, JSONArray jsonArray) throws XPatherException {
		// 获取规格与包装
		// 获取大分类的标签信息
		Object[] evaluateXPath3 = clean.evaluateXPath("//div[@class=\"Ptable\"]/div");
		JSONArray specArray = new JSONArray();
		for (Object divObj : evaluateXPath3) {
			// 获取具体的大分类标签
			TagNode divNode = (TagNode) divObj;
			// 在divNode基础上做匹配
			Object[] evaluateXPath4 = divNode.evaluateXPath("/dl/dl");
			// 迭代dl标签，获取dt，dd
			for (Object dlObj : evaluateXPath4) {
				if (evaluateXPath4.length > 0) {
					TagNode dlNode = (TagNode) dlObj;
					Object[] dtXPath = dlNode.evaluateXPath("/dt");
					JSONObject jsonObject = new JSONObject();
					if (dtXPath.length > 0) {
						TagNode dtNode = (TagNode) dtXPath[0];
						jsonObject.put("name", dtNode.getText());
					}
					Object[] ddXPath = dlNode.evaluateXPath("/dd/[last()]");
					if (ddXPath.length > 0) {
						TagNode ddNode = (TagNode) ddXPath[0];
						jsonObject.put("values", ddNode.getText());
					}
					// 将json对象存入json数组
					specArray.add(jsonObject);

				}

			}
		}
		page.addField("spec", specArray.toJSONString());
	}

	private JSONArray getPrice(Page page) {
		// 根据dataUrl获取goodsID
		String dataUrl = page.getUrl();
		String goodsId = null;
		Pattern pattern = java.util.regex.Pattern.compile("https://item.jd.com/([0-9]+).html");
		Matcher matcher = pattern.matcher(dataUrl);
		if (matcher.find()) {
			goodsId = matcher.group(1);
			page.setGoodsId(goodsId);
			// 打印goodsId
			// System.out.println(goodsId);
		}
		// 获取价格
		String priceContent = PageUtils.getContent("https://p.3.cn/prices/mgets?skuIds=J_" + goodsId);
		JSONArray jsonArray = JSONArray.parseArray(priceContent);
		// 判断json数组的size是否大于0，大于的话则取第一个付给jsonObj
		if (jsonArray.size() > 0) {
			JSONObject jsonObj = jsonArray.getJSONObject(0);
			// 取p对应的字符串并替换.00
			String price = jsonObj.getString("p").replaceAll("\\.00", "");
			page.addField("price", price);
		}
		return jsonArray;
	}

}
