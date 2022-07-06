<template>
  <div id="idc">
    <div class="home">
      <div class="sc-bZQynM" style="height: 100%;">
        <div class="sc-bdVaJa jaFIbq">
          <my-header top="true" title="下注明细" @refreshPageFun="getBetList" resbnt="true"></my-header>
          <div class="ui-content jqm_content">
            <div class="list">
              <ul>
                <li style="width:50%;" class="item-content item-input item-input-with-value">
                  <div class="item-inner">
                    <div class="item-title item-label">日期</div>
                    <div class="item-input-wrap">
                      <input @click="openPicker()" :value="showDate" readonly>
                      <mt-datetime-picker
                        ref="picker"
                        type="date"
                        v-model="currentDate"
                        @confirm="handleChange">
                      </mt-datetime-picker>
                    </div>
                  </div>
                </li>
                <li>
                  <select v-model="params.status" @change="getBetList">
                    <option value="WAITING">未结</option>
                    <option value="CANCEL">撤单</option>
                  </select>
                </li>
              </ul>
            </div>
            <table class="jqm_xd_table" cellpadding="0" cellspacing="0" border="0">
              <tbody>
                <tr>
                  <th>注单号/时间</th>
                  <th>下注类型</th>
                  <th>明细({{markets}})盘</th>
                  <th>下注金额</th>
                  <th>可赢金额</th>
                </tr>
              </tbody>
            </table>
            <div class="detailist">
              <table class="jqm_xd_table" cellpadding="0" cellspacing="0" border="0">
                <tbody>
                  <template v-for="(item, index) in orderList">
                    <tr style="background-color: rgb(235, 215, 216);">
                      <td>{{item.orderId}}<br/>{{item.betTime *
                        1000 | formatDateTwo}}
                      </td>
                      <td>
                        {{$t(item.lotteryId)}}
                        <br/>{{item.gameNo}}
                      </td>
                      <td>
                        <template v-if="item.lotteryId==101 || item.lotteryId==102 || item.lotteryId==103 || item.lotteryId==104 ||
                          item.lotteryId==105 || item.lotteryId==106 || item.lotteryId==107 || item.lotteryId==108 || item.lotteryId==109 || item.lotteryId==110 || item.lotteryId==111 || item.lotteryId==112">
                          <span>
                            <!--<span v-if="JSON.parse(item.keyName).categoryKey=='lm'">{{$t(JSON.parse(item.keyName).categoryKey)}}</span>-->
                            {{$t(JSON.parse(item.keyName).playKey)}}
                            <span style="color:red" v-if="/^[0-9]\d*$/.test(item.oddsKey)">{{item.oddsKey}}</span>
                            <span style="color:red" v-if="!/^[0-9]\d*$/.test(item.oddsKey)">
                              {{$t(item.oddsKey.toUpperCase())}}
                            </span>
                          </span>
                        </template>
                        <template v-if="item.lotteryId==201 || item.lotteryId==202 || item.lotteryId==203 || item.lotteryId==204 ||
                            item.lotteryId==205 || item.lotteryId==206 || item.lotteryId==207 || item.lotteryId == 208 || item.lotteryId==209 || item.lotteryId==210 || item.lotteryId==211">
                          <span v-if="item.betContent">{{$t(JSON.parse(item.keyName).playKey)}}
                            <span style="color:red" v-if="JSON.parse(item.keyName).playKey.indexOf('zx')==-1">
                              <span v-if="/^[0-9]\d*$/.test(item.oddsKey)">{{item.oddsKey}}</span>
                              <span v-if="!/^[0-9]\d*$/.test(item.oddsKey)">{{$t(item.oddsKey.toUpperCase())}}</span>
                            </span>
                          </span>
                          <span v-if="!item.betContent">
                            <span
                              v-if="JSON.parse(item.keyName).playKey=='t_lhh' && JSON.parse(item.keyName).categoryKey=='lm'">
                              {{$t('thesum')}}
                            </span>
                            <span
                              v-else-if="JSON.parse(item.keyName).playKey=='t_lhh' && JSON.parse(item.keyName).categoryKey=='lhh'">
                              {{$t('dtt')}}
                            </span>
                            <span v-else>{{$t(JSON.parse(item.keyName).playKey)}}</span>
                            <span v-if="JSON.parse(item.keyName).playKey.indexOf('qiu')==-1 && JSON.parse(item.keyName).playKey!='t_lhh' && JSON.parse(item.keyName).playKey.indexOf('2z')==-1
                            && JSON.parse(item.keyName).playKey.indexOf('3z')==-1 && JSON.parse(item.keyName).playKey.indexOf('hs')==-1&& JSON.parse(item.keyName).playKey.indexOf('hws')==-1
                            && JSON.parse(item.keyName).playKey.indexOf('kd')==-1 && JSON.parse(item.keyName).playKey!='nn' && JSON.parse(item.keyName).playKey!='sh' && JSON.parse(item.keyName).playKey!='top3'
                            && JSON.parse(item.keyName).playKey!='mid3' && JSON.parse(item.keyName).playKey!='end3'">
                              {{$t(JSON.parse(item.keyName).categoryKey)}}
                            </span>
                            <span style="color:red" v-if="/^[0-9]\d*$/.test(item.oddsKey)">{{item.oddsKey}}</span>
                            <span style="color:red" v-if="!/^[0-9]\d*$/.test(item.oddsKey)">
                              {{$t(item.oddsKey.toUpperCase())}}
                            </span>

                          </span>
                          <span style="color:red" v-if="item.betContent">{{item.betContent}}</span>
                        </template>
                        <template
                          v-if="item.lotteryId==301 || item.lotteryId==302 || item.lotteryId==303 || item.lotteryId==304">
                          <span v-if="item.betContent">
                            {{$t(JSON.parse(item.keyName).playKey)}}{{$t(item.oddsKey.toUpperCase())}}
                          </span>
                          <span v-if="!item.betContent">
                            <span v-if="JSON.parse(item.keyName).playKey=='sum'">{{$t('THESUM')}}</span>
                            <span v-else>{{$t(JSON.parse(item.keyName).playKey)}}</span>


                            <span style="color:red" v-if="/^[0-9]\d*$/.test(item.oddsKey)">{{item.oddsKey}}</span>
                            <span style="color:red" v-if="!/^[0-9]\d*$/.test(item.oddsKey)">
                              {{$t(item.oddsKey.toUpperCase())}}
                            </span>
                          </span>
                          <span style="color:red" v-if="item.betContent">{{item.betContent}}</span>
                        </template>
<!--                        <template v-if="item.lotteryId==401 || item.lotteryId==402 || item.lotteryId==403
                          || item.lotteryId==404 || item.lotteryId==405">
                          {{$t(JSON.parse(item.keyName).playKey)}}
                          <span style="color:red" v-if="/^[0-9]\d*$/.test(item.oddsKey)">{{item.oddsKey}}</span>
                          <span style="color:red" v-if="!/^[0-9]\d*$/.test(item.oddsKey)">
                            {{$t(item.oddsKey.toUpperCase())}}
                          </span>
                        </template>-->
                        <template v-if="item.lotteryId==401 || item.lotteryId==402 || item.lotteryId==403">
                          <span style="color:red" v-if="item.betContent">{{$t(JSON.parse(item.keyName).playKey)}}</span>
                          <span v-if="!item.betContent">
                            {{$t(JSON.parse(item.keyName).playKey)}}
                            <span style="color:red" v-if="/^[0-9]\d*$/.test(item.oddsKey)">{{item.oddsKey}}</span>
                            <span style="color:red" v-if="!/^[0-9]\d*$/.test(item.oddsKey)">
                              {{$t(item.oddsKey.toUpperCase())}}
                            </span>
                          </span>
                          <span style="color:red" v-if="item.betContent">{{item.betContent}}</span>
                        </template>
                        <template v-if="item.lotteryId==701">
                          <span v-if="JSON.parse(item.keyName).playKey=='t_lhh'">{{$t('zhlh')}}</span>
                          <span v-if="JSON.parse(item.keyName).playKey!='t_lhh'">
                            {{$t(JSON.parse(item.keyName).playKey)}}
                          </span>
                          <span v-if="JSON.parse(item.keyName).categoryKey==''"></span>
                          <span style="color:red" v-if="/^[0-9]\d*$/.test(item.oddsKey)">{{item.oddsKey}}</span>
                          <span style="color:red" v-if="!/^[0-9]\d*$/.test(item.oddsKey)">{{$t(item.oddsKey)}}</span>
                        </template>
                        <template v-if="item.lotteryId==501 || item.lotteryId==502">

                          <span>{{$t(JSON.parse(item.keyName).playKey)}}</span>

                          <span style="color:red" v-if="/^[0-9]\d*$/.test(item.oddsKey)">{{item.oddsKey}}</span>
                          <span style="color:red" v-if="!/^[0-9]\d*$/.test(item.oddsKey)">{{$t(item.oddsKey)}}</span>
                        </template>
                        <template v-if="item.lotteryId==601">
                          <span v-if="JSON.parse(item.keyName).playKey!='q2zx' && JSON.parse(item.keyName).playKey!='q3zx'
                                       && JSON.parse(item.keyName).playKey!='q2gx' && JSON.parse(item.keyName).playKey!='q3gx'&& JSON.parse(item.keyName).playKey.indexOf('rx')==-1">
                            {{$t(JSON.parse(item.keyName).playKey)}}
                          </span>

                          <span style="color:red" v-if="/^[0-9]\d*$/.test(item.oddsKey)">{{item.oddsKey}}</span>
                          <span style="color:red" v-if="!/^[0-9]\d*$/.test(item.oddsKey)">{{$t(item.oddsKey)}}</span>
                          <span style="color:red" v-if="item.betContent">{{item.betContent}}</span>
                        </template>
                        <span style="color:red"><br/>@{{item.odds}}
                        </span>
                      </td>
                      <td>{{item.betAmt }}</td>
                      <td>{{item.winAmt | moneyFmt}}</td>
                    </tr>
                  </template>
                </tbody>
              </table>
            </div>
            <table class="jqm_xd_table" cellpadding="0" cellspacing="0" border="0"
                   style="position: relative;bottom: 0px">
              <tbody>
                <tr style="background-color: rgb(235, 215, 216);">
                  <td colspan="2" style="text-align: center;">总计</td>
                  <td>{{orderList.length}}笔</td>
                  <td>{{parseInt(pageOrderMoney)}}</td>
                  <td>{{pageOrderWin | moneyFmt}}</td>
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
  import {Indicator} from 'mint-ui'
  import pager from '@/components/idc/layout/paging'
  import Utils from '@/components/comm/Utils'
  import to from "await-to-js";

  export default {
    components: {
      MyHeader,
      notice,
      pager,
    },
    data() {
      return {
        orderList: [],
        pageOrderMoney: 0.00,
        pageOrderWin: 0.00,
        params: {
          lotteryId: null,
          status: 'WAITING',
          accountDay: Utils.formatDate(new Date(), 'yyyy-MM-dd'),
          size: 10,
          page: 1,
        },
        total: 1,
        totalPage: 1,
        currentDate: new Date(),
      }
    },
    computed: {
      ...mapGetters(['gameMenu', 'showMenu', 'gameId', 'markets']),
      showDate() {
        if (this.currentDate) {
          return Utils.formatDate(this.currentDate, 'yyyy-MM-dd');
        }
      },
    },
    mounted() {

      this.params.lotteryId = this.$route.query.lotteryId;
      let date = new Date();
      let hh = date.getHours();
      if (hh < 7) {
        date.setDate(date.getDate() - 1);
        let dateParam = Utils.formatDate(date, 'yyyy-MM-dd');
        let spliceArr = dateParam.split('-');
        this.currentDate = new Date(spliceArr[0] + '-' + spliceArr[1] + '-' + spliceArr[2]);
        this.params.accountDay = Utils.formatDate(new Date(spliceArr[0] + '-' + spliceArr[1] + '-' + spliceArr[2]), 'yyyy-MM-dd');
      }
      this.getBetList()
    },
    methods: {
      openPicker() {
        this.$refs.picker.open();
      },
      handleChange(value) {
        this.params.accountDay = Utils.formatDate(value, 'yyyy-MM-dd');
        this.getBetList();
      },
      gotoPage(curPage) {

        let self = this;
        self.params.page = curPage;
        this.getBetList();
      },
      async getBetList() {
        Indicator.open({text: '加载中...'});
        let self = this;
        self.pageOrderMoney = 0.00;
        self.pageOrderWin = 0.00;
        let [err, data] = await to(this.$api.bet.betList(self.params));
        if (err || !data.success) {
          return;
        }
        if (data.success) {
          self.orderList = data.data.dataList;

          for (let i = 0; i < self.orderList.length; i++) {
            self.orderList[i].winAmt = Utils.NumberMul(self.orderList[i].betAmt, self.orderList[i].odds - 1);
            self.pageOrderMoney = Utils.NumberAdd(self.pageOrderMoney, self.orderList[i].betAmt);
            self.pageOrderWin = Utils.NumberAdd(self.pageOrderWin, self.orderList[i].winAmt);

          }
          self.total = data.data.total;
          self.totalPage = data.data.total % self.params.size == 0 ? data.data.total / self.params.size : parseInt(data.data.total / self.params.size) + 1;
          if (data.data.total == 0) {
            self.params.page = 1;
          }
        }
        Indicator.close();
      }
    },
    filters: {
      formatDate(time) {
        var date = new Date(time);
        return formatDate(date, 'yyyy-MM-dd'); // 年月日 格式自己定义   'yyyy : MM : dd'  例 2018年12月5日的格式
      },
      formatDateTwo(time) {
        var date = new Date(time);
        return formatDate(date, 'hh:mm:ss');
      },
      moneyFmt(val) {
        if (!val) {
          return '0.00';
        }
        return Utils.formatMoney(val, 2);
      },
      idFmt(val) {
        let valSubString = val.toString();
        return valSubString.substring(8);
      },
      idFmt2(val) {
        let valSubString = val.toString();
        return valSubString.substring(0, 8);
      }
    }
  }
</script>

<style scoped>
  table {
    table-layout: fixed;
    border-collapse: collapse;
  }

  .list .item-content {
    height: 44px;
    padding-left: 22px;
    display: -webkit-box;
    display: -ms-flexbox;
    display: flex;
    -webkit-box-pack: justify;
    -ms-flex-pack: justify;
    justify-content: space-between;
    -webkit-box-sizing: border-box;
    box-sizing: border-box;
    -webkit-box-align: center;
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
    display: -ms-flexbox;
    display: flex;
    -webkit-box-pack: justify;
    -ms-flex-pack: justify;
    justify-content: space-between;
    -webkit-box-sizing: border-box;
    box-sizing: border-box;
    -webkit-box-align: center;
    -ms-flex-align: center;
    align-items: center;
    -webkit-align-self: stretch;
    -ms-flex-item-align: stretch;
    align-self: stretch;
    min-height: 44px;
  }

  .list .item-title {
    min-width: 0;
    -ms-flex-negative: 1;
    flex-shrink: 1;
    white-space: nowrap;
    position: relative;
    overflow: hidden;
    text-overflow: ellipsis;
    max-width: 100%;
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

  .list .item-input-wrap[data-v-fc81e2a8] {
    padding-right: 15px;
    margin-top: -8px;
    margin-left: 5px;
    margin-bottom: -8px;
    width: 100%;
    -ms-flex-negative: 1;
    flex-shrink: 1;
    position: relative;
  }

  .list ul {
    display: flex;
    list-style: none;
    margin: 0;
    padding: 0;
    position: relative;
    background: #fff;
  }

  .list ul li {
    width: 50%;
  }

  .list ul li select {
    float: right;
    margin: 6px 10px;
    display: table;
    padding: 0 0 0 10px;
    background-color: #fff;
    height: 30px;
    width: 80%;
    color: #131313;
    border-radius: 4px;
    text-align: center;
    font-size: 14px;
  }

  .detailist {
    background-color: #fff;
    -webkit-overflow-scrolling: touch !important;
    height: calc(100% - 81px) !important;
    overflow: scroll;
  }

  .jqm_content {
    height: calc(100% - 114px) !important;
    position: relative;
    display: table;
    padding: 0px;
  }

  .list input {
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
    background: linear-gradient(360deg, rgb(239, 192, 167) 0%, rgb(253, 248, 245) 100%);
    font-size: 12px !important;
    color: #4A1A04;
    font-weight: bold;
    height: 30px;
    line-height: 30px;
  }

</style>

