// 全局样式
import './assets/css/main.css';

import Vue from 'vue'
import App from './App.vue'
import i18n from'@/i18n/i18n.js'
import router from './router'
import Lottery from '@/axios/api-game.js';
import moment from 'moment';
// 引入插件
import VueSweetalert2 from 'vue-sweetalert2';
import Utils from '@/components/comm/Utils.js'
import store from '@/vuex/store.js'
Vue.use(VueSweetalert2);
Vue.prototype.$moment = moment;

Vue.prototype.$Utils = Utils;

Vue.config.productionTip = false
Vue.prototype.$api = {Lottery};

Vue.prototype.computeGYH15LH = function (res) {
    let gyh = parseInt(res[0]) + parseInt(res[1]);
    let item = {
        zh:gyh,
        dx:gyh > 11 ? 'OVER' : 'UNDER',
        ds:(gyh % 2 === 1) ? 'ODD' : 'EVEN',
        lh1:parseInt(res[0]) > parseInt(res[9]) ? 'DRAGON' : 'TIGER',
        lh2:parseInt(res[1]) > parseInt(res[8]) ? 'DRAGON' : 'TIGER',
        lh3:parseInt(res[2]) > parseInt(res[7]) ? 'DRAGON' : 'TIGER',
        lh4:parseInt(res[3]) > parseInt(res[6]) ? 'DRAGON' : 'TIGER',
        lh5:parseInt(res[4]) > parseInt(res[5]) ? 'DRAGON' : 'TIGER'
    };
    return item;
};

Vue.prototype.computeSSCZH15LH = function (res) {
    let gyh = parseInt(res[0]) + parseInt(res[1]) + parseInt(res[2]) + parseInt(res[3])+ parseInt(res[4]);

    let n1 = [parseInt(res[0]), parseInt(res[1]), parseInt(res[2])];
    let n2 = [parseInt(res[1]), parseInt(res[2]), parseInt(res[3])];
    let n3 = [parseInt(res[2]), parseInt(res[3]), parseInt(res[4])];

    let item = {
        zh:gyh,
        ds:(gyh % 2 === 1) ? 'ODD' : 'EVEN',
        dx:gyh > 22 ? 'OVER' : 'UNDER',
        lh1:parseInt(res[0]) > parseInt(res[4]) ? 'DRAGON' : parseInt(res[0]) == parseInt(res[4]) ? 'DRAW' : 'TIGER',
        no1ou:res[0] > 4 ? 'OVER' : 'UNDER',
        no2ou:res[1] > 4 ? 'OVER' : 'UNDER',
        no3ou:res[2] > 4 ? 'OVER' : 'UNDER',
        no4ou:res[3] > 4 ? 'OVER' : 'UNDER',
        no5ou:res[4] > 4 ? 'OVER' : 'UNDER',
        no1oe:(res[0] % 2 === 1) ? 'ODD' : 'EVEN',
        no2oe:(res[1] % 2 === 1) ? 'ODD' : 'EVEN',
        no3oe:(res[2] % 2 === 1) ? 'ODD' : 'EVEN',
        no4oe:(res[3] % 2 === 1) ? 'ODD' : 'EVEN',
        no5oe:(res[4] % 2 === 1) ? 'ODD' : 'EVEN',
        c3:this.computeSSCBaozi(n1),
        z3:this.computeSSCBaozi(n2),
        h3:this.computeSSCBaozi(n3),
    };
    return item;
};
//顺子计算
Vue.prototype.computeSSCBaozi = function (nums) {
    nums = nums.sort(function(a,b){return a>b?1:-1});
    let res = '';
    if(nums[0] == nums[1] && nums[1] == nums[2]){
        res = 'LEOPARD';
    }else if(nums[0] == nums[1] || nums[1] == nums[2]){
        res = 'PAIR';
    }else if(nums[0]-nums[1]==-1 && nums[1]-nums[2]==-1){
        res = 'STRAIGHT';
    }else if(nums[0]-nums[1]==-1 || nums[1]-nums[2]==-1){
        res = 'HALF_CIS';
    }else{
        res = 'OTHER_SIX';
    }
    return res
}

Vue.prototype.computeKLSFZHLH = function (res) {
    let gyh = 0;
    for (let i = 0; i < res.length ; i++) {
        gyh += parseInt(res[i]);
    }
    let heOU = '';
    if (gyh >= 85 && gyh <= 132) {
        heOU = 'OVER';
    } else if (gyh >= 36 && gyh <= 83) {
        heOU = 'EVEN';
    } else if (gyh == 84) {
        heOU = 'draw';
    }
    let item = {
        zh:gyh,
        heOU:heOU,
        heOE:(gyh % 2 === 1) ? 'ODD' : 'EVEN',
        sumOU:parseInt(gyh.toString().substring(gyh.toString().length - 1)) >= 5 ? 'weiover' : 'weiunder',
        no1DT:parseInt(res[0]) > parseInt(res[7]) ? 'DRAGON' : 'TIGER',
        no2DT:parseInt(res[1]) > parseInt(res[6]) ? 'DRAGON' : 'TIGER',
        no3DT:parseInt(res[2]) > parseInt(res[5]) ? 'DRAGON' : 'TIGER',
        no4DT:parseInt(res[3]) > parseInt(res[4]) ? 'DRAGON' : 'TIGER'
    };
    return item;
};

new Vue({
    router,
    i18n,
    store,
    render: h => h(App)
}).$mount("#app")
