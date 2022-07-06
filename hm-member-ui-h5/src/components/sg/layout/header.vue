<template>
  <div>
  <!--首页顶部大厅-->
  <div class="home-header after-login" v-if="maintop">
    <a rel="noopener noreferrer" v-if="back"></a>
    <!-- 用户协议-->
    <div class="logo" v-if="userxy">用户协议与规则</div>
    <!--用户弹出公告-->
    <div class="logo" v-if="usergg">用户公告</div>
    <div class="logo" v-if="name">{{siteName}}</div>
    <a class="menu-toggle" v-if="!userxy && !usergg" @click="shownav()"></a>
  </div>
  <!--切换到详细游戏的头部-->
  <div class="sc-fjdhpX kTHRmj" v-if="!maintop">
    <div class="sc-hMqMXs kifISk">
      <div class="backButton" v-if="back">
        <div class="bcFIVc" v-if="!historyBack" @click="goBackFun"></div>
        <div class="bcFIVc" v-if="historyBack" @click="goHistoryFunction"></div>
      </div>
      <div class="backButton" v-if="totalReturn">
        <div class="bcFIVc" @click="goGames(game.lotteryId,game.lotteryKey)"></div>
      </div>
      <div :class="colorTypeShowOrHide?'sc-kEYyzF bOIhSN':'sc-kEYyzF fSDqXx'" @click="showgamelist()" v-if="!left"><!--彩种出现列表时要把bOIhSN 替换成 fSDqXx-->
        <span v-show="!rulesParam">{{$t(gameId)}}</span>
        <span v-show="rulesParam">{{$t(rulesTitle)}}</span>
        <span class="arrow"></span>
      </div>
    </div>
    <div class="exdTne" tabindex="1" v-if="total" @click="totalJump">
      <div class="fRfaQM">统计</div>
    </div>
    <div :class="colorTypeShowOrHide?'sc-bxivhb gzjRIk':'sc-bxivhb'" @click="showgamelist"></div><!--显示侧边加gzjRIk-->
    <div :class="colorTypeShowOrHide?'sc-eNQAEJ iFUcSP jfiSZt':'sc-eNQAEJ iFUcSP'" v-show="colorTypeShowOrHide"><!--快速显示彩种列表 jfiSZt-->
      <div class="game-div" v-show="!rulesParam">
        <h3 class="game-title">快开彩系列</h3>
        <div class="game-item-wrapper">
          <template v-for="(list,i) in gameMenu">
            <template >
              <span class="game-item" @click="goGames(list.index,list.title)">{{$t(list.title)}}</span>
            </template>
          </template>
        </div>
      </div>

      <div class="game-div" v-show="rulesParam">
        <h3 class="game-title">快开彩系列</h3>
        <div class="game-item-wrapper">
          <template v-for="(list,i) in gameMenu">
            <template >
              <span class="game-item" @click="goRules(list.title)">{{$t(list.title)}}</span>
            </template>
          </template>
        </div>
      </div>
    </div>
    <div class="title" v-if="!titles">{{title}}</div>
    <div v-if="resbnt">
    <a class="topres topresimg" @click="refreshPageFun"></a>
    </div>
    <a class="menu-toggle" v-show="!resetPasswordStatus" @click="shownav()">Menu</a>
  </div>
  </div>
</template>
<script>
  import {mapActions,mapGetters} from 'vuex'
  import {Indicator} from 'mint-ui'
  export default {
    data() {
      return {
        colorTypeShowOrHide:false,
        activeType: 'PK10JSC',
        rulesTitle:'bjpk10'
      }
    },
    props: {
      name:null,
      usergg:null,
      userxy:null,
      resbnt:null,
      maintop: null,
      titles: null,
      back: null,
      left:null,
      title:null,
      total:null,
      apiRequest:null,
      totalReturn:null,
      rulesParam:null,
      historyBack:null
    },
    computed: {
      ...mapGetters(['gameMenu','siteName','pagePosition','resetPasswordStatus','game','showMenu','member','gameId','historyReturn']),
    },
    mounted(){
      if(this.apiRequest){
      }

    },
    methods: {
      ...mapActions(['changeMenu','setWhetherSwitch','setPagePosition','selectGame','setSelectList','setNoteLists','setGames','setBalances','setPlayType']),
      shownav(){
        this.changeMenu(true);
      },
      refreshPageFun(){
        this.$emit('refreshPageFun');
      },
      showgamelist(){
        if(this.colorTypeShowOrHide){
          this.colorTypeShowOrHide = false;
        }else{
          this.colorTypeShowOrHide = true;
        }

      },

      updateRulesTitle(title){
        this.rulesTitle = title;
      },
      goBackFun(){
        if(this.resetPasswordStatus && this.title=='修改密码'){
          window.location.href = '/h5';
          return;
        }
        this.$router.push({name:this.pagePosition[this.pagePosition.length-1].name,query:this.pagePosition[this.pagePosition.length-1].query});
        this.setPagePosition();
      },
      goHistoryFunction(){
        this.$router.push({name:this.historyReturn.name,query:this.historyReturn.query});

      },
      totalJump(){
        if(this.game.lotteryKey==='bjk3' ||this.game.lotteryKey==='jsk3' ||this.game.lotteryKey==='jlk3' ||this.game.lotteryKey==='gxk3'||this.game.lotteryKey==='speed3'){
          return;
        }
        if(this.game.lotteryKey === 'bjpk10' || this.game.lotteryKey === 'xyft' ||
          this.game.lotteryKey==='lucky10' || this.game.lotteryKey==='speed10' ||
          this.game.lotteryKey==='sbspeed10' || this.game.lotteryKey==='hmpk10' ||
          this.game.lotteryKey==='epssm' || this.game.lotteryKey==='fhsc'
          || this.game.lotteryKey==='tycsm' || this.game.lotteryKey ==='xyft3' ||
          this.game.lotteryKey === 'sgft' || this.game.lotteryKey === 'xyft1396'
        ){
          this.$router.push('/sg/statisticspk10/');
        }else if(this.game.lotteryKey === 'cqssc' || this.game.lotteryKey === 'lucky5' || this.game.lotteryKey==='jsssc'
          || this.game.lotteryKey==='xjssc' || this.game.lotteryKey==='cjk5' || this.game.lotteryKey==='fhssc'
          || this.game.lotteryKey==='tycssc' || this.game.lotteryKey==='hmcqssc' || this.game.lotteryKey==='txffc'
          || this.game.lotteryKey==='txsfc' || this.game.lotteryKey==='twssc'){
          this.$router.push('/sg/statisticsssc/');
        }else if(this.game.lotteryKey==='gdkl10'|| this.game.lotteryKey==='speed8' || this.game.lotteryKey==='xync'
          || this.game.lotteryKey==='lucky8'){
          this.$router.push('/sg/statisticsgdkl10/');
        }else if(this.game.lotteryKey==='bjfast8' || this.game.lotteryKey==='lucky20'){
          this.$router.push('/sg/statisticsbjfast8/');
        }else if(this.game.lotteryKey==='gd11x5' ){
          this.$router.push('/sg/statisticsgd11x5/');
        }else if(this.game.lotteryKey==='pcdd'){
          this.$router.push('/sg/statisticspcdd/');
        }else if(this.game.lotteryKey==='gxkl10'){
          this.$router.push('/sg/statisticsgxkl10/');
        }

      },
      goRules(title){
        this.$emit('rulesSwitch',title);
        this.colorTypeShowOrHide = false;
      },
      goGames(id,title){
        let self = this;
        /*self.setPlayType(null);
        self.setSelectList(null);
        self.setNoteLists(null);*/
        let newPageIndex = id.toString().substring(0,2);
        let flag = false;
        if(self.gameId.toString().substring(0,2).indexOf(newPageIndex)!=-1){
          flag = true;
        }


        //self.selectGame(id);
        self.setWhetherSwitch(true);
        self.colorTypeShowOrHide = false;
        if(flag){
          self.$emit('clearSpecialSelect',flag);
          self.$router.push('/sg/'+title);
        }else{
          self.$router.push('/sg/'+title);
        }
      },

    },
    created(){

    },
  }
</script>
<style scoped>
  .home-header .logo {
    top: 8px;
    color: #fff;
    font-size: 20px;
    font-weight: 300;
    position: absolute;
    width: 50%;
    height: 100%;
    left: 0;
    right: 0;
    margin: auto;
    text-align: center;
  }
  .kTHRmj {
    position: relative;
    top: 0;
    left: 0;
    width: 100%;
    height: 45px;
    background: linear-gradient(135deg, rgb(19, 46, 123) 0%, rgb(0, 201, 202) 100%);
    /*background: linear-gradient(to right,#7dcaff 0%,#1a5194 100%);*/
  }
  .kTHRmj .title {
    line-height: 45px;
    text-align: center;
    font-weight: bold;
    font-size: 16px;
    color: #fff;
  }
  .backButton {
    left: 0px;
    top: 0px;

  }
  .bcFIVc {
    width: 20px;
    height: 10px;
    display: inline-block;
    vertical-align: middle;
    transform: rotate(90deg);
    background: url(../../images/tu1.png) -34px -117px / 410px 130px no-repeat;
    background-position: -34px -117px;
    transition: transform 0.1s ease-out 0s;
  }
  .home-header .menu-toggle, .kTHRmj .menu-toggle {
    font-size: 0;
    position: absolute;
    width: 29px !important;
    height: 29px !important;
    top: 8px;
    right: 9px !important;
    background: url(../../images/cd.png) center / contain no-repeat !important;
  }
  .home-header .menu-toggle, .kTHRmj .topres {
    font-size: 0;
    position: absolute;
    width: 30px;
    height: 30px;
    top: 7px;
    right: 50px;
    background: url("../../images/leftico.png");
    background-size: 457px 30px;
  }
  .topresimg{
    background-position: -276px 0px !important;
  }
  .exdTne {
    height: 45px;
    left: 50%;
    transform: translateX(-50%);
    position: fixed;
    top: 0px;
    display: inline-flex;
    -webkit-box-pack: center;
    justify-content: center;
    -webkit-box-align: center;
    align-items: center;
    padding: 0px 10px;
  }
  .fRfaQM {
    color: rgb(255, 255, 255);
    user-select: none;
    cursor: pointer;
    padding: 0px 0.7rem;
    border-width: 1px;
    border-style: solid;
    border-color: white;
    border-image: initial;
    border-radius: 3rem;
  }
  .OSUUp {
    position: fixed;
    background-color: rgba(55, 55, 55, 0.7);
    top: 0px;
    right: 0px;
    bottom: 0px;
    left: 0px;
    width: 100%;
    height: 100%;
    z-index: 2;
    cursor: pointer;
    opacity: 1;
    visibility: visible;
    transition: all 0.2s ease-in-out 0s;
  }
</style>


