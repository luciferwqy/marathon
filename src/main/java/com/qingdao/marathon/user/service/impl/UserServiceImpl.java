package com.qingdao.marathon.user.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.qingdao.marathon.contants.LoggerConstants;
import com.qingdao.marathon.logger.SysLogger;
import com.qingdao.marathon.user.mapper.MarathonRaceMapper;
import com.qingdao.marathon.user.mapper.MatchGroupMapper;
import com.qingdao.marathon.user.mapper.MatchMapper;
import com.qingdao.marathon.user.mapper.OrderGoodsMapper;
import com.qingdao.marathon.user.mapper.OrderInfoMapper;
import com.qingdao.marathon.user.mapper.PersonalInfoMapper;
import com.qingdao.marathon.user.mapper.ReceiveInfoMapper;
import com.qingdao.marathon.user.mapper.RegistrationMapper;
import com.qingdao.marathon.user.model.MarathonRace;
import com.qingdao.marathon.user.model.MatchGroup;
import com.qingdao.marathon.user.model.MatchInfo;
import com.qingdao.marathon.user.model.OrderGoods;
import com.qingdao.marathon.user.model.OrderInfo;
import com.qingdao.marathon.user.model.PersonalInfo;
import com.qingdao.marathon.user.model.ReceiveInfo;
import com.qingdao.marathon.user.model.Registration;
import com.qingdao.marathon.user.service.UserService;
import com.qingdao.marathon.util.MethodUtil;

@Service
public class UserServiceImpl implements UserService{

	@Resource
	PersonalInfoMapper personalInfoMapper;
	
	@Resource
	MarathonRaceMapper marathonRaceMapper;
	
	@Resource
	MatchMapper matchMapper;
	
	@Resource
	MatchGroupMapper matchGroupMapper;
	
	@Resource
	ReceiveInfoMapper receiveInfoMapper;
	
	@Resource
	OrderInfoMapper orderInfoMapper;
	
	@Resource
	OrderGoodsMapper orderGoodsMapper;
	
	@Resource
	RegistrationMapper registrationMapper;
	
	@Resource
	SysLogger sysLogger;
	
	private static final String ALREADY_PAY = "1";
	
	@Override
	public boolean register(String account, String pwd) {
		try {
			PersonalInfo info = new PersonalInfo();
			pwd = MethodUtil.MD5(pwd);
			info.setAccount(account);
			info.setPwd(pwd);
			personalInfoMapper.insertSelective(info);
			return true;
		} catch (Exception e) {
			sysLogger.error(LoggerConstants.USER_LOGGER, "注册出错",e);
			return false;
		}
		
	}

	@Override
	public PersonalInfo checkAccount(String account) {
		PersonalInfo info = new PersonalInfo();
		info.setAccount(account);
		return personalInfoMapper.queryByPersonal(info);
	}

	@Override
	public PersonalInfo login(String account, String pwd) {
		PersonalInfo info = new PersonalInfo();
		pwd = MethodUtil.MD5(pwd);
		info.setAccount(account);
		info.setPwd(pwd);
		return personalInfoMapper.queryByPersonal(info);
	}

	@Override
	public boolean updateInfo(PersonalInfo info) {
		try {
			personalInfoMapper.updateByPrimaryKeySelective(info);
			if(info.getRace() != null){
				marathonRaceMapper.addRace(info.getRace());
			}
			return true;
		} catch (Exception e) {
			sysLogger.error(LoggerConstants.USER_LOGGER, "注册出错",e);
			return false;
		}
	}

	@Override
	public boolean addMarathonRace(MarathonRace race) {
		try {
			marathonRaceMapper.addRace(race);
			return true;
		} catch (Exception e) {
			sysLogger.error(LoggerConstants.USER_LOGGER, "新增比赛记录",e);
			return false;
		}
	}

	@Override
	public List<MatchInfo> queryMatchList(Map<String,Object> parms) {
		// TODO Auto-generated method stub
		return matchMapper.queryList(parms);
	}

	@Override
	public List<MatchGroup> queryByMatchId(String matchId) {
		// TODO Auto-generated method stub
		return matchGroupMapper.queryByMatchId(matchId);
	}

	@Override
	public void addReceiveInfo(ReceiveInfo info) {
		// TODO Auto-generated method stub
		receiveInfoMapper.addReceiveInfo(info);
	}

	@Override
	public List<ReceiveInfo> queryByList(String account) {
		// TODO Auto-generated method stub
		return receiveInfoMapper.queryByList(account);
	}

	@Override
	public void addOrder(OrderInfo info) {
		// TODO Auto-generated method stub
		orderInfoMapper.addOrderInfo(info);
		if(info.getGoodsList() != null && info.getGoodsList().size()>0){
			for(OrderGoods goods : info.getGoodsList()){
				orderGoodsMapper.addOrderGoods(goods);
			}
		}
	}

	@Override
	public List<OrderInfo> queryByOrderList(String account) {
		// TODO Auto-generated method stub
		return orderInfoMapper.queryByList(account);
	}

	@Override
	public void addRegistration(Registration info) {
		registrationMapper.addRegistration(info);
		
	}

	@Override
	public List<Registration> queryByRegistrationList(Map<String,Object> parms) {
		// TODO Auto-generated method stub
		return registrationMapper.queryByRegistrationList(parms);
	}

	@Override
	public PersonalInfo getUserInfo(String account) {
		PersonalInfo info = new PersonalInfo();
		info.setAccount(account);
		info = personalInfoMapper.queryByPersonal(info);
		info.setPwd("");
		return info;
	}

	@Override
	public Registration queryByOrderId(String orderId) {
		// TODO Auto-generated method stub
		return registrationMapper.queryByOrderId(orderId);
	}

	@Override
	public boolean ispay(String orderId) {
		if(orderId.startsWith("SP")){
			String payState = orderInfoMapper.queryByOrderId(orderId);
			if(ALREADY_PAY.equals(payState)){
				return true;
			}else{
				return false;
			}
		}else if(orderId.startsWith("BM")){
			Registration r = registrationMapper.queryByOrderId(orderId);
			if(ALREADY_PAY.equals(r.getPayState())){
				return true;
			}else{
				return false;
			}
		}else{
			return false;
		}
	}

	@Override
	public void updateRaceOrderId(Map<String, Object> parms) {
		// TODO Auto-generated method stub
		registrationMapper.updateRaceOrderId(parms);
	}
}
