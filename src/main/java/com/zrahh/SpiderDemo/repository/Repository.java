package com.zrahh.SpiderDemo.repository;

public interface Repository {
	void add(String url);
	String poll();
}
