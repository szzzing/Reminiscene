import axios from 'axios'

export const user = {
    namespaced: true,
    state: {
        user: {},
        theme: false,
    },
    getters: {

    },
    mutations: {
        setUser(state, payload) {
            state.user = payload;
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