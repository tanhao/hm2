<template>
  <div class="gm_main" id="idc">
    <div class="home">
      <div class="sc-bZQynM OQRyf">
        <my-header back="ture" @activetypeselect="selectActiveType" @refreshTime="initSelectList"></my-header>
        <div class="sc-bdVaJa jaFIbq">
          <my-kj @clearSpecialSelect="initSelectList" ref="resetTime"></my-kj>
          <!--主布局开始-->
          <div class="sc-gzVnrw setbetright">
            <div :class="game.lotteryKey=='xync'?'sc-htoDjs bTnXwf xync':'sc-htoDjs bTnXwf klsf'">
              <div :class="activeType==='integration'?'tab tab-active':'tab'">
                <template  v-for="(list,i) in kl10Odds.integration">
                  <div class="bet-panel-wrapper wrap" >
                    <div :class="game.lotteryKey=='xync'?'tz_box xync sptz_box':'tz_box klsf sptz_box'">
                      <div class="bet-panel-header">
                        <div class="title" >{{$t('thesum')}}</div>
                      </div>
                      <ul >
                        <li v-for="(cell, index) in list">
                          <div v-if="cell.oddsKey.indexOf('wei')==-1"  :class="!kl10Odds.integration[i][index].choose?'wf_box':'wf_box bcn_back'"
                               v-tap="(e)=>selectOdds(kl10Odds.integration[i][index],e)">
                            <div class="wf_info">
                              <input class="chk" type="checkbox" style="display: none;">
                              <span class="qiu">{{$t('sum'+cell.oddsKey)}}</span>
                              <span class="odds">{{betState&&cell.status=='0'?oddsReturn(cell):'封盘'}}</span>
                            </div>
                            <div class="inp"><input type="text" maxlength="7"></div>
                          </div>
                        </li>
                      </ul>
                    </div>
                  </div>
                </template>
                <template  v-for="(list,i) in kl10Odds.integration">
                  <div class="bet-panel-wrapper wrap" >
                    <div :class="game.lotteryKey=='xync'?'tz_box xync sptz_box':'tz_box klsf sptz_box'">
                      <div class="bet-panel-header">
                        <div class="title" >{{$t('thesumwei')}}</div>
                      </div>
                      <ul >
                        <li v-for="(cell, index) in list">
                          <div v-if="cell.oddsKey.indexOf('wei')!=-1"  :class="!kl10Odds.integration[i][index].choose?'wf_box':'wf_box bcn_back'"
                               v-tap="(e)=>selectOdds(kl10Odds.integration[i][index],e)">
                            <div class="wf_info">
                              <input class="chk" type="checkbox" style="display: none;">
                              <span class="qiu">{{$t('sum'+cell.oddsKey)}}</span>
                              <span class="odds">{{betState&&cell.status=='0'?oddsReturn(cell):'封盘'}}</span>
                            </div>
                            <div class="inp"><input type="text" maxlength="7"></div>
                          </div>
                        </li>
                      </ul>
                    </div>
                  </div>
                </template>
              </div>
              <div :class="activeType==='oneToEight'?'tab tab-active':'tab'">
                <template v-for="(list,i) in kl10Odds.oneToEight">
                  <div class="bet-panel-wrapper wrap">
                    <div :class="game.lotteryKey=='xync'?'tz_box xync sptz_box':'tz_box klsf sptz_box'">
                      <div class="bet-panel-header">
                        <div class="title" >{{$t(i)}}</div>
                      </div>
                      <ul>
                        <template v-for="(item,key) in list">
                          <li >
                            <div :class="!kl10Odds.oneToEight[i][key].choose?'wf_box':'wf_box bcn_back'"
                                 v-tap="(e)=>selectOdds(kl10Odds.oneToEight[i][key],e)">
                              <div class="wf_info">
                                <input class="chk" type="checkbox" style="display: none;">
                                <span class="qiu"><em :class="'n_'+item.oddsKey">{{$t(item.oddsKey)}}</em></span>
                                <span class="odds">{{betState&&item.status=='0'?oddsReturn(item):'封盘'}}</span>
                              </div>
                              <div class="inp"><input type="text" maxlength="7"></div>
                            </div>
                          </li>
                        </template>
                        <li v-if="i=='qiu1' || i=='qiu2' || i=='qiu3' || i=='qiu4' || i=='qiu5' ||
                                  i=='qiu6' || i=='qiu7' || i=='qiu8' ">

                          <div :class="!allSearchList[i]?'wf_box':'wf_box bcn_back'"
                               @click="allSearchFun(list,i)">
                            <div class="wf_info">
                              <input class="chk" type="checkbox" style="display: none;">
                              <span class="qiu">1~20</span>
                              <span class="odds">{{betState?'全选':'封盘'}}</span>
                            </div>
                            <div class="inp"><input type="text" maxlength="7"></div>
                          </div>
                        </li>
                      </ul>
                    </div>
                  </div>
                </template>


              </div>
              <template v-for="(list,i) in kl10Odds.oneToEight">
                <div :class="i.indexOf(activeType)!=-1?'tab tab-active':'tab'">
                  <template  >
                    <div class="bet-panel-wrapper wrap">
                      <div :class="game.lotteryKey=='xync'?'tz_box xync sptz_box':'tz_box klsf sptz_box'">
                        <div class="bet-panel-header">
                          <div class="title" >{{$t(i)}}</div>
                        </div>
                        <ul >
                          <template v-for="(item, index) in list">
                            <li>
                              <div  :class="!kl10Odds.oneToEight[i][index].choose?'wf_box':'wf_box bcn_back'"
                                    v-tap="(e)=>selectOdds(kl10Odds.oneToEight[i][index],e)">
                                <div class="wf_info">
                                  <input class="chk" type="checkbox" style="display: none;">
                                  <span class="qiu"><em :class="'n_'+item.oddsKey">{{$t(item.oddsKey)}}</em></span>
                                  <span class="odds">{{betState&&item.status=='0'?oddsReturn(item):'封盘'}}</span>
                                </div>
                                <div class="inp"><input type="text" maxlength="7"></div>
                              </div>
                            </li>
                          </template>
                          <li v-if="i=='qiu1' || i=='qiu2' || i=='qiu3' || i=='qiu4' || i=='qiu5' ||
                                  i=='qiu6' || i=='qiu7' || i=='qiu8' ">
                            <div :class="!allSearchList[i]?'wf_box':'wf_box bcn_back'"
                                 @click="allSearchFun(list,i)">
                              <div class="wf_info">
                                <input class="chk" type="checkbox" style="display: none;">
                                <span class="qiu">1~20</span>
                                <span class="odds">{{betState?'全选':'封盘'}}</span>
                              </div>
                              <div class="inp"><input type="text" maxlength="7"></div>
                            </div>
                          </li>
                        </ul>
                      </div>
                    </div>
                  </template>

                </div>
              </template>
              <div :class="activeType==='positiveCode'?'tab tab-active':'tab'">
                <template v-for="(list,i) in positiveCodeOdds">
                  <div class="bet-panel-wrapper wrap">
                    <div :class="game.lotteryKey=='xync'?'tz_box xync sptz_box':'tz_box klsf sptz_box'">
                      <div class="bet-panel-header">
                        <div class="title" >{{$t(i)}}</div>
                      </div>
                      <ul>
                        <template v-for="(item,key) in list">
                          <li >
                            <div :class="!item.choose?'wf_box':'wf_box bcn_back'"
                                 @click="selectOdds(item)">
                              <div class="wf_info">
                                <input class="chk" type="checkbox" style="display: none;">
                                <span class="qiu"><em :class="'n_'+item.oddsKey">{{item.oddsKey}}</em></span>
                                <span class="odds">{{betState&&item.status=='0'?oddsReturn(item):'封盘'}}</span>
                              </div>
                              <div class="inp"><input type="text" maxlength="7"></div>
                            </div>
                          </li>
                        </template>
                      </ul>
                    </div>
                  </div>
                </template>


              </div>
              <div :class="activeType==='continuousCode'?'tab tab-active':'tab'">
                <template v-for="(list,i) in lianzuList">
                  <div class="bet-panel-wrapper wrap">
                    <div :class="game.lotteryKey=='xync'?'tz_box xync sptz_box':'tz_box klsf sptz_box'">
                      <div class="bet-panel-header">
                        <div class="title" >{{$t(i)}}</div>
                      </div>
                      <ul>
                        <template v-for="(item,key) in list">
                          <li >
                            <div :class="!item.choose?'wf_box':'wf_box bcn_back'"
                                 @click="continuousCodeChange(item)">
                              <div class="wf_info">
                                <input class="chk" type="checkbox" style="display: none;">
                                <span class="qiu">{{$t(item.oddsKey)}}</span>
                                <span class="odds">{{betState&&item.status=='0'?oddsReturn(item):'封盘'}}</span>
                              </div>
                              <div class="inp"><input type="text" maxlength="7"></div>
                            </div>
                          </li>
                        </template>
                      </ul>
                    </div>
                  </div>
                </template>
                <template  v-for="(list,i) in continuousCodeList">
                  <div v-show="continuousCodeSelect" class="bet-panel-wrapper wrap">
                    <div :class="game.lotteryKey=='xync'?'tz_box xync sptz_box':'tz_box klsf sptz_box'">
                      <div class="bet-panel-header">
                        <div class="title" >{{$t('no.')}}</div>
                      </div>
                      <ul>
                        <template v-for="(item,key) in list">
                          <li >
                            <div :class="!item.choose?'wf_box':'wf_box bcn_back'"
                                 @click="continuousCodeNumSelect(item)">
                              <div class="wf_info">
                                <input class="chk" type="checkbox" style="display: none;">
                                <span class="qiu"><em :class="'n_'+item.num">{{item.num}}</em></span>
                                <span class="odds"></span>
                              </div>
                              <div class="inp"><input type="text" maxlength="7"></div>
                            </div>
                          </li>
                        </template>
                      </ul>
                    </div>
                  </div>
                </template>

              </div>

              </div>
          </div>
        </div>
      </div>
      <myendbet ref="betPageF" @clearSpecialSelect="clearSpecialSelect"></myendbet>
    </div>

  </div>
</template>
<script>
  import MyHeader from '@/components/idc/layout/header'
  import MyKj from '@/components/idc/layout/kj'
  import Myendbet from '@/components/idc/layout/footbet'
  import {mapGetters,mapActions} from 'vuex'
  export default {
    components: {
      MyHeader,
      MyKj,
      Myendbet,
    },
    data() {
      return {
        //activeType: 'integration',
        showBetBox: false,
        continuousCodeSelect:null,
        selectFlag:[],
        lianzuList:{},
        lmListOdds:{},
        oneToEightListOdds:{},
        allOddsList:{},
        continuousCodeFlag:false,
        continuousCodeList:{'qiu':[{'num':1},{'num':2},{'num':3},{'num':4},{'num':5},{'num':6},{'num':7},{'num':8},
            {'num':9},{'num':10},{'num':11},{'num':12},{'num':13},{'num':14},{'num':15},{'num':16},{'num':17},{'num':18},
            {'num':19},{'num':20}]},
        positiveCodeOddsList:{},
        allSearchList:{qiu1:false,qiu2:false,qiu3:false,qiu4:false,qiu5:false,qiu6:false,qiu7:false,qiu8:false}
      }
    },
    computed: {
      ...mapGetters(['game','kl10Odds','showMenu','betState','selectList','betGameNo','gameInfo','member','gameId','categoryList','playType','userOddsCljps','userOddsCloses','userOddsJumps','userOddsNows','userOddss']),
      activeType:{
        get(){
          return this.playType;
        },
        set(newVal){
          this.setPlayType(newVal);
        }

      },

      positiveCodeOdds(){
        this.positiveCodeOddsList = JSON.parse(JSON.stringify(this.kl10Odds.positiveCode));
        return this.positiveCodeOddsList;
      },
      oddsReturn(odds){
        let self = this;
        return (odds)=>{
          let {categoryId,oddsId} = odds;

          let now = this.userOddsNows[oddsId];
          let o1 = now?this.userOddsNows[oddsId].reduce((pre,cur)=>pre+cur,0):0;
          let jump = this.userOddsJumps[oddsId];
          let o2 = jump?this.userOddsJumps[oddsId].reduce((pre,cur)=>pre+cur,0):0;
          let cljp = this.userOddsCljps[oddsId];
          let o3 = cljp?this.userOddsCljps[oddsId].reduce((pre,cur)=>pre+cur,0):0;
          let close = this.userOddsCloses[oddsId];
          let o4 = close?this.userOddsCloses[oddsId].reduce((pre,cur)=>pre+cur,0):0;
          let userOdds = this.userOddss[categoryId];
          let o5 = userOdds?this.userOddss[categoryId].reduce((pre,cur)=>pre+cur,0):0;
          self.$set(odds,'odds',Math.round((o1+o2+o3+o4+o5) * 100000) / 100000);
          return Math.round((o1+o2+o3+o4+o5) * 100000) / 100000>0?Math.round((o1+o2+o3+o4+o5) * 100000) / 100000:0;
        }
      },
    },
    watch:{

    },
    methods: {
      ...mapActions(['setSelectList','setSocketResetStatus','setBetGameNo','setPagePosition','setLotteryNow','setCurrentGame','setPlayType','setMarket']),
      allSearchFun(item,index){
        let self = this;
        if(!this.betState){
          return;
        }
        self.allSearchList[index] = !self.allSearchList[index];
        if(self.allSearchList[index]){
          item.forEach(val=>{
            if(val.categoryKey=='ydb' && val.status=='0'){
              if(!val.check && !val.choose){
                self.selectOdds(val);
              }
            }
          })
        }else{
          item.forEach(val=>{
            if(val.categoryKey=='ydb' && val.status=='0'){
              self.cancelChooseOdds(val);
            }
          })
        }

      },
      oddsRefreshFun(){
        /*let self = this;
        for(let key in self.allOddsList){
          self.allOddsList[key].forEach(oddsObj=>{
            self.selectList.forEach(val=>{
              if(val.oddsKey==oddsObj.oddsKey && val.)
            })
          })
        }*/

      },
      selectActiveType(type){
        this.activeType = type;
        this.setPlayType(type);
        this.initSelectList();

      },
      initSelectList(flag){
        let self = this;
        setTimeout(()=>{
          if(flag){
            //self.$refs.resetTime.closeAllTime();
            self.setSocketResetStatus(false);
            self.$refs.resetTime.infoObtain();
            self.$refs.betPageF.chip = '';
            self.$refs.betPageF.pageAmount = '';
            self.$refs.resetTime.fiveRecentResultShow = false;
            self.$refs.resetTime.fiveRecentResultList = [];
          }
        },500)
        self.lianzuList = JSON.parse(JSON.stringify(self.kl10Odds.continuousCode));
        if(!self.kl10Odds) {
          console.log('kl10Odds is undefined');
          return;
        }
        if(!self.kl10Odds.integration) {
          console.log('integration is undefined');
          return;
        }
        if(!self.kl10Odds.oneToEight) {
          console.log('oneToEight is undefined');
          return;
        }
        if(!self.kl10Odds.positiveCode) {
          console.log('positiveCode is undefined');
          return;
        }
        if(!self.lianzuList) {
          console.log('lianzuList is undefined');
          return;
        }
        if(!self.continuousCodeList) {
          console.log('continuousCodeList is undefined');
          return;
        }
        self.allSearchList = {qiu1:false,qiu2:false,qiu3:false,qiu4:false,qiu5:false,qiu6:false,qiu7:false,qiu8:false};
        self.continuousCodeList['qiu'].forEach((obj) => {
          self.$delete(obj, "choose");
          self.$set(obj, "choose",false);
          self.$delete(obj, "betAmt");
        });
        for(let key in self.lmListOdds){
          self.$delete(self.lmListOdds[key],"showHide");
          self.lmListOdds[key].forEach((obj) => {
            self.$delete(obj, "choose");
            self.$set(obj, "choose",false);
            self.$delete(obj, "betAmt");
          });
        }

        for(let key in self.oneToEightListOdds){
          self.$delete(self.oneToEightListOdds[key],"showHide");
          self.oneToEightListOdds[key].forEach((obj) => {
            self.$delete(obj, "choose");
            self.$set(obj, "choose",false);
            self.$delete(obj, "betAmt");
          });
        }
        for(let key in self.allOddsList){
          self.$delete(self.allOddsList[key],"showHide");
          self.allOddsList[key].forEach((obj) => {
            self.$delete(obj, "choose");
            self.$set(obj, "choose",false);
            self.$delete(obj, "betAmt");
          });
        }
        for(let key in self.positiveCodeOddsList){
          self.$delete(self.positiveCodeOddsList[key],"showHide");
          self.positiveCodeOddsList[key].forEach((obj) => {
            self.$delete(obj, "choose");
            self.$set(obj, "choose",false);
            self.$delete(obj, "betAmt");
          });
        }
        for(let key in self.kl10Odds.integration){
          self.$delete(self.kl10Odds.integration[key],"showHide");
          self.kl10Odds.integration[key].forEach((obj) => {
            self.$delete(obj, "choose");
            self.$set(obj, "choose",false);
            self.$delete(obj, "betAmt");
          });
        }

        for(let key in self.kl10Odds.oneToEight){
          self.$delete(self.kl10Odds.oneToEight[key],"showHide");
          self.kl10Odds.oneToEight[key].forEach((obj) => {
            self.$delete(obj, "choose");
            self.$set(obj, "choose",false);
            //self.$delete(obj, "choose");
            self.$delete(obj, "betAmt");
          });
        }
        for(let key in self.kl10Odds.positiveCode){
          self.$delete(self.kl10Odds.positiveCode[key],"showHide");
          self.kl10Odds.positiveCode[key].forEach((obj) => {
            self.$delete(obj, "choose");
            self.$set(obj, "choose",false);
            //self.$delete(obj, "choose");
            self.$delete(obj, "betAmt");
          });
        }
        for(let key in self.lianzuList){
          self.$delete(self.lianzuList[key],"showHide");
          self.lianzuList[key].forEach((obj) => {
            self.$delete(obj, "choose");
            self.$set(obj, "choose",false);
            self.$delete(obj, "betAmt");
          });
        }

        this.continuousCodeSelect = null;
        self.setSelectList(null);

      },
      showIsHide(list){
        this.$set(list,'showHide',!list.showHide);
      },
      selectOdds(item){
        if(!this.betState || item.status=='1'){
          return;
        }
        console.log('看看咯',item);
        this.$set(item,'choose',!item.choose);
        if(item.choose){
          this.chooseOdds(item);
        }else{
          this.cancelChooseOdds(item);
        }
      },
      chooseOdds(item) {//选中陪率
        if (this.betGameNo !== this.gameInfo.gameNo) {
          this.setBetGameNo(this.gameInfo.gameNo);
        }
        this.$set(item, 'choose', true);
        this.$set(item, 'check', true);
        item.choose = true;
        item.check = true;
        let index = this.findChoose(item);
        if (index === -1) {
          this.setSelectList(Object.assign({},item));
        }
      },
      findChoose(item) {//选取的陪率在列表中的位置
        return this.selectList.findIndex(function (value, index, arr) {
          return value.oddsId === this.oddsId;
        }, {oddsId: item.oddsId});
      },
      cancelChooseOdds(item) {//取消选中陪率
        this.$set(item, 'choose', false);
        this.$set(item, 'check', false);
        this.$set(item, 'betAmt', "");
        item.choose = false;
        item.check = false;
        let index = this.findChoose(item);
        if (index !== -1) {
          this.selectList.splice(index, 1);
        }
      },
      clearSpecialSelect(flag){
        if(flag){
          this.initSelectList();
        }
      },
      continuousCodeChange(item){
        let self = this;
        if(self.continuousCodeSelect!=null && item.oddsId!=self.continuousCodeSelect.oddsId){
          this.lianzuList['lianma'].forEach((item)=>{
            self.$delete(item,"choose");
            self.$delete(item,"check");
            self.$delete(item,"betAmt");
          });
        }
        this.setSelectList(null);
        this.continuousCodeList['qiu'].forEach((item)=>{
          self.$set(item,"choose",false);
        });
        this.selectFlag = [];
        if(!this.betState){
          return;
        }
        this.$set(item,'choose',!item.choose);
        if(item.choose){
          this.$set(item, 'choose', true);
          this.$set(item, 'check', true);
          this.continuousCodeSelect = item;
        }else{
          this.continuousCodeSelect = null;
        }

      },
      continuousCodeNumSelect(item){
        if(!this.betState || this.continuousCodeSelect==null){
          return;
        }
        if(!this.continuousCodeSelect.choose){
          return;
        }
        if((this.continuousCodeSelect.oddsKey=='x2'||this.continuousCodeSelect.oddsKey=='x2lz' ||
          this.continuousCodeSelect.oddsKey=='x2lg') && this.selectFlag.length==2 && !item.choose){
          return;
        }else if((this.continuousCodeSelect.oddsKey=='x3'||this.continuousCodeSelect.oddsKey=='x3topz' ||
          this.continuousCodeSelect.oddsKey=='x3topg') && this.selectFlag.length==3 && !item.choose){
          return;
        }else if(this.continuousCodeSelect.oddsKey=='x4' && this.selectFlag.length==4 && !item.choose){
          return;
        }else if(this.continuousCodeSelect.oddsKey=='x5' && this.selectFlag.length==5 && !item.choose){
          return;
        }
        this.$set(item,'choose',!item.choose);
        if(item.choose){

          this.$set(item, 'choose', true);
          this.$set(item, 'check', true);
          this.selectFlag.push(item);
          if((this.continuousCodeSelect.oddsKey=='x2'||this.continuousCodeSelect.oddsKey=='x2lz' ||
            this.continuousCodeSelect.oddsKey=='x2lg') && this.selectFlag.length==2){
            this.continuousCodeChooseOdds();
            return;
          }else if((this.continuousCodeSelect.oddsKey=='x3'||this.continuousCodeSelect.oddsKey=='x3topz' ||
            this.continuousCodeSelect.oddsKey=='x3topg') && this.selectFlag.length==3){
            this.continuousCodeChooseOdds();
            return;
          }else if(this.continuousCodeSelect.oddsKey=='x4' && this.selectFlag.length==4){
            this.continuousCodeChooseOdds();
            return;
          }else if(this.continuousCodeSelect.oddsKey=='x5' && this.selectFlag.length==5){
            this.continuousCodeChooseOdds();
            return;
          }
        }else{
          for(let i = 0;i<this.selectFlag.length;i++){
            if(this.selectFlag[i].num==item.num){
              this.selectFlag.splice(i,1);
              i=-1;
            }
          }
          this.selectList.findIndex((val,index,arr)=>{
            if(this.continuousCodeSelect.oddsId==val.oddsId){
              this.selectList.splice(index,1);
              return;
            }
          });
        }
      },
      continuousCodeChooseOdds(){
        let arr = [];
        for(let i =0;i<this.selectFlag.length;i++){
          arr.push(this.selectFlag[i].num);
        }
        this.continuousCodeSelect.betContent=arr.toLocaleString();

        this.chooseOdds(JSON.parse(JSON.stringify(this.continuousCodeSelect)));
      }

    },
    mounted(){
      let self = this;
      self.initSelectList(true);
    }
  }
</script>
<style scoped>
  .gm_main .wrap .tz_box .wf_box{
    touch-action: manipulation !important;
    line-height: 30px;
    height: 30px;
    border: 1px solid #deaf85;
    margin: 2px 2px;
    padding: 0px;
  }
  .gm_main .wrap .tz_box .wf_box .odds{
    float: right;
    margin-right: 10px;
    font-size: 14px;
    color: red;
  }
  .gm_main .wrap .tz_box .wf_box .qiu {
    font-weight: 700;
    margin-left: 4px;
    float: left;
  }
  .wf_info .qiu{
    padding-bottom: 0px;
  }
</style>
