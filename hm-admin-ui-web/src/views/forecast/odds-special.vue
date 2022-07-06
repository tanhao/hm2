<template>
    <table class="tableborder" border="0" align="center" cellpadding="1" cellspacing="1" style="border-collapse: separate;width: 100%;">
        <tr>
            <th width="45px">种类</th>
            <template v-for="(name,ni) in oddsType.names">
                <th :key="'c1_'+ni">{{name}}</th>
                <th :key="'c2_'+ni" v-if="canCloseOpen" width="22px"></th>
            </template>
            <th v-if="oddsType.names.length>1&&canEdit" width="40px">群改</th>
        </tr>
        <template v-for="(oddss,ri) in oddsType.oddss">
            <tr :key="ri">
                <template v-for="(odds,ci) in oddss">
                    <td v-if="ci==0" :key="'c1_'+ci" class="forumrow">{{oddss[0].oddsName}}</td>
                    <td :key="'c2_'+ci" class="forumrowhighlight ">
                        <template v-if="odds.categoryId">
                            <div>
                                <img v-if="canEdit" class="fl" :src="plus" @click.stop="updateOdds(odds,1)">
                                {{finalOdds(odds)}}
                                <img v-if="canEdit" class="fr" :src="minus" @click.stop="updateOdds(odds,-1)">
                            </div>
                            <div>
                                <span class="green" @click="showBuhuo(odds,oddsType.names[ci],baseOdds(odds))"> {{betAmt(odds)}}</span> / <span class="red" @click="showBuhuo(odds,oddsType.names[ci],baseOdds(odds))"> {{profitAmt(odds)}}</span>
                            </div>
                        </template>
                    </td>
                    <td :key="'c3_'+ci" class="forumrow tabletop" v-if="canCloseOpen">
                        <div style="width: 22px;margin: 0 auto">
                            <div class="switch">
                                <div v-show="!isClose(odds)" class="on" @click="updateStatus(odds,true)"></div>
                                <div v-show="isClose(odds)" class="off" @click="updateStatus(odds,false)"></div>
                            </div>
                        </div>
                    </td>
                </template>
                <template v-if="oddsType.names.length>1&&canEdit">
                    <td class="forumrow tabletop">
                        <img class="fl" :src="plus" @click="updateOddsGroup(oddsType.col,oddsType.row[ri],1)">
                        <img class="fr" :src="minus" @click="updateOddsGroup(oddsType.col,oddsType.row[ri],-1)">
                    </td>
                </template>
            </tr>
        </template>
    </table>
</template>
<script>
import minus from "@/assets/AdminDefaultTheme/Images/minus.png";
import plus from "@/assets/AdminDefaultTheme/Images/plus.png";

export default {
    name: "odds-special",
    props: {
        oddsType: Object,
        userOddss: Object,
        userOddsNows: Object,
        userOddsJumps: Object,
        userOddsCljps: Object,
        userOddsCloses: Object,
        userStats: Object,
        canEdit: Boolean,
        canCloseOpen: Boolean,
        sortBy: String,
    },
    data() {
        return {
            plus,
            minus,
            timers: {},
        };
    },
    computed: {
        finalOdds(odds) {
            return (odds) => {
                let { categoryId, oddsId } = odds;
                let o1 = this.userOddss[categoryId].reduce(
                    (pre, cur) => pre + cur,
                    0
                );
                let now = this.userOddsNows[oddsId];
                let o2 = now
                    ? now.reduce(
                          (pre, cur) => pre + cur,
                          0
                      )
                    : 0;
                let jump = this.userOddsJumps[oddsId];
                let o3 = jump
                    ? jump.reduce(
                          (pre, cur) => pre + cur,
                          0
                      )
                    : 0;
                let cljp = this.userOddsCljps[oddsId];
                let o4 = cljp
                    ? cljp.reduce(
                          (pre, cur) => pre + cur,
                          0
                      )
                    : 0;
                return Math.round((o1 + o2 + o3 + o4) * 100000) / 100000;
            };
        },
        baseOdds(odds) {
            return (odds) => {
                let { categoryId, oddsId } = odds;
                let o1 = this.userOddss[categoryId].reduce(
                    (pre, cur) => pre + cur,
                    0
                );
                let now = this.userOddsNows[oddsId];
                let o2 = now
                    ? now
                            .slice(0, now.length - 1)
                            .reduce((pre, cur) => pre + cur,0)
                    : 0;
                let jump = this.userOddsJumps[oddsId];
                let o3 = jump
                    ? jump
                            .slice(0, jump.length - 1)
                            .reduce((pre, cur) => pre + cur,0)
                    : 0;
                let cljp = this.userOddsCljps[oddsId];
                let o4 = cljp
                    ? cljp
                            .slice(0, cljp.length - 1)
                            .reduce((pre, cur) => pre + cur, 0)
                    : 0;
                return Math.round((o1 + o2 + o3 + o4) * 100000) / 100000;
            };
        },
        betAmt(odds) {
            return (odds) => {
                let obj = this.userStats[odds.oddsId];
                let amt = obj ? obj.betAmt : 0;
                return amt.toFixed(2);
            };
        },
        profitAmt(odds) {
            return (odds) => {
                let obj = this.userStats[odds.oddsId];
                let amt = obj ? obj.profitAmt : 0;
                return amt.toFixed(2);
            };
        },
        isClose(odds) {
            return (odds) => {
                return this.userOddsCloses[odds.oddsId];
            };
        },
    },
    mounted() {},
    watch: {},
    methods: {
        showBuhuo(odds, typeName, oddsVal) {
            let params = {
                oddsId: odds.oddsId,
                name: typeName,
                odds: oddsVal,
                oddsName:odds.oddsName
            };
            this.$emit("show-buhuo", params);
        },
        showOrder(odds) {
            this.$emit("show-order", odds);
        },
        updateOdds(odds, ji) {
            let { oddsId } = odds;
            if (!this.timers[oddsId]) {
                this.timers[oddsId] = { id: null, dj: 0 };
            }
            let timer = this.timers[oddsId];
            timer.dj = timer.dj + 1;
            clearTimeout(timer.id);
            timer.id = setTimeout(() => {
                this.$emit("update-odds", odds, ji * timer.dj);
                timer.dj = 0;
            }, 500);
        },
        updateOddsGroup(playKeys, oddsKey, ji) {
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
        sortOdds() {
            if (this.sortBy == "YK") {
                this.oddsType.oddss.sort((a, b) => a.profitAmt - b.profitAmt);
            } else if (this.sortBy == "JE") {
                this.oddsType.oddss.sort((a, b) => b.betAmt - a.betAmt);
            } else if (this.sortBy == "HM") {
                this.oddsType.oddss.sort((a, b) => a.ordering - b.ordering);
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
