package com.qingdao.marathon.user.service;

import java.util.List;
import java.util.Map;

import com.github.pagehelper.Page;
import com.qingdao.marathon.base.Pagination;
import com.qingdao.marathon.user.model.OrderExportEntity;
import com.qingdao.marathon.user.model.OrderGoods;
import com.qingdao.marathon.user.model.OrderInfo;

public interface OrderService {

	public void addOrder(OrderInfo info);
	
	public void updateOrder(Map<String,Object> parms);
	
	public List<OrderInfo> queryByList(String account);
	
	/**
	 * 获取订单
	 * @param pagination
	 * @param parms
	 * @param flag
	 * @return
	 */
	public Page<OrderInfo> queryList(Pagination pagination,Map<String,Object> parms,boolean flag);
	
	/**
	 * 获取订单商品
	 * @param pagination
	 * @param parms
	 * @param flag
	 * @return
	 */
	public Page<OrderGoods> queryGoodsList(Pagination pagination,Map<String,Object> parms,boolean flag);
	
	public List<OrderExportEntity> queryOrderByParams(Map<String,Object> parms);
	
	public void updateOrderState(List<OrderExportEntity> list);
	
	public List<OrderInfo> queryExport(Map<String,Object> parms);
}
