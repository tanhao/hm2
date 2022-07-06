var publicUrl = config.publicUrl;
var kinds = {},
    funObj = {};
var lotType = "10001",
    dataArr = "",
    navId = "PK拾",
   errTimeOut="";
    var ctx = c1.getContext('2d'); // 获得画笔
$(function() {
    kinds.tabSubject(); //切换彩种系列导航
    funObj.listData_pk10(lotType);
    updateSet();
})

function updateSet() {
    var updateSeti = setInterval(function() {
        funObj.requestDate();
    }, 10000)
}
//名次切换
var rankSpan = $(".tubiaoBox .showBlock #zoushiMinc").find(".actNav").find("span").text();
//切换彩种系列
kinds.tabSubject = function() {
    $("#navBox").find("li").on("click", function() {
        rankSpan = 1;
         ctx.clearRect(0, 0, c1.width, c1.height);
         ctx.fillText("正在努力绘制...", 500, 120); // 每个点的数据
        $(this).addClass("actNavBig").siblings().removeClass("actNavBig");
        navId = $(".actNavBig").text();
        var xilieKinds = $(this).text();
        //展示不同系列的具体彩种切换
        if(xilieKinds == "快乐十分") {
            //如快乐十分和其他系列的不需要展示趋势图，只需展示静态的页面跳转
            showBlock_Model("#listModelKlsf"); // 显示“快乐十分”的彩种模块
            showDetail_Model("#detailBox #detailKlsf"); // 显示“快乐十分”链接模块
            $("#zhexiantu").addClass("displayNone");
        } else {
            if($("#zhexiantu").hasClass("displayNone")) { //如果“快乐十分”隐藏了折线图区域，则重现
                $("#zhexiantu").removeClass("displayNone");
            }
            if(xilieKinds == "PK拾") {
                showBlock_Model("#listModelPK10"); // 显示“PK拾”的彩种模块
                showDetail_Model("#detailBox #detailPK10"); // 显示“PK拾”链接模块
                funObj.listData_pk10("10001");
                recoverBallMinc("#listModelPK10"); //恢复默认第一彩种，第一球
            } else if(xilieKinds == "时时彩") {
                showBlock_Model("#listModelSSC");
                showDetail_Model("#detailBox #detailSSC");
                funObj.listData_SSC("10002");
                recoverBallMinc("#listModelSSC");
            } else if(xilieKinds == "快三") {
                showBlock_Model("#listModelKuai3");
                showDetail_Model("#detailBox #detailKuai3");
                funObj.listData_Kuai3("10007");
                recoverBallMinc("#listModelKuai3");
            } else {
                showBlock_Model("#listModelSYXW");
                showDetail_Model("#detailBox #detailSYXW");
                funObj.listData_shiYX5("10006");
                recoverBallMinc("#listModelSYXW");
            }
        }
    })
    kinds.clickCaiName(); //点击每个彩种展示排名
    kinds.rankMinCi(); //点击名次切换
    //  kinds.listContent()
}

//点击每个彩种展示排名
kinds.clickCaiName = function() {
    $(".tubiaoBox .kindsCaName").find("li").on("click", function() {
        rankSpan = 1;
        $(this).addClass("actNav").siblings().removeClass("actNav");
        $(this).find("i").addClass("iboder").parent().siblings().find("i").removeClass();
        var lotName = $(this).attr("data-text");
        $(".tubiaoBox .showBlock #zoushiMinc").find("li").eq(0).addClass("actNav").siblings().removeClass("actNav");
        lotType = lotCode[lotName];
        if(navId == "PK拾") { //pk10
            funObj.listData_pk10(lotType);
        } else if(navId == "时时彩") { //时时彩
            funObj.listData_SSC(lotType);
        } else if(navId == "11选5") {
            funObj.listData_shiYX5(lotType); //11选5
        } else if(navId == "快三") {
            funObj.listData_Kuai3(lotType); //快三
        };
         ctx.clearRect(0, 0, c1.width, c1.height);
         ctx.fillText("正在努力绘制...", 500, 120); // 每个点的数据
    })
}
kinds.rankMinCi = function() {
    $(".tubiaoBox #zoushiMinc").find("li").on("click", function() {
        $(this).addClass("actNav").siblings().removeClass("actNav");
        rankSpan = $(this).find("span").text();
        //根据彩种和名字请求请求接口
        if(navId == "PK拾") { //pk10
            createListData_pk10(dataArr);
        } else if(navId == "时时彩") { //时时彩
            createListData_SSC(dataArr);
        } else if(navId == "11选5") { //11选5
            createListData_shiYX5(dataArr);
        } else if(navId == "快三") { //快三
            createListData_Kuai3(dataArr);
        }
    })
}
//跳转列表展示
kinds.listContent = function() {
    $("#detailBox #detailPK10").addClass("detailPK10").siblings().removeClass("detailPK10");
}
// 显示“彩种”跳转模块
function showBlock_Model(caiId) {
    if(caiId == "#listModelKlsf") {
        $(caiId).addClass("displayNone").siblings().removeClass("showBlock");
        $(".tubiaoBox").addClass("padding0");
    } else {
        $(".tubiaoBox").removeClass("padding0");
        $(caiId).addClass("showBlock").siblings().removeClass("showBlock"); // 显示“xxx彩种”跳转模块        
    }
}
// 显示“彩种链接”模块
function showDetail_Model(caiId) {
    $(caiId).addClass("showDetail").siblings().removeClass("showDetail");
}
//回复默认第一彩种，第一名数据
function recoverBallMinc(caiId) {
    $(caiId).find("ul").each(function() {
        $(this).find("li i").removeClass();
        $(this).find("li").eq(0).find("i").addClass("iboder");
        $(this).find("li").eq(0).addClass("actNav").siblings().removeClass("actNav")
    })
}
//请求数据-------------------------------------------
//pk10数据处理
funObj.listData_pk10 = function(lotType) {
    //"periods" 最近30期 http://api.api68.com/pks/queryLocationTrend.do?date=&periods=30&lotCode=10001
    var data = {
        date: "",
        periods: 25,
        lotCode: lotType
    }
    $.ajax({
        url: publicUrl + "pks/queryLocationTrend.do",
        type: "GET",
        data: data,
        /*beforeSend: function() {
            ctx.clearRect(0, 0, c1.width, c1.height);
            ctx.fillText("正在努力绘制...", 500, 120); // 每个点的数据
        },*/
        success: function(data) {
            //处理数据
            funObj.pk10_dataDeal(data);
        },
        error: function() {
            console.log("走势图表数据请求异常")
        }
    });
}
funObj.pk10_dataDeal = function(data) {
    if(typeof(data) == "string") {
        data = JSON.parse(data);
    }
    if(data.result.data.length == 0) {
        errTimeOut = setTimeout(function() {
            funObj.requestDate()
        }, 1000);
        return false;
    }
    dataArr = data.result.data;
    createListData_pk10(dataArr);
}
//将需要的数据，组成横轴，纵轴所需的两组数据  xArrNums,yArrNums；排名1-9（data）,25期数（periodsAll）,1-20号码（numsData）
function createListData_pk10(data) {
    //  debugger
    var xArrNums, yArrNums;
    var rankMinc = rankSpan;
    $.each(data, function(i, p) {
        xArrNums = [];
        yArrNums = [];
        if(i == rankMinc - 1) {
            var periodsAll = data[i].bodyList; //前25期
            $.each(periodsAll, function(j, c) {
                var periods = "";
                var curNum = periodsAll[j].drawCode[i]; //走势第一位号码
                periods = "" + periodsAll[j].preIssue; //对应期数
                periods = periods.substring(periods.length - 3);
                xArrNums.push(periods);
                yArrNums.push(curNum);
            })
            /*console.log("期数：" + xArrNums +  "len:" + xArrNums.length);
            console.log("第"+i+"位号码：" + yArrNums +  "len:" + yArrNums.length);*/
            funObj.trendLine(xArrNums, yArrNums);
        }
    })
}
//时时彩数据处理
funObj.listData_SSC = function(lotType) {
    //"issue" 最近30期http://api.api68.com/CQShiCai/queryCQShiCaiTrendByIssue.do?issue=30&date=&lotCode=10002
    var data = {
        date: "",
        issue: 25,
        lotCode: lotType
    }
    $.ajax({
        url: publicUrl + "CQShiCai/queryCQShiCaiTrendByIssue.do",
        type: "GET",
        data: data,
       /* beforeSend: function() {
            ctx.clearRect(0, 0, c1.width, c1.height);
            ctx.fillText("正在努力绘制...", 500, 120); // 每个点的数据
        },*/
        success: function(data) {
            //处理数据
            funObj.SSC_dataDeal(data);
        },
        error: function() {
            console.log("走势图表数据请求异常")
        }
    });
}
//时时彩数据处理
funObj.SSC_dataDeal = function(data) {
    if(typeof(data) == "string") {
        data = JSON.parse(data);
    }
    if(data.result.data[0].length == 0) {
        errTimeOut = setTimeout(function() {
            funObj.requestDate()
        }, 1000);
        return false;
    }
    dataArr = data.result.data[0].bodyList;
    createListData_SSC(dataArr, 1);
}
//将需要的数据，组成横轴，纵轴所需的两组数据  xArrNums,yArrNums；排名1-9（data）,25期数（data）,1-20号码（numsData）
function createListData_SSC(data) {
    var rankMinc = rankSpan;
    var xArrNums = [];
    var yArrNums = [];
    var periods = "",
        curNum = "";
    $.each(data, function(i, p) {
        periods = "" + data[i].issue; //对应期数
        periods = periods.substring(periods.length - 3);
        curNum = data[i].code.split(",")[rankMinc - 1];
        xArrNums.push(periods);
        yArrNums.push(curNum);
    })
    //  console.log("期数：" + xArrNums +  "len:" + xArrNums.length);
    //  console.log("第"+rankMinc+"名号码：" + yArrNums +  "len:" + yArrNums.length);
    funObj.trendLine(xArrNums, yArrNums);
}
//11选5数据处理
funObj.listData_shiYX5 = function(lotType) {
    //"issue" 最近30期http://api.api68.com/ElevenFive/queryElevnFiveTrendByIssue.do?issue=30&date=&lotCode=10006
    var data = {
        date: "",
        issue: 25,
        lotCode: lotType
    }
    $.ajax({
        url: publicUrl + "ElevenFive/queryElevnFiveTrendByIssue.do",
        type: "GET",
        data: data,
       /* beforeSend: function() {
            ctx.clearRect(0, 0, c1.width, c1.height);
            ctx.fillText("正在努力绘制...", 500, 120); // 每个点的数据
        },*/
        success: function(data) {
            //处理数据
            funObj.shiYX5_dataDeal(data);
        },
        error: function() {
            console.log("走势图表数据请求异常")
        }
    });
}
funObj.shiYX5_dataDeal = function(data) {
    if(typeof(data) == "string") {
        data = JSON.parse(data);
    }
    if(data.result.data.length == 0) {
        errTimeOut = setTimeout(function() {
            funObj.requestDate()
        }, 1000);
        return false;
    }
    dataArr = data.result.data[0].bodyList;
    createListData_shiYX5(dataArr, 1);
}
//将需要的数据，组成横轴，纵轴所需的两组数据  xArrNums,yArrNums；排名1-9（data）,25期数（data）,1-20号码（numsData）
function createListData_shiYX5(data) {
    var rankMinc = rankSpan;
    var xArrNums = [];
    var yArrNums = [];
    var periods = "",
        curNum = "";
    $.each(data, function(i, p) {
        periods = "" + data[i].issue; //对应期数
        periods = periods.substring(periods.length - 3);
        curNum = data[i].code.split(",")[rankMinc - 1];
        if(curNum.substr(0, 1) == 0) {
            curNum = curNum.slice(1);
        }
        xArrNums.push(periods);
        yArrNums.push(curNum);
    })
    //  console.log("期数：" + xArrNums +  "len:" + xArrNums.length);
    //  console.log("第"+rankMinc+"名号码：" + yArrNums +  "len:" + yArrNums.length);
    funObj.trendLine(xArrNums, yArrNums);
}
//快3数据处理
funObj.listData_Kuai3 = function(lotType) {
    //http://api.api68.com/lotteryJSFastThree/queryOrientationTrend.do?date=&issue=&periods=30&lotCode=10007
    var data = {
        date: "",
        issue: "",
        periods: 25,
        lotCode: lotType
    }
    $.ajax({
        url: publicUrl + "lotteryJSFastThree/queryOrientationTrend.do",
        type: "GET",
        data: data,
        /*beforeSend: function() {
            ctx.clearRect(0, 0, c1.width, c1.height);
            ctx.fillText("正在努力绘制...", 500, 120); // 每个点的数据
        },*/
        success: function(data) {
            //处理数据
            funObj.Kuai3_dataDeal(data);
        },
        error: function() {
            console.log("走势图表数据请求异常")
        }
    });
}
funObj.Kuai3_dataDeal = function(data) {
    if(typeof(data) == "string") {
        data = JSON.parse(data);
    }
    if(data.result.data.length == 0) {
      errTimeOut = setTimeout(function() {
            funObj.requestDate()
        }, 1000);
        return false;
    }
    dataArr = data.result.data.bodyList;
    createListData_Kuai3(dataArr, 1);
}
//将需要的数据，组成横轴，纵轴所需的两组数据  xArrNums,yArrNums；排名1-9（data）,25期数（data）,1-20号码（numsData）
function createListData_Kuai3(data) {
    var rankMinc = rankSpan;
    var xArrNums = [];
    var yArrNums = [];
    var periods = "",
        curNum = "";
    $.each(data, function(i, p) {
        periods = "" + data[i].preIssue; //对应期数
        periods = periods.substring(periods.length - 3);
        curNum = data[i].drawCode[rankMinc - 1];
        xArrNums.push(periods);
        yArrNums.push(curNum);
    })
    funObj.trendLine(xArrNums, yArrNums);
}
//每10秒请求一次当前彩种接口数据
funObj.requestDate = function() {
    clearTimeout(errTimeOut)
    var navType = $("#navBox .actNavBig").text();
    var actNavType = $(".tubiaoBox .showBlock").find(".kindsCaName").find(".actNav").attr("data-text");
    //  var paiMin = $(".tubiaoBox .showBlock #zoushiMinc").find(".actNav").find("span").text();
    console.log(actNavType,lotCode[actNavType])
    if(navType == "PK拾") {
        funObj.listData_pk10("" + lotCode[actNavType]);
    } else if(navType == "时时彩") {
        funObj.listData_SSC("" + lotCode[actNavType])
    } else if(navType == "快三") {
        funObj.listData_Kuai3("" + lotCode[actNavType])
    } else { //navType == "11选5"
        funObj.listData_shiYX5("" + lotCode[actNavType])
    }
}
//画折线图
funObj.trendLine = function(bottomArr, arr) {
	bottomArr = bottomArr.reverse();
	arr = arr.reverse();
    //  var arr = [8, 2, 2, 6, 10, 5, 7, 5, 1, 3, 4, 9, 5, 9, 3, 8, 8, 4, 2, 9, 3, 8, 8, 4, 2, 9];
    //  var arr = [8, 12, 12, 6, 10, 15, 17, 5, 11, 3, 4, 9, 15, 9, 13, 8, 18, 14, 2, 19, 8, 18, 14, 2, 19];
    //  var bottomArr = [121, 122, 123, 124, 125, 126, 127, 128, 129, 130, 131, 132, 133, 134, 135, 136, 137, 138, 139, 140, 141, 142, 143, 144, 145];

    var ctx = c1.getContext('2d'); // 获得画笔
    ctx.clearRect(0, 0, c1.width, c1.height);
    //设置字体样式
    ctx.font = '14px Arial Regular';
    ctx.textBaseline = 'alphabetic';
    ctx.shadowBlur = '';
    //绘制 x y 轴主杆
    ctx.beginPath();
    ctx.strokeStyle = '#B4B4B4';
    ctx.lineWidth = 1;
    ctx.lineJoin = 'round'; //round bevel miter  --箭头样式
    ctx.moveTo(1128, 200.5);
    ctx.lineTo(40.5, 200.5);
    ctx.lineTo(40.5, 25.5);
    ctx.stroke();
    //绘制内容行5线,只有当为11选5系列是，纵轴最大值为20
    var x = 40.5,
        y = 1130,
        z = 40, //y轴数值左间距
        t = "";
    if($("#navBox .actNavBig").text() == "11选5") {
        t = 15;
    } else if($("#navBox .actNavBig").text() == "快三") {
        t = 6
    } else {
        t = 10
    }
    for(var i = 0; i < 2; i++) {
        if($("#navBox .actNavBig").text() == "11选5" || $("#navBox .actNavBig").text() == "快三") {
            huixian(i, true)
        } else {
            huixian(i, false)
        }
        if(($("#navBox .actNavBig").text() == "11选5" && i == 1) || ($("#navBox .actNavBig").text() == "快三" && i == 1)) {
            huixian(2, true)
        }
    }

    function huixian(i, lineCount) {
        ctx.beginPath();
        ctx.strokeStyle = '#DEDEDE';
        ctx.lineWidth = 1;
        drawDashedLine(ctx, z, x, y, x, 2);
        ctx.stroke();

        //绘制 x 轴文字
        ctx.font = '16px Arial Regular';
        ctx.fillStyle = "#999999"
        ctx.fillText(t, z - 25, x);
        if($("#navBox .actNavBig").text() !== "快三") {
            t -= 5;
        } else {
            t -= 2;
        }
        //      x += 40;
        if(lineCount) {
            x += 53;
        } else {
            x += 80; //虚线垂直间距
        }
        if(t == 0) {
            ctx.fillText("号", z - 25, x);
        }
    }
    // 绘制虚线函数！！
    function drawDashedLine(ctx, x1, y1, x2, y2, dashLength) {
        dashLength = dashLength === undefined ? 5 : dashLength;
        var deltaX = x2 - x1;
        var deltaY = y2 - y1;
        var numDashes = Math.floor(
            Math.sqrt(deltaX * deltaX + deltaY * deltaY) / dashLength);
        for(var i = 0; i < numDashes; ++i) {
            ctx[i % 2 === 0 ? 'moveTo' : 'lineTo']
                (x1 + (deltaX / numDashes) * i, y1 + (deltaY / numDashes) * i);
        }
        ctx.stroke();
    };
    //绘制 y 轴小杆杆与 y 轴的数据
    var a = 80, //做标尺x起点
        //  var a = 160,
        //      b = 120,
        b = 200, //做标尺Y起点
        //      d = 110;//
        d = 190; //做标尺Y终点
    ctx.font = '14px Arial Regular';
    ctx.fillText("期", 40, 220);
    for(var c = 0; c < bottomArr.length; c++) {
        ctx.beginPath();
        ctx.moveTo(a, b);
        ctx.lineTo(a, d);
        ctx.strokeStyle = '#ccc';
        ctx.lineWidth = 0.5;
        ctx.stroke();
        ctx.fillText(bottomArr[c], a - 10, b + 20);
        //      a += 42.5;
        a += 42.5;
    }
    //数据
    var arr2 = [];
    //绘制圆点
    var h = 80;
    for(var f = 0; f < arr.length; f++) {
        var k = 0;
        ctx.beginPath();
        if(($("#navBox .actNavBig").text() == "快三")) {
            if(arr[f] >= 4) {
                k = 40 + (53 - (53 * (((arr[f] - 4) * 10) / 22)));
            } else if(arr[f] >= 2) {
                k = 93 + (53 - (53 * (((arr[f]-2)  * 10) / 22)));
            } else if(arr[f] < 2){
                k = 146 + (53 - (53 * ((arr[f] * 10) / 22)));
            }
        } else if(($("#navBox .actNavBig").text() == "11选5")) {
            if(arr[f] >= 10) {
                k = 40 + (53 - (53 * (((arr[f] - 10) * 10) / 50)));
            } else if(arr[f] >= 5) {
                k = 93 + (53 - (53 * (((arr[f]-5)  * 10) / 50)));
            } else if(arr[f] < 5){
                k = 146 + (53 - (53 * ((arr[f] * 10) / 50)));
            }
        } else {
            if(arr[f] > 5) {
                //  k="第一条虚线距上面距离" + （“y行高”）                    
                k = 40 + (80 - (80 * (((arr[f] - 5) * 10) / 50)));
            } else {
                //  k="第一条虚线加行高" + （“y行高”）
                k = 120 + (80 - (80 * ((arr[f] * 10) / 50)));
            }

        }
        arr2.push(k); //保存当前 y 坐标信息, 为绘制折线做准备
        ctx.arc(h, k, 3.5, 0, 2 * Math.PI);
        ctx.fillStyle = '#FC223B';
        ctx.fill();
        ctx.fillStyle = '#666666';
        ctx.fillText(arr[f], h - 8, k - 13); // 每个点的数据
        h += 42.5;
        //      h += 42.5;
    }
    //绘制折线
    var j = 80;
    ctx.beginPath();
    for(var f = 0; f < arr2.length; f++) {
        ctx.lineTo(j, arr2[f]);
        ctx.strokeStyle = '#F8213B';
        ctx.lineWidth = 0.8;
        //      j += 42.5;
        j += 42.5;
    }
    ctx.stroke();
}