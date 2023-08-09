import Vuex from 'vuex'
import createPersistedState from 'vuex-persistedstate';
import auth from './modules/authStore.js'
import movie from './modules/movieStore.js'
import local from './modules/localStore.js'

const store = new Vuex.Store({
    modules: {
        auth,
        movie,
        local,
    },
    plugins: [
        createPersistedState({
            paths: ["local", "auth"],
            storage: window.sessionStorage,
        }),
    ]
});

export default store;