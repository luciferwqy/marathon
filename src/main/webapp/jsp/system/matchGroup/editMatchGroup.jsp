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
							<li class="active">赛事分组修改</li>
						</ol>
					</div>
				</div>
	
				<div class="row">
					<form role="form" id="matchForm" >
						<div class="col-lg-12">
								<div class="row">
									<div class="form-group">
										<label>所属赛事:</label> <input class="form-control" disabled="disabled" id="matchName" name="matchName" value="${groupEntity.matchName}"/>
										<input class="form-control" type="hidden" disabled="disabled" id="matchId" name="matchId" value="${groupEntity.matchId}"/>
										<input class="form-control" type="hidden" disabled="disabled" id="groupId" name="groupId" value="${groupEntity.groupId}"/>
									</div>
	
									<div class="form-group">
										<label>分组名称:</label> <input class="form-control" id="groupName" name="groupName" value="${groupEntity.groupName}">
									</div>
	
									<div class="form-group">
										<label>报名费￥:</label> <input class="form-control" id="fee" name="fee" value="${groupEntity.fee}" onkeyup="return ValidateNumber($(this),value)">
									</div>
	
									<div class="form-group">
										<label>外籍报名费$:</label> <input class="form-control" id="fee2" name="fee2" value="${groupEntity.fee2}" onkeyup="return ValidateNumber($(this),value)">
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
	var groupName = $("#groupName").val();
	if(groupName == null || groupName == ''){
		$.zzComfirm.alertError("请输入分组名称",$("body"));
		return;
	}
	var fee = $("#fee").val();
	if(fee == null || fee == ''){
		$.zzComfirm.alertError("请输入报名费用",$("body"));
		return;
	}
	var fee2 = $("#fee2").val();
	if(fee2 == null || fee2 == ''){
		$.zzComfirm.alertError("请输入外籍人员报名费用",$("body"));
		return;
	}
	var groupId=$("#groupId").val();
	sync = true;
	$.ajax({
		type : "post",
		data : {
			'groupId' : groupId,
			'groupName' : groupName,
			'fee' : fee,
			'fee2' : fee2
		},
		url : '${marathon}/admin/system/matchGroupMng/updateMatchGroup.shtml',
		dataType:'json',
		success : function(data) {
			sync = false;
			if (data.success) {
				$.zzComfirm.alertSuccess("修改成功！",$("body"),function(){
					window.parent.reloadTable();
					window.parent.closeEditModal();
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

function ValidateNumber(e, pnumber) {
	/*if (!/^\d+$/.test(pnumber)) {
	$(e).val(/^\d+/.exec($(e).val()));
	}*/
	var str = pnumber;  
	var len1 = str.substr(0,1);  
	var len2 = str.substr(1,1);  
	  
	//如果第一位是0，第二位不是点，就用数字把点替换掉  
	if(str.length > 1 && len1==0 && len2 != '.'){  
	    str = str.substr(1,1);  
	    $(e).val(str);
	}  
	  
	//第一位不能是.  
	if(len1=='.'){  
	    str = ''; 
	    $(e).val(str);
	}
	
	if (!/^\d*\.{0,2}\d{0,2}$/.test(pnumber)) {
		$(e).val(/^\d*\.{0,2}\d{0,2}$/.exec($(e).val()));
	}
}
</script>
</body>
</html>
