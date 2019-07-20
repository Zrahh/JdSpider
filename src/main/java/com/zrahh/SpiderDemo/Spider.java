package com.zrahh.SpiderDemo;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.zrahh.SpiderDemo.domain.Page;
import com.zrahh.SpiderDemo.download.Downloadable;
import com.zrahh.SpiderDemo.download.HttpclientableImpl;
import com.zrahh.SpiderDemo.process.JdProcessable;
import com.zrahh.SpiderDemo.process.JdProcessableImpl;
import com.zrahh.SpiderDemo.repository.RedisRepositoryImpl;
import com.zrahh.SpiderDemo.repository.Repository;
import com.zrahh.SpiderDemo.store.Storeable;
import com.zrahh.SpiderDemo.store.consoleStoreableImpl;

public class Spider {
	// 声明下载功能接口
	private Downloadable downloadable;
	// 声明解析功能接口
	private JdProcessable jdProcessable;
	// 声明存储功能接口
	private Storeable storeable;
	// 声明一个url存储池（队列）
	private Repository repository;
	
	// 创建一个固定大小的线程池
	ExecutorService threadPool = Executors.newFixedThreadPool(5);
	
	@SuppressWarnings("static-access")
	public void start() {
		System.out.println("开始抓取数据。。。");
		while (true) {
			
			final String url = repository.poll();
			if (url != null) {
				threadPool.execute(				new Runnable() {
					public void run() {
						Page page = Spider.this.download(url);
						Spider.this.process(page);
						ArrayList<String> urlList = page.getUrls();
						for (String nextUrl : urlList) {
							repository.add(nextUrl);
						}
						if (url.startsWith("https://item.jd.com/")) {
							Spider.this.store(page);
						}
						// 每个线程到这里休息2s
						try {
							Thread.currentThread().sleep(2000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				});
			} else {
				System.out.println("没有url了。。。休息一会");
				try {
					Thread.currentThread().sleep(5000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}


	}

	public void setSeedUrl(String url) {
		this.repository.add(url);
	}

	public void setRunnableImpl() {
		this.setDownloadable(new HttpclientableImpl());
		this.setJdProcessable(new JdProcessableImpl());
		this.setStoreable(new consoleStoreableImpl());
		this.setRepository(new RedisRepositoryImpl());
	}

	public Page download(String url) {
		return this.downloadable.download(url);
	}

	public void process(Page page) {
		this.jdProcessable.process(page);
	}

	public void store(Page page) {
		this.storeable.store(page);
	}

	// 生成set方法
	public void setDownloadable(Downloadable downloadable) {
		this.downloadable = downloadable;
	}

	public void setJdProcessable(JdProcessable jdProcessable) {
		this.jdProcessable = jdProcessable;
	}


	public void setStoreable(Storeable storeable) {
		this.storeable = storeable;
	}

	public void setRepository(Repository repository) {
		this.repository = repository;
	}
	public static void main(String[] args) {
		Spider spider = new Spider();
		spider.setRunnableImpl();
		String seedUrl = "https://list.jd.com/list.html?cat=9987,653,655";
		spider.setSeedUrl(seedUrl);
		spider.start();
	}

}
