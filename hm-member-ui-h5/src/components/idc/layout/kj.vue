<template>
  <div>
    <!--在下面的这个DIV增加点事件,控制hislist为display: block;-->
    <div class="irDTzN" :style="game.lotteryKey==='bjfast8' || game.lotteryKey==='lucky20'?'height:56px;':''" @click="fiveRecentResultPlay">
      <div class="draw"><span id="numbers">{{gameInfo.prevGameNo}} &nbsp;</span>期
      </div>
      <div class="innVmp" id="open_num" style="margin-left:17px;">
        <div style="margin: 0 auto;  display: inherit;" class="pk10" v-if="gameInfo.lotteryId == 101 || gameInfo.lotteryId == 102 || gameInfo.lotteryId == 103
            || gameInfo.lotteryId == 104 || gameInfo.lotteryId == 105 || gameInfo.lotteryId == 106">
          <!--pk10 , ssc ，six ,xy28, k3-->
          <!--赛车类通用开奖 n1_1到n_10-->
          <template v-if="game.lotteryId==gameInfo.lotteryId" v-for="(item,index) in gameInfo.prevResult">

            <div :class="'n_'+item">
              <span>
                {{item}}</span></div>
          </template>

        </div>
        <div style="margin: 0 auto;  display: inherit;" class="ssc" v-if="gameInfo.lotteryId == 201 || gameInfo.lotteryId == 202 || gameInfo.lotteryId == 203
|| gameInfo.lotteryId == 204 || gameInfo.lotteryId == 205">
          <!--赛车类通用开奖 n1_1到n_10-->
          <template v-if="game.lotteryId==gameInfo.lotteryId" v-for="(item,index) in gameInfo.prevResult">

            <div :class="'n_'+item">
              <span>
                {{item}}</span></div>

          </template>

        </div>
        <div style="margin: 0 auto;  display: inherit;" :class="gameInfo.lotteryId==303?'xync':'klsf'"
             v-if="gameInfo.lotteryId == 301 || gameInfo.lotteryId == 302 ||gameInfo.lotteryId == 303
            || gameInfo.lotteryId == 304">
          <!--pk10 , ssc ，six ,xy28, k3-->
          <template v-if="game.lotteryId==gameInfo.lotteryId" v-for="(item,index) in gameInfo.prevResult">

            <div :class="'n_'+item">
              <span>
                {{item}}</span></div>
          </template>

        </div>
        <div style="margin: 0 auto;  display: inherit;" class="jsdd" v-if="gameInfo.lotteryId == 401 || gameInfo.lotteryId == 402 ||gameInfo.lotteryId == 403">
          <!--pk10 , ssc ，six ,xy28, k3-->
          <!--赛车类通用开奖 n1_1到n_10-->
          <template v-if="game.lotteryId==gameInfo.lotteryId" v-for="(item,index) in gameInfo.prevResult">

            <div :class="'n_'+item">
              <span>
                {{item}}</span></div>
            <span v-if="index<2" class="addico">+</span>
            <template v-else-if="index==2">
              <span class="addico">=</span>
              <div :class="'n_'+pcddResultAdd">
                <span>
                  {{pcddResultAdd}}</span></div>
            </template>
          </template>

        </div>
      </div>
    </div>

    <!--idc开奖最近5期-->
    <div class="hislist" v-show="fiveRecentResultShow">
      <template v-if="(game.lotteryKey==='bjpk10'||game.lotteryKey==='xyft' ||
          game.lotteryKey==='lucky10' || game.lotteryKey==='speed10'|| game.lotteryKey==='xyft3' ||
          game.lotteryKey==='sbspeed10' || game.lotteryKey==='hmpk10' || game.lotteryKey==='epssm' ||
          game.lotteryKey==='fhsc' || game.lotteryKey==='tycsm' || game.lotteryKey==='sgft' || game.lotteryKey==='xyft1396') && index<5" v-for="(list,index) in fiveRecentResultList">
        <div @click="fiveRecentResultShow=false;" class="irDTzN">
          <div class="draw"><span id="numbers">{{list.gameNo}} &nbsp;</span>期
          </div>
          <div class="innVmp" id="open_num" style="margin-left:17px;">
            <div class="pk10" style="margin: 0px auto; display: inherit;">
              <template  v-for="(item,n) in list.result">
                <div :class="'n_'+item" style="margin: 0px 1px !important;">
              <span>
                {{item}}
              </span>
                </div>
              </template>
            </div> <!----> <!----> <!----> <!----> <!----> <!----> <!---->
          </div>
        </div>
      </template>
      <template v-if="(game.lotteryKey==='cqssc'||game.lotteryKey==='lucky5' ||game.lotteryKey==='jsssc'
      ||game.lotteryKey==='xjssc'||game.lotteryKey==='cjk5' ||game.lotteryKey==='fhssc'
      ||game.lotteryKey==='tycssc'||game.lotteryKey==='hmcqssc' ||game.lotteryKey==='txffc' || game.lotteryKey==='txsfc'
      || game.lotteryKey==='twssc') && index<5 " v-for="(list,index) in fiveRecentResultList">
        <div @click="fiveRecentResultShow=false;" class="irDTzN">
          <div class="draw"><span id="numbers">{{list.gameNo}} &nbsp;</span>期
          </div>
          <div class="innVmp" id="open_num" style="margin-left:17px;">
            <div class="ssc" style="margin: 0px auto; display: inherit;">
              <template v-for="(item,n) in list.result">
                <div :class="'n_'+item" style="margin: 0px 1px !important;">
              <span>
                {{item}}
              </span>
                </div>
              </template>
            </div> <!----> <!----> <!----> <!----> <!----> <!----> <!---->
          </div>
        </div>
      </template>
      <template v-if="(game.lotteryKey === 'jsk3' || game.lotteryKey === 'bjk3' || game.lotteryKey === 'jlk3' || game.lotteryKey === 'gxk3' || game.lotteryKey==='speed3') && index<5" v-for="(list,index) in fiveRecentResultList">
        <div @click="fiveRecentResultShow=false;" class="irDTzN">
          <div class="draw"><span id="numbers">{{list.gameNo}} &nbsp;</span>期
          </div>
          <div class="innVmp" id="open_num" style="margin-left:17px;">
            <div class="k3" style="margin: 0px auto; display: inherit;">
              <template v-for="(item,n) in list.result">

                <div :class="'n_'+item" style="margin: 0px 1px !important;">
                </div>
                <span v-if="n<2" class="addico">+</span>
                <template v-else-if="n==2">
                  <span class="addico">=</span>
                  <div>
                <span class="addjg">
                  {{fiveksResultAdd(list.result)}}
                </span>
                  </div>
                </template>


              </template>
            </div> <!----> <!----> <!----> <!----> <!----> <!----> <!---->
          </div>
        </div>
      </template>

      <template v-if="(game.lotteryKey==='pcdd' || game.lotteryKey==='jnd28' || game.lotteryKey==='jsdd') && index<4" v-for="(list,index) in fiveRecentResultList">
        <div class="irDTzN">
          <div class="draw"><span id="numbers">{{list.gameNo}} &nbsp;</span>期
          </div>
          <div class="innVmp" id="open_num" style="margin-left:17px;">
            <div class="jsdd" style="margin: 0px auto; display: inherit;">
              <template v-for="(item,n) in list.result">
                <div :class="'n_'+item" style="margin: 0 1px !important;">
              <span>
                {{item}}
              </span>
                </div>
                <span v-if="n<2" class="addico">+</span>
                <template v-else-if="n==2">
                  <span class="addico">=</span>
                  <div :class="'n_'+fivePcddResultAdd(list.result)">
                <span>
                  {{fivePcddResultAdd(list.result)}}
                </span>
                  </div>
                </template>
              </template>
            </div> <!----> <!----> <!----> <!----> <!----> <!----> <!---->
          </div>
        </div>
      </template>

      <template v-if="(game.lotteryKey==='gdkl10' || game.lotteryKey==='speed8' || game.lotteryKey==='xync' || game.lotteryKey==='lucky8') && index<5" v-for="(list,index) in fiveRecentResultList">
        <div @click="fiveRecentResultShow=false;" class="irDTzN">
          <div class="draw"><span id="numbers">{{list.gameNo}} &nbsp;</span>期
          </div>
          <div class="innVmp" id="open_num" style="margin-left:17px;">

            <div :class="game.lotteryKey=='xync'?'xync':'klsf'" style="margin: 0px auto; display: inherit;">
              <template v-for="(item,n) in list.result">
                <div :class="'n_'+item" style="margin: 0 0px !important;">
              <span>
                {{item}}
              </span>
                </div>
              </template>
            </div> <!----> <!----> <!----> <!----> <!----> <!----> <!---->
          </div>
        </div>
      </template>




    </div>
    <!--<div :class="fiveRecentResultShow?'sc-bxivhb gzjRIk hslisttop':'sc-bxivhb'" @click="fiveRecentResultShow=false;"></div>--><!--显示侧边加gzjRIk-->

    <!--直接循环上面的样式即可-->
    <div class="TfWBC">
      <div class="draw"><span id="open_qihao">{{gameInfo.gameNo}} &nbsp;</span>期
      </div>
      <div class="timer-wrapper">
        <div class="close">
          <span>封盘:{{closeTime.m}}:{{closeTime.s1}}{{closeTime.s2}}</span>
        </div>
        <div class="open">
          <span>开奖:{{openTime.m}}:{{openTime.s1}}{{openTime.s2}}</span>
        </div>
        <div class="refresh" @click="oddsRefreshFun">
          <div></div>
          <span id="rf_time">刷新</span>
        </div>
      </div>

    </div>
    <div
      style="line-height: 36px;height: 36px;display: flex;border-top: 1px dashed #fc9b88;border-bottom: 1px solid #CD3C29;background-color: #fff;padding-left: 5px;">
      <div style="width: 90%;float: left;text-align: left">
        <div class="open" style="font-size: 13px;">
          <span style="margin-right: 15px">可用余额:{{balance | moneyFmt}}</span>
          <template v-if="parseFloat(winLose) >= 0">
            <span class="blue_color">输赢:{{winLose | moneyFmt}}</span>

          </template>
          <template v-if="parseFloat(winLose) < 0">
            <span class="red_color">输赢:{{winLose | moneyFmt}}</span>
          </template>
        </div>
      </div>
      <div @click="lmclFunction" style="display: table;margin-top: 5px;margin-right: 5px">
        <span class="nmcn">两面长龙</span>
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
        openTimer: null,
        closeTimer: null,
        oddsTimer: null,
        oddsRereshTime: null,
        oddsJumpTimer:null,
        fiveRecentResultShow:false,
        fiveRecentResultList:[],
        openTime: {
          m: 0,
          s1: 0,
          s2: 0
        },
        closeTime: {
          m: 0,
          s1: 0,
          s2: 0
        },
        refreshInfoTimer: null,
      }
    },
    created() {

    },
    beforeDestroy() {
      if (this.openTimer) {
        clearInterval(this.openTimer);
      }
      if (this.closeTimer) {
        clearInterval(this.closeTimer);
      }
      if (this.refreshInfoTimer) {
        clearTimeout(this.refreshInfoTimer);
      }
      if (this.oddsTimer) {
        clearInterval(this.oddsTimer);
      }
      clearInterval(this.oddsJumpTimer);
      this.oddsJumpTimer = null;
    },
    destroyed() {

      clearInterval(this.openTimer);
      this.openTimer = null;
      clearInterval(this.closeTimer);
      this.closeTimer = null;
      clearTimeout(this.refreshInfoTimer);
      this.refreshInfoTimer = null;
      clearInterval(this.oddsTimer);
      this.oddsTimer = null;
      clearInterval(this.oddsJumpTimer);
      this.oddsJumpTimer = null;

    },
    computed: {
      ...mapGetters(['game','socketResetStatus','resultStatus','balance','gameInfoRefreshNum','betWaiting','winLose', 'playType', 'gameInfo', 'selectList', 'betState', 'gameId', 'member', 'pk10Odds', 'sscOdds', 'kl10Odds', 'fast3Odds', 'pcddOdds', 'gxkl10Odds', 'bjfast8Odds', 'gd11x5Odds', 'markets', 'noteLists', 'categoryList']),
      pcddResultAdd() {
        if (this.game.lotteryKey === 'pcdd' ||this.game.lotteryKey === 'jnd28' ||this.game.lotteryKey === 'jsdd' ) {
          let sum = 0;
          this.gameInfo.prevResult.forEach(val => {
            sum = parseInt(val) + parseInt(sum);
          })
          return sum;
        }
      }
    },
    mounted() {
    },
    watch:{
      socketResetStatus(){
        if(this.socketResetStatus){
          this.infoObtain();
        }

      }
    },
    methods: {
      ...mapActions(['setWhetherSwitch','setSocketResetStatus','setUserOddsNows','setUserOddsJumps','setResultStatus', 'setPromptInformation', 'setSelectList', 'setPlayType', 'setLotteryNow', 'changeBetState', 'setCurrentGame', 'setLotteryNow', 'setCurrentGame', 'setNoteLists', 'setBalances']),
      oddsJumpRefresh(){
        let self = this;
        this.oddsJumpTimer = setInterval(()=>{
          let params = {
            lotteryId: self.gameId,
            userId: self.member.userId,
          };
          self.$api.game.getNowStats(params).then(jumpData=>{
            self.setUserOddsJumps(jumpData.data.userOddsJumps)
          })
        },3000);
      },
      fivePcddResultAdd(item){
        let sum = 0;
        item.forEach(val => {
          sum = parseInt(val) + parseInt(sum);
        })
        return sum;
      },
      fiveksResultAdd(item){
        let sum = 0;
        item.forEach(val => {
          sum = parseInt(val) + parseInt(sum);
        })
        return sum;
      },
      fiveRecentResultPlay(){
        let self = this;
        self.fiveRecentResultShow = self.fiveRecentResultShow?false:true;
        self.getFiveRecentResultFun();
      },
      async getFiveRecentResultFun(){
        let self = this;
        let getFiveParams =  {
          lotteryId: self.gameId,
          accountDay:Utils.formatDate(new Date(),'yyyy-MM-dd'),
          page:1,
          size:5
        };
        let date = new Date();
        let hh = date.getHours();
        if (hh < 7) {
          date.setDate(date.getDate() - 1);
          let dateParam = Utils.formatDate(date, 'yyyy-MM-dd');
          let spliceArr = dateParam.split('-');
          getFiveParams.accountDay = Utils.formatDate(new Date(spliceArr[0]+'-'+spliceArr[1]+'-'+spliceArr[2]),'yyyy-MM-dd');
        }
        let [err,data] = await to(this.$api.game.lotteryResult(getFiveParams));
        if (err || !data.success) {
          return;
        }
        if(data.data.dataList[0] && data.data.dataList[0].gameNo == self.gameInfo.prevGameNo){
          data.data.dataList = data.data.dataList.splice(1,4);
        }else{
          data.data.dataList = data.data.dataList.splice(0,4);
        }
        data.data.dataList.forEach((resultItem)=>{
          let param = [];
          if(typeof resultItem.result != 'undefined' && resultItem.result){

            if(self.gameId==101 || self.gameId==102 ||
              self.gameId ==104 || self.gameId==103 ||
              self.gameId ==105 || self.gameId==106 ||
              self.gameId ==107 || self.gameId==108 ||
              self.gameId ==109 || self.gameId ==110||
              self.gameId ==111 || self.gameId ==112){
              resultItem.result.split(',').forEach((obj) => {
                if (obj.at(0) === '0') {
                  param.push(obj.at(1));
                } else {
                  param.push(obj);
                }
              });
            }else if(self.gameId==201 || self.gameId == 203 || self.gameId==202||
              self.gameId==204 || self.gameId == 205 || self.gameId==206||
              self.gameId==207 || self.gameId == 208 || self.gameId==209||
              self.gameId==210 || self.gameId ==211){
              resultItem.result.split(',').forEach((obj)=>{
                param.push(obj);
              });


            }else if(self.gameId==301 || self.gameId==302 || self.gameId==303 || self.gameId==304){
              resultItem.result.split(',').forEach((obj) => {
                if (obj.at(0) === '0') {
                  param.push(obj.at(1));
                } else {
                  param.push(obj);
                }
              });
            }else if(self.gameId==401 || self.gameId==402 || self.gameId==403 ){
              resultItem.result.split(',').forEach((obj)=>{
                param.push(obj);
              });

            }
            resultItem.result = param;
          }
        })
        self.fiveRecentResultList = data.data.dataList;
      },
      lmclFunction() {
        if (this.game.lotteryKey === 'bjk3' || this.game.lotteryKey === 'jsk3' || this.game.lotteryKey === 'jlk3' || this.game.lotteryKey === 'gxk3' || this.game.lotteryKey === 'speed3') {
          return;
        }
        if (this.game.lotteryKey === 'bjpk10' || this.game.lotteryKey === 'xyft' ||
          this.game.lotteryKey === 'lucky10' || this.game.lotteryKey === 'speed10' ||
          this.game.lotteryKey === 'sbspeed10' || this.game.lotteryKey === 'hmpk10' ||
          this.game.lotteryKey === 'epssm' || this.game.lotteryKey === 'fhsc'
          || this.game.lotteryKey === 'tycsm' || this.game.lotteryKey === 'xyft3' ||
          this.game.lotteryKey === 'sgft' || this.game.lotteryKey === 'xyft1396' ) {
          this.$router.push('/idc/statisticspk10');
        } else if (this.game.lotteryKey === 'cqssc' || this.game.lotteryKey === 'lucky5' || this.game.lotteryKey === 'jsssc'
          || this.game.lotteryKey === 'xjssc' || this.game.lotteryKey === 'cjk5' || this.game.lotteryKey === 'fhssc'
          || this.game.lotteryKey === 'tycssc' || this.game.lotteryKey === 'hmcqssc' || this.game.lotteryKey === 'txffc'
          || this.game.lotteryKey === 'txsfc' || this.game.lotteryKey === 'twssc') {
          this.$router.push('/idc/statisticsssc');
        } else if (this.game.lotteryKey === 'gdkl10' || this.game.lotteryKey === 'speed8' || this.game.lotteryKey === 'xync' || this.game.lotteryKey === 'lucky8') {
          this.$router.push('/idc/statisticsgdkl10');
        } else if (this.game.lotteryKey === 'jnd28' || this.game.lotteryKey === 'pcdd' || this.game.lotteryKey === 'jsdd') {
          this.$router.push('/idc/statisticspcdd');
        }

      },
      closeAllTime() {
        clearInterval(this.closeTimer);
        this.closeTimer = null;
        clearInterval(this.openTimer);
        this.openTimer = null;
        clearTimeout(this.refreshInfoTimer);
        this.refreshInfoTimer = null;
        clearInterval(this.oddsTimer);
        this.oddsTimer = null;
        clearTimeout(this.oddsRereshTime);
        this.oddsRereshTime = null;
        clearInterval(this.oddsJumpTimer);
        this.oddsJumpTimer = null;
      },
      openTimerStart() {
        let self = this;
        if (!self.openTimer) {
          let open_time = parseInt(self.gameInfo.openTime - self.gameInfo.serverTime / 1000);
          if (open_time / 60 > 900) {
            return;
          }
          this.openTimer = setInterval(() => {
            if (open_time >= 1) {
              open_time = parseInt(self.gameInfo.openTime - self.gameInfo.serverTime / 1000);
              self.setTime(open_time, 'open');

            } else {
              self.setTime(0, 'open');
              clearInterval(self.openTimer);
              self.openTimer = null;
              if (!self.refreshInfoTimer && null != self.game && null != self.game.lotteryId && typeof self.game.lotteryId != undefined) {
                self.refreshInfoTimer = setTimeout(function () {
                  if (self && !self._isDestroyed) {
                    self.setUserOddsJumps(null);

                    let params = {
                      lotteryId: self.gameId,
                      userId: self.member.userId,
                    };
                    self.$api.game.getNowLottery(params).then(nowData=>{
                      if(nowData && nowData.data){
                        self.setUserOddsNows(nowData.data.userOddsNows)
                      }
                    })
                    self.infoObtain();
                  }
                }, 200);
              }

            }
          }, 1000);
        }
      },
      closeTimerStart() {
        let self = this;
        if (!self.closeTimer) {
          let _time = parseInt(self.gameInfo.closeTime - self.gameInfo.serverTime / 1000);

          if (_time / 60 > 900) {
            return;
          }
          this.closeTimer = setInterval(() => {
            if (_time >= 1) {
              if(self.gameInfo.serverTime && (((self.gameId== 101 || self.gameId== 201 || self.gameId== 301 ||
                self.gameId== 101 ||self.gameId== 303 || self.gameId == 204) && self.gameInfo.closeTime - self.gameInfo.serverTime/1000 - self.gameInfo.precedeClose>1200) || ((self.gameId == 102 ||
                self.gameId == 104 || self.gameId == 203 || self.gameId == 304 || self.gameId == 106 || self.gameId == 205 || self.gameId == 402) && self.gameInfo.closeTime - self.gameInfo.serverTime/1000 - self.gameInfo.precedeClose>300)
                ||((self.gameId == 103 ||
                  self.gameId == 105 || self.gameId == 202 || self.gameId == 302 || self.gameId == 403) && self.gameInfo.closeTime - self.gameInfo.serverTime/1000 - self.gameInfo.precedeClose>75)
              ||((self.gameId == 401) && self.gameInfo.closeTime - self.gameInfo.serverTime/1000 - self.gameInfo.precedeClose>210))){
                self.changeBetState(false);
              }else{
                self.changeBetState(true);
              }
              _time = parseInt(self.gameInfo.closeTime - self.gameInfo.serverTime / 1000);
              self.setTime(_time, 'close');
            } else {
              self.setTime(0, 'close');
              clearInterval(self.closeTimer);
              self.closeTimer = null;
              self.changeBetState(false);
              self.$emit("clearSpecialSelect");
              self.gameInfo.prevGameNo = parseInt(self.gameInfo.gameNo);
              self.gameInfo.prevResult = [];
              self.setResultStatus(false);
              if(self.fiveRecentResultShow){
                self.getFiveRecentResultFun();
              }

            }
          }, 1000);

        }
      },
      async oddsRefreshFun() {
        let self = this;
        Indicator.open({text: '加载中...'});
        let [err,data] = await to(this.$api.game.getOddsList({'lotteryId': self.gameId}));
        if(err || !data.success){
          return;
        }
        if(data.success){
          self.setWhetherSwitch(false);
          self.setCurrentGame(data.data);
        }
        Indicator.close();
        this.infoObtain();
      },
      setTime(time, type) {
        let m = this.formatTime(parseInt(time / 60));
        let s = this.formatTimeTwo(parseInt(time % 60));
        let obj = {'m': m, 's1': s.charAt(1), 's2': s.charAt(2)};
        if (type === 'open') {
          this.openTime = obj;
        } else {
          this.closeTime = obj;
        }
      },
      async infoObtain() {
        let self = this;
        self.closeAllTime();
        let [err,data] = await to(this.$api.game.getLotteryNow(self.gameId));
        if(err || !data.success){
          self.setPromptInformation('网络连接超时！');
          self.$router.push('/idc/main/');
          //this.infoObtain();
          return;
        }
        if (data.success) {
          self.setLotteryNow(data);
          if (self.gameInfo.status) {
            self.openTimerStart();
            self.closeTimerStart();
            //self.oddsJumpRefresh();
            if(self.gameInfo.isOpenTime < self.gameInfo.serverTime/1000){
              self.changeBetState(true);

            }
          }
        } else {
          self.openTime = {
            m: 0,
            s1: 0,
            s2: 0
          };
          self.closeTime = {
            m: 0,
            s1: 0,
            s2: 0
          };
        }
        let timer;
        clearTimeout(timer);
        timer =setTimeout(() => {
          (async() => {
            [err,data] = await to (this.$api.mem.balanceInfo());
            if(err || !data.success){
              self.setPromptInformation('网络连接超时！');
              self.$router.push('/idc/main/');
              return;
            }else{
              self.setBalances(data.data);
            }
          })();
        }, 1000);
        self.setSocketResetStatus(false);
        Indicator.close();

      },
      formatTime(time) {
        if (time >= 100) {
          return time + '';
        } else if (time >= 10) {
          return '' + time;
        } else {
          return '0' + time;
        }
      },
      formatTimeTwo(time) {
        if (time >= 10) {
          return '0' + time;
        } else {
          return '00' + time;
        }
      },
    },
    filters: {
      numFilter(val) {
        if (val.length === 2 || val.length === 3) {
          return val.replace('n', '');
        } else {
          return '';
        }
      },
      moneyFmt(val) {
        if (!val) {
          return '0.00';
        }
        return Utils.formatMoney(val, 2);
      }
    }
  }
</script>
<style scoped>
  .nmcn {
    float: right;
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
    line-height: 22px;
    height: 22px;
    display: table;
    -webkit-box-align: center;
    justify-content: flex-start;
    background-color: green;
    border-radius: 5px;
    color: #fff !important;
    cursor: pointer;
    font-size: 12px;
    font-weight: normal;
    padding: 2px 5px;
    text-shadow: initial;
    margin-left: 2px;
  }

</style>
