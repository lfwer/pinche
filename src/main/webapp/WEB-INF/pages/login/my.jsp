<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/common/taglib.jsp"%>
<style>
#my-list :FIRST-CHILD {
	border-top-left-radius: 0;
	border-top-right-radius: 0;
}

#my-list :LAST-CHILD {
	border-bottom-left-radius: 0;
	border-bottom-right-radius: 0;
}

.userTypeC{
	width: 100%;
	border: 0;
	text-align: right;
}
</style>
<c:if test="${empty user }">
	<div class="container">
		<div class="row">
			<div class="col-xs-12">
				<button type="button" class="btn btn-success btn-block btn-lg"
					style="margin-top: 50%;"
					onclick="gotoUrl1('${basePath}/login/signIn')">
					<span class="glyphicon glyphicon-user"></span> 登录
				</button>
			</div>
		</div>
	</div>
</c:if>
<c:if test="${not empty user }">
	<table style="width: auto; margin: 10px;">
		<tr>
			<td><img id="imgHeadMy"
				src="${basePath }/login/getPhoto?id=${user.id}&name=${user.photoSmall}"
				class="img-circle" width="80" height="80"
				style="width: 80px; height: 80px; cursor: pointer;"
				onerror="nohead(this);" onclick="showBigHeadPhoto()"></td>
			<td valign="bottom">&nbsp;${user.nickName }</td>
		</tr>
	</table>
	<ul id="my-list" class="list-group" style="margin-top: 10px;">
		<li class="list-group-item"
			onclick="gotoUrl1('${basePath}/login/register2')">基本资料<span
			class="pull-right glyphicon glyphicon-menu-right"></span></li>
		<li class="list-group-item"
			onclick="gotoUrl1('${basePath}/login/register3')">车主认证 <span
			class="pull-right glyphicon glyphicon-menu-right"></span>
		</li>
		<li class="list-group-item" onclick="">身份
			<div class="pull-right">
				<select id="userType">
					<c:forEach var="o" items="${userTypeList }">
						<c:if test="${o.id eq user.type }">
							<option value="${o.id }" selected="selected">${o.name }</option>
						</c:if>
						<c:if test="${o.id ne user.type }">
							<option value="${o.id }">${o.name }</option>
						</c:if>
					</c:forEach>
				</select>
			</div>
		</li>
		<li class="list-group-item" data-toggle="modal"
			data-target="#signOutModal" style="text-align: center; color: red;">退出当前账号
		</li>
	</ul>
</c:if>

<script type="text/javascript">
	var _headPhotoMy;
	var jcrop_apiMy;
	var uploaderMy;
	
	$(document).ready(function() {
	 
		$('#userType').mobiscroll().select({
	        theme: '',     // Specify theme like: theme: 'ios' or omit setting to use default 
	        mode: 'scroller',       // Specify scroller mode like: mode: 'mixed' or omit setting to use default 
	        display: 'modal', // Specify display mode like: display: 'bottom' or omit setting to use default 
	        lang: 'zh',        // Specify language like: lang: 'pl' or omit setting to use default 
	        showLabel:false,
	        inputClass:'userTypeC', //为插件生成的input添加样式
	        //placeholder: '请选择从事行业',//placeholder
	        onSelect: function(valueText,inst){
	         	
	        }
		});
		
		
		uploaderMy = WebUploader.create({
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
		
		uploaderMy.on('fileQueued', function( file ) {
			//生成预览缩略图;
			uploaderMy.makeThumb( file, function( error, src ) {
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
						jcrop_apiMy = this;
						jcrop_apiMy.setImage(src,function() {
							var bounds = jcrop_apiMy
									.getBounds();
							boundx = bounds[0];
							boundy = bounds[1];
							if(boundx<80 || boundy<80){
								alert("图片尺寸过小，请选择80*80以上规格的图片！");
								jcrop_apiMy.destroy();
								uploaderMy.reset();
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
							jcrop_apiMy.setSelect([x,y,x1,y1]);
							var h = jcrop_apiMy.getWidgetSize()[1];//图片显示高度
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
		uploaderMy.on('error', function( code ) {
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
		uploaderMy.on('uploadSuccess',function( file, data ){
			_headPhotoMy = data;
				$("#imgHeadMy").attr("src","${basePath}/login/getPhoto?id=${user.id}&name="+data.small);	
				$("#headPhotoModal").modal("hide");
		});
		 
		$("#photoEdt>.webuploader-pick").css({
			background : 'transparent'
		});
		 
		$('#headPhotoModal').on('hidden.bs.modal', function(e) {
			jcrop_apiMy.destroy();
			uploaderMy.reset();
		});
		
		
	});
 
	function showBigHeadPhoto() {
		var name = _headPhotoMy ? _headPhotoMy.large : "${user.photoLarge}";
		$("#uploadCarPhotoModal").modal("show");
		$("#imgPhoto").attr("src",
				"${basePath}/login/getPhoto?id=${user.id}&name=" + name);
	}

	function savePhoto() {
		var o = jcrop_apiMy.tellSelect();
		o.x = Math.round(o.x);
		o.y = Math.round(o.y);
		o.x2 = Math.round(o.x2);
		o.y2 = Math.round(o.y2);
		o.w = Math.round(o.w);
		o.h = Math.round(o.h);
		uploaderMy.option('formData', o);
		uploaderMy.upload();
	}

</script>