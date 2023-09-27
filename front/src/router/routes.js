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
import WithdrawPage from '@/components/mypage/WithdrawPage'
import ModifyPage from '@/components/mypage/ModifyPage'
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
        path: '/auth/login',
        component: LoginPage
    },
    {
        path: '/content/movie/:id',
        component: DetailPage,
    },
    {
        path: '/content/movie/:id/comments',
        component: CommentListPage,
    },
    {
        path: '/content/comment/:id',
        component: CommentPage,
    },
    {
        path: '/mypage',
        component: MyPage,
    },
    {
        path: '/mypage/withdraw',
        component: WithdrawPage,
    },
    {
        path: '/mypage/modify',
        component: ModifyPage,
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

export default routes;