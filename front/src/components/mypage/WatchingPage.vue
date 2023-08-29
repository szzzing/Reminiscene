<template>
    <div id="wish" class="container">
        <title-component>
            <template v-slot:emoji>🍿</template>
            <template v-slot:title>
                {{ user.nickname=='' ? user.id : user.nickname }}
                님이<br>보고있는 영화
            </template>
        </title-component>

        <movie-list-component v-bind:list="list"></movie-list-component>

        <empty-component v-if="this.list.length==0">
            <template v-slot:text>
                보고있는 영화가 없어요.
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
            this.axios.get("/watching", {params})
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
</style>