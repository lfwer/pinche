<div id="headerInfo">
	<div class="row">
		<div class="col-xs-2">
			<span class="glyphicon glyphicon-chevron-left"
				onclick="history.back();" style="cursor: pointer;">&nbsp;</span>
		</div>
		<div class="col-xs-8 text-center">
			<span>车主发布详情</span>
		</div>
		<div class="col-xs-2">
			<div class="pull-right">
				<span class="glyphicon glyphicon-trash" id="btn_trash" onclick="removeInfo('carOwner','${result.id }')"
					style="cursor: pointer;display: none;"></span>
			</div>
		</div>
	</div>
</div>
<div id="wrapperInfo" class="font-16">
	<div id="scrollerInfo">
		<div class="container">
			<div class="row">
				<div class="col-xs-12">
					<table style="font-size: 14px;">
						<tr>
							<td rowspan="2" valign="middle"><img id="viewinfo_img_user_head" 
								style="width: 50px; height: 50px;" class="img-circle"
								onerror="nohead(this);">&nbsp;&nbsp;</td>
							<td valign="bottom">
								${user.nickName }&nbsp;
								<#if result.sex = '♂'>
									<span class="badge-1">${result.sex!'-'}</span>&nbsp;
									<span class="badge-1">${result.age!'-'}</span>
								<#elseif result.sex = '♀'>
									<span class="badge-2">${result.sex!'-'}</span>&nbsp;
									<span class="badge-2">${result.age!'-'}</span>
								<#else>
									<span class="badge-3">${result.sex!'-'}</span>&nbsp;
									<span class="badge-3">${result.age!'-'}</span>
								</#if>
							</td>
						</tr>
						<tr>
							<td valign="bottom"><font color="gray">${result.addTime?string('yyyy-MM-dd hh:mm:ss') }&nbsp;&nbsp;&nbsp;&nbsp;<label
									class="glyphicon glyphicon-eye-open" title="浏览次数"></label>&nbsp;<span id="viewinfo_lookCount">${result.lookCount!0}</span></font></td>
						</tr>
					</table>
				</div>
			</div>
			<div class="row" style="height: 16px;"></div>
			<div class="row">
				<div class="col-xs-12" style="font-size: 20px;">
					<span style="color: gray; font-size: 16px;">从</span>&nbsp;&nbsp;${result.fromZoneName }&nbsp;&nbsp;<span
						style="color: gray; font-size: 16px;">到</span>&nbsp;&nbsp;${result.toZoneName }
				</div>
			</div>
			<div class="row" style="height: 16px;"></div>
			<div class="row">
				<div class="col-xs-3 text-nowrap left-label">途经地点</div>
				<div class="col-xs-8">${result.viaName }</div>
			</div>
			<div class="row">
				<div class="col-xs-3 text-nowrap left-label">联系人</div>
				<div class="col-xs-8">${result.contactUser }</div>
			</div>
			<div class="row">
				<div class="col-xs-3 text-nowrap left-label">联系电话</div>
				<div class="col-xs-9">${result.contacePhone }</div>
			</div>
			 
			<#if result.timeLimit == 1>
			<div class="row">
				<div class="col-xs-3 text-nowrap left-label">发车时间</div>
				<div class="col-xs-8">
					 ${result.pdate?string('yyyy-MM-dd') } ${result.ptime?string('HH:mm') } 
					&nbsp;&nbsp;<span class="timeago"
						style="color: gray; font-size: 14px;"
						title="${result.pdate?string('yyyy-MM-dd') } ${result.ptime?string('HH:mm') }"></span>
				</div>
			</div>
			<#else>
			<div class="row">
				<div class="col-xs-3 text-nowrap left-label">发车时间</div>
				<div class="col-xs-8">${result.pweekName }&nbsp;${result.ptime?string('HH:mm') }
				</div>
			</div>
			</#if>
			<div class="row">
				<div class="col-xs-3 text-nowrap left-label">车辆型号</div>
				<div class="col-xs-8">${result.carTypeName }，${result.carStyle }，${result.carColorName }</div>
			</div>
			<div class="row">
				<div class="col-xs-3 text-nowrap left-label">拼车价格</div>
				<div class="col-xs-8">
					<b>${result.cost }</b>&nbsp;元/人
				</div>
			</div>
			<div class="row">
				<div class="col-xs-3 text-nowrap left-label">剩余空位</div>
				<div class="col-xs-8">
					<b>${result.pnum }</b>&nbsp;位
				</div>
			</div>
			<div class="row">
				<div class="col-xs-12 text-nowrap left-label">车主留言</div>
			</div>
			<div class="row">
				<div class="col-xs-12">
					<a href="javascript:void(0)">联系我时，请说是在邯郸拼车网上看到的，谢谢！</a>
				</div>
			</div>
			<div class="row">
				<div class="col-xs-12" style="word-break: break-all;">${result.remark! }</div>
			</div>
			
			<div class="row">
				<div class="col-xs-12 text-nowrap left-label">车辆照片</div>
			</div>

			<div class="row" style="padding: 2px;">
				
				<div class="carPhotoList" itemscope itemtype="http://schema.org/ImageGallery">
				 	<#if images??>
				 	<#list images as img>
					<figure itemprop="associatedMedia" itemscope itemtype="http://schema.org/ImageObject">
						<a
							href="login/getCarPhoto?id=${img.id}&name=${img.largeName}"
							itemprop="contentUrl" data-size="${img.width}x${img.height }"> <img
							style="margin-bottom: 10px;"
							src="login/getCarPhoto?id=${img.id}&name=${img.smallName}"
							class="img-responsive center-block carPhone"
							onerror="noFind();"  itemprop="thumbnail">
							<figcaption itemprop="caption description"></figcaption>
						</a>
					</figure>
					</#list>
					</#if>
				</div>
			</div>
		</div>
	</div>
</div>
<div class="callDiv">
	<a href="tel:${result.contacePhone }"><span
		class="glyphicon glyphicon-earphone" ></span></a>
	<br>
	<a href="sms:${result.contacePhone }" ><span
		class="glyphicon glyphicon-envelope"></span></a>
</div>
<script type="text/javascript">
	$.ajax({
		url:server.path+"/carOwnerInfo/updateLookCount",
		data:{id:${result.id},_rdn:new Date().getTime()},
		success:function(data){
			if(data.valid)
				$("#viewinfo_lookCount").html(data.message);
			else
				$("#viewinfo_lookCount").html("--");
		}
	});
	
	$(".carPhotoList img").each(function(){
		var url = $(this).attr("src");
		$(this).attr("src",server.path+"/"+url);
	});
	
	$("#viewinfo_img_user_head").attr("src",server.path+"/login/getPhoto?id=${user.id}&name=${user.photoSmall}");
	
	var curUser = getCurUser();
	
	if(curUser && curUser.id == "${result.id}"){
		$("#btn_trash").show();
	}
	
	document.addEventListener('touchmove', function(e) {
		e.preventDefault();
	}, false); 
	
	var myScrollInfo = new IScroll('#wrapperInfo', {
		probeType : 1, //probeType：1对性能没有影响。在滚动事件被触发时，滚动轴是不是忙着做它的东西。probeType：2总执行滚动，除了势头，反弹过程中的事件。这类似于原生的onscroll事件。probeType：3发出的滚动事件与到的像素精度。注意，滚动被迫requestAnimationFrame（即：useTransition：假）。  
		scrollbars : true,
		mouseWheel : true,
		interactiveScrollbars : true,
		shrinkScrollbars : 'scale',
		fadeScrollbars : true,
		preventDefault : false
	});
	
	$(".timeago").timeago();
	
	//当所有图片加载完毕后刷新iscroll
	isImgLoad("carPhone", function() {
		myScrollInfo.refresh();

	});
	
	initPhotoSwipeFromDOM('.carPhotoList');
	
</script>