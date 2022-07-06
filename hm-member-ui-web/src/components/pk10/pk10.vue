<template>
  <div id="lottery">
    <div class="">
      <game-info @closeMarketFun="closeMarketFun" ref="kjPage"></game-info>
      <div class="control n_anniu">
        <div class="buttons">
          <!--快捷下注-->
          <label class="checkdefault mlwit"><input type="checkbox" @change="quickBetStatusChange" v-model="pageQuickBetStatus" class="checkbox"><span
            class="color_lv bold">快捷下注</span></label>
          <!--end-->
          <label class="checkdefault"><input type="checkbox" v-model="pagePresetStatus"  @change="betAmtChange" class="checkbox"><span
            class="color_lv bold">预设</span></label>&nbsp;&nbsp;<label
          class="quickAmount"><span class="color_lv bold">金额</span> <input
          oninput="this.value = this.value.replace(/\D/g, '').replace(/^0{1,}/g, '');" maxlength="6" @keyup="inputSetAmt" type="text"
          v-model="pagePresetAmt"></label>
          <input type="button" ref="betButton" id="betButton" @click="bet()" value="确定" class="button">
          <input type="button" @click="resetBetAmt()" value="重置" class="button">
        </div>
      </div>
      <div v-show="categorySelect=='lm'" id="bet_panel" class="bet_panel input_panel">
        <table class="table_lm">
          <tbody>
          <template v-if="index==='gyh'" v-for="(list, index) in pk10Odds.lm">

            <tr class="head">
              <th colspan="12">{{$t(index)}}</th>
            </tr>

            <tr>
              <template v-for="(item,key) in list">
                <th @click="selectOdds(item)" :class="item.choose?'oddsSelect':''" class="name"
                    :title=" $t(item.oddsKey)"><input type="hidden" value="">{{$t(item.oddsKey)}}
                </th>
                <td @click="selectOdds(item)" :class="item.choose?'oddsSelect':''" class="odds">
                  {{betState&&item.status=='0'?oddsReturn(item):'封盘'}}
                </td>
                <td v-show="!pageQuickBetStatus" :class="item.choose?'oddsSelect':''" class="amount ha">
                  <input type="text" oninput="this.value = this.value.replace(/\D/g, '').replace(/^0{1,}/g, '');" maxlength="6"
                         v-model="item.betAmt" v-bind:id="item.oddsId" v-bind:ref="item.oddsId" @input="inputBetAmt(item)" @focus="focusFun(item)"
                         @blur="blurFun(item)" class="ba">

                  <div v-show="item.quickSumStatus" class="arrow_box arrowUp"
                       style="left:-21.5px;top:32px;line-height:normal">
                    <template v-for="(quickSum,index) in quickSumList">
                      <button v-if="quickSum>0" class="db" @click="setQuickSum(quickSum,item)" rel="10">
                        下注{{quickSum}}元
                      </button>

                    </template>
                    <button @click="stopQuickSum()" class="dbclose">停用</button>
                  </div>
                </td>
              </template>
            </tr>

          </template>

          </tbody>
        </table>

        <div class="split_panel">
          <template v-if="index!='gyh'" v-for="(list, index) in pk10Odds.lm">
            <table>
              <tbody>
              <tr class="head">
                <th colspan="3">{{$t(index)}}</th>
              </tr>
              <template v-for="(item,key) in list">
                <tr >
                  <th @click="selectOdds(item)" :class="item.choose?'oddsSelect':''" class="name"
                      :title="$t(item.oddsKey)">
                    <input type="hidden" value="DX">{{$t(item.oddsKey)}}
                  </th>
                  <td @click="selectOdds(item)" :class="item.choose?'oddsSelect':''" class="odds">
                    {{betState&&item.status=='0'?oddsReturn(item):'封盘'}}
                  </td>
                  <td v-show="!pageQuickBetStatus" :class="item.choose?'oddsSelect':''" class="amount ha">
                    <input type="text" oninput="this.value = this.value.replace(/\D/g, '').replace(/^0{1,}/g, '');" maxlength="6"
                           v-model="item.betAmt" v-bind:id="item.oddsId" v-bind:ref="item.oddsId" @input="inputBetAmt(item)" @focus="focusFun(item)"
                           @blur="blurFun(item)" class="ba">
                    <div v-show="item.quickSumStatus" class="arrow_box arrowUp"
                         style="left:-21.5px;top:32px;line-height:normal">
                      <template v-for="(quickSum,index) in quickSumList">
                        <button v-if="quickSum>0" class="db" @click="setQuickSum(quickSum,item)" rel="10">
                          下注{{quickSum}}元
                        </button>

                      </template>
                      <button @click="stopQuickSum()" class="dbclose">停用</button>
                    </div>
                  </td>
                </tr>
              </template>

              </tbody>
            </table>
          </template>
        </div>
      </div>

      <div v-show="categorySelect=='gyjzh'" class="bet_panel input_panel">
        <table class="table_lm">
          <tbody>
          <tr class="head">
            <th colspan="12">{{$t('gyjzh')}}</th>
          </tr>

          </tbody>
        </table>

        <div class="split_panel winauto">
          <template v-for="(item,key) in pk10Odds.firstAddSecond">
            <template v-if="key==17">
              <table style="width:193px;margin: 0px -1px -1px 0px;">
                <tbody>
                <tr>
                  <th style="width:50px;" class="name empty"></th>

                  <td class="odds empty"></td>

                  <td class="amount ha"></td>

                </tr>
                </tbody>
              </table>
              <table style="width:193px;margin: 0px -1px -1px 0px;">
                <tbody>
                <tr>
                  <th style="width:50px;" class="name empty"></th>

                  <td class="odds empty"></td>

                  <td class="amount ha"></td>

                </tr>
                </tbody>
              </table>
              <table style="width:193px;margin: 0px -1px -1px 0px;">
                <tbody>
                <tr>
                  <th style="width:50px;" class="name empty"></th>

                  <td class="odds empty"></td>

                  <td class="amount ha"></td>

                </tr>
                </tbody>
              </table>
            </template>
            <table style="width:193px;margin: 0px -1px -1px 0px;">

              <tbody>

              <tr>
                <th style="width:50px;" @click="selectOdds(item)" :class="item.choose?'oddsSelect':''" class="name"
                    :title="$t('gyh'+item.oddsKey)">
                  <input type="hidden" value="DX">{{$t('gyh'+item.oddsKey)}}
                </th>
                <td @click="selectOdds(item)" :class="item.choose?'oddsSelect':''" class="odds">
                  {{betState&&item.status=='0'?oddsReturn(item):'封盘'}}
                </td>
                <td v-show="!pageQuickBetStatus" :class="item.choose?'oddsSelect':''" class="amount ha">
                  <input type="text" oninput="this.value = this.value.replace(/\D/g, '').replace(/^0{1,}/g, '');" maxlength="6"
                         v-model="item.betAmt" v-bind:id="item.oddsId" v-bind:ref="item.oddsId" @input="inputBetAmt(item)" @focus="focusFun(item)"
                         @blur="blurFun(item)" class="ba">
                  <div v-show="item.quickSumStatus" class="arrow_box arrowUp"
                       style="left:-21.5px;top:32px;line-height:normal">
                    <template v-for="(quickSum,index) in quickSumList">
                      <button v-if="quickSum>0" class="db" @click="setQuickSum(quickSum,item)" rel="10">
                        下注{{quickSum}}元
                      </button>

                    </template>
                    <button @click="stopQuickSum()" class="dbclose">停用</button>
                  </div>
                </td>
              </tr>


              </tbody>

            </table>
          </template>
        </div>
      </div>

      <div v-show="categorySelect=='ydsxz'" class="bet_panel input_panel">
        <div :class="cssResult">
          <template v-for="(list, index) in pk10Odds.oneToFive">
            <table>
              <tbody>

              <tr class="head">
                <th colspan="3">{{$t('ydw'+index)}}</th>
              </tr>
              <tr @click="allSearchFun(list,index)"  v-show="pageQuickBetStatus">
                <td :class="!allSearchList[index]?'':'oddsSelect'" colspan="3">
                  <label class="checkdefault mlwit">
                    <input type="checkbox" :disabled="betState?false:true" v-model="allSearchList[index]" class="checkbox">
                    <span @click="allSearchFun(list,index)" style="cursor:pointer" class="color_lv bold">{{betState?'全选':'封盘'}}</span>
                  </label>
                </td>
              </tr>
              <template v-for="(item,key) in list">
                <tr v-if="item.categoryKey!='lm'">
                  <th @click="selectOdds(item)" :class="item.choose?'oddsSelect':''" class="name"
                      :title="$t(item.oddsKey)">
                    <input type="hidden" value="DX">
                    <span :class="'b'+item.oddsKey">{{item.oddsKey}}</span>
                  </th>
                  <td @click="selectOdds(item)" :class="item.choose?'oddsSelect':''" class="odds">
                    {{betState&&item.status=='0'?oddsReturn(item):'封盘'}}
                  </td>
                  <td v-show="!pageQuickBetStatus" :class="item.choose?'oddsSelect':''" class="amount ha">
                    <input type="text" oninput="this.value = this.value.replace(/\D/g, '').replace(/^0{1,}/g, '');" maxlength="6"
                           v-model="item.betAmt" v-bind:id="item.oddsId" v-bind:ref="item.oddsId" @input="inputBetAmt(item)" @focus="focusFun(item)"
                           @blur="blurFun(item)" class="ba">

                    <div v-show="item.quickSumStatus" class="arrow_box arrowUp"
                         style="left:-21.5px;top:32px;line-height:normal">
                      <template v-for="(quickSum,index) in quickSumList">
                        <button v-if="quickSum>0" class="db" @click="setQuickSum(quickSum,item)" rel="10">
                          下注{{quickSum}}元
                        </button>

                      </template>
                      <button @click="stopQuickSum()" class="dbclose">停用</button>
                    </div>
                  </td>
                </tr>
              </template>

              </tbody>
            </table>
          </template>
        </div>

        <div :class="cssResult">
          <template v-for="(list, index) in pk10Odds.sixToTen">
            <table>
              <tr class="head">
                <th colspan="3">{{$t('lds'+index)}}</th>
              </tr>
              <tr @click="allSearchFun(list,index+5)"  v-show="pageQuickBetStatus">
                <td :class="!allSearchList[index+5]?'':'oddsSelect'" colspan="3">
                  <label class="checkdefault mlwit">
                    <input v-model="allSearchList[index+5]" type="checkbox" :disabled="betState?false:true" class="checkbox">
                    <span @click="allSearchFun(list,index+5)" style="cursor:pointer" class="color_lv bold">{{betState?'全选':'封盘'}}</span>
                  </label>
                </td>
              </tr>
              <tbody>
              <template v-for="(item,key) in list">
                <tr v-if="item.categoryKey!='lm'">
                  <th @click="selectOdds(item)" :class="item.choose?'oddsSelect':''" class="name"
                      :title="$t(item.oddsKey)">
                    <input type="hidden" value="DX">
                    <span :class="'b'+item.oddsKey">{{item.oddsKey}}</span>
                  </th>
                  <td @click="selectOdds(item)" :class="item.choose?'oddsSelect':''" class="odds">
                    {{betState&&item.status=='0'?oddsReturn(item):'封盘'}}
                  </td>
                  <td v-show="!pageQuickBetStatus" :class="item.choose?'oddsSelect':''" class="amount ha">
                    <input type="text" oninput="this.value = this.value.replace(/\D/g, '').replace(/^0{1,}/g, '');" maxlength="6"
                           v-model="item.betAmt" v-bind:id="item.oddsId" v-bind:ref="item.oddsId" @input="inputBetAmt(item)" @focus="focusFun(item)"
                           @blur="blurFun(item)" class="ba">
                    <div v-show="item.quickSumStatus" class="arrow_box arrowUp"
                         style="left:-21.5px;top:32px;line-height:normal">
                      <template v-for="(quickSum,index) in quickSumList">
                        <button v-if="quickSum>0" class="db" @click="setQuickSum(quickSum,item)" rel="10">
                          下注{{quickSum}}元
                        </button>

                      </template>
                      <button @click="stopQuickSum()" class="dbclose">停用</button>
                    </div>
                  </td>
                </tr>
              </template>

              </tbody>
            </table>
          </template>
        </div>
      </div>


      <div class="control bcontrol">
        <div class="lefts" style="">已经选中 <span id="betcount"></span>{{selectList.length}}注</div>
        <div class="buttons">
          <input type="button" class="button2" value="快选金额" @click="quickSum">
          <label class="checkdefault"><input type="checkbox" v-model="pagePresetStatus" @change="betAmtChange" class="checkbox"><span
            class="color_lv bold">预设</span></label>&nbsp;&nbsp;<label
          id="quickAmount" class="quickAmount"><span class="color_lv bold">金额</span> <input type="text" maxlength="6"
                                                                                            oninput="this.value = this.value.replace(/\D/g, '').replace(/^0{1,}/g, '');"
                                                                                            @keyup="inputSetAmt"
                                                                                            v-model="pagePresetAmt"></label>
          <input type="button" class="button" value="确定" @click="bet()"><input type="button" @click="resetBetAmt"
                                                                               class="button" value="重置"
        >
        </div>
      </div>

      <statistics ref="statisticsPage"></statistics>

    </div>
  </div>

</template>

<script>
  import gameInfo from '@/components/layout/gameInfo'
  import {mapActions, mapGetters} from 'vuex'
  import statistics from '@/components/pk10/statistics'
  import Utils from '@/components/comm/Utils.js'
  import {Indicator} from 'mint-ui'



  export default {
    components: {
      gameInfo,
      statistics
    },
    name: "frame",
    data() {
      return {
        pagePresetAmt:'',
        pagePresetStatus:false,
        pageQuickBetStatus:false,
        lotteryPageFlag:true,
        allSearchList:[false,false,false,false,false,false,false,false,false,false],
        inputAmtTimeOut:null
      }
    },
    created() {

    },
    mounted() {
      let self = this;
      if(!self.categorySelect){
        self.setCategorySelect('ydsxz');
      }
      self.pagePresetStatus = self.betAmtStatus;
      self.pagePresetAmt = self.betAmtPreSet;
      self.pageQuickBetStatus = self.quickBetStatus;
      self.$nextTick(function(){
        self.initSelectList(true);
      });
      document.onkeydown=function(event){
        var code = event.keyCode;
        if(code ==13){ //这是键盘的enter监听事件
          self.bet();
        }
      }
    },
    computed: {
      ...mapGetters(['promptInformation','quickBetStatus','betAmtPreSet','betAmtStatus','pk10Odds', 'quickSumStatus', 'quickSumList', 'betState', 'betGameNo', 'noteLists', 'gameInfo', 'selectList', 'gameId', 'categorySelect','userOddsCljps','userOddsCloses','userOddsJumps','userOddsNows','userOddss']),
      cssResult(){
        if(this.gameId==101 || this.gameId == 102 || this.gameId == 103 || this.gameId == 104 || this.gameId == 105
          || this.gameId == 106 || this.gameId == 108 || this.gameId == 110 || this.gameId == 111 || this.gameId == 112){
          return 'split_panel pk10';
        }else{
          return 'split_panel smn';
        }
      },

      oddsReturn(odds){
        let self = this;
        return (odds)=>{
          let {categoryId,oddsId} = odds;

          let now = self.userOddsNows[oddsId];
          let o1 = now?self.userOddsNows[oddsId].reduce((pre,cur)=>pre+cur,0):0;
          let jump = self.userOddsJumps[oddsId];
          let o2 = jump?self.userOddsJumps[oddsId].reduce((pre,cur)=>pre+cur,0):0;
          let cljp = self.userOddsCljps[oddsId];
          let o3 = cljp?self.userOddsCljps[oddsId].reduce((pre,cur)=>pre+cur,0):0;
          let close = self.userOddsCloses[oddsId];
          let o4 = close?self.userOddsCloses[oddsId].reduce((pre,cur)=>pre+cur,0):0;
          let userOdds = self.userOddss[categoryId];
          let o5 = userOdds?self.userOddss[categoryId].reduce((pre,cur)=>pre+cur,0):0;
          self.$set(odds,'odds',Math.round((o1+o2+o3+o4+o5) * 100000) / 100000);
          return Math.round((o1+o2+o3+o4+o5) * 100000) / 100000>0?Math.round((o1+o2+o3+o4+o5) * 100000) / 100000:0;
        }
      },
    },
    watch:{
      categorySelect(){
        let self = this;
        self.setSelectList(null);
      },
      pagePresetAmt() {
        let self = this;
        if (self.pagePresetStatus) {
          self.setBetAmtPreSet(self.pagePresetAmt);
        }
      },
    },
    methods: {
      ...mapActions(['setPromptInformation','setQuickBetStatus','setBetAmtPreSet','setBetAmtStatus', 'setQuickSumList', 'setBetGameNo', 'setSelectList', 'setCategorySelect']),
      allSearchFun(item,index){
        let self = this;
        if(!this.betState){
          return;
        }
        self.allSearchList[index] = !self.allSearchList[index];
        if(self.allSearchList[index]){
          item.forEach(val=>{
            if(val.categoryKey!='lm' && val.status=='0'){
              if(!val.check && !val.choose){
                self.selectOdds(val);
              }
            }
          })
        }else{
          item.forEach(val=>{
            if(val.categoryKey!='lm' && val.status=='0'){
              self.cancelChooseOdds(val);
            }
          })
        }

      },
      inputBetAmt(item,event){
        let self = this;
        clearTimeout(self.inputAmtTimeOut);
        if (!this.betState || item.status === '1') {
          return;
        }
        if (item.betAmt && item.betAmt > 0) {
          setTimeout(()=>{
            self.chooseOdds(item);
          },800)
        } else {
          this.cancelChooseOdds(item);
        }
        setTimeout(function () {
          self.$set(item, 'quickSumStatus', false);
        }, 300);
      },
      focusFun(item,event) {
        if (this.quickSumStatus && this.betState) {
          this.$set(item, 'quickSumStatus', true);
        }
      },
      closeMarketFun(){
        if(!this.betAmtStatus){
          this.setBetAmtPreSet('');
          this.pagePresetAmt = '';
        }
        this.initSelectList();
      },
      betAmtChange(){
        this.setBetAmtStatus(this.pagePresetStatus);
        if(this.betAmtStatus){
          this.setBetAmtPreSet(this.pagePresetAmt);
        }else{
          this.setBetAmtPreSet('');
        }
      },
      quickBetStatusChange(){
        this.setQuickBetStatus(this.pageQuickBetStatus);
        let self = this;
        for (let item in self.pk10Odds.lm) {
          self.pk10Odds.lm[item].forEach((obj) => {
            self.$delete(obj, "choose");
            self.$delete(obj, "betAmt");
            self.$set(obj, "quickSumStatus", false);
          });
        }


        self.pk10Odds.firstAddSecond.forEach((item) => {
          self.$delete(item, "choose");
          self.$delete(item, "betAmt");
          self.$set(item, "quickSumStatus", false);
        });
        self.pk10Odds.oneToFive.forEach((item) => {
          self.$delete(item, "showHide");
          item.forEach((obj) => {
            self.$delete(obj, "choose");
            self.$delete(obj, "betAmt");
            self.$set(obj, "quickSumStatus", false);
          })
        });
        self.pk10Odds.sixToTen.forEach((item) => {
          self.$delete(item, "showHide");
          item.forEach((obj) => {
            self.$delete(obj, "choose");
            self.$delete(obj, "betAmt");
            self.$set(obj, "quickSumStatus", false);
          })
        });
        self.allSearchList=[false,false,false,false,false,false,false,false,false,false];
        self.setSelectList(null);
      },
      stopQuickSum() {
        let self = this;
        self.$messageBox.close();
        self.$messageBox({$type:'confirm',message:'确认停用吗？',title:'提示',closeOnClickModal:false,showCancelButton:true}).then(action => {
          if (Object.is(action, 'confirm')) {
            self.setQuickSumList({'list': self.quickSumList, 'status': 0});
            self.setPromptInformation('停用成功!').then(

            ).catch(error => {

            });
          }
        }).catch(error => {

        });
      },
      blurFun(item) {
        let self = this;
        if (!this.betState || item.status === '1') {
          return;
        }
        if (item.betAmt && item.betAmt > 0) {
          this.chooseOdds(item);
        } else {
          this.cancelChooseOdds(item);
        }
        setTimeout(function () {
          self.$set(item, 'quickSumStatus', false);
        }, 300);

      },
      setQuickSum(quickSum, item) {
        item.betAmt = parseInt(quickSum);
        this.chooseOdds(item);
        document.getElementById(item.oddsId).focus();
      },
      inputSetAmt() {
        let self = this;
        let betAmt = parseInt(this.pagePresetAmt);
        if (betAmt) {
          self.selectList.forEach((obj) => {
            self.$set(obj, "betAmt", betAmt);
          });
        } else {
          self.selectList.forEach((obj) => {
            self.$delete(obj, "betAmt");
          });
        }

      },
      quickSum() {
        this.$emit('quickSum');
      },

      bet() {
        if(this.pageQuickBetStatus && !this.pagePresetAmt && this.selectList.length>0){
          this.setPromptInformation('请输入金额!');
          return;
        }
        this.$emit('bet');
      },
      resetBetAmt() {
        let self = this;
        self.pagePresetStatus=false;
        self.pagePresetAmt = '';
        self.setBetAmtPreSet('');
        self.setBetAmtStatus(false);
        self.initSelectList(true);
      },
      initSelectList(flag) {

        let self = this;

        setTimeout(()=>{
          if (flag) {
            //this.$refs.kjPage.closeAllTime();
            this.$refs.kjPage.infoObtain();
            self.$refs.statisticsPage.init();
          }
        },500)
        if(!this.betAmtStatus){
          this.pagePresetAmt = '';
          this.setBetAmtPreSet('');
        }
        if (!self.pk10Odds) {
          console.log('pk10Odds is undefined');
          return;
        }
        if (!self.pk10Odds.lm) {
          console.log('lm is undefined');
          return;
        }
        if (!self.pk10Odds.firstAddSecond) {
          console.log('firstAddSecond is undefined');
          return;
        }
        if (!self.pk10Odds.oneToFive) {
          console.log('oneToFive is undefined');
          return;
        }
        if (!self.pk10Odds.sixToTen) {
          console.log('sixToTen is undefined');
          return;
        }
        for (let item in self.pk10Odds.lm) {
          self.pk10Odds.lm[item].forEach((obj) => {
            self.$delete(obj, "choose");
            self.$delete(obj, "betAmt");
            self.$set(obj, "quickSumStatus", false);
          });
        }


        self.pk10Odds.firstAddSecond.forEach((item) => {
          self.$delete(item, "choose");
          self.$delete(item, "betAmt");
          self.$set(item, "quickSumStatus", false);
        });
        self.pk10Odds.oneToFive.forEach((item) => {
          self.$delete(item, "showHide");
          item.forEach((obj) => {
            self.$delete(obj, "choose");
            self.$delete(obj, "betAmt");
            self.$set(obj, "quickSumStatus", false);
          })
        });
        self.pk10Odds.sixToTen.forEach((item) => {
          self.$delete(item, "showHide");
          item.forEach((obj) => {
            self.$delete(obj, "choose");
            self.$delete(obj, "betAmt");
            self.$set(obj, "quickSumStatus", false);
          })
        });
        self.allSearchList=[false,false,false,false,false,false,false,false,false,false];

        self.setSelectList(null);

      },
      selectOdds(item) {
        if (!this.betState || item.status === '1') {
          return;
        }
        this.$set(item, 'choose', !item.choose);
        if (item.choose) {
          this.chooseOdds(item);
          /*this.$refs.betButton.focus();
          document.getElementById('betButton').focus();*/

        } else {
          this.cancelChooseOdds(item);
        }
      },
      chooseOdds(item) {//选中陪率
        if (this.betGameNo !== this.gameInfo.gameNo) {
          this.setBetGameNo(this.gameInfo.gameNo);
        }
        if (this.pagePresetAmt && !item.betAmt) {
          this.$set(item, 'betAmt', this.pagePresetAmt);
        }
        this.$set(item, 'choose', true);
        this.$set(item, 'check', true);
        let index = this.findChoose(item);
        if (index === -1) {
          this.setSelectList(item);
        }
      },
      findChoose(item) {//选取的陪率在列表中的位置
        return this.selectList.findIndex(function (value, index, arr) {
          if (value.oddsId == item.oddsId) {
            value.betAmt = item.betAmt;
          }
          return value.oddsId === this.oddsId;
        }, {oddsId: item.oddsId});
      },
      cancelChooseOdds(item) {//取消选中陪率
        this.$set(item, 'choose', false);
        this.$set(item, 'check', false);
        this.$delete(item, 'betAmt');
        let index = this.findChoose(item);
        if (index !== -1) {
          this.selectList.splice(index, 1);
        }
      },
    }
  }
</script>

<style scoped>
  .oddsSelect {
    background-color: #fff100 !important;
  }
</style>
