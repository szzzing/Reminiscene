import axios from '@/axios/authAxios';
import {router} from '@/router/index';

export const auth = {
    namespaced: true,
    state: {
        user: null,
        token: null,
        theme: true,
    },
    getters: {

    },
    mutations: {
        setUser(state, payload) {
            state.user = payload;
        },
        setToken(state, payload) {
            state.token = payload;
        },
        setTheme(state) {
            document.documentElement.classList.toggle('dark');
            state.theme = !state.theme;
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
                    const user = JSON.parse(response.headers.user);
                    user.nickname = decodeURIComponent(user.nickname);
                    context.commit("setUser", user);
                    // 메인 페이지로 이동
                    router.push({ path: '/' });
                }
            })
            .catch((error)=>{
                console.log(error);
                alert("로그인에 실패했어요🥺");
            });
        },
        getUser(context, id) {
            axios.get("http://localhost:8080/mypage/"+id)
            .then((response)=>{
                if(response.status == 200) {
                    context.commit("setUser", response.data);
                }
            })
        },
    }
};