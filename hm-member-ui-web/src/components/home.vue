<template>
  <div>
    <my-header @gamesMenuShow="gamesMenuShow" @dragMenuTableShow="dragMenuTableShow" @changeSkin="changeSkin" ref="headerPage"
               @initialization="initialization"></my-header>
    <my-main @quickSum="quickSum" ref="mainPage" @bet="showBetModel"></my-main>
    <div style="top: 135px; bottom: 27px; min-width: 1155px;">
      <div @mouseleave="gamesMenuShowStatus=false" class="popPanel" v-show="gamesMenuShowStatus"
           style="display: block;">
        <template v-if="showGameMenu.menuLast.length>1" v-for="(list,index) in showGameMenu.menuLast">
          <a style="cursor:pointer" @click="gameSelectionFun(list)"><span>{{$t(list.lotteryKey)}}</span></a>
        </template>
      </div>
      <!--下游明细-->
      <div v-show="showBetStatus"
           class="ui-dialog ui-widget ui-widget-content ui-corner-all ui-front ui-dialog-buttons ui-draggable"
           style="position: relative; height: auto; width: 400px; margin: 0 auto; top:225px; display: block; z-index: 101;"
      >
        <div class="ui-dialog-titlebar ui-widget-header ui-corner-all ui-helper-clearfix ui-draggable-handle"><span
          id="ui-id-3" class="ui-dialog-title">下注明细（请确认注单）</span>
        </div>
        <div id="betsBox" class="ui-dialog-content ui-widget-content"
             style="width: auto; min-height: 0px; max-height: none; height: auto;">
          <div class="betList">
            <table class="table">
              <thead>
              <tr>
                <th>号码</th>
                <th>赔率</th>
                <th>金额</th>
                <th>确认</th>
              </tr>
              </thead>
              <tbody id="betList">
              <template v-for="(item,index) in selectedOddsList " >
                <tr >
                  <template v-if="gameId==101 || gameId==102 || gameId==103 || gameId==104 ||
                      gameId==105 || gameId==106 || gameId==107 || gameId==108 || gameId==109 || gameId==110 || gameId==111 || gameId==112">
                    <td class="contents">{{$t(item.playKey)}}
                      <span v-if="/^[0-9]\d*$/.test(item.oddsKey)">{{item.oddsKey}}</span>
                      <span v-if="!/^[0-9]\d*$/.test(item.oddsKey)">{{$t(item.oddsKey.toUpperCase())}}</span></td>
                    <td class="odds">{{item.odds}}</td>
                  </template>
                  <template v-if="gameId==201 || gameId==202 || gameId==203 || gameId==204 ||
                      gameId==205 || gameId==206 || gameId==207 || gameId==208 || gameId==209 || gameId==210|| gameId==211">
                    <td class="contents">{{$t(item.playKey)}}
                      <span v-if="/^[0-9]\d*$/.test(item.oddsKey)">{{item.oddsKey}}</span>
                      <span v-if="!/^[0-9]\d*$/.test(item.oddsKey)">{{$t(item.oddsKey.toUpperCase())}}</span>
                      <span v-if="item.betContent">{{item.betContent}}</span></td>
                    <td class="odds">{{item.odds}}</td>
                  </template>
                  <template v-if="gameId==301 || gameId==302 || gameId==303 || gameId==304">
                    <td class="contents">
                    <span v-if="item.betContent">{{$t(item.playKey)}}{{$t(item.oddsKey.toUpperCase())}}</span>
                    <span v-if="!item.betContent">
                                    <span v-if="item.playKey=='sum'">{{$t('THESUM')}}</span>
                                    <span v-else>{{$t(item.playKey)}}</span>


                                    <span v-if="/^[0-9]\d*$/.test(item.oddsKey)">{{$t(item.oddsKey)}}</span>
                                    <span v-if="!/^[0-9]\d*$/.test(item.oddsKey)">{{$t(item.oddsKey.toUpperCase())}}</span></span>
                    <span v-if="item.betContent">{{item.betContent}}</span>

                    </td>
                    <td class="odds">{{item.odds}}</td>
                  </template>
                  <template v-if="gameId==401 || gameId == 402 || gameId==403" >
                    <td>

                      <span>{{$t(item.playKey)}}</span>
                      <span v-if="/^[0-9]\d*$/.test(item.oddsKey)">{{item.oddsKey}}</span>
                      <span v-if="!/^[0-9]\d*$/.test(item.oddsKey)">{{$t(item.oddsKey)}}</span>
                      <span v-if="item.betContent">{{item.betContent}}</span>
                    </td>
                    <td class="odds">{{item.odds}}</td>
                  </template>
                  <td class="amount"><input type="text" oninput="this.value = this.value.replace(/[^0-9]/g, '');" @keyup="notesListsBetAmtUpdate" v-model="item.betAmt"></td>
                  <td class="check"><input type="checkbox" @change="checkSelectOddsFun(item)" v-model="item.check"></td>
                </tr>
              </template>
              </tbody>
            </table>
          </div>
          <div class="bottom"><span id="bcount">笔数：{{betDetail.total}}</span><span id="btotal">总金额：{{betDetail.totalAmt | moneyFmt}}</span>
          </div>
          <!--<div><label><input type="checkbox" v-model="autoNewOdds" id="ignoreOdds">如赔率变化，按最新赔率投注，不提示赔率变化</label></div>-->
        </div>
        <div class="ui-dialog-buttonpane ui-widget-content ui-helper-clearfix">
          <div class="ui-dialog-buttonset">
            <button type="button" ref="okBetButton" id="okBetButton" @click="bet()" v-bind:disabled="isBetLoading"
                    class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only"
                    role="button"><span class="ui-button-text" v-if="isBetLoading">进行</span><span class="ui-button-text"
                                                                                                  v-if="!isBetLoading">投注</span>
            </button>
            <button type="button" @click="clearBet"
                    class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only"
                    role="button"><span class="ui-button-text">取消</span></button>
          </div>
        </div>
      </div>
      <div v-show="showBetStatus" class="ui-widget-overlay ui-front" style="z-index: 100;"></div>
      <!--用户取消下注提示框-->
      <div v-show="false"
           class="ui-dialog ui-widget ui-widget-content ui-corner-all ui-front ui-dialog-buttons ui-draggable"
           style="position: relative; height: auto; width: 400px; margin:0 auto;top: 428px;display: block; z-index: 102;"
           aria-describedby="messageBox" aria-labelledby="ui-id-4">
        <div class="ui-dialog-titlebar ui-widget-header ui-corner-all ui-helper-clearfix ui-draggable-handle"><span
          id="ui-id-4" class="ui-dialog-title">用户提示</span>
          <button type="button"
                  class="ui-button ui-widget ui-state-default ui-corner-all ui-button-icon-only ui-dialog-titlebar-close"
                  role="button" title="Close"><span
            class="ui-button-icon-primary ui-icon ui-icon-closethick"></span><span
            class="ui-button-text">Close</span></button>
        </div>
        <div id="messageBox" class="ui-dialog-content ui-widget-content"
             style="width: auto; min-height: 0px; max-height: none; height: auto;">你确定取消下注吗？
        </div>
        <div class="ui-dialog-buttonpane ui-widget-content ui-helper-clearfix">
          <div class="ui-dialog-buttonset">
            <button type="button"
                    class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only ui-state-focus"
                    role="button"><span class="ui-button-text">确定</span></button>
            <button type="button" class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only"
                    role="button"><span class="ui-button-text">取消</span></button>
          </div>
        </div>
      </div>
      <div v-show="false" class="ui-widget-overlay ui-front" style="z-index: 100;"></div>
      <!--快选金额-->
      <div v-show="quickSumModel"
           class="ui-dialog ui-widget ui-widget-content ui-corner-all ui-front ui-draggable ui-resizable"
           style="position: relative; height: auto; width: 270px; left:450px; top: 327px; display: block; z-index: 101;">
        <div class="ui-dialog-titlebar ui-widget-header ui-corner-all ui-helper-clearfix ui-draggable-handle"><span
          id="ui-id-1" class="ui-dialog-title">快选金额</span>
          <button @click="quickSumModel = false" type="button"
                  class="ui-button ui-widget ui-state-default ui-corner-all ui-button-icon-only ui-dialog-titlebar-close"
                  role="button" title="Close"><span
            class="ui-button-icon-primary ui-icon ui-icon-closethick"></span><span
            class="ui-button-text">Close</span></button>
        </div>
        <div id="settingbet" style="display: block; width: auto; min-height: 91px; max-height: none; height: auto;"
             class="ui-dialog-content ui-widget-content">
          <input v-model="quickSumList[0]" name="bet_1" type="text" oninput="this.value = this.value.replace(/[^0-9]/g, '');" placeholder="快选金额" class="ds"
                 onkeypress=""><br>
          <input v-model="quickSumList[1]" name="bet_2" type="text" oninput="this.value = this.value.replace(/[^0-9]/g, '');" placeholder="快选金额" class="ds"
                 onkeypress=""><br>
          <input v-model="quickSumList[2]" name="bet_3" type="text" oninput="this.value = this.value.replace(/[^0-9]/g, '');" placeholder="快选金额" class="ds"
                 onkeypress=""><br>
          <input v-model="quickSumList[3]" name="bet_4" type="text" oninput="this.value = this.value.replace(/[^0-9]/g, '');" placeholder="快选金额" class="ds"
                 onkeypress=""><br>
          <input v-model="quickSumList[4]" name="bet_5" type="text" oninput="this.value = this.value.replace(/[^0-9]/g, '');" placeholder="快选金额" class="ds"
                 onkeypress=""><br>
          <input v-model="quickSumList[5]" name="bet_6" type="text" oninput="this.value = this.value.replace(/[^0-9]/g, '');" placeholder="快选金额" class="ds"
                 onkeypress=""><br>
          <input v-model="quickSumList[6]" name="bet_7" type="text" oninput="this.value = this.value.replace(/[^0-9]/g, '');" placeholder="快选金额" class="ds"
                 onkeypress=""><br>
          <input v-model="quickSumList[7]" name="bet_8" type="text" oninput="this.value = this.value.replace(/[^0-9]/g, '');" placeholder="快选金额" class="ds"
                 onkeypress=""><br>

          <label>
            <input name="settingbet" v-model="quickSumStatus" type="radio" id="settingbet_0" value="1">
            启动
          </label>
          <label>
            <input name="settingbet" v-model="quickSumStatus" type="radio" id="settingbet_1" value="0">
            停用
          </label>
          <br>
          <br>
          <input type="button" class="button" value="储存" @click="quickSumSubmit()">
        </div>

      </div>
      <div v-show="false" class="ui-widget-overlay ui-front" style="z-index: 100;"></div>
    </div>
    <!--用户弹出公告-->
    <div v-show="noticeShow" style="display:none ">
      <div class="mod-dialog-bg" style="display: block;"></div>
      <div class="mod-dialog" style="left: calc(50% - 350px); top: 25%; display: block;width: 700px">
        <div class="dialog-nav">
          <span class="dialog-title">用户公告</span>
          <a class="dialog-close" href="javascript:void(0)" @click="noticeClose"></a>
        </div>
        <div class="dialog-main">
          <div class="dialog-content">
            公司通告
            <ol style="margin: 0;padding: 10px;" >

              <li v-for="(item,index) in noticeArr">{{item.content}}</li>
            </ol>
          </div>
        </div>
      </div>
    </div>
    <my-footer v-if="" @noticeModelShow="noticeModelShow"></my-footer>
  </div>

</template>

<script>

  import MyHeader from '@/components/layout/header'
  import MyMain from '@/components/layout/main'
  import MyFooter from '@/components/layout/footer'
  import {mapGetters, mapActions} from 'vuex'
  import Utils from '@/components/comm/Utils.js'
  import im from '@/components/comm/im.js'
  import {Indicator} from 'mint-ui'
  import to from "await-to-js";
  export default {
    components: {
      MyHeader,
      MyMain,
      MyFooter,
    },
    data() {
      return {
        gamesMenuShowStatus: false,
        quickSumModel: false,
        quickSumStatus: 1,
        showBetStatus: false,
        autoNewOdds: false,
        isBetLoading: false,
        betStatus: false,
        isChangePassword:false,
        params: {
          lotteryId: null,
          status: 'WAITING',
          accountDay: Utils.formatDate(new Date(),'yyyy-MM-dd'),
          page:1,
          size:9999,
        },
        selectedOddsList:[],
        total:{
          totalAmt: 0.00,
          total: 0
        },
        noticeArr:[],
        noticeShow:false,
        refreshTime:null,
      }
    },
    computed: {
      ...mapGetters(['gameMenu','balance','siteName','userOddsCljps','userOddsCloses','userOddsJumps','userOddsNows','userOddss','showGameMenu','noteLists', 'game', 'member', 'betToken', 'gameId', 'gameInfo', 'betState', 'betGameNo', 'markets', 'quickSumList', 'selectList']),
      betDetail() {
        let self = this;
        self.total = {
          totalAmt: 0.00,
          total: 0
        };
        for (let item of this.selectedOddsList) {

          if (item.check) {
            self.total.total++;
            self.total.totalAmt = Utils.NumberAdd(self.total.totalAmt, item.betAmt);
          }
        }
        return self.total;
      }
    },
    methods: {
      ...mapActions(['setWhetherSwitch','setSiteName','setLotteryNow','setSocket','setUserOddss','setUserOddsCljps','setUserOddsCloses','setUserOddsJumps','setUserOddsNows','setNoteListsFast','setSelectListFast','setPromptInformation','setNoteLists','setSelectList','setCurrentGame','setGames', 'selectGame','setBetGameNo', 'setLatesNoteList', 'setBalances', 'setQuickSumList', 'setBetToken', 'setPlayType']),
      checkSelectOddsFun(odds){
        let self = this;
        self.total = {
          totalAmt: 0.00,
          total: 0
        };
        for (let item of this.selectedOddsList) {

          if (item.check) {
            self.total.total++;
            self.total.totalAmt = Utils.NumberAdd(self.total.totalAmt, item.betAmt);
          }
        }
      },
      noticeModelShow(noticeArr){
        this.noticeArr = noticeArr;
        this.noticeShow = true;
      },
      noticeClose(){
        this.noticeShow = false;
      },
      gohome() {
        this.$router.push('/home');
      },
      initSocket(){
        let self = this;
        let member = this.member;
        let socket = im.initWs(member.uid,this.handlerMsg());
        setTimeout(()=>{
          document.title = this.siteName+'系统';
        },1000);
        self.refreshTime = setInterval(()=>{
          self.refreshBalanceFun();
        },3000)
        self.$api.Member.getLogos().then(resSiteName=>{
          if(resSiteName.code==10000){
            self.setSiteName(resSiteName.data);
          }
        })
        setTimeout(()=>{
          if(socket && socket.ws.readyState==1){
            self.setSocket(socket);
          }else{
            socket = im.initWs(member.uid,this.handlerMsg());
            self.setSocket(socket);
          }
        },1000)
      },
      handlerMsg () {
        let self = this;
        return new function(){
          this.onopen = function (event, ws) {

            // 握手连接
            if(self.$refs.mainPage.$refs.lotteryPage.lotteryPageFlag){
              self.selectGame(self.gameId);
              self.$refs.mainPage.$refs.lotteryPage.initSelectList(true);
            }
          }
          this.onmessage = function (event, ws) {
            if(!self.member) return;
            var data=eval('(' + event.data + ')');
            //心跳数据不需要处理
            if(data.code==='ping')  return;
            console.log(data);
            if(data.code==='result'){
              let lotteryInfo = data.content;
              console.log(lotteryInfo,self.game);
              if(lotteryInfo.lotteryKey == self.game.lotteryKey && lotteryInfo.gameNo >= self.gameInfo.prevGameNo){
                self.setLotteryNow(lotteryInfo);
              }
                setTimeout(function(){
                  self.refreshBalanceFun();
                },2000);
            }else if(data.code==='odds_init'){
              self.setCurrentGame(data.content);
            }else if(data.code==='odds_now'){
              let {userLevel,oddss} = data.content;
              oddss.forEach((item)=>{
                let {oddsId,odds} = item;
                if(!self.userOddsNows[oddsId]){
                  self.$set(self.userOddsNows,''+oddsId,new Array(userLevel).fill(0));
                }
                self.$set(self.userOddsNows[oddsId],userLevel,odds);
              })
              self.setUserOddsNows(self.userOddsNows);
            }else if(data.code==='odds'){
              let {userLevel,oddss} = data.content;
              oddss.forEach((item)=>{
                let {categoryId,odds} = item;
                if(!self.userOddss[categoryId]){
                  self.$set(self.userOddss,''+categoryId,new Array(userLevel).fill(0));
                }
                self.$set(self.userOddss[categoryId],userLevel,odds);
              })
              self.setUserOddss(self.userOddss);
            }else if(data.code ==='odds_cljp'){
              let {userLevel,oddss} = data.content;
              oddss.forEach((item)=>{
                let {oddsId,odds:newVal} = item;
                let arr = new Array(userLevel).fill(0);
                arr[userLevel] = newVal;
                self.$set(self.userOddsCljps,oddsId,arr);
              })
              self.setUserOddsCljps(self.userOddsCljps);
            }else if(data.code ==='odds_jump'){
              let {userLevel,oddss} = data.content;
              oddss.forEach((item)=>{
                let {oddsId,odds} = item;
                if(!self.userOddsJumps[oddsId]){
                  self.$set(self.userOddsJumps,''+oddsId,new Array(userLevel).fill(0));
                }
                self.$set(self.userOddsJumps[oddsId],userLevel,odds);
              })
              self.setUserOddsJumps(self.userOddsJumps);
            }


          }

          this.onclose = function (e, ws) {
            // error(e, ws)
          }

          this.onerror = function (e, ws) {
            // error(e, ws)
          }
        }

      },
      notesListsBetAmtUpdate(){
        let self = this;
        self.total = {
          totalAmt: 0.00,
          total: 0
        };
        for (let item of this.selectedOddsList) {

          if (item.check) {
            self.total.total++;
            self.total.totalAmt = Utils.NumberAdd(self.total.totalAmt, item.betAmt);
          }
        }
      },
      gameSelectionFun(item) {
        this.setPlayType(1);
        this.$refs.headerPage.lotteryInsert(item);
      },
      changeSkin(color) {
        this.$emit('changeSkin', color);
      },
      initialization(flag) {
        if(this.$refs.mainPage.$refs.lotteryPage.lotteryPageFlag){
          this.$refs.mainPage.$refs.lotteryPage.initSelectList(true);
        }
      },
      dragMenuTableShow(){
        this.$refs.mainPage.$data.dragMenuTable = true;
      },
      quickSumSubmit() {
        let list = {'list': this.quickSumList, 'status': this.quickSumStatus};
        this.setQuickSumList(list);
        this.setPromptInformation('储存成功!');
        this.quickSumModel = false;
      },
      gamesMenuShow(status) {

        this.gamesMenuShowStatus = status;
      },
      quickSum() {
        this.quickSumModel = true;
      },
      clearBet(){
        this.showBetStatus=false;
        this.$refs.mainPage.$refs.lotteryPage.initSelectList(true);
        this.$refs.mainPage.$refs.lotteryPage.closeMarketFun();
      },
      showBetModel() {
        let self = this;

        self.setNoteLists(null);
        self.setNoteListsFast(self.selectList);

        if (self.noteLists.length > 0) {
          for(let i = 0;i<self.noteLists.length;i++){
            for(let j = 0;j<self.noteLists.length - i -1;j++){
              if(self.noteLists[j].oddsId > self.noteLists[j+1].oddsId){
                let obj = Object.assign({},self.noteLists[j]);
                self.noteLists[j] = self.noteLists[j+1];
                self.noteLists[j+1] = obj;
              }
            }
          }
          if (self.gameInfo.gameNo !== self.betGameNo) {
            self.setBetGameNo(self.gameInfo.gameNo);
          }
          this.selectedOddsList = this.noteLists;
          this.isBetLoading = false;
          this.showBetStatus = true;
          setTimeout(()=>{
            this.$refs.okBetButton.focus();
            document.getElementById('okBetButton').focus();
          },200)

        } else {
          this.setPromptInformation('请至少选中一种赔率并输入金额!');
          this.showBetStatus=false;
        }


      },
      async bet(newOdds) {
        let self = this;
        self.isBetLoading = true;
        Indicator.open({text: '下注中...'});
        /*if (self.gameInfo.gameNo !== self.betGameNo) {
          self.setBetGameNo(self.gameInfo.gameNo);
          return this.betError('当前下注期号已经开奖', true);
        }*/
        if (self.betStatus) return this.betError('下注正在进行中！', true);
        self.betStatus = true;
        if (!self.betState) {

          return self.betError('已封盘，不能下注', true);
        }
        let isCanBet = true;
        let autoNewOdds = 0;
        if (newOdds) {
          autoNewOdds = 1;
        }

        let betRq = {
          lotteryId: self.gameId,
          gameNo: self.gameInfo.gameNo,
          betOdds: new Array(0),
          market: self.markets,
          autoNewOdds: autoNewOdds,
          betSource:'normal',
        };
        let msgDatas = '';
        for (let item of self.selectedOddsList) {
          if (item.status != '0') {
            msgDatas += self.$t(item.categoryKey) + self.$t(item.playKey) + self.$t(item.oddsKey.toUpperCase()) + '赔率已关闭。不能下注';
            isCanBet = false;
          }

          if(item.check && /^[1-9]\d*$/.test(item.betAmt)){
            let obj = {oddsId:item.oddsId,odds:item.odds,betAmt:item.betAmt,betContent:item.betContent};
            betRq.betOdds.push(JSON.parse(JSON.stringify(obj)));
          }
        }
        if (!isCanBet) {

          self.betStatus = false;

          return self.betError(msgDatas, false);
        }
        if (betRq.betOdds.length === 0) {
          self.betStatus = false;
          return self.betError('请至少选中一条进行下注！', false);
        }

        let [err,data] = await to(this.$api.Bet.bet(betRq));

        if(data.success){
          this.showBetStatus = false;
          self.$messageBox.close();
          self.$toast({
            message: '下单成功!',
            iconClass: 'fa fa-check fa-5x',
            duration:1500
          })

            self.params.lotteryId = self.gameId;
            self.params.size = self.selectedOddsList.filter(item=>/^[1-9]\d*$/.test(item.betAmt) && item.check).length;
            let date = new Date();
            let hh = date.getHours();
            if (hh < 7) {
              date.setDate(date.getDate() - 1);
              let dateParam = Utils.formatDate(date, 'yyyy-MM-dd');
              let spliceArr = dateParam.split('-');
              self.params.accountDay = Utils.formatDate(new Date(spliceArr[0]+'-'+spliceArr[1]+'-'+spliceArr[2]),'yyyy-MM-dd');
            }
            this.$api.Bet.betList(self.params).then(val => {
              if (val.code === 10000){
                self.setLatesNoteList(val.data.dataList);
                this.$api.Member.balanceInfo().then(resBalanceInfo => {
                  if (resBalanceInfo) {
                    self.setBalances(resBalanceInfo.data);
                  }
                })
              }
            }).finally(() => {
              Indicator.close();
              this.$refs.mainPage.$refs.lotteryPage.initSelectList();


            });





        }else{
          if (data.code == 2009) {
            self.$messageBox({$type:'confirm',message:'赔率变更，是否接受新赔率!',title:'提示'
              ,closeOnClickModal:false,showCancelButton:true,confirmButtonText:'接受',cancelButtonText:'不接受'}).then(action => {
              if (Object.is(action, 'confirm')) {
                self.bet(true);
              }

            }).catch((err) => {
              if (Object.is(err, 'cancel')) {
                self.setWhetherSwitch(false);
                this.showBetStatus = false;
                this.isBetLoading = false;
                this.betStatus = false;
                this.clearBet();
              }
            })
          } else {
            let keys = '';
            if(data.data && data.data.keys){
              keys = JSON.parse(data.data.keys);
            }
            this.isBetLoading = false;
            return this.betError(self.$t(keys.playKey)+' '+self.$t(keys.oddsKey)+' '+data.message);
          }
        }
        Indicator.close();
        this.betStatus = false;

      },
      betError(msg, reset) {
        this.isBetLoading = false;
        this.setPromptInformation(msg).then(() => {
          this.betStatus = false;
          /*this.$refs.mainPage.$refs.lotteryPage.initSelectList(true);*/
          this.showBetStatus = false;
          if (reset) {
            this.clearBet();
          }

        }).finally(()=>{
          Indicator.close();
        });
        Indicator.close();

      },
      async refreshBalanceFun(){
        let self = this;
        let [err,data] = await to(this.$api.Member.balanceInfo(this.member.userId));
        if(err || !data.success){

        }else{
          if(data.data.balance && data.data.balance!=this.balance){
            self.params.lotteryId = self.gameId;
            self.params.size = 20;
            let date = new Date();
            let hh = date.getHours();
            if (hh < 7) {
              date.setDate(date.getDate() - 1);
              let dateParam = Utils.formatDate(date, 'yyyy-MM-dd');
              let spliceArr = dateParam.split('-');
              self.params.accountDay = Utils.formatDate(new Date(spliceArr[0]+'-'+spliceArr[1]+'-'+spliceArr[2]),'yyyy-MM-dd');
            }
            this.$api.Bet.betList(self.params).then(val => {
              if (val.code === 10000){
                self.setLatesNoteList(val.data.dataList);
              }
            })
          }
          this.setBalances(data.data);
        }

      },
    },
    filters: {
      moneyFmt(val) {
        if (!val || isNaN(val)) {
          return '0.00';
        }
        return Utils.formatMoney(val, 2);
      },
      idFmt(val) {
        if (val) {
          let valSubString = val.toString();
          return valSubString.substring(8);
        }

      }
    },
    beforeDestroy(){
      if(this.refreshTime){
        clearInterval(this.refreshTime);
        this.refreshTime = null;
      }
    },
    destroyed() {
      clearInterval(this.refreshTime);
      this.refreshTime = null;
    },

    mounted() {
      let self = this;
      let router = self.$router;
      if(((router.currentRoute.path!='/idc/password' || router.currentRoute.path!='/sg/password'))){
          self.initSocket();
      }

      /*let self = this;
      document.onkeydown = function(e){
        var key = window.event.keyCode;

        if(key==13){
          console.log('aa');
          self.$messageBox.close();
        }
      }*/
    }
  }
</script>
<style scoped>

</style>
