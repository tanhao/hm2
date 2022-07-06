import HttpRequest from './index'
const axios = new HttpRequest()

export default {
	getOnline(type) {
		return axios.requestGet('/v2/ucenter/online/onlineAll/' + type);
	},
	deleteIdOnline (id) {
		return axios.requestDelete('/v2/ucenter/online/online/' + id);
	},
	getOnlineNum (params) {
		return axios.requestGet('/v2/ucenter/online/onlineNum', params);
	},
    outOnlineAll (type) {
        return axios.requestGet('/v2/ucenter/online/outOnlineAll/' + type);
    },















};
