<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>订单查询</title>
<link rel="stylesheet" href="${marathon}/css/bootstrap.min.css">
<link rel="stylesheet" href="${reception}/css/jquery-confirm.css">
<link rel="stylesheet" href="${marathon}/css/core.css">
<script type="text/javascript" src="${reception}/js/jquery.min.js"></script>
<script type="text/javascript" src="${reception}/js/bootstrap.min.js"></script>
<script type="text/javascript" src="${reception}/js/pagination.js"></script>
<script type="text/javascript" src="${reception}/js/jquery-confirm.js"></script>
<script type="text/javascript" src="${reception}/js/manager.js"></script>
<script type="text/javascript" src="${reception}/js/distpicker.data.js"></script>
<script type="text/javascript" src="${reception}/js/distpicker.js"></script>
<script type="text/javascript" src="${reception}/js/ajaxfileupload.js"></script>
</head>

<body>
	<div id="page-wrapper">
	<div id="mask"></div>
		<div class="container-fluid">
			<div class="row">
				<div class="col-lg-12">
					<div class="query">
						<div class="row">
							<div class="form-group">
								<label>订单状态</label> 
								<select class="form-control" name="state">
									<option value="">全部</option>
									<option value="0">未支付</option>
									<option value="1">已支付</option>
									<option value="2">已发货</option>
								</select>
							</div>
							<div class="form-group">
								<label>订单号</label> 
								<input id="orderId" name = "orderId" class="form-control"/>
							</div>
							<div class="form-group" style="width:66.66666666%">
                        		<label>收货地址</label>
					    		<div data-toggle="distpicker" style="width:calc(100% - 88px);overflow:hidden;">
					      		  <div class="col-sm-4" style="padding-right:8px">
						   			<select class="form-control info " id="receiverProvince" name="receiverProvince" style="width:100%"></select>
					      		  </div>
					      		  <div class="col-sm-4" style="padding-right:8px">
						   			<select class="form-control info" id="receiverCity" name="receiverCity" style="width:100%"></select>
					      		  </div>
					      		  <div class="col-sm-4" style="padding-right:8px">
						   			<select class="form-control info" id="receiverArea" name="receiverArea" style="width:100%"></select>
						  		  </div>
					    		</div>
                      		</div>
						</div>
						<hr class="termHr" style="width:100%;">
					    <div class="row" style="margin-bottom:15px;">
						  <button type="button" id="querybtns" class="btn btn-primary pull-right" style="margin:0 15px 0 0;">查询</button>
					    </div>
						</div>
					</div>
				</div>
			</div>

			<div class="row">
				<div class="col-lg-12">
					<div class="panel panel-default">
						<div class="panel-btn-div" style="overflow:hidden;margin-bottom:5px;">
							<button type="button" class="btn btn-primary pull-left" style="margin-right:15px;" onclick="downOrder()">待发货订单下载</button>
							<div class="form-group" style="margin:0;width:25%;float:left;">
							   <div class="input-group" style="width:100%;position:relative;">
							     <input type="text" id="declFileText" name="declFileText" class="form-control" readonly="readonly"/>
							     <input type="file" id="declFile" name="declFile" style="width:calc(100% - 75px);position:absolute;top:0px;left:0px;opacity:0;z-index:9;"/>
							     <div class="input-group-btn">
						 	       <button type="button" class="btn btn-primary" onclick="getFilePath()">订单发货</button>
							     </div>
							   </div>
						  </div>
						</div>
						<div class="panel-heading">
							<h3 class="panel-title panel-h3">
								<i class="fa fa-bar-chart-o fa-fw"></i>订单列表
							</h3>
						</div>
						<div class="panel-body">
							<table id="orderTable" class="table table-hover">
								<thead>
									<tr>
										<th>订单详情</th>
										<th>订单编号</th>
										<th>收货人</th>
										<th>联系电话</th>
										<th>省</th>
										<th>市</th>
										<th>区</th>
										<th>地址</th>
										<th>快递单号</th>
										<th>快递公司</th>
										<th>订单状态</th>
										<th>创建时间</th>
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
	<div class="modal fade" id="orderDetailModal" tabindex="-1"
		role="dialog" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true" onclick="closeOrderDetailModal()">&times;</button>
					<h4 class="modal-title" id="modelTitle">订单详情</h4>
				</div>
				<div class="modal-body">
					<iframe id="orderDetailIFrame" width="100%" height="100%" frameborder="0" scrolling="auto"></iframe>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-danger" data-dismiss="modal"
						onclick="closeOrderDetailModal();">关闭</button>
				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript">
	
	    $(window).on("load change",function(){
	    	setDeclFileText();
	    });
	
	    function setDeclFileText(){
	        if($("#declFile").val()==""){
	        	$("#declFileText").val("请选择订单")
	        }else{
	        	var text = $("#declFile").val().substring($("#declFile").val().lastIndexOf("\\")+1,$("#declFile").val().lastIndexOf("."));
	        	$("#declFileText").val(text);
	        }
	    }
	
	
		/**
		 * 初始化分页信息
		 */
		var options = {
			queryForm : ".query",
			url : "${marathon}/admin/system/orderMng/dataList.shtml",
			numPerPage : "20",
			currentPage : "",
			index : "1",
			callback : rebuildTable
		}

		$(function() {
			$(".pagination-nav").pagination(options);
		})
		
		function closeOrderDetailModal(){
			$("#orderDetailModal").modal("hide");
		}
		
		function closeShopModal(){
			$("#shopModal").modal("hide");
		}

		/**
		 * 重构table
		 */
		function rebuildTable(data) {
			$.zzComfirm.endMask();
			$("#orderTable tbody").html("");

			if (data == null || data.length == 0) {
				return;
			}

			var list = data.obj;

			if (list == null || list.length == 0) {
				$.zzComfirm.alertError("没有查到数据",$("body"));
				return;
			}

			var str = "";
			var strCom = "";
			for (var i = 0; i < list.length; i++) {
				str += "<tr>";

				str += "<td><button type='button' class='btn btn-info' onclick='showDetail(\"";
				str += list[i].orderId + "\",\"" + list[i].expressId + "\",\"" + list[i].carrierName
				str += "\")'>详情</button></td>";
				str += "</td><td>" + list[i].orderId;
				str += "</td><td>" + list[i].receiverName;
				str += "</td><td>" + list[i].receiverPhone;
				str += "</td><td>" + list[i].receiverProvince;
				str += "</td><td>" + list[i].receiverCity;
				str += "</td><td>" + list[i].receiverArea;
				str += "</td><td>" + list[i].receiverAddress;
				str += "</td><td>" + (list[i].expressId == null ? "无" : list[i].expressId);
				str += "</td><td>" + (list[i].carrierName== null ? "无" : list[i].carrierName);
				switch (list[i].state) {
				case "0":
					strCom = "未支付";
					break;
				case "1":
					strCom = "已支付";
					break;
				case "2":
					strCom = "已发货";
					break;
				}
				str += "</td><td>" + strCom;
				str += "</td><td>" + (list[i].createtime== null ? "无" : list[i].createtime);
				str += "</td></tr>";
			}

			$("#orderTable tbody").html(str);
		}



		function showDetail(orderId,expressId,carrierName) {
			var frameSrc = "${marathon}/admin/system/orderMng/showDetail.shtml?orderId="
					+ orderId + "&expressId="+expressId + "&carrierName=" + encodeURI(encodeURI(carrierName));
			$("#orderDetailIFrame").attr("src", frameSrc);
			$('#orderDetailModal').modal({
				show : true,
				backdrop : 'static'
			});
		}
		
		function downOrder(){
			var state=$("#state option:selected").val();
			$.ajax({
				type:'post',
				url:'${marathon}/admin/system/orderMng/exportReport.shtml',
				data:{
					"state":"1"
					},
				dataType:'json',
				success:function(result){
					if(result.success){
						window.location="${marathon}/admin/system/orderMng/fileDownLoad.shtml?filePath="+result.msg;
					}else{
						alert(result.msg);
					}
				},
				error:function(){
					$.zzComfirm.alertError("系统出现异常，请联系技术",$("body"));
				},
			});
	    }
		function getFilePath(){
	        var fileUploadPath = $("#declFile").val();
	    	if(fileUploadPath == null || fileUploadPath == ''){
	    		alert("请选择文件后上传");
	    		return;
	    	}
	    	$.ajaxFileUpload({
	    		url:'${marathon}/admin/system/orderMng/uploadFile.shtml', //你处理上传文件的服务端
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
		
		function test(){
			$.ajax({
				url : '${marathon}/admin/system/orderMng/test.shtml',
				dataType:"json",
				type:"post",
				success:function(result){
					window.location.href = "${marathon}/admin/system/orderMng/toGrogressbar.shtml";
				}
			})
		}
	</script>
</body>
</html>
