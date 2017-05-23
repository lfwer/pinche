<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/common/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<%@include file="/common/resource.jsp"%>
<title>${sysName }&nbsp;-&nbsp;完善信息</title>
<style>
#wrapperInfo {
	bottom: 0;
}
</style>
</head>
<body>
<div id="headerInfo">
	<div class="row">
		<div class="col-xs-2">
			<span class="glyphicon glyphicon-chevron-left"
				onclick="history.back();" style="cursor: pointer;">&nbsp;</span>
		</div>
		<div class="col-xs-8 text-center">
			<span>个人资料</span>
		</div>
		<div class="col-xs-2"></div>
	</div>
</div>	 
<div id="wrapperInfo">
	<div id="scrollerInfo">
		<div class="container">
			<form id="form1" class="form-horizontal" method="post"
				action="${basePath }/login/register2Submit">
				<div class="form-group">
					<div class="col-xs-12 text-center">
						<div id="photoEdt"
							style="cursor:pointer;width:24px;height:24px;position: absolute;top:0;left: -9999px;background: url(${basePath}/images/plus.png) no-repeat;border:0;padding:0;margin:0;">&nbsp;</div>
						<img id="imgHead"
							src="${basePath }/login/getPhoto?id=${user.id}&name=${user.photoSmall}"
							class="img-circle" width="80" height="80"
							style="width: 80px; height: 80px; cursor: pointer;"
							onerror="nohead(this);" onclick="showBigHeadPhoto()">
					</div>
				</div>
				<div class="form-group">
					<div class="col-xs-12">
						<div class="input-group">
							<span class="input-group-addon">身份<span
								class="lfwer-seize">⊙⊙</span> <span class="lfwer-required">*</span>
							</span>
							<div class="btn-group"
								style="border-radius: 0 3px 3px 0;">
								<c:forEach var="o" items="${userTypeList }" varStatus="index">
									<c:if test="${index.index == 0 }">
										<button name="typeC" type="button" class="btn btn-default"
											value="${o.id }"
											style="border-top-left-radius: 0; border-bottom-left-radius: 0;">
											${o.name }</button>
									</c:if>
									<c:if test="${index.index > 0 }">
										<button name="typeC" type="button" class="btn btn-default"
											value="${o.id }">${o.name }</button>
									</c:if>
								</c:forEach>
								<input id="type" name="type" type="text" class="lfwer-hidden"
									data-bv-notempty data-bv-notempty-message="请选择身份"
									value="${user.type }">
							</div>
						</div>
					</div>
				</div>
				
				<%-- <div class="form-group">
					<div class="col-xs-12">
						<div class="input-group">
							<span class="input-group-addon">姓名<span
								class="lfwer-seize">⊙⊙</span> <span
								class="lfwer-required">*</span>
							</span><input type="text" id="realName" name="realName"
								class="form-control" style="border-radius: 0 3px 3px 0;"
								placeholder="请填写姓名" data-bv-notempty
								data-bv-notempty-message="请填写姓名" value="${user.realName }"
								data-bv-stringlength data-bv-stringlength-max="12"
								data-bv-stringlength-message="姓名应小于12字">
						</div>
					</div>
				</div> --%>
				
				<%-- <div class="form-group">
					<div class="col-xs-12">
						<div class="input-group">
							<span class="input-group-addon">身份证号 <span
								class="lfwer-required">*</span>
							</span><input type="text" id="idCard" name="idCard" class="form-control"
								style="border-radius: 0 3px 3px 0;" placeholder="请填写身份证号"
								data-bv-notempty data-bv-notempty-message="请填写身份证号"
								value="${user.idCard }" data-bv-regexp
								data-bv-regexp-regexp="(^\d{15}$)|(^\d{17}(\d|X|x)$)"
								data-bv-regexp-message="身份证号格式不正确">
						</div>
					</div>
				</div> --%>

				<%-- <div class="form-group">
					<div class="col-xs-12">
						<div class="input-group">
							<span class="input-group-addon">现住地区 <span
								class="lfwer-required">*</span></span><select data-bv-notempty
								data-bv-notempty-message="请选择现住地区" class="form-control"
								id="zone" name="zone" >
								<option value=''>- 请选择 -</option>
								<c:forEach var="o" items="${zoneList }">
									<option value='${o.id }'>${o.name }</option>
								</c:forEach>
							</select>
						</div>
					</div>
				</div> --%>
				
				<%-- <div class="form-group">
					<div class="col-xs-12">
						<div class="input-group">
							<span class="input-group-addon">详细地址 <span
								class="lfwer-required">*</span></span><input type="text" id="addr"
								name="addr" class="form-control" placeholder="详细地址"
								data-bv-notempty data-bv-notempty-message="请填写详细地址"
								value="${user.addr }" data-bv-stringlength
								data-bv-stringlength-max="20"
								data-bv-stringlength-message="详细地址应小于20字">
						</div>
					</div>
				</div> --%>

				<div class="form-group">
					<div class="col-xs-12">
						<div class="input-group">
							<span class="input-group-addon">昵称<span
								class="lfwer-seize">⊙⊙</span> <span class="lfwer-required">*</span>
							</span><input type="text" id="nickName" name="nickName"
								class="form-control" style="border-radius: 0 3px 3px 0;"
								placeholder="方便拼车时联系" data-bv-notempty
								data-bv-notempty-message="请填写昵称" value="${user.nickName }"
								data-bv-stringlength data-bv-stringlength-max="12" data-bv-stringlength-min="2" 
								data-bv-stringlength-message="昵称长度应介于2-12个字符之间">
						</div>
					</div>
				</div>
				<div class="form-group">
					<div class="col-xs-12">
						<div style="display: table;">
							<span class="input-group-addon">性别 <span
								class="lfwer-seize">⊙⊙*</span>
							</span>
							<%-- <div class="input-group lfwer-addon-radio-checkbox-control"
								style="border-radius: 0 3px 3px 0;">
								<div class="mini-radiobuttonlist"
									url="${basePath }/dict/getDicts?type=SEX&pid=-1"
									textField="name" valueField="id" value="${user.sex }"
									onvaluechanged="radioValueChg(e,'sex')"></div>
								<input id="sex" name="sex" type="text" class="lfwer-hidden"
									value="${user.sex }">
							</div> --%>
							<div class="btn-group" style="border-radius: 0 3px 3px 0;">
								<c:forEach var="o" items="${sexList }" varStatus="index">
									<c:if test="${index.index == 0 }">
										<button name="sexC" type="button" class="btn btn-default"
											value="${o.id }"
											style="border-top-left-radius: 0; border-bottom-left-radius: 0;">
											${o.name }</button>
									</c:if>
									<c:if test="${index.index > 0 }">
										<button name="sexC" type="button" class="btn btn-default"
											value="${o.id }">${o.name }</button>
									</c:if>
								</c:forEach>
								<input id="sex" name="sex" type="text" class="lfwer-hidden"
									value="${user.sex }">
							</div>
						</div>
					</div>
				</div>

				<div class="form-group">
					<div class="col-xs-12">
						<div class="input-group">
							<span class="input-group-addon">生日 <span
								class="lfwer-seize">⊙⊙*</span>
							</span><input name="birthday"
								id="birthday" class="form-control" style="border-radius: 0;"
								readonly type="text"
								value="<fmt:formatDate value="${user.birthday }" pattern="yyyy-MM-dd" />"
								onchange="getAge(this.value)">
						</div>
					</div>
				</div>
				<div class="form-group">
					<div class="col-xs-12">
						<div class="input-group">
							<span class="input-group-addon">年龄 <span
								class="lfwer-seize">⊙⊙*</span></span> <input type="text" id="age"
								name="age" class="form-control"
								style="border-radius: 0 3px 3px 0;" placeholder="请填写年龄"
								data-bv-regexp data-bv-regexp-regexp="[0-9]+$"
								data-bv-regexp-message="年龄只能输入数字" data-bv-lessthan
								data-bv-lessthan-value="150" data-bv-lessthan-message="岁数有点大哦"
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
							<%-- <div class="input-group lfwer-addon-radio-checkbox-control"
								style="border-radius: 0 3px 3px 0;">
								<div class="mini-radiobuttonlist"
									url="${basePath }/dict/getDicts?type=MARRY&pid=-1"
									textField="name" valueField="id" value="${user.marry }"
									onvaluechanged="radioValueChg(e,'marry')"></div>
								<input id="marry" name="marry" type="text" class="lfwer-hidden"
									value="${user.marry }">
							</div> --%>
							<div class="btn-group" style="border-radius: 0 3px 3px 0;">
								<c:forEach var="o" items="${marryList }" varStatus="index">
									<c:if test="${index.index == 0 }">
										<button name="marryC" type="button" class="btn btn-default"
											value="${o.id }"
											style="border-top-left-radius: 0; border-bottom-left-radius: 0;">
											${o.name }</button>
									</c:if>
									<c:if test="${index.index > 0 }">
										<button name="marryC" type="button" class="btn btn-default"
											value="${o.id }">${o.name }</button>
									</c:if>
								</c:forEach>
								<input id="marry" name="marry" type="text" class="lfwer-hidden"
									value="${user.marry }">
							</div>
						</div>
					</div>
				</div>
				<div class="form-group">
					<div class="col-xs-12">
						<div class="input-group">
							<span class="input-group-addon">从事行业 <span
								class="lfwer-seize">*</span></span> <select id="industryList">
									<option value="">-- 请选择从事行业 --</option>
								<c:forEach var="o" items="${industryList }">
									<c:if test="${o.id eq user.industry }">
										<option value="${o.id }" selected="selected">${o.name }</option>
									</c:if>
									<c:if test="${o.id ne user.industry }">
										<option value="${o.id }">${o.name }</option>
									</c:if>
								</c:forEach>
							</select>
							<input id="industry" name="industry" type="text" class="lfwer-hidden"
								 value="${user.industry }">
						</div>
					</div>
				</div>
				<div class="form-group">
					<div class="col-xs-12">
						<div style="display: table;">
							<span class="input-group-addon">兴趣爱好 <span
								class="lfwer-seize">*</span>
							</span>
							<%-- <div class="input-group lfwer-addon-radio-checkbox-control"
								style="border-radius: 0 3px 3px 0;">
								<div class="mini-checkboxlist"
									url="${basePath }/dict/getDicts?type=HOBBY&pid=-1"
									textField="name" valueField="id" value="${user.hobby }"
									onvaluechanged="radioValueChg(e,'hobby')"></div>
								<input id="hobby" name="hobby" type="text" class="lfwer-hidden"
									value="${user.hobby }">
							</div> --%>
							<div class="btn-group" style="border-radius: 0 3px 3px 0;" data-toggle="buttons-checkbox">
								<c:forEach var="o" items="${hobbyList }" varStatus="index">
									<c:if test="${index.index == 0 }">
										<button name="hobbyC" type="button" class="btn btn-default"
											value="${o.id }"
											style="border-top-left-radius: 0; border-bottom-left-radius: 0;">
											${o.name }</button>
									</c:if>
									<c:if test="${index.index > 0 }">
										<button name="hobbyC" type="button" class="btn btn-default"
											value="${o.id }">${o.name }</button>
									</c:if>
								</c:forEach>
								<input id="hobby" name="hobby" type="text" class="lfwer-hidden"
									value="${user.hobby }">
							</div>
						</div>
					</div>
				</div>
				<div class="form-group">
					<div class="col-xs-12">
						<div class="input-group">
							<span class="input-group-addon">个性签名 <span
								class="lfwer-seize">*</span>
							</span>
							<textarea class="form-control" name="sign" id="sign" rows="3"
								data-bv-stringlength data-bv-stringlength-max="50"
								placeholder="请填写个性签名" data-bv-stringlength-message="个性签名长度应小于50个字符">${user.sign }</textarea>
						</div>
					</div>
				</div>


				<div class="form-group text-center">
					<div class="col-xs-12">
						<button type="submit" class="btn btn-success">保存</button>
						<button type="button" class="btn btn-warning"
							style="margin-left: 20px;" data-toggle="modal"
							data-target="#myModal">跳过</button>
					</div>
				</div>
			</form>
		</div>
	</div>
</div>

<div class="modal fade" id="myModal" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-body">如果不完善个人信息，系统将无法匹配您感兴趣的 [车主] / [乘客] 哦
				^_^</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-primary" data-dismiss="modal">继续完善</button>
				<button type="button" class="btn btn-default" id="btnBreak">残忍跳过
				</button>
			</div>
		</div>
	</div>
</div>

<div class="modal fade" id="uploadCarPhotoModal" tabindex="-1"
	role="dialog" aria-labelledby="uploadCarPhotoModalLabel"
	aria-hidden="true">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-body" style="margin: 0;padding: 0;">
				<center>
					<img id="imgPhoto" class="img-thumbnail" 
						onclick="$('#uploadCarPhotoModal').modal('hide');"
						onerror="noFind(this);">
				</center>
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
					<button type="button" class="btn btn-primary"
						onclick="savePhoto()">确定</button>
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
				</div>
			</div>
		</div>
	</div>
</div>
<script type="text/javascript">
	var _headPhoto;
	var jcrop_api;
	var uploader;
	var myScrollInfo;
	
	window.onresize = function() {
		resizePhotoEdt();
	}
	
	function resizePhotoEdt(){
		$("#photoEdt").css({
			left : function() {
				return $("#imgHead").position().left + 55;
			}
		});
	}
	
	$(document).ready(function() {
		//mini.parse();
		resizePhotoEdt();
		//$("#zone").val("${user.zone }");
						
		
		myScrollInfo = new IScroll('#wrapperInfo', {
			//probeType: 2, //probeType：1对性能没有影响。在滚动事件被触发时，滚动轴是不是忙着做它的东西。probeType：2总执行滚动，除了势头，反弹过程中的事件。这类似于原生的onscroll事件。probeType：3发出的滚动事件与到的像素精度。注意，滚动被迫requestAnimationFrame（即：useTransition：假）。  
			scrollbars : true,
			mouseWheel : true,
			interactiveScrollbars : true,
			shrinkScrollbars : 'scale',
			fadeScrollbars : true,
			preventDefault : false
		});
		
		uploader = WebUploader.create({
			auto:false,
			method:'post',
			server:'${basePath}/login/uploadPhoto',
			pick:{
				id:"#photoEdt",
				label:" ",
				multiple : false
			},
			//类型限制;
			accept:{
				title:"Images",
				extensions:"jpg,jpeg,bmp,png",
				mimeTypes:"image/*"
			},
			thumb : {
				width :  1,
				height :  1,
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
			//是否已二进制的流的方式发送文件，这样整个上传内容php://input都为文件内容
			sendAsBinary:false,
			//文件上传方式
			method:"POST",
			//最大上传的文件数量, 总文件大小,单个文件大小(单位字节);
			fileNumLimit : 1,
			fileSizeLimit : 1024 * 1024 * 5,
			fileSingleSizeLimit : 1024 * 1024 * 5
		});
		
		uploader.on('fileQueued', function( file ) {
			//生成预览缩略图;
			uploader.makeThumb( file, function( error, src ) {
				if (!error) {	
					$('#imgHeadPhoto').Jcrop({
						bgFade : true,
						aspectRatio : 1,
						allowSelect : false,
						allowMove : true,
						allowResize : true,
						boxWidth : $(this).width()-100,
						boxHeight : $(this).height()-100,
						handleOpacity : 0.6,
						handleSize : 9,
						minSize : [ 80,80 ]
					},function() {
						jcrop_api = this;
						jcrop_api.setImage(src,function() {
							var bounds = jcrop_api
									.getBounds();
							boundx = bounds[0];
							boundy = bounds[1];
							if(boundx<80 || boundy<80){
								alert("图片尺寸过小，请选择80*80以上规格的图片！");
								jcrop_api.destroy();
								uploader.reset();
								return;
							}
							$("#headPhotoModal").modal("show");
							var x,y,x1,y1;
							if(boundx<boundy){//用宽度
								x = 0;
								y = Math.round(boundy/2-boundx/2);
								x1 = Math.round(boundx);
								y1 = Math.round(boundy/2+boundx/2);
							}else{//用高度
								x = Math.round(boundx/2-boundy/2);
								y = 0;
								x1 = Math.round(boundx/2+boundy/2);
								y1 = Math.round(boundy);
							}
							jcrop_api.setSelect([x,y,x1,y1]);
							var h = jcrop_api.getWidgetSize()[1];//图片显示高度
							$('#headPhotoModal').css({
								'margin-top' : function() {
									var top = ($(this).height()/2-h/2)-40;
									return top<0?0:top;
								}
							});
						});
					});
				}else{
					alert("图片无法加载！");
				}
			});	
		
		});
		uploader.on('error', function( code ) {
			var text = '';
			switch( code ) {
				case  'F_DUPLICATE' : text = '该文件已经被选择了!' ;
				break;
				case  'Q_EXCEED_NUM_LIMIT' : text = '上传文件数量超过限制!' ;
				break;
				case  'F_EXCEED_SIZE' : text = '文件大小超过限制!';
				break;
				case  'Q_EXCEED_SIZE_LIMIT' : text = '所有文件总大小超过限制!';
				break;
				case 'Q_TYPE_DENIED' : text = '文件类型不正确或者是空文件!';
				break;
				default : text = '未知错误!';
					break;	
			}
           	alert( text );
       	}); 
		//上传成功后触发事件;
		uploader.on('uploadSuccess',function( file, data ){
			_headPhoto = data;
				$("#imgHead").attr("src","${basePath}/login/getPhoto?id=${user.id}&name="+data.small);	
				$("#headPhotoModal").modal("hide");
		});
		 
		$("#photoEdt>.webuploader-pick").css({
			background : 'transparent'
		});
		/*
		$("input[name=type][value='${user.type}']").attr(
				"checked", 'checked');
		$("input[name=sex][value='${user.sex}']").attr(
				"checked", 'checked');
		 */

		$("#btnBreak").click(function() {
			location.href = "${basePath}/index.jsp";
		});

		$('#myModal').modal('hide').css({
			'margin-top' : function() {
				return ($(this).height() / 2-150);
			}
		});

		//隐藏模式窗口事件
		$('#uploadCarPhotoModal').on('hidden.bs.modal',
				function(e) {
					$("#imgPhoto").removeAttr("src");
				});
		$('#headPhotoModal').on('hidden.bs.modal', function(e) {
			jcrop_api.destroy();
			uploader.reset();
		});

		$('#birthday').mobiscroll().date({
                  theme: '',     // Specify theme like: theme: 'ios' or omit setting to use default 
                  mode: 'scroller',       // Specify scroller mode like: mode: 'mixed' or omit setting to use default 
                  display: 'modal', // Specify display mode like: display: 'bottom' or omit setting to use default 
                  lang: 'zh',        // Specify language like: lang: 'pl' or omit setting to use default 
                  dateFormat: 'yy-mm-dd', // 日期格式
                  minDate:mini.parseDate('1900-01-01'),
                  maxDate:mini.parseDate('<%=new SimpleDateFormat("yyyy-MM-dd").format(new Date())%>'),
                  headerText: function (valueText) { return "选择生日"; } 
		});
		
		$('#industryList').mobiscroll().select({
            theme: '',     // Specify theme like: theme: 'ios' or omit setting to use default 
            mode: 'scroller',       // Specify scroller mode like: mode: 'mixed' or omit setting to use default 
            display: 'modal', // Specify display mode like: display: 'bottom' or omit setting to use default 
            lang: 'zh',        // Specify language like: lang: 'pl' or omit setting to use default 
            //defaultValue:"",
            inputClass:'form-control', //为插件生成的input添加样式
            //placeholder: '请选择从事行业',//placeholder
            headerText: function (valueText) { return "选择从事行业"; },
            onSelect: function(valueText,inst){
            	$("#industryList > option").each(function(){
            		if($(this).text()==valueText){
            			$("#industry").val($(this).val());
            			return false;
            		}
            	});
            	$("#industry").keyup();
            }
		});
		
		$('#form1').bootstrapValidator({
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
							url : lfwer.rootName + "/login/validateNickName?_r=" + new Date().getTime(),
							type : "post",
							delay : 1000,
							message : "该昵称已经被使用了，请换一个吧"
						}
					}
				}
			}
		}).on('error.field.bv', function(e, data) {
			myScrollInfo.refresh();
		}).on('success.field.bv', function(e, data) {
			myScrollInfo.refresh();
		});
		
		$('#uploadCarPhotoModal').modal('hide').css({
			'margin-top' : function() {
				return ($(this).height()/2-180);
			}
		}); 
		
		
		$("button[name='typeC']").click(function(){
			$("button[name='typeC']").removeClass('btn-primary').addClass('btn-default'); 
			$(this).removeClass('btn-default').addClass('btn-primary');
			$("#type").val($(this).val());
			$("#type").keyup();
		});
		$("button[name='sexC']").click(function(){
			$("button[name='sexC']").removeClass('btn-primary').addClass('btn-default'); 
			$(this).removeClass('btn-default').addClass('btn-primary');
			$("#sex").val($(this).val());
			$("#sex").keyup();
		});
		$("button[name='marryC']").click(function(){
			$("button[name='marryC']").removeClass('btn-primary').addClass('btn-default'); 
			$(this).removeClass('btn-default').addClass('btn-primary');
			$("#marry").val($(this).val());
			$("#marry").keyup();
		});
		$("button[name='hobbyC']").click(function(){
			if($(this).hasClass("btn-primary")){
				$(this).removeClass('btn-primary').addClass('btn-default');
			}else{
				$(this).removeClass('btn-default').addClass('btn-primary');
			}
			$("#hobby").val("");
			var v = "";
			$("button[name='hobbyC']").each(function(index,element){
				if($(this).hasClass("btn-primary")){
					if(index>0){
						v += ",";
					}
					v += $(this).val();
				}
			});
			$("#hobby").val(v);
			$("#hobby").keyup();
		});
		
		//初始化赋值
		$("button[name='typeC']").each(function(){
			if("${user.type}" == $(this).val()){
				$(this).removeClass('btn-default').addClass('btn-primary');
			}
		});
		$("button[name='sexC']").each(function(){
			if("${user.sex}" == $(this).val()){
				$(this).removeClass('btn-default').addClass('btn-primary');
			}
		});
		$("button[name='marryC']").each(function(){
			if("${user.marry}" == $(this).val()){
				$(this).removeClass('btn-default').addClass('btn-primary');
			}
		});
		var arrHobby = "${user.hobby}".split(",");
		$("button[name='hobbyC']").each(function(){
			a:for(var i = 0; i< arrHobby.length; i++){
				if(arrHobby[i] == $(this).val()){
					$(this).removeClass('btn-default').addClass('btn-primary');
					break a;
				}
			}
			 
		});
		 
	});
	function radioValueChg(e, id) {
		$("#" + id).val(e.value);
		$("#" + id).keyup();
	}

	function showBigHeadPhoto() {
		var name = _headPhoto ? _headPhoto.large : "${user.photoLarge}";
		$("#uploadCarPhotoModal").modal("show");
		$("#imgPhoto").attr("src",
				"${basePath}/login/getPhoto?id=${user.id}&name=" + name);
	}

	function savePhoto() {
		var o = jcrop_api.tellSelect();
		o.x = Math.round(o.x);
		o.y = Math.round(o.y);
		o.x2 = Math.round(o.x2);
		o.y2 = Math.round(o.y2);
		o.w = Math.round(o.w);
		o.h = Math.round(o.h);
		uploader.option('formData', o);
		uploader.upload();
	}

	function getAge(v) {
		if (v != "") {
			var returnAge = 0;

			var birthYear = parseInt(v.substring(0, 4));
			var birthMonth = parseInt(v.substring(5, 7));
			var birthDay = parseInt(v.substring(10, 12));

			var nowYear = parseInt("${year}");
			var nowMonth = parseInt("${month}");
			var nowDay = parseInt("${day}");

			var ageDiff = nowYear - birthYear; //年之差
			if (ageDiff > 0) {
				if (nowMonth == birthMonth) {

					var dayDiff = nowDay - birthDay;//日之差
					if (dayDiff < 0) {
						returnAge = ageDiff - 1;
					} else {
						returnAge = ageDiff;
					}
				} else {
					var monthDiff = nowMonth - birthMonth;//月之差
					if (monthDiff < 0) {
						returnAge = ageDiff - 1;
					} else {
						returnAge = ageDiff;
					}
				}

			} else {
				returnAge = 0;
			}
			if (returnAge < 0) {
				$("#birthday").val("");
				$("#age").val("");
			} else {
				$("#age").val(returnAge);
			}

		}
	}
</script>
</body>
</html>