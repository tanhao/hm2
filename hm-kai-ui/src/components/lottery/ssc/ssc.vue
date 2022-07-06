<template>
    <div class="bodybox mrcltj">
        <MyHeader></MyHeader>
        <!--主采种开始-->
        <PKtop @luzhuActionFun="getLuzhuFun" @getHisList="getHisList"></PKtop>
        <!--默认显示-->
        <div class="zhlzbox margt20" v-if="pageTabSelect=='instantDraw'">
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
                                        :language="languages.zh" @selected="customFormatter"
                                        name="uniquename"></datepicker>
                        </div>
                        <div>选择日期&nbsp;</div>
                    </div>
                </div>
            </div>
        </div>


        <div v-show="pageTabSelect=='instantDraw'" class="kaijiangjl margt20" style="display: block">
            <div class="head">
                <ul class="zoushimap" id="kaijiangjl">
                    <li class="kaijiltit kaijjlColor">开奖记录</li>
                    <li id="jrsmtj" :class="topLi.jrsm?'selected':''" @click="onTopLi(1)"><a href="javascript:">今日双面/号码统计</a>
                        <i></i></li>
                    <li id="cltx" :class="topLi.cltx?'selected':''" @click="onTopLi(2)"><a href="javascript:"> 长龙提醒</a>
                        <i></i></li>
                    <li id="hmfb" :class="topLi.hmfb?'selected':''" @click="onTopLi(3)"><a href="javascript:">号码分布</a>
                        <i></i></li>
                </ul>
            </div>
            <div class="listcontent">
                <!--今日双面统计-->
                <div class="jrsmtj" :style="topLi.jrsm?'display: block':'display: none'">
                    <div class="headtxt"> 今日双面统计</div>
                    <table cellpadding="1" cellspacing="1" border="0">
                        <tbody>
                        <tr>
                            <th>号码</th>
                            <template v-for="indx in 10">
                                <th>{{indx}}</th>
                            </template>
                        </tr>

                        <tr v-if="jrsmList" id="shuanmiandata">
                            <td>出现次数</td>
                            <template v-for="list in jrsmList">
                                <td>{{list}}</td>
                            </template>
                        </tr>
                        </tbody>
                    </table>
                    <table class="secondtb" cellpadding="1" cellspacing="1" border="0">
                        <tbody>
                        <tr>
                            <th rowspan="2">球次</th>
                            <th colspan="4">总和</th>
                            <th colspan="4">第一球</th>
                            <th colspan="4">第二球</th>
                            <th colspan="4">第三球</th>
                            <th colspan="4">第四球</th>
                            <th colspan="4">第五球</th>
                        </tr>
                        <tr>
                            <template v-for="indx in 6">
                                <th>单</th>
                                <th>双</th>
                                <th>大</th>
                                <th>小</th>
                            </template>
                        </tr>
                        <tr id="gylhcs">
                            <td>出现次数</td>
                            <template v-for="gyl in gylhMap">
                                <td>{{gyl.dan}}</td>
                                <td>{{gyl.suan}}</td>
                                <td>{{gyl.da}}</td>
                                <td>{{gyl.xiao}}</td>
                            </template>
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
                                <li>
                                    {{$t('ssclz_'+item.type)}}：&nbsp;&nbsp;
                                    <span v-if="item.oddsKey!='draw'">{{$t(item.oddsKey.toUpperCase())}}</span><span
                                    v-else>和</span>&nbsp;&nbsp;<span :style="item.number>=5?'color:#f11821':''">{{item.number}}&nbsp;&nbsp;</span>期
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
                            <li class="kaijiltit">查看车号分布：</li>
                            <li class="01" @click="numScreenFun('0')"
                                :class="resultNumScreen.find((num)=>{return num=='0'})?'selected':''"><a
                                href="javascript:">号码0</a> <i></i></li>
                            <li class="02" @click="numScreenFun('1')"
                                :class="resultNumScreen.find((num)=>{return num=='1'})?'selected':''"><a
                                href="javascript:">号码1</a> <i></i></li>
                            <li class="03" @click="numScreenFun('2')"
                                :class="resultNumScreen.find((num)=>{return num=='2'})?'selected':''"><a
                                href="javascript:">号码2</a> <i></i></li>
                            <li class="04" @click="numScreenFun('3')"
                                :class="resultNumScreen.find((num)=>{return num=='3'})?'selected':''"><a
                                href="javascript:">号码3</a> <i></i></li>
                            <li class="05" @click="numScreenFun('4')"
                                :class="resultNumScreen.find((num)=>{return num=='4'})?'selected':''"><a
                                href="javascript:">号码4</a> <i></i></li>
                            <li class="06" @click="numScreenFun('5')"
                                :class="resultNumScreen.find((num)=>{return num=='5'})?'selected':''"><a
                                href="javascript:">号码5</a> <i></i></li>
                            <li class="07" @click="numScreenFun('6')"
                                :class="resultNumScreen.find((num)=>{return num=='6'})?'selected':''"><a
                                href="javascript:">号码6</a> <i></i></li>
                            <li class="08" @click="numScreenFun('7')"
                                :class="resultNumScreen.find((num)=>{return num=='7'})?'selected':''"><a
                                href="javascript:">号码7</a> <i></i></li>
                            <li class="09" @click="numScreenFun('8')"
                                :class="resultNumScreen.find((num)=>{return num=='8'})?'selected':''"><a
                                href="javascript:">号码8</a> <i></i></li>
                            <li class="10" @click="numScreenFun('9')"
                                :class="resultNumScreen.find((num)=>{return num=='9'})?'selected':''"><a
                                href="javascript:">号码9</a> <i></i></li>
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
                <div class="jrsmhmtj jrsmhmtj_kai ssc" id="jrsmhmtj">
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
                            <th colspan="3">总和</th>
                            <th>1-5龙虎</th>
                        </tr>
                        <tr v-for="(item,index) in hisList">
                            <td>{{item.actionTimeStr}}</td>
                            <td>{{item.gameNo}}</td>
                            <td class="blueqiu">
                                <ul style="width:240px">
                                    <li v-for="(num,i) in item.result" @click="numScreenFun(num)"
                                        :class="resultClassCss(num,index,i)" style="color:#012537"><i
                                        v-show="resultShowType=='num'">{{num}}</i></li>
                                </ul>
                            </td>
                            <td>{{item.gyh15lh.zh}}</td>
                            <td :style="item.gyh15lh.ds=='EVEN'?'color:red':''">{{$t(item.gyh15lh.ds)}}</td>
                            <td :style="item.gyh15lh.dx=='OVER'?'color:red':''">{{$t(item.gyh15lh.dx)}}</td>
                            <td :style="item.gyh15lh.lh1=='DRAGON'?'color:red':item.gyh15lh.lh1=='DRAW'?'color:green':''">
                                {{$t(item.gyh15lh.lh1)}}
                            </td>
                        </tr>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>

        <!--路珠分析hhh-->
        <div v-if="pageTabSelect=='roadA'" class="zhlzbox margt20">
            <div class="listhead">
                <div class="listheadl pk10">
                    <!--选中加 xxselected-->
                    <span @click="roadAModelFun('about')" :class="roadA.roadAModeSelect=='about'?'zhms xxselected':'zhms'"><i>1</i>综合模式</span>
                    <span @click="roadAModelFun('alone')" :class="roadA.roadAModeSelect=='alone'?'dxms xxselected':'dxms'"><i>2</i>单选模式</span>
                    <span @click="roadAModelFun('lm')" :class="roadA.roadAModeSelect=='lm'?'lmmsse xxselected':'lmmsse'"><i>3</i>两面模式</span>
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
            <div  class="listbox pk10">
                <!--筛选开始-->
                <div class="checkbox">
                    <!--综合模式筛选-->
                    <div v-if="roadA.roadAModeSelect=='about'" id="zhms" class="checkbtnzh">
                        <ul class="zhmsmc">
                            <li class="title">筛选名次：</li>
                            <template v-for="(item,index) in roadA.filters.aboutCheckNoBoxList">
                                <li @click="roadAAboutFilterNoFun(index)" :class="item?'sinli checked':'sinli'"><i>1</i>
                                    <a >{{$t('ssclz_'+index)}}</a>
                                </li>
                            </template>


                            <li @click="roadAAboutNoAll(true)" class="zhcheckall">全选</li>
                            <li @click="roadAAboutNoAll(false)" class="zhclear">清空</li>
                        </ul>
                        <ul class="zhmslz">
                            <li class="title">筛选路珠：</li>
                            <template v-for="(item,index) in roadA.filters.aboutCheckRoadBoxList">
                                <li @click="roadAAboutFilterRoadFun(index)" :class="item?'lztype checked':'lztype'"><i>2</i>
                                    <a href="javascript:void(0)">{{$t('ssclz_'+index)}}</a>
                                </li>
                            </template>
                        </ul>
                    </div>
                    <!--单选模式-->
                    <div v-if="roadA.roadAModeSelect=='alone'" id="dxms" class="checkbtnzh checkbtndx">
                        <ul>
                            <li class="title">筛选路珠：</li>
                            <template v-for="(item,index) in roadA.filters.aloneCheckRoadBoxList">
                                <li @click="roadAAloneFilterRoadFun(index)" :class="item?'lztype checked':'lztype'"><i>2</i>
                                    <a href="javascript:void(0)">{{$t('ssclz_'+index)}}</a>
                                </li>
                            </template>


                            <li @click="roadAAloneNoAll(true)" class="zhcheckall_a">全选</li>
                            <li @click="roadAAloneNoAll(false)" class="zhclear_a">清空</li>
                        </ul>
                        <ul class="xuanzhemc">
                            <li class="mctitle">选择名次：</li>
                            <template v-for="(item,index) in 5">
                                <li @click="roadAAloneNoFilterFun('no'+item)" :class="roadA.filters.aloneSelectNo=='no'+item?'sinli selected':'sinli'"><span>1</span>
                                    <a href="javascript:void(0)">{{$t('ssclz_no'+item)}}</a>
                                </li>
                            </template>
                        </ul>
                    </div>
                    <!--两面模式筛选-->
                    <div v-if="roadA.roadAModeSelect=='lm'" id="lmms" class="checkbtnzh checkbtnmc">
                        <ul>
                            <li class="title">筛选名次：</li>
                            <template v-for="(item,index) in roadA.filters.lmCheckNumBoxList">
                                <li @click="roadALmNoFilterFun(index)" :class="item?'sinli checked':'sinli'"><i>1</i>
                                    <a >{{$t(index)}}</a>
                                </li>
                            </template>
                            <li @click="roadALmNoFilterAllFun(true)" class="zhcheckall">全选</li>
                            <li @click="roadALmNoFilterAllFun(false)" class="zhclear">清空</li>
                        </ul>
                        <ul class="xuanzhemc">
                            <li class="mctitle">选择路珠：</li>
                            <li @click="roadA.filters.lmSelectRoad='ou'" :class="roadA.filters.lmSelectRoad=='ou'?'lztype selected':'lztype'"><span>2</span>
                                <a>大小路珠</a>
                            </li>
                            <li @click="roadA.filters.lmSelectRoad='oe'" :class="roadA.filters.lmSelectRoad=='oe'?'lztype selected':'lztype'"><span>1</span>
                                <a>单双路珠</a>
                            </li>
                            <li @click="roadA.filters.lmSelectRoad='dt'" :class="roadA.filters.lmSelectRoad=='dt'?'lztype selected':'lztype'"><span>3</span>
                                <a>龙虎路珠</a>
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
                                        <table v-if="(roadA.roadAModeSelect=='about' && roadA.filters.aboutCheckNoBoxList[noIndex] && ((roadA.filters.aboutCheckRoadBoxList.ou && arrIndex.indexOf('ou')!=-1) || (roadA.filters.aboutCheckRoadBoxList.oe && arrIndex.indexOf('oe')!=-1) || (roadA.filters.aboutCheckRoadBoxList.dt && arrIndex.indexOf('dt')!=-1)))
                                                     ||(roadA.roadAModeSelect=='alone' && roadA.filters.aloneSelectNo==noIndex && ((roadA.filters.aloneCheckRoadBoxList.ou && noIndex+'ou' == arrIndex) || (roadA.filters.aloneCheckRoadBoxList.oe && noIndex+'oe' == arrIndex)  || (roadA.filters.aloneCheckRoadBoxList.dt && 'dtt' == arrIndex)))
                                                     ||(roadA.roadAModeSelect=='lm' && roadA.filters.lmCheckNumBoxList[noIndex] && ((noIndex+roadA.filters.lmSelectRoad == arrIndex || roadA.filters.lmSelectRoad + noIndex == arrIndex)))" class="lz_table_head  g_w1000">
                                            <tbody>
                                            <tr>
                                                <td>连续出现&nbsp;<input v-if="arrIndex!='dtt'" class="txtNum" v-model="roadA.filters['numTwo'+arrIndex]" type="number" min="1">
                                                    <input v-if="arrIndex=='dtt'" class="txtNum" v-model="roadA.filters['numTwosumdt']" type="number" min="1">&nbsp;次以上&nbsp;
                                                    <select v-if="arrIndex!='dtt'"  v-model="roadA.filters['selectTwo'+arrIndex]" class="secType">
                                                        <option :value="arrIndex.indexOf('ou')!=-1?'over':arrIndex.indexOf('oe')!=-1?'odd':arrIndex.indexOf('dt')!=-1?'dragon':''">{{arrIndex.indexOf('ou')!=-1?'大':arrIndex.indexOf('oe')!=-1?'单':arrIndex.indexOf('dt')!=-1?'龙':''}}</option>
                                                        <option :value="arrIndex.indexOf('ou')!=-1?'under':arrIndex.indexOf('oe')!=-1?'even':arrIndex.indexOf('dt')!=-1?'tiger':''">{{arrIndex.indexOf('ou')!=-1?'小':arrIndex.indexOf('oe')!=-1?'双':arrIndex.indexOf('dt')!=-1?'虎':''}}</option>
                                                        <option v-if="arrIndex.indexOf('dt')!=-1" :value="'draw'">和</option>
                                                    </select>

                                                    <select v-if="arrIndex=='dtt'"  v-model="roadA.filters['selectTwosumdt']" class="secType">
                                                        <option value="dragon">龙</option>
                                                        <option value="tiger">虎</option>
                                                        <option value="draw">和</option>
                                                    </select>
                                                    <input type="button" class="btnCheck" @click="roadATableFilterFun(arrIndex,noIndex)" value="确定">出现的次数为: <span class="sec_count" v-if="arrIndex!='dtt'">{{roadA.filters['showNum'+arrIndex]}}</span><span class="sec_count" v-if="arrIndex=='dtt'">{{roadA.filters['showNumsumdt']}} </span>
                                                </td>
                                                <td></td>
                                                <td class="righttd">
                                                    <span class="now_l"></span><span class="jinri">{{totalDateTime}}</span>&nbsp;累计：{{arrIndex.indexOf('ou')!=-1?'大':arrIndex.indexOf('oe')!=-1?'单':arrIndex.indexOf('dt')!=-1?'龙':''}}(<span class="count left_count" data="">{{arrIndex.indexOf('ou')!=-1?roadA[noIndex].over:arrIndex.indexOf('oe')!=-1?roadA[noIndex].odd:arrIndex.indexOf('dt')!=-1?roadA[noIndex].dragon:''}}</span>)&nbsp;&nbsp;{{arrIndex.indexOf('ou')!=-1?'小':arrIndex.indexOf('oe')!=-1?'双':arrIndex.indexOf('dt')!=-1?'虎':''}}(<span class="count right_count" data="单">{{arrIndex.indexOf('ou')!=-1?roadA[noIndex].under:arrIndex.indexOf('oe')!=-1?roadA[noIndex].even:arrIndex.indexOf('dt')!=-1?roadA[noIndex].tiger:''}}</span>)&nbsp;<template v-if="arrIndex=='dtt' && roadA[noIndex].draw">&nbsp;和(<span class="count right_count" data="单">{{roadA[noIndex].draw}}</span>)</template>
                                                    <span class="important"><span class="ifgj">{{$t('ssclz_'+noIndex)}}</span>&nbsp;<span class="ifds">{{arrIndex.indexOf('oe')!=-1?$t('oe'):arrIndex.indexOf('ou')!=-1?$t('ou'):arrIndex.indexOf('dt')!=-1?$t('dt'):''}}</span>&nbsp;&nbsp;&nbsp;最新</span>
                                                </td>
                                                <td class="tobottom"></td>
                                            </tr>
                                            </tbody>
                                        </table>
                                        <div v-if="roadA.roadAModeSelect=='about' && roadA.filters.aboutCheckNoBoxList[noIndex] && ((roadA.filters.aboutCheckRoadBoxList.ou && arrIndex.indexOf('ou')!=-1) || (roadA.filters.aboutCheckRoadBoxList.oe && arrIndex.indexOf('oe')!=-1) || (roadA.filters.aboutCheckRoadBoxList.dt && arrIndex.indexOf('dt')!=-1))
                                                  ||(roadA.roadAModeSelect=='alone' && roadA.filters.aloneSelectNo==noIndex && ((roadA.filters.aloneCheckRoadBoxList.ou && noIndex+'ou' == arrIndex) || (roadA.filters.aloneCheckRoadBoxList.oe && noIndex+'oe' == arrIndex)  || (roadA.filters.aloneCheckRoadBoxList.dt && 'dtt' == arrIndex)))
                                                    ||(roadA.roadAModeSelect=='lm' && roadA.filters.lmCheckNumBoxList[noIndex] && ((noIndex+(roadA.filters.lmSelectRoad+'t') == arrIndex || noIndex+roadA.filters.lmSelectRoad  == arrIndex)))" class="item_con" dir="rtl" style="background-position-x: 0px;">
                                            <table class="lz_table_con" border="0" cellpadding="1" cellspacing="1">
                                                <tbody>
                                                <tr class="tablebox">
                                                    <template v-for="(list,index) in noArr">
                                                        <template v-for="(item,i) in list">
                                                            <td :class="i%2==0?'bgcolor':''" :style="arrIndex !='dtt' && item[0] == roadA.filters['select'+arrIndex] && item.length>=roadA.filters['num'+arrIndex] && roadA.filters['btn'+arrIndex]?'color: rgb(255, 255, 255); background-color: rgb(253, 173, 86);':item[0] == roadA.filters['selectsumdt'] && item.length>=roadA.filters['numsumdt'] && roadA.filters['btnsumdt']?'color: rgb(255, 255, 255); background-color: rgb(253, 173, 86);':''">
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

        <!--综合分析-->
        <div v-show="pageTabSelect=='zhfx'" class="kaijiangjl margt20" style="display: block">
            <div class="head">
                <ul class="zoushimap" id="kaijiangjl">
                    <li class="kaijiltit kaijjlColor">综合分析</li>
                    <li id="jrsmtj" :class="topLi.jrsm?'selected':''" @click="onTopLi(1)"><a href="javascript:">今日双面/号码统计</a>
                        <i></i></li>
                    <li id="cltx" :class="topLi.cltx?'selected':''" @click="onTopLi(2)"><a href="javascript:"> 长龙提醒</a>
                        <i></i></li>
                    <li id="hmfb" :class="topLi.hmfb?'selected':''" @click="onTopLi(3)"><a href="javascript:">号码分布</a>
                        <i></i></li>
                </ul>
            </div>
            <div class="listcontent">
                <!--今日双面统计-->
                <div class="jrsmtj" :style="topLi.jrsm?'display: block':'display: none'">
                    <div class="headtxt"> 今日双面统计</div>
                    <table cellpadding="1" cellspacing="1" border="0">
                        <tbody>
                        <tr>
                            <th>号码</th>
                            <template v-for="indx in 10">
                                <th>{{indx}}</th>
                            </template>
                        </tr>

                        <tr v-if="jrsmList" id="shuanmiandata">
                            <td>出现次数</td>
                            <template v-for="list in jrsmList">
                                <td>{{list}}</td>
                            </template>
                        </tr>
                        </tbody>
                    </table>
                    <table class="secondtb" cellpadding="1" cellspacing="1" border="0">
                        <tbody>
                        <tr>
                            <th rowspan="2">球次</th>
                            <th colspan="4">总和</th>
                            <th colspan="4">第一球</th>
                            <th colspan="4">第二球</th>
                            <th colspan="4">第三球</th>
                            <th colspan="4">第四球</th>
                            <th colspan="4">第五球</th>
                        </tr>
                        <tr>
                            <template v-for="indx in 6">
                                <th>单</th>
                                <th>双</th>
                                <th>大</th>
                                <th>小</th>
                            </template>
                        </tr>
                        <tr id="gylhcs">
                            <td>出现次数</td>
                            <template v-for="gyl in gylhMap">
                                <td>{{gyl.dan}}</td>
                                <td>{{gyl.suan}}</td>
                                <td>{{gyl.da}}</td>
                                <td>{{gyl.xiao}}</td>
                            </template>
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
                                <li>
                                    {{$t('ssclz_'+item.type)}}：&nbsp;&nbsp;
                                    <span v-if="item.oddsKey!='draw'">{{$t(item.oddsKey.toUpperCase())}}</span><span
                                    v-else>和</span>&nbsp;&nbsp;<span :style="item.number>=5?'color:#f11821':''">{{item.number}}&nbsp;&nbsp;</span>期
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
                            <li class="kaijiltit">查看车号分布：</li>
                            <li class="01" @click="numScreenFun('0')"
                                :class="resultNumScreen.find((num)=>{return num=='0'})?'selected':''"><a
                                href="javascript:">号码0</a> <i></i></li>
                            <li class="02" @click="numScreenFun('1')"
                                :class="resultNumScreen.find((num)=>{return num=='1'})?'selected':''"><a
                                href="javascript:">号码1</a> <i></i></li>
                            <li class="03" @click="numScreenFun('2')"
                                :class="resultNumScreen.find((num)=>{return num=='2'})?'selected':''"><a
                                href="javascript:">号码2</a> <i></i></li>
                            <li class="04" @click="numScreenFun('3')"
                                :class="resultNumScreen.find((num)=>{return num=='3'})?'selected':''"><a
                                href="javascript:">号码3</a> <i></i></li>
                            <li class="05" @click="numScreenFun('4')"
                                :class="resultNumScreen.find((num)=>{return num=='4'})?'selected':''"><a
                                href="javascript:">号码4</a> <i></i></li>
                            <li class="06" @click="numScreenFun('5')"
                                :class="resultNumScreen.find((num)=>{return num=='5'})?'selected':''"><a
                                href="javascript:">号码5</a> <i></i></li>
                            <li class="07" @click="numScreenFun('6')"
                                :class="resultNumScreen.find((num)=>{return num=='6'})?'selected':''"><a
                                href="javascript:">号码6</a> <i></i></li>
                            <li class="08" @click="numScreenFun('7')"
                                :class="resultNumScreen.find((num)=>{return num=='7'})?'selected':''"><a
                                href="javascript:">号码7</a> <i></i></li>
                            <li class="09" @click="numScreenFun('8')"
                                :class="resultNumScreen.find((num)=>{return num=='8'})?'selected':''"><a
                                href="javascript:">号码8</a> <i></i></li>
                            <li class="10" @click="numScreenFun('9')"
                                :class="resultNumScreen.find((num)=>{return num=='9'})?'selected':''"><a
                                href="javascript:">号码9</a> <i></i></li>
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
                <div class="jrsmhmtj jrsmhmtj_kai ssc" id="jrsmhmtj">
                    <table id="jrsmhmtjTab" cellpadding="1" cellspacing="1" border="0"
                           style="background: rgb(212, 212, 212);">
                        <tbody>
                        <tr>
                            <th>期数</th>
                            <th id="numberbtn" class="numberbtn"><span @click="resultShowType='num';" id="xshm"
                                                                       :class="resultShowType=='num'?'spanselect':''">显示号码</span><span
                                id="xsdx" @click="resultShowType='numOu';"
                                :class="resultShowType=='numOu'?'spanselect':''">显示大小</span><span
                                @click="resultShowType='numOe';" id="xsds"
                                :class="resultShowType=='numOe'?'spanselect':''">显示单双</span></th>
                            <th colspan="3">总和</th>
                            <th>龙虎</th>
                            <th colspan="5">1-5球大小</th>
                            <th colspan="5">1-5球单双</th>
                            <th>前三</th>
                            <th>中三</th>
                            <th>后三</th>
                        </tr>
                        <tr v-for="(item,index) in hisList">
                            <td>{{item.gameNo}}</td>
                            <td class="blueqiu">
                                <ul style="width:240px">
                                    <li v-for="(num,i) in item.result" @click="numScreenFun(num)"
                                        :class="resultClassCss(num,index,i)" style="color:#012537"><i
                                        v-show="resultShowType=='num'">{{num}}</i></li>
                                </ul>
                            </td>
                            <td>{{item.gyh15lh.zh}}</td>
                            <td :style="item.gyh15lh.dx=='OVER'?'color:red':''">{{$t(item.gyh15lh.dx)}}</td>
                            <td :style="item.gyh15lh.ds=='EVEN'?'color:red':''">{{$t(item.gyh15lh.ds)}}</td>
                            <td :style="item.gyh15lh.lh1=='DRAGON'?'color:red':item.gyh15lh.lh1=='DRAW'?'color:green':''">
                                {{$t(item.gyh15lh.lh1)}}
                            </td>

                            <td :style="item.gyh15lh.no1ou=='OVER'?'color:red':''">{{$t(item.gyh15lh.no1ou)}}</td>
                            <td :style="item.gyh15lh.no2ou=='OVER'?'color:red':''">{{$t(item.gyh15lh.no2ou)}}</td>
                            <td :style="item.gyh15lh.no3ou=='OVER'?'color:red':''">{{$t(item.gyh15lh.no3ou)}}</td>
                            <td :style="item.gyh15lh.no4ou=='OVER'?'color:red':''">{{$t(item.gyh15lh.no4ou)}}</td>
                            <td :style="item.gyh15lh.no5ou=='OVER'?'color:red':''">{{$t(item.gyh15lh.no5ou)}}</td>
                            <td :style="item.gyh15lh.no1oe=='EVEN'?'color:red':''">{{$t(item.gyh15lh.no1oe)}}</td>
                            <td :style="item.gyh15lh.no2oe=='EVEN'?'color:red':''">{{$t(item.gyh15lh.no2oe)}}</td>
                            <td :style="item.gyh15lh.no3oe=='EVEN'?'color:red':''">{{$t(item.gyh15lh.no3oe)}}</td>
                            <td :style="item.gyh15lh.no4oe=='EVEN'?'color:red':''">{{$t(item.gyh15lh.no4oe)}}</td>
                            <td :style="item.gyh15lh.no5oe=='EVEN'?'color:red':''">{{$t(item.gyh15lh.no5oe)}}</td>

                            <td :style="item.gyh15lh.c3=='PAIR'?'color:red':''">{{$t(item.gyh15lh.c3)}}</td>
                            <td :style="item.gyh15lh.z3=='PAIR'?'color:red':''">{{$t(item.gyh15lh.z3)}}</td>
                            <td :style="item.gyh15lh.h3=='PAIR'?'color:red':''">{{$t(item.gyh15lh.h3)}}</td>
                        </tr>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>

        <!--基本走势-->
        <div v-show="pageTabSelect=='jbzs'" class="zhlzbox margt20" style="display:block">
            <div class="listhead">
                <div class="listheadl">
                    <span class="lmms"><i>3</i>基本走势</span>
                </div>
                <div class="listheadr" style="width: 150px">
                    <div class="listheadrl">
                        <span id="today">今天</span>
                        <span id="yesterday">昨天</span>
                        <span id="qianday">前天</span>
                    </div>
                    <!--<div class="listheadrr">
                        <div class="rightime">
                            <div id="dateframe">
                                <input type="text" id="date" class="date" placeholder="">
                                <div id="datebox"></div>
                                <i class="dropicond"></i>
                            </div>
                        </div>
                        <div>选择日期&nbsp;</div>
                    </div>-->
                </div>
            </div>
            <div class="listbox">
                <div class="checkbox">
                    <div id="biaozxz" class="checkbtnzh checkbtnmc">
                        <ul>
                            <li class="title">标注选择：</li>
                            <li class="sinli checked"><i>1</i>
                                <a href="javascript:void(0)">遗漏</a>
                            </li>
                            <li class="sinli checked"><i>2</i>
                                <a href="javascript:void(0)">拆线</a>
                            </li>
                            <li class="sinli"><i>3</i>
                                <a href="javascript:void(0)">遗漏分成</a>
                            </li>
                            <li class="sinli"><i>4</i>
                                <a href="javascript:void(0)">分隔线</a>
                            </li>
                        </ul>
                    </div>
                </div>
                <div class="listcontent">
                    <div class="box">
                        <div id="waitBox" class="bastren w1200">
                            <div id="chartLinediv" style="position:relative;*position:static;">
                                <div id="hovediv"
                                     style="position: absolute;top: 0;left: 0;width: 1716px;z-index: 1;height: 1318px;"></div>
                                <table id="trend_table2" width="1716" border="0" cellpadding="0" cellspacing="0">
                                    <thead>
                                    <tr class="first_th">
                                        <th width="200" rowspan="2" class="left_border left_b left_strong_down">
                                            <strong id="order" style="cursor:pointer;" type="desc">期号</strong>
                                        </th>
                                        <th width="152" class="" rowspan="2">开奖号码</th>
                                        <th colspan="10" height="38">第一球</th>
                                        <th colspan="10">第二球</th>
                                        <th colspan="10">第三球</th>
                                        <th colspan="10">第四球</th>
                                        <th colspan="10">第五球</th>
                                    </tr>
                                    <tr class="second_th">
                                        <th width="30" height="33" class="nfw clthbr">0</th>
                                        <th width="30" class="nfw">1</th>
                                        <th width="30" class="nfw">2</th>
                                        <th width="30" class="nfw">3</th>
                                        <th width="30" class="nfw">4</th>
                                        <th width="30" class="nfw">5</th>
                                        <th width="30" class="nfw">6</th>
                                        <th width="30" class="nfw">7</th>
                                        <th width="30" class="nfw">8</th>
                                        <th width="30" class="nfw">9</th>
                                        <th width="30" class="nfw">0</th>
                                        <th width="30" class="nfw">1</th>
                                        <th width="30" class="nfw">2</th>
                                        <th width="30" class="nfw">3</th>
                                        <th width="30" class="nfw">4</th>
                                        <th width="30" class="nfw">5</th>
                                        <th width="30" class="nfw">6</th>
                                        <th width="30" class="nfw">7</th>
                                        <th width="30" class="nfw">8</th>
                                        <th width="30" class="nfw">9</th>
                                        <th width="30" class="nfw">0</th>
                                        <th width="30" class="nfw">1</th>
                                        <th width="30" class="nfw">2</th>
                                        <th width="30" class="nfw">3</th>
                                        <th width="30" class="nfw">4</th>
                                        <th width="30" class="nfw">5</th>
                                        <th width="30" class="nfw">6</th>
                                        <th width="30" class="nfw">7</th>
                                        <th width="30" class="nfw">8</th>
                                        <th width="30" class="nfw">9</th>
                                        <th width="30" class="nfw">0</th>
                                        <th width="30" class="nfw">1</th>
                                        <th width="30" class="nfw">2</th>
                                        <th width="30" class="nfw">3</th>
                                        <th width="30" class="nfw">4</th>
                                        <th width="30" class="nfw">5</th>
                                        <th width="30" class="nfw">6</th>
                                        <th width="30" class="nfw">7</th>
                                        <th width="30" class="nfw">8</th>
                                        <th width="30" class="nfw">9</th>
                                        <th width="30" class="nfw">0</th>
                                        <th width="30" class="nfw">1</th>
                                        <th width="30" class="nfw">2</th>
                                        <th width="30" class="nfw">3</th>
                                        <th width="30" class="nfw">4</th>
                                        <th width="30" class="nfw">5</th>
                                        <th width="30" class="nfw">6</th>
                                        <th width="30" class="nfw">7</th>
                                        <th width="30" class="nfw">8</th>
                                        <th width="30" class="nfw">9</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <tr class="yiloutr" height="32">
                                        <td class="leftth">2020110434</td>
                                        <td><span class="kaijnum">0</span><span class="kaijnum">8</span><span
                                            class="kaijnum">8</span><span class="kaijnum">6</span><span class="kaijnum">3</span>
                                        </td>
                                        <td title="0" class="hot rank1"><span name="hotSpan" class="zoushiqiu bBK"
                                                                              style="background: rgb(251, 167, 94);">0</span>
                                        </td>
                                        <td class="rank1 yilou"><span>9</span></td>
                                        <td class="rank1 yilou"><span>8</span></td>
                                        <td class="rank1 yilou"><span>17</span></td>
                                        <td class="rank1 yilou"><span>20</span></td>
                                        <td class="rank1 yilou"><span>1</span></td>
                                        <td class="rank1 yilou"><span>18</span></td>
                                        <td class="rank1 yilou"><span>21</span></td>
                                        <td class="rank1 yilou"><span>3</span></td>
                                        <td class="rank1 yilou"><span>4</span></td>
                                        <td class="rank1 yilou rank2"><span>70</span></td>
                                        <td class="rank1 yilou rank2"><span>16</span></td>
                                        <td class="rank1 yilou rank2"><span>18</span></td>
                                        <td class="rank1 yilou rank2"><span>1</span></td>
                                        <td class="rank1 yilou rank2"><span>6</span></td>
                                        <td class="rank1 yilou rank2"><span>4</span></td>
                                        <td class="rank1 yilou rank2"><span>7</span></td>
                                        <td class="rank1 yilou rank2"><span>3</span></td>
                                        <td title="0" class="hot rank2"><span name="hotSpan" class="zoushiqiu gB"
                                                                              style="background: rgb(31, 166, 232);">8</span>
                                        </td>
                                        <td class="rank1 yilou rank2"><span>11</span></td>
                                        <td class="rank1 yilou rank3"><span>8</span></td>
                                        <td class="rank1 yilou rank3"><span>1</span></td>
                                        <td class="rank1 yilou rank3"><span>17</span></td>
                                        <td class="rank1 yilou rank3"><span>15</span></td>
                                        <td class="rank1 yilou rank3"><span>5</span></td>
                                        <td class="rank1 yilou rank3"><span>11</span></td>
                                        <td class="rank1 yilou rank3"><span>7</span></td>
                                        <td class="rank1 yilou rank3"><span>2</span></td>
                                        <td title="0" class="hot rank3"><span name="hotSpan" class="zoushiqiu oB"
                                                                              style="background: rgb(8, 191, 2);">8</span>
                                        </td>
                                        <td class="rank1 yilou rank3"><span>6</span></td>
                                        <td class="rank1 yilou rank4"><span>11</span></td>
                                        <td class="rank1 yilou rank4"><span>7</span></td>
                                        <td class="rank1 yilou rank4"><span>2</span></td>
                                        <td class="rank1 yilou rank4"><span>22</span></td>
                                        <td class="rank1 yilou rank4"><span>4</span></td>
                                        <td class="rank1 yilou rank4"><span>5</span></td>
                                        <td title="0" class="hot rank4"><span name="hotSpan" class="zoushiqiu gqB"
                                                                              style="background: rgb(133, 133, 251);">6</span>
                                        </td>
                                        <td class="rank1 yilou rank4"><span>14</span></td>
                                        <td class="rank1 yilou rank4"><span>9</span></td>
                                        <td class="rank1 yilou rank4"><span>12</span></td>
                                        <td class="rank1 yilou rank5"><span>7</span></td>
                                        <td class="rank1 yilou rank5"><span>8</span></td>
                                        <td class="rank1 yilou rank5"><span>5</span></td>
                                        <td title="0" class="hot rank5"><span name="hotSpan" class="zoushiqiu grB"
                                                                              style="background: rgb(70, 189, 149);">3</span>
                                        </td>
                                        <td class="rank1 yilou rank5"><span>16</span></td>
                                        <td class="rank1 yilou rank5"><span>26</span></td>
                                        <td class="rank1 yilou rank5"><span>12</span></td>
                                        <td class="rank1 yilou rank5"><span>1</span></td>
                                        <td class="rank1 yilou rank5"><span>15</span></td>
                                        <td class="rank1 yilou rank5"><span>3</span></td>
                                    </tr>
                                    <tr class="yiloutr" height="32">
                                        <td class="leftth">2020110433</td>
                                        <td><span class="kaijnum">5</span><span class="kaijnum">3</span><span
                                            class="kaijnum">1</span><span class="kaijnum">6</span><span class="kaijnum">7</span>
                                        </td>
                                        <td class="rank1 yilou"><span>9</span></td>
                                        <td class="rank1 yilou"><span>8</span></td>
                                        <td class="rank1 yilou"><span>7</span></td>
                                        <td class="rank1 yilou"><span>16</span></td>
                                        <td class="rank1 yilou"><span>19</span></td>
                                        <td title="0" class="hot rank1"><span name="hotSpan" class="zoushiqiu bBK"
                                                                              style="background: rgb(251, 167, 94);">5</span>
                                        </td>
                                        <td class="rank1 yilou"><span>17</span></td>
                                        <td class="rank1 yilou"><span>20</span></td>
                                        <td class="rank1 yilou"><span>2</span></td>
                                        <td class="rank1 yilou"><span>3</span></td>
                                        <td class="rank1 yilou rank2"><span>69</span></td>
                                        <td class="rank1 yilou rank2"><span>15</span></td>
                                        <td class="rank1 yilou rank2"><span>17</span></td>
                                        <td title="0" class="hot rank2"><span name="hotSpan" class="zoushiqiu gB"
                                                                              style="background: rgb(31, 166, 232);">3</span>
                                        </td>
                                        <td class="rank1 yilou rank2"><span>5</span></td>
                                        <td class="rank1 yilou rank2"><span>3</span></td>
                                        <td class="rank1 yilou rank2"><span>6</span></td>
                                        <td class="rank1 yilou rank2"><span>2</span></td>
                                        <td class="rank1 yilou rank2"><span>1</span></td>
                                        <td class="rank1 yilou rank2"><span>10</span></td>
                                        <td class="rank1 yilou rank3"><span>7</span></td>
                                        <td title="0" class="hot rank3"><span name="hotSpan" class="zoushiqiu oB"
                                                                              style="background: rgb(8, 191, 2);">1</span>
                                        </td>
                                        <td class="rank1 yilou rank3"><span>16</span></td>
                                        <td class="rank1 yilou rank3"><span>14</span></td>
                                        <td class="rank1 yilou rank3"><span>4</span></td>
                                        <td class="rank1 yilou rank3"><span>10</span></td>
                                        <td class="rank1 yilou rank3"><span>6</span></td>
                                        <td class="rank1 yilou rank3"><span>1</span></td>
                                        <td class="rank1 yilou rank3"><span>17</span></td>
                                        <td class="rank1 yilou rank3"><span>5</span></td>
                                        <td class="rank1 yilou rank4"><span>10</span></td>
                                        <td class="rank1 yilou rank4"><span>6</span></td>
                                        <td class="rank1 yilou rank4"><span>1</span></td>
                                        <td class="rank1 yilou rank4"><span>21</span></td>
                                        <td class="rank1 yilou rank4"><span>3</span></td>
                                        <td class="rank1 yilou rank4"><span>4</span></td>
                                        <td title="0" class="hot rank4"><span name="hotSpan" class="zoushiqiu gqB"
                                                                              style="background: rgb(133, 133, 251);">6</span>
                                        </td>
                                        <td class="rank1 yilou rank4"><span>13</span></td>
                                        <td class="rank1 yilou rank4"><span>8</span></td>
                                        <td class="rank1 yilou rank4"><span>11</span></td>
                                        <td class="rank1 yilou rank5"><span>6</span></td>
                                        <td class="rank1 yilou rank5"><span>7</span></td>
                                        <td class="rank1 yilou rank5"><span>4</span></td>
                                        <td class="rank1 yilou rank5"><span>12</span></td>
                                        <td class="rank1 yilou rank5"><span>15</span></td>
                                        <td class="rank1 yilou rank5"><span>25</span></td>
                                        <td class="rank1 yilou rank5"><span>11</span></td>
                                        <td title="0" class="hot rank5"><span name="hotSpan" class="zoushiqiu grB"
                                                                              style="background: rgb(70, 189, 149);">7</span>
                                        </td>
                                        <td class="rank1 yilou rank5"><span>14</span></td>
                                        <td class="rank1 yilou rank5"><span>2</span></td>
                                    </tr>
                                    <tr class="yiloutr" height="32">
                                        <td class="leftth">2020110432</td>
                                        <td><span class="kaijnum">5</span><span class="kaijnum">8</span><span
                                            class="kaijnum">7</span><span class="kaijnum">2</span><span class="kaijnum">7</span>
                                        </td>
                                        <td class="rank1 yilou"><span>8</span></td>
                                        <td class="rank1 yilou"><span>7</span></td>
                                        <td class="rank1 yilou"><span>6</span></td>
                                        <td class="rank1 yilou"><span>15</span></td>
                                        <td class="rank1 yilou"><span>18</span></td>
                                        <td title="0" class="hot rank1"><span name="hotSpan" class="zoushiqiu bBK"
                                                                              style="background: rgb(251, 167, 94);">5</span>
                                        </td>
                                        <td class="rank1 yilou"><span>16</span></td>
                                        <td class="rank1 yilou"><span>19</span></td>
                                        <td class="rank1 yilou"><span>1</span></td>
                                        <td class="rank1 yilou"><span>2</span></td>
                                        <td class="rank1 yilou rank2"><span>68</span></td>
                                        <td class="rank1 yilou rank2"><span>14</span></td>
                                        <td class="rank1 yilou rank2"><span>16</span></td>
                                        <td class="rank1 yilou rank2"><span>6</span></td>
                                        <td class="rank1 yilou rank2"><span>4</span></td>
                                        <td class="rank1 yilou rank2"><span>2</span></td>
                                        <td class="rank1 yilou rank2"><span>5</span></td>
                                        <td class="rank1 yilou rank2"><span>1</span></td>
                                        <td title="0" class="hot rank2"><span name="hotSpan" class="zoushiqiu gB"
                                                                              style="background: rgb(31, 166, 232);">8</span>
                                        </td>
                                        <td class="rank1 yilou rank2"><span>9</span></td>
                                        <td class="rank1 yilou rank3"><span>6</span></td>
                                        <td class="rank1 yilou rank3"><span>1</span></td>
                                        <td class="rank1 yilou rank3"><span>15</span></td>
                                        <td class="rank1 yilou rank3"><span>13</span></td>
                                        <td class="rank1 yilou rank3"><span>3</span></td>
                                        <td class="rank1 yilou rank3"><span>9</span></td>
                                        <td class="rank1 yilou rank3"><span>5</span></td>
                                        <td title="0" class="hot rank3"><span name="hotSpan" class="zoushiqiu oB"
                                                                              style="background: rgb(8, 191, 2);">7</span>
                                        </td>
                                        <td class="rank1 yilou rank3"><span>16</span></td>
                                        <td class="rank1 yilou rank3"><span>4</span></td>
                                        <td class="rank1 yilou rank4"><span>9</span></td>
                                        <td class="rank1 yilou rank4"><span>5</span></td>
                                        <td title="0" class="hot rank4"><span name="hotSpan" class="zoushiqiu gqB"
                                                                              style="background: rgb(133, 133, 251);">2</span>
                                        </td>
                                        <td class="rank1 yilou rank4"><span>20</span></td>
                                        <td class="rank1 yilou rank4"><span>2</span></td>
                                        <td class="rank1 yilou rank4"><span>3</span></td>
                                        <td class="rank1 yilou rank4"><span>1</span></td>
                                        <td class="rank1 yilou rank4"><span>12</span></td>
                                        <td class="rank1 yilou rank4"><span>7</span></td>
                                        <td class="rank1 yilou rank4"><span>10</span></td>
                                        <td class="rank1 yilou rank5"><span>5</span></td>
                                        <td class="rank1 yilou rank5"><span>6</span></td>
                                        <td class="rank1 yilou rank5"><span>3</span></td>
                                        <td class="rank1 yilou rank5"><span>11</span></td>
                                        <td class="rank1 yilou rank5"><span>14</span></td>
                                        <td class="rank1 yilou rank5"><span>24</span></td>
                                        <td class="rank1 yilou rank5"><span>10</span></td>
                                        <td title="0" class="hot rank5"><span name="hotSpan" class="zoushiqiu grB"
                                                                              style="background: rgb(70, 189, 149);">7</span>
                                        </td>
                                        <td class="rank1 yilou rank5"><span>13</span></td>
                                        <td class="rank1 yilou rank5"><span>1</span></td>
                                    </tr>
                                    <tr class="yiloutr" height="32">
                                        <td class="leftth">2020110431</td>
                                        <td><span class="kaijnum">8</span><span class="kaijnum">7</span><span
                                            class="kaijnum">1</span><span class="kaijnum">6</span><span class="kaijnum">9</span>
                                        </td>
                                        <td class="rank1 yilou"><span>7</span></td>
                                        <td class="rank1 yilou"><span>6</span></td>
                                        <td class="rank1 yilou"><span>5</span></td>
                                        <td class="rank1 yilou"><span>14</span></td>
                                        <td class="rank1 yilou"><span>17</span></td>
                                        <td class="rank1 yilou"><span>3</span></td>
                                        <td class="rank1 yilou"><span>15</span></td>
                                        <td class="rank1 yilou"><span>18</span></td>
                                        <td title="0" class="hot rank1"><span name="hotSpan" class="zoushiqiu bBK"
                                                                              style="background: rgb(251, 167, 94);">8</span>
                                        </td>
                                        <td class="rank1 yilou"><span>1</span></td>
                                        <td class="rank1 yilou rank2"><span>67</span></td>
                                        <td class="rank1 yilou rank2"><span>13</span></td>
                                        <td class="rank1 yilou rank2"><span>15</span></td>
                                        <td class="rank1 yilou rank2"><span>5</span></td>
                                        <td class="rank1 yilou rank2"><span>3</span></td>
                                        <td class="rank1 yilou rank2"><span>1</span></td>
                                        <td class="rank1 yilou rank2"><span>4</span></td>
                                        <td title="0" class="hot rank2"><span name="hotSpan" class="zoushiqiu gB"
                                                                              style="background: rgb(31, 166, 232);">7</span>
                                        </td>
                                        <td class="rank1 yilou rank2"><span>2</span></td>
                                        <td class="rank1 yilou rank2"><span>8</span></td>
                                        <td class="rank1 yilou rank3"><span>5</span></td>
                                        <td title="0" class="hot rank3"><span name="hotSpan" class="zoushiqiu oB"
                                                                              style="background: rgb(8, 191, 2);">1</span>
                                        </td>
                                        <td class="rank1 yilou rank3"><span>14</span></td>
                                        <td class="rank1 yilou rank3"><span>12</span></td>
                                        <td class="rank1 yilou rank3"><span>2</span></td>
                                        <td class="rank1 yilou rank3"><span>8</span></td>
                                        <td class="rank1 yilou rank3"><span>4</span></td>
                                        <td class="rank1 yilou rank3"><span>1</span></td>
                                        <td class="rank1 yilou rank3"><span>15</span></td>
                                        <td class="rank1 yilou rank3"><span>3</span></td>
                                        <td class="rank1 yilou rank4"><span>8</span></td>
                                        <td class="rank1 yilou rank4"><span>4</span></td>
                                        <td class="rank1 yilou rank4"><span>15</span></td>
                                        <td class="rank1 yilou rank4"><span>19</span></td>
                                        <td class="rank1 yilou rank4"><span>1</span></td>
                                        <td class="rank1 yilou rank4"><span>2</span></td>
                                        <td title="0" class="hot rank4"><span name="hotSpan" class="zoushiqiu gqB"
                                                                              style="background: rgb(133, 133, 251);">6</span>
                                        </td>
                                        <td class="rank1 yilou rank4"><span>11</span></td>
                                        <td class="rank1 yilou rank4"><span>6</span></td>
                                        <td class="rank1 yilou rank4"><span>9</span></td>
                                        <td class="rank1 yilou rank5"><span>4</span></td>
                                        <td class="rank1 yilou rank5"><span>5</span></td>
                                        <td class="rank1 yilou rank5"><span>2</span></td>
                                        <td class="rank1 yilou rank5"><span>10</span></td>
                                        <td class="rank1 yilou rank5"><span>13</span></td>
                                        <td class="rank1 yilou rank5"><span>23</span></td>
                                        <td class="rank1 yilou rank5"><span>9</span></td>
                                        <td class="rank1 yilou rank5"><span>17</span></td>
                                        <td class="rank1 yilou rank5"><span>12</span></td>
                                        <td title="0" class="hot rank5"><span name="hotSpan" class="zoushiqiu grB"
                                                                              style="background: rgb(70, 189, 149);">9</span>
                                        </td>
                                    </tr>
                                    <tr class="yiloutr" height="32">
                                        <td class="leftth">2020110430</td>
                                        <td><span class="kaijnum">9</span><span class="kaijnum">5</span><span
                                            class="kaijnum">7</span><span class="kaijnum">4</span><span class="kaijnum">9</span>
                                        </td>
                                        <td class="rank1 yilou"><span>6</span></td>
                                        <td class="rank1 yilou"><span>5</span></td>
                                        <td class="rank1 yilou"><span>4</span></td>
                                        <td class="rank1 yilou"><span>13</span></td>
                                        <td class="rank1 yilou"><span>16</span></td>
                                        <td class="rank1 yilou"><span>2</span></td>
                                        <td class="rank1 yilou"><span>14</span></td>
                                        <td class="rank1 yilou"><span>17</span></td>
                                        <td class="rank1 yilou"><span>3</span></td>
                                        <td title="0" class="hot rank1"><span name="hotSpan" class="zoushiqiu bBK"
                                                                              style="background: rgb(251, 167, 94);">9</span>
                                        </td>
                                        <td class="rank1 yilou rank2"><span>66</span></td>
                                        <td class="rank1 yilou rank2"><span>12</span></td>
                                        <td class="rank1 yilou rank2"><span>14</span></td>
                                        <td class="rank1 yilou rank2"><span>4</span></td>
                                        <td class="rank1 yilou rank2"><span>2</span></td>
                                        <td title="0" class="hot rank2"><span name="hotSpan" class="zoushiqiu gB"
                                                                              style="background: rgb(31, 166, 232);">5</span>
                                        </td>
                                        <td class="rank1 yilou rank2"><span>3</span></td>
                                        <td class="rank1 yilou rank2"><span>5</span></td>
                                        <td class="rank1 yilou rank2"><span>1</span></td>
                                        <td class="rank1 yilou rank2"><span>7</span></td>
                                        <td class="rank1 yilou rank3"><span>4</span></td>
                                        <td class="rank1 yilou rank3"><span>5</span></td>
                                        <td class="rank1 yilou rank3"><span>13</span></td>
                                        <td class="rank1 yilou rank3"><span>11</span></td>
                                        <td class="rank1 yilou rank3"><span>1</span></td>
                                        <td class="rank1 yilou rank3"><span>7</span></td>
                                        <td class="rank1 yilou rank3"><span>3</span></td>
                                        <td title="0" class="hot rank3"><span name="hotSpan" class="zoushiqiu oB"
                                                                              style="background: rgb(8, 191, 2);">7</span>
                                        </td>
                                        <td class="rank1 yilou rank3"><span>14</span></td>
                                        <td class="rank1 yilou rank3"><span>2</span></td>
                                        <td class="rank1 yilou rank4"><span>7</span></td>
                                        <td class="rank1 yilou rank4"><span>3</span></td>
                                        <td class="rank1 yilou rank4"><span>14</span></td>
                                        <td class="rank1 yilou rank4"><span>18</span></td>
                                        <td title="0" class="hot rank4"><span name="hotSpan" class="zoushiqiu gqB"
                                                                              style="background: rgb(133, 133, 251);">4</span>
                                        </td>
                                        <td class="rank1 yilou rank4"><span>1</span></td>
                                        <td class="rank1 yilou rank4"><span>2</span></td>
                                        <td class="rank1 yilou rank4"><span>10</span></td>
                                        <td class="rank1 yilou rank4"><span>5</span></td>
                                        <td class="rank1 yilou rank4"><span>8</span></td>
                                        <td class="rank1 yilou rank5"><span>3</span></td>
                                        <td class="rank1 yilou rank5"><span>4</span></td>
                                        <td class="rank1 yilou rank5"><span>1</span></td>
                                        <td class="rank1 yilou rank5"><span>9</span></td>
                                        <td class="rank1 yilou rank5"><span>12</span></td>
                                        <td class="rank1 yilou rank5"><span>22</span></td>
                                        <td class="rank1 yilou rank5"><span>8</span></td>
                                        <td class="rank1 yilou rank5"><span>16</span></td>
                                        <td class="rank1 yilou rank5"><span>11</span></td>
                                        <td title="0" class="hot rank5"><span name="hotSpan" class="zoushiqiu grB"
                                                                              style="background: rgb(70, 189, 149);">9</span>
                                        </td>
                                    </tr>
                                    <tr class="yiloutr" height="32">
                                        <td class="leftth">2020110429</td>
                                        <td><span class="kaijnum">9</span><span class="kaijnum">8</span><span
                                            class="kaijnum">4</span><span class="kaijnum">5</span><span class="kaijnum">2</span>
                                        </td>
                                        <td class="rank1 yilou"><span>5</span></td>
                                        <td class="rank1 yilou"><span>4</span></td>
                                        <td class="rank1 yilou"><span>3</span></td>
                                        <td class="rank1 yilou"><span>12</span></td>
                                        <td class="rank1 yilou"><span>15</span></td>
                                        <td class="rank1 yilou"><span>1</span></td>
                                        <td class="rank1 yilou"><span>13</span></td>
                                        <td class="rank1 yilou"><span>16</span></td>
                                        <td class="rank1 yilou"><span>2</span></td>
                                        <td title="0" class="hot rank1"><span name="hotSpan" class="zoushiqiu bBK"
                                                                              style="background: rgb(251, 167, 94);">9</span>
                                        </td>
                                        <td class="rank1 yilou rank2"><span>65</span></td>
                                        <td class="rank1 yilou rank2"><span>11</span></td>
                                        <td class="rank1 yilou rank2"><span>13</span></td>
                                        <td class="rank1 yilou rank2"><span>3</span></td>
                                        <td class="rank1 yilou rank2"><span>1</span></td>
                                        <td class="rank1 yilou rank2"><span>9</span></td>
                                        <td class="rank1 yilou rank2"><span>2</span></td>
                                        <td class="rank1 yilou rank2"><span>4</span></td>
                                        <td title="0" class="hot rank2"><span name="hotSpan" class="zoushiqiu gB"
                                                                              style="background: rgb(31, 166, 232);">8</span>
                                        </td>
                                        <td class="rank1 yilou rank2"><span>6</span></td>
                                        <td class="rank1 yilou rank3"><span>3</span></td>
                                        <td class="rank1 yilou rank3"><span>4</span></td>
                                        <td class="rank1 yilou rank3"><span>12</span></td>
                                        <td class="rank1 yilou rank3"><span>10</span></td>
                                        <td title="0" class="hot rank3"><span name="hotSpan" class="zoushiqiu oB"
                                                                              style="background: rgb(8, 191, 2);">4</span>
                                        </td>
                                        <td class="rank1 yilou rank3"><span>6</span></td>
                                        <td class="rank1 yilou rank3"><span>2</span></td>
                                        <td class="rank1 yilou rank3"><span>15</span></td>
                                        <td class="rank1 yilou rank3"><span>13</span></td>
                                        <td class="rank1 yilou rank3"><span>1</span></td>
                                        <td class="rank1 yilou rank4"><span>6</span></td>
                                        <td class="rank1 yilou rank4"><span>2</span></td>
                                        <td class="rank1 yilou rank4"><span>13</span></td>
                                        <td class="rank1 yilou rank4"><span>17</span></td>
                                        <td class="rank1 yilou rank4"><span>3</span></td>
                                        <td title="0" class="hot rank4"><span name="hotSpan" class="zoushiqiu gqB"
                                                                              style="background: rgb(133, 133, 251);">5</span>
                                        </td>
                                        <td class="rank1 yilou rank4"><span>1</span></td>
                                        <td class="rank1 yilou rank4"><span>9</span></td>
                                        <td class="rank1 yilou rank4"><span>4</span></td>
                                        <td class="rank1 yilou rank4"><span>7</span></td>
                                        <td class="rank1 yilou rank5"><span>2</span></td>
                                        <td class="rank1 yilou rank5"><span>3</span></td>
                                        <td title="0" class="hot rank5"><span name="hotSpan" class="zoushiqiu grB"
                                                                              style="background: rgb(70, 189, 149);">2</span>
                                        </td>
                                        <td class="rank1 yilou rank5"><span>8</span></td>
                                        <td class="rank1 yilou rank5"><span>11</span></td>
                                        <td class="rank1 yilou rank5"><span>21</span></td>
                                        <td class="rank1 yilou rank5"><span>7</span></td>
                                        <td class="rank1 yilou rank5"><span>15</span></td>
                                        <td class="rank1 yilou rank5"><span>10</span></td>
                                        <td class="rank1 yilou rank5"><span>1</span></td>
                                    </tr>
                                    <tr class="yiloutr" height="32">
                                        <td class="leftth">2020110428</td>
                                        <td><span class="kaijnum">5</span><span class="kaijnum">4</span><span
                                            class="kaijnum">9</span><span class="kaijnum">6</span><span class="kaijnum">9</span>
                                        </td>
                                        <td class="rank1 yilou"><span>4</span></td>
                                        <td class="rank1 yilou"><span>3</span></td>
                                        <td class="rank1 yilou"><span>2</span></td>
                                        <td class="rank1 yilou"><span>11</span></td>
                                        <td class="rank1 yilou"><span>14</span></td>
                                        <td title="0" class="hot rank1"><span name="hotSpan" class="zoushiqiu bBK"
                                                                              style="background: rgb(251, 167, 94);">5</span>
                                        </td>
                                        <td class="rank1 yilou"><span>12</span></td>
                                        <td class="rank1 yilou"><span>15</span></td>
                                        <td class="rank1 yilou"><span>1</span></td>
                                        <td class="rank1 yilou"><span>7</span></td>
                                        <td class="rank1 yilou rank2"><span>64</span></td>
                                        <td class="rank1 yilou rank2"><span>10</span></td>
                                        <td class="rank1 yilou rank2"><span>12</span></td>
                                        <td class="rank1 yilou rank2"><span>2</span></td>
                                        <td title="0" class="hot rank2"><span name="hotSpan" class="zoushiqiu gB"
                                                                              style="background: rgb(31, 166, 232);">4</span>
                                        </td>
                                        <td class="rank1 yilou rank2"><span>8</span></td>
                                        <td class="rank1 yilou rank2"><span>1</span></td>
                                        <td class="rank1 yilou rank2"><span>3</span></td>
                                        <td class="rank1 yilou rank2"><span>4</span></td>
                                        <td class="rank1 yilou rank2"><span>5</span></td>
                                        <td class="rank1 yilou rank3"><span>2</span></td>
                                        <td class="rank1 yilou rank3"><span>3</span></td>
                                        <td class="rank1 yilou rank3"><span>11</span></td>
                                        <td class="rank1 yilou rank3"><span>9</span></td>
                                        <td class="rank1 yilou rank3"><span>10</span></td>
                                        <td class="rank1 yilou rank3"><span>5</span></td>
                                        <td class="rank1 yilou rank3"><span>1</span></td>
                                        <td class="rank1 yilou rank3"><span>14</span></td>
                                        <td class="rank1 yilou rank3"><span>12</span></td>
                                        <td title="0" class="hot rank3"><span name="hotSpan" class="zoushiqiu oB"
                                                                              style="background: rgb(8, 191, 2);">9</span>
                                        </td>
                                        <td class="rank1 yilou rank4"><span>5</span></td>
                                        <td class="rank1 yilou rank4"><span>1</span></td>
                                        <td class="rank1 yilou rank4"><span>12</span></td>
                                        <td class="rank1 yilou rank4"><span>16</span></td>
                                        <td class="rank1 yilou rank4"><span>2</span></td>
                                        <td class="rank1 yilou rank4"><span>4</span></td>
                                        <td title="0" class="hot rank4"><span name="hotSpan" class="zoushiqiu gqB"
                                                                              style="background: rgb(133, 133, 251);">6</span>
                                        </td>
                                        <td class="rank1 yilou rank4"><span>8</span></td>
                                        <td class="rank1 yilou rank4"><span>3</span></td>
                                        <td class="rank1 yilou rank4"><span>6</span></td>
                                        <td class="rank1 yilou rank5"><span>1</span></td>
                                        <td class="rank1 yilou rank5"><span>2</span></td>
                                        <td class="rank1 yilou rank5"><span>52</span></td>
                                        <td class="rank1 yilou rank5"><span>7</span></td>
                                        <td class="rank1 yilou rank5"><span>10</span></td>
                                        <td class="rank1 yilou rank5"><span>20</span></td>
                                        <td class="rank1 yilou rank5"><span>6</span></td>
                                        <td class="rank1 yilou rank5"><span>14</span></td>
                                        <td class="rank1 yilou rank5"><span>9</span></td>
                                        <td title="0" class="hot rank5"><span name="hotSpan" class="zoushiqiu grB"
                                                                              style="background: rgb(70, 189, 149);">9</span>
                                        </td>
                                    </tr>
                                    <tr class="yiloutr" height="32">
                                        <td class="leftth">2020110427</td>
                                        <td><span class="kaijnum">8</span><span class="kaijnum">6</span><span
                                            class="kaijnum">6</span><span class="kaijnum">1</span><span class="kaijnum">0</span>
                                        </td>
                                        <td class="rank1 yilou"><span>3</span></td>
                                        <td class="rank1 yilou"><span>2</span></td>
                                        <td class="rank1 yilou"><span>1</span></td>
                                        <td class="rank1 yilou"><span>10</span></td>
                                        <td class="rank1 yilou"><span>13</span></td>
                                        <td class="rank1 yilou"><span>21</span></td>
                                        <td class="rank1 yilou"><span>11</span></td>
                                        <td class="rank1 yilou"><span>14</span></td>
                                        <td title="0" class="hot rank1"><span name="hotSpan" class="zoushiqiu bBK"
                                                                              style="background: rgb(251, 167, 94);">8</span>
                                        </td>
                                        <td class="rank1 yilou"><span>6</span></td>
                                        <td class="rank1 yilou rank2"><span>63</span></td>
                                        <td class="rank1 yilou rank2"><span>9</span></td>
                                        <td class="rank1 yilou rank2"><span>11</span></td>
                                        <td class="rank1 yilou rank2"><span>1</span></td>
                                        <td class="rank1 yilou rank2"><span>6</span></td>
                                        <td class="rank1 yilou rank2"><span>7</span></td>
                                        <td title="0" class="hot rank2"><span name="hotSpan" class="zoushiqiu gB"
                                                                              style="background: rgb(31, 166, 232);">6</span>
                                        </td>
                                        <td class="rank1 yilou rank2"><span>2</span></td>
                                        <td class="rank1 yilou rank2"><span>3</span></td>
                                        <td class="rank1 yilou rank2"><span>4</span></td>
                                        <td class="rank1 yilou rank3"><span>1</span></td>
                                        <td class="rank1 yilou rank3"><span>2</span></td>
                                        <td class="rank1 yilou rank3"><span>10</span></td>
                                        <td class="rank1 yilou rank3"><span>8</span></td>
                                        <td class="rank1 yilou rank3"><span>9</span></td>
                                        <td class="rank1 yilou rank3"><span>4</span></td>
                                        <td title="0" class="hot rank3"><span name="hotSpan" class="zoushiqiu oB"
                                                                              style="background: rgb(8, 191, 2);">6</span>
                                        </td>
                                        <td class="rank1 yilou rank3"><span>13</span></td>
                                        <td class="rank1 yilou rank3"><span>11</span></td>
                                        <td class="rank1 yilou rank3"><span>5</span></td>
                                        <td class="rank1 yilou rank4"><span>4</span></td>
                                        <td title="0" class="hot rank4"><span name="hotSpan" class="zoushiqiu gqB"
                                                                              style="background: rgb(133, 133, 251);">1</span>
                                        </td>
                                        <td class="rank1 yilou rank4"><span>11</span></td>
                                        <td class="rank1 yilou rank4"><span>15</span></td>
                                        <td class="rank1 yilou rank4"><span>1</span></td>
                                        <td class="rank1 yilou rank4"><span>3</span></td>
                                        <td class="rank1 yilou rank4"><span>16</span></td>
                                        <td class="rank1 yilou rank4"><span>7</span></td>
                                        <td class="rank1 yilou rank4"><span>2</span></td>
                                        <td class="rank1 yilou rank4"><span>5</span></td>
                                        <td title="0" class="hot rank5"><span name="hotSpan" class="zoushiqiu grB"
                                                                              style="background: rgb(70, 189, 149);">0</span>
                                        </td>
                                        <td class="rank1 yilou rank5"><span>1</span></td>
                                        <td class="rank1 yilou rank5"><span>51</span></td>
                                        <td class="rank1 yilou rank5"><span>6</span></td>
                                        <td class="rank1 yilou rank5"><span>9</span></td>
                                        <td class="rank1 yilou rank5"><span>19</span></td>
                                        <td class="rank1 yilou rank5"><span>5</span></td>
                                        <td class="rank1 yilou rank5"><span>13</span></td>
                                        <td class="rank1 yilou rank5"><span>8</span></td>
                                        <td class="rank1 yilou rank5"><span>4</span></td>
                                    </tr>
                                    <tr class="yiloutr" height="32">
                                        <td class="leftth">2020110426</td>
                                        <td><span class="kaijnum">2</span><span class="kaijnum">3</span><span
                                            class="kaijnum">0</span><span class="kaijnum">4</span><span class="kaijnum">1</span>
                                        </td>
                                        <td class="rank1 yilou"><span>2</span></td>
                                        <td class="rank1 yilou"><span>1</span></td>
                                        <td title="0" class="hot rank1"><span name="hotSpan" class="zoushiqiu bBK"
                                                                              style="background: rgb(251, 167, 94);">2</span>
                                        </td>
                                        <td class="rank1 yilou"><span>9</span></td>
                                        <td class="rank1 yilou"><span>12</span></td>
                                        <td class="rank1 yilou"><span>20</span></td>
                                        <td class="rank1 yilou"><span>10</span></td>
                                        <td class="rank1 yilou"><span>13</span></td>
                                        <td class="rank1 yilou"><span>11</span></td>
                                        <td class="rank1 yilou"><span>5</span></td>
                                        <td class="rank1 yilou rank2"><span>62</span></td>
                                        <td class="rank1 yilou rank2"><span>8</span></td>
                                        <td class="rank1 yilou rank2"><span>10</span></td>
                                        <td title="0" class="hot rank2"><span name="hotSpan" class="zoushiqiu gB"
                                                                              style="background: rgb(31, 166, 232);">3</span>
                                        </td>
                                        <td class="rank1 yilou rank2"><span>5</span></td>
                                        <td class="rank1 yilou rank2"><span>6</span></td>
                                        <td class="rank1 yilou rank2"><span>11</span></td>
                                        <td class="rank1 yilou rank2"><span>1</span></td>
                                        <td class="rank1 yilou rank2"><span>2</span></td>
                                        <td class="rank1 yilou rank2"><span>3</span></td>
                                        <td title="0" class="hot rank3"><span name="hotSpan" class="zoushiqiu oB"
                                                                              style="background: rgb(8, 191, 2);">0</span>
                                        </td>
                                        <td class="rank1 yilou rank3"><span>1</span></td>
                                        <td class="rank1 yilou rank3"><span>9</span></td>
                                        <td class="rank1 yilou rank3"><span>7</span></td>
                                        <td class="rank1 yilou rank3"><span>8</span></td>
                                        <td class="rank1 yilou rank3"><span>3</span></td>
                                        <td class="rank1 yilou rank3"><span>15</span></td>
                                        <td class="rank1 yilou rank3"><span>12</span></td>
                                        <td class="rank1 yilou rank3"><span>10</span></td>
                                        <td class="rank1 yilou rank3"><span>4</span></td>
                                        <td class="rank1 yilou rank4"><span>3</span></td>
                                        <td class="rank1 yilou rank4"><span>25</span></td>
                                        <td class="rank1 yilou rank4"><span>10</span></td>
                                        <td class="rank1 yilou rank4"><span>14</span></td>
                                        <td title="0" class="hot rank4"><span name="hotSpan" class="zoushiqiu gqB"
                                                                              style="background: rgb(133, 133, 251);">4</span>
                                        </td>
                                        <td class="rank1 yilou rank4"><span>2</span></td>
                                        <td class="rank1 yilou rank4"><span>15</span></td>
                                        <td class="rank1 yilou rank4"><span>6</span></td>
                                        <td class="rank1 yilou rank4"><span>1</span></td>
                                        <td class="rank1 yilou rank4"><span>4</span></td>
                                        <td class="rank1 yilou rank5"><span>1</span></td>
                                        <td title="0" class="hot rank5"><span name="hotSpan" class="zoushiqiu grB"
                                                                              style="background: rgb(70, 189, 149);">1</span>
                                        </td>
                                        <td class="rank1 yilou rank5"><span>50</span></td>
                                        <td class="rank1 yilou rank5"><span>5</span></td>
                                        <td class="rank1 yilou rank5"><span>8</span></td>
                                        <td class="rank1 yilou rank5"><span>18</span></td>
                                        <td class="rank1 yilou rank5"><span>4</span></td>
                                        <td class="rank1 yilou rank5"><span>12</span></td>
                                        <td class="rank1 yilou rank5"><span>7</span></td>
                                        <td class="rank1 yilou rank5"><span>3</span></td>
                                    </tr>
                                    <tr class="yiloutr" height="32">
                                        <td class="leftth">2020110425</td>
                                        <td><span class="kaijnum">1</span><span class="kaijnum">7</span><span
                                            class="kaijnum">1</span><span class="kaijnum">8</span><span class="kaijnum">0</span>
                                        </td>
                                        <td class="rank1 yilou"><span>1</span></td>
                                        <td title="0" class="hot rank1"><span name="hotSpan" class="zoushiqiu bBK"
                                                                              style="background: rgb(251, 167, 94);">1</span>
                                        </td>
                                        <td class="rank1 yilou"><span>2</span></td>
                                        <td class="rank1 yilou"><span>8</span></td>
                                        <td class="rank1 yilou"><span>11</span></td>
                                        <td class="rank1 yilou"><span>19</span></td>
                                        <td class="rank1 yilou"><span>9</span></td>
                                        <td class="rank1 yilou"><span>12</span></td>
                                        <td class="rank1 yilou"><span>10</span></td>
                                        <td class="rank1 yilou"><span>4</span></td>
                                        <td class="rank1 yilou rank2"><span>61</span></td>
                                        <td class="rank1 yilou rank2"><span>7</span></td>
                                        <td class="rank1 yilou rank2"><span>9</span></td>
                                        <td class="rank1 yilou rank2"><span>3</span></td>
                                        <td class="rank1 yilou rank2"><span>4</span></td>
                                        <td class="rank1 yilou rank2"><span>5</span></td>
                                        <td class="rank1 yilou rank2"><span>10</span></td>
                                        <td title="0" class="hot rank2"><span name="hotSpan" class="zoushiqiu gB"
                                                                              style="background: rgb(31, 166, 232);">7</span>
                                        </td>
                                        <td class="rank1 yilou rank2"><span>1</span></td>
                                        <td class="rank1 yilou rank2"><span>2</span></td>
                                        <td class="rank1 yilou rank3"><span>4</span></td>
                                        <td title="0" class="hot rank3"><span name="hotSpan" class="zoushiqiu oB"
                                                                              style="background: rgb(8, 191, 2);">1</span>
                                        </td>
                                        <td class="rank1 yilou rank3"><span>8</span></td>
                                        <td class="rank1 yilou rank3"><span>6</span></td>
                                        <td class="rank1 yilou rank3"><span>7</span></td>
                                        <td class="rank1 yilou rank3"><span>2</span></td>
                                        <td class="rank1 yilou rank3"><span>14</span></td>
                                        <td class="rank1 yilou rank3"><span>11</span></td>
                                        <td class="rank1 yilou rank3"><span>9</span></td>
                                        <td class="rank1 yilou rank3"><span>3</span></td>
                                        <td class="rank1 yilou rank4"><span>2</span></td>
                                        <td class="rank1 yilou rank4"><span>24</span></td>
                                        <td class="rank1 yilou rank4"><span>9</span></td>
                                        <td class="rank1 yilou rank4"><span>13</span></td>
                                        <td class="rank1 yilou rank4"><span>10</span></td>
                                        <td class="rank1 yilou rank4"><span>1</span></td>
                                        <td class="rank1 yilou rank4"><span>14</span></td>
                                        <td class="rank1 yilou rank4"><span>5</span></td>
                                        <td title="0" class="hot rank4"><span name="hotSpan" class="zoushiqiu gqB"
                                                                              style="background: rgb(133, 133, 251);">8</span>
                                        </td>
                                        <td class="rank1 yilou rank4"><span>3</span></td>
                                        <td title="0" class="hot rank5"><span name="hotSpan" class="zoushiqiu grB"
                                                                              style="background: rgb(70, 189, 149);">0</span>
                                        </td>
                                        <td class="rank1 yilou rank5"><span>1</span></td>
                                        <td class="rank1 yilou rank5"><span>49</span></td>
                                        <td class="rank1 yilou rank5"><span>4</span></td>
                                        <td class="rank1 yilou rank5"><span>7</span></td>
                                        <td class="rank1 yilou rank5"><span>17</span></td>
                                        <td class="rank1 yilou rank5"><span>3</span></td>
                                        <td class="rank1 yilou rank5"><span>11</span></td>
                                        <td class="rank1 yilou rank5"><span>6</span></td>
                                        <td class="rank1 yilou rank5"><span>2</span></td>
                                    </tr>
                                    <tr class="yiloutr" height="32">
                                        <td class="leftth">2020110424</td>
                                        <td><span class="kaijnum">0</span><span class="kaijnum">8</span><span
                                            class="kaijnum">1</span><span class="kaijnum">5</span><span class="kaijnum">1</span>
                                        </td>
                                        <td title="0" class="hot rank1"><span name="hotSpan" class="zoushiqiu bBK"
                                                                              style="background: rgb(251, 167, 94);">0</span>
                                        </td>
                                        <td class="rank1 yilou"><span>2</span></td>
                                        <td class="rank1 yilou"><span>1</span></td>
                                        <td class="rank1 yilou"><span>7</span></td>
                                        <td class="rank1 yilou"><span>10</span></td>
                                        <td class="rank1 yilou"><span>18</span></td>
                                        <td class="rank1 yilou"><span>8</span></td>
                                        <td class="rank1 yilou"><span>11</span></td>
                                        <td class="rank1 yilou"><span>9</span></td>
                                        <td class="rank1 yilou"><span>3</span></td>
                                        <td class="rank1 yilou rank2"><span>60</span></td>
                                        <td class="rank1 yilou rank2"><span>6</span></td>
                                        <td class="rank1 yilou rank2"><span>8</span></td>
                                        <td class="rank1 yilou rank2"><span>2</span></td>
                                        <td class="rank1 yilou rank2"><span>3</span></td>
                                        <td class="rank1 yilou rank2"><span>4</span></td>
                                        <td class="rank1 yilou rank2"><span>9</span></td>
                                        <td class="rank1 yilou rank2"><span>27</span></td>
                                        <td title="0" class="hot rank2"><span name="hotSpan" class="zoushiqiu gB"
                                                                              style="background: rgb(31, 166, 232);">8</span>
                                        </td>
                                        <td class="rank1 yilou rank2"><span>1</span></td>
                                        <td class="rank1 yilou rank3"><span>3</span></td>
                                        <td title="0" class="hot rank3"><span name="hotSpan" class="zoushiqiu oB"
                                                                              style="background: rgb(8, 191, 2);">1</span>
                                        </td>
                                        <td class="rank1 yilou rank3"><span>7</span></td>
                                        <td class="rank1 yilou rank3"><span>5</span></td>
                                        <td class="rank1 yilou rank3"><span>6</span></td>
                                        <td class="rank1 yilou rank3"><span>1</span></td>
                                        <td class="rank1 yilou rank3"><span>13</span></td>
                                        <td class="rank1 yilou rank3"><span>10</span></td>
                                        <td class="rank1 yilou rank3"><span>8</span></td>
                                        <td class="rank1 yilou rank3"><span>2</span></td>
                                        <td class="rank1 yilou rank4"><span>1</span></td>
                                        <td class="rank1 yilou rank4"><span>23</span></td>
                                        <td class="rank1 yilou rank4"><span>8</span></td>
                                        <td class="rank1 yilou rank4"><span>12</span></td>
                                        <td class="rank1 yilou rank4"><span>9</span></td>
                                        <td title="0" class="hot rank4"><span name="hotSpan" class="zoushiqiu gqB"
                                                                              style="background: rgb(133, 133, 251);">5</span>
                                        </td>
                                        <td class="rank1 yilou rank4"><span>13</span></td>
                                        <td class="rank1 yilou rank4"><span>4</span></td>
                                        <td class="rank1 yilou rank4"><span>3</span></td>
                                        <td class="rank1 yilou rank4"><span>2</span></td>
                                        <td class="rank1 yilou rank5"><span>7</span></td>
                                        <td title="0" class="hot rank5"><span name="hotSpan" class="zoushiqiu grB"
                                                                              style="background: rgb(70, 189, 149);">1</span>
                                        </td>
                                        <td class="rank1 yilou rank5"><span>48</span></td>
                                        <td class="rank1 yilou rank5"><span>3</span></td>
                                        <td class="rank1 yilou rank5"><span>6</span></td>
                                        <td class="rank1 yilou rank5"><span>16</span></td>
                                        <td class="rank1 yilou rank5"><span>2</span></td>
                                        <td class="rank1 yilou rank5"><span>10</span></td>
                                        <td class="rank1 yilou rank5"><span>5</span></td>
                                        <td class="rank1 yilou rank5"><span>1</span></td>
                                    </tr>
                                    <tr class="yiloutr" height="32">
                                        <td class="leftth">2020110423</td>
                                        <td><span class="kaijnum">2</span><span class="kaijnum">9</span><span
                                            class="kaijnum">5</span><span class="kaijnum">0</span><span class="kaijnum">9</span>
                                        </td>
                                        <td class="rank1 yilou"><span>4</span></td>
                                        <td class="rank1 yilou"><span>1</span></td>
                                        <td title="0" class="hot rank1"><span name="hotSpan" class="zoushiqiu bBK"
                                                                              style="background: rgb(251, 167, 94);">2</span>
                                        </td>
                                        <td class="rank1 yilou"><span>6</span></td>
                                        <td class="rank1 yilou"><span>9</span></td>
                                        <td class="rank1 yilou"><span>17</span></td>
                                        <td class="rank1 yilou"><span>7</span></td>
                                        <td class="rank1 yilou"><span>10</span></td>
                                        <td class="rank1 yilou"><span>8</span></td>
                                        <td class="rank1 yilou"><span>2</span></td>
                                        <td class="rank1 yilou rank2"><span>59</span></td>
                                        <td class="rank1 yilou rank2"><span>5</span></td>
                                        <td class="rank1 yilou rank2"><span>7</span></td>
                                        <td class="rank1 yilou rank2"><span>1</span></td>
                                        <td class="rank1 yilou rank2"><span>2</span></td>
                                        <td class="rank1 yilou rank2"><span>3</span></td>
                                        <td class="rank1 yilou rank2"><span>8</span></td>
                                        <td class="rank1 yilou rank2"><span>26</span></td>
                                        <td class="rank1 yilou rank2"><span>6</span></td>
                                        <td title="0" class="hot rank2"><span name="hotSpan" class="zoushiqiu gB"
                                                                              style="background: rgb(31, 166, 232);">9</span>
                                        </td>
                                        <td class="rank1 yilou rank3"><span>2</span></td>
                                        <td class="rank1 yilou rank3"><span>16</span></td>
                                        <td class="rank1 yilou rank3"><span>6</span></td>
                                        <td class="rank1 yilou rank3"><span>4</span></td>
                                        <td class="rank1 yilou rank3"><span>5</span></td>
                                        <td title="0" class="hot rank3"><span name="hotSpan" class="zoushiqiu oB"
                                                                              style="background: rgb(8, 191, 2);">5</span>
                                        </td>
                                        <td class="rank1 yilou rank3"><span>12</span></td>
                                        <td class="rank1 yilou rank3"><span>9</span></td>
                                        <td class="rank1 yilou rank3"><span>7</span></td>
                                        <td class="rank1 yilou rank3"><span>1</span></td>
                                        <td title="0" class="hot rank4"><span name="hotSpan" class="zoushiqiu gqB"
                                                                              style="background: rgb(133, 133, 251);">0</span>
                                        </td>
                                        <td class="rank1 yilou rank4"><span>22</span></td>
                                        <td class="rank1 yilou rank4"><span>7</span></td>
                                        <td class="rank1 yilou rank4"><span>11</span></td>
                                        <td class="rank1 yilou rank4"><span>8</span></td>
                                        <td class="rank1 yilou rank4"><span>4</span></td>
                                        <td class="rank1 yilou rank4"><span>12</span></td>
                                        <td class="rank1 yilou rank4"><span>3</span></td>
                                        <td class="rank1 yilou rank4"><span>2</span></td>
                                        <td class="rank1 yilou rank4"><span>1</span></td>
                                        <td class="rank1 yilou rank5"><span>6</span></td>
                                        <td class="rank1 yilou rank5"><span>3</span></td>
                                        <td class="rank1 yilou rank5"><span>47</span></td>
                                        <td class="rank1 yilou rank5"><span>2</span></td>
                                        <td class="rank1 yilou rank5"><span>5</span></td>
                                        <td class="rank1 yilou rank5"><span>15</span></td>
                                        <td class="rank1 yilou rank5"><span>1</span></td>
                                        <td class="rank1 yilou rank5"><span>9</span></td>
                                        <td class="rank1 yilou rank5"><span>4</span></td>
                                        <td title="0" class="hot rank5"><span name="hotSpan" class="zoushiqiu grB"
                                                                              style="background: rgb(70, 189, 149);">9</span>
                                        </td>
                                    </tr>
                                    <tr class="yiloutr" height="32">
                                        <td class="leftth">2020110422</td>
                                        <td><span class="kaijnum">1</span><span class="kaijnum">3</span><span
                                            class="kaijnum">9</span><span class="kaijnum">9</span><span class="kaijnum">6</span>
                                        </td>
                                        <td class="rank1 yilou"><span>3</span></td>
                                        <td title="0" class="hot rank1"><span name="hotSpan" class="zoushiqiu bBK"
                                                                              style="background: rgb(251, 167, 94);">1</span>
                                        </td>
                                        <td class="rank1 yilou"><span>4</span></td>
                                        <td class="rank1 yilou"><span>5</span></td>
                                        <td class="rank1 yilou"><span>8</span></td>
                                        <td class="rank1 yilou"><span>16</span></td>
                                        <td class="rank1 yilou"><span>6</span></td>
                                        <td class="rank1 yilou"><span>9</span></td>
                                        <td class="rank1 yilou"><span>7</span></td>
                                        <td class="rank1 yilou"><span>1</span></td>
                                        <td class="rank1 yilou rank2"><span>58</span></td>
                                        <td class="rank1 yilou rank2"><span>4</span></td>
                                        <td class="rank1 yilou rank2"><span>6</span></td>
                                        <td title="0" class="hot rank2"><span name="hotSpan" class="zoushiqiu gB"
                                                                              style="background: rgb(31, 166, 232);">3</span>
                                        </td>
                                        <td class="rank1 yilou rank2"><span>1</span></td>
                                        <td class="rank1 yilou rank2"><span>2</span></td>
                                        <td class="rank1 yilou rank2"><span>7</span></td>
                                        <td class="rank1 yilou rank2"><span>25</span></td>
                                        <td class="rank1 yilou rank2"><span>5</span></td>
                                        <td class="rank1 yilou rank2"><span>3</span></td>
                                        <td class="rank1 yilou rank3"><span>1</span></td>
                                        <td class="rank1 yilou rank3"><span>15</span></td>
                                        <td class="rank1 yilou rank3"><span>5</span></td>
                                        <td class="rank1 yilou rank3"><span>3</span></td>
                                        <td class="rank1 yilou rank3"><span>4</span></td>
                                        <td class="rank1 yilou rank3"><span>18</span></td>
                                        <td class="rank1 yilou rank3"><span>11</span></td>
                                        <td class="rank1 yilou rank3"><span>8</span></td>
                                        <td class="rank1 yilou rank3"><span>6</span></td>
                                        <td title="0" class="hot rank3"><span name="hotSpan" class="zoushiqiu oB"
                                                                              style="background: rgb(8, 191, 2);">9</span>
                                        </td>
                                        <td class="rank1 yilou rank4"><span>8</span></td>
                                        <td class="rank1 yilou rank4"><span>21</span></td>
                                        <td class="rank1 yilou rank4"><span>6</span></td>
                                        <td class="rank1 yilou rank4"><span>10</span></td>
                                        <td class="rank1 yilou rank4"><span>7</span></td>
                                        <td class="rank1 yilou rank4"><span>3</span></td>
                                        <td class="rank1 yilou rank4"><span>11</span></td>
                                        <td class="rank1 yilou rank4"><span>2</span></td>
                                        <td class="rank1 yilou rank4"><span>1</span></td>
                                        <td title="0" class="hot rank4"><span name="hotSpan" class="zoushiqiu gqB"
                                                                              style="background: rgb(133, 133, 251);">9</span>
                                        </td>
                                        <td class="rank1 yilou rank5"><span>5</span></td>
                                        <td class="rank1 yilou rank5"><span>2</span></td>
                                        <td class="rank1 yilou rank5"><span>46</span></td>
                                        <td class="rank1 yilou rank5"><span>1</span></td>
                                        <td class="rank1 yilou rank5"><span>4</span></td>
                                        <td class="rank1 yilou rank5"><span>14</span></td>
                                        <td title="0" class="hot rank5"><span name="hotSpan" class="zoushiqiu grB"
                                                                              style="background: rgb(70, 189, 149);">6</span>
                                        </td>
                                        <td class="rank1 yilou rank5"><span>8</span></td>
                                        <td class="rank1 yilou rank5"><span>3</span></td>
                                        <td class="rank1 yilou rank5"><span>9</span></td>
                                    </tr>
                                    <tr class="yiloutr" height="32">
                                        <td class="leftth">2020110421</td>
                                        <td><span class="kaijnum">9</span><span class="kaijnum">4</span><span
                                            class="kaijnum">0</span><span class="kaijnum">8</span><span class="kaijnum">3</span>
                                        </td>
                                        <td class="rank1 yilou"><span>2</span></td>
                                        <td class="rank1 yilou"><span>33</span></td>
                                        <td class="rank1 yilou"><span>3</span></td>
                                        <td class="rank1 yilou"><span>4</span></td>
                                        <td class="rank1 yilou"><span>7</span></td>
                                        <td class="rank1 yilou"><span>15</span></td>
                                        <td class="rank1 yilou"><span>5</span></td>
                                        <td class="rank1 yilou"><span>8</span></td>
                                        <td class="rank1 yilou"><span>6</span></td>
                                        <td title="0" class="hot rank1"><span name="hotSpan" class="zoushiqiu bBK"
                                                                              style="background: rgb(251, 167, 94);">9</span>
                                        </td>
                                        <td class="rank1 yilou rank2"><span>57</span></td>
                                        <td class="rank1 yilou rank2"><span>3</span></td>
                                        <td class="rank1 yilou rank2"><span>5</span></td>
                                        <td class="rank1 yilou rank2"><span>11</span></td>
                                        <td title="0" class="hot rank2"><span name="hotSpan" class="zoushiqiu gB"
                                                                              style="background: rgb(31, 166, 232);">4</span>
                                        </td>
                                        <td class="rank1 yilou rank2"><span>1</span></td>
                                        <td class="rank1 yilou rank2"><span>6</span></td>
                                        <td class="rank1 yilou rank2"><span>24</span></td>
                                        <td class="rank1 yilou rank2"><span>4</span></td>
                                        <td class="rank1 yilou rank2"><span>2</span></td>
                                        <td title="0" class="hot rank3"><span name="hotSpan" class="zoushiqiu oB"
                                                                              style="background: rgb(8, 191, 2);">0</span>
                                        </td>
                                        <td class="rank1 yilou rank3"><span>14</span></td>
                                        <td class="rank1 yilou rank3"><span>4</span></td>
                                        <td class="rank1 yilou rank3"><span>2</span></td>
                                        <td class="rank1 yilou rank3"><span>3</span></td>
                                        <td class="rank1 yilou rank3"><span>17</span></td>
                                        <td class="rank1 yilou rank3"><span>10</span></td>
                                        <td class="rank1 yilou rank3"><span>7</span></td>
                                        <td class="rank1 yilou rank3"><span>5</span></td>
                                        <td class="rank1 yilou rank3"><span>6</span></td>
                                        <td class="rank1 yilou rank4"><span>7</span></td>
                                        <td class="rank1 yilou rank4"><span>20</span></td>
                                        <td class="rank1 yilou rank4"><span>5</span></td>
                                        <td class="rank1 yilou rank4"><span>9</span></td>
                                        <td class="rank1 yilou rank4"><span>6</span></td>
                                        <td class="rank1 yilou rank4"><span>2</span></td>
                                        <td class="rank1 yilou rank4"><span>10</span></td>
                                        <td class="rank1 yilou rank4"><span>1</span></td>
                                        <td title="0" class="hot rank4"><span name="hotSpan" class="zoushiqiu gqB"
                                                                              style="background: rgb(133, 133, 251);">8</span>
                                        </td>
                                        <td class="rank1 yilou rank4"><span>8</span></td>
                                        <td class="rank1 yilou rank5"><span>4</span></td>
                                        <td class="rank1 yilou rank5"><span>1</span></td>
                                        <td class="rank1 yilou rank5"><span>45</span></td>
                                        <td title="0" class="hot rank5"><span name="hotSpan" class="zoushiqiu grB"
                                                                              style="background: rgb(70, 189, 149);">3</span>
                                        </td>
                                        <td class="rank1 yilou rank5"><span>3</span></td>
                                        <td class="rank1 yilou rank5"><span>13</span></td>
                                        <td class="rank1 yilou rank5"><span>6</span></td>
                                        <td class="rank1 yilou rank5"><span>7</span></td>
                                        <td class="rank1 yilou rank5"><span>2</span></td>
                                        <td class="rank1 yilou rank5"><span>8</span></td>
                                    </tr>
                                    <tr class="yiloutr" height="32">
                                        <td class="leftth">2020110420</td>
                                        <td><span class="kaijnum">9</span><span class="kaijnum">5</span><span
                                            class="kaijnum">0</span><span class="kaijnum">7</span><span class="kaijnum">1</span>
                                        </td>
                                        <td class="rank1 yilou"><span>1</span></td>
                                        <td class="rank1 yilou"><span>32</span></td>
                                        <td class="rank1 yilou"><span>2</span></td>
                                        <td class="rank1 yilou"><span>3</span></td>
                                        <td class="rank1 yilou"><span>6</span></td>
                                        <td class="rank1 yilou"><span>14</span></td>
                                        <td class="rank1 yilou"><span>4</span></td>
                                        <td class="rank1 yilou"><span>7</span></td>
                                        <td class="rank1 yilou"><span>5</span></td>
                                        <td title="0" class="hot rank1"><span name="hotSpan" class="zoushiqiu bBK"
                                                                              style="background: rgb(251, 167, 94);">9</span>
                                        </td>
                                        <td class="rank1 yilou rank2"><span>56</span></td>
                                        <td class="rank1 yilou rank2"><span>2</span></td>
                                        <td class="rank1 yilou rank2"><span>4</span></td>
                                        <td class="rank1 yilou rank2"><span>10</span></td>
                                        <td class="rank1 yilou rank2"><span>15</span></td>
                                        <td title="0" class="hot rank2"><span name="hotSpan" class="zoushiqiu gB"
                                                                              style="background: rgb(31, 166, 232);">5</span>
                                        </td>
                                        <td class="rank1 yilou rank2"><span>5</span></td>
                                        <td class="rank1 yilou rank2"><span>23</span></td>
                                        <td class="rank1 yilou rank2"><span>3</span></td>
                                        <td class="rank1 yilou rank2"><span>1</span></td>
                                        <td title="0" class="hot rank3"><span name="hotSpan" class="zoushiqiu oB"
                                                                              style="background: rgb(8, 191, 2);">0</span>
                                        </td>
                                        <td class="rank1 yilou rank3"><span>13</span></td>
                                        <td class="rank1 yilou rank3"><span>3</span></td>
                                        <td class="rank1 yilou rank3"><span>1</span></td>
                                        <td class="rank1 yilou rank3"><span>2</span></td>
                                        <td class="rank1 yilou rank3"><span>16</span></td>
                                        <td class="rank1 yilou rank3"><span>9</span></td>
                                        <td class="rank1 yilou rank3"><span>6</span></td>
                                        <td class="rank1 yilou rank3"><span>4</span></td>
                                        <td class="rank1 yilou rank3"><span>5</span></td>
                                        <td class="rank1 yilou rank4"><span>6</span></td>
                                        <td class="rank1 yilou rank4"><span>19</span></td>
                                        <td class="rank1 yilou rank4"><span>4</span></td>
                                        <td class="rank1 yilou rank4"><span>8</span></td>
                                        <td class="rank1 yilou rank4"><span>5</span></td>
                                        <td class="rank1 yilou rank4"><span>1</span></td>
                                        <td class="rank1 yilou rank4"><span>9</span></td>
                                        <td title="0" class="hot rank4"><span name="hotSpan" class="zoushiqiu gqB"
                                                                              style="background: rgb(133, 133, 251);">7</span>
                                        </td>
                                        <td class="rank1 yilou rank4"><span>15</span></td>
                                        <td class="rank1 yilou rank4"><span>7</span></td>
                                        <td class="rank1 yilou rank5"><span>3</span></td>
                                        <td title="0" class="hot rank5"><span name="hotSpan" class="zoushiqiu grB"
                                                                              style="background: rgb(70, 189, 149);">1</span>
                                        </td>
                                        <td class="rank1 yilou rank5"><span>44</span></td>
                                        <td class="rank1 yilou rank5"><span>14</span></td>
                                        <td class="rank1 yilou rank5"><span>2</span></td>
                                        <td class="rank1 yilou rank5"><span>12</span></td>
                                        <td class="rank1 yilou rank5"><span>5</span></td>
                                        <td class="rank1 yilou rank5"><span>6</span></td>
                                        <td class="rank1 yilou rank5"><span>1</span></td>
                                        <td class="rank1 yilou rank5"><span>7</span></td>
                                    </tr>
                                    <tr class="yiloutr" height="32">
                                        <td class="leftth">2020110419</td>
                                        <td><span class="kaijnum">0</span><span class="kaijnum">9</span><span
                                            class="kaijnum">3</span><span class="kaijnum">5</span><span class="kaijnum">8</span>
                                        </td>
                                        <td title="0" class="hot rank1"><span name="hotSpan" class="zoushiqiu bBK"
                                                                              style="background: rgb(251, 167, 94);">0</span>
                                        </td>
                                        <td class="rank1 yilou"><span>31</span></td>
                                        <td class="rank1 yilou"><span>1</span></td>
                                        <td class="rank1 yilou"><span>2</span></td>
                                        <td class="rank1 yilou"><span>5</span></td>
                                        <td class="rank1 yilou"><span>13</span></td>
                                        <td class="rank1 yilou"><span>3</span></td>
                                        <td class="rank1 yilou"><span>6</span></td>
                                        <td class="rank1 yilou"><span>4</span></td>
                                        <td class="rank1 yilou"><span>22</span></td>
                                        <td class="rank1 yilou rank2"><span>55</span></td>
                                        <td class="rank1 yilou rank2"><span>1</span></td>
                                        <td class="rank1 yilou rank2"><span>3</span></td>
                                        <td class="rank1 yilou rank2"><span>9</span></td>
                                        <td class="rank1 yilou rank2"><span>14</span></td>
                                        <td class="rank1 yilou rank2"><span>11</span></td>
                                        <td class="rank1 yilou rank2"><span>4</span></td>
                                        <td class="rank1 yilou rank2"><span>22</span></td>
                                        <td class="rank1 yilou rank2"><span>2</span></td>
                                        <td title="0" class="hot rank2"><span name="hotSpan" class="zoushiqiu gB"
                                                                              style="background: rgb(31, 166, 232);">9</span>
                                        </td>
                                        <td class="rank1 yilou rank3"><span>13</span></td>
                                        <td class="rank1 yilou rank3"><span>12</span></td>
                                        <td class="rank1 yilou rank3"><span>2</span></td>
                                        <td title="0" class="hot rank3"><span name="hotSpan" class="zoushiqiu oB"
                                                                              style="background: rgb(8, 191, 2);">3</span>
                                        </td>
                                        <td class="rank1 yilou rank3"><span>1</span></td>
                                        <td class="rank1 yilou rank3"><span>15</span></td>
                                        <td class="rank1 yilou rank3"><span>8</span></td>
                                        <td class="rank1 yilou rank3"><span>5</span></td>
                                        <td class="rank1 yilou rank3"><span>3</span></td>
                                        <td class="rank1 yilou rank3"><span>4</span></td>
                                        <td class="rank1 yilou rank4"><span>5</span></td>
                                        <td class="rank1 yilou rank4"><span>18</span></td>
                                        <td class="rank1 yilou rank4"><span>3</span></td>
                                        <td class="rank1 yilou rank4"><span>7</span></td>
                                        <td class="rank1 yilou rank4"><span>4</span></td>
                                        <td title="0" class="hot rank4"><span name="hotSpan" class="zoushiqiu gqB"
                                                                              style="background: rgb(133, 133, 251);">5</span>
                                        </td>
                                        <td class="rank1 yilou rank4"><span>8</span></td>
                                        <td class="rank1 yilou rank4"><span>2</span></td>
                                        <td class="rank1 yilou rank4"><span>14</span></td>
                                        <td class="rank1 yilou rank4"><span>6</span></td>
                                        <td class="rank1 yilou rank5"><span>2</span></td>
                                        <td class="rank1 yilou rank5"><span>10</span></td>
                                        <td class="rank1 yilou rank5"><span>43</span></td>
                                        <td class="rank1 yilou rank5"><span>13</span></td>
                                        <td class="rank1 yilou rank5"><span>1</span></td>
                                        <td class="rank1 yilou rank5"><span>11</span></td>
                                        <td class="rank1 yilou rank5"><span>4</span></td>
                                        <td class="rank1 yilou rank5"><span>5</span></td>
                                        <td title="0" class="hot rank5"><span name="hotSpan" class="zoushiqiu grB"
                                                                              style="background: rgb(70, 189, 149);">8</span>
                                        </td>
                                        <td class="rank1 yilou rank5"><span>6</span></td>
                                    </tr>
                                    <tr class="yiloutr" height="32">
                                        <td class="leftth">2020110418</td>
                                        <td><span class="kaijnum">2</span><span class="kaijnum">1</span><span
                                            class="kaijnum">4</span><span class="kaijnum">5</span><span class="kaijnum">4</span>
                                        </td>
                                        <td class="rank1 yilou"><span>18</span></td>
                                        <td class="rank1 yilou"><span>30</span></td>
                                        <td title="0" class="hot rank1"><span name="hotSpan" class="zoushiqiu bBK"
                                                                              style="background: rgb(251, 167, 94);">2</span>
                                        </td>
                                        <td class="rank1 yilou"><span>1</span></td>
                                        <td class="rank1 yilou"><span>4</span></td>
                                        <td class="rank1 yilou"><span>12</span></td>
                                        <td class="rank1 yilou"><span>2</span></td>
                                        <td class="rank1 yilou"><span>5</span></td>
                                        <td class="rank1 yilou"><span>3</span></td>
                                        <td class="rank1 yilou"><span>21</span></td>
                                        <td class="rank1 yilou rank2"><span>54</span></td>
                                        <td title="0" class="hot rank2"><span name="hotSpan" class="zoushiqiu gB"
                                                                              style="background: rgb(31, 166, 232);">1</span>
                                        </td>
                                        <td class="rank1 yilou rank2"><span>2</span></td>
                                        <td class="rank1 yilou rank2"><span>8</span></td>
                                        <td class="rank1 yilou rank2"><span>13</span></td>
                                        <td class="rank1 yilou rank2"><span>10</span></td>
                                        <td class="rank1 yilou rank2"><span>3</span></td>
                                        <td class="rank1 yilou rank2"><span>21</span></td>
                                        <td class="rank1 yilou rank2"><span>1</span></td>
                                        <td class="rank1 yilou rank2"><span>17</span></td>
                                        <td class="rank1 yilou rank3"><span>12</span></td>
                                        <td class="rank1 yilou rank3"><span>11</span></td>
                                        <td class="rank1 yilou rank3"><span>1</span></td>
                                        <td class="rank1 yilou rank3"><span>5</span></td>
                                        <td title="0" class="hot rank3"><span name="hotSpan" class="zoushiqiu oB"
                                                                              style="background: rgb(8, 191, 2);">4</span>
                                        </td>
                                        <td class="rank1 yilou rank3"><span>14</span></td>
                                        <td class="rank1 yilou rank3"><span>7</span></td>
                                        <td class="rank1 yilou rank3"><span>4</span></td>
                                        <td class="rank1 yilou rank3"><span>2</span></td>
                                        <td class="rank1 yilou rank3"><span>3</span></td>
                                        <td class="rank1 yilou rank4"><span>4</span></td>
                                        <td class="rank1 yilou rank4"><span>17</span></td>
                                        <td class="rank1 yilou rank4"><span>2</span></td>
                                        <td class="rank1 yilou rank4"><span>6</span></td>
                                        <td class="rank1 yilou rank4"><span>3</span></td>
                                        <td title="0" class="hot rank4"><span name="hotSpan" class="zoushiqiu gqB"
                                                                              style="background: rgb(133, 133, 251);">5</span>
                                        </td>
                                        <td class="rank1 yilou rank4"><span>7</span></td>
                                        <td class="rank1 yilou rank4"><span>1</span></td>
                                        <td class="rank1 yilou rank4"><span>13</span></td>
                                        <td class="rank1 yilou rank4"><span>5</span></td>
                                        <td class="rank1 yilou rank5"><span>1</span></td>
                                        <td class="rank1 yilou rank5"><span>9</span></td>
                                        <td class="rank1 yilou rank5"><span>42</span></td>
                                        <td class="rank1 yilou rank5"><span>12</span></td>
                                        <td title="0" class="hot rank5"><span name="hotSpan" class="zoushiqiu grB"
                                                                              style="background: rgb(70, 189, 149);">4</span>
                                        </td>
                                        <td class="rank1 yilou rank5"><span>10</span></td>
                                        <td class="rank1 yilou rank5"><span>3</span></td>
                                        <td class="rank1 yilou rank5"><span>4</span></td>
                                        <td class="rank1 yilou rank5"><span>2</span></td>
                                        <td class="rank1 yilou rank5"><span>5</span></td>
                                    </tr>
                                    <tr class="yiloutr" height="32">
                                        <td class="leftth">2020110417</td>
                                        <td><span class="kaijnum">3</span><span class="kaijnum">8</span><span
                                            class="kaijnum">2</span><span class="kaijnum">7</span><span class="kaijnum">0</span>
                                        </td>
                                        <td class="rank1 yilou"><span>17</span></td>
                                        <td class="rank1 yilou"><span>29</span></td>
                                        <td class="rank1 yilou"><span>14</span></td>
                                        <td title="0" class="hot rank1"><span name="hotSpan" class="zoushiqiu bBK"
                                                                              style="background: rgb(251, 167, 94);">3</span>
                                        </td>
                                        <td class="rank1 yilou"><span>3</span></td>
                                        <td class="rank1 yilou"><span>11</span></td>
                                        <td class="rank1 yilou"><span>1</span></td>
                                        <td class="rank1 yilou"><span>4</span></td>
                                        <td class="rank1 yilou"><span>2</span></td>
                                        <td class="rank1 yilou"><span>20</span></td>
                                        <td class="rank1 yilou rank2"><span>53</span></td>
                                        <td class="rank1 yilou rank2"><span>3</span></td>
                                        <td class="rank1 yilou rank2"><span>1</span></td>
                                        <td class="rank1 yilou rank2"><span>7</span></td>
                                        <td class="rank1 yilou rank2"><span>12</span></td>
                                        <td class="rank1 yilou rank2"><span>9</span></td>
                                        <td class="rank1 yilou rank2"><span>2</span></td>
                                        <td class="rank1 yilou rank2"><span>20</span></td>
                                        <td title="0" class="hot rank2"><span name="hotSpan" class="zoushiqiu gB"
                                                                              style="background: rgb(31, 166, 232);">8</span>
                                        </td>
                                        <td class="rank1 yilou rank2"><span>16</span></td>
                                        <td class="rank1 yilou rank3"><span>11</span></td>
                                        <td class="rank1 yilou rank3"><span>10</span></td>
                                        <td title="0" class="hot rank3"><span name="hotSpan" class="zoushiqiu oB"
                                                                              style="background: rgb(8, 191, 2);">2</span>
                                        </td>
                                        <td class="rank1 yilou rank3"><span>4</span></td>
                                        <td class="rank1 yilou rank3"><span>7</span></td>
                                        <td class="rank1 yilou rank3"><span>13</span></td>
                                        <td class="rank1 yilou rank3"><span>6</span></td>
                                        <td class="rank1 yilou rank3"><span>3</span></td>
                                        <td class="rank1 yilou rank3"><span>1</span></td>
                                        <td class="rank1 yilou rank3"><span>2</span></td>
                                        <td class="rank1 yilou rank4"><span>3</span></td>
                                        <td class="rank1 yilou rank4"><span>16</span></td>
                                        <td class="rank1 yilou rank4"><span>1</span></td>
                                        <td class="rank1 yilou rank4"><span>5</span></td>
                                        <td class="rank1 yilou rank4"><span>2</span></td>
                                        <td class="rank1 yilou rank4"><span>29</span></td>
                                        <td class="rank1 yilou rank4"><span>6</span></td>
                                        <td title="0" class="hot rank4"><span name="hotSpan" class="zoushiqiu gqB"
                                                                              style="background: rgb(133, 133, 251);">7</span>
                                        </td>
                                        <td class="rank1 yilou rank4"><span>12</span></td>
                                        <td class="rank1 yilou rank4"><span>4</span></td>
                                        <td title="0" class="hot rank5"><span name="hotSpan" class="zoushiqiu grB"
                                                                              style="background: rgb(70, 189, 149);">0</span>
                                        </td>
                                        <td class="rank1 yilou rank5"><span>8</span></td>
                                        <td class="rank1 yilou rank5"><span>41</span></td>
                                        <td class="rank1 yilou rank5"><span>11</span></td>
                                        <td class="rank1 yilou rank5"><span>5</span></td>
                                        <td class="rank1 yilou rank5"><span>9</span></td>
                                        <td class="rank1 yilou rank5"><span>2</span></td>
                                        <td class="rank1 yilou rank5"><span>3</span></td>
                                        <td class="rank1 yilou rank5"><span>1</span></td>
                                        <td class="rank1 yilou rank5"><span>4</span></td>
                                    </tr>
                                    <tr class="yiloutr" height="32">
                                        <td class="leftth">2020110416</td>
                                        <td><span class="kaijnum">6</span><span class="kaijnum">2</span><span
                                            class="kaijnum">8</span><span class="kaijnum">2</span><span class="kaijnum">8</span>
                                        </td>
                                        <td class="rank1 yilou"><span>16</span></td>
                                        <td class="rank1 yilou"><span>28</span></td>
                                        <td class="rank1 yilou"><span>13</span></td>
                                        <td class="rank1 yilou"><span>11</span></td>
                                        <td class="rank1 yilou"><span>2</span></td>
                                        <td class="rank1 yilou"><span>10</span></td>
                                        <td title="0" class="hot rank1"><span name="hotSpan" class="zoushiqiu bBK"
                                                                              style="background: rgb(251, 167, 94);">6</span>
                                        </td>
                                        <td class="rank1 yilou"><span>3</span></td>
                                        <td class="rank1 yilou"><span>1</span></td>
                                        <td class="rank1 yilou"><span>19</span></td>
                                        <td class="rank1 yilou rank2"><span>52</span></td>
                                        <td class="rank1 yilou rank2"><span>2</span></td>
                                        <td title="0" class="hot rank2"><span name="hotSpan" class="zoushiqiu gB"
                                                                              style="background: rgb(31, 166, 232);">2</span>
                                        </td>
                                        <td class="rank1 yilou rank2"><span>6</span></td>
                                        <td class="rank1 yilou rank2"><span>11</span></td>
                                        <td class="rank1 yilou rank2"><span>8</span></td>
                                        <td class="rank1 yilou rank2"><span>1</span></td>
                                        <td class="rank1 yilou rank2"><span>19</span></td>
                                        <td class="rank1 yilou rank2"><span>7</span></td>
                                        <td class="rank1 yilou rank2"><span>15</span></td>
                                        <td class="rank1 yilou rank3"><span>10</span></td>
                                        <td class="rank1 yilou rank3"><span>9</span></td>
                                        <td class="rank1 yilou rank3"><span>18</span></td>
                                        <td class="rank1 yilou rank3"><span>3</span></td>
                                        <td class="rank1 yilou rank3"><span>6</span></td>
                                        <td class="rank1 yilou rank3"><span>12</span></td>
                                        <td class="rank1 yilou rank3"><span>5</span></td>
                                        <td class="rank1 yilou rank3"><span>2</span></td>
                                        <td title="0" class="hot rank3"><span name="hotSpan" class="zoushiqiu oB"
                                                                              style="background: rgb(8, 191, 2);">8</span>
                                        </td>
                                        <td class="rank1 yilou rank3"><span>1</span></td>
                                        <td class="rank1 yilou rank4"><span>2</span></td>
                                        <td class="rank1 yilou rank4"><span>15</span></td>
                                        <td title="0" class="hot rank4"><span name="hotSpan" class="zoushiqiu gqB"
                                                                              style="background: rgb(133, 133, 251);">2</span>
                                        </td>
                                        <td class="rank1 yilou rank4"><span>4</span></td>
                                        <td class="rank1 yilou rank4"><span>1</span></td>
                                        <td class="rank1 yilou rank4"><span>28</span></td>
                                        <td class="rank1 yilou rank4"><span>5</span></td>
                                        <td class="rank1 yilou rank4"><span>10</span></td>
                                        <td class="rank1 yilou rank4"><span>11</span></td>
                                        <td class="rank1 yilou rank4"><span>3</span></td>
                                        <td class="rank1 yilou rank5"><span>5</span></td>
                                        <td class="rank1 yilou rank5"><span>7</span></td>
                                        <td class="rank1 yilou rank5"><span>40</span></td>
                                        <td class="rank1 yilou rank5"><span>10</span></td>
                                        <td class="rank1 yilou rank5"><span>4</span></td>
                                        <td class="rank1 yilou rank5"><span>8</span></td>
                                        <td class="rank1 yilou rank5"><span>1</span></td>
                                        <td class="rank1 yilou rank5"><span>2</span></td>
                                        <td title="0" class="hot rank5"><span name="hotSpan" class="zoushiqiu grB"
                                                                              style="background: rgb(70, 189, 149);">8</span>
                                        </td>
                                        <td class="rank1 yilou rank5"><span>3</span></td>
                                    </tr>
                                    <tr class="yiloutr" height="32">
                                        <td class="leftth">2020110415</td>
                                        <td><span class="kaijnum">8</span><span class="kaijnum">6</span><span
                                            class="kaijnum">9</span><span class="kaijnum">4</span><span class="kaijnum">6</span>
                                        </td>
                                        <td class="rank1 yilou"><span>15</span></td>
                                        <td class="rank1 yilou"><span>27</span></td>
                                        <td class="rank1 yilou"><span>12</span></td>
                                        <td class="rank1 yilou"><span>10</span></td>
                                        <td class="rank1 yilou"><span>1</span></td>
                                        <td class="rank1 yilou"><span>9</span></td>
                                        <td class="rank1 yilou"><span>6</span></td>
                                        <td class="rank1 yilou"><span>2</span></td>
                                        <td title="0" class="hot rank1"><span name="hotSpan" class="zoushiqiu bBK"
                                                                              style="background: rgb(251, 167, 94);">8</span>
                                        </td>
                                        <td class="rank1 yilou"><span>18</span></td>
                                        <td class="rank1 yilou rank2"><span>51</span></td>
                                        <td class="rank1 yilou rank2"><span>1</span></td>
                                        <td class="rank1 yilou rank2"><span>2</span></td>
                                        <td class="rank1 yilou rank2"><span>5</span></td>
                                        <td class="rank1 yilou rank2"><span>10</span></td>
                                        <td class="rank1 yilou rank2"><span>7</span></td>
                                        <td title="0" class="hot rank2"><span name="hotSpan" class="zoushiqiu gB"
                                                                              style="background: rgb(31, 166, 232);">6</span>
                                        </td>
                                        <td class="rank1 yilou rank2"><span>18</span></td>
                                        <td class="rank1 yilou rank2"><span>6</span></td>
                                        <td class="rank1 yilou rank2"><span>14</span></td>
                                        <td class="rank1 yilou rank3"><span>9</span></td>
                                        <td class="rank1 yilou rank3"><span>8</span></td>
                                        <td class="rank1 yilou rank3"><span>17</span></td>
                                        <td class="rank1 yilou rank3"><span>2</span></td>
                                        <td class="rank1 yilou rank3"><span>5</span></td>
                                        <td class="rank1 yilou rank3"><span>11</span></td>
                                        <td class="rank1 yilou rank3"><span>4</span></td>
                                        <td class="rank1 yilou rank3"><span>1</span></td>
                                        <td class="rank1 yilou rank3"><span>3</span></td>
                                        <td title="0" class="hot rank3"><span name="hotSpan" class="zoushiqiu oB"
                                                                              style="background: rgb(8, 191, 2);">9</span>
                                        </td>
                                        <td class="rank1 yilou rank4"><span>1</span></td>
                                        <td class="rank1 yilou rank4"><span>14</span></td>
                                        <td class="rank1 yilou rank4"><span>5</span></td>
                                        <td class="rank1 yilou rank4"><span>3</span></td>
                                        <td title="0" class="hot rank4"><span name="hotSpan" class="zoushiqiu gqB"
                                                                              style="background: rgb(133, 133, 251);">4</span>
                                        </td>
                                        <td class="rank1 yilou rank4"><span>27</span></td>
                                        <td class="rank1 yilou rank4"><span>4</span></td>
                                        <td class="rank1 yilou rank4"><span>9</span></td>
                                        <td class="rank1 yilou rank4"><span>10</span></td>
                                        <td class="rank1 yilou rank4"><span>2</span></td>
                                        <td class="rank1 yilou rank5"><span>4</span></td>
                                        <td class="rank1 yilou rank5"><span>6</span></td>
                                        <td class="rank1 yilou rank5"><span>39</span></td>
                                        <td class="rank1 yilou rank5"><span>9</span></td>
                                        <td class="rank1 yilou rank5"><span>3</span></td>
                                        <td class="rank1 yilou rank5"><span>7</span></td>
                                        <td title="0" class="hot rank5"><span name="hotSpan" class="zoushiqiu grB"
                                                                              style="background: rgb(70, 189, 149);">6</span>
                                        </td>
                                        <td class="rank1 yilou rank5"><span>1</span></td>
                                        <td class="rank1 yilou rank5"><span>11</span></td>
                                        <td class="rank1 yilou rank5"><span>2</span></td>
                                    </tr>
                                    <tr class="yiloutr" height="32">
                                        <td class="leftth">2020110414</td>
                                        <td><span class="kaijnum">4</span><span class="kaijnum">1</span><span
                                            class="kaijnum">7</span><span class="kaijnum">0</span><span class="kaijnum">7</span>
                                        </td>
                                        <td class="rank1 yilou"><span>14</span></td>
                                        <td class="rank1 yilou"><span>26</span></td>
                                        <td class="rank1 yilou"><span>11</span></td>
                                        <td class="rank1 yilou"><span>9</span></td>
                                        <td title="0" class="hot rank1"><span name="hotSpan" class="zoushiqiu bBK"
                                                                              style="background: rgb(251, 167, 94);">4</span>
                                        </td>
                                        <td class="rank1 yilou"><span>8</span></td>
                                        <td class="rank1 yilou"><span>5</span></td>
                                        <td class="rank1 yilou"><span>1</span></td>
                                        <td class="rank1 yilou"><span>2</span></td>
                                        <td class="rank1 yilou"><span>17</span></td>
                                        <td class="rank1 yilou rank2"><span>50</span></td>
                                        <td title="0" class="hot rank2"><span name="hotSpan" class="zoushiqiu gB"
                                                                              style="background: rgb(31, 166, 232);">1</span>
                                        </td>
                                        <td class="rank1 yilou rank2"><span>1</span></td>
                                        <td class="rank1 yilou rank2"><span>4</span></td>
                                        <td class="rank1 yilou rank2"><span>9</span></td>
                                        <td class="rank1 yilou rank2"><span>6</span></td>
                                        <td class="rank1 yilou rank2"><span>2</span></td>
                                        <td class="rank1 yilou rank2"><span>17</span></td>
                                        <td class="rank1 yilou rank2"><span>5</span></td>
                                        <td class="rank1 yilou rank2"><span>13</span></td>
                                        <td class="rank1 yilou rank3"><span>8</span></td>
                                        <td class="rank1 yilou rank3"><span>7</span></td>
                                        <td class="rank1 yilou rank3"><span>16</span></td>
                                        <td class="rank1 yilou rank3"><span>1</span></td>
                                        <td class="rank1 yilou rank3"><span>4</span></td>
                                        <td class="rank1 yilou rank3"><span>10</span></td>
                                        <td class="rank1 yilou rank3"><span>3</span></td>
                                        <td title="0" class="hot rank3"><span name="hotSpan" class="zoushiqiu oB"
                                                                              style="background: rgb(8, 191, 2);">7</span>
                                        </td>
                                        <td class="rank1 yilou rank3"><span>2</span></td>
                                        <td class="rank1 yilou rank3"><span>6</span></td>
                                        <td title="0" class="hot rank4"><span name="hotSpan" class="zoushiqiu gqB"
                                                                              style="background: rgb(133, 133, 251);">0</span>
                                        </td>
                                        <td class="rank1 yilou rank4"><span>13</span></td>
                                        <td class="rank1 yilou rank4"><span>4</span></td>
                                        <td class="rank1 yilou rank4"><span>2</span></td>
                                        <td class="rank1 yilou rank4"><span>6</span></td>
                                        <td class="rank1 yilou rank4"><span>26</span></td>
                                        <td class="rank1 yilou rank4"><span>3</span></td>
                                        <td class="rank1 yilou rank4"><span>8</span></td>
                                        <td class="rank1 yilou rank4"><span>9</span></td>
                                        <td class="rank1 yilou rank4"><span>1</span></td>
                                        <td class="rank1 yilou rank5"><span>3</span></td>
                                        <td class="rank1 yilou rank5"><span>5</span></td>
                                        <td class="rank1 yilou rank5"><span>38</span></td>
                                        <td class="rank1 yilou rank5"><span>8</span></td>
                                        <td class="rank1 yilou rank5"><span>2</span></td>
                                        <td class="rank1 yilou rank5"><span>6</span></td>
                                        <td class="rank1 yilou rank5"><span>14</span></td>
                                        <td title="0" class="hot rank5"><span name="hotSpan" class="zoushiqiu grB"
                                                                              style="background: rgb(70, 189, 149);">7</span>
                                        </td>
                                        <td class="rank1 yilou rank5"><span>10</span></td>
                                        <td class="rank1 yilou rank5"><span>1</span></td>
                                    </tr>
                                    <tr class="yiloutr" height="32">
                                        <td class="leftth">2020110413</td>
                                        <td><span class="kaijnum">7</span><span class="kaijnum">2</span><span
                                            class="kaijnum">3</span><span class="kaijnum">9</span><span class="kaijnum">9</span>
                                        </td>
                                        <td class="rank1 yilou"><span>13</span></td>
                                        <td class="rank1 yilou"><span>25</span></td>
                                        <td class="rank1 yilou"><span>10</span></td>
                                        <td class="rank1 yilou"><span>8</span></td>
                                        <td class="rank1 yilou"><span>15</span></td>
                                        <td class="rank1 yilou"><span>7</span></td>
                                        <td class="rank1 yilou"><span>4</span></td>
                                        <td title="0" class="hot rank1"><span name="hotSpan" class="zoushiqiu bBK"
                                                                              style="background: rgb(251, 167, 94);">7</span>
                                        </td>
                                        <td class="rank1 yilou"><span>1</span></td>
                                        <td class="rank1 yilou"><span>16</span></td>
                                        <td class="rank1 yilou rank2"><span>49</span></td>
                                        <td class="rank1 yilou rank2"><span>2</span></td>
                                        <td title="0" class="hot rank2"><span name="hotSpan" class="zoushiqiu gB"
                                                                              style="background: rgb(31, 166, 232);">2</span>
                                        </td>
                                        <td class="rank1 yilou rank2"><span>3</span></td>
                                        <td class="rank1 yilou rank2"><span>8</span></td>
                                        <td class="rank1 yilou rank2"><span>5</span></td>
                                        <td class="rank1 yilou rank2"><span>1</span></td>
                                        <td class="rank1 yilou rank2"><span>16</span></td>
                                        <td class="rank1 yilou rank2"><span>4</span></td>
                                        <td class="rank1 yilou rank2"><span>12</span></td>
                                        <td class="rank1 yilou rank3"><span>7</span></td>
                                        <td class="rank1 yilou rank3"><span>6</span></td>
                                        <td class="rank1 yilou rank3"><span>15</span></td>
                                        <td title="0" class="hot rank3"><span name="hotSpan" class="zoushiqiu oB"
                                                                              style="background: rgb(8, 191, 2);">3</span>
                                        </td>
                                        <td class="rank1 yilou rank3"><span>3</span></td>
                                        <td class="rank1 yilou rank3"><span>9</span></td>
                                        <td class="rank1 yilou rank3"><span>2</span></td>
                                        <td class="rank1 yilou rank3"><span>10</span></td>
                                        <td class="rank1 yilou rank3"><span>1</span></td>
                                        <td class="rank1 yilou rank3"><span>5</span></td>
                                        <td class="rank1 yilou rank4"><span>4</span></td>
                                        <td class="rank1 yilou rank4"><span>12</span></td>
                                        <td class="rank1 yilou rank4"><span>3</span></td>
                                        <td class="rank1 yilou rank4"><span>1</span></td>
                                        <td class="rank1 yilou rank4"><span>5</span></td>
                                        <td class="rank1 yilou rank4"><span>25</span></td>
                                        <td class="rank1 yilou rank4"><span>2</span></td>
                                        <td class="rank1 yilou rank4"><span>7</span></td>
                                        <td class="rank1 yilou rank4"><span>8</span></td>
                                        <td title="0" class="hot rank4"><span name="hotSpan" class="zoushiqiu gqB"
                                                                              style="background: rgb(133, 133, 251);">9</span>
                                        </td>
                                        <td class="rank1 yilou rank5"><span>2</span></td>
                                        <td class="rank1 yilou rank5"><span>4</span></td>
                                        <td class="rank1 yilou rank5"><span>37</span></td>
                                        <td class="rank1 yilou rank5"><span>7</span></td>
                                        <td class="rank1 yilou rank5"><span>1</span></td>
                                        <td class="rank1 yilou rank5"><span>5</span></td>
                                        <td class="rank1 yilou rank5"><span>13</span></td>
                                        <td class="rank1 yilou rank5"><span>31</span></td>
                                        <td class="rank1 yilou rank5"><span>9</span></td>
                                        <td title="0" class="hot rank5"><span name="hotSpan" class="zoushiqiu grB"
                                                                              style="background: rgb(70, 189, 149);">9</span>
                                        </td>
                                    </tr>
                                    <tr class="yiloutr" height="32">
                                        <td class="leftth tdbbs">2020110412</td>
                                        <td class="tdbbs"><span class="kaijnum">8</span><span
                                            class="kaijnum">6</span><span class="kaijnum">8</span><span class="kaijnum">3</span><span
                                            class="kaijnum">4</span></td>
                                        <td class="rank1 yilou tdbbs"><span>12</span></td>
                                        <td class="rank1 yilou tdbbs"><span>24</span></td>
                                        <td class="rank1 yilou tdbbs"><span>9</span></td>
                                        <td class="rank1 yilou tdbbs"><span>7</span></td>
                                        <td class="rank1 yilou tdbbs"><span>14</span></td>
                                        <td class="rank1 yilou tdbbs"><span>6</span></td>
                                        <td class="rank1 yilou tdbbs"><span>3</span></td>
                                        <td class="rank1 yilou tdbbs"><span>2</span></td>
                                        <td title="0" class="hot rank1 tdbbs"><span name="hotSpan" class="zoushiqiu bBK"
                                                                                    style="background: rgb(251, 167, 94);">8</span>
                                        </td>
                                        <td class="rank1 yilou tdbbs"><span>15</span></td>
                                        <td class="rank1 yilou rank2 tdbbs"><span>48</span></td>
                                        <td class="rank1 yilou rank2 tdbbs"><span>1</span></td>
                                        <td class="rank1 yilou rank2 tdbbs"><span>6</span></td>
                                        <td class="rank1 yilou rank2 tdbbs"><span>2</span></td>
                                        <td class="rank1 yilou rank2 tdbbs"><span>7</span></td>
                                        <td class="rank1 yilou rank2 tdbbs"><span>4</span></td>
                                        <td title="0" class="hot rank2 tdbbs"><span name="hotSpan" class="zoushiqiu gB"
                                                                                    style="background: rgb(31, 166, 232);">6</span>
                                        </td>
                                        <td class="rank1 yilou rank2 tdbbs"><span>15</span></td>
                                        <td class="rank1 yilou rank2 tdbbs"><span>3</span></td>
                                        <td class="rank1 yilou rank2 tdbbs"><span>11</span></td>
                                        <td class="rank1 yilou rank3 tdbbs"><span>6</span></td>
                                        <td class="rank1 yilou rank3 tdbbs"><span>5</span></td>
                                        <td class="rank1 yilou rank3 tdbbs"><span>14</span></td>
                                        <td class="rank1 yilou rank3 tdbbs"><span>7</span></td>
                                        <td class="rank1 yilou rank3 tdbbs"><span>2</span></td>
                                        <td class="rank1 yilou rank3 tdbbs"><span>8</span></td>
                                        <td class="rank1 yilou rank3 tdbbs"><span>1</span></td>
                                        <td class="rank1 yilou rank3 tdbbs"><span>9</span></td>
                                        <td title="0" class="hot rank3 tdbbs"><span name="hotSpan" class="zoushiqiu oB"
                                                                                    style="background: rgb(8, 191, 2);">8</span>
                                        </td>
                                        <td class="rank1 yilou rank3 tdbbs"><span>4</span></td>
                                        <td class="rank1 yilou rank4 tdbbs"><span>3</span></td>
                                        <td class="rank1 yilou rank4 tdbbs"><span>11</span></td>
                                        <td class="rank1 yilou rank4 tdbbs"><span>2</span></td>
                                        <td title="0" class="hot rank4 tdbbs"><span name="hotSpan" class="zoushiqiu gqB"
                                                                                    style="background: rgb(133, 133, 251);">3</span>
                                        </td>
                                        <td class="rank1 yilou rank4 tdbbs"><span>4</span></td>
                                        <td class="rank1 yilou rank4 tdbbs"><span>24</span></td>
                                        <td class="rank1 yilou rank4 tdbbs"><span>1</span></td>
                                        <td class="rank1 yilou rank4 tdbbs"><span>6</span></td>
                                        <td class="rank1 yilou rank4 tdbbs"><span>7</span></td>
                                        <td class="rank1 yilou rank4 tdbbs"><span>10</span></td>
                                        <td class="rank1 yilou rank5 tdbbs"><span>1</span></td>
                                        <td class="rank1 yilou rank5 tdbbs"><span>3</span></td>
                                        <td class="rank1 yilou rank5 tdbbs"><span>36</span></td>
                                        <td class="rank1 yilou rank5 tdbbs"><span>6</span></td>
                                        <td title="0" class="hot rank5 tdbbs"><span name="hotSpan" class="zoushiqiu grB"
                                                                                    style="background: rgb(70, 189, 149);">4</span>
                                        </td>
                                        <td class="rank1 yilou rank5 tdbbs"><span>4</span></td>
                                        <td class="rank1 yilou rank5 tdbbs"><span>12</span></td>
                                        <td class="rank1 yilou rank5 tdbbs"><span>30</span></td>
                                        <td class="rank1 yilou rank5 tdbbs"><span>8</span></td>
                                        <td class="rank1 yilou rank5 tdbbs"><span>7</span></td>
                                    </tr>
                                    <tr class="yiloutr" height="32">
                                        <td class="leftth">2020110411</td>
                                        <td><span class="kaijnum">8</span><span class="kaijnum">1</span><span
                                            class="kaijnum">6</span><span class="kaijnum">6</span><span class="kaijnum">0</span>
                                        </td>
                                        <td class="rank1 yilou"><span>11</span></td>
                                        <td class="rank1 yilou"><span>23</span></td>
                                        <td class="rank1 yilou"><span>8</span></td>
                                        <td class="rank1 yilou"><span>6</span></td>
                                        <td class="rank1 yilou"><span>13</span></td>
                                        <td class="rank1 yilou"><span>5</span></td>
                                        <td class="rank1 yilou"><span>2</span></td>
                                        <td class="rank1 yilou"><span>1</span></td>
                                        <td title="0" class="hot rank1"><span name="hotSpan" class="zoushiqiu bBK"
                                                                              style="background: rgb(251, 167, 94);">8</span>
                                        </td>
                                        <td class="rank1 yilou"><span>14</span></td>
                                        <td class="rank1 yilou rank2"><span>47</span></td>
                                        <td title="0" class="hot rank2"><span name="hotSpan" class="zoushiqiu gB"
                                                                              style="background: rgb(31, 166, 232);">1</span>
                                        </td>
                                        <td class="rank1 yilou rank2"><span>5</span></td>
                                        <td class="rank1 yilou rank2"><span>1</span></td>
                                        <td class="rank1 yilou rank2"><span>6</span></td>
                                        <td class="rank1 yilou rank2"><span>3</span></td>
                                        <td class="rank1 yilou rank2"><span>15</span></td>
                                        <td class="rank1 yilou rank2"><span>14</span></td>
                                        <td class="rank1 yilou rank2"><span>2</span></td>
                                        <td class="rank1 yilou rank2"><span>10</span></td>
                                        <td class="rank1 yilou rank3"><span>5</span></td>
                                        <td class="rank1 yilou rank3"><span>4</span></td>
                                        <td class="rank1 yilou rank3"><span>13</span></td>
                                        <td class="rank1 yilou rank3"><span>6</span></td>
                                        <td class="rank1 yilou rank3"><span>1</span></td>
                                        <td class="rank1 yilou rank3"><span>7</span></td>
                                        <td title="0" class="hot rank3"><span name="hotSpan" class="zoushiqiu oB"
                                                                              style="background: rgb(8, 191, 2);">6</span>
                                        </td>
                                        <td class="rank1 yilou rank3"><span>8</span></td>
                                        <td class="rank1 yilou rank3"><span>2</span></td>
                                        <td class="rank1 yilou rank3"><span>3</span></td>
                                        <td class="rank1 yilou rank4"><span>2</span></td>
                                        <td class="rank1 yilou rank4"><span>10</span></td>
                                        <td class="rank1 yilou rank4"><span>1</span></td>
                                        <td class="rank1 yilou rank4"><span>4</span></td>
                                        <td class="rank1 yilou rank4"><span>3</span></td>
                                        <td class="rank1 yilou rank4"><span>23</span></td>
                                        <td title="0" class="hot rank4"><span name="hotSpan" class="zoushiqiu gqB"
                                                                              style="background: rgb(133, 133, 251);">6</span>
                                        </td>
                                        <td class="rank1 yilou rank4"><span>5</span></td>
                                        <td class="rank1 yilou rank4"><span>6</span></td>
                                        <td class="rank1 yilou rank4"><span>9</span></td>
                                        <td title="0" class="hot rank5"><span name="hotSpan" class="zoushiqiu grB"
                                                                              style="background: rgb(70, 189, 149);">0</span>
                                        </td>
                                        <td class="rank1 yilou rank5"><span>2</span></td>
                                        <td class="rank1 yilou rank5"><span>35</span></td>
                                        <td class="rank1 yilou rank5"><span>5</span></td>
                                        <td class="rank1 yilou rank5"><span>20</span></td>
                                        <td class="rank1 yilou rank5"><span>3</span></td>
                                        <td class="rank1 yilou rank5"><span>11</span></td>
                                        <td class="rank1 yilou rank5"><span>29</span></td>
                                        <td class="rank1 yilou rank5"><span>7</span></td>
                                        <td class="rank1 yilou rank5"><span>6</span></td>
                                    </tr>
                                    <tr class="yiloutr" height="32">
                                        <td class="leftth">2020110410</td>
                                        <td><span class="kaijnum">7</span><span class="kaijnum">3</span><span
                                            class="kaijnum">4</span><span class="kaijnum">2</span><span class="kaijnum">0</span>
                                        </td>
                                        <td class="rank1 yilou"><span>10</span></td>
                                        <td class="rank1 yilou"><span>22</span></td>
                                        <td class="rank1 yilou"><span>7</span></td>
                                        <td class="rank1 yilou"><span>5</span></td>
                                        <td class="rank1 yilou"><span>12</span></td>
                                        <td class="rank1 yilou"><span>4</span></td>
                                        <td class="rank1 yilou"><span>1</span></td>
                                        <td title="0" class="hot rank1"><span name="hotSpan" class="zoushiqiu bBK"
                                                                              style="background: rgb(251, 167, 94);">7</span>
                                        </td>
                                        <td class="rank1 yilou"><span>23</span></td>
                                        <td class="rank1 yilou"><span>13</span></td>
                                        <td class="rank1 yilou rank2"><span>46</span></td>
                                        <td class="rank1 yilou rank2"><span>3</span></td>
                                        <td class="rank1 yilou rank2"><span>4</span></td>
                                        <td title="0" class="hot rank2"><span name="hotSpan" class="zoushiqiu gB"
                                                                              style="background: rgb(31, 166, 232);">3</span>
                                        </td>
                                        <td class="rank1 yilou rank2"><span>5</span></td>
                                        <td class="rank1 yilou rank2"><span>2</span></td>
                                        <td class="rank1 yilou rank2"><span>14</span></td>
                                        <td class="rank1 yilou rank2"><span>13</span></td>
                                        <td class="rank1 yilou rank2"><span>1</span></td>
                                        <td class="rank1 yilou rank2"><span>9</span></td>
                                        <td class="rank1 yilou rank3"><span>4</span></td>
                                        <td class="rank1 yilou rank3"><span>3</span></td>
                                        <td class="rank1 yilou rank3"><span>12</span></td>
                                        <td class="rank1 yilou rank3"><span>5</span></td>
                                        <td title="0" class="hot rank3"><span name="hotSpan" class="zoushiqiu oB"
                                                                              style="background: rgb(8, 191, 2);">4</span>
                                        </td>
                                        <td class="rank1 yilou rank3"><span>6</span></td>
                                        <td class="rank1 yilou rank3"><span>8</span></td>
                                        <td class="rank1 yilou rank3"><span>7</span></td>
                                        <td class="rank1 yilou rank3"><span>1</span></td>
                                        <td class="rank1 yilou rank3"><span>2</span></td>
                                        <td class="rank1 yilou rank4"><span>1</span></td>
                                        <td class="rank1 yilou rank4"><span>9</span></td>
                                        <td title="0" class="hot rank4"><span name="hotSpan" class="zoushiqiu gqB"
                                                                              style="background: rgb(133, 133, 251);">2</span>
                                        </td>
                                        <td class="rank1 yilou rank4"><span>3</span></td>
                                        <td class="rank1 yilou rank4"><span>2</span></td>
                                        <td class="rank1 yilou rank4"><span>22</span></td>
                                        <td class="rank1 yilou rank4"><span>17</span></td>
                                        <td class="rank1 yilou rank4"><span>4</span></td>
                                        <td class="rank1 yilou rank4"><span>5</span></td>
                                        <td class="rank1 yilou rank4"><span>8</span></td>
                                        <td title="0" class="hot rank5"><span name="hotSpan" class="zoushiqiu grB"
                                                                              style="background: rgb(70, 189, 149);">0</span>
                                        </td>
                                        <td class="rank1 yilou rank5"><span>1</span></td>
                                        <td class="rank1 yilou rank5"><span>34</span></td>
                                        <td class="rank1 yilou rank5"><span>4</span></td>
                                        <td class="rank1 yilou rank5"><span>19</span></td>
                                        <td class="rank1 yilou rank5"><span>2</span></td>
                                        <td class="rank1 yilou rank5"><span>10</span></td>
                                        <td class="rank1 yilou rank5"><span>28</span></td>
                                        <td class="rank1 yilou rank5"><span>6</span></td>
                                        <td class="rank1 yilou rank5"><span>5</span></td>
                                    </tr>
                                    <tr class="yiloutr" height="32">
                                        <td class="leftth">2020110409</td>
                                        <td><span class="kaijnum">6</span><span class="kaijnum">8</span><span
                                            class="kaijnum">8</span><span class="kaijnum">0</span><span class="kaijnum">1</span>
                                        </td>
                                        <td class="rank1 yilou"><span>9</span></td>
                                        <td class="rank1 yilou"><span>21</span></td>
                                        <td class="rank1 yilou"><span>6</span></td>
                                        <td class="rank1 yilou"><span>4</span></td>
                                        <td class="rank1 yilou"><span>11</span></td>
                                        <td class="rank1 yilou"><span>3</span></td>
                                        <td title="0" class="hot rank1"><span name="hotSpan" class="zoushiqiu bBK"
                                                                              style="background: rgb(251, 167, 94);">6</span>
                                        </td>
                                        <td class="rank1 yilou"><span>1</span></td>
                                        <td class="rank1 yilou"><span>22</span></td>
                                        <td class="rank1 yilou"><span>12</span></td>
                                        <td class="rank1 yilou rank2"><span>45</span></td>
                                        <td class="rank1 yilou rank2"><span>2</span></td>
                                        <td class="rank1 yilou rank2"><span>3</span></td>
                                        <td class="rank1 yilou rank2"><span>5</span></td>
                                        <td class="rank1 yilou rank2"><span>4</span></td>
                                        <td class="rank1 yilou rank2"><span>1</span></td>
                                        <td class="rank1 yilou rank2"><span>13</span></td>
                                        <td class="rank1 yilou rank2"><span>12</span></td>
                                        <td title="0" class="hot rank2"><span name="hotSpan" class="zoushiqiu gB"
                                                                              style="background: rgb(31, 166, 232);">8</span>
                                        </td>
                                        <td class="rank1 yilou rank2"><span>8</span></td>
                                        <td class="rank1 yilou rank3"><span>3</span></td>
                                        <td class="rank1 yilou rank3"><span>2</span></td>
                                        <td class="rank1 yilou rank3"><span>11</span></td>
                                        <td class="rank1 yilou rank3"><span>4</span></td>
                                        <td class="rank1 yilou rank3"><span>29</span></td>
                                        <td class="rank1 yilou rank3"><span>5</span></td>
                                        <td class="rank1 yilou rank3"><span>7</span></td>
                                        <td class="rank1 yilou rank3"><span>6</span></td>
                                        <td title="0" class="hot rank3"><span name="hotSpan" class="zoushiqiu oB"
                                                                              style="background: rgb(8, 191, 2);">8</span>
                                        </td>
                                        <td class="rank1 yilou rank3"><span>1</span></td>
                                        <td title="0" class="hot rank4"><span name="hotSpan" class="zoushiqiu gqB"
                                                                              style="background: rgb(133, 133, 251);">0</span>
                                        </td>
                                        <td class="rank1 yilou rank4"><span>8</span></td>
                                        <td class="rank1 yilou rank4"><span>9</span></td>
                                        <td class="rank1 yilou rank4"><span>2</span></td>
                                        <td class="rank1 yilou rank4"><span>1</span></td>
                                        <td class="rank1 yilou rank4"><span>21</span></td>
                                        <td class="rank1 yilou rank4"><span>16</span></td>
                                        <td class="rank1 yilou rank4"><span>3</span></td>
                                        <td class="rank1 yilou rank4"><span>4</span></td>
                                        <td class="rank1 yilou rank4"><span>7</span></td>
                                        <td class="rank1 yilou rank5"><span>2</span></td>
                                        <td title="0" class="hot rank5"><span name="hotSpan" class="zoushiqiu grB"
                                                                              style="background: rgb(70, 189, 149);">1</span>
                                        </td>
                                        <td class="rank1 yilou rank5"><span>33</span></td>
                                        <td class="rank1 yilou rank5"><span>3</span></td>
                                        <td class="rank1 yilou rank5"><span>18</span></td>
                                        <td class="rank1 yilou rank5"><span>1</span></td>
                                        <td class="rank1 yilou rank5"><span>9</span></td>
                                        <td class="rank1 yilou rank5"><span>27</span></td>
                                        <td class="rank1 yilou rank5"><span>5</span></td>
                                        <td class="rank1 yilou rank5"><span>4</span></td>
                                    </tr>
                                    <tr class="yiloutr" height="32">
                                        <td class="leftth">2020110408</td>
                                        <td><span class="kaijnum">7</span><span class="kaijnum">5</span><span
                                            class="kaijnum">9</span><span class="kaijnum">4</span><span class="kaijnum">5</span>
                                        </td>
                                        <td class="rank1 yilou"><span>8</span></td>
                                        <td class="rank1 yilou"><span>20</span></td>
                                        <td class="rank1 yilou"><span>5</span></td>
                                        <td class="rank1 yilou"><span>3</span></td>
                                        <td class="rank1 yilou"><span>10</span></td>
                                        <td class="rank1 yilou"><span>2</span></td>
                                        <td class="rank1 yilou"><span>1</span></td>
                                        <td title="0" class="hot rank1"><span name="hotSpan" class="zoushiqiu bBK"
                                                                              style="background: rgb(251, 167, 94);">7</span>
                                        </td>
                                        <td class="rank1 yilou"><span>21</span></td>
                                        <td class="rank1 yilou"><span>11</span></td>
                                        <td class="rank1 yilou rank2"><span>44</span></td>
                                        <td class="rank1 yilou rank2"><span>1</span></td>
                                        <td class="rank1 yilou rank2"><span>2</span></td>
                                        <td class="rank1 yilou rank2"><span>4</span></td>
                                        <td class="rank1 yilou rank2"><span>3</span></td>
                                        <td title="0" class="hot rank2"><span name="hotSpan" class="zoushiqiu gB"
                                                                              style="background: rgb(31, 166, 232);">5</span>
                                        </td>
                                        <td class="rank1 yilou rank2"><span>12</span></td>
                                        <td class="rank1 yilou rank2"><span>11</span></td>
                                        <td class="rank1 yilou rank2"><span>9</span></td>
                                        <td class="rank1 yilou rank2"><span>7</span></td>
                                        <td class="rank1 yilou rank3"><span>2</span></td>
                                        <td class="rank1 yilou rank3"><span>1</span></td>
                                        <td class="rank1 yilou rank3"><span>10</span></td>
                                        <td class="rank1 yilou rank3"><span>3</span></td>
                                        <td class="rank1 yilou rank3"><span>28</span></td>
                                        <td class="rank1 yilou rank3"><span>4</span></td>
                                        <td class="rank1 yilou rank3"><span>6</span></td>
                                        <td class="rank1 yilou rank3"><span>5</span></td>
                                        <td class="rank1 yilou rank3"><span>16</span></td>
                                        <td title="0" class="hot rank3"><span name="hotSpan" class="zoushiqiu oB"
                                                                              style="background: rgb(8, 191, 2);">9</span>
                                        </td>
                                        <td class="rank1 yilou rank4"><span>12</span></td>
                                        <td class="rank1 yilou rank4"><span>7</span></td>
                                        <td class="rank1 yilou rank4"><span>8</span></td>
                                        <td class="rank1 yilou rank4"><span>1</span></td>
                                        <td title="0" class="hot rank4"><span name="hotSpan" class="zoushiqiu gqB"
                                                                              style="background: rgb(133, 133, 251);">4</span>
                                        </td>
                                        <td class="rank1 yilou rank4"><span>20</span></td>
                                        <td class="rank1 yilou rank4"><span>15</span></td>
                                        <td class="rank1 yilou rank4"><span>2</span></td>
                                        <td class="rank1 yilou rank4"><span>3</span></td>
                                        <td class="rank1 yilou rank4"><span>6</span></td>
                                        <td class="rank1 yilou rank5"><span>1</span></td>
                                        <td class="rank1 yilou rank5"><span>6</span></td>
                                        <td class="rank1 yilou rank5"><span>32</span></td>
                                        <td class="rank1 yilou rank5"><span>2</span></td>
                                        <td class="rank1 yilou rank5"><span>17</span></td>
                                        <td title="0" class="hot rank5"><span name="hotSpan" class="zoushiqiu grB"
                                                                              style="background: rgb(70, 189, 149);">5</span>
                                        </td>
                                        <td class="rank1 yilou rank5"><span>8</span></td>
                                        <td class="rank1 yilou rank5"><span>26</span></td>
                                        <td class="rank1 yilou rank5"><span>4</span></td>
                                        <td class="rank1 yilou rank5"><span>3</span></td>
                                    </tr>
                                    <tr class="yiloutr" height="32">
                                        <td class="leftth">2020110407</td>
                                        <td><span class="kaijnum">6</span><span class="kaijnum">1</span><span
                                            class="kaijnum">1</span><span class="kaijnum">3</span><span class="kaijnum">0</span>
                                        </td>
                                        <td class="rank1 yilou"><span>7</span></td>
                                        <td class="rank1 yilou"><span>19</span></td>
                                        <td class="rank1 yilou"><span>4</span></td>
                                        <td class="rank1 yilou"><span>2</span></td>
                                        <td class="rank1 yilou"><span>9</span></td>
                                        <td class="rank1 yilou"><span>1</span></td>
                                        <td title="0" class="hot rank1"><span name="hotSpan" class="zoushiqiu bBK"
                                                                              style="background: rgb(251, 167, 94);">6</span>
                                        </td>
                                        <td class="rank1 yilou"><span>5</span></td>
                                        <td class="rank1 yilou"><span>20</span></td>
                                        <td class="rank1 yilou"><span>10</span></td>
                                        <td class="rank1 yilou rank2"><span>43</span></td>
                                        <td title="0" class="hot rank2"><span name="hotSpan" class="zoushiqiu gB"
                                                                              style="background: rgb(31, 166, 232);">1</span>
                                        </td>
                                        <td class="rank1 yilou rank2"><span>1</span></td>
                                        <td class="rank1 yilou rank2"><span>3</span></td>
                                        <td class="rank1 yilou rank2"><span>2</span></td>
                                        <td class="rank1 yilou rank2"><span>4</span></td>
                                        <td class="rank1 yilou rank2"><span>11</span></td>
                                        <td class="rank1 yilou rank2"><span>10</span></td>
                                        <td class="rank1 yilou rank2"><span>8</span></td>
                                        <td class="rank1 yilou rank2"><span>6</span></td>
                                        <td class="rank1 yilou rank3"><span>1</span></td>
                                        <td title="0" class="hot rank3"><span name="hotSpan" class="zoushiqiu oB"
                                                                              style="background: rgb(8, 191, 2);">1</span>
                                        </td>
                                        <td class="rank1 yilou rank3"><span>9</span></td>
                                        <td class="rank1 yilou rank3"><span>2</span></td>
                                        <td class="rank1 yilou rank3"><span>27</span></td>
                                        <td class="rank1 yilou rank3"><span>3</span></td>
                                        <td class="rank1 yilou rank3"><span>5</span></td>
                                        <td class="rank1 yilou rank3"><span>4</span></td>
                                        <td class="rank1 yilou rank3"><span>15</span></td>
                                        <td class="rank1 yilou rank3"><span>8</span></td>
                                        <td class="rank1 yilou rank4"><span>11</span></td>
                                        <td class="rank1 yilou rank4"><span>6</span></td>
                                        <td class="rank1 yilou rank4"><span>7</span></td>
                                        <td title="0" class="hot rank4"><span name="hotSpan" class="zoushiqiu gqB"
                                                                              style="background: rgb(133, 133, 251);">3</span>
                                        </td>
                                        <td class="rank1 yilou rank4"><span>18</span></td>
                                        <td class="rank1 yilou rank4"><span>19</span></td>
                                        <td class="rank1 yilou rank4"><span>14</span></td>
                                        <td class="rank1 yilou rank4"><span>1</span></td>
                                        <td class="rank1 yilou rank4"><span>2</span></td>
                                        <td class="rank1 yilou rank4"><span>5</span></td>
                                        <td title="0" class="hot rank5"><span name="hotSpan" class="zoushiqiu grB"
                                                                              style="background: rgb(70, 189, 149);">0</span>
                                        </td>
                                        <td class="rank1 yilou rank5"><span>5</span></td>
                                        <td class="rank1 yilou rank5"><span>31</span></td>
                                        <td class="rank1 yilou rank5"><span>1</span></td>
                                        <td class="rank1 yilou rank5"><span>16</span></td>
                                        <td class="rank1 yilou rank5"><span>10</span></td>
                                        <td class="rank1 yilou rank5"><span>7</span></td>
                                        <td class="rank1 yilou rank5"><span>25</span></td>
                                        <td class="rank1 yilou rank5"><span>3</span></td>
                                        <td class="rank1 yilou rank5"><span>2</span></td>
                                    </tr>
                                    <tr class="yiloutr" height="32">
                                        <td class="leftth">2020110406</td>
                                        <td><span class="kaijnum">5</span><span class="kaijnum">2</span><span
                                            class="kaijnum">0</span><span class="kaijnum">7</span><span class="kaijnum">3</span>
                                        </td>
                                        <td class="rank1 yilou"><span>6</span></td>
                                        <td class="rank1 yilou"><span>18</span></td>
                                        <td class="rank1 yilou"><span>3</span></td>
                                        <td class="rank1 yilou"><span>1</span></td>
                                        <td class="rank1 yilou"><span>8</span></td>
                                        <td title="0" class="hot rank1"><span name="hotSpan" class="zoushiqiu bBK"
                                                                              style="background: rgb(251, 167, 94);">5</span>
                                        </td>
                                        <td class="rank1 yilou"><span>11</span></td>
                                        <td class="rank1 yilou"><span>4</span></td>
                                        <td class="rank1 yilou"><span>19</span></td>
                                        <td class="rank1 yilou"><span>9</span></td>
                                        <td class="rank1 yilou rank2"><span>42</span></td>
                                        <td class="rank1 yilou rank2"><span>6</span></td>
                                        <td title="0" class="hot rank2"><span name="hotSpan" class="zoushiqiu gB"
                                                                              style="background: rgb(31, 166, 232);">2</span>
                                        </td>
                                        <td class="rank1 yilou rank2"><span>2</span></td>
                                        <td class="rank1 yilou rank2"><span>1</span></td>
                                        <td class="rank1 yilou rank2"><span>3</span></td>
                                        <td class="rank1 yilou rank2"><span>10</span></td>
                                        <td class="rank1 yilou rank2"><span>9</span></td>
                                        <td class="rank1 yilou rank2"><span>7</span></td>
                                        <td class="rank1 yilou rank2"><span>5</span></td>
                                        <td title="0" class="hot rank3"><span name="hotSpan" class="zoushiqiu oB"
                                                                              style="background: rgb(8, 191, 2);">0</span>
                                        </td>
                                        <td class="rank1 yilou rank3"><span>17</span></td>
                                        <td class="rank1 yilou rank3"><span>8</span></td>
                                        <td class="rank1 yilou rank3"><span>1</span></td>
                                        <td class="rank1 yilou rank3"><span>26</span></td>
                                        <td class="rank1 yilou rank3"><span>2</span></td>
                                        <td class="rank1 yilou rank3"><span>4</span></td>
                                        <td class="rank1 yilou rank3"><span>3</span></td>
                                        <td class="rank1 yilou rank3"><span>14</span></td>
                                        <td class="rank1 yilou rank3"><span>7</span></td>
                                        <td class="rank1 yilou rank4"><span>10</span></td>
                                        <td class="rank1 yilou rank4"><span>5</span></td>
                                        <td class="rank1 yilou rank4"><span>6</span></td>
                                        <td class="rank1 yilou rank4"><span>11</span></td>
                                        <td class="rank1 yilou rank4"><span>17</span></td>
                                        <td class="rank1 yilou rank4"><span>18</span></td>
                                        <td class="rank1 yilou rank4"><span>13</span></td>
                                        <td title="0" class="hot rank4"><span name="hotSpan" class="zoushiqiu gqB"
                                                                              style="background: rgb(133, 133, 251);">7</span>
                                        </td>
                                        <td class="rank1 yilou rank4"><span>1</span></td>
                                        <td class="rank1 yilou rank4"><span>4</span></td>
                                        <td class="rank1 yilou rank5"><span>3</span></td>
                                        <td class="rank1 yilou rank5"><span>4</span></td>
                                        <td class="rank1 yilou rank5"><span>30</span></td>
                                        <td title="0" class="hot rank5"><span name="hotSpan" class="zoushiqiu grB"
                                                                              style="background: rgb(70, 189, 149);">3</span>
                                        </td>
                                        <td class="rank1 yilou rank5"><span>15</span></td>
                                        <td class="rank1 yilou rank5"><span>9</span></td>
                                        <td class="rank1 yilou rank5"><span>6</span></td>
                                        <td class="rank1 yilou rank5"><span>24</span></td>
                                        <td class="rank1 yilou rank5"><span>2</span></td>
                                        <td class="rank1 yilou rank5"><span>1</span></td>
                                    </tr>
                                    <tr class="yiloutr" height="32">
                                        <td class="leftth">2020110405</td>
                                        <td><span class="kaijnum">3</span><span class="kaijnum">4</span><span
                                            class="kaijnum">3</span><span class="kaijnum">8</span><span class="kaijnum">9</span>
                                        </td>
                                        <td class="rank1 yilou"><span>5</span></td>
                                        <td class="rank1 yilou"><span>17</span></td>
                                        <td class="rank1 yilou"><span>2</span></td>
                                        <td title="0" class="hot rank1"><span name="hotSpan" class="zoushiqiu bBK"
                                                                              style="background: rgb(251, 167, 94);">3</span>
                                        </td>
                                        <td class="rank1 yilou"><span>7</span></td>
                                        <td class="rank1 yilou"><span>28</span></td>
                                        <td class="rank1 yilou"><span>10</span></td>
                                        <td class="rank1 yilou"><span>3</span></td>
                                        <td class="rank1 yilou"><span>18</span></td>
                                        <td class="rank1 yilou"><span>8</span></td>
                                        <td class="rank1 yilou rank2"><span>41</span></td>
                                        <td class="rank1 yilou rank2"><span>5</span></td>
                                        <td class="rank1 yilou rank2"><span>12</span></td>
                                        <td class="rank1 yilou rank2"><span>1</span></td>
                                        <td title="0" class="hot rank2"><span name="hotSpan" class="zoushiqiu gB"
                                                                              style="background: rgb(31, 166, 232);">4</span>
                                        </td>
                                        <td class="rank1 yilou rank2"><span>2</span></td>
                                        <td class="rank1 yilou rank2"><span>9</span></td>
                                        <td class="rank1 yilou rank2"><span>8</span></td>
                                        <td class="rank1 yilou rank2"><span>6</span></td>
                                        <td class="rank1 yilou rank2"><span>4</span></td>
                                        <td class="rank1 yilou rank3"><span>15</span></td>
                                        <td class="rank1 yilou rank3"><span>16</span></td>
                                        <td class="rank1 yilou rank3"><span>7</span></td>
                                        <td title="0" class="hot rank3"><span name="hotSpan" class="zoushiqiu oB"
                                                                              style="background: rgb(8, 191, 2);">3</span>
                                        </td>
                                        <td class="rank1 yilou rank3"><span>25</span></td>
                                        <td class="rank1 yilou rank3"><span>1</span></td>
                                        <td class="rank1 yilou rank3"><span>3</span></td>
                                        <td class="rank1 yilou rank3"><span>2</span></td>
                                        <td class="rank1 yilou rank3"><span>13</span></td>
                                        <td class="rank1 yilou rank3"><span>6</span></td>
                                        <td class="rank1 yilou rank4"><span>9</span></td>
                                        <td class="rank1 yilou rank4"><span>4</span></td>
                                        <td class="rank1 yilou rank4"><span>5</span></td>
                                        <td class="rank1 yilou rank4"><span>10</span></td>
                                        <td class="rank1 yilou rank4"><span>16</span></td>
                                        <td class="rank1 yilou rank4"><span>17</span></td>
                                        <td class="rank1 yilou rank4"><span>12</span></td>
                                        <td class="rank1 yilou rank4"><span>1</span></td>
                                        <td title="0" class="hot rank4"><span name="hotSpan" class="zoushiqiu gqB"
                                                                              style="background: rgb(133, 133, 251);">8</span>
                                        </td>
                                        <td class="rank1 yilou rank4"><span>3</span></td>
                                        <td class="rank1 yilou rank5"><span>2</span></td>
                                        <td class="rank1 yilou rank5"><span>3</span></td>
                                        <td class="rank1 yilou rank5"><span>29</span></td>
                                        <td class="rank1 yilou rank5"><span>25</span></td>
                                        <td class="rank1 yilou rank5"><span>14</span></td>
                                        <td class="rank1 yilou rank5"><span>8</span></td>
                                        <td class="rank1 yilou rank5"><span>5</span></td>
                                        <td class="rank1 yilou rank5"><span>23</span></td>
                                        <td class="rank1 yilou rank5"><span>1</span></td>
                                        <td title="0" class="hot rank5"><span name="hotSpan" class="zoushiqiu grB"
                                                                              style="background: rgb(70, 189, 149);">9</span>
                                        </td>
                                    </tr>
                                    <tr class="first_th">
                                        <th width="276" rowspan="2" colspan="2" class="left_border left_b">数据统计</th>
                                        <th colspan="10">第一球</th>
                                        <th colspan="10">第二球</th>
                                        <th colspan="10">第三球</th>
                                        <th colspan="10">第四球</th>
                                        <th colspan="10">第五球</th>
                                        <!--<th colspan="10">第六名</th>
                                        <th colspan="10">第七名</th>
                                        <th colspan="10">第八名</th>
                                        <th colspan="10">第九名</th>
                                        <th colspan="10">第十名</th>-->
                                    </tr>
                                    <tr class="second_th">
                                        <th width="30" class="nfw clthbr">0</th>
                                        <th width="30" class="nfw clthbr">1</th>
                                        <th width="30" class="nfw">2</th>
                                        <th width="30" class="nfw">3</th>
                                        <th width="30" class="nfw">4</th>
                                        <th width="30" class="nfw">5</th>
                                        <th width="30" class="nfw">6</th>
                                        <th width="30" class="nfw">7</th>
                                        <th width="30" class="nfw">8</th>
                                        <th width="30" class="nfw">9</th>
                                        <th width="30" class="nfw" id="thbr">0</th>
                                        <th width="30" class="nfw">1</th>
                                        <th width="30" class="nfw">2</th>
                                        <th width="30" class="nfw">3</th>
                                        <th width="30" class="nfw">4</th>
                                        <th width="30" class="nfw">5</th>
                                        <th width="30" class="nfw">6</th>
                                        <th width="30" class="nfw">7</th>
                                        <th width="30" class="nfw">8</th>
                                        <th width="30" class="nfw">9</th>
                                        <th width="30" class="nfw" id="thbr">0</th>
                                        <th width="30" class="nfw">1</th>
                                        <th width="30" class="nfw">2</th>
                                        <th width="30" class="nfw">3</th>
                                        <th width="30" class="nfw">4</th>
                                        <th width="30" class="nfw">5</th>
                                        <th width="30" class="nfw">6</th>
                                        <th width="30" class="nfw">7</th>
                                        <th width="30" class="nfw">8</th>
                                        <th width="30" class="nfw">9</th>
                                        <th width="30" class="nfw">0</th>
                                        <th width="30" class="nfw">1</th>
                                        <th width="30" class="nfw">2</th>
                                        <th width="30" class="nfw">3</th>
                                        <th width="30" class="nfw">4</th>
                                        <th width="30" class="nfw">5</th>
                                        <th width="30" class="nfw">6</th>
                                        <th width="30" class="nfw">7</th>
                                        <th width="30" class="nfw">8</th>
                                        <th width="30" class="nfw">9</th>
                                        <th width="30" class="nfw">0</th>
                                        <th width="30" class="nfw">1</th>
                                        <th width="30" class="nfw">2</th>
                                        <th width="30" class="nfw">3</th>
                                        <th width="30" class="nfw">
                                            <p>4</p>
                                        </th>
                                        <th width="30" class="nfw">5</th>
                                        <th width="30" class="nfw">6</th>
                                        <th width="30" class="nfw">7</th>
                                        <th width="30" class="nfw">8</th>
                                        <th width="30" class="nfw">9</th>
                                        <!--<th width="30" class="nfw">1</th>
                                        <th width="30" class="nfw">2</th>
                                        <th width="30" class="nfw">3</th>
                                        <th width="30" class="nfw">4</th>
                                        <th width="30" class="nfw">5</th>
                                        <th width="30" class="nfw">6</th>
                                        <th width="30" class="nfw">7</th>
                                        <th width="30" class="nfw">8</th>
                                        <th width="30" class="nfw">9</th>
                                        <th width="30" class="nfw">10</th>
                                        <th width="30" class="nfw">1</th>
                                        <th width="30" class="nfw">2</th>
                                        <th width="30" class="nfw">3</th>
                                        <th width="30" class="nfw">4</th>
                                        <th width="30" class="nfw">5</th>
                                        <th width="30" class="nfw">6</th>
                                        <th width="30" class="nfw">7</th>
                                        <th width="30" class="nfw">8</th>
                                        <th width="30" class="nfw">9</th>
                                        <th width="30" class="nfw">10</th>-->
                                        <!--<th width="30" class="nfw">1</th>
                                        <th width="30" class="nfw">2</th>
                                        <th width="30" class="nfw">3</th>
                                        <th width="30" class="nfw">4</th>
                                        <th width="30" class="nfw">5</th>
                                        <th width="30" class="nfw">6</th>
                                        <th width="30" class="nfw">7</th>
                                        <th width="30" class="nfw">8</th>
                                        <th width="30" class="nfw">9</th>
                                        <th width="30" class="nfw">10</th>
                                        <th width="30" class="nfw">1</th>
                                        <th width="30" class="nfw">2</th>
                                        <th width="30" class="nfw">3</th>
                                        <th width="30" class="nfw">4</th>
                                        <th width="30" class="nfw">5</th>
                                        <th width="30" class="nfw">6</th>
                                        <th width="30" class="nfw">7</th>
                                        <th width="30" class="nfw">8</th>
                                        <th width="30" class="nfw">9</th>
                                        <th width="30" class="nfw">10</th>
                                        <th width="30" class="nfw">1</th>
                                        <th width="30" class="nfw">2</th>
                                        <th width="30" class="nfw">3</th>
                                        <th width="30" class="nfw">4</th>
                                        <th width="30" class="nfw">5</th>
                                        <th width="30" class="nfw">6</th>
                                        <th width="30" class="nfw">7</th>
                                        <th width="30" class="nfw">8</th>
                                        <th width="30" class="nfw">9</th>
                                        <th width="30" class="nfw">10</th>-->
                                    </tr>
                                    <tr>
                                        <td class="font14" colspan="2">出现次数</td>
                                        <td class="font14">3</td>
                                        <td class="font14">2</td>
                                        <td class="font14">3</td>
                                        <td class="font14">2</td>
                                        <td class="font14">1</td>
                                        <td class="font14">4</td>
                                        <td class="font14">3</td>
                                        <td class="font14">3</td>
                                        <td class="font14">5</td>
                                        <td class="font14">4</td>
                                        <td class="font14">0</td>
                                        <td class="font14">4</td>
                                        <td class="font14">3</td>
                                        <td class="font14">4</td>
                                        <td class="font14">3</td>
                                        <td class="font14">3</td>
                                        <td class="font14">3</td>
                                        <td class="font14">2</td>
                                        <td class="font14">6</td>
                                        <td class="font14">2</td>
                                        <td class="font14">4</td>
                                        <td class="font14">5</td>
                                        <td class="font14">1</td>
                                        <td class="font14">3</td>
                                        <td class="font14">3</td>
                                        <td class="font14">1</td>
                                        <td class="font14">2</td>
                                        <td class="font14">3</td>
                                        <td class="font14">4</td>
                                        <td class="font14">4</td>
                                        <td class="font14">3</td>
                                        <td class="font14">1</td>
                                        <td class="font14">3</td>
                                        <td class="font14">2</td>
                                        <td class="font14">4</td>
                                        <td class="font14">4</td>
                                        <td class="font14">5</td>
                                        <td class="font14">3</td>
                                        <td class="font14">3</td>
                                        <td class="font14">2</td>
                                        <td class="font14">6</td>
                                        <td class="font14">4</td>
                                        <td class="font14">1</td>
                                        <td class="font14">3</td>
                                        <td class="font14">2</td>
                                        <td class="font14">1</td>
                                        <td class="font14">2</td>
                                        <td class="font14">3</td>
                                        <td class="font14">2</td>
                                        <td class="font14">6</td>
                                    </tr>
                                    <tr>
                                        <td class="font14" colspan="2">平均遗漏</td>
                                        <td class="font14">7</td>
                                        <td class="font14">10</td>
                                        <td class="font14">7</td>
                                        <td class="font14">10</td>
                                        <td class="font14">15</td>
                                        <td class="font14">6</td>
                                        <td class="font14">7</td>
                                        <td class="font14">7</td>
                                        <td class="font14">5</td>
                                        <td class="font14">6</td>
                                        <td class="font14">30</td>
                                        <td class="font14">6</td>
                                        <td class="font14">7</td>
                                        <td class="font14">6</td>
                                        <td class="font14">7</td>
                                        <td class="font14">7</td>
                                        <td class="font14">7</td>
                                        <td class="font14">10</td>
                                        <td class="font14">4</td>
                                        <td class="font14">10</td>
                                        <td class="font14">6</td>
                                        <td class="font14">5</td>
                                        <td class="font14">15</td>
                                        <td class="font14">7</td>
                                        <td class="font14">7</td>
                                        <td class="font14">15</td>
                                        <td class="font14">10</td>
                                        <td class="font14">7</td>
                                        <td class="font14">6</td>
                                        <td class="font14">6</td>
                                        <td class="font14">7</td>
                                        <td class="font14">15</td>
                                        <td class="font14">7</td>
                                        <td class="font14">10</td>
                                        <td class="font14">6</td>
                                        <td class="font14">6</td>
                                        <td class="font14">5</td>
                                        <td class="font14">7</td>
                                        <td class="font14">7</td>
                                        <td class="font14">10</td>
                                        <td class="font14">4</td>
                                        <td class="font14">6</td>
                                        <td class="font14">15</td>
                                        <td class="font14">7</td>
                                        <td class="font14">10</td>
                                        <td class="font14">15</td>
                                        <td class="font14">10</td>
                                        <td class="font14">7</td>
                                        <td class="font14">10</td>
                                        <td class="font14">4</td>
                                    </tr>
                                    <tr>
                                        <td class="font14" colspan="2">最大遗漏</td>
                                        <td class="font14">18</td>
                                        <td class="font14">33</td>
                                        <td class="font14">14</td>
                                        <td class="font14">17</td>
                                        <td class="font14">20</td>
                                        <td class="font14">28</td>
                                        <td class="font14">18</td>
                                        <td class="font14">21</td>
                                        <td class="font14">23</td>
                                        <td class="font14">22</td>
                                        <td class="font14">70</td>
                                        <td class="font14">16</td>
                                        <td class="font14">18</td>
                                        <td class="font14">11</td>
                                        <td class="font14">15</td>
                                        <td class="font14">11</td>
                                        <td class="font14">15</td>
                                        <td class="font14">27</td>
                                        <td class="font14">9</td>
                                        <td class="font14">17</td>
                                        <td class="font14">15</td>
                                        <td class="font14">17</td>
                                        <td class="font14">18</td>
                                        <td class="font14">15</td>
                                        <td class="font14">29</td>
                                        <td class="font14">18</td>
                                        <td class="font14">15</td>
                                        <td class="font14">15</td>
                                        <td class="font14">17</td>
                                        <td class="font14">8</td>
                                        <td class="font14">12</td>
                                        <td class="font14">25</td>
                                        <td class="font14">15</td>
                                        <td class="font14">22</td>
                                        <td class="font14">18</td>
                                        <td class="font14">29</td>
                                        <td class="font14">17</td>
                                        <td class="font14">14</td>
                                        <td class="font14">15</td>
                                        <td class="font14">12</td>
                                        <td class="font14">7</td>
                                        <td class="font14">10</td>
                                        <td class="font14">52</td>
                                        <td class="font14">25</td>
                                        <td class="font14">20</td>
                                        <td class="font14">26</td>
                                        <td class="font14">14</td>
                                        <td class="font14">31</td>
                                        <td class="font14">15</td>
                                        <td class="font14">9</td>
                                    </tr>
                                    <tr>
                                        <td class="font14" colspan="2">最大连出</td>
                                        <td class="font14">1</td>
                                        <td class="font14">1</td>
                                        <td class="font14">1</td>
                                        <td class="font14">2</td>
                                        <td class="font14">1</td>
                                        <td class="font14">2</td>
                                        <td class="font14">1</td>
                                        <td class="font14">1</td>
                                        <td class="font14">2</td>
                                        <td class="font14">2</td>
                                        <td class="font14">0</td>
                                        <td class="font14">1</td>
                                        <td class="font14">1</td>
                                        <td class="font14">1</td>
                                        <td class="font14">1</td>
                                        <td class="font14">1</td>
                                        <td class="font14">1</td>
                                        <td class="font14">1</td>
                                        <td class="font14">1</td>
                                        <td class="font14">1</td>
                                        <td class="font14">2</td>
                                        <td class="font14">2</td>
                                        <td class="font14">1</td>
                                        <td class="font14">1</td>
                                        <td class="font14">1</td>
                                        <td class="font14">1</td>
                                        <td class="font14">1</td>
                                        <td class="font14">1</td>
                                        <td class="font14">1</td>
                                        <td class="font14">1</td>
                                        <td class="font14">1</td>
                                        <td class="font14">1</td>
                                        <td class="font14">1</td>
                                        <td class="font14">1</td>
                                        <td class="font14">1</td>
                                        <td class="font14">2</td>
                                        <td class="font14">2</td>
                                        <td class="font14">1</td>
                                        <td class="font14">1</td>
                                        <td class="font14">1</td>
                                        <td class="font14">2</td>
                                        <td class="font14">1</td>
                                        <td class="font14">1</td>
                                        <td class="font14">1</td>
                                        <td class="font14">1</td>
                                        <td class="font14">1</td>
                                        <td class="font14">1</td>
                                        <td class="font14">2</td>
                                        <td class="font14">1</td>
                                        <td class="font14">2</td>
                                    </tr>
                                    </tbody>
                                </table>
                                <canvas width="124" height="30"
                                        style="position: absolute; z-index: 1; visibility: visible; top: 92px; left: 318px;"></canvas>
                                <canvas width="2" height="18"
                                        style="position: absolute; z-index: 1; visibility: visible; top: 132px; left: 450px;"></canvas>
                                <canvas width="70" height="28"
                                        style="position: absolute; z-index: 1; visibility: visible; top: 161px; left: 457px;"></canvas>
                                <canvas width="18" height="22"
                                        style="position: absolute; z-index: 1; visibility: visible; top: 198px; left: 539px;"></canvas>
                                <canvas width="2" height="18"
                                        style="position: absolute; z-index: 1; visibility: visible; top: 234px; left: 562px;"></canvas>
                                <canvas width="96" height="30"
                                        style="position: absolute; z-index: 1; visibility: visible; top: 262px; left: 458px;"></canvas>
                                <canvas width="70" height="28"
                                        style="position: absolute; z-index: 1; visibility: visible; top: 297px; left: 457px;"></canvas>
                                <canvas width="152" height="30"
                                        style="position: absolute; z-index: 1; visibility: visible; top: 330px; left: 374px;"></canvas>
                                <canvas width="18" height="22"
                                        style="position: absolute; z-index: 1; visibility: visible; top: 368px; left: 343px;"></canvas>
                                <canvas width="18" height="22"
                                        style="position: absolute; z-index: 1; visibility: visible; top: 402px; left: 315px;"></canvas>
                                <canvas width="42" height="26"
                                        style="position: absolute; z-index: 1; visibility: visible; top: 434px; left: 317px;"></canvas>
                                <canvas width="18" height="22"
                                        style="position: absolute; z-index: 1; visibility: visible; top: 470px; left: 343px;"></canvas>
                                <canvas width="208" height="32"
                                        style="position: absolute; z-index: 1; visibility: visible; top: 499px; left: 346px;"></canvas>
                                <canvas width="2" height="18"
                                        style="position: absolute; z-index: 1; visibility: visible; top: 540px; left: 562px;"></canvas>
                                <canvas width="236" height="32"
                                        style="position: absolute; z-index: 1; visibility: visible; top: 567px; left: 318px;"></canvas>
                                <canvas width="42" height="26"
                                        style="position: absolute; z-index: 1; visibility: visible; top: 604px; left: 317px;"></canvas>
                                <canvas width="18" height="22"
                                        style="position: absolute; z-index: 1; visibility: visible; top: 640px; left: 371px;"></canvas>
                                <canvas width="70" height="28"
                                        style="position: absolute; z-index: 1; visibility: visible; top: 671px; left: 401px;"></canvas>
                                <canvas width="42" height="26"
                                        style="position: absolute; z-index: 1; visibility: visible; top: 706px; left: 485px;"></canvas>
                                <canvas width="96" height="30"
                                        style="position: absolute; z-index: 1; visibility: visible; top: 738px; left: 430px;"></canvas>
                                <canvas width="70" height="28"
                                        style="position: absolute; z-index: 1; visibility: visible; top: 773px; left: 429px;"></canvas>
                                <canvas width="18" height="22"
                                        style="position: absolute; z-index: 1; visibility: visible; top: 810px; left: 511px;"></canvas>
                                <canvas width="2" height="18"
                                        style="position: absolute; z-index: 1; visibility: visible; top: 846px; left: 534px;"></canvas>
                                <canvas width="18" height="22"
                                        style="position: absolute; z-index: 1; visibility: visible; top: 878px; left: 511px;"></canvas>
                                <canvas width="18" height="22"
                                        style="position: absolute; z-index: 1; visibility: visible; top: 912px; left: 483px;"></canvas>
                                <canvas width="18" height="22"
                                        style="position: absolute; z-index: 1; visibility: visible; top: 946px; left: 483px;"></canvas>
                                <canvas width="18" height="22"
                                        style="position: absolute; z-index: 1; visibility: visible; top: 980px; left: 483px;"></canvas>
                                <canvas width="18" height="22"
                                        style="position: absolute; z-index: 1; visibility: visible; top: 1014px; left: 455px;"></canvas>
                                <canvas width="42" height="26"
                                        style="position: absolute; z-index: 1; visibility: visible; top: 1046px; left: 401px;"></canvas>
                                <canvas width="124" height="30"
                                        style="position: absolute; z-index: 1; visibility: visible; top: 92px; left: 682px;"></canvas>
                                <canvas width="124" height="30"
                                        style="position: absolute; z-index: 1; visibility: visible; top: 126px; left: 682px;"></canvas>
                                <canvas width="18" height="22"
                                        style="position: absolute; z-index: 1; visibility: visible; top: 164px; left: 791px;"></canvas>
                                <canvas width="42" height="26"
                                        style="position: absolute; z-index: 1; visibility: visible; top: 196px; left: 737px;"></canvas>
                                <canvas width="70" height="28"
                                        style="position: absolute; z-index: 1; visibility: visible; top: 229px; left: 737px;"></canvas>
                                <canvas width="96" height="30"
                                        style="position: absolute; z-index: 1; visibility: visible; top: 262px; left: 710px;"></canvas>
                                <canvas width="42" height="26"
                                        style="position: absolute; z-index: 1; visibility: visible; top: 298px; left: 709px;"></canvas>
                                <canvas width="70" height="28"
                                        style="position: absolute; z-index: 1; visibility: visible; top: 331px; left: 681px;"></canvas>
                                <canvas width="96" height="30"
                                        style="position: absolute; z-index: 1; visibility: visible; top: 364px; left: 682px;"></canvas>
                                <canvas width="18" height="22"
                                        style="position: absolute; z-index: 1; visibility: visible; top: 402px; left: 791px;"></canvas>
                                <canvas width="18" height="22"
                                        style="position: absolute; z-index: 1; visibility: visible; top: 436px; left: 819px;"></canvas>
                                <canvas width="152" height="30"
                                        style="position: absolute; z-index: 1; visibility: visible; top: 466px; left: 682px;"></canvas>
                                <canvas width="18" height="22"
                                        style="position: absolute; z-index: 1; visibility: visible; top: 504px; left: 679px;"></canvas>
                                <canvas width="18" height="22"
                                        style="position: absolute; z-index: 1; visibility: visible; top: 538px; left: 707px;"></canvas>
                                <canvas width="96" height="30"
                                        style="position: absolute; z-index: 1; visibility: visible; top: 568px; left: 738px;"></canvas>
                                <canvas width="208" height="32"
                                        style="position: absolute; z-index: 1; visibility: visible; top: 601px; left: 626px;"></canvas>
                                <canvas width="180" height="32"
                                        style="position: absolute; z-index: 1; visibility: visible; top: 635px; left: 626px;"></canvas>
                                <canvas width="152" height="30"
                                        style="position: absolute; z-index: 1; visibility: visible; top: 670px; left: 654px;"></canvas>
                                <canvas width="96" height="30"
                                        style="position: absolute; z-index: 1; visibility: visible; top: 704px; left: 654px;"></canvas>
                                <canvas width="124" height="30"
                                        style="position: absolute; z-index: 1; visibility: visible; top: 738px; left: 626px;"></canvas>
                                <canvas width="18" height="22"
                                        style="position: absolute; z-index: 1; visibility: visible; top: 776px; left: 623px;"></canvas>
                                <canvas width="96" height="30"
                                        style="position: absolute; z-index: 1; visibility: visible; top: 806px; left: 654px;"></canvas>
                                <canvas width="124" height="30"
                                        style="position: absolute; z-index: 1; visibility: visible; top: 840px; left: 626px;"></canvas>
                                <canvas width="42" height="26"
                                        style="position: absolute; z-index: 1; visibility: visible; top: 876px; left: 625px;"></canvas>
                                <canvas width="124" height="30"
                                        style="position: absolute; z-index: 1; visibility: visible; top: 908px; left: 682px;"></canvas>
                                <canvas width="70" height="28"
                                        style="position: absolute; z-index: 1; visibility: visible; top: 943px; left: 737px;"></canvas>
                                <canvas width="96" height="30"
                                        style="position: absolute; z-index: 1; visibility: visible; top: 976px; left: 626px;"></canvas>
                                <canvas width="18" height="22"
                                        style="position: absolute; z-index: 1; visibility: visible; top: 1014px; left: 623px;"></canvas>
                                <canvas width="42" height="26"
                                        style="position: absolute; z-index: 1; visibility: visible; top: 1046px; left: 653px;"></canvas>
                                <canvas width="180" height="32"
                                        style="position: absolute; z-index: 1; visibility: visible; top: 91px; left: 906px;"></canvas>
                                <canvas width="152" height="30"
                                        style="position: absolute; z-index: 1; visibility: visible; top: 126px; left: 906px;"></canvas>
                                <canvas width="152" height="30"
                                        style="position: absolute; z-index: 1; visibility: visible; top: 160px; left: 906px;"></canvas>
                                <canvas width="152" height="30"
                                        style="position: absolute; z-index: 1; visibility: visible; top: 194px; left: 906px;"></canvas>
                                <canvas width="70" height="28"
                                        style="position: absolute; z-index: 1; visibility: visible; top: 229px; left: 989px;"></canvas>
                                <canvas width="124" height="30"
                                        style="position: absolute; z-index: 1; visibility: visible; top: 262px; left: 990px;"></canvas>
                                <canvas width="70" height="28"
                                        style="position: absolute; z-index: 1; visibility: visible; top: 297px; left: 1045px;"></canvas>
                                <canvas width="152" height="30"
                                        style="position: absolute; z-index: 1; visibility: visible; top: 330px; left: 878px;"></canvas>
                                <canvas width="18" height="22"
                                        style="position: absolute; z-index: 1; visibility: visible; top: 368px; left: 875px;"></canvas>
                                <canvas width="2" height="18"
                                        style="position: absolute; z-index: 1; visibility: visible; top: 404px; left: 898px;"></canvas>
                                <canvas width="96" height="30"
                                        style="position: absolute; z-index: 1; visibility: visible; top: 432px; left: 906px;"></canvas>
                                <canvas width="96" height="30"
                                        style="position: absolute; z-index: 1; visibility: visible; top: 466px; left: 1018px;"></canvas>
                                <canvas width="236" height="32"
                                        style="position: absolute; z-index: 1; visibility: visible; top: 499px; left: 878px;"></canvas>
                                <canvas width="2" height="18"
                                        style="position: absolute; z-index: 1; visibility: visible; top: 540px; left: 870px;"></canvas>
                                <canvas width="70" height="28"
                                        style="position: absolute; z-index: 1; visibility: visible; top: 569px; left: 877px;"></canvas>
                                <canvas width="18" height="22"
                                        style="position: absolute; z-index: 1; visibility: visible; top: 606px; left: 959px;"></canvas>
                                <canvas width="42" height="26"
                                        style="position: absolute; z-index: 1; visibility: visible; top: 638px; left: 933px;"></canvas>
                                <canvas width="152" height="30"
                                        style="position: absolute; z-index: 1; visibility: visible; top: 670px; left: 934px;"></canvas>
                                <canvas width="18" height="22"
                                        style="position: absolute; z-index: 1; visibility: visible; top: 708px; left: 1099px;"></canvas>
                                <canvas width="42" height="26"
                                        style="position: absolute; z-index: 1; visibility: visible; top: 740px; left: 1073px;"></canvas>
                                <canvas width="96" height="30"
                                        style="position: absolute; z-index: 1; visibility: visible; top: 772px; left: 962px;"></canvas>
                                <canvas width="124" height="30"
                                        style="position: absolute; z-index: 1; visibility: visible; top: 806px; left: 962px;"></canvas>
                                <canvas width="42" height="26"
                                        style="position: absolute; z-index: 1; visibility: visible; top: 842px; left: 1045px;"></canvas>
                                <canvas width="42" height="26"
                                        style="position: absolute; z-index: 1; visibility: visible; top: 876px; left: 989px;"></canvas>
                                <canvas width="96" height="30"
                                        style="position: absolute; z-index: 1; visibility: visible; top: 908px; left: 990px;"></canvas>
                                <canvas width="18" height="22"
                                        style="position: absolute; z-index: 1; visibility: visible; top: 946px; left: 1099px;"></canvas>
                                <canvas width="208" height="32"
                                        style="position: absolute; z-index: 1; visibility: visible; top: 975px; left: 906px;"></canvas>
                                <canvas width="18" height="22"
                                        style="position: absolute; z-index: 1; visibility: visible; top: 1014px; left: 875px;"></canvas>
                                <canvas width="70" height="28"
                                        style="position: absolute; z-index: 1; visibility: visible; top: 1045px; left: 877px;"></canvas>
                                <canvas width="2" height="18"
                                        style="position: absolute; z-index: 1; visibility: visible; top: 98px; left: 1323px;"></canvas>
                                <canvas width="100" height="30"
                                        style="position: absolute; z-index: 1; visibility: visible; top: 126px; left: 1215px;"></canvas>
                                <canvas width="100" height="30"
                                        style="position: absolute; z-index: 1; visibility: visible; top: 160px; left: 1215px;"></canvas>
                                <canvas width="44" height="26"
                                        style="position: absolute; z-index: 1; visibility: visible; top: 196px; left: 1272px;"></canvas>
                                <canvas width="19" height="22"
                                        style="position: absolute; z-index: 1; visibility: visible; top: 232px; left: 1270px;"></canvas>
                                <canvas width="19" height="22"
                                        style="position: absolute; z-index: 1; visibility: visible; top: 266px; left: 1299px;"></canvas>
                                <canvas width="129" height="30"
                                        style="position: absolute; z-index: 1; visibility: visible; top: 296px; left: 1186px;"></canvas>
                                <canvas width="73" height="28"
                                        style="position: absolute; z-index: 1; visibility: visible; top: 331px; left: 1185px;"></canvas>
                                <canvas width="100" height="30"
                                        style="position: absolute; z-index: 1; visibility: visible; top: 364px; left: 1273px;"></canvas>
                                <canvas width="73" height="28"
                                        style="position: absolute; z-index: 1; visibility: visible; top: 399px; left: 1301px;"></canvas>
                                <canvas width="128" height="30"
                                        style="position: absolute; z-index: 1; visibility: visible; top: 432px; left: 1158px;"></canvas>
                                <canvas width="244" height="32"
                                        style="position: absolute; z-index: 1; visibility: visible; top: 465px; left: 1158px;"></canvas>
                                <canvas width="19" height="22"
                                        style="position: absolute; z-index: 1; visibility: visible; top: 504px; left: 1386px;"></canvas>
                                <canvas width="19" height="22"
                                        style="position: absolute; z-index: 1; visibility: visible; top: 538px; left: 1357px;"></canvas>
                                <canvas width="44" height="26"
                                        style="position: absolute; z-index: 1; visibility: visible; top: 570px; left: 1301px;"></canvas>
                                <canvas width="2" height="18"
                                        style="position: absolute; z-index: 1; visibility: visible; top: 608px; left: 1294px;"></canvas>
                                <canvas width="44" height="26"
                                        style="position: absolute; z-index: 1; visibility: visible; top: 638px; left: 1301px;"></canvas>
                                <canvas width="129" height="30"
                                        style="position: absolute; z-index: 1; visibility: visible; top: 670px; left: 1215px;"></canvas>
                                <canvas width="44" height="26"
                                        style="position: absolute; z-index: 1; visibility: visible; top: 706px; left: 1214px;"></canvas>
                                <canvas width="99" height="30"
                                        style="position: absolute; z-index: 1; visibility: visible; top: 738px; left: 1158px;"></canvas>
                                <canvas width="244" height="32"
                                        style="position: absolute; z-index: 1; visibility: visible; top: 771px; left: 1158px;"></canvas>
                                <canvas width="158" height="30"
                                        style="position: absolute; z-index: 1; visibility: visible; top: 806px; left: 1244px;"></canvas>
                                <canvas width="73" height="28"
                                        style="position: absolute; z-index: 1; visibility: visible; top: 841px; left: 1243px;"></canvas>
                                <canvas width="100" height="30"
                                        style="position: absolute; z-index: 1; visibility: visible; top: 874px; left: 1215px;"></canvas>
                                <canvas width="43" height="26"
                                        style="position: absolute; z-index: 1; visibility: visible; top: 910px; left: 1157px;"></canvas>
                                <canvas width="99" height="30"
                                        style="position: absolute; z-index: 1; visibility: visible; top: 942px; left: 1158px;"></canvas>
                                <canvas width="19" height="22"
                                        style="position: absolute; z-index: 1; visibility: visible; top: 980px; left: 1241px;"></canvas>
                                <canvas width="100" height="30"
                                        style="position: absolute; z-index: 1; visibility: visible; top: 1010px; left: 1244px;"></canvas>
                                <canvas width="19" height="22"
                                        style="position: absolute; z-index: 1; visibility: visible; top: 1048px; left: 1357px;"></canvas>
                                <canvas width="100" height="30"
                                        style="position: absolute; z-index: 1; visibility: visible; top: 92px; left: 1534px;"></canvas>
                                <canvas width="2" height="18"
                                        style="position: absolute; z-index: 1; visibility: visible; top: 132px; left: 1642px;"></canvas>
                                <canvas width="44" height="26"
                                        style="position: absolute; z-index: 1; visibility: visible; top: 162px; left: 1649px;"></canvas>
                                <canvas width="2" height="18"
                                        style="position: absolute; z-index: 1; visibility: visible; top: 200px; left: 1700px;"></canvas>
                                <canvas width="187" height="32"
                                        style="position: absolute; z-index: 1; visibility: visible; top: 227px; left: 1505px;"></canvas>
                                <canvas width="187" height="32"
                                        style="position: absolute; z-index: 1; visibility: visible; top: 261px; left: 1505px;"></canvas>
                                <canvas width="245" height="32"
                                        style="position: absolute; z-index: 1; visibility: visible; top: 295px; left: 1447px;"></canvas>
                                <canvas width="19" height="22"
                                        style="position: absolute; z-index: 1; visibility: visible; top: 334px; left: 1444px;"></canvas>
                                <canvas width="19" height="22"
                                        style="position: absolute; z-index: 1; visibility: visible; top: 368px; left: 1444px;"></canvas>
                                <canvas width="19" height="22"
                                        style="position: absolute; z-index: 1; visibility: visible; top: 402px; left: 1444px;"></canvas>
                                <canvas width="216" height="32"
                                        style="position: absolute; z-index: 1; visibility: visible; top: 431px; left: 1476px;"></canvas>
                                <canvas width="73" height="28"
                                        style="position: absolute; z-index: 1; visibility: visible; top: 467px; left: 1620px;"></canvas>
                                <canvas width="73" height="28"
                                        style="position: absolute; z-index: 1; visibility: visible; top: 501px; left: 1533px;"></canvas>
                                <canvas width="44" height="26"
                                        style="position: absolute; z-index: 1; visibility: visible; top: 536px; left: 1475px;"></canvas>
                                <canvas width="187" height="32"
                                        style="position: absolute; z-index: 1; visibility: visible; top: 567px; left: 1476px;"></canvas>
                                <canvas width="100" height="30"
                                        style="position: absolute; z-index: 1; visibility: visible; top: 602px; left: 1563px;"></canvas>
                                <canvas width="100" height="30"
                                        style="position: absolute; z-index: 1; visibility: visible; top: 636px; left: 1447px;"></canvas>
                                <canvas width="216" height="32"
                                        style="position: absolute; z-index: 1; visibility: visible; top: 669px; left: 1447px;"></canvas>
                                <canvas width="44" height="26"
                                        style="position: absolute; z-index: 1; visibility: visible; top: 706px; left: 1620px;"></canvas>
                                <canvas width="19" height="22"
                                        style="position: absolute; z-index: 1; visibility: visible; top: 742px; left: 1618px;"></canvas>
                                <canvas width="44" height="26"
                                        style="position: absolute; z-index: 1; visibility: visible; top: 774px; left: 1649px;"></canvas>
                                <canvas width="129" height="30"
                                        style="position: absolute; z-index: 1; visibility: visible; top: 806px; left: 1563px;"></canvas>
                                <canvas width="100" height="30"
                                        style="position: absolute; z-index: 1; visibility: visible; top: 840px; left: 1447px;"></canvas>
                                <canvas width="2" height="18"
                                        style="position: absolute; z-index: 1; visibility: visible; top: 880px; left: 1439px;"></canvas>
                                <canvas width="19" height="22"
                                        style="position: absolute; z-index: 1; visibility: visible; top: 912px; left: 1444px;"></canvas>
                                <canvas width="100" height="30"
                                        style="position: absolute; z-index: 1; visibility: visible; top: 942px; left: 1476px;"></canvas>
                                <canvas width="129" height="30"
                                        style="position: absolute; z-index: 1; visibility: visible; top: 976px; left: 1447px;"></canvas>
                                <canvas width="73" height="28"
                                        style="position: absolute; z-index: 1; visibility: visible; top: 1011px; left: 1446px;"></canvas>
                                <canvas width="158" height="30"
                                        style="position: absolute; z-index: 1; visibility: visible; top: 1044px; left: 1534px;"></canvas>
                            </div>
                            <div id="chartbottom" style="display: none;">
                                <table>
                                    <tbody>
                                    <tr class="first_th">
                                        <th width="276" rowspan="2" colspan="2" class="left_border left_b">数据统计</th>
                                        <th colspan="10">第一球</th>
                                        <th colspan="10">第二球</th>
                                        <th colspan="10">第三球</th>
                                        <th colspan="10">第四球</th>
                                        <th colspan="10">第五球</th>
                                        <!--<th colspan="10">第六名</th>
                                        <th colspan="10">第七名</th>
                                        <th colspan="10">第八名</th>
                                        <th colspan="10">第九名</th>
                                        <th colspan="10">第十名</th>-->
                                    </tr>
                                    <tr class="second_th">
                                        <th width="30" class="nfw clthbr">0</th>
                                        <th width="30" class="nfw clthbr">1</th>
                                        <th width="30" class="nfw">2</th>
                                        <th width="30" class="nfw">3</th>
                                        <th width="30" class="nfw">4</th>
                                        <th width="30" class="nfw">5</th>
                                        <th width="30" class="nfw">6</th>
                                        <th width="30" class="nfw">7</th>
                                        <th width="30" class="nfw">8</th>
                                        <th width="30" class="nfw">9</th>
                                        <th width="30" class="nfw" id="thbr">0</th>
                                        <th width="30" class="nfw">1</th>
                                        <th width="30" class="nfw">2</th>
                                        <th width="30" class="nfw">3</th>
                                        <th width="30" class="nfw">4</th>
                                        <th width="30" class="nfw">5</th>
                                        <th width="30" class="nfw">6</th>
                                        <th width="30" class="nfw">7</th>
                                        <th width="30" class="nfw">8</th>
                                        <th width="30" class="nfw">9</th>
                                        <th width="30" class="nfw" id="thbr">0</th>
                                        <th width="30" class="nfw">1</th>
                                        <th width="30" class="nfw">2</th>
                                        <th width="30" class="nfw">3</th>
                                        <th width="30" class="nfw">4</th>
                                        <th width="30" class="nfw">5</th>
                                        <th width="30" class="nfw">6</th>
                                        <th width="30" class="nfw">7</th>
                                        <th width="30" class="nfw">8</th>
                                        <th width="30" class="nfw">9</th>
                                        <th width="30" class="nfw">0</th>
                                        <th width="30" class="nfw">1</th>
                                        <th width="30" class="nfw">2</th>
                                        <th width="30" class="nfw">3</th>
                                        <th width="30" class="nfw">4</th>
                                        <th width="30" class="nfw">5</th>
                                        <th width="30" class="nfw">6</th>
                                        <th width="30" class="nfw">7</th>
                                        <th width="30" class="nfw">8</th>
                                        <th width="30" class="nfw">9</th>
                                        <th width="30" class="nfw">0</th>
                                        <th width="30" class="nfw">1</th>
                                        <th width="30" class="nfw">2</th>
                                        <th width="30" class="nfw">3</th>
                                        <th width="30" class="nfw">
                                            <p>4</p>
                                        </th>
                                        <th width="30" class="nfw">5</th>
                                        <th width="30" class="nfw">6</th>
                                        <th width="30" class="nfw">7</th>
                                        <th width="30" class="nfw">8</th>
                                        <th width="30" class="nfw">9</th>
                                        <!--<th width="30" class="nfw">1</th>
                                        <th width="30" class="nfw">2</th>
                                        <th width="30" class="nfw">3</th>
                                        <th width="30" class="nfw">4</th>
                                        <th width="30" class="nfw">5</th>
                                        <th width="30" class="nfw">6</th>
                                        <th width="30" class="nfw">7</th>
                                        <th width="30" class="nfw">8</th>
                                        <th width="30" class="nfw">9</th>
                                        <th width="30" class="nfw">10</th>
                                        <th width="30" class="nfw">1</th>
                                        <th width="30" class="nfw">2</th>
                                        <th width="30" class="nfw">3</th>
                                        <th width="30" class="nfw">4</th>
                                        <th width="30" class="nfw">5</th>
                                        <th width="30" class="nfw">6</th>
                                        <th width="30" class="nfw">7</th>
                                        <th width="30" class="nfw">8</th>
                                        <th width="30" class="nfw">9</th>
                                        <th width="30" class="nfw">10</th>-->
                                        <!--<th width="30" class="nfw">1</th>
                                        <th width="30" class="nfw">2</th>
                                        <th width="30" class="nfw">3</th>
                                        <th width="30" class="nfw">4</th>
                                        <th width="30" class="nfw">5</th>
                                        <th width="30" class="nfw">6</th>
                                        <th width="30" class="nfw">7</th>
                                        <th width="30" class="nfw">8</th>
                                        <th width="30" class="nfw">9</th>
                                        <th width="30" class="nfw">10</th>
                                        <th width="30" class="nfw">1</th>
                                        <th width="30" class="nfw">2</th>
                                        <th width="30" class="nfw">3</th>
                                        <th width="30" class="nfw">4</th>
                                        <th width="30" class="nfw">5</th>
                                        <th width="30" class="nfw">6</th>
                                        <th width="30" class="nfw">7</th>
                                        <th width="30" class="nfw">8</th>
                                        <th width="30" class="nfw">9</th>
                                        <th width="30" class="nfw">10</th>
                                        <th width="30" class="nfw">1</th>
                                        <th width="30" class="nfw">2</th>
                                        <th width="30" class="nfw">3</th>
                                        <th width="30" class="nfw">4</th>
                                        <th width="30" class="nfw">5</th>
                                        <th width="30" class="nfw">6</th>
                                        <th width="30" class="nfw">7</th>
                                        <th width="30" class="nfw">8</th>
                                        <th width="30" class="nfw">9</th>
                                        <th width="30" class="nfw">10</th>-->
                                    </tr>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <!--冷热分析-->
        <div v-show="pageTabSelect=='lrfx'" class="zhlzbox margt20" style="display:block">
            <div class="listhead">
                <div class="listheadl">
                    <span class="lmms"><i>3</i>冷热分析</span>
                </div>
                <div class="listheadr">
                </div>
            </div>
            <div class="listbox">
                <div class="checkbox">
                    <div id="lmms" class="checkbtnzh checkbtnmc noboder">
                        <!--<ul class="lrul">-->
                        <!--<li class="lenresinli"><i>1</i>-->
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
                <div class="listcontent nomargin ssc">
                    <div class="box">
                        <div class="bastren w1200 noboder">
                            <table class="blueqiu" id="lensetable" cellpadding="1" cellspacing="1" border="0">
                                <tbody>
                                <tr height="50">
                                    <th>名次</th>
                                    <th>热</th>
                                    <th>温</th>
                                    <th>冷</th>
                                </tr>
                                <tr height="50" v-for="rif,index in rlfxList">
                                    <td width="100">第 {{index+1}} 球</td>
                                    <td width="300" style="text-align: left">
                                        <ul v-for="r in rif" v-if="r[1]>=4">
                                            <li class="sscnumblue" style="color:#012537;text-align: center">
                                                {{r[0]}}
                                            </li>
                                        </ul>
                                    </td>
                                    <td width="400" style="text-align: left">
                                        <ul v-for="r in rif" v-if="r[1]<4 && r[1]>=2">
                                            <li class="sscnumblue" style="color:#012537;text-align: center">
                                                {{r[0]}}
                                            </li>
                                        </ul>
                                    </td>
                                    <td width="400" style="text-align: left">
                                        <ul v-for="r in rif" v-if="r[1]<2">
                                            <li class="sscnumblue" style="color:#012537;text-align: center">
                                                {{r[0]}}
                                            </li>
                                        </ul>
                                    </td>
                                </tr>
                                <!--<tr height="50">-->
                                <!--<td width="100">第二球</td>-->
                                <!--<td width="300">-->
                                <!--<ul>-->
                                <!--<li class="sscnumblue">2<i>4</i></li>-->
                                <!--<li class="sscnumblue">6<i>4</i></li>-->
                                <!--</ul>-->
                                <!--</td>-->
                                <!--<td width="400">-->
                                <!--<ul>-->
                                <!--<li class="sscnumblue">4</li>-->
                                <!--<li class="sscnumblue">7</li>-->
                                <!--<li class="sscnumblue">8</li>-->
                                <!--<li class="sscnumblue">9</li>-->
                                <!--</ul>-->
                                <!--</td>-->
                                <!--<td width="400">-->
                                <!--<ul>-->
                                <!--<li class="sscnumblue">0</li>-->
                                <!--<li class="sscnumblue">1</li>-->
                                <!--<li class="sscnumblue">3</li>-->
                                <!--<li class="sscnumblue">5</li>-->
                                <!--</ul>-->
                                <!--</td>-->
                                <!--</tr>-->
                                <!--<tr height="50">-->
                                <!--<td width="100">第三球</td>-->
                                <!--<td width="300">-->
                                <!--<ul>-->
                                <!--<li class="sscnumblue">0<i>5</i></li>-->
                                <!--</ul>-->
                                <!--</td>-->
                                <!--<td width="400">-->
                                <!--<ul>-->
                                <!--<li class="sscnumblue">1</li>-->
                                <!--<li class="sscnumblue">2</li>-->
                                <!--<li class="sscnumblue">4</li>-->
                                <!--<li class="sscnumblue">6</li>-->
                                <!--<li class="sscnumblue">7</li>-->
                                <!--<li class="sscnumblue">9</li>-->
                                <!--</ul>-->
                                <!--</td>-->
                                <!--<td width="400">-->
                                <!--<ul>-->
                                <!--<li class="sscnumblue">3</li>-->
                                <!--<li class="sscnumblue">5</li>-->
                                <!--<li class="sscnumblue">8</li>-->
                                <!--</ul>-->
                                <!--</td>-->
                                <!--</tr>-->
                                <!--<tr height="50">-->
                                <!--<td width="100">第四球</td>-->
                                <!--<td width="300">-->
                                <!--<ul></ul>-->
                                <!--</td>-->
                                <!--<td width="400">-->
                                <!--<ul>-->
                                <!--<li class="sscnumblue">0</li>-->
                                <!--<li class="sscnumblue">2</li>-->
                                <!--<li class="sscnumblue">3</li>-->
                                <!--<li class="sscnumblue">6</li>-->
                                <!--<li class="sscnumblue">7</li>-->
                                <!--<li class="sscnumblue">8</li>-->
                                <!--<li class="sscnumblue">9</li>-->
                                <!--</ul>-->
                                <!--</td>-->
                                <!--<td width="400">-->
                                <!--<ul>-->
                                <!--<li class="sscnumblue">1</li>-->
                                <!--<li class="sscnumblue">4</li>-->
                                <!--<li class="sscnumblue">5</li>-->
                                <!--</ul>-->
                                <!--</td>-->
                                <!--</tr>-->
                                <!--<tr height="50">-->
                                <!--<td width="100">第五球</td>-->
                                <!--<td width="300">-->
                                <!--<ul>-->
                                <!--<li class="sscnumblue">0<i>4</i></li>-->
                                <!--<li class="sscnumblue">5<i>5</i></li>-->
                                <!--</ul>-->
                                <!--</td>-->
                                <!--<td width="400">-->
                                <!--<ul>-->
                                <!--<li class="sscnumblue">2</li>-->
                                <!--<li class="sscnumblue">6</li>-->
                                <!--<li class="sscnumblue">7</li>-->
                                <!--<li class="sscnumblue">9</li>-->
                                <!--</ul>-->
                                <!--</td>-->
                                <!--<td width="400">-->
                                <!--<ul>-->
                                <!--<li class="sscnumblue">1</li>-->
                                <!--<li class="sscnumblue">3</li>-->
                                <!--<li class="sscnumblue">4</li>-->
                                <!--<li class="sscnumblue">8</li>-->
                                <!--</ul>-->
                                <!--</td>-->
                                <!--</tr>-->
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
                                        <th rowspan="2" width="60px">号码</th>
                                        <th colspan="4" height="50">第一球</th>
                                        <th colspan="4">第二球</th>
                                        <th colspan="4">第三球</th>
                                        <th colspan="4">第四球</th>
                                        <th colspan="4">第五球</th>

                                    </tr>
                                    <tr class="smth">
                                        <th height="45" width="40px">总开</th>
                                        <th width="40px">最多连开</th>
                                        <th width="40px">当前遗漏</th>
                                        <th width="40px">最大遗漏</th>

                                        <th width="40px">总开</th>
                                        <th width="40px">最多连开</th>
                                        <th width="40px">当前遗漏</th>
                                        <th width="40px">最大遗漏</th>

                                        <th width="40px">总开</th>
                                        <th width="40px">最多连开</th>
                                        <th width="40px">当前遗漏</th>
                                        <th width="40px">最大遗漏</th>

                                        <th width="40px">总开</th>
                                        <th width="40px">最多连开</th>
                                        <th width="40px">当前遗漏</th>
                                        <th width="40px">最大遗漏</th>

                                        <th width="40px">总开</th>
                                        <th width="40px">最多连开</th>
                                        <th width="40px">当前遗漏</th>
                                        <th width="40px">最大遗漏</th>
                                    </tr>
                                    </thead>
                                    <tbody v-for="item,index in jrhmtjList">
                                    <tr>
                                        <td>{{index}}</td>
                                        <td :style="jrhmtjColorChange(item.zk1)">{{item.zk1}}</td>
                                        <td :style="jrhmtjColorChange(item.lk1)">{{item.lk1}}</td>
                                        <td :style="jrhmtjColorChange(item.wk1)">{{item.wk1}}</td>
                                        <td></td>
                                        <td :style="jrhmtjColorChange(item.zk2)">{{item.zk2}}</td>
                                        <td></td>
                                        <td :style="jrhmtjColorChange(item.wk2)">{{item.wk2}}</td>
                                        <td></td>
                                        <td :style="jrhmtjColorChange(item.zk3)">{{item.zk3}}</td>
                                        <td></td>
                                        <td :style="jrhmtjColorChange(item.wk3)">{{item.wk3}}</td>
                                        <td></td>
                                        <td :style="jrhmtjColorChange(item.zk4)">{{item.zk4}}</td>
                                        <td></td>
                                        <td :style="jrhmtjColorChange(item.wk4)">{{item.wk4}}</td>
                                        <td></td>
                                        <td :style="jrhmtjColorChange(item.zk5)">{{item.zk5}}</td>
                                        <td></td>
                                        <td :style="jrhmtjColorChange(item.wk5)">{{item.wk5}}</td>
                                        <td></td>
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
                                    <td rowspan="2" style="width:100px">日期</td>
                                    <td class="odd" colspan="4">第一球</td>
                                    <td colspan="4">第二球</td>
                                    <td class="odd" colspan="4">第三球</td>
                                    <td colspan="4">第四球</td>
                                    <td class="odd" colspan="4">第五球</td>
                                </tr>
                                <tr class="thead smthead">
                                    <template v-for="i in 5">
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

        <!--号码历史统计-->
        <div v-show="pageTabSelect=='hmlstj'" class="zhlzbox margt20" style="display:block">
            <div class="listhead">
                <div class="listheadl">
                    <span class="lmms"><i>3</i>历史号码统计</span>
                </div>
            </div>
            <div class="listbox">
                <div class="listcontent nomargin">
                    <div class="box">
                        <div class="bastren w1200 noboder">
                            <table id="dsdxlstable" width="100%" border="0" cellpadding="0" cellspacing="0">
                                <thead>
                                <tr class="thead smthead firthtd">
                                    <th width="26">日期</th>
                                    <th width="26">0</th>
                                    <th width="26">1</th>
                                    <th width="26">2</th>
                                    <th width="26">3</th>
                                    <th width="26">4</th>
                                    <th width="26">5</th>
                                    <th width="26">6</th>
                                    <th width="26">7</th>
                                    <th width="26">8</th>
                                    <th width="26">9</th>
                                    <th width="26">单</th>
                                    <th width="26">双</th>
                                    <th width="26">大</th>
                                    <th width="26">小</th>
                                    <th width="26">龙</th>
                                    <th width="26">虎</th>
                                    <th width="26">平</th>
                                </tr>
                                </thead>
                                <tbody>
                                <tr>
                                    <td>{{dateStr}}</td>
                                    <td>{{hisNumList.no0}}</td>
                                    <td>{{hisNumList.no1}}</td>
                                    <td>{{hisNumList.no2}}</td>
                                    <td>{{hisNumList.no3}}</td>
                                    <td>{{hisNumList.no4}}</td>
                                    <td>{{hisNumList.no5}}</td>
                                    <td>{{hisNumList.no6}}</td>
                                    <td>{{hisNumList.no7}}</td>
                                    <td>{{hisNumList.no8}}</td>
                                    <td>{{hisNumList.no9}}</td>
                                    <td>{{hisNumList.dan}}</td>
                                    <td>{{hisNumList.suan}}</td>
                                    <td>{{hisNumList.da}}</td>
                                    <td>{{hisNumList.xiao}}</td>
                                    <td>{{hisNumList.long}}</td>
                                    <td>{{hisNumList.hu}}</td>
                                    <td>{{hisNumList.pin}}</td>
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
                            <li class="sinli" :class="lmsjBnt==0?'selected':''" ><span>1</span>
                                <a href="javascript:void(0)" @click="lmsjtjCustom(0)">第一球</a>
                            </li>
                            <li class="sinli" :class="lmsjBnt==1?'selected':''" ><span>2</span>
                                <a href="javascript:void(0)" @click="lmsjtjCustom(1)">第二球</a>
                            </li>
                            <li class="sinli" :class="lmsjBnt==2?'selected':''" ><span>3</span>
                                <a href="javascript:void(0)" @click="lmsjtjCustom(2)">第三球</a>
                            </li>
                            <li class="sinli" :class="lmsjBnt==3?'selected':''" ><span>4</span>
                                <a href="javascript:void(0)" @click="lmsjtjCustom(3)">第四球</a>
                            </li>
                            <li class="sinli" :class="lmsjBnt==4?'selected':''" ><span>5</span>
                                <a href="javascript:void(0)" @click="lmsjtjCustom(4)">第五球</a>
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
                                <tr height="50" class="mc1"  >
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




        <!--单双大小路珠-->
        <div v-show="pageTabSelect=='sdRoad'" class="zhlzbox margt20" style="display:block">
            <div class="listhead">
                <div class="listheadl">
                    <span class="lmms">单双大小路珠</span>
                </div>
                <div class="listheadr">
                    <div class="listheadrl">
                        <span id="today" :class="dayType==0?'checked':''"  @click="changeDate(0)">今天</span>
                        <span id="yesterday" :class="dayType==-1?'checked':''" @click="changeDate(-1)">昨天</span>
                        <span id="qianday" :class="dayType==-2?'checked':''" @click="changeDate(-2)">前天</span>
                    </div>
                    <div class="listheadrr">
                        <div class="rightime">
                            <datepicker v-model="dateStr" :format="$moment(dateStr).format('YYYY-MM-DD')" :language="languages.zh"
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
                        <ul class="xuanzemc">
                            <li class="mctitle">选择名次：</li>
                            <!--选中加class checked-->
                            <template v-for="(num,index) in 5">
                                <li :class="sdRoad.filters.selectRoad=='no'+num?'lztype selected':'lztype'" @click="sdRoadSelectFun('no'+num)"><span>2</span><a href="javascript:void(0)">{{$t('ssclz_no'+num)}}</a></li>
                            </template>
                        </ul>
                        <ul >
                            <li class="title">筛选路珠：</li>
                            <template v-for="(item,index) in sdRoad.filters.checkBoxList">
                                <li @click="sdRoadCheckBoxFun(index)" :class="sdRoad.filters.checkBoxList[index]?'sinli checked':'sinli'"><i>1</i>
                                    <a>{{$t(index)}}路珠</a>
                                </li>
                            </template>

                        </ul>
                    </div>
                </div>
                <div class="listcontent">
                    <div class="box">
                        <div class="dsLZ">

                            <template v-for="(noList,noIndex) in luzhuList">
                                <template v-for="(noArr,arrIndex) in noList">
                                    <table v-if="((sdRoad.filters.checkBoxList['ou'] && arrIndex.indexOf('ou')!=-1) || (sdRoad.filters.checkBoxList['oe'] && arrIndex.indexOf('oe')!=-1)) && sdRoad.filters.selectRoad==noIndex" class="lz_table_head  g_w1000">
                                        <tbody>
                                        <tr>
                                            <td>连续出现&nbsp;<input class="txtNum" v-model="sdRoad.filters['numTwo'+arrIndex]" type="number" min="1">&nbsp;次以上&nbsp;
                                                <select v-model="sdRoad.filters['selectTwo'+arrIndex]" class="secType">
                                                    <option :value="sdRoad.filters.checkBoxList['ou'] && arrIndex.indexOf('ou')!=-1?'over':'odd'">{{sdRoad.filters.checkBoxList['ou'] && arrIndex.indexOf('ou')!=-1?'大':'单'}}</option>
                                                    <option :value="sdRoad.filters.checkBoxList['ou'] && arrIndex.indexOf('ou')!=-1?'under':'even'">{{sdRoad.filters.checkBoxList['ou'] && arrIndex.indexOf('ou')!=-1?'小':'双'}}</option>
                                                </select>
                                                <input type="button" class="btnCheck" @click="sdRoadTableFilterFun(arrIndex,noIndex)"
                                                       value="确定">出现的次数为: <span class="sec_count">{{sdRoad.filters['showNum'+arrIndex]}}</span>
                                            </td>
                                            <td></td>
                                            <td class="righttd">
                                                <span class="now_l"></span><span class="jinri">{{totalDateTime}}</span>&nbsp;累计：{{sdRoad.filters.checkBoxList['ou'] && arrIndex.indexOf('ou')!=-1?'大':'单'}}(<span
                                                class="count left_count" :data="sdRoad.filters.checkBoxList['ou'] && arrIndex.indexOf('ou')!=-1?'大':'单'">{{sdRoad.filters.checkBoxList['ou'] && arrIndex.indexOf('ou')!=-1?sdRoad[noIndex].over:sdRoad[noIndex].odd}}</span>)&nbsp;&nbsp;{{sdRoad.filters.checkBoxList['ou'] && arrIndex.indexOf('ou')!=-1?'小':'双'}}(<span
                                                class="count right_count" :data="sdRoad.filters.checkBoxList['ou'] && arrIndex.indexOf('ou')!=-1?'小':'双'">{{sdRoad.filters.checkBoxList['ou'] && arrIndex.indexOf('ou')!=-1?sdRoad[noIndex].under:sdRoad[noIndex].even}}</span>)
                                                <span class="important"><span class="ifgj">{{$t('ssclz_'+noIndex)}}</span>&nbsp;<span
                                                    class="ifds">{{$t(arrIndex.substring(3,5))}}</span>&nbsp;&nbsp;&nbsp;最新</span>
                                            </td>
                                            <td class="tobottom"></td>
                                        </tr>
                                        </tbody>
                                    </table>
                                    <div v-if="((sdRoad.filters.checkBoxList['ou'] && arrIndex.indexOf('ou')!=-1) || (sdRoad.filters.checkBoxList['oe'] && arrIndex.indexOf('oe')!=-1)) && sdRoad.filters.selectRoad==noIndex" class="item_con" dir="rtl" style="background-position-x: -29px;">
                                        <table class="lz_table_con" border="0" cellpadding="1" cellspacing="1">
                                            <tbody>
                                            <tr class="tablebox">
                                                <template v-for="(list,index) in noArr">
                                                    <template v-for="(item,i) in list">
                                                        <td :class="item[0]=='even'||item[0]=='over'?'bgcolor':''" :style="item[0] == sdRoad.filters['select'+arrIndex] && item.length>=sdRoad.filters['num'+arrIndex] && sdRoad.filters['btn'+arrIndex]?'color: rgb(255, 255, 255); background-color: rgb(253, 173, 86);':''">
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

        <!--号码路珠-->
        <div v-show="pageTabSelect=='noRoad'" class="zhlzbox margt20" style="display:block">
            <div class="listhead">
                <div class="listheadl">
                    <span class="lmms">号码路珠</span>
                </div>
                <div class="listheadr">
                    <div class="listheadrl">
                        <span id="today" :class="dayType==0?'checked':''"  @click="changeDate(0)">今天</span>
                        <span id="yesterday" :class="dayType==-1?'checked':''" @click="changeDate(-1)">昨天</span>
                        <span id="qianday" :class="dayType==-2?'checked':''" @click="changeDate(-2)">前天</span>
                    </div>
                    <div class="listheadrr">
                        <div class="rightime">
                            <datepicker v-model="dateStr" :format="$moment(dateStr).format('YYYY-MM-DD')" :language="languages.zh"
                                        @selected="customFormatter" name="uniquename"></datepicker>
                        </div>
                        <div>选择日期&nbsp;</div>
                    </div>
                </div>
            </div>
            <!--号码路珠-->
            <div class="listbox">
                <div class="checkbox">
                    <div id="lmms" class="checkbtnzh checkbtnmc">
                        <ul >
                            <li class="title">筛选名次：</li>
                            <template v-for="(item,index) in noRoad.filters.checkBoxList">
                                <li @click="noRoadCheckBoxFun(index)" :class="noRoad.filters.checkBoxList[index]?'sinli checked':'sinli'"><i>1</i>
                                    <a>{{$t('num'+index)}}</a>
                                </li>
                            </template>
                            <li @click="noRoadCheckBoxAllHandle(true)" class="zhcheckall">全选</li>
                            <li @click="noRoadCheckBoxAllHandle(false)" class="zhclear">清空</li>
                        </ul>
                    </div>
                </div>
                <div class="listcontent">
                    <div class="box">
                        <div class="dsLZ">

                            <template v-for="(noList,noIndex) in noLuzhuList">
                                <template v-for="(noArr,arrIndex) in noList">
                                    <table v-if="noRoad.filters.checkBoxList[noIndex] && arrIndex.indexOf('bool')!=-1" class="lz_table_head  g_w1000">
                                        <tbody>
                                        <tr>
                                            <td>连续出现&nbsp;<input class="txtNum" v-model="noRoad.filters['numTwo'+noIndex]" type="number" min="1">&nbsp;次以上&nbsp;
                                                <select v-model="noRoad.filters['selectTwo'+noIndex]" class="secType">
                                                    <option value="always">总来</option>
                                                    <option value="noalways">总不来</option>
                                                </select>
                                                <input type="button" class="btnCheck" @click="noRoadTableFilterFun(arrIndex,noIndex)"
                                                       value="确定">出现的次数为: <span class="sec_count">{{noRoad.filters['showNum'+noIndex]}}</span>
                                            </td>
                                            <td></td>
                                            <td class="righttd">
                                                <span class="now_l"></span><span class="jinri">{{totalDateTime}}</span>&nbsp;累计：总来(<span
                                                class="count left_count" data="总来">{{noRoad[noIndex].always}}</span>)&nbsp;&nbsp;没来(<span
                                                class="count right_count" data="总不来">{{noRoad[noIndex].noalways}}</span>)
                                                <span class="important"><span class="ifgj">{{$t('num'+noIndex)}}</span>&nbsp;<span
                                                    class="ifds"></span>&nbsp;&nbsp;&nbsp;最新</span>
                                            </td>
                                            <td class="tobottom"></td>
                                        </tr>
                                        </tbody>
                                    </table>
                                    <div v-if="noRoad.filters.checkBoxList[noIndex] && arrIndex.indexOf('bool')!=-1" class="item_con" dir="rtl" style="background-position-x: -29px;">
                                        <table class="lz_table_con" border="0" cellpadding="1" cellspacing="1">
                                            <tbody>
                                            <tr class="tablebox">
                                                <template v-for="(list,index) in noArr">
                                                    <template v-for="(item,i) in list">
                                                        <td :class="item[0]?'bgcolor':''" :style="((item[0] && noRoad.filters['select'+noIndex] =='always') || (!item[0] && noRoad.filters['select'+noIndex] =='noalways')) && item.length>=noRoad.filters['num'+noIndex] && noRoad.filters['btn'+noIndex]?'color: rgb(255, 255, 255); background-color: rgb(253, 173, 86);':''">
                                                            <template v-for="(num,k) in item">
                                                                <p :style="item[0]?'color: rgb(241, 2, 11); display: block;':'font-weight: normal;'"
                                                                >{{num?'√':'×'}}</p>
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

        <!--总和路珠-->
        <div v-if="pageTabSelect=='sumRoad'" class="zhlzbox margt20" style="display:block">
            <div class="listhead">
                <div class="listheadl">
                    <span class="lmms">总和路珠</span>
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
            <!--总和路珠显示-->
            <div class="listbox">
                <div class="listcontent">
                    <div class="box">
                        <div class="dsLZ">
                            <template v-for="(noList,noIndex) in luzhuList">
                                <template v-for="(arrList,arrIndex) in noList">
                                    <table v-if="arrIndex.indexOf('dtt')==-1 && noIndex=='sum'"
                                           class="lz_table_head  g_w1000">
                                        <tbody>
                                        <tr>
                                            <td>连续出现&nbsp;<input class="txtNum" v-model="sumRoad.filters['numTwo'+arrIndex]"
                                                                 type="number" min="1">&nbsp;次以上&nbsp;
                                                <select v-model="sumRoad.filters['selectTwo'+arrIndex]" class="secType">
                                                    <option :value="arrIndex.indexOf('ou')!=-1?'over':'odd'">{{arrIndex.indexOf('ou')!=-1?'大':'单'}}</option>
                                                    <option :value="arrIndex.indexOf('ou')!=-1?'under':'even'">{{arrIndex.indexOf('ou')!=-1?'小':'双'}}</option>
                                                </select>
                                                <input type="button" class="btnCheck"
                                                       @click="sumRoadTableFilterFun(arrIndex,noIndex)"
                                                       value="确定">出现的次数为: <span class="sec_count">{{sumRoad.filters['showNum'+arrIndex]}}</span>
                                            </td>
                                            <td></td>
                                            <td class="righttd">
                                                <span class="now_l"></span><span class="jinri">{{totalDateTime}}</span>&nbsp;累计：{{arrIndex.indexOf('ou')!=-1?'大':'单'}}(<span
                                                class="count left_count" data="龙">{{arrIndex.indexOf('ou')!=-1?sumRoad[noIndex].over:sumRoad[noIndex].odd}}</span>)&nbsp;&nbsp;{{arrIndex.indexOf('ou')!=-1?'小':'双'}}(<span
                                                class="count right_count" data="虎">{{arrIndex.indexOf('ou')!=-1?sumRoad[noIndex].under:sumRoad[noIndex].even}}</span>)
                                                <span class="important"><span class="ifgj">{{$t(noIndex)}}</span>&nbsp;<span
                                                    class="ifds">{{$t(arrIndex)}}</span>&nbsp;&nbsp;&nbsp;最新</span>
                                            </td>
                                            <td class="tobottom"></td>
                                        </tr>
                                        </tbody>
                                    </table>
                                    <div v-if="arrIndex.indexOf('dtt')==-1 && noIndex=='sum'"
                                         class="item_con" dir="rtl" style="background-position-x: -29px;">
                                        <table class="lz_table_con" border="0" cellpadding="1" cellspacing="1">
                                            <tbody>
                                            <tr class="tablebox">
                                                <template v-for="(list,index) in arrList">
                                                    <template v-for="(item,g) in list">

                                                        <td :class="item[0]=='over' || item[0] == 'odd'?'bgcolor':''"
                                                            :style="item[0] == sumRoad.filters['select'+arrIndex] && item.length>=sumRoad.filters['num'+arrIndex] && sumRoad.filters['btn'+arrIndex]?'color: rgb(255, 255, 255); background-color: rgb(253, 173, 86);':''">
                                                            <template v-for="(num,k) in item">
                                                                <p :style="item[0]=='over' || item[0] == 'odd'?'color: rgb(241, 2, 11); display: block;':'font-weight: normal;'"
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
        data() {
            return {
                topLi: {
                    jrsm: false,
                    cltx: false,
                    hmfb: false
                },
                rlfxList: [],
                jrsmList: [],
                dxdsHisList: [],
                gylhMap: {},
                languages: {en: en, zh: zh, jp: ja},
                dayType: 0,
                dateStr: "",
                changlongList: [],
                hisList: [],
                hisNumList: [],
                jrhmtjList: [],
                jrhmtjType: {
                    red:true,
                    blue:true,
                    green:true,
                },
                lmsjBnt:0,
                lmsjList: [],
                jbzsList: [],
                luzhuList: {
                    'no1': {'no1val': [], 'no1oe': [], 'no1ou': [], 'no1': []},
                    'no2': {'no2val': [], 'no2oe': [], 'no2ou': [], 'no2': []},
                    'no3': {'no3val': [], 'no3oe': [], 'no3ou': [], 'no3': []},
                    'no4': {'no4val': [], 'no4oe': [], 'no4ou': [], 'no4': []},
                    'no5': {'no5val': [], 'no5oe': [], 'no5ou': [], 'no5': []},
                    'sum': {'dtt': [], 'sumou': [], 'sumoe': []}
                },
                noLuzhuList:{
                    'no0': {'no0bool':[]},
                    'no1': {'no1bool':[]},
                    'no2': {'no2bool':[]},
                    'no3': {'no3bool':[]},
                    'no4': {'no4bool':[]},
                    'no5': {'no5bool':[]},
                    'no6': {'no6bool':[]},
                    'no7': {'no7bool':[]},
                    'no8': {'no8bool':[]},
                    'no9': {'no9bool':[]},
                },
                lotteryMainTab: 'roadA',
                resultShowType: 'num',
                resultNumScreen: [],
                resultTypeScreen: '',
                totalDateTime: '今天',
                sumRoad:{
                    'sum':{over:0,under:0,odd:0,even:0},
                    filters:{
                        sumou:1,
                        sumoe:1,
                        numTwosumou:1,
                        numTwosumoe:1,
                        selectsumou:'over',
                        selectsumoe:'odd',
                        selectTwosumou:'over',
                        selectTwosumoe:'odd',
                        btnsumou:false,
                        btnsumoe:false,
                        showNumsumou:0,
                        showNumsumoe:0,
                    }
                },
                noRoad:{
                    'no0':{always:0,noalways:0},
                    'no1':{always:0,noalways:0},
                    'no2':{always:0,noalways:0},
                    'no3':{always:0,noalways:0},
                    'no4':{always:0,noalways:0},
                    'no5':{always:0,noalways:0},
                    'no6':{always:0,noalways:0},
                    'no7':{always:0,noalways:0},
                    'no8':{always:0,noalways:0},
                    'no9':{always:0,noalways:0},
                    filters:{
                        selectRoad:'no1',
                        numno0:1,
                        numno1:1,
                        numno2:1,
                        numno3:1,
                        numno4:1,
                        numno5:1,
                        numno6:1,
                        numno7:1,
                        numno8:1,
                        numno9:1,
                        numTwono0:1,
                        numTwono1:1,
                        numTwono2:1,
                        numTwono3:1,
                        numTwono4:1,
                        numTwono5:1,
                        numTwono6:1,
                        numTwono7:1,
                        numTwono8:1,
                        numTwono9:1,
                        selectno0:'always',
                        selectno1:'always',
                        selectno2:'always',
                        selectno3:'always',
                        selectno4:'always',
                        selectno5:'always',
                        selectno6:'always',
                        selectno7:'always',
                        selectno8:'always',
                        selectno9:'always',
                        selectTwono0:'always',
                        selectTwono1:'always',
                        selectTwono2:'always',
                        selectTwono3:'always',
                        selectTwono4:'always',
                        selectTwono5:'always',
                        selectTwono6:'always',
                        selectTwono7:'always',
                        selectTwono8:'always',
                        selectTwono9:'always',
                        btnno0:false,
                        btnno1:false,
                        btnno2:false,
                        btnno3:false,
                        btnno4:false,
                        btnno5:false,
                        btnno6:false,
                        btnno7:false,
                        btnno8:false,
                        btnno9:false,
                        showNumno0:0,
                        showNumno1:0,
                        showNumno2:0,
                        showNumno3:0,
                        showNumno4:0,
                        showNumno5:0,
                        showNumno6:0,
                        showNumno7:0,
                        showNumno8:0,
                        showNumno9:0,
                        checkBoxList:{
                            no0:true,
                            no1:true,
                            no2:true,
                            no3:true,
                            no4:true,
                            no5:true,
                            no6:true,
                            no7:true,
                            no8:true,
                            no9:true,
                        },
                    }
                },
                sdRoad:{
                    'no1':{over:0,under:0,odd:0,even:0},
                    'no2':{over:0,under:0,odd:0,even:0},
                    'no3':{over:0,under:0,odd:0,even:0},
                    'no4':{over:0,under:0,odd:0,even:0},
                    'no5':{over:0,under:0,odd:0,even:0},
                    filters:{
                        selectRoad:'no1',
                        numno1ou:1,
                        numno2ou:1,
                        numno3ou:1,
                        numno4ou:1,
                        numno5ou:1,
                        numTwono1ou:1,
                        numTwono2ou:1,
                        numTwono3ou:1,
                        numTwono4ou:1,
                        numTwono5ou:1,
                        numno1oe:1,
                        numno2oe:1,
                        numno3oe:1,
                        numno4oe:1,
                        numno5oe:1,
                        numTwono1oe:1,
                        numTwono2oe:1,
                        numTwono3oe:1,
                        numTwono4oe:1,
                        numTwono5oe:1,
                        selectno1ou:'over',
                        selectno2ou:'over',
                        selectno3ou:'over',
                        selectno4ou:'over',
                        selectno5ou:'over',
                        selectTwono1ou:'over',
                        selectTwono2ou:'over',
                        selectTwono3ou:'over',
                        selectTwono4ou:'over',
                        selectTwono5ou:'over',
                        selectno1oe:'odd',
                        selectno2oe:'odd',
                        selectno3oe:'odd',
                        selectno4oe:'odd',
                        selectno5oe:'odd',
                        selectTwono1oe:'odd',
                        selectTwono2oe:'odd',
                        selectTwono3oe:'odd',
                        selectTwono4oe:'odd',
                        selectTwono5oe:'odd',
                        btnno1ou:false,
                        btnno2ou:false,
                        btnno3ou:false,
                        btnno4ou:false,
                        btnno5ou:false,
                        showNumno1ou:0,
                        showNumno2ou:0,
                        showNumno3ou:0,
                        showNumno4ou:0,
                        showNumno5ou:0,
                        btnno1oe:false,
                        btnno2oe:false,
                        btnno3oe:false,
                        btnno4oe:false,
                        btnno5oe:false,
                        showNumno1oe:0,
                        showNumno2oe:0,
                        showNumno3oe:0,
                        showNumno4oe:0,
                        showNumno5oe:0,
                        checkBoxList:{
                            ou:true,
                            oe:true
                        },
                    }
                },
                roadA:{
                    roadAModeSelect:'about',
                    'no1':{over:0,under:0,odd:0,even:0},
                    'no2':{over:0,under:0,odd:0,even:0},
                    'no3':{over:0,under:0,odd:0,even:0},
                    'no4':{over:0,under:0,odd:0,even:0},
                    'no5':{over:0,under:0,odd:0,even:0},
                    'sum':{over:0,under:0,odd:0,even:0,dragon:0,tiger:0,draw:0},
                    filters:{
                        numno1ou:1,
                        numno1oe:1,
                        numno2ou:1,
                        numno2oe:1,
                        numno3ou:1,
                        numno3oe:1,
                        numno4ou:1,
                        numno4oe:1,
                        numno5ou:1,
                        numno5oe:1,
                        sumou:1,
                        sumoe:1,
                        sumdt:1,
                        numTwono1ou:1,
                        numTwono1oe:1,
                        numTwono2ou:1,
                        numTwono2oe:1,
                        numTwono3ou:1,
                        numTwono3oe:1,
                        numTwono4ou:1,
                        numTwono4oe:1,
                        numTwono5ou:1,
                        numTwono5oe:1,
                        numTwosumou:1,
                        numTwosumoe:1,
                        numTwosumdt:1,
                        selectno1ou:'over',
                        selectno1oe:'odd',
                        selectno2ou:'over',
                        selectno2oe:'odd',
                        selectno3ou:'over',
                        selectno3oe:'odd',
                        selectno4ou:'over',
                        selectno4oe:'odd',
                        selectno5ou:'over',
                        selectno5oe:'odd',
                        selectsumou:'over',
                        selectsumoe:'odd',
                        selectsumdt:'dragon',
                        selectTwono1ou:'over',
                        selectTwono1oe:'odd',
                        selectTwono2ou:'over',
                        selectTwono2oe:'odd',
                        selectTwono3ou:'over',
                        selectTwono3oe:'odd',
                        selectTwono4ou:'over',
                        selectTwono4oe:'odd',
                        selectTwono5ou:'over',
                        selectTwono5oe:'odd',
                        selectTwosumou:'over',
                        selectTwosumoe:'odd',
                        selectTwosumdt:'dragon',
                        btnno1ou:false,
                        btnno1oe:false,
                        btnno2ou:false,
                        btnno2oe:false,
                        btnno3ou:false,
                        btnno3oe:false,
                        btnno4ou:false,
                        btnno4oe:false,
                        btnno5ou:false,
                        btnno5oe:false,
                        btnsumou:false,
                        btnsumoe:false,
                        btnsumdt:false,
                        showNumno1ou:0,
                        showNumno1oe:0,
                        showNumno2ou:0,
                        showNumno2oe:0,
                        showNumno3ou:0,
                        showNumno3oe:0,
                        showNumno4ou:0,
                        showNumno4oe:0,
                        showNumno5ou:0,
                        showNumno5oe:0,
                        showNumsumou:0,
                        showNumsumoe:0,
                        showNumsumdt:0,
                        aboutCheckNoBoxList:{
                            no1:true,
                            no2:true,
                            no3:true,
                            no4:true,
                            no5:true,
                            sum:true
                        },
                        aboutCheckRoadBoxList:{
                            ou:true,
                            oe:true,
                            dt:true
                        },
                        aloneCheckRoadBoxList:{
                            ou:true,
                            oe:true,
                        },
                        aloneSelectNo:'no1',
                        lmCheckNumBoxList:{
                            no1:true,
                            no2:true,
                            no3:true,
                            no4:true,
                            no5:true,
                            sum:true,
                            dt:true,
                        },
                        lmSelectRoad:'ou'
                    },



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
            ...mapGetters(['lotteryKey', 'pageTabSelect'])
        },
        methods: {
            sumRoadTableFilterFun(key,no){
                this.sumRoad.filters['num'+key] = this.sumRoad.filters['numTwo'+key];
                this.sumRoad.filters['select'+key] = this.sumRoad.filters['selectTwo'+key];
                this.sumRoad.filters['btn'+key] = true;
                this.sumRoad.filters['showNum'+key] = 0;
                this.luzhuList[no][key].forEach(item=>{

                    item.forEach(obj=>{
                        if(obj[0]==this.sumRoad.filters['select'+key] && obj.length>=this.sumRoad.filters['num'+key]){
                            this.sumRoad.filters['showNum'+key]++;
                        }
                    })
                })
            },
            numberRTableFilterFun(key,no){
                this.numberR.filters['num'+no] = this.numberR.filters['numTwo'+no];
                this.numberR.filters['select'+no] = this.numberR.filters['selectTwo'+no];
                this.numberR.filters['btn'+no] = true;
                this.numberR.filters['showNum'+no] = 0;
                this.luzhuList[no][key].forEach(item=>{
                    item.forEach(obj=>{
                        if(obj[0]==this.numberR.filters['select'+no] && obj.length>=this.numberR.filters['num'+no]){
                            this.numberR.filters['showNum'+no]++;
                        }
                    })
                })
            },
            numberRCheckBoxFun(index){
                this.numberR.filters.checkBoxList[index] = !this.numberR.filters.checkBoxList[index];
            },
            numberRCheckAll(flag){
                for(let key in this.numberR.filters.checkBoxList){
                    this.numberR.filters.checkBoxList[key] = flag;
                }
            },
            roadATableFilterFun(key,no){
                if(key=='dtt'){
                    this.roadA.filters['numsumdt'] = this.roadA.filters['numTwosumdt'];
                    this.roadA.filters['selectsumdt'] = this.roadA.filters['selectTwosumdt'];
                    this.roadA.filters['btnsumdt'] = true;
                    this.roadA.filters['showNumsumdt'] = 0;
                    this.luzhuList['sum']['dtt'].forEach(item=>{
                        item.forEach(obj=>{
                            if(obj[0]==this.roadA.filters['selectsumdt'] && obj.length>=this.roadA.filters['numsumdt']){
                                this.roadA.filters['showNumsumdt']++;
                            }
                        })
                    })
                }else{
                    this.roadA.filters['num'+key] = this.roadA.filters['numTwo'+key];
                    this.roadA.filters['select'+key] = this.roadA.filters['selectTwo'+key];
                    this.roadA.filters['btn'+key] = true;
                    this.roadA.filters['showNum'+key] = 0;
                    this.luzhuList[no][key].forEach(item=>{
                        item.forEach(obj=>{
                            if(obj[0]==this.roadA.filters['select'+key] && obj.length>=this.roadA.filters['num'+key]){
                                this.roadA.filters['showNum'+key]++;
                            }
                        })
                    })
                }

            },
            roadALmNoFilterAllFun(flag){
                for(let key in this.roadA.filters.lmCheckNumBoxList){
                    this.roadA.filters.lmCheckNumBoxList[key] = flag;
                }
            },
            roadALmNoFilterFun(index){
                this.roadA.filters.lmCheckNumBoxList[index] = !this.roadA.filters.lmCheckNumBoxList[index];
            },
            roadAAloneNoFilterFun(type){
                this.roadA.filters.aloneSelectNo = type;
            },
            roadAAloneNoAll(flag){
                for(let key in this.roadA.filters.aloneCheckRoadBoxList){
                    this.roadA.filters.aloneCheckRoadBoxList[key] = flag;
                }
            },
            roadAAloneFilterRoadFun(index){
                this.roadA.filters.aloneCheckRoadBoxList[index] = !this.roadA.filters.aloneCheckRoadBoxList[index];
            },
            roadAAboutFilterRoadFun(index){
                this.roadA.filters.aboutCheckRoadBoxList[index] = !this.roadA.filters.aboutCheckRoadBoxList[index];
            },
            roadAAboutNoAll(flag){
                for(let key in this.roadA.filters.aboutCheckNoBoxList){
                    this.roadA.filters.aboutCheckNoBoxList[key] = flag;
                }
            },
            roadAAboutFilterNoFun(index){
                this.roadA.filters.aboutCheckNoBoxList[index] = !this.roadA.filters.aboutCheckNoBoxList[index];
            },
            roadAModelFun(mode){
                this.roadA.roadAModeSelect = mode;
            },
            sdRoadSelectFun(type){
                this.sdRoad.filters.selectRoad = type;
                for(let key in this.sdRoad.filters){
                    if(key.indexOf('num')!=-1 || key.indexOf('showNum')!=-1 ){
                        this.sdRoad.filters[key] = 1;
                    }else if( key.indexOf('btn')!=-1){
                        this.sdRoad.filters[key] = false;
                    }
                }

                for(let key in this.sdRoad.filters){
                    if(key.indexOf('selectTwo')!=-1 && key.indexOf('ou')!=-1){
                        this.sdRoad.filters[key] = 'over';
                    }
                }
                for(let key in this.sdRoad.filters){
                    if(key.indexOf('selectTwo')!=-1 && key.indexOf('oe')!=-1){
                        this.sdRoad.filters[key] = 'odd';
                    }
                }
            },
            sdRoadCheckBoxFun(key){
                this.sdRoad.filters.checkBoxList[key] = !this.sdRoad.filters.checkBoxList[key];
            },
            sdRoadCheckBoxAllHandle(flag){
                for(let key in this.sdRoad.filters.checkBoxList){
                    this.sdRoad.filters.checkBoxList[key] = flag;
                }
            },

            sdRoadTableFilterFun(key,no){
                this.sdRoad.filters['num'+key] = this.sdRoad.filters['numTwo'+key];
                this.sdRoad.filters['select'+key] = this.sdRoad.filters['selectTwo'+key];
                this.sdRoad.filters['btn'+key] = true;
                this.sdRoad.filters['showNum'+key] = 0;
                this.luzhuList[no][key].forEach(item=>{
                    item.forEach(obj=>{
                        if(obj[0]==this.sdRoad.filters['select'+key] && obj.length>=this.sdRoad.filters['num'+key]){
                            this.sdRoad.filters['showNum'+key]++;
                        }
                    })
                })
            },
            noRoadCheckBoxFun(key){
                this.noRoad.filters.checkBoxList[key] = !this.noRoad.filters.checkBoxList[key];
            },
            noRoadCheckBoxAllHandle(flag){
                for(let key in this.noRoad.filters.checkBoxList){
                    this.noRoad.filters.checkBoxList[key] = flag;
                }
            },
            noRoadTableFilterFun(key,no){
                this.noRoad.filters['num'+no] = this.noRoad.filters['numTwo'+no];
                this.noRoad.filters['select'+no] = this.noRoad.filters['selectTwo'+no];
                this.noRoad.filters['btn'+no] = true;
                this.noRoad.filters['showNum'+no] = 0;
                this.noLuzhuList[no][key].forEach(item=>{
                    item.forEach(obj=>{
                        if(((obj[0] && this.noRoad.filters['select'+no]=='always') || (!obj[0] && this.noRoad.filters['select'+no]=='noalways')) && obj.length>=this.noRoad.filters['num'+no]){
                            this.noRoad.filters['showNum'+no]++;
                        }
                    })
                })
            },
            clearFilterInfo(){
                this.luzhuList = {
                    'no1': {'no1val': [], 'no1oe': [], 'no1ou': [], 'no1': []},
                    'no2': {'no2val': [], 'no2oe': [], 'no2ou': [], 'no2': []},
                    'no3': {'no3val': [], 'no3oe': [], 'no3ou': [], 'no3': []},
                    'no4': {'no4val': [], 'no4oe': [], 'no4ou': [], 'no4': []},
                    'no5': {'no5val': [], 'no5oe': [], 'no5ou': [], 'no5': []},
                    'sum': {'dtt': [], 'sumou': [], 'sumoe': []}
                };
              if(this.pageTabSelect=='roadA'){
                  this.roadA = {
                      roadAModeSelect:'about',
                      'no1':{over:0,under:0,odd:0,even:0},
                      'no2':{over:0,under:0,odd:0,even:0},
                      'no3':{over:0,under:0,odd:0,even:0},
                      'no4':{over:0,under:0,odd:0,even:0},
                      'no5':{over:0,under:0,odd:0,even:0},
                      'sum':{over:0,under:0,odd:0,even:0,dragon:0,tiger:0,draw:0},
                      filters:{
                          numno1ou:1,
                          numno1oe:1,
                          numno2ou:1,
                          numno2oe:1,
                          numno3ou:1,
                          numno3oe:1,
                          numno4ou:1,
                          numno4oe:1,
                          numno5ou:1,
                          numno5oe:1,
                          sumou:1,
                          sumoe:1,
                          sumdt:1,
                          numTwono1ou:1,
                          numTwono1oe:1,
                          numTwono2ou:1,
                          numTwono2oe:1,
                          numTwono3ou:1,
                          numTwono3oe:1,
                          numTwono4ou:1,
                          numTwono4oe:1,
                          numTwono5ou:1,
                          numTwono5oe:1,
                          numTwosumou:1,
                          numTwosumoe:1,
                          numTwosumdt:1,
                          selectno1ou:'over',
                          selectno1oe:'odd',
                          selectno2ou:'over',
                          selectno2oe:'odd',
                          selectno3ou:'over',
                          selectno3oe:'odd',
                          selectno4ou:'over',
                          selectno4oe:'odd',
                          selectno5ou:'over',
                          selectno5oe:'odd',
                          selectsumou:'over',
                          selectsumoe:'odd',
                          selectsumdt:'dragon',
                          selectTwono1ou:'over',
                          selectTwono1oe:'odd',
                          selectTwono2ou:'over',
                          selectTwono2oe:'odd',
                          selectTwono3ou:'over',
                          selectTwono3oe:'odd',
                          selectTwono4ou:'over',
                          selectTwono4oe:'odd',
                          selectTwono5ou:'over',
                          selectTwono5oe:'odd',
                          selectTwosumou:'over',
                          selectTwosumoe:'odd',
                          selectTwosumdt:'dragon',
                          btnno1ou:false,
                          btnno1oe:false,
                          btnno2ou:false,
                          btnno2oe:false,
                          btnno3ou:false,
                          btnno3oe:false,
                          btnno4ou:false,
                          btnno4oe:false,
                          btnno5ou:false,
                          btnno5oe:false,
                          btnsumou:false,
                          btnsumoe:false,
                          btnsumdt:false,
                          showNumno1ou:0,
                          showNumno1oe:0,
                          showNumno2ou:0,
                          showNumno2oe:0,
                          showNumno3ou:0,
                          showNumno3oe:0,
                          showNumno4ou:0,
                          showNumno4oe:0,
                          showNumno5ou:0,
                          showNumno5oe:0,
                          showNumsumou:0,
                          showNumsumoe:0,
                          showNumsumdt:0,
                          aboutCheckNoBoxList:{
                              no1:true,
                              no2:true,
                              no3:true,
                              no4:true,
                              no5:true,
                              sum:true
                          },
                          aboutCheckRoadBoxList:{
                              ou:true,
                              oe:true,
                              dt:true
                          },
                          aloneCheckRoadBoxList:{
                              ou:true,
                              oe:true,
                          },
                          aloneSelectNo:'no1',
                          lmCheckNumBoxList:{
                              no1:true,
                              no2:true,
                              no3:true,
                              no4:true,
                              no5:true,
                              sum:true,
                              dt:true,
                          },
                          lmSelectRoad:'ou'
                      },



                  };
              }else if(this.pageTabSelect=='sdRoad'){
                  this.sdRoad={
                      'no1':{over:0,under:0,odd:0,even:0},
                      'no2':{over:0,under:0,odd:0,even:0},
                      'no3':{over:0,under:0,odd:0,even:0},
                      'no4':{over:0,under:0,odd:0,even:0},
                      'no5':{over:0,under:0,odd:0,even:0},
                      filters:{
                          selectRoad:'no1',
                              numno1ou:1,
                              numno2ou:1,
                              numno3ou:1,
                              numno4ou:1,
                              numno5ou:1,
                              numTwono1ou:1,
                              numTwono2ou:1,
                              numTwono3ou:1,
                              numTwono4ou:1,
                              numTwono5ou:1,
                              numno1oe:1,
                              numno2oe:1,
                              numno3oe:1,
                              numno4oe:1,
                              numno5oe:1,
                              numTwono1oe:1,
                              numTwono2oe:1,
                              numTwono3oe:1,
                              numTwono4oe:1,
                              numTwono5oe:1,
                              selectno1ou:'over',
                              selectno2ou:'over',
                              selectno3ou:'over',
                              selectno4ou:'over',
                              selectno5ou:'over',
                              selectTwono1ou:'over',
                              selectTwono2ou:'over',
                              selectTwono3ou:'over',
                              selectTwono4ou:'over',
                              selectTwono5ou:'over',
                              selectno1oe:'odd',
                              selectno2oe:'odd',
                              selectno3oe:'odd',
                              selectno4oe:'odd',
                              selectno5oe:'odd',
                              selectTwono1oe:'odd',
                              selectTwono2oe:'odd',
                              selectTwono3oe:'odd',
                              selectTwono4oe:'odd',
                              selectTwono5oe:'odd',
                              btnno1ou:false,
                              btnno2ou:false,
                              btnno3ou:false,
                              btnno4ou:false,
                              btnno5ou:false,
                              showNumno1ou:0,
                              showNumno2ou:0,
                              showNumno3ou:0,
                              showNumno4ou:0,
                              showNumno5ou:0,
                              btnno1oe:false,
                              btnno2oe:false,
                              btnno3oe:false,
                              btnno4oe:false,
                              btnno5oe:false,
                              showNumno1oe:0,
                              showNumno2oe:0,
                              showNumno3oe:0,
                              showNumno4oe:0,
                              showNumno5oe:0,
                              checkBoxList:{
                              ou:true,
                                  oe:true
                          },
                      }
                  };
              }else if(this.pageTabSelect == 'noRoad'){
                  this.noRoad = {
                      'no0':{always:0,noalways:0},
                      'no1':{always:0,noalways:0},
                      'no2':{always:0,noalways:0},
                      'no3':{always:0,noalways:0},
                      'no4':{always:0,noalways:0},
                      'no5':{always:0,noalways:0},
                      'no6':{always:0,noalways:0},
                      'no7':{always:0,noalways:0},
                      'no8':{always:0,noalways:0},
                      'no9':{always:0,noalways:0},
                      filters:{
                          selectRoad:'no1',
                          numno0:1,
                          numno1:1,
                          numno2:1,
                          numno3:1,
                          numno4:1,
                          numno5:1,
                          numno6:1,
                          numno7:1,
                          numno8:1,
                          numno9:1,
                          numTwono0:1,
                          numTwono1:1,
                          numTwono2:1,
                          numTwono3:1,
                          numTwono4:1,
                          numTwono5:1,
                          numTwono6:1,
                          numTwono7:1,
                          numTwono8:1,
                          numTwono9:1,
                          selectno0:'always',
                          selectno1:'always',
                          selectno2:'always',
                          selectno3:'always',
                          selectno4:'always',
                          selectno5:'always',
                          selectno6:'always',
                          selectno7:'always',
                          selectno8:'always',
                          selectno9:'always',
                          selectTwono0:'always',
                          selectTwono1:'always',
                          selectTwono2:'always',
                          selectTwono3:'always',
                          selectTwono4:'always',
                          selectTwono5:'always',
                          selectTwono6:'always',
                          selectTwono7:'always',
                          selectTwono8:'always',
                          selectTwono9:'always',
                          btnno0:false,
                          btnno1:false,
                          btnno2:false,
                          btnno3:false,
                          btnno4:false,
                          btnno5:false,
                          btnno6:false,
                          btnno7:false,
                          btnno8:false,
                          btnno9:false,
                          showNumno0:0,
                          showNumno1:0,
                          showNumno2:0,
                          showNumno3:0,
                          showNumno4:0,
                          showNumno5:0,
                          showNumno6:0,
                          showNumno7:0,
                          showNumno8:0,
                          showNumno9:0,
                          checkBoxList:{
                              no0:true,
                              no1:true,
                              no2:true,
                              no3:true,
                              no4:true,
                              no5:true,
                              no6:true,
                              no7:true,
                              no8:true,
                              no9:true,
                          },
                      }
                  };
              }else if(this.pageTabSelect=='sumRoad'){
                  this.sumRoad={
                      'sum':{over:0,under:0,odd:0,even:0},
                      filters:{
                          sumou:1,
                              sumoe:1,
                              numTwosumou:1,
                              numTwosumoe:1,
                              selectsumou:'over',
                              selectsumoe:'odd',
                              selectTwosumou:'over',
                              selectTwosumoe:'odd',
                              btnsumou:false,
                              btnsumoe:false,
                              showNumsumou:0,
                              showNumsumoe:0,
                      }
                  };
              }
            },
            customFormatter(date,flag) {
                this.dateStr =  this.$moment(date).format('YYYY-MM-DD');
                if(!flag){
                    this.totalDateTime = this.$moment(date).format('MM-DD');
                }
                if(this.pageTabSelect=='instantDraw' || this.pageTabSelect=='jrhmtj'
                    || this.pageTabSelect=='hmlstj' || this.pageTabSelect=='lmsjtj' || this.pageTabSelect=='lrfx'){
                    this.getHisList();
                }else if(this.pageTabSelect =='roadA' || this.pageTabSelect =='sdRoad' || this.pageTabSelect =='noRoad' || this.pageTabSelect=='sumRoad'){
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
                self.$api.Lottery.getLotteryRoad(this.lotteryKey).then(val => {
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
                    cssReturn = 'sscnumblue'
                } else if (this.resultShowType == 'numOu') {
                    if (num > 5) {
                        cssReturn = 'bluebig';
                    } else {
                        cssReturn = 'bluesmall';
                    }
                } else if (this.resultShowType == 'numOe') {
                    if (num % 2 == 0) {
                        cssReturn = 'blueeven';
                    } else {
                        cssReturn = 'bluesingular';
                    }
                }
                if (this.topLi.hmfb) {
                    if (this.resultTypeScreen) {
                        if (this.resultTypeScreen == 'over') {
                            return num > 4 ? cssReturn : cssReturn + ' selectedOpacity';
                        } else if (this.resultTypeScreen == 'under') {
                            return num <= 4 ? cssReturn : cssReturn + ' selectedOpacity';
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
                }
                if (num == 3) {
                    this.resultNumScreen.splice(0, this.resultNumScreen.length);
                    this.resultTypeScreen = ''
                    this.topLi.hmfb = this.topLi.hmfb ? false : true;
                }
            },
            getHisList() {
                this.$api.Lottery.getHisByDayList(this.lotteryKey + "/" + this.dateStr).then(val => {
                    this.hisList = [];
                    if (val.success) {
                        this.hisList = val.data;
                        let jrsm = [0, 0, 0, 0, 0, 0, 0, 0, 0, 0];
                        let gylh = [];
                        for (let i = 0; i < 6; i++) {
                            gylh.push({da: 0, xiao: 0, dan: 0, suan: 0});
                        }
                        this.hisList.forEach(items => {
                            if (items.result) {
                                let nums = items.result.split(",");

                                let gyh = parseInt(nums[0]) + parseInt(nums[1]) + parseInt(nums[2]) + parseInt(nums[3]) + parseInt(nums[4]);
                                let dx = gyh > 22 ? 'OVER' : 'UNDER';
                                let ds = (gyh % 2 === 1) ? 'ODD' : 'EVEN';
                                gylh[0].da += dx == 'OVER' ? 1 : 0;
                                gylh[0].xiao += dx != 'OVER' ? 1 : 0;
                                gylh[0].dan += ds == 'ODD' ? 1 : 0;
                                gylh[0].suan += ds != 'ODD' ? 1 : 0;

                                let map = [];
                                for (let i = 0; i < nums.length; i++) {
                                    map.push(nums[i]);
                                    let num = parseInt(nums[i]) * 1;
                                    num == 0 ? jrsm[0] += 1 :
                                        num == 1 ? jrsm[1] += 1 :
                                            num == 2 ? jrsm[2] += 1 :
                                                num == 3 ? jrsm[3] += 1 :
                                                    num == 4 ? jrsm[4] += 1 :
                                                        num == 5 ? jrsm[5] += 1 :
                                                            num == 6 ? jrsm[6] += 1 :
                                                                num == 7 ? jrsm[7] += 1 :
                                                                    num == 8 ? jrsm[8] += 1 : jrsm[9] += 1;


                                    let dxnum = num > 4 ? 'OVER' : 'UNDER';
                                    let dsnum = (num % 2 === 1) ? 'ODD' : 'EVEN';
                                    gylh[i + 1].da += dxnum != 'OVER' ? 1 : 0;
                                    gylh[i + 1].xiao += dxnum == 'OVER' ? 1 : 0;
                                    gylh[i + 1].dan += dsnum == 'ODD' ? 1 : 0;
                                    gylh[i + 1].suan += dsnum != 'ODD' ? 1 : 0;
                                }
                                items.result = map;
                                items.gyh15lh = this.computeSSCZH15LH(items.result);

                            }
                        })
                        this.changePageTabSelect(this.pageTabSelect);
                        this.jrsmList = jrsm;
                        this.gylhMap = gylh;
                    }
                })
                // if(this.topLi.cltx){
                //     this.readResultRoad();
                // }
            },
            getLuzhuFun(params) {
                let self = this;
                this.$api.Lottery.getLotteryRoad(params + '/' + self.dateStr).then(data => {
                    let luzhuParams = {};
                    for (let obj in data.data) {
                        if (-1 != obj.indexOf('dtt') || -1 != obj.indexOf('sum')) {
                            luzhuParams[obj] = this.$Utils.getArr(data.data[obj]);
                        }
                        if (obj.indexOf('no') != -1 && obj.split('no')[1].length > 2) {
                            luzhuParams[obj] = this.$Utils.getArr(data.data[obj]);
                        } else if (-1 == obj.indexOf('dtt') && -1 == obj.indexOf('sum')) {
                            luzhuParams[obj] = data.data[obj];
                        }
                    }
                    this.hisSumList = data.data.jrtj;
                    if(self.pageTabSelect == 'noRoad'){
                        for (let key in luzhuParams) {
                            if (-1 != key.indexOf('no0') && -1 != key.indexOf('bool')) {
                                self.noLuzhuList.no0[key] = luzhuParams[key];
                            } else if (-1 != key.indexOf('no1') && -1 != key.indexOf('bool')) {
                                self.noLuzhuList.no1[key] = luzhuParams[key];
                            } else if (-1 != key.indexOf('no2') && -1 != key.indexOf('bool')) {
                                self.noLuzhuList.no2[key] = luzhuParams[key];
                            } else if (-1 != key.indexOf('no3') && -1 != key.indexOf('bool')) {
                                self.noLuzhuList.no3[key] = luzhuParams[key];
                            } else if (-1 != key.indexOf('no4') && -1 != key.indexOf('bool')) {
                                self.noLuzhuList.no4[key] = luzhuParams[key];
                            } else if (-1 != key.indexOf('no5') && -1 != key.indexOf('bool')) {
                                self.noLuzhuList.no5[key] = luzhuParams[key];
                            } else if (-1 != key.indexOf('no6') && -1 != key.indexOf('bool')) {
                                self.noLuzhuList.no6[key] = luzhuParams[key];
                            } else if (-1 != key.indexOf('no7') && -1 != key.indexOf('bool')) {
                                self.noLuzhuList.no7[key] = luzhuParams[key];
                            } else if (-1 != key.indexOf('no8') && -1 != key.indexOf('bool')) {
                                self.noLuzhuList.no8[key] = luzhuParams[key];
                            } else if (-1 != key.indexOf('no9') && -1 != key.indexOf('bool')) {
                                self.noLuzhuList.no9[key] = luzhuParams[key];
                            }
                        }
                    }else{
                        for (let key in luzhuParams) {
                            if (-1 != key.indexOf('dtt') || -1 != key.indexOf('sum')) {
                                self.luzhuList.sum[key] = luzhuParams[key];
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
                            }
                        }
                    }
                    if(self.pageTabSelect=='roadA'){
                        for(let arrKey in self.luzhuList){
                            for(let key in self.luzhuList[arrKey]){
                                let num1 = 0;
                                let num2 = 0;
                                let num3 = 0;
                                let type = ['1','2',null];
                                if(key.indexOf('ou')!= -1 || key.indexOf('oe')!=-1 || key.indexOf('dtt')!=-1){
                                    self.luzhuList[arrKey][key].forEach(val=>{
                                        val.forEach(obj=>{
                                            obj.forEach(item=>{
                                                if(item=='over'){
                                                    num1++;
                                                    type[0] = 'over';
                                                }else if(item=='under'){
                                                    num2++;
                                                    type[1] = 'under';
                                                }else if(item=='odd'){
                                                    num1++;
                                                    type[0] = 'odd';
                                                }else if(item=='even'){
                                                    num2++;
                                                    type[1] = 'even';
                                                }else if(item=='dragon'){
                                                    num1++;
                                                    type[0] = 'dragon';
                                                }else if(item=='tiger'){
                                                    num2++;
                                                    type[1] = 'tiger';
                                                }else if( item=='draw'){
                                                    num3++;
                                                    type[2] = 'draw';
                                                }
                                            })
                                        })
                                    })
                                    self.roadA[arrKey][type[0]] = num1;
                                    self.roadA[arrKey][type[1]] = num2;
                                    self.roadA[arrKey][type[2]] = num3;
                                }
                            }
                        }
                    }else if(this.pageTabSelect=='sdRoad'){
                        for(let arrKey in self.luzhuList){

                            for(let key in self.luzhuList[arrKey]){
                                let num1 = 0;
                                let num2 = 0;
                                let type = ['1','2'];
                                if(key.indexOf('sum')==-1 && (key.indexOf('ou')!= -1 || key.indexOf('oe')!=-1)){
                                    self.luzhuList[arrKey][key].forEach(val=>{
                                        val.forEach(obj=>{
                                            obj.forEach(item=>{
                                                if(item=='over'){
                                                    num1++;
                                                    type[0] = 'over';
                                                }else if(item=='under'){
                                                    num2++;
                                                    type[1] = 'under';
                                                }else if(item=='odd'){
                                                    num1++;
                                                    type[0] = 'odd';
                                                }else if(item=='even'){
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
                    }else if(self.pageTabSelect=='noRoad'){
                        for(let arrKey in self.noLuzhuList){

                            for(let key in self.noLuzhuList[arrKey]){
                                let num1 = 0;
                                let num2 = 0;
                                let type = ['1','2'];
                                self.noLuzhuList[arrKey][key].forEach(val=>{
                                    val.forEach(obj=>{
                                        obj.forEach(item=>{
                                            if(item){
                                                num1++;
                                                type[0] = 'always';
                                            }else if(!item){
                                                num2++;
                                                type[1] = 'noalways';
                                            }
                                        })

                                    })

                                })

                                self.noRoad[arrKey][type[0]] = num1;
                                self.noRoad[arrKey][type[1]] = num2;


                            }


                        }
                    }else if(self.pageTabSelect=='sumRoad'){
                        for(let arrKey in self.luzhuList){

                            for(let key in self.luzhuList[arrKey]){
                                let num1 = 0;
                                let num2 = 0;
                                let type = ['1','2'];
                                if(key.indexOf('dtt')==-1 && key.indexOf('sum')!=-1){
                                    self.luzhuList[arrKey][key].forEach(val=>{
                                        val.forEach(obj=>{
                                            obj.forEach(item=>{
                                                if(item=='over'){
                                                    num1++;
                                                    type[0] = 'over';
                                                }else if(item=='under'){
                                                    num2++;
                                                    type[1] = 'under';
                                                }else if(item=='odd'){
                                                    num1++;
                                                    type[0] = 'odd';
                                                }else if(item=='even'){
                                                    num2++;
                                                    type[1] = 'even';
                                                }
                                            })

                                        })

                                    })

                                    self.sumRoad[arrKey][type[0]] = num1;
                                    self.sumRoad[arrKey][type[1]] = num2;
                                }
                            }
                        }
                    }
                })
            },
            customFormatter() {},
            lmsjtjCustom(no) {
                this.lmsjBnt = no;
                this.lmsjList.forEach(item=>{
                    item.da = 0;
                    item.xiao = 0;
                    item.dan = 0;
                    item.suan = 0;
                    item.startDate = item[0];
                    item.endDate =  item[item.length-1];
                    item.forEach(ite=>{
                        let res = ite.result.split(",");
                        item.da += parseInt(res[no]) > 4 ? 1 : 0;
                        item.xiao += parseInt(res[no]) > 4 ? 0 : 1;
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
                for (let i = 0; i < 10; i++) {
                    arrs.set(i, on[i] ? on[i] : 0);
                }
                let arrayObj = Array.from(arrs);
                arrayObj.sort(function (a, b) {
                    return b[1] - a[1]
                });
                return arrayObj;

            },
            changePageTabSelect(newVal) {
                if (newVal == 'zhfx') {
                    this.getLuzhuFun(this.lotteryKey);
                }
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
                            arr.forEach(item => {
                                let res = item.result.split(",");
                                on1s.push(parseInt(res[0]));
                                on2s.push(parseInt(res[1]));
                                on3s.push(parseInt(res[2]));
                                on4s.push(parseInt(res[3]));
                                on5s.push(parseInt(res[4]));
                            })
                            info.push(this.cmpLrfx(on1s))
                            info.push(this.cmpLrfx(on2s))
                            info.push(this.cmpLrfx(on3s))
                            info.push(this.cmpLrfx(on4s))
                            info.push(this.cmpLrfx(on5s))
                            this.rlfxList = info;
                        }
                    })

                }
                if (newVal == 'dsdxls') {
                    let nos = []
                    for (let i = 0; i < 5; i++) {
                        nos.push({noda: 0, noxiao: 0, nodan: 0, nosuan: 0})
                    }
                    this.hisList.forEach((val) => {
                        let res = val.result;
                        for (let i = 0; i < res.length; i++) {
                            nos[i].noda += parseInt(res[i]) > 4 ? 1 : 0;
                            nos[i].noxiao += parseInt(res[i]) > 4 ? 0 : 1;
                            nos[i].nodan += (parseInt(res[i]) % 2 === 1) ? 1 : 0;
                            nos[i].nosuan += (parseInt(res[i]) % 2 === 1) ? 0 : 1;
                        }
                    })
                    this.dxdsHisList = nos;
                }
                if (newVal == 'hmlstj') {
                    let hmHis = {
                        no0: 0, no1: 0, no2: 0, no3: 0, no4: 0, no5: 0, no6: 0, no7: 0, no8: 0, no9: 0,
                        dan: 0, suan: 0, da: 0, xiao: 0, long: 0, hu: 0, pin: 0
                    };
                    this.hisList.forEach((val) => {
                        let res = val.result;
                        hmHis.long += parseInt(res[0]) > parseInt(res[4]) ? 1 : 0;
                        hmHis.hu += parseInt(res[0]) < parseInt(res[4]) ? 1 : 0;
                        hmHis.pin += parseInt(res[0]) == parseInt(res[4]) ? 1 : 0;
                        for (let i = 0; i < res.length; i++) {
                            hmHis.no0 += parseInt(res[i]) == 0 ? 1 : 0;
                            hmHis.no1 += parseInt(res[i]) == 1 ? 1 : 0;
                            hmHis.no2 += parseInt(res[i]) == 2 ? 1 : 0;
                            hmHis.no3 += parseInt(res[i]) == 3 ? 1 : 0;
                            hmHis.no4 += parseInt(res[i]) == 4 ? 1 : 0;
                            hmHis.no5 += parseInt(res[i]) == 5 ? 1 : 0;
                            hmHis.no6 += parseInt(res[i]) == 6 ? 1 : 0;
                            hmHis.no7 += parseInt(res[i]) == 7 ? 1 : 0;
                            hmHis.no8 += parseInt(res[i]) == 8 ? 1 : 0;
                            hmHis.no9 += parseInt(res[i]) == 9 ? 1 : 0;
                            hmHis.da += parseInt(res[i]) > 4 ? 1 : 0;
                            hmHis.xiao += parseInt(res[i]) > 4 ? 0 : 1;
                            hmHis.dan += (parseInt(res[i]) % 2 === 1) ? 1 : 0;
                            hmHis.suan += (parseInt(res[i]) % 2 === 1) ? 0 : 1;
                        }
                    });
                    this.hisNumList = hmHis;
                }
                if (newVal == 'lmsjtj') {

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
                if (newVal == 'jrhmtj') {
                    let numList = [];
                    for (let i = 0; i < 10; i++) {
                        let info = {
                            zk1: 0, wk1: 0,lk1: 0,max1: 0,
                            zk2: 0, wk2: 0,lk1: 0,max1: 0,
                            zk3: 0, wk3: 0,lk1: 0,max1: 0,
                            zk4: 0, wk4: 0,lk1: 0,max1: 0,
                            zk5: 0, wk5: 0,lk1: 0,max1: 0,
                            zk6: 0, wk6: 0,lk1: 0,max1: 0,
                            zk7: 0, wk7: 0,lk1: 0,max1: 0,
                            zk8: 0, wk8: 0,lk1: 0,max1: 0,
                            zk9: 0, wk9: 0,lk1: 0,max1: 0,
                            zk10: 0, wk10: 0,lk1: 0,max1: 0,
                        };
                        numList.push(info);
                    }

                    for (let i = this.hisList.length - 1; i >= 0; i--) {
                        let re = this.hisList[i];
                        for (let i = 0; i < 5; i++) {
                            let no = parseInt(re.result[i]);
                            if (i == 0) {
                                for (let j = 0; j < 10; j++) {
                                    let zkcount = numList[j].zk1;
                                    numList[j].zk1 += no == j ? 1 : 0;
                                    if(j==0){
                                        console.log(zkcount,numList[j].zk1)
                                        numList[j].lk1 += zkcount !=numList[j].zk1 ? 1 : 0;
                                    }


                                    numList[j].wk1 += no == j ? numList[j].wk1 * -1 : 1;
                                }
                            }
                            if (i == 1) {
                                for (let j = 0; j < 10; j++) {
                                    numList[j].zk2 += no == j ? 1 : 0;
                                    numList[j].wk2 += no == j ? numList[j].wk2 * -1 : 1;
                                }
                            }
                            if (i == 2) {
                                for (let j = 0; j < 10; j++) {
                                    numList[j].zk3 += no == j ? 1 : 0;
                                    numList[j].wk3 += no == j ? numList[j].wk3 * -1 : 1;
                                }
                            }
                            if (i == 3) {
                                for (let j = 0; j < 10; j++) {
                                    numList[j].zk4 += no == j ? 1 : 0;
                                    numList[j].wk4 += no == j ? numList[j].wk4 * -1 : 1;
                                }
                            }
                            if (i == 4) {
                                for (let j = 0; j < 10; j++) {
                                    numList[j].zk5 += no == j ? 1 : 0;
                                    numList[j].wk5 += no == j ? numList[j].wk5 * -1 : 1;
                                }
                            }
                            if (i == 5) {
                                for (let j = 0; j < 10; j++) {
                                    numList[j].zk6 += no == j ? 1 : 0;
                                    numList[j].wk6 += no == j ? numList[j].wk6 * -1 : 1;
                                }
                            }
                            if (i == 6) {
                                for (let j = 0; j < 10; j++) {
                                    numList[j].zk7 += no == j ? 1 : 0;
                                    numList[j].wk7 += no == j ? numList[j].wk7 * -1 : 1;
                                }
                            }
                            if (i == 7) {
                                for (let j = 0; j < 10; j++) {
                                    numList[j].zk8 += no == j ? 1 : 0;
                                    numList[j].wk8 += no == j ? numList[j].wk8 * -1 : 1;
                                }
                            }
                            if (i == 8) {
                                for (let j = 0; j < 10; j++) {
                                    numList[j].zk9 += no == j ? 1 : 0;
                                    numList[j].wk9 += no == j ? numList[j].wk9 * -1 : 1;
                                }
                            }
                            if (i == 9) {
                                for (let j = 0; j < 10; j++) {
                                    numList[j].zk10 += no == j ? 1 : 0;
                                    numList[j].wk10 += no == j ? numList[j].wk10 * -1 : 1;
                                }
                            }
                        }
                    }
                    this.jrhmtjList = numList;
                }
                if (newVal == 'jbzs') {
                    this.hisList.forEach((val) => {
                        console.log(val)
                    })
                    this.jbzsList = this.hisList;
                }
            }
        },
        mounted() {
            this.dateStr = this.$moment(new Date()).format('YYYY-MM-DD');
            if(this.pageTabSelect=='noRoad' || this.pageTabSelect=='sdRoad'
                || this.pageTabSelect=='roadA' || this.pageTabSelect=='sumRoad'

                ){
                this.getLuzhuFun(this.lotteryKey);
            }else if(this.pageTabSelect=='instantDraw' || this.pageTabSelect=='jrhmtj' || this.pageTabSelect=='dsdxls'
                || this.pageTabSelect=='zhfx' || this.pageTabSelect=='lrfx'
                || this.pageTabSelect=='hmlstj' || this.pageTabSelect=='lmsjtj' ){
                this.getHisList();
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
    }
</script>
<style scoped>

</style>
