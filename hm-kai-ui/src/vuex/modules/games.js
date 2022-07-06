import * as types from '@/vuex/types'
export default {
    state: {
        pageTabSelect:'instantDraw',
        lotteryKey:'bjpk10',
        lotteryList:[],
        hotLotteryList:[]
    }
    ,
    getters: {
        pageTabSelect:state=>state.pageTabSelect,
        lotteryKey:state=>state.lotteryKey,
        lotteryList:state=>state.lotteryList,
        hotLotteryList:state=>state.hotLotteryList
    },
    mutations: {
        [types.SET_PAGE_TAB_SELECT](state,payload){
            state.pageTabSelect = payload;
        },
        [types.SET_LOTTERY_KEY](state,payload){
            state.lotteryKey = payload;
        },
        [types.SET_LOTTERY_LIST](state,payload){
            state.lotteryList = payload;
        },
        [types.SET_HOT_LOTTERY_LIST](state,payload){
            state.hotLotteryList = payload;
        }
    },
    actions: {
        setPageTabSelect({commit},payload){
            commit(types.SET_PAGE_TAB_SELECT,payload);
        },
        setLotteryKey({commit},payload){
            commit(types.SET_LOTTERY_KEY,payload);
        },
        setLotteryList({commit},payload){
            commit(types.SET_LOTTERY_LIST,payload);
        },
        setHotLotteryList({commit},payload){
            commit(types.SET_HOT_LOTTERY_LIST,payload);
        }
    }
}

