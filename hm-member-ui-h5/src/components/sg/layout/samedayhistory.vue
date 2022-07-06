<template>
  <div class="home">
    <div class="sc-bZQynM OQRyf">
      <my-header title="两周报表" back="true" historyBack="true" left="true"></my-header>
      <div class="sc-htoDjs UDzZc">
        <div id="top-line"></div>
        <div class="table more">
          <div class="table-header">
            <div class="col col3">类型</div>
            <div class="col col2">注数</div>
            <div class="col col2">下注金额</div>
            <div class="col col2">佣金</div>
            <div class="col col2">盈亏</div>
          </div>
          <div :class="someDayStatus!='VOID'?'table-content':'table-content line-through'">
            <template v-for="(list,i) in someDayList">
              <a class="table-row" @click="selectLotteryHistory(list.lotteryId)">
                <div class="col col3 green_color">{{someDayDate}} <br/> {{$t(list.lotteryKey)}}</div>
                <div class="col col2">{{list.num}}</div>
                <div class="col col2">{{list.betAmt}}</div>
                <div class="col col2">{{list.comm | moneyFmt}}</div>
                <div class="col col2">
                  <template v-if="parseInt(winMoneyFmt(list.winAmt,list.comm)) >= 0">
                    <span class="blue_color">{{winMoneyFmt(list.winAmt,list.comm)}}</span>

                  </template>
                  <template v-else>
                    <span class="red_color">{{winMoneyFmt(list.winAmt,list.comm)}}</span>

                  </template>
                </div>
              </a>
            </template>

          </div>
          <div class="table-footer">
            <div class="col col3">总计</div>
            <div class="col col2">{{parseInt(totalNum)}}</div>
            <div class="col col2">{{parseInt(totalBetAmt)}}</div>
            <div class="col col2">{{totalComm | moneyFmt}}</div>
            <div class="col col2">
              <span v-if="parseInt(winMoneyFmt(totalWinAmt,totalComm))>=0" class="blue_color">{{winMoneyFmt(totalWinAmt,totalComm)}}</span>
              <span v-else class="red_color"> {{winMoneyFmt(totalWinAmt,totalComm)}}</span>
            </div>
          </div>
        </div>




      </div>
    </div>
    <my-footer></my-footer>
  </div>
</template>
<script>
  import MyHeader from '@/components/sg/layout/header'
  import MyFooter from '@/components/sg/layout/footer'
  import {formatDate} from '@/components/comm/date.js'
  import {mapGetters, mapActions} from 'vuex'
  import Lottery from '@/axios/api-game.js'
  import Utils from '@/components/comm/Utils.js'
  import {Indicator} from 'mint-ui'

  export default {
    components: {
      MyHeader,
      MyFooter,
    },
    data() {
      return {
        someDayList:[],
        someDayDate:'',
        totalNum:0,
        totalBetAmt:0,
        totalActAmt:0,
        totalComm:0,
        totalWinAmt:0,
        headParams:{},
        someDayStatus:null
      }
    },
    filters: {
      moneyFmt(val){
        if(!val || 0 == val){
          return '0.00';
        }
        return Utils.formatMoney(val, 2);
      }
    },
    computed: {
      ...mapGetters(['gameMenu', 'showMenu', 'gameId','historyReturn']),

  }
  ,
  created()
  {

  }
  ,
  methods:{
    ...mapActions(['setHistoryReturn']),
      selectLotteryHistory(lotteryId){
        this.$router.push({name:'lotteryhistory',query:{'lotteryId':lotteryId,'selectDate':this.someDayDate,'status':this.someDayStatus}});
      },
    winMoneyFmt(win,comm){
      let total = Utils.NumberAdd(win,comm);
      return Utils.formatMoney(total,2);
    }
  }
  ,
  mounted(){
    let self = this;
    Indicator.open({text:'加载中...'});
    let paramDate = this.$route.query.selectDate;
    self.headParams.selectDate = this.$route.query.selectDate;
    let paramStatus = this.$route.query.status;
    self.setHistoryReturn({'name':'history','query':{},'mode':0});
    self.someDayDate = paramDate;
    self.someDayStatus = paramStatus;
    Lottery.getLotteryReport({'day':paramDate,'winOrLoserState':paramStatus}).then(val=>{
      self.someDayList = val.data;
      for(let i = 0;i<self.someDayList.length;i++){
        self.totalNum = Utils.NumberAdd(self.someDayList[i].num,self.totalNum);
        self.totalBetAmt = Utils.NumberAdd(self.someDayList[i].betAmt,self.totalBetAmt);
        self.totalActAmt = Utils.NumberAdd(self.someDayList[i].actAmt,self.totalActAmt);
        self.totalComm = Utils.NumberAdd(self.someDayList[i].comm,self.totalComm);
        self.totalWinAmt = Utils.NumberAdd(self.someDayList[i].winAmt,self.totalWinAmt);
      }
    }).finally(()=>{
      Indicator.close();
    });
  }
  }
</script>
<style scoped>
  .UDzZc {
    height: calc(100% - 150px);
    position: relative;
  }
  .UDzZc .more{
    height: calc(100% + 52px);
  }
  .UDzZc .more .table-row{
    height: auto !important;
  }
  .UDzZc .more .table-row .col2{
    width: 20% !important;
    word-wrap: break-word !important;
    word-break: normal !important;
  }
  .block {
    background: #fff;
    padding: 8px 10px;
  }
  #top-line {
    width: 100%;
    height: 3px;
    line-height: 0;
    font-size: 0;
    overflow: hidden;
    text-align: center;
    z-index: 1;
    background-color: #0fa6ea;
    background: -webkit-linear-gradient(left,rgba(15,166,234,1) 0,rgba(89,204,24,1) 10%,rgba(15,166,234,1) 60%,rgba(15,166,234,1) 100%);
    background: -moz-linear-gradient(left,rgba(15,166,234,1) 0,rgba(89,204,24,1) 10%,rgba(15,166,234,1) 60%,rgba(15,166,234,1) 100%);
    background: -ms-linear-gradient(left,rgba(15,166,234,1) 0,rgba(89,204,24,1) 10%,rgba(15,166,234,1) 60%,rgba(15,166,234,1) 100%);
    background: linear-gradient(left,rgba(15,166,234,1) 0,rgba(89,204,24,1) 10%,rgba(15,166,234,1) 60%,rgba(15,166,234,1) 100%);
  }
  .segmented {
    -webkit-align-self: center;
    -ms-flex-item-align: center;
    align-self: center;
    display: -webkit-box;
    display: -webkit-flex;
    display: -ms-flexbox;
    display: flex;
    -webkit-flex-wrap: nowrap;
    -ms-flex-wrap: nowrap;
    flex-wrap: nowrap;
  }

  .segmented .button.button-round:first-child {
    border-radius: 29px 0 0 29px;
  }

  .segmented .button:first-child {
    border-radius: 5px 0 0 5px;
    border-left-width: 1px;
    border-left-style: solid;
  }

  .segmented .button {
    border-radius: 0;
    border-left-width: 0;
  }

  .segmented .button.button-round:last-child {
    border-radius: 0 29px 29px 0;
  }

  .button.button-active {
    background: #116397;
    color: #eaeaea;
  }

  .button {
    background: #efeff4;
    font-size: 14px;
    border: 1px solid #0c9eb4;
    color: #163c7d;
    border-radius: 5px;
    line-height: 25px;
    height: 29px;
  }

  .segmented .button, .segmented button {
    width: 100%;
    -webkit-flex-shrink: 1;
    -ms-flex-negative: 1;
    flex-shrink: 1;
    min-width: 0;
  }

  .rough_lines {
    width: 100%;
    height: 10px;
    background-color: rgb(235, 235, 235);
    box-shadow: rgb(187, 187, 187) 0px 1px 1px inset;
  }

  .table {
    height: 100%;
  }

  .table {
    -webkit-box-flex: 1;
    -webkit-flex-grow: 1;
    -ms-flex-positive: 1;
    flex-grow: 1;
    display: -webkit-box;
    display: -webkit-flex;
    display: -ms-flexbox;
    display: flex;
    -webkit-flex-direction: column;
    -ms-flex-direction: column;
    flex-direction: column;
  }

  .table .table-header {
    height: 35px;
    display: -webkit-box;
    display: -webkit-flex;
    display: -ms-flexbox;
    display: flex;
    -webkit-box-pack: justify;
    -webkit-justify-content: space-between;
    -ms-flex-pack: justify;
    justify-content: space-between;
    border-bottom: 1px solid #eaeaea;
    background-color: #fff;
  }
  .table .table-header > div {
    display: -webkit-box;
    display: -webkit-flex;
    display: -ms-flexbox;
    display: flex;
    -webkit-box-pack: center;
    -webkit-justify-content: center;
    -ms-flex-pack: center;
    justify-content: center;
    -webkit-align-items: center;
    -webkit-box-align: center;
    -ms-flex-align: center;
    align-items: center;
    border-right: 1px solid #eaeaea;
    box-sizing: border-box;
  }

  .table .col {
    font-size: 12px;
  }

  .table .col3 {
    -webkit-flex: 3;
    -ms-flex: 3;
    flex: 3;
  }

  .table .col2 {
    -webkit-flex: 2;
    -ms-flex: 2;
    flex: 2;
  }

  .table .col1 {
    -webkit-flex: 1;
    -ms-flex: 1;
    flex: 1;
  }

  .table .col1-5 {
    -webkit-flex: 1.5;
    -ms-flex: 1.5;
    flex: 1.5;
  }

  .table .table-header > div:last-child {
    border-right: 0;
  }

  .table .table-header > div:last-child {
    border-right: 0;
  }

  .table .table-content {
    height: 100%;
    background-color: #ebebeb;
  }

  .table .table-content {
    overflow: auto;
    -webkit-flex: 1;
    -ms-flex: 1;
    flex: 1;
  }

  .table .table-content > .table-row {
    display: -webkit-box;
    display: -webkit-flex;
    display: -ms-flexbox;
    display: flex;
    width: 100%;
    height: 50px;
    -webkit-align-items: center;
    -webkit-box-align: center;
    -ms-flex-align: center;
    align-items: center;
    box-sizing: border-box;
    border-bottom: 1px solid #eaeaea;
    background-color: #fff;
  }

  .table .table-content > .table-row > * {
    text-align: center;
  }

  .table .table-footer {
    background: #FFF;
    border-top: 1px solid rgb(204, 204, 204);
    border-bottom: 1px solid rgb(204, 204, 204);
    display: flex;
    height: 50px;
    -webkit-box-pack: justify;
    justify-content: space-between;
    -webkit-box-align: center;
    align-items: center;
  }
  .table .table-footer > .col {
    text-align: center;
    font-size: 10px;
    font-weight: bold;
  }
  .center-row{
    border-bottom: none !important;
    font-size: 16px;
    height: 100px !important;
    display: block !important;
  }

  .table .table-footer .footer-row {
    width: 100%;
    height: 30px;
    line-height: 30px;
    color: rgb(153, 153, 153);
  }
  .table-footer .footer-row > div {
    width: 48%;
    text-align: right;
    display: inline-block;
    vertical-align: middle;
  }
  .table-footer .footer-row span {
    width: 50%;
    display: inline-block;
    vertical-align: middle;
    padding-left: 10px;
    box-sizing: border-box;
  }
  .jTVanm {
    display: flex;
    height: 3rem;
    line-height: 3rem;
    background-color: rgb(255, 255, 255);
  }
  .jTVanm > div {
    -webkit-box-flex: 1;
    flex-grow: 1;
    text-align: center;
    position: relative;
  }
  .jTVanm .page-info, .jTVanm .page-navi {
    font-size: 14px;
  }
  .jTVanm .page-info .total-row {
    margin-right: 10px;
  }
  .jTVanm .page-info, .jTVanm .page-navi {
    font-size: 14px;
  }
  .jTVanm .page-jump {
    position: absolute;
    top: 0px;
    left: 0px;
    width: 25px;
    right: 0px;
    margin: auto;
  }
  .jTVanm .page-jump input {
    width: 100%;
    height: 23px;
    font-size: 14px;
    font-weight: bold;
    text-align: center;
    box-sizing: border-box;
    border-width: 1px;
    border-style: solid;
    border-color: rgb(204, 204, 204);
    border-image: initial;
    border-radius: 4px;
    margin: 0px;
    background: transparent;
  }
  .jTVanm .page-navi span.disabled {
    pointer-events: none;
    background-color: rgb(204, 204, 204);
    color: rgb(66, 66, 66);
  }
  .jTVanm .page-navi span.prev, .jTVanm .page-navi span.next {
    position: relative;
    padding: 3px 10px;
    border-radius: 4px;
    margin: 0px 15px;
  }
  .jTVanm .page-navi span.disabled a {
    color: rgb(66, 66, 66);
  }
</style>
