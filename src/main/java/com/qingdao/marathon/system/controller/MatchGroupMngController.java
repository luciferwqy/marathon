package com.qingdao.marathon.system.controller;

import java.util.HashMap;
import java.util.List;
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
import com.qingdao.marathon.system.model.MatchGroupEntity;
import com.qingdao.marathon.system.model.Operator;
import com.qingdao.marathon.system.service.MatchGroupMngService;
import com.qingdao.marathon.util.SessionUtils;

@Controller
@RequestMapping("/admin/system/matchGroupMng")
public class MatchGroupMngController extends BaseController {
	
	@Resource
	MatchGroupMngService matchGroupMngService;

	@Resource
	SysLogger sysLogger;

	@RequestMapping(value = "/list")
	public ModelAndView toMatchGroupList(HttpServletRequest req, HttpServletResponse resp) {
		// 返回数据类型
		Map<String, Object> context = getRootMap();
		String privilege = req.getParameter("privilege");
		context.put("privilege", privilege);
		return forword("system/matchGroup/matchGroupList", context);
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
			Page<MatchGroupEntity> matchPage = matchGroupMngService.queryMatchGroup(pagination,parms,true);
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

	@RequestMapping(value = "/addMatchGroup")
	public ModelAndView addMatchGroup(Pagination pagination,HttpServletRequest req, HttpServletResponse resp) {
		// 返回数据类型
		Map<String, Object> context = getRootMap();
		Map<String,Object> parms = new HashMap<String,Object>();
		parms.put("matchId", "");
		List<MatchEntity> match = matchGroupMngService.queryByParams(parms);
		context.put("matchs", match);
		return forword("system/matchGroup/addMatchGroup", context);
	}

	@RequestMapping("/editGroup")
	public ModelAndView editGroup(Pagination pagination,HttpServletRequest req, HttpServletResponse resp) {
		// 返回数据类型
		Map<String, Object> context = getRootMap();
		try {
			String groupId = req.getParameter("groupId");
			//区分是否是主模块
			MatchGroupEntity groupEntity = matchGroupMngService.queryById(groupId);
			context.put("groupEntity", groupEntity);
			return forword("system/matchGroup/editMatchGroup", context);
		} catch (Exception e) {
			e.printStackTrace();
			sysLogger.error(LoggerConstants.SYS_LOGGER, "功能模块出错", e);
			return forword("/error", context);
		}
		
	}

	@RequestMapping("/delGroup")
	public void delGroup(HttpServletRequest req, HttpServletResponse resp) {
		String groupId = req.getParameter("groupId");
		try {
			matchGroupMngService.delMainFunc(groupId);
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
	
	@RequestMapping("/updateMatchGroup")
	public void updateMatchGroup(MatchGroupEntity matchGroupEntity,HttpServletRequest req, HttpServletResponse resp) {

		try {
			Operator operator = SessionUtils.getOperator(req);
			matchGroupEntity.setOpt(operator.getOptName());
			
			matchGroupMngService.updateMatchGroup(matchGroupEntity);
			sendSuccessMessage(resp, "更新成功");
		} catch (Exception e) {
			e.printStackTrace();
			sysLogger.error(LoggerConstants.SYS_LOGGER, "更新功能模块出错", e);
			sendFailureMessage(resp, "更新出错，请联系技术人员");
		}
	}
	
	@RequestMapping("/saveMatchGroup")
	public void saveMatchGroup(MatchGroupEntity matchGroupEntity,HttpServletRequest req, HttpServletResponse resp) {

		try {
			Operator operator = SessionUtils.getOperator(req);
			matchGroupEntity.setOpt(operator.getOptName());
			
			matchGroupMngService.addMatchGroup(matchGroupEntity);
			sendSuccessMessage(resp, "新增成功");
		} catch (Exception e) {
			e.printStackTrace();
			sysLogger.error(LoggerConstants.SYS_LOGGER, "新增功能模块出错", e);
			sendFailureMessage(resp, "新增出错，请联系技术人员");
		}
	}
	
}
