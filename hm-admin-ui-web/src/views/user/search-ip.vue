<template>
    <div >
        <a-spin :spinning="spinning" size="large">
            <a-row :gutter="8">
            <a-col :span="12">
                <div class="mb10 df">
                    <span class="maintxt mlr10">日期:</span>
                    <a-range-picker @change="onChange" size="small"
                                    :default-value="[moment(allUserParams.startTime, 'YYYY-MM-DD'), moment(allUserParams.endTime,'YYYY-MM-DD')]"style="width: 250px">
                        <a-icon slot="suffixIcon" type="smile" />
                    </a-range-picker>
                    <span class="maintxt mlr10">用户名:</span>
                    <a-input-search v-model="allUserParams.username" style="width: 120px" size="small" placeholder="用户名"/>
                    <div class="pl10">
                        <a-button type="primary" icon="search" size="small" @click="toSel">
                            查询
                        </a-button>
                    </div>
                </div>
                <table class="popright" border="0" align="center" cellpadding="5" cellspacing="1" style="border-collapse: separate;width: 100%;float: left">
                    <tbody>
                    <tr>
                        <th class="popth">账号</th>
                        <th class="popth">记录时间</th>
                        <th class="popth">IP</th>
                        <th class="popth">IP归属地</th>
                        <th class="popth">操作记录</th>
                    </tr>
                    <tr v-for="item in allUserList">
                        <td width="" class="forumrow">{{toEnabledSonName(item)}}</td>
                        <td width="" class="forumrowhighlight">{{moment(item.createTime).format("YYYY-MM-DD HH:mm:ss")}}</td>
                        <td width="" class="forumrowhighlight">{{item.createIp}}</td>
                        <td width="" class="forumrowhighlight">{{item.address}}</td>
                        <td width="" class="forumrowhighlight">{{$t(item.logType)}}</td>
                    </tr>
                    </tbody>
                </table>
                <div class="p10" style="text-align: center;">
                    <a-pagination size="small" :total="allUserCount" show-size-changer show-quick-jumper :current="allUserParams.page" :pageSize="allUserParams.size" @change="allChangePage" @showSizeChange="allChangePage" :show-total="total => `共 ${total} 条`" />
                </div>
            </a-col>

            <a-col :span="12">
                <div class="mb10 df">
                    <span class="maintxt mlr10">IP地址:</span>
                    <a-input-search style="width: 150px"  v-model="userParams.createIp"  size="small" placeholder="IP地址"/>
                    <div class="pl10">
                        <a-button type="primary" icon="search" size="small" @click="toIpList">
                            查询
                        </a-button>
                    </div>
                </div>
                <table class="popright" border="0" align="center" cellpadding="5" cellspacing="1" style="border-collapse: separate;width: 100%;float: left">
                    <tbody>
                    <tr>
                        <th class="popth">账号</th>
                        <th class="popth">记录时间</th>
                        <th class="popth">IP</th>
                        <th class="popth">IP归属地</th>
                        <th class="popth">操作记录</th>
                    </tr>
                    <tr v-for="item in userList">
                        <td width="" class="forumrow">{{toEnabledSonName(item)}}</td>
                        <td width="" class="forumrowhighlight">{{moment(item.createTime).format("YYYY-MM-DD HH:mm:ss")}}</td>
                        <td width="" class="forumrowhighlight">{{item.createIp}}</td>
                        <td width="" class="forumrowhighlight">{{item.address}}</td>
                        <td width="" class="forumrowhighlight">{{$t(item.logType)}}</td>
                    </tr>
                    </tbody>
                </table>
                <div class="p10" style="text-align: center;">
                    <a-pagination size="small" :total="userCount" show-size-changer show-quick-jumper :current="userParams.page" :pageSize="userParams.size"  @change="changePage" @showSizeChange="changePage" :show-total="total => `共 ${total} 条`" />
                </div>
            </a-col>
        </a-row>
        </a-spin>

    </div>
    
</template>

<script>
    import to from "await-to-js";

    export default {
        data() {
            return {
                spinning:false,
                userList:{},
                allUserList:{},
                userCount: 0,
                userParams: {
                    page: 1,
                    size: 10,
                    createIp:"",
                },
                allUserCount: 0,
                allUserParams: {
                    page: 1,
                    size: 10,
                    startTime:new Date().format('yyyy-MM-dd'),
                    endTime:new Date().format('yyyy-MM-dd'),
                    username:""
                }
            }
        },
        methods: {
            toEnabledSonName(val){
                var jsObject = JSON.parse(val.params);    //转换为json对象
                if(jsObject.enabledSon){
                    return val.username+"(子帐号)";
                }else {
                    return val.username;
                }
            },
            onChange(value, dateString) {
                this.allUserParams.startTime=dateString[0];
                this.allUserParams.endTime=dateString[1];
                //console.log(this.allUserParams);
                this.userLoginList();
            },
            allChangePage(page, pageSize) {
                this.allUserParams.page = page;
                this.allUserParams.size = pageSize;
                this.userLoginList();
            },
            changePage(page, pageSize) {
                this.userParams.page = page;
                this.userParams.size = pageSize;
                this.toIpList();
            },
            toSel(){
                this.allUserParams.page=1;
                this.userLoginList();
            },
            async userLoginList(){ /* 按日期和用户名查询 */
                this.spinning=true;
                let [err, data] = await to(this.$api.user.selUserNameList(this.allUserParams));
                if (err || !data.success) {
                    this.spinning = false;
                    return;
                }
                this.allUserList=data.data.dataList;
                this.allUserCount = data.data.total;
                this.spinning=false;
            },
            async toIpList(){/* 按IP查询 */
                this.spinning=true;
                let [err, data] = await to(this.$api.user.selIPList(this.userParams));
                if (err || !data.success) {
                    this.spinning = false;
                    return;
                }
                this.userList=data.data.dataList;
                this.userCount = data.data.total;
                this.spinning=false;
            }
        },
        mounted() {
            //this.userLoginList();
        },
    };
</script>

<style scoped>

</style>