import axios from 'axios'
import { store } from '../store/index'

// axios 전역 설정
axios.defaults.baseURL = "http://localhost:8080";
// 요청을 보낼 때 상태에 저장된 token을 함께 전송
axios.interceptors.request.use((config)=>{
    config.headers.Authorization = store.state.auth.token;
    return config;
});

export default axios;