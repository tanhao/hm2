<template>
  <!--默认是隐藏的选择任意下注后才弹出 noset为隐藏 此处为联动 增加noset后所有彩种的 sc-gzVnrw dwVbnv 改为 sc-gzVnrw setbetright-->
  <div :class="selectList.length>0?'bet-button-wrapper':'bet-button-wrapper noset'">

    <div class="payment-footer">
      <a class="bets-showList-btn">已选{{selectList.length}}注</a>
      <div class="dDXIQD">
        <ul>
          <template v-for="item in presetBetAmtList">
            <li class="btn" @click="pagePresetAmt=item.num"><a>{{item.num}}</a></li>
          </template>
          <li class="btn-edit" @click="presetModelDisplay==true?presetModelDisplay=false:presetModelDisplay=true;"><a>编辑</a></li>
        </ul>
      </div>
      <hr class="sperate-line">
      <div class="bets">
        <input class="bets-input-value"
               oninput="this.value = this.value.replace(/\D/g, '').replace(/^0{1,}/g, '');" @keyup="betAmtInput" placeholder="输入金额"
               type="tel"
               v-model="pagePresetAmt">

        <div class="sc-1kapr6r-3 fhCqRY">
          <input class="styled-checkbox" v-model="pagePresetStatus" @change="presetStatusFun" type="checkbox"
                 id="enablePresetAmount">
          <label for="enablePresetAmount"></label>
          <ul>
            <li>预设</li>
            <li>金额</li>
          </ul>
        </div>
        <a class="cancel-btn" @click="clearBet">取消</a>
        <button class="result-btn" @click="bet(false)" v-bind:disabled="betStatus"><template v-if="!betStatus">确认 {{totalBetAmtResult}}</template><template v-else>下注中...</template> </button>
      </div>
    </div>

    <!--弹出屋-->
    <div class="openmo" v-show="presetModelDisplay">
      <div class="bet-amount-popup">
        <div class="lobnrg">
          <a class="back-toggle" @click="presetModelDisplay=false">Back</a>
          <span class="title">预设金额</span>
        </div>
        <template v-for="obj in presetBetAmtList">
          <div class="fieldset">
            <input v-model="obj.num" @keyup="presetModelFun" class="round-input" type="tel" oninput="this.value = this.value.replace(/\D/g, '').replace(/^0{1,}/g, '');" placeholder="设置金额" name="min" style="text-align: center;">
          </div>
        </template>
        <div class="fieldset">
          <button class="field-button" @click="presetModelDisplay=false" >确认</button>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
  import {mapActions, mapGetters} from 'vuex'
  import Utils from '@/components/comm/Utils'
  import Bet from '@/axios/api-bet'
  import {Indicator} from 'mint-ui'
  import UserApi from '@/axios/api-mem.js'
  import Lottery from '@/axios/api-game.js'
  import to from "await-to-js";
  export default {
    data() {
      return {
        amount: 10,
        chip: 10,
        pagePresetStatus: false,
        pagePresetAmt: '',
        presetModelDisplay:false,
        betStatus:false,
      }
    },
    created() {
      this.setNoteLists(null);
    },
    computed: {
      ...mapGetters(['noteLists','member', 'gameInfo','markets', 'betGameNo','betToken', 'gameId', 'betState', 'selectList', 'presetBetAmt', 'presetBetAmtList', 'presetBetAmtStatus']),
      betDetail() {
        let obj = {totalAmt: 0.00, expectWinAmt: 0};
        for (let item of this.selectList) {
          this.$set(item, 'betAmt', this.amount);
          if (item.check && /^[1-9]\d*$/.test(item.betAmt)) {
            obj.expectWinAmt += Utils.NumberMul(item.betAmt, item.odds - 1);
            obj.totalAmt = Utils.NumberAdd(obj.totalAmt, item.betAmt);

          }
        }
        obj.totalAmt = Utils.formatMoney(obj.totalAmt, 2);
        obj.expectWinAmt = Utils.formatMoney(obj.expectWinAmt, 2);
        return obj;
      },
      totalBetAmtResult() {
        let self = this;
        let totalAmtResult = 0;
        self.selectList.forEach(val => {
          self.$set(val, "betAmt", self.pagePresetAmt);
          totalAmtResult = Utils.NumberAdd(totalAmtResult, val.betAmt);
        })
        return parseInt(totalAmtResult);
      },
    },
    watch: {
      selectList() {
        let self = this;
        if (self.selectList.length == 0 && !self.presetBetAmtStatus) {
          self.pagePresetAmt = '';
        }
        self.pagePresetStatus = self.presetBetAmtStatus;
        if (self.pagePresetStatus) {
          self.pagePresetAmt = self.presetBetAmt;
        }
      },
      pagePresetAmt() {
        let self = this;
        if (self.pagePresetStatus) {
          self.setPresetBetAmt(self.pagePresetAmt);
        }
      },
    },
    filters:{
      betAmtFmt(val){
        return parseInt(val);
      }
    },
    methods: {
      ...mapActions(['setWhetherSwitch','setBetGameNo','setBalances','setNoteLists', 'setPresetBetAmt', 'setPresetBetAmtList', 'setPresetBetAmtStatus', 'setPromptInformation', 'setNoteListsFast', 'setSelectList', 'setSpecialNoteLists', 'setBetToken']),
      presetModelFun(){
        let self = this;
        self.presetBetAmtList.forEach(val=>{
          if(val.num>0){
            val.num = parseInt(val.num);
          }else{
            val.num='';
          }

        })
        self.setPresetBetAmtList(self.presetBetAmtList);
      },
      betAmtInput() {
        let self = this;
        if (self.presetBetAmtStatus) {
          self.setPresetBetAmt(self.pagePresetAmt);
        }
      },
      presetStatusFun() {
        let self = this;

        self.setPresetBetAmtStatus(self.pagePresetStatus);
        if (!self.presetBetAmtStatus) {
          self.setPresetBetAmt('');
        } else {
          self.setPresetBetAmt(self.pagePresetAmt);
        }

      },
      betAmtInputFun() {
        this.amount = '';
        this.chip = 0;
      },
      async bet(newOdds){
        let self = this;
        if (self.betStatus) return self.betError('下注正在进行中！',true);
        if(!self.pagePresetAmt || self.pagePresetAmt<=0){
          return self.setPromptInformation("请输入有效金额!");
        }
        self.$messageBox.close();
        self.betStatus = true;
        for(let i = 0;i<self.noteLists.length;i++){
          for(let j = 0;j<self.noteLists.length - i -1;j++){
            if(self.noteLists[j].oddsId > self.noteLists[j+1].oddsId){
              let obj = Object.assign({},self.noteLists[j]);
              self.noteLists[j] = self.noteLists[j+1];
              self.noteLists[j+1] = obj;
            }
          }
        }
        Indicator.open({text:'下注中...'});
        if(!self.betState){
          return self.betError('已封盘，不能下注',true);
        }
        let isCanBet = true;
        let autoNewOdds = 0;
        if(newOdds){
          autoNewOdds = 1;
        }

        let betRq = {
          lotteryId:self.gameId,
          gameNo:self.gameInfo.gameNo,
          betOdds:[],
          market:self.markets,
          autoNewOdds:autoNewOdds,
          betSource:'normal',
        };
        let msgDatas = '';
        self.noteLists.forEach(item=>{
          self.$set(item,"betAmt",self.pagePresetAmt);
          if(item.status!='0'){
            msgDatas+=self.$t(item.categoryKey) + self.$t(item.playKey) + self.$t(item.oddsKey.toUpperCase()) +'赔率已关闭。不能下注';
            isCanBet = false;
          }
          item.betAmt = self.pagePresetAmt;
          let lar = /^[1-9]\d*$/.test(item.betAmt);
          if(!lar){
            item.betAmt = self.pagePresetAmt;


          }

          if(item.check && /^[1-9]\d*$/.test(item.betAmt)){
            let obj = {oddsId:item.oddsId,odds:item.odds,betAmt:item.betAmt,betContent:item.betContent};
            betRq.betOdds.push(JSON.parse(JSON.stringify(obj)));
          }
        })
        if(!isCanBet){
          self.betStatus = false;
          return self.betError(msgDatas,false);
        }
        if(betRq.betOdds.length===0){
          self.betStatus = false;
          return self.betError('请至少选中一条进行下注！',false);
        }
        let [err,data] = await to(Bet.bet(betRq));

        if(err || !data.success){
          Indicator.close();
          if (data.code == 2009) {
            self.$messageBox({$type:'confirm',message:'赔率变更，是否接受新赔率!',title:'提示'
              ,closeOnClickModal:false,showCancelButton:true,confirmButtonText:'接受',cancelButtonText:'不接受'}).then(action => {
              if (Object.is(action, 'confirm')) {
                self.bet(true);
              }

            }).catch((err) => {
              if (Object.is(err, 'cancel')) {
                self.setNoteLists(null);
                self.setSelectList(null);
                self.$emit("clearSpecialSelect",false);
                self.betStatus = false;
              }
            })
          }
        }

        if(data && data.success){
          self.setPromptInformation('下单成功!');
          let [err1,data1] = await to(this.$api.mem.balanceInfo());
          if(err1 || !data1.success){
            Indicator.close();
            self.setPromptInformation(data1.message);
          }

          if(data1.success){
            Indicator.close();
            self.setBalances(data1.data);
        }
          self.setNoteLists(null);
          self.setSelectList(null);
          self.$emit("clearSpecialSelect",false);
        }

        self.betStatus = false;

        Indicator.close();
      },
      //选中筹码
      chipLess(index) {
        return this.chip === index ? 'selected' : '';
      },
      clearBet() {
        let self = this;
        if(self.betStatus){
          return;
        }
        this.selectList.forEach((item) => {
          self.$delete(item, 'choose');
          self.$delete(item, 'betAmt');
        });
        this.setSelectList(null);
        this.$emit('clearSpecialSelect', true);
      },
      betError(msg) {
        this.setPromptInformation(msg).then(() => {
          this.betStatus = false;
          Indicator.close();
          //this.clearBet();
        });
      }
    },

  }
</script>
<style scoped>

  .openmo {
    animation: bounceIn 0.75s forwards;
    background-color: rgba(55, 55, 55, 0.7);
    position: fixed;
    top: 0px;
    left: 0px;
    right: 0px;
    bottom: 0px;
    z-index: 100;

  }

  .bets-showList-btn {
    position: absolute;
    top: -12px;
    left: calc(50% - 44px);
    font-size: 12px;
    width: 88px;
    height: 22px;
    color: white;
    text-align: center;
    line-height: 22px;
    z-index: 20;
    background: rgb(158, 158, 158);
    border-radius: 12px;
  }

  .payment-footer {
    height: 90px;
    padding-top: 12px;
    box-shadow: rgba(0, 0, 0, 0.1) 0px -8px 16px;
    margin: auto;
  }

  .dDXIQD {
    line-height: 22px;
    overflow: hidden;
    padding: 6px;
  }

  .dDXIQD > ul {
    margin: 0px;
    padding: 0px;
    list-style: none;
  }

  .dDXIQD > ul > li.btn {
    margin: 0px;
    list-style: none;
  }

  .dDXIQD > ul > li {
    width: 16.66%;
    float: left;
    font-size: 12px;
    height: 24px;
    box-sizing: border-box;
    padding: 0px 5px;
  }

  .dDXIQD > ul > li.btn > a {
    width: 100%;
    text-align: center;
    height: 22px;
    display: inline-block;
    line-height: 21px;
    font-size: 12px;
    border-radius: 12px;
    border-width: 1px;
    border-style: solid;
    border-color: rgb(4, 186, 238);
    border-image: initial;
  }

  .dDXIQD > ul > li.btn-edit {
    text-align: center;
    margin: 0px;
    list-style: none;
  }

  .payment-footer .sperate-line {
    border-bottom: 1px solid rgb(234, 234, 234);
    border-top: none;
    border-left: none;
    border-right: none;
    padding: 0px;
    margin: 0px 10px;
  }

  .payment-footer .bets {
    height: 32px;
    padding: 10px;
  }

  .payment-footer .bets .bets-input-value {
    width: calc(55% - 80px);
    height: 32px;
    line-height: 32px;
    vertical-align: middle;
    box-sizing: border-box;
    float: left;
    margin-right: 5px;
    border-radius: 24px;
    border-width: 1px;
    border-style: solid;
    border-color: rgb(118, 118, 118);
    border-image: initial;
    padding: 0px 10px;
  }

  .fhCqRY {
    width: 52px;
    height: 32px;
    display: inline-block;
    float: left;
    margin-right: 8px;
  }

  .fhCqRY .styled-checkbox {
    position: absolute;
    opacity: 0;
  }

  .fhCqRY .styled-checkbox + label {
    position: relative;
    cursor: pointer;
    float: left;
    top: 6px;
    padding: 0px;
  }

  .fhCqRY .styled-checkbox:checked + label::after {
    content: "";
    position: absolute;
    left: 6px;
    top: 10px;
    width: 2px;
    height: 2px;
    box-shadow: rgb(118, 118, 118) 2px 0px 0px, rgb(118, 118, 118) 4px 0px 0px, rgb(118, 118, 118) 4px -2px 0px, rgb(118, 118, 118) 4px -4px 0px, rgb(118, 118, 118) 4px -6px 0px, rgb(118, 118, 118) 4px -8px 0px;
    transform: rotate(45deg);
    background: rgb(118, 118, 118);
  }

  .fhCqRY .styled-checkbox + label::before {
    content: "";
    margin-right: 0px;
    display: inline-block;
    vertical-align: text-top;
    width: 20px;
    height: 20px;
    background: white;
    border-width: 1px;
    border-style: solid;
    border-color: rgb(221, 221, 221);
    border-image: initial;
    border-radius: 4px;
  }

  .fhCqRY > ul {
    float: right;
    font-size: 12px;
    margin: 1px 0px 0px;
    padding: 0px;
  }

  .fhCqRY > ul > li {
    line-height: 14px;
  }

  .payment-footer .bets .cancel-btn {
    font-family: Tahoma;
    color: red;
    font-size: 14px;
    float: left;
    margin-right: 8px;
    margin-top: 6px;
    font-weight: bold;
  }

  .payment-footer .bets .result-btn {
    line-height: 32px;
    color: white;
    font-size: 14px;
    font-weight: bold;
    width: calc(45% - 22px);
    white-space: nowrap;
    height: 32px;
    background: linear-gradient(121deg, rgb(76, 152, 242), rgb(70, 123, 185), rgb(65, 102, 144)) white;
    border-width: 1px;
    border-style: solid;
    border-color: initial;
    border-image: initial;
    border-radius: 25px;
  }

  .payment-footer .bets {
    height: 32px;
    padding: 10px;
  }

  .openmo .bet-amount-popup {
    width: 70%;
    background-color: rgb(255, 255, 255);
    text-align: center;
    padding-bottom: 12px;
    box-shadow: rgba(100, 100, 100, 0.8) 0px 0px 30px;
    position: absolute;
    top: 15%;
    left: 15%;
    border-radius: 4px;
    outline: none;
    border-width: 0px;
    border-style: initial;
    border-color: initial;
    border-image: initial;
  }

  .openmo .lobnrg {
    position: relative;
    top: 0px;
    left: 0px;
    width: 100%;
    height: 45px;
    background: linear-gradient(to right, rgb(125, 202, 255) 0%, rgb(26, 81, 148) 100%);
  }

  .openmo .lobnrg .back-toggle {
    font-size: 0px;
    position: absolute;
    width: 20px;
    height: 25px;
    top: 10px;
    left: 10px;
    display: inline-block;
    vertical-align: middle;
    transform: rotate(90deg);
    background: url(../../images/tu1.png) -34px -117px / 410px 130px no-repeat;
    background-position: -34px -117px;
    transition: transform 0.1s ease-out 0s;
  }

  .openmo .lobnrg .title {
    line-height: 45px;
    text-align: center;
    font-weight: bold;
    color: rgb(255, 255, 255);
  }

  .openmo .fieldset:not(:last-child) {
    border-bottom: 1px solid rgb(234, 234, 234);
  }


  .openmo .fieldset {
    min-height: 40px;
    height: auto;
    line-height: 40px;
    background-color: rgb(255, 255, 255);
    box-sizing: border-box;
    position: relative;
    padding: 0px 20px;
  }

  .openmo .fieldset .round-input {
    display: inline-block;
    height: 90%;
    -webkit-appearance: none;
    width: 96px;
    padding: 7px 14px;
    border-radius: 24px;
    border-width: 1px;
    border-style: solid;
    border-color: rgb(118, 118, 118);
    border-image: initial;
  }

  .openmo .field-button {
    margin-top: 10px;
    display: block;
    height: 40px;
    line-height: 40px;
    text-align: center;
    width: 100%;
    color: rgb(255, 255, 255);
    font-size: 0.875rem;
    border-width: 0px;
    border-style: initial;
    border-color: initial;
    border-image: initial;
    border-radius: 40px;
    background: linear-gradient(to right, rgb(125, 202, 255) 0%, rgb(26, 81, 148) 100%);
  }


  .noset {
    display: none;
  }

  #chiosenum {
    font-weight: bold;
    color: red;
  }

  #totalMoney, #expectMoney {
    font-weight: bold;
    color: green;
  }

  .bet-button-wrapper {
    box-shadow: 0 -2px 39px -1px rgba(0, 0, 0, 0.55), 0 -4px 5px 0 rgba(0, 0, 0, .14), 0 -1px 10px 0 rgba(0, 0, 0, 0.17);
    position: fixed;
    z-index: 2;
    right: 0;
    left: 0px;
    bottom: 0;
    height: 100px;
    padding: 0px;
    /*padding: 7px 10px;*/
    margin: auto;
    background-color: #fff;
    font-size: 14px;
  }

  .selected {
    margin-top: -10px;
  }

  .bet-button-wrapper .preset-btn {
    display: inline-block;
    vertical-align: middle;
    width: 36px;
    height: 38px;
    margin-right: 6px;
    background: url("../../images/preset.png") no-repeat;
  }

  .bet-button-wrapper .preset10 {
    background-position: top center;
  }

  .bet-button-wrapper .preset50 {
    background-position: center -45px;
  }

  .bet-button-wrapper .preset100 {
    background-position: center -90px;
  }

  .bet-button-wrapper .preset500 {
    background-position: center -135px;
  }

  .bet-button-wrapper .preset1000 {
    background-position: center -181px;
  }

  .bet-button-wrapper .preset5000 {
    background-position: center -226px;
  }

  .bet-button-wrapper .total-container {
    padding: 3px 0px 5px;
  }

  .bet-button-wrapper .button-container {
    font-size: 0px;
  }

  .bet-button-wrapper .button-container input {
    padding: 0px;
    margin: 0px;
    border: 1px solid #eaeaea;
    width: 100px;
    height: 32px;
    padding-left: 12px;
    line-height: 32px;
    outline: none;
  }

  .bet-button-wrapper button {
    height: 32px;
    padding: 0px;
    line-height: 32px;
    text-align: center;
    outline: none;
    -webkit-appearance: none;
    border: 0;
    width: 70px;
    border-radius: 4px;
    color: #fff;
  }

  .bet-button-wrapper .button-container button {
    margin-left: 10px;
  }

  .bet-button-wrapper .bet-button {
    background: linear-gradient(to right, #f00 0%, #f00 100%);
  }

  .bet-button-wrapper .add-button {
    background-color: #f79505;
  }

  .bet-button-wrapper .min-bet {
    float: right;
    margin-top: 4px;
    background-color: #673AB7;
  }

  .bet-button-wrapper .clear-button {
    background-color: #151414;
  }

  @media screen and (max-width: 360px) {
    .bet-button-wrapper .preset-btn {
      width: 30px;
      height: 32px;
      margin-right: 4px;
      background-size: cover;
    }

    .bet-button-wrapper .preset10 {
      background-position: top center;
    }

    .bet-button-wrapper .preset50 {
      background-position: center -37px;
    }

    .bet-button-wrapper .preset100 {
      background-position: center -75px;
    }

    .bet-button-wrapper .preset500 {
      background-position: center -115px;
    }

    .bet-button-wrapper .preset1000 {
      background-position: center -151px;
    }

    .bet-button-wrapper .preset5000 {
      background-position: center -189px;
    }

    .bet-button-wrapper .button-container button {
      margin-left: 5px;
      width: 63px;
    }

    .bet-button-wrapper .button-container input {
      width: 80px;
    }

    .bet-button-wrapper .min-bet {
      font-size: 12px;
      width: 68px;
      margin-top: 0px;
    }
  }

  @media screen and (min-width: 414px) {
    .bet-button-wrapper .button-container button {
      width: 83px;
    }
  }
</style>
<!--版本1-->
<!--<style scoped>-->
<!--input {-->
<!--border: 0;-->
<!--}-->
<!--label {-->
<!--width: 60px;-->
<!--}-->
<!--.popquic {-->
<!--position: relative;-->
<!--bottom: 95px;-->
<!--margin: 0 auto;-->
<!--width: 100%;-->
<!--background-color: #fff;-->
<!--z-index: 999-->
<!--}-->

<!--.footbet {-->
<!--vertical-align: middle;-->
<!--z-index: 999;-->
<!--position: absolute;-->
<!--width: 100%;-->
<!--height: 50px;-->
<!--background-color: #fff;-->
<!-- -webkit-box-sizing: border-box;-->
<!--box-sizing: border-box;-->
<!--left: 0;-->
<!--bottom: 0;-->
<!--display: -webkit-box;-->
<!--display: -ms-flexbox;-->
<!--display: flex;-->
<!--}-->

<!--.btn-bet {-->
<!--min-width: 320px;-->
<!--padding: 10px 0;-->
<!--margin: 0 auto;-->
<!--}-->

<!--.btn-bet input:nth-child(2) {-->
<!--max-width: 33%;-->
<!--height: 30px;-->
<!--border: 1px solid #ccc;-->
<!--border-radius: 4px;-->
<!--padding: 0 5px;-->
<!--}-->

<!--input[type="checkbox" i] {-->
<!--top: 4px;-->
<!--position: relative;-->
<!--margin: 0px 1px 0 7px;-->
<!--width: 18px;-->
<!--height: 18px;-->
<!--}-->

<!--.quickS {-->
<!--display: table;-->
<!--margin: 0 auto;-->
<!--font-weight: bold;-->
<!--font-size: 13px;-->
<!--color: #000;-->
<!--}-->

<!--.quickS span {-->
<!--margin: 0 8px;-->
<!--text-align: center;-->
<!--display: inline-block;-->
<!--width: 45px;-->
<!--height: 45px;-->
<!--line-height: 45px;-->
<!--cursor: pointer;-->
<!--}-->

<!--.quickS span:hover {-->
<!--position: relative;-->
<!--z-index: 3;-->
<!--top: -8px;-->
<!--}-->
<!--.selected {-->
<!--position: relative;-->
<!--z-index: 3;-->
<!--top: -8px;-->
<!--}-->
<!--.quickS span:first-child {-->
<!--background: url(../assets/images/quick.png);-->
<!--background-size: 300px;-->
<!--background-position: -10px -3px;-->
<!--}-->

<!--.quickS span:nth-child(2) {-->
<!--background: url(../assets/images/quick.png);-->
<!--background-size: 300px;-->
<!--background-position: -57px -3px;-->
<!--}-->

<!--.quickS span:nth-child(3) {-->
<!--background: url(../assets/images/quick.png);-->
<!--background-size: 300px;-->
<!--background-position: -102px -3px;-->
<!--}-->

<!--.quickS span:nth-child(4) {-->
<!--background: url(../assets/images/quick.png);-->
<!--background-size: 300px;-->
<!--background-position: 151px -3px;-->
<!--}-->

<!--.quickS span:nth-child(5) {-->
<!--background: url(../assets/images/quick.png);-->
<!--background-size: 300px;-->
<!--background-position: -195px -3px;-->
<!--}-->

<!--.quickS span:nth-child(6) {-->
<!--background: url(../assets/images/quick.png);-->
<!--background-size: 300px;-->
<!--background-position: -243px -3px;-->
<!--}-->

<!--.btn-pink {-->
<!--border-top-left-radius: 30px;-->
<!--border-bottom-left-radius: 30px;-->
<!--}-->

<!--.btn-blue, .btn-pink {-->
<!--width: 80px;-->
<!--color: #fff;-->
<!--height: 32px;-->
<!--line-height: 32px;-->
<!--outline: none;-->
<!--cursor: pointer;-->
<!--}-->

<!--.btn-pink {-->
<!--background-color: #ff9200;-->
<!--box-shadow: 2px 2px 5px #f5b3bb;-->
<!--}-->

<!--.btn-blue {-->
<!--border-top-right-radius: 30px;-->
<!--border-bottom-right-radius: 30px;-->
<!--margin-right: 1px;-->
<!--}-->

<!--.btn-blue {-->
<!--background-color: #1c4fa8;-->
<!--box-shadow: 2px 2px 5px #a29bbf;-->
<!--}-->

<!--@media screen and (max-width: 320px) {-->
<!--.quickS span {-->
<!--margin: 0 3px;-->
<!--}-->

<!--.btn-bet input:nth-child(2) {-->
<!--max-width: 21%;-->
<!--}-->
<!--}-->
<!--</style>-->
