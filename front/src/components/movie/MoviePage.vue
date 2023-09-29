<template>
    <backdrop-component v-bind:movie="movie"></backdrop-component>
    <div class="container">
        <detail-component v-bind:movie="movie"
        @changeStatus="fetchMovie"></detail-component>
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
    created() {
        this.fetchMovie();
    },
    data() {
        return {
            movie: null,
            commentFlag: false, // 코멘트 입력 감지
        }
    },
    emits: [
        'changeStatus',
    ],
    methods: {
        fetchMovie() {
            this.axios.get('/movie/'+this.$route.params.id)
            .then((response)=>{
                this.movie = response.data;
            })
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