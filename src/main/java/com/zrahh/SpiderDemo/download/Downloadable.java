package com.zrahh.SpiderDemo.download;

import com.zrahh.SpiderDemo.domain.Page;

public interface Downloadable {
	Page download(String url);

}
