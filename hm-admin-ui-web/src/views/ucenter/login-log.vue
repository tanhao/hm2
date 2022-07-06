<template>
    <div>
        <a-spin :spinning="spinning" size="large">
            <div class="mb10 df">
                <span class="maintxt mlr10">日期:</span>
                <a-range-picker @change="onChange" size="small"
                          :default-value="[moment(userParams.startTime, 'YYYY-MM-DD'), moment(userParams.endTime,'YYYY-MM-DD')]" style="width: 250px">
                    <a-icon slot="suffixIcon" type="smile" />
                </a-range-picker>
                <div class="pl10">
                    <a-button type="primary" icon="search" size="small" @click="selUserIdList">
                        查询
                    </a-button>
                </div>
            </div>
            <table class="popright" border="0" align="center" cellpadding="5" cellspacing="1" style="border-collapse: separate;width: 100%;float: left">
            <tbody>
            <tr>
                <th class="popth">帐号</th>
                <th class="popth">登陆时间</th>
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
            <div class="" style="text-align: center;">
                <a-pagination size="small" :total="userCount" show-size-changer show-quick-jumper :current="userParams.page" :pageSize="userParams.size" @change="changePage" @showSizeChange="changePage" :show-total="total => `共 ${total} 条`" />
            </div>
        </a-spin>
    </div>
</template>

<script>
    import to from "await-to-js";
    export default {
        name: "login-log",
        data() {
            return {
                spinning:false,
                userList:{},
                userCount: 0,
                userParams: {
                    page: 1,
                    size: 10,
                    startTime:new Date().format('yyyy-MM-dd'),
                    endTime:new Date().format('yyyy-MM-dd'),
                },
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
                this.userParams.startTime=dateString[0];
                this.userParams.endTime=dateString[1];
            },
            changePage(page, pageSize) {
                this.userParams.page = page;
                this.userParams.size = pageSize;
                this.selUserIdList();
            },
            async selUserIdList(){ /* 按ID查询 */
                this.spinning=true;
                let [err, data] = await to(this.$api.user.selUserIdList(this.userParams));
                if (err || !data.success) {
                    this.spinning = false;
                    return;
                }
                this.userList=data.data.dataList;
                this.userCount = data.data.total;
                this.spinning=false;
            },
        },
        mounted() {
            //this.selUserIdList();
        },
    }
</script>

<style scoped>

</style>