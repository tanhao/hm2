<template>
    <a-layout>
        <main-header></main-header>
        <a-layout-content>
            <div class="main">
                <div class="Content">
                    <router-view v-if="isRouterAlive" />
                </div>
            </div>
        </a-layout-content>
        <a-layout-footer style="text-align: center">
            <div class="FrameTabs" style="overflow: hidden;">
                <div class="txt">最新公告:</div>
                <div class="Notice">
                    <marquee class="marquee" direction="right,left" scrollamount=5>{{marquee}}</marquee>
                </div>
                <div class="allusrlist">
                    <ul>
                        <li v-if='loginLvl===1'>公司<span class="m5 red">{{company}}</span></li>
                        <li>代理<span class="m5 red">{{agent}}</span></li>
                        <li>会员<span class="m5 red">{{member}}</span></li>
                        <li>子帐<span class="m5 red">{{sub}}</span></li>
                    </ul>
                </div>
            </div>
        </a-layout-footer>

        <a-modal v-model="noticeModel" :title="noticeTitle" centered width="300px" :footer="null" @ok="noticeModel = false">
            <a-carousel dotsClass="homenokv" effect="fade" autoplay>
                <div v-for="item in noticeList">
                    <p>{{item.content}}</p>
                </div>
            </a-carousel>

        </a-modal>

    </a-layout>
</template>

<script>
import mainHeader from "./main-header";
import im from "@/tio/im.js";
import Vue from "vue";
import Bus from "@/Bus";

export default {
    name: "Main",
    components: { mainHeader },
    data() {
        return {
            agent: 0,
            member: 0,
            company: 0,
            sub: 0,
            isSub: null,
            loginLvl: null,
            refreshTime: 60,
            timer: null,
            marquee: "暂无公告!",
            noticeModel: false,
            noticeTitle: "",
            noticeList: [],
            isRouterAlive: true,
        };
    },
    computed: {},
    methods: {
        reloadRouter() {
            this.isRouterAlive = false;
            this.$nextTick(function () {
                this.isRouterAlive = true;
            });
        },
        onlineNum() {
            this.$api.online.getOnlineNum().then((res) => {
                if (res.success) {
                    this.agent = res.data.agent;
                    this.member = res.data.member;
                    this.company = res.data.company;
                    this.isSub = res.data.isSub;
                    this.loginLvl = res.data.loginLvl;
                    this.sub = res.data.sub;
                    this.refreshData();
                }
            });
        },
        refreshData() {
            clearInterval(this.timer);
            this.timer = setInterval(() => {
                this.refreshTime -= 1;
                if (this.refreshTime == 0) {
                    this.onlineNum();
                    this.loadnotice();
                    this.refreshTime = 60;
                }
            }, 1000);
        },
        handlerNotice(content) {
            this.marquee = "";
            this.noticeList = [];
            let noticeList = content;
            noticeList.forEach((notice) => {
                if (notice.isAlert) {
                    this.noticeList.push(notice);
                }
                this.marquee +=
                    notice.content +
                    `\xa0\xa0\xa0\xa0\xa0\xa0\xa0\xa0\xa0\xa0\xa0\xa0\xa0`;
            });
            if (this.noticeList.length > 0) {
                this.noticeModel = true;
                this.noticeTitle = "系统公告";
            }
            this.marquee = this.marquee ? this.marquee : "暂无公告!";
        },
        toConnect() {
            let tiows = im.initWs(
                this.$store.state.user.info.uid,
                this.handlerMsg()
            );
            Vue.prototype.tiows = tiows;
        },
        handlerMsg() {
            let self = this;
            return new (function () {
                this.onopen = function (event, ws) {
                    console.log("socket connet success...");
                    // 握手连接
                    Bus.$emit("onopen");
                };
                this.onmessage = function (event, ws) {
                    let data = JSON.parse(event.data);
                    //心跳数据不需要处理
                    if (data.code === "ping") return;
                    if (data.code === "notice") {
                        self.handlerNotice(data.content);
                    } else if (data.code === "odds") {
                        Bus.$emit("odds", data.content);
                    } else if (data.code === "odds_init") {
                        data.content.serverTime = data.serverTime;
                        Bus.$emit("odds_init", data.content);
                    } else if (data.code === "odds_status") {
                        Bus.$emit("odds_status", data.content);
                    } else if (data.code === "odds_now") {
                        Bus.$emit("odds_now", data.content);
                    } else if (data.code === "odds_jump") {
                        Bus.$emit("odds_jump", data.content);
                    } else if (data.code === "odds_cljp") {
                        Bus.$emit("odds_cljp", data.content);
                    } else if (data.code === "result") {
                        Bus.$emit("result", data.content);
                    }
                };
                this.onclose = function (e, ws) {
                    // error(e, ws)
                    console.log("================onclose");
                };
                this.onerror = function (e, ws) {
                    // error(e, ws)
                    console.log("================onerror");
                };
            })();
        },
        loadnotice() {
            let params = { page: 1, size: 3 };
            this.$api.ctrl.getNoticeShow(params).then((res) => {
                if (res.success) {
                    this.marquee = "";
                    res.data.notices.forEach((notice) => {
                        this.marquee +=
                            notice.content +
                            `\xa0\xa0\xa0\xa0\xa0\xa0\xa0\xa0\xa0\xa0\xa0\xa0\xa0`;
                    });
                    this.marquee = this.marquee ? this.marquee : "暂无公告!";
                }
            });
        },
    },
    watch: {},
    mounted() {
        this.loadnotice();
        this.onlineNum();
        this.toConnect();
        Bus.$on("to-connect", this.toConnect);
    },
    destroyed() {
        clearInterval(this.timer);
    },
    provide() {
        return {
            reloadRouter: this.reloadRouter,
        };
    },
};
</script>

