<template>
    <div>
        <div class="mb15">
            <header-groups :mapOdds="mapOdds" :userOddss="userOddss" :userStats="userStats" :groups="groups" @change-group="changeGroup"></header-groups>
        </div>
        <div>
            <template v-if="group.key!='zh'">
                <a-row :gutter="2">
                    <a-col class="gutter-row" :span="canEdit?20:22">
                        <a-row :gutter="2" class="zhhover">
                            <a-col class="gutter-row" :span="type.span" v-for="(type,ti) in group.types" :key="type.name">
                                <odds-normal :key="type.name" :oddsType="type" :canEdit="canEdit" :canCloseOpen="canCloseOpen" :userStats="userStats" :userOddss="userOddss" :userOddsNows="userOddsNows" :userOddsJumps="userOddsJumps" :userOddsCljps="userOddsCljps" :userOddsCloses="userOddsCloses" :sortBy="sortBy" @show-order="showOrder" @show-buhuo="showBuhuo" @update-odds="updateOdds" @update-odds-group="updateOddsGroup" @update-status="updateStatus" />
                                <template v-if="group.key=='no1_no2'&&ti==1">
                                    <table>
                                        <tr>
                                            <td colspan="2" class="forumrow">总投注额：</td>
                                            <td colspan="3" class="forumrowhighlight"><span class="green">{{gyhAmt.toFixed(2)}}</span></td>
                                        </tr>
                                        <tr>
                                            <td colspan="2" class="forumrow">最高亏损：</td>
                                            <td colspan="3" class="forumrowhighlight"><span class="red">{{gyhProfit.toFixed(2)}}</span></td>
                                        </tr>
                                        <tr>
                                            <td colspan="2" class="forumrow">最大注额：</td>
                                            <td colspan="3" class="forumrowhighlight">{{gyhMax}}</td>
                                        </tr>
                                    </table>
                                </template>
                            </a-col>
                        </a-row>
                    </a-col>
                    <!--1到10名快速设置与两面长龙-->
                    <a-col class="gutter-row" :span="canEdit?4:2">
                        <a-row :gutter="2" class="antrowtwo">
                            <a-col class="gutter-row" :span="10" v-if="canEdit">
                                <table class="tableborder" border="0" align="center" cellpadding="1" cellspacing="1" style="border-collapse: separate;width: 100%;">
                                    <tr>
                                        <th colspan="2">快速</th>
                                    </tr>
                                    <tr>
                                        <th colspan="2">1-10名</th>
                                    </tr>
                                    <tbody>
                                        <tr v-for="odds in quicks" :key="odds.oddsName">
                                            <td class="forumrow">{{odds.oddsName}}</td>
                                            <td width="42px" class="forumrowhighlight">
                                                <template v-if="odds.oddsKey=='dragon'||odds.oddsKey=='dragon'">
                                                    <img class="fl" :src="plus" @click.stop="updateOddsGroupSelf(['no1','no2','no3','no4','no5'],odds.oddsKey,1)">
                                                    <img class="fr" :src="minus" @click.stop="updateOddsGroupSelf(['no1','no2','no3','no4','no5'],odds.oddsKey,-1)">
                                                </template>
                                                <template v-else>
                                                    <img class="fl" :src="plus" @click.stop="updateOddsGroupSelf(['no1','no2','no3','no4','no5','no6','no7','no8','no9','no10'],odds.oddsKey,1)">
                                                    <img class="fr" :src="minus" @click.stop="updateOddsGroupSelf(['no1','no2','no3','no4','no5','no6','no7','no8','no9','no10'],odds.oddsKey,-1)">
                                                </template>
                                            </td>
                                        </tr>
                                    </tbody>
                                </table>
                            </a-col>
                            <a-col class="gutter-row" :span="canEdit?14:24">
                                <div class="gutter-box">
                                    <lmcl :lmcls="lmcls"></lmcl>
                                </div>
                            </a-col>
                        </a-row>
                    </a-col>
                </a-row>
            </template>
            <template v-else>
                <a-row :gutter="2" class="zhhover">
                    <a-col class="gutter-row" :span="type.span" v-for="type in group.types.slice(0,2)" :key="type.name">
                        <odds-special :key="type.name" :oddsType="type" :canEdit="canEdit" :canCloseOpen="false" :userStats="userStats" :userOddss="userOddss" :userOddsNows="userOddsNows" :userOddsJumps="userOddsJumps" :userOddsCljps="userOddsCljps" :userOddsCloses="userOddsCloses" :sortBy="sortBy" @show-order="showOrder" @show-buhuo="showBuhuo" @update-odds="updateOdds" @update-odds-group="updateOddsGroup" @update-status="updateStatus" />
                    </a-col>
                    <a-col class="gutter-row" span="2" style="width: 100px">
                        <lmcl :lmcls="lmcls"></lmcl>
                    </a-col>
                </a-row>
                <a-row :gutter="2" class="zhhover">
                    <a-col class="gutter-row" :span="type.span" v-for="type in group.types.slice(2,4)" :key="type.name">
                        <odds-special :key="type.name" :oddsType="type" :canEdit="canEdit" :canCloseOpen="false" :userStats="userStats" :userOddss="userOddss" :userOddsNows="userOddsNows" :userOddsJumps="userOddsJumps" :userOddsCljps="userOddsCljps" :userOddsCloses="userOddsCloses" :sortBy="sortBy" @show-order="showOrder" @show-buhuo="showBuhuo" @update-odds="updateOdds" @update-odds-group="updateOddsGroup" @update-status="updateStatus" />
                    </a-col>
                </a-row>
                <!--选种加setodds-->
            </template>
        </div>
    </div>
</template>
<script>
import minus from "@/assets/AdminDefaultTheme/Images/minus.png";
import plus from "@/assets/AdminDefaultTheme/Images/plus.png";
import OddsNormal from "./odds-normal.vue";
import OddsSpecial from "./odds-special.vue";
import HeaderGroups from "./header-groups.vue";
import Lmcl from "./lmcl.vue";
export default {
    components: {
        OddsNormal,
        OddsSpecial,
        HeaderGroups,
        Lmcl,
    },
    props: {
        userOddss: Object,
        userOddsNows: Object,
        userOddsJumps: Object,
        userOddsCljps: Object,
        userOddsCloses: Object,
        userStats: Object,
        plays: Object,
        kinds: Object,
        categorys: Object,
        oddss: Array,
        canEdit: Boolean,
        canCloseOpen: Boolean,
        oddsSteps: Array,
        sortBy: String,
        lmclObj: Object,
    },
    data() {
        return {
            plus,
            minus,
            selectIndex: 0,
            groups: [
                {
                    name: "冠、亚军组合",
                    key: "no1_no2",
                    amt: 0,
                    types: [
                        {
                            names: ["冠、亚军和 指定"],
                            span: 6,
                            col: ["gyh"],
                            row: [
                                "3",
                                "4",
                                "5",
                                "6",
                                "7",
                                "8",
                                "9",
                                "10",
                                "11",
                                "12",
                                "13",
                                "14",
                                "15",
                                "16",
                                "17",
                                "18",
                                "19",
                            ],
                            oddss: [[]],
                        },
                        {
                            names: ["冠、亚军和 两面"],
                            span: 6,
                            col: ["gyh"],
                            row: ["over", "under", "odd", "even"],
                            oddss: [[]],
                        },
                        {
                            names: ["冠军"],
                            span: 6,
                            col: ["no1"],
                            row: [
                                "over",
                                "under",
                                "odd",
                                "even",
                                "dragon",
                                "tiger",
                                "1",
                                "2",
                                "3",
                                "4",
                                "5",
                                "6",
                                "7",
                                "8",
                                "9",
                                "10",
                            ],
                            oddss: [[]],
                        },
                        {
                            names: ["亚军"],
                            span: 6,
                            col: ["no2"],
                            row: [
                                "over",
                                "under",
                                "odd",
                                "even",
                                "dragon",
                                "tiger",
                                "1",
                                "2",
                                "3",
                                "4",
                                "5",
                                "6",
                                "7",
                                "8",
                                "9",
                                "10",
                            ],
                            oddss: [[]],
                        },
                    ],
                },
                {
                    name: "三、四、五、六名",
                    amt: 0,
                    types: [
                        {
                            names: ["第三名"],
                            span: 6,
                            col: ["no3"],
                            row: [
                                "over",
                                "under",
                                "odd",
                                "even",
                                "dragon",
                                "tiger",
                                "1",
                                "2",
                                "3",
                                "4",
                                "5",
                                "6",
                                "7",
                                "8",
                                "9",
                                "10",
                            ],
                            oddss: [[]],
                        },
                        {
                            names: ["第四名"],
                            span: 6,
                            col: ["no4"],
                            row: [
                                "over",
                                "under",
                                "odd",
                                "even",
                                "dragon",
                                "tiger",
                                "1",
                                "2",
                                "3",
                                "4",
                                "5",
                                "6",
                                "7",
                                "8",
                                "9",
                                "10",
                            ],
                            oddss: [[]],
                        },
                        {
                            names: ["第五名"],
                            span: 6,
                            col: ["no5"],
                            row: [
                                "over",
                                "under",
                                "odd",
                                "even",
                                "dragon",
                                "tiger",
                                "1",
                                "2",
                                "3",
                                "4",
                                "5",
                                "6",
                                "7",
                                "8",
                                "9",
                                "10",
                            ],
                            oddss: [[]],
                        },
                        {
                            names: ["第六名"],
                            span: 6,
                            col: ["no6"],
                            row: [
                                "over",
                                "under",
                                "odd",
                                "even",
                                "1",
                                "2",
                                "3",
                                "4",
                                "5",
                                "6",
                                "7",
                                "8",
                                "9",
                                "10",
                            ],
                            oddss: [[]],
                        },
                    ],
                },
                {
                    name: "七、八、九、十名",
                    key: "no7-10",
                    amt: 0,
                    types: [
                        {
                            names: ["第七名"],
                            span: 6,
                            col: ["no7"],
                            row: [
                                "over",
                                "under",
                                "odd",
                                "even",
                                "1",
                                "2",
                                "3",
                                "4",
                                "5",
                                "6",
                                "7",
                                "8",
                                "9",
                                "10",
                            ],
                            oddss: [[]],
                        },
                        {
                            names: ["第八名"],
                            span: 6,
                            col: ["no8"],
                            row: [
                                "over",
                                "under",
                                "odd",
                                "even",
                                "1",
                                "2",
                                "3",
                                "4",
                                "5",
                                "6",
                                "7",
                                "8",
                                "9",
                                "10",
                            ],
                            oddss: [[]],
                        },
                        {
                            names: ["第九名"],
                            span: 6,
                            col: ["no9"],
                            row: [
                                "over",
                                "under",
                                "odd",
                                "even",
                                "1",
                                "2",
                                "3",
                                "4",
                                "5",
                                "6",
                                "7",
                                "8",
                                "9",
                                "10",
                            ],
                            oddss: [[]],
                        },
                        {
                            names: ["第十名"],
                            span: 6,
                            col: ["no10"],
                            row: [
                                "over",
                                "under",
                                "odd",
                                "even",
                                "1",
                                "2",
                                "3",
                                "4",
                                "5",
                                "6",
                                "7",
                                "8",
                                "9",
                                "10",
                            ],
                            oddss: [[]],
                        },
                    ],
                },
                {
                    name: "综合",
                    key: "zh",
                    amt: 0,
                    types: [
                        {
                            names: ["冠、亚军总和"],
                            span: 4,
                            col: ["gyh"],
                            row: ["over", "under", "odd", "even"],
                            oddss: [[]],
                        },
                        {
                            names: [
                                "冠军",
                                "亚军",
                                "第三名",
                                "第四名",
                                "第五名",
                            ],
                            span: 18,
                            col: ["no1", "no2", "no3", "no4", "no5"],
                            row: [
                                "over",
                                "under",
                                "odd",
                                "even",
                                "dragon",
                                "tiger",
                                "1",
                                "2",
                                "3",
                                "4",
                                "5",
                                "6",
                                "7",
                                "8",
                                "9",
                                "10",
                            ],
                            oddss: [[]],
                        },
                        {
                            names: ["冠、亚军总和"],
                            span: 4,
                            col: ["gyh"],
                            row: [
                                "3",
                                "4",
                                "5",
                                "6",
                                "7",
                                "8",
                                "9",
                                "10",
                                "11",
                                "12",
                                "13",
                                "14",
                                "15",
                                "16",
                                "17",
                                "18",
                                "19",
                            ],
                            oddss: [[]],
                        },
                        {
                            names: [
                                "第六名",
                                "第七名",
                                "第八名",
                                "第九名",
                                "第十名",
                            ],
                            span: 18,
                            col: ["no6", "no7", "no8", "no9", "no10"],
                            row: [
                                "over",
                                "under",
                                "odd",
                                "even",
                                "1",
                                "2",
                                "3",
                                "4",
                                "5",
                                "6",
                                "7",
                                "8",
                                "9",
                                "10",
                            ],
                            oddss: [[]],
                        },
                    ],
                },
            ],
            quicks: [
                { oddsName: "大", oddsKey: "over" },
                { oddsName: "小", oddsKey: "under" },
                { oddsName: "单", oddsKey: "odd" },
                { oddsName: "双", oddsKey: "even" },
                { oddsName: "龙", oddsKey: "dragon" },
                { oddsName: "虎", oddsKey: "tiger" },
                { oddsName: "1", oddsKey: "1" },
                { oddsName: "2", oddsKey: "2" },
                { oddsName: "3", oddsKey: "3" },
                { oddsName: "4", oddsKey: "4" },
                { oddsName: "5", oddsKey: "5" },
                { oddsName: "6", oddsKey: "6" },
                { oddsName: "7", oddsKey: "7" },
                { oddsName: "8", oddsKey: "8" },
                { oddsName: "9", oddsKey: "9" },
                { oddsName: "10", oddsKey: "10" },
            ],
            qiuDic: {
                no1: "第一名",
                no2: "第二名",
                no3: "第三名",
                no4: "第四名",
                no5: "第五名",
                no6: "第六名",
                no7: "第七名",
                no8: "第八名",
                no9: "第九名",
                no10: "第十名",
                gyh: "冠亚和",
            },
            lmDic: {
                over: "大",
                under: "小",
                odd: "单",
                even: "双",
                dragon: "龙",
                tiger: "虎",
            },
            timers: {},
            mapOdds: {},
        };
    },
    computed: {
        group() {
            return this.groups[this.selectIndex];
        },
        lmcls() {
            let lmcls = [];
            let keys = Object.keys(this.lmclObj);
            keys.forEach((key) => {
                let val = this.lmclObj[key];
                if (val >= 2) {
                    let arr = key.split("_");
                    lmcls.push({
                        name: this.qiuDic[arr[0]] + "-" + this.lmDic[arr[1]],
                        value: val,
                    });
                }
            });
            lmcls.sort((o1, no2) => {
                return no2.value - o1.value;
            });
            return lmcls;
        },
        gyhAmt() {
            let logic = 0;
            Object.values(this.userStats).forEach((s) => {
                logic += s.betAmt;
            });
            return logic;
        },
        gyhProfit() {
            let logic = 0;
            Object.values(this.userStats).forEach((s) => {
                logic = Math.min(logic, s.profitAmt);
            });
            return logic;
        },
        gyhMax() {
            let logic = 0;
            Object.values(this.userStats).forEach((s) => {
                logic = Math.max(logic, s.betAmt);
            });
            return logic;
        },
    },
    mounted() {
        this.refreshData(this.oddss);
    },
    watch: {
        oddss: function () {
            this.refreshData(this.oddss);
        },
        sortBy: function () {
            this.sortOdds();
        },
        userStats: function () {
            this.sortOdds();
        },
    },
    methods: {
        showBuhuo(odds) {
            this.$emit("show-buhuo", odds);
        },
        showOrder(oddsId) {
            this.$emit("show-order", oddsId);
        },
        updateOdds(oddsId, ji) {
            this.$emit("update-odds", oddsId, ji);
        },
        updateOddsGroup(playKeys, oddsKey, ji) {
            this.$emit("update-odds-group", playKeys, oddsKey, ji);
        },
        updateOddsGroupSelf(playKeys, oddsKey, ji) {
            if (!this.timers[oddsKey]) {
                this.timers[oddsKey] = { id: null, dj: 0 };
            }
            let timer = this.timers[oddsKey];
            timer.dj = timer.dj + 1;
            clearTimeout(timer.id);
            timer.id = setTimeout(() => {
                this.$emit(
                    "update-odds-group",
                    playKeys,
                    oddsKey,
                    ji * timer.dj
                );
                timer.dj = 0;
            }, 500);
        },
        updateStatus(odds, isClose) {
            this.$emit("update-status", odds, isClose);
        },
        changeGroup(index) {
            this.selectIndex = index;
            let oddsGroup = this.groups[index];
            this.sortOdds();
            this.$emit("change-group", oddsGroup);
        },
        refreshData(oddss) {
            let logic = {};
            oddss.forEach((odds) => {
                let { playId, oddsKey } = odds;
                let { playKey } = this.plays[playId];
                if (!logic[playKey]) {
                    logic[playKey] = {};
                }
                logic[playKey][oddsKey] = odds;
            });
            this.mapOdds = logic;
            this.groups.forEach((group) => {
                group.types.forEach((type) => {
                    let { col, row, oddss: os } = type;
                    row.forEach((r, ri) => {
                        col.forEach((c, ci) => {
                            if (!os[ri]) {
                                this.$set(os, ri, []);
                            }
                            let odds = logic[c][r];
                            this.$set(os[ri], ci, odds);
                        });
                    });
                });
            });
            this.sortOdds();
            let oddsGroup = this.groups[this.selectIndex];
            this.$emit("change-group", oddsGroup);
        },
        sortOdds() {
            let oddsGroup = this.groups[this.selectIndex];
            let names = {
                no1: true,
                no2: true,
                no3: true,
                no4: true,
                no5: true,
                no6: true,
                no7: true,
                no8: true,
                no9: true,
                no10: true,
            };
            if (this.sortBy == "YK") {
                oddsGroup.types.forEach((type) => {
                    if (type.col.length == 1) {
                        let name = type.col[0];
                        let has = names[name];
                        type.oddss.sort((a, b) => {
                            let {
                                oddsId: aoid,
                                categoryId: acid,
                                ordering: aod,
                            } = a[0];
                            let {
                                oddsId: boid,
                                categoryId: bcid,
                                ordering: bod,
                            } = b[0];
                            let ao = this.userStats[aoid];
                            let bo = this.userStats[boid];
                            let logic = has ? bcid - acid : 0;
                            if (logic != 0) {
                                return logic;
                            }
                            let av = ao ? ao.profitAmt : 0;
                            let bv = bo ? bo.profitAmt : 0;
                            logic = av - bv;
                            if (logic != 0) {
                                return logic;
                            }
                            return aod - bod;
                        });
                    }
                });
            } else if (this.sortBy == "JE") {
                oddsGroup.types.forEach((type) => {
                    if (type.col.length == 1) {
                        let name = type.col[0];
                        let has = names[name];
                        type.oddss.sort((a, b) => {
                            let {
                                oddsId: aoid,
                                categoryId: acid,
                                ordering: aod,
                            } = a[0];
                            let {
                                oddsId: boid,
                                categoryId: bcid,
                                ordering: bod,
                            } = b[0];
                            let ao = this.userStats[aoid];
                            let bo = this.userStats[boid];
                            let logic = has ? bcid - acid : 0;
                            if (logic != 0) {
                                return logic;
                            }
                            let av = ao ? ao.betAmt : 0;
                            let bv = bo ? bo.betAmt : 0;
                            logic = bv - av;
                            if (logic != 0) {
                                return logic;
                            }
                            return aod - bod;
                        });
                    }
                });
            } else if (this.sortBy == "HM") {
                oddsGroup.types.forEach((type) => {
                    if (type.col.length == 1) {
                        type.oddss.sort(
                            (a, b) => a[0].ordering - b[0].ordering
                        );
                    }
                });
            }
        },
    },
};
</script>
<style>
</style>
<style scoped>
td {
    font-weight: bold;
}

.type {
    background-color: #f8f8f9;
}

img {
    width: 18px;
    height: 18px;
}
</style>
