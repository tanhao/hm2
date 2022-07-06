<template>
  <div id="idc">
    <div class="home">
      <div class="sc-bZQynM OQRyf">
        <div class="sc-bdVaJa jaFIbq otherpage">
          <my-header top="true" title="两面长龙"></my-header>
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
          <div class="ui-content">
            <div class="main">
              <div class="WQWtT" v-show="showHideModel">
                <ul>
                  <template v-for="(item,index) in longDragonList">
                    <li>
                      <div>{{$t('gdkl10lz_'+item.type)}}</div>
                      <div>{{$t(item.oddsKey.toUpperCase())}} {{item.number}}期</div>
                    </li>
                  </template>
                </ul>
              </div>
              <!--结束两面长龙-->
              <div class="csoFvQ" v-show="!showHideModel">
                <div id="top-line"></div>
                <div class="jKrOPS">筛选名次</div>
                <div class="gdkl10top">
                  <ul>
                    <template v-for="item in placingMenu">
                      <li @click="selectPlacingActiveType(item.value)" :class="placingActive===item.value?'active':''">
                        <a
                        >{{$t('gdkl10lz_'+item.title)}}
                        </a>
                      </li>
                    </template>
                  </ul>
                </div>
                <div class="gdkl10z">
                  <ul class="klsf">
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
                    <li>
                      <div class="n_10">10</div>
                    </li>
                  </ul>
                  <ul>
                    <template v-for="(cell,n) in placingNumber">
                      <li v-if="n<11"><div style="text-align: center">{{cell}}</div>
                      </li>
                    </template>
                  </ul>

                  <ul class="klsf">
                    <li>
                      <div class="n_11">11</div>
                    </li>
                    <li>
                      <div class="n_12">12</div>
                    </li>
                    <li>
                      <div class="n_13">13</div>
                    </li>
                    <li>
                      <div class="n_14">14</div>
                    </li>
                    <li>
                      <div class="n_15">15</div>
                    </li>
                    <li>
                      <div class="n_16">16</div>
                    </li>
                    <li>
                      <div class="n_17">17</div>
                    </li>
                    <li>
                      <div class="n_18">18</div>
                    </li>
                    <li>
                      <div class="n_19">19</div>
                    </li>
                    <li>
                      <div class="n_20">20</div>
                    </li>
                  </ul>
                  <ul>
                    <template v-for="(cell,n) in placingNumber">
                      <li v-if="n>=11"><div style="text-align: center">{{cell}}</div>
                      </li>
                    </template>
                  </ul>
                </div>
                <br>
                <div id="top-line"></div>
                <div class="jKrOPS">筛选路珠</div>
                <div class="gdkl10">
                  <ul>
                    <template v-for="(item,i) in luzhuMenu">
                      <li v-if="i<4" :class="luzhuActive==item.value?'active':''" class="_0"
                          @click="selectLuzhuType(item.value)"><a>{{$t('gdkl10lz_'+item.title)}}</a></li>
                      <li v-if="i>=4" :class="luzhuActive==item.value?'active':''" class="_5"
                          @click="selectLuzhuType(item.value)"><a>{{$t('gdkl10lz_'+item.title)}}</a></li>
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
                                <div v-if="/^[0-9]\d*$/.test(cell)">{{cell}}</div>
                                <div v-else>{{$t(cell)}}</div>
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
      </div>
      <notice></notice>
    </div>
  </div>
</template>


<script>
  import {mapGetters, mapActions} from 'vuex'
  import MyHeader from '@/components/idc/layout/header'
  import notice from '@/components/notice'
  import Lottery from '@/axios/api-game.js'
  import Utils from '@/components/comm/Utils.js'
  export default {
    components: {
      MyHeader,
      notice,
    },
    data() {
      return {
        changlongList: [],
        showHideModel: true,
        luzhuList:{
          'no1':{'no1val':[],'no1oe':[],'no1ou':[],'no1':[],'no1wsou':[],'no1dtt':[],'no1heoe':[]},
          'no2':{'no2val':[],'no2oe':[],'no2ou':[],'no2':[],'no2wsou':[],'no2dtt':[],'no2heoe':[]},
          'no3':{'no3val':[],'no3oe':[],'no3ou':[],'no3':[],'no3wsou':[],'no3dtt':[],'no3heoe':[]},
          'no4':{'no4val':[],'no4oe':[],'no4ou':[],'no4':[],'no4wsou':[],'no4dtt':[],'no4heoe':[]},
          'no5':{'no5val':[],'no5oe':[],'no5ou':[],'no5':[],'no5wsou':[],'no5dtt':[],'no5heoe':[]},
          'no6':{'no6val':[],'no6oe':[],'no6ou':[],'no6':[],'no6wsou':[],'no6dtt':[],'no6heoe':[]},
          'no7':{'no7val':[],'no7oe':[],'no7ou':[],'no7':[],'no7wsou':[],'no7dtt':[],'no7heoe':[]},
          'no8':{'no8val':[],'no8oe':[],'no8ou':[],'no8':[],'no8wsou':[],'no8dtt':[],'no8heoe':[]},
          'sum':{'sumou':[],'sumoe':[],'sumws':[]}
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
          {value: 'no6', title: 'no6'},
          {value: 'no7', title: 'no7'},
          {value: 'no8', title: 'no8'},
        ],
        luzhuMenu: [
          {value: 'no1val', title: 'no1'},
          {value: 'no1ou', title: 'OU'},
          {value: 'no1oe', title: 'OE'},
          {value: 'no1dtt', title: 'no1dtt'},
          {value: 'no1heoe', title: 'no1heoe'},
          {value: 'no1wsou', title: 'no1wsou'},
          {value: 'sumou', title: 'sumou'},
          {value: 'sumoe', title: 'sumoe'},
          {value: 'sumws', title: 'sumws'},
        ]
      }
    },
    filters: {},

    computed: {
      ...mapGetters(['gameMenu','longDragonList','showMenu', 'gameId']),

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
        if (this.luzhuActive.indexOf('sum') == -1) {
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
      Lottery.getLotteryRoad(self.gameId).then(val => {
        if(val.code==10000 && typeof val.data != "undefined"){
          /*let arr = val.data.changlong;
          for (let obj of arr) {
            for (let key in obj) {
              let param = {'type': key.split('_')[0], 'oddsKey': key.split('_')[1], 'number': obj[key]};
              self.changlongList.push(param);
            }
          }*/
          let luzhuParams = {};

          for (let obj in val.data) {
            if (-1 != obj.indexOf('sum')) {
              luzhuParams[obj] = Utils.getArr(val.data[obj]);
            }
            if (obj.indexOf('no') != -1 && obj.split('no')[1].length > 2) {
              luzhuParams[obj] = Utils.getArr(val.data[obj]);
            } else if (-1 == obj.indexOf('sum')) {
              if (obj == 'no1') {
                self.placingNumber = val.data[obj];
              }
              luzhuParams[obj] = val.data[obj];
            }
          }

          for (let key in luzhuParams) {
            if (-1 != key.indexOf('sum')) {
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
            } else if (-1 != key.indexOf('no6')) {
              self.luzhuList.no6[key] = luzhuParams[key];
            } else if (-1 != key.indexOf('no7')) {
              self.luzhuList.no7[key] = luzhuParams[key];
            } else if (-1 != key.indexOf('no8')) {
              self.luzhuList.no8[key] = luzhuParams[key];
            } else if (-1 != key.indexOf('no9')) {
              self.luzhuList.no9[key] = luzhuParams[key];
            }

          }
        }

      });
    }
  }
</script>

<style scoped>
  table {
    border-collapse: collapse;
  }

  .block {
    padding: 8px 10px;
  }

  #top-line {
    width: 100%;
    height: 1px;
    line-height: 0;
    font-size: 0;
    overflow: hidden;
    text-align: center;
    z-index: 1;
    background-color: #cd3c29;
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
    background: linear-gradient(to left, #cd3c29 0%, #510505 100%);
    color: #eaeaea;
  }

  .button {
    background: #efeff4;
    font-size: 14px;
    border: 1px solid #cd3c29;
    color: #000;
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

  .otherpage {
    background: #fff !important;
    height: calc(100% - 4px) !important;
  }

  .ui-content {
    padding: 0px;
  }

  .ui-content {
    border-width: 0;
    overflow: auto;
    position: relative;
    height: calc(100% - 55px - 45px);
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

  .gdkl10top ul {
    clear: both;
    overflow: hidden;
    background: white;
    margin: 0px;
    padding: 0px;
  }

  .gdkl10top ul > li.active {
    background: linear-gradient(to left, #510505 0%, #cd3c29 100%);
  }

  .gdkl10top ul {
    border-bottom: 1px #ddd solid;
  }

  .gdkl10top ul > li {
    margin-bottom: -1px;
    margin-right: -1px;
    list-style-type: none;
    width: calc((100%/4) + 1px);
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

  .gdkl10top ul > li.active > a {
    color: white;
  }

  .gdkl10z ul {
    clear: both;
    margin: 0px;
    padding: 0px;
    overflow: hidden;
    background: white;
  }

  .gdkl10z ul > li {
    margin-top: -1px;
    margin-right: -1px;
    width: calc((100%/10) + 1px);
    float: left;
    height: 50px;
    box-sizing: border-box;
    padding-top: 12px;
    line-height: 24px;
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

  .gdkl10 ul {
    clear: both;
    overflow: hidden;
    background: white;
    margin: 0px;
    padding: 0px;
  }

  .gdkl10 ul > li._0, .gdkl10 ul > li._1, .gdkl10 ul > li._2 {
    width: calc((100%/4) + 1px);
  }

  .gdkl10 ul > li._3 {
    width: 40%;
  }

  .gdkl10 ul > li._4, .gdkl10 ul > li._5 {
    width: calc((100%/3) + 1px);
  }

  .gdkl10 ul > li.active {
    background: linear-gradient(to left, #510505 0%, #cd3c29 100%);
  }

  .gdkl10 ul > li {
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

  .gdkl10 ul > li.active > a {
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

