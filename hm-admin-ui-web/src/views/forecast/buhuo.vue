<template>
    <div>
        <a-spin :spinning="spinning">
            <a-modal width="150" :visible="buhuoShow" @cancel="onClose" title="补货单" centered @ok="saveOrder">
                <div>
                    <table class="" border="0" align="center" cellpadding="8" cellspacing="1" style="border-collapse: separate;width: 100%">
                        <tbody>
                            <tr>
                                <td class="txtright">类型</td>
                                <td class="">{{this.params.name}}</td>
                            </tr>
                            <tr>
                                <td class="txtright">选择</td>
                                <td class="">{{this.params.oddsName}}</td>
                            </tr>
                            <tr>
                                <td class="txtright">盘口</td>
                                <td class="">{{this.params.market}}</td>
                            </tr>
                            <tr>
                                <td class="txtright">赔率</td>
                                <td class="">{{this.params.odds}}</td>
                            </tr>
                            <tr>
                                <td class="txtright">金额</td>
                                <td class="">
                                    <a-input-number v-model="betAmt" style="width: 150px" size="small" ref="betAmtInput" />
                                </td>
                            </tr>
                            <tr>
                                <td class="txtright">限额</td>
                                <td>{{maxAmt}}</td>
                            </tr>

                        </tbody>
                    </table>
                </div>
            </a-modal>
        </a-spin>
    </div>
</template>
<script>
import to from "await-to-js";
export default {
    name: "now-order",
    props: {
        buhuoShow: Boolean,
        params: Object,
    },
    data() {
        return {
            spinning: false,
            betAmt: 0,
            maxAmt: 0,
        };
    },
    computed: {},
    mounted() {
        this.requestBuhuoAmt();
    },
    watch: {},
    methods: {
        async requestBuhuoAmt() {
            if (!this.buhuoShow) {
                return;
            }
            this.$nextTick(() => {
                this.$refs.betAmtInput.focus();
            });
            //console.log(this.params);
            let { oddsId, lotteryId, gameNo } = this.params;
            let params = {
                oddsId,
                lotteryId,
                gameNo,
            };
            this.spinning = true;
            let [err, res] = await to(this.$api.ctrl.getBuhuoAmt(params));
            this.spinning = false;
            if (err || !res.success) {
                this.$utils.handleThen(res, this);
                return;
            }
            this.maxAmt = res.data;
            /*
            this.$nextTick(() => {
                this.$refs.betAmtInput.select();
                this.$refs.betAmtInput.focus();
            });
            */
        },
        async saveOrder() {
            let { oddsId, lotteryId, gameNo, odds, market } = this.params;
            if (this.betAmt <= 0 || this.betAmt > this.maxAmt) {
                this.$utils.handleThen(
                    { success: false, message: "补货金额错误！ " },
                    this
                );
                //12return;
            }
            let params = {
                oddsId,
                lotteryId,
                gameNo,
                market,
                betAmt: this.betAmt,
                betOdds: odds,
            };
            this.spinning = true;
            let [err, res] = await to(this.$api.ctrl.saveBuhuoOrder(params));
            this.spinning = false;
            this.$utils.handleThen(res, this);
            if (err || !res.success) {
                return;
            }
            this.refreshPage();
            this.onClose();
        },
        onClose() {
            this.$emit("update:buhuoShow", false);
        },
        refreshPage() {
            this.$emit("refresh-page");
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
