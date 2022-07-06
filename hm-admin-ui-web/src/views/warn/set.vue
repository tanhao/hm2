<template>
    <div class="set">
        <a-spin :spinning="spinning">
            <div class="mb10">
                <template v-for="group in groups">
                    <template v-for="lottery in group.lotterys">
                        <a-button size="small" :type="lottery.lotteryId==lotteryId?'primary':''" :key="lottery.lotteryId" @click="changeLottery(lottery)">{{lottery.lotteryName}}</a-button>
                    </template>
                </template>

            </div>
            <div class="mb10 df">
                <span class="maintxt mlr10">第一次金额警示:</span>
                <a-input size="small" :value="0" style="width: 120px" />
                <span class="maintxt mlr10">增加此金额循环警示:</span>
                <a-input size="small" :value="0" style="width: 120px" />
                <!--
                <div class="pl10">
                    <a-checkbox>全部选择</a-checkbox>
                </div>
                -->
                <div class="pl10">
                    <a-button type="primary" icon="edit" size="small">
                        快速设置
                    </a-button>
                </div>
                <div class="pl10">
                    <a-button type="primary" icon="save" size="small">
                        保存
                    </a-button>
                </div>
            </div>
            <table class="tableborder" border="0" align="center" cellpadding="5" cellspacing="1">
                <tbody>
                    <tr>
                        <!--
                        <th>
                            <a-checkbox></a-checkbox>
                        </th>
                        -->
                        <th>种类</th>
                        <th>第一次金额警示</th>
                        <th>增加此金额循环警示</th>

                    </tr>
                    <tr v-for="kind in kinds" :key="kind.kindId">
                        <!--
                        <td class="forumrow">
                            <a-checkbox></a-checkbox>
                        </td>
                        -->
                        <td class="forumrowhighlight">{{kind.kindName}}</td>
                        <td class="forumrowhighlight">
                            <a-input size="small" :value="kind.firstAmt" style="width: 55px" />
                        </td>
                        <td class="forumrowhighlight">
                            <a-input size="small" :value="kind.loopAmt" style="width: 55px" />

                        </td>
                        <!--<span class="mlr10">
                                <a-button type="primary" icon="save" size="small"> 保存 </a-button>
                            </span>-->
                    </tr>

                </tbody>
            </table>

        </a-spin>
    </div>
</template>

<script>
import to from "await-to-js";
export default {
    name: "set",
    data() {
        return {
            lotteryId: null,
            kinds: [],
            groups: [],
            spinning: false
        };
    },
    mounted() {
        this.requestWarn();
    },
    methods: {
        changeLottery(lottery) {
            this.lotteryId = lottery.lotteryId;
            this.kinds = lottery.kinds;
        },
        async requestWarn() {
            this.spinning = true;
            let [err, data] = await to(this.$api.ctrl.getWarn({ userId: 2 }));
            if (err || !data.success) {
                return;
            }
            let {
                groups,
                kinds: mapKinds,
                lotterys: mapLotterys,
                userKinds: mapUserKinds
            } = data.data;

            groups.forEach(group => {
                let groupId = group.groupId;
                let lotterys = mapLotterys[groupId]||[];
                lotterys.forEach(lottery => {
                    let lotteryId = lottery.lotteryId;
                    let kinds = JSON.parse(JSON.stringify(mapKinds[groupId]));
                    kinds.forEach(kind => {
                        let kindId = kind.kindId;
                        let logic =
                            mapUserKinds[lotteryId] &&
                            mapUserKinds[lotteryId][kindId];
                        if (logic) {
                            let { firstAmt, loopAmt } = logic;
                            kind.firstAmt = firstAmt;
                            kind.loopAmt = loopAmt;
                        } else {
                            kind.firstAmt = 0;
                            kind.loopAmt = 0;
                        }
                    });
                    lottery.kinds = kinds;
                });
                if(lotterys.length>0){
                     group.lotteryId = lotterys[0].lotteryId;
                }
                group.lotterys = lotterys;
            });
            let lottery = groups[0].lotterys[0];
            lottery.isChecked = true;
            this.groups = groups;
            this.lotteryId = lottery.lotteryId;
            this.kinds = lottery.kinds;
            this.spinning = false;
        }
    }
};
</script>

<style scoped>
</style>