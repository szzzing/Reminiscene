<template>
    <div id="search-list">

        <div class="inner">
            <div class="menu">
                <div class="title">영화 검색결과</div>
                <router-link :to="`/search/movie/${this.query}`" v-if="this.movieList.length!=0">더보기</router-link>
            </div>
            <movie-list-component v-bind:list="movieList" v-bind:hide="true"></movie-list-component>
            <empty-component v-if="this.movieList.length==0">
                <template v-slot:text>
                    검색결과가 없어요.
                </template>
            </empty-component>
        </div>
        
        <div class="inner">
            <div class="menu">
                <div class="title">유저 검색결과</div>
                <router-link :to="`/search/user/${this.query}`" v-if="this.userList.length!=0">더보기</router-link>
            </div>
            <user-list-component v-bind:list="userList" v-bind:hide="true"></user-list-component>
            <empty-component v-if="this.userList.length==0">
                <template v-slot:text>
                    검색결과가 없어요.
                </template>
            </empty-component>
        </div>
    </div>

    <infinite-loading @infinite="getList"></infinite-loading>
</template>

<script>
import MovieListComponent from '../item/MovieListComponent.vue';
import { InfiniteLoading } from 'infinite-loading-vue3-ts';
import EmptyComponent from '../item/EmptyComponent.vue';
import UserListComponent from '../item/UserListComponent.vue';

export default {
    components: {
        MovieListComponent,
        InfiniteLoading,
        EmptyComponent,
        UserListComponent,
    },
    props: [
        'query',
    ],
    data() {
        return {
            movieList: [],
            userList: [],
        }
    },
    methods: {
        // 영화 리스트 불러오기
        getList($state) {
            const params = {
                query: this.query,
                page: 1,
            }
            this.axios.get("/movie", {params})
            .then((response)=>{
                if(response.data.list.length>0) {
                    this.movieList.push(...response.data.list);
                }
                $state.complete();
            })
            this.axios.get("/user", {params})
            .then((response)=>{
                if(response.data.list.length>0) {
                    this.userList.push(...response.data.list);
                }
            })
        },
    }
}
</script>

<style scoped>
#search-list {
    display: flex;
    flex-direction: column;
    gap: 72px;
}
.inner {
    display: flex;
    flex-direction: column;
    gap: 24px;
}
.menu {
    display: flex;
    justify-content: space-between;
    align-items: flex-end;
    padding: 8px 0px;
    border-bottom: 1px solid var(--G100);
}
.title {
    color: var(--G600);
    font-size: 18px;
    font-weight: 700;
}
a {
    color: var(--FOCUS);
}
</style>