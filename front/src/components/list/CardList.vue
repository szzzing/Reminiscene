<template>
    <div id="movie-list">
        <transition-group name="list" tag="ul" class="movies">
            <li class="movie"
                v-for="(movie) in this.$store.state.movie.list" :key="movie">
                <router-link :to="`/detail/${movie.id}`">
                    <span class="movie-inner">
                        <div class="thum" :style="{'background-image': 'url(https://image.tmdb.org/t/p/original/'+movie.poster_path+')' }">
                            <div class="vote-average">
                                ⭐️
                                {{ movie.vote_average.toFixed(1) }}
                            </div>
                        </div>
                        <div class="info">
                            <div class="title">
                                {{ movie.title }}
                            </div>
                            <div class="date">
                                {{ movie.release_date.split('-')[0] }}
                            </div>
                        </div>
                    </span>
                </router-link>
            </li>
        </transition-group>
    </div>
</template>

<script>
export default {

}
</script>

<style scoped>
    .movies {
        display: flex;
        gap: 60px 20px;
        flex-wrap: wrap;
    }
    .movie {
        width: calc((100% - 80px)/5);
        word-break: keep-all !important;
        cursor: pointer;
        overflow: visible;
    }
    @media screen and (max-width:1023px) {
        .movie {
            width: calc((100% - 60px)/4);
        }
    }
    @media screen and (max-width:768px) {
        .movie {
            width: calc((100% - 40px)/3);
        }
    }
    @media screen and (max-width:479px) {
        .movie {
            width: calc((100% - 20px)/2);
        }
    }
    .movie-inner {
        display: block;
        border-radius: 16px;
        overflow: hidden;
        background: var(--G0);
        filter: drop-shadow(0px 0px 6px var(--O100));
    }
    .dark .movie-inner {
        background: var(--G50);
        filter: none;
    }
    .movie .info {
        text-align: center;
        padding: 8px 20px 12px;
    }
    .thum {
        position: relative;
        width: 100%;
        padding-top: 150%;
        background-size: cover;
        background-position: center;
    }
    .movie .thum img {
        width: 240px;
    }
    .movie .info .title {
        font-size: 16px;
        font-weight: 600;
        text-align: center;
        word-break: keep-all !important;
        margin-bottom: 8px;
        
    }
    .movie .info .date {
        color: var(--G400);
        font-size: 14px;
        margin-bottom: 4px;
    }
    .movie .vote-average {
        font-weight: 700;
        font-size: 14px;
        position: absolute;
        top: 12px;
        right: 12px;
        height: 32px;
        line-height: 32px;
        padding: 0px 12px 0px 10px;
        border-radius: 16px;
        background: #f9fafb40;
        box-shadow: 0px 0px 10px var(--O100);
        backdrop-filter: blur(16px);
    }
    .dark .movie .vote-average {
        background: #18181820;
    }
    
</style>