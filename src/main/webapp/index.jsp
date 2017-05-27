<%@page import="com.lfwer.common.Util"%>
<%@page import="java.util.Map"%>
<%@page import="com.lfwer.common.TimeUtil"%>
<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/common/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<script type="text/javascript">
	if(location.href.indexOf("#")>0){
		location.href=location.href.substring(0,location.href.indexOf("#"));
	}
</script>
<%@include file="/common/resource.jsp"%>
<%SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); %>
<title>${sysName }</title>
<style type="text/css">
* {
	-webkit-box-sizing: border-box;
	-moz-box-sizing: border-box;
	box-sizing: border-box;
}

html, body {
    position: relative;
    height: 100%;
    overflow: hidden;
    padding: 0;
	margin: 0;
	border: 0;
}

body {
    background: #fff;
    font-family: Helvetica Neue, Helvetica, Arial, sans-serif;
    font-size: 14px;
    color:#000;
    margin: 0;
    padding: 0;
}

#header {
	position: absolute;
	z-index: 2;
	top: 0;
	left: 0;
	width: 100%;
	padding: 5px;
	border-bottom: 1px solid #E7E4E4;
}

#footer {
	position: absolute;
	z-index: 2;
	bottom: 0;
	left: 0;
	width: 100%;
	padding: 6px 0 5px 0 ;
	border-top: 1px solid #E7E4E4;
	font-size: 14px;
}

.swiper-container2 {
	position: absolute;
    width: 100%;
    top: 45px;
	bottom: 54px;
}
.swiper-container {
	position: absolute;
	width: 100%;
	top: 45px;
	bottom: 52px;
}

.swiper-slide {
    font-size: 18px;
    height: auto;
    -webkit-box-sizing: border-box;
    box-sizing: border-box;
}

.slide {
    padding: 30px;
}

.wrapper {
	position: absolute;
	z-index: 1;
	top: 0;
	bottom:0;
	left: 0;
	width: 100%;
	overflow: hidden;
}
 

.ul-main {
	list-style: none;
	padding: 0;
	margin: 0;
	width: 100%;
	text-align: left;
}

.ul-main > li {
	padding: 2px 10px;
	border-bottom: 1px solid #ccc;
	border-top: 1px solid #fff;
	font-size: 14px;
	cursor: pointer;
}

.scroller > table {
	width: 100%;
}

.lfwer-btn-check {
	border: 0;
	color: #337ab7;
	cursor: pointer;
}

.lfwer-btn-uncheck {
	border: 0;
	color: gray;
	cursor: pointer;
}

.lfwer-center {
	width: auto;
	display: table;
	margin-left: auto;
	margin-right: auto;
}

.lfwer-title {
	font-weight: bolder;
}

.liclick {
	color: gray;
}
.list-table{
	padding: 0;
	margin: 0;
	line-height: 18px;
	width: 100%;
}

#viewDiv{
	z-index: 3;
	position: absolute;
	height: 100%;
	width: 100%;
	display: none;
	background: white;
	bottom: 0;
	overflow: hidden;
}

#viewDiv2{
	z-index: 4;
	position: absolute;
	height: 100%;
	width: 100%;
	display: none;
	background: white;
	bottom: 0;
	overflow: hidden;
}

#viewMy{
	width: 100%;
	height:auto;
	overflow: hidden;
}

#operDiv{
	z-index: 5;
	position: absolute;
	height: 100%;
	width: 100%;
	display: none;
	background: white;
	bottom: 0;
	overflow: hidden;
}

.modal{
	font-size: 18px;
	line-height: 28px;
}

.callDiv{
	font-size: 32px;
	position: absolute;
	right: 5px;
	bottom: 20px;
	z-index: 2;
	line-height: 48px; 
}

.callDiv > span{
	border:1px #EEEEEE solid;
	padding: 4px;
	color: white;
	background: gray;
	
}
</style>

<script type="text/javascript">
	
	
	window.onpopstate = function(event) {
		var state = event.state;
		var name = state.name;
		var value = state.value;
		$(".mask").hide();
		if (name == "forward") {
			$(".modal").modal('hide');
			$(".mbsc-mobiscroll").hide();
			closeViewDiv();
		}else if(name == "forward2"){
			$(".modal").modal('hide');
			$(".mbsc-mobiscroll").hide();
			closeViewDiv2();
		} else {
			alertMsg('参数错误:'+name);
		}
	};

	var zhaoche = true;
	var _date1 = "<%=sdf.format(new Date())%>";
	var _date2="<%=sdf.format(new Date())%>";
	var page1 = 1;
	var page2 = 1;
	var myScroll1 = null;
	var myScroll2 = null;
	var myScroll3 = null;
	var myScroll4 = null;
	var loading1 = false;
	var loading2 = false;
	loadUp1 = true;
	loadUp2 = true;
	var swiper,swiper2;
	var firstIn =true;
	var delId,delflag;
	var curUser;
	var loadMy = false;
	var carOwnerInfoLi = '<li onclick="viewCarOwnerInfo(this,\'#id\')" style="padding:4px 4px 6px 4px;">'
		+ '<table class="list-table"><tr><td rowspan="4" width="80" style="padding-left: 5px;">'
		+ '<img src="${basePath }/login/getPhoto?id=#userId&name=#userPhotoSmall" '
		+ 'onerror="nohead(this);" class="img-circle" style="width: 60px; height: 60px;"></td>'
		+ '<td>#form → #to</td></tr>'
		+ '<tr><td>途经 #via</td></tr>'
		+ '<tr><td>时间 #date</td></tr>'
		+ '<tr><td>车型 #carType，#carStyle，#carColor</td></tr>'
		+ '<tr><td style="padding: 0 10px 0 0;">'
		+ '<span class="#class" style="float: left;">#sex</span>'
		+ '<span style="float: right;" class="#class">#age</span></td>'
		+ '<td align="left">空位 #pnum'
		+ '<span class="#class" style="float: right;'
		+ 'cursor: pointer;" onclick="viewInfo()">#nickName</span></td></tr></table></li>';
		
	var pinkerInfoLi = '<li onclick="viewPinkerInfo(this,\'#id\')" style="padding:4px 4px 6px 4px;">'
			+ '<table class="list-table"><tr>'
			+ '<td rowspan="3" width="80" style="padding-left: 5px;">'
			+ '<img src="${basePath }/login/getPhoto?id=#userId&name=#userPhotoSmall" '
			+ 'onerror="nohead(this);" class="img-circle" style="width: 60px; height: 60px;"></td>'
			+ '<td>从 #form</td></tr>'
			+ '<tr><td>到 #to</td></tr>'
			+ '<tr><td>出发 #date</td></tr><tr><td style="padding: 0 10px 0 0;">'
			+ '<span class="#class" style="float: left;">#sex</span>'
			+ '<span style="float: right;" class="#class">#age</span></td>'
			+ '<td>乘员 #pnum人'
			+ '<span class="#class" style="float: right;cursor: pointer;"'
			+ ' onclick="viewInfo()">#nickName</span>'
			+ '</td></tr></table></li>';
			
	$(document).ready(function() {
		zhaoche = true;
		$("#switchInfo").bootstrapSwitch("state",true);
		$(".mask").show();
		$.ajax({
			url:"${basePath}/login/getCurUser",
			async:true,
			success:function(data){
				$(".mask").hide();
				if(data){
					curUser = data;
					if(curUser.type == "1"){
						zhaoche = false;
						$("#switchInfo").bootstrapSwitch("state",false);
					}
				}
			},
			error:function(){
				$(".mask").hide();
			}
		});
		
		
		
		
		$("#switchInfo").on('switchChange.bootstrapSwitch',function(event, state) {
			zhaoche = state;
			info(false);
			if (zhaoche) {
				$("#addInfoName").html("&nbsp;车主发布");
				
				swiper.slideTo(0,0,false);
			} else {
				$("#addInfoName").html("&nbsp;乘客发布");
				swiper.slideTo(1,0,false); 
				if(firstIn){
					loadPinkerInfo(page2);
					firstIn = false;
				}
			}
		});
		
	  	swiper = new Swiper('.swiper-container', {
	  		mousewheelControl: true,
	  		spaceBetween: 60,
	  		slidesPerView: 1,
	  		onSlideChangeEnd: function(s){
	  			if(s.activeIndex==0){
	  				$("#switchInfo").bootstrapSwitch("state",true);
	  				info(false);
	  			}else if(s.activeIndex==1){
	  				$("#switchInfo").bootstrapSwitch("state",false);
	  				info(false);
	  			}else if(s.activeIndex==2){
	  				friend(false);
	  			}else if(s.activeIndex==3){
	  				my(false);
	  			}
	  		}
    	});
	  	
	  	/* swiper2 = new Swiper('.swiper-container2', {
	  		direction: 'vertical'
    	}); */
	   
		document.addEventListener('touchmove', function(e) {
			e.preventDefault();
		}, false);
	  	
	  	var setting = {
			probeType : 2, //probeType：1对性能没有影响。在滚动事件被触发时，滚动轴是不是忙着做它的东西。probeType：2总执行滚动，除了势头，反弹过程中的事件。这类似于原生的onscroll事件。probeType：3发出的滚动事件与到的像素精度。注意，滚动被迫requestAnimationFrame（即：useTransition：假）。  
			scrollbars : true,//有滚动条  
			mouseWheel : true,//允许滑轮滚动  
			fadeScrollbars : true,//滚动时显示滚动条，默认影藏，并且是淡出淡入效果  
			bounce : true,//边界反弹  
			interactiveScrollbars : true,//滚动条可以拖动  
			shrinkScrollbars : 'scale',// 当滚动边界之外的滚动条是由少量的收缩。'clip' or 'scale'. 
			//preventDefault : false
			click : true,// 允许点击事件  
			keyBindings : true,//允许使用按键控制  
			momentum : true // 允许有惯性滑动  
		};
	  	
	  	myScroll1 = new IScroll("#wrapper1", setting);
	  	
	  	myScroll1.on("scrollEnd",function() {
			if (!loading1) {
				if ($("#pullDown1").html() == ("松开刷新 O(∩_∩)O")) {//下拉刷新
					page1 = 1;
					$("#pullDown1").html(
							"正在加载……");
					$("#pullDown1").show();
					loadUp1 = true;
					loadCarOwnerInfo(page1);
					 
				} else if ($("#pullDown1")
						.html() == ("↑ 下拉刷新")) {
					$("#pullDown1").empty().hide();
				} else if (this.y == this.maxScrollY && loadUp1) {
					$("#pullUp1").show();
					page1++;
					loadCarOwnerInfo(page1);
				} 

			}

		});

		myScroll1.on("scroll", function() {
			var y = this.y;
			if (this.y >= 30) {
				$("#pullDown1").html("松开刷新 O(∩_∩)O").show();
			} else if (this.y > 0 && this.y < 30) {
				$("#pullDown1").html("↑ 下拉刷新").show();
			}
		});
		
		myScroll2 = new IScroll("#wrapper2", setting);
		
	  	myScroll2.on('scrollEnd',function() {
			if (!loading2) {
				if ($("#pullDown2").html() == ("松开刷新 O(∩_∩)O")) {//下拉刷新
					page2 = 1;
					$("#pullDown2").html(
							"正在加载……");
					$("#pullDown2").show();
					loadUp2 = true;
					loadPinkerInfo(page2);
				} else if ($("#pullDown2").html() == ("↑ 下拉刷新")) {
					$("#pullDown2").empty().hide();
				} else if (this.y == this.maxScrollY && loadUp2) {
					$("#pullUp2").show();
					page2++;
					loadPinkerInfo(page2);
				} 
			}

		});

		myScroll2.on("scroll", function() {
			var y = this.y;
			if (this.y >= 30) {
				$("#pullDown2").html("松开刷新 O(∩_∩)O").show();
			} else if (this.y > 0 && this.y < 30) {
				$("#pullDown2").html("↑ 下拉刷新").show();
			}
		});
		 
		$("#addInfo").click(function() {
			if (zhaoche) {
				if(curUser.id){//判断是否登录
					if(curUser.carType){
						gotoUrl1('${basePath}/carOwnerInfo/addCarOwnerInfo');
					}else{
						$('#myModal2').modal('show');
					}
				}else{
					$('#myModal').modal('show');
				}
			} else {
			 
				if(curUser.id){//判断是否登录
					gotoUrl1('${basePath}/pinkerInfo/addPinkerInfo');
				}else{
					$('#myModal').modal('show');
				}
			}
		});
		
		$("#searchInfo").click(function(){
			gotoUrl1('${basePath}/login/signIn');
		});
						
		$('#myModal').modal('hide').css({
			'margin-top' : function() {
				return ($(this).height() / 2-100);
			}
		});
		
		$('#myModal2').modal('hide').css({
			'margin-top' : function() {
				return ($(this).height() / 2-100);
			}
		});
		
		$('#myModalInfo').modal('hide').css({
			'margin-top' : function() {
				return ($(this).height() / 2-100);
			}
		});
		
		$('#msgInfo').modal('hide').css({
			'margin-top' : function() {
				return ($(this).height() / 2-100);
			}
		});
		
		$('#carModalMsg').modal('hide').css({
			'margin-top' : function() {
				return ($(this).height() / 2 - 150);
			}
		});
		
		$('#carModal3').modal('hide').css({
			'margin-top' : function() {
				return ($(this).height() / 2 - 160);
			}
		});
		
		$('#signOutModal').modal('hide').css({
			'margin-top' : function() {
				return ($(this).height() / 2 - 150);
			}
		});
		
		//隐藏模式窗口事件
		$('#uploadCarPhotoModal').on('hidden.bs.modal', function(e) {
			$("#imgCarPhoto").removeAttr("src");
		});
		
		myScroll3 = new IScroll("#wrapper3", setting);
		myScroll4 = new IScroll("#wrapper4", setting);
		
		loadCarOwnerInfo(page1);
		
		
		
	});

	function info(t) {
		$(".swiper-container").css({"top":"45px"});
		$("#header").show();
		if(t){
			if($("#switchInfo").bootstrapSwitch("state") == true){
				swiper.slideTo(0, 0,false);//切换到第一个slide，速度为1秒
			}else{
				swiper.slideTo(1, 0,false);
			}
		}
		$("#d1").removeClass("lfwer-btn-uncheck").addClass("lfwer-btn-check");
		$("#d2").removeClass("lfwer-btn-check").addClass("lfwer-btn-uncheck");
		$("#d3").removeClass("lfwer-btn-check").addClass("lfwer-btn-uncheck");
		
	}
	function friend(t) {
		$(".swiper-container").css({"top":"0"});
		$("#header").hide();
		$("#d2").removeClass("lfwer-btn-uncheck").addClass("lfwer-btn-check");
		$("#d1").removeClass("lfwer-btn-check").addClass("lfwer-btn-uncheck");
		$("#d3").removeClass("lfwer-btn-check").addClass("lfwer-btn-uncheck");
		if(t)
			swiper.slideTo(2, 0, false);
		myScroll3.refresh();
	}
	
	function my(t) {
		$(".swiper-container").css({"top":"0"});
		$("#header").hide();
		$("#d3").removeClass("lfwer-btn-uncheck").addClass("lfwer-btn-check");
		$("#d1").removeClass("lfwer-btn-check").addClass("lfwer-btn-uncheck");
		$("#d2").removeClass("lfwer-btn-check").addClass("lfwer-btn-uncheck");
		if(t)
			swiper.slideTo(3, 0, false);
		if(!loadMy){
			$("#viewMy").load("${basePath}/login/my",function(){
				loadMy = true;
				myScroll4.refresh();
			});
		}
	}
	var t1 = null;//这个设置为全局
	function viewInfo() {
		event.stopPropagation(); //  阻止事件冒泡
	}

	function loadCarOwnerInfo(page) {

		if (!loading1) {
			loading1 = true;
			$("#pullOver1").hide();
			if (page == 1) {
				$("pullDown1").html("正在刷新……").show();
				myScroll1.scrollToElement(document.querySelector("#pullDown1"),0, true, true,null);
				myScroll1.refresh();
				$.ajax({
					url : "${basePath}/dict/getTime",
					sync : true,
					success : function(data) {
						_date1 = data;
					}
				});
			} else {
				$("#pullUp1").show();
				myScroll1.scrollToElement(document.querySelector("#pullUp1"),0, true, true,null);
				myScroll1.refresh();
			}

			$.ajax({
				url : '${basePath}/carOwnerInfo/getPageInfo',
				type : 'post',
				data : 'page=' + page + "&date=" + _date1,
				success : function(data) {
					if (data && data.length > 0) {
						var result = "";
						for (var i = 0; i < data.length; i++) {
							var row = data[i];
							var arrVia = [];
							if(row[2]){
								arrVia.push(row[2]);
							}
							if(row[3]){
								arrVia.push(row[3]);
							}
							if(row[4]){
								arrVia.push(row[4]);
							}
							if(row[5]){
								arrVia.push(row[5]);
							}
							if(row[6]){
								arrVia.push(row[6]);
							}
							var via = arrVia.join(",");
							/* if (via.length > 12) {
								via = via.substring(0, 12) + "...";
							} */
							var dateStr = "";
							if (row[26] == 1) {
								dateStr = row[7];
							} else {
								if (row[19] != null && row[20] != null
										&& row[21] != null
										&& row[22] != null
										&& row[23] != null
										&& row[24] != null
										&& row[25] != null) {
									dateStr = "周一至周日";
								} else if (row[19] != null
										&& row[20] != null
										&& row[21] != null
										&& row[22] != null
										&& row[23] != null
										&& row[24] != null) {
									dateStr = "周一至周六";
								} else if (row[19] != null
										&& row[20] != null
										&& row[21] != null
										&& row[22] != null
										&& row[23] != null) {
									dateStr = "周一至周五";
								} else if (row[19] != null
										&& row[20] != null
										&& row[21] != null
										&& row[22] != null) {
									dateStr = "周一至周四";
								} else {
									if (row[19] != null) {
										dateStr += "周一";
									}
									if (row[20] != null) {
										if (dateStr != "") {
											dateStr += ",";
										}
										dateStr += "周二";
									}
									if (row[21] != null) {
										if (dateStr != "") {
											dateStr += ",";
										}
										dateStr += "周三";
									}
									if (row[22] != null) {
										if (dateStr != "") {
											dateStr += ",";
										}
										dateStr += "周四";
									}
									if (row[23] != null) {
										if (dateStr != "") {
											dateStr += ",";
										}
										dateStr += "周五";
									}
									if (row[24] != null) {
										if (dateStr != "") {
											dateStr += ",";
										}
										dateStr += "周六";
									}
									if (row[25] != null) {
										if (dateStr != "") {
											dateStr += ",";
										}
										dateStr += "周日";
									}
								}
							}
	
							dateStr += " " + row[8].substring(0, 5);
	
							result += carOwnerInfoLi
									.replace("#form", row[0])
									.replace("#to", row[1])
									.replace("#via", via ? via : "无")
									.replace("#sex", lfwer.replaceNull(row[9]))
									.replace("#age", lfwer.replaceNull(row[10]))
									.replace("#pnum", row[11])
									.replace("#carType", row[12])
									.replace("#carStyle", row[13])
									.replace("#carColor", row[14])
									.replace("#nickName", row[15])
									.replace("#id", row[16])
									.replace("#userId", row[17])
									.replace("#userPhotoSmall", row[18])
									.replace(/#class/g,row[9] == '男' ? 'badge-1' : 'badge-2')
									.replace("#date", dateStr);
						}
						if (page > 1) {
							$("#pageInfo1").append(result);
						} else {
							$("#pageInfo1").html(result);
						}
					}
					$("#pullUp1").hide();
					$("#pullDown1").hide();
	
					if (page > 1 && (data == null || data.length == 0)) {
						loadUp1 = false;
						$("#pullOver1").show();
						myScroll1.scrollToElement(document.querySelector("#pullOver1"),0, true, true,null);
					}
					if (data && data.length > 0) {
						page1++;
					}
					myScroll1.refresh();
					loading1 = false;
				},
				error : function() {
					$("#pullUp1").hide();
					$("#pullDown1").hide();
					myScroll1.refresh();
					loading1 = false;
					alertMsg("加载失败!");
				}
			});
		}
	}
	
	function viewCarOwnerInfo(o, id) {
		pushHis({name:'forward',value:''});
		$(".mask").show();
		$("#viewDiv").slideDown(500,function() {
			$("#viewDiv").load('${basePath}/carOwnerInfo/viewCarOwnerInfo?type=index&id='+ id, function() {
				if(o)
					$(o).addClass("liclick");
				$(".mask").hide();
			});
		});
	}
	
	function loadPinkerInfo(page) {
		if (!loading2) {
			loading2 = true;
			$("#pullOver2").hide();
			if (page == 1) {
				$("pullDown2").html("正在刷新……").show();
				myScroll2.scrollToElement(document.querySelector("#pullDown2"),0, true, true,null);
				myScroll2.refresh();
				$.ajax({
					url : "${basePath}/dict/getTime",
					sync : true,
					success : function(data) {
						_date2 = data;
					}
				});
			} else {
				$("#pullUp2").show();
				myScroll2.scrollToElement(document.querySelector("#pullUp2"),0, true, true,null);
				myScroll2.refresh();
			}

			$.ajax({
				url : '${basePath}/pinkerInfo/getPageInfo',
				type : 'post',
				data : 'page=' + page + "&date=" + _date2,
				success : function(data) {
					if (data && data.length > 0) {
						var result = "";
						for (var i = 0; i < data.length; i++) {
							var row = data[i];
							var dateStr = "";
							if (row[20] == 1) {
								dateStr = row[4];
							} else {
								if (row[13] != null && row[14] != null
										&& row[15] != null
										&& row[16] != null
										&& row[17] != null
										&& row[18] != null
										&& row[19] != null) {
									dateStr = "周一至周日";
								} else if (row[13] != null
										&& row[14] != null
										&& row[15] != null
										&& row[16] != null
										&& row[17] != null
										&& row[18] != null) {
									dateStr = "周一至周六";
								} else if (row[13] != null
										&& row[14] != null
										&& row[15] != null
										&& row[16] != null
										&& row[17] != null) {
									dateStr = "周一至周五";
								} else if (row[13] != null
										&& row[14] != null
										&& row[15] != null
										&& row[16] != null) {
									dateStr = "周一至周四";
								} else {
									if (row[13] != null) {
										dateStr += "周一";
									}
									if (row[14] != null) {
										if (dateStr != "") {
											dateStr += ",";
										}
										dateStr += "周二";
									}
									if (row[15] != null) {
										if (dateStr != "") {
											dateStr += ",";
										}
										dateStr += "周三";
									}
									if (row[16] != null) {
										if (dateStr != "") {
											dateStr += ",";
										}
										dateStr += "周四";
									}
									if (row[17] != null) {
										if (dateStr != "") {
											dateStr += ",";
										}
										dateStr += "周五";
									}
									if (row[18] != null) {
										if (dateStr != "") {
											dateStr += ",";
										}
										dateStr += "周六";
									}
									if (row[19] != null) {
										if (dateStr != "") {
											dateStr += ",";
										}
										dateStr += "周日";
									}
								}
							}

							dateStr += " " + row[5].substring(0, 5);

							result += pinkerInfoLi
									.replace("#form",row[0] + "，" + row[1])
									.replace("#to",row[2] + "，" + row[3])
									.replace("#sex", lfwer.replaceNull(row[6]))
									.replace("#age", lfwer.replaceNull(row[7]))
									.replace("#pnum", row[8])
									.replace("#nickName", row[9])
									.replace("#id", row[10])
									.replace("#userId", row[11])
									.replace("#userPhotoSmall", row[12])
									.replace(/#class/g,row[6] == '男' ? 'badge-1' : 'badge-2').replace("#date", dateStr);
						}
						if (page > 1) {
							$("#pageInfo2").append(result);
						} else {
							$("#pageInfo2").html(result);
						}

					}
					$("#pullUp2").hide();
					$("#pullDown2").hide();
					
					if (page > 1 && (data == null || data.length == 0)) {
						loadUp2 = false;
						$("#pullOver2").show();
						myScroll2.scrollToElement(document.querySelector("#pullOver2"),0, true, true,null);
					}
					if (data && data.length > 0) {
						page2++;
					}
					myScroll2.refresh();
					loading2 = false;
				},
				error : function() {
					$("#pullUp2").hide();
					$("#pullDown2").hide();
					myScroll2.refresh();
					loading2 = false;
					alertMsg("加载失败！");
				}
			});
		}
	}

	function viewPinkerInfo(o, id) {
		pushHis({name:'forward',value:''});
		$(".mask").show();
		$("#viewDiv").slideDown(500,function() {
			
			$("#viewDiv").load('${basePath}/pinkerInfo/viewPinkerInfo?type=index&id='+ id, function() {
				if(o)
					$(o).addClass("liclick");
				$(".mask").hide();
			});
		});
	}

	function reloadInfo1() {
		loading1 = false;
		loadUp1 = true;
		loadCarOwnerInfo(page1);

	}

	function reloadInfo2() {
		loading2 = false;
		loadUp2 = true;
		loadPinkerInfo(page2);
	}

	//弹出删除发布信息确认框
	function removeInfo(flag,id) {
		delFlag = flag;
		delId = id;
		$("#myModalInfo").modal('show');		
	}
	
	//删除发布信息
	function removeSubmit(){
		var url,name;
		if(delFlag == "carOwner"){
			url = '${basePath}/carOwnerInfo/removeCarOwnerInfo?id=' + delId;
			name = '1';
		}else if(delFlag == "pinker"){
			url = '${basePath}/pinkerInfo/removePinkerInfo?id='+delId;
			name = '2';
		}else{
			alertMsg("参数错误！");
			return;
		}
		
		$.ajax({
			url : url,
			type : 'post',
			success : function() {
				$("#msgContentInfo").html("删除成功,即将离开本页。").removeClass(
						'alert-danger').addClass('alert-success');
				$("#msgInfo").modal('show');
				window.setTimeout(function() {
					$("#msgInfo").modal('hide');
					history.back();
					if(name == "1"){
			    		$("#pullDown1").html("松开刷新 O(∩_∩)O").show();
		 				myScroll1.scrollTo(0, 30, 500, null);
			    	}else{
			    		$("#pullDown2").html("松开刷新 O(∩_∩)O").show();
		 				myScroll1.scrollTo(0, 30, 500, null);
			    	}
				}, 2000);
			},
			error : function() {
				$("#msgContentInfo").html("删除失败!").removeClass('alert-success')
						.addClass('alert-danger');
				$("#msgInfo").modal('show');
				window.setTimeout(function() {
					$("#msgInfo").modal('hide');
				}, 2000);
			}
		});
	}
	
	function pushHis(json){
		history.replaceState(json, '', '#'+json.name);
		history.pushState(json, '', '#'+json.name);
	}
	 
	function gotoLogin(){
		$("#myModal").modal('hide');
		gotoUrl1('${basePath }/login/signIn');
	}
	
	function gotoRegister3(){
		$("#myModal2").modal('hide');
		gotoUrl1('${basePath}/login/register3?type=index');
	}
	
	function gotoUrl1(url){
		pushHis({name:'forward',value:''});
		$(".mask").show();
		$("#viewDiv").slideDown(500,function() {
			$("#viewDiv").load(url, function() {
				$(".mask").hide();
			});
		});
	}
	
	function gotoUrl2(url){
		pushHis({name:'forward2',value:''});
		$(".mask").show();
		$("#viewDiv2").slideDown(500,function() {
			$("#viewDiv2").load(url, function() {
				$(".mask").hide();
			});
		});
	}
	
	function closeViewDiv() {
		$("#viewDiv").hide();
		$("#viewDiv").empty();
	}
	
	function closeViewDiv2() {
		$("#viewDiv2").hide();
		$("#viewDiv2").empty();
	}
	 
	function signOutOk(){
		location.href= "${basePath}/login/signOut";
	}
	
	function alertMsg(message){
		$("#alertMsgContent").html(message);
		$("#alertMsg").show();
		// 2秒后隐藏提示信息
		window.setTimeout(function() {
			$("#alertMsg").hide();
		}, 2000);
	}
</script>
</head>
<body>
	<div id="header">
		<table style="width: 100%;margin: 0;padding: 0;" border="0">
			<colgroup>
				<col width="50%">
				<col width="30%">
				<col width="20%">
			</colgroup>
			<tr>
				<td>&nbsp;<input type="checkbox" id="switchInfo" data-off-color="info"
					data-on-color="info" data-on-text="车主列表" data-off-text="乘客列表"
					data-label-text="切换" data-label-width="30" ></td>
				<td><a id="addInfo" style="font-size: 16px;"><span class="glyphicon glyphicon-edit"></span><span
					id="addInfoName">&nbsp;车主发布</span>
				</a></td>
				<td align="right"><a id="searchInfo" style="font-size: 18px;"> <span class="glyphicon glyphicon-search"></span>&nbsp;</td>
			</tr>
		</table>
	</div>

	<!-- 横向 -->
	<div class="swiper-container">
		<div class="swiper-wrapper">
			<div class="swiper-slide slide swiper-no-swiping" >
				<div class="wrapper" id="wrapper1">
					<div class="scroller">
						<div id="pullDown1" class="alert text-center"
							style="display: none; padding: 0; margin: 0 0 6px 0; font-size: 16px; height: 30px; color: gray;">
						</div>
						<ul id="pageInfo1" class="ul-main">
						</ul>
						<div id="pullUp1" class="alert text-center"
							style="display: none; padding: 6px; margin: 0; font-size: 16px;color: gray;">
							正在刷新……</div>
						<div id="pullOver1" class="alert text-center" onclick="reloadInfo1()"
							style="display: none; padding: 6px; margin: 0; font-size: 16px; cursor: pointer;color: gray;">
							没有更多数据，点击刷新</div>
					</div>
				</div>
			</div>
			<div class="swiper-slide slide swiper-no-swiping" >
				<div class="wrapper" id="wrapper2">
					<div class="scroller">
						<div id="pullDown2" class="alert text-center"
							style="display: none; padding: 0; margin: 0 0 6px 0; font-size: 16px; height: 30px; color: gray;">
						</div>
						<ul id="pageInfo2" class="ul-main">
						</ul>
						<div id="pullUp2" class="alert text-center"
							style="display: none; padding: 6px; margin: 0; font-size: 16px;color: gray;">
							正在刷新……</div>
						<div id="pullOver2" class="alert text-center" onclick="reloadInfo2()"
							style="display: none; padding: 6px; margin: 0; font-size: 16px; cursor: pointer;color: gray;">
							没有更多数据，点击刷新</div>
					</div>
				</div>
			</div>
			<div class="swiper-slide slide swiper-no-swiping" >
				<div class="wrapper" id="wrapper3">
					<div class="scroller">
					消息
					</div>
				</div>
			</div>
			<div class="swiper-slide slide swiper-no-swiping" >
				<div class="wrapper" id="wrapper4">
					<div class="scroller">
						<div id="viewMy"></div>
					</div>
				</div>
			</div>
		</div>
	</div>
			 
	<div id="footer">
		<div class="container">
		<div class="row text-center">
			<div class="col-xs-4">
				<div class="lfwer-btn-check" id="d1" onclick="info(true)">
					<table style="width: 100%;text-align: center;">
						<tr><td><span class="glyphicon glyphicon-home" ></span></td></tr>
						<tr><td>拼车信息</td></tr>
					</table>
				</div>
			</div>
			<div class="col-xs-4">
				<div class="lfwer-btn-uncheck" id="d2" onclick="friend(true)">
					<table style="width: 100%;text-align: center;">
						<tr><td><span class="glyphicon glyphicon-comment"></span></td></tr>
						<tr><td>消息</td></tr>
					</table>
				</div>
			</div>
			<div class="col-xs-4">
				<div class="lfwer-btn-uncheck" id="d3" onclick="my(true)">
					<table style="width: 100%;text-align: center;">
						<tr><td><span class="glyphicon glyphicon-user"></span></td></tr>
						<tr><td>我的</td></tr>
					</table>
				</div>
			</div>
		</div>
		</div>
	</div>
	
	<div id="viewDiv"></div>
	<div id="viewDiv2"></div>
	
	<iframe id="targetFrame" name="targetFrame" style="display: none;"></iframe>
	
	<div class="modal fade" id="myModal" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-body text-center">您需要登陆后才可以进行发布<br/><a href="javascript:gotoLogin();">【登陆】</a></div>
			</div>
		</div>
	</div>
	
	<div class="modal fade" id="myModal2" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel2" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-body text-center">您需要认证为车主才可以进行发布<br/>点击<a href="javascript:gotoRegister3()">【车主认证】</a></div>
			</div>
		</div>
	</div>
	
	<div class="modal fade" id="myModalInfo" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabelInfo" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content text-center">
				<div class="modal-body">确定删除此信息？</div>
				<div class="modal-footer">
					<div style="text-align: center;">
						<button type="button" class="btn btn-primary" data-dismiss="modal">取消</button>
						<button type="button" class="btn btn-warning"
							onclick="removeSubmit()" data-dismiss="modal">确定</button>
					</div>

				</div>
			</div>
		</div>
	</div>
	
	<div class="modal fade" id="signOutModal" tabindex="-1" role="dialog" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content text-center">
				<div class="modal-body">确定退出当前账号？</div>
				<div class="modal-footer">
					<div style="text-align: center;">
						<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
						<button type="button" class="btn btn-primary"
							onclick="signOutOk()" data-dismiss="modal">确定</button>
					</div>

				</div>
			</div>
		</div>
	</div>
	
	<div class="modal fade" id="msgInfo" tabindex="-1"
		aria-labelledby="msgModalLabelInfo" role="dialog" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div id="msgContentInfo" class="modal-body alert-danger text-center"
					style="font-size: 20px;"></div>
			</div>
		</div>
	</div>
	
	<div class="modal fade" id="uploadCarPhotoModal" tabindex="-1"
		role="dialog" aria-labelledby="uploadCarPhotoModalLabel"
		aria-hidden="true">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-body" style="margin: 0; padding: 2px;">
					<center>
						<img id="imgCarPhoto" class="imgCarPhoto img-responsive img-thumbnail"
							onclick="$('#uploadCarPhotoModal').modal('hide');"
							onerror="noFind(this);">
					</center>
				</div>
			</div>
		</div>
	</div>
	
<div class="modal fade" id="carModalMsg" tabindex="-1" role="dialog"
	aria-labelledby="carModalMsgLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-body">如果不完善车辆信息，您是无法发布车主拼车信息的哦 ^_^</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" id="btnBreak">残忍跳过</button>
				<button type="button" class="btn btn-primary" data-dismiss="modal">继续完善</button>
			</div>
		</div>
	</div>
</div>

<div class="modal fade" id="carModal3" tabindex="-1"
	aria-labelledby="carModal3Label" role="dialog" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-body" >
				<div style="line-height: 40px;text-align: left;">
					<%
						Map<Integer, String> carProvinceMap = Util.getCarProvinceMap();
						for (Integer i : carProvinceMap.keySet()) {
					%>
					<button class="btn btn-default chooseCarProvince" value="<%=i%>"><%=carProvinceMap.get(i)%></button>
					<%
						}
					%>
				</div>
			</div>
		</div>
	</div>
</div>

<div class="modal fade" id="headPhotoModal" tabindex="-1" role="dialog"
	aria-labelledby="headPhotoModalLabel" aria-hidden="true">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-body" style="margin: 0;padding: 0;">
				<table style="width: 100%;margin: 0;padding: 0;">
					<tr>
						<td align="center"><img id="imgHeadPhoto" /></td>
					</tr>
				</table>
			</div>
			<div class="modal-footer">
				<div class="text-center">
					<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
					<button type="button" class="btn btn-primary"
						onclick="savePhoto()">确定</button>
				</div>
			</div>
		</div>
	</div>
</div>

<div id="alertMsg" class="alert alert-danger text-center" style="display: none;position:absolute;bottom:20%;z-index: 999999999; margin:0   -40%;width: 80%;left:50%;">
	<span id="alertMsgContent"></span>
</div>
						
<!--加载效果-->
<div class="mask">
    <div class="spinner">
        <div class="spinner-container container1">
            <div class="circle1"></div>
            <div class="circle2"></div>
            <div class="circle3"></div>
            <div class="circle4"></div>
        </div>
        <div class="spinner-container container2">
            <div class="circle1"></div>
            <div class="circle2"></div>
            <div class="circle3"></div>
            <div class="circle4"></div>
        </div>
        <div class="spinner-container container3">
            <div class="circle1"></div>
            <div class="circle2"></div>
            <div class="circle3"></div>
            <div class="circle4"></div>
        </div>
    </div>
</div>
</body>
</html>
