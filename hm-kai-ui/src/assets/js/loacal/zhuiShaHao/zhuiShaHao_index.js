var FunObj = {};
var rows = $("#select").val(); // 加载多少期数据
var fundate = "";
var publicUrl = config.publicUrl;
var timeOutInit = "",
	cutdownTime = null;
var ranData = "",
	timeDate = "",
	isIE = "",
	screenWidth = "",
	topH = "";
var xaranData = "";
var powText = $(".powP").html();
var lot_type = $(".lotteryType>ul>.check").attr("data-text");
//var publicUrl = "http://13.112.108.218/";

$(function() {
	fundate = formatDate(new Date());
	//加载头部和尾部
	$("#headdivbox").load("/html/public/head.html", function() {
		if(config.ifdebug) {
			console.log("request is over!");
		}
	});
	$("#fooderbox").load("/html/public/fooder.html", function() {
		if(config.ifdebug) {
			console.log("request is over!");
		}
	});
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
	$(".backTop").on("click", function() {
		$('body,html').animate({
			scrollTop: 0
		}, 500);
	})
	//定义统一透明样式兼容IE8
	function opacity(obj, num, filt) {
		obj.style.filter = "alpha(opacity:" + filt + ")";
		obj.style.opacity = num;
	}

	// 默认加载北京pk10 的追号计划
	$('.cqssc_ran').hide();
	//  FunObj.zhuiHao_pk10("pk10");
	//  FunObj.XaHao_pk10("pk10")
	//请求PK10系列的数据
	var Loaction = window.location.search.split("=")[1];
	if(Loaction == undefined) {
		return;
	}
	lot_type = Loaction;
	if(Loaction == "pk10" || Loaction == "jisusc" || Loaction == "aozxy10" || Loaction == "xingyft") {
		if(Loaction == "pk10") {
			$("#logoImage").attr("src", "../../img/zshImage/PK10/PK10.png?v=20187201945");
			$("#caiName").text("北京PK10");
			$("#pk10Set").addClass("pk10Yellow");
		} else if(Loaction == "jisusc") {
			$("#logoImage").attr("src", "../../img/zshImage/PK10/jisusc.png?v=20187201945");
			$("#caiName").text("极速赛车");
		} else if(Loaction == "aozxy10") {
			$("#logoImage").attr("src", "../../img/zshImage/PK10/azxy10.png?v=20187201945");
			$("#caiName").text("澳洲幸运10");
		} else if(Loaction == "xingyft") {
			$("#logoImage").attr("src", "../../img/zshImage/PK10/xingyft.png");
			$("#caiName").text("极速飞艇");
		}
	} else if(Loaction == "gdklsf") {
		FunObj.zhuiHao_klsf(Loaction);
	}
	 if(Loaction == "pk10_Kill"){
	    lot_type = "pk10";
        $("#zhuiA").addClass("check_b check_a").siblings().removeClass("check_a");
//      $(".view_content").css("left","-1202px");
       $(".XHao").show().siblings().hide();
        FunObj.XaHao_pk10(lot_type);
        FunObj.zhuantiStyle_pk10(lot_type); //pk10样式切换
        changeLogo(lot_type)
        FunObj.getTimecut(lot_type)
        FunObj.isRan(lot_type);
        FunObj.introduction("北京PK拾");
        $(".list-title").find("li.pk10_name").show().siblings("li.nameUl:not('.pk10_name')").hide();
        $(".list_box").find("ul.pk10_name").show().siblings("ul.nameUl").hide();
	}else if(Loaction == "cqssc_Kill"){
        lot_type = "cqssc";
        $("#zhuiA").addClass("check_b check_a").siblings().removeClass("check_a");
//      $(".view_content").css("left","-1202px");
        $(".XHao").show().siblings().hide();
        FunObj.zhuantiStyle_cqssc(lot_type); //时时彩样式切换
        FunObj.XaHao_cqssc(lot_type);
        changeLogo(lot_type)
        FunObj.getTimecut(lot_type)
        FunObj.isRan(lot_type);
        FunObj.introduction("重庆时时彩");
        $(".list-title").find("li.ssc_name").show().siblings("li.nameUl:not('.ssc_name')").hide();
        $(".list_box").find("ul.ssc_name").show().siblings("ul.nameUl").hide();
	}else{
	    FunObj.orzhuihao(Loaction)
	}
	// 点击更多计划，触发head中元素click
	TouchEvent();
	//日期選擇
	$(".date").val(formatDate(new Date()));

	function formatDate(date) {
		var y = date.getFullYear();
		var m = date.getMonth() + 1;
		//      m = m < 10 ? '0' + m : m;
		var d = date.getDate();
		d = d < 10 ? ('0' + d) : d;
		return y + '-' + m + '-' + d;
	};
	// 11选5追号中，修改下面li里的字
	FunObj.LiText = function() {
		var litext = $(".11check5_ran>.check_ran").text();
		if($(".check_a").hasClass("zhao_a")) {
			if(FunObj.IsPk10(lot_type)) {
				$(".ZHao_1 .check_zhuOrXa").text("冠军购彩 " + litext);
			} else if(FunObj.IsXahao11check5(lot_type) || FunObj.IsXanwu(lot_type)) {
				$(".ZHao_1  .check_zhuOrXa").text("第一球购彩 " + litext);
			} else if(FunObj.zhuiOrXaHao() && FunObj.Iseagxy(lot_type)) {
				$(".ZHao_1  .check_zhuOrXa").text("和值购彩计划 ");
			} else if(FunObj.zhuiOrXaHao() && FunObj.Iskuai_3(lot_type)) {
				$(".ZHao_1  .check_zhuOrXa").text("点数购彩 " + litext);
			}
		}
	}

	//初始化时间控件
	$('#datebox').calendar({
		trigger: '.date',
		zIndex: 999,
		format: 'yyyy-mm-dd',
		onSelected: function(view, date, data) {
			var date = formatDate(date);
			fundate = date;
			console.log("选择日期时触发" + date);
			if(date != formatDate(new Date())) {
				$("#select").hide()
				rows = 0;
			} else {
				$("#select").show();
				rows = $("#select").val();
			}
			FunObj.orzhuihao(lot_type);
		},
		onClose: function(view, date, data) {
			if(config.ifdebug) {
				console.log("关闭是触发");
			}
		}
	});

	//控制悬浮的计划区
	var H = document.body.clientHeight;
	topH = H / 2 - 420;
//	$(".leftList,.leftList_cqssc,.leftList_kuai3,.leftList_syx5,.leftList_gdklsf").css("top", topH-40);
	//根据屏幕分辨率，缩放内容部分
	isIE = (!!window.ActiveXObject || "ActiveXObject" in window);
	screenWidth = screen.width;
	Object.screenWidthFun(screenWidth);
});

//根据不同的屏幕分辨率重设彩种css内容-----------------------------
Object.screenWidthFun = function(screenWidth){
	if(screenWidth <= 1440) {
		//判断是否是IE浏览器 
//		console.log(isIE);
		if(isIE) {
			if(screen.width >=1280 && screen.width < 1360){ //使用1280的缩放和left IE
//				console.log("screen.width" + screen.width)
				$("#leftL_pk10").css("left","5px");
				$("#leftL_cqssc").css("left","-5px");
				$("#leftL_syx5").css("left","0px");
				$("#leftL_kuai3").css("left","10px");
				$("#leftL_gdklsf").css("left","-5px");
				Object.resetZSH1280_css(lot_type);
				$("#fooderbox").find(".footer1").addClass("footTopNew");
				$(".leftList,.leftList_cqssc,.leftList_kuai3,.leftList_syx5,.leftList_gdklsf").css("top", topH-40);
			} else{ //1400   IE
//				console.log(screen.width)
				$("#leftL_pk10").css("left","10px");
				$("#leftL_cqssc").css("left","-10px");
				$("#leftL_syx5").css("left","10px");
				$("#leftL_kuai3").css("left","10px");
				$("#leftL_gdklsf").css("left","10px");
				$(".listcontent").css("margin-left","170px");
				$(".leftList_kuai3,.leftList_syx5").css("top", "65px");
				$(".leftList,.leftList_cqssc,.leftList_gdklsf").css("top", topH-100);
				Object.resetIEZSH_css(lot_type);
			}
		} else{ //1400   非IE
//			console.log(screen.width)
			Object.resetZSH_css(lot_type);//根据不同的屏幕分辨率重设彩种css内容
			$(".leftList,.leftList_cqssc,.leftList_kuai3,.leftList_syx5,.leftList_gdklsf").css("top", topH-20);
		}
	} else {
		$(".leftList,.leftList_cqssc,.leftList_kuai3,.leftList_syx5,.leftList_gdklsf").css("top", topH-20);
		if($(".scaleDiv").hasClass("ScreenScaleDiv1366") || $(".scaleDiv").hasClass("ScreenScaleDiv1280")) {
			$(".scaleDiv").removeClass("ScreenScaleDiv1366");
			$(".listcontent").removeClass("Screenlistcontent1366");
			$(".scaleDiv").removeClass("ScreenScaleDiv1280");
			$(".listcontent").removeClass("Screenlistcontent1280");
			$("#detailMess").removeClass().addClass("detailMess");
		}
	}
}
Object.resetZSH_css = function(lot_type){
	$("#detailMess").removeClass().addClass("detailMess");
	if(lot_type == "jisuksan" || lot_type == "jsksan" || lot_type == "gxft" || lot_type == "jlft" || lot_type == "hebft" || lot_type == "nmgft" || lot_type == "ahft" || lot_type == "fjft" || lot_type == "hubft" || lot_type == "bjft"){
//		$(".kuai3Style").find(".listcontent ").addClass("Screenlistcontent1366_kuai3");
	} else if(lot_type == "cqssc" || lot_type == "tjssc" || lot_type == "xjssc" || lot_type == "jisussc" || lot_type == "aozxy5"){
		$(".detailMess").addClass("Screenlistcontent1366_ssc") //时时彩样式重置
	} else if(lot_type == "gdklsf" || lot_type == "aozxy8" || lot_type == "tjklsf" || lot_type == "cqxync" || lot_type == "jisuklsf"){
		$(".detailMess").addClass("Screenlistcontent1366_klsf") //快乐十分样式重置
	} else if(lot_type == "gdsyxw" || lot_type == "sdsyydj" || lot_type == "zjef" || lot_type == "jxef" || lot_type == "jsef" || lot_type == "ahef" || lot_type == "shef"|| lot_type == "lnef" || lot_type == "hbef" || lot_type == "gxef"|| lot_type == "jlef" || lot_type == "nmgef" || lot_type == "jisuef"){
		$(".detailMess").addClass("Screenlistcontent1366_sxy5") //11选5样式重置
	}
	$(".scaleDiv").addClass("ScreenScaleDiv1366");
	$(".listcontent").addClass("Screenlistcontent1366");
}
Object.resetIEZSH_css = function(lot_type){
	$("#detailMess").removeClass().addClass("detailMess");
	if(lot_type == "jsksan" || lot_type == "gxft" || lot_type == "jlft" || lot_type == "hebft" || lot_type == "nmgft" || lot_type == "ahft" || lot_type == "fjft" || lot_type == "hubft" || lot_type == "bjft"){
		if($(".scaleDiv").hasClass("scaleDiv_otherLeft") || $(".scaleDiv").hasClass("scaleDiv_zshpk10")){
			if($(".scaleDiv").hasClass("scaleDiv_otherLeft") && $(".scaleDiv").hasClass("scaleDiv_zshpk10")){
				$(".scaleDiv").removeClass("scaleDiv_otherLeft");
				$(".scaleDiv").removeClass("scaleDiv_zshpk10");
			} else if($(".scaleDiv").hasClass("scaleDiv_otherLeft")){
				$(".scaleDiv").removeClass("scaleDiv_otherLeft");
			} else if($(".scaleDiv").hasClass("scaleDiv_zshpk10")){
				$(".scaleDiv").removeClass("scaleDiv_zshpk10");
			}
			$(".scaleDiv").removeClass("scaleDiv_otherLeft");
		}
		$(".scaleDiv").addClass("scaleDiv_zshkuai3");
	} else if(lot_type == "cqssc" || lot_type == "tjssc" || lot_type == "xjssc" || lot_type == "jisussc" || lot_type == "aozxy5"){
		$(".detailMess").addClass("Screenlistcontent1366_ssc") //时时彩样式重置
		Object.ifResetStyle();
		$(".scaleDiv").addClass("scaleDiv_otherLeft")
	} else if(lot_type == "gdklsf" || lot_type == "aozxy8" || lot_type == "tjklsf" || lot_type == "cqxync" || lot_type == "jisuklsf"){
		$(".detailMess").addClass("Screenlistcontent1366_klsf") //快乐十分样式重置
		Object.ifResetStyle();
		$(".scaleDiv").addClass("scaleDiv_otherLeft")
	} else if(lot_type == "gdsyxw" || lot_type == "sdsyydj" || lot_type == "zjef" || lot_type == "jxef" || lot_type == "jsef" || lot_type == "ahef" || lot_type == "shef"|| lot_type == "lnef" || lot_type == "hbef" || lot_type == "gxef"|| lot_type == "jlef" || lot_type == "nmgef" || lot_type == "jisuef"){
		$(".detailMess").addClass("Screenlistcontent1366_sxy5") //十一选五样式重置
		Object.ifResetStyle();
		$(".scaleDiv").addClass("scaleDiv_otherLeft")
	} else if(lot_type == "pk10" || lot_type == "jisusc" || lot_type == "aozxy10" || lot_type == "xingyft"){ //pk10样式重置
		if($(".scaleDiv").hasClass("scaleDiv_otherLeft") || $(".scaleDiv").hasClass("scaleDiv_zshkuai3")){
			if($(".scaleDiv").hasClass("scaleDiv_otherLeft") && $(".scaleDiv").hasClass("scaleDiv_zshkuai3")){
				$(".scaleDiv").removeClass("scaleDiv_otherLeft");
				$(".scaleDiv").removeClass("scaleDiv_zshkuai3");
			} else if($(".scaleDiv").hasClass("scaleDiv_otherLeft")){
				$(".scaleDiv").removeClass("scaleDiv_otherLeft");
			} else if($(".scaleDiv").hasClass("scaleDiv_zshkuai3")){
				$(".scaleDiv").removeClass("scaleDiv_zshkuai3");
			}
		}
		$(".scaleDiv").addClass("scaleDiv_zshpk10")
	} else{//pc蛋蛋样式重置
		Object.ifResetStyle();
	}
	$(".scaleDiv").addClass("ScreenScaleDiv1366");
	$(".listcontent").addClass("Screenlistcontent1366");
}
Object.resetZSH1280_css = function(lot_type){
	if(lot_type == "jisuksan" || lot_type == "jsksan" || lot_type == "gxft" || lot_type == "jlft" || lot_type == "hebft" || lot_type == "nmgft" || lot_type == "ahft" || lot_type == "fjft" || lot_type == "hubft" || lot_type == "bjft"){
//		$(".kuai3Style").find(".listcontent ").addClass("Screenlistcontent1366_kuai3");
	} 
	$(".scaleDiv").addClass("ScreenScaleDiv1280");
	$(".listcontent").addClass("Screenlistcontent1280");
}
//判断IE1366分辨率下是否重置了样式
Object.ifResetStyle = function(){
	if($(".scaleDiv").hasClass("scaleDiv_zshkuai3") || $(".scaleDiv").hasClass("scaleDiv_zshpk10")){
		if($(".scaleDiv").hasClass("scaleDiv_zshkuai3") && $(".scaleDiv").hasClass("scaleDiv_zshpk10")){
			$(".scaleDiv").removeClass("scaleDiv_zshkuai3");
			$(".scaleDiv").removeClass("scaleDiv_zshpk10");
		} else if($(".scaleDiv").hasClass("scaleDiv_zshkuai3")){
			$(".scaleDiv").removeClass("scaleDiv_zshkuai3");
		} else if($(".scaleDiv").hasClass("scaleDiv_zshpk10")){
			$(".scaleDiv").removeClass("scaleDiv_zshpk10");
		}
	}
}
//彩种，鼠标移进来改变边框颜色,click 添加class
$("#lotteryType>ul>li:not(.disabled)").on({
	mouseenter: function() {
		if($(this).hasClass("check")) {
			return
		}
		$(this).css("border-color", "#FA8E19");
	},
	mouseout: function() {
		if($(this).hasClass("check")) {
			return
		}
		$(this).css("border-color", "#E0E0E0");
	},
	click: function() {
		var showElem = $(this).attr("data-text");
		clearTimeout(timeOutInit);
		FunObj.isRan(showElem);
		$(this).addClass("check").css("border-color", "#FA8E19").siblings().removeClass("check");
		$(".lotteryType>ul>li:not(.check)").css("border-color", "#E0E0E0");
		console.log(checkThisCaiName);
		FunObj.orzhuihao(showElem);
		console.log($(this).text());
		FunObj.introduction($(this).text());
	}
})

$(".ran>ul>li:not(.disabled)").on({
	mouseenter: function() {
		if($(this).hasClass("check_ran")) {
			return
		}
		$(this).css("border-color", "#FA8E19");
	},
	mouseout: function() {
		if($(this).hasClass("check_ran")) {
			return
		}
		$(this).css("border-color", "#E0E0E0");
	},
	click: function() {
		$(this).addClass("check_ran").css("border-color", "#FA8E19").siblings().removeClass("check_ran");
		$(".ran>ul>li:not(.check_ran)").css("border-color", "#E0E0E0");

	}
})
$(".ch-left").on("click", "a", function(e) {
	e.preventDefault();
	if($(this).hasClass("zhao_a")) {
		$(".ZHao_1").show().siblings().hide();
		$(this).addClass("check_a");
	} else {
		$(".XHao").show().siblings().hide();
		$(this).addClass("check_b");
	}
	$(this).addClass("check_a").siblings().removeClass("check_a").removeClass("check_b");
	FunObj.isRan(lot_type);
	FunObj.orzhuihao(lot_type);
	FunObj.introduction($("li[data-text=" + lot_type + "]").text())
})
FunObj.isWidth = function(plan) {
	var wid = plan == "A" ? 177 : (plan == "B" ? 140 : 105)
	$(".listcontent .ZHao .zuHao_plan .xhuiHao_content li>.zhui_ul").css("cssText", "width:" + wid + "px !important"); // 140 105
}
FunObj.isWidth2 = function(plan) {
	var wid = plan == "A" ? 210 : (plan == "B" ? 175 : 140)
	$(".listcontent .ZHao .zuHao_plan .xhuiHao_content li>.zhui_ul").css("cssText", "width:" + wid + "px !important"); // 140 105
}
// 追号请求函数 --北京pk10
FunObj.zhuiHao_pk10 = function(lot_type) {
	var data = {
		lotCode: lotCode[lot_type], //lotCode[lot_type]
		rows: rows,
		date: fundate
	}
	$.ajax({
		url: publicUrl + "LotteryPlan/getPksPlanList.do", //  PursueNum/getPksPursueNumList.do
		type: "GET",
		data: data,
		success: function(data) {
			//执行数据请求
			var checkNum = $(".11check5_ran>.check_ran").attr("data-text");
			FunObj.zhuiHao_pk10_analysis(data, checkNum);
			ranData = data;
		},
		error: function(data) {
			console.log("追号请求错误!");
		}
	});
}

//追号数据分析函数  --北京pk10
FunObj.zhuiHao_pk10_analysis = function(data, plan, Timeout) {
	FunObj.LiText();
	if(typeof(data) == "string") {
		data = JSON.parse(data);
	}
//	console.log(data);
	$(".ZHao_1 .xhuiHao_content").remove();
	$(".ZHao_1 .sum_ul").remove();
	if(data.result.data == "" || data.result.data == []) {
		$(".check_issue").text(0);
		return;
	}
	if($("#select").css("display") == "none") {
		$(".check_issue").text(data.result.data.length);
	} else {
		$(".check_issue").text(data.result.data.length - 1);
	}
	var html = "",
		sum_html = "<ul class='sum_ul'>",
		plicpursueNum = "";
	$.each(data.result.data, function(i, p) {
		var preDrawCode = p.preDrawCode.split(",");
		var pursueNum = p["plan" + plan].split(",");
		if(i == 0) { //将最新计划号码添加至计划详情展示中
			plicpursueNum = pursueNum
		}
		var cla = "";
		if(p["profit" + plan].indexOf("-") == -1) {
			cla = "resultRed";
		} else {
			cla = "resultBlue";
		}
		if(pursueNum.indexOf(preDrawCode[0]) != -1) {
			var clas = "resultCheckLi";
		} else {
			var clas = ""
		}
		if(preDrawCode.length == 1) {
			var html_text = "等待开奖";
			var Firnum = false;
		} else {
			var Firnum = preDrawCode[0] * 1;
			var html_text = "<ul class='opactionUl'>" +
				"<li class='opaction pk10_" + preDrawCode[0] * 1 + "'><i class='" + clas + "'></i></li>" +
				"<li class='pk10_" + preDrawCode[1] * 1 + "'></li>" +
				"<li class='pk10_" + preDrawCode[2] * 1 + "'></li>" +
				"<li class='pk10_" + preDrawCode[3] * 1 + "'></li>" +
				"<li class='pk10_" + preDrawCode[4] * 1 + "'></li>" +
				"<li class='pk10_" + preDrawCode[5] * 1 + "'></li>" +
				"<li class='pk10_" + preDrawCode[6] * 1 + "'></li>" +
				"<li class='pk10_" + preDrawCode[7] * 1 + "'></li>" +
				"<li class='pk10_" + preDrawCode[8] * 1 + "'></li>" +
				"<li class='pk10_" + preDrawCode[9] * 1 + "'></li>" +
				"</ul>"
		}
		var clss = "";
		var num4 = pursueNum[3] != undefined ? "<li class='pk10_" + pursueNum[3] * 1 + "'><i class='" + (Firnum == pursueNum[3] * 1 ? 'resultCheckLi' : '') + "'></i></li>" : "";
		var num5 = pursueNum[4] != undefined ? "<li class='pk10_" + pursueNum[4] * 1 + "'><i class='" + (Firnum == pursueNum[4] * 1 ? 'resultCheckLi' : '') + "'></i></li>" : "";
		html += "<ul class='xhuiHao_content'>" +
			"<li>" + p.preDrawIssue + "期</li>" +
			"<li>" + html_text + "</li>" +
			"<li>" +
			"<ul class='zhui_ul'>" +
			"<li class='pk10_" + pursueNum[0] * 1 + "'><i class='" + (Firnum == pursueNum[0] * 1 ? 'resultCheckLi' : '') + "'></i></li>" +
			"<li class='pk10_" + pursueNum[1] * 1 + "'><i class='" + (Firnum == pursueNum[1] * 1 ? 'resultCheckLi' : '') + "'></i></li>" +
			"<li class='pk10_" + pursueNum[2] * 1 + "'><i class='" + (Firnum == pursueNum[2] * 1 ? 'resultCheckLi' : '') + "'></i></li>" + num4 + num5 +
			"</ul>" +
			"</li>" +
			"<li>" + p["lotteryCost" + plan] + "</li>" +
			"<li>" + p["lotteryCostAll" + plan] + "</li>" +
			"<li class='" + cla + "'>" + p["profit" + plan] + "</li>" +
			"<li>" + p.preDrawTime + "</li>" +
			"</ul>";

	});
	$('.ZHao_1  .zhuiHao_title').after(html);
	FunObj.isWidth(plan);

}
var timerrrrr = {};

//填充最新计划数据
FunObj.pullNewData = function(dataObj) {
//	console.log(dataObj.time);
	if(dataObj.time != undefined) { //time是秒
		clearInterval(timerrrrr);
		FunObj.countDown(dataObj.time);
	}
	var purNum = dataObj.pursueNum;
	var numHtml = "";
	$("#qishu").text(dataObj.preDrawIssue);
	$("#curCost").text(dataObj.lotteryCost);
	$("#sumCost").text(dataObj.lotteryCostAll);
	$("#yinkui").text(dataObj.profit);
	$("#hasIssueI").text(dataObj.lotteryCount);
	if(lot_type == "pk10" || lot_type == "jisusc" || lot_type == "aozxy10" || lot_type == "xingyft") {
		$.each(purNum, function(i) {
			numHtml += "<li class='pk10_" + purNum[i] * 1 + "'></li>"
		});
	} else if(lot_type == "egxy28") { //pc蛋蛋辛运28号码样式
		$.each(purNum, function(i) {
			numHtml += "<li class='pcxy28_" + "'>" + purNum[i] * 1 + "</li>"
		});
	} else if(lot_type == "cqssc" || lot_type == "tjssc" || lot_type == "xjssc" || lot_type == "jisussc" || lot_type == "aozxy5") { //时时彩号码样式
		$.each(purNum, function(i) {
			numHtml += "<li class='cqssc_" + "'>" + purNum[i] * 1 + "</li>"
		});
	} else if(lot_type == "jisuksan" || lot_type == "jsksan" || lot_type == "gxft" || lot_type == "jlft" || lot_type == "hebft" || lot_type == "nmgft" || lot_type == "ahft" || lot_type == "fjft" || lot_type == "hubft" || lot_type == "bjft") { //快3号码样式
		$.each(purNum, function(i) {
			numHtml += "<li class='kuai3_" + "'>" + purNum[i] * 1 + "</li>"
		});
	} else if(lot_type == "jisuklsf" || lot_type == "gdklsf" || lot_type == "aozxy8" || lot_type == "tjklsf" || lot_type == "cqxync") { //快乐十分
		$.each(purNum, function(i) {
			numHtml += "<li class='gdklsf_" + "'>" + purNum[i] * 1 + "</li>"
		});
	} else { //11选5
		$.each(purNum, function(i) {
			numHtml += "<li class='syx5_" + "'>" + purNum[i] * 1 + "</li>"
		});
	}
	$("#planBox").find("ul").empty();
	$("#planBox").find("ul").append(numHtml);
}
FunObj.countDown = function(times) {
	var sys_second = times; //总倒计时秒数
	var s1 = 0,
		s2 = 0,
		m1 = 0,
		m2 = 0,
		h1 = 0,
		h2 = 0;
	if(times == 5 || times <= 0) { //times < 0返回的时间为负值
		$("#kaiStyle").hide();
		$("#openStyle").show();
	} else {
		$("#kaiStyle").show();
		$("#openStyle").hide();
	}
	timerrrrr = setInterval(function() {
		if(sys_second > 1) {
			sys_second -= 1;
			var hour = Math.floor(sys_second / 3600);
			var minute = Math.floor((sys_second % 3600) / 60);
			var second = Math.floor((sys_second % 3600) % 60);
			//			console.log(hour + "  " + minute + "   " + second);
			if(hour >= 0) {
				if(hour == 0) {
					$("#hourDiv").hide(); //隐藏小时
				} else {
					$("#hourDiv").show(); //显示小时
					$("#minFir").removeClass("minFirLeft")
					if(hour < 10 && hour > 0) {
						h1 = 0;
						h2 = hour;
					} else {
						hour = "" + hour;
						h1 = hour.substr(0, 1);
						h2 = hour.substr(1, 1);
					}
				}
			}
			if(minute < 10) {
				m1 = 0;
				m2 = minute;
			} else {
				minute = "" + minute;
				m1 = minute.substr(0, 1);
				m2 = minute.substr(1, 1);
			}
			if(second < 10) {
				s1 = 0;
				s2 = second;
			} else {
				second = "" + second;
				s1 = second.substr(0, 1);
				s2 = second.substr(1, 1);
			}
			$("#horFir").text(h1);
			$("#horSec").text(h2);
			$("#minFir").text(m1);
			$("#minSec").text(m2);
			$("#secFir").text(s1);
			$("#secTwo").text(s2);
			if(sys_second > 1) {
				$("#kaiStyle").show();
				$("#openStyle").hide();
			} else if(sys_second == 1 || sys_second < 1) {
				$("#kaiStyle").hide();
				$("#openStyle").show();
				$("#minFir").removeClass("minFirLeft")
			}
		}
	}, 1000);

}
//专题切换样式 --pk10专题系列
FunObj.zhuantiStyle_pk10 = function(lot_type) {
	$("#bgColor").removeClass().addClass("pk10Style");
	$("#leftL_pk10").addClass("leftList_active").siblings("div").removeClass("leftList_active");
	if(lot_type == "pk10"){
		$("#leftL_pk10>ul").find("#" + lot_type).addClass("pk10Yellow").siblings("li").removeClass();
	} else if(lot_type == "jisusc"){
		$("#leftL_pk10>ul").find("#" + lot_type).addClass("jsscYellow").siblings("li").removeClass();
	} else if(lot_type == "aozxy10"){
		$("#leftL_pk10>ul").find("#" + lot_type).addClass("azxyYellow").siblings("li").removeClass();
	} else{
		$("#leftL_pk10>ul").find("#" + lot_type).addClass("xyftYellow").siblings("li").removeClass();
	}
}
//专题切换样式 --时时彩专题系列
FunObj.zhuantiStyle_cqssc = function(lot_type) {
	$("#bgColor").removeClass().addClass("sscStyle");
	$("#leftL_cqssc").addClass("leftList_active").siblings("div").removeClass("leftList_active");
	$("#leftL_cqssc>ul").find("#" + lot_type).addClass("acNameColor").siblings("li").removeClass("acNameColor");
}
//专题切换样式 --快3专题系列
FunObj.zhuantiStyle_kuai3 = function(lot_type) {
	$("#bgColor").removeClass().addClass("kuai3Style");
	$("#leftL_kuai3").addClass("leftList_active").siblings().removeClass("leftList_active");
	$("#leftL_kuai3>ul").find("#" + lot_type).addClass("acNameColorKuai3").siblings("li").removeClass("acNameColorKuai3");
}
//专题切换样式 --十一选5专题系列
FunObj.zhuantiStyle_syx5 = function(lot_type) {
//	debugger
	$("#bgColor").removeClass().addClass("syx5Style");
	$("#leftL_syx5").addClass("leftList_active").siblings("div").removeClass("leftList_active");
	$("#leftL_syx5>ul").find("#" + lot_type).addClass("acNameColorSYX5").siblings("li").removeClass("acNameColorSYX5");
	
}
//专题切换样式 --广东快乐十分专题系列
FunObj.zhuantiStyle_gdklsf = function(lot_type) {
	$("#bgColor").removeClass().addClass("gdklsfStyle");
	$("#leftL_gdklsf").addClass("leftList_active").siblings("div").removeClass("leftList_active");
	$("#leftL_gdklsf>ul").find("#" + lot_type).addClass("acNameColor").siblings("li").removeClass("acNameColor");
}
//专题切换样式 --pc蛋蛋幸运28专题系列
FunObj.zhuantiStyle_egxy28 = function(lot_type) {
	$("#bgColor").removeClass().addClass("egxyStyle");
	$("#leftL_pcxy28").removeClass("leftL_pk10").siblings("div").removeClass("leftList_active");
}
// 追号请求函数 --重庆时时彩
FunObj.zhuiHao_cqssc = function(lot_type) {
	var data = {
		lotCode: lotCode[lot_type],
		rows: rows,
		date: fundate
	}
	$.ajax({
		url: publicUrl + "LotteryPlan/getSscPlanList.do", //PursueNum/getSscPursueNumList.do
		type: "GET",
		data: data,
		success: function(data) {
			//执行数据请求
			var checkNum = $(".11check5_ran>.check_ran").attr("data-text");
			FunObj.zhuiHao_cqssc_analysis(data, checkNum);
			ranData = data;
		},
		error: function(data) {
			console.log("追号请求错误!");
		}
	});
}
//追号数据分析函数  --重庆时时彩
FunObj.zhuiHao_cqssc_analysis = function(data, plan, Timeout) {
	FunObj.LiText(); //修改下面li里的字
	if(typeof(data) == "string") {
		data = JSON.parse(data);
	}
	$(".ZHao_1 .xhuiHao_content").remove();
	if(data.result.data == "" || data.result.data == []) {
		$(".check_issue").text(0);
		return;
	}
	if($("#select").css("display") == "none") {
		$(".check_issue").text(data.result.data.length);
	} else {
		$(".check_issue").text(data.result.data.length - 1);
	}
	var html = "",
		sum_html = "<ul class='sum_ul'>",
		plicpursueNum = "";
	$.each(data.result.data, function(i, p) {
		var preDrawCode = p.preDrawCode.split(",");
		var pursueNum = p["plan" + plan].split(",");
		var cla = "";
		if(i == 0) { //将最新计划号码添加至计划详情展示中
			plicpursueNum = pursueNum
		}
		if(p["profit" + plan].indexOf("-") == -1) {
			cla = "resultRed";
		} else {
			cla = "resultBlue";
		}
		if(pursueNum.indexOf(preDrawCode[0]) != -1) {
			var clas = "resultCheckLissc";
		} else {
			var clas = ""
		}
		if(preDrawCode.length == 1) {
			var html_text = "等待开奖";
			var Firnum = false;
		} else {
			var Firnum = preDrawCode[0] * 1;
			var html_text = "<ul class='opactionUl'>" +
				"<li class='opaction cqssc_'>" + preDrawCode[0] * 1 + "<i class='" + clas + "'></i></li>" +
				"<li class='cqssc_'>" + preDrawCode[1] * 1 + "</li>" +
				"<li class='cqssc_'>" + preDrawCode[2] * 1 + "</li>" +
				"<li class='cqssc_'>" + preDrawCode[3] * 1 + "</li>" +
				"<li class='cqssc_'>" + preDrawCode[4] * 1 + "</li>" +
				"</ul>"
		}
		var num4 = pursueNum[3] != undefined ? "<li class='cqssc_'>" + pursueNum[3] * 1 + "<i class='" + (Firnum === pursueNum[3] * 1 ? 'resultCheckLissc' : '') + "'></i></li>" : "";
		var num5 = pursueNum[4] != undefined ? "<li class='cqssc_'>" + pursueNum[4] * 1 + "<i class='" + (Firnum === pursueNum[4] * 1 ? 'resultCheckLissc' : '') + "'></i></li>" : "";
		html += "<ul class='xhuiHao_content'>" +
			"<li>" + p.preDrawIssue + "期</li>" +
			"<li>" + html_text + "</li>" +
			"<li>" +
			"<ul class='zhui_ul'>" +
			"<li class='cqssc_'>" + pursueNum[0] * 1 + "<i class='" + (Firnum === pursueNum[0] * 1 ? 'resultCheckLissc' : '') + "'></i></li>" +
			"<li class='cqssc_'>" + pursueNum[1] * 1 + "<i class='" + (Firnum === pursueNum[1] * 1 ? 'resultCheckLissc' : '') + "'></i></li>" +
			"<li class='cqssc_'>" + pursueNum[2] * 1 + "<i class='" + (Firnum === pursueNum[2] * 1 ? 'resultCheckLissc' : '') + "'></i></li>" + num4 + num5 +
			"</ul>" +
			"</li>" +
			"<li>" + p["lotteryCost" + plan] + "</li>" +
			"<li>" + p["lotteryCostAll" + plan] + "</li>" +
			"<li class='" + cla + "'>" + p["profit" + plan] + "</li>" +
			"<li>" + p.preDrawTime + "</li>" +
			"</ul>"
	});
	$('.ZHao_1 .zhuiHao_title').after(html);
	$(".ZHao_1 .xhuiHao_content li ul:first-child").css("width", "183px");
	FunObj.isWidth(plan);
}

// 杀号请求函数 --北京pk10
FunObj.XaHao_pk10 = function(lot_type) {
	var data = {
		lotCode: lotCode[lot_type],
		rows: rows,
		date: fundate
	}
	$.ajax({
		url: publicUrl + "KillNum/getPksKillNumList.do",
		type: "GET",
		data: data,
		success: function(data) {
			//执行数据请求
			var checkNum = $(".pk10_ran>.check_ran").attr("data-text");
			FunObj.XaHao_px10_analysis(data, checkNum);
			xaranData = data;
		},
		error: function(data) {
			console.log("杀号请求错误!");
		}
	});
}

// 杀号数据分析函数 --北京pk10
FunObj.XaHao_px10_analysis = function(data, checkNum) {
	$(".list-content").remove();
	$(".remover_foot").remove();
	if(typeof(data) == "string") {
		data = JSON.parse(data);
	}
	if(data.result.data == "" || data.result.data == []) {
		return;
	}
	var contentHtml = "",
		resHtml = "";
	$.each(data.result.data.list, function(j, k) {
		var cla_col = [],
			xaArr = [],
			textArr = [],
			bgarr = [];
		var arr = k[checkNum + "Num"];
		for(var i = 0; i < arr.length; i++) {
			if((i + 1) % 2 == 0) {
				if(arr[i] == 0) {
					cla_col.push("");
					bgarr.push("");
					textArr.push("-")
				} else if(arr[i] == 1) {
					cla_col.push("col_red");
					bgarr.push("bg_red")
					textArr.push("杀对")
				} else {
					cla_col.push("col_blue");
					bgarr.push("col_blue")
					textArr.push("杀错")
				}
			} else {
				xaArr.push(arr[i])
			}
		}
		if(k.preDrawCode == "") {
			var fragmentHtml = "等待开奖";
		} else {
			var numCode = k.preDrawCode.split(",");
			var fragmentHtml = "<ul class='num_ul'>" +
				"<li class='pk10_" + numCode[0] * 1 + "'></li>" +
				"<li class='pk10_" + numCode[1] * 1 + "'></li>" +
				"<li class='pk10_" + numCode[2] * 1 + "'></li>" +
				"<li class='pk10_" + numCode[3] * 1 + "'></li>" +
				"<li class='pk10_" + numCode[4] * 1 + "'></li>" +
				"<li class='pk10_" + numCode[5] * 1 + "'></li>" +
				"<li class='pk10_" + numCode[6] * 1 + "'></li>" +
				"<li class='pk10_" + numCode[7] * 1 + "'></li>" +
				"<li class='pk10_" + numCode[8] * 1 + "'></li>" +
				"<li class='pk10_" + numCode[9] * 1 + "'></li>" +
				"</ul>"
		}
		contentHtml += "<ul class='list-content'>" +
			"<li>" + k.preDrawIssue + "期</li>" +
			"<li>" + fragmentHtml + "</li>" +
			"<li class='" + cla_col[0] + "'>杀:" + xaArr[0] + "</li>" +
			"<li class='" + bgarr[0] + "'>" + textArr[0] + "</li>" +
			"<li class='" + cla_col[1] + "'>杀:" + xaArr[1] + "</li>" +
			"<li class='" + bgarr[1] + "'>" + textArr[1] + "</li>" +
			"<li class='" + cla_col[2] + "'>杀:" + xaArr[2] + "</li>" +
			"<li class='" + bgarr[2] + "'>" + textArr[2] + "</li>" +
			"<li class='" + cla_col[3] + "'>杀:" + xaArr[3] + "</li>" +
			"<li class='" + bgarr[3] + "'>" + textArr[3] + "</li>" +
			"<li class='" + cla_col[4] + "'>杀:" + xaArr[4] + "</li>" +
			"<li class='" + bgarr[4] + "'>" + textArr[4] + "</li></ul>"
	});
	var resarr = data.result.data[checkNum + "KillRight"];
	var resarr2 = data.result.data[checkNum + "Percent"];
	resHtml = "<ul class='list_footer remover_foot'>" +
		"<li>杀对次数</li>" +
		"<li>" + resarr[0] + "</li>" +
		"<li>" + resarr[1] + "</li>" +
		"<li>" + resarr[2] + "</li>" +
		"<li>" + resarr[3] + "</li>" +
		"<li>" + resarr[4] + "</li></ul>" +
		"<ul class='list_footer remover_foot'>" +
		"<li>成功概率</li>" +
		"<li>" + resarr2[0] + "</li>" +
		"<li>" + resarr2[1] + "</li>" +
		"<li>" + resarr2[2] + "</li>" +
		"<li>" + resarr2[3] + "</li>" +
		"<li>" + resarr2[4] + "</li></ul>";
	$(".list_box>.list-title").after(contentHtml);
	$(".list_footerTil:last").after(resHtml);
}

//////////////////////////////////////////////////////////////////////////////////////////////////////////11选5
// 追号请求函数 --11选5
FunObj.zhuiHao_11check5 = function(lot_type) {
	var data = {
		lotCode: lotCode[lot_type],
		rows: rows,
		date: fundate
	}
	$.ajax({
		url: publicUrl + "LotteryPlan/getEfPlanList.do", //PursueNum/getEfPursueNumList.do
		type: "GET",
		data: data,
		success: function(data) {
			//执行数据请求
			var checkNum = $(".11check5_ran>.check_ran").attr("data-text");
			FunObj.zhuiHao_11check5_analysis(data, checkNum);
			ranData = data;
			//          $(".11check5_ran").on("click", "li", function() {
			//              var checkli = $(this).attr("data-text");
			//              FunObj.zhuiHao_11check5_analysis(data, checkli);
			//              FunObj.LiText(); //修改下面li里的字
			//          })
		},
		error: function(data) {
			console.log("11选5追号请求错误!");
		}
	});
}

//追号数据分析函数  --11选5
FunObj.zhuiHao_11check5_analysis = function(data, plan, Timeout) {
	if(typeof(data) == "string") {
		data = JSON.parse(data);
	}
	FunObj.LiText(); //修改下面li里的字
	if(data.result.data == "" || data.result.data == []) {
		$(".check_issue").text(0);
		$(".ZHao_1 .xhuiHao_content").remove();
		return;
	}
	$(".ZHao_1 .xhuiHao_content").remove();
	if($("#select").css("display") == "none") {
		$(".check_issue").text(data.result.data.length);
	} else {
		$(".check_issue").text(data.result.data.length - 1);
	}
	var html = "",
		sum_html = "<ul class='sum_ul'>",
		plicpursueNum = "";
	$.each(data.result.data, function(i, p) {
		var preDrawCode = p.preDrawCode.split(",");
		var pursueNum = p["plan" + plan].split(",");
		if(i == 0) { //将最新计划号码添加至计划详情展示中
			plicpursueNum = pursueNum
		}
		var cla = "";
		if(p["profit" + plan].indexOf("-") == -1) {
			cla = "resultRed";
		} else {
			cla = "resultBlue";
		}
		if(pursueNum.indexOf(preDrawCode[0]) != -1) {
			var clas = "resultCheckLissc";
		} else {
			var clas = ""
		}
		if(preDrawCode.length == 1) {
			var html_text = "等待开奖";
			var Firnum = false;
		} else {
			var Firnum = preDrawCode[0] * 1;
			var html_text = "<ul class='opactionUl'>" +
				"<li class='opaction cqssc_'>" + preDrawCode[0] * 1 + "<i class='" + clas + "'></i></li>" +
				"<li class='cqssc_'>" + preDrawCode[1] * 1 + "</li>" +
				"<li class='cqssc_'>" + preDrawCode[2] * 1 + "</li>" +
				"<li class='cqssc_'>" + preDrawCode[3] * 1 + "</li>" +
				"<li class='cqssc_'>" + preDrawCode[4] * 1 + "</li>" +
				"</ul>"
		}
		var num4 = pursueNum[3] != undefined ? "<li class='cqssc_'>" + pursueNum[3] * 1 + "<i class='" + (Firnum === pursueNum[3] * 1 ? 'resultCheckLissc' : '') + "'></i></li>" : "";
		var num5 = pursueNum[4] != undefined ? "<li class='cqssc_'>" + pursueNum[4] * 1 + "<i class='" + (Firnum === pursueNum[4] * 1 ? 'resultCheckLissc' : '') + "'></i></li>" : "";
		var num6 = pursueNum[5] != undefined ? "<li class='cqssc_'>" + pursueNum[5] * 1 + "<i class='" + (Firnum === pursueNum[5] * 1 ? 'resultCheckLissc' : '') + "'></i></li>" : "";
		html += "<ul class='xhuiHao_content'>" +
			"<li>" + p.preDrawIssue + "期</li>" +
			"<li>" + html_text + "</li>" +
			"<li>" +
			"<ul class='zhui_ul'>" +
			"<li class='cqssc_'>" + pursueNum[0] * 1 + "<i class='" + (Firnum === pursueNum[0] * 1 ? 'resultCheckLissc' : '') + "'></i></li>" +
			"<li class='cqssc_'>" + pursueNum[1] * 1 + "<i class='" + (Firnum === pursueNum[1] * 1 ? 'resultCheckLissc' : '') + "'></i></li>" +
			"<li class='cqssc_'>" + pursueNum[2] * 1 + "<i class='" + (Firnum === pursueNum[2] * 1 ? 'resultCheckLissc' : '') + "'></i></li>" + num4 + num5 + num6 +
			"</ul>" +
			"</li>" +
			"<li>" + p["lotteryCost" + plan] + "</li>" +
			"<li>" + p["lotteryCostAll" + plan] + "</li>" +
			"<li class='" + cla + "'>" + p["profit" + plan] + "</li>" +
			"<li>" + p.preDrawTime + "</li>" +
			"</ul>"
	});
	$('.ZHao_1 .zhuiHao_title').after(html);
	$(".ZHao_1 .xhuiHao_content li ul:first-child").css("width", "183px");
	FunObj.isWidth2(plan);
}
//////////////////////////////////////////////////////////////////////////////////////////////////////////快乐十分
// 追号请求函数 --快乐十分
FunObj.zhuiHao_klsf = function(lot_type) {
	var data = {
		lotCode: lotCode[lot_type],
		rows: rows,
		date: fundate
	}
	$.ajax({
		url: publicUrl + "LotteryPlan/getKlsfPlanList.do",
		type: "GET",
		data: data,
		success: function(data) {
			//执行数据请求
			var checkNum = $(".11check5_ran>.check_ran").attr("data-text");
			//			FunObj.zhuiHao_klsf_analysis(data, checkNum);
			if(lot_type == "cqxync") {
				FunObj.zhuiHao_cqxync_analysis(data, checkNum);
			} else {
				FunObj.zhuiHao_klsf_analysis(data, checkNum);
			}
			ranData = data;
			//          $(".11check5_ran").on("click", "li", function() {
			//              var checkli = $(this).attr("data-text");
			//              FunObj.zhuiHao_11check5_analysis(data, checkli);
			//              FunObj.LiText(); //修改下面li里的字
			//          })
		},
		error: function(data) {
			console.log("11选5追号请求错误!");
		}
	});
}
//追号数据分析函数  --快乐十分
FunObj.zhuiHao_klsf_analysis = function(data, plan, Timeout) {
	if(typeof(data) == "string") {
		data = JSON.parse(data);
	}
	FunObj.LiText(); //修改下面li里的字
//	console.log(data);
	if(data.result.data == "" || data.result.data == []) {
		$(".check_issue").text(0);
		$(".ZHao_1 .xhuiHao_content").remove();
		return;
	}
	$(".ZHao_1 .xhuiHao_content").remove();
	if($("#select").css("display") == "none") {
		$(".check_issue").text(data.result.data.length);
	} else {
		$(".check_issue").text(data.result.data.length - 1);
	}
	var html = "",
		sum_html = "<ul class='sum_ul'>",
		plicpursueNum = "";
	$.each(data.result.data, function(i, p) {
		var preDrawCode = p.preDrawCode.split(",");
		var pursueNum = p["plan" + plan].split(",");
		if(i == 0) { //将最新计划号码添加至计划详情展示中
			plicpursueNum = pursueNum
		}
		var cla = "",
			claas = "";
		if(p["profit" + plan].indexOf("-") == -1) {
			cla = "resultRed";
		} else {
			cla = "resultBlue";
		}
		if(pursueNum.indexOf((preDrawCode[0]*1).toString()) != -1) {
			var clas = "resultCheckLissc";
		} else {
			var clas = ""
		}
		if(preDrawCode.length == 1) {
			var html_text = "等待开奖";
			var Firnum = false;
		} else {
			var Firnum = preDrawCode[0] * 1;
			var html_text = "<ul class='opactionUl'>" +
				"<li class='opaction " + (preDrawCode[0] * 1 >= 19 ? 'red_gdklsf' : 'blue_gdklsf') + "'>" + preDrawCode[0] * 1 + "<i class='" + clas + "'></i></li>" +
				"<li class=' " + (preDrawCode[1] * 1 >= 19 ? 'red_gdklsf' : 'blue_gdklsf') + "'>" + preDrawCode[1] * 1 + "</li>" +
				"<li class=' " + (preDrawCode[2] * 1 >= 19 ? 'red_gdklsf' : 'blue_gdklsf') + "'>" + preDrawCode[2] * 1 + "</li>" +
				"<li class=' " + (preDrawCode[3] * 1 >= 19 ? 'red_gdklsf' : 'blue_gdklsf') + "'>" + preDrawCode[3] * 1 + "</li>" +
				"<li class=' " + (preDrawCode[4] * 1 >= 19 ? 'red_gdklsf' : 'blue_gdklsf') + "'>" + preDrawCode[4] * 1 + "</li>" +
				"<li class=' " + (preDrawCode[5] * 1 >= 19 ? 'red_gdklsf' : 'blue_gdklsf') + "'>" + preDrawCode[5] * 1 + "</li>" +
				"<li class=' " + (preDrawCode[6] * 1 >= 19 ? 'red_gdklsf' : 'blue_gdklsf') + "'>" + preDrawCode[6] * 1 + "</li>" +
				"<li class=' " + (preDrawCode[7] * 1 >= 19 ? 'red_gdklsf' : 'blue_gdklsf') + "'>" + preDrawCode[7] * 1 + "</li>" +
				"</ul>"
		}
		var num4 = pursueNum[3] != undefined ? "<li class='" + (pursueNum[3] * 1 >= 19 ? 'red_gdklsf' : 'blue_gdklsf') + "'>" + pursueNum[3] * 1 + "<i class='" + (Firnum === pursueNum[3] * 1 ? 'resultCheckLissc' : '') + "'></i></li>" : "";
		var num5 = pursueNum[4] != undefined ? "<li class='" + (pursueNum[4] * 1 >= 19 ? 'red_gdklsf' : 'blue_gdklsf') + "'>" + pursueNum[4] * 1 + "<i class='" + (Firnum === pursueNum[4] * 1 ? 'resultCheckLissc' : '') + "'></i></li>" : "";
		var num6 = pursueNum[5] != undefined ? "<li class='" + (pursueNum[5] * 1 >= 19 ? 'red_gdklsf' : 'blue_gdklsf') + "'>" + pursueNum[5] * 1 + "<i class='" + (Firnum === pursueNum[5] * 1 ? 'resultCheckLissc' : '') + "'></i></li>" : "";
		html += "<ul class='xhuiHao_content'>" +
			"<li>" + p.preDrawIssue + "期</li>" +
			"<li>" + html_text + "</li>" +
			"<li>" +
			"<ul class='zhui_ul'>" +
			"<li class='" + (pursueNum[0] * 1 >= 19 ? 'red_gdklsf' : 'blue_gdklsf') + "'>" + pursueNum[0] * 1 + "<i class='" + (Firnum === pursueNum[0] * 1 ? 'resultCheckLissc' : '') + "'></i></li>" +
			"<li class='" + (pursueNum[1] * 1 >= 19 ? 'red_gdklsf' : 'blue_gdklsf') + "'>" + pursueNum[1] * 1 + "<i class='" + (Firnum === pursueNum[1] * 1 ? 'resultCheckLissc' : '') + "'></i></li>" +
			"<li class='" + (pursueNum[2] * 1 >= 19 ? 'red_gdklsf' : 'blue_gdklsf') + "'>" + pursueNum[2] * 1 + "<i class='" + (Firnum === pursueNum[2] * 1 ? 'resultCheckLissc' : '') + "'></i></li>" + num4 + num5 + num6 +
			"</ul>" +
			"</li>" +
			"<li>" + p["lotteryCost" + plan] + "</li>" +
			"<li>" + p["lotteryCostAll" + plan] + "</li>" +
			"<li class='" + cla + "'>" + p["profit" + plan] + "</li>" +
			"<li>" + p.preDrawTime + "</li>" +
			"</ul>"
	});
	$('.ZHao_1 .zhuiHao_title').after(html);
	$(".ZHao_1 .xhuiHao_content li ul:first-child").css("width", "285px");
	FunObj.isWidth(plan);
}

//追号数据分析函数  --重庆农场
FunObj.zhuiHao_cqxync_analysis = function(data, plan, Timeout) {
	if(typeof(data) == "string") {
		data = JSON.parse(data);
	}
	FunObj.LiText(); //修改下面li里的字
	if(data.result.data == "" || data.result.data == []) {
		$(".check_issue").text(0);
		$(".ZHao_1 .xhuiHao_content").remove();
		return;
	}
	$(".ZHao_1 .xhuiHao_content").remove();
	if($("#select").css("display") == "none") {
		$(".check_issue").text(data.result.data.length);
	} else {
		$(".check_issue").text(data.result.data.length - 1);
	}
	var html = "",
		sum_html = "<ul class='sum_ul'>",
		plicpursueNum = "";
	$.each(data.result.data, function(i, p) {
		var preDrawCode = p.preDrawCode.split(",");
		var pursueNum = p["plan" + plan].split(",");
		if(i == 0) { //将最新计划号码添加至计划详情展示中
			plicpursueNum = pursueNum
		}
		var cla = "",
			claas = "";
		if(p["profit" + plan].indexOf("-") == -1) {
			cla = "resultRed";
		} else {
			cla = "resultBlue";
		}
		if(pursueNum.indexOf((preDrawCode[0]*1).toString()) != -1) {
			var clas = "resultCheckLissc";
		} else {
			var clas = ""
		}
		if(preDrawCode.length == 1) {
			var html_text = "等待开奖";
			var Firnum = false;
		} else {
			var Firnum = preDrawCode[0] * 1;
			var html_text = "<ul class='opactionUl'>" +
				"<li class='opaction " + 'cqnc_' + preDrawCode[0] * 1 + "'><i class='" + clas + "'></i></li>" +
				"<li class=' " + 'cqnc_' + preDrawCode[1] * 1 + "'></li>" +
				"<li class=' " + 'cqnc_' + preDrawCode[2] * 1 + "'></li>" +
				"<li class=' " + 'cqnc_' + preDrawCode[3] * 1 + "'></li>" +
				"<li class=' " + 'cqnc_' + preDrawCode[4] * 1 + "'></li>" +
				"<li class=' " + 'cqnc_' + preDrawCode[5] * 1 + "'></li>" +
				"<li class=' " + 'cqnc_' + preDrawCode[6] * 1 + "'></li>" +
				"<li class=' " + 'cqnc_' + preDrawCode[7] * 1 + "'></li>" +
				"</ul>"
		}
		var num4 = pursueNum[3] != undefined ? "<li class='" + 'cqnc_' + pursueNum[3] * 1 + "'><i class='" + (Firnum === pursueNum[3] * 1 ? 'resultCheckLissc' : '') + "'></i></li>" : "";
		var num5 = pursueNum[4] != undefined ? "<li class='" + 'cqnc_' + pursueNum[4] * 1 + "'><i class='" + (Firnum === pursueNum[4] * 1 ? 'resultCheckLissc' : '') + "'></i></li>" : "";
		var num6 = pursueNum[5] != undefined ? "<li class='" + 'cqnc_' + pursueNum[5] * 1 + "'><i class='" + (Firnum === pursueNum[5] * 1 ? 'resultCheckLissc' : '') + "'></i></li>" : "";
		html += "<ul class='xhuiHao_content'>" +
			"<li>" + p.preDrawIssue + "期</li>" +
			"<li>" + html_text + "</li>" +
			"<li>" +
			"<ul class='zhui_ul'>" +
			"<li class='" + 'cqnc_' + pursueNum[0] * 1 + "'><i class='" + (Firnum === pursueNum[0] * 1 ? 'resultCheckLissc' : '') + "'></i></li>" +
			"<li class='" + 'cqnc_' + pursueNum[1] * 1 + "'><i class='" + (Firnum === pursueNum[1] * 1 ? 'resultCheckLissc' : '') + "'></i></li>" +
			"<li class='" + 'cqnc_' + pursueNum[2] * 1 + "'><i class='" + (Firnum === pursueNum[2] * 1 ? 'resultCheckLissc' : '') + "'></i></li>" + num4 + num5 + num6 +
			"</ul>" +
			"</li>" +
			"<li>" + p["lotteryCost" + plan] + "</li>" +
			"<li>" + p["lotteryCostAll" + plan] + "</li>" +
			"<li class='" + cla + "'>" + p["profit" + plan] + "</li>" +
			"<li>" + p.preDrawTime + "</li>" +
			"</ul>"
	});
	$('.ZHao_1 .zhuiHao_title').after(html);
	$(".ZHao_1 .xhuiHao_content li ul:first-child").css("width", "285px");
	FunObj.isWidth(plan);
}
// 杀号请求函数 --重庆时时彩
FunObj.XaHao_cqssc = function(lot_type) {

	var data = {
		lotCode: lotCode[lot_type],
		rows: rows,
		date: fundate
	}
	$.ajax({
		url: publicUrl + "KillNum/getSscKillNumList.do",
		type: "GET",
		data: data,
		success: function(data) {
			//执行数据请求
			var checkNum = $(".cqssc_ran>.check_ran").attr("data-text");
			FunObj.XaHao_cqssc_analysis(data, checkNum);
			xaranData = data;
		},
		error: function(data) {
			console.log("杀号请求错误!");
		}
	});
}

// 杀号请求函数 --11选5
FunObj.XaHao_11check5 = function(lot_type) {
	var data = {
		lotCode: lotCode[lot_type],
		rows: rows,
		date: fundate
	}
	$.ajax({
		url: publicUrl + "KillNum/getEfKillNumList.do",
		type: "GET",
		data: data,
		success: function(data) {
			//执行数据请求
			var checkNum = $(".cqssc_ran>.check_ran").attr("data-text");
			FunObj.XaHao_cqssc_analysis(data, checkNum);
			xaranData = data;
		},
		error: function(data) {
			console.log("杀号请求错误!");
		}
	});
}

// 杀号数据分析函数 --重庆时时彩
FunObj.XaHao_cqssc_analysis = function(data, checkNum) {
	$(".list-content").remove();
	$(".remover_foot").remove();
	if(typeof(data) == "string") {
		data = JSON.parse(data);
	}
	if(data.result.data == "") {
		return;
	}
	var contentHtml = "",
		resHtml = "";
	$.each(data.result.data.list, function(j, k) {
		var cla_col = [],
			xaArr = [],
			textArr = [],
			bgarr = [];
		var arr = k[checkNum + "Num"];
		for(var i = 0; i < arr.length; i++) {
			if((i + 1) % 2 == 0) {
				if(arr[i] == 0) {
					cla_col.push("");
					bgarr.push("");
					textArr.push("-")
				} else if(arr[i] == 1) {
					cla_col.push("col_red");
					bgarr.push("bg_red")
					textArr.push("杀对")
				} else {
					cla_col.push("col_blue");
					bgarr.push("col_blue")
					textArr.push("杀错")
				}
			} else {
				xaArr.push(arr[i])
			}
		}
		if(k.preDrawCode == "") {
			var fragmentHtml = "等待开奖";
		} else {
			var numCode = k.preDrawCode.split(",");
			var fragmentHtml = "<ul class='num_ul'>" +
				"<li class='cqssc_'>" + numCode[0] * 1 + "</li>" +
				"<li class='cqssc_'>" + numCode[1] * 1 + "</li>" +
				"<li class='cqssc_'>" + numCode[2] * 1 + "</li>" +
				"<li class='cqssc_'>" + numCode[3] * 1 + "</li>" +
				"<li class='cqssc_'>" + numCode[4] * 1 + "</li>" +
				"</ul>"
		}
		contentHtml += "<ul class='list-content'>" +
			"<li>" + k.preDrawIssue + "期</li>" +
			"<li>" + fragmentHtml + "</li>" +
			"<li class='" + cla_col[0] + "'>杀:" + xaArr[0] + "</li>" +
			"<li class='" + bgarr[0] + "'>" + textArr[0] + "</li>" +
			"<li class='" + cla_col[1] + "'>杀:" + xaArr[1] + "</li>" +
			"<li class='" + bgarr[1] + "'>" + textArr[1] + "</li>" +
			"<li class='" + cla_col[2] + "'>杀:" + xaArr[2] + "</li>" +
			"<li class='" + bgarr[2] + "'>" + textArr[2] + "</li>" +
			"<li class='" + cla_col[3] + "'>杀:" + xaArr[3] + "</li>" +
			"<li class='" + bgarr[3] + "'>" + textArr[3] + "</li>" +
			"<li class='" + cla_col[4] + "'>杀:" + xaArr[4] + "</li>" +
			"<li class='" + bgarr[4] + "'>" + textArr[4] + "</li></ul>"
	});
	var resarr = data.result.data[checkNum + "KillRight"];
	var resarr2 = data.result.data[checkNum + "Percent"];
	resHtml = "<ul class='list_footer remover_foot'>" +
		"<li>杀对次数</li>" +
		"<li>" + resarr[0] + "</li>" +
		"<li>" + resarr[1] + "</li>" +
		"<li>" + resarr[2] + "</li>" +
		"<li>" + resarr[3] + "</li>" +
		"<li>" + resarr[4] + "</li></ul>" +
		"<ul class='list_footer remover_foot'>" +
		"<li>成功概率</li>" +
		"<li>" + resarr2[0] + "</li>" +
		"<li>" + resarr2[1] + "</li>" +
		"<li>" + resarr2[2] + "</li>" +
		"<li>" + resarr2[3] + "</li>" +
		"<li>" + resarr2[4] + "</li></ul>";
	$(".list_box>.list-title").after(contentHtml);
	$(".list_footerTil:last").after(resHtml);
	$(".num_ul").css("width", "175px");
}

//快3 追号
// 追号请求函数 --快3
FunObj.zhuiHao_kuai_3 = function(lot_type) {
	var data = {
		lotCode: lotCode[lot_type],
		rows: rows,
		date: fundate
	}
	$.ajax({
		url: publicUrl + "LotteryPlan/getKsPlanList.do",
		type: "GET",
		data: data,
		success: function(data) {
			//执行数据请求
			var checkNum = $(".11check5_ran>.check_ran").attr("data-text");
			FunObj.zhuiHao_kuai_3_analysis(data, checkNum);
			ranData = data;
		},
		error: function(data) {
			console.log("快3购彩请求错误!");
		}
	});
}

//追号数据分析函数  --快3
FunObj.zhuiHao_kuai_3_analysis = function(data, plan, Timeout) {
	FunObj.LiText(); //修改下面li里的字
	if(typeof(data) == "string") {
		data = JSON.parse(data);
	}
	$(".ZHao_1 .xhuiHao_content").remove();
	if(data.result.data == "" || data.result.data == []) {
		$(".check_issue").text(0);
		return;
	}
	var claArr = ["", "ksone", "kstwo", "ksthree", "ksfour", "ksfive", "kssix"];
	if($("#select").css("display") == "none") {
		$(".check_issue").text(data.result.data.length);
	} else {
		$(".check_issue").text(data.result.data.length - 1);
	}
	var html = "",
		sum_html = "<ul class='sum_ul'>",
		plicpursueNum = "";
	$.each(data.result.data, function(i, p) {
		var preDrawCode = p.preDrawCode.split(",");
		var pursueNum = p["plan" + plan].split(",");
		if(i == 0) { //将最新计划号码添加至计划详情展示中
			plicpursueNum = pursueNum
		}
		var cla = "";
		if(p["profit" + plan].indexOf("-") == -1) {
			cla = "resultRed";
		} else {
			cla = "resultBlue";
		}
		if(pursueNum.indexOf(preDrawCode[0]) != -1) {
			var clas = "resultCheckLissc";
		} else {
			var clas = ""
		}
		if(preDrawCode.length == 1) {
			var html_text = "等待开奖";
			var Firnum = false;
		} else {
			var Firnum = preDrawCode[0] * 1 + preDrawCode[1] * 1 + preDrawCode[2] * 1;
			var html_text = "<ul class='opactionUl'>" +
				"<li class='opaction " + claArr[preDrawCode[0] * 1] + "'></li>" +
				"<li class='opaction " + claArr[preDrawCode[1] * 1] + "'></li>" +
				"<li class='opaction " + claArr[preDrawCode[2] * 1] + "'></li>" +
				"</ul>"
		}
		var num4 = pursueNum[3] != undefined ? "<li class='cqssc_'>" + pursueNum[3] * 1 + "<i class='" + (Firnum === pursueNum[3] * 1 ? 'resultCheckLissc' : '') + "'></i></li>" : "";
		var num5 = pursueNum[4] != undefined ? "<li class='cqssc_'>" + pursueNum[4] * 1 + "<i class='" + (Firnum === pursueNum[4] * 1 ? 'resultCheckLissc' : '') + "'></i></li>" : "";
		html += "<ul class='xhuiHao_content'>" +
			"<li>" + p.preDrawIssue + "期</li>" +
			"<li>" + html_text + "</li>" +
			"<li>" +
			"<ul class='zhui_ul'>" +
			"<li class='cqssc_'>" + pursueNum[0] * 1 + "<i class='" + (Firnum === pursueNum[0] * 1 ? 'resultCheckLissc' : '') + "'></i></li>" +
			"<li class='cqssc_'>" + pursueNum[1] * 1 + "<i class='" + (Firnum === pursueNum[1] * 1 ? 'resultCheckLissc' : '') + "'></i></li>" +
			"<li class='cqssc_'>" + pursueNum[2] * 1 + "<i class='" + (Firnum === pursueNum[2] * 1 ? 'resultCheckLissc' : '') + "'></i></li>" + num4 + num5 +
			"</ul>" +
			"</li>" +
			"<li>" + p["lotteryCost" + plan] + "</li>" +
			"<li>" + p["lotteryCostAll" + plan] + "</li>" +
			"<li class='" + cla + "'>" + p["profit" + plan] + "</li>" +
			"<li>" + p.preDrawTime + "</li>" +
			"</ul>"
	});
	$('.ZHao_1 .zhuiHao_title').after(html);
	$(".ZHao_1 .xhuiHao_content li ul:first-child").css("width", "183px");
	FunObj.isWidth(plan);
}

// 追号请求函数 --pc蛋蛋
FunObj.zhuiHao_eagxy = function(lot_type) {
	var data = {
		lotCode: lotCode[lot_type],
		rows: rows,
		date: fundate
	}

	$.ajax({
		url: publicUrl + "LotteryPlan/getPc28PlanList.do", // PursueNum/getKsPursueNumList.do
		type: "GET",
		data: data,
		success: function(data) {
			//执行数据请求
			FunObj.zhuiHao_eagxy_analysis(data);
			ranData = data;
		},
		error: function(data) {
			console.log("pc蛋蛋追号请求错误!");
		}
	});
}

//追号数据分析函数  --pc蛋蛋
FunObj.zhuiHao_eagxy_analysis = function(data, Timeout) {
	if(typeof(data) == "string") {
		data = JSON.parse(data);
	}
	FunObj.LiText(); //修改下面li里的字
	if(data.result.data == "" || data.result.data == []) {
		$(".check_issue").text(0);
		$(".ZHao_1 .xhuiHao_content").remove();
		return;
	}
	
	$(".ZHao_1 .xhuiHao_content").remove();
	if($("#select").css("display") == "none") {
		$(".check_issue").text(data.result.data.length);
	} else {
		$(".check_issue").text(data.result.data.length - 1);
	}
	var html = "",
		sum_html = "<ul class='sum_ul'>",
		plicpursueNum = "";
	$.each(data.result.data, function(i, p) {
		var preDrawCode = p.preDrawCode.split(",");
		var pursueNum = p.planC.split(",");
		if(i == 0) { //将最新计划号码添加至计划详情展示中
			plicpursueNum = pursueNum
		}
		var cla = "",
			clas = "";
		if(p.profitC.indexOf("-") == -1) {
			cla = "resultRed";
		} else {
			cla = "resultBlue";
		}
		if(preDrawCode.length == 1) {
			var html_text = "等待开奖";
			var Firnum = false;
		} else {
			var Firnum = preDrawCode[0] * 1 + preDrawCode[1] * 1 + preDrawCode[2] * 1;
			var html_text = "<ul class='opactionUl'>" +
				"<li class='opaction cqssc_ cqssc_rig'>" + preDrawCode[0] * 1 + "<span class='addf'></span></li>" +
				"<li class='opaction cqssc_ cqssc_rig'>" + preDrawCode[1] * 1 + "<span class='addf'></span></li>" +
				"<li class='opaction cqssc_ cqssc_rig'>" + preDrawCode[2] * 1 + "</li>" +
				"</ul>"
		}
		var num4 = pursueNum[3] != undefined ? "<li class='cqssc_'>" + pursueNum[3] * 1 + "<i class='" + (Firnum === pursueNum[3] * 1 ? 'resultCheckLissc' : '') + "'></i></li>" : "";
		var num5 = pursueNum[4] != undefined ? "<li class='cqssc_'>" + pursueNum[4] * 1 + "<i class='" + (Firnum === pursueNum[4] * 1 ? 'resultCheckLissc' : '') + "'></i></li>" : "";
		var num6 = pursueNum[5] != undefined ? "<li class='cqssc_'>" + pursueNum[5] * 1 + "<i class='" + (Firnum === pursueNum[5] * 1 ? 'resultCheckLissc' : '') + "'></i></li>" : "";
		html += "<ul class='xhuiHao_content'>" +
			"<li>" + p.preDrawIssue + "期</li>" +
			"<li>" + html_text + "</li>" +
			"<li>" +
			"<ul class='zhui_ul'>" +
			"<li class='cqssc_'>" + pursueNum[0] * 1 + "<i class='" + (Firnum === pursueNum[0] * 1 ? 'resultCheckLissc' : '') + "'></i></li>" +
			"<li class='cqssc_'>" + pursueNum[1] * 1 + "<i class='" + (Firnum === pursueNum[1] * 1 ? 'resultCheckLissc' : '') + "'></i></li>" +
			"<li class='cqssc_'>" + pursueNum[2] * 1 + "<i class='" + (Firnum === pursueNum[2] * 1 ? 'resultCheckLissc' : '') + "'></i></li>" + num4 + num5 + num6 +
			"</ul>" +
			"</li>" +
			"<li>" + p.lotteryCostC + "</li>" +
			"<li>" + p.lotteryCostAllC + "</li>" +
			"<li class='" + cla + "'>" + p.profitC + "</li>" +
			"<li>" + p.preDrawTime + "</li>" +
			"</ul>"
	});
	$('.ZHao_1 .zhuiHao_title').after(html);
	$(".ZHao_1 .xhuiHao_content li ul:first-child").css("width", "190px");
	FunObj.isWidth2("B");
}

//、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、快3 杀号 
FunObj.XaHao_kuai_3 = function(lot_type) {
	var data = {
		lotCode: lotCode[lot_type],
		rows: rows,
		date: fundate
	}
	$.ajax({
		url: publicUrl + "KillNum/getFtKillNumList.do",
		type: "GET",
		data: data,
		success: function(data) {
			//          if(typeof(data) == "string") {
			//              data = JSON.parse(data);
			//          }
			//          console.log(data)
			xaranData = data;
			//执行数据请求
			var checkNum = $(".kuai_3_ran>.check_ran").attr("data-text");
			FunObj.XaHao_kuai_3_analysis(data, checkNum);
		},
		error: function(data) {
			console.log("杀号请求错误!");
		}
	});
}

//.................................快3杀号的数据分析
FunObj.XaHao_kuai_3_analysis = function(data, checkNum) {
//	console.log(checkNum);
	$(".list-content").remove();
	$(".remover_foot").remove();
	if(typeof(data) == "string") {
		data = JSON.parse(data);
	}
	if(data.result.data == "") {
		return;
	}
//	console.log(data)
	var contentHtml = "",
		resHtml = "";
	$.each(data.result.data.list, function(j, k) {
		var cla_col = [],
			xaArr = [],
			textArr = [],
			bgarr = [];
		if(checkNum == "second") {
			var arr = k.killSumNum;
		} else {
			var arr = k.firstNum;
		}
		for(var i = 0; i < arr.length; i++) {
			if((i + 1) % 2 == 0) {
				if(arr[i] == 0) {
					cla_col.push("");
					bgarr.push("");
					textArr.push("-");
				} else if(arr[i] == 1) {
					cla_col.push("col_red");
					bgarr.push("bg_red");
					textArr.push("杀对");
				} else {
					cla_col.push("col_blue");
					bgarr.push("col_blue");
					textArr.push("杀错");
				}
			} else {
				xaArr.push(arr[i])
			}
		}
		if(k.preDrawCode == "") {
			var fragmentHtml = "等待开奖";
		} else {
			var numCode = k.preDrawCode.split(",");
			var fragmentHtml = "<ul class='num_ul'>" +
				"<li class='cqssc_'>" + numCode[0] * 1 + "</li>" +
				"<li class='cqssc_'>" + numCode[1] * 1 + "</li>" +
				"<li class='cqssc_'>" + numCode[2] * 1 + "</li>" +
				"</ul>"
		}
		if(k.sumNumber == undefined) {
			var sumNumber = "";
		} else if(k.sumNumber == "") {
			var sumNumber = "-";
		} else {
			var sumNumber = k.sumNumber;
		}
		contentHtml += "<ul class='list-content'>" +
			"<li>" + k.preDrawIssue + "期</li>" +
			"<li class='sumlili'>" + fragmentHtml + "</li>" +
			"<li style='width:88px' class='sumli_2'>" + sumNumber + "</li>" +
			"<li class='" + cla_col[0] + "'>杀:" + xaArr[0] + "</li>" +
			"<li class='" + bgarr[0] + "'>" + textArr[0] + "</li>" +
			"<li class='" + cla_col[1] + "'>杀:" + xaArr[1] + "</li>" +
			"<li class='" + bgarr[1] + "'>" + textArr[1] + "</li>" +
			"<li class='" + cla_col[2] + "'>杀:" + xaArr[2] + "</li>" +
			"<li class='" + bgarr[2] + "'>" + textArr[2] + "</li>" +
			"<li class='" + cla_col[3] + "'>杀:" + xaArr[3] + "</li>" +
			"<li class='" + bgarr[3] + "'>" + textArr[3] + "</li>" +
			"<li class='" + cla_col[4] + "'>杀:" + xaArr[4] + "</li>" +
			"<li class='" + bgarr[4] + "'>" + textArr[4] + "</li></ul>"
	});
	var resarr = data.result.data[checkNum + "KillRight"];
	var resarr2 = data.result.data[checkNum + "Percent"];
	resHtml = "<ul class='list_footer remover_foot'>" +
		"<li>杀对次数</li>" +
		"<li>" + resarr[0] + "</li>" +
		"<li>" + resarr[1] + "</li>" +
		"<li>" + resarr[2] + "</li>" +
		"<li>" + resarr[3] + "</li>" +
		"<li>" + resarr[4] + "</li></ul>" +
		"<ul class='list_footer remover_foot'>" +
		"<li>成功概率</li>" +
		"<li>" + resarr2[0] + "</li>" +
		"<li>" + resarr2[1] + "</li>" +
		"<li>" + resarr2[2] + "</li>" +
		"<li>" + resarr2[3] + "</li>" +
		"<li>" + resarr2[4] + "</li></ul>";
	$(".list_box>.list-title").after(contentHtml);
	$(".list_footerTil:last").after(resHtml);
	$(".num_ul").css("width", "105px");
	if(checkNum == "second") {
		FunObj.sumLiShow('show');
	} else {
		FunObj.sumLiShow('!show');

	}
}
FunObj.sumLiShow = function(show) {
	if(show == "show") {
		$("#sumLi").show();
		$(".sumlili").css("cssText", "width:300px !important");
		$(".sumli_2").show();
	} else {
		$("#sumLi").hide();
		$(".sumlili").css("cssText", "width:388px !important");
		$(".sumli_2").hide();
	}
}

//期号改变发出请求函数
$("#select").change(function() {
	rows = $(this).val();
	//  $(".check_issue").text(rows);
	clearTimeout(timeOutInit);
	FunObj.orzhuihao(lot_type);
})

FunObj.orzhuihao = function(lot_type) {
	Object.screenWidthFun(screenWidth);
	changeLogo(lot_type); //改变不同彩种系列的开奖logo和彩种名
	clearTimeout(timeOutInit);
//	console.log(lot_type);
	FunObj.getTimecut(lot_type); //追加推荐计划倒计时接口数据请求
	if(!FunObj.zhuiOrXaHao() && (FunObj.Iseagxy(lot_type) || FunObj.Isklsf(lot_type) || lot_type == "xingyft")) {//PC蛋蛋，快乐十分，幸运飞艇 没有稳赢杀号
		$("#fugaiView").show();
	} else {
		$("#fugaiView").hide();
	}
	if(FunObj.zhuiOrXaHao() && FunObj.IsPk10(lot_type)) { //如果是追号计划  --pk10
		FunObj.zhuiHao_pk10(lot_type);
		FunObj.zhuantiStyle_pk10(lot_type); //pk10样式切换
	} else if(!FunObj.zhuiOrXaHao() && FunObj.IsPk10(lot_type)) { //如果是杀号计划  --pk10
		FunObj.XaHao_pk10(lot_type);
		FunObj.zhuantiStyle_pk10(lot_type); //pk10样式切换
		$(".list-title").find("li.pk10_name").show().siblings("li.nameUl:not('.pk10_name')").hide();
		$(".list_box").find("ul.pk10_name").show().siblings("ul.nameUl").hide();
	} else if(FunObj.zhuiOrXaHao() && FunObj.IsXanwu(lot_type)) { //如果是追号计划  --重庆时时彩
		FunObj.zhuiHao_cqssc(lot_type);
		FunObj.zhuantiStyle_cqssc(lot_type) //时时彩样式切换
	} else if(!FunObj.zhuiOrXaHao() && FunObj.IsXanwu(lot_type)) { //如果是杀号计划  --重庆时时彩
		FunObj.XaHao_cqssc(lot_type);
		FunObj.zhuantiStyle_cqssc(lot_type) //时时彩样式切换
		$(".list-title").find("li.ssc_name").show().siblings("li.nameUl:not('.ssc_name')").hide();
		$(".list_box").find("ul.ssc_name").show().siblings("ul.nameUl").hide();
	} else if(FunObj.zhuiOrXaHao() && FunObj.IsXahao11check5(lot_type)) { // 如果是追号计划  --11选5 
//		debugger
		FunObj.zhuiHao_11check5(lot_type);
		FunObj.zhuantiStyle_syx5(lot_type) //十一选五样式切换
	} else if(!FunObj.zhuiOrXaHao() && FunObj.IsXahao11check5(lot_type)) { //如果是杀号计划  --11选5
		FunObj.XaHao_11check5(lot_type);
		FunObj.zhuantiStyle_syx5(lot_type) //十一选五样式切换
		$(".list-title").find("li.check5_name").show().siblings("li.nameUl:not('.check5_name')").hide();
		$(".list_box").find("ul.check5_name").show().siblings("ul.nameUl").hide();
	} else if(FunObj.zhuiOrXaHao() && FunObj.Iskuai_3(lot_type)) { //如果是追号计划 --快3
		FunObj.zhuiHao_kuai_3(lot_type);
		FunObj.zhuantiStyle_kuai3(lot_type) //快3样式切换
	} else if(!FunObj.zhuiOrXaHao() && FunObj.Iskuai_3(lot_type)) { //如果是杀号计划 --快3
		FunObj.XaHao_kuai_3(lot_type);
		FunObj.zhuantiStyle_kuai3(lot_type); //快3样式切换
		$(".list-title").find("li.kai3_name").show().siblings("li.nameUl:not('.kai3_name')").hide();
		$(".list_box").find("ul.kai3_name").show().siblings("ul.nameUl").hide();
	} else if(FunObj.zhuiOrXaHao() && FunObj.Iseagxy(lot_type)) { //如果是追号计划 --pc蛋蛋幸运28
		FunObj.zhuiHao_eagxy(lot_type);
		FunObj.zhuantiStyle_egxy28(lot_type) //pc蛋蛋幸运28样式切换
	} else if(!FunObj.zhuiOrXaHao() && FunObj.Iseagxy(lot_type)) { //如果是杀号计划 --pc蛋蛋幸运28
		FunObj.zhuantiStyle_egxy28(lot_type) //pc蛋蛋幸运28样式切换
		$(".list-title").find("li.other_name").show().siblings("li.nameUl:not('.other_name')").hide();
		$(".list_box").find("ul.other_name").show().siblings("ul.nameUl").hide();
	} else if(FunObj.zhuiOrXaHao() && FunObj.Isklsf(lot_type)) { //如果是追号计划 --快乐十分
		FunObj.zhuiHao_klsf(lot_type);
		FunObj.zhuantiStyle_gdklsf(lot_type) //快乐十分样式切换
	} else if(!FunObj.zhuiOrXaHao() && FunObj.Isklsf(lot_type)) { //如果是杀号计划 --快乐十分
		FunObj.zhuantiStyle_gdklsf(lot_type) //快乐十分样式切换
		$(".list-title").find("li.other_name").show().siblings("li.nameUl:not('.other_name')").hide();
		$(".list_box").find("ul.other_name").show().siblings("ul.nameUl").hide();
	}
	if(FunObj.zhuiOrXaHao() && FunObj.Iseagxy(lot_type)) {
		$(".ran").hide()
	} else {
		$(".ran").show()
	}
	var checksecound = $(".kuai_3_ran>.check_ran").attr("date-text");
	if(!FunObj.zhuiOrXaHao() && FunObj.Iskuai_3(lot_type) && (checksecound == "second")) { //如果是杀号快3 的和值杀号 就将和值栏显示与调整li 宽度
		FunObj.sumLiShow("show")
	} else {
		FunObj.sumLiShow("!show")
	}
	FunObj.introduction($("#caiName").text());
}

//判断是追号或杀号
FunObj.zhuiOrXaHao = function() {
	if($(".ch-left>a.check_a").hasClass("zhao_a")) {
		return true;
	} else {
		return false;
	}
};
//判断是否是pk10 类型的li
FunObj.IsPk10 = function(lot_type) {
	if(lot_type == "pk10" || lot_type == "aozxy10" || lot_type == "jisusc" || lot_type == "xingyft") {
		return true;
	}
};
//判断是否是ssc 类型的li
FunObj.IsXanwu = function(showElem) {
	if(showElem == "cqssc" || showElem == "tjssc" || showElem == "xjssc" || showElem == "aozxy5" || showElem == "jisussc") {
		return true;
	}
};
//判断是否是11选5 类型的li
FunObj.IsXahao11check5 = function(showElem) {
	if(showElem == "jisuef" || showElem == "cqef" || showElem == "gdsyxw" || showElem == "sdsyydj" || showElem == "jxef" || showElem == "jsef" || showElem == "ahef" || showElem == "shef" || showElem == "lnef" || showElem == "hbef" || showElem == "gxef" || showElem == "jlef" || showElem == "nmgef" || showElem == "zjef") {
		return true;
	}
};
////判断是否是 快3  类型的li
FunObj.Iskuai_3 = function(showElem) {
	if(showElem == "jisuksan" || showElem == "jsksan" || showElem == "gxft" || showElem == "jlft" || showElem == "hebft" || showElem == "nmgft" || showElem == "ahft" || showElem == "fjft" || showElem == "hubft" || showElem == "bjft") {
		return true;
	}
}
FunObj.Iseagxy = function(showElem) {
	if(showElem == "egxy28") {
		return true;
	}
}
FunObj.Isklsf = function(showElem) {
	if(showElem == "jisuklsf" || showElem == "gdklsf" || showElem == "aozxy8" || showElem == "tjklsf" || showElem == "cqxync") {
		return true;
	}
}
//判断三个li选项的隐藏与显示
FunObj.isRan = function(showElem) {
	if($(".check_a").hasClass("zhao_a")) { //追号11选5  && FunObj.IsXahao11check5(showElem)
		$(".ran").show().find('.11check5_ran').show().siblings().hide();
		FunObj.LiText(); //修改下面li里的字
	} else if(!$(".check_a").hasClass("zhao_a") && FunObj.IsPk10(showElem)) { //杀号pk10
		$(".ran").show().find('.pk10_ran').show().siblings().hide();
	} else if(!$(".check_a").hasClass("zhao_a") && (FunObj.IsXahao11check5(showElem) || FunObj.IsXanwu(showElem))) { //杀号重庆时时彩与11选5
		$(".ran").show().find('.cqssc_ran').show().siblings().hide();
	} else if(!$(".check_a").hasClass("zhao_a") && FunObj.Iskuai_3(showElem)) { //杀号 快3
		$(".ran").show().find('.kuai_3_ran').show().siblings().hide();
	} else {
		$(".ran").hide();
	}
}
//功能介绍功能
FunObj.introduction = function(checkType_text) {
	if($(".check_a").hasClass("zhao_a")) { //追号
		$(".zhuihaoCla").show().siblings(".zshao_text").hide();
	} else { //杀号
		$(".sahaoCla").show().siblings(".zshao_text").hide();
	}
	var checkText = "";
	if($(".ran").css("display") != "none") {
		if($(".11check5_ran").css("display") == "block") {
			checkText = $(".11check5_ran>.check_ran").text();
		} else if($(".pk10_ran").css("display") == "block") {
			checkText = $(".pk10_ran>.check_ran").text();
		} else if($(".cqssc_ran").css("display") == "block") {
			checkText = $(".cqssc_ran>.check_ran").text();
		}
	}

	if(checkType_text.indexOf("快3") != -1) {
		$(".powP").html(checkType_text + "----三粒骰子平面点数之和值购彩计划：依据江苏快3历史大数据规律为基础，提供N组购彩计划参考，建议使用阶梯倍投方法投注")
	} else {
		$(".powP").html(powText);
	}
	$(".cp_name").text(checkType_text);
	$(".cp_check").text(checkText);
}

$(".ran").on("click", "ul>li", function() {
	FunObj.introduction($("li[data-text=" + lot_type + "]").text());
})

$(".11check5_ran").on("click", "li", function() { //购彩计划 pk10 ssc 分析fun 
	checkNum = $(this).attr("data-text");
	FunObj.LiText(); //修改下面li里的字    
	if(FunObj.IsPk10(lot_type)) {
		FunObj.zhuiHao_pk10_analysis(ranData, checkNum, true);
	} else if(FunObj.IsXanwu(lot_type)) {
		FunObj.zhuiHao_cqssc_analysis(ranData, checkNum, true);
	} else if(FunObj.IsXahao11check5(lot_type)) {
		FunObj.zhuiHao_11check5_analysis(ranData, checkNum, true)
	} else if(FunObj.Iskuai_3(lot_type)) {
		FunObj.zhuiHao_kuai_3_analysis(ranData, checkNum, true)
	} else if(FunObj.Isklsf(lot_type)) {
		FunObj.zhuiHao_klsf_analysis(ranData, checkNum, true)
	}
	FunObj.timecutData(timeDate, checkNum);
})

$(".pk10_ran").on("click", "li", function() { //杀号pk10 分析fun
	var checkli = $(this).attr("data-text");
	FunObj.XaHao_px10_analysis(xaranData, checkli);
});

$(".cqssc_ran").on("click", "li", function() { //杀号 ccs 11选5 分析fun
	var checkli = $(this).attr("data-text");
	FunObj.XaHao_cqssc_analysis(xaranData, checkli);
});

$(".kuai_3_ran").on("click", "li", function(e) {
	e.preventDefault();
	var checkNum = $(this).attr("data-text");
	FunObj.XaHao_kuai_3_analysis(xaranData, checkNum);
});

//改变彩种logo以及推荐开奖提示信息
changeLogo = function(showElem) {
	$("#hint").empty();
	var tsContent = "";
	if(showElem == "pk10") {
		$("#logoImage").attr("src", "../../img/zshImage/PK10/PK10.png?v=20187201945");
		$("#caiName").text("北京PK10");
		FunObj.changeThisTitle("北京PK10")
		tsContent = "<span><i class='tips'>提示说明：</i>每天推荐计划时间段，</span><span>时间段一<i>10:55:00-15:00:00</i></span><span>时间段二<i>17:55:00-23:50:00</i></span>"
	} else if(showElem == "jisusc") {
		$("#logoImage").attr("src", "../../img/zshImage/PK10/jisusc.png?v=20187201945");
		$("#caiName").text("极速赛车");
		FunObj.changeThisTitle("极速赛车")
		tsContent = "<span><i class='tips'>提示说明：</i>每天推荐计划时间段，</span><span>时间段一<i>10:55:00-15:00:02</i></span><span>时间段二<i>17:55:00-23:50:01</i></span><span>时间段三<i>01:00:00-03:30:00</i></span>"
	} else if(showElem == "aozxy10") {
		$("#logoImage").attr("src", "../../img/zshImage/PK10/azxy10.png?v=20187201945");
		$("#caiName").text("澳洲幸运10");
		FunObj.changeThisTitle("澳洲幸运10")
		tsContent = "<span><i class='tips'>提示说明：</i>每天推荐计划时间段，</span><span>时间段一<i>10:55:00-15:00:03</i></span><span>时间段二<i>17:55:00-23:50:02</i></span>"
	} else if(showElem == "xingyft") {
		$("#logoImage").attr("src", "../../img/zshImage/PK10/xyft.png?v=20187201945");
		$("#caiName").text("极速飞艇");
		FunObj.changeThisTitle("极速飞艇")
		tsContent = "<span><i class='tips'>提示说明：</i>每天推荐计划时间段，</span><span>时间段一<i>10:55:00-15:00:03</i></span><span>时间段二<i>17:55:00-23:50:02</i></span><span>时间段三<i>01:00:00-03:30:01</i></span>"

	} else if(showElem == "cqssc") {
		$("#logoImage").attr("src", "../../img/zshImage/ssczsh/cqssc.png?v=20187201945");
		$("#caiName").text("重庆时时彩");
		FunObj.changeThisTitle("重庆时时彩")
		tsContent = "<span><i class='tips'>提示说明：</i>每天推荐计划时间段，</span><span>时间段一<i>10:55:00-15:00:06</i></span><span>时间段二<i>17:55:00-23:50:00</i></span>"
	} else if(showElem == "tjssc") {
		$("#logoImage").attr("src", "../../img/zshImage/ssczsh/tjssc.png?v=20187201945");
		$("#caiName").text("天津时时彩");
		FunObj.changeThisTitle("天津时时彩")
		tsContent = "<span><i class='tips'>提示说明：</i>每天推荐计划时间段，</span><span>时间段一<i>10:55:00-15:00:07</i></span><span>时间段二<i>17:55:00-23:00:00</i></span>"
	} else if(showElem == "xjssc") {
		$("#logoImage").attr("src", "../../img/zshImage/ssczsh/xjssc.png?v=20187201945");
		$("#caiName").text("新疆时时彩");
		FunObj.changeThisTitle("新疆时时彩")
		tsContent = "<span><i class='tips'>提示说明：</i>每天推荐计划时间段，</span><span>时间段一<i>10:55:00-15:00:08</i></span><span>时间段二<i>17:55:00-23:50:00</i></span>"
	} else if(showElem == "jisussc") {
		$("#logoImage").attr("src", "../../img/zshImage/ssczsh/jisussc.png?v=20187201945");
		$("#caiName").text("极速时时彩");
		FunObj.changeThisTitle("极速时时彩")
		tsContent = "<span><i class='tips'>提示说明：</i>每天推荐计划时间段，</span><span>时间段一<i>10:55:00-15:00:10</i></span><span>时间段二<i>17:55:00-23:50:00</i></span><span>时间段三<i>01:00:00-03:30:00</i></span>"
	} else if(showElem == "aozxy5") {
		$("#logoImage").attr("src", "../../img/zshImage/ssczsh/aozxy5.png?v=20187201945");
		$("#caiName").text("澳洲幸运5");
		FunObj.changeThisTitle("澳洲幸运5")
		tsContent = "<span><i class='tips'>提示说明：</i>每天推荐计划时间段，</span><span>时间段一<i>10:55:00-15:00:09</i></span><span>时间段二<i>17:55:00-23:50:00</i></span><span>时间段三<i>01:00:00-03:30:00</i></span>"

	} else if(showElem == "jsksan") {
		$("#logoImage").attr("src", "../../img/zshImage/kuai3zsh/jsksan.png?v=20187201945");
		$("#caiName").text("江苏快3");
		FunObj.changeThisTitle("江苏快3")
		tsContent = "<span><i class='tips'>提示说明：</i>每天推荐计划时间段，</span><span>时间段一<i>10:50:00-15:00:00</i></span><span>时间段二<i>17:50:00-22:00:00</i></span>"
	} else if(showElem == "gxft") {
		$("#logoImage").attr("src", "../../img/zshImage/kuai3zsh/gxft.png?v=20187201945");
		$("#caiName").text("广西快3");
		FunObj.changeThisTitle("广西快3")
		tsContent = "<span><i class='tips'>提示说明：</i>每天推荐计划时间段，</span><span>时间段一<i>10:50:00-15:00:00</i></span><span>时间段二<i>17:50:00-22:30:00</i></span>"
	} else if(showElem == "jlft") {
		$("#logoImage").attr("src", "../../img/zshImage/kuai3zsh/jlft.png?v=20187201945");
		$("#caiName").text("吉林快3");
		FunObj.changeThisTitle("吉林快3")
		tsContent = "<span><i class='tips'>提示说明：</i>每天推荐计划时间段，</span><span>时间段一<i>10:50:00-15:00:00</i></span><span>时间段二<i>17:50:00-21:30:00</i></span>"
	} else if(showElem == "hebft") {
		$("#logoImage").attr("src", "../../img/zshImage/kuai3zsh/hebft.png?v=20187201945");
		$("#caiName").text("河北快3");
		FunObj.changeThisTitle("河北快3")
		tsContent = "<span><i class='tips'>提示说明：</i>每天推荐计划时间段，</span><span>时间段一<i>10:50:00-15:00:00</i></span><span>时间段二<i>17:50:00-22:00:00</i></span>"
	} else if(showElem == "nmgft") {
		$("#logoImage").attr("src", "../../img/zshImage/kuai3zsh/nmgft.png?v=20187201945");
		$("#caiName").text("内蒙古快3");
		FunObj.changeThisTitle("内蒙古快3")
		tsContent = "<span><i class='tips'>提示说明：</i>每天推荐计划时间段，</span><span>时间段一<i>10:50:00-15:00:00</i></span><span>时间段二<i>17:50:00-22:00:00</i></span>"
	} else if(showElem == "ahft") {
		$("#logoImage").attr("src", "../../img/zshImage/kuai3zsh/ahft.png?v=20187201945");
		$("#caiName").text("安徽快3");
		FunObj.changeThisTitle("安徽快3")
		tsContent = "<span><i class='tips'>提示说明：</i>每天推荐计划时间段，</span><span>时间段一<i>10:50:00-15:00:00</i></span><span>时间段二<i>17:50:00-22:00:00</i></span>"
	} else if(showElem == "fjft") {
		$("#logoImage").attr("src", "../../img/zshImage/kuai3zsh/fjft.png?v=20187201945");
		$("#caiName").text("福建快3");
		FunObj.changeThisTitle("福建快3")
		tsContent = "<span><i class='tips'>提示说明：</i>每天推荐计划时间段，</span><span>时间段一<i>10:50:00-15:00:00</i></span><span>时间段二<i>17:50:00-22:00:00</i></span>"
	} else if(showElem == "hubft") {
		$("#logoImage").attr("src", "../../img/zshImage/kuai3zsh/hubft.png?v=20187201945");
		$("#caiName").text("湖北快3");
		FunObj.changeThisTitle("湖北快3")
		tsContent = "<span><i class='tips'>提示说明：</i>每天推荐计划时间段，</span><span>时间段一<i>10:50:00-15:00:00</i></span><span>时间段二<i>17:50:00-22:00:00</i></span>"
	} else if(showElem == "bjft") {
		$("#logoImage").attr("src", "../../img/zshImage/kuai3zsh/bjft.png?v=20187201945");
		$("#caiName").text("北京快3");
		FunObj.changeThisTitle("北京快3")
		tsContent = "<span><i class='tips'>提示说明：</i>每天推荐计划时间段，</span><span>时间段一<i>10:50:00-15:00:00</i></span><span>时间段二<i>17:50:00-23:50:00</i></span>"
	} else if(showElem == "gdsyxw") {
		$("#logoImage").attr("src", "../../img/zshImage/shiyix5/gdsyxw.png?v=20187201945");
		$("#caiName").text("广东11选5");
		FunObj.changeThisTitle("广东11选5")
		tsContent = "<span><i class='tips'>提示说明：</i>每天推荐计划时间段，</span><span>时间段一<i>10:50:00-15:00:00</i></span><span>时间段二<i>17:50:00-23:00:00</i></span>"
	} else if(showElem == "zjef") {
		$("#logoImage").attr("src", "../../img/zshImage/shiyix5/zjef.png?v=20187201945");
		$("#caiName").text("浙江11选5");
		FunObj.changeThisTitle("浙江11选5")
		tsContent = "<span><i class='tips'>提示说明：</i>每天推荐计划时间段，</span><span>时间段一<i>10:50:00-15:00:00</i></span><span>时间段二<i>17:50:00-22:30:00</i></span>"
	} else if(showElem == "jxef") {
		$("#logoImage").attr("src", "../../img/zshImage/shiyix5/jxef.png?v=20187201945");
		$("#caiName").text("江西11选5");
		FunObj.changeThisTitle("江西11选5")
		tsContent = "<span><i class='tips'>提示说明：</i>每天推荐计划时间段，</span><span>时间段一<i>10:50:00-15:00:00</i></span><span>时间段二<i>17:50:00-23:00:00</i></span>"
	} else if(showElem == "jsef") {
		$("#logoImage").attr("src", "../../img/zshImage/shiyix5/jsef.png?v=20187201945");
		$("#caiName").text("江苏11选5");
		FunObj.changeThisTitle("江苏11选5")
		tsContent = "<span><i class='tips'>提示说明：</i>每天推荐计划时间段，</span><span>时间段一<i>10:50:00-15:00:00</i></span><span>时间段二<i>17:50:00-22:00:00</i></span>"
	} else if(showElem == "ahef") {
		$("#logoImage").attr("src", "../../img/zshImage/shiyix5/ahef.png?v=20187201945");
		$("#caiName").text("安徽11选5");
		FunObj.changeThisTitle("安徽11选5")
		tsContent = "<span><i class='tips'>提示说明：</i>每天推荐计划时间段，</span><span>时间段一<i>10:50:00-15:00:00</i></span><span>时间段二<i>17:50:00-22:00:00</i></span>"
	} else if(showElem == "shef") {
		$("#logoImage").attr("src", "../../img/zshImage/shiyix5/shef.png?v=20187201945");
		$("#caiName").text("上海11选5");
		FunObj.changeThisTitle("上海11选5")
		tsContent = "<span><i class='tips'>提示说明：</i>每天推荐计划时间段，</span><span>时间段一<i>10:50:00-15:00:00</i></span><span>时间段二<i>17:50:00-23:50:00</i></span>"
	} else if(showElem == "lnef") {
		$("#logoImage").attr("src", "../../img/zshImage/shiyix5/lnef.png?v=20187201945");
		$("#caiName").text("辽宁11选5");
		FunObj.changeThisTitle("辽宁11选5")
		tsContent = "<span><i class='tips'>提示说明：</i>每天推荐计划时间段，</span><span>时间段一<i>10:50:00-15:00:00</i></span><span>时间段二<i>17:50:00-22:30:00</i></span>"
	} else if(showElem == "hbef") {
		$("#logoImage").attr("src", "../../img/zshImage/shiyix5/hbef.png?v=20187201945");
		$("#caiName").text("湖北11选5");
		FunObj.changeThisTitle("湖北11选5")
		tsContent = "<span><i class='tips'>提示说明：</i>每天推荐计划时间段，</span><span>时间段一<i>10:50:00-15:00:00</i></span><span>时间段二<i>17:50:00-22:00:00</i></span>"
	} else if(showElem == "gxef") {
		$("#logoImage").attr("src", "../../img/zshImage/shiyix5/gxef.png?v=20187201945");
		$("#caiName").text("广西11选5");
		FunObj.changeThisTitle("广西11选5")
		tsContent = "<span><i class='tips'>提示说明：</i>每天推荐计划时间段，</span><span>时间段一<i>10:50:00-15:00:00</i></span><span>时间段二<i>17:50:00-23:50:00</i></span>"
	} else if(showElem == "jlef") {
		$("#logoImage").attr("src", "../../img/zshImage/shiyix5/jlef.png?v=20187201945");
		$("#caiName").text("吉林11选5");
		FunObj.changeThisTitle("吉林11选5")
		tsContent = "<span><i class='tips'>提示说明：</i>每天推荐计划时间段，</span><span>时间段一<i>10:50:00-15:00:00</i></span><span>时间段二<i>17:50:00-21:30:00</i></span>"
	} else if(showElem == "nmgef") {
		$("#logoImage").attr("src", "../../img/zshImage/shiyix5/nmgef.png?v=20187201945");
		$("#caiName").text("内蒙古11选5");
		FunObj.changeThisTitle("内蒙古11选5")
		tsContent = "<span><i class='tips'>提示说明：</i>每天推荐计划时间段，</span><span>时间段一<i>10:50:00-15:00:00</i></span><span>时间段二<i>17:50:00-23:00:00</i></span>"
	} else if(showElem == "sdsyydj") {
		$("#logoImage").attr("src", "../../img/zshImage/shiyix5/sdsyydj.png?v=20187201945");
		$("#caiName").text("十一运夺金");
		FunObj.changeThisTitle("十一运夺金")
		tsContent = "<span><i class='tips'>提示说明：</i>每天推荐计划时间段，</span><span>时间段一<i>10:50:00-15:00:00</i></span><span>时间段二<i>17:50:00-23:00:00</i></span>"
	} else if(showElem == "egxy28") {
		$("#logoImage").attr("src", "../../img/zshImage/pcEgg/egxy28.png?v=20187201945");
		$("#caiName").text("PC蛋蛋幸运28");
		FunObj.changeThisTitle("PC蛋蛋幸运28")
		tsContent = "<span><i class='tips'>提示说明：</i>每天推荐计划时间段，</span><span>时间段一<i>10:50:00-15:00:00</i></span><span>时间段二<i>17:50:00-23:50:00</i></span>"
	} else if(showElem == "gdklsf") {
		$("#logoImage").attr("src", "../../img/zshImage/gdklsf/gdklsf.png?v=20187201945");
		$("#caiName").text("广东快乐十分");
		FunObj.changeThisTitle("广东快乐十分")
		tsContent = "<span><i class='tips'>提示说明：</i>每天推荐计划时间段，</span><span>时间段一<i>10:50:00-15:00:13</i></span><span>时间段二<i>17:50:00-23:00:00</i></span>"
	} else if(showElem == "tjklsf") {
		$("#logoImage").attr("src", "../../img/zshImage/gdklsf/tjklsf.png?v=20187201945");
		$("#caiName").text("天津快乐十分");
		FunObj.changeThisTitle("天津快乐十分")
		tsContent = "<span><i class='tips'>提示说明：</i>每天推荐计划时间段，</span><span>时间段一<i>10:50:00-15:00:13</i></span><span>时间段二<i>17:50:00-23:00:00</i></span>"
	} else if(showElem == "aozxy8") {
		$("#logoImage").attr("src", "../../img/zshImage/gdklsf/aozxy8.png?v=20187201945");
		$("#caiName").text("澳洲幸运8");
		FunObj.changeThisTitle("澳洲幸运8")
		tsContent = "<span><i class='tips'>提示说明：</i>每天推荐计划时间段，</span><span>时间段一<i>10:50:00-15:00:13</i></span><span>时间段二<i>17:50:00-23:50:00</i></span>"
	} else if(showElem == "cqxync") {
		$("#logoImage").attr("src", "../../img/zshImage/gdklsf/cqxync.png?v=20187201945");
		$("#caiName").text("重庆幸运农场");
		FunObj.changeThisTitle("重庆幸运农场")
		tsContent = "<span><i class='tips'>提示说明：</i>每天推荐计划时间段，</span><span>时间段一<i>10:50:00-15:00:13</i></span><span>时间段二<i>17:50:00-23:50:00</i></span>"
	}
	$("#hint").append(tsContent);
}

//点击更多计划，对应head中元素触发click事件
TouchEvent = function() {
	//点击极速赛车，使head页面中的同元素触发
	$("#pk10").on("click", function() { //PK10系列
		$("#headdivbox").find(".pk10Bg").click();
		$(this).siblings().removeClass();
		$(this).addClass("pk10Yellow");
	})
	$("#jisusc").on("click", function() {
		$("#headdivbox").find(".jisuscBg").click();
		$(this).siblings().removeClass();
		$(this).addClass("jsscYellow");
	})
	$("#aozxy10").on("click", function() {
		$("#headdivbox").find(".aozxy10Bg").click();
		$(this).siblings().removeClass();
		$(this).addClass("azxyYellow");
	})
	$("#xingyft").on("click", function() {
		$("#headdivbox").find(".jsftBg").click();
		$(this).siblings().removeClass();
		$(this).addClass("xyftYellow");
	})
	$("#cqssc").on("click", function() { //时时彩系列
		$(this).addClass("acNameColor").siblings().removeClass("acNameColor");
		$("#headdivbox").find(".cqsscNav").click();
	})
	$("#tjssc").on("click", function() {
		$(this).addClass("acNameColor").siblings().removeClass("acNameColor");
		$("#headdivbox").find(".tjsscNav").click();
	})
	$("#xjssc").on("click", function() {
		$(this).addClass("acNameColor").siblings().removeClass("acNameColor");
		$("#headdivbox").find(".xjsscNav").click();
	})
	$("#jisussc").on("click", function() {
		$(this).addClass("acNameColor").siblings().removeClass("acNameColor");
		$("#headdivbox").find(".jisusscNav").click();
	})
	$("#aozxy5").on("click", function() {
		$(this).addClass("acNameColor").siblings().removeClass("acNameColor");
		$("#headdivbox").find(".aozxy5Nav").click();
	})
	$("#jsksan").on("click", function() { //快3系列
		$(this).addClass("acNameColorKuai3").siblings().removeClass("acNameColorKuai3");
		$("#headdivbox").find(".jsksanNav").click();
	})
	$("#gxft").on("click", function() {
		$(this).addClass("acNameColorKuai3").siblings().removeClass("acNameColorKuai3");
		$("#headdivbox").find(".gxftNav").click();
	})
	$("#jlft").on("click", function() {
		$(this).addClass("acNameColorKuai3").siblings().removeClass("acNameColorKuai3");
		$("#headdivbox").find(".jlftNav").click();
	})
	$("#hebft").on("click", function() {
		$(this).addClass("acNameColorKuai3").siblings().removeClass("acNameColorKuai3");
		$("#headdivbox").find(".hebftNav").click();
	})
	$("#nmgft").on("click", function() {
		$(this).addClass("acNameColorKuai3").siblings().removeClass("acNameColorKuai3");
		$("#headdivbox").find(".nmgftNav").click();
	})
	$("#ahft").on("click", function() {
		$(this).addClass("acNameColorKuai3").siblings().removeClass("acNameColorKuai3");
		$("#headdivbox").find(".ahftNav").click();
	})
	$("#fjft").on("click", function() {
		$(this).addClass("acNameColorKuai3").siblings().removeClass("acNameColorKuai3");
		$("#headdivbox").find(".fjftNav").click();
	})
	$("#hubft").on("click", function() {
		$(this).addClass("acNameColorKuai3").siblings().removeClass("acNameColorKuai3");
		$("#headdivbox").find(".hubftNav").click();
	})
	$("#bjft").on("click", function() {
		$(this).addClass("acNameColorKuai3").siblings().removeClass("acNameColorKuai3");
		$("#headdivbox").find(".bjftNav").click();
	})
	$("#jisuksan").on("click", function() { //极速快3
		$(this).addClass("acNameColorKuai3").siblings().removeClass("acNameColorKuai3");
		$("#headdivbox").find(".jisukuai3Nav").click();
	})
	$("#gdsyxw").on("click", function() { //11选5系列
		$(this).addClass("acNameColorSYX5").siblings().removeClass("acNameColorSYX5");
		$("#headdivbox").find(".gdsyxwNav").click();
	})
	$("#zjef").on("click", function() {
		$(this).addClass("acNameColorSYX5").siblings().removeClass("acNameColorSYX5");
		$("#headdivbox").find(".zjefNav").click();
	})
	$("#jxef").on("click", function() {
		$(this).addClass("acNameColorSYX5").siblings().removeClass("acNameColorSYX5");
		$("#headdivbox").find(".jxefNav").click();
	})
	$("#jsef").on("click", function() {
		$(this).addClass("acNameColorSYX5").siblings().removeClass("acNameColorSYX5");
		$("#headdivbox").find(".jsefNav").click();
	})
	$("#ahef").on("click", function() {
		$(this).addClass("acNameColorSYX5").siblings().removeClass("acNameColorSYX5");
		$("#headdivbox").find(".ahefNav").click();
	})
	$("#shef").on("click", function() {
		$(this).addClass("acNameColorSYX5").siblings().removeClass("acNameColorSYX5");
		$("#headdivbox").find(".shefNav").click();
	})
	$("#lnef").on("click", function() {
		$(this).addClass("acNameColorSYX5").siblings().removeClass("acNameColorSYX5");
		$("#headdivbox").find(".lnefNav").click();
	})
	$("#hbef").on("click", function() {
		$(this).addClass("acNameColorSYX5").siblings().removeClass("acNameColor");
		$("#headdivbox").find(".hbefNav").click();
	})
	$("#gxef").on("click", function() {
		$(this).addClass("acNameColorSYX5").siblings().removeClass("acNameColorSYX5");
		$("#headdivbox").find(".gxefNav").click();
	})
	$("#jlef").on("click", function() {
		$(this).addClass("acNameColorSYX5").siblings().removeClass("acNameColorSYX5");
		$("#headdivbox").find(".jlefNav").click();
	})
	$("#nmgef").on("click", function() {
		$(this).addClass("acNameColorSYX5").siblings().removeClass("acNameColorSYX5");
		$("#headdivbox").find(".nmgefNav").click();
	})
	$("#sdsyydj").on("click", function() {
		$(this).addClass("acNameColorSYX5").siblings().removeClass("acNameColorSYX5");
		$("#headdivbox").find(".sdsyydjNav").click();
	})
	$("#jisusyxw").on("click", function() { //极速11选5
		$(this).addClass("acNameColorSYX5").siblings().removeClass("acNameColorSYX5");
//		debugger
		$("#headdivbox").find(".jisusyxwNav").click();
	})
	$("#gdklsf").on("click", function() { //快乐十分系列
		$(this).addClass("acNameColor").siblings().removeClass("acNameColor");
		$("#headdivbox").find(".gdklsfNav").click();
	})
	$("#tjklsf").on("click", function() {
		$(this).addClass("acNameColor").siblings().removeClass("acNameColor");
		$("#headdivbox").find(".tjklsfNav").click();
	})
	$("#aozxy8").on("click", function() {
		$(this).addClass("acNameColor").siblings().removeClass("acNameColor");
		$("#headdivbox").find(".aozxy8Nav").click();
	})
	$("#cqxync").on("click", function() {
		$(this).addClass("acNameColor").siblings().removeClass("acNameColor");
		$("#headdivbox").find(".cqxyncNav").click();
	})
	$("#jisuklsf").on("click", function() { //极速快乐十分系列
		$(this).addClass("acNameColor").siblings().removeClass("acNameColor");
		$("#headdivbox").find(".jisuklsfNav").click();
	})
}

//区分时时更新彩种类别
FunObj.getTimecut = function(lot_type) {
	var plancheck = $(".11check5_ran>li.check_ran").attr("data-text")
	if(FunObj.IsPk10(lot_type)) { //PK10系列
		var timeUrl = "LotteryPlan/getPksPlan.do";
	} else if(FunObj.IsXanwu(lot_type)) { //试时时彩系列
		var timeUrl = "LotteryPlan/getSscPlan.do";
	} else if(FunObj.IsXahao11check5(lot_type)) { //11选5系列
		var timeUrl = "LotteryPlan/getEfPlan.do";
	} else if(FunObj.Iskuai_3(lot_type)) { //快3系列
		var timeUrl = "LotteryPlan/getKsPlan.do";
	} else if(FunObj.Isklsf(lot_type)) { //快乐十分系列
		var timeUrl = "LotteryPlan/getKlsfPlan.do";
	} else if(FunObj.Iseagxy(lot_type)) { //PC蛋蛋
		var timeUrl = "LotteryPlan/getPc28Plan.do";
	}
	$.ajax({
		type: "get",
		url: publicUrl + timeUrl,
		data: {
			lotCode: lotCode[lot_type]
		},
		success: function(data) {
			//执行数据请求
			FunObj.timecutData(data, plancheck);
			timeDate = data;
		},
		error: function(data) {
			console.log(lot_type + "倒计时请求错误!");
		}
	});
}
FunObj.timecutData = function(data, plan) {
	if(typeof(data) == "string") {
		data = JSON.parse(data);
	}
//	console.log(data)
	var timer = data.result.data.countTime < 1 && data.result.data.countTime > -1000 ? 5000 : (data.result.data.countTime < 1 && data.result.data.countTime < -1000 ? 1000000 : data.result.data.countTime * 1000);
//	console.log(lot_type, timer / 1000);
	cutdownTime = timer;
	timeOutInit = setTimeout(function() {
		FunObj.orzhuihao(lot_type);
	}, timer);
	if(lot_type == "egxy28") {
		var dataObj = {
			lotteryCount: data.result.data.lotteryCount,
			time: data.result.data.countTime,
			pursueNum: data.result.data.planC.split(","),
			preDrawIssue: data.result.data.preDrawIssue,
			lotteryCost: data.result.data.lotteryCostAllC,
			lotteryCostAll: data.result.data.lotteryCostAllC,
			profit: data.result.data.profitC
		};
	} else {
		var dataObj = {
			lotteryCount: data.result.data.lotteryCount,
			time: data.result.data.countTime,
			pursueNum: data.result.data["plan" + plan].split(","),
			preDrawIssue: data.result.data.preDrawIssue,
			lotteryCost: data.result.data["lotteryCost" + plan],
			lotteryCostAll: data.result.data["lotteryCostAll" + plan],
			profit: data.result.data["profit" + plan]
		};
	}
	FunObj.pullNewData(dataObj);
}
FunObj.changeThisTitle = function(thisTitle){
	$(document).attr("title");
	var title = "【"+thisTitle+"开奖结果】"+ thisTitle+"开奖查询_"+ thisTitle+"开奖号码 - 绿星国际彩票开奖网";
	$(document).attr("title",title);
}
