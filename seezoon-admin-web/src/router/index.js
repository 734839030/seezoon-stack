import {createRouter, createWebHistory} from 'vue-router'


const routes = [
    {path: '/login', component: () => import('../views/user/Login')},
    {path: '/', component: () => import('../views/index')},
    {path: '/home', component: () => import('../views/index')},
    {path: '/sys/param', component: () => import('../views/sys/param/index')},
    {path: '/sys/dict', component: () => import('../views/sys/dict/index')},
]


const router = createRouter({
    history: createWebHistory(),
    routes // (缩写) 相当于 routes: routes
})

export default router