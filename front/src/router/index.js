import {createRouter, createWebHashHistory} from 'vue-router'
import store from '@/store/index'
import axios from '@/axios/index'

import MainPage from '@/components/main/MainPage'
import ErrorPage from '@/components/error/ErrorPage'
import LoginPage from '@/components/auth/LoginPage'
import RegisterPage from '@/components/auth/RegisterPage'
import FindPage from '@/components/auth/FindPage'
import FindComponent from '@/components/auth/FindComponent'
import FindIdPage from '@/components/auth/FindIdPage'
import FindPwPage from '@/components/auth/FindPwPage'
import DetailPage from '@/components/detail/DetailPage'
import CommentListPage from '@/components/comment/CommentListPage'
import CommentPage from '@/components/comment/CommentPage'
import MyPage from '@/components/mypage/MyPage'
import MyInfoComponent from '@/components/mypage/InfoComponent'
import WithdrawComponent from '@/components/mypage/WithdrawComponent'
import ModifyPage from '@/components/mypage/ModifyPage'
import MyWishPage from '@/components/mypage/WishPage'
import MyCommentPage from '@/components/mypage/CommentPage'
import MyLikePage from '@/components/mypage/LikePage'
import MyWatchingPage from '@/components/mypage/WatchingPage'
import SearchPage from '@/components/search/SearchPage'
import SearchComponent from '@/components/search/SearchComponent'
import SearchUserComponent from '@/components/search/SearchUserComponent'
import SearchMovieComponent from '@/components/search/SearchMovieComponent'
import UserPage from '@/components/user/UserPage'
import AdminMainPage from '@/components/admin/MainPage'
import AdminUserPage from '@/components/admin/UserPage'
import AdminMoviePage from '@/components/admin/MoviePage'

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
        component: FindPage,
        children: [
            {
                path: '/auth/find',
                component: FindComponent,
            },
            {
                path: '/auth/find/id',
                component: FindIdPage,
            },
            {
                path: '/auth/find/pw',
                component: FindPwPage,
            }
        ]
    },
    {
        path: '/login',
        component: LoginPage
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
        children: [
            {
                path: '/mypage',
                component: MyInfoComponent,
            },
            {
                path: '/mypage/modify',
                component: ModifyPage,
            },
            {
                path: '/mypage/withdraw',
                component: WithdrawComponent,
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
                path: '/mypage/like',
                component: MyLikePage,
            },
            {
                path: '/mypage/watching',
                component: MyWatchingPage,
            },
        ]
    },
    {
        path: '/search/:query',
        component: SearchPage,
        children: [
            {
                path: '/search/user/:query',
                component: SearchUserComponent,
            },
            {
                path: '/search/movie/:query',
                component: SearchMovieComponent,
            },
            {
                path: '/search/:query',
                component: SearchComponent,
            },
        ]
    },
    {
        path: '/user/:id',
        component: UserPage,
    },
    {
        path: '/admin',
        component: AdminMainPage,
        children: [
            {
                path: '/admin/user',
                component: AdminUserPage,
            },
            {
                path: '/admin/movie',
                component: AdminMoviePage,
            },
        ],
    },
    {
        path: '/:pathMatch(.*)*',
        redirect: '/error',
    },
]

const router = createRouter({
    history: createWebHashHistory(),
    // 라우터 선택 효과
    linkActiveClass: "route-active",
    linkExactActiveClass: "route-active",
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