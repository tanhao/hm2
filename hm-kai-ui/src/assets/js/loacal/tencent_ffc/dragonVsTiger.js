$(function() {
    //关闭加载动画
    animateMethod.loadingList("#jrsmhmtj", true);
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
        //console.log($(this).scrollTop())
        if($(this).scrollTop() > 10) {
            $("#gotop").show();
        } else {
            $("#gotop").hide();
        }
    });

    //初始化时间控件
    $('#datebox').calendar({
        trigger: '.date',
        zIndex: 999,
        format: 'yyyy-mm-dd',
        onSelected: function(view, date, data) {
            var date = formatDate(date);
            loadotherData(date);
            tabarr = [];
            $("#chakanchfb,#daxiaodsfb").find("li").removeClass("selected");
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
    //定义统一透明样式兼容IE8
    function opacity(obj, num, filt) {
        obj.style.filter = "alpha(opacity:" + filt + ")";
        obj.style.opacity = num;
    }

    //启动加载数据
    //getData();
    //listData(); //重新请求list数据
});

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
//构造table数据
//加载数据
function loadotherData(date) {
    listData(date);
}
//请求list数据
function listData(date) {
    date = date == undefined ? "" : date;
    $.ajax({
        url: urlbublic + "CQShiCai/getLongHuDouList.do?date=" + date,
        type: "GET",
        data: {
            "lotCode": lotCode
        },
        success: function(data) {
            createHtmlList(data);
            //关闭加载动画
            animateMethod.loadingList("#jrsmhmtj", false);
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
//当出现故障时5秒请求一次
var reloadotherData = null;

function clearinterval(interval) {
    clearInterval(intervalPk10);
}

var listdata = {};
//构建开奖记录数据
function createHtmlList(jsondata) {
    //jsondata = listdata;
    var data = null;
    if(typeof jsondata != "object") {
        data = JSON.parse(jsondata);
    } else {
        data = JSON.stringify(jsondata);
        data = JSON.parse(data);
    }
    data = data.result.data;
    var drawCode = "";
    $("#jrsmhmtj>table").empty();
    $("#jrsmhmtj>table").html('<tr><th rowspan="2" width="13%">时间</th><th  rowspan="2" width="11%"> 期数</th><th id="numberbtn"  class="numberbtn" colspan="5">开奖号码</th><th colspan="10" width="44%">龙虎斗专栏</th></tr><tr><th>第 1 球</th><th>第 2 球</th><th>第 3 球</th><th>第 4 球</th><th>第 5 球</th> <th>1V2</th><th>1V3</th><th>1V4</th><th>1V5</th><th>2V3</th><th>2V4</th><th>2V5</th><th>3V4</th><th>3V5</th><th>4V5</th></tr>');
    $(data).each(function(index) {
        var kaitim = this.preDrawTime.slice(0, this.preDrawTime.length - 3).split(" ");
        kaitim = kaitim[0] + "&nbsp;&nbsp;&nbsp;" + kaitim[1];
        var tddate = "<td>" + kaitim + "</td><td>" + this.preDrawIssue + "</td>";
        var tdli = "";
        var preDrawCode = (this.preDrawCode).split(",");
        $(preDrawCode).each(function() {
            tdli += "<td  style='color:#012537'><i class='sscnumblue'>" + this + "</i></td>";
        });
        var stylestr = "style='color:";
        var arr = [];
        var styl = [];
        var dragonvstigerText = "";
        arr[0] = typeOf("lhh", this.oneVsTwo);
        arr[1] = typeOf("lhh", this.oneVsThree);
        arr[2] = typeOf("lhh", this.oneVsFour);
        arr[3] = typeOf("lhh", this.oneVsFive);
        arr[4] = typeOf("lhh", this.twoVsThree);
        arr[5] = typeOf("lhh", this.twoVsFour);
        arr[6] = typeOf("lhh", this.twoVsFive);
        arr[7] = typeOf("lhh", this.threeVsFour);
        arr[8] = typeOf("lhh", this.threeVsFive);
        arr[9] = typeOf("lhh", this.fourVsFive);
        for(var i = 0; i < arr.length; i++) {
            if(arr[i] == "龙") {
                styl[i] = stylestr + "#f12d35'";
            } else if(arr[i] == "和") {
                styl[i] = stylestr + "#666666'";
            } else {
                styl[i] = stylestr + "#184DD5'";
            }
            dragonvstigerText += "<td " + styl[i] + ">" + arr[i] + "</td>"
        }
        var tr = "<tr>" + tddate + "" + tdli + "" + dragonvstigerText + "</tr>";
        $("#jrsmhmtj>table").append(tr);
    });
    $("table").find("td").each(function() {
        if($(this).text() == "undefined") {
            $(this).text("");
        }
    });
}

//类型转换
function typeOf(type, num) {
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
    } else if(type == "state") {
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
        }
    } else if(type == "san") {
        //0杂六、1半顺、2顺子、3对子、4豹子
        switch(num * 1) {
            case 0:
                return "杂六";
                break;
            case 1:
                return "半顺";
                break;
            case 2:
                return "顺子";
                break;
            case 3:
                return "对子";
                break;
            case 4:
                return "豹子";
                break;
        }
    } else if(type == "lhh") {
        //0龙、1虎、2和
        switch(num * 1) {
            case 0:
                return "龙";
                break;
            case 1:
                return "虎";
                break;
            case 2:
                return "和";
                break;
        }
    }
}
