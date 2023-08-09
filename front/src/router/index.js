import {createRouter, createWebHashHistory} from 'vue-router'
import store from '@/store/index'
import axios from '@/axios/index'

import MainPage from '../components/main/MainPage'
import ErrorPage from '../components/error/ErrorPage'
import LoginPage from '../components/auth/LoginPage'
import RegisterPage from '../components/auth/RegisterPage'
import FindPage from '../components/auth/FindPage'
import ListPage from '../components/list/ListPage'
import DetailPage from '../components/detail/DetailPage'

const routes = [
    {
        path: '/',
        component: MainPage
    },
    {
        path: '/error',
        component: ErrorPage
    },
    {
        path: '/auth/register',
        component: RegisterPage
    },
    {
        path: '/auth/find',
        component: FindPage
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
        path: '/detail/:id',
        component: DetailPage,
    },
    {
        path: '/mypage',
        component: MainPage,
    }
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

    // 페이지 위치 정보 저장
    if(to.path!='/login' && !to.path.startsWith('/auth') && !to.path.endsWith("/error")) {
        store.state.local.location = to.path;
    }
    console.log(store.state.local.location);

    // 인증 처리
    axios.get("/route").then(() => {
        // 1. anonymous() : 인증 상태일 시 에러페이지로
        if(to.path.startsWith("/auth") || to.path.endsWith("/login")) {
            console.log("비인증상태여야함");
            if(store.state.auth.user!=null) {
                console.log("현재 인증상태");
                next("/error");
            } else {
                next();
            }
        // 2. authorized() : 비인증 상태일 시 로그인 페이지로
        } else if(to.path.startsWith("/mypage")) {
            console.log("인증상태여야함");
            if(store.state.auth.user==null) {
                console.log("현재 비인증상태");
                return next("/login");
            } else {
                next();
            }
        // 3. permitAll() : 처리 없이 route
        } else {
            next();
        }
    }).catch((error)=>{
        console.log("router 에러 : ", error);
    });
});

export default router;