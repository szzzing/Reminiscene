import {createRouter, createWebHistory} from 'vue-router'
import AppMain from '../components/common/AppMain'
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