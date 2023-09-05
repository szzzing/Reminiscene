<template>
    <backdrop-component v-bind:movie="movie"></backdrop-component>
    <div class="container">
        <detail-component v-bind:movie="movie"></detail-component>
        <comment-component v-bind:movie="movie"></comment-component>
    </div>
</template>

<script>
import DetailComponent from './DetailComponent.vue'
import CommentComponent from './CommentComponent.vue'
import BackdropComponent from './BackdropComponent.vue';

export default {
    components: { 
        DetailComponent,
        CommentComponent,
        BackdropComponent,
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
    created() {
        this.fetchMovie();
    },
    data() {
        return {
            movie: null,
        }
    },
    methods: {
        fetchMovie() {
            this.axios.get('/movie/'+this.$route.params.id)
            .then((response)=>{
                this.movie = response.data;
            })
            .catch(()=>{
                this.$router.push('/error');
            });
        },
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
        '$route.params.id': 'fetchMovie',
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