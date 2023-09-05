import {createRouter, createWebHashHistory} from 'vue-router'
import store from '@/store/index'
import axios from '@/axios/index'

import MainPage from '@/components/main/MainPage'
import ErrorPage from '@/components/error/ErrorPage'
import LoginPage from '@/components/auth/LoginPage'
import RegisterPage from '@/components/auth/RegisterPage'
import FindPage from '@/components/auth/FindPage'
import DetailPage from '@/components/detail/DetailPage'
import CommentListPage from '@/components/comment/CommentListPage'
import CommentPage from '@/components/comment/CommentPage'
import MyPage from '@/components/mypage/MyPage'
import MyInfoComponent from '@/components/mypage/InfoComponent'
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
import UserInfoComponent from '@/components/user/InfoComponent'
import UserWishPage from '@/components/user/WishPage'
import UserCommentPage from '@/components/user/CommentPage'
import UserLikePage from '@/components/user/LikePage'
import UserWatchingPage from '@/components/user/WatchingPage'

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
        children: [
            {
                path: '/user/:id',
                component: UserInfoComponent,
            },
            {
                path: '/user/:id/wish',
                component: UserWishPage,
            },
            {
                path: '/user/:id/comment',
                component: UserCommentPage,
            },
            {
                path: '/user/:id/like',
                component: UserLikePage,
            },
            {
                path: '/user/:id/watching',
                component: UserWatchingPage,
            },
        ]
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