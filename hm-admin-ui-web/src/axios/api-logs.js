import HttpRequest from './index'
const axios = new HttpRequest();

export default {

    addLogs(createType, oldValue, newValue, userId){
        let record = {createType, oldValue, newValue, userId};
        //return axios.requestPost('/v1/logs/record/push', record);
        return axios.requestPost("/v2/logs/hmLogs/addLogs",record)
    },

    selHmLogsIdList(params){
        return axios.requestGet("/v2/logs/hmLogs/selHmLogsIdList",params);
    },
    selHmUserSonLogIdList(params){
        return axios.requestGet("/v2/logs/hmLogs/selHmUserSonLogIdList",params);
    },
    pushRecordLogList(data) {
        return axios.requestPost('/v2/logs/hmLogs/push/list', data);
    },
    getIpsList(data) {
        return axios.requestGet('/v2/logs/hmLogs/logs/ips/list', data);
    },

};
