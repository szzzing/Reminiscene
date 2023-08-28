
const movie = {
    namespaced: true,
    state: {
        query: '',
        list: [],
        page: 1,
        state: null,
    },
    mutations: {
        setQuery(state, payload) {
            state.query = payload;
        },

        setList(state, payload) {
            state.list = payload;
        },

        setPage(state, payload) {
            state.page = payload;
        },
        setState(state, payload) {
            state.state = payload;
        }
    },
};

export default movie;