function tryRecoverArr(){var n=[];$(".headCode").find("ul li").each(function(){n.push(parseInt($(this).text()))}),runKaiCode(n)}function headKaiCode(n){for(var a="",i="",t="",e=0;e<n.length;e++)0==e?a="<li id='oneCode'><span>"+n[e]+"</span></li>":1==e?i="<li id='twoCode'><span>"+n[e]+"</span></li>":t="<li id='threeCode'><span>"+n[e]+"</span></li>";var l=a+i+t;$(".headCode").find("ul").append(l)}function runKaiCode(n){for(var a="",i="",t="",e=0;e<n.length;e++)0==e?a="<li class='firstBall'><span>"+n[e]+"</span></li>":1==e?i="<li class='secondBall'><span>"+n[e]+"</span></li>":t="<li class='thirdBall'><span>"+n[e]+"</span></li>";var l=a+i+t;$("#curNumUl").append(l)}function kaiCodeAnimate(n){var a=0;$(".runCode").find("#curNumUl").empty();var i=setInterval(function(){0==a?($("#curNumUl").append("<li class='firstBall'><span>"+n[a]+"</span></li>"),clearInterval(runBall1),dropAgian(1)):1==a?($("#curNumUl").append("<li class='secondBall'><span>"+n[a]+"</span></li>"),clearInterval(runBall2),dropAgian(2)):2==a&&($("#curNumUl").append("<li class='thirdBall'><span>"+n[a]+"</span></li>"),clearInterval(runBall3),dropAgian(3)),++a>3&&(clearInterval(i),bgMusic())},1e3)}function dropBall(){var n=$(".parLi").find("ul li"),a=0;$(".runCode").find("#curNumUl").empty();for(a=0;a<30;a++)dropStart(a,a<5?n.eq(a).position().top+11*(5-a):a>=5&&a<10?n.eq(a).position().top+11*(10-a):a>=10&&a<15?n.eq(a).position().top+11*(15-a):a>=15&&a<20?n.eq(a).position().top+11*(20-a):a>=20&&a<25?n.eq(a).position().top+11*(25-a):n.eq(a).position().top+11*(30-a),n.eq(a).position().left)}function dropAgian(n){var a=$(".parLi").find("ul li");1==n?agianAnimate(a,0,10):2==n?agianAnimate(a,10,20):agianAnimate(a,20,30)}function agianAnimate(n,a,i){for(var t=a;t<i;t++)topN=createNum(50,55),leftN=n.eq(t).position().left+createNum(-5,5),$(".parLi").find("ul li").eq(t).animate({top:topN+"px",left:leftN+"px"},500)}function dropStart(n,a,i){var t="";t=n<5||n>=10&&n<15||n>=20&&n<25?createNum(0,25)+i:createNum(-25,0)+i;var e=a+createNum(-5,1);$(".parLi").find("ul li").eq(n).animate({top:e+"px",left:t+"px"},500)}function ballRunning1(){for(var n=$(".firstBallPip").find("ul li"),a=0,i=0,t=0;t<10;t++)i=createNum(0,55),t<5?(a=createNum(-5,25),ballAnimate(n[t],i,a)):(a=createNum(-25,5),ballAnimate(n[t],i,a))}function ballRunning2(){for(var n=$(".secondBallPip").find("ul li"),a=0,i=0,t=0;t<10;t++)i=createNum(0,55),t<5?(a=createNum(-5,25),ballAnimate(n[t],i,a)):(a=createNum(-25,5),ballAnimate(n[t],i,a))}function ballRunning3(){for(var n=$(".thirdBallPip").find("ul li"),a=0,i=0,t=0;t<10;t++)i=createNum(0,55),t<5?(a=createNum(-5,25),ballAnimate(n[t],i,a)):(a=createNum(-25,5),ballAnimate(n[t],i,a))}function ballAnimate(n,a,i){var t=createNum(10,50);$(n).animate({top:a+"px",left:i+"px"},t)}function createNum(n,a){var i=a-n,t=Math.random();return n+Math.round(t*i)}function bgMusic(){audioType="b",fc3d.sound.play("audioidBg"),fc3d.sound.stop("audioidKai")}function kaiMusic(){audioType="r",fc3d.sound.play("audioidKai"),fc3d.sound.stop("audioidBg")}var fc3d={},isTry=!1,ifopen=!1,playa="b",animateId={},timer=null;$(function(){$("#soundBth").on("click",function(){"soundsOn"==$("#soundBth").attr("class")?($("#soundBth").removeClass("soundsOn").addClass("soundsOff"),fc3d.sound.stop("audioidKai"),fc3d.sound.stop("audioidBg")):($("#soundBth").removeClass("soundsOff").addClass("soundsOn"),"b"==audioType?(fc3d.sound.play("audioidBg"),fc3d.sound.stop("audioidKai")):(audioType,fc3d.sound.play("audioidKai"),fc3d.sound.stop("audioidBg")))}),$("#tryBtn").on("click",function(){if(isTry)return $(".jzCheck").show(),setTimeout(function(){$(".jzCheck").hide()},1e3),!1;isTry=!0,fc3d.startGame(1)})}),fc3d.startGame=function(n){kaiMusic();var a=[];fc3d.startAnimate(),setTimeout(function(){1==n&&(a=fc3d.createArr(),fc3d.stopAnimate(1,a))},4e3)},fc3d.recoverBall=function(){$(".parLi").find("ul").empty();$(".parLi").find("ul li");var n="<li></li><li></li><li></li><li></li><li></li>";$(".leftUl").append(n),$(".rightUl").append(n)},fc3d.sound={play:function(n){"soundsOn"==$("#soundBth").attr("class")&&document.getElementById(n).play()},stop:function(n){document.getElementById(n).pause()}},fc3d.startVid=function(n){$("#hourtxt").show(),$("#opening").hide(),bgMusic(),fc3d.Data(n),runKaiCode(n.preDrawCode)},fc3d.stopVid=function(n){$("#hourtxt").show(),$("#opening").hide(),fc3d.stopAnimate(2,n.preDrawCode),fc3d.Data(n)},fc3d.Data=function(n){$(".headCode").find("ul").empty(),$("#issue").text(n.drawIssue),$("#kaiTime").text(n.drawTime),$(".runCode").find("#curNumUl").empty();var a=n.preDrawTime.split(":");a[0],a[1],a[2];fc3d.cutTime(fc3d.getSecond(n.preDrawTime)),headKaiCode(n.preDrawCode)},fc3d.stopAnimate=function(n,a){$(".runCode").find("#curNumUl").empty(),kaiCodeAnimate(a),setTimeout(function(){fc3d.recoverBall(),1==n&&($(".runCode").find("#curNumUl").empty(),tryRecoverArr()),isTry=!1},5e3)};var runBall1=null,runBall2=null,runBall3=null;fc3d.startAnimate=function(){dropBall(),setTimeout(function(){runBall1=setInterval(function(){ballRunning1()},50),runBall2=setInterval(function(){ballRunning2()},50),runBall3=setInterval(function(){ballRunning3()},50)},500)},fc3d.csstartAnimate=function(){dropBall(),setTimeout(function(){runBall1=setInterval(function(){ballRunning1()},50),runBall2=setInterval(function(){ballRunning2()},50),runBall3=setInterval(function(){ballRunning3()},50),setTimeout(function(){var n={preDrawCode:[3,6,9],drawIssue:20170921,drawTime:1111,preDrawTime:"00:00:30"};fc3d.stopVid(n)},1e4)},500)},fc3d.getSecond=function(n){var a=n.split(":"),i=a[0],t=a[1],e=a[2];return 3600*(i=i<10?i.substring(i.length-1,i.length):i)+60*(t=t<10?t.substring(t.length-1,t.length):t)+1*(e=e<10?e.substring(e.length-1,e.length):e)},fc3d.cutTime=function(n){null!=timer&&clearInterval(timer);var n=n;timer=setInterval(function(){if(n>=1){n-=1;var a=Math.floor(n/3600),i=Math.floor(n/60%60),t=Math.floor(n%60),e="";if(e=(a<10?"0"+a:a)+" : ",e=e+""+(i<10?"0"+i:i)+" : "+(t<10?"0"+t:t),$("#hourtxt").text(e),n<10){var l=$(".linelist").find("li");$(l).eq(n).addClass("redli")}}else clearInterval(timer),$("#hourtxt").hide(),$("#opening").css("display","block"),kaiMusic(),fc3d.startAnimate()},1e3)},fc3d.clearTime=function(){clearInterval(timer)},fc3d.createArr=function(){for(var n=[],a=0;a<3;a++){var i=createNum(0,9);if(0!=a)for(var t=0,e=n.length-1;t<n.length;t++){if(i==n[t]){a--;break}if(t==e){n.push(i);break}}else n.push(i)}return n};