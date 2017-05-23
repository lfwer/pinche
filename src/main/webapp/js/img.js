var imgjs_t_img; // 定时器
var imgjs_isLoad = false; // 控制变量
// 判断图片加载的函数
function isImgLoad(className, callback) {
	// 查找所有封面图，迭代处理
	$('.' + className).each(function() {
		// 如果complete为true则imgjs_isLoad设为false，并退出each
		if (!this.complete) {
			imgjs_isLoad = false;
			return false;
		}
	});
	// 为true，没有发现为0的。加载完毕
	if (imgjs_isLoad) {
		clearTimeout(imgjs_t_img); // 清除定时器
		imgjs_isLoad = false;
		// 回调函数
		callback();
		// 为false，因为找到了没有加载完成的图，将调用定时器递归
	} else {
		imgjs_isLoad = true;
		imgjs_t_img = setTimeout(function() {
			isImgLoad(className,callback); // 递归扫描
		}, 200); // 我这里设置的是500毫秒就扫描一次，可以自己调整
	}
}

function noFind(o) {
	//var img = event.srcElement;
	//img.src = lfwer.rootName+"/images/no.png";
	//控制不要一直跳动
	//img.onerror = null;
	$(o).attr("src",lfwer.rootName+"/images/no.png");
}

function nohead(o) {
	$(o).attr("src",lfwer.rootName+"/images/nohead.jpg");
}

function noCar(o) {
	$(o).attr("src",lfwer.rootName+"/images/nocar.png");
}