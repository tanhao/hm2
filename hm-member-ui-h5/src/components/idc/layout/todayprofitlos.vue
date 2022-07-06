<template>
  <div id="idc">
    <div class="home">
      <div class="sc-bZQynM" style="height: 100%;">
        <div class="sc-bdVaJa jaFIbq">
          <my-header top="true" back="true" profitlosBack="true" resbnt="true" @refreshPageFun="infoIntial"
                     :title="params.status=='VOID'?'作废注单明细':'账户历史明细'"></my-header>
          <div class="ui-content jqm_content">
            <table class="jqm_xd_table" cellpadding="0" cellspacing="0" border="0">
              <thead>
              <tr>
                <th style="height: auto;line-height: normal;width: 80px">注单号/<br>时间</th>
                <th width="80">类型</th>
                <th>玩法</th>
                <th>注金</th>
                <th>退水</th>
                <th>结果</th>
              </tr>
              </thead>
            </table>
            <div class="detailist">
              <table :class="params.status!='VOID'?'':'line-through'" class="jqm_xd_table" cellpadding="0" cellspacing="0" border="0">
                <template v-for="(list,index) in lotteryHistoryList">
                  <tr>
                    <td width="80">{{list.orderId}}<br/>{{list.betTime*1000 | formatDate}}
                      <br/>{{list.betTime*1000 | formatDateTwo}}
                    </td>
                    <td width="80">
                      <template v-for="(obj, i) in gameMenu">
                        <template v-if="parseInt(obj.index)===list.lotteryId">{{$t(obj.title)}}</template>
                      </template>
                       {{list.gameNo}} <br/>
                      盘口（{{list.market }}）
                    </td>

                    <td>
                                          <span class="blue_color">
                                            <span class="blue_color" v-if="list.betContent">{{$t(JSON.parse(list.keyName).playKey)}}<br/>
                                              <template
                                                v-if="/^[0-9]\d*$/.test(list.oddsKey)" class="red_color">{{list.oddsKey}}<br/></template>
                                              <template
                                                v-if="!/^[0-9]\d*$/.test(list.oddsKey)" class="red_color">{{$t(list.oddsKey)}}<br/></template>
                                            </span>
                                            <span class="blue_color" v-if="!list.betContent"><span class="blue_color"
                                                                                                   v-if="JSON.parse(list.keyName).categoryKey=='lm'">{{$t(JSON.parse(list.keyName).categoryKey)}}</span>{{$t(JSON.parse(list.keyName).playKey)}}<br/><span
                                              v-if="/^[0-9]\d*$/.test(list.oddsKey)"
                                              class="red_color">{{list.oddsKey}}<br/></span><span
                                              v-if="!/^[0-9]\d*$/.test(list.oddsKey)" class="red_color">{{$t(list.oddsKey)}}<br/></span></span>
                                            <span class="blue_color" v-if="list.betContent">@{{list.betContent}}<br/></span>
                                            @<span class="red_color">{{list.odds}}</span>
                                          </span>

                    </td>
                    <td>{{list.betAmt}}</td>
                    <td>{{list.water}}</td>
                    <td>
                      <template v-if="list.winAmt">
                        <template v-if="parseFloat(list.winAmt+list.water) >= 0">
                          <span class="blue_color">{{list.winAmt+list.water | moneyFmt}}</span>

                        </template>
                        <template v-if="parseFloat(list.winAmt+list.water) < 0">
                          <span class="red_color">{{list.winAmt+list.water | moneyFmt}}</span>

                        </template>
                      </template>
                      <template v-else>
                        <span class="blue_color">{{0.00 | moneyFmt}}</span>
                      </template>
                      <br/>
                      <span v-if="list.status=='REDIVIDEND'">重派</span>
                    </td>
                  </tr>
                </template>
              </table>
            </div>
            <table class="jqm_xd_table" cellpadding="0" cellspacing="0" border="0" style="position: relative;bottom: 0px">
              <tbody>
              <tr style="background-color: rgb(235, 215, 216);">
                <td colspan="2" style="text-align: center;width: 160px">总计</td>
                <td>-</td>
                <td>{{parseInt(totalBetAmt)}}</td>
                <td>{{totalWater | moneyFmt}}</td>
                <td>
                  <template v-if="parseFloat(totalWinAmt) >= 0">
                    <span class="blue_color">{{totalWinAmt | moneyFmt}}</span>

                  </template>
                  <template v-if="parseFloat(totalWinAmt) < 0">
                    <span class="red_color">{{totalWinAmt | moneyFmt}}</span>

                  </template>
                </td>
              </tr>
              </tbody>
            </table>
            <div class="pagerPage">
              <pager ref="pager"
                     :pageSize="params.size"
                     :curPage="params.page"
                     :total="totalPage"
                     :transSum="total"
                     @setPage="gotoPage"
              ></pager>
            </div>
          </div>
        </div>
      </div>
      <notice></notice>
    </div>
  </div>
</template>
<script>
  import {mapGetters, mapActions} from 'vuex'
  import MyHeader from '@/components/idc/layout/header'
  import notice from '@/components/notice'
  import {formatDate} from '@/components/comm/date.js'
  import Bet from '@/axios/api-bet.js'
  import Utils from '@/components/comm/Utils.js'
  import {Indicator} from 'mint-ui'
  import pager from '@/components/idc/layout/paging'
  import to from "await-to-js";
  export default {
    components: {
      MyHeader,
      notice,
      pager,
    },
    data() {
      return {
        lotteryHistoryList: [],
        someDayDate: '',
        totalNum: 0,
        totalBetAmt: 0,
        totalWinAmt: 0,
        totalWater:0,
        totalPage:1,
        params: {
          lotteryId: null,
          status: 'DIVIDEND',
          accountDay: '',
          size: 8,
          page:1,
          winOrLoserState: null,
          searchAll: 1
        },
        curPage: 1,//当前页
        total: 0,//总共页数
        pageSize: 8,//每页记录数
        dataChanged: false,
        curPageShowList: []
      }
    },
    computed: {
      ...mapGetters(['gameMenu', 'showMenu', 'gameId', 'profitlosReturn']),
    },
    filters: {
      moneyFmt(val) {
        if (!val || 0 == val) {
          return '0.0';
        }
        return Utils.formatMoney(val, 1);
      },
      formatDate(time) {
        var date = new Date(time);
        return formatDate(date, 'MM/dd'); // 年月日 格式自己定义   'yyyy : MM : dd'  例 2018年12月5日的格式
      },
      formatDateTwo(time) {
        var date = new Date(time);
        return formatDate(date, 'hh:mm:ss');
      },
    },
    mounted() {
      this.infoIntial();
    },
    methods: {
      ...mapActions(['setProfitlosReturn']),
      winMoneyFmt(win, comm) {
        let total = Utils.NumberAdd(win, comm);
        return Utils.formatMoney(total, 2);
      },
      selectLotteryHistory(lotteryId) {
        this.$router.push({name: 'lotteryprofitlos', query: {'lotteryId': lotteryId, 'selectDate': this.someDayDate}});
      },
      async infoIntial() {
        let self = this;
        Indicator.open({text: '加载中...'});
        self.someDayDate = this.$route.query.lotteryId;
        self.params.lotteryId = null;
        self.params.accountDay = this.$route.query.selectDate;
        self.params.winOrLoserState = this.$route.query.winOrLoserState;
        self.params.status = this.$route.query.status?this.$route.query.status:self.params.status;
        let singleOrAll = this.$route.query.singleOrAll;
        let routerName = '';
        if (singleOrAll) {
          routerName = 'profitlos';
        } else {
          routerName = 'samedayprofitlos';
        }
        self.totalBetAmt = 0;
        self.totalWinAmt = 0;
        self.totalWater = 0;
        self.setProfitlosReturn({
          'name': routerName,
          'query': {
            'selectDate': self.params.accountDay,
            'singleOrAll': singleOrAll,
            'lotteryId': this.$route.query.lotteryId,
            'winOrLoserState': this.$route.query.winOrLoserState,
            'status':self.params.status
          },
          'mode': 0
        });
        let [err,data] = await to(Bet.betList(self.params));
        if(data.success){
          self.lotteryHistoryList = data.data.dataList;
          if(self.lotteryHistoryList){
            for (let i = 0; i < self.lotteryHistoryList.length; i++) {
              self.totalBetAmt = Utils.NumberAdd(self.lotteryHistoryList[i].betAmt, self.totalBetAmt);
              self.totalWinAmt = Utils.NumberAdd(self.lotteryHistoryList[i].winAmt, self.totalWinAmt);
              self.$set(self.lotteryHistoryList[i], 'water', Utils.NumberDiv(Utils.NumberMul(self.lotteryHistoryList[i].betAmt, self.lotteryHistoryList[i].commPct), 100.00, 3));
              self.totalWater = Utils.NumberAdd(self.totalWater, self.lotteryHistoryList[i].water);

            }

            self.totalWinAmt = Utils.NumberAdd(self.totalWinAmt, self.totalWater);
            self.total = data.data.total;
            self.totalPage = data.data.total % self.params.size==0?data.data.total/self.params.size:parseInt(data.data.total/self.params.size)+1;
          }
        }
        Indicator.close();
      },
      gotoPage(curPage) {

        let self = this;
        self.params.page = curPage;
        this.infoIntial();
      }
    },
  }
</script>

<style scoped>
  table {
    table-layout: fixed;
    border-collapse: collapse;
  }

  .detailist {
    background-color: #fff;
    -webkit-overflow-scrolling: touch !important;
    height: calc(100% - 85px) !important;
    overflow: scroll;
  }

  .jqm_content {
    height: calc(100% - 70px) !important;
    position: relative;
    display: table;
    padding: 0px;
  }

  .ui-content {
    border-width: 0;
    overflow: scroll;
  }

  .jqm_xd_table {
    width: 100%;
  }

  .jqm_xd_table tr td {
    text-align: center;
    border: 1px solid #EFC0A7;
    height: 30px;
    line-height: 18px;
    font-size: 12px;
  }

  .jqm_xd_table tr th {
    border: 1px solid #EFC0A7;
    text-align: center;
    background: linear-gradient(360deg, rgb(239, 192, 167) 0%, rgb(253, 248, 245) 100%);
    font-size: 12px !important;
    color: #4A1A04;
    font-weight: bold;
    height: 30px;
    line-height: 30px;
  }


</style>

