<template>
    <!--记录-->
    <a-modal
            title="操作记录"
            :width="800"
            :visible="userRecordShow"
            :maskClosable="maskClosable"
            dialogClass="tk"
            :footer="null"
            @cancel="onClose"
    >
        <div>
            <div class="mb10 df">
                <span class="maintxt mlr10">日期:</span>
                <a-range-picker @change="onChanges" size="small" :disabled-date="disabledDate"
                                :value="[moment(params.startTime, 'YYYY-MM-DD'), moment(params.endTime,'YYYY-MM-DD')]"style="width: 250px">
                    <a-icon slot="suffixIcon" type="smile" />
                </a-range-picker>
                <div class="pl10">
                    <a-button type="primary" icon="search" size="small" @click="toSel">
                        查询
                    </a-button>
                </div>
            </div>
            <a-spin :spinning="spinning" >
                <table class="popright" border="0" align="center" cellpadding="5" cellspacing="1"
                       style="border-collapse: separate;width: 100%;">
                    <tbody>
                    <tr>
                        <th class="popth">变更时间</th>
                        <th class="popth">变更类别</th>
                        <th class="popth">原始值</th>
                        <th class="popth">变更值</th>
                        <th class="popth">变更人</th>
                        <th class="popth">IP</th>
                        <th class="popth">IP归属地</th>
                    </tr>
                    <tr v-for="item in logsList">
                        <td width="80" class="forumrow">{{moment(item.createTime).format("YYYY-MM-DD HH:mm:ss")}}</td>
                        <td width="150" class="forumrowhighlight">{{item.createType }}</td>
                        <td width="80" class="forumrowhighlight">{{/^\d+$/.test(item.oldValue)?item.oldValue:$t(item.oldValue)}}</td>
                        <td width="80" class="forumrowhighlight">{{/^\d+$/.test(item.newValue)?item.newValue:$t(item.newValue)}}</td>
                        <td width="" class="forumrowhighlight">{{item.createUser }}</td>
                        <td width="" class="forumrowhighlight">{{item.createIp }}</td>
                        <td width="" class="forumrowhighlight">{{item.address}}</td>
                    </tr>
                    </tbody>
                </table>
                <div class="" style="text-align: center;">
                    <a-pagination size="small" :total="userCount" show-size-changer show-quick-jumper :current="params.page" :pageSize="params.size" @change="changePage" @showSizeChange="changePage" :show-total="total => `共 ${total} 条`" />
                </div>
            </a-spin>
        </div>
    </a-modal>
</template>

<script>
    export default {
        name: "user-record",
        data() {
            return {
                maskClosable:false,
                spinning: false,
                logsList:{},
                params: {
                    page: 1,
                    size: 10,
                    userId:'',
                    startTime:'',
                    endTime:'',
                },
                userCount: 0,
            }
        },
        props: {
            userRecordShow: {
                type: Boolean
            },
            editUserId:null
        },
        methods: {
            disabledDate(current) {
                return current && current > this.moment().endOf('day');
            },
            toSel(){
                this.params.page=1;
                this.selHmLogsList();
            },
            onChanges(value, dateString) {
                this.params.startTime=dateString[0];
                this.params.endTime=dateString[1];
                this.selHmLogsList();
            },
            onClose() {
                this.$emit('update:userRecordShow', false)
            },
            changePage(page, pageSize) {
                this.params.page = page;
                this.params.size = pageSize;
                this.selHmLogsList();
            },
            selHmLogsList(){
                this.params.userId=this.editUserId;
                this.$api.logs.selHmLogsIdList(this.params).then(res =>{
                    if(res.success){
                        this.logsList = res.data.dataList;
                        this.userCount = res.data.total;
                    }
                })
            }
        },
        watch: {
            'userRecordShow': {
                handler: function (val, old) {
                    if (val) {
                        this.params.startTime =this.todayStr();
                        this.params.endTime =this.todayStr();
                        this.selHmLogsList();
                    }
                }
            }
        }
    }
</script>

<style scoped>

</style>
