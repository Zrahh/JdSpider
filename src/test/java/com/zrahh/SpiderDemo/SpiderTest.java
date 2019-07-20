package com.zrahh.SpiderDemo;

import org.junit.Test;

import com.zrahh.SpiderDemo.domain.Page;
import com.zrahh.SpiderDemo.download.HttpclientableImpl;
import com.zrahh.SpiderDemo.process.JdProcessableImpl;
import com.zrahh.SpiderDemo.store.MysqlStoreableImpl;

public class SpiderTest {
	@Test
	public void testSpider() {
		Spider spider = new Spider();
		//	给下载接口设置实现类
		spider.setDownloadable(new HttpclientableImpl());
		spider.setJdProcessable(new JdProcessableImpl());
		spider.setStoreable(new MysqlStoreableImpl());
		// 定义种子url
		String url = "https://list.jd.com/list.html?cat=9987,653,655";
		Page page = spider.download(url);
		// 解析page，并存储进page对象
		spider.process(page);

		// 将对象中的数据存储进mysql
		//spider.store(page);
	}

}
