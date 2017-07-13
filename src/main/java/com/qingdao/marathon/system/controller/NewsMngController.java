package com.qingdao.marathon.system.controller;

import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.qingdao.marathon.base.BaseController;
import com.qingdao.marathon.contants.Constants;
import com.qingdao.marathon.system.model.NewsModel;
import com.qingdao.marathon.system.service.NewsMngService;
import com.qingdao.marathon.util.FTPUtil;

@RequestMapping("/admin/system/newsMng")
@Controller
public class NewsMngController extends BaseController{
	
	private static final String ZH_CN = "cn";
	private static final String EN = "en";
	
	@Resource
	NewsMngService newsMngService;
	
	@RequestMapping("/addNews")
	public ModelAndView toNewsList(HttpServletRequest req,HttpServletResponse res){
		Map<String,Object> context = getRootMap();
		return forword("/system/news/addNews", context);
	}

	@RequestMapping("/saveNews")
	public void saveNews(HttpServletRequest req,HttpServletResponse res){
		String lang = req.getParameter("lang");
		String type = req.getParameter("type");
		String title = req.getParameter("title");
		String context = req.getParameter("context");
		String path = "";
		String fileName = System.currentTimeMillis()+"";
		if(ZH_CN.equals(lang)){
			path = "/htdocs/html/chiness/temp/news/";
		}
		if(EN.equals(lang)){
			path = "/htdocs/html/english/temp/news/";
		}
		try {
			String news = "<p style='text-align:center'><strong><span style='font-size:24px'>"+title+"</span></strong></p><p><br/></p>"+context;
			InputStream input = new ByteArrayInputStream(news.getBytes("utf-8"));
			FTPUtil.uploadFile(path, fileName+".html", input);
		} catch (IOException e) {
			e.printStackTrace();
			sendFailureMessage(res, "网络出现问题");
		}
		NewsModel model = new NewsModel();
		model.setLang(lang);
		model.setFileName(fileName);
		model.setTitle(title);
		model.setType(type);
		newsMngService.addNews(model);
		sendSuccessMessage(res, "保存成功");
	}
}
