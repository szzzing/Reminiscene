export const local = {
    namespaced: true,
    state: {
        theme: true,
    },
    mutations: {
        setTheme(state) {
            // document.documentElement.classList.toggle('dark');
            state.theme = !state.theme;
        },
    },
};