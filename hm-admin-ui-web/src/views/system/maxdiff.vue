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
                            <table class="tableborder odds" border="0" align="center" cellpadding="2" cellspacing="1" width="100%" style="border-collapse: separate;">
                                <tr>
                                    <th width="150" colspan="2">种类</th>
                                    <th>賺取賠差上限</th>
                                    <!--<th>操作</th>-->
                                </tr>
                                <template v-for="kind in lottery.kinds">
                                    <template v-for="(category,ctgrIndex) in kind.categorys">
                                        <tr :key="category.categoryId">
                                            <td v-if="ctgrIndex==0" :rowspan="kind.categorys.length" class="forumrow">{{kind.kindName}}</td>
                                            <td class="forumrow">{{category.categoryName}}</td>
                                            <td class="forumrowhighlight" style="text-align: left">
                                                <a-input-number v-model.number="category.maxdiff" @focus="focus($event)" size="small" style="width: 100px" :class="category.isChanged?'oddsselected':''" @change="maxdiffCheckField(kind,category,lottery.lotteryId,category.categoryId)" />
                                                ≤{{category.maxMaxdiff}}
                                            </td>
                                            <!--
                                                <td class="forumrowhighlight">
                                                    <a-button type="primary" size="small" :disabled="!kind.isDirty" @click="saveMaxdiff(group,kind)"> 保存 </a-button>
                                                </td>
                                                -->
                                        </tr>
                                    </template>
                                </template>
                            </table>
                            <div class="mt16" style="text-align: center">
                                <a-button type="primary" icon="save" size="small" class="mlr10" @click="saveMaxdiff(group, lottery)">
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
    name: "maxdiff",
    data() {
        return {
            spinning: false,
            groups: [],
            times: 0,
            timer: null,
            mapOddss: null,
            diff: null,
        };
    },
    mounted() {
        //console.log("editUserId:", this.editUserId);
        this.requestMaxdiff();
    },
    methods: {
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
        maxdiffCheckField(regress, odds, lotteryId, categoryId) {
            clearTimeout(this.timer);
            this.timer = setTimeout(() => {
                let decimals = odds.decimals;
                let key = "maxdiff";
                let maxKey = "maxMaxdiff";
                let changeKey = "isChanged";
                let originVal = this.mapOddss[lotteryId][categoryId][key];
                let changeVal = odds[key];
                let maxVal = odds[maxKey];
                let logicVal =
                    changeVal > maxVal ? maxVal : changeVal < 0 ? 0 : changeVal;

                logicVal = Math.floor(logicVal * decimals) / decimals;
                odds[key] = logicVal;
                let isChanged = originVal != logicVal;
                odds[changeKey] = isChanged;
                odds.isDirty = odds.isChanged;
            }, 500);
        },
        async requestMaxdiff() {
            this.spinning = true;
            let [err, res] = await to(this.$api.ctrl.getMaxdiff());
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
                userCategorys: mapUserCategorys,
            } = res.data;
            this.mapOddss = mapUserCategorys;
            groups.forEach((group) => {
                let groupId = group.groupId;
                let lotterys = mapLotterys[groupId]||[];
                lotterys.forEach((lottery) => {
                    let lotteryId = lottery.lotteryId;
                    let kinds = JSON.parse(JSON.stringify(mapKinds[groupId]));
                    kinds.forEach((kind) => {
                        let kindId = kind.kindId;
                        let categorys = JSON.parse(
                            JSON.stringify(mapCategorys[kindId])
                        );
                        categorys.forEach((category) => {
                            let categoryId = category.categoryId;
                            let odds = mapUserCategorys[lotteryId][categoryId];
                            category.minMaxdiff = odds.minMaxdiff;
                            category.maxdiff = odds.maxdiff;
                            category.maxMaxdiff = odds.maxMaxdiff;
                            category.isChanged = false;
                        });
                        kind.isDirty = false;
                        kind.isChanged = false;
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
        async saveMaxdiff(group, lottery) {
            let lotterys = group.lotterys.filter(
                (lottery) => lottery.isChecked
            );
            let lotteryIds = lotterys.map((lottery) => lottery.lotteryId);

            let params = {
                userId: this.editUserId,
                lotteryIds,
                maxdiffs: [],
            };
            lottery.kinds.forEach((regress) => {
                let { kindId } = regress;
                regress.categorys.forEach((category) => {
                    if (category.isChanged) {
                        let { categoryId, maxdiff } = category;
                        params.maxdiffs.push({
                            kindId,
                            categoryId,
                            maxdiff,
                        });
                    }
                });
            });
            this.spinning = true;
            let [err, res] = await to(this.$api.ctrl.updateMaxdiff(params));
            this.spinning = false;
            this.$utils.handleThen(res, this);
            if (err || !res.success) {
                return;
            }
            lotterys.forEach((lottery) => {
                let { lotteryId } = lottery;
                params.maxdiffs.forEach((odds) => {
                    let { kindId, categoryId, maxdiff } = odds;
                    let logic = this.mapOddss[lotteryId][categoryId];
                    logic.maxdiff = maxdiff;
                    let n = lottery.kinds
                        .find((kind) => kind.kindId == kindId)
                        .categorys.find(
                            (category) => category.categoryId == categoryId
                        );
                    n.minMaxdiff = maxdiff;
                    n.maxdiff = maxdiff;
                    n.isChanged = false;
                });
            });
        },
    },
    watch: {},
};
</script>

<style scoped>
</style>
