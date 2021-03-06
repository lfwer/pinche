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
			<span>找回密码</span>
		</div>
		<div class="col-xs-2">
		</div>
	</div>
</div>
<div id="wrapperInfo2">
	<div id="scrollerInfo">
		<div class="container">
			<form id="retrievePwdForm" class="form-horizontal"
				action="${basePath }/login/retrievePwdSubmit" method="post">
				<div class="form-group">
					<div class="col-xs-12">
						<div style="display: table;">
							<span class="input-group-addon"><span
								class="glyphicon glyphicon-earphone"></span></span><input type="text"
								id="phone" name="phone" class="form-control"
								style="border-radius: 0 3px 3px 0;" placeholder="请填写注册时手机号"
								data-bv-notempty data-bv-notempty-message="请填写注册时手机号"
								data-bv-regexp data-bv-regexp-regexp="^1[3|4|5|8][0-9]\d{8}$"
								data-bv-regexp-message="请填写11位手机号">
						</div>
					</div>
				</div>
				<div class="form-group">
					<div class="col-xs-7">
						<div style="display: table;">
							<span class="input-group-addon"><span
								class="glyphicon glyphicon-envelope"></span></span><input type="text"
								id="smsCode" name="smsCode" class="form-control"
								style="border-radius: 0 3px 3px 0;" placeholder="手机验证码"
								data-bv-notempty data-bv-notempty-message="请填写手机验证码">
						</div>
					</div>
					<div class="col-xs-5">
						<button type="button" id="btnCode2" class="btn btn-success">获取验证码</button>
					</div>
				</div>
				<div class="form-group">
					<div class="col-xs-12">
						<button type="submit" class="btn btn-success btn-block">
							<span class="glyphicon glyphicon-forward"></span> 下一步
						</button>
					</div>
				</div>
			</form>
			<br>
			<div id="retrievePwdMsg" style="display: none;" class="alert alert-success">
				<span id="retrievePwdMsgContent"></span>
			</div>

		</div>
	</div>
</div>
<script type="text/javascript">
	yzmCanSend = false;
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
		$('#retrievePwdForm').bootstrapValidator({
			message : '验证失败',
			live : 'disabled',
			feedbackIcons : {
				//valid : 'glyphicon glyphicon-ok',
				//invalid : 'glyphicon glyphicon-remove',
				validating : 'glyphicon glyphicon-refresh'
			},
			fields : {
				phone : {
					validators : {
						remote : {
							url : lfwer.rootName + "/login/validatePhone?type=2&_r="+new Date().getTime(),
							type : "post",
							delay : 1000,
							message : "手机号未注册"
						}
					}
				}
			}
		}).on('error.field.bv', function(e, data) {
			myScrollInfo2.refresh();
			if (data.field == "phone") {
				yzmCanSend = false;
			}
		}).on(
			'success.field.bv',
			function(e, data) {
				myScrollInfo2.refresh();
				if (data.field == 'phone'
					&& $("#btnCode2").html() == "获取验证码") {
				if (yzmCanSend) {
					$('#btnCode2').html(
							"正在获取...");
					yzmCanSend = false;
					// 禁用发送验证码按钮
					$('#btnCode2').prop('disabled',true)
						.removeClass('btn-info').addClass('disabled');
					var phone = $("#phone").val();
					$.ajax({
						url : lfwer.rootName + "/login/genSMS",
						data : "phone="+ phone+ "&_r="+ new Date().getTime(),
						success : function(result) {
							if (result.valid == true) {
								$("#retrievePwdMsg").removeClass("alert-danger")
									.addClass("alert-success");
							} else {
								$("#retrievePwdMsg").removeClass("alert-success")
									.addClass("alert-danger");
							}
							$("#retrievePwdMsgContent").html(result.message);
							$("#retrievePwdMsg").show();
							// 4秒后隐藏提示信息
							window.setTimeout(function() {
								$("#retrievePwdMsg").hide();
							},
							4000);
							// 60秒后启用发送验证码按钮
							var i = 60;
							var resend = window.setInterval(function() {
								$('#btnCode2').html("重新发送("+ (i--)+ ")");
								if (i < 0) {window.clearInterval(resend);
									$('#btnCode2').html("获取验证码");
									$('#btnCode2').prop('disabled',false)
										.removeClass('disabled')
										.addClass('btn-info');
								}
							},
							1000);
						}
					});
				}
			}
		}).on('success.form.bv', function(e) {
			// Prevent form submission
			e.preventDefault();
			// Get the form instance
			var $form = $(e.target);
			// Get the BootstrapValidator instance
			var bv = $form.data('bootstrapValidator');
			// Use Ajax to submit form data
			$.post($form.attr('action'), $form.serialize(), function(result) {
				if (result.valid == true) {
					$("#viewDiv2").empty();
					$(".mask").show();
					$("#viewDiv2").slideDown(500,function() {
						$("#viewDiv2").load("${basePath}/login/retrievePwd2", function() {
							$(".mask").hide();
						});
					});
				} else {
					$("#retrievePwdMsg").removeClass("alert-success")
					.addClass("alert-danger");
					$("#retrievePwdMsgContent").html("验证码不正确");
					$("#retrievePwdMsg").show();
					// 4秒后隐藏提示信息
					window.setTimeout(function() {
						$("#retrievePwdMsg").hide();
					}, 4000);
				}
			}, 'json');
		});
		// 发送验证码按钮点击事件
		$("#btnCode2").click(function() {
			$('#retrievePwdForm').bootstrapValidator('validateField', 'phone');
			yzmCanSend = true;
		});
	});
</script>