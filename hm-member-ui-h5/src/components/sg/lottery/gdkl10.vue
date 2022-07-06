<template>
  <div class="gm_main">
    <div class="home">
      <div class="sc-bZQynM OQRyf">
        <my-header total="true" @clearSpecialSelect="initSelectList"></my-header>
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
              <!--注单-->
              <!--<my-bet @showBet="showBetFunction"></my-bet>-->
            </div>
            <!--右边下注区域-->
            <div :class="game.lotteryKey=='xync'?'sc-htoDjs bTnXwf xync':'sc-htoDjs bTnXwf klsf'">
              <div :class="activeType==='integration'?'tab tab-active':'tab'">
                <template v-for="(list,i) in kl10Odds.integration">
                  <div class="bet-panel-wrapper wrap">
                    <div class="tz_box ">
                      <div class="bet-panel-header">
                        <img id="bgs" class="toggle" :src="!list.showHide?require('../../images/shang.png'):require('../../images/xia.png')"  ><!--点这里要切换图片shang.png xia.png-->
                        <div class="title">{{$t('thesum')}}</div>
                      </div>
                      <ul  v-show="!list.showHide" >
                        <li v-for="(cell,index) in list">
                          <div :class="!kl10Odds.integration[i][index].choose?'wf_box':'wf_box bcn_back'"
                               v-tap="(e)=>selectOdds(kl10Odds.integration[i][index],e)" > <!--选中加bcn_back-->
                            <div class="wf_info" >
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
                  <div class="rough_lines"></div>
                </template>
                <template v-for="(list,i) in kl10Odds.oneToEight">
                  <div class="bet-panel-wrapper wrap">
                    <div :class="game.lotteryKey=='xync'?'tz_box xync sptz_box':'tz_box klsf sptz_box'">
                      <div class="bet-panel-header">
                        <img id="bgs" class="toggle" :src="!list.showHide?require('../../images/shang.png'):require('../../images/xia.png')"  ><!--点这里要切换图片shang.png xia.png-->
                        <div class="title">{{$t(i)}}</div>
                      </div>
                      <ul  v-show="!list.showHide" >

                        <li v-for="(cell,index) in list">
                          <template v-if="cell.categoryKey!='ydb'">
                            <div :class="!kl10Odds.oneToEight[i][index].choose?'wf_box':'wf_box bcn_back'"
                                 v-tap="(e)=>selectOdds(kl10Odds.oneToEight[i][index],e)" > <!--选中加bcn_back-->
                              <div class="wf_info" >
                                <input class="chk" type="checkbox" style="display: none;">
                                <span class="qiu">{{$t(cell.oddsKey)}}</span>
                                <span class="odds">{{betState&&cell.status=='0'?oddsReturn(cell):'封盘'}}</span>
                              </div>
                              <div class="inp"><input type="text" maxlength="7"></div>
                            </div>
                          </template>
                        </li>

                      </ul>
                    </div>
                  </div>
                  <div class="rough_lines"></div>
                </template>
              </div>
              <div :class="activeType==='oneToEight'?'tab tab-active':'tab'">
                <template v-for="(list,i) in kl10Odds.oneToEight">
                  <div class="bet-panel-wrapper wrap">
                    <div :class="game.lotteryKey=='xync'?'tz_box xync sptz_box':'tz_box klsf sptz_box'">
                      <div class="bet-panel-header">
                        <img id="bgs" class="toggle" :src="!list.showHide?require('../../images/shang.png'):require('../../images/xia.png')"  ><!--点这里要切换图片shang.png xia.png-->
                        <div class="title">{{$t(i)}}</div>
                      </div>
                      <ul  v-show="!list.showHide" >
                        <li v-for="(cell,index) in list">
                          <template v-if="cell.categoryKey=='ydb'">
                            <div :class="!kl10Odds.oneToEight[i][index].choose?'wf_box':'wf_box bcn_back'"
                                 v-tap="(e)=>selectOdds(kl10Odds.oneToEight[i][index],e)" > <!--选中加bcn_back-->
                              <div class="wf_info" >
                                <input class="chk" type="checkbox" style="display: none;">
                                <span class="qiu"><em :class="'n_'+cell.oddsKey">{{cell.oddsKey}}</em></span>
                                <span class="odds">{{betState&&cell.status=='0'?oddsReturn(cell):'封盘'}}</span>
                              </div>
                              <div class="inp"><input type="text" maxlength="7"></div>
                            </div>
                          </template>
                        </li>
                        <li >
                          <div :class="!allSearchList[i]?'wf_box':'wf_box bcn_back'"
                               @click="allSearchFun(list,i)" > <!--选中加bcn_back-->
                            <div class="wf_info" >
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
                  <div class="rough_lines"></div>
                </template>

              </div>
              <template v-for="(list,i) in kl10Odds.oneToEight">
                <div :class="activeType===i?'tab tab-active':'tab'">

                    <div class="bet-panel-wrapper wrap">
                      <div :class="game.lotteryKey=='xync'?'tz_box xync sptz_box':'tz_box klsf sptz_box'">
                        <div class="bet-panel-header">
                          <img id="bgs" class="toggle" :src="!list.showHide?require('../../images/shang.png'):require('../../images/xia.png')"  ><!--点这里要切换图片shang.png xia.png-->
                          <div class="title">{{$t(i)}}</div>
                        </div>
                        <ul  v-show="!list.showHide" >
                          <li v-for="(cell,index) in list">
                            <template v-if="cell.categoryKey=='ydb'">
                              <div :class="!kl10Odds.oneToEight[i][index].choose?'wf_box':'wf_box bcn_back'"
                                   v-tap="(e)=>selectOdds(kl10Odds.oneToEight[i][index],e)" > <!--选中加bcn_back-->
                                <div class="wf_info" >
                                  <input class="chk" type="checkbox" style="display: none;">
                                  <span class="qiu"><em :class="'n_'+cell.oddsKey">{{cell.oddsKey}}</em></span>
                                  <span class="odds">{{betState&&cell.status=='0'?oddsReturn(cell):'封盘'}}</span>
                                </div>
                                <div class="inp"><input type="text" maxlength="7"></div>
                              </div>
                            </template>
                          </li>
                          <li >
                            <div :class="!allSearchList[i]?'wf_box':'wf_box bcn_back'"
                                 @click="allSearchFun(list,i)" > <!--选中加bcn_back-->
                              <div class="wf_info" >
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
                    <div class="rough_lines"></div>
                  <template v-if="i==num" v-for="(sum,num) in kl10Odds.oneToEight">
                    <div class="bet-panel-wrapper wrap">
                      <div :class="game.lotteryKey=='xync'?'tz_box xync sptz_box':'tz_box klsf sptz_box'">
                        <div class="bet-panel-header">
                          <img id="bgs" class="toggle" :src="!list.showHide?require('../../images/shang.png'):require('../../images/xia.png')"  ><!--点这里要切换图片shang.png xia.png-->
                          <div class="title">{{$t('lm')}}</div>
                        </div>
                        <ul  v-show="!list.showHide" >
                          <li v-for="(cell,index) in list">
                            <template v-if="cell.categoryKey!='ydb'">
                              <div :class="!kl10Odds.oneToEight[num][index].choose?'wf_box':'wf_box bcn_back'"
                                   v-tap="(e)=>selectOdds(kl10Odds.oneToEight[num][index],e)" > <!--选中加bcn_back-->
                                <div class="wf_info" >
                                  <input class="chk" type="checkbox" style="display: none;">
                                  <span class="qiu"><em :class="'n_'+cell.oddsKey">{{$t(cell.oddsKey)}}</em></span>
                                  <span class="odds">{{betState&&cell.status=='0'?oddsReturn(cell):'封盘'}}</span>
                                </div>
                                <div class="inp"><input type="text" maxlength="7"></div>
                              </div>
                            </template>
                          </li>

                        </ul>
                      </div>
                    </div>
                    <div class="rough_lines"></div>
                  </template>
                </div>
              </template>
              <div :class="activeType==='positiveCode'?'tab tab-active':'tab'">
                <template v-for="(list,i) in kl10Odds.positiveCode">
                  <div class="bet-panel-wrapper wrap">
                    <div :class="game.lotteryKey=='xync'?'tz_box xync sptz_box':'tz_box klsf sptz_box'">
                      <div class="bet-panel-header">
                        <img id="bgs" class="toggle" :src="!list.showHide?require('../../images/shang.png'):require('../../images/xia.png')"  ><!--点这里要切换图片shang.png xia.png-->
                        <div class="title">{{$t(i)}}</div>
                      </div>
                      <ul  v-show="!list.showHide" >
                        <li v-for="(cell,index) in list">
                          <div :class="!kl10Odds.positiveCode[i][index].choose?'wf_box':'wf_box bcn_back'"
                               v-tap="(e)=>selectOdds(kl10Odds.positiveCode[i][index],e)" > <!--选中加bcn_back-->
                            <div class="wf_info" >
                              <input class="chk" type="checkbox" style="display: none;">
                              <span class="qiu"><em :class="'n_'+cell.oddsKey">{{cell.oddsKey}}</em></span>
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
                <template v-for="(list,i) in kl10Odds.integration">
                  <div class="bet-panel-wrapper wrap">
                    <div :class="game.lotteryKey=='xync'?'tz_box xync sptz_box':'tz_box klsf sptz_box'">
                      <div class="bet-panel-header">
                        <img id="bgs" class="toggle" :src="!list.showHide?require('../../images/shang.png'):require('../../images/xia.png')"  ><!--点这里要切换图片shang.png xia.png-->
                        <div class="title">{{$t('thesum')}}</div>
                      </div>
                      <ul  v-show="!list.showHide" >
                        <li v-for="(cell,index) in list">
                          <div :class="!kl10Odds.integration[i][index].choose?'wf_box':'wf_box bcn_back'"
                               v-tap="(e)=>selectOdds(kl10Odds.integration[i][index],e)" > <!--选中加bcn_back-->

                            <div class="wf_info" >
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
                  <div class="rough_lines"></div>
                </template>
              </div>
              <div :class="activeType==='continuousCode'?'tab tab-active':'tab'">
                <template v-for="(list,i) in lianzuList">
                  <div class="bet-panel-wrapper wrap">
                    <div :class="game.lotteryKey=='xync'?'tz_box xync sptz_box':'tz_box klsf sptz_box'">

                      <ul  v-show="!list.showHide" >
                        <li v-for="(cell,index) in list">
                          <div :class="!cell.choose?'wf_box':'wf_box bcn_back'"
                               @click="continuousCodeChange(cell)" > <!--选中加bcn_back-->

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
                </template>
                <template v-for="(list,i) in continuousCodeList">
                  <div class="bet-panel-wrapper wrap">
                    <div :class="game.lotteryKey=='xync'?'tz_box xync sptz_box':'tz_box klsf sptz_box'">
                      <div class="bet-panel-header" >
                        <div class="title">连码</div>
                      </div>
                      <ul  v-show="!list.showHide" >
                        <li v-for="(cell,index) in list">
                          <div :class="!cell.choose?'wf_box':'wf_box bcn_back'"
                               @click="continuousCodeNumSelect(cell)" > <!--选中加bcn_back-->

                            <div class="wf_info">
                              <input class="chk" type="checkbox" style="display: none;">
                              <span class="qiu"><em :class="'n_'+cell.num">{{cell.num}}</em></span>

                              <span class="odds"></span>
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
      <my-footer  v-show="selectList.length==0" ></my-footer>
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
        activeType: 'integration',
        hightShowOrHide:true,
        showBetBox: false,
        lotteryPage:true,
        showBetList:false,
        continuousCodeSelect:null,
        selectFlag:[],
        lianzuList:{},
        continuousCodeFlag:false,
        foldis:true,
        continuousCodeList:{'qiu':[{'num':1},{'num':2},{'num':3},{'num':4},{'num':5},{'num':6},{'num':7},{'num':8},
            {'num':9},{'num':10},{'num':11},{'num':12},{'num':13},{'num':14},{'num':15},{'num':16},{'num':17},{'num':18},
            {'num':19},{'num':20}]},
        leftMenu: [
          {value: 'oneToEight', title: 'oneToEight'},
          {value: 'integration', title: 'integration'},
          {value: 'qiu1', title: 'qiu1'},
          {value: 'qiu2', title: 'qiu2'},
          {value: 'qiu3', title: 'qiu3'},
          {value: 'qiu4', title: 'qiu4'},
          {value: 'qiu5', title: 'qiu5'},
          {value: 'qiu6', title: 'qiu6'},
          {value: 'qiu7', title: 'qiu7'},
          {value: 'qiu8', title: 'qiu8'},
          {value: 'positiveCode', title: 'positiveCode'},
          {value: 'continuousCode', title: 'continuousCode'},
        ],
        allSearchList:{qiu1:false,qiu2:false,qiu3:false,qiu4:false,qiu5:false,qiu6:false,qiu7:false,qiu8:false}
      }
    },
    mounted(){
      let self = this;
      self.initSelectList(true);
      this.$nextTick(()=>{
        if(self.playType){
          self.activeType = self.playType;
        }

        self.setSelectList(null);
      });
      /*setTimeout(()=>{
        self.lianzuList = JSON.parse(JSON.stringify(self.kl10Odds.continuousCode));
      },300);*/

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
      ...mapGetters(['kl10Odds','betState','betGameNo','game','noteLists','gameInfo','selectList','gameId','playType','userOddsCljps','userOddsCloses','userOddsJumps','userOddsNows','userOddss']),
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
      imgCssResult(){
        if(this.game.lotteryKey=='xync'){
          return 'sc-htoDjs bTnXwf xync';
        }else{
          return 'sc-htoDjs bTnXwf klsf';
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
    methods: {
      ...mapActions(['setNoteLists','setSocketResetStatus','setBetGameNo','setSelectList','setLotteryNow','setMarket','setCurrentGame','setPlayType']),
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
        }
        this.continuousCodeSelect = item;
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
      },
      showBetFunction(data){
        this.showBetList = data;
        if(!data){
          this.initSelectList(true);
        }
      },
      selectActiveType(type){
        let self = this;
        this.activeType=type;
        self.allSearchList={qiu1:false,qiu2:false,qiu3:false,qiu4:false,qiu5:false,qiu6:false,qiu7:false,qiu8:false};
        this.setPlayType(type);
        self.setSelectList(null);
        self.setNoteLists(null);
        self.lianzuList = JSON.parse(JSON.stringify(self.kl10Odds.continuousCode));
        if(this.lianzuList['lianma']){
          this.lianzuList['lianma'].forEach((item)=>{
            self.$delete(item,"choose");
            self.$delete(item,"check");
            self.$delete(item,"betAmt");
          });
        }
        if(this.continuousCodeList['qiu']){
          this.continuousCodeList['qiu'].forEach((item)=>{
            self.$set(item,"choose",false);
          });
        }


      },
      initAllOddsList(){
        let self = this;
        self.continuousCodeList['qiu'].forEach((obj) => {
          self.$delete(obj, "choose");
          self.$delete(obj, "betAmt");
        });
        for(let key in self.kl10Odds.integration){
          self.$delete(self.kl10Odds.integration[key],"showHide");
          self.kl10Odds.integration[key].forEach((obj) => {
            self.$delete(obj, "choose");
            self.$delete(obj, "betAmt");
          });
        }

        for(let key in self.kl10Odds.oneToEight){
          self.$delete(self.kl10Odds.oneToEight[key],"showHide");
          self.kl10Odds.oneToEight[key].forEach((obj) => {
            self.$delete(obj, "choose");
            self.$delete(obj, "betAmt");
          });
        }
        for(let key in self.kl10Odds.positiveCode){
          self.$delete(self.kl10Odds.positiveCode[key],"showHide");
          self.kl10Odds.positiveCode[key].forEach((obj) => {
            self.$delete(obj, "choose");
            self.$delete(obj, "betAmt");
          });
        }
        for(let key in self.lianzuList){
          self.$delete(self.lianzuList[key],"showHide");
          self.lianzuList[key].forEach((obj) => {
            self.$delete(obj, "choose");
            self.$delete(obj, "betAmt");
          });
        }
        self.allSearchList={qiu1:false,qiu2:false,qiu3:false,qiu4:false,qiu5:false,qiu6:false,qiu7:false,qiu8:false};
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


        self.initAllOddsList();

      },
      selectOdds(item){
        if(!this.betState || item.status=='1'){
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
        this.$set(item, 'choose', true);
        this.$set(item, 'check', true);
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
