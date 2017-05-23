<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/common/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<%@include file="/common/resource.jsp"%>
<title>${sysName }&nbsp;-&nbsp;完善信息</title>
<style type="text/css">
.mini-textbox-border {
	border-radius: 0 4px 4px 0;
}
</style>
<script type="text/javascript">
	$(document)
			.ready(
					function() {
						/*
						$("input[name=type][value='${user.type}']").attr(
								"checked", 'checked');
						$("input[name=privacy][value='${user.privacy}']").attr(
								"checked", 'checked');
						$("input[name=sex][value='${user.sex}']").attr(
								"checked", 'checked');
						 */
						$("#btnBreak").click(function() {
							location.href = "${basePath}/index.jsp";
						});

						$('#myModal').modal('hide').css({
							'margin-top' : function() {
								return ($(this).height() / 2 - 100);
							}
						});

						/*
						$("input[type=radio]").iCheck({
							radioClass : 'iradio_square-red',
							increaseArea : '100%'
						});
						 */

						//$("#privacy").bootstrapSwitch();
						$('.form_date').datetimepicker({
							language : 'zh-CN',
							weekStart : 1,
							todayBtn : 1,
							autoclose : 1,
							todayHighlight : 1,
							startView : 2,
							minView : 2,
							forceParse : 0
						});

						$('#form1')
								.bootstrapValidator(
										{
											message : '验证失败',
											feedbackIcons : {
												//valid : 'glyphicon glyphicon-ok',
												//invalid : 'glyphicon glyphicon-remove',
												validating : 'glyphicon glyphicon-refresh'
											},
											fields : {
												nickName : {
													validators : {
														remote : {
															url : lfwer.rootName
																	+ "/login/validateNickName?_r="
																	+ new Date()
																			.getTime(),
															type : "post",
															delay : 1000,
															message : "该昵称已经被使用了，请换一个吧"
														}
													}
												}
											}
										}).on('error.field.bv',
										function(e, data) {

										}).on('success.field.bv',
										function(e, data) {

										});
					});
</script>
</head>
<body>
	<div class="panel panel-default" style="padding: 0; margin: 0;">
		<div class="panel-heading text-center">
			<span>注册成功，请完善个人信息</span>
		</div>
		<div class="panel-body">
			<form id="form1" class="form-horizontal" method="post"
				action="${basePath }/login/registerSubmit1">
				<div class="form-group">
					<div class="col-xs-12">
						<div style="display: table;">
							<span class="input-group-addon">身份<span
								class="lfwer-seize">⊙⊙</span> <span class="lfwer-required">*</span>
							</span>
							<div class="lfwer-addon-radio-checkbox-control input-group"
								style="border-radius: 0 3px 3px 0;">
								<div id="type" name="type" value="${user.type }"
									class="mini-radiobuttonlist" required="true"
									requiredErrorText="请选择身份"
									url="${basePath }/dict/getDictByType?type=USERTYPE"
									textField="name" valueField="id"></div>
							</div>
						</div>
					</div>
				</div>
				<div class="form-group">
					<div class="col-xs-12">
						<div class="input-group">
							<span class="input-group-addon">昵称<span
								class="lfwer-seize">⊙⊙</span> <span class="lfwer-required">*</span>
							</span><input type="text" id="nickName" name="nickName"
								class="form-control" style="border-radius: 0 3px 3px 0;"
								placeholder="请填写昵称" data-bv-notempty
								data-bv-notempty-message="请填写昵称" value="${user.nickName }"
								data-bv-stringlength data-bv-stringlength-max="12"
								data-bv-stringlength-message="昵称长度小于12">
						</div>
					</div>
				</div>
				<div class="form-group">
					<div class="col-xs-12">
						<div class="input-group">
							<span class="input-group-addon">个性签名 <span
								class="lfwer-seize">*</span>
							</span>
							<textarea class="form-control" name="sign" id="sign" rows="2"
								data-bv-stringlength data-bv-stringlength-max="80"
								placeholder="请填写个性签名" data-bv-stringlength-message="个性签名长度应小于80">${user.sign }</textarea>
						</div>
					</div>
				</div>
				<div class="form-group">
					<div class="col-xs-12">
						<div style="display: table;">
							<span class="input-group-addon">隐私<span
								class="lfwer-seize">⊙⊙</span> <span class="lfwer-required">*</span></span>
							<div class="lfwer-addon-radio-checkbox-control input-group"
								style="border-radius: 0 3px 3px 0;">
								<div id="privacy" name="privacy" value="${user.privacy }"
									class="mini-radiobuttonlist" required="true"
									requiredErrorText="请选择是否公开隐私"
									url="${basePath }/dict/getDictByType?type=PRIVACY"
									textField="name" valueField="id"></div>
							</div>
						</div>
					</div>
				</div>
				<div class="form-group">
					<div class="col-xs-12">
						<div style="display: table;">
							<span class="input-group-addon">性别 <span
								class="lfwer-seize">⊙⊙</span><span class="lfwer-required">*</span>
							</span>
							<div class="lfwer-addon-radio-checkbox-control input-group"
								style="border-radius: 0 3px 3px 0;">
								<div class="mini-radiobuttonlist"
									url="${basePath }/dict/getDictByType?type=SEX" textField="name"
									valueField="id"></div>
							</div>
						</div>
					</div>
				</div>
				<div class="form-group">
					<div class="col-xs-12">
						<div class="input-group date form_date"
							data-date-format="yyyy-mm-dd">
							<span class="input-group-addon">生日 <span
								class="lfwer-seize">⊙⊙*</span></span><input name="birthday"
								id="birthday" class="form-control" style="border-radius: 0;"
								readonly type="text" value="${user.birthday }"><span
								class="input-group-addon"><span
								class="glyphicon glyphicon-remove"></span></span><span
								class="input-group-addon"><span
								class="glyphicon glyphicon-calendar"></span></span>
						</div>
					</div>
				</div>
				<div class="form-group">
					<div class="col-xs-12">
						<div class="input-group">
							<span class="input-group-addon">年龄 <span
								class="lfwer-seize">年龄*</span></span> <input type="text" id="age"
								name="age" class="form-control"
								style="border-radius: 0 3px 3px 0;" placeholder="请填写年龄"
								data-bv-regexp data-bv-regexp-regexp="[0-9]+$"
								data-bv-regexp-message="年龄只能输入数字" data-bv-lessthan
								data-bv-lessthan-value="150" data-bv-lessthan-message="咱没那么大岁数吧"
								value="${user.age }">
						</div>
					</div>
				</div>
				<div class="form-group">
					<div class="col-xs-12">
						<div style="display: table;">
							<span class="input-group-addon">情感状态 <span
								class="lfwer-seize">*</span>
							</span>
							<div class="lfwer-addon-radio-checkbox-control input-group"
								style="border-radius: 0 3px 3px 0;">
								<div name="user.marry" class="mini-radiobuttonlist"
									url="${basePath }/dict/getDictByType?type=MARRY"
									textField="name" valueField="id" value="${user.marry }"></div>
							</div>
						</div>
					</div>
				</div>
				<div class="form-group">
					<div class="col-xs-12">
						<div style="display: table;">
							<span class="input-group-addon">兴趣爱好 <span
								class="lfwer-seize">*</span>
							</span>
							<div class="lfwer-addon-radio-checkbox-control input-group"
								style="border-radius: 0 3px 3px 0;">
								<div name="user.hobby" class="mini-checkboxlist"
									url="${basePath }/dict/getDictByType?type=HOBBY"
									textField="name" valueField="id" value="${user.hobby }"></div>
							</div>

						</div>
					</div>
				</div>
				<div class="form-group text-center">
					<div class="col-xs-12">
						<button type="submit" class="btn btn-success">完善信息</button>
						<button type="button" class="btn btn-warning"
							style="margin-left: 20px;" data-toggle="modal"
							data-target="#myModal">跳过</button>
					</div>
				</div>
			</form>
		</div>
	</div>


	<div class="modal fade" id="myModal" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-body">如果不完善个人信息，系统将无法匹配您感兴趣的 [车主] / [拼客] 哦
					^_^</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-primary" data-dismiss="modal">继续完善</button>
					<button type="button" class="btn btn-default" id="btnBreak">残忍跳过
					</button>
				</div>
			</div>
		</div>
	</div>





</body>
</html>