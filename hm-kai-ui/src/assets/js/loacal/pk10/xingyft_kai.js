$(function() {
	$("#headdivbox").load("../public/head.html?v=20181181558", function() {
		if(config.ifdebug) {
			console.log("request is over!");
		}
	});
	$("#fooderbox").load("../public/fooder.html?v=20181181558", function() {
		if(config.ifdebug) {
			console.log("request is over!");
		}
	});
	//今天，昨天，前天选择操作
	$(".listheadrl span").live("click", function() {
		$(this).siblings().removeClass("checked");
		$(this).addClass("checked");
		//createHtmlGetData(null);
		var id = $(this).attr("id");
		var $sinli4 = $("#biaozxz .sinli:nth-child(4)");
		var $sinli5 = $("#biaozxz .sinli:nth-child(5)");
		if($sinli4.hasClass("checked")) {
			$sinli4.removeClass("checked");
		}
		if($sinli5.hasClass("checked")) {
			$sinli5.removeClass("checked");
		}
		tabarr = [];
		$("#chakanchfb,#daxiaodsfb").find("li").removeClass("selected");
		//传变量是为了检查是不是重置功能
		if(id == "today") {
			listData(getDateStr(0), "");
			$(".jinri").text("今天");
			$("#dateframe").find("input").val(getDateStr(0));
		} else if(id == "yesterday") {
			listData(getDateStr(-1), "");
			$(".jinri").text(currentDay(getDateStr(-1)));
			$("#dateframe").find("input").val(getDateStr(-1));
		} else if(id == "qianday") {
			listData(getDateStr(-2), "");
			$(".jinri").text(currentDay(getDateStr(-2)));
			$("#dateframe").find("input").val(getDateStr(-2));
		} else if(id == "thirty") {
			listData("", "30");
		} else if(id == "sixty") {
			listData("", "60");
		} else if(id == "ninety") {
			listData("", "90");
		}
	});
	//弹出播放视频
	$("#startVideo").on("click", function() {
		if($("iframe")[0].contentWindow.ifopen) {
			return;
		}
		$("#videobox").animate({
			"z-index": "19999"
		}, 200, function() {
			$(".content").animate({
				"bottom": "50%",
				"right": "50%",
				"width": "880px",
				"height": "636px",
				"margin-bottom": "-298px"
			}, 500, function() {
				$(".big").hide();
				$(".small").show();
				$(".animate").height("575");
				iframe(); //加载动画界面
				isfirthload = false;
				$("iframe")[0].contentWindow.fun.stateSound();
				tools.insertVideo(); //向视频中添加数据
				tools.setPK10TB(); //启动纠错
			});
		});
		document.addEventListener('touchstart', function() {
//			if(ifopen&&($("iframe")[0].contentWindow.ifsund()))
			$("iframe")[0].contentWindow.document.getElementById("sound").play();
		}, false);
		$("iframe")[0].contentWindow.ifopen = true;
	});
	//关闭视频
	$("#videobox .closevideo").on("click", function() {
		clearInterval(pk10jiuchuo);
		$(".content").animate({
			"width": "0",
			"margin-right": "0"
		}, 200, function() {
			$("#videobox").css({
				"z-index": "-1",
				"position": "fixed"
			});
			$("#videobox .content").css({
				"width": "880px",
				"margin-right": "-440px",
				"bottom": "-50%"
			});
		});
		$("iframe")[0].contentWindow.ifopen = false;
		$("iframe")[0].contentWindow.running.pause();
		$("iframe")[0].contentWindow.kaisound.pause();
		$("iframe").contents().find("#sound").attr("src", "");
	});
	//最少化视频
	$("#videobox .small").on("click", function() {
		$(".content").animate({
			"right": "0",
			"width": "340px",
			"height": "290px",
			"margin-right": "10px",
			"bottom": "0",
			"margin-bottom": "0"
		}, 200, function() {
			$("#videobox").css({
				"position": "static"
			});
			$(".big").show();
			$(".small").hide();
			$(".animate").height("290");
			iframe(); //加载动画界面
			$(".content").width("347px");
			if(!toolBoxs.isIE()) {
				$(".animate iframe").contents().find(".footer1,.footer2,.footer3").find("div").css({
					"transform": "scale(0.85)",
//					"margin-top": "6px",
					"margin-left": "10px"
				});
				$(".animate iframe").contents().find(".footer2_1").css({
					"transform": "scale(0.85)",
					"width": "auto"
				});
			}
		});
	});
	//还原视频
	$("#videobox .big").on("click", function() {
		$("#videobox").animate({
			"z-index": "19999"
		}, 200, function() {
			$(".content").animate({
				"bottom": "50%",
				"right": "50%",
				"height": "640px",
				"margin-right": "-440px",
				"width": "880px",
				"margin-bottom": "-298px"
			}, 300, function() {
				$("#videobox").css({
					"position": "fixed"
				});
				$(".big").hide();
				$(".small").show();
				$(".animate").height("575");
				iframe(); //加载动画界面
			});
		});
	});
	ajaxRequst("", boxid);
});
//公共URL前缀
var urlbublic = config.publicUrl;
//采种类型
var lotCode = lotCode.xingyft;
var boxid = "#pk10";
var isfirthload = true;

function iframe() {
	if(isfirthload) {
		//加载初始界面
		$('.animate').find(".loading").fadeOut('slow');
		$('.animate iframe').contents().find(".container").hide();
		$('.animate iframe').contents().find("#preloader").hide();
	}
	var W = $(".animate").width();
	var H = $(".animate").height();
	var w = 242,
		h = 414,
		one, tow;
	one = (((w - W) / 13.1) / 100 - 0.31325) * (-1); //宽
	two = (((h - H) / 8.65) / 100 - 0.283) * (-1); //高
	if(W <= 360) {
		$(".animate iframe").contents().find("html").css("left", "-2px");
	}
	if(W <= 325) {
		$(".animate iframe").contents().find("html").css("left", "-5px");
		console.log("kshgdehgo")
	}
	//	$(".animate iframe").contents().find("html").css("transform", "scale(" + one + "," + two + ")");
	var zoomW = (W / 1310);
	//$(".animate iframe").contents().find("#bbo").css("transform", "scale(" + one + "," + two + ")");
	//	$(".animate iframe").contents().find(".container").css("transform", "scale(" + zoomW + ")");
	$(".animate iframe").contents().find(".container").css("zoom", zoomW);
	$(".animate iframe").contents().find(".container").css({
		"-moz-transform": "scale(" + zoomW + ")",
		"-moz-transform-origin": "top left"
	});
	if(toolBoxs.isIE()) {}
	setTimeout(function() {
		$('.animate iframe').contents().find(".container").fadeIn('slow');
		$('.animate iframe').contents().find("body").delay(350).css({
			'overflow': 'visible'
		});
	}, 500);
	$(".content").width(W + 16);
	if(toolBoxs.isIE()) {
		$(".content").width(W + 18);
	}
}

function ajaxRequst(issue, id) {
	var issue = issue == undefined ? "" : issue;
	var iferror = false;
	$.ajax({
		url: urlbublic + "pks/getLotteryPksInfo.do?issue=" + issue,
		type: "get",
		timeout: 60000,
		asasync: false,
		data: {
			"lotCode": lotCode
		},
		beforeSend: function() {
			if(animateID[id] == undefined) {
				animateMethod.pk10OpenAnimate(id); //请求执行开奖动画
			}
		},
		success: function(data) {
			try {
				creatDataHead(data, id); //向头部填充数据
			} catch(e) {
				setTimeout(function() {
					ajaxRequst(issue, id);
				}, "1000");
			}
		},
		error: function(data) {
			setTimeout(function() {
				ajaxRequst(issue, id);
			}, "1000");
			iferror = true;
		},
		complete: function(xmlobj, state) {
			iferror = false;
			xmlobj = null;
			if(!iferror) {
				if(state == "timeout") {
					setTimeout(function() {
						ajaxRequst(issue, id);
					}, "1000");
				}
			}
		}
	});

}

function formatDate(date) {
	var y = date.getFullYear();
	var m = date.getMonth() + 1;
	m = m < 10 ? '0' + m : m;
	var d = date.getDate();
	d = d < 10 ? ('0' + d) : d;
	return y + '-' + m + '-' + d;
};

//向开奖区填数据
function creatDataHead(jsondata, id) {
	var data = tools.parseObj(jsondata);
	data = data.result.data;
	var codeArr = data.preDrawCode.split(",");
	var	codeNum = [];
	for(var i =0; i < codeArr.length; i++){
		if(codeArr[i].charAt(0) != 1){
			var items = codeArr[i].charAt(1);			
		} else{
			var items = codeArr[i];
		}
		codeNum.push(items);
	}
	var preDrawCode = codeNum.toString();
	var timeResult = tools.operatorTime(data.drawTime == "" ? "0" : data.drawTime, data.serverTime); //得到时间差
	if(timeResult <= 0) {
		throw new Error("error");
	} else {
		//只有开奖页面有视频
		if(!$("#videobox").length < 1) {
			if(!($("#videobox").css("z-index") == -1)) {
				$("iframe")[0].contentWindow.finishgame(preDrawCode,timeResult);
				setTimeout(function() {
					publicmethod.insertHeadPk10(jsondata, id);
				}, 3000);
				setTimeout(function() {
					tools.insertVideo(); //切换到开奖
				}, 10000);
			} else {
				publicmethod.insertHeadPk10(jsondata, id);
			}
		} else {
			publicmethod.insertHeadPk10(jsondata, id);
		}
	}
}