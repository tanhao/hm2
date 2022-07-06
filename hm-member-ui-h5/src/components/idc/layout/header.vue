<template>
  <div>
    <!--首页顶部大厅-->
    <div class="home-header after-login top" v-if="maintop">
      <a rel="noopener noreferrer" v-if="back"></a>
      <!-- 用户协议-->
      <div class="logo" v-if="userxy">用户协议与规则</div>
      <!--用户弹出公告-->
      <div class="logo" v-if="usergg">用户公告</div>
      <div class="logo" v-if="!userxy && !usergg">{{siteName}}</div>
      <a class="menu-toggle" v-if="!userxy && !usergg" @click="shownav()">Menu</a>
    </div>
    <!--切换到详细游戏的头部-->
    <div class="sc-fjdhpX kTHRmj" v-if="!maintop">
      <span class="back" v-if="!back" @click="goback()"></span>
      <span class="back" v-if="profitlosBack" @click="goProfitlosReturn()"></span>
      <div class="sc-hMqMXs kifISk" v-if="!top">
        <div class="com-sel">
          <select class="com-opt" name="" v-model="lotteryId" @change="selectCountry">
            <template v-for="item in lotteryListFind">
              <option  :value="item.index" :key="item.index">{{$t(item.title)}}</option>
            </template>
          </select>
            <select class="com-opt" name="" v-model="lotterylistName" @change="activeTypeSelect">
              <template v-if="categoryList" >
                <option :value="item" v-for="(item,index) in categoryListFind">{{$t(item)}}</option>
              </template>
            </select>
        </div>
      </div>
      <div class="sc-hMqMXs kifISk" v-if="userInfomation">
        <div class="com-sel">
          <select class="com-opt" name="" v-model="infomationLotteryKey" @change="userInfomationSelect">
            <template v-for="item in gameMenu">
              <option  :value="item.title" :key="item.index">{{$t(item.title)}}</option>
            </template>
          </select>
        </div>
      </div>
      <div class="title" v-if="!titles">{{title}}</div>
      <div v-if="resbnt" @click="">
      <a class="topres topresimg" @click="refreshPageFun"></a>
      </div>
      <a class="menu-toggle" v-if="!resetPasswordStatus" @click="shownav()">Menu</a>
    </div>
  </div>
</template>
<script>
  import {mapActions,mapGetters} from 'vuex'
  export default {
    data() {
      return {
        lotteryId:null,
        lotterylistName: "请选择彩种",
        colorTypeShowOrHide: false,
        activeType: 'PK10JSC',
        lotterylist: [

        ],
        infomationLotteryKey:'bjpk10'
      }
    },
    props: {
      userxy:null,
      usergg:null,
      resbnt:null,
      top: null,
      maintop: null,
      titles: null,
      back: null,
      title: null,
      userInfomation:null,
      profitlosBack:null,

    },
    computed: {
      ...mapGetters((['gameMenu','siteName','resetPasswordStatus','gameId','categoryList','gameInfo','pagePosition','game','playType','profitlosReturn'])),
      categoryListFind(){
        if(this.playType){
          this.lotterylistName = this.playType;
        }else{
          this.lotterylistName = this.categoryList[0];
        }
        return this.categoryList;
      },
      lotteryKey(){
        return this.game.lotteryKey;
      },
      lotteryListFind(){
        let self = this;
        self.lotteryId = self.gameId;
        return this.gameMenu;
      }
    },
    methods: {
      ...mapActions(['changeMenu','setLotteryNow','setPagePosition','setWhetherSwitch','selectGame','setCurrentGame','setPlayType']),
      shownav() {
        this.changeMenu(true);
      },
      goProfitlosReturn(){
        this.$router.push({name:this.profitlosReturn.name,query:this.profitlosReturn.query});
      },
      refreshPageFun(){
        this.$emit('refreshPageFun');
      },
      showgamelist() {
        if (this.colorTypeShowOrHide)
          this.colorTypeShowOrHide = false;
        else
          this.colorTypeShowOrHide = true;
      },
      selectCountry() {
        let self = this;
        self.setPlayType(null);
        let lotteryObj = self.gameMenu.find(val=>{
          if(val.index+'' == self.lotteryId){
            return val;
          }
        });

        let newPageIndex = lotteryObj.index.toString().substring(0,2);
        let flag = false;
        self.setWhetherSwitch(true);
        if(self.gameId.toString().indexOf(newPageIndex)!=-1){
          flag = true;
        }
        //self.selectGame(lotteryObj.index);
        if(flag){
          this.$emit('refreshTime',flag);
          this.$router.push('/idc/'+lotteryObj.title);
        }else{
          this.$router.push('/idc/'+lotteryObj.title);
        }



      },
      goback() {

        if(!this.title && this.title!='下注明细'){
          this.setPlayType(null);
        }
        if(this.title && this.title=='变更密码' && this.resetPasswordStatus){
          window.location.href='/h5';
          return;
        }
        this.$router.push({name:this.pagePosition[this.pagePosition.length-1].name,query:this.pagePosition[this.pagePosition.length-1].query});
        this.setPagePosition();

      },
      activeTypeSelect(){
        console.log('1',this.lotterylistName);
        this.setPlayType(this.lotterylistName);
        this.$emit('activetypeselect',this.lotterylistName);
      },
      userInfomationSelect(){
        this.$emit('lotteryLimit',{'lotteryKey':this.infomationLotteryKey});
      }
    },
    mounted() {
      let self = this;
      this.$nextTick(()=>{
        if(self.playType){
          self.lotterylistName = self.playType;
          self.activeTypeSelect();
        }

      })
    },
    created(){


    }
  }
</script>
<style scoped>
  ::-webkit-scrollbar {
    width: 0;
  }

  select::-ms-expand {
    display: none;
  }

  option::-ms-expand {
    display: none;
  }

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
    background: #CD3C29;
  }

  .kTHRmj .title {
    line-height: 45px;
    text-align: center;
    font-weight: bold;
    font-size: 1.15em;
    color: #fff;
  }

  .home-header .menu-toggle, .kTHRmj .menu-toggle {
    font-size: 0;
    position: absolute;
    width: 30px;
    height: 30px;
    top: 7px;
    right: 13px !important;
    background: url("../../images/cd.png") center / contain no-repeat !important;
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
  .back {
    font-size: 0;
    position: absolute;
    width: 30px;
    height: 30px;
    top: 7px;
    left: 13px;
    z-index:900;
    background: url("../../images/back.png") center / contain no-repeat;
  }

  .com-sel {
    border-radius: 5px;
    padding: 4px 5px;
    background-color: #fff;
  }

  .com-opt {
    padding-right: 22px;
    position: relative;
    appearance: none;
    -moz-appearance: none;
    -webkit-appearance: none;
    background: url("../../images/idcsetico.png") no-repeat right center transparent;
  }

  .com-sel select {
    padding-left: 8px;
    overflow: hidden;
    font-weight: 700;
    height: 24px;
    border: solid 0px #fff;
    appearance: none;
    -moz-appearance: none;
    -webkit-appearance: none;
  }
</style>


