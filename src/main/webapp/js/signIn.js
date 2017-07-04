var myScrollInfo;
$(document).ready(function() {
	document.addEventListener('touchmove', function(e) {
		e.preventDefault();
	}, false);
	myScrollInfo = new IScroll('#wrapperInfo', {
		// probeType: 2,
		// //probeType：1对性能没有影响。在滚动事件被触发时，滚动轴是不是忙着做它的东西。probeType：2总执行滚动，除了势头，反弹过程中的事件。这类似于原生的onscroll事件。probeType：3发出的滚动事件与到的像素精度。注意，滚动被迫requestAnimationFrame（即：useTransition：假）。
		scrollbars : true,
		mouseWheel : true,
		interactiveScrollbars : true,
		shrinkScrollbars : 'scale',
		fadeScrollbars : true,
		preventDefault : false
	});

	$("#signIn_register").click(function() {
		gotoUrl2(lfwer.rootName + '/pages/login/register.html');
	});

	$("#signForm").attr("action", server.path + "/login/signSubmit");

	$('#signForm').bootstrapValidator({
		message : '验证失败',
		//live : 'disabled',
		feedbackIcons : {
			// valid : 'glyphicon glyphicon-ok',
			// invalid : 'glyphicon glyphicon-remove',
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
		$.ajax({
			url : $form.attr('action'),
			type : 'post',
			data : $form.serialize(),
			success : function(result) {
				if (result.valid == true) {
					// 浏览器中加入cookie
					server.cookieName = result.message;
					$.cookie(server.cookieDomainName, result.message, {
						expires : server.cookieMaxAge,
						path : server.cookiePath
					});
					$("#loginMsgContent").html("登陆成功");
					$("#loginMsg").show();

					window.setTimeout(function() {
						history.back();
					}, 500);

				} else {
					$("#loginMsgContent").html(result.message);
					$("#loginMsg").show();
					// 2秒后隐藏提示信息
					window.setTimeout(function() {
						$("#loginMsg").hide();
					}, 2000);
				}
			},
			error : function() {
				alertMsg('登陆失败！');
			}
		});

	});
});

function forgetPwd() {
	gotoUrl2(lfwer.rootName + '/pages/login/retrievePwd.html');
}