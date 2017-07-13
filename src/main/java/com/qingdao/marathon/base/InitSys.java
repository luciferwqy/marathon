package com.qingdao.marathon.base;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.qingdao.marathon.cache.CacheMap;
import com.qingdao.marathon.contants.Constants;
import com.qingdao.marathon.express.model.CarrierModel;
import com.qingdao.marathon.schedule.ExchangeRateSchedule;
import com.qingdao.marathon.util.ExcelUtil;

/**
 * 初始化系统 1、加载缓存数据
 */
@Component
public class InitSys {

	@Resource
	ExchangeRateSchedule exchangeRateSchedule;
	
	@PostConstruct
	public void init() {
		try {
			exchangeRateSchedule.getRate();
			
			loadXls();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 加载物流公司
	 */
	private void loadXls() {
		try {
			Map<String,String> carrierMap = new HashMap<String,String>();
			String filePath = URLDecoder.decode(this.getClass().getClassLoader().getResource("").getPath(),"utf-8");
			List<CarrierModel> list = ExcelUtil.instance().getCache(filePath+"/ExpressCode.xls");
			for(CarrierModel model : list){
				carrierMap.put(model.getCarrierName(), model.getCarrierID());
			}
			CacheMap.getCache().put(Constants.CARRIER, carrierMap);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
			
}
