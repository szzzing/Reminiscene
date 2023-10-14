<template>

    <backdrop-component v-bind:movie="movie"></backdrop-component>
    <div class="container" v-if="this.movie">
        
        <comment-list-component v-bind:list="list"></comment-list-component>
        
        <empty-component v-if="this.list && this.list.length==0">
            <template v-slot:text>
                {{ this.movie.title }}에 등록된 코멘트가 없어요.
            </template>
        </empty-component>
        
        <infinite-loading @infinite="getComment"></infinite-loading>
    </div>

</template>

<script>
import InfiniteLoading from 'infinite-loading-vue3-ts';
import EmptyComponent from '../item/EmptyComponent.vue';
import CommentListComponent from '../item/CommentListComponent.vue';
import BackdropComponent from './BackdropComponent.vue';

export default {
    components: {
    InfiniteLoading,
    EmptyComponent,
    CommentListComponent,
    BackdropComponent
},
    created() {
        this.getMovie();
    },
    data() {
        return {
            user: this.$store.state.auth.user,
            page: 1,
            sort: null,
            list: null,
            movie: null,
            state: null,
            listCount: 0,
        }
    },
    methods: {
        getMovie() {
            this.axios.get('/movie/'+this.$route.params.id)
            .then((response)=>{
                this.movie = response.data;
            })
        },
        getComment($state) {
            this.state = $state;

            const params = {
                page: this.page,
                sort: this.sort,
                movieId: this.$route.params.id,
            }
            this.axios.get("/comment", {params})
            .then((response)=>{
                if(response.data.list.length!=0) {
                    if(response.data.page==1) {
                        this.list = response.data.list;
                    } else {
                        this.list.push(...response.data.list);
                    }
                    this.page = response.data.page + 1;
                    this.listCount = response.data.listCount;
                    $state.loaded();
                }
            })
        },
        fetchData() {
            this.getMovie();

            const params = {
                page: 1,
                sort: this.sort,
                movieId: this.$route.params.id,
            }
            this.axios.get("/comment", {params})
            .then((response)=>{
                this.list = response.data.list;
                this.page = response.data.page + 1;
                this.state.loaded();
            })
        }
    },
    watch: {
        '$route.params.id': 'fetchData',
    },
}
</script>

<style scoped>
.container {
    max-width: 960px;
    display: flex;
    flex-direction: column;
    margin-top: 40px;
}
</style>