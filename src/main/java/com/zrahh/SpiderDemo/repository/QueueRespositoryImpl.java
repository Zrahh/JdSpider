package com.zrahh.SpiderDemo.repository;

import java.util.concurrent.ConcurrentLinkedQueue;

public class QueueRespositoryImpl implements Repository {
	
	ConcurrentLinkedQueue<String> queue = new ConcurrentLinkedQueue<String>();
	@Override
	public void add(String url) {
		queue.add(url);
	}

	@Override
	public String poll() {
		return queue.poll();
	}

}
