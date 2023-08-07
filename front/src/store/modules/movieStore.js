import anonymous from '../../axios/anonymousAxios'

export const movie = {
    namespaced: true,
    state: {
        query: '',
        detail: {},
        list: {},
        page: 1,
        maxPage: 0
    },
    getters: {

    },
    mutations: {
        // 검색어
        setQuery(state, payload) {
            state.query = payload;
            state.page = 1; // 현재 페이지 재설정
            state.maxPage = 0;  // 전체 페이지 재설정
        },
        // 영화 리스트
        setList(state, payload) {
            // 첫페이지일 경우 리스트 = 결과
            if(state.page==1) {
                state.list = payload;
                // 페이지>1일 경우 기존 리스트에 붙여넣기
            } else {
                for(var p of payload) {
                    state.list.push(p);
                }
            }
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
                anonymous
                .get('api.themoviedb.org/3/search/movie?query='+context.state.query+'&api_key=7bf40bf859def4eaf9886f19bb497169&language=ko-KR&page='+context.state.page)
                .then(function(response) {
                    context.commit('setPage', response.data.page);
                    context.commit('setMaxPage', response.data.total_pages);
                    context.commit('setList', response.data.results);
                });
            }
        },
    }
};