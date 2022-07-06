<!--修改标准一级才有-->
<template>
    <div>
    <a-modal :footer="null" :title="'修改赔率：'+editUsername" :width="1024" :visible="agent1ORLShow" :body-style="{ paddingBottom: '0px'}" :maskClosable="maskClosable" dialogClass="tk" @cancel="onClose">
        <div class="buhuo">
            <a-spin :spinning="spinning">
                <a-row :gutter="8" v-if="isNewUser">
                    <a-col style="float: right;display: table;width: auto;">
                        <a-button type="primary" icon="edit" size="small" @click="selectLowerModal = true">
                            复制退水赔率
                        </a-button>
                    </a-col>
                </a-row>
                <a-row :gutter="8">
                    <template v-if="!hasLower&&!hasCheckPwd">
                        <a-col v-if="!isJustLook" :span="19" class="df" style="width: calc(100% - 210px); ">
                            <span class="maintxt mlr5">安保密码:</span>
                            <a-input-password v-model="safePwd" size="small" placeholder="请输入安保密码" style="width: 150px" @keyup.enter="handleEnter" />
                            <div class="pl10">
                                <a-button type="primary" icon="key" size="small" @click="checkSafePwd">
                                    确定
                                </a-button>
                            </div>
                            <div class="red dstable mlr10 fontwe">需要修改时，先输入安保密码</div>
                        </a-col>
                        <a-col :span="2" style="float: right" class="mr10">
                            <!--
                            <a-button type="primary" icon="edit" size="small" @click="regressLimitLogShow=true">
                                赚水日志
                            </a-button>
                            -->
                            <a-button type="primary" icon="edit" size="small" @click="regressLimitLogShow=true">
                                限注日志
                            </a-button>
                        </a-col>
                    </template>
                </a-row>
                <template v-if="!hasLower&&hasCheckPwd">
                    <a-row :gutter="16">
                        <template v-for="group in groups" >
                            <a-col :span="24" :key="group.groupId" v-if="group.lotterys && group.lotterys.length>0">
                                <a-divider orientation="left" >
                                    {{group.groupName}}
                                </a-divider>
                                <a-tabs v-model="group.lotteryId" @change="changeTab(group,false)">
                                    <a-tab-pane v-for="lottery in group.lotterys" :key="lottery.lotteryId">
                                        <span slot="tab">
                                            <a-checkbox v-model="lottery.isChecked" class="mlr5"></a-checkbox>
                                            <span>{{lottery.lotteryName}}</span>
                                        </span>
                                        <table class="tableborder odds" border="0" align="center" cellpadding="2" cellspacing="1" width="100%" style="border-collapse: separate;">
                                            <tr>
                                                <th width="150" colspan="2" rowspan="2">种类</th>
                                                <th v-if="markets.A" colspan="2">A盘(%)</th>
                                                <th v-if="markets.B" colspan="2">B盘(%)</th>
                                                <th v-if="markets.C" colspan="2">C盘(%)</th>
                                                <th v-if="markets.D" colspan="2">D盘(%)</th>
                                                <template v-if="!isStandard">
                                                    <th colspan="2" rowspan="2">注单限额</th>
                                                    <th colspan="2" rowspan="2">单期限额</th>
                                                </template>
                                            </tr>
                                            <tr>
                                                <template v-if="markets.A">
                                                    <th width="103">赔率</th>
                                                    <th>退水</th>
                                                </template>
                                                <template v-if="markets.B">
                                                    <th>赔率</th>
                                                    <th>退水</th>
                                                </template>
                                                <template v-if="markets.C">
                                                    <th>赔率</th>
                                                    <th>退水</th>
                                                </template>
                                                <template v-if="markets.D">
                                                    <th>赔率</th>
                                                    <th>退水</th>
                                                </template>
                                            </tr>
                                            <template v-for="kind in lottery.kinds">
                                                <template v-for="(category,ctgrIndex) in kind.categorys">
                                                    <tr :key="category.categoryId">
                                                        <td v-if="ctgrIndex==0" :rowspan="kind.categorys.length" class="forumrow">{{kind.kindName}}</td>
                                                        <td class="forumrow">{{category.categoryName}}</td>
                                                        <template v-if="markets.A">
                                                            <td class="forumrowhighlight">
                                                                <img class="fl" :src="plus" @click="plusOrMinusOdds(kind,category,lottery.lotteryId,category.categoryId,1)">
                                                                <a-input size="small" v-model.number="category.oddsA" style="width: 55px" :class="category.isChangedA?'oddsselected':''" @change="oddsCheckField(kind,category,lottery.lotteryId,category.categoryId,'A')" />
                                                                <img class="fr" :src="minus" @click="plusOrMinusOdds(kind,category,lottery.lotteryId,category.categoryId,-1)">
                                                            </td>
                                                            <td v-if="ctgrIndex==0" :rowspan="kind.categorys.length" class="forumrowhighlight">
                                                                <template v-if="!isStandard&&!hasOrder">
                                                                    <a-input size="small" v-model.number="kind.regressA" style="width: 55px" :class="kind.isChangedA?'oddsselected':''" @change="regressCheckField(kind,lottery.lotteryId,kind.kindId,'A')" />
                                                                </template>
                                                                <template v-else>
                                                                    {{kind.regressA}}
                                                                </template>
                                                            </td>
                                                        </template>
                                                        <template v-if="markets.B">
                                                            <td class="forumrowhighlight">
                                                                <a-input size="small" v-model.number="category.oddsB" style="width: 55px" :class="category.isChangedB?'oddsselected':''" @change="oddsCheckField(kind,category,lottery.lotteryId,category.categoryId,'B')" />
                                                            </td>
                                                            <td v-if="ctgrIndex==0" :rowspan="kind.categorys.length" class="forumrowhighlight">
                                                                <template v-if="!isStandard&&!hasOrder">
                                                                    <a-input size="small" v-model.number="kind.regressB" style="width: 55px" :class="kind.isChangedB?'oddsselected':''" @change="regressCheckField(kind,lottery.lotteryId,kind.kindId,'B')" />
                                                                </template>
                                                                <template v-else>
                                                                    {{kind.regressB}}
                                                                </template>
                                                            </td>
                                                        </template>
                                                        <template v-if="markets.C">
                                                            <td class="forumrowhighlight">
                                                                <a-input size="small" v-model.number="category.oddsC" style="width: 55px" :class="category.isChangedC?'oddsselected':''" @change="oddsCheckField(kind,category,lottery.lotteryId,category.categoryId,'C')" />
                                                            </td>
                                                            <td v-if="ctgrIndex==0" :rowspan="kind.categorys.length" class="forumrowhighlight">
                                                                <template v-if="!isStandard&&!hasOrder">
                                                                    <a-input size="small" v-model.number="kind.regressC" style="width: 55px" :class="kind.isChangedC?'oddsselected':''" @change="regressCheckField(kind,lottery.lotteryId,kind.kindId,'C')" />
                                                                </template>
                                                                <template v-else>
                                                                    {{kind.regressC}}
                                                                </template>
                                                            </td>
                                                        </template>
                                                        <template v-if="markets.D">
                                                            <td class="forumrowhighlight">
                                                                <a-input size="small" v-model.number="category.oddsD" style="width: 55px" :class="category.isChangedD?'oddsselected':''" @change="oddsCheckField(kind,category,lottery.lotteryId,category.categoryId,'D')" />
                                                            </td>
                                                            <td v-if="ctgrIndex==0" :rowspan="kind.categorys.length" class="forumrowhighlight">
                                                                <template v-if="!isStandard&&!hasOrder">
                                                                    <a-input size="small" v-model.number="kind.regressD" style="width: 55px" :class="kind.isChangedD?'oddsselected':''" @change="regressCheckField(kind,lottery.lotteryId,kind.kindId,'D')" />
                                                                </template>
                                                                <template v-else>
                                                                    {{kind.regressD}}
                                                                </template>
                                                            </td>
                                                        </template>
                                                        <template v-if="!isStandard">
                                                            <td v-if="ctgrIndex==0" :rowspan="kind.categorys.length" class="forumrowhighlight" colspan="2">
                                                                <a-input size="small" v-model.number="kind.maxBet" style="width: 55px" :class="kind.isChangedMB?'oddsselected':''" @change="limitCheckField(kind,lottery.lotteryId,kind.kindId,'maxBet','baseMaxBet')" />
                                                            </td>
                                                            <td v-if="ctgrIndex==0" :rowspan="kind.categorys.length" class="forumrowhighlight" colspan="2">
                                                                <a-input size="small" v-model.number="kind.maxPeriod" style="width: 55px" :class="kind.isChangedMP?'oddsselected':''" @change="limitCheckField(kind,lottery.lotteryId,kind.kindId,'maxPeriod','baseMaxPeriod')" />
                                                            </td>
                                                        </template>
                                                    </tr>
                                                </template>
                                            </template>
                                        </table>
                                        <div class="mt16" style="text-align: center">
                                            <a-button type="primary" icon="save" size="small" class="mlr10" @click="saveORL(group,lottery)">
                                                保存
                                            </a-button>
                                        </div>
                                    </a-tab-pane>
                                    <a-button class="mr10" size="small" type="primary" slot="tabBarExtraContent" @click="changeTab(group,!group.isChecked)">
                                        <span v-if="!group.isChecked">全选</span><span v-else>取消</span>
                                    </a-button>
                                </a-tabs>
                            </a-col>
                        </template>
                    </a-row>
                </template>
                <template v-else>
                    <a-row :gutter="16">
                         <template v-for="group in groups" >
                            <a-col :span="24"  :key="group.groupId"  v-if="group.lotterys && group.lotterys.length>0">
                                <a-divider orientation="left">
                                    {{group.groupName}}
                                </a-divider>
                                <a-tabs v-model="group.lotteryId" @change="changeTab(group,false)">
                                        <a-tab-pane v-for="lottery in group.lotterys" :key="lottery.lotteryId">
                                            <span slot="tab">
                                                <!--<a-checkbox v-model="lottery.isChecked" class="mlr10"></a-checkbox>-->
                                                <span>{{lottery.lotteryName}}</span>
                                            </span>
                                            <table class="tableborder odds" border="0" align="center" cellpadding="2" cellspacing="1" width="100%" style="border-collapse: separate;">
                                                <tr>
                                                    <th width="150" colspan="2" rowspan="2">种类</th>
                                                    <th colspan="2" v-if="markets.A">A盘(%)</th>
                                                    <th colspan="2" v-if="markets.B">B盘(%)</th>
                                                    <th colspan="2" v-if="markets.C">C盘(%)</th>
                                                    <th colspan="2" v-if="markets.D">D盘(%)</th>
                                                    <template v-if="!isStandard">
                                                        <th colspan="2" rowspan="2">注单限额</th>
                                                        <th colspan="2" rowspan="2">单期限额</th>
                                                    </template>
                                                </tr>
                                                <tr>
                                                    <template v-if="markets.A">
                                                        <th width="103">赔率</th>
                                                        <th>退水</th>
                                                    </template>
                                                    <template v-if="markets.B">
                                                        <th>赔率</th>
                                                        <th>退水</th>
                                                    </template>
                                                    <template v-if="markets.C">
                                                        <th>赔率</th>
                                                        <th>退水</th>
                                                    </template>
                                                    <template v-if="markets.D">
                                                        <th>赔率</th>
                                                        <th>退水</th>
                                                    </template>
                                                </tr>
                                                <template v-for="kind in lottery.kinds">
                                                    <template v-for="(category,ctgrIndex) in kind.categorys">
                                                        <tr :key="category.categoryId">
                                                            <td v-if="ctgrIndex==0" :rowspan="kind.categorys.length" class="forumrow">{{kind.kindName}}</td>
                                                            <td class="forumrow">{{category.categoryName}}</td>
                                                            <template v-if="markets.A">
                                                                <td class="forumrowhighlight">
                                                                    {{category.oddsA}}
                                                                </td>
                                                                <td v-if="ctgrIndex==0" :rowspan="kind.categorys.length" class="forumrowhighlight">
                                                                    {{kind.regressA}}
                                                                </td>
                                                            </template>
                                                            <template v-if="markets.B">
                                                                <td class="forumrowhighlight">
                                                                    {{category.oddsB}}
                                                                </td>
                                                                <td v-if="ctgrIndex==0" :rowspan="kind.categorys.length" class="forumrowhighlight">
                                                                    {{kind.regressB}}
                                                                </td>
                                                            </template>
                                                            <template v-if="markets.C">
                                                                <td class="forumrowhighlight">
                                                                    {{category.oddsC}}
                                                                </td>
                                                                <td v-if="ctgrIndex==0" :rowspan="kind.categorys.length" class="forumrowhighlight">
                                                                    {{kind.regressC}}
                                                                </td>
                                                            </template>
                                                            <template v-if="markets.D">
                                                                <td class="forumrowhighlight">
                                                                    {{category.oddsD}}
                                                                </td>
                                                                <td v-if="ctgrIndex==0" :rowspan="kind.categorys.length" class="forumrowhighlight">
                                                                    {{kind.regressD}}
                                                                </td>
                                                            </template>
                                                            <template v-if="!isStandard">
                                                                <td v-if="ctgrIndex==0" :rowspan="kind.categorys.length" class="forumrowhighlight" colspan="2">
                                                                    {{kind.maxBet}}
                                                                </td>
                                                                <td v-if="ctgrIndex==0" :rowspan="kind.categorys.length" class="forumrowhighlight" colspan="2">
                                                                    {{kind.maxPeriod}}
                                                                </td>
                                                            </template>
                                                        </tr>
                                                    </template>
                                                </template>
                                            </table>
                                        </a-tab-pane>
                                </a-tabs>
                            </a-col>
                         </template>
                    </a-row>
                </template>
            </a-spin>
            <!--            <div class="opnewinright">-->
            <!--                <a-button type="primary" size="small" @click="onClose">-->
            <!--                    关闭-->
            <!--                </a-button>-->
            <!--            </div>-->
            <a-modal title="复制" :visible="selectLowerModal" :confirm-loading="spinning" @ok="saveCopy" @cancel="selectLowerModal=false" width="300px">
                从 <a-select style="width: 160px" v-model="fromUserId" size="small">
                    <a-select-option v-for="user in lowers" :key="user.userId" :value="user.userId">
                        {{user.username}} <template v-if="user.nickName!=null&&user.nickName!=''"> ({{user.nickName}})</template>
                    </a-select-option>
                </a-select>
                到 {{this.editUsername}}
            </a-modal>
        </div>
    </a-modal>
    <regress-limit-log :regressLimitLogShow.sync="regressLimitLogShow" :editUserId.sync="editUserId" :editUsername.sync="editUsername"></regress-limit-log>
    </div>
</template>
<script>
import to from "await-to-js";
import { setInterval, clearInterval, setTimeout, clearTimeout } from "timers";
import minus from "@/assets/AdminDefaultTheme/Images/minus.png";
import plus from "@/assets/AdminDefaultTheme/Images/plus.png";
import BigNumber from "bignumber.js";
import RegressLimitLog from "./regress-limit-log";
export default {
    components: { RegressLimitLog },
    name: "user-agent1-orl",
    props: {
        agent1ORLShow: {
            type: Boolean,
        },
        editUserId: null,
        editUsername: null,
        isNewUser: false,
        isDisk: true,
    },
    data() {
        return {
            plus,
            minus,
            maskClosable: false,
            safePwd: null,
            timers: {},
            spinning: false,
            mapOddss: null,
            mapRegresss: null,
            markets: {},
            groups: [],
            hasLower: false,
            hasOrder: false,
            hasCheckPwd: false,
            isStandard: true,
            lowers: [],
            fromUserId: null,
            selectLowerModal: false,
            isJustLook: this.$store.state.user.info.userLevel == 1,
            regressLimitLogShow:false,
        };
    },
    created() {
        //console.log("重新加载啦");
    },
    mounted() {
        //console.log("editUserId:", this.editUserId);
        this.hasCheckPwd = this.isNewUser;
        this.isStandard = this.isDisk;
        if (this.isNewUser) {
            this.isStandard = true;
        }
        this.requestORL();
    },
    methods: {
        onClose() {
            this.$emit("update:agent1ORLShow", false);
        },
        async saveCopy() {
            if (this.fromUserId == null || this.fromUserId == "") {
                this.$utils.handleThen(
                    { success: false, message: "请选择要从那个公司复制！" },
                    this
                );
                return;
            }
            let params = {
                toUserId: this.editUserId,
                fromUserId: this.fromUserId,
            };
            let [err, res] = await to(this.$api.ctrl.copyAgent1(params));
            this.spinning = false;
            this.$utils.handleThen(res, this);
            if (err || !res.success) {
                return;
            }
            this.selectLowerModal = false;
            await this.requestORL();
        },
        changeTab(group, isCheckedAll) {
            group.isChecked = isCheckedAll;
            group.lotterys.forEach((lottery) => {
                if (group.lotteryId === lottery.lotteryId) {
                    lottery.isChecked = true;
                } else {
                    lottery.isChecked = isCheckedAll;
                }
            });
        },
        handleEnter() {
            this.checkSafePwd();
        },
        async checkSafePwd() {
            if (this.safePwd == null || this.safePwd == "") {
                return;
            }
            this.spinning = true;
            let [err, res] = await to(
                this.$api.ctrl.checkSafePwd({ pwd: this.safePwd })
            );

            if (err || !res.success) {
                this.spinning = false;
                this.$utils.handleThen(res, this);
                return;
            }
            this.safePwd = null;
            this.hasCheckPwd = true;
            this.spinning = false;
        },
        oddsChange(regress, odds, lotteryId, categoryId, market, newVal) {
            let decimals = odds.decimals;
            newVal = new BigNumber(newVal)
                .multipliedBy(decimals)
                .integerValue(BigNumber.ROUND_DOWN)
                .dividedBy(decimals)
                .toNumber();
            let key = "odds" + market;
            let baseKey = "baseOdds" + market;
            let changeKey = "isChanged" + market;
            let originOdds = this.mapOddss[lotteryId][categoryId];
            let originVal = originOdds[key];
            let changeVal = newVal;
            let baseVal = odds[baseKey];
            let logicVal =
                changeVal > baseVal ? baseVal : changeVal < 1 ? 1 : changeVal;
            odds[key] = logicVal;

            let isChanged = originVal != logicVal;
            odds[changeKey] = isChanged;
            let diff = Number((originVal - logicVal).toFixed(5));
            if (market == "A") {
                ["B", "C", "D"].forEach((m) => {
                    key = "odds" + m;
                    baseKey = "baseOdds" + m;
                    changeKey = "isChanged" + m;
                    logicVal = new BigNumber(originOdds[key]).minus(diff);
                    logicVal = Math.max(Math.min(logicVal, odds[baseKey]), 1);
                    logicVal = new BigNumber(logicVal)
                        .multipliedBy(decimals)
                        .integerValue(BigNumber.ROUND_DOWN)
                        .dividedBy(decimals)
                        .toNumber();
                    odds[key] = logicVal;
                    isChanged = originOdds[key] != logicVal;
                    odds[changeKey] = isChanged;
                });
            }
            odds.isDirty = true;
        },
        plusOrMinusOdds(regress, odds, lotteryId, categoryId, zf) {
            let timerKey = "pm_" + categoryId;
            if (!this.timers[timerKey]) {
                this.timers[timerKey] = { id: null, dj: 0 };
            }
            let timer = this.timers[timerKey];
            timer.dj = timer.dj + 1;
            clearTimeout(timer.id);
            timer.id = setTimeout(() => {
                let diff = new BigNumber(odds.step)
                    .multipliedBy(zf)
                    .multipliedBy(timer.dj);
                let newVal = new BigNumber(odds["oddsA"]).plus(diff);
                this.oddsChange(
                    regress,
                    odds,
                    lotteryId,
                    categoryId,
                    "A",
                    newVal
                );
                timer.dj = 0;
            }, 500);
        },
        oddsCheckField(regress, odds, lotteryId, categoryId, market) {
            let timerKey = market + "_" + categoryId;
            if (!this.timers[timerKey]) {
                this.timers[timerKey] = { id: null, dj: 0 };
            }
            let timer = this.timers[timerKey];
            clearTimeout(timer.id);
            timer.id = setTimeout(() => {
                let newVal = odds["odds" + market];
                console.log(lotteryId, categoryId, newVal);
                this.oddsChange(
                    regress,
                    odds,
                    lotteryId,
                    categoryId,
                    market,
                    newVal
                );
            }, 500);
        },
        regressCheckField(regress, lotteryId, kindId, market) {
            let timerKey = "r_" + kindId;
            clearTimeout(this.timers[timerKey]);
            this.timers[timerKey] = setTimeout(() => {
                let key = "regress" + market;
                let baseKey = "baseRegress" + market;
                let changeKey = "isChanged" + market;

                let oddsKey = "odds" + market;
                let baseOddsKey = "baseOdds" + market;
                let changeOddsKey = "isChanged" + market;

                let originVal = this.mapRegresss[lotteryId][kindId][key];
                let changeVal = regress[key];
                let baseVal = regress[baseKey];
                let logicVal =
                    changeVal > baseVal
                        ? baseVal
                        : changeVal < 0
                        ? 0
                        : changeVal;
                regress[key] = logicVal;
                let isChanged = originVal != logicVal;
                regress[changeKey] = isChanged;

                regress.categorys.forEach((category) => {
                    let baseOdds =
                        Math.round(
                            (((100 - logicVal) * category.odds) / 100) * 100000
                        ) / 100000;
                    category[baseOddsKey] = baseOdds;
                    let curOdds = category[oddsKey];
                    let newVal = Math.min(baseOdds, curOdds);

                    category[baseOddsKey] = baseOdds;
                    category[oddsKey] = newVal;

                    let originVal = this.mapOddss[lotteryId][
                        category.categoryId
                    ][oddsKey];
                    category[changeOddsKey] = originVal != newVal;
                });
            }, 500);
        },
        limitCheckField(regress, lotteryId, kindId, key, baseKey) {
            let timerKey = "l_" + kindId;
            clearTimeout(this.timers[timerKey]);
            this.timers[timerKey] = setTimeout(() => {
                let originVal = this.mapRegresss[lotteryId][kindId][key];
                let changeVal = regress[key];
                let baseVal = regress[baseKey];

                let logicVal =
                    changeVal > baseVal
                        ? baseVal
                        : changeVal < 0
                        ? 0
                        : changeVal;
                regress[key] = logicVal;

                let isChanged = originVal != logicVal;
                let changeKey = "isChanged" + (key == "maxBet" ? "MB" : "MP");
                regress[changeKey] = isChanged;
            }, 500);
        },
        async requestORL() {
            if (this.editUserId == 0 || !this.agent1ORLShow) {
                return;
            }
            this.spinning = true;
            let [err, res] = await to(
                this.$api.ctrl.getOdds({ userId: this.editUserId })
            );
            if (err || !res.success) {
                this.spinning = false;
                this.$swal({
                    title: err ? err.message : res.message,
                    icon: "error",
                    confirmButtonText: "确定",
                });
                return;
            }
            let {
                groups,
                kinds: mapKinds,
                categorys: mapCategorys,
                lotterys: mapLotterys,
                regress: mapRegresss,
                odds: mapOddss,
                markets,
                hasLower,
                hasOrder,
                lowers,
            } = res.data;
            this.lowers = lowers.filter(
                (lower) => lower.userId != this.editUserId
            );
            this.hasOrder = hasOrder;
            //this.hasLower = hasLower;
            this.hasLower = false;
            this.mapOddss = mapOddss;
            this.mapRegresss = mapRegresss;
            this.markets = markets;
            groups.forEach((group) => {
                let groupId = group.groupId;
                let lotterys = mapLotterys[groupId]||[];
                lotterys.forEach((lottery) => {
                    let lotteryId = lottery.lotteryId;
                    let kinds = JSON.parse(JSON.stringify(mapKinds[groupId]));
                    kinds.forEach((kind) => {
                        let kindId = kind.kindId;
                        let regress = mapRegresss[lotteryId][kindId];
                        Object.assign(kind, regress);
                        let categorys = JSON.parse(
                            JSON.stringify(mapCategorys[kindId])
                        );
                        categorys.forEach((category) => {
                            let categoryId = category.categoryId;
                            let odds = mapOddss[lotteryId][categoryId];
                            category.isChangedA = false;
                            category.isChangedB = false;
                            category.isChangedC = false;
                            category.isChangedD = false;
                            category.isChangedF = false;
                            Object.assign(category, odds);
                        });
                        kind.isChangedA = false;
                        kind.isChangedB = false;
                        kind.isChangedC = false;
                        kind.isChangedD = false;
                        kind.isChangedMB = false;
                        kind.isChangedMP = false;
                        kind.isDirty = false;
                        kind.categorys = categorys;
                    });
                    //lottery.isChecked = false;
                    lottery.isChecked = true;
                    lottery.kinds = kinds;
                });
                //lotterys[0].isChecked = true;
                //group.isChecked = false;
                group.isChecked = true;
                group.lotterys = lotterys;
                if(lotterys.length>0){
                    group.lotteryId = lotterys[0].lotteryId;
                }
         
               
            });
            this.groups = groups;
            this.spinning = false;
        },
        async saveORL(group, lottery) {
            let lotterys = group.lotterys.filter(
                (lottery) => lottery.isChecked
            );
            let lotteryIds = lotterys.map((lottery) => lottery.lotteryId);
            let params = {
                userId: this.editUserId,
                lotteryIds,
                regresss: [],
                oddss: [],
                limits: [],
                fulloddss: [],
            };
            lottery.kinds.forEach((regress) => {
                if (
                    regress.isChangedA ||
                    regress.isChangedB ||
                    regress.isChangedC ||
                    regress.isChangedD
                ) {
                    let {
                        kindId,
                        regressA,
                        regressB,
                        regressC,
                        regressD,
                    } = regress;
                    params.regresss.push({
                        kindId,
                        regressA,
                        regressB,
                        regressC,
                        regressD,
                    });
                }
                if (regress.isChangedMB || regress.isChangedMP) {
                    let { kindId, maxBet, maxPeriod } = regress;
                    params.limits.push({ kindId, maxBet, maxPeriod });
                }
                regress.categorys.forEach((category) => {
                    if (
                        category.isChangedA ||
                        category.isChangedB ||
                        category.isChangedC ||
                        category.isChangedD
                    ) {
                        let {
                            kindId,
                            categoryId,
                            oddsA,
                            oddsB,
                            oddsC,
                            oddsD,
                        } = category;
                        params.oddss.push({
                            kindId,
                            categoryId,
                            oddsA,
                            oddsB,
                            oddsC,
                            oddsD,
                        });
                    }
                });
            });
            this.spinning = true;
            let [err, res] = await to(this.$api.ctrl.updateAgent1ORL(params));
            this.spinning = false;
            this.$utils.handleThen(res, this);
            if (err || !res.success) {
                return;
            }
            lotterys.forEach((lottery) => {
                let { lotteryId } = lottery;
                params.regresss.forEach((regress) => {
                    let {
                        kindId,
                        regressA,
                        regressB,
                        regressC,
                        regressD,
                    } = regress;
                    let o = this.mapRegresss[lotteryId][kindId];
                    o.regressA = regressA;
                    o.regressB = regressB;
                    o.regressC = regressC;
                    o.regressD = regressD;
                    let n = lottery.kinds.find((kind) => kind.kindId == kindId);
                    n.regressA = regressA;
                    n.regressB = regressB;
                    n.regressC = regressC;
                    n.regressD = regressD;
                    n.isChangedA = false;
                    n.isChangedB = false;
                    n.isChangedC = false;
                    n.isChangedD = false;
                });
                params.limits.forEach((limit) => {
                    let { kindId, maxBet, maxPeriod } = limit;
                    let o = this.mapRegresss[lotteryId][kindId];
                    o.maxBet = maxBet;
                    o.maxPeriod = maxPeriod;
                    let n = lottery.kinds.find((kind) => kind.kindId == kindId);
                    n.maxBet = maxBet;
                    n.maxPeriod = maxPeriod;
                    n.isChangedMB = false;
                    n.isChangedMP = false;
                });

                params.oddss.forEach((odds) => {
                    let {
                        kindId,
                        categoryId,
                        oddsA,
                        oddsB,
                        oddsC,
                        oddsD,
                    } = odds;
                    let logic = this.mapOddss[lotteryId][categoryId];
                    logic.oddsA = oddsA;
                    logic.oddsB = oddsB;
                    logic.oddsC = oddsC;
                    logic.oddsD = oddsD;
                    let n = lottery.kinds
                        .find((kind) => kind.kindId == kindId)
                        .categorys.find(
                            (category) => category.categoryId == categoryId
                        );
                    n.oddsA = oddsA;
                    n.oddsB = oddsB;
                    n.oddsC = oddsC;
                    n.oddsD = oddsD;
                    n.isChangedA = false;
                    n.isChangedB = false;
                    n.isChangedC = false;
                    n.isChangedD = false;
                });
            });
        },
    },
    watch: {},
};
</script>
<style scoped>
</style>
