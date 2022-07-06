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
    presetBetAmtList:[{num:5},{num:10},{num:20},{num:50},{num:100}],
    presetBetAmtStatus:false,
    presetBetAmt:'',
    amount:10
  },
  getters: {
    /*noteLists:state=>{return state.noteLists?state.noteLists:new Array(0)},
    selectList:state=>{return state.selectList?state.selectList:new Array(0)},*/
    noteLists:state=>Object.assign([],state.selectList),
    selectList:state=>state.selectList,
    betGameNo:state=>state.betGameNo,
    betToken:state=>state.betToken,
    presetBetAmt:state=>state.presetBetAmt,
    presetBetAmtList:state=>state.presetBetAmtList,
    presetBetAmtStatus:state=>state.presetBetAmtStatus,
    amount:state=>state.amount
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
        state.noteLists.forEach(obj=>{
          obj.choose = false;
          obj.check=false;
        });
        state.noteLists=new Array(0);
      }
    },
    [types.SET_SELECT_LIST](state,payload){
      if(payload){
        state.selectList.push(payload);
      }else{
        //state.selectList=new Array(0);
        state.selectList.forEach(obj=>{
          obj.choose = false;
          obj.check=false;
          obj.betAmt='';
        });
        state.selectList=[];
      }
      state.noteLists = Object.assign([],state.selectList);
    },
    [types.SET_BET_GAME_NO](state,payload){
      state.betGameNo = payload;
    },
    [types.SET_SPECIAL_NOTE_LISTS](state,payload){
      if(payload){
        let obj = state.noteLists.findIndex(function(value,index,arr){
          return value.playKey === payload.playKey && value.oddsKey === payload.oddsKey && value.betContent == payload.betContent;
        });
        if(-1===obj){
          state.noteLists.push(payload);
        }else{
          state.noteLists[obj].betAmt = payload.betAmt;
        }

      }else{
        state.noteLists=new Array(0);
      }
    },
    [types.SET_BET_TOKEN](state,payload){
      if(payload){
        state.betToken = payload;
      }
    },
    [types.SET_SELECT_LIST_FAST](state,payload){
      state.selectList = [];
      payload.forEach(val=>{
        state.selectList.push(Object.assign({},val));
      })
      state.noteLists = Object.assign([],state.selectList);

    },
    [types.SET_NOTE_LISTS_FAST](state,payload){
      state.noteLists=state.noteLists.concat(state.noteLists,JSON.parse(JSON.stringify(payload)));
    },
    [types.SET_PRESET_BET_AMT](state,payload){
      state.presetBetAmt = payload;
    },
    [types.SET_PRESET_BET_AMT_LIST](state,payload){
      state.presetBetAmtList = payload;
    },
    [types.SET_PRESET_BET_AMT_STATUS](state,payload){
      state.presetBetAmtStatus = payload;
    },
    [types.SET_BET_CLEAR](state,payload){
      console.log('清空了bet');
      state.noteLists = [];
      state.betGameNo=null;
      state.selectList=[];
      state.betToken=null;
      state.presetBetAmtList=[{num:5},{num:10},{num:20},{num:50},{num:100}];
      state.presetBetAmtStatus=false;
      state.presetBetAmt='';
    },
    [types.SET_AMOUNT](state,payload){
      state.amount = payload;
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
    setPresetBetAmt({commit},payload){
      commit(types.SET_PRESET_BET_AMT,payload);
    },
    setPresetBetAmtList({commit},payload){
      commit(types.SET_PRESET_BET_AMT_LIST,payload);
    },
    setPresetBetAmtStatus({commit},payload){
      commit(types.SET_PRESET_BET_AMT_STATUS,payload);
    },
    setBetClear({commit},payload){
      commit(types.SET_BET_CLEAR,payload);
    },
    setAmount({commit},payload){
      commit(types.SET_AMOUNT,payload);
    }
  }
}

