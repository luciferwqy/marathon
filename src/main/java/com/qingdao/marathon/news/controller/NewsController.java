package com.qingdao.marathon.news.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.qingdao.marathon.contants.Constants;
import com.qingdao.marathon.model.ResultModel;
import com.qingdao.marathon.system.model.NewsModel;
import com.qingdao.marathon.system.service.NewsMngService;

@Controller
public class NewsController {

	@Resource
	NewsMngService newsMngService;

	private final Integer pageNum = 20;

	@RequestMapping("/news/getNewsList")
	@ResponseBody
	public ResultModel getNewsList(HttpServletRequest req, HttpServletResponse res) {
		ResultModel model = new ResultModel();
		res.setHeader(Constants.CROSS_DOMAIN, Constants.DOMAIN_NAME);
		try {
			String type = req.getParameter("type");
			String lang = req.getParameter("lang");
			Integer page = Integer.valueOf(req.getParameter("page"));
			Map<String, Object> parms = new HashMap<String, Object>();
			parms.put("type", type);
			parms.put("lang", lang);
			int totalPage = newsMngService.queryCount(parms) % pageNum == 0 ? newsMngService.queryCount(parms) / pageNum
					: newsMngService.queryCount(parms) / pageNum + 1;
			parms.put("start", pageNum * (page - 1));
			parms.put("end", pageNum);
			List<NewsModel> list = newsMngService.queryByList(parms);
			model.setSuccess(true);
			model.setObj(list);
			model.setMsg(totalPage+"");
		} catch (Exception e) {
			e.printStackTrace();
			model.setSuccess(false);
			model.setMsg("网络异常");
		}

		return model;
	}
}
