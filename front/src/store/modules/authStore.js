import axios from '@/axios/authAxios';
import {router} from '@/router/index';

export const auth = {
    namespaced: true,
    state: {
        user: null,
        token: null,
    },
    getters: {

    },
    mutations: {
        setUser(state, payload) {
            payload.nickname = decodeURIComponent(payload.nickname);
            state.user = payload;
        },
        setToken(state, payload) {
            state.token = payload;
        },
        logout(state) {
            state.user = null;
            state.token = null;
        }
    },
    actions: {
        login(context, {id, pw}) {
            axios.post("/login",{id, pw})
            .then(response=>{
                if(response.status==200) {
                    // 토큰 저장
                    context.commit("setToken", response.headers.authorization);
                    // 사용자 정보 저장
                    context.commit("setUser", JSON.parse(response.headers.user));
                    // 메인 페이지로 이동
                    router.push({ path: '/' });
                }
            })
            .catch(()=>{
                alert("로그인에 실패했어요🥺");
            });
        },
    }
};