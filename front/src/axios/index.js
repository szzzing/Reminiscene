import router from '@/router/index'
import store from '@/store/index'
import axios from 'axios';

axios.interceptors.request.use(
    (config) => {
        config.headers.token = store.state.auth.token;
        return config;
    },
);

axios.interceptors.response.use(
    (config) => {
        console.log("전역 인터셉터 동작");
        // 토큰 저장
        store.commit("auth/setToken", config.headers.token);
        // 사용자 정보 저장
        store.commit("auth/setUser", config.headers.user==null ? null : JSON.parse(config.headers.user));

        if(config.headers.auth=="IS_EXPIRED") {
            store.commit("auth/logout");
            alert("오랫동안 접속하지 않아 로그아웃 되었어요🥺");
            router.push({ path: '/login' });
        }
        return config
    },
    (error) => {
        console.log(error);
    }
);

export default axios;