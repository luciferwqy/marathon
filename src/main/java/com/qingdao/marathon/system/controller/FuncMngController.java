package com.qingdao.marathon.system.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.github.pagehelper.Page;
import com.qingdao.marathon.base.BaseController;
import com.qingdao.marathon.base.PageCallBack;
import com.qingdao.marathon.base.Pagination;
import com.qingdao.marathon.contants.LoggerConstants;
import com.qingdao.marathon.logger.SysLogger;
import com.qingdao.marathon.system.model.FuncEntity;
import com.qingdao.marathon.system.model.Operator;
import com.qingdao.marathon.system.service.FuncMngService;
import com.qingdao.marathon.util.SessionUtils;

@Controller
@RequestMapping("/admin/system/funcMng")
public class FuncMngController extends BaseController {

	@Resource
	FuncMngService funcMngService;

	@Resource
	SysLogger sysLogger;

	@RequestMapping(value = "/list")
	public ModelAndView toFuncList(HttpServletRequest req, HttpServletResponse resp) {
		// 返回数据类型
		Map<String, Object> context = getRootMap();
		return forword("system/funcMng/funcList", context);
	}

	@RequestMapping(value = "/dataList", method = RequestMethod.POST)
	@ResponseBody
	public PageCallBack dataList(Pagination pagination, HttpServletRequest req, HttpServletResponse resp) {
		PageCallBack pcb = new PageCallBack();
		try {
			String funcId = req.getParameter("funcId");
			String name = req.getParameter("name");
			//区分是否是主模块
			String isMain = req.getParameter("isMain");
			Map<String,Object> parms = new HashMap<String,Object>();
			parms.put("isMain", isMain);
			parms.put("funcId", funcId);
			parms.put("name", name);
			Page<FuncEntity> funcPage = funcMngService.queryFunc(pagination,parms,true);
			pcb.setPagination(webPageConverter(funcPage));
			pcb.setObj(funcPage);
			pcb.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			sysLogger.error(LoggerConstants.SYS_LOGGER, "查询功能模块出错", e);
			pcb.setErrTrace(e.getMessage());
			pcb.setSuccess(false);
		}

		return pcb;
	}

	@RequestMapping("/addFunc")
	public ModelAndView addFunc(Pagination pagination,HttpServletRequest req, HttpServletResponse resp) {
		// 返回数据类型
		Map<String, Object> context = getRootMap();
		try {
			//区分是否是主模块
			String isMain = req.getParameter("isMain");
			String funcId = req.getParameter("funcId");
			if("false".equals(isMain)){
				Map<String,Object> parms = new HashMap<String,Object>();
				parms.put("isMain", isMain);
				Page<FuncEntity> funcPage = funcMngService.queryFunc(pagination,parms,false);
				if(funcPage != null && funcPage.getResult() != null){
					context.put("funcList", funcPage.getResult());
				}else{
					return forword("/error", context);
				}
			}
			context.put("isMain", isMain);
			context.put("funcId", funcId);
			return forword("system/funcMng/addFunc", context);
		} catch (Exception e) {
			e.printStackTrace();
			sysLogger.error(LoggerConstants.SYS_LOGGER, "功能模块出错", e);
			return forword("/error", context);
		}
		
	}

	@RequestMapping("/editFunc")
	public ModelAndView editFunc(Pagination pagination,HttpServletRequest req, HttpServletResponse resp) {
		// 返回数据类型
		Map<String, Object> context = getRootMap();
		try {
			String funcId = req.getParameter("funcId");
			//区分是否是主模块
			String isMain = req.getParameter("isMain");
			if("false".equals(isMain)){
				Map<String,Object> parms = new HashMap<String,Object>();
				parms.put("isMain", isMain);
				Page<FuncEntity> funcPage = funcMngService.queryFunc(pagination,parms,false);
				if(funcPage != null && funcPage.getResult() != null){
					context.put("funcList", funcPage.getResult());
				}else{
					return forword("/error", context);
				}
			}
			Map<String, Object> parms = new HashMap<String, Object>();
			parms.put("funcId", funcId);
			FuncEntity funcEntity = funcMngService.queryByParams(parms);
			context.put("funcEntity", funcEntity);
			context.put("isMain", isMain);
			return forword("system/funcMng/editFunc", context);
		} catch (Exception e) {
			e.printStackTrace();
			sysLogger.error(LoggerConstants.SYS_LOGGER, "功能模块出错", e);
			return forword("/error", context);
		}
		
	}

	@RequestMapping("/delFunc")
	public void delFunc(HttpServletRequest req, HttpServletResponse resp) {

		String funcId = req.getParameter("funcId");

		try {
			funcMngService.delMainFunc(funcId);
			sendSuccessMessage(resp, "删除成功");
		} catch (Exception e) {
			e.printStackTrace();
			sysLogger.error(LoggerConstants.SYS_LOGGER, "删除功能模块出错", e);
			sendFailureMessage(resp, "删除出错，请联系技术人员");
		}
	}

	@RequestMapping("/viewDetail")
	public ModelAndView viewDetail(HttpServletRequest req, HttpServletResponse resp) {
		// 返回数据类型
		Map<String, Object> context = getRootMap();
		try {
			String funcId = req.getParameter("funcId");
			String name = "";
			name = new String(req.getParameter("name").getBytes("ISO-8859-1"),"UTF-8");
			
			context.put("funcId", funcId);
			context.put("name", name);
			
			return forword("system/funcMng/funcDetail", context);
		} catch (Exception e) {
			e.printStackTrace();
			sysLogger.error(LoggerConstants.SYS_LOGGER, "功能模块出错", e);
			return forword("/error", context);
		}
	}
	
	@RequestMapping("/updateFunc")
	public void updateFunc(FuncEntity funcEntity,HttpServletRequest req, HttpServletResponse resp) {

		try {
			Operator operator = SessionUtils.getOperator(req);
			funcEntity.setOpt(operator.getOptName());
			
			funcMngService.updateFunc(funcEntity);
			sendSuccessMessage(resp, "更新成功");
		} catch (Exception e) {
			e.printStackTrace();
			sysLogger.error(LoggerConstants.SYS_LOGGER, "更新功能模块出错", e);
			sendFailureMessage(resp, "更新出错，请联系技术人员");
		}
	}
	
	@RequestMapping("/saveFunc")
	public void saveFunc(FuncEntity funcEntity,HttpServletRequest req, HttpServletResponse resp) {

		try {
			Operator operator = SessionUtils.getOperator(req);
			funcEntity.setOpt(operator.getOptName());
			
			funcMngService.addFunc(funcEntity);
			sendSuccessMessage(resp, "新增成功");
		} catch (Exception e) {
			e.printStackTrace();
			sysLogger.error(LoggerConstants.SYS_LOGGER, "新增功能模块出错", e);
			sendFailureMessage(resp, "新增出错，请联系技术人员");
		}
	}
	
}
