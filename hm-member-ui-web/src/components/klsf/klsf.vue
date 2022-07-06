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
          <label class="checkdefault"><input type="checkbox" v-model="pagePresetStatus" @change="betAmtChange" class="checkbox"><span
            class="color_lv bold">预设</span></label>&nbsp;&nbsp;<label
          class="quickAmount"><span class="color_lv bold">金额</span> <input
          oninput="this.value = this.value.replace(/\D/g, '').replace(/^0{1,}/g, '');" maxlength="6" @keyup="inputSetAmt" type="text"
          v-model="pagePresetAmt"></label>
          <!--所有球全选-->
          <label v-show="pageQuickBetStatus" @click="allSelectOnetoEightFun" class="checkdefault mlwit"><input type="checkbox" :disabled="!betState?true:false" @click="allSelectOnetoEightFun" v-model="oneToEightallSelectBox" class="checkbox"><span
            class="color_lv bold">所有球全选</span></label>

          <input type="button" ref="betButton" id="betButton" @click="bet()" value="确定" class="button">
          <input type="button" @click="resetBetAmt()" value="重置" class="button">
        </div>
      </div>
      <div v-show="categorySelect=='positiveCode'" class="bet_panel input_panel">
        <!--第一球 到第八球一样-->
        <table :class='classResult'>
          <tbody>
          <tr class="head">
            <th>号码</th>
            <th>赔率</th>
            <th v-show="!pageQuickBetStatus" class="ha">金额</th>
            <th>号码</th>
            <th>赔率</th>
            <th v-show="!pageQuickBetStatus" class="ha">金额</th>
            <th>号码</th>
            <th>赔率</th>
            <th v-show="!pageQuickBetStatus" class="ha">金额</th>
            <th>号码</th>
            <th>赔率</th>
            <th v-show="!pageQuickBetStatus" class="ha">金额</th>
          </tr>
          <template v-for="(list,i) in zmOddsResult">
            <tr>
              <template v-for="(item,index) in list">
                <th @click="selectOdds(item)" :class="item.choose?'oddsSelect':''" class="name"
                    :title="item.oddsKey"><input type="hidden" value="">
                  <span :class="'b'+item.oddsKey">{{item.oddsKey}}</span>
                </th>
                <td @click="selectOdds(item)" :class="item.choose?'oddsSelect':''" class="odds">
                  {{betState&&item.status=='0'?oddsReturn(item):'封盘'}}
                </td>
                <td v-show="!pageQuickBetStatus" :class="item.choose?'oddsSelect':''" class="amount ha">
                  <input type="text" oninput="this.value = this.value.replace(/\D/g, '').replace(/^0{1,}/g, '');" maxlength="6"
                         v-model="item.betAmt" @focus="focusFun(item)"
                         v-bind:id="item.oddsId" v-bind:ref="item.oddsId" @input="inputBetAmt(item)"
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

              <!--<th class="name" title="第一球 16"><input type="hidden" value=""><span class="b16">16</span></th>

              <td class="odds">19.56</td>
              <td class="amount ha"><input  class="ba"></td>-->
            </tr>
          </template>
          </tbody>
        </table>

      </div>
      <div v-show="categorySelect=='integration' || categorySelect=='positiveCode'" id="bet_panel" class="bet_panel input_panel">
        <!--两面盘-->
        <!--总和-->
        <table v-show="categorySelect=='integration' || categorySelect=='positiveCode'" class="table_zh">
          <tbody>
          <tr class="head">
            <th colspan="12">总和</th>
          </tr>
          <template v-for="(list,index) in kl10Odds.integration">
            <tr >
              <template v-for="(item,key) in list">
                <template v-if="key<3">
                  <th @click="selectOdds(item)" :class="item.choose?'oddsSelect':''" class="name"
                      :title=" $t('sum'+item.oddsKey)"><input type="hidden" value="">{{$t('sum'+item.oddsKey)}}
                  </th>
                  <td @click="selectOdds(item)" :class="item.choose?'oddsSelect':''" class="odds">
                    {{betState&&item.status=='0'?oddsReturn(item):'封盘'}}
                  </td>
                  <td v-show="!pageQuickBetStatus" :class="item.choose?'oddsSelect':''" class="amount ha">
                    <input type="text" oninput="this.value = this.value.replace(/\D/g, '').replace(/^0{1,}/g, '');" maxlength="6"
                           v-model="item.betAmt" @focus="focusFun(item)"
                           v-bind:id="item.oddsId" v-bind:ref="item.oddsId" @input="inputBetAmt(item)"
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
          <template v-for="(list,index) in kl10Odds.integration">
            <tr >
              <template v-for="(item,key) in list">
                <template v-if="key>2">
                  <th @click="selectOdds(item)" :class="item.choose?'oddsSelect':''" class="name"
                      :title=" $t('sum'+item.oddsKey)"><input type="hidden" value="">{{$t('sum'+item.oddsKey)}}
                  </th>
                  <td @click="selectOdds(item)" :class="item.choose?'oddsSelect':''" class="odds">
                    {{betState&&item.status=='0'?oddsReturn(item):'封盘'}}
                  </td>
                  <td v-show="!pageQuickBetStatus" :class="item.choose?'oddsSelect':''" class="amount ha">
                    <input type="text" oninput="this.value = this.value.replace(/\D/g, '').replace(/^0{1,}/g, '');" maxlength="6"
                           v-model="item.betAmt" @focus="focusFun(item)"
                           v-bind:id="item.oddsId" v-bind:ref="item.oddsId" @input="inputBetAmt(item)"
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
        <!--第一球到八球 二行四列-->
        <div class="split_panels" v-show="categorySelect=='integration'">
          <!-- 从这里开始 一行四 分二行-->
          <template v-for="(list,i) in kl10Odds.oneToEight">
            <table style="width:25%;float:left" v-if="i.substring(i.length-1)<5">
              <tbody>
              <tr class="head">
                <th colspan="3">{{$t(i)}}</th>
              </tr>
              <template v-for="(item,index) in list">
                <tr v-if="item.categoryKey=='lm'">
                  <th @click="selectOdds(item)" :class="item.choose?'oddsSelect':''" class="name"
                      :title=" $t(item.oddsKey)"><input type="hidden" value="">{{$t(item.oddsKey)}}
                  </th>
                  <td @click="selectOdds(item)" :class="item.choose?'oddsSelect':''" class="odds">
                    {{betState&&item.status=='0'?oddsReturn(item):'封盘'}}
                  </td>
                  <td v-show="!pageQuickBetStatus" :class="item.choose?'oddsSelect':''" class="amount ha">
                    <input type="text" oninput="this.value = this.value.replace(/\D/g, '').replace(/^0{1,}/g, '');" maxlength="6"
                           v-model="item.betAmt" @focus="focusFun(item)"
                           v-bind:id="item.oddsId" v-bind:ref="item.oddsId" @input="inputBetAmt(item)"
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
          <template v-for="(list,i) in kl10Odds.oneToEight">
            <table style="width:25%;float:left" v-if="i.substring(i.length-1)>=5">
              <tbody>
              <tr class="head">
                <th colspan="3">{{$t(i)}}</th>
              </tr>
              <template v-for="(item,index) in list">
                <tr v-if="item.categoryKey=='lm'">
                  <th @click="selectOdds(item)" :class="item.choose?'oddsSelect':''" class="name"
                      :title=" $t(item.oddsKey)"><input type="hidden" value="">{{$t(item.oddsKey)}}
                  </th>
                  <td @click="selectOdds(item)" :class="item.choose?'oddsSelect':''" class="odds">
                    {{betState&&item.status=='0'?oddsReturn(item):'封盘'}}
                  </td>
                  <td v-show="!pageQuickBetStatus" :class="item.choose?'oddsSelect':''" class="amount ha">
                    <input type="text" oninput="this.value = this.value.replace(/\D/g, '').replace(/^0{1,}/g, '');" maxlength="6"
                           v-model="item.betAmt" @focus="focusFun(item)"
                           v-bind:id="item.oddsId" v-bind:ref="item.oddsId" @input="inputBetAmt(item)"
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
      <!--单球1～8-->
      <div v-show="categorySelect=='oneToEight'" class="bet_panel input_panel">
        <table width="100%" :class="classResult">
          <tbody>
          <tr class="head">
            <th></th>
            <th :colspan="pageQuickBetStatus?'1':'2'">第一球</th>
            <th :colspan="pageQuickBetStatus?'1':'2'">第二球</th>
            <th :colspan="pageQuickBetStatus?'1':'2'">第三球</th>
            <th :colspan="pageQuickBetStatus?'1':'2'">第四球</th>
            <th :colspan="pageQuickBetStatus?'1':'2'">第五球</th>
            <th :colspan="pageQuickBetStatus?'1':'2'">第六球</th>
            <th :colspan="pageQuickBetStatus?'1':'2'">第七球</th>
            <th :colspan="pageQuickBetStatus?'1':'2'">第八球</th>
          </tr>
          <tr  v-show="pageQuickBetStatus">
            <td></td>
          <template v-for="(list,i) in kl10Odds.oneToEight">
              <td  @click="allSearchFun(list,i)" :class="!allSearchList[i]?'':'oddsSelect'" :colspan="i==1?'2':'1'">
                <label  class="checkdefault mlwit">
                  <input :disabled="!betState?true:false" type="checkbox" v-model="allSearchList[i]" class="checkbox">
                  <span @click="allSearchFun(list,i)" style="cursor:pointer" class="color_lv bold">{{betState?'全选':'封盘'}}</span>
                </label>
              </td>

          </template>
          </tr>
          <tr class="head">
            <th>号</th>
            <th>赔率</th>
            <th v-show="!pageQuickBetStatus">金额</th>
            <th>赔率</th>
            <th v-show="!pageQuickBetStatus">金额</th>
            <th>赔率</th>
            <th v-show="!pageQuickBetStatus">金额</th>
            <th>赔率</th>
            <th v-show="!pageQuickBetStatus">金额</th>
            <th>赔率</th>
            <th v-show="!pageQuickBetStatus">金额</th>
            <th>赔率</th>
            <th v-show="!pageQuickBetStatus">金额</th>
            <th>赔率</th>
            <th v-show="!pageQuickBetStatus">金额</th>
            <th>赔率</th>
            <th v-show="!pageQuickBetStatus">金额</th>
          </tr>

          <template v-for="(num,numKey) in 20">
            <tr>
              <td class="name"><span :class="'b'+num">{{num}}</span></td>
              <template v-for="(list,i) in kl10Odds.oneToEight">
                <template v-if="item.oddsKey == num" v-for="(item,key) in list">
                    <td @click="selectOdds(item)" :class="item.choose?'oddsSelect':''" class="odds">{{betState&&item.status=='0'?oddsReturn(item):'封盘'}}</td>
                    <td v-show="!pageQuickBetStatus" :class="item.choose?'oddsSelect':''" class="amount ha">
                      <input type="text" oninput="this.value = this.value.replace(/\D/g, '').replace(/^0{1,}/g, '');" maxlength="6"
                             v-model="item.betAmt" @focus="focusFun(item)"
                             v-bind:id="item.oddsId" v-bind:ref="item.oddsId" @input="inputBetAmt(item)"
                             @blur="blurFun(item)" class="ba" style="width: 30px;">
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
      </div>
      <div v-show="categorySelect.indexOf('qiu')!=-1" class="bet_panel input_panel">
        <!--第一球 到第八球一样-->
        <table :class="classResult">
          <tbody>
          <tr class="head">
            <th>号码</th>
            <th>赔率</th>
            <th v-show="!pageQuickBetStatus" class="ha">金额</th>
            <th>号码</th>
            <th>赔率</th>
            <th v-show="!pageQuickBetStatus" class="ha">金额</th>
            <th>号码</th>
            <th>赔率</th>
            <th v-show="!pageQuickBetStatus" class="ha">金额</th>
            <th>号码</th>
            <th>赔率</th>
            <th v-show="!pageQuickBetStatus" class="ha">金额</th>
          </tr>
          <template v-for="(list,i) in oneToEightOddsResult">
            <tr>
              <template v-for="(item,index) in list">
                <th @click="selectOdds(item)" :class="item.choose?'oddsSelect':''" class="name"
                    :title="item.oddsKey"><input type="hidden" value="">
                  <template v-if="/^[0-9]\d*$/.test(item.oddsKey)">
                    <span :class="'b'+item.oddsKey">{{item.oddsKey}}</span>
                  </template>
                  <template v-else>
                    {{$t(item.oddsKey)}}
                  </template>
                </th>
                <td @click="selectOdds(item)" :class="item.choose?'oddsSelect':''" class="odds">
                  {{betState&&item.status=='0'?oddsReturn(item):'封盘'}}
                </td>
                <td v-show="!pageQuickBetStatus" :class="item.choose?'oddsSelect':''" class="amount ha">
                  <input type="text" oninput="this.value = this.value.replace(/\D/g, '').replace(/^0{1,}/g, '');" maxlength="6"
                         v-model="item.betAmt" @focus="focusFun(item)"
                         v-bind:id="item.oddsId" v-bind:ref="item.oddsId" @input="inputBetAmt(item)"
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

              <!--<th class="name" title="第一球 16"><input type="hidden" value=""><span class="b16">16</span></th>

              <td class="odds">19.56</td>
              <td class="amount ha"><input  class="ba"></td>-->
            </tr>
          </template>
          </tbody>
        </table>

      </div>


      <div v-show="categorySelect=='continuousCode'" class="bet_panel input_panel">
        <!--连码-->
        <table class="games">
          <tbody>
          <tr class="head">
            <template v-for="(item,index) in kl10Odds.continuousCode['lianma']">
              <th><input type="radio" v-model="continuousCodeSelect" name="game" :value="item.oddsKey" :title="$t(item.oddsKey)"></th>
            </template>
          </tr>
          <tr>


            <template v-for="(item,index) in kl10Odds.continuousCode['lianma']">
              <td>{{$t(item.oddsKey)}}</td>
            </template>
          </tr>
          <tr>
            <template v-for="(item,index) in kl10Odds.continuousCode['lianma']">
              <td class="odds">{{betState&&item.status=='0'?oddsReturn(item):'封盘'}}</td>
            </template>
          </tr>
          </tbody>
        </table>

        <div class="status_panel">
          <table :class="classResult">
            <tbody>
            <tr class="head">
              <th>号</th>
              <th>选择</th>
              <th>号</th>
              <th>选择</th>
              <th>号</th>
              <th>选择</th>
              <th>号</th>
              <th>选择</th>
            </tr>
            <template v-for="(list,i) in continuousCodeList">
              <tr>
                <template v-for="(item,index) in list">
                  <th class="name"><span :class="'b'+item.oddsKey">{{item.oddsKey}}</span></th>
                  <td :class="item.choose?'oddsSelect':''" @click="lianmaSelectOdds(item)" class="check">
                    <input v-bind:disabled="(lmOddsSelectStatus && !item.check) || !betState?'disabled':false" type="checkbox" v-model="item.check" value=""></td>

                </template>
              </tr>
            </template>
            </tbody>
          </table>
        </div>
      </div>

      <div class="control bcontrol">
        <div class="lefts" style="">已经选中 <span id="betcount"></span>{{selectList.length}}注</div>
        <div class="buttons">
          <input type="button" class="button2" value="快选金额" @click="quickSum">
          <label class="checkdefault"><input type="checkbox" v-model="pagePresetStatus" @change="betAmtChange" class="checkbox"><span
            class="color_lv bold">预设</span></label>&nbsp;&nbsp;<label
          id="quickAmount" class="quickAmount"><span class="color_lv bold">金额</span> <input type="text"
                                                                                            oninput="this.value = this.value.replace(/\D/g, '').replace(/^0{1,}/g, '');"
                                                                                            @keyup="inputSetAmt" maxlength="6"
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
  import statistics from '@/components/klsf/statistics'
  import Utils from '@/components/comm/Utils.js'
  import {Indicator} from 'mint-ui'
  export default {
    name: "klsf",
    components: {
      gameInfo,
      statistics
    },
    data(){
      return{
        pagePresetAmt:'',
        pagePresetStatus:false,
        continuousCodeSelect:'x2',
        continuousCodeList:[],
        oneToEightallSelectBox:false,
        lotteryPageFlag:true,
        continuousCodeSelectList:[],
        pageQuickBetStatus:false,
        lmOddsSelectStatus:false,
        allSearchList:{qiu1:false,qiu2:false,qiu3:false,qiu4:false,qiu5:false,qiu6:false,qiu7:false,qiu8:false},
        inputAmtTimeOut:null
      }
    },
    computed:{
      ...mapGetters(['kl10Odds','quickBetStatus','betAmtPreSet','betAmtStatus', 'quickSumStatus', 'quickSumList', 'betState', 'betGameNo', 'noteLists', 'gameInfo', 'selectList', 'gameId', 'categorySelect', 'categorySelect','userOddsCljps','userOddsCloses','userOddsJumps','userOddsNows','userOddss']),
      classResult(){
        if(this.gameId==301 || this.gameId==302 || this.gameId==304){
          return 'table_ball klsf';
        }else if(this.gameId==303){
          return 'table_ball xync';
        }
      },
      oneToEightOddsResult(){

        let self = this;
        let resultObj = [];
        let arr1 = [];
        let arr2 = [];
        let arr3 = [];
        let arr4 = [];
        let arr5 = [];
        if(self.categorySelect.indexOf('qiu')!=-1){

          for(let i = 0;i<self.kl10Odds.oneToEight[self.categorySelect].length;i++){
            if(self.kl10Odds.oneToEight[self.categorySelect][i]){
              self.$set(self.kl10Odds.oneToEight[self.categorySelect][i],'odds',self.oddsReturn(self.kl10Odds.oneToEight[self.categorySelect][i]));
            }
            if(i<20){

              arr1.push(self.kl10Odds.oneToEight[self.categorySelect][i]);
              if(arr1.length==4){
                resultObj.push(arr1);
                arr1 = [];
              }
            }else{
              if(i==20 || i==22|| i==24 || i==26){
                arr1.push(self.kl10Odds.oneToEight[self.categorySelect][i]);
              }else if(i==21 || i==23|| i==25|| i==27){
                arr2.push(self.kl10Odds.oneToEight[self.categorySelect][i]);
              }else if(i>27 && i<32){
                arr3.push(self.kl10Odds.oneToEight[self.categorySelect][i]);
              }else if(i>31 && i < 35){
                arr4.push(self.kl10Odds.oneToEight[self.categorySelect][i]);
              }else{
                arr5.push(self.kl10Odds.oneToEight[self.categorySelect][i]);
              }
            }

          }
        }
        resultObj.push(arr1,arr2,arr3,arr4,arr5);
        return resultObj;

      },
      zmOddsResult(){

        let self = this;
        let resultObj = [];
        let arr1 = [];
        if(self.categorySelect == 'positiveCode'){
          for(let i = 0;i<self.kl10Odds.positiveCode['zm'].length;i++){
            if(self.kl10Odds.positiveCode['zm'][i]){
              self.$set(self.kl10Odds.positiveCode['zm'][i],'odds',self.kl10Odds.positiveCode['zm'][i]);
            }
            if(i<20){
              arr1.push(self.kl10Odds.positiveCode['zm'][i]);
              if(arr1.length==4){
                resultObj.push(arr1);
                arr1 = [];
              }
            }
          }
        }
        return resultObj;

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
        if(self.categorySelect=='continuousCode'){
          self.continuousCodeList=[];
          let arr = [];
          for(let i = 1;i<=5;i++){
            let obj = {};
            self.$set(obj,"oddsKey",i);
            self.$set(obj,"choose",false);
            self.$set(obj,"check",false);
            arr.push(obj);
            obj = {};
            self.$set(obj,"oddsKey",i+5);
            self.$set(obj,"choose",false);
            self.$set(obj,"check",false);
            arr.push(obj);
            obj = {};
            self.$set(obj,"oddsKey",i+5*2);
            self.$set(obj,"choose",false);
            self.$set(obj,"check",false);
            arr.push(obj);
            obj = {};
            self.$set(obj,"oddsKey",i+5*3);
            self.$set(obj,"choose",false);
            self.$set(obj,"check",false);
            arr.push(obj);

            if(arr.length==4){
              self.continuousCodeList.push(arr);
              arr=[];
            }
          }
          self.continuousCodeSelectList = [];
        }
        for(let key in self.continuousCodeList){
          self.continuousCodeList[key].forEach(val=>{
            self.$delete(val,"choose");
            self.$delete(val,"check");
          })
        }
      },
      continuousCodeSelect(){
        let self = this;
        self.lmOddsSelectStatus = false;
        if(self.continuousCodeList.length>0){
          for(let key in self.continuousCodeList){
            self.continuousCodeList[key].forEach(val=>{
              self.$delete(val,"choose");
              self.$delete(val,"check");
            })
          }
        }
        self.continuousCodeSelectList = [];
      },
      pagePresetAmt() {
        let self = this;
        if (self.pagePresetStatus) {
          self.setBetAmtPreSet(self.pagePresetAmt);
        }
      },
    },
    methods:{
      ...mapActions([ 'setPromptInformation','setQuickBetStatus','setBetAmtPreSet','setBetAmtStatus','setQuickSumList', 'setBetGameNo', 'setSelectList', 'setCategorySelect','setSelectListFast']),

      allSelectOnetoEightFun(){
        let self = this;
        if(!this.betState){
          return;
        }
        self.oneToEightallSelectBox = !self.oneToEightallSelectBox;
        if(self.oneToEightallSelectBox){
          for(let item in self.kl10Odds.oneToEight){

            self.kl10Odds.oneToEight[item].forEach(val=>{
              if(val.categoryKey=='ydb' && val.status=='0'){
                if(!val.check && !val.choose){
                  self.selectOdds(val);
                }
              }
            })
          }

        }else{
          for(let item in self.kl10Odds.oneToEight){
            self.kl10Odds.oneToEight[item].forEach(val => {
              if (val.categoryKey == 'ydb' && val.status == '0') {
                self.cancelChooseOdds(val);
              }
            })
          }
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
      closeMarketFun(){
        let self = this;

        self.initSelectList();
        for(let key in self.continuousCodeList){
          self.continuousCodeList[key].forEach(val=>{
            self.$delete(val,"choose");
            self.$delete(val,"check");
          })
        }
      },
      quickBetStatusChange() {
        this.setQuickBetStatus(this.pageQuickBetStatus);
        this.allSearchList={qiu1:false,qiu2:false,qiu3:false,qiu4:false,qiu5:false,qiu6:false,qiu7:false,qiu8:false};
        this.setSelectList(null);
      },
      betAmtChange(){
        this.setBetAmtStatus(this.pagePresetStatus);
        if(this.betAmtStatus){
          this.setBetAmtPreSet(this.pagePresetAmt);
        }else{
          this.setBetAmtPreSet('');
        }
      },
      lianmaSelectOdds(item){
        let self = this;
        if (!this.betState || item.status == '1') {
          return;
        }
        if(((self.continuousCodeSelect =='x2' && self.continuousCodeSelectList.length==2) ||
          (self.continuousCodeSelect =='x2lz' && self.continuousCodeSelectList.length==2)||
          (self.continuousCodeSelect =='x2lg' && self.continuousCodeSelectList.length==2)) && !item.choose){
          return;
        }else if(((self.continuousCodeSelect =='x3' && self.continuousCodeSelectList.length==3) ||
          (self.continuousCodeSelect =='x3topz' && self.continuousCodeSelectList.length==3)||
          (self.continuousCodeSelect =='x3topg' && self.continuousCodeSelectList.length==3)) && !item.choose){
          return;
        }else if((self.continuousCodeSelect =='x4' && self.continuousCodeSelectList.length==4) && !item.choose){
          return;
        }else if((self.continuousCodeSelect =='x5' && self.continuousCodeSelectList.length==5) && !item.choose){
          return;
        }


        this.$set(item, 'choose', !item.choose);
        this.$set(item,'check' ,!item.check);
        if(item.check){
          this.continuousCodeSelectList.push(item);
        }else{
          let returnIndex = this.continuousCodeSelectList.findIndex((value,index,arr)=>{
            return value.oddsKey == item.oddsKey;
          })
          this.continuousCodeSelectList.splice(returnIndex,1);
        }

        if(((self.continuousCodeSelect =='x2' && self.continuousCodeSelectList.length==2) ||
          (self.continuousCodeSelect =='x2lz' && self.continuousCodeSelectList.length==2)||
          (self.continuousCodeSelect =='x2lg' && self.continuousCodeSelectList.length==2))){
          self.lmOddsSelectStatus = true;
          return;
        }else if(((self.continuousCodeSelect =='x3' && self.continuousCodeSelectList.length==3) ||
          (self.continuousCodeSelect =='x3topz' && self.continuousCodeSelectList.length==3)||
          (self.continuousCodeSelect =='x3topg' && self.continuousCodeSelectList.length==3)) ){
          self.lmOddsSelectStatus = true;
          return;
        }else if((self.continuousCodeSelect =='x4' && self.continuousCodeSelectList.length==4)){
          self.lmOddsSelectStatus = true;
          return;
        }else if((self.continuousCodeSelect =='x5' && self.continuousCodeSelectList.length==5) ){
          self.lmOddsSelectStatus = true;
          return;
        }else{
          self.lmOddsSelectStatus = false;
        }

      },
      focusFun(item) {
        if (this.quickSumStatus && this.betState) {
          this.$set(item, 'quickSumStatus', true);
        }
      },
      stopQuickSum() {
        let self = this;
        self.$messageBox.close();
        self.$messageBox({$type:'confirm',message:'确认停用吗？',title:'提示',closeOnClickModal:false,showCancelButton:true}).then(action => {
          if (Object.is(action, 'confirm')) {
            self.setQuickSumList({'list': self.quickSumList, 'status': 0});
            this.setPromptInformation('停用成功!').then(

            ).catch(error => {

            });
          }
        });
      },
      blurFun(item) {
        let self = this;
        if (!this.betState || item.status == '1') {
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
        let self = this;
        if(self.categorySelect=='continuousCode'){
          if((self.continuousCodeSelect =='x2' && self.continuousCodeSelectList.length!=2) ||
            (self.continuousCodeSelect =='x2lz' && self.continuousCodeSelectList.length!=2)||
            (self.continuousCodeSelect =='x2lg' && self.continuousCodeSelectList.length!=2)){
            return self.setPromptInformation('选二系列只能勾选两个号码！');
          }else if((self.continuousCodeSelect =='x3' && self.continuousCodeSelectList.length!=3) ||
            (self.continuousCodeSelect =='x3topz' && self.continuousCodeSelectList.length!=3)||
            (self.continuousCodeSelect =='x3topg' && self.continuousCodeSelectList.length!=3)){
            return self.setPromptInformation('选三系列只能勾选三个号码！');
          }else if(self.continuousCodeSelect =='x4' && self.continuousCodeSelectList.length!=4){
            return self.setPromptInformation('选四系列只能勾选四个号码！');
          }else if(self.continuousCodeSelect =='x5' && self.continuousCodeSelectList.length!=5){
            return self.setPromptInformation('选五系列只能勾选五个号码！');
          }
          let betObj = self.kl10Odds.continuousCode['lianma'].find(val=>{
            return (val.oddsKey == self.continuousCodeSelect);
          })
          let betContent = [];
          self.continuousCodeSelectList.forEach(val=>{
            betContent.push(val.oddsKey);
          });
          self.$set(betObj,"choose",true);
          self.$set(betObj,"check",true);
          self.$set(betObj,"betContent",betContent.toString());
          self.$set(betObj,"betAmt",self.pagePresetAmt);
          if(!betObj.betAmt){
            return self.setPromptInformation('请输入下注金额！');
          }
          let arr = [];
          arr.push(betObj);
          self.setSelectListFast(arr);
        }
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

        if (!self.kl10Odds) {
          console.log('pk10Odds is undefined');
          return;
        }
        for(let key in self.kl10Odds.integration){
          self.kl10Odds.integration[key].forEach((obj) => {
            self.$delete(obj, "choose");
            self.$delete(obj,"check");
            self.$delete(obj, "betAmt");
          });
        }

        for(let key in self.kl10Odds.oneToEight){
          self.kl10Odds.oneToEight[key].forEach((obj) => {
            self.$delete(obj, "choose");
            self.$delete(obj,"check");
            self.$delete(obj, "betAmt");
          });
        }
        for(let key in self.kl10Odds.positiveCode){
          self.kl10Odds.positiveCode[key].forEach((obj) => {
            self.$delete(obj, "choose");
            self.$delete(obj,"check");
            self.$delete(obj, "betAmt");
          });
        }
        if(self.continuousCodeList.length>0){
          for(let key in self.continuousCodeList){
            self.continuousCodeList[key].forEach(val=>{
              self.$delete(val,"choose");
              self.$delete(val,"check");
            })
          }
        }
        self.continuousCodeSelectList = [];
        self.allSearchList={qiu1:false,qiu2:false,qiu3:false,qiu4:false,qiu5:false,qiu6:false,qiu7:false,qiu8:false};
        self.oneToEightallSelectBox = false;
        self.lmOddsSelectStatus = false;
        self.setSelectList(null);

      },
      selectOdds(item) {
        if (!this.betState || item.status == '1') {
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
      }
    },
    mounted(){
      let self = this;
      if(!self.categorySelect){
        self.setCategorySelect('integration');
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
    }
  }
</script>

<style scoped>
  .oddsSelect {
    background-color: #fff100 !important;
  }
</style>
