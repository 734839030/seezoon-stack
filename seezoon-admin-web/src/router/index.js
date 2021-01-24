import {createRouter, createWebHistory} from 'vue-router'


const routes = [
    {path: '/login', component: () => import('../views/user/Login')},
    {path: '/', component: () => import('../views/index')},
    {path: '/home', component: () => import('../views/index')},
    {path: '/sys/param', component: () => import('../views/sys/param/index')},
    {path: '/sys/dict', component: () => import('../views/sys/dict/index')},
    {path: '/sys/file', component: () => import('../views/sys/file/index')},
    {path: '/sys/dept', component: () => import('../views/sys/dept/index')},
    {path: '/sys/user', component: () => import('../views/sys/user/index')},
    {path: '/sys/role', component: () => import('../views/sys/role/index')},
]


const router = createRouter({
    history: createWebHistory(),
    routes // (缩写) 相当于 routes: routes
})

export default router