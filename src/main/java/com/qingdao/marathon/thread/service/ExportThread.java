package com.qingdao.marathon.thread.service;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.github.pagehelper.Page;
import com.qingdao.marathon.base.Pagination;
import com.qingdao.marathon.cache.ExportCache;
import com.qingdao.marathon.thread.model.ProgressbarModel;
import com.qingdao.marathon.util.DateUtil;
import com.qingdao.marathon.util.ExcelUtil;
import com.qingdao.marathon.util.SpringContextUtil;

public class ExportThread implements Runnable{

	private Map<String,Object> parms;
	private Class clazz;
	private String fileName;
	private ProgressbarModel model;
	
	public ExportThread(Map<String,Object> parms,Class calzz,String fileName,ProgressbarModel model){
		this.parms = parms;
		this.clazz = calzz;
		this.fileName = fileName;
		this.model = model;
	}
	
	@Override
	public void run() {
		
		try {
			
			Pagination pagination = new Pagination();
			pagination.setCurrentPage(1);//当前批次
			pagination.setNumPerPage(100);//分批数量
			
			String filePath = getFilePath(fileName);
			
			HSSFWorkbook swb = new HSSFWorkbook();
			
			String queryService = (String) parms.get("queryService");
			
			List dataList = getDataList(queryService, parms);
			
			
			//初始化进度条数据
			model.setCurrentNum(0);
			model.setTotal(dataList.size());
			model.setFileName(fileName);
			model.setPath(filePath);
			
			ExcelUtil.createExcel(dataList, clazz, filePath, model , swb);
			
			ExcelUtil.writeToExcel(swb, filePath);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	private String getFilePath(String fileName) throws Exception {
		WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();
		ServletContext servletContext = webApplicationContext.getServletContext();
		String savepath = servletContext.getRealPath("/") + "DL/Export/" + fileName + "-" + DateUtil.getNowLongTime() + ".xls";
		return savepath;
	}
	
	private List getDataList(String queryService, Map filterParams) throws Exception {
		Object obj = SpringContextUtil.getBean(queryService);
		Class serviceClass = obj.getClass();
		Method method = serviceClass.getDeclaredMethod("queryExport", Map.class);
		return (List) method.invoke(obj,filterParams);
	}
	
}
