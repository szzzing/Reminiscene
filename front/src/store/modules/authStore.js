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
            // 닉네임 변환
            if(payload != null && payload.nickname != null) {
                payload.nickname = decodeURIComponent(payload.nickname);
            }

            // 생일 변환
            if(payload != null && payload.birthday != null) {
                const months = [
                    "Jan", "Feb", "Mar", "Apr", "May", "Jun",
                    "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"
                ];
            
                const parts = payload.birthday.split(/ |, /);
                const month = (months.indexOf(parts[0]) + 1).toString().padStart(2, '0');
                const day = parts[1];
                const year = parts[2];

                payload.birthday = `${year}-${month}-${day}`;
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