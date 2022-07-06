<template>
  <div class="gm_main">
    <div class="home">
      <div class="sc-bZQynM OQRyf">
        <my-header @clearSpecialSelect="initSelectList" total="true"></my-header>
        <div class="sc-bdVaJa jaFIbq">
          <my-suyin @showShuyin="updateHigh"></my-suyin>
          <my-kj @clearSpecialSelect="initAllOddsList" ref="resetTime"></my-kj>
          <!--主布局开始-->
          <div :class="foldFunction"  ><!-- 'scgzVnrw dwVbnv':'scgzVnrw setbetright kgTTgo'点顶部时这里要联动加kgTTgo--><!--点击下注区域时dwVbnv改为setbetright-->
            <!--左边-->
            <div class="sc-dnqmqq bVspgC">
              <div class="left-menu-list-view">
                <template v-for="item in leftMenu">
                  <a @click="selectActiveType(item.value)" :class="activeType===item.value?'active':''"
                     class="left-menu-list-item">{{$t(item.title)}}</a>
                </template>
              </div>
            </div>
            <!--右边下注区域-->
            <div class="sc-htoDjs bTnXwf pk10">
              <div :class="activeType==='lm'?'tab tab-active':'tab'">
                <template v-for="(list, index) in pk10Odds.lm">
                  <div class="bet-panel-wrapper wrap">
                    <div class="tz_box">
                      <div class="bet-panel-header" >
                        <img id="bgs" class="toggle" :src="!list.showHide?require('../../images/shang.png'):require('../../images/xia.png')" ><!--点这里要切换图片shang.png xia.png-->
                        <div class="title">{{$t(index)}}</div>
                      </div>
                      <ul class="cs1" style="display:block;" v-show="!list.showHide">

                        <li v-for="(cell,num) in list">
                          <div :class="!pk10Odds.lm[index][num].choose?'wf_box':'wf_box bcn_back'"
                               v-tap="(e)=>selectOdds(pk10Odds.lm[index][num],e)" > <!--选中加bcn_back-->
                            <div class="wf_info" >
                              <input class="chk" type="checkbox" style="display: none;">
                              <span class="qiu" v-if="index=='gyh'">{{$t(index+'_'+cell.oddsKey)}}</span>
                              <span class="qiu" v-else>{{$t(cell.oddsKey)}}</span>
                              <span class="odds">{{betState&&cell.status=='0'?oddsReturn(cell):'封盘'}}</span>
                            </div>
                            <div class="inp"><input type="text" maxlength="7"></div>
                          </div>

                        </li>
                      </ul>
                    </div>
                  </div>
                  <div class="rough_lines"></div>
                </template>
              </div>
              <div :class="activeType==='gyh'?'tab tab-active':'tab'">
                <div class="bet-panel-wrapper wrap">
                  <div class="tz_box pk10 sptz_box">
                    <div class="bet-panel-header" >
                      <img id="bgs" class="toggle" :src="gyhShowOrHide?require('../../images/shang.png'):require('../../images/xia.png')"  >
                      <div class="title">{{$t('gyjzh')}}</div>
                    </div>
                    <ul v-show="gyhShowOrHide">
                      <template v-for="(list,i) in pk10Odds.firstAddSecond">
                        <li>
                          <div :class="!pk10Odds.firstAddSecond[i].choose?'wf_box':'wf_box bcn_back'"
                               v-tap="(e)=>selectOdds(pk10Odds.firstAddSecond[i],e)">
                            <div class="wf_info">
                              <input class="chk" type="checkbox" style="display: none;">
                              <span class="qiu">{{$t('gyh'+list.oddsKey)}}</span>
                              <span class="odds">{{betState&&list.status=='0'?oddsReturn(list):'封盘'}}</span>
                            </div>
                            <div class="inp"><input type="text" maxlength="7"></div>
                          </div>
                        </li>
                      </template>
                    </ul>
                  </div>
                </div>
                <div class="rough_lines"></div>
              </div>
              <div :class="activeType==='yds'?'tab tab-active':'tab'">
                <template v-for="(list, index) in pk10Odds.oneToFive">
                  <div class="bet-panel-wrapper wrap">
                    <div class="tz_box pk10 sptz_box">
                      <div class="bet-panel-header">
                        <img id="bgs" class="toggle" :src="!list.showHide?require('../../images/shang.png'):require('../../images/xia.png')" >
                        <div class="title">{{$t('ydw'+index)}}</div>
                      </div>
                      <ul v-show="!list.showHide" >
                          <template v-for="(cell, key) in list" >
                            <li :key="key" v-if="cell.categoryKey!='lm'">
                              <div :class="!pk10Odds.oneToFive[index][key].choose?'wf_box':'wf_box bcn_back'"
                                   v-tap="(e)=>selectOdds(pk10Odds.oneToFive[index][key],e)">
                                <div class="wf_info">
                                  <input class="chk" type="checkbox" style="display: none;">
                                  <span class="qiu"><em :class="'n_'+cell.oddsKey">{{$t(cell.oddsKey)}}</em></span>
                                  <span class="odds">{{betState&&cell.status=='0'?oddsReturn(cell):'封盘'}}</span>
                                </div>
                                <div class="inp"><input type="text" maxlength="7"></div>
                              </div>
                            </li>
                          </template>
                        <template v-for="(cell, key) in list" >
                          <li :key="key" v-if="cell.categoryKey=='lm'">
                            <div :class="!pk10Odds.oneToFive[index][key].choose?'wf_box':'wf_box bcn_back'"
                                 v-tap="(e)=>selectOdds(pk10Odds.oneToFive[index][key],e)">
                              <div class="wf_info">
                                <input class="chk" type="checkbox" style="display: none;">
                                <span class="qiu"><em :class="'n_'+cell.oddsKey">{{$t(cell.oddsKey)}}</em></span>
                                <span class="odds">{{betState&&cell.status=='0'?oddsReturn(cell):'封盘'}}</span>
                              </div>
                              <div class="inp"><input type="text" maxlength="7"></div>
                            </div>
                          </li>
                        </template>
                        <li>
                          <div :class="!allSearchList[index]?'wf_box':'wf_box bcn_back'"
                               @click="allSearchFun(list,index)">
                            <div class="wf_info">
                              <input class="chk" type="checkbox" style="display: none;">
                              <span class="qiu">1~10</span>
                              <span class="odds">{{betState?'全选':'封盘'}}</span>
                            </div>
                            <div class="inp"><input type="text" maxlength="7"></div>
                          </div>
                        </li>
                      </ul>
                    </div>
                  </div>
                  <div class="rough_lines"></div>
                </template>
                <template v-for="(list, index) in pk10Odds.sixToTen">
                  <div  class="bet-panel-wrapper wrap">
                    <div class="tz_box pk10 sptz_box">
                      <div class="bet-panel-header">
                        <img id="bgs" class="toggle" :src="!list.showHide?require('../../images/shang.png'):require('../../images/xia.png')" >
                        <div class="title">{{$t('lds'+index)}}</div>
                      </div>
                      <ul v-show="!list.showHide">
                        <template v-for="(cell, key) in list" >
                          <li :key="key"  v-if="cell.categoryKey!='lm'">
                            <div :class="!pk10Odds.sixToTen[index][key].choose?'wf_box':'wf_box bcn_back'"
                                 v-tap="(e)=>selectOdds(pk10Odds.sixToTen[index][key],e)">
                              <div class="wf_info">
                                <input class="chk" type="checkbox" style="display: none;">
                                <span class="qiu"><em :class="'n_'+cell.oddsKey">{{$t(cell.oddsKey)}}</em></span>
                                <span class="odds">{{betState&&cell.status=='0'?oddsReturn(cell):'封盘'}}</span>
                              </div>
                              <div class="inp"><input type="text" maxlength="7"></div>
                            </div>
                          </li>
                        </template>
                        <template v-for="(cell, key) in list" >
                          <li :key="key"  v-if="cell.categoryKey=='lm'">
                            <div :class="!pk10Odds.sixToTen[index][key].choose?'wf_box':'wf_box bcn_back'"
                                 v-tap="(e)=>selectOdds(pk10Odds.sixToTen[index][key],e)">
                              <div class="wf_info">
                                <input class="chk" type="checkbox" style="display: none;">
                                <span class="qiu"><em :class="'n_'+cell.oddsKey">{{$t(cell.oddsKey)}}</em></span>
                                <span class="odds">{{betState&&cell.status=='0'?oddsReturn(cell):'封盘'}}</span>
                              </div>
                              <div class="inp"><input type="text" maxlength="7"></div>
                            </div>
                          </li>
                        </template>
                        <li>
                          <div :class="!allSearchList[index+5]?'wf_box':'wf_box bcn_back'"
                               @click="allSearchFun(list,(index+5))">
                            <div class="wf_info">
                              <input class="chk" type="checkbox" style="display: none;">
                              <span class="qiu">1~10</span>
                              <span class="odds">{{betState?'全选':'封盘'}}</span>
                            </div>
                            <div class="inp"><input type="text" maxlength="7"></div>
                          </div>
                        </li>
                      </ul>
                    </div>
                  </div>
                  <div class="rough_lines"></div>
                </template>
              </div>
            </div>
          </div>
        </div>
      </div>
      <my-footer  v-show="selectList.length==0"  ></my-footer>
      <Myendbet @showBet="showBetFunction" @clearSpecialSelect="initSelectList" v-show="selectList.length>0" ></Myendbet>
    </div>


  </div>
</template>



<script>
  import MyHeader from '@/components/sg/layout/header'
  import MyFooter from '@/components/sg/layout/footer'
  import MySuyin from '@/components/sg/layout/balance'
  import MyKj from '@/components/sg/layout/kj'
  import Myendbet from '@/components/sg/layout/footbet'
  import {mapActions, mapGetters} from 'vuex'
  export default {
    components: {
      MyHeader,
      MyFooter,
      MySuyin,
      MyKj,
      Myendbet,
    },
    data() {
      return {
        activeType: 'yds',
        hightShowOrHide:true,
        gyhShowOrHide:true,
        lotteryPage:true,
        foldis:true,
        leftMenu: [
          {value: 'yds', title: 'yds'},
          {value: 'gyh', title: 'gyh'},
          {value: 'lm', title: 'lm'},
        ],
        tio:null,
        allSearchList:[false,false,false,false,false,false,false,false,false,false]
      }
    },
    mounted(){

      let self = this;
      self.initSelectList(true);
      this.$nextTick(()=>{
        if(self.playType){
          self.activeType = self.playType;
        }
        this.setSelectList(null);
        //this.selectGame(self.gameId);
      });
    },
    computed: {
      ...mapGetters(['pk10Odds','betState','betGameNo','noteLists','gameInfo','selectList','game','gameId','playType','userOddsCljps','userOddsCloses','userOddsJumps','userOddsNows','userOddss']),
      foldFunction(){
        if(!this.foldis && this.selectList.length>0){
          return 'scgzVnrw dwVbnv ';
        }else if(!this.foldis && this.selectList.length==0){
          return'scgzVnrw setbetright kgTTgo';
        }else if(this.foldis && this.selectList.length>0){
          return 'scgzVnrw setbetright';
        }else if(this.foldis && this.selectList.length==0){
          return'scgzVnrw dwVbnv';
        }
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
      playType(){
        let self = this;
        self.activeType = self.playType;
      }
    },
    methods: {
      ...mapActions(['setNoteLists','setSocketResetStatus','selectGame','setBetGameNo','setLogout','setSelectList','setLotteryNow','setMarket','setCurrentGame','setPlayType']),
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
      initAllOddsList(){
        let self = this;
        for(let item in self.pk10Odds.lm) {
          self.pk10Odds.lm[item].forEach((obj) => {
            self.$delete(obj, "choose");
            self.$delete(obj, "betAmt");
          });
        }


        self.pk10Odds.firstAddSecond.forEach((item)=>{
          self.$delete(item,"choose");
          self.$delete(item,"betAmt");
        });

        self.pk10Odds.oneToFive.forEach((item)=>{
          self.$delete(item,"showHide");

          item.forEach((obj)=>{
            self.$delete(obj,"choose");
            self.$delete(obj,"betAmt");
          })


        });
        self.allSearchList=[false,false,false,false,false,false,false,false,false,false];
        self.pk10Odds.sixToTen.forEach((item) => {
          self.$delete(item, "showHide");

          item.forEach((obj) => {
            self.$delete(obj, "choose");
            self.$delete(obj, "betAmt");
          })
        });
        this.setSelectList(null);
        this.setNoteLists(null);
      },
      pageInitialization(){
        let self = this;

        self.initSelectList(true);

      },
      showBetFunction(data){
        if(!data){
          this.initSelectList(true);
        }
      },
      selectActiveType(type){
        this.activeType=type;
        this.setPlayType(type);
        this.setSelectList(null);
        this.setNoteLists(null);

      },

      initSelectList(flag){
        let self = this;
        setTimeout(()=>{
          if(flag){
            //self.$refs.resetTime.closeAllTime();
            self.setSocketResetStatus(false);
            self.$refs.resetTime.infoObtain();
            self.$refs.resetTime.fiveRecentResultShow = false;
            self.$refs.resetTime.fiveRecentResultList = [];
          }
        },500)

        if(!self.pk10Odds) {
          console.log('pk10Odds is undefined');
          return;
        }
        if(!self.pk10Odds.lm) {
          console.log('lm is undefined');
          return;
        }
        if(!self.pk10Odds.firstAddSecond) {
          console.log('firstAddSecond is undefined');
          return;
        }
        if(!self.pk10Odds.oneToFive) {
          console.log('oneToFive is undefined');
          return;
        }
        if(!self.pk10Odds.sixToTen) {
          console.log('sixToTen is undefined');
          return;
        }


        if(self.playType){
          self.activeType = self.playType;
        }
        self.initAllOddsList();
        self.setSelectList(null);

      },

      selectOdds(item){

        if(!this.betState || item.status=='1'){
          return;
        }
        this.$set(item,'choose',!item.choose);
        item.choose = item.choose;
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
      showIsHide(list){
        this.$set(list,'showHide',!list.showHide);

      },
      updateHigh(data){
        this.foldis = data.showShuyin;
      },

    },
  }
</script>
<style scoped>


</style>
