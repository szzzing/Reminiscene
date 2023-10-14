const local = {
    namespaced: true,
    state: {
        theme: true,
        location: null,
        title: "Reminiscene",
        suggest: false,
    },
    mutations: {
        setLocation(state, payload) {
            state.location = payload;
        },
        setTheme(state) {
            document.documentElement.classList.toggle('dark');
            state.theme = !state.theme;
        },
        setSuggest(state, payload) {
            state.suggest = payload;
        },
    },
};

export default local;