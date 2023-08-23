const local = {
    namespaced: true,
    state: {
        theme: true,
        location: null,
        title: "Reminiscene"
    },
    mutations: {
        setLocation(state, payload) {
            state.location = payload;
        },
        setTheme(state) {
            document.documentElement.classList.toggle('dark');
            state.theme = !state.theme;
        },
    },
};

export default local;