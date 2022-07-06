<template>
    <div class="notice">
        <a-spin :spinning="spinning">
            <a-row :gutter="8">
                <a-col :span="19" class="noticelist">
                    <table class="tableborder" border="0" align="center" cellpadding="5" cellspacing="1">
                        <tbody>
                            <tr>
                                <th width="40">排序</th>
                                <th width="60">开始时间</th>
                                <th width="220">公告内容</th>
                                <th width="60">结束日期</th>
                                <th width="50">发布人</th>
                                <th width="110">功能</th>
                                <th width="90">操作</th>
                            </tr>
                            <tr v-for="(notice,idx) in notices" :key="notice.id">
                                <td class="forumrow">{{idx+1}}</td>
                                <td class="forumrowhighlight">
                                    {{moment(notice.startTime*1000).format('YYYY-MM-DD')}}<br />
                                    {{moment(notice.startTime*1000).format('HH:mm:ss')}}
                                </td>
                                <td class="forumrowhighlight">
                                    {{notice.content}}
                                </td>
                                <td class="forumrowhighlight" style="width:60px">
                                    {{moment(notice.endTime*1000).format('YYYY-MM-DD')}}<br />
                                    {{moment(notice.endTime*1000).format('HH:mm:ss')}}
                                </td>
                                <td class="forumrowhighlight">{{notice.username}}</td>
                                <td class="forumrowhighlight">
                                    <template v-if="notice.isMyself">
                                        <a-switch v-model="notice.isAlert" @click="updateNoticeState(notice.id,'is_alert',notice.isAlert)" checked-children="弹框" un-checked-children="弹框" default-checked />
                                        <a-switch v-model="notice.isAgent" @click="updateNoticeState(notice.id,'is_agent',notice.isAgent)" class="mlr5" checked-children="代理" un-checked-children="代理" default-checked />
                                        <a-switch v-model="notice.isMember" @click="updateNoticeState(notice.id,'is_member',notice.isMember)" checked-children="会员" un-checked-children="会员" default-checked />
                                    </template>
                                </td>
                                <td class="forumrowhighlight">
                                    <template v-if="notice.isMyself">
                                        <a-button type="primary" class="mr10" icon="edit" size="small" @click="toUpdateNotice(notice)"> 修改 </a-button>
                                        <a-button type="danger" icon="delete" size="small" @click="deleteNotice(notice.id)"> 删除 </a-button>
                                    </template>
                                </td>
                            </tr>
                            <tr v-if="notices.length==0">
                                <td colspan="7" class="forumrowhighlight nohover">
                                    <a-empty />
                                </td>
                            </tr>
                        </tbody>
                    </table>
                    <div class="p10" style="text-align: center;">
                        <a-pagination @change="pageChange" @showSizeChange="sizeChange" size="small" :total="total" :current="page" :pageSize="size" show-size-changer show-quick-jumper :show-total="total => `共 ${parseInt(total/size)+1} 页`" />
                    </div>
                </a-col>
                <a-col :span="5" class="addnotice">
                    <table class="tableborder" border="0" align="center" cellpadding="5" cellspacing="1" style="border-collapse: separate;width: 100%">
                        <tr>
                            <th height="22" colspan="2" sytle="line-height:150%" class="txtleft pl10">新增公告</th>
                        </tr>
                        <tbody>
                            <tr>
                                <!--<td width="80" class="forumrow txtright">公告内容</td>-->
                                <td colspan="2" class="forumrowhighlight txtleft">
                                    <a-textarea v-model="addForm.content" placeholder="请添加公告内容" :rows="10" />
                                </td>
                            </tr>
                            <tr>
                                <td class="forumrow txtright">开始时间</td>
                                <td class="forumrowhighlight txtleft">
                                    <a-date-picker v-model="addForm.startTime" @openChange="handleStartOpenChange" format="YYYY-MM-DD HH:mm:ss" :disabled-date="disabledStartDate" :allowClear="false" :show-time="{ defaultValue: moment('00:00:00', 'HH:mm:ss') }" size="small" style="120" />
                                </td>
                            </tr>
                            <tr>
                                <td class="forumrow txtright">结束时间</td>
                                <td class="forumrowhighlight txtleft">
                                    <a-date-picker v-model="addForm.endTime" @openChange="handleEndOpenChange" format="YYYY-MM-DD HH:mm:ss" :disabled-date="disabledEndDate" :allowClear="false" :show-time="{ defaultValue: moment('00:00:00', 'HH:mm:ss') }" size="small" style="120" />
                                </td>
                            </tr>
                            <tr>
                                <td class="forumrow txtright">登录弹窗</td>
                                <td class="forumrowhighlight txtleft">
                                    <span class="mlr10">
                                        <a-switch v-model="addForm.isAlert" checked-children="开" un-checked-children="关" default-checked />
                                    </span>
                                </td>
                            </tr>
                            <tr>
                                <td class="forumrow txtright">发布对象</td>
                                <td class="forumrowhighlight txtleft">
                                    <span class="pl10">
                                        <a-checkbox v-model="addForm.isAgent" value="代理">
                                            代理
                                        </a-checkbox>
                                        <a-checkbox v-model="addForm.isMember" value="会员">
                                            会员
                                        </a-checkbox>
                                    </span>
                                </td>
                            </tr>
                            <!--
                            <tr>
                                <td class="forumrow txtright">排序</td>
                                <td class="forumrowhighlight txtleft">
                                    <a-input v-model.number="addForm.ordering" size="small" allow-clear placeholder="0" style="width: 160px" />
                                </td>
                            </tr>
                            -->
                            <tr>
                                <td height="50" align="right" class="forumrow txtright"></td>
                                <td class="forumrowhighlight txtleft">
                                    <a-button type="primary" icon="plus" size="small" @click="saveNotice">
                                        新增
                                    </a-button>
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </a-col>
            </a-row>

            <!--修改抽屉-->
            <a-drawer title="修改公告" :width="350" :visible="isShowUpdate" :body-style="{ paddingBottom: '80px'}" @close="onCloseUpdateForm">
                <table class="tableborder" border="0" align="center" cellpadding="5" cellspacing="1" style="border-collapse: separate;width: 100%">
                    <tbody>
                        <tr>
                            <!--<td width="80" class="forumrow txtright">公告内容</td>-->
                            <td colspan="2" class="forumrowhighlight txtleft">
                                <a-textarea v-model="addForm.content" placeholder="请添加公告内容" :rows="10" />
                            </td>
                        </tr>
                        <tr>
                            <td class="forumrow txtright">开始时间</td>
                            <td class="forumrowhighlight txtleft">
                                <a-date-picker v-model="addForm.startTime" format="YYYY-MM-DD HH:mm:ss" :disabled-date="disabledStartDate" size="small" style="120" />
                            </td>
                        </tr>
                        <tr>
                            <td class="forumrow txtright">结束时间</td>
                            <td class="forumrowhighlight txtleft">
                                <a-date-picker v-model="addForm.endTime" format="YYYY-MM-DD HH:mm:ss" :disabled-date="disabledEndDate" size="small" style="120" />
                            </td>
                        </tr>
                        <tr>
                            <td class="forumrow txtright">登录弹窗</td>
                            <td class="forumrowhighlight txtleft">
                                <span class="mlr10">
                                    <a-switch v-model="addForm.isAlert" checked-children="开" un-checked-children="关" default-checked />
                                </span>
                            </td>
                        </tr>
                        <tr>
                            <td class="forumrow txtright">发布对象</td>
                            <td class="forumrowhighlight txtleft">
                                <span class="pl10">
                                    <a-checkbox v-model="addForm.isAgent" value="代理">
                                        代理
                                    </a-checkbox>
                                    <a-checkbox v-model="addForm.isMember" value="会员">
                                        会员
                                    </a-checkbox>
                                </span>
                            </td>
                        </tr>
                        <!--
                        <tr>
                            <td class="forumrow txtright">排序</td>
                            <td class="forumrowhighlight txtleft">
                                <a-input size="small" v-model="addForm.ordering" allow-clear placeholder="0" style="width: 160px" />
                            </td>
                        </tr>
                        -->
                        <tr>
                            <td height="50" align="right" class="forumrow txtright"></td>
                            <td class="forumrowhighlight txtleft">
                                <a-button type="primary" icon="edit" size="small" @click="updateNotice"> 修改</a-button>
                            </td>
                        </tr>
                    </tbody>
                </table>

                <div class="opnewinright">
                    <a-button :style="{ marginRight: '12px' }" size="small" @click="onCloseUpdateForm">
                        取消
                    </a-button>
                    <a-button type="primary" size="small" @click="updateNotice">
                        确定
                    </a-button>
                </div>
            </a-drawer>
        </a-spin>
    </div>
</template>

<script>
import to from "await-to-js";
import moment from "moment";
export default {
    data() {
        return {
            spinning: false,
            page: 1,
            size: 20,
            total: 0,
            notices: [],
            isShowUpdate: false,
            addForm: {
                id: null,
                content: null,
                startTime: null,
                endTime: null,
                isAlert: true,
                isAgent: true,
                isMember: true,
                ordering: 0,
                endOpen: false,
            },
        };
    },
    mounted() {
        this.requestNotice();
    },
    methods: {
        moment,
        toUpdateNotice(notice) {
            this.isShowUpdate = true;
            let {
                id,
                content,
                isAlert,
                isMember,
                isAgent,
                ordering,
                startTime,
                endTime,
            } = notice;
            this.addForm.id = id;
            this.addForm.content = content;
            this.addForm.isAlert = isAlert;
            this.addForm.isMember = isMember;
            this.addForm.isAgent = isAgent;
            this.addForm.ordering = ordering;
            this.addForm.startTime = moment(startTime * 1000);
            this.addForm.endTime = moment(endTime * 1000);
        },
        onCloseUpdateForm() {
            this.isShowUpdate = false;
            this.addForm.content = null;
            this.addForm.startTime = null;
            this.addForm.endTime = null;
            this.addForm.isAlert = true;
            this.addForm.isAgent = true;
            this.addForm.isMember = true;
            this.addForm.ordering = 0;
        },
        async deleteNotice(id) {
            this.$swal({
                title: "是否删除本公告?",
                icon: "warning",
                showCancelButton: true,
                confirmButtonColor: "#3085d6",
                cancelButtonColor: "#d33",
                confirmButtonText: "是",
                cancelButtonText: "取消",
            }).then(async (res) => {
                if (res.value) {
                    this.spinning = true;
                    let [err, data] = await to(
                        this.$api.ctrl.deleteNotice({ id })
                    );
                    if (err || !data.success) {
                        this.spinning = false;
                        this.$message.error("请求出错！！！");
                        return;
                    }
                    await this.requestNotice();
                    this.spinning = false;
                }
            });
        },
        async requestNotice() {
            this.spinning = true;
            let params = { page: this.page, size: this.size };
            let [err, data] = await to(this.$api.ctrl.getNotice(params));
            if (err || !data.success) {
                this.spinning = false;
                this.$message.error("请求出错！！！");
                return;
            }
            let { page, size, total, notices } = data.data;
            this.page = page;
            this.size = size;
            this.total = total;
            this.notices = notices;
            this.spinning = false;
        },
        async saveNotice() {
            if (this.addForm.content == null || this.addForm.content == "") {
                this.$utils.handleThen(res, this, "请至少选择代理或会员！");
                return;
            }
            if (
                this.addForm.startTime == null ||
                this.addForm.startTime == ""
            ) {
                this.$utils.handleThen(res, this, "请至少选择代理或会员！");
                return;
            }
            if (this.addForm.endTime == null || this.addForm.endTime == "") {
                this.$utils.handleThen(res, this, "请至少选择代理或会员！");
                return;
            }
            if (
                this.addForm.isAgent == false &&
                this.addForm.isMember == false
            ) {
                this.$utils.handleThen(res, this, "请至少选择代理或会员！");
                return;
            }

            this.spinning = true;
            let {
                content,
                isAlert,
                isMember,
                isAgent,
                ordering,
                startTime,
                endTime,
            } = this.addForm;
            let st = (startTime.valueOf() / 1000).toFixed(0);
            let et = (endTime.valueOf() / 1000).toFixed(0);
            let [err, res] = await to(
                this.$api.ctrl.saveNotice({
                    content,
                    isAlert,
                    isMember,
                    isAgent,
                    ordering,
                    startTime: st,
                    endTime: et,
                })
            );
            this.$utils.handleThen(res, this);
            if (err || res.data < 1 || !res.success) {
                this.spinning = false;
                return;
            }
            await this.requestNotice();
            this.addForm.content = null;
            this.addForm.startTime = null;
            this.addForm.endTime = null;
            this.addForm.isAlert = true;
            this.addForm.isAgent = true;
            this.addForm.isMember = true;
            this.addForm.ordering = 0;
            this.spinning = false;
        },
        async updateNoticeState(id, type, val) {
            let typeKey =
                type == "is_alert"
                    ? "isAlert"
                    : type == "isAgent"
                    ? "isAgent"
                    : "isMember";
            let params = { id, content: type, isAgent: val };
            let [err, data] = await to(
                this.$api.ctrl.updateNoticeState(params)
            );
            if (err || data.data < 1 || !data.success) {
                this.spinning = false;
                this.notices.find((notice) => notice.id == id)[typeKey] = !val;
                this.$message.error("新增失败！！！", err);
                return;
            }
        },
        async updateNotice() {
            if (this.addForm.content == null || this.addForm.content == "") {
                this.$utils.handleThen(res, this, "请至少选择代理或会员！");
                return;
            }
            if (
                this.addForm.startTime == null ||
                this.addForm.startTime == ""
            ) {
                this.$utils.handleThen(res, this, "请至少选择代理或会员！");
                return;
            }
            if (this.addForm.endTime == null || this.addForm.endTime == "") {
                this.$utils.handleThen(res, this, "请至少选择代理或会员！");
                return;
            }
            if (
                this.addForm.isAgent == false &&
                this.addForm.isMember == false
            ) {
                this.$utils.handleThen(res, this, "请至少选择代理或会员！");
                return;
            }

            this.spinning = true;
            let {
                id,
                content,
                isAlert,
                isMember,
                isAgent,
                ordering,
                startTime,
                endTime,
            } = this.addForm;
            let st = (startTime.valueOf() / 1000).toFixed(0);
            let et = (endTime.valueOf() / 1000).toFixed(0);
            let [err, res] = await to(
                this.$api.ctrl.updateNotice({
                    id,
                    content,
                    isAlert,
                    isMember,
                    isAgent,
                    ordering,
                    startTime: st,
                    endTime: et,
                })
            );
            this.$utils.handleThen(res, this);
            if (err || res.data < 1 || !res.success) {
                this.spinning = false;
                return;
            }
            this.isShowUpdate = false;
            await this.requestNotice();
            this.addForm.content = null;
            this.addForm.startTime = null;
            this.addForm.endTime = null;
            this.addForm.isAlert = true;
            this.addForm.isAgent = true;
            this.addForm.isMember = true;
            this.addForm.ordering = 0;
            this.spinning = false;
        },
        async sizeChange(current, size) {
            this.page = current;
            this.size = size;
            this.requestNotice();
        },
        async pageChange(page, size) {
            this.page = page;
            this.size = size;
            this.requestNotice();
        },

        disabledStartDate(startTime) {
            return startTime && startTime < moment().add(-1, "day");
        },
        disabledEndDate(endTime) {
            return (
                endTime &&
                (endTime < moment(this.addForm.startTime) ||
                    endTime > moment(this.addForm.startTime).endOf("month"))
            );
        },
        handleStartOpenChange(open) {
            if (open) {
                this.addForm.startTime = moment();
            }
        },
        handleEndOpenChange(open) {
            if (open) {
                this.addForm.endTime = this.addForm.startTime;
            }
        },
    },
};
</script>

<style scoped>
</style>