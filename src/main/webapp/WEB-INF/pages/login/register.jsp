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
			<span>注册</span>
		</div>
		<div class="col-xs-2"></div>
	</div>
</div>
<div id="wrapperInfo2">
	<div id="scrollerInfo">
		<div class="container">
			<form id="registerForm" class="form-horizontal" method="post"
				action="${basePath }/login/registerSubmit" target="targetFrame">
				<div class="form-group">
					<div class="col-xs-12">
						<div style="display: table;">
							<span class="input-group-addon"><span
								class="glyphicon glyphicon-user"></span></span><input type="text"
								id="username" name="username" class="form-control"
								style="border-radius: 0 3px 3px 0;" placeholder="请填写用户名"
								data-bv-notempty data-bv-notempty-message="请填写用户名"
								data-bv-regexp data-bv-regexp-regexp="[a-zA-Z0-9]+$"
								data-bv-regexp-message="用户名只能是数字或字母" data-bv-stringlength
								data-bv-stringlength-min="5" data-bv-stringlength-max="12"
								data-bv-stringlength-message="用户名长度必须在5至12位之间">
						</div>
					</div>
				</div>
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
						<div style="display: table;">
							<span class="input-group-addon"><span
								class="glyphicon glyphicon-earphone"></span></span><input type="text"
								id="phone" name="phone" class="form-control"
								style="border-radius: 0 3px 3px 0;" placeholder="请填写手机号"
								data-bv-notempty data-bv-notempty-message="请填写手机号"
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
						<button type="button" id="btnCode" class="btn btn-info">获取验证码</button>
					</div>
				</div>
				<div class="form-group">
					<div class="col-xs-12">
						<button type="submit" class="btn btn-lg btn-success btn-block">
							<span class="glyphicon glyphicon-leaf"></span> 注 册
						</button>
					</div>
				</div>
			</form>
			<br>
			<div id="registerMsg" style="display: none;" class="alert alert-success">
				<span id="registerMsgContent"></span>
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
		$('#registerForm').bootstrapValidator({
			message : '验证失败',
			live : 'disabled',
			feedbackIcons : {
				//valid : 'glyphicon glyphicon-ok',
				//invalid : 'glyphicon glyphicon-remove',
				validating : 'glyphicon glyphicon-refresh'
			},
			fields : {
				username : {
					validators : {
						remote : {
							url : lfwer.rootName + "/login/validateUsername?type=1&_r="+new Date().getTime(),
							type : "post",
							delay : 1000,
							message : "用户名已注册"
						}
					}
				},
				phone : {
					validators : {
						remote : {
							url : lfwer.rootName + "/login/validatePhone?type=1&_r="+new Date().getTime(),
							type : "post",
							delay : 1000,
							message : "手机号已注册"
						}
					}
				},
				smsCode : {
					validators : {
						remote : {
							url : lfwer.rootName + "/login/validateSMS",
							type : "post",
							data : {
								phone : function(validator) {
									return $("#phone").val();
								}
							},
							delay : 1000,
							message : "手机验证码不正确"
						}
					}
				}
			}
		}).on('error.field.bv', function(e, data) {
			myScrollInfo2.refresh();
			if (data.field == "phone") {
				canSend2 = false;
			}
		}).on('success.field.bv', function(e, data) {
				myScrollInfo2.refresh();
				if (data.field == 'phone'
					&& $("#btnCode").html() == "获取验证码") {
				if (canSend2) {
					$('#btnCode').html(
							"正在获取...");
					canSend2 = false;
					// 禁用发送验证码按钮
					$('#btnCode').prop('disabled',true)
						.removeClass('btn-info').addClass('disabled');
					var phone = $("#phone").val();
					$.ajax({
						url : lfwer.rootName + "/login/genSMS",
						data : "phone="+ phone+ "&_r="+ new Date().getTime(),
						success : function(result) {
							if (result.valid == true) {
								$("#registerMsg").removeClass("alert-danger")
									.addClass("alert-success");
							} else {
								$("#registerMsg").removeClass("alert-success")
									.addClass("alert-danger");
							}
							$("#registerMsgContent").html(result.message);
							$("#registerMsg").show();
							// 4秒后隐藏提示信息
							window.setTimeout(function() {
								$("#registerMsg").hide();
							},
							4000);

							// 60秒后启用发送验证码按钮
							var i = 60;
							var resend = window.setInterval(function() {
								$('#btnCode').html("重新发送("+ (i--)+ ")");
								if (i < 0) {window.clearInterval(resend);
									$('#btnCode').html("获取验证码");
									$('#btnCode').prop('disabled',false)
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
					$("#registerMsg").removeClass('alert-danger').addClass('alert-success');
					window.setTimeout(function(){
						history.back();
					}, 2000);
				} else {
					$("#registerMsg").removeClass('alert-success').addClass('alert-danger');
				}
				$("#registerMsgContent").html(result.message);
				$("#registerMsg").show();
			}, 'json');
		});;
		// 发送验证码按钮点击事件
		$("#btnCode").click(function() {
			$("#registerForm").data('bootstrapValidator').resetForm();
			$('#registerForm').bootstrapValidator('validateField', 'phone');
			myScrollInfo2.refresh();
			canSend2 = true;
		});
	});
</script>