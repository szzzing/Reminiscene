import {createRouter, createWebHistory} from 'vue-router'
import AppMain from '../components/main/AppMain'
import AppJoin from '../components/main/AppJoin'
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
        path: '/join',
        component: AppJoin
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
    history: createWebHistory(),
    routes
})