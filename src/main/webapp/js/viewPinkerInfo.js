$(document).ready(function() {
		$(".timeago").timeago();
		document.addEventListener('touchmove', function(e) {
			e.preventDefault();
		}, false);
		var myScroll = new IScroll('#wrapper', {
			//probeType: 2, //probeType：1对性能没有影响。在滚动事件被触发时，滚动轴是不是忙着做它的东西。probeType：2总执行滚动，除了势头，反弹过程中的事件。这类似于原生的onscroll事件。probeType：3发出的滚动事件与到的像素精度。注意，滚动被迫requestAnimationFrame（即：useTransition：假）。  
			scrollbars : true,
			mouseWheel : true,
			interactiveScrollbars : true,
			shrinkScrollbars : 'scale',
			fadeScrollbars : true,
			preventDefault : false
		});
		
		$('#myModal').modal('hide').css({
			'margin-top' : function() {
				return ($(this).height() / 2-100);
			}
		});
	});
	function removePinkerInfo() {
		$.ajax({
			url : lfwer.rootName+'/pinkerInfo/removePinkerInfo?id=${result.id}',
			type : 'post',
			success : function() {
				$("#msgContent").html("删除成功,即将离开本页。").removeClass(
						'alert-danger').addClass('alert-success');
				$("#msg").modal('show');
				window.setTimeout(function() {
					$("#msg").modal('hide');
					location.href = lfwer.rootName+"/index.jsp";
				}, 2000);
			},
			error : function() {
				$("#msgContent").html("删除失败!").removeClass('alert-success')
						.addClass('alert-danger');
				$("#msg").modal('show');
				window.setTimeout(function() {
					$("#msg").modal('hide');
				}, 2000);
			}
		});
	}