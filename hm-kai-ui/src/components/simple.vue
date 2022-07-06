<template>
    <!--    简洁版-->
    <div class="bodybox">
        <MyHeader></MyHeader>
        <div class="middbox" id="toRMC">
            <!--            <div class="leftmenu">-->
            <!--                <ul>-->
            <!--                    <li class="lisesected">-->
            <!--                        <a href="#toRMC" class="atoRMC">热门彩</a><i></i></li>-->
            <!--                    <li>-->
            <!--                        <a href="#toJISUC" class="atoJISUC">极速彩</a><i></i></li>-->
            <!--                    <li>-->
            <!--                        <a href="#toGPC" class="atoGPC">高频彩</a><i></i></li>-->
            <!--                    <li>-->
            <!--                        <a href="#toJWC" class="atoJWC">境外彩</a><i></i></li>-->
            <!--                    <li>-->
            <!--                        <a href="#toQGC" class="atoQGC">全国彩</a><i></i></li>-->
            <!--                </ul>-->
            <!--            </div>-->
            <div class="title">
                <dl>
                    <dt>热门彩种</dt>
                    <dd>
                        <a href="javascript:void(0)" class="showallbtn">显示全部</a>
                    </dd>
                </dl>
            </div>
            <div class="tablehead">
                <ul>
                    <li class="head" style="background: rgb(229, 229, 229);">
                        <span class="cz">彩种</span>
                        <span class="kjhm">开奖号码</span>
                        <span class="kjsj">开奖时间</span>
                        <span class="czlast">操作</span>
                    </li>
                    <li class="list">
                        <div class="cz">
                            <div class="imgheadl">
                                <a href="#"><img
                                    src="@/assets/img/small_logo/sgAirship.png" alt="SG飞艇"></a>
                            </div>
                            <div class="title">
                                <p class="lotName">SG飞艇</p>
                                <p class="number"><span class="preIssue preDrawIssue">20201013205</span>期</p>
                            </div>
                        </div>
                        <div class="kjhm class">
                            <div class="kajianhao">
                                <ul id="jnumber" class="numberbox" style="display: block;">
                                    <li class="numsm08 "><i style="font-size:10px; display:none">08</i></li>
                                    <li class="numsm06 "><i style="font-size:10px; display:none">06</i></li>
                                    <li class="numsm07 "><i style="font-size:10px; display:none">07</i></li>
                                    <li class="numsm10 "><i style="font-size:10px; display:none">10</i></li>
                                    <li class="numsm04 "><i style="font-size:10px; display:none">04</i></li>
                                    <li class="numsm09 "><i style="font-size:10px; display:none">09</i></li>
                                    <li class="numsm02 "><i style="font-size:10px; display:none">02</i></li>
                                    <li class="numsm01 "><i style="font-size:10px; display:none">01</i></li>
                                    <li class="numsm05 "><i style="font-size:10px; display:none">05</i></li>
                                    <li class="numsm03 li_after"><i style="font-size:10px; display:none">03</i></li>
                                </ul>
                                <div class="progress" style="display: none;"><img
                                    src="@/assets/img/icon/piaog.gif"></div>
                            </div>
                        </div>
                        <div class="kjsj class">
                            <div>
                                <ul>
                                    <li>
                                        <a href="#">开奖</a>
                                    </li>
                                    <li>|</li>
                                    <li>
                                        <a href="#">走势</a>
                                    </li>
                                </ul>
                            </div>
                            <div class="kaijintime">
                                <p class="opentyle" style="display: none;">开奖中...</p>
                                <div class="line linetime cuttime indextime" id="timebox" style="display: block;">
                                    <p>距离<span class="nextIssue">20201013206</span> 期开奖时间：
                                        <span class="bgtime hour" style="display: none;">00</span><span class="hourtxt"
                                                                                                        style="display: none;">时</span>
                                        <span class="bgtime minute">03</span>分
                                        <span class="bgtime second">34</span>秒
                                    </p>
                                </div>
                            </div>
                        </div>
                        <div class="czlast deletebtn"></div>
                    </li>
                    <li class="list" style="background: rgb(245, 245, 245);">
                        <div class="cz">
                            <div class="imgheadl">
                                <a href="#"><img
                                    src="@/assets/img/small_logo/xingyft.png" alt="幸运飞艇"></a>
                            </div>
                            <div class="title">
                                <p class="lotName">幸运飞艇</p>
                                <p class="number"><span class="preIssue preDrawIssue">20201013048</span>期</p>
                            </div>
                        </div>
                        <div class="kjhm class">
                            <div class="kajianhao">
                                <ul id="jnumber" class="numberbox" style="display: block;">
                                    <li class="numsm02 "><i style="font-size:10px; display:none">02</i></li>
                                    <li class="numsm06 "><i style="font-size:10px; display:none">06</i></li>
                                    <li class="numsm03 "><i style="font-size:10px; display:none">03</i></li>
                                    <li class="numsm08 "><i style="font-size:10px; display:none">08</i></li>
                                    <li class="numsm10 "><i style="font-size:10px; display:none">10</i></li>
                                    <li class="numsm09 "><i style="font-size:10px; display:none">09</i></li>
                                    <li class="numsm01 "><i style="font-size:10px; display:none">01</i></li>
                                    <li class="numsm07 "><i style="font-size:10px; display:none">07</i></li>
                                    <li class="numsm05 "><i style="font-size:10px; display:none">05</i></li>
                                    <li class="numsm04 li_after"><i style="font-size:10px; display:none">04</i></li>
                                </ul>
                                <div class="progress" style="display: none;"><img
                                    src="@/assets/img/icon/piaog.gif"></div>
                            </div>
                        </div>
                        <div class="kjsj class">
                            <div>
                                <ul>
                                    <li>
                                        <a href="#">开奖</a>
                                    </li>
                                    <li>|</li>
                                    <li>
                                        <a href="#">走势</a>
                                    </li>
                                </ul>
                            </div>
                            <div class="kaijintime">
                                <p class="opentyle" style="display: none;">开奖中...</p>
                                <div class="line linetime cuttime indextime" id="timebox" style="display: block;">
                                    <p>距离<span class="nextIssue">20201013049</span> 期开奖时间：
                                        <span class="bgtime hour" style="display: none;">00</span><span class="hourtxt"
                                                                                                        style="display: none;">时</span>
                                        <span class="bgtime minute">02</span>分
                                        <span class="bgtime second">19</span>秒
                                    </p>
                                </div>
                            </div>
                        </div>
                        <div class="czlast deletebtn"></div>
                    </li>
                    <li class="list">
                        <div class="cz">
                            <div class="imgheadl">
                                <a href="#"><img src="@/assets/img/beijinpk.png"
                                                 alt="北京PK10"></a>
                            </div>
                            <div class="title">
                                <p class="lotName">北京PK10</p>
                                <p class="number"><span class="preIssue preDrawIssue">751150</span>期</p>
                            </div>
                        </div>
                        <div class="kjhm class">
                            <div class="kajianhao">
                                <ul id="jnumber" class="numberbox" style="display: block;">
                                    <li class="numsm03 "><i style="font-size:10px; display:none">03</i></li>
                                    <li class="numsm08 "><i style="font-size:10px; display:none">08</i></li>
                                    <li class="numsm07 "><i style="font-size:10px; display:none">07</i></li>
                                    <li class="numsm05 "><i style="font-size:10px; display:none">05</i></li>
                                    <li class="numsm02 "><i style="font-size:10px; display:none">02</i></li>
                                    <li class="numsm01 "><i style="font-size:10px; display:none">01</i></li>
                                    <li class="numsm09 "><i style="font-size:10px; display:none">09</i></li>
                                    <li class="numsm10 "><i style="font-size:10px; display:none">10</i></li>
                                    <li class="numsm06 "><i style="font-size:10px; display:none">06</i></li>
                                    <li class="numsm04 li_after"><i style="font-size:10px; display:none">04</i></li>
                                </ul>
                                <div class="progress" style="display: none;"><img
                                    src="@/assets/img/icon/piaog.gif"></div>
                            </div>
                        </div>
                        <div class="kjsj class">
                            <div>
                                <ul>
                                    <li>
                                        <a href="#">开奖</a>
                                    </li>
                                    <li>|</li>
                                    <li>
                                        <a href="#">走势</a>
                                    </li>
                                </ul>
                            </div>
                            <div class="kaijintime">
                                <p class="opentyle" style="display: none;">开奖中...</p>
                                <div class="line linetime cuttime indextime" id="timebox" style="display: block;">
                                    <p>距离<span class="nextIssue">751151</span> 期开奖时间：
                                        <span class="bgtime hour" style="display: none;">00</span><span class="hourtxt"
                                                                                                        style="display: none;">时</span>
                                        <span class="bgtime minute">04</span>分
                                        <span class="bgtime second">14</span>秒
                                    </p>
                                </div>
                            </div>
                        </div>
                        <div class="czlast deletebtn"></div>
                    </li>
                    <li class="list" style="background: rgb(245, 245, 245);">
                        <div class="cz">
                            <div class="imgheadl">
                                <a href="#"><img src="@/assets/img/small_logo/chongqingnc.png" alt="幸运农场"></a>
                            </div>
                            <div class="title">
                                <p class="lotName">重庆幸运农场</p>
                                <p class="number"><span class="preIssue preDrawIssue">20201014035</span>期</p>
                            </div>
                        </div>
                        <div class="kjhm class">
                            <div class="kajianhao">
                                <ul class="numberbox" style="display: block;">
                                    <li class="ncnum09"><i style="font-size:10px;display:none">09</i></li>
                                    <li class="ncnum16"><i style="font-size:10px;display:none">16</i></li>
                                    <li class="ncnum06"><i style="font-size:10px;display:none">06</i></li>
                                    <li class="ncnum17"><i style="font-size:10px;display:none">17</i></li>
                                    <li class="ncnum03"><i style="font-size:10px;display:none">03</i></li>
                                    <li class="ncnum19"><i style="font-size:10px;display:none">19</i></li>
                                    <li class="ncnum20"><i style="font-size:10px;display:none">20</i></li>
                                    <li class="ncnum05"><i style="font-size:10px;display:none">05</i></li>
                                </ul>
                                <div class="progress" style="display: none;"><img src="@/assets/img/icon/piaog.gif">
                                </div>
                            </div>
                        </div>
                        <div class="kjsj class">
                            <div>
                                <ul>
                                    <li>
                                        <a href="#">开奖</a>
                                    </li>
                                    <li>|</li>
                                    <li>
                                        <a href="#">走势</a>
                                    </li>
                                    <!--<li>|</li>
                                    <li>
                                        <a href="">遗漏</a>
                                    </li>-->
                                </ul>
                            </div>
                            <div class="kaijintime">
                                <p class="opentyle" style="display: none;">开奖中...</p>
                                <div class="line linetime cuttime indextime" id="timebox" style="display: block;">
                                    <p>距离<span class="nextIssue">20201014036</span> 期开奖时间：
                                        <span class="bgtime hour" style="display: none;">00</span><span class="hourtxt"
                                                                                                        style="display: none;">时</span>
                                        <span class="bgtime minute">15</span>分
                                        <span class="bgtime second">15</span>秒
                                    </p>
                                </div>
                            </div>
                        </div>
                        <div class="czlast deletebtn"></div>
                    </li>
                    <li class="list">
                        <div class="cz">
                            <div class="imgheadl">
                                <a href="#"><img src="@/assets/img/small_logo/guangdongklsf.png" alt="广东快乐十分"></a>
                            </div>
                            <div class="title">
                                <p class="lotName">广东快乐十分</p>
                                <p class="number"><span class="preIssue preDrawIssue">2020101419</span>期</p>
                            </div>
                        </div>
                        <div class="kjhm class">
                            <div class="kajianhao">
                                <ul class="numberbox" style="display: block;">
                                    <li class="numblueHead" style="padding-top: 0px; line-height: 36px;">12</li>
                                    <li class="numblueHead" style="padding-top: 0px; line-height: 36px;">13</li>
                                    <li class="numblueHead" style="padding-top: 0px; line-height: 36px;">11</li>
                                    <li class="numblueHead" style="padding-top: 0px; line-height: 36px;">03</li>
                                    <li class="numblueHead" style="padding-top: 0px; line-height: 36px;">05</li>
                                    <li class="numblueHead" style="padding-top: 0px; line-height: 36px;">08</li>
                                    <li class="numblueHead" style="padding-top: 0px; line-height: 36px;">10</li>
                                    <li class="numblueHead numred"
                                        style="margin-right: 0px; padding-top: 0px; line-height: 36px;">19
                                    </li>
                                </ul>
                            </div>
                        </div>
                        <div class="kjsj class">
                            <div>
                                <ul>
                                    <li>
                                        <a href="#">开奖</a>
                                    </li>
                                    <li>|</li>
                                    <li>
                                        <a href="#">走势</a>
                                    </li>
                                </ul>
                            </div>
                            <div class="kaijintime">
                                <p class="opentyle" style="display: none;">开奖中...</p>
                                <div class="line linetime cuttime indextime" id="timebox" style="display: block;">
                                    <p>距离<span class="nextIssue">2020101420</span> 期开奖时间：
                                        <span class="bgtime hour" style="display: none;">00</span><span class="hourtxt"
                                                                                                        style="display: none;">时</span>
                                        <span class="bgtime minute">11</span>分
                                        <span class="bgtime second">54</span>秒
                                    </p>
                                </div>
                            </div>
                        </div>
                        <div class="czlast deletebtn"></div>
                    </li>

                </ul>
            </div>
        </div>
        <MyFoot></MyFoot>
    </div>
</template>

<script>
    import MyHeader from '@/components/layout/head'
    import MyFoot from '@/components/layout/foot'
    export default {
        data() {
            return {}
        },
        components: {
            MyHeader,

            MyFoot,
        },
        methods: {},
    }
</script>
