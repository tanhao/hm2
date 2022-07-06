<template>
  <div id="resultPanel">
    <table class="tabTitle">
      <tbody>
      <tr>
        <template v-for="item in placingMenu">
          <th :class="placingActive===item.value?'selected':''" @click="selectPlacingActiveType(item.value)"><a
            href="#">{{$t('ssclz_'+item.title)}}</a></th>
        </template>
      </tr>
      </tbody>
    </table>
    <table class="ballTable tab_title02">
      <tbody>
      <tr class="head">
        <th class="b0">0</th>
        <th class="b1">1</th>
        <th class="b2">2</th>
        <th class="b3">3</th>
        <th class="b4">4</th>
        <th class="b5">5</th>
        <th class="b6">6</th>
        <th class="b7">7</th>
        <th class="b8">8</th>
        <th class="b9">9</th>
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
                                                                                                     class="ball">{{$t('ssclz_'+item.title)}}</a>
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
                      <template v-if="typeof cell == 'number'">{{cell}}</template>
                      <template v-else>{{$t(cell)}}</template>
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
      },
      async init() {
        let self = this;
        self.changlongList = [];
        let [err,data] = await to(this.$api.Lottery.getLotteryRoad(self.gameId));
        if(data.success){
          let luzhuParams = {};

          for (let obj in data.data) {
            if (-1 != obj.indexOf('dtt') || -1 != obj.indexOf('sum')) {
              luzhuParams[obj] = Utils.getArr(data.data[obj]);
            }
            if (obj.indexOf('no') != -1 && obj.split('no')[1].length > 2) {
              luzhuParams[obj] = Utils.getArr(data.data[obj]);
            } else if (-1 == obj.indexOf('dtt') && -1 == obj.indexOf('sum')) {
              if (obj == 'no1') {
                self.placingNumber = data.data[obj];
              }
              luzhuParams[obj] = data.data[obj];
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
        }
      },
    mounted() {
    }
  }
</script>

<style scoped>

</style>
