<template>
  <div class="gm_main" id="idc">
    <div class="home">
      <div class="sc-bZQynM OQRyf">
        <my-header back="ture" @activetypeselect="selectActiveType" @refreshTime="initSelectList"></my-header>
        <div class="sc-bdVaJa jaFIbq">
          <my-kj @clearSpecialSelect="initSelectList" ref="resetTime"></my-kj>
          <!--主布局开始-->
          <div class="sc-gzVnrw setbetright">
            <div class="sc-htoDjs bTnXwf jspcdd">
              <div :class="activeType==='qbwf'?'tab tab-active':'tab'">
                <template  >
                  <div class="bet-panel-wrapper wrap">
                    <div class="tz_box jspcdd sptz_box">
                      <div class="bet-panel-header">
                        <div class="title" >{{$t('lm')}}</div>
                      </div>
                      <ul >
                        <template v-for="(item, index) in pcddOdds.lm">
                          <li >
                            <div  :class="!item.choose?'wf_box':'wf_box bcn_back'"
                                  @click="selectOdds(item)">
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


              </div>
              <div :class="activeType==='qbwf'?'tab tab-active':'tab'">
              <template >
                <div class="bet-panel-wrapper wrap">
                  <div class="tz_box jspcdd sptz_box">
                    <div class="bet-panel-header">
                      <div class="title" >{{$t('hezhi')}}</div>
                    </div>
                    <ul>
                      <template v-for="(item,key) in pcddOdds.hezhi">
                        <li >
                          <div :class="!item.choose?'wf_box':'wf_box bcn_back'"
                               @click="selectOdds(item)">
                            <div class="wf_info">
                              <input class="chk" type="checkbox" style="display: none;">
                              <span class="qiu">{{item.oddsKey}}</span>
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
              <div :class="activeType==='qbwf'?'tab tab-active':'tab'">
                <template  >
                  <div class="bet-panel-wrapper wrap">
                    <div class="tz_box jspcdd sptz_box">
                      <div class="bet-panel-header">
                        <div class="title" >{{$t('baozi')}}、{{$t('duizi')}}、{{$t('shunzi')}}</div>
                      </div>
                      <ul >
                        <template v-for="(item, index) in pcddOdds.baozi">
                          <li >
                            <div  :class="!item.choose?'wf_box':'wf_box bcn_back'"
                                  @click="selectOdds(item)">
                              <div class="wf_info">
                                <input class="chk" type="checkbox" style="display: none;">
                                <span class="qiu">{{$t(item.oddsKey.toUpperCase())}}</span>
                                <span class="odds">{{betState&&item.status=='0'?oddsReturn(item):'封盘'}}</span>
                              </div>
                              <div class="inp"><input type="text" maxlength="7"></div>
                            </div>
                          </li>
                        </template>
                        <template v-for="(item, index) in pcddOdds.duizi">
                          <li >
                            <div :class="!item.choose?'wf_box':'wf_box bcn_back'"
                                 @click="selectOdds(item)">
                              <div class="wf_info">
                                <input class="chk" type="checkbox" style="display: none;">
                                <span class="qiu">{{$t(item.oddsKey)}}</span>
                                <span class="odds">{{betState&&item.status=='0'?oddsReturn(item):'封盘'}}</span>
                              </div>
                              <div class="inp"><input type="text" maxlength="7"></div>
                            </div>
                          </li>
                        </template>
                        <template v-for="(item, index) in pcddOdds.shunzi">
                          <li >
                            <div :class="!item.choose?'wf_box':'wf_box bcn_back'"
                                 @click="selectOdds(item)">
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


              </div>
              <div :class="activeType==='qbwf'?'tab tab-active':'tab'">
                <template >
                  <div class="bet-panel-wrapper wrap">
                    <div class="tz_box jspcdd sptz_box">
                      <div class="bet-panel-header">
                        <div class="title">{{$t('sebo')}}</div>
                      </div>
                      <ul >
                        <template v-for="(item, index) in pcddOdds.sebo">
                          <li >
                            <div :class="!item.choose?'wf_box':'wf_box bcn_back'"
                                 @click="selectOdds(item)">
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


              </div>

              </div>
          </div>
        </div>
      </div>
      <myendbet ref="betPageF" @clearSpecialSelect="clearSpecialSelect"></myendbet>
    </div>
    <left-menu v-show="showMenu"></left-menu>

  </div>
</template>
<script>
  import MyHeader from '@/components/idc/layout/header'
  import MyFooter from '@/components/idc/layout/footer'
  import MyKj from '@/components/idc/layout/kj'
  import LeftMenu from '@/components/idc/layout/leftmenu'
  import Myendbet from '@/components/idc/layout/footbet'
  import {mapGetters,mapActions} from 'vuex'
  import {Indicator} from 'mint-ui'
  export default {
    components: {
      MyHeader,
      MyFooter,
      MyKj,
      LeftMenu,
      Myendbet,
    },
    data() {
      return {
        //activeType: 'lm',
        showBetBox: false,
        gyhShowOrHide: true,
        tmbsOne:0,
        tmbsTwo:1,
        tmbsThree:2,
        tmbsFristSet:[],
        tmbsSecondSet:[],
        tmbsThirdSet:[],
        leftMenu: [
          {value: 'lm', title: '两面盘'},
          {value: 'gyh', title: '冠亚和'},
          {value: 'yds', title: '1~10名'},
        ],

      }
    },computed: {
      ...mapGetters(['game','pcddOdds','showMenu','betState','selectList','betGameNo','gameInfo','member','balances','gameId','categoryList','playType','playType','userOddsCljps','userOddsCloses','userOddsJumps','userOddsNows','userOddss']),
      activeType:{
        get(){
          return this.playType;
        },
        set(newVal){
          this.setPlayType(newVal);
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
      ...mapActions(['setSelectList','setBetGameNo','setSocketResetStatus','setPagePosition','setLotteryNow','setCurrentGame','setPlayType','setMarket']),
      refreshTime(flag){
        /*this.$refs.resetTime.closeAllTime();
        if(flag){
          this.$refs.resetTime.infoObtain();
        };*/

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
            self.$refs.resetTime.fiveRecentResultShow = false;
            self.$refs.betPageF.chip = '';
            self.$refs.betPageF.pageAmount = '';
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
      showIsHide(list){
        this.$set(list,'showHide',!list.showHide);

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
      clearSpecialSelect(flag){
        console.log('clearSelect');
        this.initSelectList(flag);
      }

    },
    mounted(){
      let self = this;
      Indicator.open({text:'加载中...'});
      self.setPagePosition(this.game.lotteryKey);
      this.initSelectList(true);
      this.$nextTick(()=>{
        Indicator.close();
      });
    }
  }
</script>
<style scoped>
  .gm_main .wrap .tz_box .wf_box{
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
    font-weight: bold;
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
