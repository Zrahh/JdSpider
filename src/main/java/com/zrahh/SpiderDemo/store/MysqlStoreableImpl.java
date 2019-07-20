package com.zrahh.SpiderDemo.store;

import java.util.Date;
import java.util.Map;

import com.zrahh.SpiderDemo.domain.Page;
import com.zrahh.SpiderDemo.utils.MyDateUtils;
import com.zrahh.SpiderDemo.utils.MyDbUtils;

public class MysqlStoreableImpl implements Storeable {

	@Override
	public void store(Page page) {
		String goods_id = page.getGoodsId();
		String data_url = page.getUrl();
		Map<String, String> values = page.getValues();
		String pic_url = values.get("picUrl");
		String title = values.get("title");
		String price = values.get("price");
		String param = values.get("spec");
		String curr_time = MyDateUtils.formatDate2(new Date());
		MyDbUtils.update(MyDbUtils.INSERT_LOG, goods_id, data_url, pic_url, title, price, param, curr_time);
		//System.out.println(goods_id + "\n" + data_url + "\n" + pic_url + "\n" + title + "\n" + price + "\n" + param + "\n" + curr_time);
	}

}
