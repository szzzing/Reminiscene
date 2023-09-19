<template>
    <div id="wish" v-if="this.user">
        <movie-list-component v-bind:list="list" v-bind:movie="true"></movie-list-component>

        <empty-component v-if="this.list && this.list.length==0">
            <template v-slot:text>
                보고있는 영화가 없어요.
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
        }
    },
    props: [
        'user',
    ],
    methods: {
        fetchData() {
            const params = {
                userId: this.$route.params.id,
                page: 1,
            }
            this.axios.get("/watching", {params})
            .then((response)=>{
                this.page = response.data.page + 1;
                this.list = response.data.list;
                this.state.reset();
            })
        },
        getList($state) {
            this.state = $state;
            
            const params = {
                userId: this.$route.params.id,
                page: this.page,
            }
            this.axios.get("/watching", {params})
            .then((response)=>{
                if(response.data.list.length>0) {
                    this.page = response.data.page + 1;
                    if(response.data.page==1) {
                        this.list = response.data.list;
                    } else {
                        this.list.push(...response.data.list);
                    }
                    $state.loaded();
                }
            })
        },
    },
    watch: {
        '$route.params.id': 'fetchData',
    },
}
</script>

<style scoped>
</style>