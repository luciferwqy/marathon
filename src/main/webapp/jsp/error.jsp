<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title> ERROR！！</title>
    <style type="text/css">
      html,body{
        height:100%;
      }
      body{
        background-image:url(/images/error/error.gif);
        background-position:50% 50%;
        background-repeat: no-repeat;
        background-color: #040844;
        position:relative;
      }
      div{
        width:400px;
        height:100px;
        margin:auto;
        position:absolute;
        top:0px;
        bottom:0px;
        left:0px;
        right:0px;
      }
      p{
        font-size:16px;
        color:#fff;
        margin: 218px 0 0 45px;
      }
      #num{
        font-size:20px;
        color:red;
        margin:0 5px;
        display:inline-block;
        vertical-align:-2px;
      }
      a{
        width:120px;
        display:block;
        text-decoration:none;
        font-size:16px;
        margin: 12px 0 0 123px;
        color: #e0c73c;
        padding:30px 0 30px 30px;
        display:block
      }
    </style>
  </head>
  <body>
     <div>
     <input type = "hidden" value = "${msg}" >
       <p>
        <span>哎呀，你访问的页面消失了！</span>
        <span id="num">5</span>秒后返回首页！
       </p>
      <a href="http://bxu2344780002.my3w.com">立即返回首页</a>
     </div>
  </body>
  
  <script type="text/javascript">
  
    var timer=5;
    getTimer();
    
    function getTimer(){
       if(timer>-1){
		   document.getElementById("num").innerHTML=timer
		   timer--;
		   if(timer>0){
			   setTimeout("getTimer()",1000);
		   }else{
			   setTimeout("getTimer()",500);
		   }
       }	
       else{
    	   window.location.href="http://bxu2344780002.my3w.com";
    	   return;
       }
    }
  
  </script>  
</html>