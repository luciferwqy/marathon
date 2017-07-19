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
							<li class="active">参赛人员信息导出</li>
						</ol>
					</div>
				</div>
				
				<div class="col-lg-12">
					  <div class="row">
							<div class="form-group">
								<label>报名组别:</label> 
								<select name="groupId" id="groupId" class="form-control">
										<option value=""></option>
					    			<c:forEach items="${matchs}" var="item">
										<option value="${item.groupId}">${item.groupName}</option>
									</c:forEach>
								</select>
							</div>
					  </div>
					  
	                  <hr class="termHr" style="width:100%;">
					  <div class="row" style="margin-bottom:15px;">
			    		<button type="button" class="btn btn-primary pull-right" onclick="downFile()" style="margin:0 15px 0 0;">导出</button> 
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
										<th>组别名称</th>
										<th>参赛编号</th>
										<th>参赛人员</th>
										<th>联系电话</th>
										<th>证件编号</th>
										<th>邮箱地址</th>
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
			url :  "${marathon}/admin/system/reviewMng/exportDataList.shtml?groupId="+groupId,
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
			str += "<td>" + list[i].groupName;
			str += "</td><td>" + list[i].competitionNo;
			str += "</td><td>" + list[i].name;
			str += "</td><td>" + list[i].mobilPhone;
			str += "</td><td>" + list[i].idnumber;
			str += "</td><td>" + list[i].email;
			str += "</td></tr>";
		}
		$("#reviewTable tbody").html(str);	
	}
	
	function reloadTable(){
		groupId=$("#groupId option:selected").val();
		options = {
				queryForm : $(".query"),
				url :  "${marathon}/admin/system/reviewMng/exportDataList.shtml?groupId="+groupId,
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
	
	function downFile(){
		var groupId=$("#groupId option:selected").val();
		$.ajax({
			type:'post',
			url:'${marathon}/admin/system/reviewMng/participantExportReport.shtml',
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
</body>
</html>
