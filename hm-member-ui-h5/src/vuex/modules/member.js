import * as types from '@/vuex/types'
import io from 'socket.io-client'
export default {
    state: {
      member:sessionStorage.getItem("member")?JSON.parse(sessionStorage.getItem("member")):{},
      showMenu:false,
      token:null,
      resetPasswordStatus:null,
      siteName:'',
      balance:'',
      betWaiting:'',
      winLose:'',
      noticeArr:[],
    },
    getters: {
      member:state=>state.member,
      showMenu:state=>state.showMenu,
      token:state=>state.token,
      resetPasswordStatus:state=>state.resetPasswordStatus,
      siteName:state=>state.siteName,
      balance:state=>state.balance,
      betWaiting:state=>state.betWaiting,
      winLose:state=>state.winLose,
      noticeArr:state=>state.noticeArr
    },
    mutations: {
      [types.SUCCESS_LOGIN](state,payload){
        state.member = payload;
        sessionStorage.setItem("member",JSON.stringify(payload));
      },
      [types.LOGOUT](state,payload){
        if(state.member){
          sessionStorage.clear();
          state.member = '';
        }

        sessionStorage.clear();
      },
      [types.CHANGE_MENU](state,payload){
        state.showMenu = payload;
      },
      [types.SET_TOKEN](state,payload){
        state.token = null;
        state.token = payload;
        /*state.token=Object.assign({},payload);*/
      },
      [types.SET_BALANCES](state,payload){
        /*if(payload.reserveModel=='CASH'){
          state.member.balance = payload.useRecBalance;
        }else if(payload.reserveModel =='CREDIT'){
          state.member.balance = payload.useBalance;
        }
        state.member.reserveModel = payload.reserveModel;*/
        state.member.balance = payload.balance;
        state.member.credit = payload.credit;
        state.member.winLose = payload.winLose;
        state.member.betWaiting = payload.betWaiting;
        state.member.market = payload.market;
        sessionStorage.setItem("member",JSON.stringify(state.member));
        state.balance = payload.balance;
        state.winLose = payload.winLose;
        state.betWaiting = payload.betWaiting;
      },
      [types.SET_MEMBER_CLEAR](state,payload){
        console.log('清空了member');
        state.member=sessionStorage.getItem("member")?JSON.parse(sessionStorage.getItem("member")):{};
        state.showMenu=false;
        state.token=null;
        state.siteName = '';
      },
      [types.SET_RESET_PASSWORD_STATUS](state,payload){
        state.resetPasswordStatus = payload;
      },
      [types.SET_SITE_NAME](state,payload){
        state.siteName = payload;
      },
      [types.SET_NOTICE_ARR](state,payload){
        state.noticeArr = payload;
      }
    },
    actions: {
      loginSuccess({commit},payload){
        commit(types.SUCCESS_LOGIN,payload);
      },
      setLogout({commit},payload){
        commit(types.LOGOUT,payload);
      },
      changeMenu({commit},payload){
        commit(types.CHANGE_MENU,payload);
      },
      setToken({commit},payload){
        commit(types.SET_TOKEN,payload);
      },
      setBalances({commit},payload){
        commit(types.SET_BALANCES,payload);
      },
      setMemberClear({commit},payload){
        commit(types.SET_MEMBER_CLEAR,payload);
      },
      setResetPasswordStatus({commit},payload){
        commit(types.SET_RESET_PASSWORD_STATUS,payload);
      },
      setSiteName({commit},payload){
        commit(types.SET_SITE_NAME,payload);
      },
      setNoticeArr({commit},payload){
        commit(types.SET_NOTICE_ARR,payload);
      },
    }
}

