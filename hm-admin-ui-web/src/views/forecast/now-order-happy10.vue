<template>
    <div>
        <div class="mb15">
            <header-groups :mapOdds="mapOdds" :userOddss="userOddss" :userStats="userStats" :groups="groups" @change-group="changeGroup"></header-groups>
        </div>
        <div>
            <template v-if="group.key!='lianma'">
                <a-row :gutter=" 2">
                    <a-col class="gutter-row" :span="21">
                        <a-row :gutter="2" class="zhhover">
                            <a-col class="gutter-row" :span="type.span" v-for="(type,ti) in group.types" :key="type.name">
                                <template v-if="group.key=='szp'||group.key=='lmp'">
                                    <odds-special :key="type.name" :oddsType="type" :canEdit="canEdit" :canCloseOpen="false" :userStats="userStats" :userOddss="userOddss" :userOddsNows="userOddsNows" :userOddsJumps="userOddsJumps" :userOddsCljps="userOddsCljps" :userOddsCloses="userOddsCloses" :sortBy="sortBy" @show-order="showOrder" @show-buhuo="showBuhuo" @update-odds="updateOdds" @update-odds-group="updateOddsGroup" @update-status="updateStatus" />
                                </template>
                                <template v-if="group.key=='qiu1'||group.key=='qiu2'||group.key=='qiu3'||group.key=='qiu4'||group.key=='qiu5'||group.key=='qiu6'||group.key=='qiu7'||group.key=='qiu8'||group.key=='sum_zm'">
                                    <odds-normal :key="type.name" :oddsType="type" :canEdit="canEdit" :canCloseOpen="canCloseOpen" :userStats="userStats" :userOddss="userOddss" :userOddsNows="userOddsNows" :userOddsJumps="userOddsJumps" :userOddsCljps="userOddsCljps" :userOddsCloses="userOddsCloses" :sortBy="sortBy" @show-order="showOrder" @show-buhuo="showBuhuo" @update-odds="updateOdds" @update-odds-group="updateOddsGroup" @update-status="updateStatus" />
                                </template>
                            </a-col>
                        </a-row>
                    </a-col>
                    <!--遗漏-->
                    <a-col class="gutter-row" :span="1">
                        <yl :yls="yls"></yl>
                    </a-col>
                    <!--两面长龙-->
                    <a-col class="gutter-row" :span="2">
                        <lmcl :lmcls="lmcls"></lmcl>
                    </a-col>
                </a-row>
            </template>
            <template v-else>
                <a-row :gutter="2" class="zhhoversu" v-for="(type,ri) in group.types" :key="ri">
                    <a-col class="gutter-row" :span="type.span">
                        <odds-tran-col :key="type.name" :oddsType="type" :canEdit="canEdit" :canCloseOpen="canCloseOpen" :userStats="userStats" :userOddss="userOddss" :userOddsNows="userOddsNows" :userOddsJumps="userOddsJumps" :userOddsCljps="userOddsCljps" :userOddsCloses="userOddsCloses" :sortBy="sortBy" @show-order="showOrder" @show-buhuo="showBuhuo" @update-odds="updateOdds" @update-odds-group="updateOddsGroup" @update-status="updateStatus" />
                    </a-col>
                </a-row>
            </template>

        </div>
    </div>

</template>
<script>
import minus from "@/assets/AdminDefaultTheme/Images/minus.png";
import plus from "@/assets/AdminDefaultTheme/Images/plus.png";
import OddsNormal from "./odds-normal.vue";
import OddsSpecial from "./odds-special.vue";
import OddsTranCol from "./odds-tran-col.vue";
import HeaderGroups from "./header-groups.vue";
import Lmcl from "./lmcl.vue";
import Yl from "./yl.vue";
export default {
    components: {
        OddsNormal,
        OddsSpecial,
        OddsTranCol,
        HeaderGroups,
        Lmcl,
        Yl,
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
        ylObj: Object,
    },
    data() {
        return {
            plus,
            minus,
            selectIndex: 0,
            groups: [
                {
                    name: "数字盘",
                    key: "szp",
                    amt: 0,
                    types: [
                        {
                            span: 24,
                            names: [
                                "第一球",
                                "第二球",
                                "第三球",
                                "第四球",
                                "第五球",
                                "第六球",
                                "第七球",
                                "第八球",
                            ],
                            col: [
                                "qiu1",
                                "qiu2",
                                "qiu3",
                                "qiu4",
                                "qiu5",
                                "qiu6",
                                "qiu7",
                                "qiu8",
                            ],
                            row: [
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
                                "11",
                                "12",
                                "13",
                                "14",
                                "15",
                                "16",
                                "17",
                                "18",
                                "19",
                                "20",
                            ],
                            oddss: [[]],
                        },
                    ],
                },
                {
                    name: "两面盘",
                    key: "lmp",
                    amt: 0,
                    types: [
                        {
                            span: 21,
                            names: [
                                "第一球",
                                "第二球",
                                "第三球",
                                "第四球",
                                "第五球",
                                "第六球",
                                "第七球",
                                "第八球",
                            ],
                            col: [
                                "qiu1",
                                "qiu2",
                                "qiu3",
                                "qiu4",
                                "qiu5",
                                "qiu6",
                                "qiu7",
                                "qiu8",
                            ],
                            row: [
                                "over",
                                "under",
                                "odd",
                                "even",
                                "dragon",
                                "tiger",
                                "weiover",
                                "weiunder",
                                "heodd",
                                "heeven",
                                "east",
                                "south",
                                "west",
                                "north",
                                "zhong",
                                "fa",
                                "bai",
                            ],
                            oddss: [[]],
                        },
                        {
                            span: 3,
                            names: ["总和"],
                            col: ["sum"],
                            row: [
                                "over",
                                "under",
                                "odd",
                                "even",
                                "weiover",
                                "weiunder",
                            ],
                            oddss: [[]],
                        },
                    ],
                },
                {
                    name: "第一球",
                    key: "qiu1",
                    amt: 0,
                    types: [
                        {
                            span: 12,
                            names: ["第一球"],
                            col: ["qiu1"],
                            row: [
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
                                "11",
                                "12",
                                "13",
                                "14",
                                "15",
                                "16",
                                "17",
                                "18",
                                "19",
                                "20",
                            ],
                            oddss: [[]],
                        },
                        {
                            span: 12,
                            names: ["第一球"],
                            col: ["qiu1"],
                            row: [
                                "over",
                                "under",
                                "odd",
                                "even",
                                "dragon",
                                "tiger",
                                "weiover",
                                "weiunder",
                                "heodd",
                                "heeven",
                                "east",
                                "south",
                                "west",
                                "north",
                                "zhong",
                                "fa",
                                "bai",
                            ],
                            oddss: [[]],
                        },
                    ],
                },
                {
                    name: "第二球",
                    key: "qiu2",
                    amt: 0,
                    types: [
                        {
                            span: 12,
                            names: ["第二球"],
                            col: ["qiu2"],
                            row: [
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
                                "11",
                                "12",
                                "13",
                                "14",
                                "15",
                                "16",
                                "17",
                                "18",
                                "19",
                                "20",
                            ],
                            oddss: [[]],
                        },
                        {
                            span: 12,
                            names: ["第二球"],
                            col: ["qiu2"],
                            row: [
                                "over",
                                "under",
                                "odd",
                                "even",
                                "dragon",
                                "tiger",
                                "weiover",
                                "weiunder",
                                "heodd",
                                "heeven",
                                "east",
                                "south",
                                "west",
                                "north",
                                "zhong",
                                "fa",
                                "bai",
                            ],
                            oddss: [[]],
                        },
                    ],
                },
                {
                    name: "第三球",
                    key: "qiu3",
                    amt: 0,
                    types: [
                        {
                            span: 12,
                            names: ["第三球"],
                            col: ["qiu3"],
                            row: [
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
                                "11",
                                "12",
                                "13",
                                "14",
                                "15",
                                "16",
                                "17",
                                "18",
                                "19",
                                "20",
                            ],
                            oddss: [[]],
                        },
                        {
                            span: 12,
                            names: ["第三球"],
                            col: ["qiu3"],
                            row: [
                                "over",
                                "under",
                                "odd",
                                "even",
                                "dragon",
                                "tiger",
                                "weiover",
                                "weiunder",
                                "heodd",
                                "heeven",
                                "east",
                                "south",
                                "west",
                                "north",
                                "zhong",
                                "fa",
                                "bai",
                            ],
                            oddss: [[]],
                        },
                    ],
                },
                {
                    name: "第四球",
                    key: "qiu4",
                    amt: 0,
                    types: [
                        {
                            span: 12,
                            names: ["第四球"],
                            col: ["qiu4"],
                            row: [
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
                                "11",
                                "12",
                                "13",
                                "14",
                                "15",
                                "16",
                                "17",
                                "18",
                                "19",
                                "20",
                            ],
                            oddss: [[]],
                        },
                        {
                            span: 12,
                            names: ["第四球"],
                            col: ["qiu4"],
                            row: [
                                "over",
                                "under",
                                "odd",
                                "even",
                                "dragon",
                                "tiger",
                                "weiover",
                                "weiunder",
                                "heodd",
                                "heeven",
                                "east",
                                "south",
                                "west",
                                "north",
                                "zhong",
                                "fa",
                                "bai",
                            ],
                            oddss: [[]],
                        },
                    ],
                },
                {
                    name: "第五球",
                    key: "qiu5",
                    amt: 0,
                    types: [
                        {
                            span: 12,
                            names: ["第五球"],
                            col: ["qiu5"],
                            row: [
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
                                "11",
                                "12",
                                "13",
                                "14",
                                "15",
                                "16",
                                "17",
                                "18",
                                "19",
                                "20",
                            ],
                            oddss: [[]],
                        },
                        {
                            span: 12,
                            names: ["第五球"],
                            col: ["qiu5"],
                            row: [
                                "over",
                                "under",
                                "odd",
                                "even",
                                "weiover",
                                "weiunder",
                                "heodd",
                                "heeven",
                                "east",
                                "south",
                                "west",
                                "north",
                                "zhong",
                                "fa",
                                "bai",
                            ],
                            oddss: [[]],
                        },
                    ],
                },
                {
                    name: "第六球",
                    key: "qiu6",
                    amt: 0,
                    types: [
                        {
                            span: 12,
                            names: ["第六球"],
                            col: ["qiu6"],
                            row: [
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
                                "11",
                                "12",
                                "13",
                                "14",
                                "15",
                                "16",
                                "17",
                                "18",
                                "19",
                                "20",
                            ],
                            oddss: [[]],
                        },
                        {
                            span: 12,
                            names: ["第六球"],
                            col: ["qiu6"],
                            row: [
                                "over",
                                "under",
                                "odd",
                                "even",
                                "weiover",
                                "weiunder",
                                "heodd",
                                "heeven",
                                "east",
                                "south",
                                "west",
                                "north",
                                "zhong",
                                "fa",
                                "bai",
                            ],
                            oddss: [[]],
                        },
                    ],
                },
                {
                    name: "第七球",
                    key: "qiu7",
                    amt: 0,
                    types: [
                        {
                            span: 12,
                            names: ["第七球"],
                            col: ["qiu7"],
                            row: [
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
                                "11",
                                "12",
                                "13",
                                "14",
                                "15",
                                "16",
                                "17",
                                "18",
                                "19",
                                "20",
                            ],
                            oddss: [[]],
                        },
                        {
                            span: 12,
                            names: ["第七球"],
                            col: ["qiu7"],
                            row: [
                                "over",
                                "under",
                                "odd",
                                "even",
                                "weiover",
                                "weiunder",
                                "heodd",
                                "heeven",
                                "east",
                                "south",
                                "west",
                                "north",
                                "zhong",
                                "fa",
                                "bai",
                            ],
                            oddss: [[]],
                        },
                    ],
                },
                {
                    name: "第八球",
                    key: "qiu8",
                    amt: 0,
                    types: [
                        {
                            span: 12,
                            names: ["第八球"],
                            col: ["qiu8"],
                            row: [
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
                                "11",
                                "12",
                                "13",
                                "14",
                                "15",
                                "16",
                                "17",
                                "18",
                                "19",
                                "20",
                            ],
                            oddss: [[]],
                        },
                        {
                            span: 12,
                            names: ["第八球"],
                            col: ["qiu8"],
                            row: [
                                "over",
                                "under",
                                "odd",
                                "even",
                                "weiover",
                                "weiunder",
                                "heodd",
                                "heeven",
                                "east",
                                "south",
                                "west",
                                "north",
                                "zhong",
                                "fa",
                                "bai",
                            ],
                            oddss: [[]],
                        },
                    ],
                },
                {
                    name: "总和丶正码",
                    key: "sum_zm",
                    amt: 0,
                    types: [
                        {
                            span: 12,
                            names: ["正码"],
                            col: ["zm"],
                            row: [
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
                                "11",
                                "12",
                                "13",
                                "14",
                                "15",
                                "16",
                                "17",
                                "18",
                                "19",
                                "20",
                            ],
                            oddss: [[]],
                        },
                        {
                            span: 12,
                            names: ["总和"],
                            col: ["sum"],
                            row: [
                                "over",
                                "under",
                                "odd",
                                "even",
                                "weiover",
                                "weiunder",
                            ],
                            oddss: [[]],
                        },
                    ],
                },
                {
                    name: "连码",
                    key: "lianma",
                    amt: 0,
                    types: [
                        {
                            span: 24,
                            names: ["连码"],
                            col: ["lianma"],
                            row: [
                                "x2",
                                "x2lz",
                                "x2lg",
                                "x3",
                                "x3topz",
                                "x3topg",
                                "x4",
                                "x5",
                            ],
                            oddss: [[]],
                        },
                    ],
                },
            ],
            quicks: [
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
                { oddsName: "11", oddsKey: "11" },
                { oddsName: "12", oddsKey: "12" },
                { oddsName: "13", oddsKey: "13" },
                { oddsName: "14", oddsKey: "14" },
                { oddsName: "15", oddsKey: "15" },
                { oddsName: "16", oddsKey: "16" },
                { oddsName: "17", oddsKey: "17" },
                { oddsName: "18", oddsKey: "18" },
                { oddsName: "19", oddsKey: "19" },
                { oddsName: "20", oddsKey: "20" },
            ],
            quicks2: [
                { oddsName: "大", oddsKey: "1" },
                { oddsName: "小", oddsKey: "2" },
                { oddsName: "单", oddsKey: "3" },
                { oddsName: "双", oddsKey: "4" },
                { oddsName: "尾大", oddsKey: "5" },
                { oddsName: "尾小", oddsKey: "6" },
                { oddsName: "合数单", oddsKey: "7" },
                { oddsName: "合数双", oddsKey: "8" },
                { oddsName: "东", oddsKey: "9" },
                { oddsName: "西", oddsKey: "10" },
                { oddsName: "南", oddsKey: "11" },
                { oddsName: "北", oddsKey: "12" },
                { oddsName: "中", oddsKey: "13" },
                { oddsName: "发", oddsKey: "14" },
                { oddsName: "白", oddsKey: "15" },
                { oddsName: "龙", oddsKey: "16" },
                { oddsName: "虎", oddsKey: "17" },
            ],
            qiuDic: {
                qiu1: "第一球",
                qiu2: "第二球",
                qiu3: "第三球",
                qiu4: "第四球",
                qiu5: "第五球",
                qiu6: "第六球",
                qiu7: "第七球",
                qiu8: "第八球",
                sum: "总和",
            },
            lmDic: {
                over: "大",
                under: "小",
                odd: "单",
                even: "双",
                dragon: "龙",
                tiger: "虎",
                heodd: "合数单",
                heeven: "合数双",
                weiover: "尾大",
                weiunder: "尾小",
            },
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
            lmcls.sort((o1, o2) => {
                return o2.value - o1.value;
            });
            return lmcls;
        },
        yls() {
            let yls = [];

            let keys = Object.keys(this.ylObj);
            keys.forEach((key) => {
                let val = this.ylObj[key];
                if (val >= 2) {
                    let arr = key.split("_");
                    yls.push({
                        name: arr[1],
                        value: val,
                    });
                }
            });
            yls.sort((o1, o2) => {
                return o2.value - o1.value;
            });
            return yls;
        },
    },
    mounted() {
        this.refreshData(this.oddss);
    },
    watch: {
        sortBy: function () {
            this.sortOdds();
        },
        oddss: function (n, o) {
            this.refreshData(this.oddss);
        },
        userStats: function () {
            this.sortOdds();
        },
    },
    methods: {
        changeGroup(index) {
            this.selectIndex = index;
            this.sortOdds();
            this.$emit("change-group", this.groups[index]);
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
                            if (odds) {
                                this.$set(os[ri], ci, odds);
                            } else {
                                this.$set(os[ri], ci, {});
                            }
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
                qiu1: true,
                qiu2: true,
                qiu3: true,
                qiu4: true,
                qiu5: true,
                qiu6: true,
                qiu7: true,
                qiu8: true,
                zm: true,
            };
            if (this.sortBy == "YK") {
                oddsGroup.types.forEach((type, index) => {
                    if (type.col.length == 1) {
                        let name = type.col[0];
                        let has = names[name] && index == 0;
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
                oddsGroup.types.forEach((type, index) => {
                    if (type.col.length == 1) {
                        let name = type.col[0];
                        let has = names[name] && index == 0;
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
        showBuhuo(odds) {
            this.$emit("show-buhuo", odds);
        },
        showOrder(oddsId) {
            this.$emit("show-order", oddsId);
        },
        updateOdds(id, ji) {
            this.$emit("update-odds", id, ji);
        },
        updateOddsGroup(playKeys, oddsKey, ji) {
            this.$emit("update-odds-group", playKeys, oddsKey, ji);
        },
        updateStatus(odds, isClose) {
            this.$emit("update-status", odds, isClose);
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
