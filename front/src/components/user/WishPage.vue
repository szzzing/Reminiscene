<template>
    <div id="wish" class="container" v-if="this.user">
        <title-component>
            <template v-slot:emoji>🧞‍♂️</template>
            <template v-slot:title>
                {{ user.nickname=='' ? user.id : user.nickname }}
                님이<br>보고싶은 영화
            </template>
        </title-component>

        <transition-group id="movie-list" tag="div" class="list">
            <router-link class="item" v-for="(movie) in this.list" :key="movie" :to="`/detail/${movie.id}`">
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

export default {
    components: {
        TitleComponent,
        InfiniteLoading,
        EmptyComponent,
    },
    beforeCreate() {
        this.axios.get("/user/"+this.$route.params.id)
        .then((response)=>{
            this.user = response.data;
        });
    },
    data() {
        return {
            list: [],
            page: 1,
            user: null,
        }
    },

    methods: {
        getList($state) {
            const params = {
                userId: this.user.id,
                page: this.page,
            }
            this.axios.get("/wish", {params})
            .then((response)=>{
                if(response.data.list.length>0) {
                    this.page = response.data.page + 1;
                    this.list.push(...response.data.list);
                    $state.loaded();
                } else {
                    $state.complete();
                }
            })
        },
    }
}
</script>

<style scoped>
.container {
    max-width: 960px;
    display: flex;
    flex-direction: column;
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