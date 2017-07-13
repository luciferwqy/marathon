<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>青岛马拉松后台管理</title>
<link rel="stylesheet" href="${reception}/css/bootstrap.min.css">
<link rel="stylesheet" href="${reception}/css/form-elements.css">
<link rel="stylesheet" href="${reception}/css/jquery-confirm.css">
<link rel="stylesheet" href="${reception}/css/style.css">
<script type='text/javascript' src="${reception}/js/jquery.min.js"></script>
<script type='text/javascript' src="${reception}/js/bootstrap.min.js"></script>
<script type='text/javascript' src="${reception}/js/jquery-confirm.js"></script>
<script type="text/javascript" src="${reception}/js/manager.js"></script>
</head>

<body>
	<!-- Top content -->
        <div class="top-content">
            <div class="inner-bg">
                <div class="container">
                    <div class="row">
                        <div class="col-sm-8 col-sm-offset-2 text">
                            <h1 style="text-align:center;color:#fff;"><strong>青岛马拉松后台管理</strong></h1>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-sm-6 col-sm-offset-3 form-box">
                        	<div class="form-top">
                        		<div class="form-top-left">
                        			<h3>登录</h3>
                            		<p>请输入您的账号和密码登陆：</p>
                        		</div>
                        		<div class="form-top-right">
                        			<i class="fa fa-key"></i>
                        		</div>
                            </div>
                            <div class="form-bottom">
			                    <form action="" class="login-form">
			                    	<div class="form-group">
			                    		<label class="sr-only" for="form-username">Username</label>
			                        	<input type="text" name="form-username" placeholder="Badge..." class="form-username form-control" id="form-username">
			                        </div>
			                        <div class="form-group">
			                        	<label class="sr-only" for="form-password">Password</label>
			                        	<input type="password" name="form-password" placeholder="Password..." class="form-password form-control" id="form-password">
			                        </div>
			                    	<button id="submitBtn" type="button" class="btn" style="font-size:21px;font-weight:blod">登 录</button>
			                    </form>
		                    </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        
	<script type="text/javascript">
	
	jQuery(document).ready(function() {
		
		function checkForm(){
	    	var userName = $("#form-username").val();
	    	var pwd = $("#form-password").val();
	    	if(userName==""){
	    		$("#form-username").addClass('input-error');
	    		return;
	    	}
	    	if(pwd==""){
	    		$("#form-password").addClass('input-error');
	    		return;
	    	}
    		$.ajax(
    				{
    		            type:"post",  //提交方式  
    		            dataType:"json", //数据类型  
    		            url:encodeURI("${marathon}/admin/login.shtml"), //请求url  
    		            data:{'userName':userName,'pwd':pwd},
    		            success:function(data){ //提交成功的回调函数  
    		            	if(data.success){
    		            		window.location.href = "${marathon}/admin/main.shtml";
    		            	}else{
    		            		$.zzComfirm.alertError(data.msg,$("body"));
    		            	}
	    		         } ,
	    		         error : function(data) {   
	    		            	$(".login_err").html("网络异常，请稍后登录");	
	    		       	 }
    				}
    			)
		}
		
	    $('.login-form input[type="text"], .login-form input[type="password"], .login-form textarea').on('focus', function() {
	    	$(this).removeClass('input-error');
	    });
	    
	    $('#submitBtn').on('click',checkForm);
	    $(document).keypress(function(e){
	    	var e=window.event||e; 
	    	var key = e.which||e.keyCode; 
	    	if(key == 13) { 
	    		if($("div.jconfirm").length>0){
	    			$("div.jconfirm").remove();
	    			$("body").removeClass("jconfirm-noscroll")
	    		}
	    		else{
		    	   checkForm();
	    	    }
	    	}
	    });
	    
	});
	</script>
	
</body>
</html>
