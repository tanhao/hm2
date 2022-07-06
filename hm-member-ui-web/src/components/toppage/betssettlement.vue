<template>
  <div class="historyback">
    <div class="sub">
      <div>
        <a style="margin-right: 5px;cursor:pointer;" v-if="returnBtn" @click="returnWeek" class="selected">返回上一级</a>
      </div>
    </div>
      <!--未结已结样式通用-->
      <table :class="params.status=='VOID'?'list table line-through':'list table'">
        <thead>
        <tr>
          <th>注单号</th>
          <th>时间</th>
          <th>类型</th>
          <th>玩法</th>
          <th>盘口</th>
          <th>下注金额</th>
          <th width="100px">退水(%)</th>
          <th>结果</th>
        </tr>
        </thead>
        <tbody>
        <tr v-if="orderList.length==0" class="">
          <th class="nodata" colspan="8">暂无数据!</th>
        </tr>
        <template v-for="(item,index) in orderList">
          <tr class="">
            <td>{{item.orderId}}</td>
            <td class="time">{{item.betTime*1000 | formatDate}} <br/>{{item.betTime*1000 | formatDateTwo}}</td>
            <td class="period"><span class="lottery">
              <template v-for="(obj, i) in gameMenu">
                  <span v-if="parseInt(obj.id)===item.lotteryId">{{$t(obj.lotteryKey)}}</span>
                </template>
            </span><br><span class="draw_number">第 {{item.gameNo}} 期</span></td>
            <td class="contents">
              <template class="blue_color" v-if="item.lotteryId==101 || item.lotteryId==102 || item.lotteryId==103 || item.lotteryId==104 ||
                  item.lotteryId==105 || item.lotteryId==106 || item.lotteryId==107 || item.lotteryId==108 || item.lotteryId==109 || gameId==110 || gameId==111 || gameId==112">
                  <span ><!--<span v-if="JSON.parse(item.keyName).categoryKey=='lm'">{{$t(JSON.parse(item.keyName).categoryKey)}}</span>-->{{$t(JSON.parse(item.keyName).playKey)}}
                    <span v-if="/^[0-9]\d*$/.test(item.oddsKey)">{{item.oddsKey}}</span>
                    <span v-if="!/^[0-9]\d*$/.test(item.oddsKey)">{{$t(item.oddsKey.toUpperCase())}}</span></span>
              </template>

              <template class="blue_color" v-if="item.lotteryId==201 || item.lotteryId==202 || item.lotteryId==203 || item.lotteryId==204 ||
                  item.lotteryId==205 || item.lotteryId==206 || item.lotteryId==207 || item.lotteryId==208 || item.lotteryId==209 || item.lotteryId==210
                  || item.lotteryId==211">
                    <span v-if="item.betContent">{{$t(JSON.parse(item.keyName).playKey)}}
                      <span v-if="JSON.parse(item.keyName).playKey.indexOf('zx')==-1">
                        <span v-if="/^[0-9]\d*$/.test(item.oddsKey)">{{item.oddsKey}}</span>
                        <span v-if="!/^[0-9]\d*$/.test(item.oddsKey)">{{$t(item.oddsKey.toUpperCase())}}</span>
                      </span>
                    </span>
                <span v-if="!item.betContent">
                      <span v-if="JSON.parse(item.keyName).playKey=='t_lhh' && JSON.parse(item.keyName).categoryKey=='lm'">{{$t('thesum')}}</span>
                      <span v-else-if="JSON.parse(item.keyName).playKey=='t_lhh' && JSON.parse(item.keyName).categoryKey=='lhh'">{{$t('dtt')}}</span>
                      <span v-else>{{$t(JSON.parse(item.keyName).playKey)}}</span>
                      <span v-if="JSON.parse(item.keyName).playKey.indexOf('qiu')==-1 && JSON.parse(item.keyName).playKey!='t_lhh' && JSON.parse(item.keyName).playKey.indexOf('2z')==-1
                    && JSON.parse(item.keyName).playKey.indexOf('3z')==-1 && JSON.parse(item.keyName).playKey.indexOf('hs')==-1&& JSON.parse(item.keyName).playKey.indexOf('hws')==-1
                    && JSON.parse(item.keyName).playKey.indexOf('kd')==-1 && JSON.parse(item.keyName).playKey!='nn' && JSON.parse(item.keyName).playKey!='sh' && JSON.parse(item.keyName).playKey!='top3'
                            && JSON.parse(item.keyName).playKey!='mid3' && JSON.parse(item.keyName).playKey!='end3'">
                        {{$t(item.categoryKey)}}</span>
                      <span v-if="/^[0-9]\d*$/.test(item.oddsKey)">{{item.oddsKey}}</span>
                      <span v-if="!/^[0-9]\d*$/.test(item.oddsKey)">{{$t(item.oddsKey.toUpperCase())}}</span>

                    </span>
                <span v-if="item.betContent">{{item.betContent}}</span>
              </template>
              <template class="blue_color" v-if="item.lotteryId==301 || item.lotteryId==302 || item.lotteryId==303 || item.lotteryId==304">
                <span v-if="item.betContent">{{$t(JSON.parse(item.keyName).playKey)}}{{$t(item.oddsKey)}}</span>
                <span v-if="!item.betContent">
                                    <span v-if="JSON.parse(item.keyName).playKey=='sum'">{{$t('THESUM')}}</span>
                                    <span v-else>{{$t(JSON.parse(item.keyName).playKey)}}</span>


                                    <span v-if="/^[0-9]\d*$/.test(item.oddsKey)">{{item.oddsKey}}</span>
                                    <span v-if="!/^[0-9]\d*$/.test(item.oddsKey)">{{$t(item.oddsKey.toUpperCase())}}</span></span>
                <span v-if="item.betContent">{{item.betContent}}</span>
              </template>
              <template class="blue_color" v-if="item.lotteryId==401 || item.lotteryId==402 || item.lotteryId==403
                          || item.lotteryId==404 || item.lotteryId==405">
                {{$t(JSON.parse(item.keyName).playKey)}}
                <span v-if="/^[0-9]\d*$/.test(item.oddsKey)">{{item.oddsKey}}</span>
                <span v-if="!/^[0-9]\d*$/.test(item.oddsKey)">{{$t(item.oddsKey.toUpperCase())}}</span>
              </template>
              <template v-if="item.lotteryId==801">
                <span v-if="item.betContent">{{$t(JSON.parse(item.keyName).playKey)}}</span>
                <span v-if="!item.betContent">
                                    {{$t(JSON.parse(item.keyName).playKey)}}
                                    <span v-if="/^[0-9]\d*$/.test(item.oddsKey)">{{item.oddsKey}}</span>
                                    <span v-if="!/^[0-9]\d*$/.test(item.oddsKey)">{{$t(item.oddsKey.toUpperCase())}}</span></span>
                <span v-if="item.betContent">{{item.betContent}}</span>
              </template>

              <template v-if="item.lotteryId==701">
                <span v-if="JSON.parse(item.keyName).playKey=='t_lhh'">{{$t('zhlh')}}</span>
                <span v-if="JSON.parse(item.keyName).playKey!='t_lhh'">{{$t(JSON.parse(item.keyName).playKey)}}</span>
                <span v-if="/^[0-9]\d*$/.test(item.oddsKey)">{{item.oddsKey}}</span>
                <span v-if="!/^[0-9]\d*$/.test(item.oddsKey)">{{$t(item.oddsKey)}}</span>
              </template>
              <template v-if="item.lotteryId==501 || item.lotteryId==502">

                <span >{{$t(JSON.parse(item.keyName).playKey)}}</span>

                <span v-if="/^[0-9]\d*$/.test(item.oddsKey)">{{item.oddsKey}}</span>
                <span v-if="!/^[0-9]\d*$/.test(item.oddsKey)">{{$t(item.oddsKey)}}</span>
              </template>
              <template v-if="item.lotteryId==601">

                                <span v-if="JSON.parse(item.keyName).playKey!='q2zx' && JSON.parse(item.keyName).playKey!='q3zx'
                                   && JSON.parse(item.keyName).playKey!='q2gx' && JSON.parse(item.keyName).playKey!='q3gx'&& JSON.parse(item.keyName).playKey.indexOf('rx')==-1">{{$t(JSON.parse(item.keyName).playKey)}}</span>

                <span v-if="/^[0-9]\d*$/.test(item.oddsKey)">{{item.oddsKey}}</span>
                <span v-if="!/^[0-9]\d*$/.test(item.oddsKey)">{{$t(item.oddsKey)}}</span>
                <span v-if="item.betContent">{{item.betContent}}</span>
              </template>
               @ <span class="odds">{{item.odds}}</span></td>
            <td class="range">{{item.market}}</td>
            <td class="amount">{{item.betAmt}}</td>
            <td>{{item.commPct}}%</td>
            <template v-if="parseFloat(item.winAmt) >= 0">
              <td class="result color blue">{{item.winAmt | moneyFmt}}<br/><span>{{item.water}}</span><br/><span style="color:#302F2F">{{item.status=='REDIVIDEND'?'重派':''}}</span></td>

            </template>
            <template v-if="parseFloat(item.winAmt) < 0">
              <td class="result red">{{item.winAmt | moneyFmt}}<br/><span class="result color blue">{{item.water}}</span><br/><span style="color:#302F2F">{{item.status=='REDIVIDEND'?'重派':''}}</span></td>
            </template>
            <!--<td class="result color">{{item.winAmt | moneyFmt}}</td>-->
          </tr>
        </template>
        </tbody>
        <tfoot>
        <tr>
          <th></th>
          <th></th>
          <th></th>
          <th></th>
          <th>合计</th>
          <th>{{parseInt(pageOrderMoney)}}</th>
          <th></th>

          <template v-if="parseFloat(this.pageOrderWin) >= 0">
            <th class="result blue">{{pageOrderWin | moneyFmt}}</th>

          </template>
          <template v-if="parseFloat(this.pageOrderWin) < 0">
            <th class="result red" style="color:red;">{{pageOrderWin | moneyFmt}}</th>

          </template>
        </tr>
        <tr >
          <th></th>
          <th></th>
          <th></th>
          <th></th>
          <th>总计</th>
          <th>{{parseInt(totalOrderMoney)}}</th>
          <th></th>

          <template v-if="parseFloat(this.totalOrderWin) >= 0">
            <th class="result blue">{{totalOrderWin | moneyFmt}}</th>

          </template>
          <template v-if="parseFloat(this.totalOrderWin) < 0">
            <th class="result red" style="color:red;">{{totalOrderWin | moneyFmt}}</th>

          </template>
        </tr>
        </tfoot>
      </table>
      <!--结束


<!--公用分页样式,完成后放回最下面-->
    <div id="nav">
      <ul class="pagination margin">
        <li class="pageall">共<span>{{totalPage}}</span>页</li>
        <li @click="selectPage(params.page-1)"><a >❮</a></li>
        <template v-for="(item,index) in pageList">
          <li @click="selectPage(item)"><a  :class="item==params.page?'active':''">{{item}}</a></li>
        </template>
        <li @click="selectPage(params.page+1)"><a >❯</a></li>
        <li class="pageall">跳至
          <input style="width:40px;height: 20px;" ref="jumpPageValue" @keyup.enter="jumpPageFun($event.target.value)" @blur="jumpPageFun($event.target.value)" onkeyup="this.value=this.value.replace(/[^0-9-]+/,'');" type="number"/> 页</li>
      </ul>
    </div>
  </div>
</template>

<script>
  import {mapGetters, mapActions} from 'vuex'
  import Utils from '@/components/comm/Utils.js'
  import {Indicator} from 'mint-ui'
  import to from "await-to-js";
  export default {
    name: "bets",
    data(){
      return{
        orderList:[],
        orderTotal:'',
        pageOrderMoney:0.00,
        pageOrderWin:0.00,
        params:{
          lotteryId:null,
          status:'DIVIDEND',
          accountDay:Utils.formatDate(new Date(),'yyyy-MM-dd'),
          size:20,
          page:1,
        },
        pageList:[],
        jumpPage:1,
        currentPage:1,
        totalPage:1,
        totalOrderMoney:0.00,
        totalOrderWin:0.00,
        currentDate:new Date(),
        weekReportPage:false,
        returnBtn:false,
      }
    },
    filters: {
      formatDate(time) {
        var date = new Date(time);
        return Utils.formatDate(date, 'yyyy-MM-dd'); // 年月日 格式自己定义   'yyyy : MM : dd'  例 2018年12月5日的格式
      },
      formatDateTwo(time){
        var date = new Date(time);
        return Utils.formatDate(date, 'hh:mm:ss');
      },
      moneyFmt(val){
        if(!val){
          return '0.00';
        }
        return Utils.formatMoney(val, 2);
      },
      idFmt(val){
        if(val){
          let valSubString = val.toString();
          return valSubString.substring(8);
        }

      }
    },
    computed:{
      ...mapGetters(['gameMenu','showMenu','gameId']),
    },
    methods:{
      returnWeek(){
        this.$router.back();
      },
      jumpPageFun(pageValue){
        if(this.jumpPage==pageValue){
          return;
        }
        this.jumpPage = pageValue;
        this.jumpPage==0?this.jumpPage = 1:this.jumpPage>this.totalPage?this.jumpPage = this.totalPage:1;
        this.params.page = this.jumpPage;
        this.selectPage(this.jumpPage);
      },
      pageListDownOrUp(size){
        let self = this;
        self.pageList=[];
        self.currentPage=parseInt(size);
        if(this.$refs['jumpPageValue']){
          this.$refs['jumpPageValue'].value = self.currentPage;
        }
        for(let i = 1;i<=self.totalPage;i++){
          if(self.pageList.length==7){
            return;
          }
          if(i-self.currentPage>=-3 || i-self.currentPage>=3){
            self.pageList.push(i);
          }
          if(i==self.totalPage){
            break;
          }
        }
      },
      selectPage(page){
        if(page==0||page>this.totalPage){
          return;
        }
        this.params.page = page;
        this.getBetList();
        this.pageListDownOrUp(page);


      },
      async getBetList(){
        Indicator.open({text:'加载中...'});

        let self = this;
        self.pageOrderMoney = 0.00;
        self.pageOrderWin = 0.00;
        let totalWater = 0.00;



        let [err,data] = await to(this.$api.Bet.betList(self.params));
        if(data.success){
          self.orderList = data.data.dataList;
          for(let i = 0;i<self.orderList.length;i++){
            self.pageOrderMoney=Utils.NumberAdd(self.pageOrderMoney,self.orderList[i].betAmt);
            self.pageOrderWin =Utils.NumberAdd(self.pageOrderWin,self.orderList[i].winAmt);
            self.$set(self.orderList[i],'water',Utils.NumberDiv(Utils.NumberMul(self.orderList[i].betAmt,self.orderList[i].commPct),100.00,3));
            totalWater = Utils.NumberAdd(self.orderList[i].water,totalWater);
          }
          self.orderTotal = self.orderList.length;
          self.pageOrderWin = Utils.NumberAdd(self.pageOrderWin,totalWater);
          self.totalPage = data.data.total%self.params.size==0?data.data.total/self.params.size:(parseInt(data.data.total/self.params.size)+1);
          self.pageList=[];
          self.pageListDownOrUp(self.currentPage);
        }
        Indicator.close();
      }
    },
    mounted(){
      let date = new Date();
      let hh = date.getHours();
      if (hh < 7) {
        date.setDate(date.getDate() - 1);
        let dateParam = Utils.formatDate(date, 'yyyy-MM-dd');
        let spliceArr = dateParam.split('-');
        this.currentDate = new Date(spliceArr[0]+'-'+spliceArr[1]+'-'+spliceArr[2]);
        this.params.accountDay = Utils.formatDate(new Date(spliceArr[0]+'-'+spliceArr[1]+'-'+spliceArr[2]),'yyyy/MM/dd');
      }
      this.totalOrderMoney = this.$route.query.betAmt;
      this.totalOrderWin = this.$route.query.winAmt;
      let paramDate = this.$route.query.selectDate;
      let paramStatus = this.$route.query.status;
      if(paramDate){
        this.params.accountDay = paramDate;
        this.returnBtn = true;
        //this.weekReportPage = true;
      }
      if(paramStatus){
        this.params.status = paramStatus;
      }

      this.getBetList();
    }
  }
</script>

<style>
  .table th, .table td {
    text-align: center;
    height: 28px;
    padding: 0;
  }

  .historyback .sub {
    color: #999;
    height: 36px;
    line-height: 36px;
  }

  .historyback .sub .selected{
    color: #fff;
    border: 1px solid #ff6e39;
    background: -webkit-gradient(linear, left top, left bottom, from(#ff8d52), to(#ff692a));
    background: linear-gradient(to bottom, #ff8d52 0%, #ff692a 100%);
  }

  .historyback .sub a {
    color: #444;
    padding: 0.25em 1em;
    -webkit-box-sizing: border-box;
    box-sizing: border-box;
    border: 1px solid #e6e6e6;
    background: -webkit-gradient(linear, left top, left bottom, from(#f1f1f1), to(#e2e2e2));
    background: linear-gradient(to bottom, #f1f1f1 0%, #e2e2e2 100%);
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
  .history {
    padding-left: 9px;
  }

  .history a {
    color: #35406d;
    text-decoration: none;
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

  /*个人资讯*/

  .user_info_table tbody td {
    text-align: left;
    text-indent: 22px;
  }

  .info_body {
    margin: 0;
    padding: 0;
    border: 0;
    list-style: none;
    overflow: hidden;
  }

  /*修改密码*/
  .password tbody th {
    width: 231px;
    text-align: right;
    padding-right: 36px;
  }
  .password tbody td {
    text-align: left;
    text-indent: 22px;
  }
  .password tbody input {
    border: 1px solid #b0b0b0;
    height: 14px;
    padding: 1px;
  }
  a{
    cursor:pointer;
  }
  input::-webkit-outer-spin-button,
  input::-webkit-inner-spin-button {
    -webkit-appearance: none;
  }
  input[type="number"]{
    -moz-appearance: textfield;
  }


</style>
