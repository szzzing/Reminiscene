<template>
    <div class="container">
        <div class="input-box">
            <input type="text" placeholder="오펜하이머" v-model="query">
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
import { InfiniteLoading } from 'infinite-loading-vue3-ts';

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
            state: null,
        }
    },

    methods: {
        getList($state) {
            this.state = $state;
            const vm = this;

            this.movieAxios.get('api.themoviedb.org/3/search/movie?query='+this.query+'&api_key=7bf40bf859def4eaf9886f19bb497169&language=ko-KR&page='+this.page)
            .then(function(response) {
                if(response.data.results.length!=0) {
                    vm.list.push(...response.data.results);
                    vm.page = response.data.page + 1;
                    $state.loaded();
                } else {
                    $state.complete();
                }
            });
        },
    },

    watch: {
        query() {
            this.list = [];
            this.page = 1;
            this.state.reset();
        },
    },

    // /detail 페이지로 이동시 검색결과 유지
    // 그 외 페이지로 이동시 검색결과 유지 x
    beforeRouteLeave(to, from, next) {
        if(!to.path.startsWith('/detail')) {
            this.$store.commit('movie/setQuery', '');
            this.$store.commit('movie/setList', []);
        } else {
            this.$store.commit('movie/setQuery', this.query);
            this.$store.commit('movie/setList', this.list);
            this.$store.commit('movie/setPage', this.page);
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