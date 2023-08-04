import {createRouter, createWebHashHistory} from 'vue-router'
import AppMain from '../components/main/AppMain'
import AppLogin from '../components/auth/AppLogin'
import AppRegister from '../components/auth/AppRegister'
import MovieSearch from '../components/movie/MovieSearch.vue'
import MovieContent from '../components/movie/MovieContent.vue'

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
]

export const router = createRouter({
    history: createWebHashHistory(),
    // 페이지 이동 시 스크롤 탑 적용
    scrollBehavior() {
        return { top: 0 }
    },
    routes
})