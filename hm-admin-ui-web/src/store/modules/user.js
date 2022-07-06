import * as types from '../types'

export default {
    state: {
        info: sessionStorage.getItem("userInfo") ? JSON.parse(sessionStorage.getItem("userInfo")) : {},
        hasGetRouter: false,
        menuItems:[],
    },
    getters: {
        info: state => state.info,
    },
    mutations: {
        [types.SUCCESS_LOGIN](state, payload) {
            state.info = payload;
            sessionStorage.setItem("userInfo", JSON.stringify(payload));
            sessionStorage.setItem("token", payload.uid);
        },
        [types.LOGOUT](state) {
            state.info = {};
            sessionStorage.clear();
        },
        [types.SET_ROUTERS](state, menuItems) {
            state.menuItems = menuItems;
        },
        setHasGetRouter (state, status) {
            state.hasGetRouter = status
        },
    },
    actions: {
        setLoginSuccess({commit}, payload) {
            commit(types.SUCCESS_LOGIN, payload);
        },
        setLogout({commit}) {
            commit(types.LOGOUT);
        },
        setRouters({commit},payload) {
            commit(types.SET_ROUTERS, payload);
            commit('setHasGetRouter', true)
        }
    }
}