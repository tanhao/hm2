<!--修改标准一级才有-->
<template>
<div>
    <a-modal :title="'修改退水限注：'+editUsername" :width="1030" :maskClosable="maskClosable" dialogClass="tk" :visible="agent1RegressLimitShow" :body-style="{ paddingBottom: '0px'}" @cancel="onClose">
        <template slot="footer">
            <a-button key="back" @click="onClose">
                取消
            </a-button>
        </template>
        <div class="buhuo ts allhigit">
            <a-spin :spinning="spinning">
                <a-row :gutter="8" style="margin-bottom:4px">
                    <template v-if="!hasLower&&!hasCheckPwd">
                        <a-col v-if="!isJustLook" :span="18" class="df">
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
                        <a-col :span="6" style="float: right;display: table;width: auto">
                            <!--
                            <a-button type="primary" icon="edit" size="small" @click="regressLimitLogShow=true">
                                赚水日志
                            </a-button>
                            -->
                            <a-button type="primary" icon="edit" size="small" @click="regressLimitLogShow=true">
                                限注日志
                            </a-button>
                        </a-col>
                    
                </a-row>
                <template v-if="!hasLower&&hasCheckPwd">
                    <a-row :gutter="16">
                        <a-col :span="24">
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
                            <a-col :span="24"  :key="group.groupId" v-if="group.lotterys && group.lotterys.length>0">
                                <a-divider orientation="left">
                                    {{group.groupName}}
                                </a-divider>
                                <template v-for="lottery in group.lotterys">
                                    <a-row :gutter="5" :key="lottery.lotteryId">
                                        <a-col :span="24">
                                            <div class="demotit">{{lottery.lotteryName}}</div>
                                        </a-col>
                                        <a-col :span="9">
                                            <table class="tableborder odds" border="0" align="center" cellpadding="2" cellspacing="1" width="100%" style="border-collapse: separate;">
                                                <template v-if="hasOrder">
                                                    <tr>
                                                        <th colspan="5">当前值</th>
                                                    </tr>
                                                    <tr>
                                                        <th width="150">种类</th>
                                                        <th v-if="markets.A">A盘(%)</th>
                                                        <th v-if="markets.B">B盘(%)</th>
                                                        <th v-if="markets.C">C盘(%)</th>
                                                        <th v-if="markets.D">D盘(%)</th>
                                                    </tr>
                                                    <template v-for="kind in lottery.kinds">
                                                        <tr :key="kind.kindId">
                                                            <td class="forumrow">{{kind.kindName}}</td>
                                                            <td v-if="markets.A" class="forumrowhighlight">
                                                                {{kind.regressA}}
                                                            </td>
                                                            <td v-if="markets.B" class="forumrowhighlight">
                                                                {{kind.regressB}}
                                                            </td>
                                                            <td v-if="markets.C" class="forumrowhighlight">
                                                                {{kind.regressC}}
                                                            </td>
                                                            <td v-if="markets.D" class="forumrowhighlight">
                                                                {{kind.regressD}}
                                                            </td>
                                                        </tr>
                                                    </template>
                                                </template>
                                                <template v-else>
                                                    <tr>
                                                        <th colspan="6">当前值</th>
                                                    </tr>
                                                    <tr>
                                                        <th width="150">种类</th>
                                                        <th v-if="markets.A">A盘(%)</th>
                                                        <th v-if="markets.B">B盘(%)</th>
                                                        <th v-if="markets.C">C盘(%)</th>
                                                        <th v-if="markets.D">D盘(%)</th>
                                                    </tr>
                                                    <template v-for="kind in lottery.kinds">
                                                        <tr :key="kind.kindId">
                                                            <td class="forumrow">{{kind.kindName}}</td>
                                                            <template v-if="markets.A">
                                                                <td class="forumrowhighlight">
                                                                    <a-input size="small" v-model.number="kind.regressA" style="width: 55px" :class="kind.isChangedA?'oddsselected':''" @change="regressCheckField(kind,lottery.lotteryId,kind.kindId,'A')" />
                                                                </td>
                                                            </template>
                                                            <template v-if="markets.B">
                                                                <td class="forumrowhighlight">
                                                                    <a-input size="small" v-model.number="kind.regressB" style="width: 55px" :class="kind.isChangedB?'oddsselected':''" @change="regressCheckField(kind,lottery.lotteryId,kind.kindId,'B')" />
                                                                </td>
                                                            </template>
                                                            <template v-if="markets.C">
                                                                <td class="forumrowhighlight">
                                                                    <a-input size="small" v-model.number="kind.regressC" style="width: 55px" :class="kind.isChangedC?'oddsselected':''" @change="regressCheckField(kind,lottery.lotteryId,kind.kindId,'C')" />
                                                                </td>
                                                            </template>
                                                            <template v-if="markets.D">
                                                                <td class="forumrowhighlight">
                                                                    <a-input size="small" v-model.number="kind.regressD" style="width: 55px" :class="kind.isChangedD?'oddsselected':''" @change="regressCheckField(kind,lottery.lotteryId,kind.kindId,'D')" />
                                                                </td>
                                                            </template>
                                                        </tr>
                                                    </template>
                                                </template>
                                            </table>
                                        </a-col>
                                        <a-col :span="9">
                                            <table class="tableborder gree" border="0" align="center" cellpadding="2" cellspacing="1" width="100%" style="border-collapse: separate;">
                                                <template v-if="hasOrder">
                                                    <tr>
                                                        <th colspan="5">预修改值*隔天生效</th>
                                                    </tr>
                                                    <tr>
                                                        <th v-if="markets.A">A盘(%)</th>
                                                        <th v-if="markets.B">B盘(%)</th>
                                                        <th v-if="markets.C">C盘(%)</th>
                                                        <th v-if="markets.D">D盘(%)</th>
                                                    </tr>
                                                    <template v-for="kind in lottery.kinds">
                                                        <tr :key="kind.kindId">
                                                            <template v-if="markets.A">
                                                                <td class="forumrowhighlight">
                                                                    <a-input size="small" v-model.number="kind.tmrRegressA" style="width: 55px" :class="kind.isChangedA?'oddsselected':''" @change="regressCheckField(kind,lottery.lotteryId,kind.kindId,'A')" />
                                                                    ≤{{kind.baseRegressA}}
                                                                </td>
                                                            </template>
                                                            <template v-if="markets.B">
                                                                <td class="forumrowhighlight">
                                                                    <a-input size="small" v-model.number="kind.tmrRegressB" style="width: 55px" :class="kind.isChangedB?'oddsselected':''" @change="regressCheckField(kind,lottery.lotteryId,kind.kindId,'B')" />
                                                                    ≤{{kind.baseRegressB}}
                                                                </td>
                                                            </template>
                                                            <template v-if="markets.C">
                                                                <td class="forumrowhighlight">
                                                                    <a-input size="small" v-model.number="kind.tmrRegressC" style="width: 55px" :class="kind.isChangedC?'oddsselected':''" @change="regressCheckField(kind,lottery.lotteryId,kind.kindId,'C')" />
                                                                    ≤{{kind.baseRegressC}}
                                                                </td>
                                                            </template>
                                                            <template v-if="markets.D">
                                                                <td class="forumrowhighlight">
                                                                    <a-input size="small" v-model.number="kind.tmrRegressD" style="width: 55px" :class="kind.isChangedD?'oddsselected':''" @change="regressCheckField(kind,lottery.lotteryId,kind.kindId,'D')" />
                                                                    ≤{{kind.baseRegressD}}
                                                                </td>
                                                            </template>

                                                        </tr>
                                                    </template>
                                                </template>
                                                <template v-else>
                                                    <tr>
                                                        <th colspan="4">预修改值*隔天生效</th>
                                                    </tr>
                                                    <tr>
                                                        <th v-if="markets.A">A盘(%)</th>
                                                        <th v-if="markets.B">B盘(%)</th>
                                                        <th v-if="markets.C">C盘(%)</th>
                                                        <th v-if="markets.D">D盘(%)</th>
                                                    </tr>
                                                    <template v-for="kind in lottery.kinds">
                                                        <tr :key="kind.kindId">
                                                            <td class="forumrowhighlight">
                                                                {{kind.tmrRegressA}}
                                                            </td>
                                                            <td class="forumrowhighlight">
                                                                {{kind.tmrRegressB}}
                                                            </td>
                                                            <td class="forumrowhighlight">
                                                                {{kind.tmrRegressC}}
                                                            </td>
                                                            <td class="forumrowhighlight">
                                                                {{kind.tmrRegressD}}
                                                            </td>
                                                        </tr>
                                                    </template>
                                                </template>
                                            </table>
                                        </a-col>
                                        <a-col :span="6">
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
                                    </a-row>

                                </template>

                            </a-col>
                        </template>
                    </a-row>
                    <div class="mt16" style="text-align: center">
                        <a-button type="primary" icon="save" size="small" class="mlr10" @click="saveRegressLimit">
                            保存
                        </a-button>
                    </div>
                </template>
                <template v-else>
                    <a-row :gutter="16">
                        <template v-for="group in groups">
                            <a-col :span="24"  :key="group.groupId" v-if="group.lotterys && group.lotterys.length>0">
                                <a-divider orientation="left">
                                    {{group.groupName}}
                                </a-divider>
                                <template v-for="lottery in group.lotterys">
                                    <a-row :gutter="5" :key="lottery.lotteryId">
                                        <a-col :span="24">
                                            <div class="demotit">{{lottery.lotteryName}}</div>
                                        </a-col>
                                        <a-col :span="10">
                                            <table class="tableborder odds" border="0" align="center" cellpadding="2" cellspacing="1" width="100%" style="border-collapse: separate;">
                                                <tr>
                                                    <th colspan="5">当前值</th>
                                                </tr>
                                                <tr>
                                                    <th width="150">种类</th>
                                                    <th v-if="markets.A">A盘(%)</th>
                                                    <th v-if="markets.B">B盘(%)</th>
                                                    <th v-if="markets.C">C盘(%)</th>
                                                    <th v-if="markets.D">D盘(%)</th>
                                                </tr>
                                                <template v-for="kind in lottery.kinds">
                                                    <tr :key="kind.kindId">
                                                        <td class="forumrow">{{kind.kindName}}</td>
                                                        <td v-if="markets.A" class="forumrowhighlight">
                                                            {{kind.regressA}}
                                                        </td>
                                                        <td v-if="markets.B" class="forumrowhighlight">
                                                            {{kind.regressB}}
                                                        </td>
                                                        <td v-if="markets.C" class="forumrowhighlight">
                                                            {{kind.regressC}}
                                                        </td>
                                                        <td v-if="markets.D" class="forumrowhighlight">
                                                            {{kind.regressD}}
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
                                                    <th v-if="markets.A">A盘(%)</th>
                                                    <th v-if="markets.B">B盘(%)</th>
                                                    <th v-if="markets.C">C盘(%)</th>
                                                    <th v-if="markets.D">D盘(%)</th>
                                                </tr>
                                                <template v-for="kind in lottery.kinds">
                                                    <tr :key="kind.kindId">
                                                        <td v-if="markets.A" class="forumrowhighlight">
                                                            {{kind.tmrRegressA}}
                                                        </td>
                                                        <td v-if="markets.B" class="forumrowhighlight">
                                                            {{kind.tmrRegressB}}
                                                        </td>
                                                        <td v-if="markets.C" class="forumrowhighlight">
                                                            {{kind.tmrRegressC}}
                                                        </td>
                                                        <td v-if="markets.D" class="forumrowhighlight">
                                                            {{kind.tmrRegressD}}
                                                        </td>
                                                    </tr>
                                                </template>

                                            </table>
                                        </a-col>
                                        <a-col :span="6">
                                            <table class="tableborder tabend" border="0" align="center" cellpadding="2" cellspacing="1" width="100%" style="border-collapse: separate;">
                                                <tr>
                                                    <th colspan="2">当前值</th>
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
                                    </a-row>

                                </template>
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
import loginVue from "../../login/login.vue";
import RegressLimitLog from "./regress-limit-log";
export default {
    components: { RegressLimitLog },
    name: "user-agent1-regress-limit",
    props: {
        agent1RegressLimitShow: {
            type: Boolean,
        },
        editUserId: null,
        editUsername: null,
    },
    data() {
        return {
            plus,
            minus,
            maskClosable: false,
            safePwd: null,
            times: 0,
            timer: null,
            spinning: false,
            mapOddss: null,
            mapRegresss: null,
            markets: {},
            groups: [],
            hasLower: false,
            hasOrder: false,
            hasCheckPwd: false,
            quickSetting: [],

            isJustLook: this.$store.state.user.info.userLevel == 1,

            regressLimitLogShow:false,
        };
    },
    created() {
        //console.log("重新加载啦");
    },
    mounted() {
        //console.log("editUserId:", this.editUserId);
        this.requestORLF();
    },
    methods: {
        onClose() {
            this.$emit("update:agent1RegressLimitShow", false);
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
        regressCheckField(regress, lotteryId, kindId, market) {
            clearTimeout(this.timer);
            this.timer = setTimeout(() => {
                let key = (this.hasOrder ? "tmrRegress" : "regress") + market;
                let baseKey = "baseRegress" + market;
                let changeKey = "isChanged" + market;
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
        async requestORLF() {
            if (this.editUserId == 0 || !this.agent1RegressLimitShow) {
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
                hasOrder,
                quickSetting,
            } = res.data;
            this.hasOrder = hasOrder;
            this.quickSetting = quickSetting;
            this.mapOddss = mapOddss;
            this.mapRegresss = mapRegresss;
            this.markets = markets;
            groups.forEach((group) => {
                let groupId = group.groupId;
                let lotterys = mapLotterys[groupId] ||[];
                lotterys.forEach((lottery) => {
                    let lotteryId = lottery.lotteryId;
                    let kinds = JSON.parse(JSON.stringify(mapKinds[groupId]));
                    kinds.forEach((kind) => {
                        let kindId = kind.kindId;
                        let regress = mapRegresss[lotteryId][kindId];
                        Object.assign(kind, regress);
                        /*
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
                        */
                        kind.isChangedA = false;
                        kind.isChangedB = false;
                        kind.isChangedC = false;
                        kind.isChangedD = false;
                        kind.isChangedMB = false;
                        kind.isChangedMP = false;
                        //kind.categorys = categorys;
                    });
                    lottery.isChecked = false;
                    lottery.kinds = kinds;
                });
                group.isChecked = false;
                group.lotterys = lotterys;
                if(lotterys.length>0){
                    lotterys[0].isChecked = true;
                    group.lotteryId = lotterys[0].lotteryId;
                }
    
                
            });

            let qsArr = {};
            this.quickSetting.forEach((qs) => {
                qsArr[qs.quickSettingId] = qs;
            });
            groups.forEach((group) => {
                let groupId = group.groupId;
                let lotterys = mapLotterys[groupId] ||[];
                lotterys.forEach((lottery) => {
                    let lotteryId = lottery.lotteryId;
                    let kinds = JSON.parse(JSON.stringify(mapKinds[groupId]));
                    kinds.forEach((kind) => {
                        let { kindId, quickSettingId } = kind;
                        let rg = mapRegresss[lotteryId][kindId];
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
        async saveRegressLimit() {
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
                                tmrRegressA,
                                tmrRegressB,
                                tmrRegressC,
                                tmrRegressD,
                            } = regress;
                            if (this.hasOrder) {
                                params.tmrRegresss.push({
                                    kindId,
                                    lotteryId,
                                    regressA: tmrRegressA,
                                    regressB: tmrRegressB,
                                    regressC: tmrRegressC,
                                    regressD: tmrRegressD,
                                });
                            } else {
                                params.regresss.push({
                                    kindId,
                                    lotteryId,
                                    regressA,
                                    regressB,
                                    regressC,
                                    regressD,
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
                this.$api.ctrl.udpateAgent1RegressLimit(params)
            );
            this.$utils.handleThen(res, this);
            this.spinning = false;
            if (err || !res.success) {
                return;
            }
            await this.requestORLF();
        },
    },
    watch: {},
};
</script>

<style scoped>
</style>
