<template>
<div>
    <a-modal :footer="null" :title="'修改赚水：'+editUsername" :width="1024" :visible="userZhuanRegressShow" :body-style="{ paddingBottom: '0px'}" :maskClosable="maskClosable" dialogClass="tk" @cancel="onClose">
        <div class="buhuo allhigit">
            <a-spin :spinning="spinning">
                <a-row :gutter="8" style="margin-bottom:4px">
                    <a-col style="float: right;display: table;width: auto;">
                        <template v-if="!isJustLook">
                            赚取退水：
                            <a-input v-model.number="diff" size="small" style="width: 120px" />
                            <a-button type="primary" icon="setting" size="small" class="mlr5" @click="quiskSetting">
                                快速设置
                            </a-button>
                        </template>
                        <!--
                        <a-button type="primary" icon="edit" size="small">
                            赚水日志
                        </a-button>
                        -->
                         <a-button type="primary" icon="edit" size="small" @click="regressLimitLogShow=true">
                            限注日志
                        </a-button>
                    </a-col>
                </a-row>
                <a-row :gutter="16">
                    <a-col :span="24" v-if="!isJustLook">
                        <table class="tableborder odds" border="0" align="center" cellpadding="2" cellspacing="1" width="100%" style="border-collapse: separate;">
                            <tr>
                                <th colspan="8">大项快速设置</th>
                            </tr>
                            <tr>
                                <th width="150">调整项目</th>
                                <th v-if="markets.A">A盘(%)</th>
                                <th v-if="markets.B">B盘(%)</th>
                                <th v-if="markets.C">C盘(%)</th>
                                <th v-if="markets.D">D盘(%)</th>
                                <th>注单限额</th>
                                <th>单期限额</th>
                                <th>操作</th>
                            </tr>
                            <template v-for="qs in quickSetting">
                                <tr :key="qs.id">
                                    <td class="forumrow">{{qs.quickSettingName}}</td>
                                    <template v-if="markets.A">
                                        <td class="forumrowhighlight">
                                            <a-input size="small" v-model.number="qs.regressA" style="width: 55px" />
                                            ≤{{qs.baseRegressA}}
                                        </td>
                                    </template>
                                    <template v-if="markets.B">
                                        <td class="forumrowhighlight">
                                            <a-input size="small" v-model.number="qs.regressB" style="width: 55px" />
                                            ≤{{qs.baseRegressB}}
                                        </td>
                                    </template>
                                    <template v-if="markets.C">
                                        <td class="forumrowhighlight">
                                            <a-input size="small" v-model.number="qs.regressC" style="width: 55px" />
                                            ≤{{qs.baseRegressC}}
                                        </td>
                                    </template>
                                    <template v-if="markets.D">
                                        <td class="forumrowhighlight">
                                            <a-input size="small" v-model.number="qs.regressD" style="width: 55px" />
                                            ≤{{qs.baseRegressD}}
                                        </td>
                                    </template>
                                    <td class="forumrowhighlight">
                                        <a-input size="small" v-model.number="qs.maxBet" style="width: 55px" />
                                    </td>
                                    <td class="forumrowhighlight">
                                        <a-input size="small" v-model.number="qs.maxPeriod" style="width: 55px" />
                                    </td>
                                    <td class="forumrowhighlight">
                                        <a-button type="primary" size="small" class="mlr10" @click="qsSetting(qs)">
                                            修改
                                        </a-button>
                                    </td>
                                </tr>
                            </template>
                        </table>
                    </a-col>
                </a-row>
                <a-row :gutter="16">
                     <template v-for="group in groups" >
                        <a-col :span="24" :key="group.groupId" v-if="group.lotterys && group.lotterys.length>0">
                        <a-divider orientation="left">
                            {{group.groupName}}
                        </a-divider>
                        <template v-for="lottery in group.lotterys">
                            <a-row :gutter="5" :key="lottery.lotteryId">
                                <a-col :span="24">
                                    <div class="demotit">{{lottery.lotteryName}}</div>
                                </a-col>
                                <template v-if="isJustLook">
                                    <a-col :span="10">
                                        <table class="tableborder odds" border="0" align="center" cellpadding="2" cellspacing="1" width="100%" style="border-collapse: separate;">
                                            <tr>
                                                <th colspan="5">当前值</th>
                                            </tr>
                                            <tr>
                                                <th width="80">种类</th>
                                                <th>A盘%</th>
                                                <th>B盘%</th>
                                                <th>C盘%</th>
                                                <th>D盘%</th>
                                            </tr>
                                            <template v-for="kind in lottery.kinds">
                                                <tr :key="kind.kindId">
                                                    <td class="forumrow">{{kind.kindName}}</td>
                                                    <td class="forumrowhighlight">{{kind.regressA}} </td>
                                                    <td class="forumrowhighlight">{{kind.regressB}} </td>
                                                    <td class="forumrowhighlight">{{kind.regressC}} </td>
                                                    <td class="forumrowhighlight">{{kind.regressD}} </td>
                                                </tr>
                                            </template>
                                        </table>
                                    </a-col>
                                    <a-col :span="10">
                                        <table class="tableborder odds" border="0" align="center" cellpadding="2" cellspacing="1" width="100%" style="border-collapse: separate;">
                                            <tr>
                                                <th colspan="4">预修改值*隔天生效</th>
                                            </tr>
                                            <tr>
                                                <th>A盘%</th>
                                                <th>B盘%</th>
                                                <th>C盘%</th>
                                                <th>D盘%</th>
                                            </tr>
                                            <template v-for="kind in lottery.kinds">
                                                <tr :key="kind.kindId">
                                                    <td class="forumrowhighlight">{{kind.tmrRegressA}}</td>
                                                    <td class="forumrowhighlight">{{kind.tmrRegressB}} </td>
                                                    <td class="forumrowhighlight">{{kind.tmrRegressC}} </td>
                                                    <td class="forumrowhighlight">{{kind.tmrRegressD}}</td>
                                                </tr>
                                            </template>
                                        </table>
                                    </a-col>
                                    <a-col :span="4">
                                        <table class="tableborder tabend" border="0" align="center" cellpadding="2" cellspacing="1" width="100%" style="border-collapse: separate;">
                                            <tr>
                                                <th colspan="3">当前值</th>
                                            </tr>
                                            <tr>
                                                <th>注单限额</th>
                                                <th>单期限额</th>
                                            </tr>
                                            <template v-for="kind in lottery.kinds">
                                                <tr :key="kind.kindId">
                                                    <td class="forumrowhighlight">
                                                        {{kind.maxBet}}
                                                    </td>
                                                    <td class="forumrowhighlight">
                                                        {{kind.maxPeriod}}
                                                    </td>
                                                </tr>
                                            </template>
                                        </table>
                                    </a-col>
                                </template>
                                <template v-else>
                                    <template v-if="hasOrder">
                                        <a-col :span="8">
                                            <table class="tableborder odds" border="0" align="center" cellpadding="2" cellspacing="1" width="100%" style="border-collapse: separate;">
                                                <tr>
                                                    <th colspan="5">当前值</th>
                                                </tr>
                                                <tr>
                                                    <th width="80">种类</th>
                                                    <th v-if="markets.A">A盘%</th>
                                                    <th v-if="markets.B">B盘%</th>
                                                    <th v-if="markets.C">C盘%</th>
                                                    <th v-if="markets.D">D盘%</th>
                                                </tr>
                                                <template v-for="kind in lottery.kinds">
                                                    <tr :key="kind.kindId">
                                                        <td class="forumrow">{{kind.kindName}}</td>
                                                        <td v-if="markets.A" class="forumrowhighlight">{{kind.regressA}} </td>
                                                        <td v-if="markets.B" class="forumrowhighlight">{{kind.regressB}} </td>
                                                        <td v-if="markets.C" class="forumrowhighlight">{{kind.regressC}} </td>
                                                        <td v-if="markets.D" class="forumrowhighlight">{{kind.regressD}} </td>
                                                    </tr>
                                                </template>
                                            </table>
                                        </a-col>
                                        <a-col :span="12">
                                            <table class="tableborder gree" border="0" align="center" cellpadding="2" cellspacing="1" width="100%" style="border-collapse: separate;">
                                                <tr>
                                                    <th colspan="5">预修改值*隔天生效</th>
                                                </tr>
                                                <tr>
                                                    <th v-if="markets.A">A盘%</th>
                                                    <th v-if="markets.B">B盘%</th>
                                                    <th v-if="markets.C">C盘%</th>
                                                    <th v-if="markets.D">D盘%</th>
                                                </tr>
                                                <template v-for="kind in lottery.kinds">
                                                    <tr :key="kind.kindId">
                                                        <td v-if="markets.A" class="forumrowhighlight">
                                                            <a-input v-model.number="kind.tmrRegressA" size="small" style="width: 55px" :class="kind.isChangedA?'oddsselected':''" @change="regressCheckField(kind,lottery.lotteryId,kind.kindId,'A')" />
                                                            ≤{{kind.baseRegressA}}
                                                        </td>
                                                        <td v-if="markets.B" class="forumrowhighlight">
                                                            <a-input v-model.number="kind.tmrRegressB" size="small" style="width: 55px" :class="kind.isChangedB?'oddsselected':''" @change="regressCheckField(kind,lottery.lotteryId,kind.kindId,'B')" />
                                                            ≤{{kind.baseRegressB}}
                                                        </td>
                                                        <td v-if="markets.C" class="forumrowhighlight">
                                                            <a-input v-model.number="kind.tmrRegressC" size="small" style="width: 55px" :class="kind.isChangedC?'oddsselected':''" @change="regressCheckField(kind,lottery.lotteryId,kind.kindId,'C')" />
                                                            ≤{{kind.baseRegressC}}
                                                        </td>
                                                        <td v-if="markets.D" class="forumrowhighlight">
                                                            <a-input v-model.number="kind.tmrRegressD" size="small" style="width: 55px" :class="kind.isChangedD?'oddsselected':''" @change="regressCheckField(kind,lottery.lotteryId,kind.kindId,'D')" />
                                                            ≤{{kind.baseRegressD}}
                                                        </td>
                                                    </tr>
                                                </template>
                                            </table>
                                        </a-col>
                                    </template>
                                    <template v-else>
                                        <a-col :span="12">
                                            <table class="tableborder odds" border="0" align="center" cellpadding="2" cellspacing="1" width="100%" style="border-collapse: separate;">
                                                <tr>
                                                    <th colspan="6">当前值</th>
                                                </tr>
                                                <tr>
                                                    <th width="80">种类</th>
                                                    <th v-if="markets.A">A盘%</th>
                                                    <th v-if="markets.B">B盘%</th>
                                                    <th v-if="markets.C">C盘%</th>
                                                    <th v-if="markets.D">D盘%</th>
                                                </tr>
                                                <template v-for="kind in lottery.kinds">
                                                    <tr :key="kind.kindId">
                                                        <td class="forumrow">{{kind.kindName}}</td>
                                                        <td v-if="markets.A" class="forumrowhighlight">
                                                            <a-input v-model.number="kind.regressA" size="small" style="width: 55px" :class="kind.isChangedA?'oddsselected':''" @change="regressCheckField(kind,lottery.lotteryId,kind.kindId,'A')" />
                                                            ≤{{kind.baseRegressA}}
                                                        </td>
                                                        <td v-if="markets.B" class="forumrowhighlight">
                                                            <a-input v-model.number="kind.regressB" size="small" style="width: 55px" :class="kind.isChangedB?'oddsselected':''" @change="regressCheckField(kind,lottery.lotteryId,kind.kindId,'B')" />
                                                            ≤{{kind.baseRegressB}}
                                                        </td>
                                                        <td v-if="markets.C" class="forumrowhighlight">
                                                            <a-input v-model.number="kind.regressC" size="small" style="width: 55px" :class="kind.isChangedC?'oddsselected':''" @change="regressCheckField(kind,lottery.lotteryId,kind.kindId,'C')" />
                                                            ≤{{kind.baseRegressC}}
                                                        </td>
                                                        <td v-if="markets.D" class="forumrowhighlight">
                                                            <a-input v-model.number="kind.regressD" size="small" style="width: 55px" :class="kind.isChangedD?'oddsselected':''" @change="regressCheckField(kind,lottery.lotteryId,kind.kindId,'D')" />
                                                            ≤{{kind.baseRegressD}}
                                                        </td>
                                                    </tr>
                                                </template>
                                            </table>
                                        </a-col>
                                        <a-col :span="8">
                                            <table class="tableborder gree" border="0" align="center" cellpadding="2" cellspacing="1" width="100%" style="border-collapse: separate;">
                                                <tr>
                                                    <th colspan="4">预修改值*隔天生效</th>
                                                </tr>
                                                <tr>
                                                    <th v-if="markets.A">A盘%</th>
                                                    <th v-if="markets.B">B盘%</th>
                                                    <th v-if="markets.C">C盘%</th>
                                                    <th v-if="markets.D">D盘%</th>
                                                </tr>
                                                <template v-for="kind in lottery.kinds">
                                                    <tr :key="kind.kindId">
                                                        <td v-if="markets.A" class="forumrowhighlight">{{kind.tmrRegressA}}</td>
                                                        <td v-if="markets.B" class="forumrowhighlight">{{kind.tmrRegressB}} </td>
                                                        <td v-if="markets.C" class="forumrowhighlight">{{kind.tmrRegressC}} </td>
                                                        <td v-if="markets.D" class="forumrowhighlight">{{kind.tmrRegressD}}</td>
                                                    </tr>
                                                </template>
                                            </table>
                                        </a-col>
                                    </template>
                                    <a-col :span="4">
                                        <table class="tableborder tabend" border="0" align="center" cellpadding="2" cellspacing="1" width="100%" style="border-collapse: separate;">
                                            <tr>
                                                <th colspan="3">当前值</th>
                                            </tr>
                                            <tr>

                                                <th>注单限额</th>
                                                <th>单期限额</th>
                                            </tr>
                                            <template v-for="kind in lottery.kinds">
                                                <tr :key="kind.kindId">
                                                    <td class="forumrowhighlight">
                                                        <a-input size="small" v-model.number="kind.maxBet" style="width: 55px" :class="kind.isChangedMB?'oddsselected':''" @change="limitCheckField(kind,lottery.lotteryId,kind.kindId,'maxBet','baseMaxBet')" />
                                                    </td>
                                                    <td class="forumrowhighlight">
                                                        <a-input size="small" v-model.number="kind.maxPeriod" style="width: 55px" :class="kind.isChangedMP?'oddsselected':''" @change="limitCheckField(kind,lottery.lotteryId,kind.kindId,'maxPeriod','baseMaxPeriod')" />
                                                    </td>
                                                </tr>
                                            </template>
                                        </table>
                                    </a-col>
                                </template>
                            </a-row>
                        </template>

                    </a-col>
                     </template>
                </a-row>
                <div class="mt5" style="text-align: center">
                    <a-button type="default" icon="close" size="small" class="mlr10" @click="onClose">
                        关闭
                    </a-button>
                    <a-button type="primary" icon="save" size="small" class="mlr10" @click="saveZhuanRegress">
                        保存
                    </a-button>
                </div>
            </a-spin>
        </div>
        <!--        <div class="opnewinright">-->
        <!--            <a-button type="primary" size="small" @click="onClose">-->
        <!--                关闭-->
        <!--            </a-button>-->
        <!--        </div>-->
    </a-modal>
     <regress-limit-log :regressLimitLogShow.sync="regressLimitLogShow" :editUserId.sync="editUserId" :editUsername.sync="editUsername"></regress-limit-log>
     </div>
</template>

<script>
import to from "await-to-js";
import BigNumber from "bignumber.js";
import RegressLimitLog from "./regress-limit-log";
export default {
    components: { RegressLimitLog },
    name: "user-zhuan-regress",
    props: {
        userZhuanRegressShow: {
            type: Boolean,
        },
        editUserId: null,
        editUsername: null,
    },
    data() {
        return {
            regressLimitLogShow:false,
            maskClosable: false,
            spinning: false,
            groups: [],
            times: 0,
            timer: null,
            mapRegresss: null,
            hasOrder: false,
            diff: null,
            markets: {},
            quickSetting: [],
            isJustLook: this.$store.state.user.info.userLevel == 1,
        };
    },
    mounted() {
        //console.log("editUserId:", this.editUserId);
        this.requestZhuanRegress();
    },
    methods: {
        onClose() {
            this.$emit("update:userZhuanRegressShow", false);
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
        regressCheckField(regress, lotteryId, kindId, market) {
            clearTimeout(this.timer);
            this.timer = setTimeout(() => {
                let key = (this.hasOrder ? "tmrRegress" : "regress") + market;
                let baseKey = "baseRegress" + market;
                let changeKey = "isChanged" + market;
                //let originVal = this.mapRegresss[lotteryId][kindId][key];
                let originRegress =
                    this.mapRegresss[lotteryId] &&
                    this.mapRegresss[lotteryId][kindId];
                let originVal = originRegress != null ? originRegress[key] : 0;
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

                regress.isDirtyR =
                    regress.isChangedA ||
                    regress.isChangedB ||
                    regress.isChangedC ||
                    regress.isChangedD;
            }, 500);
        },
        limitCheckField(regress, lotteryId, kindId, key, baseKey) {
            clearTimeout(this.timer);
            this.timer = setTimeout(() => {
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

                regress.isDirtyL =
                    regress.isChangedMB || regress.isChangedMP ? true : false;
            }, 500);
        },
        qsSetting(qs) {
            ["A", "B", "C", "D"].forEach((market) => {
                let key = (this.hasOrder ? "tmrRegress" : "regress") + market;
                let baseKey = "baseRegress" + market;
                let changeKey = "isChanged" + market;
                let changeVal = qs["regress" + market];
                let baseVal = qs[baseKey];
                let logicVal =
                    changeVal > baseVal
                        ? baseVal
                        : changeVal < 0
                        ? 0
                        : changeVal;
                qs[key] = logicVal;
                this.groups.forEach((group) => {
                    group.lotterys.forEach((lottery) => {
                        let { lotteryId, kinds } = lottery;
                        kinds.forEach((kind) => {
                            let { quickSettingId, kindId } = kind;
                            if (quickSettingId == qs.quickSettingId) {
                                let newVal = Math.min(logicVal, kind[baseKey]);
                                kind[key] = newVal;
                                let originVal = this.mapRegresss[lotteryId][
                                    kindId
                                ][key];
                                kind[changeKey] = newVal != originVal;
                            }
                        });
                    });
                });
            });
            [
                { key: "maxBet", baseKey: "baseMaxBet" },
                { key: "maxPeriod", baseKey: "baseMaxPeriod" },
            ].forEach(({ key, baseKey }) => {
                let changeVal = qs[key];
                let baseVal = qs[baseKey];
                let changeKey = "isChanged" + (key == "maxBet" ? "MB" : "MP");
                let logicVal =
                    changeVal > baseVal
                        ? baseVal
                        : changeVal < 0
                        ? 0
                        : changeVal;
                qs[key] = logicVal;
                this.groups.forEach((group) => {
                    group.lotterys.forEach((lottery) => {
                        let { lotteryId, kinds } = lottery;
                        kinds.forEach((kind) => {
                            let { quickSettingId, kindId } = kind;
                            if (quickSettingId == qs.quickSettingId) {
                                let newVal = Math.min(logicVal, kind[baseKey]);
                                kind[key] = newVal;
                                let originVal = this.mapRegresss[lotteryId][
                                    kindId
                                ][key];
                                kind[changeKey] = newVal != originVal;
                            }
                        });
                    });
                });
            });
        },
        quiskSetting() {
            if (this.diff == null || this.diff < 0) {
                return;
            }
            //console.log("diff2", this.diff);
            ["A", "B", "C", "D"].forEach((market) => {
                let key = (this.hasOrder ? "tmrRegress" : "regress") + market;
                let baseKey = "baseRegress" + market;
                let changeKey = "isChanged" + market;
                let logic = Math.abs(this.diff < 0 ? 0 : this.diff);
                //console.log("logic:", logic);
                this.groups.forEach((group) => {
                    group.lotterys.forEach((lottery) => {
                        let { lotteryId, kinds } = lottery;
                        kinds.forEach((kind) => {
                            let { kindId } = kind;
                            let baseVal = kind[baseKey];
                            let logicVal = new BigNumber(baseVal).minus(logic);
                            logicVal = Math.floor(logicVal * 1000) / 1000;
                            let newVal = Math.max(
                                Math.min(logicVal, baseVal),
                                0
                            );
                            kind[key] = newVal;
                            let originVal = this.mapRegresss[lotteryId][kindId][
                                key
                            ];
                            kind[changeKey] = newVal != originVal;
                        });
                    });
                });
            });
        },
        async requestZhuanRegress() {
            if (this.editUserId == 0 || !this.userZhuanRegressShow) {
                return;
            }
            this.spinning = true;
            let [err, res] = await to(
                this.$api.ctrl.getZhuanRegress({ userId: this.editUserId })
            );
            if (err || !res.success) {
                this.spinning = false;
                this.$utils.handleThen(res, this);
                return;
            }
            let {
                groups,
                kinds: mapKinds,
                lotterys: mapLotterys,
                userKinds: mapUserKinds,
                hasOrder,
                markets,
                quickSetting,
            } = res.data;
            this.hasOrder = hasOrder;
            this.markets = markets;
            this.quickSetting = quickSetting;
            this.mapRegresss = mapUserKinds;
            groups.forEach((group) => {
                let groupId = group.groupId;
                let lotterys = mapLotterys[groupId] || [];
                lotterys.forEach((lottery) => {
                    let lotteryId = lottery.lotteryId;
                    let kinds = JSON.parse(JSON.stringify(mapKinds[groupId]));
                    kinds.forEach((kind) => {
                        let kindId = kind.kindId;
                        let regress = mapUserKinds[lotteryId][kindId];
                        Object.assign(kind, regress);
                        kind.isChangedA = false;
                        kind.isChangedB = false;
                        kind.isChangedC = false;
                        kind.isChangedD = false;
                        kind.isChangedMB = false;
                        kind.isChangedMP = false;
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

            let qsArr = {};
            this.quickSetting.forEach((qs) => {
                qsArr[qs.quickSettingId] = qs;
            });
            groups.forEach((group) => {
                let groupId = group.groupId;
                let lotterys = mapLotterys[groupId] || [];
                lotterys.forEach((lottery) => {
                    let lotteryId = lottery.lotteryId;
                    let kinds = JSON.parse(JSON.stringify(mapKinds[groupId]));
                    kinds.forEach((kind) => {
                        let { kindId, quickSettingId } = kind;
                        let rg = mapUserKinds[lotteryId][kindId];
                        let qs = qsArr[quickSettingId];
                        qs.baseRegressA = Math.max(
                            rg.baseRegressA,
                            qs.baseRegressA
                        );
                        qs.baseRegressB = Math.max(
                            rg.baseRegressB,
                            qs.baseRegressB
                        );
                        qs.baseRegressC = Math.max(
                            rg.baseRegressC,
                            qs.baseRegressC
                        );
                        qs.baseRegressD = Math.max(
                            rg.baseRegressD,
                            qs.baseRegressD
                        );
                        qs.baseMaxBet = Math.max(rg.baseMaxBet, qs.baseMaxBet);
                        qs.baseMaxPeriod = Math.max(
                            rg.baseMaxPeriod,
                            qs.baseMaxPeriod
                        );
                    });
                });
            });
            this.quickSetting.forEach((qs) => {
                qs.regressA = qs.baseRegressA;
                qs.regressB = qs.baseRegressB;
                qs.regressC = qs.baseRegressC;
                qs.regressD = qs.baseRegressD;
                qs.maxBet = qs.baseMaxBet;
                qs.maxPeriod = qs.baseMaxPeriod;
            });

            this.groups = groups;
            this.spinning = false;
        },
        async saveZhuanRegress() {
            let params = {
                userId: this.editUserId,
                lotteryIds: [],
                regresss: [],
                tmrRegresss: [],
                oddss: [],
                limits: [],
                fulloddss: [],
            };
            this.groups.forEach((group) => {
                group.lotterys.forEach((lottery) => {
                    let { lotteryId, kinds } = lottery;
                    kinds.forEach((regress) => {
                        let {
                            kindId,
                            isChangedA,
                            isChangedB,
                            isChangedC,
                            isChangedD,
                            isChangedMB,
                            isChangedMP,
                        } = regress;
                        if (
                            isChangedA ||
                            isChangedB ||
                            isChangedC ||
                            isChangedD
                        ) {
                            let {
                                regressA,
                                regressB,
                                regressC,
                                regressD,
                                baseRegressA,
                                baseRegressB,
                                baseRegressC,
                                baseRegressD,
                                tmrRegressA,
                                tmrRegressB,
                                tmrRegressC,
                                tmrRegressD,
                            } = regress;
                            if (this.hasOrder) {
                                params.tmrRegresss.push({
                                    kindId,
                                    lotteryId,
                                    regressA: (
                                        baseRegressA - tmrRegressA
                                    ).toFixed(3),
                                    regressB: (
                                        baseRegressB - tmrRegressB
                                    ).toFixed(3),
                                    regressC: (
                                        baseRegressC - tmrRegressC
                                    ).toFixed(3),
                                    regressD: (
                                        baseRegressD - tmrRegressD
                                    ).toFixed(3),
                                });
                            } else {
                                params.regresss.push({
                                    kindId,
                                    lotteryId,
                                    regressA: (baseRegressA - regressA).toFixed(
                                        3
                                    ),
                                    regressB: (baseRegressB - regressB).toFixed(
                                        3
                                    ),
                                    regressC: (baseRegressC - regressC).toFixed(
                                        3
                                    ),
                                    regressD: (baseRegressD - regressD).toFixed(
                                        3
                                    ),
                                });
                            }
                        }

                        if (isChangedMB || isChangedMP) {
                            let { maxBet, maxPeriod } = regress;
                            params.limits.push({
                                kindId,
                                lotteryId,
                                maxBet,
                                maxPeriod,
                            });
                        }
                    });
                });
            });

            this.spinning = true;
            let [err, res] = await to(
                this.$api.ctrl.updateZhuanRegress(params)
            );
            this.$utils.handleThen(res, this);
            this.spinning = false;
            if (err || !res.success) {
                return;
            }
            await this.requestZhuanRegress();
        },
    },
    watch: {},
};
</script>

<style scoped>
</style>
