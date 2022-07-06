$(function() {
	//加载头部和底部
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
	//初始化监听程序
	indexObj.init();
});

//创建对象
var indexObj = new Object();
//初始化数据
indexObj.init = function() {
	//选择彩种添加勾选背景
	$("#czList>li").on("click", function() {
		if($(this).hasClass("czListBg")) {
			$(this).removeClass("czListBg");
		} else {
			$(this).addClass("czListBg");
		}
		indexObj.testMenu();
	});
	//全选选择
	$("#checkAll").on("click", function() {
		$("#czList>li").each(function() {
			if(!($(this).hasClass("czListBg"))) {
				$(this).addClass("czListBg");
			}
		});
		indexObj.testMenu();
	});
	//取消选择
	$("#cancelAll").on("click", function() {
		$("#czList>li").each(function() {
			if(($(this).hasClass("czListBg"))) {
				$(this).removeClass("czListBg");
			}
		});
		indexObj.testMenu();
	});
	$("#display>li").on("hover", function() {
		if(!($(this).hasClass("liactiveBG"))) {
			$(this).addClass("liactiveBG").siblings().removeClass("liactiveBG");
		}
		if($(this).attr("id") == "kaiLi") {
			$("#kaiYes").show();
			$("#kaiYes6").show();
			$("#kaiNo").hide();
			$("#kaiNo6").hide();
		} else {
			$("#kaiNo").show();
			$("#kaiNo6").show();
			$("#kaiYes").hide();
			$("#kaiYes6").hide();
		}
	})
	//开启报警
	$("#soundsCtrl").on("click", function() {
		if($(this).find("i").hasClass("iChecked")) {
			$(this).find("i").removeClass("iChecked");
		} else {
			$(this).find("i").addClass("iChecked");
		}
	});
	$("#selectNum").on("change", function() {
		datasy.count = $(this).val();
		datasn.count = $(this).val();
		datas6.count = $(this).val();
		//启动ajax
		indexObj.ajax(url, datasy);
		indexObj.ajax(url6, datas6,6);
		indexObj.ajax(url, datasn);
	});
	indexObj.ajax(url, datasy);
	indexObj.ajax(url6, datas6,6);
	indexObj.ajax(url, datasn);
	//启动ajax
	indexObj.initAjax(url, datasy, datasn);
}
var datasy = {
	hot: 1,
	isOpen: 1,
	count: 2
}
var datasn = {
	hot: 1,
	isOpen: -1,
	count: 2
}
var datas6 = {
	hot: 0,
	isOpen: 1,
	count: 1
}
var intevVal = {};
var url = config.publicUrl + "dailyDragon/queryDailyDragonList.do?";
var url6 = config.url6+"dailyDragon/queryDailyDragonList.do";
indexObj.initAjax = function(url, datasy, datasn) {
	clearInterval(intevVal["interV"]);
	var flag = true;
	//初始加载
	var interV = setInterval(function() {
		if(flag) {
			indexObj.ajax(url, datasy);
			indexObj.ajax(url6, datas6,6);
			flag = false;
		} else {
			indexObj.ajax(url, datasn);
			flag = true;
		}
	}, 4000);
	intevVal["interV"] = interV;
}
indexObj.ajax = function(url, datas,num) {
	$.ajax({
		type: "post",
		url: url,
		data: datas,
		beforeSend: function() {},
		success: function(data) {
			if(num=="6"){
				indexObj.loadList6(datas, data);
			}else{
				indexObj.loadList(datas, data);
			}
			
			indexObj.testMenu();
		},
		error: function(data) {}
	});
}
//加载菜单
indexObj.loadMenu = function(data) {
	var data = indexObj.parseObj();
}
//显示已经选重的菜单列表
indexObj.testMenu = function() {
	$("#kaiDetial").find("tbody").find("tr").hide();
	$("#czList").find(".czListBg").each(function(i) {
		$("." + $(this).attr("id")).show();
	});
}
//构建HTML列表
indexObj.loadList = function(isOpen, data) {
	/**/
	var data = indexObj.parseObj(data);
	var html = "",
		flag = false;
	if(isOpen.isOpen == "1") {
		$("#kaiYes").find("tbody").empty();
	} else {
		$("#kaiNo").find("tbody").empty();
	}
	if(data.errorCode == "0") {
		$(data.result.data).each(function() {
			var lz, cltj,lot;
			var beadSource = indexObj.parseObj(this.beadSource);
			lz = "<a  href='javascript:void(0)' onclick='indexObj.getUrl(true," + this.lotCode + "," + beadSource.rank + "," + beadSource.type + ")'>查看</a>";
			cltj = "<a  href='javascript:void(0)' onclick='indexObj.getUrl(false," + this.lotCode + "," + beadSource.rank + "," + beadSource.type + ")'>查看</a>";
			html = "<tr class='" + this.lotCode + "'><td>" + this.lotteryName + "</td><td>" + this.location + "</td><td>" + this.currentDragon + "</td><td>" + lz + "</td><td>" + cltj + "</td></tr>"
			if(isOpen.isOpen == "1") {
				$("#kaiYes").find("tbody").append(html);
			} else {
				$("#kaiNo").find("tbody").append(html);
			}
			lot = this.lotCode;
			$("#czList").find(".czListBg").each(function() {
					if(lot==$(this).attr("id")){
						flag = true;
					}
			})
		});
		//如果有数据属于被选择的，报警
		if(flag&&$("#soundsCtrl").find("i").hasClass("iChecked")){
			document.getElementById("duSound").play();
		}
	}

}
//构建HTML列表
indexObj.loadList6 = function(isOpen, data) {
	var data = indexObj.parseObj(data);
	var html = "",
		flag = false;
	if(isOpen.isOpen == "1") {
		$("#kaiYes6").find("tbody").empty();
	} else {
		$("#kaiNo6").find("tbody").empty();
	}
	if(data.errorCode == "0") {
		$(data.result.data).each(function() {
			var lz, cltj,lot;
			lz = "<a  href='javascript:void(0)' onclick='jumpHtml(\"" + this.beadSource + "\")'>查看</a>";
			cltj = "<a  href='javascript:void(0)' onclick='jumpHtml(\"" + this.dragonSource + "\")'>查看</a>";
			html = "<tr class='" + this.lotCode + "'><td  width='222px'>" + this.lotteryName + "</td><td>" + this.location + "</td><td  width='120px'>" + this.currentDragon + "</td><td  width='50px'>" + lz + "</td><td  width='100px'>" + cltj + "</td></tr>"
			if(isOpen.isOpen == "1") {
				$("#kaiYes6").find("tbody").append(html);
			} else {
				$("#kaiNo6").find("tbody").append(html);
			}
			lot = this.lotCode;
			$("#czList").find(".czListBg").each(function() {
				if(lot==$(this).attr("id")){
					flag = true;
				}
			});
		});
		//如果有数据属于被选择的，报警
		if(flag&&$("#soundsCtrl").find("i").hasClass("iChecked")){
			document.getElementById("duSound").play();
		}
	}

}
//json转成对象
indexObj.parseObj = function(jsondata) {
	var data = null;
	if(typeof jsondata != "object") {
		data = JSON.parse(jsondata);
	} else {
		data = JSON.stringify(jsondata);
		data = JSON.parse(data);
	}
	return data;
}
//json转成对象
indexObj.getUrl = function(iflz, lotCode, rank, type) {
	var url = "";
	if(iflz) {
		switch(lotCode * 1) {
			case 10001: //pk10
				jumpHtml("../PK10/pk10kai_luzhufxzh.html?" + lotCode + "?" + rank + "?" + type);
				break;
			case 10002: //重庆时时彩
				jumpHtml("../shishicai_cq/ssc_luzhufenxizh.html?" + lotCode + "?" + rank + "?" + type);
				break;
			case 10005: //广东快乐十分
				jumpHtml("../klsf/klsf_zonghelzfx.html?" + lotCode + "?" + rank + "?" + type);
				break;
			case 10009: //重庆幸运农场
				jumpHtml("../cqnc/klsf_zonghelzfx.html?" + lotCode + "?" + rank + "?" + type);
				break;
			case 10004: //新疆时时彩
				jumpHtml("../shishicai_xj/ssc_luzhufenxizh.html?" + lotCode + "?" + rank + "?" + type);
				break;
			case 10003: //天津时时彩
				jumpHtml("../shishicai_tj/ssc_luzhufenxizh.html?" + lotCode + "?" + rank + "?" + type);
				break;
			case 10014: //北京快乐8
				jumpHtml("../beijinkl8/bjkl8_luzhufx.html?" + lotCode + "?" + rank + "?" + type);
				break;
			case 10038: //广西快乐十分
				jumpHtml("../klsf_gaungxi/klsf_zonghelzfx.html?" + lotCode + "?" + rank + "?" + type);
				break;
			case 10008: //十一运夺金
				jumpHtml("../shiyix5_sd/zonghelzfx.html?" + lotCode + "?" + rank + "?" + type);
				break;
			case 10006: //广东十一选五
				jumpHtml("../shiyix5_gd/zonghelzfx.html?" + lotCode + "?" + rank + "?" + type);
				break;
			case 10041: //福彩3D
				jumpHtml("../fc3D/kjhistory.html?" + lotCode + "?" + rank + "?" + type);
				break;
			case 10043: //体彩排列
				jumpHtml("../tcpl3/kjhistory.html?" + lotCode + "?" + rank + "?" + type);
				break;
			case 10026: //广西快三
				jumpHtml("../kuai3_guangxi/kuai3_zonghelz.html?" + lotCode + "?" + rank + "?" + type);
				break;
		}
	} else {
		switch(lotCode * 1) {
			case 10001: //pk10
				jumpHtml("../PK10/pk10kai.html");
				break;
			case 10002: //重庆时时彩
				jumpHtml("../shishicai_cq/ssc_index.html");
				break;
			case 10005: //广东快乐十分
				jumpHtml("../klsf/klsf_index.html");
				break;
			case 10009: //重庆幸运农场
				jumpHtml("../cqnc/index.html");
				break;
			case 10004: //新疆时时彩
				jumpHtml("../shishicai_xj/ssc_index.html");
				break;
			case 10003: //天津时时彩
				jumpHtml("../shishicai_tj/ssc_index.html");
				break; 
			case 10038: //广西快乐十分
				jumpHtml("../klsf_gaungxi/klsf_index.html");
				break;
			case 10008: //广东11选5
				jumpHtml("../shiyix5_sd/index.html");
				break;
			case 10006: //广东十一选五
				jumpHtml("../shiyix5_gd/index.html");
				break;
			case 10014: //北京快乐八
				jumpHtml("../beijinkl8/bjkl8_index.html");
				break;
			case 10041: //福彩3D
				jumpHtml("../fc3D/index.html");
				break;
			case 10043: //体彩排列
				jumpHtml("../tcpl3/index.html");
				break;
			case 10026: //广西快三
				jumpHtml("../kuai3_guangxi/kuai3_index.html");
				break;
		}
	}
	return url;
}

function jumpHtml(url) {
	window.open(url);
}