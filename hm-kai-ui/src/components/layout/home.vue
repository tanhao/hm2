<template>
    <div class="menubannbox">
        <div class="menubox">
            <div class="zhezao">
                <div class="zhezao_content">
                    <div class="kair" style="display: none">
                        <div class="announce_top">
                            <div id="littleimg" class="home_littleimg">
                                <div class="progress"
                                     style="display: block;margin-top:30px;width:100%;text-align:center;"> <img src=""> </div>
                            </div>
                            <div class="announce_middle">
                                <h1> <span class="newAnn">行业新闻</span> <span class="more"> <a>更多</a> </span> </h1>
                                <div class="margin_top" id="bannernews">
                                    <p> <a target="_blank" href="#"> 福彩3D第2020221期观点：九宫八卦分析 </a> </p>
                                    <p> <a target="_blank" href="#"> 9月26日北京快3号码分析 </a> </p>
                                    <p> <a target="_blank" href="#"> 9月26日上海快3号码分析 </a> </p>
                                    <p> <a target="_blank" href="#"> 9月26日上海11选5号码分析 </a> </p>
                                    <p> <a target="_blank" href="#"> 幸运飞艇投注的心得到底好不好 </a> </p>
                                    <p> <a target="_blank" href="#"> 幸运飞艇开户我们可以自由选择 </a> </p>
                                    <p> <a target="_blank" href="#"> 七星彩玩法技巧之分析大小比选号 </a> </p>
                                </div>
                                <!--<div class="margin_top" id="bannernews">正在加载...</div>-->
                            </div>
                        </div>
                        <div class="video_box"> <span class="v_left"></span> <span class="v_right"></span>
                            <ul>
                                <li class="pk10"> <a href="pk10">
                                    <dl>
                                        <dt> <img src="@/assets/img/video_png/pk10.png" alt="">
                                            <p> <span></span> <span>北京PK10</span> </p>
                                        </dt>
                                        <dd class="dd_pak10">
                                            <div class="cuttime" style="display: inline-block;">倒计时: <span
                                                class="bgtime hour" style="display: none;">00 </span> <span
                                                class="hourtxt" style="display: none;">时 </span> <span
                                                class="bgtime minute">57 </span> <span class="">分</span> <span class="bgtime second">20</span> <span
                                                class="">秒 </span> </div>
                                            <p class="opentyle" style="display: none;">开奖中...</p>
                                        </dd>
                                    </dl>
                                </a> </li>
                                <li class="xingyft"> <a href="xingyft">
                                    <dl>
                                        <dt> <img src="@/assets/img/video_png/jsft.png" alt="">
                                            <p> <span></span> <span>极速飞艇</span> </p>
                                        </dt>
                                        <dd class="dd_pak10">
                                            <!--倒计时：<span>07 </span> 分 <span> 50 </span> 秒-->
                                            <div class="cuttime" style="display: inline-block;">倒计时: <span
                                                class="bgtime hour" style="display: none;">00 </span> <span
                                                class="hourtxt" style="display: none;">时 </span> <span
                                                class="bgtime minute">00 </span> <span class="">分</span> <span class="bgtime second">10</span> <span
                                                class="">秒 </span> </div>
                                            <p class="opentyle" style="display: none;">开奖中...</p>
                                        </dd>
                                    </dl>
                                </a> </li>
                                <li class="gxklsf"> <a href="gxklsf">
                                    <dl>
                                        <dt> <img src="@/assets/img/video_png/gxklsf.png" alt="">
                                            <p> <span></span> <span>广西快乐十分</span> </p>
                                        </dt>
                                        <dd class="dd_pak10">
                                            <!--倒计时：<span>07 </span> 分 <span> 50 </span> 秒-->
                                            <div class="cuttime" style="display: inline-block;">倒计时: <span
                                                class="bgtime hour" style="display: none;">00 </span> <span
                                                class="hourtxt" style="display: none;">时 </span> <span
                                                class="bgtime minute">47 </span> <span class="">分</span> <span class="bgtime second">25</span> <span
                                                class="">秒 </span> </div>
                                            <p class="opentyle" style="display: none;">开奖中...</p>
                                        </dd>
                                    </dl>
                                </a> </li>
                                <li class="tjklsf"> <a href="tjklsf">
                                    <dl>
                                        <dt> <img src="@/assets/img/video_png/tjklsf_v.jpg" alt="">
                                            <p> <span></span> <span>天津快乐十分</span> </p>
                                        </dt>
                                        <dd class="dd_pak10">
                                            <div class="cuttime" style="display: inline-block;">倒计时: <span
                                                class="bgtime hour" style="display: none;">00 </span> <span
                                                class="hourtxt" style="display: none;">时 </span> <span
                                                class="bgtime minute">37 </span> <span class="">分</span> <span class="bgtime second">35</span> <span
                                                class="">秒 </span> </div>
                                            <p class="opentyle" style="display: none;">开奖中...</p>
                                        </dd>
                                    </dl>
                                </a> </li>
                            </ul>
                        </div>
                    </div>
                    <main>
                        <div class="lottey_title">
                            <ul>
                                <li data-text="#hot_li" @click="groupSelectFun('hotColors')" :class="lotteryGroupSelect=='hotColors'?'check':''">热门彩</li>
                                <li data-text="#pk10_li" @click="groupSelectFun('pk10')" :class="lotteryGroupSelect=='pk10'?'check':''">PK10</li>
                                <li data-text="#ssc_li" @click="groupSelectFun('ssc')" :class="lotteryGroupSelect=='ssc'?'check':''">时时彩</li>
                                <li data-text="#check5_li" @click="groupSelectFun('klsf')" :class="lotteryGroupSelect=='klsf'?'check':''">快乐十分</li>
                                <!--<li data-text="#klsf_li">快乐十分</li>-->
                                <!--<li data-text="#kuai3_li">快三</li>-->
                               <!--<li data-text="#other_li"  @click="groupSelectFun('qt')">其它</li>-->

                               <!--注意快乐十分样式 需要更改 19 20 要在原来的 numblueHead 加上 numred-->
                                <!--幸运农场为下列样式-->
                                <!--<li class="ncnum12"></li>-->
                            </ul>
                        </div>
                        <div class="view_box">
                            <div id="hot_li">
                                <template v-for="(item,index) in resultItems">
                                    <div v-if="item.groupId==100 && ((lotteryGroupSelect=='hotColors' && (item.lotteryKey=='speed10' ||
                                    item.lotteryKey=='xyft' || item.lotteryKey=='lucky10' || item.lotteryKey=='hmpk10')) || lotteryGroupSelect=='pk10')" id="pk10_hot">
                                        <div class="rowbox1">
                                            <div @click="saveLotteryKeyFun(item.lotteryKey)" class="imgheadl"> <a :class="item.lotteryKey" target="_blank" href="/#/pk10/"> </a> </div>
                                            <div class="num_ul">
                                                <h1>{{$t(item.lotteryKey)}} <span class="preDrawIssue">{{item.gameNo}}</span> 期 </h1>
                                                <p>当前<span class="drawCount">{{item.gameCount}}</span>期，剩<span
                                                    class="sdrawCountnext">{{item.nextRound}}</span>期 </p>
                                                <ul id="jnumber" class="numberbox">
                                                    <template v-for="(resultNum,i) in item.result">
                                                        <li :class="'nub'+resultNum"> <i style="font-size:10px; display:none">{{resultNum}}</i> </li>
                                                    </template>

                                                </ul>
                                            </div>
                                            <div class="nextkai_time">
                                                <p>距下期开奖</p>
                                                <div v-show="item.isOpenStatus" class="line linetime cuttime indextime" id="timebox"
                                                     style="display: inline-block;"> <span class="bgtime hour"
                                                                                           style="display: none;"> </span> <span class="hourtxt" style="display: none;">时</span> <span
                                                    class="bgtime minute">{{returnLotteryTime(item).m}} </span> <span>分</span> <span
                                                    class="bgtime second">{{returnLotteryTime(item).s1}}{{returnLotteryTime(item).s2}} </span> <span>秒</span> </div>
                                                <p v-show="!item.isOpenStatus" class="opentyle" style="display: none;">开奖中...</p>
                                            </div>
                                        </div>
                                        <div class="rowbox2">
                                            <table border="0" cellpadding="1" cellspacing="1">
                                                <tbody>
                                                <tr>
                                                    <th colspan="5">1-5龙虎</th>
                                                    <th colspan="3">冠亚和</th>
                                                </tr>
                                                <tr class="longhu">
                                                    <td>{{$t(item.special[3])}}</td>
                                                    <td>{{$t(item.special[4])}}</td>
                                                    <td>{{$t(item.special[5])}}</td>
                                                    <td>{{$t(item.special[6])}}</td>
                                                    <td>{{$t(item.special[7])}}</td>
                                                    <td>{{$t(item.special[0])}}</td>
                                                    <td>{{$t(item.special[1])}}</td>
                                                    <td>{{$t(item.special[2])}}</td>
                                                </tr>
                                                </tbody>
                                            </table>
                                        </div>
                                        <div class="rowbox3" id="rowbox3">
                                            <ul>
<!--                                                <li> <a @click="showAlert" class="video_a">开奖视频 </a> </li>-->
                                                <li> <a @click="openLotteryByTitle(item.lotteryKey,'dtRoad')" target="_blank" href="/#/pk10/">龙虎路珠</a> </li>
                                                <li> <a @click="openLotteryByTitle(item.lotteryKey,'roadA')" target="_blank" href="/#/pk10/">路珠分析</a> </li>
                                                <li> <a @click="openLotteryByTitle(item.lotteryKey,'lhtj')" target="_blank" href="/#/pk10/">龙虎统计</a> </li>
                                                <li> <a @click="openLotteryByTitle(item.lotteryKey,'lrfx')" target="_blank" href="/#/pk10/">冷热分析</a> </li>
                                            </ul>
                                        </div>
                                    </div>
                                    <div v-if="item.groupId==200 && ((lotteryGroupSelect=='hotColors' && (item.lotteryKey=='lucky5' ||
                                    item.lotteryKey=='hmcqssc')) || lotteryGroupSelect=='ssc')" id="cqSsc_hot">
                                        <div class="rowbox1">
                                            <div @click="saveLotteryKeyFun(item.lotteryKey)" class="imgheadl">
                                                <a :class="item.lotteryKey" target="_blank" href="/#/ssc/"> </a>
                                            </div>
                                            <div class="num_ul">
                                                <h1>{{$t(item.lotteryKey)}}<span class="preDrawIssue">{{item.gameNo}}</span> 期 </h1>
                                                <p>当前<span class="drawCount">{{item.gameCount}}</span>期，剩<span
                                                    class="sdrawCountnext">{{item.nextRound}}</span>期 </p>
                                                <ul class="kajianhao">
                                                    <template v-for="(resultNum,i) in item.result">
                                                        <li class="numblueHead"
                                                            style="padding-top: 0px; line-height: 36px;">{{resultNum}} </li>
                                                    </template>
                                                </ul>
                                            </div>
                                            <div class="nextkai_time">
                                                <p>距下期开奖</p>
                                                <div v-show="item.isOpenStatus" class="line linetime cuttime indextime" id="timebox"
                                                     style="display: inline-block;"> <span class="bgtime hour"
                                                                                           style="display: none;">84040 </span> <span class="hourtxt"
                                                                                                                                              style="display: none;">时 </span> <span
                                                    class="bgtime minute">{{returnLotteryTime(item).m}}  </span> <span>分</span> <span
                                                    class="bgtime second">{{returnLotteryTime(item).s1}}{{returnLotteryTime(item).s2}} </span> <span>秒</span> </div>
                                                <p class="opentyle" v-show="!item.isOpenStatus" style="display: none;">开奖中...</p>
                                            </div>
                                        </div>
                                        <div class="rowbox2">
                                            <table border="0" cellpadding="1" cellspacing="1">
                                                <tbody>
                                                    <tr>
                                                        <th width="40" colspan="3">总和</th>
                                                        <th>龙虎</th>
                                                        <th>前三</th>
                                                        <th>中三</th>
                                                        <th>后三</th>
                                                    </tr>
                                                    <tr class="longhu2">
                                                        <td class="sumNum">{{item.special[0]}}</td>
                                                        <td class="sumSingleDouble">{{$t(item.special[1])}}</td>
                                                        <td class="sumBigSmall">{{$t(item.special[2])}}</td>
                                                        <td class="dragonTiger">{{$t(item.special[3])}}</td>
                                                        <td class="behindThree">{{$t(item.special[4])}}</td>
                                                        <td class="betweenThree">{{$t(item.special[5])}}</td>
                                                        <td class="lastThree">{{$t(item.special[6])}}</td>
                                                    </tr>
                                                </tbody>
                                            </table>
                                        </div>
                                        <div class="rowbox3" id="rowbox3">
                                            <ul>
<!--                                                <li> <a @click="showAlert" class="video_a">开奖视频 </a> </li>-->
                                                <li> <a @click="openLotteryByTitle(item.lotteryKey,'roadA')" target="_blank" href="/#/ssc/">路珠分析</a> </li>
                                                <li> <a @click="openLotteryByTitle(item.lotteryKey,'noRoad')" target="_blank" href="/#/ssc/">号码路珠</a> </li>
                                                <li> <a @click="openLotteryByTitle(item.lotteryKey,'lmsjtj')" target="_blank" href="/#/ssc/">两面统计</a> </li>
                                                <li> <a @click="openLotteryByTitle(item.lotteryKey,'lrfx')" target="_blank" href="/#/ssc/">冷热分析</a> </li>
                                            </ul>
                                        </div>
                                    </div>
                                    <div v-if="item.groupId==300 && lotteryGroupSelect=='klsf'" id="klsf_hot">
                                        <div class="rowbox1">
                                            <div class="imgheadl" @click="saveLotteryKeyFun(item.lotteryKey)"> <a :class="item.lotteryKey" target="_blank" href="/#/klsf"> </a> </div>
                                            <div class="num_ul">
                                                <h1>{{$t(item.lotteryKey)}} <span class="preDrawIssue">{{item.gameNo}}</span> 期 </h1>
                                                <p>当前<span class="drawCount">{{item.gameCount}}</span>期，剩<span
                                                    class="sdrawCountnext">{{item.nextRound}}</span>期 </p>
                                                <ul class="kajianhao">
                                                    <template v-for="(resultNum,i) in item.result">
                                                        <li v-if="item.lotteryKey!='xync'" :class="resultNum>=19?'numblueHead numred':'numblueHead'"
                                                            style="padding-top: 0px; line-height: 36px;">{{resultNum}} </li>
                                                        <li v-else :class="'ncnum'+resultNum"
                                                            style="padding-top: 0px; line-height: 36px;"> </li>
                                                    </template>
                                                </ul>
                                            </div>
                                            <div class="nextkai_time">
                                                <p>距下期开奖</p>
                                                <div v-show="item.isOpenStatus" class="line linetime cuttime indextime" id="timebox"
                                                     style="display: inline-block;"> <span class="bgtime hour"
                                                                                           style="display: none;">00 </span> <span class="hourtxt" style="display: none;">时</span> <span
                                                    class="bgtime minute">{{returnLotteryTime(item).m}} </span> <span>分</span> <span
                                                    class="bgtime second">{{returnLotteryTime(item).s1}}{{returnLotteryTime(item).s2}} </span> <span>秒</span> </div>
                                                <p class="opentyle" v-show="!item.isOpenStatus" style="display: none;">开奖中...</p>
                                            </div>
                                        </div>
                                        <div class="rowbox2">
                                            <table border="0" cellpadding="1" cellspacing="1">
                                                <tbody>
                                                    <tr>
                                                        <th width="40" colspan="4">总和</th>
                                                        <th colspan="4">1-4龙虎</th>
                                                    </tr>
                                                    <tr class="longhu2">
                                                        <td class="sumNum">{{item.special[0]}}</td>
                                                        <td class="sumSingleDouble">{{$t(item.special[1])}}</td>
                                                        <td class="sumBigSmall">{{$t(item.special[2])}}</td>
                                                        <td class="lastBigSmall">{{$t(item.special[3])}}</td>
                                                        <td class="firstDragonTiger">{{$t(item.special[4])}}</td>
                                                        <td class="secondDragonTiger">{{$t(item.special[5])}}</td>
                                                        <td class="thirdDragonTiger">{{$t(item.special[6])}}</td>
                                                        <td class="fourthDragonTiger">{{$t(item.special[7])}}</td>
                                                    </tr>
                                                </tbody>
                                            </table>
                                        </div>
                                        <div class="rowbox3" id="rowbox3">
                                            <ul>
<!--                                                <li> <a @click="showAlert" class="video_a">开奖视频 </a> </li>-->
                                                <li> <a @click="openLotteryByTitle(item.lotteryKey,'roadA')" target="_blank" href="/#/klsf/">路珠分析</a> </li>
                                                <li> <a @click="openLotteryByTitle(item.lotteryKey,'lmsjtj')" target="_blank" href="/#/klsf/">两面统计</a> </li>
                                                <li> <a @click="openLotteryByTitle(item.lotteryKey,'jrhmtj')" target="_blank" href="/#/klsf/">号码统计</a> </li>
                                                <li> <a @click="openLotteryByTitle(item.lotteryKey,'sumRoad')" target="_blank" href="/#/klsf/">总和路珠</a> </li>
                                            </ul>
                                        </div>

                                    </div>

                                </template>
                            </div>

                        </div>
                        <div class="px10sanhao">
                            <div class="sahu_lottey">
                                <h1>北京PK拾 <span>杀号计划</span> </h1>
                            </div>
                            <p class="rank"> <span class="active" data-text="fifth">冠军</span> <span
                                data-text="second">亚军 </span> <span data-text="third">第三名</span> <span
                                data-text="fourth">第四名 </span> <span data-text="fifth">第五名</span> <span
                                data-text="sixth">第六名 </span> <span data-text="seven">第七名</span> <span
                                data-text="eight">第八名 </span> <span data-text="nine">第九名</span> <span
                                data-text="ten">第十名 </span> </p>
                            <div class="user_score">
                                <ul>
                                    <li>
                                        <div class="head_div"> <a
                                            href="#"> <img
                                            src="@/assets/img/head_png/headicon_0.png"> </a> <span>冷雨夜</span> </div>
                                        <div class="result">
                                            <h3>最新冠军杀号: <span>10</span> </h3>
                                            <p>近10期成功率： <span class="clo_red">90%</span> </p>
                                            <p>历史战绩： <span>86%</span> </p>
                                        </div>
                                    </li>
                                    <li>
                                        <div class="head_div"> <a
                                            href="#"> <img
                                            src="@/assets/img/head_png/headicon_1.png"> </a> <span>木木三</span> </div>
                                        <div class="result">
                                            <h3>最新冠军杀号: <span>4</span> </h3>
                                            <p>近10期成功率： <span class="clo_red">80%</span> </p>
                                            <p>历史战绩： <span>90%</span> </p>
                                        </div>
                                    </li>
                                    <li>
                                        <div class="head_div"> <a
                                            href="#"> <img
                                            src="@/assets/img/head_png/headicon_2.png"> </a> <span>醉书生</span> </div>
                                        <div class="result">
                                            <h3>最新冠军杀号: <span>7</span> </h3>
                                            <p>近10期成功率： <span class="clo_red">90%</span> </p>
                                            <p>历史战绩： <span>96%</span> </p>
                                        </div>
                                    </li>
                                    <li>
                                        <div class="head_div"> <a
                                            href="#"> <img
                                            src="@/assets/img/head_png/headicon_3.png"> </a> <span>司令爷</span> </div>
                                        <div class="result">
                                            <h3>最新冠军杀号: <span>3</span> </h3>
                                            <p>近10期成功率： <span class="clo_red">90%</span> </p>
                                            <p>历史战绩： <span>82%</span> </p>
                                        </div>
                                    </li>
                                    <li>
                                        <div class="head_div"> <a
                                            href="#"> <img
                                            src="@/assets/img/head_png/headicon_4.png"> </a> <span>沐子眠</span> </div>
                                        <div class="result">
                                            <h3>最新冠军杀号: <span>8</span> </h3>
                                            <p>近10期成功率： <span class="clo_red">100%</span> </p>
                                            <p>历史战绩： <span>92%</span> </p>
                                        </div>
                                    </li>
                                </ul>
                            </div>
                        </div>
                        <div class="SSCsanhao">
                            <div class="sahu_lottey">
                                <h1>重庆时时彩 <span>杀号计划</span> </h1>
                            </div>
                            <p class="rank"> <span class="active" data-text="fifth">万位</span> <span
                                data-text="second">千位 </span> <span data-text="third">百位</span> <span
                                data-text="fourth">十位 </span> <span data-text="fifth">个位</span> </p>
                            <div class="user_score">
                                <ul>
                                    <li>
                                        <div class="head_div"> <a
                                            href="#"> <img
                                            src="@/assets/img/head_png/headicon_0.png"> </a> <span>忆离笙</span> </div>
                                        <div class="result">
                                            <h3>最新冠军杀号: <span>5</span> </h3>
                                            <p>近10期成功率： <span class="clo_red">80%</span> </p>
                                            <p>历史战绩： <span>90%</span> </p>
                                        </div>
                                    </li>
                                    <li>
                                        <div class="head_div"> <a
                                            href="#"> <img
                                            src="@/assets/img/head_png/headicon_1.png"> </a> <span>值半钱</span> </div>
                                        <div class="result">
                                            <h3>最新冠军杀号: <span>7</span> </h3>
                                            <p>近10期成功率： <span class="clo_red">90%</span> </p>
                                            <p>历史战绩： <span>90%</span> </p>
                                        </div>
                                    </li>
                                    <li>
                                        <div class="head_div"> <a
                                            href="#"> <img
                                            src="@/assets/img/head_png/headicon_2.png"> </a> <span>楼满风</span> </div>
                                        <div class="result">
                                            <h3>最新冠军杀号: <span>2</span> </h3>
                                            <p>近10期成功率： <span class="clo_red">100%</span> </p>
                                            <p>历史战绩： <span>90%</span> </p>
                                        </div>
                                    </li>
                                    <li>
                                        <div class="head_div"> <a
                                            href="#"> <img
                                            src="@/assets/img/head_png/headicon_3.png"> </a> <span>沐雪柔</span> </div>
                                        <div class="result">
                                            <h3>最新冠军杀号: <span>2</span> </h3>
                                            <p>近10期成功率： <span class="clo_red">100%</span> </p>
                                            <p>历史战绩： <span>90%</span> </p>
                                        </div>
                                    </li>
                                    <li>
                                        <div class="head_div"> <a
                                            href="#"> <img
                                            src="@/assets/img/head_png/headicon_4.png"> </a> <span>寒千落</span> </div>
                                        <div class="result">
                                            <h3>最新冠军杀号: <span>3</span> </h3>
                                            <p>近10期成功率： <span class="clo_red">100%</span> </p>
                                            <p>历史战绩： <span>94%</span> </p>
                                        </div>
                                    </li>
                                </ul>
                            </div>
                        </div>
                    </main>
                    <Myright></Myright>
                </div>
            </div>
        </div>
    </div>
</template>
<script>
    import Myright from '@/components/layout/mainright'
    import {mapActions, mapGetters} from 'vuex'
    export default {
        data() {
            return {
                resultItems:[],
                serverTime:'',
                serverTimer:null,
                lotteryRefreshTimer:null,
                lotteryGroupSelect:'hotColors'
            }
        },
        components: {
            Myright,

        },
        computed:{

            returnLotteryTime(item){
                let self = this;

                return (item)=>{
                    let open_time = parseInt((new Date(item.nextActionTime).getTime()/1000) - self.serverTime/1000);
                    if(open_time>0){
                        self.$set(item,"isOpenStatus",true);
                        clearInterval(item.isRefreshResult);
                        self.$set(item,'isRefreshResult',null);
                        let m = self.formatTime(parseInt(open_time / 60));
                        let s = self.formatTimeTwo(parseInt(open_time % 60));
                        let obj = {'m': m, 's1': s.charAt(1), 's2': s.charAt(2)};
                        return obj;
                    }else{


                        if(item.isOpenStatus){
                            item.isRefreshResult = setInterval(()=>{
                                item.result.sort(function(){
                                    return Math.random()-0.5
                                })
                            },100);
                        }
                        self.$set(item,"isOpenStatus",false);
                        return {'m':0,'s1':0,'s2':0};
                    }

                }
            }
        },
        beforeDestroy() {
            clearInterval(this.serverTimer);
            this.serverTimer = null;
            clearInterval(this.lotteryRefreshTimer);
            this.lotteryRefreshTimer = null;

        },
        destroyed() {
            clearInterval(this.serverTimer);
            this.serverTimer = null;
            clearInterval(this.lotteryRefreshTimer);
            this.lotteryRefreshTimer = null;

        },
        methods: {
            ...mapActions(['setLotteryList','setLotteryKey','setPageTabSelect']),
            showAlert() {
                this.$swal({
                    position: 'center',
                    title: '功能正在开发中！',
                    icon: 'info',
                    showCancelButton: false,
                    confirmButtonColor: '#3085d6',
                    cancelButtonColor: '#d33',
                    confirmButtonText: '取消',
                    timer: 1000,
                })
            },
            saveLotteryKeyFun(key){
                this.setLotteryKey(key);
            },
            openLotteryByTitle(key,title){
                this.setLotteryKey(key);
                this.setPageTabSelect(title);
            },
            groupSelectFun(value){
                this.lotteryGroupSelect = value;
            },
            threeBrotherNumTakeAlgorithm(one, two, three, type) {
                let judgeOne = '';
                let judgeTwo = '';
                let judgethree = '';
                if (type == 'take') {
                    if (one == two || two == three || one == three) {
                        return false;
                    }
                    judgeOne = one - two == 1 || one - two == -1 || one - two == -9 || one - two == 9 ? true : false;
                    judgeTwo = one - three == 1 || one - three == -1 || one - three == -9 || one - three == 9 ? true : false;
                    judgethree = two - three == 1 || two - three == -1 || two - three == -9 || two - three == 9 ? true : false;
                } else {
                    if (one == two || two == three || one == three) {
                        return true;
                    }
                    judgeOne = one - two != 1 && one - two != -1 && one - two != -9 && one - two != 9 ? true : false;
                    judgeTwo = one - three != 1 && one - three != -1 && one - three != -9 && one - three != 9 ? true : false;
                    judgethree = two - three != 1 && two - three != -1 && two - three != -9 && two - three != 9 ? true : false;
                }
                if (judgeOne && judgeTwo) {
                    return true;
                } else if (judgeOne && judgethree) {
                    return true;
                } else if (judgeTwo && judgethree) {
                    return true;
                } else {
                    return false;
                }


            },
            closeAllTime() {
                clearInterval(this.serverTimer);
                this.serverTimer = null;
            },
            setTime(time) {
                let m = this.formatTime(parseInt(time / 60));
                let s = this.formatTimeTwo(parseInt(time % 60));
                let obj = {'m': m, 's1': s.charAt(1), 's2': s.charAt(2)};
                return obj;
            },
            formatTime(time) {
                if (time >= 100) {
                    return time + '';
                } else if (time >= 10) {
                    return '' + time;
                } else {
                    return '0' + time;
                }
            },
            formatTimeTwo(time) {
                if (time >= 10) {
                    return '0' + time;
                } else {
                    return '00' + time;
                }
            },
            initLotterys(){
                let self = this;
                this.$api.Lottery.getTheResList().then(val=>{
                    if(val.success){
                        if(val.data){
                            self.closeAllTime();
                            self.serverTime = val.serverTime;
                            self.serverTimer = setInterval(()=>{
                                self.serverTime += 1000;
                            },1000);

                            val.data.forEach((resultItem)=>{
                                self.$set(resultItem,'isOpenStatus',true);
                                if(typeof resultItem.result != 'undefined' && resultItem.result) {
                                    let param = [];
                                    if (resultItem.groupId == 100) {
                                        resultItem.result.split(',').forEach((obj) => {
                                            if (obj.substring(0, 1) === '0') {
                                                param.push(obj.substring(1));
                                            } else {
                                                param.push(obj);
                                            }
                                        });

                                        let resultArr = resultItem.result.split(',');
                                        if(val.data.groupId==300){

                                            for(let i = 0;i<resultArr.length;i++){
                                                if(resultArr[i]<10){
                                                    resultArr[i] = '0'+resultArr[i];
                                                }

                                            }
                                        }
                                        resultItem.result = resultArr;
                                        let arr = [];
                                        let gyh = parseInt(resultArr[0]) + parseInt(resultArr[1]);
                                        let gyhOU = gyh > 11 ? 'OVER' : 'UNDER';
                                        let gyhOE = (gyh % 2 === 1) ? 'ODD' : 'EVEN';
                                        let no1DT = parseInt(resultArr[0]) > parseInt(resultArr[9]) ? 'DRAGON' : 'TIGER';
                                        let no2DT = parseInt(resultArr[1]) > parseInt(resultArr[8]) ? 'DRAGON' : 'TIGER';
                                        let no3DT = parseInt(resultArr[2]) > parseInt(resultArr[7]) ? 'DRAGON' : 'TIGER';
                                        let no4DT = parseInt(resultArr[3]) > parseInt(resultArr[6]) ? 'DRAGON' : 'TIGER';
                                        let no5DT = parseInt(resultArr[4]) > parseInt(resultArr[5]) ? 'DRAGON' : 'TIGER';
                                        arr = [gyh + '', gyhOU, gyhOE, no1DT, no2DT, no3DT, no4DT, no5DT];
                                        resultItem.special = arr;
                                        self.resultItems.find(obj=>{
                                            if(obj.lotteryKey == resultItem.lotteryKey  && resultItem.gameNo > obj.gameNo){
                                                if(obj.isRefreshResult){
                                                    clearInterval(obj.isRefreshResult);
                                                    obj.isRefreshResult = null;
                                                }
                                                resultItem.result = [];

                                                Object.assign(obj,resultItem);
                                                let time_i = 100;
                                                self.$set(obj,'result',[]);
                                                resultArr.forEach(val=>{
                                                    setTimeout(()=>{
                                                        obj.result.push(val);
                                                    },time_i);
                                                    time_i+=100;
                                                })
                                                return;
                                            }
                                        })

                                    } else if (resultItem.groupId == 200) {
                                        resultItem.result.split(',').forEach((obj) => {
                                            param.push(obj);
                                        });
                                        let resultArr = resultItem.result.split(',');
                                        resultItem.result = resultArr;
                                        let arr = [];
                                        let theSum = parseInt(resultArr[0]) + parseInt(resultArr[1]) + parseInt(resultArr[2]) + parseInt(resultArr[3]) + parseInt(resultArr[4]);
                                        let theSumOU = theSum > 22 ? 'OVER' : 'UNDER';
                                        let theSumOE = (theSum % 2 === 1) ? 'ODD' : 'EVEN';
                                        let theSumDT = parseInt(resultArr[0]) > parseInt(resultArr[4]) ? 'DRAGON' : 'TIGER';
                                        let top3 = '';
                                        if (parseInt(resultArr[0]) == parseInt(resultArr[1]) && parseInt(resultArr[1]) == parseInt(resultArr[2])) {
                                            top3 = 'baozi';
                                        } else if (parseInt(resultArr[0]) == parseInt(resultArr[1]) || parseInt(resultArr[1]) == parseInt(resultArr[2]) || parseInt(resultArr[0]) == parseInt(resultArr[2])) {
                                            top3 = 'duizi';
                                        } else if (self.threeBrotherNumTakeAlgorithm(parseInt(resultArr[0]), parseInt(resultArr[1]), parseInt(resultArr[2]), 'take')) {
                                            top3 = 'STRAIGHT';
                                        } else if ((parseInt(resultArr[0]) - parseInt(resultArr[1]) == 1 || parseInt(resultArr[0]) - parseInt(resultArr[1]) == -1 ||
                                            parseInt(resultArr[0]) - parseInt(resultArr[1]) == 9 || parseInt(resultArr[0]) - parseInt(resultArr[1]) == -9) ||
                                            (parseInt(resultArr[0]) - parseInt(resultArr[2]) == 1 || parseInt(resultArr[0]) - parseInt(resultArr[2]) == -1 ||
                                                parseInt(resultArr[0]) - parseInt(resultArr[2]) == 9 || parseInt(resultArr[0]) - parseInt(resultArr[2]) == -9) ||
                                            (parseInt(resultArr[1]) - parseInt(resultArr[2]) == 1 || parseInt(resultArr[1]) - parseInt(resultArr[2]) == -1 ||
                                                parseInt(resultArr[1]) - parseInt(resultArr[2]) == 9 || parseInt(resultArr[1]) - parseInt(resultArr[2]) == -9)) {
                                            top3 = 'bansun';
                                        } else {
                                            top3 = 'zaliu';
                                        }
                                        let mid3 = '';
                                        if (parseInt(resultArr[1]) == parseInt(resultArr[2]) && parseInt(resultArr[2]) == parseInt(resultArr[3])) {
                                            mid3 = 'baozi';
                                        } else if (parseInt(resultArr[1]) == parseInt(resultArr[2]) || parseInt(resultArr[2]) == parseInt(resultArr[3]) || parseInt(resultArr[1]) == parseInt(resultArr[3])) {
                                            mid3 = 'duizi';
                                        } else if (self.threeBrotherNumTakeAlgorithm(parseInt(resultArr[1]), parseInt(resultArr[2]), parseInt(resultArr[3]), 'take')) {
                                            mid3 = 'STRAIGHT';
                                        } else if ((parseInt(resultArr[1]) - parseInt(resultArr[2]) == 1 || parseInt(resultArr[1]) - parseInt(resultArr[2]) == -1 ||
                                            parseInt(resultArr[1]) - parseInt(resultArr[2]) == 9 || parseInt(resultArr[1]) - parseInt(resultArr[2]) == -9) ||
                                            (parseInt(resultArr[1]) - parseInt(resultArr[3]) == 1 || parseInt(resultArr[1]) - parseInt(resultArr[3]) == -1 ||
                                                parseInt(resultArr[1]) - parseInt(resultArr[3]) == 9 || parseInt(resultArr[1]) - parseInt(resultArr[3]) == -9) ||
                                            (parseInt(resultArr[2]) - parseInt(resultArr[3]) == 1 || parseInt(resultArr[2]) - parseInt(resultArr[3]) == -1 ||
                                                parseInt(resultArr[2]) - parseInt(resultArr[3]) == 9 || parseInt(resultArr[2]) - parseInt(resultArr[3]) == -9)) {
                                            mid3 = 'bansun';
                                        } else {
                                            mid3 = 'zaliu';
                                        }
                                        let end3 = '';
                                        if (parseInt(resultArr[2]) == parseInt(resultArr[3]) && parseInt(resultArr[3]) == parseInt(resultArr[4])) {
                                            end3 = 'baozi';
                                        } else if (parseInt(resultArr[2]) == parseInt(resultArr[3]) || parseInt(resultArr[3]) == parseInt(resultArr[4]) || parseInt(resultArr[2]) == parseInt(resultArr[4])) {
                                            end3 = 'duizi';
                                        } else if (self.threeBrotherNumTakeAlgorithm(parseInt(resultArr[2]), parseInt(resultArr[3]), parseInt(resultArr[4]), 'take')) {
                                            end3 = 'STRAIGHT';
                                        } else if ((parseInt(resultArr[2]) - parseInt(resultArr[3]) == 1 || parseInt(resultArr[2]) - parseInt(resultArr[3]) == -1 ||
                                            parseInt(resultArr[2]) - parseInt(resultArr[3]) == 9 || parseInt(resultArr[2]) - parseInt(resultArr[3]) == -9) ||
                                            (parseInt(resultArr[2]) - parseInt(resultArr[4]) == 1 || parseInt(resultArr[2]) - parseInt(resultArr[4]) == -1 ||
                                                parseInt(resultArr[2]) - parseInt(resultArr[4]) == 9 || parseInt(resultArr[2]) - parseInt(resultArr[4]) == -9) ||
                                            (parseInt(resultArr[3]) - parseInt(resultArr[4]) == 1 || parseInt(resultArr[3]) - parseInt(resultArr[4]) == -1 ||
                                                parseInt(resultArr[3]) - parseInt(resultArr[4]) == 9 || parseInt(resultArr[3]) - parseInt(resultArr[4]) == -9)) {
                                            end3 = 'bansun';
                                        } else {
                                            end3 = 'zaliu';
                                        }
                                        arr = [theSum + '', theSumOU, theSumOE, theSumDT, top3, mid3, end3];
                                        resultItem.special = arr;
                                        self.resultItems.find(obj=>{
                                            if(obj.lotteryKey == resultItem.lotteryKey  && resultItem.gameNo > obj.gameNo){
                                                if(obj.isRefreshResult){
                                                    clearInterval(obj.isRefreshResult);
                                                    obj.isRefreshResult = null;
                                                }
                                                resultItem.result = [];

                                                Object.assign(obj,resultItem);
                                                let time_i = 100;
                                                self.$set(obj,'result',[]);
                                                resultArr.forEach(val=>{
                                                    setTimeout(()=>{
                                                        obj.result.push(val);
                                                    },time_i);
                                                    time_i+=100;
                                                })
                                                return;
                                            }
                                        })
                                    } else if (resultItem.groupId == 300) {
                                        let he = 0;
                                        resultItem.result.split(',').forEach((obj) => {
                                            he += parseInt(obj);
                                            if (obj.substring(0, 1) === '0') {
                                                param.push(obj.substring(1, 2));
                                            } else {
                                                param.push(obj);
                                            }
                                        });
                                        let resultArr = resultItem.result.split(',');
                                        if(resultItem.groupId==300){

                                            for(let i = 0;i<resultArr.length;i++){
                                                if(resultArr[i]<10){
                                                    resultArr[i] = '0'+resultArr[i];
                                                }

                                            }
                                        }
                                        resultItem.result = resultArr;
                                        let arr = [];
                                        let heOU = '';
                                        if (he >= 85 && he <= 132) {
                                            heOU = 'OVER';
                                        } else if (he >= 36 && he <= 83) {
                                            heOU = 'EVEN';
                                        } else if (he == 84) {
                                            heOU = 'draw';
                                        }

                                        let heOE = (he % 2 === 1) ? 'ODD' : 'EVEN';
                                        let sumOU = parseInt(he.toString().substring(he.toString().length - 1)) >= 5 ? 'weiover' : 'weiunder';
                                        let no1DT = parseInt(resultArr[0]) > parseInt(resultArr[7]) ? 'DRAGON' : 'TIGER';
                                        let no2DT = parseInt(resultArr[1]) > parseInt(resultArr[6]) ? 'DRAGON' : 'TIGER';
                                        let no3DT = parseInt(resultArr[2]) > parseInt(resultArr[5]) ? 'DRAGON' : 'TIGER';
                                        let no4DT = parseInt(resultArr[3]) > parseInt(resultArr[4]) ? 'DRAGON' : 'TIGER';
                                        arr = [he + '', heOU, heOE, sumOU, no1DT, no2DT, no3DT, no4DT];
                                        resultItem.special = arr;
                                        self.resultItems.find(obj=>{
                                            if(obj.lotteryKey == resultItem.lotteryKey  && resultItem.gameNo > obj.gameNo){
                                                if(obj.isRefreshResult){
                                                    clearInterval(obj.isRefreshResult);
                                                    obj.isRefreshResult = null;
                                                }
                                                resultItem.result = [];

                                                Object.assign(obj,resultItem);
                                                let time_i = 100;
                                                self.$set(obj,'result',[]);
                                                resultArr.forEach(val=>{
                                                    setTimeout(()=>{
                                                        obj.result.push(val);
                                                    },time_i);
                                                    time_i+=100;
                                                })
                                                return;
                                            }
                                        })
                                    }


                                }
                            });

                            if(self.resultItems.length==0){

                                self.resultItems = val.data;
                            }
                        }
                    }
                }).finally(()=>{

                })
            }
        },
        mounted(){
            this.initLotterys();
            clearInterval(this.lotteryRefreshTimer);
            this.lotteryRefreshTimer = null;
            this.lotteryRefreshTimer = setInterval(()=>{
                this.initLotterys();
            },5000);
        }
    }
</script>
