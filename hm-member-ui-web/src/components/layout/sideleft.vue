<template>
  <div class="side_left" id="side">
    <div class="user_info">
      <div class="title">账户信息</div>
      <div class="zhanghu">
        <div class="info">
          <label>账号：</label>
          <div class="inline-name">
            <span>{{member.username}} ({{member.marketOpen}}盘)</span>
          </div>
          <label>余额：</label>
          <div class="inline-name">
            <span>{{balance | moneyFmt}} <div style="right: 5px; position: absolute; display: initial;"><a @click="balanceSearchFun" class="refresh-icon"></a></div> </span>
          </div>
          <label>未结算金额：</label>
          <div class="inline-name" style="width:90px;">
            <span>{{betWaiting | moneyFmt}} </span>
          </div>
        </div>

      </div>
    </div>

    <div v-if="intelligenceBet" class="user_info">
      <div class="title">智投止损止盈设置</div>
      <div class="zhanghu">
        <div class="info">
          <label class="red pb10">止损止盈金额是控制当天金额</label>
          <div style="display: table;margin-bottom: 5px">
          <label>止损：</label>
          <div class="inline-name">
            <span style="display: flex">
              <input style="width: 75px;margin-right: 10px;height: 15px;"
                     oninput="this.value = this.value.replace(/\D/g, '').replace(/^0{1,}/g, '');"  @focus="profitFocusFmt(lossMoney,'loss')" @blur="profitBlurFmt(lossMoney,'loss')" v-model="lossMoney" maxlength="6" type="text" placeholder="这里是金额">
              <input  style="border: 0px;height: auto" type="button" @click="lossOkBtn" value="设置" class="hyzthov">
            </span>
          </div>
          </div>
          <div style="display: table">
          <label>止盈：</label>
          <div class="inline-name">
            <span style="display: flex">
              <input style="width: 75px;margin-right: 10px;height: 15px;"
                     oninput="this.value = this.value.replace(/\D/g, '').replace(/^0{1,}/g, '');" @focus="profitFocusFmt(profitMoney,'profit')" @blur="profitBlurFmt(profitMoney,'profit')" v-model="profitMoney" maxlength="6" type="text" placeholder="这里是金额">
              <input  style="border: 0px;height: auto" type="button" @click="profitOkBtn" value="设置" class="hyzthov">
            </span>
          </div>
          </div>
        </div>

      </div>
    </div>

    <div style="display: table;margin-top: 10px">
      <a href="https://app.xiazai328.com/app.php/1" target="_blank">
        <button class="supportbtn">
          <span>安卓手机客户端下载</span>
        </button>
      </a>
    </div>
    <input type="hidden" id="userType" value="0">
    <div class="betdone" id="lastBets" style="display: block;">
      <div class="title"><span>最新注单</span></div>
    </div>
    <div id="betResultPanel" v-show="listInit.list && listInit.list.length>0">

      <div class="control s0">
        <a style="cursor:pointer" @click="clearLatesNoteList">返 回</a>
      </div>
      <div id="betResultDrawNumber" class="Paneltitle">{{listInit.gameNo}}期</div>
      <div class="bresults">
        <ul class="bets" id="betReulstList"></ul>
        <ul class="bets">
          <template v-for="(item,index) in listInit.list">
            <li v-if="gameId==101 || gameId==102 || gameId==103 || gameId==104 ||
                  gameId==105 || gameId==106 || gameId==107 || gameId==108 || gameId==109 || gameId==110 || gameId == 111 || gameId == 112">

              <p>注单号：<span class="bid">{{item.orderId}}</span></p>
              <p class="contents">
                <span class="text">{{$t(item.lotteryId)}}&nbsp;{{ $t(JSON.parse(item.keyName).playKey)}}</span>
                <span v-if="/^[0-9]\d*$/.test(item.oddsKey)">{{item.oddsKey}}</span>
                <span v-if="!/^[0-9]\d*$/.test(item.oddsKey)">{{$t(item.oddsKey.toUpperCase())}}</span>
                @<span class="odds">{{item.odds}}</span></p>
              <p>下注额：￥{{item.betAmt | moneyFmt}}</p></li>

            <li v-if="gameId==201 || gameId==202 || gameId==203 || gameId==204 ||
                  gameId==205 || gameId==206 || gameId==207 || gameId==208 || gameId==209 || gameId==210 || gameId == 211">

              <p>注单号：<span class="bid">{{item.orderId}}</span></p>
              <p class="contents">
                <span class="text">{{$t(item.lotteryId)}}&nbsp;{{$t(JSON.parse(item.keyName).playKey)}}</span>
                <span v-if="/^[0-9]\d*$/.test(item.oddsKey)">{{item.oddsKey}}</span>
                <span v-if="!/^[0-9]\d*$/.test(item.oddsKey)">{{$t(item.oddsKey.toUpperCase())}}</span>
                <span v-if="item.betContent">{{item.betContent}}</span>
                @<span class="odds">{{item.odds}}</span></p>
              <p>下注额：￥{{item.betAmt | moneyFmt}}</p></li>

            <li v-if="gameId==301 || gameId==302 || gameId==303 || gameId==304">
              <p>注单号：<span class="bid">{{item.orderId}}</span></p>
              <p class="contents">
                <span v-if="item.betContent">{{$t(item.lotteryId)}}&nbsp;{{$t(JSON.parse(item.keyName).playKey)}}{{$t(item.oddsKey.toUpperCase())}}</span>
                <span v-if="!item.betContent">
                  <span v-if="JSON.parse(item.keyName).playKey=='sum'">{{$t('THESUM')}}</span>
                  <span v-else>{{$t(JSON.parse(item.keyName).playKey)}}</span>


                  <span v-if="/^[0-9]\d*$/.test(item.oddsKey)">{{item.oddsKey}}</span>
                  <span v-if="!/^[0-9]\d*$/.test(item.oddsKey)">{{$t(item.oddsKey.toUpperCase())}}</span></span>
                <span v-if="item.betContent">{{item.betContent}}</span>
                @<span class="odds">{{item.odds}}</span></p>
              <p>下注额：￥{{item.betAmt | moneyFmt}}</p>
            </li>
            <li v-if="gameId==401 || gameId==402 || gameId==403 || gameId==404 ||
                  gameId==405">

              <p>注单号：<span class="bid">{{item.orderId}}</span></p>
              <p class="contents">
                <span class="text">{{$t(JSON.parse(item.keyName).playKey)}}</span>
                <span v-if="/^[0-9]\d*$/.test(item.oddsKey)">{{item.oddsKey}}</span>
                <span v-if="!/^[0-9]\d*$/.test(item.oddsKey)">{{$t(item.oddsKey.toUpperCase())}}</span>
                @<span class="odds">{{item.odds}}</span></p>
              <p>下注额：￥{{item.betAmt | moneyFmt}}</p></li>

            <li v-if="gameId==501 || gameId==502">

              <p>注单号：<span class="bid">{{item.orderId}}</span></p>
              <p class="contents">
                <span class="text">{{$t(JSON.parse(item.keyName).playKey)}}</span>
                <span v-if="/^[0-9]\d*$/.test(item.oddsKey)">{{item.oddsKey}}</span>
                <span v-if="!/^[0-9]\d*$/.test(item.oddsKey)">{{$t('kl8_'+item.oddsKey)}}</span>
                @<span class="odds">{{item.odds}}</span></p>
              <p>下注额：￥{{item.betAmt | moneyFmt}}</p></li>

            <li v-if="gameId==601">

              <p>注单号：<span class="bid">{{item.orderId}}</span></p>
              <p class="contents">
              <span class="text" v-if="JSON.parse(item.keyName).playKey!='q2zx' && JSON.parse(item.keyName).playKey!='q3zx'
                                   && JSON.parse(item.keyName).playKey!='q2gx' && JSON.parse(item.keyName).playKey!='q3gx'&& JSON.parse(item.keyName).playKey.indexOf('rx')==-1">
                {{$t(JSON.parse(item.keyName).playKey)}}</span>
                <span v-if="/^[0-9]\d*$/.test(item.oddsKey)">{{item.oddsKey}}</span>
                <span v-if="!/^[0-9]\d*$/.test(item.oddsKey)">{{$t(item.oddsKey)}}</span>
                <span v-if="item.betContent">{{item.betContent}}</span>
                @<span class="odds">{{item.odds}}</span></p>
              <p>下注额：￥{{item.betAmt | moneyFmt}}</p></li>
            <li v-if="gameId==701">

              <p>注单号：<span class="bid">{{item.orderId}}</span></p>
              <p class="contents">
                <span class="text">{{$t(JSON.parse(item.keyName).playKey)}}</span>
                <span v-if="/^[0-9]\d*$/.test(item.oddsKey)">{{item.oddsKey}}</span>
                <span v-if="!/^[0-9]\d*$/.test(item.oddsKey)">{{$t(item.oddsKey)}}</span>
                @<span class="odds">{{item.odds}}</span></p>
              <p>下注额：￥{{item.betAmt | moneyFmt}}</p></li>
            <li v-if="gameId==801">

              <p>注单号：<span class="bid">{{item.orderId}}</span></p>
              <p class="contents">
                <span v-if="item.oddsKey!='tmbs'" class="text">{{$t(JSON.parse(item.keyName).playKey)}}</span>
                <span v-if="/^[0-9]\d*$/.test(item.oddsKey)">{{item.oddsKey}}</span>
                <span v-if="!/^[0-9]\d*$/.test(item.oddsKey)">{{$t(item.oddsKey)}}</span>
                <span v-if="item.betContent">{{item.betContent}}</span>
                @<span class="odds">{{item.odds}}</span></p>
              <p>下注额：￥{{item.betAmt | moneyFmt}}</p></li>
          </template>
        </ul>
        <table class="total s0">
          <tbody>
          <tr>
            <td class="label">下注笔数</td>
            <td id="betResultCount">{{listInit.totalSum}}笔</td>
          </tr>
          <tr>
            <td class="label">合计金额</td>
            <td id="betResultTotal">￥{{listInit.totalBetAmt | moneyFmt}}</td>
          </tr>
          </tbody>
        </table>
      </div>
    </div>

  </div>

</template>

<script>
  import {mapActions, mapGetters} from 'vuex'
  import Utils from '@/components/comm/Utils.js'
  import {Indicator} from 'mint-ui'
  export default {
    name: "sideleft",
    data() {
      return {
        totalSum: 0,
        totalBetAmt: 0.00,
        params: {
          lotteryId: null,
          status: 'WAITING',
          accountDay: Utils.formatDate(new Date(),'yyyy-MM-dd'),
          page:1,
          size:9999,
        },
        profitMoney:'',
        lossMoney:'',
        intelligenceBet:process.env.region=="test"?true:false
      }
    },
    computed: {
      ...mapGetters(['member','balance','betWaiting','winLose', 'gameInfo', 'gameId', 'markets', 'latesNoteList']),
      listInit() {
        let results = {'list': [], 'totalSum': 0, 'totalBetAmt': 0.00, 'gameNo': ''};
        results.list = this.latesNoteList;
        results.totalSum = this.latesNoteList.length;
        for (let i = 0; i < results.list.length; i++) {
          results.totalBetAmt = Utils.NumberAdd(results.totalBetAmt, results.list[i].betAmt);
        }
        if (results.list.length > 0) {
          results.gameNo = results.list[0].gameNo;
        }
        return results;
      },
      balanceAndBetAmtSum() {
        let self = this;
        let resultObj = {'balance': '', 'betAmtSum': ''};
        resultObj.balance = self.balance;
        resultObj.betAmtSum = self.betWaiting;
        return resultObj;
      },

    },
    watch:{
      gameId(){
        let self = this;
        self.params.lotteryId = self.gameId;
        self.params.size = 20;
        let date = new Date();
        let hh = date.getHours();
        if (hh < 7) {
          date.setDate(date.getDate() - 1);
          let dateParam = Utils.formatDate(date, 'yyyy-MM-dd');
          let spliceArr = dateParam.split('-');
          self.params.accountDay = Utils.formatDate(new Date(spliceArr[0]+'-'+spliceArr[1]+'-'+spliceArr[2]),'yyyy-MM-dd');
        }
        this.$api.Bet.betList(self.params).then(val => {
          if (val.code === 10000){
            self.setLatesNoteList(val.data.dataList);
          }
        })
      }
    },
    methods: {
      ...mapActions(['setLatesNoteList','setBalances']),
      clearLatesNoteList() {
        this.setLatesNoteList([]);

      },
      profitOkBtn(){
        let oldMny = this.profitMoney.replace(/,/g, '');
        if(oldMny.indexOf(".")>0){
          oldMny = oldMny.replace(/0+?$/,"");//去除尾部多余的0
          oldMny = oldMny.replace(/[.]$/,"");//如果最后一位是.则去掉
        }
        let params = {stopProfit:oldMny,stopLoss:''};
        this.profitLossUpdateFun(params);
      },
      lossOkBtn(){
        let oldMny = this.lossMoney.replace(/,/g, '');
        if(oldMny.indexOf(".")>0){
          oldMny = oldMny.replace(/0+?$/,"");//去除尾部多余的0
          oldMny = oldMny.replace(/[.]$/,"");//如果最后一位是.则去掉
        }
        let params = {stopProfit:'',stopLoss:oldMny};
        this.profitLossUpdateFun(params);
      },
      profitLossUpdateFun(params){
        this.$api.Bet.updateStopProfitLoss(params).then(val=>{
          if(val.success){
            this.$messageBox.alert(val.message);

          }
        });
      },
      profitBlurFmt(value,mode){
        let obj = value;
        let mnyReg = /^([1-9][0-9]*|(([0]\.[0-9]{0,2}[1-9]\d*|[1-9][0-9]*\.\d*)))$/;//根据需求修改
        if(!(mnyReg.test(obj))){
          //layer.tips("请输入正确的资金", $(this).attr("id"),{time:1000});
          return;
        }
        /*let n = 3; //保留得小数位
        obj = parseFloat(obj).toFixed(n); //obj=123456.789*/
        let left = obj.split(".")[0].split("").reverse();//left = ["6","5","4","3","2","1"]
        let right = obj.split(".")[1]; //right = "789"
        let total = new Array();
        for (let i = 0; i < left.length; i++) {
          total.push(left[i]);
          if((i + 1) % 3 == 0 && (i + 1) != left.length){
            total.push(",");
          }
        } //total = ["6","5","4",",","3","2","1"]
        if(mode=='profit'){
          this.profitMoney = total.reverse().join("");

        }else{
          this.lossMoney = total.reverse().join("");
        }
        return total.reverse().join("");
      },
      profitFocusFmt(value,mode){
        let oldMny = value.replace(/,/g, '');
        if(oldMny.indexOf(".")>0){
          oldMny = oldMny.replace(/0+?$/,"");//去除尾部多余的0
          oldMny = oldMny.replace(/[.]$/,"");//如果最后一位是.则去掉
        }
        if(mode=='profit'){
          this.profitMoney = oldMny;

        }else{
          this.lossMoney = oldMny;
        }
        return oldMny;
      },
      balanceSearchFun(){
        let self = this;
        Indicator.open({text:'加载中...'});
        this.$api.Member.balanceInfo().then(resBalanceInfo => {
          self.setBalances(resBalanceInfo.data);
        }).finally(()=>{
          Indicator.close();
        })
        self.params.lotteryId = self.gameId;
        self.params.size = 20;
        let date = new Date();
        let hh = date.getHours();
        if (hh < 7) {
          date.setDate(date.getDate() - 1);
          let dateParam = Utils.formatDate(date, 'yyyy-MM-dd');
          let spliceArr = dateParam.split('-');
          self.params.accountDay = Utils.formatDate(new Date(spliceArr[0]+'-'+spliceArr[1]+'-'+spliceArr[2]),'yyyy-MM-dd');
        }
        this.$api.Bet.betList(self.params).then(val => {
          if (val.code === 10000){
            self.setLatesNoteList(val.data.dataList);
          }
        })
      }
    },
    mounted(){
      this.$api.Bet.getStopProfitLoss().then(val=>{
        if(val.success){
          this.profitBlurFmt(val.data.stopProfit.toString(),'profit');
          this.profitBlurFmt(val.data.stopLoss.toString(),'loss');
        }

      });
    },
    create(){
    },
    filters: {
      moneyFmt(val) {
        if (!val) {
          return '0.00';
        }
        return Utils.formatMoney(val, 2);
      },
      idFmt(val) {
        if (val) {
          let valSubString = val.toString();
          return valSubString.substring(8);
        }

      }
    },
  }
</script>

<style scoped>
  .supportbtn {
    /* padding: 4px 0 3px 0; */
    width: 180px;
    margin-left: 5px;
    margin-top: 2px;
    border: 1px solid #29904f;
    background: rgb(93, 210, 136);
    background: -moz-linear-gradient(top, rgba(93, 210, 136, 1) 0%, rgba(57, 197, 109, 1) 100%);
    background: -webkit-linear-gradient(top, rgba(93, 210, 136, 1) 0%, rgba(57, 197, 109, 1) 100%);
    background: linear-gradient(to bottom, rgba(93, 210, 136, 1) 0%, rgba(57, 197, 109, 1) 100%);
    filter: progid:DXImageTransform.Microsoft.gradient(startColorstr='#5dd288', endColorstr='#39c56d', GradientType=0);
    float: left;
    color: #fff;
    font-size: 14px;
    -webkit-border-radius: 4px;
    -moz-border-radius: 4px;
    border-radius: 4px;
    cursor: pointer;
    font-weight: bold;
    text-align: center;
    line-height: 30px;
  }
  .supportbtn:hover {
    background: rgb(0, 198, 86);
    background: -moz-linear-gradient( top, rgba(0, 198, 86, 1) 0%, rgba(0, 198, 86, 1) 100% );
    background: -webkit-linear-gradient( top, rgba(0, 198, 86, 1) 0%, rgba(0, 198, 86, 1) 100% );
    background: linear-gradient( to bottom, rgba(0, 198, 86, 1) 0%, rgba(0, 198, 86, 1) 100% );
    filter: progid:DXImageTransform.Microsoft.gradient(startColorstr='#00c656', endColorstr='#00c656', GradientType=0);
  }

</style>
