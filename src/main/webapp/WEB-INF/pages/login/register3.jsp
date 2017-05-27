<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/common/taglib.jsp"%>
<style>
#wrapperInfo {
	bottom: 0;
}
</style>
<div id="headerInfo">
	<div class="row">
		<div class="col-xs-2">
			<span class="glyphicon glyphicon-chevron-left"
				onclick="history.back();" style="cursor: pointer;">&nbsp;</span>
		</div>
		<div class="col-xs-8 text-center">
			<span>车主认证</span>
		</div>
		<div class="col-xs-2"></div>
	</div>
</div>
<div id="wrapperInfo">
	<div id="scrollerInfo">
		<div class="container">
			<div class="row" style="height: 10px;"></div>
			<form id="form1" class="form-horizontal" method="post"
				action="${basePath }/login/register3Submit">
				<div class="form-group">
					<div class="col-xs-12">
						<div class="input-group">
							<span class="input-group-addon">认证车型 <span
								class="lfwer-seize">*</span>
							</span>
							<ul id="carTypeList" style="display: none;">
								<%@include file="/common/carType.jsp"%>
								<%-- <c:forEach var="o" items="${carTypeList }">
								<c:if test="${o.pid == -1 }">
									<li>
										<span class="carBrand" id="${o.id }">${o.name }</span>
										<ul>
											<c:forEach var="o2" items="${carTypeList }">
												<c:if test="${o2.pid == o.id }">
													<li class="carType" id="${o2.id }">${o2.name }</li>
												</c:if>
											</c:forEach>
										</ul>
									</li>
								</c:if>
							</c:forEach> --%>
							</ul>
							<input id="carType" name="carType" type="text"
								class="lfwer-hidden" value="${user.carType }" data-bv-notempty
								data-bv-notempty-message="请选择认证车型"> <input id="carBrand"
								name="carBrand" type="text" class="lfwer-hidden"
								value="${user.carBrand }">
						</div>
					</div>
				</div>
				<div class="form-group">
					<div class="col-xs-12">
						<div class="input-group">
							<span class="input-group-addon">车辆款式 <span
								class="lfwer-seize">*</span></span><input type="text" id="carStyle"
								name="carStyle" class="form-control"
								style="border-radius: 0 3px 3px 0;" placeholder="如：2017款自动豪华型"
								value="${user.carStyle }" data-bv-stringlength
								data-bv-stringlength-min="2" data-bv-stringlength-max="20"
								data-bv-stringlength-message="车辆款式长度必须在2至20位之间" data-bv-notempty
								data-bv-notempty-message="请填写车辆款式">
						</div>
					</div>
				</div>
				<div class="form-group">
					<div class="col-xs-12">
						<div class="input-group">
							<span class="input-group-addon">车辆颜色 <span
								class="lfwer-seize">*</span></span> <select id="carColorList">
								<option value=''>-- 请选择车辆颜色 --</option>
								<c:forEach var="o" items="${carColorList }">
									<c:if test="${o.id eq user.carColor }">
										<option value='${o.id }' selected="selected">${o.name }</option>
									</c:if>
									<c:if test="${o.id ne user.carColor }">
										<option value='${o.id }'>${o.name }</option>
									</c:if>
								</c:forEach>
							</select> <input id="carColor" name="carColor" type="text"
								data-bv-notempty data-bv-notempty-message="请选择车辆颜色"
								value="${user.carColor }" class="lfwer-hidden">
						</div>
					</div>
				</div>
				<div class="form-group">
					<div class="col-xs-12">
						<div class="input-group">
							<span class="input-group-addon">车牌号码 <span
								class="lfwer-seize">*</span></span><span class="input-group-addon"
								style="cursor: pointer; color: blue;" onclick=""
								data-toggle="modal" data-target="#carModal3" id="carProvinceC">${carProvinceName }</span><input
								type="text" id="carNum" name="carNum" class="form-control"
								style="border-radius: 0 3px 3px 0;" placeholder="请填写车牌号码"
								value="${user.carNum }" data-bv-regexp
								data-bv-regexp-regexp="^[A-Z]{1}[A-Z_0-9]{5}$"
								data-bv-regexp-message="车牌号码格式不正确"> <input
								id="carProvince" name="carProvince" type="text"
								data-bv-notempty data-bv-notempty-message="请选择车牌归属地"
								value="${user.carProvince}" class="lfwer-hidden">
							<!-- ^[\u4e00-\u9fa5]{1}[A-Z]{1}[A-Z_0-9]{5}$ -->
						</div>
					</div>
				</div>
				<div class="form-group">
					<div class="col-xs-12">
						<div style="display: table;">
							<span class="input-group-addon">车辆图片 <span
								class="lfwer-seize">*</span></span>
							<div class="input-group form-control"
								style="border-radius: 0 3px 3px 0;">
								<table>
									<tr>
										<td align="center"><img
											src="${basePath }/login/getCarPhoto?id=${user.id}&name=${user.carPhotoSmall1}"
											id="imgCarPhoto1" width="100" height="100"
											class="img-thumbnail" alt="查看大图"
											style="cursor: pointer; width: 100px; height: 100px;"
											onerror="noCar(this);" onclick="showBigCarPhoto(1)"></td>
										<td width="10px">&nbsp;</td>
										<td align="center"><img
											src="${basePath }/login/getCarPhoto?id=${user.id}&name=${user.carPhotoSmall2}"
											id="imgCarPhoto2" width="100" height="100"
											class="img-thumbnail"
											style="cursor: pointer; width: 100px; height: 100px;"
											onerror="noCar(this);" onclick="showBigCarPhoto(2)"></td>
									</tr>
									<tr>
										<td colspan="3" height="6px;"></td>
									</tr>
									<tr>
										<td align="center"><div id="divCarPhoto1"></div></td>
										<td width="10px">&nbsp;</td>
										<td align="center"><div id="divCarPhoto2"></div></td>
									</tr>
								</table>
							</div>
						</div>
					</div>
				</div>
				<div class="form-group">
					<div class="col-xs-12">
						<div style="display: table;">
							<span class="input-group-addon">行驶证 <span
								class="lfwer-seize">⊙*</span></span>
							<div class="input-group form-control"
								style="border-radius: 0 3px 3px 0;">
								<table>
									<tr>
										<td align="center"><img
											src="${basePath }/login/getDrivingBookPhoto?id=${user.id}&name=${user.drivingBookPhotoSmall}"
											id="imgDrivingBookPhoto" width="100" height="100"
											class="img-thumbnail" alt="查看大图"
											style="cursor: pointer; width: 100px; height: 100px;"
											onerror="noCar(this);" onclick="showDrivingBookPhoto()"></td>
									</tr>
									<tr>
										<td colspan="3" height="6px;"></td>
									</tr>
									<tr>
										<td align="center"><div id="divDrivingBookPhoto"></div></td>
									</tr>
								</table>
							</div>
						</div>
					</div>
				</div>
				<div class="form-group text-center">
					<div class="col-xs-12">
						<button type="submit" class="btn btn-success btn-block">
							<span class="glyphicon glyphicon-leaf"></span> 保 存
						</button>
					</div>
				</div>
			</form>
		</div>
	</div>
</div>
<script type="text/javascript">
	var _carPhoto1;
	var _carPhoto2;
	var _drivingBookPhoto;
	var myScrollInfo;
	$(document).ready(function() {
		
		document.addEventListener('touchmove', function(e) {
			e.preventDefault();
		}, false);
		myScrollInfo = new IScroll('#wrapperInfo', {
			//probeType: 2, //probeType：1对性能没有影响。在滚动事件被触发时，滚动轴是不是忙着做它的东西。probeType：2总执行滚动，除了势头，反弹过程中的事件。这类似于原生的onscroll事件。probeType：3发出的滚动事件与到的像素精度。注意，滚动被迫requestAnimationFrame（即：useTransition：假）。  
			scrollbars : true,
			mouseWheel : true,
			interactiveScrollbars : true,
			shrinkScrollbars : 'scale',
			fadeScrollbars : true,
			preventDefault : false
		});
		
		
		$('#divCarPhoto1').diyUpload({
			// 选完文件后，是否自动上传。
			auto : true,
			url : '${basePath}/login/uploadCarPhoto?_type=1',
			success : function(data) {
				_carPhoto1 = data;
				$("#imgCarPhoto1").attr(
						"src",
						'${basePath}/login/getCarPhoto?id=${user.id}&name='
								+ data.small);
			},
			error : function(err) {
				alertMsg("上传失败");
			},
			thumb : {
				width : 100,
				height : 100,
				// 图片质量，只有type为`image/jpeg`的时候才有效。
				quality : 100,
				// 是否允许放大，如果想要生成小图的时候不失真，此选项应该设置为false.
				allowMagnify : false,
				// 是否允许裁剪。
				crop : true,
				// 为空的话则保留原有图片格式。
				// 否则强制转换成指定的类型。
				type : "image/jpeg"
			},
			compress : false,
			buttonText : '选择图片',
			//最大上传的文件数量, 总文件大小,单个文件大小(单位字节);
			fileNumLimit : 1,
			fileSizeLimit : 1024 * 1024 * 5,
			fileSingleSizeLimit : 1024 * 1024 * 4
		});
		$('#divCarPhoto2').diyUpload({
			// 选完文件后，是否自动上传。
			auto : true,
			url : '${basePath}/login/uploadCarPhoto?_type=2',
			success : function(data) {
				_carPhoto2 = data;
				$("#imgCarPhoto2").attr(
						"src",
						'${basePath}/login/getCarPhoto?id=${user.id}&name='
								+ data.small);
			},
			error : function(err) {
				alertMsg("上传失败");
			},
			thumb : {
				width : 100,
				height : 100,
				// 图片质量，只有type为`image/jpeg`的时候才有效。
				quality : 70,
				// 是否允许放大，如果想要生成小图的时候不失真，此选项应该设置为false.
				allowMagnify : false,
				// 是否允许裁剪。
				crop : true,
				// 为空的话则保留原有图片格式。
				// 否则强制转换成指定的类型。
				type : "image/jpeg"
			},
			// 修改后图片上传前，尝试将图片压缩到 
			compress : false,
			buttonText : '选择图片',
			//最大上传的文件数量, 总文件大小,单个文件大小(单位字节);
			fileNumLimit : 1,
			fileSizeLimit : 1024 * 1024 * 5,
			fileSingleSizeLimit : 1024 * 1024 * 4
		});
		$('#divDrivingBookPhoto').diyUpload({
			// 选完文件后，是否自动上传。
			auto : true,
			url : '${basePath}/login/uploadDrivingBookPhoto',
			success : function(data) {
				_drivingBookPhoto = data;
				$("#imgDrivingBookPhoto").attr(
						"src",
						'${basePath}/login/getDrivingBookPhoto?id=${user.id}&name='
								+ data.small);
			},
			error : function(err) {
				alertMsg("上传失败");
			},
			thumb : {
				width : 100,
				height : 100,
				// 图片质量，只有type为`image/jpeg`的时候才有效。
				quality : 70,
				// 是否允许放大，如果想要生成小图的时候不失真，此选项应该设置为false.
				allowMagnify : false,
				// 是否允许裁剪。
				crop : true,
				// 为空的话则保留原有图片格式。
				// 否则强制转换成指定的类型。
				type : "image/jpeg"
			},
			// 修改后图片上传前，尝试将图片压缩到 
			compress : false,
			buttonText : '选择图片',
			//最大上传的文件数量, 总文件大小,单个文件大小(单位字节);
			fileNumLimit : 1,
			fileSizeLimit : 1024 * 1024 * 5,
			fileSingleSizeLimit : 1024 * 1024 * 4
		});
		
		$("#btnBreak").click(function() {
			location.href = "${basePath}/index.jsp";
		});

		$('#form1').bootstrapValidator({
			message : '验证失败',
			feedbackIcons : {
				//valid : 'glyphicon glyphicon-ok',
				//invalid : 'glyphicon glyphicon-remove',
				validating : 'glyphicon glyphicon-refresh'
			}
		}).on('error.field.bv', function(e, data) {
			myScrollInfo.refresh();
		}).on('success.field.bv', function(e, data) {
			myScrollInfo.refresh();
		});

		$('#carColorList').mobiscroll().select({
			theme : '', // Specify theme like: theme: 'ios' or omit setting to use default 
			mode : 'scroller', // Specify scroller mode like: mode: 'mixed' or omit setting to use default 
			display : 'bottom', // Specify display mode like: display: 'bottom' or omit setting to use default 
			lang : 'zh', // Specify language like: lang: 'pl' or omit setting to use default 
			inputClass : 'form-control', //为插件生成的input添加样式
			//placeholder: '请选择从事行业',//placeholder
			headerText : function(valueText) {
				return "选择车辆颜色";
			},
			onSelect : function(valueText, inst) {
				$("#carColorList > option").each(function() {
					if ($(this).text() == valueText) {
						$("#carColor").val($(this).val());
						return false;
					}
				});
				$("#carColor").keyup();
			}
		});
		
		$('#uploadCarPhotoModal').modal('hide').css({'margin-top' : "0"}); 
 		
		$("#carTypeList").mobiscroll().treelist({
			theme : '', // Specify theme like: theme: 'ios' or omit setting to use default 
			mode : 'scroller', // Specify scroller mode like: mode: 'mixed' or omit setting to use default 
			display : 'bottom', // Specify display mode like: display: 'bottom' or omit setting to use default 
			lang : 'zh', // Specify language like: lang: 'pl' or omit setting to use default 
			//defaultValue:[],
			inputClass : 'form-control', //为插件生成的input添加样式
			//placeholder: '请选择从事行业',//placeholder
			headerText : function(valueText) {
				return "选择认证车型";
			},
			//rows:10,
			onSelect : function(valueText, inst) {
				if(valueText != ""){
					var arr = valueText.split(" ");
					var carBrand = arr[0];
					var carType = arr[1];
					$(".carBrand").each(function() {
						if ($(this).text() == carBrand) {
							$("#carBrand").val($(this).attr("id"));
							return false;
						}
					});
					$(".carType").each(function() {
						if ($(this).text() == carType) {
							$("#carType").val($(this).attr("id"));
							return false;
						}
					});
				}else{
					$("#carType").val("");
					$("#carBrand").val("");
				}
				$("#carType").keyup();
			},
			formatResult : function(array) {
				return $('#carTypeList>li').eq(array[0]).children('span').text() + ' '
						+ $('#carTypeList>li').eq(array[0]).find('ul li').eq(array[1]).text().trim(' ');
			}
		});
		
		if("${user.carBrand}" != ""){
			var b,t;
			$(".carBrand").each(function() {
				if ($(this).attr("id") == "${user.carBrand}") {
					b = $(this).text();
					return false;
				}
			});
			$(".carType").each(function() {
				if ($(this).attr("id") == "${user.carType}") {
					t = $(this).text();
					return false;
				}
			});
			$("#carTypeList_dummy").val(b+" "+t);
		}
		
		$(".chooseCarProvince").click(function(){
			$("#carProvinceC").text($(this).text());
			$("#carProvince").val($(this).val());
			$("#carModal3").modal('hide');
		});
	});

	function showBigCarPhoto(v) {
		var name;
		if (v == 1) {
			name = _carPhoto1 ? _carPhoto1.large : "${user.carPhotoLarge1}";
		} else if (v == 2) {
			name = _carPhoto2 ? _carPhoto2.large : "${user.carPhotoLarge2}";
		}
		$("#imgCarPhoto").attr("src",
				"${basePath}/login/getCarPhoto?id=${user.id}&name=" + name);
		$("#uploadCarPhotoModal").modal("show");
	}
	
	function showDrivingBookPhoto(){
		var name = _drivingBookPhoto ? _drivingBookPhoto.large : "${user.drivingBookPhotoLarge}";
		$("#imgCarPhoto").attr("src",
				"${basePath}/login/getDrivingBookPhoto?id=${user.id}&name=" + name);
		$("#uploadCarPhotoModal").modal("show");
	}
</script>