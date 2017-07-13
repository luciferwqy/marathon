package com.qingdao.marathon.user.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.qingdao.marathon.base.Pagination;
import com.qingdao.marathon.user.mapper.OrderGoodsMapper;
import com.qingdao.marathon.user.mapper.OrderInfoMapper;
import com.qingdao.marathon.user.model.OrderExportEntity;
import com.qingdao.marathon.user.model.OrderGoods;
import com.qingdao.marathon.user.model.OrderInfo;
import com.qingdao.marathon.user.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService{

	@Resource
	OrderInfoMapper orderInfoMapper;
	
	@Resource
	OrderGoodsMapper orderGoodsMapper;
	
	@Override
	public void addOrder(OrderInfo info) {
		// TODO Auto-generated method stub
		orderInfoMapper.addOrderInfo(info);
		if(info.getGoodsList() != null && info.getGoodsList().size()>0){
			for(OrderGoods goods : info.getGoodsList()){
				goods.setOrderId(info.getOrderId());
				orderGoodsMapper.addOrderGoods(goods);
			}
		}
	}

	@Override
	public List<OrderInfo> queryByList(String account) {
		// TODO Auto-generated method stub
		return orderInfoMapper.queryByList(account);
	}

	
	@Override
	public Page<OrderInfo> queryList(Pagination pagination,Map<String,Object> parms,boolean flag) {
		PageHelper.startPage(pagination.getCurrentPage(), pagination.getNumPerPage(), flag);
		return orderInfoMapper.queryList(parms);
	}

	@Override
	public Page<OrderGoods> queryGoodsList(Pagination pagination, Map<String, Object> parms, boolean flag) {
		PageHelper.startPage(pagination.getCurrentPage(), pagination.getNumPerPage(), flag);
		return orderGoodsMapper.queryGoodsList(parms);
	}

	@Override
	public List<OrderExportEntity> queryOrderByParams(Map<String,Object> parms) {
		return orderInfoMapper.queryOrderByParams(parms);
	}

	@Override
	public void updateOrderState(List<OrderExportEntity> list) {
		orderInfoMapper.updateOrderState(list);
	}

	@Override
	public List<OrderInfo> queryExport(Map<String, Object> parms) {
		// TODO Auto-generated method stub
		return orderInfoMapper.queryExport(parms);
	}

	@Override
	public void updateOrder(Map<String, Object> parms) {
		// TODO Auto-generated method stub
		orderInfoMapper.updateOrder(parms);
		orderGoodsMapper.updateOrderId(parms);
		
	}
}
