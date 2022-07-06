

import HttpRequest from './index'
const axios = new HttpRequest()

export default {
  getLotList(params) {
    return axios.requestGet('/api/lots', params);
  },
  getTheResList(params) {
    return axios.requestGet('/api/the/rst', params);
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
    return axios.requestGet('/api/next/' + params, '');
  },
  getLotteryRoad(params) {
    return axios.requestGet('/api/road/'+params,'');
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
    return axios.requestGet('/api/road/', params);
  },


  /**历史页面**/
  getHisList(params) {
      return axios.requestGet('/api/his/'+params);
  },
  getHisByDayList(params) {
      return axios.requestGet('/api/his/'+params);
  },
  getHisBySumList(params) {
      return axios.requestGet('/api/his/sum/'+params);
  },

  /** 计划 **/
  getLotteryPlan() {
      return axios.requestGet('/api/rplan');
  },
  getPlanByList(params) {
      return axios.requestGet('/api/plan/list/'+params);
  },
  getPlanByNew(params) {
      return axios.requestGet('/api/plan/new/'+params);
  },
}
