<template>
    <div>
        <a-spin :spinning="spinning">
            <a-modal :visible="logShow" @cancel="onClose" :footer="null" width="1024px" title="操盘日志" class="bblisttc">
                <div class="mb10 df">
                    <span class="maintxt mlr10">选择彩种:</span>
                    <a-select v-model="lotteryId" style="width: 120px" @change="changeLottery" size="small">
                        <a-select-option key="A">
                            A
                        </a-select-option>
                        <a-select-option key="B">
                            B
                        </a-select-option>
                    </a-select>
                    <span class="maintxt mlr10">玩法:</span>
                    <a-select v-model="kindId" style="width: 120px" size="small">
                        <a-select-option key="A">
                            A
                        </a-select-option>
                        <a-select-option key="B">
                            B
                        </a-select-option>
                    </a-select>
                    <div class="pl10">
                        <a-button type="primary" icon="search" size="small">
                            查询
                        </a-button>
                    </div>
                </div>
                <table class="tableborder fontwe" border="0" align="center" cellpadding="1" cellspacing="1" style="border-collapse: separate;width: 100%;">
                    <tbody>
                        <tr>
                            <th colspan="2">遗漏</th>
                        </tr>
                    </tbody>
                </table>
            </a-modal>
        </a-spin>
    </div>
</template>
<script>
export default {
    name: "log",
    props: {
        logShow: Boolean,
    },
    data() {
        return {
            spinning: false,
            lotteryId: null,
            kindId: null,
        };
    },
    computed: {},
    mounted() {},
    watch: {},
    methods: {
        changeLottery(lotteryId) {},
        onClose() {
            this.$emit("update:logShow", false);
        },
    },
};
</script>
<style>
</style>