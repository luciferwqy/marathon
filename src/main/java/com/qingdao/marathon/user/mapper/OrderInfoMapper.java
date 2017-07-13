package com.qingdao.marathon.user.mapper;

import java.util.List;
import java.util.Map;

import com.github.pagehelper.Page;
import com.qingdao.marathon.user.model.OrderExportEntity;
import com.qingdao.marathon.user.model.OrderInfo;

public interface OrderInfoMapper {
	
	public void addOrderInfo(OrderInfo info);
	
	public List<OrderInfo> queryByList(String account); 
	
	public void update(String orderId);
	
	public Page<OrderInfo> queryList(Map<String,Object> parms);
	
	public List<OrderExportEntity> queryOrderByParams(Map<String,Object> parms); 
	
	public void updateOrderState(List<OrderExportEntity> list); 
	
	public List<OrderInfo> queryExport(Map<String,Object> parms);
	
	public String queryByOrderId(String orderId);
	
	public void updateOrder(Map<String,Object> parms);
}