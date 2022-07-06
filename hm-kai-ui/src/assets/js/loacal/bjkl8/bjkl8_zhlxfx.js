$(function() {
	//设置当前时间
	var current_time = getDateStr(0);
	$("#date").val(current_time);
//	loadotherData();
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

	//单选项卡切换功能
	var ifxgdxlm = ""; //记录选项卡为综合模式
	$(".listheadl").find("span").live("click", function() {
			$(this).siblings().removeClass("xxselected")
			$(this).addClass("xxselected");
			var type = $(this).find("i").text();
			if(type == "1") { //选项卡为综合模式
				ifxgdxlm = "1";
				$("#dxms,#lmms").hide(200);
				$("#zhms").show(200);
				excuteZhmsSelect(zhmssxmc, zhmssxlz);
			} else if(type == "2") { //选项卡为单选模式
				ifxgdxlm = "1";
				$("#zhms,#lmms").hide(200);
				$("#dxms").show(200);
				excuteZhmsSelect(dxmssxmc, dxmssxlz);
			} else if(type == "3") { //选项卡为两面模式
				ifxgdxlm = "3";
				$("#dxms,#zhms").hide(200);
				$("#lmms").show(200);
				excuteZhmsSelect(lmmssxmc, lmmssxlz);
			}
		})
		//今天，昨天，明天选择操作
	$(".listheadrl span").live("click", function() {
			$(this).siblings().removeClass("checked");
			$(this).addClass("checked");
//			createHtmlGetData(null);
			var id = $(this).attr("id");
			if(id == "today") {
				$(".now_l").css("display", "none");
				$(".jinri").css("display", "inline-block");
				$(".jinri").text("今天");
				listData(getDateStr(0));
			} else if(id == "yesterday") {
				$(".now_l").css("display", "none");
				$(".jinri").css("display", "inline-block");
				$(".jinri").text("昨天");
				listData(getDateStr(-1));
			} else if(id == "qianday") {
				$(".now_l").css("display", "none");
				$(".jinri").css("display", "inline-block");
				$(".jinri").text("前天");
				listData(getDateStr(-2));
			}
		})
		//综合
		//兼听点击li
	$(".zhclear").live("click", function() {
		zhmsMcMethod("zhclear", this);
	});
	$(".zhcheckall").live("click", function() {
		zhmsMcMethod("all", this);
	});

	$("#zhms li").hover(function() {
		if($(this).hasClass("checked")) {
			$(this).addClass("hoverli");
		}
	}, function() {
		if($(this).hasClass("hoverli")) {
			$(this).removeClass("hoverli");
		}
	});
	$("#zhms .sinli").live("click", function() {
		zhmsMcMethod("zhchecksing", this);
	});
	$("#zhms .zhmslz .lztype").live("click", function() {
		zhmsLzMethod("zhchecksing", this);
	});
	//单选
	$("#dxms li").hover(function() {
		if($(this).hasClass("checked")) {
			$(this).addClass("hoverli");
		}
	}, function() {
		if($(this).hasClass("hoverli")) {
			$(this).removeClass("hoverli");
		}
	});
	$("#dxms .lztype").live("click", function() {
		dxmsLzMethod("", this);
	});
	$("#dxms .sinli").live("click", function() {
		dxmsMcMethod("", this);
	});
	//两面
	$("#lmms .zhclear").live("click", function() {
		lmmsMcMethod("zhclear", this);
	});
	$("#lmms .zhcheckall").live("click", function() {
		lmmsMcMethod("all", this);
	});
	$("#lmms li").hover(function() {
		if($(this).hasClass("checked")) {
			$(this).addClass("hoverli");
		}
	}, function() {
		if($(this).hasClass("hoverli")) {
			$(this).removeClass("hoverli");
		}
	});
	$("#lmms .sinli").live("click", function() {

		lmmsMcMethod("zhchecksing", this);
	});
	$("#lmms .lztype").live("click", function() {
		lmmsLzMethod("zhchecksing", this);
	});

	//开奖记录js tab切换
	$("#kaijiangjl").delegate("li", "click", function() {
		var id = $(this).attr("id");
		if($(this).hasClass("selected")) {
			$(this).removeClass("selected");
			if(id == "jrsmtj") {
				$("." + id).hide("200");
			} else if(id == "cltx") {
				$("." + id).hide("200");
			} else if(id == "hmfb") {
				$("." + id).hide("100");
				$("." + id).animate({
					opacity: "0"
				}, 200);
			}
		} else {
			if($(this).hasClass("kaijiltit")) {
				return;
			} else {
				$(this).addClass("selected");
				if(id == "jrsmtj") {
					$("." + id).show("200");
				} else if(id == "cltx") {
					$("." + id).show("200");
				} else if(id == "hmfb") {
					$("." + id).show("100");
					$("." + id).animate({
						opacity: "1"
					}, 200);
				}
			}

		}
	});
	//查看车号分布 选项卡
	var tabarr = []; //存放已经选中的选项卡
	$("#chakanchfb").delegate("li", "click", function() {});
	//查看车号分布：选中执行方法
	function ckchfb(num) {
		if(tabarr.length) {
			var flag = false;
			for(var i = 0; i < tabarr.length; i++) {
				if(num == tabarr[i]) {
					tabarr.splice(i, 1); //定义为取消
					flag = true;
				}
			}
			if(!flag) {
				tabarr.push(num);
			}
		} else {
			tabarr.push(num);
		}
		//执行循环
		excuteCkchfb(false);
	}
	//查看号码分布
	function excuteCkchfb(flag) {
		var li = $("#jrsmhmtj").find(".imgnumber").children().addClass("selectedOpacity");
		//遍历选项卡
		for(var i = 0, len1 = tabarr.length; i < len1; i++) {
			//当选择号码一
			//得到一个当前遍历的li中的号码
			$("#jrsmhmtj .imgnumber li").each(function() {
				if($(this).text() == tabarr[i]) {
					$(this).removeClass("selectedOpacity");
				}
			});
		}
	}
	//	判断是否同时选中
	var ifarrDxDs = [];
	//向数组中添加已选中的对象
	function ifArrHas(num) {
		if(ifarrDxDs.length <= 0) {
			ifarrDxDs.push(num);
		} else {
			for(var i = 0, len = ifarrDxDs.length; i < len; i++) {
				if(ifarrDxDs[i] == num) {
					return;
				}
			}
			ifarrDxDs.push(num);
		}
	}
	//当前行计算连续出现的次数
	$(".box .btnCheck").live("click", function() {
		tongjiCount($(this)); //调用统计方法
	});

	//大小单双分布  ---大小
	$("#daxiaodsfb").delegate("li", "click", function() {});
	//判断是否为大小 type：为当前点击按钮类型 ifmyself：表示当时按钮是否取消选中
	function excuteDx(type, ifmyself) {}
	$("#waringbox").delegate("i", "click", function() {
		$(this).parent().parent().hide("200");
	});

	//初始化时间控件
	$('#datebox').calendar({
		trigger: '#date',
		zIndex: 999,
		format: 'yyyy-mm-dd',
		onSelected: function(view, date, data) {
			var date = formatDate(date);
			listData(date);
			if(config.ifdebug) {
				console.log("选择日期时触发" + date);
			}

			var date1 = date.split("-");
			var month = parseInt(date1[1]);
			var day = parseInt(date1[2]);
			var current_date = month + "/" + day;
			$(".jinri").css("display", "none");
			$(".now_l").css("display", 'inline-block').text(current_date);
		},
		onClose: function(view, date, data) {
			if(config.ifdebug) {
				console.log("关闭是触发");
			}
		}
	});
	//启动加载数据
	//getData();
	//初始化单选模式
	//初始化两面模式
	//lmmsMcMethod("all", null);
	//lmmsLzMethod("all", null);
	//初始化综合模式
	//zhmsMcMethod("all", null);
	//zhmsLzMethod("all", null);
//	listData(); //初始页面请求路珠list数据
	dxmsMcMethod("all", $("#dxms").find(".selected"));
	dxmsLzMethod("all", $("#dxms").find(".checked"));
});

//**************************************************************
function formatDate(date) {
	var y = date.getFullYear();
	var m = date.getMonth() + 1;
	m = m < 10 ? '0' + m : m;
	var d = date.getDate();
	d = d < 10 ? ('0' + d) : d;
	//  return y + '-' + m + '-' + d;  
};

function tongjiCount(obj) {
	var $obj = $(obj);
	//得到选择的次数
	var txtNum = $obj.parent().find(".txtNum").val();
	//得到路珠类型
	var sectype = $obj.parent().find(".secType").val();
	//得到路珠类型
	var ifds = $obj.parent().parent().find(".ifds").text();
	var str = "";
	if(ifds == "总和大小") {//总和大小
		if(sectype == -1) {
			str = "小";
		} else if(sectype == 0){
			str = "和";
		} else if(sectype == 1){
			str = "大";
		}
	} else if(ifds == "总和单双") {//总和单双
		if(sectype == -1) {
			str = "双";
		} else if(sectype == 0){
			str = "和";
		} else if(sectype == 1){
			str = "单";
		}
	} else if(ifds == "总和大小单双") {//总和大小单双
		if(sectype == 1) {
			str = "总大单";
		} else if(sectype == 2){
			str = "总大双";
		} else if(sectype == 3){
			str = "总小单";
		} else if(sectype == 4){
			str = "总小双";
		} else if(sectype == 5){
			str = "总和";
		}
	} else if(ifds == "前后和") {//前后和 
		if(sectype == -1) {
			str = "后多";
		} else if(sectype == 0){
			str = "前后和";
		} else if(sectype == 1){
			str = "前多";
		}
	}
	else if(ifds == "单双和") {//单双和；
		if(sectype == -1) {
			str = "双多";
		} else if(sectype == 0){
			str = "单双和";
		} else if(sectype == 1){
			str = "单多";
		}
	}
	else if(ifds == "五行") {//五行  
		if(sectype == 1) {
			str = "金";
		} else if(sectype == 2){
			str = "木";
		} else if(sectype == 3){
			str = "水";
		} else if(sectype == 4){
			str = "火";
		} else if(sectype == 5){
			str = "土";
		}
	}
	$obj.parent().parent().parent().parent().parent().find(".tablebox").html();
	var $tdlist = $obj.parent().parent().parent().parent().parent().find(".tablebox").children();
	var countnum = 0;
	$tdlist.each(function(index) {
		var count = $(this).find("p").length;
		$(this).css({
			"background-color": "",
			"color": "#666666"
		});
		if(count >= txtNum && str == $(this).children('p').html()) {
			$(this).css({
				"background-color": "rgb(253, 173, 86)",
				"color": "#666666"
			});
			countnum += 1;
		}
	});
	$obj.parent().parent().find(".sec_count").text(countnum);
}
/* 今日统计\统计连续出现次数  */
function doCheck(mol, id) {
	var txtNum = $("." + mol + "  .item_" + id + " .lz_table_head td .txtNum").val(); //输入连续出现次数
	var inputStr = $("." + mol + "  .item_" + id + " .lz_table_head td .secType").val(); //选择查询类型
	var inputNum = Number("0" + txtNum);
	var n = 0;
	$("." + mol + "  .item_" + id + " .lz_table_con td").each(function() { //搜：结果
		$(this).removeClass("shaw");
		if($(this).children('p').html() == inputStr && $(this).children('p').length >= inputNum) {
			$(this).removeAttr("style");
			$(this).addClass("shaw");
			n++;
		}
	});
	$("." + mol + "  .item_" + id + " .lz_table_head td .sec_count").html(n);

	$("." + mol + "  .item_" + id + "  .lz_table_head td .count").each(function() { //今日统计
		var str = $(this).attr("data");
		var m = 0;
		$("." + mol + "  .item_" + id + " .lz_table_con td p").each(function() {
			if($(this).html() == str) {
				m++;
			}
		});
		$(this).html(m);
	});
	/*当前开出期字体加粗  附加闪烁效果*/
	var i = 0;
	var result = $("." + mol + "  .item_" + id + " .lz_table_con td:first-child p:last ");
	result.css("font-weight", "bold");
	var timeOutId = setTimeout(function() {
		result.fadeOut(100).fadeIn(100);
		i++;
		if(i == 1) {
			timeOutId = setInterval(arguments.callee, 600);
		}
		if(i == 30) {
			window.clearInterval(timeOutId);
			//result.css("font-weight", "normal");
		}
	}, 1000);
}

//公用随机数
//得到随机数
function excutenum() {
	var j = Math.floor(Math.random() * 10); //得到0到9的随机数
	//var j = Math.ceil(Math.random()*10);//得到0到10的随机数
	return j;
}

function excutenum1_6() {
	var j = Math.floor(Math.random() * 6); //得到1到6的随机数
	return j;
}
//PK拾动画******************start
var jnumber = $("#jnumber>li");
var res = [];
var lilength = 0;
var time = 0;
//启动终极动画
function sendj(arr) {
	var timeidc = setTimeout("sendj()", 100);
	var lastli = "";
	if(lilength == arr.length - 1) {
		lastli = "li_after";
		clearTimeout(timeidc);
		lilength = 0;
	}
	//终极真实数据显示
	$("#jnumber").append("<li class='nub" + arr[lilength] + " " + lastli + "'></li>");
	lilength++;
}
//执行动画的方法
function excutek() {
	var arr = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10];
	for(var i = 0, len = arr.length; i < len; i++) {
		var j = Math.floor(Math.random() * arr.length);
		res[i] = arr[j];
		arr.splice(j, 1);
	}
	for(var a = 0, len = jnumber.length; a < len; a++) {
		jnumber[a].className = "nub" + res[a];
		if(a == len - 1) {
			jnumber[a].style.marginRight = "0";
		}
	}
	time++;
	var timeId = setTimeout("excutek()", 200);
	if(time >= 25) {
		clearTimeout(timeId);
		$("#jnumber").html("");
		var lilengh = 0;
		sendj();
	}
}
//excutek(); //启动动画
//PK拾动画******************end
//主页moserhover
$(".bothover").hover(function() {
	$(this).find(".toright").css("background-color", "#FFFFFF");
	$(".botline").css("border", "none");
	$(this).find(".childmenu").show();
}, function() {
	$(this).find(".toright").css("background-color", "");
	$(".botline").css("border", "");
	$(this).find(".childmenu").hide();
});
//头部开奖数据请求
function ajaxRequst(issue) {}
//请求路珠list数据
function listData(date) {
	var action = date == undefined ? "" : "date=" + date;
	$.ajax({
		url: urlbublic + "LuckTwenty/queryComprehensiveRoadBead.do?" + action,
		type: "GET",
		data: {
			"lotCode": lotCode
		},
		success: function(data) {
			//执行数据请求
			createHtmlList(data);
		},
		error: function(data) {
			setTimeout(function() {
				loadotherData();
			}, 1000);
			if(config.ifdebug) {
				console.log("data error");
			}
			//createHtml(data);	
		}
	});
}
//异步时时开奖追加到list中 传入一下期开奖期号
function repeatGetData(preIssue) {
	$.ajax({
		url: urlbublic + "LuckTwenty/getRoadBeadStateByIssue.do?preIssue=" + preIssue,
		type: "GET",
		data: {
			"lotCode": lotCode
		},
		success: function(data) {
			//执行数据请求
			createHtmlGetData(data);
		},
		error: function(data) {
			if(config.ifdebug) {
				console.log("路珠请求错误!");
			}
			//createHtml(data);	
		}
	});
}

function parseTonum(str) {
	return str.charAt(0) * 1 <= 0 ? str.charAt(1) : str;
}
var localllistdata = {};
var localheaddata = {};
//今日双面数据
function loadTodayData(jsondata) {}

function minci(num, type) {
	if(type == "rank") {
		//名次,1-10分别为第一——第十名,11为冠亚和
		switch(num * 1) {
			case 1:
				return "冠军";
				break;
			case 2:
				return "亚军";
				break;
			case 3:
				return "三";
				break;
			case 4:
				return "四";
				break;
			case 5:
				return "五";
				break;
			case 6:
				return "六";
				break;
			case 7:
				return "七";
				break;
			case 8:
				return "八";
				break;
			case 9:
				return "九";
				break;
			case 10:
				return "十";
				break;
			case 11:
				return "冠亚和";
				break;
		}
	} else if(type == "state") {
		//形态:如1.单2.双,3.大4.小,5.龙6.虎
		switch(num * 1) {
			case 1:
				return "单";
				break;
			case 2:
				return "双";
				break;
			case 3:
				return "大";
				break;
			case 4:
				return "小";
				break;
			case 5:
				return "龙";
				break;
			case 6:
				return "虎";
				break;
		}
	}

}
//长龙连开提醒
function loadLongData(jsondata) {
	if(typeof jsondata != "object") {
		data = JSON.parse(jsondata);
	} else {
		data = JSON.stringify(jsondata);
		data = JSON.parse(data);
	}
	data = data.result.data;
	$("#cltxul").html("");
	for(var i = 0, len = data.length; i < len; i++) {
		var rank = minci(data[i].rank, "rank");
		var state = minci(data[i].state, "state");
		var counthtml = data[i].count >= 5 ? "<span style='color:#f11821'>" + data[i].count + "</span>" : "<span>" + data[i].count + "</span>";
		var html = "<li>第<span>" + rank + "</span>名：&nbsp;&nbsp;<span>" + state + "</span>&nbsp;&nbsp;" + counthtml + "期</li>";
		if(data[i].rank == 11 || data[i].rank == 1 || data[i].rank == 2) {
			html = "<li><span>" + rank + "</span>：&nbsp;&nbsp;<span>" + state + "</span>&nbsp;&nbsp;" + counthtml + "期</li>";
		}
		$("#cltxul").append(html);
	}

}
//初始加载动画
function excuteAnimate(arr, id) {
	var arr = arr;
	var id = id;
	var arrlength = arr.length;
	var time = 0;
	var jnumberid = $(id);
	$(jnumberid).html("");
	var timer = setInterval(function() {
		if(time < arrlength) {
			var lihtml = "<li class='nub" + arr[time] + "'><i style='font-size:10px'>" + arr[time] + "</i></li>";
			$(jnumberid).append(lihtml);
			time++;
		} else {
			clearInterval(timer);
		}
	}, 100);
}
//开奖动画
var intervalPk10 = null;
//动画方法
function excuteOpenAnimate(id) {
	var time = 600;
	var jnumberid = $(id).find("li");
	$(".opening").show();
	$(".clock").hide(); //隐藏倒计时
	intervalPk10 = setInterval(function() {
		var arr = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10];
		var res = [];
		var lilength = 10;
		time--;
		for(var i = 0; i < lilength; i++) {
			var j = Math.floor(Math.random() * arr.length);
			res[i] = arr[j];
			arr.splice(j, 1);
		}
		var lihtml = "";
		for(var a = 0; a < lilength; a++) {
			var className = res[a] < lilength ? "nub0" + res[a] : "nub" + res[a];
			lihtml += "<li class='" + className + "'></li>"
		}
		$(id).empty();
		$(id).append(lihtml);
		if(time == 100) {
			$("#waringbox").show(300); //显示网络waring提示
			//当请求时间超过规定的最大时间，显示上一次的开奖号码
			excuteAnimate((localStorage.drawCode).split(","), "#jnumber");
		}
		//重新reload页面，重新请求接口
		if(time == 0) {
			clearInterval(intervalPk10);
			window.location.reload();
		}

	}, 100);
}
//获得当前日的日期
function getSystime() {
	var date = new Date();
	var year = date.getFullYear(); //获取当前年份
	var mon = date.getMonth() + 1; //获取当前月份
	var da = date.getDate(); //获取当前日
	var day = date.getDay(); //获取当前星期几
	var h = date.getHours(); //获取小时
	var m = date.getMinutes(); //获取分钟
	var s = date.getSeconds(); //获取秒
	var d = document.getElementById('Date');
	return year + "-" + mon + "-" + da;
	//	return {today:year + "-" + mon + "-" + da,yesterday}
}

function getDateStr(AddDayCount) {
	var dd = new Date();
	dd.setDate(dd.getDate() + AddDayCount); //获取AddDayCount天后的日期
	var y = dd.getFullYear();
	var m = dd.getMonth() + 1; //获取当前月份的日期
	var d = dd.getDate();
	return y + "-" + m + "-" + d;
	/* document.write("前天："+GetDateStr(-2));
	document.write("<br />昨天："+GetDateStr(-1));
	document.write("<br />今天："+GetDateStr(0));
	document.write("<br />明天："+GetDateStr(1));
	document.write("<br />后天："+GetDateStr(2));
	document.write("<br />大后天："+GetDateStr(3));*/
}

function clearinterval(interval) {
	clearInterval(intervalPk10);
}
//综合路珠页面的功能操作************************start***************
var zhmssxmc = []; //筛选名次
var zhmssxlz = []; //筛选路珠
var dxmssxlz = []; //筛选路珠
var dxmssxmc = []; //筛选名次
var lmmssxmc = []; //筛选名次
var lmmssxlz = []; //选择路珠
//综合模式方法--筛选名次
function zhmsMcMethod(type, obj) {
	var $obj = $(obj);
	//全选
	if(type == "all") {
		zhmssxmc = []; //清空数组
		if(!(zhmssxmc.length >= 10)) {
			$($("#zhms").find(".sinli")).each(function(index) {
				if($(this).hasClass("title") || $(this).hasClass("zhcheckall") || $(this).hasClass("zhclear")) {
					return;
				} else {

					zhmssxmc.push($(this).find("i").text());
					$(this).addClass("checked");
				}
			});
		}
		//清空
	} else if(type == "zhclear") {
		$($("#zhms").find(".sinli")).each(function(index) {
			if($(this).hasClass("checked")) {
				$(this).removeClass("checked");
				zhmssxmc = []; //清空数组
			}
		});
		//单选
	} else if(type == "zhchecksing") {
		//清除当前样式
		var clicktxt = $obj.find("i").text();
		console.log("clicktxt:" + clicktxt + "hasclass:" + $obj.hasClass("checked"));
		$obj.removeClass("hoverli");
		if($obj.hasClass("checked")) {
			$obj.removeClass("checked");
			for(var i = 0, len = zhmssxmc.length; i < len; i++) {
				//
				if((zhmssxmc[i]) == clicktxt) {
					//清除当前被选择的索引
					zhmssxmc.splice(i, 1);
				}
			}
		} else {
			$obj.addClass("hoverli");
			$obj.addClass("checked");
			if(zhmssxmc.length <= 0) {
				zhmssxmc.push(clicktxt);
			} else {
				for(var i = 0, len = zhmssxmc.length; i < len; i++) {
					if((zhmssxmc[i]) == clicktxt) {
						break;
					}
				}
				zhmssxmc.push(clicktxt);
			}
		}
		if(config.ifdebug) {
			console.log("zhmssxmc" + JSON.stringify(zhmssxmc));
		}
	}
	if(config.ifdebug) {
		console.log("最后数组中有几个：" + zhmssxmc.length);
	}
	//传入名次和路珠
	excuteZhmsSelect(zhmssxmc, zhmssxlz);
}
//综合模式方法--筛选路珠
function zhmsLzMethod(type, obj) {
	if(type == "all") {
		zhmssxlz = []; //清空数组
		if(!(zhmssxlz.length >= 10)) {
			$($("#zhms .zhmslz").find(".lztype")).each(function(index) {
				zhmssxlz.push($(this).find("i").text());
				$(this).addClass("checked");
			});
		}
	} else {
		var $obj = $(obj);
		//清除当前样式
		var clicktxt = $obj.find("i").text();
		$obj.removeClass("hoverli");
		if($obj.hasClass("checked")) {
			$obj.removeClass("checked");
			for(var i = 0, len = zhmssxlz.length; i < len; i++) {
				//
				if((zhmssxlz[i]) == clicktxt) {
					//清除当前被选择的索引
					zhmssxlz.splice(i, 1);
				}
			}
		} else {
			$obj.addClass("hoverli");
			$obj.addClass("checked");
			if(zhmssxlz.length <= 0) {
				zhmssxlz.push(clicktxt);
			} else {
				for(var i = 0, len = zhmssxlz.length; i < len; i++) {
					if((zhmssxlz[i]) == clicktxt) {
						break;
					}
				}
				zhmssxlz.push(clicktxt);
			}
		}
		if(config.ifdebug) {
			console.log(zhmssxlz);
		}
	}
	//传入名次和路珠
	excuteZhmsSelect(zhmssxmc, zhmssxlz);
}

//***************单选模式***start*********************
//筛选路珠
function dxmsLzMethod(type, obj) {

	if(type == "all") {
		var $dxms = $("#dxms .lztype");
		$dxms.each(function(index) {
			dxmssxlz.push($(this).find("i").text());
			$(this).addClass("checked")
		});
		excuteZhmsSelect(dxmssxmc, dxmssxlz);
		return;
	}
	//
	var $obj = $(obj);
	var clicktxt = $obj.find("i").text();
	if(config.ifdebug) {
		console.log("clicktxt:" + clicktxt + "hasclass:" + $obj.hasClass("checked"));
	}
	$obj.removeClass("hoverli");
	if($obj.hasClass("checked")) {
		$obj.removeClass("checked");
		for(var i = 0, len = dxmssxlz.length; i < len; i++) {
			//
			if((dxmssxlz[i]) == clicktxt) {
				//清除当前被选择的索引
				dxmssxlz.splice(i, 1);
			}
		}
	} else {
		$obj.addClass("hoverli");
		$obj.addClass("checked");
		if(dxmssxlz.length <= 0) {
			dxmssxlz.push(clicktxt);
		} else {
			for(var i = 0, len = dxmssxlz.length; i < len; i++) {
				if((dxmssxlz[i]) == clicktxt) {
					break;
				}
			}
			dxmssxlz.push(clicktxt);
		}
	}
	if(config.ifdebug) {
		console.log("dxmssxlz" + JSON.stringify(dxmssxlz));
	}
	excuteZhmsSelect(dxmssxmc, dxmssxlz);

}
//名次筛选
function dxmsMcMethod(type, obj) {
	dxmssxmc = [];
	if(type == "all") {
		var $txt = $("#dxms .xuanzhemc li:nth-child(2) span");
		dxmssxmc.push($txt.text());
		$txt.parent().addClass("selected");
		excuteZhmsSelect(dxmssxmc, dxmssxlz);
		return;
	}
	var $obj = $(obj);

	var clicktxt = $obj.find("span").text();
	if(config.ifdebug) {
		console.log("clicktxt:" + clicktxt + "hasclass:" + $obj.hasClass("checked"));
	}
	$obj.siblings().removeClass("selected");
	$obj.removeClass("hoverli");
	$obj.addClass("selected");
	dxmssxmc.push(clicktxt);
	if(config.ifdebug) {
		console.log("dxmssxlz" + JSON.stringify(dxmssxmc));
	}
	excuteZhmsSelect(dxmssxmc, dxmssxlz);
}

//***************单选模式*****end*********************
//***************两面模式*****start*********************
//两面模式方法--筛选名次
function lmmsMcMethod(type, obj) {
	var $obj = $(obj);
	$obj.removeClass("hoverli");
	//全选
	if(type == "all") {
		lmmssxmc = []; //清空数组
		if(!(lmmssxmc.length >= 10)) {
			$($("#lmms").find(".sinli")).each(function(index) {
				if($(this).hasClass("title") || $(this).hasClass("zhcheckall") || $(this).hasClass("zhclear")) {
					return;
				} else {
					lmmssxmc.push($(this).find("i").text());
					$(this).addClass("checked");
				}
			});
		}
		if(config.ifdebug) {
			console.log("全选" + JSON.stringify(lmmssxmc));
		}
		//清空
	} else if(type == "zhclear") {
		$($("#lmms").find(".sinli")).each(function(index) {
			if($(this).hasClass("checked")) {
				$(this).removeClass("checked");
				lmmssxmc = []; //清空数组
			}
		});
		//单选
	} else if(type == "zhchecksing") {
		//清除当前样式
		var clicktxt = $obj.find("i").text();
		if($obj.hasClass("checked")) {
			$obj.removeClass("checked");
			for(var i = 0, len = lmmssxmc.length; i < len; i++) {
				//
				if((lmmssxmc[i]) == clicktxt) {
					//清除当前被选择的索引
					lmmssxmc.splice(i, 1);
				}
			}
		} else {
			$obj.addClass("hoverli");
			$obj.addClass("checked");
			if(lmmssxmc.length <= 0) {
				lmmssxmc.push(clicktxt);
			} else {
				for(var i = 0, len = lmmssxmc.length; i < len; i++) {
					if((lmmssxmc[i]) == clicktxt) {
						break;
					}
				}
				lmmssxmc.push(clicktxt);
			}
		}
	}
	if(config.ifdebug) {
		console.log("最后数组中有几个：" + lmmssxmc.length);
	}
	//传入名次和路珠
	excuteZhmsSelect(lmmssxmc, lmmssxlz);
}
//两面模式方法--筛选路珠
function lmmsLzMethod(type, obj) {
	if(type == "all") {
		var $lmms = $("#lmms>.xuanzhemc .lztype:nth-child(2)");
		var html = $lmms.html();
		lmmssxlz.push($lmms.find("span").text());
		$lmms.addClass("selected")
		excuteZhmsSelect(lmmssxmc, lmmssxlz);
		return;
	}
	var $obj = $(obj);
	$obj.removeClass("hoverli");
	var clicktxt = $obj.find("span").text();
	if(config.ifdebug) {
		console.log("clicktxt:" + clicktxt + "hasclass:" + $obj.hasClass("checked"));
	}
	$obj.siblings().removeClass("selected");
	$obj.addClass("selected");
	lmmssxlz = [];
	lmmssxlz.push(clicktxt);
	if(config.ifdebug) {
		console.log("dxmssxlz" + JSON.stringify(lmmssxlz));
	}
	excuteZhmsSelect(lmmssxmc, lmmssxlz);
}
//***************两面模式*****end*********************

//隐藏和显示//前面为名次，后面为路珠
function excuteZhmsSelect(mc, lz) {
	var newselected = [];
	for(var i = 0, len1 = mc.length; i < len1; i++) {
		for(var j = 0, len2 = lz.length; j < len2; j++) {
			if(mc[i] >= 6) {
				newselected.push(mc[i] + "" + lz[j]);
			} else {
				newselected.push(mc[i] + "" + lz[j]);
			}
		}
	}
	if(config.ifdebug) {
		console.log("所显示的divbox：" + newselected + "    newselectedlength:" + newselected.length);
	}
	$(".box .item").hide();
	$(newselected).each(function(index) {
		$("#box" + newselected[index]).show();
	})
}
//单选模式方法
function dxmsMethod() {
	$($("#zhms").find("li")).each(function(index) {
		if(config.ifdebug) {
			console.log($(this).html());
		}
	})
}
//两面模式方法
function lmmsMethod() {
	$($("#zhms").find("li")).each(function(index) {
		if(config.ifdebug) {
			console.log($(this).html());
		}
	})
}
//综合路珠页面的功能操作************************end*****************
//构建路珠开奖记录数据
function createHtmlList(jsondata) {
	var data = null;
	if(typeof jsondata != "object") {
		data = JSON.parse(jsondata);
	} else {
		data = JSON.stringify(jsondata);
		data = JSON.parse(data);
	}
	var dataarr = data.result.data;
	if(dataarr.length <= 1) {
		$(".listcontent").find(".box").hide();
	} else {
		$(".listcontent").find(".box").show();
		$(dataarr).each(function(index) {
			forRank(dataarr[index].rank, dataarr[index]) //目前状态state只有1
		});
	}
}
//构建路珠开奖记录异步数据
function createHtmlGetData(jsondata) {
	if(config.ifdebug) {
		console.log("路珠异步请求结果：" + JSON.stringify(jsondata));
	}
	var data = null;
	if(typeof jsondata != "object") {
		data = JSON.parse(jsondata);
	} else {
		data = JSON.stringify(jsondata);
		data = JSON.parse(data);
	}
	var dataarr = data.result.data;
	$(dataarr).each(function(index) {
		forRankYibu(dataarr[index].rank, dataarr[index])
	})
}
//校正小背景偏移
function bgPostionX($boxnumb) {
	var tdlenth = $boxnumb.find(".tablebox td").length;
	if(tdlenth % 2 != 0) {
		$boxnumb.find(".item_con_small").css({
			"background-positionX": "0"
		});
	} else {
		$boxnumb.find(".item_con_small").css({
			"background-positionX": "-29px"
		});
	}
}

//校正大背景偏移
function bg_bigPositionX($boxnumb) {
	var tdlength = $boxnumb.find(".tablebox td").length;
	if(tdlength % 2 != 0) {
		$boxnumb.find(".item_con_big").css({
			"background-positionX":"0"
		});
	} else {
		$boxnumb.find(".item_con_big").css({
			"background-positionX":"-58px"
		});
	}
}
//根据不同的排名去填值
function forRank(rank, data) {
	//rank:名次,0：总和大小；1：总和单双；2：总和大小单双；3：前后和 ；4： 单双和； 5：五行  
	//state形态:如1.单双;2.大小;3.龙虎
	//totals：长度为2的数组,数组第一个元素表示单、大、龙;第二个元素表示双、小、虎
	//roadBeads:数组,数组的元素值为0或1,1表示单、大、龙;0表示双、小、虎
	//向不同的box中填值1到5有大小单双龙虎三种类型  6-11到有2个类型，没有龙虎
	var $boxnumb = $("#box" + boxList(rank, data));

	if(rank == 0) { //#box01
		$boxnumb.find(".small_count").text(data.totals[0]);
		$boxnumb.find(".big_count").text(data.totals[1]);
		$boxnumb.find(".sum_count").text(data.totals[2]);
	} else if(rank == 1) { //#box11
		$boxnumb.find(".double_count").text(data.totals[0]);
		$boxnumb.find(".single_count").text(data.totals[1]);
		$boxnumb.find(".singleDouble_count").text(data.totals[2]);
	} else if(rank == 2) { //#box21
		$boxnumb.find(".totalSingle_count").text(data.totals[0]);
		$boxnumb.find(".totalBigDouble_count").text(data.totals[1]);
		$boxnumb.find(".totalSmallSingle_count").text(data.totals[2]);
		$boxnumb.find(".totalSmallDouble_count").text(data.totals[3]);
		$boxnumb.find(".totalCount_count").text(data.totals[4]);
	} else if(rank == 3) { //#box31
		$boxnumb.find(".behind_more").text(data.totals[0]);
		$boxnumb.find(".before_more").text(data.totals[1]);
		$boxnumb.find(".beforeBehind_more").text(data.totals[2]);
	} else if(rank == 4) { //#box41
//		debugger
		$boxnumb.find(".single_more").text(data.totals[0]);
		$boxnumb.find(".double_more").text(data.totals[1]);
		$boxnumb.find(".signleDouble_more").text(data.totals[2]);
	} else if(rank == 5) { //#box51
		$boxnumb.find(".jin_count").text(data.totals[0]);
		$boxnumb.find(".mu_count").text(data.totals[1]);
		$boxnumb.find(".shui_count").text(data.totals[2]);
		$boxnumb.find(".huo_count").text(data.totals[3]);
		$boxnumb.find(".tu_count").text(data.totals[4]);
	}

	$boxnumb.find(".ifds").text(typeOf("state", data.rank));
	var tdhtml = "";
	$boxnumb.find(".tablebox").empty();
	//迭代list
	$(data.roadBeads).each(function(index) {
		var stylestr = "";
		//得到上一行中最后一个号码判断是否换行追加
		var lastp = $boxnumb.find(".tablebox td:nth-child(1) p:last-child").text();
		$boxnumb.find(".tablebox td:nth-child(1) p:last-child").css({
			"font-weight": "normal"
		});

		//通过rank来添加路珠
		var ranks = rank;
		var newroadbeads = "";
		if(ranks == 0) { //总和大小： -1：小; 0:和；1:大 ;
			newroadbeads = typeOf("dxh", data.roadBeads[index]);
		} else if(ranks == 1) { //总和单双：-1：双;0:和; 1:单 ;
			newroadbeads = typeOf("dsh", data.roadBeads[index]);
		} else if(ranks == 2) { //总和大小单双：1:总大单，2:总大双，3:总小单，4:总小双 5:总和
			newroadbeads = typeOf("zhzh", data.roadBeads[index]);
		} else if(ranks == 3) { //前后和：-1后多；0：和 1:前多;
			newroadbeads = typeOf("qhh", data.roadBeads[index]);
		} else if(ranks == 4) { //单双和：-1：双多；0：和 1:单多；
			newroadbeads = typeOf("dsd", data.roadBeads[index]);
		} else if(ranks == 5) { //总和五行sumWuXing 1-5对应金木水火土 
			newroadbeads = typeOf("wuxing", data.roadBeads[index]);
		}

		if(newroadbeads == "大" || newroadbeads == "双" || newroadbeads == "总大双" || newroadbeads == "后多" || newroadbeads == "双多") {
			stylestr = "style='color:#F00078'";
		} else if(newroadbeads == "总小双") {
			stylestr = "style='color:#FF2FFF'";
		} else if(newroadbeads == "总小单" || newroadbeads == "前后和" || newroadbeads == "单双和") {
			stylestr = "style='color:#009100'";
		}

		if(lastp == newroadbeads) { //如果与上一次的号码相同则在同一列中追加反之增加新列
			tdhtml = "<p  " + stylestr + ">" + lastp + "</p>"
			$boxnumb.find(".tablebox td:nth-child(1)").append(tdhtml);
		} else {
			var bgcolor = "";
			var hasclas = $boxnumb.find(".tablebox td:nth-child(1)").hasClass("bgcolor");
			if(!hasclas) { //如果为单就加背景
				bgcolor = "bgcolor";
			}
			tdhtml = "<td class='" + bgcolor + "'><p " + stylestr + ">" + newroadbeads + "</p></td>"
			if(lastp == "") { //第一次加载数据时
				$boxnumb.find(".tablebox").append(tdhtml);
			} else { //已经有数据
				$boxnumb.find(".tablebox td:nth-child(1)").before(tdhtml);
			}

		};
	});
	//校正背景
	if(rank == 0 || rank == 1 || rank == 5){
		bgPostionX($boxnumb);		
	} else{
		bg_bigPositionX($boxnumb);
	}
	/*当前开出期字体加粗  附加闪烁效果*/
	var i = 0;
	var result = $boxnumb.find(".tablebox td:nth-child(1) p:last-child");
	result.css({
		"font-weight": "bold"
	});
	var timeOutId = setTimeout(function() {
		result.fadeOut(100).fadeIn(100);
		i++;
		if(i == 1) {
			timeOutId = setInterval(arguments.callee, 600);
		}
		if(i == 30) {
			window.clearInterval(timeOutId);
			//result.css("font-weight", "normal");
		}
	}, 1000);
}

function boxList(rank, data) {
	//rank:0：总和大小;1:总和单双；2总和大小单双；3：前后和；4:单双和；5：五行
	//state形态:暂时只有1
	//totals：长度为n的数组
	//roadBeads:数组,数组的元素值为0、1、-1；如-1：小、双、双多、后多； 1：大、单、单多、前多、金
	//向不同的box中填值1到5有大小单双龙虎三种类型  6-11到有2个类型，没有龙虎
	var box = "";
	if(rank == 0 && data.state == 1) {
		box = "" + rank + "" + data.state;
	} else if(rank == 1 && data.state == 1) {
		box = "" + rank + "" + data.state;
	} else if(rank == 2 && data.state == 1) {
		box = "" + rank + "" + data.state;
	} else if(rank == 3 && data.state == 1) {
		box = "" + rank + "" + data.state;
	} else if(rank == 4 && data.state == 1) {
		box = "" + rank + "" + data.state;
	} else if(rank == 5 && data.state == 1) {
		box = "" + rank + "" + data.state;
	} else if(rank == 6 && data.state == 1) {
		box = "" + rank + "" + data.state;
	}
	return box;
}
//根据不同的排名去填值
function forRankYibu(rank, data) {
	var $boxnumb = $("#box" + boxList(rank, data));
	var tdhtml = "";
	//迭代list
	var stylestr = "";
	//得到上一行中最后一个号码判断是否换行追加
	var lastp = $boxnumb.find(".tablebox td:nth-child(1) p:last-child").text();
	$boxnumb.find(".tablebox td:nth-child(1) p:last-child").css({
		"font-weight": "normal"
	});
	var state = data.state;
	var newroadbeads = "";
	if(state == "1") {
		newroadbeads = (data.value == 1) ? "单" : "双";
	} else if(state == "2") {
		newroadbeads = (data.value == 1) ? "大" : "小";
	} else if(state == "3") {
		newroadbeads = (data.value == 1) ? "龙" : "虎";
	}
	if(newroadbeads == "双" || newroadbeads == "龙" || newroadbeads == "大") {
		stylestr = "style='color:#f1020b'";
	}
	var $left_count = $boxnumb.find(".left_count");
	var $right_count = $boxnumb.find(".right_count");
	var leftxt = $left_count.text() * 1 + 1;
	var rigtxt = $right_count.text() * 1 + 1;
	if(newroadbeads == "单") {
		$left_count.text(leftxt);
	} else if(newroadbeads == "双") {
		$right_count.text(rigtxt);
	} else if(newroadbeads == "大") {
		$left_count.text(leftxt);
	} else if(newroadbeads == "小") {
		$right_count.text(rigtxt);
	} else if(newroadbeads == "龙") {
		$left_count.text(leftxt);
	} else if(newroadbeads == "虎") {
		$right_count.text(rigtxt);
	}
	
	if(lastp == newroadbeads) { //如果与上一次的号码相同则在同一列中追加反之增加新列
		tdhtml = "<p  " + stylestr + ">" + lastp + "</p>"
		$boxnumb.find(".tablebox td:nth-child(1)").append(tdhtml);
	} else {
		var bgcolor = "";
		var hasclas = $boxnumb.find(".tablebox td:nth-child(1)").hasClass("bgcolor");
		if(!hasclas) { //如果为单就加背景
			bgcolor = "bgcolor";
		}
		tdhtml = "<td class='" + bgcolor + "'><p " + stylestr + ">" + newroadbeads + "</p></td>"
		if(lastp == "") { //第一次加载数据时
			$boxnumb.find(".tablebox").append(tdhtml);
		} else { //已经有数据
			$boxnumb.find(".tablebox td:nth-child(1)").before(tdhtml);
		}
	}
	//校正背景
	bgPostionX($boxnumb);
	/*当前开出期字体加粗  附加闪烁效果*/
	var i = 0;
	var result = $boxnumb.find(".tablebox td:nth-child(1) p:last-child");
	result.css({
		"font-weight": "bold"
	});
	var timeOutId = setTimeout(function() {
		result.fadeOut(100).fadeIn(100);
		i++;
		if(i == 1) {
			timeOutId = setInterval(arguments.callee, 600);
		}
		if(i == 30) {
			window.clearInterval(timeOutId);
			//result.css("font-weight", "normal");
		}
	}, 1000);
}

function loadotherData() {
	//	listData(date);
	listData(getDateStr(0));
	$(".listheadrl span").siblings().removeClass("checked");
	$("#today").addClass("checked");
	$(".jinri").text("今天");
}