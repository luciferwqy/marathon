<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"  %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"  %>

<!DOCTYPE HTML>
<html lang="en">
	<head>	
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1,user-scalable=no">
	<title>青岛马拉松后台管理</title>	 
	<link rel="stylesheet" href="${marathon}/css/bootstrap.min.css">
	<link rel="stylesheet" href="${marathon}/css/bootstrap-datetimepicker.min.css">
    <link rel="stylesheet" href="${marathon}/css/font-awesome.min.css">
	<link rel="stylesheet" href="${reception}/css/jquery-confirm.css">  
	<link rel="stylesheet" href="${reception}/css/metisMenu.min.css">
	<link rel="stylesheet" href="${reception}/css/mainpage.css">  
	<script type="text/javascript" src="${reception}/js/jquery.min.js"></script>
	<script type="text/javascript" src="${reception}/js/bootstrap.min.js"></script>
	<script type="text/javascript" src="${reception}/js/metisMenu.min.js"></script>
	<script type="text/javascript" src="${reception}/js/mainPageHtml.js"></script>
	<!--[if lt IE 9]>
	   <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
	   <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
	<![endif] -->
	</head>
	<body>
		<nav class="navbar navbar-default navbar-static-top" id="mainTopNav" role="navigation" style="margin-bottom: 0">
			<div class="navbar-header">
	                <button type="button" class="navbar-toggle" id="mainNavBtn" data-toggle="collapse" data-target=".navbar-collapse" onclick="mainTopNav_click()">
	                    <span class="icon-bar"></span>
	                    <span class="icon-bar"></span>
	                    <span class="icon-bar"></span>
	                </button>
                    <div class="header">
                      <a href="${marathon}/admin/main.shtml" class="mainHomePage">
                        <span>青岛马拉松后台管理</span>
                      </a>
                    </div>      
	            </div>
	            <ul class="nav navbar-top-links navbar-right">
                <li class="dropdown" id="mainDropdown">
                    <a class="dropdown-toggle" data-toggle="dropdown" href="#">
                        <i class="fa fa-user fa-fw"></i>${operator.optName} <i class="fa fa-caret-down"></i>
                    </a>
                    <ul class="dropdown-menu dropdown-user" style="width:150px;">
                        <li class="divider"></li>
                        <li><a href="${marathon}/admin/logout.shtml" class="sign-out"><i class="fa fa-sign-out fa-fw"></i> 退出</a>
                        </li>
                    </ul>
                </li>
            </ul>
            
            <div class="Kbar-default sidebar" id="mainLeftNav" role="navigation">
            	<div class="sidebar-nav navbar-collapse">
                      <ul class="nav in" id="side-menu">
            			<c:forEach var="item" items="${menuList}">
            				<li>
	            				<a href="javascript:void(0);"><i class="fa ${item.tag} fa-fw"></i> ${item.name}</a>
	            				<ul class="nav nav-second-level">
			            			<!-- 引用已有的页面，target="new"时：新建，target="refresh"时：刷新，未设置时：不新建不刷新   --> 
		            				<c:forEach var="node" items="${item.children}">
			            				<li>
			            				<c:choose>
			            					<c:when test="${node.url==null}">${node.name}</c:when>
			            					<c:otherwise> <a href="${marathon}${node.url}?privilege=${node.privilege}" target="" onclick="mainLeftNav_click()">${node.name}</a></c:otherwise>
		                                </c:choose>
		                                </li>
									</c:forEach>
	            				</ul>
            				</li>
						   </c:forEach>
            		     </ul>
            	     </div>
               </div>
		</nav>
		<div id="mainContainer">
		  <div id="mainHead">
		   <div id="mainNavTabs"> 
		     <ul class="nav nav-tabs">
             </ul>
            </div> 
            <div id="mainRefresh">
              <span class="fa fa-refresh fa-fw"></span>
            </div>
		  </div>
		  <div id="mainContent">
		    <div class="tab-content">
            </div>
		  </div>
		</div>
	</body>
	<script type="text/javascript">
	  $(function(e){
		  
		  // 页面单击或双击,取消主页面右上角下拉菜单
		  $(document).on("click dbclick",function(){$("#mainDropdown").removeClass("open");})
	  })
	</script>
</html>
