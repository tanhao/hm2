$(function() {
	//设置当前时间
	var current_time = getDateStr(0);
	$(".date").val(current_time);
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

		//综合
		//两面
	$("#biaozxz .zhclear").live("click", function() {
		zoushiMethod("zhclear", this);
	});
	$("#biaozxz .zhcheckall").live("click", function() {
		zoushiMethod("all", this);
	});
	$("#biaozxz li").hover(function() {
		if($(this).hasClass("checked")) {
			$(this).addClass("hoverli");
		}
	}, function() {
		if($(this).hasClass("hoverli")) {
			$(this).removeClass("hoverli");
		}
	});
	//点击选择菜单
	$("#biaozxz .sinli").live("click", function() {
		$(this).removeClass("hoverli");
		var index = $(this).find("i").text();
		if(index == "1") { //隐藏遗漏
			if($(this).hasClass("checked")) {
				$(this).removeClass("checked")
				$(".yilou span").css("display", "none");
			} else {
				$(this).addClass("hoverli");
				$(this).addClass("checked")
				$(".yilou span").css("display", "block");
			}
		} else if(index == "2") { //隐藏折线
			if($(this).hasClass("checked")) {
				$(this).removeClass("checked")
				$("#chartLinediv canvas").css("display", "none");
			} else {
				$(this).addClass("hoverli");
				$(this).addClass("checked")
				$("#chartLinediv canvas").css("display", "block");
			}
		} else if(index == "3") { //遗漏分层
			if($(this).hasClass("checked")) {
				$(this).removeClass("checked")
				$("#chartLinediv .yilou").removeClass("yiloufc");
			} else {
				yilouFc(); //执行分层操作
				$(this).addClass("hoverli");
				$(this).addClass("checked")
			}
		} else if(index == "4") { //分隔线
			if($(this).hasClass("checked")) {
				$(this).removeClass("checked")
//				$("#chartLinediv .yilou").removeClass("yiloufc");
				$("#table_ganyah").find("tr[class='line_x']").remove();
//				fengeX(); //分割线
				drawLine();
			} else {
				fengeX(); //分割线
				$(this).addClass("hoverli");
				$(this).addClass("checked")

			}
		}
	});

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
		trigger: '.date',
		zIndex: 999,
		format: 'yyyy-mm-dd',
		onSelected: function(view, date, data) {
			var date = formatDate(date);
			listData(date);
			if(config.ifdebug) {
				console.log("选择日期时触发" + date);
			}
		},
		onClose: function(view, date, data) {
			if(config.ifdebug) {
				console.log("关闭是触发");
			}
		}
	});
	//启动加载数据
	//getData();
	
});

//**************************************************************
//遗漏分层
function yilouFc() {
	var tr = $("#table_ganyah tbody tr[class='yiloutr']");
	var trLen = tr.length;
	var tdcount = tr.filter(":first").children("td").size();
	for(var i = 4; i < tdcount; i++) {
		for(var j = 0; j <= trLen; j++) {
			var $td = tr.eq(j).children("td").eq(i);
			if($td.attr("title") == '0') {
				break;
			}
			$td.addClass("yiloufc");
		}
	}
}
//遗漏分层
function fengeX() {
	var tr = $("#table_ganyah tbody tr");
	var trLen = tr.length;
	tr.each(function(i, tr) {
		var tdLen = $(this).find("td").length;
		if(i > 0 && (i + 1) % 5 == 0) {
			$(tr).after("<tr class='line_x' style='height:0px'><td class='botmline2' style='height:0px;background-color:#dbdbdb' colspan='" + tdLen + "'></td></tr>")
		}
	});
	drawLine();
}

function formatDate(date) {
	var y = date.getFullYear();
	var m = date.getMonth() + 1;
	m = m < 10 ? '0' + m : m;
	var d = date.getDate();
	d = d < 10 ? ('0' + d) : d;
	return y + '-' + m + '-' + d;
};
//统计路珠类型
function tongjiCount(obj) {
	var $obj = $(obj);
	//得到选择的次数
	var txtNum = $obj.parent().find(".txtNum").val();
	//得到路珠类型
	var sectype = $obj.parent().find(".secType").val();
	//得到路珠类型
	var ifds = $obj.parent().parent().find(".ifds").text();
	var str = "";
	if(ifds == "单双") {
		if(sectype == 1) {
			str = "单";
		} else {
			str = "双";
		}
	} else if(ifds == "大小") {
		if(sectype == 1) {
			str = "大";
		} else {
			str = "小";
		}
	} else if(ifds == "龙虎") {
		if(sectype == 1) {
			str = "龙";
		} else {
			str = "虎";
		}
	} else if(ifds == "前后") {
		if(sectype == 1) {
			str = "前";
		} else {
			str = "后";
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
				"color": "#fff"
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
//加载当前页面数据
function loadotherData() {
	//倒计时完毕界面回答默认状态（最近30期）表格数据同样恢复默认
	listData(getDateStr(0), "");
	$(".listheadrl span").siblings().removeClass("checked");
	$("#today").addClass("checked");
}
//请求路珠list数据传入参数：time:时间；periods：期数
function listData(time, periods) {
	time = time == undefined ? "" : time; //如果请求数据time为undefined处理为""
	periods = periods == undefined ? "" : periods; //如果请求数据periods为undefined处理为""
	$.ajax({
		url: urlbublic + "pks/queryGysumTrend.do?date=" + time + "&periods=" + periods,
		type: "GET",
		data: {
			"lotCode":lotCode
		},
		success: function(data) {
			//执行数据请求
			createHtmlList(data);
		},
		error: function(data) {
			setTimeout(function(){
				loadotherData() //重新请求list数据
			},1000);
			if(config.ifdebug) {
				console.log("data error");
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
var lmmssxmc = []; //筛选名次
var lmmssxlz = []; //选择路珠
//***************两面模式*****start*********************
//两面模式方法--筛选名次
function zoushiMethod(type, obj) {

	var $obj = $(obj);
	$obj.removeClass("hoverli");
	//全选
	if(type == "all") {
		lmmssxmc = []; //清空数组
		if(!(lmmssxmc.length >= 10)) {
			$($("#biaozxz").find(".sinli")).each(function(index) {
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
		$($("#biaozxz").find(".sinli")).each(function(index) {
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
	$(lmmssxmc).each(function(index) {
		$("#box" + $(this) + "4").show();
	})
	excuteZhmsSelect(lmmssxmc, ["4"]);
}
//隐藏和显示//前面为名次，后面为路珠
function excuteZhmsSelect(mc, lz) {
	$(mc).each(function() {
		if(this == "1") { //遗漏
			$("#table_ganyah .yilou").find("span").hide();
		} else if(this == "2") { //拆线

		} else if(this == "3") { //遗漏分层

		} else if(this == "4") { //分隔线

		}
	})
}
//单选模式方法
function dxmsMethod() {
	$($("#zhms").find("li")).each(function(index) {
		console.log($(this).html());
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
//开奖数据格式转换
function typeOf(num, type) {
	if(type == "rank") {
		switch(num * 1) {
			case 1:
				return "冠军";
				break;
			case 2:
				return "亚军";
				break;
			case 3:
				return "第三名";
				break;
			case 4:
				return "第四名";
				break;
			case 5:
				return "第五名";
				break;
			case 6:
				return "第六名";
				break;
			case 7:
				return "第七名";
				break;
			case 8:
				return "第八名";
				break;
			case 9:
				return "第九名";
				break;
			case 10:
				return "第十名";
				break;
			case 11:
				return "冠亚和";
				break;
		}
	} else if("state") {
		switch(num * 1) {
			case 1:
				return "单双";
				break;
			case 2:
				return "大小";
				break;
			case 3:
				return "龙虎";
				break;
			case 4:
				return "前后";
				break;
		}
	}
}
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
	forRank(dataarr);
}
//构建路珠开奖记录异步数据
//校正背景偏移
function bgPostionX($boxnumb) {
	var tdlenth = $boxnumb.find(".tablebox td").length;
	if(tdlenth % 2 != 0) {
		$boxnumb.find(".item_con").css({
			"background-positionX": "0"
		});
	} else {
		$boxnumb.find(".item_con").css({
			"background-positionX": "-29px"
		});
	}
}
//根据不同的排名去填值
function forRank(data) {
	/*preIssue 开奖期数
	preDrawTime 开奖时间
	preDrawCode 开始号码
	gySum 冠亚和值
	missing 遗漏值，数组中小于零的值表示当前遗漏值，大于零表示冠亚和连接出现的次数
	appearCount 出现次数,数组长度为17
	averageMissingValues 平均遗漏值,数组长度为17
	maxMissingValues 最大遗漏值,数组长度为17
	maxAppearValues 最大连接出现值,数组长度为17*/
	var tdhtml = "";
	$("#table_ganyah tbody").empty();
	//迭代list
	$(data.list).each(function(index) {
		var preIssue = '<td>' + this.preIssue + '</td>';
		var preDrawTime = '<td>' + this.preDrawTime + '</td>';
		var fpredrawSpan = "";
		$(this.preDrawCode).each(function(index) {
			if(index == 0) {
				fpredrawSpan += "<span style='color:#f1010a'>" + this + "</span>&nbsp;";
			} else if(index == 1) {
				fpredrawSpan += "<span style='color:#0096ff'>" + this + "</span>&nbsp;";
			} else {
				fpredrawSpan += "<span>" + this + "</span>&nbsp;";
			}

		});
		var preDrawCode = '<td>' + fpredrawSpan + '</td>';
		var sum = this.gySum;
		var gySum = '<td style="background:#fdfaf5">' + sum + '</td>';
		var missingtd = "";;
		$(this.missing).each(function(index) {
			var title = "";
			var bgqiu = "";
			var yilou = sum;
			//			;
			if(this * 1 > 0) {
				title = "title='0'";
				bgqiu = "style='background:" + color[1] + "'";
			} else {
				yilou = Math.abs(this);
				title = "class='yilou'";
			}
			if(index > 16) {
				return;
			}
			missingtd += "<td " + title + "><span " + bgqiu + ">" + yilou + "</span></td>";
		});
		var tr = "<tr class='yiloutr'>" + preIssue + "" + preDrawTime + "" + fpredrawSpan + "" + preDrawCode + "" + gySum + "" + missingtd + "</tr>";
		$("#table_ganyah tbody").append(tr);
	});
	drawLine(); //画线
	/*//为tr追加斑马色
	for(var i=0;i<=10;i++){
		$("#lmsjtable").find(".mc"+i+":odd").css("background","#fffaf5");
	}*/
	var headthml = "<tr><th colspan='4' height='38'>数据统计</th><th>3</th><th>4</th><th>5</th><th>6</th><th>7</th><th>8</th><th>9</th><th>10</th><th>11</th><th>12</th><th>13</th><th>14</th><th>15</th><th>16</th><th>17</th><th>18</th><th>19</th></tr>";
	$("#table_ganyah tbody").append(headthml);
	var tdline = "";
	var tr1 = "<td class='font14' colspan='4'>出现总次数</td>";
	var tr2 = "<td class='font14' colspan='4'>平均遗漏值</td>";
	var tr3 = "<td class='font14' colspan='4'>最大连出值</td>";
	var tr4 = "<td class='font14' colspan='4'>最大遗漏值</td>";
	$(data.title).each(function(index) {

		//循环第一行
		$(this.appearCount).each(function() {
			tr1 += "<td>" + Math.abs(this) + "</td>";
		});
		//循环第二行
		$(this.averageMissingValues).each(function() {
			tr2 += "<td>" + Math.abs(this) + "</td>";
		})

		//循环第三行
		$(this.maxAppearValues).each(function() {
				tr3 += "<td>" + Math.abs(this) + "</td>";
			})
			//循环第四行
		$(this.maxMissingValues).each(function() {
			tr4 += "<td>" + Math.abs(this) + "</td>";
		})
	});
	$("#table_ganyah tbody").append("<tr>" + tr1 + "</tr><tr>" + tr2 + "</tr><tr>" + tr3 + "</tr><tr>" + tr4 + "</tr>");
}
//执行画线
function drawLine() {
	$("#chartLinediv canvas").remove();
	tablename = "table_ganyah";
	$("#" + tablename).find("tr").each(
		function() {
			$(this).find("td").each(
				function() {
					if($(this).attr("title") == "0") {
						$(this).attr("class", "hot " + $(this).removeClass("grey").attr("class")).find("span").attr("name", "hotSpan").text($(this).attr("name")).attr("class", "zoushiqiu bBK");
					}
				});
		}
	);
	$("#" + tablename + " tr").eq($("#" + tablename + " tr").length - 8).find("td").removeClass("tdbb").addClass("tdbbs");
	//画线
	chartOfBaseTrend.guanyaheLine();
}

//将数据拼成字符串
function diyString(strarr) {
	var str = "";
	$(strarr).each(function() {
		str += "&nbsp;" + this;
	})
	return str;
}

function boxList(rank, data) {
	//rank:名次,1-10分别为第一——第十名,11为冠亚和
	//state形态:如1.单双;2.大小;3.龙虎
	//totals：长度为2的数组,数组第一个元素表示单、大、龙;第二个元素表示双、小、虎
	//roadBeads:数组,数组的元素值为0或1,1表示单、大、龙;0表示双、小、虎
	//向不同的box中填值1到5有大小单双龙虎三种类型  6-11到有2个类型，没有龙虎
	var box = "";
	if(rank == 1 && data.state == 1) {
		box = "" + rank + "" + data.state;
	} else if(rank == 1 && data.state == 2) {
		box = "" + rank + "" + data.state;
	} else if(rank == 1 && data.state == 3) {
		box = "" + rank + "" + data.state;
	} else if(rank == 2 && data.state == 1) {
		box = "" + rank + "" + data.state;
	} else if(rank == 2 && data.state == 2) {
		box = "" + rank + "" + data.state;
	} else if(rank == 2 && data.state == 3) {
		box = "" + rank + "" + data.state;
	} else if(rank == 3 && data.state == 1) {
		box = "" + rank + "" + data.state;
	} else if(rank == 3 && data.state == 2) {
		box = "" + rank + "" + data.state;
	} else if(rank == 3 && data.state == 3) {
		box = "" + rank + "" + data.state;
	} else if(rank == 4 && data.state == 1) {
		box = "" + rank + "" + data.state;
	} else if(rank == 4 && data.state == 2) {
		box = "" + rank + "" + data.state;
	} else if(rank == 4 && data.state == 3) {
		box = "" + rank + "" + data.state;
	} else if(rank == 5 && data.state == 1) {
		box = "" + rank + "" + data.state;
	} else if(rank == 5 && data.state == 2) {
		box = "" + rank + "" + data.state;
	} else if(rank == 5 && data.state == 3) {
		box = "" + rank + "" + data.state;
	} else if(rank == 6 && data.state == 1) {
		box = "" + rank + "" + data.state;
	} else if(rank == 6 && data.state == 2) {
		box = "" + rank + "" + data.state;
	} else if(rank == 7 && data.state == 1) {
		box = "" + rank + "" + data.state;
	} else if(rank == 7 && data.state == 2) {
		box = "" + rank + "" + data.state;
	} else if(rank == 8 && data.state == 1) {
		box = "" + rank + "" + data.state;
	} else if(rank == 8 && data.state == 2) {
		box = "" + rank + "" + data.state;
	} else if(rank == 9 && data.state == 1) {
		box = "" + rank + "" + data.state;
	} else if(rank == 9 && data.state == 2) {
		box = "" + rank + "" + data.state;
	} else if(rank == 10 && data.state == 1) {
		box = "" + rank + "" + data.state;
	} else if(rank == 10 && data.state == 2) {
		box = "" + rank + "" + data.state;
	} else if(rank == 11 && data.state == 1) {
		box = "" + rank + "" + data.state;
	} else if(rank == 11 && data.state == 2) {
		box = "" + rank + "" + data.state;
	} else if(rank == 1 && data.state == 4) {
		box = "" + rank + "" + data.state;
	} else if(rank == 2 && data.state == 4) {
		box = "" + rank + "" + data.state;
	} else if(rank == 3 && data.state == 4) {
		box = "" + rank + "" + data.state;
	} else if(rank == 4 && data.state == 4) {
		box = "" + rank + "" + data.state;
	} else if(rank == 5 && data.state == 4) {
		box = "" + rank + "" + data.state;
	} else if(rank == 6 && data.state == 4) {
		box = "" + rank + "" + data.state;
	} else if(rank == 7 && data.state == 4) {
		box = "" + rank + "" + data.state;
	} else if(rank == 8 && data.state == 4) {
		box = "" + rank + "" + data.state;
	} else if(rank == 9 && data.state == 4) {
		box = "" + rank + "" + data.state;
	} else if(rank == 10 && data.state == 4) {
		box = "" + rank + "" + data.state;
	}
	return box;
}