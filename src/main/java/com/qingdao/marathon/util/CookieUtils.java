package com.qingdao.marathon.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookieUtils {

	//设置cookie范围
//	private static final String uri = "www.qingdaomarathon.org";
	
	public static void addCookie(HttpServletResponse res,String name,String value){
		Cookie cookie = new Cookie(name, value);
		cookie.setMaxAge(3600);
		res.addCookie(cookie);
	}
	
	public static void delCookie(HttpServletRequest req,HttpServletResponse res){
		Cookie[] cookies = req.getCookies();
		if(cookies != null){
			for(Cookie cookie : cookies){
				cookie.setMaxAge(0);
				cookie.setPath("/");
				res.addCookie(cookie);
			}
		}
	}
	
	public static String getCookie(HttpServletRequest req,String name){
		Cookie[] cookies = req.getCookies();
		if(cookies != null){
			
			for(Cookie cookie : cookies){
				if(name.equalsIgnoreCase(cookie.getName())){
					return cookie.getValue();
				}
			}
		}
		return null;
	}
}
