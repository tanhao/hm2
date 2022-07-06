<template>
  <div id="idc">
    <div class="home">
      <div class="sc-bZQynM OQRyf">
        <div class="sc-bdVaJa jaFIbq otherpage">
          <my-header top="true" back="true" profitlosBack="true" resbnt="true" @refreshPageFun="infoIntial" title="结算报表"></my-header>
          <div class="ui-content jqm_content">
            <!--这里是两个筛选下拉框-->
            <table width="100%" :class="queryParam.winOrLoserState!='VOID'?'':'line-through'" align="left" cellspacing="0" cellpadding="0">
              <tbody><tr>
                <td id="tdlist" valign="top">
                  <table width="100%" class="jqm_xd_table" cellpadding="0" cellspacing="0" border="0" id="table2">
                    <tbody><tr align="center" class="td_caption_1">
                      <th>类型</th>
                      <th>注数</th>
                      <th>下注金额</th>
                      <th>退水</th>
                      <th>退水后结果</th>
                    </tr>

                    <template v-for="(list,index) in someDayList">
                      <tr @click="selectLotteryHistory(list.lotteryId)">
                        <td>{{someDayDate.substring(5)}}<br/> {{$t(list.lotteryKey)}}</td>
                        <td>{{list.num}}</td>
                        <td>{{list.betAmt }}</td>
                        <td>{{list.comm | moneyFmt}}</td>
                        <td><!--<span   :class="list.winAmt<0?'red_color':''">{{list.winAmt}}</span>-->
                          <template v-if="parseInt(winMoneyFmt(list.winAmt,list.comm)) >= 0">
                            <span class="blue_color">{{winMoneyFmt(list.winAmt,list.comm) }}</span><br/>
                            <!--<span class="blue_color">{{list.comm | moneyFmt}}</span>-->

                          </template>
                          <template v-else>
                            <span class="red_color">{{winMoneyFmt(list.winAmt,list.comm) }}</span>

                          </template></td>
                      </tr>
                    </template>
                    <tr class="t_list_bottom">
                      <td align="center">总计</td>
                      <td>{{parseInt(totalNum)}}</td>
                      <td>{{parseInt(totalBetAmt)}}</td>
                      <td>{{totalComm | moneyFmt}}</td>
                      <td>
                        <template v-if="parseInt(winMoneyFmt(totalWinAmt,totalComm)) >= 0">
                          <span class="blue_color">{{winMoneyFmt(totalWinAmt,totalComm) }}</span>

                        </template>
                        <template v-else>
                          <span class="red_color">{{winMoneyFmt(totalWinAmt,totalComm) }}</span>

                        </template>
                      </td>
                    </tr>
                    </tbody></table>
                </td>
              </tr>
              </tbody></table>
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
  import Utils from '@/components/comm/Utils.js'
  import Lottery from '@/axios/api-game.js'
  import {Indicator} from 'mint-ui'
  import to from "await-to-js";
  export default {
    components: {
      MyHeader,
      notice,
    },
    data() {
      return {
        nowOrPrevActive:true,
        someDayList:[],
        someDayDate:'',
        totalNum:0,
        totalBetAmt:0,
        totalActAmt:0,
        totalComm:0,
        totalWinAmt:0,
        queryParam:{},
        headParams:{}
      }
    },
    computed: {
      ...mapGetters(['gameMenu','showMenu','gameId','profitlosReturn']),
    },
    filters: {
      moneyFmt(val){
        if(!val || 0 == val){
          return '0.00';
        }
        return Utils.formatMoney(val, 2);
      }
    },
    mounted(){
      let self = this;
      Indicator.open({text:'加载中...'});
      let paramDate = this.$route.query.selectDate;
      self.queryParam.day = paramDate;
      self.queryParam.winOrLoserState = this.$route.query.winOrLoserState;
      self.headParams.selectDate = this.$route.query.selectDate;
      self.setProfitlosReturn({'name':'profitlos','query':{'winOrLoserState':this.$route.query.winOrLoserState},'mode':0});
      self.someDayDate = paramDate;
      self.infoIntial();
    },
    methods:{
      ...mapActions(['setProfitlosReturn']),
      winMoneyFmt(win,comm){
        if(!win){
          win = 0;
        }
        if(!comm || typeof comm == 'undefined'){
          comm = 0;
        }
        let total = Utils.NumberAdd(win,comm);
        return Utils.formatMoney(total,2);
      },
      selectLotteryHistory(lotteryId){
        this.$router.push({name:'lotteryprofitlos',query:{'lotteryId':lotteryId,'selectDate':this.someDayDate,'status':this.queryParam.winOrLoserState,'winOrLoserState':this.$route.query.winOrLoserState}});
      },
      async infoIntial(){
        let self = this;
        self.totalNum = 0;
        self.totalBetAmt = 0;
        self.totalActAmt = 0;
        self.totalComm = 0;
        self.totalWinAmt = 0;
        let [err,data] = await to(Lottery.getLotteryReport(self.queryParam));
        if(data.success){
          self.someDayList = data.data;
          for(let i = 0;i<self.someDayList.length;i++){
            self.totalNum = Utils.NumberAdd(self.someDayList[i].num,self.totalNum);
            self.totalBetAmt = Utils.NumberAdd(self.someDayList[i].betAmt,self.totalBetAmt);
            self.totalActAmt = Utils.NumberAdd(self.someDayList[i].actAmt,self.totalActAmt);
            self.totalComm = Utils.NumberAdd(self.someDayList[i].comm,self.totalComm);
            self.totalWinAmt = Utils.NumberAdd(self.someDayList[i].winAmt,self.totalWinAmt);

          }
        }
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


