package com.qingdao.marathon.pay.wxpay.config;

public class WXConfig {

	public static final String APP_ID = "wx694dd1af2b95c3a4";
	
	public static final String MCH_ID = "1341847301";
	
	public static final String API_KEY = "mnbvcxzlkjhgfdsapoiuytrewq032701";
	//发起电脑IP
	public static final String CREATE_IP = "36.22.63.94";
	
//	public static final String CREATE_IP = "121.196.229.214";
	
	public static final String URL = "https://api.mch.weixin.qq.com/pay/unifiedorder";
	
	public static final String NOTIFY_URL = "http://121.196.229.214:8080/marathon/payMng/wxPayReturn.shtml";
}
