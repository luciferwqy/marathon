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
<link rel="stylesheet" href="${reception}/css/carrier.css">
<link rel="stylesheet" href="${reception}/css/jquery-confirm.css">
<link rel="stylesheet" href="${marathon}/css/core.css">
<script type="text/javascript" src="${reception}/js/jquery.min.js"></script>
<script type="text/javascript" src="${reception}/js/bootstrap.min.js"></script>
<script type="text/javascript" src="${reception}/js/pagination.js"></script>
<script type="text/javascript" src="${reception}/js/jquery-confirm.js"></script>
<script type="text/javascript" src="${reception}/js/manager.js"></script>
<script type="text/javascript" src="${reception}/js/distpicker.data.js"></script>
<script type="text/javascript" src="${reception}/js/distpicker.js"></script>
<script type="text/javascript" src="${reception}/js/carrier.js"></script>
</head>

<body>
	<div id="page-wrapper">

		<div class="container-fluid">
			<!-- Page Heading -->
			<div class="row">
				<div class="col-lg-12">
					<ol class="breadcrumb">
						<li><i class="fa fa-book fa-fw"></i>订单查询</li>
						<li class="active"><i class="fa fa-bookmark fa-fw"></i>订单详情</li>
					</ol>
				</div>
			</div>
			<div class="row">
			</div>

			<div class="row">
				<div class="col-lg-12">
					<div class="panel panel-default">
						<div class="panel-heading">
							<h3 class="panel-title panel-h3">
								<i class="fa fa-bar-chart-o fa-fw"></i>商品明细
							</h3>
						</div>
						<div class="panel-body">
							<table id="orderDetailTable" class="table table-hover">
								<thead>
									<tr>
										<th>商品名称</th>
										<th>数量</th>
										<th>尺寸</th>
										<th>颜色</th>
										<th>单价</th>
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
		<div class="row">
			<div class="col-lg-12">
				<div class="panel-heading">
					<h3 class="panel-title panel-h3">
						<i class="fa fa-bar-chart-o fa-fw"></i>物流信息
					</h3>
				</div>
				<div data-mohe-type="kuaidi_new" class="g-mohe " id="mohe-kuaidi_new">
			        <div id="mohe-kuaidi_new_nucom">
			            <div class="mohe-wrap mh-wrap">
			                <div class="mh-cont mh-list-wrap mh-unfold">
			                    <div class="mh-list">
			                    </div>
			                </div>
			            </div>
		        	</div>
	        	</div>
	    	</div>
    	</div>
	</div>
	<script type="text/javascript">
		/**
		 * 初始化分页信息
		 */
		var options = {
			queryForm : ".query",
			url : "${marathon}/admin/system/orderMng/goodsList.shtml?orderId=${orderId}",
			numPerPage : "10",
			currentPage : "",
			index : "1",
			callback : rebuildTable
		}
		
		var expressId = "${expressId}";
		var carrierName = "${carrierName}";

		$(function() {
			$(".pagination-nav").pagination(options);
		})
		
		/**
		*重构物流信息
		*/
		function rebuildUl(data){
			$(".mh-list").html("");
			if(data == null || data.traces == null || data.traces.length ==0){
				$.zzComfirm.alertError("没有物流信息",$("body"));
				return;
			}
			var $str = "<ul>";
			for (var i = (data.traces.length-1); i>=0; i--){
				if(i == (data.traces.length-1)){
					$str += "<li class=\"first\">";
				}else {
					$str += "<li>";
				}
				$str += "<p>"+data.traces[i].accepttime+"</p>";
				$str += "<p>"+data.traces[i].acceptstation+"</p>"
				$str += "<span class=\"before\"></span><span class=\"after\"></span>";
				if(i == (data.traces.length-1)){
					$str += "<i class=\"mh-icon mh-icon-new\"></i>";
				}
				$str += "</li>";
			}
			$str += "</ul>";
			$(".mh-list").html($str);
		}

		/**
		 * 重构table
		 */
		function rebuildTable(data) {
			$("#orderDetailTable tbody").html("");

			if (data == null || data.length == 0) {
				return;
			}

			var list = data.obj;

			//跳过查询
			if (data.errTrace == "Search.") {
				return;
			} else if (data.errTrace != null) {
				$.zzComfirm.alertError(data.errTrace);
				return;
			}

			if (list == null || list.length == 0) {
				$.zzComfirm.alertError("没有查到数据",$("body"));
				return;
			}

			var str = "";
			var strCom = "";
			for (var i = 0; i < list.length; i++) {
				str += "<tr><td align='left'>" + list[i].goodsName;
				str += "</td><td align='left'>" + list[i].quantity;
				str += "</td><td align='left'>" + list[i].size;
				str += "</td><td align='left'>" + list[i].color;
				str += "</td><td align='left'>" + list[i].amount/100;
				str += "</td></tr>";
			}

			$("#orderDetailTable tbody").html(str);
			
			if (expressId != null && carrierName != null){
				var parms = {
					url : "${marathon}/express/getRoute.shtml",
					expressId : expressId,
					carrierName : carrierName,
					callback : rebuildUl
				}
				$(".mh-list").getCarrier(parms);
			}
		}
	</script>
</body>
</html>
