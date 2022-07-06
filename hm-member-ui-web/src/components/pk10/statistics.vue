<template>
  <div id="resultPanel">
    <table class="tabTitle">
      <tbody>
      <tr>
        <template v-for="item in placingMenu">
          <th :class="placingActive===item.value?'selected':''" @click="selectPlacingActiveType(item.value)"><a
            href="#">{{$t(item.title)}}</a></th>
        </template>
      </tr>
      </tbody>
    </table>
    <table class="ballTable tab_title02">
      <tbody>
      <tr class="head">
        <th class="b1">1</th>
        <th class="b2">2</th>
        <th class="b3">3</th>
        <th class="b4">4</th>
        <th class="b5">5</th>
        <th class="b6">6</th>
        <th class="b7">7</th>
        <th class="b8">8</th>
        <th class="b9">9</th>
        <th class="b10">10</th>
      </tr>
      <tr>
        <template v-for="(cell,n) in placingNumber">
          <td :class="'b'+cell">{{cell}}</td>
        </template>
      </tr>
      </tbody>
    </table>
    <table class="tabTitle">
      <tbody>
      <tr>
        <template v-for="(item,i) in luzhuMenu">
          <th :class="luzhuActive==item.value?'selected':''" @click="selectLuzhuType(item.value)"><a href="#"
                                                                                                     class="ball">{{$t(item.title)}}</a>
          </th>
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
                    {{$t(cell)}}<br/>
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
  import to from "await-to-js";
  export default {
    name: "statistics",
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
          'no6': {'no6val': [], 'no6oe': [], 'no6ou': [], 'no6': []},
          'no7': {'no7val': [], 'no7oe': [], 'no7ou': [], 'no7': []},
          'no8': {'no8val': [], 'no8oe': [], 'no8ou': [], 'no8': []},
          'no9': {'no9val': [], 'no9oe': [], 'no9ou': [], 'no9': []},
          'no10': {'no10val': [], 'no10oe': [], 'no10ou': [], 'no10': []},
          'gyh': {'gyh': [], 'gyhou': [], 'gyhoe': []}
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
          {value: 'no9', title: 'no9'},
          {value: 'no10', title: 'no10'},
        ],
        luzhuMenu: [
          {value: 'no1val', title: 'no1'},
          {value: 'no1ou', title: 'OU'},
          {value: 'no1oe', title: 'OE'},
          {value: 'gyh', title: 'gyjh'},
          {value: 'gyhou', title: 'gyjhou'},
          {value: 'gyhoe', title: 'gyjhoe'},
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
        this.placingNumber = this.luzhuList[activeType][activeType];
        if (this.luzhuActive.indexOf('gyh') == -1) {
          if (this.luzhuActive.indexOf('ou') != -1) {
            this.luzhuActive = activeType + 'ou';
          } else if (this.luzhuActive.indexOf('oe') != -1) {
            this.luzhuActive = activeType + 'oe';
          } else if (this.luzhuActive.indexOf('val') != -1) {
            this.luzhuActive = activeType + 'val';
          }
        }

      },
      selectLuzhuType(activeType) {
        this.luzhuActive = activeType;
      },
      async init() {
        let self = this;
        self.changlongList = [];
        let [err,data] = await to(this.$api.Lottery.getLotteryRoad(self.gameId));
        if(data.success){
          let luzhuParams = {};

          for (let obj in data.data) {
            if (-1 != obj.indexOf('gyh')) {
              luzhuParams[obj] = Utils.getArr(data.data[obj]);
            }
            if (obj.indexOf('no') != -1 && obj.split('no')[1].length > 2) {
              luzhuParams[obj] = Utils.getArr(data.data[obj]);
            } else if (-1 == obj.indexOf('gyh')) {
              if (obj == 'no1') {
                self.placingNumber = data.data[obj];
              }
              luzhuParams[obj] = data.data[obj];
            }
          }

          for (let key in luzhuParams) {
            if (key == 'no10' || key == 'no10val' || key == 'no10oe' || key == 'no10ou') {
              self.luzhuList.no10[key] = luzhuParams[key];
            } else {
              if (-1 != key.indexOf('gyh')) {
                self.luzhuList.gyh[key] = luzhuParams[key];
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

      }
    },
    mounted() {
    }
  }
</script>

<style scoped>

</style>
