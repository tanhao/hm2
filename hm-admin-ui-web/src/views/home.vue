<template>
    <div class="allusermain">
        <a-spin :spinning="spinning">
            <table class="tableborder" border="0" align="center" cellpadding="5" cellspacing="1">
                <tbody>
                    <tr>
                        <th width="40">ID</th>
                        <th width="80">开始时间</th>
                        <th width="220">公告内容</th>
                        <th width="90">结束日期</th>
                    </tr>
                    <tr v-for="(notice,index) in notices" :key="notice.id">
                        <td class="forumrow">{{index+1}}</td>
                        <td class="forumrowhighlight">
                            {{moment(notice.startTime*1000).format('YYYY-MM-DD HH:mm:ss')}}<br />
                        </td>
                        <td class="forumrowhighlight">
                            <p class="alltxtcont"> {{notice.content}}</p>
                        </td>
                        <td class="forumrowhighlight">
                            {{moment(notice.endTime*1000).format('YYYY-MM-DD HH:mm:ss')}}<br />
                        </td>
                    </tr>
                    <tr v-if="notices.length==0">
                        <td colspan="4" class="forumrowhighlight nohover">
                            <a-empty />
                        </td>
                    </tr>
                </tbody>
            </table>
            <div class="p10" style="text-align: center;">
                <a-pagination @change="pageChange" @showSizeChange="sizeChange" size="small" :total="total" :current="page" :pageSize="size" show-size-changer show-quick-jumper :show-total="total => `共 ${parseInt(total/size)+1} 页`" />
            </div>
        </a-spin>

        <a-modal v-model="noticeModel"
                 title="系统公告"
                 centered
                 width="300px"
                 :footer="null"
                 @ok="noticeModel = false">
            <div class="win_info" v-for="item in noticeList">
                <ol>
                    <li>{{item.content}}</li>
                </ol>
            </div>
<!--            <a-carousel dotsClass="homenokv" effect="fade" autoplay>-->
<!--                <div v-for="item in noticeList">-->
<!--                    <p>{{item.content}}</p>-->
<!--                </div>-->
<!--            </a-carousel>-->

        </a-modal>
    </div>
</template>

<script>
import to from "await-to-js";
export default {
    data() {
        return {
            spinning: false,
            page: 1,
            size: 20,
            total: 0,
            notices: [],
            noticeModel: false,
            marquee: '暂无公告!',
            noticeList: [],
        };
    },
    mounted: function () {
        this.requestNotice();
    },
    methods: {
        async requestNotice() {
            this.spinning = true;
            let params = { page: this.page, size: this.size };
            let [err, data] = await to(this.$api.ctrl.getNoticeShow(params));
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
            this.showAlert();
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
        showAlert(){
            let checkAlert= sessionStorage.getItem("checkAlert");
            if(!checkAlert) {
                this.notices.forEach(not => {
                    if (not.isAlert) {
                        this.noticeModel = true;
                        this.noticeList.push(not);
                    }
                })
                if(this.noticeModel){
                    sessionStorage.setItem("checkAlert",true);
                }
            }
        }
    },
};
</script>
<style scoped>
</style>
