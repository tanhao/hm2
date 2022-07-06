<template>
    <div>
        <div class="mb15">
            <header-groups :mapOdds="mapOdds" :userOddss="userOddss" :userStats="userStats" :groups="groups" @change-group="changeGroup"></header-groups>
        </div>
        <div>

            <template v-if="group.key=='zh'">
                <a-row :gutter="2" class="zhhover">
                    <a-col class="gutter-row" :span="type.span" v-for="(type,ti) in group.types.slice(0,2)" :key="type.name">
                        <odds-special :key="type.name" :oddsType="type" :canEdit="canEdit" :canCloseOpen="canCloseOpen" :userStats="userStats" :userOddss="userOddss" :userOddsNows="userOddsNows" :userOddsJumps="userOddsJumps" :userOddsCljps="userOddsCljps" :userOddsCloses="userOddsCloses" :sortBy="sortBy" @show-order="showOrder" @show-buhuo="showBuhuo" @update-odds="updateOdds" @update-status="updateStatus" />
                        <template v-if="ti==1">
                            <template v-for="type in group.types.slice(2,3)">
                                <odds-special :key="type.name" :oddsType="type" :canEdit="canEdit" :canCloseOpen="canCloseOpen" :userStats="userStats" :userOddss="userOddss" :userOddsNows="userOddsNows" :userOddsJumps="userOddsJumps" :userOddsCljps="userOddsCljps" :userOddsCloses="userOddsCloses" :sortBy="sortBy" @show-order="showOrder" @show-buhuo="showBuhuo" @update-odds="updateOdds" @update-odds-group="updateOddsGroup" @update-status="updateStatus" />
                            </template>
                        </template>
                    </a-col>
                    <a-col class="gutter-row" :span="2">
                        <div class="gutter-box">
                            <lmcl :lmcls="lmcls"></lmcl>
                        </div>
                    </a-col>
                </a-row>
            </template>
            <template v-else-if="group.key=='1z'||group.key=='2z'||group.key=='3z'||group.key=='2hs'||group.key=='3hs'||group.key=='kd'">
                <a-row :gutter="2" class="zhhover">
                    <a-col class="gutter-row" :span="type.span" v-for="type in group.types.slice(0,2)" :key="type.name">
                        <odds-special :key="type.name" :oddsType="type" :canEdit="canEdit" :canCloseOpen="canCloseOpen" :userStats="userStats" :userOddss="userOddss" :userOddsNows="userOddsNows" :userOddsJumps="userOddsJumps" :userOddsCljps="userOddsCljps" :userOddsCloses="userOddsCloses" :sortBy="sortBy" @show-order="showOrder" @show-buhuo="showBuhuo" @update-odds="updateOdds" @update-odds-group="updateOddsGroup" @update-status="updateStatus" />
                    </a-col>
                </a-row>
            </template>
            <template v-else-if="group.key=='2dw'||group.key=='3dw'|| group.key=='zx3'||group.key=='zx6'">
                <a-row :gutter="2" v-for="(type,ri) in group.types" :key="ri">
                    <a-col class="gutter-row" :span="type.span">
                        <odds-tran-col :key="type.name" :oddsType="type" :canEdit="canEdit" :canCloseOpen="canCloseOpen" :userStats="userStats" :userOddss="userOddss" :userOddsNows="userOddsNows" :userOddsJumps="userOddsJumps" :userOddsCljps="userOddsCljps" :userOddsCloses="userOddsCloses" :sortBy="sortBy" @show-order="showOrder" @show-buhuo="showBuhuo" @update-odds="updateOdds" @update-odds-group="updateOddsGroup" @update-status="updateStatus" />
                    </a-col>
                </a-row>
            </template>
            <template v-else-if="group.key=='fs'||group.key=='sh'||group.key=='nn'">
                <a-row :gutter="2">
                    <a-col class="gutter-row" :span="type.span" v-for="type in group.types.slice(0,2)" :key="type.name">
                        <odds-normal :key="type.name" :oddsType="type" :canEdit="canEdit" :canCloseOpen="canCloseOpen" :userStats="userStats" :userOddss="userOddss" :userOddsNows="userOddsNows" :userOddsJumps="userOddsJumps" :userOddsCljps="userOddsCljps" :userOddsCloses="userOddsCloses" :sortBy="sortBy" @show-order="showOrder" @show-buhuo="showBuhuo" @update-odds="updateOdds" @update-odds-group="updateOddsGroup" @update-status="updateStatus" />
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

export default {
    components: {
        OddsNormal,
        OddsSpecial,
        OddsTranCol,
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
                    name: "综合",
                    key: "zh",
                    amt: 0,
                    types: [
                        {
                            names: [
                                "第一球",
                                "第二球",
                                "第三球",
                                "第四球",
                                "第五球",
                            ],
                            span: 14,
                            col: ["qiu1", "qiu2", "qiu3", "qiu4", "qiu5"],
                            row: [
                                "over",
                                "under",
                                "odd",
                                "even",
                                "0",
                                "1",
                                "2",
                                "3",
                                "4",
                                "5",
                                "6",
                                "7",
                                "8",
                                "9",
                            ],
                            oddss: [[]],
                        },
                        {
                            names: ["总和"],
                            span: 8,
                            col: ["t_lhh"],
                            row: [
                                "over",
                                "under",
                                "odd",
                                "even",
                                "dragon",
                                "tiger",
                                "tie",
                            ],
                            oddss: [[]],
                        },
                        {
                            names: ["前三", "中三", "后三"],
                            col: ["top3", "mid3", "end3"],
                            row: ["baozi", "sunzi", "duizi", "bansun", "zaliu"],
                            oddss: [[]],
                        },
                    ],
                },
                {
                    name: "一字",
                    key: "1z",
                    amt: 0,
                    types: [
                        {
                            names: ["前三", "中三", "后三", "全五"],
                            span: 24,
                            col: ["1ztop3", "1zmid3", "1zend3", "1zall5"],
                            row: [
                                "0",
                                "1",
                                "2",
                                "3",
                                "4",
                                "5",
                                "6",
                                "7",
                                "8",
                                "9",
                            ],
                            oddss: [[]],
                        },
                    ],
                },
                {
                    name: "二字",
                    key: "2z",
                    amt: 0,
                    types: [
                        {
                            names: ["前三", "中三", "后三"],
                            span: 12,
                            col: ["2ztop3", "2zmid3", "2zend3"],
                            row: [
                                "00",
                                "01",
                                "02",
                                "03",
                                "04",
                                "05",
                                "06",
                                "07",
                                "08",
                                "09",
                                "11",
                                "12",
                                "13",
                                "14",
                                "15",
                                "16",
                                "17",
                                "18",
                                "19",
                                "22",
                                "23",
                                "24",
                                "25",
                                "26",
                                "27",
                                "28",
                                "29",
                            ],
                            oddss: [[]],
                        },
                        {
                            names: ["前三", "中三", "后三"],
                            span: 12,
                            col: ["2ztop3", "2zmid3", "2zend3"],
                            row: [
                                "33",
                                "34",
                                "36",
                                "37",
                                "38",
                                "39",
                                "44",
                                "45",
                                "46",
                                "47",
                                "48",
                                "49",
                                "55",
                                "56",
                                "57",
                                "58",
                                "59",
                                "66",
                                "67",
                                "68",
                                "69",
                                "77",
                                "78",
                                "79",
                                "88",
                                "89",
                                "99",
                            ],
                            oddss: [[]],
                        },
                    ],
                },
                {
                    name: "三字",
                    key: "3z",
                    amt: 0,
                    types: [
                        {
                            names: ["前三", "中三", "后三"],
                            span: 12,
                            col: ["3ztop3", "3zmid3", "3zend3"],
                            row: [
                                "000",
                                "001",
                                "002",
                                "003",
                                "004",
                                "005",
                                "006",
                                "007",
                                "008",
                                "009",
                                "011",
                                "012",
                                "013",
                                "014",
                                "015",
                                "016",
                                "017",
                                "018",
                                "019",
                                "022",
                                "023",
                                "024",
                                "025",
                                "026",
                                "027",
                                "028",
                                "029",
                                "033",
                                "034",
                                "035",
                                "036",
                                "037",
                                "038",
                                "039",
                                "044",
                                "045",
                                "046",
                                "047",
                                "048",
                                "049",
                                "055",
                                "056",
                                "057",
                                "058",
                                "059",
                                "066",
                                "067",
                                "068",
                                "069",
                                "077",
                                "078",
                                "079",
                                "088",
                                "089",
                                "099",
                                "111",
                                "112",
                                "113",
                                "114",
                                "115",
                                "116",
                                "117",
                                "118",
                                "119",
                                "122",
                                "123",
                                "124",
                                "125",
                                "126",
                                "127",
                                "128",
                                "129",
                                "133",
                                "134",
                                "135",
                                "136",
                                "137",
                                "138",
                                "139",
                                "144",
                                "145",
                                "146",
                                "147",
                                "148",
                                "149",
                                "155",
                                "156",
                                "157",
                                "158",
                                "159",
                                "166",
                                "167",
                                "168",
                                "169",
                                "177",
                                "178",
                                "179",
                                "188",
                                "189",
                                "199",
                                "222",
                                "223",
                                "224",
                                "225",
                                "226",
                                "227",
                                "228",
                                "229",
                                "233",
                                "234",
                                "235",
                            ],
                            oddss: [[]],
                        },
                        {
                            names: ["前三", "中三", "后三"],
                            span: 12,
                            col: ["3ztop3", "3zmid3", "3zend3"],
                            row: [
                                "236",
                                "237",
                                "238",
                                "239",
                                "244",
                                "245",
                                "246",
                                "247",
                                "248",
                                "249",
                                "255",
                                "256",
                                "257",
                                "258",
                                "259",
                                "266",
                                "267",
                                "268",
                                "269",
                                "277",
                                "278",
                                "279",
                                "288",
                                "289",
                                "299",
                                "333",
                                "334",
                                "335",
                                "336",
                                "337",
                                "338",
                                "339",
                                "344",
                                "345",
                                "346",
                                "347",
                                "348",
                                "349",
                                "355",
                                "356",
                                "357",
                                "358",
                                "359",
                                "366",
                                "367",
                                "368",
                                "369",
                                "377",
                                "378",
                                "379",
                                "388",
                                "389",
                                "399",
                                "444",
                                "445",
                                "446",
                                "447",
                                "448",
                                "449",
                                "455",
                                "456",
                                "457",
                                "458",
                                "459",
                                "466",
                                "467",
                                "468",
                                "469",
                                "477",
                                "478",
                                "479",
                                "488",
                                "489",
                                "499",
                                "555",
                                "556",
                                "557",
                                "558",
                                "559",
                                "566",
                                "567",
                                "568",
                                "569",
                                "577",
                                "578",
                                "579",
                                "588",
                                "589",
                                "599",
                                "666",
                                "667",
                                "668",
                                "669",
                                "677",
                                "678",
                                "679",
                                "688",
                                "689",
                                "699",
                                "777",
                                "778",
                                "779",
                                "788",
                                "789",
                                "799",
                                "888",
                                "889",
                                "899",
                                "999",
                            ],
                            oddss: [[]],
                        },
                    ],
                },
                {
                    name: "二字定",
                    key: "2dw",
                    amt: 0,
                    types: [
                        {
                            names: ["二字定"],
                            span: 24,
                            col: ["2dw"],
                            row: [
                                "wq",
                                "wb",
                                "ws",
                                "wg",
                                "qb",
                                "qs",
                                "qg",
                                "bs",
                                "bg",
                                "sg",
                            ],
                            oddss: [[]],
                        },
                    ],
                },
                {
                    name: "三字定",
                    key: "3dw",
                    amt: 0,
                    types: [
                        {
                            names: ["三字定"],
                            col: ["3dw"],
                            row: ["top3", "mid3", "end3"],
                            oddss: [[]],
                        },
                    ],
                },
                {
                    name: "二字和数",
                    key: "2hs",
                    amt: 0,
                    types: [
                        {
                            names: [
                                "万千定位",
                                "万佰定位",
                                "万拾定位",
                                "万个定位",
                                "千佰定位",
                                "千拾定位",
                                "千个定位",
                                "佰拾定位",
                                "佰个定位",
                                "拾个定位",
                            ],
                            span: 24,
                            col: [
                                "2hswq",
                                "2hswb",
                                "2hsws",
                                "2hswg",
                                "2hsqb",
                                "2hsqs",
                                "2hsqg",
                                "2hsbs",
                                "2hsbg",
                                "2hssg",
                            ],
                            row: [
                                "0~4",
                                "5",
                                "6",
                                "7",
                                "8",
                                "9",
                                "10",
                                "11",
                                "12",
                                "13",
                                "14~18",
                            ],
                            oddss: [[]],
                        },
                        {
                            names: [
                                "万千和数尾数",
                                "万佰和数尾数",
                                "万拾和数尾数",
                                "万个和数尾数",
                                "千佰和数尾数",
                                "千拾和数尾数",
                                "千个和数尾数",
                                "佰拾和数尾数",
                                "佰个和数尾数",
                                "拾个和数尾数",
                            ],
                            span: 24,
                            col: [
                                "2hwswq",
                                "2hwswb",
                                "2hwsws",
                                "2hwswg",
                                "2hwsqb",
                                "2hwsqs",
                                "2hwsqg",
                                "2hwsbs",
                                "2hwsbg",
                                "2hwssg",
                            ],
                            row: [
                                "0",
                                "1",
                                "2",
                                "3",
                                "4",
                                "5",
                                "6",
                                "7",
                                "8",
                                "9",
                            ],
                            oddss: [[]],
                        },
                    ],
                },
                {
                    name: "三字和数",
                    key: "3hs",
                    amt: 0,
                    types: [
                        {
                            names: ["前三和数", "中三和数", "后三和数"],
                            span: 12,
                            col: ["3hstop3", "3hsmid3", "3hsend3"],
                            row: [
                                "0~6",
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
                                "21~27",
                            ],
                            oddss: [[]],
                        },
                        {
                            names: [
                                "前三和数尾数",
                                "中三和数尾数",
                                "后三和数尾数",
                            ],
                            span: 12,
                            col: ["3hwstop3", "3hwsmid3", "3hwsend3"],
                            row: [
                                "0",
                                "1",
                                "2",
                                "3",
                                "4",
                                "5",
                                "6",
                                "7",
                                "8",
                                "9",
                            ],
                            oddss: [[]],
                        },
                    ],
                },
                {
                    name: "组选三",
                    key: "zx3",
                    amt: 0,
                    types: [
                        {
                            names: ["前三组选三"],
                            span: 24,
                            col: ["zx3top3"],
                            row: ["5", "6", "7", "8", "9", "10"],
                            oddss: [[]],
                        },
                        {
                            names: ["中三组选三"],
                            span: 24,
                            col: ["zx3mid3"],
                            row: ["5", "6", "7", "8", "9", "10"],
                            oddss: [[]],
                        },
                        {
                            names: ["后三组选三"],
                            span: 24,
                            col: ["zx3end3"],
                            row: ["5", "6", "7", "8", "9", "10"],
                            oddss: [[]],
                        },
                    ],
                },
                {
                    name: "组选六",
                    key: "zx6",
                    amt: 0,
                    types: [
                        {
                            names: ["前三组选六"],
                            span: 24,
                            col: ["zx6top3"],
                            row: ["4", "5", "6", "7", "8"],
                            oddss: [[]],
                        },
                        {
                            names: ["中三组选六"],
                            span: 24,
                            col: ["zx6mid3"],
                            row: ["4", "5", "6", "7", "8"],
                            oddss: [[]],
                        },
                        {
                            names: ["后三组选六"],
                            span: 24,
                            col: ["zx6end3"],
                            row: ["4", "5", "6", "7", "8"],
                            oddss: [[]],
                        },
                    ],
                },
                {
                    name: "复式组合",
                    key: "fs",
                    amt: 0,
                    types: [
                        {
                            names: ["复式"],
                            col: ["fs"],
                            row: ["top3", "mid3", "end3"],
                            span: 24,
                            oddss: [[]],
                        },
                    ],
                },
                {
                    name: "跨度",
                    key: "kd",
                    amt: 0,
                    types: [
                        {
                            names: ["前三跨度", "中三跨度", "后三跨度"],
                            col: ["kdtop3", "kdmid3", "kdend3"],
                            row: [
                                "0",
                                "1",
                                "2",
                                "3",
                                "4",
                                "5",
                                "6",
                                "7",
                                "8",
                                "9",
                            ],
                            span: 24,
                            oddss: [[]],
                        },
                    ],
                },
                {
                    name: "梭哈",
                    key: "sh",
                    amt: 0,
                    types: [
                        {
                            names: ["梭哈"],
                            col: ["sh"],
                            row: [
                                "5mei",
                                "4d1",
                                "hulu",
                                "3tiao",
                                "sunzi",
                                "2pair",
                                "1pair",
                                "5bukao",
                                "sanhao",
                            ],
                            span: 24,
                            oddss: [[]],
                        },
                    ],
                },
                {
                    name: "牛牛",
                    key: "nn",
                    amt: 0,
                    types: [
                        {
                            names: ["牛牛"],
                            col: ["nn"],
                            row: [
                                "n0",
                                "n1",
                                "n2",
                                "n3",
                                "n4",
                                "n5",
                                "n6",
                                "n7",
                                "n8",
                                "n9",
                                "nn",
                                "over",
                                "under",
                                "odd",
                                "even",
                            ],
                            span: 24,
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
                { oddsName: "0", oddsKey: "0" },
                { oddsName: "1", oddsKey: "1" },
                { oddsName: "2", oddsKey: "2" },
                { oddsName: "3", oddsKey: "3" },
                { oddsName: "4", oddsKey: "4" },
                { oddsName: "5", oddsKey: "5" },
                { oddsName: "6", oddsKey: "6" },
                { oddsName: "7", oddsKey: "7" },
                { oddsName: "8", oddsKey: "8" },
                { oddsName: "9", oddsKey: "9" },
            ],
            quicks2: [
                { oddsName: "豹子", oddsKey: "baozi" },
                { oddsName: "顺子", oddsKey: "sunzi" },
                { oddsName: "对子", oddsKey: "duizi" },
                { oddsName: "半顺", oddsKey: "bansun" },
                { oddsName: "杂六", oddsKey: "zaliu" },
            ],
            qiuDic: {
                qiu1: "第一球",
                qiu2: "第二球",
                qiu3: "第三球",
                qiu4: "第四球",
                qiu5: "第五球",
                t_lhh: "龙虎和",
            },
            lmDic: {
                over: "大",
                under: "小",
                odd: "单",
                even: "双",
                dragon: "龙",
                tiger: "虎",
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
                    let splitIdx = key.lastIndexOf("_");
                    let arr = [
                        key.substring(0, splitIdx),
                        key.substring(splitIdx + 1),
                    ];
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
    },
    mounted() {
        this.refreshData(this.oddss);
    },
    watch: {
        oddss: function (n, o) {
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
        showOrder(odds) {
            this.$emit("show-order", odds);
        },
        updateOdds(odds, ji) {
            this.$emit("update-odds", odds, ji);
        },
        updateOddsGroup(playKeys, oddsKey, ji) {
            this.$emit("update-odds-group", playKeys, oddsKey, ji);
        },
        updateStatus(odds, isClose) {
            this.$emit("update-status", odds, isClose);
        },
        changeGroup(index) {
            this.selectIndex = index;
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
            //console.log("logic:", logic);
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
            let oddsGroup = this.groups[this.selectIndex];
            this.$emit("change-group", oddsGroup);
            this.sortOdds();
        },
        sortOdds() {
            let oddsGroup = this.groups[this.selectIndex];
            if (this.sortBy == "YK") {
                oddsGroup.types.forEach((type) => {
                    if (type.col.length == 1) {
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
                            let av = ao ? ao.profitAmt : 0;
                            let bv = bo ? bo.profitAmt : 0;
                            let logic = av - bv;
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
                            let av = ao ? ao.betAmt : 0;
                            let bv = bo ? bo.betAmt : 0;
                            let logic = bv - av;
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
