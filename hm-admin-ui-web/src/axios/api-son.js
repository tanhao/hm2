import HttpRequest from './index'
const axios = new HttpRequest()

export default {

    getSonList(data) {
        return axios.requestGet('/v2/ucenter/son/findList', data);
    },
    updateSon(data){
        return axios.requestPost('/v2/ucenter/son/update', data);
    },
    saveSonInfo(data){
        return axios.requestPost('/v2/ucenter/son/save', data);
    },
    updateSonStatus(data){
        return axios.requestPost('/v2/ucenter/son/status', data);
    },

};
