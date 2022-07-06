import Vue from 'vue'
import Vuex from 'vuex'
import GamesModule from '@/vuex/modules/games'
import createPersistedState from "vuex-persistedstate";
Vue.use(Vuex);

export default new Vuex.Store({
  modules:{
    games:GamesModule,
  },
  plugins: [createPersistedState({
    storage: window.sessionStorage
  })]
})

