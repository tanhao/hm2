<template>
  <div class="home">
    <div class="sc-bZQynM OQRyf">
      <my-header title="统计" left="true" back="true"></my-header>
      <div class="sc-htoDjs UDzZc">
        <div class="block">
          <div class="segmented segmented-round">
            <button class="button button-round button-outline" :class="showHideModel?'button-active':''"
                    @click="selectModelFunction(true)">两面长龙
            </button>
            <button class="button button-round button-outline" :class="!showHideModel?'button-active':''"
                    @click="selectModelFunction(false)">路珠
            </button>
          </div>
        </div>
        <div class="main">
          <div id="top-line" v-show="showHideModel"></div>
          <div class="WQWtT" v-show="showHideModel">
            <ul>
              <template v-for="(item,index) in longDragonList">
                <li>
                  <div>{{$t('ssclz_'+item.type)}}</div>
                  <div v-if="item.oddsKey!='draw'">{{$t(item.oddsKey.toUpperCase())}} {{item.number}}期</div>
                  <div v-else>和 {{item.number}}期</div>
                </li>
              </template>
            </ul>
          </div>
          <!--结束两面长龙-->
          <div class="csoFvQ" v-show="!showHideModel">
            <div id="top-line"></div>
            <div class="jKrOPS">筛选名次</div>
            <div class="ssctop">
              <ul>
                <template v-for="item in placingMenu">
                  <li @click="selectPlacingActiveType(item.value)" :class="placingActive===item.value?'active':''"><a
                  >{{$t('ssclz_'+item.title)}}</a></li>
                </template>
              </ul>
            </div>
            <div class="sscz">
              <ul class="ssc">
                <li>
                  <div class="n_0">0</div>
                </li>
                <li>
                  <div class="n_1">1</div>
                </li>
                <li>
                  <div class="n_2">2</div>
                </li>
                <li>
                  <div class="n_3">3</div>
                </li>
                <li>
                  <div class="n_4">4</div>
                </li>
                <li>
                  <div class="n_5">5</div>
                </li>
                <li>
                  <div class="n_6">6</div>
                </li>
                <li>
                  <div class="n_7">7</div>
                </li>
                <li>
                  <div class="n_8">8</div>
                </li>
                <li>
                  <div class="n_9">9</div>
                </li>
              </ul>
            </div>
            <div class="sscz">
              <ul>
                <template v-for="(cell,n) in placingNumber">
                  <li><div style="text-align: center">{{cell}}</div>
                  </li>
                </template>
              </ul>
            </div>
            <br>
            <div id="top-line"></div>
            <div class="jKrOPS">筛选路珠</div>
            <div class="ssc">
              <ul>
                <template v-for="(item,i) in luzhuMenu">
                  <li v-if="i<4" :class="luzhuActive==item.value?'active':''" class="_0"
                      @click="selectLuzhuType(item.value)"><a>{{$t('ssclz_'+item.title)}}</a></li>
                  <li v-if="i>=4" :class="luzhuActive==item.value?'active':''" class="_5"
                      @click="selectLuzhuType(item.value)"><a>{{$t('ssclz_'+item.title)}}</a></li>
                </template>
              </ul>
            </div>
            <div class="kPKqVh">
              <template v-for="(qiuList,index) in luzhuList">
                <template v-for="(list,i) in qiuList">
                  <ul v-show="luzhuActive==i">
                    <template v-for="(item,j) in list">
                      <template v-for="(obj,k) in item">
                        <li>
                          <template v-for="(cell,n) in obj">

                            <div v-if="cell!='draw' && /^[0-9]\d*$/.test(cell)">{{cell}}</div>
                            <div v-else-if="cell!='draw' && !/^[0-9]\d*$/.test(cell)">{{$t(cell)}}</div>
                            <div v-else>和</div>
                          </template>
                        </li>
                      </template>
                    </template>
                  </ul>
                </template>
              </template>
            </div>
          </div>
        </div>
      </div>
    </div>
    <my-footer></my-footer>
  </div>
</template>
<script>
  import MyHeader from '@/components/sg/layout/header'
  import MyFooter from '@/components/sg/layout/footer'
  import Lottery from '@/axios/api-game.js'
  import {mapGetters, mapActions} from 'vuex'
  import Utils from '@/components/comm/Utils.js'

  export default {
    components: {
      MyHeader,
      MyFooter,
    },
    data() {
      return {
        changlongList: [],
        showHideModel: true,
        luzhuList: {
          'no1': {'no1val': [], 'no1oe': [], 'no1ou': [], 'no1': []},
          'no2': {'no2val': [], 'no2oe': [], 'no2ou': [], 'no2': []},
          'no3': {'no3val': [], 'no3oe': [], 'no3ou': [], 'no3': []},
          'no4': {'no4val': [], 'no4oe': [], 'no4ou': [], 'no4': []},
          'no5': {'no5val': [], 'no5oe': [], 'no5ou': [], 'no5': []},
          'sum': {'dtt': [], 'sumou': [], 'sumoe': []}
        },
        placingActive: 'no1',
        placingNumber: [],
        luzhuActive: 'no1val',
        placingMenu: [
          {value: 'no1', title: 'no1'},
          {value: 'no2', title: 'no2'},
          {value: 'no3', title: 'no3'},
          {value: 'no4', title: 'no4'},
          {value: 'no5', title: 'no5'},
        ],
        luzhuMenu: [
          {value: 'no1val', title: 'no1val'},
          {value: 'no1ou', title: 'OU'},
          {value: 'no1oe', title: 'OE'},
          {value: 'dtt', title: 'dtt'},
          {value: 'sumou', title: 'sumou'},
          {value: 'sumoe', title: 'sumoe'},
        ]
      }
    },
    filters: {},

    computed: {
      ...mapGetters(['longDragonList','gameMenu', 'showMenu', 'gameId']),

    }
    ,
    created() {

    }
    ,
    methods: {
      selectModelFunction(flag) {
        this.showHideModel = flag;
      },
      selectPlacingActiveType(activeType) {
        this.placingActive = activeType;
        this.luzhuMenu[0].value = activeType + 'val';
        this.luzhuMenu[0].title = activeType;
        this.luzhuMenu[1].value = activeType + 'ou';
        this.luzhuMenu[2].value = activeType + 'oe';
        this.placingNumber = this.luzhuList[activeType][activeType];
        if (this.luzhuActive.indexOf('dtt') == -1 || this.luzhuActive.indexOf('sum') == -1) {
          for (let i = 0; i < 3; i++) {
            if (this.luzhuMenu[i].value.indexOf(this.luzhuActive.substring(3, this.luzhuActive.length)) != -1) {
              this.luzhuActive = this.luzhuMenu[i].value;
            }
          }
        }

      },
      selectLuzhuType(activeType) {
        this.luzhuActive = activeType;
      }
    }
    ,
    mounted() {
      let self = this;
      let num = 0;
      Lottery.getLotteryRoad({'lotteryId': self.gameId}).then(val => {
        if(val.code==10000 && typeof val.data != "undefined"){
          /*let arr = val.data.changlong;
          let luzhu = [];
          for (let obj of arr) {
            for (let key in obj) {
              let param = {'type': key.split('_')[0], 'oddsKey': key.split('_')[1], 'number': obj[key]};
              self.changlongList.push(param);
            }
          }*/
          let luzhuParams = {};

          for (let obj in val.data) {
            if (-1 != obj.indexOf('dtt') || -1 != obj.indexOf('sum')) {
              luzhuParams[obj] = Utils.getArr(val.data[obj]);
            }
            if (obj.indexOf('no') != -1 && obj.split('no')[1].length > 2) {
              luzhuParams[obj] = Utils.getArr(val.data[obj]);
            } else if (-1 == obj.indexOf('dtt') && -1 == obj.indexOf('sum')) {
              if (obj == 'no1') {
                self.placingNumber = val.data[obj];
              }
              luzhuParams[obj] = val.data[obj];
            }
          }
          for (let key in luzhuParams) {

            if (-1 != key.indexOf('dtt') || -1 != key.indexOf('sum')) {
              self.luzhuList.sum[key] = luzhuParams[key];
            } else if (-1 != key.indexOf('no1')) {
              self.luzhuList.no1[key] = luzhuParams[key];
            } else if (-1 != key.indexOf('no2')) {
              self.luzhuList.no2[key] = luzhuParams[key];
            } else if (-1 != key.indexOf('no3')) {
              self.luzhuList.no3[key] = luzhuParams[key];
            } else if (-1 != key.indexOf('no4')) {
              self.luzhuList.no4[key] = luzhuParams[key];
            } else if (-1 != key.indexOf('no5')) {
              self.luzhuList.no5[key] = luzhuParams[key];
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
    background: -webkit-linear-gradient(left, rgba(15, 166, 234, 1) 0, rgba(89, 204, 24, 1) 10%, rgba(15, 166, 234, 1) 60%, rgba(15, 166, 234, 1) 100%);
    background: -moz-linear-gradient(left, rgba(15, 166, 234, 1) 0, rgba(89, 204, 24, 1) 10%, rgba(15, 166, 234, 1) 60%, rgba(15, 166, 234, 1) 100%);
    background: -ms-linear-gradient(left, rgba(15, 166, 234, 1) 0, rgba(89, 204, 24, 1) 10%, rgba(15, 166, 234, 1) 60%, rgba(15, 166, 234, 1) 100%);
    background: linear-gradient(left, rgba(15, 166, 234, 1) 0, rgba(89, 204, 24, 1) 10%, rgba(15, 166, 234, 1) 60%, rgba(15, 166, 234, 1) 100%);
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

  .ssctop ul {
    clear: both;
    overflow: hidden;
    background: white;
    margin: 0px;
    padding: 0px;
  }

  .ssctop ul > li.active {
    background: rgb(0, 68, 119);
  }

  .ssctop ul {
    border-bottom: 1px #ddd solid;
  }

  .ssctop ul > li {
    margin-bottom: -1px;
    margin-right: -1px;
    list-style-type: none;
    width: calc((100%/5) + 1px);
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

  .ssctop ul > li.active > a {
    color: white;
  }

  .sscz ul {
    clear: both;
    margin: 0px;
    padding: 0px;
    overflow: hidden;
    background: white;
  }

  .sscz ul > li {
    margin-top: -1px;
    margin-right: -1px;
    width: calc((100%/10) + 1px);
    display: table;
    float: left;
    height: 50px;
    box-sizing: border-box;
    padding-top: 12px;
    line-height: 18px;
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
    border-radius: 0 29px 29px 0;
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

  .ssc ul {
    clear: both;
    overflow: hidden;
    background: white;
    margin: 0px;
    padding: 0px;
  }

  .ssc ul > li._0, .ssc ul > li._1, .ssc ul > li._2 {
    width: calc((100%/4) + 1px);
  }

  .ssc ul > li._3 {
    width: 40%;
  }

  .ssc ul > li._4, .ssc ul > li._5 {
    width: calc((100%/2) + 1px);
  }

  .ssc ul > li.active {
    background: rgb(0, 68, 119);
  }

  .ssc ul > li {
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

  .ssc ul > li.active > a {
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
    display: table;
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

