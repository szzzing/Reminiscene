import Vuex from 'vuex'
import createPersistedState from 'vuex-persistedstate';
import { user } from './userStore.js'
import { movie } from './movieStore.js'

export const store = new Vuex.Store({
    modules: {
        user,
        movie,
    },
    plugins: [
        createPersistedState({
            paths: ["user"],
            storage: window.sessionStorage,
        }),
    ]
});