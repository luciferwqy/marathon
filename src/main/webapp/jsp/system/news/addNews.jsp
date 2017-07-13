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
<link rel="stylesheet" href="${reception}/css/form-elements.css">
<link rel="stylesheet" href="${reception}/css/jquery-confirm.css">
<link rel="stylesheet" href="${marathon}/css/core.css">
<script type='text/javascript' src="${reception}/js/jquery.min.js"></script>
<script type='text/javascript' src="${reception}/js/bootstrap.min.js"></script>
<script type='text/javascript' src="${reception}/js/jquery-confirm.js"></script>
<script type="text/javascript" src="${reception}/js/manager.js"></script>
<script type="text/javascript" charset="utf-8" src="${marathon}/plugin/ueditor/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="${marathon}/plugin/ueditor/ueditor.all.js"> </script>
<script type="text/javascript" charset="utf-8" src="${marathon}/plugin/ueditor/ueditor.parse.js"> </script>
<script type="text/javascript" charset="utf-8" src="${marathon}/plugin/ueditor/lang/zh-cn/zh-cn.js"></script>
</head>
<body>
		<div id="page-wrapper">
			<div class="container-fluid">
				<div class="row">
					<div class="col-lg-12">
						<ol class="breadcrumb">
							<li><i class="fa fa-fighter-jet fa-fw"></i>系统管理</li>
							<li class="active">新闻发布</li>
						</ol>
					</div>
				</div>
				<div class="row">
					<form role="form" id="roleForm" >
						<div class="col-lg-12">
							<div class="resetBox">
								<div class="row">
									<div class="form-group">
										<label style="width:40px;">类型:</label>
										<select name="type" id="type"  class="form-control" style="width:calc(100% - 85px);">
													<option value="1">赛事新闻</option>
													<option value="0" selected="selected">官方公告</option>
										</select>
									</div>
									<div class="form-group">
										<label style="width:40px;">语言:</label>
										<select name="lang" id="lang"  class="form-control" style="width:calc(100% - 85px);">
													<option value="cn" selected="selected">中文</option>
													<option value="en">英文</option>
										</select>
									</div>
								</div>
								<div class="row">
									<div class="form-group">
										<label style="width:40px;">标题:</label>
										<input id="title" name="title" class="form-control" style="width:calc(100% - 85px);"/>
									</div>
								</div>
								<div class="row">
									<div class="form-group" style="width:100%">
										<SCRIPT id="myEditor" align="center" name="myEditor" type=text/plain></SCRIPT>
									</div>
								</div>
								<div class="row">
									<div class="form-group" style="width:100%">
										<input type = "button" class="btn" value="保存" onclick="submitForm()">
									</div>
								</div>
							</div>
						</div>
					</form>
				</div>
			</div>
		</div>
<script type="text/javascript">

var editor = UE.getEditor("myEditor");

function submitForm() {
		var title = $("#title").val();
		if(title == null || title == ''){
			$.zzComfirm.alertError("请输入标题",$("body"));
			return;
		}
		var context = editor.getContent();
		if(context == null || context == ''){
			$.zzComfirm.alertError("请输入内容",$("body"));
			return;
		}
		var type = $("#type").val();
		var lang = $("#lang").val();
		$.ajax({
			type : "post",
			data : {"title":title,"context":context,"type":type,"lang":lang},
			dataType : 'json',
			url : '${marathon}/admin/system/newsMng/saveNews.shtml',
			dataType:'json',
			success : function(data) {
				if (data.success) {
					$.zzComfirm.alertSuccess("新增成功！",$("body"),function(){
						location.reload()
					});
				} else {
					$.zzComfirm.alertError(data.msg,$("body"));
				}
			},
			error : function(data) {
				$.zzComfirm.alertError("操作失败，请联系管理员！",$("body"));
			}
		});
	};

</script>
</body>
</html>
