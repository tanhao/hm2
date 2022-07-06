<template>
  <div class="home">
    <div class="sc-bZQynM OQRyf">
      <my-header title="统计" left="false" totalReturn="true"></my-header>
      <div class="sc-htoDjs UDzZc">
        <div class="block">
          <div class="segmented segmented-round">
            <button class="button button-round button-outline button-active">两面长龙</button>
          </div>
        </div>
        <div class="main">
          <div id="top-line" v-show="showHideModel"></div>
          <div class="WQWtT" v-show="showHideModel">
          <ul>
            <template v-for="(item,index) in changlongList">
              <li>
                <div>{{$t(item.type)}}</div>
                <div>{{$t(item.oddsKey.toUpperCase())}} {{item.number}}期</div>
              </li>
            </template>
          </ul>
          </div>


        </div>
      </div>
    </div>
    <my-footer></my-footer>
    <left-menu v-show="showMenu"></left-menu>
  </div>
</template>
<script>
  import MyHeader from '@/components/sg/layout/header'
  import MyFooter from '@/components/sg/layout/footer'
  import LeftMenu from '@/components/sg/layout/leftmenu'
  import Lottery from '@/axios/api-game.js'
  import {mapGetters, mapActions} from 'vuex'
  import Bet from '@/fetch/bet.js'
  import Utils from '@/components/comm/Utils.js'
  import {Indicator} from 'mint-ui'

  export default {
    components: {
      MyHeader,
      MyFooter,
      LeftMenu
    },
    data() {
      return {
        changlongList:[],
        showHideModel:true,
      }
    },
    filters: {},

    computed: {
      ...mapGetters(['gameMenu', 'showMenu', 'gameId']),

    }
    ,
    created()
    {

    }
    ,
    methods:{
      selectModelFunction(flag){
        this.showHideModel = flag;
      },
    }
    ,
    mounted(){
      let self = this;
      Lottery.getLotteryRoad(self.gameId).then(val=>{
        if(val.code==10000 && typeof val.data != "undefined"){
          let arr = val.data.changlong;
          for(let obj of arr){
            for(let key in obj){
              let param = {'type':key.split('_')[0],'oddsKey':key.split('_')[1],'number':obj[key]};
              self.changlongList.push(param);
            }
          }
        }

      });
    }
    }
</script>
<style scoped>
  .UDzZc {
    height: calc(100% - 45px);
    position: relative;
  }

  .main {
    height: calc(100% - 45px);
    overflow: auto;
  }
  #top-line {
    width: 100%;
    height: 3px;
    line-height: 0;
    font-size: 0;
    overflow: hidden;
    text-align: center;
    z-index: 1;
    background-color: #0fa6ea;
    background: -webkit-linear-gradient(left,rgba(15,166,234,1) 0,rgba(89,204,24,1) 10%,rgba(15,166,234,1) 60%,rgba(15,166,234,1) 100%);
    background: -moz-linear-gradient(left,rgba(15,166,234,1) 0,rgba(89,204,24,1) 10%,rgba(15,166,234,1) 60%,rgba(15,166,234,1) 100%);
    background: -ms-linear-gradient(left,rgba(15,166,234,1) 0,rgba(89,204,24,1) 10%,rgba(15,166,234,1) 60%,rgba(15,166,234,1) 100%);
    background: linear-gradient(left,rgba(15,166,234,1) 0,rgba(89,204,24,1) 10%,rgba(15,166,234,1) 60%,rgba(15,166,234,1) 100%);
  }
  .WQWtT {
    height: 100%;
    overflow: auto;
  }

  .WQWtT > ul {
    margin: 0px;
    padding: 0px;
  }

  .WQWtT > ul > li {
    list-style-type: none;
    clear: both;
    overflow: hidden;
    background: white;
    border-bottom: 1px solid rgb(238, 238, 238);
  }

  .WQWtT > ul > li > div:first-child {
    float: left;
    width: 50%;
    text-align: center;
    height: 45px;
    line-height: 45px;
    font-size: 16px;
  }

  .WQWtT > ul > li > div:last-child {
    float: right;
    width: 49.5%;
    text-align: center;
    height: 45px;
    line-height: 45px;
    font-size: 16px;
    color: red;
    border-left: 1px solid rgb(238, 238, 238);
  }

  .csoFvQ {
    height: 100%;
    overflow: auto;
  }

  .jKrOPS {
    text-align: center;
    height: 50px;
    line-height: 50px;
    font-size: 18px;
    background: white;
  }

  .hEaWLh ul {
    clear: both;
    overflow: hidden;
    background: white;
    margin: 0px;
    padding: 0px;
  }

  .hEaWLh ul > li.active {
    background: rgb(0, 68, 119);
  }

  .hEaWLh ul {
    border-bottom: 1px #ddd solid;
  }

  .hEaWLh ul > li {
    margin-bottom: -1px;
    margin-right: -1px;
    list-style-type: none;
    width: 20.25%;
    float: left;
    text-align: center;
    height: 40px;
    line-height: 40px;
    box-sizing: border-box;
    font-size: 16px;
    border-width: 0.5px;
    border-style: solid;
    border-color: rgb(234, 234, 234);
    border-image: initial;
  }

  .hEaWLh ul > li.active > a {
    color: white;
  }

  .hkLcFi ul {
    clear: both;
    margin: 0px;
    padding: 0px;
    overflow: hidden;
    background: white;
  }

  .hkLcFi ul > li {
    margin-top: -1px;
    margin-right: -1px;
    width: 10.25%;
    float: left;
    height: 50px;
    box-sizing: border-box;
    padding-left: 5px;
    padding-top: 12px;
    line-height: 18px;
    border-width: 1px;
    border-style: solid;
    border-color: rgb(234, 234, 234);
    border-image: initial;
  }

  .iabIyi ul {
    clear: both;
    margin: 0px;
    padding: 0px;
    overflow: hidden;
    background: white;
  }

  .iabIyi ul > li {
    margin-top: -1px;
    width: 10.25%;
    float: left;
    height: 50px;
    box-sizing: border-box;
    text-align: center;
    line-height: 50px;
    margin-right: -1px;
    border-width: 1px;
    border-style: solid;
    border-color: rgb(234, 234, 234);
    border-image: initial;
  }

  .block {
    background: linear-gradient(43deg, rgb(19, 46, 123) 0%, rgb(0, 201, 202) 100%);
    padding: 8px 10px;
  }

  .segmented {
    -webkit-align-self: center;
    -ms-flex-item-align: center;
    align-self: center;
    display: -webkit-box;
    display: -webkit-flex;
    display: -ms-flexbox;
    display: flex;
    -webkit-flex-wrap: nowrap;
    -ms-flex-wrap: nowrap;
    flex-wrap: nowrap;
  }

  .segmented .button.button-round:first-child {
    border-radius: 29px 0 0 29px;
  }

  .segmented .button:first-child {
    border-radius: 5px 0 0 5px;
    border-left-width: 1px;
    border-left-style: solid;
  }

  .segmented .button {
    border-radius: 0;
    border-left-width: 0;
  }

  .segmented .button.button-round:last-child {
    border-radius: 29px;
  }

  .button.button-active {
    background: #116397;
    color: #eaeaea;
  }

  .button {
    background: #efeff4;
    font-size: 14px;
    border: 1px solid #0c9eb4;
    color: #163c7d;
    border-radius: 5px;
    line-height: 25px;
    height: 29px;
  }

  .segmented .button, .segmented button {
    width: 100%;
    -webkit-flex-shrink: 1;
    -ms-flex-negative: 1;
    flex-shrink: 1;
    min-width: 0;
  }

  .rough_lines {
    width: 100%;
    height: 10px;
    background-color: rgb(235, 235, 235);
    box-shadow: rgb(187, 187, 187) 0px 1px 1px inset;
  }

  .gmqvGy ul {
    clear: both;
    overflow: hidden;
    background: white;
    margin: 0px;
    padding: 0px;
  }

  .gmqvGy ul > li._0, .gmqvGy ul > li._1, .gmqvGy ul > li._2 {
    width: 25.25%;
  }

  .gmqvGy ul > li._3 {
    width: 40%;
  }

  .gmqvGy ul > li._4, .gmqvGy ul > li._5 {
    width: 50.25%;
  }

  .gmqvGy ul > li.active {
    background: rgb(0, 68, 119);
  }

  .gmqvGy ul > li {
    margin-bottom: -1px;
    margin-right: -1px;
    list-style-type: none;
    float: left;
    text-align: center;
    height: 40px;
    line-height: 40px;
    box-sizing: border-box;
    font-size: 16px;
    border-width: 0.5px;
    border-style: solid;
    border-color: rgb(234, 234, 234);
    border-image: initial;
  }
  .gmqvGy ul > li.active > a {
    color: white;
  }
  .kPKqVh > ul {
    clear: both;
    display: flex;
    flex-direction: row-reverse;
    flex-wrap: wrap;
    margin: 0px;
    padding: 0px;
    overflow: hidden;
    background: white;
  }
  .kPKqVh > ul > li:nth-of-type(2n+1) {
    color: rgb(0, 68, 119);
  }
  .kPKqVh > ul > li {
    margin-bottom: -1px;
    margin-right: -1px;
    list-style-type: none;
    width: calc((100%/10) + 1px);
    float: left;
    text-align: center;
    line-height: 45px;
    box-sizing: border-box;
    font-size: 16px;
    border-width: 0.5px;
    border-style: solid;
    border-color: rgb(234, 234, 234);
    border-image: initial;
  }
  .kPKqVh > ul > li:nth-of-type(2n) {
    color: rgb(243, 129, 2);
  }
</style>

