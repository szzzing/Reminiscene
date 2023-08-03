import {createRouter, createWebHashHistory} from 'vue-router'
import AppMain from '../components/main/AppMain'
import AppRegister from '../components/main/AppRegister'
import AppLogin from '../components/main/AppLogin'
import MovieSearch from '../components/movie/MovieSearch.vue'
import MovieContent from '../components/movie/MovieContent.vue'
import AdminMain from '../components/admin/AdminMain.vue'
import AdminUser from '../components/admin/AdminUser.vue'

const routes = [
    {
        path: '/',
        component: AppMain
    },
    {
        path: '/register',
        component: AppRegister
    },
    {
        path: '/login',
        component: AppLogin
    },
    {
        path: '/search',
        component: MovieSearch
    },
    {
        path: '/content/:id',
        component: MovieContent
    },
    {
        path: '/admin',
        component: AdminMain,
        children: [
            {
                path: 'user',
                component: AdminUser
            },
        ]
    }
]

export const router = createRouter({
    history: createWebHashHistory(),
    // 페이지 이동 시 스크롤 탑 적용
    scrollBehavior() {
        return { top: 0 }
    },
    routes
})