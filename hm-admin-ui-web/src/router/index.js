import Vue from 'vue'
import VueRouter from 'vue-router'
import routes from './routers'
import store from '@/store'
import userApi from '@/axios/api-user'

Vue.use(VueRouter)
const originalPush = VueRouter.prototype.push
VueRouter.prototype.push = function push(location) {
    return originalPush.call(this, location).catch(err => err)
}

const router = new VueRouter({
    // mode: 'history',
    base: process.env.BASE_URL,
    routes
})

router.beforeEach((to, from, next) => {
    let token = sessionStorage.getItem("token");
    if (to.path !== "/login" && !token) {
        next({path: '/login'})
    } else {
        if (!store.state.user.hasGetRouter && to.path !== "/login") {
            userApi.getRouters(1).then(res => {
                if (res.success) {
                    //初始化菜单，所有账号都有home
                    let menuItems = [pageHome];
                    let routeData = res.data.routers;
                    filterAsyncRouter(routeData, menuItems);
                    store.dispatch("setRouters", menuItems);
                    router.addRoutes(routes.concat([page404]));
                    next({...to, replace: true});
                }
            })
        } else {
            next();
        }
    }
})

router.afterEach(to => {
    // window.document.title = `系统管理 - ${to.name}`;
    window.scrollTo(0, 0)
})

export default router

const page404 = {
    path: '*', name: 'error_404',
    component: () => import('@/views/error-page/page404.vue')
}

const pageHome = {
    mtype: 2,
    path: '/home',
    url: '/home',
    name: '系统公告',
    children: [],
}

function filterAsyncRouter(routers, menuItem) {
    routers.forEach(route => {
        let router = {url: route.url.replace(":", ""), mtype: route.mtype, name: route.menuName};
        router.path = route.url;
        router.children = [];
        if (router.mtype === 2) {
            if (router.path.indexOf(":") !== -1) {
                let path = router.path.split("/:")[0];
                router.path = path + "/:type";
                router.component = () => import(`@/views${path}`);
            } else {
                router.component = () => import(`@/views${router.path}`);
            }
            //获取home下的路由为主路由
            routes[1].children.push(router)
        }
        if (route.children && route.children.length) {
            filterAsyncRouter(route.children, router.children);
        }
        //转换菜单栏需要的数据
        menuItem.push(router);
    });
}