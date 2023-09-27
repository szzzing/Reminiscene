<template>
    <div id="wish" v-if="this.user">
        <movie-list-component v-bind:list="list"></movie-list-component>

        <empty-component v-if="this.list && this.list.length==0">
            <template v-slot:text>
                보고싶은 영화가 없어요.
            </template>
        </empty-component>
        
        <infinite-loading @infinite="getList"></infinite-loading>
    </div>
</template>

<script>
import InfiniteLoading from 'infinite-loading-vue3-ts';
import EmptyComponent from '../item/EmptyComponent.vue';
import MovieListComponent from '../item/MovieListComponent.vue';

export default {
    components: {
        InfiniteLoading,
        EmptyComponent,
        MovieListComponent,
    },
    data() {
        return {
            list: null,
            page: 1,
            state: null,
            // user: this.$store.state.auth.user,
        }
    },
    props: [
        'user'
    ],
    methods: {
        getList($state) {
            this.state = $state;

            const params = {
                userId: this.user.id,
                page: this.page,
            }
            this.axios.get("/wish", {params})
            .then((response)=>{
                if(response.data.list.length>0) {
                    this.page = response.data.page + 1;
                    if(response.data.page==1) {
                        this.list = response.data.list;
                    } else {
                        this.list.push(...response.data.list);
                    }
                    $state.loaded();
                } else {
                    if(response.data.page==1) {
                        this.list = [];
                    }
                }
            })
        },
    },
}
</script>

<style scoped>
</style>