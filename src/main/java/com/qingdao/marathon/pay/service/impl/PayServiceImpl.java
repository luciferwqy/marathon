package com.qingdao.marathon.pay.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.qingdao.marathon.pay.service.PayService;
import com.qingdao.marathon.user.mapper.OrderInfoMapper;
import com.qingdao.marathon.user.mapper.RegistrationMapper;

@Service
public class PayServiceImpl implements PayService{

	@Resource
	RegistrationMapper registrationMapper;
	
	@Resource
	OrderInfoMapper orderInfoMapper;
	
	@Override
	public void updateRegPay(String orderId) {
		// TODO Auto-generated method stub
		registrationMapper.update(orderId);
	}

	@Override
	public void updateOrderPay(String orderId) {
		// TODO Auto-generated method stub
		orderInfoMapper.update(orderId);
	}

}
