<template>
    <div v-if="this.movie!=null">
        <div class="backdrop" :style="{'background-image': 'url('+this.movie.backdrop_path+')' }">
            <div class="container">
                <div class="title emoji">
                    {{ this.movie.title }}
                </div>
                <div class="original-title emoji" v-if="this.movie.title !=this.movie.original_title">
                    {{ this.movie.original_title }}
                </div>
                <div class="genre-release emoji"
                v-if="this.movie.genres+this.movie.release_date!=''">
                    {{ [this.movie.genres,this.movie.release_date].join(" ・ ") }}
                </div>
                <div class="runtime emoji"
                v-if="this.movie.runtime!=0">
                    {{ this.movie.runtime+'분' }}
                </div>
            </div>
        </div>
        <div class="container"
            v-if="this.movie.overview!=''">
            <div class="inner">
                <div class="tagline"
                v-if="this.movie.tagline!=''">
                    {{ this.movie.tagline }}
                </div>
                <div class="overview">
                    {{ this.movie.overview }}
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import anonymous from '@/axios/anonymousAxios';

export default {
    async beforeCreate() {
        try {
            const response = await anonymous
            .get('api.themoviedb.org/3/movie/'+this.$route.params.id+'?api_key=7bf40bf859def4eaf9886f19bb497169&language=ko-KR');
            this.movie = response.data;
        } catch(error) {
            error;
        }
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
            this.movie.backdrop_path = 'https://image.tmdb.org/t/p/original/'+this.movie.backdrop_path
            var genre = [];
            for(var g of this.movie.genres) {
                genre.push(g.name);
            }
            this.movie.genres = genre.join('/');
        }
    }
}
</script>

<style scoped>
    .backdrop {
        height: 100vh;
        background-size: cover;
        background-position: center;
        height: 70vh;
        position: relative;
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
    .title {
        font-size: 48px;
        font-weight: 700;
    }
    .original-title {
        font-size: 24px;
        font-weight: 400;
    }
    .genre-release {
        margin-top: 8px;
    }
    .runtime {
        margin-top: 4px;
    }
    .genre-release, .runtime {
        font-size: 18px;
        font-weight: 500;
    }
    .tagline {
        font-size: 24px;
        font-weight: 600;
        margin-bottom: 12px;
    }
    .overview {
        font-size: 18px;
        line-height: 1.4;
    }
</style>