import {createRouter, createWebHashHistory} from 'vue-router'
import MainPage from '../components/main/MainPage'
import LoginPage from '../components/auth/LoginPage'
import RegisterPage from '../components/auth/RegisterPage'
import ListPage from '../components/list/ListPage'
import DetailPage from '../components/detail/DetailPage'

const routes = [
    {
        path: '/',
        component: MainPage
    },
    {
        path: '/register',
        component: RegisterPage
    },
    {
        path: '/login',
        component: LoginPage
    },
    {
        path: '/list',
        component: ListPage
    },
    {
        path: '/content/:id',
        component: DetailPage
    },
]

export const router = createRouter({
    history: createWebHashHistory(),
    // 페이지 이동 시 스크롤 탑 적용
    scrollBehavior() {
        return { top: 0 }
    },
    routes
})