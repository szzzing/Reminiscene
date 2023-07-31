import axios from 'axios'

export const actions = {
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
    getDetails(context, payload) {
        axios
        .get('https://api.themoviedb.org/3/movie/'+payload+'?api_key=7bf40bf859def4eaf9886f19bb497169&language=ko-KR')
        .then(function(response) {
            context.commit('setDetails', response.data);
        });
    }
}