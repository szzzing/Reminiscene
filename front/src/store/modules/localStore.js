const local = {
    namespaced: true,
    state: {
        theme: true,
        location: null,
        title: "Reminiscene",
        query: null,
    },
    mutations: {
        setLocation(state, payload) {
            state.location = payload;
        },
        setTheme(state) {
            document.documentElement.classList.toggle('dark');
            state.theme = !state.theme;
        },
        setQuery(state, payload) {
            state.query = payload;
        }
    },
};

export default local;