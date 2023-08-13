// import router from '@/router/index';
// import axios from '@/axios/index'
// import store from '../index'

const auth = {
    namespaced: true,
    state: {
        user: null,
        token: null,
        loginAlert: false,
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
            
        },
        setLoginAlert(state) {
            state.loginAlert = true;
            setTimeout(() => state.loginAlert = false, 3000);
        }
    },
    actions: {

    }
};

export default auth;