//公用部分
import AppHome from '@/home'
import login from '@/components/login'
import registered from '@/components/registered'
import selectmode from '@/components/selectmode'
import network from '@/components/network'
//sg
import sgHome from '@/components/sg/home/home'
import sgMain from '@/components/sg/lottery/main'
import kjlist from '@/components/sg/layout/kjlist'
import weije from '@/components/sg/layout/weije'
import userinfo from '@/components/sg/layout/userinfo'
import password from '@/components/sg/layout/password'
import history from '@/components/sg/layout/history'
import samedayhistory from '@/components/sg/layout/samedayhistory'
import lotteryhistory from '@/components/sg/layout/lotteryhistory'
import yije from '@/components/sg/layout/yije'
import pk10 from '@/components/sg/lottery/pk10'
import ssc from '@/components/sg/lottery/ssc'
import pcdd from '@/components/sg/lottery/pcdd'
import newestnotes from '@/components/sg/layout/latestnotes'
import kl10 from '@/components/sg/lottery/gdkl10'
import rules from '@/components/rules'
import sgnotice from '@/components/sg/lottery/sgnotice'
//统计
import statisticspk10 from '@/components/sg/statistics/pk10'
import statisticsssc from '@/components/sg/statistics/ssc'
import statisticsgdkl10 from '@/components/sg/statistics/gdkl10'
import statisticspcdd from '@/components/sg/statistics/pcdd'
//idc
import idcHome from '@/components/idc/home/home'
import idcmain from '@/components/idc/lottery/main'
import idcstatisticspk10 from '@/components/idc/statistics/pk10'
import idcstatisticsssc from '@/components/idc/statistics/ssc'
import idcstatisticsgdkl10 from '@/components/idc/statistics/gdkl10'
import idcstatisticspcdd from '@/components/idc/statistics/pcdd'
import idcpk10 from '@/components/idc/lottery/pk10'
import idcinformation from '@/components/idc/layout/information'
import idckjlist from '@/components/idc/layout/kjlist'
import idcweijie from '@/components/idc/layout/weije'
import idcyijie from '@/components/idc/layout/yije'
import idcdetails from '@/components/idc/layout/details'
import idcpassword from '@/components/idc/layout/password'
import idcinquiry from '@/components/idc/layout/inquiry'
import idcprofitlos from '@/components/idc/layout/profitlos'
import idcrules from '@/components/idc/layout/rules'
import idccqssc from '@/components/idc/lottery/ssc'
import idckl10 from '@/components/idc/lottery/gdkl10'
import idcpcdd from '@/components/idc/lottery/pcdd'
import idcsamedayprofitlos from '@/components/idc/layout/sameDayProfitlos'
import idclotteryprofitlos from '@/components/idc/layout/lotteryProfitlos'
import idctodayprofitlos from '@/components/idc/layout/todayprofitlos'
import idcnotice from '@/components/idc/lottery/idcnotice'
export default [
  //公用
  {
    path: '/login',
    component: login,
    name:'login',
    meta: {allowBack: false}
  },
  {
    path: '/registered/',
    component: registered,
    name:'registered'
  },
  //无网络连接
  {
    path: '/network/',
    component: network,
    name:'network'
  },
  {
    path: '/selectmode/',
    component: selectmode,
    name:'selectmode'
  },


  {
    path: '/',
    name: 'home',
    component: AppHome,
    children: [
      {
        path: '/sg',
        name: 'sg-home',
        component: sgHome,
        children:[
          {
            path:'/sg/notice/',
            component:sgnotice,
            name:'sgnotice'
          },
          {
            path: '/sg/main',
            component: sgMain,
            name:'main'
          },
          {
            path: '/sg/bjpk10',
            component: pk10,
            name:'bjpk10'
          },
          {
            path: '/sg/xyft',
            component: pk10,
            name:'xyft'
          },
          {
            path: '/sg/xyft1396',
            component: pk10,
            name:'xyft1396'
          },
          {
            path:'/sg/lucky10',
            component:pk10,
            name:'lucky10'
          },
          {
            path:'/sg/speed10',
            component:pk10,
            name:'speed10'
          },
          {
            path:'/sg/sbspeed10',
            component:pk10,
            name:'sbspeed10'
          },
          {
            path:'/sg/hmpk10',
            component:pk10,
            name:'hmpk10'
          },
          {
            path:'/sg/epssm',
            component:pk10,
            name:'epssm'
          },
          {
            path:'/sg/fhsc',
            component:pk10,
            name:'fhsc'
          },
          {
            path:'/sg/tycsm',
            component:pk10,
            name:'tycsm'
          },
          {
            path:'/sg/xyft3',
            component:pk10,
            name:'xyft3'
          },
          {
            path:'/sg/sgft',
            component:pk10,
            name:'sgft'
          },
          {
            path: '/sg/cqssc',
            component: ssc,
            name:'cqssc'
          },
          {
            path: '/sg/twssc',
            component: ssc,
            name:'twssc'
          },
          {
            path:'/sg/lucky5',
            component:ssc,
            name:'lucky5'
          },
          {
            path:'/sg/jsssc',
            component:ssc,
            name:'jsssc'
          },
          {
            path:'/sg/xjssc',
            component:ssc,
            name:'xjssc'
          },
          {
            path:'/sg/cjk5',
            component:ssc,
            name:'cjk5'
          },
          {
            path:'/sg/fhssc',
            component:ssc,
            name:'fhssc'
          },
          {
            path:'/sg/tycssc',
            component:ssc,
            name:'tycssc'
          },
          {
            path:'/sg/hmcqssc',
            component:ssc,
            name:'hmcqssc'
          },
          {
            path:'/sg/txffc',
            component:ssc,
            name:'txffc'
          },
          {
            path:'/sg/txsfc',
            component:ssc,
            name:'txsfc'
          },
          {
            path:'/sg/gdkl10',
            component:kl10,
            name:'gdkl10'
          },
          {
            path:'/sg/speed8',
            component:kl10,
            name:'speed8'
          },
          {
            path:'/sg/xync',
            component:kl10,
            name:'xync'
          },
          {
            path:'/sg/lucky8',
            component:kl10,
            name:'lucky8'
          },
          {
            path:'/sg/jnd28',
            component:pcdd,
            name:'jnd28'
          },
          {
            path:'/sg/pcdd',
            component:pcdd,
            name:'pcdd'
          },
          {
            path:'/sg/jsdd',
            component:pcdd,
            name:'jsdd'
          },


          {
            path: '/sg/kjlist',
            component: kjlist,
            name:'kjlist'
          },
          {
            path: '/sg/userinfo',
            component: userinfo,
            name:'userinfo'
          },
          {
            path: '/sg/password',
            component: password,
            name:'password'
          },
          {
            path: '/sg/history',
            component: history,
            name:'history'
          },
          {
            path:'/sg/samedayhistory',
            component:samedayhistory,
            name:'samedayhistory'
          },
          {
            path:'/sg/lotteryhistory',
            component:lotteryhistory,
            name:'lotteryhistory'
          },
          {
            path: '/sg/weije',
            component: weije,
            name:'weijie'
          },
          {
            path: '/sg/yije',
            component: yije,
            name:'yijie'
          },
          {
            path:'/sg/latestnotes',
            component:newestnotes,
            name:'newestnotes'
          },
          {
            path:'/sg/rules',
            component:rules,
            name:'rules'
          },
          //统计
          {
            path: '/sg/statisticspk10',
            component: statisticspk10,
            name:'statisticspk10'
          },
          {
            path:'/sg/statisticsssc',
            component:statisticsssc,
            name:'statisticsssc'
          },
          {
            path:'/sg/statisticsgdkl10',
            component:statisticsgdkl10,
            name:'statisticsgdkl10'
          },
          {
            path:'/sg/statisticspcdd',
            component:statisticspcdd,
            name:'statisticspcdd'
          },
        ]
      },
      {
        path: '/idc',
        name: 'idc-home',
        component: idcHome,
        children:[
          //idc路由
          {
            path:'/idc/notice/',
            component:idcnotice,
            name:'idcnotice'
          },
          {
            path: '/idc/main',
            component: idcmain,
            name:'idcmain'
          },
          {
            path: '/idc/statisticspk10',
            component: idcstatisticspk10,
          },
          {
            path:'/idc/statisticsssc',
            component:idcstatisticsssc
          },
          {
            path:'/idc/statisticsgdkl10',
            component:idcstatisticsgdkl10
          },
          {
            path:'/idc/statisticspcdd',
            component:idcstatisticspcdd
          },
          {
            path: '/idc/bjpk10',
            component: idcpk10,
            name:'idcbjpk10'
          },
          {
            path: '/idc/sgft',
            component: idcpk10,
            name:'idcsgft'
          },
          {
            path:'/idc/epssm',
            component:idcpk10,
            name:'idcepssm'
          },
          {
            path:'/idc/fhsc',
            component:idcpk10,
            name:'idcfhsc'
          },
          {
            path:'/idc/tycsm',
            component:idcpk10,
            name:'idctycsm'
          },
          {
            path:'/idc/jnd28',
            component:idcpcdd,
            name:'idcjnd28'
          },
          {
            path:'/idc/pcdd',
            component:idcpcdd,
            name:'idcpcdd'
          },
          {
            path:'/idc/jsdd',
            component:idcpcdd,
            name:'idcjsdd'
          },
          {
            path: '/idc/information',
            component: idcinformation,
          },
          {
            path:'/idc/kjlist',
            component:idckjlist,
          },
          {
            path:'/idc/weijie',
            component:idcweijie,
            name:'idcweijie'
          },
          {
            path:'/idc/yijie',
            component:idcyijie,
            name:'idcyijie'
          },
          {
            path: '/idc/password',
            component: idcpassword,
          },
          //账务查询
          {
            path: '/idc/inquiry',
            component: idcinquiry,
          },
          //新结算报表
          {
            path: '/idc/profitlos',
            component: idcprofitlos,
            name:'profitlos'
          },
          {
            path:'/idc/samedayprofitlos',
            component:idcsamedayprofitlos,
            name:'samedayprofitlos'
          },
          {
            path:'/idc/lotteryprofitlos',
            component:idclotteryprofitlos,
            name:'lotteryprofitlos'
          },
          {
            path:'/idc/todayprofitlos',
            component:idctodayprofitlos,
            name:'todayprofitlos'
          },
          //规则
          {
            path: '/idc/rules',
            component: idcrules,
          },
          {
            path:'/idc/details',
            component:idcdetails,
            name:'idcdetails'
          },
          {
            path:'/idc/cqssc',
            component:idccqssc,
            name:'idccqssc'
          },
          {
            path:'/idc/lucky5',
            component:idccqssc,
            name:'idclucky5'
          },
          {
            path:'/idc/jsssc',
            component:idccqssc,
            name:'idcjsssc'
          },
          {
            path:'/idc/xjssc',
            component:idccqssc,
            name:'idcxjssc'
          },
          {
            path:'/idc/cjk5',
            component:idccqssc,
            name:'idccjk5'
          },
          {
            path:'/idc/fhssc',
            component:idccqssc,
            name:'idcfhssc'
          },
          {
            path:'/idc/tycssc',
            component:idccqssc,
            name:'idctycssc'
          },
          {
            path:'/idc/hmcqssc',
            component:idccqssc,
            name:'idchmcqssc'
          },
          {
            path:'/idc/txffc',
            component:idccqssc,
            name:'idctxffc'
          },
          {
            path:'/idc/txsfc',
            component:idccqssc,
            name:'idctxsfc'
          },
          {
            path:'/idc/twssc',
            component:idccqssc,
            name:'idctwssc'
          },
          {
            path:'/idc/xyft',
            component:idcpk10,
            name:'idcxyft'
          },
          {
            path:'/idc/lucky10',
            component:idcpk10,
            name:'idclucky10'
          },
          {
            path:'/idc/speed10',
            component:idcpk10,
            name:'idcspeed10'
          },
          {
            path:'/idc/sbspeed10',
            component:idcpk10,
            name:'idcsbspeed10'
          },
          {
            path:'/idc/xyft3',
            component:idcpk10,
            name:'idcxyft3'
          },
          {
            path:'/idc/xyft1396',
            component:idcpk10,
            name:'idcxyft1396'
          },
          {
            path:'/idc/hmpk10',
            component:idcpk10,
            name:'idchmpk10'
          },
          {
            path:'/idc/gdkl10',
            component:idckl10,
            name:'idcgdkl10'
          },
          {
            path:'/idc/speed8',
            component:idckl10,
            name:'idcspeed8'
          },
          {
            path:'/idc/xync',
            component:idckl10,
            name:'idcxync'
          },
          {
            path:'/idc/lucky8',
            component:idckl10,
            name:'idclucky8'
          },
        ]
      },
    ]
  },

]
