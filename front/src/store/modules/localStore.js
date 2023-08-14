const local = {
    namespaced: true,
    state: {
        theme: true,
        location: null,
        alert: false,
        alertEmoji: null,
        alertText: null,
    },
    mutations: {
        setLocation(state, payload) {
            state.location = payload;
        },
        setTheme(state) {
            document.documentElement.classList.toggle('dark');
            state.theme = !state.theme;
        },
        setAlert(state, {alertEmoji, alertText}) {
            state.alertEmoji = alertEmoji;
            state.alertText = alertText;
            state.alert = true;
            setTimeout(() => state.alert = false, 3000);
        }
    },
};

export default local;