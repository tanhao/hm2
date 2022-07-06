<template>
    <div class="autofutllwith">
        <a-spin :spinning="spinning">
            <div style="min-width: 1290px">
                <div class="mb10 df">
                    <a-date-picker v-model="logForm.startTime" @openChange="handleStartOpenChange" format="YYYY-MM-DD HH:mm:ss" :allowClear="false" :show-time="{ defaultValue: moment('00:00:00', 'HH:mm:ss') }" size="small" />
                    <span class="maintxt mlr10">到</span>
                    <a-date-picker v-model="logForm.endTime" @openChange="handleEndOpenChange" format="YYYY-MM-DD HH:mm:ss" :allowClear="false" :show-time="{ defaultValue: moment('00:00:00', 'HH:mm:ss') }" size="small" />
                    <span class="maintxt mlr10">彩种:</span>
                    <a-select v-model="logForm.lotteryId" style="width: 110px" @change="changeLottery" size="small">
                        <a-select-option v-for="lottery in lotterys" :key="lottery.lotteryId">
                            {{ lottery.lotteryName }}
                        </a-select-option>
                    </a-select>
                    <span class="maintxt mlr10">类别:</span>
                    <a-select v-model="logForm.kindId" style="width: 100px" size="small">
                        <a-select-option :key="-1">全部</a-select-option>
                        <a-select-option v-for="kind in kinds" :key="kind.kindId">
                            {{ kind.kindName }}
                        </a-select-option>
                    </a-select>
                    <span class="maintxt mlr10">期号:</span>
                    <a-select v-model="logForm.gameNo" style="width: 120px" size="small">
                        <a-select-option :key="-1">全部</a-select-option>
                        <a-select-option v-for="gameNo in gameNos" :key="gameNo">
                            {{ gameNo }}
                        </a-select-option>
                    </a-select>
                    <span class="maintxt mlr10">分类:</span>
                    <a-select v-model="logForm.playId" style="width: 100px" size="small">
                        <a-select-option :key="-1">全部</a-select-option>
                        <a-select-option v-for="play in plays" :key="play.playId">
                            {{ play.playName }}
                        </a-select-option>
                    </a-select>
                    <span class="maintxt mlr10">用户ID:</span>
                    <a-input size="small" style="width: 80px" v-model.number="logForm.userId" />
                    <div class="pl10">
                        <a-button type="primary" icon="search" size="small" @click="requestLog" :loading="spinning">
                            查询
                        </a-button>
                    </div>
                </div>
                <table class="tableborder leftlist" border="0" align="center" cellpadding="5" cellspacing="1">
                    <tr>
                        <th>用户</th>
                        <th>类别</th>
                        <th>期数</th>
                        <th>明细</th>
                        <th>变更人</th>
                        <th>变更时間</th>
                        <th>IP</th>
                        <th>IP归属</th>
                    </tr>
                    <template v-for="(item,index) in logs">
                        <tr :key="index">
                            <td class="forumrowhighlight">{{item.username}} ({{item.userId}})</td>
                            <td class="forumrowhighlight">{{item.kindName}}{{item.categoryName==''?'':'-'+item.categoryName}}</td>
                            <td class="forumrowhighlight">{{item.gameNo}}</td>
                            <td class="forumrowhighlight" style="word-wrap:break-word;width:846px; ">
                                <div style="white-space: pre-line;margin-left: 8px; padding-right: 8px; text-align: left;">
                                    {{item.playName}}『 {{item.oddsName}} 』@ {{item.detail}}</div>
                            </td>
                            <td class="forumrowhighlight">{{item.type=="JUMP"||item.type=="DOWN"?"系统":item.updateBy}}</td>
                            <td class="forumrowhighlight">
                                {{moment(item.updateTime*1000).format('YYYY-MM-DD')}}<br />
                                {{moment(item.updateTime*1000).format('HH:mm:ss')}}
                            </td>
                            <td class="forumrowhighlight">{{item.updateIp}}</td>
                            <td class="forumrowhighlight">{{item.type=="JUMP"?"自动跳盘":item.type=="DOWN"?"长龙降赔":item.updateAddr}}</td>
                        </tr>
                    </template>
                    <tr v-if="logs.length==0">
                        <td colspan="9" class="forumrowhighlight nohover">
                            <a-empty />
                        </td>
                    </tr>
                </table>
                <div class="p10" style="text-align: center;">
                    <a-pagination @change="pageChange" @showSizeChange="sizeChange" size="small" :total="logForm.total" :current="logForm.page" :pageSize="logForm.size" show-size-changer show-quick-jumper :show-total="total => `共 ${parseInt(logForm.total/logForm.size)+1} 页`" />
                </div>
            </div>
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
            lottery: {},
            kinds: [],
            lotterys: [],
            mapKinds: {},
            gameNos: [],
            plays: [],
            mapPlays: {},
            logs: [],
            logForm: {
                startTime: moment().add(-1, "days"),
                endTime: moment(),
                lotteryId: null,
                kindId: null,
                playId: -1,
                gameNo: -1,
                userId: null,
                total: 0,
                page: 1,
                size: 20,
            },
        };
    },
    mounted() {
        this.logInit();
    },
    methods: {
        async sizeChange(current, size) {
            this.logForm.page = current;
            this.logForm.size = size;
            this.requestLog();
        },
        async pageChange(page, size) {
            this.logForm.page = page;
            this.logForm.size = size;
            this.requestLog();
        },
        async logInit() {
            this.spinning = true;
            let [err, res] = await to(this.$api.ctrl.getLogInit());
            if (err || !res.success) {
                this.spinning = false;
                return;
            }
            let {
                lotterys,
                kinds: mapKinds,
                kindId,
                lotteryId,
                plays: mapPlays,
                playId,
            } = res.data;
            this.logForm.kindId = kindId;
            this.logForm.lotteryId = lotteryId;
            //this.logForm.playId = playId;
            this.lotterys = lotterys;
            this.mapKinds = mapKinds;
            this.mapPlays = mapPlays;
            this.lottery = this.lotterys.find(
                (lottery) => lottery.lotteryId == lotteryId
            );
            this.kinds = this.mapKinds[this.lottery.groupId];
            this.plays = this.mapPlays[this.lottery.lotteryId];
            await this.requestGameNos();
            this.spinning = false;
        },
        changeLottery(lotteryId) {
            let lottery = this.lotterys.find(
                (lottery) => lottery.lotteryId == lotteryId
            );
            this.lottery = lottery;
            this.logForm.lotteryId = lotteryId;

            let kinds = this.mapKinds[lottery.groupId];
            this.kinds = kinds;

            this.logForm.kindid = kinds[0].kindId;
            let plays = this.mapPlays[this.lottery.lotteryId];
            this.plays = plays;
            //this.logForm.playId = plays[0].playId;
        },
        async requestGameNos() {
            this.spinning = false;
            let { lotteryId, kindId, startTime, endTime } = this.logForm;
            let st = parseInt(startTime.valueOf() / 1000);
            let et = parseInt(endTime.valueOf() / 1000);
            let params = {
                lotteryId,
                startTime: st,
                endTime: et,
            };
            let [err, data] = await to(this.$api.ctrl.getGameNos(params));
            if (err || !data.success) {
                this.spinning = false;
                return;
            }
            this.gameNos = data.data;
            this.spinning = false;
        },
        handleStartOpenChange(open) {
            if (open) {
                this.logForm.startTime = moment();
            }
        },
        handleEndOpenChange(open) {
            if (open) {
                this.logForm.endTime = this.logForm.startTime;
            }
        },
        async requestLog() {
            this.loading = true;
            let {
                lotteryId,
                kindId,
                startTime,
                endTime,
                page,
                size,
                playId,
                gameNo,
                userId,
            } = this.logForm;
            let st = parseInt(startTime.valueOf() / 1000);
            let et = parseInt(endTime.valueOf() / 1000);
            let params = {
                lotteryId,
                kindId: kindId == -1 ? null : kindId,
                playId: playId == -1 ? null : playId,
                gameNo: gameNo == "-1" ? null : gameNo,
                startTime: st,
                endTime: et,
                userId: this.userId,
                page,
                userId,
            };
            let [err, data] = await to(this.$api.ctrl.getLogCtrl(params));
            if (err || !data.success) {
                this.$utils.handleThen(res, this);
                this.spinning = false;
                return;
            }
            let { logs, total, page: pageIndex, size: pageSize } = data.data;
            this.logs = logs;
            this.logForm.total = total;
            this.logForm.page = pageIndex;
            this.logForm.size = pageSize;
            this.spinning = false;
        },
    },
};
</script>

<style scoped>
</style>