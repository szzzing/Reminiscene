<template>
    <div id="wish" class="container">
        <title-component>
            <template v-slot:emoji>🧞‍♂️</template>
            <template v-slot:title>
                {{ user.nickname=='' ? user.id : user.nickname }}
                님이<br>보고싶은 영화
            </template>
        </title-component>

        <transition-group id="movie-list" tag="div" class="list">
            <router-link class="item item-shadow" v-for="(movie) in this.list" :key="movie" :to="`/detail/${movie.id}`">
                <div class="thum" v-if="movie.poster_path"
                :style="{'background-image': 'url(https://image.tmdb.org/t/p/original/'+movie.poster_path+')' }">
                </div>
                <div class="no-image" v-if="!movie.poster_path">
                    <div class="icon">🌙</div>
                </div>
                <div class="info">
                    <div class="title">
                        {{ movie.title }}
                    </div>
                    <div class="date">
                        {{ movie.release_date.split('-')[0] }}
                    </div>
                </div>
            </router-link>
        </transition-group>
        <empty-component v-if="this.list.length==0">
            <template v-slot:text>
                보고싶은 영화가 없어요.
            </template>
        </empty-component>
        <infinite-loading @infinite="getList"></infinite-loading>
    </div>
</template>

<script>
import InfiniteLoading from 'infinite-loading-vue3-ts';
import TitleComponent from '../item/TitleComponent.vue';
import EmptyComponent from '../item/EmptyComponent.vue';
import movieAxios from '@/axios/movieAxios';

export default {
    components: {
        TitleComponent,
        InfiniteLoading,
        EmptyComponent,
    },

    data() {
        return {
            list: [],
            page: 1,
            user: this.$store.state.auth.user,
        }
    },

    methods: {
        getList($state) {
            const params = {
                userId: this.$store.state.auth.user.id,
                page: this.page,
            }
            this.axios.get("/wish", {params})
            .then((response)=>{
                console.log(response.data);
                if(response.data.list.length>0) {
                    this.page = response.data.page + 1;
                    this.getMovie(response.data.list);
                    $state.loaded();
                } else {
                    $state.complete();
                }
            })
        },
        async getMovie(list) {
            try {
                for(const l of list) {
                    const response = await movieAxios.get('api.themoviedb.org/3/movie/'+l.movieId+'?api_key=7bf40bf859def4eaf9886f19bb497169&language=ko-KR');
                    this.list.push(response.data);
                }
            } catch(error) {
                console.log(error);
            }
        }
    }
}
</script>

<style scoped>
.container {
    max-width: 960px;
    display: flex;
    flex-direction: column;
}
.list {
    display: flex;
    gap: 20px;
    flex-wrap: wrap;
    align-items: flex-start;
}
.item {
    width: calc((100% - 80px)/5);
    word-break: keep-all !important;
    cursor: pointer;
    border-radius: 16px;
    overflow: hidden;
    flex-grow: 0;
}
@media screen and (max-width:1179px) {
    .item {
        width: calc((100% - 60px)/4);
    }
}
@media screen and (max-width:768px) {
    .item {
        width: calc((100% - 40px)/3);
    }
}
@media screen and (max-width:600px) {
    .item {
        width: calc((100% - 20px)/2);
    }
}
.info {
    text-align: center;
    padding: 12px 20px;
}
.thum, .no-image {
    position: relative;
    width: 100%;
    padding-top: 150%;
    background-size: cover;
    background-position: center;
    display: flex;
    justify-content: center;
}
.no-image {
    background-color: var(--G1000);
}
.no-image .icon {
    font-size: 36px;
    position: absolute;
    top: calc((100% - 50px)/2);
}
.info .title {
    font-weight: 600;
    text-align: center;
    word-break: keep-all !important;
}
.info .date {
    color: var(--G400);
    font-size: 14px;
    margin-bottom: 4px;
}
.vote-average {
    font-weight: 700;
    font-size: 14px;
    position: absolute;
    top: 12px;
    right: 12px;
    height: 32px;
    line-height: 32px;
    padding: 0px 12px 0px 10px;
    border-radius: 16px;
    background: #f9fafb50;
    box-shadow: 0px 0px 10px var(--O100);
    backdrop-filter: blur(16px);
}
.dark .vote-average {
    background: #18181820;
}

</style>