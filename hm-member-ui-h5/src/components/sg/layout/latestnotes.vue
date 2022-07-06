<template>
  <div class="home">
    <div class="sc-bZQynM OQRyf">
      <my-header  title="最新注单"  back="true" left="true"></my-header>
      <div class="sc-htoDjs UDzZc">
        <div class="table">
          <div class="table-header">
          <div class="col col2">注单号</div>
          <div class="col col3">类型</div>
          <div class="col col1-5">玩法</div>
          <div class="col col1-5">下注</div>
          <div class="col col1">可赢</div>
        </div>
        <div class="rough_lines"></div>
        <div class="table-content">
          <template v-for="(item, index) in orderList">
            <div class="table-row">
              <div class="col col2">
                <div class="green_color">{{item.orderId}}</div>
                <div>{{item.betTime*1000 | formatDateTwo}}</div>
              </div>
              <div class="col col3">
                <template v-for="(obj, i) in gameMenu">
                  <div v-if="parseInt(obj.index)===item.lotteryId">{{$t(obj.title)}}</div>
                </template>
                <div class="green_color">{{item.gameNo}}</div>
                <div class="blue_color">{{item.market}}</div>
              </div>
              <div class="col col1-5">
                <div class="blue_color" v-if="gameId==101 || gameId==102 || gameId==103 || gameId==104 ||
                  gameId==105 || gameId==106 || gameId==107 || gameId==108 || gameId==109 || gameId==110 || gameId == 111">
                  <span ><!--<span v-if="JSON.parse(item.keyName).categoryKey=='lm'">{{$t(JSON.parse(item.keyName).categoryKey)}}</span>-->{{$t(JSON.parse(item.keyName).playKey)}}
                    <span v-if="/^[0-9]\d*$/.test(item.oddsKey)">{{$t(item.oddsKey)}}</span>
                    <span v-if="!/^[0-9]\d*$/.test(item.oddsKey)">{{$t(item.oddsKey.toUpperCase())}}</span></span>
                </div>
                <div class="blue_color" v-if="gameId==201 || gameId==202 || gameId==203 || gameId==204 ||
                  gameId==205 || gameId==206 || gameId==207 || gameId==208 || gameId==209 || gameId==210
                  || gameId==211">
                  <span v-if="item.betContent">{{$t(JSON.parse(item.keyName).playKey)}}
                    <span v-if="JSON.parse(item.keyName).playKey.indexOf('zx')==-1">
                      <span v-if="/^[0-9]\d*$/.test(item.oddsKey)">{{$t(item.oddsKey)}}</span>
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
                    <span v-if="/^[0-9]\d*$/.test(item.oddsKey)">{{$t(item.oddsKey)}}</span>
                    <span v-if="!/^[0-9]\d*$/.test(item.oddsKey)">{{$t(item.oddsKey.toUpperCase())}}</span>

                  </span>
                  <span v-if="item.betContent">{{item.betContent}}</span>
                </div>
                <div class="blue_color" v-if="gameId==301 || gameId==302 || gameId==303 || gameId==304">
                  <span v-if="item.betContent">{{$t(JSON.parse(item.keyName).playKey)}}{{$t(item.oddsKey.toUpperCase())}}</span>
                  <span v-if="!item.betContent">
                    <span v-if="JSON.parse(item.keyName).playKey=='sum'">{{$t('THESUM')}}</span>
                    <span v-else>{{$t(JSON.parse(item.keyName).playKey)}}</span>


                    <span v-if="/^[0-9]\d*$/.test(item.oddsKey)">{{$t(item.oddsKey)}}</span>
                    <span v-if="!/^[0-9]\d*$/.test(item.oddsKey)">{{$t(item.oddsKey.toUpperCase())}}</span></span>
                  <span v-if="item.betContent">{{item.betContent}}</span>
                </div>
                <div class="blue_color" v-if="gameId==401 || gameId==402 || gameId==403
                  || gameId==404 || gameId==405">
                  {{$t(JSON.parse(item.keyName).playKey)}}
                  <span v-if="/^[0-9]\d*$/.test(item.oddsKey)">{{$t(item.oddsKey)}}</span>
                  <span v-if="!/^[0-9]\d*$/.test(item.oddsKey)">{{$t(item.oddsKey.toUpperCase())}}</span>
                </div>
                <div class="blue_color" v-if="gameId==801">
                  <span v-if="item.betContent">{{$t(JSON.parse(item.keyName).playKey)}}</span>
                  <span v-if="!item.betContent">
                    {{$t(JSON.parse(item.keyName).playKey)}}
                    <span v-if="/^[0-9]\d*$/.test(item.oddsKey)">{{$t(item.oddsKey)}}</span>
                    <span v-if="!/^[0-9]\d*$/.test(item.oddsKey)">{{$t(item.oddsKey.toUpperCase())}}</span></span>
                  <span v-if="item.betContent">{{item.betContent}}</span>
                </div>
                <div class="blue_color" v-if="gameId==701">
                  <span v-if="JSON.parse(item.keyName).playKey=='t_lhh'">{{$t('zhlh')}}</span>
                  <span v-if="JSON.parse(item.keyName).playKey!='t_lhh'">{{$t(JSON.parse(item.keyName).playKey)}}</span>
                  <span v-if="JSON.parse(item.keyName).categoryKey==''"></span>
                  <span v-if="/^[0-9]\d*$/.test(item.oddsKey)">{{$t(item.oddsKey)}}</span>
                  <span v-if="!/^[0-9]\d*$/.test(item.oddsKey)">{{$t(item.oddsKey)}}</span>
                </div>
                <div class="blue_color" v-if="gameId==501 || gameId==502">

                  <span >{{$t(JSON.parse(item.keyName).playKey)}}</span>

                  <span v-if="/^[0-9]\d*$/.test(item.oddsKey)">{{$t(item.oddsKey)}}</span>
                  <span v-if="!/^[0-9]\d*$/.test(item.oddsKey)">{{$t(item.oddsKey)}}</span>
                </div>
                <div class="blue_color" v-if="gameId==601">

                  <span v-if="JSON.parse(item.keyName).playKey!='q2zx' && JSON.parse(item.keyName).playKey!='q3zx'
                                   && JSON.parse(item.keyName).playKey!='q2gx' && JSON.parse(item.keyName).playKey!='q3gx'&& JSON.parse(item.keyName).playKey.indexOf('rx')==-1">{{$t(JSON.parse(item.keyName).playKey)}}</span>

                  <span v-if="/^[0-9]\d*$/.test(item.oddsKey)">{{$t(item.oddsKey)}}</span>
                  <span v-if="!/^[0-9]\d*$/.test(item.oddsKey)">{{$t(item.oddsKey)}}</span>
                  <span v-if="item.betContent">{{item.betContent}}</span>
                </div>
                <div class="red_color">
                  <span class="blue_color">@</span>
                  {{item.odds}}
                </div>
              </div>
              <div class="col col1-5">{{item.betAmt}}</div>
              <div class="col col1 blue_color">{{item.winAmt | moneyFmt}}</div>
            </div>
          </template>
        </div>
        <div class="rough_lines"></div>
        <div class="table-footer pagination">
            <div class="pagination sc-gzVnrw oPkft">
              <div class="page-navi">
                <span class="prev disabled"><a></a></span>
                <span class="current">
                  <form class="page-jump">
                    <input type="text"  value="" placeholder="1">
                  </form><span>页</span></span>
                <span class="next disabled"><a></a></span>
              </div>
            </div>
          <div>
          <div class="footer-inline"><div class="title">注数(总计)</div><span class="content">{{this.orderTotal}}</span></div>
          <div class="footer-inline"><div class="title">本页下注金额</div><span class="content">{{parseInt(this.pageOrderMoney)}}</span></div>
          <div class="footer-inline"><div class="title">本页结果</div><span class="content">{{this.pageOrderWin | moneyFmt}}</span></div>
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
  import { formatDate } from '@/components/comm/date.js'
  import {mapGetters,mapActions} from 'vuex'
  import Bet from '@/axios/api-bet.js'
  import Utils from '@/components/comm/Utils.js'
  import {Indicator} from 'mint-ui'


  export default {
    components: {
      MyHeader,
      MyFooter,
    },
    data() {
      return {
        orderList:[],
        orderTotal:'',
        pageOrderMoney:0.00,
        pageOrderWin:0.00,
        params:{
          lotteryId:null,
          status:'WAITING',
          accountDay:Utils.formatDate(new Date(),'yyyy-MM-dd'),
          num:0
        },
        currentDate:new Date(),
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
        if(val){
          let valSubString = val.toString();
          return valSubString.substring(8);
        }

      }
    },
    computed: {
      ...mapGetters(['gameMenu','showMenu','gameId','noteLists','historyReturn','game']),
      showDate(){
        if(this.currentDate){
          return Utils.formatDate(this.currentDate,'yyyy-MM-dd');
        }
      },
    },
    created(){
      Indicator.open({text:'加载中...'});
      this.params.lotteryId = this.gameId;
      this.getBetList()




    },
    methods:{
      ...mapActions(['setNoteLists','setHistoryReturn']),
      getBetList(){
        let self = this;
        self.params.num = self.noteLists.length;
        Bet.betList(self.params).then(val=>{
          if(val.code===10000){
            self.orderList = val.data.dataList;

            for(let i = 0;i<self.orderList.length;i++){
              self.orderList[i].winAmt=Utils.NumberMul(self.orderList[i].betAmt,self.orderList[i].odds-1);
              self.pageOrderMoney=Utils.NumberAdd(self.pageOrderMoney,self.orderList[i].betAmt);
              self.pageOrderWin =Utils.NumberAdd(self.pageOrderWin,self.orderList[i].winAmt);

            }
            //this.pageOrderWin=this.pageOrderWin.toFixed(2);
            self.orderTotal = self.orderList.length;
          }

        }).finally(()=>{
          for(let i = 0;i<this.gameMenu.length;i++){
            if(this.gameMenu[i].index==this.game.lotteryId){
              self.setHistoryReturn({'name':this.gameMenu[i].title,'query':{},'mode':1});
            }
          }
          this.clearBet();
          Indicator.close();
        });
      },
      openPicker() {
        this.$refs.picker.open();
      },
      handleChange(value) {
        this.params.accountDay = Utils.formatDate(value,'yyyy-MM-dd');
        this.getBetList();
      },
      clearBet() {
        let self = this;
        this.noteLists.forEach((item) => {
          self.$delete(item, 'choose');
          self.$delete(item, 'betAmt');
        });
        this.setNoteLists(null);

      }
    },
    mounted(){

    }
  }
</script>
<style scoped>
  .UDzZc {
    height: calc(100% - 46px);
    position: relative;
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
    height: 30px;
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
    font-size: 10px;
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
  .table .table-footer.pagination {
    height: 70px;
    display: block;
  }
  .table .table-footer {
    display: -webkit-box;
    display: -webkit-flex;
    display: -ms-flexbox;
    display: flex;
    height: 30px;
    -webkit-box-pack: justify;
    -webkit-justify-content: space-between;
    -ms-flex-pack: justify;
    justify-content: space-between;
    -webkit-align-items: center;
    -webkit-box-align: center;
    -ms-flex-align: center;
    align-items: center;
    background-color: #fff;
    border-top: 1px solid #eaeaea;
    position: relative;
  }
  .table .table-footer .pagination {
    position: absolute;
    right: 0;
    bottom: 0;
    height: 40px;
    width: 200px;
  }
  .table .table-footer .pagination {
    position: absolute;
    right: 0;
    bottom: 0;
    height: 40px;
    width: 200px;
  }
  .oPkft {
    display: -webkit-box;
    display: -webkit-flex;
    display: -ms-flexbox;
    display: flex;
    height: 3rem;
    line-height: 3rem;
    background-color: #fff;
  }
  .oPkft > div {
    -webkit-box-flex: 1;
    -webkit-flex-grow: 1;
    -ms-flex-positive: 1;
    flex-grow: 1;
    text-align: center;
    position: relative;
  }
  .oPkft .page-navi {
    display: -webkit-box;
    display: -webkit-flex;
    display: -ms-flexbox;
    display: flex;
  }
  .oPkft .page-info, .oPkft .page-navi {
    font-size: 14px;
  }
  .oPkft .page-navi span {
    -webkit-flex: 1 1 33%;
    -ms-flex: 1 1 33%;
    flex: 1 1 33%;
    -webkit-box-pack: center;
    -webkit-justify-content: center;
    -ms-flex-pack: center;
    justify-content: center;
    position: relative;
  }
  .oPkft .page-navi span.disabled a {
    pointer-events: none;
    background-color: #ccc;
    color: #424242;
  }
  .oPkft .page-navi span.prev a, .oPkft .page-navi span.next a {
    border-radius: 4px;
    width: 28px;
    height: 30px;
    margin: auto;
    color: #fff;
    font-size: 0;
    position: absolute;
    top: 0;
    right: 0;
    bottom: 0;
    left: 0;
    background-image: url(../../images/you.png);
    background-color: #2061b3;
    background-position: center;
    background-size: 50% 50%;
    background-repeat: no-repeat;
  }
  .oPkft .page-navi span.next a {
    -webkit-transform: scaleX(-1);
    -ms-transform: scaleX(-1);
    transform: scaleX(-1);
  }
  .oPkft .page-navi span.current {
    display: -webkit-box;
    display: -webkit-flex;
    display: -ms-flexbox;
    display: flex;
    line-height: 40px;
  }
  .oPkft .page-navi span.current > * {
    -webkit-box-flex: 1;
    -webkit-flex-grow: 1;
    -ms-flex-positive: 1;
    flex-grow: 1;
    -webkit-flex-shrink: 1;
    -ms-flex-negative: 1;
    flex-shrink: 1;
    margin: 0 2px;
  }
  .oPkft .page-navi span {
    -webkit-flex: 1 1 33%;
    -ms-flex: 1 1 33%;
    flex: 1 1 33%;
    -webkit-box-pack: center;
    -webkit-justify-content: center;
    -ms-flex-pack: center;
    justify-content: center;
    position: relative;
  }
  .oPkft .page-navi span {
    -webkit-flex: 1 1 33%;
    -ms-flex: 1 1 33%;
    flex: 1 1 33%;
    -webkit-box-pack: center;
    -webkit-justify-content: center;
    -ms-flex-pack: center;
    justify-content: center;
    position: relative;
  }
  .oPkft .page-navi span.current {
    display: -webkit-box;
    display: -webkit-flex;
    display: -ms-flexbox;
    display: flex;
    line-height: 40px;
  }
  .oPkft .page-jump input {
    border: 1px solid #ccc;
    border-radius: 4px;
    width: 100%;
    height: 23px;
    margin: 8px 0px;
    font-size: 14px;
    font-weight: bold;
    text-align: center;
    background: transparent;
    box-sizing: border-box;
  }
  .oPkft .page-navi span.disabled a {
    pointer-events: none;
    background-color: #ccc;
    color: #424242;
  }
  .oPkft .page-navi span.disabled a {
    pointer-events: none;
    background-color: #ccc;
    color: #424242;
  }

  .table .table-footer .footer-inline:first-child {
    margin-top: 4px;
  }
  .table .table-footer .footer-inline {
    height: 20px;
    line-height: 20px;
    padding: 0 20px;
    box-sizing: border-box;
    display: -webkit-box;
    display: -webkit-flex;
    display: -ms-flexbox;
    display: flex;
    width: 100%;
    font-size: 12px;
  }
  .table .table-footer .footer-inline .title {
    width: 100px;
    color: #aaa;
    display: block;
    -webkit-flex: 1 1 30%;
    -ms-flex: 1 1 30%;
    flex: 1 1 30%;
  }
  .table .table-footer .footer-inline .content {
    width: calc(100% - 100px);
    display: block;
    -webkit-flex: 1 1 70%;
    -ms-flex: 1 1 70%;
    flex: 1 1 70%;
    color: #5f5f5f;
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
    padding-left: 22px;
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

