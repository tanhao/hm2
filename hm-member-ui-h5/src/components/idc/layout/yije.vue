<template>
  <div id="idc">
    <div class="home">
      <div class="sc-bZQynM OQRyf">
        <div class="sc-bdVaJa jaFIbq otherpage">
          <my-header top="true" title="已结"></my-header>
          <div class="list">
            <ul>
              <li class="item-content item-input item-input-with-value">
                <div class="item-inner">
                  <div class="item-title item-label">日期</div>
                  <div class="item-input-wrap">
                    <input  @click="openPicker()" :value="showDate" readonly>
                    <mt-datetime-picker
                      ref="picker"
                      type="date"
                      v-model="currentDate"
                      @confirm="handleChange">
                    </mt-datetime-picker>
                  </div>
                </div>
              </li>
            </ul>
          </div>
          <div class="ui-content jqm_content">

            <table class="jqm_xd_table" cellpadding="0" cellspacing="0" border="0">
              <tbody>
                <tr>
                  <th>注单号/时间</th>
                  <th>下注类型</th>
                  <th>注单明细</th>
                  <th>下注金额</th>
                  <th>输赢</th>
                </tr>
              </tbody>
            </table>
            <div class="detailist">
              <table class="jqm_xd_table" cellpadding="0" cellspacing="0" border="0">
                <tbody>
                  <template v-for="(item, index) in orderList">
                    <tr style="background-color: rgb(235, 215, 216);">
                      <td colspan="2">{{item.orderId}}<br/>{{item.betTime *1000 | formatDateTwo}}<br/>{{item.gameNo}}</td>
                      <td>
                        <template v-for="(obj, i) in gameMenu">
                          <span v-if="parseInt(obj.index)===item.lotteryId">{{$t(obj.title)}}</span>
                        </template>
                        <template v-if="gameId==101 || gameId==102 || gameId==103 || gameId==104 ||
                          gameId==105 || gameId==106 || gameId==107 || gameId==108 || gameId==109 || gameId==110 || gameId==111 || gameId==112">
                          <span ><!--<span v-if="JSON.parse(item.keyName).categoryKey=='lm'">{{$t(JSON.parse(item.keyName).categoryKey)}}</span>-->{{$t(JSON.parse(item.keyName).playKey)}}
                            <span v-if="/^[0-9]\d*$/.test(item.oddsKey)">{{$t(item.oddsKey)}}</span>
                            <span v-if="!/^[0-9]\d*$/.test(item.oddsKey)">{{$t(item.oddsKey.toUpperCase())}}</span></span>
                        </template>
                        <template v-if="gameId==201 || gameId==202 || gameId==203 || gameId==204 ||
                            gameId==205 || gameId==206 || gameId==207 || gameId==208 || gameId==209 || gameId==210 || gameId==211">
                          <span v-if="item.betContent">{{$t(JSON.parse(item.keyName).playKey)}}
                            <span v-if="JSON.parse(item.keyName).playKey.indexOf('zx')==-1">
                              <span v-if="/^[0-9]\d*$/.test(item.oddsKey)">{{$t(item.oddsKey)}}</span>
                              <span v-if="!/^[0-9]\d*$/.test(item.oddsKey)">{{$t(item.oddsKey.toUpperCase())}}</span>
                            </span>
                          </span>
                          <span v-if="!item.betContent">
                            <span v-if="JSON.parse(item.keyName).playKey=='t_lhh'">{{$t('thesum')}}</span>
                            <span v-else>{{$t(JSON.parse(item.keyName).playKey)}}</span>
                            <span v-if="JSON.parse(item.keyName).playKey.indexOf('qiu')==-1 && JSON.parse(item.keyName).playKey!='t_lhh' && JSON.parse(item.keyName).playKey.indexOf('2z')==-1
                            && JSON.parse(item.keyName).playKey.indexOf('3z')==-1 && JSON.parse(item.keyName).playKey.indexOf('hs')==-1&& JSON.parse(item.keyName).playKey.indexOf('hws')==-1
                            && JSON.parse(item.keyName).playKey.indexOf('kd')==-1 && JSON.parse(item.keyName).playKey!='nn' && JSON.parse(item.keyName).playKey!='sh' && JSON.parse(item.keyName).playKey!='top3'
                            && JSON.parse(item.keyName).playKey!='mid3' && JSON.parse(item.keyName).playKey!='end3'">
                              {{$t(item.categoryKey)}}</span>
                            <span v-if="/^[0-9]\d*$/.test(item.oddsKey)">{{$t(item.oddsKey)}}</span>
                            <span v-if="!/^[0-9]\d*$/.test(item.oddsKey)">{{$t(item.oddsKey.toUpperCase())}}</span>

                          </span>
                          <span v-if="item.betContent">{{item.betContent}}</span>
                        </template>
                        <template v-if="gameId==301 || gameId==302 || gameId==303 || gameId==304">
                          <span v-if="item.betContent">{{$t(JSON.parse(item.keyName).playKey)}}{{$t(item.oddsKey.toUpperCase())}}</span>
                          <span v-if="!item.betContent">
                            <span v-if="JSON.parse(item.keyName).playKey=='sum'">{{$t('THESUM')}}</span>
                            <span v-else>{{$t(JSON.parse(item.keyName).playKey)}}</span>


                            <span v-if="/^[0-9]\d*$/.test(item.oddsKey)">{{$t(item.oddsKey)}}</span>
                            <span v-if="!/^[0-9]\d*$/.test(item.oddsKey)">{{$t(item.oddsKey.toUpperCase())}}</span></span>
                          <span v-if="item.betContent">{{item.betContent}}</span>
                        </template>
<!--                        <template v-if="gameId==401 || gameId==402 || gameId==403
                          || gameId==404 || gameId==405">
                          {{$t(JSON.parse(item.keyName).playKey)}}
                          <span v-if="/^[0-9]\d*$/.test(item.oddsKey)">{{$t(item.oddsKey)}}</span>
                          <span v-if="!/^[0-9]\d*$/.test(item.oddsKey)">{{$t(item.oddsKey.toUpperCase())}}</span>
                        </template>-->
                        <template v-if="gameId==401 || gameId==402 || gameId==403">
                          <span v-if="item.betContent">{{$t(JSON.parse(item.keyName).playKey)}}</span>
                          <span v-if="!item.betContent">
                            {{$t(JSON.parse(item.keyName).playKey)}}
                            <span v-if="/^[0-9]\d*$/.test(item.oddsKey)">{{$t(item.oddsKey)}}</span>
                            <span v-if="!/^[0-9]\d*$/.test(item.oddsKey)">{{$t(item.oddsKey.toUpperCase())}}</span></span>
                          <span v-if="item.betContent">{{item.betContent}}</span>
                        </template>
                        <template v-if="gameId==701">
                          <span v-if="JSON.parse(item.keyName).playKey=='t_lhh'">{{$t('zhlh')}}</span>
                          <span v-if="JSON.parse(item.keyName).playKey!='t_lhh'">{{$t(JSON.parse(item.keyName).playKey)}}</span>
                          <span v-if="/^[0-9]\d*$/.test(item.oddsKey)">{{$t(item.oddsKey)}}</span>
                          <span v-if="!/^[0-9]\d*$/.test(item.oddsKey)">{{$t(item.oddsKey)}}</span>
                        </template>
                        <template v-if="gameId==501 || gameId==502">

                          <span >{{$t(JSON.parse(item.keyName).playKey)}}</span>

                          <span v-if="/^[0-9]\d*$/.test(item.oddsKey)">{{$t(item.oddsKey)}}</span>
                          <span v-if="!/^[0-9]\d*$/.test(item.oddsKey)">{{$t(item.oddsKey)}}</span>
                        </template>
                        <template v-if="gameId==601">
                        <span v-if="JSON.parse(item.keyName).playKey!='q2zx' && JSON.parse(item.keyName).playKey!='q3zx'
                                     && JSON.parse(item.keyName).playKey!='q2gx' && JSON.parse(item.keyName).playKey!='q3gx'&& JSON.parse(item.keyName).playKey.indexOf('rx')==-1">{{$t(JSON.parse(item.keyName).playKey)}}</span>

                        <span v-if="/^[0-9]\d*$/.test(item.oddsKey)">{{$t(item.oddsKey)}}</span>
                        <span v-if="!/^[0-9]\d*$/.test(item.oddsKey)">{{$t(item.oddsKey)}}</span>
                        <span v-if="item.betContent">{{item.betContent}}</span>
                  </template>
                        <span style="color:red">@{{item.odds}}</span>
                      </td>
                      <td>{{item.betAmt | moneyFmt}}</td>
                      <td>
                        <template v-if="parseFloat(item.winAmt) >= 0">
                          <span >{{item.winAmt | moneyFmt}}</span>
                        </template>
                        <template v-if="parseFloat(item.winAmt) < 0">
                          <span class="red_color">{{item.winAmt | moneyFmt}}</span>
                        </template>
                        <br/>{{item.water}}
                      </td>
                    </tr>
                  </template>
                </tbody>
              </table>
            </div>
            <table class="jqm_xd_table" cellpadding="0" cellspacing="0" border="0" style="bottom: 25px;position: fixed;">
              <tbody>
                <tr style="background-color: rgb(235, 215, 216);">
                  <td colspan="2" style="text-align: center;">总计</td>
                  <td>{{orderList.length}}笔</td>
                  <td>{{pageOrderMoney | moneyFmt}}</td>
                  <td>{{pageOrderWin | moneyFmt}}</td>
                </tr>
              </tbody>
            </table>
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
  import to from "await-to-js";
  export default {
    components: {
      MyHeader,
      notice,
    },
    data() {
      return {
        orderList:[],
        orderTotal:'',
        pageOrderMoney:0.00,
        pageOrderWin:0.00,
        params:{
          lotteryId:null,
          status:'DIVIDEND',
          accountDay:Utils.formatDate(new Date(),'yyyy-MM-dd'),
          size:8,
          page:1
        },
        currentDate:new Date(),
      }
    },
    computed: {
      ...mapGetters(['gameMenu','showMenu','gameId']),
      showDate(){
        if(this.currentDate){
          return Utils.formatDate(this.currentDate,'yyyy/MM/dd');
        }
      }
    },
    mounted(){
      Indicator.open({text:'加载中...'});
      this.params.lotteryId = this.gameId;
      let date = new Date();
      let hh = date.getHours();
      if (hh < 7) {
        date.setDate(date.getDate() - 1);
        let dateParam = Utils.formatDate(date, 'yyyy-MM-dd');
        let spliceArr = dateParam.split('-');
        this.currentDate = new Date(spliceArr[0]+'-'+spliceArr[1]+'-'+spliceArr[2]);
        this.params.accountDay = Utils.formatDate(new Date(spliceArr[0]+'-'+spliceArr[1]+'-'+spliceArr[2]),'yyyy/MM/dd');
      }
      this.getBetList();
    },
    methods:{
      async getBetList(){
        let self = this;
        self.pageOrderMoney=0.00;
        self.pageOrderWin=0.00;
        let [err,data] = await to(Bet.betList(self.params));
        self.orderList = data.data.dataList;

        for(let i = 0;i<self.orderList.length;i++){
          self.pageOrderMoney=Utils.NumberAdd(self.pageOrderMoney,self.orderList[i].betAmt);
          self.pageOrderWin =Utils.NumberAdd(self.pageOrderWin,self.orderList[i].winAmt);
          self.$set(self.orderList[i],'water',Utils.NumberDiv(Utils.NumberMul(self.orderList[i].betAmt,self.orderList[i].userRegress),100.00,3));
        }
        self.orderTotal = self.orderList.length;
        Indicator.close();

      },
      openPicker() {
        this.$refs.picker.open();
      },
      handleChange(value) {
        this.params.accountDay = Utils.formatDate(value,'yyyy-MM-dd');
        this.getBetList();
      }
    },
    filters: {
      formatDate(time) {
        var date = new Date(time);
        return formatDate(date, 'yyyy-MM-dd'); // 年月日 格式自己定义   'yyyy : MM : dd'  例 2018年12月5日的格式
      },
      formatDateTwo(time){
        var date = new Date(time);
        return formatDate(date, 'hh:mm:ss');
      },
      moneyFmt(val){
        if(!val){
          return '0.00';
        }
        return Utils.formatMoney(val, 2);
      },
      idFmt(val){
        let valSubString = val.toString();
        return valSubString.substring(8);
      },
      idFmt2(val){
        let valSubString = val.toString();
        return valSubString.substring(0,8);
      }
    }
  }
</script>

<style scoped>
  table {
    table-layout: fixed;
    border-collapse: collapse;
  }
  .otherpage {
    background: #fff !important;
    height: calc(100% - 4px) !important;
  }
  .detailist{
    height: calc(100% - 178px) !important;
    position: fixed;
    overflow: auto;
  }
  .jqm_content {
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

  .list {
    color: #666 !important;
    margin: 1px 0;
    font-size: 14px;
    position: relative;
    z-index: 1;
  }
  .list ul {
    list-style: none;
    margin: 0;
    padding: 0;
    position: relative;
    background: #fff;
  }
  .list .item-content {
    min-height: 44px;
    padding-left: 20px;
    display: -webkit-box;
    display: -webkit-flex;
    display: -ms-flexbox;
    display: flex;
    -webkit-box-pack: justify;
    -webkit-justify-content: space-between;
    -ms-flex-pack: justify;
    justify-content: space-between;
    -webkit-box-sizing: border-box;
    box-sizing: border-box;
    -webkit-box-align: center;
    -webkit-align-items: center;
    -ms-flex-align: center;
    align-items: center;
  }
  .list .item-inner {
    position: relative;
    width: 100%;
    padding-top: 8px;
    padding-bottom: 8px;
    min-width: 0;
    display: -webkit-box;
    display: -webkit-flex;
    display: -ms-flexbox;
    display: flex;
    -webkit-box-pack: justify;
    -webkit-justify-content: space-between;
    -ms-flex-pack: justify;
    justify-content: space-between;
    -webkit-box-sizing: border-box;
    box-sizing: border-box;
    -webkit-box-align: center;
    -webkit-align-items: center;
    -ms-flex-align: center;
    align-items: center;
    -webkit-align-self: stretch;
    -ms-flex-item-align: stretch;
    align-self: stretch;
    min-height: 44px;
  }
  .item-label {
    overflow: visible;
    font-size: 14px;
    width: 35%;
    line-height: 1.4;
    -webkit-align-self: flex-start;
    -ms-flex-item-align: start;
    align-self: flex-start;
    padding-top: 3px;
  }
  .list .item-title {
    min-width: 0;
    -webkit-flex-shrink: 1;
    -ms-flex-negative: 1;
    flex-shrink: 1;
    white-space: nowrap;
    position: relative;
    overflow: hidden;
    text-overflow: ellipsis;
    max-width: 100%;
  }
  .list .item-input-wrap {
    padding-right: 15px;
    margin-top: -8px;
    margin-left: 5px;
    margin-bottom: -8px;
    width: 100%;
    -webkit-flex-shrink: 1;
    -ms-flex-negative: 1;
    flex-shrink: 1;
    position: relative;
  }
  .list input{
    -webkit-box-sizing: border-box;
    box-sizing: border-box;
    -webkit-appearance: none;
    -moz-appearance: none;
    appearance: none;
    border: none;
    -webkit-box-shadow: none;
    box-shadow: none;
    border-radius: 0;
    outline: 0;
    display: block;
    padding: 0;
    margin: 0;
    resize: none;
    font-size: inherit;
    font-family: inherit;
    text-align: right;
    width: 100%;
    height: 44px;
    color: #000;
    font-size: 14px !important;
    line-height: 44px;
  }

</style>

