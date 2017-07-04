
var now = new Date();

var nowDate = mini.parseDate(now,'yyyy-MM-dd');
var nowDateStr = mini.formatDate(now,'yyyy-MM-dd');

var nowTime = mini.parseDate(now,'HH:mm');
var nowTimeStr = mini.formatDate(now,'HH:mm');

var myScrollInfo;

$(document).ready(function() {
	
	$("#userId").val(server.cookieName);
	
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
				if(index>0){
					v += ",";
				}
				v += $(this).val();
			}
		});
		$("#pweekName").val(v);
		$("#pweekName").keyup();
	});
	
	$('#addCarOwnerInfoForm').bootstrapValidator({
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
	
	$('#fromZoneC').mobiscroll().select({
        theme: '',     // Specify theme like: theme: 'ios' or omit setting to use default 
        mode: 'scroller',       // Specify scroller mode like: mode: 'mixed' or omit setting to use default 
        display: 'bottom', // Specify display mode like: display: 'bottom' or omit setting to use default 
        lang: 'zh',        // Specify language like: lang: 'pl' or omit setting to use default 
        inputClass:'form-control', //为插件生成的input添加样式
       	//rows:10,
        onSelect: function(valueText,inst){
        	$("#fromZoneC > option").each(function(){
        		if($(this).text()==valueText){
        			$("#fromZone").val($(this).val());
        			return false;
        		}
        	});
        	$("#fromZone").keyup();
        }
	});
	
	$('#toZoneC').mobiscroll().select({
        theme: '',     // Specify theme like: theme: 'ios' or omit setting to use default 
        mode: 'scroller',       // Specify scroller mode like: mode: 'mixed' or omit setting to use default 
        display: 'bottom', // Specify display mode like: display: 'bottom' or omit setting to use default 
        lang: 'zh',        // Specify language like: lang: 'pl' or omit setting to use default 
        inputClass:'form-control', //为插件生成的input添加样式
       	//rows:10,
        onSelect: function(valueText,inst){
        	$("#toZoneC > option").each(function(){
        		if($(this).text()==valueText){
        			$("#toZone").val($(this).val());
        			return false;
        		}
        	});
        	$("#toZone").keyup();
        }
	});
	
	 
	$("#pdate").val(nowDateStr);
	$('#pdate').mobiscroll().date({
		theme: '',     // Specify theme like: theme: 'ios' or omit setting to use default 
		mode: 'scroller',       // Specify scroller mode like: mode: 'mixed' or omit setting to use default 
		display: 'bottom', // Specify display mode like: display: 'bottom' or omit setting to use default 
		lang: 'zh',        // Specify language like: lang: 'pl' or omit setting to use default 
		dateFormat: 'yy-mm-dd', // 日期格式
		//rows:10,
		showLabel:true,
		minDate:nowDate
              
	}).on("change",function(){
		changeTime(1);
	});
	
	$("#ptime").val(nowTimeStr);
	$('#ptime').mobiscroll().time({
		theme: '',     // Specify theme like: theme: 'ios' or omit setting to use default 
		mode: 'scroller',       // Specify scroller mode like: mode: 'mixed' or omit setting to use default 
		display: 'bottom', // Specify display mode like: display: 'bottom' or omit setting to use default 
		lang: 'zh',       // Specify language like: lang: 'pl' or omit setting to use default 
		dateFormat: 'hh:ii', // 日期格式
		//rows:10,
		showLabel:true,
		minDate:nowTime
             
	}).on('change',function(){
		$("#ptime").keyup();
	});
	
	$("#cost").mobiscroll().number({
		 theme: '',     // Specify theme like: theme: 'ios' or omit setting to use default 
         mode: 'scroller',       // Specify scroller mode like: mode: 'mixed' or omit setting to use default 
         display: 'bottom', // Specify display mode like: display: 'bottom' or omit setting to use default 
         lang: 'zh',       // Specify language like: lang: 'pl' or omit setting to use default 
         //rows:10,
         min:5,
         max:9999,
         width:150,
         step:1,
         onSelect: function(valueText,inst){
         	$("#cost").keyup();
         }
        
	});
	
	$("#btnSave").click(function(){
		 var $form = $('#addCarOwnerInfoForm');
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
		 
		 $.post(server.path+"/carOwnerInfo/addCarOwnerInfoSubmit",$form.serialize(),"json").done(function(result){
			 $this.removeAttr("disabled");
			 if(result && result.valid == true){
				 	//查看详情
				 	$("#viewDiv").empty();
				 	$(".mask").show();
					$("#viewDiv").slideDown(500,function() {
						$("#viewDiv").load(lfwer.rootName+'/pages/carOwnerInfo/views/'+result.message+'.html?type=index', function() {
							$(".mask").hide();
						});
					});
			 }else{
				 alertMsg(result.message);
			 }
		 });
	});
});

function changeTime(v){
	var d = "00:00";
	if(v==1){ 
		if($("#pdate").val() == nowDateStr){
			d = nowTimeStr;
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