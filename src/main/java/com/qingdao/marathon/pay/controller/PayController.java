package com.qingdao.marathon.pay.controller;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.qingdao.marathon.cache.CacheMap;
import com.qingdao.marathon.contants.Constants;
import com.qingdao.marathon.contants.LoggerConstants;
import com.qingdao.marathon.logger.SysLogger;
import com.qingdao.marathon.model.ResultModel;
import com.qingdao.marathon.pay.alipay.api.PayApi;
import com.qingdao.marathon.pay.alipay.util.AlipayNotify;
import com.qingdao.marathon.pay.model.OrderGoods;
import com.qingdao.marathon.pay.model.PayOrderInfo;
import com.qingdao.marathon.pay.service.PayService;
import com.qingdao.marathon.pay.wxpay.config.WXConfig;
import com.qingdao.marathon.pay.wxpay.util.PayUtil;
import com.qingdao.marathon.pay.wxpay.wxpayapi.WXPayApi;
import com.qingdao.marathon.user.model.OrderInfo;
import com.qingdao.marathon.user.model.PersonalInfo;
import com.qingdao.marathon.user.model.Registration;
import com.qingdao.marathon.user.service.OrderService;
import com.qingdao.marathon.user.service.UserService;
import com.qingdao.marathon.util.CookieUtils;
import com.qingdao.marathon.util.DateUtil;
import com.qingdao.marathon.util.MathUtil;
import com.qingdao.marathon.util.URLUtils;
import com.qingdao.marathon.util.XMLUtil;

@Controller
public class PayController {
	
	private static final String ZH_CN = "cn";
	private static final String EN = "en";
	
	//支付类型
	private final String weixin = "1";
	private final String alipay = "0";
	
	//订单类型
	private final String SHOPPING = "0";
	
	@Resource
	OrderService orderService;
	
	@Resource
	PayService payService;
	
	@Resource
	UserService userService;
	
	@Resource
	SysLogger sysLogger;

	@RequestMapping("/payMng/pay")
	@ResponseBody
	public ResultModel pay(OrderInfo order,HttpServletRequest req,HttpServletResponse res){
		ResultModel model = new ResultModel();
		res.setHeader(Constants.CROSS_DOMAIN, Constants.DOMAIN_NAME);
		String payType = req.getParameter("payType");
//		String account = req.getParameter("account");
		String orderType = req.getParameter("orderType");
		
		Map<String,Object> parms = new HashMap<String,Object>();
		
		PayOrderInfo info = new PayOrderInfo();
		try {
			//如果是购物订单，先插入订单
			if(SHOPPING.equals(orderType)){
				Map<String,String> confMap = URLUtils.getConfMap();
				
//				order.setAccount(account);
				double temp = 0.0;
				if(order.getGoodsList() != null && order.getGoodsList().size()>0){
					for(com.qingdao.marathon.user.model.OrderGoods goods : order.getGoodsList()){
						temp += goods.getQuantity()*Double.valueOf(confMap.get("clothes"));
					}
					order.setTotalAmount(temp+"");
				} else {
					model.setSuccess(false);
					model.setMsg("没有商品");
					return model;
				}
				
				String orderId = "SP"+System.currentTimeMillis();
				
				if(order.getOrderId() == null || "".equals(order.getOrderId())){
					order.setOrderId(orderId);
					orderService.addOrder(order);
				}
//				else {
//					parms.put("orderId", order.getOrderId());
//					parms.put("newOrderId", orderId);
//					order.setOrderId(orderId);
//					orderService.updateOrder(parms);
//				}
				
				info.setTotalAmount(order.getTotalAmount());
			}else{
				//时分秒
//				String now = DateUtil.getNowPlusTimeMill();
				PersonalInfo personal = userService.getUserInfo(order.getAccount());
				Registration r = userService.queryByOrderId(order.getOrderId());
//				String newRaceOrderId = "BM"+now+r.getCompetitionNo();
//				parms.put("orderId", order.getOrderId());
//				parms.put("newRaceOrderId", newRaceOrderId);
//				order.setOrderId(newRaceOrderId);
//				userService.updateRaceOrderId(parms);
				if(Constants.CHINA.equals(personal.getNationality()) || Constants.TAIBEI.equals(personal.getNationality())
						|| Constants.HONGKONG.equals(personal.getNationality()) || Constants.MACAO.equals(personal.getNationality())){
					
					info.setTotalAmount(r.getFee());
				}else{
					String rate = (String) CacheMap.getCache().getData().get(Constants.RATE);
					info.setTotalAmount(MathUtil.mul(r.getFee2(), rate));
				}
			}
			
			info.setOutTradeNo(order.getOrderId());
			info.setBody(order.getBody());
			info.setSubject(order.getSubject());
			
			if(weixin.equals(payType)){
				Map<String,Object> result = WXPayApi.weixin_pay(info, req);
//				req.getSession().setAttribute("qrFile", result.get("qrFile"));
//				req.getSession().setAttribute("body", result.get("body"));
//				req.getSession().setAttribute("orderId", result.get("orderId"));
//				req.getSession().setAttribute("totalAmount", result.get("totalAmount"));
				result.put("payType", weixin);
				model.setSuccess(true);
				model.setObj(result);
			}else if(alipay.equals(payType)){
				
				if(order.getGoodsList() != null && order.getGoodsList().size()>0){
					List<OrderGoods> goodsList = new ArrayList<OrderGoods>();
					OrderGoods temp = null;
					for(com.qingdao.marathon.user.model.OrderGoods goods : order.getGoodsList()){
						temp = new OrderGoods();
						temp.setAmount(goods.getAmount());
						temp.setGoodsName(goods.getGoodsName());
						temp.setQty(goods.getQuantity());
						goodsList.add(temp);
					}
					info.setGoodsList(goodsList);
				}
				String html = PayApi.aliPay(info);
//				String html = "test";
//				PrintWriter pw = res.getWriter();
//				pw.print(html);
//				pw.close();
				Map<String,Object> result = new HashMap<String,Object>();
				result.put("payType", alipay);
				model.setMsg(html);
				model.setObj(result);
				model.setSuccess(true);
			}
		} catch (Exception e) {
			e.printStackTrace();
			sysLogger.error(LoggerConstants.PAY, "支付宝支付出错",e);
			model.setSuccess(false);
		}
		return model;
		
	}
	
	//return_url
	@RequestMapping("/payMng/payReturn")
	public void payReturn(HttpServletRequest req,HttpServletResponse res){
		sysLogger.info(LoggerConstants.PAY,"支付宝RETURN回调");  
		try {
			//获取支付宝GET过来反馈信息
			Map<String,String> params = new HashMap<String,String>();
			Map requestParams = req.getParameterMap();
			for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
				String name = (String) iter.next();
				String[] values = (String[]) requestParams.get(name);
				String valueStr = "";
				for (int i = 0; i < values.length; i++) {
					valueStr = (i == values.length - 1) ? valueStr + values[i]
							: valueStr + values[i] + ",";
				}
				//乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
				valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
				params.put(name, valueStr);
			}
			
			//获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以下仅供参考)//
			//商户订单号

			String out_trade_no = new String(req.getParameter("out_trade_no").getBytes("ISO-8859-1"),"UTF-8");

			//交易状态
			String trade_status = new String(req.getParameter("trade_status").getBytes("ISO-8859-1"),"UTF-8");

			String lang = CookieUtils.getCookie(req, "lang");
			
			//计算得出通知验证结果
			boolean verify_result = AlipayNotify.verify(params);
			
			if(verify_result){

				if(trade_status.equals("TRADE_FINISHED") || trade_status.equals("TRADE_SUCCESS")){
					if(out_trade_no.startsWith("SP")){
						payService.updateOrderPay(out_trade_no);
						if(lang == null || ZH_CN.equals(lang)){
							res.sendRedirect(Constants.DOMAIN_NAME+"/html/chiness/personal/information.html");
						}else if(EN.equals(lang)){
							res.sendRedirect(Constants.DOMAIN_NAME+"/html/english/personal/information.html");
						}
					}else{
						payService.updateRegPay(out_trade_no);
						if(lang == null || ZH_CN.equals(lang)){
							res.sendRedirect(Constants.DOMAIN_NAME+"/html/chiness/personal/myRegister.html");
						}else if(EN.equals(lang)){
							res.sendRedirect(Constants.DOMAIN_NAME+"/html/english/personal/myRegister.html");
						}
					}
					
				}else{
					req.setAttribute("msg", "支付失败");
					req.getRequestDispatcher("/jsp/error.jsp").forward(req, res);
				}

			}else{
				req.setAttribute("msg", "验证失败");
				req.getRequestDispatcher("/jsp/error.jsp").forward(req, res);
			}
		} catch (Exception e) {
			sysLogger.error(LoggerConstants.PAY, "支付宝支付",e);
		}
	}
	
	//notify_url
	@RequestMapping("/payMng/payNotify")
	public void payNotify(HttpServletRequest req,HttpServletResponse res) throws IOException{
		sysLogger.info(LoggerConstants.PAY,"支付宝NOTIFY回调");  
		PrintWriter pw = res.getWriter();
		try {
			//获取支付宝POST过来反馈信息
			Map<String,String> params = new HashMap<String,String>();
			Map requestParams = req.getParameterMap();
			for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
				String name = (String) iter.next();
				String[] values = (String[]) requestParams.get(name);
				String valueStr = "";
				for (int i = 0; i < values.length; i++) {
					valueStr = (i == values.length - 1) ? valueStr + values[i]
							: valueStr + values[i] + ",";
				}
				//乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
//				valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
				sysLogger.info(LoggerConstants.PAY, "回调信息=====name="+name + "&&&&&& valueStr="+valueStr);
				params.put(name, valueStr);
			}
			
			//获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以下仅供参考)//
			//商户订单号

			String out_trade_no = new String(req.getParameter("out_trade_no").getBytes("ISO-8859-1"),"UTF-8");
			sysLogger.info(LoggerConstants.PAY, "out_trade_no====="+ out_trade_no);
			//交易状态
			String trade_status = new String(req.getParameter("trade_status").getBytes("ISO-8859-1"),"UTF-8");
			sysLogger.info(LoggerConstants.PAY, "trade_status====="+ trade_status);
			//获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以上仅供参考)//

			if(AlipayNotify.verify(params)){//验证成功

				if(trade_status.equals("TRADE_FINISHED") || trade_status.equals("TRADE_SUCCESS")){
					if(out_trade_no.startsWith("SP")){
						payService.updateOrderPay(out_trade_no);
					}else{
						payService.updateRegPay(out_trade_no);
					}
					pw.print("success");	
				}else{
					pw.print("fail");
				}

			}else{//验证失败
				sysLogger.info(LoggerConstants.PAY, "支付宝验证失败");
				pw.print("fail");
			}
		} catch (Exception e) {
			sysLogger.error(LoggerConstants.PAY, "支付宝回调出错",e);
			pw.print("fail");
		}
	}
	
	@RequestMapping("/payMng/wxPayReturn")
	public void wxNotify(HttpServletRequest req,HttpServletResponse res) throws Exception{
		sysLogger.info(LoggerConstants.PAY,"微信NOTIFY回调");  
		//读取参数  
        InputStream inputStream ;  
        StringBuffer sb = new StringBuffer();  
        inputStream = req.getInputStream();  
        String s ;  
        BufferedReader in = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));  
        while ((s = in.readLine()) != null){  
            sb.append(s);  
        }  
        in.close();  
        inputStream.close();  
  
        //解析xml成map  
        Map<String, String> m = new HashMap<String, String>();  
        m = XMLUtil.doXMLParse(sb.toString());  
          
        //过滤空 设置 TreeMap  
        SortedMap<Object,Object> packageParams = new TreeMap<Object,Object>();        
        Iterator it = m.keySet().iterator();  
        while (it.hasNext()) {  
            String parameter = (String) it.next();  
            String parameterValue = m.get(parameter);  
              
            String v = "";  
            if(null != parameterValue) {  
                v = parameterValue.trim();  
            }  
            packageParams.put(parameter, v);  
        }  
          
        // 账号信息  
        String key = WXConfig.API_KEY; // key  
  
        //判断签名是否正确  
        if(PayUtil.isTenpaySign("UTF-8", packageParams,key)) {  
            //------------------------------  
            //处理业务开始  
            //------------------------------  
            String resXml = "";  
            if("SUCCESS".equals((String)packageParams.get("result_code"))){  
                //////////执行自己的业务逻辑////////////////  
                String out_trade_no = (String)packageParams.get("out_trade_no");  
                  
                if(out_trade_no.startsWith("SP")){
					payService.updateOrderPay(out_trade_no);
				}else{
					payService.updateRegPay(out_trade_no);
				}
                  
                  
                //通知微信.异步确认成功.必写.不然会一直通知后台.八次之后就认为交易失败了.  
                resXml = "<xml>" + "<return_code><![CDATA[SUCCESS]]></return_code>"  
                        + "<return_msg><![CDATA[OK]]></return_msg>" + "</xml> ";  
                  
            } else {  
                resXml = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>"  
                        + "<return_msg><![CDATA[报文为空]]></return_msg>" + "</xml> ";  
            }  
            //------------------------------  
            //处理业务完毕  
            //------------------------------  
            BufferedOutputStream out = new BufferedOutputStream(  
                    res.getOutputStream());  
            out.write(resXml.getBytes());  
            out.flush();  
            out.close();  
        } else{  
        	sysLogger.info(LoggerConstants.PAY,"微信通知签名验证失败");  
        }  
	}
}
