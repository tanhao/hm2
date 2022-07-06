<template>
    <div class="buhuo">
        <a-spin :spinning="spinning" size="large">
            <a-row :gutter="8">
                <template v-if="info.userLevel<=2&&!hasCheckPwd">
                    <a-col :span="20" class="df" style="width: calc(100% - 210px); ">
                        <span class="maintxt mlr5">安保密码:</span>
                        <a-input-password v-model="safePwd" size="small" placeholder="请输入安保密码" style="width: 150px" @keyup.enter="handleEnter" />
                        <div class="pl10">
                            <a-button type="primary" icon="key" size="small" @click="checkSafePwd">
                                确定
                            </a-button>
                        </div>
                        <div class="red dstable mlr10 fontwe">需要修改时，先输入安保密码</div>
                    </a-col>
                </template>
                <a-col v-if="this.userId==null" :span="4" style="float: right;display: table;width: auto;">
                    <a-button type="primary" icon="edit" size="small">
                        修改日志
                    </a-button>
                </a-col>
            </a-row>
            <template v-if="info.userLevel==1&&hasCheckPwd">
                <a-row :gutter="16">
                    <a-col :span="24" v-for="group in groups" :key="group.groupId">
                        <a-divider orientation="left">
                            {{group.groupName}}
                        </a-divider>
                        <a-tabs v-model="group.lotteryId" @change="changeTab(group,false)">
                            <a-tab-pane v-for="lottery in group.lotterys" :key="lottery.lotteryId">
                                <span slot="tab">
                                    <a-checkbox v-model="lottery.isChecked" class="mlr5"></a-checkbox>
                                    <span>{{lottery.lotteryName}}</span>
                                </span>
                                <table class="tableborder odds" border="0" align="center" cellpadding="2" cellspacing="1">
                                    <tr>
                                        <th width="150" colspan="2" rowspan="2">种类</th>
                                        <th v-if="markets.A" :colspan="info.userLevel==1?3:2">A盘(%)</th>
                                        <th v-if="markets.B" colspan="2">B盘(%)</th>
                                        <th v-if="markets.C" colspan="2">C盘(%)</th>
                                        <th v-if="markets.D" colspan="2">D盘(%)</th>
                                        <th rowspan="2">注单限额</th>
                                        <th rowspan="2">单期限额</th>
                                        <th width="60" rowspan="2">操作</th>
                                    </tr>
                                    <tr>
                                        <th v-if="info.userLevel==1">满盘值</th>
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
                                                <td class="forumrowhighlight" v-if="info.userLevel==1">
                                                    <a-input size="small" v-model.number="category.odds" style="width: 55px" :class="category.isChangedF?'oddsselected':''" @change="fullOddsCheckField(kind,category,lottery.lotteryId,category.categoryId)" />
                                                </td>
                                                <template v-if="markets.A">
                                                    <td class="forumrowhighlight">
                                                        <img class="fl" :src="plus" @click="plusOrMinusOdds(kind,category,lottery.lotteryId,category.categoryId,1)">
                                                        <a-input size="small" v-model.number="category.oddsA" style="width: 55px" :class="category.isChangedA?'oddsselected':''" @change="oddsCheckField(kind,category,lottery.lotteryId,category.categoryId,'A')" />
                                                        <img class="fr" :src="minus" @click="plusOrMinusOdds(kind,category,lottery.lotteryId,category.categoryId,-1)">
                                                    </td>
                                                    <td v-if="ctgrIndex==0" :rowspan="kind.categorys.length" class="forumrowhighlight">
                                                        <a-input size="small" v-model.number="kind.regressA" style="width: 55px" :class="kind.isChangedA?'oddsselected':''" @change="regressCheckField(kind,lottery.lotteryId,kind.kindId,'A')" />
                                                    </td>
                                                </template>
                                                <template v-if="markets.B">
                                                    <td class="forumrowhighlight">
                                                        <a-input size="small" v-model.number="category.oddsB" style="width: 55px" :class="category.isChangedB?'oddsselected':''" @change="oddsCheckField(kind,category,lottery.lotteryId,category.categoryId,'B')" />
                                                    </td>
                                                    <td v-if="ctgrIndex==0" :rowspan="kind.categorys.length" class="forumrowhighlight">
                                                        <a-input size="small" v-model.number="kind.regressB" style="width: 55px" :class="kind.isChangedB?'oddsselected':''" @change="regressCheckField(kind,lottery.lotteryId,kind.kindId,'B')" />
                                                    </td>
                                                </template>
                                                <template v-if="markets.C">
                                                    <td class="forumrowhighlight">
                                                        <a-input size="small" v-model.number="category.oddsC" style="width: 55px" :class="category.isChangedC?'oddsselected':''" @change="oddsCheckField(kind,category,lottery.lotteryId,category.categoryId,'C')" />
                                                    </td>
                                                    <td v-if="ctgrIndex==0" :rowspan="kind.categorys.length" class="forumrowhighlight">
                                                        <a-input size="small" v-model.number="kind.regressC" style="width: 55px" :class="kind.isChangedC?'oddsselected':''" @change="regressCheckField(kind,lottery.lotteryId,kind.kindId,'C')" />
                                                    </td>
                                                </template>
                                                <template v-if="markets.D">
                                                    <td class="forumrowhighlight">
                                                        <a-input size="small" v-model.number="category.oddsD" style="width: 55px" :class="category.isChangedD?'oddsselected':''" @change="oddsCheckField(kind,category,lottery.lotteryId,category.categoryId,'D')" />
                                                    </td>
                                                    <td v-if="ctgrIndex==0" :rowspan="kind.categorys.length" class="forumrowhighlight">
                                                        <a-input size="small" v-model.number="kind.regressD" style="width: 55px" :class="kind.isChangedD?'oddsselected':''" @change="regressCheckField(kind,lottery.lotteryId,kind.kindId,'D')" />
                                                        <!-- class="oddsselected" -->
                                                    </td>
                                                </template>
                                                <td v-if="ctgrIndex==0" :rowspan="kind.categorys.length" class="forumrowhighlight">
                                                    <a-input size="small" v-model.number="kind.maxBet" style="width: 55px" :class="kind.isChangedMB?'oddsselected':''" @change="limitCheckField(kind,lottery.lotteryId,kind.kindId,'maxBet','baseMaxBet')" />
                                                </td>
                                                <td v-if="ctgrIndex==0" :rowspan="kind.categorys.length" class="forumrowhighlight">
                                                    <a-input size="small" v-model.number="kind.maxPeriod" style="width: 55px" :class="kind.isChangedMP?'oddsselected':''" @change="limitCheckField(kind,lottery.lotteryId,kind.kindId,'maxPeriod','baseMaxPeriod')" />
                                                </td>
                                            </tr>
                                        </template>
                                    </template>
                                </table>
                            </a-tab-pane>
                            <a-button class="mr10" size="small" type="primary" slot="tabBarExtraContent" @click="changeTab(group,!group.isChecked)">
                                <span v-if="!group.isChecked">全选</span><span v-else>取消</span>
                            </a-button>
                        </a-tabs>
                    </a-col>
                </a-row>
            </template>
            <template v-else-if="info.userLevel==2&&hasCheckPwd">
                <a-row :gutter="16">
                    <a-col :span="24" v-for="group in groups" :key="group.groupId">
                        <a-divider orientation="left">
                            {{group.groupName}}
                        </a-divider>
                        <a-tabs v-model="group.lotteryId" @change="changeTab(group,false)">
                            <a-tab-pane v-for="lottery in group.lotterys" :key="lottery.lotteryId">
                                <span slot="tab">
                                    <a-checkbox v-model="lottery.isChecked" class="mlr5"></a-checkbox>
                                    <span>{{lottery.lotteryName}}</span>
                                </span>
                                <table class="tableborder odds" border="0" align="center" cellpadding="2" cellspacing="1">
                                    <tr>
                                        <th width="150" colspan="2" rowspan="2">种类</th>
                                        <th v-if="markets.A" :colspan="info.userLevel==1?3:2">A盘(%)</th>
                                        <th v-if="markets.B" colspan="2">B盘(%)</th>
                                        <th v-if="markets.C" colspan="2">C盘(%)</th>
                                        <th v-if="markets.D" colspan="2">D盘(%)</th>
                                        <th colspan="2" rowspan="2">注单限额</th>
                                        <th colspan="2" rowspan="2">单期限额</th>
                                    </tr>
                                    <tr>
                                        <th v-if="info.userLevel==1">满盘值</th>
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
                                                <td class="forumrowhighlight" v-if="info.userLevel==1">
                                                    <a-input size="small" v-model.number="category.odds" style="width: 55px" :class="category.isChangedF?'oddsselected':''" @change="fullOddsCheckField(kind,category,lottery.lotteryId,category.categoryId)" />
                                                </td>
                                                <template v-if="markets.A">
                                                    <td class="forumrowhighlight">
                                                        <img class="fl" :src="plus" @click="plusOrMinusOdds(kind,category,lottery.lotteryId,category.categoryId,1)">
                                                        <a-input size="small" v-model.number="category.oddsA" style="width: 55px" :class="category.isChangedA?'oddsselected':''" @change="oddsCheckField(kind,category,lottery.lotteryId,category.categoryId,'A')" />
                                                        <img class="fr" :src="minus" @click="plusOrMinusOdds(kind,category,lottery.lotteryId,category.categoryId,-1)">
                                                    </td>
                                                    <td v-if="ctgrIndex==0" :rowspan="kind.categorys.length" class="forumrowhighlight">
                                                        <!-- <a-input size="small" v-model.number="kind.regressA" style="width: 55px" :class="kind.isChangedA?'oddsselected':''" @change="regressCheckField(kind,lottery.lotteryId,kind.kindId,'A')" /> -->
                                                        {{kind.regressA}}
                                                    </td>
                                                </template>
                                                <template v-if="markets.B">
                                                    <td class="forumrowhighlight">
                                                        <a-input size="small" v-model.number="category.oddsB" style="width: 55px" :class="category.isChangedB?'oddsselected':''" @change="oddsCheckField(kind,category,lottery.lotteryId,category.categoryId,'B')" />
                                                    </td>
                                                    <td v-if="ctgrIndex==0" :rowspan="kind.categorys.length" class="forumrowhighlight">
                                                        <!-- <a-input size="small" v-model.number="kind.regressB" style="width: 55px" :class="kind.isChangedB?'oddsselected':''" @change="regressCheckField(kind,lottery.lotteryId,kind.kindId,'B')" /> -->
                                                        {{kind.regressB}}
                                                    </td>
                                                </template>
                                                <template v-if="markets.C">
                                                    <td class="forumrowhighlight">
                                                        <a-input size="small" v-model.number="category.oddsC" style="width: 55px" :class="category.isChangedC?'oddsselected':''" @change="oddsCheckField(kind,category,lottery.lotteryId,category.categoryId,'C')" />
                                                    </td>
                                                    <td v-if="ctgrIndex==0" :rowspan="kind.categorys.length" class="forumrowhighlight">
                                                        <!-- <a-input size="small" v-model.number="kind.regressC" style="width: 55px" :class="kind.isChangedC?'oddsselected':''" @change="regressCheckField(kind,lottery.lotteryId,kind.kindId,'C')" /> -->
                                                        {{kind.regressC}}
                                                    </td>
                                                </template>
                                                <template v-if="markets.D">
                                                    <td class="forumrowhighlight">
                                                        <a-input size="small" v-model.number="category.oddsD" style="width: 55px" :class="category.isChangedD?'oddsselected':''" @change="oddsCheckField(kind,category,lottery.lotteryId,category.categoryId,'D')" />
                                                    </td>
                                                    <td v-if="ctgrIndex==0" :rowspan="kind.categorys.length" class="forumrowhighlight">
                                                        <!-- <a-input size="small" v-model.number="kind.regressD" style="width: 55px" :class="kind.isChangedD?'oddsselected':''" @change="regressCheckField(kind,lottery.lotteryId,kind.kindId,'D')" /> -->
                                                        {{kind.regressD}}
                                                    </td>
                                                </template>
                                                <td v-if="ctgrIndex==0" :rowspan="kind.categorys.length" class="forumrowhighlight" colspan="2">
                                                    <a-input size="small" v-model.number="kind.maxBet" style="width: 55px" :class="kind.isChangedMB?'oddsselected':''" @change="limitCheckField(kind,lottery.lotteryId,kind.kindId,'maxBet','baseMaxBet')" />
                                                </td>
                                                <td v-if="ctgrIndex==0" :rowspan="kind.categorys.length" class="forumrowhighlight" colspan="2">
                                                    <a-input size="small" v-model.number="kind.maxPeriod" style="width: 55px" :class="kind.isChangedMP?'oddsselected':''" @change="limitCheckField(kind,lottery.lotteryId,kind.kindId,'maxPeriod','baseMaxPeriod')" />
                                                </td>
                                            </tr>
                                        </template>
                                    </template>
                                </table>
                                <div class="mt16" style="text-align: center">
                                    <a-button type="primary" icon="save" size="small" class="mlr10" @click="saveORLF(group,lottery)">
                                        保存
                                    </a-button>
                                </div>
                            </a-tab-pane>
                            <a-button class="mr10" size="small" type="primary" slot="tabBarExtraContent" @click="changeTab(group,!group.isChecked)">
                                <span v-if="!group.isChecked">全选</span><span v-else>取消</span>
                            </a-button>
                        </a-tabs>
                    </a-col>
                </a-row>
            </template>
            <template v-else>
                <a-row :gutter="16">
                    <a-col :span="24" v-for="group in groups" :key="group.groupId">
                        <a-divider orientation="left">
                            {{group.groupName}}
                        </a-divider>
                        <a-tabs v-model="group.lotteryId" @change="changeTab(group,false)">
                            <a-tab-pane v-for="lottery in group.lotterys" :key="lottery.lotteryId">
                                <span slot="tab">
                                    <!--<a-checkbox v-model="lottery.isChecked" class="mlr10"></a-checkbox>-->
                                    <span>{{lottery.lotteryName}}</span>
                                </span>
                                <table class="tableborder odds" border="0" align="center" cellpadding="2" cellspacing="1">
                                    <tr>
                                        <th width="150" colspan="2" rowspan="2">种类</th>
                                        <th :colspan="info.userLevel==1?3:2">A盘(%)</th>
                                        <th colspan="2">B盘(%)</th>
                                        <th colspan="2">C盘(%)</th>
                                        <th colspan="2">D盘(%)</th>
                                        <th colspan="2" rowspan="2">注单限额</th>
                                        <th colspan="2" rowspan="2">单期限额</th>
                                    </tr>
                                    <tr>
                                        <th v-if="info.userLevel==1">满盘值</th>
                                        <th width="103">赔率</th>
                                        <th>退水</th>
                                        <th>赔率</th>
                                        <th>退水</th>
                                        <th>赔率</th>
                                        <th>退水</th>
                                        <th>赔率</th>
                                        <th>退水</th>
                                    </tr>
                                    <template v-for="kind in lottery.kinds">
                                        <template v-for="(category,ctgrIndex) in kind.categorys">
                                            <tr :key="category.categoryId">
                                                <td v-if="ctgrIndex==0" :rowspan="kind.categorys.length" class="forumrow">{{kind.kindName}}</td>
                                                <td class="forumrow">{{category.categoryName}}</td>
                                                <td class="forumrowhighlight" v-if="info.userLevel==1">
                                                    {{category.odds}}
                                                </td>
                                                <td class="forumrowhighlight">
                                                    {{category.oddsA}}
                                                </td>
                                                <td v-if="ctgrIndex==0" :rowspan="kind.categorys.length" class="forumrowhighlight">
                                                    {{kind.regressA}}
                                                </td>
                                                <td class="forumrowhighlight">
                                                    {{category.oddsB}}
                                                </td>
                                                <td v-if="ctgrIndex==0" :rowspan="kind.categorys.length" class="forumrowhighlight">
                                                    {{kind.regressB}}
                                                </td>
                                                <td class="forumrowhighlight">
                                                    {{category.oddsC}}
                                                </td>
                                                <td v-if="ctgrIndex==0" :rowspan="kind.categorys.length" class="forumrowhighlight">
                                                    {{kind.regressC}}
                                                </td>
                                                <td class="forumrowhighlight">
                                                    {{category.oddsD}}
                                                </td>
                                                <td v-if="ctgrIndex==0" :rowspan="kind.categorys.length" class="forumrowhighlight">
                                                    {{kind.regressD}}
                                                </td>
                                                <td v-if="ctgrIndex==0" :rowspan="kind.categorys.length" class="forumrowhighlight" colspan="2">
                                                    {{kind.maxBet}}
                                                </td>
                                                <td v-if="ctgrIndex==0" :rowspan="kind.categorys.length" class="forumrowhighlight" colspan="2">
                                                    {{kind.maxPeriod}}
                                                </td>
                                            </tr>
                                        </template>
                                    </template>
                                </table>
                            </a-tab-pane>
                        </a-tabs>
                    </a-col>
                </a-row>
            </template>
        </a-spin>
    </div>
</template>
<script>
import { mapGetters } from "vuex";
import { setInterval, clearInterval, setTimeout, clearTimeout } from "timers";
import minus from "@/assets/AdminDefaultTheme/Images/minus.png";
import plus from "@/assets/AdminDefaultTheme/Images/plus.png";
import to from "await-to-js";
import BigNumber from "bignumber.js";

export default {
    data() {
        return {
            plus,
            minus,
            timers: {},
            userId: null,
            spinning: false,
            mapOddss: null,
            mapRegresss: null,
            markets: {},
            groups: [],
            safePwd: null,
            hasCheckPwd: false,
        };
    },
    mounted() {
        let userId = this.$route.params.userId;
        if (userId) {
            this.userId = userId;
            this.$route.params.userId = undefined;
        }
        this.requestORLF();
    },
    computed: {
        ...mapGetters(["info"]),
    },
    methods: {
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
            //let regressKey = "regress" + market;
            let originOdds = this.mapOddss[lotteryId][categoryId];
            let originVal = originOdds[key];
            let changeVal = newVal;
            let baseVal = odds[baseKey];
            let logicVal =
                changeVal > baseVal ? baseVal : changeVal < 1 ? 1 : changeVal;
            odds[key] = logicVal;
            let isChanged = originVal != logicVal;
            odds[changeKey] = isChanged;
            let diff = new BigNumber(originVal).minus(logicVal);
            //console.log("diff:", diff, originVal, logicVal);
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
        fullOddsCheckField(regress, odds, lotteryId, categoryId) {
            let timerKey = "f_" + kindId;
            clearTimeout(this.timers[timerKey]);
            this.timers[timerKey] = setTimeout(() => {
                let key = "odds";
                let baseKey = "baseOdds";
                let changeKey = "isChangedF";
                let originOdds = this.mapOddss[lotteryId][categoryId];
                let originVal = originOdds[key];
                let changeVal = odds[key];
                let baseVal = odds[baseKey];
                let logicVal =
                    changeVal > baseVal
                        ? baseVal
                        : changeVal < 1
                        ? 1
                        : changeVal;
                odds[key] = logicVal;
                let isChanged = originVal != logicVal;
                odds[changeKey] = isChanged;

                ["A", "B", "C", "D"].forEach((market) => {
                    key = "odds" + market;
                    baseKey = "baseOdds" + market;
                    changeKey = "isChanged" + market;
                    let regressKey = "regress" + market;
                    odds[baseKey] =
                        Math.round(
                            (((100 - regress[regressKey]) * logicVal) / 100) *
                                100000
                        ) / 100000;
                    odds[key] = Math.min(odds[baseKey], odds[key]);
                    isChanged = originOdds[key] != odds[key];
                    odds[changeKey] = isChanged;
                });
            }, 500);
        },
        async requestORLF() {
            this.spinning = true;
            let [err, res] = await to(
                this.$api.ctrl.getOdds({ userId: this.userId })
            );
            if (err || !res.success) {
                this.spinning = false;
                this.$utils.handleThen(res, this);
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
            } = res.data;
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
                    lottery.isChecked = false;
                    lottery.kinds = kinds;
                });
                if(lotterys.length>0){
                    lotterys[0].isChecked = true;
                     group.lotteryId = lotterys[0].lotteryId;
                }
                group.isChecked = false;
                group.lotterys = lotterys;
            });
            this.groups = groups;
            this.spinning = false;
        },
        async saveORLF(group, lottery) {
            let lotterys = group.lotterys.filter(
                (lottery) => lottery.isChecked
            );
            let lotteryIds = lotterys.map((lottery) => lottery.lotteryId);

            let params = {
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
                    if (category.isChangedF) {
                        let { kindId, categoryId, odds } = category;
                        params.fulloddss.push({
                            kindId,
                            categoryId,
                            odds,
                        });
                    }
                });
            });
            this.spinning = true;
            let [err, res] = await to(this.$api.ctrl.updateORLF(params));
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

                params.fulloddss.forEach((fullodds) => {
                    let { kindId, categoryId, odds } = fullodds;
                    let o = this.mapOddss[lotteryId][categoryId];
                    o.odds = odds;
                    let n = lottery.kinds
                        .find((kind) => kind.kindId == kindId)
                        .categorys.find(
                            (category) => category.categoryId == categoryId
                        );
                    n.odds = odds;
                    n.isChangedF = false;
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
};
</script>
<style scoped>
</style>