<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="java.util.Date"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<c:set var="basePath" value="${pageContext.request.contextPath}" />
<c:set var="sysName" value="邯郸拼车" />
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
<!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
<title>${sysName }-登录</title>

<link rel="stylesheet"
	href="${basePath }/jslib/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet"
	href="${basePath }/jslib/bootstrapvalidator/css/bootstrapValidator.css" />
<link rel="stylesheet" href="${basePath }/css/signin.css">

<script src="${basePath }/js/jquery-1.11.3.min.js"></script>
<script src="${basePath }/jslib/bootstrap/js/bootstrap.min.js"></script>
<script
	src="${basePath }/jslib/bootstrapvalidator/js/bootstrapValidator.js"></script>

<style type="text/css">
html, body {
	margin: 0;
	padding: 0;
}

.panel-footer {
	position: fixed;
	bottom: 0;
	width: 100%;
}

.panel-heading {
	position: fixed;
	top: 0;
	width: 100%;
}

.panel-body {
	position: fixed;
	width: 100%;
}

#myTab {
	width: 100%;
	text-align: center;
}

#myTab li {
	width: 50%;
}

@media all and (orientation : landscape) { /*　　这是匹配横屏的状态，横屏时的css代码　　*/
}

@media all and (orientation : portrait) { /*　　这是匹配竖屏的状态，竖屏时的css代码　　*/
}
</style>

<script type="text/javascript">
	function fullScreen() {
		// 		$(".panel-body").height(
		// 				$(document).height() - $(".panel-heading").height()
		// 						- $(".panel-footer").height() - 75);
		$(".panel-body").css("top", $(".panel-heading").height());
	}
	$(document).ready(function() {

	});
</script>
</head>
<body>
	<ul id="myTab" class="nav nav-tabs">
		<li class="active"><a href="#a" data-toggle="tab"> 找车子 </a></li>
		<li><a href="#b" data-toggle="tab">找拼客</a></li>
	</ul>
	<div id="myTabContent" class="tab-content">
		<div class="tab-pane fade in active" id="a">
			<div class="table-responsive">
				<table class="table table-hover table-condensed">
					<colgroup>
						<col width="100%">
						<col style="white-space: nowrap;">
					</colgroup>
					<thead>
						<tr>
							<th>标题</th>
							<th>出发日期</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td>从邯郸回峰峰</td>
							<td>09/11 12:24</td>
						</tr>
						<tr>
							<td>从邯郸回峰峰</td>
							<td>09/11 12:24</td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
		<div class="tab-pane fade" id="b">
			<div class="table-responsive">
				<table class="table table-hover table-condensed">
					<colgroup>
						<col width="100%">
						<col style="white-space: nowrap;">
					</colgroup>
					<thead>
						<tr>
							<th>标题</th>
							<th>出发日期</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td>从峰峰回邯郸</td>
							<td>09/11 12:24</td>
						</tr>
						<tr>
							<td>从峰峰回邯郸</td>
							<td>09/11 12:24</td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
	</div>
</body>
</html>