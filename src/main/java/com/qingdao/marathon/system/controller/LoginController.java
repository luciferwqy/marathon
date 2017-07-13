package com.qingdao.marathon.system.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.qingdao.marathon.annotation.Auth;
import com.qingdao.marathon.base.BaseController;
import com.qingdao.marathon.contants.LoggerConstants;
import com.qingdao.marathon.logger.SysLogger;
import com.qingdao.marathon.system.model.AuthInfo;
import com.qingdao.marathon.system.model.Operator;
import com.qingdao.marathon.system.service.FuncMngService;
import com.qingdao.marathon.system.service.OperatorMngService;
import com.qingdao.marathon.util.MethodUtil;
import com.qingdao.marathon.util.SessionUtils;
import com.qingdao.marathon.util.StringUtil;
import com.qingdao.marathon.util.URLUtils;

@Controller
@RequestMapping("/admin")
public class LoginController extends BaseController {

	@Resource
	OperatorMngService operatorMngService;

	@Resource
	FuncMngService funcMngService;

	@Resource
	SysLogger sysLogger;

	@RequestMapping(value = "/toLogin")
	@Auth(verifyLogin = false, verifyURL = false)
	public ModelAndView toLogin(HttpServletRequest req, HttpServletResponse resp) {
		// 返回数据类型
		Map<String, Object> context = getRootMap();
		return forword("login", context);
	}

	@RequestMapping(value = "/error")
	@Auth(verifyLogin = false, verifyURL = false)
	public ModelAndView error(HttpServletRequest req, HttpServletResponse resp) {
		// 返回数据类型
		Map<String, Object> context = getRootMap();
		context.put("msg", req.getAttribute("msg"));
		return forword("error", context);
	}
	
	@RequestMapping(value = "/logout")
	@Auth(verifyLogin = false, verifyURL = false)
	public void logout(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		// 返回数据类型
		SessionUtils.removeOperator(req);
		SessionUtils.removeMenuList(req);
		resp.sendRedirect(URLUtils.get("marathon") + "/admin/toLogin.shtml");
	}

	/**
	 * 用户登录
	 * 
	 * @param email
	 * @param pwd
	 * @param verifyCode
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/login")
	@Auth(verifyLogin = false, verifyURL = false)
	public void login(String userName, String pwd, String verifyCode, HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 判断验证码是否正确
		if (StringUtil.isEmpty(userName)) {
			sendFailureMessage(response, "账号不能为空.");
			return;
		}
		if (StringUtil.isEmpty(pwd)) {
			sendFailureMessage(response, "密码不能为空.");
			return;
		}

		String msg = "用户登录日志:";
		Operator operator = operatorMngService.queryByLoginInfo(userName, MethodUtil.MD5(pwd));
		if (operator == null) {
			// 记录错误登录日志
			sysLogger.error(LoggerConstants.LOGIN_LOGGER, msg + "[" + userName + "]" + "账号或者密码输入错误.");
			sendFailureMessage(response, "账号或者密码输入错误.");
			return;
		}

		initSession(request, operator);

		sendSuccessMessage(response, "登录成功.");
	}

	private boolean initSession(HttpServletRequest request, Operator operator) {
		// 设置Operator到Session
		SessionUtils.setOperator(request, operator);

		// 设置MenuList到Session
		List<AuthInfo> authInfos = funcMngService.queryFuncByOptId(operator.getOptid());
		List<AuthInfo> menuList = treeAuthInfo(authInfos);

		if (menuList == null) {
			return false;
		}

		SessionUtils.setMenuList(request, menuList);

		return true;

	}

	/**
	 * 首頁
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/main")
	@Auth(verifyLogin = false, verifyURL = false)
	public ModelAndView main(HttpServletRequest req, HttpServletResponse resp, HttpSession session) {
		Map<String, Object> context = getRootMap();

		Operator operator = SessionUtils.getOperator(req);
		if (operator == null) {
			return forword("login", context);
		}

		context.put("menuList", SessionUtils.getMenuList(req));
		context.put("operator", operator);
		return forword("main", context);
	}

	private List<AuthInfo> treeAuthInfo(List<AuthInfo> authInfos) {
		if (authInfos == null || authInfos.size() == 0) {
			return null;
		}
		Map<String, AuthInfo> authCaches = new LinkedHashMap<String, AuthInfo>();
		for (AuthInfo authInfo : authInfos) {
			if (authInfo.getParentId() == null || "".equals(authInfo.getParentId())) {
				authCaches.put(authInfo.getFuncId(), authInfo);
			}
		}

		for (AuthInfo authInfo : authInfos) {
			if (authCaches.containsKey(authInfo.getParentId())) {
				List<AuthInfo> temChild = authCaches.get(authInfo.getParentId()).getChildren();
				if (temChild == null) {
					temChild = new ArrayList<AuthInfo>();
					temChild.add(authInfo);
					authCaches.get(authInfo.getParentId()).setChildren(temChild);
				} else {
					temChild.add(authInfo);
				}
			}
		}

		List<AuthInfo> menuAuth = new ArrayList<AuthInfo>();

		for (Map.Entry<String, AuthInfo> entry : authCaches.entrySet()) {
			menuAuth.add(entry.getValue());
		}

		return menuAuth;
	}
}
