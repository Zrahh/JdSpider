package com.zrahh.SpiderDemo.download;

import com.zrahh.SpiderDemo.domain.Page;
import com.zrahh.SpiderDemo.utils.PageUtils;

public class HttpclientableImpl implements Downloadable {

	@Override
	public Page download(String url) {
		Page page = new Page();
		page.setContent(PageUtils.getContent(url));
		page.setUrl(url);
		return page;
	}

}
