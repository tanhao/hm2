备胎（）<template>
  <div>
    <div class="input-group input-group-lg" id="footer-input">
      <span @click="chipShow?chipShow=false:chipShow=true" class="input-group-addon chongma">
        <div class="jqm_btn_cm_b">筹码</div>
<!--        <<input class="jqm_btn_cm_b" value="筹码"  type="button">div class="jqm_btn_cm_b>筹码</div>-->
      </span>
      <input type="tel" oninput="this.value = this.value.replace(/\D/g, '').replace(/^0{1,}/g, '');" class="quick-money form-control jqm_order_price"
             @keydown="betAmtValidata" @focus="betAmtInputFun" @blur="saveAmountFun" v-model="pageAmount" placeholder="输入金额">
      <span class="input-group-addon quick-bet" id="kjbet" @click="whetherBet">
        <div class="jqm_btn_order">确认下注</div>
<!--        <input class="jqm_btn_order" value="确认下注" type="button">-->
      </span>
      <span class="input-group-addon quick-qingkong" @click="clearBet">
        <div class="jqm_btn_qing">清空</div>
<!--        <input class="jqm_btn_qing" value="清空" type="button">-->
      </span>
      <span class="input-group-addon mingxi" @click="details">
        <div class="jqm_btn_mc_b">明细</div>
<!--        <input class="jqm_btn_mc_b" value="明细" type="button">-->
      </span>
      <ul class="choumadiv rcm" v-show="chipShow"><!--默认不显示，点筹码时才让他显示-->
        <li class="c_money cmitem cm1" :class="chipLess(2)" @click="setAmountFun(2)" t="2"></li>
        <li class="c_money cmitem cm2" :class="chipLess(10)" @click="setAmountFun(10)" t="10"></li>
        <li class="c_money cmitem cm3" :class="chipLess(50)" @click="setAmountFun(50)" t="50"></li>
        <li class="c_money cmitem cm4" :class="chipLess(100)" @click="setAmountFun(100)" t="100"></li>
        <li class="c_money cmitem cm5" :class="chipLess(200)" @click="setAmountFun(200)" t="200"></li>
        <li class="c_money cmitem cm6" :class="chipLess(500)" @click="setAmountFun(500)" t="500"></li>
      </ul>
    </div>

    <div class="betlisttable" v-show="betTable && noteLists.length>0">
      <div class="popwin">
      <table class="jqm_xd_table" cellpadding="0" cellspacing="0" border="0" align="center">
        <tr>
          <th colspan="2">下注内容</th>
          <th>赔率</th>
          <th>金额</th>
        </tr>
        <tr>
          <td colspan="2">共{{noteLists.length}}</td>
          <td></td>
          <td>{{totalBetAmt | moneyFmt}}</td>
        </tr>
      </table>
        <div class="detailist">
      <table class="jqm_xd_table" cellpadding="0" cellspacing="0" border="0" align="center">
        <template v-for="(item,index) in noteLists">
          <tr>
            <template v-if="gameId==101 || gameId==102 || gameId==103 || gameId==104 ||
                          gameId==105 || gameId==106 || gameId==107 || gameId==108 || gameId==109 || gameId==110 || gameId==111 || gameId==112">
              <td>{{$t(item.playKey)}}
                <span v-if="/^[0-9]\d*$/.test(item.oddsKey)">{{item.oddsKey}}</span>
                <span v-if="!/^[0-9]\d*$/.test(item.oddsKey)">{{$t(item.oddsKey.toUpperCase())}}</span>
              </td>
            </template>
            <template v-if="gameId==201 || gameId==202 || gameId==203 || gameId==204 ||
                            gameId==205 || gameId==206 || gameId==207 ||gameId==208 || gameId==209 || gameId==210 || gameId==211">
              <td v-if="item.betContent">{{$t(item.playKey)}}
                <span >
                  <span v-if="/^[0-9]\d*$/.test(item.oddsKey)">{{item.oddsKey}}</span>
                  <span v-if="!/^[0-9]\d*$/.test(item.oddsKey)">{{$t(item.oddsKey.toUpperCase())}}</span>
                  <span>{{item.betContent}}</span>
                </span>
              </td>
              <td v-if="!item.betContent">
                <span v-if="item.playKey=='t_lhh' && item.classKey=='lm'">{{$t('thesum')}}</span>
                <span v-else-if="item.playKey=='t_lhh' && item.classKey=='lhh'">{{$t('dtt')}}</span>
                <span v-else>{{$t(item.playKey)}}</span>
                <span v-if="item.playKey.indexOf('qiu')==-1 && item.playKey!='t_lhh' && item.playKey.indexOf('2z')==-1
                            && item.playKey.indexOf('3z')==-1 && item.playKey.indexOf('hs')==-1&& item.playKey.indexOf('hws')==-1
                            && item.playKey.indexOf('kd')==-1 && item.playKey!='nn' && item.playKey!='sh' && item.playKey!='top3'
                            && item.playKey!='mid3' && item.playKey!='end3'">
                  {{$t(item.categoryKey)}}</span>
                <span>
                  <span v-if="/^[0-9]\d*$/.test(item.oddsKey)">{{item.oddsKey}}</span>
                  <span v-if="!/^[0-9]\d*$/.test(item.oddsKey)">{{$t(item.oddsKey.toUpperCase())}}</span>
                </span>
                <span v-if="item.betContent">{{item.betContent}}</span>
              </td>

            </template>
            <template v-if="gameId==301 || gameId==302 || gameId==303 || gameId==304">
              <td v-if="item.betContent">{{$t(item.playKey)}}{{$t(item.oddsKey.toUpperCase())}}<span v-if="item.betContent">{{item.betContent}}</span></td>
              <td v-if="!item.betContent">
                <span v-if="item.playKey=='sum'">{{$t('THESUM')}}</span>
                <span v-else>{{$t(item.playKey)}}</span>


                <span v-if="/^[0-9]\d*$/.test(item.oddsKey)">{{item.oddsKey}}</span>
                <span v-if="!/^[0-9]\d*$/.test(item.oddsKey)">{{$t(item.oddsKey.toUpperCase())}}</span>

              </td>

            </template>
            <template v-if="gameId==401 || gameId==402 || gameId==403
                          || gameId==404 || gameId==405">
              <td>{{$t(item.playKey)}}
              <span v-if="/^[0-9]\d*$/.test(item.oddsKey)">{{item.oddsKey}}</span>
              <span v-if="!/^[0-9]\d*$/.test(item.oddsKey)">{{$t(item.oddsKey.toUpperCase())}}</span>
              </td>
            </template>
            <template v-if="gameId==801">
              <td>
              <span v-if="item.betContent">{{$t(item.playKey)}}</span>
              <span v-if="!item.betContent">
                {{$t(item.playKey)}}
                <span v-if="/^[0-9]\d*$/.test(item.oddsKey)">{{item.oddsKey}}</span>
                <span v-if="!/^[0-9]\d*$/.test(item.oddsKey)">{{$t(item.oddsKey.toUpperCase())}}</span></span>
              <span v-if="item.betContent">{{item.betContent}}</span>
              </td>
            </template>
            <template v-if="gameId==701">
              <td>
                <span v-if="item.playKey=='t_lhh'">{{$t('zhlh')}}</span>
                <span v-if="item.playKey!='t_lhh'">{{$t(item.playKey)}}</span>
                <span v-if="item.classKey==''"></span>
                <span v-if="/^[0-9]\d*$/.test(item.oddsKey)">{{item.oddsKey}}</span>
                <span v-if="!/^[0-9]\d*$/.test(item.oddsKey)">{{$t(item.oddsKey)}}</span>
              </td>
            </template>
            <template v-if="gameId==501 ||gameId==502">
              <td>
                <span >{{$t(item.playKey)}}</span>
                <span v-if="/^[0-9]\d*$/.test(item.oddsKey)">{{item.oddsKey}}</span>
                <span v-if="!/^[0-9]\d*$/.test(item.oddsKey)">{{$t(item.oddsKey)}}</span>
              </td>
            </template>
            <template v-if="gameId==601">
              <td>
                <span v-if="item.playKey!='q2zx' && item.playKey!='q3zx'
                                     && item.playKey!='q2gx' && item.playKey!='q3gx'&& item.playKey.indexOf('rx')==-1">{{$t(item.playKey)}}</span>

                <span v-if="/^[0-9]\d*$/.test(item.oddsKey)">{{item.oddsKey}}</span>
                <span v-if="!/^[0-9]\d*$/.test(item.oddsKey)">{{$t(item.oddsKey)}}</span>
                <span v-if="item.betContent">{{item.betContent}}</span>
              </td>
            </template>
            <td>{{item.odds}}</td>
            <td>{{item.betAmt | moneyFmt}}</td>
          </tr>
        </template>
      </table>
        </div>
      <table cellpadding="0" cellspacing="0" border="0" style="padding: 10px; margin: 0 auto; text-align: center;width: 85%;">
        <tr>
          <td colspan="2" style="height:44px;color:#510505">
            <input style="float:left;" type="checkbox" v-model="autoNewOdds"/>如赔率变化，按最新赔率投注，不提示赔率变化
          </td>
        </tr>
        <tr>
          <td><button @click="hideBetTable" class="jqm_btn_qing" style="width: 80px">取消</button></td>
          <td><button @click="bet(false)" v-bind:disabled="isBetLoading" class="jqm_btn_order"><template v-if="isBetLoading">下注中...</template><template v-if="!isBetLoading">确定</template></button></td>
        </tr>
      </table>
      </div>
    </div>
  </div>
</template>
<script>
  import {mapActions, mapGetters} from 'vuex'
  import {Indicator} from 'mint-ui'
  import Utils from '@/components/comm/Utils'
  import to from "await-to-js";
  export default {
    data() {
      return {
        chipShow:false,
        betTable:false,
        chip:'',
        pageAmount:'',
        betStatus:false,
        autoNewOdds:false,
        isBetLoading:false,
      }
    },
    computed:{
      ...mapGetters(['gameId','amount','noteLists','gameInfo','betGameNo','game','betState','selectList','betToken','markets','member']),
      totalBetAmt(){
        let totalAmt = 0.00;
        for(let i =0;i<this.selectList.length;i++){
          this.$set(this.selectList[i],'betAmt',this.pageAmount);
          totalAmt = Utils.NumberAdd(totalAmt,this.selectList[i].betAmt);
        }
        return totalAmt;
      },

    },
    filters:{
      moneyFmt(val){
        if(!val){
          return '0.00';
        }
        return Utils.formatMoney(val, 2);
      }
    },
    watch:{
      pageAmount(){
        let self = this;
        if (self.pageAmount) {
          this.chip = self.pageAmount;
          self.setAmount(self.pageAmount);
        }
      }
    },
    methods: {
      ...mapActions(['setPromptInformation','setAmount','setNoteLists','setSelectList','setSpecialNoteLists','setBetGameNo','setBetToken','setBalances']),
      setAmountFun(value){
        this.chip = value;
        this.pageAmount = value;
        this.setAmount(value);
        this.chipShow = false;
      },
      hideBetTable(){
        let self = this;
        if(self.betStatus){
          return;
        }
        //self.setSelectList(null);
        //self.$emit('clearSpecialSelect',true);
        self.betTable = false;
      },
      saveAmountFun(){
        //this.setAmount(this.pageAmount);
      },
      orderset() {
        this.$router.push('/sg/betlist');
      },
      betAmtInputFun(){
        this.pageAmount = '';
        this.chip = 0;
      },
      betAmtValidata(e){
      },
      //选中筹码
      chipLess(index){
        return this.chip === index?'selected':'';
      },
      clearBet() {
        let self = this;
        self.$messageBox.close();
        self.$messageBox({$type:'confirm',message:'确认清空吗？',title:'提示',closeOnClickModal:false,showCancelButton:true}).then(action=>{
          if(Object.is(action,'confirm')){
            self.selectList.forEach((item) => {
              self.$delete(item, 'choose');
              self.$delete(item, 'betAmt');
            });
            self.setSelectList(null);
            self.$emit('clearSpecialSelect',true);
            self.pageAmount = '';
            self.chip = '';
          }
        }).catch(err=>{
          if(Object.is(err,'cancel')){

          }
        }).finally(()=>{
          this.betStatus = false;
        });

      },
      details() {
        this.$router.push({path:'/idc/details',query:{lotteryId:this.game.lotteryId}});
      },
      whetherBet(){
        let self = this;
        if(self.pageAmount==''){
          self.setPromptInformation('请选择筹码或者手动输入筹码！');
          return;
        }
        this.$emit('calculationOdds');
        let whetherTo = false;
        for(let i = 0;i<self.noteLists.length;i++){
          self.noteLists[i].betAmt = self.pageAmount;
          for(let j = 0;j<self.noteLists.length - i -1;j++){
            if(self.noteLists[j].oddsId > self.noteLists[j+1].oddsId){
              let obj = Object.assign({},self.noteLists[j]);
              self.noteLists[j] = self.noteLists[j+1];
              self.noteLists[j+1] = obj;
            }
          }
        }
        if(self.selectList.length>0 && !whetherTo){
          this.betTable=true;
        }else{
          this.betTable=false;
          self.setPromptInformation("请选中后再下注！");
        }

      },
      async bet(newOdds){
        let self = this;
        Indicator.open({text:'下注中...'});
        self.$messageBox.close();
        this.isBetLoading = true;
        /*if (self.gameInfo.gameNo !== self.betGameNo) {
          self.setBetGameNo(self.gameInfo.gameNo);
          return this.betError('当前下注期号已经开奖，重新点击投注即可下最新一期！',false);
        }*/
        if (self.betStatus)  return this.betError('下注正在进行中！',true);

        if(!self.betState){
          return self.betError('已封盘，不能下注',true);
        }
        self.betStatus = true;
        let isCanBet = true;
        let autoNewOdds = 0;
        if(newOdds || self.autoNewOdds){
          autoNewOdds = 1;
        }
        let betRq = {
          lotteryId:self.gameId,
          gameNo:self.gameInfo.gameNo,
          betOdds:new Array(0),
          market:self.markets,
          autoNewOdds:autoNewOdds,
          betSource:'normal',
        };
        let msgDatas = '';
        for(let item of self.noteLists){
          if(item.status!='0'){
            msgDatas+=self.$t(item.classKey) + self.$t(item.playKey) + self.$t(item.oddsKey.toUpperCase()) +'赔率已关闭。不能下注';
            isCanBet = false;
          }
          item.betAmt = self.pageAmount;
          let lar = /^[1-9]\d*$/.test(item.betAmt);
          if(!lar){
            item.betAmt = self.pageAmount;

          }
          if(item.check && /^[1-9]\d*$/.test(item.betAmt)){
            let obj = {oddsId:item.oddsId,odds:item.odds,betAmt:item.betAmt,betContent:item.betContent};
            betRq.betOdds.push(JSON.parse(JSON.stringify(obj)));
          }
        }
        if(!isCanBet){
          self.betStatus = false;

          return self.betError(msgDatas,false);
        }
        if(betRq.betOdds.length===0){
          self.betStatus = false;
          return self.betError('请至少选中一条进行下注！',false);
        }
        let [err,data] = await to(this.$api.bet.bet(betRq));
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
                self.betTable=false;
                self.isBetLoading=false;
              }
            })
          }


        }
        if(data.success){
          self.setPromptInformation('下单成功!');
          let [err1,data1] = await to(this.$api.mem.balanceInfo());
          if(err1 || !data1.success){
            Indicator.close();
            self.setPromptInformation(data1.message);
          }

          if(data1.success){
            self.setBalances(data1.data);
          }
          Indicator.close();
          self.setNoteLists(null);
          self.setSelectList(null);
          console.log('下注成功!');
          self.$emit('clearSpecialSelect',true);
        }
        self.betTable=false;
        self.betStatus = false;
        self.isBetLoading=false;
        Indicator.close();
      },
      betError(msg,reset) {
        this.isBetLoading=false;
        Indicator.close();
        this.setPromptInformation(msg).then(()=>{
          this.betStatus = false;
          if(reset){
            //this.clearBet();
          }

        });
        this.betTable=false;

      },
    },
    mounted(){
      this.isBetLoading=false;
      /*this.chip = this.amount;
      this.pageAmount = this.amount;*/
    }
  }
</script>

<style scoped>
  .betlisttable{
    background-color: rgba(0,0,0,0.7);
    visibility: visible;
    position: fixed;
    top: 0;
    left: 0;
    bottom: 0;
    right: 0;
    z-index: 1999;
    display: flex;
    color: #fff;
  }
  .jqm_xd_table {
    color: #515a6e;
    width: 100%;
  }
  .popwin{
    left: calc((100% - 300px)/2) !important;;
    bottom: 10%;
    position: fixed;
    padding: 2px;
    width: 300px;
    margin: 0px auto;
    display: table;
    background-color: rgb(255, 255, 255);
    border-radius: 8px;

  }
  .detailist{
    width: 100%;
    max-height: 300px;
    overflow: auto;
  }
  .jqm_xd_table tr td {
    text-align: center;
    text-overflow: ellipsis;
    vertical-align: middle;
    border-bottom: 1px solid #e8eaec;
    border-right: 1px solid #e8eaec;
    text-align: center;
    height: 30px;
    line-height: 18px;
    font-size: 13px;
  }

  .jqm_xd_table tr th {
    text-align: center;
    text-overflow: ellipsis;
    vertical-align: middle;
    border-bottom: 1px solid #e8eaec;
    border-right: 1px solid #e8eaec;
    text-align: center;
    font-size: 14px !important;
    color: #4A1A04;
    font-weight: bold;
    height: 38px;
    line-height: 38px;
    background-color: #f8f8f9;
  }
  #footer-input {
    padding-bottom: constant(safe-area-inset-bottom) !important;
    padding-bottom: env(safe-area-inset-bottom) !important;
    position: fixed;
    height: 40px;
    left: 0;
    right: 0;
    bottom: 0;
    z-index: 2;
    background: #3F4953;
    font-size: 14px !important;
  }
  #footer-input .quick-qingkong, #footer-input .chongma, #footer-input .mingxi {
    text-align: center;
    background-color: transparent;
    padding-right: 5px;
    border: 0;
  }
  #footer-input .quick-money {
    background: #fff;
    border: 1px solid #fff;
    height: 28px !important;
    margin-top: 5px;
    padding: 0px;
    outline: none;
    box-shadow: none;
    color: #333;
    border-radius: 3px;
  }
  #footer-input .quick-bet {
    background-color: transparent;
    padding-left: 5px;
    padding-right: 5px;
    border: 0;
  }
  #footer-input .rcm {
    width: 100%;
    height: 64px;
    position: absolute;
    top: -65px;
    left: 0;
    background: #fff;
    list-style: none;
  }
  #footer-input .cmitem {
    margin: 13px 1.9%;
    width: 16.66%;
    height: 100%;
    float: left;
    max-width: 48px;
    background: url('../../images/money.png') no-repeat center;
  }
  #footer-input .cmitem.cm1 {
    background-position: 0 0;
  }
  #footer-input .cmitem.cm2 {
    background-position: -48px 0;
  }
  #footer-input .cmitem.cm3 {
    background-position: -96px 0;
  }
  #footer-input .cmitem.cm4 {
    background-position: -146px 0;
  }
  #footer-input .cmitem.cm5 {
    background-position: -193px 0;
  }
  #footer-input .cmitem.cm6 {
    background-position: -240px 0;
  }
  .input-group {
    position: relative;
    display: table;
    border-collapse: separate;
  }
  .input-group .form-control, .input-group-addon {
    display: table-cell;
  }

  .input-group-addon {
    width: 1%;
    white-space: nowrap;
    vertical-align: middle;
  }
  .input-group .form-control {
    position: relative;
    z-index: 2;
    float: left;
    width: 100%;
    margin-bottom: 0;
  }

  .jqm_btn_cm_b {
    display: table;
    margin: 0 auto;
    border: 0 none;
    color: #fff;
    line-height: 30px;
    cursor: pointer;
    height: 30px;
    margin-left: 5px;
    vertical-align: middle;
    width: 50px;
    font-size: 16px;
    background: -moz-linear-gradient(top,#008000 0%,#008000 30%,#008000 60%,#008000 100%);
    background: -webkit-gradient(linear,left top,left bottom,color-stop(0%,#008000),color-stop(30%,#008000),color-stop(60%,#008000),color-stop(100%,#008000));
    background: -webkit-linear-gradient(top,#008000 0%,#008000 30%,#008000 60%,#008000 100%);
    background: -o-linear-gradient(top,#008000 0%,#008000 30%,#008000 60%,#008000 100%);
    background: -ms-linear-gradient(top,#008000 0%,#008000 30%,#008000 60%,#008000 100%);
    background: linear-gradient(to bottom,#008000 0%,#008000 30%,#008000 60%,#008000 100%);
    border-radius: .2em;
    -moz-border-radius: .2em;
    -o-border-radius: .2em;
    -ms-border-radius: .2em;
    -webkit-border-radius: .2em;
  }
  .jqm_btn_order {
    line-height: 30px;
    display: table;
    text-align: center !important;
    border: 0 none;
    color: #fff;
    cursor: pointer;
    height: 30px;
    width: 80px;
    font-size: 16px;
    background: -moz-linear-gradient(top,#e1222a 0%,#de1f27 30%,#db1c24 60%,#d81921 100%);
    background: -webkit-gradient(linear,left top,left bottom,color-stop(0%,#e1222a),color-stop(30%,#de1f27),color-stop(60%,#db1c24),color-stop(100%,#d81921));
    background: -webkit-linear-gradient(top,#e1222a 0%,#de1f27 30%,#db1c24 60%,#d81921 100%);
    background: -o-linear-gradient(top,#e1222a 0%,#de1f27 30%,#db1c24 60%,#d81921 100%);
    background: -ms-linear-gradient(top,#e1222a 0%,#de1f27 30%,#db1c24 60%,#d81921 100%);
    background: linear-gradient(to bottom,#e1222a 0%,#de1f27 30%,#db1c24 60%,#d81921 100%);
    border-radius: .2em;
    -moz-border-radius: .2em;
    -o-border-radius: .2em;
    -ms-border-radius: .2em;
    -webkit-border-radius: .2em;
  }
  .jqm_btn_qing, .jqm_btn_mc_b {
    line-height: 30px;
    text-align: center !important;
    border: 0 none;
    color: #fff;
    cursor: pointer;
    height: 30px;
    width: 50px;
    font-size: 16px;
    background: -moz-linear-gradient(top,#4773c0 0%,#4773c0 30%,#4773c0 60%,#4773c0 100%);
    background: -webkit-gradient(linear,left top,left bottom,color-stop(0%,#4773c0),color-stop(30%,#4773c0),color-stop(60%,#4773c0),color-stop(100%,#4773c0));
    background: -webkit-linear-gradient(top,#4773c0 0%,#4773c0 30%,#4773c0 60%,#4773c0 100%);
    background: -o-linear-gradient(top,#4773c0 0%,#4773c0 30%,#4773c0 60%,#4773c0 100%);
    background: -ms-linear-gradient(top,#4773c0 0%,#4773c0 30%,#4773c0 60%,#4773c0 100%);
    background: linear-gradient(to bottom,#4773c0 0%,#4773c0 30%,#4773c0 60%,#4773c0 100%);
    border-radius: .2em;
    -moz-border-radius: .2em;
    -o-border-radius: .2em;
    -ms-border-radius: .2em;
    -webkit-border-radius: .2em;
  }
  .selected{
    position: relative;
    top:-10px;

  }

  @media screen and (max-width: 360px) {
    #footer-input .cmitem {
      margin: 0 .81%;
    }
  }



</style>
