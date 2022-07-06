var bgSelect = setInterval(function() {
    $(".zixun_nav").addClass("li_checked").siblings().removeClass("li_checked");
    if($(".zixun_nav").length != 0) {
        clearInterval();
    }
}, 200)

//设为首页
function SetHome(url) {
    if(document.all) {
        document.body.style.behavior = 'url(#default#homepage)';
        document.body.setHomePage(url);
    } else {
        alert("您好,您的浏览器不支持自动设置页面为首页功能,请您手动在浏览器里设置该页面为首页!");
    }
}

//收藏本站
function addFavorite2() {
    var url = window.location;
    var title = document.title;
    var ua = navigator.userAgent.toLowerCase();
    if(ua.indexOf("360se") > -1) {
        alert("由于360浏览器功能限制，请按 Ctrl+D 手动收藏！");
    } else if(ua.indexOf("msie 8") > -1) {
        window.external.AddToFavoritesBar(url, title); //IE8
    } else if(document.all) {
        try {
            window.external.addFavorite(url, title);
        } catch(e) {
            alert('您的浏览器不支持,请按 Ctrl+D 手动收藏!');
        }
    } else if(window.sidebar) {
        window.sidebar.addPanel(title, url, "");
    } else {
        alert('您的浏览器不支持,请按 Ctrl+D 手动收藏!');
    }
}

var hotNewData = "";
$(function() {
    $(".movedowm").on({
        hover: function() {
            $(".tjlottey").addClass("hover")
        }
    })
    $(".tjlottey").mouseleave(function() {
        $(this).removeClass("hover")
    })
    //回到顶部
    var publicurl = config.publicUrl;
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

    $("#headdivbox").load("/html/public/head.html", function() {
        console.log("request is over!");
    });
    $("#fooderbox").load("/html/public/fooder.html", function() {
        console.log("request is over!");
    });

    //加载热门文章
    getHotArticle_createhtml = function(pageNo, pageSize) {
        $("#hotNews>ul").empty();
        $.ajax({
            type: "get",
            url: publicurl + "news/findHotNews.do",
            //          url:"http://192.绿星国际.1.111:8080/kai-api/news/findHotNews.do",
            async: true,
            data: {
                pageNo: pageNo,
                pageSize: pageSize
            },
            dataType: "json",
            success: function(data) {
                if(!(typeof data == 'object')) {
                    var data = JSON.parse(data);
                }
                hotNewData = data.result.data.list;
                getHotArticle_create(data.result.data);
                console.log(hotNewData);

            },
            error: function(err) {
                console.log("data error");
            }
        });
    }
    getHotArticle_create = function(data) {
        var html = "";
        $.each(data.list, function(i, p) {
            html += "<li><a href='zx_detail.html?" + p.newsId + "'> <span>[" + p.programaName + "]</span>" + p.title + "</a></li>"
        });
        $("#hotNews>ul").html(html)
    }
    var pageon = 1,
        pagesize = 8;
    // getHotArticle_createhtml(pageon, pagesize);
    if($("#hotNewData").length != 0) {
        hotNewData = JSON.parse($("#hotNewData").val());
    }
    $("#hotNews>ul").on("click", "a", function(e) {
        var seobj = {
            data: hotNewData,
            funame: "checkRight()",
            NewsHot: true,
            pageNo: pageon,
            pageSize: pagesize
        };
        console.log(seobj)
        JSON.stringify(seobj)
        sessionStorage.setItem("sessData", JSON.stringify(seobj))
    })

});