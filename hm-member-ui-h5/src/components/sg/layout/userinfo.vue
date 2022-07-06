<template>
  <div class="home">
    <div class="sc-bZQynM OQRyf gm_main">
      <my-header title="个人资讯" left="false" ></my-header>
      <div :class="colorTypeShowOrHide?'sc-bxivhb gzjRIk':'sc-bxivhb'" @click="showgamelist"></div><!--显示侧边加gzjRIk-->
      <div :class="colorTypeShowOrHide?'sc-eNQAEJ iFUcSP jfiSZt':'sc-eNQAEJ iFUcSP'" v-show="colorTypeShowOrHide"><!--快速显示彩种列表 jfiSZt-->
        <div class="game-div">
          <h3 class="game-title">快开彩系列</h3>
          <div class="game-item-wrapper">
            <template v-for="(list,i) in gameMenu">
              <template >
                <span class="game-item" @click="selectLotteryLimit(list.index,list.title)">{{$t(list.title)}}</span>
              </template>
            </template>
          </div>
        </div>
      </div>
      <div class="sc-htoDjs UDzZc">
        <div class="contact_type1 mini">
          <div class="contact_info1" style="border-bottom: 1px solid rgb(224, 224, 224);">
            <div class="contact_name1">会员账号:</div>
            <div class="contact_name1" style="color: rgb(0, 0, 0);">{{member.username}}</div>
          </div>
          <div class="contact_info1" style="border-bottom: 1px solid rgb(224, 224, 224);">
          <div class="contact_name1">会员名称:</div>
            <div class="contact_name1" style="color: rgb(0, 0, 0);">{{member.nickName}}</div>
          </div>
          <div class="contact_info1" style="border-bottom: 1px solid rgb(224, 224, 224);">
            <div class="contact_name1">所属盘口:</div>
            <div class="contact_name1" style="color: rgb(0, 0, 0);">{{market}}盘</div>
          </div>
          <div class="contact_info1" style="border-bottom: 1px solid rgb(224, 224, 224);">
            <div class="contact_name1">账户状态:</div>
            <div class="contact_name1" style="color: rgb(0, 0, 0);">启用</div>
          </div>
          <div class="contact_info1" style="border-bottom: 1px solid rgb(224, 224, 224);">
            <div class="contact_name1">额度 : </div>
            <div class="contact_name1" style="color: rgb(0, 0, 0);">{{member.credit | moneyFmt}} (余额：{{balance | moneyFmt}})</div>
          </div>
        </div>
        <div class="personal_Navigation">
          <div class="i1h1lp-1 jhcQwh" style="width: 100%; position: relative;">
            <div class="i1h1lp-2 epExtR" @click="showgamelist">
              <span class="text">{{$t(selectGameTitle)}}</span>
              <span class="arrow"></span>
            </div>
          </div>
        </div>
        <div class="personal-info-scroll">
          <template v-for="(item,index) in orderList">
            <div>
              <div class="no_date1">
                <div class="nd_div1">{{$t(item.kindKey)}}</div>
              </div>
              <div class="info_table_title1">
                <div class="ir_4">{{item.marketOpen.substring(item.marketOpen.length-1)}}盘退水<br><span style="color: rgb(21, 117, 193);">{{item.regress}}%</span></div>
                <div class="ir_4">单注最低<br><span style="color: rgb(21, 117, 193);">{{item.minBetLimit}}</span></div>
                <div class="ir_4">单注最高<br><span style="color: rgb(21, 117, 193);">{{item.maxBetLimit}}</span></div>
                <div class="ir_4">单期最高<br><span style="color: rgb(21, 117, 193);">{{item.maxPeriodLimit}}</span></div>
              </div>
              <div class="rough_lines"></div>
            </div>
          </template>
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
  import Lottery from '@/axios/api-game.js'
  import Utils from '@/components/comm/Utils.js'


  export default {
    components: {
      MyHeader,
      MyFooter,
    },
    data() {
      return {
        orderList:[],
        colorTypeShowOrHide:false,
        params:{
          'lotteryId':101
        },
        selectGameTitle:'bjpk10'
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
      ...mapGetters(['gameMenu','balance','market','showMenu','member','gameId','game']),

    },
    created(){

    },
    methods:{
      selectLotteryLimit(id,title){
        this.params.lotteryId = id;
        this.selectGameTitle = title;
        Lottery.getLotteryLimit(this.params.lotteryId).then(val=>{
          this.orderList = val.data;
          this.colorTypeShowOrHide = false;
        });
      },
      showgamelist(){
        if(this.colorTypeShowOrHide){
          this.colorTypeShowOrHide = false;
        }else{
          this.colorTypeShowOrHide = true;
        }

      }
    },
    mounted(){
      if(this.game.lotteryId && this.game.lotteryKey){
        this.selectLotteryLimit(this.game.lotteryId,this.game.lotteryKey);
      }else{
        this.selectLotteryLimit(101,'bjpk10');
      }

    }
  }
</script>
<style scoped>
  .UDzZc {
    height: calc(100% - 46px);
    position: relative;
  }
  .contact_type1.mini {
    display: table;
    margin: 0px auto;
  }
  .contact_type1{
    width: 100%;
  }
  .contact_info1 {
    background-color: #FFF;
    height: 25px;
  }
 .contact_info1 .contact_name1:first-of-type {
    padding-left: 1.5rem;
  }
 .contact_info1 .contact_name1 {
    line-height: 25px;
    font-size: 13px;
  }
  .contact_type1 .contact_info1 div {
    float: left;
  }
 .contact_type1 .contact_info1 .contact_name1:last-of-type {
    padding-left: 1rem;
    color: rgb(21, 117, 193) !important;
  }
  .UDzZc .personal_Navigation {
    height: 45px;
    position: relative;
    width: 100%;
    z-index: 1;
    background: linear-gradient(135deg, rgb(22, 46, 119) 0%, rgb(34, 201, 203) 100%);
  }
  .jhcQwh {
    height: 45px;
    position: fixed;
    top: 0px;
    display: inline-flex;
    -webkit-box-pack: center;
    justify-content: center;
    -webkit-box-align: center;
    align-items: center;
    box-sizing: border-box;
    color: rgb(255, 255, 255);
    padding: 0px 10px;
  }
  .epExtR {
    font-size: 16px;
    user-select: none;
    cursor: pointer;
    display: flex;
    flex-direction: row;
    flex-wrap: nowrap;
    -webkit-box-pack: justify;
    justify-content: space-between;
    -webkit-box-align: center;
    align-items: center;
    width: 100%;
    color: rgb(255, 255, 255);
  }
  .epExtR .text {
    margin-right: 8px;
    font-size: 16px;
    user-select: none;
    cursor: pointer;
    display: flex;
    flex-direction: row;
    flex-wrap: nowrap;
    -webkit-box-pack: justify;
    justify-content: space-between;
    -webkit-box-align: center;
    align-items: center;
    width: 100%;
    color: rgb(255, 255, 255);
  }
  .epExtR .arrow {
    width: 8px;
    height: 8px;
    display: inline-block;
    vertical-align: middle;
    transform-origin: center center;
    transform: rotate(45deg) translate(-2px, -2px);
    border-style: solid;
    border-color: rgb(255, 255, 255);
    border-width: 0px 2px 2px 0px;
    transition: transform 0.1s ease-out 0s;
  }
  .personal-info-scroll {

    height: calc(95% - 135px);
    width: 100%;
    position: relative;
    overflow: auto;
  }
  .personal-info-scroll .no_date1 {
    height: 50px;
    background-color: rgb(255, 255, 255);
    box-sizing: border-box;
    border-bottom: 1px solid rgb(204, 204, 204);
  }
  .personal-info-scroll .no_date1 .nd_div1 {
    padding-top: 5px;
    text-align: center;
    color: rgb(153, 153, 153);
    font-size: 18px;
    line-height: 40px;
    margin: 0px auto;
  }
  .personal-info-scroll .info_table_title1 {
    background-color: #fff;
    font-size: 16px;
    min-height: 50px;
  }
  .personal-info-scroll .info_table_title1 div {
    float: left;
    text-align: center;
    line-height: 1.6em;
    height: 50px;
    color: rgb(102, 102, 102);
    box-sizing: border-box;
    border-left: 1px solid rgb(204, 204, 204);
    border-bottom: 1px solid rgb(204, 204, 204);
  }
  .personal-info-scroll .ir_4 {
    width: 25%;
  }
  .rough_lines {
    width: 100%;
    height: 10px;
    background-color: rgb(235, 235, 235);
    box-shadow: rgb(187, 187, 187) 0px 1px 1px inset;
  }

</style>

