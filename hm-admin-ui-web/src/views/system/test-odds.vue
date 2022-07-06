<template>
    <div class="buhuo allhigit">
        <a-row :gutter="8">
            <template>
                <a-col :span="14" class="df">
                    <span class="maintxt mlr5">安保密码:</span>
                    <a-input-password size="small" placeholder="请输入安保密码" style="width: 150px"/>
                    <div class="pl10">
                        <a-button type="primary" icon="key" size="small">
                            确定
                        </a-button>
                    </div>
                    <div class="red dstable mlr10 fontwe">需要修改时，先输入安保密码</div>
                </a-col>
                <a-col :span="8" style="float: right;display: table;width: auto;">
                    <template>
                        賺取賠差：
                        <a-input size="small" style="width: 120px"/>
                        <a-button type="primary" icon="setting" size="small" @click="showsetting" class="mlr5">
                            复制退水赔率
                        </a-button>
                        <a-button type="primary" icon="setting" size="small" class="mlr5">
                            快速设置
                        </a-button>
                    </template>
                    <a-button type="primary" icon="edit" size="small">
                        修改日志
                    </a-button>
                </a-col>


            </template>
        </a-row>


        <a-row :gutter="2" class="mt16">
            <a-col :span="24">
                <table class="tableborder odds" border="0" align="center" cellpadding="2" cellspacing="1">
                    <tbody>
                    <tr>
                        <th colspan="2" rowspan="2">种类</th>
                        <th colspan="3">A盘</th>
                        <th colspan="2">B盘</th>
                        <th colspan="2">C盘</th>
                        <th colspan="2">D盘</th>
                        <th rowspan="2">赚赔</th>
                        <th colspan="4">赚赔后</th>
                        <th rowspan="2">注单限注</th>
                        <th rowspan="2">单期限注</th>
                    </tr>
                    <tr>
                        <th>满盘值</th>
                        <th>上级赔率</th>
                        <th>退水</th>
                        <th>上级赔率</th>
                        <th>退水</th>
                        <th>上级赔率</th>
                        <th>退水</th>
                        <th>上级赔率</th>
                        <th>退水</th>
                        <th>A盘</th>
                        <th>B盘</th>
                        <th>C盘</th>
                        <th>D盘</th>
                    </tr>
                    <tr v-for="i in ['1-10车号','两面','冠亚和大双','冠亚和小单']">
                        <td class="forumrow" colspan="2">{{i}}</td>
                        <td class="forumrowhighlight">1.8</td>
                        <td class="forumrowhighlight">
                            <img class="fl" :src="plus">
                            <a-input size="small"  style="width: 55px" value="9.938" />
                            <img class="fr" :src="minus">
                        </td>
                        <td class="forumrowhighlight">0.599</td>
                        <td class="forumrowhighlight">9.938</td>
                        <td class="forumrowhighlight">0.599</td>
                        <td class="forumrowhighlight">9.938</td>
                        <td class="forumrowhighlight">0.599</td>
                        <td class="forumrowhighlight">9.938</td>
                        <td class="forumrowhighlight">0.599</td>
                        <td class="forumrowhighlight">0.001</td>
                        <td class="forumrowhighlight">9.937</td>
                        <td class="forumrowhighlight">9.937</td>
                        <td class="forumrowhighlight">9.937</td>
                        <td class="forumrowhighlight">9.937</td>
                        <td class="forumrowhighlight">200,000</td>
                        <td class="forumrowhighlight">500,000</td>
                    </tr>
                    <tr>
                        <td class="forumrow" rowspan="5">冠亚和</td>
                        <td class="forumrowhighlight">3,4,18,19</td>
                        <td class="forumrowhighlight">1.8</td>
                        <td class="forumrowhighlight">
                            <img class="fl" :src="plus">
                            <a-input size="small"  style="width: 55px" value="9.938" />
                            <img class="fr" :src="minus">
                        </td>
                        <td class="forumrowhighlight" rowspan="5">0.599</td>
                        <td class="forumrowhighlight">9.938</td>
                        <td class="forumrowhighlight" rowspan="5">0.599</td>
                        <td class="forumrowhighlight">9.938</td>
                        <td class="forumrowhighlight" rowspan="5">0.599</td>
                        <td class="forumrowhighlight">9.938</td>
                        <td class="forumrowhighlight" rowspan="5">0.599</td>
                        <td class="forumrowhighlight">0.001</td>
                        <td class="forumrowhighlight">9.937</td>
                        <td class="forumrowhighlight">9.937</td>
                        <td class="forumrowhighlight">9.937</td>
                        <td class="forumrowhighlight">9.937</td>
                        <td class="forumrowhighlight">200,000</td>
                        <td class="forumrowhighlight">500,000</td>
                    </tr>
                    <tr>
                        <td class="forumrow">5,6,16,17</td>
                        <td class="forumrowhighlight">22</td>
                        <td class="forumrowhighlight">
                            <img class="fl" :src="plus">
                            <a-input size="small"  style="width: 55px" value="9.938" />
                            <img class="fr" :src="minus">
                        </td>
                        <td class="forumrowhighlight">9.938</td>
                        <td class="forumrowhighlight">9.938</td>
                        <td class="forumrowhighlight">9.938</td>
                        <td class="forumrowhighlight">0.001</td>
                        <td class="forumrowhighlight">9.937</td>
                        <td class="forumrowhighlight">9.937</td>
                        <td class="forumrowhighlight">9.937</td>
                        <td class="forumrowhighlight">9.937</td>
                        <td class="forumrowhighlight">200,000</td>
                        <td class="forumrowhighlight">500,00</td>
                    </tr>
                    <tr>
                        <td class="forumrow">7,8,14,15</td>
                        <td class="forumrowhighlight">15</td>
                        <td class="forumrowhighlight">
                            <img class="fl" :src="plus">
                            <a-input size="small"  style="width: 55px" value="9.938" />
                            <img class="fr" :src="minus">
                        </td>
                        <td class="forumrowhighlight">9.938</td>
                        <td class="forumrowhighlight">9.938</td>
                        <td class="forumrowhighlight">9.938</td>
                        <td class="forumrowhighlight">0.001</td>
                        <td class="forumrowhighlight">9.937</td>
                        <td class="forumrowhighlight">9.937</td>
                        <td class="forumrowhighlight">9.937</td>
                        <td class="forumrowhighlight">9.937</td>
                        <td class="forumrowhighlight">200,000</td>
                        <td class="forumrowhighlight">500,000</td>
                    </tr>
                    <tr>
                        <td class="forumrow">9,10,12,13</td>
                        <td class="forumrowhighlight">11.25</td>
                        <td class="forumrowhighlight">
                            <img class="fl" :src="plus">
                            <a-input size="small"  style="width: 55px" value="9.938" />
                            <img class="fr" :src="minus">
                        </td>
                        <td class="forumrowhighlight">9.938</td>
                        <td class="forumrowhighlight">9.938</td>
                        <td class="forumrowhighlight">9.938</td>
                        <td class="forumrowhighlight">0.001</td>
                        <td class="forumrowhighlight">9.937</td>
                        <td class="forumrowhighlight">9.937</td>
                        <td class="forumrowhighlight">9.937</td>
                        <td class="forumrowhighlight">9.937</td>
                        <td class="forumrowhighlight">200,000</td>
                        <td class="forumrowhighlight">500,000</td>
                    </tr>
                    <tr>
                        <td class="forumrow">11</td>
                        <td class="forumrowhighlight">9</td>
                        <td class="forumrowhighlight">
                            <img class="fl" :src="plus">
                            <a-input size="small"  style="width: 55px" value="9.938" />
                            <img class="fr" :src="minus">
                        </td>
                        <td class="forumrowhighlight">9.938</td>
                        <td class="forumrowhighlight">9.938</td>
                        <td class="forumrowhighlight">9.938</td>
                        <td class="forumrowhighlight">0.001</td>
                        <td class="forumrowhighlight">9.937</td>
                        <td class="forumrowhighlight">9.937</td>
                        <td class="forumrowhighlight">9.937</td>
                        <td class="forumrowhighlight">9.937</td>
                        <td class="forumrowhighlight">200,000</td>
                        <td class="forumrowhighlight">500,000</td>
                    </tr>
                    </tbody>
                </table>
                <div class="mt16" style="text-align: center">

                    <a-button type="primary" icon="save" size="small" class="mlr10">
                        保存
                    </a-button>

                </div>

            </a-col>

        </a-row>






    </div>
</template>
<script>
    import { mapGetters } from "vuex";
    import { setInterval, clearInterval, setTimeout, clearTimeout } from "timers";
    import minus from "@/assets/AdminDefaultTheme/Images/minus.png";
    import plus from "@/assets/AdminDefaultTheme/Images/plus.png";
    import to from "await-to-js";
    export default {
        data() {
            return {
                plus,
                minus,
                times: 0,
                timer: null,
                userId: null,
                spinning: false,
                copyDrawer: false,
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
            showsetting() {
                this.copyDrawer = true;
            },
            onClose() {
                this.copyDrawer = false;
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
                clearTimeout(this.timer);
                this.timer = setTimeout(() => {
                    let key = "odds" + market;
                    let baseKey = "baseOdds" + market;
                    let changeKey = "isChanged" + market;
                    let originOdds = this.mapOddss[lotteryId][categoryId];
                    let originVal = originOdds[key];
                    let changeVal = newVal;
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
                    let diff = Number((originVal - logicVal).toFixed(5));
                    //console.log("diff:", diff, originVal, logicVal);
                    if (market == "A") {
                        ["B", "C", "D"].forEach((m) => {
                            key = "odds" + m;
                            baseKey = "baseOdds" + m;
                            changeKey = "isChanged" + m;
                            logicVal = Number(
                                Math.max(
                                    Math.min(originOdds[key] - diff, odds[baseKey]),
                                    1
                                ).toFixed(5)
                            );
                            odds[key] = logicVal;
                            isChanged = originOdds[key] != logicVal;
                            odds[changeKey] = isChanged;
                        });
                    }
                    odds.isDirty = true;
                    regress.isDirty =
                        regress.isChangedA ||
                        regress.isChangedB ||
                        regress.isChangedC ||
                        regress.isChangedD ||
                        regress.isChangedMB ||
                        regress.isChangedMP ||
                        regress.categorys.filter(
                            (category) =>
                                category.isChangedA ||
                                category.isChangedB ||
                                category.isChangedC ||
                                category.isChangedD ||
                                category.isChangedF
                        ).length > 0
                            ? true
                            : false;

                    this.times = 0;
                }, 500);
            },
            plusOrMinusOdds(regress, odds, lotteryId, categoryId, offset) {
                this.times++;
                let newVal = Number(
                    (odds["oddsA"] + offset * this.times).toFixed(5)
                );
                this.oddsChange(regress, odds, lotteryId, categoryId, "A", newVal);
            },
            oddsCheckField(regress, odds, lotteryId, categoryId, market) {
                let newVal = odds["odds" + market];
                this.oddsChange(
                    regress,
                    odds,
                    lotteryId,
                    categoryId,
                    market,
                    newVal
                );
            },
            regressCheckField(regress, lotteryId, kindId, market) {
                clearTimeout(this.timer);
                this.timer = setTimeout(() => {
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

                    regress.isDirty =
                        regress.isChangedA ||
                        regress.isChangedB ||
                        regress.isChangedC ||
                        regress.isChangedD ||
                        regress.isChangedMB ||
                        regress.isChangedMP ||
                        regress.categorys.filter(
                            (category) =>
                                category.isChangedA ||
                                category.isChangedB ||
                                category.isChangedC ||
                                category.isChangedD ||
                                category.isChangedF
                        ).length > 0
                            ? true
                            : false;
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

                    regress.isDirty =
                        regress.isChangedA ||
                        regress.isChangedB ||
                        regress.isChangedC ||
                        regress.isChangedD ||
                        regress.isChangedMB ||
                        regress.isChangedMP ||
                        regress.categorys.filter(
                            (category) =>
                                category.isChangedA ||
                                category.isChangedB ||
                                category.isChangedC ||
                                category.isChangedD ||
                                category.isChangedF
                        ).length > 0
                            ? true
                            : false;
                }, 500);
            },
            fullOddsCheckField(regress, odds, lotteryId, categoryId) {
                clearTimeout(this.timer);
                this.timer = setTimeout(() => {
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

                    regress.isDirty =
                        regress.isChangedA ||
                        regress.isChangedB ||
                        regress.isChangedC ||
                        regress.isChangedD ||
                        regress.isChangedMB ||
                        regress.isChangedMP ||
                        regress.categorys.filter(
                            (category) =>
                                category.isChangedA ||
                                category.isChangedB ||
                                category.isChangedC ||
                                category.isChangedD ||
                                category.isChangedF
                        ).length > 0
                            ? true
                            : false;
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
            async saveORLF(group, regress) {
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
                        let { kindId, maxBet, maxPeriod } = regress;
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

                    lottery.kinds.find(
                        (kind) => kind.kindId == regress.kindId
                    ).isDirty = false;
                });
            },
        },
    };
</script>
<style scoped>
</style>
