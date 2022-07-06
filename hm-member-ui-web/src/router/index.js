import Vue from 'vue'
import Router from 'vue-router'
import helpPage from '@/components/help/help'
import login from '@/components/login'
import home from '@/components/home'
import bjpk10 from '@/components/pk10/pk10'
import ssc from '@/components/ssc/ssc'
import klsf from '@/components/klsf/klsf'
import pcdd from '@/components/pcdd/pcdd'
import betList from '@/components/toppage/bets'
import betssettlement from '@/components/toppage/betssettlement'
import twoweekreport from '@/components/toppage/twoweekreport'
import kjlist from '@/components/toppage/kjlist'
import updatePassword from '@/components/toppage/changePassword'
import userInfo from '@/components/toppage/userInfo'
import homeNotice from '@/components/homeNotice'
Vue.use(Router)
const originalPush = Router.prototype.push;
Router.prototype.push = function push(location) {
  return originalPush.call(this, location).catch(err => err)
}
export default new Router({
  routes: [
    {
      path: '/',
      component: login,
      meta: {allowBack: false}
    },
    {path:'/resetPassword/',component:updatePassword},
    {path:'/homeNotice/',component:homeNotice},
    {
      path: '/home/',
      name:'homePage',
      component: home,
      children:[
        {path:'/lottery/bjpk10/',name:'bjpk10',component:bjpk10},
        {path:'/lottery/xyft/',name:'xyft',component:bjpk10},
        {path:'/lottery/speed10/',name:'speed10',component:bjpk10},
        {path:'/lottery/lucky10/',name:'lucky10',component:bjpk10},
        {path:'/lottery/sbspeed10/',name:'sbspeed10',component:bjpk10},
        {path:'/lottery/hmpk10/',name:'hmpk10',component:bjpk10},
        {path:'/lottery/epssm/',name:'epssm',component:bjpk10},
        {path:'/lottery/fhsc/',name:'fhsc',component:bjpk10},
        {path:'/lottery/tycsm/',name:'tycsm',component:bjpk10},
        {path:'/lottery/xyft3/',name:'xyft3',component:bjpk10},
        {path:'/lottery/xyft1396/',name:'xyft1396',component:bjpk10},
        {path:'/lottery/sgft/',name:'sqft',component:bjpk10},
        {path:'/lottery/cqssc/',name:'cqssc',component:ssc},
        {path:'/lottery/jsssc/',name:'jsssc',component:ssc},
        {path:'/lottery/lucky5/',name:'lucky5',component:ssc},
        {path:'/lottery/xjssc/',name:'xjssc',component:ssc},
        {path:'/lottery/cjk5/',name:'cjk5',component:ssc},
        {path:'/lottery/fhssc/',name:'fhssc',component:ssc},
        {path:'/lottery/tycssc/',name:'tycssc',component:ssc},
        {path:'/lottery/hmcqssc/',name:'hmcqssc',component:ssc},
        {path:'/lottery/txffc/',name:'txffc',component:ssc},
        {path:'/lottery/txsfc/',name:'txsfc',component:ssc},
        {path:'/lottery/twssc/',name:'twssc',component:ssc},
        {path:'/lottery/gdkl10/',name:'gdkl10',component:klsf},
        {path:'/lottery/speed8/',name:'speed8',component:klsf},
        {path:'/lottery/xync/',name:'xync',component:klsf},
        {path:'/lottery/lucky8/',name:'lucky8',component:klsf},
        {path:'/lottery/pcdd/',name:'pcdd',component:pcdd},
        {path:'/lottery/jnd28/',name:'jnd28',component:pcdd},
        {path:'/lottery/jsdd/',name:'jsdd',component:pcdd},
        {path:'/betList/',name:'betList',component:betList},
        {path:'/betssettlement/',name:'betssettlement',component:betssettlement},
        {path:'/twoweekreport/',name:'twoweekreport',component:twoweekreport},
        {path:'/kjlist/',name:'kjlist',component:kjlist},
        {path:'/helpPage/',name:'helpPage',component:helpPage},
        {path:'/updatePassword/',name:'updatePassword',component:updatePassword},
        {path:'/userInfo/',name:'userInfo',component:userInfo},
      ]
    },

  ]
})
