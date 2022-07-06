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
          <input type="button" id="betButton" ref="betButton" @click="bet()" value="确定" class="button">
          <input type="button" @click="resetBetAmt()" value="重置" class="button">
        </div>
      </div>
      <div id="bet_panel" class="bet_panel input_panel">
        <table v-show="categorySelect=='nnp'" class="table_lm">
          <template v-for="(list, title) in sscOdds.lm">
            <tr v-if="title=='t_lhh'" class="head">
              <th colspan="15">{{$t(title)}}</th>
            </tr>

            <tr>
              <template v-if="title=='t_lhh'" v-for="(item,index) in list">
                <template v-if="index<4">
                  <th @click="selectOdds(item)" :class="item.choose?'oddsSelect':''" class="name" :title="item.oddsKey">
                    <input type="hidden" value="">{{$t('ssclmzh'+item.oddsKey)}}
                  </th>
                  <td @click="selectOdds(item)" :class="item.choose?'oddsSelect':''" :style="pageQuickBetStatus?'width:144px;':''" class="odds">
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
            <tr>
              <template v-if="title=='t_lhh'" v-for="(item,index) in list">
                <template v-if="index>=4">
                  <th @click="selectOdds(item)" :class="item.choose?'oddsSelect':''" class="name" :title="item.oddsKey">
                    <input type="hidden" value="">{{$t(item.oddsKey)}}
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
              <template v-if="title=='t_lhh'">
                <th class="name empty"></th>
                <td :style="pageQuickBetStatus?'width:144px;':''" class="odds empty"></td>
                <td v-show="!pageQuickBetStatus" class="amount ha"></td>
              </template>
            </tr>

          </template>
        </table>
        <!--  一到五球-->
        <div v-show="categorySelect=='nnp'" class="split_panel">
          <!--循环table-->
          <template v-for="(list, title) in sscOdds.lm">
            <table v-if="title.indexOf('qiu')!=-1">
              <tbody>
              <tr class="head">
                <th colspan="3">{{$t(title)}}</th>
              </tr>
              <template v-for="(item,index) in list">
                <tr>
                  <th @click="selectOdds(item)" :class="item.choose?'oddsSelect':''" class="name" :title="item.oddsKey">
                    <input type="hidden" value="">{{$t(item.oddsKey)}}
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


        <!--  前三 中三 后三 注意分开-->
        <template v-for="(list, title) in sscOdds.lm">
          <table v-if="title!='t_lhh' && title.indexOf('qiu')==-1" v-show="categorySelect=='nnp'" class="table_ts mt10">
            <tbody>
            <tr class="head">
              <th colspan="15">{{$t(title)}}</th>
            </tr>
            <tr>
              <template v-for="(item,index) in list">
                <th @click="selectOdds(item)" :class="item.choose?'oddsSelect':''" class="name" :title="item.oddsKey">
                  <input type="hidden" value="">{{$t(item.oddsKey)}}
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
            </tr>
            </tbody>
          </table>
        </template>
        <!--  1-5-->
        <div v-show="categorySelect=='ball'" class="split_panel ssc">
          <!--循环table-->
          <template v-for="(list,title) in 5">
            <table class="table_ts mt10">

              <tr class="head">
                <th colspan="3">{{$t('ssc'+list)}}</th>
              </tr>
              <tr @click="allSearchFun('qiu'+list,title)"  v-show="pageQuickBetStatus">
                <td :class="!allSearchList[title]?'':'oddsSelect'" colspan="3">
                  <label class="checkdefault mlwit">
                    <input type="checkbox" :disabled="betState?false:true" v-model="allSearchList[title]" class="checkbox">
                    <span @click="allSearchFun('qiu'+list,title)" style="cursor:pointer" class="color_lv bold">{{betState?'全选':'封盘'}}</span>
                  </label>
                </td>
              </tr>
              <tbody>
              <template v-for="(item,index) in sscOdds['qiu'+list]">
                <tr>
                  <th @click="selectOdds(item)" :class="item.choose?'oddsSelect':''" class="name" :title="item.oddsKey">
                    <input type="hidden" value=""><span v-if="/^[0-9]\d*$/.test(item.oddsKey)" :class="'b'+item.oddsKey">{{item.oddsKey}}</span>
                    <template v-else>{{$t(item.oddsKey)}}</template>
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

        <!--  一字组合(前三)-->
        <table v-show="categorySelect=='onezh'" class="table_ball">
          <tbody>
          <!--循环-->
          <tr class="head">
            <th colspan="20">一字组合(前三)</th>
          </tr>
          <tr>
            <template v-for="(item,i) in sscOdds.onezzh">

              <template v-if="item.categoryKey==='top3' && i%2==0">
                <th @click="selectOdds(item)" :class="item.choose?'oddsSelect':''" class="name"
                    :title="'前三'+item.oddsKey">
                  <input type="hidden" value="">{{$t(item.oddsKey)}}
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
          <tr>
            <template v-for="(item,i) in sscOdds.onezzh">
              <template v-if="item.categoryKey==='top3' && i%2!=0">
                <th @click="selectOdds(item)" :class="item.choose?'oddsSelect':''" class="name"
                    :title="'前三'+item.oddsKey">
                  <input type="hidden" value="">{{$t(item.oddsKey)}}
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

          <tr class="head">
            <th colspan="20">一字组合(中三)</th>
          </tr>
          <tr>
            <template v-for="(item,i) in sscOdds.onezzh">
              <template v-if="item.categoryKey==='mid3' && i%2==0">
                <th @click="selectOdds(item)" :class="item.choose?'oddsSelect':''" class="name"
                    :title="'中三'+item.oddsKey">
                  <input type="hidden" value="">{{$t(item.oddsKey)}}
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
          <tr>
            <template v-for="(item,i) in sscOdds.onezzh">
              <template v-if="item.categoryKey==='mid3' && i%2!=0">
                <th @click="selectOdds(item)" :class="item.choose?'oddsSelect':''" class="name"
                    :title="'中三'+item.oddsKey">
                  <input type="hidden" value="">{{$t(item.oddsKey)}}
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

          <tr class="head">
            <th colspan="20">一字组合(后三)</th>
          </tr>
          <tr>
            <template v-for="(item,i) in sscOdds.onezzh">
              <template v-if="item.categoryKey==='end3' && i%2==0">
                <th @click="selectOdds(item)" :class="item.choose?'oddsSelect':''" class="name"
                    :title="'后三'+item.oddsKey">
                  <input type="hidden" value="">{{$t(item.oddsKey)}}
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
          <tr>
            <template v-for="(item,i) in sscOdds.onezzh">
              <template v-if="item.categoryKey==='end3' && i%2!=0">
                <th @click="selectOdds(item)" :class="item.choose?'oddsSelect':''" class="name"
                    :title="'后三'+item.oddsKey">
                  <input type="hidden" value="">{{$t(item.oddsKey)}}
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

          <tr class="head">
            <th colspan="20">一字组合(全五)</th>
          </tr>
          <tr>
            <template v-for="(item,i) in sscOdds.onezzh">
              <template v-if="item.categoryKey==='all5' && i%2==0">
                <th @click="selectOdds(item)" :class="item.choose?'oddsSelect':''" class="name"
                    :title="'全五'+item.oddsKey">
                  <input type="hidden" value="">{{$t(item.oddsKey)}}
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
          <tr>
            <template v-for="(item,i) in sscOdds.onezzh">
              <template v-if="item.categoryKey==='all5' && i%2!=0">
                <th @click="selectOdds(item)" :class="item.choose?'oddsSelect':''" class="name"
                    :title="'全五'+item.oddsKey">
                  <input type="hidden" value="">{{$t(item.oddsKey)}}
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
          <!--结束-->
          </tbody>
        </table>

        <!--  二字组合-->
        <template v-show="categorySelect=='twozh'">
          <ul v-show="categorySelect=='twozh'" class="tab">
            <li class="tab_title02">
              <a style="cursor:pointer" :class="twozhSelect=='top3'?'on':''" @click="twozhChanges('top3')">二字前三</a>
              <a style="cursor:pointer" :class="twozhSelect=='mid3'?'on':''" @click="twozhChanges('mid3')">二字中三</a>
              <a style="cursor:pointer" :class="twozhSelect=='end3'?'on':''" @click="twozhChanges('end3')">二字后三</a>
            </li>
          </ul>
          <!--  二字前三-->
          <div v-show="categorySelect=='twozh'" class="split_panel">
            <!--循环table-->
            <template v-for="(i,index) in 5">
              <table class="table_ts mt10">
                <tbody>

                <tr class="head">
                  <th style="width: 30px;">种类</th>
                  <th>赔率</th>
                  <th v-show="!pageQuickBetStatus" class="ha">金额</th>
                </tr>
                <template v-for="(item,key) in twozhResultOdds[index]">
                  <tr>
                    <th @click="selectOdds(item)" :class="item.choose?'oddsSelect':''" class="name"
                        :title="$t('2z'+twozhSelect)+item.oddsKey">
                      <input type="hidden" value="">{{item.oddsKey}}
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
        </template>
        <!--  三字组合-->

        <template v-show="categorySelect=='threezh'">
          <ul v-show="categorySelect=='threezh'" class="tab">
            <li class="tab_title02">
              <a style="cursor:pointer" :class="threezhSelect=='top3'?'on':''" @click="threezhChanges('top3')">三字前三</a>
              <a style="cursor:pointer" :class="threezhSelect=='mid3'?'on':''" @click="threezhChanges('mid3')">三字中三</a>
              <a style="cursor:pointer" :class="threezhSelect=='end3'?'on':''" @click="threezhChanges('end3')">三字后三</a>
            </li>
          </ul>
          <table v-show="categorySelect=='threezh'" class="quick_sec_table_fw" id="quick_sec_table_fw">
            <tbody>
            <tr>
              <td class="t">
                范围定位
              </td>
              <td class="c">
                开始
                <input class="fw_b text input_focus" v-model="threezhRangeStart" type="text"
                       oninput="this.value = this.value.replace(/[^0-9]/g, '');" maxlength='3'>
                ~
                结束
                <input class="fw_e text" type="text" v-model="threezhRangeStop"
                       oninput="this.value = this.value.replace(/[^0-9]/g, '');" maxlength='3'>
                <input type="button" value="确定" @click="threezhRange" name="btnRange" class="button btn_red">
              </td>
            </tr>
            </tbody>
          </table>
          <!--  三字前三-->
          <div v-show="categorySelect=='threezh'" class="split_panel">
            <!--循环table-->
            <template v-for="(i,index) in 5">
              <table class="table_ts mt10">
                <tbody>

                <tr class="head">
                  <th style="width: 30px;">种类</th>
                  <th>赔率</th>
                  <th v-show="!pageQuickBetStatus" class="ha">金额</th>
                </tr>
                <template v-for="(item,key) in threezhResultOdds[i-1]">
                  <tr>
                    <th @click="selectOdds(item)" :class="item.choose?'oddsSelect':''" class="name"
                        :title="$t('2z'+twozhSelect)+item.oddsKey">
                      <input type="hidden" value="">{{item.oddsKey}}
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
        </template>


        <!--  三字前三-->
        <!--  跟二字一样了-->

        <!--  二字定位-->
        <template v-show="categorySelect=='twodw'">
          <ul v-show="categorySelect=='twodw'" class="tab">
            <li class="tab_title02">
              <template v-for="(item,index) in sscOdds.twozdw">
                <a style="cursor:pointer" @click="twodwSelect=item.oddsKey" :class="twodwSelect==item.oddsKey?'on':''">{{$t(item.oddsKey)}}</a>
              </template>
            </li>
          </ul>
          <table v-show="categorySelect=='twodw'" class="tab_title02">
            <tbody>
            <tr class="head">
              <th>种类</th>
              <th>万千</th>
            </tr>
            <tr>
              <td>赔率</td>
              <td>
                <span class="odds">
                  <template v-for="(item,index) in sscOdds.twozdw">
                    <template v-if="item.oddsKey==twodwSelect">
                      {{betState&&item.status=='0'?oddsReturn(item):'封盘'}}
                    </template>
                  </template>
                </span>
              </td>
            </tr>
            </tbody>
          </table>

          <ul v-show="categorySelect=='twodw'" class="tab">
            <li class="tab_title02">
              <a id="fastChoose1" @click="twodwSelectMode='firstKind'" :class="twodwSelectMode=='firstKind'?'on':''"
                 style="width:150px;cursor:pointer;">{{$t(twodwSelect)}}位快选</a>
              <a id="fastChoose2" @click="twodwSelectMode='secondKinds'" :class="twodwSelectMode=='secondKinds'?'on':''"
                 style="width:150px;cursor:pointer;">选项快选</a>
            </li>
          </ul>

          <div v-show="categorySelect=='twodw' && twodwSelectMode=='firstKind'" class="status_panel">
            <table class="table_ball ssc tab_title02">
              <tbody>
              <tr class="head">
                <th colspan="12">{{$t(twodwFirstKindList[0].key)}}</th>
              </tr>
              <tr class="head">
                <th>球</th>
                <th>选择</th>
                <th>球</th>
                <th>选择</th>
                <th>球</th>
                <th>选择</th>
                <th>球</th>
                <th>选择</th>
                <th>球</th>
                <th>选择</th>
              </tr>
              <tr>
                <template v-for="(item,key) in twodwFirstKindList[0].list">
                  <template v-if="key<=4">
                    <th class="name" :title="item.oddsKey"><span :class="'b'+item.oddsKey">{{item.oddsKey}}</span></th>
                    <td :class="item.choose?'oddsSelect':''" v-bind:disabled="(twodwFirstOneStatus && !item.check) || !betState?'disabled':false" @click="twoSecondKindsFun(item,twodwFirstKindList[0].key)" class="check"><input
                      type="checkbox" :disabled="betState?false:true" v-model="item.check" v-bind:disabled="(twodwFirstOneStatus && !item.check) || !betState?'disabled':false" name="chk0" value="0"></td>
                  </template>
                </template>
              </tr>
              <tr>
                <template v-for="(item,key) in twodwFirstKindList[0].list">
                  <template v-if="key>4">
                    <th class="name" :title="item.oddsKey"><span :class="'b'+item.oddsKey">{{item.oddsKey}}</span></th>
                    <td :class="item.choose?'oddsSelect':''" v-bind:disabled="(twodwFirstOneStatus && !item.check) || !betState?'disabled':false" @click="twoSecondKindsFun(item,twodwFirstKindList[0].key)" class="check"><input
                      type="checkbox" :disabled="betState?false:true" v-model="item.check" v-bind:disabled="(twodwFirstOneStatus && !item.check) || !betState?'disabled':false"  name="chk0" value="0"></td>
                  </template>
                </template>
              </tr>
              </tbody>
            </table>
            <table class="table_ball ssc">
              <tbody>
              <tr class="head">
                <th colspan="12">{{$t(twodwFirstKindList[1].key)}}</th>
              </tr>
              <tr class="head">
                <th>球</th>
                <th>选择</th>
                <th>球</th>
                <th>选择</th>
                <th>球</th>
                <th>选择</th>
                <th>球</th>
                <th>选择</th>
                <th>球</th>
                <th>选择</th>
              </tr>

              <tr>
                <template v-for="(item,key) in twodwFirstKindList[1].list">
                  <template v-if="key<=4">
                    <th class="name" :title="item.oddsKey"><span :class="'b'+item.oddsKey">{{item.oddsKey}}</span></th>
                    <td :class="item.choose?'oddsSelect':''" v-bind:disabled="(twodwFirstTwoStatus && !item.check) || !betState?'disabled':false" @click="twoSecondKindsFun(item,twodwFirstKindList[1].key)" class="check"><input
                      type="checkbox" :disabled="betState?false:true" v-model="item.check" v-bind:disabled="(twodwFirstTwoStatus && !item.check) || !betState?'disabled':false" name="chk0" value="0"></td>
                  </template>
                </template>
              </tr>
              <tr>
                <template v-for="(item,key) in twodwFirstKindList[1].list">
                  <template v-if="key>4">
                    <th class="name" :title="item.oddsKey"><span :class="'b'+item.oddsKey">{{item.oddsKey}}</span></th>
                    <td :class="item.choose?'oddsSelect':''" v-bind:disabled="(twodwFirstTwoStatus && !item.check) || !betState?'disabled':false" @click="twoSecondKindsFun(item,twodwFirstKindList[1].key)" class="check"><input
                      type="checkbox" :disabled="betState?false:true" v-model="item.check" v-bind:disabled="(twodwFirstTwoStatus && !item.check) || !betState?'disabled':false" name="chk0" value="0"></td>
                  </template>
                </template>
              </tr>
              </tbody>
            </table>
          </div>


        </template>
        <!--二字定位选项快选-->
        <div v-show="categorySelect=='twodw' && twodwSelectMode=='secondKinds'" class="status_panel bet_panel">
          <table class="table_ball">
            <tbody>
            <tr>
              <th class="name" style="padding:0 15px;width:40px;">{{$t(twodwFirstKindList[0].key)}}</th>
              <td>
                <div style="float:left;"><input type="checkbox" :disabled="betState?false:true" v-bind:disabled="!betState?'disabled':false" v-model="fsNum.odd" @change="twodwSpecialSelectFun(0)"
                                                value="odd" id="fsOddNum"><span>单数</span></div>
                <div style="float:left;"><input type="checkbox" :disabled="betState?false:true" v-bind:disabled="!betState?'disabled':false" v-model="fsNum.even" @change="twodwSpecialSelectFun(1)"
                                                value="even" id="fsEvenNum"><span>双数</span></div>
                <div><span>含</span><input type="text" v-bind:disabled="!betState?'disabled':false" @keyup="twodwSpecialInputFun('fsNumTake')" v-model="fsNumTake"
                                          oninput="this.value = this.value.replace(/[^0-9]/g, '');" id="fsInclude"
                                          style="width:30px;"></div>
              </td>
              <th class="name" style="padding:0 15px;width:40px;">双重</th>
              <td>
                <div style="float:left;"><input type="checkbox" :disabled="betState?false:true" v-bind:disabled="!betState?'disabled':false" v-model="doubleNum.take"
                                                @change="twodwSpecialSelectFun(2)" value="take"
                                                id="doubleNum"><span>取</span></div>
                <div><input type="checkbox" :disabled="betState?false:true" v-bind:disabled="!betState?'disabled':false" value="except" v-model="doubleNum.except" @change="twodwSpecialSelectFun(3)"
                            id="doubleNumX"><span>除</span></div>
              </td>

              <th class="name" style="padding:0 15px;width:40px;">数字</th>
              <td>
                <div>
                  <div style="float:left;"><span>含</span><input type="text" v-bind:disabled="!betState?'disabled':false" v-model="numberTake"
                                                                @keyup="twodwSpecialInputFun('numberTake')"
                                                                oninput="this.value = this.value.replace(/[^0-9]/g, '');"
                                                                id="contain" style="width:30px;"></div>
                  <div><span>排除</span><input type="text" v-bind:disabled="!betState?'disabled':false" @keyup="twodwSpecialInputFun('numberExcept')"
                                             v-model="numberExcept" id="exclude"
                                             oninput="this.value = this.value.replace(/[^0-9]/g, '');"
                                             style="width:30px;"></div>
                </div>
              </td>
            </tr>

            <tr>
              <th class="name" style="padding:0 15px;width:40px;">{{$t(twodwFirstKindList[1].key)}}</th>
              <td>
                <div style="float:left;"><input type="checkbox" :disabled="betState?false:true" v-bind:disabled="!betState?'disabled':false" v-model="secNum.odd" @change="twodwSpecialSelectFun(4)"
                                                value="odd" id="secOddNum"><span>单数</span></div>
                <div style="float:left;"><input type="checkbox" :disabled="betState?false:true" v-bind:disabled="!betState?'disabled':false" v-model="secNum.even" @change="twodwSpecialSelectFun(5)"
                                                value="even" id="secEvenNum"><span>双数</span></div>
                <div><span>含</span><input type="text" v-bind:disabled="!betState?'disabled':false" v-model="secNumTake" @keyup="twodwSpecialInputFun('secNumTake')"
                                          oninput="this.value = this.value.replace(/[^0-9]/g, '');" id="secInclude"
                                          style="width:30px;"></div>
              </td>
              <th class="name" style="padding:0 15px;width:40px;">二兄弟</th>
              <td>
                <div style="float:left;"><input type="checkbox":disabled="betState?false:true" v-bind:disabled="!betState?'disabled':false" v-model="brotherNum.take"
                                                @change="twodwSpecialSelectFun(6)" value="快选"
                                                id="brotherNum"><span>取</span></div>
                <div><input type="checkbox" :disabled="betState?false:true" v-model="brotherNum.except" value="快选" @change="twodwSpecialSelectFun(7)"
                            id="brotherNumX"><span>除</span></div>
              </td>
            </tr>

            </tbody>
          </table>


        </div>
        <div v-show="categorySelect=='twodw' && twodwSelectMode=='secondKinds'" class="split_panel bet_panel">
          <!-- 循环这个5列 共两大行-->


          <template v-for="(list,key) in twodwSecondKindsList">
            <table v-show="categorySelect=='twodw' && twodwSelectMode=='secondKinds'" class="table_ts ssc">

              <tbody>

              <tr class="head">
                <th>{{$t(twodwSelect.substring(0,1))}}</th>
                <th>{{$t(twodwSelect.substring(1,2))}}</th>
                <th>选择</th>
              </tr>
              <template v-for="(item,index) in list.list">


                <tr>
                  <th class="name" :title="item.oddsKey.substring(0,1)"><span :class="'b'+item.oddsKey.substring(0,1)">{{item.oddsKey.substring(0,1)}}</span>
                  </th>
                  <th class="name" :title="item.oddsKey.substring(1,2)"><span :class="'b'+item.oddsKey.substring(1,2)">{{item.oddsKey.substring(1,2)}}</span>
                  </th>
                  <td :class="item.choose?'oddsSelect':''" v-bind:disabled="!betState?'disabled':false" @click="twoSecondKindsFun(item)" class="check"><input
                    type="checkbox" :disabled="betState?false:true" v-model="item.check" value=""></td>
                </tr>
              </template>

              </tbody>
            </table>
          </template>
        </div>


        <!--三字定位-->
        <template v-show="categorySelect=='threedw'">
          <ul v-show="categorySelect=='threedw'" class="tab">
            <li class="tab_title02">
              <template v-for="(list,index) in sscOdds.threezdw">
                <a style="cursor:pointer" @click="threedwSelect=list.oddsKey"
                   :class="threedwSelect==list.oddsKey?'on':''">{{$t('3zdw'+list.oddsKey)}}</a>
              </template>
            </li>
          </ul>
          <table v-show="categorySelect=='threedw'" class="tab_title02">
            <tbody>
            <tr class="head">
              <th>种类</th>
              <th>{{$t('3dw')}}</th>
            </tr>
            <tr>
              <td>赔率</td>
              <td>
                <span class="odds">
                  <template v-for="(item,index) in sscOdds.threezdw">
                    <template v-if="item.oddsKey==threedwSelect">
                      {{betState&&item.status=='0'?item.odds:'封盘'}}
                    </template>
                  </template>
                </span>
              </td>
            </tr>
            </tbody>
          </table>

          <ul v-show="categorySelect=='threedw'" class="tab">
            <li class="tab_title02">
              <a @click="threedwSelectMode='firstKind'" :class="threedwSelectMode=='firstKind'?'on':''"
                 style="width:150px;cursor:pointer;">{{$t('3zdw'+threedwSelect)}}快选</a>
              <a @click="threedwSelectMode='secondKinds'" :class="threedwSelectMode=='secondKinds'?'on':''"
                 style="width:150px;cursor:pointer;">选项快选</a>
            </li>
          </ul>
          <div v-show="categorySelect=='threedw' && threedwSelectMode=='firstKind'" class="status_panel">
            <table class="table_ball ssc tab_title02">
              <tbody>
              <tr class="head">
                <th colspan="12">{{$t(threedwFirstKindList[0].key)}}</th>
              </tr>
              <tr class="head">
                <th>球</th>
                <th>选择</th>
                <th>球</th>
                <th>选择</th>
                <th>球</th>
                <th>选择</th>
                <th>球</th>
                <th>选择</th>
                <th>球</th>
                <th>选择</th>
              </tr>
              <tr>
                <template v-for="(item,index) in threedwFirstKindList[0].list">
                  <template v-if="index<=4">
                    <th class="name" :title="item.oddsKey"><span :class="'b'+item.oddsKey">{{item.oddsKey}}</span></th>
                    <td :class="item.choose?'oddsSelect':''" v-bind:disabled="(threedwFirstOneStatus && !item.check) || !betState?'disabled':false" @click="twoSecondKindsFun(item,threedwFirstKindList[0].key)" class="check"><input
                      type="checkbox" :disabled="betState?false:true" v-model="item.check" v-bind:disabled="(threedwFirstOneStatus && !item.check) || !betState?'disabled':false" name="chk0" value="0"></td>
                  </template>
                </template>
              </tr>
              <tr>
                <template v-for="(item,index) in threedwFirstKindList[0].list">
                  <template v-if="index>4">
                    <th class="name" :title="item.oddsKey"><span :class="'b'+item.oddsKey">{{item.oddsKey}}</span></th>
                    <td :class="item.choose?'oddsSelect':''" v-bind:disabled="(threedwFirstOneStatus && !item.check) || !betState?'disabled':false" @click="twoSecondKindsFun(item,threedwFirstKindList[0].key)" class="check"><input
                      type="checkbox" :disabled="betState?false:true" v-model="item.check" v-bind:disabled="(threedwFirstOneStatus && !item.check) || !betState?'disabled':false" name="chk0" value="0"></td>
                  </template>
                </template>
              </tr>
              </tbody>
            </table>
            <table class="table_ball ssc">
              <tbody>
              <tr class="head">
                <th colspan="12">{{$t(threedwFirstKindList[1].key)}}</th>
              </tr>
              <tr class="head">
                <th>球</th>
                <th>选择</th>
                <th>球</th>
                <th>选择</th>
                <th>球</th>
                <th>选择</th>
                <th>球</th>
                <th>选择</th>
                <th>球</th>
                <th>选择</th>
              </tr>

              <tr>
                <template v-for="(item,index) in threedwFirstKindList[1].list">
                  <template v-if="index<=4">
                    <th class="name" :title="item.oddsKey"><span :class="'b'+item.oddsKey">{{item.oddsKey}}</span></th>
                    <td :class="item.choose?'oddsSelect':''" v-bind:disabled="(threedwFirstTwoStatus && !item.check) || !betState?'disabled':false" @click="twoSecondKindsFun(item,threedwFirstKindList[1].key)" class="check"><input
                      type="checkbox" :disabled="betState?false:true" v-model="item.check" v-bind:disabled="(threedwFirstTwoStatus && !item.check) || !betState?'disabled':false" name="chk0" value="0"></td>
                  </template>
                </template>
              </tr>
              <tr>
                <template v-for="(item,index) in threedwFirstKindList[1].list">
                  <template v-if="index>4">
                    <th class="name" :title="item.oddsKey"><span :class="'b'+item.oddsKey">{{item.oddsKey}}</span></th>
                    <td :class="item.choose?'oddsSelect':''" v-bind:disabled="(threedwFirstTwoStatus && !item.check) || !betState?'disabled':false" @click="twoSecondKindsFun(item,threedwFirstKindList[1].key)" class="check"><input
                      type="checkbox" :disabled="betState?false:true" v-model="item.check" v-bind:disabled="(threedwFirstTwoStatus && !item.check) || !betState?'disabled':false" name="chk0" value="0"></td>
                  </template>
                </template>
              </tr>
              </tbody>
            </table>
            <table class="table_ball ssc">
              <tbody>
              <tr class="head">
                <th colspan="12">{{$t(threedwFirstKindList[2].key)}}</th>
              </tr>
              <tr class="head">
                <th>球</th>
                <th>选择</th>
                <th>球</th>
                <th>选择</th>
                <th>球</th>
                <th>选择</th>
                <th>球</th>
                <th>选择</th>
                <th>球</th>
                <th>选择</th>
              </tr>

              <tr>
                <template v-for="(item,index) in threedwFirstKindList[2].list">
                  <template v-if="index<=4">
                    <th class="name" :title="item.oddsKey"><span :class="'b'+item.oddsKey">{{item.oddsKey}}</span></th>
                    <td :class="item.choose?'oddsSelect':''" v-bind:disabled="(threedwFirstThreeStatus && !item.check) || !betState?'disabled':false" @click="twoSecondKindsFun(item,threedwFirstKindList[2].key)" class="check"><input
                      type="checkbox" :disabled="betState?false:true" v-model="item.check" v-bind:disabled="(threedwFirstThreeStatus && !item.check) || !betState?'disabled':false" name="chk0" value="0"></td>
                  </template>
                </template>
              </tr>
              <tr>
                <template v-for="(item,index) in threedwFirstKindList[2].list">
                  <template v-if="index>4">
                    <th class="name" :title="item.oddsKey"><span :class="'b'+item.oddsKey">{{item.oddsKey}}</span></th>
                    <td :class="item.choose?'oddsSelect':''" v-bind:disabled="(threedwFirstThreeStatus && !item.check) || !betState?'disabled':false" @click="twoSecondKindsFun(item,threedwFirstKindList[2].key)" class="check"><input
                      type="checkbox" :disabled="betState?false:true" v-model="item.check" v-bind:disabled="(threedwFirstThreeStatus && !item.check) || !betState?'disabled':false" name="chk0" value="0"></td>
                  </template>
                </template>
              </tr>
              </tbody>
            </table>
          </div>


        </template>
        <!--三字定位选项快选-->
        <div v-show="categorySelect=='threedw' && threedwSelectMode=='secondKinds'" class="status_panel bet_panel">
          <table class="table_ball">
            <tbody>
            <tr>
              <th class="name" style="padding:0 15px;width:40px;">{{$t(threedwFirstKindList[0].key)}}</th>
              <td>
                <div style="float:left;"><input type="checkbox" :disabled="betState?false:true" v-bind:disabled="!betState?'disabled':false" v-model="threeFsNum.odd"
                                                @change="threedwSpecialSelectFun(0)" value="odd"
                                                id="fsOddNum"><span>单数</span></div>
                <div style="float:left;"><input type="checkbox" :disabled="betState?false:true" v-bind:disabled="!betState?'disabled':false" v-model="threeFsNum.even"
                                                @change="threedwSpecialSelectFun(1)" value="even" id="fsEvenNum"><span>双数</span>
                </div>
                <div><span>含</span><input type="text" v-bind:disabled="!betState?'disabled':false" @keyup="threedwListChange" v-model="threeFsNumTake"
                                          oninput="this.value = this.value.replace(/[^0-9]/g, '');" id="fsInclude"
                                          style="width:30px;"></div>
              </td>
              <th class="name" style="padding:0 15px;width:40px;">双重</th>
              <td>
                <div style="float:left;"><input type="checkbox" :disabled="betState?false:true" v-bind:disabled="!betState?'disabled':false" v-model="threeDoubleNum.take"
                                                @change="threedwSpecialSelectFun(2)" value="take"
                                                id="doubleNum"><span>取</span></div>
                <div><input type="checkbox" :disabled="betState?false:true" v-bind:disabled="!betState?'disabled':false" value="except" v-model="threeDoubleNum.except"
                            @change="threedwSpecialSelectFun(3)" id="doubleNumX"><span>除</span></div>
              </td>

              <th class="name" style="padding:0 15px;width:40px;">二兄弟</th>
              <td>
                <div style="float:left;"><input type="checkbox" :disabled="betState?false:true" v-bind:disabled="!betState?'disabled':false" v-model="threeTwoBrotherNum.take"
                                                @change="threedwSpecialSelectFun(4)" value="快选"
                                                id="brotherNum"><span>取</span></div>
                <div><input type="checkbox" :disabled="betState?false:true" v-bind:disabled="!betState?'disabled':false" v-model="threeTwoBrotherNum.except" value="快选"
                            @change="threedwSpecialSelectFun(5)" id="brotherNumX"><span>除</span></div>
              </td>

            </tr>

            <tr>
              <th class="name" style="padding:0 15px;width:40px;">{{$t(threedwFirstKindList[1].key)}}</th>
              <td>
                <div style="float:left;"><input v-bind:disabled="!betState?'disabled':false" :disabled="betState?false:true" type="checkbox" v-model="threeSecNum.odd"
                                                @change="threedwSpecialSelectFun(6)" value="odd"
                                                id="secOddNum"><span>单数</span></div>
                <div style="float:left;"><input v-bind:disabled="!betState?'disabled':false" type="checkbox" :disabled="betState?false:true" v-model="threeSecNum.even"
                                                @change="threedwSpecialSelectFun(7)" value="even" id="secEvenNum"><span>双数</span>
                </div>
                <div><span>含</span><input v-bind:disabled="!betState?'disabled':false" type="text" v-model="threeSecNumTake" @keyup="threedwListChange"
                                          oninput="this.value = this.value.replace(/[^0-9]/g, '');" id="secInclude"
                                          style="width:30px;"></div>
              </td>

              <th class="name" style="padding:0 15px;width:40px;">三重</th>
              <td>
                <div style="float:left;"><input v-bind:disabled="!betState?'disabled':false" :disabled="betState?false:true" type="checkbox" v-model="threeRepeatNum.take"
                                                @change="threedwSpecialSelectFun(8)" value="take"
                                                id="doubleNum"><span>取</span></div>
                <div><input type="checkbox" :disabled="betState?false:true" v-bind:disabled="!betState?'disabled':false" value="except" v-model="threeRepeatNum.except"
                            @change="threedwSpecialSelectFun(9)" id="doubleNumX"><span>除</span></div>
              </td>
              <th class="name" style="padding:0 15px;width:40px;">三兄弟</th>
              <td>
                <div style="float:left;"><input v-bind:disabled="!betState?'disabled':false" :disabled="betState?false:true" type="checkbox" v-model="threeThreeBrotherNum.take"
                                                @change="threedwSpecialSelectFun(10)" value="快选"
                                                id="brotherNum"><span>取</span></div>
                <div><input type="checkbox" :disabled="betState?false:true" v-bind:disabled="!betState?'disabled':false" v-model="threeThreeBrotherNum.except" value="快选"
                            @change="threedwSpecialSelectFun(11)" id="brotherNumX"><span>除</span></div>
              </td>

            </tr>
            <tr>
              <th class="name" style="padding:0 15px;width:40px;">{{$t(threedwFirstKindList[2].key)}}</th>
              <td>
                <div style="float:left;"><input v-bind:disabled="!betState?'disabled':false" :disabled="betState?false:true" type="checkbox" v-model="threeTrdNum.odd"
                                                @change="threedwSpecialSelectFun(12)" value="odd" id="secOddNum"><span>单数</span>
                </div>
                <div style="float:left;"><input v-bind:disabled="!betState?'disabled':false" :disabled="betState?false:true" type="checkbox" v-model="threeTrdNum.even"
                                                @change="threedwSpecialSelectFun(13)" value="even"
                                                id="secEvenNum"><span>双数</span></div>
                <div><span>含</span><input v-bind:disabled="!betState?'disabled':false" type="text" v-model="threeTrdNumTake" @keyup="threedwListChange"
                                          oninput="this.value = this.value.replace(/[^0-9]/g, '');" id="secInclude"
                                          style="width:30px;"></div>
              </td>
              <th class="name" style="padding:0 15px;width:40px;">数字</th>
              <td>
                <div>
                  <div style="float:left;"><span>含</span><input type="text" v-bind:disabled="!betState?'disabled':false" v-model="threeNumberTake"
                                                                @keyup="threedwListChange"
                                                                oninput="this.value = this.value.replace(/[^0-9]/g, '');"
                                                                id="contain" style="width:30px;"></div>
                  <div><span>排除</span><input type="text" v-bind:disabled="!betState?'disabled':false" @keyup="threedwListChange" v-model="threeNumberExcept"
                                             id="exclude" oninput="this.value = this.value.replace(/[^0-9]/g, '');"
                                             style="width:30px;"></div>
                </div>
              </td>
            </tr>

            </tbody>
          </table>


        </div>
        <div v-show="categorySelect=='threedw' && threedwSelectMode=='secondKinds'" class="split_panel bet_panel">
          <!-- 循环这个5列 共两大行-->
          <template v-for="(list,index) in threedwScreenList">

            <table v-if="list.list.length>0" v-show="categorySelect=='threedw' && threedwSelectMode=='secondKinds'"
                   class="table_ts ssc">
              <tbody>
              <tr class="head">
                <th>{{$t(threedwFirstKindList[0].key)}}</th>
                <th>{{$t(threedwFirstKindList[1].key)}}</th>
                <th>{{$t(threedwFirstKindList[2].key)}}</th>
                <th>选择</th>
              </tr>
              <template v-for="(item,i) in list.list">

                <tr v-if="null != item.oddsKey">
                  <th class="name" :title="item.oddsKey.substring(0,1)"><span :class="'b'+item.oddsKey.substring(0,1)">{{item.oddsKey.substring(0,1)}}</span>
                  </th>
                  <th class="name" :title="item.oddsKey.substring(1,2)"><span :class="'b'+item.oddsKey.substring(1,2)">{{item.oddsKey.substring(1,2)}}</span>
                  </th>
                  <th class="name" :title="item.oddsKey.substring(2,3)"><span :class="'b'+item.oddsKey.substring(2,3)">{{item.oddsKey.substring(2,3)}}</span>
                  </th>
                  <td :class="item.choose?'oddsSelect':''" v-bind:disabled="!betState?'disabled':false" @click="twoSecondKindsFun(item)" class="check"><input
                    type="checkbox" :disabled="betState?false:true" v-model="item.check" value=""></td>
                </tr>
              </template>
              </tbody>
            </table>
          </template>
        </div>
        <!--  二字和数-->
        <template v-show="categorySelect=='twohs'">
          <ul v-show="categorySelect=='twohs'" class="tab">
            <li class="tab_title02">
              <a style="cursor:pointer" @click="twohsSelect='wq'" :class="twohsSelect=='wq'?'on':''">{{$t('wq')}}</a>
              <a style="cursor:pointer" @click="twohsSelect='wb'" :class="twohsSelect=='wb'?'on':''">{{$t('wb')}}</a>
              <a style="cursor:pointer" @click="twohsSelect='ws'" :class="twohsSelect=='ws'?'on':''">{{$t('ws')}}</a>
              <a style="cursor:pointer" @click="twohsSelect='wg'" :class="twohsSelect=='wg'?'on':''">{{$t('wg')}}</a>
              <a style="cursor:pointer" @click="twohsSelect='qb'" :class="twohsSelect=='qb'?'on':''">{{$t('qb')}}</a>
              <a style="cursor:pointer" @click="twohsSelect='qs'" :class="twohsSelect=='qs'?'on':''">{{$t('qs')}}</a>
              <a style="cursor:pointer" @click="twohsSelect='qg'" :class="twohsSelect=='qg'?'on':''">{{$t('qg')}}</a>
              <a style="cursor:pointer" @click="twohsSelect='bs'" :class="twohsSelect=='bs'?'on':''">{{$t('bs')}}</a>
              <a style="cursor:pointer" @click="twohsSelect='bg'" :class="twohsSelect=='bg'?'on':''">{{$t('bg')}}</a>
              <a style="cursor:pointer" @click="twohsSelect='sg'" :class="twohsSelect=='sg'?'on':''">{{$t('sg')}}</a>
            </li>
          </ul>
          <table v-show="categorySelect=='twohs'" class="table_ball">
            <tbody>
            <!--循环-->
            <tr class="head">
              <th colspan="20">{{$t(twohsSelect+'hs')}}</th>
            </tr>
            <tr>
              <template v-for="(item,i) in sscOdds.twozhs['2hs'+twohsSelect]">
                <template v-if="i<5">
                  <th @click="selectOdds(item)" :class="item.choose?'oddsSelect':''" class="name"
                      :title="item.oddsKey">
                    <input type="hidden" value="">{{item.oddsKey}}
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
            <tr>
              <template v-for="(item,i) in sscOdds.twozhs['2hs'+twohsSelect]">
                <template v-if="i>4 && i <10">
                  <th @click="selectOdds(item)" :class="item.choose?'oddsSelect':''" class="name"
                      :title="item.oddsKey">
                    <input type="hidden" value="">{{item.oddsKey}}
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
            <tr>
              <template v-for="(item,i) in sscOdds.twozhs['2hs'+twohsSelect]">
                <template v-if="i>9">
                  <th @click="selectOdds(item)" :class="item.choose?'oddsSelect':''" class="name"
                      :title="item.oddsKey">
                    <input type="hidden" value="">{{item.oddsKey}}
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
            <tr class="head">
              <th colspan="20">{{$t(twohsSelect+'hws')}}</th>
            </tr>
            <tr>
              <template v-for="(item,i) in sscOdds.twozhs['2hws'+twohsSelect]">
                <template v-if="i<5">
                  <th @click="selectOdds(item)" :class="item.choose?'oddsSelect':''" class="name"
                      :title="item.oddsKey">
                    <input type="hidden" value="">{{item.oddsKey}}
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
            <tr>
              <template v-for="(item,i) in sscOdds.twozhs['2hws'+twohsSelect]">
                <template v-if="i>4">
                  <th @click="selectOdds(item)" :class="item.choose?'oddsSelect':''" class="name"
                      :title="item.oddsKey">
                    <input type="hidden" value="">{{item.oddsKey}}
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
            <!--结束-->
            </tbody>
          </table>


        </template>

        <!--  三字和数-->
        <template v-show="categorySelect=='threehs'">
          <ul v-show="categorySelect=='threehs'" class="tab">
            <li class="tab_title02">
              <a style="cursor:pointer" @click="threehsSelect='top3'" :class="threehsSelect=='top3'?'on':''">{{$t('top3hs')}}</a>
              <a style="cursor:pointer" @click="threehsSelect='mid3'" :class="threehsSelect=='mid3'?'on':''">{{$t('mid3hs')}}</a>
              <a style="cursor:pointer" @click="threehsSelect='end3'" :class="threehsSelect=='end3'?'on':''">{{$t('end3hs')}}</a>
            </li>
          </ul>
          <table v-show="categorySelect=='threehs'" class="table_ball">
            <tbody>
            <!--循环-->
            <tr class="head">
              <th colspan="20">{{$t(threehsSelect+'hs')}}</th>
            </tr>
            <tr>
              <template v-for="(item,i) in sscOdds.threezhs['3hs'+threehsSelect]">
                <template v-if="i<5">
                  <th @click="selectOdds(item)" :class="item.choose?'oddsSelect':''" class="name"
                      :title="item.oddsKey">
                    <input type="hidden" value="">{{item.oddsKey}}
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
            <tr>
              <template v-for="(item,i) in sscOdds.threezhs['3hs'+threehsSelect]">
                <template v-if="i>4 && i <10">
                  <th @click="selectOdds(item)" :class="item.choose?'oddsSelect':''" class="name"
                      :title="item.oddsKey">
                    <input type="hidden" value="">{{item.oddsKey}}
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
            <tr>
              <template v-for="(item,i) in sscOdds.threezhs['3hs'+threehsSelect]">
                <template v-if="i>9 && i<15">
                  <th @click="selectOdds(item)" :class="item.choose?'oddsSelect':''" class="name"
                      :title="item.oddsKey">
                    <input type="hidden" value="">{{item.oddsKey}}
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
            <tr>
              <template v-for="(item,i) in sscOdds.threezhs['3hs'+threehsSelect]">
                <template v-if="i>14">
                  <th @click="selectOdds(item)" :class="item.choose?'oddsSelect':''" class="name"
                      :title="item.oddsKey">
                    <input type="hidden" value="">{{item.oddsKey}}
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
            <tr class="head">
              <th colspan="20">{{$t(threehsSelect+'hs')}}{{$t('weishu')}}</th>
            </tr>
            <tr>
              <template v-for="(item,i) in sscOdds.threezhs['3hws'+threehsSelect]">
                <template v-if="i<5">
                  <th @click="selectOdds(item)" :class="item.choose?'oddsSelect':''" class="name"
                      :title="item.oddsKey">
                    <input type="hidden" value="">{{item.oddsKey}}
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
            <tr>
              <template v-for="(item,i) in sscOdds.threezhs['3hws'+threehsSelect]">
                <template v-if="i>4">
                  <th @click="selectOdds(item)" :class="item.choose?'oddsSelect':''" class="name"
                      :title="item.oddsKey">
                    <input type="hidden" value="">{{item.oddsKey}}
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
            <!--结束-->
            </tbody>
          </table>


        </template>


        <!--  斗牛/梭哈-->
        <template v-show="categorySelect=='ndsm'">
          <table v-show="categorySelect=='ndsm'" class="table_ball">
            <tbody>
            <!--循环-->
            <tr class="head">
              <th colspan="20">{{$t('dn')}}</th>
            </tr>
            <tr>
              <template v-for="(item,i) in sscOdds.ndsm['nn']">
                <template v-if="i<5">
                  <th @click="selectOdds(item)" :class="item.choose?'oddsSelect':''" class="name"
                      :title="$t(item.oddsKey)">
                    <input type="hidden" value="">{{$t(item.oddsKey)}}
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
            <tr>
              <template v-for="(item,i) in sscOdds.ndsm['nn']">
                <template v-if="i>4 && i <10">
                  <th @click="selectOdds(item)" :class="item.choose?'oddsSelect':''" class="name"
                      :title="$t(item.oddsKey)">
                    <input type="hidden" value="">{{$t(item.oddsKey)}}
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
            <tr>
              <template v-for="(item,i) in sscOdds.ndsm['nn']">
                <template v-if="i>9 && i<15">
                  <th @click="selectOdds(item)" :class="item.choose?'oddsSelect':''" class="name"
                      :title="$t(item.oddsKey)">
                    <input type="hidden" value="">{{$t(item.oddsKey)}}
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

            <tr class="head">
              <th colspan="20">{{$t('suoha')}}</th>
            </tr>
            <tr>
              <template v-for="(item,i) in sscOdds.ndsm['sh']">
                <template v-if="i<5">
                  <th @click="selectOdds(item)" :class="item.choose?'oddsSelect':''" class="name"
                      :title="$t(item.oddsKey)">
                    <input type="hidden" value="">{{$t(item.oddsKey)}}
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
            <tr>
              <template v-for="(item,i) in sscOdds.ndsm['sh']">
                <template v-if="i>4">
                  <th @click="selectOdds(item)" :class="item.choose?'oddsSelect':''" class="name"
                      :title="$t(item.oddsKey)">
                    <input type="hidden" value="">{{$t(item.oddsKey)}}
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
            <!--结束-->
            </tbody>
          </table>


        </template>


        <!--组选三 组选六也一样-->
        <!--前三组选三-->
        <ul class="tab" v-show="categorySelect=='zxthree'">
          <li class="tab_title02">
            <a style="cursor:pointer" @click="zxthreeSelect='top3'" :class="zxthreeSelect=='top3'?'on':''">{{$t('zx3top3')}}</a>
            <a style="cursor:pointer" @click="zxthreeSelect='mid3'" :class="zxthreeSelect=='mid3'?'on':''">{{$t('zx3mid3')}}</a>
            <a style="cursor:pointer" @click="zxthreeSelect='end3'" :class="zxthreeSelect=='end3'?'on':''">{{$t('zx3end3')}}</a>
          </li>
        </ul>
        <table class="table_ball" v-show="categorySelect=='zxthree'">
          <tbody>
          <tr class="head">
            <th>种类</th>
            <th>{{$t('zx3'+zxthreeSelect)}}</th>
          </tr>
          <tr>
            <td>赔率</td>
            <td>
              <span class="odds" style="display: inline;">{{zxthreeOddsResult}}</span>
            </td>
          </tr>
          </tbody>
        </table>
        <div class="status_panel" v-show="categorySelect=='zxthree'">
          <table class="table_ball">
            <tbody>
            <template v-for="(list,index) in zxthreeList">
              <tr >
                <template v-for="(item,i ) in list">
                  <th class="name" :title="item.oddsKey">{{item.oddsKey}}</th>
                  <td class="check" v-bind:disabled="!betState?'disabled':false" :class="item.choose?'oddsSelect':''" @click="twoSecondKindsFun(item)"><input type="checkbox" :disabled="betState?false:true" v-bind:disabled="!betState?'disabled':false" v-model="item.check" value=""></td>
                </template>
              </tr>
            </template>
            </tbody>
          </table>
        </div>


        <!--组选六-->
        <ul class="tab" v-show="categorySelect=='zxsix'">
          <li class="tab_title02">
            <a style="cursor:pointer" @click="zxsixSelect='top3'" :class="zxsixSelect=='top3'?'on':''">{{$t('zx6top3')}}</a>
            <a style="cursor:pointer" @click="zxsixSelect='mid3'" :class="zxsixSelect=='mid3'?'on':''">{{$t('zx6mid3')}}</a>
            <a style="cursor:pointer" @click="zxsixSelect='end3'" :class="zxsixSelect=='end3'?'on':''">{{$t('zx6end3')}}</a>
          </li>
        </ul>
        <table class="table_ball" v-show="categorySelect=='zxsix'">
          <tbody>
          <tr class="head">
            <th>种类</th>
            <th>{{$t('zx6'+zxsixSelect)}}</th>
          </tr>
          <tr>
            <td>赔率</td>
            <td>
              <span class="odds" style="display: inline;">{{zxsixOddsResult}}</span>
            </td>
          </tr>
          </tbody>
        </table>
        <div class="status_panel" v-show="categorySelect=='zxsix'">
          <table class="table_ball">
            <tbody>
            <template v-for="(list,index) in zxthreeList">
              <tr >
                <template v-for="(item,i ) in list">
                  <th class="name" :title="item.oddsKey">{{item.oddsKey}}</th>
                  <td class="check" v-bind:disabled="(zxsixStatus && !item.check) || !betState?'disabled':false" :class="item.choose?'oddsSelect':''" @click="twoSecondKindsFun(item)"><input type="checkbox" :disabled="betState?false:true" v-bind:disabled="(zxsixStatus && !item.check) || !betState?'disabled':false" v-model="item.check" value=""></td>
                </template>
              </tr>
            </template>
            </tbody>
          </table>
        </div>

        <!--复式组合-->
        <!--前三复式-->
        <ul class="tab" v-show="categorySelect=='fszh'">
          <li class="tab_title02">
            <a style="cursor:pointer" @click="fszhSelect='top3'" :class="fszhSelect=='top3'?'on':''">{{$t('fstop3')}}</a>
            <a style="cursor:pointer" @click="fszhSelect='mid3'" :class="fszhSelect=='mid3'?'on':''">{{$t('fsmid3')}}</a>
            <a style="cursor:pointer" @click="fszhSelect='end3'" :class="fszhSelect=='end3'?'on':''">{{$t('fsend3')}}</a>

          </li>
        </ul>
        <table class="table_ball" v-show="categorySelect=='fszh'">
          <tbody>
          <tr class="head">
            <th>种类</th>
            <th>复式组合</th>
          </tr>
          <tr>
            <td>赔率</td>
            <td><span id="fs_odds" class="odds">
              <template v-for="(item,index) in sscOdds.fszh">
                <template v-if="item.oddsKey==fszhSelect">{{item.odds}}</template>
              </template>
            </span></td>
          </tr>
          </tbody>
        </table>

        <div class="status_panel" v-show="categorySelect=='fszh'">
          <table class="table_ball">
            <tbody>
            <tr class="head">
              <th>
                <template v-if="fszhSelect=='top3'">
                  {{$t('w')}}
                </template>
                <template v-if="fszhSelect=='mid3'">
                  {{$t('q')}}
                </template>
                <template v-if="fszhSelect=='end3'">
                  {{$t('b')}}
                </template>
              </th>
              <th>勾选</th>
              <th><template v-if="fszhSelect=='top3'">
                {{$t('q')}}
              </template>
                <template v-if="fszhSelect=='mid3'">
                  {{$t('b')}}
                </template>
                <template v-if="fszhSelect=='end3'">
                  {{$t('s')}}
                </template></th>
              <th>勾选</th>
              <th><template v-if="fszhSelect=='top3'">
                {{$t('b')}}
              </template>
                <template v-if="fszhSelect=='mid3'">
                  {{$t('s')}}
                </template>
                <template v-if="fszhSelect=='end3'">
                  {{$t('g')}}
                </template></th>
              <th>勾选</th>
            </tr>
            <template v-for="(list,index) in fszhList">
              <tr>
                <template v-for="(item,i) in list">
                <th class="name" style="width: 97px;" :title="item.oddsKey">{{item.oddsKey}}</th>
                <td :class="item.choose?'oddsSelect':''" v-bind:disabled="(fszhFirstStatus[item.key] && !item.check) || !betState?'disabled':false" @click="twoSecondKindsFun(item)" class="check"><input name="wan" type="checkbox" :disabled="betState?false:true" v-bind:disabled="(fszhFirstStatus[item.key] && !item.check) || !betState?'disabled':false" v-model="item.check" value="0"></td>
                </template>
              </tr>
            </template>


            </tbody>
          </table>
        </div>

        <!--跨度-->
        <ul class="tab" v-show="categorySelect=='kd'">
          <li class="tab_title02">
            <a style="cursor:pointer" @click="kdSelect='top3'" :class="kdSelect=='top3'?'on':''">{{$t('kdtop3')}}</a>
            <a style="cursor:pointer" @click="kdSelect='mid3'" :class="kdSelect=='mid3'?'on':''">{{$t('kdmid3')}}</a>
            <a style="cursor:pointer" @click="kdSelect='end3'" :class="kdSelect=='end3'?'on':''">{{$t('kdend3')}}</a>

          </li>
        </ul>

        <table class="table_ball" v-show="categorySelect=='kd'">
          <tbody>
          <template v-for="(list,index) in kdOddsResult">
            <tr>
              <template v-for="(item,i) in list">

                <th class="name" :title="$t('kd'+kdSelect)+item.oddsKey"><input type="hidden" value="">{{item.oddsKey}}</th>

                <td :class="item.choose?'oddsSelect':''" @click="selectOdds(item)" class="odds">{{oddsReturn(item)}}</td>
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
            </tr>
          </template>
          </tbody>
        </table>
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


  <!--  总和-龙虎和-->
</template>

<script>
  import gameInfo from '@/components/layout/gameInfo'
  import {mapActions, mapGetters} from 'vuex'
  import statistics from '@/components/ssc/statistics'
  import Utils from '@/components/comm/Utils.js'
  import {Indicator} from 'mint-ui'

  export default {
    name: "ssc",
    components: {
      gameInfo,
      statistics
    },
    data() {
      return {
        pagePresetAmt:'',
        pagePresetStatus:false,
        twozhSelect: 'top3',
        threezhSelect: 'top3',
        lotteryPageFlag:true,
        threezhRangeStart: '',
        threezhRangeStop: '',
        twodwSelect: 'wq',
        twodwSelectMode: 'firstKind',
        twodwSecondKindsList: {
          '0': {'list': []}, '1': {'list': []}, '2': {'list': []}, '3': {'list': []}, '4': {'list': []},
          '5': {'list': []}, '6': {'list': []}, '7': {'list': []}, '8': {'list': []}, '9': {'list': []},
        },
        twodwFirstKindList: {'0': {'key': 'w', 'list': []}, '1': {'key': 'q', 'list': []}},
        twodwFirstOneStatus:false,
        twodwFirstTwoStatus:false,
        threedwFirstOneStatus:false,
        threedwFirstTwoStatus:false,
        threedwFirstThreeStatus:false,
        fszhFirstStatus:{'w':false,'q':false,'b':false,'s':false,'g':false},
        numberTake: '',
        numberExcept: '',
        fsNumTake: '',
        secNumTake: '',
        fsNum: {
          odd: false,
          even: false
        },
        doubleNum: {
          take: false,
          except: false
        },
        secNum: {
          odd: false,
          even: false
        },
        brotherNum: {
          take: false,
          except: false
        },
        specialCheckBoxList: {},
        threedwSelect: 'top3',
        threedwSelectMode: 'firstKind',
        threedwSecondKindsList: {
          '0': {'list': []}, '1': {'list': []}, '2': {'list': []}, '3': {'list': []}, '4': {'list': []},
          '5': {'list': []}, '6': {'list': []}, '7': {'list': []}, '8': {'list': []}, '9': {'list': []},
        },
        threedwFirstKindList: {
          '0': {'key': 'w', 'list': []},
          '1': {'key': 'q', 'list': []},
          '2': {'key': 'b', 'list': []}
        },
        threeNumberTake: '',
        threeNumberExcept: '',
        threeFsNumTake: '',
        threeSecNumTake: '',
        threeTrdNumTake: '',
        threeFsNum: {
          odd: false,
          even: false
        },
        threeDoubleNum: {
          take: false,
          except: false
        },
        threeRepeatNum: {
          take: false,
          except: false
        },
        threeSecNum: {
          odd: false,
          even: false
        },
        threeTwoBrotherNum: {
          take: false,
          except: false
        },
        threeTrdNum: {
          odd: false,
          even: false
        },
        threeThreeBrotherNum: {
          take: false,
          except: false
        },
        threedwScreenList: {},
        twohsSelect: 'wq',
        threehsSelect: 'top3',
        zxthreeSelect:'top3',
        zxthreeList:{},
        zxsixSelect:'top3',
        fszhSelect:'top3',
        fszhList:{},
        kdSelect:'top3',
        kdList:{},
        pageQuickBetStatus:false,
        allSearchList:[false,false,false,false,false],
        zxsixStatus:false,
        inputAmtTimeOut:null
      }
    },
    computed: {
      ...mapGetters(['sscOdds','quickBetStatus','betAmtPreSet','betAmtStatus','promptInformation', 'quickSumStatus', 'quickSumList', 'betState', 'betGameNo', 'noteLists', 'gameInfo', 'selectList', 'gameId', 'categorySelect','userOddsCljps','userOddsCloses','userOddsJumps','userOddsNows','userOddss']),
      kdOddsResult(){
        let self = this;

        let kdList = {};

        for(let i = 0;i<5;i++){
          let arr = [];
          let obj = {oddsKey:i,choose:false,check:false};
          arr.push(JSON.parse(JSON.stringify(obj)));
          obj.oddsKey=i+5;
          arr.push(JSON.parse(JSON.stringify(obj)));
          self.$set(kdList,i,arr);
        }
        for(let key in kdList){
          for(let i = 0;i<kdList[key].length;i++){
            if(self.sscOdds.kd['kd'+self.kdSelect]){
              self.sscOdds.kd['kd'+self.kdSelect].forEach(val=>{
                if(val.oddsKey==kdList[key][i].oddsKey){
                  self.$set(val,'odds',self.oddsReturn(val));
                  kdList[key][i] = val;
                }
              });
            }

          }
        }
        return kdList;
      },
      zxsixOddsResult(){
        let self = this;
        if(!this.betState){
          return '封盘';
        }

        if(!this.sscOdds){
          return;
        }

        let zxsixNo = 0;
        for(let key in this.zxthreeList){
          for(let i =0;i<this.zxthreeList[key].length;i++){
            if(this.zxthreeList[key][i].check){
              zxsixNo++;

            }
          }
        }
        if(zxsixNo<4 || zxsixNo>8){
          return '- -';
        }

        for(let odd in this.sscOdds.zxl){
          for(let item of this.sscOdds.zxl[odd]){
            if(zxsixNo == item.oddsKey){
              //return item.status=='1'?'封盘':item.odds;
              return item.status=='1'?'封盘':self.oddsReturn(item);
            }
          }
        }
      },
      zxthreeOddsResult(){
        if(!this.betState){
          return '封盘';
        }

        if(!this.sscOdds){
          return;
        }
        let self = this;
        let zxthreeNo = 0;
        for(let key in this.zxthreeList){
          for(let i =0;i<this.zxthreeList[key].length;i++){
            if(this.zxthreeList[key][i].check){
              zxthreeNo++;

            }
          }
        }

        if(zxthreeNo<5 ){
          return '- -';
        }
        for(let odd in this.sscOdds.zxs){
          for(let i = 0;i<this.sscOdds.zxs[odd].length;i++){
            if(zxthreeNo == this.sscOdds.zxs[odd][i].oddsKey){
              //return this.sscOdds.zxs[odd][i].status=='1'?'封盘':this.sscOdds.zxs[odd][i].odds;
              return this.sscOdds.zxs[odd][i].status=='1'?'封盘':self.oddsReturn(self.sscOdds.zxs[odd][i]);
            }
          }
        }
      },
      twozhResultOdds() {
        let self = this;
        let twozhList = {
          '0': [],
          '1': [],
          '2': [],
          '3': [],
          '4': [],
        };
        if (self.sscOdds.twozzh['2z' + self.twozhSelect]) {
          for (let i = 0; i <= self.sscOdds.twozzh['2z' + self.twozhSelect].length; i++) {
            if(self.sscOdds.twozzh['2z' + self.twozhSelect][i]){
              self.$set(self.sscOdds.twozzh['2z' + self.twozhSelect][i],'odds',self.oddsReturn(self.sscOdds.twozzh['2z' + self.twozhSelect][i]));
            }
            if (i < 11) {
              twozhList['0'].push(self.sscOdds.twozzh['2z' + self.twozhSelect][i]);
            } else if (i < 22) {
              twozhList['1'].push(self.sscOdds.twozzh['2z' + self.twozhSelect][i]);
            } else if (i < 33) {
              twozhList['2'].push(self.sscOdds.twozzh['2z' + self.twozhSelect][i]);
            } else if (i < 44) {
              twozhList['3'].push(self.sscOdds.twozzh['2z' + self.twozhSelect][i]);
            } else if (i < 55) {
              twozhList['4'].push(self.sscOdds.twozzh['2z' + self.twozhSelect][i]);
            }
          }
        }


        return twozhList;
      },
      threezhResultOdds() {
        let self = this;
        let threezhList = {
          '0': [],
          '1': [],
          '2': [],
          '3': [],
          '4': [],
        };
        let arr = [];
        if (self.sscOdds.threezzh['3z' + self.threezhSelect]) {
            for (let i = 0; i <= self.sscOdds.threezzh['3z' + self.threezhSelect].length; i++) {
              if(self.sscOdds.threezzh['3z' + self.threezhSelect][i]){
                self.$set(self.sscOdds.threezzh['3z' + self.threezhSelect][i],'odds',self.oddsReturn(self.sscOdds.threezzh['3z' + self.threezhSelect][i]));
              }
              if (i < 44) {
                threezhList['0'].push(self.sscOdds.threezzh['3z' + self.threezhSelect][i]);
              } else if (i < 88) {
                threezhList['1'].push(self.sscOdds.threezzh['3z' + self.threezhSelect][i]);
              } else if (i < 132) {
                threezhList['2'].push(self.sscOdds.threezzh['3z' + self.threezhSelect][i]);
              } else if (i < 176) {
                threezhList['3'].push(self.sscOdds.threezzh['3z' + self.threezhSelect][i]);
              } else if (i < 220) {
                threezhList['4'].push(self.sscOdds.threezzh['3z' + self.threezhSelect][i]);
              }
            }


        }

        return threezhList;
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
    watch: {
      twohsSelect(){
        this.setSelectList(null);
      },
      threehsSelect(){
        this.setSelectList(null);
      },
      fszhSelect(){
        this.fszhInitialization();
        this.setSelectList(null);
      },
      setSelectListFast(){
        this.$refs.betButton.focus();
        document.getElementById('betButton').focus();
      },
      kdSelect(){
        let self = this;
        for(let key in this.kdOddsResult){
          this.kdOddsResult[key].forEach(val=>{
            self.$delete(val,"choose");
            self.$delete(val,"check");
            self.$delete(val,"betAmt");
          })
        }
      },
      zxthreeSelect(){
        this.zxthreeInitialization();
      },
      zxsixSelect(){
        this.zxthreeInitialization();
      },
      categorySelect() {
        if (this.categorySelect == 'twodw') {
          this.twodwInitialization();
        } else if (this.categorySelect == 'threedw') {
          this.threedwInitialization();
        }else if(this.categorySelect == 'zxthree' || this.categorySelect == 'zxsix'){
          this.zxthreeInitialization();
        }else if(this.categorySelect =='fszh'){
          this.fszhInitialization();
        }else if(this.categorySelect == 'kd'){
          this.kdInitialization();
        }
        this.setSelectList(null);
      },
      twodwSelect() {
        this.twodwInitialization();
      },
      twodwSelectMode() {
        this.twodwInitialization();
      },
      threedwSelect() {
        this.threedwInitialization();
      },
      threedwSelectMode() {
        this.threedwInitialization();
      },
      specialCheckBoxList() {

      },
      pagePresetAmt() {
        let self = this;
        if (self.pagePresetStatus) {
          self.setBetAmtPreSet(self.pagePresetAmt);
        }
      },

    },
    methods: {
      ...mapActions(['setSelectListFast','setQuickBetStatus','setBetAmtPreSet','setBetAmtStatus','setPromptInformation', 'setQuickSumList', 'setBetGameNo', 'setSelectList', 'setLotteryNow', 'setMarket', 'setCurrentGame', 'setCategorySelect', 'setPlayType']),
      fszhInitialization(){
        let self = this;
        let keys = '';
        if(self.fszhSelect=='top3'){
          keys = 'wqb';
        }else if(self.fszhSelect=='mid3'){
          keys = 'qbs';
        }else if(self.fszhSelect=='end3'){
          keys='bsg';
        }
        self.fszhFirstStatus = {'w':false,'q':false,'b':false,'s':false,'g':false};
        for(let i = 0;i<10;i++){
          self.$set(self.fszhList,i,[]);
          let objOne = {oddsKey:i,choose:false,check:false,key:keys.substring(0,1)};
          let objTwo = {oddsKey:i,choose:false,check:false,key:keys.substring(1,2)};
          let objThree = {oddsKey:i,choose:false,check:false,key:keys.substring(2,3)};
          self.fszhList[i].push(Object.assign({},objOne));
          self.fszhList[i].push(Object.assign({},objTwo));
          self.fszhList[i].push(Object.assign({},objThree));
        }
      },
      kdInitialization(){

      },
      quickBetStatusChange() {
        let self = this;
        this.setQuickBetStatus(this.pageQuickBetStatus);
        self.allSearchList=[false,false,false,false,false];
        self.setSelectList(null);
      },
      zxthreeInitialization(){
        let self = this;
        self.zxthreeList = {};
        self.zxsixStatus = false;
        for(let i = 0;i<5;i++){
          let arr = [];
          let obj = {oddsKey:i,choose:false,check:false};
          arr.push(JSON.parse(JSON.stringify(obj)));
          obj.oddsKey=i+5;
          arr.push(JSON.parse(JSON.stringify(obj)));
          self.$set(self.zxthreeList,i,arr);
        }


      },
      threedwInitialization() {
        let self = this;
        for (let key in self.threedwSecondKindsList) {
          self.twodwSecondKindsList[key].list.forEach(obj => {
            self.$delete(obj, "choose");
            self.$delete(obj, "check");
          })
        }
        for (let key in self.threedwFirstKindList) {
          self.threedwFirstKindList[key].list.forEach(obj => {
            self.$delete(obj, "choose");
            self.$delete(obj, "check");
          })
        }
        let keys = '';
        if (self.threedwSelect == 'top3') {
          keys = 'wqb';
        } else if (self.threedwSelect == 'mid3') {
          keys = 'qbs';
        } else if (self.threedwSelect == 'end3') {
          keys = 'bsg';
        }
        self.threedwSecondKindsList = {
          '0': {'list': []}, '1': {'list': []}, '2': {'list': []}, '3': {'list': []}, '4': {'list': []},
          '5': {'list': []}, '6': {'list': []}, '7': {'list': []}, '8': {'list': []}, '9': {'list': []},
        };
        self.threedwFirstKindList = {
          0: {'key': keys.substring(0, 1), 'list': []},
          1: {'key': keys.substring(1, 2), 'list': []},
          2: {'key': keys.substring(2, 3), 'list': []}
        };
        for (let i = 0; i < 10; i++) {
          for (let j = 0; j < 10; j++) {
            for (let k = 0; k < 10; k++) {
              let obj = {'oddsKey': i + '' + j + '' + k, 'choose': false, 'check': false};
              self.$set(obj, "fsNumOdd", i % 2 == 1 ? true : false);
              self.$set(obj, "fsNumEven", i % 2 == 0 ? true : false);
              self.$set(obj, "secNumOdd", j % 2 == 1 ? true : false);
              self.$set(obj, "secNumEven", j % 2 == 0 ? true : false);
              self.$set(obj, "trdNumOdd", k % 2 == 1 ? true : false);
              self.$set(obj, "trdNumEven", k % 2 == 0 ? true : false);
              self.$set(obj, "doubleNumTake", i == j || j == k || i == k ? true : false);
              self.$set(obj, "doubleNumExcept", i != j && j != k && i != k ? true : false);
              self.$set(obj, "repeatNumTake", i == j && i == k ? true : false);
              self.$set(obj, "repeatNumExcept", i != j || i != k ? true : false);
              self.$set(obj, "brotherNumTake", i - j == 1 || i - j == -1 || i - j == 9 || i - j == -9 || i - k == 1 || i - k == -1 || i - k == 9 || i - k == -9 || j - k == 1 || j - k == -1 || j - k == 9 || j - k == -9 ? true : false);
              self.$set(obj, "brotherNumExcept", i - j != 1 && i - j != -1 && i - j != 9 && i - j != -9 && i - k != 1 && i - k != -1 && i - k != 9 && i - k != -9 && j - k != 1 && j - k != -1 && j - k != 9 && j - k != -9 ? true : false);
              self.$set(obj, "threeBrotherNumTake", self.threeBrotherNumTakeAlgorithm(i, j, k, 'take'));
              self.$set(obj, "threeBrotherNumExcept", self.threeBrotherNumTakeAlgorithm(i, j, k, 'except'));
              self.threedwSecondKindsList[i].list.push(obj);
            }

          }
        }

        for (let i = 0; i < 10; i++) {
          let obj = {'oddsKey': i, 'choose': false, 'check': false};
          self.threedwFirstKindList["0"].list.push(Object.assign({}, obj));
          self.threedwFirstKindList["1"].list.push(Object.assign({}, obj));
          self.threedwFirstKindList["2"].list.push(Object.assign({}, obj));
        }
        self.threeNumberTake = '';
        self.threeNumberExcept = '';
        self.threeFsNumTake = '';
        self.threeSecNumTake = '';
        self.threeTrdNumTake = '';
        self.threeFsNum.odd = false;
        self.threeFsNum.even = false;
        self.threeDoubleNum.take = false;
        self.threeDoubleNum.except = false;
        self.threeRepeatNum.take = false;
        self.threeRepeatNum.except = false;
        self.threeSecNum.odd = false;
        self.threeSecNum.even = false;
        self.threeTwoBrotherNum.take = false;
        self.threeTwoBrotherNum.except = false;
        self.threeTrdNum.odd = false;
        self.threeTrdNum.even = false;
        self.threeThreeBrotherNum.take = false;
        self.threeThreeBrotherNum.except = false;

        self.threedwScreenList = {};
      },
      threeBrotherNumTakeAlgorithm(one, two, three, type) {
        let judgeOne = '';
        let judgeTwo = '';
        let judgethree = '';
        if (type == 'take') {
          if (one == two || two == three || one == three) {
            return false;
          }
          judgeOne = one - two == 1 || one - two == -1 || one - two == -9 || one - two == 9 ? true : false;
          judgeTwo = one - three == 1 || one - three == -1 || one - three == -9 || one - three == 9 ? true : false;
          judgethree = two - three == 1 || two - three == -1 || two - three == -9 || two - three == 9 ? true : false;
        } else {
          if (one == two || two == three || one == three) {
            return true;
          }
          judgeOne = one - two != 1 && one - two != -1 && one - two != -9 && one - two != 9 ? true : false;
          judgeTwo = one - three != 1 && one - three != -1 && one - three != -9 && one - three != 9 ? true : false;
          judgethree = two - three != 1 && two - three != -1 && two - three != -9 && two - three != 9 ? true : false;
        }

        if (judgeOne && judgeTwo) {
          return true;
        } else if (judgeOne && judgethree) {
          return true;
        } else if (judgeTwo && judgethree) {
          return true;
        } else {
          return false;
        }


      },
      twodwInitialization() {
        let self = this;
        for (let key in self.twodwSecondKindsList) {
          self.twodwSecondKindsList[key].list.forEach(obj => {
            self.$delete(obj, "choose");
            self.$delete(obj, "check");
          })
        }
        self.twodwFirstKindList[0].list.forEach(obj => {
          self.$delete(obj, "choose");
          self.$delete(obj, "check");
        })
        self.twodwFirstKindList[1].list.forEach(obj => {
          self.$delete(obj, "choose");
          self.$delete(obj, "check");
        })
        self.twodwSecondKindsList = {
          '0': {'list': []}, '1': {'list': []}, '2': {'list': []}, '3': {'list': []}, '4': {'list': []},
          '5': {'list': []}, '6': {'list': []}, '7': {'list': []}, '8': {'list': []}, '9': {'list': []},
        };
        self.twodwFirstKindList = {0: {'key': '', 'list': []}, 1: {'key': '', 'list': []}};
        for (let i = 0; i < 10; i++) {
          for (let j = 0; j < 10; j++) {
            let obj = {'oddsKey': i + '' + j, 'choose': false, 'check': false};
            self.$set(obj, "fsNumOdd", i % 2 == 1 ? true : false);
            self.$set(obj, "fsNumEven", i % 2 == 0 ? true : false);
            self.$set(obj, "secNumOdd", j % 2 == 1 ? true : false);
            self.$set(obj, "secNumEven", j % 2 == 0 ? true : false);
            self.$set(obj, "doubleNumTake", i == j ? true : false);
            self.$set(obj, "doubleNumExcept", i != j ? true : false);
            self.$set(obj, "brotherNumTake", i - j == 1 || i - j == -1 || i - j == 9 || i - j == -9 ? true : false);
            self.$set(obj, "brotherNumExcept", i - j != 1 && i - j != -1 && i - j != 9 && i - j != -9 ? true : false);
            self.twodwSecondKindsList[i].list.push(obj);
          }
        }

        for (let i = 0; i < 10; i++) {
          let obj = {'oddsKey': i, 'choose': false, 'check': false};
          self.twodwFirstKindList["0"].list.push(Object.assign({}, obj));
          self.twodwFirstKindList["0"].key = self.twodwSelect.substring(0, 1);
          self.twodwFirstKindList["1"].list.push(Object.assign({}, obj));
          self.twodwFirstKindList["1"].key = self.twodwSelect.substring(1, 2);
        }
        self.specialCheckBoxList = {};
        self.fsNum.odd = false;
        self.fsNum.even = false;
        self.doubleNum.take = false;
        self.doubleNum.except = false;
        self.secNum.odd = false;
        self.secNum.even = false;
        self.brotherNum.take = false;
        self.brotherNum.except = false;
        self.fsNumTake = '';
        self.secNumTake = '';
        self.numberTake = '';
        self.numberExcept = '';

      },
      twozhChanges(select) {
        let self = this;
        self.setSelectList(null);
        self.twozhSelect = select;
      },
      threedwSpecialSelectFun(num) {
        let self = this;
        if(!self.betState) return;
        if (num == 0) {

          if (self.threeFsNum.odd) {
            self.$set(self.specialCheckBoxList, "fsNumOdd", self.threeFsNum.odd);
            self.$delete(self.specialCheckBoxList, "fsNumEven");
          } else {
            self.$delete(self.specialCheckBoxList, "fsNumOdd");
          }
          self.threeFsNum.even = false;
        } else if (num == 1) {

          if (self.threeFsNum.even) {
            self.$set(self.specialCheckBoxList, "fsNumEven", self.threeFsNum.even);
            self.$delete(self.specialCheckBoxList, "fsNumOdd");
          } else {
            self.$delete(self.specialCheckBoxList, "fsNumEven");
          }
          self.threeFsNum.odd = false;
        } else if (num == 2) {

          if (self.threeDoubleNum.take) {
            self.$set(self.specialCheckBoxList, "doubleNumTake", self.threeDoubleNum.take);
            self.$delete(self.specialCheckBoxList, "doubleNumExcept");
          } else {
            self.$delete(self.specialCheckBoxList, "doubleNumTake");
          }
          self.threeDoubleNum.except = false;
        } else if (num == 3) {

          if (self.threeDoubleNum.except) {
            self.$set(self.specialCheckBoxList, "doubleNumExcept", self.threeDoubleNum.except);
            self.$delete(self.specialCheckBoxList, "doubleNumTake");
          } else {
            self.$delete(self.specialCheckBoxList, "doubleNumExcept");
          }
          self.threeDoubleNum.take = false;
        } else if (num == 4) {

          if (self.threeTwoBrotherNum.take) {
            self.$set(self.specialCheckBoxList, "brotherNumTake", self.threeTwoBrotherNum.take);
            self.$delete(self.specialCheckBoxList, "brotherNumExcept");
          } else {
            self.$delete(self.specialCheckBoxList, "brotherNumTake");
          }
          self.threeTwoBrotherNum.except = false;
        } else if (num == 5) {

          if (self.threeTwoBrotherNum.except) {
            self.$set(self.specialCheckBoxList, "brotherNumExcept", self.threeTwoBrotherNum.except);
            self.$delete(self.specialCheckBoxList, "brotherNumTake");
          } else {
            self.$delete(self.specialCheckBoxList, "brotherNumExcept");
          }
          self.threeTwoBrotherNum.take = false;
        } else if (num == 6) {

          if (self.threeSecNum.odd) {
            self.$set(self.specialCheckBoxList, "secNumOdd", self.threeSecNum.odd);
            self.$delete(self.specialCheckBoxList, "secNumEven");
          } else {
            self.$delete(self.specialCheckBoxList, "secNumOdd");
          }
          self.threeSecNum.even = false;
        } else if (num == 7) {

          if (self.threeSecNum.even) {
            self.$set(self.specialCheckBoxList, "secNumEven", self.threeSecNum.even);
            self.$delete(self.specialCheckBoxList, "secNumOdd");
          } else {
            self.$delete(self.specialCheckBoxList, "secNumEven");
          }
          self.threeSecNum.odd = false;
        } else if (num == 8) {

          if (self.threeRepeatNum.take) {
            self.$set(self.specialCheckBoxList, "repeatNumTake", self.threeRepeatNum.take);
            self.$delete(self.specialCheckBoxList, "repeatNumExcept");
          } else {
            self.$delete(self.specialCheckBoxList, "repeatNumTake");
          }
          self.threeRepeatNum.except = false;
        } else if (num == 9) {

          if (self.threeRepeatNum.except) {
            self.$set(self.specialCheckBoxList, "repeatNumExcept", self.threeRepeatNum.except);
            self.$delete(self.specialCheckBoxList, "repeatNumTake");
          } else {
            self.$delete(self.specialCheckBoxList, "repeatNumExcept");
          }
          self.threeRepeatNum.take = false;
        } else if (num == 10) {

          if (self.threeThreeBrotherNum.take) {
            self.$set(self.specialCheckBoxList, "threeBrotherNumTake", self.threeThreeBrotherNum.take);
            self.$delete(self.specialCheckBoxList, "threeBrotherNumExcept");
          } else {
            self.$delete(self.specialCheckBoxList, "threeBrotherNumTake");
          }
          self.threeThreeBrotherNum.except = false;
        } else if (num == 11) {

          if (self.threeThreeBrotherNum.except) {
            self.$set(self.specialCheckBoxList, "threeBrotherNumExcept", self.threeThreeBrotherNum.except);
            self.$delete(self.specialCheckBoxList, "threeBrotherNumTake");
          } else {
            self.$delete(self.specialCheckBoxList, "threeBrotherNumExcept");
          }
          self.threeThreeBrotherNum.take = false;
        } else if (num == 12) {

          if (self.threeTrdNum.odd) {
            self.$set(self.specialCheckBoxList, "trdNumOdd", self.threeTrdNum.odd);
            self.$delete(self.specialCheckBoxList, "trdNumEven");
          } else {
            self.$delete(self.specialCheckBoxList, "trdNumOdd");
          }
          self.threeTrdNum.even = false;
        } else if (num == 13) {

          if (self.threeTrdNum.even) {
            self.$set(self.specialCheckBoxList, "trdNumEven", self.threeTrdNum.even);
            self.$delete(self.specialCheckBoxList, "trdNumOdd");
          } else {
            self.$delete(self.specialCheckBoxList, "trdNumEven");
          }
          self.threeTrdNum.odd = false;
        }
        self.threedwListChange();
      },
      threedwListChange() {
        let self = this;
        if(!self.betState) return;
        self.threedwScreenList = {};
        let screenIndex = 0;
        self.$set(self.threedwScreenList, screenIndex, {'list': []});
        if (JSON.stringify(self.specialCheckBoxList) === '{}') {
          for (let key in self.threedwSecondKindsList) {
            for (let i = 0; i < self.threedwSecondKindsList[key].list.length; i++) {
              self.$delete(self.threedwSecondKindsList[key].list[i], "choose");
              self.$delete(self.threedwSecondKindsList[key].list[i], "check");
            }
          }
        }
        let arr = {};

        for (let i in self.threedwSecondKindsList) {
          self.threedwSecondKindsList[i].list.filter(item => {
            let isflag = false;

            let checkBoxFlag = false;

            let inputFlag = false;

            let inputCheck = false;

            if (self.threeFsNumTake || self.threeSecNumTake || self.threeNumberTake || self.threeNumberExcept || self.threeTrdNumTake) {
              inputFlag = true;
            }

            if (self.threeFsNumTake && self.threeSecNumTake && self.threeNumberTake && self.threeNumberExcept && self.threeTrdNumTake) {
              if (self.threeFsNumTake.indexOf(item.oddsKey.substring(0, 1)) != -1 &&
                self.threeSecNumTake.indexOf(item.oddsKey.substring(1, 2)) != -1 &&
                (self.threeNumberTake.indexOf(item.oddsKey.substring(0, 1)) != -1 || self.threeNumberTake.indexOf(item.oddsKey.substring(1, 2)) != -1 || self.threeNumberTake.indexOf(item.oddsKey.substring(2, 3)) != -1) &&
                (self.threeNumberExcept.indexOf(item.oddsKey.substring(0, 1)) == -1 && self.threeNumberExcept.indexOf(item.oddsKey.substring(1, 2)) == -1 && self.threeNumberExcept.indexOf(item.oddsKey.substring(2, 3)) == -1) &&
                self.threeTrdNumTake.indexOf(item.oddsKey.substring(2, 3)) != -1) {
                inputCheck = true;
              }
            } else if (self.threeFsNumTake && self.threeSecNumTake && self.threeNumberTake && self.threeNumberExcept) {
              if (self.threeFsNumTake.indexOf(item.oddsKey.substring(0, 1)) != -1 &&
                self.threeSecNumTake.indexOf(item.oddsKey.substring(1, 2)) != -1 &&
                (self.threeNumberTake.indexOf(item.oddsKey.substring(0, 1)) != -1 || self.threeNumberTake.indexOf(item.oddsKey.substring(1, 2)) != -1 || self.threeNumberTake.indexOf(item.oddsKey.substring(2, 3)) != -1) &&
                (self.threeNumberExcept.indexOf(item.oddsKey.substring(0, 1)) == -1 && self.threeNumberExcept.indexOf(item.oddsKey.substring(1, 2)) == -1 && self.threeNumberExcept.indexOf(item.oddsKey.substring(2, 3)) == -1)
              ) {
                inputCheck = true;
              }
            } else if (self.threeFsNumTake && self.threeSecNumTake && self.threeNumberTake && self.threeTrdNumTake) {
              if (self.threeFsNumTake.indexOf(item.oddsKey.substring(0, 1)) != -1 &&
                self.threeSecNumTake.indexOf(item.oddsKey.substring(1, 2)) != -1 &&
                (self.threeNumberTake.indexOf(item.oddsKey.substring(0, 1)) != -1 || self.threeNumberTake.indexOf(item.oddsKey.substring(1, 2)) != -1 || self.threeNumberTake.indexOf(item.oddsKey.substring(2, 3)) != -1) &&
                self.threeTrdNumTake.indexOf(item.oddsKey.substring(2, 3)) != -1) {
                inputCheck = true;
              }
            } else if (self.threeFsNumTake && self.threeSecNumTake && self.threeNumberExcept && self.threeTrdNumTake) {
              if (self.threeFsNumTake.indexOf(item.oddsKey.substring(0, 1)) != -1 &&
                self.threeSecNumTake.indexOf(item.oddsKey.substring(1, 2)) != -1 &&
                (self.threeNumberExcept.indexOf(item.oddsKey.substring(0, 1)) == -1 && self.threeNumberExcept.indexOf(item.oddsKey.substring(1, 2)) == -1 && self.threeNumberExcept.indexOf(item.oddsKey.substring(2, 3)) == -1) &&
                self.threeTrdNumTake.indexOf(item.oddsKey.substring(2, 3)) != -1) {
                inputCheck = true;
              }
            } else if (self.threeFsNumTake && self.threeNumberTake && self.threeNumberExcept && self.threeTrdNumTake) {
              if (self.threeFsNumTake.indexOf(item.oddsKey.substring(0, 1)) != -1 &&
                (self.threeNumberTake.indexOf(item.oddsKey.substring(0, 1)) != -1 || self.threeNumberTake.indexOf(item.oddsKey.substring(1, 2)) != -1 || self.threeNumberTake.indexOf(item.oddsKey.substring(2, 3)) != -1) &&
                (self.threeNumberExcept.indexOf(item.oddsKey.substring(0, 1)) == -1 && self.threeNumberExcept.indexOf(item.oddsKey.substring(1, 2)) == -1 && self.threeNumberExcept.indexOf(item.oddsKey.substring(2, 3)) == -1) &&
                self.threeTrdNumTake.indexOf(item.oddsKey.substring(2, 3)) != -1) {
                inputCheck = true;
              }
            } else if (self.threeSecNumTake && self.threeNumberTake && self.threeNumberExcept && self.threeTrdNumTake) {
              if (
                self.threeSecNumTake.indexOf(item.oddsKey.substring(1, 2)) != -1 &&
                (self.threeNumberTake.indexOf(item.oddsKey.substring(0, 1)) != -1 || self.threeNumberTake.indexOf(item.oddsKey.substring(1, 2)) != -1 || self.threeNumberTake.indexOf(item.oddsKey.substring(2, 3)) != -1) &&
                (self.threeNumberExcept.indexOf(item.oddsKey.substring(0, 1)) == -1 && self.threeNumberExcept.indexOf(item.oddsKey.substring(1, 2)) == -1 && self.threeNumberExcept.indexOf(item.oddsKey.substring(2, 3)) == -1) &&
                self.threeTrdNumTake.indexOf(item.oddsKey.substring(2, 3)) != -1) {
                inputCheck = true;
              }
            } else if (self.threeFsNumTake && self.threeSecNumTake && self.threeNumberTake) {
              if (self.threeFsNumTake.indexOf(item.oddsKey.substring(0, 1)) != -1 &&
                self.threeSecNumTake.indexOf(item.oddsKey.substring(1, 2)) != -1 &&
                (self.threeNumberTake.indexOf(item.oddsKey.substring(0, 1)) != -1 || self.threeNumberTake.indexOf(item.oddsKey.substring(1, 2)) != -1 || self.threeNumberTake.indexOf(item.oddsKey.substring(2, 3)) != -1)) {
                inputCheck = true;
              }
            } else if (self.threeFsNumTake && self.threeSecNumTake && self.threeNumberExcept) {
              if (self.threeFsNumTake.indexOf(item.oddsKey.substring(0, 1)) != -1 &&
                self.threeSecNumTake.indexOf(item.oddsKey.substring(1, 2)) != -1 &&
                (self.threeNumberExcept.indexOf(item.oddsKey.substring(0, 1)) == -1 && self.threeNumberExcept.indexOf(item.oddsKey.substring(1, 2)) == -1 && self.threeNumberExcept.indexOf(item.oddsKey.substring(2, 3)) == -1)) {
                inputCheck = true;
              }
            } else if (self.threeFsNumTake && self.threeSecNumTake && self.threeTrdNumTake) {
              if (self.threeFsNumTake.indexOf(item.oddsKey.substring(0, 1)) != -1 &&
                self.threeSecNumTake.indexOf(item.oddsKey.substring(1, 2)) != -1 &&
                self.threeTrdNumTake.indexOf(item.oddsKey.substring(2, 3)) != -1) {
                inputCheck = true;
              }
            } else if (self.threeFsNumTake && self.threeNumberTake && self.threeNumberExcept) {
              if (self.threeFsNumTake.indexOf(item.oddsKey.substring(0, 1)) != -1 &&
                (self.threeNumberTake.indexOf(item.oddsKey.substring(0, 1)) != -1 || self.threeNumberTake.indexOf(item.oddsKey.substring(1, 2)) != -1 || self.threeNumberTake.indexOf(item.oddsKey.substring(2, 3)) != -1) &&
                (self.threeNumberExcept.indexOf(item.oddsKey.substring(0, 1)) == -1 && self.threeNumberExcept.indexOf(item.oddsKey.substring(1, 2)) == -1 && self.threeNumberExcept.indexOf(item.oddsKey.substring(2, 3)) == -1)) {
                inputCheck = true;
              }
            } else if (self.threeFsNumTake && self.threeNumberTake && self.threeTrdNumTake) {
              if (self.threeFsNumTake.indexOf(item.oddsKey.substring(0, 1)) != -1 &&
                (self.threeNumberTake.indexOf(item.oddsKey.substring(0, 1)) != -1 || self.threeNumberTake.indexOf(item.oddsKey.substring(1, 2)) != -1 || self.threeNumberTake.indexOf(item.oddsKey.substring(2, 3)) != -1) &&
                self.threeTrdNumTake.indexOf(item.oddsKey.substring(2, 3)) != -1) {
                inputCheck = true;
              }
            } else if (self.threeFsNumTake && self.threeNumberExcept && self.threeTrdNumTake) {
              if (self.threeFsNumTake.indexOf(item.oddsKey.substring(0, 1)) != -1 &&
                (self.threeNumberExcept.indexOf(item.oddsKey.substring(0, 1)) == -1 && self.threeNumberExcept.indexOf(item.oddsKey.substring(1, 2)) == -1 && self.threeNumberExcept.indexOf(item.oddsKey.substring(2, 3)) == -1) &&
                self.threeTrdNumTake.indexOf(item.oddsKey.substring(2, 3)) != -1) {
                inputCheck = true;
              }
            } else if (self.threeSecNumTake && self.threeNumberTake && self.threeNumberExcept) {
              if (
                self.threeSecNumTake.indexOf(item.oddsKey.substring(1, 2)) != -1 &&
                (self.threeNumberTake.indexOf(item.oddsKey.substring(0, 1)) != -1 || self.threeNumberTake.indexOf(item.oddsKey.substring(1, 2)) != -1 || self.threeNumberTake.indexOf(item.oddsKey.substring(2, 3)) != -1) &&
                (self.threeNumberExcept.indexOf(item.oddsKey.substring(0, 1)) == -1 && self.threeNumberExcept.indexOf(item.oddsKey.substring(1, 2)) == -1 && self.threeNumberExcept.indexOf(item.oddsKey.substring(2, 3)) == -1)) {
                inputCheck = true;
              }
            } else if (self.threeSecNumTake && self.threeNumberTake && self.threeTrdNumTake) {
              if (
                self.threeSecNumTake.indexOf(item.oddsKey.substring(1, 2)) != -1 &&
                (self.threeNumberTake.indexOf(item.oddsKey.substring(0, 1)) != -1 || self.threeNumberTake.indexOf(item.oddsKey.substring(1, 2)) != -1 || self.threeNumberTake.indexOf(item.oddsKey.substring(2, 3)) != -1) &&
                self.threeTrdNumTake.indexOf(item.oddsKey.substring(2, 3)) != -1) {
                inputCheck = true;
              }
            } else if (self.threeSecNumTake && self.threeNumberExcept && self.threeTrdNumTake) {
              if (
                self.threeSecNumTake.indexOf(item.oddsKey.substring(1, 2)) != -1 &&
                (self.threeNumberExcept.indexOf(item.oddsKey.substring(0, 1)) == -1 && self.threeNumberExcept.indexOf(item.oddsKey.substring(1, 2)) == -1 && self.threeNumberExcept.indexOf(item.oddsKey.substring(2, 3)) == -1) &&
                self.threeTrdNumTake.indexOf(item.oddsKey.substring(2, 3)) != -1) {
                inputCheck = true;
              }
            } else if (self.threeNumberTake && self.threeNumberExcept && self.threeTrdNumTake) {
              if ((self.threeNumberTake.indexOf(item.oddsKey.substring(0, 1)) != -1 || self.threeNumberTake.indexOf(item.oddsKey.substring(1, 2)) != -1 || self.threeNumberTake.indexOf(item.oddsKey.substring(2, 3)) != -1) &&
                (self.threeNumberExcept.indexOf(item.oddsKey.substring(0, 1)) == -1 && self.threeNumberExcept.indexOf(item.oddsKey.substring(1, 2)) == -1 && self.threeNumberExcept.indexOf(item.oddsKey.substring(2, 3)) == -1) &&
                self.threeTrdNumTake.indexOf(item.oddsKey.substring(2, 3)) != -1) {
                inputCheck = true;
              }
            } else if (self.threeFsNumTake && self.threeSecNumTake) {
              if (self.threeFsNumTake.indexOf(item.oddsKey.substring(0, 1)) != -1 &&
                self.threeSecNumTake.indexOf(item.oddsKey.substring(1, 2)) != -1) {
                inputCheck = true;
              }
            } else if (self.threeFsNumTake && self.threeNumberTake) {
              if (self.threeFsNumTake.indexOf(item.oddsKey.substring(0, 1)) != -1 &&
                (self.threeNumberTake.indexOf(item.oddsKey.substring(0, 1)) != -1 || self.threeNumberTake.indexOf(item.oddsKey.substring(1, 2)) != -1 || self.threeNumberTake.indexOf(item.oddsKey.substring(2, 3)) != -1)) {
                inputCheck = true;
              }
            } else if (self.threeFsNumTake && self.threeNumberExcept) {
              if (self.threeFsNumTake.indexOf(item.oddsKey.substring(0, 1)) != -1 &&
                (self.threeNumberExcept.indexOf(item.oddsKey.substring(0, 1)) == -1 && self.threeNumberExcept.indexOf(item.oddsKey.substring(1, 2)) == -1 && self.threeNumberExcept.indexOf(item.oddsKey.substring(2, 3)) == -1)) {
                inputCheck = true;
              }
            } else if (self.threeFsNumTake && self.threeTrdNumTake) {
              if (self.threeFsNumTake.indexOf(item.oddsKey.substring(0, 1)) != -1 &&
                self.threeTrdNumTake.indexOf(item.oddsKey.substring(2, 3)) != -1) {
                inputCheck = true;
              }
            } else if (self.threeSecNumTake && self.threeNumberTake) {
              if (
                self.threeSecNumTake.indexOf(item.oddsKey.substring(1, 2)) != -1 &&
                (self.threeNumberTake.indexOf(item.oddsKey.substring(0, 1)) != -1 || self.threeNumberTake.indexOf(item.oddsKey.substring(1, 2)) != -1 || self.threeNumberTake.indexOf(item.oddsKey.substring(2, 3)) != -1)) {
                inputCheck = true;
              }
            } else if (self.threeSecNumTake && self.threeNumberExcept) {
              if (
                self.threeSecNumTake.indexOf(item.oddsKey.substring(1, 2)) != -1 &&
                (self.threeNumberExcept.indexOf(item.oddsKey.substring(0, 1)) == -1 && self.threeNumberExcept.indexOf(item.oddsKey.substring(1, 2)) == -1 && self.threeNumberExcept.indexOf(item.oddsKey.substring(2, 3)) == -1)) {
                inputCheck = true;
              }
            } else if (self.threeSecNumTake && self.threeTrdNumTake) {
              if (
                self.threeSecNumTake.indexOf(item.oddsKey.substring(1, 2)) != -1 &&
                self.threeTrdNumTake.indexOf(item.oddsKey.substring(2, 3)) != -1) {
                inputCheck = true;
              }
            } else if (self.threeNumberTake && self.threeNumberExcept) {
              if ((self.threeNumberTake.indexOf(item.oddsKey.substring(0, 1)) != -1 || self.threeNumberTake.indexOf(item.oddsKey.substring(1, 2)) != -1 || self.threeNumberTake.indexOf(item.oddsKey.substring(2, 3)) != -1) &&
                (self.threeNumberExcept.indexOf(item.oddsKey.substring(0, 1)) == -1 && self.threeNumberExcept.indexOf(item.oddsKey.substring(1, 2)) == -1 && self.threeNumberExcept.indexOf(item.oddsKey.substring(2, 3)) == -1)) {
                inputCheck = true;
              }
            } else if (self.threeNumberTake && self.threeTrdNumTake) {
              if ((self.threeNumberTake.indexOf(item.oddsKey.substring(0, 1)) != -1 || self.threeNumberTake.indexOf(item.oddsKey.substring(1, 2)) != -1 || self.threeNumberTake.indexOf(item.oddsKey.substring(2, 3)) != -1) &&
                self.threeTrdNumTake.indexOf(item.oddsKey.substring(2, 3)) != -1) {
                inputCheck = true;
              }
            } else if (self.threeNumberExcept && self.threeTrdNumTake) {
              if ((self.threeNumberExcept.indexOf(item.oddsKey.substring(0, 1)) == -1 && self.threeNumberExcept.indexOf(item.oddsKey.substring(1, 2)) == -1 && self.threeNumberExcept.indexOf(item.oddsKey.substring(2, 3)) == -1) &&
                self.threeTrdNumTake.indexOf(item.oddsKey.substring(2, 3)) != -1) {
                inputCheck = true;
              }
            } else if (self.threeFsNumTake) {
              if (self.threeFsNumTake.indexOf(item.oddsKey.substring(0, 1)) != -1) {
                inputCheck = true;
              }
            } else if (self.threeSecNumTake) {
              if (
                self.threeSecNumTake.indexOf(item.oddsKey.substring(1, 2)) != -1) {
                inputCheck = true;
              }
            } else if (self.threeNumberTake) {
              if (
                self.threeNumberTake.indexOf(item.oddsKey.substring(0, 1)) != -1 || self.threeNumberTake.indexOf(item.oddsKey.substring(1, 2)) != -1 || self.threeNumberTake.indexOf(item.oddsKey.substring(2, 3)) != -1
              ) {
                inputCheck = true;
              }
            } else if (self.threeNumberExcept) {
              if ((self.threeNumberExcept.indexOf(item.oddsKey.substring(0, 1)) == -1 && self.threeNumberExcept.indexOf(item.oddsKey.substring(1, 2)) == -1 && self.threeNumberExcept.indexOf(item.oddsKey.substring(2, 3)) == -1)) {
                inputCheck = true;
              }
            } else if (self.threeTrdNumTake) {
              if (self.threeTrdNumTake.indexOf(item.oddsKey.substring(2, 3)) != -1) {
                inputCheck = true;
              }
            }

            for (let key in self.specialCheckBoxList) {
              if (key.indexOf('Input') == -1) {
                checkBoxFlag = true;
                if (item[key] == true) {
                  self.$set(arr, key, true);
                } else {
                  self.$set(arr, key, false);
                }
              }
            }

            for (let obj in arr) {
              if (!arr[obj]) {
                isflag = false;
                break;
              } else {
                isflag = true;
              }
            }
            if (self.threedwScreenList[screenIndex].list.length == 10) {
              screenIndex++;
              self.$set(self.threedwScreenList, screenIndex, {'list': []});
            }
            if (inputFlag) {
              if (inputCheck) {

                if (checkBoxFlag) {
                  if (isflag) {
                    self.$set(item, "choose", true);
                    self.$set(item, "check", true);
                    self.threedwScreenList[screenIndex].list.push(item);
                  } else {
                    self.$delete(item, "choose");
                    self.$delete(item, "check");
                  }
                } else {
                  self.$set(item, "choose", true);
                  self.$set(item, "check", true);
                  self.threedwScreenList[screenIndex].list.push(item);
                }
              } else {
                self.$delete(item, "choose");
                self.$delete(item, "check");
              }
            } else {
              if (isflag) {
                self.$set(item, "choose", true);
                self.$set(item, "check", true);
                self.threedwScreenList[screenIndex].list.push(item);
              } else {
                self.$delete(item, "choose");
                self.$delete(item, "check");
              }
            }


          })
        }
      },
      twodwSpecialSelectFun(num) {
        let self = this;
        if(!self.betState) return;
        if (num == 0) {

          if (self.fsNum.odd) {
            self.$set(self.specialCheckBoxList, "fsNumOdd", self.fsNum.odd);
            self.$delete(self.specialCheckBoxList, "fsNumEven");
          } else {
            self.$delete(self.specialCheckBoxList, "fsNumOdd");
          }
          self.fsNum.even = false;
        } else if (num == 1) {
          if (self.fsNum.even) {
            self.$set(self.specialCheckBoxList, "fsNumEven", self.fsNum.even);
            self.$delete(self.specialCheckBoxList, "fsNumOdd");
          } else {
            self.$delete(self.specialCheckBoxList, "fsNumEven");
          }
          self.fsNum.odd = false;
        } else if (num == 2) {
          if (self.doubleNum.take) {
            self.$set(self.specialCheckBoxList, "doubleNumTake", self.doubleNum.take);
            self.$delete(self.specialCheckBoxList, "doubleNumExcept");
          } else {
            self.$delete(self.specialCheckBoxList, "doubleNumTake");
          }
          self.doubleNum.except = false;
        } else if (num == 3) {
          if (self.doubleNum.except) {
            self.$set(self.specialCheckBoxList, "doubleNumExcept", self.doubleNum.except);
            self.$delete(self.specialCheckBoxList, "doubleNumTake");
          } else {
            self.$delete(self.specialCheckBoxList, "doubleNumExcept");
          }
          self.doubleNum.take = false;
        } else if (num == 4) {
          if (self.secNum.odd) {
            self.$set(self.specialCheckBoxList, "secNumOdd", self.secNum.odd);
            self.$delete(self.specialCheckBoxList, "secNumEven");
          } else {
            self.$delete(self.specialCheckBoxList, "secNumOdd");
          }
          self.secNum.even = false;
        } else if (num == 5) {
          if (self.secNum.even) {
            self.$set(self.specialCheckBoxList, "secNumEven", self.secNum.even);
            self.$delete(self.specialCheckBoxList, "secNumOdd");
          } else {
            self.$delete(self.specialCheckBoxList, "secNumEven");
          }
          self.secNum.odd = false;
        } else if (num == 6) {
          if (self.brotherNum.take) {
            self.$set(self.specialCheckBoxList, "brotherNumTake", self.brotherNum.take);
            self.$delete(self.specialCheckBoxList, "brotherNumExcept");
          } else {
            self.$delete(self.specialCheckBoxList, "brotherNumTake");
          }
          self.brotherNum.except = false;
        } else if (num == 7) {
          if (self.brotherNum.except) {
            self.$set(self.specialCheckBoxList, "brotherNumExcept", self.brotherNum.except);
            self.$delete(self.specialCheckBoxList, "brotherNumTake");
          } else {
            self.$delete(self.specialCheckBoxList, "brotherNumExcept");
          }
          self.brotherNum.take = false;
        }
        self.twodwListChange();
      },
      twodwSpecialInputFun(model) {
        let self = this;
        self.twodwListChange();
      },
      twodwListChange() {
        let self = this;
        if(!self.betState) return;
        if (JSON.stringify(self.specialCheckBoxList) === '{}') {
          for (let key in self.twodwSecondKindsList) {
            for (let i = 0; i < self.twodwSecondKindsList[key].list.length; i++) {
              self.$delete(self.twodwSecondKindsList[key].list[i], "choose");
              self.$delete(self.twodwSecondKindsList[key].list[i], "check");
            }
          }
        }
        let arr = {};

        for (let i in self.twodwSecondKindsList) {
          self.twodwSecondKindsList[i].list.filter(item => {
            let isflag = false;

            let checkBoxFlag = false;

            let inputFlag = false;

            let inputCheck = false;

            if (self.fsNumTake || self.secNumTake || self.numberTake || self.numberExcept) {
              inputFlag = true;
            }

            if (self.fsNumTake && self.secNumTake && self.numberTake && self.numberExcept) {
              if (self.fsNumTake.indexOf(item.oddsKey.substring(0, 1)) != -1 &&
                self.secNumTake.indexOf(item.oddsKey.substring(1, 2)) != -1 &&
                (self.numberTake.indexOf(item.oddsKey.substring(0, 1)) != -1 || self.numberTake.indexOf(item.oddsKey.substring(1, 2)) != -1) &&
                (self.numberExcept.indexOf(item.oddsKey.substring(0, 1)) == -1 && self.numberExcept.indexOf(item.oddsKey.substring(1, 2)) == -1)) {
                inputCheck = true;
              }
            } else if (self.fsNumTake && self.secNumTake && self.numberTake) {
              if (self.fsNumTake.indexOf(item.oddsKey.substring(0, 1)) != -1 &&
                self.secNumTake.indexOf(item.oddsKey.substring(1, 2)) != -1 &&
                (self.numberTake.indexOf(item.oddsKey.substring(0, 1)) != -1 || self.numberTake.indexOf(item.oddsKey.substring(1, 2)) != -1)) {
                inputCheck = true;
              }
            } else if (self.fsNumTake && self.numberTake && self.numberExcept) {
              if (self.fsNumTake.indexOf(item.oddsKey.substring(0, 1)) != -1 &&
                (self.numberTake.indexOf(item.oddsKey.substring(0, 1)) != -1 || self.numberTake.indexOf(item.oddsKey.substring(1, 2)) != -1) &&
                (self.numberExcept.indexOf(item.oddsKey.substring(0, 1)) == -1 && self.numberExcept.indexOf(item.oddsKey.substring(1, 2)) == -1)) {
                inputCheck = true;
              }
            } else if (self.fsNumTake && self.secNumTake && self.numberExcept) {
              if (self.fsNumTake.indexOf(item.oddsKey.substring(0, 1)) != -1 &&
                self.secNumTake.indexOf(item.oddsKey.substring(1, 2)) != -1 &&
                (self.numberExcept.indexOf(item.oddsKey.substring(0, 1)) == -1 && self.numberExcept.indexOf(item.oddsKey.substring(1, 2)) == -1)) {
                inputCheck = true;
              }
            } else if (self.secNumTake && self.numberTake && self.numberExcept) {
              if (
                self.secNumTake.indexOf(item.oddsKey.substring(1, 2)) != -1 &&
                (self.numberTake.indexOf(item.oddsKey.substring(0, 1)) != -1 || self.numberTake.indexOf(item.oddsKey.substring(1, 2)) != -1) &&
                (self.numberExcept.indexOf(item.oddsKey.substring(0, 1)) == -1 && self.numberExcept.indexOf(item.oddsKey.substring(1, 2)) == -1)) {
                inputCheck = true;
              }
            } else if (self.fsNumTake && self.secNumTake) {
              if (self.fsNumTake.indexOf(item.oddsKey.substring(0, 1)) != -1 &&
                self.secNumTake.indexOf(item.oddsKey.substring(1, 2)) != -1) {
                inputCheck = true;
              }
            } else if (self.fsNumTake && self.numberTake) {
              if (self.fsNumTake.indexOf(item.oddsKey.substring(0, 1)) != -1 &&
                (self.numberTake.indexOf(item.oddsKey.substring(0, 1)) != -1 || self.numberTake.indexOf(item.oddsKey.substring(1, 2)) != -1)) {
                inputCheck = true;
              }
            } else if (self.fsNumTake && self.numberExcept) {
              if (self.fsNumTake.indexOf(item.oddsKey.substring(0, 1)) != -1 &&
                (self.numberExcept.indexOf(item.oddsKey.substring(0, 1)) == -1 && self.numberExcept.indexOf(item.oddsKey.substring(1, 2)) == -1)) {
                inputCheck = true;
              }
            } else if (self.secNumTake && self.numberTake) {
              if (
                self.secNumTake.indexOf(item.oddsKey.substring(1, 2)) != -1 &&
                (self.numberTake.indexOf(item.oddsKey.substring(0, 1)) != -1 || self.numberTake.indexOf(item.oddsKey.substring(1, 2)) != -1)) {
                inputCheck = true;
              }
            } else if (self.secNumTake && self.numberExcept) {
              if (
                self.secNumTake.indexOf(item.oddsKey.substring(1, 2)) != -1 &&
                (self.numberExcept.indexOf(item.oddsKey.substring(0, 1)) == -1 && self.numberExcept.indexOf(item.oddsKey.substring(1, 2)) == -1)) {
                inputCheck = true;
              }
            } else if (self.numberTake && self.numberExcept) {
              if (
                (self.numberTake.indexOf(item.oddsKey.substring(0, 1)) != -1 || self.numberTake.indexOf(item.oddsKey.substring(1, 2)) != -1) &&
                (self.numberExcept.indexOf(item.oddsKey.substring(0, 1)) == -1 && self.numberExcept.indexOf(item.oddsKey.substring(1, 2)) == -1)) {
                inputCheck = true;
              }
            } else if (self.fsNumTake) {
              if (self.fsNumTake.indexOf(item.oddsKey.substring(0, 1)) != -1) {
                inputCheck = true;
              }
            } else if (self.secNumTake) {
              if (
                self.secNumTake.indexOf(item.oddsKey.substring(1, 2)) != -1) {
                inputCheck = true;
              }
            } else if (self.numberTake) {
              if (
                (self.numberTake.indexOf(item.oddsKey.substring(0, 1)) != -1 || self.numberTake.indexOf(item.oddsKey.substring(1, 2)) != -1)) {
                inputCheck = true;
              }
            } else if (self.numberExcept) {
              if ((self.numberExcept.indexOf(item.oddsKey.substring(0, 1)) == -1 && self.numberExcept.indexOf(item.oddsKey.substring(1, 2)) == -1)) {
                inputCheck = true;
              }
            }

            for (let key in self.specialCheckBoxList) {

              if (key.indexOf('Input') == -1) {
                checkBoxFlag = true;
                if (item[key] == true) {
                  self.$set(arr, key, true);
                } else {
                  self.$set(arr, key, false);
                }
              }
            }

            for (let obj in arr) {
              if (!arr[obj]) {
                isflag = false;
                break;
              } else {
                isflag = true;
              }
            }
            if (inputFlag) {
              if (inputCheck) {
                self.$set(item, "choose", true);
                self.$set(item, "check", true);
                if (checkBoxFlag) {
                  if (isflag) {
                    self.$set(item, "choose", true);
                    self.$set(item, "check", true);
                  } else {
                    self.$delete(item, "choose");
                    self.$delete(item, "check");
                  }
                }
              } else {
                self.$delete(item, "choose");
                self.$delete(item, "check");
              }
            } else {
              if (isflag) {
                self.$set(item, "choose", true);
                self.$set(item, "check", true);
              } else {
                self.$delete(item, "choose");
                self.$delete(item, "check");
              }
            }


          })
        }

      },
      threezhRange() {
        let self = this;
        self.setSelectList(null);
        let arr = [];
        self.sscOdds.threezzh['3z' + self.threezhSelect].forEach(obj => {
          if (parseInt(obj.oddsKey) >= parseInt(self.threezhRangeStart) && parseInt(obj.oddsKey) <= parseInt(self.threezhRangeStop)) {
            self.$set(obj,"choose",true);
            self.$set(obj,"check",true);
            self.$set(obj,"betAmt",self.pagePresetAmt);
            if(obj.betAmt){
              arr.push(Object.assign({},obj));
            }


          }
        });

        self.setSelectListFast(arr);
        /*this.$refs.betButton.focus();
        document.getElementById('betButton').focus();*/
        },
        threezhChanges(select){
          let self = this;
          self.setSelectList(null);
          self.sscOdds.threezzh['3z'+self.threezhSelect].forEach(obj=>{
            self.$delete(obj, "choose");
            self.$delete(obj, "check");
            self.$delete(obj, "betAmt");
          })
          self.threezhSelect = select;
        },

      /*disabledCheckFun(item,key){
        let self = this;

        if(self.categorySelect=='twodw' && self.twodwSelectMode=='firstKind' && !item.check){
          let oneNum = 0;
          let twoNum = 0;
          self.twodwFirstKindList[0].list.forEach(val=>{
            if(val.choose && val.check){
              oneNum++;
            }
          })
          self.twodwFirstKindList[1].list.forEach(val=>{
            if(val.choose && val.check){
              twoNum++;
            }
          })
          if(oneNum==8 && key==self.twodwFirstKindList[0].key){
            self.setPromptInformation('二字定位每一位最多只能选8个号!');
            return;
          }
          if(twoNum==8 && key==self.twodwFirstKindList[1].key){
            self.setPromptInformation('二字定位每一位最多只能选8个号!');
            return;
          }
        }
        self.$set(item,"check",false);
      },*/
        twoSecondKindsFun(item,key){
          let self = this;
          if(self.categorySelect=='zxsix' && !item.check){
            let index = 0;
            for(let key in self.zxthreeList){
              self.zxthreeList[key].forEach(val=>{
                if(val.check){
                  index++;
                }
              })
            }

            if(index==8){
              self.zxsixStatus = true;
              self.setPromptInformation('组选六最多只能选8个号!');
              return;
            }
          }else if(self.categorySelect=='fszh' && !item.check){
            let index = 0;
            let regSelectNums = {w:0,q:0,b:0,s:0,g:0};
            for(let key in self.fszhList){

              self.fszhList[key].forEach(val=>{
                if(val.check && val.choose){
                  index++;
                  regSelectNums[val.key]=regSelectNums[val.key]+1;
                }
              })

            }

            for(let key in regSelectNums){
              if(regSelectNums[key]>6 && item.key==key){
                self.setPromptInformation('复式组合每一位最多选择7个号!');
                return;
              }
            }
            if(index==21){
              self.setPromptInformation('复式组合最多只能选21个号!');
              return;
            }
          }else if(self.categorySelect=='twodw' && self.twodwSelectMode=='firstKind' && !item.check){
            let oneNum = 0;
            let twoNum = 0;
            self.twodwFirstKindList[0].list.forEach(val=>{
              if(val.choose && val.check){
                oneNum++;
              }
            })
            self.twodwFirstKindList[1].list.forEach(val=>{
              if(val.choose && val.check){
                twoNum++;
              }
            })
            if(oneNum==8 && key==self.twodwFirstKindList[0].key){
              self.twodwFirstOneStatus = true;
              self.setPromptInformation('二字定位每一位最多只能选8个号!');
              return;
            }
            if(twoNum==8 && key==self.twodwFirstKindList[1].key){
              self.twodwFirstTwoStatus = true;
              self.setPromptInformation('二字定位每一位最多只能选8个号!');
              return;
            }
          }else if(self.threedwSelectMode=='firstKind' && self.categorySelect=='threedw' && !item.check){
            let oneNum = 0;
            let twoNum = 0;
            let threeNum = 0;
            self.threedwFirstKindList[0].list.forEach(val=>{
              if(val.choose && val.check){
                oneNum++;
              }
            })
            self.threedwFirstKindList[1].list.forEach(val=>{
              if(val.choose && val.check){
                twoNum++;
              }
            })
            self.threedwFirstKindList[2].list.forEach(val=>{
              if(val.choose && val.check){
                threeNum++;
              }
            })
            if(oneNum==5 && key==self.threedwFirstKindList[0].key){
              self.threedwFirstOneStatus=true;
              self.setPromptInformation('三字定位每一位最多只能选5个号!');
              return;
            }
            if(twoNum==5 && key==self.threedwFirstKindList[1].key){
              self.threedwFirstTwoStatus=true;
              self.setPromptInformation('三字定位每一位最多只能选5个号!');
              return;
            }
            if(threeNum==5 && key==self.threedwFirstKindList[2].key){
              self.threedwFirstThreeStatus=true;
              self.setPromptInformation('三字定位每一位最多只能选5个号!');
              return;
            }
          }
          if (!this.betState || item.status === '1') {
            return;
          }
          this.$set(item, 'choose', !item.choose);
          this.$set(item, 'check', !item.check);

          if(self.categorySelect=='twodw' && self.twodwSelectMode=='firstKind'){
            let oneNum = 0;
            let twoNum = 0;
            self.twodwFirstKindList[0].list.forEach(val=>{
              if(val.choose && val.check){

                oneNum++;
              }
            })
            self.twodwFirstKindList[1].list.forEach(val=>{
              if(val.choose && val.check){

                twoNum++;
              }
            })

            if(oneNum==8 ){
              self.twodwFirstOneStatus = true;
            }else{
              self.twodwFirstOneStatus = false;
            }
            if(twoNum==8 ){
              self.twodwFirstTwoStatus = true;
            }else{
              self.twodwFirstTwoStatus = false;
            }
          }else if(self.threedwSelectMode=='firstKind' && self.categorySelect=='threedw'){
            let oneNum = 0;
            let twoNum = 0;
            let threeNum = 0;
            self.threedwFirstKindList[0].list.forEach(val=>{
              if(val.choose && val.check){
                oneNum++;
              }
            })
            self.threedwFirstKindList[1].list.forEach(val=>{
              if(val.choose && val.check){
                twoNum++;
              }
            })
            self.threedwFirstKindList[2].list.forEach(val=>{
              if(val.choose && val.check){
                threeNum++;
              }
            })
            if(oneNum==5 ){
              self.threedwFirstOneStatus=true;
            }else{
              self.threedwFirstOneStatus=false;
            }
            if(twoNum==5 ){
              self.threedwFirstTwoStatus=true;
            }else{
              self.threedwFirstTwoStatus=false;
            }
            if(threeNum==5 ){
              self.threedwFirstThreeStatus=true;
            }else{
              self.threedwFirstThreeStatus=false;
            }
          }else if(self.categorySelect=='fszh'){
            let regSelectNums = {w:0,q:0,b:0,s:0,g:0};
            for(let key in self.fszhList){

              self.fszhList[key].forEach(val=>{
                if(val.check && val.choose){
                  regSelectNums[val.key]=regSelectNums[val.key]+1;
                }
              })

            }
            for(let key in regSelectNums){
              if(regSelectNums[key]==7){
                self.fszhFirstStatus[key] = true;
              }else{
                self.fszhFirstStatus[key] = false;
              }
            }

          }else if(self.categorySelect=='zxsix'){
            let index = 0;
            for(let key in self.zxthreeList){
              self.zxthreeList[key].forEach(val=>{
                if(val.check){
                  index++;
                }
              })
            }
            if(index==8){
              self.zxsixStatus = true;
            }else{
              self.zxsixStatus = false;
            }
          }

          // this.$refs.betButton.focus();
          // document.getElementById('betButton').focus();
        },
        findSpecial(item){


        return this.specialLists.findIndex((value, index, arr) => {
          return item.oddsKey == value.oddsKey;


        });
      },
      allSearchFun(key,index){
        let self = this;
        if(!this.betState){
          return;
        }
        self.allSearchList[index] = !self.allSearchList[index];
        if(self.allSearchList[index]){
          self.sscOdds[key].forEach(val=>{
            if(val.categoryKey!='lm' && val.status=='0'){
              if(!val.check && !val.choose){
                self.selectOdds(val);
              }
            }
          })
        }else{
          self.sscOdds[key].forEach(val=>{
            if(val.categoryKey!='lm' && val.status=='0'){
              self.cancelChooseOdds(val);
            }
          })
        }
      },
        initSelectList(flag){
          let self = this;


          setTimeout(()=>{
            if (flag) {
              //this.$refs.kjPage.closeAllTime();
              this.$refs.kjPage.infoObtain();
              self.$refs.statisticsPage.init();
            }
          },500)

          self.allSearchList=[false,false,false,false,false];
          self.twozhSelect = 'top3';
          self.twozhChanges(self.twozhSelect);
          /*self.twodwInitialization();
          self.threedwInitialization();
          self.zxthreeInitialization();
          self.fszhInitialization();
          self.kdInitialization();*/
          self.setSelectList(null);
          if(!this.betAmtStatus){
            this.pagePresetAmt = '';
            this.setBetAmtPreSet('');
          }
          self.specialListCancel();
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
            this.self.setPromptInformation('停用成功!').then(

            ).catch(error => {

            });
          }
        }).catch(error=>{

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
      quickSum() {
        this.$emit('quickSum');
      },
      resetBetAmt() {
        let self = this;
        self.pagePresetStatus=false;
        self.pagePresetAmt = '';
        self.setBetAmtPreSet('');
        self.setBetAmtStatus(false);
        self.initSelectList(true);
        self.specialListCancel();
      },
      bet() {
        let self = this;
        if(self.categorySelect=='twodw'){
          let arr = [];
          let betObj = self.sscOdds.twozdw.find((val) => {
            if (val.oddsKey === self.twodwSelect) {
              return val;
            }
          });
          betObj = JSON.parse(JSON.stringify(betObj));
          if(self.twodwSelectMode=='firstKind'){

            for(let i = 0;i<self.twodwFirstKindList[0].list.length;i++){
              for(let j = 0;j<self.twodwFirstKindList[1].list.length;j++){
                if(self.twodwFirstKindList[0].list[i].check && self.twodwFirstKindList[1].list[j].check){
                  let betContent = [];
                  betContent.push(self.twodwFirstKindList[0].list[i].oddsKey);
                  betContent.push(self.twodwFirstKindList[1].list[j].oddsKey);
                  self.$set(betObj,"betContent",betContent.toString());
                  self.$set(betObj,"choose",true);
                  self.$set(betObj,"check",true);
                  self.$set(betObj,"betAmt",self.pagePresetAmt);
                  if(betObj.betAmt=="" || betObj.betAmt==0){
                    return self.setPromptInformation('请输入下注金额!');
                  }
                  arr.push(JSON.parse(JSON.stringify(betObj)));
                  self.$delete(betObj, 'betContent');
                }
              }
            }
            self.setSelectListFast(arr);

          }else{
            for(let key in self.twodwSecondKindsList){
              self.twodwSecondKindsList[key].list.forEach(obj=>{
                if(obj.check){
                  self.$set(betObj,"betContent",obj.oddsKey.substring(0,1)+','+obj.oddsKey.substring(1,2));
                  self.$set(betObj,"choose",true);
                  self.$set(betObj,"check",true);
                  self.$set(betObj,"betAmt",self.pagePresetAmt);
                  if(betObj.betAmt=="" || betObj.betAmt==0){
                    return self.setPromptInformation('请输入下注金额!');
                  }
                  arr.push(JSON.parse(JSON.stringify(betObj)));
                  self.$delete(betObj, 'betContent');
                }
              });
            }
            self.setSelectListFast(arr);
          }
        }else if(self.categorySelect=='threedw'){
          let arr = [];
          let betObj = self.sscOdds.threezdw.find((val) => {
            if (val.oddsKey === self.threedwSelect) {
              return val;
            }
          });
          betObj = JSON.parse(JSON.stringify(betObj));
          if(self.threedwSelectMode=='firstKind'){

            for(let i = 0;i<self.threedwFirstKindList[0].list.length;i++){
              for(let j = 0;j<self.threedwFirstKindList[1].list.length;j++){
                for(let k = 0;k<self.threedwFirstKindList[2].list.length;k++) {
                  if (self.threedwFirstKindList[0].list[i].check && self.threedwFirstKindList[1].list[j].check
                    && self.threedwFirstKindList[2].list[k].check) {
                    let betContent = [];
                    betContent.push(self.threedwFirstKindList[0].list[i].oddsKey);
                    betContent.push(self.threedwFirstKindList[1].list[j].oddsKey);
                    betContent.push(self.threedwFirstKindList[2].list[k].oddsKey);
                    self.$set(betObj, "betContent", betContent.toString());
                    self.$set(betObj, "choose", true);
                    self.$set(betObj, "check", true);
                    self.$set(betObj, "betAmt", self.pagePresetAmt);
                    if(betObj.betAmt=="" || betObj.betAmt==0){
                      return self.setPromptInformation('请输入下注金额!');
                    }
                    arr.push(JSON.parse(JSON.stringify(betObj)));
                    self.$delete(betObj, 'betContent');
                  }
                }
              }
            }
            self.setSelectListFast(arr);

          }else{

            for(let key in self.threedwScreenList){
              self.threedwScreenList[key].list.forEach(obj=>{

                if(obj.check){

                  self.$set(betObj,"betContent",obj.oddsKey.substring(0,1)+','+obj.oddsKey.substring(1,2)+','+obj.oddsKey.substring(2,3));
                  self.$set(betObj,"choose",true);
                  self.$set(betObj,"check",true);
                  self.$set(betObj,"betAmt",self.pagePresetAmt);
                  if(betObj.betAmt=="" || betObj.betAmt==0){
                    return self.setPromptInformation('请输入下注金额!');
                  }
                  arr.push(JSON.parse(JSON.stringify(betObj)));
                  self.$delete(betObj, 'betContent');
                }
              });
            }
            self.setSelectListFast(arr);
          }
        }else if(self.categorySelect=='zxsix'){
          let arr = [];
          for(let key in self.zxthreeList){
            self.zxthreeList[key].forEach(val=>{
              if(val.check){
                arr.push(val.oddsKey);
              }
            });
          }
          if(arr.length<4){
            return self.setPromptInformation('组选三最少勾选4个号，最多8个!');
          }
          if(!self.pagePresetAmt){
            return self.setPromptInformation('请输入下注金额!');
          }
          let betObj = self.sscOdds.zxl['zx6'+self.zxsixSelect].find(val=>{
            return val.oddsKey==arr.length;
          });
          for(let i =0;i<arr.length-1;i++){
            for(let j =0;j<arr.length-1-i;j++){
              if(arr[j]>arr[j+1]){
                let temp = arr[j];
                arr[j] =arr[j+1];
                arr[j+1] = temp;
              }
            }
          }
          this.$set(betObj,'betContent',arr.toString());
          this.$set(betObj,'betAmt',self.pagePresetAmt);
          self.chooseOdds(betObj);
        }else if(self.categorySelect=='zxthree'){
          let arr = [];
          for(let key in self.zxthreeList){
            self.zxthreeList[key].forEach(val=>{
              if(val.check){
                arr.push(val.oddsKey);
              }
            });
          }
          if(arr.length<5){
            return self.setPromptInformation('组选三最少最中5个号!');
          }
          if(!self.pagePresetAmt){
            return self.setPromptInformation('请输入下注金额!');
          }
          let betObj = self.sscOdds.zxs['zx3'+self.zxthreeSelect].find(val=>{
            return val.oddsKey==arr.length;
          });
          for(let i =0;i<arr.length-1;i++){
            for(let j =0;j<arr.length-1-i;j++){
              if(arr[j]>arr[j+1]){
                let temp = arr[j];
                arr[j] =arr[j+1];
                arr[j+1] = temp;
              }
            }
          }
          this.$set(betObj,'betContent',arr.toString());
          this.$set(betObj,'betAmt',self.pagePresetAmt);
          self.chooseOdds(betObj);
        }else if(self.categorySelect=='fszh'){
          let firstPlaceList = [];
          let secondPlaceList = [];
          let thirdPlaceList = [];
          let indexKey = [];
          if(self.fszhSelect=='top3'){
            indexKey = ['w','q','b'];
          }else if(self.fszhSelect=='mid3'){
            indexKey = ['q','b','s'];
          }else if(self.fszhSelect=='end3'){
            indexKey = ['b','s','g'];
          }
          if(!self.pagePresetAmt){
            return self.setPromptInformation('请输入下注金额!');
          }
          for(let key in self.fszhList){
            self.fszhList[key].forEach(val=>{
              if(val.check){
                for(let i = 0;i<indexKey.length;i++){
                  if(val.key==indexKey[i] && i==0){
                    firstPlaceList.push(val.oddsKey);
                  }else if(val.key==indexKey[i] && i==1){
                    secondPlaceList.push(val.oddsKey);
                  }else if(val.key==indexKey[i] && i==2){
                    thirdPlaceList.push(val.oddsKey);
                  }
                }
              }
            });
          }
          let totalLength = firstPlaceList.length + secondPlaceList.length + thirdPlaceList.length;
          if (firstPlaceList.length > 0 && secondPlaceList.length > 0 && thirdPlaceList.length > 0 && totalLength >= 9 && totalLength <= 21) {
            let arr = [];
            let betObj = this.sscOdds.fszh.find((val) => {
              if (val.oddsKey === this.fszhSelect) {
                return val;
              }
            });
            betObj = JSON.parse(JSON.stringify(betObj));
            for (let i = 0; i < firstPlaceList.length; i++) {
              for (let j = 0; j < secondPlaceList.length; j++) {
                for (let k = 0; k < thirdPlaceList.length; k++) {
                  let betContent = [];
                  betContent.push(firstPlaceList[i]);
                  betContent.push(secondPlaceList[j]);
                  betContent.push(thirdPlaceList[k]);
                  this.$set(betObj, 'betContent', betContent.toString());
                  this.$set(betObj,'betAmt',self.pagePresetAmt);
                  this.$set(betObj, 'choose', true);
                  this.$set(betObj, 'check', true);
                  arr.push(JSON.parse(JSON.stringify(betObj)));
                  self.$delete(betObj, 'betContent');
                }

              }
            }
            self.setSelectListFast(arr);
          }else{
            return self.setPromptInformation('最少勾选9个，最多21个。并且每一位至少勾选一个！');
          }
        }
        if(this.pageQuickBetStatus && !this.pagePresetAmt && this.selectList.length>0){
          this.setPromptInformation('请输入金额!');
          return;
        }
        this.$emit('bet');
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
      closeMarketFun(){
        if(!this.betAmtStatus){
          this.pagePresetAmt = '';
          this.setBetAmtPreSet('');
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
      specialListCancel(){
        let self = this;
        for(let key in self.zxthreeList){
          self.zxthreeList[key].forEach(val=>{
            self.$delete(val,"check");
            self.$delete(val,"choose");
          })
        }
        for(let key in self.fszhList){
          self.fszhList[key].forEach(val=>{
            self.$delete(val,"check");
            self.$delete(val,"choose");
          })
        }
        for(let key in self.twodwFirstKindList){
          self.twodwFirstKindList[key].list.forEach(val=>{
            self.$delete(val,"check");
            self.$delete(val,"choose");
          })
        }
        for (let key in self.twodwSecondKindsList) {
          for (let i = 0; i < self.twodwSecondKindsList[key].list.length; i++) {
            self.$delete(self.twodwSecondKindsList[key].list[i], "choose");
            self.$delete(self.twodwSecondKindsList[key].list[i], "check");
          }
        }

        for(let key in self.threedwFirstKindList){
          self.threedwFirstKindList[key].list.forEach(val=>{
            self.$delete(val,"check");
            self.$delete(val,"choose");
          })
        }

        for (let key in self.threedwScreenList) {
          for (let i = 0; i < self.threedwScreenList[key].list.length; i++) {
            self.$delete(self.threedwScreenList[key].list[i], "choose");
            self.$delete(self.threedwScreenList[key].list[i], "check");
          }
        }
        self.sscOdds.threezzh['3z'+self.threezhSelect].forEach(obj=>{
          self.$delete(obj, "choose");
          self.$delete(obj, "check");
        })
        self.specialCheckBoxList = {};
        self.fsNum.odd = false;
        self.fsNum.even = false;
        self.doubleNum.take = false;
        self.doubleNum.except = false;
        self.secNum.odd = false;
        self.secNum.even = false;
        self.brotherNum.take = false;
        self.brotherNum.except = false;
        self.fsNumTake = '';
        self.secNumTake = '';
        self.numberTake = '';
        self.numberExcept = '';


        self.threeNumberTake = '';
        self.threeNumberExcept = '';
        self.threeFsNumTake = '';
        self.threeSecNumTake = '';
        self.threeTrdNumTake = '';
        self.threeFsNum.odd = false;
        self.threeFsNum.even = false;
        self.threeDoubleNum.take = false;
        self.threeDoubleNum.except = false;
        self.threeRepeatNum.take = false;
        self.threeRepeatNum.except = false;
        self.threeSecNum.odd = false;
        self.threeSecNum.even = false;
        self.threeTwoBrotherNum.take = false;
        self.threeTwoBrotherNum.except = false;
        self.threeTrdNum.odd = false;
        self.threeTrdNum.even = false;
        self.threeThreeBrotherNum.take = false;
        self.threeThreeBrotherNum.except = false;
        self.twodwFirstOneStatus=false;
        self.twodwFirstTwoStatus=false;
        self.threedwFirstOneStatus=false;
        self.threedwFirstTwoStatus=false;
        self.threedwFirstThreeStatus=false;
        self.zxsixStatus = false;
        self.fszhFirstStatus = {'w':false,'q':false,'b':false,'s':false,'g':false};
        self.threedwScreenList = {};

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
      },
      mounted() {
        let self = this;
        self.setCategorySelect('nnp');
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
      created() {
      }
    }
</script>

<style scoped>
  .oddsSelect {
    background-color: #fff100 !important;
  }

  .quick_sec_table_fw td {
    border: 0px;
    text-align: left;
  }

  .quick_sec_table_fw td.t {
    width: 80px;
    padding-left: 5px;
  }

  .quick_sec_table_fw .text {
    border: #B9C2CB 1px solid;
    padding: 0 2px;
    width: 48px;
    height: 18px;
  }

  .bet_panel td {
    height: 28px;
    line-height: 28px;
  }

</style>
