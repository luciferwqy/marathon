<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1,user-scalable=no">
<link rel="stylesheet" href="${marathon}/css/bootstrap.min.css">
<link rel="stylesheet" href="${marathon}/css/bootstrap-datetimepicker.min.css">
<link rel="stylesheet" href="${marathon}/css/font-awesome.min.css">
<link rel="stylesheet" href="${reception}/css/jquery-confirm.css">
<link rel="stylesheet" href="${reception}/css/core.css">
<script type='text/javascript' src="${reception}/js/jquery.min.js"></script>
<script type='text/javascript' src="${reception}/js/bootstrap.min.js"></script>
<script type="text/javascript" src="${reception}/js/bootstrap-datetimepicker.min.js"></script>
<script type="text/javascript" src="${reception}/js/bootstrap-datetimepicker.zh-CN.js"></script>
<script type='text/javascript' src="${reception}/js/jquery-confirm.js"></script>
<script type="text/javascript" src="${reception}/js/manager.js"></script>
</head>
<body>
		<div id="page-wrapper" style="padding-top:0">
			<div class="container-fluid">
				<div class="row">
					<div class="col-lg-12">
						<ol class="breadcrumb">
							<li><i class="fa fa-fighter-jet fa-fw"></i>赛事管理</li>
							<li class="active">赛事新增</li>
						</ol>
					</div>
				</div>
	
				<div class="row">
					<form role="form" id="matchForm" >
						<div class="col-lg-12">
								<div class="row">
									<div class="form-group">
										<label>赛事编号:</label> <input class="form-control" disabled="disabled" style="color:red" value="系统自动生成"/>
									</div>
	
									<div class="form-group">
										<label>赛事名称:</label> <input class="form-control" id="matchName" name="matchName" value="">
									</div>
	
									<div class="form-group">
										<label>报名开始时间:</label>
										<div class="input-group input-append date form_datetime"> 
										 <input size="18" class="form-control dataPicker" format="yyyy-mm-dd hh:ii" minView="hour" readonly="readonly" id="startTime" name="startTime">
										 <span class="input-group-addon add-on"><i class="fa fa-calendar fa-fw"></i></span>
										</div>
									</div>
	
									<div class="form-group">
										<label>报名截止时间:</label>
										<div class="input-group input-append date form_datetime"> 
										 <input size="18" class="form-control dataPicker" format="yyyy-mm-dd hh:ii" minView="hour" readonly="readonly" id="regDeadline" name="regDeadline">
										 <span class="input-group-addon add-on"><i class="fa fa-calendar fa-fw"></i></span>
										</div>
									</div>
	
									<div class="form-group">
										<label>赛事状态:</label>
										<select class="form-control" id="state" name="state">
											<option value="0">开启</option>
											<option value="1">结束</option>
										</select>
									</div>
								</div>
						</div>
					</form>
				</div>
			</div>
		</div>
<script type="text/javascript">
var sync = false;
function submitForm() {	
	if (sync) {
		return;
	}
	var matchName = $("#matchName").val();
	if(matchName == null || matchName == ''){
		$.zzComfirm.alertError("请输入赛事名称",$("body"));
		return;
	}
	var startTime = $("#startTime").val();
	if(startTime == null || startTime == ''){
		$.zzComfirm.alertError("请输入赛事开始时间",$("body"));
		return;
	}
	var regDeadline = $("#regDeadline").val();
	if(regDeadline == null || regDeadline == ''){
		$.zzComfirm.alertError("请输入报名截止时间",$("body"));
		return;
	}
	var options=$("#state option:selected");
	var state = options.val();
	sync = true;
	$.ajax({
		type : "post",
		data : {
			'matchName' : matchName,
			'startTime' : startTime,
			'regDeadline' : regDeadline,
			'state' : state
		},
		url : '${marathon}/admin/system/matchMng/saveMatch.shtml',
		dataType:'json',
		success : function(data) {
			sync = false;
			if (data.success) {
				$.zzComfirm.alertSuccess("新增成功！",$("body"),function(){
					window.parent.reloadTable();
					window.parent.closeAddModal();
				});
			} else {
				$.zzComfirm.alertError(data.msg,$("body"));
			}
		},
		error : function(data) {
			sync = false;
			$.zzComfirm.alertError("操作失败，请联系管理员！",$("body"));
		}
	});
};

$(function(){
 	//日历插件 
	$(".form_datetime").each(function(){
		var time = $(this).find(".dataPicker").attr("format")||"yyyy-mm-dd hh:ii:ss";
		var view = $(this).find(".dataPicker").attr("minView")||"hour";
		$(this).datetimepicker({
			container:$(this).find(".dataPicker").parents("div:first"),
			language: "zh-CN",
			startView:"month",
			format:time,
			minView:view,
			pickerPosition:"bottom-left",
			autoclose:true
		});
	}) 
})
</script>
</body>
</html>
