import {createRouter, createWebHashHistory} from 'vue-router'
import {store} from '../store/index'

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

const router = createRouter({
    history: createWebHashHistory(),
    // 페이지 이동 시 스크롤 탑 적용
    scrollBehavior() {
        return { top: 0 }
    },
    routes
})

// 라우터 가드
router.beforeEach(function (to, from, next) {
    // to : 이동할 url
    // from : 현재 url
    // next : to에서 지정한 url로 이동하기 위해 꼭 호출해야 하는 함수
    console.log("to", to);
    console.log("from", from);
    next();
});

export { router };