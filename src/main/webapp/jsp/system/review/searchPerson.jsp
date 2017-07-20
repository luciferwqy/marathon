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
<link rel="stylesheet" href="${marathon}/css/core.css">
<script type='text/javascript' src="${reception}/js/jquery.min.js"></script>
<script type='text/javascript' src="${reception}/js/bootstrap.min.js"></script>
<script type="text/javascript" src="${reception}/js/bootstrap-datetimepicker.min.js"></script>
<script type="text/javascript" src="${reception}/js/bootstrap-datetimepicker.zh-CN.js"></script>
<script type="text/javascript" src="${reception}/js/bootstrap-datetimepicker.setting.js"></script>
<script type='text/javascript' src="${reception}/js/jquery-confirm.js"></script>
<script type="text/javascript" src="${reception}/js/manager.js"></script>
</head>
<body>
<div id="page-wrapper" style="padding-top:0">
	<div class="container-fluid">
		<div class="row">
			<form role="form" id="matchForm" >
				<div class="col-lg-12">
					<div class="row">
						<div class="form-group">
							<label>姓名:</label> <input class="form-control" disabled="disabled" value="${info.name}"/>
						</div>
						<div class="form-group">
							<c:if test="${info.nationality==1}">
								<label>国籍:</label> <input class="form-control" disabled="disabled" value="中国"/>
							</c:if>
							<c:if test="${info.nationality!=1}">
								<label>国籍:</label> <input class="form-control" disabled="disabled" value="外国"/>
							</c:if>
						</div>
					</div>
					<div class="row">
						<div class="form-group">
							<c:if test="${info.IDType==1}">
								<label>证件类型:</label> <input class="form-control" disabled="disabled" value="身份证"/>
							</c:if>
							<c:if test="${info.IDType==2}">
								<label>证件类型:</label> <input class="form-control" disabled="disabled" value="护照"/>
							</c:if>
						</div>
						<div class="form-group">
							<label>证件号码:</label> <input class="form-control" disabled="disabled" value="${info.IDNumber}"/>
						</div>
					</div>
					<div class="row">
						<div class="form-group">
							<c:if test="${info.sex==0}">
								<label>性别:</label> <input class="form-control" disabled="disabled" value="男"/>
							</c:if>
							<c:if test="${info.sex==1}">
								<label>性别:</label> <input class="form-control" disabled="disabled" value="女"/>
							</c:if>
						</div>
						<div class="form-group">
							<label>生日:</label> <input class="form-control" disabled="disabled" value="${info.birthDay}"/>
						</div>
					</div>
					<div class="row">
						<div class="form-group">
							<label>手机号码:</label> <input class="form-control" disabled="disabled" value="${info.mobilPhone}"/>
						</div>
						<div class="form-group">
							<label>邮箱地址:</label> <input class="form-control" disabled="disabled" value="${info.email}"/>
						</div>
					</div>
					<div class="row">
						<div class="form-group">
							<label>居住地址:</label> <input class="form-control" disabled="disabled" value="${info.residence}"/>
						</div>
						<div class="form-group">
							<label>通讯地址:</label> <input class="form-control" disabled="disabled" value="${info.address}"/>
						</div>
					</div>
					<div class="row">
						<div class="form-group">
							<label>血型:</label> <input class="form-control" disabled="disabled" value="${info.bloodType}"/>
						</div>
						<div class="form-group">
							<label>职业:</label> <input class="form-control" disabled="disabled" value="${info.occupation}"/>
						</div>
					</div>
					<div class="row">
						<div class="form-group">
							<label>教育程度:</label> <input class="form-control" disabled="disabled" value="${info.education}"/>
						</div>
						<div class="form-group">
							<label>在读大学:</label> <input class="form-control" disabled="disabled" value="${info.university}"/>
						</div>
					</div>
					<div class="row">
						<div class="form-group">
							<label>紧急联络人:</label> <input class="form-control" disabled="disabled" value="${info.urgentPerson}"/>
						</div>
						<div class="form-group">
							<label>紧急联络人电话:</label> <input class="form-control" disabled="disabled" value="${info.urgentPhone}"/>
						</div>
					</div>
					<div class="row">
						<div class="form-group">
							<label>前次马拉松名称:</label> <input class="form-control" disabled="disabled" value="${info.race.marathonName}"/>
						</div>
						<div class="form-group">
							<label>前次马拉松成绩:</label> <input class="form-control" disabled="disabled" value="${info.race.achievement}"/>
						</div>
					</div>
					<div class="row">
						<div class="form-group">
							<label>前次马拉松完赛证书:</label> <img src="${info.race.certificatePath}"/>
						</div>
					</div>
				</div>
			</form>
		</div>
	</div>
</div>
</body>
</html>
