<template>
    <div>
        <div class="mb15">
            <header-groups :mapOdds="mapOdds" :userOddss="userOddss" :userStats="userStats" :groups="groups" @change-group="changeGroup"></header-groups>
        </div>
        <div>
            <a-row :gutter="2">
                <a-col class="gutter-row" :span="canEdit?20:22">
                    <a-row :gutter="2" class="zhhover">
                        <a-col class="gutter-row" :span="type.span" v-for="(type,ti) in group.types" :key="type.name">
                            <odds-normal :key="type.name" :oddsType="type" :canEdit="canEdit" :canCloseOpen="canCloseOpen" :userStats="userStats" :userOddss="userOddss" :userOddsNows="userOddsNows" :userOddsJumps="userOddsJumps" :userOddsCljps="userOddsCljps" :userOddsCloses="userOddsCloses" :sortBy="sortBy" @show-order="showOrder" @show-buhuo="showBuhuo" @update-odds="updateOdds" @update-odds-group="updateOddsGroup" @update-status="updateStatus" />
                        </a-col>
                    </a-row>
                </a-col>
            </a-row>
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
                    name: "全部",
                    key: "total",
                    amt: 0,
                    types: [
                        {
                            names: ["两面"],
                            span: 6,
                            col: ["lm"],
                            row: [
                                "over",
                                "under",
                                "odd",
                                "even",
                                "extremaOver",
                                "tinyUnder",
                                "overOdd",
                                "underOdd",
                                "overEven",
                                "underEven"
                            ],
                            oddss: [[]],
                        },
                        {
                            names: ["和值"],
                            span: 6,
                            col: ["hezhi"],
                            row: ["0","1","2","3","4","5","6","7","8","9",
                                  "10","11","12","13","14","15","16","17","18","19",
                                  "20","21","22","23","24","25","26","27"],
                            oddss: [[]],
                        },
                        {
                            names: ["色波"],
                            span: 6,
                            col: ["sebo"],
                            row: [
                                "redBall",
                                "greenBall",
                                "blueBall"
                            ],
                            oddss: [[]],
                        },
                        {
                            names: ["豹子"],
                            span: 6,
                            col: ["baozi"],
                            row: [
                                "baozi",
                            ],
                            oddss: [[]],
                        },
                        {
                            names: ["对子"],
                            span: 6,
                            col: ["duizi"],
                            row: [
                                "duizi",
                            ],
                            oddss: [[]],
                        },
                        {
                            names: ["顺子"],
                            span: 6,
                            col: ["shunzi"],
                            row: [
                                "shunzi",
                            ],
                            oddss: [[]],
                        }
                    ],
                }
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
