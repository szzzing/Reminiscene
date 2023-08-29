<template>
    <backdrop-component v-bind:movie="movie"></backdrop-component>
    <div class="container">
        <detail-component v-bind:movie="movie"></detail-component>
        <statistics-component></statistics-component>
        <comment-component v-bind:movie="movie"></comment-component>
    </div>
</template>

<script>
import DetailComponent from './DetailComponent.vue'
import CommentComponent from './CommentComponent.vue'
import BackdropComponent from './BackdropComponent.vue';
import StatisticsComponent from './StatisticsComponent.vue';

export default {
    components: { 
        DetailComponent,
        CommentComponent,
        BackdropComponent,
        StatisticsComponent,
    },
    // /list 페이지로 이동시 검색결과 유지
    // 그 외 페이지로 이동시 검색결과 유지 x
    beforeRouteLeave(to, from, next) {
        if(!to.path.startsWith('/list') && !to.path.startsWith('/login')) {
            this.$store.commit('movie/setQuery', '');
            this.$store.commit('movie/setList', []);
        }
        next();
    },
    async beforeCreate() {
        try {
            const response = await this.axios.get('/movie/'+this.$route.params.id);
            this.movie = response.data;
        } catch(error) {
            this.$router.push('/error');
        }
    },
    data() {
        return {
            movie: null,
        }
    },
    watch: {
        // 영화 상세정보 가공
        movie() {
            this.movie.releaseDate = this.movie.releaseDate.split('-').join('.');
            if(this.movie.backdropPath) {
                this.movie.backdropPath = '/upload/backdrop/'+this.movie.backdropPath;
            }
            if(this.movie.posterPath) {
                this.movie.posterPath = '/upload/poster/'+this.movie.posterPath;
            }
        },
    },
}
</script>

<style scoped>
.container {
    margin-top: 40px;
    max-width: 1280px;
    display: flex;
    flex-direction: column;
    gap: 72px;
}
</style>