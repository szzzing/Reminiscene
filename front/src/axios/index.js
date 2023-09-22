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
        // 1. 인증이 필요하진 않지만, 토큰 만료로 로그아웃된 경우
        if(store.state.auth.token!=null && config.headers.token==null) {
            store.commit("auth/logout");
            store.commit("modal/setAlert", { alertEmoji:"⚠️", alertText:"인증이 만료되어 로그아웃되었어요." });
        }
        // 토큰 저장
        store.commit("auth/setToken", config.headers.token);
        // 사용자 정보 저장
        store.commit("auth/setUser", config.headers.user==null ? null : JSON.parse(config.headers.user));

        return config;
    },
    (error) => {
        // 1. 미인증으로 인한 에러
        if(error.response.status==401) {

            // 1) 토큰 만료롤 인한 로그아웃 시
            if(store.state.auth.token!=null) {
                store.commit("auth/logout");
                store.commit("modal/setAlert", { alertEmoji:"⚠️", alertText:"다시 로그인해주세요." });
            }
            // 2) 로그인 페이지로 이동
            router.replace({ path: '/login' });
        }

        // 2. 권한 부족으로 인한 에러
        if(error.response.status==403) {

            // 1) 로그인이 안되어있으면 로그인페이지로
            if(store.state.auth.token==null) {
                router.push({ path: '/login' });
                store.commit("modal/setAlert", { alertEmoji:"⚠️", alertText:"로그인이 필요한 페이지예요." });

            // 2) 로그인이 되어있으면 에러페이지로
            } else if(store.state.auth.token!=null) {
                router.replace({ path: '/error' });
            }
        }

        // 2. PAGE NOT FOUND 에러
        if(error.response.status==404) {
            router.replace({ path: '/error' });
        }

        // 4. 서버 에러
        if(error.response.status==500) {
            store.commit("modal/setAlert", { alertEmoji:"⚠️", alertText:"다시 시도해주세요." });
        }

        // 에러 되던지기
        return Promise.reject(error);
    }
);

export default axios;