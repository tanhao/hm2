<template>
    <router-view ref="mainPage"/>
</template>

<script>
  import im from '@/components/comm/im'
  import {mapGetters, mapActions} from 'vuex'
  import {Indicator} from 'mint-ui'
  import to from 'await-to-js'
  import Lottery from '@/axios/api-game.js'
  export default {
    data(){
      return {
        refreshTime:null,
        socketRefreshTime:null
      }
    },
    computed: {
      ...mapGetters(['game','selectList','userOddsCljps','gameId','userOddsCloses','userOddsJumps','userOddsNows','userOddss','gameInfo','member']),
    },
    beforeDestroy(){
      if(this.refreshTime){
        clearInterval(this.refreshTime);
        this.refreshTime = null;
      }
      clearInterval(this.socketRefreshTime);
      this.socketRefreshTime = null;
    },
    destroyed() {
      clearInterval(this.refreshTime);
      this.refreshTime = null;
      clearInterval(this.socketRefreshTime);
      this.socketRefreshTime = null;
    },
    methods: {
      ...mapActions(['setSocket','setSocketResetStatus','setResultStatus','selectGame','setBalances','setLotteryNow','setCurrentGame','setUserOddss','setUserOddsCljps','setUserOddsCloses','setUserOddsJumps','setUserOddsNows']),
      initSocket(){
        let self = this;
        let member = this.member;
        let socket = im.initWs(member.uid,this.handlerMsg());

        if(socket){
          self.setSocket(socket);
        }
        /*setTimeout(()=>{
          console.log('看看',socket);

        },1000)*/
        //this.setSocket(socket);

      },
      handlerMsg () {
        let self = this;
        return new function(){
          this.onopen = function (event, ws) {

            // 握手连接
            let router = self.$router;
            let lotteryPage = false;
            if(((router.currentRoute.path=='/sg/bjpk10' || router.currentRoute.path=='/idc/bjpk10') || (router.currentRoute.path=='/sg/xyft' || router.currentRoute.path=='/idc/xyft')
              ||(router.currentRoute.path=='/sg/speed10' || router.currentRoute.path=='/idc/speed10') || (router.currentRoute.path=='/sg/lucky10' || router.currentRoute.path=='/idc/lucky10')
              ||(router.currentRoute.path=='/sg/hmpk10' || router.currentRoute.path=='/idc/hmpk10') ||(router.currentRoute.path=='/sg/xjssc' || router.currentRoute.path=='/idc/xjssc')
              ||(router.currentRoute.path=='/sg/hmcqssc' || router.currentRoute.path=='/idc/hmcqssc')
              ||  (router.currentRoute.path=='/sg/sbspeed10' || router.currentRoute.path=='/idc/sbspeed10') || (router.currentRoute.path=='/sg/cqssc' || router.currentRoute.path=='/idc/cqssc')
              || (router.currentRoute.path=='/sg/jsssc' || router.currentRoute.path=='/idc/jsssc') || (router.currentRoute.path=='/sg/lucky5' || router.currentRoute.path=='/idc/lucky5')
              || (router.currentRoute.path=='/sg/gdkl10' || router.currentRoute.path=='/idc/gdkl10') || (router.currentRoute.path=='/sg/speed8' || router.currentRoute.path=='/idc/speed8')
              || (router.currentRoute.path=='/sg/xync' || router.currentRoute.path=='/idc/xync') || (router.currentRoute.path=='/sg/lucky8' || router.currentRoute.path=='/idc/lucky8')
              || (router.currentRoute.path=='/sg/jnd28' || router.currentRoute.path=='/idc/jnd28')|| (router.currentRoute.path=='/sg/pcdd' || router.currentRoute.path=='/idc/pcdd')
              || (router.currentRoute.path=='/sg/jsdd' || router.currentRoute.path=='/idc/jsdd'))){
              lotteryPage = true;

            }

            if(self.gameId && lotteryPage){
              self.selectGame(self.gameId);
              self.setSocketResetStatus(true);
              /*Lottery.getLotteryNow(self.gameId).then(resData=>{
                if(resData.success){
                  self.setLotteryNow(resData);
                }
              })*/

            }



          }
          this.onmessage = function (event, ws) {
            if(!self.member) return;
            var data=eval('(' + event.data + ')');
            //心跳数据不需要处理
            if(data.code==='ping')  return;
            if(data.code==='result'){
              let lotteryInfo = data.content;
              if(lotteryInfo.lotteryKey == self.game.lotteryKey && lotteryInfo.gameNo >= self.gameInfo.prevGameNo){
                self.setLotteryNow(lotteryInfo);
                self.setResultStatus(true);

              }
              setTimeout(function(){
                self.refreshBalanceFun();
              },1000)
            }else if(data.code==='odds_init'){
              self.selectList.forEach(obj=>{
                data.content.oddss.find(resultOddsI=>{
                  if(obj.oddsId==resultOddsI.oddsId){
                    self.$set(resultOddsI,'choose',obj.choose);

                  }
                })
              });
              self.setCurrentGame(data.content);
            }else if(data.code==='odds_now'){
              let {userLevel,oddss} = data.content;
              oddss.forEach((item)=>{
                let {oddsId,odds} = item;
                if(!self.userOddsNows[oddsId]){
                  self.$set(self.userOddsNows,''+oddsId,new Array(userLevel).fill(0));
                }
                self.$set(self.userOddsNows[oddsId],userLevel,odds);
              })
              self.setUserOddsNows(self.userOddsNows);
            }else if(data.code==='odds'){
              let {userLevel,oddss} = data.content;
              oddss.forEach((item)=>{
                let {categoryId,odds} = item;
                if(!self.userOddss[categoryId]){
                  self.$set(self.userOddss,''+categoryId,new Array(userLevel).fill(0));
                }
                self.$set(self.userOddss[categoryId],userLevel,odds);
              })
              self.setUserOddss(self.userOddss);
            }else if(data.code ==='odds_cljp'){
              let {userLevel,oddss} = data.content;
              oddss.forEach((item)=>{
                let {oddsId,odds:newVal} = item;
                let arr = new Array(userLevel).fill(0);
                arr[userLevel] = newVal;
                self.$set(self.userOddsCljps,oddsId,arr);
              })
              self.setUserOddsCljps(self.userOddsCljps);
            }else if(data.code ==='odds_jump'){
              let {userLevel,oddss} = data.content;
              oddss.forEach((item)=>{
                let {oddsId,odds} = item;
                if(!self.userOddsJumps[oddsId]){
                  self.$set(self.userOddsJumps,''+oddsId,new Array(userLevel).fill(0));
                }
                self.$set(self.userOddsJumps[oddsId],userLevel,odds);
              })
              self.setUserOddsJumps(self.userOddsJumps);
            }


          }

          this.onclose = function (e, ws) {
            // error(e, ws)
          }

          this.onerror = function (e, ws) {
            // error(e, ws)
          }
        }

      },
      async refreshBalanceFun(){
        let [err1,data1] = await to(this.$api.mem.balanceInfo(this.member.userId));
        if(err1 || !data1.success){
          return;
        }
        if(data1.success){
          this.setBalances(data1.data);
        }

      }
    },
    mounted() {
      let self = this;


      let router = self.$router;
      if(((router.currentRoute.path!='/sg/password') && (router.currentRoute.path!='/idc/password') )){
        /*setTimeout(()=>{
          self.initSocket();
        },1000);*/
        self.refreshTime = setInterval(()=>{
          self.refreshBalanceFun();
        },3000);
        self.initSocket();
      }
    },
  }
</script>

<style scoped>

</style>
