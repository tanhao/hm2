import fetch from '../fetch/api.js'

export default{
  getOddsList(params){
    return fetch('/v2/ctrl/now',params,'get',30000);
  },
  getLotteryList(params){
    return fetch('/v2/ctrl/lottery/',params,'get',30000);
  },
  getLotteryNow(params){
    return fetch('/v2/ctrl/lottery/'+params,'','get',30000);
  },
  getLotteryRoad(params){
    return fetch('/v1/mem/result/road',params,'get',30000);
  },
  getFiveRecentResultList(params){
    return fetch('/v1/mem/result/fiveRecentResult',params,'get',30000);
  },
  getReport(params){
    return fetch('/v1/report/mem/report/weeks',params,'get',30000);
  },
  getLotteryReport(params){
    return fetch('/v1/report/mem/report/game',params,'get',30000);
  },
  getLotteryLimit(params){
    return fetch('/v1/mem/lottery/limit',params,'get',30000);
  },
  lotteryResult(params){
    return fetch('/v2/result/list',params,'post',30000);
  },
  getRoad(params){
    return fetch('/v1/result/openAward',params,'get',30000);
  }
};
