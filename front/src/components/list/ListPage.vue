<template>
    <div class="container">
        <div class="input-box">
            <input type="text" placeholder="오펜하이머" :value="query" @input="changeQuery">
        </div>
        <div class="result" v-if="this.query!=''">
            <b>{{ this.query }}</b>에 대한 영화를 보여줄게.
        </div>
        <list-component v-bind:list="list"></list-component>
        <infinite-loading @infinite="getList"></infinite-loading>

    </div>
</template>

<script>
import ListComponent from "./ListComponent.vue";
import InfiniteLoading from 'infinite-loading-vue3-ts';

export default {

    components: {
        InfiniteLoading,
        ListComponent,
    },

    data() {
        return {
            query: this.$store.state.movie.query,
            list: this.$store.state.movie.list,
            page: this.$store.state.movie.page,
            state: this.$store.state.movie.state,
        }
    },

    methods: {
        // 리스트 불러오기
        getList($state) {
            console.log($state);
            this.state = $state;
            const page = this.page;
            const query = this.query;
            
            this.movieAxios.get('api.themoviedb.org/3/search/movie?query='+query+'&api_key=7bf40bf859def4eaf9886f19bb497169&language=ko-KR&page='+page)
            .then((response)=>{
                if(response.data.results.length!=0) {
                    if(page==1) {
                        this.list = response.data.results;
                    } else {
                        this.list.push(...response.data.results);
                    }
                    this.page = page+1;
                    $state.loaded();
                } else {
                    $state.complete();
                }
            })
        },
        // 한글 변경 감지
        changeQuery(e) {
            this.query = e.target.value.trim();
        },
    },
    
    watch: {
        query() {
            this.page = 1;
            this.list = [];
            this.state.reset();
        },
    },

    // /detail 페이지로 이동시 검색결과 유지
    // 그 외 페이지로 이동시 검색결과 유지 x
    beforeRouteLeave(to, from, next) {
        if(!to.path.startsWith('/detail')) {
            this.$store.commit('movie/setQuery', '');
            this.$store.commit('movie/setList', []);
            this.$store.commit('movie/setPage', 1);
            this.$store.commit('movie/setState', null);
        } else {
            this.$store.commit('movie/setQuery', this.query);
            this.$store.commit('movie/setList', this.list);
            this.$store.commit('movie/setPage', this.page);
            this.$store.commit('movie/setState', this.state);
        }
        next();
    },
}
</script>

<style scoped>
    .container {
        max-width: 1280px;
    }
    .input-box {
        max-width: 400px;
    }
    input {
        font-size: 18px;
        text-align: center;
    }
    :focus {
        outline: none;
    }
    .result {
        font-size: 24px;
        text-align: center;
        margin: 40px 0 60px;
        color: var(--G1000);
    }
    .next {
        text-align: center;
        margin: 40px auto 0;
        width: 108px;
    }
</style>