/**
 * Created by jw on 2017/12/15 0015.
 */
import * as types from '@/vuex/types'
export default {
  state: {
    noteLists:[],
    betGameNo:null,
    selectList:[],
    betToken:null,
    quickSumList:[],
    quickSumStatus:false,
    latesNoteList:[],
    betAmtPreSet: '',
    betAmtStatus: false,
    quickBetStatus:false,
  },
  getters: {
    /*noteLists:state=>{return state.noteLists?state.noteLists:new Array(0)},
    selectList:state=>{return state.selectList?state.selectList:new Array(0)},*/
    /*noteLists:state=>Object.assign([],state.selectList.filter(val=>val.betAmt)),*/
    noteLists:state=>state.noteLists,
    selectList:state=>state.selectList,
    betGameNo:state=>state.betGameNo,
    betToken:state=>state.betToken,
    quickSumList:state=>state.quickSumList,
    quickSumStatus:state=>state.quickSumStatus,
    latesNoteList:state=>state.latesNoteList,
    betAmtPreSet:state=>state.betAmtPreSet,
    betAmtStatus:state=>state.betAmtStatus,
    quickBetStatus:state=>state.quickBetStatus
  },
  mutations: {
    [types.SET_NOTE_LISTS](state,payload){
      /*let noteLists = [];
      for(let item of payload){
        let obj = Object.assign({},item);//浅拷贝;
        noteLists.push(obj);
      }
      state.noteLists = noteLists;*/
      if(payload){
        let obj = state.noteLists.findIndex(function(value,index,arr){
          if(value.oddsId === payload.oddsId){
            value.odds = payload.odds;
          }
          return value.oddsId === payload.oddsId;
        });
        if(payload.betContent){
          obj = -1;
        }
        if(-1===obj){
          state.noteLists.push(payload);
        }else{
          state.noteLists[obj].betAmt = payload.betAmt;
        }

      }else{
        state.noteLists=new Array(0);
      }
    },
    [types.SET_SELECT_LIST](state,payload){
      if(payload){
        state.selectList.push(payload);
      }else{
        //state.selectList=new Array(0);

        if(state.selectList){
          state.selectList.forEach(obj=>{
            obj.choose = false;
            obj.check=false;
            obj.betAmt = '';
          });

        }
        state.selectList=[];
      }
      state.noteLists = Object.assign([],state.selectList);
    },
    [types.SET_BET_GAME_NO](state,payload){
      state.betGameNo = payload;
    },
    [types.SET_SPECIAL_NOTE_LISTS](state,payload){
      if(payload){
        let obj = state.selectList.findIndex(function(value,index,arr){
          return value.playKey === payload.playKey && value.oddsKey === payload.oddsKey && value.betContent == payload.betContent;
        });

        if(-1===obj){
          state.selectList.push(payload);
        }else{
          state.selectList[obj].betAmt = payload.betAmt;
        }

      }else{
        state.selectList=new Array(0);
      }
    },
    [types.SET_BET_TOKEN](state,payload){
      if(payload){
        state.betToken = payload;
      }
    },
    [types.SET_SELECT_LIST_FAST](state,payload){
      /*payload.forEach(val=>{
        state.selectList.push(JSON.parse(JSON.stringify(val)));
      })*/
      /*state.selectList=Object.assign([],payload);*/
      state.selectList = payload;
      //state.noteLists = Object.assign([],state.selectList);
    },
    [types.SET_NOTE_LISTS_FAST](state,payload){
      //state.noteLists=state.noteLists.concat(JSON.parse(JSON.stringify(payload)),state.noteLists);
      state.noteLists = JSON.parse(JSON.stringify(payload));
    },
    [types.SET_QUICK_SUM_LIST](state,payload){
      state.quickSumList = payload.list;
      state.quickSumStatus = payload.status;
    },
    [types.SET_LATES_NOTELIST](state,payload){
      state.latesNoteList = payload;
    },
    [types.SET_BET_CLEAR](state,payload){
      console.log('清空了bet');
      state.noteLists=[];
      state.betGameNo=null;
      state.selectList=[];
      state.betToken=null;
      state.quickSumList=[];
      state.quickSumStatus=false;
      state.latesNoteList=[];
      state.quickBetStatus = false;
    },
    [types.SET_BET_AMT_PRESET](state,payload){
      state.betAmtPreSet = payload;
    },
    [types.SET_BET_AMT_STATUS](state,payload){
      state.betAmtStatus = payload;
    },
    [types.SET_QUICK_BET_STATUS](state,payload){
      state.quickBetStatus = payload;
    }
  },
  actions: {
    setNoteLists({commit},payload){
      commit(types.SET_NOTE_LISTS,payload);
    },
    setBetGameNo({commit},payload){
      commit(types.SET_BET_GAME_NO,payload);
    },
    setSelectList({commit},payload){
      commit(types.SET_SELECT_LIST,payload);
    },
    setSpecialNoteLists({commit},payload){
      commit(types.SET_SPECIAL_NOTE_LISTS,payload);
    },
    setBetToken({commit},payload){
      commit(types.SET_BET_TOKEN,payload);
    },
    setSelectListFast({commit},payload){
      commit(types.SET_SELECT_LIST_FAST,payload);
    },
    setNoteListsFast({commit},payload){
      commit(types.SET_NOTE_LISTS_FAST,payload);
    },
    setQuickSumList({commit},payload){
      commit(types.SET_QUICK_SUM_LIST,payload);
    },
    setLatesNoteList({commit},payload){
      commit(types.SET_LATES_NOTELIST,payload);
    },
    setBetClear({commit},payload){
      commit(types.SET_BET_CLEAR,payload);
    },
    setBetAmtPreSet({commit},payload){
      commit(types.SET_BET_AMT_PRESET,payload);
    },
    setBetAmtStatus({commit},payload){
      commit(types.SET_BET_AMT_STATUS,payload);
    },
    setQuickBetStatus({commit},payload){
      commit(types.SET_QUICK_BET_STATUS,payload);
    }
  }
}

