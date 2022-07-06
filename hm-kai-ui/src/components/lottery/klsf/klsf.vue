<template>
    <div class="bodybox mrcltj">
        <MyHeader></MyHeader>
        <!--主采种开始-->
        <PKtop @luzhuActionFun="getLuzhuFun" @clearFilter="clearFilterInfo" @getHisList="getHisList"></PKtop>
        <!--默认显示-->
        <div v-if="pageTabSelect=='instantDraw'" class="zhlzbox margt20">
            <div class="listhead">
                <div class="listheadl">
                    <span class="lmms"><i>3</i>开奖记录</span>
                </div>
                <div class="listheadr">
                    <div class="listheadrl">
                        <span id="today" :class="dayType==0?'checked':''"  @click="changeDate(0)">今天</span>
                        <span id="yesterday" :class="dayType==-1?'checked':''" @click="changeDate(-1)">昨天</span>
                        <span id="qianday" :class="dayType==-2?'checked':''" @click="changeDate(-2)">前天</span>
                    </div>
                    <div class="listheadrr">
                        <div class="rightime">
                            <datepicker v-model="dateStr" :format="customFormatter" :language="languages.zh"
                                        @selected="customFormatter" name="uniquename"></datepicker>
                        </div>
                        <div>选择日期&nbsp;</div>
                    </div>
                </div>
            </div>
        </div>
        <div v-if="pageTabSelect=='instantDraw'" class="kaijiangjl margt20" style="display: block">
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
                <!--<div id="kjls"><a href="/#/klsfhistory">开奖历史</a></div>-->

            </div>
            <div class="listcontent">
                <!--今日双面统计-->
                <div class="jrsmtj" :style="topLi.jrsm?'display: block':'display: none'">
                    <div class="headtxt"> 今日双面统计</div>
                    <table cellpadding="1" cellspacing="1" border="0">
                        <tbody>
                        <tr>
                            <th rowspan="2">号码</th>
                            <th colspan="4">第一球</th>
                            <th colspan="4">第二球</th>
                            <th colspan="4">第三球</th>
                            <th colspan="4">第四球</th>
                            <th colspan="4">第五球</th>
                            <th colspan="4">第六球</th>
                            <th colspan="4">第七球</th>
                            <th colspan="4">第八球</th>
                        </tr>
                        <tr>
                            <template v-for="indx in 8">
                                <th>单</th>
                                <th>双</th>
                                <th>大</th>
                                <th>小</th>
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
                            <th rowspan="2">球次</th>
                            <th colspan="4">总和</th>
                            <th colspan="2">第一名龙虎</th>
                            <th colspan="2">第二名龙虎</th>
                            <th colspan="2">第三名龙虎</th>
                            <th colspan="2">第四名龙虎</th>
                        </tr>
                        <tr>
                            <th>单</th>
                            <th>双</th>
                            <th>大</th>
                            <th>小</th>
                            <th>龙</th>
                            <th>虎</th>
                            <th>龙</th>
                            <th>虎</th>
                            <th>龙</th>
                            <th>虎</th>
                            <th>龙</th>
                            <th>虎</th>
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
                                <li>{{$t('gdkl10lz_'+item.type)}}&nbsp;&nbsp;<span>{{$t(item.oddsKey.toUpperCase())}}</span>&nbsp;&nbsp;<span :style="item.number>=5?'color:#f11821':''">{{item.number}}&nbsp;&nbsp;</span>期
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
                            <li class="01" @click="numScreenFun('01')" :class="resultNumScreen.find((num)=>{return num=='01'})?'selected':''"><a href="javascript:">号码1</a> <i></i></li>
                            <li class="02" @click="numScreenFun('02')" :class="resultNumScreen.find((num)=>{return num=='02'})?'selected':''"><a href="javascript:">号码2</a> <i></i></li>
                            <li class="03" @click="numScreenFun('03')" :class="resultNumScreen.find((num)=>{return num=='03'})?'selected':''"><a href="javascript:">号码3</a> <i></i></li>
                            <li class="04" @click="numScreenFun('04')" :class="resultNumScreen.find((num)=>{return num=='04'})?'selected':''"><a href="javascript:">号码4</a> <i></i></li>
                            <li class="05" @click="numScreenFun('05')" :class="resultNumScreen.find((num)=>{return num=='05'})?'selected':''"><a href="javascript:">号码5</a> <i></i></li>
                            <li class="06" @click="numScreenFun('06')" :class="resultNumScreen.find((num)=>{return num=='06'})?'selected':''"><a href="javascript:">号码6</a> <i></i></li>
                            <li class="07" @click="numScreenFun('07')" :class="resultNumScreen.find((num)=>{return num=='07'})?'selected':''"><a href="javascript:">号码7</a> <i></i></li>
                            <li class="08" @click="numScreenFun('08')" :class="resultNumScreen.find((num)=>{return num=='08'})?'selected':''"><a href="javascript:">号码8</a> <i></i></li>
                            <li class="09" @click="numScreenFun('09')" :class="resultNumScreen.find((num)=>{return num=='09'})?'selected':''"><a href="javascript:">号码9</a> <i></i></li>
                            <li class="10" @click="numScreenFun('10')" :class="resultNumScreen.find((num)=>{return num=='10'})?'selected':''"><a href="javascript:">号码10</a> <i></i></li>
                            <li class="01" @click="numScreenFun('11')" :class="resultNumScreen.find((num)=>{return num=='11'})?'selected':''"><a href="javascript:">号码11</a> <i></i></li>
                            <li class="02" @click="numScreenFun('12')" :class="resultNumScreen.find((num)=>{return num=='12'})?'selected':''"><a href="javascript:">号码12</a> <i></i></li>
                            <li class="03" @click="numScreenFun('13')" :class="resultNumScreen.find((num)=>{return num=='13'})?'selected':''"><a href="javascript:">号码13</a> <i></i></li>
                            <li class="04" @click="numScreenFun('14')" :class="resultNumScreen.find((num)=>{return num=='14'})?'selected':''"><a href="javascript:">号码14</a> <i></i></li>
                            <li class="05" @click="numScreenFun('15')" :class="resultNumScreen.find((num)=>{return num=='15'})?'selected':''"><a href="javascript:">号码15</a> <i></i></li>
                            <li class="06" @click="numScreenFun('16')" :class="resultNumScreen.find((num)=>{return num=='16'})?'selected':''"><a href="javascript:">号码16</a> <i></i></li>
                            <li class="07" @click="numScreenFun('17')" :class="resultNumScreen.find((num)=>{return num=='17'})?'selected':''"><a href="javascript:">号码17</a> <i></i></li>
                            <li class="08" @click="numScreenFun('18')" :class="resultNumScreen.find((num)=>{return num=='18'})?'selected':''"><a href="javascript:">号码18</a> <i></i></li>
                            <li class="09" @click="numScreenFun('19')" :class="resultNumScreen.find((num)=>{return num=='19'})?'selected':''"><a href="javascript:">号码19</a> <i></i></li>
                            <li class="10" @click="numScreenFun('20')" :class="resultNumScreen.find((num)=>{return num=='20'})?'selected':''"><a href="javascript:">号码20</a> <i></i></li>
                            <!--<li class="reset">
                                              还原
                                          </li>-->
                        </ul>
                    </div>
                    <div class="head head2">
                        <ul class="zoushimap" id="daxiaodsfb">
                            <li @click="numTypeScreenFun('odd')" :class="resultTypeScreen=='odd'?'selected':''" id="dannum"><a href="javascript:">单</a> <i></i></li>
                            <li @click="numTypeScreenFun('even')" :class="resultTypeScreen=='even'?'selected':''" id="shuangnum"><a href="javascript:">双</a> <i></i></li>
                            <li @click="numTypeScreenFun('over')" :class="resultTypeScreen=='over'?'selected':''" id="danum"><a href="javascript:">大</a> <i></i></li>
                            <li @click="numTypeScreenFun('under')" :class="resultTypeScreen=='under'?'selected':''" id="xiaonum"><a href="javascript:">小</a> <i></i></li>
                            <li @click="numTypeScreenFun('pair')" :class="resultTypeScreen=='pair'?'selected':''" id="duizinum"><a href="javascript:">对子号</a> <i></i></li>
                            <li @click="clearResultScreenFun" class="reset"> 还原</li>
                        </ul>
                    </div>
                </div>
                <!--主显示-->
                <div class="jrsmhmtj jrsmhmtj_kai klsf" id="jrsmhmtj">
                    <table id="jrsmhmtjTab" cellpadding="1" cellspacing="1" border="0"
                           style="background: rgb(212, 212, 212);">
                        <tbody>
                        <tr>
                            <th>时间</th>
                            <th>期数</th>
                            <th id="numberbtn" class="numberbtn"><span @click="resultShowType='num';" id="xshm" :class="resultShowType=='num'?'spanselect':''">显示号码</span><span
                                id="xsdx" @click="resultShowType='numOu';" :class="resultShowType=='numOu'?'spanselect':''">显示大小</span><span @click="resultShowType='numOe';" id="xsds" :class="resultShowType=='numOe'?'spanselect':''">显示单双</span></th>
                            <th colspan="3">总和</th>
                            <th >尾大小</th>
                            <th colspan="4">龙虎</th>
                        </tr>
                        <tr v-for="(item,index) in hisList">
                            <td>{{item.actionTimeStr}}</td>
                            <td>{{item.gameNo}}</td>
                            <td class="blueqiu">
                                <ul class="klsf_kaiul" v-for="(num,i) in item.result">
                                    <!--快乐十分这里要注意啦 19 20 两个号 要加 numredkong -->

                                    <!--幸运农场这里显示不一样-->
<!--                                    <li class="ncnum01" style="color:#ffffff;border:none;margin-right:13px;margin-left:0;margin-top:5px"></li>-->
                                    <li  :class="resultClassCss(num,index,i)" @click="numScreenFun(num)" style="color:#012537"><i v-show="resultShowType=='num' && lotteryKey!='xync'">{{num}}</i></li>
                                </ul>
                            </td>
                            <td>{{item.gyh15lh.zh}}</td>
                            <td :style="item.gyh15lh.heOE=='EVEN'?'color:red':''">{{$t(item.gyh15lh.heOE)}}</td>
                            <td :style="item.gyh15lh.heOU=='OVER'?'color:red':''">{{$t(item.gyh15lh.heOU)}}</td>
                            <td :style="item.gyh15lh.sumOU=='weiover'?'color:red':''">{{$t(item.gyh15lh.sumOU)}}</td>
                            <td :style="item.gyh15lh.no1DT=='DRAGON'?'color:red':''">{{$t(item.gyh15lh.no1DT)}}</td>
                            <td :style="item.gyh15lh.no2DT=='DRAGON'?'color:red':''">{{$t(item.gyh15lh.no2DT)}}</td>
                            <td :style="item.gyh15lh.no3DT=='DRAGON'?'color:red':''">{{$t(item.gyh15lh.no3DT)}}</td>
                            <td :style="item.gyh15lh.no4DT=='DRAGON'?'color:red':''">{{$t(item.gyh15lh.no4DT)}}</td>
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
                                    <a >{{$t(index)}}</a>
                                </li>
                            </template>


                            <li @click="roadAAboutNoAll(true)" class="zhcheckall">全选</li>
                            <li @click="roadAAboutNoAll(false)" class="zhclear">清空</li>
                        </ul>
                        <ul class="zhmslz">
                            <li class="title">筛选路珠：</li>
                            <template v-for="(item,index) in roadA.filters.aboutCheckRoadBoxList">
                                <li @click="roadAAboutFilterRoadFun(index)" :class="item?'lztype checked':'lztype'"><i>2</i>
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
                                <li @click="roadAAloneFilterRoadFun(index)" :class="item?'lztype checked':'lztype'"><i>2</i>
                                    <a href="javascript:void(0)">{{$t(index)}}</a>
                                </li>
                            </template>


                            <li @click="roadAAloneNoAll(true)" class="zhcheckall_a">全选</li>
                            <li @click="roadAAloneNoAll(false)" class="zhclear_a">清空</li>
                        </ul>
                        <ul class="xuanzhemc">
                            <li class="mctitle">选择名次：</li>
                            <template v-for="(item,index) in 8">
                                <li @click="roadAAloneNoFilterFun('no'+item)" :class="roadA.filters.aloneSelectNo=='no'+item?'sinli selected':'sinli'"><span>1</span>
                                    <a href="javascript:void(0)">{{$t('gdkl10lz_no'+item)}}</a>
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
                                <a>大小</a>
                            </li>
                            <li @click="roadA.filters.lmSelectRoad='oe'" :class="roadA.filters.lmSelectRoad=='oe'?'lztype selected':'lztype'"><span>1</span>
                                <a>单双</a>
                            </li>
                            <li @click="roadA.filters.lmSelectRoad='dtt'" :class="roadA.filters.lmSelectRoad=='dtt'?'lztype selected':'lztype'"><span>3</span>
                                <a>龙虎</a>
                            </li>
                            <li @click="roadA.filters.lmSelectRoad='wsou'" :class="roadA.filters.lmSelectRoad=='wsou'?'lztype selected':'lztype'"><span>3</span>
                                <a>尾数大小</a>
                            </li>
                            <li @click="roadA.filters.lmSelectRoad='heoe'" :class="roadA.filters.lmSelectRoad=='heoe'?'lztype selected':'lztype'"><span>3</span>
                                <a>合数单双</a>
                            </li>
                            <li @click="roadA.filters.lmSelectRoad='zfb'" :class="roadA.filters.lmSelectRoad=='zfb'?'lztype selected':'lztype'"><span>3</span>
                                <a>中发白</a>
                            </li>
                            <li @click="roadA.filters.lmSelectRoad='fw'" :class="roadA.filters.lmSelectRoad=='fw'?'lztype selected':'lztype'"><span>3</span>
                                <a>方位</a>
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
                                        <table v-if="(roadA.roadAModeSelect=='about' && roadA.filters.aboutCheckNoBoxList[noIndex] && ((roadA.filters.aboutCheckRoadBoxList.ou && (arrIndex.indexOf('ou')!=-1 && arrIndex.indexOf('wsou')==-1)) || (roadA.filters.aboutCheckRoadBoxList.oe && (arrIndex.indexOf('oe')!=-1 && arrIndex.indexOf('heoe')==-1)) || (roadA.filters.aboutCheckRoadBoxList.dtt && arrIndex.indexOf('dtt')!=-1) || (roadA.filters.aboutCheckRoadBoxList.wsou && arrIndex.indexOf('ws')!=-1) || (roadA.filters.aboutCheckRoadBoxList.heoe && arrIndex.indexOf('heoe')!=-1) || (roadA.filters.aboutCheckRoadBoxList.zfb && arrIndex.indexOf('zfb')!=-1) || (roadA.filters.aboutCheckRoadBoxList.fw && arrIndex.indexOf('fw')!=-1)))
                                                     ||(roadA.roadAModeSelect=='alone' && roadA.filters.aloneSelectNo==noIndex && ((roadA.filters.aloneCheckRoadBoxList.ou && noIndex+'ou' == arrIndex) || (roadA.filters.aloneCheckRoadBoxList.oe && noIndex+'oe' == arrIndex)  || (roadA.filters.aloneCheckRoadBoxList.dtt && noIndex + 'dtt' == arrIndex) || (roadA.filters.aloneCheckRoadBoxList.heoe && noIndex + 'heoe' == arrIndex) || (roadA.filters.aloneCheckRoadBoxList.wsou && noIndex + 'wsou' == arrIndex)  || (roadA.filters.aloneCheckRoadBoxList.zfb && noIndex + 'zfb' == arrIndex) || (roadA.filters.aloneCheckRoadBoxList.fw && noIndex + 'fw' == arrIndex)))
                                                     ||(roadA.roadAModeSelect=='lm' && roadA.filters.lmCheckNumBoxList[noIndex] && ((noIndex+roadA.filters.lmSelectRoad == arrIndex || (arrIndex=='sumws' && roadA.filters.lmSelectRoad=='wsou'))))" class="lz_table_head  g_w1000">
                                            <tbody>
                                            <tr>
                                                <td>连续出现&nbsp;<input class="txtNum" v-model="roadA.filters['numTwo'+arrIndex]" type="number" min="1">&nbsp;次以上&nbsp;
                                                    <select v-model="roadA.filters['selectTwo'+arrIndex]" class="secType">
                                                        <option :value="(arrIndex.indexOf('ou')!=-1 && arrIndex.indexOf('wsou')==-1)?'over':(arrIndex.indexOf('oe')!=-1 && arrIndex.indexOf('heoe')==-1)?'odd':arrIndex.indexOf('dtt')!=-1?'dragon':arrIndex.indexOf('ws')!=-1?'over':arrIndex.indexOf('heoe')!=-1?'odd':arrIndex.indexOf('zfb')!=-1?'zhong':arrIndex.indexOf('fw')!=-1?'east':''">{{(arrIndex.indexOf('ou')!=-1 && arrIndex.indexOf('wsou')==-1)?'大':(arrIndex.indexOf('oe')!=-1 && arrIndex.indexOf('heoe')==-1)?'单':arrIndex.indexOf('dtt')!=-1?'龙':arrIndex.indexOf('ws')!=-1?'尾大':arrIndex.indexOf('heoe')!=-1?'合单':arrIndex.indexOf('zfb')!=-1?'中':arrIndex.indexOf('fw')!=-1?'东':''}}</option>
                                                        <option :value="(arrIndex.indexOf('ou')!=-1 && arrIndex.indexOf('wsou')==-1)?'under':(arrIndex.indexOf('oe')!=-1 && arrIndex.indexOf('heoe')==-1)?'even':arrIndex.indexOf('dtt')!=-1?'tiger':arrIndex.indexOf('ws')!=-1?'under':arrIndex.indexOf('heoe')!=-1?'even':arrIndex.indexOf('zfb')!=-1?'fa':arrIndex.indexOf('fw')!=-1?'south':''">{{(arrIndex.indexOf('ou')!=-1 && arrIndex.indexOf('wsou')==-1)?'小':(arrIndex.indexOf('oe')!=-1 && arrIndex.indexOf('heoe')==-1)?'双':arrIndex.indexOf('dtt')!=-1?'虎':arrIndex.indexOf('ws')!=-1?'尾小':arrIndex.indexOf('heoe')!=-1?'合双':arrIndex.indexOf('zfb')!=-1?'发':arrIndex.indexOf('fw')!=-1?'南':''}}</option>
                                                        <option v-if="arrIndex.indexOf('zfb')!=-1 || arrIndex.indexOf('fw')!=-1" :value="arrIndex.indexOf('zfb')!=-1?'bai':arrIndex.indexOf('fw')!=-1?'west':''">{{arrIndex.indexOf('zfb')!=-1?'白':arrIndex.indexOf('fw')!=-1?'西':''}}</option>
                                                        <option v-if="arrIndex.indexOf('fw')!=-1" value="north">北</option>
                                                    </select>
                                                    <input type="button" class="btnCheck" @click="roadATableFilterFun(arrIndex,noIndex)" value="确定">出现的次数为: <span class="sec_count">{{roadA.filters['showNum'+arrIndex]}}</span>
                                                </td>
                                                <td></td>
                                                <td class="righttd">
                                                    <span class="now_l"></span><span class="jinri">{{totalDateTime}}</span>&nbsp;累计：{{(arrIndex.indexOf('ou')!=-1 && arrIndex.indexOf('wsou')==-1)?'大':(arrIndex.indexOf('oe')!=-1 && arrIndex.indexOf('heoe')==-1)?'单':arrIndex.indexOf('dtt')!=-1?'龙':arrIndex.indexOf('ws')!=-1?'尾大':arrIndex.indexOf('heoe')!=-1?'合单':''}}<template v-if="arrIndex.indexOf('zfb')!=-1">中<span class="count left_count" data="">({{roadA[noIndex].zhong}})&nbsp;</span>发<span class="count left_count" data="">({{roadA[noIndex].fa}})&nbsp;</span>白<span class="count left_count" data="">({{roadA[noIndex].bai}})&nbsp;</span></template><template v-if="arrIndex.indexOf('fw')!=-1">东<span class="count left_count" data="">({{roadA[noIndex].east}})&nbsp;</span>南<span class="count left_count" data="">({{roadA[noIndex].south}})&nbsp;</span>西<span class="count left_count" data="">({{roadA[noIndex].west}})&nbsp;</span>北<span class="count left_count" data="">({{roadA[noIndex].north}})&nbsp;</span></template><template v-if="arrIndex.indexOf('zfb')==-1 && arrIndex.indexOf('fw')==-1">(<span class="count left_count" data="">{{(arrIndex.indexOf('ou')!=-1 && arrIndex.indexOf('wsou')==-1)?roadA[noIndex].over:(arrIndex.indexOf('heoe')==-1 && arrIndex.indexOf('oe')!=-1)?roadA[noIndex].odd:arrIndex.indexOf('dtt')!=-1?roadA[noIndex].dragon:arrIndex.indexOf('wsou')!=-1?roadA[noIndex].weiover:arrIndex=='sumws'?roadA[noIndex].weiover:arrIndex.indexOf('heoe')!=-1?roadA[noIndex].heodd:''}}</span>)&nbsp;&nbsp;{{(arrIndex.indexOf('ou')!=-1 && arrIndex.indexOf('wsou')==-1)?'小':(arrIndex.indexOf('oe')!=-1 && arrIndex.indexOf('heoe')==-1)?'双':arrIndex.indexOf('dtt')!=-1?'虎':arrIndex.indexOf('ws')!=-1?'尾小':arrIndex.indexOf('heoe')!=-1?'合双':''}}(<span class="count right_count" data="单">{{(arrIndex.indexOf('ou')!=-1 && arrIndex.indexOf('wsou')==-1)?roadA[noIndex].under:(arrIndex.indexOf('oe')!=-1 && arrIndex.indexOf('heoe')==-1)?roadA[noIndex].even:arrIndex.indexOf('dtt')!=-1?roadA[noIndex].tiger:arrIndex.indexOf('wsou')!=-1?roadA[noIndex].weiunder:arrIndex=='sumws'?roadA[noIndex].weiunder:arrIndex.indexOf('heoe')!=-1?roadA[noIndex].heeven:''}}</span>)</template><template v-if="arrIndex=='sumou' && roadA[noIndex].draw">&nbsp;和(<span class="count right_count" data="单">{{roadA[noIndex].draw}}</span>)</template>
                                                    <span class="important"><span class="ifgj">{{$t('gdkl10lz_'+noIndex)}}</span>&nbsp;<span class="ifds">{{(arrIndex.indexOf('oe')!=-1 && arrIndex.indexOf('heoe')==-1)?$t('oe'):(arrIndex.indexOf('ou')!=-1 && arrIndex.indexOf('wsou')==-1)?$t('ou'):arrIndex.indexOf('dtt')!=-1?$t('dtt'):arrIndex.indexOf('ws')!=-1?$t('wsou'):arrIndex.indexOf('heoe')!=-1?$t('heoe'):arrIndex.indexOf('zfb')!=-1?$t('zfb'):arrIndex.indexOf('fw')!=-1?$t('fwei'):''}}</span>&nbsp;&nbsp;&nbsp;最新</span>
                                                </td>
                                                <td class="tobottom"></td>
                                            </tr>
                                            </tbody>
                                        </table>
                                        <div v-if="roadA.roadAModeSelect=='about' && roadA.filters.aboutCheckNoBoxList[noIndex] && ((roadA.filters.aboutCheckRoadBoxList.ou && (arrIndex.indexOf('ou')!=-1 && arrIndex.indexOf('wsou')==-1)) || (roadA.filters.aboutCheckRoadBoxList.oe && (arrIndex.indexOf('oe')!=-1 && arrIndex.indexOf('heoe')==-1)) || (roadA.filters.aboutCheckRoadBoxList.dtt && arrIndex.indexOf('dtt')!=-1) || (roadA.filters.aboutCheckRoadBoxList.wsou && arrIndex.indexOf('ws')!=-1) || (roadA.filters.aboutCheckRoadBoxList.heoe && arrIndex.indexOf('heoe')!=-1) || (roadA.filters.aboutCheckRoadBoxList.zfb && arrIndex.indexOf('zfb')!=-1) || (roadA.filters.aboutCheckRoadBoxList.fw && arrIndex.indexOf('fw')!=-1))
                                                  ||(roadA.roadAModeSelect=='alone' && roadA.filters.aloneSelectNo==noIndex && ((roadA.filters.aloneCheckRoadBoxList.ou && noIndex+'ou' == arrIndex) || (roadA.filters.aloneCheckRoadBoxList.oe && noIndex+'oe' == arrIndex)  || (roadA.filters.aloneCheckRoadBoxList.dtt && noIndex + 'dtt' == arrIndex) || (roadA.filters.aloneCheckRoadBoxList.heoe && noIndex + 'heoe' == arrIndex) || (roadA.filters.aloneCheckRoadBoxList.wsou && noIndex + 'wsou' == arrIndex)  || (roadA.filters.aloneCheckRoadBoxList.zfb && noIndex + 'zfb' == arrIndex) || (roadA.filters.aloneCheckRoadBoxList.fw && noIndex + 'fw' == arrIndex)))
                                                    ||(roadA.roadAModeSelect=='lm' && roadA.filters.lmCheckNumBoxList[noIndex] && ((noIndex+roadA.filters.lmSelectRoad == arrIndex || (arrIndex=='sumws' && roadA.filters.lmSelectRoad=='wsou'))))" class="item_con" dir="rtl" style="background-position-x: 0px;">
                                            <table class="lz_table_con" border="0" cellpadding="1" cellspacing="1">
                                                <tbody>
                                                <tr class="tablebox">
                                                    <template v-for="(list,index) in noArr">
                                                        <template v-for="(item,i) in list">
                                                            <td :class="i%2==0?'bgcolor':''" :style="item[0] == roadA.filters['select'+arrIndex] && item.length>=roadA.filters['num'+arrIndex] && roadA.filters['btn'+arrIndex]?'color: rgb(255, 255, 255); background-color: rgb(253, 173, 86);':''">
                                                                <template v-for="(num,k) in item">
                                                                    <p :style="item[0]=='even'||item[0]=='over' || item[0] == 'dragon' || item[0] == 'zhong'  || item[0] == 'south'?'color: rgb(241, 2, 11); display: block;':'font-weight: normal;'"
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
                <div class="listbox_spread" id="listbox_spread">
                    <div id="spreadDiv" style="height: 28px; padding: 0px; overflow: hidden;">
                        <p id="prep"><i>说明：</i>总和85至132为大，36至83为小，84为和。<span></span></p>
                    </div>
                </div>
                <div class="listcontent">
                    <div class="box">
                        <div class="dsLZ">
                            <template v-for="(noList,noIndex) in luzhuList">
                                <template v-for="(arrList,arrIndex) in noList">
                                    <table v-if="noIndex=='sum'"
                                           class="lz_table_head  g_w1000">
                                        <tbody>
                                        <tr>
                                            <td>连续出现&nbsp;<input class="txtNum" v-model="sumRoad.filters['numTwo'+arrIndex]"
                                                                 type="number" min="1">&nbsp;次以上&nbsp;
                                                <select v-model="sumRoad.filters['selectTwo'+arrIndex]" class="secType">
                                                    <option :value="(arrIndex.indexOf('ou')!=-1 && arrIndex.indexOf('wsou')==-1)?'over':(arrIndex.indexOf('oe')!=-1 && arrIndex.indexOf('heoe')==-1)?'odd':arrIndex.indexOf('dtt')!=-1?'dragon':arrIndex.indexOf('ws')!=-1?'over':arrIndex.indexOf('heoe')!=-1?'odd':arrIndex.indexOf('zfb')!=-1?'zhong':arrIndex.indexOf('fw')!=-1?'east':''">{{(arrIndex.indexOf('ou')!=-1 && arrIndex.indexOf('wsou')==-1)?'大':(arrIndex.indexOf('oe')!=-1 && arrIndex.indexOf('heoe')==-1)?'单':arrIndex.indexOf('dtt')!=-1?'龙':arrIndex.indexOf('ws')!=-1?'尾大':arrIndex.indexOf('heoe')!=-1?'合单':arrIndex.indexOf('zfb')!=-1?'中':arrIndex.indexOf('fw')!=-1?'东':''}}</option>
                                                    <option :value="(arrIndex.indexOf('ou')!=-1 && arrIndex.indexOf('wsou')==-1)?'under':(arrIndex.indexOf('oe')!=-1 && arrIndex.indexOf('heoe')==-1)?'even':arrIndex.indexOf('dtt')!=-1?'tiger':arrIndex.indexOf('ws')!=-1?'under':arrIndex.indexOf('heoe')!=-1?'even':arrIndex.indexOf('zfb')!=-1?'fa':arrIndex.indexOf('fw')!=-1?'south':''">{{(arrIndex.indexOf('ou')!=-1 && arrIndex.indexOf('wsou')==-1)?'小':(arrIndex.indexOf('oe')!=-1 && arrIndex.indexOf('heoe')==-1)?'双':arrIndex.indexOf('dtt')!=-1?'虎':arrIndex.indexOf('ws')!=-1?'尾小':arrIndex.indexOf('heoe')!=-1?'合双':arrIndex.indexOf('zfb')!=-1?'发':arrIndex.indexOf('fw')!=-1?'南':''}}</option>
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
                                    <div v-if="noIndex=='sum'"
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

        <!--中发白路珠-->
        <div v-if="pageTabSelect=='zfbRoad'" class="zhlzbox margt20" style="display:block">
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
            <!--中发白路珠显示-->
            <div class="listbox">
                <div class="listbox_spread" id="listbox_spread">
                    <div id="spreadDiv" style="height: 28px; padding: 0px; overflow: hidden;">
                        <p id="prep"><i>说明：</i>1 中：开出号码为01、02、03、04、05、06、07<span></span></p>
                        <p id="prep"><i>说明：</i>2 发：开出号码为08、09、10、11、12、13、14<span></span></p>
                        <p id="prep"><i>说明：</i>1 白：开出号码为15、16、17、18、19、20<span></span></p>
                    </div>
                </div>

                <div class="checkbox">
                    <!--综合模式筛选-->
                    <div id="zhms" class="checkbtnzh">
                        <ul class="zhmsmc">
                            <li class="title">筛选球号：</li>
                            <template v-for="(item,index) in zfbRoad.filters.checkBoxList">
                                <li @click="zfbRoadCheckBoxFun(index)" :class="item?'sinli checked':'sinli'"><i>1</i>
                                    <a >{{$t('gdkl10lz_'+index)}}</a>
                                </li>
                            </template>


                            <li @click="zfbRoadCheckBoxAllHandle(true)" class="zhcheckall">全选</li>
                            <li @click="zfbRoadCheckBoxAllHandle(false)" class="zhclear">清空</li>
                        </ul>
                    </div>
                </div>
                <div class="listcontent">
                    <div class="box">
                        <div class="dsLZ">
                            <template v-for="(noList,noIndex) in luzhuList">
                                <template v-for="(arrList,arrIndex) in noList">
                                    <table v-if="arrIndex.indexOf('zfb')!=-1 && zfbRoad.filters.checkBoxList[noIndex]"
                                           class="lz_table_head  g_w1000">
                                        <tbody>
                                        <tr>
                                            <td>连续出现&nbsp;<input class="txtNum" v-model="zfbRoad.filters['numTwo'+noIndex]"
                                                                 type="number" min="1">&nbsp;次以上&nbsp;
                                                <select v-model="zfbRoad.filters['selectTwo'+noIndex]" class="secType">
                                                    <option value="zhong">中</option>
                                                    <option value="fa">发</option>
                                                    <option value="bai">白</option>
                                                </select>
                                                <input type="button" class="btnCheck"
                                                       @click="zfbRoadTableFilterFun(arrIndex,noIndex)"
                                                       value="确定">出现的次数为: <span class="sec_count">{{zfbRoad.filters['showNum'+noIndex]}}</span>
                                            </td>
                                            <td></td>
                                            <td class="righttd">
                                                <span class="now_l"></span><span class="jinri">{{totalDateTime}}</span>&nbsp;累计：中(<span
                                                class="count left_count" data="中">{{zfbRoad[noIndex].zhong}}</span>)&nbsp;&nbsp;发(<span
                                                class="count right_count" data="发">{{zfbRoad[noIndex].fa}}</span>)
                                                &nbsp;&nbsp;白(<span
                                                class="count right_count" data="白">{{zfbRoad[noIndex].bai}}</span>)
                                                <span class="important"><span class="ifgj">{{$t('gdkl10lz_'+noIndex)}}</span>&nbsp;<span
                                                    class="ifds">中发白</span>&nbsp;&nbsp;&nbsp;最新</span>
                                            </td>
                                            <td class="tobottom"></td>
                                        </tr>
                                        </tbody>
                                    </table>
                                    <div v-if="arrIndex.indexOf('zfb')!=-1 && zfbRoad.filters.checkBoxList[noIndex]"
                                         class="item_con" dir="rtl" style="background-position-x: -29px;">
                                        <table class="lz_table_con" border="0" cellpadding="1" cellspacing="1">
                                            <tbody>
                                            <tr class="tablebox">

                                                <template v-for="(list,index) in arrList">
                                                    <template v-for="(item,g) in list">

                                                        <td :class="g%2==0?'bgcolor':''"
                                                            :style="item[0] == zfbRoad.filters['select'+noIndex] && item.length>=zfbRoad.filters['num'+noIndex] && zfbRoad.filters['btn'+noIndex]?'color: rgb(255, 255, 255); background-color: rgb(253, 173, 86);':''">
                                                            <template v-for="(num,k) in item">
                                                                <p :style="item[0]=='zhong'?'color: rgb(241, 2, 11); display: block;':'font-weight: normal;'"
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


        <!--合数单双路珠-->
        <div v-if="pageTabSelect=='heoeRoad'" class="zhlzbox margt20" style="display:block">
            <div class="listhead">
                <div class="listheadl">
                    <span class="lmms">合数单双路珠</span>
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
            <!--合数单双路珠-->
            <div class="listbox">
                <div class="checkbox">
                    <!--综合模式筛选-->
                    <div id="zhms" class="checkbtnzh">
                        <ul class="zhmsmc">
                            <li class="title">筛选球号：</li>
                            <template v-for="(item,index) in heoeRoad.filters.checkBoxList">
                                <li @click="heoeRoadCheckBoxFun(index)" :class="item?'sinli checked':'sinli'"><i>1</i>
                                    <a >{{$t('gdkl10lz_'+index)}}</a>
                                </li>
                            </template>


                            <li @click="heoeRoadCheckBoxAllHandle(true)" class="zhcheckall">全选</li>
                            <li @click="heoeRoadCheckBoxAllHandle(false)" class="zhclear">清空</li>
                        </ul>
                    </div>
                </div>
                <div class="listcontent">
                    <div class="box">
                        <div class="dsLZ">
                            <template v-for="(noList,noIndex) in luzhuList">
                                <template v-for="(arrList,arrIndex) in noList">
                                    <table v-if="arrIndex.indexOf('heoe')!=-1 && heoeRoad.filters.checkBoxList[noIndex]"
                                           class="lz_table_head  g_w1000">
                                        <tbody>
                                        <tr>
                                            <td>连续出现&nbsp;<input class="txtNum" v-model="heoeRoad.filters['numTwo'+noIndex]"
                                                                 type="number" min="1">&nbsp;次以上&nbsp;
                                                <select v-model="heoeRoad.filters['selectTwo'+noIndex]" class="secType">
                                                    <option value="odd">合单</option>
                                                    <option value="even">合双</option>
                                                </select>
                                                <input type="button" class="btnCheck"
                                                       @click="heoeRoadTableFilterFun(arrIndex,noIndex)"
                                                       value="确定">出现的次数为: <span class="sec_count">{{heoeRoad.filters['showNum'+noIndex]}}</span>
                                            </td>
                                            <td></td>
                                            <td class="righttd">
                                                <span class="now_l"></span><span class="jinri">{{totalDateTime}}</span>&nbsp;累计：合单(<span
                                                class="count left_count" data="合单">{{heoeRoad[noIndex].odd}}</span>)&nbsp;&nbsp;合双(<span
                                                class="count right_count" data="发">{{heoeRoad[noIndex].even}}</span>)

                                                <span class="important"><span class="ifgj">{{$t('gdkl10lz_'+noIndex)}}</span>&nbsp;<span
                                                    class="ifds">合单合双</span>&nbsp;&nbsp;&nbsp;最新</span>
                                            </td>
                                            <td class="tobottom"></td>
                                        </tr>
                                        </tbody>
                                    </table>
                                    <div v-if="arrIndex.indexOf('heoe')!=-1 && heoeRoad.filters.checkBoxList[noIndex]"
                                         class="item_con" dir="rtl" style="background-position-x: -29px;">
                                        <table class="lz_table_con" border="0" cellpadding="1" cellspacing="1">
                                            <tbody>
                                            <tr class="tablebox">

                                                <template v-for="(list,index) in arrList">
                                                    <template v-for="(item,g) in list">

                                                        <td :class="item[0]=='even'?'bgcolor':''"
                                                            :style="item[0] == heoeRoad.filters['select'+noIndex] && item.length>=heoeRoad.filters['num'+noIndex] && heoeRoad.filters['btn'+noIndex]?'color: rgb(255, 255, 255); background-color: rgb(253, 173, 86);':''">
                                                            <template v-for="(num,k) in item">
                                                                <p :style="item[0]=='even'?'color: rgb(241, 2, 11); display: block;':'font-weight: normal;'"
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

        <!--尾数大小路珠-->
        <div v-if="pageTabSelect=='wsouRoad'" class="zhlzbox margt20" style="display:block">
            <div class="listhead">
                <div class="listheadl">
                    <span class="lmms">尾数大小路珠</span>
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
            <!--尾数大小路珠-->
            <div class="listbox">
                <div class="listbox_spread" id="listbox_spread">
                    <div id="spreadDiv" style="height: 28px; padding: 0px; overflow: hidden;">
                        <p id="prep"><i>说明：</i>尾数大指开出号码的个位大于或等于5为尾大，小于或等于4为尾小.<span></span></p>
                    </div>
                </div>
                <div class="checkbox">
                    <!--综合模式筛选-->
                    <div id="zhms" class="checkbtnzh">
                        <ul class="zhmsmc">
                            <li class="title">筛选球号：</li>
                            <template v-for="(item,index) in wsouRoad.filters.checkBoxList">
                                <li @click="wsouRoadCheckBoxFun(index)" :class="item?'sinli checked':'sinli'"><i>1</i>
                                    <a >{{$t('gdkl10lz_'+index)}}</a>
                                </li>
                            </template>


                            <li @click="wsouRoadCheckBoxAllHandle(true)" class="zhcheckall">全选</li>
                            <li @click="wsouRoadCheckBoxAllHandle(false)" class="zhclear">清空</li>
                        </ul>
                    </div>
                </div>
                <div class="listcontent">
                    <div class="box">
                        <div class="dsLZ">
                            <template v-for="(noList,noIndex) in luzhuList">
                                <template v-for="(arrList,arrIndex) in noList">
                                    <table v-if="arrIndex.indexOf('wsou')!=-1 && wsouRoad.filters.checkBoxList[noIndex]"
                                           class="lz_table_head  g_w1000">
                                        <tbody>
                                        <tr>
                                            <td>连续出现&nbsp;<input class="txtNum" v-model="wsouRoad.filters['numTwo'+noIndex]"
                                                                 type="number" min="1">&nbsp;次以上&nbsp;
                                                <select v-model="wsouRoad.filters['selectTwo'+noIndex]" class="secType">
                                                    <option value="over">尾大</option>
                                                    <option value="under">尾小</option>
                                                </select>
                                                <input type="button" class="btnCheck"
                                                       @click="wsouRoadTableFilterFun(arrIndex,noIndex)"
                                                       value="确定">出现的次数为: <span class="sec_count">{{wsouRoad.filters['showNum'+noIndex]}}</span>
                                            </td>
                                            <td></td>
                                            <td class="righttd">
                                                <span class="now_l"></span><span class="jinri">{{totalDateTime}}</span>&nbsp;累计：尾大(<span
                                                class="count left_count" data="尾大">{{wsouRoad[noIndex].over}}</span>)&nbsp;&nbsp;尾小(<span
                                                class="count right_count" data="尾小">{{wsouRoad[noIndex].under}}</span>)

                                                <span class="important"><span class="ifgj">{{$t('gdkl10lz_'+noIndex)}}</span>&nbsp;<span
                                                    class="ifds">尾大尾小</span>&nbsp;&nbsp;&nbsp;最新</span>
                                            </td>
                                            <td class="tobottom"></td>
                                        </tr>
                                        </tbody>
                                    </table>
                                    <div v-if="arrIndex.indexOf('wsou')!=-1 && wsouRoad.filters.checkBoxList[noIndex]"
                                         class="item_con" dir="rtl" style="background-position-x: -29px;">
                                        <table class="lz_table_con" border="0" cellpadding="1" cellspacing="1">
                                            <tbody>
                                            <tr class="tablebox">

                                                <template v-for="(list,index) in arrList">
                                                    <template v-for="(item,g) in list">

                                                        <td :class="item[0]=='over'?'bgcolor':''"
                                                            :style="item[0] == wsouRoad.filters['select'+noIndex] && item.length>=wsouRoad.filters['num'+noIndex] && wsouRoad.filters['btn'+noIndex]?'color: rgb(255, 255, 255); background-color: rgb(253, 173, 86);':''">
                                                            <template v-for="(num,k) in item">
                                                                <p :style="item[0]=='over'?'color: rgb(241, 2, 11); display: block;':'font-weight: normal;'"
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
            <!--龙虎路珠-->
            <div class="listbox">
                <div class="checkbox">
                    <!--综合模式筛选-->
                    <div id="zhms" class="checkbtnzh">
                        <ul class="zhmsmc">
                            <li class="title">筛选球号：</li>
                            <template v-for="(item,index) in dtRoad.filters.checkBoxList">
                                <li @click="dtRoadCheckBoxFun(index)" :class="item?'sinli checked':'sinli'"><i>1</i>
                                    <a >{{$t('gdkl10lz_'+index)}}</a>
                                </li>
                            </template>


                            <li @click="dtRoadCheckBoxAllHandle(true)" class="zhcheckall">全选</li>
                            <li @click="dtRoadCheckBoxAllHandle(false)" class="zhclear">清空</li>
                        </ul>
                    </div>
                </div>
                <div class="listcontent">
                    <div class="box">
                        <div class="dsLZ">
                            <template v-for="(noList,noIndex) in luzhuList">
                                <template v-for="(arrList,arrIndex) in noList">
                                    <table v-if="arrIndex.indexOf('dtt')!=-1 && dtRoad.filters.checkBoxList[noIndex]"
                                           class="lz_table_head  g_w1000">
                                        <tbody>
                                        <tr>
                                            <td>连续出现&nbsp;<input class="txtNum" v-model="dtRoad.filters['numTwo'+noIndex]"
                                                                 type="number" min="1">&nbsp;次以上&nbsp;
                                                <select v-model="dtRoad.filters['selectTwo'+noIndex]" class="secType">
                                                    <option value="dragon">龙</option>
                                                    <option value="tiger">虎</option>
                                                </select>
                                                <input type="button" class="btnCheck"
                                                       @click="dtRoadTableFilterFun(arrIndex,noIndex)"
                                                       value="确定">出现的次数为: <span class="sec_count">{{dtRoad.filters['showNum'+noIndex]}}</span>
                                            </td>
                                            <td></td>
                                            <td class="righttd">
                                                <span class="now_l"></span><span class="jinri">{{totalDateTime}}</span>&nbsp;累计：龙(<span
                                                class="count left_count" data="龙">{{dtRoad[noIndex].dragon}}</span>)&nbsp;&nbsp;虎(<span
                                                class="count right_count" data="虎">{{dtRoad[noIndex].tiger}}</span>)

                                                <span class="important"><span class="ifgj">{{$t('gdkl10lz_'+noIndex)}}</span>&nbsp;<span
                                                    class="ifds">龙虎</span>&nbsp;&nbsp;&nbsp;最新</span>
                                            </td>
                                            <td class="tobottom"></td>
                                        </tr>
                                        </tbody>
                                    </table>
                                    <div v-if="arrIndex.indexOf('dtt')!=-1 && dtRoad.filters.checkBoxList[noIndex]"
                                         class="item_con" dir="rtl" style="background-position-x: -29px;">
                                        <table class="lz_table_con" border="0" cellpadding="1" cellspacing="1">
                                            <tbody>
                                            <tr class="tablebox">

                                                <template v-for="(list,index) in arrList">
                                                    <template v-for="(item,g) in list">

                                                        <td :class="item[0]=='dragon'?'bgcolor':''"
                                                            :style="item[0] == dtRoad.filters['select'+noIndex] && item.length>=dtRoad.filters['num'+noIndex] && dtRoad.filters['btn'+noIndex]?'color: rgb(255, 255, 255); background-color: rgb(253, 173, 86);':''">
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

        <!--单双大小路珠-->
        <div v-if="pageTabSelect=='sdRoad'" class="zhlzbox margt20" style="display:block">
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
                    <!--综合模式筛选-->
                    <div id="zhms" class="checkbtnzh">
                        <ul class="zhmsmc">
                            <li class="title">筛选球号：</li>
                            <template v-for="(item,index) in sdRoad.filters.checkNoBoxList">
                                <li @click="sdRoadCheckNoBoxFun(index)" :class="item?'sinli checked':'sinli'"><i>1</i>
                                    <a >{{$t('gdkl10lz_'+index)}}</a>
                                </li>
                            </template>
                            <li @click="sdRoadCheckBoxAllHandle(true)" class="zhcheckall">全选</li>
                            <li @click="sdRoadCheckBoxAllHandle(false)" class="zhclear">清空</li>
                        </ul>
                        <ul class="zhmsmc">
                            <li class="title">筛选路珠：</li>
                            <template v-for="(item,index) in sdRoad.filters.checkRoadBoxList">
                                <li @click="sdRoadCheckRoadBoxFun(index)" :class="item?'sinli checked':'sinli'"><i>1</i>
                                    <a >{{$t(index)}}路珠</a>
                                </li>
                            </template>
                        </ul>
                    </div>
                </div>
                <div class="listcontent">
                    <div class="box">
                        <div class="dsLZ">
                            <template v-for="(noList,noIndex) in luzhuList">
                                <template v-for="(arrList,arrIndex) in noList">
                                    <table v-if="noIndex!='sum' && (((arrIndex.indexOf('ou')!=-1 && arrIndex.indexOf('ws')==-1 && sdRoad.filters.checkRoadBoxList['ou']) || (arrIndex.indexOf('oe')!=-1 && arrIndex.indexOf('he')==-1 && sdRoad.filters.checkRoadBoxList['oe'])) && sdRoad.filters.checkNoBoxList[noIndex])"
                                           class="lz_table_head  g_w1000">
                                        <tbody>
                                        <tr>
                                            <td>连续出现&nbsp;<input class="txtNum" v-model="sdRoad.filters['numTwo'+arrIndex]"
                                                                 type="number" min="1">&nbsp;次以上&nbsp;
                                                <select v-model="sdRoad.filters['selectTwo'+arrIndex]" class="secType">
                                                    <option :value="arrIndex.indexOf('ou')!=-1?'over':'odd'">{{arrIndex.indexOf('ou')!=-1?'大':'单'}}</option>
                                                    <option :value="arrIndex.indexOf('ou')!=-1?'under':'even'">{{arrIndex.indexOf('ou')!=-1?'小':'双'}}</option>
                                                </select>
                                                <input type="button" class="btnCheck"
                                                       @click="sdRoadTableFilterFun(arrIndex,noIndex)"
                                                       value="确定">出现的次数为: <span class="sec_count">{{sdRoad.filters['showNum'+arrIndex]}}</span>
                                            </td>
                                            <td></td>
                                            <td class="righttd">
                                                <span class="now_l"></span><span class="jinri">{{totalDateTime}}</span>&nbsp;累计：{{arrIndex.indexOf('ou')!=-1?'大':'单'}}(<span
                                                class="count left_count" data="龙">{{arrIndex.indexOf('ou')!=-1?sdRoad[noIndex].over:sdRoad[noIndex].odd}}</span>)&nbsp;&nbsp;{{arrIndex.indexOf('ou')!=-1?'小':'双'}}(<span
                                                class="count right_count" data="虎">{{arrIndex.indexOf('ou')!=-1?sdRoad[noIndex].under:sdRoad[noIndex].even}}</span>)

                                                <span class="important"><span class="ifgj">{{$t('gdkl10lz_'+noIndex)}}</span>&nbsp;<span
                                                    class="ifds">{{arrIndex.indexOf('ou')!=-1?'大小':'单双'}}</span>&nbsp;&nbsp;&nbsp;最新</span>
                                            </td>
                                            <td class="tobottom"></td>
                                        </tr>
                                        </tbody>
                                    </table>
                                    <div v-if="noIndex!='sum' && (((arrIndex.indexOf('ou')!=-1 && arrIndex.indexOf('ws')==-1 && sdRoad.filters.checkRoadBoxList['ou']) || (arrIndex.indexOf('oe')!=-1 && arrIndex.indexOf('he')==-1 && sdRoad.filters.checkRoadBoxList['oe'])) && sdRoad.filters.checkNoBoxList[noIndex])"
                                         class="item_con" dir="rtl" style="background-position-x: -29px;">
                                        <table class="lz_table_con" border="0" cellpadding="1" cellspacing="1">
                                            <tbody>
                                            <tr class="tablebox">

                                                <template v-for="(list,index) in arrList">
                                                    <template v-for="(item,g) in list">

                                                        <td :class="item[0]=='over' || item[0] == 'even'?'bgcolor':''"
                                                            :style="item[0] == sdRoad.filters['select'+arrIndex] && item.length>=sdRoad.filters['num'+arrIndex] && sdRoad.filters['btn'+arrIndex]?'color: rgb(255, 255, 255); background-color: rgb(253, 173, 86);':''">
                                                            <template v-for="(num,k) in item">
                                                                <p :style="item[0]=='over' || item[0] == 'even'?'color: rgb(241, 2, 11); display: block;':'font-weight: normal;'"
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


        <!--东南西北路珠-->
        <div v-if="pageTabSelect=='fwRoad'" class="zhlzbox margt20" style="display:block">
            <div class="listhead">
                <div class="listheadl">
                    <span class="lmms">东南西北路珠</span>
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
            <!--东南西北路珠-->
            <div class="listbox">
                <div class="listbox_spread" id="listbox_spread">
                    <div id="spreadDiv" style="height: 28px; padding: 0px; overflow: hidden;">
                        <p id="prep"><i>说明：</i>1 東：开出号码为01、05、09、13、17；<span></span></p>
                        <p id="prep"><i>     </i>2 南：开出号码为02、06、10、14、18；<span></span></p>
                        <p id="prep"><i>     </i>3 西：开出号码为03、07、11、15、19;<span></span></p>
                        <p id="prep"><i>     </i>4 北：开出号码为04、08、12、16、20<；<span></span></p>
                    </div>
                </div>
                <div class="checkbox">
                    <!--综合模式筛选-->
                    <div id="zhms" class="checkbtnzh">
                        <ul class="zhmsmc">
                            <li class="title">筛选球号：</li>
                            <template v-for="(item,index) in fwRoad.filters.checkBoxList">
                                <li @click="fwRoadCheckBoxFun(index)" :class="item?'sinli checked':'sinli'"><i>1</i>
                                    <a >{{$t('gdkl10lz_'+index)}}</a>
                                </li>
                            </template>


                            <li @click="fwRoadCheckBoxAllHandle(true)" class="zhcheckall">全选</li>
                            <li @click="fwRoadCheckBoxAllHandle(false)" class="zhclear">清空</li>
                        </ul>
                    </div>
                </div>
                <div class="listcontent">
                    <div class="box">
                        <div class="dsLZ">
                            <template v-for="(noList,noIndex) in luzhuList">
                                <template v-for="(arrList,arrIndex) in noList">
                                    <table v-if="arrIndex.indexOf('fw')!=-1 && fwRoad.filters.checkBoxList[noIndex]"
                                           class="lz_table_head  g_w1000">
                                        <tbody>
                                        <tr>
                                            <td>连续出现&nbsp;<input class="txtNum" v-model="fwRoad.filters['numTwo'+noIndex]"
                                                                 type="number" min="1">&nbsp;次以上&nbsp;
                                                <select v-model="fwRoad.filters['selectTwo'+noIndex]" class="secType">
                                                    <option value="east">东</option>
                                                    <option value="south">南</option>
                                                    <option value="west">西</option>
                                                    <option value="north">北</option>
                                                </select>
                                                <input type="button" class="btnCheck"
                                                       @click="fwRoadTableFilterFun(arrIndex,noIndex)"
                                                       value="确定">出现的次数为: <span class="sec_count">{{fwRoad.filters['showNum'+noIndex]}}</span>
                                            </td>
                                            <td></td>
                                            <td class="righttd">
                                                <span class="now_l"></span><span class="jinri">{{totalDateTime}}</span>&nbsp;累计：东(<span
                                                class="count left_count" data="东">{{fwRoad[noIndex].east}}</span>)&nbsp;&nbsp;南(<span
                                                class="count right_count" data="南">{{fwRoad[noIndex].south}}</span>)
                                                西(<span
                                                class="count left_count" data="西">{{fwRoad[noIndex].west}}</span>)&nbsp;&nbsp;北(<span
                                                class="count right_count" data="北">{{fwRoad[noIndex].north}}</span>)

                                                <span class="important"><span class="ifgj">{{$t('gdkl10lz_'+noIndex)}}</span>&nbsp;<span
                                                    class="ifds">东南西北</span>&nbsp;&nbsp;&nbsp;最新</span>
                                            </td>
                                            <td class="tobottom"></td>
                                        </tr>
                                        </tbody>
                                    </table>
                                    <div v-if="arrIndex.indexOf('fw')!=-1 && fwRoad.filters.checkBoxList[noIndex]"
                                         class="item_con" dir="rtl" style="background-position-x: -29px;">
                                        <table class="lz_table_con" border="0" cellpadding="1" cellspacing="1">
                                            <tbody>
                                            <tr class="tablebox">

                                                <template v-for="(list,index) in arrList">
                                                    <template v-for="(item,g) in list">

                                                        <td :class="g%2==0?'bgcolor':''"
                                                            :style="item[0] == fwRoad.filters['select'+noIndex] && item.length>=fwRoad.filters['num'+noIndex] && fwRoad.filters['btn'+noIndex]?'color: rgb(255, 255, 255); background-color: rgb(253, 173, 86);':''">
                                                            <template v-for="(num,k) in item">
                                                                <p :style="item[0]=='south'?'color: rgb(241, 2, 11); display: block;':'font-weight: normal;'"
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
        <div class="zhlzbox margt20" style="display: none">
            <div class="listhead">
                <div class="listheadl">
                    <span class="lmms"><i>3</i>冷热分析</span>
                </div>

            </div>
            <div class="listbox">
                <div class="checkbox">
                    <div id="lmms" class="checkbtnzh checkbtnmc noboder">
                        <ul class="lrul">
                            <li class="sinli"><i>1</i>
                                <a href="javascript:void(0)">显示热号码出现次数</a>
                            </li>
                        </ul>
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
                                <tr height="50">
                                    <td width="100">冠军</td>
                                    <td width="300">
                                        <ul>
                                            <li class="numsm02"><i>4</i></li>
                                            <li class="numsm10"><i>4</i></li>
                                        </ul>
                                    </td>
                                    <td width="400">
                                        <ul>
                                            <li class="numsm01"></li>
                                            <li class="numsm03"></li>
                                            <li class="numsm05"></li>
                                            <li class="numsm08"></li>
                                        </ul>
                                    </td>
                                    <td width="400">
                                        <ul>
                                            <li class="numsm04"></li>
                                            <li class="numsm06"></li>
                                            <li class="numsm07"></li>
                                            <li class="numsm09"></li>
                                        </ul>
                                    </td>
                                </tr>
                                <tr height="50">
                                    <td width="100">亚军</td>
                                    <td width="300">
                                        <ul>
                                            <li class="numsm06"><i>4</i></li>
                                        </ul>
                                    </td>
                                    <td width="400">
                                        <ul>
                                            <li class="numsm01"></li>
                                            <li class="numsm03"></li>
                                            <li class="numsm04"></li>
                                            <li class="numsm07"></li>
                                            <li class="numsm09"></li>
                                            <li class="numsm10"></li>
                                        </ul>
                                    </td>
                                    <td width="400">
                                        <ul>
                                            <li class="numsm02"></li>
                                            <li class="numsm05"></li>
                                            <li class="numsm08"></li>
                                        </ul>
                                    </td>
                                </tr>
                                <tr height="50">
                                    <td width="100">第三名</td>
                                    <td width="300">
                                        <ul>
                                            <li class="numsm02"><i>4</i></li>
                                        </ul>
                                    </td>
                                    <td width="400">
                                        <ul>
                                            <li class="numsm04"></li>
                                            <li class="numsm06"></li>
                                            <li class="numsm07"></li>
                                            <li class="numsm08"></li>
                                            <li class="numsm09"></li>
                                        </ul>
                                    </td>
                                    <td width="400">
                                        <ul>
                                            <li class="numsm01"></li>
                                            <li class="numsm03"></li>
                                            <li class="numsm05"></li>
                                            <li class="numsm10"></li>
                                        </ul>
                                    </td>
                                </tr>
                                <tr height="50">
                                    <td width="100">第四名</td>
                                    <td width="300">
                                        <ul>
                                            <li class="numsm01"><i>5</i></li>
                                        </ul>
                                    </td>
                                    <td width="400">
                                        <ul>
                                            <li class="numsm03"></li>
                                            <li class="numsm05"></li>
                                            <li class="numsm06"></li>
                                            <li class="numsm08"></li>
                                            <li class="numsm10"></li>
                                        </ul>
                                    </td>
                                    <td width="400">
                                        <ul>
                                            <li class="numsm02"></li>
                                            <li class="numsm04"></li>
                                            <li class="numsm07"></li>
                                            <li class="numsm09"></li>
                                        </ul>
                                    </td>
                                </tr>
                                <tr height="50">
                                    <td width="100">第五名</td>
                                    <td width="300">
                                        <ul>
                                            <li class="numsm05"><i>4</i></li>
                                            <li class="numsm08"><i>4</i></li>
                                        </ul>
                                    </td>
                                    <td width="400">
                                        <ul>
                                            <li class="numsm01"></li>
                                            <li class="numsm03"></li>
                                            <li class="numsm10"></li>
                                        </ul>
                                    </td>
                                    <td width="400">
                                        <ul>
                                            <li class="numsm02"></li>
                                            <li class="numsm04"></li>
                                            <li class="numsm06"></li>
                                            <li class="numsm07"></li>
                                            <li class="numsm09"></li>
                                        </ul>
                                    </td>
                                </tr>
                                <tr height="50">
                                    <td width="100">第六名</td>
                                    <td width="300">
                                        <ul>
                                            <li class="numsm04"><i>4</i></li>
                                            <li class="numsm06"><i>4</i></li>
                                            <li class="numsm07"><i>4</i></li>
                                        </ul>
                                    </td>
                                    <td width="400">
                                        <ul>
                                            <li class="numsm02"></li>
                                            <li class="numsm08"></li>
                                            <li class="numsm10"></li>
                                        </ul>
                                    </td>
                                    <td width="400">
                                        <ul>
                                            <li class="numsm01"></li>
                                            <li class="numsm03"></li>
                                            <li class="numsm05"></li>
                                            <li class="numsm09"></li>
                                        </ul>
                                    </td>
                                </tr>
                                <tr height="50">
                                    <td width="100">第七名</td>
                                    <td width="300">
                                        <ul>
                                            <li class="numsm09"><i>4</i></li>
                                        </ul>
                                    </td>
                                    <td width="400">
                                        <ul>
                                            <li class="numsm03"></li>
                                            <li class="numsm04"></li>
                                            <li class="numsm05"></li>
                                            <li class="numsm07"></li>
                                            <li class="numsm10"></li>
                                        </ul>
                                    </td>
                                    <td width="400">
                                        <ul>
                                            <li class="numsm01"></li>
                                            <li class="numsm02"></li>
                                            <li class="numsm06"></li>
                                            <li class="numsm08"></li>
                                        </ul>
                                    </td>
                                </tr>
                                <tr height="50">
                                    <td width="100">第八名</td>
                                    <td width="300">
                                        <ul>
                                            <li class="numsm09"><i>5</i></li>
                                        </ul>
                                    </td>
                                    <td width="400">
                                        <ul>
                                            <li class="numsm04"></li>
                                            <li class="numsm05"></li>
                                            <li class="numsm06"></li>
                                            <li class="numsm07"></li>
                                            <li class="numsm08"></li>
                                            <li class="numsm10"></li>
                                        </ul>
                                    </td>
                                    <td width="400">
                                        <ul>
                                            <li class="numsm01"></li>
                                            <li class="numsm02"></li>
                                            <li class="numsm03"></li>
                                        </ul>
                                    </td>
                                </tr>
                                <tr height="50">
                                    <td width="100">第九名</td>
                                    <td width="300">
                                        <ul>
                                            <li class="numsm05"><i>4</i></li>
                                            <li class="numsm08"><i>4</i></li>
                                        </ul>
                                    </td>
                                    <td width="400">
                                        <ul>
                                            <li class="numsm01"></li>
                                            <li class="numsm02"></li>
                                            <li class="numsm04"></li>
                                            <li class="numsm09"></li>
                                        </ul>
                                    </td>
                                    <td width="400">
                                        <ul>
                                            <li class="numsm03"></li>
                                            <li class="numsm06"></li>
                                            <li class="numsm07"></li>
                                            <li class="numsm10"></li>
                                        </ul>
                                    </td>
                                </tr>
                                <tr height="50">
                                    <td width="100">第十名</td>
                                    <td width="300">
                                        <ul>
                                            <li class="numsm03"><i>5</i></li>
                                        </ul>
                                    </td>
                                    <td width="400">
                                        <ul>
                                            <li class="numsm01"></li>
                                            <li class="numsm02"></li>
                                            <li class="numsm04"></li>
                                            <li class="numsm05"></li>
                                            <li class="numsm06"></li>
                                            <li class="numsm07"></li>
                                        </ul>
                                    </td>
                                    <td width="400">
                                        <ul>
                                            <li class="numsm08"></li>
                                            <li class="numsm09"></li>
                                            <li class="numsm10"></li>
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
                                </tr>
                                <tr class="thead smthead">
                                    <template v-for="i in 8">
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
                                </tr>
                                </thead>
                                <tbody>
                                <tr>
                                    <td>{{dateStr}}</td>
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
                                    </tr>
                                    </thead>
                                    <tbody v-for="item,index in jrhmtjList">
                                    <tr>
                                        <td>{{index+1}}</td>
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
                totalDateTime: '今天',
                jrsmList: [],
                gylhMap: {},
                languages:{en: en,zh: zh,jp: ja},
                dayType:0,
                dateStr:"",
                changlongList:[],
                hisList:[],
                dxdsHisList:[],
                lmsjList:[],
                lmsjBnt:0,
                hisNumList:[],
                gyhlmList: [],
                jrhmtjList: [],
                jrhmtjType: {
                    red:true,
                    blue:true,
                    green:true,
                },
                luzhuList:{
                    'no1':{'no1val':[],'no1oe':[],'no1ou':[],'no1':[],'no1wsou':[],'no1dtt':[],'no1heoe':[],'no1zfb':[],'no1fw':[]},
                    'no2':{'no2val':[],'no2oe':[],'no2ou':[],'no2':[],'no2wsou':[],'no2dtt':[],'no2heoe':[],'no2zfb':[],'no2fw':[]},
                    'no3':{'no3val':[],'no3oe':[],'no3ou':[],'no3':[],'no3wsou':[],'no3dtt':[],'no3heoe':[],'no3zfb':[],'no3fw':[]},
                    'no4':{'no4val':[],'no4oe':[],'no4ou':[],'no4':[],'no4wsou':[],'no4dtt':[],'no4heoe':[],'no4zfb':[],'no4fw':[]},
                    'no5':{'no5val':[],'no5oe':[],'no5ou':[],'no5':[],'no5wsou':[],'no5heoe':[],'no5zfb':[],'no5fw':[]},
                    'no6':{'no6val':[],'no6oe':[],'no6ou':[],'no6':[],'no6wsou':[],'no6heoe':[],'no6zfb':[],'no6fw':[]},
                    'no7':{'no7val':[],'no7oe':[],'no7ou':[],'no7':[],'no7wsou':[],'no7heoe':[],'no7zfb':[],'no7fw':[]},
                    'no8':{'no8val':[],'no8oe':[],'no8ou':[],'no8':[],'no8wsou':[],'no8heoe':[],'no8zfb':[],'no8fw':[]},
                    'sum':{'sumou':[],'sumoe':[],'sumws':[]}
                },
                noLuzhuList:{
                    'no1': {'no1bool':[]},
                    'no2': {'no2bool':[]},
                    'no3': {'no3bool':[]},
                    'no4': {'no4bool':[]},
                    'no5': {'no5bool':[]},
                    'no6': {'no6bool':[]},
                    'no7': {'no7bool':[]},
                    'no8': {'no8bool':[]},
                    'no9': {'no9bool':[]},
                    'no10': {'no10bool':[]},
                    'no11': {'no11bool':[]},
                    'no12': {'no12bool':[]},
                    'no13': {'no13bool':[]},
                    'no14': {'no14bool':[]},
                    'no15': {'no15bool':[]},
                    'no16': {'no16bool':[]},
                    'no17': {'no17bool':[]},
                    'no18': {'no18bool':[]},
                    'no19': {'no19bool':[]},
                    'no20': {'no20bool':[]},
                },
                resultShowType:'num',
                resultNumScreen:[],
                resultTypeScreen:'',
                roadA:{
                    roadAModeSelect:'about',
                    'no1':{over:0,under:0,draw:0,odd:0,even:0,dragon:0,tiger:0,weiover:0,weiunder:0,heodd:0,heeven:0,zhong:0,fa:0,bai:0,east:0,south:0,west:0,north:0},
                    'no2':{over:0,under:0,draw:0,odd:0,even:0,dragon:0,tiger:0,weiover:0,weiunder:0,heodd:0,heeven:0,zhong:0,fa:0,bai:0,east:0,south:0,west:0,north:0},
                    'no3':{over:0,under:0,draw:0,odd:0,even:0,dragon:0,tiger:0,weiover:0,weiunder:0,heodd:0,heeven:0,zhong:0,fa:0,bai:0,east:0,south:0,west:0,north:0},
                    'no4':{over:0,under:0,draw:0,odd:0,even:0,dragon:0,tiger:0,weiover:0,weiunder:0,heodd:0,heeven:0,zhong:0,fa:0,bai:0,east:0,south:0,west:0,north:0},
                    'no5':{over:0,under:0,draw:0,odd:0,even:0,weiover:0,weiunder:0,heodd:0,heeven:0,zhong:0,fa:0,bai:0,east:0,south:0,west:0,north:0},
                    'no6':{over:0,under:0,draw:0,odd:0,even:0,weiover:0,weiunder:0,heodd:0,heeven:0,zhong:0,fa:0,bai:0,east:0,south:0,west:0,north:0},
                    'no7':{over:0,under:0,draw:0,odd:0,even:0,weiover:0,weiunder:0,heodd:0,heeven:0,zhong:0,fa:0,bai:0,east:0,south:0,west:0,north:0},
                    'no8':{over:0,under:0,draw:0,odd:0,even:0,weiover:0,weiunder:0,heodd:0,heeven:0,zhong:0,fa:0,bai:0,east:0,south:0,west:0,north:0},
                    'sum':{over:0,under:0,draw:0,odd:0,even:0,weiover:0,weiunder:0},
                    filters:{
                        numno1ou:1,
                        numno1oe:1,
                        numno1wsou:1,
                        numno1dtt:1,
                        numno1heoe:1,
                        numno1zfb:1,
                        numno1fw:1,
                        numno2ou:1,
                        numno2oe:1,
                        numno2wsou:1,
                        numno2dtt:1,
                        numno2heoe:1,
                        numno2zfb:1,
                        numno2fw:1,
                        numno3ou:1,
                        numno3oe:1,
                        numno3wsou:1,
                        numno3dtt:1,
                        numno3heoe:1,
                        numno3zfb:1,
                        numno3fw:1,
                        numno4ou:1,
                        numno4oe:1,
                        numno4wsou:1,
                        numno4dtt:1,
                        numno4heoe:1,
                        numno4zfb:1,
                        numno4fw:1,
                        numno5ou:1,
                        numno5oe:1,
                        numno5wsou:1,
                        numno5heoe:1,
                        numno5zfb:1,
                        numno5fw:1,
                        numno6ou:1,
                        numno6oe:1,
                        numno6wsou:1,
                        numno6heoe:1,
                        numno6zfb:1,
                        numno6fw:1,
                        numno7ou:1,
                        numno7oe:1,
                        numno7wsou:1,
                        numno7heoe:1,
                        numno7zfb:1,
                        numno7fw:1,
                        numno8ou:1,
                        numno8oe:1,
                        numno8wsou:1,
                        numno8heoe:1,
                        numno8zfb:1,
                        numno8fw:1,
                        numsumou:1,
                        numsumoe:1,
                        numsumws:1,
                        numTwono1ou:1,
                        numTwono1oe:1,
                        numTwono1wsou:1,
                        numTwono1dtt:1,
                        numTwono1heoe:1,
                        numTwono1zfb:1,
                        numTwono1fw:1,
                        numTwono2ou:1,
                        numTwono2oe:1,
                        numTwono2wsou:1,
                        numTwono2dtt:1,
                        numTwono2heoe:1,
                        numTwono2zfb:1,
                        numTwono3ou:1,
                        numTwono3oe:1,
                        numTwono3wsou:1,
                        numTwono3dtt:1,
                        numTwono3heoe:1,
                        numTwono3zfb:1,
                        numTwono3fw:1,
                        numTwono4ou:1,
                        numTwono4oe:1,
                        numTwono4wsou:1,
                        numTwono4dtt:1,
                        numTwono4heoe:1,
                        numTwono4zfb:1,
                        numTwono4fw:1,
                        numTwono5ou:1,
                        numTwono5oe:1,
                        numTwono5wsou:1,
                        numTwono5heoe:1,
                        numTwono5zfb:1,
                        numTwono5fw:1,
                        numTwono6ou:1,
                        numTwono6oe:1,
                        numTwono6wsou:1,
                        numTwono6heoe:1,
                        numTwono6zfb:1,
                        numTwono6fw:1,
                        numTwono7ou:1,
                        numTwono7oe:1,
                        numTwono7wsou:1,
                        numTwono7heoe:1,
                        numTwono7zfb:1,
                        numTwono7fw:1,
                        numTwono8ou:1,
                        numTwono8oe:1,
                        numTwono8wsou:1,
                        numTwono8heoe:1,
                        numTwono8zfb:1,
                        numTwono8fw:1,
                        numTwosumou:1,
                        numTwosumoe:1,
                        numTwosumws:1,
                        selectno1ou:'over',
                        selectno1oe:'odd',
                        selectno1wsou:'over',
                        selectno1dtt:'dragon',
                        selectno1heoe:'odd',
                        selectno1zfb:'zhong',
                        selectno1fw:'east',
                        selectno2ou:'over',
                        selectno2oe:'odd',
                        selectno2wsou:'over',
                        selectno2dtt:'dragon',
                        selectno2heoe:'odd',
                        selectno2zfb:'zhong',
                        selectno2fw:'east',
                        selectno3ou:'over',
                        selectno3oe:'odd',
                        selectno3wsou:'over',
                        selectno3dtt:'dragon',
                        selectno3heoe:'odd',
                        selectno3zfb:'zhong',
                        selectno3fw:'east',
                        selectno4ou:'over',
                        selectno4oe:'odd',
                        selectno4wsou:'over',
                        selectno4dtt:'dragon',
                        selectno4heoe:'odd',
                        selectno4zfb:'zhong',
                        selectno4fw:'east',
                        selectno5ou:'over',
                        selectno5oe:'odd',
                        selectno5wsou:'over',
                        selectno5heoe:'odd',
                        selectno5zfb:'zhong',
                        selectno5fw:'east',
                        selectno6ou:'over',
                        selectno6oe:'odd',
                        selectno6wsou:'over',
                        selectno6heoe:'odd',
                        selectno6zfb:'zhong',
                        selectno6fw:'east',
                        selectno7ou:'over',
                        selectno7oe:'odd',
                        selectno7wsou:'over',
                        selectno7heoe:'odd',
                        selectno7zfb:'zhong',
                        selectno7fw:'east',
                        selectno8ou:'over',
                        selectno8oe:'odd',
                        selectno8wsou:'over',
                        selectno8heoe:'odd',
                        selectno8zfb:'zhong',
                        selectno8fw:'east',
                        selectsumou:'over',
                        selectsumoe:'odd',
                        selectsumws:'weiover',
                        selectTwono1ou:'over',
                        selectTwono1oe:'odd',
                        selectTwono1wsou:'over',
                        selectTwono1dtt:'dragon',
                        selectTwono1heoe:'odd',
                        selectTwono1zfb:'zhong',
                        selectTwono1fw:'east',
                        selectTwono2ou:'over',
                        selectTwono2oe:'odd',
                        selectTwono2wsou:'over',
                        selectTwono2dtt:'dragon',
                        selectTwono2heoe:'odd',
                        selectTwono2zfb:'zhong',
                        selectTwono2fw:'east',
                        selectTwono3ou:'over',
                        selectTwono3oe:'odd',
                        selectTwono3wsou:'over',
                        selectTwono3dtt:'dragon',
                        selectTwono3heoe:'odd',
                        selectTwono3zfb:'zhong',
                        selectTwono3fw:'east',
                        selectTwono4ou:'over',
                        selectTwono4oe:'odd',
                        selectTwono4wsou:'over',
                        selectTwono4dtt:'dragon',
                        selectTwono4heoe:'odd',
                        selectTwono4zfb:'zhong',
                        selectTwono4fw:'east',
                        selectTwono5ou:'over',
                        selectTwono5oe:'odd',
                        selectTwono5wsou:'over',
                        selectTwono5heoe:'odd',
                        selectTwono5zfb:'zhong',
                        selectTwono5fw:'east',
                        selectTwono6ou:'over',
                        selectTwono6oe:'odd',
                        selectTwono6wsou:'over',
                        selectTwono6heoe:'odd',
                        selectTwono6zfb:'zhong',
                        selectTwono6fw:'east',
                        selectTwono7ou:'over',
                        selectTwono7oe:'odd',
                        selectTwono7wsou:'over',
                        selectTwono7heoe:'odd',
                        selectTwono7zfb:'zhong',
                        selectTwono7fw:'east',
                        selectTwono8ou:'over',
                        selectTwono8oe:'odd',
                        selectTwono8wsou:'over',
                        selectTwono8heoe:'odd',
                        selectTwono8zfb:'zhong',
                        selectTwono8fw:'east',
                        selectTwosumou:'over',
                        selectTwosumoe:'odd',
                        selectTwosumws:'over',
                        btnno1ou:false,
                        btnno1oe:false,
                        btnno1wsou:false,
                        btnno1dtt:false,
                        btnno1heoe:false,
                        btnno1zfb:false,
                        btnno1fw:false,
                        btnno2ou:false,
                        btnno2oe:false,
                        btnno2wsou:false,
                        btnno2dtt:false,
                        btnno2heoe:false,
                        btnno2zfb:false,
                        btnno2fw:false,
                        btnno3ou:false,
                        btnno3oe:false,
                        btnno3wsou:false,
                        btnno3dtt:false,
                        btnno3heoe:false,
                        btnno3zfb:false,
                        btnno3fw:false,
                        btnno4ou:false,
                        btnno4oe:false,
                        btnno4wsou:false,
                        btnno4dtt:false,
                        btnno4heoe:false,
                        btnno4zfb:false,
                        btnno4fw:false,
                        btnno5ou:false,
                        btnno5oe:false,
                        btnno5wsou:false,
                        btnno5heoe:false,
                        btnno5zfb:false,
                        btnno5fw:false,
                        btnno6ou:false,
                        btnno6oe:false,
                        btnno6wsou:false,
                        btnno6heoe:false,
                        btnno6zfb:false,
                        btnno6fw:false,
                        btnno7ou:false,
                        btnno7oe:false,
                        btnno7wsou:false,
                        btnno7heoe:false,
                        btnno7zfb:false,
                        btnno7fw:false,
                        btnno8ou:false,
                        btnno8oe:false,
                        btnno8wsou:false,
                        btnno8heoe:false,
                        btnno8zfb:false,
                        btnno8fw:false,
                        btnsumou:false,
                        btnsumoe:false,
                        btnsumws:false,
                        showNumno1ou:0,
                        showNumno1oe:0,
                        showNumno1wsou:0,
                        showNumno1dtt:0,
                        showNumno1heoe:0,
                        showNumno1zfb:0,
                        showNumno1fw:0,
                        showNumno2ou:0,
                        showNumno2oe:0,
                        showNumno2wsou:0,
                        showNumno2dtt:0,
                        showNumno2heoe:0,
                        showNumno2zfb:0,
                        showNumno2fw:0,
                        showNumno3ou:0,
                        showNumno3oe:0,
                        showNumno3wsou:0,
                        showNumno3dtt:0,
                        showNumno3heoe:0,
                        showNumno3zfb:0,
                        showNumno3fw:0,
                        showNumno4ou:0,
                        showNumno4oe:0,
                        showNumno4wsou:0,
                        showNumno4dtt:0,
                        showNumno4heoe:0,
                        showNumno4zfb:0,
                        showNumno4fw:0,
                        showNumno5ou:0,
                        showNumno5oe:0,
                        showNumno5wsou:0,
                        showNumno5heoe:0,
                        showNumno5zfb:0,
                        showNumno5fw:0,
                        showNumno6ou:0,
                        showNumno6oe:0,
                        showNumno6wsou:0,
                        showNumno6heoe:0,
                        showNumno6zfb:0,
                        showNumno6fw:0,
                        showNumno7ou:0,
                        showNumno7oe:0,
                        showNumno7wsou:0,
                        showNumno7heoe:0,
                        showNumno7zfb:0,
                        showNumno7fw:0,
                        showNumno8ou:0,
                        showNumno8oe:0,
                        showNumno8wsou:0,
                        showNumno8heoe:0,
                        showNumno8zfb:0,
                        showNumno8fw:0,
                        showNumsumou:0,
                        showNumsumoe:0,
                        showNumsumws:0,

                        aboutCheckNoBoxList:{
                            no1:true,
                            no2:true,
                            no3:true,
                            no4:true,
                            no5:true,
                            no6:true,
                            no7:true,
                            no8:true,
                            sum:true
                        },
                        aboutCheckRoadBoxList:{
                            ou:true,
                            oe:true,
                            dtt:true,
                            wsou:true,
                            heoe:true,
                            zfb:true,
                            fw:true,
                        },
                        aloneCheckRoadBoxList:{
                            ou:true,
                            oe:true,
                            dtt:true,
                            wsou:true,
                            heoe:true,
                            zfb:true,
                            fw:true,
                        },
                        aloneSelectNo:'no1',
                        lmCheckNumBoxList:{
                            no1:true,
                            no2:true,
                            no3:true,
                            no4:true,
                            no5:true,
                            no6:true,
                            no7:true,
                            no8:true,
                            no9:true,
                            sum:true
                        },
                        lmSelectRoad:'ou'
                    },



                },
                sumRoad:{
                    'sum':{over:0,under:0,draw:0,odd:0,even:0,weiover:0,weiunder:0},
                    filters:{
                        numsumou:1,
                        numsumoe:1,
                        numsumws:1,
                        numTwosumou:1,
                        numTwosumoe:1,
                        numTwosumws:1,
                        selectsumou:'over',
                        selectsumoe:'odd',
                        selectsumws:'over',
                        selectTwosumou:'over',
                        selectTwosumoe:'odd',
                        selectTwosumws:'over',
                        btnsumou:false,
                        btnsumoe:false,
                        btnsumws:false,
                        showNumsumou:0,
                        showNumsumoe:0,
                        showNumsumws:0,
                    }
                },
                zfbRoad:{
                    'no1':{zhong:0,fa:0,bai:0},
                    'no2':{zhong:0,fa:0,bai:0},
                    'no3':{zhong:0,fa:0,bai:0},
                    'no4':{zhong:0,fa:0,bai:0},
                    'no5':{zhong:0,fa:0,bai:0},
                    'no6':{zhong:0,fa:0,bai:0},
                    'no7':{zhong:0,fa:0,bai:0},
                    'no8':{zhong:0,fa:0,bai:0},
                    filters:{

                        numno1:1,
                        numno2:1,
                        numno3:1,
                        numno4:1,
                        numno5:1,
                        numno6:1,
                        numno7:1,
                        numno8:1,
                        numTwono1:1,
                        numTwono2:1,
                        numTwono3:1,
                        numTwono4:1,
                        numTwono5:1,
                        numTwono6:1,
                        numTwono7:1,
                        numTwono8:1,
                        selectno1:'zhong',
                        selectno2:'zhong',
                        selectno3:'zhong',
                        selectno4:'zhong',
                        selectno5:'zhong',
                        selectno6:'zhong',
                        selectno7:'zhong',
                        selectno8:'zhong',
                        selectTwono1:'zhong',
                        selectTwono2:'zhong',
                        selectTwono3:'zhong',
                        selectTwono4:'zhong',
                        selectTwono5:'zhong',
                        selectTwono6:'zhong',
                        selectTwono7:'zhong',
                        selectTwono8:'zhong',
                        btnno1:false,
                        btnno2:false,
                        btnno3:false,
                        btnno4:false,
                        btnno5:false,
                        btnno6:false,
                        btnno7:false,
                        btnno8:false,
                        showNumno1:0,
                        showNumno2:0,
                        showNumno3:0,
                        showNumno4:0,
                        showNumno5:0,
                        showNumno6:0,
                        showNumno7:0,
                        showNumno8:0,

                        checkBoxList:{
                            no1:true,
                            no2:true,
                            no3:true,
                            no4:true,
                            no5:true,
                            no6:true,
                            no7:true,
                            no8:true,
                        },
                    },
                },
                heoeRoad:{
                    'no1':{odd:0,even:0},
                    'no2':{odd:0,even:0},
                    'no3':{odd:0,even:0},
                    'no4':{odd:0,even:0},
                    'no5':{odd:0,even:0},
                    'no6':{odd:0,even:0},
                    'no7':{odd:0,even:0},
                    'no8':{odd:0,even:0},
                    filters:{
                        numno1:1,
                        numno2:1,
                        numno3:1,
                        numno4:1,
                        numno5:1,
                        numno6:1,
                        numno7:1,
                        numno8:1,
                        numTwono1:1,
                        numTwono2:1,
                        numTwono3:1,
                        numTwono4:1,
                        numTwono5:1,
                        numTwono6:1,
                        numTwono7:1,
                        numTwono8:1,
                        selectno1:'odd',
                        selectno2:'odd',
                        selectno3:'odd',
                        selectno4:'odd',
                        selectno5:'odd',
                        selectno6:'odd',
                        selectno7:'odd',
                        selectno8:'odd',
                        selectTwono1:'odd',
                        selectTwono2:'odd',
                        selectTwono3:'odd',
                        selectTwono4:'odd',
                        selectTwono5:'odd',
                        selectTwono6:'odd',
                        selectTwono7:'odd',
                        selectTwono8:'odd',
                        btnno1:false,
                        btnno2:false,
                        btnno3:false,
                        btnno4:false,
                        btnno5:false,
                        btnno6:false,
                        btnno7:false,
                        btnno8:false,
                        showNumno1:0,
                        showNumno2:0,
                        showNumno3:0,
                        showNumno4:0,
                        showNumno5:0,
                        showNumno6:0,
                        showNumno7:0,
                        showNumno8:0,

                        checkBoxList:{
                            no1:true,
                            no2:true,
                            no3:true,
                            no4:true,
                            no5:true,
                            no6:true,
                            no7:true,
                            no8:true,
                        },
                    },
                },
                noRoad:{
                    'no1':{always:0,noalways:0},
                    'no2':{always:0,noalways:0},
                    'no3':{always:0,noalways:0},
                    'no4':{always:0,noalways:0},
                    'no5':{always:0,noalways:0},
                    'no6':{always:0,noalways:0},
                    'no7':{always:0,noalways:0},
                    'no8':{always:0,noalways:0},
                    'no9':{always:0,noalways:0},
                    'no10':{always:0,noalways:0},
                    'no11':{always:0,noalways:0},
                    'no12':{always:0,noalways:0},
                    'no13':{always:0,noalways:0},
                    'no14':{always:0,noalways:0},
                    'no15':{always:0,noalways:0},
                    'no16':{always:0,noalways:0},
                    'no17':{always:0,noalways:0},
                    'no18':{always:0,noalways:0},
                    'no19':{always:0,noalways:0},
                    'no20':{always:0,noalways:0},
                    filters:{
                        numno1:1,
                        numno2:1,
                        numno3:1,
                        numno4:1,
                        numno5:1,
                        numno6:1,
                        numno7:1,
                        numno8:1,
                        numno9:1,
                        numno10:1,
                        numno11:1,
                        numno12:1,
                        numno13:1,
                        numno14:1,
                        numno15:1,
                        numno16:1,
                        numno17:1,
                        numno18:1,
                        numno19:1,
                        numno20:1,
                        numTwono1:1,
                        numTwono2:1,
                        numTwono3:1,
                        numTwono4:1,
                        numTwono5:1,
                        numTwono6:1,
                        numTwono7:1,
                        numTwono8:1,
                        numTwono9:1,
                        numTwono10:1,
                        numTwono11:1,
                        numTwono12:1,
                        numTwono13:1,
                        numTwono14:1,
                        numTwono15:1,
                        numTwono16:1,
                        numTwono17:1,
                        numTwono18:1,
                        numTwono19:1,
                        numTwono20:1,
                        selectno1:'always',
                        selectno2:'always',
                        selectno3:'always',
                        selectno4:'always',
                        selectno5:'always',
                        selectno6:'always',
                        selectno7:'always',
                        selectno8:'always',
                        selectno9:'always',
                        selectno10:'always',
                        selectno11:'always',
                        selectno12:'always',
                        selectno13:'always',
                        selectno14:'always',
                        selectno15:'always',
                        selectno16:'always',
                        selectno17:'always',
                        selectno18:'always',
                        selectno19:'always',
                        selectno20:'always',
                        selectTwono1:'always',
                        selectTwono2:'always',
                        selectTwono3:'always',
                        selectTwono4:'always',
                        selectTwono5:'always',
                        selectTwono6:'always',
                        selectTwono7:'always',
                        selectTwono8:'always',
                        selectTwono9:'always',
                        selectTwono10:'always',
                        selectTwono11:'always',
                        selectTwono12:'always',
                        selectTwono13:'always',
                        selectTwono14:'always',
                        selectTwono15:'always',
                        selectTwono16:'always',
                        selectTwono17:'always',
                        selectTwono18:'always',
                        selectTwono19:'always',
                        selectTwono20:'always',
                        btnno1:false,
                        btnno2:false,
                        btnno3:false,
                        btnno4:false,
                        btnno5:false,
                        btnno6:false,
                        btnno7:false,
                        btnno8:false,
                        btnno9:false,
                        btnno10:false,
                        btnno11:false,
                        btnno12:false,
                        btnno13:false,
                        btnno14:false,
                        btnno15:false,
                        btnno16:false,
                        btnno17:false,
                        btnno18:false,
                        btnno19:false,
                        btnno20:false,
                        showNumno1:0,
                        showNumno2:0,
                        showNumno3:0,
                        showNumno4:0,
                        showNumno5:0,
                        showNumno6:0,
                        showNumno7:0,
                        showNumno8:0,
                        showNumno9:0,
                        showNumno10:0,
                        showNumno11:0,
                        showNumno12:0,
                        showNumno13:0,
                        showNumno14:0,
                        showNumno15:0,
                        showNumno16:0,
                        showNumno17:0,
                        showNumno18:0,
                        showNumno19:0,
                        showNumno20:0,
                        checkBoxList:{
                            no1:true,
                            no2:true,
                            no3:true,
                            no4:true,
                            no5:true,
                            no6:true,
                            no7:true,
                            no8:true,
                            no9:true,
                            no10:true,
                            no11:true,
                            no12:true,
                            no13:true,
                            no14:true,
                            no15:true,
                            no16:true,
                            no17:true,
                            no18:true,
                            no19:true,
                            no20:true,
                        },
                    }
                },
                wsouRoad:{
                    'no1':{over:0,under:0},
                    'no2':{over:0,under:0},
                    'no3':{over:0,under:0},
                    'no4':{over:0,under:0},
                    'no5':{over:0,under:0},
                    'no6':{over:0,under:0},
                    'no7':{over:0,under:0},
                    'no8':{over:0,under:0},
                    filters:{
                        numno1:1,
                        numno2:1,
                        numno3:1,
                        numno4:1,
                        numno5:1,
                        numno6:1,
                        numno7:1,
                        numno8:1,
                        numTwono1:1,
                        numTwono2:1,
                        numTwono3:1,
                        numTwono4:1,
                        numTwono5:1,
                        numTwono6:1,
                        numTwono7:1,
                        numTwono8:1,
                        selectno1:'over',
                        selectno2:'over',
                        selectno3:'over',
                        selectno4:'over',
                        selectno5:'over',
                        selectno6:'over',
                        selectno7:'over',
                        selectno8:'over',
                        selectTwono1:'over',
                        selectTwono2:'over',
                        selectTwono3:'over',
                        selectTwono4:'over',
                        selectTwono5:'over',
                        selectTwono6:'over',
                        selectTwono7:'over',
                        selectTwono8:'over',
                        btnno1:false,
                        btnno2:false,
                        btnno3:false,
                        btnno4:false,
                        btnno5:false,
                        btnno6:false,
                        btnno7:false,
                        btnno8:false,
                        showNumno1:0,
                        showNumno2:0,
                        showNumno3:0,
                        showNumno4:0,
                        showNumno5:0,
                        showNumno6:0,
                        showNumno7:0,
                        showNumno8:0,

                        checkBoxList:{
                            no1:true,
                            no2:true,
                            no3:true,
                            no4:true,
                            no5:true,
                            no6:true,
                            no7:true,
                            no8:true,
                        },
                    },
                },
                dtRoad:{
                    'no1':{dragon:0,tiger:0},
                    'no2':{dragon:0,tiger:0},
                    'no3':{dragon:0,tiger:0},
                    'no4':{dragon:0,tiger:0},
                    filters:{
                        numno1:1,
                        numno2:1,
                        numno3:1,
                        numno4:1,
                        numTwono1:1,
                        numTwono2:1,
                        numTwono3:1,
                        numTwono4:1,
                        selectno1:'dragon',
                        selectno2:'dragon',
                        selectno3:'dragon',
                        selectno4:'dragon',
                        selectTwono1:'dragon',
                        selectTwono2:'dragon',
                        selectTwono3:'dragon',
                        selectTwono4:'dragon',
                        btnno1:false,
                        btnno2:false,
                        btnno3:false,
                        btnno4:false,
                        showNumno1:0,
                        showNumno2:0,
                        showNumno3:0,
                        showNumno4:0,

                        checkBoxList:{
                            no1:true,
                            no2:true,
                            no3:true,
                            no4:true,
                        },
                    },
                },
                sdRoad:{
                    'no1':{over:0,under:0,odd:0,even:0},
                    'no2':{over:0,under:0,odd:0,even:0},
                    'no3':{over:0,under:0,odd:0,even:0},
                    'no4':{over:0,under:0,odd:0,even:0},
                    'no5':{over:0,under:0,odd:0,even:0},
                    'no6':{over:0,under:0,odd:0,even:0},
                    'no7':{over:0,under:0,odd:0,even:0},
                    'no8':{over:0,under:0,odd:0,even:0},
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
                        numno6ou:1,
                        numno6oe:1,
                        numno7ou:1,
                        numno7oe:1,
                        numno8ou:1,
                        numno8oe:1,
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
                        numTwono6ou:1,
                        numTwono6oe:1,
                        numTwono7ou:1,
                        numTwono7oe:1,
                        numTwono8ou:1,
                        numTwono8oe:1,
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
                        selectno6ou:'over',
                        selectno6oe:'odd',
                        selectno7ou:'over',
                        selectno7oe:'odd',
                        selectno8ou:'over',
                        selectno8oe:'odd',
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
                        selectTwono6ou:'over',
                        selectTwono6oe:'odd',
                        selectTwono7ou:'over',
                        selectTwono7oe:'odd',
                        selectTwono8ou:'over',
                        selectTwono8oe:'odd',
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
                        btnno6ou:false,
                        btnno6oe:false,
                        btnno7ou:false,
                        btnno7oe:false,
                        btnno8ou:false,
                        btnno8oe:false,
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
                        showNumno6ou:0,
                        showNumno6oe:0,
                        showNumno7ou:0,
                        showNumno7oe:0,
                        showNumno8ou:0,
                        showNumno8oe:0,
                        checkNoBoxList:{
                            no1:true,
                            no2:true,
                            no3:true,
                            no4:true,
                            no5:true,
                            no6:true,
                            no7:true,
                            no8:true,
                        },
                        checkRoadBoxList:{
                            ou:true,
                            oe:true,
                        },
                    },
                },
                fwRoad:{
                    'no1':{east:0, south:0, west:0,north:0},
                    'no2':{east:0, south:0, west:0,north:0},
                    'no3':{east:0, south:0, west:0,north:0},
                    'no4':{east:0, south:0, west:0,north:0},
                    'no5':{east:0, south:0, west:0,north:0},
                    'no6':{east:0, south:0, west:0,north:0},
                    'no7':{east:0, south:0, west:0,north:0},
                    'no8':{east:0, south:0, west:0,north:0},
                    filters:{
                        numno1:1,
                        numno2:1,
                        numno3:1,
                        numno4:1,
                        numno5:1,
                        numno6:1,
                        numno7:1,
                        numno8:1,
                        numTwono1:1,
                        numTwono2:1,
                        numTwono3:1,
                        numTwono4:1,
                        numTwono5:1,
                        numTwono6:1,
                        numTwono7:1,
                        numTwono8:1,
                        selectno1:'east',
                        selectno2:'east',
                        selectno3:'east',
                        selectno4:'east',
                        selectno5:'east',
                        selectno6:'east',
                        selectno7:'east',
                        selectno8:'east',
                        selectTwono1:'east',
                        selectTwono2:'east',
                        selectTwono3:'east',
                        selectTwono4:'east',
                        selectTwono5:'east',
                        selectTwono6:'east',
                        selectTwono7:'east',
                        selectTwono8:'east',
                        btnno1:false,
                        btnno2:false,
                        btnno3:false,
                        btnno4:false,
                        btnno5:false,
                        btnno6:false,
                        btnno7:false,
                        btnno8:false,
                        showNumno1:0,
                        showNumno2:0,
                        showNumno3:0,
                        showNumno4:0,
                        showNumno5:0,
                        showNumno6:0,
                        showNumno7:0,
                        showNumno8:0,

                        checkBoxList:{
                            no1:true,
                            no2:true,
                            no3:true,
                            no4:true,
                            no5:true,
                            no6:true,
                            no7:true,
                            no8:true,
                        },
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
        computed:{
            ...mapGetters(['lotteryKey','pageTabSelect'])
        },
        methods: {
            fwRoadCheckBoxFun(key){
                this.fwRoad.filters.checkBoxList[key] = !this.fwRoad.filters.checkBoxList[key];
            },
            fwRoadCheckBoxAllHandle(flag) {
                for(let key in this.fwRoad.filters.checkBoxList){
                    this.fwRoad.filters.checkBoxList[key] = flag;
                }
            },
            fwRoadTableFilterFun(key,no){
                this.fwRoad.filters['num'+no] = this.fwRoad.filters['numTwo'+no];
                this.fwRoad.filters['select'+no] = this.fwRoad.filters['selectTwo'+no];
                this.fwRoad.filters['btn'+no] = true;
                this.fwRoad.filters['showNum'+no] = 0;
                this.luzhuList[no][key].forEach(item=>{
                    item.forEach(obj=>{
                        if(obj[0]==this.fwRoad.filters['select'+no] && obj.length>=this.fwRoad.filters['num'+no]){
                            this.fwRoad.filters['showNum'+no]++;
                        }
                    })
                })
            },
            sdRoadCheckNoBoxFun(key){
                this.sdRoad.filters.checkNoBoxList[key] = !this.sdRoad.filters.checkNoBoxList[key];
            },
            sdRoadCheckRoadBoxFun(key){
                this.sdRoad.filters.checkRoadBoxList[key] = !this.sdRoad.filters.checkRoadBoxList[key];
            },
            sdRoadCheckBoxAllHandle(flag) {
                for(let key in this.sdRoad.filters.checkNoBoxList){
                    this.sdRoad.filters.checkNoBoxList[key] = flag;
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
            dtRoadCheckBoxFun(key){
                this.dtRoad.filters.checkBoxList[key] = !this.dtRoad.filters.checkBoxList[key];
            },
            dtRoadCheckBoxAllHandle(flag) {
                for(let key in this.dtRoad.filters.checkBoxList){
                    this.dtRoad.filters.checkBoxList[key] = flag;
                }
            },
            dtRoadTableFilterFun(key,no){
                this.dtRoad.filters['num'+no] = this.dtRoad.filters['numTwo'+no];
                this.dtRoad.filters['select'+no] = this.dtRoad.filters['selectTwo'+no];
                this.dtRoad.filters['btn'+no] = true;
                this.dtRoad.filters['showNum'+no] = 0;
                this.luzhuList[no][key].forEach(item=>{
                    item.forEach(obj=>{
                        if(obj[0]==this.dtRoad.filters['select'+no] && obj.length>=this.dtRoad.filters['num'+no]){
                            this.dtRoad.filters['showNum'+no]++;
                        }
                    })
                })
            },
            wsouRoadCheckBoxFun(key){
                this.wsouRoad.filters.checkBoxList[key] = !this.wsouRoad.filters.checkBoxList[key];
            },
            wsouRoadCheckBoxAllHandle(flag) {
                for(let key in this.wsouRoad.filters.checkBoxList){
                    this.wsouRoad.filters.checkBoxList[key] = flag;
                }
            },
            wsouRoadTableFilterFun(key,no){
                this.wsouRoad.filters['num'+no] = this.wsouRoad.filters['numTwo'+no];
                this.wsouRoad.filters['select'+no] = this.wsouRoad.filters['selectTwo'+no];
                this.wsouRoad.filters['btn'+no] = true;
                this.wsouRoad.filters['showNum'+no] = 0;
                this.luzhuList[no][key].forEach(item=>{
                    item.forEach(obj=>{
                        if(obj[0]==this.wsouRoad.filters['select'+no] && obj.length>=this.wsouRoad.filters['num'+no]){
                            this.wsouRoad.filters['showNum'+no]++;
                        }
                    })
                })
            },
            heoeRoadCheckBoxFun(key){
                this.heoeRoad.filters.checkBoxList[key] = !this.heoeRoad.filters.checkBoxList[key];
            },
            heoeRoadCheckBoxAllHandle(flag) {
                for(let key in this.heoeRoad.filters.checkBoxList){
                    this.heoeRoad.filters.checkBoxList[key] = flag;
                }
            },
            heoeRoadTableFilterFun(key,no){
                this.heoeRoad.filters['num'+no] = this.heoeRoad.filters['numTwo'+no];
                this.heoeRoad.filters['select'+no] = this.heoeRoad.filters['selectTwo'+no];
                this.heoeRoad.filters['btn'+no] = true;
                this.heoeRoad.filters['showNum'+no] = 0;
                this.luzhuList[no][key].forEach(item=>{
                    item.forEach(obj=>{
                        if(obj[0]==this.heoeRoad.filters['select'+no] && obj.length>=this.heoeRoad.filters['num'+no]){
                            this.heoeRoad.filters['showNum'+no]++;
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
            zfbRoadCheckBoxFun(key){
                this.zfbRoad.filters.checkBoxList[key] = !this.zfbRoad.filters.checkBoxList[key];
            },
            zfbRoadCheckBoxAllHandle(flag) {
                for(let key in this.zfbRoad.filters.checkBoxList){
                    this.zfbRoad.filters.checkBoxList[key] = flag;
                }
            },
            zfbRoadTableFilterFun(key,no){
                this.zfbRoad.filters['num'+no] = this.zfbRoad.filters['numTwo'+no];
                this.zfbRoad.filters['select'+no] = this.zfbRoad.filters['selectTwo'+no];
                this.zfbRoad.filters['btn'+no] = true;
                this.zfbRoad.filters['showNum'+no] = 0;
                this.luzhuList[no][key].forEach(item=>{
                    item.forEach(obj=>{
                        if(obj[0]==this.zfbRoad.filters['select'+no] && obj.length>=this.zfbRoad.filters['num'+no]){
                            this.zfbRoad.filters['showNum'+no]++;
                        }
                    })
                })
            },
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
            roadATableFilterFun(key,no){

                this.roadA.filters['num'+key] = this.roadA.filters['numTwo'+key];
                this.roadA.filters['select'+key] = this.roadA.filters['selectTwo'+key];
                this.roadA.filters['btn'+key] = true;
                this.roadA.filters['showNum'+key] = 0;
                console.log('aa',key,no,this.roadA.filters['num'+key],'22',this.roadA.filters['select'+key]);
                this.luzhuList[no][key].forEach(item=>{
                    item.forEach(obj=>{
                        if(obj[0]==this.roadA.filters['select'+key] && obj.length>=this.roadA.filters['num'+key]){
                            this.roadA.filters['showNum'+key]++;
                        }
                    })
                })
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
            clearFilterInfo(){
                this.luzhuList={
                    'no1':{'no1val':[],'no1oe':[],'no1ou':[],'no1':[],'no1wsou':[],'no1dtt':[],'no1heoe':[],'no1zfb':[],'no1fw':[]},
                    'no2':{'no2val':[],'no2oe':[],'no2ou':[],'no2':[],'no2wsou':[],'no2dtt':[],'no2heoe':[],'no2zfb':[],'no2fw':[]},
                    'no3':{'no3val':[],'no3oe':[],'no3ou':[],'no3':[],'no3wsou':[],'no3dtt':[],'no3heoe':[],'no3zfb':[],'no3fw':[]},
                    'no4':{'no4val':[],'no4oe':[],'no4ou':[],'no4':[],'no4wsou':[],'no4dtt':[],'no4heoe':[],'no4zfb':[],'no4fw':[]},
                    'no5':{'no5val':[],'no5oe':[],'no5ou':[],'no5':[],'no5wsou':[],'no5heoe':[],'no5zfb':[],'no5fw':[]},
                    'no6':{'no6val':[],'no6oe':[],'no6ou':[],'no6':[],'no6wsou':[],'no6heoe':[],'no6zfb':[],'no6fw':[]},
                    'no7':{'no7val':[],'no7oe':[],'no7ou':[],'no7':[],'no7wsou':[],'no7heoe':[],'no7zfb':[],'no7fw':[]},
                    'no8':{'no8val':[],'no8oe':[],'no8ou':[],'no8':[],'no8wsou':[],'no8heoe':[],'no8zfb':[],'no8fw':[]},
                    'sum':{'sumou':[],'sumoe':[],'sumws':[]}
                };
              if(this.pageTabSelect =='roadA'){
                  this.roadA={
                      roadAModeSelect:'about',
                      'no1':{over:0,under:0,draw:0,odd:0,even:0,dragon:0,tiger:0,weiover:0,weiunder:0,heodd:0,heeven:0,zhong:0,fa:0,bai:0,east:0,south:0,west:0,north:0},
                      'no2':{over:0,under:0,draw:0,odd:0,even:0,dragon:0,tiger:0,weiover:0,weiunder:0,heodd:0,heeven:0,zhong:0,fa:0,bai:0,east:0,south:0,west:0,north:0},
                      'no3':{over:0,under:0,draw:0,odd:0,even:0,dragon:0,tiger:0,weiover:0,weiunder:0,heodd:0,heeven:0,zhong:0,fa:0,bai:0,east:0,south:0,west:0,north:0},
                      'no4':{over:0,under:0,draw:0,odd:0,even:0,dragon:0,tiger:0,weiover:0,weiunder:0,heodd:0,heeven:0,zhong:0,fa:0,bai:0,east:0,south:0,west:0,north:0},
                      'no5':{over:0,under:0,draw:0,odd:0,even:0,weiover:0,weiunder:0,heodd:0,heeven:0,zhong:0,fa:0,bai:0,east:0,south:0,west:0,north:0},
                      'no6':{over:0,under:0,draw:0,odd:0,even:0,weiover:0,weiunder:0,heodd:0,heeven:0,zhong:0,fa:0,bai:0,east:0,south:0,west:0,north:0},
                      'no7':{over:0,under:0,draw:0,odd:0,even:0,weiover:0,weiunder:0,heodd:0,heeven:0,zhong:0,fa:0,bai:0,east:0,south:0,west:0,north:0},
                      'no8':{over:0,under:0,draw:0,odd:0,even:0,weiover:0,weiunder:0,heodd:0,heeven:0,zhong:0,fa:0,bai:0,east:0,south:0,west:0,north:0},
                      'sum':{over:0,under:0,draw:0,odd:0,even:0,weiover:0,weiunder:0},
                      filters:{
                          numno1ou:1,
                          numno1oe:1,
                          numno1wsou:1,
                          numno1dtt:1,
                          numno1heoe:1,
                          numno1zfb:1,
                          numno1fw:1,
                          numno2ou:1,
                          numno2oe:1,
                          numno2wsou:1,
                          numno2dtt:1,
                          numno2heoe:1,
                          numno2zfb:1,
                          numno2fw:1,
                          numno3ou:1,
                          numno3oe:1,
                          numno3wsou:1,
                          numno3dtt:1,
                          numno3heoe:1,
                          numno3zfb:1,
                          numno3fw:1,
                          numno4ou:1,
                          numno4oe:1,
                          numno4wsou:1,
                          numno4dtt:1,
                          numno4heoe:1,
                          numno4zfb:1,
                          numno4fw:1,
                          numno5ou:1,
                          numno5oe:1,
                          numno5wsou:1,
                          numno5heoe:1,
                          numno5zfb:1,
                          numno5fw:1,
                          numno6ou:1,
                          numno6oe:1,
                          numno6wsou:1,
                          numno6heoe:1,
                          numno6zfb:1,
                          numno6fw:1,
                          numno7ou:1,
                          numno7oe:1,
                          numno7wsou:1,
                          numno7heoe:1,
                          numno7zfb:1,
                          numno7fw:1,
                          numno8ou:1,
                          numno8oe:1,
                          numno8wsou:1,
                          numno8heoe:1,
                          numno8zfb:1,
                          numno8fw:1,
                          numsumou:1,
                          numsumoe:1,
                          numsumws:1,
                          numTwono1ou:1,
                          numTwono1oe:1,
                          numTwono1wsou:1,
                          numTwono1dtt:1,
                          numTwono1heoe:1,
                          numTwono1zfb:1,
                          numTwono1fw:1,
                          numTwono2ou:1,
                          numTwono2oe:1,
                          numTwono2wsou:1,
                          numTwono2dtt:1,
                          numTwono2heoe:1,
                          numTwono3ou:1,
                          numTwono3oe:1,
                          numTwono3wsou:1,
                          numTwono3dtt:1,
                          numTwono3heoe:1,
                          numTwono3zfb:1,
                          numTwono3fw:1,
                          numTwono4ou:1,
                          numTwono4oe:1,
                          numTwono4wsou:1,
                          numTwono4dtt:1,
                          numTwono4heoe:1,
                          numTwono4zfb:1,
                          numTwono4fw:1,
                          numTwono5ou:1,
                          numTwono5oe:1,
                          numTwono5wsou:1,
                          numTwono5heoe:1,
                          numTwono5zfb:1,
                          numTwono5fw:1,
                          numTwono6ou:1,
                          numTwono6oe:1,
                          numTwono6wsou:1,
                          numTwono6heoe:1,
                          numTwono6zfb:1,
                          numTwono6fw:1,
                          numTwono7ou:1,
                          numTwono7oe:1,
                          numTwono7wsou:1,
                          numTwono7heoe:1,
                          numTwono7zfb:1,
                          numTwono7fw:1,
                          numTwono8ou:1,
                          numTwono8oe:1,
                          numTwono8wsou:1,
                          numTwono8heoe:1,
                          numTwono8zfb:1,
                          numTwono8fw:1,
                          numTwosumou:1,
                          numTwosumoe:1,
                          numTwosumws:1,
                          selectno1ou:'over',
                          selectno1oe:'odd',
                          selectno1wsou:'over',
                          selectno1dtt:'dragon',
                          selectno1heoe:'odd',
                          selectno1zfb:'zhong',
                          selectno1fw:'east',
                          selectno2ou:'over',
                          selectno2oe:'odd',
                          selectno2wsou:'over',
                          selectno2dtt:'dragon',
                          selectno2heoe:'odd',
                          selectno2zfb:'zhong',
                          selectno2fw:'east',
                          selectno3ou:'over',
                          selectno3oe:'odd',
                          selectno3wsou:'over',
                          selectno3dtt:'dragon',
                          selectno3heoe:'odd',
                          selectno3zfb:'zhong',
                          selectno3fw:'east',
                          selectno4ou:'over',
                          selectno4oe:'odd',
                          selectno4wsou:'over',
                          selectno4dtt:'dragon',
                          selectno4heoe:'odd',
                          selectno4zfb:'zhong',
                          selectno4fw:'east',
                          selectno5ou:'over',
                          selectno5oe:'odd',
                          selectno5wsou:'over',
                          selectno5heoe:'odd',
                          selectno5zfb:'zhong',
                          selectno5fw:'east',
                          selectno6ou:'over',
                          selectno6oe:'odd',
                          selectno6wsou:'over',
                          selectno6heoe:'odd',
                          selectno6zfb:'zhong',
                          selectno6fw:'east',
                          selectno7ou:'over',
                          selectno7oe:'odd',
                          selectno7wsou:'over',
                          selectno7heoe:'odd',
                          selectno7zfb:'zhong',
                          selectno7fw:'east',
                          selectno8ou:'over',
                          selectno8oe:'odd',
                          selectno8wsou:'over',
                          selectno8heoe:'odd',
                          selectno8zfb:'zhong',
                          selectno8fw:'east',
                          selectsumou:'over',
                          selectsumoe:'odd',
                          selectsumws:'weiover',
                          selectTwono1ou:'over',
                          selectTwono1oe:'odd',
                          selectTwono1wsou:'over',
                          selectTwono1dtt:'dragon',
                          selectTwono1heoe:'odd',
                          selectTwono1zfb:'zhong',
                          selectTwono1fw:'east',
                          selectTwono2ou:'over',
                          selectTwono2oe:'odd',
                          selectTwono2wsou:'over',
                          selectTwono2dtt:'dragon',
                          selectTwono2heoe:'odd',
                          selectTwono2zfb:'zhong',
                          selectTwono2fw:'east',
                          selectTwono3ou:'over',
                          selectTwono3oe:'odd',
                          selectTwono3wsou:'over',
                          selectTwono3dtt:'dragon',
                          selectTwono3heoe:'odd',
                          selectTwono3zfb:'zhong',
                          selectTwono3fw:'east',
                          selectTwono4ou:'over',
                          selectTwono4oe:'odd',
                          selectTwono4wsou:'over',
                          selectTwono4dtt:'dragon',
                          selectTwono4heoe:'odd',
                          selectTwono4zfb:'zhong',
                          selectTwono4fw:'east',
                          selectTwono5ou:'over',
                          selectTwono5oe:'odd',
                          selectTwono5wsou:'over',
                          selectTwono5heoe:'odd',
                          selectTwono5zfb:'zhong',
                          selectTwono5fw:'east',
                          selectTwono6ou:'over',
                          selectTwono6oe:'odd',
                          selectTwono6wsou:'over',
                          selectTwono6heoe:'odd',
                          selectTwono6zfb:'zhong',
                          selectTwono6fw:'east',
                          selectTwono7ou:'over',
                          selectTwono7oe:'odd',
                          selectTwono7wsou:'over',
                          selectTwono7heoe:'odd',
                          selectTwono7zfb:'zhong',
                          selectTwono7fw:'east',
                          selectTwono8ou:'over',
                          selectTwono8oe:'odd',
                          selectTwono8wsou:'over',
                          selectTwono8heoe:'odd',
                          selectTwono8zfb:'zhong',
                          selectTwono8fw:'east',
                          selectTwosumou:'over',
                          selectTwosumoe:'odd',
                          selectTwosumws:'over',
                          btnno1ou:false,
                          btnno1oe:false,
                          btnno1wsou:false,
                          btnno1dtt:false,
                          btnno1heoe:false,
                          btnno1zfb:false,
                          btnno1fw:false,
                          btnno2ou:false,
                          btnno2oe:false,
                          btnno2wsou:false,
                          btnno2dtt:false,
                          btnno2heoe:false,
                          btnno2zfb:false,
                          btnno2fw:false,
                          btnno3ou:false,
                          btnno3oe:false,
                          btnno3wsou:false,
                          btnno3dtt:false,
                          btnno3heoe:false,
                          btnno3zfb:false,
                          btnno3fw:false,
                          btnno4ou:false,
                          btnno4oe:false,
                          btnno4wsou:false,
                          btnno4dtt:false,
                          btnno4heoe:false,
                          btnno4zfb:false,
                          btnno4fw:false,
                          btnno5ou:false,
                          btnno5oe:false,
                          btnno5wsou:false,
                          btnno5heoe:false,
                          btnno5zfb:false,
                          btnno5fw:false,
                          btnno6ou:false,
                          btnno6oe:false,
                          btnno6wsou:false,
                          btnno6heoe:false,
                          btnno6zfb:false,
                          btnno6fw:false,
                          btnno7ou:false,
                          btnno7oe:false,
                          btnno7wsou:false,
                          btnno7heoe:false,
                          btnno7zfb:false,
                          btnno7fw:false,
                          btnno8ou:false,
                          btnno8oe:false,
                          btnno8wsou:false,
                          btnno8heoe:false,
                          btnno8zfb:false,
                          btnno8fw:false,
                          btnsumou:false,
                          btnsumoe:false,
                          btnsumws:false,
                          showNumno1ou:0,
                          showNumno1oe:0,
                          showNumno1wsou:0,
                          showNumno1dtt:0,
                          showNumno1heoe:0,
                          showNumno1zfb:0,
                          showNumno1fw:0,
                          showNumno2ou:0,
                          showNumno2oe:0,
                          showNumno2wsou:0,
                          showNumno2dtt:0,
                          showNumno2heoe:0,
                          showNumno2zfb:0,
                          showNumno2fw:0,
                          showNumno3ou:0,
                          showNumno3oe:0,
                          showNumno3wsou:0,
                          showNumno3dtt:0,
                          showNumno3heoe:0,
                          showNumno3zfb:0,
                          showNumno3fw:0,
                          showNumno4ou:0,
                          showNumno4oe:0,
                          showNumno4wsou:0,
                          showNumno4dtt:0,
                          showNumno4heoe:0,
                          showNumno4zfb:0,
                          showNumno4fw:0,
                          showNumno5ou:0,
                          showNumno5oe:0,
                          showNumno5wsou:0,
                          showNumno5heoe:0,
                          showNumno5zfb:0,
                          showNumno5fw:0,
                          showNumno6ou:0,
                          showNumno6oe:0,
                          showNumno6wsou:0,
                          showNumno6heoe:0,
                          showNumno6zfb:0,
                          showNumno6fw:0,
                          showNumno7ou:0,
                          showNumno7oe:0,
                          showNumno7wsou:0,
                          showNumno7heoe:0,
                          showNumno7zfb:0,
                          showNumno7fw:0,
                          showNumno8ou:0,
                          showNumno8oe:0,
                          showNumno8wsou:0,
                          showNumno8heoe:0,
                          showNumno8zfb:0,
                          showNumno8fw:0,
                          showNumsumou:0,
                          showNumsumoe:0,
                          showNumsumws:0,

                          aboutCheckNoBoxList:{
                              no1:true,
                              no2:true,
                              no3:true,
                              no4:true,
                              no5:true,
                              no6:true,
                              no7:true,
                              no8:true,
                              sum:true
                          },
                          aboutCheckRoadBoxList:{
                              ou:true,
                              oe:true,
                              dtt:true,
                              wsou:true,
                              heoe:true,
                              zfb:true,
                              fw:true,
                          },
                          aloneCheckRoadBoxList:{
                              ou:true,
                              oe:true,
                              dtt:true,
                              wsou:true,
                              heoe:true,
                              zfb:true,
                              fw:true,
                          },
                          aloneSelectNo:'no1',
                          lmCheckNumBoxList:{
                              no1:true,
                              no2:true,
                              no3:true,
                              no4:true,
                              no5:true,
                              no6:true,
                              no7:true,
                              no8:true,
                              no9:true,
                              sum:true
                          },
                          lmSelectRoad:'ou'
                      },



                  };
              }else if(this.pageTabSelect=='sumRoad'){
                  this.sumRoad={
                      'sum':{over:0,under:0,draw:0,odd:0,even:0,weiover:0,weiunder:0},
                      filters:{
                          numsumou:1,
                              numsumoe:1,
                              numsumws:1,
                              numTwosumou:1,
                              numTwosumoe:1,
                              numTwosumws:1,
                              selectsumou:'over',
                              selectsumoe:'odd',
                              selectsumws:'over',
                              selectTwosumou:'over',
                              selectTwosumoe:'odd',
                              selectTwosumws:'over',
                              btnsumou:false,
                              btnsumoe:false,
                              btnsumws:false,
                              showNumsumou:0,
                              showNumsumoe:0,
                              showNumsumws:0,
                      }
                  };
              }else if(this.pageTabSelect=='zfbRoad'){
                  this.zfbRoad = {
                      'no1':{zhong:0,fa:0,bai:0},
                      'no2':{zhong:0,fa:0,bai:0},
                      'no3':{zhong:0,fa:0,bai:0},
                      'no4':{zhong:0,fa:0,bai:0},
                      'no5':{zhong:0,fa:0,bai:0},
                      'no6':{zhong:0,fa:0,bai:0},
                      'no7':{zhong:0,fa:0,bai:0},
                      'no8':{zhong:0,fa:0,bai:0},
                      filters:{

                          numno1:1,
                              numno2:1,
                              numno3:1,
                              numno4:1,
                              numno5:1,
                              numno6:1,
                              numno7:1,
                              numno8:1,
                              numTwono1:1,
                              numTwono2:1,
                              numTwono3:1,
                              numTwono4:1,
                              numTwono5:1,
                              numTwono6:1,
                              numTwono7:1,
                              numTwono8:1,
                              selectno1:'zhong',
                              selectno2:'zhong',
                              selectno3:'zhong',
                              selectno4:'zhong',
                              selectno5:'zhong',
                              selectno6:'zhong',
                              selectno7:'zhong',
                              selectno8:'zhong',
                              selectTwono1:'zhong',
                              selectTwono2:'zhong',
                              selectTwono3:'zhong',
                              selectTwono4:'zhong',
                              selectTwono5:'zhong',
                              selectTwono6:'zhong',
                              selectTwono7:'zhong',
                              selectTwono8:'zhong',
                              btnno1:false,
                              btnno2:false,
                              btnno3:false,
                              btnno4:false,
                              btnno5:false,
                              btnno6:false,
                              btnno7:false,
                              btnno8:false,
                              showNumno1:0,
                              showNumno2:0,
                              showNumno3:0,
                              showNumno4:0,
                              showNumno5:0,
                              showNumno6:0,
                              showNumno7:0,
                              showNumno8:0,

                              checkBoxList:{
                              no1:true,
                                  no2:true,
                                  no3:true,
                                  no4:true,
                                  no5:true,
                                  no6:true,
                                  no7:true,
                                  no8:true,
                          },
                      },
                  };
              }else if(this.pageTabSelect=='noRoad'){
                  this.noLuzhuList = {
                      'no1': {'no1bool':[]},
                      'no2': {'no2bool':[]},
                      'no3': {'no3bool':[]},
                      'no4': {'no4bool':[]},
                      'no5': {'no5bool':[]},
                      'no6': {'no6bool':[]},
                      'no7': {'no7bool':[]},
                      'no8': {'no8bool':[]},
                      'no9': {'no9bool':[]},
                      'no10': {'no10bool':[]},
                      'no11': {'no11bool':[]},
                      'no12': {'no12bool':[]},
                      'no13': {'no13bool':[]},
                      'no14': {'no14bool':[]},
                      'no15': {'no15bool':[]},
                      'no16': {'no16bool':[]},
                      'no17': {'no17bool':[]},
                      'no18': {'no18bool':[]},
                      'no19': {'no19bool':[]},
                      'no20': {'no20bool':[]},
                  };
                  this.noRoad = {
                      'no1': {always: 0, noalways: 0},
                      'no2': {always: 0, noalways: 0},
                      'no3': {always: 0, noalways: 0},
                      'no4': {always: 0, noalways: 0},
                      'no5': {always: 0, noalways: 0},
                      'no6': {always: 0, noalways: 0},
                      'no7': {always: 0, noalways: 0},
                      'no8': {always: 0, noalways: 0},
                      'no9': {always: 0, noalways: 0},
                      'no10': {always: 0, noalways: 0},
                      'no11': {always: 0, noalways: 0},
                      'no12': {always: 0, noalways: 0},
                      'no13': {always: 0, noalways: 0},
                      'no14': {always: 0, noalways: 0},
                      'no15': {always: 0, noalways: 0},
                      'no16': {always: 0, noalways: 0},
                      'no17': {always: 0, noalways: 0},
                      'no18': {always: 0, noalways: 0},
                      'no19': {always: 0, noalways: 0},
                      'no20': {always: 0, noalways: 0},
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
                          numno11: 1,
                          numno12: 1,
                          numno13: 1,
                          numno14: 1,
                          numno15: 1,
                          numno16: 1,
                          numno17: 1,
                          numno18: 1,
                          numno19: 1,
                          numno20: 1,
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
                          numTwono11: 1,
                          numTwono12: 1,
                          numTwono13: 1,
                          numTwono14: 1,
                          numTwono15: 1,
                          numTwono16: 1,
                          numTwono17: 1,
                          numTwono18: 1,
                          numTwono19: 1,
                          numTwono20: 1,
                          selectno1: 'always',
                          selectno2: 'always',
                          selectno3: 'always',
                          selectno4: 'always',
                          selectno5: 'always',
                          selectno6: 'always',
                          selectno7: 'always',
                          selectno8: 'always',
                          selectno9: 'always',
                          selectno10: 'always',
                          selectno11: 'always',
                          selectno12: 'always',
                          selectno13: 'always',
                          selectno14: 'always',
                          selectno15: 'always',
                          selectno16: 'always',
                          selectno17: 'always',
                          selectno18: 'always',
                          selectno19: 'always',
                          selectno20: 'always',
                          selectTwono1: 'always',
                          selectTwono2: 'always',
                          selectTwono3: 'always',
                          selectTwono4: 'always',
                          selectTwono5: 'always',
                          selectTwono6: 'always',
                          selectTwono7: 'always',
                          selectTwono8: 'always',
                          selectTwono9: 'always',
                          selectTwono10: 'always',
                          selectTwono11: 'always',
                          selectTwono12: 'always',
                          selectTwono13: 'always',
                          selectTwono14: 'always',
                          selectTwono15: 'always',
                          selectTwono16: 'always',
                          selectTwono17: 'always',
                          selectTwono18: 'always',
                          selectTwono19: 'always',
                          selectTwono20: 'always',
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
                          btnno11: false,
                          btnno12: false,
                          btnno13: false,
                          btnno14: false,
                          btnno15: false,
                          btnno16: false,
                          btnno17: false,
                          btnno18: false,
                          btnno19: false,
                          btnno20: false,
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
                          showNumno11: 0,
                          showNumno12: 0,
                          showNumno13: 0,
                          showNumno14: 0,
                          showNumno15: 0,
                          showNumno16: 0,
                          showNumno17: 0,
                          showNumno18: 0,
                          showNumno19: 0,
                          showNumno20: 0,
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
                              no11: true,
                              no12: true,
                              no13: true,
                              no14: true,
                              no15: true,
                              no16: true,
                              no17: true,
                              no18: true,
                              no19: true,
                              no20: true,
                          },
                      }
                  };
              }else if(this.pageTabSelect=='heoeRoad'){
                  this.heoeRoad = {
                      'no1':{odd:0,even:0},
                      'no2':{odd:0,even:0},
                      'no3':{odd:0,even:0},
                      'no4':{odd:0,even:0},
                      'no5':{odd:0,even:0},
                      'no6':{odd:0,even:0},
                      'no7':{odd:0,even:0},
                      'no8':{odd:0,even:0},
                      filters:{
                          numno1:1,
                              numno2:1,
                              numno3:1,
                              numno4:1,
                              numno5:1,
                              numno6:1,
                              numno7:1,
                              numno8:1,
                              numTwono1:1,
                              numTwono2:1,
                              numTwono3:1,
                              numTwono4:1,
                              numTwono5:1,
                              numTwono6:1,
                              numTwono7:1,
                              numTwono8:1,
                              selectno1:'odd',
                              selectno2:'odd',
                              selectno3:'odd',
                              selectno4:'odd',
                              selectno5:'odd',
                              selectno6:'odd',
                              selectno7:'odd',
                              selectno8:'odd',
                              selectTwono1:'odd',
                              selectTwono2:'odd',
                              selectTwono3:'odd',
                              selectTwono4:'odd',
                              selectTwono5:'odd',
                              selectTwono6:'odd',
                              selectTwono7:'odd',
                              selectTwono8:'odd',
                              btnno1:false,
                              btnno2:false,
                              btnno3:false,
                              btnno4:false,
                              btnno5:false,
                              btnno6:false,
                              btnno7:false,
                              btnno8:false,
                              showNumno1:0,
                              showNumno2:0,
                              showNumno3:0,
                              showNumno4:0,
                              showNumno5:0,
                              showNumno6:0,
                              showNumno7:0,
                              showNumno8:0,

                              checkBoxList:{
                              no1:true,
                                  no2:true,
                                  no3:true,
                                  no4:true,
                                  no5:true,
                                  no6:true,
                                  no7:true,
                                  no8:true,
                          },
                      },
                  };
              }else if(this.pageTabSelect=='wsouRoad'){
                  this.wsouRoad={
                      'no1':{over:0,under:0},
                      'no2':{over:0,under:0},
                      'no3':{over:0,under:0},
                      'no4':{over:0,under:0},
                      'no5':{over:0,under:0},
                      'no6':{over:0,under:0},
                      'no7':{over:0,under:0},
                      'no8':{over:0,under:0},
                      filters:{
                          numno1:1,
                              numno2:1,
                              numno3:1,
                              numno4:1,
                              numno5:1,
                              numno6:1,
                              numno7:1,
                              numno8:1,
                              numTwono1:1,
                              numTwono2:1,
                              numTwono3:1,
                              numTwono4:1,
                              numTwono5:1,
                              numTwono6:1,
                              numTwono7:1,
                              numTwono8:1,
                              selectno1:'over',
                              selectno2:'over',
                              selectno3:'over',
                              selectno4:'over',
                              selectno5:'over',
                              selectno6:'over',
                              selectno7:'over',
                              selectno8:'over',
                              selectTwono1:'over',
                              selectTwono2:'over',
                              selectTwono3:'over',
                              selectTwono4:'over',
                              selectTwono5:'over',
                              selectTwono6:'over',
                              selectTwono7:'over',
                              selectTwono8:'over',
                              btnno1:false,
                              btnno2:false,
                              btnno3:false,
                              btnno4:false,
                              btnno5:false,
                              btnno6:false,
                              btnno7:false,
                              btnno8:false,
                              showNumno1:0,
                              showNumno2:0,
                              showNumno3:0,
                              showNumno4:0,
                              showNumno5:0,
                              showNumno6:0,
                              showNumno7:0,
                              showNumno8:0,

                              checkBoxList:{
                              no1:true,
                                  no2:true,
                                  no3:true,
                                  no4:true,
                                  no5:true,
                                  no6:true,
                                  no7:true,
                                  no8:true,
                          },
                      },
                  };
              }else if(this.pageTabSelect=='dtRoad'){
                  this.dtRoad={
                      'no1':{dragon:0,tiger:0},
                      'no2':{dragon:0,tiger:0},
                      'no3':{dragon:0,tiger:0},
                      'no4':{dragon:0,tiger:0},
                      filters:{
                          numno1:1,
                              numno2:1,
                              numno3:1,
                              numno4:1,
                              numTwono1:1,
                              numTwono2:1,
                              numTwono3:1,
                              numTwono4:1,
                              selectno1:'dragon',
                              selectno2:'dragon',
                              selectno3:'dragon',
                              selectno4:'dragon',
                              selectTwono1:'dragon',
                              selectTwono2:'dragon',
                              selectTwono3:'dragon',
                              selectTwono4:'dragon',
                              btnno1:false,
                              btnno2:false,
                              btnno3:false,
                              btnno4:false,
                              showNumno1:0,
                              showNumno2:0,
                              showNumno3:0,
                              showNumno4:0,

                              checkBoxList:{
                              no1:true,
                                  no2:true,
                                  no3:true,
                                  no4:true,
                          },
                      },
                  };
              }else if(this.pageTabSelect=='sdRoad'){
                  this.sdRoad={
                      'no1':{over:0,under:0,odd:0,even:0},
                      'no2':{over:0,under:0,odd:0,even:0},
                      'no3':{over:0,under:0,odd:0,even:0},
                      'no4':{over:0,under:0,odd:0,even:0},
                      'no5':{over:0,under:0,odd:0,even:0},
                      'no6':{over:0,under:0,odd:0,even:0},
                      'no7':{over:0,under:0,odd:0,even:0},
                      'no8':{over:0,under:0,odd:0,even:0},
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
                              numno6ou:1,
                              numno6oe:1,
                              numno7ou:1,
                              numno7oe:1,
                              numno8ou:1,
                              numno8oe:1,
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
                              numTwono6ou:1,
                              numTwono6oe:1,
                              numTwono7ou:1,
                              numTwono7oe:1,
                              numTwono8ou:1,
                              numTwono8oe:1,
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
                              selectno6ou:'over',
                              selectno6oe:'odd',
                              selectno7ou:'over',
                              selectno7oe:'odd',
                              selectno8ou:'over',
                              selectno8oe:'odd',
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
                              selectTwono6ou:'over',
                              selectTwono6oe:'odd',
                              selectTwono7ou:'over',
                              selectTwono7oe:'odd',
                              selectTwono8ou:'over',
                              selectTwono8oe:'odd',
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
                              btnno6ou:false,
                              btnno6oe:false,
                              btnno7ou:false,
                              btnno7oe:false,
                              btnno8ou:false,
                              btnno8oe:false,
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
                              showNumno6ou:0,
                              showNumno6oe:0,
                              showNumno7ou:0,
                              showNumno7oe:0,
                              showNumno8ou:0,
                              showNumno8oe:0,
                              checkNoBoxList:{
                              no1:true,
                                  no2:true,
                                  no3:true,
                                  no4:true,
                                  no5:true,
                                  no6:true,
                                  no7:true,
                                  no8:true,
                          },
                          checkRoadBoxList:{
                              ou:true,
                                  oe:true,
                          },
                      },
                  };
              }else if(this.pageTabSelect=='fwRoad'){
                  this.fwRoad={
                      'no1':{east:0, south:0, west:0,north:0},
                      'no2':{east:0, south:0, west:0,north:0},
                      'no3':{east:0, south:0, west:0,north:0},
                      'no4':{east:0, south:0, west:0,north:0},
                      'no5':{east:0, south:0, west:0,north:0},
                      'no6':{east:0, south:0, west:0,north:0},
                      'no7':{east:0, south:0, west:0,north:0},
                      'no8':{east:0, south:0, west:0,north:0},
                      filters:{
                          numno1:1,
                              numno2:1,
                              numno3:1,
                              numno4:1,
                              numno5:1,
                              numno6:1,
                              numno7:1,
                              numno8:1,
                              numTwono1:1,
                              numTwono2:1,
                              numTwono3:1,
                              numTwono4:1,
                              numTwono5:1,
                              numTwono6:1,
                              numTwono7:1,
                              numTwono8:1,
                              selectno1:'east',
                              selectno2:'east',
                              selectno3:'east',
                              selectno4:'east',
                              selectno5:'east',
                              selectno6:'east',
                              selectno7:'east',
                              selectno8:'east',
                              selectTwono1:'east',
                              selectTwono2:'east',
                              selectTwono3:'east',
                              selectTwono4:'east',
                              selectTwono5:'east',
                              selectTwono6:'east',
                              selectTwono7:'east',
                              selectTwono8:'east',
                              btnno1:false,
                              btnno2:false,
                              btnno3:false,
                              btnno4:false,
                              btnno5:false,
                              btnno6:false,
                              btnno7:false,
                              btnno8:false,
                              showNumno1:0,
                              showNumno2:0,
                              showNumno3:0,
                              showNumno4:0,
                              showNumno5:0,
                              showNumno6:0,
                              showNumno7:0,
                              showNumno8:0,

                              checkBoxList:{
                              no1:true,
                                  no2:true,
                                  no3:true,
                                  no4:true,
                                  no5:true,
                                  no6:true,
                                  no7:true,
                                  no8:true,
                          },
                      },
                  };
              }
            },
            customFormatter(date,flag) {
                this.dateStr =  this.$moment(date).format('YYYY-MM-DD');
                if(!flag){
                    this.totalDateTime = this.$moment(date).format('MM-DD');
                }
                if(this.pageTabSelect=='instantDraw' || this.pageTabSelect=='hmlstj'){
                    this.getHisList();
                }else if(this.pageTabSelect =='roadA' || this.pageTabSelect =='sumRoad'
                    || this.pageTabSelect =='noRoad' || this.pageTabSelect =='zfbRoad'
                    || this.pageTabSelect =='heoeRoad' || this.pageTabSelect =='wsouRoad'
                    || this.pageTabSelect=='dtRoad' || this.pageTabSelect=='sdRoad'
                    || this.pageTabSelect=='fwRoad'){
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
            readResultRoad(){
                let self = this;
                let changlongArr = [];
                self.$api.Lottery.getLotteryRoad(self.lotteryKey+'/'+self.dateStr).then(val=>{
                    if(val.success){
                        let arr = val.data.changlong;
                        for (let obj of arr) {
                            for (let key in obj) {
                                let param = {'type': key.split('_')[0], 'oddsKey': key.split('_')[1], 'number': obj[key]};
                                changlongArr.push(param);
                            }
                        }
                        self.changlongList = changlongArr;
                    }
                });
            },
            clearResultScreenFun(){
                this.resultNumScreen.splice(0,this.resultNumScreen.length);
                this.resultTypeScreen = '';
            },
            numScreenFun(num){
                if(!this.topLi.hmfb){
                    this.topLi.hmfb = true;
                }
                let flag = this.resultNumScreen.findIndex(function (value, index, arr){return value == this.val}, {val:num});
                if(this.resultTypeScreen){this.resultTypeScreen = ''};

                if(flag!=-1){
                    this.resultNumScreen.splice(flag,1);
                }else{
                    this.resultNumScreen.push(num);
                }
            },
            numTypeScreenFun(type){
                this.resultNumScreen.splice(0,this.resultNumScreen.length);

                this.resultTypeScreen==type?this.resultTypeScreen = '':this.resultTypeScreen = type;
            },
            resultClassCss(num,listIndex,resultIndex){
                let cssReturn = '';
                if(this.resultShowType=='num'){
                    cssReturn =  this.lotteryKey!='xync' && num>= 19?'numblue numredkong':this.lotteryKey!='xync'?'numblue':'ncnum'+num;
                }else if(this.resultShowType=='numOu'){
                    if(num>5){
                        cssReturn = 'bluebig';
                    }else{
                        cssReturn = 'bluesmall';
                    }
                }else if(this.resultShowType=='numOe'){
                    if(num%2==0){
                        cssReturn = 'blueeven';
                    }else{
                        cssReturn = 'bluesingular';
                    }
                }
                if(this.topLi.hmfb){
                    if(this.resultTypeScreen){
                        if(this.resultTypeScreen == 'over'){
                            return num>5?cssReturn:cssReturn+' selectedOpacity';
                        }else if(this.resultTypeScreen == 'under'){
                            return num<=5?cssReturn:cssReturn+' selectedOpacity';
                        }else if(this.resultTypeScreen == 'odd'){
                            return num%2!=0?cssReturn:cssReturn+' selectedOpacity';
                        }else if(this.resultTypeScreen == 'even'){
                            return num%2==0?cssReturn:cssReturn+' selectedOpacity';
                        }else if(this.resultTypeScreen == 'pair'){
                            if(listIndex==0){
                                return this.hisList[listIndex+1].result[resultIndex] == num?cssReturn:cssReturn+' selectedOpacity';
                            }else{
                                if(listIndex==this.hisList.length-1){
                                    return this.hisList[listIndex-1].result[resultIndex] == num?cssReturn:cssReturn+' selectedOpacity';
                                }
                                return this.hisList[listIndex-1].result[resultIndex] == num?cssReturn:this.hisList[listIndex+1].result[resultIndex] == num?cssReturn:cssReturn+' selectedOpacity';
                            }
                        }
                    }else if(this.resultNumScreen.length>0){
                        let index = this.resultNumScreen.findIndex(function (value, index, arr){return value == this.val}, {val:num});
                        return index!=-1?cssReturn:cssReturn+' selectedOpacity';
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
                    if(this.topLi.cltx){
                        this.readResultRoad();
                    }
                }
                if (num == 3) {
                    this.resultNumScreen.splice(0,this.resultNumScreen.length);
                    this.resultTypeScreen = '';
                    this.topLi.hmfb = this.topLi.hmfb ? false : true;
                }
            },
            getHisList() {
                this.$api.Lottery.getHisByDayList(this.lotteryKey+"/"+this.dateStr).then(val => {
                    this.hisList = [];
                    if (val.success) {
                        this.hisList = val.data;
                        let jrsm = [];
                        for (let i = 0; i < 8; i++) {
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
                                    if(nums[i]<10){
                                        nums[i] = '0'+nums[i];
                                    }
                                    map.push(nums[i])
                                    nums[i] > 10 ? jrsm[i].da += 1 : jrsm[i].xiao += 1;
                                    (nums[i] % 2) == 1 ? jrsm[i].dan += 1 : jrsm[i].suan += 1;
                                }
                                items.result = map;
                                items.gyh15lh = this.computeKLSFZHLH(items.result);
                                items.gyh15lh.heOU == 'OVER' ? gylh.gyhda += 1 : gylh.gyhxiao += 1;
                                items.gyh15lh.heOE == 'ODD' ? gylh.gyhdan += 1 : gylh.gyhsuan += 1;
                                items.gyh15lh.no1DT == 'DRAGON' ? gylh.lh11 += 1 : gylh.lh12 += 1;
                                items.gyh15lh.no2DT == 'DRAGON' ? gylh.lh21 += 1 : gylh.lh22 += 1;
                                items.gyh15lh.no3DT == 'DRAGON' ? gylh.lh31 += 1 : gylh.lh32 += 1;
                                items.gyh15lh.no4DT == 'DRAGON' ? gylh.lh41 += 1 : gylh.lh42 += 1;
                            }
                        })
                        //this.hisList = this.hisList.slice(0,200);
                        this.jrsmList = jrsm;
                        this.gylhMap = gylh;

                    }
                    this.changePageTabSelect(this.pageTabSelect);

                })
                if(this.topLi.cltx){
                    this.readResultRoad();
                }
            },
            jrhmtjColorChange(num){
                return this.jrhmtjType.red && num>=15 && num<=30?'color:red':this.jrhmtjType.blue && num>=31 && num<=40?'color:#1163b1':this.jrhmtjType.green && num>=41?'color:#18ab4c':'';
            },
            getLuzhuFun(params){
                let self = this;
                this.$api.Lottery.getLotteryRoad(params + '/' + self.dateStr).then(val=>{
                    let luzhuParams = {};
                    for (let obj in val.data) {
                        if (-1 != obj.indexOf('sum')) {
                            luzhuParams[obj] = self.$Utils.getArr(val.data[obj]);
                        }
                        if (obj.indexOf('no') != -1 && obj.split('no')[1].length > 2) {
                            luzhuParams[obj] = self.$Utils.getArr(val.data[obj]);
                        } else if (-1 == obj.indexOf('sum')) {
                            if (obj == 'no1') {
                                self.placingNumber = val.data[obj];
                            }
                            luzhuParams[obj] = val.data[obj];
                        }
                    }
                    if(self.pageTabSelect=='noRoad'){
                        for (let key in luzhuParams) {
                            if (-1 != key.indexOf('no1bool')) {
                                self.noLuzhuList.no1[key] = luzhuParams[key];
                            } else if (-1 != key.indexOf('no2bool')) {
                                self.noLuzhuList.no2[key] = luzhuParams[key];
                            } else if (-1 != key.indexOf('no3bool')) {
                                self.noLuzhuList.no3[key] = luzhuParams[key];
                            } else if (-1 != key.indexOf('no4bool')) {
                                self.noLuzhuList.no4[key] = luzhuParams[key];
                            } else if (-1 != key.indexOf('no5bool')) {
                                self.noLuzhuList.no5[key] = luzhuParams[key];
                            } else if (-1 != key.indexOf('no6bool')) {
                                self.noLuzhuList.no6[key] = luzhuParams[key];
                            } else if (-1 != key.indexOf('no7bool')) {
                                self.noLuzhuList.no7[key] = luzhuParams[key];
                            } else if (-1 != key.indexOf('no8bool')) {
                                self.noLuzhuList.no8[key] = luzhuParams[key];
                            } else if (-1 != key.indexOf('no9bool')) {
                                self.noLuzhuList.no9[key] = luzhuParams[key];
                            } else if (-1 != key.indexOf('no10bool')) {
                                self.noLuzhuList.no10[key] = luzhuParams[key];
                            } else if (-1 != key.indexOf('no11bool')) {
                                self.noLuzhuList.no11[key] = luzhuParams[key];
                            } else if (-1 != key.indexOf('no12bool')) {
                                self.noLuzhuList.no12[key] = luzhuParams[key];
                            } else if (-1 != key.indexOf('no13bool')) {
                                self.noLuzhuList.no13[key] = luzhuParams[key];
                            } else if (-1 != key.indexOf('no14bool')) {
                                self.noLuzhuList.no14[key] = luzhuParams[key];
                            } else if (-1 != key.indexOf('no15bool')) {
                                self.noLuzhuList.no15[key] = luzhuParams[key];
                            } else if (-1 != key.indexOf('no16bool')) {
                                self.noLuzhuList.no16[key] = luzhuParams[key];
                            } else if (-1 != key.indexOf('no17bool')) {
                                self.noLuzhuList.no17[key] = luzhuParams[key];
                            } else if (-1 != key.indexOf('no18bool')) {
                                self.noLuzhuList.no18[key] = luzhuParams[key];
                            } else if (-1 != key.indexOf('no19bool')) {
                                self.noLuzhuList.no19[key] = luzhuParams[key];
                            } else if (-1 != key.indexOf('no20bool')) {
                                self.noLuzhuList.no20[key] = luzhuParams[key];
                            }
                        }
                    }else{
                        for (let key in luzhuParams) {
                            if (-1 != key.indexOf('sum')) {
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
                            } else if (-1 != key.indexOf('no6')) {
                                self.luzhuList.no6[key] = luzhuParams[key];
                            } else if (-1 != key.indexOf('no7')) {
                                self.luzhuList.no7[key] = luzhuParams[key];
                            } else if (-1 != key.indexOf('no8')) {
                                self.luzhuList.no8[key] = luzhuParams[key];
                            }
                        }
                    }
                    if(this.pageTabSelect=='roadA'){
                        for(let arrKey in self.luzhuList) {
                            for (let key in self.luzhuList[arrKey]) {
                                let num1 = 0;
                                let num2 = 0;
                                let num3 = 0;
                                let num4 = 0;
                                let type = ['1', '2', null, null];
                                if (key.indexOf('ou')!= -1 || key.indexOf('oe')!=-1 || key.indexOf('dtt')!=-1 || key.indexOf('wsou')!=-1 || key.indexOf('heoe')!=-1  || key.indexOf('zfb')!=-1  || key.indexOf('fw')!=-1 || key.indexOf('ws')!=-1) {
                                    self.luzhuList[arrKey][key].forEach(val => {
                                        val.forEach(obj => {
                                            obj.forEach(item => {
                                                if (item == 'over' && key.indexOf('ws') != -1) {
                                                    num1++;
                                                    type[0] = 'weiover';
                                                }else if (item == 'under' && key.indexOf('ws') != -1) {
                                                    num2++;
                                                    type[1] = 'weiunder';
                                                }else if (item == 'over' && key.indexOf('wsou') == -1) {
                                                    num1++;
                                                    type[0] = 'over';
                                                } else if (item == 'under' && key.indexOf('wsou') == -1) {
                                                    num2++;
                                                    type[1] = 'under';
                                                } else if (item == 'odd' && key.indexOf('heoe') == -1) {
                                                    num1++;
                                                    type[0] = 'odd';
                                                } else if (item == 'even' && key.indexOf('heoe') == -1) {
                                                    num2++;
                                                    type[1] = 'even';
                                                } else if (item == 'dragon') {
                                                    num1++;
                                                    type[0] = 'dragon';
                                                } else if (item == 'tiger') {
                                                    num2++;
                                                    type[1] = 'tiger';
                                                }else if (item == 'odd' && key.indexOf('heoe') != -1) {
                                                    num1++;
                                                    type[0] = 'heodd';
                                                }else if (item == 'even' && key.indexOf('heoe') != -1) {
                                                    num2++;
                                                    type[1] = 'heeven';
                                                }else if (item == 'zhong') {
                                                    num1++;
                                                    type[0] = 'zhong';
                                                }else if (item == 'fa') {
                                                    num2++;
                                                    type[1] = 'fa';
                                                }else if (item == 'bai') {
                                                    num3++;
                                                    type[2] = 'bai';
                                                }else if (item == 'east') {
                                                    num1++;
                                                    type[0] = 'east';
                                                }else if (item == 'south') {
                                                    num2++;
                                                    type[1] = 'south';
                                                }else if (item == 'west') {
                                                    num3++;
                                                    type[2] = 'west';
                                                }else if (item == 'north') {
                                                    num4++;
                                                    type[3] = 'north';
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
                                    self.roadA[arrKey][type[3]] = num4;
                                }
                            }
                        }
                    }else if(self.pageTabSelect=='sumRoad'){
                        for(let arrKey in self.luzhuList) {
                            for (let key in self.luzhuList[arrKey]) {
                                let num1 = 0;
                                let num2 = 0;
                                let type = ['1', '2'];
                                if (arrKey.indexOf('sum')!= -1) {
                                    self.luzhuList[arrKey][key].forEach(val => {
                                        val.forEach(obj => {
                                            obj.forEach(item => {
                                                if (item == 'over' && key.indexOf('ws') != -1) {
                                                    num1++;
                                                    type[0] = 'weiover';
                                                }else if (item == 'under' && key.indexOf('ws') != -1) {
                                                    num2++;
                                                    type[1] = 'weiunder';
                                                }else if (item == 'over' && key.indexOf('wsou') == -1) {
                                                    num1++;
                                                    type[0] = 'over';
                                                } else if (item == 'under' && key.indexOf('wsou') == -1) {
                                                    num2++;
                                                    type[1] = 'under';
                                                } else if (item == 'odd' && key.indexOf('heoe') == -1) {
                                                    num1++;
                                                    type[0] = 'odd';
                                                } else if (item == 'even' && key.indexOf('heoe') == -1) {
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
                    }else if(self.pageTabSelect=='zfbRoad'){
                        for(let arrKey in self.luzhuList) {
                            for (let key in self.luzhuList[arrKey]) {
                                if (key.indexOf('zfb')!= -1) {
                                    let num1 = 0;
                                    let num2 = 0;
                                    let num3 = 0;
                                    let type = ['1', '2','3'];
                                    self.luzhuList[arrKey][key].forEach(val => {
                                        val.forEach(obj => {
                                            obj.forEach(item => {

                                                if (item == 'zhong') {
                                                    num1++;
                                                    type[0] = 'zhong';
                                                }else if (item == 'fa') {
                                                    num2++;
                                                    type[1] = 'fa';
                                                }else if (item == 'bai') {
                                                    num3++;
                                                    type[2] = 'bai';
                                                }
                                            })
                                        })
                                    })
                                    self.zfbRoad[arrKey][type[0]] = num1;
                                    self.zfbRoad[arrKey][type[1]] = num2;
                                    self.zfbRoad[arrKey][type[2]] = num3;
                                }
                            }
                        }
                    }else if(self.pageTabSelect=='heoeRoad'){
                        for(let arrKey in self.luzhuList) {
                            for (let key in self.luzhuList[arrKey]) {

                                if (key.indexOf('heoe')!= -1) {
                                    let num1 = 0;
                                    let num2 = 0;
                                    let type = ['1', '2'];
                                    self.luzhuList[arrKey][key].forEach(val => {
                                        val.forEach(obj => {
                                            obj.forEach(item => {

                                                if (item == 'odd') {
                                                    num1++;
                                                    type[0] = 'odd';
                                                }else if (item == 'even') {
                                                    num2++;
                                                    type[1] = 'even';
                                                }
                                            })

                                        })

                                    })
                                    self.heoeRoad[arrKey][type[0]] = num1;
                                    self.heoeRoad[arrKey][type[1]] = num2;

                                }

                            }
                        }
                    }else if(self.pageTabSelect=='wsouRoad'){
                        for(let arrKey in self.luzhuList) {
                            for (let key in self.luzhuList[arrKey]) {

                                if (key.indexOf('wsou')!= -1) {
                                    let num1 = 0;
                                    let num2 = 0;
                                    let type = ['1', '2'];
                                    self.luzhuList[arrKey][key].forEach(val => {
                                        val.forEach(obj => {
                                            obj.forEach(item => {

                                                if (item == 'over') {
                                                    num1++;
                                                    type[0] = 'over';
                                                }else if (item == 'under') {
                                                    num2++;
                                                    type[1] = 'under';
                                                }
                                            })

                                        })

                                    })
                                    self.wsouRoad[arrKey][type[0]] = num1;
                                    self.wsouRoad[arrKey][type[1]] = num2;

                                }

                            }
                        }
                    }else if(self.pageTabSelect=='dtRoad'){
                        for(let arrKey in self.luzhuList) {
                            for (let key in self.luzhuList[arrKey]) {

                                if (key.indexOf('dtt')!= -1) {
                                    let num1 = 0;
                                    let num2 = 0;
                                    let type = ['1', '2'];
                                    self.luzhuList[arrKey][key].forEach(val => {
                                        val.forEach(obj => {
                                            obj.forEach(item => {

                                                if (item == 'dragon') {
                                                    num1++;
                                                    type[0] = 'dragon';
                                                }else if (item == 'tiger') {
                                                    num2++;
                                                    type[1] = 'tiger';
                                                }
                                            })

                                        })

                                    })
                                    self.dtRoad[arrKey][type[0]] = num1;
                                    self.dtRoad[arrKey][type[1]] = num2;

                                }

                            }
                        }
                    }else if(self.pageTabSelect=='sdRoad'){
                        for(let arrKey in self.luzhuList) {
                            for (let key in self.luzhuList[arrKey]) {

                                if (arrKey != 'sum' && (key.indexOf('ou')!= -1 || key.indexOf('oe')!= -1)) {
                                    let num1 = 0;
                                    let num2 = 0;
                                    let type = ['1', '2'];
                                    self.luzhuList[arrKey][key].forEach(val => {
                                        val.forEach(obj => {
                                            obj.forEach(item => {

                                                if (item == 'over') {
                                                    num1++;
                                                    type[0] = 'over';
                                                }else if (item == 'under') {
                                                    num2++;
                                                    type[1] = 'under';
                                                }else if (item == 'odd') {
                                                    num1++;
                                                    type[0] = 'odd';
                                                }else if (item == 'even') {
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
                    }else if(self.pageTabSelect=='fwRoad'){
                        for(let arrKey in self.luzhuList) {
                            for (let key in self.luzhuList[arrKey]) {

                                if (arrKey != 'sum' && key.indexOf('fw')!= -1) {
                                    let num1 = 0;
                                    let num2 = 0;
                                    let num3 = 0;
                                    let num4 = 0;
                                    let type = ['1', '2','3','4'];
                                    self.luzhuList[arrKey][key].forEach(val => {
                                        val.forEach(obj => {
                                            obj.forEach(item => {

                                                if (item == 'east') {
                                                    num1++;
                                                    type[0] = 'east';
                                                }else if (item == 'south') {
                                                    num2++;
                                                    type[1] = 'south';
                                                }else if (item == 'west') {
                                                    num3++;
                                                    type[2] = 'west';
                                                }else if (item == 'north') {
                                                    num4++;
                                                    type[3] = 'north';
                                                }
                                            })

                                        })

                                    })
                                    self.fwRoad[arrKey][type[0]] = num1;
                                    self.fwRoad[arrKey][type[1]] = num2;
                                    self.fwRoad[arrKey][type[2]] = num3;
                                    self.fwRoad[arrKey][type[3]] = num4;

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
                    }
                })
                console.log('咯朱',self.noLuzhuList);
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
                        item.da += parseInt(res[no]) > 10 ? 1 : 0;
                        item.xiao += parseInt(res[no]) > 10 ? 0 : 1;
                        item.dan += (parseInt(res[no]) % 2 === 1) ? 1 : 0;
                        item.suan += (parseInt(res[no]) % 2 === 1) ? 0 : 1;
                    })
                })
            },
            changePageTabSelect(newVal) {
                if (newVal == 'dsdxls') {
                    let nos = []
                    for (let i = 0; i < 8; i++) {
                        nos.push({noda: 0, noxiao: 0, nodan: 0, nosuan: 0})
                    }
                    this.hisList.forEach((val) => {
                        let res = val.result;
                        for (let i = 0; i < res.length; i++) {
                            nos[i].noda += parseInt(res[i]) > 10 ? 1 : 0;
                            nos[i].noxiao += parseInt(res[i]) > 10 ? 0 : 1;
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
                if (newVal == 'hmlstj') {
                    let hmHis = {
                        no0: 0, no1: 0, no2: 0, no3: 0, no4: 0, no5: 0, no6: 0, no7: 0, no8: 0, no9: 0,
                        dan: 0, suan: 0, da: 0, xiao: 0, long: 0, hu: 0
                    };
                    this.hisList.forEach((val) => {
                        let res = val.result;
                        hmHis.long += val.gyh15lh.no1DT == 'DRAGON' ? 1 : 0;
                        hmHis.long += val.gyh15lh.no2DT == 'DRAGON' ? 1 : 0;
                        hmHis.long += val.gyh15lh.no3DT == 'DRAGON' ? 1 : 0;
                        hmHis.long += val.gyh15lh.no4DT == 'DRAGON' ? 1 : 0;
                        hmHis.hu += val.gyh15lh.no1DT == 'TIGER' ? 1 : 0;
                        hmHis.hu += val.gyh15lh.no2DT == 'TIGER' ? 1 : 0;
                        hmHis.hu += val.gyh15lh.no3DT == 'TIGER' ? 1 : 0;
                        hmHis.hu += val.gyh15lh.no4DT == 'TIGER' ? 1 : 0;
                        for (let i = 0; i < res.length; i++) {
                            hmHis.no1 += parseInt(res[i]) == 1 ? 1 : 0;
                            hmHis.no2 += parseInt(res[i]) == 2 ? 1 : 0;
                            hmHis.no3 += parseInt(res[i]) == 3 ? 1 : 0;
                            hmHis.no4 += parseInt(res[i]) == 4 ? 1 : 0;
                            hmHis.no5 += parseInt(res[i]) == 5 ? 1 : 0;
                            hmHis.no6 += parseInt(res[i]) == 6 ? 1 : 0;
                            hmHis.no7 += parseInt(res[i]) == 7 ? 1 : 0;
                            hmHis.no8 += parseInt(res[i]) == 8 ? 1 : 0;
                            hmHis.no9 += parseInt(res[i]) == 9 ? 1 : 0;
                            hmHis.da += parseInt(res[i]) > 10 ? 1 : 0;
                            hmHis.xiao += parseInt(res[i]) > 10 ? 0 : 1;
                            hmHis.dan += (parseInt(res[i]) % 2 === 1) ? 1 : 0;
                            hmHis.suan += (parseInt(res[i]) % 2 === 1) ? 0 : 1;
                        }
                    });
                    this.hisNumList = hmHis;
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
                    for (let i = 0; i < 20; i++) {
                        let info = {
                            zk1: 0, wk1: 0,
                            zk2: 0, wk2: 0,
                            zk3: 0, wk3: 0,
                            zk4: 0, wk4: 0,
                            zk5: 0, wk5: 0,
                            zk6: 0, wk6: 0,
                            zk7: 0, wk7: 0,
                            zk8: 0, wk8: 0,
                        };
                        numList.push(info);
                    }

                    for (let i = this.hisList.length - 1; i >= 0; i--) {
                        let re = this.hisList[i];
                        for (let i = 0; i < 8; i++) {
                            let no = parseInt(re.result[i]);
                            if (i == no) {
                                for (let j = 0; j < 20; j++) {
                                    numList[j].zk1 += no == (j + 1) ? 1 : 0;
                                    numList[j].wk1 += no == (j + 1) ? numList[j].wk1 * -1 : 1;
                                }
                            }
                            if (i == 1) {
                                for (let j = 0; j < 20; j++) {
                                    numList[j].zk2 += no == (j + 1) ? 1 : 0;
                                    numList[j].wk2 += no == (j + 1) ? numList[j].wk2 * -1 : 1;
                                }
                            }
                            if (i == 2) {
                                for (let j = 0; j < 20; j++) {
                                    numList[j].zk3 += no == (j + 1) ? 1 : 0;
                                    numList[j].wk3 += no == (j + 1) ? numList[j].wk3 * -1 : 1;
                                }
                            }
                            if (i == 3) {
                                for (let j = 0; j < 20; j++) {
                                    numList[j].zk4 += no == (j + 1) ? 1 : 0;
                                    numList[j].wk4 += no == (j + 1) ? numList[j].wk4 * -1 : 1;
                                }
                            }
                            if (i == 4) {
                                for (let j = 0; j < 20; j++) {
                                    numList[j].zk5 += no == (j + 1) ? 1 : 0;
                                    numList[j].wk5 += no == (j + 1) ? numList[j].wk5 * -1 : 1;
                                }
                            }
                            if (i == 5) {
                                for (let j = 0; j < 20; j++) {
                                    numList[j].zk6 += no == (j + 1) ? 1 : 0;
                                    numList[j].wk6 += no == (j + 1) ? numList[j].wk6 * -1 : 1;
                                }
                            }
                            if (i == 6) {
                                for (let j = 0; j < 20; j++) {
                                    numList[j].zk7 += no == (j + 1) ? 1 : 0;
                                    numList[j].wk7 += no == (j + 1) ? numList[j].wk7 * -1 : 1;
                                }
                            }
                            if (i == 7) {
                                for (let j = 0; j < 20; j++) {
                                    numList[j].zk8 += no == (j + 1) ? 1 : 0;
                                    numList[j].wk8 += no == (j + 1) ? numList[j].wk8 * -1 : 1;
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
            this.dateStr =  this.$moment(new Date()).format('YYYY-MM-DD');
            if(this.pageTabSelect=='sumRoad' || this.pageTabSelect=='zfbRoad'
                || this.pageTabSelect=='roadA'  || this.pageTabSelect=='noRoad'
                || this.pageTabSelect=='heoeRoad' || this.pageTabSelect=='wsouRoad'
                || this.pageTabSelect=='dtRoad' || this.pageTabSelect=='sdRoad'
                || this.pageTabSelect=='fwRoad'){
                this.getLuzhuFun(this.lotteryKey);
            }else if(this.pageTabSelect=='instantDraw' || this.pageTabSelect=='hmlstj' || this.pageTabSelect=='lmsjtj'){
                this.getHisList();
            }
        }
    }
</script>
<style scoped>

</style>
