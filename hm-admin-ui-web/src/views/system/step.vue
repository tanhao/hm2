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
                            <table class="tableborder odds" border="0" align="center" cellpadding="2" cellspacing="1">
                                <tr>
                                    <th width="150">种类</th>
                                    <th>步长</th>
                                    <th>操作</th>
                                </tr>
                                <template v-for="kind in lottery.kinds">
                                    <tr :key="kind.kindId">
                                        <td class="forumrow">{{kind.kindName}}</td>
                                        <td class="forumrowhighlight">
                                            <a-input-number v-model="kind.step" :min="0" size="small" :step="0.1" />
                                        </td>
                                        <td class="forumrowhighlight">
                                            <a-button type="primary" size="small"> 保存 </a-button>
                                        </td>
                                    </tr>
                                </template>

                            </table>
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
    name: "step",
    data() {
        return {
            spinning: false,
            groups: []
        };
    },
    mounted() {
        this.requestStep();
    },
    methods: {
        changeTab(group, isCheckedAll) {
            group.isChecked = isCheckedAll;
            group.lotterys.forEach(lottery => {
                if (group.lotteryId === lottery.lotteryId) {
                    lottery.isChecked = true;
                } else {
                    lottery.isChecked = isCheckedAll;
                }
            });
        },
        async requestStep() {
            this.spinning = true;
            let [err, data] = await to(this.$api.ctrl.getStep({ userId: 2 }));
            if (err || !data.success) {
                this.spinning = false;
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
                            kind.step = logic.step;
                        } else {
                            kind.step = 0;
                        }
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
        }
    }
};
</script>

<style scoped>
</style>