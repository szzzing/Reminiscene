<template>
    <div id="detail" v-if="this.movie!=null">

        <div class="backdrop" :style="{'background-image': 'url('+movie.backdrop_path+')' }">
            <div class="container">
                <div class="title shadow">
                    {{ movie.title }}
                </div>
                <div class="original-title shadow" v-if="movie.title !=movie.original_title">
                    {{ movie.original_title }}
                </div>
                <div class="genre-release shadow" v-if="movie.genres+movie.release_date!=''">
                    {{ [movie.genres, movie.release_date].join(" ・ ") }}
                </div>
                <div class="runtime shadow" v-if="movie.runtime!=0">
                    {{ movie.runtime+'분' }}
                </div>
            </div>
        </div>

        <div class="container flex-container">

            <div class="inner" v-if="movie.poster_path">
                <img class="poster" :src="movie.poster_path">
            </div>

            <div class="inner">
                <rate-component v-bind:movie="movie"></rate-component>
                <div class="info-area max-size" v-if="movie.overview!=''">
                    <div class="tagline" v-if="movie.tagline!=''">{{ movie.tagline }}</div>
                    <div class="overview">{{ movie.overview }}</div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import movieAxios from '@/axios/movieAxios';
import RateComponent from './RateComponent.vue';

export default {
    async beforeCreate() {
        try {
            const response = await movieAxios
            .get('api.themoviedb.org/3/movie/'+this.$route.params.id+'?api_key=7bf40bf859def4eaf9886f19bb497169&language=ko-KR');
            this.movie = response.data;
        } catch(error) {
            this.$router.push('/error');
        }
    },
    components: {
        RateComponent,
    },
    data() {
        return {
            movie: null
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
.backdrop {
    background: var(--G1000);
    background-size: cover;
    background-position: center;
    height: 70vh;
    position: relative;
}
.dark .backdrop {
    background-color: var(--G50);
}
.backdrop * {
    color: #fff;
}
.backdrop > .container {
    position: absolute;
    bottom: 0;
    left: 0;
    right: 0;
}
.container {
    max-width: 1280px;
    padding: 40px 0;
}
.flex-container {
    display: flex;
    flex-wrap: wrap-reverse;
    gap: 20px;
}
.inner {
    display: flex;
    flex-direction: column;
    gap: 48px;
    justify-content: space-between;
}
.flex-container > .inner:first-child {
    flex-basis: 300px;
}
.flex-container > .inner:last-child {
    flex-basis: 600px;
    flex-grow: 1;
    flex-shrink: 1;
}
.title {
    font-size: 36px;
    font-weight: 700;
}
.genre-release {
    margin-top: 8px;
}
.genre-release, .runtime {
    font-weight: 500;
}
.poster {
    max-width: 300px;
    object-fit: cover;
    margin: auto;
}
.tagline {
    font-weight: 600;
    font-size: 18px;
    margin-bottom: 12px;
}
</style>