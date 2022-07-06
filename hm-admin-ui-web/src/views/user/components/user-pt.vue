<template>
    <!--显示占成-->
    <a-modal
            :title="titleName"
            :width="1200"
            :visible="userPtShow"
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
        <a-spin :spinning="spinning" v-if="editUserType=='AGENT'">
            <table class="othertable" border="0" align="center" cellpadding="5" cellspacing="1"
                   style="width: 100%;border-collapse:separate;">
                <thead>
                <tr>
                    <th rowspan="2" style="width: 80px;">彩种</th>
                    <th colspan="1" rowspan="2" style="width: 160px;">
                        <div>
                            <template v-if="basicUpper.userLevel===1">
                                <span>后桶占成</span>
                            </template>
                            <template v-else>
                                <span>{{basicUpper.usernameLabel}}最高占成</span>
                            </template>
                        </div>
                    </th>
                    <th colspan="1" rowspan="2" style="width: 50px;">
                        <a-tooltip>
                            <span>预设值</span>
                            <template slot="title">
                                第二天生效
                            </template>
                        </a-tooltip>
                    </th>
                    <template v-if="basicUpper.userLevel!==1 && editUserType!=='MEMBER'">
                        <th colspan="4" rowspan="1">
                            <div><span>{{basicUpper.adduserLabel}}占成</span></div>
                        </th>
                    </template>
                </tr>
                <template v-if="basicUpper.userLevel!==1 && editUserType!=='MEMBER'">
                    <tr>
                        <th colspan="1" rowspan="1">
                            <div><span>最低</span></div>
                        </th>
                        <th rowspan="1">预设值</th>
                        <th colspan="1" rowspan="1">
                            <div><span>最高</span></div>
                        </th>
                        <th rowspan="1">预设值</th>
                    </tr>
                </template>
                </thead>
                <tbody>
                <template v-for="([name,items],i) in types">
                    <th colspan="7" class="titbg txtleft pl10">{{name}}</th>
                    <tr v-for="item in items">
                        <td class="forumrow fontwe">
                            {{item}}
                        </td>
                        <td class="forumrowhighlight">
                            {{ownPre[item].ptPct}}
                            ({{upperPre[item].ptMinPct}}% 至 {{upperPre[item].ptMaxPct}}%)
                        </td>
                        <td class="forumrowhighlight">
                            <span v-if="ownPre[item].prePct>=0 && ownPre[item].prePct">{{ownPre[item].prePct}}%</span>
                        </td>
                        <template v-if="basicUpper.userLevel!==1 && editUserType!=='MEMBER'">

                            <td class="forumrowhighlight">
                                {{ownPre[item].ptMinPct}}
                                (最低 0%)
                            </td>
                            <td class="forumrowhighlight">
                                <span v-if="ownPre[item].preMinPct>=0">{{ownPre[item].preMinPct}}%</span>
                            </td>
                            <td class="forumrowhighlight">
                                {{ownPre[item].ptMaxPct}}
                                (最高 {{upperPre[item].ptMaxPct}}%)
                            </td>
                            <td class="forumrowhighlight">
                                <span v-if="ownPre[item].preMaxPct>=0">{{ownPre[item].preMaxPct}}%</span>
                            </td>
                        </template>
                    </tr>
                </template>
                </tbody>
            </table>

        </a-spin>
        <!--会员占成-->
        <a-spin :spinning="spinning" v-if="editUserType=='MEMBER'">
            <table class="othertable" border="0" align="center" cellpadding="5" cellspacing="1"
                   style="width: 100%;border-collapse:separate;">
                <thead>
                <tr>
                    <th style="width: 250px!important;"> 类型/帐号</th>
                    <template v-for="(v,k) in actualList" v-if="actualList[k][0].isOpenUp===1 && actualList[k][0].isOpenSelf===1">
                        <!--<th colspan="7" class="titbg txtleft pl10">{{name}}</th>-->
                        <th class="forumrow fontwe">
                            {{$t(k)}}
                        </th>
                    </template>
                </tr>
                </thead>
                <tbody v-if="actualList[101]">
                <tr v-for="(item,i) in actualList[101]" >
                    <td class="forumrowhighlight">{{$t('AGENT'+item.userLevel)}}/{{item.username}}</td>
                    <template v-for="(v,k) in actualList" v-if="actualList[k][i].isOpenUp===1 && actualList[k][i].isOpenSelf===1">
                        <td class="forumrowhighlight">
                            <span >{{actualList[k][i].actualPrePt}}%</span>
                        </td>
                    </template>
                </tr>
                </tbody>
            </table>
            <br/>
            <template v-if="actualPrePtNum===0">
                <table class="othertable" border="0" align="center" cellpadding="5" cellspacing="1"
                       style="width: 100%;border-collapse:separate;">
                    <thead>
                    <tr ><th :colspan="13">预设占成</th></tr>
                    <tr>
                        <th style="width: 250px!important;"> 类型/帐号</th>
                        <template v-for="(v,k) in actualList" v-if="actualList[k][0].isOpenUp===1 && actualList[k][0].isOpenSelf===1">
                            <!--<th colspan="7" class="titbg txtleft pl10">{{name}}</th>-->
                            <th class="forumrow fontwe">
                                {{$t(k)}}
                            </th>
                        </template>
                    </tr>
                    </thead>
                    <tbody v-if="actualList[101]">
                    <tr v-for="(item,i) in actualList[101]">
                        <td class="forumrowhighlight">{{$t('AGENT'+item.userLevel)}}/{{item.username}}</td>
                        <template v-for="(v,k) in actualList" v-if="actualList[k][i].isOpenUp===1 && actualList[k][i].isOpenSelf===1">
                            <td class="forumrowhighlight" >
                                <span >{{actualList[k][i].actualPrePt}}%</span>
                            </td>
                        </template>
                    </tr>
                    </tbody>
                </table>
            </template>
        </a-spin>
<!--        <div class="opnewinright">-->
<!--            <a-button type="primary" size="small" @click="onClose">-->
<!--                关闭-->
<!--            </a-button>-->
<!--        </div>-->
    </a-modal>
</template>

<script>
    export default {
        name: "user-pt",
        props: {
            userPtShow: {
                type: Boolean
            },
            editUserId: null,
            editParentId: null,
            editUserType: null,
            editUsername:null,
        },
        data() {
            return {
                maskClosable:false,
                types: {},
                spinning: false,
                upperPre: {},
                ownPre: {},
                basicUpper: {},
                memberPre: {},
                lotteryNameList: {},
                titleName: '',
                actualList: {},
                actualPrePtNum:0,
            }
        },
        methods: {
            onClose() {
                this.$emit('update:userPtShow', false)
            },
            loadUserPrePt() {
                this.spinning = true;
                this.$api.user.findPrePt(this.editUserId).then(res1 => {
                    if (res1.success) {
                        let strMap = new Map();
                        res1.data.forEach(item => {
                            if (strMap.has(item.groupName)) {
                                strMap.get(item.groupName).push(item.lotteryName)
                            } else {
                                strMap.set(item.groupName, [item.lotteryName])
                            }
                            this.$set(this.ownPre, item.lotteryName, item)
                        })
                        strMap.forEach((pre, key) => {
                            this.ownPre[key] = {};
                        })

                        this.$api.user.findPrePt(this.editParentId).then(res => {
                            if (res.success) {
                                let strMap = new Map();
                                res.data.forEach(item => {
                                    if (strMap.has(item.groupName)) {
                                        strMap.get(item.groupName).push(item.lotteryName)
                                    } else {
                                        strMap.set(item.groupName, [item.lotteryName])
                                    }
                                    this.upperPre[item.lotteryName] = item;
                                })
                                this.types = strMap;
                                strMap.forEach((pre, key) => {
                                    this.upperPre[key] = {};
                                })
                            }
                        }).finally(e => {
                            this.spinning = false;
                        })
                    }
                });
            },
            loadUserMemberPrePt() {/*查询会员占成*/
                this.spinning = true;
                this.actualList = [];
                this.$api.user.findActualPt(this.editUserId).then(res => {
                    if (res.success) {
                        this.actualList = res.data.resultMap;
                        this.actualPrePtNum=res.data.actualPrePtNum;
                    }
                }).finally(e => {
                    this.spinning = false;
                })
            },
            getUserInfo() {/*查询上级信息*/
                this.$api.user.findUserBasic(this.editParentId).then(res => {
                    if (res.success) {
                        this.basicUpper = res.data;
                        this.basicUpper.usernameLabel = this.$t(this.basicUpper.userType + this.basicUpper.userLevel);
                        this.basicUpper.adduserLabel = this.$t(this.basicUpper.userType + (this.basicUpper.userLevel + 1));
                    }
                })
            }
        },
        watch: {
            'userPtShow': {
                handler: function (val, old) {
                    if (val) {
                        this.types = {};
                        if (this.editUserType === "AGENT") {
                            this.loadUserPrePt();
                            this.titleName = "预览占成 ：    "+this.editUsername;
                            this.getUserInfo();
                        } else {
                            this.loadUserMemberPrePt();
                            this.titleName = "占成明细 ：    "+this.editUsername;
                        }
                    }
                }
            }
        }
    }
</script>

<style scoped>

</style>
