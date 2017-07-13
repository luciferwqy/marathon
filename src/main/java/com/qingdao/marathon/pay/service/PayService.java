package com.qingdao.marathon.pay.service;

public interface PayService {

	/**
	 * 更新报名信息已经交费
	 * @param orderId
	 */
	public void updateRegPay(String orderId);
	/**
	 * 更新订单已经缴费
	 * @param orderId
	 */
	public void updateOrderPay(String orderId);
}
