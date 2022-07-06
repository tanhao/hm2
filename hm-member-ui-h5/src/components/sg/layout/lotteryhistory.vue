<template>
  <div class="home">
    <div class="sc-bZQynM OQRyf gm_main">
      <my-header title="两周报表" historyBack="true" back="true" left="true"></my-header>
      <div class="sc-htoDjs UDzZc">
        <div id="top-line"></div>
        <div class="table more">
          <div class="table-header">
            <div class="col col2">注单号</div>
            <div class="col col3">类型</div>
            <div class="col col2">玩法</div>
            <div class="col col2">下注金额</div>
            <div class="col col2">结果</div>
          </div>
          <div :class="params.status!='VOID'?'table-content':'table-content line-through'">
            <template v-for="(list,i) in lotteryHistoryList">
              <a  class="table-row">
                <div class="col col2"><div class="green_color">{{list.orderId}}</div><div>{{list.betTime*1000 | formatDate}}</div><div>{{list.betTime*1000 | formatDateTwo}}</div></div>
                <div class="col col3">
                  <template v-for="(obj, i) in gameMenu">
                    <div v-if="parseInt(obj.index)===list.lotteryId">{{$t(obj.title)}}</div>
                  </template>
                  <div class="green_color">第 {{list.gameNo}} 期</div><div class="blue_color">盘口（{{list.market}}）</div></div>

                <div class="col col1-5">
                  <div class="blue_color">
                    <span class="blue_color" v-if="list.betContent">{{$t(JSON.parse(list.keyName).playKey)}}{{$t(list.oddsKey.toUpperCase())}}</span>
                    <span class="blue_color" v-if="!list.betContent"><span class="blue_color" v-if="JSON.parse(list.keyName).categoryKey=='lm'">{{$t(JSON.parse(list.keyName).categoryKey)}}</span>{{$t(JSON.parse(list.keyName).playKey)}}<span v-if="/^[0-9]\d*$/.test(list.oddsKey)" class="blue_color">{{$t(list.oddsKey)}}</span><span v-if="!/^[0-9]\d*$/.test(list.oddsKey)" class="blue_color">{{$t(list.oddsKey.toUpperCase())}}</span></span>
                    <span class="blue_color" v-if="list.betContent">@{{list.betContent}}</span>
                  @<span class = "red_color">{{list.odds}}</span>
                  </div>
                </div>
                <div class="col col1-5">{{list.betAmt}}</div>
                <div class="col col2">
                  <template v-if="parseFloat(list.winAmt) >= 0">
                    <span class="blue_color">{{list.winAmt | moneyFmt}}</span>

                  </template>
                  <template v-else>
                    <span class="red_color">{{list.winAmt | moneyFmt}}</span>

                  </template>
                  <br/>
                  <span class="blue_color">{{list.water}}</span>
                </div>
              </a>
            </template>
            <!---->
          </div>
          <div class="sc-cSHVUG cfhhpx">
            <div class="sc-kGXeez ljfNBe">
              <div class="Pagination">
                <div title="共">共<span>{{totalPage}}</span>页</div>
                <a class="tips" title="首页" @click="pageDownOrUp(1)">首页</a>
                <a class="tips" title="上一页" @click="pageDownOrUp(params.page-1)">上一页</a>
                <div><input class="form-control" type="text" oninput="this.value = this.value.replace(/[^0-9]/g, '');" @blur="inputPages" v-model="showPage"></div>
                <a class="tips" title="下一页" @click="pageDownOrUp(params.page+1)">下一页</a>
                <a class="tips" title="末页" @click="pageDownOrUp(totalPage)">末页</a>
              </div>
            </div>
          </div>
          <div class="table-footer center-row">
            <div id="top-line"></div>
            <div class="footer-row">
              <div>注数</div>
              <span>{{totalNum}}</span>
            </div>
            <div class="footer-row">
              <div>下注金额(计)</div>
              <span>{{parseInt(totalBetAmt)}}</span>
            </div>
            <div class="footer-row">
              <div>结果(总计)</div>
              <span><template v-if="parseFloat(totalWinAmt) >= 0">
                <span class="blue_color">{{totalWinAmt | moneyFmt}}</span>

              </template>
                <template v-else>
                  <span class="red_color">{{totalWinAmt | moneyFmt}}</span>

                </template></span>
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
  import Bet from '@/axios/api-bet.js'
  import Utils from '@/components/comm/Utils.js'
  import {Indicator} from 'mint-ui'
  import to from "await-to-js";
  export default {
    components: {
      MyHeader,
      MyFooter,
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
          page:1
        },
        showPage:1,
        totalPage:1,
      }
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
        return formatDate(date, 'yyyy-MM-dd'); // 年月日 格式自己定义   'yyyy : MM : dd'  例 2018年12月5日的格式
      },
      formatDateTwo(time){
        var date = new Date(time);
        return formatDate(date, 'hh:mm:ss');
      }
    },
    computed: {
      ...mapGetters(['gameMenu',  'showMenu', 'gameId','historyReturn']),

  }
  ,
  created()
  {

  }
  ,
  methods:{
    ...mapActions(['setHistoryReturn']),
    inputPages(){
      if(this.showPage!=''){

        if(this.params.page == this.totalPage && this.showPage>this.totalPage){
          this.showPage = this.totalPage;
          return;
        }
        this.params.page = this.showPage;
        if(this.showPage>this.totalPage){
          this.showPage = this.totalPage;
          this.params.page = this.totalPage;
        }else if(this.showPage==0){
          this.params.page = 1;
          this.showPage = 1;
        }
      }
      this.selectLotteryHistory();
    },
    pageDownOrUp(page){
      if(page==0||page>this.totalPage){
        return;
      }
      this.params.page = page;
      this.showPage = this.params.page;
      this.selectLotteryHistory();
    },
    async selectLotteryHistory(){
      let self = this;
      Indicator.open({text:'加载中...'});
      self.totalBetAmt = 0;
      self.totalWinAmt = 0;
      self.params.lotteryId = this.$route.query.lotteryId;
      self.params.accountDay = this.$route.query.selectDate;
      self.params.status = this.$route.query.status?this.$route.query.status:self.params.status;
      self.setHistoryReturn({'name':'samedayhistory','query':{'lotteryId':self.params.lotteryId,'selectDate':self.params.accountDay,'status':self.params.status},'mode':0});
      let waterTotalMoney = 0;
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
        self.totalPage = data.data.total % self.params.size==0?data.data.total/self.params.size:parseInt(data.data.total/self.params.size)+1;
        if(data.data.total==0){
          self.showPage = 1;
          self.params.page = 1;
        }
      }

      self.totalNum = self.lotteryHistoryList.length;
      Indicator.close();
    }
  }
  ,
  mounted(){
    this.selectLotteryHistory();

  }
  }
</script>
<style scoped>
  .UDzZc {
    height: calc(100% - 100px);
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
  .cfhhpx {
    background: #fff;
    height: 45px;
    width: 100%;
    display: -webkit-box;
    display: -ms-flexbox;
    display: flex;
    -webkit-box-pack: space-evenly;
    -ms-flex-pack: space-evenly;
    justify-content: space-evenly;
    -webkit-box-align: center;
    -ms-flex-align: center;
    align-items: center;
    border-bottom: 1px solid #ccc;
  }

  .tips{
    font-size: 14px;
    margin: 0 8px;
  }

  .ljfNBe {
    -ms-flex: 3;
    -webkit-box-flex: 3;
    flex: 3;
    display: -webkit-box;
    display: -ms-flexbox;
    display: flex;
    -webkit-box-pack: space-around;
    -ms-flex-pack: space-around;
    justify-content: space-around;
    -webkit-box-align: center;
    -ms-flex-align: center;
    align-items: center;
    height: 100%;
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
