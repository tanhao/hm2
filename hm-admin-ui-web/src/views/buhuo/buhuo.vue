<template>
    <div class="buhuo">
        <a-spin :spinning="spinning">
            <a-row :gutter="16">
                <a-col :span="24" v-for="group in groups" :key="group.groupId">
                    <a-divider orientation="left">
                        {{group.groupName}}
                    </a-divider>
                    <a-tabs v-model="group.lotteryId" @change="changeTab(group,false)">
                        <a-tab-pane v-for="lottery in group.lotterys" :key="lottery.lotteryId">
                            <span slot="tab">
                                <a-checkbox v-model="lottery.isChecked" class="mlr10"></a-checkbox>
                                <span>{{lottery.lotteryName}}</span>
                            </span>
                            <table class="tableborder" border="0" align="center" cellpadding="5" cellspacing="1">
                                <tbody>
                                    <tr>
                                        <th>种类</th>
                                        <th>计算方式</th>
                                        <th>控制额度</th>
                                        <th>
                                            <a-checkbox @change="changeAll(lottery)" v-model="lottery.isActive">
                                                <span v-if="lottery.isActive">开启</span> <span v-else>关闭</span>
                                            </a-checkbox>
                                        </th>
                                        <th>种类</th>
                                        <th>计算方式</th>
                                        <th>控制额度</th>
                                        <th>
                                            <a-checkbox @change="changeAll(lottery)" v-model="lottery.isActive">
                                                <span v-if="lottery.isActive">开启</span> <span v-else>关闭</span>
                                            </a-checkbox>
                                        </th>
                                    </tr>
                                    <template v-for="(kind,index) in lottery.kinds">
                                        <tr :key="index" v-if="index%2==0">
                                            <td class="forumrow blue">{{lottery.kinds[index].kindName}}</td>
                                            <td class="forumrowhighlight">下注额</td>
                                            <td class="forumrowhighlight">
                                                {{minVal(lottery.lotteryId,lottery.kinds[index].kindId)}}
                                                &lt;=
                                                <a-input-number v-model="lottery.kinds[index].buhuoValue" @focus="focus($event)" :min="0" :precision="0" size="small" />
                                            </td>
                                            <td class="forumrowhighlight">
                                                <a-checkbox v-model="lottery.kinds[index].isActive">
                                                    <span v-if="lottery.kinds[index].isActive">开启</span> <span v-else>关闭</span>
                                                </a-checkbox>
                                            </td>
                                            <template v-if="lottery.kinds[index+1]">
                                                <td class="forumrow blue">{{lottery.kinds[index+1].kindName}}</td>
                                                <td class="forumrowhighlight">下注额</td>
                                                <td class="forumrowhighlight">
                                                    {{minVal(lottery.lotteryId,lottery.kinds[index+1].kindId)}}
                                                    &lt;=
                                                    <a-input-number v-model="lottery.kinds[index+1].buhuoValue" @focus="focus($event)" :min="0" :precision="0" size="small" />
                                                </td>
                                                <td class="forumrowhighlight">
                                                    <a-checkbox v-model="lottery.kinds[index+1].isActive">
                                                        <span v-if="lottery.kinds[index+1].isActive">开启</span> <span v-else>关闭</span>
                                                    </a-checkbox>
                                                </td>
                                            </template>
                                            <template v-else>
                                                <td class="forumrowhighlight" colspan="4"></td>
                                            </template>
                                        </tr>
                                        <!--
                                        <tr :key="kind.kindId">
                                            <td class="forumrow blue">{{kind.kindName}}</td>
                                            <td class="forumrowhighlight">下注额</td>
                                            <td class="forumrowhighlight">
                                                {{minVal(lottery.lotteryId,kind.kindId)}}
                                                &lt;=
                                                <a-input-number v-model="kind.buhuoValue" @focus="focus($event)" :min="0" :precision="0" size="small" />
                                            </td>
                                            <td class="forumrowhighlight">
                                                <a-checkbox v-model="kind.isActive">
                                                    <span v-if="kind.isActive">开启</span> <span v-else>关闭</span>
                                                </a-checkbox>
                                            </td>
                                        </tr>
                                        -->
                                    </template>
                                </tbody>
                            </table>
                            <div class="mt16" style="text-align: center">
                                <a-button type="primary" icon="save" size="small" class="mlr10" @click="updateBuhuo(group,lottery)">
                                    保存
                                </a-button>
                                <a-button size="small" type="primary" @click="refreshPage()">刷新</a-button>
                            </div>
                        </a-tab-pane>
                        <a-button class="mr10" size="small" type="primary" slot="tabBarExtraContent" @click="changeTab(group,!group.isChecked)">
                            <span v-if="!group.isChecked">全选</span><span v-else>取消</span>
                        </a-button>
                    </a-tabs>

                </a-col>
            </a-row>

        </a-spin>
    </div>
</template>

<script>
import to from "await-to-js";
export default {
    name: "buhuo",
    data() {
        return {
            spinning: false,
            mapUserKinds: {},
            groups: [],
            buhuoStats: {},
        };
    },
    mounted() {
        this.requestBuhou();
    },
    computed: {
        minVal(lotteryId, kindId) {
            return (lotteryId, kindId) => {
                let lottery = this.buhuoStats[lotteryId];
                if (lottery) {
                    let kind = lottery[kindId];
                    return kind ? kind.amt : 0;
                } else {
                    return 0;
                }
            };
        },
    },
    methods: {
        refreshPage() {
            this.requestBuhou();
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
        changeAll(lottery) {
            let checked = lottery.isActive;
            lottery.kinds.forEach((kinds) => {
                kinds.isActive = checked;
            });
        },
        async requestBuhou() {
            this.spinning = true;
            let [err, data] = await to(this.$api.ctrl.getBuhuo());
            if (err || !data.success) {
                this.spinning = false;
                return;
            }
            let {
                groups,
                kinds: mapKinds,
                lotterys: mapLotterys,
                userKinds: mapUserKinds,
                buhuoStats,
            } = data.data;
            this.buhuoStats = buhuoStats;
            this.mapUserKinds = mapUserKinds;
            groups.forEach((group) => {
                let groupId = group.groupId;
                let lotterys = mapLotterys[groupId] ||[];
                lotterys.forEach((lottery) => {
                    let lotteryId = lottery.lotteryId;
                    let kinds = JSON.parse(JSON.stringify(mapKinds[groupId]));
                    kinds.forEach((kind) => {
                        let kindId = kind.kindId;
                        let logic =
                            mapUserKinds[lotteryId] &&
                            mapUserKinds[lotteryId][kindId];
                        if (logic) {
                            let { isActive, buhuoValue } = logic;
                            kind.isActive = isActive;
                            kind.buhuoValue = buhuoValue;
                        } else {
                            kind.isActive = false;
                            kind.buhuoValue = 0;
                            this.mapUserKinds[lotteryId] = {};
                        }
                        kind.isChecked = false;
                    });
                    lottery.isChecked = false;
                    lottery.isActive = false;
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
        async updateBuhuo(group, lottery) {
            let lotterys = group.lotterys.filter(
                (lottery) => lottery.isChecked
            );
            let lotteryIds = lotterys.map((lottery) => lottery.lotteryId);
            let params = {
                lotteryIds,
                buhuos: [],
            };
            lottery.kinds.forEach((kind) => {
                let { kindId, isActive, buhuoValue } = kind;
                params.buhuos.push({
                    kindId,
                    isActive,
                    buhuoValue,
                });
            });

            this.spinning = true;
            let [err, res] = await to(this.$api.ctrl.updateBuhuo(params));
            this.spinning = false;
            this.$utils.handleThen(res, this);
            if (err || !res.success) {
                return;
            }
            this.requestBuhou();
        },
    },
};
</script>

<style scoped>
</style>
