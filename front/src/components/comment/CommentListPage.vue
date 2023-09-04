<template>
    <div id="comment-list" class="container" v-if="this.movie">

        <title-component>
            <template v-slot:emoji>✨</template>
            <template v-slot:title>
                {{ this.movie.title }}의
                <br>코멘트
            </template>
        </title-component>

        <comment-list-component v-bind:list="list"></comment-list-component>

        <empty-component v-if="this.list.length==0">
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
import TitleComponent from '../item/TitleComponent.vue';
import CommentListComponent from '../item/CommentListComponent.vue';

export default {
    components: {
        InfiniteLoading,
        TitleComponent,
        EmptyComponent,
        CommentListComponent,
    },
    created() {
        this.getMovie();
    },
    data() {
        return {
            user: this.$store.state.auth.user,
            page: 1,
            sort: null,
            list: [],
            movie: null,
        }
    },
    methods: {
        async getMovie() {
            try {
                const response = await this.axios.get('/movie/'+this.$route.params.id);
                console.log(response.data)
                this.movie = response.data;
            } catch(error) {
                this.$router.push('/error');
            }
        },
        getComment($state) {
            const params = {
                page: this.page,
                sort: this.sort,
                movieId: this.$route.params.id,
            }
            this.axios.get("/comments", {params})
            .then((response)=>{
                console.log(response.data);
                if(response.data.list.length!=0) {
                    this.list.push(...response.data.list);
                    this.page = response.data.page + 1;
                    $state.loaded();
                } else {
                    $state.complete();
                }
            })
        },
    },
}
</script>

<style scoped>
.container {
    max-width: 960px;
    display: flex;
    flex-direction: column;
}
.title {
    font-size: 24px;
    font-weight: 700;
}

.inner {
    display: flex;
    flex-direction: column;
    gap: 24px;
}
</style>