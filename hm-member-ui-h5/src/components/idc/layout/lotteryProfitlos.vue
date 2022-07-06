<template>
  <div id="idc">
    <div class="home">
      <div class="sc-bZQynM OQRyf">
        <div class="sc-bdVaJa jaFIbq otherpage">
          <my-header top="true" back="true" profitlosBack="true" resbnt="true" @refreshPageFun="infoIntial" title="结算报表"></my-header>
          <div class="ui-content jqm_content">
           <!--这里是两个筛选下拉框-->

            <table width="100%" :class="params.status!='VOID'?'':'line-through'" align="left" cellspacing="0" cellpadding="0">
              <tbody><tr>
                <td id="tdlist" valign="top">
                  <table width="100%" class="jqm_xd_table" cellpadding="0" cellspacing="0" border="0" id="table2">
                    <tbody><tr align="center" class="td_caption_1">
                      <th>注单号</th>
                      <th>类型</th>
                      <th>玩法</th>
                      <th>下注金额</th>
                      <th>结果</th>
                    </tr>

                    <template v-for="(list,index) in lotteryHistoryList">
                      <tr >
                        <td>{{list.orderId}}<br/>{{list.betTime*1000 | formatDate}}
                          <br/>{{list.betTime*1000 | formatDateTwo}}</td>
                        <td>
                          <template v-for="(obj, i) in gameMenu">
                            <template v-if="parseInt(obj.index)===list.lotteryId">{{$t(obj.title)}}</template>
                          </template>
                          <br/>{{list.gameNo}} <br/>
                          盘口（{{list.market }}）
                        </td>

                        <td>
                          <span class="blue_color">
                            <span class="blue_color" v-if="list.betContent">{{$t(JSON.parse(list.keyName).playKey)}}<br/>
                              <template v-if="/^[0-9]\d*$/.test(list.oddsKey)" style="color:red;">{{list.oddsKey}}</template>
                              <template v-if="!/^[0-9]\d*$/.test(list.oddsKey)" style="color:red;">{{$t(list.oddsKey)}}</template>
                              <br/>
                            </span>
                            <span class="blue_color" v-if="!list.betContent"><span class="blue_color" v-if="JSON.parse(list.keyName).categoryKey=='lm'">{{$t(JSON.parse(list.keyName).categoryKey)}}</span>{{$t(JSON.parse(list.keyName).playKey)}}<br/><span v-if="/^[0-9]\d*$/.test(list.oddsKey)" class="red_color">{{list.oddsKey}}<br/></span><span v-if="!/^[0-9]\d*$/.test(list.oddsKey)" class="red_color">{{$t(list.oddsKey)}}<br/></span></span>
                            <span class="blue_color" v-if="list.betContent">@{{list.betContent}}<br/></span>
                            @<span class = "red_color">{{list.odds}}</span>
                          </span>

                        </td>
                        <td>{{list.betAmt}}</td>
                        <td>
                          <template v-if="list.winAmt">
                            <template v-if="parseFloat(list.winAmt) >= 0">
                            <span class="blue_color">{{list.winAmt | moneyFmt}}</span>

                            </template>
                            <template v-if="parseFloat(list.winAmt) < 0">
                              <span class="red_color">{{list.winAmt | moneyFmt}}</span>

                            </template>
                          </template>
                          <template v-else>
                            <span class="blue_color">{{0.00 | moneyFmt}}</span>
                          </template>
                          <br/>
                          <span class="blue_color">{{list.water}}</span><br/>
                          <span v-if="list.status=='REDIVIDEND'">重派</span></td>
                      </tr>
                    </template>
                    <tr class="t_list_bottom">
                      <td align="center">总计</td>
                      <td>-</td>
                      <td>注数({{totalNum}})</td>
                      <td>{{parseInt(totalBetAmt)}}</td>
                      <td>
                        <template v-if="parseFloat(totalWinAmt) >= 0">
                          <span class="blue_color">{{totalWinAmt | moneyFmt}}</span>

                        </template>
                        <template v-if="parseFloat(totalWinAmt) < 0">
                          <span class="red_color">{{totalWinAmt | moneyFmt}}</span>

                        </template>
                      </td>
                    </tr>
                    </tbody></table>

                </td>
              </tr>
              </tbody></table>
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
  import { formatDate } from '@/components/comm/date.js'
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
        lotteryHistoryList:[],
        someDayDate:'',
        totalNum:0,
        totalBetAmt:0,
        totalWinAmt:0,
        params:{
          lotteryId:null,
          status:'DIVIDEND',
          accountDay:'',
          size:20,
          page:1,
          winOrLoserState:null
        },
        total:1,
        totalPage:1
      }
    },
    computed: {
      ...mapGetters(['gameMenu','showMenu','gameId','profitlosReturn']),
    },
    filters: {
      moneyFmt(val){
        if(!val || 0 == val){
          return '0.0';
        }
        return Utils.formatMoney(val, 1);
      },
      formatDate(time) {
        var date = new Date(time);
        return formatDate(date, 'MM/dd'); // 年月日 格式自己定义   'yyyy : MM : dd'  例 2018年12月5日的格式
      },
      formatDateTwo(time){
        var date = new Date(time);
        return formatDate(date, 'hh:mm:ss');
      },
      /*marketFilter(val){
        if(val=='IDCE'){
          return "IDCA"
        }else if(val=='IDCF'){
          return "IDCB"
        }else if(val=='IDCG'){
          return "IDCC"
        }else if(val=='IDCH'){
          return "IDCD"
        }
      }*/
    },
    mounted(){
      this.infoIntial();
    },
    methods:{
      ...mapActions(['setProfitlosReturn']),
      winMoneyFmt(win,comm){
        let total = Utils.NumberAdd(win,comm);
        return Utils.formatMoney(total,2);
      },
      selectLotteryHistory(lotteryId){
        this.$router.push({name:'lotteryprofitlos',query:{'lotteryId':lotteryId,'selectDate':this.someDayDate}});
      },
      gotoPage(curPage) {

        let self = this;
        self.params.page = curPage;
        this.infoIntial();
      },
      async infoIntial(){
        let self = this;
        Indicator.open({text:'加载中...'});
        self.someDayDate = this.$route.query.lotteryId;
        self.params.lotteryId = this.$route.query.lotteryId;
        self.params.accountDay = this.$route.query.selectDate;
        self.params.winOrLoserState = this.$route.query.winOrLoserState;
        self.params.status = this.$route.query.status?this.$route.query.status:self.params.status;
        let singleOrAll = this.$route.query.singleOrAll;
        let routerName = '';
        if(singleOrAll){
          routerName = 'profitlos';
        }else{
          routerName = 'samedayprofitlos';
        }
        self.totalBetAmt = 0;
        self.totalWinAmt = 0;
        let waterTotalMoney = 0;
        self.setProfitlosReturn({'name':routerName,'query':{'selectDate':self.params.accountDay,'singleOrAll':singleOrAll,'lotteryId':this.$route.query.lotteryId,'winOrLoserState':this.$route.query.winOrLoserState},'mode':0});
        let [err,data] = await to(Bet.betList(self.params));
        if(data.success){
          self.lotteryHistoryList = data.data.dataList;
          for(let i = 0;i<self.lotteryHistoryList.length;i++){
            self.totalBetAmt = Utils.NumberAdd(self.lotteryHistoryList[i].betAmt,self.totalBetAmt);
            self.totalWinAmt = Utils.NumberAdd(self.lotteryHistoryList[i].winAmt,self.totalWinAmt);
            self.$set(self.lotteryHistoryList[i],'water',Utils.NumberDiv(Utils.NumberMul(self.lotteryHistoryList[i].betAmt,self.lotteryHistoryList[i].commPct),100.00,3));
            waterTotalMoney = Utils.NumberAdd(waterTotalMoney,self.lotteryHistoryList[i].water);

          }

          self.totalWinAmt = Utils.NumberAdd(self.totalWinAmt,waterTotalMoney);
          self.total = data.data.total;
          self.totalPage = data.data.total % self.params.size==0?data.data.total/self.params.size:parseInt(data.data.total/self.params.size)+1;
          if(data.data.total==0){
            self.params.page = 1;
          }
        }


        self.totalNum = self.lotteryHistoryList.length;
        Indicator.close();

      }
    },
  }
</script>

<style scoped>
  table {
    width: 100%;
    border-collapse: collapse;
  }
  .otherpage {
    background: #fff !important;
    height: calc(100% - 4px) !important;
  }

  .jqm_content {
    height: calc(100% - 47px) !important;
    padding: 0px;
  }

  .ui-content {
    border-width: 0;
    overflow: visible;
    overflow-x: hidden;
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
    background-image: url("../../images/tb_bg.jpg");
    font-size: 12px !important;
    color: #4A1A04;
    font-weight: bold;
    height: 30px;
    line-height: 30px;
  }
  .t_list_bottom {
    font-size: 14px;
    background-color: #F7D3B9;
  }

</style>

