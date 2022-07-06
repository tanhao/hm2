<template>
    <div>
        <a-spin :spinning="spinning" size="large">
            <div class="mb10 df">
                <!--<span class="maintxt mlr10">账号:</span>
                <a-input-search style="width: 120px" size="small" placeholder="账号"/>
                <div class="pl10">

                </div>-->

                <span class="maintxt mlr10">分类:</span>
                <a-select v-model="type" style="width: 120px" size="small" @change="changeType">
                    <a-select-option key="member">会员</a-select-option>
                    <a-select-option key="agent" v-if="userLevel<=11">代理</a-select-option>
                    <a-select-option key="company" v-if="userLevel <= 1">公司</a-select-option>
                    <a-select-option key="subAccount">子帐</a-select-option>
                    <a-select-option key="all">全部</a-select-option>
                </a-select>
                <span class="maintxt mlr10"></span>

                <a-button type="primary" icon="search" size="small" @click="requestOnline">
                刷新
            </a-button>
                <a-button style="" type="primary" icon="search" size="small" @click="outOnlineAll">
                    全踢出
                </a-button>
            </div>
            <table class="tableborder leftlist" border="0" align="center" cellpadding="5" cellspacing="1">
                <tr>
                    <th>序号</th>
                    <th>账号</th>
                    <th>名称</th>
                    <th>身份</th>
                    <th>登录时间</th>
                    <th>在线时间</th>
                    <th>登录IP/归属地</th>
                    <th>登陆方式/来源</th>
                    <th>基本操作</th>
                </tr>
                <tr v-for="(online,index) in onlines" :key="index+'x_'+ online.userId">
                    <td class="forumrow">{{index+1}}</td>
                    <td class="forumrow"> {{online.username}} <template v-if="online.nickName!=null&&online.nickName!=''">( {{online.nickName}})</template></td>
                    <td class="forumrow"> {{online.nickName}}</td>
                    <td class="forumrow" v-if="!online.enabledSon">{{online.userType == "MEMBER" ? (online.userLevel-3)+"级会员" : $t(online.userType+online.userLevel)}}</td>
                    <td class="forumrow" v-else>子账号</td>
                    <td class="forumrow" >{{moment(online.loginTime).format("YYYY-MM-DD")}}</td>
                    <td class="forumrow" >{{online.onlineTime}}</td>
                    <td class="forumrow">{{online.loginIp}} {{online.ipaddress?'/ '+online.ipaddress:''}}</td>
                    <td class="forumrow" >{{toHttpHeader(online.httpHeader)}}</td>
                    <td class="forumrow" >
                        <Button size="small" class="tdButton" type="primary"
                                v-on:click="outOnline(online.userId)">踢出</Button>
                    </td>
                </tr>
                <tr v-if="onlines.length ==0">
                    <td colspan="9" class="forumrowhighlight nohover"><a-empty/></td>
                </tr>
            </table>
        </a-spin>
    </div>
</template>

<script>
    import moment from 'moment';
    export default {
        name: "online",
        data() {
            return {
                spinning:false,
                spinShow:false,
                onlines: [],
                ipItems: [],
                num: 0,
                type:'member',
                userLevel: this.$store.state.user.info.userLevel,
            }
        },
        methods: {
            moment,
            outOnlineAll(){
                this.spinShow = true;
                console.log(this.type)
                this.$api.online.outOnlineAll(this.type).then(res => {
                    if (res.success) {
                        this.$utils.handleThen(res,this,false);
                        this.spinShow = false;
                        this.requestOnline();
                    }else {
                        this.$utils.handleThen(res,this);
                        this.spinShow = false;
                    }
                });
            },
            changeType(type) {
                this.type=type;
                this.requestOnline();
            },
            toHttpHeader(val){
                var jsObject = JSON.parse(val);    //转换为json对象
                return jsObject.os+"/"+jsObject.browser;
            },
             requestIps(par) {
                 this.$api.logs.getIpsList({ips:par}).then(res => {
                     if (res.success) {
                         let datas = res.data.dataList;
                         this.onlines.forEach((obj) => {
                             let ipItem  = datas.find(v=>v.ip==obj.loginIp);
                             if(ipItem){
                                 obj.ipaddress = ipItem.address;
                             }
                         });
                     }
                 });
                 this.onlines.sort(function (a,b) {
                     return moment(b.loginTime) - moment(a.loginTime);
                 });
                this.spinning = false;
            },
             requestOnline() {
                 this.spinning=true;
                 this.spinShow = true;
                 this.$api.online.getOnline(this.type).then(res => {
                     if (res.success) {
                         res.data.forEach(e=>{
                             e.ipaddress="";
                         });
                         this.onlines = res.data;
                         this.num = this.onlines.length;
                         let ips = this.onlines.map(v => v.loginIp);
                         if(ips){
                             this.spinShow = false;
                             ips = [...new Set(ips)].join();
                             if(ips.length>0){
                                 this.requestIps(ips);
                             }
                         }else{
                             this.spinShow = false;
                         }
                     }else {
                         this.$utils.handleThen(res,this);
                     }
                 }).finally((e) => {
                     this.spinning = false;
                 });
            },
             outOnline(id) {
                this.spinShow = true;
                 this.$api.online.deleteIdOnline(id).then(res => {
                     if (res.success) {
                         this.$utils.handleThen(res,this,false);
                         this.spinShow = false;
                         this.requestOnline();
                     }else {
                         this.$utils.handleThen(res,this);
                         this.spinShow = false;
                     }
                 });
            }
        },
        mounted() {
            this.requestOnline();
        },
    }
</script>

<style scoped>

</style>