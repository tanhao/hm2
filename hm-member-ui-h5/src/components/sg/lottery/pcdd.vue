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
            <div class="sc-htoDjs bTnXwf xy28">

              <div :class="activeType==='qbwf'?'tab tab-active':'tab'">
                <div class="bet-panel-wrapper wrap">
                  <div class="tz_box">
                    <div class="bet-panel-header" @click="lmShowHide?lmShowHide=false:lmShowHide=true">
                      <img id="bgs" class="toggle" :src="lmShowHide?require('../../images/shang.png'):require('../../images/xia.png')" ><!--点这里要切换图片shang.png xia.png-->
                      <div class="title">{{$t('lm')}}</div>
                    </div>
                    <ul  v-show="lmShowHide" >
                      <li v-for="(cell) in pcddOdds.lm">
                        <div :class="!cell.choose?'wf_box':'wf_box bcn_back'"
                             @click="selectOdds(cell)" > <!--选中加bcn_back-->
                          <div class="wf_info" >
                            <input class="chk" type="checkbox" style="display: none;">
                            <span class="qiu">{{$t(cell.oddsKey)}}</span>
                            <span class="odds">{{betState&&cell.status=='0'?oddsReturn(cell):'封盘'}}</span>
                          </div>
                          <div class="inp"><input type="text" maxlength="7"></div>
                        </div>
                      </li>
                    </ul>
                  </div>
                </div>
                <div class="rough_lines"></div>
                <div class="bet-panel-wrapper wrap">
                  <div class="tz_box xy28 sptz_box">
                    <div class="bet-panel-header" @click="hezhiShowHide?hezhiShowHide=false:hezhiShowHide=true">
                      <img id="bgs" class="toggle" v-if="" :src="hezhiShowHide?require('../../images/shang.png'):require('../../images/xia.png')" >
                      <div class="title">{{$t('hezhi')}}</div>
                    </div>
                    <ul  v-show="hezhiShowHide">
                      <template v-for="(list,i) in pcddOdds.hezhi">
                        <li>
                          <div :class="!list.choose?'wf_box':'wf_box bcn_back'"
                               @click="selectOdds(list)">
                            <div class="wf_info">
                              <input class="chk" type="checkbox" style="display: none;">
                              <span class="qiu"><em :class="'b_'+list.oddsKey">{{list.oddsKey}}</em></span>
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
                  <div class="bet-panel-wrapper wrap">
                    <div class="tz_box xy28 sptz_box">
                      <div class="bet-panel-header" @click="bzShowHide?bzShowHide=false:bzShowHide=true">
                        <img id="bgs" class="toggle" :src="bzShowHide?require('../../images/shang.png'):require('../../images/xia.png')" >
                        <div class="title">{{$t('baozi')}}、{{$t('duizi')}}、{{$t('shunzi')}}</div>
                      </div>
                      <ul v-show="bzShowHide">
                          <template v-for="(cell, key) in pcddOdds.baozi" >
                            <li >
                              <div :class="!cell.choose?'wf_box':'wf_box bcn_back'"
                                   @click="selectOdds(cell)">
                                <div class="wf_info">
                                  <input class="chk" type="checkbox" style="display: none;">
                                  <span class="qiu"><em :class="'b_'+cell.oddsKey">{{$t(cell.oddsKey.toUpperCase())}}</em></span>
                                  <span class="odds">{{betState&&cell.status=='0'?oddsReturn(cell):'封盘'}}</span>
                                </div>
                                <div class="inp"><input type="text" maxlength="7"></div>
                              </div>
                            </li>
                          </template>
                        <template v-for="(cell, key) in pcddOdds.duizi" >
                          <li >
                            <div :class="!cell.choose?'wf_box':'wf_box bcn_back'"
                                 @click="selectOdds(cell)">
                              <div class="wf_info">
                                <input class="chk" type="checkbox" style="display: none;">
                                <span class="qiu"><em :class="'n_'+cell.oddsKey">{{$t(cell.oddsKey)}}</em></span>
                                <span class="odds">{{betState&&cell.status=='0'?oddsReturn(cell):'封盘'}}</span>
                              </div>
                              <div class="inp"><input type="text" maxlength="7"></div>
                            </div>
                          </li>
                        </template>
                        <template v-for="(cell, key) in pcddOdds.shunzi" >
                          <li >
                            <div :class="!cell.choose?'wf_box':'wf_box bcn_back'"
                                 @click="selectOdds(cell)">
                              <div class="wf_info">
                                <input class="chk" type="checkbox" style="display: none;">
                                <span class="qiu"><em :class="'n_'+cell.oddsKey">{{$t(cell.oddsKey)}}</em></span>
                                <span class="odds">{{betState&&cell.status=='0'?oddsReturn(cell):'封盘'}}</span>
                              </div>
                              <div class="inp"><input type="text" maxlength="7"></div>
                            </div>
                          </li>
                        </template>
                      </ul>
                    </div>
                  </div>
                  <div class="rough_lines"></div>
                  <div class="bet-panel-wrapper wrap">
                    <div class="tz_box xy28 sptz_box">
                      <div class="bet-panel-header" @click="seboShowHide?seboShowHide=false:seboShowHide=true">
                        <img id="bgs" class="toggle" :src="seboShowHide?require('../../images/shang.png'):require('../../images/xia.png')" >
                        <div class="title">{{$t('sebo')}}</div>
                      </div>
                      <ul v-show="seboShowHide">
                        <template v-for="(cell, key) in pcddOdds.sebo" >
                          <li >
                            <div :class="!cell.choose?'wf_box':'wf_box bcn_back'"
                                 @click="selectOdds(cell)">
                              <div class="wf_info">
                                <input class="chk" type="checkbox" style="display: none;">
                                <span class="qiu"><em :class="'n_'+cell.oddsKey">{{$t(cell.oddsKey)}}</em></span>
                                <span class="odds">{{betState&&cell.status=='0'?oddsReturn(cell):'封盘'}}</span>
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
            </div>
          </div>
        </div>
      </div>
      <my-footer   v-show="selectList.length==0" ></my-footer>
      <Myendbet @showBet="showBetFunction" @clearSpecialSelect="initSelectList" v-show="selectList.length>0" ></Myendbet>
    </div>
  </div>
</template>

<script>
  import MyHeader from '@/components/sg/layout/header'
  import MyFooter from '@/components/sg/layout/footer'
  import MySuyin from '@/components/sg/layout/balance'
  import MyKj from '@/components/sg/layout/kj'

  import LeftMenu from '@/components/sg/layout/leftmenu'
  import Myendbet from '@/components/sg/layout/footbet'
  import {mapActions, mapGetters} from 'vuex'
  export default {
    components: {
      MyHeader,
      MyFooter,
      MySuyin,
      MyKj,
      LeftMenu,
      Myendbet,
    },
    data() {
      return {
        activeType: 'lm',
        hightShowOrHide:true,
        lmShowHide:true,
        hezhiShowHide:true,
        bzShowHide:true,
        seboShowHide:true,
        tmbsShowHide:true,
        duiziShowHide:true,
        shunziShowHide:true,
        showBetBox: false,
        showBetList:false,
        foldis:true,
        tmbsOne:0,
        tmbsTwo:1,
        tmbsThree:2,
        tmbsFristSet:[],
        tmbsSecondSet:[],
        tmbsThirdSet:[],
        leftMenu: [
          {value: 'qbwf', title: 'qbwf'},

        ],
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
        this.tmbsOne=0;
        this.tmbsTwo=1;
        this.tmbsThree=2;
      });
    },
    created(){

    },
    watch:{
      playType(){
        let self = this;
        self.activeType = self.playType;
      }
    },
    computed: {
      ...mapGetters(['pcddOdds','betState','betGameNo','noteLists','gameInfo','selectList','gameId','playType','game','gameId','playType','userOddsCljps','userOddsCloses','userOddsJumps','userOddsNows','userOddss']),
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
      tmsbNumberListOne(){
        let arr = [];
        for(let i = 0;i<28;i++){
          if(i == this.tmbsTwo || i == this.tmbsThree){
            continue;
          }else{
            arr.push(i);
          }
        }
        return arr;
      },
      tmsbNumberListTwo(){
        let arr = [];
        for(let i = 0;i<28;i++){
          if(i == this.tmbsOne || i == this.tmbsThree){
            continue;
          }else{
            arr.push(i);
          }
        }
        return arr;
      },
      tmsbNumberListThree(){
        let arr = [];
        for(let i = 0;i<28;i++){
          if(i == this.tmbsTwo || i == this.tmbsOne){
            continue;
          }else{
            arr.push(i);
          }
        }
        return arr;
      },

    },
    methods: {
      ...mapActions(['setNoteLists','setBetGameNo','setSocketResetStatus','setSelectList','setLotteryNow','setMarket','setCurrentGame','setPlayType']),

      pageInitialization(){
      },
      showBetFunction(data){
        this.showBetList = data;
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
      initAllOddsList(){
        let self = this;
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




        self.initAllOddsList();


      },
      tmbsChange(){
        let self = this;
        this.selectList.findIndex((value,index,arr)=>{
          if(value.oddsKey=='tmbs'){
            this.selectList.splice(index,1);
            self.pcddOdds.tmbs.forEach((item)=>{
              self.$delete(item,"choose");
              self.$delete(item,"betAmt");
            });
          }
        });
      },
      selectOdds(item){

        if(!this.betState || item.status==='1'){
          return;
        }
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
          return value.oddsId === this.oddsId;
        }, {oddsId: item.oddsId});
      },
      cancelChooseOdds(item) {//取消选中陪率
        this.$set(item, 'choose', false);
        this.$set(item, 'check', false);
        this.$set(item, 'betAmt', "");
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
