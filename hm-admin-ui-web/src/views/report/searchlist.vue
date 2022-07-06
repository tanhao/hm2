<template>
    <div class="bblist">
        <a-spin :spinning="spinning">
            <div class="mb10">
                <a-row>
                    <a-checkbox :checked="searchType==='time'" @click="changeSearch('time')">
                        <span class="maintxt mr10">选择日期:</span></a-checkbox>
                    <a-date-picker size="small" v-model="selectDay" @change="changeLottery(params.lotteryId)"
                                   placeholder="选择日期" style="width: 120px"/>
                    <span class="maintxt mlr10">选择彩种:</span>
                    <a-select style="width: 120px" @change="changeLottery" size="small" v-model="params.lotteryId">
                        <a-select-option v-for="item in lotterys" :key="item.lotteryId">
                            {{ item.lotteryName }}
                        </a-select-option>
                    </a-select>
                    <span class="maintxt mlr10">选择期数:</span>
                    <a-select style="width: 120px" size="small" v-model="params.gameNo" show-search
                              option-filter-prop="children" :filter-option="filterOption">
                        <a-select-option v-for="no in gameNos" :key="no">
                            {{ no }}
                        </a-select-option>
                    </a-select>
                    <span class="maintxt mlr10">注单状态:</span>
                    <a-select style="width: 80px" size="small" v-model="params.status" show-search
                              option-filter-prop="children" :filter-option="filterOption">
                        <a-select-option key="">全部</a-select-option>
                        <a-select-option key="WAITING">{{$t('WAITING')}}</a-select-option>
                        <a-select-option key="DIVIDEND">{{$t('DIVIDEND')}}</a-select-option>
                        <a-select-option key="CANCEL">{{$t('CANCEL')}}</a-select-option>
                        <a-select-option key="VOID">{{$t('VOID')}}</a-select-option>
                        <a-select-option key="REDIVIDEND">{{$t('REDIVIDEND')}}</a-select-option>
                    </a-select>
                    <span class="maintxt mlr10">用户名:</span>
                    <a-input style="width: 130px" size="small" v-model="params.username" placeholder="用户名"/>
                    <a-button type="primary" icon="search" size="small" class="mlr10" :disabled="searchType==='no'"
                              @click="changePage(1,params.size)">
                        查询
                    </a-button>
                </a-row>
                <a-divider dashed/>
                <a-row>
                    <a-checkbox :checked="searchType==='no'" @click="changeSearch('no')">
                        <span class="maintxt mr10">单号查询:</span></a-checkbox>
                    <a-input-search v-model="orderNo" @focus="focus($event)" style="width: 190px" size="small"
                                    placeholder="注单号"/>
                    <a-button type="primary" icon="search" size="small" class="mlr10" :disabled="searchType==='time'"
                              @click="changePage(1,params.size)">
                        查询
                    </a-button>
<!--                    <div class="fr">
                        <template v-if="searchType === 'time'">
                           &lt;!&ndash; <a-button type="primary" size="small" style="margin-left: 10px;"
                                      :disabled="selectIds.length===0"
                                      @click="showCancel('id_void',true)">
                                注单作废
                            </a-button>&ndash;&gt;
                            <a-button type="primary" :disabled="cancelGameNo" size="small" style="margin-left: 10px;"
                                      @click="showCancel('no_void',false)">
                                当期注单作废
                            </a-button>
                            <a-button type="primary" :disabled="cancelUsername" size="small" style="margin-left: 10px;"
                                      @click="showCancel('mem_void',false)">
                                当期用户注单作废
                            </a-button>
                        </template>
                    </div>-->
                </a-row>
                <a-divider dashed/>
            </div>
            <table class="tableborder leftlist" border="0" align="center" cellpadding="5" cellspacing="1">
                <tr>
                    <!--<th>
                        <a-checkbox v-model="selectAll" @change="changeBoxAll">
                        </a-checkbox>
                    </th>-->
                    <th>注单号</th>
                    <th>下注时间</th>
                    <th>期号</th>
                    <th>账号</th>
                    <th>下注明细</th>
                    <th>下注金额</th>
                    <th>退水率</th>
                    <th>退水</th>
                    <th>输赢金额</th>
                    <th>下注类型</th>
                    <th>状态</th>
                </tr>
                <tr v-for="item in orderList">
                   <!-- <td class="forumrow">
                        <a-checkbox v-model="item.isCheck" @change="changeBox(item)">
                        </a-checkbox>
                    </td>-->
                    <td class="forumrow">{{item.orderId}}</td>
                    <td class="forumrow">
                        {{moment(item.betTime*1000).format('YYYY-MM-DD HH:mm:ss')}}
                    </td>
                    <td class="forumrow">{{$t(item.lotteryId)}}：<br/>{{item.gameNo}}期</td>
                    <td class="forumrow">{{item.username}}/{{item.market}}盘</td>
                    <td class="forumrow">
                        {{$t(JSON.parse(item.keyName).playKey)}}[{{$t(item.oddsKey)}}]
                        <template v-if="item.betContent">
                            <br/>{{item.betContent}}<br/>
                        </template>
                        @{{$utils.getAnsQ(item.odds)}}
                    </td>
                    <td class="forumrow textright">{{$utils.getAnsS(item.betAmt)}}</td>
                    <td class="forumrow textright">{{$utils.getAnsS(item.commPct)}}%</td>
                    <td class="forumrow textright">{{$utils.getAnsS(item.betAmt*item.commPct/100)}}</td>
                    <td class="forumrow textright">
                        <span :class="$utils.getColorCss(item.winAmt)">{{$utils.getAnsS(item.winAmt)}}</span>
                    </td>
                    <td class="forumrow">
                        {{item.betType==='zc'?'会员投注':item.manual?'手动补货':'自动补货'}}
                    </td>
                    <td class="forumrow">
                        <span v-if="item.status==='VOID'">
                            <a-tooltip>
                            <template slot="title">
                                {{item.voidReason}}
                            </template>
                            {{$t(item.status)}}
                        </a-tooltip>
                        </span>
                        <div v-else>
                            {{$t(item.status)}}
                        </div>
                    </td>
                </tr>
                <tr v-show="orderList.length===0">
                    <td colspan="50" class="forumrowhighlight nohover">
                        <a-empty/>
                    </td>
                </tr>
            </table>
            <div class="p10" style="text-align: center;" v-if="searchType==='time'">
                <a-pagination size="small" :total="orderTotal" show-size-changer show-quick-jumper
                              :current="params.page"
                              :pageSize="params.size" @change="changePage"
                              @showSizeChange="changePage" :show-total="total => `共 ${total} 条`"/>
            </div>
        </a-spin>

        <!--弹出窗口-->
        <a-modal v-model="czlist" title="注单作废" @ok="voidTicket" centered
                 class="bblisttc">
            <div style="text-align: center">
                <div style="text-align: center">
                    <span class="red">作废内容：
                        <span v-if="cancelBet && selectIds.length>0">注单号：{{selectIds}}</span>
                        <span v-if="!cancelBet && !cancelGameNo">{{$t(backParams.lotteryId)}}->第{{backParams.gameNo}}期</span>
                        <span v-if="!cancelBet && !cancelUsername">{{$t(backParams.lotteryId)}}->第{{backParams.gameNo}}期->用户名({{backParams.username}})</span>
                    </span>
                </div>
                <br/>
                <span class="red mr10">作废原因：</span>
                <a-input v-model="voidReason" style="width: 180px;"></a-input>
                <!--<a-select v-model="voidReason" style="width: 180px;" v-if="!voidMaunl">
                    <a-select-option value="非法投注">
                        非法投注
                    </a-select-option>
                    <a-select-option value="异常投注">
                        异常投注
                    </a-select-option>
                    <a-select-option value="其它原因1">
                        其它原因1
                    </a-select-option>
                    <a-select-option value="其它原因2">
                        其它原因2
                    </a-select-option>
                    <a-select-option value="其它原因3">
                        其它原因3
                    </a-select-option>
                </a-select>
                <a-input v-model="voidReason" style="width: 180px;" v-if="voidMaunl"></a-input>
               <a-checkbox class="mlr10" v-model="voidMaunl">手动输入</a-checkbox>-->
            </div>
            <br/>

        </a-modal>
    </div>


</template>

<script>
    export default {
        data() {
            return {
                spinning: false,
                searchType: 'time',
                lotterys: [],
                selectDay: this.todayDate(),
                gameNos: [],
                params: {
                    page: 1,
                    size: 10,
                    lotteryId: null,
                    gameNo: null,
                    username: null,
                    status:"",
                },
                orderNo: null,
                orderList: [],
                orderTotal: 0,
                selectAll: false,
                selectIds: [],
                cancelGameNo: true,
                cancelUsername: true,
                cancelBet: false,
                czlist: false,
                backParams: {},
                voidReason: '',
                voidType: null,
                voidMaunl:false,
            };
        },
        methods: {
            filterOption(input, option) {
                return (
                    option.componentOptions.children[0].text.toLowerCase().indexOf(input.toLowerCase()) >= 0
                );
            },
            showCancel(type, flg) {
                this.cancelBet = flg;
                this.czlist = true;
                this.voidType = type;
            },
            voidTicket(e) {
                this.czlist = false;
                let params = {
                    orderIds: this.selectIds,
                    voidReason: this.voidReason,
                    voidType: this.voidType,
                    lotteryId: this.backParams.lotteryId,
                    gameNo: this.backParams.gameNo,
                    username: this.backParams.username
                }
                this.$api.order.voidOrder(params).then(res => {
                    this.$utils.handleThen(res, this, '作废成功！')
                    this.changePage(1, this.params.size);
                })
            },
            changeLottery(id) {
                let day = this.selectDay.format('YYYY-MM-DD');
                this.params.gameNo = '';
                this.$api.order.getGameNo(day, id).then(res => {
                    if (res.success) {
                        this.gameNos = res.data;
                    }
                })
            },
            loadLottery() {
                this.$api.ctrl.getLotteryCompany().then(res => {
                    if (res.success) {
                        this.lotterys = res.data;
                    }
                })
            },
            changePage(page, pageSize) {
                this.selectAll = false;
                this.selectIds = [];
                this.params.page = page;
                this.params.size = pageSize;
                this.loadOrderList();
            },
            loadOrderList() {
                this.checkCallBack(() => {
                    this.orderList = [];
                    this.cancelGameNo = true;
                    this.cancelUsername = true;
                    if (this.searchType === 'time') {
                        this.params.startTime = this.selectDay.format('YYYY-MM-DD');
                        this.params.endTime = this.selectDay.format('YYYY-MM-DD');
                        this.checkNull(this.params.lotteryId, "请选择彩种！");
                        this.checkNull(this.params.gameNo, "请选择期号！");
                        this.spinning = true;
                        this.params.lotteryIds = [this.params.lotteryId];
                        this.$api.order.getOrderList(this.params).then(res => {
                            if (res.success) {
                                this.backParams = Object.assign({}, this.params);
                                this.orderList = res.data.dataList;
                                this.orderTotal = res.data.total;
                                if (this.orderTotal > 0) {
                                    if (this.params.username) {
                                        this.cancelUsername = false;
                                    } else {
                                        this.cancelGameNo = false;
                                    }
                                }
                            }
                        }).finally(e => {
                            this.spinning = false;
                        })
                    } else if (this.searchType === 'no') {
                        this.checkNull(this.orderNo, "请输入注单号！");
                        this.spinning = true;
                        this.$api.order.getOrderById(this.orderNo).then(res => {
                            if (res.success) {
                                if (res.data) {
                                    this.orderList.push(res.data);
                                }
                                this.orderTotal = 0;
                            }
                        }).finally(e => {
                            this.spinning = false;
                        })
                    }
                })
            },
            changeBoxAll(v) {
                this.selectIds = [];
                this.orderList.forEach(order => {
                    if (order.status === 'DIVIDEND') {
                        if (v.target.checked) {
                            this.selectIds.push(order.orderId);
                        }
                        this.$set(order, 'isCheck', v.target.checked);
                    }
                })
            },
            changeBox(v) {
                if (v.status === 'DIVIDEND') {
                    if (v.isCheck) {
                        this.selectIds.push(v.orderId);
                    } else {
                        this.selectIds.splice(this.selectIds.findIndex(v => v === v.orderId), 1)
                    }
                } else {
                    v.isCheck = false;
                }
            },
            changeSearch(searchType){
                this.searchType=searchType;
                this.orderList=[];
                this.selectIds = [];
                this.selectAll = false;
            }
        },
        mounted() {
            this.loadLottery();
        },
    };
</script>

<style scoped>

</style>