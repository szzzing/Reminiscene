import Vuex from 'vuex'
import createPersistedState from 'vuex-persistedstate';
import { auth } from './modules/authStore.js'
import { movie } from './modules/movieStore.js'

export const store = new Vuex.Store({
    modules: {
        auth,
        movie,
    },
    plugins: [
        createPersistedState({
            paths: ["auth"],
            storage: window.sessionStorage,
        }),
    ]
});