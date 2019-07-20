package com.zrahh.SpiderDemo.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Page {
	/*
	 * 页面内容
	 */
	private String content;

	/*
	 * 解析出来的内容
	 */
	private Map<String, String> values = new HashMap<String, String>();
	/*
	 * 当前页面的url
	 */
	private String url;
	/*
	 * 商品ID
	 */
	private String goodsId;

	private ArrayList<String> urls = new ArrayList<String>();

	// 生成get set方法 快捷键ctrl + 1
	public String getContent() {
		return content;
	}

	public ArrayList<String> getUrls() {
		return urls;
	}

	public void addUrl(String url) {
		this.urls.add(url);
	}

	public void setContent(String content) {
		this.content = content;
	}

	/*
	 * 向map中添加数据
	 */
	public void addField(String field, String value) {
		this.values.put(field, value);
	}

	/*
	 * 获取map中的数据
	 */
	public Map<String, String> getValues() {
		return this.values;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}
}
