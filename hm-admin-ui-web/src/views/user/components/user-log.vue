<template>

    <!--日志-->
    <a-modal
            title="登陆日志"
            :width="800"
            :visible="userLogShow"
            :maskClosable="maskClosable"
            dialogClass="tk"
            :footer="null"
            @cancel="onClose"
    >
        <div>
            <div class="mb10 df">
                <span class="maintxt mlr10">日期:</span>
                <a-range-picker @change="onChanges" size="small" :disabled-date="disabledDate"
                                :value="[userParams.startTime, userParams.endTime]" style="width: 250px">
                    <a-icon slot="suffixIcon" type="smile" />
                </a-range-picker>
            </div>
            <a-spin :spinning="spinning" size="large">
<!--                <span class="red pb10 dstable">注意:日志最多被保留7天</span>-->
                <table class="popright" border="0" align="center" cellpadding="5" cellspacing="1"
                   style="border-collapse: separate;width: 100%;">
                    <tbody>
                    <tr>
                        <th class="popth">帐号</th>
                        <th class="popth">登陆时间</th>
                        <th class="popth">登陆IP</th>
                        <th class="popth">归属地</th>
                        <th class="popth">操作记录</th>
                    </tr>
                    <tr v-for="item in userList">
                        <td width="30" class="forumrow">{{toEnabledSonName(item)}}</td>
                        <td width="100" class="forumrowhighlight">{{moment(item.createTime).format("YYYY-MM-DD HH:mm:ss")}}</td>
                        <td width="70" class="forumrowhighlight">{{item.createIp}}</td>
                        <td width="110" class="forumrowhighlight">{{item.address}}</td>
                        <td width="140" class="forumrowhighlight">{{info(item.info)}}</td>
                    </tr>
                    </tbody>
                </table>
                <div class="" style="text-align: center;">
                    <a-pagination size="small" :total="userCount" show-size-changer show-quick-jumper :current="userParams.page" :pageSize="userParams.size" @change="changePage" @showSizeChange="changePage" :show-total="total => `共 ${total} 条`" />
                </div>
            </a-spin>
        </div>
    </a-modal>
</template>

<script>
    import to from "await-to-js";
    export default {
        name: "user-log",
        props: {
            userLogShow: {
                type: Boolean
            },
            editUserId:null,
        },
        data() {
            return {
                maskClosable:false,
                spinning: false,
                userParams: {
                    page: 1,
                    size: 10,
                    startTime:'',
                    endTime:'',
                    userId:''
                },
                userList:{},
                userCount: 0,
            }
        },
        methods: {
            disabledDate(current) {
                return current && current > this.moment().endOf('day');
            },
            onChanges(value, dateString) {
                this.userParams.startTime=dateString[0];
                this.userParams.endTime=dateString[1];
                this.selUserIdList();
            },
            toEnabledSonName(val){
                var jsObject = JSON.parse(val.params);    //转换为json对象
                if(jsObject.enabledSon){
                    return val.username+"(子帐号)";
                }else {
                    return val.username;
                }
            },
            changePage(page, pageSize) {
                this.userParams.page = page;
                this.userParams.size = pageSize;
                this.selUserIdList();
            },
            info(val){
                var jsObject = JSON.parse(val);    //转换为json对象
                return jsObject.os+"/"+jsObject.browser;
            },
            onClose() {
                this.$emit('update:userLogShow', false)
            },
            async selUserIdList(){ /* 按ID查询 */
                this.spinning=true;
                this.userParams.userId=this.editUserId;
                let [err, data] = await to(this.$api.user.selUserIdByList(this.userParams));
                if (err || !data.success) {
                    this.spinning = false;
                    return;
                }
                this.userList=data.data.dataList;
                this.userCount = data.data.total;
                this.spinning=false;
            },

        },
        watch: {
            'userLogShow': {
                handler: function (val, old) {
                    if (val) {
                        this.userParams.startTime = this.moment().subtract(7, 'days').format('YYYY-MM-DD')
                        this.userParams.endTime = this.todayStr();
                        this.selUserIdList();
                    }
                }
            }
        }
    }
</script>

<style scoped>

</style>
