<template>
  <!--两周报表-->
<!--  作废注单请在这里加上 line-through-->
  <div class="main history">
    <div class="search">
      <div class="sub">
        <div>
            <a style="margin-right: 5px;cursor:pointer;"  :class="weekSelect=='两周报表'?'selected':''" @click="weekSelectFun('两周报表')">两周报表</a>
            <a style="margin-right: 5px;cursor:pointer;"  :class="weekSelect=='作废注单'?'selected':''" @click="weekSelectFun('作废注单')">作废注单</a>
        </div>
      </div>
    </div>
    <table :class="weekSelect=='作废注单'?'list table line-through':'list table'">
      <thead>
      <tr>
        <th class="date">日期</th>
        <th class="count">注数</th>
        <th class="amount">下注金额</th>
        <th class="amount">有效金额</th>
        <th class="cm">佣金</th>
        <th class="dividend">盈亏</th>
      </tr>
      </thead>
      <tbody>

      <!--所有TABLE鼠标经过加TR 要加 HOVER-->
      <template v-for="(item,i) in prevList">
        <tr @click="sameDayReport(item.date)" style="cursor:pointer" class="hover">
          <td clss="date"><a>{{item.date}}</a></td>
          <td>{{item.num}}</td>
          <td class="money">{{item.betAmt}}</td>
          <td class="money">{{item.actAmt}}</td>
          <td class="money">{{item.comm | moneyFmt}}</td>
          <td class="result"><template v-if="parseFloat(winMoneyFmt(item.winAmt,item.comm)) >= 0">
            <span class="result color">{{winMoneyFmt(item.winAmt,item.comm) | moneyFmt}}</span>
          </template>
            <template v-else>
              <span class="result red">{{winMoneyFmt(item.winAmt,item.comm) | moneyFmt}}</span>
            </template></td>
        </tr>
      </template>
      </tbody>
      <tfoot>
      <tr>
        <th>上周</th>
        <th>{{parseInt(prevtotalNum)}}</th>
        <th class="money">{{parseInt(prevtotalBetAmt)}}</th>
        <th class="money">{{parseInt(prevtotalActAmt)}}</th>
        <th class="money">{{prevtotalComm | moneyFmt}}</th>
        <th class="result">
          <template v-if="parseInt(winMoneyFmt(prevtotalWinAmt,prevtotalComm)) >= 0">
            <span class="result color">{{winMoneyFmt(prevtotalWinAmt,prevtotalComm) | moneyFmt}}</span>
          </template>
          <template v-else>
            <span class="result red">{{winMoneyFmt(prevtotalWinAmt,prevtotalComm) | moneyFmt}}</span>

          </template>
        </th>
      </tr>
      </tfoot>
    </table>
    <table :class="weekSelect=='作废注单'?'list mt table line-through':'list mt table'">
      <thead>
      <tr>
        <th class="date">日期</th>
        <th class="count">注数</th>
        <th class="amount">下注金额</th>
        <th class="amount">有效金额</th>
        <th class="cm">佣金</th>
        <th class="dividend">盈亏</th>
      </tr>
      </thead>
      <tbody>
      <template v-for="(item,i) in nowList">
        <tr @click="sameDayReport(item.date,item.betAmt,winMoneyFmt(item.winAmt,item.comm))" style="cursor:pointer" class="hover dd">
          <td clss="date"><a>{{item.date}}</a></td>
          <td>{{item.num}}</td>
          <td class="money">{{item.betAmt}}</td>
          <td class="money">{{item.actAmt}}</td>
          <td class="money">{{item.comm | moneyFmt}}</td>
          <td class="result"><template v-if="parseInt(winMoneyFmt(item.winAmt,item.comm)) >= 0">
            <span class="result color">{{winMoneyFmt(item.winAmt,item.comm) | moneyFmt}}</span>

          </template>
            <template v-else>
              <span class="result red">{{winMoneyFmt(item.winAmt,item.comm) | moneyFmt}}</span>

            </template></td>
        </tr>
      </template>
      </tbody>
      <tfoot>
      <tr>
        <th>本周</th>
        <th>{{parseInt(nowtotalNum)}}</th>
        <th class="money">{{parseInt(nowtotalBetAmt)}}</th>
        <th class="money">{{parseInt(nowtotalActAmt)}}</th>
        <th class="money">{{nowtotalComm | moneyFmt}}</th>
        <th class="result">
          <template v-if="parseInt(winMoneyFmt(nowtotalWinAmt,nowtotalComm)) >= 0">
            <span class="result color">{{winMoneyFmt(nowtotalWinAmt,nowtotalComm) | moneyFmt}}</span>
          </template>
          <template v-else>
            <span class="result red">{{winMoneyFmt(nowtotalWinAmt,nowtotalComm) | moneyFmt}}</span>

          </template>
        </th>
      </tr>
      </tfoot>
    </table>
  </div>
</template>

<script>
  import {mapGetters, mapActions} from 'vuex'
  import Utils from '@/components/comm/Utils.js'
  import {Indicator} from 'mint-ui'
    export default {
        name: "twoweekreport",
      data(){
        return {
          nowList:[],
          prevList:[],
          nowOrPrevActive:true,
          prevtotalNum:0,
          prevtotalBetAmt:0,
          prevtotalActAmt:0,
          prevtotalComm:0,
          prevtotalWinAmt:0,
          nowtotalNum:0,
          nowtotalBetAmt:0,
          nowtotalActAmt:0,
          nowtotalComm:0,
          nowtotalWinAmt:0,
          weekSelect:'两周报表',
        }
      },
      computed:{
        ...mapGetters(['gameMenu',  'showMenu', 'gameId','game']),
      },
      methods:{
        weekSelectFun(title){
          if(this.weekSelect == title){
            return
          }
          this.weekSelect = title;
          if(title=='作废注单'){
            this.weekReportGet({'winOrLoserState':'VOID'});
          }else{
            this.weekReportGet();
          }
        },
        sameDayReport(date,betAmt,winAmt){
          let params = {selectDate:date,betAmt:betAmt,winAmt:winAmt};
          if(this.weekSelect=='作废注单'){
            this.$set(params,"status",'VOID');
          }
          this.$router.push({path:'/betssettlement/',query:params});
        },
        winMoneyFmt(win,comm){
          let total = Utils.NumberAdd(win,comm);
          return Utils.formatMoney(total,2);
        },
        nowPrevActive(){
          let self = this;
          self.prevtotalNum = 0;
          self.prevtotalBetAmt = 0;
          self.prevtotalActAmt = 0;
          self.prevtotalComm = 0;
          self.prevtotalWinAmt = 0;
          self.nowtotalNum = 0;
          self.nowtotalBetAmt = 0;
          self.nowtotalActAmt = 0;
          self.nowtotalComm = 0;
          self.nowtotalWinAmt = 0;
          for(let i = 0;i<self.nowList.length;i++){
            self.nowtotalNum = Utils.NumberAdd(self.nowList[i].num,self.nowtotalNum);
            self.nowtotalBetAmt = Utils.NumberAdd(self.nowList[i].betAmt,self.nowtotalBetAmt);
            self.nowtotalActAmt = Utils.NumberAdd(self.nowList[i].actAmt,self.nowtotalActAmt);
            self.nowtotalComm = Utils.NumberAdd(self.nowList[i].comm,self.nowtotalComm);
            self.nowtotalWinAmt = Utils.NumberAdd(self.nowList[i].winAmt,self.nowtotalWinAmt);
          }

          for(let i = 0;i<self.prevList.length;i++){
            self.prevtotalNum = Utils.NumberAdd(self.prevList[i].num,self.prevtotalNum);
            self.prevtotalBetAmt = Utils.NumberAdd(self.prevList[i].betAmt,self.prevtotalBetAmt);
            self.prevtotalActAmt = Utils.NumberAdd(self.prevList[i].actAmt,self.prevtotalActAmt);
            self.prevtotalComm = Utils.NumberAdd(self.prevList[i].comm,self.prevtotalComm);
            self.prevtotalWinAmt = Utils.NumberAdd(self.prevList[i].winAmt,self.prevtotalWinAmt);
          }
        },
        selectSameDay(date){
          this.$router.push({path:'/samedayhistory/',query:{selectDate:date}});
        },
        weekReportGet(params){
          let self = this;
          Indicator.open({text:'加载中...'});
          this.$api.Lottery.getReport(params).then(val=>{
            if(val.code==10000){
              self.nowList = val.data.now;
              self.prevList = val.data.prev;
            }
          }).finally(()=>{
            Indicator.close();
            self.nowPrevActive();

          });

        }
      },
      mounted(){
        this.weekReportGet();
      },
      filters:{
        moneyFmt(val){
          if(!val || 0 == val){
            return '0.00';
          }
          return Utils.formatMoney(val, 2);
        }
      }
    }
</script>

<style scoped>
  .table th, .table td {
    text-align: center;
    height: 28px;
    padding: 0;
  }

  tfoot tr td, tfoot tr th {
    height: 28px;
    background-color: #e6e6fa !important;
  }

  .list .hover {
    background: none repeat 0 0 #c3d9f1 !important;
  }

  .color {
    color: #2836f4 !important;
    font-weight: bold;
  }

  .nodata {
    height: 30px;
    line-height: 30px;
    font-weight: normal;
  }
  /*两周报表*/
  .history .sub {
    color: #999;
    height: 36px;
    line-height: 36px;
  }

  .history .sub .selected{
    color: #fff;
    border: 1px solid #ff6e39;
    background: -webkit-gradient(linear, left top, left bottom, from(#ff8d52), to(#ff692a));
    background: linear-gradient(to bottom, #ff8d52 0%, #ff692a 100%);
  }

  .history .sub a {
    color: #444;
    padding: 0.25em 1em;
    -webkit-box-sizing: border-box;
    box-sizing: border-box;
    border: 1px solid #e6e6e6;
    background: -webkit-gradient(linear, left top, left bottom, from(#f1f1f1), to(#e2e2e2));
    background: linear-gradient(to bottom, #f1f1f1 0%, #e2e2e2 100%);
  }

  .history .today .date a {
    color: blue;
  }

  .history td {
    width: 16.67%;
  }

  .search .title {
    height: 28px;
    line-height: 28px;
    color: #060606;
    display: inline-block;
    font-weight: bold;
  }

  .history a:hover {
    color: RED;
    text-decoration: none;
  }

  .mt {
    margin-top: 20px;
  }
</style>
