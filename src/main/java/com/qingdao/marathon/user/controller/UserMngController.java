package com.qingdao.marathon.user.controller;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.qingdao.marathon.base.BaseController;
import com.qingdao.marathon.cache.CacheMap;
import com.qingdao.marathon.contants.Constants;
import com.qingdao.marathon.model.ResultModel;
import com.qingdao.marathon.user.model.MarathonRace;
import com.qingdao.marathon.user.model.MatchGroup;
import com.qingdao.marathon.user.model.MatchInfo;
import com.qingdao.marathon.user.model.OrderInfo;
import com.qingdao.marathon.user.model.PersonalInfo;
import com.qingdao.marathon.user.model.ReceiveInfo;
import com.qingdao.marathon.user.model.Registration;
import com.qingdao.marathon.user.service.UserService;
import com.qingdao.marathon.util.MarathonUtil;
import com.qingdao.marathon.util.SessionUtils;
import com.qingdao.marathon.util.URLUtils;

@Controller
public class UserMngController extends BaseController{

	@Resource
	UserService userService;
	
	private final String localPath = "upload/raceCertificate/";
	
	@RequestMapping("/user/checkAccount")
	@ResponseBody
	public ResultModel checkAccount(HttpServletRequest req,HttpServletResponse res){
		ResultModel model = new ResultModel();
		res.setHeader(Constants.CROSS_DOMAIN, Constants.DOMAIN_NAME);
		String account = req.getParameter("account");
		PersonalInfo info = userService.checkAccount(account);
		if(info == null){
			model.setSuccess(true);
		}else{
			model.setSuccess(false);
			model.setMsg("用户名已存在");
		}
		return model;
	}
	
	@RequestMapping("/user/register")
	@ResponseBody
	public ResultModel register(HttpServletRequest req,HttpServletResponse res){
		ResultModel model = new ResultModel();
		res.setHeader(Constants.CROSS_DOMAIN, req.getHeader("Origin"));
		res.setHeader(Constants.CREDENTIALS, Constants.BOOLEAN);
		String account = req.getParameter("account");
		String pwd = req.getParameter("pwd");
		String yzm = req.getParameter("yzm");
		String serverYzm = SessionUtils.getValidateCode(req) == null ? "" : SessionUtils.getValidateCode(req);
		if(serverYzm.equalsIgnoreCase(yzm)){
			boolean result = userService.register(account, pwd);
			if(result){
				model.setSuccess(true);
			}else{
				model.setSuccess(false);
				model.setMsg("网络出现异常");
			}
		}else{
			model.setMsg("验证码错误");
			model.setSuccess(false);
		}
		return model;
	}
	
	@RequestMapping("/user/login")
	@ResponseBody
	public ResultModel login(HttpServletRequest req,HttpServletResponse res){
		ResultModel model = new ResultModel();
		res.setHeader(Constants.CROSS_DOMAIN, req.getHeader("Origin"));
		res.setHeader(Constants.CREDENTIALS, Constants.BOOLEAN);
		String account = req.getParameter("account");
		String pwd = req.getParameter("pwd");
		String yzm = req.getParameter("yzm");
		String serverYzm = SessionUtils.getValidateCode(req) == null ? "" : SessionUtils.getValidateCode(req);
		if(serverYzm.equalsIgnoreCase(yzm) || yzm==null){
			PersonalInfo info = userService.login(account, pwd);
			if(info == null){
				model.setSuccess(false);
				model.setMsg("用户名或者密码错误");
			}else{
				model.setSuccess(true);
			}
		}else{
			model.setMsg("验证码错误");
			model.setSuccess(false);
		}
		return model;
	}
	
	@RequestMapping("/user/logout")
	@ResponseBody
	public ResultModel logout(HttpServletRequest req,HttpServletResponse res){
		ResultModel model = new ResultModel();
		res.setHeader(Constants.CROSS_DOMAIN, Constants.DOMAIN_NAME);
		model.setSuccess(true);
		return model;
	}
	
	@RequestMapping("/user/getUserInfo")
	@ResponseBody
	public ResultModel getUserInfo(HttpServletRequest req,HttpServletResponse res){
		ResultModel model = new ResultModel();
		res.setHeader(Constants.CROSS_DOMAIN, Constants.DOMAIN_NAME);
		String account = req.getParameter("account");
		try {
			PersonalInfo info = userService.getUserInfo(account);
			model.setObj(info);
			model.setSuccess(true);
		} catch (Exception e) {
			model.setSuccess(false);
			model.setMsg("网络异常");
			e.printStackTrace();
		}
		return model;
	}
	
	
	@RequestMapping("/user/updateInfo")
	@ResponseBody
	public ResultModel updateInfo(PersonalInfo info,HttpServletRequest req,HttpServletResponse res){
		ResultModel model = new ResultModel();
		res.setHeader(Constants.CROSS_DOMAIN, Constants.DOMAIN_NAME);
		boolean result = userService.updateInfo(info);
		if(result){
			model.setSuccess(true);
		}else{
			model.setSuccess(false);
			model.setMsg("网络异常");
		}
		return model;
	}
	
	
	@RequestMapping(value = "/uploadRaceFile", method = RequestMethod.POST)
	@ResponseBody
	public ResultModel uploadDeclFile(@RequestParam("raceFile") MultipartFile raceFile, HttpServletRequest req, HttpServletResponse res) throws Exception {
		ResultModel model = new ResultModel();
		String account = req.getParameter("account");
		res.setHeader(Constants.CROSS_DOMAIN, Constants.DOMAIN_NAME);
		if (!raceFile.isEmpty()) {
			String fileName = raceFile.getOriginalFilename();
			if (fileName == null || (!fileName.endsWith("png") && (!fileName.endsWith("jpg")))) {
				model.setSuccess(false);
				model.setMsg("请使用PNG或JPG格式");
				return model;
			}
			fileName = System.currentTimeMillis()+".jpg";
			String newPath = localPath + account + "/";

			String filePath = req.getSession().getServletContext().getRealPath("/") + newPath + fileName;
			File newFile = new File(filePath);
			if (!newFile.exists()) {
				newFile.mkdirs();
			}

			// 转存文件
			raceFile.transferTo(new File(filePath));

			MarathonRace race = new MarathonRace();
			race.setAccount(account);
			race.setCertificatePath(URLUtils.get("marathon")+"/"+ newPath + fileName);

			boolean result = userService.addMarathonRace(race);

			if(result){
				model.setSuccess(true);
				return model;
			}else{
				model.setSuccess(false);
				model.setMsg("网络异常");
				return model;
			}
		} else {
			model.setSuccess(false);
			model.setMsg("没有文件信息");
			return model;
		}
	}
	
	@RequestMapping("/user/getMatch")
	@ResponseBody
	public ResultModel getMatch(HttpServletRequest req,HttpServletResponse res){
		ResultModel model = new ResultModel();
		res.setHeader(Constants.CROSS_DOMAIN, Constants.DOMAIN_NAME);
		String type = req.getParameter("type");
		try {
			Map<String,Object> parms = new HashMap<String,Object>();
			parms.put("type", type);
			List<MatchInfo> list = userService.queryMatchList(parms);
			model.setSuccess(true);
			model.setObj(list);
			return model;
		} catch (Exception e) {
			model.setSuccess(false);
			model.setMsg("网络异常");
			e.printStackTrace();
			return model;
		}
	}
	
	@RequestMapping("/user/getGroup")
	@ResponseBody
	public ResultModel getGroup(HttpServletRequest req,HttpServletResponse res){
		ResultModel model = new ResultModel();
		res.setHeader(Constants.CROSS_DOMAIN, Constants.DOMAIN_NAME);
		String matchId = req.getParameter("matchId");
		try {
			List<MatchGroup> list = userService.queryByMatchId(matchId);
			model.setSuccess(true);
			model.setObj(list);
			return model;
		} catch (Exception e) {
			model.setSuccess(false);
			model.setMsg("网络异常");
			e.printStackTrace();
			return model;
		}
	}
	
	@RequestMapping("/user/addReceive")
	@ResponseBody
	public ResultModel addReceive(ReceiveInfo info,HttpServletRequest req,HttpServletResponse res){
		ResultModel model = new ResultModel();
		res.setHeader(Constants.CROSS_DOMAIN, Constants.DOMAIN_NAME);
		try {
			userService.addReceiveInfo(info);
			model.setSuccess(true);
			model.setObj(info);
		} catch (Exception e) {
			model.setSuccess(false);
			model.setMsg("网络异常");
			e.printStackTrace();
		}
		return model;
	}
	
	@RequestMapping("/user/getReceive")
	@ResponseBody
	public ResultModel getReceive(HttpServletRequest req,HttpServletResponse res){
		ResultModel model = new ResultModel();
		res.setHeader(Constants.CROSS_DOMAIN, Constants.DOMAIN_NAME);
		String account = req.getParameter("account");
		try {
			List<ReceiveInfo> list = userService.queryByList(account);
			model.setSuccess(true);
			model.setObj(list);
		} catch (Exception e) {
			model.setSuccess(false);
			model.setMsg("网络异常");
			e.printStackTrace();
		}
		return model;
	}
	
	@RequestMapping("/user/addOrder")
	@ResponseBody
	public ResultModel addOrder(OrderInfo info,HttpServletRequest req,HttpServletResponse res){
		ResultModel model = new ResultModel();
		res.setHeader(Constants.CROSS_DOMAIN, Constants.DOMAIN_NAME);
		String orderId = "SP"+System.currentTimeMillis();
		info.setOrderId(orderId);
		try {
			userService.addOrder(info);
			model.setSuccess(true);
			model.setObj(info);
		} catch (Exception e) {
			model.setSuccess(false);
			model.setMsg("网络异常");
			e.printStackTrace();
		}
		return model;
	}
	
	@RequestMapping("/user/getOrder")
	@ResponseBody
	public ResultModel getOrder(HttpServletRequest req,HttpServletResponse res){
		ResultModel model = new ResultModel();
		res.setHeader(Constants.CROSS_DOMAIN, Constants.DOMAIN_NAME);
		String account = req.getParameter("account");
		try {
			List<OrderInfo> list = userService.queryByOrderList(account);
			model.setSuccess(true);
			model.setObj(list);
		} catch (Exception e) {
			model.setSuccess(false);
			model.setMsg("网络异常");
			e.printStackTrace();
		}
		return model;
	}
	
	@RequestMapping("/user/isPay")
	@ResponseBody
	public ResultModel isPay(HttpServletRequest req,HttpServletResponse res){
		ResultModel model = new ResultModel();
		res.setHeader(Constants.CROSS_DOMAIN, Constants.DOMAIN_NAME);
		String orderId = req.getParameter("orderId");
		try {
			if(orderId == null){
				model.setMsg("没有订单号");
				model.setSuccess(false);
				return model;
			}else {
				boolean flag = userService.ispay(orderId);
				model.setSuccess(flag);
			}
		} catch (Exception e) {
			model.setSuccess(false);
			model.setMsg("网络异常");
			e.printStackTrace();
		}
		return model;
	}
	
	@RequestMapping("/user/getRegistration")
	@ResponseBody
	public ResultModel getRegistration(HttpServletRequest req,HttpServletResponse res){
		ResultModel model = new ResultModel();
		res.setHeader(Constants.CROSS_DOMAIN, Constants.DOMAIN_NAME);
		String account = req.getParameter("account");
		String matchId = req.getParameter("matchId");
		String competitionNo = req.getParameter("competitionNo");
		Map<String,Object> parms = new HashMap<String,Object>();
		parms.put("account", account);
		parms.put("matchId", matchId);
		parms.put("competitionNo", competitionNo);
		try {
			List<Registration> list = userService.queryByRegistrationList(parms);
			model.setSuccess(true);
			model.setObj(list);
		} catch (Exception e) {
			model.setSuccess(false);
			model.setMsg("网络异常");
			e.printStackTrace();
		}
		return model;
	}
	
	@RequestMapping("/user/addRegistration")
	@ResponseBody
	public ResultModel addRegistration(Registration info,HttpServletRequest req,HttpServletResponse res){
		ResultModel model = new ResultModel();
		res.setHeader(Constants.CROSS_DOMAIN, Constants.DOMAIN_NAME);
		try {
			PersonalInfo personal = userService.getUserInfo(info.getAccount());
			boolean flag = MarathonUtil.checkObjFieldIsNull(personal);
			if(flag){
				model.setSuccess(false);
				model.setMsg("请先把个人信息全部完善，谢谢");
				return model;
			}
			userService.addRegistration(info);
			model.setSuccess(true);
			model.setObj(info);
		} catch (Exception e) {
			if(e.getMessage().contains("PRIMARY")){
				model.setSuccess(false);
				model.setMsg("已经报名该赛事");
			}else{
				model.setSuccess(false);
				model.setMsg("网络异常");
				e.printStackTrace();
			}
		}
		return model;
	}
}
