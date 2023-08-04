import { createApp } from 'vue'
import App from './App.vue'
import { store } from './store/store'
import { router } from './router/index'
import axios from 'axios'

const app = createApp(App);

// axios 전역 설정
axios.defaults.baseURL = "http://localhost:8080";
// 요청을 보낼 때 상태에 저장된 token을 함께 전송
axios.interceptors.request.use((config)=>{
    config.headers.Authorization = store.state.auth.token;
    return config;
});

app.config.globalProperties.axios = axios;
app.use(router)
    .use(store)
    .mount('#app')