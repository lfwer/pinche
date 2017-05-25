/*!
 * lfwer v1.0
 * Copyright 2015-2015 刘方伟, Inc.
 */
var lfwer = function() {
};

lfwer.getRootPath = function() {
	// 获取当前网址，如： http://localhost:8083/proj/meun.jsp
	var curWwwPath = window.document.location.href;
	// 获取主机地址之后的目录，如： proj/meun.jsp
	var pathName = window.document.location.pathname;
	var pos = curWwwPath.indexOf(pathName);
	// 获取主机地址，如： http://localhost:8083
	var localhostPath = curWwwPath.substring(0, pos);
	// 获取带"/"的项目名，如：/proj
	var projectName = pathName
			.substring(0, pathName.substr(1).indexOf('/') + 1);
	return (localhostPath + projectName);
}

lfwer.rootName = lfwer.getRootPath().substring(
		lfwer.getRootPath().lastIndexOf("/"));

lfwer.replaceNull = function(value){
	if(value == null){
		return "--";
	}else if(value.length==0){
		return "--";
	}else{
		return value;
	}
}

