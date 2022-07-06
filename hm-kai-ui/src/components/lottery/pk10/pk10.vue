<template>
    <div class="bodybox mrcltj">
        <MyHeader></MyHeader>
        <!--主采种开始-->
        <PKtop @luzhuActionFun="getLuzhuFun" ref="lotteryTop" @getHisList="getHisList"
               @clearFilter="clearFilterInfo"></PKtop>
        <!--默认显示-->
        <div v-if="pageTabSelect=='instantDraw'" class="zhlzbox margt20">
            <div class="listhead">
                <div class="listheadl">
                    <span class="lmms"><i>3</i>开奖记录</span>
                </div>
                <div class="listheadr">
                    <div class="listheadrl">
                        <span id="today" :class="dayType==0?'checked':''" @click="changeDate(0)">今天</span>
                        <span id="yesterday" :class="dayType==-1?'checked':''" @click="changeDate(-1)">昨天</span>
                        <span id="qianday" :class="dayType==-2?'checked':''" @click="changeDate(-2)">前天</span>
                    </div>
                    <div class="listheadrr">
                        <div class="rightime">
                            <datepicker v-model="dateStr" :format="$moment(dateStr).format('YYYY-MM-DD')"
                                        :language="languages.zh"
                                        @selected="customFormatter" name="uniquename"></datepicker>
                        </div>
                        <div>选择日期&nbsp;</div>
                    </div>
                </div>
            </div>
        </div>
        <div v-if="pageTabSelect=='instantDraw' " class="kaijiangjl margt20" style="display: block;margin: 0 auto;">
            <div class="head">
                <ul class="zoushimap" id="kaijiangjl">
                    <li class="kaijiltit kaijjlColor"></li>
                    <li id="jrsmtj" :class="topLi.jrsm?'selected':''" @click="onTopLi(1)"><a href="javascript:">今日双面/号码统计</a>
                        <i></i></li>
                    <li id="cltx" :class="topLi.cltx?'selected':''" @click="onTopLi(2)"><a href="javascript:"> 长龙提醒</a>
                        <i></i></li>
                    <li id="hmfb" :class="topLi.hmfb?'selected':''" @click="onTopLi(3)"><a href="javascript:">号码分布</a>
                        <i></i></li>
                </ul>
                <!--<div @click="resultHistoryShow=!resultHistoryShow" id="kjls"><a href="javascript:">开奖历史</a></div>-->
            </div>

            <div class="listcontent">
                <!--今日双面统计-->
                <div class="jrsmtj" :style="topLi.jrsm?'display: block':'display: none'">
                    <div class="headtxt"> 今日双面统计</div>
                    <table cellpadding="1" cellspacing="1" border="0">
                        <tbody>
                        <tr>
                            <th>名次</th>
                            <th colspan="4">冠军</th>
                            <th colspan="4">亚军</th>
                            <th colspan="4">第三名</th>
                            <th colspan="4">第四名</th>
                            <th colspan="4">第五名</th>
                            <th colspan="4">第六名</th>
                            <th colspan="4">第七名</th>
                            <th colspan="4">第八名</th>
                            <th colspan="4">第九名</th>
                            <th colspan="4">第十名</th>
                        </tr>
                        <tr>
                            <td>单双大小</td>
                            <template v-for="indx in 10">
                                <td>单</td>
                                <td>双</td>
                                <td>大</td>
                                <td>小</td>
                            </template>
                        </tr>
                        <tr v-if="jrsmList" id="shuanmiandata">
                            <td>出现次数</td>
                            <template v-for="list in jrsmList">
                                <td>{{list.dan}}</td>
                                <td>{{list.suan}}</td>
                                <td>{{list.da}}</td>
                                <td>{{list.xiao}}</td>
                            </template>
                        </tr>
                        </tbody>
                    </table>
                    <table class="secondtb" cellpadding="1" cellspacing="1" border="0">
                        <tbody>
                        <tr>
                            <th>冠亚龙虎</th>
                            <th colspan="4">冠亚和</th>
                            <th colspan="2">第一名龙虎</th>
                            <th colspan="2">第二名龙虎</th>
                            <th colspan="2">第三名龙虎</th>
                            <th colspan="2">第四名龙虎</th>
                            <th colspan="2">第五名龙虎</th>
                        </tr>
                        <tr>
                            <td>单双大小龙虎</td>
                            <td>单</td>
                            <td>双</td>
                            <td>大</td>
                            <td>小</td>
                            <td>龙</td>
                            <td>虎</td>
                            <td>龙</td>
                            <td>虎</td>
                            <td>龙</td>
                            <td>虎</td>
                            <td>龙</td>
                            <td>虎</td>
                            <td>龙</td>
                            <td>虎</td>
                        </tr>
                        <tr id="gylhcs">
                            <td>出现次数</td>
                            <td>{{gylhMap.gyhdan}}</td>
                            <td>{{gylhMap.gyhsuan}}</td>
                            <td>{{gylhMap.gyhda}}</td>
                            <td>{{gylhMap.gyhxiao}}</td>
                            <td>{{gylhMap.lh11}}</td>
                            <td>{{gylhMap.lh12}}</td>
                            <td>{{gylhMap.lh21}}</td>
                            <td>{{gylhMap.lh22}}</td>
                            <td>{{gylhMap.lh31}}</td>
                            <td>{{gylhMap.lh32}}</td>
                            <td>{{gylhMap.lh41}}</td>
                            <td>{{gylhMap.lh42}}</td>
                            <td>{{gylhMap.lh51}}</td>
                            <td>{{gylhMap.lh52}}</td>
                        </tr>
                        </tbody>
                    </table>
                </div>
                <!--长龙连开提醒-->
                <div class="cltx" :style="topLi.cltx?'display: block':'display: none'">
                    <div class="headtxt"> 长龙连开提醒</div>
                    <div class="cltxul">
                        <ul id="cltxul">
                            <template v-for="(item,index) in changlongList">
                                <li>{{$t(item.type)}}&nbsp;&nbsp;<span>{{$t(item.oddsKey.toUpperCase())}}</span>&nbsp;&nbsp;<span
                                    :style="item.number>=5?'color:#f11821':''">{{item.number}}&nbsp;&nbsp;</span>期
                                </li>
                            </template>
                        </ul>
                    </div>
                </div>
                <!--查看车号分布-->
                <div class="hmfb" :style="topLi.hmfb?'display: block':'display: none'">
                    <div class="head">
                        <ul class="zoushimap" id="chakanchfb">
                            <li class="kaijiltit">查看车号分布：</li>
                            <li class="01" @click="numScreenFun('01')"
                                :class="resultNumScreen.find((num)=>{return num=='01'})?'selected':''"><a
                                href="javascript:">号码1</a> <i></i></li>
                            <li class="02" @click="numScreenFun('02')"
                                :class="resultNumScreen.find((num)=>{return num=='02'})?'selected':''"><a
                                href="javascript:">号码2</a> <i></i></li>
                            <li class="03" @click="numScreenFun('03')"
                                :class="resultNumScreen.find((num)=>{return num=='03'})?'selected':''"><a
                                href="javascript:">号码3</a> <i></i></li>
                            <li class="04" @click="numScreenFun('04')"
                                :class="resultNumScreen.find((num)=>{return num=='04'})?'selected':''"><a
                                href="javascript:">号码4</a> <i></i></li>
                            <li class="05" @click="numScreenFun('05')"
                                :class="resultNumScreen.find((num)=>{return num=='05'})?'selected':''"><a
                                href="javascript:">号码5</a> <i></i></li>
                            <li class="06" @click="numScreenFun('06')"
                                :class="resultNumScreen.find((num)=>{return num=='06'})?'selected':''"><a
                                href="javascript:">号码6</a> <i></i></li>
                            <li class="07" @click="numScreenFun('07')"
                                :class="resultNumScreen.find((num)=>{return num=='07'})?'selected':''"><a
                                href="javascript:">号码7</a> <i></i></li>
                            <li class="08" @click="numScreenFun('08')"
                                :class="resultNumScreen.find((num)=>{return num=='08'})?'selected':''"><a
                                href="javascript:">号码8</a> <i></i></li>
                            <li class="09" @click="numScreenFun('09')"
                                :class="resultNumScreen.find((num)=>{return num=='09'})?'selected':''"><a
                                href="javascript:">号码9</a> <i></i></li>
                            <li class="10" @click="numScreenFun('10')"
                                :class="resultNumScreen.find((num)=>{return num=='10'})?'selected':''"><a
                                href="javascript:">号码10</a> <i></i></li>
                            <!--<li class="reset">
                                              还原
                                          </li>-->
                        </ul>
                    </div>
                    <div class="head head2">
                        <ul class="zoushimap" id="daxiaodsfb">
                            <li class="kaijiltit">大小单双分布：</li>
                            <li @click="numTypeScreenFun('odd')" :class="resultTypeScreen=='odd'?'selected':''"
                                id="dannum"><a href="javascript:">单</a> <i></i></li>
                            <li @click="numTypeScreenFun('even')" :class="resultTypeScreen=='even'?'selected':''"
                                id="shuangnum"><a href="javascript:">双</a> <i></i></li>
                            <li @click="numTypeScreenFun('over')" :class="resultTypeScreen=='over'?'selected':''"
                                id="danum"><a href="javascript:">大</a> <i></i></li>
                            <li @click="numTypeScreenFun('under')" :class="resultTypeScreen=='under'?'selected':''"
                                id="xiaonum"><a href="javascript:">小</a> <i></i></li>
                            <li @click="numTypeScreenFun('pair')" :class="resultTypeScreen=='pair'?'selected':''"
                                id="duizinum"><a href="javascript:">对子号</a> <i></i></li>
                            <li @click="clearResultScreenFun" class="reset"> 还原</li>
                        </ul>
                    </div>
                </div>
                <!--主显示-->
                <div class="jrsmhmtj jrsmhmtj_kai" id="jrsmhmtj">
                    <table id="jrsmhmtjTab" cellpadding="1" cellspacing="1" border="0"
                           style="background: rgb(212, 212, 212);">
                        <tbody>
                        <tr>
                            <th>时间</th>
                            <th>期数</th>
                            <th id="numberbtn" class="numberbtn"><span @click="resultShowType='num';" id="xshm"
                                                                       :class="resultShowType=='num'?'spanselect':''">显示号码</span><span
                                id="xsdx" @click="resultShowType='numOu';"
                                :class="resultShowType=='numOu'?'spanselect':''">显示大小</span><span
                                @click="resultShowType='numOe';" id="xsds"
                                :class="resultShowType=='numOe'?'spanselect':''">显示单双</span></th>
                            <th colspan="3">冠亚和</th>
                            <th colspan="5">1-5龙虎</th>
                        </tr>
                        <tr v-for="(item,index) in hisList">
                            <td>{{item.actionTimeStr}}</td>
                            <td>{{item.gameNo}}</td>
                            <td>
                                <ul class="imgnumber" v-for="(num,i) in item.result">
                                    <li @click="numScreenFun(num)" :class="resultClassCss(num,index,i)"><i>{{num}}</i>
                                    </li>
                                </ul>
                            </td>
                            <td>{{item.gyh15lh.zh}}</td>
                            <td :style="item.gyh15lh.dx=='OVER'?'color:red':''">{{$t(item.gyh15lh.dx)}}</td>
                            <td :style="item.gyh15lh.ds=='EVEN'?'color:red':''">{{$t(item.gyh15lh.ds)}}</td>
                            <td :style="item.gyh15lh.lh1=='DRAGON'?'color:red':''">{{$t(item.gyh15lh.lh1)}}</td>
                            <td :style="item.gyh15lh.lh2=='DRAGON'?'color:red':''">{{$t(item.gyh15lh.lh2)}}</td>
                            <td :style="item.gyh15lh.lh3=='DRAGON'?'color:red':''">{{$t(item.gyh15lh.lh3)}}</td>
                            <td :style="item.gyh15lh.lh4=='DRAGON'?'color:red':''">{{$t(item.gyh15lh.lh4)}}</td>
                            <td :style="item.gyh15lh.lh5=='DRAGON'?'color:red':''">{{$t(item.gyh15lh.lh5)}}</td>
                        </tr>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>

        <!--路珠分析-->
        <div v-if="pageTabSelect=='roadA'" class="zhlzbox margt20">
            <div class="listhead">
                <div class="listheadl pk10">
                    <!--选中加 xxselected-->
                    <span @click="roadAModelFun('about')"
                          :class="roadA.roadAModeSelect=='about'?'zhms xxselected':'zhms'"><i>1</i>综合模式</span>
                    <span @click="roadAModelFun('alone')"
                          :class="roadA.roadAModeSelect=='alone'?'dxms xxselected':'dxms'"><i>2</i>单选模式</span>
                    <span @click="roadAModelFun('lm')"
                          :class="roadA.roadAModeSelect=='lm'?'lmmsse xxselected':'lmmsse'"><i>3</i>两面模式</span>
                </div>
                <div class="listheadr">
                    <div class="listheadrl">
                        <span id="today" :class="dayType==0?'checked':''" @click="changeDate(0)">今天</span>
                        <span id="yesterday" :class="dayType==-1?'checked':''" @click="changeDate(-1)">昨天</span>
                        <span id="qianday" :class="dayType==-2?'checked':''" @click="changeDate(-2)">前天</span>
                    </div>
                    <div class="listheadrr">
                        <div class="rightime">
                            <datepicker v-model="dateStr" :format="$moment(dateStr).format('YYYY-MM-DD')"
                                        :language="languages.zh"
                                        @selected="customFormatter" name="uniquename"></datepicker>
                        </div>
                        <div>选择日期&nbsp;</div>
                    </div>
                </div>
            </div>
            <!--主开始-->
            <div class="listbox pk10">
                <!--筛选开始-->
                <div class="checkbox">
                    <!--综合模式筛选-->
                    <div v-if="roadA.roadAModeSelect=='about'" id="zhms" class="checkbtnzh">
                        <ul class="zhmsmc">
                            <li class="title">筛选名次：</li>
                            <template v-for="(item,index) in roadA.filters.aboutCheckNoBoxList">
                                <li @click="roadAAboutFilterNoFun(index)" :class="item?'sinli checked':'sinli'"><i>1</i>
                                    <a>{{$t(index)}}</a>
                                </li>
                            </template>


                            <li @click="roadAAboutNoAll(true)" class="zhcheckall">全选</li>
                            <li @click="roadAAboutNoAll(false)" class="zhclear">清空</li>
                        </ul>
                        <ul class="zhmslz">
                            <li class="title">筛选路珠：</li>
                            <template v-for="(item,index) in roadA.filters.aboutCheckRoadBoxList">
                                <li @click="roadAAboutFilterRoadFun(index)" :class="item?'lztype checked':'lztype'">
                                    <i>2</i>
                                    <a href="javascript:void(0)">{{$t(index)}}</a>
                                </li>
                            </template>
                        </ul>
                    </div>
                    <!--单选模式-->
                    <div v-if="roadA.roadAModeSelect=='alone'" id="dxms" class="checkbtnzh checkbtndx">
                        <ul>
                            <li class="title">筛选路珠：</li>
                            <template v-for="(item,index) in roadA.filters.aloneCheckRoadBoxList">
                                <li @click="roadAAloneFilterRoadFun(index)" :class="item?'lztype checked':'lztype'">
                                    <i>2</i>
                                    <a href="javascript:void(0)">{{$t(index)}}</a>
                                </li>
                            </template>


                            <li @click="roadAAloneNoAll(true)" class="zhcheckall_a">全选</li>
                            <li @click="roadAAloneNoAll(false)" class="zhclear_a">清空</li>
                        </ul>
                        <ul class="xuanzhemc">
                            <li class="mctitle">选择名次：</li>
                            <template v-for="(item,index) in 10">
                                <li @click="roadAAloneNoFilterFun('no'+item)"
                                    :class="roadA.filters.aloneSelectNo=='no'+item?'sinli selected':'sinli'">
                                    <span>1</span>
                                    <a href="javascript:void(0)">{{$t('no'+item)}}</a>
                                </li>
                            </template>
                            <li @click="roadAAloneNoFilterFun('gyh')"
                                :class="roadA.filters.aloneSelectNo=='gyh'?'sinli selected':'sinli'"><span>11</span>
                                <a href="javascript:void(0)">冠亚和</a>
                            </li>
                        </ul>
                    </div>
                    <!--两面模式筛选-->
                    <div v-if="roadA.roadAModeSelect=='lm'" id="lmms" class="checkbtnzh checkbtnmc">
                        <ul>
                            <li class="title">筛选名次：</li>
                            <template v-for="(item,index) in roadA.filters.lmCheckNumBoxList">
                                <li @click="roadALmNoFilterFun(index)" :class="item?'sinli checked':'sinli'"><i>1</i>
                                    <a>{{$t(index)}}</a>
                                </li>
                            </template>
                            <li @click="roadALmNoFilterAllFun(true)" class="zhcheckall">全选</li>
                            <li @click="roadALmNoFilterAllFun(false)" class="zhclear">清空</li>
                        </ul>
                        <ul class="xuanzhemc">
                            <li class="mctitle">选择路珠：</li>
                            <li @click="roadA.filters.lmSelectRoad='ou'"
                                :class="roadA.filters.lmSelectRoad=='ou'?'lztype selected':'lztype'"><span>2</span>
                                <a>大小</a>
                            </li>
                            <li @click="roadA.filters.lmSelectRoad='oe'"
                                :class="roadA.filters.lmSelectRoad=='oe'?'lztype selected':'lztype'"><span>1</span>
                                <a>单双</a>
                            </li>
                            <li @click="roadA.filters.lmSelectRoad='lh'"
                                :class="roadA.filters.lmSelectRoad=='lh'?'lztype selected':'lztype'"><span>3</span>
                                <a>龙虎</a>
                            </li>
                        </ul>
                    </div>
                </div>

                <!--                主显示-->
                <div class="listcontent">
                    <div class="box">
                        <div id="box11" class="item item_1   ball_1 " style="display: block;">
                            <div class="dsLZ">
                                <template v-for="(noList,noIndex) in luzhuList">
                                    <template v-for="(noArr,arrIndex) in noList">
                                        <table v-if="(roadA.roadAModeSelect=='about' && roadA.filters.aboutCheckNoBoxList[noIndex] && ((roadA.filters.aboutCheckRoadBoxList.ou && arrIndex.indexOf('ou')!=-1) || (roadA.filters.aboutCheckRoadBoxList.oe && arrIndex.indexOf('oe')!=-1) || (roadA.filters.aboutCheckRoadBoxList.lh && arrIndex.indexOf('lh')!=-1)))
                                                     ||(roadA.roadAModeSelect=='alone' && roadA.filters.aloneSelectNo==noIndex && ((roadA.filters.aloneCheckRoadBoxList.ou && noIndex+'ou' == arrIndex) || (roadA.filters.aloneCheckRoadBoxList.oe && noIndex+'oe' == arrIndex)  || (roadA.filters.aloneCheckRoadBoxList.lh && 'lh'+noIndex == arrIndex)))
                                                     ||(roadA.roadAModeSelect=='lm' && roadA.filters.lmCheckNumBoxList[noIndex] && ((noIndex+roadA.filters.lmSelectRoad == arrIndex || roadA.filters.lmSelectRoad + noIndex == arrIndex)))"
                                               class="lz_table_head  g_w1000">
                                            <tbody>
                                            <tr>
                                                <td>连续出现&nbsp;<input class="txtNum"
                                                                     v-model="roadA.filters['numTwo'+arrIndex]"
                                                                     type="number" min="1">&nbsp;次以上&nbsp;
                                                    <select v-model="roadA.filters['selectTwo'+arrIndex]"
                                                            class="secType">
                                                        <option
                                                            :value="arrIndex.indexOf('ou')!=-1?'over':arrIndex.indexOf('oe')!=-1?'odd':arrIndex.indexOf('lh')!=-1?'dragon':''">
                                                            {{arrIndex.indexOf('ou')!=-1?'大':arrIndex.indexOf('oe')!=-1?'单':arrIndex.indexOf('lh')!=-1?'龙':''}}
                                                        </option>
                                                        <option
                                                            :value="arrIndex.indexOf('ou')!=-1?'under':arrIndex.indexOf('oe')!=-1?'even':arrIndex.indexOf('lh')!=-1?'tiger':''">
                                                            {{arrIndex.indexOf('ou')!=-1?'小':arrIndex.indexOf('oe')!=-1?'双':arrIndex.indexOf('lh')!=-1?'虎':''}}
                                                        </option>
                                                    </select>
                                                    <input type="button" class="btnCheck"
                                                           @click="roadATableFilterFun(arrIndex,noIndex)" value="确定">出现的次数为:
                                                    <span class="sec_count">{{roadA.filters['showNum'+arrIndex]}}</span>
                                                </td>
                                                <td></td>
                                                <td class="righttd">
                                                    <span class="now_l"></span><span
                                                    class="jinri">{{totalDateTime}}</span>&nbsp;累计：{{arrIndex.indexOf('ou')!=-1?'大':arrIndex.indexOf('oe')!=-1?'单':arrIndex.indexOf('lh')!=-1?'龙':''}}(<span
                                                    class="count left_count" data="">{{arrIndex.indexOf('ou')!=-1?roadA[noIndex].over:arrIndex.indexOf('oe')!=-1?roadA[noIndex].odd:arrIndex.indexOf('lh')!=-1?roadA[noIndex].dragon:''}}</span>)&nbsp;&nbsp;{{arrIndex.indexOf('ou')!=-1?'小':arrIndex.indexOf('oe')!=-1?'双':arrIndex.indexOf('lh')!=-1?'虎':''}}(<span
                                                    class="count right_count" data="单">{{arrIndex.indexOf('ou')!=-1?roadA[noIndex].under:arrIndex.indexOf('oe')!=-1?roadA[noIndex].even:arrIndex.indexOf('lh')!=-1?roadA[noIndex].tiger:''}}</span>)
                                                    <span class="important"><span class="ifgj">{{$t(noIndex)}}</span>&nbsp;<span
                                                        class="ifds">{{arrIndex.indexOf('oe')!=-1?$t('oe'):arrIndex.indexOf('ou')!=-1?$t('ou'):arrIndex.indexOf('lh')!=-1?$t('lh'):''}}</span>&nbsp;&nbsp;&nbsp;最新</span>
                                                </td>
                                                <td class="tobottom"></td>
                                            </tr>
                                            </tbody>
                                        </table>
                                        <div v-if="roadA.roadAModeSelect=='about' && roadA.filters.aboutCheckNoBoxList[noIndex] && ((roadA.filters.aboutCheckRoadBoxList.ou && arrIndex.indexOf('ou')!=-1) || (roadA.filters.aboutCheckRoadBoxList.oe && arrIndex.indexOf('oe')!=-1) || (roadA.filters.aboutCheckRoadBoxList.lh && arrIndex.indexOf('lh')!=-1))
                                                  ||(roadA.roadAModeSelect=='alone' && roadA.filters.aloneSelectNo==noIndex && ((roadA.filters.aloneCheckRoadBoxList.ou && noIndex+'ou' == arrIndex) || (roadA.filters.aloneCheckRoadBoxList.oe && noIndex+'oe' == arrIndex)  || (roadA.filters.aloneCheckRoadBoxList.lh && 'lh'+noIndex == arrIndex)))
                                                    ||(roadA.roadAModeSelect=='lm' && roadA.filters.lmCheckNumBoxList[noIndex] && ((noIndex+roadA.filters.lmSelectRoad == arrIndex || roadA.filters.lmSelectRoad + noIndex == arrIndex)))"
                                             class="item_con" dir="rtl" style="background-position-x: 0px;">
                                            <table class="lz_table_con" border="0" cellpadding="1" cellspacing="1">
                                                <tbody>
                                                <tr class="tablebox">
                                                    <template v-for="(list,index) in noArr">
                                                        <template v-for="(item,i) in list">
                                                            <td :class="item[0]=='even'||item[0]=='over' || item[0] == 'dragon'?'bgcolor':''"
                                                                :style="item[0] == roadA.filters['select'+arrIndex] && item.length>=roadA.filters['num'+arrIndex] && roadA.filters['btn'+arrIndex]?'color: rgb(255, 255, 255); background-color: rgb(253, 173, 86);':''">
                                                                <template v-for="(num,k) in item">
                                                                    <p :style="item[0]=='even'||item[0]=='over' || item[0] == 'dragon'?'color: rgb(241, 2, 11); display: block;':'font-weight: normal;'"
                                                                    >{{$t(num)}}</p>
                                                                </template>

                                                            </td>
                                                        </template>

                                                    </template>
                                                </tr>
                                                </tbody>
                                            </table>
                                        </div>
                                    </template>
                                </template>
                            </div>
                        </div>

                    </div>
                </div>
            </div>
        </div>

        <!--龙虎路珠-->
        <div v-if="pageTabSelect=='dtRoad'" class="zhlzbox margt20" style="display:block">
            <div class="listhead">
                <div class="listheadl">
                    <span class="lmms">龙虎路珠</span>
                </div>
                <div class="listheadr">
                    <div class="listheadrl">
                        <span id="today" :class="dayType==0?'checked':''" @click="changeDate(0)">今天</span>
                        <span id="yesterday" :class="dayType==-1?'checked':''" @click="changeDate(-1)">昨天</span>
                        <span id="qianday" :class="dayType==-2?'checked':''" @click="changeDate(-2)">前天</span>
                    </div>
                    <div class="listheadrr">
                        <div class="rightime">
                            <datepicker v-model="dateStr" :format="$moment(dateStr).format('YYYY-MM-DD')"
                                        :language="languages.zh"
                                        @selected="customFormatter" name="uniquename"></datepicker>
                        </div>
                        <div>选择日期&nbsp;</div>
                    </div>
                </div>
            </div>
            <!--龙虎路珠显示-->
            <div class="listbox">
                <div class="listbox_spread" id="listbox_spread">
                    <div id="spreadDiv" style="height: 28px; padding: 0px; overflow: hidden;">
                        <p id="prep"><i>说明：</i>1 表格需从左向右浏览，右侧一列为最新结果；<span></span></p>
                        <p>2 当长龙中断时，则另起一列显示结果； </p>
                        <p>3 龙虎说明： </p>
                        <p>冠　军龙/虎：“第一名”车号大于“第十名”车号视为【龙】中奖、反之小于视为【虎】中奖；</p>
                        <p>亚　军龙/虎：“第二名”车号大于“第九名”车号视为【龙】中奖、反之小于视为【虎】中奖；</p>
                        <p>第三名龙/虎：“第三名”车号大于“第八名”车号视为【龙】中奖、反之小于视为【虎】中奖；</p>
                        <p>第四名龙/虎：“第四名”车号大于“第七名”车号视为【龙】中奖、反之小于视为【虎】中奖；</p>
                        <p>第五名龙/虎：“第五名”车号大于“第六名”车号视为【龙】中奖、反之小于视为【虎】中奖。</p>
                    </div>
                </div>

                <div class="checkbox">
                    <div id="lmms" class="checkbtnzh checkbtnmc">
                        <ul>
                            <li class="title">筛选名次：</li>
                            <!--选中加class checked-->
                            <template v-for="(item,index) in dtRoad.filters.checkBoxList">
                                <li @click="dtRoadCheckBoxFun(index)"
                                    :class="dtRoad.filters.checkBoxList[index]?'sinli checked':'sinli'"><i>1</i>
                                    <a>{{$t(index.split('lh')[1])}}</a>
                                </li>
                            </template>
                            <li class="zhcheckall" @click="dtRoadCheckBoxAllHandle(true)">全选</li>
                            <li class="zhclear" @click="dtRoadCheckBoxAllHandle(false)">清空</li>
                        </ul>
                    </div>
                </div>

                <div class="listcontent">
                    <div class="box">
                        <div class="dsLZ">
                            <template v-for="i in 5">
                                <template v-for="(nolhList,lhIndex) in luzhuList['no'+i]">
                                    <table v-if="lhIndex.indexOf('lh')!=-1 && dtRoad.filters.checkBoxList[lhIndex]"
                                           class="lz_table_head  g_w1000">
                                        <tbody>
                                        <tr>
                                            <td>连续出现&nbsp;<input class="txtNum" v-model="dtRoad.filters['numTwo'+i]"
                                                                 type="number" min="1">&nbsp;次以上&nbsp;
                                                <select v-model="dtRoad.filters['selectTwo'+i]" class="secType">
                                                    <option value="dragon">龙</option>
                                                    <option value="tiger">虎</option>
                                                </select>
                                                <input type="button" class="btnCheck"
                                                       @click="dtRoadTableFilterFun(lhIndex,i)"
                                                       value="确定">出现的次数为: <span class="sec_count">{{dtRoad.filters['showNum'+i]}}</span>
                                            </td>
                                            <td></td>
                                            <td class="righttd">
                                                <span class="now_l"></span><span class="jinri">{{totalDateTime}}</span>&nbsp;累计：龙(<span
                                                class="count left_count" data="龙">{{dtRoad[lhIndex].dragon}}</span>)&nbsp;&nbsp;虎(<span
                                                class="count right_count" data="虎">{{dtRoad[lhIndex].tiger}}</span>)
                                                <span class="important"><span class="ifgj">{{$t(lhIndex.split('lh')[1])}}</span>&nbsp;<span
                                                    class="ifds">龙虎</span>&nbsp;&nbsp;&nbsp;最新</span>
                                            </td>
                                            <td class="tobottom"></td>
                                        </tr>
                                        </tbody>
                                    </table>
                                    <div v-if="lhIndex.indexOf('lh')!=-1 && dtRoad.filters.checkBoxList[lhIndex]"
                                         class="item_con" dir="rtl" style="background-position-x: -29px;">
                                        <table class="lz_table_con" border="0" cellpadding="1" cellspacing="1">
                                            <tbody>
                                            <tr class="tablebox">
                                                <template v-for="(list,index) in nolhList">
                                                    <template v-for="(item,g) in list">

                                                        <td :class="item[0]=='tiger'?'bgcolor':''"
                                                            :style="item[0] == dtRoad.filters['select'+i] && item.length>=dtRoad.filters['num'+i] && dtRoad.filters['btn'+i]?'color: rgb(255, 255, 255); background-color: rgb(253, 173, 86);':''">
                                                            <template v-for="(num,k) in item">
                                                                <p :style="item[0]=='dragon'?'color: rgb(241, 2, 11); display: block;':'font-weight: normal;'"
                                                                >{{$t(num)}}</p>
                                                            </template>

                                                        </td>
                                                    </template>

                                                </template>
                                            </tr>
                                            </tbody>
                                        </table>
                                    </div>
                                </template>
                            </template>
                        </div>

                    </div>
                </div>

            </div>
        </div>


        <!--号码前后路珠-->
        <div v-if="pageTabSelect=='numberR'" class="zhlzbox margt20" style="display:block">
            <div class="listhead">
                <div class="listheadl">
                    <span class="lmms">号码前后路珠</span>
                </div>
                <div class="listheadr">
                    <div class="listheadr">
                        <div class="listheadrl">
                            <span id="today" :class="dayType==0?'checked':''" @click="changeDate(0)">今天</span>
                            <span id="yesterday" :class="dayType==-1?'checked':''" @click="changeDate(-1)">昨天</span>
                            <span id="qianday" :class="dayType==-2?'checked':''" @click="changeDate(-2)">前天</span>
                        </div>
                        <div class="listheadrr">
                            <div class="rightime">
                                <datepicker v-model="dateStr" :format="$moment(dateStr).format('YYYY-MM-DD')"
                                            :language="languages.zh"
                                            @selected="customFormatter" name="uniquename"></datepicker>
                            </div>
                            <div>选择日期&nbsp;</div>
                        </div>
                    </div>
                </div>
            </div>
            <!--号码前后路珠-->
            <div class="listbox">
                <div class="listbox_spread" id="listbox_spread">
                    <div id="spreadDiv" style="height: 28px; padding: 0px; overflow: hidden;">
                        <p id="prep"><i>说明：</i>1 表格需从左向右浏览，右侧一列为最新结果；<span></span></p>
                        <p> 2 当长龙中断时，则另起一列显示结果；</p>
                        <p>3 “前”表示号码开出在前5名，“后”表示号码开出在后5名 </p>
                    </div>
                </div>

                <div class="checkbox">
                    <div id="lmms" class="checkbtnzh checkbtnmc">
                        <ul>
                            <li class="title">筛选名次：</li>
                            <!--选中加class checked-->
                            <template v-for="(item,index) in numberR.filters.checkBoxList">
                                <li @click="numberRCheckBoxFun(index)" :class="item?'sinli checked':'sinli'"><i>1</i>
                                    <a>{{$t('hm_'+index)}}</a>
                                </li>
                            </template>
                            <li @click="numberRCheckAll(true)" class="zhcheckall">全选</li>
                            <li @click="numberRCheckAll(false)" class="zhclear">清空</li>
                        </ul>
                    </div>
                </div>

                <div class="listcontent">
                    <div class="box">
                        <div class="dsLZ">
                            <template v-for="(noList,noIndex) in luzhuList">
                                <template v-for="(noArr,arrIndex) in noList">
                                    <table v-if="arrIndex.indexOf('qh')!=-1 && numberR.filters.checkBoxList[noIndex]"
                                           class="lz_table_head  g_w1000">
                                        <tbody>
                                        <tr>
                                            <td>连续出现&nbsp;<input class="txtNum"
                                                                 v-model="numberR.filters['numTwo'+noIndex]"
                                                                 type="number" min="1">&nbsp;次以上&nbsp;
                                                <select v-model="numberR.filters['selectTwo'+noIndex]" class="secType">
                                                    <option value="qian">前</option>
                                                    <option value="hou">后</option>
                                                </select>
                                                <input type="button" class="btnCheck"
                                                       @click="numberRTableFilterFun(arrIndex,noIndex)"
                                                       value="确定">出现的次数为: <span class="sec_count">{{numberR.filters['showNum'+noIndex]}}</span>
                                            </td>
                                            <td></td>
                                            <td class="righttd">
                                                <span class="now_l"></span><span class="jinri">{{totalDateTime}}</span>&nbsp;累计：前(<span
                                                class="count left_count" data="前">{{numberR[noIndex].qian}}</span>)&nbsp;&nbsp;后(<span
                                                class="count right_count" data="后">{{numberR[noIndex].hou}}</span>)
                                                <span class="important"><span class="ifgj">{{$t('hm_'+noIndex)}}</span>&nbsp;<span
                                                    class="ifds">前后</span>&nbsp;&nbsp;&nbsp;最新</span>
                                            </td>
                                            <td class="tobottom"></td>
                                        </tr>
                                        </tbody>
                                    </table>
                                    <div v-if="arrIndex.indexOf('qh')!=-1 && numberR.filters.checkBoxList[noIndex]"
                                         class="item_con" dir="rtl" style="background-position-x: -29px;">
                                        <table class="lz_table_con" border="0" cellpadding="1" cellspacing="1">
                                            <tbody>
                                            <tr class="tablebox">
                                                <template v-for="(list,index) in noArr">
                                                    <template v-for="(item,i) in list">
                                                        <td :class="item[0]=='qian'?'bgcolor':''"
                                                            :style="item[0] == numberR.filters['select'+noIndex] && item.length>=numberR.filters['num'+noIndex] && numberR.filters['btn'+noIndex]?'color: rgb(255, 255, 255); background-color: rgb(253, 173, 86);':''">
                                                            <template v-for="(num,k) in item">
                                                                <p :style="item[0]=='qian'?'color: rgb(241, 2, 11); display: block;':'font-weight: normal;'"
                                                                >{{$t('qh_'+num)}}</p>
                                                            </template>

                                                        </td>
                                                    </template>

                                                </template>
                                            </tr>
                                            </tbody>
                                        </table>
                                    </div>
                                </template>
                            </template>
                        </div>

                    </div>
                </div>

            </div>
        </div>


        <!--冠亚和路珠-->
        <div v-show="pageTabSelect=='gyhLZ'" class="zhlzbox margt20" style="display:block">
            <div class="listhead">
                <div class="listheadl">
                    <span class="lmms">冠亚和路珠</span>
                </div>
                <div class="listheadr">
                    <div class="listheadrl">
                        <span id="today" :class="dayType==0?'checked':''" @click="changeDate(0)">今天</span>
                        <span id="yesterday" :class="dayType==-1?'checked':''" @click="changeDate(-1)">昨天</span>
                        <span id="qianday" :class="dayType==-2?'checked':''" @click="changeDate(-2)">前天</span>
                    </div>
                    <div class="listheadrr">
                        <div class="rightime">
                            <datepicker v-model="dateStr" :format="$moment(dateStr).format('YYYY-MM-DD')"
                                        :language="languages.zh"
                                        @selected="customFormatter" name="uniquename"></datepicker>
                        </div>
                        <div>选择日期&nbsp;</div>
                    </div>
                </div>
            </div>
            <!--冠亚和路珠-->
            <div class="listbox">
                <div class="listbox_spread" id="listbox_spread">
                    <div id="spreadDiv" style="height: 28px; padding: 0px; overflow: hidden;">
                        <p id="prep"><i>说明：</i>1 表格需从左向右浏览，右侧一列为最新结果；<span></span></p>
                        <p>2 当长龙中断时，则另起一列显示结果；</p>
                        <p>3 冠亚和单双：“冠亚和值”爲单视为投注“单”的注单视为中奖，爲双视为投注“双”的注单视为中奖，其余视为不中奖；</p>
                        <p>4 冠亚和大小：“冠亚和值”大于11时投注“大”的注单视为中奖，小于或等于11时投注“小”的注单视为中奖，其余视为不中奖；</p>
                        <p>5 “冠亚和值”可能出现的结果爲3～19， 投中对应“冠亚和值”数字的视为中奖，其余视为不中奖。</p>
                    </div>
                </div>
                <div class="listcontent">
                    <div class="box">
                        <div class="dsLZ">
                            <template v-for="(gyh,gyhindex) in luzhuList.gyh">

                                <table v-if="gyhindex=='gyhoe' || gyhindex=='gyhou' " class="lz_table_head  g_w1000">
                                    <tbody>
                                    <tr>
                                        <td>连续出现&nbsp;<template v-if="gyhindex=='gyhou'"><input class="txtNum"
                                                                                                v-model="gyhLZOUNumTwo"
                                                                                                type="number" min="1">&nbsp;次以上&nbsp;
                                        </template>
                                            <template v-if="gyhindex=='gyhoe'"><input class="txtNum"
                                                                                      v-model="gyhLZOENumTwo"
                                                                                      type="number" min="1">&nbsp;次以上&nbsp;
                                            </template>
                                            <select v-if="gyhindex=='gyhou'" v-model="gyhLZOUSelectTwo" class="secType">
                                                <option value="over">大</option>
                                                <option value="under">小</option>
                                            </select>
                                            <select v-if="gyhindex=='gyhoe'" v-model="gyhLZOESelectTwo" class="secType">
                                                <option value="odd">单</option>
                                                <option value="even">双</option>
                                            </select>
                                            <input type="button" class="btnCheck" @click="tableFilterFun(gyhindex)"
                                                   value="确定">出现的次数为: <span class="sec_count">{{gyhindex=='gyhoe'?gyhLZOEShowNum:gyhLZOUShowNum}}</span>
                                        </td>
                                        <td></td>
                                        <td class="righttd">
                                            <span class="now_l"></span><span class="jinri">{{totalDateTime}}</span>&nbsp;累计：{{gyhindex=='gyhou'?'大':'单'}}(<span
                                            class="count left_count" data="大">{{gyhindex=='gyhou'?gyhLZTotal.over:gyhLZTotal.odd}}</span>)&nbsp;&nbsp;{{gyhindex=='gyhou'?'小':'双'}}(<span
                                            class="count right_count" data="小">{{gyhindex=='gyhou'?gyhLZTotal.under:gyhLZTotal.even}}</span>)
                                            <span class="important"><span class="ifgj">冠亚和</span>&nbsp;<span
                                                class="ifds">{{gyhindex=='gyhou'?'大小':'单双'}}</span>&nbsp;&nbsp;&nbsp;最新</span>
                                        </td>
                                        <td class="tobottom"></td>
                                    </tr>
                                    </tbody>
                                </table>
                                <div v-if="gyhindex=='gyhoe' || gyhindex=='gyhou' " class="item_con" dir="rtl"
                                     style="background-position-x: -29px;">
                                    <table class="lz_table_con" border="0" cellpadding="1" cellspacing="1">
                                        <tbody>
                                        <tr class="tablebox">
                                            <template v-for="(list,index) in gyh">
                                                <template v-for="(item,i) in list">

                                                    <td :class="item[0]=='even'||item[0]=='over'?'bgcolor':''"
                                                        :style="((gyhindex=='gyhoe' && item[0] == gyhLZOESelect && gyhLZOENum<=item.length && gyhLZOEBtnFlag) || (gyhindex=='gyhou' && item[0] == gyhLZOUSelect && gyhLZOUNum<=item.length && gyhLZOUBtnFlag))?'color: rgb(255, 255, 255); background-color: rgb(253, 173, 86);':''">
                                                        <template v-for="(num,k) in item">
                                                            <p :style="item[0]=='even'||item[0]=='over'?'color: rgb(241, 2, 11); display: block;':'font-weight: normal;'"
                                                            >{{$t(num)}}</p>
                                                        </template>

                                                    </td>
                                                </template>

                                            </template>
                                        </tr>
                                        </tbody>
                                    </table>
                                </div>
                            </template>
                        </div>

                    </div>
                </div>

            </div>
        </div>


        <!--单双大小路珠-->
        <div v-show="pageTabSelect=='sdRoad'" class="zhlzbox margt20" style="display:block">
            <div class="listhead">
                <div class="listheadl">
                    <span class="lmms">单双大小路珠</span>
                </div>
                <div class="listheadr">
                    <div class="listheadrl">
                        <span id="today" :class="dayType==0?'checked':''" @click="changeDate(0)">今天</span>
                        <span id="yesterday" :class="dayType==-1?'checked':''" @click="changeDate(-1)">昨天</span>
                        <span id="qianday" :class="dayType==-2?'checked':''" @click="changeDate(-2)">前天</span>
                    </div>
                    <div class="listheadrr">
                        <div class="rightime">
                            <datepicker v-model="dateStr" :format="$moment(dateStr).format('YYYY-MM-DD')"
                                        :language="languages.zh"
                                        @selected="customFormatter" name="uniquename"></datepicker>
                        </div>
                        <div>选择日期&nbsp;</div>
                    </div>
                </div>
            </div>
            <!--单双大小路珠-->
            <div class="listbox">
                <div class="checkbox">
                    <div id="lmms" class="checkbtnzh checkbtnmc">
                        <ul>
                            <li class="title">筛选名次：</li>
                            <!--选中加class checked-->
                            <template v-for="(item,index) in sdRoad.filters.checkBoxList">
                                <li @click="sdRoadCheckBoxFun(index)"
                                    :class="sdRoad.filters.checkBoxList[index]?'sinli checked':'sinli'"><i>1</i>
                                    <a>{{$t(index)}}</a>
                                </li>
                            </template>
                            <li class="zhcheckall" @click="sdRoadCheckBoxAllHandle(true)">全选</li>
                            <li class="zhclear" @click="sdRoadCheckBoxAllHandle(false)">清空</li>
                        </ul>
                        <ul class="xuanzemc">
                            <li class="mctitle">选择路珠：</li>
                            <li :class="sdRoad.filters.selectRoad=='ou'?'lztype selected':'lztype'"
                                @click="sdRoadSelectFun('ou')"><span>2</span><a href="javascript:void(0)">大小</a></li>
                            <li :class="sdRoad.filters.selectRoad=='oe'?'lztype selected':'lztype'"
                                @click="sdRoadSelectFun('oe')"><span>2</span><a href="javascript:void(0)">单双</a></li>
                        </ul>
                    </div>
                </div>
                <div class="listcontent">
                    <div class="box">
                        <div class="dsLZ">

                            <template v-for="(noList,noIndex) in luzhuList">
                                <template v-for="(noArr,arrIndex) in noList">
                                    <table
                                        v-if="((sdRoad.filters.selectRoad=='ou' && arrIndex.indexOf('ou')!=-1) || (sdRoad.filters.selectRoad=='oe' && arrIndex.indexOf('oe')!=-1)) && sdRoad.filters.checkBoxList[noIndex]"
                                        class="lz_table_head  g_w1000">
                                        <tbody>
                                        <tr>
                                            <td>连续出现&nbsp;<input class="txtNum"
                                                                 v-model="sdRoad.filters['numTwo'+noIndex]"
                                                                 type="number" min="1">&nbsp;次以上&nbsp;
                                                <select v-model="sdRoad.filters['selectTwo'+noIndex]" class="secType">
                                                    <option :value="sdRoad.filters.selectRoad=='ou'?'over':'odd'">
                                                        {{sdRoad.filters.selectRoad=='ou'?'大':'单'}}
                                                    </option>
                                                    <option :value="sdRoad.filters.selectRoad=='ou'?'under':'even'">
                                                        {{sdRoad.filters.selectRoad=='ou'?'小':'双'}}
                                                    </option>
                                                </select>
                                                <input type="button" class="btnCheck"
                                                       @click="sdRoadTableFilterFun(arrIndex,noIndex)"
                                                       value="确定">出现的次数为: <span class="sec_count">{{sdRoad.filters['showNum'+noIndex]}}</span>
                                            </td>
                                            <td></td>
                                            <td class="righttd">
                                                <span class="now_l"></span><span class="jinri">{{totalDateTime}}</span>&nbsp;累计：{{sdRoad.filters.selectRoad=='ou'?'大':'单'}}(<span
                                                class="count left_count"
                                                :data="sdRoad.filters.selectRoad=='ou'?'大':'单'">{{sdRoad.filters.selectRoad=='ou'?sdRoad[noIndex].over:sdRoad[noIndex].odd}}</span>)&nbsp;&nbsp;{{sdRoad.filters.selectRoad=='ou'?'小':'双'}}(<span
                                                class="count right_count"
                                                :data="sdRoad.filters.selectRoad=='ou'?'小':'双'">{{sdRoad.filters.selectRoad=='ou'?sdRoad[noIndex].under:sdRoad[noIndex].even}}</span>)
                                                <span class="important"><span
                                                    class="ifgj">{{$t(noIndex)}}</span>&nbsp;<span
                                                    class="ifds">{{$t(sdRoad.filters.selectRoad)}}</span>&nbsp;&nbsp;&nbsp;最新</span>
                                            </td>
                                            <td class="tobottom"></td>
                                        </tr>
                                        </tbody>
                                    </table>
                                    <div
                                        v-if="((sdRoad.filters.selectRoad=='ou' && arrIndex.indexOf('ou')!=-1) || (sdRoad.filters.selectRoad=='oe' && arrIndex.indexOf('oe')!=-1)) && sdRoad.filters.checkBoxList[noIndex]"
                                        class="item_con" dir="rtl" style="background-position-x: -29px;">
                                        <table class="lz_table_con" border="0" cellpadding="1" cellspacing="1">
                                            <tbody>
                                            <tr class="tablebox">
                                                <template v-for="(list,index) in noArr">
                                                    <template v-for="(item,i) in list">
                                                        <td :class="item[0]=='even'||item[0]=='over'?'bgcolor':''"
                                                            :style="item[0] == sdRoad.filters['select'+noIndex] && item.length>=sdRoad.filters['num'+noIndex] && sdRoad.filters['btn'+noIndex]?'color: rgb(255, 255, 255); background-color: rgb(253, 173, 86);':''">
                                                            <template v-for="(num,k) in item">
                                                                <p :style="item[0]=='even'||item[0]=='over'?'color: rgb(241, 2, 11); display: block;':'font-weight: normal;'"
                                                                >{{$t(num)}}</p>
                                                            </template>

                                                        </td>
                                                    </template>

                                                </template>
                                            </tr>
                                            </tbody>
                                        </table>
                                    </div>
                                </template>
                            </template>
                        </div>

                    </div>
                </div>

            </div>
        </div>

        <!--长龙显示-->
        <div class="zhlzbox margt20" style="display: none">
            <div class="listhead">
                <div class="listheadl checkbtndx" id="tabdsdxcl">
                    <span class="zhms xxselected"><i title="0">1</i>单长龙</span>
                    <span class="dxms"><i title="0">2</i>双长龙</span>
                    <span class="lmms"><i title="0">3</i>大长龙</span>
                    <span class="scl"><i title="0">4</i>小长龙</span>
                    <span class="lcl"><i title="1">5</i>龙长龙</span>
                    <span class="hcl"><i title="1">6</i>虎长龙</span>
                </div>

            </div>

            <div class="listbox">
                <div class="checkbox">
                    <div id="dxdsmc" class="checkbtnzh checkbtndx block" style="display: block;">
                        <ul class="xuanzhemc">
                            <li class="mctitle">该页面统计长龙每天出现的条数，例如某日连续开出5期长龙（该形态）共出现10次</li>
                        </ul>
                        <ul class="xuanzhemc">
                            <li class="mctitle">选择名次：</li>
                            <!--选中加checked-->
                            <li class="sinli checked"><span>1</span>
                                <a>冠军</a>
                            </li>
                            <li class="sinli"><span>2</span>
                                <a>亚军</a>
                            </li>
                            <li class="sinli"><span>3</span>
                                <a>第三名</a>
                            </li>
                            <li class="sinli"><span>4</span>
                                <a>第四名</a>
                            </li>
                            <li class="sinli"><span>5</span>
                                <a>第五名</a>
                            </li>
                            <li class="sinli"><span>6</span>
                                <a>第六名</a>
                            </li>
                            <li class="sinli"><span>7</span>
                                <a>第七名</a>
                            </li>
                            <li class="sinli"><span>8</span>
                                <a>第八名</a>
                            </li>
                            <li class="sinli"><span>9</span>
                                <a>第九名</a>
                            </li>
                            <li class="sinli"><span>10</span>
                                <a>第十名</a>
                            </li>
                            <li class="sinli"><span>11</span>
                                <a>冠亚和</a>
                            </li>
                        </ul>
                    </div>

                </div>
                <div class="listcontent">
                    <div class="box">
                        <div id="waitBox" class="bastren w1200">
                            <div id="chartLinediv" style="position:relative;*position:static;">
                                <table id="table_cltj" width="100%" border="0" cellpadding="0" cellspacing="0"
                                       style="display: table;">
                                    <thead>
                                    <tr>
                                        <th rowspan="2" width="123" height="38" class="sanjiao"
                                            style="overflow: hidden;">
                                            <div class="div_out">
                                                <span class="spanc">长龙</span> <span class="spanr">日期</span>
                                                <div class="div_gai">
                                                </div>
                                            </div>
                                        </th>
                                        <th colspan="10" id="headcolspan">开“<span id="cltype">单</span>”长龙出现条数</th>
                                    </tr>
                                    <tr id="qihao">
                                        <th>2期</th>
                                        <th>3期</th>
                                        <th>4期</th>
                                        <th>5期</th>
                                        <th>6期</th>
                                        <th>7期</th>
                                        <th>8期</th>
                                        <th>9期</th>
                                        <th>10期</th>
                                        <th>11期</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <tr>
                                        <td>2020-10-28</td>
                                        <td>20</td>
                                        <td>6</td>
                                        <td>3</td>
                                        <td>2</td>
                                        <td>1</td>
                                        <td>0</td>
                                        <td>1</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                    </tr>
                                    <tr style="background: rgb(245, 245, 245);">
                                        <td>2020-10-27</td>
                                        <td>23</td>
                                        <td>11</td>
                                        <td>5</td>
                                        <td>1</td>
                                        <td>1</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                    </tr>
                                    <tr>
                                        <td>2020-10-26</td>
                                        <td>26</td>
                                        <td>6</td>
                                        <td>3</td>
                                        <td>1</td>
                                        <td>0</td>
                                        <td>1</td>
                                        <td>0</td>
                                        <td>0</td>
                                        <td>0</td>
                                        <td>1</td>
                                    </tr>
                                    <tr style="background: rgb(245, 245, 245);">
                                        <td>2020-10-25</td>
                                        <td>14</td>
                                        <td>7</td>
                                        <td>8</td>
                                        <td>2</td>
                                        <td>2</td>
                                        <td>3</td>
                                        <td>0</td>
                                        <td>0</td>
                                        <td>1</td>
                                        <td>&nbsp;</td>
                                    </tr>
                                    <tr>
                                        <td>2020-10-24</td>
                                        <td>28</td>
                                        <td>10</td>
                                        <td>2</td>
                                        <td>2</td>
                                        <td>1</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                    </tr>
                                    <tr style="background: rgb(245, 245, 245);">
                                        <td>2020-10-23</td>
                                        <td>20</td>
                                        <td>5</td>
                                        <td>6</td>
                                        <td>2</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                    </tr>
                                    <tr>
                                        <td>2020-10-22</td>
                                        <td>17</td>
                                        <td>13</td>
                                        <td>4</td>
                                        <td>3</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                    </tr>
                                    <tr style="background: rgb(245, 245, 245);">
                                        <td>2020-10-21</td>
                                        <td>23</td>
                                        <td>6</td>
                                        <td>2</td>
                                        <td>1</td>
                                        <td>0</td>
                                        <td>1</td>
                                        <td>0</td>
                                        <td>1</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                    </tr>
                                    <tr>
                                        <td>2020-10-20</td>
                                        <td>16</td>
                                        <td>8</td>
                                        <td>8</td>
                                        <td>3</td>
                                        <td>1</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                    </tr>
                                    <tr style="background: rgb(245, 245, 245);">
                                        <td>2020-10-19</td>
                                        <td>17</td>
                                        <td>13</td>
                                        <td>5</td>
                                        <td>1</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                    </tr>
                                    <tr>
                                        <td>2020-10-18</td>
                                        <td>18</td>
                                        <td>7</td>
                                        <td>3</td>
                                        <td>3</td>
                                        <td>0</td>
                                        <td>2</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                    </tr>
                                    <tr style="background: rgb(245, 245, 245);">
                                        <td>2020-10-17</td>
                                        <td>17</td>
                                        <td>9</td>
                                        <td>3</td>
                                        <td>4</td>
                                        <td>1</td>
                                        <td>1</td>
                                        <td>0</td>
                                        <td>1</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                    </tr>
                                    <tr>
                                        <td>2020-10-16</td>
                                        <td>11</td>
                                        <td>11</td>
                                        <td>5</td>
                                        <td>6</td>
                                        <td>1</td>
                                        <td>0</td>
                                        <td>0</td>
                                        <td>2</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                    </tr>
                                    <tr style="background: rgb(245, 245, 245);">
                                        <td>2020-10-15</td>
                                        <td>16</td>
                                        <td>11</td>
                                        <td>6</td>
                                        <td>2</td>
                                        <td>1</td>
                                        <td>2</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                    </tr>
                                    <tr>
                                        <td>2020-10-14</td>
                                        <td>21</td>
                                        <td>11</td>
                                        <td>4</td>
                                        <td>2</td>
                                        <td>1</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                    </tr>
                                    <tr style="background: rgb(245, 245, 245);">
                                        <td>2020-10-13</td>
                                        <td>28</td>
                                        <td>11</td>
                                        <td>3</td>
                                        <td>0</td>
                                        <td>1</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                    </tr>
                                    <tr>
                                        <td>2020-10-12</td>
                                        <td>18</td>
                                        <td>7</td>
                                        <td>5</td>
                                        <td>5</td>
                                        <td>0</td>
                                        <td>0</td>
                                        <td>0</td>
                                        <td>0</td>
                                        <td>1</td>
                                        <td>&nbsp;</td>
                                    </tr>
                                    <tr style="background: rgb(245, 245, 245);">
                                        <td>2020-10-11</td>
                                        <td>19</td>
                                        <td>4</td>
                                        <td>6</td>
                                        <td>1</td>
                                        <td>0</td>
                                        <td>0</td>
                                        <td>0</td>
                                        <td>1</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                    </tr>
                                    <tr>
                                        <td>2020-10-10</td>
                                        <td>21</td>
                                        <td>10</td>
                                        <td>6</td>
                                        <td>1</td>
                                        <td>2</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                    </tr>
                                    <tr style="background: rgb(245, 245, 245);">
                                        <td>2020-10-09</td>
                                        <td>15</td>
                                        <td>10</td>
                                        <td>1</td>
                                        <td>2</td>
                                        <td>1</td>
                                        <td>3</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                    </tr>
                                    <tr>
                                        <td>2020-10-08</td>
                                        <td>18</td>
                                        <td>9</td>
                                        <td>3</td>
                                        <td>1</td>
                                        <td>2</td>
                                        <td>2</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                    </tr>
                                    <tr style="background: rgb(245, 245, 245);">
                                        <td>2020-10-07</td>
                                        <td>19</td>
                                        <td>10</td>
                                        <td>4</td>
                                        <td>3</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                    </tr>
                                    <tr>
                                        <td>2020-10-06</td>
                                        <td>18</td>
                                        <td>8</td>
                                        <td>6</td>
                                        <td>0</td>
                                        <td>0</td>
                                        <td>0</td>
                                        <td>1</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                    </tr>
                                    <tr style="background: rgb(245, 245, 245);">
                                        <td>2020-10-05</td>
                                        <td>16</td>
                                        <td>4</td>
                                        <td>5</td>
                                        <td>0</td>
                                        <td>3</td>
                                        <td>1</td>
                                        <td>1</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                    </tr>
                                    <tr>
                                        <td>2020-10-04</td>
                                        <td>22</td>
                                        <td>10</td>
                                        <td>3</td>
                                        <td>0</td>
                                        <td>2</td>
                                        <td>0</td>
                                        <td>2</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                    </tr>
                                    <tr style="background: rgb(245, 245, 245);">
                                        <td>2020-10-03</td>
                                        <td>18</td>
                                        <td>9</td>
                                        <td>3</td>
                                        <td>1</td>
                                        <td>3</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                    </tr>
                                    <tr>
                                        <td>2020-10-02</td>
                                        <td>16</td>
                                        <td>8</td>
                                        <td>6</td>
                                        <td>2</td>
                                        <td>1</td>
                                        <td>0</td>
                                        <td>0</td>
                                        <td>1</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                    </tr>
                                    <tr style="background: rgb(245, 245, 245);">
                                        <td>2020-10-01</td>
                                        <td>21</td>
                                        <td>9</td>
                                        <td>3</td>
                                        <td>3</td>
                                        <td>1</td>
                                        <td>3</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                    </tr>
                                    <tr>
                                        <td>2020-09-30</td>
                                        <td>18</td>
                                        <td>11</td>
                                        <td>8</td>
                                        <td>4</td>
                                        <td>1</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                    </tr>
                                    <tr style="background: rgb(245, 245, 245);">
                                        <td>2020-09-29</td>
                                        <td>22</td>
                                        <td>10</td>
                                        <td>3</td>
                                        <td>2</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                    </tr>
                                    <tr>
                                        <td>2020-09-28</td>
                                        <td>17</td>
                                        <td>9</td>
                                        <td>4</td>
                                        <td>1</td>
                                        <td>3</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                    </tr>
                                    </tbody>
                                </table>
                            </div>
                            <div id="chartbottom" style="display: none;">
                            </div>
                        </div>
                    </div>
                </div>
            </div>


        </div>

        <!--冷热分析-->
        <div v-show="pageTabSelect=='lrfx'" class="zhlzbox margt20" style="display: none">
            <div class="listhead">
                <div class="listheadl">
                    <span class="lmms"><i>3</i>冷热分析</span>
                </div>
                <!--<div class="listheadr">-->
                <!--<div class="listheadrl">-->
                <!--<span id="today" :class="dayType==0?'checked':''" @click="changeDate(0)">今天</span>-->
                <!--<span id="yesterday" :class="dayType==-1?'checked':''" @click="changeDate(-1)">昨天</span>-->
                <!--<span id="qianday" :class="dayType==-2?'checked':''" @click="changeDate(-2)">前天</span>-->
                <!--</div>-->
                <!--<div class="listheadrr">-->
                <!--<div class="rightime">-->
                <!--<datepicker v-model="dateStr" :format="$moment(dateStr).format('YYYY-MM-DD')"-->
                <!--:language="languages.zh"-->
                <!--@selected="customFormatter" name="uniquename"></datepicker>-->
                <!--</div>-->
                <!--<div>选择日期&nbsp;</div>-->
                <!--</div>-->
                <!--</div>-->
            </div>
            <div class="listbox">
                <div class="checkbox">
                    <div id="lmms" class="checkbtnzh checkbtnmc noboder">
                        <!--<ul class="lrul">-->
                        <!--<li class="sinli"><i>1</i>-->
                        <!--<a href="javascript:void(0)">显示热号码出现次数</a>-->
                        <!--</li>-->
                        <!--</ul>-->
                        <ul class="titleinfor">
                            <li class="paddl0 inforcolor">
                                近20期内 热码：出现频繁、活跃的号码；冷码：出现频率低甚至没有出现的号码；温码：介于冷热之间的号码。
                            </li>
                        </ul>
                    </div>
                </div>
                <div class="listcontent nomargin">
                    <div class="box">
                        <div class="bastren w1200 noboder">
                            <table id="lensetable" width="" border="0" cellpadding="0" cellspacing="0">
                                <tbody>
                                <tr height="50">
                                    <th>名次</th>
                                    <th>热</th>
                                    <th>温</th>
                                    <th>冷</th>
                                </tr>
                                <tr height="50" v-for="rif,index in rlfxList">
                                    <td width="100">
                                        <span v-if="index==0">冠军</span>
                                        <span v-if="index==1">亚军</span>
                                        <span v-if="index!=1||index!=2">{{index+1}}</span> 名
                                    </td>
                                    <td width="300">
                                        <ul v-for="r in rif" v-if="r[1]>=4">
                                            <li :class="r[0]==10?'numsm'+r[0]:'numsm0'+r[0]">
                                            </li>
                                        </ul>
                                    </td>
                                    <td width="400">
                                        <ul v-for="r in rif" v-if="r[1]<4 && r[1]>=2">
                                            <li :class="r[0]==10?'numsm'+r[0]:'numsm0'+r[0]">
                                            </li>
                                        </ul>
                                    </td>
                                    <td width="400">
                                        <ul v-for="r in rif" v-if="r[1]<2">
                                            <li :class="r[0]==10?'numsm'+r[0]:'numsm0'+r[0]">
                                            </li>
                                        </ul>
                                    </td>
                                </tr>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <!--单双大小历史-->
        <div v-show="pageTabSelect=='dsdxls'" class="zhlzbox margt20" style="display:block">
            <div class="listhead">
                <div class="listheadl">
                    <span class="lmms"><i>3</i>单双大小历史</span>
                </div>
            </div>
            <div class="listbox">
                <div class="listcontent nomargin">
                    <div class="box">
                        <div class="bastren w1200 noboder">
                            <table id="dsdxlstable" width="100%" border="0" cellpadding="0" cellspacing="0">
                                <thead>
                                <tr class="thead" style="height: 40px; width: 100%; background-color: #ECECEC;">
                                    <td rowspan="2">日期</td>
                                    <td class="odd" colspan="4">冠军</td>
                                    <td colspan="4">亚军</td>
                                    <td class="odd" colspan="4">第三名</td>
                                    <td colspan="4">第四名</td>
                                    <td class="odd" colspan="4">第五名</td>
                                    <td colspan="4">第六名</td>
                                    <td class="odd" colspan="4">第七名</td>
                                    <td colspan="4">第八名</td>
                                    <td class="odd" colspan="4">第九名</td>
                                    <td colspan="4">第十名</td>
                                </tr>
                                <tr class="thead smthead">
                                    <template v-for="i in 10">
                                        <th width="26">大</th>
                                        <th width="26">小</th>
                                        <th width="26">单</th>
                                        <th width="26">双</th>
                                    </template>
                                </tr>
                                </thead>
                                <tbody>
                                <tr height="38">
                                    <td width="100">{{dateStr}}</td>
                                    <template v-for="dxds,index in dxdsHisList">
                                        <td>{{dxds.noda}}</td>
                                        <td>{{dxds.noxiao}}</td>
                                        <td>{{dxds.nodan}}</td>
                                        <td>{{dxds.nosuan}}</td>
                                    </template>
                                </tr>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <!--两面数据统计-->
        <div v-show="pageTabSelect=='lmsjtj'" class="zhlzbox margt20" style="display:block">
            <div class="listhead">
                <div class="listheadl">
                    <span class="lmms"><i>3</i>两面数据统计</span>
                </div>
                <div class="listheadr">
                </div>
            </div>
            <div class="listbox">
                <div class="listbox_p">
                    <p><i>说明：</i>以每10期为一组，统计最近20组中单双大小的出现次数。</p><br/>
                </div>
                <div class="checkbox">
                    <div id="xzmc" class="checkbtnzh checkbtnmc">
                        <ul class="xuanzhemc">
                            <!--<li class="mctitle">选择名次：</li>-->
                            <li class="sinli" :class="lmsjBnt==0?'selected':''"><span>1</span>
                                <a href="javascript:void(0)" @click="lmsjtjCustom(0)">冠军</a>
                            </li>
                            <li class="sinli" :class="lmsjBnt==1?'selected':''"><span>2</span>
                                <a href="javascript:void(0)" @click="lmsjtjCustom(1)">亚军</a>
                            </li>
                            <li class="sinli" :class="lmsjBnt==2?'selected':''"><span>3</span>
                                <a href="javascript:void(0)" @click="lmsjtjCustom(2)">第三名</a>
                            </li>
                            <li class="sinli" :class="lmsjBnt==3?'selected':''"><span>4</span>
                                <a href="javascript:void(0)" @click="lmsjtjCustom(3)">第四名</a>
                            </li>
                            <li class="sinli" :class="lmsjBnt==4?'selected':''"><span>5</span>
                                <a href="javascript:void(0)" @click="lmsjtjCustom(4)">第五名</a>
                            </li>
                            <li class="sinli" :class="lmsjBnt==5?'selected':''"><span>5</span>
                                <a href="javascript:void(0)" @click="lmsjtjCustom(5)">第六名</a>
                            </li>
                            <li class="sinli" :class="lmsjBnt==6?'selected':''"><span>5</span>
                                <a href="javascript:void(0)" @click="lmsjtjCustom(6)">第七名</a>
                            </li>
                            <li class="sinli" :class="lmsjBnt==7?'selected':''"><span>5</span>
                                <a href="javascript:void(0)" @click="lmsjtjCustom(7)">第八名</a>
                            </li>
                            <li class="sinli" :class="lmsjBnt==8?'selected':''"><span>5</span>
                                <a href="javascript:void(0)" @click="lmsjtjCustom(8)">第九名</a>
                            </li>
                            <li class="sinli" :class="lmsjBnt==9?'selected':''"><span>5</span>
                                <a href="javascript:void(0)" @click="lmsjtjCustom(9)">第十名</a>
                            </li>
                        </ul>
                    </div>
                </div>
                <div class="listcontent">
                    <div class="box">
                        <div class="bastren w1200 noboder">
                            <table id="lmsjtable" width="" border="0" cellpadding="0" cellspacing="0">
                                <thead>
                                <tr height="50" class="mc1">
                                    <th width="400">日期</th>
                                    <th width="300">单</th>
                                    <th width="300">双</th>
                                    <th width="300">大</th>
                                    <th width="300">小</th>
                                </tr>
                                </thead>
                                <tbody v-for="item in lmsjList">
                                <tr height="50" class="mc1">
                                    <td width="400">{{$moment(item.startDate.actionTimeStr).format('YYYY-MM-DD')}}&nbsp;&nbsp;&nbsp;&nbsp;{{item.endDate.gameNo}}&nbsp;-&nbsp;{{item.startDate.gameNo}}期
                                    </td>
                                    <td width="300">{{item.dan}}</td>
                                    <td width="300">{{item.suan}}</td>
                                    <td width="300">{{item.da}}</td>
                                    <td width="300">{{item.xiao}}</td>
                                </tr>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
                <br>
                <br>
                <br>
            </div>
        </div>

        <!--龙虎统计-->
        <div v-show="pageTabSelect=='lhtj'" class="zhlzbox margt20" style="display:block">
            <div class="listhead">
                <div class="listheadl">
                    <span class="lmms"><i>3</i>龙虎统计</span>
                </div>
            </div>
            <div class="listbox">
                <div class="listbox_p">
                    <p><i>说明：</i>统计开奖号码的龙虎出现次数</p>
                    <br/>
                </div>
                <div class="listcontent nomargin">
                    <div class="box">
                        <div class="bastren w1200 noboder">
                            <table id="lhtjtable" width="" border="0" cellpadding="0" cellspacing="0">
                                <tbody>
                                <tr height="50">
                                    <th width="300">日期</th>
                                    <th width="98">冠军龙</th>
                                    <th width="98">冠军虎</th>
                                    <th width="98">亚军龙</th>
                                    <th width="98">亚军虎</th>
                                    <th width="98">第三名龙</th>
                                    <th width="98">第三名虎</th>
                                    <th width="98">第四名龙</th>
                                    <th width="98">第四名虎</th>
                                    <th width="98">第五名龙</th>
                                    <th width="98">第五名虎</th>
                                </tr>
                                <tr height="50">
                                    <td width="300">{{dateStr}}&nbsp;&nbsp;&nbsp;&nbsp;</td>
                                    <td width="98">{{lhtjList.no1l}}</td>
                                    <td width="98">{{lhtjList.no1h}}</td>
                                    <td width="98">{{lhtjList.no2l}}</td>
                                    <td width="98">{{lhtjList.no2h}}</td>
                                    <td width="98">{{lhtjList.no3l}}</td>
                                    <td width="98">{{lhtjList.no3h}}</td>
                                    <td width="98">{{lhtjList.no4l}}</td>
                                    <td width="98">{{lhtjList.no4h}}</td>
                                    <td width="98">{{lhtjList.no5l}}</td>
                                    <td width="98">{{lhtjList.no5h}}</td>
                                </tr>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <!--冠亚和两面历史-->
        <div v-show="pageTabSelect=='gyhlmls'" class="zhlzbox margt20" style="display:block">
            <div class="listhead">
                <div class="listheadl">
                    <span class="lmms"><i>3</i>冠亚和两面历史</span>
                </div>
                <!--<div class="listheadr datedouble">
                    <div class="listheadrl">
                        <span id="today" class="checked searchbtn">&nbsp;查询&nbsp;</span>
                    </div>
                    <div class="listheadrr">
                        <div class="rightime">
                            <div id="dateframe">
                                <input type="text" id="date" class="date" placeholder="">
                                <div id="datebox"></div>
                                <i class="dropicond"></i>
                            </div>
                        </div>
                        <div>&nbsp;至&nbsp;</div>
                        <div class="rightime">
                            <div id="dateframe">
                                <input type="text" class="date" placeholder="">
                                <div id="datebox2"></div>
                                <i class="dropicond"></i>
                            </div>
                        </div>
                        <div>选择日期&nbsp;</div>
                    </div>
                </div>-->
            </div>
            <div class="listbox">
                <div class="listbox_p">
                    <p><i>说明：</i>统计冠亚和每天单双大小的出现次数.</p>
                    <br/>
                </div>
                <div class="listcontent nomargin">
                    <div class="box">
                        <div class="bastren w1200 noboder">
                            <table id="gyhlmlstable" width="100%" border="0" cellpadding="0" cellspacing="0">
                                <tbody>
                                <tr height="48" class="thead">
                                    <th>日期</th>
                                    <th>冠亚和大</th>
                                    <th>冠亚和小</th>
                                    <th>冠亚和单</th>
                                    <th>冠亚和双</th>
                                </tr>
                                <tr height="40">
                                    <td width="300" class="smthead">{{dateStr}}</td>
                                    <td width="200">{{gyhlmList.gyhda}}</td>
                                    <td width="200">{{gyhlmList.gyhxiao}}</td>
                                    <td width="200">{{gyhlmList.gyhdan}}</td>
                                    <td width="200">{{gyhlmList.gyhsuan}}</td>
                                </tr>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <!--今日号码统计-->
        <div v-show="pageTabSelect=='jrhmtj'" class="zhlzbox margt20" style="display:block">
            <div class="listbox">
                <div class="listbox_p">
                    <div class="checkbox">
                        <div id="zhms" class="checkbtnzh">
                            <ul class="zhmslz">
                                <li class="title">参数设置：</li>
                                <li @click="jrhmtjType.red?jrhmtjType.red=false:jrhmtjType.red=true"
                                    :class="jrhmtjType.red?'lztype checked':'lztype'"><i>2</i><a href="javascript:void(0)">当数值为 15 - 30 为<span style="color:red;">红</span>色</a></li>
                                <li @click="jrhmtjType.blue?jrhmtjType.blue=false:jrhmtjType.blue=true"
                                    :class="jrhmtjType.blue?'lztype checked':'lztype'"><i>2</i><a href="javascript:void(0)">当数值为 31 - 40 为<span style="color:blue;">蓝</span>色</a></li>
                                <li @click="jrhmtjType.green?jrhmtjType.green=false:jrhmtjType.green=true"
                                    :class="jrhmtjType.green?'lztype checked':'lztype'"><i>2</i><a href="javascript:void(0)">当数值为 41 以上 为<span style="color:green;">绿</span>色</a></li>
                            </ul>
                        </div>
                    </div>
                    <br/>
                </div>
                <div class="listcontent nomargin">
                    <div class="box">
                        <div id="waitBox" class="bastren w1200">
                            <div id="chartLinediv" style="position:relative;*position:static;">
                                <table id="table_cltj" width="100%" border="0" cellpadding="0" cellspacing="0"
                                       style="display: table;">
                                    <thead>
                                    <tr>
                                        <th rowspan="2">号码</th>
                                        <th colspan="2" height="50">冠 军</th>
                                        <th colspan="2">亚 军</th>
                                        <th colspan="2">第三名</th>
                                        <th colspan="2">第四名</th>
                                        <th colspan="2">第五名</th>
                                        <th colspan="2">第六名</th>
                                        <th colspan="2">第七名</th>
                                        <th colspan="2">第八名</th>
                                        <th colspan="2">第九名</th>
                                        <th colspan="2">第十名</th>
                                    </tr>
                                    <tr class="smth">
                                        <th height="45">总开</th>
                                        <th>未开</th>
                                        <th>总开</th>
                                        <th>未开</th>
                                        <th>总开</th>
                                        <th>未开</th>
                                        <th>总开</th>
                                        <th>未开</th>
                                        <th>总开</th>
                                        <th>未开</th>
                                        <th>总开</th>
                                        <th>未开</th>
                                        <th>总开</th>
                                        <th>未开</th>
                                        <th>总开</th>
                                        <th>未开</th>
                                        <th>总开</th>
                                        <th>未开</th>
                                        <th>总开</th>
                                        <th>未开</th>
                                    </tr>
                                    </thead>
                                    <tbody v-for="item,index in jrhmtjList">
                                    <tr>
                                        <td><span style="display:inline-block;width:26px;height:26px"
                                                  :class="index==9?'numss10':'numss0'+(index+1)"></span></td>
                                        <td :style="jrhmtjColorChange(item.zk1)">{{item.zk1}}</td>
                                        <td :style="jrhmtjColorChange(item.wk1)">{{item.wk1}}</td>
                                        <td :style="jrhmtjColorChange(item.zk2)">{{item.zk2}}</td>
                                        <td :style="jrhmtjColorChange(item.wk2)">{{item.wk2}}</td>
                                        <td :style="jrhmtjColorChange(item.zk3)">{{item.zk3}}</td>
                                        <td :style="jrhmtjColorChange(item.wk3)">{{item.wk3}}</td>
                                        <td :style="jrhmtjColorChange(item.zk4)">{{item.zk4}}</td>
                                        <td :style="jrhmtjColorChange(item.wk4)">{{item.wk4}}</td>
                                        <td :style="jrhmtjColorChange(item.zk5)">{{item.zk5}}</td>
                                        <td :style="jrhmtjColorChange(item.wk5)">{{item.wk5}}</td>
                                        <td :style="jrhmtjColorChange(item.zk6)">{{item.zk6}}</td>
                                        <td :style="jrhmtjColorChange(item.wk6)">{{item.wk6}}</td>
                                        <td :style="jrhmtjColorChange(item.zk7)">{{item.zk7}}</td>
                                        <td :style="jrhmtjColorChange(item.wk7)">{{item.wk7}}</td>
                                        <td :style="jrhmtjColorChange(item.zk8)">{{item.zk8}}</td>
                                        <td :style="jrhmtjColorChange(item.wk8)">{{item.wk8}}</td>
                                        <td :style="jrhmtjColorChange(item.zk9)">{{item.zk9}}</td>
                                        <td :style="jrhmtjColorChange(item.wk9)">{{item.wk9}}</td>
                                        <td :style="jrhmtjColorChange(item.zk10)">{{item.zk10}}</td>
                                        <td :style="jrhmtjColorChange(item.wk10)">{{item.wk10}}</td>
                                    </tr>
                                    </tbody>
                                </table>
                            </div>
                            <div id="chartbottom" style="display: none;">
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <div v-show="pageTabSelect=='cltj'" class="zhlzbox margt20" style="display:block">
            <div class="listhead">
                <div class="listheadl checkbtndx" id="tabdsdxcl">
                    <span class="zhms xxselected"><i title="0">1</i>单长龙</span>
                    <span class="dxms"><i title="0">2</i>双长龙</span>
                    <span class="lmms" style="top: 0;left: 340px;"><i title="0">3</i>大长龙</span>
                    <span class="scl"><i title="0">4</i>小长龙</span>
                    <span class="lcl"><i title="1">5</i>龙长龙</span>
                    <span class="hcl"><i title="1">6</i>虎长龙</span>
                </div>
            </div>
            <div class="listbox">
                <div class="checkbox">
                    <div id="dxdsmc" class="checkbtnzh checkbtndx block" style="display: block;">
                        <ul class="xuanzhemc smBgcolor">
                            <li class="mctitle">
                                <div>说明：</div>
                                该页面统计长龙每天出现的条数，例如某日连续开出5期长龙（该形态）共出现10次
                            </li>
                        </ul>
                        <ul class="xuanzhemc">
                            <li class="mctitle">选择名次：</li>
                            <li class="sinli checked"><span>1</span>
                                <a href="javascript:void(0)">冠军</a>
                            </li>
                            <li class="sinli"><span>2</span>
                                <a href="javascript:void(0)">亚军</a>
                            </li>
                            <li class="sinli"><span>3</span>
                                <a href="javascript:void(0)">第三名</a>
                            </li>
                            <li class="sinli"><span>4</span>
                                <a href="javascript:void(0)">第四名</a>
                            </li>
                            <li class="sinli"><span>5</span>
                                <a href="javascript:void(0)">第五名</a>
                            </li>
                            <li class="sinli"><span>6</span>
                                <a href="javascript:void(0)">第六名</a>
                            </li>
                            <li class="sinli"><span>7</span>
                                <a href="javascript:void(0)">第七名</a>
                            </li>
                            <li class="sinli"><span>8</span>
                                <a href="javascript:void(0)">第八名</a>
                            </li>
                            <li class="sinli"><span>9</span>
                                <a href="javascript:void(0)">第九名</a>
                            </li>
                            <li class="sinli"><span>10</span>
                                <a href="javascript:void(0)">第十名</a>
                            </li>
                            <li class="sinli"><span>11</span>
                                <a href="javascript:void(0)">冠亚和</a>
                            </li>
                        </ul>
                    </div>
                    <div id="lhmc" class="checkbtnzh checkbtndx">
                        <ul class="xuanzhemc">
                            <li class="mctitle">该页面统计长龙每天出现的条数，例如某日连续开出5期长龙（该形态）共出现10次</li>
                        </ul>
                        <ul class="xuanzhemc">
                            <li class="mctitle">选择名次：</li>
                            <li class="sinli checked"><span>1</span>
                                <a href="javascript:void(0)">冠军</a>
                            </li>
                            <li class="sinli"><span>2</span>
                                <a href="javascript:void(0)">亚军</a>
                            </li>
                            <li class="sinli"><span>3</span>
                                <a href="javascript:void(0)">第三名</a>
                            </li>
                            <li class="sinli"><span>4</span>
                                <a href="javascript:void(0)">第四名</a>
                            </li>
                            <li class="sinli"><span>5</span>
                                <a href="javascript:void(0)">第五名</a>
                            </li>
                        </ul>
                    </div>

                </div>
                <div class="listcontent">
                    <div class="box">
                        <div id="waitBox" class="bastren w1200">
                            <div id="chartLinediv" style="position:relative;*position:static;">
                                <table id="table_cltj" width="100%" border="0" cellpadding="0" cellspacing="0"
                                       style="display: table;">
                                    <thead>
                                    <tr>
                                        <th rowspan="2" width="123" height="38" class="sanjiao"
                                            style="overflow: hidden;">
                                            <div class="div_out">
                                                <span class="spanc">长龙</span> <span class="spanr">日期</span>
                                                <div class="div_gai">
                                                </div>
                                            </div>
                                        </th>
                                        <th colspan="14" id="headcolspan">开“<span id="cltype">单</span>”长龙出现条数</th>
                                    </tr>
                                    <tr id="qihao">
                                        <th>2期</th>
                                        <th>3期</th>
                                        <th>4期</th>
                                        <th>5期</th>
                                        <th>6期</th>
                                        <th>7期</th>
                                        <th>8期</th>
                                        <th>9期</th>
                                        <th>10期</th>
                                        <th>11期</th>
                                        <th>12期</th>
                                        <th>13期</th>
                                        <th>14期</th>
                                        <th>15期</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <tr>
                                        <td>2020-11-20</td>
                                        <td>11</td>
                                        <td>6</td>
                                        <td>4</td>
                                        <td>2</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                    </tr>
                                    <tr style="background: rgb(245, 245, 245);">
                                        <td>2020-11-19</td>
                                        <td>19</td>
                                        <td>6</td>
                                        <td>5</td>
                                        <td>2</td>
                                        <td>2</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                    </tr>
                                    <tr>
                                        <td>2020-11-18</td>
                                        <td>23</td>
                                        <td>8</td>
                                        <td>7</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                    </tr>
                                    <tr style="background: rgb(245, 245, 245);">
                                        <td>2020-11-17</td>
                                        <td>19</td>
                                        <td>13</td>
                                        <td>2</td>
                                        <td>2</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                    </tr>
                                    <tr>
                                        <td>2020-11-16</td>
                                        <td>15</td>
                                        <td>10</td>
                                        <td>6</td>
                                        <td>5</td>
                                        <td>0</td>
                                        <td>0</td>
                                        <td>1</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                    </tr>
                                    <tr style="background: rgb(245, 245, 245);">
                                        <td>2020-11-15</td>
                                        <td>17</td>
                                        <td>6</td>
                                        <td>9</td>
                                        <td>4</td>
                                        <td>0</td>
                                        <td>2</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                    </tr>
                                    <tr>
                                        <td>2020-11-14</td>
                                        <td>18</td>
                                        <td>8</td>
                                        <td>3</td>
                                        <td>1</td>
                                        <td>2</td>
                                        <td>0</td>
                                        <td>0</td>
                                        <td>0</td>
                                        <td>1</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                    </tr>
                                    <tr style="background: rgb(245, 245, 245);">
                                        <td>2020-11-13</td>
                                        <td>19</td>
                                        <td>5</td>
                                        <td>3</td>
                                        <td>3</td>
                                        <td>0</td>
                                        <td>1</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                    </tr>
                                    <tr>
                                        <td>2020-11-12</td>
                                        <td>25</td>
                                        <td>8</td>
                                        <td>4</td>
                                        <td>1</td>
                                        <td>1</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                    </tr>
                                    <tr style="background: rgb(245, 245, 245);">
                                        <td>2020-11-11</td>
                                        <td>16</td>
                                        <td>8</td>
                                        <td>9</td>
                                        <td>2</td>
                                        <td>0</td>
                                        <td>0</td>
                                        <td>0</td>
                                        <td>1</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                    </tr>
                                    <tr>
                                        <td>2020-11-10</td>
                                        <td>21</td>
                                        <td>13</td>
                                        <td>3</td>
                                        <td>2</td>
                                        <td>0</td>
                                        <td>1</td>
                                        <td>1</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                    </tr>
                                    <tr style="background: rgb(245, 245, 245);">
                                        <td>2020-11-09</td>
                                        <td>16</td>
                                        <td>10</td>
                                        <td>3</td>
                                        <td>4</td>
                                        <td>2</td>
                                        <td>1</td>
                                        <td>1</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                    </tr>
                                    <tr>
                                        <td>2020-11-08</td>
                                        <td>15</td>
                                        <td>6</td>
                                        <td>4</td>
                                        <td>1</td>
                                        <td>2</td>
                                        <td>0</td>
                                        <td>1</td>
                                        <td>1</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                    </tr>
                                    <tr style="background: rgb(245, 245, 245);">
                                        <td>2020-11-07</td>
                                        <td>21</td>
                                        <td>9</td>
                                        <td>5</td>
                                        <td>0</td>
                                        <td>1</td>
                                        <td>1</td>
                                        <td>0</td>
                                        <td>1</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                    </tr>
                                    <tr>
                                        <td>2020-11-06</td>
                                        <td>20</td>
                                        <td>9</td>
                                        <td>6</td>
                                        <td>1</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                    </tr>
                                    <tr style="background: rgb(245, 245, 245);">
                                        <td>2020-11-05</td>
                                        <td>16</td>
                                        <td>12</td>
                                        <td>5</td>
                                        <td>1</td>
                                        <td>1</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                    </tr>
                                    <tr>
                                        <td>2020-11-04</td>
                                        <td>23</td>
                                        <td>9</td>
                                        <td>5</td>
                                        <td>1</td>
                                        <td>3</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                    </tr>
                                    <tr style="background: rgb(245, 245, 245);">
                                        <td>2020-11-03</td>
                                        <td>21</td>
                                        <td>12</td>
                                        <td>5</td>
                                        <td>2</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                    </tr>
                                    <tr>
                                        <td>2020-11-02</td>
                                        <td>12</td>
                                        <td>12</td>
                                        <td>6</td>
                                        <td>3</td>
                                        <td>0</td>
                                        <td>0</td>
                                        <td>1</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                    </tr>
                                    <tr style="background: rgb(245, 245, 245);">
                                        <td>2020-11-01</td>
                                        <td>16</td>
                                        <td>12</td>
                                        <td>6</td>
                                        <td>1</td>
                                        <td>1</td>
                                        <td>0</td>
                                        <td>1</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                    </tr>
                                    <tr>
                                        <td>2020-10-31</td>
                                        <td>21</td>
                                        <td>4</td>
                                        <td>7</td>
                                        <td>1</td>
                                        <td>1</td>
                                        <td>2</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                    </tr>
                                    <tr style="background: rgb(245, 245, 245);">
                                        <td>2020-10-30</td>
                                        <td>16</td>
                                        <td>9</td>
                                        <td>1</td>
                                        <td>3</td>
                                        <td>3</td>
                                        <td>1</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                    </tr>
                                    <tr>
                                        <td>2020-10-29</td>
                                        <td>18</td>
                                        <td>6</td>
                                        <td>2</td>
                                        <td>4</td>
                                        <td>1</td>
                                        <td>0</td>
                                        <td>0</td>
                                        <td>1</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                    </tr>
                                    <tr style="background: rgb(245, 245, 245);">
                                        <td>2020-10-28</td>
                                        <td>18</td>
                                        <td>6</td>
                                        <td>6</td>
                                        <td>3</td>
                                        <td>0</td>
                                        <td>1</td>
                                        <td>0</td>
                                        <td>0</td>
                                        <td>0</td>
                                        <td>1</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                    </tr>
                                    <tr>
                                        <td>2020-10-27</td>
                                        <td>20</td>
                                        <td>4</td>
                                        <td>6</td>
                                        <td>0</td>
                                        <td>1</td>
                                        <td>1</td>
                                        <td>1</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                    </tr>
                                    <tr style="background: rgb(245, 245, 245);">
                                        <td>2020-10-26</td>
                                        <td>11</td>
                                        <td>11</td>
                                        <td>3</td>
                                        <td>2</td>
                                        <td>0</td>
                                        <td>0</td>
                                        <td>1</td>
                                        <td>0</td>
                                        <td>1</td>
                                        <td>0</td>
                                        <td>0</td>
                                        <td>0</td>
                                        <td>0</td>
                                        <td>1</td>
                                    </tr>
                                    <tr>
                                        <td>2020-10-25</td>
                                        <td>20</td>
                                        <td>8</td>
                                        <td>2</td>
                                        <td>1</td>
                                        <td>1</td>
                                        <td>1</td>
                                        <td>1</td>
                                        <td>0</td>
                                        <td>0</td>
                                        <td>0</td>
                                        <td>0</td>
                                        <td>1</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                    </tr>
                                    <tr style="background: rgb(245, 245, 245);">
                                        <td>2020-10-24</td>
                                        <td>20</td>
                                        <td>10</td>
                                        <td>2</td>
                                        <td>1</td>
                                        <td>1</td>
                                        <td>0</td>
                                        <td>1</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                    </tr>
                                    <tr>
                                        <td>2020-10-23</td>
                                        <td>28</td>
                                        <td>11</td>
                                        <td>6</td>
                                        <td>1</td>
                                        <td>1</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                    </tr>
                                    <tr style="background: rgb(245, 245, 245);">
                                        <td>2020-10-22</td>
                                        <td>18</td>
                                        <td>7</td>
                                        <td>5</td>
                                        <td>1</td>
                                        <td>0</td>
                                        <td>1</td>
                                        <td>1</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                    </tr>
                                    <tr>
                                        <td>2020-10-21</td>
                                        <td>17</td>
                                        <td>7</td>
                                        <td>6</td>
                                        <td>1</td>
                                        <td>2</td>
                                        <td>0</td>
                                        <td>1</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                    </tr>
                                    </tbody>
                                </table>
                            </div>
                            <div id="chartbottom" style="display: none;">
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>


        <!--&lt;!&ndash;冠亚和走势&ndash;&gt;-->
        <!--<div class="zhlzbox margt20" style="display:block">-->
            <!--<div class="listhead">-->
                <!--<div class="listheadl">-->
                    <!--<span class="lmms"><i>3</i>冠亚和走势</span>-->
                <!--</div>-->
                <!--<div class="listheadr" style="width: 1000px;">-->
                    <!--<div class="listheadrl" style="width: 380px">-->
                        <!--<span id="today" class="checked">今天</span>-->
                        <!--<span id="yesterday">昨天</span>-->
                        <!--<span id="qianday">前天</span>-->
                        <!--<span id="thirty">最近30期</span>-->
                        <!--<span id="sixty">最近60期</span>-->
                        <!--<span id="ninety">最近90期</span>-->
                    <!--</div>-->
                    <!--<div class="listheadrr" style="width: 300px">-->
                        <!--<div class="rightime">-->
                            <!--<datepicker v-model="dateStr" :format="$moment(dateStr).format('YYYY-MM-DD')"-->
                                        <!--:language="languages.zh"-->
                                        <!--@selected="customFormatter" name="uniquename"></datepicker>-->
                        <!--</div>-->
                        <!--<div>选择日期&nbsp;</div>-->
                    <!--</div>-->
                <!--</div>-->
            <!--</div>-->
            <!--<div class="listbox">-->
                <!--<div class="checkbox">-->
                    <!--<div id="biaozxz" class="checkbtnzh checkbtnmc">-->
                        <!--<ul>-->
                            <!--<li class="title">标注选择：</li>-->
                            <!--<li class="sinli checked"><i>1</i>-->
                                <!--<a>遗漏</a>-->
                            <!--</li>-->
                            <!--<li class="sinli checked"><i>2</i>-->
                                <!--<a>拆线</a>-->
                            <!--</li>-->
                            <!--<li class="sinli"><i>3</i>-->
                                <!--<a>遗漏分成</a>-->
                            <!--</li>-->
                            <!--<li class="sinli"><i>4</i>-->
                                <!--<a>分隔线</a>-->
                            <!--</li>-->
                        <!--</ul>-->
                    <!--</div>-->
                <!--</div>-->
                <!--<div class="listcontent">-->
                    <!--<div class="box">-->
                        <!--<div id="waitBox" class="bastren w1200">-->
                            <!--<div id="chartLinediv" style="position:relative;*position:static;">-->
                                <!--<table id="table_ganyah" width="100%" border="0" cellpadding="0" cellspacing="0">-->
                                    <!--<thead>-->
                                    <!--<tr>-->
                                        <!--<th width="85" height="38" class="left_border left_b left_strong_down">期号</th>-->
                                        <!--<th width="100">开奖时间</th>-->
                                        <!--<th width="215">开奖号码</th>-->
                                        <!--<th width="58">冠亚和</th>-->
                                        <!--<th width="40">3</th>-->
                                        <!--<th width="40">4</th>-->
                                        <!--<th width="40">5</th>-->
                                        <!--<th width="40">6</th>-->
                                        <!--<th width="40">7</th>-->
                                        <!--<th width="40">8</th>-->
                                        <!--<th width="40">9</th>-->
                                        <!--<th width="40">10</th>-->
                                        <!--<th width="40">11</th>-->
                                        <!--<th width="40">12</th>-->
                                        <!--<th width="40">13</th>-->
                                        <!--<th width="40">14</th>-->
                                        <!--<th width="40">15</th>-->
                                        <!--<th width="40">16</th>-->
                                        <!--<th width="40">17</th>-->
                                        <!--<th width="40">18</th>-->
                                        <!--<th width="40">19</th>-->
                                    <!--</tr>-->
                                    <!--</thead>-->
                                    <!--<tbody>-->
                                    <!--<tr class="yiloutr">-->
                                        <!--<td>31705917</td>-->
                                        <!--<td>10:15:33</td>-->
                                        <!--<td>-->
                                            <!--<span style="color:#f1010a">6</span>&nbsp;-->
                                            <!--<span style="color:#0096ff">7</span>&nbsp;-->
                                            <!--<span>1</span>&nbsp;-->
                                            <!--<span>2</span>&nbsp;-->
                                            <!--<span>9</span>&nbsp;-->
                                            <!--<span>10</span>&nbsp;-->
                                            <!--<span>5</span>&nbsp;-->
                                            <!--<span>4</span>&nbsp;-->
                                            <!--<span>3</span>&nbsp;-->
                                            <!--<span>8</span>&nbsp;-->
                                        <!--</td>-->
                                        <!--<td style="background:#fdfaf5">13</td>-->
                                        <!--<td class="yilou"><span>35</span></td>-->
                                        <!--<td class="yilou"><span>115</span></td>-->
                                        <!--<td class="yilou"><span>2</span></td>-->
                                        <!--<td class="yilou"><span>66</span></td>-->
                                        <!--<td class="yilou"><span>5</span></td>-->
                                        <!--<td class="yilou"><span>21</span></td>-->
                                        <!--<td class="yilou"><span>6</span></td>-->
                                        <!--<td class="yilou"><span>15</span></td>-->
                                        <!--<td class="yilou"><span>11</span></td>-->
                                        <!--<td class="yilou"><span>1</span></td>-->
                                        <!--<td title="0" class="hot undefined">-->
                                            <!--<span style="background:#1fa6e8" name="hotSpan"-->
                                                  <!--class="zoushiqiu bBK">13</span>-->
                                        <!--</td>-->
                                        <!--<td class="yilou"><span>34</span></td>-->
                                        <!--<td class="yilou"><span>4</span></td>-->
                                        <!--<td class="yilou"><span>3</span></td>-->
                                        <!--<td class="yilou"><span>53</span></td>-->
                                        <!--<td class="yilou"><span>23</span></td>-->
                                        <!--<td class="yilou"><span>116</span></td>-->
                                    <!--</tr>-->

                                    <!--</tbody>-->
                                <!--</table>-->
                            <!--</div>-->
                            <!--<div id="chartbottom" style="display: none;">-->
                            <!--</div>-->
                        <!--</div>-->
                    <!--</div>-->
                <!--</div>-->
            <!--</div>-->


        <!--</div>-->

        <!--&lt;!&ndash;号码走势&ndash;&gt;-->
        <!--<div class="zhlzbox margt20" style="display:block">-->
            <!--<div class="listhead">-->
                <!--<div class="listheadl">-->
                    <!--<span class="lmms"><i>3</i>号码走势</span>-->
                <!--</div>-->
                <!--<div class="listheadr" style="width: 700px;">-->
                    <!--<div class="listheadrl" style="width: 380px">-->
                        <!--<span id="today">今天</span>-->
                        <!--<span id="yesterday">昨天</span>-->
                        <!--<span id="qianday">前天</span>-->
                        <!--<span id="thirty" class="checked">最近30期</span>-->
                        <!--<span id="sixty">最近60期</span>-->
                        <!--<span id="ninety">最近90期</span>-->
                    <!--</div>-->
                <!--</div>-->
            <!--</div>-->
            <!--<div class="listbox">-->
                <!--<div class="checkbox">-->
                    <!--<div id="biaozxz" class="checkbtnzh checkbtnmc">-->
                        <!--<ul>-->
                            <!--<li class="title">标注选择：</li>-->
                            <!--<li class="sinli checked"><i>1</i>-->
                                <!--<a>遗漏</a>-->
                            <!--</li>-->
                            <!--<li class="sinli checked"><i>2</i>-->
                                <!--<a>拆线</a>-->
                            <!--</li>-->
                            <!--<li class="sinli"><i>3</i>-->
                                <!--<a>遗漏分成</a>-->
                            <!--</li>-->
                            <!--<li class="sinli"><i>4</i>-->
                                <!--<a>分隔线</a>-->
                            <!--</li>-->
                        <!--</ul>-->
                    <!--</div>-->
                <!--</div>-->
                <!--<div class="listcontent">-->
                    <!--<div class="box">-->
                        <!--<div id="waitBox" class="bastren w1200">-->
                            <!--<div id="chartLinediv" style="position:relative;*position:static;">-->
                                <!--<div id="hovediv"-->
                                     <!--style="position: absolute; top: 0px; left: 0px; width: 3276px; z-index: 1; height: 1318px;"></div>-->
                                <!--<table id="trend_table2" width="3276" border="0" cellpadding="0" cellspacing="0">-->
                                    <!--<thead>-->
                                    <!--<tr class="first_th">-->
                                        <!--<th width="90" rowspan="2" class="left_border left_b left_strong_down"><strong-->
                                            <!--id="order" style="cursor:pointer;" type="desc">期号</strong></th>-->
                                        <!--<th width="152" class="verline" rowspan="2">开奖号码</th>-->
                                        <!--<th colspan="10" height="38">冠军</th>-->
                                        <!--<th colspan="10">亚军</th>-->
                                        <!--<th colspan="10">第三名</th>-->
                                        <!--<th colspan="10">第四名</th>-->
                                        <!--<th colspan="10">第五名</th>-->
                                        <!--<th colspan="10">第六名</th>-->
                                        <!--<th colspan="10">第七名</th>-->
                                        <!--<th colspan="10">第八名</th>-->
                                        <!--<th colspan="10">第九名</th>-->
                                        <!--<th colspan="10">第十名</th>-->
                                    <!--</tr>-->
                                    <!--<tr class="second_th">-->
                                        <!--<th width="30" height="33" class="nfw clthbr">1</th>-->
                                        <!--<th width="30" class="nfw">2</th>-->
                                        <!--<th width="30" class="nfw">3</th>-->
                                        <!--<th width="30" class="nfw">4</th>-->
                                        <!--<th width="30" class="nfw">5</th>-->
                                        <!--<th width="30" class="nfw">6</th>-->
                                        <!--<th width="30" class="nfw">7</th>-->
                                        <!--<th width="30" class="nfw">8</th>-->
                                        <!--<th width="30" class="nfw">9</th>-->
                                        <!--<th width="30" class="nfw" id="thbr">10</th>-->
                                        <!--<th width="30" class="nfw">1</th>-->
                                        <!--<th width="30" class="nfw">2</th>-->
                                        <!--<th width="30" class="nfw">3</th>-->
                                        <!--<th width="30" class="nfw">4</th>-->
                                        <!--<th width="30" class="nfw">5</th>-->
                                        <!--<th width="30" class="nfw">6</th>-->
                                        <!--<th width="30" class="nfw">7</th>-->
                                        <!--<th width="30" class="nfw">8</th>-->
                                        <!--<th width="30" class="nfw">9</th>-->
                                        <!--<th width="30" class="nfw" id="thbr">10</th>-->
                                        <!--<th width="30" class="nfw">1</th>-->
                                        <!--<th width="30" class="nfw">2</th>-->
                                        <!--<th width="30" class="nfw">3</th>-->
                                        <!--<th width="30" class="nfw">4</th>-->
                                        <!--<th width="30" class="nfw">5</th>-->
                                        <!--<th width="30" class="nfw">6</th>-->
                                        <!--<th width="30" class="nfw">7</th>-->
                                        <!--<th width="30" class="nfw">8</th>-->
                                        <!--<th width="30" class="nfw">9</th>-->
                                        <!--<th width="30" class="nfw">10</th>-->
                                        <!--<th width="30" class="nfw">1</th>-->
                                        <!--<th width="30" class="nfw">2</th>-->
                                        <!--<th width="30" class="nfw">3</th>-->
                                        <!--<th width="30" class="nfw">4</th>-->
                                        <!--<th width="30" class="nfw">5</th>-->
                                        <!--<th width="30" class="nfw">6</th>-->
                                        <!--<th width="30" class="nfw">7</th>-->
                                        <!--<th width="30" class="nfw">8</th>-->
                                        <!--<th width="30" class="nfw">9</th>-->
                                        <!--<th width="30" class="nfw">10</th>-->
                                        <!--<th width="30" class="nfw">1</th>-->
                                        <!--<th width="30" class="nfw">2</th>-->
                                        <!--<th width="30" class="nfw">3</th>-->
                                        <!--<th width="30" class="nfw">4</th>-->
                                        <!--<th width="30" class="nfw">5</th>-->
                                        <!--<th width="30" class="nfw">6</th>-->
                                        <!--<th width="30" class="nfw">7</th>-->
                                        <!--<th width="30" class="nfw">8</th>-->
                                        <!--<th width="30" class="nfw">9</th>-->
                                        <!--<th width="30" class="nfw">10</th>-->
                                        <!--<th width="30" class="nfw">1</th>-->
                                        <!--<th width="30" class="nfw">2</th>-->
                                        <!--<th width="30" class="nfw">3</th>-->
                                        <!--<th width="30" class="nfw">4</th>-->
                                        <!--<th width="30" class="nfw">5</th>-->
                                        <!--<th width="30" class="nfw">6</th>-->
                                        <!--<th width="30" class="nfw">7</th>-->
                                        <!--<th width="30" class="nfw">8</th>-->
                                        <!--<th width="30" class="nfw">9</th>-->
                                        <!--<th width="30" class="nfw">10</th>-->
                                        <!--<th width="30" class="nfw">1</th>-->
                                        <!--<th width="30" class="nfw">2</th>-->
                                        <!--<th width="30" class="nfw">3</th>-->
                                        <!--<th width="30" class="nfw">4</th>-->
                                        <!--<th width="30" class="nfw">5</th>-->
                                        <!--<th width="30" class="nfw">6</th>-->
                                        <!--<th width="30" class="nfw">7</th>-->
                                        <!--<th width="30" class="nfw">8</th>-->
                                        <!--<th width="30" class="nfw">9</th>-->
                                        <!--<th width="30" class="nfw">10</th>-->
                                        <!--<th width="30" class="nfw">1</th>-->
                                        <!--<th width="30" class="nfw">2</th>-->
                                        <!--<th width="30" class="nfw">3</th>-->
                                        <!--<th width="30" class="nfw">4</th>-->
                                        <!--<th width="30" class="nfw">5</th>-->
                                        <!--<th width="30" class="nfw">6</th>-->
                                        <!--<th width="30" class="nfw">7</th>-->
                                        <!--<th width="30" class="nfw">8</th>-->
                                        <!--<th width="30" class="nfw">9</th>-->
                                        <!--<th width="30" class="nfw">10</th>-->
                                        <!--<th width="30" class="nfw">1</th>-->
                                        <!--<th width="30" class="nfw">2</th>-->
                                        <!--<th width="30" class="nfw">3</th>-->
                                        <!--<th width="30" class="nfw">4</th>-->
                                        <!--<th width="30" class="nfw">5</th>-->
                                        <!--<th width="30" class="nfw">6</th>-->
                                        <!--<th width="30" class="nfw">7</th>-->
                                        <!--<th width="30" class="nfw">8</th>-->
                                        <!--<th width="30" class="nfw">9</th>-->
                                        <!--<th width="30" class="nfw">10</th>-->
                                        <!--<th width="30" class="nfw">1</th>-->
                                        <!--<th width="30" class="nfw">2</th>-->
                                        <!--<th width="30" class="nfw">3</th>-->
                                        <!--<th width="30" class="nfw">4</th>-->
                                        <!--<th width="30" class="nfw">5</th>-->
                                        <!--<th width="30" class="nfw">6</th>-->
                                        <!--<th width="30" class="nfw">7</th>-->
                                        <!--<th width="30" class="nfw">8</th>-->
                                        <!--<th width="30" class="nfw">9</th>-->
                                        <!--<th width="30" class="nfw">10</th>-->
                                    <!--</tr>-->
                                    <!--</thead>-->
                                    <!--<tbody>-->
                                    <!--<tr class="yiloutr" height="32">-->
                                        <!--<td class="leftth">31705925</td>-->
                                        <!--<td><span class="kaijnum">&nbsp;2&nbsp;5&nbsp;1&nbsp;6&nbsp;7&nbsp;9&nbsp;4&nbsp;10&nbsp;8&nbsp;3</span>-->
                                        <!--</td>-->
                                        <!--<td class="rank1 yilou"><span>3</span></td>-->
                                        <!--<td title="0" class="hot undefined"><span name="hotSpan" class="zoushiqiu bBK"-->
                                                                                  <!--style="background: rgb(251, 167, 94);">2</span>-->
                                        <!--</td>-->
                                        <!--<td class="rank1 yilou"><span>13</span></td>-->
                                        <!--<td class="rank1 yilou"><span>35</span></td>-->
                                        <!--<td class="rank1 yilou"><span>7</span></td>-->
                                        <!--<td class="rank1 yilou"><span>8</span></td>-->
                                        <!--<td class="rank1 yilou"><span>2</span></td>-->
                                        <!--<td class="rank1 yilou"><span>21</span></td>-->
                                        <!--<td class="rank1 yilou"><span>1</span></td>-->
                                        <!--<td class="rank1 yilou"><span>6</span></td>-->
                                        <!--<td class="rank2 yilou"><span>1</span></td>-->
                                        <!--<td class="rank2 yilou"><span>20</span></td>-->
                                        <!--<td class="rank2 yilou"><span>4</span></td>-->
                                        <!--<td class="rank2 yilou"><span>13</span></td>-->
                                        <!--<td title="0" class="hot undefined rank2"><span name="hotSpan"-->
                                                                                        <!--class="zoushiqiu gB"-->
                                                                                        <!--style="background: rgb(31, 166, 232);">5</span>-->
                                        <!--</td>-->
                                        <!--<td class="rank2 yilou"><span>16</span></td>-->
                                        <!--<td class="rank2 yilou"><span>3</span></td>-->
                                        <!--<td class="rank2 yilou"><span>22</span></td>-->
                                        <!--<td class="rank2 yilou"><span>2</span></td>-->
                                        <!--<td class="rank2 yilou"><span>7</span></td>-->
                                        <!--<td title="0" class="hot undefined rank3"><span name="hotSpan"-->
                                                                                        <!--class="zoushiqiu oB"-->
                                                                                        <!--style="background: rgb(8, 191, 2);">1</span>-->
                                        <!--</td>-->
                                        <!--<td class="rank3 yilou"><span>3</span></td>-->
                                        <!--<td class="rank3 yilou"><span>26</span></td>-->
                                        <!--<td class="rank3 yilou"><span>2</span></td>-->
                                        <!--<td class="rank3 yilou"><span>5</span></td>-->
                                        <!--<td class="rank3 yilou"><span>4</span></td>-->
                                        <!--<td class="rank3 yilou"><span>17</span></td>-->
                                        <!--<td class="rank3 yilou"><span>1</span></td>-->
                                        <!--<td class="rank3 yilou"><span>37</span></td>-->
                                        <!--<td class="rank3 yilou"><span>9</span></td>-->
                                        <!--<td class="rank4 yilou"><span>23</span></td>-->
                                        <!--<td class="rank4 yilou"><span>2</span></td>-->
                                        <!--<td class="rank4 yilou"><span>3</span></td>-->
                                        <!--<td class="rank4 yilou"><span>4</span></td>-->
                                        <!--<td class="rank4 yilou"><span>6</span></td>-->
                                        <!--<td title="0" class="hot undefined rank4"><span name="hotSpan"-->
                                                                                        <!--class="zoushiqiu gqB"-->
                                                                                        <!--style="background: rgb(133, 133, 251);">6</span>-->
                                        <!--</td>-->
                                        <!--<td class="rank4 yilou"><span>1</span></td>-->
                                        <!--<td class="rank4 yilou"><span>15</span></td>-->
                                        <!--<td class="rank4 yilou"><span>20</span></td>-->
                                        <!--<td class="rank4 yilou"><span>19</span></td>-->
                                        <!--<td class="rank5 yilou"><span>9</span></td>-->
                                        <!--<td class="rank5 yilou"><span>1</span></td>-->
                                        <!--<td class="rank5 yilou"><span>36</span></td>-->
                                        <!--<td class="rank5 yilou"><span>6</span></td>-->
                                        <!--<td class="rank5 yilou"><span>2</span></td>-->
                                        <!--<td class="rank5 yilou"><span>29</span></td>-->
                                        <!--<td title="0" class="hot undefined rank5"><span name="hotSpan"-->
                                                                                        <!--class="zoushiqiu grB"-->
                                                                                        <!--style="background: rgb(70, 189, 149);">7</span>-->
                                        <!--</td>-->
                                        <!--<td class="rank5 yilou"><span>11</span></td>-->
                                        <!--<td class="rank5 yilou"><span>7</span></td>-->
                                        <!--<td class="rank5 yilou"><span>5</span></td>-->
                                        <!--<td class="rank6 yilou"><span>14</span></td>-->
                                        <!--<td class="rank6 yilou"><span>66</span></td>-->
                                        <!--<td class="rank6 yilou"><span>6</span></td>-->
                                        <!--<td class="rank6 yilou"><span>18</span></td>-->
                                        <!--<td class="rank6 yilou"><span>1</span></td>-->
                                        <!--<td class="rank6 yilou"><span>3</span></td>-->
                                        <!--<td class="rank6 yilou"><span>10</span></td>-->
                                        <!--<td class="rank6 yilou"><span>2</span></td>-->
                                        <!--<td title="0" class="hot undefined rank6"><span name="hotSpan"-->
                                                                                        <!--class="zoushiqiu bBK"-->
                                                                                        <!--style="background: rgb(226, 107, 171);">9</span>-->
                                        <!--</td>-->
                                        <!--<td class="rank6 yilou"><span>8</span></td>-->
                                        <!--<td class="rank7 yilou"><span>7</span></td>-->
                                        <!--<td class="rank7 yilou"><span>18</span></td>-->
                                        <!--<td class="rank7 yilou"><span>19</span></td>-->
                                        <!--<td title="0" class="hot undefined rank7"><span name="hotSpan"-->
                                                                                        <!--class="zoushiqiu gB"-->
                                                                                        <!--style="background: rgb(242, 182, 83);">4</span>-->
                                        <!--</td>-->
                                        <!--<td class="rank7 yilou"><span>8</span></td>-->
                                        <!--<td class="rank7 yilou"><span>2</span></td>-->
                                        <!--<td class="rank7 yilou"><span>6</span></td>-->
                                        <!--<td class="rank7 yilou"><span>13</span></td>-->
                                        <!--<td class="rank7 yilou"><span>10</span></td>-->
                                        <!--<td class="rank7 yilou"><span>1</span></td>-->
                                        <!--<td class="rank8 yilou"><span>31</span></td>-->
                                        <!--<td class="rank8 yilou"><span>4</span></td>-->
                                        <!--<td class="rank8 yilou"><span>7</span></td>-->
                                        <!--<td class="rank8 yilou"><span>5</span></td>-->
                                        <!--<td class="rank8 yilou"><span>13</span></td>-->
                                        <!--<td class="rank8 yilou"><span>1</span></td>-->
                                        <!--<td class="rank8 yilou"><span>30</span></td>-->
                                        <!--<td class="rank8 yilou"><span>10</span></td>-->
                                        <!--<td class="rank8 yilou"><span>18</span></td>-->
                                        <!--<td title="0" class="hot undefined rank8"><span name="hotSpan"-->
                                                                                        <!--class="zoushiqiu oB"-->
                                                                                        <!--style="background: rgb(98, 142, 243);">10</span>-->
                                        <!--</td>-->
                                        <!--<td class="rank9 yilou"><span>5</span></td>-->
                                        <!--<td class="rank9 yilou"><span>9</span></td>-->
                                        <!--<td class="rank9 yilou"><span>2</span></td>-->
                                        <!--<td class="rank9 yilou"><span>1</span></td>-->
                                        <!--<td class="rank9 yilou"><span>21</span></td>-->
                                        <!--<td class="rank9 yilou"><span>13</span></td>-->
                                        <!--<td class="rank9 yilou"><span>7</span></td>-->
                                        <!--<td title="0" class="hot undefined rank9"><span name="hotSpan"-->
                                                                                        <!--class="zoushiqiu gqB"-->
                                                                                        <!--style="background: rgb(94, 198, 66);">8</span>-->
                                        <!--</td>-->
                                        <!--<td class="rank9 yilou"><span>3</span></td>-->
                                        <!--<td class="rank9 yilou"><span>12</span></td>-->
                                        <!--<td class="rank yilou"><span>2</span></td>-->
                                        <!--<td class="rank yilou"><span>6</span></td>-->
                                        <!--<td title="0" class="hot undefined rank"><span name="hotSpan"-->
                                                                                       <!--class="zoushiqiu grB"-->
                                                                                       <!--style="background: rgb(246, 109, 109);">3</span>-->
                                        <!--</td>-->
                                        <!--<td class="rank yilou"><span>14</span></td>-->
                                        <!--<td class="rank yilou"><span>11</span></td>-->
                                        <!--<td class="rank yilou"><span>7</span></td>-->
                                        <!--<td class="rank yilou"><span>4</span></td>-->
                                        <!--<td class="rank yilou"><span>3</span></td>-->
                                        <!--<td class="rank yilou"><span>5</span></td>-->
                                        <!--<td class="rank yilou"><span>10</span></td>-->
                                    <!--</tr>-->

                                    <!--</tbody>-->
                                <!--</table>-->
                            <!--</div>-->
                            <!--<div id="chartbottom" style="display: none;">-->
                            <!--</div>-->
                        <!--</div>-->
                    <!--</div>-->
                <!--</div>-->
            <!--</div>-->


        <!--</div>-->

        <!--&lt;!&ndash;位置走势&ndash;&gt;-->
        <!--<div class="zhlzbox margt20" style="display:block">-->
            <!--<div class="listhead">-->
                <!--<div class="listheadl">-->
                    <!--<span class="lmms"><i>3</i>位置走势</span>-->
                <!--</div>-->
                <!--<div class="listheadr" style="width: 700px;">-->
                    <!--<div class="listheadrl" style="width: 380px">-->
                        <!--<span id="today">今天</span>-->
                        <!--<span id="yesterday">昨天</span>-->
                        <!--<span id="qianday">前天</span>-->
                        <!--<span id="thirty" class="checked">最近30期</span>-->
                        <!--<span id="sixty">最近60期</span>-->
                        <!--<span id="ninety">最近90期</span>-->
                    <!--</div>-->
                <!--</div>-->
            <!--</div>-->
            <!--<div class="listbox">-->
                <!--<div class="listbox_spread" id="listbox_spread">-->
                    <!--<div id="spreadDiv" style="height: 28px; padding: 0px;">-->
                        <!--<p id="prep"><i>说明：</i> 1 奇偶：除2余数为1的为奇数，含1、3、5、7、9，除2余0的为偶数，含2、4、6、8、10；<span></span></p>-->
                        <!--<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 2-->
                            <!--大小：6、7、8、9、10为大数，1、2、3、4、5为小数； </p>-->
                        <!--<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 3-->
                            <!--012路：除3余数为0的为0路号码，包含3、6、9,；除3余数为1的为1路号码，包含1、4、7、10；除3余数为2的为2路号码，包含2、5、8;</p>-->
                        <!--<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 4-->
                            <!--升平降：比上期奖号数值大的，表示本期奖号特征为升；与上期相同的，表示本期奖号特征为平；比上期奖号数值小的，表示本期奖号特征为降； </p>-->
                        <!--<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 5-->
                            <!--分隔线：每五期使用分隔线，使横向导航更加清晰;</p>-->
                        <!--<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 6-->
                            <!--出现总次数：统计期数内实际出现的次数； </p>-->
                        <!--<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 7-->
                            <!--遗漏：自上期开出到本期间隔的期数;</p>-->
                        <!--<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 8-->
                            <!--平均遗漏：统计期数内遗漏的平均值。（计算公式：平均遗漏＝统计期数/(出现次数+1)。）； </p>-->
                        <!--<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 9-->
                            <!--最大遗漏：统计期数内遗漏的最大值;</p>-->
                        <!--<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 10-->
                            <!--当前遗漏：自上期开出到当前期所间隔的期数； </p>-->
                        <!--<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 11-->
                            <!--最大连出值：统计期数内连续开出的最大值;</p>-->
                    <!--</div>-->
                <!--</div>-->
                <!--<div class="checkbox">-->
                    <!--<div id="biaozxz" class="checkbtnzh checkbtnmc">-->
                        <!--<ul class="xuanzhemc liMarginReset wzzsLeftUl">-->
                            <!--&lt;!&ndash;<li class="mctitle">选择名次：</li>&ndash;&gt;-->
                            <!--<li class="sinli selected"><span>1</span>-->
                                <!--<a href="javascript:void(0)">冠军</a>-->
                            <!--</li>-->
                            <!--<li class="sinli"><span>2</span>-->
                                <!--<a href="javascript:void(0)">亚军</a>-->
                            <!--</li>-->
                            <!--<li class="sinli"><span>3</span>-->
                                <!--<a href="javascript:void(0)">第三名</a>-->
                            <!--</li>-->
                            <!--<li class="sinli"><span>4</span>-->
                                <!--<a href="javascript:void(0)">第四名</a>-->
                            <!--</li>-->
                            <!--<li class="sinli"><span>5</span>-->
                                <!--<a href="javascript:void(0)">第五名</a>-->
                            <!--</li>-->
                            <!--<li class="sinli"><span>6</span>-->
                                <!--<a href="javascript:void(0)">第六名</a>-->
                            <!--</li>-->
                            <!--<li class="sinli"><span>7</span>-->
                                <!--<a href="javascript:void(0)">第七名</a>-->
                            <!--</li>-->
                            <!--<li class="sinli"><span>8</span>-->
                                <!--<a href="javascript:void(0)">第八名</a>-->
                            <!--</li>-->
                            <!--<li class="sinli"><span>9</span>-->
                                <!--<a href="javascript:void(0)">第九名</a>-->
                            <!--</li>-->
                            <!--<li class="sinli"><span>10</span>-->
                                <!--<a href="javascript:void(0)">第十名</a>-->
                            <!--</li>-->
                        <!--</ul>-->
                        <!--<ul class="wzzsRightUl">-->
                            <!--&lt;!&ndash;<li class="title">标注选择：</li>&ndash;&gt;-->
                            <!--<li class="sinli checked"><i>1</i>-->
                                <!--<a href="javascript:void(0)">遗漏</a>-->
                            <!--</li>-->
                            <!--<li class="sinli checked"><i>2</i>-->
                                <!--<a href="javascript:void(0)">拆线</a>-->
                            <!--</li>-->
                            <!--<li class="sinli"><i>3</i>-->
                                <!--<a href="javascript:void(0)">遗漏分层</a>-->
                            <!--</li>-->
                            <!--<li class="sinli"><i>4</i>-->
                                <!--<a href="javascript:void(0)">分隔线</a>-->
                            <!--</li>-->
                        <!--</ul>-->
                    <!--</div>-->
                <!--</div>-->
                <!--<div class="listcontent">-->
                    <!--<div class="box">-->
                        <!--<div id="waitBox" class="bastren w1200">-->
                            <!--<div id="chartLinediv" style="position:relative;*position:static;">-->
                                <!--<table id="table_weizhi1" width="100%" border="0" cellpadding="0" cellspacing="0"-->
                                       <!--style="display: table;">-->
                                    <!--<thead>-->
                                    <!--<tr class="first_th">-->
                                        <!--<th rowspan="2" width="85" height="38"-->
                                            <!--class="left_border left_b left_strong_down">期号-->
                                        <!--</th>-->
                                        <!--<th rowspan="2" width="215">开奖号码</th>-->
                                        <!--<th colspan="10" width="40">冠军分布</th>-->
                                        <!--<th colspan="6" width="40">形态特征</th>-->
                                        <!--<th colspan="3" width="40">012路</th>-->
                                        <!--<th colspan="3" width="40">升平降</th>-->
                                    <!--</tr>-->
                                    <!--<tr class="second_th">-->
                                        <!--<th width="40">1</th>-->
                                        <!--<th width="40">2</th>-->
                                        <!--<th width="40">3</th>-->
                                        <!--<th width="40">4</th>-->
                                        <!--<th width="40">5</th>-->
                                        <!--<th width="40">6</th>-->
                                        <!--<th width="40">7</th>-->
                                        <!--<th width="40">8</th>-->
                                        <!--<th width="40">9</th>-->
                                        <!--<th width="40">10</th>-->
                                        <!--<th width="40">奇</th>-->
                                        <!--<th width="40">偶</th>-->
                                        <!--<th width="40">大</th>-->
                                        <!--<th width="40">小</th>-->
                                        <!--<th width="40">质</th>-->
                                        <!--<th width="40">合</th>-->
                                        <!--<th width="40">0</th>-->
                                        <!--<th width="40">1</th>-->
                                        <!--<th width="40">2</th>-->
                                        <!--<th width="40">升</th>-->
                                        <!--<th width="40">平</th>-->
                                        <!--<th width="40">降</th>-->
                                    <!--</tr>-->
                                    <!--</thead>-->
                                    <!--<tbody>-->
                                    <!--<tr class="yiloutr">-->
                                        <!--<td>31705931</td>-->
                                        <!--<td class="fpredrawSpan"><span class="colorred">2</span>&nbsp;<span>1</span>&nbsp;<span>9</span>&nbsp;<span>8</span>&nbsp;<span>3</span>&nbsp;<span>4</span>&nbsp;<span>7</span>&nbsp;<span>10</span>&nbsp;<span>5</span>&nbsp;<span>6</span>&nbsp;-->
                                        <!--</td>-->
                                        <!--<td style="background-color:#fcf9f2" class="yilou"><span>9</span></td>-->
                                        <!--<td style="background-color:#fcf9f2" title="0" class="hot undefined"><span-->
                                            <!--style="background:#1fa6e8" name="hotSpan" class="zoushiqiu bBK">2</span>-->
                                        <!--</td>-->
                                        <!--<td style="background-color:#fcf9f2" class="yilou"><span>2</span></td>-->
                                        <!--<td style="background-color:#fcf9f2" class="yilou"><span>41</span></td>-->
                                        <!--<td style="background-color:#fcf9f2" class="yilou"><span>13</span></td>-->
                                        <!--<td style="background-color:#fcf9f2" class="yilou"><span>3</span></td>-->
                                        <!--<td style="background-color:#fcf9f2" class="yilou"><span>5</span></td>-->
                                        <!--<td style="background-color:#fcf9f2" class="yilou"><span>1</span></td>-->
                                        <!--<td style="background-color:#fcf9f2" class="yilou"><span>4</span></td>-->
                                        <!--<td style="background-color:#fcf9f2" class="yilou"><span>12</span></td>-->
                                        <!--<td class="yilou"><span>2</span></td>-->
                                        <!--<td title="0" class="hot undefined"><span style="background: rgb(60, 176, 236);"-->
                                                                                  <!--name="hotSpan"-->
                                                                                  <!--class="zoushiqiuf outdbg">偶</span>-->
                                        <!--</td>-->
                                        <!--<td class="yilou"><span>1</span></td>-->
                                        <!--<td title="0" class="hot undefined"><span style="background: rgb(60, 176, 236);"-->
                                                                                  <!--name="hotSpan"-->
                                                                                  <!--class="zoushiqiuf outdbg">小</span>-->
                                        <!--</td>-->
                                        <!--<td title="0" class="hot undefined"><span style="background: rgb(250, 153, 50);"-->
                                                                                  <!--name="hotSpan"-->
                                                                                  <!--class="zoushiqiuf jitdbg">质</span>-->
                                        <!--</td>-->
                                        <!--<td class="yilou"><span>1</span></td>-->
                                        <!--<td class="yilou"><span>2</span></td>-->
                                        <!--<td class="yilou"><span>5</span></td>-->
                                        <!--<td title="0" class="hot undefined"><span style="background: rgb(250, 153, 50);"-->
                                                                                  <!--name="hotSpan"-->
                                                                                  <!--class="zoushiqiuf jitdbg">2</span>-->
                                        <!--</td>-->
                                        <!--<td class="yilou"><span>1</span></td>-->
                                        <!--<td class="yilou"><span>9</span></td>-->
                                        <!--<td title="0" class="hot undefined"><span style="background: rgb(60, 176, 236);"-->
                                                                                  <!--name="hotSpan"-->
                                                                                  <!--class="zoushiqiuf outdbg">降</span>-->
                                        <!--</td>-->
                                    <!--</tr>-->

                                    <!--<tr>-->
                                        <!--<th rowspan="2" colspan="2" width="85" height="38"-->
                                            <!--class="left_border left_b left_strong_down">数据统计-->
                                        <!--</th>-->
                                        <!--<th colspan="10" width="40">冠军分布</th>-->
                                        <!--<th colspan="6" width="40">形态特征</th>-->
                                        <!--<th colspan="3" width="40">012路</th>-->
                                        <!--<th colspan="3" width="40">升平降</th>-->
                                    <!--</tr>-->
                                    <!--<tr>-->
                                        <!--<th width="40" class="font14">1</th>-->
                                        <!--<th width="40" class="font14">2</th>-->
                                        <!--<th width="40" class="font14">3</th>-->
                                        <!--<th width="40" class="font14">4</th>-->
                                        <!--<th width="40" class="font14">5</th>-->
                                        <!--<th width="40" class="font14">6</th>-->
                                        <!--<th width="40" class="font14">7</th>-->
                                        <!--<th width="40" class="font14">8</th>-->
                                        <!--<th width="40" class="font14">9</th>-->
                                        <!--<th width="40" class="font14">10</th>-->
                                        <!--<th width="40" class="font14">奇</th>-->
                                        <!--<th width="40" class="font14">偶</th>-->
                                        <!--<th width="40" class="font14">大</th>-->
                                        <!--<th width="40" class="font14">小</th>-->
                                        <!--<th width="40" class="font14">质</th>-->
                                        <!--<th width="40" class="font14">合</th>-->
                                        <!--<th width="40" class="font14">0</th>-->
                                        <!--<th width="40" class="font14">1</th>-->
                                        <!--<th width="40" class="font14">2</th>-->
                                        <!--<th width="40" class="font14">升</th>-->
                                        <!--<th width="40" class="font14">平</th>-->
                                        <!--<th width="40" class="font14">降</th>-->
                                    <!--</tr>-->
                                    <!--<tr>-->
                                        <!--<td class="font14" colspan="2">总次数</td>-->
                                        <!--<td>3</td>-->
                                        <!--<td>6</td>-->
                                        <!--<td>4</td>-->
                                        <!--<td>0</td>-->
                                        <!--<td>1</td>-->
                                        <!--<td>4</td>-->
                                        <!--<td>6</td>-->
                                        <!--<td>2</td>-->
                                        <!--<td>3</td>-->
                                        <!--<td>1</td>-->
                                        <!--<td>17</td>-->
                                        <!--<td>13</td>-->
                                        <!--<td>16</td>-->
                                        <!--<td>14</td>-->
                                        <!--<td>20</td>-->
                                        <!--<td>10</td>-->
                                        <!--<td>11</td>-->
                                        <!--<td>10</td>-->
                                        <!--<td>9</td>-->
                                        <!--<td>13</td>-->
                                        <!--<td>3</td>-->
                                        <!--<td>14</td>-->
                                    <!--</tr>-->
                                    <!--<tr>-->
                                        <!--<td class="font14" colspan="2">平均遗漏</td>-->
                                        <!--<td>7</td>-->
                                        <!--<td>4</td>-->
                                        <!--<td>6</td>-->
                                        <!--<td>30</td>-->
                                        <!--<td>15</td>-->
                                        <!--<td>6</td>-->
                                        <!--<td>4</td>-->
                                        <!--<td>10</td>-->
                                        <!--<td>7</td>-->
                                        <!--<td>15</td>-->
                                        <!--<td>1</td>-->
                                        <!--<td>2</td>-->
                                        <!--<td>1</td>-->
                                        <!--<td>2</td>-->
                                        <!--<td>1</td>-->
                                        <!--<td>2</td>-->
                                        <!--<td>2</td>-->
                                        <!--<td>2</td>-->
                                        <!--<td>3</td>-->
                                        <!--<td>2</td>-->
                                        <!--<td>7</td>-->
                                        <!--<td>2</td>-->
                                    <!--</tr>-->
                                    <!--<tr>-->
                                        <!--<td class="font14" colspan="2">当前遗漏</td>-->
                                        <!--<td>9</td>-->
                                        <!--<td>0</td>-->
                                        <!--<td>2</td>-->
                                        <!--<td>41</td>-->
                                        <!--<td>13</td>-->
                                        <!--<td>3</td>-->
                                        <!--<td>5</td>-->
                                        <!--<td>1</td>-->
                                        <!--<td>4</td>-->
                                        <!--<td>12</td>-->
                                        <!--<td>2</td>-->
                                        <!--<td>0</td>-->
                                        <!--<td>1</td>-->
                                        <!--<td>0</td>-->
                                        <!--<td>0</td>-->
                                        <!--<td>1</td>-->
                                        <!--<td>2</td>-->
                                        <!--<td>5</td>-->
                                        <!--<td>0</td>-->
                                        <!--<td>1</td>-->
                                        <!--<td>9</td>-->
                                        <!--<td>0</td>-->
                                    <!--</tr>-->
                                    <!--<tr>-->
                                        <!--<td class="font14" colspan="2">最大连出</td>-->
                                        <!--<td>2</td>-->
                                        <!--<td>2</td>-->
                                        <!--<td>1</td>-->
                                        <!--<td>0</td>-->
                                        <!--<td>1</td>-->
                                        <!--<td>2</td>-->
                                        <!--<td>1</td>-->
                                        <!--<td>1</td>-->
                                        <!--<td>1</td>-->
                                        <!--<td>1</td>-->
                                        <!--<td>5</td>-->
                                        <!--<td>2</td>-->
                                        <!--<td>4</td>-->
                                        <!--<td>3</td>-->
                                        <!--<td>4</td>-->
                                        <!--<td>4</td>-->
                                        <!--<td>3</td>-->
                                        <!--<td>5</td>-->
                                        <!--<td>2</td>-->
                                        <!--<td>3</td>-->
                                        <!--<td>1</td>-->
                                        <!--<td>2</td>-->
                                    <!--</tr>-->
                                    <!--<tr>-->
                                        <!--<td class="font14" colspan="2">最大遗漏</td>-->
                                        <!--<td>17</td>-->
                                        <!--<td>23</td>-->
                                        <!--<td>18</td>-->
                                        <!--<td>41</td>-->
                                        <!--<td>19</td>-->
                                        <!--<td>10</td>-->
                                        <!--<td>29</td>-->
                                        <!--<td>25</td>-->
                                        <!--<td>15</td>-->
                                        <!--<td>34</td>-->
                                        <!--<td>2</td>-->
                                        <!--<td>5</td>-->
                                        <!--<td>3</td>-->
                                        <!--<td>4</td>-->
                                        <!--<td>4</td>-->
                                        <!--<td>4</td>-->
                                        <!--<td>6</td>-->
                                        <!--<td>6</td>-->
                                        <!--<td>6</td>-->
                                        <!--<td>3</td>-->
                                        <!--<td>10</td>-->
                                        <!--<td>4</td>-->
                                    <!--</tr>-->
                                    <!--</tbody>-->
                                <!--</table>-->
                            <!--</div>-->
                            <!--<div id="chartbottom" style="display: none;">-->
                                <!--<table>-->
                                    <!--<tbody>-->
                                    <!--<tr class="first_th">-->
                                        <!--<th rowspan="2" colspan="2" width="85" height="38"-->
                                            <!--class="left_border left_b left_strong_down">数据统计-->
                                        <!--</th>-->
                                        <!--<th colspan="10" width="40">冠军分布</th>-->
                                        <!--<th colspan="6" width="40">形态特征</th>-->
                                        <!--<th colspan="3" width="40">012路</th>-->
                                        <!--<th colspan="3" width="40">升平降</th>-->
                                    <!--</tr>-->
                                    <!--<tr class="second_th">-->
                                        <!--<th width="40">1</th>-->
                                        <!--<th width="40">2</th>-->
                                        <!--<th width="40">3</th>-->
                                        <!--<th width="40">4</th>-->
                                        <!--<th width="40">5</th>-->
                                        <!--<th width="40">6</th>-->
                                        <!--<th width="40">7</th>-->
                                        <!--<th width="40">8</th>-->
                                        <!--<th width="40">9</th>-->
                                        <!--<th width="40">10</th>-->
                                        <!--<th width="40">奇</th>-->
                                        <!--<th width="40">偶</th>-->
                                        <!--<th width="40">大</th>-->
                                        <!--<th width="40">小</th>-->
                                        <!--<th width="40">质</th>-->
                                        <!--<th width="40">合</th>-->
                                        <!--<th width="40">0</th>-->
                                        <!--<th width="40">1</th>-->
                                        <!--<th width="40">2</th>-->
                                        <!--<th width="40">升</th>-->
                                        <!--<th width="40">平</th>-->
                                        <!--<th width="40">降</th>-->
                                    <!--</tr>-->
                                    <!--</tbody>-->
                                <!--</table>-->
                            <!--</div>-->
                        <!--</div>-->

                    <!--</div>-->
                <!--</div>-->
            <!--</div>-->


        <!--</div>-->
        <MyFoot></MyFoot>
    </div>
</template>
<script>
    import MyHeader from '@/components/layout/head'
    import MyFoot from '@/components/layout/foot'
    import PKtop from '@/components/lottery/pk10/top'
    import {mapActions, mapGetters} from 'vuex'
    import Datepicker from 'vuejs-datepicker';
    import {en, ja, zh} from 'vuejs-datepicker/dist/locale'

    export default {
        name: "pk10",
        data() {
            return {
                topLi: {
                    jrsm: false,
                    cltx: false,
                    hmfb: false
                },
                languages: {en: en, zh: zh, jp: ja},
                dayType: 0,
                dateStr: "",
                jrsmList: [],
                gylhMap: {},
                hisList: [],
                rlfxList: [],
                dxdsHisList: [],
                lmsjBnt: 1,
                lmsjList: [],
                lhtjList: [],
                gyhlmList: [],
                changlongList: [],
                jrhmtjList: [],
                jrhmtjType: {
                    red:true,
                    blue:true,
                    green:true,
                },
                resultHistoryShow: false,
                luzhuList: {
                    'no1': {'no1val': [], 'no1oe': [], 'no1ou': [], 'no1': [], 'lhno1': [], 'no1qh': []},
                    'no2': {'no2val': [], 'no2oe': [], 'no2ou': [], 'no2': [], 'lhno2': [], 'no2qh': []},
                    'no3': {'no3val': [], 'no3oe': [], 'no3ou': [], 'no3': [], 'lhno3': [], 'no3qh': []},
                    'no4': {'no4val': [], 'no4oe': [], 'no4ou': [], 'no4': [], 'lhno4': [], 'no4qh': []},
                    'no5': {'no5val': [], 'no5oe': [], 'no5ou': [], 'no5': [], 'lhno5': [], 'no5qh': []},
                    'no6': {'no6val': [], 'no6oe': [], 'no6ou': [], 'no6': [], 'no6qh': []},
                    'no7': {'no7val': [], 'no7oe': [], 'no7ou': [], 'no7': [], 'no7qh': []},
                    'no8': {'no8val': [], 'no8oe': [], 'no8ou': [], 'no8': [], 'no8qh': []},
                    'no9': {'no9val': [], 'no9oe': [], 'no9ou': [], 'no9': [], 'no9qh': []},
                    'no10': {'no10val': [], 'no10oe': [], 'no10ou': [], 'no10': [], 'no10qh': []},
                    'gyh': {'gyh': [], 'gyhou': [], 'gyhoe': []},
                },
                lotteryMainTab: 'roadA',
                resultShowType: 'num',
                resultNumScreen: [],
                resultTypeScreen: '',
                gyhLZTotal: {
                    over: 0,
                    under: 0,
                    odd: 0,
                    even: 0,
                },
                gyhLZOENum: 1,
                gyhLZOUNum: 1,
                gyhLZOENumTwo: 1,
                gyhLZOUNumTwo: 1,
                gyhLZOESelect: 'odd',
                gyhLZOESelectTwo: 'odd',
                gyhLZOUSelect: 'over',
                gyhLZOUSelectTwo: 'over',
                gyhLZOUShowNum: 0,
                gyhLZOEShowNum: 0,
                gyhLZOEBtnFlag: false,
                gyhLZOUBtnFlag: false,
                totalDateTime: '今天',
                dtRoad: {
                    'lhno1': {dragon: 0, tiger: 0},
                    'lhno2': {dragon: 0, tiger: 0},
                    'lhno3': {dragon: 0, tiger: 0},
                    'lhno4': {dragon: 0, tiger: 0},
                    'lhno5': {dragon: 0, tiger: 0},
                    filters: {
                        num1: 1,
                        num2: 1,
                        num3: 1,
                        num4: 1,
                        num5: 1,
                        numTwo1: 1,
                        numTwo2: 1,
                        numTwo3: 1,
                        numTwo4: 1,
                        numTwo5: 1,
                        select1: 'dragon',
                        select2: 'dragon',
                        select3: 'dragon',
                        select4: 'dragon',
                        select5: 'dragon',
                        selectTwo1: 'dragon',
                        selectTwo2: 'dragon',
                        selectTwo3: 'dragon',
                        selectTwo4: 'dragon',
                        selectTwo5: 'dragon',
                        btn1: false,
                        btn2: false,
                        btn3: false,
                        btn4: false,
                        btn5: false,
                        showNum1: 0,
                        showNum2: 0,
                        showNum3: 0,
                        showNum4: 0,
                        showNum5: 0,
                        checkBoxList: {
                            lhno1: true,
                            lhno2: true,
                            lhno3: true,
                            lhno4: true,
                            lhno5: true,

                        }
                    }
                },
                sdRoad: {
                    'no1': {over: 0, under: 0, odd: 0, even: 0},
                    'no2': {over: 0, under: 0, odd: 0, even: 0},
                    'no3': {over: 0, under: 0, odd: 0, even: 0},
                    'no4': {over: 0, under: 0, odd: 0, even: 0},
                    'no5': {over: 0, under: 0, odd: 0, even: 0},
                    'no6': {over: 0, under: 0, odd: 0, even: 0},
                    'no7': {over: 0, under: 0, odd: 0, even: 0},
                    'no8': {over: 0, under: 0, odd: 0, even: 0},
                    'no9': {over: 0, under: 0, odd: 0, even: 0},
                    'no10': {over: 0, under: 0, odd: 0, even: 0},
                    'gyh': {over: 0, under: 0, odd: 0, even: 0},
                    filters: {
                        selectRoad: 'ou',
                        numno1: 1,
                        numno2: 1,
                        numno3: 1,
                        numno4: 1,
                        numno5: 1,
                        numno6: 1,
                        numno7: 1,
                        numno8: 1,
                        numno9: 1,
                        numno10: 1,
                        numgyh: 1,
                        numTwono1: 1,
                        numTwono2: 1,
                        numTwono3: 1,
                        numTwono4: 1,
                        numTwono5: 1,
                        numTwono6: 1,
                        numTwono7: 1,
                        numTwono8: 1,
                        numTwono9: 1,
                        numTwono10: 1,
                        numTwogyh: 1,
                        selectno1: 'over',
                        selectno2: 'over',
                        selectno3: 'over',
                        selectno4: 'over',
                        selectno5: 'over',
                        selectno6: 'over',
                        selectno7: 'over',
                        selectno8: 'over',
                        selectno9: 'over',
                        selectno10: 'over',
                        selectgyh: 'over',
                        selectTwono1: 'over',
                        selectTwono2: 'over',
                        selectTwono3: 'over',
                        selectTwono4: 'over',
                        selectTwono5: 'over',
                        selectTwono6: 'over',
                        selectTwono7: 'over',
                        selectTwono8: 'over',
                        selectTwono9: 'over',
                        selectTwono10: 'over',
                        selectTwogyh: 'over',
                        btnno1: false,
                        btnno2: false,
                        btnno3: false,
                        btnno4: false,
                        btnno5: false,
                        btnno6: false,
                        btnno7: false,
                        btnno8: false,
                        btnno9: false,
                        btnno10: false,
                        btngyh: false,
                        showNumno1: 0,
                        showNumno2: 0,
                        showNumno3: 0,
                        showNumno4: 0,
                        showNumno5: 0,
                        showNumno6: 0,
                        showNumno7: 0,
                        showNumno8: 0,
                        showNumno9: 0,
                        showNumno10: 0,
                        showNumgyh: 0,
                        checkBoxList: {
                            no1: true,
                            no2: true,
                            no3: true,
                            no4: true,
                            no5: true,
                            no6: true,
                            no7: true,
                            no8: true,
                            no9: true,
                            no10: true,
                            gyh: true,
                        }
                    }
                },
                roadA: {
                    roadAModeSelect: 'about',
                    'no1': {over: 0, under: 0, odd: 0, even: 0, dragon: 0, tiger: 0},
                    'no2': {over: 0, under: 0, odd: 0, even: 0, dragon: 0, tiger: 0},
                    'no3': {over: 0, under: 0, odd: 0, even: 0, dragon: 0, tiger: 0},
                    'no4': {over: 0, under: 0, odd: 0, even: 0, dragon: 0, tiger: 0},
                    'no5': {over: 0, under: 0, odd: 0, even: 0, dragon: 0, tiger: 0},
                    'no6': {over: 0, under: 0, odd: 0, even: 0},
                    'no7': {over: 0, under: 0, odd: 0, even: 0},
                    'no8': {over: 0, under: 0, odd: 0, even: 0},
                    'no9': {over: 0, under: 0, odd: 0, even: 0},
                    'no10': {over: 0, under: 0, odd: 0, even: 0},
                    'gyh': {over: 0, under: 0, odd: 0, even: 0},
                    filters: {
                        numno1ou: 1,
                        numno1oe: 1,
                        numlhno1: 1,
                        numno2ou: 1,
                        numno2oe: 1,
                        numlhno2: 1,
                        numno3ou: 1,
                        numno3oe: 1,
                        numlhno3: 1,
                        numno4ou: 1,
                        numno4oe: 1,
                        numlhno4: 1,
                        numno5ou: 1,
                        numno5oe: 1,
                        numlhno5: 1,
                        numno6ou: 1,
                        numno6oe: 1,
                        numno7ou: 1,
                        numno7oe: 1,
                        numno8ou: 1,
                        numno8oe: 1,
                        numno9ou: 1,
                        numno9oe: 1,
                        numno10ou: 1,
                        numno10oe: 1,
                        numgyhou: 1,
                        numgyhoe: 1,
                        numTwono1ou: 1,
                        numTwono1oe: 1,
                        numTwolhno1: 1,
                        numTwono2ou: 1,
                        numTwono2oe: 1,
                        numTwolhno2: 1,
                        numTwono3ou: 1,
                        numTwono3oe: 1,
                        numTwolhno3: 1,
                        numTwono4ou: 1,
                        numTwono4oe: 1,
                        numTwolhno4: 1,
                        numTwono5ou: 1,
                        numTwono5oe: 1,
                        numTwolhno5: 1,
                        numTwono6ou: 1,
                        numTwono6oe: 1,
                        numTwono7ou: 1,
                        numTwono7oe: 1,
                        numTwono8ou: 1,
                        numTwono8oe: 1,
                        numTwono9ou: 1,
                        numTwono9oe: 1,
                        numTwono10ou: 1,
                        numTwono10oe: 1,
                        numTwogyhou: 1,
                        numTwogyhoe: 1,
                        selectno1ou: 'over',
                        selectno1oe: 'odd',
                        selectlhno1: 'dragon',
                        selectno2ou: 'over',
                        selectno2oe: 'odd',
                        selectlhno2: 'dragon',
                        selectno3ou: 'over',
                        selectno3oe: 'odd',
                        selectlhno3: 'dragon',
                        selectno4ou: 'over',
                        selectno4oe: 'odd',
                        selectlhno4: 'dragon',
                        selectno5ou: 'over',
                        selectno5oe: 'odd',
                        selectlhno5: 'dragon',
                        selectno6ou: 'over',
                        selectno6oe: 'odd',
                        selectno7ou: 'over',
                        selectno7oe: 'odd',
                        selectno8ou: 'over',
                        selectno8oe: 'odd',
                        selectno9ou: 'over',
                        selectno9oe: 'odd',
                        selectno10ou: 'over',
                        selectno10oe: 'odd',
                        selectgyhou: 'over',
                        selectgyhoe: 'odd',
                        selectTwono1ou: 'over',
                        selectTwono1oe: 'odd',
                        selectTwolhno1: 'dragon',
                        selectTwono2ou: 'over',
                        selectTwono2oe: 'odd',
                        selectTwolhno2: 'dragon',
                        selectTwono3ou: 'over',
                        selectTwono3oe: 'odd',
                        selectTwolhno3: 'dragon',
                        selectTwono4ou: 'over',
                        selectTwono4oe: 'odd',
                        selectTwolhno4: 'dragon',
                        selectTwono5ou: 'over',
                        selectTwono5oe: 'odd',
                        selectTwolhno5: 'dragon',
                        selectTwono6ou: 'over',
                        selectTwono6oe: 'odd',
                        selectTwono7ou: 'over',
                        selectTwono7oe: 'odd',
                        selectTwono8ou: 'over',
                        selectTwono8oe: 'odd',
                        selectTwono9ou: 'over',
                        selectTwono9oe: 'odd',
                        selectTwono10ou: 'over',
                        selectTwono10oe: 'odd',
                        selectTwogyhou: 'over',
                        selectTwogyhoe: 'odd',
                        btnno1ou: false,
                        btnno1oe: false,
                        btnlhno1: false,
                        btnno2ou: false,
                        btnno2oe: false,
                        btnlhno2: false,
                        btnno3ou: false,
                        btnno3oe: false,
                        btnlhno3: false,
                        btnno4ou: false,
                        btnno4oe: false,
                        btnlhno4: false,
                        btnno5ou: false,
                        btnno5oe: false,
                        btnlhno5: false,
                        btnno6ou: false,
                        btnno6oe: false,
                        btnno7ou: false,
                        btnno7oe: false,
                        btnno8ou: false,
                        btnno8oe: false,
                        btnno9ou: false,
                        btnno9oe: false,
                        btnno10ou: false,
                        btnno10oe: false,
                        btngyhou: false,
                        btngyhoe: false,
                        showNumno1ou: 0,
                        showNumno1oe: 0,
                        showNumlhno1: 0,
                        showNumno2ou: 0,
                        showNumno2oe: 0,
                        showNumlhno2: 0,
                        showNumno3ou: 0,
                        showNumno3oe: 0,
                        showNumlhno3: 0,
                        showNumno4ou: 0,
                        showNumno4oe: 0,
                        showNumlhno4: 0,
                        showNumno5ou: 0,
                        showNumno5oe: 0,
                        showNumlhno5: 0,
                        showNumno6ou: 0,
                        showNumno6oe: 0,
                        showNumno7ou: 0,
                        showNumno7oe: 0,
                        showNumno8ou: 0,
                        showNumno8oe: 0,
                        showNumno9ou: 0,
                        showNumno9oe: 0,
                        showNumno10ou: 0,
                        showNumno10oe: 0,
                        showNumgyhou: 0,
                        showNumgyhoe: 0,
                        aboutCheckNoBoxList: {
                            no1: true,
                            no2: true,
                            no3: true,
                            no4: true,
                            no5: true,
                            no6: true,
                            no7: true,
                            no8: true,
                            no9: true,
                            no10: true,
                            gyh: true,
                        },
                        aboutCheckRoadBoxList: {
                            ou: true,
                            oe: true,
                            lh: true
                        },
                        aloneCheckRoadBoxList: {
                            ou: true,
                            oe: true,
                            lh: true
                        },
                        aloneSelectNo: 'no1',
                        lmCheckNumBoxList: {
                            no1: true,
                            no2: true,
                            no3: true,
                            no4: true,
                            no5: true,
                            no6: true,
                            no7: true,
                            no8: true,
                            no9: true,
                            no10: true,
                            gyh: true,
                        },
                        lmSelectRoad: 'ou'
                    },


                },
                numberR: {
                    'no1': {qian: 0, hou: 0},
                    'no2': {qian: 0, hou: 0},
                    'no3': {qian: 0, hou: 0},
                    'no4': {qian: 0, hou: 0},
                    'no5': {qian: 0, hou: 0},
                    'no6': {qian: 0, hou: 0},
                    'no7': {qian: 0, hou: 0},
                    'no8': {qian: 0, hou: 0},
                    'no9': {qian: 0, hou: 0},
                    'no10': {qian: 0, hou: 0},
                    filters: {
                        numno1: 1,
                        numno2: 1,
                        numno3: 1,
                        numno4: 1,
                        numno5: 1,
                        numno6: 1,
                        numno7: 1,
                        numno8: 1,
                        numno9: 1,
                        numno10: 1,
                        numTwono1: 1,
                        numTwono2: 1,
                        numTwono3: 1,
                        numTwono4: 1,
                        numTwono5: 1,
                        numTwono6: 1,
                        numTwono7: 1,
                        numTwono8: 1,
                        numTwono9: 1,
                        numTwono10: 1,
                        selectno1: 'qian',
                        selectno2: 'qian',
                        selectno3: 'qian',
                        selectno4: 'qian',
                        selectno5: 'qian',
                        selectno6: 'qian',
                        selectno7: 'qian',
                        selectno8: 'qian',
                        selectno9: 'qian',
                        selectno10: 'qian',
                        selectTwono1: 'qian',
                        selectTwono2: 'qian',
                        selectTwono3: 'qian',
                        selectTwono4: 'qian',
                        selectTwono5: 'qian',
                        selectTwono6: 'qian',
                        selectTwono7: 'qian',
                        selectTwono8: 'qian',
                        selectTwono9: 'qian',
                        selectTwono10: 'qian',
                        btnno1: false,
                        btnno2: false,
                        btnno3: false,
                        btnno4: false,
                        btnno5: false,
                        btnno6: false,
                        btnno7: false,
                        btnno8: false,
                        btnno9: false,
                        btnno10: false,
                        showNumno1: 0,
                        showNumno2: 0,
                        showNumno3: 0,
                        showNumno4: 0,
                        showNumno5: 0,
                        showNumno6: 0,
                        showNumno7: 0,
                        showNumno8: 0,
                        showNumno9: 0,
                        showNumno10: 0,
                        checkBoxList: {
                            no1: true,
                            no2: true,
                            no3: true,
                            no4: true,
                            no5: true,
                            no6: true,
                            no7: true,
                            no8: true,
                            no9: true,
                            no10: true,
                        }
                    }
                },

            }
        },
        components: {
            MyHeader,
            MyFoot,
            PKtop,
            Datepicker
        },
        computed: {
            ...mapGetters(['lotteryKey', 'pageTabSelect']),
        },
        methods: {
            numberRTableFilterFun(key, no) {
                this.numberR.filters['num' + no] = this.numberR.filters['numTwo' + no];
                this.numberR.filters['select' + no] = this.numberR.filters['selectTwo' + no];
                this.numberR.filters['btn' + no] = true;
                this.numberR.filters['showNum' + no] = 0;
                this.luzhuList[no][key].forEach(item => {
                    item.forEach(obj => {
                        if (obj[0] == this.numberR.filters['select' + no] && obj.length >= this.numberR.filters['num' + no]) {
                            this.numberR.filters['showNum' + no]++;
                        }
                    })
                })
            },
            numberRCheckBoxFun(index) {
                this.numberR.filters.checkBoxList[index] = !this.numberR.filters.checkBoxList[index];
            },
            numberRCheckAll(flag) {
                for (let key in this.numberR.filters.checkBoxList) {
                    this.numberR.filters.checkBoxList[key] = flag;
                }
            },
            roadATableFilterFun(key, no) {
                this.roadA.filters['num' + key] = this.roadA.filters['numTwo' + key];
                this.roadA.filters['select' + key] = this.roadA.filters['selectTwo' + key];
                this.roadA.filters['btn' + key] = true;
                this.roadA.filters['showNum' + key] = 0;
                this.luzhuList[no][key].forEach(item => {
                    item.forEach(obj => {
                        if (obj[0] == this.roadA.filters['select' + key] && obj.length >= this.roadA.filters['num' + key]) {
                            this.roadA.filters['showNum' + key]++;
                        }
                    })
                })
            },
            roadALmNoFilterAllFun(flag) {
                for (let key in this.roadA.filters.lmCheckNumBoxList) {
                    this.roadA.filters.lmCheckNumBoxList[key] = flag;
                }
            },
            roadALmNoFilterFun(index) {
                this.roadA.filters.lmCheckNumBoxList[index] = !this.roadA.filters.lmCheckNumBoxList[index];
            },
            roadAAloneNoFilterFun(type) {
                this.roadA.filters.aloneSelectNo = type;
            },
            roadAAloneNoAll(flag) {
                for (let key in this.roadA.filters.aloneCheckRoadBoxList) {
                    this.roadA.filters.aloneCheckRoadBoxList[key] = flag;
                }
            },
            roadAAloneFilterRoadFun(index) {
                this.roadA.filters.aloneCheckRoadBoxList[index] = !this.roadA.filters.aloneCheckRoadBoxList[index];
            },
            roadAAboutFilterRoadFun(index) {
                this.roadA.filters.aboutCheckRoadBoxList[index] = !this.roadA.filters.aboutCheckRoadBoxList[index];
            },
            roadAAboutNoAll(flag) {
                for (let key in this.roadA.filters.aboutCheckNoBoxList) {
                    this.roadA.filters.aboutCheckNoBoxList[key] = flag;
                }
            },
            roadAAboutFilterNoFun(index) {
                this.roadA.filters.aboutCheckNoBoxList[index] = !this.roadA.filters.aboutCheckNoBoxList[index];
            },
            roadAModelFun(mode) {
                this.roadA.roadAModeSelect = mode;
            },
            sdRoadSelectFun(type) {
                this.sdRoad.filters.selectRoad = type;
                for (let key in this.sdRoad.filters) {
                    if (key.indexOf('num') != -1 || key.indexOf('showNum') != -1) {
                        this.sdRoad.filters[key] = 1;
                    } else if (key.indexOf('btn') != -1) {
                        this.sdRoad.filters[key] = false;
                    }
                }
                if (type == 'ou') {

                    for (let key in this.sdRoad.filters) {
                        if (key.indexOf('selectTwo') != -1) {
                            this.sdRoad.filters[key] = 'over';
                        }
                    }
                } else {
                    for (let key in this.sdRoad.filters) {
                        if (key.indexOf('selectTwo') != -1) {
                            this.sdRoad.filters[key] = 'odd';
                        }
                    }
                }
            },
            sdRoadCheckBoxFun(key) {
                this.sdRoad.filters.checkBoxList[key] = !this.sdRoad.filters.checkBoxList[key];
            },
            sdRoadCheckBoxAllHandle(flag) {
                for (let key in this.sdRoad.filters.checkBoxList) {
                    this.sdRoad.filters.checkBoxList[key] = flag;
                }
            },
            sdRoadTableFilterFun(key, no) {
                this.sdRoad.filters['num' + no] = this.sdRoad.filters['numTwo' + no];
                this.sdRoad.filters['select' + no] = this.sdRoad.filters['selectTwo' + no];
                this.sdRoad.filters['btn' + no] = true;
                this.sdRoad.filters['showNum' + no] = 0;
                this.luzhuList[no][key].forEach(item => {
                    item.forEach(obj => {
                        if (obj[0] == this.sdRoad.filters['select' + no] && obj.length >= this.sdRoad.filters['num' + no]) {
                            this.sdRoad.filters['showNum' + no]++;
                        }
                    })
                })
            },
            dtRoadCheckBoxFun(key) {
                this.dtRoad.filters.checkBoxList[key] = !this.dtRoad.filters.checkBoxList[key];
            },
            dtRoadCheckBoxAllHandle(flag) {
                this.dtRoad.filters.checkBoxList.lhno1 = flag;
                this.dtRoad.filters.checkBoxList.lhno2 = flag;
                this.dtRoad.filters.checkBoxList.lhno3 = flag;
                this.dtRoad.filters.checkBoxList.lhno4 = flag;
                this.dtRoad.filters.checkBoxList.lhno5 = flag;
            },
            dtRoadTableFilterFun(key, no) {
                this.dtRoad.filters['num' + no] = this.dtRoad.filters['numTwo' + no];
                this.dtRoad.filters['select' + no] = this.dtRoad.filters['selectTwo' + no];
                this.dtRoad.filters['btn' + no] = true;
                this.dtRoad.filters['showNum' + no] = 0;
                this.luzhuList['no' + no][key].forEach(item => {
                    item.forEach(obj => {
                        if (obj[0] == this.dtRoad.filters['select' + no] && obj.length >= this.dtRoad.filters['num' + no]) {
                            this.dtRoad.filters['showNum' + no]++;
                        }
                    })
                })
            },
            tableFilterFun(key) {


                if (key == 'gyhou') {
                    this.gyhLZOUShowNum = 0;
                    this.gyhLZOUNum = this.gyhLZOUNumTwo;
                    this.gyhLZOUBtnFlag = true;
                    this.gyhLZOUSelect = this.gyhLZOUSelectTwo;
                    this.luzhuList.gyh.gyhou.forEach(item => {
                        item.forEach(obj => {
                            if (obj[0] == this.gyhLZOUSelect && obj.length >= this.gyhLZOUNum) {
                                this.gyhLZOUShowNum++;
                                return;
                            }
                        })
                    })
                } else {
                    this.gyhLZOEShowNum = 0;
                    this.gyhLZOENum = this.gyhLZOENumTwo;
                    this.gyhLZOEBtnFlag = true;
                    this.gyhLZOESelect = this.gyhLZOESelectTwo;
                    this.luzhuList.gyh.gyhoe.forEach(item => {
                        item.forEach(obj => {
                            if (obj[0] == this.gyhLZOESelect && obj.length >= this.gyhLZOENum) {

                                this.gyhLZOEShowNum++;
                                return;
                            }
                        })
                    })
                }
            },
            clearFilterInfo() {
                if (this.pageTabSelect == 'gyhLZ') {
                    this.gyhLZOEBtnFlag = false;
                    this.gyhLZOUBtnFlag = false;
                    this.gyhLZOEShowNum = 0;
                    this.gyhLZOUShowNum = 0;
                    this.gyhLZOENumTwo = 1;
                    this.gyhLZOUNumTwo = 1;
                    this.gyhLZOENum = 1;
                    this.gyhLZOUNum = 1;
                    this.gyhLZOUSelectTwo = 'over';
                    this.gyhLZOESelectTwo = 'odd';
                    this.gyhLZOUSelect = 'over';
                    this.gyhLZOESelect = 'odd';
                } else if (this.pageTabSelect == 'dtRoad') {
                    this.dtRoad = {
                        'lhno1': {dragon: 0, tiger: 0},
                        'lhno2': {dragon: 0, tiger: 0},
                        'lhno3': {dragon: 0, tiger: 0},
                        'lhno4': {dragon: 0, tiger: 0},
                        'lhno5': {dragon: 0, tiger: 0},
                        filters: {
                            num1: 1,
                            num2: 1,
                            num3: 1,
                            num4: 1,
                            num5: 1,
                            numTwo1: 1,
                            numTwo2: 1,
                            numTwo3: 1,
                            numTwo4: 1,
                            numTwo5: 1,
                            select1: 'dragon',
                            select2: 'dragon',
                            select3: 'dragon',
                            select4: 'dragon',
                            select5: 'dragon',
                            selectTwo1: 'dragon',
                            selectTwo2: 'dragon',
                            selectTwo3: 'dragon',
                            selectTwo4: 'dragon',
                            selectTwo5: 'dragon',
                            btn1: false,
                            btn2: false,
                            btn3: false,
                            btn4: false,
                            btn5: false,
                            showNum1: 0,
                            showNum2: 0,
                            showNum3: 0,
                            showNum4: 0,
                            showNum5: 0,
                            checkBoxList: {
                                lhno1: true,
                                lhno2: true,
                                lhno3: true,
                                lhno4: true,
                                lhno5: true,
                            }
                        },
                    };
                } else if (this.pageTabSelect == 'sdRoad') {
                    this.sdRoad = {
                        'no1': {over: 0, under: 0, odd: 0, even: 0},
                        'no2': {over: 0, under: 0, odd: 0, even: 0},
                        'no3': {over: 0, under: 0, odd: 0, even: 0},
                        'no4': {over: 0, under: 0, odd: 0, even: 0},
                        'no5': {over: 0, under: 0, odd: 0, even: 0},
                        'no6': {over: 0, under: 0, odd: 0, even: 0},
                        'no7': {over: 0, under: 0, odd: 0, even: 0},
                        'no8': {over: 0, under: 0, odd: 0, even: 0},
                        'no9': {over: 0, under: 0, odd: 0, even: 0},
                        'no10': {over: 0, under: 0, odd: 0, even: 0},
                        'gyh': {over: 0, under: 0, odd: 0, even: 0},
                        filters: {
                            selectRoad: 'ou',
                            numno1: 1,
                            numno2: 1,
                            numno3: 1,
                            numno4: 1,
                            numno5: 1,
                            numno6: 1,
                            numno7: 1,
                            numno8: 1,
                            numno9: 1,
                            numno10: 1,
                            numgyh: 1,
                            numTwono1: 1,
                            numTwono2: 1,
                            numTwono3: 1,
                            numTwono4: 1,
                            numTwono5: 1,
                            numTwono6: 1,
                            numTwono7: 1,
                            numTwono8: 1,
                            numTwono9: 1,
                            numTwono10: 1,
                            numTwogyh: 1,
                            selectno1: 'over',
                            selectno2: 'over',
                            selectno3: 'over',
                            selectno4: 'over',
                            selectno5: 'over',
                            selectno6: 'over',
                            selectno7: 'over',
                            selectno8: 'over',
                            selectno9: 'over',
                            selectno10: 'over',
                            selectgyh: 'over',
                            selectTwono1: 'over',
                            selectTwono2: 'over',
                            selectTwono3: 'over',
                            selectTwono4: 'over',
                            selectTwono5: 'over',
                            selectTwono6: 'over',
                            selectTwono7: 'over',
                            selectTwono8: 'over',
                            selectTwono9: 'over',
                            selectTwono10: 'over',
                            selectTwogyh: 'over',
                            btnno1: false,
                            btnno2: false,
                            btnno3: false,
                            btnno4: false,
                            btnno5: false,
                            btnno6: false,
                            btnno7: false,
                            btnno8: false,
                            btnno9: false,
                            btnno10: false,
                            btngyh: false,
                            showNumno1: 0,
                            showNumno2: 0,
                            showNumno3: 0,
                            showNumno4: 0,
                            showNumno5: 0,
                            showNumno6: 0,
                            showNumno7: 0,
                            showNumno8: 0,
                            showNumno9: 0,
                            showNumno10: 0,
                            showNumgyh: 0,
                            checkBoxList: {
                                no1: true,
                                no2: true,
                                no3: true,
                                no4: true,
                                no5: true,
                                no6: true,
                                no7: true,
                                no8: true,
                                no9: true,
                                no10: true,
                                gyh: true,
                            }
                        }
                    };
                } else if (this.pageTabSelect == 'roadA') {
                    this.roadA = {
                        roadAModeSelect: 'about',
                        'no1': {over: 0, under: 0, odd: 0, even: 0, dragon: 0, tiger: 0},
                        'no2': {over: 0, under: 0, odd: 0, even: 0, dragon: 0, tiger: 0},
                        'no3': {over: 0, under: 0, odd: 0, even: 0, dragon: 0, tiger: 0},
                        'no4': {over: 0, under: 0, odd: 0, even: 0, dragon: 0, tiger: 0},
                        'no5': {over: 0, under: 0, odd: 0, even: 0, dragon: 0, tiger: 0},
                        'no6': {over: 0, under: 0, odd: 0, even: 0},
                        'no7': {over: 0, under: 0, odd: 0, even: 0},
                        'no8': {over: 0, under: 0, odd: 0, even: 0},
                        'no9': {over: 0, under: 0, odd: 0, even: 0},
                        'no10': {over: 0, under: 0, odd: 0, even: 0},
                        'gyh': {over: 0, under: 0, odd: 0, even: 0},
                        filters: {
                            numno1ou: 1,
                            numno1oe: 1,
                            numlhno1: 1,
                            numno2ou: 1,
                            numno2oe: 1,
                            numlhno2: 1,
                            numno3ou: 1,
                            numno3oe: 1,
                            numlhno3: 1,
                            numno4ou: 1,
                            numno4oe: 1,
                            numlhno4: 1,
                            numno5ou: 1,
                            numno5oe: 1,
                            numlhno5: 1,
                            numno6ou: 1,
                            numno6oe: 1,
                            numno7ou: 1,
                            numno7oe: 1,
                            numno8ou: 1,
                            numno8oe: 1,
                            numno9ou: 1,
                            numno9oe: 1,
                            numno10ou: 1,
                            numno10oe: 1,
                            numgyhou: 1,
                            numgyhoe: 1,
                            numTwono1ou: 1,
                            numTwono1oe: 1,
                            numTwolhno1: 1,
                            numTwono2ou: 1,
                            numTwono2oe: 1,
                            numTwolhno2: 1,
                            numTwono3ou: 1,
                            numTwono3oe: 1,
                            numTwolhno3: 1,
                            numTwono4ou: 1,
                            numTwono4oe: 1,
                            numTwolhno4: 1,
                            numTwono5ou: 1,
                            numTwono5oe: 1,
                            numTwolhno5: 1,
                            numTwono6ou: 1,
                            numTwono6oe: 1,
                            numTwono7ou: 1,
                            numTwono7oe: 1,
                            numTwono8ou: 1,
                            numTwono8oe: 1,
                            numTwono9ou: 1,
                            numTwono9oe: 1,
                            numTwono10ou: 1,
                            numTwono10oe: 1,
                            numTwogyhou: 1,
                            numTwogyhoe: 1,
                            selectno1ou: 'over',
                            selectno1oe: 'odd',
                            selectlhno1: 'dragon',
                            selectno2ou: 'over',
                            selectno2oe: 'odd',
                            selectlhno2: 'dragon',
                            selectno3ou: 'over',
                            selectno3oe: 'odd',
                            selectlhno3: 'dragon',
                            selectno4ou: 'over',
                            selectno4oe: 'odd',
                            selectlhno4: 'dragon',
                            selectno5ou: 'over',
                            selectno5oe: 'odd',
                            selectlhno5: 'dragon',
                            selectno6ou: 'over',
                            selectno6oe: 'odd',
                            selectno7ou: 'over',
                            selectno7oe: 'odd',
                            selectno8ou: 'over',
                            selectno8oe: 'odd',
                            selectno9ou: 'over',
                            selectno9oe: 'odd',
                            selectno10ou: 'over',
                            selectno10oe: 'odd',
                            selectgyhou: 'over',
                            selectgyhoe: 'odd',
                            selectTwono1ou: 'over',
                            selectTwono1oe: 'odd',
                            selectTwolhno1: 'dragon',
                            selectTwono2ou: 'over',
                            selectTwono2oe: 'odd',
                            selectTwolhno2: 'dragon',
                            selectTwono3ou: 'over',
                            selectTwono3oe: 'odd',
                            selectTwolhno3: 'dragon',
                            selectTwono4ou: 'over',
                            selectTwono4oe: 'odd',
                            selectTwolhno4: 'dragon',
                            selectTwono5ou: 'over',
                            selectTwono5oe: 'odd',
                            selectTwolhno5: 'dragon',
                            selectTwono6ou: 'over',
                            selectTwono6oe: 'odd',
                            selectTwono7ou: 'over',
                            selectTwono7oe: 'odd',
                            selectTwono8ou: 'over',
                            selectTwono8oe: 'odd',
                            selectTwono9ou: 'over',
                            selectTwono9oe: 'odd',
                            selectTwono10ou: 'over',
                            selectTwono10oe: 'odd',
                            selectTwogyhou: 'over',
                            selectTwogyhoe: 'odd',
                            btnno1ou: false,
                            btnno1oe: false,
                            btnlhno1: false,
                            btnno2ou: false,
                            btnno2oe: false,
                            btnlhno2: false,
                            btnno3ou: false,
                            btnno3oe: false,
                            btnlhno3: false,
                            btnno4ou: false,
                            btnno4oe: false,
                            btnlhno4: false,
                            btnno5ou: false,
                            btnno5oe: false,
                            btnlhno5: false,
                            btnno6ou: false,
                            btnno6oe: false,
                            btnno7ou: false,
                            btnno7oe: false,
                            btnno8ou: false,
                            btnno8oe: false,
                            btnno9ou: false,
                            btnno9oe: false,
                            btnno10ou: false,
                            btnno10oe: false,
                            btngyhou: false,
                            btngyhoe: false,
                            showNumno1ou: 0,
                            showNumno1oe: 0,
                            showNumlhno1: 0,
                            showNumno2ou: 0,
                            showNumno2oe: 0,
                            showNumlhno2: 0,
                            showNumno3ou: 0,
                            showNumno3oe: 0,
                            showNumlhno3: 0,
                            showNumno4ou: 0,
                            showNumno4oe: 0,
                            showNumlhno4: 0,
                            showNumno5ou: 0,
                            showNumno5oe: 0,
                            showNumlhno5: 0,
                            showNumno6ou: 0,
                            showNumno6oe: 0,
                            showNumno7ou: 0,
                            showNumno7oe: 0,
                            showNumno8ou: 0,
                            showNumno8oe: 0,
                            showNumno9ou: 0,
                            showNumno9oe: 0,
                            showNumno10ou: 0,
                            showNumno10oe: 0,
                            showNumgyhou: 0,
                            showNumgyhoe: 0,
                            aboutCheckNoBoxList: {
                                no1: true,
                                no2: true,
                                no3: true,
                                no4: true,
                                no5: true,
                                no6: true,
                                no7: true,
                                no8: true,
                                no9: true,
                                no10: true,
                                gyh: true,
                            },
                            aboutCheckRoadBoxList: {
                                ou: true,
                                oe: true,
                                lh: true
                            },
                            aloneCheckRoadBoxList: {
                                ou: true,
                                oe: true,
                                lh: true
                            },
                            aloneSelectNo: 'no1',
                            lmCheckNumBoxList: {
                                no1: true,
                                no2: true,
                                no3: true,
                                no4: true,
                                no5: true,
                                no6: true,
                                no7: true,
                                no8: true,
                                no9: true,
                                no10: true,
                                gyh: true,
                            },
                            lmSelectRoad: 'ou'
                        },


                    };
                } else if (this.pageTabSelect == 'numberR') {
                    this.numberR = {
                        'no1': {qian: 0, hou: 0},
                        'no2': {qian: 0, hou: 0},
                        'no3': {qian: 0, hou: 0},
                        'no4': {qian: 0, hou: 0},
                        'no5': {qian: 0, hou: 0},
                        'no6': {qian: 0, hou: 0},
                        'no7': {qian: 0, hou: 0},
                        'no8': {qian: 0, hou: 0},
                        'no9': {qian: 0, hou: 0},
                        'no10': {qian: 0, hou: 0},
                        filters: {
                            numno1: 1,
                            numno2: 1,
                            numno3: 1,
                            numno4: 1,
                            numno5: 1,
                            numno6: 1,
                            numno7: 1,
                            numno8: 1,
                            numno9: 1,
                            numno10: 1,
                            numTwono1: 1,
                            numTwono2: 1,
                            numTwono3: 1,
                            numTwono4: 1,
                            numTwono5: 1,
                            numTwono6: 1,
                            numTwono7: 1,
                            numTwono8: 1,
                            numTwono9: 1,
                            numTwono10: 1,
                            selectno1: 'qian',
                            selectno2: 'qian',
                            selectno3: 'qian',
                            selectno4: 'qian',
                            selectno5: 'qian',
                            selectno6: 'qian',
                            selectno7: 'qian',
                            selectno8: 'qian',
                            selectno9: 'qian',
                            selectno10: 'qian',
                            selectTwono1: 'qian',
                            selectTwono2: 'qian',
                            selectTwono3: 'qian',
                            selectTwono4: 'qian',
                            selectTwono5: 'qian',
                            selectTwono6: 'qian',
                            selectTwono7: 'qian',
                            selectTwono8: 'qian',
                            selectTwono9: 'qian',
                            selectTwono10: 'qian',
                            btnno1: false,
                            btnno2: false,
                            btnno3: false,
                            btnno4: false,
                            btnno5: false,
                            btnno6: false,
                            btnno7: false,
                            btnno8: false,
                            btnno9: false,
                            btnno10: false,
                            showNumno1: 0,
                            showNumno2: 0,
                            showNumno3: 0,
                            showNumno4: 0,
                            showNumno5: 0,
                            showNumno6: 0,
                            showNumno7: 0,
                            showNumno8: 0,
                            showNumno9: 0,
                            showNumno10: 0,
                            checkBoxList: {
                                no1: true,
                                no2: true,
                                no3: true,
                                no4: true,
                                no5: true,
                                no6: true,
                                no7: true,
                                no8: true,
                                no9: true,
                                no10: true,
                            }
                        }
                    };
                }
            },
            customFormatter(date, flag) {
                this.dateStr = this.$moment(date).format('YYYY-MM-DD');
                if (!flag) {
                    this.totalDateTime = this.$moment(date).format('MM-DD');
                }
                if (this.pageTabSelect == 'instantDraw' || this.pageTabSelect=='lhtj' || this.pageTabSelect=='lmsjtj'
                    || this.pageTabSelect=='gyhlmls' || this.pageTabSelect=='dsdxls' || this.pageTabSelect=='lrfx' ) {
                    this.getHisList();
                } else if (this.pageTabSelect == 'gyhLZ' || this.pageTabSelect == 'numberR' || this.pageTabSelect == 'dtRoad' || this.pageTabSelect == 'sdRoad'
                    || this.pageTabSelect == 'roadA') {
                    this.clearFilterInfo();
                    this.getLuzhuFun(this.lotteryKey);
                }
                return this.$moment(date).format('YYYY-MM-DD')
            },
            changeDate(type) {
                this.dayType = type;

                let dateTime = new Date();
                dateTime = dateTime.setDate(dateTime.getDate() + type);
                dateTime = new Date(dateTime)
                if (type == 0) {
                    this.totalDateTime = '今天';
                } else if (type == -1) {
                    this.totalDateTime = '昨天';
                } else if (type == -2) {
                    this.totalDateTime = '前天';
                }
                this.customFormatter(dateTime, true);
            },
            readResultRoad() {
                let self = this;
                let changlongArr = [];
                self.$api.Lottery.getLotteryRoad(this.lotteryKey + '/' + this.dateStr).then(val => {
                    if (val.success) {
                        let arr = val.data.changlong;
                        for (let obj of arr) {
                            for (let key in obj) {
                                let param = {
                                    'type': key.split('_')[0],
                                    'oddsKey': key.split('_')[1],
                                    'number': obj[key]
                                };
                                changlongArr.push(param);
                            }
                        }
                        self.changlongList = changlongArr;
                    }
                });
            },
            clearResultScreenFun() {
                this.resultNumScreen.splice(0, this.resultNumScreen.length);
                this.resultTypeScreen = '';
            },
            numScreenFun(num) {
                if (!this.topLi.hmfb) {
                    this.topLi.hmfb = true;
                }
                let flag = this.resultNumScreen.findIndex(function (value, index, arr) {
                    return value == this.val
                }, {val: num});
                if (this.resultTypeScreen) {
                    this.resultTypeScreen = ''
                }
                ;

                if (flag != -1) {
                    this.resultNumScreen.splice(flag, 1);
                } else {
                    this.resultNumScreen.push(num);
                }
            },
            numTypeScreenFun(type) {
                this.resultNumScreen.splice(0, this.resultNumScreen.length);

                this.resultTypeScreen == type ? this.resultTypeScreen = '' : this.resultTypeScreen = type;
            },
            resultClassCss(num, listIndex, resultIndex) {
                let cssReturn = '';
                if (this.resultShowType == 'num') {
                    cssReturn = 'numsm' + num;
                } else if (this.resultShowType == 'numOu') {
                    if (num > 5) {
                        cssReturn = 'bignum';
                    } else {
                        cssReturn = 'smallnum';
                    }
                } else if (this.resultShowType == 'numOe') {
                    if (num % 2 == 0) {
                        cssReturn = 'evennum';
                    } else {
                        cssReturn = 'singularnum';
                    }
                }
                if (this.topLi.hmfb) {
                    if (this.resultTypeScreen) {
                        if (this.resultTypeScreen == 'over') {
                            return num > 5 ? cssReturn : cssReturn + ' selectedOpacity';
                        } else if (this.resultTypeScreen == 'under') {
                            return num <= 5 ? cssReturn : cssReturn + ' selectedOpacity';
                        } else if (this.resultTypeScreen == 'odd') {
                            return num % 2 != 0 ? cssReturn : cssReturn + ' selectedOpacity';
                        } else if (this.resultTypeScreen == 'even') {
                            return num % 2 == 0 ? cssReturn : cssReturn + ' selectedOpacity';
                        } else if (this.resultTypeScreen == 'pair') {
                            if (listIndex == 0) {
                                return this.hisList[listIndex + 1].result[resultIndex] == num ? cssReturn : cssReturn + ' selectedOpacity';
                            } else {
                                if (listIndex == this.hisList.length - 1) {
                                    return this.hisList[listIndex - 1].result[resultIndex] == num ? cssReturn : cssReturn + ' selectedOpacity';
                                }
                                return this.hisList[listIndex - 1].result[resultIndex] == num ? cssReturn : this.hisList[listIndex + 1].result[resultIndex] == num ? cssReturn : cssReturn + ' selectedOpacity';
                            }
                        }
                    } else if (this.resultNumScreen.length > 0) {
                        let index = this.resultNumScreen.findIndex(function (value, index, arr) {
                            return value == this.val
                        }, {val: num});
                        return index != -1 ? cssReturn : cssReturn + ' selectedOpacity';
                    }
                }
                return cssReturn;

            },
            onTopLi(num) {
                if (num == 1) {
                    this.topLi.jrsm = this.topLi.jrsm ? false : true;
                }
                if (num == 2) {

                    this.topLi.cltx = this.topLi.cltx ? false : true;
                    if (this.topLi.cltx) {
                        this.readResultRoad();
                    }
                }
                if (num == 3) {
                    this.resultNumScreen.splice(0, this.resultNumScreen.length);
                    this.resultTypeScreen = '';
                    this.topLi.hmfb = this.topLi.hmfb ? false : true;
                }
            },
            getHisList() {
                this.$api.Lottery.getHisByDayList(this.lotteryKey + "/" + this.dateStr).then(val => {
                    this.hisList = [];
                    if (val.success) {
                        this.hisList = val.data;
                        let jrsm = [];
                        for (let i = 0; i < 10; i++) {
                            jrsm.push({
                                dan: 0,
                                suan: 0,
                                da: 0,
                                xiao: 0,
                            })
                        }
                        let gylh = {
                            gyhdan: 0,
                            gyhsuan: 0,
                            gyhda: 0,
                            gyhxiao: 0,
                            lh11: 0,
                            lh12: 0,
                            lh21: 0,
                            lh22: 0,
                            lh31: 0,
                            lh32: 0,
                            lh41: 0,
                            lh42: 0,
                            lh51: 0,
                            lh52: 0
                        };
                        this.gylhMap = gylh;
                        this.hisList.forEach(items => {
                            if (items.result) {
                                let nums = items.result.split(",");
                                let map = [];
                                for (let i = 0; i < nums.length; i++) {
                                    map.push(nums[i])
                                    nums[i] > 5 ? jrsm[i].da += 1 : jrsm[i].xiao += 1;
                                    (nums[i] % 2) == 1 ? jrsm[i].dan += 1 : jrsm[i].suan += 1;
                                }
                                items.result = map;
                                items.gyh15lh = this.computeGYH15LH(items.result);
                                items.gyh15lh.dx == 'OVER' ? gylh.gyhda += 1 : gylh.gyhxiao += 1;
                                items.gyh15lh.ds == 'ODD' ? gylh.gyhdan += 1 : gylh.gyhsuan += 1;
                                items.gyh15lh.lh1 == 'DRAGON' ? gylh.lh11 += 1 : gylh.lh12 += 1;
                                items.gyh15lh.lh2 == 'DRAGON' ? gylh.lh21 += 1 : gylh.lh22 += 1;
                                items.gyh15lh.lh3 == 'DRAGON' ? gylh.lh31 += 1 : gylh.lh32 += 1;
                                items.gyh15lh.lh4 == 'DRAGON' ? gylh.lh41 += 1 : gylh.lh42 += 1;
                                items.gyh15lh.lh5 == 'DRAGON' ? gylh.lh51 += 1 : gylh.lh52 += 1;
                            }
                        })
                        //this.hisList = this.hisList.slice(0,200);
                        this.jrsmList = jrsm;
                        this.gylhMap = gylh;

                    }
                    this.changePageTabSelect(this.pageTabSelect);
                });

                if (this.topLi.cltx) {
                    this.readResultRoad();
                }
            },
            getLuzhuFun(params) {
                let self = this;
                this.$api.Lottery.getLotteryRoad(params + '/' + self.dateStr).then(data => {
                    let luzhuParams = {};
                    if (self.pageTabSelect == 'gyhLZ') {
                        self.gyhLZTotal.over = 0;
                        self.gyhLZTotal.under = 0;
                        self.gyhLZTotal.odd = 0;
                        self.gyhLZTotal.even = 0;
                        if (data.data.gyhou) {
                            data.data.gyhou.forEach(val => {
                                if (val == 'over') {
                                    self.gyhLZTotal.over++;
                                } else if (val == 'under') {
                                    self.gyhLZTotal.under++;
                                }
                            })
                        }
                        if (data.data.gyhoe) {
                            data.data.gyhoe.forEach(val => {
                                if (val == 'odd') {
                                    self.gyhLZTotal.odd++;
                                } else if (val == 'even') {
                                    self.gyhLZTotal.even++;
                                }
                            })
                        }
                    } else if (self.pageTabSelect == 'dtRoad') {

                        for (let i = 1; i <= 5; i++) {
                            let dragon = 0;
                            let tiger = 0;
                            data.data['lhno' + i].forEach(val => {
                                if (val == 'dragon') {
                                    dragon++;
                                } else if (val == 'tiger') {
                                    tiger++;
                                }
                            })
                            self.dtRoad['lhno' + i].dragon = dragon;
                            self.dtRoad['lhno' + i].tiger = tiger;
                        }
                    }

                    for (let obj in data.data) {
                        if (-1 != obj.indexOf('gyh')) {
                            luzhuParams[obj] = this.$Utils.getArr(data.data[obj]);
                        }
                        if (obj.indexOf('no') != -1) {
                            luzhuParams[obj] = this.$Utils.getArr(data.data[obj]);
                        } else if (-1 == obj.indexOf('gyh')) {
                            if (obj == 'no1') {
                                self.placingNumber = data.data[obj];
                            }
                            luzhuParams[obj] = data.data[obj];
                        }
                    }
                    for (let key in luzhuParams) {
                        if (key == 'no10' || key == 'no10val' || key == 'no10oe' || key == 'no10ou' || key == 'no10qh') {
                            self.luzhuList.no10[key] = luzhuParams[key];
                        } else {
                            if (-1 != key.indexOf('gyh')) {
                                self.luzhuList.gyh[key] = luzhuParams[key];
                            } else if (-1 != key.indexOf('no1')) {
                                self.luzhuList.no1[key] = luzhuParams[key];
                            } else if (-1 != key.indexOf('no2')) {
                                self.luzhuList.no2[key] = luzhuParams[key];
                            } else if (-1 != key.indexOf('no3')) {
                                self.luzhuList.no3[key] = luzhuParams[key];
                            } else if (-1 != key.indexOf('no4')) {
                                self.luzhuList.no4[key] = luzhuParams[key];
                            } else if (-1 != key.indexOf('no5')) {
                                self.luzhuList.no5[key] = luzhuParams[key];
                            } else if (-1 != key.indexOf('no6')) {
                                self.luzhuList.no6[key] = luzhuParams[key];
                            } else if (-1 != key.indexOf('no7')) {
                                self.luzhuList.no7[key] = luzhuParams[key];
                            } else if (-1 != key.indexOf('no8')) {
                                self.luzhuList.no8[key] = luzhuParams[key];
                            } else if (-1 != key.indexOf('no9')) {
                                self.luzhuList.no9[key] = luzhuParams[key];
                            }
                        }
                    }
                    if (self.pageTabSelect == 'sdRoad') {
                        for (let arrKey in self.luzhuList) {

                            for (let key in self.luzhuList[arrKey]) {
                                let num1 = 0;
                                let num2 = 0;
                                let type = ['1', '2'];
                                if (key.indexOf('ou') != -1 || key.indexOf('oe') != -1) {
                                    self.luzhuList[arrKey][key].forEach(val => {
                                        val.forEach(obj => {
                                            obj.forEach(item => {
                                                if (item == 'over') {
                                                    num1++;
                                                    type[0] = 'over';
                                                } else if (item == 'under') {
                                                    num2++;
                                                    type[1] = 'under';
                                                } else if (item == 'odd') {
                                                    num1++;
                                                    type[0] = 'odd';
                                                } else if (item == 'even') {
                                                    num2++;
                                                    type[1] = 'even';
                                                }
                                            })
                                        })
                                    })
                                    self.sdRoad[arrKey][type[0]] = num1;
                                    self.sdRoad[arrKey][type[1]] = num2;
                                }
                            }
                        }
                    } else if (self.pageTabSelect == 'roadA') {
                        for (let arrKey in self.luzhuList) {

                            for (let key in self.luzhuList[arrKey]) {
                                let num1 = 0;
                                let num2 = 0;
                                let type = ['1', '2'];
                                if (key.indexOf('ou') != -1 || key.indexOf('oe') != -1 || key.indexOf('lh') != -1) {
                                    self.luzhuList[arrKey][key].forEach(val => {
                                        val.forEach(obj => {
                                            obj.forEach(item => {
                                                if (item == 'over') {
                                                    num1++;
                                                    type[0] = 'over';
                                                } else if (item == 'under') {
                                                    num2++;
                                                    type[1] = 'under';
                                                } else if (item == 'odd') {
                                                    num1++;
                                                    type[0] = 'odd';
                                                } else if (item == 'even') {
                                                    num2++;
                                                    type[1] = 'even';
                                                } else if (item == 'dragon') {
                                                    num1++;
                                                    type[0] = 'dragon';
                                                } else if (item == 'tiger') {
                                                    num2++;
                                                    type[1] = 'tiger';
                                                }
                                            })

                                        })

                                    })

                                    self.roadA[arrKey][type[0]] = num1;
                                    self.roadA[arrKey][type[1]] = num2;

                                }

                            }


                        }
                    } else if (self.pageTabSelect == 'numberR') {
                        for (let arrKey in self.luzhuList) {

                            for (let key in self.luzhuList[arrKey]) {
                                let num1 = 0;
                                let num2 = 0;
                                let type = ['1', '2'];
                                if (key.indexOf('qh') != -1) {
                                    self.luzhuList[arrKey][key].forEach(val => {
                                        val.forEach(obj => {
                                            obj.forEach(item => {
                                                if (item == 'qian') {
                                                    num1++;
                                                    type[0] = 'qian';
                                                } else if (item == 'hou') {
                                                    num2++;
                                                    type[1] = 'hou';
                                                }
                                            })

                                        })

                                    })
                                    self.numberR[arrKey][type[0]] = num1;
                                    self.numberR[arrKey][type[1]] = num2;
                                }

                            }


                        }
                    }
                    console.log('路珠', self.luzhuList);
                })
            },
            lmsjtjCustom(no) {
                this.lmsjBnt = no;
                this.lmsjList.forEach(item => {
                    item.da = 0;
                    item.xiao = 0;
                    item.dan = 0;
                    item.suan = 0;
                    item.startDate = item[0];
                    item.endDate = item[item.length - 1];
                    item.forEach(ite => {
                        let res = ite.result.split(",");
                        item.da += parseInt(res[no]) > 5 ? 1 : 0;
                        item.xiao += parseInt(res[no]) > 5 ? 0 : 1;
                        item.dan += (parseInt(res[no]) % 2 === 1) ? 1 : 0;
                        item.suan += (parseInt(res[no]) % 2 === 1) ? 0 : 1;
                    })
                })
            },
            jrhmtjColorChange(num){
                return this.jrhmtjType.red && num>=15 && num<=30?'color:red':this.jrhmtjType.blue && num>=31 && num<=40?'color:#1163b1':this.jrhmtjType.green && num>=41?'color:#18ab4c':'';
            },
            cmpLrfx(nums) {
                let on = nums.reduce(function (a, b) {
                    if (b in a) {
                        a[b]++;
                    } else {
                        a[b] = 1
                    }
                    return a
                }, {});
                let arrs = new Map()
                for (let i = 1; i <= 10; i++) {
                    arrs.set(i, on[i] ? on[i] : 0);
                }
                let arrayObj = Array.from(arrs);
                arrayObj.sort(function (a, b) {
                    return b[1] - a[1]
                });
                return arrayObj;

            },
            changePageTabSelect(newVal) {
                if (newVal == 'lrfx') {
                    this.$api.Lottery.getHisBySumList(this.lotteryKey + "/" + 20).then(val => {
                        if (val.success) {
                            let info = [];
                            let arr = val.data;
                            let on1s = [];
                            let on2s = [];
                            let on3s = [];
                            let on4s = [];
                            let on5s = [];
                            let on6s = [];
                            let on7s = [];
                            let on8s = [];
                            let on9s = [];
                            let on10s = [];
                            arr.forEach(item => {
                                let res = item.result.split(",");
                                on1s.push(parseInt(res[0]));
                                on2s.push(parseInt(res[1]));
                                on3s.push(parseInt(res[2]));
                                on4s.push(parseInt(res[3]));
                                on5s.push(parseInt(res[4]));
                                on6s.push(parseInt(res[5]));
                                on7s.push(parseInt(res[6]));
                                on8s.push(parseInt(res[7]));
                                on9s.push(parseInt(res[8]));
                                on10s.push(parseInt(res[9]));
                            })
                            info.push(this.cmpLrfx(on1s))
                            info.push(this.cmpLrfx(on2s))
                            info.push(this.cmpLrfx(on3s))
                            info.push(this.cmpLrfx(on4s))
                            info.push(this.cmpLrfx(on5s))
                            info.push(this.cmpLrfx(on6s))
                            info.push(this.cmpLrfx(on7s))
                            info.push(this.cmpLrfx(on8s))
                            info.push(this.cmpLrfx(on9s))
                            info.push(this.cmpLrfx(on10s))
                            this.rlfxList = info;
                        }
                    })
                }
                if (newVal == 'dsdxls') {
                    let nos = []
                    for (let i = 0; i < 10; i++) {
                        nos.push({noda: 0, noxiao: 0, nodan: 0, nosuan: 0})
                    }
                    this.hisList.forEach((val) => {
                        let res = val.result;
                        for (let i = 0; i < res.length; i++) {
                            nos[i].noda += parseInt(res[i]) > 5 ? 1 : 0;
                            nos[i].noxiao += parseInt(res[i]) > 5 ? 0 : 1;
                            nos[i].nodan += (parseInt(res[i]) % 2 === 1) ? 1 : 0;
                            nos[i].nosuan += (parseInt(res[i]) % 2 === 1) ? 0 : 1;
                        }
                    })
                    this.dxdsHisList = nos;
                }
                if (newVal == 'lmsjtj') {
                    let info = {noda: 0, noxiao: 0, nodan: 0, nosuan: 0};
                    this.$api.Lottery.getHisBySumList(this.lotteryKey + "/" + 200).then(val => {
                        if (val.success) {
                            let array = val.data;
                            let index = 0;
                            let newArray = [];
                            while (index < array.length) {
                                newArray.push(array.slice(index, index += 10));
                            }
                            this.lmsjList = newArray;
                            this.lmsjtjCustom(0);
                        }
                    });
                }
                if (newVal == 'lhtj') {
                    let lhno = {
                        no1l: 0,
                        no1h: 0,
                        no2l: 0,
                        no2h: 0,
                        no3l: 0,
                        no3h: 0,
                        no4l: 0,
                        no4h: 0,
                        no5l: 0,
                        no5h: 0,
                        gyhda: 0,
                        gyhxiao: 0,
                        gyhdan: 0,
                        gyhsuan: 0,
                    };
                    this.hisList.forEach((re) => {
                        lhno.no1l += re.gyh15lh.lh1 == 'DRAGON' ? 1 : 0;
                        lhno.no1h += re.gyh15lh.lh1 != 'DRAGON' ? 1 : 0;
                        lhno.no2l += re.gyh15lh.lh2 == 'DRAGON' ? 1 : 0;
                        lhno.no2h += re.gyh15lh.lh2 != 'DRAGON' ? 1 : 0;
                        lhno.no3l += re.gyh15lh.lh3 == 'DRAGON' ? 1 : 0;
                        lhno.no3h += re.gyh15lh.lh3 != 'DRAGON' ? 1 : 0;
                        lhno.no4l += re.gyh15lh.lh4 == 'DRAGON' ? 1 : 0;
                        lhno.no4h += re.gyh15lh.lh4 != 'DRAGON' ? 1 : 0;
                        lhno.no5l += re.gyh15lh.lh5 == 'DRAGON' ? 1 : 0;
                        lhno.no5h += re.gyh15lh.lh5 != 'DRAGON' ? 1 : 0;
                        lhno.gyhda += re.gyh15lh.dx == 'OVER' ? 1 : 0;
                        lhno.gyhxiao += re.gyh15lh.dx != 'OVER' ? 1 : 0;
                        lhno.gyhdan += re.gyh15lh.ds == 'ODD' ? 1 : 0;
                        lhno.gyhsuan += re.gyh15lh.ds != 'ODD' ? 1 : 0;
                    })
                    this.lhtjList = lhno;
                }
                if (newVal == 'gyhlmls') {
                    let lhno = {
                        gyhda: 0,
                        gyhxiao: 0,
                        gyhdan: 0,
                        gyhsuan: 0,
                    };
                    this.hisList.forEach((re) => {
                        lhno.gyhda += re.gyh15lh.dx == 'OVER' ? 1 : 0;
                        lhno.gyhxiao += re.gyh15lh.dx != 'OVER' ? 1 : 0;
                        lhno.gyhdan += re.gyh15lh.ds == 'ODD' ? 1 : 0;
                        lhno.gyhsuan += re.gyh15lh.ds != 'ODD' ? 1 : 0;
                    })
                    this.gyhlmList = lhno;
                }
                if (newVal == 'jrhmtj') {
                    let numList = [];
                    for (let i = 0; i < 10; i++) {
                        let info = {
                            zk1: 0, wk1: 0,
                            zk2: 0, wk2: 0,
                            zk3: 0, wk3: 0,
                            zk4: 0, wk4: 0,
                            zk5: 0, wk5: 0,
                            zk6: 0, wk6: 0,
                            zk7: 0, wk7: 0,
                            zk8: 0, wk8: 0,
                            zk9: 0, wk9: 0,
                            zk10: 0, wk10: 0,
                        };
                        numList.push(info);
                    }

                    for (let i = this.hisList.length - 1; i >= 0; i--) {
                        let re = this.hisList[i];
                        for (let i = 0; i < 10; i++) {
                            let no = parseInt(re.result[i]);
                            if (i == 0) {
                                for (let j = 0; j < 10; j++) {
                                    numList[j].zk1 += no == (j+1) ? 1 : 0;
                                    numList[j].wk1 += no == (j+1) ? numList[j].wk1 * -1 : 1;
                                }
                            }
                            if (i == 1) {
                                for (let j = 0; j < 10; j++) {
                                    numList[j].zk2 += no == (j+1) ? 1 : 0;
                                    numList[j].wk2 += no == (j+1) ? numList[j].wk2 * -1 : 1;
                                }
                            }
                            if (i == 2) {
                                for (let j = 0; j < 10; j++) {
                                    numList[j].zk3 += no == (j+1) ? 1 : 0;
                                    numList[j].wk3 += no == (j+1) ? numList[j].wk3 * -1 : 1;
                                }
                            }
                            if (i == 3) {
                                for (let j = 0; j < 10; j++) {
                                    numList[j].zk4 += no == (j+1) ? 1 : 0;
                                    numList[j].wk4 += no == (j+1) ? numList[j].wk4 * -1 : 1;
                                }
                            }
                            if (i == 4) {
                                for (let j = 0; j < 10; j++) {
                                    numList[j].zk5 += no == (j+1) ? 1 : 0;
                                    numList[j].wk5 += no == (j+1) ? numList[j].wk5 * -1 : 1;
                                }
                            }
                            if (i == 5) {
                                for (let j = 0; j < 10; j++) {
                                    numList[j].zk6 += no == (j+1) ? 1 : 0;
                                    numList[j].wk6 += no == (j+1) ? numList[j].wk6 * -1 : 1;
                                }
                            }
                            if (i == 6) {
                                for (let j = 0; j < 10; j++) {
                                    numList[j].zk7 += no == (j+1) ? 1 : 0;
                                    numList[j].wk7 += no == (j+1) ? numList[j].wk7 * -1 : 1;
                                }
                            }
                            if (i == 7) {
                                for (let j = 0; j < 10; j++) {
                                    numList[j].zk8 += no == (j+1) ? 1 : 0;
                                    numList[j].wk8 += no == (j+1) ? numList[j].wk8 * -1 : 1;
                                }
                            }
                            if (i == 8) {
                                for (let j = 0; j < 10; j++) {
                                    numList[j].zk9 += no == (j+1) ? 1 : 0;
                                    numList[j].wk9 += no == (j+1) ? numList[j].wk9 * -1 : 1;
                                }
                            }
                            if (i == 9) {
                                for (let j = 0; j < 10; j++) {
                                    numList[j].zk10 += no == (j+1) ? 1 : 0;
                                    numList[j].wk10 += no == (j+1) ? numList[j].wk10 * -1 : 1;
                                }
                            }
                        }
                    }
                    this.jrhmtjList = numList;
                }
            }
        },
        watch: {
            dateStr(newVal, oldVal) {
                newVal = this.$moment(newVal).format('YYYY-MM-DD');
                oldVal = this.$moment(oldVal).format('YYYY-MM-DD');
                if (oldVal != newVal) {
                    this.dateStr = this.$moment(newVal).format('YYYY-MM-DD');
                    //this.getHisList();
                }

            },
            pageTabSelect(newVal,oldVal){
                this.changePageTabSelect(newVal);
            }
        },
        mounted() {
            console.log(this.pageTabSelect)
            this.dateStr = this.$moment(new Date()).format('YYYY-MM-DD');
            if (this.pageTabSelect == 'dtRoad' || this.pageTabSelect == 'gyhLZ' || this.pageTabSelect == 'sdRoad'
                || this.pageTabSelect == 'roadA' || this.pageTabSelect == 'numberR') {
                this.getLuzhuFun(this.lotteryKey);
            } else if (this.pageTabSelect == 'instantDraw' || this.pageTabSelect=='lhtj' || this.pageTabSelect=='lmsjtj'
                || this.pageTabSelect=='gyhlmls' || this.pageTabSelect=='dsdxls' || this.pageTabSelect=='lrfx' ) {
                this.getHisList();
            }

        }
    }
</script>
<style scoped>

</style>
