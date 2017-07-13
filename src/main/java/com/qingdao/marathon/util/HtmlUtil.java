package com.qingdao.marathon.util;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;

import com.qingdao.marathon.contants.LoggerConstants;
import com.qingdao.marathon.logger.SysLogger;

/**
 * <br>
 * <b>功能：</b>详细的功能描述<br>
 * <b>作者：</b>罗泽军<br>
 * <b>日期：</b> Dec 14, 2011 <br>
 * <b>更新者：</b><br>
 * <b>日期：</b> <br>
 * <b>更新内容：</b><br>
 */
public class HtmlUtil {
	
	private static SysLogger syslogger = new SysLogger();
	
	/**
	 * 
	 * <br>
	 * <b>功能：</b>输出json格式<br>
	 * <b>作者：</b>zhaozf<br>
	 * <b>日期：</b> Dec 14, 2011 <br>
	 * @param response
	 * @param jsonStr
	 * @throws Exception
	 */
	public static void writerJson(HttpServletResponse response,String jsonStr) {
			writer(response,jsonStr);
	}
	
	public static void writerJson(HttpServletResponse response,Object object){
			try {
				response.setContentType("application/json");
				writer(response,JSONUtil.toJSONString(object));
			} catch (JSONException e) {
				e.printStackTrace();
			}
	}
	
	/**
	 * 
	 * <br>
	 * <b>功能：</b>输出HTML代码<br>
	 * <b>作者：</b>zhaozf<br>
	 * <b>日期：</b> Dec 14, 2011 <br>
	 * @param response
	 * @param htmlStr
	 * @throws Exception
	 */
	public static void writerText(HttpServletResponse response,String htmlStr) {
		writer(response,htmlStr);
	}
	
	private static void writer(HttpServletResponse response,String str){
		try {
			OutputStream outputStream = response.getOutputStream();
			response.setHeader("Pragma", "No-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setCharacterEncoding("UTF-8");
			byte[] dataByteArr = str.getBytes("UTF-8");
			outputStream.write(dataByteArr);
		} catch (IOException e) {
			syslogger.error(LoggerConstants.HTTP_BUSINESS, "回传异常", e);
		}
	} 
}
