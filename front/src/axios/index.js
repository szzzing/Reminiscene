import axios from 'axios'
import { store } from '../store/index'

// axios 전역 설정
axios.defaults.baseURL = "http://localhost:8080";

// 요청을 보낼 때 상태에 저장된 token을 함께 전송
axios.interceptors.request.use(
    (config)=>{
        config.headers.Authorization = store.state.auth.token;
        return config;
    },
);

// 토큰이 만료되었을 시 전역 상태에서 삭제
axios.interceptors.response.use(
    (config) => {
        return config
    },
    (error) => {
        console.log(error);
        store.commit("auth/setToken", "");
        store.commit("auth/setUser", null);
    }
)

export default axios;