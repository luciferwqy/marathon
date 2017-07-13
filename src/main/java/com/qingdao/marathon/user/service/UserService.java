package com.qingdao.marathon.user.service;

import java.util.List;
import java.util.Map;

import com.qingdao.marathon.user.model.MarathonRace;
import com.qingdao.marathon.user.model.MatchGroup;
import com.qingdao.marathon.user.model.MatchInfo;
import com.qingdao.marathon.user.model.OrderInfo;
import com.qingdao.marathon.user.model.PersonalInfo;
import com.qingdao.marathon.user.model.ReceiveInfo;
import com.qingdao.marathon.user.model.Registration;

public interface UserService {
	/**
	 * 注册
	 * @param account
	 * @param pwd
	 * @return
	 */
	public boolean register(String account,String pwd);
	/**
	 * 验证账号是否存在
	 * @param account
	 * @return
	 */
	public PersonalInfo checkAccount(String account);
	/**
	 * 登录
	 */
	public PersonalInfo login(String account,String pwd);
	/**
	 * 更新个人信息
	 * @param info
	 * @return
	 */
	public boolean updateInfo(PersonalInfo info);
	/**
	 * 获取个人信息
	 * @param account
	 * @return
	 */
	public PersonalInfo getUserInfo(String account);
	/**
	 * 增加比赛经历
	 * @param race
	 * @return
	 */
	public boolean addMarathonRace(MarathonRace race);
	/**
	 * 获取比赛列表
	 * @return
	 */
	public List<MatchInfo> queryMatchList(Map<String,Object> parms);
	/**
	 * 获取组别列表
	 * @param matchId
	 * @return
	 */
	public List<MatchGroup> queryByMatchId(String matchId);
	/**
	 * 新增收货地址
	 * @param info
	 */
	public void addReceiveInfo(ReceiveInfo info);
	/**
	 * 查询收货信息
	 * @param account
	 * @return
	 */
	public List<ReceiveInfo> queryByList(String account);
	/**
	 * 新增用户订单
	 * @param info
	 */
	public void addOrder(OrderInfo info);
	/**
	 * 查询订单
	 * @param acount
	 * @return
	 */
	public List<OrderInfo> queryByOrderList(String account);
	/**
	 * 新增报名记录
	 * @param info
	 */
	public void addRegistration(Registration info);
	/**
	 * 查询报名
	 * @param account
	 * @return
	 */
	public List<Registration> queryByRegistrationList(Map<String,Object> parms);
	/**
	 * 根据订单号查询
	 * @param orderId
	 * @return
	 */
	public Registration queryByOrderId(String orderId);
	/**
	 * 判断是否支付成功
	 * @param orderId
	 * @return
	 */
	public boolean ispay(String orderId);
	/**
	 * 更新报名订单号
	 * @param parms
	 */
	public void updateRaceOrderId(Map<String,Object> parms);
	
}
