import {createRouter, createWebHashHistory} from 'vue-router'
import store from '@/store/index'
import axios from '@/axios/index'

import MainPage from '@/components/main/MainPage'
import ErrorPage from '@/components/error/ErrorPage'
import LoginPage from '@/components/auth/LoginPage'
import RegisterPage from '@/components/auth/RegisterPage'
import FindPage from '@/components/auth/FindPage'
import ListPage from '@/components/list/ListPage'
import DetailPage from '@/components/detail/DetailPage'
import CommentListPage from '@/components/comment/CommentListPage'
import CommentPage from '@/components/comment/CommentPage'
import MyPage from '@/components/mypage/MyPage'
import ModifyPage from '@/components/mypage/ModifyPage'
import MyWishPage from '@/components/mypage/WishPage'
import MyCommentPage from '@/components/mypage/CommentPage'

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
        path: '/detail/:id/comment',
        component: CommentListPage,
    },
    {
        path: '/comment/:id',
        component: CommentPage,
    },
    {
        path: '/mypage',
        component: MyPage,
    },
    {
        path: '/mypage/modify',
        component: ModifyPage,
    },
    {
        path: '/mypage/wish',
        component: MyWishPage,
    },
    {
        path: '/mypage/comment',
        component: MyCommentPage,
    },
    {
        path: '/:pathMatch(.*)*',
        redirect: '/error',
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

    // 프로필 모달 닫기
    store.commit("modal/setProfile", false);

    // 페이지 위치 정보 저장
    if(to.path!='/login' && !to.path.startsWith('/auth') && !to.path.endsWith("/error")) {
        store.state.local.location = to.path;
    }

    // 인증 처리
    axios.get("/route"+to.path).then(() => {
        next();
    });
});

export default router;