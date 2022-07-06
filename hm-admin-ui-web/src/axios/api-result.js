import HttpRequest from './index'
const axios = new HttpRequest()

export default {

    getResultList(data) {
        return axios.requestGet('/v2/result/list', data);
    },
    getResultSearch(lotteryKey,gameNo) {
        return axios.requestGet(`/v2/result/search/${lotteryKey}/${gameNo}`);
    },
    saveManualResult(data){
        return axios.requestPost('/v2/result/manual/save', data);
    },
    getTotalList(day) {
        return axios.requestGet(`/v2/result/total/${day}`);
    },
    cancelResult(id) {
        return axios.requestPost(`/v2/result/cancel/${id}`);
    },
    rePayoutResult(data) {
        return axios.requestPost(`/v2/result/rePayout`,data);
    },
    getCountResult(day) {
        return axios.requestGet(`/v2/result/count/${day}`);
    },
    getNullResultList(id,day) {
        return axios.requestGet(`/v2/result/null/list/${id}/${day}`);
    },
    savePayoutResult(id){
        return axios.requestPost('/v2/result/manual/payout/'+id);
    },






    /****************************通知****************************/
    addNotice(params){/*添加*/
        return axios.requestPost("/v2/ucenter/notice/addNotice",params)
    },
    updateNotice(params){/*修改*/
        return axios.requestPost("/v2/ucenter/notice/updateNotice",params)
    },


    resultZkc (params) {
        return axios.requestPost('/v2/result/zkc', params,50000);
    },
};
