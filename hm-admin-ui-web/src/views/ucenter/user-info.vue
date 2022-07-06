<template>
    <div class="userinfo">
        <a-spin :spinning="spinning" size="large">
            <a-row>
                <a-col :span="24">
                    <a-alert type="info" message="用户基本信息">
                        <div slot="description">
                            <table class="" border="0" align="center" cellpadding="8" cellspacing="1"
                                   style="border-collapse: separate;width: 100%;font-size: 15px;"
                                   v-if="userInfo.username">
                                <tr>
                                    <td class="txtright">
                                        <span v-cloak>
                                            {{$t('AGENT'+userInfo.userLevel)}}:
                                        </span>
                                    </td>
                                    <td class="blue">{{userInfo.username}}</td>
                                    <td class="txtright">信用额度:</td>
                                    <td class="green">
                                        <span v-if="userInfo.userLevel<=2">无限</span>
                                        <span v-else>{{$utils.getAnsS(userInfo.creditBalance)}}</span>
                                    </td>
                                    <td class="txtright">余额:</td>
                                    <td class="green">
                                        <span v-if="userInfo.userLevel<=2">无限</span>
                                        <span v-else>{{$utils.getAnsS(userInfo.useCreditBalance)}}</span>
                                    </td>
                                    <td class="txtright">充值额度:</td>
                                    <td class="green">
                                        <span v-if="userInfo.userLevel<=2">无限</span>
                                        <span v-else>{{$utils.getAnsS(userInfo.rechargeBalance)}}</span>
                                    </td>
                                    <td class="txtright">余额:</td>
                                    <td class="green">
                                        <span v-if="userInfo.userLevel<=2">无限</span>
                                        <span v-else>{{$utils.getAnsS(userInfo.useRechargeBalance)}}</span>
                                    </td>
                                </tr>
                                <tr v-if="$store.state.user.info.userLevel===2" style="text-align: center">
                                    <td colspan="10" class="green" v-if="startTime!=='00'">
                                        <span style="font-size: 16px;margin-right: 15px;">营业时间: {{startTime}}至{{endTime}}</span>
                                        <template v-if="$store.state.user.info.userLevel===2">
                                            <a-tag color="#108ee9" @click="showModalDate"
                                                   style="font-size: 14px;padding: 3px 10px">
                                                修改营业时间
                                            </a-tag>
                                        </template>
                                    </td>
                                    <td colspan="10" v-else>
                                        <span
                                            style="font-size: 16px;margin-right: 15px;">暂无营业时间</span></td>
                                </tr>
                                <tr>
                                    <td colspan="10" class="txtcenter">
                                        <a-tag color="#108ee9" @click="() => (modalpassword = true)"
                                               style="font-size: 14px;padding: 3px 10px">
                                            变更密码
                                        </a-tag>
                                        <a-tag v-show="toSonLevel" color="#108ee9"
                                               @click="() => (modalSecondary = true)"
                                               style="font-size: 14px;padding: 3px 10px">
                                            变更安保密码
                                        </a-tag>
                                    </td>
                                </tr>
                            </table>
                        </div>
                    </a-alert>
                </a-col>
            </a-row>
            <a-row :gutter="16">
                <a-col :span="24">
                    <template v-for="group in groups">
                        <a-divider orientation="left" :key="group.groupId">
                            {{group.groupName}}
                        </a-divider>
                        <template v-for="lottery in group.lotterys">
                            <a-col :span="12" :key="lottery.lotteryId">
                                <table class="tableborder odds" border="0" align="center" cellpadding="2"
                                       cellspacing="1" width="100%" style="border-collapse: separate;">
                                    <tr>
                                        <th colspan="7">{{lottery.lotteryName}}</th>
                                    </tr>
                                    <tr>
                                        <th width="150">种类</th>
                                        <th v-if="markets.A">A盘(%)</th>
                                        <th v-if="markets.B">B盘(%)</th>
                                        <th v-if="markets.C">C盘(%)</th>
                                        <th v-if="markets.D">D盘(%)</th>
                                        <th>注单限额</th>
                                        <th>单期限额</th>
                                    </tr>
                                    <template v-for="kind in lottery.kinds">
                                        <tr :key="kind.kindId">
                                            <td class="forumrow">{{kind.kindName}}</td>
                                            <td v-if="markets.A" class="forumrowhighlight">
                                                {{kind.regressA}}
                                            </td>
                                            <td v-if="markets.B" class="forumrowhighlight">
                                                {{kind.regressB}}
                                            </td>
                                            <td v-if="markets.C" class="forumrowhighlight">
                                                {{kind.regressC}}
                                            </td>
                                            <td v-if="markets.D" class="forumrowhighlight">
                                                {{kind.regressD}}
                                            </td>
                                            <td v-if="markets.D" class="forumrowhighlight">
                                                {{kind.maxBet}}
                                            </td>
                                            <td v-if="markets.D" class="forumrowhighlight">
                                                {{kind.maxPeriod}}
                                            </td>
                                        </tr>
                                    </template>
                                </table>
                            </a-col>
                        </template>

                    </template>
                </a-col>
            </a-row>

            <!--修改密码-->
            <a-modal width="500" v-model="modalpassword" title="修改用户密码" centered @ok="updatePwd">
                <div>
                    <table class="" border="0" align="center" cellpadding="8" cellspacing="1"
                           style="border-collapse: separate;width: 100%">
                        <tbody>
                        <tr>
                            <td class="txtright">原始密码</td>
                            <td class="">
                                <a-input-password ref="oldPwd" style="width: 200px" @focus="focus($event)" v-model="userPwd.password"
                                                  size="small"/>
                            </td>
                        </tr>
                        <tr>
                            <td class="txtright">新设密码</td>
                            <td class="">
                                <a-input-password ref="newPwd" style="width: 200px" @focus="focus($event)"
                                                  v-model="userPwd.newPassword" size="small"/>
                            </td>
                        </tr>
                        <tr>
                            <td colspan="2" class="txtright">
                                <span class="red" style="font-size: 12px;"><i color="red">*</i> [8-16]長度,最少二个字母(0-9,a-z,@)</span>
                            </td>
                        </tr>
                        <tr>
                            <td class="txtright">确认密码</td>
                            <td>
                                <a-input-password ref="okPwd" placeholder="确认密码" @focus="focus($event)" v-model="userPwd.okPassword"
                                                  size="small" style="width: 200px"/>
                            </td>
                        </tr>
                        </tbody>
                    </table>

                </div>
            </a-modal>

            <!--修改安保密码-->
            <a-modal width="500" v-model="modalSecondary" title="修改安保密码" centered @ok="updateSecondary">
                <div>
                    <table class="" border="0" align="center" cellpadding="8" cellspacing="1"
                           style="border-collapse: separate;width: 100%">
                        <tbody>
                        <tr>
                            <td class="txtright">原始安保密码</td>
                            <td class="">
                                <a-input-password ref="oldPwd" style="width: 200px" @focus="focus($event)" v-model="userPwd.password"
                                                  size="small"/>
                            </td>
                        </tr>
                        <tr>
                            <td class="txtright">新设安保密码</td>
                            <td class="">
                                <a-input-password ref="newPwd" style="width: 200px" @focus="focus($event)"
                                                  v-model="userPwd.newPassword" size="small"/>
                            </td>
                        </tr>
                        <tr>
                            <td colspan="2" class="txtright">
                                <span class="red" style="font-size: 12px;"><i color="red">*</i> [8-16]長度,最少二个字母(0-9,a-z,@)</span>
                            </td>
                        </tr>
                        <tr>
                            <td class="txtright">确认安保密码</td>
                            <td>
                                <a-input-password ref="okPwd" placeholder="确认安保密码" @focus="focus($event)"
                                                  v-model="userPwd.okPassword" size="small" style="width: 200px"/>
                            </td>
                        </tr>
                        </tbody>
                    </table>

                </div>
            </a-modal>

            <!--修改营业时间-->
            <a-modal width="500" v-model="modalDate" title="修改营业时间" centered @ok="updateDate">
                <div>
                    <table class="" border="0" align="center" cellpadding="8" cellspacing="1"
                           style="border-collapse: separate;width: 100%">
                        <tbody>
                        <tr>
                            <td class="txtright">开始时间(当天) {{startTime}}</td>
                            <td class="">
                                <a-select v-model="dateInfo.startTime" :default-value="startTime" style="width: 120px"
                                          @change="onChangeStartTime">
                                    <template v-for="i in startTimeList">
                                        <a-select-option :value="i+':00'" v-if="i>=10">{{i}}:00</a-select-option>
                                        <a-select-option :value="'0'+i+':00'" v-else>0{{i}}:00</a-select-option>
                                    </template>

                                </a-select>
                            </td>
                        </tr>
                        <tr>
                            <td class="txtright">结束时间(次日) {{endTime}}</td>
                            <td class="">
                                <a-select v-model="dateInfo.endTime" :default-value="endTime" style="width: 120px"
                                          @change="onChangeEndTime">
                                    <a-select-option v-for="i in endTimeList" :key="i" :value="'0'+i+':00'">0{{i}}:00
                                    </a-select-option>
                                </a-select>
                            </td>
                        </tr>
                        </tbody>
                    </table>
                </div>
            </a-modal>
        </a-spin>
    </div>
</template>

<script>
    import to from "await-to-js";
    import moment from 'moment';

    export default {
        data() {
            return {
                spinning: false,
                modalpassword: false,
                modalSecondary: false,
                modalDate: false,
                groups: [],
                markets: {},
                userInfo: {
                    username: "",
                },
                userPwd: {
                    password: "", //密码
                    newPassword: "", //新密码
                    okPassword: "", //确定新密码
                },
                toSonLevel: false,
                startTimeList: [7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23],
                endTimeList: [6, 5, 4, 3, 2, 1],
                startTime: "",
                endTime: "",
                dateInfo: {
                    startTime: "",
                    endTime: "",
                },
                user:{}
            };
        },
        mounted() {
            this.loadUserInfo();
            if (this.$store.state.user.info.userLevel !== 1) {//后桶不进去查询数据
                this.requestORLF();
            }
        },
        methods: {
            onChangeStartTime(value) {
                this.dateInfo.startTime = value;
            },
            onChangeEndTime(value) {
                this.dateInfo.endTime = value;
            },
            focus(event) {
                event.currentTarget.select();
            },
            moment,
            toSon() {/*是否是子帐号和是否是后桶和公司*/
                let user = this.$store.state.user.info;
                if (user.enabledSon === false && user.userLevel <= 2) {
                    this.toSonLevel = true;
                } else {
                    this.toSonLevel = false;
                }
            },
            loadUserInfo() {
                this.spinning = true;
                let userId = this.$store.state.user.info.primaryId;
                this.$api.user.findUserBasic(userId).then((res) => {
                    if (res.success) {
                        this.userInfo = res.data;
                        this.toSon();
                        if (this.userInfo.userLevel === 2) {
                            this.toStartDate(userId);
                        }
                    }
                }).finally((e) => {
                    this.spinning = false;
                });
            },
            toStartDate(userId) {
                this.$api.user.toStartTime(userId).then((res) => {
                    if (res.success) {
                        this.startTime = res.data.startTime;
                        this.endTime = res.data.endTime;
                        this.dateInfo.startTime = res.data.startTime;
                        this.dateInfo.endTime = res.data.endTime;
                        this.user ={ ...res.data}
                    }
                });
                this.spinning = false;
            },
            showModalDate() {
                this.modalDate = true;
                let userId = this.$store.state.user.info.primaryId;
                this.toStartDate(userId);
            },
            async requestORLF() {
                this.spinning = true;
                let [err, res] = await to(this.$api.ctrl.getOdds());
                if (err || !res.success) {
                    this.spinning = false;
                    this.$utils.handleThen(res, this);
                    return;
                }
                let {
                    groups,
                    kinds: mapKinds,
                    categorys: mapCategorys,
                    lotterys: mapLotterys,
                    regress: mapRegresss,
                    odds: mapOddss,
                    markets,
                } = res.data;
                this.markets = markets;
                groups.forEach((group) => {
                    let groupId = group.groupId;
                    let lotterys = mapLotterys[groupId]||[];
                    lotterys.forEach((lottery) => {
                        let lotteryId = lottery.lotteryId;
                        let kinds = JSON.parse(JSON.stringify(mapKinds[groupId]));
                        kinds.forEach((kind) => {
                            let kindId = kind.kindId;
                            let regress = mapRegresss[lotteryId][kindId];
                            Object.assign(kind, regress);
                            let categorys = JSON.parse(
                                JSON.stringify(mapCategorys[kindId])
                            );
                            categorys.forEach((category) => {
                                let categoryId = category.categoryId;
                                let odds = mapOddss[lotteryId][categoryId];
                                Object.assign(category, odds);
                            });
                            kind.categorys = categorys;
                        });
                        lottery.kinds = kinds;
                    });
                    if(lotterys.length>0){
                        lotterys[0].isChecked = true;
                        group.lotteryId = lotterys[0].lotteryId;
                    }
                    group.isChecked = false;
                    group.lotterys = lotterys;
                });
                this.groups = groups;
                this.spinning = false;
            },
            updatePwd() {/*修改登入密码*/
                if (this.checkUser()) {
                    let params = {
                        oldPwd: this.userPwd.password,
                        newPwd: this.userPwd.newPassword,
                    };
                    this.$api.user.updatePwd(params).then((res) => {
                        if (res.success) {
                            this.modalpassword = false;
                            this.$utils.handleThen(res, this);
                        } else {
                            if(res.code===23020){
                                this.userPwd.password="";
                                this.$refs.oldPwd.focus();
                            }else if(res.code===23030){
                                this.userPwd.newPassword="";
                                this.userPwd.okPassword="";
                                this.$refs.newPwd.focus();
                            }
                            this.$utils.handleThen(res, this);
                        }
                    });
                }
            },
            updateSecondary() {/*修改安保密码*/
                if (this.checkUser()) {
                    let params = {
                        oldPwd: this.userPwd.password,
                        newPwd: this.userPwd.newPassword,
                    };
                    this.$api.user.updateSecondary(params).then((res) => {
                        if (res.success) {
                            this.modalSecondary = false;
                            this.$utils.handleThen(res, this);
                            this.$api.logs.addLogs("修改安保密码","******","******",this.$store.state.user.info.primaryId);
                        } else {
                            if(res.code===23020){
                                this.userPwd.password="";
                                this.$refs.oldPwd.focus();
                            }else if(res.code===23030){
                                this.userPwd.newPassword="";
                                this.userPwd.okPassword="";
                                this.$refs.newPwd.focus();
                            }
                            this.$utils.handleThen(res, this);
                        }
                    });
                }
            },
            updateDate() {
                //console.log(this.dateInfo)
                this.$api.user.toUpdateTime(this.dateInfo).then((res) => {
                    if (res.success) {
                        this.startTime = this.dateInfo.startTime;
                        this.endTime = this.dateInfo.endTime;
                        this.modalDate = false;
                        this.$utils.handleThen(res, this, '修改营业时间成功!', false);
                        this.$api.logs.addLogs("修改营业时间",this.user.startTime+"-"+this.user.endTime,this.startTime+"-"+this.endTime,this.$store.state.user.info.primaryId);
                    } else {
                        this.$utils.handleThen(res, this);
                    }
                })

            },
            checkUser() {
                let checked = false;
                this.checkCallBack(() => {
                    if (this.userPwd.password === "") {
                        throw new Error("请输入原始密码!");
                    }
                    if (this.userPwd.newPassword !== this.userPwd.okPassword) {
                        this.userPwd.newPassword="";
                        this.userPwd.okPassword="";
                        this.$refs.newPwd.focus();
                        throw new Error("两次输入的密码不一样,请重新输入!");
                    }
                    this.$utils.checkPassword(
                        this.userPwd.newPassword,
                        "密码格式不符合,请重新设置!"
                    );
                    checked = true;
                });
                return checked;
            },
        },
    };
</script>

<style scoped>
</style>