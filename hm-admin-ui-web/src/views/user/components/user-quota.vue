<template>
    <div>
    <!--额度修改-->
    <a-modal
            title="额度修改"
            :width="500"
            :visible="userQuotaShow"
            :maskClosable="maskClosable"
            :body-style="{ paddingBottom: '0px'}"
            dialogClass="tk quoto"
            @cancel="onClose"
    >
        <template slot="footer">
            <a-button key="back" @click="onClose">
                关闭
            </a-button>
        </template>
        <a-spin :spinning="spinning">
            <table class="popright" border="0" align="center" cellpadding="8" cellspacing="1"
               style="border-collapse: separate;width: 100%">
            <tbody>
            <tr>
                <td width="110" class="forumrow txtright">修改账号</td>
                <td class="forumrowhighlight txtleft"> {{user.username}} </td>
            </tr>
            <tr>
                <td class="forumrow txtright">上级可用额度</td>
                <td class="forumrowhighlight txtleft">
                    <ul>
                        <li class="tabthcolor fontwe" v-if="user.reserveModel==='OMNIBUS' || user.reserveModel==='CREDIT'">信用额度:
                            <span v-if="user.userLevel>3">
                                {{$utils.getAnsS(basicUpper.useCreditBalance)}}
                            </span>
                            <span v-else>无限</span>
                        </li>
                        <li class="tabthcolor fontwe" v-if="user.reserveModel==='OMNIBUS' || user.reserveModel==='RECHARGE'">充值余额:
                            <span v-if="user.userLevel>3">{{$utils.getAnsS(basicUpper.useRechargeBalance)}}</span>
                            <span v-else>无限</span>
                        </li>
                    </ul>
                </td>
            </tr>
            <tr>
                <td class="forumrow txtright">账号类型</td>
                <td class="forumrowhighlight txtleft">
                    {{ $t(""+user.reserveModel)}}
                </td>
            </tr>
            <tr v-if="user.reserveModel==='OMNIBUS' || user.reserveModel==='CREDIT'">
                <td class="forumrow txtright">信用额度</td>
                <td class="forumrowhighlight txtleft">
                    <span class="tabthcolor fontwe mr10 w100">
                        <!--{{$utils.getAnsS(user.useCreditBalance)}} -->
                        <a-input-number :precision="2" ref="CREDITS" @focus="focus($event)" style="width: 150px" size="small" v-model="user.creditBalance"></a-input-number>

                    </span>
                    <span>{{user.creditBalance|number_chinese}}</span>
                    <a-button v-if="user.userType==='AGENT'" type="primary"  class="mr10" size="small" @click="allBalance('CREDIT')">
                        全回收
                    </a-button>
                    <!--<a-button type="primary" size="small" class="mr10" @click="toShwRecModel('RECYCLE','CREDIT')">
                        回收
                    </a-button>
                    <a-button type="primary" size="small" class="mr10" @click="toShwRecModel('DEPOSIT','CREDIT')">
                        充值
                    </a-button>-->
                    <a-button v-if="user.userType==='MEMBER'" type="primary" size="small"  class="mr10" @click="nullBalance('CREDIT')">
                        清零
                    </a-button>
                    <a-button type="primary" size="small" :disabled="updateBut"  class="mr10" @click="updateCreditBalance(user)">
                        修改
                    </a-button>
                    <br/>
                    最少修改:
                    <template v-if="(userForm.creditBalance-userForm.useCreditBalance)>0">
                        {{$utils.getAnsS(userForm.creditBalance-userForm.useCreditBalance)}}
                    </template>
                   <template v-else>0</template>
                    <template v-if="user.userLevel>3">
                        最大修改:{{$utils.getAnsS(userForm.creditBalance+basicUpper.useCreditBalance)}}
                    </template>

                </td>
            </tr>
            <tr v-if="user.reserveModel==='OMNIBUS' || user.reserveModel==='RECHARGE'">
                <td class="forumrow txtright">充值账户余额</td>
                <td class="forumrowhighlight txtleft">
                    <span class="tabthcolor fontwe mr10 w100"> {{$utils.getAnsS(user.useRechargeBalance)}}</span>
                    <a-button v-if="user.userType==='MEMBER'" type="primary" size="small"  class="mr10" @click="nullBalance('RECHARGE')">
                        清零
                    </a-button>
                    <a-button v-if="user.userType==='AGENT'" type="primary" size="small"  class="mr10" @click="allBalance('RECHARGE')">
                        全回收
                    </a-button>
                    <a-button type="primary" size="small" class="mr10" @click="toShwRecModel('WITHDRAWAL','RECHARGE')" v-if="user.userType==='MEMBER'">
                        提现
                    </a-button>
                    <a-button type="primary" size="small" class="mr10" @click="toShwRecModel('RECYCLE','RECHARGE')">
                        回收
                    </a-button>
                    <a-button type="primary" size="small" class="mr10" @click="toShwRecModel('DEPOSIT','RECHARGE')">
                        充值
                    </a-button>
                </td>
            </tr>
            </tbody>
        </table>
        </a-spin>
<!--        <div class="opnewinright">-->
<!--        <a-button type="primary" :style="{ marginRight: '12px' }" size="small" @click="onClose">-->
<!--            取消-->
<!--        </a-button>-->

<!--        </div>-->
    </a-modal>

        <!--充值-->
        <a-modal
                :width="500"
                v-model="recModel"
                :title="'下线'+$t(recParams.reserveModel)+'充值'"
                @ok="toCreditBalance"
                :maskClosable="maskClosable"
        >
            <!-- RECHARGE 充值 -->
            <div v-if="recParams.reserveModel==='RECHARGE'">
                <table class="" border="0" align="center" cellpadding="8" cellspacing="1"
                       style="border-collapse: separate;width: 100%">
                    <tbody>
                    <tr>
                        <td class="txtright">上级账号</td>
                        <td class="">
                            <span>{{basicUpper.username}}</span>
                        </td>
                    </tr>
                    <tr>
                        <td class="txtright">上级充值余额</td>
                        <td class="">
                            <span v-if="user.userLevel>3">
                                {{$utils.getAnsS(basicUpper.useRechargeBalance)}}
                            </span>
                            <span v-else>无限</span>
                        </td>
                    </tr>
                    <tr>
                        <td class="txtright">充值账号</td>
                        <td>
                            <span>{{user.username}}</span>
                        </td>
                    </tr>
                    <tr>
                        <td class="txtright">充值金额</td>
                        <td>
                            <a-input-number :precision="2" style="width: 150px" ref="DEPOSIT" :min="0"   v-model="recParams.amount" size="small"></a-input-number>
                        </td>
                    </tr>
                    <tr>
                        <th></th>
                        <td style="text-align: left">
                            {{recParams.amount|number_chinese}}
                        </td>
                    </tr>
                    </tbody>
                </table>
            </div>
            <!-- 回收 -->
            <div v-else>
                <table class="" border="0" align="center" cellpadding="8" cellspacing="1"
                       style="border-collapse: separate;width: 100%">
                    <tbody>
                    <tr>
                        <td class="txtright">上级账号</td>
                        <td class="">
                            <span>{{basicUpper.username}}</span>
                        </td>
                    </tr>
                    <tr>
                        <td class="txtright">上级信用余额</td>
                        <td class="">
                            <span v-if="user.userLevel>3">
                                {{$utils.getAnsS(basicUpper.useCreditBalance)}}
                            </span>
                            <span v-else>无限</span>
                        </td>
                    </tr>
                    <tr>
                        <td class="txtright">充值账号</td>
                        <td>
                            <span>{{user.username}}</span>
                        </td>
                    </tr>
                    <tr>
                        <td class="txtright">充值金额</td>
                        <td>
                            <a-input-number :precision="2" style="width: 150px" ref="DEPOSIT" :min="0"  v-model="recParams.amount" size="small"></a-input-number>
                        </td>
                    </tr>
                    <tr>
                        <th></th>
                        <td style="text-align: left">
                            {{recParams.amount|number_chinese}}
                        </td>
                    </tr>
                    </tbody>
                </table>

            </div>
        </a-modal>

        <!--回收-->
        <a-modal
                    :width="500"
                    v-model="recRecModel"
                    :title="'回收'+$t(recParams.reserveModel)+'额度'"
                    :maskClosable="maskClosable"
                    @ok="toCreditBalance"
            >
                <!--RECHARGE 充值 -->
            <div v-if="recParams.reserveModel==='RECHARGE'">
                    <table class="" border="0" align="center" cellpadding="8" cellspacing="1"
                           style="border-collapse: separate;width: 100%">
                        <tbody>
                        <tr>
                            <td class="txtright">上级账号</td>
                            <td class="">
                                <span>{{basicUpper.username}}</span>
                            </td>
                        </tr>
                        <tr>
                            <td class="txtright">回收额度账号</td>
                            <td>
                                <span>{{user.username}}</span>
                            </td>
                        </tr>
                        <tr>
                            <td class="txtright">充值额度</td>
                            <td>
                                <span>{{$utils.getAnsS(user.rechargeBalance)}}</span>
                            </td>
                        </tr>
                        <tr>
                            <td class="txtright">
                                <template v-if="user.userType==='AGENT'">
                                    代理余额
                                </template>
                                <template v-else>
                                    会员余额
                                </template>
                            </td>
                            <td>
                                <span>{{$utils.getAnsS(user.useRechargeBalance)}}</span>
                            </td>
                        </tr>
                        <tr>
                            <td class="txtright">可回收充值额度</td>
                            <td class="">
                                <span >{{$utils.getAnsS(user.maxRecycleRecBalance)}}</span>
                            </td>
                        </tr>
                        <tr>
                            <td class="txtright">回收充值额度</td>
                            <td>
                               <a-input-number :precision="2" ref="RECYCLE" style="width: 150px" :min="0"  size="small"  v-model="recParams.amount" ></a-input-number>
                                <a-button  type="primary" size="small"  class="mr10" @click="allRecyclew('RECHARGE')">
                                    全部
                                </a-button>
                            </td>
                        </tr>
                        <tr>
                            <th></th>
                            <td style="text-align: left">
                                {{recParams.amount|number_chinese}}
                            </td>
                        </tr>
                        </tbody>
                    </table>

                </div>
            <!-- 回收 -->
            <div v-else>
                    <table class="" border="0" align="center" cellpadding="8" cellspacing="1"
                       style="border-collapse: separate;width: 100%">
                    <tbody>
                    <tr>
                        <td class="txtright">上级账号</td>
                        <td class="">
                            <span>{{basicUpper.username}}</span>
                        </td>
                    </tr>
                    <tr>
                        <td class="txtright">回收额度账号</td>
                        <td>
                            <span>{{user.username}}</span>
                        </td>
                    </tr>
                    <tr>
                        <td class="txtright">信用额度</td>
                        <td>
                            <span>{{$utils.getAnsS(user.creditBalance )}}</span>
                        </td>
                    </tr>
                    <tr>
                        <td class="txtright">信用余额</td>
                        <td>
                            <span>{{$utils.getAnsS(user.useCreditBalance)}}</span>
                        </td>
                    </tr>
                    <tr>
                        <td class="txtright">可回收信用额度</td>
                        <td class="">
                            <span>{{$utils.getAnsS(user.maxRecycleBalance)}}</span>
                        </td>
                    </tr>
                    <tr>
                        <td class="txtright">回收信用额度</td>
                        <td>
                            <a-input-number :precision="2" ref="RECYCLE" style="width: 150px" :min="0"  size="small" v-model="recParams.amount"></a-input-number>
                            <a-button  type="primary" size="small"  class="mr10" @click="allRecyclew('CREDIT')">
                                全部
                            </a-button>
                        </td>
                    </tr>
                    <tr>
                        <th></th>
                        <td style="text-align: left">
                            {{recParams.amount|number_chinese}}
                        </td>
                    </tr>
                    </tbody>
                </table>
                </div>
        </a-modal>

        <!--提现-->
        <a-modal
                :width="500"
                v-model="recWdwModel"
                title="会员提现额度"
                @ok="toCreditBalance"
                :maskClosable="maskClosable"
        >
            <div >
                <table class="" border="0" align="center" cellpadding="8" cellspacing="1"
                       style="border-collapse: separate;width: 100%">
                    <tbody>
                    <tr>
                        <td class="txtright">上级账号</td>
                        <td class="">
                            <span>{{basicUpper.username}}</span>
                        </td>
                    </tr>
                    <tr>
                        <td class="txtright">可回提现金额</td>
                        <td class="">
                            <span>{{$utils.getAnsS(withdrawalAmt)}}</span>
                        </td>
                    </tr>
                    <tr>
                        <td class="txtright">提现账号</td>
                        <td>
                            <span>{{user.username}}</span>
                        </td>
                    </tr>
                    <tr>
                        <td class="txtright">提现金额</td>
                        <td>
                            <a-input-number  :precision="2"  ref="WITHDRAWAL" style="width: 150px" :min="0"  size="small"   v-model="recParams.amount"></a-input-number>
                            <a-button  type="primary" size="small"  class="mr10" @click="allWithdrawal()">
                                全部
                            </a-button>
                        </td>
                    </tr>
                    <tr>
                        <th></th>
                        <td style="text-align: left">
                            {{recParams.amount|number_chinese}}
                        </td>
                    </tr>
                    </tbody>
                </table>
            </div>
        </a-modal>

    </div>
</template>

<script>
    import to from "await-to-js";
    import {mapActions, mapGetters} from 'vuex'
    export default {
        name: "user-update",
        props: {
            userQuotaShow: {
                type: Boolean
            },
            editParentId:null,
            editUserId:null,
        },
        data() {
            return {
                maskClosable:false,
                modalRecharge: false,
                modalRecovery:false,
                basicUpper:{},//上级数据
                user:{},//自己数据
                spinning:false,
                recParams:{
                    amount:null,
                },
                recModel:false,// 充值
                recWdwModel:false,// 提现
                recRecModel:false,// 回收
                withdrawalAmt:0,//可提现
                userForm:{},//用于添加日志
                updateBut:false,
            }
        },
        mounted() {

        },
        methods: {
            focus(event) {
                event.currentTarget. select();
            },
            allWithdrawal(){/*全部可以提现金额*/
                this.recParams.amount =this.withdrawalAmt;
            },
            allRecyclew(type){/*全部可以回收金额*/
                if(type==='CREDIT'){
                    this.recParams.amount =this.user.maxRecycleBalance;//信用
                }else {
                    this.recParams.amount =this.user.maxRecycleRecBalance;//充值
                }
            },
            updateCreditBalance(user){
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
                        if(this.basicUpper.userLevel>2){
                            if(params.operation!=="RECYCLE"){
                               this.checkTrue(this.basicUpper.maxRecycleBalance>=params.amount, "大于上级可用余额,请重新输入！");
                            }else {
                                if(this.userForm.userType==='AGENT'){
                                    this.checkTrue(this.userForm.useCreditBalance>=params.amount, "下级额度以使用,请回收下级额度！");
                                }
                            }
                            this.checkTrue(user.creditBalance<=(this.userForm.creditBalance+this.basicUpper.useCreditBalance).toFixed(2), "大于可修改的范围请重新输入！");
                        }
                        this.checkTrue(user.creditBalance>=(this.userForm.creditBalance-this.userForm.useCreditBalance).toFixed(2), "小于可修改的范围请重新输入！");
                        this.updateBut=true;
                        this.$api.user.putBalance(params).then(res => {
                            if (res.success) {
                                this.$parent.loadUserList();//调用父页面方法刷新
                                this.loadUserBase();
                                this.$utils.handleThen(res, this, this.$t(''+params.operation)+" 成功！",false);
                                this.onClose();
                            }else {
                                this.$utils.handleThen(res, this, this.$t(''+params.operation)+" 失败！",false);
                            }
                            this.updateBut=false;
                        });
                    });
                }
                this.spinning = false;
            },
            onClose() {
                this.$emit('update:userQuotaShow', false)
            },
            async loadUserBase() {
                this.withdrawalAmt=0;
                this.spinning = true;
                let [err, data] = await to(this.$api.user.getAddUser(this.editUserId));
                if (err || !data.success) {
                    this.spinning = false;
                    return;
                }
                this.user = data.data;
                this.userForm = { ...data.data};
                this.user.maxRecycleBalance = Math.min(
                    this.user.creditBalance ,
                    this.user.useCreditBalance
                );
                this.user.maxRecycleRecBalance = Math.min(
                    this.user.rechargeBalance ,
                    this.user.useRechargeBalance
                );
                this.withdrawalAmt=Math.max(this.user.useRechargeBalance-this.user.rechargeBalance,0);//可提现额度= MAX(余额-总充值额度,0)
                if (this.user.userLevel === 2) {
                    this.user.superUseRecBalance = 99999999999999;
                    this.user.superUseBalance = 99999999999999;
                }
                if(this.user.userLevel>=3){//等级小于3 上级额度无限 就不查询上级数据了
                    let [err, data] = await to(this.$api.user.findUserBasic(this.editParentId));
                    if (err || !data.success) {
                        this.spinning = false;
                        return;
                    }
                    this.basicUpper = data.data;
                    this.basicUpper.maxRecycleBalance = Math.min(
                        this.basicUpper.creditBalance ,
                        this.basicUpper.useCreditBalance
                    );
                }
                this.spinning = false;
                if(this.user.reserveModel==='CREDIT'){
                    this.$refs.CREDITS.focus();
                }
            },
            toCreditBalance(){//充值
                this.spinning = true;
                this.checkCallBack(() => {
                    this.checkTrue(this.recParams.amount > 0, "请输入金额！");
                    let msg = "充值成功";//当前修改的是二级代理以上的需要验证存款上级余额是否足够
                    if(this.user.userLevel>3){
                        if (this.recParams.operation === "DEPOSIT") {
                            let maxUse = this.basicUpper.useCreditBalance;
                            if (this.recParams.reserveModel === "RECHARGE") {
                                maxUse = this.basicUpper.useRechargeBalance;
                            }
                            this.checkTrue(this.recParams.amount <= maxUse, "上级可用余额不足！" );
                        }
                    }
                    //取款验证，当前余额是否足够
                    if (this.recParams.operation === "WITHDRAWAL") {
                        msg = "取款成功";
                        /*let maxUse = this.user.rechargeBalance;
                        if (this.recParams.reserveModel === "RECHARGE") {
                            maxUse = this.user.useRechargeBalance;
                        }*/
                        this.checkTrue(this.recParams.amount <= this.withdrawalAmt.toFixed(2) , "提取金额超出当前余额！" );

                    }
                    //回收验证
                    if (this.recParams.operation === "RECYCLE") {
                        msg = "回收成功";
                        let maxUse = this.user.maxRecycleBalance ;
                        if (this.recParams.reserveModel === "RECHARGE") {
                            maxUse = this.user.maxRecycleRecBalance ;
                        }
                        this.checkTrue(this.recParams.amount <= maxUse, "回收金额超出当前余额！" );
                    }
                    this.recParams.userId = this.user.userId;
                    /*if(this.user.betWaiting===true && this.recParams.operation !== "DEPOSIT"){
                        this.checkTrue(false,"会员有未完成注单,请完成后在操作！" );
                        this.$parent.loadUserList();//调用父页面方法刷新
                        this.onClose();
                    }else {*/
                        this.$api.user.putBalance(this.recParams).then(res => {
                            if (res.success) {
                                this.$utils.handleThen(res, this, msg,false);
                                this.recModel = false;
                                this.recRecModel = false;
                                this.recWdwModel = false;
                                this.loadUserBase();
                                this.$parent.loadUserList();//调用父页面方法刷新
                                this.onClose();
                            }else {
                                this.$utils.handleThen(res, this, "",false);
                            }
                        });
                   // }

                });
                this.spinning = false;
            },
            allBalance(reserveModel) {/* 全回收额度 */
                const self = this;
                this.spinning = true;
                this.recParams.reserveModel = reserveModel;
                this.recParams.userId = this.user.userId;
                this.checkCallBack(() => {
                    if(this.user.betWaiting===true){
                        this.checkTrue(false, "会员有未完成注单,请完成后在操作！" );
                        this.$parent.loadUserList();//调用父页面方法刷新
                        this.onClose();
                    }else {
                        this.$confirm({
                            title: '全回收额度',
                            content: '是否回收 '+self.user.username+' 和他下线的所有 '+self.$t(''+reserveModel)+'额度！',
                            okText: '确认',
                            onOk() {
                                self.$api.user.allBalance(self.recParams).then(res => {
                                    if (res.success) {
                                        self.$utils.handleThen(res, self, "全回收额度成功!",false);
                                        self.loadUserBase();
                                        self.$parent.loadUserList();//调用父页面方法刷新
                                        self.$emit('update:userQuotaShow',false);
                                        self.onClose();
                                    } else {
                                        self.$utils.handleThen(res, self, "全回收额度失败!");
                                    }
                                    self.loadUserBase();
                                })
                            },
                            cancelText: '取消',
                            onCancel() {
                                self.toMessageInfo("已取消修改", 'info');
                            },
                        });
                    }
                });
                this.spinning = false;
            },
            nullBalance(reserveModel){//清零会员
                const self = this;
                this.spinning = true;
                this.checkCallBack(() => {
                    if(this.user.betWaiting===true){
                        this.checkTrue(false,"会员有未完成注单,请完成后在操作！" );
                        this.$parent.loadUserList();//调用父页面方法刷新
                        this.onClose();
                    }else {
                        this.$confirm({
                            title: '清零会员额度',
                            content: '是否清零会员 '+self.user.username+' 的'+self.$t(''+reserveModel)+'额度！',
                            okText: '确认',
                            onOk() {
                                self.$api.user.nullMemBalance({userId:self.user.userId,reserveModel:reserveModel}).then(res => {
                                    if (res.success) {
                                        self.$utils.handleThen(res, self, "清零会员额度成功!",false);
                                        self.loadUserBase();
                                        self.$parent.loadUserList();//调用父页面方法刷新
                                        self.$emit('update:userQuotaShow',false);
                                        self.onClose();
                                    } else {
                                        self.$utils.handleThen(res, self, "清零会员额度失败!");
                                    }
                                    self.loadUserBase();
                                })
                            },
                            cancelText: '取消',
                            onCancel() {
                                self.toMessageInfo("已取消修改", 'info');
                            },
                        });
                    }
                });
                this.spinning = false;
            },
            toShwRecModel(operation,reserveModel){//打开充值和回收 余额
                this.recParams.amount=null;
                this.recParams.operation = operation;
                this.recParams.reserveModel = reserveModel;
                if (operation === "DEPOSIT") {
                    this.recModel = true;
                    this.$nextTick(() => {
                        this.$refs.DEPOSIT.focus();
                    })
                } else if (operation === "WITHDRAWAL") {
                    this.recWdwModel = true;
                    this.$nextTick(() => {
                        this.$refs.WITHDRAWAL.focus();
                    })
                } else if (operation === "RECYCLE") {
                    this.recRecModel = true;
                    this.$nextTick(() => {
                        this.$refs.RECYCLE.focus();
                    })
                }
            },
            toMessageInfo(msg, type) {/*提示*/
                this.$swal({
                        title: msg,
                        icon: type,
                    }
                );
            },
        },
        watch: {
            'userQuotaShow': {
                handler: function (val, old) {
                    if (val) {
                        this.loadUserBase();
                    }
                }
            },

        }

    }
</script>

<style scoped>
    .ant-input-number-handler-wrap{
        display: none !important;
    }

</style>
