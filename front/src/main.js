import { createApp } from 'vue'
import App from './App.vue'
import store from '@/store/index'
import router from '@/router/index'
import axios from '@/axios/index'
import InfiniteLoading from 'infinite-loading-vue3-ts';


const app = createApp(App);
app.config.globalProperties.axios = axios;

app.use(router)
    .use(store)
    .use(InfiniteLoading)
    .mount('#app');