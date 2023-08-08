import authAxios from '@/axios/authAxios';
import {router} from '@/router/index';
import {store} from '../index'

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
            router.go(0);
        }
    },
    actions: {
        login(context, {id, pw}) {
            authAxios.post("/login",{id, pw})
            .then(response=>{
                if(response.status==200) {
                    // 토큰 저장
                    context.commit("setToken", response.headers.authorization);
                    // 사용자 정보 저장
                    context.commit("setUser", JSON.parse(response.headers.user));
                    // 페이지 이동 - 이전 페이지 정보가 있을 시 이전페이지, 없을 시 메인페이지
                    router.push({ path: store.state.local.location==null ? '/' : store.state.local.location });
                }
            })
            .catch(()=>{
                alert("로그인에 실패했어요🥺");
            });
        },
    }
};