<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,Chrome=1">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
<script>
//只能微信内使用
/* var useragent = navigator.userAgent;
if (useragent.match(/MicroMessenger/i) != 'MicroMessenger') {
    // 这里警告框会阻塞当前页面继续加载
    alert('已禁止本次访问：您必须使用微信内置浏览器访问本页面！');
    // 以下代码是用javascript强行关闭当前页面
    var opened = window.open('about:blank', '_self');
    opened.opener = null;
    opened.close();
} */
</script>
<!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
<link rel="stylesheet"
	href="${basePath }/jslib/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet"
	href="${basePath }/jslib/bootstrapvalidator/css/bootstrapValidator.min.css" />
<link rel="stylesheet"
	href="${basePath }/jslib/bootstrapswitch/css/bootstrap-switch.min.css" />
<link rel="stylesheet"
	href="${basePath }/jslib/bootstrapdatetimepicker/css/bootstrap-datetimepicker.min.css" />
<link rel="stylesheet" href="${basePath }/jslib/icheck/skins/all.css" />
<link rel="stylesheet"
	href="${basePath }/jslib/miniui/themes/default/miniui.css" />
<link rel="stylesheet"
	href="${basePath }/jslib/miniui/themes/bootstrap/skin.css" />
<link rel="stylesheet" href="${basePath }/jslib/miniui/themes/icons.css" />
<link rel="stylesheet" href="${basePath }/css/miniui-diysize.css" />
<link rel="stylesheet" href="${basePath }/jslib/diyUpload/css/webuploader.css" />
<link rel="stylesheet" href="${basePath }/jslib/diyUpload/css/diyUpload.css" />
<link rel="stylesheet" href="${basePath }/jslib/Jcrop/css/jquery.Jcrop.min.css" />
<link rel="stylesheet" href="${basePath }/jslib/selectize/css/selectize.bootstrap3.css" />
<link rel="stylesheet" href="${basePath }/jslib/mobiscroll/css/mobiscroll.custom-2.17.0.min.css" />
<link rel="stylesheet" href="${basePath }/css/lfwer.css" />
<link rel="stylesheet" href="${basePath }/css/mask.css" />
<link rel="stylesheet" href="${basePath }/css/view.css">
<link rel="stylesheet" href="${basePath }/jslib/swiper/css/swiper.min.css" />

<script src="${basePath }/js/jquery-1.11.3.min.js"></script>
<script src="${basePath }/jslib/bootstrap/js/bootstrap.min.js"></script>
<script
	src="${basePath }/jslib/bootstrapvalidator/js/bootstrapValidator.min.js"></script>
<script
	src="${basePath }/jslib/bootstrapswitch/js/bootstrap-switch.min.js"></script>
<script
	src="${basePath }/jslib/bootstrapdatetimepicker/js/bootstrap-datetimepicker.min.js"></script>
<script
	src="${basePath }/jslib/bootstrapdatetimepicker/js/locales/bootstrap-datetimepicker.zh-CN.js"></script>
<script src="${basePath }/jslib/icheck/icheck.min.js"></script>
<script
	src="${basePath }/jslib/jqueryplaceholder/jquery.placeholder.min.js"></script>
<script src="${basePath }/jslib/miniui/miniui.js"></script>
<script src="${basePath }/jslib/diyUpload/js/webuploader.html5only.min.js"></script>
<script src="${basePath }/jslib/diyUpload/js/diyUpload.js"></script>
<script src="${basePath }/jslib/Jcrop/js/jquery.Jcrop.min.js"></script>
<script src="${basePath }/jslib/iscroll/iscroll-probe.js"></script>
<script src="${basePath }/jslib/selectize/js/standalone/selectize.min.js"></script>
<script src="${basePath }/jslib/mobiscroll/js/mobiscroll.custom-2.17.0.min.js"></script>
<script src="${basePath }/jslib/timeago/jquery.timeago.js"></script> 
<script src="${basePath }/jslib/timeago/locales/jquery.timeago.zh-CN.js"></script>
<script src="${basePath }/js/lfwer.js"></script>
<script src="${basePath }/js/img.js"></script>
<script src="${basePath }/jslib/swiper/js/swiper.min.js"></script> 
<script type="text/javascript">
	$(function() {
		$('input, textarea').placeholder();
	});
</script>