<template>
    <div class="autofutllwith bblist">
        <a-spin :spinning="loading">
            <div>
                <div class="mb10 df" v-show="reportShow">
                    <table border="0" align="center" cellpadding="5" cellspacing="1" class="popright "
                           style="border-collapse: separate; width: 100%;">
                        <tbody>
                        <tr>
                            <td style="width: 25%" class="forumrow txtright"><span class="maintxt mlr10">日期范围</span>
                            </td>
                            <td style="width: 75%" class="forumrowhighlight txtleft">
                                <div style="display: flex">
                                    <date-ranger @on-change="changeDate"></date-ranger>
                                    <div style="margin-left: 10px">
                                        <date-button @on-change="changeButton" showButton="true"></date-button>
                                    </div>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="forumrow txtright"><span class="maintxt mlr10">选择彩种:</span></td>
                            <td class="forumrowhighlight txtleft">
                                <a-select size="small" style="width: 120px" v-model="selLotteryId" placeholder="默认所有彩种"
                                          @change="handleChange">
                                    <a-select-option :key="0">所有彩种</a-select-option>
                                    <a-select-option style="width: 120px" v-for="item in lotterys"
                                                     :key="item.lotteryId">
                                        {{ item.lotteryName }}
                                    </a-select-option>
                                </a-select>
                            </td>
                        </tr>
                        <tr>
                            <td class="forumrow txtright"><span class="maintxt mlr10">选择期数:</span></td>
                            <td class="forumrowhighlight txtleft">
                                <a-select style="width: 120px" size="small" v-model="params.gameNo" show-search
                                          option-filter-prop="children" :filter-option="filterOption">
                                    <a-select-option v-for="no in gameNos" :key="no">
                                        {{ no }}
                                    </a-select-option>
                                </a-select>
                            </td>
                        </tr>
                        <tr>
                            <td class="forumrow txtright"><span class="maintxt mlr10">报表种类:</span></td>
                            <td class="forumrowhighlight txtleft">
                                <a-radio-group v-model="reportType" class="maintxt mlr10" size="small">
                                    <a-radio-button value="js">
                                        交收报表
                                    </a-radio-button>
                                    <a-radio-button value="fl">
                                        分类报表
                                    </a-radio-button>
                                </a-radio-group>
                            </td>
                        </tr>
                        <tr>
                            <td class="forumrow txtright"><span class="maintxt mlr10">结算状态:</span></td>
                            <td class="forumrowhighlight txtleft">
                                <a-radio-group v-model="params.status" class="maintxt mlr10" size="small">
                                    <a-radio-button value="DIVIDEND">
                                        已结算
                                    </a-radio-button>
                                    <a-radio-button value="WAITING">
                                        未结算
                                    </a-radio-button>
                                    <a-radio-button value="VOID">
                                        作废
                                    </a-radio-button>
                                </a-radio-group>
                            </td>
                        </tr>
                        <tr>
                            <td class="forumrow txtright"><span class="maintxt mlr10">用户名:</span></td>
                            <td class="forumrowhighlight txtleft">
                                <a-input-search v-model="params.username" style="width: 120px" size="small"
                                                placeholder="用户名"/>
                            </td>
                        </tr>
                        <tr>
                            <td colspan="2" class="forumrow txtleft">
                                <div style="float: left;margin-left: 50%">
                                    <a-button type="primary" icon="search" size="small" @click="searchLoad()">
                                        查询
                                    </a-button>
                                </div>
                            </td>
                        </tr>
                        </tbody>
                    </table>
                </div>
                <!-- 分类报表 -->
                <div v-show="reportTypeFL">
                    <div class="mb10">
                        <span class="blue"> 分类报表[ {{params.startTime}} -- {{params.endTime}}]</span>
                        <a-button type="primary" size="small" style="margin-left: 10px;" @click="returnChoice()">返回
                        </a-button>
                        <a-button type="primary" size="small" style="margin-left: 10px;" @click="loadWinLoseByKind()">
                            刷新
                        </a-button>
                    </div>
                    <table class="tableborder leftlist" border="0" align="center" cellpadding="5" cellspacing="1">
                        <thead>
                        <tr>
                            <th rowspan="2" width="150px;">种类</th>
                            <th colspan="3">{{$t("AGENT"+(userLevel+1))}}下线合计</th>
                            <th :colspan="userLevel==2?7:10">{{$t("AGENT"+(userLevel))}}：{{username}}</th>
                            <th width="140px;" rowspan="2" v-if="userLevel>1">上交货量</th>
                            <th width="140px;" rowspan="2" v-if="userLevel>1">应付上级</th>
                        </tr>
                        <tr>
                            <th>笔数</th>
                            <th>有效金额</th>
                            <th>下线输赢</th>
                            <th>应收下线</th>
                            <th>占成%</th>
                            <th>实占注单</th>
                            <th>占货比%</th>
                            <th>实占输赢</th>
                            <th>实占退水</th>
                            <th>实占结果</th>
                            <th v-if="userLevel>2">赚取退水</th>
                            <th v-if="userLevel>2">赚取赔差</th>
                            <th v-if="userLevel>2">赚水赔差后</th>
                        </tr>
                        </thead>
                        <template v-for="(kindList,yId) in kindMap">
                            <tr>
                                <td colspan="50" class="forumrow nohover">
                                    <a-divider style="top: 0px" orientation="left">{{$t(yId)}}</a-divider>
                                </td>
                            </tr>
                            <tr v-for="item in kindList" :class="{'hjend':item.username==='合计'}">
                                <td class="forumrow">
                                    {{item.kindName}}
                                </td>
                                <td class="forumrow">
                                    <span> {{$utils.getAnsZ(item.betCount)}}</span>
                                </td>
                                <td class="forumrow txtright">
                                    <span v-if="item.username==='合计'"> {{$utils.getAnsG(item.betAmt)}}</span>
                                    <a v-else @click="showKindDetail(item)">{{$utils.getAnsG(item.betAmt)}}</a>
                                </td>
                                <td class="forumrow txtright" :class="$utils.getColorCssG(item.betWinAmt)">
                                    {{$utils.getAnsG(item.betWinAmt)}}
                                </td>
                                <td class="forumrow txtright"
                                    :class="$utils.getColorCssG(item.toLowerWin+item.toLowerComm)">
                                    <a-tooltip>
                                        <template slot="title">
                                            <p>输赢：{{$utils.getAnsG(item.toLowerWin)}}</p>
                                            <p>退水：{{$utils.getAnsG(item.toLowerComm)}}</p>
                                        </template>
                                        <span>{{$utils.getAnsG(item.toLowerWin+item.toLowerComm)}}</span>
                                    </a-tooltip>
                                </td>
                                <td class="forumrow txtright">
                                    <span>{{$utils.getAnsG(item.actualPtBetAmt/item.betAmt*100)}}</span>
                                </td>
                                <td class="forumrow txtright" :class="$utils.getColorCssG(item.actualPtBetAmt)">
                                    <span>{{$utils.getAnsG(item.actualPtBetAmt)}}</span>
                                </td>
                                <td class="forumrow txtright">
                                    <span>{{$utils.getAnsG(item.actualPtBetAmt/kindList[kindList.length-1].actualPtBetAmt*100)}}</span>
                                </td>
                                <td class="forumrow txtright" :class="$utils.getColorCssG(item.actualWinAmt)">
                                    <span>{{$utils.getAnsG(item.actualWinAmt)}}</span>
                                </td>
                                <td class="forumrow txtright" :class="$utils.getColorCssG(item.actualCommAmt)">
                                    <span>{{$utils.getAnsG(item.actualCommAmt)}}</span>
                                </td>
                                <td class="forumrow txtright" :class="$utils.getColorCssG(item.actualResult)">
                                    <span>{{$utils.getAnsG(item.actualResult)}}</span>
                                </td>
                                <td class="forumrow txtright" v-if="userLevel>2"
                                    :class="$utils.getColorCssG(item.gainCommAmt)">
                                    <span>{{$utils.getAnsG(item.gainCommAmt)}}</span>
                                </td>
                                <td class="forumrow txtright" v-if="userLevel>2"
                                    :class="$utils.getColorCssG(item.gainOddsAmt)">
                                    <span>{{$utils.getAnsG(item.gainOddsAmt)}}</span>
                                </td>
                                <td class="forumrow txtright" v-if="userLevel>2"
                                    :class="$utils.getColorCssG(item.gainCommOdds)">
                                    <span>{{$utils.getAnsG(item.gainCommOdds)}}</span>
                                </td>
                                <td class="forumrow txtright" v-if="userLevel>1"
                                    :class="$utils.getColorCssG(item.toUperAmt)">
                                    <span>{{$utils.getAnsG(item.toUperAmt)}}</span>
                                </td>
                                <td class="forumrow txtright" v-if="userLevel>1"
                                    :class="$utils.getColorCssG(item.toUperWin)">
                                    <span>{{$utils.getAnsG(item.toUperWin)}}</span>
                                </td>
                            </tr>
                        </template>

                        <tr class="hjend" v-if="kindTotal.betCount">
                            <td class="forumrow">
                                总计
                            </td>
                            <td class="forumrow">
                                <span> {{$utils.getAnsZ(kindTotal.betCount)}}</span>
                            </td>
                            <td class="forumrow txtright">
                                <span>{{$utils.getAnsG(kindTotal.betAmt)}}</span>
                            </td>
                            <td class="forumrow txtright" :class="$utils.getColorCssG(kindTotal.betWinAmt)">
                                {{$utils.getAnsG(kindTotal.betWinAmt)}}
                            </td>
                            <td class="forumrow txtright"
                                :class="$utils.getColorCssG(kindTotal.toLowerWin+kindTotal.toLowerComm)">
                                <span>{{$utils.getAnsG(kindTotal.toLowerWin+kindTotal.toLowerComm)}}</span>
                            </td>
                            <td class="forumrow txtright">
                                <span>{{$utils.getAnsG(kindTotal.actualPtBetAmt/kindTotal.betAmt*100)}}</span>
                            </td>
                            <td class="forumrow txtright" :class="$utils.getColorCssG(kindTotal.actualPtBetAmt)">
                                <span>{{$utils.getAnsG(kindTotal.actualPtBetAmt)}}</span>
                            </td>
                            <td class="forumrow txtright">
                                <span>{{$utils.getAnsG(100)}}</span>
                            </td>
                            <td class="forumrow txtright" :class="$utils.getColorCssG(kindTotal.actualWinAmt)">
                                <span>{{$utils.getAnsG(kindTotal.actualWinAmt)}}</span>
                            </td>
                            <td class="forumrow txtright" :class="$utils.getColorCssG(kindTotal.actualCommAmt)">
                                <span>{{$utils.getAnsG(kindTotal.actualCommAmt)}}</span>
                            </td>
                            <td class="forumrow txtright" :class="$utils.getColorCssG(kindTotal.actualResult)">
                                <span>{{$utils.getAnsG(kindTotal.actualResult)}}</span>
                            </td>
                            <td class="forumrow txtright" v-if="userLevel>2">
                                <span>{{$utils.getAnsG(kindTotal.gainCommAmt)}}</span>
                            </td>
                            <td class="forumrow txtright" v-if="userLevel>2">
                                <span>{{$utils.getAnsG(kindTotal.gainOddsAmt)}}</span>
                            </td>
                            <td class="forumrow txtright" v-if="userLevel>1"
                                :class="$utils.getColorCssG(kindTotal.gainCommOdds)">
                                <span>{{$utils.getAnsG(kindTotal.gainCommOdds)}}</span>
                            </td>
                            <td class="forumrow txtright" v-if="userLevel>1"
                                :class="$utils.getColorCssG(kindTotal.toUperAmt)">
                                <span>{{$utils.getAnsG(kindTotal.toUperAmt)}}</span>
                            </td>
                            <td class="forumrow txtright" v-if="userLevel>1"
                                :class="$utils.getColorCssG(kindTotal.toUperWin)">
                                <span>{{$utils.getAnsG(kindTotal.toUperWin)}}</span>
                            </td>
                        </tr>
                    </table>
                    <a-empty v-show="Object.keys(kindMap).length===0"/>
                </div>

                <!--交收报表-->
                <div v-show="reportTypeJS">
                    <div class="mb10">
                        <a-breadcrumb>
                            <a-breadcrumb-item>选择账号：</a-breadcrumb-item>
                            <a-breadcrumb-item v-for="(par,i) in parentPath">
                                <a href="javascript:void(0)" @click="changeUser(i)">{{par.showName}}</a>
                            </a-breadcrumb-item>
                        </a-breadcrumb>
                        <a-divider dashed/>
                        <span class="blue"> 交收报表[ {{params.startTime}} -- {{params.endTime}}]</span>
                        <a-button type="primary" size="small" style="margin-left: 10px;" @click="returnChoice()">返回
                        </a-button>
                        <a-button type="primary" size="small" style="margin-left: 10px;" @click="loadWinLose()">刷新
                        </a-button>
                    </div>
                    <table class="tableborder leftlist" border="0" align="center" cellpadding="5" cellspacing="1">
                        <thead>
                        <tr>
                            <th colspan="2" width="120px">{{$t("AGENT"+(userLevel+1))}}列表</th>
                            <th colspan="3">{{$t("AGENT"+(userLevel+1))}}下线合计</th>
                            <th :colspan="userLevel==2?7:10">{{$t("AGENT"+(userLevel))}}：{{username}}</th>
                            <th width="100px;" rowspan="2" v-if="userLevel>1">上交货量</th>
                            <th width="100px;" rowspan="2" v-if="userLevel>1">应付上级</th>
                        </tr>
                        <tr>
                            <th>名称</th>
                            <th>账号
                                <span class="ve-table-sort">
                                    <i @click="loadWinLose('nameasc')" title="升序：最低到最高" class="ve-icon iconfont-vet icon-vet-sort-top-arrow ve-table-sort-icon ve-table-sort-icon-top"></i>
                                    <i @click="loadWinLose('namedesc')" title="降序：最高到最低" class="ve-icon iconfont-vet icon-vet-sort-bottom-arrow ve-table-sort-icon ve-table-sort-icon-bottom"></i>
                                </span>
                            </th>
                            <th>笔数</th>
                            <th>有效金额
                                <span class="ve-table-sort">
                                    <i @click="loadWinLose('betasc')" title="升序：最低到最高" class="ve-icon iconfont-vet icon-vet-sort-top-arrow ve-table-sort-icon ve-table-sort-icon-top"></i>
                                    <i @click="loadWinLose('betdesc')" title="降序：最高到最低" class="ve-icon iconfont-vet icon-vet-sort-bottom-arrow ve-table-sort-icon ve-table-sort-icon-bottom"></i>
                                </span>
                            </th>
                            <th>下线输赢
                                <span class="ve-table-sort">
                                    <i @click="loadWinLose('winasc')" title="升序：最低到最高" class="ve-icon iconfont-vet icon-vet-sort-top-arrow ve-table-sort-icon ve-table-sort-icon-top"></i>
                                    <i @click="loadWinLose('windesc')" title="降序：最高到最低" class="ve-icon iconfont-vet icon-vet-sort-bottom-arrow ve-table-sort-icon ve-table-sort-icon-bottom"></i>
                                </span>
                            </th>
                            <th>应收下线</th>
                            <th>占成%</th>
                            <th>实占注单</th>
                            <th>占货比%</th>
                            <th>实占输赢</th>
                            <th>实占退水</th>
                            <th>实占结果</th>
                            <th v-if="userLevel>2">赚取退水</th>
                            <th v-if="userLevel>2">赚取赔差</th>
                            <th v-if="userLevel>2">赚水赔差后</th>
                        </tr>
                        </thead>
                        <tr v-for="item in infoList" :class="{'hjend':item.username==='合计'}">
                            <td class="forumrow">
                                {{item.nickName}}
                            </td>
                            <td class="forumrow">
                                <a @click="changeAccount(item)" v-if="item.userId && item.userType==='AGENT'">{{item.username}}</a>
                                <span v-else>{{item.username}}</span>
                            </td>
                            <td class="forumrow">
                                {{$utils.getAnsZ(item.betCount)}}
                            </td>
                            <td class="forumrow txtright">
                                <a @click="showMemberDetail(item)" v-if="item.userId &&item.userType==='MEMBER'">{{$utils.getAnsG(item.betAmt)}}</a>
                                <span v-else>{{$utils.getAnsG(item.betAmt)}}</span>
                            </td>
                            <td class="forumrow txtright" :class="$utils.getColorCssG(item.betWinAmt)">
                                {{$utils.getAnsG(item.betWinAmt)}}
                            </td>
                            <td class="forumrow txtright"
                                :class="$utils.getColorCssG(item.toLowerWin+item.toLowerComm)">
                                <a-tooltip>
                                    <template slot="title">
                                        <p>输赢：{{$utils.getAnsG(item.toLowerWin)}}</p>
                                        <p>退水：{{$utils.getAnsG(item.toLowerComm)}}</p>
                                    </template>
                                    <span>{{$utils.getAnsG(item.toLowerWin+item.toLowerComm)}}</span>
                                </a-tooltip>
                            </td>
                            <td class="forumrow txtright">
                                <span>{{$utils.getAnsG(item.actualPtBetAmt/item.betAmt*100)}}</span>
                            </td>
                            <td class="forumrow txtright" :class="$utils.getColorCssG(item.actualPtBetAmt)">
                                <span>{{$utils.getAnsG(item.actualPtBetAmt)}}</span>
                            </td>
                            <td class="forumrow txtright">
                                <span>{{$utils.getAnsG(item.actualPtBetAmt/infoList[infoList.length-1].actualPtBetAmt*100)}}</span>
                            </td>
                            <td class="forumrow txtright" :class="$utils.getColorCssG(item.actualWinAmt)">
                                <span>{{$utils.getAnsG(item.actualWinAmt)}}</span>
                            </td>
                            <td class="forumrow txtright" :class="$utils.getColorCssG(item.actualCommAmt)">
                                <span>{{$utils.getAnsG(item.actualCommAmt)}}</span>
                            </td>
                            <td class="forumrow txtright" :class="$utils.getColorCssG(item.actualResult)">
                                <span>{{$utils.getAnsG(item.actualResult)}}</span>
                            </td>
                            <td class="forumrow txtright" v-if="userLevel>2"
                                :class="$utils.getColorCssG(item.gainCommAmt)">
                                <span>{{$utils.getAnsG(item.gainCommAmt)}}</span>
                            </td>
                            <td class="forumrow txtright" v-if="userLevel>2"
                                :class="$utils.getColorCssG(item.gainOddsAmt)">
                                <span>{{$utils.getAnsG(item.gainOddsAmt)}}</span>
                            </td>
                            <td class="forumrow txtright" v-if="userLevel>2"
                                :class="$utils.getColorCssG(item.gainCommOdds)">
                                <span>{{$utils.getAnsG(item.gainCommOdds)}}</span>
                            </td>
                            <td class="forumrow txtright" v-if="userLevel>1"
                                :class="$utils.getColorCssG(item.toUperAmt)">
                                <span>{{$utils.getAnsG(item.toUperAmt)}}</span>
                            </td>
                            <td class="forumrow txtright" v-if="userLevel>1"
                                :class="$utils.getColorCssG(item.toUperWin)">
                                <span>{{$utils.getAnsG(item.toUperWin)}}</span>
                            </td>
                        </tr>
                        <tr v-if="infoList.length===0">
                            <td colspan="50" class="forumrowhighlight nohover">
                                <a-empty/>
                            </td>
                        </tr>
                    </table>
                    <template v-if="infoList.length>0 && userLevel > 1">
                        <div class="mtb10 font14" align="center">
                            <span>实占结果：
                                <span :class="$utils.getColorCssG(infoTotal.actualResult)">
                                {{$utils.getAnsG(infoTotal.actualResult)}}</span> /
                            </span>
                            <template v-if="userLevel > 2">
                                <span>赚取退水：
                                <span :class="$utils.getColorCssG(infoTotal.gainCommAmt)">
                                    {{$utils.getAnsG(infoTotal.gainCommAmt)}}</span> /
                                </span>
                                <span>
                                赚取赔差：<span :class="$utils.getColorCssG(infoTotal.gainOddsAmt)">
                                {{$utils.getAnsG(infoTotal.gainOddsAmt)}}</span> /
                                </span>
                                <span>抵扣补货及赚水后结果：
                                <span :class="$utils.getColorCssG(infoTotal.gainCommOdds-bhAmtRp.bhWinAmt-bhAmtRp.bhComm)">
                                    {{$utils.getAnsG(infoTotal.gainCommOdds-bhAmtRp.bhWinAmt-bhAmtRp.bhComm)}}</span> /
                                </span>
                            </template>
                            <span>应付上级：
                                <span :class="$utils.getColorCssG(infoTotal.toUperWin+bhAmtRp.bhWinAmt+bhAmtRp.bhComm)">
                                {{$utils.getAnsG(infoTotal.toUperWin+bhAmtRp.bhWinAmt+bhAmtRp.bhComm)}}</span></span>
                        </div>
                        <div class="textcenter">
                            <table class="tableborder leftlist" border="0" align="center" cellpadding="5"
                                   cellspacing="1"
                                   style="width: 720px;margin:auto"
                                   v-if="bhAmtRp.betCount>0">
                                <tr>
                                    <th>笔数</th>
                                    <th>补货金额</th>
                                    <th>补货输赢</th>
                                    <th>退水</th>
                                    <th>退水后结果</th>
                                </tr>
                                <tr>
                                    <td class="forumrow">{{bhAmtRp.betCount}}</td>
                                    <td class="forumrow">
                                        <a :class="$utils.getColorCssG(bhAmtRp.bhAmt)"
                                           @click="showBhDetail(bhAmtRp.userId)">
                                            {{$utils.getAnsG(bhAmtRp.bhAmt)}}</a>
                                    </td>
                                    <td class="forumrow"><span :class="$utils.getColorCssG(bhAmtRp.bhWinAmt)">
                                    {{$utils.getAnsG(bhAmtRp.bhWinAmt)}}</span></td>
                                    <td class="forumrow"><span :class="$utils.getColorCssG(bhAmtRp.bhComm)">
                                    {{$utils.getAnsG(bhAmtRp.bhComm)}}</span></td>
                                    <td class="forumrow"><span
                                            :class="$utils.getColorCssG(bhAmtRp.bhWinAmt+bhAmtRp.bhComm)">
                                    {{$utils.getAnsG(bhAmtRp.bhWinAmt+bhAmtRp.bhComm)}}</span></td>
                                </tr>
                            </table>
                        </div>
                    </template>
                    <!--显示隐藏彩种类型报表-->
                    <div class="mtb10">
                        <a-button @click="triggerType()" v-if="showType" size="small" type="primary" icon="check">
                            隐藏彩种类型报表
                        </a-button>
                        <a-button @click="triggerType()" v-if="!showType" size="small" type="primary" icon="check">
                            显示彩种类型报表
                        </a-button>
                    </div>
                    <!--隐藏彩种类型报表-->
                    <div class="mtb10" v-if="showType">
                        <a-spin :spinning="lotteryLoading">
                            <table class="tableborder leftlist" border="0" align="center" cellpadding="5"
                                   cellspacing="1">
                                <tr>
                                    <th width="150px;">彩票种类</th>
                                    <th>笔数</th>
                                    <th>有效金额</th>
                                    <th>下线输赢</th>
                                    <th>应收下线</th>
                                    <th>占成%</th>
                                    <th>实占注单</th>
                                    <th>占货比%</th>
                                    <th>实占输赢</th>
                                    <th>实占退水</th>
                                    <th>实占结果</th>
                                    <th v-if="userLevel>2">赚取退水</th>
                                    <th v-if="userLevel>2">赚取赔差</th>
                                    <th v-if="userLevel>2">赚水赔差后</th>
                                    <th v-if="userLevel>1" width="100px;">上交货量</th>
                                    <th v-if="userLevel>1" width="100px;">应付上级</th>
                                </tr>
                                <tr v-for="item in types" :class="{'hjend':item.username==='合计'}">
                                    <td class="forumrow">
                                        <span v-if="item.lotteryId">{{$t(item.lotteryId)}}</span>
                                        <span v-else>合计</span>
                                    </td>
                                    <td class="forumrow">{{$utils.getAnsZ(item.betCount)}}</td>
                                    <td class="forumrow txtright">
                                        <span>{{$utils.getAnsG(item.betAmt)}}</span>
                                    </td>
                                    <td :class="$utils.getColorCssG(item.betWinAmt)" class="forumrow txtright">
                                        {{$utils.getAnsG(item.betWinAmt)}}
                                    </td>
                                    <td :class="$utils.getColorCssG(item.toLowerWin+item.toLowerComm)"
                                        class="forumrow txtright">
                                        <a-tooltip>
                                            <template slot="title">
                                                <p>输赢：{{$utils.getAnsG(item.toLowerWin)}}</p>
                                                <p>退水：{{$utils.getAnsG(item.toLowerComm)}}</p>
                                            </template>
                                            <span>{{$utils.getAnsG(item.toLowerWin+item.toLowerComm)}}</span>
                                        </a-tooltip>
                                    </td>
                                    <td class="forumrow txtright">
                                        <span>{{$utils.getAnsG(item.actualPtBetAmt/item.betAmt*100)}}</span>
                                    </td>
                                    <td :class="$utils.getColorCssG(item.actualPtBetAmt)" class="forumrow txtright">
                                        <span>{{$utils.getAnsG(item.actualPtBetAmt)}}</span>
                                    </td>
                                    <td class="forumrow txtright">
                                        <span>{{$utils.getAnsG(item.actualPtBetAmt/types[types.length-1].actualPtBetAmt*100)}}</span>
                                    </td>
                                    <td :class="$utils.getColorCssG(item.actualWinAmt)" class="forumrow txtright">
                                        <span>{{$utils.getAnsG(item.actualWinAmt)}}</span>
                                    </td>
                                    <td :class="$utils.getColorCssG(item.actualCommAmt)" class="forumrow txtright">
                                        <span>{{$utils.getAnsG(item.actualCommAmt)}}</span>
                                    </td>
                                    <td :class="$utils.getColorCssG(item.actualResult)" class="forumrow txtright">
                                        <span>{{$utils.getAnsG(item.actualResult)}}</span>
                                    </td>
                                    <td v-if="userLevel>2" class="forumrow txtright"
                                        :class="$utils.getColorCssG(item.gainCommAmt)">
                                        <span>{{$utils.getAnsG(item.gainCommAmt)}}</span>
                                    </td>
                                    <td v-if="userLevel>2" class="forumrow txtright"
                                        :class="$utils.getColorCssG(item.gainOddsAmt)">
                                        <span>{{$utils.getAnsG(item.gainOddsAmt)}}</span>
                                    </td>
                                    <td v-if="userLevel>2" :class="$utils.getColorCssG(item.gainCommOdds)"
                                        class="forumrow txtright">
                                        <span>{{$utils.getAnsG(item.gainCommOdds)}}</span>
                                    </td>
                                    <td v-if="userLevel>1" :class="$utils.getColorCssG(item.toUperAmt)"
                                        class="forumrow txtright">
                                        <span>{{$utils.getAnsG(item.toUperAmt)}}</span>
                                    </td>
                                    <td v-if="userLevel>1" :class="$utils.getColorCssG(item.toUperWin)"
                                        class="forumrow txtright">
                                        {{$utils.getAnsG(item.toUperWin)}}
                                    </td>
                                </tr>
                                <tr v-if="types.length===0">
                                    <td colspan="50" class="forumrowhighlight nohover">
                                        <a-empty/>
                                    </td>
                                </tr>
                            </table>
                            <table class="tableborder leftlist" border="0" align="center" cellpadding="5"
                                   v-if="bhList.length>0"
                                   cellspacing="1">
                                <tr>
                                    <th width="100px;">彩票种类(补货)</th>
                                    <th width="50px;">笔数</th>
                                    <th width="140px;">补货金额</th>
                                    <th width="140px;">补货输赢</th>
                                    <th width="140px;">退水</th>
                                    <th width="140px;">退水后结果</th>
                                    <th width="140px;">上交货量</th>
                                    <th width="140px;">应付上级</th>
                                </tr>
                                <template v-for="item in bhList">
                                    <tr>
                                        <td class="forumrow">
                                            <span v-if="item.lotteryId">{{$t(item.lotteryId)}}</span>
                                            <span v-else>合计</span>
                                        </td>
                                        <td class="forumrow txtright">{{$utils.getAnsZ(item.betCount)}}</td>
                                        <td class="forumrow txtright">
                                            {{$utils.getAnsG(item.bhAmt)}}
                                        </td>
                                        <td :class="$utils.getColorCssG(item.bhWinAmt)" class="forumrow txtright">
                                            {{$utils.getAnsG(item.bhWinAmt)}}
                                        </td>
                                        <td :class="$utils.getColorCssG(item.bhComm)" class="forumrow txtright">
                                            {{$utils.getAnsG(item.bhComm)}}
                                        </td>
                                        <td :class="$utils.getColorCssG(item.bhWinAmt+item.bhComm)"
                                            class="forumrow txtright">
                                            {{$utils.getAnsG(item.bhWinAmt+item.bhComm)}}
                                        </td>
                                        <td :class="$utils.getColorCssG(item.bhAmt)" class="forumrow txtright">
                                            {{$utils.getAnsG(item.bhAmt)}}
                                        </td>
                                        <td :class="$utils.getColorCssG(item.bhWinAmt+item.bhComm)"
                                            class="forumrow txtright">
                                            {{$utils.getAnsG(item.bhWinAmt+item.bhComm)}}
                                        </td>
                                    </tr>
                                </template>
                                <tr v-if="types.length===0">
                                    <td colspan="50" class="forumrowhighlight nohover">
                                        <a-empty/>
                                    </td>
                                </tr>
                            </table>
                            <table class="tableborder leftlist" border="0" align="center" cellpadding="5"
                                   v-if="bhList.length>0"
                                   cellspacing="1">
                                <tr>
                                    <th width="130px;" rowspan="2">总计</th>
                                    <th width="60px;">笔数</th>
                                    <th width="140px;">有效金额</th>
                                    <th width="140px;">应收下线</th>
                                    <th width="140px;">输赢</th>
                                    <th width="120px;">退水</th>
                                    <th width="120px;">赚取退水</th>
                                    <th width="140px;">赚取赔差</th>
                                    <th width="140px;">赚水赔差后</th>
                                    <th width="140px;">上交货量</th>
                                    <th width="140px;">应付上级</th>
                                </tr>
                                <tr>
                                    <td class="forumrow">
                                        {{bhTotal.betCount}}
                                    </td>
                                    <td class="forumrow txtright"
                                        :class="$utils.getColorCssG(bhTotal.betAmt+bhTotal.bhAmt)">
                                        {{$utils.getAnsG(bhTotal.betAmt+bhTotal.bhAmt)}}
                                    </td>
                                    <td class="forumrow txtright"
                                        :class="$utils.getColorCssG(bhTotal.toLowerWin+bhTotal.toLowerComm)">
                                        {{$utils.getAnsG(bhTotal.toLowerWin+bhTotal.toLowerComm)}}
                                    </td>
                                    <td class="forumrow txtright"
                                        :class="$utils.getColorCssG(bhTotal.actualWinAmt-bhTotal.bhWinAmt)">
                                        {{$utils.getAnsG(bhTotal.actualWinAmt-bhTotal.bhWinAmt)}}
                                    </td>
                                    <td class="forumrow txtright"
                                        :class="$utils.getColorCssG(bhTotal.actualCommAmt-bhTotal.bhComm)">
                                        {{$utils.getAnsG(bhTotal.actualCommAmt-bhTotal.bhComm)}}
                                    </td>
                                    <td class="forumrow txtright" :class="$utils.getColorCssG(bhTotal.gainCommAmt)">
                                        {{$utils.getAnsG(bhTotal.gainCommAmt)}}
                                    </td>
                                    <td class="forumrow txtright" :class="$utils.getColorCssG(bhTotal.gainOddsAmt)">
                                        {{$utils.getAnsG(bhTotal.gainOddsAmt)}}
                                    </td>
                                    <td class="forumrow txtright"
                                        :class="$utils.getColorCssG(bhTotal.actualWinAmt-bhTotal.bhWinAmt-bhTotal.actualCommAmt-bhTotal.bhComm+bhTotal.gainCommAmt+bhTotal.gainOddsAmt)">
                                        {{$utils.getAnsG(bhTotal.actualWinAmt-bhTotal.bhWinAmt+bhTotal.actualCommAmt-bhTotal.bhComm+bhTotal.gainCommAmt+bhTotal.gainOddsAmt)}}
                                    </td>
                                    <td class="forumrow txtright"
                                        :class="$utils.getColorCssG(bhTotal.toUperAmt+bhTotal.bhAmt)">
                                        {{$utils.getAnsG(bhTotal.toUperAmt+bhTotal.bhAmt)}}
                                    </td>
                                    <td class="forumrow txtright"
                                        :class="$utils.getColorCssG(bhTotal.toUperWin+bhTotal.bhWinAmt+bhTotal.bhComm)">
                                        {{$utils.getAnsG(bhTotal.toUperWin+bhTotal.bhWinAmt+bhTotal.bhComm)}}
                                    </td>
                                </tr>
                            </table>
                        </a-spin>
                    </div>
                </div>
            </div>
        </a-spin>
        <!-- 注单明细弹窗-->
        <a-modal v-model="showMember" :footer="null" :maskClosable="maskClosable" width="1024px" :z-index="9999"
                 title="注单明细" @ok="showMember=false"
                 class="bblisttc">
            <a-spin :spinning="detailLoading">
                <div class="mb10 df">
                    <a-input-search style="width: 120px" size="small" placeholder="用户名"
                                    v-model="memParams.username"/>
                    <div class="pl10">
                        选择日期
                        <select v-model="timeDay" @change="changePage(memParams.page,memParams.size)">
                            <option v-for="item in timeCount" :key="item.accountDay" :value="item.accountDay">
                                {{item.accountDay}} 【{{item.count}}笔】
                            </option>
                        </select>
                    </div>
                    <div class="pl10">
                        <a-button type="primary" icon="search" size="small"
                                  @click="changePage(memParams.page,memParams.size)">
                            查询
                        </a-button>
                    </div>
                    <span class="red">*为了提高注单查询速度，请选择日期查询</span>
                </div>
                <table class="tableborder leftlist" border="0" align="center" cellpadding="5" cellspacing="1"
                       style="width: 100%;border-collapse: separate;">
                    <tr>
                        <th>注单号</th>
                        <th>下注时间</th>
                        <th>期号</th>
                        <th>用户名/盘口</th>
                        <th>下注明细</th>
                        <th>下注额</th>
                        <th>退水率</th>
                        <th>退水</th>
                        <th>输赢</th>
                        <th>下注类型</th>
                        <th>状态</th>
                    </tr>
                    <tr v-for="item in memberDetails">
                        <td class="forumrow">{{item.orderId}}</td>
                        <td class="forumrow"> {{moment(item.betTime*1000).format('YYYY-MM-DD HH:mm:ss')}}</td>
                        <td class="forumrow">{{$t(item.lotteryId)}}：<br/>{{item.gameNo}}期</td>
                        <td class="forumrow">{{item.username}}/{{item.market}}盘</td>
                        <td class="forumrow">
                            {{$t(JSON.parse(item.keyName).playKey)}}[{{$t(item.oddsKey)}}]
                            <template v-if="item.betContent">
                                <br/>{{item.betContent}}<br/>
                            </template>
                            @{{$utils.getAnsQ(item.odds)}}
                        </td>
                        <td class="forumrow">
                            {{$utils.getAnsG(item.betAmt)}}
                        </td>
                        <td class="forumrow">
                            {{item.commPct}}%
                        </td>
                        <td class="forumrow">
                            {{$utils.getAnsS(item.betAmt*item.commPct/100)}}
                        </td>
                        <td class="forumrow"
                            :class="$utils.getColorCssG(item.winAmt+item.betAmt*item.commPct/100)">
                            {{$utils.getAnsS(item.winAmt+item.betAmt*item.commPct/100)}}
                        </td>
                        <td class="forumrow">
                            {{item.betType==='zc'?'会员投注':item.manual?'手动补货':'自动补货'}}
                        </td>
                        <td class="forumrow">{{$t(item.status)}}</td>
                    </tr>
                </table>
                <div class="p10" style="text-align: center;">
                    <a-pagination size="small" :total="rowCount" show-size-changer show-quick-jumper
                                  :current="memParams.page"
                                  :pageSize="memParams.size" @change="changePage"
                                  @showSizeChange="changePage" :show-total="total => `共 ${total} 条`"/>
                </div>
            </a-spin>
        </a-modal>
    </div>
</template>

<script>
    import dateButton from '@/components/my-date-button.vue'
    import dateRanger from '@/components/my-date-range.vue'
    import Bus from "@/Bus";

    export default {
        inject: ['reloadRouter'],
        components: {
            dateButton,
            dateRanger
        },
        data() {
            return {
                maskClosable: false,
                loading: false,
                lotterys: [],
                params: {
                    gameNo: '',
                    username: '',
                    status: 'DIVIDEND',
                },
                reportType: 'js',
                reportShow: true,
                reportTypeJS: false,
                reportTypeFL: false,
                parentPath: [],
                userLevel: 0,
                username: '',
                infoList: [],
                bhAmtRp: {},
                infoTotal: {},
                showType: false,
                types: [],
                lotteryLoading: false,

                bhList: [],
                bhTotal: [],

                memParams: {
                    page: 1,
                    size: 10
                },
                showMember: false,
                detailLoading: false,
                memberDetails: [],
                rowCount: 0,

                kindMap: {},
                gameNos: [],
                selLotteryId: 0,
                kindTotal: {},

                timeCount: [],
                timeDay: null,
            };
        },
        methods: {
            filterOption(input, option) {
                return (
                    option.componentOptions.children[0].text.toLowerCase().indexOf(input.toLowerCase()) >= 0
                );
            },
            returnChoice() {
                this.reloadRouter();
            },
            handleChange(id) {
                this.lotterys.forEach((lottery) => {
                    if (id === 0) {
                        lottery.isChecked = true;
                    } else {
                        if (lottery.lotteryId === id) {
                            lottery.isChecked = true;
                        } else {
                            lottery.isChecked = false;
                        }
                    }
                });
                this.loadGameNo();
            },
            loadGameNo() {
                let day = this.params.startTime;
                this.params.gameNo = '';
                this.gameNos = [];
                if (this.selLotteryId !== 0) {
                    this.$api.order.getGameNo(day, this.selLotteryId).then(res => {
                        if (res.success) {
                            this.gameNos = res.data;
                        }
                    })
                }
            },
            changeDate(data) {
                this.params.startTime = data[0];
                this.params.endTime = data[1];
            },
            changeButton(data) {
                if (data) {
                    if (this.params.startTime !== data[0]) {
                        this.loadGameNo();
                    }
                    this.params.startTime = data[0];
                    this.params.endTime = data[1];
                    Bus.$emit("upTime", data);
                }
                /*if (this.reportType === 'js') {
                    this.loadWinLose();
                } else {
                    this.loadWinLoseByKind();
                }*/
            },
            searchLoad() {
                this.reportShow = false;
                if (this.reportType === 'js') {
                    this.loadWinLose();
                    this.reportTypeJS = true;
                    this.reportTypeFL = false;
                } else {
                    this.loadWinLoseByKind();
                    this.reportTypeFL = true;
                    this.reportTypeJS = false;
                }
            },
            loadLottery() {
                this.$api.ctrl.getLotteryCompany().then(res => {
                    if (res.success) {
                        res.data.forEach(item => {
                            item.isChecked = true;
                        })
                        this.lotterys = res.data;
                    }
                })
            },
            checkTime() {
                let date = new Date();
                if (date.getHours() === 6) {
                    this.$swal({
                            title: "报表统计中,请7点后进行查询!",
                            confirmButtonText: '确定',
                            icon: 'warning',
                            timer: 1000,
                        }
                    );
                    return false;
                }
                return true;
            },
            loadWinLose(sort) {
                if (!this.checkTime()) {
                    return false;
                }
                let lotteryIds = this.lotterys.filter((lottery) => lottery.isChecked).map((lottery) => lottery.lotteryId);
                if (this.selLotteryId === 0) {
                    this.params.lotteryIds = null;
                } else {
                    this.params.lotteryIds = lotteryIds;
                }
                if(sort){
                    this.params.sort=sort;
                }
                this.showType = false;
                this.loading = true;
                this.infoList = [];
                this.bhAmtRp = {
                    bhAmt: 0,
                    bhWinAmt: 0,
                    bhComm: 0,
                };
                this.$api.order.getWinLose(this.params).then(res => {
                    if (res.success) {
                        this.types = [];
                        this.infoList = res.data.winLoseInfos;
                        if (res.data.bhAmtRp) {
                            this.bhAmtRp = res.data.bhAmtRp;
                        }
                        if (this.infoList.length > 0) {
                            this.infoTotal = this.handTotal(this.infoList);
                            this.infoList.push(this.infoTotal);
                        }
                    }
                }).finally(e => {
                    this.loading = false;
                })
            },
            triggerType() {
                this.showType = !this.showType;
                if (this.showType) {
                    this.loadWinLoseByLottery();
                }
            },
            loadWinLoseByLottery() {
                if (!this.checkTime()) {
                    return false;
                }
                let lotteryIds = this.lotterys.filter((lottery) => lottery.isChecked).map((lottery) => lottery.lotteryId);
                this.params.lotteryIds = lotteryIds;
                this.types = [];
                this.lotteryLoading = true;
                this.$api.order.getWinLoseByLottery(this.params).then(res => {
                    if (res.success) {
                        this.types = Object.assign([], res.data.lotterys);
                        if (this.types.length > 0) {
                            this.types.push(this.handTotal(this.types));
                        }
                        this.bhList = Object.assign([], res.data.bhList);
                        if (this.bhList.length > 0) {
                            let bh = this.handTotal(this.bhList);
                            this.bhList.push(bh);
                            this.bhTotal = this.handTotal(res.data.lotterys.concat(bh))
                        }
                    }
                }).finally(e => {
                    this.lotteryLoading = false;
                })
            },
            loadWinLoseByKind() {
                if (!this.checkTime()) {
                    return false;
                }
                let lotteryIds = this.lotterys.filter((lottery) => lottery.isChecked).map((lottery) => lottery.lotteryId);
                this.params.lotteryIds = lotteryIds;
                this.loading = true;
                this.kindMap = {};
                this.kindTotal = {};
                this.$api.order.getWinLoseByKind(this.params).then(res => {
                    if (res.success) {
                        res.data.kinds.forEach((item) => {
                            if (this.kindMap[item.lotteryId]) {
                                this.kindMap[item.lotteryId].push(item)
                            } else {
                                this.kindMap[item.lotteryId] = [item]
                            }
                        })
                        let kindTotal = [];
                        Object.values(this.kindMap).forEach((v) => {
                            if (v.length > 0) {
                                let totalRow = this.handTotal(v);
                                kindTotal.push(Object.assign({}, totalRow));
                                v.push(totalRow);
                            }
                        })
                        if (kindTotal.length > 1) {
                            this.kindTotal = this.handTotal(kindTotal);
                        }
                    }
                }).finally(e => {
                    this.loading = false;
                })
            },
            handTotal(selection) {
                let total = Object.assign({}, selection[0]);
                selection[0].kindName = this.$t(selection[0].kindKey) + " " + this.$t(selection[0].playKey);
                selection[0].oddsName = this.$t(selection[0].kindKey) + " " + this.$t(selection[0].playKey) + " " + this.$t(selection[0].oddsKey);
                for (let col of selection.slice(1)) {
                    col.kindName = this.$t(col.kindKey) + " " + this.$t(col.playKey);
                    col.oddsName = this.$t(col.kindKey) + " " + this.$t(col.playKey) + " " + this.$t(col.oddsKey);
                    let aProps = Object.getOwnPropertyNames(col);
                    for (let i = 0; i < aProps.length; i++) {
                        let propName = aProps[i];
                        total[propName] = (total[propName] || 0) + (col[propName] || 0);
                    }
                }
                total.username = '合计';
                total.kindName = '合计';
                total.oddsName = '合计';
                total.nickName = '';
                total.userId = '';
                total.lotteryId = '';
                total.className = '';
                total.categoryName = '';
                total.categoryId = '';
                return total;
            },
            changeAccount(item) {
                this.params.userId = item.userId;
                this.parentPath.push({
                    username: item.username,
                    userId: item.userId,
                    userLevel: item.userLevel,
                    showName: this.$t(item.userType + item.userLevel) + ":" + item.username
                });
                this.username = item.username;
                this.userLevel = item.userLevel;
                this.loadWinLose();
                this.showType = false;
            },
            changeUser(index) {
                if (this.parentPath.splice(index + 1).length > 0) {
                    let parent = this.parentPath[index];
                    this.params.userId = parent.userId;
                    this.username = parent.username;
                    this.userLevel = parent.userLevel;
                    this.loadWinLose();
                }
            },
            showKindDetail(row) {
                let params = {
                    startTime: this.params.startTime,
                    endTime: this.params.endTime,
                    playId: row.playId,
                    kindId: row.kindId,
                    lotteryId: row.lotteryId,
                    parentId: row.parentId,
                    status: this.params.status,
                };
                this.memberDetails = [];
                this.$api.order.getDetailTimeCount(params).then(res => {
                    if (res.success) {
                        this.timeCount = res.data;
                        if (this.timeCount.length > 0) {
                            this.timeDay = this.timeCount[0].accountDay;
                            delete this.memParams.userId;
                            delete this.memParams.categoryId;
                            this.memParams.playId = row.playId;
                            this.memParams.kindId = row.kindId;
                            this.memParams.agentId = row.parentId;
                            this.memParams.lotteryIds = [parseInt(row.lotteryId)];
                            this.changePage(1, this.memParams.size);

                        }
                    }
                })
                this.showMember = true;
            },
            showMemberDetail(row) {
                let lotteryIds = this.lotterys.filter((lottery) => lottery.isChecked).map((lottery) => lottery.lotteryId);
                this.params.lotteryIds = lotteryIds;
                this.memParams.userId = row.userId;
                this.memParams.categoryId = "";
                this.memParams.type = "zc";
                this.memParams.oddsId = row.oddsId;
                this.memParams.lotteryIds = this.params.lotteryIds;
                this.loadTimeCount(row.userId);
                this.showMember = true;
            },
            showBhDetail(userId) {
                let lotteryIds = this.lotterys.filter((lottery) => lottery.isChecked).map((lottery) => lottery.lotteryId);
                this.params.lotteryIds = lotteryIds;
                this.memParams.userId = userId;
                this.memParams.type = "bh";
                this.memParams.lotteryIds = this.params.lotteryIds;
                this.loadTimeCount(userId);
                this.showMember = true;
            },
            loadTimeCount(userId,) {
                this.timeCount = [];
                this.memberDetails = [];
                let params = {
                    startTime: this.params.startTime,
                    endTime: this.params.endTime,
                    status: this.params.status,
                    type: this.memParams.type,
                    userId
                };
                this.$api.order.getDetailTimeCount(params).then(res => {
                    if (res.success) {
                        this.timeCount = res.data;
                        if (this.timeCount.length > 0) {
                            this.timeDay = this.timeCount[0].accountDay;
                            this.changePage(1, this.memParams.size);
                        }
                    }
                })
            },
            changePage(page, pageSize) {
                this.memberDetails = [];
                this.memParams.gameNo = this.params.gameNo;
                this.memParams.page = page;
                this.memParams.status = this.params.status;
                this.memParams.size = pageSize;
                this.memParams.startTime = this.timeDay;
                this.detailLoading = true;
                this.$api.order.getOrderList(this.memParams).then(res => {
                    if (res.success) {
                        this.memberDetails = res.data.dataList;
                        this.rowCount =this.timeCount.find((val)=>{return val.accountDay===this.timeDay}).count;
                    }
                }).finally(e => {
                    this.detailLoading = false;
                })
            },
            checkLotteryAll(v) {
                this.lotterys.forEach(item => {
                    item.isChecked = v.target.checked;
                })
            }
        },
        mounted() {
            this.loadLottery();
            let userInfo = this.$store.state.user.info;
            this.userLevel = userInfo.userLevel;
            this.username = userInfo.username;
            this.parentPath.push({
                username: userInfo.username,
                userId: userInfo.primaryId,
                userLevel: userInfo.userLevel,
                showName: this.$t("AGENT" + userInfo.userLevel) + ":" + userInfo.username
            });
        },
    };
</script>

<style scoped>
    .ant-divider-horizontal.ant-divider-with-text-left::before {
        top: 0%;
    }

    .ant-divider-horizontal.ant-divider-with-text-left::after {
        top: 0%;
    }
</style>
