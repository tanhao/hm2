

import HttpRequest from './index'
const axios = new HttpRequest()

export default {
  login(params){
    return axios.requestPost('/v2/auth/login',params);
  },
  getNotice(params){
    return axios.requestGet('/v2/ctrl/notice-show?page=1&size=3',params);
  },
  logout(params){
    return axios.requestGet('/v2/ucenter/user/userlogout',params);
  },
  updatePassword(params){
    return axios.requestPost('/v2/ucenter/user/updatePwd',params);
  },
  updatePwdNotoken(params) {
    return axios.requestPost('/v2/ucenter/user/updatePwd/notoken',params);;
  },
  refresh(params){
    return axios.requestGet('/v1/member/refresh',params);
  },
  exit(){
    return axios.requestGet('/v1/member/exit',{});
  },
  userExt(){
    return axios.requestGet('/v1/ucenter/userloginext',{});
  },
  balanceInfo(){
    return axios.requestGet('/v2/ucenter/mem/balance',{},2000)
  },
  resetPassword(){
    return axios.requestGet('/v2/ucenter/user/reset/pwd',{})
  },
  getLogos(){
    return axios.requestGet('/v2/ucenter/website/sitename',{})
  }
}
