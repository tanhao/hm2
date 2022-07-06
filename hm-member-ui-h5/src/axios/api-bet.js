import HttpRequest from './index'
import fetch from "../fetch/api";
const axios = new HttpRequest()

export default {
  bet(params) {
    return axios.requestPost('/v2/mem/trans/task/bet', params);
  },
  getBetToken(){//获取下注Token，防止重复下注
    return axios.requestGet('/v1/mem/trans/token',{});
  },
  getNotes(params){//获取最新注单
    return axios.requestGet('/v1/bet/waiting',params);
  },
  clearBets(params){//快速撤单
    return axios.requestPost('/v1/bet/withdraw',params);
  },
  betList(params){//下注列表
    return axios.requestPost('/v2/order/mem/list',params);
  },
  reBetList(params){//重派列表
    return axios.requestGet('/v1/bet/list/re',params);
  },
  winLose(){
    return axios.requestPost('/v1/bet/winLose',{});
  },
  winLoseLottery(params){
    return axios.requestPost('/v1/bet/winLose/lottery',params);
  }

};
