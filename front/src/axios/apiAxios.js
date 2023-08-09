import axios from 'axios'
import { router } from '../router/index'
import { store } from '../store/index'

const apiAxios = axios.create({
    baseURL: 'https://',
});

// 토큰이 만료되었을 시 전역 상태에서 삭제
apiAxios.interceptors.response.use(
    config => {
        if(config.headers.auth=="IS_EXPIRED") {
            store.commit("auth/logout");
            alert("인증이 만료되었어요🥺\n다시 로그인해주세요.");
            router.push({ path: '/login' });
        }
        return config
    },
    error => {
        console.log(error);
        // if(error.response.status == '901') {
        //     alert("다시 로그인해주세요.");
        //     router.push({ path: '/login' });
        //     store.commit("auth/logout");
        // }
    }
)

export default apiAxios;