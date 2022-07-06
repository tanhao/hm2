//全局css
import './assets/main.css';
import 'babel-polyfill'
import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import Antd from 'ant-design-vue'
import 'ant-design-vue/dist/antd.css';
import i18n from '@/locale'
// 引入插件
import VueSweetalert2 from 'vue-sweetalert2';

import 'sweetalert2/dist/sweetalert2.min.css';

import axios from 'axios'
import moment from "moment";

Vue.prototype.moment = moment;
Vue.prototype.axios = axios;

import user from './axios/api-user'
import menu from './axios/api-menu'
import ctrl from './axios/api-ctrl'
import son from './axios/api-son'
import result from './axios/api-result'
import online from './axios/api-online'
import order from './axios/api-order'
import logs from './axios/api-logs'

Vue.prototype.$api = {user, menu, ctrl, son,result,online,order,logs};

import utils from './libs/utils'

Vue.prototype.$utils = utils;

Vue.config.productionTip = false


Vue.use(Antd);

Vue.use(VueSweetalert2);


import filter from '@/libs/filter'

Object.keys(filter).forEach(k => Vue.filter(k, filter[k]));

Vue.prototype.checkNull = function (val, msg) {
    if (val == null || val === undefined || val === '' || val.length === 0) {
        throw new Error(msg);
    }
};
Vue.prototype.checkTrue = function (val, msg) {
    if (!val) {
        throw new Error(msg);
    }
};
Vue.prototype.checkCallBack = function (callBack) {
    try {
        callBack(this);
    } catch (e) {
        this.$swal({
                title: e.message,
                icon: 'error',
                confirmButtonText: '确定'
            }
        )
    }
};
Vue.prototype.todayStr = function () {
    return moment().add(-7,'hours').format('YYYY-MM-DD');
}
Vue.prototype.todayDate = function () {
    return moment().add(-7,'hours');
}
Vue.prototype.focus= function (event) {
    event.currentTarget.select();
}
Vue.prototype.dateFormat = function (val) {
    return moment(val).format("YYYY-MM-DD HH:mm:ss");
};

new Vue({
    router,
    i18n,
    store,
    render: h => h(App)
}).$mount('#app')
