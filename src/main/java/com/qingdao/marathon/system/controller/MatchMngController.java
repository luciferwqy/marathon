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
import com.qingdao.marathon.system.model.MatchEntity;
import com.qingdao.marathon.system.model.Operator;
import com.qingdao.marathon.system.service.MatchMngService;
import com.qingdao.marathon.util.SessionUtils;

@Controller
@RequestMapping("/admin/system/matchMng")
public class MatchMngController extends BaseController {
	
	@Resource
	MatchMngService matchMngService;

	@Resource
	SysLogger sysLogger;

	@RequestMapping(value = "/list")
	public ModelAndView toMatchList(HttpServletRequest req, HttpServletResponse resp) {
		// 返回数据类型
		Map<String, Object> context = getRootMap();
		String privilege = req.getParameter("privilege");
		context.put("privilege", privilege);
		return forword("system/match/matchList", context);
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
			Page<MatchEntity> matchPage = matchMngService.queryMatch(pagination,parms,true);
			pcb.setPagination(webPageConverter(matchPage));
			pcb.setObj(matchPage);
			pcb.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			sysLogger.error(LoggerConstants.SYS_LOGGER, "查询功能模块出错", e);
			pcb.setErrTrace(e.getMessage());
			pcb.setSuccess(false);
		}

		return pcb;
	}

	@RequestMapping(value = "/addMatch")
	public ModelAndView addMatch(Pagination pagination,HttpServletRequest req, HttpServletResponse resp) {
		// 返回数据类型
		Map<String, Object> context = getRootMap();
		return forword("system/match/addMatch", context);
	}

	@RequestMapping("/editMatch")
	public ModelAndView editMatch(Pagination pagination,HttpServletRequest req, HttpServletResponse resp) {
		// 返回数据类型
		Map<String, Object> context = getRootMap();
		try {
			String matchId = req.getParameter("matchId");
			//区分是否是主模块
			MatchEntity matchEntity = matchMngService.queryById(matchId);
			context.put("matchEntity", matchEntity);
			return forword("system/match/editMatch", context);
		} catch (Exception e) {
			e.printStackTrace();
			sysLogger.error(LoggerConstants.SYS_LOGGER, "功能模块出错", e);
			return forword("/error", context);
		}
		
	}

	@RequestMapping("/delMatch")
	public void delMatch(HttpServletRequest req, HttpServletResponse resp) {

		String matchId = req.getParameter("matchId");

		try {
			matchMngService.delMainFunc(matchId);
			sendSuccessMessage(resp, "删除成功");
		} catch (Exception e) {
			e.printStackTrace();
			sysLogger.error(LoggerConstants.SYS_LOGGER, "删除功能模块出错", e);
			sendFailureMessage(resp, "删除出错，请联系技术人员");
		}
	}
//
//	@RequestMapping("/viewDetail")
//	public ModelAndView viewDetail(HttpServletRequest req, HttpServletResponse resp) {
//		// 返回数据类型
//		Map<String, Object> context = getRootMap();
//		try {
//			String funcId = req.getParameter("funcId");
//			String name = "";
//			name = new String(req.getParameter("name").getBytes("ISO-8859-1"),"UTF-8");
//			
//			context.put("funcId", funcId);
//			context.put("name", name);
//			
//			return forword("system/funcMng/funcDetail", context);
//		} catch (Exception e) {
//			e.printStackTrace();
//			sysLogger.error(LoggerConstants.SYS_LOGGER, "功能模块出错", e);
//			return forword("/error", context);
//		}
//	}
	
	@RequestMapping("/updateMatch")
	public void updateMatch(MatchEntity matchEntity,HttpServletRequest req, HttpServletResponse resp) {

		try {
			Operator operator = SessionUtils.getOperator(req);
			matchEntity.setOpt(operator.getOptName());
			
			matchMngService.updateMatch(matchEntity);
			sendSuccessMessage(resp, "更新成功");
		} catch (Exception e) {
			e.printStackTrace();
			sysLogger.error(LoggerConstants.SYS_LOGGER, "更新功能模块出错", e);
			sendFailureMessage(resp, "更新出错，请联系技术人员");
		}
	}
	
	@RequestMapping("/saveMatch")
	public void saveMatch(MatchEntity matchEntity,HttpServletRequest req, HttpServletResponse resp) {

		try {
			Operator operator = SessionUtils.getOperator(req);
			matchEntity.setOpt(operator.getOptName());
			
			matchMngService.addMatch(matchEntity);
			sendSuccessMessage(resp, "新增成功");
		} catch (Exception e) {
			e.printStackTrace();
			sysLogger.error(LoggerConstants.SYS_LOGGER, "新增功能模块出错", e);
			sendFailureMessage(resp, "新增出错，请联系技术人员");
		}
	}
	
}
