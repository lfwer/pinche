document.addEventListener('touchmove', function(e) {
	e.preventDefault();
}, false);
var user = getCurUser();
console.log("my:"+user+",time:"+new Date().getTime());
if (user) {
	$(".nickName").text(user.nickName);

	if (user.photoLarge) {
		$("#userPhotoBig_img").attr(
				"src",
				server.path + "/login/getPhoto?id=" + user.id + "&name="
						+ user.photoSmall);
		$("#userPhotoBig_a").attr(
				"href",
				server.path + "/login/getPhoto?id=" + user.id + "&name="
						+ user.photoLarge);
		$("#userPhotoBig_a").attr(
				"data-size",
				user.photoLarge.split("_")[1] + "x"
						+ user.photoLarge.split("_")[2]);
		initPhotoSwipeFromDOM('.userPhotoBig');
	}

	$("#userType").val(user.type);

	$('#userType').mobiscroll().select({
		theme : '', // Specify theme like: theme: 'ios' or omit
		// setting to use default
		mode : 'scroller', // Specify scroller mode like: mode:
		// 'mixed' or omit setting to use
		// default
		display : 'bottom', // Specify display mode like: display:
		// 'bottom' or omit setting to use
		// default
		lang : 'zh', // Specify language like: lang: 'pl' or omit
		// setting to use default
		headerText : function(valueText) {
			return "选择身份";
		},
		inputClass : 'selectC', // 为插件生成的input添加样式
		// placeholder: '请选择从事行业',//placeholder
		onSelect : function(valueText, inst) {
			var v;
			$("#userType > option").each(function() {
				if ($(this).text() == valueText) {
					v = $(this).val();
					return false;
				}
			});
			$.ajax({
				url : server.path + '/login/updateUser',
				data : 'type=type&value=' + v,
				type : 'post',
				success : function(data) {
					if (!data.valid) {
						alertMsg(data.message);
					} else {
						alertMsg(data.message, "success");
					}
				},
				error : function() {
					alertMsg('保存失败！');
				}
			});
		}
	});

	$(".my_li_1").click(function() {
		gotoUrl1(lfwer.rootName + '/pages/login/register2.html');
	});
	$(".my_li_2").click(function() {
		gotoUrl1(lfwer.rootName + '/pages/login/register3.html');
	});
	$(".my_li_3").click(function() {
		$('#userType').mobiscroll("show");
	});
	$(".my_li_4").click(function() {
		alertMsg("敬请期待");
	});

	$("#my_d2").show();

} else { // 登陆

	$("#my_btn_login").click(function() {
		gotoUrl1(lfwer.rootName + '/pages/login/signIn.html', 'signIn');
	});

	$("#my_d1").show();

}