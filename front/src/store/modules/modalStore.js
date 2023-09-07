const modal = {
    namespaced: true,
    state: {
        alert: false,
        alertEmoji: null,
        alertText: null,
        profile: false,
    },
    mutations: {
        setAlert(state, {alertEmoji, alertText}) {
            state.alertEmoji = alertEmoji;
            state.alertText = alertText;
            state.alert = true;
            setTimeout(() => {
                state.alert = false;
            }, 3000);
            clearTimeout();
        },
        setProfile(state, payload) {
            state.profile = payload;
        }
    },
};

export default modal;