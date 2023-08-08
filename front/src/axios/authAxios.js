import axios from 'axios'
import { store } from '../store/index'
import { router } from '../router/index'

// axios 전역 설정
axios.defaults.baseURL = "http://localhost:8080";

// 요청을 보낼 때 상태에 저장된 token을 함께 전송
axios.interceptors.request.use(
    config => {
        config.headers.authorization = store.state.auth.token;
        return config;
    },
);

// 응답으로 받은 헤더에서 토큰 및 사용자 정보 확인,
// 토큰이 만료되었을 시 전역 상태에서 삭제
axios.interceptors.response.use(
    config => {
        // 토큰 저장
        store.commit("auth/setToken", config.headers.authorization);
        // 사용자 정보 저장
        if(config.headers.user != null) {
            store.commit("auth/setUser", JSON.parse(config.headers.user));
        }
        return config
    },
    error => {
        if(error.response.status == '901') {
            store.commit("auth/logout");
            alert("인증이 만료되었어요🥺\n다시 로그인해주세요.");
            router.push({ path: '/login' });
        }
        // Promise.reject(error);
    }
)

export default axios;