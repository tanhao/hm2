// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import 'babel-polyfill'
import App from './App'
import router from './router'
import store from './vuex/store'
import '@/assets/css/style.css'
import i18n from'@/i18n/i18n.js'
import Mint from 'mint-ui';
import 'mint-ui/lib/style.css';
import axios from 'axios';
import Lottery from '@/axios/api-game.js';
import Bet from '@/axios/api-bet.js';
import Member from '@/axios/api-mem.js';
import { MessageBox } from 'mint-ui' // 弹出式提示框(错误提示)
import { Toast } from 'mint-ui';
Vue.use(Mint);
Object.defineProperty(Vue.prototype, '$messageBox', { value: MessageBox })
Object.defineProperty(Vue.prototype, '$toast', { value: Toast })
Vue.config.productionTip = false
Vue.prototype.$api = {Lottery,Bet,Member};
router.afterEach((to,from)=>{
});



router.beforeEach((to,from,next)=>{
  let member = JSON.parse(sessionStorage.getItem('member'));

  console.log('to',to,store.getters.updatepasswordBlackUserName);
  if(to.path!='/'){
    if(from.path=='/resetPassword/' && store.getters.updatepasswordBlackUserName){
      next();
    }else if(to.path=='/resetPassword/'){
      next();
    }else if(!member){
      store.dispatch('setPromptInformation','请登录!');
      window.location.href='/';
    }else if(to.path=='/home/'){
      next();
    }else{
      store.dispatch('setPagePosition',to.path);

      let gameId = store.getters.gameId;
      if(to.path=='/lottery/bjpk10/'){
        gameId = 101;
      }else if(to.path=='/lottery/xyft/'){
        gameId = 102;
      }else if(to.path=='/lottery/speed10/' ){
        gameId = 103;
      }else if(to.path=='/lottery/lucky10/' ){
        gameId = 104;
      }else if(to.path=='/lottery/sbspeed10/' ){
        gameId = 105;
      }else if(to.path=='/lottery/hmpk10/' ){
        gameId = 106;
      }else if(to.path=='/lottery/epssm/' ){
        gameId = 107;
      }else if(to.path=='/lottery/fhsc/' ){
        gameId = 108;
      }else if(to.path=='/lottery/tycsm/'){
        gameId = 109;
      }else if(to.path=='/lottery/xyft3/' ){
        gameId = 110;
      }else if(to.path=='/lottery/sgft/' ){
        gameId = 111;
      }else if(to.path=='/lottery/xyft1396/' ){
        gameId = 112;
      }else if(to.path=='/lottery/cqssc/'){
        gameId = 201;
      }else if(to.path=='/lottery/jsssc/'){
        gameId = 202;
      }else if(to.path=='/lottery/lucky5/'){
        gameId = 203;
      }else if(to.path=='/lottery/xjssc/'){
        gameId = 204;
      }else if(to.path=='/lottery/hmcqssc/'){
        gameId = 205;
      }else if(to.path=='/lottery/gdkl10/'){
        gameId = 301;
      }else if(to.path=='/lottery/speed8/'){
        gameId = 302;
      }else if(to.path=='/lottery/xync/'){
        gameId = 303;
      }else if(to.path=='/lottery/lucky8/'){
        gameId = 304;
      }else if(to.path=='/lottery/jnd28/'){
        gameId = 401;
      }else if(to.path=='/lottery/pcdd/'){
        gameId = 402;
      }else if(to.path=='/lottery/jsdd/'){
        gameId = 403;
      }
      if(to.path.indexOf('/lottery/')!=-1){
        console.log('routerBeforeEach',to,from);
        let gameInterval =setInterval(()=>{
          if(store.getters.socket && store.getters.socket.ws.readyState == 1){
            store.dispatch('selectGame',gameId);
            clearInterval(gameInterval);
          }
        },200)
      }
      next();
      /*store.dispatch('setPagePosition',to.path);
      if(gameId!=0){
        let gameInterval =setInterval(()=>{
          if(store.getters.socket){
            store.dispatch('selectGame',gameId);
            clearInterval(gameInterval);
          }
        },100)
        next();
      }else{
        if(store.modules){
          if(store.modules.games){
            store.dispatch('selectGame',store.modules.games.gameMenu[0].lotteryId);
          }
        }

        next();
      }
      console.log('idaa',gameId);*/

    }

  }else{
    next();
  }
});

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  i18n,
  store,
  components: { App },
  template: '<App/>'
})
function handleVisibilityChange() {
  if (document[hidden]) {
    /*refreshTime = 0;
    changeTimer = setInterval(()=>{
      refreshTime++;
    },1000);*/
    /*if(router.currentRoute.path=='/lottery/bjpk10/' || router.currentRoute.path=='/lottery/xyft/'
      || router.currentRoute.path=='/lottery/speed10/'|| router.currentRoute.path=='/lottery/lucky10/'
      || router.currentRoute.path=='/lottery/sbspeed10/' || router.currentRoute.path=='/lottery/cqssc/'
      || router.currentRoute.path=='/lottery/jsssc/' || router.currentRoute.path=='/lottery/lucky5/'
      || router.currentRoute.path=='/lottery/gdkl10/' || router.currentRoute.path=='/lottery/speed8/'
      || router.currentRoute.path=='/lottery/xync/' || router.currentRoute.path=='/lottery/lucky8/'){

    }*/
  } else {

    if(store.getters.gameId) {
      /*clearInterval(changeTimer);
      changeTimer = null;*/
      let gameId = store.getters.gameId;

      if (gameId) {
        Lottery.getLotteryList({}).then(resGame => {
          Lottery.getLotteryNow(gameId).then(resLottery => {
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

