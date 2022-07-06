<template>
    <div>
        <a-spin :spinning="spinning">
            <div class="mb10 df">
                <span class="maintxt mlr10">选择彩种:</span>
                <a-select v-model="lotteryId" style="width: 120px" @change="changeLottery" size="small">
                    <a-select-option v-for="lottery in lotterys" :key="lottery.lotteryId">
                        {{ lottery.lotteryName }}
                    </a-select-option>
                </a-select>
                <span class="maintxt mlr10">玩法:</span>
                <a-select v-model="kindId" style="width: 120px" size="small">
                    <a-select-option v-for="kind in kinds" :key="kind.kindId">
                        {{ kind.kindName }}
                    </a-select-option>
                </a-select>
                <a-radio-group name="radioGroup" v-model="model" class="maintxt mlr10">
                    <a-radio :value="1">
                        长期开降赔
                    </a-radio>
                    <a-radio :value="2">
                        长期不开降赔
                    </a-radio>
                </a-radio-group>

                <div class="pl10">
                    <a-button type="primary" icon="search" size="small" @click="requestCljp">
                        查询
                    </a-button>
                </div>
                <div class="pl10">
                    <a-button type="primary" icon="plus" size="small" @click="showdwon">
                        新增记录
                    </a-button>
                </div>
                <div class="pl10 red">连开期数大于设置的最大期数将会清空</div>
            </div>
            <table class="tableborder" border="0" align="center" cellpadding="5" cellspacing="1" style="border-collapse: separate;width: 100%;float: left">
                <tbody>
                    <tr>
                        <th>长期开降赔</th>
                        <th>累计下调赔率</th>
                        <th>操作</th>
                    </tr>
                    <tr v-for="(cljp,idx) in cljps" :key="cljp.id">
                        <td class="forumrow">{{cljp.times}}</td>
                        <td class="forumrowhighlight">{{cljp.cljpValue}}</td>
                        <td class="forumrowhighlight">
                            <a-button type="danger" icon="delete" size="small" @click="delCljp(idx,cljp.id)">
                                删除
                            </a-button>
                        </td>
                    </tr>
                    <tr v-if="cljps.length==0">
                        <td colspan="3" class="forumrowhighlight nohover">
                            <a-empty />
                        </td>
                    </tr>
                </tbody>
            </table>

            <!--新增或修改-->
            <a-drawer title="新增或修改降赔" :width="250" :visible="isShowAddForm" :body-style="{ paddingBottom: '80px'}" @close="onClose">
                <table class="tableborder" border="0" align="center" cellpadding="5" cellspacing="1" style="border-collapse: separate;width: 100%">
                    <tr>
                        <th>长期开降赔</th>
                        <th>累计下调赔率</th>
                    </tr>
                    <tbody>
                        <tr v-for="(cljp,cljpIndex) in newClips" :key="'cljp'+cljpIndex">
                            <td class="forumrow">
                                <a-input-number v-model="cljp.times" @focus="focus($event)" :step="1" :min="0" :precision="0" size="small" />
                            </td>
                            <td class="forumrowhighlight">
                                <a-input-number v-model="cljp.cljpValue" @focus="focus($event)" :step="0.1" :min="0" size="small" />
                            </td>
                        </tr>
                    </tbody>
                </table>
                <div class="opnewinright">
                    <a-button :style="{ marginRight: '12px' }" size="small" @click="onClose">
                        取消
                    </a-button>
                    <a-button type="primary" size="small" @click="updateCljp">
                        确定
                    </a-button>
                </div>
            </a-drawer>
        </a-spin>
    </div>
</template>

<script>
import to from "await-to-js";
export default {
    name: "cljp",
    data() {
        return {
            spinning: false,
            lotteryId: null,
            kindId: null,
            model: 1,
            lottery: {},
            kinds: [],
            lotterys: [],
            mapKinds: {},
            cljps: [],
            isShowAddForm: false,
            newClips: [
                { times: null, cljpValue: null },
                { times: null, cljpValue: null },
                { times: null, cljpValue: null },
                { times: null, cljpValue: null },
                { times: null, cljpValue: null },
                { times: null, cljpValue: null },
                { times: null, cljpValue: null },
                { times: null, cljpValue: null },
                { times: null, cljpValue: null },
                { times: null, cljpValue: null },
                { times: null, cljpValue: null },
                { times: null, cljpValue: null },
                { times: null, cljpValue: null },
                { times: null, cljpValue: null },
                { times: null, cljpValue: null },
                { times: null, cljpValue: null },
                { times: null, cljpValue: null },
                { times: null, cljpValue: null },
            ],
        };
    },
    mounted() {
        this.requestCljpInit();
    },
    methods: {
        showdwon() {
            this.isShowAddForm = true;
        },
        onClose() {
            this.isShowAddForm = false;
        },
        changeLottery(lotteryId) {
            let lottery = this.lotterys.find(
                (lottery) => lottery.lotteryId == lotteryId
            );
            this.lottery = lottery;
            this.lotteryId = lotteryId;

            let kinds = this.mapKinds[lottery.groupId];
            this.kinds = kinds;
            this.kindid = kinds[0].kindId;
        },
        async requestCljpInit() {
            this.spinning = true;
            let [err, res] = await to(this.$api.ctrl.getClipInit());
            if (err || !res.success) {
                this.spinning = false;
                return;
            }
            let {
                lotterys,
                kinds: mapKinds,
                kindId,
                lotteryId,
                userKinds: mapUserKinds,
            } = res.data;

            //console.log(mapUserKinds);
            this.kindId = kindId;
            this.lotteryId = lotteryId;
            this.lotterys = lotterys;
            this.mapKinds = mapKinds;
            this.cljps = mapUserKinds;
            this.lottery = this.lotterys.find(
                (lottery) => lottery.lotteryId == lotteryId
            );
            this.kinds = this.mapKinds[this.lottery.groupId];
            this.spinning = false;
        },
        async requestCljp() {
            this.spinning = true;
            let params = {
                lotteryId: this.lotteryId,
                kindId: this.kindId,
                model: this.model,
            };
            let [err, res] = await to(this.$api.ctrl.getCljp(params));
            this.spinning = false;
            //this.$utils.handleThen(res, this);
            if (err || !res.success) {
                return;
            }
            this.cljps = res.data;
            this.spinning = false;
        },
        async updateCljp() {
            let params = {
                lotteryId: this.lotteryId,
                kindId: this.kindId,
                model: this.model,
                cljps: [],
            };
            this.newClips.forEach((cljp) => {
                let { times, cljpValue } = cljp;
                if (times != null && cljpValue != null) {
                    params.cljps.push({ times, cljpValue });
                }
            });
            this.spinning = true;
            let [err, res] = await to(this.$api.ctrl.updateCljp(params));
            this.spinning = false;
            this.$utils.handleThen(res, this);
            if (err || !res.success) {
                return;
            }
            this.requestCljp();
        },
        async delCljp(idx, cljpId) {
            this.spinning = true;
            let [err, res] = await to(this.$api.ctrl.delCljp({ cljpId }));
            this.spinning = false;
            this.$utils.handleThen(res, this);
            if (err || !res.success) {
                return;
            }
            this.cljps.splice(idx, 1);
            this.spinning = false;
        },
    },
};
</script>

<style scoped>
</style>