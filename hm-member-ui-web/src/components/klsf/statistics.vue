<template>
  <div id="resultPanel">
    <table class="tabTitle">
      <tbody>
      <tr>
        <template v-for="item in placingMenu">
          <th @click="selectPlacingActiveType(item.value)" :class="placingActive===item.value?'selected':''"><a
          >{{$t('gdkl10lz_'+item.title)}}</a></th>
        </template>
      </tr>
      </tbody>
    </table>
    <table class="ballTable">
      <tbody>
      <tr class="head">
        <th class="title">号码</th>
        <th class="b1">01</th>
        <th class="b2">02</th>
        <th class="b3">03</th>
        <th class="b4">04</th>
        <th class="b5">05</th>
        <th class="b6">06</th>
        <th class="b7">07</th>
        <th class="b8">08</th>
        <th class="b9">09</th>
        <th class="b10">10</th>
        <th class="b11">11</th>
        <th class="b12">12</th>
        <th class="b13">13</th>
        <th class="b14">14</th>
        <th class="b15">15</th>
        <th class="b16">16</th>
        <th class="b17">17</th>
        <th class="b18">18</th>
        <th class="b19">19</th>
        <th class="b20">20</th>
      </tr>
      <tr>
        <th>出 球 率</th>
        <template v-for="(cell,n) in placingNumber">
          <td :class="'b'+cell">{{cell}}
          </td>
        </template>
      </tr>
      </tbody>
    </table>
    <table class="tabTitle">
      <tbody>
      <tr>
        <template v-for="(item,i) in luzhuMenu">
          <th :class="luzhuActive==item.value?'selected':''" class="_0"
              @click="selectLuzhuType(item.value)"><a class="ball">{{$t('gdkl10lz_'+item.title)}}</a></th>
        </template>

      </tr>
      </tbody>
    </table>
    <table class="tabContents">
      <tbody>
      <template v-for="(qiuList,index) in luzhuList">
        <template v-for="(list,i) in qiuList">
          <tr v-show="luzhuActive==i">
            <template v-for="(item,j) in list">
              <template v-for="(obj,k) in item">
                <td>
                  <template v-for="(cell,n) in obj">
                    <div v-if="/^[0-9]\d*$/.test(cell)">{{cell}}</div>
                    <div v-else>{{$t(cell)}}</div>
                    <br/>
                  </template>
                </td>
              </template>
            </template>
          </tr>
        </template>
      </template>
      </tbody>
    </table>
  </div>
</template>

<script>
  import {mapGetters, mapActions} from 'vuex'
  import Utils from '@/components/comm/Utils.js'
  import {Indicator} from 'mint-ui'
  import to from "await-to-js";
  export default {
    name: "statistics",
    data() {
      return {
        changlongList:[],
        showHideModel:true,
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
    computed: {
      ...mapGetters(['gameMenu', 'showMenu', 'gameId']),
    },
    methods: {
      ...mapActions(['setLongDragonList']),
      selectModelFunction(flag) {
        this.showHideModel = flag;
      },
      selectPlacingActiveType(activeType) {
        this.placingActive = activeType;
        this.luzhuMenu[0].value = activeType + 'val';
        this.luzhuMenu[0].title = activeType;
        this.luzhuMenu[1].value = activeType + 'ou';
        this.luzhuMenu[2].value = activeType + 'oe';
        this.luzhuMenu[3].title = activeType + 'dtt';
        this.luzhuMenu[3].value = activeType + 'dtt';
        this.luzhuMenu[4].title = activeType + 'heoe';
        this.luzhuMenu[4].value = activeType + 'heoe';
        this.luzhuMenu[5].title = activeType + 'wsou';
        this.luzhuMenu[5].value = activeType + 'wsou';
        this.placingNumber = this.luzhuList[activeType][activeType];
        if (this.luzhuActive.indexOf('sum') == -1) {
          for (let i = 0; i < 6; i++) {
            if (this.luzhuMenu[i].value.indexOf(this.luzhuActive.substring(3, this.luzhuActive.length)) != -1) {
              this.luzhuActive = this.luzhuMenu[i].value;
            }
          }
        }

      },
      selectLuzhuType(activeType) {
        this.luzhuActive = activeType;
      },
      async init(){
        let self = this;
        self.changlongList = [];
        let [err,data] = await to(this.$api.Lottery.getLotteryRoad(self.gameId));
        if(data.success){
          let luzhuParams = {};
          for (let obj in data.data) {
            if (-1 != obj.indexOf('sum')) {
              luzhuParams[obj] = Utils.getArr(data.data[obj]);
            }
            if (obj.indexOf('no') != -1 && obj.split('no')[1].length > 2) {
              luzhuParams[obj] = Utils.getArr(data.data[obj]);
            } else if (-1 == obj.indexOf('sum')) {
              if (obj == 'no1') {
                self.placingNumber = data.data[obj];
              }
              luzhuParams[obj] = data.data[obj];
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

      }
    },
    mounted() {
    }
  }
</script>

<style scoped>

</style>
