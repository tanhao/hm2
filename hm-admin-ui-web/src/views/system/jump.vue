<template>
    <div class="buhuo">
        <a-spin :spinning="spinning" size="large">
            <a-row :gutter="16">
                <template v-if="isAgent1">
                    <div style="text-align:center">
                        <a-radio-group v-model="jumpType" @change="changeModel" button-style="solid">
                            <a-radio-button value="SZ">
                                实占
                            </a-radio-button>
                            <a-radio-button value="XZ">
                                虚占
                            </a-radio-button>
                        </a-radio-group>
                    </div>
                </template>
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
                                    <th v-if="isAgent1">类型</th>
                                    <th>下注金额</th>
                                    <th>降赔值</th>
                                    <th>最多降</th>
                                </tr>
                                <template v-for="kind in lottery.kinds">
                                    <tr :key="kind.kindId">
                                        <td class="forumrow">{{kind.kindName}}</td>
                                        <td class="forumrow" v-if="isAgent1">{{jumpTypeStr}}</td>
                                        <td class="forumrowhighlight">
                                            <a-input-number v-model.number="kind.jumpAmt" @focus="focus($event)" :min="0" size="small" />
                                        </td>
                                        <td class="forumrowhighlight">
                                            <a-input-number v-model.number="kind.jumpValue" @focus="focus($event)" :min="0" size="small" />
                                        </td>
                                        <td class="forumrowhighlight">
                                            <a-input-number v-model.number="kind.jumpMax" @focus="focus($event)" :min="0" size="small" />
                                        </td>

                                    </tr>
                                </template>
                            </table>
                            <div class="mt16" style="text-align: center">
                                <a-button type="primary" icon="save" size="small" class="mlr10" @click="updateJump(group,lottery)">
                                    保存
                                </a-button>
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
    name: "step",
    data() {
        return {
            spinning: false,
            jumpType: "XZ",
            jumpTypeStr: "虚占",
            mapUserKinds: null,
            groups: [],
            isAgent1: this.$store.state.user.info.userLevel == 3
        };
    },
    mounted() {
        this.requestJump();
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
        async changeModel() {
            this.spinning = true;
            let [err, res] = await to(
                this.$api.ctrl.updateJumpModel({ jumpType: this.jumpType })
            );
            this.spinning = false;
            this.$utils.handleThen(res, this);
            if (err || !res.success) {
                return;
            }
            this.jumpTypeStr = this.jumpType == "SZ" ? "实占" : "虚占";
        },
        async requestJump() {
            this.spinning = true;
            let [err, data] = await to(this.$api.ctrl.getJump());
            if (err || !data.success) {
                this.spinning = false;
                return;
            }
            let {
                jumpType,
                groups,
                kinds: mapKinds,
                lotterys: mapLotterys,
                userKinds: mapUserKinds
            } = data.data;
            this.mapUserKinds = mapUserKinds;
            groups.forEach(group => {
                let groupId = group.groupId;
                let lotterys = mapLotterys[groupId] || [];
                lotterys.forEach(lottery => {
                    let lotteryId = lottery.lotteryId;
                    let kinds = JSON.parse(JSON.stringify(mapKinds[groupId]));
                    kinds.forEach(kind => {
                        let kindId = kind.kindId;
                        let logic =
                            mapUserKinds[lotteryId] &&
                            mapUserKinds[lotteryId][kindId];
                        if (logic) {
                            let { jumpAmt, jumpValue, jumpMax } = logic;
                            kind.jumpAmt = jumpAmt;
                            kind.jumpValue = jumpValue;
                            kind.jumpMax = jumpMax;
                        } else {
                            kind.jumpAmt = 0;
                            kind.jumpValue = 0;
                            kind.jumpMax = 0;
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
            this.jumpType = jumpType;
            this.jumpTypeStr = this.jumpType == "SZ" ? "实占" : "虚占";
            this.groups = groups;
            this.spinning = false;
        },
        async updateJump(group, lottery) {
            let lotterys = group.lotterys.filter(lottery => lottery.isChecked);
            let lotteryIds = lotterys.map(lottery => lottery.lotteryId);
            let params = {
                lotteryIds,
                jumps: []
            };
            let lotteryId = lottery.lotteryId;
            lottery.kinds.forEach(regress => {
                let { kindId, jumpAmt, jumpValue, jumpMax } = regress;
                let [oldJumpAmt, oldJumpValue, oldJumpMax] = [0, 0, 0];
                let mapKinds = this.mapUserKinds[lotteryId];
                if (mapKinds && mapKinds[kindId]) {
                    let kind = mapKinds[kindId];
                    oldJumpAmt = kind.jumpAmt;
                    oldJumpValue = kind.jumpValue;
                    oldJumpMax = kind.jumpMax;
                }
                if (
                    jumpAmt != oldJumpAmt ||
                    jumpValue != oldJumpValue ||
                    jumpMax != oldJumpMax
                ) {
                    params.jumps.push({
                        kindId,
                        jumpAmt,
                        jumpValue,
                        jumpMax
                    });
                }
            });
            this.spinning = true;
            let [err, res] = await to(this.$api.ctrl.updateJump(params));
            this.spinning = false;
            this.$utils.handleThen(res, this);
            if (err || !res.success) {
                return;
            }
            this.requestJump();
        }
    }
};
</script>
