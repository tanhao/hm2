import AppMain from "../components/main/main.vue";

export default [
    {
        path: '/login',
        name: '登入',
        component: () => import('../views/login/login.vue')
    },
    {
        path: '/',
        name: '_home',
        redirect: '/home',
        component: AppMain,
        children: [
            {
                path: '/home',
                name: '系统公告',
                url: '/home',
                component: () => import('../views/home.vue')
            },
        ]
    },
    {path: '/404', name: 'page404', component: () => import('../views/error-page/page404.vue')},
]
