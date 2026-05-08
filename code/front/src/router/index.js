import Vue from 'vue'
import VueRouter from 'vue-router'


Vue.use(VueRouter)

const routes = [
    {
        path: '/',
        redirect: '/login'
    },
    {
        path: '/login',
        name: 'login',
        component: () => import('@/views/Login.vue')
    },
    {
        path: '/register',
        name: 'register',
        component: () => import('@/views/Register.vue')
    },
    // 前台
    {
        path: '/front',
        name: 'front',
        component: () => import('@/views/front/index.vue'),
        children: [
            {
                path: '/supplier',
                name: 'supplier',
                component: () => import('@/views/front/views/Supplier.vue'),
                meta: {
                    title: '供应商',
                }
            },
            {
                path: '/producer',
                name: 'producer',
                component: () => import('@/views/front/views/Producer.vue'),
                meta: {
                    title: '生产商',
                }
            },
            {
                path: '/retailer',
                name: 'retailer',
                component: () => import('@/views/front/views/Retailer.vue'),
                meta: {
                    title: '零售商',
                }
            },
            {
                path: '/trace',
                name: 'trace',
                component: () => import('@/views/front/views/Trace.vue'),
                meta: {
                    title: '溯源查询',
                }
            },
            {
                path: '/traceDetail/:traceCode',
                name: 'trace-detail',
                component: () => import('@/views/front/views/TraceDetail.vue'),
                meta: {
                    title: '溯源详情',
                }
            }
        ]
    },
]

const router = new VueRouter({
    routes,
    mode: 'history'
})

export default router
