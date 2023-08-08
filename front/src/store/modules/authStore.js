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
                    context.commit("setToken", response.headers.authorization);
                    context.dispatch("getUser", id);
                    router.push({ path: '/' });
                }
            })
            .catch(()=>{
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