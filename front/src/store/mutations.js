export const mutations = {
    // 영화 상세정보 수정
    setMovie(state, payload) {
        payload.release_date = payload.release_date.split('-').join('.');
        payload.backdrop_path = 'https://image.tmdb.org/t/p/original/'+payload.backdrop_path
        var genre = [];
        for(var g of payload.genres) {
            genre.push(g.name);
        }
        payload.genres = genre.join('/');
        state.details = payload;
    },
    setSearchList(state, payload) {
        if(state.page==1) {
            state.searchList = payload;
        } else {
            for(var p of payload) {
                state.searchList.push(p);
            }
        }
    },
    setQuery(state, payload) {
        state.query = payload;
        state.page = 1;
        state.maxPage = 0;
    },
    setPage(state, payload) {
        if(payload<=state.maxPage) {
            state.page = payload
        }
    },
    setMaxPage(state, payload) {
        state.maxPage = payload
    },
    setTheme(state) {
        state.theme = !state.theme;
    }
}