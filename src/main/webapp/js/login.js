var canSend = false;
$(document)
		.ready(
				function() {
					// 表单验证
					$('#form1')
							.bootstrapValidator(
									{
										message : '验证失败',
										live : 'disabled',
										feedbackIcons : {
											valid : 'glyphicon glyphicon-ok',
											invalid : 'glyphicon glyphicon-remove',
											validating : 'glyphicon glyphicon-refresh'
										},
										fields : {
											smsCode : {
												validators : {
													remote : {
														url : lfwer.rootName
																+ "/login/validateSMS",
														type : "post",
														data : {
															phone : function(
																	validator) {
																return $(
																		"#phone")
																		.val();
															}
														},
														delay : 1000,
														message : "手机验证码不正确"
													}
												}
											}
										}
									})
							.on('error.field.bv', function(e, data) {
								if (data.field == "phone") {
									// $('#btnCode').prop('disabled',
									// true).removeClass(
									// 'btn-success').addClass('disabled');
									canSend = false;
								}

							})
							.on(
									'success.field.bv',
									function(e, data) {
										if (data.field == 'phone'
												&& $("#btnCode").html() == "获取验证码") {
											// The postal code is valid
											// $('#btnCode').prop('disabled',
											// false).removeClass(
											// 'disabled').addClass('btn-success');
											 
											if (canSend) {
												$('#btnCode').html("正在获取...");
												canSend = false;
												// 禁用发送验证码按钮
												$('#btnCode').prop('disabled',
														true).removeClass(
														'btn-success')
														.addClass('disabled');

												var phone = $("#phone").val();
												$
														.ajax({
															url : lfwer.rootName
																	+ "/login/genSMS",
															data : "phone="
																	+ phone
																	+ "&_r="
																	+ new Date()
																			.getTime(),
															success : function(
																	result) {
																if (result.type == "success") {
																	$("#msg")
																			.removeClass(
																					"alert-danger")
																			.addClass(
																					"alert-success");
																} else {
																	$("#msg")
																			.removeClass(
																					"alert-success")
																			.addClass(
																					"alert-danger");

																}
																$("#msgContent")
																		.html(
																				result.info);
																$("#msg")
																		.show();
																// 4秒后隐藏提示信息
																window
																		.setTimeout(
																				function() {
																					$(
																							"#msg")
																							.hide();
																				},
																				4000);

																// 60秒后启用发送验证码按钮
																var i = 60;

																var resend = window
																		.setInterval(
																				function() {
																					$(
																							'#btnCode')
																							.html(
																									"重新发送("
																											+ (i--)
																											+ ")");
																					if (i < 0) {
																						window
																								.clearInterval(resend);
																						$(
																								'#btnCode')
																								.html(
																										"获取验证码");
																						$(
																								'#btnCode')
																								.prop(
																										'disabled',
																										false)
																								.removeClass(
																										'disabled')
																								.addClass(
																										'btn-success');
																						canSend = true;
																					}
																				},
																				1000);
															}
														});
											}

										}
									});
					// 发送验证码按钮点击事件
					$("#btnCode").click(
							function() {
								$('#form1').bootstrapValidator('validateField',
										'phone');
								canSend = true;
							});
				});
