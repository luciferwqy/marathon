package com.qingdao.marathon.system.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.qingdao.marathon.system.mapper.NewsMapper;
import com.qingdao.marathon.system.model.NewsModel;
import com.qingdao.marathon.system.service.NewsMngService;


@Service
public class NewsMngServiceImpl implements NewsMngService {

	@Resource
	NewsMapper newsMapper;
	
	@Override
	public void addNews(NewsModel model) {
		// TODO Auto-generated method stub
		newsMapper.add(model);
	}

	@Override
	public List<NewsModel> queryByList(Map<String, Object> parms) {
		
		return newsMapper.queryByList(parms);
	}

	@Override
	public int queryCount(Map<String, Object> parms) {
		// TODO Auto-generated method stub
		return newsMapper.queryCount(parms);
	}

}
