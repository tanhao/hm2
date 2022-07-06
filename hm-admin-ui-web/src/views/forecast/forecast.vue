<template>
    <div class="autofutllwith">
        <a-spin :spinning="spinning" size="large">
            <div>
                <div class="toplist">
                    <a-row type="flex" justify="space-around" align="middle" :gutter="16" class="mb10 bordbot">
                        <a-col class="gutter-row" :span="20">
                            <div class="gutter-box">
                                <ul>
                                    <!--
                                    <div class="setico">
                                        <a-icon type="setting" title="设置显示喜欢的彩种" @click="isShowFavoriteLotteryMadel=true" />
                                    </div>
                                    -->
                                    <li v-for="item in favoriteLotterys" :key="item.lotteryId" href="#" :class="item.lotteryId==lottery.lotteryId?'pb5 selected':'pb5'" @click="changeLottery(item.lotteryId,item.lotteryKey)">
                                        {{item.lotteryName}} <br> {{$utils.getAnsG(lotteryAmt(item.lotteryId))}}
                                    </li>
                                </ul>
                            </div>
                        </a-col>
                        <a-col class="gutter-row" :span="4">
                            <div class="gutter-box pb5" style="float: right">

                                <a-dropdown>
                                    <a-menu slot="overlay">
                                        <a-menu-item v-for="lottery in moreLotterys" :key="lottery.lotteryId" @click="changeLottery(lottery.lotteryId,lottery.lotteryKey)">
                                            <a-icon type="trophy" />
                                            {{lottery.lotteryName}} ( {{$utils.getAnsG(lotteryAmt(lottery.lotteryId))}} )
                                        </a-menu-item>
                                    </a-menu>
                                    <a-button type="primary" size="small" style="margin-left: 8px;"> 更多彩种
                                        <a-icon type="down" />
                                    </a-button>
                                </a-dropdown>
                                <!--
                                <a-button type="primary" size="small" style="margin-left: 8px;" @click="showLog()"> 操盘日志
                                </a-button>
                                -->
                            </div>
                        </a-col>
                    </a-row>
                </div>

                <div class="jszd">
                    <a-row :gutter="16" type="flex" justify="space-around" align="middle" class="mb15">
                        <a-col class="gutter-row" :span="15">
                            <div class="gutter-box">
                                <span class="maintxt mlr5">当前选种:</span>
                                <a-divider type="vertical" />
                                <span class="mlr5 green fontwe">{{lottery.lotteryName}}</span>
                                <a-divider type="vertical" />
                                <span class="mlr5 fontwe">{{lottery.gameNo}}</span>
                                <a-divider type="vertical" />
                                <span class="mlr5 green fontwe">{{groupName}}</span>
                                <a-divider type="vertical" />
                                <span v-if="closeCountdown<0" class="mlr5 green fontwe">
                                    距开奖：{{actionTimeStr}}
                                </span>
                                <span v-if="closeCountdown>=0" class="mlr5 red fontwe">
                                    距封盘：{{fengpanTimeStr}}
                                </span>
                                <a-divider type="vertical" />
                                <span class="mlr5 red fontwe">今日输赢：<span :class="winlose>0?'blue':'red'">{{$utils.getAnsG(winlose)}}</span></span>
                            </div>
                        </a-col>

                        <a-col class="gutter-row" :span="9">
                            <div class="gutter-box" style="float: right">
                                <template v-if="userLevel>=2&&canEdit">
                                    是否联动
                                    <a-switch checked-children="开" un-checked-children="关" v-model="lottery.isLink" @change="updateLotteryIsLink" />
                                    <a-divider type="vertical" />
                                    每期复位
                                    <a-switch checked-children="开" un-checked-children="关" v-model="lottery.isReset" @change="updateLotteryIsReset" />
                                    <a-divider type="vertical" />
                                </template>
                                <a-button v-if="canEdit" type="primary" icon="plus" size="small" @click="showStepMadel">
                                    设置步长
                                </a-button>
                            </div>
                        </a-col>

                    </a-row>

                    <a-row :gutter="16" type="flex" justify="space-around" align="middle" class="mb15">
                        <a-col class="gutter-row" :span="11">
                            <div class="gutter-box">
                                <a-select v-model="searchFrom.bindMarket" style="width: 70px" size="small" @change="marketChange">
                                    <a-select-option v-for="item in  searchFrom.markets" :key="item.value" :value="item.value">
                                        {{item.label}}
                                    </a-select-option>
                                </a-select>
                                <a-divider type="vertical" />
                                <a-select v-model="searchFrom.buhuo" style="width: 70px" size="small" @change="szxzbhChange">
                                    <a-select-option v-for="item in  searchFrom.buhuos" :key="item.value" :value="item.value">
                                        {{item.label}}
                                    </a-select-option>
                                </a-select>
                                <a-divider type="vertical" />
                                <a-select v-model="searchFrom.sort" style="width: 100px" size="small">
                                    <a-select-option v-for="item in  searchFrom.sorts" :key="item.value" :value="item.value">
                                        {{item.label}}
                                    </a-select-option>
                                </a-select>
                            </div>

                        </a-col>

                        <a-col class="gutter-row" :span="13">
                            <div class="gutter-box" style="float: right">
                                <span class="maintxt mr10">{{lottery.prevGameNo}}期开奖:</span>
                                <div class="innVmp">
                                    <div :class="lottery.lotteryKey" style="margin: 0px auto; display: inherit;">
                                        <div :class="'n_'+parseInt(n)" v-for="(n,i) in lottery.prevResult" :key="'r_'+i">{{n}}</div>
                                    </div>
                                </div>
                                <a-button type="primary" icon="sync" spin size="small" class="mlr5" @click="refreshPage">
                                    刷新
                                </a-button>
                                <a-badge class="mr10" :count="refreshCountdown" />
                                <a-select v-model="refreshInterval" style="width: 70px" size="small" @change="refreshPage">
                                    <a-select-option v-for="item in refreshTimes" :value="item.value" :key="item.value">
                                        {{ item.label }}
                                    </a-select-option>
                                </a-select>

                            </div>
                        </a-col>
                    </a-row>

                    <keep-alive>
                        <now-order-carship v-if="lottery.groupId==100" :userStats="userStats" :userOddss="userOddss" :userOddsNows="userOddsNows" :userOddsJumps="userOddsJumps" :userOddsCljps="userOddsCljps" :userOddsCloses="userOddsCloses" :plays="plays" :kinds="kinds" :categorys="categorys" :oddss="oddss" :canEdit="canEdit" :canCloseOpen="canCloseOpen" :sortBy="searchFrom.sort" :lmclObj="lottery.prevLmcl" @change-group="changeGroup" @show-order="showOrder"
                            @show-buhuo="showBuhuo" @update-odds="updateOdds" @update-status="updateStatus" @update-odds-group="updateOddsGroup">
                        </now-order-carship>

                        <now-order-ssc v-if="lottery.groupId==200" :userStats="userStats" :userOddss="userOddss" :userOddsNows="userOddsNows" :userOddsJumps="userOddsJumps" :userOddsCljps="userOddsCljps" :userOddsCloses="userOddsCloses" :plays="plays" :kinds="kinds" :categorys="categorys" :oddss="oddss" :canEdit="canEdit" :canCloseOpen="canCloseOpen" :sortBy="searchFrom.sort" :lmclObj="lottery.prevLmcl" @change-group="changeGroup" @show-order="showOrder"
                            @show-buhuo="showBuhuo" @update-odds="updateOdds" @update-status="updateStatus" @update-odds-group="updateOddsGroup">
                        </now-order-ssc>

                        <now-order-happy10 v-if="lottery.groupId==300" :userStats="userStats" :userOddss="userOddss" :userOddsNows="userOddsNows" :userOddsJumps="userOddsJumps" :userOddsCljps="userOddsCljps" :userOddsCloses="userOddsCloses" :plays="plays" :kinds="kinds" :categorys="categorys" :oddss="oddss" :canEdit="canEdit" :canCloseOpen="canCloseOpen" :sortBy="searchFrom.sort" :lmclObj="lottery.prevLmcl" :ylObj="lottery.prevYl" @change-group="changeGroup"
                            @show-order="showOrder" @show-buhuo="showBuhuo" @update-odds="updateOdds" @update-status="updateStatus" @update-odds-group="updateOddsGroup">
                        </now-order-happy10>

                         <now-order-luck-28 v-if="lottery.groupId==400" :userStats="userStats" :userOddss="userOddss" :userOddsNows="userOddsNows" :userOddsJumps="userOddsJumps" :userOddsCljps="userOddsCljps" :userOddsCloses="userOddsCloses" :plays="plays" :kinds="kinds" :categorys="categorys" :oddss="oddss" :canEdit="canEdit" :canCloseOpen="canCloseOpen" :sortBy="searchFrom.sort" :lmclObj="lottery.prevLmcl" :ylObj="lottery.prevYl" @change-group="changeGroup"
                            @show-order="showOrder" @show-buhuo="showBuhuo" @update-odds="updateOdds" @update-status="updateStatus" @update-odds-group="updateOddsGroup">
                        </now-order-luck-28>
                    </keep-alive>
                </div>

                <!--弹出调赔幅度-->
                <a-drawer title="设置步长" :width="300" :visible="isShowStepMadel" :body-style="{ paddingBottom: '80px'}" @close="closeStepMadel">
                    <div class="red dstable pb10"><span class="fontwe">{{lottery.lotteryName}}</span>
                    </div>
                    <table class="popright" border="0" align="center" cellpadding="5" cellspacing="1" style="border-collapse: separate;width: 100%;float: left">
                        <template v-for="(category,key) in categorys">
                            <tr :key="key">
                                <td v-if="category.row>0" :rowspan="category.row" class="forumrow">{{kinds[category.kindId].kindName}} {{category.row}}</td>
                                <td class="forumrow">{{category.categoryName}}</td>
                                <td class="forumrowhighlight">
                                    <a-input size="small" v-model.number="category.step" @change="stepCheckField(category)" />
                                </td>
                            </tr>
                        </template>

                    </table>

                    <div class="opnewinright">
                        <a-button :style="{ marginRight: '12px' }" size="small" @click="closeStepMadel">
                            取消
                        </a-button>
                        <a-button type="primary" size="small" @click="updateStep">
                            确定
                        </a-button>
                    </div>
                </a-drawer>

                <!--我的最爱-->
                <!--
                <a-drawer title="我的最爱" placement="left" :width="180" :visible="isShowFavoriteLotteryMadel" :body-style="{ paddingBottom: '80px'}" @close="isShowFavoriteLotteryMadel=false">
                    <table class="popright draggable" border="0" align="center" cellpadding="5" cellspacing="1" style="border-collapse: separate;width: 100%;float: left">
                        <draggable v-model="lotterys" tag="tbody">
                            <tr v-for="item in lotterys" :key="item.lotteryId" class="forumrow txtleft mocur">
                                <td>
                                    <a-checkbox v-model="item.isFavorite">{{item.lotteryName}}</a-checkbox>
                                </td>
                            </tr>
                        </draggable>
                    </table>
                    <div class="red dstable pt15">选中彩种拖动可以直接进行排序，最多选择9个彩种。</div>
                    <div class="opnewinright">
                        <a-button :style="{ marginRight: '12px' }" size="small" @click="isShowFavoriteLotteryMadel=false">
                            取消
                        </a-button>
                        <a-button type="primary" size="small" @click="saveFavoriteLottery">
                            确定
                        </a-button>
                    </div>
                </a-drawer>
                -->

                <!-- 补货-->
                <buhuo :key="'bh_'+now" :buhuoShow.sync="buhuoShow" :params.sync="buhuoFrom" @refresh-page="refreshPage">
                </buhuo>

                <!--注单列表-->
                <order :key="'no_'+now" :orderShow.sync="orderShow" :params.sync="orderFrom">
                </order>

                <!--日志-->
                <!--
                <log :key="'log_'+now" :logShow.sync="logShow">
                </log>
                -->
            </div>
        </a-spin>
    </div>
</template>
<script>
import to from "await-to-js";
import NowOrderCarship from "./now-order-carship.vue";
import NowOrderSsc from "./now-order-ssc.vue";
import NowOrderHappy10 from "./now-order-happy10.vue";
import NowOrderLuck28 from "./now-order-luck28.vue";
import Order from "./orders.vue";
import Buhuo from "./buhuo.vue";
import draggable from "vuedraggable";
import Bus from "@/Bus";

import Log from "./log.vue";

export default {
    components: {
        NowOrderCarship,
        NowOrderSsc,
        NowOrderHappy10,
        NowOrderLuck28,
        Order,
        Buhuo,
        draggable,
        Log,
    },
    data() {
        return {
            now: 0,
            isConnect: false,
            sysdateTime: 0,
            spinning: false,
            value: 0,
            isShowStepMadel: false,
            isShowFavoriteLotteryMadel: false,
            isSending: false,

            winlose: 0,
            groupName: null,
            oddsGroup: {},
            canEdit: true,
            canCloseOpen: true,
            lottery: {},
            kinds: {},
            categorys: {},
            plays: {},
            oddss: [],
            lotterys: [],
            mapOdds: {},
            userOddss: {},
            userOddsNows: {},
            userOddsJumps: {},
            userOddsCljps: {},
            userOddsCloses: {},
            userSteps: [],
            userStats: {},
            userStatsLottery: {},
            searchFrom: {
                otherLotteryId: null,
                userId: null,
                lotteryId: null,
                lotteryKey: null,
                buhuo: "SZ",
                sort: "HM",
                bindMarket: "-1",
                market: null,
                updateMarket: null,
                buhuos: [
                    {
                        label: "实占",
                        value: "SZ",
                    },
                    {
                        label: "虚注",
                        value: "XZ",
                    },
                    {
                        label: "补货",
                        value: "BH",
                    },
                ],
                sorts: [
                    {
                        label: "按盈亏排序",
                        value: "YK",
                    },
                    {
                        label: "按金额排序",
                        value: "JE",
                    },
                    {
                        label: "按号码排序",
                        value: "HM",
                    },
                ],
                markets: [
                    {
                        label: "全部",
                        value: "-1",
                    },
                    {
                        label: "A",
                        value: "A",
                    },
                    {
                        label: "B",
                        value: "B",
                    },
                    {
                        label: "C",
                        value: "C",
                    },
                    {
                        label: "D",
                        value: "D",
                    },
                ],
            },
            markets: {},
            diffTime: 0,
            refreshTime: 10,
            refreshInterval: 10,
            refreshTimes: [
                {
                    label: "手动",
                    value: -1,
                },
                {
                    label: "10秒",
                    value: 10,
                },
                {
                    label: "20秒",
                    value: 20,
                },
                {
                    label: "30秒",
                    value: 30,
                },
                {
                    label: "40秒",
                    value: 40,
                },
                {
                    label: "50秒",
                    value: 50,
                },
                {
                    label: "60秒",
                    value: 60,
                },
                {
                    label: "99秒",
                    value: 99,
                },
            ],
            isCompany: this.$store.state.user.info.userLevel == 2,
            userLevel: this.$store.state.user.info.userLevel,
            timerLottery: null,

            orderShow: false,
            orderFrom: {},
            buhuoShow: false,
            buhuoFrom: {},
            logShow: false,
        };
    },
    created() {
        //console.log("created forecast:");
    },
    beforeDestroy() {
        //console.log("beforeDestroy forecast:");
        Bus.$off("odds_status");
        Bus.$off("odds");
        Bus.$off("odds_init");
        Bus.$off("odds_now");
        Bus.$off("odds_jump");
        Bus.$off("odds_cljp");
        Bus.$off("result");
        Bus.$off("onopen");
        //document.removeEventListener("visibilitychange");
        clearInterval(this.timer);
        clearTimeout(this.timerLottery);
        let msg = {
            code: "odds_unlottery",
            content: {},
        };
        this.tiows.send(JSON.stringify(msg));
    },
    mounted() {
        //console.log("mounted forecast:");
        this.spinning = true;
        Bus.$on("odds_init", (data) => {
            //console.log("forecast odds_init:", data);
            this.requestNowOrder(data);
        });
        Bus.$on("odds_status", (data) => {
            let { oddsId, isClose } = data;
            this.$set(this.userOddsCloses, oddsId, isClose);
        });
        Bus.$on("odds", (data) => {
            console.log("odds:", data);
            let { userLevel, oddss } = data;
            oddss.forEach((odds) => {
                let { categoryId, odds: newVal } = odds;
                if (!this.userOddss[categoryId]) {
                    this.$set(
                        this.userOddss,
                        categoryId,
                        new Array(userLevel).fill(0)
                    );
                }
                this.$set(this.userOddss[categoryId], userLevel, newVal);
            });
        });
        Bus.$on("odds_now", (data) => {
            console.log("now:", data);
            let { userLevel, oddss } = data;
            oddss.forEach((odds) => {
                let { oddsId, odds: newVal } = odds;
                if (!this.userOddsNows[oddsId]) {
                    this.$set(
                        this.userOddsNows,
                        oddsId,
                        new Array(userLevel).fill(0)
                    );
                }
                this.$set(this.userOddsNows[oddsId], userLevel, newVal);
            });
        });
        Bus.$on("odds_jump", (data) => {
            //console.log("jump:", data);
            let { userLevel, oddss } = data;
            oddss.forEach((odds) => {
                let { oddsId, odds: newVal } = odds;
                let arr = new Array(userLevel).fill(0);
                arr[userLevel] = newVal;
                this.$set(this.userOddsJumps, oddsId, arr);
            });
        });
        Bus.$on("odds_cljp", (data) => {
            //console.log("cljp:", data);
            let { userLevel, oddss } = data;
            oddss.forEach((odds) => {
                let { oddsId, odds: newVal } = odds;
                let arr = new Array(userLevel).fill(0);
                arr[userLevel] = newVal;
                this.$set(this.userOddsCljps, oddsId, arr);
            });
        });
        Bus.$on("result", (data) => {
            let { result, gameNo, stats: jsonStr } = data;
            /*
            console.log(
                "socket.prevGameNo:",
                this.lottery.prevGameNo,
                "gameNo:",
                gameNo,
                "result:",
                data
            );
            */
            if (gameNo >= this.lottery.prevGameNo) {
                //console.log("YYYYYYYYYYYES");
                //this.lottery.prevResult = result.split(",");
                //this.lottery.prevGameNo = gameNo;
                let stats = JSON.parse(jsonStr);
                this.lottery.prevLmcl = stats.lmcl;
                this.lottery.prevYl = stats.yl;
                this.$set(this.lottery, "prevGameNo", gameNo);
                this.$set(this.lottery, "prevResult", result.split(","));
            }
        });
        Bus.$on("onopen", (data) => {
            //console.log("onopen:", this.isSending);
            if (this.isSending) {
                this.refreshLottery();
                this.sendSelectLottery();
            }
            this.isSending = true;
        });

        this.searchFrom.userId = this.$store.state.user.info.primaryId;
        //刷新继续选中上次的赛事
        let lotteryId = sessionStorage.getItem("now_lotteryId");
        let lotteryKey = sessionStorage.getItem("now_lotteryKey");
        if (lotteryId) {
            this.searchFrom.lotteryId = lotteryId;
            this.searchFrom.lotteryKey = lotteryKey;
        }
        this.sendSelectLottery();
        this.startCountdown();
        //this.requestNowOrder({});

        document.addEventListener("visibilitychange", (e) => {
            if (document.visibilityState == "visible") {
                //状态判断：显示（切换到当前页面）
                // 切换到页面执行事件
                //console.log("visible");
                console.log("state:", this.tiows.ws.readyState);
                if (this.tiows && this.tiows.ws.readyState == 1) {
                    console.log("Socket有效，直接发送选彩种");
                    this.sendSelectLottery();
                } else {
                    console.log("Socket已断开，重连");
                    Bus.$emit("to-connect", data.content);
                }
            } else if (document.visibilityState == "hidden") {
                //状态判断：隐藏（离开当前页面）
                // 离开页面执行事件
                console.log("hidden");
            }
        });
    },
    computed: {
        actionTimeStr() {
            let { actionTime, delayOpen } = this.lottery;
            let time =
                actionTime - this.sysdateTime + delayOpen - this.diffTime;
            let ocd = Math.max(0, time);
            let logicMinute = Number.parseInt(ocd / 60);
            let logicHour = Number.parseInt(ocd / 60 / 60);
            let day = Number.parseInt(ocd / 60 / 60 / 24);
            let hour = logicHour % 24;
            let minute = logicMinute % 60;
            let second = ocd % 60;
            let timeStr =
                (day > 0 ? day + "天" : "") +
                (hour > 0 ? hour + ":" : "") +
                minute.toString().padStart(2, "0") +
                ":" +
                second.toString().padStart(2, "0");

            return timeStr;
        },
        fengpanTimeStr() {
            let { actionTime, precedeClose } = this.lottery;
            let time =
                actionTime - this.sysdateTime - precedeClose - this.diffTime;

            let ocd = Math.max(0, time);
            let logicMinute = Number.parseInt(ocd / 60);
            let logicHour = Number.parseInt(ocd / 60 / 60);
            let day = Number.parseInt(ocd / 60 / 60 / 24);
            let hour = logicHour % 24;
            let minute = logicMinute % 60;
            let second = ocd % 60;
            let timeStr =
                (day > 0 ? day + "天" : "") +
                (hour > 0 ? hour + ":" : "") +
                minute.toString().padStart(2, "0") +
                ":" +
                second.toString().padStart(2, "0");
            return timeStr;
        },
        openCountdown() {
            let { actionTime, delayOpen } = this.lottery;
            let time =
                actionTime - this.sysdateTime + delayOpen - this.diffTime;
            return time;
        },
        closeCountdown() {
            let { actionTime, precedeClose } = this.lottery;
            let time =
                actionTime - this.sysdateTime - precedeClose - this.diffTime;
            return time;
        },
        refreshCountdown() {
            return this.refreshTime.toString().padStart(2, "0");
        },
        favoriteLotterys() {
            return this.lotterys
                .filter((lottery) => lottery.isFavorite)
                .slice(0, 9);
        },
        moreLotterys() {
            let lotterys = [];
            let logic = [];
            for (let i = 0; i < this.lotterys.length; i++) {
                let lottery = this.lotterys[i];
                if (lottery.isFavorite && logic.length <= 9) {
                    logic.push(lottery);
                } else {
                    lotterys.push(lottery);
                }
            }
            return lotterys;
        },
        lotteryAmt(lotteryId) {
            return (lotteryId) => {
                let lottery = this.userStatsLottery[lotteryId];
                if (lottery) {
                    return lottery.betAmt;
                }
                return 0;
            };
        },
    },
    methods: {
        sendSelectLottery() {
            if (this.tiows && this.tiows.ws.readyState == 1) {
                console.log("===>>>sendSelectLottery<<<===");
                let msg = {
                    code: "odds_lottery",
                    content: {
                        lotteryId: this.searchFrom.lotteryId,
                        lotteryKey: this.searchFrom.lotteryKey,
                        market: this.searchFrom.market,
                        userId: this.searchFrom.userId,
                    },
                };
                //console.log("send_odds_lottery", msg);
                this.tiows.send(JSON.stringify(msg));
            } else {
                setTimeout(this.sendSelectLottery, 200);
            }
        },
        async requestNowOrder(data) {
            /*
            let { updateMarket, market, buhuo, lotteryId } = this.searchFrom;
            let params = {
                market,
                buhuo,
                lotteryId,
                markets: updateMarket,
            };

          
            let [err, res] = await to(this.$api.ctrl.getNowOrder(params));
            if (err || !res.success) {
                this.spinning = false;
                this.$utils.handleThen(res, this);
                return;
            }
            console.log(res.data);
            data = res.data;
            */
            this.spinning = true;
            let {
                lotterys,
                lottery,
                kinds,
                categorys,
                plays,
                oddss,
                userOddss,
                userOddsNows,
                userOddsJumps,
                userOddsCljps,
                userOddsCloses,
                userSteps,
                userStats,
                userStatsLottery,
                canCloseOpen,
                canEdit,
                market,
                markets,
                serverTime,
                winlose,
            } = data;

            //this.canCloseOpen = false;
            //this.canEdit = false;
            let logic = null;
            Object.keys(categorys).forEach((key) => {
                let category = categorys[key];
                let { kindId } = category;
                let row = 0;
                if (logic == null || logic != kindId) {
                    row = Object.values(categorys).filter(
                        (c) => c.kindId == kindId
                    ).length;
                }
                category.step = userSteps[key].step;
                category.row = row;
                logic = kindId;
            });
            this.userSteps = userSteps;
            let favoriteLotterys = sessionStorage.getItem("favorite_lotterys");
            if (favoriteLotterys) {
                let fjson = JSON.parse(favoriteLotterys);
                lotterys.forEach((lottery, idx) => {
                    lottery.isFavorite = fjson[lottery.lotteryId];
                });
            } else {
                lotterys.forEach((lottery, idx) => {
                    lottery.isFavorite = idx <= 8 ? true : false;
                });
            }
            let mapOdds = {};
            oddss.forEach((odds) => {
                let { playId, oddsKey } = odds;
                let { playKey } = plays[playId];
                if (!mapOdds[playKey]) {
                    mapOdds[playKey] = {};
                }
                mapOdds[playKey][oddsKey] = odds;
            });
            this.mapOdds = mapOdds;
            if (lottery.prevResult) {
                lottery.prevResult = lottery.prevResult.split(",");
            } else {
                lottery.prevResult = [];
            }
            if (lottery.prevLmcl) {
                lottery.prevLmcl = JSON.parse(lottery.prevLmcl);
            } else {
                lottery.prevLmcl = {};
            }
            if (lottery.prevYl) {
                lottery.prevYl = JSON.parse(lottery.prevYl);
            } else {
                lottery.prevYl = {};
            }
            this.searchFrom.lotteryId = lottery.lotteryId;
            sessionStorage.setItem("now_lotteryId", lottery.lotteryId);
            sessionStorage.setItem("now_lotteryKey", lottery.lotteryKey);
            //this.searchFrom.buhuo = "SZ";
            this.searchFrom.market = market;
            //this.searchFrom.bindMarket = "-1"; //;
            this.searchFrom.updateMarket = Object.keys(markets).join();
            this.searchFrom.markets = [
                {
                    label: "全部",
                    value: "-1",
                },
                ...Object.keys(markets).map((m) => {
                    let obj = { label: m, value: m };
                    return obj;
                }),
            ];
            this.markets = markets;
            this.sysdateTime = parseInt(serverTime / 1000);
            this.winlose = winlose;
            this.canEdit = canEdit;
            this.canCloseOpen = canCloseOpen;
            this.lotterys = lotterys;
            this.lottery = lottery;
            this.kinds = kinds;
            this.categorys = categorys;
            this.plays = plays;
            this.oddss = oddss;
            this.userStats = userStats ? userStats : {};
            this.userStatsLottery = userStatsLottery ? userStatsLottery : {};
            this.userOddss = userOddss;
            this.userOddsNows = userOddsNows;
            this.userOddsJumps = userOddsJumps;
            this.userOddsCljps = userOddsCljps;
            this.userOddsCloses = userOddsCloses;
            this.spinning = false;
            this.startCountdown();
            this.refreshLottery();
           
            //this.searchFrom.sort = "HM";
        },
        stepCheckField(userStep) {
            clearTimeout(this.timer);
            this.timer = setTimeout(() => {
                let { decimals, step } = userStep;
                userStep.step = Math.floor(step * decimals) / decimals;
            }, 500);
        },
        async updateStep() {
            let params = {
                lotteryIds: [this.lottery.lotteryId],
                steps: [],
            };
            Object.keys(this.categorys).forEach((key) => {
                let { step } = this.categorys[key];
                if (step > 0) {
                    params.steps.push({ step, categoryId: key });
                }
            });
            if (params.steps.length == 0) {
                return;
            }
            let [err, res] = await to(this.$api.ctrl.updateStep(params));
            this.closeStepMadel();
            this.$utils.handleThen(res, this);
        },
        async marketChange() {
            if (this.searchFrom.bindMarket == -1) {
                this.searchFrom.updateMarket = Object.keys(this.markets).join();
                this.searchFrom.market = Object.keys(this.markets)[0];
            } else {
                this.searchFrom.updateMarket = this.searchFrom.bindMarket;
                this.searchFrom.market = this.searchFrom.bindMarket;
            }
            //sessionStorage.setItem("now_market", this.searchFrom.bindMarket);
            let { gameNo, lotteryId } = this.lottery;
            let { updateMarket, market, buhuo } = this.searchFrom;
            let params = {
                gameNo,
                buhuo,
                lotteryId,
                market,
                markets: updateMarket,
            };
            let msg = {
                code: "odds_market",
                content: { market },
            };
            //console.log("msg", msg);
            this.tiows.send(JSON.stringify(msg));
            let [err, res] = await to(this.$api.ctrl.getNowOdds(params));
            if (err || !res.success) {
                return;
            }
            let { userOddss, userOddsNows, userStats } = res.data;
            this.userOddss = userOddss;
            this.userOddsNows = userOddsNows;
            this.userStats = userStats;
            this.refreshTime = this.refreshInterval;
        },
        async szxzbhChange() {
            this.refreshPage();
            this.refreshTime = this.refreshInterval;
            //sessionStorage.setItem("now_buhuo", this.searchFrom.buhuo);
        },
        async refreshPage() {
            let { gameNo, lotteryId } = this.lottery;
            let { updateMarket, buhuo } = this.searchFrom;
            let params = {
                gameNo,
                buhuo,
                lotteryId,
                markets: updateMarket,
            };
            let [err, res] = await to(this.$api.ctrl.getNowStats(params));
            if (err || !res.success) {
                return;
            }
            let {
                userStats,
                userStatsLottery,
                winlose,
                //userOddsJumps,
            } = res.data;
            this.userStats = userStats;
            this.userStatsLottery = userStatsLottery;
            //this.userOddsJumps = userOddsJumps;
            this.refreshTime = this.refreshInterval;
            this.winlose = winlose;
        },
        async refreshLottery() {
            let nextTime = this.openCountdown * 1000;
            //console.log("nextTime", nextTime);
            clearTimeout(this.timerLottery);
            this.timerLottery = setTimeout(async () => {
                this.userOddsJumps = {};
                let params = {
                    lotteryId: this.searchFrom.lotteryId,
                    userId: this.searchFrom.userId,
                    market: this.searchFrom.market,
                };
                let [err, res] = await to(this.$api.ctrl.getNowLottery(params));
                if (err || !res.success) {
                    return;
                }
                let lottery = res.data.lottery;
                //console.log("request:", lottery);
                if (lottery.prevGameNo > this.lottery.prevGameNo) {
                    if (lottery.prevResult) {
                        lottery.prevResult = lottery.prevResult.split(",");
                    } else {
                        lottery.prevResult = [];
                    }
                    if (lottery.prevLmcl) {
                        lottery.prevLmcl = JSON.parse(lottery.prevLmcl);
                    } else {
                        lottery.prevLmcl = {};
                    }
                    if (lottery.prevYl) {
                        lottery.prevYl = JSON.parse(lottery.prevYl);
                    } else {
                        lottery.prevYl = {};
                    }
                    this.lottery.prevGameNo = lottery.prevGameNo;
                }
                this.lottery.gameNo = lottery.gameNo;
                this.lottery.openTime = lottery.openTime;
                this.lottery.actionTime = lottery.actionTime;
                this.lottery.isLink = lottery.isLink;
                this.lottery.isReset = lottery.isReset;
                this.lottery.delayOpen = lottery.delayOpen;

                //Object.assign(this.lottery, lottery);
                let userOddsNows = res.data.userOddsNows;
                if (Object.keys(userOddsNows).length == 0) {
                    Object.keys(this.userOddsNows).forEach((key) => {
                        this.$delete(this.userOddsNows, key);
                    });
                } else {
                    this.userOddsNows = userOddsNows;
                }
                this.refreshLottery();
            }, nextTime);
        },
        startCountdown() {
            this.diffTime = 0;
            this.refreshTime = this.refreshInterval;
            clearInterval(this.timer);
            this.timer = setInterval(() => {
                this.diffTime += 1;
                this.refreshTime -= 1;
                if (this.refreshTime == 0) {
                    this.refreshPage();
                    this.refreshTime = this.refreshInterval;
                }
            }, 1000);
        },
        async updateOddsGroup(playKeys, oddsKey, ji) {
            //console.log("forecaset update odds group:", playKeys, oddsKey, ji);
            let oddss = playKeys.map(
                (playKey) => this.mapOdds[playKey][oddsKey]
            );
            let { categoryId } = oddss[0];
            let step = this.categorys[categoryId].step;
            if (!step || step <= 0) {
                this.$swal({
                    title: "请先设置步长",
                    icon: "warning",
                    confirmButtonText: "确定",
                });
            }
            let oddsIds = oddss.map((odds) => odds.oddsId);
            let { updateMarket, lotteryId } = this.searchFrom;
            let diff = ji * step;
            let params = {
                lotteryId,
                diff,
                markets: updateMarket,
                oddsIds,
            };
            let [err, res] = await to(this.$api.ctrl.plusMinusOdds(params));
            if (err || !res.success) {
                return;
            }
            /*
            oddsIds.forEach((oddsId) => {
                if (!this.userOddsNows[oddsId]) {
                    this.$set(
                        this.userOddsNows,
                        oddsId,
                        new Array(this.userLevel).fill(0)
                    );
                }
                let newVal = Math.min(
                    Math.round(
                        (this.userOddsNows[oddsId][this.userLevel] + diff) *
                            100000
                    ) / 100000,
                    0
                );
                this.$set(this.userOddsNows[oddsId], this.userLevel, newVal);
            });
            */
        },
        async updateOdds(odds, ji) {
            if (this.closeCountdown <= 0) {
                this.$swal({
                    title: "已封盘，不能修改！！！",
                    icon: "error",
                    confirmButtonText: "确定",
                });
                return;
            }
            let { oddsId, categoryId } = odds;
            console.log(ji, odds);
            let step = this.categorys[categoryId].step;
            if (!step || step <= 0) {
                this.$swal({
                    title: "请先设置步长！！！",
                    icon: "warning",
                    confirmButtonText: "确定",
                });
            }
            let { updateMarket, lotteryId } = this.searchFrom;
            let diff = ji * step;
            let params = {
                lotteryId,
                diff,
                markets: updateMarket,
                oddsIds: [oddsId],
            };
            let [err, res] = await to(this.$api.ctrl.plusMinusOdds(params));
            if (err || !res.success) {
                this.$utils.handleThen(res, this);
                return;
            }
            /*
            if (!this.userOddsNows[oddsId]) {
                this.$set(
                    this.userOddsNows,
                    oddsId,
                    new Array(this.userLevel).fill(0)
                );
            }
            let newVal = Math.min(
                Math.round(
                    (this.userOddsNows[oddsId][this.userLevel] + diff) * 100000
                ) / 100000,
                0
            );
            this.$set(this.userOddsNows[oddsId], this.userLevel, newVal);
            */
        },
        async updateStatus(odds, isClose) {
            let { oddsId } = odds;
            let params = {
                lotteryId: this.searchFrom.lotteryId,
                oddsId,
                isClose: isClose ? 1 : 0,
            };
            let [err, res] = await to(this.$api.ctrl.openCloseOdds(params));
            if (err || !res.success) {
                return;
            }
            // this.$set(this.userOddsCloses, oddsId, isClose);
        },
        showOrder(odds) {
            let { lotteryId, gameNo } = this.lottery;
            let { oddsId } = odds;
            let params = {
                oddsId,
                lotteryIds: [lotteryId],
                gameNo,
            };
            this.orderShow = true;
            this.orderFrom = params;
            this.now = new Date().getTime();
        },
        showBuhuo(odds) {
            if (this.userLevel <= 2) {
                return;
            }
            let { gameNo, lotteryId } = this.lottery;
            let params = {market:this.searchFrom.bindMarket};
            Object.assign(params, odds);
            params.gameNo = gameNo;
            params.lotteryId = lotteryId;
            params.market = this.searchFrom.market;
            this.buhuoShow = true;
            this.buhuoFrom = params;
            this.now = new Date().getTime();
        },
        showLog() {
            this.logShow = true;
            this.now = new Date().getTime();
        },
        changeGroup(oddsGroup) {
            this.groupName = oddsGroup.name;
            this.oddsGroup = oddsGroup;
        },
        async changeLottery(lotteryId, lotteryKey) {
            this.spinning = true;
            let msg = {
                code: "odds_lottery",
                content: {
                    lotteryId: lotteryId,
                    lotteryKey: lotteryKey,
                    market: this.searchFrom.market,
                    buhuo: this.searchFrom.buhuo,
                    userId: this.$store.state.user.info.primaryId,
                },
            };
            console.log("msg", msg);
            this.tiows.send(JSON.stringify(msg));
        },
        async updateLotteryIsLink() {  
            let { lotteryId, isLink } = this.lottery;
            let [err, res] = await to(
                this.$api.ctrl.updateLotteryIsLink({ lotteryId, isLink })
            );
            if (err || !res.success) {
                this.lottery.isLink = !isLink;
            }
        },
        async updateLotteryIsReset() {
            let { lotteryId, isReset } = this.lottery;
            let [err, res] = await to(
                this.$api.ctrl.updateLotteryIsReset({ lotteryId, isReset })
            );
            if (err || !res.success) {
                this.lottery.isReset = !isReset;
            }
        },
        saveFavoriteLottery() {
            let data = this.lotterys.reduce(
                (pre, { lotteryId, isFavorite }) => {
                    pre[lotteryId] = isFavorite;
                    return pre;
                },
                {}
            );
            sessionStorage.setItem("favorite_lotterys", JSON.stringify(data));
            this.isShowFavoriteLotteryMadel = false;
        },
        showStepMadel() {
            this.isShowStepMadel = true;
        },
        closeStepMadel() {
            this.isShowStepMadel = false;
        },
        error() {
            this.$swal({
                title: "功能未开放!",
                //text: 'Do you want to continue',
                icon: "error",
                confirmButtonText: "确定",
            });
        },
        warning() {
            this.$message.warning("功能暂未开放", 3);
        },
    },
};
</script>
<style scoped>
</style>