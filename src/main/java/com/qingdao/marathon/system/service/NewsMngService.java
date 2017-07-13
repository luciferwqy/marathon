package com.qingdao.marathon.system.service;

import java.util.List;
import java.util.Map;

import com.qingdao.marathon.system.model.NewsModel;

public interface NewsMngService {

	/**
	 * 插入新闻
	 * @param model
	 */
	public void addNews(NewsModel model);
	/**
	 * 获取新闻
	 * @param parms
	 * @return
	 */
	public List<NewsModel> queryByList(Map<String,Object> parms);
	/**
	 * 
	 * @param parms
	 * @return
	 */
	public int queryCount(Map<String,Object> parms);
}
