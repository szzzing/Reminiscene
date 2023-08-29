<template>
    <div id="wish" class="container">
        <title-component>
            <template v-slot:emoji>🧞‍♂️</template>
            <template v-slot:title>
                {{ user.nickname=='' ? user.id : user.nickname }}
                님이<br>보고싶은 영화
            </template>
        </title-component>

        <movie-list-component v-bind:list="list"></movie-list-component>

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
import MovieListComponent from '../item/MovieListComponent.vue';

export default {
    components: {
        TitleComponent,
        InfiniteLoading,
        EmptyComponent,
        MovieListComponent,
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