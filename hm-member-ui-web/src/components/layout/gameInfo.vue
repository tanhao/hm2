<template>
  <div id="lotteryheader">
    <div class="lottery_info">
      <div class="lottery_info_left floatleft">
        <span class="name" id="lotteryName">{{$t(gameInfo.lotteryId)}}</span> —
        <span class="gameName" id="gameName">{{$t(categorySelect)}}</span>
        <span class="result">&nbsp;今日输赢：<span id="bresult">{{winLose | moneyFmt}}</span>
        </span>
      </div>
      <div class="lottery_info_right floatright">
        <span id="drawNumber">{{gameInfo.gameNo}}</span>期<span class="cdContainer" style="display: inline;">&nbsp;&nbsp;距离封盘：<span
        class="color_lv bold" style="display: inline;"><span id="cdClose">{{closeTime.m}}:{{closeTime.s1}}{{closeTime.s2}}</span></span></span>
        <span class="cdContainer" style="display: none;">&nbsp;&nbsp;距开盘：<span class="color_lv bold" style="display: none;">
          <span id="cdOpen">00:00</span>
        </span>
        </span>
        &nbsp;&nbsp;距离开奖：<span class="color_lv bold">
        <span id="cdDraw">{{openTime.m}}:{{openTime.s1}}{{openTime.s2}}</span>
      </span>
        <!--<span id="cdRefresh" style="float:right;width: 50px;">{{refreshSum}}秒</span>-->
      </div>
      <div class="clearfloat"></div>
    </div>

  </div>
</template>

<script>
  import {mapGetters,mapActions} from 'vuex'
  import Utils from '@/components/comm/Utils.js'
  import to from "await-to-js";
  export default {
      name: "gameInfo",
    data(){
        return {
          openTimer:null,
          closeTimer:null,
          oddsTimer:null,
          oddsRereshTime:null,
          oddsJumpTimer:null,
          openTime:{
            m:0,
            s1:0,
            s2:0
          },
          closeTime:{
            m:0,
            s1:0,
            s2:0
          },
          refreshSum:0,
          refreshInfoTimer:null,
        }
    },
    filters:{
      numFilter(val){
        if(val.length===2 || val.length===3){
          return val.replace('n','');
        }else{
          return '';
        }
      },
      moneyFmt(val){
        if(!val){
          return '0.00';
        }
        return Utils.formatMoney(val, 2);
      }
    },
    beforeDestroy(){
      if(this.openTimer){
        clearInterval(this.openTimer);
      }
      if(this.closeTimer){
        clearInterval(this.closeTimer);
      }
      if(this.refreshInfoTimer){
        clearTimeout(this.refreshInfoTimer);
      }
      if(this.oddsTimer){
        clearInterval(this.oddsTimer);
        this.oddsTimer = null;
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
    computed:{
      ...mapGetters(['game','gameInfo','winLose','gameInfoRefreshNum','betState','gameId','member','markets','selectList','pk10Odds','categorySelect']),
    },
    methods:{
      ...mapActions(['setWhetherSwitch','setUserOddsNows','setUserOddsJumps','setPromptInformation','setLotteryNow','setKjlistK3', 'changeBetState', 'setCurrentGame','setSelectList', 'setNoteLists', 'setBalances']),
      oddsJumpRefresh(){
        let self = this;
        this.oddsJumpTimer = setInterval(()=>{
          let params = {
            lotteryId: self.gameId,
            userId: self.member.userId,
          };
          self.$api.Lottery.getNowStats(params).then(jumpData=>{
            self.setUserOddsJumps(jumpData.data.userOddsJumps)
          })
        },3000);
      },
      openTimerStart() {
        let self = this;
        if (!self.openTimer) {
          let open_time = parseInt(self.gameInfo.openTime - self.gameInfo.serverTime / 1000);
          if (open_time / 60 > 900) {
            return;
          }
          self.openTimer = setInterval(() => {
            if (open_time >= 1) {
              open_time = parseInt(self.gameInfo.openTime - self.gameInfo.serverTime / 1000);

              self.setTime(open_time, 'open');

            } else {
              self.setTime(0, 'open');
              clearInterval(self.openTimer);
              self.openTimer = null;
              if (!self.refreshInfoTimer && null != self.game && null != self.game.lotteryId && typeof self.game.lotteryId != undefined) {
                self.refreshInfoTimer = setTimeout(function () {
                  if(self && !self._isDestroyed){
                    self.setWhetherSwitch(false);
                    self.setUserOddsJumps(null);
                    let params = {
                      lotteryId: self.gameId,
                      userId: self.member.userId,
                    };
                    self.$api.Lottery.getNowLottery(params).then(nowData=>{
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
          self.closeTimer = setInterval(() => {
            if (_time >= 1) {
              if(self.gameInfo.serverTime && (((self.gameId== 101 || self.gameId== 201 || self.gameId== 301 ||
                self.gameId== 101 ||self.gameId== 303 || self.gameId == 204) && self.gameInfo.closeTime - self.gameInfo.serverTime/1000 - self.gameInfo.precedeClose>1200) || ((self.gameId == 102 ||
                self.gameId == 104 || self.gameId == 203 || self.gameId == 304 || self.gameId == 106  || self.gameId == 205 || self.gameId == 402) && self.gameInfo.closeTime - self.gameInfo.serverTime/1000 - self.gameInfo.precedeClose>300)
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
              self.setSelectList(null);
              self.$emit('closeMarketFun');
              self.gameInfo.prevGameNo = parseInt(self.gameInfo.gameNo);
              self.gameInfo.prevResult = [];
            }
          }, 1000);

        }
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
      closeAllTime(){
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

      async infoObtain() {
        let self = this;
        self.closeAllTime();
        let [err,data] = await to(this.$api.Lottery.getLotteryNow(self.gameId));
        if(err || !data.success){

        } else{
          self.setLotteryNow(data);
          if(self.gameInfo.status){

            self.openTimerStart();
            self.closeTimerStart();
            //self.oddsJumpRefresh();
            if(self.gameInfo.isOpenTime < new Date().getTime()/1000){
              self.changeBetState(true);
            }
          }else{
            self.openTime={
              m:0,
              s1:0,
              s2:0
            };
            self.closeTime={
              m:0,
              s1:0,
              s2:0
            };
          }
        }
        let timer;
        clearTimeout(timer);
        timer =setTimeout(() => {
          (async() => {
            let [err1,data1] = await to(this.$api.Member.balanceInfo(self.member.userId));
            if(err1 || !data1.success){

            }else{
              self.setBalances(data1.data);
            }
          })();
        }, 1000);

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
    mounted(){
        let self = this;



    }
  }
</script>

<style scoped>

</style>
