import Vue from 'vue'
import Vuex from 'vuex'
import UserModule from './modules/user'
import NowModule from './modules/now'

Vue.use(Vuex)

export default new Vuex.Store({
    modules: {
        user: UserModule,
        now: NowModule
    },
})
