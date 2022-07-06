// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import 'babel-polyfill'
import Vue from 'vue'
import App from './App'
import router from './router'
import store from '@/vuex/store'
import '@/components/sg/assets/css/sg.css'
import '@/components/idc/assets/css/idc.css'
import Mint from 'mint-ui';
import 'mint-ui/lib/style.css';
import i18n from'@/i18n/i18n.js'
import game from '@/axios/api-game.js'
import mem from '@/axios/api-mem.js'
import bet from '@/axios/api-bet.js'
import vueTouch from 'kim-vue-touch'
Vue.prototype.$api = {game, mem, bet};

Vue.config.productionTip = false
Vue.use(Mint);
Vue.use(vueTouch);

import { MessageBox } from 'mint-ui' // 弹出式提示框(错误提示)
import { Toast } from 'mint-ui';
Object.defineProperty(Vue.prototype, '$messageBox', { value: MessageBox})
Object.defineProperty(Vue.prototype, '$toast', { value: Toast })

import Utils from '@/components/comm/Utils'


Vue.prototype.Utils=Utils;
window.socketTime = null;
let mainVue = new Vue({
  el: '#app',
  router,
  i18n,
  store,
  components: { App },
  template: '<App/>'
})

function handleVisibilityChange() {
  if (document[hidden]) {
    if(((router.currentRoute.path=='/sg/bjpk10' || router.currentRoute.path=='/idc/bjpk10') || (router.currentRoute.path=='/sg/xyft' || router.currentRoute.path=='/idc/xyft')
      ||(router.currentRoute.path=='/sg/speed10' || router.currentRoute.path=='/idc/speed10') || (router.currentRoute.path=='/sg/lucky10' || router.currentRoute.path=='/idc/lucky10')
      ||(router.currentRoute.path=='/sg/hmpk10' || router.currentRoute.path=='/idc/hmpk10') ||(router.currentRoute.path=='/sg/xjssc' || router.currentRoute.path=='/idc/xjssc')
      ||(router.currentRoute.path=='/sg/hmcqssc' || router.currentRoute.path=='/idc/hmcqssc')
      ||  (router.currentRoute.path=='/sg/sbspeed10' || router.currentRoute.path=='/idc/sbspeed10') || (router.currentRoute.path=='/sg/cqssc' || router.currentRoute.path=='/idc/cqssc')
      || (router.currentRoute.path=='/sg/jsssc' || router.currentRoute.path=='/idc/jsssc') || (router.currentRoute.path=='/sg/lucky5' || router.currentRoute.path=='/idc/lucky5')
      || (router.currentRoute.path=='/sg/gdkl10' || router.currentRoute.path=='/idc/gdkl10') || (router.currentRoute.path=='/sg/speed8' || router.currentRoute.path=='/idc/speed8')
      || (router.currentRoute.path=='/sg/xync' || router.currentRoute.path=='/idc/xync') || (router.currentRoute.path=='/sg/lucky8' || router.currentRoute.path=='/idc/lucky8')
      || (router.currentRoute.path=='/sg/jnd28' || router.currentRoute.path=='/idc/jnd28')|| (router.currentRoute.path=='/sg/pcdd' || router.currentRoute.path=='/idc/pcdd')
      || (router.currentRoute.path=='/sg/jsdd' || router.currentRoute.path=='/idc/jsdd'))){
      refreshTime = true;
      /*changeTimer = setInterval(()=>{
        refreshTime++;
      },1000);*/
    }
  } else {
    if(store.getters.gameId && refreshTime) {
      /*clearInterval(changeTimer);
      changeTimer = null;*/
      refreshTime = null;
      let gameId = store.getters.gameId;
      if (gameId) {
        game.getLotteryList({}).then(resGame => {
          game.getLotteryNow(gameId).then(resLottery => {
            if (resGame.data) {
              store.dispatch('setGames', resGame.data);
              store.dispatch('selectGame', gameId);
              if (resLottery.code === 10000) {
                store.dispatch('setLotteryNow', resLottery);
              } else {
                router.push('/');
              }
            } else {
              router.push('/');
            }

          }).catch(reason => {
            console.log(reason);
            router.push('/');
          });
        }).catch(reason => {
          console.log(reason);
          router.push('/');
        });

      }
    }
  }
}
let hidden, visibilityChange,changeTimer,refreshTime;
if (typeof document.hidden !== "undefined") { // Opera 12.10 and Firefox 18 and later support
  hidden = "hidden";
  visibilityChange = "visibilitychange";
} else if (typeof document.msHidden !== "undefined") {
  hidden = "msHidden";
  visibilityChange = "msvisibilitychange";
} else if (typeof document.webkitHidden !== "undefined") {
  hidden = "webkitHidden";
  visibilityChange = "webkitvisibilitychange";
} else if (typeof document.mozHidden !== "undefined") {
  hidden = "mozHidden";
  visibilityChange = "mozvisibilitychange";
}
if(hidden){
  if (document.addEventListener) {
    document.addEventListener(visibilityChange, handleVisibilityChange, false);
  } else if (document.attachEvent) {
    document.attachEvent(visibilityChange, handleVisibilityChange, false);
  } else{
    alert("您的浏览器有部分功能不受支持，请升级到最新版本或更换为Chrome,UC,QQ浏览器");
  }
}else{
  alert("您的浏览器有部分功能支持，请升级到最新版本或更换为Chrome,UC,QQ浏览器");
}
