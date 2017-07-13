package com.qingdao.marathon.system.controller;

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
import com.qingdao.marathon.system.model.AuthInfo;
import com.qingdao.marathon.system.model.Operator;
import com.qingdao.marathon.system.model.RoleEntity;
import com.qingdao.marathon.system.service.FuncMngService;
import com.qingdao.marathon.system.service.RoleMngService;
import com.qingdao.marathon.util.JSONUtil;
import com.qingdao.marathon.util.SessionUtils;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("/admin/system/roleMng")
public class RoleMngController extends BaseController{

	@Resource
	SysLogger sysLogger;
	
	@Resource
	RoleMngService roleMngService;
	
	@Resource
	FuncMngService funcMngService;
	
	@RequestMapping("/list")
	public ModelAndView toRoleList(HttpServletRequest req,HttpServletResponse res){
		
		Map<String,Object> context =  getRootMap();
		
		return forword("/system/roleMng/roleList", context);
	}
	
	@RequestMapping("/dataList")
	@ResponseBody
	public PageCallBack dataList(Pagination pagination,HttpServletRequest req,HttpServletResponse res){
		PageCallBack pcb = new PageCallBack();
		try {
			String roleid = req.getParameter("roleid");
			String roleName = req.getParameter("roleName");
			Map<String,Object> parms = new HashMap<String,Object>();
			parms.put("roleName", roleName);
			parms.put("roleid", roleid);
			Page<RoleEntity> rolePage = roleMngService.queryByList(pagination,parms,true);
			pcb.setPagination(webPageConverter(rolePage));
			pcb.setObj(rolePage);
			pcb.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			sysLogger.error(LoggerConstants.SYS_LOGGER, "查询角色模块出错", e);
			pcb.setErrTrace(e.getMessage());
			pcb.setSuccess(false);
		}

		return pcb;
	}
	
	@RequestMapping("/addRole")
	public ModelAndView addRole(Pagination pagination,HttpServletRequest req, HttpServletResponse resp) {
		// 返回数据类型
		Map<String, Object> context = getRootMap();
		return forword("system/roleMng/addRole", context);
	}

	@RequestMapping("/editRole")
	public ModelAndView editRole(Pagination pagination,HttpServletRequest req, HttpServletResponse resp) {
		// 返回数据类型
		Map<String, Object> context = getRootMap();
		try {
			String roleid = req.getParameter("roleid");
			Map<String, Object> parms = new HashMap<String, Object>();
			parms.put("roleid", roleid);
			Page<RoleEntity> rolePage = roleMngService.queryByList(pagination,parms,false);
			if(rolePage == null || rolePage.getResult() == null || rolePage.getResult().size() == 0){
				return forword("/error", context);
			}
			context.put("role", rolePage.getResult().get(0));
			return forword("system/roleMng/editRole", context);
		} catch (Exception e) {
			e.printStackTrace();
			sysLogger.error(LoggerConstants.SYS_LOGGER, "角色模块出错", e);
			return forword("/error", context);
		}
		
	}

	@RequestMapping("/delRole")
	public void delFunc(HttpServletRequest req, HttpServletResponse resp) {

		String roleid = req.getParameter("roleid");

		try {
			roleMngService.delRole(roleid);
			sendSuccessMessage(resp, "删除成功");
		} catch (Exception e) {
			e.printStackTrace();
			sysLogger.error(LoggerConstants.SYS_LOGGER, "删除角色出错", e);
			sendFailureMessage(resp, "删除出错，请联系技术人员");
		}
	}

	@RequestMapping("/updateRole")
	public void updateRole(RoleEntity roleEntity,HttpServletRequest req, HttpServletResponse resp) {

		try {
			Operator operator = SessionUtils.getOperator(req);
			roleEntity.setOpt(operator.getOptName());
			
			roleMngService.updateRole(roleEntity);
			sendSuccessMessage(resp, "更新成功");
		} catch (Exception e) {
			e.printStackTrace();
			sysLogger.error(LoggerConstants.SYS_LOGGER, "更新角色出错", e);
			sendFailureMessage(resp, "更新出错，请联系技术人员");
		}
	}
	
	@RequestMapping("/saveRole")
	public void saveRole(RoleEntity roleEntity,HttpServletRequest req, HttpServletResponse resp) {

		try {
			Operator operator = SessionUtils.getOperator(req);
			roleEntity.setOpt(operator.getOptName());
			
			roleMngService.addRole(roleEntity);
			sendSuccessMessage(resp, "新增成功");
		} catch (Exception e) {
			System.out.println(e.getMessage());
			if(e.getMessage().contains("for key 'roleName'")){
				sendFailureMessage(resp, "角色名称已存在");
			}else{
				e.printStackTrace();
				sysLogger.error(LoggerConstants.SYS_LOGGER, "新增角色出错", e);
				sendFailureMessage(resp, "新增出错，请联系技术人员");
			}
		}
	}
	
	@RequestMapping("/editPriv")
	public ModelAndView editPriv(HttpServletRequest req, HttpServletResponse resp){
		// 返回数据类型
		Map<String, Object> context = getRootMap();
		try {
			String roleid = req.getParameter("roleid");
			List<AuthInfo> authList = funcMngService.queryFuncByRoleId(roleid);
			List<AuthInfo> allAuthList = funcMngService.queryAllFunc();
			for(AuthInfo auth : allAuthList){
				for(AuthInfo roleAuth : authList){
					if(auth.getFuncId().equals(roleAuth.getFuncId())){
						auth.setPrivilege(roleAuth.getPrivilege());
					}
				}
			}
			List<AuthInfo> treeAuth = funcMngService.treeAuthInfo(allAuthList);
			String treeAuthStr = JSONUtil.toJSONString(treeAuth);
			context.put("treeAuth", treeAuth);
			context.put("treeAuthStr", treeAuthStr);
			context.put("roleid", roleid);
			return forword("system/roleMng/privilege", context);
		} catch (Exception e) {
			e.printStackTrace();
			sysLogger.error(LoggerConstants.SYS_LOGGER, "角色模块出错", e);
			return forword("/error", context);
		}
		
	}
	
	@SuppressWarnings({ "static-access", "unchecked", "rawtypes" })
	@RequestMapping("/updatePriv")
	public void updatePriv(HttpServletRequest req, HttpServletResponse resp){
		try {
			String jsonStr = req.getParameter("obj");
			String roleid = req.getParameter("roleid");
			Operator opt = SessionUtils.getOperator(req);
			JSONObject  jasonObject = JSONObject.fromObject(jsonStr);
			Map<String,String> privObj = (Map)jasonObject.toBean(jasonObject,Map.class);
		
			roleMngService.updatePriv(privObj, roleid, opt.getOptName());
			sendSuccessMessage(resp, "");
		} catch (Exception e) {
			e.printStackTrace();
			sysLogger.error(LoggerConstants.SYS_LOGGER, "权限分配出错", e);
			sendFailureMessage(resp, "系统出错，请联系技术");
			
		}
	}
}
