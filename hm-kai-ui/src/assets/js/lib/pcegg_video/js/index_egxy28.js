$(function() {
	//初始化数据及监听事件
	pubmethod.initAdata();
	//初始化数据
	method.indexLoad(boxId, lotCode);
	ifishad = true; //判断是否是刷新页
	tools.loadDate(); //初始化时间控件
	//	method.loadOther(""); //加载列表及其他数据
	//弹出播放视频
	$("#startVideo").on("touchstart", function() {
		$("iframe")[0].contentWindow.pcEgg.startVid(tools.pcEggDataStr());
		startVideo();
		$("iframe")[0].contentWindow.ifopen = true;
	});
	document.addEventListener('click', function() {
		if($("iframe")[0].contentWindow.ifopen) {
			$("iframe")[0].contentWindow.pcEgg.sound.play("audioidKai");
			$("iframe")[0].contentWindow.pcEgg.sound.play("audioidBg");
			$("iframe")[0].contentWindow.pcEgg.sound.stop("audioidKai");
		}
	}, false);
	//关闭视频
	$("#videobox .closevideo").on("click", function() {
		$(".content").animate({
			"top": "-200%"
		}, 200, function() {
			$("#videobox").css({
				"z-index": "-1",
				"position": "fixed"
			});
		});
		$("iframe")[0].contentWindow.pcEgg.sound.stop("audioidBg");
		$("iframe")[0].contentWindow.pcEgg.sound.stop("audioidKai");
		$("iframe")[0].contentWindow.pcEgg.clearTime();
		$("iframe")[0].contentWindow.ifopen = false;
	});
	setTimeout(function() {
		config.ifFirstLoad = true;
	}, 4000);
});
(function() {
	var supportOrientation = (typeof window.orientation === 'number' &&
		typeof window.onorientationchange === 'object');
	var init = function() {
		var htmlNode = document.body.parentNode,
			orientation;
		var updateOrientation = function() {
			if(supportOrientation) {
				orientation = window.orientation; //得到屏幕旋转参数
				if(($("#videobox").find(".content").css("top") == "0px")) {
					startVideo();
				}
			}
		};
		if(supportOrientation) {
			window.addEventListener('orientationchange', updateOrientation, false);
		} else {
			//监听resize事件
			window.addEventListener('resize', updateOrientation, false);
		}
		updateOrientation();
	};
	window.addEventListener('DOMContentLoaded', init, false);
})();

function iframe() {
	var W = $(".animate").width();
	var H = W * 780 / 1125;
	var zoomW = (W / 1125);
	setTimeout(function() {
//		$('.animate iframe').contents().find("html").css("zoom", zoomW) // 父级宽/1310*100  取0.0000
		$('.animate iframe').contents().find("html").css({"transform-origin":"top left","transform":"scale("+zoomW+")"});
		var contnetH = $('.animate iframe').contents().find(".container").height();
		$(".animate").animate({
			"height": H + 50
		}, 600);
		$('.animate iframe').animate({
			"height": H + 50
		}, 600);
		$(".content").animate({
			"height": H + 25
		}, 600);
	}, 1);
	setTimeout(function() {
		$('.animate iframe').contents().find(".container").fadeIn('slow');
		$('.animate iframe').contents().find("#preloader").fadeOut('slow');
	}, 1000);
}

function startVideo() {
	//启动动画
	$("#videobox").animate({
		"z-index": "19999"
	}, 10, function() {
		var W = $(".animate").width();
		var H = W * 780 / 1125;
		$(".content").css({
			"height": H + 35
		});
		$(".content").animate({
			"top": "0"
		}, 500, function() {
			iframe(); //加载动画界面
		});
	});
}

//判断是否是刷新页
var ifishad = false;
//加载其他数据
//入口加载数据
var method = {};
//加载其他数据
method.loadOther = function(date) {
	//处理所选日期不为当日时不加载列表数据
	if(date == "") {
		if(!tools.ifOnDay()) {
			return;
		}
	}
	method.listData(date); //重新请求list数据
	//此处需要慢一步因为数据需要时时放入数据库分析
	/*	setTimeout(function() {
			method.todayData(date); //加载双面统计     
		}, 1000);
		setTimeout(function() {
			method.longData(date); //长龙提醒         
		}, 2000);*/
}
//入口加载数据
method.indexLoad = function(id) {
	var nextIssue = $(id).find(".nextIssue").val();
	var id = "#" + $(id).attr("id");
	headMethod.loadHeadData(nextIssue, id); //页面启动时加载数据第一次加载不传参
	method.loadOther(""); //加载列表及其他数据
}
//请求list数据
method.listData = function(date) {
	$.ajax({
		url: config.publicUrl + "LuckTwenty/getPcLucky28List.do?date=" + date,
		type: "GET",
		/*	data: {
				"lotCode": lotCode
			},*/
		beforeSend: function() {
			if(!ifishad) {
				$("#numlist").html("<span class='loading'>努力加载中...</span>");
			}
		},
		success: function(data) {
			method.createHtmlList(data);
			//关闭加载动画
			animate.loadingList("body", false);
		},
		error: function(data) {
			if(config.ifdebug) {
				console.log("data error");
			}
		}
	});
}
/*	//今日双面/号码统计
method.todayData = function(date) {
		$.ajax({
			url: config.publicUrl + "CQShiCai/queryDoubleNumber.do?date=" + date,
			type: "GET",
			data: {
				"lotCode": lotCode
			},
			success: function(data) {
				method.loadTodayData(data);
			},
			error: function(data) {
				if(config.ifdebug) {
					console.log("todayData data error");
				}
			}
		});
	}*/
/*	//长龙提醒
method.longData = function(date) {
	$.ajax({
		url: config.publicUrl + "CQShiCai/getShiCaiDailyDragonCount.do?date=" + date,
		type: "GET",
		data: {
			"lotCode": lotCode
		},
		success: function(data) {
			method.loadLongData(data);
		},
		error: function(data) {
			if(config.ifdebug) {
				console.log("data error");
			}
		}
	});
}*/
method.createHtmlList = function(jsondata) {
	var data = null;
	if(typeof jsondata != "object") {
		data = JSON.parse(jsondata);
	} else {
		data = JSON.stringify(jsondata);
		data = JSON.parse(data);
	}
	if(data.errorCode == 0) {
		if(data.result.businessCode == 0) {
			data = data.result.data;
			//1：为开奖号码TAB添加数据---start
			$("#numlist").empty();
			$("#haomafblist").empty();
			$(data).each(function() {
				var divhtml = "";
				divhtml += '<div class="listotherline bortop001 ssclist">';
				divhtml += '<div class="leftspan">';
				divhtml += '<span class="boxflex">';
				var drawTime = this.preDrawTime;
				drawTime = drawTime.substring((drawTime.length) - 8, (drawTime.length) - 3);
				divhtml += '<span class="graytime">' + drawTime + '</span>';
				divhtml += '<span class="graytime">' + tools.subStr(this.preDrawIssue) + '</span>';
				divhtml += '</span>';
				divhtml += '</div>';
				divhtml += '<div class="rightspan">';
				divhtml += '<div class="rightdiv" style="padding-left:0;position:relative">';
				divhtml += '<div class="haomali eghaomali"><ul class="ssclilist listli egxy_ssclilist">';
				var preDrawCode = this.preDrawCode.split(",");
				$(preDrawCode).each(function(index) {
					if(this == "") {
						return;
					}
					var add = "";
					if(index == 0 || index == 1) {
						add = '<li><span class="addicon" style="width:0.2rem"></span></li>';
					}
					divhtml += '<li><span class="bluenum"><i>' + this + '</i></span></li>' + add;
				});
				divhtml += '</ul></div>';
				divhtml += '<div class="longhuli displaynone"><ul id="" class="ssclilistxt listli lhlist">';
				var stylestr = "style='color:";
				if(!(preDrawCode.length <= 1)) {
					var sumBigSamll = tools.typeOf("sumBigSmall", this.sumBigSmall);
					var sumSingleDouble = tools.typeOf("sumSingleDouble", this.sumSingleDouble);
					var singleDoubleCount = tools.typeOf("singleDoubleCount", this.singleDoubleCount);
					var frontBehindCount = tools.typeOf("frontBehindCount", this.frontBehindCount);
					var sumBsSd = tools.typeOf("sumBsSd", this.sumBsSd);
					var sumWuXing = tools.typeOf("sumWuXing", this.sumWuXing);
					var dragonTiger = "";
					if(this.dragonTiger == "0") {
						dragonTiger = "龙";
					} else if(this.dragonTiger == "1") {
						dragonTiger = "虎";
					} else if(this.dragonTiger == "2") {
						dragonTiger = "和";
					}
				}
				divhtml += "<li style='color:#f12d35'>" + this.sumNum + "</li><li style='color:#666'>" + sumBigSamll + "</li><li style='color:#666'>" + sumSingleDouble + "</li><li style='color:#666'>" + singleDoubleCount + "</li><li style='color:#666'>" + frontBehindCount + "</li><li style='color:#666'>" + sumBsSd + "</li><li style='color:#666'>" + sumWuXing + "</li>";

				divhtml += '</ul>';
				divhtml += '</div>';
				divhtml += '</div>';
				divhtml += '</div>';
				divhtml += '</div>';
				$("#numlist").append(divhtml);
				$("#haomafblist").append(divhtml);
			});
			//method.selectedBS($(".rightdiv").find(".spanchecked"), true); //执行标题是不是选重
			//1：为开奖号码TAB添加数据---end
			//1：为开奖号码TAB添加数据---start

			//1：为开奖号码TAB添加数据---end
		} else {
			console.log("数据请求失败！");
		}
	}
}
//筛选号码大小单双
method.selectedBS = function(obj, ifload) {
	tools.bigOrSmall(obj, ifload, "41");
}
//号码分布
method.selectedHm = function(obj, ifload) {
	var ifmyself = $(obj).hasClass("lichecked");
	if($(obj).parent().parent().attr("class") == "dansdxbtn") {
		$(".numbtn").find("li").removeClass("lichecked");
		if($(obj).hasClass("lichecked")) {
			$(obj).removeClass("lichecked");
		} else {
			$(obj).addClass("lichecked");
		}
		var dansdxbtn = $(obj).text();
		if(dansdxbtn == "单") {
			$(".dansdxbtn li:nth-child(2)").removeClass("lichecked");
		} else if(dansdxbtn == "双") {
			$(".dansdxbtn li:nth-child(1)").removeClass("lichecked");
		} else if(dansdxbtn == "大") {
			$(".dansdxbtn li:nth-child(4)").removeClass("lichecked");
		} else if(dansdxbtn == "小") {
			$(".dansdxbtn li:nth-child(3)").removeClass("lichecked");
		}
	} else {
		$(".dansdxbtn").find("li").removeClass("lichecked");
		if($(obj).hasClass("lichecked")) {
			$(obj).removeClass("lichecked");
		} else {
			$(obj).addClass("lichecked").siblings().removeClass("lichecked");
		}
		//得到一个当前遍历的li中的号码
		if($(".numbtn").find(".lichecked").text()) {
			$("#haomafblist li").addClass("selectedOpacity");
			$("#haomafblist li").each(function() {
				if($(this).text() == $(".numbtn").find(".lichecked").text()) {
					$(this).removeClass("selectedOpacity").siblings();
				}
			});
		} else {
			$("#haomafblist li").removeClass("selectedOpacity");
		}

	}
	var dannum = $(".dansdxbtn li:nth-child(1)").hasClass("lichecked"); //单号选中
	var shuangnum = $(".dansdxbtn li:nth-child(2)").hasClass("lichecked"); //双号选中
	var danum = $(".dansdxbtn li:nth-child(3)").hasClass("lichecked"); //大号选中
	var xiaonum = $(".dansdxbtn li:nth-child(4)").hasClass("lichecked"); //小号选中

	$("#haomafblist li").each(function() {
		var number = $(this).text();
		//是否为单双
		var ifds = number % 2 == 0 ? true : false;
		//是否为大小
		var ifdx = number >= 6 ? true : false;
		//如果为真，说明是双数，如果为假说明是单数
		//判断是否同时选中，如果同时选中， 那么有一个为真就要显示
		//双重条件执行
		if(dansdxbtn == "单") { //当前按钮为单
			if(ifmyself) {
				if(danum) { //大号被选中，否则小号被选中
					if(ifdx) { //显示大号和单数
						$(this).removeClass("selectedOpacity");
					} else {
						$(this).addClass("selectedOpacity");
					}
				} else if(xiaonum) { //小号被选中
					if(!ifdx) { //显示小号和单数
						$(this).removeClass("selectedOpacity");
					} else {
						$(this).addClass("selectedOpacity");
					}
				} else {
					$(this).removeClass("selectedOpacity");
				}
			} else {
				if(danum) { //大号被选中，否则小号被选中
					if(ifdx && !ifds) { //显示大号和单数
						$(this).removeClass("selectedOpacity");
					} else {
						$(this).addClass("selectedOpacity");
					}

				} else if(xiaonum) { //小号被选中
					if(!ifdx && !ifds) { //显示小号和单数
						$(this).removeClass("selectedOpacity");
					} else {
						$(this).addClass("selectedOpacity");
					}
				} else {
					if(!ifds) {
						$(this).removeClass("selectedOpacity");
					} else {
						$(this).addClass("selectedOpacity");
					}

				}
			}
		} else if(dansdxbtn == "双") { //当前按钮为双
			if(ifmyself) {
				if(danum) { //大号被选中，否则小号被选中
					if(ifdx) { //显示大号和双数
						$(this).removeClass("selectedOpacity");
					} else {
						$(this).addClass("selectedOpacity");
					}

				} else if(xiaonum) { //小号被选中
					if(!ifdx) { //显示小号和双数
						$(this).removeClass("selectedOpacity");
					} else {
						$(this).addClass("selectedOpacity");
					}
				} else {
					$(this).removeClass("selectedOpacity");
				}
			} else {
				if(danum) { //大号被选中，否则小号被选中
					if(ifdx && ifds) { //显示大号和双数
						$(this).removeClass("selectedOpacity");
					} else {
						$(this).addClass("selectedOpacity");
					}

				} else if(xiaonum) { //小号被选中
					if(!ifdx && ifds) { //显示小号和双数
						$(this).removeClass("selectedOpacity");
					} else {
						$(this).addClass("selectedOpacity");
					}
				} else {
					if(ifds) { //显示小号和双数
						$(this).removeClass("selectedOpacity");
					} else {
						$(this).addClass("selectedOpacity");
					}
				}
			}
		} else if(dansdxbtn == "大") { //当前按钮为大
			if(ifmyself) {
				if(dannum) { //单号被选中，否则双号被选中
					if(!ifds) { //显示大号和单数
						$(this).removeClass("selectedOpacity");
					} else {
						$(this).addClass("selectedOpacity");
					}

				} else if(shuangnum) { //双号被选中
					if(ifds) { //显示大号和双数
						$(this).removeClass("selectedOpacity");
					} else {
						$(this).addClass("selectedOpacity");
					}
				} else {
					$(this).removeClass("selectedOpacity");
				}
			} else {
				if(dannum) { //单号被选中，否则双号被选中
					if(ifdx && !ifds) { //显示大号和单数
						$(this).removeClass("selectedOpacity");
					} else {
						$(this).addClass("selectedOpacity");
					}

				} else if(shuangnum) { //双号被选中
					if(ifdx && ifds) { //显示大号和双数
						$(this).removeClass("selectedOpacity");
					} else {
						$(this).addClass("selectedOpacity");
					}
				} else {
					if(ifdx) { //显示大号和双数
						$(this).removeClass("selectedOpacity");
					} else {
						$(this).addClass("selectedOpacity");
					}
				}
			}
		} else if(dansdxbtn == "小") { //当前按钮为小
			if(ifmyself) {
				if(dannum) { //单号被选中，否则双号被选中
					if(!ifds) { //显示小号和单号
						$(this).removeClass("selectedOpacity");
					} else {
						$(this).addClass("selectedOpacity");
					}
				} else if(shuangnum) { //双号被选中
					if(ifds) { //显示小号和双号
						$(this).removeClass("selectedOpacity");
					} else {
						$(this).addClass("selectedOpacity");
					}
				} else {
					$(this).removeClass("selectedOpacity");
				}
			} else {
				if(dannum) { //单号被选中，否则双号被选中
					if(!ifdx && !ifds) { //显示小号和单号
						$(this).removeClass("selectedOpacity");
					} else {
						$(this).addClass("selectedOpacity");
					}

				} else if(shuangnum) { //双号被选中
					if(!ifdx && ifds) { //显示小号和双号
						$(this).removeClass("selectedOpacity");
					} else {
						$(this).addClass("selectedOpacity");
					}
				} else {
					if(!ifdx) { //显示小号
						$(this).removeClass("selectedOpacity");
					} else {
						$(this).addClass("selectedOpacity");
					}
				}
			}
		}
	});

	console.log($(obj).parent().parent().attr("class"));
}
//今日双面数据
method.loadTodayData = function(jsondata) {
	if(typeof jsondata != "object") {
		data = JSON.parse(jsondata);
	} else {
		data = JSON.stringify(jsondata);
		data = JSON.parse(data);
	}
	if(data.errorCode == 0) {
		if(data.result.businessCode == 0) {
			data = data.result.data;
			var todydata = [{
				//组装号码出现次数
				data: [
					data.numZero,
					data.numOne,
					data.numTwo,
					data.numThree,
					data.numFour,
					data.numFive,
					data.numSix,
					data.numSeven,
					data.numEight,
					data.numNine
				]
			}, {
				data: [
					data.firstSingle,
					data.firstDouble,
					data.firstBig,
					data.firstSmall
				]
			}, {
				data: [
					data.secondSingle,
					data.secondDouble,
					data.secondBig,
					data.secondSmall
				]
			}, {
				data: [
					data.thirdSingle,
					data.thirdDouble,
					data.thirdBig,
					data.thirdSmall
				]
			}, {
				data: [
					data.fourthSingle,
					data.fourthDouble,
					data.fourthBig,
					data.fourthSmall
				]
			}, {
				data: [
					data.fifthSingle,
					data.fifthDouble,
					data.fifthBig,
					data.fifthSmall
				]
			}, {
				data: [
					data.sumSingle,
					data.sumDouble,
					data.sumBig,
					data.sumSmall
				]
			}];
			$("#liangmianbox").empty();
			$(todydata).each(function(index) {
				var tdthml = "";
				$(this.data).each(function() {
					tdthml += "<td>" + this + "</td>";
				});
				var rank = tools.typeOf("liangm", (index));
				var head = "";
				if(index == 0) {
					head = "head1";
				} else if(index == 1) {
					head = "head2";
				}
				var listbox = "";
				var headtd = "";
				if(index > 0) {
					headtd = '<td>单</td><td>双</td><td>大</td><td>小</td>';
				} else {
					headtd = '<td>0</td>' +
						'<td>1</td>' +
						'<td>2</td>' +
						'<td>3</td>' +
						'<td>4</td>' +
						'<td>5</td>' +
						'<td>6</td>' +
						'<td>7</td>' +
						'<td>8</td>' +
						'<td>9</td>';
				}
				listbox = '<div class="lianmlist">' +
					'<div  class="head ' + head + '">' + rank + '</div>' +
					'<table cellpadding="0" cellspacing="0" border="0">' +
					'<tr class="tr1">' + headtd + '</tr>' +
					'<tr class="tr2">' + tdthml + '</tr>' +
					'</table>' +
					'</div>';
				$("#liangmianbox").append(listbox);
			});
		} else {
			//				alert("数据加载异常！");
		}
	}
}
//长龙连开提醒
method.loadLongData = function(jsondata) {
	if(typeof jsondata != "object") {
		data = JSON.parse(jsondata);
	} else {
		data = JSON.stringify(jsondata);
		data = JSON.parse(data);
	}
	if(data.errorCode == 0) {
		if(data.result.businessCode == 0) {
			data = data.result.data;
			if(config.ifdebug) {
				console.log("长龙连开提醒长度:" + data.length + "结果数据：" + JSON.stringify(data));
			}
			$("#longDrag").empty("");
			for(var i = 0, len = data.length; i < len; i++) {
				var rank = tools.typeOf("qiuqiu1", data[i].rank);
				var state = tools.typeOf("stated", data[i].state);
				var counthtml = data[i].count >= 5 ? "<span style='color:#f11821'>" + data[i].count + "</span>" : "<span>" + data[i].count + "</span>";
				var html = "<li><span>" + rank + "</span>：<span>" + state + "</span>" + counthtml + "期</li>";
				if(data[i].rank == 11 || data[i].rank == 1 || data[i].rank == 2) {
					html = "<li><span>" + rank + "</span>：<span>" + state + "</span>" + counthtml + "期</li>";
				}
				$("#longDrag").append(html);
			}
		} else {
			//			alert("数据加载失败！");
		}
	}

}