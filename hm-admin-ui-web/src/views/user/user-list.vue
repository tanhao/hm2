<template>
    <div class="autofutllwith">
        <div>
            <a-spin :spinning="spinning" size="large">
                <div class="mb10 df">
                    <span class="maintxt mlr10">模式:</span>
                    <a-select default-value="ALL" style="width: 120px" size="small" v-model="userParams.reserveModel" @change="changePage(1,userParams.size)">
                        <a-select-option value="ALL">
                            全部
                        </a-select-option>
                        <a-select-option value="OMNIBUS">
                            综合
                        </a-select-option>
                        <a-select-option value="CREDIT">
                            信用
                        </a-select-option>
                        <a-select-option value="RECHARGE">
                            现金
                        </a-select-option>
                    </a-select>
                    <span class="maintxt mlr10">状态:</span>
                    <a-select default-value="OPEN" style="width: 120px" size="small" v-model="userParams.status" @change="changePage(1,userParams.size)">
                        <a-select-option value="ALL">
                            全部
                        </a-select-option>
                        <a-select-option value="OPEN">
                            启用
                        </a-select-option>
                        <a-select-option value="SUSPEND">
                            冻结
                        </a-select-option>
                        <a-select-option value="CLOSE">
                            停用
                        </a-select-option>
                    </a-select>
                    <span class="maintxt mlr10">账号:</span>
                    <a-input-search style="width: 120px" size="small" placeholder="搜索账户" v-model="userParams.username" />
                    <div class="pl10">
                        <a-button type="primary" icon="search" size="small" @click="loadUserList">
                            搜索
                        </a-button>
                    </div>
                    <div class="pl10" v-show="userLowers.length>0">
                        <a-button type="danger" icon="left" size="small" @click="showUpper">
                            返回上一级
                        </a-button>
                    </div>
                    <template v-if="userParams.userType==='agent'">
                        <div class="pl10" v-show="userParams.userLevel<12 && userParams.userLevel>=1">
                            <a-button type="primary" icon="plus" size="small" v-if="userInfoLevel!==1 || userParams.userLevel===1" @click="addUserName='新增'+$t('AGENT'+(userParams.userLevel+1));
                                      editParentId=userParams.parentId;userAddShow=true;editUserType='AGENT'">
                                新增{{$t('AGENT'+(userParams.userLevel+1))}}
                            </a-button>
                        </div>
                    </template>
                   <!-- <template v-if="userParams.userType==='allAgent'">
                        <div class="pl10" v-show="userParams.userLevel<12 && userParams.userLevel>=1">
                            <a-button type="primary" icon="plus" size="small" v-if="userInfoLevel!==1 || userParams.userLevel===1" @click="addUserName='新增'+$t('AGENT'+(userParams.userLevel));
                                      editParentId=userParams.parentId;userAddShow=true;editUserType='AGENT'">
                                新增{{$t('AGENT'+(userParams.userLevel+1))}}
                            </a-button>
                        </div>
                    </template>-->
                    <div class="pl10" v-show="userParams.userLevel>2 && userInfoLevel!==1 && (userParams.userType!=='allMember'&& userParams.userType!=='allAgent')">
                        <a-button type="primary" icon="plus" size="small" @click="addUserName='新增直属会员';editParentId=userParams.parentId;userAddShow=true;editUserType='MEMBER'">
                            新增直属会员
                        </a-button>
                    </div>
                </div>

                <table class="tableborder" border="0" align="center" cellpadding="5" cellspacing="1">
                    <thead>
                        <tr>
                            <th rowspan="2">在线</th>
                            <th width="120" rowspan="2">账号</th>
                            <th width="70" rowspan="2">用户类型</th>
                            <th rowspan="2" width="40px">占成</th>
                            <template v-if="userParams.userLevel!==1">
                                <th width="200" rowspan="2">账号额度</th>
                            </template>
                            <template v-if="userParams.userType!=='MEMBER'">
                                <th rowspan="2" width="40px">盤口</th>
                            </template>
                            <th colspan="2" v-show="userParams.userType==='agent'||userParams.userType==='allAgent'">直属下线
                            </th>
                            <th colspan="2" v-show="userParams.userType==='agent'||userParams.userType==='allAgent'">所有下线
                            </th>
                            <th rowspan="2" width="80px">新增日期</th>
                            <th rowspan="2" width="65px">状态</th>
                            <th rowspan="2">操作</th>
                        </tr>
                        <tr v-show="userParams.userType==='agent'||userParams.userType==='allAgent'">
                            <th>代理</th>
                            <th>会员</th>
                            <th>代理</th>
                            <th>会员</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr v-for="user in userList" :class="'table-row-'+user.status">
                            <td class="forumrow">
                                <a-icon type="bulb" class="font16" :class="{'yellow':user.online}" />
                            </td>
                            <td class="forumrow" >
                                <template v-if="user.userType==='AGENT' && user.userLevel <=11 ">
                                    <div >
                                        <a @click="showLower(user,'agent')">{{user.username}}<br><span v-if="user.nickName">[{{user.nickName}}]</span></a>
                                    </div>
                                </template>
                                <template v-else>
                                    <div>
                                        <template v-if="user.userType==='AGENT'">
                                            <a @click="showLower(user,'member')">{{user.username}}<br>
                                                <span v-if="user.nickName">[{{user.nickName}}]</span>
                                            </a>
                                        </template>
                                        <template v-else>
                                            {{user.username}}<br><span v-if="user.nickName">[{{user.nickName}}]</span>
                                        </template>
                                    </div>
                                </template>
                            </td>
                            <td class="forumrow">
                                <span>{{$t(user.reserveModel)}}<br>
                                    <template v-if="user.userType==='MEMBER'">{{$t(user.userType+user.userLevel)}}</template>
                                    <template v-else>{{$t(user.userType+(user.userType==='AGENT'?user.userLevel:''))}}</template>
                                </span>
                            </td>
                            <td class="forumrowhighlight">
                                <a @click="editUserId=user.userId;editParentId=user.parentId;userPtShow=true;editUserType=user.userType;editUsername=user.username">
                                    <span>查看</span>
                                </a>
                            </td>
                            <template v-if="userParams.userLevel!==1">
                                <td class="forumrowhighlight">
                                   <!-- 后桶不可以打开修改金額-->
                                    <template v-if="userInfoLevel>1">
                                        <ul @click="updateBalanceShow(user)" v-if="user.userLevel>2 && user.userType==='AGENT'">
                                            <!--<a-tooltip placement="topLeft" arrow-point-at-center :title="'信用会员額度:'+$utils.getAnsS(user.memberCreditBalance)">
                                            </a-tooltip>-->


                                            <template v-if="user.reserveModel==='OMNIBUS'||user.reserveModel==='CREDIT'">
                                                <a-popover >
                                                    <template slot="content">
                                                        <template v-if="user.userLevel<=11">
                                                            代理可用信用余额:{{$utils.getAnsS(user.agentUseCreditBalance)}}
                                                            <br/>
                                                        </template>
                                                        会员可用信用余额:{{$utils.getAnsS(user.memberUseCreditBalance)}}
                                                    </template>
                                                    <li class="tabthcolor fontwe">信用額度:{{$utils.getAnsS(user.creditBalance)}}</li>
                                                    <li class="green fontwe">余額:{{$utils.getAnsS(user.useCreditBalance)}}</li>
                                                </a-popover>
                                                <!--<a-tooltip placement="topLeft" arrow-point-at-center :title="'信用会员額度:'+$utils.getAnsS(user.memberCreditBalance)">
                                                    <li class="tabthcolor fontwe">信用額度:{{$utils.getAnsS(user.creditBalance)}}</li>
                                                    <li class="green fontwe">余額:{{$utils.getAnsS(user.useCreditBalance)}}</li>
                                                </a-tooltip>-->
                                            </template>
                                            <template v-if="user.reserveModel==='OMNIBUS'||user.reserveModel==='RECHARGE'">
                                                <a-popover >
                                                    <template slot="content">
                                                        <template v-if="user.userLevel<=11">
                                                            代理可用充值余额:{{$utils.getAnsS(user.agentUseRechargeBalance)}}
                                                            <br/>
                                                        </template>
                                                        会员可用充值余额:{{$utils.getAnsS(user.memberUseRechargeBalance)}}
                                                    </template>
                                                    <li class="tabthcolor fontwe">充值额度:{{$utils.getAnsS(user.rechargeBalance)}}</li>
                                                    <li class="green fontwe">可用:{{$utils.getAnsS(user.useRechargeBalance)}}</li>
                                                </a-popover>
                                               <!-- <a-tooltip placement="topLeft" arrow-point-at-center :title="'充值会员額度:'+$utils.getAnsS(user.memberRechargeBalance)">
                                                    <li class="tabthcolor fontwe">充值額度:{{$utils.getAnsS(user.rechargeBalance)}}</li>
                                                    <li class="green fontwe">可用:{{$utils.getAnsS(user.useRechargeBalance)}}</li>
                                                </a-tooltip>-->
                                            </template>
                                        </ul>
                                        <ul @click="updateBalanceShow(user)" v-if="user.userType==='MEMBER'">
                                            <template v-if="user.reserveModel==='OMNIBUS'||user.reserveModel==='CREDIT'">
                                                <li class="tabthcolor fontwe">信用額度:{{$utils.getAnsS(user.creditBalance)}}</li>
                                                <li class="green fontwe">余額:{{$utils.getAnsS(user.useCreditBalance)}}</li>
                                            </template>
                                            <template v-if="user.reserveModel==='OMNIBUS'||user.reserveModel==='RECHARGE'">
                                                <li class="tabthcolor fontwe">充值額度:{{$utils.getAnsS(user.rechargeBalance)}}</li>
                                                <li class="green fontwe">可用:{{$utils.getAnsS(user.useRechargeBalance)}}</li>
                                            </template>
                                        </ul>
                                    </template>
                                    <template v-else>
                                        <ul v-if="user.userLevel>2">
                                            <template v-if="user.reserveModel==='OMNIBUS'||user.reserveModel==='CREDIT'">
                                               <!-- <a-tooltip placement="topLeft" arrow-point-at-center :title="'信用会员額度:'+$utils.getAnsS(user.memberCreditBalance)">
                                                    <li class="tabthcolor fontwe">信用額度:{{$utils.getAnsS(user.creditBalance)}}</li>
                                                    <li class="green fontwe">余額:{{$utils.getAnsS(user.useCreditBalance)}}</li>
                                                </a-tooltip>-->
                                                <a-popover >
                                                    <template slot="content">
                                                        <template v-if="user.userLevel<=11">
                                                            代理可用信用余额:{{$utils.getAnsS(user.agentUseCreditBalance)}}
                                                            <br/>
                                                        </template>
                                                        会员可用信用余额:{{$utils.getAnsS(user.memberUseCreditBalance)}}
                                                    </template>
                                                    <li class="tabthcolor fontwe">信用額度:{{$utils.getAnsS(user.creditBalance)}}</li>
                                                    <li class="green fontwe">余額:{{$utils.getAnsS(user.useCreditBalance)}}</li>
                                                </a-popover>
                                            </template>
                                            <template v-if="user.reserveModel==='OMNIBUS'||user.reserveModel==='RECHARGE'">
                                                <!--<a-tooltip placement="topLeft" arrow-point-at-center :title="'充值会员額度:'+$utils.getAnsS(user.memberRechargeBalance)">
                                                    <li class="tabthcolor fontwe">充值額度:{{$utils.getAnsS(user.rechargeBalance)}}</li>
                                                    <li class="green fontwe">可用:{{$utils.getAnsS(user.useRechargeBalance)}}</li>
                                                </a-tooltip>-->
                                                <a-popover >
                                                    <template slot="content">
                                                        <template v-if="user.userLevel<=11">
                                                            代理可用充值余额:{{$utils.getAnsS(user.agentUseRechargeBalance)}}
                                                            <br/>
                                                        </template>
                                                        会员可用充值余额:{{$utils.getAnsS(user.memberUseRechargeBalance)}}
                                                    </template>
                                                    <li class="tabthcolor fontwe">充值额度:{{$utils.getAnsS(user.rechargeBalance)}}</li>
                                                    <li class="green fontwe">可用:{{$utils.getAnsS(user.useRechargeBalance)}}</li>
                                                </a-popover>
                                            </template>
                                        </ul>
                                    </template>
                                </td>
                            </template>
                            <template v-if="userParams.userType!=='MEMBER'">
                                <td class="forumrowhighlight">
                                    <template>
                                        {{user.disk===true?'标准':'特殊'}}
                                    </template>
                                </td>
                            </template>
                            <td v-show="userParams.userType==='agent'||userParams.userType==='allAgent'" class="forumrow">
                                <template v-if="user.userLevel===12">
                                    {{user.agentCount}}
                                </template>
                                <template v-else>
                                    <a @click="showLower(user,'agent')">{{user.agentCount}}</a>
                                </template>
                            </td>
                            <td v-show="userParams.userType==='agent'||userParams.userType==='allAgent'" class="forumrow">
                                <template v-if="user.userLevel===2">
                                    {{user.memberCount}}
                                </template>
                                <template v-else>
                                    <a @click="showLower(user,'member')">{{user.memberCount}}</a>
                                </template>
                            </td>
                            <td v-show="userParams.userType==='agent'||userParams.userType==='allAgent'" class="forumrow">
                                <template v-if="user.userLevel===12">
                                    {{user.agentCount}}
                                </template>
                                <template v-else>
                                    <a @click="showLower(user,'allAgent')">{{user.allAgentCount}}</a>
                                </template>
                            </td>
                            <td v-show="userParams.userType==='agent'||userParams.userType==='allAgent'" class="forumrow">
                                <template v-if="user.userLevel===12">
                                    {{user.allMemberCount}}
                                </template>
                                <template v-else>
                                     <a @click="showLower(user,'allMember')">{{user.allMemberCount}}</a>
                                </template>
                            </td>
                            <td class="forumrowhighlight"><span>{{moment(user.created).format("YYYY-MM-DD")}}</span></td>
                            <td class="forumrowhighlight">
                                <div style="text-align:center ">
                                    <a-select v-model="user.status" style="width: 65px" size="small" @change="changeUserStatus(user)">
                                        <a-select-option value="OPEN">
                                            启用
                                        </a-select-option>
                                        <a-select-option value="SUSPEND">
                                            冻结
                                        </a-select-option>
                                        <a-select-option value="CLOSE">
                                            停用
                                        </a-select-option>
                                    </a-select>
                                </div>
                            </td>
                            <td class="forumrowhighlight" style="text-align: left">
                                <!--
                                <a-button type="primary" size="small" class="mlr5" @click="showCopyCompanyModal(user.userId,user.username)">
                                    co
                                </a-button>
                                <a-button type="primary" size="small" class="mlr5" @click="showCopyAgent1Modal(user.userId,user.username)">
                                    ag
                                </a-button>
                                -->
                                <template v-if="user.userType==='AGENT' && user.userLevel <= 2">
                                    <!-- <router-link :to="{name:'盘口设置',params:{userId:user.userId}}">-->
                                    <a-button type="primary" size="small" class="mlr2" @click="showCompanyOddsModal(user.userId,user.username)">
                                        盘口设置
                                    </a-button>
                                    <!--</router-link>-->
                                    <a-button type="primary" size="small" class="mlr2" @click="userLotteryShow=true;editUserId=user.userId" v-if="user.userLevel===2">
                                        彩种
                                    </a-button>
                                </template>
                                <template v-if="user.userType==='AGENT' && user.userLevel ===3">
                                    <a-button v-if="!user.disk" type="primary" size="small" class="mlr2" @click="showAgent1OddsModal(user.userId,user.username,false)">
                                        盘口设置
                                    </a-button>
                                    <a-button v-if="user.disk" type="primary" size="small" class="mlr2" @click="showAgent1OddsModal(user.userId,user.username,true)">
                                        盘口设置
                                    </a-button>
                                    <a-button v-if="user.disk" type="primary" size="small" class="mlr2" @click="showAgent1RegressLimitModal(user.userId,user.username)">
                                        退水限注
                                    </a-button>
                                    <!--
                                    <a-button v-if="user.disk" type="primary" size="small" class="mlr5" @click="showAgent1MaxdiffModal(user.userId,user.username)">
                                        最大赔差
                                    </a-button>
                                    -->
                                </template>
                                <template v-if="user.userLevel >3 ">
                                    <!--user.userType==='AGENT'-->
                                    <a-button v-if="user.pgainOdds==='OPEN'" type="primary" size="small" class="mlr2" @click="showZhuanOddsModal(user.userId,user.username)">
                                        赚赔
                                    </a-button>
                                </template>
                                <template v-if=" user.userLevel >3 ">
                                    <!--user.userType==='AGENT'-->
                                    <a-button type="primary" size="small" class="mlr2" @click="showZhuanRegressModal(user.userId,user.username)">
                                        退水限注
                                    </a-button>
                                </template>
                                <template v-if="user.userType==='AGENT' && user.userLevel <=12">
                                    <template v-if="userInfoLevel===1 && user.userLevel>2">
                                    </template>
                                    <template v-else>
                                        <a-button type="primary" size="small" class="mlr2" @click="updateUser('修改'+$t('AGENT'+(userParams.userLevel+1)),user,'AGENT')">
                                            修改
                                        </a-button>
                                    </template>
                                </template>
                                <template v-if="user.userType==='MEMBER' && user.userLevel >2">
                                    <template v-if="userInfoLevel===1 && user.userLevel>2">
                                    </template>
                                    <template v-else>
                                        <a-button type="primary" size="small" class="mlr2" @click="updateUser('修改会员',user,'MEMBER')">
                                            修改
                                        </a-button>
                                    </template>
                                </template>
                                <template v-if=" user.userLevel <=13">
                                    <template v-if="userInfoLevel===1 && user.userLevel>2">
                                    </template>
                                    <template v-else>
                                        <a-button type="primary" size="small" class="mlr2" @click="userUpdatePt(user)">
                                            修改占成
                                        </a-button>
                                    </template>
                                </template>
                                <a-button type="primary" size="small" class="mlr2" @click="selUserLogShow(user)">
                                    登入日志
                                </a-button>
                                <a-button type="primary" size="small" class="mlr2" @click="selHmLogs(user)">
                                    记录
                                </a-button>
<!--                                <template v-if="user.agentCount===0 && user.memberCount===0 && user.userType==='AGENT'">
                                    <a-button type="danger" size="small" class="mlr2" @click="delUser(user.userId)">
                                        删除
                                    </a-button>
                                </template>-->
                               <!-- <a-button v-if="$utils.dateDiffDay(user.created)>30" type="danger" size="small" class="mlr2" @click="error">
                                    删除
                                </a-button>-->
                                <template v-if="user.userLevel>2">
                                    <a-button type="primary" size="small" class="mlr2" @click="selUserRunningShow(user)">
                                        余额日志
                                    </a-button>
                                </template>
                                <a-button type="danger" size="small" v-if="user.passwordError>=5" @click="resetPassword(user.username)">
                                    锁定({{user.passwordError}})
                                </a-button>
                            </td>
                        </tr>
                        <tr v-if="userList.length==0">
                            <td colspan="20" class="forumrowhighlight nohover">
                                <a-empty />
                            </td>
                        </tr>
                    </tbody>
                </table>
                <div class="p10" style="text-align: center;">
                    <a-pagination size="small" :total="userCount" show-size-changer show-quick-jumper :current="userParams.page" :pageSize="userParams.size" @change="changePage" @showSizeChange="changePage" :show-total="total => `共 ${total} 条`" />
                </div>
            </a-spin>

            <user-add :userAddShow.sync="userAddShow" :editParentId.sync="editParentId" :editUserType.sync="editUserType" :addUserName.sync="addUserName"></user-add>

            <user-pt :userPtShow.sync="userPtShow" :editUserId.sync="editUserId" :editParentId.sync="editParentId" :editUserType.sync="editUserType" :editUsername.sync="editUsername"></user-pt>

            <user-log :userLogShow.sync="userLogShow" :editUserId.sync="editUserId"></user-log>

            <user-running :userRunningShow.sync="userRunningShow" :editUserId.sync="editUserId" :runningName.sync="runningName"></user-running>

            <user-lottery :userLotteryShow.sync="userLotteryShow" :editUserId.sync="editUserId"></user-lottery>

            <user-record :userRecordShow.sync="userRecordShow" :editUserId.sync="editUserId"></user-record>

            <user-update :userUpdateShow.sync="userUpdateShow" :updateUserName.sync="updateUserName" :editUserId.sync="editUserId" :editParentId.sync="editParentId" :editUserType.sync="editUserType"></user-update>
            <!--修改占成-->
            <user-update-pt :userUpdatePtShow.sync="userUpdatePtShow" :updatePtName.sync="updatePtName" :editUserId.sync="editUserId" :editParentId.sync="editParentId" :editUserType.sync="editUserType" :editUserLevel.sync="editUserLevel"></user-update-pt>

            <!--赚水-->
            <user-zhuan-odds :key="'do_'+timer" :userZhuanOddsShow.sync="userZhuanOddsShow" :editUserId.sync="editUserId" :editUsername.sync="editUsername"></user-zhuan-odds>
            <!--赚赔-->
            <user-zhuan-regress :key="'dr_'+timer" :userZhuanRegressShow.sync="userZhuanRegressShow" :editUserId.sync="editUserId" :editUsername.sync="editUsername"></user-zhuan-regress>
            <!--后桶修改公司赔率-->
            <user-company-orlf :key="'co_'+timer" :companyORLFShow.sync="companyORLFShow" :editUserId.sync="editUserId" :editUsername.sync="editUsername" :isNewUser.sync="isNewUser"></user-company-orlf>
            <!--公司修改一级赔率-->
            <user-agent1-orl :key="'ao_'+timer" :agent1ORLShow.sync="agent1ORLShow" :editUserId.sync="editUserId" :editUsername.sync="editUsername" :isNewUser.sync="isNewUser" :isDisk.sync="isDisk"></user-agent1-orl>
            <!--公司修改一级退水限注-->
            <user-agent1-regress-limit :key="'rl_'+timer" :agent1RegressLimitShow.sync="agent1RegressLimitShow" :editUserId.sync="editUserId" :editUsername.sync="editUsername"></user-agent1-regress-limit>
            <!--公司修改一级最大赚赔-->
            <user-agent1-maxdiff :key="'md_'+timer" :agent1MaxdiffShow.sync="agent1MaxdiffShow" :editUserId.sync="editUserId" :editUsername.sync="editUsername"></user-agent1-maxdiff>

            <!--额度修改-->
            <Userquota :userQuotaShow.sync="userQuotaShow" :editParentId.sync="editParentId" :editUserId.sync="editUserId"></Userquota>
        </div>
    </div>
</template>

<script>
import userAdd from "./components/user-add";
import userPt from "./components/user-pt";
import userLog from "./components/user-log";
import userRunning from "./components/user-running";
import userLottery from "./components/user-lottery";
import UserRecord from "./components/user-record";
import UserUpdate from "./components/user-update";
import UserUpdatePt from "./components/user-update-pt";
import Userquota from "./components/user-quota";
import UserZhuanOdds from "./components/user-zhuan-odds";
import UserZhuanRegress from "./components/user-zhuan-regress";
import UserCompanyOrlf from "./components/user-company-orlf";
import UserAgent1Orl from "./components/user-agent1-orl";
import UserAgent1RegressLimit from "./components/user-agent1-regress-limit";
import UserAgent1Maxdiff from "./components/user-agent1-maxdiff";

export default {
    components: {
        UserUpdate,
        UserRecord,
        userAdd,
        userPt,
        userLog,
        userLottery,
        Userquota,
        UserZhuanOdds,
        UserZhuanRegress,
        UserCompanyOrlf,
        UserAgent1Orl,
        UserAgent1RegressLimit,
        UserAgent1Maxdiff,
        UserUpdatePt,
        userRunning,
    },
    data() {
        return {
            timer: 0,
            spinning: false,
            userAddShow: false,
            userPtShow: false,
            userLogShow: false,
            userLotteryShow: false,
            userRecordShow: false,
            userUpdateShow: false,
            userQuotaShow: false,
            userZhuanOddsShow: false,
            userZhuanRegressShow: false,
            companyORLFShow: false,
            agent1ORLShow: false,
            agent1RegressLimitShow: false,
            agent1MaxdiffShow: false,
            userRunningShow:false,
            runningName:"",
            userList: [],
            userCount: 50,
            userParams: {
                page: 1,
                size: 10,
                reserveModel: "ALL",
                userType: "",
                status: "OPEN",
                username: "",
                parentId: this.$store.state.user.info.primaryId,
                userLevel: this.$store.state.user.info.userLevel,
            },
            userLowers: [],
            editUserId: 0,
            editParentId: 0,
            editUsername: null,
            editUserType: "AGENT",
            addUserName: "",
            updateUserName: "",
            userInfoLevel: this.$store.state.user.info.userLevel,
            isNewUser: false,
            isDisk: false,
            userUpdatePtShow: false,
            updatePtName: "",
            editUserLevel: null,
        };
    },
    mounted() {
        this.userParams.userType = this.$route.params.type;
        this.loadUserList();
    },
    methods: {
        selHmLogs(user){
            this.userRecordShow=true;
            this.editUserId=user.userId;
        },
        selUserLogShow(user){
          this.userLogShow=true;
          this.editUserId=user.userId;
        },
        selUserRunningShow(user){
            this.userRunningShow=true;
            this.editUserId=user.userId;
            this.runningName=user.username;
        },
        resetPassword(username){
            this.$swal({
                position: 'top-end',
                title: '解锁'+username+'账号?',
                icon: 'warning',
                showCancelButton: true,
                confirmButtonColor: '#3085d6',
                cancelButtonColor: '#d33',
                confirmButtonText: '是',
                cancelButtonText: '取消'
            }).then((res) => {
                if (res.value) {
                    this.$api.user.resetPwd(username).then((res) => {
                        this.$utils.handleThen(res, this, "解锁成功！");
                        this.loadUserList();
                    });
                }
            })

        },
        userUpdatePt(user) {
            this.userUpdatePtShow = true;
            this.editParentId = user.parentId;
            this.editUserId = user.userId;
            this.editUserLevel = user.userLevel;
            this.updatePtName = "修改占成:" + this.$t(user.userType + (user.userLevel)) + " " + user.username;
            this.editUserType = user.userType;
        },
        updateBalanceShow(user) {
            this.editParentId = user.parentId;
            this.editUserId = user.userId;
            this.userQuotaShow = true;
        },
        delUser(userId) {
            /*删除重开*/
            this.spinning = true;
            this.$api.user
                .delUserId(userId)
                .then((res) => {
                    if (res.success) {
                        this.$utils.handleThen(res, this, "删除成功！");
                        this.loadUserList();
                    }
                })
                .finally((e) => {
                    this.spinning = false;
                });
        },
        updateUser(name, user, type) {
            this.updateUserName = name;
            this.editUserType = type;
            this.editUserId = user.userId;
            this.editParentId = user.parentId;
            this.userUpdateShow = true;
        },
        loadUserList() {
            this.spinning = true;
            this.$api.user
                .getUserList(this.userParams)
                .then((res) => {
                    this.userList = res.data.dataList;
                    this.userCount = res.data.total;
                })
                .finally((e) => {
                    this.spinning = false;
                });
        },
        changePage(page, pageSize) {
            this.userParams.page = page;
            this.userParams.size = pageSize;
            this.loadUserList();
        },
        showLower(user, userType) {
            //记录本次查询数据，返回上级使用查询
            this.userLowers.push(Object.assign({}, this.userParams));
            //修改查询参数进行查询
            this.userParams.userType = userType;
            this.userParams.parentId = user.userId;
            this.userParams.userLevel = user.userLevel;
            this.userParams.username = "";
            this.changePage(1, this.userParams.size);
        },
        showUpper() {
            let lastParams = this.userLowers.pop();
            this.userParams.parentId = lastParams.parentId;
            this.userParams.userType = lastParams.userType;
            this.userParams.userLevel = lastParams.userLevel;
            this.userParams.username = "";
            this.changePage(1, this.userParams.size);
        },
        changeUserStatus(user) {
            this.$api.user
                .updateUserInfo({ userId: user.userId, status: user.status })
                .then((res) => {
                    if (res.success) {
                        this.loadUserList();
                        this.$utils.handleThen(res,this,"修改成功",false);
                    }
                });
        },
        showZhuanOddsModal(userId, username) {
            this.userZhuanOddsShow = true;
            this.editUserId = userId;
            this.editUsername = username;
            this.timer = new Date().getTime();
        },
        showZhuanRegressModal(userId, username) {
            this.userZhuanRegressShow = true;
            this.editUserId = userId;
            this.editUsername = username;
            this.timer = new Date().getTime();
        },

        showCompanyOddsModal(userId, username) {
            this.companyORLFShow = true;
            this.editUserId = userId;
            this.editUsername = username;
            this.isNewUser = false;
            this.timer = new Date().getTime();
        },
        showAgent1OddsModal(userId, username, isDisk) {
            this.agent1ORLShow = true;
            this.editUserId = userId;
            this.editUsername = username;
            this.isNewUser = false;
            this.isDisk = isDisk;
            this.timer = new Date().getTime();
        },
        showCopyCompanyModal(userId, username) {
            this.companyORLFShow = true;
            this.editUserId = userId;
            this.editUsername = username;
            this.isNewUser = true;
            this.timer = new Date().getTime();
        },
        showCopyAgent1Modal(userId, username) {
            this.agent1ORLShow = true;
            this.editUserId = userId;
            this.editUsername = username;
            this.isNewUser = true;
            this.timer = new Date().getTime();
        },

        showAgent1RegressLimitModal(userId, username) {
            this.agent1RegressLimitShow = true;
            this.editUserId = userId;
            this.editUsername = username;
            this.timer = new Date().getTime();
        },
        showAgent1MaxdiffModal(userId, username) {
            this.agent1MaxdiffShow = true;
            this.editUserId = userId;
            this.editUsername = username;
            this.timer = new Date().getTime();
        },
        error() {
            this.$swal({
                title: "功能未开放!",
                icon: "error",
                confirmButtonText: "确定",
            });
        },
    },
    watch: {
        "$route.params.type": {
            handler: function (val, old) {
                this.userParams = {
                    page: 1,
                    size: 10,
                    reserveModel: "ALL",
                    userType: val,
                    status: "OPEN",
                    username: "",
                    parentId: this.$store.state.user.info.primaryId || 1,
                    userLevel: this.$store.state.user.info.userLevel,
                };
                this.userLowers = [];
                this.loadUserList();
            },
        },
    },
};
</script>

<style scoped>
</style>
