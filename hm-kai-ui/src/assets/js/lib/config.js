function SetHome(t) {
    document.all ? (document.body.style.behavior = "url(#default#homepage)", document.body.setHomePage(t)) : alert("您好,您的浏览器不支持自动设置页面为首页功能,请您手动在浏览器里设置该页面为首页!")
}
function addFavorite2() {
    var t = window.location,
    e = document.title,
    i = navigator.userAgent.toLowerCase();
    if (i.indexOf("360se") > -1) alert("由于360浏览器功能限制，请按 Ctrl+D 手动收藏！");
    else if (i.indexOf("msie 8") > -1) window.external.AddToFavoritesBar(t, e);
    else if (document.all) try {
        window.external.addFavorite(t, e)
    } catch(t) {
        alert("您的浏览器不支持,请按 Ctrl+D 手动收藏!")
    } else window.sidebar ? window.sidebar.addPanel(e, t, "") : alert("您的浏览器不支持,请按 Ctrl+D 手动收藏!")
}
function currentDay(t) {
    var e = t.split("-");
    return parseInt(e[1]) + "/" + (t = parseInt(e[2]))
}
function getDate() {
    var t = new Date;
    return t.getFullYear() + "-" + (t.getMonth() + 1) + "-" + t.getDate() + " " + t.getHours() + ":" + t.getMinutes() + ":" + t.getSeconds()
}
function ifNumIsNull(t, e) {
    return "" == t ? (0 != $(e).find(".errorbox").length && $(e).find(".errorbox").remove(), $(e).find(".rowbox2").append("<span class='errorbox' style='font-size:11px;color:orangered;'>开奖号码未开出，请尝试刷新页面或稍后再试！</span>"), $(e).find(".kajianhao").hide(), !0) : ($(e).find(".errorbox").hide(), $(e).find(".kajianhao").show(), !1)
}
function setTextColor(t) {
    listColor = "#" + t,
    $("#jrsmhmtj table tr:odd").find("td").css("background", listColor)
}
var config = {
    publicUrl: "/api.php?param=",
   
   
    listTime: 2e3,
   
    firstLoad: !1,
    ifdebug: !1,
    ifScalse: .782,
    newRows: 7,
    Uname: ["冷雨夜", "木木三", "醉书生", "司令爷", "沐子眠"],
    SscUname: ["忆离笙", "值半钱", "楼满风", "沐雪柔", "寒千落"]
},
lotCode = {
    pk10: 10001,
    aozxy10: 10012,
    jisusc: 10037,
    cqssc: 10002,
    cqqxc: 10050,
    tjssc: 10003,
    xjssc: 10004,
    aozxy5: 10010,
    jisussc: 10036,
    gdklsf: 10005,
    aozxy8: 10011,
    tjklsf: 10034,
    cqxync: 10009,
    gxklsf: 10038,
    gdsyxw: 10006,
    jxef: 10015,
    jsef: 10016,
    ahef: 10017,
    shef: 10018,
    lnef: 10019,
    hbef: 10020,
    cqef: 10021,
    gxef: 10022,
    jlef: 10023,
    nmgef: 10024,
    zjef: 10025,
    sdsyydj: 10008,
    jsksan: 10007,
    gxft: 10026,
    jlft: 10027,
    hebft: 10028,
    nmgft: 10029,
    ahft: 10030,
    fjft: 10031,
    hubft: 10032,
    bjft: 10033,
    aozxy20: 10013,
    bjkl8: 10014,
    twbg: 10047,
    fcssq: 10039,
    cjdlt: 10040,
    fcsd: 10041,
    fcqlc: 10042,
    pailie3: 10043,
    pailie5: 10044,
    qxc: 10045,
    egxy28: 10046,
    xingyft: 10035,
	jisuklsf: 10053,
    jisuef: 10055,
    jisuksan: 10052,
    jisukl8: 10054,
    tencentffc: 10056
},
oldLog = console.log;
console.log = function() {
    config.ifDebugger && oldLog.apply(console, arguments)
};
var ifopenSoundsBtn = !1;
$(function() {
    if ($("#littleimg").length >= 1 && "index" != $("#ifindex").val() && (tools.bannerImg(), $("#littleimg").find(".swiper-container").addClass("swiperother")), publicmethod.fixBox(), $(".wxkefuicon").on("mouseover",
    function() {
        $(".wxewmicon").css("display", "inline-block")
    }), $(".wxkefuicon").on("mouseout",
    function() {
        $(".wxewmicon").css("display", "none")
    }), $(".wxkekufu").on("mouseover",
    function() {
        $(".wechatImg").css("display", "inline-block")
    }), $(".wxkekufu").on("mouseout",
    function() {
        $(".wechatImg").css("display", "none")
    }), $("#localyears").text((new Date).getFullYear()), $(".fankuicon").live("click",
    function() {
        if ("" != localStorage.current_time && void 0 != localStorage.current_time) {
            var t = localStorage.current_time,
            e = getDate(),
            i = t.replace("-", "/"),
            n = e.replace("-", "/");
            i = new Date(i.replace("-", "/")).getTime(),
            ((n = new Date(n.replace("-", "/")).getTime()) - i) / 1e3 / 60 <= 10 ? ($("#info1").hide(), $("#info2").hide(), $("#info3").show()) : ($("#btn_submit").show(), $("#info3").hide(), $("#info2").hide(), $("#info1").show())
        } else $("#btn_submit").show(),
        $("#info3").hide(),
        $("#info2").hide(),
        $("#info1").show()
    }), $("#btn_submit").live("click",
    function() {
        var t = $("#nickName").val(),
        e = $("#linkType").val(),
        i = parseInt(e),
        n = $("#linkNumber").val(),
        o = $("#advice").val();
        yonghufankui.listData(t, i, n, o)
    }), "index" == $("#ifindex").val() || $("#user_adv").load("../public/user_adv.html",
    function() {}), $("#ifSoundOpen").on("click", ".ifSoundOpen",
    function(t) {
        ifopenSoundsBtn ? ($("#ifSoundOpen").find(".ifSoundIcon").removeClass("stopsound"), $("#ifSoundOpen").find("i").text("关闭声音"), ifopenSoundsBtn = !1, $(".soundbtn").css("background", "#ff7b00"), $("#soundSet").find(".soundbtn").removeClass("disabled"), $("#soundKindsIcon").removeClass().addClass("soundDefY"), console.log("ifopenSoundsBtn   " + ifopenSoundsBtn)) : ($("#ifSoundOpen").find(".ifSoundIcon").addClass("stopsound"), $("#ifSoundOpen").find("i").text("打开声音"), ifopenSoundsBtn = !0, $("#soundSet").find(".soundbtn").addClass("disabled"), $(".soundbtn").css("background", "#ccc"), $("#soundSet").find(".soundpanel").hide("200"), $("#soundKindsIcon").removeClass().addClass("soundDef"), console.log("ifopenSoundsBtn   " + ifopenSoundsBtn)),
        t.stopPropagation()
    }), $("#soundSet").on("click", ".soundbtn:not('.disabled')",
    function(t) {
        $(this).find(".soundpanel").show("200"),
        t.stopPropagation()
    }), $(".bodybox").on("click",
    function(t) {
        $(this).parent().parent().find(".soundpanel").hide("200")
    }), $("#soundSet").on("click", ".close",
    function(t) {
        $(this).parent().parent().find(".soundpanel").hide("200");
        var e = $("#soundSet").find("input:[checked='checked']").val();
        "RING_01" == e ? $("#soundKindsIcon").removeClass().addClass("soundDefY") : "RING_02" == e ? $("#soundKindsIcon").removeClass().addClass("sound2Y") : "RING_03" == e ? $("#soundKindsIcon").removeClass().addClass("sound3Y") : "RING_04" == e ? $("#soundKindsIcon").removeClass().addClass("sound4Y") : "RING_05" == e && $("#soundKindsIcon").removeClass().addClass("sound5Y"),
        t.stopPropagation()
    }), $("#soundSet").on("click", "input",
    function() {
		if($(this).val() == 0){
			return $("#soundSet").find("audio").attr("src", "");
		}
        void 0 != $(this).val() && ("RING_01" == $(this).val() ? $("#soundKindsIcon").removeClass().addClass("soundDefY") : "RING_02" == $(this).val() ? $("#soundKindsIcon").removeClass().addClass("sound2Y") : "RING_03" == $(this).val() ? $("#soundKindsIcon").removeClass().addClass("sound3Y") : "RING_04" == $(this).val() ? $("#soundKindsIcon").removeClass().addClass("sound4Y") : "RING_05" == $(this).val() && $("#soundKindsIcon").removeClass().addClass("sound5Y"), $("#soundSet").find("audio").attr("src", "../../media/" + $(this).val() + ".wav"))
    }), "operator" == $("#operator").val() && $(window).on("scroll",
    function() {
        $(this).scrollTop() > 195 ? $(".haomabox").addClass("fixedHead") : $(".haomabox").removeClass("fixedHead")
    }), setTimeout(function() {
        try {
            tools.addSund()
        } catch(t) {}
    },
    1e3), "noLoad" == $("#ofNoLoad").val());
    else try {
        loadotherData()
    } catch(t) {}
    setTimeout(function() {
        config.firstLoad = !0
    },
    2e3),
    tools.initListen(),
	$("#showRule img").on("click",
    function() {
        $("#rulebox").css("z-index", "200"),
        $(".ruleContent").animate({
            width: "735px",
            "margin-right": "-440px"
        },
        200)
    }),
    $("#rulebox .closeRule").on("click",
    function() {
        $(".ruleContent").animate({
            width: "0",
            "margin-right": "0"
        },
        200,
        function() {
            $("#rulebox").css("z-index", "-1")
        })
    })
});
var publicUrl = config.publicUrl,
yonghufankui = {},
publicmethod = {},
tools = {};
tools.undefindeInitZero = function() {
    $("#shuanmiandata>td:contains('undefined')").text(0),
    $("#gylhcs>td:contains('undefined')").text(0)
},
yonghufankui.listData = function(t, e, i, n) {
    var o = {
        nickName: t,
        linkType: e,
        linkNumber: i,
        fedBack: n,
        source: window.location.href
    };
    $.ajax({
        url: publicUrl + "fedBack/saveFedBack.do",
        type: "GET",
        data: o,
        success: function(t) {
            $("#btn_submit").hide(),
            $("#btn_submiting").show(),
            yonghufankui.createList(t)
        },
        error: function(t) {
            console.log("data error")
        }
    })
},
yonghufankui.createList = function(t) {
    if ($("#btn_submiting").hide(), "0" == (t = JSON.parse(t)).errorCode) {
        var e = getDate();
        localStorage.current_time = e,
        $("#info1").hide(),
        $("#info2").show()
    }
},
tools.addSund = function() {
    $("#ifSoundOpen,#soundSet").empty(),
    $("#ifSoundOpen").append('<div class="ifSoundOpen"><i>关闭声音</i><div class="ifSoundIcon"></div></div>'),
    $("#soundSet").append('<div class="soundbtn"><i>铃声设置</i><div class="soundicon soundDefY" id="soundKindsIcon">\t<audio src="../../media/RING_01.wav"  controls="controls" id="audioid">\t\tYour browser does not support the audio tag.\t</audio></div><div class="soundpanel">\t<div class="close"></div>\t<div class="soundlist">\t\t<ul>\t\t\t<li><label><input type="radio" name="sound" value="RING_01"  checked="checked">&nbsp;默认(闹钟铃)</label></li>\t\t\t<li><label><input type="radio" name="sound" value="RING_02">&nbsp;声音二(上课铃)</label></li>\t\t\t<li><label><input type="radio" name="sound" value="RING_03">&nbsp;声音三(打锣声)</label></li>\t\t\t<li><label><input type="radio" name="sound" value="RING_04">&nbsp;声音四(打鼓声)</label></li>\t\t\t<li><label><input type="radio" name="sound" value="RING_05">&nbsp;声音五(点滴声)</label></li>\t\t\t<li><label><input type="radio" name="sound" value="0">&nbsp;关闭</label></li>\t\t\t<li>\t\t\t\t提示时间：\t\t\t\t<select>\t\t\t\t\t<option value="5"  selected="selected">开奖前5秒</option>\t\t\t\t\t<option value="20">开奖前20秒</option>\t\t\t\t\t<option value="10">开奖前10秒</option>\t\t\t\t\t<option value="30">开奖前30秒</option>\t\t\t\t\t<option value="40">开奖前40秒</option>\t\t\t\t\t<option value="50">开奖前50秒</option>\t\t\t\t\t<option value="60">开奖前60秒</option>\t\t\t\t\t<option value="begin">开奖后</option>\t\t\t\t</select>\t\t\t</li>\t\t</ul>\t</div></div></div>')
},
publicmethod.fixBox = function() {
    var t = $(".fixedgoBack").find(".fix1200"),
    e = $(".fixedgoBack").find(".leftright");
    $(t).empty(),
    $(e).empty();
    $(t).append('<div class = "wxewmicon"></div><div class = "wechatImg"></div><ul><li><a class="backold" target="_blank" href="javascript:void(0)" target="_blank"></a></li><li><a class="kefuicon" target="_blank" href="'+'"></a></li><li><a class="wxkekufu" target="_blank"></a></li><li>\x3c!--用户反馈模态框--\x3e<span class="fankuicon fankuicon_a" data-toggle="modal" data-target="#myModal"><span class="fankuicons"></span></span></li><li><a class="wxkefuicon" target="_blank"></a></li><li><a class="topicon" id="gotop" href="javascript:"></a></li></ul>'),
    0 != e.length && $(t).append('<ul class="ul_pre"><li class="prev_li"></li><li class="next_li"></li></ul>');
	$.ajax({
		url: "/news/index.php/api/index/get_config?name=qqkf",
		type: "GET",
		timeout: 60000,
		async: false,
		success: function(data) {
			if(data){
				$('.kefuicon').attr('href',data);
			}
		},
	});
},
publicmethod.insertHeadPk10 = function(t, e) {
    var i = tools.parseObj(t);
    if ("100002" == i.result.businessCode) throw new Error("error");
    if (i = i.result.data, tools.operatorTime("" == i.drawTime ? "0": i.drawTime, i.serverTime) <= 0) throw new Error("error");
    $(".nextIssue").text(i.drawIssue),
    $(".preDrawIssue").text(i.preDrawIssue);
    var n = i.preDrawCode.split(","),
    o = i.totalCount;
    $(".totalCount").text(o),
    $(".drawCount").text(i.drawCount),
    $(".sdrawCount").text(o - 1 * i.drawCount);
    for (var a = "",
    s = 0,
    d = $(".longhu").find("td").length; s < d; s++) switch (s) {
    case 0:
        a += "<td>" + ("0" == i.firstDT ? "龙": "虎") + "</td>";
        break;
    case 1:
        a += "<td>" + ("0" == i.secondDT ? "龙": "虎") + "</td>";
        break;
    case 2:
        a += "<td>" + ("0" == i.thirdDT ? "龙": "虎") + "</td>";
        break;
    case 3:
        a += "<td>" + ("0" == i.fourthDT ? "龙": "虎") + "</td>";
        break;
    case 4:
        a += "<td>" + ("0" == i.fifthDT ? "龙": "虎") + "</td>"
    }
    return $(".longhu").html(""),
    $(".longhu").append(a),
    $(".sumFS").text(i.sumFS),
    $(".sumBigSamll").text("0" == i.sumBigSamll ? "大": "小"),
    $(".sumSingleDouble").text("0" == i.sumSingleDouble ? "单": "双"),
    tools.countDown(i.drawTime, i.serverTime, e),
    animateMethod.pk10end(n, e),
    $("#waringbox").hide(300),
    clearInterval(animateID[e]),
    delete animateID[e],
    setTimeout(function() {
        tools.ifCheckedOnToday() && loadotherData()
    },
    config.listTime),
    i.preDrawCode
},
publicmethod.insertHeadSsC = function(t, e) {
    var i = tools.parseObj(t);
    if ("100002" == i.result.businessCode) throw new Error("error");
    if (i = i.result.data, tools.operatorTime("" == i.drawTime ? "0": i.drawTime, i.serverTime) <= 0) throw new Error("error");
    var n = i.totalCount;
    if ($(e).find(".preDrawIssue").text(i.preDrawIssue), $(e).find(".nextIssue").text(i.drawIssue), void 0 !== $("#drawTime").val()) {
        $("#drawTime").val(i.drawTime.substr(i.drawTime.length - 8, 8)),
        $(e).find("#sumNum").val(i.sumNum),
        $(e).find("#sumSingleDouble").val(0 == i.sumSingleDouble ? "单": "双"),
        $(e).find("#sumBigSmall").val(0 == i.sumBigSmall ? "大": "小");
        var o = "";
        "0" == i.dragonTiger ? o = "龙": "1" == i.dragonTiger ? o = "虎": "2" == i.dragonTiger && (o = "和"),
        $(e).find("#dragonTiger").val(o)
    }
    $(e).find(".totalCount").text(n),
    $(e).find(".drawCount").text(i.drawCount),
    $(e).find(".sdrawCount").text(1 * n - 1 * i.drawCount),
    config.ifdebug && console.log("nextIssue:" + localStorage.nextIssue),
    $(".lenresinli").removeClass("checked"),
    tools.countDown(i.drawTime, i.serverTime, e),
    animateMethod.sscAnimateEnd(i.preDrawCode, e),
    $("#waringbox").hide(300),
    setTimeout(function() {
        tools.ifCheckedOnToday() && loadotherData()
    },
    config.listTime)
},
publicmethod.insertHeadKlsf = function(t, e) {
    var i = tools.parseObj(t);
    if ("100002" == i.result.businessCode) throw new Error("error");
    if (i = i.result.data, tools.operatorTime("" == i.drawTime ? "0": i.drawTime, i.serverTime) <= 0) throw new Error("error");
    var n = i.totalCount;
    $(e).find(".preDrawIssue").text(i.preDrawIssue),
    $(e).find(".nextIssue").text(i.drawIssue),
    $(e).find(".drawCount").text(i.drawCount),
    $(".totalCount").text(n),
    $(e).find(".sdrawCount").text(1 * n - 1 * i.drawCount),
    $(e).find(".sumNum").text(i.sumNum),
    $(e).find(".sumSingleDouble").text(0 == i.sumSingleDouble ? "单": "双"),
    $(e).find(".sumBigSmall").text(0 == i.sumBigSmall ? "大": "小"),
    $(e).find(".lastBigSmall").text(0 == i.lastBigSmall ? "尾大": "尾小");
    "0" == i.dragonTiger || ("1" == i.dragonTiger || i.dragonTiger),
    $(e).find(".firstDragonTiger").text(0 == i.firstDragonTiger ? "龙": "虎"),
    $(e).find(".secondDragonTiger").text(0 == i.secondDragonTiger ? "龙": "虎"),
    $(e).find(".thirdDragonTiger").text(0 == i.thirdDragonTiger ? "龙": "虎"),
    $(e).find(".fourthDragonTiger").text(0 == i.fourthDragonTiger ? "龙": "虎"),
    void 0 !== $("#drawTime").val() && $("#drawTime").val(i.drawTime.substr(i.drawTime.length - 8, 8)),
    tools.countDown(i.drawTime, i.serverTime, e),
    animateMethod.sscAnimateEnd(i.preDrawCode, e);
    var o = i.preDrawCode.split(",");
    "#gxklsf" == e && tools.gxKaiBg(o, e),
    $("#waringbox").hide(300),
    setTimeout(function() {
        tools.ifCheckedOnToday() && loadotherData()
    },
    config.listTime)
},
publicmethod.insertHead11xw = function(t, e) {
    var i = tools.parseObj(t);
    if ("100002" == i.result.businessCode) throw new Error("error");
    if (i = i.result.data, tools.operatorTime("" == i.drawTime ? "0": i.drawTime, i.serverTime) <= 0) throw new Error("error");
    var n = i.totalCount;
    $(e).find(".preDrawIssue").text(i.preDrawIssue),
    $(e).find(".nextIssue").text(i.drawIssue),
    $(e).find(".drawCount").text(i.drawCount),
    $(".totalCount").text(n),
    $(e).find(".sdrawCount").text(1 * n - 1 * i.drawCount),
    $(e).find(".sumNum").text(i.sumNum),
    $(e).find(".sumSingleDouble").text(0 == i.sumSingleDouble ? "单": "双"),
    $(e).find(".sumBigSmall").text(0 == i.sumBigSmall ? "大": "小"),
    $(e).find(".lastBigSmall").text(0 == i.lastBigSmall ? "尾大": "尾小");
    "0" == i.dragonTiger || ("1" == i.dragonTiger || i.dragonTiger),
    void 0 !== $("#drawTime").val() && ($("#drawTime").val(i.drawTime.substr(i.drawTime.length - 8, 8)), $(e).find("#sumNum").val(i.sumNum), $(e).find("#sumSingleDouble").val(typeOf("dsh", i.sumSingleDouble)), $(e).find("#sumBigSmall").val(typeOf("dxh", i.sumBigSmall))),
    $(e).find(".behindThree").text(typeOf("san", i.behindThree)),
    $(e).find(".betweenThree").text(typeOf("san", i.betweenThree)),
    $(e).find(".lastThree").text(typeOf("san", i.lastThree)),
    $(".lenresinli").removeClass("checked"),
    tools.countDown(i.drawTime, i.serverTime, e),
    animateMethod.sscAnimateEnd(i.preDrawCode, e),
    $("#waringbox").hide(300),
    setTimeout(function() {
        setTimeout(function() {
            tools.ifCheckedOnToday() && loadotherData()
        },
        1e3)
    },
    config.listTime)
},
publicmethod.insertHeadJsk3 = function(t, e) {
    var i = tools.parseObj(t);
    if ("100002" == i.result.businessCode) throw new Error("error");
    if (i = i.result.data, tools.operatorTime("" == i.drawTime ? "0": i.drawTime, i.serverTime) <= 0) throw new Error("error");
    var n = i.totalCount;
    $(e).find(".preDrawIssue").text(i.preDrawIssue),
    $(e).find(".nextIssue").text(i.drawIssue),
    $(e).find(".sumNum").text(i.sumNum),
    $(e).find(".sumSingleDouble").text(0 == i.sumSingleDouble ? "单": "双"),
    $(e).find(".sumBigSmall").text(0 == i.sumBigSmall ? "大": "小"),
    $(e).find(".totalCount").text(n),
    $(e).find(".drawCount").text(i.drawCount),
    $(e).find(".sdrawCount").text(1 * n - 1 * i.drawCount);
    var o = "";
    "0" == i.dragonTiger ? o = "龙": "1" == i.dragonTiger ? o = "虎": "2" == i.dragonTiger && (o = "和"),
    $(e).find(".dragonTiger").text(o),
    tools.countDown(i.drawTime, i.serverTime, e),
    animateMethod.kuai3AnimateEnd(i.preDrawCode, e),
    $("#waringbox").hide(300),
    setTimeout(function() {
        tools.ifCheckedOnToday() && loadotherData()
    },
    config.listTime)
},
publicmethod.insertHeadCqnc = function(t, e) {
    var i = tools.parseObj(t);
    if ("100002" == i.result.businessCode) throw new Error("error");
    if (i = i.result.data, tools.operatorTime("" == i.drawTime ? "0": i.drawTime, i.serverTime) <= 0) throw new Error("error");
    var n = i.totalCount;
    $(e).find(".preDrawIssue").text(i.preDrawIssue),
    $(e).find(".nextIssue").text(i.drawIssue),
    $(e).find(".drawCount").text(i.drawCount),
    $(e).find(".totalCount").text(n),
    $(e).find(".sdrawCount").text(1 * n - 1 * i.drawCount),
    $(e).find(".sumNum").text(i.sumNum),
    $(e).find(".sumSingleDouble").text(0 == i.sumSingleDouble ? "单": "双"),
    $(e).find(".sumBigSmall").text(0 == i.sumBigSmall ? "大": "小"),
    $(e).find(".lastBigSmall").text(0 == i.lastBigSmall ? "尾大": "尾小");
    "0" == i.dragonTiger || ("1" == i.dragonTiger || i.dragonTiger),
    $(e).find(".firstDragonTiger").text(0 == i.firstDragonTiger ? "龙": "虎"),
    $(e).find(".secondDragonTiger").text(0 == i.secondDragonTiger ? "龙": "虎"),
    $(e).find(".thirdDragonTiger").text(0 == i.thirdDragonTiger ? "龙": "虎"),
    $(e).find(".fourthDragonTiger").text(0 == i.fourthDragonTiger ? "龙": "虎"),
    tools.countDown(i.drawTime, i.serverTime, e),
    animateMethod.cqncAnimateEnd(i.preDrawCode, e),
    $("#waringbox").hide(300),
    setTimeout(function() {
        tools.ifCheckedOnToday() && loadotherData()
    },
    config.listTime)
},
publicmethod.insertHeadBjkl8 = function(t, e) {
    var i = tools.parseObj(t);
    if ("100002" == i.result.businessCode) throw new Error("error");
    if (i = i.result.data, tools.operatorTime("" == i.drawTime ? "0": i.drawTime, i.serverTime) <= 0) throw new Error("error");
    var n = i.totalCount;
    $(e).find(".preDrawIssue").text(i.preDrawIssue),
    $(e).find(".nextIssue").text(i.drawIssue),
    $(e).find(".drawCount").text(i.drawCount),
    $(e).find(".totalCount").text(n),
    $(e).find(".sdrawCount").text(1 * n - 1 * i.drawCount),
    $(e).find(".sumNum").text(i.sumNum),
    $(e).find(".sumBigSmall").text(typeOf("dxh", i.sumBigSmall)),
    $(e).find(".sumSingleDouble").text(typeOf("dsh", i.sumSingleDouble)),
    $(e).find(".singleDoubleCount").text(typeOf("dsd", i.singleDoubleCount)),
    $(e).find(".frontBehindCount").text(typeOf("qhh", i.frontBehindCount)),
    $(e).find(".sumBsSd").text(typeOf("zhzh", i.sumBsSd)),
    $(e).find(".sumWuXing").text(typeOf("wuxing", i.sumWuXing)),
    void 0 !== $("#drawTime").val() && ($("#drawTime").val(i.drawTime.substr(i.drawTime.length - 8, 8)), $(e).find("#preDrawIssue").val(i.preDrawIssue)),
    tools.countDown(i.drawTime, i.serverTime, e);
    var o = i.preDrawCode + "," + i.sumNum;
    "#egxy" == e ? (animateMethod.sscAnimateEnd(o, e), tools.egxy28(e)) : (animateMethod.sscAnimateEnd(o, e), tools.bjkl8BagColor(o, e)),
    $("#waringbox").hide(300),
    setTimeout(function() {
        tools.ifCheckedOnToday() && loadotherData()
    },
    config.listTime)
},
tools.ifselectedOpacity = function(t) {
    $(t).hasClass("selectedOpacity") ? ($(t).removeClass(), $(t).addClass("selectedOpacity")) : $(t).removeClass()
},
tools.bigOrSmall = function(t, e) {
    $("#jrsmhmtj .blueqiu li").each(function(i) {
        var n = $(this).text(),
        o = n % 2 == 0,
        a = n >= e;
        $(this).find("i").hide(),
        "xshm" == t ? (tools.ifselectedOpacity($(this)), $(this).addClass("gxnumblue"), (i + 1) % 10 == 0 && $(this).addClass("li_after"), "10038" == lotCode ? 1 == n || 4 == n || 7 == n || 10 == n || 13 == n || 16 == n || 19 == n ? $(this).addClass("gxnumred") : 3 != n && 6 != n && 9 != n && 12 != n && 15 != n && 18 != n && 21 != n || $(this).addClass("gxnumgreen") : "10011" == lotCode || "10005" == lotCode || "10034" == lotCode ? n >= 19 && $(this).addClass("numredkong") : $(this).addClass("sscnumblue"), $(this).find("i").show()) : "xsdx" == t ? (tools.ifselectedOpacity($(this)), a ? (21 == n ? $(this).addClass("bluetotle") : $(this).addClass("bluebig"), (i + 1) % 10 == 0 && $(this).addClass("bluebig li_after")) : ($(this).addClass("bluesmall"), (i + 1) % 10 == 0 && $(this).addClass("bluesmall li_after"))) : "xsds" == t && (tools.ifselectedOpacity($(this)), o ? ($(this).addClass("blueeven"), (i + 1) % 10 == 0 && $(this).addClass("blueeven li_after")) : (21 == n && $(this).addClass("bluetotle"), $(this).addClass("bluesingular"), (i + 1) % 10 == 0 && $(this).addClass("bluesingular li_after")))
    })
},
tools.bigOrSmallTot = function(t, e) {
    $("#jrsmhmtj .blueqiu li").each(function(i) {
        var n = $(this).text(),
        o = n % 2 == 0,
        a = n >= e;
        $(this).find("i").hide(),
        "xshm" == t ? (tools.ifselectedOpacity($(this)), $(this).addClass("sscnumblue"), (i + 1) % 10 == 0 && $(this).addClass("li_after"), 19 != n && 20 != n || $(this).addClass("numred"), $(this).find("i").show()) : "xsdx" == t ? (tools.ifselectedOpacity($(this)), a ? (11 == n ? $(this).addClass("bluetotle") : $(this).addClass("bluebig"), (i + 1) % 10 == 0 && $(this).addClass("bluebig li_after")) : ($(this).addClass("bluesmall"), (i + 1) % 10 == 0 && $(this).addClass("bluesmall li_after"))) : "xsds" == t && (tools.ifselectedOpacity($(this)), o ? ($(this).addClass("blueeven"), (i + 1) % 10 == 0 && $(this).addClass("blueeven li_after")) : (11 == n ? $(this).addClass("bluetotle") : $(this).addClass("bluesingular"), (i + 1) % 10 == 0 && $(this).addClass("bluesingular li_after")))
    })
},
tools.parseObj = function(t) {
    var e = null;
    return "object" != typeof t ? e = JSON.parse(t) : (e = JSON.stringify(t), e = JSON.parse(e)),
    e
},
tools.bigOrSmallXync = function(t, e) {
    $("#jrsmhmtj .blueqiu li").each(function(i) {
        var n = $(this).text(),
        o = n % 2 == 0,
        a = n > e;
        "xshm" == t ? (tools.ifselectedOpacity($(this)), $(this).addClass("ncnum" + n), (i + 1) % 10 == 0 && $(this).addClass("li_after")) : "xsdx" == t ? (tools.ifselectedOpacity($(this)), a ? ($(this).addClass("bluebig"), (i + 1) % 10 == 0 && $(this).addClass("bluebig li_after")) : ($(this).addClass("bluesmall"), (i + 1) % 10 == 0 && $(this).addClass("bluesmall li_after"))) : "xsds" == t && (tools.ifselectedOpacity($(this)), o ? ($(this).addClass("blueeven"), (i + 1) % 10 == 0 && $(this).addClass("blueeven li_after")) : ($(this).addClass("bluesingular"), (i + 1) % 10 == 0 && $(this).addClass("bluesingular li_after")))
    })
},
tools.playSound = function(t) {
	if($("#soundSet").find("audio").attr("src")==''){
		return false;
	}
    if (0 == ifopenSoundsBtn && void 0 != ifopenSoundsBtn) {
        var e = $("#audioid");
        "begin" == t && "begin" == $("#soundSet").find("select").val() ? e[0].play() : $("#soundSet").find("select").val() == t - 1 && e[0].play()
    }
},
tools.repeatAjaxt = {
    kuai3: function(t) {
        clearInterval(animateID[t]),
        setTimeout(function() {
            ajaxRequst($(t).find(".nextIssue").text(), $(t).attr("id"))
        },
        1e3)
    },
    qiu: function(t) {
        clearInterval(animateID[t]);
        var e = "";
        $(t).find(".kajianhao li").each(function() {
            e += $(this).text() + ","
        }),
        animateMethod.sscAnimateEnd(e, t),
        setTimeout(function() {
            ajaxRequst($(t).find(".nextIssue").text(), $(t).attr("id"))
        },
        1e3)
    },
    pk10: function(t) {
        clearInterval(animateID[t]),
        setTimeout(function() {
            ajaxRequst($(".nextIssue").text())
        },
        1e3)
    },
    cqnc: function(t) {
        clearInterval(animateID[t]),
        setTimeout(function() {
            ajaxRequst($(".nextIssue").text())
        },
        1e3)
    }
},
tools.repeatIndexAjax = {
    kuai3: function(t) {
        setTimeout(function() {
            ajaxRequst($(t).find(".nextIssue").text(), $(t).attr("id"))
        },
        1e3)
    },
    qiu: function(t) {
        var e = "";
        $(t).find(".kajianhao li").each(function() {
            e += $(this).text() + ","
        }),
        animateMethod.sscAnimateEnd(e, t),
        setTimeout(function() {
            ajaxRequst($(t).find(".nextIssue").text(), $(t).attr("id"))
        },
        1e3)
    },
    qiuam: function(t) {
        var e = "";
        $(t).find(".kajianhao li").each(function() {
            e += $(this).text() + ","
        }),
        animateMethod.sscAnimateEnd(e, t)
    },
    pk10: function(t) {
        setTimeout(function() {
            ajaxRequst($(t).find(".nextIssue").text(), $(t).attr("id"))
        },
        1e3)
    },
    cqnc: function(t) {
        clearInterval(animateID[t]),
        setTimeout(function() {
            ajaxRequst($(".nextIssue").text())
        },
        1e3)
    }
},
tools.bannerImg = function(t) {
    var n = "";
	for(var i in t){
		n += '<div class="swiper-slide"><a target="_blank" href="'+t[i].url+'"><img src="'+t[i].img+'" alt=""></a></div>';
	}
	var i = t.length;
    var o = '<div class="device"><div class="swiper-container"><div class="swiper-wrapper">' + n + '</div></div><div class="pagination"></div></div>';
    i <= 1 ? $("#littleimg").append(n) : $("#littleimg").append(o);
    var a = new Swiper(".swiper-container", {
        pagination: ".pagination",
        loop: !0,
        freeMode: !0,
        grabCursor: !0,
        paginationClickable: !0,
        autoplay: 4500,
        effect: "fade",
        fade: {
            crossFade: !0
        },
        autoplayDisableOnInteraction: !1,
        onSlideChangeStart: function(t) {
            $(".device").css("background-color", "#fff")
        }
    });
    $(".arrow-left").on("click",
    function(t) {
        t.preventDefault(),
        a.swipePrev()
    }),
    $(".arrow-right").on("click",
    function(t) {
        t.preventDefault(),
        a.swipeNext()
    })
},
tools.advertisImg = function(t) {
    if ($("#advertisebox").length > 0) {
        var e = function(t, e) {
            return '<div><a class="logobox guanggao1" target="_blank" href="' + t + '"><img src="' + e + '" alt="" /></a></div>'
        };
        $("#advertisebox").empty(),
        $(t).each(function() {
            $("#advertisebox").append(e(this.link, config.imgUrl + "" + this.image))
        })
    }
    try {
        "" != t[0].noticeContent && void 0 != t[0].noticeContent ? $("#noticeContent").text(t[0].noticeContent) : $("#hotbox").hide()
    } catch(t) {
        $("#hotbox").hide()
    }
},
tools.browserRedirect = function() {

},
tools.countDown = function(t, e, i) {
    var n = i.replace("#", "."),
    o = t.replace("-", "/"),
    e = e.replace("-", "/");
    o = o.replace("-", "/"),
    e = e.replace("-", "/");
    var a = $(i).find(".hour"),
    s = $(i).find(".minute"),
    d = $(i).find(".second"),
    r = $(i).find(".opentyle"),
    l = $(i).find(".cuttime"),
    c = $(n).find(".hour"),
    f = $(n).find(".minute"),
    m = $(n).find(".second"),
    u = $(n).find(".opentyle"),
    x = $(n).find(".cuttime"),
    h = (new Date(o) - new Date(e)) / 1e3,
    p = !0,
    g = new Date,
    b = setInterval(function() {
        var t = Math.abs(new Date - g) / 1e3;
        if (g = new Date, (t = t.toString().split("."))[0] > 1 && (h -= t[0]), p ? (p = !1, tools.playSound("begin")) : tools.playSound(h), h > 1) {
            h -= 1;
            var e = Math.floor(h / 3600),
            o = Math.floor(h / 60 % 60),
            w = Math.floor(h % 60);
            $(a).text(e < 10 ? "0" + e: e),
            $(s).text(o < 10 ? "0" + o: o),
            $(d).text(w < 10 ? "0" + w: w),
            $(c).text(e < 10 ? "0" + e: e),
            $(f).text(o < 10 ? "0" + o: o),
            $(m).text(w < 10 ? "0" + w: w),
            e <= 0 ? ($(i).find(".hourtxt").hide(), $(a).hide(), $(n).find(".hourtxt").hide(), $(c).hide()) : ($(i).find(".hourtxt").css('display','inline-block'), $(a).css('display','inline-block'), $(n).find(".hourtxt").css('display','inline-block'), $(c).css('display','inline-block')),
            $(r).hide(),
            $(l).css({
                display: "inline-block"
            }),
            $(u).hide(),
            $(x).css({
                display: "inline-block"
            })
        } else $(r).show(),
        $(l).hide(),
        $(u).show(),
        $(x).hide(),
        clearInterval(b),
        setTimeout(function() {
            ajaxRequst($(i).find(".nextIssue").text(), i)
        },
        1e3)
    },
    1e3)
},
tools.ifCheckedOnToday = function() {
    var t = null;
    if (!$("#dateframe").length < 1) {
        var e = $(".listheadrl").find(".checked"),
        i = $("#dateframe").find(".date").val();
        t = "today" == $(e).attr("id") && i == tools.getDate()
    } else t = !0;
    return ! (!t || !config.firstLoad)
},
tools.getDate = function() {
    var t = new Date,
    e = t.getFullYear(),
    i = t.getMonth() + 1,
    n = t.getDate();
    t.getHours(),
    t.getMinutes(),
    t.getSeconds();
    return e + "-" + i + "-" + n
},
tools.insertVideo = function() {
    var t = 3600 * $("#pk10 .cuttime").find(".hour").text() + 60 * $("#pk10 .cuttime").find(".minute").text() + 1 * $("#pk10 .cuttime").find(".second").text() - 1;
    "-1" == t && (t = 0),
    $("iframe")[0].contentWindow.startcountdown(t);
    var e = "",
    i = $("#pk10 #jnumber").find("li");
    $(i).each(function() {
        e += $(this).text() + ","
    });
    var n = null;
    n = e.length < 11 ? "5,6,3,4,8,7,9,10,2,1": e.substring(0, e.length - 1),
    $("iframe")[0].contentWindow.showcurrentresult(n),
    $(".animate iframe").contents().find("#currentdrawid").text($("#pk10").find(".drawCount").text()),
    $(".animate iframe").contents().find("#nextdrawtime").text($("#pk10").find(".preDrawIssue").text()),
    $(".animate iframe").contents().find("#stat1_1").text($("#pk10").find(".sumFS").text()),
    $(".animate iframe").contents().find("#stat1_2").text($("#pk10").find(".sumBigSamll").text()),
    $(".animate iframe").contents().find("#stat1_3").text($("#pk10").find(".sumSingleDouble").text());
    var o = $("#pk10 .longhu").find("td");
    $(".animate iframe").contents().find("#stat2_1").text($(o).eq(0).text()),
    $(".animate iframe").contents().find("#stat2_2").text($(o).eq(1).text()),
    $(".animate iframe").contents().find("#stat2_3").text($(o).eq(2).text()),
    $(".animate iframe").contents().find("#stat2_4").text($(o).eq(3).text()),
    $(".animate iframe").contents().find("#stat2_5").text($(o).eq(4).text())
},
tools.noAPP = function() {
    var t = tools.YM();
    //"绿星国际0100" == t || "绿星国际1580" == t || "绿星国际3580" == t || "绿星国际3550" == t || "绿星国际1700" == t || "绿星国际0101kai" == t || "绿星国际0102kai" == t || "绿星国际0103kai" == t || "绿星国际0104kai" == t || "绿星国际0105kai" == t ? ($(".kair_right").html("<img src='../../img/jusc.jpg'/>"), $(".fix1200").find(".backold").hide(), $(".6hch").attr("href", "//6hch001.com"), $(".zzwzdh").attr("href", "//绿星国际0100.com/html/public/home.html")) : $(".loginbox").show(),
    $(".mphone").attr("href", toM()),
    $(".miniIphone").find("a").attr("href", toM())
},
tools.clearHC = function() {
    config.version,
    $("link").attr("href");
    $("link").each(function() {
        var t = $(this).attr("href"),
        e = new Date;
        t = t + "?v=" + e.getFullYear() + e.getMonth() + 1 + e.getDate(),
        $(this).attr("href", t)
    })
},
tools.setPK10TB = function() {
    pk10jiuchuo = setInterval(function() { - 1 != $("#videobox").css("z-index") && ("00:00" != $(".animate iframe").contents().find(".countdownnum").text() ? (tools.insertVideo(), config.ifdebug && console.log("纠错....")) : config.ifdebug && (console.log("开始开奖了...."), console.log("停止纠错....")))
    },
    5e3)
},
tools.operatorTime = function(t, e) {
    var i = t.replace("-", "/"),
    e = e.replace("-", "/");
    return i = i.replace("-", "/"),
    e = e.replace("-", "/"),
    (new Date(i).getTime() - new Date(e).getTime()) / 1e3
},
tools.gxKaiBg = function(t, e) {
    for (var i = 0; i < t.length; i++) 1 == t[i] || 4 == t[i] || 7 == t[i] || 10 == t[i] || 13 == t[i] || 16 == t[i] || 19 == t[i] ? $(e).find(".gx_kajianhao").find("li").eq(i).addClass("numred") : 3 != t[i] && 6 != t[i] && 9 != t[i] && 12 != t[i] && 15 != t[i] && 18 != t[i] && 21 != t[i] || $(e).find(".gx_kajianhao").find("li").eq(i).addClass("numgreen")
},
tools.egxy28 = function(t) {
    $(t).find(".kajianhao ul").find("li:last-child").addClass("numred")
},
tools.bjkl8BagColor = function(t, e) {
    var i = t.split(",");
    i.splice(i.length - 1, 1);
    for (var n = 0; n < i.length - 1; n++) i[n] >= 41 && $(e).find(".kajianhao ul").find("li").eq(n).addClass("numWeightblue");
    $(e).find(".kajianhao ul").find("li:last-child").addClass("numOrange")
},
tools.setTimefun_k3 = function() {
    setTimeout(function() {
        if (void 0 != $("#drawTime").val() && -1 != $("#videobox").css("z-index")) {
            var t, e = "",
            i = "",
            n = ($("#cqSsc").find(".kajianhao li").length, 0),
            o = $("#cqSsc").find(".preDrawIssue").text(),
            a = parseInt(o),
            s = 0 + $(".drawCount").text(),
            d = 3600 * $("#timebox").find("hour").text() + 60 * $("#timebox").find(".minute").text() + 1 * $("#timebox").find(".second").text() - 2;
            "-1" == d && (d = 0),
            $("#cqSsc").find(".kajianhao").find("li").each(function(o) {
                e = $(this).attr("class"),
                t = e.substring(e.length - 1, e.length),
                o <= 2 && $("iframe").contents().find("#codetop").find("li").eq(o).text(t),
                i += t,
                n += parseInt(t)
            }),
            i = [].slice.call(i),
            $("iframe").contents().find(".nowDraw").text(s);
            var r = {
                preDrawCode: i,
                sumNum: n,
                sumBigSmall: sumBigSmall,
                drawIssue: a + 1,
                drawTime: drawTime,
                seconds: d
            };
            $("iframe")[0].contentWindow.k3v.stopVideo(r)
        }
    },
    2e3)
},
tools.setTimefun_ssc = function() {
    setTimeout(function() {
        if (void 0 != $("#drawTime").val() && -1 != $("#videobox").css("z-index")) {
            var t = 3600 * $("#timebox").find(".hour").text() + 60 * $("#timebox").find(".minute").text() + 1 * $("#timebox").find(".second").text() - 1;
            "-1" == t && (t = 0);
            var e = $("#cqSsc").find(".kajianhao li").text(),
            i = {
                preDrawCode: [].slice.call(e),
                id: "#numBig",
                counttime: t,
                preDrawIssue: $(".preDrawIssue").text(),
                drawTime: $("#drawTime").val(),
                sumNum: $("#sumNum").val(),
                sumSingleDouble: $("#sumSingleDouble").val(),
                sumBigSmall: $("#sumBigSmall").val(),
                dragonTiger: $("#dragonTiger").val()
            };
            $("iframe")[0].contentWindow.sscAnimateEnd(i)
        }
    },
    1e3)
},
tools.setTimefun_cqnc = function() {
    setTimeout(function() {
        if (void 0 != $("#drawTime").val() && -1 != $("#videobox").css("z-index")) {
            var t = 3600 * $("#timebox").find(".hour").text() + 60 * $("#timebox").find(".minute").text() + 1 * $("#timebox").find(".second").text() - 1;
            "-1" == t && (t = 0);
            var e = [];
            if ($("#klsf").find(".kajianhao li").each(function() {
                e.push(parseInt($(this).text()))
            }), console.log("开奖后：code数组1：" + e), e.length < 8 || NaN == e[0]) return void setTimeout(function() {
                tools.setTimefun_cqnc()
            },
            500);
            $(".preDrawIssue").text();
            var i = t;
            console.log("开奖后：code数组2：" + e),
            $("iframe")[0].contentWindow.stopanimate(e, i)
        }
    },
    1e3)
},
tools.setTimefun_shiyixw = function() {
    setTimeout(function() {
        void 0 != $("#drawTime").val() && -1 != $("#videobox").css("z-index") && $("iframe")[0].contentWindow.k3v.stopVideo(tools.getSyxwData())
    },
    1500)
},
tools.getSyxwData = function() {
    var t = $("#timebox").find(".hour").text() + ":" + $("#timebox").find(".minute").text() + ":" + $("#timebox").find(".second").text(),
    e = [];
    return $("#shiyix5").find(".kajianhao li").each(function() {
        e.push(parseInt($(this).text()))
    }),
    {
        preDrawCode: e,
        sumNum: $("#sumNum").val(),
        sumBigSmall: $("#sumBigSmall").val(),
        sumSingleDouble: $("#sumSingleDouble").val(),
        drawIssue: $(".nextIssue").text(),
        drawTime: $("#drawTime").val(),
        preDrawTime: t
    }
},
tools.setTimefun_bjkl8 = function() {
    setTimeout(function() {
            if (void 0 != $("#drawTime").val() && -1 != $("#videobox").css("z-index")) {
                var t = $("#timebox").find(".hour").text() + ":" + $("#timebox").find(".minute").text() + ":" + $("#timebox").find(".second").text(),
                    e = [];
                $("#klsf").find(".kajianhao li").each(function() {
                    e.push(parseInt($(this).text()))
                });
                var i = {
                    preDrawCode: e,
                    drawIssue: $("#preDrawIssue").val(),
                    drawTime: $("#drawTime").val(),
                    preDrawTime: t
                };
                $("iframe")[0].contentWindow.syxwV.stopVid(i)
            }
        },
        1500)
},
tools.setTimefun_pcEgg = function() {
    setTimeout(function() {
        if (void 0 != $("#drawTime").val() && -1 != $("#videobox").css("z-index")) {
            var t = $("#timebox").find(".hour").text() + ":" + $("#timebox").find(".minute").text() + ":" + $("#timebox").find(".second").text(),
            e = [];
            $("#egxy").find(".kajianhao li").each(function(t) {
                t < 3 && e.push(parseInt($(this).text()))
            });
            var i = {
                numArr: e,
                nextIssue: $(".nextIssue").text(),
                drawTime: $("#drawTime").val(),
                preDrawTime: t
            };
            $("iframe")[0].contentWindow.pcEgg.stopVid(i)
        }
    },
    1500)
},
tools.setTimefun_gdklsf = function() {
    setTimeout(function() {
        if (void 0 != $("#drawTime").val() && -1 != $("#videobox").css("z-index")) {
            var t = 3600 * $("#timebox").find("hour").text() + 60 * $("#timebox").find(".minute").text() + 1 * $("#timebox").find(".second").text() - 2,
            e = [];
            $("#klsf").find(".kajianhao li").each(function() {
                e.push(parseInt($(this).text()))
            });
            var i = {
                arr: e,
                thisIssue: $("#klsf").find(".preDrawIssue").text(),
                nextIssue: $("#klsf").find(".nextIssue").text(),
                nextTime: $("#klsf").find("#drawTime").val(),
                countdown: t
            };
            $("iframe")[0].contentWindow.fun.Trueresult(i.arr),
            $("iframe")[0].contentWindow.fun.fillHtml(i.thisIssue, i.nextIssue, i.nextTime, i.countdown)
        }
    },
    1500)
},
tools.setTimefun_gxklsf = function() {
    setTimeout(function() {
        if (void 0 != $("#drawTime").val() && -1 != $("#videobox").css("z-index")) {
            var t = $("#timebox").find("hour").text(),
            e = $("#timebox").find(".minute").text(),
            i = $("#timebox").find(".second").text(),
            n = t + ":" + e + ":" + i,
            o = [];
            $("#gxklsf").find(".kajianhao li").each(function() {
                o.push(parseInt($(this).text()))
            });
            var a = {
                numArr: o,
                thisIssue: $("#gxklsf").find(".preDrawIssue").text(),
                nextIssue: $("#gxklsf").find(".nextIssue").text(),
                drawTime: $("#gxklsf").find("#drawTime").val(),
                cutdonwTime: n
            };
            $("iframe")[0].contentWindow.gxklsf.stopVid(a)
        }
    },
    1500)
},
tools.setTimefun_fc3D = function() {
    setTimeout(function() {
        if (void 0 != $("#drawTime").val() && -1 != $("#videobox").css("z-index")) {
            var t = $("#timebox").find(".hour").text() + ":" + $("#timebox").find(".minute").text() + ":" + $("#timebox").find(".second").text(),
            e = [];
            $("#cqSsc").find(".kajianhao li").each(function() {
                e.push(parseInt($(this).text()))
            });
            var i = {
                preDrawCode: e,
                drawIssue: $("#preDrawIssue").val(),
                drawTime: $("#drawTime").val(),
                preDrawTime: t
            };
            $("iframe")[0].contentWindow.fc3d.stopVid(i)
        }
    },
    1500)
},
tools.setTimefun_fcssq = function() {
    setTimeout(function() {
        if (void 0 != $("#drawTime").val() && -1 != $("#videobox").css("z-index")) {
            var t = $("#timebox").find(".hour").text() + ":" + $("#timebox").find(".minute").text() + ":" + $("#timebox").find(".second").text(),
            e = [];
            $("#cqSsc").find(".kajianhao li").each(function() {
                e.push(parseInt($(this).text()))
            });
            var i = {
                preDrawCode: e,
                drawIssue: $("#cqSsc").find(".preDrawIssue").text(),
                drawTime: $("#cqSsc").find("#drawTime").val(),
                data: $("#cqSsc").find("#nextDate").val(),
                preDrawTime: t
            };
            $("iframe")[0].contentWindow.ssq.stopVid(i)
        }
    },
    1500)
};
var listColor = "";
tools.initListen = function() {
    $("#jrsmhmtj").find("table").css("background", "#d4d4d4"),
    $("#selectcolor").on("click", "span",
    function() {
        $(this).addClass("select").siblings().removeClass(),
        1 != $(this).children().length && (listColor = $(this).css("background-color"), $("#jrsmhmtj table tr:odd").find("td").css("background", listColor))
    })
},
tools.resetListColor = function() {
    "" != listColor && $("#jrsmhmtj table tr:odd").find("td").css("background", listColor)
},
tools.Msit = function() {
    $("#advertisebox").width()
},
tools.YM = function() {
    var t = window.location.href;
    return "www" == (t = t.split("//")[1].split("/")[0].split("."))[0] ? t = t[1] : "www" != t[0] && (t = "192" == t[0] ? "绿星国际0218": t[0]),
    t
},
tools.advertisement = function() {
    $.ajax({
        url:  "/news/api/index/get_adv/type/banner",
        type: "GET",
        dataType: "json",
        data: {
            type: "0",
            position: "1",
            sourceUrl: tools.YM()
        },
        timeout: 6e4,
        beforeSend: function() {
            $("#littleimg").html('<div class="progress" style="display: block;margin-top:30px;width:100%;text-align:center;"><img src="/img/icon/piaog.gif"></div>')
        },
        success: function(t) {
			if(t){
				$("#littleimg").empty();
				for(var i in t){
					$("#littleimg").append('<a target="_blank" href="'+t[i].url+'"><img src="'+t[i].img+'" alt=""></a>');
				}
			}else{
				$("#littleimg").empty().html("数据加载异常！");
			}
        },
        error: function(t) {
            setTimeout(tools.advertisement(), 1e3)
        },
        complete: function(t, e) {
            null
        }
    })
},
tools.WWWD = function() {
    "绿星国际1700" == tools.YM() && $("#tryplay").hide()
},
tools.getCom = function() {
    var t = window.location.hostname.split(".");
    return "192" == t[0] ? ".com": "." + t[t.length - 1]
},
tools.advertisement_index = function() {
    $.ajax({
        url:  "/news/api/index/get_adv/type/top_adv",
        type: "GET",
        dataType: "json",
        beforeSend: function() {
            $("#advertisebox").html('<div class="progress" style="display: block;margin-top:30px;width:100%;text-align:center;"><a href=""><img src="/img/icon/piaog.gif"></a></div>')
        },
        success: function(t) {
			if(t){
				var html = '<a class="logobox guanggao1" target="_blank" href="'+t[0].url+'"><img src="'+t[0].img+'" alt=""></a>';
				$('#advertisebox').html(html);
			}else{
				$("#advertisebox").empty().html("数据加载异常！");
			}
        },
        error: function(t) {
            setTimeout(tools.advertisement_index(), 1e3)
        },
        complete: function(t, e) {
            null
        }
    })
},
tools.showExplain = function() {
    $("#prep").click(function(t) {
        t.stopPropagation();
        $("#spreadDiv").height();
        var e = $("#spreadDiv").find("p").length;
        e *= $("#spreadDiv").find("p").eq(0).height();
        $("#spreadDiv").stop().animate({
            height: e,
            padding: "10px 0"
        },
        300)
    }),
    $("#spreadDiv").mouseleave(function(t) {
        t.stopPropagation();
        $("#spreadDiv").stop().animate({
            height: "28px",
            padding: "0"
        },
        300)
    })
},
tools.playVideo = function() {
    $("#startVideo").on("click",
    function() {
        ifopen || ($("#videobox").animate({
            "z-index": "19999"
        },
        200,
        function() {
            $(".content").animate({
                bottom: "50%",
                right: "50%",
                width: "880px",
                height: "640px",
                "margin-bottom": "-298px"
            },
            500,
            function() {
                $(".big").hide(),
                $(".small").show(),
                $(".animate").height("575"),
                iframe(),
                isfirthload = !1,
                tools.insertVideo(),
                tools.setPK10TB()
            })
        }), ifopen = !0, document.addEventListener("touchstart",
        function() {
            ifopen && $("iframe")[0].contentWindow.ifsund() && $("iframe")[0].contentWindow.document.getElementById("sound").play()
        },
        !1), $("iframe")[0].contentWindow.ifopen = !0)
    }),
    $("#videobox .closevideo").on("click",
    function() {
        clearInterval(pk10jiuchuo),
        $(".content").animate({
            width: "0",
            "margin-right": "0"
        },
        200,
        function() {
            $("#videobox").css({
                "z-index": "-1",
                position: "fixed"
            }),
            $("#videobox .content").css({
                width: "880px",
                "margin-right": "-440px",
                bottom: "-50%"
            })
        }),
        ifopen = !1,
        $("iframe")[0].contentWindow.ifopen = !1,
        $("iframe").contents().find("#sound").attr("src", "")
    }),
    $("#videobox .small").on("click",
    function() {
        $(".content").animate({
            right: "0",
            width: "340px",
            height: "290px",
            "margin-right": "10px",
            bottom: "0",
            "margin-bottom": "0"
        },
        200,
        function() {
            $("#videobox").css({
                position: "static"
            }),
            $(".big").show(),
            $(".small").hide(),
            $(".animate").height("290"),
            iframe(),
            $(".content").width("347px"),
            toolBoxs.isIE() || ($(".animate iframe").contents().find(".footer1,.footer2,.footer3").find("div").css({
                transform: "scale(0.85)",
                "margin-top": "6px",
                "margin-left": "0px"
            }), $(".animate iframe").contents().find(".footer2_1").css({
                transform: "scale(0.85)",
                width: "auto"
            }))
        })
    }),
    $("#videobox .big").on("click",
    function() {
        $("#videobox").animate({
            "z-index": "19999"
        },
        200,
        function() {
            $(".content").animate({
                bottom: "50%",
                right: "50%",
                height: "640px",
                "margin-right": "-440px",
                width: "880px",
                "margin-bottom": "-298px"
            },
            300,
            function() {
                $("#videobox").css({
                    position: "fixed"
                }),
                $(".big").hide(),
                $(".small").show(),
                $(".animate").height("575"),
                iframe()
            })
        })
    })
},
tools.playVideo_SSC = function() {
    $("#startVideo").on("click",
    function() {
        $("iframe")[0].contentWindow.ifopen || ($("#videobox").animate({
            "z-index": "19999"
        },
        200,
        function() {
            $(".content").animate({
                bottom: "50%",
                right: "50%",
                width: "880px",
                height: "630px",
                "margin-bottom": "-298px"
            },
            500,
            function() {
                $(".big").hide(),
                $(".small").show(),
                $(".animate").height("575");
                var t = 3600 * $("#timebox").find(".hour").text() + 60 * $("#timebox").find(".minute").text() + 1 * $("#timebox").find(".second").text() - 1;
                "-1" == t && (t = 0);
                var e = $("#cqSsc").find(".kajianhao li").text(),
                i = {
                    preDrawCode: [].slice.call(e),
                    id: "#numBig",
                    counttime: t,
                    preDrawIssue: $(".preDrawIssue").text(),
                    drawTime: $("#drawTime").val(),
                    sumNum: $("#sumNum").val(),
                    sumSingleDouble: $("#sumSingleDouble").val(),
                    sumBigSmall: $("#sumBigSmall").val(),
                    dragonTiger: $("#dragonTiger").val()
                };
                $("iframe")[0].contentWindow.sscAnimateEnd(i)
            })
        }), $("iframe")[0].contentWindow.ifopen = !0)
    }),
    document.addEventListener("touchstart",
    function() {
        $("iframe")[0].contentWindow.videoTools.sounds.soundsT.play("bgsound")
    },
    !1),
    $("#videobox .closevideo").on("click",
    function() {
        $(".content").animate({
            width: "0",
            "margin-right": "0"
        },
        200,
        function() {
            $("#videobox").css({
                "z-index": "-1",
                position: "fixed"
            }),
            $("#videobox .content").css({
                width: "880px",
                "margin-right": "-440px",
                bottom: "-50%"
            }),
            $(".animate iframe").contents().find("#qishu,#nexttime,#cuttime,#btnbox").show();
            $(".animate iframe").contents().find(".bluefont").text();
            $(".animate iframe").contents().find(".bluefont").css({
                "font-size": "25px",
                left: "36%",
                top: "0"
            }),
            $(".animate iframe").contents().find(".bckj").css({
                "margin-top": "0"
            }),
            $(".animate iframe").contents().find(".ml").css({
                "font-size": "50%"
            })
        }),
        $("iframe")[0].contentWindow.videoTools.sounds.soundsT.stop("bgsound"),
        $("iframe")[0].contentWindow.videoTools.clearInterval(),
        $("iframe")[0].contentWindow.ifopen = !1
    }),
    $("#videobox .small").on("click",
    function() {
        $(".content").animate({
            right: "0",
            width: "340px",
            height: "290px",
            "margin-right": "10px",
            bottom: "0",
            "margin-bottom": "0"
        },
        200,
        function() {
            $("#videobox").css({
                position: "static"
            }),
            $(".big").show(),
            $(".small").hide(),
            $(".content").width("347px"),
            $(".animate iframe").contents().find("#qishu,#nexttime,#cuttime,#btnbox").hide();
            var t, e;
            $(".animate iframe").contents().find(".bluefont").text().length > 5 ? (t = "14px", e = "0") : (t = "20px", e = "3px"),
            $(".animate iframe").contents().find(".bluefont").css({
                "font-size": t,
                left: e,
                top: "7px"
            }),
            $(".animate iframe").contents().find(".bckj").css({
                "margin-top": "7px"
            }),
            $(".animate iframe").contents().find(".ml").css({
                "font-size": "20px"
            })
        })
    }),
    $("#videobox .big").on("click",
    function() {
        $("#videobox").animate({
            "z-index": "19999"
        },
        200,
        function() {
            $(".content").animate({
                bottom: "50%",
                right: "50%",
                height: "630px",
                "margin-right": "-440px",
                width: "880px",
                "margin-bottom": "-298px"
            },
            300,
            function() {
                $("#videobox").css({
                    position: "fixed"
                }),
                $(".big").hide(),
                $(".small").show(),
                $(".animate").height("575"),
                $(".animate iframe").contents().find("#qishu,#nexttime,#cuttime,#btnbox").show();
                $(".animate iframe").contents().find(".bluefont").text();
                $(".animate iframe").contents().find(".bluefont").css({
                    "font-size": "25px",
                    left: "36%",
                    top: "0"
                }),
                $(".animate iframe").contents().find(".bckj").css({
                    "margin-top": "25%"
                }),
                $(".animate iframe").contents().find(".ml").css({
                    "font-size": "50%"
                })
            })
        })
    })
},
tools.playVideo_klsf = function() {
    $("#startVideo").on("click",
    function() {
        $("iframe")[0].contentWindow.ifopen || ($("#videobox").animate({
            "z-index": "19999"
        },
        200,
        function() {
            $(".content").animate({
                bottom: "50%",
                right: "50%",
                width: "880px",
                height: "630px",
                "margin-bottom": "-298px"
            },
            500,
            function() {
                $(".big").hide(),
                $(".small").show(),
                $(".animate").height("575");
                var t = $("#timebox").find(".hour").text(),
                e = $("#timebox").find(".minute").text(),
                i = $("#timebox").find(".second").text();
                "..." == t && (t = 0),
                "..." == e && (e = 0),
                "..." == i && (i = 0);
                var n = 3600 * t + 60 * e + 1 * i - 2;
                "-1" != n && "-2" != n || (n = 0);
                var o = [],
                a = [],
                s = $("#klsf").find(".preDrawIssue").text(),
                d = $("#klsf").find(".nextIssue").text(),
                r = $("#klsf").find("#drawTime").val();
                $("#klsf").find(".kajianhao").find("li").each(function() {
                    o.push(parseInt($(this).text()))
                }),
                console.log("当前开奖号码数组：" + o);
                var l = {
                    arr: o,
                    thisIssue: s,
                    nextIssue: d,
                    nextTime: r,
                    countdown: n
                };
                $("iframe")[0].contentWindow.fun.stateSound(),
                0 == n ? ($("#jrsmhmtjTab").find("tr:nth-child(2)").find(".klsf_kaiul li").each(function() {
                    a.push(parseInt($(this).text()))
                }), $("iframe")[0].contentWindow.fun.onPcFillHtml(l.thisIssue, l.nextIssue, l.nextTime, a), $("iframe")[0].contentWindow.fun.moveBall()) : $("iframe")[0].contentWindow.fun.fillHtml(l.thisIssue, l.nextIssue, l.nextTime, l.countdown, l.arr)
            })
        }), $("iframe")[0].contentWindow.ifopen = !0)
    }),
    $("#videobox .closevideo").on("click",
    function() {
        $(".content").animate({
            width: "0",
            "margin-right": "0"
        },
        200,
        function() {
            $("#videobox").css({
                "z-index": "-1",
                position: "fixed"
            }),
            $("#videobox .content").css({
                width: "880px",
                "margin-right": "-440px",
                bottom: "-50%"
            }),
            $("iframe").contents().find(".video_box").css("transform", "scale(0.782)"),
            $(".animate iframe").contents().find("#qishu,#nexttime,#cuttime,#btnbox").show();
            $(".animate iframe").contents().find(".bluefont").text();
            $(".animate iframe").contents().find(".bluefont").css({
                "font-size": "25px",
                left: "36%",
                top: "0"
            }),
            $(".animate iframe").contents().find(".bckj").css({
                "margin-top": "0"
            }),
            $(".animate iframe").contents().find(".ml").css({
                "font-size": "50%"
            })
        }),
        $("iframe")[0].contentWindow.fun.ballStatic(),
        $("iframe")[0].contentWindow.bgsound.pause(),
        $("iframe")[0].contentWindow.kaisound.pause(),
        $("iframe")[0].contentWindow.ifopen = !1
    }),
    $("#videobox .small").on("click",
    function() {
        $(".content").animate({
            right: "0",
            width: "340px",
            height: "290px",
            "margin-right": "10px",
            bottom: "0",
            "margin-bottom": "0"
        },
        200,
        function() {
            $("#videobox").css({
                position: "static"
            }),
            $(".big").show(),
            $(".small").hide(),
            $(".content").width("347px"),
            $("iframe").contents().find(".video_box").css("transform", "scale(0.31)"),
            $(".animate iframe").contents().find("#qishu,#nexttime,#cuttime,#btnbox").hide();
            var t, e;
            $(".animate iframe").contents().find(".bluefont").text().length > 5 ? (t = "14px", e = "0") : (t = "20px", e = "3px"),
            $(".animate iframe").contents().find(".bluefont").css({
                "font-size": t,
                left: e,
                top: "7px"
            }),
            $(".animate iframe").contents().find(".bckj").css({
                "margin-top": "7px"
            }),
            $(".animate iframe").contents().find(".ml").css({
                "font-size": "20px"
            })
        })
    }),
    $("#videobox .big").on("click",
    function() {
        $("#videobox").animate({
            "z-index": "19999"
        },
        200,
        function() {
            $(".content").animate({
                bottom: "50%",
                right: "50%",
                height: "630px",
                "margin-right": "-440px",
                width: "880px",
                "margin-bottom": "-298px"
            },
            300,
            function() {
                $("#videobox").css({
                    position: "fixed"
                }),
                $(".big").hide(),
                $(".small").show(),
                $(".animate").height("575"),
                $("iframe").contents().find(".video_box").css("transform", "scale(0.782)"),
                $(".animate iframe").contents().find("#qishu,#nexttime,#cuttime,#btnbox").show();
                $(".animate iframe").contents().find(".bluefont").text();
                $(".animate iframe").contents().find(".bluefont").css({
                    "font-size": "25px",
                    left: "36%",
                    top: "0"
                }),
                $(".animate iframe").contents().find(".bckj").css({
                    "margin-top": "25%"
                }),
                $(".animate iframe").contents().find(".ml").css({
                    "font-size": "50%"
                })
            })
        })
    })
},
tools.playVideo_cqnc = function() {
    $("#videobox .closevideo").on("click",
    function() {
        $("iframe")[0].contentWindow.stopanimate(),
        $(".content").animate({
            width: "0",
            "margin-right": "0"
        },
        200,
        function() {
            $("#videobox").css({
                "z-index": "-1",
                position: "fixed"
            }),
            $("#videobox .content").css({
                width: "880px",
                "margin-right": "-440px",
                bottom: "-50%"
            }),
            $(".animate iframe").contents().find("#qishu,#nexttime,#cuttime,#btnbox").show();
            $(".animate iframe").contents().find(".bluefont").text();
            $(".animate iframe").contents().find(".bluefont").css({
                "font-size": "25px",
                left: "36%",
                top: "0"
            }),
            $(".animate iframe").contents().find(".bckj").css({
                "margin-top": "0"
            }),
            $(".animate iframe").contents().find(".ml").css({
                "font-size": "50%"
            })
        }),
        $("iframe")[0].contentWindow.ifopen = !1
    }),
    $("#videobox .small").on("click",
    function() {
        $(".content").animate({
            right: "0",
            width: "340px",
            height: "290px",
            "margin-right": "10px",
            bottom: "0",
            "margin-bottom": "0"
        },
        200,
        function() {
            $("#videobox").css({
                position: "static"
            }),
            $(".big").show(),
            $(".small").hide(),
            $(".content").width("347px"),
            $("iframe").contents().find(".content").css("transform", "scale(0.31)"),
            $(".animate iframe").contents().find("#qishu,#nexttime,#cuttime,#btnbox").hide();
            var t, e;
            $(".animate iframe").contents().find(".bluefont").text().length > 5 ? (t = "14px", e = "0") : (t = "20px", e = "3px"),
            $(".animate iframe").contents().find(".bluefont").css({
                "font-size": t,
                left: e,
                top: "7px"
            }),
            $(".animate iframe").contents().find(".bckj").css({
                "margin-top": "7px"
            }),
            $(".animate iframe").contents().find(".ml").css({
                "font-size": "20px"
            })
        })
    }),
    $("#videobox .big").on("click",
    function() {
        $("#videobox").animate({
            "z-index": "19999"
        },
        200,
        function() {
            $(".content").animate({
                bottom: "50%",
                right: "50%",
                height: "630px",
                "margin-right": "-440px",
                width: "880px",
                "margin-bottom": "-298px"
            },
            300,
            function() {
                $("#videobox").css({
                    position: "fixed"
                }),
                $(".big").hide(),
                $(".small").show(),
                $(".animate").height("575"),
                $("iframe").contents().find(".content").css("transform", "scale(0.782)"),
                $(".animate iframe").contents().find("#qishu,#nexttime,#cuttime,#btnbox").show();
                $(".animate iframe").contents().find(".bluefont").text();
                $(".animate iframe").contents().find(".bluefont").css({
                    "font-size": "25px",
                    left: "36%",
                    top: "0"
                }),
                $(".animate iframe").contents().find(".bckj").css({
                    "margin-top": "25%"
                }),
                $(".animate iframe").contents().find(".ml").css({
                    "font-size": "50%"
                })
            })
        })
    })
},
tools.playVideo_GXklsf = function() {
    $("#startVideo").on("click",
    function() {
        $("iframe")[0].contentWindow.ifopen || ($("#videobox").animate({
            "z-index": "19999"
        },
        200,
        function() {
            $(".content").animate({
                bottom: "50%",
                right: "50%",
                width: "880px",
                height: "630px",
                "margin-bottom": "-298px"
            },
            500,
            function() {
                $(".big").hide(),
                $(".small").show(),
                $(".animate").height("575");
                var t = $("#timebox").find(".hour").text() + ":" + $("#timebox").find(".minute").text() + ":" + $("#timebox").find(".second").text(),
                e = [],
                i = $("#gxklsf").find(".preDrawIssue").text(),
                n = $("#gxklsf").find(".nextIssue").text(),
                o = $("#gxklsf").find("#drawTime").val();
                $("#gxklsf").find(".kajianhao").find("li").each(function() {
                    e.push(parseInt($(this).text()))
                }),
                console.log("当前开奖号码数组：" + e);
                var a = {
                    numArr: e,
                    thisIssue: i,
                    nextIssue: n,
                    drawTime: o,
                    cutdonwTime: t
                };
                $("iframe")[0].contentWindow.gxklsf.startVid(a)
            })
        }), $("iframe")[0].contentWindow.ifopen = !0)
    }),
    document.addEventListener("touchstart",
    function() {
        $("iframe")[0].contentWindow.gxklsf.sound.stop("audioidKai")
    },
    !1),
    $("#videobox .closevideo").on("click",
    function() {
        $(".content").animate({
            width: "0",
            "margin-right": "0"
        },
        200,
        function() {
            $("#videobox").css({
                "z-index": "-1",
                position: "fixed"
            }),
            $("#videobox .content").css({
                width: "880px",
                "margin-right": "-440px",
                bottom: "-50%"
            }),
            $("iframe").contents().find(".content").css("transform", "scale(0.782)"),
            $(".animate iframe").contents().find("#qishu,#nexttime,#cuttime,#btnbox").show();
            $(".animate iframe").contents().find(".bluefont").text();
            $(".animate iframe").contents().find(".bluefont").css({
                "font-size": "25px",
                left: "36%",
                top: "0"
            }),
            $(".animate iframe").contents().find(".bckj").css({
                "margin-top": "0"
            }),
            $(".animate iframe").contents().find(".ml").css({
                "font-size": "50%"
            })
        }),
        $("iframe")[0].contentWindow.gxklsf.sound.stop("audioidBg"),
        $("iframe")[0].contentWindow.gxklsf.sound.stop("audioidKai"),
        $("iframe")[0].contentWindow.ifopen = !1,
        $("iframe")[0].contentWindow.gxklsf.clearTime()
    }),
    $("#videobox .small").on("click",
    function() {
        $(".content").animate({
            right: "0",
            width: "340px",
            height: "290px",
            "margin-right": "10px",
            bottom: "0",
            "margin-bottom": "0"
        },
        200,
        function() {
            $("#videobox").css({
                position: "static"
            }),
            $(".big").show(),
            $(".small").hide(),
            $(".content").width("347px"),
            $("iframe").contents().find(".content").css("transform", "scale(0.31)"),
            $(".animate iframe").contents().find("#qishu,#nexttime,#cuttime,#btnbox").hide();
            var t, e;
            $(".animate iframe").contents().find(".bluefont").text().length > 5 ? (t = "14px", e = "0") : (t = "20px", e = "3px"),
            $(".animate iframe").contents().find(".bluefont").css({
                "font-size": t,
                left: e,
                top: "7px"
            }),
            $(".animate iframe").contents().find(".bckj").css({
                "margin-top": "7px"
            }),
            $(".animate iframe").contents().find(".ml").css({
                "font-size": "20px"
            })
        })
    }),
    $("#videobox .big").on("click",
    function() {
        $("#videobox").animate({
            "z-index": "19999"
        },
        200,
        function() {
            $(".content").animate({
                bottom: "50%",
                right: "50%",
                height: "630px",
                "margin-right": "-440px",
                width: "880px",
                "margin-bottom": "-298px"
            },
            300,
            function() {
                $("#videobox").css({
                    position: "fixed"
                }),
                $(".big").hide(),
                $(".small").show(),
                $(".animate").height("575"),
                $("iframe").contents().find(".content").css("transform", "scale(0.782)"),
                $(".animate iframe").contents().find("#qishu,#nexttime,#cuttime,#btnbox").show();
                $(".animate iframe").contents().find(".bluefont").text();
                $(".animate iframe").contents().find(".bluefont").css({
                    "font-size": "25px",
                    left: "36%",
                    top: "0"
                }),
                $(".animate iframe").contents().find(".bckj").css({
                    "margin-top": "25%"
                }),
                $(".animate iframe").contents().find(".ml").css({
                    "font-size": "50%"
                })
            })
        })
    })
},
tools.playVideo_BJKL8 = function() {
    $("#startVideo").on("click",
    function() {
        $("iframe")[0].contentWindow.ifopen || ($("#videobox").animate({
            "z-index": "19999"
        },
        200,
        function() {
            $(".content").animate({
                bottom: "50%",
                right: "50%",
                width: "880px",
                height: "630px",
                "margin-bottom": "-298px"
            },
            500,
            function() {
                $(".big").hide(),
                $(".small").show(),
                $(".animate").height("575"),
                $("iframe").contents().find(".content").css("transform", "scale(0.782)");
                var t = $("#timebox").find(".hour").text(),
                e = $("#timebox").find(".minute").text(),
                i = $("#timebox").find(".second").text(),
                n = t + ":" + e + ":" + i,
                o = [],
                a = [];
                "..." == t && (t = 0),
                "..." == e && (e = 0),
                "..." == i && (i = 0);
                var s = 3600 * t + 60 * e + 1 * i - 2;
                "-1" != s && "-2" != s || (s = 0),
                $("#klsf").find(".kajianhao li").each(function() {
                    o.push(parseInt($(this).text()))
                });
                var d = {
                    preDrawCode: o,
                    drawIssue: $("#preDrawIssue").val(),
                    drawTime: $("#drawTime").val(),
                    preDrawTime: n
                };
                0 == s ? ($("#jrsmhmtjTab").find("tr:nth-child(2)").find("ul li").each(function() {
                    a.push(parseInt($(this).text()))
                }), $("iframe")[0].contentWindow.syxwV.pcFillData(d.drawIssue, d.drawTime, a, 1), $("iframe")[0].contentWindow.syxwV.intoKai(), $("iframe")[0].contentWindow.syxwV.startFun()) : $("iframe")[0].contentWindow.syxwV.startVid(d)
            })
        }), $("iframe")[0].contentWindow.ifopen = !0)
    }),
    $("#videobox .closevideo").on("click",
    function() {
        config.ifScalse = .782,
        $(".content").animate({
            width: "0",
            "margin-right": "0"
        },
        200,
        function() {
            $("#videobox").css({
                "z-index": "-1",
                position: "fixed"
            }),
            $("#videobox .content").css({
                width: "880px",
                "margin-right": "-440px",
                bottom: "-50%"
            }),
            $("iframe").contents().find(".content").css("transform", "scale(0.782)"),
            $(".animate iframe").contents().find("#qishu,#nexttime,#cuttime,#btnbox").show();
            $(".animate iframe").contents().find(".bluefont").text();
            $(".animate iframe").contents().find(".bluefont").css({
                "font-size": "25px",
                left: "36%",
                top: "0"
            }),
            $(".animate iframe").contents().find(".bckj").css({
                "margin-top": "0"
            }),
            $(".animate iframe").contents().find(".ml").css({
                "font-size": "50%"
            })
        }),
        $("iframe")[0].contentWindow.syxwV.sound.stop("audioidBg"),
        $("iframe")[0].contentWindow.syxwV.sound.stop("audioidKai"),
        $("iframe")[0].contentWindow.syxwV.clearTime(),
        $("iframe")[0].contentWindow.ifopen = !1
    }),
    $("#videobox .small").on("click",
    function() {
        config.ifScalse = .31,
        $(".content").animate({
            right: "0",
            width: "340px",
            height: "290px",
            "margin-right": "10px",
            bottom: "0",
            "margin-bottom": "0"
        },
        200,
        function() {
            $("#videobox").css({
                position: "static"
            }),
            $(".big").show(),
            $(".small").hide(),
            $(".content").width("347px"),
            $("iframe").contents().find(".content").css("transform", "scale(0.31)"),
            $(".animate iframe").contents().find("#qishu,#nexttime,#cuttime,#btnbox").hide();
            var t, e;
            $(".animate iframe").contents().find(".bluefont").text().length > 5 ? (t = "14px", e = "0") : (t = "20px", e = "3px"),
            $(".animate iframe").contents().find(".bluefont").css({
                "font-size": t,
                left: e,
                top: "7px"
            }),
            $(".animate iframe").contents().find(".bckj").css({
                "margin-top": "7px"
            }),
            $(".animate iframe").contents().find(".ml").css({
                "font-size": "20px"
            })
        })
    }),
    $("#videobox .big").on("click",
    function() {
        config.ifScalse = .782,
        $("#videobox").animate({
            "z-index": "19999"
        },
        200,
        function() {
            $(".content").animate({
                bottom: "50%",
                right: "50%",
                height: "630px",
                "margin-right": "-440px",
                width: "880px",
                "margin-bottom": "-298px"
            },
            300,
            function() {
                $("#videobox").css({
                    position: "fixed"
                }),
                $(".big").hide(),
                $(".small").show(),
                $(".animate").height("575"),
                $("iframe").contents().find(".content").css("transform", "scale(0.782)"),
                $(".animate iframe").contents().find("#qishu,#nexttime,#cuttime,#btnbox").show();
                $(".animate iframe").contents().find(".bluefont").text();
                $(".animate iframe").contents().find(".bluefont").css({
                    "font-size": "25px",
                    left: "36%",
                    top: "0"
                }),
                $(".animate iframe").contents().find(".bckj").css({
                    "margin-top": "25%"
                }),
                $(".animate iframe").contents().find(".ml").css({
                    "font-size": "50%"
                })
            })
        })
    })
},
tools.playVideo_kuai3 = function() {
    $("#startVideo").on("click",
    function() {
        $("iframe")[0].contentWindow.ifopen || ($("#videobox").animate({
            "z-index": "19999"
        },
        200,
        function() {
            $(".content").animate({
                bottom: "50%",
                right: "50%",
                width: "880px",
                height: "630px",
                "margin-bottom": "-298px"
            },
            500,
            function() {
                $(".big").hide(),
                $(".small").show(),
                $(".animate").height("575");
                var t, e = "",
                i = "",
                n = ($("#cqSsc").find(".kajianhao li").length, 0),
                o = $("#cqSsc").find(".preDrawIssue").text(),
                a = parseInt(o),
                s = 0 + $(".drawCount").text(),
                d = 3600 * $("#timebox").find("hour").text() + 60 * $("#timebox").find(".minute").text() + 1 * $("#timebox").find(".second").text() - 2;
                "-1" == d && (d = 0),
                $("#cqSsc").find(".kajianhao").find("li").each(function(o) {
                    e = $(this).attr("class"),
                    t = e.substring(e.length - 1, e.length),
                    o <= 2 && $("iframe").contents().find("#codetop").find("li").eq(o).text(t),
                    i += t,
                    n += parseInt(t)
                });
                var r = n;
                i = [].slice.call(i),
                $("iframe").contents().find(".nowDraw").text(s);
                var l = {
                    preDrawCode: i,
                    sumNum: n,
                    sumBigSmall: r,
                    drawIssue: a + 1,
                    drawTime: drawTime,
                    seconds: d
                };
                $("iframe")[0].contentWindow.k3v.stopVideo(l)
            })
        }), $("iframe")[0].contentWindow.ifopen = !0)
    }),
    document.addEventListener("touchstart",
    function() {
        $("iframe")[0].contentWindow.k3v.sound.stop("audioid")
    },
    !1),
    $("#videobox .closevideo").on("click",
    function() {
        $(".content").animate({
            width: "0",
            "margin-right": "0"
        },
        200,
        function() {
            $("#videobox").css({
                "z-index": "-1",
                position: "fixed"
            }),
            $("#videobox .content").css({
                width: "880px",
                "margin-right": "-440px",
                bottom: "-50%"
            }),
            $("iframe").contents().find(".content").css("transform", "scale(0.782)"),
            $(".animate iframe").contents().find("#qishu,#nexttime,#cuttime,#btnbox").show();
            $(".animate iframe").contents().find(".bluefont").text();
            $(".animate iframe").contents().find(".bluefont").css({
                "font-size": "25px",
                left: "36%",
                top: "0"
            }),
            $(".animate iframe").contents().find(".bckj").css({
                "margin-top": "0"
            }),
            $(".animate iframe").contents().find(".ml").css({
                "font-size": "50%"
            })
        }),
        $("iframe")[0].contentWindow.k3v.sound.stop("audioid"),
        $("iframe")[0].contentWindow.ifopen = !1
    }),
    $("#videobox .small").on("click",
    function() {
        $(".content").animate({
            right: "0",
            width: "340px",
            height: "290px",
            "margin-right": "10px",
            bottom: "0",
            "margin-bottom": "0"
        },
        200,
        function() {
            $("#videobox").css({
                position: "static"
            }),
            $(".big").show(),
            $(".small").hide(),
            $(".content").width("347px"),
            $("iframe").contents().find(".content").css("transform", "scale(0.31)"),
            $(".animate iframe").contents().find("#qishu,#nexttime,#cuttime,#btnbox").hide();
            var t, e;
            $(".animate iframe").contents().find(".bluefont").text().length > 5 ? (t = "14px", e = "0") : (t = "20px", e = "3px"),
            $(".animate iframe").contents().find(".bluefont").css({
                "font-size": t,
                left: e,
                top: "7px"
            }),
            $(".animate iframe").contents().find(".bckj").css({
                "margin-top": "7px"
            }),
            $(".animate iframe").contents().find(".ml").css({
                "font-size": "20px"
            })
        })
    }),
    $("#videobox .big").on("click",
    function() {
        $("#videobox").animate({
            "z-index": "19999"
        },
        200,
        function() {
            $(".content").animate({
                bottom: "50%",
                right: "50%",
                height: "630px",
                "margin-right": "-440px",
                width: "880px",
                "margin-bottom": "-298px"
            },
            300,
            function() {
                $("#videobox").css({
                    position: "fixed"
                }),
                $(".big").hide(),
                $(".small").show(),
                $(".animate").height("575"),
                $("iframe").contents().find(".content").css("transform", "scale(0.782)"),
                $(".animate iframe").contents().find("#qishu,#nexttime,#cuttime,#btnbox").show();
                $(".animate iframe").contents().find(".bluefont").text();
                $(".animate iframe").contents().find(".bluefont").css({
                    "font-size": "25px",
                    left: "36%",
                    top: "0"
                }),
                $(".animate iframe").contents().find(".bckj").css({
                    "margin-top": "25%"
                }),
                $(".animate iframe").contents().find(".ml").css({
                    "font-size": "50%"
                })
            })
        })
    })
},
tools.playVideo_shiyix5 = function() {
    $("#startVideo").on("click",
    function() {
        $("iframe")[0].contentWindow.ifopen || ($("#videobox").animate({
            "z-index": "19999"
        },
        200,
        function() {
            $(".content").animate({
                bottom: "50%",
                right: "50%",
                width: "880px",
                height: "630px",
                "margin-bottom": "-298px"
            },
            500,
            function() {
                $(".big").hide(),
                $(".small").show(),
                $(".animate").height("575"),
                "block" == $(".small").css("display") && $("iframe").contents().find(".content").css("transform", "scale(0.782)");
                var t = $("#timebox").find(".hour").text(),
                e = $("#timebox").find(".minute").text(),
                i = $("#timebox").find(".second").text(),
                n = t + ":" + e + ":" + i,
                o = 3600 * t + 60 * e + 1 * i - 2;
                "-1" != o && "-2" != o || (o = 0);
                var a = [],
                s = [];
                $("#shiyix5").find(".kajianhao li").each(function() {
                    a.push(parseInt($(this).text()))
                });
                var d = {
                    preDrawCode: a,
                    sumNum: $("#sumNum").val(),
                    sumBigSmall: $("#sumBigSmall").val(),
                    sumSingleDouble: $("#sumSingleDouble").val(),
                    drawIssue: $(".nextIssue").text(),
                    drawTime: $("#drawTime").val(),
                    preDrawTime: n
                };
                0 == o ? ($("#jrsmhmtjTab").find("tr:nth-child(2)").find(".blueqiu").find("ul li").each(function() {
                    s.push(parseInt($(this).text()))
                }), $("iframe")[0].contentWindow.k3v.defStartVideo(d.drawIssue, d.drawTime, d.sumNum, d.sumBigSmall, d.sumSingleDouble, s)) : $("iframe")[0].contentWindow.k3v.startVideo(d)
            })
        }), $("iframe")[0].contentWindow.ifopen = !0)
    }),
    $("#videobox .closevideo").on("click",
    function() {
        $(".content").animate({
            width: "0",
            "margin-right": "0"
        },
        200,
        function() {
            $("#videobox").css({
                "z-index": "-1",
                position: "fixed"
            }),
            $("#videobox .content").css({
                width: "880px",
                "margin-right": "-440px",
                bottom: "-50%"
            }),
            $(".animate iframe").contents().find("#qishu,#nexttime,#cuttime,#btnbox").show();
            $(".animate iframe").contents().find(".bluefont").text();
            $(".animate iframe").contents().find(".bluefont").css({
                "font-size": "25px",
                left: "36%",
                top: "0"
            }),
            $(".animate iframe").contents().find(".bckj").css({
                "margin-top": "0"
            }),
            $(".animate iframe").contents().find(".ml").css({
                "font-size": "50%"
            })
        }),
        $("iframe")[0].contentWindow.k3v.sound.stop("audioidB"),
        $("iframe")[0].contentWindow.k3v.sound.stop("audioidR"),
        $("iframe")[0].contentWindow.ifopen = !1,
        $("iframe")[0].contentWindow.k3v.clearTime()
    }),
    $("#videobox .small").on("click",
    function() {
        $(".content").animate({
            right: "0",
            width: "340px",
            height: "290px",
            "margin-right": "10px",
            bottom: "0",
            "margin-bottom": "0"
        },
        200,
        function() {
            $("#videobox").css({
                position: "static"
            }),
            $(".big").show(),
            $(".small").hide(),
            $(".content").width("347px"),
            $("iframe").contents().find(".content").css("transform", "scale(0.31)"),
            $(".animate iframe").contents().find("#qishu,#nexttime,#cuttime,#btnbox").hide();
            var t, e;
            $(".animate iframe").contents().find(".bluefont").text().length > 5 ? (t = "14px", e = "0") : (t = "20px", e = "3px"),
            $(".animate iframe").contents().find(".bluefont").css({
                "font-size": t,
                left: e,
                top: "7px"
            }),
            $(".animate iframe").contents().find(".bckj").css({
                "margin-top": "7px"
            }),
            $(".animate iframe").contents().find(".ml").css({
                "font-size": "20px"
            })
        })
    }),
    $("#videobox .big").on("click",
    function() {
        $("#videobox").animate({
            "z-index": "19999"
        },
        200,
        function() {
            $(".content").animate({
                bottom: "50%",
                right: "50%",
                height: "630px",
                "margin-right": "-440px",
                width: "880px",
                "margin-bottom": "-298px"
            },
            300,
            function() {
                $("#videobox").css({
                    position: "fixed"
                }),
                $(".big").hide(),
                $(".small").show(),
                $(".animate").height("575"),
                $("iframe").contents().find(".content").css("transform", "scale(0.782)"),
                $(".animate iframe").contents().find("#qishu,#nexttime,#cuttime,#btnbox").show();
                $(".animate iframe").contents().find(".bluefont").text();
                $(".animate iframe").contents().find(".bluefont").css({
                    "font-size": "25px",
                    left: "36%",
                    top: "0"
                }),
                $(".animate iframe").contents().find(".bckj").css({
                    "margin-top": "25%"
                }),
                $(".animate iframe").contents().find(".ml").css({
                    "font-size": "50%"
                })
            })
        })
    })
};
$('.haomaqur_l').append('<a target="_blank" href="https://kfc8002.com/?a=23329"><i class="lijitouzhu">立即投注</i></a>');