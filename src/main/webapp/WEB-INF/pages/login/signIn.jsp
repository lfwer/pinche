<%@page import="com.lfwer.common.CookieUtil"%>
<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/common/taglib.jsp"%>
<div id="headerInfo">
	<div class="row">
		<div class="col-xs-4">
			<span class="glyphicon glyphicon-chevron-left"
				onclick="history.back();" style="cursor: pointer;">&nbsp;</span>
		</div>
		<div class="col-xs-4 text-center">
			<span>登录</span>
		</div>
		<div class="col-xs-4">
			<div class="pull-right">
				<a href="javascript:forgetPwd();" style="font-size: 14px;">忘记密码&nbsp;</a>
			</div>
		</div>
	</div>
</div>
<div id="wrapperInfo">
	<div id="scrollerInfo">
		<div class="container">
			<form id="signForm" class="form-horizontal"
				action="${basePath }/login/signSubmit" method="post" style="margin-top: 20px;">
				<div class="form-group">
					<div class="col-xs-12">
				<div class="input-group ">
					<span class="input-group-addon"><span
						class="glyphicon glyphicon-user"></span></span><input type="text"
						id="username" name="username" class="form-control"
						style="border-radius: 0 3px 3px 0;" placeholder="用户名/手机号"
						data-bv-notempty data-bv-notempty-message="请填写用户名/手机号">
				</div>
				</div></div>
				<div class="form-group">
					<div class="col-xs-12">
					<div class="input-group ">
							<span class="input-group-addon"><span
								class="glyphicon glyphicon-lock"></span></span><input type="password"
								id="password" name="password" class="form-control"
								style="border-radius: 0 3px 3px 0;" placeholder="密码"
								data-bv-notempty data-bv-notempty-message="请填写密码">
						</div>
					</div>
				</div>
				<div class="form-group">
					<div class="col-xs-6">
						<button type="button" class="btn btn-warning btn-block"
						onclick="gotoUrl2('${basePath }/login/register');" >
						<span class="glyphicon glyphicon-new-window"></span> 注册
					</button>
					</div>
					<div class="col-xs-6">
						<button type="submit" class="btn btn-success btn-block">
						<span class="glyphicon glyphicon-check"></span> 登录
					</button>
					</div>
				</div>
				<div class="form-group text-center">
					<div class="col-xs-12">
						<div id="loginMsg" class="alert alert-danger" style="display: none;">
							<span id="loginMsgContent"></span>
						</div>
					</div>
				</div>
			</form>
		</div>
	</div>
</div>
<script type="text/javascript">
	var myScrollInfo;
	$(document).ready(function() {
		document.addEventListener('touchmove', function(e) {
			e.preventDefault();
		}, false);
		myScrollInfo = new IScroll('#wrapperInfo', {
			//probeType: 2, //probeType：1对性能没有影响。在滚动事件被触发时，滚动轴是不是忙着做它的东西。probeType：2总执行滚动，除了势头，反弹过程中的事件。这类似于原生的onscroll事件。probeType：3发出的滚动事件与到的像素精度。注意，滚动被迫requestAnimationFrame（即：useTransition：假）。  
			scrollbars : true,
			mouseWheel : true,
			interactiveScrollbars : true,
			shrinkScrollbars : 'scale',
			fadeScrollbars : true,
			preventDefault : false
		});
		$('#signForm').bootstrapValidator({
			message : '验证失败',
			live : 'disabled',
			feedbackIcons : {
				//valid : 'glyphicon glyphicon-ok',
				//invalid : 'glyphicon glyphicon-remove',
				validating : 'glyphicon glyphicon-refresh'
			}
		}).on('error.field.bv', function(e, data) {
			myScrollInfo.refresh();
		}).on('success.form.bv', function(e) {
			myScrollInfo.refresh();
			// Prevent form submission
			e.preventDefault();
			// Get the form instance
			var $form = $(e.target);
			// Get the BootstrapValidator instance
			var bv = $form.data('bootstrapValidator');
			// Use Ajax to submit form data
			$.post($form.attr('action'), $form.serialize(), function(result) {
				if (result.valid == true) {
					//浏览器中加入cookie
					<%-- $.cookie("<%=CookieUtil.cookieDomainName%>", result.message,{ expires: <%=CookieUtil.cookieMaxAge%>, path: '/' }); --%> 
					//location.reload();
					history.back();
				} else {
					$("#loginMsgContent").html(result.message);
					$("#loginMsg").show();
					// 2秒后隐藏提示信息
					window.setTimeout(function() {
						$("#loginMsg").hide();
					}, 2000);
				}
			}, 'json');
		});
	});
	
	function forgetPwd(){
		gotoUrl2('${basePath }/login/retrievePwd');
	}
	 
</script>