var server = {
	path : "http://192.168.40.87:8080/pinche",
	cookieDomainName : "handanpinche",
	cookieMaxAge : 365,
	cookiePath : "/",
	curUser : null,
	cookieName : null
}

function getCurUser(){
	if(server.curUser){
		return server.curUser.value;
	}else{
		return undefined;
	}
}
function setCurUser(user){
	server.curUser = user;
}