<template>
    <div id="movie-list" class="inner" v-if="this.$store.state.searchList.length>0">
        <transition-group name="list" tag="ul" class="movies">
            <li class="movie"
                v-for="(movie) in searchList" :key="movie"
                @click="clickContent(movie.id)">
                <router-link :to="`/content/${movie.id}`">
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
        <div class="next"
            v-if="this.$store.state.page<this.$store.state.maxPage"
            @click="this.nextPage()">
            다음 페이지
        </div>
    </div>
</template>

<script>
import {mapState} from 'vuex'

export default {
    data() {
        return {
            isContent: false,
            selected: ''
        }
    },
    computed: {
        ...mapState(['searchList'])
    },
    methods: {
        clickContent(id) {
            this.$store.dispatch('getDetails', id);
        },
        nextPage() {
            this.$store.commit('setPage', this.$store.state.page+1);
            this.$store.dispatch('searchList');
        }
    }
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
        background: #222;
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
        color: #fff;
    }
    .movie .info .date {
        color: #aaa;
        font-size: 14px;
        margin-bottom: 4px;
    }
    .movie .vote-average {
        color: #fff;
        font-weight: 700;
        font-size: 14px;
        position: absolute;
        top: 12px;
        right: 12px;
        height: 32px;
        line-height: 32px;
        padding: 0px 12px 0px 10px;
        border-radius: 16px;
        background: #222;
    }
    .next {
        text-align: center;
        color: #fff;
        margin: 60px 0 0;
        cursor: pointer;
    }
</style>