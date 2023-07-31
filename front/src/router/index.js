import {createRouter, createWebHistory} from 'vue-router'
import MovieMain from '../components/movie/MovieMain.vue'
import MovieContent from '../components/movie/MovieContent.vue'
import AdminMain from '../components/admin/AdminMain.vue'
import AdminUser from '../components/admin/AdminUser.vue'

const routes = [
    {
        path: '/',
        component: MovieMain
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