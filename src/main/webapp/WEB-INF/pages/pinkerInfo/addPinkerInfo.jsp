<%@page import="com.lfwer.common.TokenProcessor"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
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
					onclick="history.go(-1);" style="cursor: pointer;">&nbsp;</span>
			</div>
			<div class="col-xs-8 text-center">
				<span>乘客发布</span>
			</div>
			<div class="col-xs-2"></div>
		</div>
	</div>
	<div id="wrapperInfo">
		<div id="scrollerInfo">
			<div class="container">
				<input type="hidden" id="mirror_field" value="" readonly />
				<div class="alert alert-info"
					style="padding: 4px; margin-bottom: 10px;">
					<span>温馨提示：使用本平台拼车前，请认真阅读《免责声明》，平台不承担任何法律连带责任！</span>
				</div>
				<form id="addPinkerInfoForm" class="form-horizontal" method="post"
					action="${basePath }/pinkerInfo/addPinkerInfoSubmit"
					style="margin: 0; padding: 0;">
					<input type="hidden" name="<%=TokenProcessor.TOKENNAME%>"
						value="<%=session.getAttribute(TokenProcessor.TOKENNAME)%>">
						
					<%-- <div class="form-group">
						<div class="col-xs-12">
							<div class="input-group">
								<span class="input-group-addon">联系人<span
									class="lfwer-seize">⊙</span></span><input readonly type="text"
									id="contactUser" name="contactUser" class="form-control"
									placeholder="请填写联系人姓名" data-bv-notempty
									data-bv-notempty-message="请填写联系人姓名" data-bv-stringlength
									data-bv-stringlength-min="2" data-bv-stringlength-max="12"
									data-bv-stringlength-message="联系人长度必须在2至12位之间"
									value="${user.nickName }">
							</div>
						</div>
					</div>
					<div class="form-group">
						<div class="col-xs-12">
							<div class="input-group">
								<span class="input-group-addon">联系电话</span><input readonly type="text"
									id="contacePhone" name="contacePhone" class="form-control"
									placeholder="请填写手机号" data-bv-notempty
									data-bv-notempty-message="请填写手机号" data-bv-regexp
									data-bv-regexp-regexp="^1[3|4|5|8][0-9]\d{8}$"
									data-bv-regexp-message="请填写11位手机号" value="${user.phone }">
							</div>
						</div>
					</div>	 --%>
						
					<div class="form-group">
						<div class="col-xs-12">
							<div class="input-group">
								<span class="input-group-addon">出发地<span
									class="lfwer-seize">⊙</span></span><select data-bv-notempty
									data-bv-notempty-message="请选择出发地区" class="form-control"
									id="fromZone" name="fromZone">
									<option value=''>- 请选择 -</option>
									<c:forEach var="o" items="${zoneList }">
										<option value='${o.id }'>${o.name }</option>
									</c:forEach>
								</select>
							</div>
						</div>
					</div>
					<div class="form-group">
						<div class="col-xs-12">
							<div class="input-group">
								<span class="input-group-addon">目的地<span
									class="lfwer-seize">⊙</span></span><select data-bv-notempty
									data-bv-notempty-message="请选择到达地区" class="form-control"
									id="toZone" name="toZone">
									<option value=''>- 请选择 -</option>
									<c:forEach var="o" items="${zoneList }">
										<option value='${o.id }'>${o.name }</option>
									</c:forEach>
								</select>
							</div>
						</div>
					</div>
					
					<div class="form-group">
						<div class="col-xs-12">
							<div class="input-group">
								<span class="input-group-addon">上车地点</span><input type="text"
									id="onSite" name="onSite" class="form-control"
									placeholder="请填写上车地点" data-bv-notempty
									data-bv-notempty-message="请填写上车地点" data-bv-stringlength
									data-bv-stringlength-min="2" data-bv-stringlength-max="20"
									data-bv-stringlength-message="上车地点长度必须在2至20位之间">
							</div>
						</div>
					</div>
					<div class="form-group">
						<div class="col-xs-12">
							<div class="input-group">
								<span class="input-group-addon">下车地点</span><input type="text"
									id="offSite" name="offSite" class="form-control"
									placeholder="请填写下车地点" data-bv-notempty
									data-bv-notempty-message="请填写下车地点" data-bv-stringlength
									data-bv-stringlength-min="2" data-bv-stringlength-max="20"
									data-bv-stringlength-message="下车地点长度必须在2至20位之间">
							</div>
						</div>
					</div>
					<div class="form-group">
						<div class="col-xs-12">
							<div class="input-group btn-group">
								<span class="input-group-addon">发布期限</span>
								<button name="timeLimitC" type="button" class="btn btn-primary"
									value="1">单次</button>
								<button name="timeLimitC" type="button" class="btn btn-default"
									value="2">周期</button>
								<input name="timeLimit" id="timeLimit" type="text"
									class="lfwer-hidden" value="1" data-bv-notempty
									data-bv-notempty-message="请选择发布期限">
							</div>
						</div>
					</div>
					<div class="form-group" id="divDate">
						<div class="col-xs-12">
							<div class="input-group">
								<span class="input-group-addon">拼车日期</span><input
									class="form-control" id="pdate" name="pdate" data-bv-notempty
									data-bv-notempty-message="请选择拼车日期" type="text"
									value="<%=new SimpleDateFormat("yyyy-MM-dd").format(new Date())%>">
							</div>
						</div>
					</div>
					<div class="form-group" id="divWeek" style="display: none;">
						<div class="col-xs-12">
							<div class="btn-group" data-toggle="buttons-checkbox">		  
								<c:forEach var="o" items="${weekList }">
									<button type="button" name="pweekC" class="btn btn-default" value="${o.id }"  style="font-size:10px;">${o.name }</button>
								</c:forEach>
								<input name="pweekName" id="pweekName" type="text"
								class="lfwer-hidden" value="" data-bv-notempty
								data-bv-notempty-message="请选择拼车周期" >
							</div>
						</div>
					</div>
					<div class="form-group">
						<div class="col-xs-12">
							<div class="input-group">
								<span class="input-group-addon">出发时间</span><input name="ptime"
									id="ptime" class="form-control" readonly type="text"
									data-bv-notempty data-bv-notempty-message="请选择出发时间">
							</div>
						</div>
					</div>
					<div class="form-group">
						<div class="col-xs-12">
							<div class="input-group btn-group">
								<span class="input-group-addon">乘车人数</span>
								<c:forEach var="o" items="${seatingList }">
									<c:if test="${o.id eq 1 }">
										<button name="pnumC" type="button" class="btn btn-primary"
											value="${o.id }">${o.name }</button>
									</c:if>
									<c:if test="${o.id ne 1 }">
										<button name="pnumC" type="button" class="btn btn-default"
											value="${o.id }">${o.name }</button>
									</c:if>
								</c:forEach>
								<input name="pnum" id="pnum" type="text" class="lfwer-hidden"
									value="1" data-bv-notempty data-bv-notempty-message="请选择乘车人数">
							</div>
						</div>
					</div>
					<div class="form-group">
						<div class="col-xs-12">
							<div class="input-group">
								<span class="input-group-addon">拼车价格</span><input type="text"
									id="cost" name="cost" class="form-control"
									placeholder="请填写拼车价格" data-bv-notempty
									data-bv-notempty-message="请填写拼车价格" data-bv-regexp
									data-bv-regexp-regexp="[0-9]+$"
									data-bv-regexp-message="拼车价格只能输入数字" maxlength="4"><span
									class="input-group-addon">元</span>
							</div>
						</div>
					</div>
					<div class="form-group">
						<div class="col-xs-12">
							<textarea name="remark" id="remark" class="form-control"
								style="width: 100%; height: 100px" placeholder="留言，限200字以内"
								data-bv-stringlength data-bv-stringlength-max="200"
								data-bv-stringlength-message="留言长度需小于200字"></textarea>
						</div>
					</div>

					<div class="form-group text-center">
						<div class="col-xs-12">
							<button type="button" id="btnSave" class="btn btn-primary btn-lg btn-block">发布并查看</button>
						</div>
					</div>
				</form>
				<div id="dtBox"></div>
				<%@include file="/common/bottom.jsp"%>
			</div>
		</div>
	</div>
<script type="text/javascript">
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
						 
		$("button[name='timeLimitC']").click(function(){
			$("#timeLimit").val($(this).val());
			$("#timeLimit").keyup();
			if($(this).val()=="1"){
				$("#divDate").show();
				$("#divWeek").hide();
			}else{
				$("#divWeek").show();
				$("#divDate").hide();
			}
			$("button[name='timeLimitC']").removeClass('btn-primary').addClass('btn-default'); 
			$(this).removeClass('btn-default').addClass('btn-primary');
			changeTime($(this).val());
		});
		$("button[name='pnumC']").click(function(){
			$("button[name='pnumC']").removeClass('btn-primary').addClass('btn-default'); 
			$(this).removeClass('btn-default').addClass('btn-primary');
			$("#pnum").val($(this).val());
			$("#pnum").keyup();
		});
		
		$("button[name='pweekC']").click(function(){
			if($(this).hasClass("btn-primary")){
				$(this).removeClass('btn-primary').addClass('btn-default');
			}else{
				$(this).removeClass('btn-default').addClass('btn-primary');
			}
			$("#pweekName").val("");
			var v = "";
			$("button[name='pweekC']").each(function(index,element){
				if($(this).hasClass("btn-primary")){
					if(index>1){
						v += ",";
					}
					v += $(this).val();
				}
			});
			$("#pweekName").val(v);
			$("#pweekName").keyup();
		});
 		
		
		 
		
		$('#addPinkerInfoForm').bootstrapValidator({
			message : '验证失败',
			feedbackIcons : {
				//valid : 'glyphicon glyphicon-ok',
				//invalid : 'glyphicon glyphicon-remove',
				validating : 'glyphicon glyphicon-refresh'
		}}).on('error.field.bv', function(e, data) {
			myScrollInfo.refresh();
		}).on('success.field.bv', function(e, data) {
			myScrollInfo.refresh();
		});
					
		$('#pdate').mobiscroll().date({
                  theme: '',     // Specify theme like: theme: 'ios' or omit setting to use default 
                  mode: 'scroller',       // Specify scroller mode like: mode: 'mixed' or omit setting to use default 
                  display: 'modal', // Specify display mode like: display: 'bottom' or omit setting to use default 
                  lang: 'zh',        // Specify language like: lang: 'pl' or omit setting to use default 
                  dateFormat: 'yy-mm-dd', // 日期格式
                  minDate:mini.parseDate('<%=new SimpleDateFormat("yyyy-MM-dd").format(new Date())%>')
                  
		}).on("change",function(){
			changeTime(1);
		});
		$('#ptime').mobiscroll().time({
                  theme: '',     // Specify theme like: theme: 'ios' or omit setting to use default 
                  mode: 'scroller',       // Specify scroller mode like: mode: 'mixed' or omit setting to use default 
                  display: 'modal', // Specify display mode like: display: 'bottom' or omit setting to use default 
                  lang: 'zh',       // Specify language like: lang: 'pl' or omit setting to use default 
                  dateFormat: 'hh:ii', // 日期格式
                  minDate:mini.parseDate('<%=new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date())%>')
		}).on('change',function(){
			$("#ptime").keyup();
		});
	
		
		$("#btnSave").click(function(){
			 var $form = $('#addPinkerInfoForm');
			 var $this = $(this);
			 $this.attr({"disabled":"disabled"});
			 var data = $form.data('bootstrapValidator');
			 if (data) {
				 data.validate();
				 if (!data.isValid()) {
					 $this.removeAttr("disabled");
					 return;
				 }
			 }
			 
			 $.post($form.attr("action"),$form.serialize(),"json").done(function(result){
				 $this.removeAttr("disabled");
				 if(result && result.valid == true){
					 	//刷新列表
					 	//$("#pullDown2").html("松开刷新 O(∩_∩)O").show();
					 	//myScroll2.scrollTo(0, 30, 500, null);
					 	//查看详情
					 	$("#viewDiv").empty();
					 	$(".mask").show();
						$("#viewDiv").slideDown(500,function() {
							$("#viewDiv").load('${basePath}/pinkerInfo/viewPinkerInfo?type=index&id='+ result.message, function() {
								$(".mask").hide();
							});
						});
				 }else{
					 alert(result.message);
				 }
			 });
		});
	});
	function changeTime(v){
		var d = "00:00";
		if(v==1){ 
			if($("#pdate").val()=="<%=new SimpleDateFormat("yyyy-MM-dd").format(new Date())%>"){
				d = "<%=new SimpleDateFormat("HH:mm").format(new Date())%>";
			}
			if($("#ptime").val()!=""){
				if($("#ptime").val().replace(":","")<d.replace(":","")){
					$("#ptime").val("");
				}
			}
		}
		$('#ptime').mobiscroll("option","minDate",mini.parseDate($("#pdate").val()+' '+d)); 
		$("#pdate").keyup();
		$("#ptime").keyup();
	}
	function radioValueChg(e, id) {
		$("#" + id).val(e.value);
		$("#" + id).keyup();
	}
</script>