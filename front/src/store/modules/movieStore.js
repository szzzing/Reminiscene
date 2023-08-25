
const movie = {
    namespaced: true,
    state: {
        query: '',
        list: [],
        page: 1,
    },
    mutations: {
        setQuery(state, payload) {
            state.query = payload;
        },

        setList(state, payload) {
            state.list = payload;
        },

        setPage(state, payload) {
            state.page = payload
        },
    },
};

export default movie;