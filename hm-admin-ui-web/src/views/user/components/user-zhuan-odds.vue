<template>
<div>

    <a-modal :footer="null" :title="'修改赚赔：'+editUsername" :width="1003" :visible="userZhuanOddsShow" :body-style="{ paddingBottom: '0px'}" :maskClosable="maskClosable" dialogClass="tk" @cancel="onClose">
        <div class="buhuo">
            <a-spin :spinning="spinning">
                <a-row :gutter="8">
                    <a-col style="float: right;display: table;width: auto;">
                        <template v-if="!isJustLook">
                            <!--
                            赚取赔差：
                            <a-input v-model.number="diff" size="small" style="width: 120px" />
                            -->
                            <a-button type="primary" icon="setting" size="small" class="mlr5" @click="quiskSetting">
                                快速设置
                            </a-button>
                        </template>
                        <a-button type="primary" icon="edit" size="small" @click="zhuanOddsLogShow=true">
                            赚赔日志
                        </a-button>
                    </a-col>
                </a-row>
                <a-row :gutter="16">
                     <template v-for="group in groups" >
                        <a-col :span="24" :key="group.groupId" v-if="group.lotterys && group.lotterys.length>0">
                        <a-divider orientation="left">
                            {{group.groupName}}
                        </a-divider>
                        <a-tabs v-model="group.lotteryId" @change="changeTab(group,false)">
                            <a-tab-pane v-for="lottery in group.lotterys" :key="lottery.lotteryId">
                                <span slot="tab">
                                    <a-checkbox v-model="lottery.isChecked" class="mlr5"></a-checkbox>
                                    <span>{{lottery.lotteryName}}</span>
                                </span>
                                <table class="tableborder odds" border="0" align="center" cellpadding="2" cellspacing="1" width="100%" style="border-collapse: separate;">
                                    <template v-if="isJustLook">
                                        <tr>
                                            <th rowspan="2" colspan="2">种类</th>
                                            <th :colspan="colspan">上级赔率</th>
                                            <th rowspan="2">赚赔</th>
                                            <th :colspan="colspan">赚赔后</th>
                                        </tr>
                                        <tr>
                                            <th v-if="markets.A">A盘</th>
                                            <th v-if="markets.B">B盘</th>
                                            <th v-if="markets.C">C盘</th>
                                            <th v-if="markets.D">D盘</th>
                                            <th v-if="markets.A">A盘</th>
                                            <th v-if="markets.B">B盘</th>
                                            <th v-if="markets.C">C盘</th>
                                            <th v-if="markets.D">D盘</th>
                                        </tr>
                                        <template v-for="kind in lottery.kinds">
                                            <template v-for="(category,ctgrIndex) in kind.categorys">
                                                <tr :key="category.categoryId">
                                                    <td v-if="ctgrIndex==0" :rowspan="kind.categorys.length" class="forumrow" style="width: 80px">{{kind.kindName}}</td>
                                                    <td class="forumrow" style="width: 80px">{{category.categoryName}}</td>
                                                    <td class="forumrowhighlight">
                                                        {{category.oddsA}}
                                                    </td>
                                                    <td class="forumrowhighlight">
                                                        {{category.oddsB}}
                                                    </td>
                                                    <td class="forumrowhighlight">
                                                        {{category.oddsC}}
                                                    </td>
                                                    <td class="forumrowhighlight">
                                                        {{category.oddsD}}
                                                    </td>
                                                    <td class="forumrowhighlight">
                                                        {{category.diff}}
                                                    </td>
                                                    <td class="forumrowhighlight">
                                                        {{formatFloat(category.oddsA-category.diff,4)}}
                                                    </td>
                                                    <td class="forumrowhighlight">
                                                        {{formatFloat(category.oddsB-category.diff,4)}}
                                                    </td>
                                                    <td class="forumrowhighlight">
                                                        {{formatFloat(category.oddsC-category.diff,4)}}
                                                    </td>
                                                    <td class="forumrowhighlight">
                                                        {{formatFloat(category.oddsD-category.diff,4)}}
                                                    </td>
                                                </tr>
                                            </template>
                                        </template>

                                    </template>
                                    <template v-else>
                                        <tr>
                                            <th rowspan="2" colspan="2">种类</th>
                                            <th :colspan="colspan">上级赔率</th>
                                            <th rowspan="2">赔差</th>
                                            <th :colspan="colspan">赚赔后</th>

                                        </tr>
                                        <tr>
                                            <th v-if="markets.A">A盘</th>
                                            <th v-if="markets.B">B盘</th>
                                            <th v-if="markets.C">C盘</th>
                                            <th v-if="markets.D">D盘</th>
                                            <th v-if="markets.A">A盘</th>
                                            <th v-if="markets.B">B盘</th>
                                            <th v-if="markets.C">C盘</th>
                                            <th v-if="markets.D">D盘</th>
                                        </tr>
                                        <template v-for="kind in lottery.kinds">
                                            <template v-for="(category,ctgrIndex) in kind.categorys">
                                                <tr :key="category.categoryId">
                                                    <td v-if="ctgrIndex==0" :rowspan="kind.categorys.length" class="forumrow" style="width: 80px">{{kind.kindName}}</td>
                                                    <td class="forumrow" style="width: 80px">{{category.categoryName}}</td>
                                                    <td class="forumrowhighlight">
                                                        {{category.oddsA}}
                                                    </td>
                                                    <td class="forumrowhighlight">
                                                        {{category.oddsB}}
                                                    </td>
                                                    <td class="forumrowhighlight">
                                                        {{category.oddsC}}
                                                    </td>
                                                    <td class="forumrowhighlight">
                                                        {{category.oddsD}}
                                                    </td>
                                                    <td class="forumrowhighlight">
                                                        <a-input size="small" v-model.number="category.diff" @focus="focus($event)" style="width: 55px" :class="category.isChanged?'oddsselected':''" @change="zhuanOddsCheckField(kind,category,lottery.lotteryId,category.categoryId,'A')" />
                                                    </td>
                                                    <td class="forumrowhighlight">
                                                        {{formatFloat(category.oddsA-category.diff,4)}}
                                                    </td>
                                                    <td class="forumrowhighlight">
                                                        {{formatFloat(category.oddsB-category.diff,4)}}
                                                    </td>
                                                    <td class="forumrowhighlight">
                                                        {{formatFloat(category.oddsC-category.diff,4)}}
                                                    </td>
                                                    <td class="forumrowhighlight">
                                                        {{formatFloat(category.oddsD-category.diff,4)}}
                                                    </td>
                                                </tr>
                                            </template>
                                        </template>
                                    </template>
                                </table>
                                <div class="mt16" style="text-align: center">
                                    <a-button type="" icon="close" size="small" class="mlr10" @click="onClose">
                                        关闭
                                    </a-button>
                                    <a-button type="primary" icon="save" size="small" class="mlr10" @click="saveZhuanOdds(group,lottery)">
                                        保存
                                    </a-button>
                                </div>
                            </a-tab-pane>
                            <a-button class="mr10" size="small" type="primary" slot="tabBarExtraContent" @click="changeTab(group,!group.isChecked)">
                                <span v-if="!group.isChecked">全选</span><span v-else>取消</span>
                            </a-button>
                        </a-tabs>
                    </a-col>
                     </template>
                </a-row>
            </a-spin>
        </div>
        <!--        <div class="opnewinright">-->
        <!--            <a-button type="primary" size="small" @click="onClose">-->
        <!--                关闭-->
        <!--            </a-button>-->
        <!--        </div>-->
    </a-modal>
    <zhuan-odds-log :zhuanOddsLogShow.sync="zhuanOddsLogShow" :editUserId.sync="editUserId" :editUsername.sync="editUsername"></zhuan-odds-log>
</div>
</template>

<script>
import to from "await-to-js";
import ZhuanOddsLog from "./zhuan-odds-log";
export default {
    components: { ZhuanOddsLog },
    name: "user-zhuan-odds",
    props: {
        userZhuanOddsShow: {
            type: Boolean,
        },
        editUserId: null,
        editUsername: null,
    },
    data() {
        return {
            maskClosable: false,
            spinning: false,
            groups: [],
            markets: {},
            times: 0,
            timer: null,
            mapOddss: null,
            diff: null,
            colspan: 0,
            isJustLook: this.$store.state.user.info.userLevel == 1,
            zhuanOddsLogShow:false,
        };
    },
    mounted() {
        //console.log("editUserId:", this.editUserId);
        this.requestZhuanOdds();
    },
    computed: {},
    methods: {
        onClose() {
            this.$emit("update:userZhuanOddsShow", false);
        },
        formatFloat(f, digit) {
            var m = Math.pow(10, digit);
            return Math.round(f * m, 10) / m;
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
        zhuanOddsCheckField(regress, odds, lotteryId, categoryId) {
            clearTimeout(this.timer);
            this.timer = setTimeout(() => {
                let originVal = this.mapOddss[lotteryId][categoryId]["diff"];
                let changeVal = odds.diff;
                let baseVal = odds.maxdiff;
                let logicVal =
                    changeVal > baseVal
                        ? baseVal
                        : changeVal < 0
                        ? 0
                        : changeVal;
                odds.diff = logicVal;
                odds.isChanged = originVal != logicVal;
                this.times = 0;
            }, 500);
        },
        quiskSetting() {
            /*
            if (this.diff == null || this.diff == "" || this.diff < 0) {
                return;
            }
            */
            ["A", "B", "C", "D"].forEach((market) => {
                this.groups.forEach((group) => {
                    group.lotterys.forEach((lottery) => {
                        let { lotteryId, kinds } = lottery;
                        kinds.forEach((kind) => {
                            kind.categorys.forEach((category) => {
                                let {
                                    categoryId,
                                    decimals,
                                    maxdiff,
                                } = category;
                                /*
                                let newVal = Math.max(
                                    Math.min(this.diff, maxdiff),
                                    0
                                );
                                newVal =
                                    Math.floor(newVal * decimals) / decimals;
                                */
                                let newVal = maxdiff;
                                category.diff = newVal;
                                let originVal = this.mapOddss[lotteryId][
                                    categoryId
                                ]["diff"];
                                category.isChanged = newVal != originVal;
                            });
                        });
                    });
                });
            });
        },
        async requestZhuanOdds() {
            if (this.editUserId == 0 || !this.userZhuanOddsShow) {
                return;
            }
            this.spinning = true;
            let [err, res] = await to(
                this.$api.ctrl.getZhuanOdds({
                    userId: this.editUserId,
                })
            );
            if (err || !res.success) {
                this.spinning = false;
                this.$swal({
                    title: err ? err.message : res.message,
                    icon: "error",
                    confirmButtonText: "确定",
                });
                return;
            }
            let {
                groups,
                kinds: mapKinds,
                categorys: mapCategorys,
                lotterys: mapLotterys,
                userCategorys: mapUserCategorys,
                markets,
            } = res.data;
            this.mapOddss = mapUserCategorys;
            this.markets = markets;
            let { A, B, C, D } = markets;
            this.colspan =
                (A ? 1 : 0) + (B ? 1 : 0) + (C ? 1 : 0) + (D ? 1 : 0);
            groups.forEach((group) => {
                let groupId = group.groupId;
                let lotterys = mapLotterys[groupId] || [];
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
                            Object.assign(category, odds);
                            category.isChanged = false;
                        });
                        kind.isDirty = false;
                        kind.categorys = categorys;
                    });
                    lottery.isChecked = true;
                    lottery.kinds = kinds;
                });
                //lotterys[0].isChecked = true;
                if(lotterys.length>0){
                    group.lotteryId = lotterys[0].lotteryId;
                }
                group.isChecked = true;
                group.lotterys = lotterys;
              
            });
            this.groups = groups;
            this.spinning = false;
        },
        async saveZhuanOdds(group, lottery) {
            let lotterys = group.lotterys.filter(
                (lottery) => lottery.isChecked
            );
            let lotteryIds = lotterys.map((lottery) => lottery.lotteryId);

            let params = {
                userId: this.editUserId,
                lotteryIds,
                oddss: [],
            };

            lottery.kinds.forEach((regress) => {
                let { kindId } = regress;
                regress.categorys.forEach((category) => {
                    if (category.isChanged) {
                        let { categoryId, diff } = category;
                        params.oddss.push({ kindId, categoryId, oddsA: diff });
                    }
                });
            });

            this.spinning = true;
            let [err, res] = await to(this.$api.ctrl.updateZhuanOdds(params));
            this.spinning = false;
            this.$utils.handleThen(res, this);
            if (err || !res.success) {
                return;
            }
            lotterys.forEach((lottery) => {
                let { lotteryId } = lottery;
                params.oddss.forEach((odds) => {
                    let { kindId, categoryId, oddsA } = odds;
                    let logic = this.mapOddss[lotteryId][categoryId];
                    logic.diff = oddsA;
                    let n = lottery.kinds
                        .find((kind) => kind.kindId == kindId)
                        .categorys.find(
                            (category) => category.categoryId == categoryId
                        );
                    n.diff = oddsA;
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
