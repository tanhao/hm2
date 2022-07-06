<template>
    <div class="bodybox">
        <MyHeader></MyHeader>
        <!--计划开始-->
        <div id="bgColor" class="pk10Style">
            <div class="margt20 " id="bgStyle">
                <div class="headInfo bannerBg">
                    <div class="scaleDiv">
                        <div id="leftListDiv">
                            <div class="leftList leftList_active" id="leftL_pk10" style="top: 164.5px;">
                                <div class="bg_head"></div>
                                <ul>
                                    <li :class="lotteryKey=='speed10'?'setok':''" @click="changeGame('speed10')">
                                        <b>极速赛车</b></li>
                                    <b class="bg_b"></b>
                                    <li :class="lotteryKey=='lucky10'?'setok':''" @click="changeGame('lucky10')"><b>澳洲幸运10</b>
                                    </li>
                                    <b class="bg_b"></b>
                                    <li :class="lotteryKey=='sbspeed10'?'setok':''" @click="changeGame('sbspeed10')"><b>极速飞艇</b>
                                    </li>
                                    <b class="bg_b"></b>
                                </ul>
                                <span id="backTop" class="backTop"><i></i></span></div>
                        </div>
                        <div class="detailMess" id="detailMess">
                            <div class="logoBox">
                                <span :class="lotteryKey"></span>
                                <span class="caiName" id="caiName">{{$t(lotteryKey)}}</span></div>
                            <div class="cutimeBox">
                                <div class="timebox">
                                    <div class="toptxt">倒计时</div>
                                    <div id="kaiStyle">
                                        <div id="hourDiv"><span id="horFir" class="clearCont">0</span><span id="horSec"
                                                                                                            class="clearCont">0</span>
                                            <span>时</span>
                                        </div>
                                        <span id="minFir" class="clearCont">{{returnLotteryTime(gameInfo).m1}}</span>
                                        <span id="minSec" class="clearCont">{{returnLotteryTime(gameInfo).m2}}</span>
                                        <span>分</span> <span id="secFir" class="clearCont">{{returnLotteryTime(gameInfo).s1}}</span>
                                        <span id="secTwo" class="clearCont">{{returnLotteryTime(gameInfo).s2}}</span>
                                        <span>秒</span>
                                    </div>
                                    <div id="openStyle" style="display: none;">开奖中...</div>
                                    <div id="hasIssue"> 已推荐<i
                                        id="hasIssueI">{{pageType==2?killCount:newPlan.planCount}}</i>期
                                    </div>
                                </div>
                                <span class="stopbox"
                                      style="display:none;text-align:center;width:100%;color: #ff0b0b;font-size:20px;height:170px;line-height:170px">停止销售</span>
                            </div>
                            <div class="planBox" id="planBox">
                                <div class="toptxt">最新计划</div>
                                <ul>
                                    <li v-for="num in newPlan.planA" :class="'pk10_'+num*1"></li>
                                </ul>
                                <div class="character"><span class="span_1"><i id="qishu">{{newPlan.gameNo}}</i>期</span>
                                    <span
                                        class="span_2">本次成本：<i id="curCost">{{newPlan.costA}}</i></span></div>
                            </div>
                            <div class="dataBox">
                                <div class="toptxt">数据汇总</div>
                                <span class="sumSpan">累计成本<i id="sumCost">{{newPlan.costAllA}}</i></span>
                                <span class="kuiSpan">累计盈亏<i id="yinkui">{{newPlan.profitA}}</i></span></div>
                        </div>
                    </div>
                </div>

                <div class="listcontent">
                    <div class="change_til">
                        <div class="ch-left">
                            <a _href="#" class="check_a zhao_a" @click="pageType=1">购彩计划</a>
                            <a _href="#" class="check_b check_a" id="zhuiA" @click="pageType=2">稳赢杀号</a>
                        </div>
                        <div class="ch-right">
                            <div class="listheadrr">
                                <div class="rightime gh">
                                    <datepicker v-model="dateStr" :format="customFormatter"
                                                :language="languages.zh"></datepicker>
                                </div>
                            </div>
                            <span v-if="selectState">
                                <select v-model="selectCount" @change="changeSelect">
                                <option v-for="item in countsItems" :key="item.id" :value="item.id">近{{item.id}}期
                                </option>
                            </select>
                            </span>

                        </div>
                    </div>
                    <div class="view" v-if="pageType==1">
                        <div id="hint">
                            <span><i class="tips">提示说明：</i>每天推荐计划时间段，</span>
                            <span>时间段一<i>{{lotteryPlan.plan1Time1}}-{{lotteryPlan.plan1Time2}}</i></span>
                            <span>时间段二<i>{{lotteryPlan.plan2Time1}}-{{lotteryPlan.plan2Time2}}</i></span>
                            <span v-if="lotteryPlan.plan3Time1!='00:00:00'">时间段三<i>{{lotteryPlan.plan3Time1}}-{{lotteryPlan.plan3Time2}}</i></span>
                        </div>
                        <div class="ran">
                            <!--购彩计划-->
                            <ul class="11check5_ran ">
                                <li :class="checkRan.one?'check_ran':''" data-text="A" @click="checkRanChange(1)">计划一
                                </li>
                                <li :class="checkRan.two?'check_ran':''" data-text="B" @click="checkRanChange(2)">计划二
                                </li>
                                <li :class="checkRan.three?'check_ran':''" data-text="C" @click="checkRanChange(3)">
                                    计划三
                                </li>
                            </ul>
                        </div>
                        <div class="view_content">
                            <div class="ZHao ZHao_1" style="display: block;">
                                <div class="zuHao_plan">
                                    <ul class="zhuiHao_title">
                                        <li>开奖期数</li>
                                        <li>开奖结果</li>
                                        <li class="check_zhuOrXa">冠军购彩 计划一</li>
                                        <li>当前成本</li>
                                        <li>累计成本</li>
                                        <li>赢利/亏损</li>
                                        <li>开奖时间</li>
                                    </ul>
                                    <template v-for="his in copyResultPlanList">
                                        <ul class="xhuiHao_content" v-if="checkRan.one">
                                            <li>{{his.gameNo}}期</li>
                                            <li>
                                                <ul class="opactionUl">
                                                    <li v-for="num,index in his.result" :class="'opaction pk10_'+num*1"
                                                        v-if="index==0"><i v-if="his.winAno" class="resultCheckLi"></i>
                                                    </li>
                                                    <li v-for="num,index in his.result" :class="'pk10_'+num*1"
                                                        v-if="index!=0"></li>
                                                </ul>
                                            </li>
                                            <li>
                                                <ul class="opactionUl">

                                                    <li v-for="num,index in his.planA" :class="'opaction pk10_'+num*1">
                                                        <i :class="num == his.winAno?'resultCheckLi':''"></i></li>
                                                </ul>
                                            </li>
                                            <li>{{his.costA}}</li>
                                            <li>{{his.costAllA}}</li>
                                            <li class="resultRed">{{his.profitA}}</li>
                                            <li>{{$moment(his.actionTime).format('hh:mm:ss')}}</li>
                                        </ul>
                                        <ul class="xhuiHao_content" v-if="checkRan.two">
                                            <li>{{his.gameNo}}期</li>
                                            <li>
                                                <ul class="opactionUl">
                                                    <li v-for="num,index in his.result" :class="'opaction pk10_'+num*1"
                                                        v-if="index==0"><i v-if="his.winBno" class="resultCheckLi"></i>
                                                    </li>
                                                    <li v-for="num,index in his.result" :class="'pk10_'+num*1"
                                                        v-if="index!=0"></li>
                                                </ul>
                                            </li>
                                            <li>
                                                <ul class="opactionUl" style="width: 177px !important;">
                                                    <li v-for="num,index in his.planB" :class="'opaction pk10_'+num*1">
                                                        <i :class="num == his.winBno?'resultCheckLi':''"></i></li>
                                                </ul>
                                            </li>
                                            <li>{{his.costB}}</li>
                                            <li>{{his.costAllB}}</li>
                                            <li class="resultRed">{{his.profitB}}</li>
                                            <li>{{$moment(his.actionTime).format('hh:mm:ss')}}</li>
                                        </ul>
                                        <ul class="xhuiHao_content" v-if="checkRan.three">
                                            <li>{{his.gameNo}}期</li>
                                            <li>
                                                <ul class="opactionUl">
                                                    <li v-for="num,index in his.result" :class="'opaction pk10_'+num*1"
                                                        v-if="index==0"><i v-if="his.winCno" class="resultCheckLi"></i>
                                                    </li>
                                                    <li v-for="num,index in his.result" :class="'pk10_'+num*1"
                                                        v-if="index!=0"></li>
                                                </ul>
                                            </li>
                                            <li>
                                                <ul class="opactionUl">
                                                    <li v-for="num,index in his.planC" :class="'opaction pk10_'+num*1">
                                                        <i :class="num == his.winCno?'resultCheckLi':''"></i></li>
                                                </ul>
                                            </li>
                                            <li>{{his.costC}}</li>
                                            <li>{{his.costAllC}}</li>
                                            <li class="resultRed">{{his.profitC}}</li>
                                            <li>{{$moment(his.actionTime).format('hh:mm:ss')}}</li>
                                        </ul>

                                    </template>
                                </div>
                                <!--计划下方说明-->
                                <div class="zshao_text zhuihaoCla" style="display: block;">
                                    <h3>功能介绍</h3>
                                    <p class="powP"><span class="cp_name">{{$t(lotteryKey)}}</span><span
                                        class="cp_check">计划一</span>购彩计划：依据<span
                                        class="cp_name">{{$t(lotteryKey)}}</span>历史大数据规律为基础，提供N组购彩计划参考，建议使用阶梯倍投方法投注，如果连续10期没有成功则标记为失败
                                    </p>
                                    <h3 class="h3_two">本推荐玩法</h3>
                                    <p><span class="cp_name">{{$t(lotteryKey)}}</span><span class="cp_check">计划一</span>追踪第一球开奖号码，以倍投的形式，第一期以1为基数，以此类推，连续十次不中则更新计划。
                                    </p>
                                    <h3 class="h3_two">使用技巧</h3>
                                    <p><span class="cp_name">{{$t(lotteryKey)}}</span><span class="cp_check">计划一</span>购彩计划，建议使用，阶梯倍投方式追号投注。
                                    </p>
                                    <h3 class="h3_two">推荐计划</h3>
                                    <p><span class="cp_name">{{$t(lotteryKey)}}</span><span class="cp_check">计划一</span>推荐计划，以此类推，连续十次不中则更新计划
                                        推荐计划：三种方案的推荐计划分别对应不同个数的号码，号码越多中奖率越高，但是中奖额度较小，风险
                                        越大，收益越高。比如：计划1为5个号码，所需要的成本为5，假如赔率是10，则赢5;如果是计划3， 有3个号码，成本为3,则赢7。</p>
                                    <h3 class="h3_two">当前成本</h3>
                                    <p><span class="cp_name">{{$t(lotteryKey)}}</span><span class="cp_check">计划一</span>当前成本，指当前期购彩号码成本，每个计划的成本以号码多少为基准。
                                    </p>
                                    <h3 class="h3_two">累计成本</h3>
                                    <p><span class="cp_name">{{$t(lotteryKey)}}</span><span class="cp_check">计划一</span>累计成本，指从推荐第一期开始计算所用总成本。
                                    </p>
                                    <h3 class="h3_two">赢亏</h3>
                                    <p><span class="cp_name">{{$t(lotteryKey)}}</span><span class="cp_check">计划一</span>赢亏，根据试玩的赔率计算得来，红为赢，蓝为亏。
                                    </p>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="view" v-if="pageType==2">
                        <div class="ran">
                            <ul class="pk10_ran">
                                <li data-text="first" style="border-color: rgb(224, 224, 224);" :class="killType==0?'check_ran':''" @click="killType=0">冠军</li>
                                <li data-text="second" style="border-color: rgb(224, 224, 224);" :class="killType==1?'check_ran':''" @click="killType=1">亚军</li>
                                <li data-text="third" style="border-color: rgb(224, 224, 224);" :class="killType==2?'check_ran':''" @click="killType=2">第三名</li>
                                <li data-text="fourth" style="border-color: rgb(224, 224, 224);" :class="killType==3?'check_ran':''" @click="killType=3">第四名</li>
                                <li data-text="fifth" style="border-color: rgb(224, 224, 224);" :class="killType==4?'check_ran':''" @click="killType=4">第五名</li>
                                <li data-text="sixth" style="border-color: rgb(224, 224, 224);" :class="killType==5?'check_ran':''" @click="killType=5">第六名</li>
                                <li data-text="seven" style="border-color: rgb(224, 224, 224);" :class="killType==6?'check_ran':''" @click="killType=6">第七名</li>
                                <li data-text="eight" style="border-color: rgb(224, 224, 224);" :class="killType==7?'check_ran':''" @click="killType=7">第八名</li>
                                <li data-text="nine" style="border-color: rgb(224, 224, 224);" :class="killType==8?'check_ran':''" @click="killType=8">第九名</li>
                                <li data-text="ten" style="border-color: rgb(224, 224, 224);" :class="killType==9?'check_ran':''" @click="killType=9">第十名</li>
                            </ul>

                        </div>
                        <div class="view_content">

                            <div class="XHao" style="display: block;">
                                <div class="list_box">
                                    <ul class="list-title">
                                        <li>开奖期号</li>
                                        <li class="sumlili" style="width: 388px !important;">开奖结果</li>
                                        <li class="nameUl pk10_name">冷雨夜</li>
                                        <li>成绩</li>
                                        <li class="nameUl pk10_name">木木三</li>
                                        <li>成绩</li>
                                        <li class="nameUl pk10_name">醉书生</li>
                                        <li>成绩</li>
                                        <li class="nameUl pk10_name">司令爷</li>
                                        <li>成绩</li>
                                        <li class="nameUl pk10_name">沐子眠</li>
                                        <li>成绩</li>
                                    </ul>
                                    <ul class="list-content" v-for="itemKill in copyResultKillList">
                                        <li>{{itemKill.gameNo}}期</li>
                                        <li>
                                            <span v-if="!itemKill.result">等待开奖</span>
                                            <span v-if="itemKill.result">
                                                <ul class="num_ul" >
                                                    <li v-for="num in itemKill.result" :class="'pk10_'+num*1"></li>
                                                </ul>
                                            </span>
                                        </li>
                                        <li :class="!itemKill.kill1?'':itemKill.kill1[killType]==1?'col_red':'col_blue'" >杀:{{itemKill.user1[killType]}}</li>
                                        <li :class="!itemKill.kill1?'':itemKill.kill1[killType]==1?'bg_red':'col_blue'">{{!itemKill.kill1?'-':itemKill.kill1[killType]==1?'杀对':'杀错'}}</li>
                                         <li :class="!itemKill.kill2?'':itemKill.kill2[killType]==1?'col_red':'col_blue'" >杀:{{itemKill.user2[killType]}}</li>
                                        <li :class="!itemKill.kill2?'':itemKill.kill2[killType]==1?'bg_red':'col_blue'">{{!itemKill.kill2?'-':itemKill.kill2[killType]==1?'杀对':'杀错'}}</li>
                                         <li :class="!itemKill.kill3?'':itemKill.kill3[killType]==1?'col_red':'col_blue'" >杀:{{itemKill.user3[killType]}}</li>
                                        <li :class="!itemKill.kill3?'':itemKill.kill3[killType]==1?'bg_red':'col_blue'">{{!itemKill.kill3?'-':itemKill.kill3[killType]==1?'杀对':'杀错'}}</li>
                                         <li :class="!itemKill.kill4?'':itemKill.kill4[killType]==1?'col_red':'col_blue'" >杀:{{itemKill.user4[killType]}}</li>
                                        <li :class="!itemKill.kill4?'':itemKill.kill4[killType]==1?'bg_red':'col_blue'">{{!itemKill.kill4?'-':itemKill.kill4[killType]==1?'杀对':'杀错'}}</li>
                                         <li :class="!itemKill.kill5?'':itemKill.kill5[killType]==1?'col_red':'col_blue'" >杀:{{itemKill.user5[killType]}}</li>
                                        <li :class="!itemKill.kill5?'':itemKill.kill5[killType]==1?'bg_red':'col_blue'">{{!itemKill.kill5?'-':itemKill.kill5[killType]==1?'杀对':'杀错'}}</li>

                                    </ul>
                                    <ul class="list_footer list_footerTil nameUl pk10_name">
                                        <li>成绩统计</li>
                                        <li>冷雨夜</li>
                                        <li>木木三</li>
                                        <li>醉书生</li>
                                        <li>司令爷</li>
                                        <li>沐子眠</li>
                                    </ul>
                                    <ul class="list_footer remover_foot">
                                        <li>杀对次数</li>
                                        <li>{{selectState?selectCount:killCount}}期对{{killWin[0][killType]}}期</li>
                                        <li>{{selectState?selectCount:killCount}}期对{{killWin[1][killType]}}期</li>
                                        <li>{{selectState?selectCount:killCount}}期对{{killWin[2][killType]}}期</li>
                                        <li>{{selectState?selectCount:killCount}}期对{{killWin[3][killType]}}期</li>
                                        <li>{{selectState?selectCount:killCount}}期对{{killWin[4][killType]}}期</li>
                                    </ul>
                                    <ul class="list_footer remover_foot">
                                        <li>成功概率</li>
                                        <li>
                                            {{selectState?parseInt(killWin[0][killType]/selectCount*100):parseInt(killWin[0][killType]/killCount*100)}}%
                                        </li>
                                        <li>
                                            {{selectState?parseInt(killWin[1][killType]/selectCount*100):parseInt(killWin[1][killType]/killCount*100)}}%
                                        </li>
                                        <li>
                                            {{selectState?parseInt(killWin[2][killType]/selectCount*100):parseInt(killWin[2][killType]/killCount*100)}}%
                                        </li>
                                        <li>
                                            {{selectState?parseInt(killWin[3][killType]/selectCount*100):parseInt(killWin[3][killType]/killCount*100)}}%
                                        </li>
                                        <li>
                                            {{selectState?parseInt(killWin[4][killType]/selectCount*100):parseInt(killWin[4][killType]/killCount*100)}}%
                                        </li>
                                    </ul>

                                </div>
                                <!--计划下方说明-->
                                <div class="zshao_text zhuihaoCla" style="display: none;">
                                    <h3>功能介绍</h3>
                                    <p class="powP"><span class="cp_name">极速赛车</span><span class="cp_check">冠军</span>购彩计划：依据<span
                                        class="cp_name">极速赛车</span>历史大数据规律为基础，提供N组购彩计划参考，建议使用阶梯倍投方法投注，如果连续10期没有成功则标记为失败
                                    </p>
                                    <h3 class="h3_two">使用技巧</h3>
                                    <p><span class="cp_name">极速赛车</span><span class="cp_check">冠军</span>购彩计划，建议使用，阶梯倍投方式追号投注。
                                    </p>
                                </div>
                                <div class="zshao_text sahaoCla" style="display: block;">
                                    <h3>功能介绍</h3>
                                    <p><span class="cp_name">极速赛车</span><span class="cp_check">冠军</span>杀号公式，依据<span
                                        class="cp_name">极速赛车</span>历史开奖数据规律为依托，排除出现概率最小的<span
                                        class="cp_name">极速赛车</span>号码，<span
                                        class="cp_name">极速赛车</span>综合杀号公式是有效提高缩小<span class="cp_name">极速赛车</span>投注范围的一种方法指导。
                                    </p>
                                    <h3 class="h3_two">使用技巧</h3>
                                    <p><span class="cp_name">极速赛车</span><span class="cp_check">冠军</span>杀号公式公式A~E，选择几种成绩率高的<span
                                        class="cp_name">极速赛车</span><span class="cp_check">冠军</span>杀号公式的公式结果即可，并非全部<span
                                        class="cp_check">冠军</span>杀号公式都做为彩票投注依托。</p>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <MyFoot></MyFoot>
    </div>
</template>
<script>
    import MyHeader from '@/components/layout/head'
    import MyFoot from '@/components/layout/foot'
    import Datepicker from 'vuejs-datepicker';
    import {en, ja, zh} from 'vuejs-datepicker/dist/locale'

    export default {
        data() {
            return {
                dateStr: "",
                languages: {en: en, zh: zh, jp: ja},
                highlightedFn: {
                    customPredictor(date) {
                        if (date.getDate() === (new Date()).getDate()) {
                            return true;
                        }
                    }
                },
                lotteryRefreshTimer: null,
                serverTimer: null,
                serverTime: '',
                gameInfo: {lotteryKey: null, groupId: null},
                lotteryPlan: {},
                planList: [],
                newPlan: {
                    planCount: 0
                },
                resultPlanList: [],
                copyResultPlanList: [],
                resultKillList: [],
                copyResultKillList: [],
                lotteryKey: 'speed10',
                checkRan: {
                    one: true,
                    two: false,
                    three: false
                },
                selectCount: 10,
                countsItems: [
                    {id: 10},
                    {id: 20},
                    {id: 30},
                    {id: 50},
                    {id: 100}
                ],
                pageType: 1,
                killType: 0,
                killCount: 0,
                selectState: true,
                killWin: [],
            }
        },
        components: {
            MyHeader,
            MyFoot,
            Datepicker,
        },
        computed: {
            returnLotteryTime(item) {
                let _this = this;
                return (item) => {
                    let open_time = parseInt((new Date(item.nextActionTime).getTime() / 1000) - _this.serverTime / 1000);
                    if (open_time > 0) {
                        if (this.lotteryRefreshTimer) {
                            clearInterval(this.lotteryRefreshTimer);
                            this.lotteryRefreshTimer = null;
                        }
                        _this.$set(item, "isOpenStatus", true);
                        clearInterval(item.isRefreshResult);
                        _this.$set(item, 'isRefreshResult', null);
                        let m = _this.formatTime(parseInt(open_time / 60));
                        let s = _this.formatTime(parseInt(open_time % 60));
                        let obj = {'m1': m.charAt(1), 'm2': m.charAt(2), 's1': s.charAt(1), 's2': s.charAt(2)};
                        return obj;
                    } else {
                        if (!this.lotteryRefreshTimer) {
                            this.lotteryRefreshTimer = setInterval(() => {
                                this.initLotterys();
                            }, 5000);
                        }
                        if (item.isOpenStatus) {
                            item.isRefreshResult = setInterval(() => {
                                item.result.sort(function () {
                                    return Math.random() - 0.5
                                })
                            }, 100);
                        }
                        _this.$set(item, "isOpenStatus", false);
                        return {'m1': 0, 'm2': 0, 's1': 0, 's2': 0};
                    }

                }
            }
        },
        methods: {
            customFormatter(date) {
                this.dateStr = this.$moment(date).format('YYYY-MM-DD');
                this.getPlanList();
                return this.dateStr;
            },
            changeSelect() {
                let _this = this;
                this.copyResultPlanList = JSON.parse(JSON.stringify(_this.resultPlanList));
                this.copyResultKillList = JSON.parse(JSON.stringify(_this.resultKillList));
                let newDay = this.$moment(new Date()).format('YYYY-MM-DD');
                this.selectState = false;
                if (newDay == this.dateStr) {
                    this.copyResultPlanList = this.copyResultPlanList.slice(0, _this.selectCount + 1);
                    this.copyResultKillList = this.copyResultKillList.slice(0, _this.selectCount + 1);
                    this.selectState = true;
                }
                this.killWin = [];
                let user1 = [0, 0, 0, 0, 0, 0, 0, 0, 0, 0];
                let user2 = [0, 0, 0, 0, 0, 0, 0, 0, 0, 0];
                let user3 = [0, 0, 0, 0, 0, 0, 0, 0, 0, 0];
                let user4 = [0, 0, 0, 0, 0, 0, 0, 0, 0, 0];
                let user5 = [0, 0, 0, 0, 0, 0, 0, 0, 0, 0];
                this.copyResultKillList.forEach(items => {
                    if (items.result) {
                        for (let i = 0; i < items.kill1.length; i++) {
                            if (items.kill1[i] == 1) user1[i] += items.kill1[i];
                            if (items.kill2[i] == 1) user2[i] += items.kill2[i];
                            if (items.kill3[i] == 1) user3[i] += items.kill3[i];
                            if (items.kill4[i] == 1) user4[i] += items.kill4[i];
                            if (items.kill5[i] == 1) user5[i] += items.kill5[i];
                        }

                    }
                })
                this.killWin.push(user1);
                this.killWin.push(user2);
                this.killWin.push(user3);
                this.killWin.push(user4);
                this.killWin.push(user5);
            },
            getPlanAll() {
                this.$api.Lottery.getLotteryPlan().then(val => {
                    this.planList = [];
                    if (val.success) {
                        this.planList = val.data;
                        this.lotteryPlan = val.data.find(v => v.lotteryKey == this.lotteryKey);
                    }
                })
            },
            getPlanList() {
                this.$api.Lottery.getPlanByList(this.lotteryKey + "/" + this.dateStr).then(val => {
                    this.resultPlanList = [];
                    this.resultKillList = [];
                    if (val.success) {
                        this.killCount = val.data.killCount;
                        this.resultPlanList = val.data.planDate;
                        this.resultPlanList.forEach(items => {
                            let planA = items.planA.split(",");
                            let planB = items.planB.split(",");
                            let planC = items.planC.split(",");
                            items.planA = planA.sort(function (a, b) {
                                return a - b
                            });
                            items.planB = planB.sort(function (a, b) {
                                return a - b
                            });
                            items.planC = planC.sort(function (a, b) {
                                return a - b
                            });
                            if (items.result) {
                                let nums = items.result.split(",");
                                items.result = nums;
                            }
                        })
                        this.getNewPlan();
                        this.resultKillList = val.data.killDate;
                        this.resultKillList.forEach(items => {
                            items.result = eval(items.result);
                            items.user1 = eval(items.user1);
                            items.kill1 = eval(items.kill1);
                            items.user2 = eval(items.user2);
                            items.kill2 = eval(items.kill2);
                            items.user3 = eval(items.user3);
                            items.kill3 = eval(items.kill3);
                            items.user4 = eval(items.user4);
                            items.kill4 = eval(items.kill4);
                            items.user5 = eval(items.user5);
                            items.kill5 = eval(items.kill5);
                        })
                        this.changeSelect();
                    }
                })
            },
            getNewPlan() {
                this.$api.Lottery.getPlanByNew(this.lotteryKey).then(val => {
                    if (val.success) {
                        let items = val.data;
                        let planA = items.planA.split(",");
                        let planB = items.planB.split(",");
                        let planC = items.planC.split(",");
                        items.planA = planA.sort(function (a, b) {
                            return a - b
                        });
                        items.planB = planB.sort(function (a, b) {
                            return a - b
                        });
                        items.planC = planC.sort(function (a, b) {
                            return a - b
                        });
                        this.newPlan = items;
                    }
                })
            },
            changeGame(type) {
                this.lotteryKey = type;
                this.getPlanAll();
                this.getPlanList();
            },
            checkRanChange(type) {
                this.checkRan.one = type == 1 ? true : false;
                this.checkRan.two = type == 2 ? true : false;
                this.checkRan.three = type == 3 ? true : false;
            },
            setTime(time) {
                let m = this.formatTime(parseInt(time / 60));
                let s = this.formatTime(parseInt(time % 60));
                console.log(m)
                let obj = {'m1': m.charAt(1), 'm2': m.charAt(2), 's1': s.charAt(1), 's2': s.charAt(2)};
                return obj;
            },
            formatTime(time) {
                if (time >= 100) {
                    return time + '';
                } else if (time >= 10) {
                    return '0' + time;
                } else {
                    return '00' + time;
                }
            },
            closeAllTime() {
                clearInterval(this.serverTimer);
                this.serverTimer = null;
            },
            initLotterys() {
                let _this = this;
                this.$api.Lottery.getLotteryNow(_this.lotteryKey).then(val => {
                    if (val.success) {
                        if (val.data) {
                            _this.closeAllTime();
                            _this.serverTime = val.serverTime;
                            _this.serverTimer = setInterval(() => {
                                _this.serverTime += 1000;
                            }, 1000);
                            let resultArr = val.data.result.split(',');
                            if (val.data.groupId == 300) {

                                for (let i = 0; i < resultArr.length; i++) {
                                    if (resultArr[i] < 10) {
                                        resultArr[i] = '0' + resultArr[i];
                                    }

                                }
                            }
                            val.data.result = resultArr;
                            if (_this.gameInfo.lotteryKey) {
                                _this.$set(_this.gameInfo, 'isOpenStatus', true);
                                _this.gameInfo.result = resultArr;
                                if (_this.gameInfo.isRefreshResult) {
                                    clearInterval(_this.gameInfo.isRefreshResult);
                                    _this.gameInfo.isRefreshResult = null;
                                }
                                //_this.gameInfo.result = [];
                                if (val.data.gameNo > _this.gameInfo.gameNo) {
                                    Object.assign(_this.gameInfo, val.data);
                                    let time_i = 100;
                                    _this.$set(_this.gameInfo, 'result', []);
                                    resultArr.forEach(val => {
                                        setTimeout(() => {
                                            _this.gameInfo.result.push(val);
                                        }, time_i);
                                        time_i += 100;
                                    })
                                    return;
                                }

                            } else {
                                _this.gameInfo = val.data;
                            }
                        }
                    }
                }).finally(() => {

                })
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
        mounted() {
            this.dateStr = this.$moment(new Date()).format('YYYY-MM-DD');
            this.getPlanAll();
            this.getPlanList();
            setTimeout(() => {
                this.initLotterys();
            }, 300);
            clearInterval(this.lotteryRefreshTimer);
            this.lotteryRefreshTimer = null;
            this.lotteryRefreshTimer = setInterval(() => {
                this.initLotterys();
            }, 5000);
        }
    }
</script>
<style scoped>

</style>
