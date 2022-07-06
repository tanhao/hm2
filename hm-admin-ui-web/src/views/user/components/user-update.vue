<template>
    <!--修改-->
    <a-modal
            :title="updateUserName"
            :width="700"
            :visible="userUpdateShow"
            :body-style="{ paddingBottom: '0px'}"
            :maskClosable="maskClosable"
            dialogClass="tk"
            @cancel="onClose"
    >
        <template slot="footer">
            <a-button key="back" @click="onClose">
                关闭
            </a-button>
        </template>
        <a-spin :spinning="spinning">
            <table class="popright noinptonum" border="0" align="center" cellpadding="5" cellspacing="1"
                   style="border-collapse: separate;width: 100%">
                <tbody>
                <tr>
                    <td width="110" class="forumrow txtright">{{superUser.usernameLabel}}账号</td>
                    <td class="forumrowhighlight txtleft">
                        <a-input size="small" allow-clear style="width: 160px" placeholder="HM"
                                 v-model="superUser.username" disabled/>
                    </td>
                </tr>
                <tr>
                    <td class="forumrow txtright">{{superUser.adduserLabel}}账号</td>
                    <td class="forumrowhighlight txtleft">
                        <a-input size="small" allow-clear style="width: 160px;margin-right: 10px" placeholder="yytest"
                                 v-model="users.username" disabled/>
                        <span class="red"><i color="red">*</i> [2-10]长度，字母，@，数字组合</span>
                    </td>
                </tr>
                <tr>
                    <td class="forumrow txtright">登陆密码</td>
                    <td class="forumrowhighlight txtleft">
                        <a-input-password allow-clear placeholder="" size="small" v-model="users.password" @focus="focus($event)"
                                          style="width: 160px;margin-right: 10px"/>
                        <a-button type="primary" icon="edit" size="small" @click="handlePassword()">
                            修改
                        </a-button>
                        <span class="red"><i color="red">*</i> [8-16]長度,最少二个字母(0-9,a-z,@)</span>
                    </td>
                </tr>
                <tr v-if="superUser.userLevel===1">
                    <td class="forumrow txtright">安保密码</td>
                    <td class="forumrowhighlight txtleft">
                        <a-input-password allow-clear placeholder="" size="small" v-model="users.secondary" @focus="focus($event)"
                                          style="width: 160px;margin-right: 10px"/>
                        <a-button type="primary" icon="edit" size="small" @click="handleSecondary()">
                            修改
                        </a-button>
                        <span class="red"><i color="red">*</i> [8-16]長度,最少二个字母(0-9,a-z,@)</span>
                    </td>
                </tr>
                <tr v-if="superUser.userLevel===1">
                    <td class="forumrow txtright">搜索码</td>
                    <td class="forumrowhighlight txtleft">
                        <a-input size="small" inputallow-clear v-model="users.securityCode" @focus="focus($event)"
                                 style="width: 160px;margin-right: 10px"/>
                        <a-button type="primary" icon="edit" size="small" @click="handleSecurityCode()">
                            修改
                        </a-button>
                        <span class="red"><i color="red">*</i> [5]长度，数字组合</span>
                    </td>
                </tr>
                <tr v-if="superUser.userLevel===1">
                    <td class="forumrow txtright">网站名称</td>
                    <td class="forumrowhighlight txtleft">
                        <a-input size="small" allow-clear v-model="users.siteName" @focus="focus($event)"
                                 style="width: 160px;margin-right: 10px"/>
                        <a-button type="primary" icon="edit" size="small" @click="handleSiteName()">
                            修改
                        </a-button>
                        <span>[2-8]长度，可以输入中文</span>
                    </td>
                </tr>
                <tr>
                    <td class="forumrow txtright">{{superUser.adduserLabel}}名称</td>
                    <td class="forumrowhighlight txtleft">
                        <a-input size="small" allow-clear v-model="users.nickName" @focus="focus($event)"
                                 style="width: 160px;margin-right: 10px"/>
                        <a-button type="primary" icon="edit" size="small" @click="handleNickName()">
                            修改
                        </a-button>
                        <span>[5]长度，可以输入中文，空值默认取HM后桶账号数据</span>
                    </td>
                </tr>
                <tr v-if="superUser.userLevel===1 && editUserType==='AGENT'">
                    <td class="forumrow txtright">可开会员数</td>
                    <td class="forumrowhighlight txtleft">
                        <a-input-number size="small" style="width: 160px;margin-right: 10px" v-model="users.maxUser" @focus="focus($event)" :min="0"
                                        :max="999999999999"/>
                        <a-button type="primary" icon="edit" size="small" @click="handleMaxUser()">
                            修改
                        </a-button>
                    </td>
                </tr>
                <tr>
                    <td class="forumrow txtright">绑定登陆IP</td>
                    <td class="forumrowhighlight txtleft">
                        <a-textarea placeholder="绑定登陆IP" :rows="4" v-model="users.loginIpBound" allow-clear @focus="focus($event)"
                                    style="margin-bottom: 10px"/>
                        <a-button type="primary" icon="edit" size="small" @click="handleBoundIp()" style="margin-left: 170px">
                            修改
                        </a-button>
                        <span style="margin-left: 10px;">最多绑定五个IP，使用","分割</span>
                    </td>
                </tr>
                <tr v-show="superUser.userLevel!==1"
                    v-if="users.reserveModel==='OMNIBUS' || users.reserveModel==='CREDIT'">
                    <td class="forumrow txtright">信用账号额度</td>
                    <td class="forumrowhighlight txtleft">
                        <div style="float: left;width: 80%;">
                        <!--{{$utils.getAnsS(userForm.creditBalance)}}-->
                            <a-input-number  style="width: 150px" size="small" v-model="users.creditBalance"></a-input-number>
                        <span v-if="superUser.userLevel!==2">(上级余额：{{$utils.getAnsS(superUser.maxRecycleBalance)}})</span>
                            <span >{{users.creditBalance|number_chinese}}</span>
                        </div>
                        <a-button type="primary" icon="edit" size="small" @click="toCreditBalance(users)">
                            修改
                        </a-button>

                    </td>
                </tr>
                <tr v-show="superUser.userLevel!==1"
                    v-if="users.reserveModel==='OMNIBUS' || users.reserveModel==='RECHARGE'">
                    <td class="forumrow txtright">充值账号额度</td>
                    <td class="forumrowhighlight txtleft">
                        <div style="float: left;width: 80%;">
                        {{$utils.getAnsS(users.rechargeBalance)}}<span >{{users.rechargeBalance|number_chinese}}</span>
                        <span v-if="superUser.userLevel!==2">（上级余额：{{$utils.getAnsS(superUser.rechargeBalance)}}）</span>
                        </div>

                        <a-button type="primary" icon="edit" size="small" @click="showBalance(users)">
                            修改
                        </a-button>
                    </td>
                </tr>
                <tr v-if="editUserType==='MEMBER'">
                    <td class="forumrow txtright">最高盈利</td>
                    <td class="forumrowhighlight txtleft">
                        <span v-if="users.maxWin>=0">{{$utils.getAnsS(users.maxWin)}}</span>
                        <span v-else>无限制</span>
                        <a-button type="primary" icon="edit" size="small" @click="showMaxWin()"
                                  style="margin-left: 10px">
                            修改
                        </a-button>
                    </td>
                </tr>
                <!--<tr  v-if="users.userLevel===2">
                    <td class="forumrow txtright">额度模式</td>
                    <td class="forumrowhighlight txtleft">
                        {{users.reserveModel}}
                    </td>
                </tr>-->
                <template v-if="editUserType==='AGENT' && superUser.userLevel!==1">
                    <tr>
                        <td class="forumrow txtright">选择投注模式</td>
                        <td class="forumrowhighlight txtleft">
                            <a-checkbox-group v-model="users.marketSelect">
                                <a-checkbox v-for="k in superUser.marketSelect" :value="k" disabled >
                                    {{k}}
                                </a-checkbox>
                            </a-checkbox-group>
                            <span class="red"></span>
                            <template v-if="(users.marketSelect!==undefined && superUser.marketSelect!==undefined ) && users.marketSelect.length!==superUser.marketSelect.length">
                                <a-button type="primary" icon="edit" size="small" @click="onMarketSelectModal">
                                    修改
                                </a-button>
                            </template>
                        </td>
                    </tr>
                    <tr>
                        <td class="forumrow txtright">开放盘口</td>
                        <td class="forumrowhighlight txtleft">
                            <a-checkbox-group v-model="users.handicap">
                                <a-checkbox v-for="k in superUser.markets" :value="k" disabled>
                                    {{k}}盘
                                </a-checkbox>
                            </a-checkbox-group>
                            <template v-if="(users.handicap!==undefined && superUser.markets!==undefined ) && users.handicap.length!==superUser.markets.length">
                                <a-button type="primary" icon="edit" size="small" @click="onChangeHandicap">
                                    修改
                                </a-button>
                            </template>
                        </td>
                    </tr>
                </template>
                <template v-if="editUserType==='MEMBER'">
                    <tr>
                        <td class="forumrow txtright">选择投注模式</td>
                        <td class="forumrowhighlight txtleft">
                            <div style="float: left;width: 80%;">
                                <a-radio-group v-model="users.marketSelect">
                                    <a-radio v-for="k in superUser.marketSelect" :value="k" >{{k}}</a-radio>
                                </a-radio-group>
                                <span class="red">* 二选一</span>
                            </div>
                            <a-button type="primary" icon="edit" size="small" @click="okMarketSelect">
                                修改
                            </a-button>
                        </td>
                    </tr>
                    <tr>
                        <td class="forumrow txtright">开放盘口</td>
                        <td class="forumrowhighlight txtleft">
                            <a-radio-group v-model="users.handicaps">
                                <a-radio v-for="k in superUser.markets" :value="k" disabled> {{k}}盘</a-radio>
                            </a-radio-group>
                            <a-button :disabled="users.betSlip" type="primary" icon="edit" size="small" @click="onChangeHandicap">
                                修改
                            </a-button>
                        </td>
                    </tr>
                </template>
                <!--<tr v-if="superUser.userLevel===2 && editUserType==='AGENT'">
                    <td class="forumrow txtright">是否标准盘</td>
                    <td class="forumrowhighlight txtleft">
                        <a-radio-group v-model="users.disk">
                            <a-radio :value="true">是</a-radio>
                            <a-radio :value="false">否</a-radio>
                        </a-radio-group>
                    </td>
                </tr>-->
                <tr v-if="superUser.userLevel!==1 && superUser.gainOdds==='OPEN' && editUserType==='AGENT'">
                    <td class="forumrow txtright">赚赔</td>
                    <td class="forumrowhighlight txtleft">
                        <a-switch checked-children="开" un-checked-children="关"
                                  v-model="users.gainOdds=='OPEN'?true:false" @change="handleGainOdds"/>
                    </td>
                </tr>
                <tr v-if="(superUser.userLevel===2||superUser.userLevel===3) && superUser.controlOdds==='OPEN' && editUserType==='AGENT'">
                    <td class="forumrow txtright">控盘</td>
                    <td class="forumrowhighlight txtleft">
                        <a-switch checked-children="开" un-checked-children="关"
                                  v-model="users.controlOdds=='OPEN'?true:false" @change="handleControlOdds"/>
                    </td>
                </tr>
                <tr v-if="superUser.userLevel!==1 && editUserType!=='MEMBER' && superUser.bhStatus===true">
                    <td class="forumrow txtright">开放补货</td>
                    <td class="forumrowhighlight txtleft">
                        <a-switch checked-children="开" un-checked-children="关"
                                  v-model="users.bhStatus" @change="handleBhStatus" />
                    </td>
                </tr>
                <tr v-if="superUser.userLevel===1">
                    <td class="forumrow txtright">租金</td>
                    <td class="forumrowhighlight txtleft">
                        <a-input-number size="small" :min="0"  v-model="users.rental" @focus="focus($event)"
                                        style="width: 160px;margin-right: 10px"/>
                        <a-button type="primary" icon="edit" size="small" @click="handleRental()">
                            修改
                        </a-button>
                    </td>
                </tr>
                <!--<tr v-if="superUser.userLevel===1">
                    <td class="forumrow txtright">占成模式</td>
                    <td class="forumrowhighlight txtleft">
                        {{users.ptModel}}
                    </td>
                </tr>-->

                </tbody>
            </table>
        </a-spin>

        <!--修改最高盈利-->
        <a-modal
                centered
                width="600"
                title="修改最高盈利"
                :visible="showMaxWinModel"
                @ok="showMaxWinModelOK"
                @cancel="showMaxWinModel=false"
        >
            <a-radio-group v-model="maxWinRadio">
                <a-radio value="wxz">无限制</a-radio>
                <a-radio value="zdz">设置金额</a-radio>
            </a-radio-group>
            <a-input v-if="maxWinRadio==='zdz'" @focus="focus($event)" v-model="maxWin"></a-input>
            <span class="red">* 盈利金额超出此值时不能投注</span>
        </a-modal>

        <!--修改开放盘口-->
        <a-modal
                centered
                width="600"
                title="开放盘口"
                :visible="handicapModal"
                @ok="okHandicap"
                @cancel="handicapModal=false"
        >
            <div>
                <template v-if="editUserType==='AGENT'">
                    <a-checkbox-group v-model="handicap">
                        <a-checkbox v-for="k in superUser.markets" :value="k" :disabled="toMarketOpen(k)"> {{k}}盘</a-checkbox>
                    </a-checkbox-group>
                </template>
                <template v-if="editUserType==='MEMBER'">
                    <a-radio-group v-model="handicaps" v-if="editUserType==='MEMBER'">
                        <a-radio v-for="k in superUser.markets" :value="k"> {{k}}盘</a-radio>
                    </a-radio-group>
                    <span class="red">* 四选一</span>
                </template>
            </div>
        </a-modal>

        <!--修改代理选择投注模式-->
        <a-modal
                centered
                width="300px"
                title="选择投注模式"
                :visible="marketSelModal"
                @ok="okMarketSelectAG"
                @cancel="marketSelModal=false"
        >
            <div style="text-align: center">
                <template v-if="editUserType==='AGENT'">
                    <a-checkbox-group v-model="users.marketSelect" >
                        <a-checkbox v-for="k in superUser.marketSelect" :value="k" :disabled="toMarketSel(k)">
                            {{k}}
                        </a-checkbox>
                    </a-checkbox-group>
                </template>
            </div>
        </a-modal>

<!--        <div class="opnewinright">-->
<!--            <a-button :style="{ marginRight: '12px' }" size="small" @click="onClose">-->
<!--                闭关-->
<!--            </a-button>-->
<!--        </div>-->
    </a-modal>
</template>

<script>
    import to from "await-to-js";
    export default {
        name: "user-update",
        data() {
            return {
                maskClosable:false,
                spinning: false,
                listShow: [false, false, false],
                types: null,
                upperPre: {},
                ownPre: {},
                oldUpperPre: [],
                oldOwnPre: [],
                handicaps: '',
                handicap: [],
                toHandicap:[],//用于修改盘口
                users: {
                    username: '',//登录名称
                    password: '',//密码
                    nickName: '',//名称
                    loginIpBound: '',//登录IP
                    balance: 0,//余额
                    reserveModel: 'CREDIT',
                    openHandicap: '',
                    commBack: 0,
                    ptModel: 'MEMBER_PT',
                    rental: 0,
                    handicap: [],
                    ptPct: 0,
                    minPtPct: 0,
                    maxPtPct: 0,
                    prePt: {},
                    marketSelect: [],
                    gainOdds: '',
                    controlOdds: '',
                    securityCode: '',
                    userType: '',
                    maxUser: 0,
                    secondary: ''
                },
                superUser: {
                    markets: [],
                    usernameLabel: '',
                },
                showMaxWinModel: false,
                maxWinRadio: 'wxz',
                maxWin: 10000,
                handicapModal: false,//开放盘口
                marketSelModal: false,//选择投注模式
                toBetShow:false,
                userForm:{},//用于添加日志
            }
        },
        props: {
            userUpdateShow: {
                type: Boolean
            },
            editUserId: null,
            editParentId: null,
            updateUserName: '',
            editUserType: null,
        },
        methods: {
            toCreditBalance(user) {//充值
                this.spinning = true;
                let params ={};
                params.userId=user.userId;
                params.reserveModel= "CREDIT";
                if(user.creditBalance > this.userForm.creditBalance){
                    params.operation="DEPOSIT";
                    params.amount = user.creditBalance-this.userForm.creditBalance;
                }else {
                    params.operation="RECYCLE";
                    params.amount=this.userForm.creditBalance-user.creditBalance;
                }
                if( params.amount>0){
                    this.checkCallBack(() => {
                        if(this.superUser.userLevel>2){
                            if(params.operation!=="RECYCLE"){
                                this.checkTrue(this.superUser.maxRecycleBalance>params.amount, "大于上级可用余额,请重新输入！");
                            }else {
                                this.checkTrue(this.userForm.useCreditBalance>=params.amount, "下级额度以使用,请回收下级额度！");
                            }
                        }
                        //console.log(params)
                        this.$api.user.putBalance(params).then(res => {
                            if (res.success) {
                                this.$parent.loadUserList();//调用父页面方法刷新
                                this.$utils.handleThen(res, this, this.$t(''+params.operation)+" 成功！",false);
                            }else {
                                this.$utils.handleThen(res, this, this.$t(''+params.operation)+" 失败！",false);
                            }
                        });
                    });
                }
                this.spinning = false;
            },
            toMarketSel(val){/*返回是否已经选择了投注模式*/
                if(val===this.users.marketSelect[0]){
                    return true;
                }
                return  false;
            },
            toMarketOpen(val){/*返回是否已经选择了开放盘口*/
                let res=false;
                this.toHandicap.forEach(item =>{
                    if(val===item){
                        res=true;
                    }
                });
                return res;
            },
            async okPt() {
                this.spinning = true;
                let params = Object.assign({prePt: [],userId:this.users.userId});
                for (let key in this.ownPre) {
                    let pre = this.ownPre[key];
                    if (pre.lotteryId) {
                        params.prePt.push(pre);
                    }
                }
                //console.log("params",params)
                this.$api.user.putUserPt(params).then(res => {
                    if (res.success) {
                        this.upperPre.prePt = this.users.prePt;
                        this.$utils.handleThen(res, this, "修改占成成功!");
                    } else {
                        this.$utils.handleThen(res, this, "修改占成失败!");
                    }
                });
                this.spinning = false;
            },
            onChangeMinMaxPt(item) {
                if (this.superUser.userLevel === 1) {//后桶开公司时
                    this.ownPre[item]['ptMaxPct'] = 100 - this.ownPre[item]['ptPct'];
                    this.ownPre[item]['ptMinPct'] = 100 - this.ownPre[item]['ptPct'];
                }
            },
            focus(event) {
                event.currentTarget.select();
            },
            async initAddUserInfo() {
                let userId = this.editUserId, parentId = this.editParentId;
                this.users.marketSelect=[];
                let [err, data] = await to(this.$api.user.getAddUser(parentId));
                if (err || !data.success) {
                    this.spinning = false;
                    return;
                }
                this.superUser = data.data;
                this.superUser.maxRecycleBalance = Math.min(
                    this.superUser.creditBalance ,
                    this.superUser.useCreditBalance
                );
                this.superUser.usernameLabel = this.$t(this.superUser.userType + this.superUser.userLevel);
                if (this.editUserType === 'AGENT') {
                    this.superUser.adduserLabel = this.$t(this.superUser.userType + (this.superUser.userLevel + 1));
                } else {
                    this.superUser.adduserLabel = this.$t('MEMBER');
                }
                this.superUser.markets = data.data.marketOpen.split(",");
                this.superUser.marketSelect = data.data.marketSelect.split(",");
                if (this.superUser.userLevel === 2) {
                    this.superUser.balance = 9999999999;
                }
                let [err1, data1] = await to(this.$api.user.getAddUser(userId));
                if (err1 || !data1.success) {
                    this.spinning = false;
                    return;
                }
                this.users = data1.data;
                this.userForm = { ...data1.data};
                this.users.handicap = this.users.marketOpen.split(",");
                if(this.users.userType==="AGENT"){
                    this.users.marketSelect = this.users.marketSelect.split(",");
                }
                if (this.users.commBack === 0) {
                    this.users.commBackLabel = "水全退到低";
                } else if (this.users.commBack === 100) {
                    this.users.commBackLabel = "赚取所有退水";
                } else {
                    this.users.commBackLabel = "赚取" + this.users.commBack + "退水";
                }
                this.users.handicap.forEach(hp => {
                    this.users.handicaps = hp;
                });
            },
            onClose() {
                this.$emit('update:userUpdateShow', false)
            },
            onChangePre(ownPre, type) {
                for (let k in this.ownPre) {
                    let v = this.ownPre[k];
                    if (ownPre === "赛车飞艇组") {//赛车飞艇组的修改全部 其他的修改自己组
                        v[type] = this.ownPre[ownPre][type];
                    } else {
                        if (v.groupName === ownPre) {
                            v[type] = this.ownPre[ownPre][type];
                        }
                    }
                    if(type==="ptPct"){
                        if(v[type]<this.upperPre[k]['ptMinPct']){//当群改值 小于最少值给能添加的最少值
                            v[type]=this.upperPre[k]['ptMinPct'];
                        }
                    }
                    if(type==="ptMinPct"){
                        if(v[type]>this.ownPre[k]['ptMaxPct']){//当群改值 大于最大值给能添加的最大值
                            v[type]=this.ownPre[k]['ptMaxPct'];
                        }
                    }else {
                        if (v[type] > this.upperPre[k]['ptMaxPct']) {//当群改值 大于最大值给能添加的最大值
                            v[type] = this.upperPre[k]['ptMaxPct'];
                        }
                    }
                    if (this.superUser.userLevel === 1) {//后桶开公司时
                        v['ptMaxPct'] = 100 - v[type];
                        v['ptMinPct'] = 100 - v[type];
                    }
                }
            },
            changeBh(ownPre) {
                //console.log(this.ownPre[ownPre].ptBh)
                for (let k in this.ownPre) {
                    let v = this.ownPre[k];
                    if (v.groupName === ownPre) {
                        v.ptBh = this.ownPre[ownPre].ptBh;
                    }
                }
            },
            toggle(i, items) {
                this.$set(this.listShow, i, !this.listShow[i]);
            },
            async loadUserPrePt() {
                this.spinning = true;
                let [err, data] = await to(this.$api.user.findPrePt(this.editUserId));
                if (err || !data.success) {
                    this.spinning = false;
                    return;
                }
                this.oldUpperPre = Object.assign([], data.data);
                let strMap = new Map();
                data.data.forEach(item => {
                    if (strMap.has(item.groupName)) {
                        strMap.get(item.groupName).push(item.lotteryName)
                    } else {
                        strMap.set(item.groupName, [item.lotteryName])
                    }
                    this.$set(this.ownPre, item.lotteryName, item)
                    // this.ownPre[item.lotteryName] = item;
                })
                strMap.forEach((pre, key) => {
                    let group = data.data.filter(e => {
                        return e.groupName === key;
                    })
                    let ptMaxPct = Math.min.apply(Math, group.map(o => {
                        return o.ptMaxPct
                    }));
                    let ptMinPct = Math.min.apply(Math, group.map(o => {
                        return o.ptMinPct
                    }));
                    let ptPct = Math.min.apply(Math, group.map(o => {
                        return o.ptPct
                    }));
                    let preMaxPct = Math.min.apply(Math, group.map(o => {
                        return o.preMaxPct
                    }));
                    let preMinPct = Math.max.apply(Math, group.map(o => {
                        return o.preMinPct
                    }));
                    let prePct = Math.max.apply(Math, group.map(o => {
                        return o.prePct
                    }));
                    this.$set(this.ownPre, key, {
                        ptMaxPct,
                        ptMinPct,
                        ptPct,
                        preMaxPct,
                        preMinPct,
                        prePct,
                        ptBh: true
                    })
                })
                let [err1, data1] = await to(this.$api.user.findPrePt(this.editParentId));
                if (err1 || !data1.success) {
                    this.spinning = false;
                    return;
                }
                this.oldUpperPre = Object.assign([], data1.data);
                let strMap1 = new Map();
                data1.data.forEach(item => {
                    if (strMap1.has(item.groupName)) {
                        strMap1.get(item.groupName).push(item.lotteryName)
                    } else {
                        strMap1.set(item.groupName, [item.lotteryName])
                    }
                    this.upperPre[item.lotteryName] = item;
                });
                this.types = strMap1;
                strMap1.forEach((pre, key) => {
                    let group = data1.data.filter(e => {
                        return e.groupName === key;
                    });
                    let ptMaxPct = Math.max.apply(Math, group.map(o => {
                        return o.ptMaxPct
                    }));
                    let ptMinPct = Math.min.apply(Math, group.map(o => {
                        return o.ptMinPct
                    }));
                    let ptPct = Math.min.apply(Math, group.map(o => {
                        return o.ptMaxPct
                    }));
                    let preMaxPct = Math.min.apply(Math, group.map(o => {
                        return o.preMaxPct
                    }));
                    let preMinPct = Math.max.apply(Math, group.map(o => {
                        return o.preMinPct
                    }));
                    let prePct = Math.max.apply(Math, group.map(o => {
                        return o.prePct
                    }));
                    this.upperPre[key] = {ptMaxPct, ptMinPct, ptPct, preMaxPct, preMinPct, prePct};
                });
                this.spinning = false;
            },
            toMessageInfo(msg, type) {/*提示*/
                this.$swal({
                        title: msg,
                        icon: type,
                        confirmButtonText: '确定'
                    }
                )
            },
            handlePassword() {/*修改密码*/
                const self = this;
                this.spinning = true;
                this.$confirm({
                    title: '修改密码',
                    content: '是否修改当前密码',
                    okText: '确认',
                    onOk() {
                        let params = {userId: self.users.userId, password: self.users.password};
                        self.$api.user.updateUserInfo(params).then(res => {
                            if (res.success) {
                                self.users.password="";
                                self.$utils.handleThen(res, self, "修改密码成功!",false);
                                if(self.editUserType==="MEMBER"){//会员修改密码后踢出会员
                                    self.$api.online.deleteIdOnline(self.users.userId).then({});
                                }
                                self.$api.logs.addLogs("修改密码","******","******",params.userId);
                            } else {
                                self.$utils.handleThen(res, self, "修改密码失败!");
                            }
                        })
                    },
                    cancelText: '取消',
                    onCancel() {
                        self.toMessageInfo("已取消修改", 'info');
                    },
                });
                this.spinning = false;
            },
            handleSecondary() {/*修改安保密码*/
                const self = this;
                this.spinning = true;
                this.$confirm({
                    title: '修改安保密码',
                    content: '是否修改当前安保密码',
                    okText: '确认',
                    onOk() {
                        let params = {userId: self.users.userId, secondary: self.users.secondary};
                        self.$api.user.updateUserInfo(params).then(res => {
                            if (res.success) {
                                self.users.secondary="";
                                self.$utils.handleThen(res, self, "修改安保密码成功!");
                                self.$api.logs.addLogs("修改安保密码","******","******",params.userId);
                            } else {
                                self.$utils.handleThen(res, self, "修改安保密码失败!");
                            }
                        })
                    },
                    cancelText: '取消',
                    onCancel() {
                        self.toMessageInfo("已取消修改", 'info');
                    },
                });
                this.spinning = false;
            },
            handleSecurityCode() {/*搜索码*/
                let code = this.users.securityCode + "";
                console.log(this.userForm)
                const self = this;
                this.spinning = true;
                this.$confirm({
                    title: '修改搜索码',
                    content: '是否修改当前搜索码',
                    okText: '确认',
                    onOk() {
                        let params = {userId: self.users.userId, securityCode: code};
                        self.$api.user.updateUserInfo(params).then(res => {
                            if (res.success) {
                                self.$utils.handleThen(res, self, "修改搜索码成功!");
                                self.$api.logs.addLogs("修改搜索码",self.userForm.securityCode||"",code,params.userId);
                            } else {
                                self.$utils.handleThen(res, self, "修改搜索码失败!");
                            }
                        })
                    },
                    cancelText: '取消',
                    onCancel() {
                        self.toMessageInfo("已取消修改", 'info');
                    },
                });
                this.spinning = false;
            },
            handleSiteName() {/*网站名*/
                let siteName = this.users.siteName + "";
                const self = this;
                this.spinning = true;
                this.$confirm({
                    title: '修改网站名称',
                    content: '是否修改当前网站名称',
                    okText: '确认',
                    onOk() {
                        let params = {userId: self.users.userId, siteName: siteName};
                        self.$api.user.updateUserInfo(params).then(res => {
                            if (res.success) {
                                self.$parent.loadUserList();//调用父页面方法刷新
                                self.$utils.handleThen(res, self, "修改网站名称成功!");
                                self.$api.logs.addLogs("修改网站名称",self.userForm.siteName||"",params.siteName,params.userId);
                            } else {
                                self.$utils.handleThen(res, self, "修改网站名称失败!");
                            }
                        })
                    },
                    cancelText: '取消',
                    onCancel() {
                        self.toMessageInfo("已取消修改", 'info');
                    },
                });
                this.spinning = false;
            },
            handleNickName() {/*公司名称*/
                let nickName = this.users.nickName + "";
                let name="";
                if(this.editUserType==="AGENT"){
                    if(this.users.userLevel===2)
                        name="公司";
                    else
                        name="代理";
                }else {
                    name="会员";
                }
                const self = this;
                this.spinning = true;
                this.$confirm({
                    title: '修改'+name+'名称',
                    content: '是否修改当前'+name+'名称',
                    okText: '确认',
                    onOk() {
                        let params = {userId: self.users.userId, nickName: nickName};
                        self.$api.user.updateUserInfo(params).then(res => {
                            if (res.success) {
                                self.$parent.loadUserList();//调用父页面方法刷新
                                self.$utils.handleThen(res, self, "修改"+name+"名称成功!");
                                self.$api.logs.addLogs("修改"+name+"名称",self.userForm.nickName||"",params.nickName,params.userId);
                            } else {
                                self.$utils.handleThen(res, self, "修改"+name+"名称失败!");
                            }
                        })
                    },
                    cancelText: '取消',
                    onCancel() {
                        self.toMessageInfo("已取消修改", 'info');
                    },
                });
                this.spinning = false;
            },
            handleMaxUser() {
                const self = this;
                this.spinning = true;
                this.$confirm({
                    title: '修改可开会员数',
                    content: '是否修改当前可开会员数',
                    okText: '确认',
                    onOk() {
                        let params = {userId: self.users.userId, maxUser: self.users.maxUser};
                        self.$api.user.updateUserInfo(params).then(res => {
                            if (res.success) {
                                self.$utils.handleThen(res, self, "修改可开会员数成功!");
                                self.$api.logs.addLogs("修改可开会员数",self.userForm.maxUser||"",params.maxUser,params.userId);
                            } else {
                                self.$utils.handleThen(res, self, "修改可开会员数失败!");
                            }
                        })
                    },
                    cancelText: '取消',
                    onCancel() {
                        self.toMessageInfo("已取消修改", 'info');
                    },
                });
                this.spinning = false;
            },
            handleBoundIp() {/*绑定登陆IP*/
                let loginIpBound = this.users.loginIpBound + "";
                const self = this;
                this.spinning = true;
                this.$confirm({
                    title: '修改绑定登录IP',
                    content: '是否修改当前登录IP',
                    okText: '确认',
                    onOk() {
                        let params = {userId: self.users.userId, loginIpBound: loginIpBound};
                        self.$api.user.updateUserInfo(params).then(res => {
                            if (res.success) {
                                self.$utils.handleThen(res, self, "修改绑定登录IP成功!");
                                self.$api.logs.addLogs("修改绑定登录IP",self.userForm.loginIpBound||"",params.loginIpBound,params.userId);
                            } else {
                                self.$utils.handleThen(res, self, "修改绑定登录IP失败!");
                            }
                        })
                    },
                    cancelText: '取消',
                    onCancel() {
                        self.toMessageInfo("已取消修改", 'info');
                    },
                });
                this.spinning = false;
            },
            handleRental() {
                let rental = this.users.rental + "";
                const self = this;
                this.spinning = true;
                this.$confirm({
                    title: '修改租金',
                    content: '是否修改当前租金',
                    okText: '确认',
                    onOk() {
                        let params = {userId: self.users.userId, rental: rental};
                        self.$api.user.updateUserInfo(params).then(res => {
                            if (res.success) {
                                self.$utils.handleThen(res, self, "修改租金成功!");
                                self.$api.logs.addLogs("修改租金",self.userForm.rental||"",params.rental,params.userId);
                            } else {
                                self.$utils.handleThen(res, self, "修改租金失败!");
                            }
                        })
                    },
                    cancelText: '取消',
                    onCancel() {
                        self.toMessageInfo("已取消修改", 'info');
                    },
                });
                this.spinning = false;
            },
            okMarketSelect() {/*修改选择投注模式*/
                this.checkNull(this.users.marketSelect, "必须选择一个投注模式！");
                let params = {userId: this.users.userId, marketSelect: this.users.marketSelect};
                const self = this;
                this.spinning = true;
                this.$confirm({
                    title: '修改选择投注模式',
                    content: '是否修改当前选择投注模式为 ' + this.users.marketSelect,
                    okText: '确认',
                    onOk() {
                        self.$api.user.updateUserInfo(params).then(res => {
                            if (res.success) {
                                self.$utils.handleThen(res, self, "修改选择投注模式成功!");
                                self.$api.logs.addLogs("修改选择投注模式",self.userForm.marketSelect||"",params.marketSelect,params.userId);
                            } else {
                                self.$utils.handleThen(res, self, "修改选择投注模式失败!");
                            }
                        })
                    },
                    cancelText: '取消',
                    onCancel() {
                        self.toMessageInfo("已取消修改", 'info');
                    },
                });
                this.spinning = false;
            },
            okMarketSelectAG() {/*修改代理选择投注模式*/
                this.checkNull(this.users.marketSelect, "必须选择一个投注模式！");
                let marketSelect = this.users.marketSelect.join(",");
                let params = {userId: this.users.userId, marketSelect: marketSelect};
                const self = this;
                this.spinning = true;
                this.$confirm({
                    title: '修改选择投注模式',
                    content: '是否修改当前选择投注模式为 ' + this.users.marketSelect,
                    okText: '确认',
                    onOk() {
                        self.$api.user.updateUserInfo(params).then(res => {
                            if (res.success) {
                                self.$utils.handleThen(res, self, "修改选择投注模式成功!");
                                self.$api.logs.addLogs("修改选择投注模式",self.userForm.marketSelect||"",params.marketSelect,params.userId);
                            } else {
                                self.$utils.handleThen(res, self, "修改选择投注模式失败!");
                            }
                        })
                    },
                    cancelText: '取消',
                    onCancel() {
                        self.toMessageInfo("已取消修改", 'info');
                    },
                });
                this.marketSelModal = false;
                this.spinning = false;
            },
            onChangeHandicap() {/*修改开放盘口*/
                this.handicap = this.users.handicap;
                this.toHandicap=this.users.handicap;
                this.handicaps = this.users.handicaps;
                this.handicapModal = true;
            },
            onMarketSelectModal(){/*打开修改选择投注模式*/
                //this.handicap = this.users.marketSelect;
                this.marketSelModal = true;
            },
            okHandicap() {/*修改开放盘口*/
                let hp = [];
                if (this.editUserType === 'MEMBER') {
                    hp.push(this.handicaps);
                } else {
                    for (let key in this.handicap) {
                        hp.push(this.handicap[key]);
                    }
                    hp.sort((a, b) => a.replace("IDC", "SG").localeCompare(b.replace("IDC", "SG")));
                }
                if (hp.length === 0) {
                    return this.toMessageInfo("必须选择一个盘口", 'info');
                }
                let marketOpen = hp.join(",");
                let params = {userId: this.users.userId, marketOpen};
                if (marketOpen !== this.users.marketOpen) {
                    const self = this;
                    this.spinning = true;
                    this.$confirm({
                        title: '修改开放盘口',
                        content: '是否修改当前开放盘口',
                        okText: '确认',
                        onOk() {
                            console.log(params)
                            self.$api.user.updateUserInfo(params).then(res => {
                                if (res.success) {
                                    self.users.marketOpen = marketOpen;
                                    self.users.handicap = self.users.marketOpen.split(",");
                                    self.users.handicaps = self.users.marketOpen;
                                    self.handicapModal = false;
                                    self.$utils.handleThen(res, self, "修改开放盘口成功!",false);
                                    self.$api.logs.addLogs("修改开放盘口",self.userForm.marketOpen||"",params.marketOpen,params.userId);
                                } else {
                                    self.$utils.handleThen(res, self, "修改开放盘口失败!");
                                }
                            })
                        },
                        cancelText: '取消',
                        onCancel() {
                            self.toMessageInfo("已取消修改", 'info');
                        },
                    });
                    this.spinning = false;
                } else {
                    this.handicapModal=false;
                    this.toMessageInfo("无修改", 'info');
                }
            },
            showMaxWinModelOK() {/*修改最高盈利*/
                if (this.maxWinRadio === 'wxz') {
                    this.maxWin = -1;
                }
                if (this.users.maxWin === this.maxWin) {
                    return self.toMessageInfo("无修改", 'info');
                }
                const self = this;
                this.spinning = true;
                this.$confirm({
                    title: '修改最高盈利',
                    content: '是否修改当前最高盈利',
                    okText: '确认',
                    onOk() {
                        let params = {userId: self.users.userId, maxWin: self.maxWin};
                        self.$api.user.updateUserInfo(params).then(res => {
                            if (res.success) {
                                self.users.maxWin=params.maxWin;
                                self.showMaxWinModel = false;
                                self.$utils.handleThen(res, self, "修改最高盈利成功!");
                                self.$api.logs.addLogs("修改最高盈利",self.userForm.maxWin||"",params.maxWin,params.userId);
                            } else {
                                self.$utils.handleThen(res, self, "修改最高盈利失败!");
                            }
                        })
                    },
                    cancelText: '取消',
                    onCancel() {
                        self.toMessageInfo("已取消修改", 'info');
                    },
                });
                this.spinning = false;
            },
            showMaxWin() {/*打开修改最高盈利框*/
                if (this.users.maxWin >= 0) {
                    this.maxWinRadio = 'zdz';
                    this.maxWin = this.users.maxWin;
                } else {
                    this.maxWinRadio = 'wxz';
                    this.maxWin = 10000;
                }
                this.showMaxWinModel = true;
            },
            showBalance(user) {
                this.$parent.updateBalanceShow(user);
                this.onClose();
            },
            handleGainOdds(gainOdds) {/*修改赚赔*/
                let status = gainOdds === true ? '开放' : '关闭';
                const self = this;
                this.spinning = true;
                this.$confirm({
                    title: '修改赚赔',
                    content: '是否' + status + this.users.username + '赚赔',
                    okText: '确认',
                    onOk() {
                        gainOdds = gainOdds === true ? 'OPEN' : 'CLOSE';
                        let params = {userId: self.users.userId, gainOdds};
                        self.$api.user.updateUserInfo(params).then(res => {
                            if (res.success) {
                                self.users.gainOdds = gainOdds;
                                self.$utils.handleThen(res, self, "修改赚赔成功!");
                                self.$api.logs.addLogs("修改赚赔",self.userForm.gainOdds,gainOdds,params.userId);
                            } else {
                                self.$utils.handleThen(res, self, "修改赚赔失败!");
                            }
                        })
                    },
                    cancelText: '取消',
                    onCancel() {
                        self.toMessageInfo("已取消修改", 'info');
                    },
                });
                this.spinning = false;
            },
            handleControlOdds(controlOdds) {/*修改控盘*/
                let status = controlOdds === true ? '开放' : '关闭';
                const self = this;
                this.spinning = true;
                this.$confirm({
                    title: '修改控盘',
                    content: '是否' + status + this.users.username + '控盘',
                    okText: '确认',
                    onOk() {
                        controlOdds = controlOdds ===true ? 'OPEN' : 'CLOSE';
                        let params = {userId: self.users.userId, controlOdds};
                        console.log(params,controlOdds);
                        self.$api.user.updateUserInfo(params).then(res => {
                            if (res.success) {
                                self.users.controlOdds = controlOdds;
                                self.$utils.handleThen(res, self, "修改控盘成功!");
                                self.$api.logs.addLogs("修改控盘",self.userForm.controlOdds,controlOdds,params.userId);
                            } else {
                                self.$utils.handleThen(res, self, "修改控盘失败!");
                            }
                        })
                    },
                    cancelText: '取消',
                    onCancel() {
                        self.toMessageInfo("已取消修改", 'info');
                    },
                });
                this.spinning = false;
            },
            handleBhStatus(bhStatus){/*修改补货状态*/
                let status = bhStatus === true ? '开放 ' : '关闭 ';
                const self = this;
                this.spinning = true;
                this.$confirm({
                    title: '修改补货状态',
                    content: '是否 ' + status + this.users.username + ' 补货状态',
                    okText: '确认',
                    onOk() {
                        let params = {userId: self.users.userId, bhStatus};
                        self.$api.user.updateUserInfo(params).then(res => {
                            if (res.success) {
                                self.users.bhStatus = bhStatus;
                                self.$utils.handleThen(res, self, "修改补货状态成功!");
                                self.$api.logs.addLogs("修改补货状态",self.userForm.bhStatus,bhStatus,params.userId);
                            } else {
                                self.$utils.handleThen(res, self, "修改补货状态失败!");
                            }
                        })
                    },
                    cancelText: '取消',
                    onCancel() {
                        self.users.bhStatus = bhStatus===true?false:true;
                        self.toMessageInfo("已取消修改", 'info');
                    },
                });
                this.spinning = false;
            }
        },
        watch: {
            'userUpdateShow': {
                handler: function (val, old) {
                    if (val) {
                        this.loadUserPrePt();
                        this.initAddUserInfo();
                    }
                }
            }
        }
    }
</script>

<style scoped>

</style>
