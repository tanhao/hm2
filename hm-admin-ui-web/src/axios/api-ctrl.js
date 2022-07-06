import HttpRequest from './index'
const axios = new HttpRequest()

export default {

    getBuhuo (params) {
        return axios.requestGet('/v2/ctrl/buhuo', params);
    },
    updateBuhuo (params) {
        return axios.requestPut('/v2/ctrl/buhuo', params);
    },
    getStep (params) {
        return axios.requestGet('/v2/ctrl/step', params);
    },
    updateStep (params) {
        return axios.requestPut('/v2/ctrl/step', params);
    },
    getJump (params) {
        return axios.requestGet('/v2/ctrl/jump', params);
    },
    updateJump (params) {
        return axios.requestPut('/v2/ctrl/jump', params);
    },
    updateJumpModel (params) {
        return axios.requestPut('/v2/ctrl/jump-model', params);
    },

    getNotice (params) {
        return axios.requestGet('/v2/ctrl/notice', params);
    },

    getNoticeShow (params) {
        return axios.requestGet('/v2/ctrl/notice-show', params);
    },

    saveNotice (params) {
        return axios.requestPost('/v2/ctrl/notice', params);
    },
    deleteNotice (params) {
        return axios.requestDelete('/v2/ctrl/notice', params);
    },
    updateNotice (params) {
        return axios.requestPut('/v2/ctrl/notice', params);
    },
    updateNoticeState (params) {
        return axios.requestPut('/v2/ctrl/notice/state', params);
    },
    getLottery (params) {
        return axios.requestGet('/v2/ctrl/lottery', params);
    },
    getLotteryCompany (params) {
        return axios.requestGet('/v2/ctrl/company/lottery', params);
    },
    updateLotteryIsLink (params) {
        return axios.requestPut('/v2/ctrl/lottery/link', params);
    },
    updateLotteryIsReset (params) {
        return axios.requestPut('/v2/ctrl/lottery/reset', params);
    },
    updateLotteryStatus (params) {
        return axios.requestPut('/v2/ctrl/lottery/status', params);
    },
    updateLotteryDelay (params) {
        return axios.requestPut('/v2/ctrl/lottery/delay', params);
    },
    updateLotteryDelayObo (params) {
        return axios.requestPut('/v2/ctrl/lottery/delay-obo', params);
    },
    updateLotteryOrdering (params) {
        return axios.requestPut('/v2/ctrl/lottery/ordering', params);
    },
    updateLotteryOpenModel (params) {
        return axios.requestPut('/v2/ctrl/lottery/open-model', params);
    },
    checkSafePwd (params) {
        return axios.requestGet('/v2/ctrl/check-safe-pwd', params);
    },

    getWarn (params) {
        return axios.requestGet('/v2/ctrl/warn', params);
    },
    getClipInit (params) {
        return axios.requestGet('/v2/ctrl/cljp-init', params);
    },
    getCljp (params) {
        return axios.requestGet('/v2/ctrl/cljp', params);
    },
    updateCljp (params) {
        return axios.requestPut('/v2/ctrl/cljp', params);
    },
    delCljp (params) {
        return axios.requestDelete('/v2/ctrl/cljp', params);
    },
    getOdds (params) {
        return axios.requestGet('/v2/ctrl/odds', params);
    },
    updateORLF (params) {
        return axios.requestPut('/v2/ctrl/orlf', params);
    },

    updateCompanyORLF (params) {
        return axios.requestPut('/v2/ctrl/compnay-orlf', params);
    },
    getAgent1ORL (params) {
        return axios.requestGet('/v2/ctrl/agent1-orl', params);
    },
    updateAgent1ORL (params) {
        return axios.requestPut('/v2/ctrl/agent1-orl', params);
    },
    udpateAgent1RegressLimit (params) {
        return axios.requestPut('/v2/ctrl/agent1-regress-limit', params);
    },

    copyCompnay (params) {
        return axios.requestPut('/v2/ctrl/copy-company', params);
    },
    copyAgent1 (params) {
        return axios.requestPut('/v2/ctrl/copy-agent1', params);
    },

    getNowOrder (params) {
        return axios.requestGet('/v2/ctrl/now', params);
    },
    getNowLottery (params) {
        return axios.requestGet('/v2/ctrl/now-lottery', params);
    },
    getNowStats (params) {
        return axios.requestGet('/v2/ctrl/now-stats', params);
    },
    getNowOdds (params) {
        return axios.requestGet('/v2/ctrl/now-odds', params);
    },
    getBuhuoAmt (params) {
        return axios.requestGet('/v2/ctrl/buhuo-amt', params);
    },
    saveBuhuoOrder (params) {
        return axios.requestPut('/v2/ctrl/buhuo-manual', params);
    },

    getZhuanOdds (params) {
        return axios.requestGet('/v2/ctrl/zhuan-odds', params);
    },
    updateZhuanOdds (params) {
        return axios.requestPut('/v2/ctrl/zhuan-odds', params);
    },
    getZhuanRegress (params) {
        return axios.requestGet('/v2/ctrl/zhuan-regress', params);
    },
    updateZhuanRegress (params) {
        return axios.requestPut('/v2/ctrl/zhuan-regress', params);
    },

    getMaxdiff (params) {
        return axios.requestGet('/v2/ctrl/maxdiff', params);
    },
    updateMaxdiff (params) {
        return axios.requestPut('/v2/ctrl/maxdiff', params);
    },

    plusMinusOdds (params) {
        return axios.requestPut('/v2/ctrl/plus-minus-odds', params);
    },
    openCloseOdds (params) {
        return axios.requestPut('/v2/ctrl/open-close-odds', params);
    },

    getLogInit (params) {
        return axios.requestGet('/v2/ctrl/log-init', params);
    },
    getGameNos (params) {
        return axios.requestGet('/v2/ctrl/game-nos', params);
    },
    getLogCtrl (params) {
        return axios.requestGet('/v2/ctrl/log-ctrl', params);
    },
    getLogLimit (params) {
        return axios.requestGet('/v2/ctrl/log-limit', params);
    },
    getLogZhuanOdds (params) {
        return axios.requestGet('/v2/ctrl/log-zhuang-odds', params);
    },
};
