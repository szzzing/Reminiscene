const local = {
    namespaced: true,
    state: {
        theme: true,
        location: null,
        fileDir: "/upload/",
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