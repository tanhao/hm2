import Vue from 'vue'
import Router from 'vue-router'
import index from '@/components/index'
// 简版
import simple from '@/components/simple'

//开奖网所有页面
import six from '@/components/6hch/main'
import about from '@/components/page/about'
import help from '@/components/page/help'
import cooperation from '@/components/page/cooperation'
import customer from '@/components/page/customer'
import declaration from '@/components/page/declaration'
import rules from '@/components/lottery/rules'
import news from '@/components/news/news'
//彩种类
import pk10 from '@/components/lottery/pk10/pk10'
import pk10history from '@/components/lottery/pk10/history'
import pk10nh from '@/components/lottery/pk10/nhlist'
import pk10gh from '@/components/lottery/pk10/gh'
import ssc from '@/components/lottery/ssc/ssc'

import sschistory from '@/components/lottery/ssc/history'
import sscgh from '@/components/lottery/ssc/gh'
import klsf from '@/components/lottery/klsf/klsf'
import klsfhistory from '@/components/lottery/klsf/history'
import xync from '@/components/lottery/xync/xync'

Vue.use(Router)
export default new Router({
    routes: [
        {
            path: '/',
            component: index,
            name: 'index'
        },

        //简版
        {
            path: '/simple/',
            component: simple,
            name: 'simple'
        },
        // 六合彩
        {
            path: '/six/',
            component: six,
            name: 'six'
        },
        // 关于我们
        {
            path: '/about/',
            component: about,
            name: 'about'
        },
        // 帮助中心
        {
            path: '/help/',
            component: help,
            name: 'help'
        },
        // 客服
        {
            path: '/cooperation/',
            component: cooperation,
            name: 'cooperation'
        },
        // 商务合作
        {
            path: '/customer/',
            component: customer,
            name: 'customer'
        },
        // 免责声明
        {
            path: '/declaration/',
            component: declaration,
            name: 'declaration'
        },
        // 玩法规则
        {
            path: '/rules/',
            component: rules,
            name: 'rules'
        },
        //新闻
        {
            path: '/news/',
            component: news,
            name: 'news'
        },

        // 彩种类 pk10
        {
            path: '/pk10/',
            component: pk10,
            name: 'pk10'
        },
        // pk10 开奖历史
        {
            path: '/pk10history/',
            component: pk10history,
            name: 'pk10history'
        },
        // pk10 龙虎
        {
            path: '/pk10nh/',
            component: pk10nh,
            name: 'pk10nh'
        },
        // pk10 计划
        {
            path: '/pk10gh/',
            component: pk10gh,
            name: 'pk10gh'
        },
        // 彩种类 ssc
        {
            path: '/ssc/',
            component: ssc,
            name: 'ssc'
        },
        // ssc 计划
        {
            path: '/sscgh/',
            component: sscgh,
            name: 'sscgh'
        },
        // ssc 开奖历史
        {
            path: '/sschistory/',
            component: sschistory,
            name: 'sschistory'
        },
        // 彩种类 klsf
        {
            path: '/klsf/',
            component: klsf,
            name: 'klsf'
        },
        // klsf 开奖历史
        {
            path: '/klsfhistory/',
            component: klsfhistory,
            name: 'klsfhistory'
        },
        // 彩种类 xync
        {
            path: '/xync/',
            component: xync,
            name: 'xync'
        },
    ]
})
