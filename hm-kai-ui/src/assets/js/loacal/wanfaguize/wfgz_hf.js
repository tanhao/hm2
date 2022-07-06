$(function(){
	//回到顶部
	$("#gotop").click(function() {
		$('body,html').animate({
			scrollTop: 0
		}, 500);
		$(this).hide();
		return false;
	});
	$(document).scroll(function() {
		if($(this).scrollTop() > 10) {
			$("#gotop").show();
		} else {
			$("#gotop").hide();
		}
	});
	
	$("#headdivbox").load("../public/head.html", function() {
		if(config.ifdebug) {
			console.log("request is over!");
		}
	});
	$("#fooderbox").load("../public/fooder.html", function() {
		if(config.ifdebug) {
			console.log("request is over!");
		}
	});
});
