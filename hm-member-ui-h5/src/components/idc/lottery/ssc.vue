<template>
  <div class="gm_main" id="idc">
    <div class="home">
      <div class="sc-bZQynM OQRyf">
        <my-header back="ture" @activetypeselect="selectActiveType" @refreshTime="initSelectList"></my-header>
        <div class="sc-bdVaJa jaFIbq">
          <my-kj @clearSpecialSelect="initAllOddsList" ref="resetTime"></my-kj>
          <!--主布局开始-->
          <div class="sc-gzVnrw setbetright">
            <div class="sc-htoDjs bTnXwf ssc">
              <!--两面-->
              <div :class="activeType==='totalPenl'?'tab tab-active':'tab'">
                <template v-if="index!='t_lhh' && index.indexOf('qiu') == -1" v-for="(list, index) in sscOdds.lm" >
                  <div  class="bet-panel-wrapper wrap">
                    <div class="tz_box pk10 sptz_box">
                      <div class="bet-panel-header">
                        <div class="title" >{{$t(index)}}</div>
                      </div>
                      <ul  v-show="1==1">
                        <template v-for="(item,key) in list">
                          <li >
                            <div  :class="!sscOdds.lm[index][key].choose?'wf_box':'wf_box bcn_back'"
                                  v-tap="(e)=>selectOdds(sscOdds.lm[index][key],e)">
                              <div class="wf_info">
                                <input class="chk" type="checkbox" style="display: none;">
                                <span class="qiu"><em :class="'n_'+item.oddsKey">{{$t('ssc_'+item.oddsKey)}}</em></span>
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
                <template v-if="index=='t_lhh'" v-for="(list, index) in sscOdds.lm" >
                  <div  class="bet-panel-wrapper wrap">
                    <div class="tz_box pk10 sptz_box">
                      <div class="bet-panel-header">
                        <div class="title" >{{$t(index)}}</div>
                      </div>
                      <ul  v-show="1==1">
                        <template v-for="(item,key) in list">
                          <li >
                            <div  :class="!sscOdds.lm[index][key].choose?'wf_box':'wf_box bcn_back'"
                                  v-tap="(e)=>selectOdds(sscOdds.lm[index][key],e)">
                              <div class="wf_info">
                                <input class="chk" type="checkbox" style="display: none;">
                                <span class="qiu"><em :class="'n_'+item.oddsKey">{{$t('ssc_'+item.oddsKey)}}</em></span>
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
              <!--一到五-->
              <div :class="activeType==='ball'?'tab tab-active':'tab'">
                <template v-for="(i, index) in 5" >
                  <div class="bet-panel-wrapper wrap">
                    <div class="tz_box pk10 sptz_box">
                      <div class="bet-panel-header">
                        <div class="title" >{{$t('ssc'+i)}}</div>
                      </div>
                      <ul v-show="1==1">
                        <template v-for="(item,key) in sscOdds['qiu'+i]">
                          <li >
                            <div  :class="!sscOdds['qiu'+i][key].choose?'wf_box':'wf_box bcn_back'"
                                  v-tap="(e)=>selectOdds(sscOdds['qiu'+i][key],e)">
                              <div class="wf_info">
                                <input class="chk" type="checkbox" style="display: none;">
                                <span class="qiu"><em :class="'n_'+item.oddsKey">{{$t(item.oddsKey.toUpperCase())}}</em></span>
                                <span class="odds">{{betState&&item.status=='0'?oddsReturn(item):'封盘'}}</span>
                              </div>
                              <div class="inp"><input type="text" maxlength="7"></div>
                            </div>
                          </li>
                        </template>
                        <li >
                          <div  :class="!allSearchList[index]?'wf_box':'wf_box bcn_back'"
                                @click="allSearchFun('qiu'+i,index)">
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
                </template>

              </div>
              <!--一字组合-->
              <div :class="activeType==='onezh'?'tab tab-active':'tab'">
                <div class="bet-panel-wrapper wrap">
                  <div class="tz_box pk10 sptz_box">
                    <div class="bet-panel-header">
                      <div class="title" >一字组合(前三)</div>
                    </div>
                    <ul v-show="1==1">
                      <template v-for="(item,key) in sscOdds.onezzh">
                        <li v-if="item.categoryKey==='top3'">
                          <div  :class="!sscOdds.onezzh[key].choose?'wf_box':'wf_box bcn_back'"
                                v-tap="(e)=>selectOdds(sscOdds.onezzh[key],e)">
                            <div class="wf_info">
                              <input class="chk" type="checkbox" style="display: none;">
                              <span class="qiu"><em :class="'n_'+item.oddsKey">{{$t(item.oddsKey.toUpperCase())}}</em></span>
                              <span class="odds">{{betState&&item.status=='0'?oddsReturn(item):'封盘'}}</span>
                            </div>
                            <div class="inp"><input type="text" maxlength="7"></div>
                          </div>
                        </li>
                      </template>
                    </ul>
                  </div>
                </div>
                <div class="bet-panel-wrapper wrap">
                  <div class="tz_box pk10 sptz_box">
                    <div class="bet-panel-header">
                      <div class="title" >一字组合(中三)</div>
                    </div>
                    <ul v-show="1==1">
                      <template v-for="(item,key) in sscOdds.onezzh">
                        <li v-if="item.categoryKey==='mid3'">
                          <div  :class="!sscOdds.onezzh[key].choose?'wf_box':'wf_box bcn_back'"
                                v-tap="(e)=>selectOdds(sscOdds.onezzh[key],e)">
                            <div class="wf_info">
                              <input class="chk" type="checkbox" style="display: none;">
                              <span class="qiu"><em :class="'n_'+item.oddsKey">{{$t(item.oddsKey.toUpperCase())}}</em></span>
                              <span class="odds">{{betState&&item.status=='0'?oddsReturn(item):'封盘'}}</span>
                            </div>
                            <div class="inp"><input type="text" maxlength="7"></div>
                          </div>
                        </li>
                      </template>
                    </ul>
                  </div>
                </div>
                <div class="bet-panel-wrapper wrap">
                  <div class="tz_box pk10 sptz_box">
                    <div class="bet-panel-header">
                      <div class="title" >一字组合(后三)</div>
                    </div>
                    <ul v-show="1==1">
                      <template v-for="(item,key) in sscOdds.onezzh">
                        <li v-if="item.categoryKey==='end3'">
                          <div  :class="!sscOdds.onezzh[key].choose?'wf_box':'wf_box bcn_back'"
                                v-tap="(e)=>selectOdds(sscOdds.onezzh[key],e)">
                            <div class="wf_info">
                              <input class="chk" type="checkbox" style="display: none;">
                              <span class="qiu"><em :class="'n_'+item.oddsKey">{{$t(item.oddsKey.toUpperCase())}}</em></span>
                              <span class="odds">{{betState&&item.status=='0'?oddsReturn(item):'封盘'}}</span>
                            </div>
                            <div class="inp"><input type="text" maxlength="7"></div>
                          </div>
                        </li>
                      </template>
                    </ul>
                  </div>
                </div>
                <div class="bet-panel-wrapper wrap">
                  <div class="tz_box pk10 sptz_box">
                    <div class="bet-panel-header">
                      <div class="title" >一字组合(全五)</div>
                    </div>
                    <ul v-show="1==1">
                      <template v-for="(item,key) in sscOdds.onezzh">
                        <li v-if="item.categoryKey==='all5'">
                          <div  :class="!sscOdds.onezzh[key].choose?'wf_box':'wf_box bcn_back'"
                                v-tap="(e)=>selectOdds(sscOdds.onezzh[key],e)">
                            <div class="wf_info">
                              <input class="chk" type="checkbox" style="display: none;">
                              <span class="qiu"><em :class="'n_'+item.oddsKey">{{$t(item.oddsKey.toUpperCase())}}</em></span>
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
              <!--二字组合-->
              <div :class="activeType==='twozh'?'tab tab-active':'tab'">

                  <div class="bet-panel-wrapper wrap">
                    <div class="tz_box pk10 sptz_box">
                      <div class="bet-panel-header">
                        <div class="title" >
                          <select style="text-align: center; text-align-last: center;" v-model="twozzhSelect"  @change="twozzhChange">
                          <option  value="2ztop3">二字前三</option>
                          <option  value="2zmid3">二字中三</option>
                          <option  value="2zend3">二字后三</option>
                        </select>
                          </div>
                      </div>
                      <ul v-show="1==1">
                        <template v-for="(item,key) in twozzhList">
                          <li >
                            <div  :class="!item.choose?'wf_box':'wf_box bcn_back'"
                                  @click="selectOdds(item)">
                              <div class="wf_info">
                                <input class="chk" type="checkbox" style="display: none;">
                                <span class="qiu"><em :class="'n_'">{{item.oddsKey}}</em></span>
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
              <!--三字组合-->
              <div :class="activeType==='threezh'?'tab tab-active':'tab'">
                <div class="bet-panel-wrapper wrap">
                  <div class="tz_box pk10 sptz_box">
                    <div class="bet-panel-header">
                      <div class="title" ><select style="text-align: center; text-align-last: center;" v-model="threezzhSelect" @change="threezzhChange">
                        <option value="3ztop3">三字前三</option>
                        <option value="3zmid3">三字中三</option>
                        <option value="3zend3">三字后三</option>
                      </select></div>
                    </div>
                  </div>
                </div>
                <template v-for="(list, index) in threezzhList" >
                  <div class="bet-panel-wrapper wrap">
                    <div class="tz_box pk10 sptz_box">
                      <div class="bet-panel-header">
                        <div class="title" >{{$t(index)}}</div>
                      </div>
                      <ul v-show="1==1">
                        <template v-for="(item,key) in list">
                          <li >
                            <div  :class="!item.choose?'wf_box':'wf_box bcn_back'"
                                  @click="selectOdds(item)">
                              <div class="wf_info">
                                <input class="chk" type="checkbox" style="display: none;">
                                <span class="qiu"><em :class="'n_'+item.oddsKey">{{item.oddsKey.at(0)}}{{item.oddsKey.at(1)}}{{item.oddsKey.at(2)}}</em></span>
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
              <!--二字定位-->
              <div :class="activeType==='twodw'?'tab tab-active':'tab'">

                <div class="bet-panel-wrapper wrap">
                  <div class="tz_box pk10 sptz_box">
                    <div class="bet-panel-header">
                      <div class="title" >
                        <select style="text-align: center; text-align-last: center;" v-model="twodwSelect" @change="initSpecialChoose('twodw',twodwSelect)">
                          <template v-for="(list,index) in sscOdds.twozdw">
                            <option :value="list.oddsKey">{{$t(list.oddsKey)}}(赔率:{{betState&&list.status=='0'?oddsReturn(list):'封盘'}})</option>
                          </template>
                        </select>
                      </div>
                    </div>
                    <ul v-show="1==1">
                      <template v-for="(item,key) in twodwLists1">
                        <li >
                          <div  :class="!item.choose?'wf_box':'wf_box bcn_back'"
                                @click="specialSelect(item)">
                            <div class="wf_info">
                              <input class="chk" type="checkbox" style="display: none;">
                              <span class="qiu"><em :class="'n_'+item.number">{{item.number}}</em></span>
                              <span class="odds">{{betState?$t(twodwPosition.one):'封盘'}}</span>
                            </div>
                            <div class="inp"><input type="text" maxlength="7"></div>
                          </div>
                        </li>
                      </template>
                    </ul>
                  </div>
                </div>

                <div class="bet-panel-wrapper wrap">
                  <div class="tz_box pk10 sptz_box">
                    <div class="bet-panel-header">
                      <div class="title" >
                        {{$t(twodwPosition.two)}}
                      </div>
                    </div>
                    <ul v-show="1==1">
                      <template v-for="(item,key) in twodwLists2">
                        <li >
                          <div  :class="!item.choose?'wf_box':'wf_box bcn_back'"
                                @click="specialSelect(item)">
                            <div class="wf_info">
                              <input class="chk" type="checkbox" style="display: none;">
                              <span class="qiu"><em :class="'n_'+item.number">{{item.number}}</em></span>
                              <span class="odds">{{betState?$t(twodwPosition.two):'封盘'}}</span>
                            </div>
                            <div class="inp"><input type="text" maxlength="7"></div>
                          </div>
                        </li>
                      </template>
                    </ul>
                  </div>
                </div>
              </div>
              <!--三字定位-->
              <div :class="activeType==='threedw'?'tab tab-active':'tab'">

                <div class="bet-panel-wrapper wrap">
                  <div class="tz_box pk10 sptz_box">
                    <div class="bet-panel-header">
                      <div class="title" >
                        <select style="text-align: center; text-align-last: center;" v-model="threedwSelect" @change="initSpecialChoose('threedw',threedwSelect)">
                          <template v-for="(list,index) in sscOdds.threezdw">
                            <option :value="list.oddsKey">{{$t(list.oddsKey)}}(赔率:{{betState && list.status=='0'?oddsReturn(list):'封盘'}})</option>
                          </template>
                        </select>
                      </div>
                    </div>
                    <ul v-show="1==1">
                      <template v-for="(item,key) in threedwLists1">
                        <li >
                          <div  :class="!item.choose?'wf_box':'wf_box bcn_back'"
                                @click="specialSelect(item)">
                            <div class="wf_info">
                              <input class="chk" type="checkbox" style="display: none;">
                              <span class="qiu"><em :class="'n_'+item.number">{{item.number}}</em></span>
                              <span class="odds">{{betState?$t(threedwPosition.one):'封盘'}}</span>
                            </div>
                            <div class="inp"><input type="text" maxlength="7"></div>
                          </div>
                        </li>
                      </template>
                    </ul>
                  </div>
                </div>

                <div class="bet-panel-wrapper wrap">
                  <div class="tz_box pk10 sptz_box">
                    <div class="bet-panel-header">
                      <div class="title" >
                        {{$t(threedwPosition.two)}}
                      </div>
                    </div>
                    <ul v-show="1==1">
                      <template v-for="(item,key) in threedwLists2">
                        <li >
                          <div  :class="!item.choose?'wf_box':'wf_box bcn_back'"
                                @click="specialSelect(item)">
                            <div class="wf_info">
                              <input class="chk" type="checkbox" style="display: none;">
                              <span class="qiu"><em :class="'n_'+item.number">{{item.number}}</em></span>
                              <span class="odds">{{betState?$t(threedwPosition.two):'封盘'}}</span>
                            </div>
                            <div class="inp"><input type="text" maxlength="7"></div>
                          </div>
                        </li>
                      </template>
                    </ul>
                  </div>
                </div>
                <div class="bet-panel-wrapper wrap">
                  <div class="tz_box pk10 sptz_box">
                    <div class="bet-panel-header">
                      <div class="title" >
                        {{$t(threedwPosition.three)}}
                      </div>
                    </div>
                    <ul v-show="1==1">
                      <template v-for="(item,key) in threedwLists3">
                        <li >
                          <div  :class="!item.choose?'wf_box':'wf_box bcn_back'"
                                @click="specialSelect(item)">
                            <div class="wf_info">
                              <input class="chk" type="checkbox" style="display: none;">
                              <span class="qiu"><em :class="'n_'+item.number">{{item.number}}</em></span>
                              <span class="odds">{{betState?$t(threedwPosition.three):'封盘'}}</span>
                            </div>
                            <div class="inp"><input type="text" maxlength="7"></div>
                          </div>
                        </li>
                      </template>
                    </ul>
                  </div>
                </div>
              </div>
              <!--二字和数-->
              <div :class="activeType==='twohs'?'tab tab-active':'tab'">

                <div class="bet-panel-wrapper wrap">
                  <div class="tz_box pk10 sptz_box">
                    <div class="bet-panel-header">
                      <div class="title" >
                        {{$t('2hs')}}<select style="text-align: center; text-align-last: center;" v-model="twohsSelect" @change="twohsChange">
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
                      </div>
                    </div>
                    <ul v-show="1==1">
                      <template v-for="(item,key) in twohsLists">
                        <li >
                          <div  :class="!item.choose?'wf_box':'wf_box bcn_back'"
                                @click="selectOdds(item)">
                            <div class="wf_info">
                              <input class="chk" type="checkbox" style="display: none;">
                              <span class="qiu"><em :class="'n_'">{{item.oddsKey}}</em></span>
                              <span class="odds">{{betState&&item.status=='0'?oddsReturn(item):'封盘'}}</span>
                            </div>
                            <div class="inp"><input type="text" maxlength="7"></div>
                          </div>
                        </li>
                      </template>
                    </ul>
                  </div>
                </div>

                <div class="bet-panel-wrapper wrap">
                  <div class="tz_box pk10 sptz_box">
                    <div class="bet-panel-header">
                      <div class="title" >
                        {{$t('2hws')}}
                      </div>
                    </div>
                    <ul v-show="1==1">
                      <template v-for="(item,key) in twohwsLists">
                        <li >
                          <div  :class="!item.choose?'wf_box':'wf_box bcn_back'"
                                @click="selectOdds(item)">
                            <div class="wf_info">
                              <input class="chk" type="checkbox" style="display: none;">
                              <span class="qiu"><em :class="'n_'">{{item.oddsKey}}</em></span>
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

                <div class="bet-panel-wrapper wrap">
                  <div class="tz_box pk10 sptz_box">
                    <div class="bet-panel-header">
                      <div class="title" >
                        {{$t('3hs')}}<select style="text-align: center; text-align-last: center;" v-model="threehsSelect" @change="threehsChange">
                          <option value="top3">{{$t('top3')}}</option>
                          <option value="mid3">{{$t('mid3')}}</option>
                          <option value="end3">{{$t('end3')}}</option>
                        </select>
                      </div>
                    </div>
                    <ul v-show="1==1">
                      <template v-for="(item,key) in threehsLists">
                        <li >
                          <div  :class="!item.choose?'wf_box':'wf_box bcn_back'"
                                @click="selectOdds(item)">
                            <div class="wf_info">
                              <input class="chk" type="checkbox" style="display: none;">
                              <span class="qiu"><em :class="'n_'">{{item.oddsKey}}</em></span>
                              <span class="odds">{{betState&&item.status=='0'?oddsReturn(item):'封盘'}}</span>
                            </div>
                            <div class="inp"><input type="text" maxlength="7"></div>
                          </div>
                        </li>
                      </template>
                    </ul>
                  </div>
                </div>

                <div class="bet-panel-wrapper wrap">
                  <div class="tz_box pk10 sptz_box">
                    <div class="bet-panel-header">
                      <div class="title" >
                        {{$t('3hws')}}
                      </div>
                    </div>
                    <ul v-show="1==1">
                      <template v-for="(item,key) in threehwsLists">
                        <li >
                          <div  :class="!item.choose?'wf_box':'wf_box bcn_back'"
                                @click="selectOdds(item)">
                            <div class="wf_info">
                              <input class="chk" type="checkbox" style="display: none;">
                              <span class="qiu"><em :class="'n_'">{{item.oddsKey}}</em></span>
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

                <div class="bet-panel-wrapper wrap">
                  <div class="tz_box pk10 sptz_box">
                    <div class="bet-panel-header">
                      <div class="title" >
                        组选三<select style="text-align: center; text-align-last: center;" v-model="zxthreeSelect" @change="initSpecialChoose('zx',zxthreeSelect)">
                        <option value="zx3top3">{{$t('top3')}}</option>
                        <option value="zx3mid3">{{$t('mid3')}}</option>
                        <option value="zx3end3">{{$t('end3')}}</option>
                      </select>
                      </div>
                    </div>
                    赔率:{{betState?zxthreeOdds:'封盘'}}
                    <ul v-show="1==1">
                      <template v-for="(item,key) in specialLists">
                        <li >
                          <div  :class="!item.choose?'wf_box':'wf_box bcn_back'"
                                @click="specialSelect(item)">
                            <div class="wf_info">
                              <input class="chk" type="checkbox" style="display: none;">
                              <span class="qiu"><em :class="'n_'+item.number">{{item.number}}</em></span>
                              <span class="odds">{{betState?'':'封盘'}}</span>
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

                <div class="bet-panel-wrapper wrap">
                  <div class="tz_box pk10 sptz_box">
                    <div class="bet-panel-header">
                      <div class="title" >
                        组选六<select style="text-align: center; text-align-last: center;" v-model="zxsixSelect" @change="initSpecialChoose('zx',zxsixSelect)">
                        <option value="zx6top3">{{$t('top3')}}</option>
                        <option value="zx6mid3">{{$t('mid3')}}</option>
                        <option value="zx6end3">{{$t('end3')}}</option>
                      </select>
                      </div>
                    </div>
                    赔率:{{betState?zxsixOdds:'封盘'}}
                    <ul v-show="1==1">
                      <template v-for="(item,key) in specialLists">
                        <li >
                          <div  :class="!item.choose?'wf_box':'wf_box bcn_back'"
                                @click="specialSelect(item)">
                            <div class="wf_info">
                              <input class="chk" type="checkbox" style="display: none;">
                              <span class="qiu"><em :class="'n_'+item.number">{{item.number}}</em></span>
                              <span class="odds">{{betState?'':'封盘'}}</span>
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

                <div class="bet-panel-wrapper wrap">
                  <div class="tz_box pk10 sptz_box">
                    <div class="bet-panel-header">
                      <div class="title" >
                        复式组合<select style="text-align: center; text-align-last: center;" v-model="fszhSelect" @change="initSpecialChoose('fszh',fszhSelect)">
                        <template v-for="(item,index) in sscOdds.fszh">
                          <option :value="item.oddsKey">{{$t(item.oddsKey)}}</option>
                        </template>
                      </select>
                      </div>
                    </div>
                    赔率:{{betState?fszhOddsResult:'封盘'}}
                    <ul v-show="1==1">
                      <template v-for="(item,key) in fsLists1">
                        <li >
                          <div  :class="!item.choose?'wf_box':'wf_box bcn_back'"
                                @click="specialSelect(item)">
                            <div class="wf_info">
                              <input class="chk" type="checkbox" style="display: none;">
                              <span class="qiu"><em :class="'n_'+item.number">{{item.number}}</em></span>
                              <span class="odds">{{betState?$t(fsPosition.one):'封盘'}}</span>
                            </div>
                            <div class="inp"><input type="text" maxlength="7"></div>
                          </div>
                        </li>
                      </template>
                    </ul>
                  </div>
                </div>
                <div class="bet-panel-wrapper wrap">
                  <div class="tz_box pk10 sptz_box">
                    <div class="bet-panel-header">
                      <div class="title" >
                        {{$t(fsPosition.two)}}
                      </div>
                    </div>
                    <ul v-show="1==1">
                      <template v-for="(item,key) in fsLists2">
                        <li >
                          <div  :class="!item.choose?'wf_box':'wf_box bcn_back'"
                                @click="specialSelect(item)">
                            <div class="wf_info">
                              <input class="chk" type="checkbox" style="display: none;">
                              <span class="qiu"><em :class="'n_'+item.number">{{item.number}}</em></span>
                              <span class="odds">{{betState?$t(fsPosition.two):'封盘'}}</span>
                            </div>
                            <div class="inp"><input type="text" maxlength="7"></div>
                          </div>
                        </li>
                      </template>
                    </ul>
                  </div>
                </div>
                <div class="bet-panel-wrapper wrap">
                  <div class="tz_box pk10 sptz_box">
                    <div class="bet-panel-header">
                      <div class="title" >
                        {{$t(fsPosition.three)}}
                      </div>
                    </div>
                    <ul v-show="1==1">
                      <template v-for="(item,key) in fsLists3">
                        <li >
                          <div  :class="!item.choose?'wf_box':'wf_box bcn_back'"
                                @click="specialSelect(item)">
                            <div class="wf_info">
                              <input class="chk" type="checkbox" style="display: none;">
                              <span class="qiu"><em :class="'n_'+item.number">{{item.number}}</em></span>
                              <span class="odds">{{betState?$t(fsPosition.three):'封盘'}}</span>
                            </div>
                            <div class="inp"><input type="text" maxlength="7"></div>
                          </div>
                        </li>
                      </template>
                    </ul>
                  </div>
                </div>
              </div>
              <!--跨度-->
              <div :class="activeType==='kd'?'tab tab-active':'tab'">

                <div class="bet-panel-wrapper wrap">
                  <div class="tz_box pk10 sptz_box">
                    <div class="bet-panel-header">
                      <div class="title" >
                        跨度<select style="text-align: center; text-align-last: center;" v-model="kdSelect" @change="kdChange">
                        <option value="kdtop3">{{$t('top3')}}</option>
                        <option value="kdmid3">{{$t('mid3')}}</option>
                        <option value="kdend3">{{$t('end3')}}</option>
                      </select>
                      </div>
                    </div>
                    <ul v-show="1==1">
                      <template v-for="(item,key) in kdList">
                        <li >
                          <div  :class="!item.choose?'wf_box':'wf_box bcn_back'"
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
              </div>
              <!--牛牛/梭哈-->
              <div :class="activeType==='ndsm'?'tab tab-active':'tab'">
                <template v-for="(list, index) in ndsmOddsResult" >
                  <div  class="bet-panel-wrapper wrap">
                    <div class="tz_box pk10 sptz_box">
                      <div class="bet-panel-header">
                        <div class="title" >{{$t(index)}}</div>
                      </div>
                      <ul  v-show="1==1">
                        <template v-for="(item,key) in list">
                          <li >
                            <div  :class="!item.choose?'wf_box':'wf_box bcn_back'"
                                  @click="selectOdds(item)">
                              <div class="wf_info">
                                <input class="chk" type="checkbox" style="display: none;">
                                <span class="qiu"><em :class="'n_'+item.oddsKey">{{$t(item.oddsKey)}}</em></span>
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
      <myendbet ref="betPageF" @clearSpecialSelect="clearSpecialSelect" @calculationOdds = "calculationOdds"></myendbet>
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
    mounted(){
      let self = this;
      self.initSelectList(true);
      self.setSelectList(null);
    },
    data() {
      return {
        //activeType: 'nnp',
        showBetBox: false,
        gyhShowOrHide: true,
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
        leftMenu: [
          {value: 'nnp', title:'两面盘'},
          {value: 'ball', title:'一至五球'},
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
        allSearchList:[false,false,false,false,false],
      }
    },computed: {
      ...mapGetters(['game','sscOdds','betState','betGameNo','noteLists','gameInfo','selectList','gameId','playType','userOddsCljps','userOddsCloses','userOddsJumps','userOddsNows','userOddss']),
      activeType:{
        get(){
          return this.playType;
        },
        set(newVal){
          this.setPlayType(newVal);
        }

      },
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

              return this.sscOdds.zxs[odd][i].status=='1'?'封盘':this.sscOdds.zxs[odd][i].odds;
            }
          }
        }
      },
      zxsixOdds(){
        if(!this.betState){
          return '封盘';
        }
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
              return item.status=='1'?'封盘':item.odds;
            }
          }
        }
      },
      fszhOddsResult(){
        let self =this;
        if(!self.betState){
          return '封盘';
        }
        let item = self.sscOdds.fszh.find(val=>{
          return val.oddsKey == self.fszhSelect;
        })

        if(item){
          return item.odds;
        }else{
          return '';
        }

      },
      ndsmOddsResult(){
        let self = this;
        self.ndsmList = JSON.parse(JSON.stringify(self.sscOdds.ndsm));
        return self.ndsmList;
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
      ...mapActions(['setNoteLists','setSocketResetStatus','setSelectListFast','setBetGameNo','setSelectList','setLotteryNow','setMarket','setCurrentGame','setPagePosition','setPlayType']),
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
        if(this.activeType=='zxsix' && this.specialChoose.length==8 && 0==item.select.indexOf('zx6') && !item.choose){
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


      },
      calculationOdds(){
        let self = this;
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
          for (let i = 0; i < this.specialChoose.length; i++) {
            if (this.specialChoose[i].type === this.twodwSelect.at(0)) {
              firstPlaceList.push(Object.assign({},this.specialChoose[i]));
            } else if (this.specialChoose[i].type === this.twodwSelect.at(1)) {
              secondPlaceList.push(Object.assign({},this.specialChoose[i]));
            }
          }
          if (firstPlaceList.length > 0 && secondPlaceList.length > 0) {
            let betObj = this.sscOdds.twozdw.find((val) => {
              if (val.oddsKey === this.twodwSelect) {
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
            betObj = JSON.parse(JSON.stringify(betObj));
            for (let i = 0; i < firstPlaceList.length; i++) {
              for (let j = 0; j < secondPlaceList.length; j++) {
                for (let k = 0; k < thirdPlaceList.length; k++) {
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
            betObj = JSON.parse(JSON.stringify(betObj));
            let arr = [];
            for (let i = 0; i < firstPlaceList.length; i++) {
              for (let j = 0; j < secondPlaceList.length; j++) {
                for (let k = 0; k < thirdPlaceList.length; k++) {
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
      },
      selectActiveType(activeType){
        let self = this;
        this.activeType = activeType;

        this.specialLists = [];
        this.specialChoose = [];
        this.initSpecialChoose();
        this.selectList.forEach((item) => {
          self.$delete(item, 'choose');
          self.$delete(item, 'betAmt');
        });
        this.setSelectList(null);
        if(this.activeType==='twozh'){
          this.twozzhList = this.sscOdds.twozzh[this.twozzhSelect];
        }else if(this.activeType==='threezh'){
          this.threezzhList = this.sscOdds.threezzh[this.threezzhSelect];
        }else if(this.activeType==='kd'){
          this.kdList = this.sscOdds.kd[this.kdSelect];
        }else if(this.activeType==='ndsm'){
          /*this.ndsmList = this.sscOdds.ndsm;*/
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
        this.setSelectList(null);
      },
      threehsChange(){
        this.threehsLists = this.sscOdds.threezhs['3hs'+this.threehsSelect];
        this.threehwsLists = this.sscOdds.threezhs['3hws'+this.threehsSelect];
        this.setSelectList(null);
      },
      ndsmChange(){
        this.ndsmList = this.sscOdds.ndsm[this.ndsmSelect];
        this.setSelectList(null);
      },
      twozzhChange(){
        this.twozzhList = this.sscOdds.twozzh[this.twozzhSelect];
        this.setSelectList(null);
      },
      threezzhChange(){
        this.threezzhList = this.sscOdds.threezzh[this.threezzhSelect];
        this.setSelectList(null);
      },
      kdChange(){
        this.kdList = this.sscOdds.kd[this.kdSelect];
        this.setSelectList(null);
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
        item.check = false;
        let index = this.findChoose(item);
        if (index !== -1) {
          this.selectList.splice(index, 1);
        }
      },
      initAllOddsList(){
        let self = this;
        this.specialChoose=[];

        self.allSearchList = [false,false,false,false,false];
        for(let i =1;i<=5;i++){
          self.sscOdds['qiu'+i].forEach(val=>{
            self.$delete(val, 'choose');
            self.$delete(val, 'check');
            self.$delete(val, 'betAmt');
          })
        }
        self.specialLists.forEach(val=>{
          self.$delete(val, 'choose');
          self.$delete(val, 'check');
          self.$delete(val, 'betAmt');
        });
        self.twozzhList.forEach(val=>{
          self.$delete(val, 'choose');
          self.$delete(val, 'check');
          self.$delete(val, 'betAmt');
        });
        for(let key in self.threezzhList){
          self.threezzhList[key].forEach(val=>{
            self.$delete(val, 'choose');
            self.$delete(val, 'check');
            self.$delete(val, 'betAmt');
          });
        }
        self.twohsLists.forEach(val => {
          self.$delete(val, 'choose');
          self.$delete(val, 'check');
          self.$delete(val, 'betAmt');
        });
        self.twohwsLists.forEach(val => {
          self.$delete(val, 'choose');
          self.$delete(val, 'check');
          self.$delete(val, 'betAmt');
        });

        self.threehsLists.forEach(val => {
          self.$delete(val, 'choose');
          self.$delete(val, 'check');
          self.$delete(val, 'betAmt');
        });
        self.threehwsLists.forEach(val => {
          self.$delete(val, 'choose');
          self.$delete(val, 'check');
          self.$delete(val, 'betAmt');
        });

        self.kdList.forEach(val => {
          self.$delete(val, 'choose');
          self.$delete(val, 'check');
          self.$delete(val, 'betAmt');
        });
        for(let key in self.ndsmList){
          self.ndsmList[key].forEach(val => {
            self.$delete(val, 'choose');
            self.$delete(val, 'check');
            self.$delete(val, 'betAmt');
          });
        }

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
      clearSpecialSelect(flag){
        let self = this;
        if(flag){
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



        }
        self.initAllOddsList();
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

      }

    },
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
