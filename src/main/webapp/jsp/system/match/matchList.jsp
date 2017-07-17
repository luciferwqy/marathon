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
<script type="text/javascript" src="${reception}/js/jquery.min.js"></script>
<script type="text/javascript" src="${reception}/js/bootstrap.min.js"></script>
<script type="text/javascript" src="${reception}/js/pagination.js"></script>
<script type="text/javascript" src="${reception}/js/jquery-confirm.js"></script>
<script type="text/javascript" src="${reception}/js/manager.js"></script>

</head>
<body>
		<div id="page-wrapper">
			<div class="container-fluid">
				<div class="row">
					<div class="col-lg-12">
						<ol class="breadcrumb">
							<li><i class="fa fa-fighter-jet fa-fw"></i>比赛管理</li>
							<li class="active">赛事管理</li>
						</ol>
					</div>
				</div>
				<div class="row">
				<div class="col-lg-12">
					<div class="panel panel-default">
						<div class="panel-btn-div">
							<c:if test="${privilege>1}">
								<button type="submit" class="btn btn-primary" onclick="showAddFunc()">新增</button>
							</c:if>
						</div>
						<div class="panel-heading">
						  <h3 class="panel-title panel-h3">
						   <i class="fa fa-table fa-fw"></i><span>赛事列表</span>
						  </h3>
						</div>  
						<div class="panel-body table-responsive">
							<table id="matchTable" class="table table-bordered table-hover dataFilter">
								<thead>
									<tr>
										<th>操作</th>
										<th>赛事名称</th>
										<th>报名开始时间</th>
										<th>报名截止时间</th>
										<th>赛事状态</th>
										<th>创建日期</th>
										<th>更新日期</th>
										<th>操作人</th>
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
	
	<div class="modal fade" id="addModal" tabindex="-1" role="dialog" aria-hidden="true">
	   <div class="modal-dialog" >
	      <div class="modal-content">
	         <div class="modal-header">
	            <button type="button" class="close"  aria-hidden="true" onclick="closeAddModal()">&times;</button>
	         	<h4 class="modal-title" id="modelTitle">赛事新增</h4>
	         </div>
	         <div class="modal-body"  >
	         <iframe id="addIFrame" width="100%" height="100%" frameborder="0" scrolling="auto"></iframe>
	         </div>
	         <div class="modal-footer">
	         	<button type="button" class="btn btn-primary" id="saveMatch">新增</button>
	            <button type="button" class="btn btn-danger" onclick="closeAddModal()">取消 </button>
	         </div>
	      </div>
		</div>
	</div>
	
	<div class="modal fade" id="editModal" tabindex="-1" role="dialog" aria-hidden="true">
	   <div class="modal-dialog" >
	      <div class="modal-content">
	         <div class="modal-header">
	            <button type="button" class="close"  aria-hidden="true" onclick="closeEditModal()">&times;</button>
	         	<h4 class="modal-title" id="modelTitle">赛事修改</h4>
	         </div>
	         <div class="modal-body"  >
	         <iframe id="editIFrame" width="100%" height="100%" frameborder="0" scrolling="auto"></iframe>
	         </div>
	         <div class="modal-footer">
	         	<button type="button" class="btn btn-primary" id="updateMatch">修改</button>
	            <button type="button" class="btn btn-danger" onclick="closeEditModal()">取消 </button>
	         </div>
	      </div>
		</div>
	</div>
<script type="text/javascript">

	options = {
			queryForm : $(".query"),
			url :  "${marathon}/admin/system/matchMng/dataList.shtml",
			numPerPage:"10",
			currentPage:"",
			index:"1",
			callback:rebuildTable
	}

	/**
	 * 重构table
	 */
	 function rebuildTable(data){	
		
		$("#matchTable tbody").html("");
		
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
			str += "<tr><td>";
			if("${privilege==2}"=="true"){
				str += "<button type='button' class='btn btn-warning' onclick='showEditModal("+list[i].matchId+")'>修改</button>" ;
			}
			if("${privilege==3}"=="true"){
				str += "<button type='button' class='btn btn-warning' onclick='showEditModal("+list[i].matchId+")'>修改</button>&nbsp;&nbsp;<button type='button' class='btn btn-danger' onclick='delMatch("+list[i].matchId+")'>删除</button>" ;
			}
			str += "</td><td>" + list[i].matchName;
			str += "</td><td>" + list[i].startTime;
			str += "</td><td>" + list[i].regDeadline;
			switch (list[i].state) {
   			case "0":
   				strCom = "开启";
   				break;
   			case "1":
   				strCom = "结束";
   				break;
   			}
			str += "</td><td>" + strCom;
			str += "</td><td>" + list[i].createTime;
			str += "</td><td>" + list[i].updateTime;
			str += "</td><td>" + list[i].opt;
			str += "</td></tr>";
		}
		$("#matchTable tbody").html(str);	
	}

function reloadTable(){
	$.page.loadData(options,$("body"));
}

$(function(){

	 $("#saveMatch").click(function(){
		  $("#addIFrame")[0].contentWindow.submitForm();
	 });
	 
	  $("#updateMatch").click(function(){
		  $("#editIFrame")[0].contentWindow.submitForm();
	 });
	 
	 $(".pagination-nav").pagination(options);

 })

	 function showEditModal(value){
        var frameSrc = '${marathon}/admin/system/matchMng/editMatch.shtml?matchId='+value;
		$("#editIFrame").attr("src", frameSrc); 
		$('#editModal').modal({ show: true, backdrop: 'static' });
	}

	function closeEditModal(){
		$("#editIFrame").attr("src", "");
		$("#editModal").modal("hide");
	}

	function showAddFunc(){
        var frameSrc = '${marathon}/admin/system/matchMng/addMatch.shtml';
		$("#addIFrame").attr("src", frameSrc); 
		$('#addModal').modal({ show: true });
	}

	function closeAddModal(){
		$("#addIFrame").attr("src", ""); 
		$("#addModal").modal("hide");
	}
	
	function delMatch(value){
		$.zzComfirm.alertConfirm("删除将导致数据消失且不可逆转，确定要删除吗？",$("body"),function(){
			$.ajax({
				type:'post',
				url:'${marathon}/admin/system/matchMng/delMatch.shtml',
				data:{"matchId":value},
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
