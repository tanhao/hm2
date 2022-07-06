<template>
  <div id="idc">
    <div class="home">
      <div class="sc-bZQynM OQRyf">
        <div class="sc-bdVaJa jaFIbq otherpage">
          <my-header top="false" resbnt="true" @refreshPageFun="refreshFun" title="结算报表"></my-header>
          <div class="block">
            <div class="segmented segmented-round">
              <button class="button button-round button-outline" :class="weekActive?'button-active':''"
                      @click="weekSelectFun(true)">两周报表
              </button>
              <button class="button button-round button-outline" :class="!weekActive?'button-active':''"
                      @click="weekSelectFun(false)">作废注单
              </button>
            </div>
          </div>
          <div v-show="weekActive" class="sc-htoDjs biwSnT">
            <div>
              <div class="tb_tit">
                <select id="gm_type" v-model="lotterySelect" @change="nowPrevActive">

                  <option value="all">全部</option>
                  <option v-for="(list,index) in gameMenu" :value="list.index">{{$t(list.title)}}</option>
                </select>
              </div>
              <div class="tb_tit">
                <select @change="nowPrevActive" v-model="winOrLoserState" id="ddlstatus">
                  <option value="DIVIDEND">已结算</option>
                  <option value="WAITING">未结算</option>
                  <option value="LOSE">已结算-不中</option>
                  <option value="WIN">已结算-中奖</option>
                </select>
              </div>
            </div>
          </div>
          <div class="ui-content jqm_content">
            <!--这里是两个筛选下拉框-->
            <table width="100%" :class="weekActive?'':'line-through'" align="left" cellspacing="0" cellpadding="0">
              <tbody>
                <tr>
                  <td id="tdlist" valign="top">
                    <table width="100%" class="jqm_xd_table" cellpadding="0" cellspacing="0" border="0" id="table2">
                      <tbody>
                        <tr align="center" class="td_caption_1">
                          <th>交易日期</th>
                          <th>注单笔数</th>
                          <th>下注金额</th>
                          <th>输赢结果</th>
                          <th>退水</th>
                          <th>退水后结果</th>
                        </tr>
                        <template v-for="(list,index) in nowList">
                          <tr>
                            <td style="b" @click="selectSameDay(list.date)">{{list.date.substring(5)}}
                              <br/>
                              {{$t(list.week)}}
                            </td>
                            <td @click="selectSameDay(list.date)">{{list.num}}</td>
                            <td @click="selectSameDay(list.date)">{{list.actAmt}}</td>
                            <td @click="selectSameDay(list.date)">{{list.winAmt | moneyFmt}}</td>
                            <td @click="selectSameDay(list.date)">{{list.comm | moneyFmt}}</td>
                            <td @click="selectDayAmtInfo(list.date)">
                              <!--<span   :class="list.winAmt<0?'red_color':''">{{list.winAmt}}</span>-->
                              <template v-if="parseInt(winMoneyFmt(list.winAmt,list.comm)) >= 0">
                                <span class="blue_color">{{winMoneyFmt(list.winAmt,list.comm)}}</span>

                              </template>
                              <template v-else>
                                <span class="red_color">{{winMoneyFmt(list.winAmt,list.comm)}}</span>

                              </template>
                            </td>
                          </tr>
                        </template>
                        <tr class="t_list_bottom">
                          <th align="center">本周</th>
                          <th>{{parseInt(nowTotalNum)}}</th>
                          <th>{{parseInt(nowTotalBetAmt)}}</th>
                          <th>{{parseInt(nowTotalActAmt)}}</th>
                          <th>{{nowTotalComm | moneyFmt}}</th>
                          <th>
                            <template v-if="parseInt(winMoneyFmt(nowTotalWinAmt,nowTotalComm)) >= 0">
                              <span class="blue_color">{{winMoneyFmt(nowTotalWinAmt,nowTotalComm)}}</span>

                            </template>
                            <template v-else>
                              <span class="red_color">{{winMoneyFmt(nowTotalWinAmt,nowTotalComm)}}</span>

                            </template>
                          </th>
                        </tr>
                      </tbody>
                    </table>
                    <table width="100%" class="jqm_xd_table" cellpadding="0" cellspacing="0" border="0" id="table1"
                           style="margin-top:5px;margin-bottom: 65px;">
                      <tbody>
                        <tr align="center" class="td_caption_1">
                          <th>交易日期</th>
                          <th>注单笔数</th>
                          <th>下注金额</th>
                          <th>输赢结果</th>
                          <th>退水</th>
                          <th>退水后结果</th>
                        </tr>
                        <template v-for="(list,index) in prevList">
                          <tr>
                            <td @click="selectSameDay(list.date)">{{list.date.substring(5)}}
                              <br/>
                              {{$t(list.week)}}
                            </td>
                            <td @click="selectSameDay(list.date)">{{list.num}}</td>
                            <td @click="selectSameDay(list.date)">{{list.actAmt}}</td>
                            <td @click="selectSameDay(list.date)">{{list.winAmt | moneyFmt}}</td>
                            <td @click="selectSameDay(list.date)">{{list.comm | moneyFmt}}</td>
                            <td @click="selectDayAmtInfo(list.date)">
                              <template v-if="parseInt(winMoneyFmt(list.winAmt,list.comm)) >= 0">
                                <span class="blue_color">{{winMoneyFmt(list.winAmt,list.comm)}}</span>

                              </template>
                              <template v-else>
                                <span class="red_color">{{winMoneyFmt(list.winAmt,list.comm)}}</span>

                              </template>
                            </td>
                          </tr>
                        </template>
                        <tr class="t_list_bottom">
                          <th align="center">上周</th>
                          <th>{{parseInt(prevTotalNum)}}</th>
                          <th>{{parseInt(prevTotalBetAmt)}}</th>
                          <th>{{parseInt(prevTotalActAmt)}}</th>
                          <th>{{prevTotalComm | moneyFmt}}</th>
                          <th>
                            <template v-if="parseInt(winMoneyFmt(prevTotalWinAmt,prevTotalComm)) >= 0">
                              <span class="blue_color">{{winMoneyFmt(prevTotalWinAmt,prevTotalComm)}}</span>

                            </template>
                            <template v-else>
                              <span class="red_color">{{winMoneyFmt(prevTotalWinAmt,prevTotalComm)}}</span>

                            </template>
                          </th>
                        </tr>
                      </tbody>
                    </table>
                  </td>
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
  import {formatDate} from '@/components/comm/date.js'
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
        nowList: [],
        prevList: [],
        nowOrPrevActive: true,
        nowTotalNum: 0,
        nowTotalBetAmt: 0,
        nowTotalActAmt: 0,
        nowTotalComm: 0,
        nowTotalWinAmt: 0,
        prevTotalNum: 0,
        prevTotalBetAmt: 0,
        prevTotalActAmt: 0,
        prevTotalComm: 0,
        prevTotalWinAmt: 0,
        lotterySelect: 'all',
        winOrLoserState: 'DIVIDEND',
        weekActive: true,
      }
    },
    computed: {
      ...mapGetters(['gameMenu', 'showMenu', 'gameId']),
    },
    filters: {
      moneyFmt(val) {
        if (!val || 0 == val) {
          return '0.00';
        }
        return Utils.formatMoney(val, 2);
      }
    },
    mounted() {
      let self = this;
      Indicator.open({text: '加载中...'});
      let singleOrAll = self.$route.query.singleOrAll;
      if (singleOrAll) {
        self.lotterySelect = self.$route.query.lotteryId;
      }
      if (self.$route.query.winOrLoserState) {
        self.winOrLoserState = self.$route.query.winOrLoserState;
        ;
      }
      this.winOrLoserState = 'DIVIDEND';
      self.nowPrevActive();
      this.$nextTick(() => {
        Indicator.close();

      });
    },
    methods: {
      ...mapActions(['setPromptInformation', 'setPagePosition']),
      refreshFun(){
        if (!this.weekActive) {
          this.weekReportGet({'winOrLoserState': 'VOID'});
        } else {
          this.nowPrevActive();
        }
      },
      weekSelectFun(flag) {
        if (this.weekActive == flag) {
          return
        }
        this.weekActive = flag;
        if (!this.weekActive) {
          this.weekReportGet({'winOrLoserState': 'VOID'});
        } else {
          this.nowPrevActive();
        }
      },
      winMoneyFmt(win, comm) {
        let total = Utils.NumberAdd(win, comm);
        return Utils.formatMoney(total, 2);
      },
      nowPrevActive() {
        let self = this;
        let param = {lotteryId: null, winOrLoserState: this.winOrLoserState};
        if (self.lotterySelect != 'all') {
          param.lotteryId = parseInt(self.lotterySelect);
        }
        this.weekReportGet(param);
      },
      async weekReportGet(params) {
        let self = this;
        self.nowTotalNum = 0;
        self.nowTotalBetAmt = 0;
        self.nowTotalActAmt = 0;
        self.nowTotalComm = 0;
        self.nowTotalWinAmt = 0;


        self.prevTotalNum = 0;
        self.prevTotalBetAmt = 0;
        self.prevTotalActAmt = 0;
        self.prevTotalComm = 0;
        self.prevTotalWinAmt = 0;
        let [err, data] = await to(Lottery.getReport(params));
        if (data.success) {
          self.nowList = data.data.now;
          self.prevList = data.data.prev;
          for (let i = 0; i < self.nowList.length; i++) {
            self.nowTotalNum = Utils.NumberAdd(self.nowList[i].num, self.nowTotalNum);
            self.nowTotalBetAmt = Utils.NumberAdd(self.nowList[i].betAmt, self.nowTotalBetAmt);
            self.nowTotalActAmt = Utils.NumberAdd(self.nowList[i].actAmt, self.nowTotalActAmt);
            self.nowTotalComm = Utils.NumberAdd(self.nowList[i].comm, self.nowTotalComm);
            self.nowTotalWinAmt = Utils.NumberAdd(self.nowList[i].winAmt, self.nowTotalWinAmt);
          }

          for (let i = 0; i < self.prevList.length; i++) {
            self.prevTotalNum = Utils.NumberAdd(self.prevList[i].num, self.prevTotalNum);
            self.prevTotalBetAmt = Utils.NumberAdd(self.prevList[i].betAmt, self.prevTotalBetAmt);
            self.prevTotalActAmt = Utils.NumberAdd(self.prevList[i].actAmt, self.prevTotalActAmt);
            self.prevTotalComm = Utils.NumberAdd(self.prevList[i].comm, self.prevTotalComm);
            self.prevTotalWinAmt = Utils.NumberAdd(self.prevList[i].winAmt, self.prevTotalWinAmt);
          }
        }
      },
      selectSameDay(date) {
        if (this.lotterySelect == 'all') {
          if (this.weekActive) {
            this.$router.push({
              path: '/idc/samedayprofitlos/',
              query: {selectDate: date, 'winOrLoserState': this.winOrLoserState}
            });
          } else {
            this.$router.push({path: '/idc/samedayprofitlos/', query: {selectDate: date, 'winOrLoserState': 'VOID'}});
          }
        } else {
          if (this.weekActive) {
            this.$router.push({
              name: 'lotteryprofitlos',
              query: {
                'lotteryId': this.lotterySelect,
                'selectDate': date,
                'singleOrAll': true,
                'winOrLoserState': this.winOrLoserState
              }
            });
          } else {
            this.$router.push({
              name: 'lotteryprofitlos',
              query: {
                'lotteryId': this.lotterySelect,
                'selectDate': date,
                'singleOrAll': true,
                'winOrLoserState': 'VOID'
              }
            });

          }
        }

      },
      selectDayAmtInfo(date) {
        if (this.weekActive) {
          this.$router.push({
            name: 'todayprofitlos',
            query: {
              'lotteryId': this.lotterySelect,
              'selectDate': date,
              'singleOrAll': true,
              'winOrLoserState': this.winOrLoserState
            }
          });
        } else {
          this.$router.push({
            name: 'todayprofitlos',
            query: {
              'lotteryId': this.lotterySelect,
              'selectDate': date,
              'singleOrAll': true,
              'winOrLoserState': 'VOID',
              'status': 'VOID'
            }
          });
        }

      }
    },
  }
</script>

<style scoped>
  table {
    table-layout: fixed;
    width: 100%;
    border-collapse: collapse;
  }

  .block {
    padding: 8px 10px;
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
    background: linear-gradient(to left, #cd3c29 0%, #510505 100%);
    color: #eaeaea;
  }

  .button {
    background: #efeff4;
    font-size: 14px;
    border: 1px solid #cd3c29;
    color: #000;
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

  .otherpage {
    background: #fff !important;
    height: -webkit-calc(100% - 4px) !important;
    height: calc(100% - 4px) !important;
  }

  .biwSnT {
    background-color: #ebebeb;
    display: -webkit-box;
    display: -webkit-flex;
    display: -ms-flexbox;
    display: flex;
    -webkit-align-items: center;
    -webkit-box-align: center;
    -ms-flex-align: center;
    align-items: center;
    -webkit-box-pack: justify;
    -webkit-justify-content: space-between;
    -ms-flex-pack: justify;
    justify-content: space-between;
    padding: 5px;
    border-right: 1px solid #eaeaea;
  }

  .biwSnT div {
    color: #666;
    width: 100%;
    font-weight: 400;
    font-size: 0.8125rem;
    border: 1px solid rgba(255, 0, 0, 0);
    background-color: rgba(255, 255, 255, 0);
    line-height: 2.5rem;
  }

  .tb_tit select {
    padding: 0 0 0 10px;
    background-color: #fff;
    height: 40px;
    width: 100%;
    color: #131313;
    border-radius: 4px;
    text-align: center;
    font-size: 14px;
    font-weight: bold;
    margin-right: 5px;
  }

  option {
    padding: 0 0 0 10px;
  }

  .jqm_content {
    margin-bottom: 20px;
    overflow: auto;
    height: calc(100% - 122px) !important;
    padding: 0px;
  }

  .ui-content {
    border-width: 0;
    overflow: scroll;
    -webkit-overflow-scrolling: touch !important;
  }

  .jqm_xd_table {
    width: 100%;
  }

  .jqm_xd_table tr td {
    word-wrap: break-word;
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

  .t_list_bottom {
    font-size: 14px;
    background-color: #F7D3B9;
  }

</style>

