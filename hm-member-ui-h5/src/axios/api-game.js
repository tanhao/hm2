

import HttpRequest from './index'
const axios = new HttpRequest()

export default {
  getOddsList(params) {
    return axios.requestGet('/v2/ctrl/now-front/', params);
  },
  getLotteryList(params) {
    return axios.requestGet('/v2/ctrl/lottery', params);
  },
  getNowLottery(params){
    return axios.requestGet('/v2/ctrl/now-lottery',params);
  },
  getNowStats(params){
    return axios.requestGet('/v2/ctrl/now-stats-front',params);
  },
  getLotteryNow(params) {
    return axios.requestGet('/v2/ctrl/lottery/' + params, '');
  },
  getLotteryRoad(params) {
    return axios.requestGet('/v2/mem/road/getRoad/'+params,'');
  },
  getFiveRecentResultList(params) {
    return axios.requestGet('/v1/mem/result/fiveRecentResult', params);
  },
  getReport(params) {
    return axios.requestGet('/v2/order/mem/report/weeks', params);
  },
  getLotteryReport(params) {
    return axios.requestGet('/v2/order/mem/report/game', params);
  },
  getLotteryLimit(params) {
    return axios.requestGet('/v2/ctrl/lotteryLimit/'+params);
  },
  lotteryResult(params) {
    return axios.requestPost('/v2/result/mem/list', params,);
  },
  getRoad(params) {
    return axios.requestGet('/v1/result/openAward', params);
  }
}
