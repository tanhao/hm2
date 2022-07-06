<template>
    <div>
        <a-spin :spinning="spinning">
            <a-modal :visible="orderShow" @cancel="onClose" :footer="null" width="1024px" :z-index="9999" title="注单明细" class="bblisttc">
                <a-spin :spinning="spinning">
                    <table class="tableborder leftlist" border="0" align="center" cellpadding="5" cellspacing="1" style="width: 100%;border-collapse: separate;">
                        <tr>
                            <th>注单号</th>
                            <th>下注时间</th>
                            <th>期号</th>
                            <th>用户名/盘口</th>
                            <th>下注明细</th>
                            <th>下注额</th>
                            <th>退水率</th>
                            <th>退水</th>
                            <th>输赢</th>
                            <th>下注类型</th>
                            <th>状态</th>
                            <th>占成明细</th>
                        </tr>
                        <tr v-for="(item,idx) in orders" :key="idx">
                            <td class="forumrow">{{item.orderId}}</td>
                            <td class="forumrow"> {{moment(item.betTime*1000).format('YYYY-MM-DD HH:mm:ss')}}</td>
                            <td class="forumrow">{{$t(item.lotteryId)}}：<br />{{item.gameNo}}期</td>
                            <td class="forumrow">{{item.username}}/{{item.market}}盘</td>
                            <td class="forumrow">
                                {{$t(JSON.parse(item.keyName).playKey)}}[{{$t(item.oddsKey)}}]
                                <template v-if="item.betContent">
                                    <br />{{item.betContent}}<br />
                                </template>
                                @{{$utils.getAnsQ(item.odds)}}
                            </td>
                            <td class="forumrow">
                                {{$utils.getAnsG(item.betAmt)}}
                            </td>
                            <td class="forumrow">
                                {{item.commPct}}%
                            </td>
                            <td class="forumrow">
                                {{$utils.getAnsS(item.betAmt*item.commPct/100)}}
                            </td>
                            <td class="forumrow" :class="$utils.getColorCssG(item.winAmt+item.betAmt*item.commPct/100)">
                                {{$utils.getAnsS(item.winAmt+item.betAmt*item.commPct/100)}}
                            </td>
                            <td class="forumrow">
                                {{item.betType==='zc'?'会员投注':item.manual?'手动补货':'自动补货'}}
                            </td>
                            <td class="forumrow">{{$t(item.status)}}</td>
                            <td class="forumrow">
                                <a-button type="primary" icon="save" size="small" class="mlr10" @click="showPtDetail(item.ticketDetail)">
                                    明细
                                </a-button>
                            </td>
                        </tr>
                    </table>
                    <div class="p10" style="text-align: center;">
                        <a-pagination size="small" :total="total" show-size-changer show-quick-jumper :current="page" :pageSize="size" @change="changePage" @showSizeChange="changePage" :show-total="total => `共 ${total} 页`" />
                    </div>
                </a-spin>
            </a-modal>
            <a-modal :visible="ptDetailModel" @cancel="closePTDetail" :footer="null" width="500px" :z-index="9999" title="注单明细" class="bblisttc">
                <table class="tableborder leftlist" border="0" align="center" cellpadding="5" cellspacing="1" style="width: 100%;border-collapse: separate;">
                    <tr>
                        <th>类型</th>
                        <th>账号</th>
                        <th>占成</th>
                        <th>退水</th>
                    </tr>
                    <tr v-for="(pd,index) in ptDetails" :key="index">
                        <td class="forumrow">{{pd.userType=="MEMBER"?"会员":pd.userLevel==1?"后统":pd.userLevel==2?"公司":pd.userLevel==3?"一级代理":pd.userLevel==4?"二级代理"
                              :pd.userLevel==5?"三级代理":pd.userLevel==6?"四级代理":pd.userLevel==7?"五级代理":pd.userLevel==8?"六级代理"
                              :pd.userLevel==9?"七级代理":pd.userLevel==10?"八级代理":pd.userLevel==11?"九级代理":pd.userLevel==12?"十级代理":"会员"}}
                        </td>
                        <td class="forumrow">{{pd.userName}}</td>
                        <td class="forumrow">{{pd.ptPct}}%</td>
                        <td class="forumrow">{{pd.commPct}}%</td>
                    </tr>
                </table>
            </a-modal>
        </a-spin>
    </div>
</template>
<script>
import to from "await-to-js";
export default {
    name: "now-order",
    props: {
        orderShow: Boolean,
        params: Object,
    },
    data() {
        return {
            spinning: false,
            orders: [],
            page: 1,
            size: 10,
            total: 0,
            ptDetailModel: false,
            ptDetails: [],
            userLevel: this.$store.state.user.info.userLevel,
        };
    },
    computed: {},
    mounted() {
        this.requestOrder();
    },
    watch: {},
    methods: {
        showPtDetail(data) {
            this.ptDetailModel = true;
            let details = [];
            let jsonObject = JSON.parse(data);
            Object.keys(jsonObject).forEach((key) => {
                let item = jsonObject[key];
                if (item.userLevel >= this.userLevel) {
                    details.push(item);
                }
            });
            this.ptDetails = details;
        },
        changePage(page, size) {
            this.page = page;
            this.size = size;
            this.requestOrder();
        },
        async requestOrder() {
            if (!this.orderShow) {
                return;
            }
            let { oddsId, lotteryIds, gameNo } = this.params;
            let params = {
                oddsId,
                lotteryIds,
                gameNo,
                page: this.page,
                size: this.size,
                status: "WAITING",
            };
            this.spinning = true;
            let [err, res] = await to(this.$api.order.getOrderList(params));
            this.spinning = false;
            if (err || !res.success) {
                this.$utils.handleThen(res, this);
                return;
            }
            let { dataList, total } = res.data;
            this.orders = dataList;
            this.total = total;
            console.log(res.data);
        },
        onClose() {
            this.$emit("update:orderShow", false);
        },
        closePTDetail() {
            this.ptDetailModel = false;
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
