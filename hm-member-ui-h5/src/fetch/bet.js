import fetch from '../fetch/api.js'

export default{
  bet(params){//下注
    return fetch('/v2/mem/trans/task/bet',params,'post',30000);
  },
  getBetToken(){//获取下注Token，防止重复下注
    return fetch('/v1/mem/trans/token',null,'get',30000);
  },
  getNotes(params){//获取最新注单
    return fetch('/v1/bet/waiting',params,'get',30000);
  },
  clearBets(params){//快速撤单
    return fetch('/v1/bet/withdraw',params,'post',30000);
  },
  betList(params){//下注列表
    return fetch('/v2/order/mem/list',params,'post',30000);
  },
  reBetList(params){//重派列表
    return fetch('/v1/bet/list/re',params,'get',30000);
  },
  winLose(){
    return fetch('/v1/bet/winLose',null,'get',30000);
  },
  winLoseLottery(params){
    return fetch('/v1/bet/winLose/lottery',params,'get',30000);
  }
};

