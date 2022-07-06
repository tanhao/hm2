import Vue from 'vue'
import Vuex from 'vuex'
import MemberModule from '@/vuex/modules/member'
import GamesModule from '@/vuex/modules/games'
import BetModule from '@/vuex/modules/bet'
import createPersistedState from "vuex-persistedstate";
Vue.use(Vuex);

export default new Vuex.Store({
  modules:{
    member:MemberModule,
    games:GamesModule,
    bet:BetModule
  },
  plugins: [createPersistedState({
    storage: window.sessionStorage
  })]
})

