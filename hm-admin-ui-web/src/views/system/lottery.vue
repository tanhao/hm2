<template>
    <div class="cpgn">
        <a-spin :spinning="spinning">
            <div class="red dstable mb10 font14" v-if="info.userLevel<=2">
                <a-button type="primary" size="small" class="mr10" @click="updateOrdering">
                    保存排序
                </a-button>
                提示:选中彩种拖动可以直接进行排序
            </div>

            <table class="tableborder" border="0" align="center" cellpadding="5" cellspacing="1">
                <!--
               <tr>
                        <th colspan="9" class="titbg">自开彩</th>
                </tr>
                -->
                <tr>
                    <th>排序</th>
                    <th>名称</th>
                    <th v-if="info.userLevel<=2">操作</th>
                    <th>当前期数</th>
                    <th>开盘时间</th>
                    <th>封盘时间</th>
                    <th>设置开盘时间</th>
                    <th>设置封盘时间</th>
                </tr>
                <template v-if="info.userLevel<=2">
                    <draggable v-model="lotterys" tag="tbody">
                        <template v-for="(lottery,lotteryIndex) in lotterys">
                            <a-tooltip placement="topRight" title="当鼠标经过变成四角箭头时,可以拖动本行进行排序" :key="lottery.lotteryId">
                                <tr :key="lottery.lotteryId">
                                    <td class="forumrow">{{lotteryIndex+1}}</td>
                                    <td class="forumrow">{{lottery.lotteryName}}</td>
                                    <td v-if="info.userLevel<=2" class="forumrowhighlight">
                                        <span class="mlr10" v-if="info.userLevel==2">
                                            <a-switch v-model="lottery.isOpenSelf" @change="closeOrOpen(lottery)" checked-children="开" un-checked-children="关" default-checked />
                                        </span>
                                        <a-button type="primary" class="mr10" icon="edit" size="small" @click="showDelayModal(lottery)">
                                            延时
                                        </a-button>
                                        <template v-if="info.userLevel==1&&lottery.isZkc&&(info.username=='jin'||info.username=='hm88')">
                                            <a-radio-group v-model="lottery.openModel" @change="changeOpenModel(lottery)">
                                                <a-radio value="MANUAL">
                                                    手动
                                                </a-radio>
                                                <a-radio value="AUTO">
                                                    自动
                                                </a-radio>
                                            </a-radio-group>
                                        </template>
                                    </td>
                                    <td class="forumrowhighlight">{{lottery.gameNo}}</td>
                                    <td class="forumrowhighlight">
                                        <span v-if="lottery.openTime">
                                            {{moment(lottery.openTime*1000).format('YYYY-MM-DD HH:mm:ss')  }}
                                        </span>
                                    </td>
                                    <td class="forumrowhighlight">
                                        <span v-if="lottery.actionTime">
                                            {{moment(lottery.actionTime*1000).format('YYYY-MM-DD HH:mm:ss') }}
                                        </span>
                                    </td>
                                    <td class="forumrowhighlight">
                                        <span v-if="lottery.openTime">
                                            {{moment((lottery.openTime+lottery.delayOpen)*1000).format('YYYY-MM-DD HH:mm:ss')}}
                                        </span>
                                    </td>
                                    <td class="forumrowhighlight">
                                        <span v-if="lottery.actionTime">
                                            {{moment((lottery.actionTime-lottery.precedeClose)*1000).format('YYYY-MM-DD HH:mm:ss')}}
                                        </span>
                                    </td>
                                </tr>
                            </a-tooltip>
                        </template>
                    </draggable>
                </template>
                <template v-else>
                    <template v-for="(lottery,lotteryIndex) in lotterys">
                        <a-tooltip placement="topRight" title="当鼠标经过变成四角箭头时,可以拖动本行进行排序" :key="lottery.lotteryId">
                            <tr :key="lottery.lotteryId">
                                <td class="forumrow">{{lotteryIndex+1}}</td>
                                <td class="forumrow">{{lottery.lotteryName}}</td>
                                <td v-if="info.userLevel<=2" class="forumrowhighlight">
                                    <span class="mlr10" v-if="info.userLevel==2">
                                        <a-switch v-model="lottery.isOpenSelf" @change="closeOrOpen(lottery)" checked-children="开" un-checked-children="关" default-checked />
                                    </span>
                                    <a-button type="primary" icon="edit" size="small" @click="showDelayModal(lottery)">
                                        延时
                                    </a-button>
                                </td>
                                <td class="forumrowhighlight">{{lottery.gameNo}}</td>
                                <td class="forumrowhighlight">
                                    <span v-if="lottery.openTime">
                                        {{moment(lottery.openTime*1000).format('YYYY-MM-DD HH:mm:ss')  }}
                                    </span>
                                </td>
                                <td class="forumrowhighlight">
                                    <span v-if="lottery.actionTime">
                                        {{moment(lottery.actionTime*1000).format('YYYY-MM-DD HH:mm:ss') }}
                                    </span>
                                </td>
                                <td class="forumrowhighlight">
                                    <span v-if="lottery.openTime">
                                        {{moment((lottery.openTime+lottery.delayOpen)*1000).format('YYYY-MM-DD HH:mm:ss')}}
                                    </span>
                                </td>
                                <td class="forumrowhighlight">
                                    <span v-if="lottery.actionTime">
                                        {{moment((lottery.actionTime-lottery.precedeClose)*1000).format('YYYY-MM-DD HH:mm:ss')}}
                                    </span>
                                </td>
                            </tr>
                        </a-tooltip>
                    </template>
                </template>
            </table>

            <!--弹出窗口-->

            <a-modal width="300" v-model="groupDelayModal" :title="lotteryName" centered @ok="updateDelay">
                <div>
                    <table class="" border="0" align="center" cellpadding="8" cellspacing="1" style="border-collapse: separate;width: 100%">
                        <tbody>
                            <tr>
                                <td class="txtright">延时开盘</td>
                                <td class="">
                                    <a-input v-model.number="delayForm.delayOpen" style="width: 80px" size="small" />
                                </td>
                            </tr>
                            <tr>
                                <td class="txtright">提前关盘</td>
                                <td class="">
                                    <a-input v-model.number="delayForm.precedeClose" style="width: 80px" size="small" />
                                </td>
                            </tr>
                            <tr>
                                <td class="txtright">正码提前关盘</td>
                                <td class="">
                                    <a-input v-model.number="delayForm.zmPrecedeClose" style="width: 80px" size="small" />
                                </td>
                            </tr>

                        </tbody>
                    </table>
                </div>
            </a-modal>

            <a-modal width="300" v-model="companyDelayModal" :title="lotteryName" centered @ok="updateDelay" :footer="null">
                <div>
                    <table class="" border="0" align="center" cellpadding="8" cellspacing="1" style="border-collapse: separate;width: 100%">
                        <tbody>
                            <tr>
                                <td class="txtright">延时开盘</td>
                                <td class="">
                                    <img class="fl" :src="minus" @click="plusOrMinusDelay('delayOpen',-1)">
                                    <a-input v-model.number="delayForm.delayOpen" style="width: 50px;margin:0px 2px 0px 2px" size="small" :disabled="true" />
                                    <img class="fr" :src="plus" @click="plusOrMinusDelay('delayOpen',1)">
                                </td>
                            </tr>
                            <tr>
                                <td class="txtright">提前关盘</td>
                                <td class="">
                                    <img class="fl" :src="minus" @click="plusOrMinusDelay('precedeClose',-1)">
                                    <a-input v-model.number="delayForm.precedeClose" style="width: 50px;margin:0px 2px 0px 2px" size="small" :disabled="true" />
                                    <img class="fr" :src="plus" @click="plusOrMinusDelay('precedeClose',1)">
                                </td>
                            </tr>
                            <tr>
                                <td class="txtright">正码提前关盘</td>
                                <td class="">
                                    <img class="fl" :src="minus" @click="plusOrMinusDelay('zmPrecedeClose',-1)">
                                    <a-input v-model.number="delayForm.zmPrecedeClose" style="width: 50px;margin:0px 2px 0px 2px" size="small" :disabled="true" />
                                    <img class="fr" :src="plus" @click="plusOrMinusDelay('zmPrecedeClose',1)">
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </div>
            </a-modal>
        </a-spin>
    </div>
</template>

<script>
import { mapGetters } from "vuex";
import to from "await-to-js";
import draggable from "vuedraggable";
import minus from "@/assets/AdminDefaultTheme/Images/minus.png";
import plus from "@/assets/AdminDefaultTheme/Images/plus.png";
export default {
    components: {
        draggable,
    },
    data() {
        return {
            plus,
            minus,
            spinning: false,
            groupDelayModal: false,
            companyDelayModal: false,
            lotterys: [],
            dragging: false,
            lotteryName: null,
            delayForm: {
                lotteryId: null,
                delayOpen: 0,
                precedeClose: 0,
                zmPrecedeClose: 0,
            },
            isGroup: this.$store.state.user.info.userLevel == 1,
            isCompany: this.$store.state.user.info.userLevel == 2,
        };
    },
    computed: {
        ...mapGetters(["info"]),
    },
    mounted() {
        this.requestLottery();
    },
    methods: {
        showDelayModal(lottery) {
            this.groupDelayModal = this.isGroup;
            this.companyDelayModal = this.isCompany;

            let {
                lotteryName,
                lotteryId,
                delayOpen,
                precedeClose,
                zmPrecedeClose,
            } = lottery;
            this.lotteryName = lotteryName;
            this.delayForm.lotteryId = lotteryId;
            this.delayForm.delayOpen = delayOpen;
            this.delayForm.precedeClose = precedeClose;
            this.delayForm.zmPrecedeClose = zmPrecedeClose;
        },
        async plusOrMinusDelay(field, zf) {
            this.spinning = true;
            let params = {
                openModel: field,
                delayOpen: zf,
                lotteryId: this.delayForm.lotteryId,
            };
            let [err, data] = await to(
                this.$api.ctrl.updateLotteryDelayObo(params)
            );
            if (err || !data.success) {
                this.spinning = false;
                return;
            }
            let {
                lotteryId,
                delayOpen,
                precedeClose,
                zmPrecedeClose,
            } = data.data;
            let lottery = this.lotterys.find(
                (lottery) => lottery.lotteryId == lotteryId
            );
            lottery.delayOpen = delayOpen;
            lottery.precedeClose = precedeClose;
            lottery.zmPrecedeClose = zmPrecedeClose;
            this.delayForm.delayOpen = delayOpen;
            this.delayForm.precedeClose = precedeClose;
            this.delayForm.zmPrecedeClose = zmPrecedeClose;
            //await this.requestLottery();
            this.spinning = false;
        },
        async closeOrOpen(lottery) {
            this.spinning = true;
            let param = {
                lotteryId: lottery.lotteryId,
                isOpen: lottery.isOpenSelf,
            };
            let [err, res] = await to(
                this.$api.ctrl.updateLotteryStatus(param)
            );
            this.$utils.handleThen(res, this);
            this.spinning = false;
        },
        async updateDelay() {
            this.spinning = true;
            let [err, data] = await to(
                this.$api.ctrl.updateLotteryDelay(this.delayForm)
            );
            if (err || !data.success) {
                this.spinning = false;
                return;
            }
            await this.requestLottery();
            this.groupDelayModal = false;
            this.spinning = false;
        },
        async updateOrdering() {
            let lotterys = this.lotterys.map((lottery, index) => {
                let logic = {
                    lotteryId: lottery.lotteryId,
                    ordering: index + 1,
                };
                return logic;
            });
            this.spinning = true;
            let [err, res] = await to(
                this.$api.ctrl.updateLotteryOrdering({ lotterys })
            );
            this.$utils.handleThen(res, this);
            this.spinning = false;
        },
        async requestLottery() {
            this.spinning = true;
            let [err, data] = await to(this.$api.ctrl.getLottery());
            if (err || !data.success) {
                this.spinning = false;
                return;
            }
            this.lotterys = data.data;
            this.spinning = false;
        },
        async changeOpenModel(lottery) {
            let param = {
                lotteryId: lottery.lotteryId,
                openModel: lottery.openModel,
            };
            this.spinning = true;
            let [err, res] = await to(
                this.$api.ctrl.updateLotteryOpenModel(param)
            );
            this.$utils.handleThen(res, this);
            if (err || !res.success) {
                lottery.openModel =
                    lottery.openModel == "AUTO" ? "MANUAL" : "AUTO";
            }
            this.spinning = false;
        },
    },
};
</script>

<style scoped>
</style>
