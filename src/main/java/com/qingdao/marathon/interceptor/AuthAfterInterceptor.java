package com.qingdao.marathon.interceptor;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.qingdao.marathon.system.model.AuthInfo;
import com.qingdao.marathon.util.SessionUtils;





/**
* 说明：权限拦截器,获得权限
* @author  wqy
* @version  1.0
*/ 
public class AuthAfterInterceptor extends HandlerInterceptorAdapter {
	
	@Override  
    public void postHandle(HttpServletRequest request,HttpServletResponse response,Object handler,ModelAndView modelAndView) throws Exception{
		
		if(modelAndView!=null){
			String menuUrl = StringUtils.remove( request.getRequestURI(),request.getContextPath());
			menuUrl = StringUtils.substringBeforeLast(menuUrl, "/");
			
			List<AuthInfo> authInfos = SessionUtils.getMenuList(request);
			
			if(authInfos!=null){
				for(AuthInfo authInfo:authInfos){
					List<AuthInfo> children = authInfo.getChildren();
					
					if(children==null){
						continue;
					}
					
					for(AuthInfo child:children){
						if(child.getUrl().contains(menuUrl)){
							modelAndView.addObject("privilege", child.getPrivilege());
						}
					}
				}
			}
		}
		
		super.postHandle(request, response, handler, modelAndView) ;
    }  

	
}
