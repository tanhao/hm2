import fetch from '@/fetch/api'

export default{
  login(params){
    return fetch('/v2/auth/login',params,'post',30000);
  },
  getNotice(params){
    return fetch('/v1/sysmgr/notice-top3',params,'get',30000);
  },
  logout(params){
    return fetch('/v2/ucenter/user/userlogout',params,'get',30000);
  },
  updatePassword(params){
    return fetch('/v1/ucenter/update/password',params,'put',30000);
  },
  refresh(params){
    return fetch('/v1/member/refresh',params,'get',30000);
  },
  exit(){
    return fetch('/v1/member/exit',{},'get',30000);
  },
  userExt(){
    return fetch('/v1/ucenter/userloginext',{},'get',30000);
  },
  balanceInfo(){
    return fetch('/v2/ucenter/mem/balance','','get',30000)
  },
  resetPassword(){
    return fetch('/v2/ucenter/user/reset/pwd','','get',300000)
  },
  getLogos(){
    return fetch('/v2/ucenter/website/sitename','','get',300000)
  }
};

