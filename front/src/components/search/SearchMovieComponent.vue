<template>
    <transition-group id="movie-list" tag="div" class="list">
        <router-link class="item" v-for="(movie) in this.movieList" :key="movie" :to="`/detail/${movie.id}`">
            <div class="poster" :style="{'background-image': `url(/upload/poster/${movie.posterPath}` }">
            </div>
            <div class="info">
                <div class="title">{{ movie.title }}</div>
                <div class="genre">{{ movie.genre }}</div>
                <div class="footer">
                    <div class="releaseDate">{{ movie.releaseDate.split("-")[0] }}</div>
                    <div class="avg-rate" v-if="movie.avgRate>0">⭐️ {{ movie.avgRate.toFixed(1) }}</div>
                </div>
            </div>
        </router-link>
    </transition-group>

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

export default {
    components: {
        EmptyComponent,
        InfiniteLoading,
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
            this.axios.get("/movies", {params})
            .then((response)=>{
                if(response.data.list.length>0) {
                    this.movieList.push(...response.data.list);
                    this.page = response.data.page+1;
                    $state.loaded();
                }
            })
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
.query {
    font-size: 18px;
}
#movie-list {
    display: flex;
    flex-wrap: wrap;
    gap: 20px;
}
.item {
    width: calc((100% - 80px)/5);
    padding-bottom: 20px;
    flex-grow: 0;
    display: flex;
    flex-direction: column;
    gap: 8px;
    white-space: nowrap;
    overflow: hidden;
    text-overflow: ellipsis;
}
@media screen and (max-width:1179px) {
    .item {
        width: calc((100% - 60px)/4);
    }
}
@media screen and (max-width:860px) {
    .item {
        width: calc((100% - 40px)/3);
    }
}
@media screen and (max-width:600px) {
    .item {
        width: calc((100% - 40px)/2);
    }
}
@media screen and (max-width:414px) {
    .item {
        width: 100%;
    }
}
.poster {
    width: 100%;
    padding-top: 150%;
    background-size: cover;
    background-position: center;
    display: flex;
    justify-content: center;
    border: 1px solid var(--G200);
    border-radius: 4px;
}
.avg-rate {
    font-weight: 700;
    font-size: 14px;
}
.info {
    display: flex;
    flex-direction: column;
}
.title {
    font-weight: 600;
    line-height: 1.4;
}
.genre, .releaseDate {
    color: var(--G400);
    font-size: 14px;
}
.footer {
    display: flex;
    justify-content: space-between;
}
</style>