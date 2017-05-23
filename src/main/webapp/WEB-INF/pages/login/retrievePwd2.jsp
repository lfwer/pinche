<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/common/taglib.jsp"%>
 <div id="headerInfo">
	<div class="row">
		<div class="col-xs-2">
			<span class="glyphicon glyphicon-chevron-left"
				onclick="history.back();" style="cursor: pointer;">&nbsp;</span>
		</div>
		<div class="col-xs-8 text-center">
			<span>重设密码</span>
		</div>
		<div class="col-xs-2"></div>
	</div>
</div>
<div id="wrapperInfo2">
	<div id="scrollerInfo">
		<div class="container">  
			<form id="form1" class="form-horizontal" method="post"
				action="${basePath }/login/retrievePwd2Submit">
				 <div class="form-group">
					<div class="col-xs-12">
						<div style="display: table;">
							<span class="input-group-addon"><span
								class="glyphicon glyphicon-lock"></span></span><input type="password"
								id="password" name="password" class="form-control"
								style="border-radius: 0 3px 3px 0;" placeholder="请填写密码"
								data-bv-notempty data-bv-notempty-message="请填写密码"
								data-bv-stringlength data-bv-stringlength-min="6"
								data-bv-stringlength-max="20"
								data-bv-stringlength-message="密码长度必须在6至20位之间" data-bv-regexp
								data-bv-regexp-regexp="[a-zA-Z0-9]+$"
								data-bv-regexp-message="密码只能是数字或字母" data-bv-identical
								data-bv-identical-field="password2"
								data-bv-identical-message="两次输入密码不一致">
						</div>
					</div>
				</div>
				<div class="form-group">
					<div class="col-xs-12">
						<div style="display: table;">
							<span class="input-group-addon"><span
								class="glyphicon glyphicon-share-alt"></span></span><input
								type="password" id="password2" name="password2"
								class="form-control" style="border-radius: 0 3px 3px 0;"
								placeholder="请再次输入密码" data-bv-notempty
								data-bv-notempty-message="请再次输入密码" data-bv-identical
								data-bv-identical-field="password"
								data-bv-identical-message="两次输入密码不一致">
						</div>
					</div>
				</div>
				<div class="form-group">
					<div class="col-xs-12">
						<button type="submit" class="btn btn-success btn-block">
							重设密码
						</button>
					</div>
				</div>
			</form>
			<br>
			<div id="retrievePwd2Msg" style="display: none;" class="alert alert-success">
				<span id="retrievePwd2MsgContent"></span>
			</div>
		</div>
	</div>
	</div>
<script type="text/javascript">
	canSend2 = false;
	var myScrollInfo2;
	$(document).ready(function() {
		
		document.addEventListener('touchmove', function(e) {
			e.preventDefault();
		}, false);
		myScrollInfo2 = new IScroll('#wrapperInfo2', {
			//probeType: 2, //probeType：1对性能没有影响。在滚动事件被触发时，滚动轴是不是忙着做它的东西。probeType：2总执行滚动，除了势头，反弹过程中的事件。这类似于原生的onscroll事件。probeType：3发出的滚动事件与到的像素精度。注意，滚动被迫requestAnimationFrame（即：useTransition：假）。  
			scrollbars : true,
			mouseWheel : true,
			interactiveScrollbars : true,
			shrinkScrollbars : 'scale',
			fadeScrollbars : true,
			preventDefault : false
		});
		
		// 表单验证
		$('#form1').bootstrapValidator({
			message : '验证失败',
			live : 'disabled',
			feedbackIcons : {
				//valid : 'glyphicon glyphicon-ok',
				//invalid : 'glyphicon glyphicon-remove',
				validating : 'glyphicon glyphicon-refresh'
			}
		}).on('error.field.bv', function(e, data) {
			myScrollInfo2.refresh();
		}).on('success.form.bv', function(e) {
			myScrollInfo2.refresh();
			// Prevent form submission
			e.preventDefault();
			// Get the form instance
			var $form = $(e.target);
			// Get the BootstrapValidator instance
			var bv = $form.data('bootstrapValidator');
			// Use Ajax to submit form data
			$.post($form.attr('action'), $form.serialize(), function(result) {
				if (result.valid == true) {
					$("#retrievePwd2Msg").removeClass('alert-danger').addClass('alert-success');
					window.setTimeout(function(){
						history.back();
					}, 2000);
				} else {
					$("#retrievePwd2Msg").removeClass('alert-success').addClass('alert-danger');
					window.setTimeout(function(){
						$("#viewDiv2").empty();
						$(".mask").show();
						$("#viewDiv2").slideDown(500,function() {
							$("#viewDiv2").load("${basePath}/login/retrievePwd", function() {
								$(".mask").hide();
							});
						});
					}, 2000);
				}
				$("#retrievePwd2MsgContent").html(result.message);
				$("#retrievePwd2Msg").show();
			}, 'json');
		});
	});
</script>