import * as types from '@/vuex/types'
import io from 'socket.io-client'
export default {
    state: {
      member:sessionStorage.getItem("member")?JSON.parse(sessionStorage.getItem("member")):{},
      showMenu:false,
      token:null,
      skinColor:'blue',
      siteName:'',
      balance:'',
      betWaiting:'',
      winLose:'',
      updatepasswordBlackUserName:'',
      resetPasswordStatus:null,
    },
    getters: {
      member:state=>state.member,
      showMenu:state=>state.showMenu,
      token:state=>state.token,
      skinColor:state=>state.skinColor,
      siteName:state=>state.siteName,
      balance:state=>state.balance,
      betWaiting:state=>state.betWaiting,
      winLose:state=>state.winLose,
      updatepasswordBlackUserName:state=>state.updatepasswordBlackUserName,
      resetPasswordStatus:state=>state.resetPasswordStatus,
    },
    mutations: {
      [types.SUCCESS_LOGIN](state,payload){
        state.member = payload;
        sessionStorage.setItem("member",JSON.stringify(payload));
      },
      [types.LOGOUT](state,payload){
        state.member = {};
        sessionStorage.clear();
      },
      [types.CHANGE_MENU](state,payload){
        state.showMenu = payload;
      },
      [types.SET_TOKEN](state,payload){
        state.token=payload;
      },
      [types.SET_BALANCES](state,payload){
        /*if(payload.reserveModel=='CASH'){
          state.member.balance = payload.useRecBalance;
        }else if(payload.reserveModel =='CREDIT'){
          state.member.balance = payload.useBalance;
        }
        state.member.reserveModel = payload.reserveModel;*/
        state.member.balance = payload.balance;
        state.member.winLose = payload.winLose;
        state.member.betWaiting = payload.betWaiting;
        state.member.credit = payload.credit;
        state.balance = payload.balance;
        state.winLose = payload.winLose;
        state.betWaiting = payload.betWaiting;
      },
      [types.SET_SKIN_COLOR](state,payload){
        state.skinColor = payload;
      },
      [types.SET_MEMBER_CLEAR](state,payload){
        console.log('清空了member');
        state.member=sessionStorage.getItem("member")?JSON.parse(sessionStorage.getItem("member")):{};
        state.showMenu=false;
        state.token=null;
        state.skinColor='blue';
        state.siteName = '';
      },
      [types.SET_SITE_NAME](state,payload){
        state.siteName = payload;
      },
      [types.SET_UPUN](state,payload){
        state.updatepasswordBlackUserName = payload;
      },
      [types.SET_RESET_PASSWORD_STATUS](state,payload){
        state.resetPasswordStatus = payload;
      },
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
      setSkinColor({commit},payload){
        commit(types.SET_SKIN_COLOR,payload);
      },
      setMemberClear({commit},payload){
        commit(types.SET_MEMBER_CLEAR,payload);
      },
      setSiteName({commit},payload){
        commit(types.SET_SITE_NAME,payload);
      },
      setUPUN({commit},payload){
        commit(types.SET_UPUN,payload);
      },
      setResetPasswordStatus({commit},payload){
        commit(types.SET_RESET_PASSWORD_STATUS,payload);
      },
    }
}

