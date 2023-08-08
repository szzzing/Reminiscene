import {createRouter, createWebHashHistory} from 'vue-router'
import axios from '../axios/authAxios'
import {store} from '../store/index'

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
        component: MainPage
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
    // 인증 처리
    axios.get("/route").then(()=>{
        // 1. anonymous() : 인증 상태일 시 에러페이지로
        if(to.path.startsWith("/auth") || to.path.startsWith("/login")) {
            if(store.state.auth.user!=null) {
                next("/error");
            } else {
                next();
            }
        // 2. authorized() : 비인증 상태일 시 로그인 페이지로
        } else if(to.path.startsWith("/mypage")) {
            if(store.state.auth.user==null) {
                alert("로그인이 필요한 페이지입니다.\n로그인 페이지로 이동합니다.");
                return next("/login");
            } else {
                next();
            }
        // 3. permitAll() : 처리 없이 route
        } else {
            next();
        }
    });
});

export { router };