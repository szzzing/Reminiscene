<template>
    <backdrop-component v-bind:movie="movie"></backdrop-component>
    <div class="container">
        <detail-component v-bind:movie="movie"></detail-component>
        <stastics-component></stastics-component>
        <comment-component v-bind:movie="movie"></comment-component>
    </div>
</template>

<script>
import DetailComponent from './DetailComponent.vue'
import CommentComponent from './CommentComponent.vue'
import BackdropComponent from './BackdropComponent.vue';
import StasticsComponent from './StasticsComponent.vue';
import movieAxios from '@/axios/movieAxios';

export default {
    components: { 
        DetailComponent,
        CommentComponent,
        BackdropComponent,
        StasticsComponent,
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
            const response = await movieAxios
            .get('api.themoviedb.org/3/movie/'+this.$route.params.id+'?api_key=7bf40bf859def4eaf9886f19bb497169&language=ko-KR');
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
            this.movie.release_date = this.movie.release_date.split('-').join('.');
            if(this.movie.backdrop_path) {
                this.movie.backdrop_path = 'https://image.tmdb.org/t/p/original/'+this.movie.backdrop_path;
            }
            if(this.movie.poster_path) {
                this.movie.poster_path = 'https://image.tmdb.org/t/p/original/'+this.movie.poster_path;
            }
            var genre = [];
            for(var g of this.movie.genres) {
                genre.push(g.name);
            }
            this.movie.genres = genre.join('/');
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