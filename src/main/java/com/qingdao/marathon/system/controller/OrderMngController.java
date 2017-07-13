package com.qingdao.marathon.system.controller;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.github.pagehelper.Page;
import com.qingdao.marathon.base.BaseController;
import com.qingdao.marathon.base.PageCallBack;
import com.qingdao.marathon.base.Pagination;
import com.qingdao.marathon.cache.ExportCache;
import com.qingdao.marathon.contants.LoggerConstants;
import com.qingdao.marathon.logger.SysLogger;
import com.qingdao.marathon.model.ResultModel;
import com.qingdao.marathon.system.model.AchievementEntity;
import com.qingdao.marathon.thread.model.ProgressbarModel;
import com.qingdao.marathon.thread.service.ExportThread;
import com.qingdao.marathon.user.model.OrderExportEntity;
import com.qingdao.marathon.user.model.OrderGoods;
import com.qingdao.marathon.user.model.OrderInfo;
import com.qingdao.marathon.user.service.OrderService;
import com.qingdao.marathon.util.DateUtil;
import com.qingdao.marathon.util.ExcelUtil;
import com.qingdao.marathon.util.FileDownloadUtil;

@Controller
@RequestMapping("/admin/system/orderMng")
public class OrderMngController extends BaseController{
	
	@Resource
	SysLogger sysLogger;
	
	@Resource
	OrderService orderService;
	
	@Resource
	ThreadPoolTaskExecutor taskExecutor;

	@RequestMapping("/list")
	public ModelAndView orderList(HttpServletRequest req,HttpServletResponse res){
		Map<String,Object> context = getRootMap();
		return forword("system/order/orderList", context);
	}
	
	@RequestMapping("/dataList")
	@ResponseBody
	public PageCallBack dataList(Pagination pagination,HttpServletRequest req,HttpServletResponse res){
		PageCallBack pcb = new PageCallBack();
		try {
			String state = req.getParameter("state");
			String orderId = req.getParameter("orderId");
			String receiverProvince = req.getParameter("receiverProvince");
			String receiverCity = req.getParameter("receiverCity");
			String receiverArea = req.getParameter("receiverArea");
			Map<String,Object> parms = new HashMap<String,Object>();
			parms.put("orderId", orderId);
			parms.put("state", state);
			parms.put("receiverProvince", receiverProvince);
			parms.put("receiverCity", receiverCity);
			parms.put("receiverArea", receiverArea);
			Page<OrderInfo> orderPage = orderService.queryList(pagination, parms, true);
			pcb.setPagination(webPageConverter(orderPage));
			pcb.setObj(orderPage);
			pcb.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			sysLogger.error(LoggerConstants.SYS_LOGGER, "查询功能模块出错", e);
			pcb.setErrTrace(e.getMessage());
			pcb.setSuccess(false);
		}

		return pcb;
	}
	
	
	@RequestMapping("/showDetail")
	public ModelAndView showDetail(HttpServletRequest req,HttpServletResponse res) throws UnsupportedEncodingException{
		Map<String,Object> context = getRootMap();
		String orderId = req.getParameter("orderId");
		String expressId = req.getParameter("expressId");
		String carrierName = URLDecoder.decode(req.getParameter("carrierName") == null ? "" : req.getParameter("carrierName"), "utf-8");
		context.put("expressId", expressId);
		context.put("carrierName", carrierName);
		context.put("orderId", orderId);
		return forword("system/order/orderDetail", context);
	}
	
	@RequestMapping("/goodsList")
	@ResponseBody
	public PageCallBack goodsList(Pagination pagination,HttpServletRequest req,HttpServletResponse res){
		PageCallBack pcb = new PageCallBack();
		try {
			String orderId = req.getParameter("orderId");
			Map<String,Object> parms = new HashMap<String,Object>();
			parms.put("orderId", orderId);
			Page<OrderGoods> goodsPage = orderService.queryGoodsList(pagination, parms, true);
			pcb.setPagination(webPageConverter(goodsPage));
			pcb.setObj(goodsPage);
			pcb.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			sysLogger.error(LoggerConstants.SYS_LOGGER, "查询功能模块出错", e);
			pcb.setErrTrace(e.getMessage());
			pcb.setSuccess(false);
		}

		return pcb;
	}
	
	@RequestMapping("/test")
	public void exportOrder(Pagination pagination,HttpServletRequest req,HttpServletResponse res){
		String state = req.getParameter("state");
		String receiverProvince = req.getParameter("receiverProvince");
		String receiverCity = req.getParameter("receiverCity");
		String receiverArea = req.getParameter("receiverArea");
		String fileName = req.getParameter("fileName");
		String queryService = req.getParameter("queryService");
		Map<String,Object> parms = new HashMap<String,Object>();
		parms.put("state", state);
		parms.put("receiverProvince", receiverProvince);
		parms.put("receiverCity", receiverCity);
		parms.put("receiverArea", receiverArea);
		parms.put("queryService", "orderServiceImpl");
		ProgressbarModel model = new ProgressbarModel();
		model.setCurrentNum(0);
		model.setId(ExportCache.getInstance().getId());
		model.setTotal(1);
		model.setFileName(fileName);
		boolean flag = ExportCache.getInstance().put(model);
		if(!flag){
			sendFailureMessage(res, "下载进程太多");
			return;
		}
		taskExecutor.execute(new ExportThread(parms, OrderInfo.class , fileName,model));
		sendSuccessMessage(res, "");
	}
	
	@RequestMapping("/getGrogressbar")
	@ResponseBody
	public ResultModel getGrogressbar(HttpServletRequest req,HttpServletResponse res){
		ResultModel model = new ResultModel();
		model.setObj(ExportCache.getInstance().getProgressbarSet());
		return model;
	}
	
	@RequestMapping("/toGrogressbar")
	public ModelAndView toGrogressbar(HttpServletRequest req,HttpServletResponse res){
		Map<String,Object> context = getRootMap();
		return forword("progressbar", context);
	}
	
	@RequestMapping("/exportReport")
	public void exportReport(HttpServletRequest req,HttpServletResponse resp){
		String[] nameArray = null;
		String[] colArray = null;
		WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();
		ServletContext servletContext = webApplicationContext.getServletContext();
		String filePath = servletContext.getRealPath("/") + "DL/Export/OrderModel.xlsx";
		System.out.println(filePath);
		try {
			String state = req.getParameter("state");
			Map<String,Object> parms = new HashMap<String,Object>();
			parms.put("state", state);
			
			List<OrderExportEntity> ReportList = orderService.queryOrderByParams(parms);
			nameArray = new String[]{"订单编号","购买人账号","商品名称","商品尺寸","商品颜色","购买数量","收货人","联系电话","省","市","区","详细地址","快递公司","快递单号"};
			colArray = new String[]{"Orderid","Account","GoodsName","Size","Color","Quantity","ReceiverName","ReceiverPhone","ReceiverProvince","ReceiverCity","ReceiverArea","ReceiverAddress","CarrierName","ExpressId"};
			int index = 0;//表中行号
			int sheetNum = 1;//工作表号
			String sheetName = "sheet"+sheetNum;//工作表名
			SXSSFWorkbook swb = new SXSSFWorkbook(100);
			ExcelUtil.createExcel(ReportList, nameArray, colArray, filePath, index, sheetName, swb);
			ExcelUtil.writeToExcel(swb, filePath);
			sendSuccessMessage(resp, filePath);
		} catch (Exception e) {
			e.printStackTrace();
			sysLogger.error(LoggerConstants.SYS_LOGGER, "导出报表出错", e);
		}
	}
	
	@RequestMapping(value = "/fileDownLoad")
	public void fileDownLoad(HttpServletRequest req, HttpServletResponse resp) {
		String filePath = req.getParameter("filePath");
		try {
			FileDownloadUtil.downloadFile(req, resp, new File(filePath));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
	public void uploadDeclFile(@RequestParam("declFile") MultipartFile declFile, HttpServletRequest req, HttpServletResponse resp) throws Exception {

		try {
			if (!declFile.isEmpty()) {
				String fileName = declFile.getOriginalFilename();
				if (fileName == null) {
					sendFailureMessage(resp, "未接收到上传文件信息");
					return;
				}

				String newPath = "UL/Upload/" + DateUtil.getNowShortDate() + "/";

				String filePath = req.getSession().getServletContext().getRealPath("/") + newPath + fileName;
				System.out.println(filePath);
				File newFile = new File(filePath);
				if (!newFile.exists()) {
					newFile.mkdirs();
				}

				// 转存文件
				declFile.transferTo(new File(filePath));
				String[] colArray = new String[]{"Orderid","Account","GoodsName","Size","Color","Quantity","ReceiverName","ReceiverPhone","ReceiverProvince","ReceiverCity","ReceiverArea","ReceiverAddress","CarrierName","ExpressId"};
				List<OrderExportEntity> ReportList = ExcelUtil.getCache(filePath,OrderExportEntity.class,colArray);
				
				orderService.updateOrderState(ReportList);
				sendSuccessMessage(resp, "文件上传成功");
			} else {
				sendFailureMessage(resp, "没有文件信息！");
			}
		} catch (Exception e) {
			e.printStackTrace();
			sysLogger.error(LoggerConstants.SYS_LOGGER, "导入报表出错", e);
		}
	}
}
