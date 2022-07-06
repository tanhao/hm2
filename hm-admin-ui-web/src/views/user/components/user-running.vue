<template>

  <!--日志-->
  <a-modal
      :title="'余额变动日志: '+runningName"
      :width="800"
      :visible="userRunningShow"
      :maskClosable="maskClosable"
      dialogClass="tk"
      :footer="null"
      @cancel="onClose"
  >
    <div>
      <div class="mb10 df">
        <span class="maintxt mlr10">选择日期:</span>
        <a-date-picker size="small" placeholder="选择日期" v-model="dateTime" @change="changePage(1,userParams.size)"
                       style="width: 150px" :disabled-date="disabledDate"/>
        <div class="pl10">
          <a-button type="primary" icon="search" size="small" @click="changePage(1,userParams.size)">
            查询
          </a-button>
        </div>
      </div>
      <a-spin :spinning="spinning" size="large">
        <!--        <span class="red pb10 dstable">注意:日志最多被保留14天</span>-->
        <table class="popright" border="0" align="center" cellpadding="5" cellspacing="1"
               style="border-collapse: separate;width: 100%;">
          <tbody>
          <tr>
            <th class="popth">记录日期</th>
            <th class="popth">用户</th>
            <th class="popth">记录类型</th>
            <th class="popth">变前额度</th>
            <th class="popth">额度变动</th>
            <th class="popth">变后额度</th>
            <th class="popth">修改人</th>
          </tr>
          <tr v-for="item in userList">
            <td width="150" class="forumrow">{{dateFormat(item.createDateTime)}}</td>
            <td width="100" class="forumrowhighlight">{{item.username}}</td>
            <td width="100" class="forumrowhighlight">{{$t(item.type)+'/'+$t(''+item.operation)}}</td>
            <td width="100" class="forumrowhighlight">{{$utils.getAnsS(item.balance-item.amount)}}</td>
            <td width="100" class="forumrowhighlight" :style="item.amount>0?'color:blue':'color:red'"><span
                v-if="item.amount>0">+</span>{{$utils.getAnsS(item.amount)}}
            </td>
            <td width="100" class="forumrowhighlight">{{$utils.getAnsS(item.balance)}}</td>
            <td width="100" class="forumrowhighlight">{{item.createBy}}</td>
          </tr>
          </tbody>
        </table>
        <br/>
        <div class="" style="text-align: center;">
          <a-pagination size="small" :total="userCount" show-size-changer show-quick-jumper :current="userParams.page"
                        :pageSize="userParams.size" @change="changePage" @showSizeChange="changePage"
                        :show-total="total => `共 ${total} 条`"/>
        </div>
      </a-spin>
    </div>
  </a-modal>
</template>

<script>
    import to from "await-to-js";

    export default {
        name: "user-running",
        props: {
            userRunningShow: {
                type: Boolean
            },
            editUserId: null,
            runningName: null,
        },
        data() {
            return {
                maskClosable: false,
                spinning: false,
                userParams: {
                    page: 1,
                    size: 10,
                    startTime: '',
                    userId: ''
                },
                userList: {},
                userCount: 0,
                dateTime:'',
            }
        },
        methods: {
            disabledDate(current) {
                return current < this.moment().subtract(31, 'days') || current > this.moment().endOf('day');
            },
            onClose() {
                this.$emit('update:userRunningShow', false)
            },
            changeDate(value, dateString) {
                this.params.startTime = dateString[0];
                this.params.endTime = dateString[1];
                this.selUserIdList();
            },
            changePage(page, pageSize) {
                this.userParams.page = page;
                this.userParams.size = pageSize;
                this.selUserIdList();
            },
            async selUserIdList() { /* 按ID查询 */
                this.spinning = true;
                this.userParams.userId = this.editUserId;
                this.userParams.startTime = this.dateTime.format('YYYY-MM-DD');
                let [err, data] = await to(this.$api.user.selUserRunning(this.userParams));
                if (err || !data.success) {
                    this.spinning = false;
                    return;
                }
                this.userList = data.data.dataList;
                this.userCount = data.data.total;
                this.spinning = false;
            },
        },
        watch: {
            'userRunningShow': {
                handler: function (val, old) {
                    if (val) {
                        this.dateTime = this.todayDate()
                        this.selUserIdList();
                    }
                }
            }
        }
    }
</script>

<style scoped>

</style>
