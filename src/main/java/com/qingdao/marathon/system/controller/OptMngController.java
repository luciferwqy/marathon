package com.qingdao.marathon.system.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.github.pagehelper.Page;
import com.qingdao.marathon.base.BaseController;
import com.qingdao.marathon.base.PageCallBack;
import com.qingdao.marathon.base.Pagination;
import com.qingdao.marathon.contants.LoggerConstants;
import com.qingdao.marathon.logger.SysLogger;
import com.qingdao.marathon.system.model.Operator;
import com.qingdao.marathon.system.model.RoleEntity;
import com.qingdao.marathon.system.service.OperatorMngService;
import com.qingdao.marathon.system.service.RoleMngService;
import com.qingdao.marathon.util.MethodUtil;
import com.qingdao.marathon.util.SessionUtils;

@Controller
@RequestMapping("/admin/system/optMng")
public class OptMngController extends BaseController{

	@Resource
	SysLogger sysLogger;
	
	@Resource
	OperatorMngService operatorMngService;
	
	@Resource
	RoleMngService roleMngService;
	
	@RequestMapping("/list")
	public ModelAndView toOptList(HttpServletRequest req,HttpServletResponse res){
		
		Map<String,Object> context =  getRootMap();
		
		return forword("/system/optMng/optList", context);
	}
	
	@RequestMapping("/dataList")
	@ResponseBody
	public PageCallBack dataList(Pagination pagination,HttpServletRequest req,HttpServletResponse res){
		PageCallBack pcb = new PageCallBack();
		try {
			String badge = req.getParameter("badge");
			String optName = req.getParameter("optName");
			Map<String,Object> parms = new HashMap<String,Object>();
			parms.put("badge", badge);
			parms.put("optName", optName);
			Page<Operator> optPage = operatorMngService.queryByList(pagination,parms,true);
			pcb.setPagination(webPageConverter(optPage));
			pcb.setObj(optPage);
			pcb.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			sysLogger.error(LoggerConstants.SYS_LOGGER, "查询员工模块出错", e);
			pcb.setErrTrace(e.getMessage());
			pcb.setSuccess(false);
		}

		return pcb;
	}
	
	
	@RequestMapping("/addOpt")
	public ModelAndView addOpt(Pagination pagination,HttpServletRequest req, HttpServletResponse resp) {
		// 返回数据类型
		Map<String, Object> context = getRootMap();
		try {
			Map<String,Object> parms = new HashMap<String,Object>();
			parms.put("roleState", "1");
			parms.put("noSearchSysAdmin", "yes");
			Page<RoleEntity> rolePage = roleMngService.queryByList(pagination,parms,false);
			if(rolePage == null || rolePage.getResult() == null){
				List<RoleEntity> roleList = new ArrayList<RoleEntity>();
				context.put("roleList", roleList);
			}else{
				context.put("roleList", rolePage.getResult());
			}
			return forword("system/optMng/addOpt", context);
		} catch (Exception e) {
			e.printStackTrace();
			sysLogger.error(LoggerConstants.SYS_LOGGER, "员工模块出错", e);
			return forword("/error", context);
		}
		
	}

	@RequestMapping("/editOpt")
	public ModelAndView editOpt(Pagination pagination,HttpServletRequest req, HttpServletResponse resp) {
		// 返回数据类型
		Map<String, Object> context = getRootMap();
		try {
			String optid = req.getParameter("optid");
			Map<String, Object> parms = new HashMap<String, Object>();
			parms.put("roleState", "1");
			parms.put("noSearchSysAdmin", "yes");
			Page<RoleEntity> rolePage = roleMngService.queryByList(pagination,parms,false);
			if(rolePage == null || rolePage.getResult() == null){
				List<RoleEntity> roleList = new ArrayList<RoleEntity>();
				context.put("roleList", roleList);
			}else{
				context.put("roleList", rolePage.getResult());
			}
			parms.clear();
			parms.put("optid", optid);
			Page<Operator> optPage = operatorMngService.queryByList(pagination,parms,false);
			if(optPage == null || optPage.getResult() == null || optPage.getResult().size()==0){
				return forword("/error", context);
			}
			context.put("operator", optPage.getResult().get(0));
			return forword("system/optMng/editOpt", context);
		} catch (Exception e) {
			e.printStackTrace();
			sysLogger.error(LoggerConstants.SYS_LOGGER, "员工模块出错", e);
			return forword("/error", context);
		}
		
	}

	
	@RequestMapping("/updateOpt")
	public void updateOpt(Operator operator,HttpServletRequest req, HttpServletResponse resp) {

		try {
			Operator opt = SessionUtils.getOperator(req);
			operator.setOpt(opt.getOptName());
			
			operatorMngService.updateOptBindRole(operator);
			sendSuccessMessage(resp, "更新成功");
		} catch (Exception e) {
			e.printStackTrace();
			sysLogger.error(LoggerConstants.SYS_LOGGER, "更新员工出错", e);
			sendFailureMessage(resp, "更新出错，请联系技术人员");
		}
	}
	
	@RequestMapping("/saveOpt")
	public void saveOpt(Operator operator,HttpServletRequest req, HttpServletResponse resp) {

		try {
			Operator opt = SessionUtils.getOperator(req);
			operator.setOpt(opt.getOptName());
			operator.setPassword(MethodUtil.MD5(operator.getPassword()));
			operatorMngService.addOptBindRole(operator);
			sendSuccessMessage(resp, "新增成功");
		} catch (Exception e) {
			e.printStackTrace();
			sysLogger.error(LoggerConstants.SYS_LOGGER, "新增员工出错", e);
			sendFailureMessage(resp, "新增出错，请联系技术人员");
		}
	}
	
	@RequestMapping("/checkExist")
	public void checkExist(HttpServletRequest req, HttpServletResponse resp){
		try {
			String badge = req.getParameter("badge");
			
			Operator opt = operatorMngService.selectBadge(badge);
			
			if(opt != null){
				sendFailureMessage(resp, "该工号已经存在");
			} else {
				sendSuccessMessage(resp, "");
			}
		} catch (Exception e) {
			e.printStackTrace();
			sysLogger.error(LoggerConstants.SYS_LOGGER, "新增员工出错", e);
			sendFailureMessage(resp, "新增出错，请联系技术人员");
		}
		
	}
	
}
