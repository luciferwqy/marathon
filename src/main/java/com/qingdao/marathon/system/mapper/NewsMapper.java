package com.qingdao.marathon.system.mapper;

import java.util.List;
import java.util.Map;

import com.qingdao.marathon.system.model.NewsModel;

/**
 * 
 * @author wqy
 * @datetime 2016年7月27日
 * @func
 */
public interface NewsMapper {
	
	public void add(NewsModel model);
	
	public List<NewsModel> queryByList(Map<String,Object> parms);
	
	public int queryCount(Map<String,Object> parms);

}
