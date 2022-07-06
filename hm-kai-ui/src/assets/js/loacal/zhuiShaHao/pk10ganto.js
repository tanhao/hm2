$(function() {
    //加载头部和尾部
     $("#headdivbox").load("/html/public/head.html", function() {
         if(config.ifdebug) {
             console.log("request is over!");
         }
     });
    var firstalert = true; //是否是第一次弹出完成目标
    var gethisli = true; //未达到目标继续请求li 接口
    $("#fooderbox").load("../public/fooder.html", function() {
        if(config.ifdebug) {
            console.log("request is over!");
        }
    });
    $("#user_adv").load("../public/user_adv.html", function() {
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

    $(".date").val(formatDate(new Date()));
    //初始化时间控件
    $('#datebox').calendar({
        trigger: '.date',
        zIndex: 999,
        format: 'yyyy-mm-dd',
        onSelected: function(view, date, data) {
            var date = formatDate(date);
            var fundate = date.split("-");
            if(fundate[1] * 1 <= 9) {
                datE = fundate[0] + "-" + "0" + fundate[1] + "-" + fundate[2]
            } else {
                datE = date
            }
            //          console.log(datE)
            objli(datE);
        },
        onClose: function(view, date, data) {
            if(config.ifdebug) {
                console.log("关闭是触发");
            }
        }
    });

    function formatDate(date) {
        var y = date.getFullYear();
        var m = date.getMonth() + 1;
        //      m = m < 10 ? '0' + m : m;
        var d = date.getDate();
        d = d < 10 ? ('0' + d) : d;
        return y + '-' + m + '-' + d;
    };

    // 数据接口
    var public = "";
    var datE = $('.date').val();
    var firstfundate = datE.split("-");
    if(firstfundate[1] * 1 <= 9) {
        datE = firstfundate[0] + "-" + "0" + firstfundate[1] + "-" + firstfundate[2]
    }
    objli = function(date) {
        $.ajax({
            type: "get",
            //          url: public + "http://13.112.108.218/LotteryPlan/getBetInfoList.do",
            url: publicUrl + "/LotteryPlan/getBetInfoList.do",
            async: true,
            dataType: "json",
            data: {
                date: date,
                lotCode: "10001"
            },
            success: function(data) {
                console.log(data);
                resultHtml(data)
            },
            error: function(errObj) {
                console.log(errObj)
            }
        });
    }
    objli(datE);
    // $(".btn_red").click(function(){
    //      datE=$('.date').val();
    //      objli(datE);
    // })
    resultHtml = function(data) {
        if(typeof(data) == "string") {
            data = JSON.parse(data);
        }
        arr = data.result.data;
        var onceOrsec = true;
        var ballnum, ballBs, ballBet, bsscore, danxu, danxuBet, danxuscore, score, sumNum, html = "";
        $.each(arr, function(i, p) {
            ballnum = p.ballNum;
            ballBs = (p.bigOrSmall == 1 ? "大" : "小");
            ballBet = p.ballBsBet;
            bsscore = (p.ballBsf == 0 ? "?" : p.ballBsf);
            danxu = (p.singleOrDouble == 1 ? "单" : "双");
            danxuscore = (p.ballSdf == 0 ? "?" : p.ballSdf); //////
            danxuBet = p.ballSdBet;
            score = (p.subtotal === "" ? "?" : p.subtotal + "分");
            sumNum = (p.sumtotal === "" ? "?" : p.sumtotal + "分");
            if(i == 0 && p.sumtotal !== "") {
                $(".pushball").text(ballnum);
                $(".sum_result").text(p.sumtotal);
                var kill = (50 - (p.sumtotal * 1) <= 0 ? "目标已完成" : 50 - (p.sumtotal * 1));
                if(kill.indexOf("目标已完成") == 0) {
                    $(".jsVal").text(kill);
                    if(formatDate(new Date()) == $('.date').val() && firstalert) {
                        $('.completeTarget').show();
                        firstalert = false;
                        gethisli = false;
                    }
                } else {
                    $(".jsVal").text("距离目标：" + kill);
                }
                onceOrsec = false;
            } else if(i == 1 && onceOrsec) {
                $(".pushball").text(ballnum);
                $(".sum_result").text(p.sumtotal);
                var kill = (50 - (p.sumtotal * 1) <= 0 ? "目标已完成" : 50 - (p.sumtotal * 1));
                $(".jsVal").text("距离目标：" + kill);
            }
            if(bsscore != "?") {
                if(bsscore.toString().indexOf("-") == "-1") {
                    bsscore = "赢" + bsscore + "分"
                } else {
                    bsscore = bsscore.toString().replace("-", "输") + "分"
                }
            }
            if(danxuscore != "?") {
                if(danxuscore.toString().indexOf("-") == "-1") {
                    danxuscore = "赢" + danxuscore + "分"
                } else {
                    danxuscore = danxuscore.toString().replace("-", "输") + "分"
                }
            }
            var liarr = [],
                tdhtml = "等待开奖";
            if(p.preDrawCode != "") {
                liarr = p.preDrawCode.split(",");
                tdhtml = "<ul> <li class='numsm" + liarr[0] + " " + (1 == ballnum * 1 ? "push" : "") + "'></li>" +
                    "<li class='numsm" + liarr[1] + " " + (2 == ballnum * 1 ? "push" : "") + "'></li>" +
                    "<li class='numsm" + liarr[2] + " " + (3 == ballnum * 1 ? "push" : "") + "'></li>" +
                    "<li class='numsm" + liarr[3] + " " + (4 == ballnum * 1 ? "push" : "") + "'></li>" +
                    "<li class='numsm" + liarr[4] + " " + (5 == ballnum * 1 ? "push" : "") + "'></li>" +
                    "<li class='numsm" + liarr[5] + " " + (6 == ballnum * 1 ? "push" : "") + "'></li>" +
                    "<li class='numsm" + liarr[6] + " " + (7 == ballnum * 1 ? "push" : "") + "'></li>" +
                    "<li class='numsm" + liarr[7] + " " + (8 == ballnum * 1 ? "push" : "") + "'></li>" +
                    "<li class='numsm" + liarr[8] + " " + (9 == ballnum * 1 ? "push" : "") + "'></li>" +
                    "<li class='numsm" + liarr[9] + " " + (10 == ballnum * 1 ? "push" : "") + "'></li></ul>";
            }
            html += "<tr><td>" + p.preDrawTime + "</td>" +
                "<td>" + p.preDrawIssue + "期</td><td>" + tdhtml + "</td>" +
                "<td>" + ballnum + "球</td>" +
                "<td class='oth_cla'><span>" + ballBs + "</span><span>" + bsscore + "</span></td>" +
                "<td>" + ballBet + "分</td>" +
                "<td class='oth_cla'><span>" + danxu + "</span><span>" + danxuscore + "</span></td>" +
                "<td>" + danxuBet + "分</td>" +
                "<td class='98k'>" + score + "</td>" +
                "<td>" + sumNum + "</td></tr>";
        });
        $(".th_til").siblings().remove();
        $(".th_til").after(html);
        $("#result_table .oth_cla>span:contains('赢')").css("color", "#FD3A3A")
        $("#result_table .oth_cla>span:contains('?')").css({
            "color": "#FD3A3A",
            "margin-left": "25px"
        })
        $("#result_table td.98k:contains('?')").css("color", "#FD3A3A");
    }
    var countDown = function(timestr, serverTime, id) {
        //timestr：下期开奖时间
        //serverTime：服务器时间
        //id：倒计时显示区域
        var time = timestr.replace("-", "/");
        var serverTime = serverTime.replace("-", "/");
        time = time.replace("-", "/");
        serverTime = serverTime.replace("-", "/");
        //var day_elem = $(id).find('.day');
        var hour_elem = $(id).find('.hour');
        var minute_elem = $(id).find('.minute');
        var second_elem = $(id).find('.second');
        var opentyle = $(id).find('.opentyle');
        var cuttime = $(id).find('.cuttime');

        var end_time = new Date(time); //月份是实际月份-1
        var sys_second = (end_time - new Date(serverTime)) / 1000;
        // sys_second = sys_second - 1000;
        var isOpen = true;
        var lastTime = new Date();
        var timer = setInterval(function() {
            var timerS = Math.abs(new Date() - lastTime) / 1000;
            lastTime = new Date();
            timerS = timerS.toString().split(".");
            if(timerS[0] > 1) {
                sys_second = sys_second - timerS[0];
            }
            //倒计时铃声
            if(isOpen) {
                isOpen = false;
                tools.playSound("begin");
            } else {
                tools.playSound(sys_second);
            }
            if(sys_second > 1) {
                sys_second -= 1;
                //var day = Math.floor((sys_second / 3600) / 24);
                var hour = Math.floor(sys_second / (60 * 60));
                var minute = Math.floor((sys_second / (60)) % 60);
                var second = Math.floor((sys_second) % 60);
                //day_elem && $(day_elem).text(day); //计算天
                $(hour_elem).text(hour < 10 ? "0" + hour : hour); //计算小时
                $(minute_elem).text(minute < 10 ? "0" + minute : minute); //计算分钟
                $(second_elem).text(second < 10 ? "0" + second : second); //计算秒杀
                //如果时间小于0则删除时间显示
                if(hour <= 0) {
                    $(id).find(".hourtxt").hide();
                    $(hour_elem).hide();
                } else {
                    $(id).find(".hourtxt").show();
                    $(hour_elem).show();
                }
                $(opentyle).hide(); //倒计时区域显示开奖中...
                $(cuttime).css({
                    display: "inline-block"
                }); //倒计时区域隐藏...
            } else {
                $(opentyle).show(); //倒计时区域显示开奖中...
                $(cuttime).hide(); //倒计时区域隐藏...
                clearInterval(timer); //清除当前定时器
                setTimeout(function() {
                    //              ajaxRequst($(id).find(".nextIssue").text(), id); //请求后台加载数据传入一下期期数
                    ajaxpk10("", "10001", "#pk10kai");
                }, 1000);
            }
        }, 1000);
    }
    var pk10end_GaryAddIndex = function(arr, id) {
        var arr = arr;
        var id = id;
        var arrlength = arr.length;
        var time = 0,
            timer;
        var jnumberid = $(id).find(".numberbox");
        if(pk10animate && id == pkid) { //如果相同的采种相同的ID动画没有执行完成，不会启动新的动画
            return;
        }
        $(jnumberid).empty();
        timer = setInterval(function() {
            pkid = id;
            pk10animate = true;
            var lastli = "";
            if(time < arrlength) {
                if(time == arrlength - 1) {
                    lastli = "li_after";
                }
                var lihtml = "<li class='numsm" + arr[time] + " " + lastli + "'><i style='font-size:10px; display:none'>" + arr[time] + "</i></li>";
                $(jnumberid).append(lihtml);
                time += 1;
            } else {
                clearInterval(timer);
                pk10animate = false;
            }
        }, 60);
        $(id).find(".opentyle").hide();
        $(id).find(".cuttime").show(); //隐藏倒计时
        $("#waringbox").hide("200");
        if(gethisli) {
            setTimeout(function() {
                objli(datE);
            }, 3000)
        }
    }

    var pk10OpenAnimate_GaryAddIndex = function(id) {
        var time = 600;
        var jnumberid = $(id + " .numberbox").find("li");
        $(id).find(".opentyle").show();
        $(id).find(".cuttime").hide(); //隐藏倒计时
        var intervalPk10 = setInterval(function() {
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
                var className = res[a] < lilength ? "numsm0" + res[a] : "numsm" + res[a];
                if(a == lilength - 1) {
                    lihtml += "<li style='margin-right: 0px;' class='" + className + "'></li>"
                } else {
                    lihtml += "<li class='" + className + "'></li>"
                }
            }
            $(id).find(".numberbox").empty();
            $(id).find(".numberbox").append(lihtml);
            if(time == 100) {
                $("#waringbox").show(300); //显示网络waring提示
            }
        }, 100);
        animateID[id] = intervalPk10; //向动画数据中放动画
    }
    var ajaxpk10 = function(issue, lotCode, id) {
        var issue = issue == undefined ? "" : issue;
        var iferror = false;
        $.ajax({
            url: publicUrl + "pks/getLotteryPksInfo.do?issue=" + issue,
            type: "GET",
            data: {
                "lotCode": lotCode
            },
            timeout: 60000,
            beforeSend: function() {
                if(animateID[id] == undefined) {
                    pk10OpenAnimate_GaryAddIndex(id); //请求执行开奖动画
                }
            },
            success: function(data) {
                try {
                    pk10Data(data, lotCode, id); //向头部填充数据1
                    clearInterval(animateID[id]); //清除动画
                    delete animateID[id];
                } catch(e) {
                    setTimeout(function() {
                        ajaxpk10("", "10001", "#pk10kai")
                    }, 1000)
                }
            },
            error: function(data) {
                setTimeout(function() {
                    ajaxpk10("", "10001", "#pk10kai")
                }, 1000)
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
    var pk10Data = function(jsondata, lotCode, id) {
        var data = tools.parseObj(jsondata);
        if(data.result.businessCode == "100002") {
            throw new Error("error");
        } else {
            data = data.result.data;
            var timeResult = tools.operatorTime(data.drawTime == "" ? "0" : data.drawTime, data.serverTime); //得到时间差
            if(timeResult <= 0) {
                throw new Error("error");
            }
            $(id).find(".opentyle").hide();
            $(id).find(".cuttime").css({
                display: "inline-block"
            });
            //总期数
            var count = data.totalCount;
            //          $(id).find(".drawCount").text(data.drawCount);
            //          $(id).find(".sdrawCountnext").text(count - (data.drawCount) * 1);
            var nextIssue = $(id).find(".nextIssue").text();
            $(id).find(".nextIssue").text(data.drawIssue);
            $(id).find(".preDrawIssue").text(data.preDrawIssue);
            var drawCode = (data.preDrawCode).split(",");
            //添加倒计时
            countDown(data.drawTime, data.serverTime, id);
            pk10end_GaryAddIndex(drawCode, id);
        }
    }
    ajaxpk10("", "10001", "#pk10kai");
    $(".close_target").click(function() {
        $(this).parent().parent().hide()
    })

})