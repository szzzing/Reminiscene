<template>
    <movie-list-component v-bind:list="movieList"></movie-list-component>

    <empty-component v-if="this.movieList.length==0">
        <template v-slot:text>
            검색결과가 없어요.
        </template>
    </empty-component>

    <infinite-loading @infinite="getMovieList"></infinite-loading>
</template>

<script>
import EmptyComponent from '../item/EmptyComponent.vue';
import { InfiniteLoading } from 'infinite-loading-vue3-ts';
import MovieListComponent from '../item/MovieListComponent.vue';

export default {
    components: {
        EmptyComponent,
        InfiniteLoading,
        MovieListComponent,
    },
    data() {
        return {
            movieList: [],
            page: 1,
        }
    },
    props: [
        'query',
    ],
    methods: {
        // 영화 리스트 불러오기
        getMovieList($state) {
            const params = {
                query: this.query,
                page: this.page,
            }
            this.axios.get("/movie", {params})
            .then((response)=>{
                if(response.data.list.length>0) {
                    this.movieList.push(...response.data.list);
                    this.page = response.data.page+1;
                    $state.loaded();
                }
            })
        },
    },
}
</script>

<style scoped>
.query {
    font-size: 18px;
}
</style>