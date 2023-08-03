import axios from 'axios'

export const movie = {
    state: {
        query: '',
        details: {},
        searchList: {},
        page: 1,
        maxPage: 0
    },
    getters: {

    },
    mutations: {
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
    },
    actions: {
        // 영화 검색하기
        searchList(context) {
            if(context.state.maxPage==0 || context.state.page <= context.state.maxPage) {
                axios
                .get('https://api.themoviedb.org/3/search/movie?query='+context.state.query+'&api_key=7bf40bf859def4eaf9886f19bb497169&language=ko-KR&page='+context.state.page)
                .then(function(response) {
                    context.commit('setPage', response.data.page);
                    context.commit('setMaxPage', response.data.total_pages);
                    context.commit('setSearchList', response.data.results);
                });
                console.log('search');
            }
        },
        // 영화 상세정보 불러오기
        searchMovie(context, payload) {
            axios
            .get('https://api.themoviedb.org/3/movie/'+payload+'?api_key=7bf40bf859def4eaf9886f19bb497169&language=ko-KR')
            .then(function(response) {
                context.commit('setMovie', response.data);
            });
        }
    }
};