package com.qingdao.marathon.system.controller;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.xssf.streaming.SXSSFWorkbook;
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
import com.qingdao.marathon.contants.LoggerConstants;
import com.qingdao.marathon.logger.SysLogger;
import com.qingdao.marathon.system.model.AchievementEntity;
import com.qingdao.marathon.system.model.DrawEntity;
import com.qingdao.marathon.system.model.MatchEntity;
import com.qingdao.marathon.system.model.MatchGroupEntity;
import com.qingdao.marathon.system.model.Operator;
import com.qingdao.marathon.system.service.MatchGroupMngService;
import com.qingdao.marathon.system.service.ReviewMngService;
import com.qingdao.marathon.user.model.Registration;
import com.qingdao.marathon.util.DateUtil;
import com.qingdao.marathon.util.ExcelUtil;
import com.qingdao.marathon.util.FileDownloadUtil;
import com.qingdao.marathon.util.SessionUtils;

@Controller
@RequestMapping("/admin/system/reviewMng")
public class ReviewMngController extends BaseController {
	
	@Resource
	ReviewMngService reviewMngService;
	
	@Resource
	MatchGroupMngService matchGroupMngService;

	@Resource
	SysLogger sysLogger;

	@RequestMapping(value = "/list")
	public ModelAndView toList(HttpServletRequest req, HttpServletResponse resp) {
		// 返回数据类型
		Map<String, Object> context = getRootMap();
		String privilege = req.getParameter("privilege");
		context.put("privilege", privilege);
		
		Map<String,Object> parms = new HashMap<String,Object>();
		parms.put("matchId", "");
		List<MatchGroupEntity> match = matchGroupMngService.queryGroupByParams(parms);
		context.put("matchs", match);
		return forword("system/review/reviewList", context);
	}

	@RequestMapping(value = "/dataList", method = RequestMethod.POST)
	@ResponseBody
	public PageCallBack dataList(Pagination pagination, HttpServletRequest req, HttpServletResponse resp) {
		PageCallBack pcb = new PageCallBack();
		try {
			String groupId = req.getParameter("groupId");
			Map<String,Object> parms = new HashMap<String,Object>();
			parms.put("groupId", groupId);
			Page<DrawEntity> matchPage = reviewMngService.queryDraw(pagination,parms,true);
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
	
	@RequestMapping("/drawNum")
	public void drawNum(HttpServletRequest req, HttpServletResponse resp) {
		try {
			String drawNum = req.getParameter("drawNum");
			String groupId = req.getParameter("groupId");
			//判断组别是否已经抽取过参赛人数
			MatchGroupEntity groupEntity = matchGroupMngService.queryById(groupId);
			if (null != groupEntity.getHasDraw() && !"".equals(groupEntity.getHasDraw()) && !"null".equals(groupEntity.getHasDraw())) {
				sendFailureMessage(resp, "该分组已经抽取过参赛人员，请勿重复抽取");
				return;
			} else {
				//抽取日期时分秒
				String now = DateUtil.getNowPlusTimeMill();
				//参赛号
				DecimalFormat df2=(DecimalFormat) DecimalFormat.getInstance();
				df2.applyPattern("0000");
				List<Registration> registration = reviewMngService.queryById(groupId);
				List<Registration> drawRegistration = new ArrayList<Registration>();
				boolean overFlg = false;
				//报名人数小于抽取人数
				if (registration.size()<=Integer.parseInt(drawNum)) {
					if (registration.size() <= 0) {
						sendFailureMessage(resp, "该分组下暂无报名人员，无法进行抽取");
						return;
					} else {
						int index = 1;
						for(Registration reg :registration) {
							reg.setAuditState("1");
							reg.setCompetitionNo(groupId+df2.format(index).toString());
							reg.setRaceOrderId("BM"+now+df2.format(index).toString());
							index++;
						}
					}
				} else {
					overFlg = true;
					for (int i = 1;i <Integer.parseInt(drawNum)+1;i++) {
						int random = (int) (Math.random() * registration.size()-1);
						Registration reg = registration.get(random);
						reg.setAuditState("1");
						reg.setCompetitionNo(groupId+df2.format(i).toString());
						reg.setRaceOrderId("BM"+now+df2.format(i).toString());
						drawRegistration.add(reg);
						registration.remove(random);
					}
				}
				if (overFlg) {
					reviewMngService.updateRegistrationMatchGroup(drawRegistration,groupId);
				} else {
					reviewMngService.updateRegistrationMatchGroup(registration,groupId);
				}
				sendSuccessMessage(resp, "参赛人员抽取完成");
			}
		} catch (Exception e) {
			e.printStackTrace();
			sysLogger.error(LoggerConstants.SYS_LOGGER, "更新功能模块出错", e);
			sendFailureMessage(resp, "更新出错，请联系技术人员");
		}
	}
	
	@RequestMapping("/updateRegistration")
	public void updateRegistration(MatchEntity matchEntity,HttpServletRequest req, HttpServletResponse resp) {
		try {
			Operator operator = SessionUtils.getOperator(req);
			matchEntity.setOpt(operator.getOptName());

			String value[] = req.getParameter("value").split("&");
			sendSuccessMessage(resp, "更新成功");
		} catch (Exception e) {
			e.printStackTrace();
			sysLogger.error(LoggerConstants.SYS_LOGGER, "更新功能模块出错", e);
			sendFailureMessage(resp, "更新出错，请联系技术人员");
		}
	}
	
	@RequestMapping("/exportReport")
	public void exportReport(HttpServletRequest req,HttpServletResponse resp){
		String[] nameArray = null;
		String[] colArray = null;
		WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();
		ServletContext servletContext = webApplicationContext.getServletContext();
		String filePath = servletContext.getRealPath("/") + "DL/Export/Model.xlsx";
		System.out.println(filePath);
		try {
			String groupId = req.getParameter("groupId");
			Map<String,Object> parms = new HashMap<String,Object>();
			parms.put("groupId", groupId);
			
			List<AchievementEntity> ReportList = matchGroupMngService.queryAchByParams(parms);
			nameArray = new String[]{"赛事ID","赛事名称","组别ID","组别名称","参赛者姓名","参赛者证件号","参赛编号","比赛成绩","比赛名称"};
			colArray = new String[]{"MatchId","MatchName","GroupId","GroupName","Name","IDNumber","CompetitionNo","Achievement","Ranking"};
			int index = 0;//表中行号
			int sheetNum = 1;//工作表号
			String sheetName = "sheet"+sheetNum;//工作表名
			SXSSFWorkbook swb = new SXSSFWorkbook(100);
			ExcelUtil.createExcel(ReportList, nameArray, colArray, filePath, index, sheetName, swb);
			ExcelUtil.writeToExcel(swb, filePath);
			sendSuccessMessage(resp, filePath);
			//FileDownloadUtil.downloadFile(req, resp, new File(filePath));
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
	
	@RequestMapping(value = "/fileUpload")
	public ModelAndView toFileUpload(HttpServletRequest req, HttpServletResponse resp) {
		// 返回数据类型
		Map<String, Object> context = getRootMap();
		String privilege = req.getParameter("privilege");
		context.put("privilege", privilege);
		
		Map<String,Object> parms = new HashMap<String,Object>();
		parms.put("matchId", "");
		List<MatchGroupEntity> match = matchGroupMngService.queryGroupByParams(parms);
		context.put("matchs", match);
		return forword("system/review/uploadFile", context);
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
				String[] colArray = new String[]{"MatchId","MatchName","GroupId","GroupName","Name","IDNumber","CompetitionNo","Achievement","Ranking"};
				List<AchievementEntity> ReportList = ExcelUtil.getCache(filePath,AchievementEntity.class,colArray);
				
				reviewMngService.updateRegistration(ReportList);
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
