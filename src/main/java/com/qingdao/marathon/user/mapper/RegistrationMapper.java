package com.qingdao.marathon.user.mapper;

import java.util.List;
import java.util.Map;

import com.qingdao.marathon.user.model.Registration;

public interface RegistrationMapper {
	
	public void addRegistration(Registration info);
	
	public List<Registration> queryByRegistrationList(Map<String,Object> parms); 
	
	public void update(String orderId);
	
	public Registration queryByOrderId(String orderId);
	
	public void updateRaceOrderId(Map<String,Object> parms);
}