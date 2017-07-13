<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">  
<html>  
<head>  
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="${marathon}/css/bootstrap.min.css">
<link rel="stylesheet" href="${reception}/css/jquery-confirm.css">
<link rel="stylesheet" href="${marathon}/css/core.css">
<script type='text/javascript' src="${reception}/js/jquery.min.js"></script>
<script type='text/javascript' src="${reception}/js/bootstrap.min.js"></script>
<script type='text/javascript' src="${reception}/js/jquery-confirm.js"></script>
<script type="text/javascript" src="${reception}/js/manager.js"></script>
<script type="text/javascript" src="${reception}/js/ajaxfileupload.js"></script>
</head>  
<body>
 <div id="page-wrapper">
			<div class="container-fluid">
				<div class="row">
					<div class="col-lg-12">
						<ol class="breadcrumb">
							<li><i class="fa fa-fighter-jet fa-fw"></i>比赛管理</li>
							<li class="active">成绩录入</li>
						</ol>
					</div>
				</div>
				
				<div class="col-lg-12">
					  <div class="row">
						  <div class="form-group" style="width:40%">
							<label>组别:</label> 
							<select name="groupId" id="groupId" class="form-control" style="width:calc(100% - 180px)">
				    			<c:forEach items="${matchs}" var="item">
									<option value="${item.groupId}">${item.groupName}</option>
								</c:forEach>
							</select>
							<button type="button" class="btn btn-primary" onclick="downFile()">模板下载</button>
					  	  </div>
					  </div>
					  
					  <div class="row">
					  	  <div class="form-group" style="width:40%;position:relative;">
							<label>成绩导入:</label> 
							<input type="text" id="declFileText" name="declFileText" class="form-control" style="width:calc(100% - 180px);" readonly="readonly"/>
							<button type="button" class="btn btn-primary" onclick="getFilePath()">成绩上传</button>
							<input type="file" id="declFile" name="declFile" style="width:calc(100% - 180px);position:absolute;top:0px;left:85px;opacity:0;"/>
						  </div>
					  </div>
			   </div>
			</div>
		</div>
</body>  
<script type="text/javascript">

    $(window).on("load change",function(){
    	setDeclFileText();
    });

    function setDeclFileText(){
        if($("#declFile").val()==""){
        	$("#declFileText").val("请选择文件")
        }else{
        	var text = $("#declFile").val().substring($("#declFile").val().lastIndexOf("\\")+1,$("#declFile").val().lastIndexOf("."));
        	$("#declFileText").val(text);
        }
    }
    
	function getFilePath(){
        var fileUploadPath = $("#declFile").val();
    	if(fileUploadPath == null || fileUploadPath == ''){
    		alert("请选择文件后上传");
    		return;
    	}
    	$.ajaxFileUpload({
    		url:'${marathon}/admin/system/reviewMng/uploadFile.shtml', //你处理上传文件的服务端
    		secureuri:false,
    		fileElementId:"declFile",
    		dataType: 'json',
    		success:function(result){
				if(result.success){
					alert(result.msg);
				}else{
					alert(result.msg);
				}
			},
			error:function(){
				$.zzComfirm.alertError("系统出现异常，请联系技术",$("body"));
			},
    	})
    }
	
	function downFile(){
		var groupId=$("#groupId option:selected").val();
		$.ajax({
			type:'post',
			url:'${marathon}/admin/system/reviewMng/exportReport.shtml',
			data:{
				"groupId":groupId
				},
			dataType:'json',
			success:function(result){
				if(result.success){
					window.location="${marathon}/admin/system/reviewMng/fileDownLoad.shtml?filePath="+result.msg;
				}else{
					alert(result.msg);
				}
			},
			error:function(){
				$.zzComfirm.alertError("系统出现异常，请联系技术",$("body"));
			},
		});
    }
</script>
</html>