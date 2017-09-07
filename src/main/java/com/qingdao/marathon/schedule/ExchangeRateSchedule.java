package com.qingdao.marathon.schedule;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.Map;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.qingdao.marathon.cache.CacheMap;
import com.qingdao.marathon.contants.Constants;
import com.qingdao.marathon.util.XMLUtil;
/**
 * 获取汇率
 * @author user
 *
 */
@Component
public class ExchangeRateSchedule {

	@Scheduled(cron = "0 0 1 * * ?")
	public void getRate() throws Exception {
        URL u=new URL("http://api.k780.com/?app=finance.rate&scur=USD&tcur=CNY&appkey=24585&sign=fa5f04d314635156270e40a1375419e8&format=xml");
        InputStream in=u.openStream();
        ByteArrayOutputStream out=new ByteArrayOutputStream();
        try {
            byte buf[]=new byte[1024];
            int read = 0;
            while ((read = in.read(buf)) > 0) {
                out.write(buf, 0, read);
            }
        }  finally {
            if (in != null) {
                in.close();
            }
        }
        byte b[]=out.toByteArray();
        System.out.println(new String(b,"utf-8"));
        Map<String,Object> result = XMLUtil.doRateXMLParse(new String(b,"utf-8"));
        CacheMap.getCache().put(Constants.RATE, result.get(Constants.RATE));
    }
}
