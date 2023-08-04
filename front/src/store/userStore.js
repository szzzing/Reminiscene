import axios from 'axios'

export const user = {
    namespaced: true,
    state: {
        user: {},
        token: '',
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
    },
    actions: {
        getUser(context, id) {
            axios.get("http://localhost:8080/user/"+id)
            .then((response)=>{
                if(response.status == 200) {
                    context.commit("setUser", response.data);
                }
            })
        },
    }
};