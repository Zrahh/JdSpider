package com.zrahh.SpiderDemo.store;

import com.zrahh.SpiderDemo.domain.Page;

public class consoleStoreableImpl implements Storeable {

	@Override
	public void store(Page page) {
		System.out.println(page.getUrl()+"\t"+page.getValues().get("price"));
	}

}
