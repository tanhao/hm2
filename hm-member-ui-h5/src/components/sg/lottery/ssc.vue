<template>
  <div class="gm_main">
    <div class="home">
      <div class="sc-bZQynM OQRyf">
        <my-header @clearSpecialSelect="clearSpecialSelect"  total="true"></my-header>
         <div class="sc-bdVaJa jaFIbq">
          <my-suyin @showShuyin="updateHigh"></my-suyin>
          <my-kj @clearSpecialSelect="initAllOddsList" ref="resetTime"></my-kj>
          <!--主布局开始-->
          <div :class="foldFunction" >
            <!--左边-->
            <div class="sc-dnqmqq bVspgC">
              <div class="left-menu-list-view">
                <template v-for="item in leftMenu">
                  <a @click="selectActiveType(item.value)" :class="activeType===item.value?'active':''" class="left-menu-list-item">{{item.title}}</a>
                </template>
              </div>
              <!--注单-->
              <!--<my-bet @showBet="showBetFunction"></my-bet>-->
            </div>
            <!--右边下注区域-->
            <div class="sc-htoDjs bTnXwf ssc">
              <div :class="activeType==='nnp'?'tab tab-active':'tab'">
                <template v-for="(list, title) in sscOdds.lm">
                  <div class="bet-panel-wrapper wrap">
                    <div class="tz_box">
                      <div class="bet-panel-header">
                        <img id="bgs" class="toggle" :src="!list.showHide?require('../../images/shang.png'):require('../../images/xia.png')"  >
                        <div class="title">{{$t(title)}}</div>
                      </div>
                      <ul class="cs1" style="display:block;" v-show="!list.showHide">
                        <li v-for="(cell, key) in list" :key="key">
                          <div :class="!sscOdds.lm[title][key].choose?'wf_box':'wf_box bcn_back'"
                               v-tap="(e)=>selectOdds(sscOdds.lm[title][key],e)"> <!--选中加bcn_back-->
                            <div class="wf_info">
                              <input class="chk" type="checkbox" style="display: none;">
                              <span class="qiu">{{$t(cell.playKey+'_'+cell.oddsKey)}}</span>
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
              <!--1~5球-->
              <div :class="activeType==='ball'?'tab tab-active':'tab'" >
                <template v-for="(item,index) in qiuydwList">
                  <div class="bet-panel-wrapper wrap">
                    <div class="tz_box sptz_box">
                      <div class="bet-panel-header">
                        <img id="bgs" class="toggle" :src="item.showHide?require('../../images/shang.png'):require('../../images/xia.png')" >
                        <div class="title">{{$t('ssc'+item.no)}}</div>
                      </div>
                      <ul v-show="item.showHide">
                        <template v-for="(list,j) in sscOdds['qiu'+item.no]">
                          <li >
                            <div :class="!sscOdds['qiu'+item.no][j].choose?'wf_box':'wf_box bcn_back'"
                                 v-tap="(e)=>selectOdds(sscOdds['qiu'+item.no][j],e)">
                              <div class="wf_info">
                                <input class="chk" type="checkbox" style="display: none;">
                                <span class="qiu"><em :class="'n_'+list.oddsKey">{{$t(list.oddsKey.toUpperCase())}}</em></span>
                                <span class="odds">{{betState&&list.status=='0'?oddsReturn(list):'封盘'}}</span>
                              </div>
                              <div class="inp"><input type="text" maxlength="7"></div>
                            </div>
                          </li>
                        </template>
                        <li >
                          <div :class="!allSearchList[index]?'wf_box':'wf_box bcn_back'"
                               @click="allSearchFun('qiu'+item.no,index)">
                            <div class="wf_info">
                              <input class="chk" type="checkbox" style="display: none;">
                              <span class="qiu">0~9</span>
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

              <!--一字组合-->
              <div :class="activeType==='onezh'?'tab tab-active':'tab'">
                <template>
                  <div class="bet-panel-wrapper wrap">
                    <div class="tz_box sptz_box">
                      <div class="bet-panel-header">
                        <img id="bgs" class="toggle" :src="onezzhShowHide.top3?require('../../images/shang.png'):require('../../images/xia.png')" >
                        <div class="title">一字组合(前三)</div>
                      </div>
                      <ul v-show="onezzhShowHide.top3">
                        <template v-for="(list,i) in sscOdds.onezzh">
                          <li v-if="list.categoryKey==='top3'">
                          <div :class="!sscOdds.onezzh[i].choose?'wf_box':'wf_box bcn_back'"
                               v-tap="(e)=>selectOdds(sscOdds.onezzh[i],e)">
                            <div class="wf_info">
                              <input class="chk" type="checkbox" style="display: none;">
                              <span class="qiu"><em class="n_0">{{$t(list.oddsKey.toUpperCase())}}</em></span>
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
                </template>
                <template>
                  <div class="bet-panel-wrapper wrap">
                    <div class="tz_box sptz_box">
                      <div class="bet-panel-header">
                        <img id="bgs" class="toggle" :src="onezzhShowHide.mind3?require('../../images/shang.png'):require('../../images/xia.png')" >
                        <div class="title">一字组合(中三)</div>
                      </div>
                      <ul v-show="onezzhShowHide.mind3">
                        <template v-for="(list,i) in sscOdds.onezzh">
                          <li v-if="list.categoryKey==='mid3'">
                            <div :class="!sscOdds.onezzh[i].choose?'wf_box':'wf_box bcn_back'"
                                 v-tap="(e)=>selectOdds(sscOdds.onezzh[i],e)">
                              <div class="wf_info">
                                <input class="chk" type="checkbox" style="display: none;">
                                <span class="qiu"><em class="n_0">{{$t(list.oddsKey.toUpperCase())}}</em></span>
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
                </template>
                <template>
                  <div class="bet-panel-wrapper wrap">
                    <div class="tz_box sptz_box">
                      <div class="bet-panel-header">
                        <img id="bgs" class="toggle" :src="onezzhShowHide.end3?require('../../images/shang.png'):require('../../images/xia.png')" >
                        <div class="title">一字组合(后三)</div>
                      </div>
                      <ul v-show="onezzhShowHide.end3">
                        <template v-for="(list,i) in sscOdds.onezzh">
                          <li v-if="list.categoryKey==='end3'">
                            <div :class="!sscOdds.onezzh[i].choose?'wf_box':'wf_box bcn_back'"
                                 v-tap="(e)=>selectOdds(sscOdds.onezzh[i],e)">
                              <div class="wf_info">
                                <input class="chk" type="checkbox" style="display: none;">
                                <span class="qiu"><em class="n_0">{{$t(list.oddsKey.toUpperCase())}}</em></span>
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
                </template>
                <template>
                  <div class="bet-panel-wrapper wrap">
                    <div class="tz_box sptz_box">
                      <div class="bet-panel-header">
                        <img id="bgs" class="toggle" :src="onezzhShowHide.all?require('../../images/shang.png'):require('../../images/xia.png')" >
                        <div class="title">一字组合(全五)</div>
                      </div>
                      <ul v-show="onezzhShowHide.all">
                        <template v-for="(list,i) in sscOdds.onezzh">
                          <li v-if="list.categoryKey==='all5'">
                            <div :class="!sscOdds.onezzh[i].choose?'wf_box':'wf_box bcn_back'"
                                 v-tap="(e)=>selectOdds(sscOdds.onezzh[i],e)">
                              <div class="wf_info">
                                <input class="chk" type="checkbox" style="display: none;">
                                <span class="qiu"><em class="n_0">{{$t(list.oddsKey.toUpperCase())}}</em></span>
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
                </template>
              </div>
              <!--二字组合-->
              <div :class="activeType==='twozh'?'tab tab-active':'tab'">
                <template>
                  <select class="setlist" v-model="twozzhSelect" @change="twozzhChange">
                    <option value="2ztop3">二字前三</option>
                    <option value="2zmid3">二字中三</option>
                    <option value="2zend3">二字后三</option>
                  </select>
                  <div class="bet-panel-wrapper wrap pd43">
                    <div class="rough_lines"></div>
                    <div class="tz_box ssc sptz_box">
                      <ul>
                        <template v-for="(list,index) in twozzhList">
                          <li >
                            <div :class="!list.choose?'wf_box':'wf_box bcn_back'"
                                 @click="selectOdds(list)">
                              <div class="wf_info">
                                <input class="chk" type="checkbox" style="display: none;">
                                <span class="qiu">{{list.oddsKey.at(0)}}{{list.oddsKey.at(1)}}</span>
                                <span class="odds">{{betState&&list.status=='0'?oddsReturn(list):'封盘'}}</span>
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
              <!--三字组合-->
              <div :class="activeType==='threezh'?'tab tab-active':'tab'">
              <template>
                <select class="setlist" v-model="threezzhSelect" @change="threezzhChange">
                  <option value="3ztop3">三字前三</option>
                  <option value="3zmid3">三字中三</option>
                  <option value="3zend3">三字后三</option>
                </select>
                <div class="rough_lines"></div>
                <div class="rough_lines"></div>
                <div class="rough_lines"></div>
                <div class="rough_lines"></div>
                <div class="rough_lines"></div>
                <div class="rough_lines"></div>
                <div class="rough_lines"></div>
                <div class="rough_lines"></div>

                <template v-for="(list,index) in threezzhList">

                  <div class="bet-panel-wrapper wrap">
                    <div class="tz_box ssc sptz_box">
                      <div class="bet-panel-header">
                        <img id="bgs" class="toggle" :src="!list.showHide?require('../../images/shang.png'):require('../../images/xia.png')"  >
                        <div class="title">{{$t(index)}}</div>
                      </div>
                      <ul class="cs1" style="display:block;" v-show="!list.showHide">
                        <li v-for="(cell, key) in list" :key="key">
                          <div :class="!cell.choose?'wf_box':'wf_box bcn_back'"
                               @click="selectOdds(cell)"> <!--选中加bcn_back-->
                            <div class="wf_info">
                              <input class="chk" type="checkbox" style="display: none;">
                              <span class="qiu">{{cell.oddsKey.at(0)}}{{cell.oddsKey.at(1)}}{{cell.oddsKey.at(2)}}</span>
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
              </template>
            </div>

              <!--二字定位-->
              <div :class="activeType==='twodw'?'tab tab-active':'tab'">
                <template>
                  <div class="ssctop2z"><span>赔率：</span><span>
                    {{twodwOddsResult}}
                  </span></div>
                  <select class="setlist "  v-model="twodwSelect" @change="initSpecialChoose('twodw',twodwSelect)" style="width: 70px; position:absolute;">
                    <template v-for="(list,index) in sscOdds.twozdw">
                      <option :value="list.oddsKey">{{$t(list.oddsKey)}}</option>
                    </template>
                  </select>
                  <div class="bet-panel-wrapper wrap pd43">
                    <div class="tz_box ssc">
                      <ul >
                        <div class="bet-panel-header">{{$t(twodwSelect)}}</div>
                        <li class="sscfont">{{$t(twodwSelect.substring(0,1))}}</li>
                        <li class="sscfont">{{$t(twodwSelect.substring(1,2))}}</li>
                        <template v-for="(num,i) in 10">
                          <template v-if="item.number == i" v-for="(item,index) in twodwLists1">
                            <li >
                              <div :class="!item.choose?'wf_box':'wf_box bcn_back'"
                                   @click="specialSelect(item)">
                                <div class="wf_info">
                                  <input class="chk" type="checkbox" style="display: none;">
                                  <span class="qiu"><em :class="'n_'+item.number">{{item.number}}</em></span>

                                  <span class="odds"></span>
                                </div>
                                <div class="inp"><input type="text" maxlength="7"></div>
                              </div>
                            </li>
                            <template v-if="item.number == i" v-for="(item,index) in twodwLists2">
                              <li >
                                <div :class="!item.choose?'wf_box':'wf_box bcn_back'"
                                     @click="specialSelect(item)">
                                  <div class="wf_info">
                                    <input class="chk" type="checkbox" style="display: none;">
                                    <span class="qiu"><em :class="'n_'+item.number">{{item.number}}</em></span>

                                    <span class="odds"></span>
                                  </div>
                                  <div class="inp"><input type="text" maxlength="7"></div>
                                </div>
                              </li>

                            </template>
                          </template>
                        </template>
                      </ul>
                    </div>
                  </div>
                </template>
              </div>
              <!--三字定位-->
              <div :class="activeType==='threedw'?'tab tab-active':'tab'">

                <template>
                  <div class="ssctop2z"><span>赔率：</span><span>
                    {{threedwOddsResult}}
                  </span></div>
                  <select class="setlist"  v-model="threedwSelect" @change="initSpecialChoose('threedw',threedwSelect)" style="width: 100px;position:absolute;">
                    <template v-for="(list,index) in sscOdds.threezdw">
                      <option :value="list.oddsKey">{{$t('3zdw'+list.oddsKey)}}</option>
                    </template>
                  </select>
                  <div class="bet-panel-wrapper wrap pd43">
                    <div class="tz_box ssc sptz_box">
                      <ul>
                        <div class="bet-panel-header">{{$t('3zdw'+threedwSelect)}}</div>
                        <li class="sscfont">{{$t(threedwTitleResult[0])}}</li>
                        <li class="sscfont">{{$t(threedwTitleResult[1])}}</li>
                        <li class="sscfont">{{$t(threedwTitleResult[2])}}</li>
                        <template v-for="(num,i) in 10">
                          <template v-if="item.number == i" v-for="(item,index) in threedwLists1">
                            <li >
                              <div :class="!item.choose?'wf_box':'wf_box bcn_back'"
                                   @click="specialSelect(item)">
                                <div class="wf_info">
                                  <input class="chk" type="checkbox" style="display: none;">
                                  <span class="qiu"><em :class="'n_'+item.number">{{item.number}}</em></span>

                                  <span class="odds"></span>
                                </div>
                                <div class="inp"><input type="text" maxlength="7"></div>
                              </div>
                            </li>
                          </template>
                          <template v-if="item.number == i" v-for="(item,index) in threedwLists2">
                            <li >
                              <div :class="!item.choose?'wf_box':'wf_box bcn_back'"
                                   @click="specialSelect(item)">
                                <div class="wf_info">
                                  <input class="chk" type="checkbox" style="display: none;">
                                  <span class="qiu"><em :class="'n_'+item.number">{{item.number}}</em></span>

                                  <span class="odds"></span>
                                </div>
                                <div class="inp"><input type="text" maxlength="7"></div>
                              </div>
                            </li>
                          </template>
                          <template v-if="item.number == i" v-for="(item,index) in threedwLists3">
                            <li >
                              <div :class="!item.choose?'wf_box':'wf_box bcn_back'"
                                   @click="specialSelect(item)">
                                <div class="wf_info">
                                  <input class="chk" type="checkbox" style="display: none;">
                                  <span class="qiu"><em :class="'n_'+item.number">{{item.number}}</em></span>

                                  <span class="odds"></span>
                                </div>
                                <div class="inp"><input type="text" maxlength="7"></div>
                              </div>
                            </li>
                          </template>
                        </template>
                      </ul>
                    </div>
                  </div>
                </template>
              </div>
              <!--二字和数-->
              <div :class="activeType==='twohs'?'tab tab-active':'tab'">
                  <select class="setlist" v-model="twohsSelect" @change="twohsChange">
                    <option value="wq">{{$t('wq')}}</option>
                    <option value="wb">{{$t('wb')}}</option>
                    <option value="ws">{{$t('ws')}}</option>
                    <option value="wg">{{$t('wg')}}</option>
                    <option value="qb">{{$t('qb')}}</option>
                    <option value="qs">{{$t('qs')}}</option>
                    <option value="qg">{{$t('qg')}}</option>
                    <option value="bs">{{$t('bs')}}</option>
                    <option value="bg">{{$t('bg')}}</option>
                    <option value="sg">{{$t('sg')}}</option>
                  </select>
                    <div class="bet-panel-wrapper wrap pd43">
                      <div class="rough_lines"></div>
                      {{$t(twohsSelect+'hs')}}
                      <div class="tz_box ssc sptz_box">
                        <ul>
                          <template v-for="(item,index) in twohsLists">
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
                <div class="bet-panel-wrapper wrap pd43">
                  <div class="rough_lines"></div>
                  {{$t(twohsSelect+'hws')}}
                  <div class="tz_box ssc sptz_box">
                    <ul>
                      <template v-for="(item,index) in twohwsLists">
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
              </div>
              <!--三字和数-->
              <div :class="activeType==='threehs'?'tab tab-active':'tab'">
                <select class="setlist" v-model="threehsSelect" @change="threehsChange">
                  <option value="top3">{{$t('top3')}}</option>
                  <option value="mid3">{{$t('mid3')}}</option>
                  <option value="end3">{{$t('end3')}}</option>
                </select>

                <div class="bet-panel-wrapper wrap pd43">

                  <div class="rough_lines"></div>
                  {{$t(threehsSelect)}}{{$t('hs')}}
                  <div class="tz_box ssc sptz_box">
                    <ul>
                      <template v-for="(item,index) in threehsLists">
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
                <div class="bet-panel-wrapper wrap pd43">

                  <div class="rough_lines"></div>
                  {{$t(threehsSelect)}}{{$t('hws')}}
                  <div class="tz_box ssc sptz_box">
                    <ul>
                      <template v-for="(item,index) in threehwsLists">
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
              </div>
              <!--组选三-->
              <div :class="activeType==='zxthree'?'tab tab-active':'tab'">
                <div class="ssctop2z"><span>赔率：</span><span>
                    {{betState?zxthreeOdds:'封盘'}}
                  </span></div>
                <select class="setlist" v-model="zxthreeSelect" @change="initSpecialChoose('zx',zxthreeSelect)" style="width: 115px;position:absolute;">
                  <option value="zx3top3">{{$t('zx3top3')}}</option>
                  <option value="zx3mid3">{{$t('zx3mid3')}}</option>
                  <option value="zx3end3">{{$t('zx3end3')}}</option>
                </select>
                <div class="bet-panel-wrapper wrap pd43">
              <div class="tz_box ssc sptz_box">
                <ul>
                  <div class="bet-panel-header">{{$t(zxthreeSelect)}}</div>
                  <template v-for="(item,index) in specialLists">
                    <li ><!--:style="index==specialLists.length-1?'margin-left:33%;':''"-->
                      <div :class="!item.choose?'wf_box':'wf_box bcn_back'"
                           @click="specialSelect(item)">
                        <div class="wf_info">
                          <input class="chk" type="checkbox" style="display: none;">
                          <span class="qiu"><em :class="'n_'+item.number">{{item.number}}</em></span>

                          <span class="odds"></span>
                        </div>
                        <div class="inp"><input type="text" maxlength="7"></div>
                      </div>
                    </li>
                  </template>
                </ul>
              </div>
            </div>
            </div>
              <!--组选六-->
              <div :class="activeType==='zxsix'?'tab tab-active':'tab'">
                <div class="ssctop2z"><span>赔率：</span><span>
                    {{betState?zxsixOdds:'封盘'}}
                  </span></div>
                <select class="setlist" v-model="zxsixSelect" @change="initSpecialChoose('zx',zxsixSelect)" style="width: 115px;position:absolute;">
                  <option value="zx6top3">{{$t('zx6top3')}}</option>
                  <option value="zx6mid3">{{$t('zx6mid3')}}</option>
                  <option value="zx6end3">{{$t('zx6end3')}}</option>
                </select>
                <div class="bet-panel-wrapper wrap pd43">
                  <div class="tz_box ssc sptz_box">
                    <ul>
                      <div class="bet-panel-header">{{$t(zxsixSelect)}}</div>
                      <template v-for="(item,index) in specialLists">
                        <li >
                          <div :class="!item.choose?'wf_box':'wf_box bcn_back'"
                               @click="specialSelect(item)">
                            <div class="wf_info">
                              <input class="chk" type="checkbox" style="display: none;">
                              <span class="qiu"><em :class="'n_'+item.number">{{item.number}}</em></span>

                              <span class="odds"></span>
                            </div>
                            <div class="inp"><input type="text" maxlength="7"></div>
                          </div>
                        </li>
                      </template>
                    </ul>
                  </div>
                </div>
              </div>
              <!--复式组合-->
              <div :class="activeType==='fszh'?'tab tab-active':'tab'">

                <div class="ssctop2z"><span>赔率：</span><span>
                    {{fsOddsResult}}
                  </span></div>
                <select class="setlist" v-model="fszhSelect" @change="initSpecialChoose('fszh',fszhSelect)" style="width: 100px;position:absolute;">
                  <template v-for="(item,index) in sscOdds.fszh">
                    <option :value="item.oddsKey">{{$t('fs'+item.oddsKey)}}</option>
                  </template>
                </select>
                  <div class="bet-panel-wrapper wrap pd43">
                    <div class="tz_box ssc sptz_box">
                      <ul>
                        <div class="bet-panel-header">{{$t('fs'+fszhSelect)}}</div>
                        <li class="sscfont">{{$t(fsTitleResult[0])}}</li>
                        <li class="sscfont">{{$t(fsTitleResult[1])}}</li>
                        <li class="sscfont">{{$t(fsTitleResult[2])}}</li>
                        <template v-for="(num,i) in 10">
                          <template v-if="item.number == i" v-for="(item,index) in fsLists1">
                            <li >
                              <div :class="!item.choose?'wf_box':'wf_box bcn_back'"
                                   @click="specialSelect(item)">
                                <div class="wf_info">
                                  <input class="chk" type="checkbox" style="display: none;">
                                  <span class="qiu"><em :class="'n_'+item.number">{{item.number}}</em></span>

                                  <span class="odds"></span>
                                </div>
                                <div class="inp"><input type="text" maxlength="7"></div>
                              </div>
                            </li>
                          </template>
                          <template v-if="item.number == i" v-for="(item,index) in fsLists2">
                            <li >
                              <div :class="!item.choose?'wf_box':'wf_box bcn_back'"
                                   @click="specialSelect(item)">
                                <div class="wf_info">
                                  <input class="chk" type="checkbox" style="display: none;">
                                  <span class="qiu"><em :class="'n_'+item.number">{{item.number}}</em></span>

                                  <span class="odds"></span>
                                </div>
                                <div class="inp"><input type="text" maxlength="7"></div>
                              </div>
                            </li>
                          </template>
                        <template v-if="item.number == i" v-for="(item,index) in fsLists3">
                          <li >
                            <div :class="!item.choose?'wf_box':'wf_box bcn_back'"
                                 @click="specialSelect(item)">
                              <div class="wf_info">
                                <input class="chk" type="checkbox" style="display: none;">
                                <span class="qiu"><em :class="'n_'+item.number">{{item.number}}</em></span>

                                <span class="odds"></span>
                              </div>
                              <div class="inp"><input type="text" maxlength="7"></div>
                            </div>
                          </li>
                        </template>
                        </template>
                      </ul>
                    </div>
                  </div>
              </div>
              <!--跨度-->
              <div :class="activeType==='kd'?'tab tab-active':'tab'">
                <select  class="setlist" v-model="kdSelect" @change="kdChange">
                  <option  value="kdtop3">{{$t('kdtop3')}}</option>
                  <option  value="kdmid3">{{$t('kdmid3')}}</option>
                  <option  value="kdend3">{{$t('kdend3')}}</option>
                </select>
                  <div class="bet-panel-wrapper wrap pd43">
                    <div class="rough_lines"></div>
                    <div class="tz_box ssc sptz_box">
                      <ul>
                        <template v-for="(item,index) in kdList">
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
              </div>
              <!--斗牛/梭哈-->
              <div :class="activeType==='ndsm'?'tab tab-active':'tab'">
                <select  class="setlist" v-model="ndsmSelect" @change="ndsmChange">
                    <option v-for="(item,index) in sscOdds.ndsm" :value="index">{{$t(index)}}</option>
                </select>
                <div class="bet-panel-wrapper wrap pd43">
                  <div class="rough_lines"></div>
                  <div class="tz_box ssc sptz_box">
                    <ul>
                      <template v-for="(item,index) in ndsmList">
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
              </div>
            </div>
          </div>
        </div>
      </div>
      <my-footer  v-show="selectList.length==0"></my-footer>
      <Myendbet @clearSpecialSelect="clearSpecialSelect"  @showBet="showBetFunction" v-show="selectList.length>0"></Myendbet>
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
  import Lottery from '@/axios/api-game.js'
  import axios from 'axios'
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
        linkStyle:'scgzVnrw dwVbnv',
        activeType: 'ball',
        lotteryPage:true,
        showBetBox: false,
        qiuydwList:[
          {'no':'1','showHide':true},
          {'no':'2','showHide':true},
          {'no':'3','showHide':true},
          {'no':'4','showHide':true},
          {'no':'5','showHide':true},
        ],
        onezzhShowHide:{'top3':true,'mind3':true,'end3':true,'all':true},
        twozzhList:[],
        twozzhSelect:'2ztop3',
        threezzhList:[],
        threezzhSelect:'3ztop3',
        twodwSelect:'wq',
        threedwSelect:'top3',
        twohsSelect:'wq',
        threehsSelect:'top3',
        zxthreeSelect:'zx3top3',
        zxsixSelect:'zx6top3',
        fszhSelect:'top3',
        kdSelect:'kdtop3',
        ndsmSelect:'nn',
        //二字定位前后的集合
        twodwLists1:[],
        twodwLists2:[],
        twodwPosition:{
          one:'w',
          two:'q'
        },
        //三字定位前中后的集合
        threedwLists1:[],
        threedwLists2:[],
        threedwLists3:[],
        threedwPosition:{
          one:'w',
          two:'q',
          three:'b'
        },
        //复式组合前中后集合
        fsLists1:[],
        fsLists2:[],
        fsLists3:[],
        fsPosition:{
          one:'w',
          two:'q',
          three:'b'
        },
        ndsmList:[],
        kdList:[],
        twohsLists:[],
        twohwsLists:[],
        threehsLists:[],
        threehwsLists:[],
        //特殊玩法的集合(组选系列，定位系列)
        specialLists:[],
        specialChoose:[],
        showBetList:false,
        foldis:true,
        leftMenu: [
          {value: 'ball', title:'全部'},
          {value: 'nnp', title:'两面'},

          {value: 'onezh', title:'一字组合'},
          {value: 'twozh', title:'二字组合'},
          {value: 'threezh', title:'三字组合'},
          {value: 'twodw', title:'二字定位'},
          {value: 'threedw', title:'三字定位'},
          {value: 'twohs', title:'二字和数'},
          {value: 'threehs', title:'三字和数'},
          {value: 'zxthree', title:'组选三'},
          {value: 'zxsix', title:'组选六'},
          {value: 'fszh', title:'复式组合'},
          {value: 'kd', title:'跨度'},
          {value: 'ndsm', title:'斗牛/梭哈'},
        ],
        allSearchList:[false,false,false,false,false]
      }
    },
    beforeUpdate(){

    },
    watch:{
      playType(){
        let self = this;
        self.activeType = self.playType;
      }
    },
    computed: {
      ...mapGetters(['sscOdds','betState','betGameNo','noteLists','gameInfo','selectList','gameId','playType','userOddsCljps','userOddsCloses','userOddsJumps','userOddsNows','userOddss']),
      zxthreeOdds(){
        if(!this.betState){
          return '封盘';
        }
        if(!this.specialChoose){
          return;
        }
        if(!this.sscOdds){
          return;
        }
        if(!this.sscOdds.zxl){
          return;
        }
        let zxthreeNo = 0;
        let select = '';
        for(let i =0;i<this.specialChoose.length;i++){
          if(0==this.specialChoose[i].select.indexOf('zx3')){
            zxthreeNo++;
            select = this.specialChoose[i].select;
          }
        }
        if(zxthreeNo<5 ){
          return '- -';
        }
        for(let odd in this.sscOdds.zxs){
          for(let i = 0;i<this.sscOdds.zxs[odd].length;i++){
            if(zxthreeNo == this.sscOdds.zxs[odd][i].oddsKey && select == this.sscOdds.zxs[odd][i].playKey){

              return this.sscOdds.zxs[odd][i].status=='1'?'封盘':this.oddsReturn(this.sscOdds.zxs[odd][i]);
            }
          }
        }
      },
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
      zxsixOdds(){
        if(!this.betState){
          return '封盘';
        }
        let self = this;
        let zxsixNo = 0;
        let select = '';
        if(this.specialChoose.length>8){
          return;
        }
        if(!this.specialChoose){
          return;
        }
        if(!this.sscOdds){
          return;
        }
        if(!this.sscOdds.zxl){
          return;
        }
        for(let i =0;i<this.specialChoose.length;i++){
          if(0==this.specialChoose[i].select.indexOf('zx6')){
            zxsixNo++;
            select = this.specialChoose[i].select;
          }
        }
        if(zxsixNo<4 || zxsixNo>8){
          return '- -';
        }

        for(let odd in this.sscOdds.zxl){
          for(let item of this.sscOdds.zxl[odd]){
            if(zxsixNo == item.oddsKey && select == item.playKey){
              return item.status=='1'?'封盘':self.oddsReturn(item);
            }
          }
        }
      },
      threedwTitleResult(){
        let resultArr = [];
        if(this.threedwSelect=='top3'){
          resultArr.push('w','q','b');
        }else if(this.threedwSelect=='mid3'){
          resultArr.push('q','b','s');
        }else if(this.threedwSelect=='end3'){
          resultArr.push('b','s','g');
        }
        return resultArr;
      },
      fsTitleResult(){
        let resultArr = [];
        if(this.fszhSelect=='top3'){
          resultArr.push('w','q','b');
        }else if(this.fszhSelect=='mid3'){
          resultArr.push('q','b','s');
        }else if(this.fszhSelect=='end3'){
          resultArr.push('b','s','g');
        }
        return resultArr;
      },
      twodwOddsResult(){
        let self = this;

        for(let i = 0;i<self.sscOdds.twozdw.length;i++){
          if(self.betState && self.sscOdds.twozdw[i].status=='0' && self.sscOdds.twozdw[i].oddsKey==self.twodwSelect){
            return self.oddsReturn(self.sscOdds.twozdw[i]);
          }
        }
        return "封盘";
      },
      threedwOddsResult(){
        let self = this;
        for(let i = 0;i<self.sscOdds.threezdw.length;i++){
          if(self.betState && self.sscOdds.threezdw[i].status=='0' && self.sscOdds.threezdw[i].oddsKey==self.threedwSelect){
            return self.oddsReturn(self.sscOdds.threezdw[i]);
          }
        }
        return "封盘";
      },
      fsOddsResult(){
        let self = this;
        for(let i = 0;i<self.sscOdds.fszh.length;i++){
          if(self.betState && self.sscOdds.fszh[i].status=='0' && self.sscOdds.fszh[i].oddsKey==self.fszhSelect){
            return self.oddsReturn(self.sscOdds.fszh[i]);
          }
        }
        return "封盘";
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
      ...mapActions(['setNoteLists','setSocketResetStatus','setBetGameNo','setSelectListFast','setSelectList','setLotteryNow','setMarket','setCurrentGame','setPlayType']),
      showBetFunction(data){
        this.showBetList = data;
        if(!data){
          this.clearSpecialSelect(true);
        }

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
      specialSelect(item){
        let self = this;
        if(!this.betState){
          return;
        }
        if(this.activeType=='twodw') {
          let firstPlaceList = [];
          let secondPlaceList = [];
          self.specialChoose.forEach(val => {
            if (val.type === self.twodwSelect.at(0)) {
              firstPlaceList.push(Object.assign({}, val));
            } else if (val.type === self.twodwSelect.at(1)) {
              secondPlaceList.push(Object.assign({}, val));
            }
          })
          if(firstPlaceList.length==8 && item.type==self.twodwSelect.at(0) && !item.choose){
            return;
          }else if(secondPlaceList.length == 8  && item.type==self.twodwSelect.at(1) && !item.choose){
            return;
          }
        } else if(this.activeType=='threedw'){
          let firstPlaceList = [];
          let secondPlaceList = [];
          let thirdPlaceList = [];
          for (let i = 0; i < this.specialChoose.length; i++) {
            if (this.specialChoose[i].place === 'one') {
              firstPlaceList.push(Object.assign({},this.specialChoose[i]));
            } else if (this.specialChoose[i].place === 'two') {
              secondPlaceList.push(Object.assign({},this.specialChoose[i]));
            } else if (this.specialChoose[i].place === 'three') {
              thirdPlaceList.push(Object.assign({},this.specialChoose[i]));
            }
          }
          if(firstPlaceList.length==5 && item.place === 'one' && !item.choose){
            return;
          }else if(secondPlaceList.length == 5  && item.place === 'two' && !item.choose){
            return;
          }else if(thirdPlaceList.length == 5  && item.place === 'three' && !item.choose){
            return;
          }
        }
        if(this.activeType=='zxsix' && this.specialChoose.length==8 && 0==item.select.indexOf('zx6') && !item.choose){
          return;
        }
        if (this.activeType == 'fszh') {
          let firstPlaceList = [];
          let secondPlaceList = [];
          let thirdPlaceList = [];
          for (let i = 0; i < this.specialChoose.length; i++) {
            if (this.specialChoose[i].place === 'one') {
              firstPlaceList.push(Object.assign({}, this.specialChoose[i]));
            } else if (this.specialChoose[i].place === 'two') {
              secondPlaceList.push(Object.assign({}, this.specialChoose[i]));
            } else if (this.specialChoose[i].place === 'three') {
              thirdPlaceList.push(Object.assign({}, this.specialChoose[i]));
            }
          }
          if ((firstPlaceList.length >= 7 && item.place==='one' && !item.choose) || (secondPlaceList.length >= 7 && item.place==='two' && !item.choose) || (thirdPlaceList.length >= 7 && item.place==='three' && !item.choose)) {
            return;
          }
        }

        this.$set(item,'choose',!item.choose);
        if(item.choose){
          this.specialChoose.push(item);
        }else {
          let index = this.findSpecial(item);
          if(index!=-1){
            this.specialChoose.splice(index, 1);
          }
        }
        for(let i = 0;i<this.selectList.length;i++){
          if(this.selectList[i]){
            if ('3dw'===this.selectList[i].playKey ||'2dw'===this.selectList[i].playKey || 'fs'===this.selectList[i].playKey) {
              self.selectList.splice(i,1);
              i=-1;
            }
          }

        }

        if(this.activeType=='zxthree'){
          let obj = undefined;
          if(this.specialChoose.length>=5){
            obj = this.sscOdds.zxs[this.zxthreeSelect].find((value)=>{
              if(value.oddsKey === this.specialChoose.length.toString()){
                return value;
              }
            });
            this.selectList.findIndex((value,index,arr)=>{
              if(value.categoryKey==='zx3' && value.oddsKey != this.specialChoose.length.toString()){
                this.selectList.splice(index,1);
              }
            });
            let betContent = [];
            for(let i = 0;i<this.specialChoose.length;i++){
              betContent.push(this.specialChoose[i].number);
            }
            for(let i =0;i<betContent.length-1;i++){
              for(let j =0;j<betContent.length-1-i;j++){
                if(betContent[j]>betContent[j+1]){
                  let temp = betContent[j];
                  betContent[j] =betContent[j+1];
                  betContent[j+1] = temp;
                }
              }
            }
            this.$set(obj,'betContent',betContent.toString());
            this.chooseOdds(obj);
          }else{
            this.selectList.findIndex((value,index,arr)=>{
              if(value.categoryKey==='zx3' ){
                this.selectList.splice(index,1);
              }
            });
          }
        }else if(this.activeType=='zxsix'){

          let obj = undefined;
          if(this.specialChoose.length>=4 && this.specialChoose.length<=8){
            obj = this.sscOdds.zxl[this.zxsixSelect].find((value)=>{
              if(value.oddsKey === this.specialChoose.length.toString()){
                return value;
              }
            });
            this.selectList.findIndex((value,index,arr)=>{
              if(value.categoryKey==='zx6' && value.oddsKey != this.specialChoose.length.toString()){
                this.selectList.splice(index,1);
              }
            });
            let betContent = [];
            for(let i = 0;i<this.specialChoose.length;i++){
              betContent.push(this.specialChoose[i].number);
            }
            for(let i =0;i<betContent.length-1;i++){
              for(let j =0;j<betContent.length-1-i;j++){
                if(betContent[j]>betContent[j+1]){
                  let temp = betContent[j];
                  betContent[j] =betContent[j+1];
                  betContent[j+1] = temp;
                }
              }
            }
            this.$set(obj,'betContent',betContent.toString());
            this.chooseOdds(obj);
          }else{
            this.selectList.findIndex((value,index,arr)=>{
              if(value.categoryKey==='zx6' ){
                this.selectList.splice(index,1);
              }
            });
          }
        }else if(this.activeType=='twodw') {
          let firstPlaceList = [];
          let secondPlaceList = [];
          self.specialChoose.forEach(val=>{
            if (val.type === self.twodwSelect.at(0)) {
              firstPlaceList.push(Object.assign({},val));
            } else if (val.type === self.twodwSelect.at(1)) {
              secondPlaceList.push(Object.assign({},val));
            }
          })
          if (firstPlaceList.length > 0 && secondPlaceList.length > 0) {
            let betObj = self.sscOdds.twozdw.find((val) => {
              if (val.oddsKey === self.twodwSelect) {
                return val;
              }
            });
            let arr = [];

            for (let i = 0; i < firstPlaceList.length; i++) {
              for (let j = 0; j < secondPlaceList.length; j++) {
                betObj = JSON.parse(JSON.stringify(betObj));
                let betContent = [];
                betContent.push(firstPlaceList[i].number);
                betContent.push(secondPlaceList[j].number);
                self.$set(betObj, 'betContent', betContent.toString());
                if (self.betGameNo !== self.gameInfo.gameNo) {
                  self.setBetGameNo(self.gameInfo.gameNo);
                }
                self.$set(betObj, 'choose', true);
                self.$set(betObj, 'check', true);
                arr.push(JSON.parse(JSON.stringify(betObj)));
                self.$delete(betObj, 'betContent');
                //this.setSelectList(Object.assign({}, betObj));
              }
            }
            arr.forEach(val=>{
            })
            self.setSelectListFast(arr);


          }
          } else if (this.activeType == 'threedw') {
            let firstPlaceList = [];
            let secondPlaceList = [];
            let thirdPlaceList = [];
            for (let i = 0; i < this.specialChoose.length; i++) {
              if (this.specialChoose[i].place === 'one') {
                firstPlaceList.push(Object.assign({},this.specialChoose[i]));
              } else if (this.specialChoose[i].place === 'two') {
                secondPlaceList.push(Object.assign({},this.specialChoose[i]));
              } else if (this.specialChoose[i].place === 'three') {
                thirdPlaceList.push(Object.assign({},this.specialChoose[i]));
              }
            }

            if (firstPlaceList.length > 0 && secondPlaceList.length > 0 && thirdPlaceList.length > 0) {
              let betObj = this.sscOdds.threezdw.find((val) => {
                if (val.oddsKey === this.threedwSelect) {
                  return val;
                }
              });

              let arr = [];
              for (let i = 0; i < firstPlaceList.length; i++) {
                for (let j = 0; j < secondPlaceList.length; j++) {
                  for (let k = 0; k < thirdPlaceList.length; k++) {
                    betObj = JSON.parse(JSON.stringify(betObj));
                    let betContent = [];
                    betContent.push(firstPlaceList[i].number);
                    betContent.push(secondPlaceList[j].number);
                    betContent.push(thirdPlaceList[k].number);
                    this.$set(betObj, 'betContent', betContent.toString());
                    if (this.betGameNo !== this.gameInfo.gameNo) {
                      this.setBetGameNo(this.gameInfo.gameNo);
                    }
                    this.$set(betObj, 'choose', true);
                    this.$set(betObj, 'check', true);
                    arr.push(JSON.parse(JSON.stringify(betObj)));
                    //this.setSelectList(Object.assign({}, betObj));

                  }

                }
              }
              this.setSelectListFast(arr);
            }
          } else if (this.activeType == 'fszh') {
            let firstPlaceList = [];
            let secondPlaceList = [];
            let thirdPlaceList = [];
            for (let i = 0; i < this.specialChoose.length; i++) {
              if (this.specialChoose[i].place === 'one') {
                firstPlaceList.push(Object.assign({},this.specialChoose[i]));
              } else if (this.specialChoose[i].place === 'two') {
                secondPlaceList.push(Object.assign({},this.specialChoose[i]));
              } else if (this.specialChoose[i].place === 'three') {
                thirdPlaceList.push(Object.assign({},this.specialChoose[i]));
              }
            }

            let totalLength = firstPlaceList.length + secondPlaceList.length + thirdPlaceList.length;
            if (firstPlaceList.length > 0 && secondPlaceList.length > 0 && thirdPlaceList.length > 0 && totalLength >= 9 && totalLength <= 21) {
              let betObj = this.sscOdds.fszh.find((val) => {
                if (val.oddsKey === this.fszhSelect) {
                  return val;
                }
              });
              let arr = [];

              for (let i = 0; i < firstPlaceList.length; i++) {
                for (let j = 0; j < secondPlaceList.length; j++) {
                  for (let k = 0; k < thirdPlaceList.length; k++) {
                    betObj = JSON.parse(JSON.stringify(betObj));
                    let betContent = [];
                    betContent.push(firstPlaceList[i].number);
                    betContent.push(secondPlaceList[j].number);
                    betContent.push(thirdPlaceList[k].number);
                    this.$set(betObj, 'betContent', betContent.toString());
                    if (this.betGameNo !== this.gameInfo.gameNo) {
                      this.setBetGameNo(this.gameInfo.gameNo);
                    }
                    this.$set(betObj, 'choose', true);
                    this.$set(betObj, 'check', true);
                    arr.push(JSON.parse(JSON.stringify(betObj)));
                    //this.setSelectList(Object.assign({}, betObj));
                  }

                }
              }
              self.setSelectListFast(arr);
            }
          }

      },
      findSpecial(item){

        return this.specialChoose.findIndex((value,index,arr)=>{
          if(item.activeType=='threedw' || item.activeType=='fszh'){
            return (item.activeType===value.activeType  && item.type==value.type && item.place === value.place && item.number === value.number);
          }else if(item.activeType=='twodw'){
            return (item.activeType===value.activeType && item.type==value.type && item.select === value.select && item.number === value.number);
          }else if(item.activeType=='zxthree'){
            return (item.activeType===value.activeType && item.type==value.type && item.select === value.select && item.number === value.number);
          }else if(item.activeType=='zxsix'){
            return (item.activeType===value.activeType && item.type==value.type && item.select === value.select && item.number === value.number);
          }

        });
      },
      selectActiveType(activeType){
        this.activeType = activeType;
        this.setPlayType(activeType);
        this.specialLists = [];
        this.specialChoose = [];
        this.setSelectList(null);
        this.setNoteLists(null);
        this.initSpecialChoose();
        if(this.activeType==='twozh'){
          this.twozzhList = this.sscOdds.twozzh[this.twozzhSelect];
        }else if(this.activeType==='threezh'){
          this.threezzhList = this.sscOdds.threezzh[this.threezzhSelect];
        }else if(this.activeType==='kd'){
          this.kdList = this.sscOdds.kd[this.kdSelect];
        }else if(this.activeType==='ndsm'){
          this.ndsmList = this.sscOdds.ndsm[this.ndsmSelect];
        }else if(this.activeType==='twohs'){
          this.twohsChange();
        }else if(this.activeType==='threehs'){
          this.threehsChange();
        }else if(this.activeType==='zxthree'){
          this.initSpecialChoose('zx',this.zxthreeSelect);
        }else if(this.activeType==='zxsix'){
          this.initSpecialChoose('zx',this.zxsixSelect);
        }else if(this.activeType==='twodw'){
          this.initSpecialChoose('twodw',this.twodwSelect);
        }else if(this.activeType==='threedw'){
          this.initSpecialChoose('threedw',this.twodwSelect);
        }else if(this.activeType==='fszh'){
          this.initSpecialChoose('fszh',this.fszhSelect);
        }
      },
      twohsChange(){
        this.twohsLists = this.sscOdds.twozhs['2hs'+this.twohsSelect];
        this.twohwsLists = this.sscOdds.twozhs['2hws'+this.twohsSelect];
      },
      threehsChange(){
        this.threehsLists = this.sscOdds.threezhs['3hs'+this.threehsSelect];
        this.threehwsLists = this.sscOdds.threezhs['3hws'+this.threehsSelect];
      },
      ndsmChange(){
        this.ndsmList = this.sscOdds.ndsm[this.ndsmSelect];
        this.setSelectList(null);
      },
      twozzhChange(){
        this.twozzhList = this.sscOdds.twozzh[this.twozzhSelect];
      },
      threezzhChange(){
        this.threezzhList = this.sscOdds.threezzh[this.threezzhSelect];
      },
      kdChange(){
        this.kdList = this.sscOdds.kd[this.kdSelect];
      },
      selectOdds(item){
        if(!this.betState || item.status==='1'){
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
        item.choose = false;
        let index = this.findChoose(item);
        if (index !== -1) {
          this.selectList.splice(index, 1);
        }
      },
      updateHigh(data){
        this.foldis = data.showShuyin;

      },
      initAllOddsList(){
        let self = this;
        if(!self.specialLists){
          console.log('self.specialLists is undefined');
          return;
        }
        if(!self.twodwLists1){
          console.log('self.twodwLists1 is undefined');
          return;
        }
        if(!self.twodwLists2){
          console.log('self.twodwLists2 is undefined');
          return;
        }
        if(!self.threedwLists1){
          console.log('self.threedwLists1 is undefined');
          return;
        }
        if(!self.threedwLists2){
          console.log('self.threedwLists2 is undefined');
          return;
        }
        if(!self.threedwLists3){
          console.log('self.threedwLists3 is undefined');
          return;
        }
        if(!self.fsLists1){
          console.log('self.fsLists1 is undefined');
          return;
        }
        if(!self.fsLists2){
          console.log('self.fsLists2 is undefined');
          return;
        }
        if(!self.fsLists3){
          console.log('self.fsLists3 is undefined');
          return;
        }
        self.allSearchList=[false,false,false,false,false];
        self.specialLists.forEach(val=>{
          self.$delete(val, 'choose');
          self.$delete(val, 'check');
        })
        self.twozzhList.forEach(val=>{
          self.$delete(val, 'choose');
          self.$delete(val, 'check');
          self.$delete(val, 'betAmt');
        })
        for(let key in self.threezzhList){
          self.threezzhList[key].forEach(val=>{
            self.$delete(val, 'choose');
            self.$delete(val, 'check');
            self.$delete(val, 'betAmt');
          })
        }
        self.twohsLists.forEach(val=>{
          self.$delete(val, 'choose');
          self.$delete(val, 'check');
          self.$delete(val, 'betAmt');
        })
        self.twohwsLists.forEach(val=>{
          self.$delete(val, 'choose');
          self.$delete(val, 'check');
          self.$delete(val, 'betAmt');
        })
        self.threehsLists.forEach(val=>{
          self.$delete(val, 'choose');
          self.$delete(val, 'check');
          self.$delete(val, 'betAmt');
        })
        self.threehwsLists.forEach(val=>{
          self.$delete(val, 'choose');
          self.$delete(val, 'check');
          self.$delete(val, 'betAmt');
        })

        self.specialChoose= [];
        self.specialLists.forEach(val=>{
          self.$delete(val, 'choose');
          self.$delete(val, 'check');
          self.$delete(val, 'betAmt');
        });
        self.twodwLists1.forEach(val => {
          self.$delete(val, 'choose');
          self.$delete(val, 'check');
          self.$delete(val, 'betAmt');
        });
        self.twodwLists2.forEach(val => {
          self.$delete(val, 'choose');
          self.$delete(val, 'check');
          self.$delete(val, 'betAmt');
        });
        self.threedwLists1.forEach(val => {
          self.$delete(val, 'choose');
          self.$delete(val, 'check');
          self.$delete(val, 'betAmt');
        });
        self.threedwLists2.forEach(val => {
          self.$delete(val, 'choose');
          self.$delete(val, 'check');
          self.$delete(val, 'betAmt');
        });
        self.threedwLists3.forEach(val => {
          self.$delete(val, 'choose');
          self.$delete(val, 'check');
          self.$delete(val, 'betAmt');
        });
        self.fsLists1.forEach(val => {
          self.$delete(val, 'choose');
          self.$delete(val, 'check');
          self.$delete(val, 'betAmt');
        });
        self.fsLists2.forEach(val => {
          self.$delete(val, 'choose');
          self.$delete(val, 'check');
          self.$delete(val, 'betAmt');
        });
        self.fsLists3.forEach(val => {
          self.$delete(val, 'choose');
          self.$delete(val, 'check');
          self.$delete(val, 'betAmt');
        });
        self.setSelectList(null);
      },
      initSelectList(flag){
        this.clearSpecialSelect(flag);
      },
      clearSpecialSelect(flag){

        let self = this;

        if(self.playType && self.showBetList){
          self.selectActiveType(self.playType);
        }
        setTimeout(()=>{
          if(flag){
            //self.$refs.resetTime.closeAllTime();
            self.setSocketResetStatus(false);
            self.$refs.resetTime.infoObtain();
            self.$refs.resetTime.fiveRecentResultShow = false;
            self.$refs.resetTime.fiveRecentResultList = [];
          }
        },500)
        self.initAllOddsList();
        //this.setSelectList(null);
      },
      initSpecialChoose(type,select){
        let self = this;
        if(self.selectList!=null && typeof self.selectList != undefined){
          for(let i = 0;i<this.selectList.length;i++){
            if(this.selectList[i]){
              if ('3dw'===this.selectList[i].playKey ||'2dw'===this.selectList[i].playKey || 'fs'===this.selectList[i].playKey) {
                self.selectList.splice(i,1);
                i=-1;
              }
            }

          }
        }

        if(type==='zx'){
          this.specialLists=[];
          this.specialChoose=[];

        }else if(type==='twodw'){
          this.twodwLists1=[];
          this.twodwLists2=[];
          this.twodwPosition.one=this.twodwSelect.at(0);
          this.twodwPosition.two=this.twodwSelect.at(1);
        }else if(type==='threedw'){
          this.threedwLists1=[];
          this.threedwLists2=[];
          this.threedwLists3=[];
          if(this.threedwSelect=='top3'){
            this.threedwPosition.one = 'w';
            this.threedwPosition.two = 'q';
            this.threedwPosition.three = 'b';
          }else if(this.threedwSelect=='mid3'){
            this.threedwPosition.one = 'q';
            this.threedwPosition.two = 'b';
            this.threedwPosition.three = 's';
          }else if(this.threedwSelect=='end3'){
            this.threedwPosition.one = 'b';
            this.threedwPosition.two = 's';
            this.threedwPosition.three = 'g';
          }
        }else if(type==='fszh'){
          this.fsLists1=[];
          this.fsLists2=[];
          this.fsLists3=[];
          if(this.fszhSelect=='top3'){
            this.fsPosition.one = 'w';
            this.fsPosition.two = 'q';
            this.fsPosition.three = 'b';
          }else if(this.fszhSelect=='mid3'){
            this.fsPosition.one = 'q';
            this.fsPosition.two = 'b';
            this.fsPosition.three = 's';
          }else if(this.fszhSelect=='end3'){
            this.fsPosition.one = 'b';
            this.fsPosition.two = 's';
            this.fsPosition.three = 'g';
          }
        }
        let specialObj = {activeType:this.activeType,select:'',number:-1};
        for(let i = 0;i<10;i++){
          this.$set(specialObj,'select',select);
          this.$set(specialObj,'number',i);
          if(type==='zx'){
            this.specialLists.push(Object.assign({},specialObj));
          }else if(type==='twodw'){
            specialObj.type=this.twodwSelect.at(0);
            this.twodwLists1.push(Object.assign({},specialObj));
            specialObj.type=this.twodwSelect.at(1);
            this.twodwLists2.push(Object.assign({},specialObj));
          }else if(type==='threedw'){
            specialObj.type=this.threedwSelect;
            this.$set(specialObj,'place','one');
            this.threedwLists1.push(Object.assign({},specialObj));
            this.$set(specialObj,'place','two');
            this.threedwLists2.push(Object.assign({},specialObj));
            this.$set(specialObj,'place','three');
            this.threedwLists3.push(Object.assign({},specialObj));
          }else if(type==='fszh'){
            specialObj.type=this.fszhSelect;
            this.$set(specialObj,'place','one');
            this.fsLists1.push(Object.assign({},specialObj));
            this.$set(specialObj,'place','two');
            this.fsLists2.push(Object.assign({},specialObj));
            this.$set(specialObj,'place','three');
            this.fsLists3.push(Object.assign({},specialObj));
          }

        }

      },
      showIsHide(list){
        this.$set(list,'showHide',!list.showHide);

      },
      pageInitialization(){
        let self = this;
        let params = {'lotteryId':self.gameId};
        axios.all([Lottery.getOddsList(params),Lottery.getLotteryNow(params)]).then(axios.spread(function(resLotterys,resLotteryNow){
          if(resLotteryNow.code===10000){
            self.setLotteryNow(resLotteryNow.data);
          }
          if(resLotterys.code===10000){

            self.setCurrentGame(resLotterys.data[0]);

          }

        }))
      }
    },
    mounted(){
      let self = this;

      self.clearSpecialSelect(true);
      this.$nextTick(()=>{
        if(self.playType){

          self.activeType = self.playType;
          self.selectActiveType(self.playType);
        }
        self.setSelectList(null);
      })
    },
    created(){
    }
  }
</script>
<style scoped>
  em, i {
    font-style: normal !important;
  }

</style>
