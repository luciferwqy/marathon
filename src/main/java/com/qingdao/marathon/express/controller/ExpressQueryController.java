package com.qingdao.marathon.express.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.qingdao.marathon.cache.CacheMap;
import com.qingdao.marathon.contants.Constants;
import com.qingdao.marathon.express.model.RoteModel;
import com.qingdao.marathon.express.model.TracesModel;
import com.qingdao.marathon.model.ResultModel;
import com.qingdao.marathon.util.HttpUtil;
import com.qingdao.marathon.util.MethodUtil;

import net.sf.json.JSONObject;


@Controller
public class ExpressQueryController {

	//电商ID
    private String EBusinessID="1284162";
    //电商加密私钥，快递鸟提供，注意保管，不要泄漏
    private String AppKey="7132d6fb-4be0-4982-b317-961de527cd82";
    //请求url
    private String ReqURL="http://api.kdniao.cc/Ebusiness/EbusinessOrderHandle.aspx";
	
	@SuppressWarnings("unchecked")
	@RequestMapping("/express/getRoute")
	@ResponseBody
    public ResultModel getRoute(HttpServletRequest req,HttpServletResponse res) {
		ResultModel model = new ResultModel();
		res.setHeader(Constants.CROSS_DOMAIN, Constants.DOMAIN_NAME);
		String carrierName = req.getParameter("carrierName");//快递公司名称
		String expressID = req.getParameter("expressId");//快递单号
        try {
        	Map<String,String> carrierMap = (Map<String, String>) CacheMap.getCache().getData().get(Constants.CARRIER);
        	String carrierID = carrierMap.get(carrierName);//获取快递公司编码
            String result = getOrderTracesByJson(carrierID, expressID);
            System.out.print(result);
            JSONObject jsonObj = JSONObject.fromObject(result.toLowerCase());
            Map<String,Class> classMap = new HashMap<String,Class>();
            classMap.put("traces", TracesModel.class);
            RoteModel rote = (RoteModel) jsonObj.toBean(jsonObj, RoteModel.class,classMap);
            model.setObj(rote);
            model.setSuccess(true);
             
        } catch (Exception e) {
            e.printStackTrace();
            model.setMsg("网络异常");
            model.setSuccess(false);
        }
        return model;
    }
     
    /**
     * Json方式 查询订单物流轨迹
     * @throws Exception 
     */
    public String getOrderTracesByJson(String expCode, String expNo) throws Exception{
        String requestData= "{'OrderCode':'','ShipperCode':'" + expCode + "','LogisticCode':'" + expNo + "'}";
         
        Map<String, String> params = new HashMap<String, String>();
        params.put("RequestData", MethodUtil.urlEncoder(requestData, "UTF-8"));
        params.put("EBusinessID", EBusinessID);
        params.put("RequestType", "1002");
        String dataSign=MethodUtil.encrypt(requestData, AppKey, "UTF-8");
        params.put("DataSign", MethodUtil.urlEncoder(dataSign, "UTF-8"));
        params.put("DataType", "2");
         
        String result=HttpUtil.sendPost(ReqURL, params);  
         
        //根据公司业务处理返回的信息......
         
        return result;
    }
    
    public static void main(String[] args) {
    	ExpressQueryController api = new ExpressQueryController();
        try {
            String result = api.getOrderTracesByJson("ANE", "210001633605");
            System.out.print(result.toLowerCase());
//            JSONObject jsonObj = JSONObject.fromObject(result.toLowerCase());
//            Map<String,Class> classMap = new HashMap<String,Class>();
//            classMap.put("traces", TracesModel.class);
//            RoteModel model = (RoteModel) jsonObj.toBean(jsonObj, RoteModel.class,classMap);
//            System.out.println("\n");
//            System.out.println(model.getSuccess());
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
}
