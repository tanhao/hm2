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

      <div id="bet_panel" class="bet_panel input_panel">
        <!--主势盘-->
        <div class="split_panel1 pcdd">
          <!--开始4列-->
          <template v-for="(num,i) in 4">
            <table style="margin-bottom: 10px;" >
              <tbody>
              <tr class="head">
                <th>和值</th>
                <th>赔率</th>
                <th v-show="!pageQuickBetStatus" class="ha">金额</th>
              </tr>
              <template v-for="(item,index) in pcddOdds.hezhi">
                <tr v-if="index>=i*7 && index<num*7">
                  <th @click="selectOdds(item)" :class="item.choose?'oddsSelect':''" class="name"
                      :title="'和值 '+ item.oddsKey"><input type="hidden" value=""><span
                          :class="'b'+item.oddsKey">{{item.oddsKey}}</span>
                  </th>
                  <td @click="selectOdds(item)" :class="item.choose?'oddsSelect':''" class="odds">
                    {{betState&&item.status=='0'?oddsReturn(item):'封盘'}}
                  </td>
                  <td v-show="!pageQuickBetStatus" :class="item.choose?'oddsSelect':''" class="amount ha">
                    <input type="text" oninput="this.value = this.value.replace(/[^0-9]/g, '');" maxlength="6" @click="selectOdds(item)"
                           v-model="item.betAmt" @focus="focusFun(item)"
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
              <!--开始7列-->
              </tbody>
            </table>
          </template>
        </div>

        <!--两面-->
        <table class="table_lm">
          <tbody>
          <tr class="head">
            <th colspan="16">两面</th>
          </tr>
          <!--开始2行-->
          <template v-for="(num,i) in 2">
            <tr >
              <template v-for="(item,key) in pcddOdds.lm">
                <template v-if="key>=i*5 && key<num*5">
                  <th @click="selectOdds(item)" :class="item.choose?'oddsSelect':''" class="name"
                      :title="$t(item.oddsKey)"><input type="hidden" value="">{{$t(item.oddsKey)}}
                  </th>
                  <td @click="selectOdds(item)" :class="item.choose?'oddsSelect':''" class="odds">
                    {{betState&&item.status=='0'?oddsReturn(item):'封盘'}}
                  </td>
                  <td v-show="!pageQuickBetStatus" :class="item.choose?'oddsSelect':''" class="amount ha">
                    <input type="text" oninput="this.value = this.value.replace(/[^0-9]/g, '');" maxlength="6" @click="selectOdds(item)"
                           v-model="item.betAmt" @focus="focusFun(item)"
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
              </template>
            </tr>
          </template>

          </tbody>
        </table>

        <!--色波/豹子/对子/顺子-->
        <table class="table_lm" style="margin-top: 10px;">
          <tbody>
          <tr class="head">
            <th colspan="9">色波/豹子/对子/顺子</th>
          </tr>
          <tr>
            <template v-for="(item,index) in pcddOdds.sebo">
              <th @click="selectOdds(item)" :class="cssResultFun(item)"
                  :title="$t(item.oddsKey)"><input type="hidden" value="">{{$t(item.oddsKey)}}
              </th>
              <td @click="selectOdds(item)" :class="item.choose?'oddsSelect':''" class="odds">
                {{betState&&item.status=='0'?oddsReturn(item):'封盘'}}
              </td>
              <td v-show="!pageQuickBetStatus" :class="item.choose?'oddsSelect':''" class="amount ha">
                <input type="text" oninput="this.value = this.value.replace(/[^0-9]/g, '');" maxlength="6" @click="selectOdds(item)"
                       v-model="item.betAmt" @focus="focusFun(item)"
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
          <tr>
            <template v-for="(item,index) in pcddOdds.baozi">
              <th @click="selectOdds(item)" :class="item.choose?'oddsSelect':''" class="name"
                  :title="$t(item.oddsKey)"><input type="hidden" value="">{{$t(item.oddsKey)}}
              </th>
              <td @click="selectOdds(item)" :class="item.choose?'oddsSelect':''" class="odds">
                {{betState&&item.status=='0'?oddsReturn(item):'封盘'}}
              </td>
              <td v-show="!pageQuickBetStatus" :class="item.choose?'oddsSelect':''" class="amount ha">
                <input type="text" oninput="this.value = this.value.replace(/[^0-9]/g, '');" maxlength="6" @click="selectOdds(item)"
                       v-model="item.betAmt" @focus="focusFun(item)"
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
            <template v-for="(item,index) in pcddOdds.duizi">
              <th @click="selectOdds(item)" :class="item.choose?'oddsSelect':''" class="name"
                  :title="$t(item.oddsKey)"><input type="hidden" value="">{{$t(item.oddsKey)}}
              </th>
              <td @click="selectOdds(item)" :class="item.choose?'oddsSelect':''" class="odds">
                {{betState&&item.status=='0'?oddsReturn(item):'封盘'}}
              </td>
              <td v-show="!pageQuickBetStatus" :class="item.choose?'oddsSelect':''" class="amount ha">
                <input type="text" oninput="this.value = this.value.replace(/[^0-9]/g, '');" maxlength="6" @click="selectOdds(item)"
                       v-model="item.betAmt" @focus="focusFun(item)"
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
            <template v-for="(item,index) in pcddOdds.shunzi">
              <th @click="selectOdds(item)" :class="item.choose?'oddsSelect':''" class="name"
                  :title="$t(item.oddsKey)"><input type="hidden" value="">{{$t(item.oddsKey)}}
              </th>
              <td @click="selectOdds(item)" :class="item.choose?'oddsSelect':''" class="odds">
                {{betState&&item.status=='0'?oddsReturn(item):'封盘'}}
              </td>
              <td v-show="!pageQuickBetStatus" :class="item.choose?'oddsSelect':''" class="amount ha">
                <input type="text" oninput="this.value = this.value.replace(/[^0-9]/g, '');" maxlength="6" @click="selectOdds(item)"
                       v-model="item.betAmt" @focus="focusFun(item)"
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
          </tbody>
        </table>



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

<!--      <statistics ref="statisticsPage"></statistics>-->

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
      ...mapGetters(['pcddOdds','quickBetStatus','betAmtPreSet','betAmtStatus', 'quickSumStatus', 'quickSumList', 'betState', 'betGameNo', 'noteLists', 'gameInfo', 'selectList', 'gameId', 'categorySelect','categorySelect','userOddsCljps','userOddsCloses','userOddsJumps','userOddsNows','userOddss']),
      cssResult(){
        /*if(this.gameId==101 || this.gameId == 102 || this.gameId == 103 || this.gameId == 104 || this.gameId == 105
                || this.gameId == 106 || this.gameId == 108 || this.gameId == 110 || this.gameId == 111 || this.gameId == 112){
          return 'split_panel pk10';
        }else{
          return 'split_panel smn';
        }*/
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
      gameId(){

      },
      categorySelect(){
        let self = this;
        self.setSelectList(null);
      },
      betState(){
        let self = this;
        if(!this.betState){
          self.selectList.forEach(val=>{
            self.$delete(val,"check");
            self.$delete(val,"choose");
            self.$delete(val,"betAmt");
          })
        }
      },
      pagePresetAmt() {
        let self = this;
        if (self.pagePresetStatus) {
          self.setBetAmtPreSet(self.pagePresetAmt);
        }
      },
    },
    methods:{
      ...mapActions(['setPromptInformation','setQuickBetStatus','setBetAmtPreSet','setBetAmtStatus', 'setQuickSumList', 'setBetGameNo', 'setSelectList', 'setCategorySelect','setSelectListFast']),
      focusFun(item) {
        if (this.quickSumStatus && this.betState) {
          this.$set(item, 'quickSumStatus', true);
        }
      },
      closeMarketFun(){
        if(!this.betAmtStatus){
          this.pagePresetAmt = '' ;
          this.setBetAmtPreSet('');
        }
      },
      betAmtChange(){
        this.setBetAmtStatus(this.pagePresetStatus);
        if(this.betAmtStatus){
          this.setBetAmtPreSet(this.pagePresetAmt);
        }else{
          this.setBetAmtPreSet('');
        }
      },
      quickBetStatusChange() {
        this.setQuickBetStatus(this.pageQuickBetStatus);
        this.setSelectList(null);
      },
      cssResultFun(item){
        let resultCss = item.oddsKey=='redBall'?'name redname':item.oddsKey=='blueBall'?'name bluename':'name greenname';
        if(item.choose){
          resultCss += ' oddsSelect';
        }
        return resultCss;
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
        });
      },
      blurFun(item) {
        let self = this;
        setTimeout(function () {
          self.$set(item, 'quickSumStatus', false);
        }, 300);
      },
      setQuickSum(quickSum, item) {
        item.betAmt = parseInt(quickSum);
        this.chooseOdds(item);
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
        let self = this;
        this.$emit('bet');
      },
      resetBetAmt() {
        let self = this;
        self.betAmtPreSet = '';
        self.betAmtStatus = false;
        self.setBetAmtPreSet('');
        self.setBetAmtStatus(false);
        self.initSelectList(true);
      },

      initSelectList(flag) {
        let self = this;
        this.$refs.kjPage.closeAllTime();
        if (flag) {
          this.$refs.kjPage.infoObtain();
        }

        if(!this.betAmtStatus){
          this.pagePresetAmt = '' ;
          this.setBetAmtPreSet('');
        }
        if(!self.pcddOdds) {
          console.log('pcddOdds is undefined');
          return;
        }
        if(!self.pcddOdds.lm) {
          console.log('lm is undefined');
          return;
        }
        if(!self.pcddOdds.baozi) {
          console.log('baozi is undefined');
          return;
        }
        if(!self.pcddOdds.tmbs) {
          console.log('tmbs is undefined');
          return;
        }
        if(!self.pcddOdds.hezhi) {
          console.log('hezi is undefined');
          return;
        }
        if(!self.pcddOdds.sebo) {
          console.log('sebo is undefined');
          return;
        }
        if(self.pcddOdds.lm.length>0){
          self.pcddOdds.lm.forEach((obj) => {
            self.$delete(obj, "choose");
            self.$delete(obj, "betAmt");
          });
        }


        if(self.pcddOdds.tmbs.length>0){
          self.pcddOdds.tmbs.forEach((item)=>{
            self.$delete(item,"choose");
            self.$delete(item,"betAmt");
          });
        }
        if(self.pcddOdds.baozi.length>0){
          self.pcddOdds.baozi.forEach((item)=>{
            self.$delete(item,"choose");
            self.$delete(item,"betAmt");


          });
        }
        if(self.pcddOdds.sebo.length>0){
          self.pcddOdds.sebo.forEach((item) => {
            self.$delete(item, "choose");
            self.$delete(item, "betAmt");
          });
        }

        if(self.pcddOdds.hezhi.length>0){
          self.pcddOdds.hezhi.forEach((item) => {
            self.$delete(item, "choose");
            self.$delete(item, "betAmt");
          });
        }
        self.setSelectList(null);

      },
      selectOdds(item) {
        if (!this.betState || item.status === '1') {
          return;
        }
        this.$set(item, 'choose', !item.choose);
        if (item.choose) {
          this.chooseOdds(item);
        } else {
          this.cancelChooseOdds(item);
        }
      },
      chooseOdds(item) {//选中陪率
        if (this.betGameNo !== this.gameInfo.gameNo) {
          this.setBetGameNo(this.gameInfo.gameNo);
        }
        this.$set(item, 'betAmt', this.pagePresetAmt);
        if(item.oddsKey=='tmbs'){
          let arr = [this.tmbsOne,this.tmbsTwo,this.tmbsThree];
          this.$set(item,'betContent',arr.toString());
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
      }
    },
    mounted(){
      let self = this;
      self.setCategorySelect('zsp');
      self.pagePresetStatus = self.betAmtStatus;
      self.pagePresetAmt = self.betAmtPreSet;
      self.pageQuickBetStatus = self.quickBetStatus;
    }
  }
</script>

<style scoped>
  .oddsSelect {
    background-color: #fff100 !important;
  }
</style>
