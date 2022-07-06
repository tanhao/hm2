import Vue from 'vue'
import VueRouter from 'vue-router'
import routes from './routers'
import store from '@/vuex/store'
import {Indicator} from 'mint-ui'
Vue.use(VueRouter)
const originalPush = VueRouter.prototype.push
VueRouter.prototype.push = function push(location) {
    return originalPush.call(this, location).catch(err => err)
}

const router = new VueRouter({
    // mode: 'history',
    routes
})
router.beforeEach((to, from, next) => {
    let token = store.getters.member;
    console.log('routerBeforeEach',to,from);
    if(from.path=='/sg/password' || from.path=='/idc/password'){
      next();
    }else if(to.path=='/sg/password' || to.path=='/idc/password'){
      next();
    }else if (to.path !== "/login" && !token) {
      Indicator.close();
      window.location.href = '/';
      next({path:'/login'});
    }else if(from.path=='/sg/password' && store.getters.resetPasswordStatus){
      next({path:'/sg/password'});
    }else if(from.path=='/idc/password' && store.getters.resetPasswordStatus){
      next({path:'/idc/password'});
    }else if(to.path=='/' && from.path=='/'){
      if(token && token.uid){
        next({path:'/'+token.marketSelect.toLocaleLowerCase()+'/main'});
      }else{
        sessionStorage.clear();
        Indicator.close();
        next({path:'/login'});
      }
    }else{
      if(token &&((token.marketSelect == 'SG' && to.path.indexOf('idc')!=-1) || (token.marketSelect == 'IDC' && to.path.indexOf('sg')!=-1))){
        store.dispatch('setPromptInformation','无效的链接!');
        next({path:from.path});
        return;
      }
      let gameId = store.getters.gameId;
      if(to.path=='/sg/bjpk10' || to.path=='/idc/bjpk10'){
        gameId = 101;
      }else if(to.path=='/sg/xyft' || to.path=='/idc/xyft'){
        gameId = 102;
      }else if(to.path=='/sg/speed10' || to.path=='/idc/speed10'){
        gameId = 103;
      }else if(to.path=='/sg/lucky10' || to.path=='/idc/lucky10'){
        gameId = 104;
      }else if(to.path=='/sg/sbspeed10' || to.path=='/idc/sbspeed10'){
        gameId = 105;
      }else if(to.path=='/sg/hmpk10' || to.path=='/idc/hmpk10'){
        gameId = 106;
      }else if(to.path=='/sg/cqssc' || to.path=='/idc/cqssc'){
        gameId = 201;
      }else if(to.path=='/sg/jsssc' || to.path=='/idc/jsssc'){
        gameId = 202;
      }else if(to.path=='/sg/lucky5' || to.path=='/idc/lucky5'){
        gameId = 203;
      }else if(to.path=='/sg/xjssc' || to.path=='/idc/xjssc'){
        gameId = 204;
      }else if(to.path=='/sg/hmcqssc' || to.path=='/idc/hmcqssc'){
        gameId = 205;
      }else if(to.path=='/sg/gdkl10' || to.path=='/idc/gdkl10'){
        gameId = 301;
      }else if(to.path=='/sg/speed8' || to.path=='/idc/speed8'){
        gameId = 302;
      }else if(to.path=='/sg/xync' || to.path=='/idc/xync'){
        gameId = 303;
      }else if(to.path=='/sg/lucky8' || to.path=='/idc/lucky8'){
        gameId = 304;
      }else if(to.path=='/sg/jnd28' || to.path=='/idc/jnd28'){
        gameId = 401;
      }else if(to.path=='/sg/pcdd' || to.path=='/idc/pcdd'){
        gameId = 402;
      }else if(to.path=='/sg/jsdd' || to.path=='/idc/jsdd'){
        gameId = 403;
      }
      if(((to.path=='/sg/bjpk10' || to.path=='/idc/bjpk10') || (to.path=='/sg/xyft' || to.path=='/idc/xyft')
        || (to.path=='/sg/hmpk10' || to.path=='/idc/hmpk10') || (to.path=='/sg/xjssc' || to.path=='/idc/xjssc')
        || (to.path=='/sg/hmcqssc' || to.path=='/idc/hmcqssc')
        ||(to.path=='/sg/speed10' || to.path=='/idc/speed10') || (to.path=='/sg/lucky10' || to.path=='/idc/lucky10')
        ||  (to.path=='/sg/sbspeed10' || to.path=='/idc/sbspeed10') || (to.path=='/sg/cqssc' || to.path=='/idc/cqssc')
        || (to.path=='/sg/jsssc' || to.path=='/idc/jsssc') || (to.path=='/sg/lucky5' || to.path=='/idc/lucky5')
        || (to.path=='/sg/gdkl10' || to.path=='/idc/gdkl10') || (to.path=='/sg/speed8' || to.path=='/idc/speed8')
        || (to.path=='/sg/xync' || to.path=='/idc/xync') || (to.path=='/sg/lucky8' || to.path=='/idc/lucky8') ||
        (to.path=='/sg/jsdd' || to.path=='/idc/jsdd') || (to.path=='/sg/pcdd' || to.path=='/idc/pcdd') ||
        (to.path=='/sg/jnd28' || to.path=='/idc/jnd28')) && gameId){
        store.dispatch('selectGame',gameId);
        let gameInterval =setInterval(()=>{
          if(store.getters.socket && store.getters.socket.ws.readyState == 1){

            next();
            clearInterval(gameInterval);

          }
        },300)

      }else if(from.path!='/'){
        next();
      }

      if(to.path.indexOf('sg')==-1){
        if((from.path.indexOf('profitlos')==-1 && from.name) || (to.name=='profitlos' && from.path.indexOf('profitlos')==-1 && from.name)){
          store.dispatch('setPagePosition',{'path':from.path,'name':from.name,'query':from.query});
          //store.dispatch('setResetPasswordStatus',false);
        }
        next();
      }else{
        if((from.path.indexOf('history')==-1 && from.name) || (to.name=='history' && from.path.indexOf('history')==-1 && from.name)){
          store.dispatch('setPagePosition',{'path':from.path,'name':from.name,'query':from.query});
          //store.dispatch('setResetPasswordStatus',false);
        }
        next();
      }


        /**/

    }
})

export default router
