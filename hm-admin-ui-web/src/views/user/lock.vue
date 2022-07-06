<template>
    <div class="autofutllwith">
        <div>
            <div class="mb10 df">
                <div class="pl10">
                    <a-button type="primary" icon="search" size="small" @click="initLockList">
                        刷新
                    </a-button>
                </div>
            </div>

            <table class="tableborder" border="0" align="center" cellpadding="5" cellspacing="1">
                <thead>
                <tr>
                    <th>账号</th>
                    <th>用户类型</th>
                    <th>用户状态</th>
                    <th>登录状态</th>
                    <th>累计密码错误次数</th>
                    <th>累计解锁次数</th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="item in lockeds">
                    <td class="forumrow">{{ item.username }}</td>
                    <td class="forumrow">{{ $t(item.lvl) }}<br/>{{item.userType}}</td>
                    <td class="forumrow">
                        <span v-if="item.status==='OPEN'">启用</span>
                        <span v-if="item.status==='SUSPEND'">冻结</span>
                        <span v-if="item.status==='CLOSE'">停用</span>
                    </td>
                    <td class="forumrow">
                        <span v-if="item.passwordError>=5" class="red">锁定</span>
                        <span v-else>正常</span>
                    </td>
                    <td class="forumrow">
                        <a @click="getErrorInfo(item.username,201)">{{ item.countError }}</a>
                    </td>
                    <td class="forumrow">
                        <a @click="getErrorInfo(item.username,301)">{{ item.unlockCount }}</a>
                    </td>
                </tr>
                </tbody>
            </table>
        </div>
        <a-modal
            title="密码错误记录"
            :width="800"
            :visible="showInfo"
            dialogClass="tk"
            :footer="null"
            @cancel="showInfo=false"
        >
            <div>
                <table class="popright" border="0" align="center" cellpadding="5" cellspacing="1"
                       style="border-collapse: separate;width: 100%;">
                    <thead>
                    <tr>
                        <th class="popth">账号</th>
                        <th class="popth">记录类型</th>
                        <th class="popth">创建ip</th>
                        <th class="popth">创建时间</th>
                        <th class="popth">创建人</th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr v-for="item in infos">
                        <td class="forumrow">{{item.username}}</td>
                        <td class="forumrow">{{item.logType}}</td>
                        <td class="forumrow">{{item.createIp}}</td>
                        <td class="forumrow">{{moment(item.createTime).format("YYYY-MM-DD HH:mm:ss")}}</td>
                        <td class="forumrow">{{item.createBy}}</td>
                    </tr>
                    </tbody>
                </table>
            </div>
        </a-modal>
    </div>
</template>

<script>
export default {
    name: "lock",
    data() {
        return {
            lockeds: [],
            infos: [],
            showInfo: false
        }
    },
    methods: {
        initLockList() {
            this.$api.user.lockList().then(res => {
                if (res.success) {
                    this.lockeds = res.data;
                }
            })
        },
        getErrorInfo(username,type) {
            this.$api.user.lockInfo(username,type).then(res => {
                if (res.success) {
                    this.showInfo = true;
                    this.infos = res.data;
                }
            })
        }
    },
    mounted() {
        this.initLockList();
    }
}
</script>

<style scoped>

</style>