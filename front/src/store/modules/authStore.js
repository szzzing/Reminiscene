import axios from 'axios'

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
            state.theme = !state.theme;
        },
        logout(state) {
            state.user = null;
            state.token = null;
        }
    },
    actions: {
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