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
<link rel="stylesheet" href="${reception}/css/jquery-confirm.css">
<link rel="stylesheet" href="${marathon}/css/core.css">
<script type='text/javascript' src="${reception}/js/jquery.min.js"></script>
<script type='text/javascript' src="${reception}/js/bootstrap.min.js"></script>
<script type="text/javascript" src="${reception}/js/pagination.js"></script>
<script type='text/javascript' src="${reception}/js/jquery-confirm.js"></script>
<script type="text/javascript" src="${reception}/js/manager.js"></script>
</head>
<body>
		<div id="page-wrapper">
			<div class="container-fluid">
				<div class="row">
					<div class="col-lg-12">
						<ol class="breadcrumb">
							<li><i class="fa fa-fighter-jet fa-fw"></i>比赛管理</li>
							<li class="active">人员审核</li>
						</ol>
					</div>
				</div>
				
				<div class="col-lg-12">
					  <div class="row">
							<div class="form-group">
								<label>报名组别:</label> 
								<select name="groupId" id="groupId" class="form-control">
					    			<c:forEach items="${matchs}" var="item">
										<option value="${item.groupId}">${item.groupName}</option>
									</c:forEach>
								</select>
							</div>
							<div class="form-group">
					    		<label>参赛人数:</label> <input class="form-control" id="groupNumber" name="groupNumber" onkeyup="return ValidateNumber($(this),value)">
							</div>
					  </div>
					  
	                  <hr class="termHr" style="width:100%;">
					  <div class="row" style="margin-bottom:15px;">
			    		<button type="button" class="btn btn-primary pull-right" onclick="postDrawNum()" style="margin:0 15px 0 0;">抽取</button> 
			    		<button type="button" class="btn btn-primary pull-right" onclick="reloadTable()" style="margin:0 15px 0 0;">查询</button>
					  </div>
			   </div>
			   
			  <!-- 按钮组 -->	
				<div class="row">
				<div class="col-lg-12">
					<div class="panel panel-default">
						<div class="panel-heading">
						  <h3 class="panel-title panel-h3">
						   <i class="fa fa-table fa-fw"></i><span>参赛人员列表</span>
						  </h3>
						</div>  
						<div class="panel-body table-responsive">
							<table id="reviewTable" class="table table-bordered table-hover dataFilter">
								<thead>
									<tr>
										<th>赛事名称</th>
										<th>组别名称</th>
										<th>比赛时间</th>
										<th>报名时间</th>
										<th>参赛人员</th>
										<th>参赛编号</th>
										<th>报名状态</th>
										<th>付款状态</th>
										<th>操作</th>
									</tr>
								</thead>
								<tbody>
								</tbody>
							</table>
						</div>
						<div class="pagination-nav">
							<ul id="pagination" class="pagination">
							</ul>
						</div>
					</div>
				</div>
			</div>
			</div>
		</div>
<script type="text/javascript">

	var options=$("#groupId option:selected");
	var groupId = options.val();
	
	options = {
			queryForm : $(".query"),
			url :  "${marathon}/admin/system/reviewMng/dataList.shtml?groupId="+groupId,
			numPerPage:"10",
			currentPage:"",
			index:"1",
			callback:rebuildTable
	}

	/**
	 * 重构table
	 */
	 function rebuildTable(data){	
		
		$("#reviewTable tbody").html("");
		
		if (data == null || data.length == 0) {
			return;
		}
		
		var list = data.obj;
		
		if (list == null || list.length == 0) {
			$.zzComfirm.alertError("没有查到数据",$("body"));
			return;
		}
	
		var str = "";
		for (var i = 0; i < list.length; i++) {
			str += "<tr>";
			str += "<td>" + list[i].matchName;
			str += "</td><td>" + list[i].groupName;
			str += "</td><td>" + list[i].startTime.split(" ")[0];
			str += "</td><td>" + list[i].createTime;
			str += "</td><td>" + list[i].name;
			str += "</td><td>" + list[i].competitionNo;
			switch (list[i].auditState) {
   			case "0":
   				strCom = "未通过";
   				break;
   			case "1":
   				strCom = "通过";
   				break;
   			}
   	  		str += "</td><td>" + strCom;
			switch (list[i].payState) {
   			case "0":
   				strCom = "未支付";
   				break;
   			case "1":
   				strCom = "已支付";
   				break;
   			}
   	  		str += "</td><td>" + strCom;
   	  		strCom = list[i].matchId+"&"+list[i].groupId+"&"+list[i].account+"&"+list[i].name;
   	  		switch (list[i].auditState) {
			case "0":
				str += "</td><td>" ;
				break;
			case "1":
				switch (list[i].payState) {
	   			case "0":
					str += "</td><td>" ;
					//str += "</td><td><button type='button' class='btn btn-primary' onclick='postReviewCancel(\""+strCom+"\")'>取消资格</button>";
	   				break;
	   			case "1":
					str += "</td><td>" ;
	   				break;
	   			}
				break;
			}
			str += "</td></tr>";
		}
		$("#reviewTable tbody").html(str);	
	}

	function ValidateNumber(e, pnumber) {
		if (!/^\d+$/.test(pnumber)) {
			$(e).val(/^\d+/.exec($(e).val()));
		}
	}
	
	function reloadTable(){
		groupId=$("#groupId option:selected").val();
		options = {
				queryForm : $(".query"),
				url :  "${marathon}/admin/system/reviewMng/dataList.shtml?groupId="+groupId,
				numPerPage:"10",
				currentPage:"",
				index:"1",
				callback:rebuildTable
		}
		$.page.loadData(options,$("body"));
	}
	
	$(function(){
		 $(".pagination-nav").pagination(options);
	 })
	
	function postDrawNum(){
		var drawNum = $("#groupNumber").val();
		if (drawNum == "") {
			$.zzComfirm.alertError("未输入参赛人数，请确认",$("body"));
			return;
		}
		var options=$("#groupId option:selected");
		var groupName = options.text();
		var groupId = options.val();
		
		$.zzComfirm.alertConfirm("将进行组别:"+groupName+"的参赛人数抽取,抽取人数:"+drawNum+"人,确认开始吗?",$("body"),function(){
			$.ajax({
				type:'post',
				url:'${marathon}/admin/system/reviewMng/drawNum.shtml',
				data:{
					"drawNum":drawNum,
					"groupId":groupId
					},
				dataType:'json',
				success:function(result){
					if(result.success){
						$.zzComfirm.alertSuccess(result.msg,$("body"),function(){
							reloadTable();
						});
					}else{
						$.zzComfirm.alertError(result.msg,$("body"));
					}
				},
				error:function(){
					$.zzComfirm.alertError("系统出现异常，请联系技术",$("body"));
				},
			});
		})
	 }
	
	function postReviewCancel(value){
		alert("功能开发中...");
		return;
		$.zzComfirm.alertConfirm("正在进行组别:"+groupName+",参赛人:"+value.split("&")[3]+"的参赛资格取消操作,请核实参赛人",$("body"),function(){
			$.ajax({
				type:'post',
				url:'${marathon}/admin/system/reviewMng/updateRegistration.shtml',
				data:{
					"value":value
					},
				dataType:'json',
				success:function(result){
					if(result.success){
						$.zzComfirm.alertSuccess(result.msg,$("body"),function(){
							reloadTable();
						});
					}else{
						$.zzComfirm.alertError(result.msg,$("body"));
					}
				},
				error:function(){
					$.zzComfirm.alertError("系统出现异常，请联系技术",$("body"));
				},
			});
		})
	 }
</script>
</body>
</html>
