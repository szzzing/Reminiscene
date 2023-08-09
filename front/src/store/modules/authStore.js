import router from '@/router/index';
import axios from '@/axios/index'
import store from '../index'

const auth = {
    namespaced: true,
    state: {
        user: null,
        token: null,
    },
    getters: {

    },
    mutations: {
        setUser(state, payload) {
            if(payload != null && payload.nickname != null) {
                payload.nickname = decodeURIComponent(payload.nickname);
            }
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
                if(response.headers.auth=="IS_AUTHENTICATED") {
                    // 토큰 저장
                    context.commit("setToken", response.headers.token);
                    // 사용자 정보 저장
                    context.commit("setUser", JSON.parse(response.headers.user));
                    // 페이지 이동 - 이전 페이지 정보가 있을 시 이전페이지, 없을 시 메인페이지
                    router.push({ path: store.state.local.location==null ? '/' : store.state.local.location });
                } else if(response.headers.auth=="AUTHENTICATION_FAILED") {
                    alert("로그인에 실패했어요🥺");
                }
            })
            .catch(()=>{
            });
        },
    }
};

export default auth;