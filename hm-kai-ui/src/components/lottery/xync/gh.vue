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
                                    <li id="pk10" @click="changeGame('lucky10')" class="pk10Yellow"><i></i></li>
                                    <b class="bg_b"></b>
                                    <li id="jisusc" @click="changeGame('speed10')" class="jsscYellow"><i></i></li>
                                    <b class="bg_b"></b>
                                    <li id="jisuft"><i></i></li>
                                    <b class="bg_b"></b>
                                    <li id="sgAirship"><i></i></li>
                                </ul>
                                <span id="backTop" class="backTop"><i></i></span></div>
                        </div>
                        <div class="detailMess" id="detailMess">
                            <div class="logoBox"><img id="logoImage" src="@/assets/img/zshImage/PK10/PK10.png" alt="">
                                <span class="caiName" id="caiName">澳洲幸运10</span></div>
                            <div class="cutimeBox">
                                <div class="timebox">
                                    <div class="toptxt">倒计时</div>
                                    <div id="kaiStyle" >
                                        <div id="hourDiv"><span id="horFir" class="clearCont">0</span><span id="horSec" class="clearCont">0</span>
                                            <span>时</span>
                                        </div>
                                        <span id="minFir" class="clearCont">{{returnLotteryTime(gameInfo).m1}}</span>
                                        <span id="minSec" class="clearCont">{{returnLotteryTime(gameInfo).m2}}</span>
                                        <span>分</span> <span id="secFir" class="clearCont">{{returnLotteryTime(gameInfo).s1}}</span>
                                        <span id="secTwo" class="clearCont">{{returnLotteryTime(gameInfo).s2}}</span>
                                        <span>秒</span>
                                    </div>
                                    <div id="openStyle" style="display: none;">开奖中...</div>
                                    <div id="hasIssue"> 已推荐<i id="hasIssueI">{{newPlan.planCount}}</i>期</div>
                                </div>
                                <span class="stopbox" style="display:none;text-align:center;width:100%;color: #ff0b0b;font-size:20px;height:170px;line-height:170px">停止销售</span>
                            </div>
                            <div class="planBox" id="planBox">
                                <div class="toptxt">最新计划</div>
                                <ul>
                                    <li v-for="num in newPlan.planA" :class="'pk10_'+num*1"></li>
                                </ul>
                                <div class="character"><span class="span_1"><i id="qishu">{{newPlan.gameNo}}</i>期</span> <span
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
                            <a href="#" class="check_a zhao_a">购彩计划</a>
                            <a class="" href="#" id="zhuiA">稳赢杀号</a>
                        </div>
                    </div>
                    <div class="view">
                        <div id="hint">
                            <span><i class="tips">提示说明：</i>每天推荐计划时间段，</span>
                            <span>时间段一<i>{{lotteryPlan.plan1Time1}}-{{lotteryPlan.plan1Time2}}</i></span>
                            <span>时间段二<i>{{lotteryPlan.plan2Time1}}-{{lotteryPlan.plan2Time2}}</i></span>
                            <span v-if="lotteryPlan.plan3Time1!='00:00:00'">时间段三<i>{{lotteryPlan.plan3Time1}}-{{lotteryPlan.plan3Time2}}</i></span>
                        </div>
                        <div id="fugaiView" style="display: none;">
                            正在开发当中
                        </div>
                        <div class="ran">
                            <!--购彩计划-->
                            <ul class="11check5_ran " >
                                <li :class="checkRan.one?'check_ran':''" data-text="A" @click="checkRanChange(1)">计划一</li>
                                <li :class="checkRan.two?'check_ran':''" data-text="B" @click="checkRanChange(2)">计划二</li>
                                <li :class="checkRan.three?'check_ran':''" data-text="C" @click="checkRanChange(3)">计划三</li>
                            </ul>
                            <!--稳赢杀号-->
                            <ul class="pk10_ran" style="display: none">
                                <li class="check_ran" data-text="first">冠军</li>
                                <li data-text="second">亚军</li>
                                <li data-text="third">第三名</li>
                                <li data-text="fourth">第四名</li>
                                <li data-text="fifth">第五名</li>
                                <li data-text="sixth">第六名</li>
                                <li data-text="seven">第七名</li>
                                <li data-text="eight">第八名</li>
                                <li data-text="nine">第九名</li>
                                <li data-text="ten">第十名</li>
                            </ul>
                            <ul class="cqssc_ran" style="display: none;">
                                <li class="check_ran" data-text="first">万位杀号</li>
                                <li data-text="second">千位杀号</li>
                                <li data-text="third">百位杀号</li>
                                <li data-text="fourth">十位杀号</li>
                                <li data-text="fifth">个位杀号</li>
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
                                    <template v-for="his in resultPlanList">
                                        <ul class="xhuiHao_content" v-if="checkRan.one">
                                            <li>{{his.gameNo}}期</li>
                                            <li>
                                                <ul class="opactionUl">
                                                    <li  v-for="num,index in his.result" :class="'opaction pk10_'+num*1" v-if="index==0" ><i v-if="his.winAno" class="resultCheckLi"></i></li>
                                                    <li  v-for="num,index in his.result" :class="'pk10_'+num*1" v-if="index!=0" ></li>
                                                </ul>
                                            </li>
                                            <li>
                                                <ul class="opactionUl"  >

                                                    <li  v-for="num,index in his.planA" :class="'opaction pk10_'+num*1"><i :class="num == his.winAno?'resultCheckLi':''"></i></li>
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
                                                    <li  v-for="num,index in his.result" :class="'opaction pk10_'+num*1" v-if="index==0" ><i v-if="his.winBno" class="resultCheckLi"></i></li>
                                                    <li  v-for="num,index in his.result" :class="'pk10_'+num*1" v-if="index!=0" ></li>
                                                </ul>
                                            </li>
                                            <li>
                                                <ul class="opactionUl" style="width: 177px !important;" >
                                                    <li  v-for="num,index in his.planB" :class="'opaction pk10_'+num*1"><i :class="num == his.winBno?'resultCheckLi':''"></i></li>
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
                                                    <li  v-for="num,index in his.result" :class="'opaction pk10_'+num*1" v-if="index==0" ><i v-if="his.winCno" class="resultCheckLi"></i></li>
                                                    <li  v-for="num,index in his.result" :class="'pk10_'+num*1" v-if="index!=0" ></li>
                                                </ul>
                                            </li>
                                            <li>
                                                <ul class="opactionUl" >
                                                    <li  v-for="num,index in his.planC" :class="'opaction pk10_'+num*1"><i :class="num == his.winCno?'resultCheckLi':''"></i></li>
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
                                    <p class="powP"><span class="cp_name">极速赛车</span><span class="cp_check">计划一</span>购彩计划：依据<span
                                        class="cp_name">极速赛车</span>历史大数据规律为基础，提供N组购彩计划参考，建议使用阶梯倍投方法投注，如果连续10期没有成功则标记为失败
                                    </p>
                                    <h3 class="h3_two">本推荐玩法</h3>
                                    <p><span class="cp_name">极速赛车</span><span class="cp_check">计划一</span>追踪第一球开奖号码，以倍投的形式，第一期以1为基数，以此类推，连续十次不中则更新计划。
                                    </p>
                                    <h3 class="h3_two">使用技巧</h3>
                                    <p><span class="cp_name">极速赛车</span><span class="cp_check">计划一</span>购彩计划，建议使用，阶梯倍投方式追号投注。
                                    </p>
                                    <h3 class="h3_two">推荐计划</h3>
                                    <p><span class="cp_name">极速赛车</span><span class="cp_check">计划一</span>推荐计划，以此类推，连续十次不中则更新计划
                                        推荐计划：三种方案的推荐计划分别对应不同个数的号码，号码越多中奖率越高，但是中奖额度较小，风险
                                        越大，收益越高。比如：计划1为5个号码，所需要的成本为5，假如赔率是10，则赢5;如果是计划3， 有3个号码，成本为3,则赢7。</p>
                                    <h3 class="h3_two">当前成本</h3>
                                    <p><span class="cp_name">极速赛车</span><span class="cp_check">计划一</span>当前成本，指当前期购彩号码成本，每个计划的成本以号码多少为基准。
                                    </p>
                                    <h3 class="h3_two">累计成本</h3>
                                    <p><span class="cp_name">极速赛车</span><span class="cp_check">计划一</span>累计成本，指从推荐第一期开始计算所用总成本。
                                    </p>
                                    <h3 class="h3_two">赢亏</h3>
                                    <p><span class="cp_name">极速赛车</span><span class="cp_check">计划一</span>赢亏，根据试玩的赔率计算得来，红为赢，蓝为亏。
                                    </p>
                                </div>
                                <div class="zshao_text sahaoCla" style="display: none;">
                                    <h3>功能介绍</h3>
                                    <p><span class="cp_name">极速赛车</span><span class="cp_check">计划一</span>杀号公式，依据<span
                                        class="cp_name">极速赛车</span>历史开奖数据规律为依托，排除出现概率最小的快3号码，北京福彩快3综合杀号公式是有效提高缩小快3投注范围的一种方法指导。
                                    </p>
                                    <h3 class="h3_two">使用技巧</h3>
                                    <p><span class="cp_name">极速赛车</span><span class="cp_check">计划一</span>杀号公式公式A~E，选择几种成绩率高的<span
                                        class="cp_name">极速赛车</span><span
                                        class="cp_check">计划一</span>杀号公式的公式结果即可，并非全部<span class="cp_check">计划一</span>杀号公式都做为彩票投注依托。
                                    </p>
                                </div>
                            </div>

                            <div class="XHao">
                                <!--计划下方说明-->
                                <div class="zshao_text zhuihaoCla" style="display: block;">
                                    <h3>功能介绍</h3>
                                    <p class="powP"><span class="cp_name">极速赛车</span><span class="cp_check">计划一</span>购彩计划：依据<span
                                        class="cp_name">极速赛车</span>历史大数据规律为基础，提供N组购彩计划参考，建议使用阶梯倍投方法投注，如果连续10期没有成功则标记为失败
                                    </p>
                                    <h3 class="h3_two">本推荐玩法</h3>
                                    <p><span class="cp_name">极速赛车</span><span class="cp_check">计划一</span>追踪第一球开奖号码，以倍投的形式，第一期以1为基数，以此类推，连续十次不中则更新计划。
                                    </p>
                                    <h3 class="h3_two">使用技巧</h3>
                                    <p><span class="cp_name">极速赛车</span><span class="cp_check">计划一</span>购彩计划，建议使用，阶梯倍投方式追号投注。
                                    </p>
                                    <h3 class="h3_two">推荐计划</h3>
                                    <p><span class="cp_name">极速赛车</span><span class="cp_check">计划一</span>推荐计划，以此类推，连续十次不中则更新计划
                                        推荐计划：三种方案的推荐计划分别对应不同个数的号码，号码越多中奖率越高，但是中奖额度较小，风险
                                        越大，收益越高。比如：计划1为5个号码，所需要的成本为5，假如赔率是10，则赢5;如果是计划3， 有3个号码，成本为3,则赢7。</p>
                                    <h3 class="h3_two">当前成本</h3>
                                    <p><span class="cp_name">极速赛车</span><span class="cp_check">计划一</span>当前成本，指当前期购彩号码成本，每个计划的成本以号码多少为基准。
                                    </p>
                                    <h3 class="h3_two">累计成本</h3>
                                    <p><span class="cp_name">极速赛车</span><span class="cp_check">计划一</span>累计成本，指从推荐第一期开始计算所用总成本。
                                    </p>
                                    <h3 class="h3_two">赢亏</h3>
                                    <p><span class="cp_name">极速赛车</span><span class="cp_check">计划一</span>赢亏，根据试玩的赔率计算得来，红为赢，蓝为亏。
                                    </p>
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
    export default {
        data() {
            return {
                lotteryRefreshTimer:null,
                serverTimer:null,
                serverTime:'',
                gameInfo:{lotteryKey:null,groupId:null},
                lotteryPlan:{},
                planList: [],
                newPlan:{
                    planCount:0
                },
                resultPlanList: [],
                lotteryKey: 'speed10',
                checkRan:{
                    one:true,
                    two:false,
                    three:false
                },
            }
        },
        components: {
            MyHeader,
            MyFoot,
        },
        computed:{
            returnLotteryTime(item){
                let _this = this;
                return (item)=>{
                    let open_time = parseInt((new Date(item.nextActionTime).getTime()/1000) - _this.serverTime/1000);
                    if(open_time>0){
                        if(this.lotteryRefreshTimer){
                            clearInterval(this.lotteryRefreshTimer);
                            this.lotteryRefreshTimer = null;
                        }
                        _this.$set(item,"isOpenStatus",true);
                        clearInterval(item.isRefreshResult);
                        _this.$set(item,'isRefreshResult',null);
                        let m = _this.formatTime(parseInt(open_time / 60));
                        let s = _this.formatTime(parseInt(open_time % 60));
                        let obj = {'m1': m.charAt(1),'m2': m.charAt(2), 's1': s.charAt(1), 's2': s.charAt(2)};
                        return obj;
                    }else{
                        if(!this.lotteryRefreshTimer){
                            this.lotteryRefreshTimer = setInterval(()=>{
                                this.initLotterys();
                            },5000);
                        }
                        if(item.isOpenStatus){
                            item.isRefreshResult = setInterval(()=>{
                                item.result.sort(function(){
                                    return Math.random()-0.5
                                })
                            },100);
                        }
                        _this.$set(item,"isOpenStatus",false);
                        return {'m1':0,'m2':0,'s1':0,'s2':0};
                    }

                }
            }
        },
        methods: {
            getPlanAll(){
                this.$api.Lottery.getLotteryPlan().then(val => {
                    this.planList = [];
                    if (val.success) {
                        this.planList = val.data;
                        this.lotteryPlan = val.data.find(v=> v.lotteryKey == this.lotteryKey);
                    }
                })
            },
            getPlanList() {
                this.$api.Lottery.getPlanByList(this.lotteryKey + "/" + this.dateStr).then(val => {
                    this.resultPlanList = [];
                    if (val.success) {
                        this.resultPlanList = val.data;
                        this.resultPlanList.forEach(items => {
                            let planA = items.planA.split(",");
                            let planB = items.planB.split(",");
                            let planC = items.planC.split(",");
                            items.planA = planA.sort(function(a, b){return a - b});
                            items.planB = planB.sort(function(a, b){return a - b});
                            items.planC = planC.sort(function(a, b){return a - b});
                            if (items.result) {
                                let nums = items.result.split(",");
                                items.result = nums;
                            }
                        })
                        this.newPlan = this.resultPlanList.shift();
                    }
                })
            },
            changeGame(type){
                this.lotteryKey = type;
                this.getPlanAll ();
                this.getPlanList();
            },
            checkRanChange(type){
                this.checkRan.one = type==1?true:false;
                this.checkRan.two = type==2?true:false;
                this.checkRan.three = type==3?true:false;
            },
            setTime(time) {
                let m = this.formatTime(parseInt(time / 60));
                let s = this.formatTime(parseInt(time % 60));
                console.log(m)
                let obj = {'m1': m.charAt(1),'m2': m.charAt(2), 's1': s.charAt(1), 's2': s.charAt(2)};
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
            this.getPlanList();
            this.getPlanAll();
            setTimeout(()=>{
                this.initLotterys();
            },300);
            clearInterval(this.lotteryRefreshTimer);
            this.lotteryRefreshTimer = null;
            this.lotteryRefreshTimer = setInterval(()=>{
                this.initLotterys();
            },5000);
        }
    }
</script>
<style scoped>

</style>
