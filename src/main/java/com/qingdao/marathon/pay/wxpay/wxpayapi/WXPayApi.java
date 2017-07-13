package com.qingdao.marathon.pay.wxpay.wxpayapi;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import com.alipay.demo.trade.utils.ZxingUtils;
import com.qingdao.marathon.logger.SysLogger;
import com.qingdao.marathon.pay.model.PayOrderInfo;
import com.qingdao.marathon.pay.wxpay.config.WXConfig;
import com.qingdao.marathon.pay.wxpay.util.PayUtil;
import com.qingdao.marathon.util.HttpUtil;
import com.qingdao.marathon.util.XMLUtil;

public class WXPayApi {
	
	static SysLogger sysLogger = new SysLogger();

	public static Map<String,Object> weixin_pay(PayOrderInfo info,HttpServletRequest req) throws Exception {  
        Map<String,Object> result = new HashMap<String, Object>();
		// 账号信息  
        String appid = WXConfig.APP_ID;  // appid  
        String mch_id = WXConfig.MCH_ID; // 商业号  
        String key = WXConfig.API_KEY; // key  
  
        String currTime = PayUtil.getCurrTime();  
        String strTime = currTime.substring(8, currTime.length());  
        String strRandom = PayUtil.buildRandom(4) + "";  
        String nonce_str = strTime + strRandom;  
          
        String order_price = Math.round((Double.valueOf(info.getTotalAmount())*100)) + ""; // 价格   注意：价格的单位是分  
        String body = info.getBody();   // 商品名称  
        String out_trade_no = info.getOutTradeNo(); // 订单号  
          
        // 获取发起电脑 ip  
        String spbill_create_ip = WXConfig.CREATE_IP;  
        // 回调接口   
        String notify_url = WXConfig.NOTIFY_URL;  
        String trade_type = "NATIVE";  
          
        SortedMap<Object,Object> packageParams = new TreeMap<Object,Object>();  
        packageParams.put("appid", appid);  
        packageParams.put("mch_id", mch_id);  
        packageParams.put("nonce_str", nonce_str);  
        packageParams.put("body", body);  
        packageParams.put("out_trade_no", out_trade_no);  
        packageParams.put("total_fee", order_price);  
        packageParams.put("spbill_create_ip", spbill_create_ip);  
        packageParams.put("notify_url", notify_url);  
        packageParams.put("trade_type", trade_type);  
  
        String sign = PayUtil.createSign("UTF-8", packageParams,key);  
        packageParams.put("sign", sign);  
          
        String requestXML = PayUtil.getRequestXml(packageParams);  
   
        String resXml = HttpUtil.postData(WXConfig.URL, requestXML);  
  
        sysLogger.info("微信返回=========",resXml);
          
        Map map = XMLUtil.doXMLParse(resXml);  
        //String return_code = (String) map.get("return_code");  
        //String prepay_id = (String) map.get("prepay_id");  
        String urlCode = (String) map.get("code_url");  
        // 需要修改为运行机器上的路径
        String path = req.getSession().getServletContext().getRealPath("/")+"wxpay";
        File newFile = new File(path);
		if (!newFile.exists()) {
			newFile.mkdirs();
		}
        String filePath = String.format(path+"/qr-%s.png",
        		out_trade_no);
        ZxingUtils.getQRCodeImge(urlCode, 256, filePath);
        result.put("qrFile", "qr-"+out_trade_no + ".png");
        result.put("totalAmount", order_price);
        result.put("orderId", out_trade_no);
        result.put("body", body);
        return  result;
	}  
}
