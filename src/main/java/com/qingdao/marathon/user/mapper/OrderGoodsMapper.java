package com.qingdao.marathon.user.mapper;

import java.util.Map;

import com.github.pagehelper.Page;
import com.qingdao.marathon.user.model.OrderGoods;


public interface OrderGoodsMapper {
	
	public void addOrderGoods(OrderGoods goods);
	
	public Page<OrderGoods> queryGoodsList(Map<String,Object> parms);
	
	public void updateOrderId(Map<String,Object> parms);
}