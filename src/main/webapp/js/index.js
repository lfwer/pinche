$(function() {
	$('input, textarea').placeholder();
});

window.onhashchange = function(event){
  	
	var _hash = event.oldURL.substring(event.oldURL.indexOf("#"));
	$(".mask").hide(); 
	//var _hash = location.hash;
	if(_hash == "#forward"){
		$(".modal").modal('hide');
		$(".mbsc-mobiscroll").hide();
		closeViewDiv();
	}else if(_hash == "#forward2"){
		$(".modal").modal('hide');
		$(".mbsc-mobiscroll").hide();
		closeViewDiv2();
	}else if(_hash == "#signIn"){
		$(".modal").modal('hide');
		$(".mbsc-mobiscroll").hide();
		closeViewDiv();
		getCurUserForServer(function(){
			my(true);
		});
		
	}else if(_hash == "#forwardEdit"){
		$(".editDiv").hide();
	}
	 
}

/* window.onpopstate = function(event) {
	alert("location: " + document.location + ", state: " + JSON.stringify(event.state));
	var state = event.state;
	if(state){
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
		}else {
			alertMsg('参数错误:'+name);
		}
	}
}; */

var zhaoche = true;
var _date1 = new Date();;
var _date2= new Date();
var page1 = 1;
var page2 = 1;
var myScroll1 = null;
var myScroll2 = null;
var myScroll3 = null;
var myScroll4 = null;
var loading1 = false;
var loading2 = false;
var overPullDown1 = false;
var overPullDown2 = false;
var swiper,swiper2;
var firstIn1 = true;
var firstIn2 = true;
var delId,delflag;
var gallery;
var curPhone = null;
var carOwnerInfoLi = '<li onclick="viewCarOwnerInfo(this,\'#id\')" style="padding:4px 4px 6px 4px;">'
	+ '<table class="list-table"><tr><td rowspan="4" width="80" style="padding-left: 5px;">'
	+ '<img src="'+server.path+'/login/getPhoto?id=#userId&name=#userPhotoSmall" '
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
		+ '<img src="'+server.path+'/login/getPhoto?id=#userId&name=#userPhotoSmall" '
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
	
	document.addEventListener('touchmove', function(e) {
		e.preventDefault();
	}, false);
	
	server.cookieName = $.cookie(server.cookieDomainName);
	
	zhaoche = true;
	
	$("#switchInfo").bootstrapSwitch("state",true);
	
	var setting = {
			probeType : 2, //probeType：1对性能没有影响。在滚动事件被触发时，滚动轴是不是忙着做它的东西。probeType：2总执行滚动，除了势头，反弹过程中的事件。这类似于原生的onscroll事件。probeType：3发出的滚动事件与到的像素精度。注意，滚动被迫requestAnimationFrame（即：useTransition：假）。  
			scrollbars : true,//有滚动条  
			mouseWheel : true,//允许滑轮滚动  
			fadeScrollbars : true,//滚动时显示滚动条，默认影藏，并且是淡出淡入效果  
			bounce : true,//边界反弹  
			interactiveScrollbars : true,//滚动条可以拖动  
			shrinkScrollbars : 'scale',// 当滚动边界之外的滚动条是由少量的收缩。'clip' or 'scale'. 
			preventDefault : false,
			click : true,// 允许点击事件  
			keyBindings : true,//允许使用按键控制  
			momentum : true // 允许有惯性滑动  
		};
	  	
  	myScroll1 = new IScroll("#wrapper1", setting);
  	
  	myScroll1.on("scrollEnd",function() {
		if (!loading1) {
			if (this.y == 0 && $("#pullDown1").text() == ("松开刷新 O(∩_∩)O")) {//下拉刷新
				page1 = 1;
				loadCarOwnerInfo(page1);
			} else if ($("#pullDown1").html() == ("↑ 下拉刷新")) {
				$("#pullDown1").empty().hide();
			} else if (this.y == this.maxScrollY && !overPullDown1) {
				$("#pullUp1").show();
				loadCarOwnerInfo(page1);
			} 
		}

	});

	myScroll1.on("scroll", function() {
		if(loading1){
			alertMsg("请不要频繁刷新");
			return;
		}else{
			var y = this.y;
			if (this.y >= 30) {
				$("#pullDown1").html("松开刷新 O(∩_∩)O").show();
			} else if (this.y > 0 && this.y < 30) {
				$("#pullDown1").html("↑ 下拉刷新").show();
			}
		}
	});
	
	myScroll2 = new IScroll("#wrapper2", setting);
	
  	myScroll2.on('scrollEnd',function() {
		if (!loading2) {
			if (this.y == 0 && $("#pullDown2").text() == ("松开刷新 O(∩_∩)O")) {//下拉刷新
				page2 = 1;
				loadPinkerInfo(page2);
			} else if ($("#pullDown2").html() == ("↑ 下拉刷新")) {
				$("#pullDown2").empty().hide();
			} else if (this.y == this.maxScrollY && !overPullDown2) {
				$("#pullUp2").show();
				loadPinkerInfo(page2);
			} 
		}

	});

	myScroll2.on("scroll", function() {
		if(loading2){
			alertMsg("请不要频繁刷新");
			return;
		}else{
			var y = this.y;
			console.log(y);
			if (this.y >= 30) {
				$("#pullDown2").html("松开刷新 O(∩_∩)O").show();
			} else if (this.y > 0 && this.y < 30) {
				$("#pullDown2").html("↑ 下拉刷新").show();
			}
		}
	});
	
	myScroll3 = new IScroll("#wrapper3", setting);
	myScroll4 = new IScroll("#wrapper4", setting);
	
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
	
	$("#searchInfo").click(function(){
		alertMsg("敬请期待");
	});
	
	getCurUserForServer(function(){
		
		$("#switchInfo").on('switchChange.bootstrapSwitch',function(event, state) {
			zhaoche = state;
			info(false);
			if (zhaoche) {
				$("#addInfoName").html("&nbsp;车主发布");
				swiper.slideTo(0,0,false);
				if(firstIn1){
					$("#pullDown1").html("正在刷新……").show();
				 	myScroll1.scrollTo(0, 10, 500, null);
					firstIn1 = false;
				}
			} else {
				$("#addInfoName").html("&nbsp;乘客发布");
				swiper.slideTo(1,0,false); 
				if(firstIn2){
					$("#pullDown2").html("正在刷新……").show();
				 	myScroll2.scrollTo(0, 10, 500, null);
					firstIn2 = false;
				}
			}
		});
		
		if(getCurUser() && getCurUser().type == "1"){
			zhaoche = false;
			$("#switchInfo").bootstrapSwitch("state",false);
		}
		
		
		$("#index_addInfo").click(function() {
			if (zhaoche) {
				if(getCurUser()){//判断是否登录
					
					if(getCurUser().carType){
						gotoUrl1(lfwer.rootName+'/pages/carOwnerInfo/addCarOwnerInfo.html');
					}else{
						$('#myModal2').modal('show');
					}
				}else{
					$('#myModal').modal('show');
				}
			} else {
				if(getCurUser()){//判断是否登录
					gotoUrl1(lfwer.rootName+'/pages/pinkerInfo/addPinkerInfo.html');
				}else{
					$('#myModal').modal('show');
				}
			}
		});
		
		if(zhaoche){
			$("#pullDown1").html("正在刷新……").show();
		 	myScroll1.scrollTo(0, 10, 500, null);
		 	firstIn1 = false;
		}else{
			$("#pullDown2").html("正在刷新……").show();
			myScroll2.scrollTo(0, 10, 500, null);
			firstIn2 = false;
		}	
		
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
	
 	
});


function getCurUserForServer(callback){
	setCurUser(undefined);
	if(server.cookieName){
		$(".mask").show(); 
		$.ajax({ 
			url:server.path+"/login/getCurUser",
			data:{name : server.cookieName},
			//async:false,//jsonp不支持同步
			//crossDomain: true,
			//dataType: "jsonp",
			success:function(data){
				//console.log("ajax:"+data+",time:"+new Date().getTime());
				$(".mask").hide();
				if(data){
 					setCurUser(data);
				}
				if("function" == typeof(callback)){  
					callback();//将ajax请求后需要进行的操作放在回调函数callback()中  
				} 
			},
			error:function(){
				$(".mask").hide();
			}
		});
	}else{
		if("function" == typeof(callback)){  
			callback();//将ajax请求后需要进行的操作放在回调函数callback()中  
		}
	}
}

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
	$("#index_d1").removeClass("lfwer-btn-uncheck").addClass("lfwer-btn-check");
	$("#index_d2").removeClass("lfwer-btn-check").addClass("lfwer-btn-uncheck");
	$("#index_d3").removeClass("lfwer-btn-check").addClass("lfwer-btn-uncheck");
	
}
function friend(t) {
	$(".swiper-container").css({"top":"0"});
	$("#header").hide();
	$("#index_d2").removeClass("lfwer-btn-uncheck").addClass("lfwer-btn-check");
	$("#index_d1").removeClass("lfwer-btn-check").addClass("lfwer-btn-uncheck");
	$("#index_d3").removeClass("lfwer-btn-check").addClass("lfwer-btn-uncheck");
	if(t)
		swiper.slideTo(2, 0, false);
	myScroll3.refresh();
}

function my(t) {
	$(".swiper-container").css({"top":"0"});
	$("#header").hide();
	$("#index_d3").removeClass("lfwer-btn-uncheck").addClass("lfwer-btn-check");
	$("#index_d1").removeClass("lfwer-btn-check").addClass("lfwer-btn-uncheck");
	$("#index_d2").removeClass("lfwer-btn-check").addClass("lfwer-btn-uncheck");
	if(t)
		swiper.slideTo(3, 0, false);
	$("#viewMy").load(lfwer.rootName+"/pages/login/my.html?_v="+new Date().getTime(),function(){
		myScroll4.refresh();
	});
}
var t1 = null;//这个设置为全局
function viewInfo() {
	event.stopPropagation(); //  阻止事件冒泡
}

function loadCarOwnerInfo(page,retry) {
	if (!loading1) {
		loading1 = true;
		$("#pullOver1").hide();
		if (page == 1) {
			if(retry){
				$("#pullUp1").show();
				myScroll1.scrollToElement(document.querySelector("#pullUp1"),0, true, true,null);
			}else{
				$("#pullDown1").html("正在刷新……").show();
				myScroll1.scrollToElement(document.querySelector("#pullDown1"),0, true, true,null);
			}
			myScroll1.refresh();
			$.ajax({
				url : server.path+"/dict/getTime",
				async : false,
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
			url : server.path+'/carOwnerInfo/getPageInfo',
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

				if ((data == null || data.length < 20)) {
					$("#pullOver1").show();
					overPullDown1 = true;
					 
					if(page1 > 1)
						myScroll1.scrollToElement(document.querySelector("#pullOver1"),0, true, true,null);
					else{
						var obj = $("#wrapper1");
						//判断是否有滚动条
						if(!(obj.scrollHeight>obj.clientHeight||obj.offsetHeight>obj.clientHeight)){
							$("#pullOver1").html('<a href="javascript:loadCarOwnerInfo(1,true);">暂无更新，点我刷新</a>');
						}else{
							$("#pullOver1").html("已经到底啦");
							 
						}
					}
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
				alertMsg("刷新失败!");
			}
		});
	}
}

function viewCarOwnerInfo(o, id) {
	pushHis({name:'forward',value:''});
	$(".mask").show();
	$("#viewDiv").slideDown(500,function() {
		$("#viewDiv").load(lfwer.rootName+'/pages/carOwnerInfo/views/'+id+'.html?type=index&id='+ id, function() {
			if(o)
				$(o).addClass("liclick");
			$(".mask").hide();
		});
	});
}

function loadPinkerInfo(page,retry) {
	if (!loading2) {
		loading2 = true;
		$("#pullOver2").hide();
		if (page == 1) {
			if(retry){
				$("#pullUp2").show();
				myScroll2.scrollToElement(document.querySelector("#pullUp2"),0, true, true,null);
			}else{
				$("#pullDown2").html("正在刷新……").show();
				myScroll2.scrollToElement(document.querySelector("#pullDown2"),0, true, true,null);
			}
			myScroll2.refresh();
			$.ajax({
				url : server.path+"/dict/getTime",
				async : false,
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
			url : server.path+'/pinkerInfo/getPageInfo',
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
				
				if ((data == null || data.length < 20)) {
					$("#pullOver2").show();
					overPullDown2 = true;
					if(page2 > 1)
						myScroll2.scrollToElement(document.querySelector("#pullOver2"),0, true, true,null);
					else{
						var obj = $("#wrapper2");
						//判断是否有滚动条
						if(!(obj.scrollHeight>obj.clientHeight||obj.offsetHeight>obj.clientHeight)){
							$("#pullOver2").html('<a href="javascript:loadPinkerInfo(1,true);">暂无更新，点我刷新</a>');
						}else{
							$("#pullOver2").html("已经到底啦");
						}
					}
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
				alertMsg("刷新失败！");
			}
		});
	}
}

function viewPinkerInfo(o, id) {
	pushHis({name:'forward',value:''});
	$(".mask").show();
	$("#viewDiv").slideDown(500,function() {
		
		$("#viewDiv").load(lfwer.rootName+'/pages/pinkerInfo/views/'+id+'.html?type=index&id='+ id, function() {
			if(o)
				$(o).addClass("liclick");
			$(".mask").hide();
		});
	});
}

function reloadInfo1() {
	loadCarOwnerInfo(page1);

}

function reloadInfo2() {
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
		url = server.path+'/carOwnerInfo/removeCarOwnerInfo?id=' + delId;
		name = '1';
	}else if(delFlag == "pinker"){
		url = server.path+'/pinkerInfo/removePinkerInfo?id='+delId;
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
		    		$("#pullDown1").html("正在刷新……").show();
	 				myScroll1.scrollTo(0, 10, 500, null);
		    	}else{
		    		$("#pullDown2").html("正在刷新……").show();
	 				myScroll1.scrollTo(0, 10, 500, null);
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
	//history.replaceState(json, '', '#'+json.name);
	history.pushState(json, '', '#'+json.name);
}
 
function gotoLogin(){
	$("#myModal").modal('hide');
	gotoUrl1(lfwer.rootName+'/pages/login/signIn.html');
}

function gotoRegister3(){
	$("#myModal2").modal('hide');
	gotoUrl1(lfwer.rootName+'/pages/login/register3.html?type=index');
}

function gotoUrl1(url,name){
	if(!name)
		name = "forward";
	pushHis({name:name,value:''});
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
	$.cookie(server.cookieDomainName, null,{expires: -1,path : server.cookiePath});
	server.cookieName = undefined;
	setCurUser(undefined);
	location.href = lfwer.rootName + "/index.html";
}

function alertMsg(message,type){
	$("#alertMsgContent").html(message);
	if(type == "success"){
		$("#alertMsg").removeClass("alert-danger").addClass("alert-success");
	}else{
		$("#alertMsg").removeClass("alert-success").addClass("alert-danger");
	}
	$("#alertMsg").show();
	// 2秒后隐藏提示信息
	window.setTimeout(function() {
		$("#alertMsg").hide();
	}, 2000);
}

//图片预览
var initPhotoSwipeFromDOM = function(gallerySelector) {
	
	// parse slide data (url, title, size ...) from DOM elements 
	// (children of gallerySelector)
	var parseThumbnailElements = function(el) {
		var thumbElements = el.childNodes;
		var numNodes = thumbElements.length;
		var items = [], figureEl, linkEl, size, item;

		for (var i = 0; i < numNodes; i++) {

			figureEl = thumbElements[i]; // <figure> element

			// include only element nodes 
			if (figureEl.nodeType !== 1) {
				continue;
			}

			linkEl = figureEl.children[0]; // <a> element

			size = linkEl.getAttribute('data-size').split('x');

			// create slide object
			item = {
				src : linkEl.getAttribute('href'),
				w : parseInt(size[0], 10),
				h : parseInt(size[1], 10)
			};

			if (figureEl.children.length > 1) {
				// <figcaption> content
				item.title = figureEl.children[1].innerHTML;
			}

			if (linkEl.children.length > 0) {
				// <img> thumbnail element, retrieving thumbnail url
				item.msrc = linkEl.children[0].getAttribute('src');
			}

			item.el = figureEl; // save link to element for getThumbBoundsFn
			items.push(item);
		}

		return items;
	};

	// find nearest parent element
	var closest = function closest(el, fn) {
		return el && (fn(el) ? el : closest(el.parentNode, fn));
	};

	// triggers when user clicks on thumbnail
	var onThumbnailsClick = function(e) {
		e = e || window.event;
		e.preventDefault ? e.preventDefault() : e.returnValue = false;

		var eTarget = e.target || e.srcElement;

		// find root element of slide
		var clickedListItem = closest(
				eTarget,
				function(el) {
					return (el.tagName && el.tagName.toUpperCase() === 'FIGURE');
				});

		if (!clickedListItem) {
			return;
		}

		// find index of clicked item by looping through all child nodes
		// alternatively, you may define index via data- attribute
		var clickedGallery = clickedListItem.parentNode;
		var childNodes = clickedListItem.parentNode.childNodes;
		var numChildNodes = childNodes.length, nodeIndex = 0, index;

		for (var i = 0; i < numChildNodes; i++) {
			if (childNodes[i].nodeType !== 1) {
				continue;
			}

			if (childNodes[i] === clickedListItem) {
				index = nodeIndex;
				break;
			}
			nodeIndex++;
		}

		if (index >= 0) {
			// open PhotoSwipe if valid index found
			openPhotoSwipe(index, clickedGallery);
		}
		return false;
	};

	// parse picture index and gallery index from URL (#&pid=1&gid=2)
	var photoswipeParseHash = function() {
		var hash = window.location.hash.substring(1), params = {};
		if (hash.length < 5) {
			return params;
		}

		var vars = hash.split('&');
		for (var i = 0; i < vars.length; i++) {
			if (!vars[i]) {
				continue;
			}
			var pair = vars[i].split('=');
			if (pair.length < 2) {
				continue;
			}
			params[pair[0]] = pair[1];
		}

		if (params.gid) {
			params.gid = parseInt(params.gid, 10);
		}

		return params;
	};

	var openPhotoSwipe = function(index, galleryElement,
			disableAnimation, fromURL) {
		var pswpElement = document.querySelectorAll('.pswp')[0], options, items;

		items = parseThumbnailElements(galleryElement);

		// define options (if needed)
		options = {

			// define gallery index (for URL)
			galleryUID : galleryElement.getAttribute('data-pswp-uid'),
			getThumbBoundsFn : function(index) {
				// See Options -> getThumbBoundsFn section of documentation for more info
				var thumbnail = items[index].el
						.getElementsByTagName('img')[0], // find thumbnail
				pageYScroll = window.pageYOffset
						|| document.documentElement.scrollTop, rect = thumbnail
						.getBoundingClientRect();

				return {
					x : rect.left,
					y : rect.top + pageYScroll,
					w : rect.width
				};
			}

		};
		// PhotoSwipe opened from URL
		if (fromURL) {
			if (options.galleryPIDs) {
				// parse real index when custom PIDs are used 
				// http://photoswipe.com/documentation/faq.html#custom-pid-in-url
				for (var j = 0; j < items.length; j++) {
					if (items[j].pid == index) {
						options.index = j;
						break;
					}
				}
			} else {
				// in URL indexes start from 1
				options.index = parseInt(index, 10) - 1;
			}
		} else {
			options.index = parseInt(index, 10);
		}

		// exit if index not found
		if (isNaN(options.index)) {
			return;
		}

		if (disableAnimation) {
			options.showAnimationDuration = 0;
		}

		// Pass data to PhotoSwipe and initialize it
		gallery = new PhotoSwipe(pswpElement, PhotoSwipeUI_Default,
				items, options);
		gallery.init();
	};

	// loop through all gallery elements and bind events
	var galleryElements = document.querySelectorAll(gallerySelector);

	for (var i = 0, l = galleryElements.length; i < l; i++) {
		galleryElements[i].setAttribute('data-pswp-uid', i + 1);
		galleryElements[i].onclick = onThumbnailsClick;
	}

	// Parse URL and open gallery if it contains #&pid=3&gid=1
	var hashData = photoswipeParseHash();
	if (hashData.pid && hashData.gid) {
		openPhotoSwipe(hashData.pid, galleryElements[hashData.gid - 1],
				true, true);
	}
}