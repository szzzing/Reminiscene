import store from '@/store/index';
import router from '@/router';
import axios from '@/axios/index';

const auth = {
    namespaced: true,
    state: {
        user: null,
        token: null,
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
                const day = parts[1].padStart(2, '0');
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
            store.commit("modal/setProfile", false);
            store.commit("modal/setAlert", { alertEmoji:"🖐️", alertText:"로그아웃 되었어요." });

            // 라우팅
            const path = router.currentRoute._value.path;
            if(path.startsWith('/mypage')) {
                router.replace({path: "/"});
            } else if(path.startsWith('/admin')) {
                router.replace({path: "/login"});
            }
        },
    },
    actions: {
        logout(context) {
            if(context.state.user) {
                axios.post(`/logout/${context.state.user.id}`)
                .then(()=>{
                    context.commit("logout");
                })
            }
        },
    }
};

export default auth;